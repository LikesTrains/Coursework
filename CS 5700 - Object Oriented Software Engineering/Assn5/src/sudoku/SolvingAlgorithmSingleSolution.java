package sudoku;

import java.util.ArrayList;

public class SolvingAlgorithmSingleSolution extends SolvingAlgorithm {
	
	public SolvingAlgorithmSingleSolution() {
	}

	@Override
	protected SudokuCell[] performAlgorithm(SudokuBoard board) {
		if(singleSolutionWorks(board)==false) {
			return new SudokuCell[0];
		}
		ArrayList <SudokuCell> toModify = new ArrayList<SudokuCell>();
		for(SudokuCell cell:board.getOrdered()) {
			if(cell.getSubscript().size()==1) {
				toModify.add(cell);
			}
			else {
				break;
			}
		}
		for(SudokuCell cell:toModify) {
			board.getOrdered().remove(cell);
			cell.setValue((String) cell.getSubscript().toArray()[0]);
		}
		
		SudokuCell[] modded = new SudokuCell[toModify.size()];
		toModify.toArray(modded);
		
		return modded;
	}
	private boolean singleSolutionWorks(SudokuBoard board) {
		return(board.getOrdered().get(0).getSubscript().size()==1);
	}
}
