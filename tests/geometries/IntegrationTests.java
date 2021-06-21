//package geometries;
//
//import elements.Camera;
//import org.junit.jupiter.api.Test;
//import primitives.Point3D;
//import primitives.Ray;
//import primitives.Vector;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * Testing constructRayThroughPixel(int, int, int, int)
// * and findIntersections(Ray) of Sphere, Plane, and Triangle.
// */
//public class IntegrationTests {
//
//
//    /**
//     * Goes through the whole View Plane and summarise all the intersections points
//     *
//     * @param geometry the intersect object
//     * @param camera   the camera
//     * @param nY       number of rows (int)
//     * @param nX       number of columns (int)
//     * @return the number of intersections
//     */
//    private int sumOfIntersections(Intersectable geometry, Camera camera, int nY, int nX) {
//
//        int count = 0;
//        for (int i = 0; i < nX; i++) {
//            for (int j = 0; j < nY; j++) {
//                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);
//                if (geometry.findIntersections(ray) != null)
//                    count += geometry.findIntersections(ray).size();
//            }
//        }
//        return count;
//    }
//
//    /**
//     * Test method for
//     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}
//     * and {@link geometries.Sphere#findIntersections(Ray)}.
//     */
//    @Test
//    public void sphereIntegrationTest() {
//
//        //TC01: Sphere r=1 (2 intersections)
//        Sphere sphere1 = new Sphere(new Point3D(0, 0, -3), 1d);
//        Camera camera1 = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0),
//                new Vector(0, 0, -1)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);
//        assertEquals(2, sumOfIntersections(sphere1, camera1, 3, 3), "sphere with r=1");
//
//        //--new camera for tests 02,03,04--
//        Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 1, 0),
//                new Vector(0, 0, -1)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);
//
//        //TC02: Sphere r=2.5 (18 intersections)
//        Sphere sphere2 = new Sphere(new Point3D(0, 0, -2.5), 2.5);
//        assertEquals(18, sumOfIntersections(sphere2, camera2, 3, 3), "sphere with r=2.5");
//
//        //TC03: Sphere r=2 (10 intersections)
//        Sphere sphere3 = new Sphere(new Point3D(0, 0, -2), 2d);
//        assertEquals(10, sumOfIntersections(sphere3, camera2, 3, 3), "sphere with r=2");
//
//        //TC04: Sphere r=4 (9 intersections)
//        Sphere sphere4 = new Sphere(new Point3D(0, 0, -0.5), 4d);
//        assertEquals(9, sumOfIntersections(sphere4, camera2, 3, 3), "sphere with r=4");
//
//        //TC05: Sphere r=0.5 (0 intersections)
//        Sphere sphere5 = new Sphere(new Point3D(0, 0, 1), 0.5);
//        Camera camera3 = new Camera(new Point3D(0, 0, 0.25), new Vector(0, 1, 0),
//                new Vector(0, 0, -1)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);
//        assertEquals(0, sumOfIntersections(sphere5, camera3, 3, 3), "sphere with r=0.5");
//
//    }
//
//    /**
//     * Test method for
//     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}
//     * and {@link geometries.Plane#findIntersections(Ray)}.
//     */
//    @Test
//    public void planeIntegrationTest() {
//
//        Camera camera = new Camera(new Point3D(0, 0, 1), new Vector(0, 1, 0),
//                new Vector(0, 0, -1)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);
//
//        //TC01: The plane parallel to the View Plane (9 intersections)
//        Plane plane1 = new Plane(new Point3D(0, 0, -2), new Vector(0, 0, 1));
//        assertEquals(9, sumOfIntersections(plane1, camera, 3, 3), "parallel plane");
//
//        //TC02: Diagonal plane to the View Plane (9 intersections)
//        Plane plane2 = new Plane(new Point3D(0, 0, -2), new Vector(0, -1, 4));
//        assertEquals(9, sumOfIntersections(plane2, camera, 3, 3), "slant plane");
//
//        //TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)
//        Plane plane3 = new Plane(new Point3D(0, 0, -2), new Vector(0, -4, 1));
//        assertEquals(6, sumOfIntersections(plane3, camera, 3, 3),
//                "slant plane with an obtuse angle to the View Plane");
//    }
//
//    /**
//     * Test method for
//     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}
//     * and {@link geometries.Triangle#findIntersections(Ray)}.
//     */
//    @Test
//    public void triangleIntegrationTest() {
//
//        Camera camera = new Camera(new Point3D(0, 0, 1), new Vector(0, 1, 0),
//                new Vector(0, 0, -1)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);
//
//        //TC01: Small triangle (1 intersection)
//        Triangle triangle1 = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
//        assertEquals(1, sumOfIntersections(triangle1, camera, 3, 3), "small triangle");
//
//        //TC02: Large triangle (2 intersection)
//        Triangle triangle2 = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
//        assertEquals(2, sumOfIntersections(triangle2, camera, 3, 3), "Large triangle");
//
//
//    }
//}
