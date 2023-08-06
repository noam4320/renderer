/**
 * @authors: Daniel Yanovsky, id: 311026512
 * Noam Bleiberg, id: 334063435
 */
package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

/**
 * Class that represents a Ray. When constructed - it automatically normalizes the vector it uses.
 */
public class Ray {
    final private Point p0; //point and vector to define ray
    final private Vector dir;
    final private double DELTA = 0.1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * Constructor that gets a point and a vector and constructs a Ray. it automatically normalizes the vector.
     *
     * @param p0  point to initialize
     * @param dir vector vector initialize
     */

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }


    /**
     * getter
     *
     * @return dir value
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * getter
     *
     * @return P0 value
     */
    public Point getP0() {
        return p0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    //to string
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * finds closest point that ray intersects with
     *
     * @param points list of points, to find closest one
     * @return closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * finds closest GeoPoint that ray intersects wtih
     *
     * @param intersections list of GeoPoints, to find closest one
     * @return GeoPoint closest to ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        GeoPoint temp = null;
        if (intersections == null || intersections.isEmpty())
            return null;
        double minDist = Double.POSITIVE_INFINITY;
        for (var p : intersections) {
            double pointDist = p0.distance(p.point);
            if (minDist > pointDist) {
                minDist = pointDist;
                temp = p;
            }
        }
        return temp;
    }

    /**
     * finds point that intersects with ray
     *
     * @param t t*vector is distance to point
     * @return point intersection
     */
    public Point getPoint(Double t) {
        try {
            return p0.add(dir.scale(t));
        } catch (IllegalArgumentException ignore) {
            return p0;
        }
    }

    /**
     * constructor that moves the point by a small amount, to the normal vector
     *
     * @param point  point to move
     * @param vector vector
     * @param normal vector to move point by
     */
    public Ray(Point point, Vector vector, Vector normal) {
        double sign = vector.dotProduct(normal);
        if (sign >= 0)
            p0 = point.add(normal.scale(DELTA));
        else p0 = point.add(normal.scale(-DELTA));
        dir = vector;
    }

}
