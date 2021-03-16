package primitives;

import java.lang.Math;

public class Point3D {

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    static Point3D ZERO = new Point3D(0.0d, 0.0d, 0.0d);


    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = new Coordinate(x.coord);
        _y = new Coordinate(y.coord);
        _z = new Coordinate(z.coord);
    }

    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }


    public Vector subtract(Point3D point) {

        Point3D newPoint = new Point3D(point._x.coord - _x.coord,
                point._y.coord - _y.coord,
                point._z.coord - _z.coord);

        return new Vector(newPoint);
    }


    public Point3D add(Vector vector) {

        Point3D newPoint = new Point3D(_x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord);

        return newPoint;
    }


    public double distanceSquared(Point3D point) {

        double distanceX = (_x.coord - point._x.coord),
                distanceY = (_y.coord - point._y.coord),
                distanceZ = (_z.coord - point._z.coord);

        double distance = distanceX * distanceX
                + distanceY * distanceY
                + distanceZ * distanceZ;

        return distance;
    }

    public double distance (Point3D point){

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
