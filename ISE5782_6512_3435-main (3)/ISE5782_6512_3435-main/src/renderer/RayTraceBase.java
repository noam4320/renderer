package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Scene.Scene;

/**
 * Class to create a scene, and check intersections with ray
 */
public abstract class RayTraceBase {
    protected final Scene scene;

    /**
     * Constructor that gets parameter scene.
     *
     * @param scene Scene
     */
    public RayTraceBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Abstract function to return color of ray intersection
     *
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
