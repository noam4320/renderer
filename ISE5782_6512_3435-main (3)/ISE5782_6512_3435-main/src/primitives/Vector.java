/**
 * @authors: Daniel Yanovsky, id: 311026512
 * Noam Bleiberg, id: 334063435
 */
package primitives;

/**
 * A class to represent a vector on a plane. it doesn't have variables.
 * it extends the Point class, and implements the methods in a vector context.
 */
public class Vector extends Point {
    /**
     * constructor given triad
     *
     * @param xyz (Double3)
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Error! Zero vector");
    }

    /**
     * constructor that gets 3 double values
     *
     * @param x x axis value (double)
     * @param y y axis value (double)
     * @param z z axis value (double)
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector)) return false;
        return super.equals(obj);
    }

    /**
     * override of toString method to print a vector
     */
    @Override
    public String toString() {
        return "->" + super.toString();
    }

    /**
     * adds vector to a vector
     *
     * @param vector to add to this (vector)
     * @return result of add
     */

    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * @param scaler (double)
     * @return
     */
    public Vector scale(double scaler) {
        return new Vector(xyz.scale(scaler));
    }

    /**
     * gets vector, and calculates the cross vector between this and the given vector
     *
     * @param mult vector to multiply (vector)
     * @return result of the mult
     */

    public Vector crossProduct(Vector mult) {
        return new Vector(
                this.xyz.d2 * mult.xyz.d3 - this.xyz.d3 * mult.xyz.d2,
                this.xyz.d3 * mult.xyz.d1 - this.xyz.d1 * mult.xyz.d3,
                this.xyz.d1 * mult.xyz.d2 - this.xyz.d2 * mult.xyz.d1);
    }

    /**
     * calculates length of vector squared
     *
     * @return result of lengthSquared
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * calculates length of vector
     *
     * @return result of length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * calculates the normal vector of given vector
     *
     * @return result of normalize
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(this.length()));
    }

    /**
     * calculates dot product with another vector
     *
     * @param vectorProduct vector to calculate dot product with (vector)
     * @return result of dotProduct
     */
    public double dotProduct(Vector vectorProduct) {
        return this.xyz.d1 * vectorProduct.xyz.d1 +
                this.xyz.d2 * vectorProduct.xyz.d2 +
                this.xyz.d3 * vectorProduct.xyz.d3;
    }

}
