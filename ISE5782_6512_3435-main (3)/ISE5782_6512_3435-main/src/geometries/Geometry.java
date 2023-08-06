/**
 * @authors: Daniel Yanovsky, id: 311026512
 * Noam           , id:
 */
package geometries;

import primitives.*;

/**
 * Geometry interface for use of geometry classes
 */
public abstract class Geometry extends Intersectable {
    /**
     * Variable to represent the emissions as Color - by default set to Black
     */
    protected Color emissions = Color.BLACK;

    /**
     * Variable to represent Material
     */
    protected Material material = new Material();

    /**
     * getter for Material
     *
     * @return Material object
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setter for Material
     *
     * @param material Material obj to set
     * @return the Geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter for Emissuibs
     *
     * @return emissions as Color object
     */
    public Color getEmissions() {
        return emissions;
    }

    /**
     * setter for emissions
     *
     * @param emissions emissions to be set
     * @return the Geometry itself
     */
    public Geometry setEmissions(Color emissions) {
        this.emissions = emissions;
        return this;
    }

    /**
     * returns the normal vector of a point
     *
     * @param point to find the normal of
     * @return result of getNormal
     */
    public abstract Vector getNormal(Point point);

    public Double3 getKr() {
        return this.material.kR;
    }
}