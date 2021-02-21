package sudoku;

public abstract class SolvingAlgorithm {
	long timeSpent=0,timesUsed=0;
	public SolvingAlgorithm() {
	}
	// runs algorithm and returns whether or not it affected the board
	public boolean Solve(SudokuBoard board) {
		long timeStart = System.nanoTime();
		SudokuCell[] modified = performAlgorithm(board);
		updateSubscripts(modified, board);
		timeMethod(timeStart);
		return checkAffected(modified);
	}
	protected abstract SudokuCell[] performAlgorithm(SudokuBoard board);
	protected final void updateSubscripts(SudokuCell[] cells, SudokuBoard board) {
		for(SudokuCell cell:cells) {
			board.updateAllNeighbors(cell);
		}		
		board.sortCellList();
	}
	protected final void timeMethod(long timeStart) {
		long timeEnd = System.nanoTime();
		timeSpent += (timeEnd - timeStart);
		timesUsed +=1;
	}
	protected final boolean checkAffected(SudokuCell[] modified) {
		if(modified.length==0) {
			return false;
		}
		else {
			return true;
		}
	}
}
