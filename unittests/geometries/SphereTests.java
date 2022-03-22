package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    @Test
    void getCenter() {
        fail("Not yet implemented");
    }

    @Test
    void getRadius() {
        fail("Not yet implemented");
    }

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(new Point(0,0,0),5);
        Vector tmp=new Vector(0,1,0);
        assertTrue(  tmp.equals(sp.getNormal(new Point(0, 5, 0))),
                "getNormal() result is not expected");
    }

    @Test
    void testToString() {
        fail("Not yet implemented");
    }
}