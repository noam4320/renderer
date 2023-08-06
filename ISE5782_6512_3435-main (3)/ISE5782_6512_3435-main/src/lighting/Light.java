package lighting;

import primitives.Color;

/**
 * class to represent light
 */
abstract class Light {
    /**
     * intensity object of type Color
     */
    protected final Color intensity;

    /**
     * Constructor for Light class
     *
     * @param intensity parameter of type Color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for Light class
     *
     * @return returns intensity of type Color
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
