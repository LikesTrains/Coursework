package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import shapes.EmbeddedImage;
import shapes.ShapeException;
import shapes.ShapeFactory;

class EmbeddedImageTest {

	@Test
	void testValidConstruction() throws ShapeException, IOException {
		// set up factory
		ShapeFactory sf = new ShapeFactory();
		
		//looking at it as file		
		InputStream inputS = new ByteArrayInputStream("embedded,100,100,file,src/resources/testImage1.jpg".getBytes());
		EmbeddedImage tester = (EmbeddedImage) sf.createShape(inputS);
		assertEquals(0, tester.getPoints().get(0).getX(), 0.0001);
		assertEquals(0, tester.getPoints().get(0).getY(), 0.0001);
		assertEquals(100, tester.getPoints().get(1).getX(), 0.0001);
		assertEquals(0, tester.getPoints().get(1).getY(), 0.0001);
		assertEquals(0, tester.getPoints().get(2).getX(), 0.0001);
		assertEquals(100, tester.getPoints().get(2).getY(), 0.0001);
		assertEquals(100, tester.getPoints().get(3).getX(), 0.0001);
		assertEquals(100, tester.getPoints().get(3).getY(), 0.0001);
		
		BufferedImage testImg = ImageIO.read(new FileInputStream("src/resources/testImage1.jpg"));
		assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(0), testImg));
		assertEquals(0,tester.getKey());
		
		// looking at is as a resource
		inputS = new ByteArrayInputStream("embedded,100,100,resource,/resources/testImage1.jpg".getBytes());
		tester = (EmbeddedImage) sf.createShape(inputS);
		
		assertEquals(0, tester.getPoints().get(0).getX(), 0.0001);
		assertEquals(0, tester.getPoints().get(0).getY(), 0.0001);
		assertEquals(100, tester.getPoints().get(1).getX(), 0.0001);
		assertEquals(0, tester.getPoints().get(1).getY(), 0.0001);
		assertEquals(0, tester.getPoints().get(2).getX(), 0.0001);
		assertEquals(100, tester.getPoints().get(2).getY(), 0.0001);
		assertEquals(100, tester.getPoints().get(3).getX(), 0.0001);
		assertEquals(100, tester.getPoints().get(3).getY(), 0.0001);
		
		testImg = ImageIO.read(getClass().getResourceAsStream("/resources/testImage1.jpg"));
		
		assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(0), testImg));
		assertEquals(sf.getIntrinsic().getImages().size(),1);
		assertEquals(0,tester.getKey());
		
		// testing another copy of image2
		inputS = new ByteArrayInputStream("embedded,100,100,resource,/resources/testImage2.jpg".getBytes());
		tester = (EmbeddedImage) sf.createShape(inputS);
		
		assertEquals(0, tester.getPoints().get(0).getX(), 0.0001);
		assertEquals(0, tester.getPoints().get(0).getY(), 0.0001);
		assertEquals(100, tester.getPoints().get(1).getX(), 0.0001);
		assertEquals(0, tester.getPoints().get(1).getY(), 0.0001);
		assertEquals(0, tester.getPoints().get(2).getX(), 0.0001);
		assertEquals(100, tester.getPoints().get(2).getY(), 0.0001);
		assertEquals(100, tester.getPoints().get(3).getX(), 0.0001);
		assertEquals(100, tester.getPoints().get(3).getY(), 0.0001);
		
		testImg = ImageIO.read(getClass().getResourceAsStream("/resources/testImage2.jpg"));
		
		assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(1), testImg));
		assertEquals(sf.getIntrinsic().getImages().size(),2);
		assertEquals(1,tester.getKey());
		
		//and another just for good measure
		inputS = new ByteArrayInputStream("embedded,100,100,resource,/resources/testImage2.jpg".getBytes());
		tester = (EmbeddedImage) sf.createShape(inputS);
		
		testImg = ImageIO.read(getClass().getResourceAsStream("/res/image1.png"));
		
		assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(1), testImg));
		assertEquals(sf.getIntrinsic().getImages().size(),2);
		assertEquals(1,tester.getKey());
		//add another copy of image 1 to see how it does
		inputS = new ByteArrayInputStream("embedded,100,100,resource,/resources/testImage1.jpg".getBytes());
		tester = (EmbeddedImage) sf.createShape(inputS);
		
		testImg = ImageIO.read(getClass().getResourceAsStream("/res/image0.png"));
		
		assertTrue(sf.compareImages(sf.getIntrinsic().getImages().get(0), testImg));
		assertEquals(sf.getIntrinsic().getImages().size(),2);
		assertEquals(0,tester.getKey());
	}
	
	@Test
	void testInvalidConstruction() throws ShapeException, IOException {
		ShapeFactory sf = new ShapeFactory();
		
		try {
			sf.createShape(new ByteArrayInputStream("embedded,100,100,file,resources/noImage".getBytes()));
		}
		catch(Exception  e) {
            assertEquals(FileNotFoundException.class, e.getClass());
            assertEquals("resources\\noImage (The system cannot find the path specified)", e.getMessage());
		}
		
		try {
			sf.createShape(new ByteArrayInputStream("embedded,100,100,file,src/resources/test".getBytes()));
		}
		catch(Exception  e) {
            assertEquals(IOException.class, e.getClass());
            assertEquals("File is not an image", e.getMessage());
		}
		
		try {
			sf.createShape(new ByteArrayInputStream("embedded,100,100,file,src/resources/empty".getBytes()));
		}
		catch(Exception  e) {
            assertEquals(IOException.class, e.getClass());
            assertEquals("File is empty", e.getMessage());
		}
		
		try {
			sf.createShape(new ByteArrayInputStream("embedded,100,100,resource,resources/noImage".getBytes()));
		}
		catch(Exception e) {
            assertEquals(IOException.class, e.getClass());
            assertEquals("Invalid input", e.getMessage());
		}
		
		try {
			sf.createShape(new ByteArrayInputStream("embedded,100,100,resource,/resources/test".getBytes()));
		}
		catch(Exception e) {
            assertEquals(IOException.class, e.getClass());
            assertEquals("File is not an image", e.getMessage());
		}
		
		try {
			sf.createShape(new ByteArrayInputStream("embedded,100,100,resource,/resources/empty".getBytes()));
		}
		catch(Exception e) {
            assertEquals(IOException.class, e.getClass());
            assertEquals("File is empty", e.getMessage());
		}
	}
	@Test
    public void testToString() throws Exception{
		// set up factory
		ShapeFactory sf = new ShapeFactory();
		
		//load an embedded shape
		InputStream inputS = new ByteArrayInputStream("embedded,100,100,file,src/resources/testImage1.jpg".getBytes());
		EmbeddedImage tester = (EmbeddedImage) sf.createShape(inputS);
		
		assertEquals("embedded,100.0,100.0,file,src/res/image0.png",tester.toString());
				
		EmbeddedImage tester2 = (EmbeddedImage) sf.createShape(new ByteArrayInputStream(tester.toString().getBytes()));
		
    	assertEquals(tester2.toString(),tester.toString());
    	
    	// load a second one with a different image
    	inputS = new ByteArrayInputStream("embedded,200,200,file,src/resources/testImage2.jpg".getBytes());
    	
    	EmbeddedImage tester3 = (EmbeddedImage) sf.createShape(inputS);
		
		assertEquals("embedded,200.0,200.0,file,src/res/image1.png",tester3.toString());
				
		EmbeddedImage tester4 = (EmbeddedImage) sf.createShape(new ByteArrayInputStream(tester3.toString().getBytes()));
		
		assertEquals(tester3.toString(),tester4.toString());
		
    	// load a third one cause why not
    	inputS = new ByteArrayInputStream("embedded,200,200,file,src/resources/testImage3.jpg".getBytes());
    	
    	EmbeddedImage tester5 = (EmbeddedImage) sf.createShape(inputS);
		
		assertEquals("embedded,200.0,200.0,file,src/res/image2.png",tester5.toString());
				
		EmbeddedImage tester6 = (EmbeddedImage) sf.createShape(new ByteArrayInputStream(tester5.toString().getBytes()));
		
		assertEquals(tester5.toString(),tester6.toString());
    }
    @Test
    public void testRenderEmbedded() throws ShapeException, IOException {
        // Setup
		ShapeFactory sf = new ShapeFactory();
		
		//looking at it as file		
		InputStream inputS = new ByteArrayInputStream("embedded,100,300,file,src/resources/testImage1.jpg".getBytes());
		EmbeddedImage testShape = (EmbeddedImage) sf.createShape(inputS);
        
        BufferedImage bImg = new BufferedImage(100, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 100, 300);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);
        
        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/embedded1.png")));
        
        // Setup
		inputS = new ByteArrayInputStream("embedded,200,100,file,src/resources/testImage2.jpg".getBytes());
		testShape = (EmbeddedImage) sf.createShape(inputS);
        bImg = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
        graphics = bImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 200, 100);
        graphics.setColor(Color.BLACK);

        // Stimulus
        testShape.draw(graphics);

        // Write observed results to a file so it can be manual compared
        assertTrue(ImageIO.write(bImg, "png", new File("src/resources/embedded2.png")));
    }
}
