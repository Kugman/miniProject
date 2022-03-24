package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    List<Intersectable> intersectables = null;

    public Geometries(){
        intersectables = new LinkedList<>();
    }

    public Geometries(Intersectable...geometries){
        this.intersectables = new LinkedList<>();
        add(geometries);
    }

    public void add(Intersectable...geometries){
        for (Intersectable item : intersectables)
            this.intersectables.add(item);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable item : this.intersectables) {
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
}
