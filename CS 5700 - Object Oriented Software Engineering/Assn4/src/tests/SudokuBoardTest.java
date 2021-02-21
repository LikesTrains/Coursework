package tests;
import sudoku.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class SudokuBoardTest {

	@Test
	void testValidConstruction() throws Exception {	
		InputStream inS = new FileInputStream("resources/InputFiles/Puzzle-9x9-0001.txt");
		SudokuBoard test = new SudokuBoard(inS);
		assertEquals("valid",test.validationMessage);
		assertEquals("9\n" + 
				"1 2 3 4 5 6 7 8 9\n" + 
				"4 9 - 1 3 6 7 - 8\n" + 
				"- 6 3 5 - - - 9 -\n" + 
				"5 - - - 2 9 3 6 4\n" + 
				"- 2 - 3 1 - - 4 -\n" + 
				"- 7 4 - - - 2 1 -\n" + 
				"- - 1 - 6 4 - 8 -\n" + 
				"1 8 6 9 - - - 2 5\n" + 
				"- 4 - - 5 1 8 3 -\n" + 
				"3 - 9 4 8 - - 7 -",test.toString());
		assertEquals("[2]",test.getCells()[0][2].toString());
		assertEquals("[5]",test.getCells()[0][7].toString());
		assertEquals("[9]",test.getCells()[4][4].toString());
		assertEquals("[2, 6, 7]",test.getCells()[7][3].toString());
		assertEquals("[1, 6]",test.getCells()[8][8].toString());
		
		assertEquals(1,test.getOrdered().get(0).getSubscript().size());
		
		inS = new FileInputStream("resources/InputFiles/Puzzle-4x4-0001.txt");		
		test = new SudokuBoard(inS);
		assertEquals("valid",test.validationMessage);
		assertEquals("4\n" + 
				"1 2 3 4\n" + 
				"2 - 3 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3",test.toString());		
		
		assertEquals("[1, 2, 3, 4]",test.getAllowedValues().toString());
		assertEquals(4,test.getDimension());
		assertEquals(12,test.getSets().size());
		assertEquals(4,test.getOrdered().size());
		assertEquals(1,test.getOrdered().get(0).getSubscript().size());
		
		assertEquals("2",test.getCells()[0][0].toString());
		assertEquals("[4]",test.getCells()[0][1].toString());
		assertEquals("3",test.getCells()[0][2].toString());
		assertEquals("1",test.getCells()[0][3].toString());
		assertEquals("1",test.getCells()[1][0].toString());
		assertEquals("3",test.getCells()[1][1].toString());
		assertEquals("[2]",test.getCells()[1][2].toString());
		assertEquals("4",test.getCells()[1][3].toString());
		assertEquals("3",test.getCells()[2][0].toString());
		assertEquals("1",test.getCells()[2][1].toString());
		assertEquals("4",test.getCells()[2][2].toString());
		assertEquals("[2]",test.getCells()[2][3].toString());
		assertEquals("[4]",test.getCells()[3][0].toString());
		assertEquals("2",test.getCells()[3][1].toString());
		assertEquals("1",test.getCells()[3][2].toString());
		assertEquals("3",test.getCells()[3][3].toString());		
	}
	@Test
	void testInvalidConstruction() throws Exception {
				
		InputStream inS = new ByteArrayInputStream("4\n1 2 3 4 5 \n2 - 3 1 \n1 3 - 4 \n3 1 4 - \n- 2 1 3".getBytes());
		SudokuBoard board = new SudokuBoard(inS);	
		assertEquals(board.validationMessage,"Number of allowed values given does not coincide with dimensions of puzzle");
		
		inS = new ByteArrayInputStream("4\n1 2 3 4 \n2 - 3 1 \n1 5 - 4 \n3 1 4 - \n- 2 1 3".getBytes());
		board = new SudokuBoard(inS);	
		assertEquals(board.validationMessage,"A cell did not contain an allowed value");
		
		inS = new ByteArrayInputStream("4\n1 2 3 4 \n2 - 3 2 \n1 3 - 4 \n3 1 4 - \n- 2 1 3".getBytes());
		board = new SudokuBoard(inS);	
		assertEquals(board.validationMessage,"Duplicate values found");
		
		inS = new ByteArrayInputStream("4\n1 2 3 4 \n- - - - \n- - - - \n- - - - \n- - - - \n- - - -".getBytes());
		board = new SudokuBoard(inS);	
		assertEquals(board.validationMessage,"Number of rows provided does not match expected sudoku shape");
		
		inS = new ByteArrayInputStream("4\n1 2 3 4 \n- - - - 1\n- - - - \n- - - - \n- - - - ".getBytes());
		board = new SudokuBoard(inS);	
		assertEquals(board.validationMessage,"Invalid number of columns");
	}
	@Test
	void testBoardOutput() throws Exception{
		InputStream inS = new ByteArrayInputStream("4\n1 2 3 4 \n2 - 3 1 \n1 3 - 4 \n3 1 4 - \n- 2 1 3".getBytes());
		SudokuBoard board = new SudokuBoard(inS);
		OutputStream outS = new ByteArrayOutputStream();
		
		board.outputBoard(outS);
		assertEquals("4\n" + 
				"1 2 3 4\n" + 
				"2 - 3 1\n" + 
				"1 3 - 4\n" + 
				"3 1 4 -\n" + 
				"- 2 1 3",outS.toString());	
	}
	
	@Test
	void testDefault(){
		SudokuBoard tester = new SudokuBoard();
		assertEquals(new HashSet<String>(), tester.getAllowedValues());
		assertEquals(new ArrayList<HashSet<String>>(), tester.getSets());
	}
}
