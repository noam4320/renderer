package primitives.Scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class to represent a Scene
 */
public class Scene {
    /**
     * name of scene
     **/
    public final String name;
    /**
     * color of scene, default is black
     */
    public Color background = Color.BLACK;
    /**
     * ambient light of scene
     */
    public AmbientLight ambientLight = new AmbientLight();
    /**
     * geometries of the scene
     */
    public Geometries geometries = new Geometries();

    /**
     * list to represent the light sources in the scene
     */
    public List<LightSource> lights = new LinkedList<>();

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * setter for background
     *
     * @param background Color
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter for ambient light
     *
     * @param ambientLight ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * setter for Geometries
     *
     * @param geometries geometries
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Constructor for Scene that gets name as parameter
     *
     * @param name string
     */
    public Scene(String name) {
        this.name = name;
    }

}

