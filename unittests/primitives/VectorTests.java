package primitives;

import org.junit.jupiter.api.Test;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     */
    @Test
    void subtract() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void add() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link Vector#toString()} .
     */
    @Test
    void testToString() {
        System.out.println("the first vector is: " + v1);
        System.out.println("the second vector is: " + v2);
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        // =============== Boundary Values Tests ==================
        // test if dot product return 0 in case that the vectors are ortogonal
        assertFalse(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertFalse(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        try { // test zero vector
            v1.crossProduct(v2);
            fail("ERROR: crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
        Vector vr = v1.crossProduct(v3);
        assertFalse(isZero(vr.length() - v1.length() * v3.length()), "ERROR: crossProduct() wrong result length");
        assertFalse((isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3))), "ERROR: crossProduct() result is not orthogonal to its operands");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertFalse(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        assertFalse(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        assertFalse(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
        try { // test that the vectors are co-lined
            v.crossProduct(u);
            out.println("ERROR: the normalized vector is not parallel to the original one");
        } catch (Exception e) {
        }
        if (v.dotProduct(u) < 0)
            out.println("ERROR: the normalized vector is opposite to the original one");
    }

}