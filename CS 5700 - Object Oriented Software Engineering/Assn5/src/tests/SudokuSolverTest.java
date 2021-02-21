package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import sudoku.SudokuSolver;

class SudokuSolverTest {

	@Test
	void testValidConstruction() throws Exception {
		InputStream inS = new ByteArrayInputStream("4\n1 2 3 4\n2 - 3 1\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		OutputStream outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		System.out.println(outS.toString());

	}
	@Test
	void testInvalidConstruction() throws Exception {
		InputStream inS = new ByteArrayInputStream("4 4\n1 2 3 4\n2 - 3 1\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		OutputStream outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("4 4\n" + 
				"1 2 3 4\n" + 
				"2 - 3 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"\n" + 
				"Invalid: More than one parameter on line one",outS.toString());
		inS = new ByteArrayInputStream("5\n1 2 3 4\n2 - 3 1\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("5\n" + 
				"1 2 3 4\n" + 
				"2 - 3 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"\n" + 
				"Invalid: Invalid dimension",outS.toString());
		inS = new ByteArrayInputStream("4\n1 2 3 4\n2 - 3 1\n1 3 - 4\n3 1 4 -\n- 2 1 3\n- - - -".getBytes());
		outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("4\n" + 
				"1 2 3 4\n" + 
				"2 - 3 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"- - - -\n" + 
				"\n" + 
				"Invalid: Number of rows provided does not match expected sudoku shape",outS.toString());
		
		inS = new ByteArrayInputStream("4\n1 2 3 4 5\n2 - 3 1\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("4\n" + 
				"1 2 3 4 5\n" + 
				"2 - 3 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"\n" + 
				"Invalid: Number of allowed values given does not coincide with dimensions of puzzle",outS.toString());
		
		inS = new ByteArrayInputStream("4\n1 2 3 4\n2 - 2 1\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("4\n" + 
				"1 2 3 4\n" + 
				"2 - 2 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"\n" + 
				"Invalid: Duplicate values found",outS.toString());
		
		inS = new ByteArrayInputStream("4\n1 2 3 4\n2 - 3 1 -\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("4\n" + 
				"1 2 3 4\n" + 
				"2 - 3 1 -\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"\n" + 
				"Invalid: Invalid number of columns",outS.toString());
		
		inS = new ByteArrayInputStream("4\n1 2 3 4\n2 - 3 5\n1 3 - 4\n3 1 4 -\n- 2 1 3".getBytes());
		outS = new ByteArrayOutputStream();
		
		new SudokuSolver(inS,outS);
		
		assertEquals("4\n" + 
				"1 2 3 4\n" + 
				"2 - 3 5\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3\n" + 
				"\n" + 
				"Invalid: A cell did not contain an allowed value",outS.toString());
	}
}
