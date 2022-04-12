package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTests {
    Point point1 = new Point(0, 0, 1);
    Point point2 = new Point(1, 0, 0);
    Point point3 = new Point(0, 1, 0);
    Point point4 = new Point(-1, 1, 1);
    Polygon pl = new Polygon(point1, point2, point3, point4);

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC00: less than 3 vertics
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0)), //
                "Constructed a polygon with not enougth Points");


        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }


    /**
     * Test method for {@link Polygon#getVertices()} .
     */
    @Test
    void getVertices() {
        List<Point> points = pl.getVertices();
        assertTrue(points.contains(point1), "ERROR: in Polygon.getVertics(): point#1 is not returned");
        assertTrue(points.contains(point2), "ERROR: in Polygon.getVertics(): point#2 is not returned");
        assertTrue(points.contains(point3), "ERROR: in Polygon.getVertics(): point#3 is not returned");
        assertTrue(points.contains(point4), "ERROR: in Polygon.getVertics(): point#4 is not returned");
        assertEquals(points.size(), 4, "ERROR: in Polygon.getVertics(): return too many vertics");
    }


    /**
     * Test method for {@link Polygon#getPlane()} .
     */
    @Test
    void getPlane() {
        Plane plane = new Plane(point1, point2, point3);
        Plane resault = pl.getPlane();
        assertTrue((resault.getPoint().equals(plane.getPoint()) &&
                (resault.getVector().equals(plane.getVector()))),
                "ERROR: in Polygon.getPlane- Wrong Plane returned");
    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        Vector result = pl.getNormal(new Point(0, 0, 1));
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), result, "Bad normal to trinagle");
    }


    /**
     * Test method for {@link Polygon#toString()} .
     */
    @Test
    void testToString() {
        assertEquals("Poligon: {" +
                        "[Point: {(0.0,0.0,1.0)}, Point: {(1.0,0.0,0.0)}, Point: {(0.0,1.0,0.0)}, Point: {(-1.0,1.0,1.0)}], " +
                        "Plane{Point: {(0.0,0.0,1.0)}, Vector: {(0.5773502691896258,0.5773502691896258,0.5773502691896258)}}" +
                        "}",
                pl.toString());
    }
}