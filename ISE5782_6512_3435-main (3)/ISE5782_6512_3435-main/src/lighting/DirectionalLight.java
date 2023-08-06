package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * class to represent directional light
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * Variable direction as Vector
     */
    private Vector direction;

    /**
     * Constructor for Light class
     *
     * @param intensity parameter of type Color
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        return this.intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
