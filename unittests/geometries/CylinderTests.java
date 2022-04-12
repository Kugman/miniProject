package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTests {

    Point point = new Point(1, 2, 3);
    Vector vector = new Vector(1, 1, 1);
    Ray ray = new Ray(point, vector);
    Cylinder cylinder = new Cylinder(3, ray, 5);

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(double,Ray, double)}.
     */
    @Test
    void Cylinder(){
        try{
            Cylinder c = new Cylinder(3, ray, 5);
        }catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Cylinder");
        }
    }

    /**
     * Test method for {@link geometries.Cylinder#getHeight()}.
     */
    @Test
    void getHeight() {
        assertEquals(cylinder.getHeight(), 5, "ERROR: cylynder getHeight() does not work correctly");
    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        Cylinder c = new Cylinder(3, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 5);
        // ============ Equivalence Partitions Tests ==============
        //TEST FOR CYLINDER PLANE
        Vector v1 = new Vector(0.0, 0.7071067811865475, 0.7071067811865475);
        assertEquals(v1, c.getNormal(new Point(0, Math.sqrt(9 / 2), Math.sqrt(9 / 2))), "getNormal() result is not expected");

        // =============== Boundary Values Tests ==================
        //When connecting the point to the head of the ray of the cylinder axis produces a right angle with the axis - the point "is in front of the head of the ray")
        Vector v = new Vector(0, 1, 0);
        //o	The first and second points are equal.
        assertTrue(v.equals(c.getNormal(new Point(0, 3, 0))), "getNormal() result is not expected");
        //o	The points are all on the same line
        assertEquals(v, c.getNormal(new Point(0, 3, 0)), "getNormal() result is not expected");
    }


    /**
     * Test method for {@link Cylinder#toString()}.
     */
    @Test
    void testToString(){
        assertEquals("Cylinder {" +
                        "Ray: {" +
                        "Point: {(1.0,2.0,3.0)}" +
                        "Vector: {(0.5773502691896258,0.5773502691896258,0.5773502691896258)}}, " +
                        "radius: 3.0, " +
                        "height: 5.0" +
                        "}"
        , cylinder.toString());
    }
}