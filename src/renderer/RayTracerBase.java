package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * @param scene initialize field scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * abstract function get a ray and return color
     * @param ray
     * @return color
     */
    public abstract Color traceRay(Ray ray);

}
