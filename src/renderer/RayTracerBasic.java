package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase{


    /**
     * @param scene initialize field scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null) return scene.background;
        Point closestPoint = ray.findClosestPoint(intersections);
        if(closestPoint == null) return scene.background;
        return calcColor(closestPoint);
    }
}
