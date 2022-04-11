package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;
import static primitives.Util.alignZero;

public class SpotLight extends PointLight{

    private final Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p){
        double projection = direction.dotProduct(getL(p));
        double factor = alignZero(max(0, projection));
        return super.getIntensity(p).scale(factor);
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(point);
    }

}
