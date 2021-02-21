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

import shapes.Ellipse;
import shapes.Point;
import shapes.ShapeException;

class EllipseTest {

	@Test
    public void testValidConstruction() throws Exception {
        Point center = new Point(1,2);
        Ellipse myEllipse = new Ellipse(center, 5, 2);
        assertSame(center, myEllipse.getCenter());
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(2, myEllipse.getRadius2(), 0);

        myEllipse = new Ellipse(1.3, 2.6, 2.5, 3.6);
        assertEquals(1.3, myEllipse.getCenter().getX(), 0);
        assertEquals(2.6, myEllipse.getCenter().getY(), 0);
        assertEquals(2.5, myEllipse.getRadius1(), 0);
        assertEquals(3.6, myEllipse.getRadius2(), 0);
    }

    @Test
    public void testInvalidConstruction() {

        try {
            new Ellipse(null, 2.5, 3.6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse( new Point(1, 2), Double.POSITIVE_INFINITY, 3.6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }
        try {
            new Ellipse( new Point(1, 2), 2.5, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(new Point(1, 2), Double.NEGATIVE_INFINITY, 3.6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }
        
        try {
            new Ellipse(new Point(1, 2), 2.5, Double.NEGATIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }
        
        try {
            new Ellipse(new Point(1, 2), Double.NaN, 3.6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(new Point(1, 2), 2.5, Double.NaN);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }
        
        try {
            new Ellipse(Double.POSITIVE_INFINITY, 2, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(Double.NEGATIVE_INFINITY, 2, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(Double.NaN, 2, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, Double.POSITIVE_INFINITY, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, Double.NEGATIVE_INFINITY, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, Double.NaN, 3, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }


        try {
            new Ellipse(1, 2, Double.POSITIVE_INFINITY, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, 2, Double.NEGATIVE_INFINITY, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, 2, Double.NaN, 4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }
        
        try {
            new Ellipse(1, 2, 3, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, 2, 3, Double.NEGATIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            new Ellipse(1, 2, 3, Double.NaN);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

    }

    @Test
    public void testMove() throws ShapeException {
        Ellipse myEllipse = new Ellipse(1, 2, 5, 6);
        assertEquals(1, myEllipse.getCenter().getX(), 0);
        assertEquals(2, myEllipse.getCenter().getY(), 0);
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(6, myEllipse.getRadius2(), 0);

        myEllipse.move(3,  4);
        assertEquals(4, myEllipse.getCenter().getX(), 0);
        assertEquals(6, myEllipse.getCenter().getY(), 0);
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(6, myEllipse.getRadius2(), 0);

        myEllipse.move(0.123,  0.456);
        assertEquals(4.123, myEllipse.getCenter().getX(), 0);
        assertEquals(6.456, myEllipse.getCenter().getY(), 0);
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(6, myEllipse.getRadius2(), 0);

        myEllipse.move(-0.123,  -0.456);
        assertEquals(4, myEllipse.getCenter().getX(), 0);
        assertEquals(6, myEllipse.getCenter().getY(), 0);
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(6, myEllipse.getRadius2(), 0);

        myEllipse.move(-12,  -26);
        assertEquals(-8, myEllipse.getCenter().getX(), 0);
        assertEquals(-20, myEllipse.getCenter().getY(), 0);
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(6, myEllipse.getRadius2(), 0);

        try {
            myEllipse.move(Double.POSITIVE_INFINITY, 1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.move(Double.NEGATIVE_INFINITY, 1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.move(Double.NaN, 1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.move(1, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.move(1, Double.NEGATIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.move(1, Double.NaN);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

    }

    @Test
    public void testScale() throws ShapeException {
        Ellipse myEllipse = new Ellipse(1, 2, 5, 6);
        assertEquals(1, myEllipse.getCenter().getX(), 0);
        assertEquals(2, myEllipse.getCenter().getY(), 0);
        assertEquals(5, myEllipse.getRadius1(), 0);
        assertEquals(6, myEllipse.getRadius2(), 0);

        myEllipse.scale(3);
        assertEquals(1, myEllipse.getCenter().getX(), 0);
        assertEquals(2, myEllipse.getCenter().getY(), 0);
        assertEquals(15, myEllipse.getRadius1(), 0);
        assertEquals(18, myEllipse.getRadius2(), 0);

        myEllipse.scale(0.2);
        assertEquals(1, myEllipse.getCenter().getX(), 0);
        assertEquals(2, myEllipse.getCenter().getY(), 0);
        assertEquals(3, myEllipse.getRadius1(), 0);
        assertEquals(3.6, myEllipse.getRadius2(), 0);

        try {
            myEllipse.scale(Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.scale(Double.NEGATIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }

        try {
            myEllipse.scale(Double.NaN);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            // ignore
        }
    }

    @Test
    public void testComputeArea() throws ShapeException {
        Ellipse myEllipse = new Ellipse(1, 2, 5, 6);
        assertEquals(94.24777, myEllipse.getArea(), 0.0001);

        myEllipse = new Ellipse(1, 2, 4.234, 5.682);
        assertEquals(75.57914, myEllipse.getArea(), 0.0001);

        myEllipse = new Ellipse(1, 2, 0, 0);
        assertEquals(0, myEllipse.getArea(), 0);

    }
    @Test
    public void testToString() throws Exception{
    	Ellipse e1 = new Ellipse(1,6,2,3);    	
    	assertEquals("Ellipse,1.0,6.0,2.0,3.0",e1.toString());
    	
    	Ellipse e2 = new Ellipse(-1, -999,50,60);
    	assertEquals("Ellipse,-1.0,-999.0,50.0,60.0",e2.toString());
    }
    
    @Test
    public void testRenderEllipse() throws ShapeException, IOException {
        // Setup
        Ellipse testShape = new Ellipse(25, 50, 30, 45);
        BufferedImage bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/ellipse1.png")));
        
        // Setup
        testShape = new Ellipse(0, 50, 70, 25);
        bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/ellipse2.png")));
    }
}
