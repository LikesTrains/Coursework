package command_pattern;

import sudoku.SudokuBoard;

public class CommandFactory {
	SudokuBoard board, solvedBoard;
	
	public CommandFactory(SudokuBoard board, SudokuBoard solved) {
		this.board = board;
		this.solvedBoard = solved;
	}
	public Command makeCommand(String[] userInput) {
		Command toReturn = new CommandInvalidInput("Unforeseen error...");
		if(userInput.length>0) {
			if(userInput.length==1) {
				if("undo".equals(userInput[0].trim().toLowerCase())) {
					toReturn = new CommandUndo();
				}
				else if("help".equals(userInput[0].trim().toLowerCase())) {
					toReturn = new CommandHelp();
				}
				else if("hint".equals(userInput[0].trim().toLowerCase())) {
					toReturn = new CommandHint(board,solvedBoard);
				}
				else if("exit".equals(userInput[0].trim().toLowerCase())) {
					toReturn = new CommandExit();
				}
				else {
					toReturn = new CommandInvalidInput("Unrecognized input parameter");
				}
			}
			else if(userInput.length==3) {
				
				int i = -1;
				int j = -1;
				try {
					i = Integer.parseInt(userInput[0]);
					j = Integer.parseInt(userInput[1]);
				}
				catch(Exception e) {
					toReturn = new CommandInvalidInput("One of the dimensional arguments did not parse into an int");
				}
				String val = userInput[2];
				if(i!=-1&&j!=-1) {
					if(i<board.getDimension()&&i>=0&&j<board.getDimension()&&j>=0&&board.getAllowedValues().contains(val)&&board.getCells()[i][j].isModifiable()) {
						toReturn  =new CommandModifyCell(this.board,i,j,val);
					}
					else if (i>=board.getDimension()||i<0||j>=board.getDimension()||j<0){
						toReturn = new CommandInvalidInput("Invalid dimensional argument for cell modification");
					}
					else if(!board.getAllowedValues().contains(val)) {
						toReturn = new CommandInvalidInput("Value attempted is not allowed");
					}
					else {
						toReturn = new CommandInvalidInput("Cell specified cannot be modified");
					}
				}
			}
			else {
				toReturn = new CommandInvalidInput("Input does not match expected length");
			}
		}
		else {
			toReturn = new CommandInvalidInput("No parameters specified");
		}
		
		return toReturn;
	}
}
