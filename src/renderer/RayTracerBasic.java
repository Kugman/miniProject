package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

public class RayTracerBasic extends RayTracerBase{


    /**
     * @param scene initialize field scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Color calcColor(GeoPoint point){
        Color c = scene.ambientLight.getIntensity().add(point.geometry.getEmission());
        return c;
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        if(closestPoint == null) return scene.background;
        return calcColor(closestPoint);
    }
}
