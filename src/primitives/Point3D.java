package primitives;

import java.lang.Math;

/**
 * class Point3D representing a point in 3 domination
 *
 * @author Aviel Buta and Yakir Yohanan
 */

public class Point3D {

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public static Point3D ZERO = new Point3D(0.0d, 0.0d, 0.0d);

    /**
     * Point3D c-tor receiving 3 double values
     *
     * @param x double value
     * @param y double value
     * @param z double value
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    public double getX() {
        return _x.coord;
    }

    public double getY() {
        return _y.coord;
    }

    public double getZ() {
        return _z.coord;
    }

    /**
     * subtract method return new Vector witch his coordinates is the subtract of the
     * receiving point's coordinates from 'this' point's coordinates.
     *
     * @param point Point3D value
     * @return Vector value
     */
    public Vector subtract(Point3D point) {

        Point3D newPoint = new Point3D(_x.coord - point._x.coord,
                _y.coord - point._y.coord,
                _z.coord - point._z.coord);

        return new Vector(newPoint);
    }


    /**
     * add method return new Poind3D witch his coordinates is the sum of the
     * receiving vector's coordinates and 'this' point's coordinates.
     *
     * @param vector Vector value
     * @return Point3D value
     */
    public Point3D add(Vector vector) {

        Point3D newPoint = new Point3D(_x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord);

        return newPoint;
    }

    /**
     * distanceSquared method return the distance between 2 points power 2.
     *
     * @param point Point3D value
     * @return double value
     */
    public double distanceSquared(Point3D point) {

        double distanceX = (_x.coord - point._x.coord),
                distanceY = (_y.coord - point._y.coord),
                distanceZ = (_z.coord - point._z.coord);

        double distance = distanceX * distanceX
                + distanceY * distanceY
                + distanceZ * distanceZ;

        return distance;
    }

    /**
     * distanceSquared method return the distance between 2 points.
     *
     * @param point Point3D value
     * @return double value
     */
    public double distance(Point3D point) {

        return Math.sqrt(distanceSquared(point));

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3D point3D = (Point3D) o;

        if (_x != null ? !_x.equals(point3D._x) : point3D._x != null) return false;
        if (_y != null ? !_y.equals(point3D._y) : point3D._y != null) return false;
        return _z != null ? _z.equals(point3D._z) : point3D._z == null;
    }

    @Override
    public String toString() {
        return "(" +
                _x +
                "," + _y +
                "," + _z +
                ')';
    }
}
