package unittests;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * testing integrations
 *
 * Authors: Daniel Yanovsky & Noam Bleiberg
 */
public class IntegrationTests {
    /**
     * test method for getting the correct number of intersections from a ray casted from camera to a geometry
     * through the view plane.
     * @param camera the casting camera
     * @param geometry the target geometry
     * @param expected the expected amount of intersections
     */
    private void assertCountIntersections(Camera camera, Intersectable geometry, int expected) {
        int count = 0;
        camera.setVPSize(3,3).setVPDistance(1);
        for(int i=0; i<3; ++i)
            for(int j=0; j<3; ++j) {
                var intersections = geometry.findIntersections(camera.constructRayThroughPixel(3,3,j,i));
                count += intersections == null ? 0 : intersections.size();
            }
        assertEquals(expected, count, "Wrong amount of intersections");
    }

    /**
     * Test method for camera and sphere intersections.
     */
    @Test
    void CameraSphereIntersections() {
        Camera camera1 = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1);
        Camera camera2 = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,-1,0));

        //TC01: small sphere - 2 intersections
        assertCountIntersections(camera1, new Sphere(1,new Point(0,0,-3)), 2);
        //TC02: Big sphere - 18 intersections
        assertCountIntersections(camera2, new Sphere(2.5, new Point(0,0,-2.5)), 18);
        //TC03: Medium sphere - 10 intersections
        assertCountIntersections(camera2, new Sphere(2,new Point(0,0,-2) ), 10);
        //TC04: Inside sphere - 9 intersections
        assertCountIntersections(camera2, new Sphere(4,new Point(0,0,-1)), 9);
        //TC05: Beyond sphere - 0 intersections
        assertCountIntersections(camera1, new Sphere(0.5,new Point(0,0,1)), 0);
    }

    /**
     * Test method for camera and plane intersections.
     */
    @Test
    void CameraPlaneIntersections() {
        Camera camera = new Camera(Point.ZERO, new Vector(0,0,-1), new Vector(0,-1,0));

        //TC01
        assertCountIntersections(camera, new Plane(new Point(0,0,-5), new Vector(0,0,1)), 9);
        //TC02
        assertCountIntersections(camera, new Plane(new Point(0,0,-5), new Vector(0,1,2)), 9);
        //TC03
        assertCountIntersections(camera, new Plane(new Point(0,0,-5), new Vector(0,1,1)), 6);

    }

    /**
     * Test method for camera and triangle intersections.
     */
    @Test
    void CameraTriangleIntersections() {
        Camera camera = new Camera(Point.ZERO, new Vector(0,0,-1), new Vector(0,-1,0));

        //TC01
        assertCountIntersections(camera, new Triangle(new Point(1,1,-2), new Point(-1,1,-2), new Point(0,-1,-2)),1);
        //TC02
        assertCountIntersections(camera, new Triangle(new Point(1,1,-2), new Point(-1,1,-2), new Point(0,-20,-2)),2);
    }

}
