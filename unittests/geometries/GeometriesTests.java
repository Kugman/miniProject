package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {
    Point point1 = new Point(1, 0, 0);
    Point point2 = new Point(1, 1, 0);
    Point point3 = new Point(1, 1, 1);
    Triangle triangle = new Triangle(point1, point2, point3);
    Geometries geometries = new Geometries(triangle);
//    Geometries emptyGeometries = new Geometries();

    /**
     * Test method for {@link Geometries#Geometries()} .
     */
    @Test
    void Geometries(){
        try {
            Geometries geometries = new Geometries();
        }catch (IllegalArgumentException e) {
            fail("Failed constructing an empty Geometries object");
        }
    }

    /**
     * Test method for {@link Geometries#Geometries(Intersectable...)}  .
     */
    @Test
    void Geometries2(){
        Point point1 = new Point(1, 0, 0);
        Point point2 = new Point(1, 1, 0);
        Point point3 = new Point(1, 1, 1);
        Triangle triangle = new Triangle(point1, point2, point3);

        try{
            Geometries geometries = new Geometries(triangle);
        }catch (IllegalArgumentException e) {
            fail("Failed constructing correct Geometries object");
        }
    }

    /**
     * Test method for {@link Geometries#add(Intersectable...)}   .
     */
    @Test
    void add() {
        try{
            geometries.add(triangle);
        }catch (IllegalArgumentException e) {
            fail("Failed to add a correct object");
        }
    }

    /**
     * Test method for {@link Geometries#findIntersections(Ray)}    .
     */
    @Test
    void findIntersections() {
        Geometries geometries = new Geometries();

        // =============== Boundary Values Tests ==================
        //Empty collection
        assertNull(  geometries.findIntersections(new Ray(new Point(3, 1, 0.5), new Vector(1, 1, 0))),"list Empty");

        geometries.add(
                new Triangle(
                        new Point(-1, 0.5, -6),
                        new Point(-1, 0, -6),
                        new Point(2, 0, -6)),
                new Plane(
                        new Point(1, 0, -4),
                        new Vector (0, 0, 1)),
                new Sphere(
                        new Point(0, 0, 1), 5)
                );

        //No shape intersects with a body
        assertNull( geometries.findIntersections(new Ray(new Point(0,0,8), new Vector(1,0, 0))),"Ray not included in the plane");

        //One shape intersects
        List<Point> l = geometries.findIntersections(new Ray(new Point(6,-6,4), new Vector(-12,12,0)));
        assertEquals( 2, l.size(),"Ray not included in the plane");

        //All shapes intersect
        l = geometries.findIntersections(new Ray(new Point(-0.7, 0.2, -8), new Vector(0, 0, 1)));
        assertEquals( 4, l.size(),"Ray not included in the plane");


        // ============ Equivalence Partitions Tests ==============
        //Some shapes but not all intersects
        l = geometries.findIntersections(new Ray(new Point(1, 0, -8), new Vector(0, 0, 1)));
        assertEquals( 3, l.size(),"Ray not included in the plane");

    }
}