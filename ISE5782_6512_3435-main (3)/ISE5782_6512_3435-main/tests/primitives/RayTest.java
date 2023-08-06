package primitives;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * test method for finding the closest point from ray to geometry
     */
    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        Ray ray=new Ray(Point.ZERO, new Vector(1,0,0));
        List<Point> points=List.of(new Point(2,0,0),new Point(1.5,0,0),new Point (3,0,0),new Point(5,0,0));
        // =============== Boundary Values Tests ==================

        //first element is closest
        assertEquals(ray.findClosestPoint(points),new Point(1.5,0,0));
        List<Point> points2=List.of(new Point(1.2,0,0),new Point(1.5,0,0),new Point (3,0,0),new Point(5,0,0));
        assertEquals(ray.findClosestPoint(points2),new Point(1.2,0,0));
        //last element is closest
        List<Point> points3=List.of(new Point(1.2,0,0),new Point(1.5,0,0),new Point (3,0,0),new Point(1.1,0,0));
        assertEquals(ray.findClosestPoint(points3),new Point(1.1,0,0));
        //null list
        assertEquals(null,ray.findClosestPoint(List.of()));

    }
}