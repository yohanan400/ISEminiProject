package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Point3D Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class Point3DTest {

    // Global points for all tests
    Point3D p1 = new Point3D(1, 2,3);
    Point3D p2 = new Point3D(5,6,7);

    /**
     * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the subtract method is proper.
        assertEquals(new Vector(1, 1, 1), new Point3D(2, 3, 4).subtract(p1),
                "ERROR: Point-subtract does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the add method is proper.
        assertEquals(Point3D.ZERO,p1.add(new Vector(-1, -2, -3)),"ERROR: Point-add does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}
     */
    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the distanceSquared is proper.
        assertEquals(p1.distanceSquared(p2), 48, "ERROR: distanceSquared() does not work correctly");


    }

    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}
     */
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        // Test if the distance is proper.
        assertEquals(p1.distance(p2), Math.sqrt(48), "ERROR: distance() does not work correctly");
    }

}