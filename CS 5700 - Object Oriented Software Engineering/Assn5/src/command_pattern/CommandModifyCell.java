package command_pattern;

import sudoku.SudokuBoard;
import sudoku.SudokuCell;

public class CommandModifyCell implements Command{
	SudokuBoard board;
	int i, j; 
	String val;
	String prevVal;
	
	public CommandModifyCell(SudokuBoard board, int i, int j, String val) {
		this.board = board;
		this.i = i;
		this.j = j;
		this.val = val;		
	}

	@Override
	public void execute() {	
		SudokuCell toModify = board.getCells()[i][j];
		this.prevVal = toModify.getValue();
		toModify.setValue(val);
		System.out.println("Set the value of cell " + i+", "+j+" to "+val);
	}
	
	public void undo() {
		SudokuCell toModify = board.getCells()[i][j];
		toModify.setValue(prevVal);
		System.out.println("Set the value of cell " + i+", "+j+" back to "+prevVal);
	}
}
