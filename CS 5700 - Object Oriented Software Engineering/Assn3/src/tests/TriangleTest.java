package tests;

import org.junit.Test;

import shapes.Point;
import shapes.ShapeException;
import shapes.Triangle;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TriangleTest {

    @Test
    public void testValidConstruction() throws Exception {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	
    	
    	Triangle myTriangle = new Triangle(p1,p2,p3);
    	assertSame(p1, myTriangle.getPoints().get(0));
    	assertSame(p2, myTriangle.getPoints().get(1));
    	assertSame(p3, myTriangle.getPoints().get(2));
    	
    	p1 = new Point(0,3);
    	p2 = new Point(2,5);
    	p3 = new Point(6,1);
    	myTriangle = new Triangle(p1,p2,p3);
    	assertSame(p1, myTriangle.getPoints().get(0));
    	assertSame(p2, myTriangle.getPoints().get(1));
    	assertSame(p3, myTriangle.getPoints().get(2));
    	
    	myTriangle = new Triangle(3.8, 6.4, 1.2, 0.6, 9.9, 7.8);
    	assertEquals(3.8,myTriangle.getPoints().get(0).getX(),0);
    	assertEquals(6.4,myTriangle.getPoints().get(0).getY(),0);
    	assertEquals(1.2,myTriangle.getPoints().get(1).getX(),0);
    	assertEquals(0.6,myTriangle.getPoints().get(1).getY(),0);
    	assertEquals(9.9,myTriangle.getPoints().get(2).getX(),0);
    	assertEquals(7.8,myTriangle.getPoints().get(2).getY(),0);    	
    }

    @Test
    public void testInvalidConstruction() throws ShapeException {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	
    	try {
    		new Triangle(null, p2, p3);
    		fail("Expected exception not thrown for when the first parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Triangle(p1, null, p3);
    		fail("Expected exception not thrown for when the second parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Triangle(p1, p2, null);
    		fail("Expected exception not thrown for when the third parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
            new Triangle(Double.POSITIVE_INFINITY, 2, 3, 4, 5, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Triangle(1, Double.POSITIVE_INFINITY, 3, 4, 5, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Triangle(1, 2, Double.POSITIVE_INFINITY, 4, 5, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Triangle(1, 2, 3, Double.POSITIVE_INFINITY, 5, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Triangle(1, 2, 3, 4, Double.POSITIVE_INFINITY, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Triangle(1, 2, 3, 4, 5, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
        try {
            new Triangle(p1, p1, p2);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Triangle(p1, p2, p1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Triangle(p2, p1, p1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Triangle(1,2, 1,2, 2,5);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Triangle(1,2, 2,5, 1,2);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Triangle(2,5, 1,2, 1,2);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Triangle(1,1, 2,2, 3,3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("All 3 points must not be in the same line", e.getMessage());
        }
        
        p1 = new Point(0,0);
        p2 = new Point(2,2);
        p3 = new Point(4,4);
        
        try {
            new Triangle(p1, p2, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("All 3 points must not be in the same line", e.getMessage());
        }
    }
    @Test
    public void testMove() throws ShapeException {
        Triangle myTriangle = new Triangle(0, 1, 3, 6, 9, 10);

        myTriangle.move(3, 4);
        assertEquals(3, myTriangle.getPoints().get(0).getX(), 0);
        assertEquals(5, myTriangle.getPoints().get(0).getY(), 0);
        assertEquals(6, myTriangle.getPoints().get(1).getX(), 0);
        assertEquals(10, myTriangle.getPoints().get(1).getY(), 0);
        assertEquals(12, myTriangle.getPoints().get(2).getX(), 0);
        assertEquals(14, myTriangle.getPoints().get(2).getY(), 0);

        myTriangle.move(.4321, .7654);
        assertEquals(3.4321, myTriangle.getPoints().get(0).getX(), 0);
        assertEquals(5.7654, myTriangle.getPoints().get(0).getY(), 0);
        assertEquals(6.4321, myTriangle.getPoints().get(1).getX(), 0);
        assertEquals(10.7654, myTriangle.getPoints().get(1).getY(), 0);
        assertEquals(12.4321, myTriangle.getPoints().get(2).getX(), 0);
        assertEquals(14.7654, myTriangle.getPoints().get(2).getY(), 0);

        myTriangle.move(-0.4321, -0.7654);
        assertEquals(3, myTriangle.getPoints().get(0).getX(), 0);
        assertEquals(5, myTriangle.getPoints().get(0).getY(), 0);
        assertEquals(6, myTriangle.getPoints().get(1).getX(), 0);
        assertEquals(10, myTriangle.getPoints().get(1).getY(), 0);
        assertEquals(12, myTriangle.getPoints().get(2).getX(), 0);
        assertEquals(14, myTriangle.getPoints().get(2).getY(), 0);
    }
    
    @Test
    public void testComputeArea() throws ShapeException {
        Triangle myTriangle = new Triangle(0, 0, 0, 5, 5, 0);
        assertEquals(12.5, myTriangle.getArea(),0.1);
        
        myTriangle = new Triangle(-2,0,2,0,0,3);
        assertEquals(6, myTriangle.getArea(),0.1);
        
        myTriangle = new Triangle(-5,6,3,6,-1,-2);
        assertEquals(32, myTriangle.getArea(),0.1);
        
        myTriangle = new Triangle(0,0,0,1,1,0);
        assertEquals(0.5, myTriangle.getArea(),0.1);
    }
    @Test
    public void testToString() throws Exception{
    	Triangle t1 = new Triangle(new Point(1,6),new Point(2,3), new Point(2, 5));    	
    	assertEquals("Triangle,1.0,6.0,2.0,3.0,2.0,5.0",t1.toString());
    	
    	Triangle t2 = new Triangle(new Point(-3,12),new Point(10,-300), new Point(420, 69));    	
    	assertEquals("Triangle,-3.0,12.0,10.0,-300.0,420.0,69.0",t2.toString());
    }
    @Test
    public void testRenderTriangle() throws ShapeException, IOException {
        // Setup
        Triangle testShape = new Triangle(10, 10, 80, 40, 30, 60);
        BufferedImage bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);
        

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/triangle1.png")));
        
        // Setup
        testShape = new Triangle(30, 60, 80, 60, 12, 90);
        bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/triangle2.png")));
    }
}
