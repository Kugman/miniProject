package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

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
        try {
            Triangle triangle = new Triangle(point1, point2, point3);
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct triangle");
        }
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
        System.out.println("the triangle is: " + triangle.toString());
    }
}