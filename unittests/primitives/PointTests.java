package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Efrat Kugman
 *
 */


class PointTests {
    Point p1 = new Point(1, 2, 3);
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void subtract() {
        assertEquals(new Vector(1,1,1), (new Point(2,3,4).subtract(p1)), "ERROR: Point - Point does not work correctly");
        assertThrows(IllegalArgumentException.class, //
                () -> p1.subtract(p1), //
                "substruct cannt work on 2 equals points");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void add() {
        assertEquals(p1.add(new Vector(-1, -2, -3)), (new Point(0, 0, 0)), "ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void distanceSquared() {
        assertEquals(0,p1.distance(p1));
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void distance() {
        assertEquals(0,p1.distance(p1));
    }

    /**
     * Test method for {@link Point#toString()} .
     */
    @Test
    void testToString() {
        System.out.println("the point is: " + p1);
    }

    /**
     * Test method for {@link primitives.Point#equals(Object)}.
     */
    @Test
    void testEquals() {
        assertTrue(p1.equals(new Point(1, 2, 3)), "ERROR: Point equals does not work correctly");
    }
}