package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * Camera class representing a camera in 3d space.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Camera {

    /**
     * The center of the camera lens
     */
    private final Point3D _p0;

    /**
     * The camera's direction vectors
     */
    final private Vector _vUp, _vTo, _vRight;

    /**
     * The view plane width
     */
    private double _width;

    /**
     * The view plane height
     */
    private double _height;

    /**
     *  To do depth of field (True) or not (False)
     */
    private boolean DOF = true;

    /**
     * The distance from the camera to the view plane
     */
    private double _distance;

    /**
     * The distance from the camera to the focal plane
     */
    private double _focalDistance;

    /**
     * The aperture radius size
     * The more large radius size the more depth of filed you get.
     */
    private double _apertureRadiusSize;


    /**
     * Set the aperture radius size.
     * The more large radius the more depth of filed you get.
     *
     * @param apertureRadiusSize The aperture radius (double)
     * @return this (Camera)
     */
    public Camera setApertureRadiusSize(double apertureRadiusSize) {
        _apertureRadiusSize = apertureRadiusSize;

        // return this for chaining
        return this;
    }

    /**
     * Set the focal distance - the distance from the aperture to the focal plane
     *
     * @param focalDistance The focal distance
     * @return this (Camera)
     */
    public Camera setFocalDistance(double focalDistance) {
        _focalDistance = focalDistance;

        // return this for chaining
        return this;
    }

    public Camera setDOF(boolean DOF) {
        this.DOF = DOF;

        // return this for chaining
        return this;
    }

    /**
     * c-tor receive 1 starting point and 2 orthogonal vectors (and normalized them).
     *
     * @param p0  starting point (Point3D)
     * @param vUp 'up' vector (Vector)
     * @param vTo 'to' vector (Vector)
     */
    public Camera(Point3D p0, Vector vUp, Vector vTo) {

        // Check if the direction vectors are orthogonal
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The received vectors are not orthogonal");

        // Initialize the values center point and up and to direction vectors
        _p0 = p0;
        _vUp = vUp.normalized();
        _vTo = vTo.normalized();

        // Calculate the right direction vector
        _vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Setting the size of the View Plane
     *
     * @param width  width of the View Plane (double)
     * @param height height of the View Plane (double)
     * @return return the camera itself (Camera)
     */
    public Camera setViewPlaneSize(double width, double height) {

        _width = width;
        _height = height;

        // return this for chaining
        return this;
    }

    /**
     * Set the distance between the View Plane and the Camera
     *
     * @param distance the distance (double)
     * @return return the camera itself (Camera)
     */
    public Camera setViewPlaneDistance(double distance) {
        _distance = distance;

        // return this for chaining
        return this;
    }

    /**
     * Generate the ray from the camera's aperture to the object
     * and go through the view plane and the focal plane
     *
     * @param nX number of columns (int)
     * @param nY number of rows (int)
     * @param j  column index of the point in the view plane (int)
     * @param i  row index of the point in the view plane (int)
     * @return the ray from the lens of the camera to the p(i,j) in the view plane (Ray)
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        //Image center
        Point3D pC = _p0.add(_vTo.scale(_distance));

        // Ratio (pixel width & height)
        double rX = _width / nX;
        double rY = _height / nY;

        //Pixel [i,j] center
        double xJ = (j - (nX - 1) / 2d) * rX;
        double yI = -1 * (i - (nY - 1) / 2d) * rY;

        // In the beginning pIJ is the center pixel, and if we need to move up and down or right and left we'll add it
        Point3D pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(_vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(_vUp.scale(yI));

        // The vector go through the current pixel
        Vector vIJ = pIJ.subtract(_p0);

        if(DOF) return constructRayThroughPixelDOF(pIJ, vIJ);

        // return the ray go through the pixel
        return new Ray(_p0, vIJ);
    }

    public Ray constructRayThroughPixelDOF(Point3D pIJ, Vector vIJ) {

        // The distance from the view plane to the focal plane
        double distance = _focalDistance - _distance;

        // Create the focal point
        // Take the centered point on the view plane and go with the same direction, from the camera
        // to the view plane, to the focal plane.
        // Like the line formula (x,y,z) + t (a,b,c)
        // (x,y,z) - The centered point on the view plane
        //    t    - The distance from the view plane to the focal plane
        // (a,b,c) - The direction vector from camera to view plane
        Point3D focalPoint = pIJ.add(vIJ.normalize().scale(distance));

        // Generate random point on the aperture circle
        Point3D pointOnAperture;
        do {
            double randomX = random(-1 * _apertureRadiusSize, _apertureRadiusSize);
            double randomY = random(-1 * _apertureRadiusSize, _apertureRadiusSize);
            pointOnAperture = new Point3D(_p0.getX() + randomX, _p0.getY() + randomY, _p0.getZ());
        }
        while (pointOnAperture.distance(_p0) > _apertureRadiusSize);

        return new Ray(pointOnAperture, focalPoint.subtract(pointOnAperture));

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
