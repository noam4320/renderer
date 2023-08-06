/**
 *
 */
package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Vectors
 *
 * @author Daniel
 *
 */
class PointTests {

    /**
     * test method for adding point to point
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(-1, -2, -3);
        //check addition
        assertEquals(Point.ZERO, p1.add(v1));
        //check symmetry of addition
        assertEquals(Point.ZERO.add(v1.scale(-1)), p1);
    }

    /**
     * test method to subtract point from point
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        //checks if point minus point correctly gives vector
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Point(2, 3, 4).subtract(p1);
        assertEquals(v1, new Vector(1, 1, 1));
        //checks length of vector
        assertEquals(v1.length(), new Vector(1, 1, 1).length());
    }
}
