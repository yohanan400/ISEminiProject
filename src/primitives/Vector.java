package primitives;

import static primitives.Point3D.ZERO;

public class Vector {

    Point3D _head;


    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D newPoint = new Point3D(x, y, z);

        if (newPoint.equals(ZERO)) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");
        _head = new Point3D(x, y, z);
    }

    public Vector(double x, double y, double z) {
        Point3D newPoint = new Point3D(x, y, z);

        if (newPoint.equals(ZERO)) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");
        _head = new Point3D(x, y, z);
    }

    public Vector(Point3D head) {
        _head = head;
    }


    public Vector add(Vector vector) {
        Vector newVector = new Vector(_head._x.coord + vector._head._x.coord,
                _head._y.coord + vector._head._y.coord,
                _head._z.coord + vector._head._z.coord);

        return newVector;
    }

    public Vector subtract(Vector vector) {
        Vector newVector = new Vector(_head._x.coord - vector._head._x.coord,
                _head._y.coord - vector._head._y.coord,
                _head._z.coord - vector._head._z.coord);

        return newVector;
    }



    /*
    public Vector scale (double s){

        return;
    }

    public Vector crossProduct (Vector vector){
        return;
    }

    public double dotProduct (Vector vector){
        return;
    }

    public double lengthSquared (){
        return;
    }

    public double length (){
        return;
    }

    public Vector normalize (){
        return;
    }

    public Vector normalized (){
        return;


    public Point3D getHead() {
        return _head;
    }
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return _head != null ? _head.equals(vector._head) : vector._head == null;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
