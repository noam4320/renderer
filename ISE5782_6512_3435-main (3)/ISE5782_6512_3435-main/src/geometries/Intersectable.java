package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * abstract class to represent Intersectable between rays and Geometries
 */
public abstract class Intersectable {
    /**
     * Inner class to represent Geometry Point
     */
    public static class GeoPoint {
        /**
         * Variable to represent the Geometry on which the point is located
         */
        public Geometry geometry;
        /**
         * Variable to represent the point itself
         */
        public Point point;

        /**
         * constructor for GeoPoint
         *
         * @param geometry parameter for the Geometry
         * @param point    parameter for the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Old method for finding intersections between ray and the geometry. currently this returns uses the newer findGeoIntersections method
     * and maps the result to a List of Points
     *
     * @param ray the ray to be checked with the geometry
     * @return list of intersections
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Method to find intersections between geometry and rays
     *
     * @param ray the ray to be checked with the geometry
     * @return calls an inner helper function findGeoIntersectionsHelper();
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method to find intersections between geometry and rays
     *
     * @param ray
     * @return list of intersections as GeoPoints
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
