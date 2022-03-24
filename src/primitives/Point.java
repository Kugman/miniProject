package primitives;
/**
 * basic point for project
 *
 * @author Efrat Kugman
 */

public class Point {
    Double3 point;

    //---------Constructors-------------------//
    public Point(Double3 point)
    {
        this.point = point;
    }

    public Point(double x, double y, double z) {
        this.point = new Double3(x, y, z);
       }

    //--------functions----------------------//
    public Vector subtract(Point pt2) {
        if (pt2.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector to Point (0,0,0)");
        }
        return new Vector(this.point.subtract(pt2.point));
    }

    public Point add(Vector vector) {
        Point point = new Point(this.point.add(vector.point));
        return point;
    }

    public double distanceSquared(Point other) {
        Double3 tmpPoint = this.point.subtract(other.point);
        return tmpPoint.product(tmpPoint).doubleHashCode();
    }

    public double distance(Point other1) {
        return Math.sqrt(distanceSquared(other1));
    }

    public String toString(){
        return "Point: {" +
                this.point.toString() +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return (this.point.equals(other.point));
    }


    public double getX() {
        return point.d1;
    }

    public double getY() {
        return point.d2;
    }

    public double getZ() {
        return point.d3;
    }

}
