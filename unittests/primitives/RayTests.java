package primitives;

import org.junit.jupiter.api.Test;

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
}