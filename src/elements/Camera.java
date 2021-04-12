package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {

    private Point3D _p0;
    private Vector _vUp, _vTo, _vRight;
    private double _width, _height, _distance;

    public Camera(Point3D p0, Vector vUp, Vector vTo) {

        _p0 = new Point3D(p0.getX(), p0.getY(), p0.getZ());
        _vUp = new Vector(vUp.getHead()).normalize();
        _vTo = new Vector(vTo.getHead()).normalize();

        _vRight = new Vector(vTo.crossProduct(vUp).getHead()).normalize();
    }

    public Camera setViewPlaneSize(double width, double height) {

        _width = width;
        _height = height;

        return this;
    }

    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
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
