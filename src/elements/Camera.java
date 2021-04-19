package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Camera class representing a camera in 3d space.
 */
public class Camera {

    private Point3D _p0;
    private Vector _vUp, _vTo, _vRight;
    private double _width, _height, _distance;

    /**
     * c-tor receive 1 starring point and 2 orthogonals vectors.
     *
     * @param p0 starting point (Point3D)
     * @param vUp 'up' vector (Vector)
     * @param vTo 'to' vector (Vector)
     */
    public Camera(Point3D p0, Vector vUp, Vector vTo) {

        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The received vectors are not orthogonal");

        _p0 = new Point3D(p0.getX(), p0.getY(), p0.getZ());
        _vUp = new Vector(vUp.getHead()).normalize();
        _vTo = new Vector(vTo.getHead()).normalize();

        _vRight = new Vector(vTo.crossProduct(vUp).getHead()).normalize();
    }

    /**
     * setting the size of the View Plane
     * @param width width of the View Plane (double)
     * @param height height of the View Plane (double)
     * @return return the camera itself (Camera)
     */
    public Camera setViewPlaneSize(double width, double height) {

        _width = width;
        _height = height;

        return this;
    }

    /**
     * set the distance between the camera and the View Plane and the Camera
     * @param distance the distance (double)
     * @return return the camera itself (Camera)
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }


    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        return null;
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDistance() {
        return _distance;
    }
}
