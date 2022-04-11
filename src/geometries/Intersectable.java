package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import java.util.List;


public abstract class Intersectable {

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public String toString(){
            return "GeoPoint{" +
                    this.geometry.toString()		+ ", " +
                    this.point.toString()	+
                    "}";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Point)) return false;
            GeoPoint other = (GeoPoint) obj;
            return (this.point.equals(other.point) && this.geometry.equals(other.geometry));
        }
    }

    /**
     * @param ray for calculating the intersaction with the geometry
     * @return list of  points of the  intersections  of the ray with the geometry.
     * there is default implementation to the func
     */
    public List<Point> findIntersections(Ray ray){
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp->gp.point).toList();
    }


//    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

}
