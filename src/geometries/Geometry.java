package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface
 *
 * @author Efrat Kugman
 */


public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;


    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public abstract Vector getNormal(Point point);

    public abstract String toString();
} // Marker inferace
