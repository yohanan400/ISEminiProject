package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Cylinder Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        Cylinder cy = new Cylinder(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 1, 10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: check the first base
        assertEquals(new Point3D(0,1,0) ,cy.getNormal(new Point3D(0,0.5,0)).getHead(),
                "Bad normal to first base of cylinder");

        // TC02: check the second base
        assertEquals(new Point3D(0,1,0) ,cy.getNormal(new Point3D(0,0.5,10)).getHead(),
                "Bad normal to second base of cylinder");

        // TC03: check the side of the cylinder
        assertEquals(new Point3D(0,1,0) ,cy.getNormal(new Point3D(0,1,5)).getHead(),
                "Bad normal to the side of the cylinder");

        // =============== Boundary Values Tests ==================
        // TC10: check first center base normal (if p = o)
        assertEquals(cy._axisRay.getDir(), cy.getNormal(new Point3D(0,0,0)),
                "Bad normal to first base center of the cylinder");

        // TC11: check second center base normal (if p = o)
        assertEquals(cy._axisRay.getDir(), cy.getNormal(new Point3D(0,0,10)),
                "Bad normal to second base center of the cylinder");
    }
}