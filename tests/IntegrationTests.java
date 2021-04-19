import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {


    @Test
    public void sphereIntegrationTest() {

        //TC01: Sphere r=1 (2 intersections)
        Sphere sphere1 = new Sphere(new Point3D(0, 0, -3), 1d);
        Camera camera1 = new Camera(new Point3D(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera1.setDistance(1);
        Ray ray1 = new Ray(camera1.getP0(), camera1.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(List.of(new Point3D(0, 0, -2), new Point3D(0, 0, -4)),
                sphere1.findIntersections(ray1),
                "sphere with r=1");

        //TODO: CHANGE THE lIST.OF TO 2. (AN INTEGER INSTEAD lIST) AND ADD .SIZE() TO THE ASSERT.

        //TC02: Sphere r=2.5 (18 intersections)
        Sphere sphere2 = new Sphere(new Point3D(0, 0, -2.5), 2.5);
        Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera2.setDistance(1);
        Ray ray2 = new Ray(camera2.getP0(), camera2.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(18, sphere2.findIntersections(ray2).size(), "sphere with r=2.5");


        //TC03: Sphere r=2 (10 intersections)
        Sphere sphere3 = new Sphere(new Point3D(0, 0, -2), 2d);
        Camera camera3 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera3.setDistance(1);
        Ray ray3 = new Ray(camera3.getP0(), camera3.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(10, sphere3.findIntersections(ray3).size(), "sphere with r=2");

        //TC04: Sphere r=4 (9 intersections)
        Sphere sphere4 = new Sphere(new Point3D(0, 0, -0.5), 4d);
        Camera camera4 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera4.setDistance(1);
        Ray ray4 = new Ray(camera4.getP0(), camera4.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(9, sphere4.findIntersections(ray4).size(), "sphere with r=4");

        //TC05: Sphere r=0.5 (0 intersections)
        Sphere sphere5 = new Sphere(new Point3D(0, 0, 1), 0.5);
        Camera camera5 = new Camera(new Point3D(0, 0, 0.25), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera5.setDistance(1);
        Ray ray5 = new Ray(camera5.getP0(), camera5.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(0, sphere4.findIntersections(ray5).size(), "sphere with r=0.5");

    }

    @Test
    public void planeIntegrationTest() {

        //TC01: The plane parallel to the View Plane (9 intersections)
        Plane plane1 = new Plane(new Point3D(0,0,-2),new Vector(0,0,1));
        Camera camera1 = new Camera(new Point3D(0, 0, 1), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera1.setDistance(1);
        Ray ray1 = new Ray(camera1.getP0(), camera1.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(9, plane1.findIntersections(ray1).size(), "parallel plane");

        //TC02: Diagonal plane to the View Plane (9 intersections)
        Plane plane2 = new Plane(new Point3D(0,0,-2),new Vector(0,4,1));
        Camera camera2 = new Camera(new Point3D(0, 0, 1), new Vector(0, 1, 0), new Vector(0, 0, -1));
        camera2.setDistance(1);
        Ray ray2 = new Ray(camera2.getP0(), camera2.constructRayThroughPixel(3, 3, 3, 3).getDir());

        assertEquals(9, plane2.findIntersections(ray2).size(), "slant plane");

        //TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)

    }


    }
