package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface
 *
 * @author Efrat Kugman
 */


public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    public Color getEmission() {
        return emission;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public abstract Vector getNormal(Point point);

    public abstract String toString();
} // Marker inferace
