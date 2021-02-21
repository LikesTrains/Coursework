package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import shapes.Point;
import shapes.Rectangle;
import shapes.ShapeException;

class RectangleTest {

    @Test
    public void testValidConstruction() throws Exception {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	Point p4 = new Point(5,5);
    	
    	
    	Rectangle myRectangle = new Rectangle(p1,p2,p3,p4);
    	assertSame(p1, myRectangle.getPoints().get(0));
    	assertSame(p2, myRectangle.getPoints().get(1));
    	assertSame(p3, myRectangle.getPoints().get(2));
    	assertSame(p4, myRectangle.getPoints().get(3));
    	
    	p1 = new Point(-2,1);
    	p2 = new Point(-2,5);
    	p3 = new Point(2,1);
    	p4 = new Point(2,5);
    	myRectangle = new Rectangle(p1,p2,p3,p4);
    	assertSame(p1, myRectangle.getPoints().get(0));
    	assertSame(p2, myRectangle.getPoints().get(1));
    	assertSame(p3, myRectangle.getPoints().get(2));
    	assertSame(p4, myRectangle.getPoints().get(3));
    	
    	myRectangle = new Rectangle(-4.5, -5, -4.5, 6, 9.6, -5, 9.6, 6);
    	assertEquals(-4.5,myRectangle.getPoints().get(0).getX(),0);
    	assertEquals(-5,myRectangle.getPoints().get(0).getY(),0);
    	assertEquals(-4.5,myRectangle.getPoints().get(1).getX(),0);
    	assertEquals(6,myRectangle.getPoints().get(1).getY(),0);
    	assertEquals(9.6,myRectangle.getPoints().get(2).getX(),0);
    	assertEquals(-5,myRectangle.getPoints().get(2).getY(),0);
    	assertEquals(9.6,myRectangle.getPoints().get(3).getX(),0);
    	assertEquals(6,myRectangle.getPoints().get(3).getY(),0);    
    }
    @Test
    public void testInvalidConstruction() throws ShapeException {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	Point p4 = new Point(5,5);
    	
    	try {
    		new Rectangle(null, p2, p3, p4);
    		fail("Expected exception not thrown for when the first parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Rectangle(p1, null, p3, p4);
    		fail("Expected exception not thrown for when the second parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Rectangle(p1, p2, null, p4);
    		fail("Expected exception not thrown for when the third parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Rectangle(p1, p2, p3, null);
    		fail("Expected exception not thrown for when the fourth parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	
    	try {
            new Rectangle(Double.POSITIVE_INFINITY, 2, 3, 4, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, Double.POSITIVE_INFINITY, 3, 4, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, 2, Double.POSITIVE_INFINITY, 4, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, 2, 3, Double.POSITIVE_INFINITY, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, 2, 3, 4, Double.POSITIVE_INFINITY, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, 2, 3, 4, 5, Double.POSITIVE_INFINITY, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, 2, 3, 4, 5, 6, Double.POSITIVE_INFINITY, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Rectangle(1, 2, 3, 4, 5, 5, 7, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
        try {
            new Rectangle(p1, p1, p2, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
        	new Rectangle(p1, p2, p2, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
        	new Rectangle(p1, p2, p3, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
        	new Rectangle(p1, p2, p3, p1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Rectangle(1,2, 1,2, 2,5 ,3, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
            new Rectangle(1,2, 2,5, 2,5 ,3, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
            new Rectangle(1,2, 2,5, 3, 6 ,3, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
            new Rectangle(1,2, 2,5, 3, 6 ,1,2);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        
        try {
            new Rectangle(1,1, 2,2, 3,3, 4,5);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Adjacent points must not be in the same line", e.getMessage());
        }
        try {
            new Rectangle(1,2, 2,2, 3,3, 4,4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Adjacent points must not be in the same line", e.getMessage());
        }
        try {
            new Rectangle(1,1, 2,3, 3,3, 4,4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Adjacent points must not be in the same line", e.getMessage());
        }
        
        p1 = new Point(0,0);
        p2 = new Point(0,2);
        p3 = new Point(2,2);
        p4 = new Point(3,0);
        
        try {
            new Rectangle(p1, p2, p3,p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid rectangle, inconsistent distance from vertex to center", e.getMessage());
        }
        
        p1 = new Point(0,0);
        p2 = new Point(0,2);
        p3 = new Point(2,2);
        p4 = new Point(3,0);
        
        try {
            new Rectangle(p1, p2, p3,p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid rectangle, inconsistent distance from vertex to center", e.getMessage());
        }
        
        p1 = new Point(-1,-1);
        p2 = new Point(-1,1);
        p3 = new Point(1,2);
        p4 = new Point(3,0);
        
        try {
            new Rectangle(p1, p2, p3,p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid rectangle, inconsistent distance from vertex to center", e.getMessage());
        }
    }
    
    @Test
    public void testMove() throws ShapeException {
        Rectangle myRectangle = new Rectangle(0, 0, 0, 5, 5, 0, 5, 5);

        myRectangle.move(3, 4);
        assertEquals(3, myRectangle.getPoints().get(0).getX(), 0);
        assertEquals(4, myRectangle.getPoints().get(0).getY(), 0);
        assertEquals(3, myRectangle.getPoints().get(1).getX(), 0);
        assertEquals(9, myRectangle.getPoints().get(1).getY(), 0);
        assertEquals(8, myRectangle.getPoints().get(2).getX(), 0);
        assertEquals(4, myRectangle.getPoints().get(2).getY(), 0);
        assertEquals(8, myRectangle.getPoints().get(3).getX(), 0);
        assertEquals(9, myRectangle.getPoints().get(3).getY(), 0);

        myRectangle.move(.4321, .7654);
        assertEquals(3.4321, myRectangle.getPoints().get(0).getX(), 0.1);
        assertEquals(4.7654, myRectangle.getPoints().get(0).getY(), 0.1);
        assertEquals(3.4321, myRectangle.getPoints().get(1).getX(), 0.1);
        assertEquals(9.7654, myRectangle.getPoints().get(1).getY(), 0.1);
        assertEquals(8.4321, myRectangle.getPoints().get(2).getX(), 0.1);
        assertEquals(4.7654, myRectangle.getPoints().get(2).getY(), 0.1);
        assertEquals(8.4321, myRectangle.getPoints().get(3).getX(), 0.1);
        assertEquals(9.7654, myRectangle.getPoints().get(3).getY(), 0.1);

        myRectangle.move(-0.4321, -0.7654);
        assertEquals(3, myRectangle.getPoints().get(0).getX(), 0.1);
        assertEquals(4, myRectangle.getPoints().get(0).getY(), 0.1);
        assertEquals(3, myRectangle.getPoints().get(1).getX(), 0.1);
        assertEquals(9, myRectangle.getPoints().get(1).getY(), 0.1);
        assertEquals(8, myRectangle.getPoints().get(2).getX(), 0.1);
        assertEquals(4, myRectangle.getPoints().get(2).getY(), 0.1);
        assertEquals(8, myRectangle.getPoints().get(3).getX(), 0.1);
        assertEquals(9, myRectangle.getPoints().get(3).getY(), 0.1);
    }
    
    @Test
    public void testComputeArea() throws ShapeException {
        Rectangle myRectangle = new Rectangle(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(25, myRectangle.getArea(),0.1);
        
        myRectangle = new Rectangle(5, 0, 0, 0, 0, 5, 5, 5);
        assertEquals(25, myRectangle.getArea(),0.1);
        
        myRectangle = new Rectangle(-2,0,2,0,-2,3, 2, 3);
        assertEquals(12, myRectangle.getArea(),0.1);
        
        myRectangle = new Rectangle(-5,6,3,6,-5,-2,3,-2);
        assertEquals(64, myRectangle.getArea(),0.1);
        
        myRectangle = new Rectangle(0,0,0,1,1,0, 1, 1);
        assertEquals(1, myRectangle.getArea(),0.1);
        
        myRectangle = new Rectangle(0,-1,0,1,1,0, -1,0);
        assertEquals(2, myRectangle.getArea(),0.1);
    }
    
    @Test
    public void testGetWidth() throws ShapeException {
        Rectangle myRectangle = new Rectangle(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(5, myRectangle.getWidth(),0.1);
        
        myRectangle = new Rectangle(5, 0, 0, 0, 0, 5, 5, 5);
        assertEquals(5, myRectangle.getWidth(),0.1);
        
        myRectangle = new Rectangle(-2,0,2,0,-2,3, 2, 3);
        assertEquals(4, myRectangle.getWidth(),0.1);
        
        myRectangle = new Rectangle(-5,6,3,6,-5,-2,3,-2);
        assertEquals(8, myRectangle.getWidth(),0.1);
        
        myRectangle = new Rectangle(0,0,0,1,1,0, 1, 1);
        assertEquals(1, myRectangle.getWidth(),0.1);
        
        myRectangle = new Rectangle(0,-1,0,1,1,0, -1,0);
        assertEquals(1.4142, myRectangle.getWidth(),0.1);
    }
    
    @Test
    public void testGetHeight() throws ShapeException {
        Rectangle myRectangle = new Rectangle(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(5, myRectangle.getHeight(),0.1);
        
        myRectangle = new Rectangle(5, 0, 0, 0, 0, 5, 5, 5);
        assertEquals(5, myRectangle.getHeight(),0.1);
        
        myRectangle = new Rectangle(-2,0,2,0,-2,3, 2, 3);
        assertEquals(3, myRectangle.getHeight(),0.1);
        
        myRectangle = new Rectangle(-5,6,3,6,-5,-2,3,-2);
        assertEquals(8, myRectangle.getHeight(),0.1);
        
        myRectangle = new Rectangle(0,0,0,1,1,0, 1, 1);
        assertEquals(1, myRectangle.getHeight(),0.1);
        
        myRectangle = new Rectangle(0,-1,0,1,1,0, -1,0);
        assertEquals(1.4142, myRectangle.getHeight(),0.1);
    }
    @Test
    public void testToString() throws Exception{
    	Rectangle r1 = new Rectangle(new Point(1,6),new Point(2,9), new Point(2, 6), new Point(1,9));    	
    	assertEquals("Rectangle,1.0,6.0,2.0,9.0,2.0,6.0,1.0,9.0",r1.toString());
    	
    	Rectangle r2 = new Rectangle(new Point(-30,12),new Point(100,-300), new Point(-30, -300),new Point(100,12));    	
    	assertEquals("Rectangle,-30.0,12.0,100.0,-300.0,-30.0,-300.0,100.0,12.0",r2.toString());
    }
    @Test
    public void testRenderRectangle() throws ShapeException, IOException {
        // Setup
        Rectangle testShape = new Rectangle(10, 10, 80, 50, 10, 50, 80, 10);
        BufferedImage bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);
        
        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/rectangle1.png")));
        
        // Setup
        testShape = new Rectangle(0, 50, 50,0,50, 100, 100, 50);
        bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/rectangle2.png")));
    }
}