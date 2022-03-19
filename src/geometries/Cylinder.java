package geometries;

import primitives.Ray;

public class Cylinder extends Tube{
    protected double height;

    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }

    public double getHeight(){ return height; }

    @Override
    public String toString(){
        return "Cylinder {"     +
                ray.toString()  + ", "      +
                "radius: "      + radius    + ", " +
                "height: "      + height    +
                "}";
    }
}
