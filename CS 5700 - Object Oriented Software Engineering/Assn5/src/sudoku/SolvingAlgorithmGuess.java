package sudoku;

import java.io.ByteArrayInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class SolvingAlgorithmGuess extends SolvingAlgorithm{

	AlgorithmManager man;
	ArrayDeque <SudokuBoard> boardStates = new ArrayDeque<SudokuBoard>();
	
	public SolvingAlgorithmGuess() {
	}
	public void setAlgManager(AlgorithmManager newMan) {
		man = newMan;
	}

	@Override
	protected SudokuCell[] performAlgorithm(SudokuBoard board) {
		SudokuCell cell = board.getOrdered().get(0);
		
		ArrayList<String>  toModify  = new ArrayList<String>();
		toModify.addAll(cell.getSubscript());
		String toSet = toModify.get(0);
		
		toModify.remove(0);
					
		SudokuBoard curState = null;
		
		try {
			curState = new SudokuBoard(new ByteArrayInputStream(board.toString().getBytes()));
		} catch (Exception e) {
			// SudokuBoard CAN throw IO exceptions, this only happens when they're made from files and not from ByteAttayInputStreams. Still have to a try/catch but this should never throw an exception
			e.printStackTrace();
		}
		// remove choice we made from board and push it onto stack in case we ever need to go back to it.
		curState.getCells()[cell.getCoordinates()[0]][cell.getCoordinates()[1]].setSubscript(new HashSet<String>(toModify));
		if("valid"==curState.validationMessage) {
			boardStates.push(curState);
		}
		
		cell.setValue(toSet);
		SudokuCell[] toReturn = {cell};
		board.getOrdered().remove(cell);
		return toReturn;
	}

}
