package primitives;

/**
 * The class represents a cartesian 3D coordinate system
 */
public class Point {
    /**
     * zero triad
     */
    public static final Point ZERO = new Point(0, 0, 0);

    protected final Double3 xyz;

    /**
     * Constructor  given triad
     *
     * @param xyz coordinate values triad
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }


    /**
     * constructor given three values
     *
     * @param x first coordinate of point
     * @param y second coordinate of point
     * @param z third coordinate of point
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    //checks if equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point other)) return false;
        return xyz.equals(other.xyz);
    }

    //hash code
    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    //to string
    @Override
    public String toString() {
        return "Point {" +
                "XYZ = " + xyz +
                '}';
    }

    /**
     * getter of x
     *
     * @return first coordinate
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * getter of y
     *
     * @return second coordinate
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * getter of z
     *
     * @return third coordinate
     */
    public double getZ() {
        return xyz.d3;
    }

    /**
     * adds a vector to current vector
     *
     * @param vector to add to current vector
     * @return result of add
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }


    /**
     * subtracts a points coordinates from point, returning a new vector
     *
     * @param point to subtract
     * @return result of subtract
     */
    public Vector subtract(Point point) {
        return new Vector(this.xyz.subtract(point.xyz));
    }

    /**
     * finds the distance between two points
     *
     * @param point to find distance between
     * @return reuslt of distance
     */
    public double distance(Point point) {

        Double3 distance = point.xyz.subtract(this.xyz);
        distance = distance.product(distance);
        return Math.sqrt(distance.d1 + distance.d2 + distance.d3);
    }

}