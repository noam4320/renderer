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
 * A class that represents a triangle. it inherits from polygon.
 */
public class Triangle extends Polygon {
    /**
     * constructor for triangle
     *
     * @param a vertex a
     * @param b vertex b
     * @param c vertex c
     */
    public Triangle(Point a, Point b, Point c) {
        super(a, b, c);
    }

    @Override
    public Vector getNormal(Point pointer) {
        return super.getNormal(pointer);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersection = plane.findGeoIntersections(ray);
        if (intersection == null) return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double check = alignZero(v.dotProduct(n1));
        if (check == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double temp = alignZero(v.dotProduct(n2));
        if (check * temp <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        temp = alignZero(v.dotProduct(n3));
        if (check * temp <= 0) return null;
        intersection.get(0).geometry = this;
        return intersection;
    }
}
