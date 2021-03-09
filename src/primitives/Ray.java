package primitives;

public class Ray {

    Point3D _p0;
    Vector _dir;


    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalize();
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _dir=" + _dir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (_p0 != null ? !_p0.equals(ray._p0) : ray._p0 != null) return false;
        return _dir != null ? _dir.equals(ray._dir) : ray._dir == null;
    }

}
