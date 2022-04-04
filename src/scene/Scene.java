package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import primitives.Double3;

public class Scene {

    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public Scene(String name){
        this.name = name;
        geometries = new Geometries();
        background = Color.BLACK;
        ambientLight  = new AmbientLight(Color.BLACK, new Double3(0,0,0));
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



}
