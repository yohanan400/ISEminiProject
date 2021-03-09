package primitives;

public class Vector {

    Point3D _head;

    public Vector(Point3D head) {
        if (head._x.coord ==0.0d && head._y.coord == 0.0d && head._z.coord ==0.0d ) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");
        _head = head;
    }

    public Point3D getHead() {
        return _head;
    }

    /*
    public Vector add (Vector vector){

        return;
    }

    public Vector subtract (Vector vector){
        return;
    }

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
