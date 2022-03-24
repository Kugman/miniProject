package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

public class Sphere implements Geometry {

	private Point center;
	private double radius;

	//----------------Constructor-----------------------//
	public Sphere(Point point, double radius) {
		this.center = point;
		this.radius = radius;
	}

	//----------------Getters---------------------------//
	public Point getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	//----------------Interface implements--------------//
	@Override
	public Vector getNormal(Point point) {
		if (point.equals(center))
			throw new IllegalArgumentException("CANNOT CREATE VECTOR 0");
		return point.subtract(center).normalize();
	}

	@Override
	public String toString(){
		return "Sphere: {"			+
				center.toString() 	+ ", " 	+
				"Radius: " 			+ radius+
				"}";
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		//calculating variables for the final formula
        Point P0 = ray.getStartPoint();
        Vector V = ray.getDirectionVector();
        Point O = center;

		if(P0.equals(center)) return  List.of(center.add(V.scale(radius)));

		Vector U = O.subtract(P0);
        double tm = V.dotProduct(U);
        double d =alignZero(Math.sqrt(alignZero(U.lengthSquared() - tm * tm)));

		//there is no intersection
        if(d>=radius) return null;

		double th =alignZero(Math.sqrt(alignZero(radius*radius-d*d)));
        double t1=alignZero(tm-th);
        double t2=alignZero(tm+th);

		// if P is on the surface---
        if(isZero(th)) return null;

        //in case of 2 intersection points
        if(t1 > 0 && t2 > 0) //the first point and the second point
            return List.of(ray.getPoint(t1),
					ray.getPoint(t2));

        //in case of 1 intersection points
        if(t1 > 0) return List.of(ray.getPoint(t1));

		//in case of 1 intersection points
        if(t2 > 0) return List.of(ray.getPoint(t2));

        return null;

	}
}
