package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import shapes.Shape;
import shapes.ShapeException;
import shapes.ShapeFactory;

class GeneralTest {

	@Test
	public void testMultipleParts() throws NumberFormatException, ShapeException, IOException, InterruptedException {
		String in = "Point,2,5";
		InputStream inS = new ByteArrayInputStream(in.getBytes());
		
		ShapeFactory sf = new ShapeFactory();
		
		Shape result = sf.createShape(inS);
		
		OutputStream outS = new FileOutputStream("src/resources/testOutput1.txt");
		result.outputShape(outS);
		
		Thread.sleep(20);
		
		inS = new FileInputStream("src/resources/testOutput1.txt");
		
		Shape checker = sf.createShape(in);
		assertTrue(checker.equals(result));
		
		inS.close();
		inS = new FileInputStream("src/resources/script2.txt");
		
		result = sf.createShape(inS);
		
		outS = new FileOutputStream("src/resources/testOutput2.txt");
		result.outputShape(outS);
		
		Thread.sleep(20);
		
		inS = new FileInputStream("src/resources/testOutput2.txt");
		
		checker = sf.createShape(inS);
		
		assertTrue(checker.equals(result));
		assertFalse(checker.equals(null));
	}

}
