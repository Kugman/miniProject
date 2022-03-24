package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;


public interface Intersectable {

    /**
     * @param ray for calculating the intersaction with the geometry
     * @return list of  points of the  intersections  of the ray with the geometry.
     * there is default implementation to the func
     */
    List<Point> findIntersections(Ray ray);

    
}
