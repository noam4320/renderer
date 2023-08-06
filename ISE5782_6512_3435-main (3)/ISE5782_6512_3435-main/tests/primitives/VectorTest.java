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
class VectorTest {

    /**
     * test method for comparing two vectors
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Two equal vectors
        assertEquals(new Vector(1, 1, 1), new Vector(1, 1, 1), "Failed testEquals test");
    }

    /**
     * test method for adding two vectors
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: add two vectors and compare to a third
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 2, 2);
        assertEquals(v1.add(v2), new Vector(3, 3, 3), "Failed add test");
    }

    /**
     * test method for subtracting vector from vector
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        //
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 2, 2);
        assertEquals(v1.scale(2), v2, "Failed scale test");
        assertEquals(v1.scale(2).length(), Math.sqrt(12), "Failed scale test");

    }

    /**
     * test method for crossProduct between two vectors
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);


        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * test method for getting the length-squared of a vector
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(v1.scale(2).lengthSquared(), 12, "Failed length squared test");
    }

    /**
     * test method for getting the length of a vector
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(v1.scale(2).length(), Math.sqrt(12), "Failed length test");
    }

    /**
     * test method for normalizing a vector
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        double num = 1 / Math.sqrt(3);
        assertEquals(v1.normalize(), (new Vector(num, num, num)), "failed normalize test");

    }

    /**
     * test method for dotProduct of two vectors
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 3, 4);
        //test non parallel vectors
        assertEquals(v1.dotProduct(v2), 9, "failed dot product test");
        // ============ Boundary Values Tests ==============
        //test parallel vectors
        Vector v3 = new Vector(3, 3, 3);
        assertEquals(v1.dotProduct(v3), v1.length() * v3.length(), "failed dot product test");
        //test orthogonal vectors
        Vector v4 = new Vector(-1, 0, 1);
        assertEquals(v1.dotProduct(v4), 0, "failed dot product test");

    }
}