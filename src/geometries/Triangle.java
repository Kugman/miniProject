package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
}
