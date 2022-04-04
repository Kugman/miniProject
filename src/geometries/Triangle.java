package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

public class Triangle extends Polygon {

	//---------------- Constructors ------------------ //
	
	public Triangle(Point point1, Point point2, Point point3){
		super(point1, point2, point3);
	}

	// ----------------- Getters --------------------- //
	public Point getPoint1() { return vertices.get(0); }
	public Point getPoint2() { return vertices.get(1); }
	public Point getPoint3() { return vertices.get(2); }

	//----------------Interface implements--------------//

	@Override
	public String toString(){
		return "Triangle: {"			+
				vertices.toString() +
				"}";
	}

	@Override
	public List<Point> findIntersections(Ray ray) {

		List<Point> list1 = plane.findIntersections(ray);

		//IF THE PLANE HAS NO INTERSACTION POINTS-RETURN NULL
        if (list1 == null) return null;

		Vector v1 = vertices.get(0).subtract(ray.getStartPoint());
        Vector v2 = vertices.get(1).subtract(ray.getStartPoint());
        Vector v3 = vertices.get(2).subtract(ray.getStartPoint());

        Vector N1 = v1.crossProduct(v2).normalize();
        Vector N2 = v2.crossProduct(v3).normalize();
        Vector N3 = v3.crossProduct(v1).normalize();

        double t1 = ray.getDirectionVector().dotProduct(N1);
        if(isZero(t1)) return null;

		double t2 = ray.getDirectionVector().dotProduct(N2);
        if(isZero(t2)) return null;

		double t3 = ray.getDirectionVector().dotProduct(N3);
        if(isZero(t3)) return null;

		//if they all have the same sign-there is an intersaction
        if(alignZero(t1) >0 && alignZero(t2)> 0 && alignZero(t3) > 0 ||
				alignZero(t1)<0 && alignZero(t2) < 0 && alignZero(t3) < 0)
            return plane.findIntersections(ray);

        return null;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
		List<Point> list1 = this.plane.findIntersections(ray);

		//IF THE PLANE HAS NO INTERSACTION POINTS-RETURN NULL
		if (list1 == null) return null;

		Vector v1 = vertices.get(0).subtract(ray.getStartPoint());
		Vector v2 = vertices.get(1).subtract(ray.getStartPoint());
		Vector v3 = vertices.get(2).subtract(ray.getStartPoint());

		Vector N1 = v1.crossProduct(v2).normalize();
		Vector N2 = v2.crossProduct(v3).normalize();
		Vector N3 = v3.crossProduct(v1).normalize();

		double t1 = ray.getDirectionVector().dotProduct(N1);
		if(isZero(t1)) return null;

		double t2 = ray.getDirectionVector().dotProduct(N2);
		if(isZero(t2)) return null;

		double t3 = ray.getDirectionVector().dotProduct(N3);
		if(isZero(t3)) return null;

		//if they all have the same sign-there is an intersaction
		if(alignZero(t1) >0 && alignZero(t2)> 0 && alignZero(t3) > 0 ||
				alignZero(t1)<0 && alignZero(t2) < 0 && alignZero(t3) < 0)
			return List.of(new GeoPoint(this, this.plane.findGeoIntersectionsHelper(ray).get(0).point));

		return null;

	}

}
