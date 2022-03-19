package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Tube implements Geometry {

    protected Ray ray;
    protected double radius;

    //----------------Constructor-----------------------//
    public Tube(double radius, Ray ray) {
        this.ray = ray;
        this.radius = radius;
    }

    //----------------Getters---------------------------//
    public Ray getRay() {
        return ray;
    }

    public double getRadius() {
        return radius;
    }

    //----------------Interface implements--------------//
    @Override
    public Vector getNormal(Point point) {
        Vector vector1 = ray.getDirectionVector();
        Vector vector2 = point.subtract(ray.getStartPoint());
        double tmp = vector1.dotProduct(vector2);
        if(Util.isZero(tmp)) return vector2.normalize();
        Point point1 = vector2.add(vector1.scale(tmp));
        Vector vector3 = point.subtract(point1);
        return vector3.normalize();
    }

    @Override
    public String toString(){
        return "Tube: {"			+
                ray.toString() 	+ ", " 	+
                "Radius: " 		+ radius+
                "}";
    }
}
