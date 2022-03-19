package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface
 *
 * @author Efrat Kugman
 */


public interface Geometry {
    Vector getNormal(Point point);
    String toString();
} // Marker inferace
