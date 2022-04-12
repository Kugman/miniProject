package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    Point point = new Point(1, 1, 1);
    Vector vector = new Vector(2, 2, 1);
    Ray ray = new Ray(point, vector);
    double radius = 3.5;
    Tube tube = new Tube(radius, ray);


    /**
     * Test method for {@link Tube#Tube(double, Ray)} .
     */
    @Test
    void Tube(){
        try {
            Tube t = new Tube(radius, ray);
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct tube");
        }
    }

    /**
     * Test method for {@link Tube#getRay()} .
     */
    @Test
    void getRay() {
        assertEquals(tube.getRay(), ray, "ERROR: getRay() return wrong value");
    }

    /**
     * Test method for {@link Tube#getRadius()} .
     */
    @Test
    void getRadius() {
        assertEquals(tube.getRadius(), radius, "ERROR: getRadius() return wrong value");
    }

    /**
     * Test method for {@link Tube#getNormal(Point)}  .
     */
    @Test
    void getNormal() {
        Point rayPoint = new Point(0, 0, 0);
        Vector rayVector = new Vector(1, 0, 0);
        Ray tubeRay = new Ray(rayPoint, rayVector);
        Tube t = new Tube(3, tubeRay);
        // ============ Equivalence Partitions Tests ==============
        //TEST FOR TUBE PLANE
        Vector v1 = new Vector(0.0, 0.7071067811865475, 0.7071067811865475);
        assertEquals(v1, t.getNormal(new Point(0, Math.sqrt(9 / 2), Math.sqrt(9 / 2))), "getNormal() result is not expected");

        // =============== Boundary Values Tests ==================
        //When connecting the point to the head of the ray of the cylinder axis produces a right angle with the axis - the point "is in front of the head of the ray")
        Vector v = new Vector(0, 1, 0);
        //o	The first and second points are equal.
        assertTrue(v.equals(t.getNormal(new Point(0, 3, 0))), "getNormal() result is not expected");
        //o	The points are all on the same line
        assertEquals(v, t.getNormal(new Point(0, 3, 0)), "getNormal() result is not expected");
        Point funcParam = new Point(1, 2, 3);
        Vector vector2 = funcParam.subtract(rayPoint);
        double tmp = rayVector.dotProduct(vector2);
        Point point3 = vector2.add(rayVector.scale(tmp));
        Vector result = funcParam.subtract(point3).normalize();
        assertEquals(result, t.getNormal(funcParam), "getNormal() result is not expected");
    }

    /**
     * Test method for {@link Tube#toString()} .
     */
    @Test
    void testToString() {
        assertEquals("Tube: {" +
                        "Ray: {" +
                        "Point: {(1.0,1.0,1.0)}" +
                        "Vector: {(0.6666666666666666,0.6666666666666666,0.3333333333333333)}}, " +
                        "Radius: 3.5}",
                tube.toString());
    }
}