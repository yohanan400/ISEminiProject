package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void add() {
    }

    @Test
    void findIntersections() {
        Plane plane = new Plane(new Point3D(1,0,0), new Point3D(2,0,0), new Point3D(1.5,0,1));
        Sphere sphere = new Sphere(new Point3D(1,0,1), 1);
        Triangle triangle = new Triangle(new Point3D(1,2,0), new Point3D(2,2,0), new Point3D(1.5,2,1));
        Geometries geometries = new Geometries(plane, sphere, triangle);


        // ============ Equivalence Partitions Tests ==============
        //TC01: More the one object intersect (but not all the objects)
        Ray rayManyObjectIntersect = new Ray(new Point3D(1,1.5,1), new Vector(0,-1,0));
        assertEquals(List.of(new Point3D(1,1, 1), new Point3D(1,0, 1) ), geometries.findIntersections(rayManyObjectIntersect),
                "More then one object intersect (but not all the objects)");

        // =============== Boundary Values Tests ==================
        //TC10: Empty list
        Geometries geometriesEmptyList = new Geometries();
        Ray rayEmptyList = new Ray(new Point3D(1,1,1), new Vector(0,-1,0));

        assertNull(geometriesEmptyList.findIntersections(rayEmptyList), "The List empty");

        // TC11: No intersection with the objects
        Ray rayNoIntersections = new Ray(new Point3D(1,-1,1), new Vector(0,-1,0));

        assertNull(geometries.findIntersections(rayNoIntersections), "The ray suppose not intersect the objects");

        //TC12: One object intersect
        Ray rayOneObjectIntersect = new Ray(new Point3D(1.5,1.5,0.5), new Vector(0,1,0));
        assertEquals(List.of(new Point3D(1.5,2, 0.5)), geometries.findIntersections(rayOneObjectIntersect),
                "Suppose to be one intersection point (one object intersect)");

        //TC13: All the objects intersect
        Ray rayAllObjectIntersect = new Ray(new Point3D(1.5,2.5,0.5), new Vector(0,-1,0));
        assertEquals(List.of(new Point3D(1.5,2, 0.5), new Point3D(1.5,1, 0.5), new Point3D(1.5,-1, 0.5) ,new Point3D(1.5,0, 0.5)), geometries.findIntersections(rayAllObjectIntersect),
                "Suppose to be 4 intersection points");



    }
}