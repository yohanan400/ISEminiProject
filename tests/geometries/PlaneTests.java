package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {


    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    void getNormal() throws Exception {
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        if (!pl.getNormal().equals(new Vector(sqrt3, sqrt3, sqrt3)) &&
                !pl.getNormal().scale(-1).equals(new Vector(sqrt3, sqrt3, sqrt3))) {
            throw new Exception("Bad normal to plane");
        }

        // =============== Boundary Values Tests ==================
        // TC10: Test if the first and second points coalesce.
        assertThrows(IllegalArgumentException.class, () -> {
            Plane pl2 = new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0));
        }, "The vector cannot be the 'zero vector' ");

        // TC11: Test if the points are on the same line.
        assertThrows(IllegalArgumentException.class, () -> {
            Plane pl3 = new Plane(new Point3D(1, 2, 1), new Point3D(1, 2, 2), new Point3D(1, 2, 3));
        }, "The vector cannot be the 'zero vector' ");
    }

}