package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void scale() {
    }

    @Test
    void dotProduct() {

        // test Dot-Product
        if (!isZero(v1.dotProduct(v3)))
            out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            out.println("ERROR: dotProduct() wrong value");
    }


    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {

            // ============ Equivalence Partitions Tests ==============

            Vector vr = v1.crossProduct(v3);

            // TC01: Test that length of cross-product is proper (orthogonal vectors taken
            // for simplicity)
            assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

            // TC02: Test cross-product result orthogonality to its operands
            assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
            assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

            // =============== Boundary Values Tests ==================
            // TC11: test zero vector from cross-productof co-lined vectors

            assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                   "crossProduct() for parallel vectors does not throw an exception");
             try {
                 v1.crossProduct(v2);
                 fail("crossProduct() for parallel vectors does not throw an exception");
             } catch (Exception e) {}


    }

    @Test
    void length() {
        // test length..
        if (!isZero(v1.lengthSquared() - 14))
            out.println("ERROR: lengthSquared() wrong value");
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            out.println("ERROR: length() wrong value");
    }

    @Test
    void normalize() {
    }

    @Test
    void normalized() {
    }
}