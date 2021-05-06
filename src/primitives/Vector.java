package primitives;

import static primitives.Point3D.ZERO;

/**
 * Vector class representing a vector.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Vector {

    Point3D _head;

    /**
     * Vector c-tor receiving 3 double values
     *
     * @param x double value
     * @param y double value
     * @param z double value
     */
    public Vector(double x, double y, double z) {
        Point3D newPoint = new Point3D(x, y, z);

        if (newPoint.equals(ZERO)) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");
        _head = new Point3D(x, y, z);
    }

    /**
     * Vector c-tor receiving Point3D
     *
     * @param head Point3D value
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");
        _head = head;
    }

    /**
     * add method return vector witch is the sum of two vectors
     *
     * @param vector Vector value
     * @return Vector value
     */
    public Vector add(Vector vector) {
        Vector newVector = new Vector(_head._x.coord + vector._head._x.coord,
                _head._y.coord + vector._head._y.coord,
                _head._z.coord + vector._head._z.coord);

        return newVector;
    }

    /**
     * subtract method return new Vector witch his coordinates is the subtract of the
     * receiving Vector coordinates from 'this' Vector coordinates.
     *
     * @param vector Vector value
     * @return Vector value
     */
    public Vector subtract(Vector vector) {
        Vector newVector = new Vector(_head._x.coord - vector._head._x.coord,
                _head._y.coord - vector._head._y.coord,
                _head._z.coord - vector._head._z.coord);

        return newVector;
    }

    /**
     * scale return multiple vector by a receiving scalar
     *
     * @param scalar double value
     * @return Vector value
     */
    public Vector scale(double scalar) {

        return new Vector(_head._x.coord * scalar, _head._y.coord * scalar, _head._z.coord * scalar);
    }


    /**
     * dotProduct return sum of multiple between the coordinates of the two vectors
     *
     * @param vector Vector value
     * @return double value
     */
    public double dotProduct(Vector vector) {
        return (_head._x.coord * vector._head._x.coord +
                _head._y.coord * vector._head._y.coord +
                _head._z.coord * vector._head._z.coord);
    }

    /**
     * crossProduct calculate cartesian product and return the a new Vector
     *
     * @param vector Vector value
     * @return Vector value
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                _head._y.coord * vector._head._z.coord - _head._z.coord * vector._head._y.coord,
                _head._z.coord * vector._head._x.coord - _head._x.coord * vector._head._z.coord,
                _head._x.coord * vector._head._y.coord - _head._y.coord * vector._head._x.coord
        );
    }

    /**
     * lengthSquared method calculate the length of vector (power 2)
     *
     * @return double value
     */
    public double lengthSquared() {
        return _head._x.coord * _head._x.coord
                + _head._y.coord * _head._y.coord
                + _head._z.coord * _head._z.coord;
    }


    /**
     * length method calculate the length of vector
     *
     * @return double value
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * normalize method normalize the vector
     *
     * @return Vector value
     */
    public Vector normalize() {
        double length = this.length();

        Point3D newPoint = new Point3D(
                _head._x.coord / length,
                _head._y.coord / length,
                _head._z.coord / length
        );

        _head = newPoint;

        return this;
    }

    /**
     * normalized method return a new normalize vector
     *
     * @return Vector value
     */
    public Vector normalized() {
        Vector newVector = new Vector(this._head);
        return newVector.normalize();
    }

    /**
     * getHead return the head point of the vector
     *
     * @return Point3D value
     */
    public Point3D getHead() {
        return _head;
    }

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
