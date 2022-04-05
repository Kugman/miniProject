package scene;

import geometries.Intersectable;
import lighting.AmbientLight;
import geometries.Geometries;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights;

    public Scene(String name){
        this.name = name;
        geometries = new Geometries();
        background = Color.BLACK;
        ambientLight  = new AmbientLight(Color.BLACK, new Double3(0,0,0));
        lights = new LinkedList<>();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(LightSource...lights){
        for (LightSource item : lights)
            this.lights.add(item);
        return this;
    }

}
