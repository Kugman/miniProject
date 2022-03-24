package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Plane Class
 *
 * @author Efrat Kugman
 */

public class Plane implements Geometry {

	private Vector normal;
	private Point q0;
	
	//--------------------Constructors--------------- //
	
	public Plane(Point point1, Point point2, Point point3){
		this.q0 = point1;
		Vector v1 = point2.subtract(point1);
		Vector v2 = point3.subtract(point1);
		this.normal = v1.crossProduct(v2).normalize();
	}
	
	public Plane (Point point, Vector vector){
		this.q0 = point;
		this.normal = vector.normalize();
	}

	//-------------------Getters---------------------//

	public Vector getVector(){
		return normal;
	}

	public Point getPoint() {
		return q0;
	}

	//-------------------Interface implement --------//
	@Override
	public Vector getNormal(Point point) {
		return this.normal;
	}

	@Override
	public String toString(){
		return "Plane{" +
				this.q0.toString()		+ ", " +
				this.normal.toString()	+
				"}";
	}

	@Override
	public List<Point> findIntersections(Ray ray) {

		//data of the ray
        Point P0 = ray.getStartPoint();
        Vector v = ray.getDirectionVector();

		//if the intersaction point is q0
        if (q0.equals(P0)) //Returns an unmodifiable list containing one element-q0
            return List.of(q0);

        double nv = normal.dotProduct(v);
        //check if 0 in the denominator-n and v orthogonal
        //the ray is lying on the plane
        if (isZero(nv)) return null;

        //if everything good-calculate p
        double t = normal.dotProduct(q0.subtract(P0));
        t /= nv;
        Point p = ray.getPoint(t);
        return List.of(p);
    }
}
