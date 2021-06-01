package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Camera class representing a camera in 3d space.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Camera {

    private Point3D _p0; // The center of the camera lens
    final private Vector _vUp, _vTo, _vRight; // The direction vectors
    private double _width, _height, _distance; // The grid values. (distance is the distance between the grid and the camera)

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
        _p0 =p0;
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
    public Camera setDistance(double distance) {
        _distance = distance;

        // return this for chaining
        return this;
    }


    /**
     * find the ray from the lens of the camera to the p(i,j) in the view plane
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
        double yI = -1 * (i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        // in the beginning pIJ is the center pixel, and if we need to move up and down or right and left we'll add it
        Point3D pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(_vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(_vUp.scale(yI));

        Vector vIJ = pIJ.subtract(_p0);

        // return the ray go through the pixel
        return new Ray(_p0, vIJ);


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
