package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class to represent ambient color of the scene. derives from class Light
 */
public class AmbientLight extends Light {
    /**
     * Constructor that gets intensity and scaler
     *
     * @param intensity of type Color
     * @param Ka        Scaler of type Double3
     */
    public AmbientLight(Color intensity, Double3 Ka) {
        super(intensity.scale(Ka));
    }

    /**
     * ctor that gets intesnity and double scaler
     *
     * @param intensity of type olor
     * @param Ka        Scaler for double3
     */
    public AmbientLight(Color intensity, double Ka) {
        super(intensity.scale(new Double3(Ka)));
    }


    /**
     * Default constructor that sets color to black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

}
