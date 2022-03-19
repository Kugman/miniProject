package geometries;
import primitives.Point;
import primitives.Vector;

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
}
