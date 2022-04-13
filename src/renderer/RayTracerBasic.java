package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{

//    private static final double DELTA = 0.1;
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * @param scene initialize field scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Color calcColor(GeoPoint point, Ray ray){
        //GeoPoint closestPoint = findClosestIntersection(ray);
        Color c = calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
        return c;
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k){
        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));
        return (1 == level) ? color : color.add(calcGlobalEffects(intersection, ray.getDirectionVector(), level, k));
    }

    private Color calcGlobalEffects(Ray ray, int level, double kx, double kkx){
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx));
    }

    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffects(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);

        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));

        return color;
    }

    private Color calcLocalEffects(GeoPoint point, Ray ray, double k) {
        Color color = Color.BLACK;
        Vector v = ray.getDirectionVector();
        Vector n = point.geometry.getNormal(point.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = point.geometry.getMaterial();
        color = point.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
//                if(unshaded(lightSource, point, l, n, nv)) {
                double ktr = transparency(point, lightSource, l, n, nv);
                if(ktr * k > MIN_CALC_COLOR_K){
                    Color iL = lightSource.getIntensity(point.point).scale(ktr);
                    color = color.add(calcDiffusive(material.kD, nl, iL),
                            calcSpecular(nl, material.kS, l, n, v, material.nShininess, iL));

                }
            }
        }
        return color;
    }

    private Ray constructRefractedRay(Point point, Vector inRay, Vector n) {
        return new Ray(point, inRay, n);
    }

    private Ray constructReflectedRay(Point point, Vector inRay, Vector n) {
        Vector r = inRay.subtract(n.scale(2 * inRay.dotProduct(n)));
        return new Ray(point, r, n);
    }

    private Color calcSpecular(double nl, double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        //according to phong model formula
        //Calculating reflectance vector:
        Vector r = l.subtract(n.scale(nl * 2));
        double minusvr = v.dotProduct(r) * -1;
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusvr), nShininess));
    }

    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        //according to phong model formula
        double factor = kd * Math.abs(nl);
        return lightIntensity.scale(factor);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        GeoPoint closestIntersection = ray.findClosestGeoPoint(intersections);
        return closestIntersection;
    }

    @Override
    public Color traceRay(Ray ray){
        GeoPoint closestPoint = findClosestIntersection(ray);
        return (null == closestPoint) ? scene.background : calcColor(closestPoint, ray);
    }

    public Color old_traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        if(closestPoint == null) return scene.background;
        return calcColor(closestPoint, ray);
    }

    private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(lightRay);//, lightSource.getDistance(gp.point));
        if(intersections == null) return true;

        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint geoPoint : intersections) {
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) > 0
            && alignZero(geoPoint.geometry.getMaterial().kT) == 0)
                return false;
        }
        return true;
    }

    private double transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {
        //Vector lightDirection = l.scale(-1);
        Vector v = lightSource.getL(gp.point).scale(-1);
        Ray lightRay = new Ray(gp.point, v, n);
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(lightRay);//, lightSource.getDistance(gp.point));
        if(intersections == null) return 1.0;

        double ktr = 1;
        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint geoPoint : intersections) {
            if (alignZero(geoPoint.point.distance(gp.point) - lightDistance) > 0){
                ktr *= geoPoint.geometry.getMaterial().kT;
                if(ktr < MIN_CALC_COLOR_K) return 0.0;
            }

        }
        return ktr;
    }

}
