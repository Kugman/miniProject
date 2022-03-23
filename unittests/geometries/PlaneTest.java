package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTest {
    Point point1 = new Point(1,2,0);
    Point point2 = new Point(-4,7,0);
    Point point3 = new Point(1,0,5);
    Plane plane  = new Plane(point1, point2, point3);

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    public void testConstructor(){
        try{
            Plane p = new Plane(new Point(1, 1, 1), new Point(1, 2, 3), new Point(2, 2, 2));
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }

    /**
     * Test method for {@link Plane#Plane(Point, Vector)}.
     */
    @Test
    public void testConstructor2(){
        try{
            Plane p = new Plane(new Point(1, 1, 1), new Vector(1, 1, 1));
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }

    /**
     * Test method for {@link geometries.Plane#getVector()}.
     */
    @Test
    void getVector() {
        Vector v1 = point2.subtract(point1);
        Vector v2 = point3.subtract(point1);
        assertEquals( plane.getVector(), v1.crossProduct(v2).normalize(),"ERROR: getVector does not work propertly");
    }

    /**
     * Test method for {@link geometries.Plane#getPoint()}.
     */
    @Test
    void getPoint() {
        assertEquals(plane.getPoint(), point1, "ERROR: getPoint() does not work propertly");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        //test for normal plane
        Vector v1 = point1.subtract(point2);
        Vector v2= point2.subtract(point3);
        Vector v3= point3.subtract(point1);
        Vector n = plane.getNormal(point1);
        //check that the vector n is orthogonal for all the vectors in the plane
        assertTrue( isZero(v1.dotProduct(n)),"ERROR: Bad normal to plane");
        assertTrue( isZero(v2.dotProduct(n)),"ERROR: Bad normal to plane");
        assertTrue( isZero(v3.dotProduct(n)),"ERROR: Bad normal to plane");
        try {
            new Plane(new Point(1,2,3),new Point(2,4,6),new Point(4,8,12)).getNormal(point1);
            fail("GetNormal() should throw an exception, but it failed");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link geometries.Plane#toString()}.
     */
    @Test
    void testToString() {
        System.out.println("the plane is: " + plane);
    }
}