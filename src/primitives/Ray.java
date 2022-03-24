package primitives;

/**
 * @author Efrat Kugman
 * /**
 *  * "Half-straight - all the points on the line that are on one side of the point given on the line called the beginning / beginning / beginning of the fund):
 */
public class Ray {

    private final Point startPoint;
    private final Vector directionVector;

    public Ray(Point startPoint, Vector directionVector) {
        this.startPoint = startPoint;
        this.directionVector = directionVector.normalize();
    }

    public Point getPoint(double t){
        return startPoint.add(directionVector.scale(t));
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Vector getDirectionVector(){
        return directionVector;
    }

    public String toString(){
        return "Ray: {" +
                startPoint.toString()       +
                directionVector.toString()  +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return startPoint.equals(ray.startPoint) && directionVector.equals(ray.directionVector);
    }
}
