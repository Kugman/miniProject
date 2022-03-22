package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    @Test
    void getRay() {
        fail("Not yet implemented");
    }

    @Test
    void getRadius() {
        fail("Not yet implemented");
    }

    @Test
    void getNormal() {
        Tube t = new Tube(3, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
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
    }

    @Test
    void testToString() {
        fail("Not yet implemented");
    }
}