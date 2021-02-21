package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import shapes.*;

class ShapeFactoryTest {

	@Test
	void testCreateShape() throws NumberFormatException, ShapeException, IOException {
		
		ShapeFactory sf = new ShapeFactory();
		
		String inputS = new String("Point, 1, 2");
		
		Shape tester = sf.createShape(inputS);

		assertTrue(tester instanceof Point);		
		if(tester instanceof Point) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(2,tester.getPoints().get(0).getY());
		}
		
		inputS = new String("Line, 1, 2, 2, 1");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Line);		
		if(tester instanceof Line) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(2,tester.getPoints().get(0).getY());
			assertEquals(2,tester.getPoints().get(1).getX());
			assertEquals(1,tester.getPoints().get(1).getY());
			assertEquals(-1, ((Line) tester).computeSlope());
			assertEquals(1.41, ((Line) tester).computeLength(), 0.01);
		}
		
		inputS = new String("Circle, 1, 2, 3");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Circle);		
		if(tester instanceof Circle) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(2,tester.getPoints().get(0).getY());
			assertEquals(3,((Circle) tester).getRadius());
			assertEquals(28.2743, tester.getArea(), 0.001);
		}

		inputS = new String("Ellipse, 1, 5, 3, 6");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Ellipse);		
		if(tester instanceof Ellipse) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(5,tester.getPoints().get(0).getY());
			assertEquals(3,((Ellipse) tester).getRadius1());
			assertEquals(6,((Ellipse) tester).getRadius2());
			assertEquals(56.5486, tester.getArea(), 0.001);
		}
		
		inputS = new String("Triangle, 1, 6, 1, 2, 3, 4");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Triangle);		
		if(tester instanceof Triangle) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(6,tester.getPoints().get(0).getY());
			assertEquals(1,tester.getPoints().get(1).getX());
			assertEquals(2,tester.getPoints().get(1).getY());
			assertEquals(3,tester.getPoints().get(2).getX());
			assertEquals(4,tester.getPoints().get(2).getY());
			assertEquals(4,tester.getArea(),0.0001);
		}
		
		inputS = new String("Rectangle, 1, 6, 1, 2, 3, 6, 3, 2");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Rectangle);		
		if(tester instanceof Rectangle) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(6,tester.getPoints().get(0).getY());
			assertEquals(1,tester.getPoints().get(1).getX());
			assertEquals(2,tester.getPoints().get(1).getY());
			assertEquals(3,tester.getPoints().get(2).getX());
			assertEquals(6,tester.getPoints().get(2).getY());
			assertEquals(3,tester.getPoints().get(3).getX());
			assertEquals(2,tester.getPoints().get(3).getY());
			assertEquals(8,tester.getArea(),0.0001);
		}
		
		inputS = new String("Square, 1, 4, 1, 2, 3, 4, 3, 2");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Square);		
		if(tester instanceof Square) {
			assertEquals(1,tester.getPoints().get(0).getX());
			assertEquals(4,tester.getPoints().get(0).getY());
			assertEquals(1,tester.getPoints().get(1).getX());
			assertEquals(2,tester.getPoints().get(1).getY());
			assertEquals(3,tester.getPoints().get(2).getX());
			assertEquals(4,tester.getPoints().get(2).getY());
			assertEquals(3,tester.getPoints().get(3).getX());
			assertEquals(2,tester.getPoints().get(3).getY());
			assertEquals(4,tester.getArea(),0.0001);
		}
		inputS = new String("Embedded, 1, 4,file,src/resources/testImage1.jpg");
		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof EmbeddedImage);		
		if(tester instanceof EmbeddedImage) {
			assertEquals(0,tester.getPoints().get(0).getX());
			assertEquals(0,tester.getPoints().get(0).getY());
			assertEquals(1,tester.getPoints().get(1).getX());
			assertEquals(0,tester.getPoints().get(1).getY());
			assertEquals(0,tester.getPoints().get(2).getX());
			assertEquals(4,tester.getPoints().get(2).getY());
			assertEquals(1,tester.getPoints().get(3).getX());
			assertEquals(4,tester.getPoints().get(3).getY());
			assertEquals(4,tester.getArea(),0.0001);
		}
		inputS = new String("Embedded, 1, 4,resource,/resources/testImage1.jpg");		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof EmbeddedImage);		
		if(tester instanceof EmbeddedImage) {
			assertEquals(0,tester.getPoints().get(0).getX());
			assertEquals(0,tester.getPoints().get(0).getY());
			assertEquals(1,tester.getPoints().get(1).getX());
			assertEquals(0,tester.getPoints().get(1).getY());
			assertEquals(0,tester.getPoints().get(2).getX());
			assertEquals(4,tester.getPoints().get(2).getY());
			assertEquals(1,tester.getPoints().get(3).getX());
			assertEquals(4,tester.getPoints().get(3).getY());
			assertEquals(4,tester.getArea(),0.0001);
			
			InputStream tester2 = getClass().getResourceAsStream("/resources/testImage1.jpg");
			BufferedImage testImg = ImageIO.read(tester2);
			
			assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(0), testImg));
		}
		inputS = new String("Embedded, 1, 4,file,src/resources/testImage1.jpg");		
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof EmbeddedImage);		
		if(tester instanceof EmbeddedImage) {
			assertEquals(0,tester.getPoints().get(0).getX());
			assertEquals(0,tester.getPoints().get(0).getY());
			assertEquals(1,tester.getPoints().get(1).getX());
			assertEquals(0,tester.getPoints().get(1).getY());
			assertEquals(0,tester.getPoints().get(2).getX());
			assertEquals(4,tester.getPoints().get(2).getY());
			assertEquals(1,tester.getPoints().get(3).getX());
			assertEquals(4,tester.getPoints().get(3).getY());
			assertEquals(4,tester.getArea(),0.0001);
			
			InputStream tester2 = new FileInputStream("src/resources/testImage1.jpg");
			BufferedImage testImg = ImageIO.read(tester2);			
			assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(0), testImg));
		}
		
		inputS = new String("Composite, 2 \n Line,1,1,3,4 \n Point,6,8");
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Composite);		
		if(tester instanceof Composite) {
			assertEquals(2,((Composite) tester).getShapes().size());
			assertTrue(((Composite) tester).getShapes().get(0) instanceof Line);
			assertTrue(((Composite) tester).getShapes().get(1) instanceof Point);
		}
		
		inputS = new String(
				"Composite, 4 \n "
					+ "Circle,5,6,11 \n"
					+ "Composite,2 \n "
						+ "Line,1,1,3,4 \n "
						+ "Ellipse,1,2,8,9\n"
					+ "Point,1,12 \n "
					+ "Circle,3,6,8"
				);
		tester = sf.createShape(inputS);
		
		
		inputS = new String(
				"Composite, 3 \n "
					+ "Composite,2 \n "
						+ "Line,1,1,3,4 \n "
						+ "Composite,2 \n "
							+ "Circle,5,6,11 \n"
							+ "Composite,1 \n "
								+ "Ellipse,1,2,8,9\n"
					+ "Point,1,12 \n "
					+ "Circle,3,6,8"
				);
		tester = sf.createShape(inputS);
		
		assertTrue(tester instanceof Composite);		
		if(tester instanceof Composite) {
			//assertEquals(6,((Composite) tester).getShapes().size());
			assertTrue(((Composite) tester).getShapes().get(0) instanceof Composite);
			Shape inner1 = ((Composite) tester).getShapes().get(0);
			if(inner1 instanceof Composite) {
				assertTrue(((Composite) inner1).getShapes().get(0) instanceof Line);
				Shape inner2 = ((Composite) inner1).getShapes().get(1);
				assertTrue(inner2 instanceof Composite);
				if(inner2 instanceof Composite) {
					assertTrue(((Composite) inner2).getShapes().get(0) instanceof Circle);
					Shape inner3 = ((Composite) inner2).getShapes().get(1);
					assertTrue(inner3 instanceof Composite);
					if(inner3 instanceof Composite) {
						assertTrue(((Composite) inner2).getShapes().get(0) instanceof Ellipse);
					}
				}
			}
			assertTrue(((Composite) tester).getShapes().get(1) instanceof Point);
			assertTrue(((Composite) tester).getShapes().get(2) instanceof Circle);
		}
	}
	@Test
	void testInputStreams() throws NumberFormatException, IOException, ShapeException{
		//ByteArrayStream
		String inputS = new String("Composite, 2 \n Line,1,1,3,4 \n Point,6,8");
		InputStream streamIn = new ByteArrayInputStream(inputS.getBytes());
		ShapeFactory sf = new ShapeFactory();
		
		Shape tester = sf.createShape(streamIn);
		
		assertTrue(tester instanceof Composite);
		if(tester instanceof Composite) {
			assertEquals(2,((Composite) tester).getShapes().size());
			assertTrue(((Composite) tester).getShapes().get(0) instanceof Line);
			assertTrue(((Composite) tester).getShapes().get(1) instanceof Point);
		}
		//FileInputStream
		streamIn = new FileInputStream("src/resources/script1.txt");
		tester = sf.createShape(streamIn);
		
		assertTrue(tester instanceof Composite);
		if(tester instanceof Composite) {
			assertEquals(3,((Composite) tester).getShapes().size());
			assertTrue(((Composite) tester).getShapes().get(0) instanceof Line);
			assertTrue(((Composite) tester).getShapes().get(1) instanceof Point);
			assertTrue(((Composite) tester).getShapes().get(2) instanceof Line);
		}
		//ResourceStream
		streamIn = getClass().getResourceAsStream("/resources/script2.txt");
		tester = sf.createShape(streamIn);
		
		assertTrue(tester instanceof Composite);
		if(tester instanceof Composite) {
			assertEquals(4,((Composite) tester).getShapes().size());
			assertTrue(((Composite) tester).getShapes().get(0) instanceof Line);
			assertTrue(((Composite) tester).getShapes().get(1) instanceof Point);
			assertTrue(((Composite) tester).getShapes().get(2) instanceof Line);
			assertTrue(((Composite) tester).getShapes().get(3) instanceof Line);
		}
	}
}
