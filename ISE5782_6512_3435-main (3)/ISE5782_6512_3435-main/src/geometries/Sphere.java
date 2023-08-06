/**
 * @authors: Daniel Yanovsky, id: 311026512
 * Noam Bleiberg, id: 334063435
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class that represents a ephere. represented by a center<point> and a radius<double>
 */
public class Sphere extends Geometry {
    /**
     * Variable for the canter of the Sphere
     */
    private final Point center;

    /**
     * Variable for the radius of the Sphere
     */
    private final double radius;

    /**
     * Variable for the second radius of the Sphere
     */
    private final double radius2;

    /**
     * constructor for the Sphare
     *
     * @param radius radius
     * @param center center
     */
    public Sphere(double radius, Point center) {
        this.center = center;
        this.radius = radius;
        radius2 = radius * radius;
    }

    /**
     * returns vector going from center to point, normalized
     *
     * @param pointer
     * @return
     */
    public Vector getNormal(Point pointer) {
        return pointer.subtract(this.center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (ray.getP0().equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));

        Vector v = ray.getDir();
        Vector u = center.subtract(ray.getP0());
        double tm = v.dotProduct(u);
        double d2 = u.dotProduct(u) - tm * tm;
        double th2 = alignZero(radius2 - d2);
        if (th2 <= 0)
            return null;
        double th = Math.sqrt(th2);
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }
};