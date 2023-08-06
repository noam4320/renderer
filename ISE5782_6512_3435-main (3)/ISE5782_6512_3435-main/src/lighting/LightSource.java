package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface to represent light sources
 */
public interface LightSource {
    /**
     * method to get the intensity at a given point
     *
     * @param p the point to calculate the light intensity
     * @return the light intensity
     */
    Color getIntensity(Point p);

    /**
     * method to get the light direction to a given point
     *
     * @param p the point to get the light direction to
     * @return normalized vector of direction
     */
    public Vector getL(Point p);

    /**
     * calculates distance between lightsource and point
     *
     * @param point distance from
     * @return distance
     */
    public double getDistance(Point point);


}
