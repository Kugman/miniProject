package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {
    Point point1 = new Point(1, 0, 0);
    Point point2 = new Point(1, 1, 0);
    Point point3 = new Point(1, 1, 1);
    Triangle triangle = new Triangle(point1, point2, point3);

    /**
     * Test method for {@link Triangle#Triangle(Point, Point, Point)} .
     */
    @Test
    void Triangle() {
        assertDoesNotThrow(()-> new Triangle(point1, point2, point3),
                "Failed constructing a correct triangle");
    }

    /**
     * Test method for {@link Triangle#getPoint1()} .
     */
    @Test
    void getPoint1() {
        assertEquals(triangle.getPoint1(), point1, "ERROR: function getPoint1() return wrong value");
    }

    /**
     * Test method for {@link Triangle#getPoint2()} .
     */
    @Test
    void getPoint2() {
        assertEquals(triangle.getPoint2(), point2, "ERROR: function getPoint2() return wrong value");
    }

    /**
     * Test method for {@link Triangle#getPoint3()} .
     */
    @Test
    void getPoint3() {
        assertEquals(triangle.getPoint3(), point3, "ERROR: function getPoint3() return wrong value");
    }

    /**
     * Test method for {@link Triangle#toString()} .
     */
    @Test
    void testToString() {
        assertEquals("Triangle: {[Point: {(1.0,0.0,0.0)}, Point: {(1.0,1.0,0.0)}, Point: {(1.0,1.0,1.0)}]}",
                triangle.toString());
    }

    @Test
    void findIntersections() {
        Triangle triangle = new Triangle(new Point(-1, 3, 0), new Point(-1, 0, 0), new Point(2, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01:Ray inside triangle (1 ptions)
        List<Point> result = triangle.findIntersections(new Ray(new Point(-1, 8, -5), new Vector(1, -7, 5)));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(0, 1, 0)), result,"Ray intersects the triangle");

        //TC02:Ray outside against edge (0 ptions)
        assertNull(triangle.findIntersections(new Ray(new Point(-3, -3, -1), new Vector(1, 5, 1))),"Ray not included in the triangle");
        //TC03:Ray outside against vertex (0 ptions)
        assertNull(triangle.findIntersections(new Ray(new Point(-3, -3, -1), new Vector(1, 2, 1))),"Ray not included in the triangle");

        // =============== Boundary Values Tests ==================

        //TC11:Ray on edge (0 ptions)
        assertNull(triangle.findIntersections(new Ray(new Point(-2, -3, -1), new Vector(1, 4, 1))),"Ray not included in the triangle");

        //TC12:Ray in vertex (0 ptions)
        assertNull(triangle.findIntersections(new Ray(new Point(-2, -3, -1), new Vector(1, 6, 1))),"Ray not included in the triangle");

        //TC13: Ray on edge's continuation (0 ptions)
        assertNull(triangle.findIntersections(new Ray(new Point(-2, -3, -1), new Vector(-1, 8, 1))),"Ray not included in the triangle");


    }



}