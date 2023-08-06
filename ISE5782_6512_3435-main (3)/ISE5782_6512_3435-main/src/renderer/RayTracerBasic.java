package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import primitives.Scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class to implement Ray tracer. it can return the color of Ray
 */
public class RayTracerBasic extends RayTraceBase {
    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * Cunstructor that gets scene as parameter
     *
     * @param scene Scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculate color method that gets geoPoint & Ray as parameters
     *
     * @param gp  geoPoint
     * @param ray Ray
     * @return the calculated color at the provided geoPoint
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
    }

    /**
     * Method to calculate the final color of the light that gets
     *
     * @param gp  the GeoPoint of intersection
     * @param ray the casted ray
     * @return Color
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));

    }

    /**
     * calculates the refraction and reflection of a ray, returning its final color
     *
     * @param gp    GoePoint  to calculate effects on
     * @param ray   ray intersecting with the point
     * @param level integer, number of times the ray has been reflected/refracted
     * @param k     strength of ray
     * @return the color fo the ray
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Color color = Color.BLACK;
        Vector normal = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = material.kR.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = calcGlobalEffects(constructReflected(gp, ray, normal), level, material.kR, kkr);
        }
        Double3 kkt = material.kT.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffects(constructRefracted(gp, ray, normal), level, material.kT, kkt));
        }
        return color;
    }

    /**
     * helper method to global effects
     *
     * @param ray   ray to calculate effects
     * @param level double, amount of times of recursion, level of depth going in
     * @param kx    constant to scale
     * @param kkx   constant to multiply by
     * @return color of the effects
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null) ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * helper function to calculate local effects of materials & reflections on the light
     *
     * @param gp  the gp of the ray\surface
     * @param ray the ray
     * @return the calculated color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmissions();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv) > 0) {
                Double3 ktr = transparency(gp, lightSource, l, n, nv);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(calcDiffusive(material.kD, nl, iL), calcSpecular(material.kS, l, n, nl, v, material.nShininess, iL));
                }
            }
        }
        return color;
    }

    /**
     * calculates the specular effects of light, returning a color
     *
     * @param ks         specular coefficient
     * @param l          vector
     * @param n          vector
     * @param nl         double, dot product of n and l vectors
     * @param V          vector of the specular
     * @param nShininess integer, shineness of material
     * @param ip         color
     * @return color of specular effects
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector V, int nShininess, Color ip) {
        double p = nShininess;
        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector R = l.add(n.scale(-2 * nl));
        double VR = alignZero(V.dotProduct(R));
        if (VR >= 0) {
            return Color.BLACK;
        }

        return ip.scale(ks.scale(Math.pow(-1d * VR, p)));
    }

    /**
     * calcualtes the diffusion effects
     *
     * @param kd             Doubled3  diffusion coefficient
     * @param nl             double  strength to diffuse
     * @param lightIntensity color to diffuse
     * @return color caused by diffusion
     */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * checks if point is shaded
     *
     * @param gp point to check if shaded
     * @param l  vector of light to check from
     * @return if point is shaded
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Double3 ktr = new Double3(1.0);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return ktr;

        for (GeoPoint geopoint : intersections) {
            if (light.getDistance(gp.point) > geopoint.point.distance(gp.point)) {
                ktr = ktr.product(geopoint.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }
        return ktr;
    }


    /**
     * calculates the reflection of a ray on a geometry point
     *
     * @param ray ray to reflect
     * @param gp  point to reflect on
     * @return reflected ray
     */
    public Ray constructReflected(GeoPoint gp, Ray ray, Vector normal) {

        Vector v = ray.getDir().add(normal.scale(-2 * ray.getDir().dotProduct(normal)));
        return new Ray(gp.point, v, normal);
    }

    /**
     * reflects a ray off a point, to find the reflected ray
     *
     * @param gp     point to reflect off
     * @param ray    ray to reflect
     * @param normal normal of geometry
     * @return the reflected ray
     */
    public Ray constructRefracted(GeoPoint gp, Ray ray, Vector normal) {
        return new Ray(gp.point, ray.getDir(), normal);
    }

    /**
     * finds the closest intersection of ray with a geometry
     *
     * @param ray ray to find intersections with
     * @return intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));

    }

    /**
     * calculates if a point is shaded or not
     *
     * @param gp    the geometric point
     * @param light the lightsource
     * @param l     vector of lightsource
     * @param n     normal of geometry
     * @param nv    dot product of n and vector of ray
     * @return true if the geometry point is unshaded
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector epsVector = n.scale(nv < 0 ? EPS : -EPS);
//        Point point = gp.point.add(epsVector);

        // Ray lightRay = new Ray(point, lightDirection);
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        GeoPoint geo = findClosestIntersection(lightRay);
        if (geo == null)
            return true;
        if (light.getDistance(gp.point) > geo.point.distance(gp.point))
            return false;
        if (!geo.geometry.getMaterial().kT.equals(0))
            return false;
        return true;
    }


}

