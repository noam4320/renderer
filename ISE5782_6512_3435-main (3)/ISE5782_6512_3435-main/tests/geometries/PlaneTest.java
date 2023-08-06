/**
 *
 */
package geometries;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Planes
 */
class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, -1, 0);
        Point p3 = new Point(-1, 0, 0);
        Plane p = new Plane(p1, p2, p3);
        assertEquals(p.getNormal().dotProduct(p2.subtract(p1)), 0);
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() {
        Plane plane = new Plane(new Point(1, 1, 0), new Point(2, 1, 0), new Point(4, 3, 0));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        assertEquals(List.of(new Point(1, 1, 0)), plane.findIntersections(new Ray(new Point(1, 1, 3), new Vector(0, 0, -1))));
        //TC02: Ray doesn't intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 3), new Vector(4, 4, 3))));

        // =============== Boundary Values Tests ==================
        //TC03: Ray is parallel & included in the plane
        List<Point> result = plane.findIntersections(new Ray(new Point(2, 2, 0), new Vector(2, 5, 0)));
        assertNull(result);

        //TC04: Ray is parallel but not included in the plane
        List<Point> result2 = plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(2, 1, 0)));
        assertNull(result2);

        //TC05: Ray is orthogonal to the plane & starts before the plane
        List<Point> result3 = plane.findIntersections(new Ray(new Point(1, 1, -4), new Vector(2, 2, 4)));
        assertEquals(List.of(new Point(3, 3, 0)), result3);

        //TC06: Ray is orthogonal to the plane & Starts at the plane
        List<Point> result4 = plane.findIntersections(new Ray(new Point(5, 5, 0), new Vector(5, 5, 5)));
        assertNull(result4);

        //TC07: Ray is orthogonal to the plane & Starts after the plane
        List<Point> result5 = plane.findIntersections(new Ray(new Point(5, 5, 1), new Vector(5, 5, 5)));
        assertNull(result5);

        //TC08: Ray is not orthogonal nor parallel to the plane, But starts at the plane
        List<Point> result6 = plane.findIntersections(new Ray(new Point(5, 5, 0), new Vector(6, 6, 6)));
        assertNull(result6);

        //TC09: Ray is not orthogonal nor parallel to the plane, But starts at the reference of the plane
        List<Point> result7 = plane.findIntersections(new Ray(new Point(1, 1, 0), new Vector(-2, 2, 2)));
        assertNull(result7);
    }
}