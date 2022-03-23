package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {
    double radius = 3.0;
    Point point = new Point(1, 1, 1);
    Sphere sphere = new Sphere(point ,radius);

    /**
     * Test method for {@link Sphere#Sphere(Point, double)} .
     */
    @Test
    void Sphere() {
        try{
            Sphere p = new Sphere(new Point(1, 1, 1),3);
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }
    }


    /**
     * Test method for {@link Sphere#getCenter()} .
     */
    @Test
    void getCenter() {
        Point result = sphere.getCenter();
        assertEquals(result, point, "ERROR: in Sphere.getCenter- Wrong point returned");
    }

    /**
     * Test method for {@link Sphere#getRadius()}.
     */
    @Test
    void getRadius() {
        double result = sphere.getRadius();
        assertEquals(result, radius, "ERROR: in Sphere.getCenter- Wrong raduis returned");
    }

    /**
     * Test method for {@link Sphere#getNormal(Point)} .
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(new Point(0,0,0),5);
        Vector tmp=new Vector(0,1,0);
        assertTrue(  tmp.equals(sp.getNormal(new Point(0, 5, 0))),
                "getNormal() result is not expected");
        assertThrows(IllegalArgumentException.class, //
                () -> sphere.getNormal(point), //
                "getNormal() should fail for point parameter equals to sphere center");

    }

    /**
     * Test method for {@link Sphere#toString()}.
     */
    @Test
    void testToString() {
        System.out.println("the sphere is: " + sphere.toString());
    }
}