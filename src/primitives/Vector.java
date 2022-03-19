package primitives;


/**
 * class Vactor is the basic class representing a vector that start from the beginning.
 *
 * @author Efrat Kugman
 */

public class Vector extends Point {

    //------------Constructors-----------------//
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.point.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector head cannot be (0,0,0)");
    }

    public Vector(Double3 head) {
        super(head);
        if (head.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector head cannot be (0,0,0)");
    }

    //------------Functions-------------------//
    public Vector add(Vector v) {
        return new Vector(this.point.add(v.point));
    }

    public Vector scale(double factor) {
        return new Vector(this.point.scale(factor));
    }

    public double dotProduct(Vector v) {
        return this.point.product(v.point).doubleHashCode();
    }

    public Vector crossProduct(Vector v) {
        double u1 = this.point.d1;
        double u2 = this.point.d2;
        double u3 = this.point.d3;

        double v1 = v.point.d1;
        double v2 = v.point.d2;
        double v3 = v.point.d3;

        return new Vector(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1);
    }

    public double lengthSquared() {
        return this.point.product(this.point).doubleHashCode();
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        return scale(1 / this.length());
    }

    public String toString(){
        return "Vector: {" +
                this.point.toString() +
                "}";
    }
}