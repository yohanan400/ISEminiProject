package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        Sphere sp = new Sphere(new Point3D(0,0,0), 5 );

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(new Point3D(0,0,1), sp.getNormal(new Point3D(0,0,5)).getHead(), "Bad normal to sphere");
    }
}