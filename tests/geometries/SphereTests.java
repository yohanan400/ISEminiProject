package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing Sphere Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        Sphere sp = new Sphere(new Point3D(0, 0, 0), 5);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(new Point3D(0, 0, 1), sp.getNormal(new Point3D(0, 0, 5)).getHead(), "Bad normal to sphere");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    public void findIntersectionsTest() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);
        Vector dirVector = new Vector(3, 1, 0);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), dirVector));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals(List.of(p2),
                sphere.findIntersections(new Ray(new Point3D(0.8, 0.6, 0), dirVector)),
                "Ray crosses sphere on one point");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 1, 0))),
                "Ray's line after the sphere");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(p2),
                sphere.findIntersections(new Ray(p1, dirVector)),
                "Ray starts at sphere and goes inside");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), dirVector)), "Ray starts at sphere and goes inside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        assertEquals(List.of(new Point3D(0, 0, 0), new Point3D(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0))),
                "Ray starts before the sphere and need to be 2 intersections");

        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals(List.of(new Point3D(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0))),
                "Ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 points)
        assertEquals(List.of(new Point3D(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0))),
                "Ray starts inside ");

        // TC16: Ray starts at the center (1 points)
        assertEquals(List.of(new Point3D(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0.5, 0, 0))),
                "Ray starts at the center ");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))),
                "Ray starts at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))),
                "Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, -1, -1), new Vector(0, 1, 1))),
                "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 1, 1))),
                "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 2, 2), new Vector(0, 1, 1))),
                "Ray starts after the tangent point");
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(0, 0, 1))),
                "Ray starts after the tangent point");

    }
}