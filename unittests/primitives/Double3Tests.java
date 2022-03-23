package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Efrat Kugman
 *
 */


class Double3Tests {
    Double3 ob = new Double3(1.5, 1, 1);


    /**
     * Test method for {@link Double3#equals)}.
     */
    @Test
    void equalsTest() {
        assertTrue(ob.equals(ob), "ERROR: equals() should return True for the same object");
        assertFalse(ob.equals(null), "ERROR: equals() should return False for null object");
        assertFalse(ob.equals(3.7), "ERROR: equals() should return False for wrong type object");
        assertTrue(ob.equals(new Double3(1.5, 1, 1)), "ERROR: equals() should return True for the equals object");
    }

    /**
     * Test method for {@link Double3#hashCode()}.
     */
    @Test
    void hashCodeTest() {
        assertEquals(Math.round(3.5), ob.hashCode(), "ERROR: hashCode() does not work correctly");
    }

    /**
     * Test method for {@link Double3#doubleHashCode()} .
     */
    @Test
    void doubleHashCode() {
        assertEquals(1.5 + 1 + 1, ob.hashCode(), "ERROR: doubleHashCode() does not work correctly");
    }

    /**
     * Test method for {@link Double3#toString()}.
     */
    @Test
    void toStringTest() {
        System.out.println("the Double3 object is: " + ob.toString());
    }

    /**
     * Test method for {@link Double3#add(Double3)} .
     */
    @Test
    void add() {
        Double3 dAdd = new Double3(1.5, 2, 2);
        Double3 res = new Double3(3, 3, 3);
        assertEquals(res, ob.add(dAdd), "ERROR: add() does not work propertly");
    }

    /**
     * Test method for {@link Double3#subtract(Double3)} .
     */
    @Test
    void subtract() {
        Double3 res = new Double3(1.5, 2, 2);
        Double3 src = new Double3(3, 3, 3);
        assertEquals(res, src.subtract(ob), "ERROR: subtract() does not work propertly");
    }

    /**
     * Test method for {@link Double3#scale(double)}  .
     */
    @Test
    void scale() {
        Double3 res = new Double3(3, 2, 2);
        double red = 2;
        assertEquals(res, ob.scale(red), "ERROR: scale() does not work propertly");
    }

    /**
     * Test method for {@link Double3#reduce(double)}  .
     */
    @Test
    void reduce() {
        Double3 res = new Double3(3, 2, 2);
        double red = 0.5;
        assertEquals(res, ob.reduce(red), "ERROR: reduce() does not work propertly");
    }

    /**
     * Test method for {@link Double3#product(Double3)}  .
     */
    @Test
    void product() {
        Double3 res = new Double3(3, 3, 5);
        Double3 var = new Double3(2, 3, 5);
        assertEquals(res, ob.product(var), "ERROR: product() does not work propertly");
    }
}