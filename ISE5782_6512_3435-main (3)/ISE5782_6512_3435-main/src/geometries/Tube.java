/**
 * @authors: Daniel Yanovsky, id: 311026512
 * Noam Bleiberg, id: 334063435
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * a class that represents a tube. has a Ray fot axis, and a Double for radius.
 */
public class Tube extends Geometry {
    final protected Ray axisRay;
    final protected double radius;

    /**
     * constructor of tube
     *
     * @param axisRay the axis of the tube
     * @param radius  radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter
     *
     * @return axisRay of tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter
     *
     * @return radius of tube
     */
    public double getRadius() {
        return radius;
    }

    /**
     * method to get the normal to the tube
     *
     * @param p1 point to find the normal of
     * @return result of getNormal
     */
    public Vector getNormal(Point p1) {
        double t = axisRay.getDir().dotProduct(p1.subtract(axisRay.getP0()));  //finds number of times to multiply vector to get to point orthogonal to point on surface
        Point o = axisRay.getPoint(t); //projection of p1 onto ray
        return p1.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector delta = ray.getP0().subtract(axisRay.getP0());
        double a, b, c;
        double cross1 = ray.getDir().dotProduct(axisRay.getDir());

        if (delta.dotProduct(axisRay.getDir()) == 0)
            return List.of(new GeoPoint(this, ray.getPoint(ray.getP0().distance(axisRay.getP0()) - radius)), new GeoPoint(this, (ray.getPoint(ray.getP0().distance(axisRay.getP0()) + radius))));


        if (cross1 == 0) {
            a = ray.getDir().lengthSquared();
            b = 2 * (ray.getDir().dotProduct(delta.subtract(axisRay.getDir().scale(delta.dotProduct(axisRay.getDir())))));
            c = delta.subtract(axisRay.getDir().scale(delta.dotProduct(axisRay.getDir()))).lengthSquared() - radius * radius;
        } else {
            a = ray.getDir().subtract(axisRay.getDir().scale(ray.getDir().dotProduct(axisRay.getDir()))).lengthSquared();
            b = 2 * (ray.getDir().subtract(axisRay.getDir().scale(axisRay.getDir().dotProduct(ray.getDir())))).dotProduct(delta.subtract(axisRay.getDir().scale(delta.dotProduct(axisRay.getDir()))));
            c = delta.subtract(axisRay.getDir().scale(delta.dotProduct(axisRay.getDir()))).lengthSquared() - radius * radius;
        }
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0)
            return null;
        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        if (discriminant < 0)
            return null;
        if (discriminant == 0 && t1 < 0)
            return null;
        if (discriminant == 0 && t1 == 0)
            return List.of(new GeoPoint(this, ray.getP0()), new GeoPoint(this, ray.getPoint(t2)));
        if (discriminant == 0 && t1 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }
}