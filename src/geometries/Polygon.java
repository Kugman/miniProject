package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * geometries.Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Efrat Kugman
 */

public class Polygon implements Geometry {

	protected List<Point> vertices;
	protected Plane plane;

	//----------------Constructor-----------------------//
	public Polygon(Point... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			// no need for more tests for a geometries.Triangle
			return;
	}

	//----------------Getters---------------------------//
	public List<Point> getVertices() {
		return vertices;
	}

	public Plane getPlane() {
		return plane;
	}

	//----------------Interface implements--------------//
	@Override
	public Vector getNormal(Point point) {
		return plane.getNormal(point);
	}

	@Override
	public String toString(){
		return "Poligon: {"			+
				vertices.toString() + ", " +
				plane.toString()	+
				"}";
	}
}
