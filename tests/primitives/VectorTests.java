package primitives;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Vector Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class VectorTests {

    // Global vectors for all tests
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the add method is proper.
        assertEquals(v1.add(v2), new Vector(-1,-2, -3), "ERROR: add() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the subtract method is proper.
        assertEquals(v1.subtract(v2), new Vector(3,6, 9), "ERROR: subtract() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the scale product is proper.
        assertEquals(v1.scale(2), new Vector(2,4,6), "ERROR: scale() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {

        // ============ Equivalence Partitions Tests ==============
        // Test if the dot product is proper.
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        // Test dot-product by taking 2 orthogonal vectors and check if the result is 0.
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");

    }


    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {

        // ============ Equivalence Partitions Tests ==============

        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // Test zero vector from cross-product of co-lined vectors

        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception");

        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the length-squared is proper by subtract the real length-squared and check if the result is 0.
        assertTrue(isZero(v1.lengthSquared() - 14),"ERROR: lengthSquared() wrong value" );
    }


    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the length is proper by subtract the real length and check if the result is 0.
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }


    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(v1.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        // Test if the normalize method return a new vector
        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");

        // Test if the length of the normal equal to 1.
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    void normalized() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the normalized method return a new vector.
        Vector u = v1.normalized();
        assertNotSame(u, v1, "ERROR: normalized() function does not create a new vector");
    }


}