package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * class to represent Geometries as a group
 */
public class Geometries extends Intersectable {

    /**
     * group of geometries as a linked list
     */
    private final List<Intersectable> groupOfGeometries = new LinkedList<>();

    /**
     * constructor given geometries
     *
     * @param geometries list of Geometries
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds group of geometries to Class
     *
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        if (geometries.length > 0) groupOfGeometries.addAll(List.of(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : groupOfGeometries) {
            var temp = geometry.findGeoIntersections(ray);
            if (temp != null) {
                if (intersections == null)
                    intersections = new LinkedList<>(temp);
                else
                    intersections.addAll(temp);
            }
        }
        return intersections;
    }

}
