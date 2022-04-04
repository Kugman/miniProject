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

public class Polygon extends Geometry {

	protected List<Point> vertices;
	protected Plane plane;

	//----------------Constructor-----------------------//
	public Polygon(Point... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return;

		Vector n = plane.getNormal(vertices[3]);
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
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

	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
		return null;
	}
}
