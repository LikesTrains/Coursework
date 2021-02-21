package tests;

import org.junit.Test;



import shapes.Line;
import shapes.Point;
import shapes.ShapeException;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LineTest {

    @Test
    public void testValidConstruction() throws Exception {
        Point p1 = new Point(1,2);
        Point p2 = new Point(4, 10);

        Line myLine = new Line(p1, p2);
        assertSame(p1, myLine.getPoints().get(0));
        assertSame(p2, myLine.getPoints().get(1));

        p1 = new Point(1.4,2.5);
        p2 = new Point(4.6, 10.7);
        myLine = new Line(p1, p2);
        assertSame(p1, myLine.getPoints().get(0));
        assertSame(p2, myLine.getPoints().get(1));

        myLine = new Line(1, 3.33, 4.444, 5.5555);
        assertEquals(1, myLine.getPoints().get(0).getX(), 0);
        assertEquals(3.33, myLine.getPoints().get(0).getY(), 0);
        assertEquals(4.444, myLine.getPoints().get(1).getX(), 0);
        assertEquals(5.5555, myLine.getPoints().get(1).getY(), 0);
    }

    @Test
    public void testInvalidConstruction() throws ShapeException {
        Point p1 = new Point(1,2);
        Point p2 = new Point(4, 10);

        try {
            new Line(null, p2);
            fail("Expected exception not thrown for when the first parameter is null");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
        }

        try {
            new Line(p1, null);
            fail("Expected exception not thrown for when the second parameter is null");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
        }

        try {
            new Line(Double.POSITIVE_INFINITY, 2, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }

        try {
            new Line(1, Double.POSITIVE_INFINITY, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }

        try {
            new Line(1, 2, Double.POSITIVE_INFINITY, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }

        try {
            new Line(1, 2, 3, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }

        try {
            new Line(p1, p1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }

        try {
            new Line(1,2, 1,2);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
    }

    @Test
    public void testMove() throws ShapeException {
        Line myLine = new Line(1, 2, 4, 10);

        myLine.move(3, 4);
        assertEquals(4, myLine.getPoints().get(0).getX(), 0);
        assertEquals(6, myLine.getPoints().get(0).getY(), 0);
        assertEquals(7, myLine.getPoints().get(1).getX(), 0);
        assertEquals(14, myLine.getPoints().get(1).getY(), 0);

        myLine.move(.4321, .7654);
        assertEquals(4.4321, myLine.getPoints().get(0).getX(), 0);
        assertEquals(6.7654, myLine.getPoints().get(0).getY(), 0);
        assertEquals(7.4321, myLine.getPoints().get(1).getX(), 0);
        assertEquals(14.7654, myLine.getPoints().get(1).getY(), 0);

        myLine.move(-0.4321, -0.7654);
        assertEquals(4, myLine.getPoints().get(0).getX(), 0);
        assertEquals(6, myLine.getPoints().get(0).getY(), 0);
        assertEquals(7, myLine.getPoints().get(1).getX(), 0);
        assertEquals(14, myLine.getPoints().get(1).getY(), 0);
    }

    @Test
    public void testComputeLength() throws ShapeException {

        Line myLine = new Line(1, 2, 4, 10);
        assertEquals(8.544, myLine.computeLength(), 0.001);

        myLine = new Line(1, 2, 1, 3);
        assertEquals(Math.sqrt(1), myLine.computeLength(), 0.001);

        myLine = new Line(3, -2, -4, 10);
        assertEquals(13.892, myLine.computeLength(), 0.001);
    }

    @Test
    public void testComputeSlope() throws ShapeException {
        Line myLine = new Line(2, 2, 4, 10);
        assertEquals(4, myLine.computeSlope(), 0.1);

        myLine = new Line(2, 2, 4, 10);
        assertEquals(4, myLine.computeSlope(), 0.1);

        myLine = new Line(2, 2, 2, 4);
        assertEquals(Double.POSITIVE_INFINITY, myLine.computeSlope(), 0.1);

        myLine = new Line(2, 2, 4, 2);
        assertEquals(0, myLine.computeSlope(), 0.1);

        myLine = new Line(4, 2, 2, 2);
        assertEquals(0, myLine.computeSlope(), 0.1);
    }
    @Test
    public void testToString() throws Exception{
    	Line l1 = new Line(new Point(1,6),new Point(2,3));    	
    	assertEquals("Line,1.0,6.0,2.0,3.0",l1.toString());
    	
    	Line l2 = new Line(new Point(-3,12),new Point(10,-300));    	
    	assertEquals("Line,-3.0,12.0,10.0,-300.0",l2.toString());
    }
    
    @Test
    public void testRenderLine() throws ShapeException, IOException {
        // Setup
        Line testShape = new Line(0, 50, 100,70);
        BufferedImage bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/line1.png")));
        
        // Setup
        testShape = new Line(0, 0, 100,100);
        bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/line2.png")));
    }
}