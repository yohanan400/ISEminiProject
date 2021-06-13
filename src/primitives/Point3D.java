package primitives;

/**
 * class Point3D representing a point in 3 domination
 *
 * @author Aviel Buta and Yakir Yohanan
 */

public class Point3D {

    /**
     * The x coordinate
     */
    final Coordinate _x;

    /**
     * The y coordinate
     */
    final Coordinate _y;

    /**
     * The z coordinate
     */
    final Coordinate _z;

    /**
     * The zero point for shortcut
     */
    public static Point3D ZERO = new Point3D(0.0d, 0.0d, 0.0d);

    /**
     * c-tor, initiate the coordinates of the point with the receiving values
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    /**
     * Get the x coordinate
     *
     * @return The x coordinate (double)
     */
    public double getX() {
        return _x.coord;
    }

    /**
     * Get the y coordinate
     *
     * @return The y coordinate (double)
     */
    public double getY() {
        return _y.coord;
    }

    /**
     * Get the z coordinate
     *
     * @return The z coordinate (double)
     */
    public double getZ() {
        return _z.coord;
    }

    /**
     * Subtract method return new Vector wich the coordinates are the subtract
     * of the receiving point's coordinates from 'this' point's coordinates.
     *
     * @param point The point to subtract from 'this' point
     * @return The received vector from the subtraction (Vector)
     */
    public Vector subtract(Point3D point) {

        Point3D newPoint = new Point3D(getX() - point.getX(),
                getY() - point.getY(),
                getZ() - point.getZ());

        return new Vector(newPoint);
    }


    /**
     * Add method return new point (Point3D) wich his coordinates is the sum of the
     * receiving vector's coordinates and 'this' point's coordinates.
     *
     * @param vector The vector to add to 'this' point
     * @return New point after adding the vector coordinates values (Point3D)
     */
    public Point3D add(Vector vector) {

        Point3D newPoint = new Point3D(getX() + vector._head.getX(),
                getY() + vector._head.getY(),
                getZ() + vector._head.getZ());

        return newPoint;
    }

    /**
     * Calculate the distance between 2 points by power of 2.
     *
     * @param point The point to measure the distance from
     * @return the squared distance (double)
     */
    public double distanceSquared(Point3D point) {

        // Calculate the difference between the points
        double distanceX = (getX() - point.getX()),
                distanceY = (getY() - point.getY()),
                distanceZ = (getZ() - point.getZ());

        // Calculate the squared distance
        double distanceSquared = distanceX * distanceX
                + distanceY * distanceY
                + distanceZ * distanceZ;

        return distanceSquared;
    }

    /**
     * Calculate the distance between 2 points.
     *
     * @param point The point to measure the distance from
     * @return The distance (double)
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
