/**
 * @authors: Daniel Yanovsky, id: 311026512
 * Noam Bleiberg, id: 334063435
 */
package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class to represent a Cylinder by extending Sphare class and adding height<double>
 */
public class Cylinder extends Sphere {
    /**
     * variable to represent height of the Cylinder
     */
    double height;

    /**
     * constructor for Cylinder
     *
     * @param center center of the Cylinder
     * @param radius radius of the Cylinder
     * @param height height of the Cylinder
     */
    public Cylinder(Point center, double radius, double height) {
        super(radius, center);
        this.height = height;
    }

    /**
     * getter for the Cylinder height
     *
     * @return double height
     */
    public double getHeight() {
        return height;
    }


    /**
     * method to get the normal to a Cylinder
     *
     * @param pointer the point at where to get the normal
     * @return the normal vector to the Cylinder at the given point
     */
    public Vector getNormal(Point pointer) {
        return null;
    }
}
