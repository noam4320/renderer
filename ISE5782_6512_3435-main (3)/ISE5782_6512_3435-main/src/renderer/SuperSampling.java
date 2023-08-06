package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static projectSettings.ProjectSettings.superSamplingResolution;

/**
 * class to represent enhancements to the image by using super-sampling methods.
 * <p>
 * currently the class adds anti-aliasing to the rendering.
 * <p>
 * other super-sampling improvements should be added here.
 *
 * @authors: Daniel Yanovsky & Noam Bleiberg
 */
public class SuperSampling {
    /**
     * variable to represent the width of a pixel
     */
    private final double widthOfPixel;
    /**
     * variable to represent the height of a pixel
     */
    private final double heightOfPixel;
    /**
     * location of the camera
     */
    private final Point location;
    /**
     * vector to the view plane from the camera
     */
    Vector vTo;
    /**
     * vector upward from the camera for moving up & down on the view plane
     */
    Vector vUp;
    /**
     * vector right from the camera for moving left & right on the view plane
     */
    Vector vRight;
    /**
     * vector to the center of pixel on the viewPlane
     */
    Vector vIJ;
    /**
     * object of Ray Tracer to trace rays to the geometries
     */
    RayTraceBase rayTracer;

    /**
     * a variable to represent the super sampling resolution. to turn off super sampling -> set resolution to 1.
     */
    private int resolution = superSamplingResolution;
    /**
     * a matrix of colors for using in adaptive super sampling
     */
    private Color colorArray[][] = new Color[resolution][resolution];

    /**
     * defualt constructor for superSampling
     *
     * @param location      location of the camera
     * @param vTo           vector to the viewPlane
     * @param vUp           vector up
     * @param resolution    resolution of pixels
     * @param widthOfPixel  width of pixel
     * @param heightOfPixel height of pixel
     * @param vIJ           vector to center of pixel
     * @param rayTracer     rayTracer obj for use inside the class
     */
    public SuperSampling(Point location, Vector vTo, Vector vUp, int resolution, double widthOfPixel, double heightOfPixel, Vector vIJ, RayTraceBase rayTracer) {
        this.location = location;
        this.vTo = vTo;
        this.vUp = vUp;
        this.widthOfPixel = widthOfPixel;
        this.heightOfPixel = heightOfPixel;
        this.rayTracer = rayTracer;
        this.vRight = this.vTo.crossProduct(vUp);
        this.vIJ = vIJ;
        //this.resolution = resolution;
    }

    /**
     * setter for the resolution for use in test cases
     *
     * @param resolution the resolution
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    /**
     * method to construct multiple rays around a pixel (a beam)
     *
     * @return a list of the rays in the beam
     */
    private Ray constructRaysThroughPixel(int i, int j) {
        if (i == 0 && j != 0) {
            return new Ray(location, vIJ.add(vUp.scale((double) j / resolution * heightOfPixel * 2)));
        }
        if (j == 0 && i != 0) {
            return new Ray(location, vIJ.add(vRight.scale((double) i / resolution * widthOfPixel * 2)));
        }
        if (i == 0 && j == 0) {
            return new Ray(location, vIJ);
        }
        return (new Ray(location, vIJ.add(vRight.scale((double) i / resolution * widthOfPixel * 2).add(vUp.scale((double) j / resolution * heightOfPixel * 2)))));
    }

    /**
     * method that gets a list of colors, and calculates the average RGB color
     *
     * @param colors a list of colors
     * @return color
     */
    private Color averageOfColors(List<Color> colors) {
        Color total = new Color(0, 0, 0);
        for (Color color : colors) {
            total = total.add(color);
        }
        return total.reduce(colors.size());
    }

    /**
     * method to calculate the color of pixel square using recursive method for adaptive super sampling
     *
     * @param i0 row of coordinate 0
     * @param j0 col of coordinate 0
     * @param i1 row of coordinate 1
     * @param j1 col of coordinate 1
     * @return average of the colors in the square
     */
    private Color colorOfSquare(int i0, int j0, int i1, int j1) {
        Color a;
        Color b;
        Color c;
        Color d;
        a = checkColorInMatrix(i0, j0);
        b = checkColorInMatrix(i0, j1);
        c = checkColorInMatrix(i1, j0);
        d = checkColorInMatrix(i1, j1);
        if (a.equals(b) && b.equals(c) && c.equals(d))
            return a;
        int middleJ = (j0 + j1) / 2; //the middle column
        int middleI = (i0 + i1) / 2; // the middle row
        if (i1 - i0 <= 1)
            return averageOfColors(List.of(a, b, c, d));
        return averageOfColors(List.of(
                colorOfSquare(i0, j0, middleI, middleJ),
                colorOfSquare(i0, middleJ, middleI, j1),
                colorOfSquare(middleI, j0, i1, middleJ),
                colorOfSquare(middleI, middleJ, i1, j1)));
    }

    /**
     * method to check if the current color was already calculated in the matrix. and if not -> calculate it
     *
     * @param i0 row coordinate
     * @param j0 col coordinate
     * @return Color of the coordinate
     */
    private Color checkColorInMatrix(int i0, int j0) {
        i0 += resolution / 2;
        j0 += resolution / 2;
        if (colorArray[i0][j0] != null)
            return colorArray[i0][j0];
        else {
            Color color = rayTracer.traceRay(constructRaysThroughPixel(i0, j0));
            colorArray[i0][j0] = color;
            return color;
        }
    }

    /**
     * method to calculate the color of the pixel
     *
     * @param x row coordinate of pixel
     * @param y col coordinate of pixel
     * @return color of pixel
     */
    public Color finalColor(int x, int y) {
        return colorOfSquare(-resolution / 2, -resolution / 2, resolution / 2, resolution / 2);
    }


}
