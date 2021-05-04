package geometries;

import primitives.Point3D;
import primitives.Ray;


import java.util.LinkedList;
import java.util.List;

/**
 * Intersectable interface, to calculate all the intersections on the geometries shapes
 */
public interface Intersectable {

    /**
     * calculate the intersactions on the geometry shapes
     * @param ray light ray
     * @return List of all the intersections between the shape and the ray (List type)
     */
    List<Point3D> findIntersections(Ray ray);

    /**
     * find geometries' intersections
     * @param ray The light ray
     * @return List of intersections points (List)
     */
    public List<GeoPoint> findGeoIntersections(Ray ray);

    /**
     * The class represent geometries points. (PDS)
     */
    public static class GeoPoint {
        public Geometry _geometry;
        public Point3D _point;

        public GeoPoint(Geometry geometry, Point3D point) {
            this._geometry = geometry;
            this._point = point;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            if (_geometry != null ? !_geometry.equals(geoPoint._geometry) : geoPoint._geometry != null) return false;
            return _point != null ? _point.equals(geoPoint._point) : geoPoint._point == null;
        }

    }


}
