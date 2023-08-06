package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        // ============ Boundary Tests ==============
        //check for random sphere
        Sphere orb = new Sphere(3,new Point(2, 6, 4));
        //point on top of sphere should have vector(0,0,1)
        assertEquals(orb.getNormal(new Point(2, 6, 7)), new Vector(0, 0, 1), "Failed Spheretest test\"");
        // ============ Equivalence Partitions Tests ==============
        //random point on sphere
        assertEquals(orb.getNormal(new Point(2 + Math.sqrt(3), 6 + Math.sqrt(3), 4)), new Vector(1 / Math.sqrt(2), 1 / Math.sqrt(2), 0), "Failed Spheretest test\"");

    }
    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)} (primitives.Point)}.
     */
    @Test
    void findIntersectionsTest() {

        // ============ Equivalence Partitions Tests ==============

        //  Ray's line is outside the sphere (0 points)
        Sphere sphere = new Sphere(1,new Point(1, 0, 0));
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))));

        //  Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result1 = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(result1.size(), 2, "Wrong number of points failed sphere test1");
        if (result1.get(0).getX() > result1.get(1).getX())
            result1 = List.of(result1.get(1), result1.get(0));
        assertEquals(result1, List.of(p1, p2), "Ray crosses sphere failed shpere test");
        // Ray starts in sphere

        Point p3 = p1.add(new Vector(3,1,0).scale(0.4));
        List<Point> result2 = sphere.findIntersections(new Ray(p3, new Vector(3, 1, 0)));
        assertEquals(result2, List.of(p2), "ray does not cross sphere failed sphere test");
        assertEquals(result2.size(), 1, "wrong number of points failed sphere test2");
        //ray line goes through sphere, but not the ray itself (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))));
        // ============ Boundary Tests ==============
        //line tangent to sphere
        //ray starts before sphere
        assertNull(sphere.findIntersections(new Ray(new Point(2, 2, 0), new Vector(0, -1, 0))), "failed boundary test");
        //ray starts after sphere
        assertNull(sphere.findIntersections(new Ray(new Point(2, -2, 0), new Vector(0, -1, 0))), "failed boundary test");
        //ray starts on sphere
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, -1, 0))), "failed boundary test");
        //line on sphere with line going through sphere
        //line on sphere, going outside
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 1, 0))), "failed boundary test");
        //line on sphere, going inside
        assertEquals(sphere.findIntersections(new Ray(new Point(1+Math.sqrt(2)/2, Math.sqrt(2)/2,0), new Vector(-1, 0, 0))), List.of(new Point(1-(Math.sqrt(2)/2), Math.sqrt(2)/2,0)), "failed boundary test");
        //line going through center
        //tangent point with ray going outside
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(3, 0, 0))), "failed boundary test");
        //point outside of sphere, with ray going outward from sphere
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(3, 0, 0))), "failed boundary test");
        //point in sphere
        assertEquals(sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),List.of(new Point(2,0,0)) ,"failed boundary test");
        //point outside of sphere, with ray going into sphere
        assertEquals(sphere.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(1, 0, 0))), List.of(Point.ZERO, new Point(2,0,0)),
                "failed boundary test");
        //point on sphere, going through center
        assertEquals(sphere.findIntersections(new Ray(Point.ZERO, new Vector(3, 0, 0))), List.of( new Point(2,0,0)),"failed boundary test");
        //ray starting in center
        assertEquals(sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(3, 0, 0))), List.of( new Point(2,0,0)),"failed boundary test");


    }

}