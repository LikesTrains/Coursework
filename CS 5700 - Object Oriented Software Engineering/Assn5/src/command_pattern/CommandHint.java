package command_pattern;

import sudoku.SudokuBoard;

public class CommandHint implements Command {
	String message;
	
	public CommandHint(SudokuBoard current, SudokuBoard solved) {
		message = "No hint available";
		for(int i=0; i<current.getDimension();i++) {
			for(int j=0; j<current.getDimension();j++) {
				if(!current.getCells()[i][j].getValue().equals(solved.getCells()[i][j].getValue())) {
					message = "On cell " + i +" "+ j +" set the value to "+solved.getCells()[i][j].getValue();
					break;
				}
			}
		}
	}

	@Override
	public void execute() {
		System.out.println("Hint:");
		System.out.println(message);		
	}

}
