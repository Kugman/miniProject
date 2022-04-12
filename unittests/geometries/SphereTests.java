package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        assertEquals("Sphere: {Point: {(1.0,1.0,1.0)}, Radius: 3.0}" ,
                sphere.toString());
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)} .
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        List<Point> result3 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result3.size(),"Wrong number of points");
        assertEquals(result3.get(0),new Point(2,0,0));

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2.2, 0, 0), new Vector(1, 1, 0))),"Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals(new Point(0.9999999999999998, 0.9999999999999998, 0), result.get(0),"Ray in sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(  sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0.6, 0.8, 0))),"Ray's line out of sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals( 2, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)), result,"Ray in sphere");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0)), result,"Ray in sphere");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(1, 0, 0)));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0)), result,"Ray start center");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals(List.of(new Point(1, 1, 0)), result,"Ray start center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(  sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),"Ray start at sphere and goes outside");

        // TC18: Ray starts after sphere (0 points)
        assertNull( sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),"Ray start after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull( sphere.findIntersections(new Ray(new Point(2, 0, -1), new Vector(0, 0, 1))),"Ray start before tanget point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1))),"Ray start at tanget point" );

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 1), new Vector(0, 0, 1))),"Ray start after tanget point" );

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(0, 0, 1))),"Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }


}