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

    double _height;

    /**
     * cylinder c-tor receiving a Ray, and two doubles
     *
     * @param axisRay Ray value
     * @param radius  double value
     * @param height  double value
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * getHeight return the height of the cylinder
     *
     * @return double value
     */
    public double getHeight() {
        return _height;
    }


    /**
     * getNormal method return the normal vector of the cylinder
     *
     * @param point Point3D value
     * @return return the normal of cylinder (Vector value)
     */
    @Override
    public Vector getNormal(Point3D point) {

        // if the point is the center base
        if (point.equals(_axisRay.getP0()) || point.equals(_axisRay.getP0().add(_axisRay.getDir().scale(_height)))) {
            return _axisRay.getDir();
        }

        double projection = _axisRay.getDir().dotProduct(point.subtract(_axisRay.getP0()));
        //if the point is on the bases but not the center point
        if (projection == 0) {
            Vector v1 = point.subtract(_axisRay.getP0());
            return v1.normalize();
        }

        //if the point is on the side of the cylinder
        Point3D center = _axisRay.getP0().add(_axisRay.getDir().scale(projection));
        Vector v = point.subtract(center);
        return v.normalize();

    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
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
