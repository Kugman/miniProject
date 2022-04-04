package primitives;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * @author Efrat Kugman
 * /**
 *  * "Half-straight - all the points on the line that are on one side of the point given on the line called the beginning / beginning / beginning of the fund):
 */
public class Ray {

    private final Point startPoint;
    private final Vector directionVector;

    public Ray(Point startPoint, Vector directionVector) {
        this.startPoint = startPoint;
        this.directionVector = directionVector.normalize();
    }

    public Point getPoint(double t){
        return startPoint.add(directionVector.scale(t));
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Vector getDirectionVector(){
        return directionVector;
    }

    public String toString(){
        return "Ray: {" +
                startPoint.toString()       +
                directionVector.toString()  +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return startPoint.equals(ray.startPoint) && directionVector.equals(ray.directionVector);
    }

    public Point findClosestPoint(List<Point> listOfPoints){

        if(listOfPoints==null) return null;

        Point result = null;

        //double closestDistane=listOfPoints.get(0).distance(_p0);//זה הכי יפה בעיננו//
        double closestDistane = Double.MAX_VALUE;

        for (Point p :listOfPoints){
            double tmp = p.distance(startPoint);
            if(tmp <= closestDistane){
                closestDistane = tmp;
                result = p;
            }
        }
        return result;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> listOfPoints){
        if(listOfPoints==null) return null;

        GeoPoint result = null;

        double closestDistane = Double.MAX_VALUE;

        for (GeoPoint gp :listOfPoints){
            double tmp = gp.point.distance(startPoint);
            if(tmp <= closestDistane){
                closestDistane = tmp;
                result = gp;
            }
        }
        return result;

    }

}
