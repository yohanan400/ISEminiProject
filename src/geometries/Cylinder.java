package geometries;

import primitives.*;


/**
 * Cylinder class representing a three-dimensional cylinder in 3D Cartesian coordinate
 * system
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


    @Override
    public Vector getNormal(Point3D point) {

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
