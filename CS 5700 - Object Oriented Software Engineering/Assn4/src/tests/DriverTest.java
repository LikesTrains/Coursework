package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.jupiter.api.Test;

import sudoku.Driver;


class DriverTest {

	@Test
	void testCorrectMain() throws Exception {
	    String[] args4_1 = {"resources/InputFiles/Puzzle-4x4-0001.txt", "resources/testFiles/testMain4_1.txt"};
	    Driver.main(args4_1);
	    
	    String[] args4_2 = {"resources/InputFiles/Puzzle-4x4-0002.txt", "resources/testFiles/testMain4_2.txt"};
	    Driver.main(args4_2);
	    
	    String[] args4_3 = {"resources/InputFiles/Puzzle-4x4-0101.txt", "resources/testFiles/testMain4_3.txt"};
	    Driver.main(args4_3);
	    
	    String[] args4_4 = {"resources/InputFiles/Puzzle-4x4-0201.txt", "resources/testFiles/testMain4_4.txt"};
	    Driver.main(args4_4);
	    
	    String[] args4_5 = {"resources/InputFiles/Puzzle-4x4-0903.txt", "resources/testFiles/testMain4_5.txt"};
	    Driver.main(args4_5);
	    
	    String[] args9_1 = {"resources/InputFiles/Puzzle-9x9-0001.txt", "resources/testFiles/testMain9_1.txt"};
	    Driver.main(args9_1);
	    
	    String[] args9_2 = {"resources/InputFiles/Puzzle-9x9-0101.txt", "resources/testFiles/testMain9_2.txt"};
	    Driver.main(args9_2);
	    
	    String[] args9_3 = {"resources/InputFiles/Puzzle-9x9-0102.txt", "resources/testFiles/testMain9_3.txt"};
	    Driver.main(args9_3);
	    
	    String[] args9_4 = {"resources/InputFiles/Puzzle-9x9-0103.txt", "resources/testFiles/testMain9_4.txt"};
	    Driver.main(args9_4);
	    
	    String[] args9_5 = {"resources/InputFiles/Puzzle-9x9-0201.txt", "resources/testFiles/testMain9_5.txt"};
	    Driver.main(args9_5);
	    
	    String[] args16_1 = {"resources/InputFiles/Puzzle-16x16-0001.txt", "resources/testFiles/testMain16_1.txt"};
	    Driver.main(args16_1);
	    
	    String[] args16_2 = {"resources/InputFiles/Puzzle-16x16-0101.txt", "resources/testFiles/testMain16_2.txt"};
	    Driver.main(args16_2);
	    
	    String[] args16_3 = {"resources/InputFiles/Puzzle-16x16-0102.txt", "resources/testFiles/testMain16_3.txt"};
	    Driver.main(args16_3);
	    
	    String[] args16_4 = {"resources/InputFiles/Puzzle-16x16-0301.txt", "resources/testFiles/testMain16_4.txt"};
	    Driver.main(args16_4);
	    
	    String[] args16_5 = {"resources/InputFiles/Puzzle-16x16-0901.txt", "resources/testFiles/testMain16_5.txt"};
	    Driver.main(args16_5);
	    
	    String[] args25_1 = {"resources/InputFiles/Puzzle-25x25-0101.txt", "resources/testFiles/testMain25_1.txt"};
	    Driver.main(args25_1);
	    
	    String[] hard_1 = {"resources/InputFiles/hardBoi.txt", "resources/testFiles/hard_1.txt"};
	    Driver.main(hard_1);
	    
	    String[] usolvable_1 = {"resources/InputFiles/unsolvable1.txt", "resources/testFiles/unsolvable_1.txt"};
	    Driver.main(usolvable_1);
	    
	    String[] argsH = {"-h"};
	    Driver.main(argsH);
	    
	    Driver driver = new Driver();
	    assertEquals(Driver.class,driver.getClass());
	}
	@Test
	void testIncorrectMain() throws Exception {
	    String[] args = {"resources/testFiles/invalidInput1.txt", "resources/testFiles/invalidOutput1.txt"};
	    Driver.main(args);
	    String[] args1 = {"resources/testFiles/invalidInput2.txt", "resources/testFiles/invalidOutput2.txt"};
	    Driver.main(args1);
	    String[] args2 = {"resources/testFiles/invalidInput3.txt", "resources/testFiles/invalidOutput3.txt"};
	    Driver.main(args2);
	    String[] args3 = {"resources/testFiles/invalidInput4.txt", "resources/testFiles/invalidOutput4.txt"};
	    Driver.main(args3);
	    
	    String[] args4 = {};
	    Driver.main(args4);
	    String[] args5 = {"big","lul","lmao"};
	    Driver.main(args5);
	    String[] args6 = {"nonexistentFile"};
	    Driver.main(args6);
	    String[] args7 = {"resources/InputFiles/Puzzle-4x4-0001.txt","resources/testFiles"};
	    Driver.main(args7);
	    
	}
	@Test
	void testValidator() throws FileNotFoundException {
		String[] args1 = {"resources/testFiles/invalidInput1"};
		Driver.processInput(args1);
		assertEquals(System.out, Driver.os);
		assertEquals(FileInputStream.class,Driver.is.getClass());
		String[] args2 = {"resources/testFiles/invalidInput1","resources/testFiles/invalidOutput2.txt"};
		Driver.processInput(args2);
		assertEquals(FileOutputStream.class,Driver.os.getClass());
		assertEquals(FileInputStream.class,Driver.is.getClass());
	}
}
