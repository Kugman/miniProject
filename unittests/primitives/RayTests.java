package primitives;

import geometries.Intersectable;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {
    Point point = new Point(1, 2, 3);
    Vector vector = new Vector(1, 1, 1);
    Ray ray = new Ray(point, vector);

    /**
     * Test method for {@link Ray#Ray(Point, Vector)}.
     */
    @Test
    void Ray(){
        try {
            Ray tmp = new Ray(point, vector);
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Ray");
        }
    }

    /**
     * Test method for {@link Ray#getStartPoint()}.
     */
    @Test
    void getStartPoint() {
        assertEquals(point, ray.getStartPoint(), "ERROR: getStartPoint() return wrong value");
    }

    /**
     * Test method for {@link Ray#getDirectionVector()} .
     */
    @Test
    void getDirectionVector() {
        assertEquals(vector.normalize(), ray.getDirectionVector(),"ERROR: getDirectionVector() return wrong value");
    }

    /**
     * Test method for {@link Ray#toString()} .
     */
    @Test
    void testToString() {
        System.out.println("the ray is: " + ray.toString());
    }

    /**
     * Test method for {@link Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:A point in the middle of the list is closest to the beginning of the fund
        Ray ray = new Ray(new Point(1,3,0), new Vector(0,1,0));

        List<Point> list = new LinkedList<Point>();
        list.add(new Point(0,5,0));
        list.add(new Point(0,4,0));
        list.add(new Point(0, 3,0));
        list.add(new Point(0,2,0));
        list.add(new Point(0,1,0));
        assertEquals(list.get(2), ray.findClosestPoint(list));
        // =============== Boundary Values Tests ==================
        //TC11:THE LIST IS EMPTY-return null
        Ray ray1 = new Ray(new Point(0, 0, 10), new Vector(1, 10, -100));

        List<Point> list1 = null;
        assertNull(ray.findClosestPoint(list1), "try again");
        //TC12:The first point is closest to the beginning of the foundation
        Ray ray2 = new Ray(new Point(1,5,0), new Vector(0,1,0));
        assertEquals(list.get(0), ray2.findClosestPoint(list));
        //TC13: The last point is closest to the beginning of the foundation

        Ray ray3 = new Ray(new Point(1,1,0), new Vector(0,1,0));
        assertEquals(list.get(4), ray3.findClosestPoint(list));


    }

}