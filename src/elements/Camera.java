package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;
import static renderer.Render.MAX_DEPTH_OF_ADAPTIVE;
import static renderer.Render.STARTING_DEPTH;

/**
 * Camera class representing a camera in 3d space.
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public class Camera {

    //--------------------------------------------------- FIELDS ----------------------------------------------------//

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
     * To do depth of field (True) or not (False)
     */
    public boolean DOF = false;

    /**
     * The distance from the camera to the view plane
     */
    private double _viewPlaneDistance;

    /**
     * The distance from the camera to the focal plane
     */
    private double _focalDistance;

    /**
     * The aperture radius size
     * The more large radius size the more depth of filed you get.
     */
    private double _apertureRadiusSize = 5;

    //--------------------------------------------------- SETTERS ---------------------------------------------------//

    /**
     * Set the aperture radius size.
     * The more large radius the more depth of filed you get.
     *
     * @param apertureRadiusSize The aperture radius (double)
     * @return this (Camera)
     * @throws IllegalArgumentException If the aperture radius size is not bigger then zero
     */
    public Camera setApertureRadiusSize(double apertureRadiusSize) {

        if (apertureRadiusSize <= 0) throw new IllegalArgumentException("Aperture radius must be bigger then zero");
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

    /**
     * Set the depth of field, with depth (true) or without (false)
     *
     * @param DOF boolean
     * @return this (Camera)
     */
    public Camera setDOF(boolean DOF) {
        this.DOF = DOF;

        // return this for chaining
        return this;
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
        _viewPlaneDistance = distance;

        // return this for chaining
        return this;
    }

    //--------------------------------------------------- METHODS ---------------------------------------------------//

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
     * Generate one ray from the camera to the object and go through the view plane
     *
     * @param nX number of columns (int)
     * @param nY number of rows (int)
     * @param j  column index of the point in the view plane (int)
     * @param i  row index of the point in the view plane (int)
     * @return The ray from the lens of the camera to the p(i,j) in the view plane (Ray)
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        //Image center
        Point3D pC = _p0.add(_vTo.scale(_viewPlaneDistance));

        // Ratio (pixel width & height)
        double rX = _width / nX;
        double rY = _height / nY;

        //Pixel [i,j] center
        double yI = -1 * (i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        double x = random(-rX / 2, rX / 2);
        double y = random(-rY / 2, rY / 2);

        // in the beginning pIJ is the center pixel, and if we need to move up and down or right and left
        Point3D pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(_vRight.scale(xJ + x));
        if (yI != 0) pIJ = pIJ.add(_vUp.scale(yI + y));

        Vector vIJ = pIJ.subtract(_p0);

        // If DOF true the ray go through the focal plane and need new root point
        if (DOF) return constructRayThroughPixelDOF(pIJ, vIJ);

        return new Ray(_p0, vIJ);
    }

    /**
     * Calculate the four centers of the sub-pixels
     *
     * @param nX    number of columns (int)
     * @param nY    number of rows (int)
     * @param pIJ   Center of a pixel
     * @param depth The depth of the adaptive recursion
     * @return A list with four sub-pixels centers
     */
    public List<Point3D> centerOfPixels(int nX, int nY, Point3D pIJ, int depth) {

        List<Point3D> centerOfPixels = new LinkedList<>();

        // Ratio (pixel width & height)
        double rX = _width / nX;
        double rY = _height / nY;

        double axisXLength = rX / Math.pow(2, depth);
        double axisYLength = rY / Math.pow(2, depth);

        Point3D center1 = pIJ.add(_vUp.scale(axisYLength)).add(_vRight.scale(axisXLength));
        Point3D center2 = pIJ.add(_vUp.scale(axisYLength)).add(_vRight.scale(axisXLength * -1));
        Point3D center3 = pIJ.add(_vUp.scale(axisYLength * -1)).add(_vRight.scale(axisXLength * -1));
        Point3D center4 = pIJ.add(_vUp.scale(axisYLength * -1)).add(_vRight.scale(axisXLength));

        centerOfPixels.add(center1);
        centerOfPixels.add(center2);
        centerOfPixels.add(center3);
        centerOfPixels.add(center4);

        return centerOfPixels;
    }

    /**
     * Calculate the center of a pixel
     *
     * @param nX number of columns (int)
     * @param nY number of rows (int)
     * @param j  The current column in the view plane
     * @param i  The current row in the view plane
     * @return The center of the pixel
     */
    public Point3D centerOfMainPixel(int nX, int nY, int j, int i) {
        //Image center
        Point3D pC = _p0.add(_vTo.scale(_viewPlaneDistance));

        // Ratio (pixel width & height)
        double rX = _width / nX;
        double rY = _height / nY;

        //Pixel [i,j] center
        double yI = -1 * (i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        // in the beginning pIJ is the center pixel, and if we need to move up and down or right and left
        Point3D pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(_vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(_vUp.scale(yI));

        return pIJ;
    }

    /**
     * Generate the rays from the camera to the object
     * and go through the view plane and the focal plane.
     * <p>
     * The return rays is refer to wich quarter it is.
     *
     * @param nX number of columns (int)
     * @param nY number of rows (int)
     * @param depth The depth of the recursion
     * @param signX The X sign of the quarter
     * @param signY The Y sign of the quarter
     * @param pIJ The center of the quarter
     * @return List of rays from the lens of the camera to the p(i,j) in the view plane (Ray)
     */
    public List<Ray> constructRayThroughPixelAdaptive(int nX, int nY, int depth, int signX, int signY, Point3D pIJ) {

        List<Ray> rays = new LinkedList<>();

        // Ratio (pixel width & height)
        double rX = _width / nX;
        double rY = _height / nY;

        double axisXLength = rX / Math.pow(2, depth);
        double axisYLength = rY / Math.pow(2, depth);


        // If the first time, we need the right-up corner
        if (depth == STARTING_DEPTH) {
            Point3D pIJ1 = pIJ.add(_vRight.scale(axisXLength * signX)).add(_vUp.scale(axisYLength * signY)); // Right up
            Vector vIJ1 = pIJ1.subtract(_p0);
            if (DOF) rays.add(constructRayThroughPixelDOF(pIJ1, vIJ1));
            else rays.add(new Ray(_p0, vIJ1));
        }

        // If we are in the 2nd quarter, we need the left-down corner
        if (signX < 0 && signY > 0) {
            Point3D pIJ3 = pIJ.add(_vRight.scale(axisXLength * -signX)).add(_vUp.scale(axisYLength * -signY)); // Left down
            Vector vIJ3 = pIJ3.subtract(_p0);
            if (DOF) rays.add(constructRayThroughPixelDOF(pIJ3, vIJ3));
            else rays.add(new Ray(_p0, vIJ3));
        }

        // If we are in the first quarter (or it's the first time) we need the left-down, left-up and right-down corners too
        if (signX > 0 && signY > 0) {

            Point3D pIJ2 = pIJ.add(_vRight.scale(axisXLength * -signX)).add(_vUp.scale(axisYLength * signY)); // Left up
            Vector vIJ2 = pIJ2.subtract(_p0);
            if (DOF) rays.add(constructRayThroughPixelDOF(pIJ2, vIJ2));
            else rays.add(new Ray(_p0, vIJ2));

            Point3D pIJ3 = pIJ.add(_vRight.scale(axisXLength * -signX)).add(_vUp.scale(axisYLength * -signY)); // Left down
            Vector vIJ3 = pIJ3.subtract(_p0);
            if (DOF) rays.add(constructRayThroughPixelDOF(pIJ3, vIJ3));
            else rays.add(new Ray(_p0, vIJ3));

            Point3D pIJ4 = pIJ.add(_vRight.scale(axisXLength * signX)).add(_vUp.scale(axisYLength * -signY)); // Right down
            Vector vIJ4 = pIJ4.subtract(_p0);
            if (DOF) rays.add(constructRayThroughPixelDOF(pIJ4, vIJ4));
            else rays.add(new Ray(_p0, vIJ4));
        }

        // If we are in the 3rd quarter we need the right-down corner
        if (signX < 0 && signY < 0) {
            Point3D pIJ4 = pIJ.add(_vRight.scale(axisXLength * -signX)).add(_vUp.scale(axisYLength * signY)); // Right down
            Vector vIJ4 = pIJ4.subtract(_p0);
            if (DOF) rays.add(constructRayThroughPixelDOF(pIJ4, vIJ4));
            else rays.add(new Ray(_p0, vIJ4));
        }

        // return the ray go through the pixel
        return rays;
    }


    /**
     * Find the 4 sub-pixels center of the aperture
     * @param pIJ The center of the pixel
     * @param depth The depth of the recursion
     * @return List of the 4 sub-pixel centers
     */
    public List<Point3D> centerOfPixelsDOF(Point3D pIJ, int depth) {

        List<Point3D> centerOfPixels = new LinkedList<>();

        // Ratio (pixel width & height)
        double axisLength = _apertureRadiusSize / Math.pow(2, depth);


        Point3D center1 = new Point3D(pIJ.getX()+axisLength, pIJ.getY()+axisLength, pIJ.getZ());
        Point3D center2 = new Point3D(pIJ.getX()+axisLength*-1, pIJ.getY()+axisLength, pIJ.getZ());
        Point3D center3 = new Point3D(pIJ.getX()+axisLength*-1, pIJ.getY()+axisLength*-1, pIJ.getZ());
        Point3D center4 = new Point3D(pIJ.getX()+axisLength, pIJ.getY()+axisLength*-1, pIJ.getZ());

        centerOfPixels.add(center1);
        centerOfPixels.add(center2);
        centerOfPixels.add(center3);
        centerOfPixels.add(center4);

        return centerOfPixels;
    }

    /**
     * Find the point in the focal plane
     * @param pointOnVP The point of the view plane
     * @return The 
     */
   public Point3D findFocalPoint(Point3D pointOnVP){
       // The distance from the view plane to the focal plane
       double distance = _focalDistance - _viewPlaneDistance;

       Vector vIJ = pointOnVP.subtract(_p0);
       Point3D focalPoint = pointOnVP.add(vIJ.normalize().scale(distance));

       return focalPoint;
   }


    public List<Ray> constractRayAdaptivGridDOF(Point3D focalPoint, int depth, int signX, int signY, Point3D pIJ){



        List<Ray> rays = new LinkedList<>();

        // Ratio (aperture width & height)
        double axisLength = _apertureRadiusSize / Math.pow(2, depth);


        // If the first time, we need the right-up corner
        if (depth == STARTING_DEPTH) {
            Point3D pIJ1 = new Point3D( pIJ.getX()+axisLength * signX, pIJ.getY()+axisLength * signY,pIJ.getZ() ); // Right up
            Vector vIJ1 = focalPoint.subtract(pIJ1);
            rays.add(new Ray(pIJ1, vIJ1));
        }

        // If we are in the 2nd quarter, we need the left-down corner
        if (signX < 0 && signY > 0) {
            Point3D pIJ3 = new Point3D( pIJ.getX()+axisLength * signX, pIJ.getY()+axisLength * -signY,pIJ.getZ() ); // Left down
            Vector vIJ3 = focalPoint.subtract(pIJ3);
            rays.add(new Ray(pIJ3, vIJ3));
        }

        // If we are in the first quarter (or it's the first time) we need the left-down, left-up and right-down corners too
        if (signX > 0 && signY > 0) {

            Point3D pIJ2 = new Point3D( pIJ.getX()+axisLength * -signX, pIJ.getY()+axisLength * signY,pIJ.getZ() ); // Left up
            Vector vIJ2 = focalPoint.subtract(pIJ2);
            rays.add(new Ray(pIJ2, vIJ2));

            Point3D pIJ3 = new Point3D( pIJ.getX()+axisLength * -signX, pIJ.getY()+axisLength * -signY,pIJ.getZ() ); // Left down
            Vector vIJ3 = focalPoint.subtract(pIJ3);
            rays.add(new Ray(pIJ3, vIJ3));

            Point3D pIJ4 = new Point3D( pIJ.getX()+axisLength * signX, pIJ.getY()+axisLength * -signY,pIJ.getZ() ); // Right down
            Vector vIJ4 = focalPoint.subtract(pIJ4);
            rays.add(new Ray(pIJ4, vIJ4));
        }

        // If we are in the 3rd quarter we need the right-down corner
        if (signX < 0 && signY < 0) {
            Point3D pIJ4 = new Point3D( pIJ.getX()+axisLength * signX, pIJ.getY()+axisLength * -signY,pIJ.getZ() ); // Right down
            Vector vIJ4 = focalPoint.subtract(pIJ4);
            rays.add(new Ray(pIJ4, vIJ4));
        }

        // return the ray go through the pixel
        return rays;

    }
    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------

    /**
     * Generate one ray from the camera's aperture (rematch the starting point to some point on the aperture)
     * to the object and go through the focal plane
     *
     * @param pIJ The center of the current pixel in the view plane
     * @param vIJ The direction vector from the camera, through the pixel, to the scene
     * @return The new ray from the aperture to the focal plane (in particular, focal point) (Ray)
     */
    public Ray constructRayThroughPixelDOF(Point3D pIJ, Vector vIJ) {

        // The distance from the view plane to the focal plane
        double distance = _focalDistance - _viewPlaneDistance;

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

    //--------------------------------------------------- GETTERS ---------------------------------------------------//

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

    public double getViewPlaneDistance() {
        return _viewPlaneDistance;
    }
}