package geometries;
import primitives.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        Tube tube = new Tube(new Ray(Point.ZERO,new Vector(1,0,0)),1);
        assertEquals(tube.getNormal(new Point(5,0,1)),new Vector(0,0,1),"failed tube test");
    }
    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)} (primitives.Point)}.
     */
    @Test
    void findIntersectionsTest(){

        // ============ Equivalence Partitions Tests ==============
        //ray outside of tube, going through tube twice
        Tube tube = new Tube(new Ray(new Point(5,0,0),new Vector(1,0,0)),1);
        List<Point> result1=tube.findIntersections(new Ray(new Point(6, 5, 0),
                new Vector(0, -1, 0)));
        Point p1 = new Point(6,1,0);
        Point p2= new Point(6,-1,0);
       // assertEquals(result1,List.of(p1,p2),"failed tube test");
        //assertEquals(result1.size(),2,"failed tube test- didn't get 2 points");
        //ray inside of tube
        List<Point> result2=tube.findIntersections(new Ray(new Point(6, 1, 0),
                new Vector(0, 1, 0)));
        //assertEquals(result2,p1,"failed tube test");
        //assertEquals(result2.size(),1,"failed tube test- didn't get one point");

    }
}