package examples.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;

class RectangleTest {

    @Test
    public void testValidConstruction() throws Exception {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	Point p4 = new Point(5,5);
    	
    	
    	Rectangle myRectangle = new Rectangle(p1,p2,p3,p4);
    	assertSame(p1, myRectangle.getPoint1());
    	assertSame(p2, myRectangle.getPoint2());
    	assertSame(p3, myRectangle.getPoint3());
    	assertSame(p4, myRectangle.getPoint4());
    	
    	p1 = new Point(-2,1);
    	p2 = new Point(-2,5);
    	p3 = new Point(2,1);
    	p4 = new Point(2,5);
    	myRectangle = new Rectangle(p1,p2,p3,p4);
    	assertSame(p1, myRectangle.getPoint1());
    	assertSame(p2, myRectangle.getPoint2());
    	assertSame(p3, myRectangle.getPoint3());
    	assertSame(p4, myRectangle.getPoint4());
    	
    	myRectangle = new Rectangle(-4.5, -5, -4.5, 6, 9.6, -5, 9.6, 6);
    	assertEquals(-4.5,myRectangle.getPoint1().getX(),0);
    	assertEquals(-5,myRectangle.getPoint1().getY(),0);
    	assertEquals(-4.5,myRectangle.getPoint2().getX(),0);
    	assertEquals(6,myRectangle.getPoint2().getY(),0);
    	assertEquals(9.6,myRectangle.getPoint3().getX(),0);
    	assertEquals(-5,myRectangle.getPoint3().getY(),0);
    	assertEquals(9.6,myRectangle.getPoint4().getX(),0);
    	assertEquals(6,myRectangle.getPoint4().getY(),0);    
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
        assertEquals(3, myRectangle.getPoint1().getX(), 0);
        assertEquals(4, myRectangle.getPoint1().getY(), 0);
        assertEquals(3, myRectangle.getPoint2().getX(), 0);
        assertEquals(9, myRectangle.getPoint2().getY(), 0);
        assertEquals(8, myRectangle.getPoint3().getX(), 0);
        assertEquals(4, myRectangle.getPoint3().getY(), 0);
        assertEquals(8, myRectangle.getPoint4().getX(), 0);
        assertEquals(9, myRectangle.getPoint4().getY(), 0);

        myRectangle.move(.4321, .7654);
        assertEquals(3.4321, myRectangle.getPoint1().getX(), 0.1);
        assertEquals(4.7654, myRectangle.getPoint1().getY(), 0.1);
        assertEquals(3.4321, myRectangle.getPoint2().getX(), 0.1);
        assertEquals(9.7654, myRectangle.getPoint2().getY(), 0.1);
        assertEquals(8.4321, myRectangle.getPoint3().getX(), 0.1);
        assertEquals(4.7654, myRectangle.getPoint3().getY(), 0.1);
        assertEquals(8.4321, myRectangle.getPoint4().getX(), 0.1);
        assertEquals(9.7654, myRectangle.getPoint4().getY(), 0.1);

        myRectangle.move(-0.4321, -0.7654);
        assertEquals(3, myRectangle.getPoint1().getX(), 0.1);
        assertEquals(4, myRectangle.getPoint1().getY(), 0.1);
        assertEquals(3, myRectangle.getPoint2().getX(), 0.1);
        assertEquals(9, myRectangle.getPoint2().getY(), 0.1);
        assertEquals(8, myRectangle.getPoint3().getX(), 0.1);
        assertEquals(4, myRectangle.getPoint3().getY(), 0.1);
        assertEquals(8, myRectangle.getPoint4().getX(), 0.1);
        assertEquals(9, myRectangle.getPoint4().getY(), 0.1);
    }
    
    @Test
    public void testComputeArea() throws ShapeException {
        Rectangle myRectangle = new Rectangle(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(25, myRectangle.ComputeArea(),0.1);
        
        myRectangle = new Rectangle(5, 0, 0, 0, 0, 5, 5, 5);
        assertEquals(25, myRectangle.ComputeArea(),0.1);
        
        myRectangle = new Rectangle(-2,0,2,0,-2,3, 2, 3);
        assertEquals(12, myRectangle.ComputeArea(),0.1);
        
        myRectangle = new Rectangle(-5,6,3,6,-5,-2,3,-2);
        assertEquals(64, myRectangle.ComputeArea(),0.1);
        
        myRectangle = new Rectangle(0,0,0,1,1,0, 1, 1);
        assertEquals(1, myRectangle.ComputeArea(),0.1);
        
        myRectangle = new Rectangle(0,-1,0,1,1,0, -1,0);
        assertEquals(2, myRectangle.ComputeArea(),0.1);
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
}