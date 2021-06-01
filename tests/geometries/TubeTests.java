package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Tube Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        Tube tu = new Tube(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)), 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(new Vector(0, 0, 1), tu.getNormal(new Point3D(1, 0, 1)), "Bad normal to tube");

        // =============== Boundary Values Tests ==================
        // Test when the point is orthogonal to the head of the ray.
        // Causes to the ZERO vector.
        assertThrows(IllegalArgumentException.class, () -> {
                    tu.getNormal(new Point3D(0, 0, 1));
                },
                "can't be ZERO vector");
    }
}