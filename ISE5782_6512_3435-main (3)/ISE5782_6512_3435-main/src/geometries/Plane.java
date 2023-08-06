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
import static primitives.Util.isZero;

/**
 * Plane class represents one dimensional plane
 */
public class Plane extends Geometry {
    /**
     * q0     ---> reference point for the plane
     */
    private final Point q0;
    /**
     * normal ---> direction for the plane
     */
    private final Vector normal;

    /**
     * constructor for plane with 3 points
     *
     * @param a point
     * @param b point
     * @param c point
     */
    Plane(Point a, Point b, Point c) {
        q0 = a;
        normal = ((b.subtract(a)).crossProduct(c.subtract(a))).normalize();
    }

    /**
     * constructor for plane with a point & vector
     *
     * @param point  point
     * @param vector vector
     */
    public Plane(Point point, Vector vector) {
        q0 = point;
        normal = vector.normalize();
    }

    /**
     * getter for the reference point of the plane
     *
     * @return reference point
     */
    public Point getQ0() {
        return q0;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * getter for the normal to the plane
     *
     * @return normal vector to the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        double denominator = normal.dotProduct(ray.getDir());
        if (isZero(denominator)) return null;

        Vector u;
        try {
            u = q0.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            return null; // p0 = q0
        }

        double t = alignZero((normal.dotProduct(u)) / denominator);
        return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}
