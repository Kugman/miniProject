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
    public void testConstructor3Point(){
        assertDoesNotThrow(()->new Plane(
                        new Point(1, 1, 1),
                        new Point(1, 2, 3),
                        new Point(2, 2, 2)),
                "Failed constructing a correct plane");
    }

    /**
     * Test method for {@link Plane#Plane(Point, Vector)}.
     */
    @Test
    public void testConstructorPointVector(){
        assertDoesNotThrow(()->new Plane(new Point(1, 1, 1), new Vector(1, 1, 1)),
                "Failed constructing a correct plane");
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
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(
                        new Point(1,2,3),
                        new Point(2,4,6),
                        new Point(4,8,12)
                ).getNormal(point1),
                "GetNormal() should throw an exception, but it failed");

    }

    /**
     * Test method for {@link geometries.Plane#toString()}.
     */
    @Test
    void testToString() {
        assertEquals("Plane{" +
                        "Point: {(1.0,2.0,0.0)}, " +
                        "Vector: {(0.6804138174397716,0.6804138174397716,0.2721655269759087)}" +
                        "}",
        plane.toString());
    }
}