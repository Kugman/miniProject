package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable{

    List<Intersectable> geometries = null;

    public Geometries(){
        geometries = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries){
        this.geometries = new LinkedList<>();
        add(geometries);
    }

    public void add(Intersectable... geometries){
        Collections.addAll(this.geometries, geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable item : this.geometries) {
            // Activates the function for each shape according to its  implementation
            List<Point> itemPoints = item.findIntersections(ray);
            //if there is intersection
            if (itemPoints != null) {
                if (result == null) result = new LinkedList<>();
                result.addAll(itemPoints);
            }
        }
        return result;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        List<GeoPoint> result = null;
        for (Intersectable item : this.geometries) {
            // Activates the function for each shape according to its  implementation
            List<GeoPoint> itemPoints = item.findGeoIntersectionsHelper(ray, maxDistance);
            //if there is intersection
            if (itemPoints != null) {
                if (result == null) result = new LinkedList<>();
                result.addAll(itemPoints);
            }
        }
        return result;
    }
}
