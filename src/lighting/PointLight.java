package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC, kL, kQ;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        double dSquared = d*d;
        return (this.getIntensity().scale(1/(kC + kL * d + kQ * dSquared)));
    }

    @Override
    public Vector getL(Point p) {
        if (p.equals(position)) {
            return null;
        }
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    //-------------Setters-------------------------------//

    public PointLight setPosition(Point position){
        this.position = position;
        return this;
    }

    public PointLight setKC(double kC){
        this.kC = kC;
        return this;
    }

    public PointLight setKL(double kL){
        this.kL = kL;
        return this;
    }

    public PointLight setKQ(double kQ){
        this.kQ = kQ;
        return this;
    }

}
