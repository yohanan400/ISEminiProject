package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Intersectable interface, to calculate all the intersections on geometries shapes
 *
 * @author Aviel Buta and Yakir Yohanan
 */
public interface Intersectable {

    /**
     * Calculate the intersections on geometry shapes with the received ray
     *
     * @param ray Light ray
     * @return List of all the intersections between the shape and the ray (List type)
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray); // Collect all the intersection points
        return geoList == null ? null // if there is no have any intersection point return null
                : geoList.stream().map(gp -> gp._point).collect(Collectors.toList()); // else return the intersection points
    }

    /**
     * Find geometries intersections
     *
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

            /**
             * c-tor initialize the fields to the receive values
             * @param geometry The geometry
             * @param point The point on the geometry
             */
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
