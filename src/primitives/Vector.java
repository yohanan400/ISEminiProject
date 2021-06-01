package primitives;

import static primitives.Point3D.ZERO;

/**
 * Vector class representing a vector in the space (3D).
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Vector {

    /**
     * The head of the vector
     */
    Point3D _head;

    /**
     * c-tor, initiate the coordinate of the vector's head with the receiving values
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public Vector(double x, double y, double z) {
        //Creating the new point with the receiving coordinates
        Point3D newPoint = new Point3D(x, y, z);

        // Check if the coordinates create the ZERO vector, because in our program its can't be.
        if (newPoint.equals(ZERO)) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");

        // Update the head to be the created point
        _head = new Point3D(x, y, z);
    }

    /**
     * c-tor, initiate the coordinate of the vector's head with the receiving point's coordinates
     *
     * @param head The point to initiate the coordinate with
     */
    public Vector(Point3D head) {
        // Check if the coordinates create the ZERO vector, because in our program its can't be.
        if (head.equals(ZERO)) throw new IllegalArgumentException("The vector cannot be the 'zero vector' ");
        _head = head;
    }

    /**
     * Adding two vectors, 'this' and the received one
     *
     * @param vector The vector to add to 'this'
     * @return New vector after adding (Vector)
     */
    public Vector add(Vector vector) {
        Vector newVector = new Vector(getHead().getX() + vector.getHead().getX(),
                getHead().getY() + vector.getHead().getY(),
                getHead().getZ() + vector.getHead().getZ());

        return newVector;
    }

    /**
     * Subtract the receiving Vector coordinates from 'this' Vector coordinates.
     *
     * @param vector The vector to subtract from 'this'
     * @return New vector after subtracting (Vector)
     */
    public Vector subtract(Vector vector) {
        Vector newVector = new Vector(getHead().getX() - vector.getHead().getX(),
                getHead().getY() - vector.getHead().getY(),
                getHead().getZ() - vector.getHead().getZ());

        return newVector;
    }

    /**
     * Scaling vector by a receiving scalar
     *
     * @param scalar The scalar scaling value
     * @return New vector after scaling (Vector)
     */
    public Vector scale(double scalar) {

        return new Vector(getHead().getX() * scalar, getHead().getY() * scalar, getHead().getZ() * scalar);
    }


    /**
     * Calculating an dot product between two vectors
     * by summarize the scaling results between the coordinates
     *
     * @param vector The vector to product with
     * @return The dot product result (double)
     */
    public double dotProduct(Vector vector) {
        return (getHead().getX() * vector.getHead().getX() +
                getHead().getY() * vector.getHead().getY() +
                getHead().getZ() * vector.getHead().getZ());
    }

    /**
     * Calculate cross product between two vectors
     *
     * @param vector The vector to product with
     * @return The new Vector with the coordinates after scaling (Vector)
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                getHead().getY() * vector.getHead().getZ() - getHead().getZ() * vector.getHead().getY(),
                getHead().getZ() * vector.getHead().getX() - getHead().getX() * vector.getHead().getZ(),
                getHead().getX() * vector.getHead().getY() - getHead().getY() * vector.getHead().getX()
        );
    }

    /**
     * Calculate the length of the vector (powered by 2)
     *
     * @return The squared length of the vector (double)
     */
    public double lengthSquared() {
        return getHead().getX() * getHead().getX()
                + getHead().getY() * getHead().getY()
                + getHead().getZ() * getHead().getZ();
    }


    /**
     * Calculate the length of the vector
     *
     * @return The length of the vector (double)
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalize the vector
     *
     * @return The normalized vector (Vector)
     */
    public Vector normalize() {

        //Calculate the length of the vector to reduce the coordinate with
        double length = this.length();

        //Normalize the vector
        Point3D newPoint = new Point3D(
                getHead().getX() / length,
                getHead().getY() / length,
                getHead().getZ() / length
        );

        _head = newPoint;

        return this;
    }

    /**
     * Return a new normalize vector
     *
     * @return The new normalized vector
     */
    public Vector normalized() {

        // Create new vector with the same values
        Vector newVector = new Vector(getHead());

        // normalized and return the new vector
        return newVector.normalize();
    }

    /**
     * Return the head point of the vector
     *
     * @return The head point of the vector (Point3D)
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
