package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing Triangle Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {
        Triangle t = new Triangle(new Point3D(0, 1, 0), new Point3D(0, 5, 0), new Point3D(0, 3, 5));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The intersection point is in the triangle
        assertEquals(List.of(new Point3D(0, 3, 1)),
                t.findIntersections(new Ray(new Point3D(1, 3, 0), new Vector(-1, 0, 1))),
                "The point supposed to be in the triangle");

        // TC02: The intersection point is outside the triangle, against edge
        assertNull(t.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 1))),
                "The point supposed to be outside the triangle, against edge");

        // TC03: The intersection point is outside the triangle, against vertex
        assertNull(t.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0.1, -0.1))),
                "The point supposed to be outside the triangle, against vertex");

        // =============== Boundary Values Tests ==================
        // TC10: The point is on edge
        assertNull(t.findIntersections(new Ray(new Point3D(1, 3, 0), new Vector(-1, 0, 0))),
                "The point supposed to be on edge");

        // TC11: The point is in vertex
        assertNull(t.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(-1, 0, 0))),
                "The point supposed to be in vertex");

        // TC12: The point is on edge's continuation
        assertNull(t.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0.1, 0))),
                "The point supposed to be on edge's continuation");
    }
}