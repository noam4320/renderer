package geometries;

import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    /**
     * test method for finding Intersections between ray and geometry {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {
        Geometries geometries = new Geometries();
        Ray ray = new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0));
        assertNull(geometries.findIntersections(ray), "does not return null");//no shapes intersect when there are zero bodies
        geometries.add(new Sphere(1,new Point(7, 4, 0)));
        assertNull(geometries.findIntersections(ray), "does not return null"); //no shape intersect when there is one body
        geometries.add(new Sphere(1,new Point(3, 0, 0)));
        List<Point> result = new ArrayList<Point>();
        assertEquals( geometries.findIntersections(ray).size(),2, "does not return 2 points"); //only one shape intersect but not all
        geometries.add(new Sphere(2,new Point(10, 0, 0)));
        assertEquals(geometries.findIntersections(ray).toArray().length,4,"didn't result in 4 points"); //some shapes but not all intersect
        Geometries geometries2=new Geometries((new Sphere(1, new Point(2,0,0))),new Sphere (2,new Point(10,0,0)),new Plane(new Point(16,3,4), new Point(16,5,6),new Point(16,7,7)));
        List<Point> result2 =  new ArrayList<Point>();
        assertEquals(geometries2.findIntersections(ray).toArray().length, 5,"didn't find 4 points"); //all points intersect


    }
}