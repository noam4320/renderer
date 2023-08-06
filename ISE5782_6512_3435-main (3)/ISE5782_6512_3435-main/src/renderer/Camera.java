package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import projectSettings.ProjectSettings;

import java.util.stream.IntStream;

import static primitives.Util.isZero;
import static projectSettings.ProjectSettings.multiThreading;
import static projectSettings.ProjectSettings.threadsInterval;

/**
 * Class to represent a camera in front of a view plane
 */
public class Camera {
    /**
     * location of the camera
     */
    protected Point location;
    /**
     * vector to the viewPlane
     */
    protected Vector vTo;
    /**
     * vector in the upwards direction from the camera for moving up & down on the viewPlane
     */
    protected Vector vUp;
    /**
     * vector in the right direction of the camera to move left & right on the viewPlane
     */
    protected Vector vRight;
    /**
     * height of the viewPlane
     */
    protected double viewPlaneHeight;
    /**
     * width of the viewPlane
     */
    protected double viewPlaneWidth;
    /**
     * distance between the camera and the center of the viewPlane
     */
    protected double viewPlaneDistance;
    /**
     * an imageWriter object to write images
     */
    protected ImageWriter imageWriter;
    /**
     * rayTracer object to trace rays from camera to viewPlane
     */
    protected RayTraceBase rayTracer;

    /**
     * setter
     *
     * @param rayTraceBase
     * @return this
     */
    public Camera setRayTracer(RayTraceBase rayTraceBase) {
        this.rayTracer = rayTraceBase;
        return this;
    }

    /**
     * Setter for image writer
     *
     * @param imageWriter ImageWriter
     * @return this
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Method to find color for each pixel.
     */
    public Camera renderImage() {
        if (location == null || vTo == null || vUp == null || vRight == null || imageWriter == null || rayTracer == null)
            throw new UnsupportedOperationException();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        if (multiThreading) {//using threads
            Pixel.initialize(nY, nX, threadsInterval);
            IntStream.range(0, nY).parallel().forEach(i -> {
                IntStream.range(0, nX).parallel().forEach(j -> {
                    castRay(nX, nY, i, j);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                });
            });
        } else {//without threads
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    castRay(nX, nY, i, j);
                }
            }
        }
        return this;
    }


    /**
     * method to cast ray from camera. depending on project setting - will use superSampling or not.
     *
     * @param nX int representing the x size of the viewPlane
     * @param nY int representing the y size of the viewPlane
     * @param x  int representing the x amount of pixels
     * @param y  int representing the y amount of pixels
     */
    protected void castRay(int nX, int nY, int x, int y) {
        if (ProjectSettings.SuperSampling) {
            Point pIJ = this.getCenterOfPixel(nX, nY, x, y);
            Vector vIJ = pIJ.subtract(this.location);
            SuperSampling sampling = new SuperSampling(this.location, this.vTo, this.vRight, 3, this.viewPlaneWidth / nX, this.viewPlaneHeight / nY, vIJ, rayTracer);
            imageWriter.writePixel(x, y, sampling.finalColor(x, y));
        } else {
            imageWriter.writePixel(x, y, rayTracer.traceRay(constructRayThroughPixel(nX, nY, x, y)));
        }

    }

    /**
     * @return location of the camera
     */
    public Point getLocation() {
        return location;
    }

    /**
     * @return Vector vUp of the camera
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * @return Vector vRight of the camera
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * @return Vector vTo of the camera
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * @return Height of the view plane (Double)
     */
    public double getViewPlaneHeight() {
        return viewPlaneHeight;
    }

    /**
     * @return Width of the view plane (Double)
     */
    public double getViewPlaneWidth() {
        return viewPlaneWidth;
    }

    /**
     * @return Distance from the camera of the view plane (Double)
     */
    public double getViewPlaneDistance() {
        return viewPlaneDistance;
    }

    /**
     * Constructor that gets 3 parameters,
     * and makes sure the vUp vector and the vTo vector are not orthogonal
     *
     * @param location location of the camera
     * @param vUp      Vector vUp of the camera
     * @param vTo      Vector vDown of the camera
     */
    public Camera(Point location, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("Vectors are not orthogonal to each other");
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(vUp);
    }

    /**
     * Method to set the height and width of the view plane
     *
     * @param width  width of the view plane (Double)
     * @param height height of the view plane (Double)
     * @return The calling Camera object
     */
    public Camera setVPSize(double width, double height) {
        this.viewPlaneWidth = width;
        this.viewPlaneHeight = height;
        return this;
    }

    /**
     * Method to set the distance of the view plane from the camera
     *
     * @param distance distance of the view plane from the camera (Double)
     * @return The calling Camera object
     */
    public Camera setVPDistance(double distance) {
        this.viewPlaneDistance = distance;
        return this;
    }

    /**
     * Method to construct a ray from the camera through the view plane
     *
     * @param nX int
     * @param nY int
     * @param j  int
     * @param i  int
     * @return result of constructRayThroughPixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pIJ = getCenterOfPixel(nX, nY, j, i);
        Vector vIJ = pIJ.subtract(this.location);
        Ray ray = new Ray(location, vIJ);
        return ray;
    }

    /**
     * method to get center of pixel cordinates
     *
     * @param nX number of x pixels
     * @param nY number of y pixels
     * @param j  int
     * @param i  int
     * @return
     */
    public Point getCenterOfPixel(int nX, int nY, int j, int i) {
        Point pCenter = this.getLocation().add(this.vTo.scale(viewPlaneDistance));
        double rX = this.viewPlaneWidth / nX;
        double rY = this.viewPlaneHeight / nY;
        double yI = -(i - ((nY - 1) / 2.0)) * rY;
        double xJ = (j - ((nX - 1) / 2.0)) * rX;
        Point pIJ = pCenter;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        return pIJ;
    }

    /**
     * Method to paint on image grid
     *
     * @param interval int
     * @param color    Color
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new UnsupportedOperationException();
        Color color1 = new Color(3, 4, 300);
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color1);
            }
        }
    }

    /**
     * opens an image, and makes sure it isn't null
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new UnsupportedOperationException();
        imageWriter.writeToImage();

    }

}

