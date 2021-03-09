package primitives;

import java.util.Vector;

public class Point3D {

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;


    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = new Coordinate(x.coord);
        _y = new Coordinate(y.coord);
        _z = new Coordinate(z.coord);
    }
/*
    public Coordinate getX() {
        return _x;
    }

    public Coordinate getY() {
        return _y;
    }

    public Coordinate getZ() {
        return _z;
    }

    public Point3D add(Vector vector){

        return;
    }

    public Vector subtract(Point3D point){
        return;
    }

    public double distanceSquared (Point3D point){
        return;
    }
*/

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
