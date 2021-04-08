package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
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

    @Test
    void findIntsersections() {
        Plane plane = new Plane(new Point3D(1,0,1), new Point3D(0,1,1), new Point3D(1,1,1));

        // ================ EP: The Ray must be neither orthogonal nor parallel to the plane ==================
        //TC01: Ray intersects the plane
        assertEquals(new Point3D(1,0.5,1),
                plane.findIntsersections(new Ray(new Point3D(0,0.5,0),new Vector(1,0,1))),
                "Ray intersects the plane");

        //TC02: Ray does not intersect the plane
        assertNull(plane.findIntsersections(new Ray(new Point3D(1,0.5,2), new Vector(1,2,5))),
                "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        //TC10: The ray included in the plane
        assertNull(plane.findIntsersections(new Ray(new Point3D(1,2,1), new Vector(1,0,0))),
                "Ray is parallel to the plane, the ray included in the plane");

        //TC11: The ray not included in the plane
        assertNull(plane.findIntsersections(new Ray(new Point3D(1,2,2), new Vector(1,0,0))),
                "Ray is parallel to the plane, the ray not included in the plane");

        // **** Group: Ray is orthogonal to the plane
        //TC12: according to 𝑃0, before the plane
        assertEquals(new Point3D(1,1,1),
                plane.findIntsersections(new Ray(new Point3D(1,1,0), new Vector(0,0,1))),
                "Ray is orthogonal to the plane, according to p0, before the plane");

        //TC13: according to 𝑃0, in the plane
        assertNull(plane.findIntsersections(new Ray(new Point3D(1,2,1), new Vector(0,0,1))),
                "Ray is orthogonal to the plane, according to p0, in the plane");

        //TC14: according to 𝑃0, after the plane
        assertNull(plane.findIntsersections(new Ray(new Point3D(1,2,2), new Vector(0,0,1))),
                "Ray is orthogonal to the plane, according to p0, after the plane");

        // **** Group: Ray is neither orthogonal nor parallel to
        //TC15: Ray begin at the plane
        //TC16: begins in the same point which appears as reference point in the plane

    }
}