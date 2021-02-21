package examples.shapes;

import org.junit.Test;

import static org.junit.Assert.*;


public class TriangleTest {

    @Test
    public void testValidConstruction() throws Exception {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	
    	
    	Triangle myTriangle = new Triangle(p1,p2,p3);
    	assertSame(p1, myTriangle.getPoint1());
    	assertSame(p2, myTriangle.getPoint2());
    	assertSame(p3, myTriangle.getPoint3());
    	
    	p1 = new Point(0,3);
    	p2 = new Point(2,5);
    	p3 = new Point(6,1);
    	myTriangle = new Triangle(p1,p2,p3);
    	assertSame(p1, myTriangle.getPoint1());
    	assertSame(p2, myTriangle.getPoint2());
    	assertSame(p3, myTriangle.getPoint3());
    	
    	myTriangle = new Triangle(3.8, 6.4, 1.2, 0.6, 9.9, 7.8);
    	assertEquals(3.8,myTriangle.getPoint1().getX(),0);
    	assertEquals(6.4,myTriangle.getPoint1().getY(),0);
    	assertEquals(1.2,myTriangle.getPoint2().getX(),0);
    	assertEquals(0.6,myTriangle.getPoint2().getY(),0);
    	assertEquals(9.9,myTriangle.getPoint3().getX(),0);
    	assertEquals(7.8,myTriangle.getPoint3().getY(),0);    	
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
        assertEquals(3, myTriangle.getPoint1().getX(), 0);
        assertEquals(5, myTriangle.getPoint1().getY(), 0);
        assertEquals(6, myTriangle.getPoint2().getX(), 0);
        assertEquals(10, myTriangle.getPoint2().getY(), 0);
        assertEquals(12, myTriangle.getPoint3().getX(), 0);
        assertEquals(14, myTriangle.getPoint3().getY(), 0);

        myTriangle.move(.4321, .7654);
        assertEquals(3.4321, myTriangle.getPoint1().getX(), 0);
        assertEquals(5.7654, myTriangle.getPoint1().getY(), 0);
        assertEquals(6.4321, myTriangle.getPoint2().getX(), 0);
        assertEquals(10.7654, myTriangle.getPoint2().getY(), 0);
        assertEquals(12.4321, myTriangle.getPoint3().getX(), 0);
        assertEquals(14.7654, myTriangle.getPoint3().getY(), 0);

        myTriangle.move(-0.4321, -0.7654);
        assertEquals(3, myTriangle.getPoint1().getX(), 0);
        assertEquals(5, myTriangle.getPoint1().getY(), 0);
        assertEquals(6, myTriangle.getPoint2().getX(), 0);
        assertEquals(10, myTriangle.getPoint2().getY(), 0);
        assertEquals(12, myTriangle.getPoint3().getX(), 0);
        assertEquals(14, myTriangle.getPoint3().getY(), 0);
    }
    
    @Test
    public void testComputeArea() throws ShapeException {
        Triangle myTriangle = new Triangle(0, 0, 0, 5, 5, 0);
        assertEquals(12.5, myTriangle.ComputeArea(),0.1);
        
        myTriangle = new Triangle(-2,0,2,0,0,3);
        assertEquals(6, myTriangle.ComputeArea(),0.1);
        
        myTriangle = new Triangle(-5,6,3,6,-1,-2);
        assertEquals(32, myTriangle.ComputeArea(),0.1);
        
        myTriangle = new Triangle(0,0,0,1,1,0);
        assertEquals(0.5, myTriangle.ComputeArea(),0.1);
    }

}
