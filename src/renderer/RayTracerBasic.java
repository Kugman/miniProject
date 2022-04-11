package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{

    private static final double DELTA = 0.1;

    /**
     * @param scene initialize field scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Color calcColor(GeoPoint point, Ray ray){
        Color c = scene.ambientLight.getIntensity()
                .add(point.geometry.getEmission())
                .add(calcLocalEffects(point, ray));
        return c;
    }

    private Color calcLocalEffects(GeoPoint point, Ray ray) {
        Color color = Color.BLACK;
        Vector v = ray.getDirectionVector();
        Vector n = point.geometry.getNormal(point.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = point.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(lightSource, point, l, n, nv)) {
                    Color iL = lightSource.getIntensity(point.point);//.scale(DELTA);
                    color = color.add(iL.scale(calcDiffusive(material.kD, nl)),
                            iL.scale(calcSpecular(nl, material.kS, l, n, v, material.nShininess)));

                }
            }
        }
        return color;
    }

    private double calcSpecular(double nl, double ks, Vector l, Vector n, Vector v, int nShininess) {
        //according to phong model formula
        //Calculating reflectance vector:
        Vector r = l.subtract(n.scale(nl * 2));
        double minusvr = v.dotProduct(r) * -1;
        return ks * Math.pow(Math.max(0, minusvr), nShininess);
    }

    private double calcDiffusive(double kd, double nl) {
        //according to phong model formula
        double factor = kd * Math.abs(nl);
        return factor;
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        if(closestPoint == null) return scene.background;
        return calcColor(closestPoint, ray);
    }

    private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1);

        Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point,lightDirection);

        // if(true) return false;
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(lightRay);//, lightSource.getDistance(gp.point));
        if(intersections == null) return true;

        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint geoPoint : intersections) {
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) > 0)
                return false;
        }
        return true;
    }







}
