package examples.shapes;

import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {

    @Test
    public void testValidConstruction() throws Exception {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	Point p4 = new Point(5,5);    	
    	
    	Square mySquare = new Square(p1,p2,p3,p4);
    	assertSame(p1, mySquare.getPoint1());
    	assertSame(p2, mySquare.getPoint2());
    	assertSame(p3, mySquare.getPoint3());
    	assertSame(p4, mySquare.getPoint4());
    	
    	p1 = new Point(-2,1);
    	p2 = new Point(-2,5);
    	p3 = new Point(2,1);
    	p4 = new Point(2,5);
    	mySquare = new Square(p1,p2,p3,p4);
    	assertSame(p1, mySquare.getPoint1());
    	assertSame(p2, mySquare.getPoint2());
    	assertSame(p3, mySquare.getPoint3());
    	assertSame(p4, mySquare.getPoint4());
    	
    	mySquare = new Square(-4.5, -5, -4.5, 6, 6.5, 6, 6.5, -5);
    	assertEquals(-4.5,mySquare.getPoint1().getX(),0);
    	assertEquals(-5,mySquare.getPoint1().getY(),0);
    	assertEquals(-4.5,mySquare.getPoint2().getX(),0);
    	assertEquals(6,mySquare.getPoint2().getY(),0);
    	assertEquals(6.5,mySquare.getPoint3().getX(),0);
    	assertEquals(6,mySquare.getPoint3().getY(),0);
    	assertEquals(6.5,mySquare.getPoint4().getX(),0);
    	assertEquals(-5,mySquare.getPoint4().getY(),0);    
    }
    @Test
    public void testInvalidConstruction() throws ShapeException {
    	Point p1 = new Point(1,1);
    	Point p2 = new Point(1,5);
    	Point p3 = new Point(5,1);
    	Point p4 = new Point(5,5);
    	
    	try {
    		new Square(null, p2, p3, p4);
    		fail("Expected exception not thrown for when the first parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Square(p1, null, p3, p4);
    		fail("Expected exception not thrown for when the second parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Square(p1, p2, null, p4);
    		fail("Expected exception not thrown for when the third parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	try {
    		new Square(p1, p2, p3, null);
    		fail("Expected exception not thrown for when the fourth parameter is null");
    	} catch(Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid Point", e.getMessage());
    	}
    	
    	
    	try {
            new Square(Double.POSITIVE_INFINITY, 2, 3, 4, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Square(1, Double.POSITIVE_INFINITY, 3, 4, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Square(1, 2, Double.POSITIVE_INFINITY, 4, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Square(1, 2, 3, Double.POSITIVE_INFINITY, 5, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Square(1, 2, 3, 4, Double.POSITIVE_INFINITY, 6, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Square(1, 2, 3, 4, 5, Double.POSITIVE_INFINITY, 7, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
    	try {
            new Square(1, 2, 3, 4, 5, 6, Double.POSITIVE_INFINITY, 8);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid x-location", e.getMessage());
        }
    	
    	try {
            new Square(1, 2, 3, 4, 5, 5, 7, Double.POSITIVE_INFINITY);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid y-location", e.getMessage());
        }
    	
        try {
            new Square(p1, p1, p2, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
        	new Square(p1, p2, p2, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
        	new Square(p1, p2, p3, p3);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
        	new Square(p1, p2, p3, p1);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        try {
            new Square(1,2, 1,2, 2,5 ,3, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
            new Square(1,2, 2,5, 2,5 ,3, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
            new Square(1,2, 2,5, 3, 6 ,3, 6);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        try {
            new Square(1,2, 2,5, 3, 6 ,1,2);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("A line must have a length > 0", e.getMessage());
        }
        
        
        try {
            new Square(1,1, 2,2, 3,3, 4,5);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Adjacent points must not be in the same line", e.getMessage());
        }
        try {
            new Square(1,2, 2,2, 3,3, 4,4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Adjacent points must not be in the same line", e.getMessage());
        }
        try {
            new Square(1,1, 2,3, 3,3, 4,4);
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
            new Square(p1, p2, p3, p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid square, inconsistent distance from vertex to center", e.getMessage());
        }
        
        p1 = new Point(0,0);
        p2 = new Point(0,2);
        p3 = new Point(2,2);
        p4 = new Point(3,0);
        
        try {
            new Square(p1, p2, p3,p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid square, inconsistent distance from vertex to center", e.getMessage());
        }
        
        p1 = new Point(-1,-1);
        p2 = new Point(-1,1);
        p3 = new Point(1,2);
        p4 = new Point(3,0);
        
        try {
            new Square(p1, p2, p3,p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid square, inconsistent distance from vertex to center", e.getMessage());
        }
        p1 = new Point(-1,-1);
        p2 = new Point(-1,1);
        p3 = new Point(2,1);
        p4 = new Point(2,-1);
        
        try {
            new Square(p1, p2, p3,p4);
            fail("Expected exception not thrown");
        } catch (Exception e) {
            assertEquals(ShapeException.class, e.getClass());
            assertEquals("Invalid square, width and height do not match", e.getMessage());
        }
    }
    @Test
    public void testMove() throws ShapeException {
        Square mySquare = new Square(0, 0, 0, 5, 5, 0, 5, 5);

        mySquare.move(3, 4);
        assertEquals(3, mySquare.getPoint1().getX(), 0);
        assertEquals(4, mySquare.getPoint1().getY(), 0);
        assertEquals(3, mySquare.getPoint2().getX(), 0);
        assertEquals(9, mySquare.getPoint2().getY(), 0);
        assertEquals(8, mySquare.getPoint3().getX(), 0);
        assertEquals(4, mySquare.getPoint3().getY(), 0);
        assertEquals(8, mySquare.getPoint4().getX(), 0);
        assertEquals(9, mySquare.getPoint4().getY(), 0);

        mySquare.move(.4321, .7654);
        assertEquals(3.4321, mySquare.getPoint1().getX(), 0.1);
        assertEquals(4.7654, mySquare.getPoint1().getY(), 0.1);
        assertEquals(3.4321, mySquare.getPoint2().getX(), 0.1);
        assertEquals(9.7654, mySquare.getPoint2().getY(), 0.1);
        assertEquals(8.4321, mySquare.getPoint3().getX(), 0.1);
        assertEquals(4.7654, mySquare.getPoint3().getY(), 0.1);
        assertEquals(8.4321, mySquare.getPoint4().getX(), 0.1);
        assertEquals(9.7654, mySquare.getPoint4().getY(), 0.1);

        mySquare.move(-0.4321, -0.7654);
        assertEquals(3, mySquare.getPoint1().getX(), 0.1);
        assertEquals(4, mySquare.getPoint1().getY(), 0.1);
        assertEquals(3, mySquare.getPoint2().getX(), 0.1);
        assertEquals(9, mySquare.getPoint2().getY(), 0.1);
        assertEquals(8, mySquare.getPoint3().getX(), 0.1);
        assertEquals(4, mySquare.getPoint3().getY(), 0.1);
        assertEquals(8, mySquare.getPoint4().getX(), 0.1);
        assertEquals(9, mySquare.getPoint4().getY(), 0.1);
    }
    
    @Test
    public void testComputeArea() throws ShapeException {
        Square mySquare = new Square(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(25, mySquare.ComputeArea(),0.1);
        
        mySquare = new Square(-2,0,2,0,-2,4, 2, 4);
        assertEquals(16, mySquare.ComputeArea(),0.1);
        
        mySquare = new Square(-5,6,3,6,-5,-2,3,-2);
        assertEquals(64, mySquare.ComputeArea(),0.1);
        
        mySquare = new Square(0,0,0,1,1,0, 1, 1);
        assertEquals(1, mySquare.ComputeArea(),0.1);
        
        mySquare = new Square(0,-1,0,1,1,0, -1,0);
        assertEquals(2, mySquare.ComputeArea(),0.1);
    }
    @Test
    public void testGetWidth() throws ShapeException {
        Square mySquare = new Square(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(5, mySquare.getWidth(),0.1);
        
        mySquare = new Square(-2,0,2,0,-2,4, 2, 4);
        assertEquals(4, mySquare.getWidth(),0.1);
        
        mySquare = new Square(-5,6,3,6,-5,-2,3,-2);
        assertEquals(8, mySquare.getWidth(),0.1);
        
        mySquare = new Square(0,0,0,1,1,0, 1, 1);
        assertEquals(1, mySquare.getWidth(),0.1);
        
        mySquare = new Square(0,-1,0,1,1,0, -1,0);
        assertEquals(1.4142, mySquare.getWidth(),0.1);
    }
    @Test
    public void testGetHeight() throws ShapeException {
        Square mySquare = new Square(0, 0, 0, 5, 5, 0, 5, 5);
        assertEquals(5, mySquare.getHeight(),0.1);
        
        mySquare = new Square(-2,0,2,0,-2,4, 2, 4);
        assertEquals(4, mySquare.getHeight(),0.1);
        
        mySquare = new Square(-5,6,3,6,-5,-2,3,-2);
        assertEquals(8, mySquare.getHeight(),0.1);
        
        mySquare = new Square(0,0,0,1,1,0, 1, 1);
        assertEquals(1, mySquare.getHeight(),0.1);
        
        mySquare = new Square(0,-1,0,1,1,0, -1,0);
        assertEquals(1.4142, mySquare.getHeight(),0.1);
    }
}
