package tests;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.Test;

import command_pattern.Driver;


class DriverTest {

	@Test
	void testCorrectMain1() throws Exception {
	    String[] args = {"resources/InputFiles/Puzzle-4x4-0001.txt"};	    
	    final FileInputStream fips = new FileInputStream(new File("resources/testFiles/4x4-1-solution.txt"));
	    System.setIn(fips);
	    Driver.main(args);
	    System.setIn(System.in);
	}
	@Test
	void testCorrectMain2() throws Exception {
	    String[] args = {"resources/InputFiles/Puzzle-9x9-0301.txt"};	    
	    final FileInputStream fips = new FileInputStream(new File("resources/testFiles/4x4-1-solution.txt"));
	    System.setIn(fips);
	    Driver.main(args);
	    System.setIn(System.in);
	}
	
	@Test
	void testUtilities() throws Exception{
		String[] help = {"-h"};
		Driver.main(help);
		String[] args = {"resources/InputFiles/Puzzle-4x4-0001.txt"};	    
	    final FileInputStream fips = new FileInputStream(new File("resources/testFiles/utility-tester"));
	    System.setIn(fips);
	    Driver.main(args);
	    String[] args2 = {"resources/InputFiles/Puzzle-4x4-0001.txt"};	    
	    final FileInputStream fips2 = new FileInputStream(new File("resources/testFiles/utility-tester-2"));
	    System.setIn(fips2);
	    Driver.main(args2);
	    System.setIn(System.in);
	}
	
	@Test
	void testWrongFormats() throws Exception{
		String[] args = {"resources/InputFiles/Puzzle-4x4-0001.txt"};	    
	    final FileInputStream fips = new FileInputStream(new File("resources/testFiles/wrong-formats"));
	    System.setIn(fips);
	    Driver.main(args);
	    System.setIn(System.in);
	}
	
	@Test
	void testIncorrectMain() throws Exception {
	    String[] args = {"resources/testFiles/invalidInput1"};
	    Driver.main(args);
	    String[] args1 = {"resources/testFiles/invalidInput2"};
	    Driver.main(args1);
	    String[] args2 = {"resources/testFiles/invalidInput3"};
	    Driver.main(args2);
	    String[] args3 = {"resources/testFiles/invalidInput4"};
	    Driver.main(args3);
	    
	    String[] args4 = {};
	    Driver.main(args4);
	    String[] args5 = {"big","lul","lmao"};
	    Driver.main(args5);
	    String[] args6 = {"nonexistentFile"};
	    Driver.main(args6);
	    
	}
}
