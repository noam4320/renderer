package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * class to represent a spotLight
 */
public class SpotLight extends PointLight {
    /**
     * Variable direction as Vector object
     */
    private final Vector direction;

    /**
     * constructor for SpotLight
     *
     * @param color  color of the light
     * @param point  beginning point of the light
     * @param vector the direction of the light
     */
    public SpotLight(Color color, Point point, Vector vector) {
        super(color, point);
        this.direction = vector.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        double factor = alignZero(direction.dotProduct(getL(point)));
        return factor <= 0 ? Color.BLACK : super.getIntensity(point).scale((Math.max(0, direction.dotProduct(getL(point)))));
    }

}
