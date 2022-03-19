package geometries;
import primitives.Point;
import primitives.Vector;

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
}
