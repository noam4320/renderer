package geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Point p1 = new Point(1,0,0);
        Point p2 = new Point(0,-1,0);
        Point p3 = new Point(-1,0,0);
        Plane p = new Plane(p1,p2,p3);
        System.out.println(p.getNormal().length());
        assertEquals(p.getNormal().dotProduct(p2.subtract(p1)), 0);
        assertEquals(p.getNormal().dotProduct(p3.subtract(p1)), 0);
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void findsIntersections() {
        Triangle triangle = new Triangle(new Point(3,3,0), new Point (3,0,0), new Point(0,3,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray is inside Triangle
        assertEquals(List.of(new Point(2, 2, 0)), triangle.findIntersections(new Ray(new Point(2, 2, -4), new Vector(0,0,2))));
        //TC02: Ray is outside against the edge of a triangle
        assertNull(triangle.findIntersections(new Ray(new Point(3,2,4), new Vector(0,0,-2))));
        //TC03: Ray is outside against the vertex of the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(4,4,4), new Vector(0,0,-8))));

        // =============== Boundary Values Tests ==================
        //TC04: Ray is on the edge of a triangle
        assertNull(triangle.findIntersections(new Ray(new Point(3, 3, -2), new Vector(0,0,4))));
        //TC05: Ray is on the vertex of a triangle
        assertNull(triangle.findIntersections(new Ray(new Point(3, 3, -4), new Vector(0,0,2))));
        //TC06: Ray is on the continuation of an edge of a triangle
        assertNull(triangle.findIntersections(new Ray(new Point(4, 3, -2), new Vector(0,0,2))));
    }
}