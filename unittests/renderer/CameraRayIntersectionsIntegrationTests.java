package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class CameraRayIntersectionsIntegrationTests {

    static final Point ZERO_POINT = new Point(0, 0, 0);

    private void countIntersections(Camera cam, Intersectable geometry, int expectedAns, int nX, int nY) {
        int counter = 0;
        //values according to the presentation
        for (int i = 0; i < nX; ++i)
            for (int j = 0; j < nY; ++j) {
                //intersection is the list of intersection point/s with the specific geometry
                var intersections = geometry.findIntersections(cam.constructRay(3, 3, j, i));
                counter += intersections == null ? 0 : intersections.size();
            }
        //assert count
        assertEquals(expectedAns, counter, "Wrong amount of intersections");
    }

    @Test
    public void CameraRaySphereIntersections(){
        Camera cam1 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);

        //TC01-2 intersection points-small sphere
        Sphere sp = new Sphere(new Point(0, 0, -3), 1d);
        countIntersections(cam1, sp, 2, 3, 3);

        //TC02-18 intersection points-big sphere
        Sphere sp1 = new Sphere(new Point(0, 0, -2.5), 2.5d);
        countIntersections(cam2, sp1, 18, 3, 3);

        //TC03-10 intersection points-medium sphere
        Sphere sp2 = new Sphere(new Point(0, 0, -2), 2);
        countIntersections(cam2, sp2, 10, 3, 3);

        //TC04-9 intersection points-the camera is in the sphere
        Sphere sp3 = new Sphere(new Point(0, 0, -1), 4);
        countIntersections(cam2, sp3, 9, 3, 3);

        //TC05-0 intersection points-the sphere is behind the camera
        Sphere sp4 = new Sphere(new Point(0, 0, 1), 0.5);
        countIntersections(cam1, sp4, 0, 3, 3);
    }

    @Test
    public void CameraRayTriangleIntersections() {
        Camera cam1 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);

        //TC01-1 intersection points-small TRIANGLE
        Triangle tr1 = new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        countIntersections(cam2, tr1, 1, 3, 3);

        //TC02-2 intersection points-big TRIANGLE
        Triangle tr2 = new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        countIntersections(cam2, tr2, 2, 3, 3);
    }

    @Test
    public void CameraRayPlaneIntersections() {
        Camera cam1 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);

        // TC01: Plane is against camera 9 points
        Plane pl1=new Plane(new Point(0,0,-4),new Vector(0,0,1));
        countIntersections(cam1, pl1, 9, 3, 3);

        // TC02:  Plane with small angle 9 points
        //(the plane formula: y+2z=-10)
        Plane pl2 =new Plane (new Point(2,-2,-4), new Vector(0,1,2));
        countIntersections(cam2, pl2, 9, 3, 3);

        // TC03:  Plane is  parallel to the low rays 6 points
        //(the plane formula: y+z=-5)
        Plane pl3 =new Plane (new Point(2,-2,-3), new Vector(0,1,1));
        countIntersections(cam2, pl3, 6, 3, 3);

        // TC03:  Plane is  behind the camera
        //we added a test
        Plane pl4 =new Plane(new Point(0,0,4),new Vector(0,0,1));
        countIntersections(cam1, pl4, 0, 3, 3);
    }

}
