package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;


import shapes.*;

class CompositeTest {

	@Test
	void testValidConstruction() throws ShapeException {
		Composite tester = new Composite();
		assertTrue(tester.getPoints().isEmpty());
		
		Shape s1 = new Line(new Point(0,0),new Point(1,2));
		tester = new Composite(s1);
		assertSame(s1,tester.getShapes().get(0));
		
		Shape s2 = new Point(1,4);
		Shape s3 = new Circle(1,2,6);
		
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(s1);
		shapes.add(s2);
		shapes.add(s3);		
		
		tester = new Composite(shapes);
		assertSame(s1,tester.getShapes().get(0));
		assertSame(s2,tester.getShapes().get(1));
		assertSame(s3,tester.getShapes().get(2));
				
	}
	
	@Test
	void testInvalidConstruction() throws ShapeException {
		
		Shape s1 = null;
		
		try{
			new Composite(s1);
		}
		catch(Exception e){
			assertEquals(ShapeException.class, e.getClass());
			assertEquals("Shape cannot be null", e.getMessage());
		}
				
		Shape s2 = new Point(1,4);
		Shape s3 = new Circle(1,2,6);
		
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(s1);
		shapes.add(s2);
		shapes.add(s3);		
		
		try{
			new Composite(shapes);
		}
		catch(Exception e){
			assertEquals(ShapeException.class, e.getClass());
			assertEquals("Shape cannot be null", e.getMessage());
		}
	}
	@Test
	void testShapeFunctionailities() throws ShapeException{
		Composite base = new Composite();
		
		Shape s1 = new Line(new Point(0,0),new Point(1,2));
		Shape s2 = new Point(1,4);
		Shape s3 = new Circle(1,2,6);
		
		base.addShape(s1);
		assertSame(s1,base.getShapes().get(0));
		base.addShape(s2);
		assertSame(s2,base.getShapes().get(1));
		
		base.removeAll();
		assertTrue(base.getShapes().isEmpty());
		
		base.addShape(s3);
		assertSame(s3,base.getShapes().get(0));
		
		base.removeShape(0);
		assertTrue(base.getShapes().isEmpty());
	}
	@Test
	void testMove1() throws ShapeException {
		Composite base = new Composite();
		
		Shape s1 = new Line(new Point(0,0),new Point(1,2));
		Shape s2 = new Point(1,4);
		Shape s3 = new Circle(1,2,6);
		
		base.addShape(s1);
		base.addShape(s2);
		base.addShape(s3);
		
		base.move(1, -1);
		
		assertEquals(1,base.getShapes().get(0).getPoints().get(0).getX());
		assertEquals(-1,base.getShapes().get(0).getPoints().get(0).getY());
		assertEquals(2,base.getShapes().get(0).getPoints().get(1).getX());
		assertEquals(1,base.getShapes().get(0).getPoints().get(1).getY());
		assertEquals(2,base.getShapes().get(1).getPoints().get(0).getX());
		assertEquals(3,base.getShapes().get(1).getPoints().get(0).getY());
		assertEquals(2,base.getShapes().get(2).getPoints().get(0).getX());
		assertEquals(1,base.getShapes().get(2).getPoints().get(0).getY());
	}
	@Test
	void testMove2() throws ShapeException {
		Composite base = new Composite();
		
		Shape s1 = new Line(new Point(0,0),new Point(1,2));
		Shape s2 = new Point(1,4);
		Shape s3 = new Circle(1,2,6);
		
		base.addShape(s1);
		base.addShape(s2);
		base.addShape(s3);
		
		Composite tester = new Composite();
		Shape s4 = new Triangle(new Point(1,3), new Point(1,9),new Point(8,3));
		tester.addShape(base);
		tester.addShape(s4);
		
		tester.move(1, -1);
		
		assertEquals(1,((Composite) tester.getShapes().get(0)).getShapes().get(0).getPoints().get(0).getX());
		assertEquals(-1,((Composite) tester.getShapes().get(0)).getShapes().get(0).getPoints().get(0).getY());
		assertEquals(2,((Composite) tester.getShapes().get(0)).getShapes().get(0).getPoints().get(1).getX());
		assertEquals(1,((Composite) tester.getShapes().get(0)).getShapes().get(0).getPoints().get(1).getY());
		assertEquals(2,((Composite) tester.getShapes().get(0)).getShapes().get(1).getPoints().get(0).getX());
		assertEquals(3,((Composite) tester.getShapes().get(0)).getShapes().get(1).getPoints().get(0).getY());
		assertEquals(2,((Composite) tester.getShapes().get(0)).getShapes().get(2).getPoints().get(0).getX());
		assertEquals(1,((Composite) tester.getShapes().get(0)).getShapes().get(2).getPoints().get(0).getY());
		assertEquals(2,tester.getShapes().get(1).getPoints().get(0).getX());
		assertEquals(2,tester.getShapes().get(1).getPoints().get(0).getY());
		assertEquals(2,tester.getShapes().get(1).getPoints().get(1).getX());
		assertEquals(8,tester.getShapes().get(1).getPoints().get(1).getY());
		assertEquals(9,tester.getShapes().get(1).getPoints().get(2).getX());
		assertEquals(2,tester.getShapes().get(1).getPoints().get(2).getY());
	}
	@Test
	void testGetArea() throws ShapeException {
		Composite base = new Composite();
		
		Shape s1 = new Line(new Point(0,0),new Point(1,2));
		Shape s2 = new Ellipse(1,4,5,6);
		Shape s3 = new Circle(1,2,6);
		
		base.addShape(s1);
		base.addShape(s2);
		base.addShape(s3);
		
		assertEquals(207.345,base.getArea(),0.001);		
	}
	@Test
	void testGetPoints() throws ShapeException {
		Composite base = new Composite();
		
		Shape s1 = new Line(new Point(0,0),new Point(1,2));
		Shape s2 = new Ellipse(1,4,5,6);
		
		base.addShape(s1);
		base.addShape(s2);
		
		ArrayList <Point> points = base.getPoints();
		
		assertEquals(0,points.get(0).getX());
		assertEquals(0,points.get(0).getY());
		assertEquals(1,points.get(1).getX());
		assertEquals(2,points.get(1).getY());
		assertEquals(1,points.get(2).getX());
		assertEquals(4,points.get(2).getY());
	}
	@Test
	void testToString() throws ShapeException {
		Composite inner1 = new Composite();
			inner1.addShape(new Line(0,0,9,6));
			inner1.addShape(new Line(2,3,4,5));
		Shape s1 = new Triangle(1,1,9,3,7,8);
		
		Composite base = new Composite();
		base.addShape(inner1);
		base.addShape(s1);
		assertEquals("Composite,2\nComposite,2\nLine,0.0,0.0,9.0,6.0\nLine,2.0,3.0,4.0,5.0\nTriangle,1.0,1.0,9.0,3.0,7.0,8.0",base.toString());
	}
    @Test
    public void testRenderComposite() throws ShapeException, IOException {
        // Setup
    	Composite base = new Composite();
    	
        Square testShape1 = new Square(10, 10, 60, 60, 10, 60, 60, 10);
        Square testShape2 = new Square(10, 50, 50,10,50, 90, 90, 50);
        Ellipse testShape3 = new Ellipse(0, 50, 70, 25);
        Line testShape4 = new Line(0, 0, 100,100);
        Triangle testShape5 = new Triangle(30, 60, 80, 60, 12, 90);
        
		ShapeFactory sf = new ShapeFactory();
		
		//looking at it as file		
		InputStream inputS = new ByteArrayInputStream("embedded, 25,30,file,src/resources/testImage1.jpg".getBytes());
		EmbeddedImage testShape6 = (EmbeddedImage) sf.createShape(inputS);
        
        
        base.addShape(testShape1);
        base.addShape(testShape2);
        base.addShape(testShape3);
        base.addShape(testShape4);
        base.addShape(testShape5);
        base.addShape(testShape6);
        
        BufferedImage bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        base.draw(graphics);
        
        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/composite1.png")));
        
    }
}
