package primitives;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Testing  Ray Class
 *
 * @author Aviel buta and Yakir Yohanan
 */
class RayTests {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    void findClosestPoint() {

        List<Point3D> point3DList = new LinkedList<>();

        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(2, 2, 2);
        Point3D p3 = new Point3D(3, 3, 3);

        point3DList.add(p1);
        point3DList.add(p2);
        point3DList.add(p3);

        Vector dirVector = new Vector(0, -0.5, 0);

        // ============ Equivalence Partitions Tests ==============
        //TC01: The closest point is in the middle of the list
        Ray ray1 = new Ray(new Point3D(2, 2.5, 2), dirVector);
        assertEquals(p2, ray1.findClosestPoint(point3DList), "The point in the middle!!");

        // =============== Boundary Values Tests ==================
        //TC10: The closest point is the first point in the list
        Ray ray2 = new Ray(new Point3D(1, 1.25, 1), dirVector);
        assertEquals(p1, ray2.findClosestPoint(point3DList), "The point is the first one!!");

        //TC11: The closest point is the last point in the list
        Ray ray3 = new Ray(new Point3D(3, 3.5, 3), dirVector);
        assertEquals(p3, ray3.findClosestPoint(point3DList), "The point is the last one!!");

        //TC12: The list is null
        point3DList.clear();
        assertNull(ray3.findClosestPoint(point3DList), "The list is empty!!");
    }

    /**
     * Test method for {@link primitives.Ray#getClosestGeoPoint(List)}
     */
    @Test
    void getClosestGeoPoint() {

        List<GeoPoint> geoPointsList = new LinkedList<>();

        Sphere sphere = new Sphere(new Point3D(1, 1, 1), 0);

        GeoPoint geoPoint1 = new GeoPoint(sphere, new Point3D(1, 1, 1));
        GeoPoint geoPoint2 = new GeoPoint(sphere, new Point3D(2, 2, 2));
        GeoPoint geoPoint3 = new GeoPoint(sphere, new Point3D(3, 3, 3));

        geoPointsList.add(geoPoint1);
        geoPointsList.add(geoPoint2);
        geoPointsList.add(geoPoint3);

        Vector dirVector = new Vector(0, -0.5, 0);

        // ============ Equivalence Partitions Tests ==============
        //TC01: The closest point is in the middle of the list
        Ray ray1 = new Ray(new Point3D(2, 2.5, 2), dirVector);
        assertEquals(geoPoint2, ray1.getClosestGeoPoint(geoPointsList), "The point in the middle!!");

        // =============== Boundary Values Tests ==================
        //TC10: The closest point is the first point in the list
        Ray ray2 = new Ray(new Point3D(1, 1.25, 1), dirVector);
        assertEquals(geoPoint1, ray2.getClosestGeoPoint(geoPointsList), "The point is the first one!!");

        //TC11: The closest point is the last point in the list
        Ray ray3 = new Ray(new Point3D(3, 3.5, 3), dirVector);
        assertEquals(geoPoint3, ray3.getClosestGeoPoint(geoPointsList), "The point is the last one!!");

        //TC12: The list is null
        geoPointsList.clear();
        assertNull(ray3.getClosestGeoPoint(geoPointsList), "The list is empty!!");
    }
}
