package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class to represent Point light
 */
public class PointLight extends Light implements LightSource {
    /**
     * Point that represents the location
     */
    private final Point position;

    /**
     * double that represents the kC parameter by Phong model
     */
    private double kC = 1d;

    /**
     * double that represents the kL parameter by Phong model
     */
    private double kL = 0d;

    /**
     * double that represents the kQ parameter by Phong model
     */
    private double kQ = 0d;

    /**
     * constructor
     *
     * @param intensity Color
     * @param position  Point
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point point) {
        double denominator, distance;
        distance = position.distance(point);
        denominator = kC + kL * distance + kQ * distance * distance;
        return getIntensity().scale(1 / (denominator));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * setter for Kc
     *
     * @param kC of type double
     * @return the calling PointLight itself
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter for kL that gets a double
     *
     * @param kL double
     * @return PointLight
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * constructor for kQ that gets a double
     *
     * @param kQ double
     * @return PointLight
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }
}
