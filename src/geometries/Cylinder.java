package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


/**
 * Cylinder class representing a three-dimensional cylinder in 3D Cartesian coordinate
 * system
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder
     */
    double _height;

    /**
     * Cylinder c-tor receiving a Ray, and two doubles
     *
     * @param axisRay The centered ray of the cylinder
     * @param radius  The radius of the cylinder
     * @param height  The height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius); // initialize the fields to the received values
        System.out.println("Cylinder created");
        _height = height;
    }

    /**
     * Return the height of the cylinder
     *
     * @return The height of the cylinder (double)
     */
    public double getHeight() {
        return _height;
    }


    /**
     * Return the normal vector of the cylinder
     *
     * @param point The point to measure the normal (Point3D)
     * @return The normal of cylinder (Vector)
     */
    @Override
    public Vector getNormal(Point3D point) {

        // The center of sides of the cylinder
        Vector directionOfCylinder = _axisRay.getDir();
        Point3D centerOfOneSide = _axisRay.getP0();
        Point3D centerOfSecondSide = _axisRay.getP0().add(_axisRay.getDir().scale(_height));

        System.out.println("cylinder getNormal called");

        // If the point is the center base (on the sides of the cylinder)
        if (point.equals(centerOfOneSide) || point.equals(centerOfSecondSide)) {
            // return the centered ray

            System.out.println("on the center of the cylinder");

            return directionOfCylinder;
        }

        double projection = directionOfCylinder.dotProduct(point.subtract(centerOfOneSide));
        //If the point is on the bases but not the center point
        if (projection == 0) {
            Vector v1 = point.subtract(centerOfOneSide);
            System.out.println("on the bases but not center");

            return v1.normalize();
        }

        System.out.println("on the side");

        //If the point is on the side of the cylinder
        Point3D center = centerOfOneSide.add(directionOfCylinder.scale(projection));
        Vector v = point.subtract(center);
        return v.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {



        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Cylinder cylinder = (Cylinder) o;

        return Double.compare(cylinder._height, _height) == 0;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
