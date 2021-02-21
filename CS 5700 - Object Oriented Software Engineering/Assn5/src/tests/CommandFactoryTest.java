package tests;

import command_pattern.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommandFactoryTest {

	@Test
	void testFactory() throws Exception {
		String[] files = {"resources/inputFiles/Puzzle-4x4-0001.txt"};		
		Commander com = new Commander(files);		
		CommandFactory factory = new CommandFactory(com.toSolve,com.solved);
		
		String[] help = {"help"};		
		Command tester = factory.makeCommand(help);		
		assertTrue(tester instanceof CommandHelp);
		
		String[] hint = {"hint"};		
		tester = factory.makeCommand(hint);
		assertTrue(tester instanceof CommandHint);
		
		String[] mod = "3 0 4".split(" ");
		tester = factory.makeCommand(mod);
		assertTrue(tester instanceof CommandModifyCell);
		
		String[] undo = {"undo"};
		tester = factory.makeCommand(undo);
		assertTrue(tester instanceof CommandUndo);
		
		String[] none = {};	
		tester = factory.makeCommand(none);		
		assertTrue(tester instanceof CommandInvalidInput);
		
		String[] odd = {"odd"};		
		tester = factory.makeCommand(odd);		
		assertTrue(tester instanceof CommandInvalidInput);
		
		String[] tooMany = "e e e e".split(" ");
		tester = factory.makeCommand(tooMany);
		assertTrue(tester instanceof CommandInvalidInput);
		
		String[] exit = {"exit"};
		tester = factory.makeCommand(exit);
		assertTrue(tester instanceof CommandExit);
	}

}
