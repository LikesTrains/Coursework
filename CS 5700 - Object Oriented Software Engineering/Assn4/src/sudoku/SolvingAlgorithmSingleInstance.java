package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SolvingAlgorithmSingleInstance extends SolvingAlgorithm {

	public SolvingAlgorithmSingleInstance() {
	}
	
	private SudokuCell[] performAlgorithmOnCells(SudokuCell[] cells) {
		ArrayList<SudokuCell> toReturn = new ArrayList<SudokuCell>();
		
		HashMap<String, ArrayList<SudokuCell>>dict = new HashMap<String, ArrayList<SudokuCell>>();
		for(SudokuCell cell:cells) {
			if(cell.getValue()=="-") {
				for(String sub:cell.getSubscript()) {
					ArrayList<SudokuCell> eval = dict.get(sub);
					if(eval==null) {
						ArrayList<SudokuCell> toAdd =  new ArrayList<SudokuCell>();
						toAdd.add(cell);
						dict.put(sub, toAdd);
					}
					else {
						eval.add(cell);						
					}
				}
			}
		}
		for (Map.Entry<String, ArrayList<SudokuCell>> entry : dict.entrySet()) {
		    if(entry.getValue().size()==1) {
		    	HashSet<String> toSet = new HashSet<String>();
		    	toSet.add(entry.getKey());
		    	entry.getValue().get(0).setSubscript(toSet);
		    	toReturn.add(entry.getValue().get(0));
		    }			    
		}
		
		SudokuCell[] finalArr=new SudokuCell[toReturn.size()];
		toReturn.toArray(finalArr);
		return finalArr;
	}
	
	@Override
	protected SudokuCell[] performAlgorithm(SudokuBoard board) {
		HashSet<SudokuCell> toReturn = new HashSet<SudokuCell>();
		
		
		// first, check on the row
		SudokuCell[][] cells = board.getCells();
		int dimension = board.getDimension();
		for(int rowI = 0; rowI<dimension;rowI++) {
			toReturn.addAll(Arrays.asList(performAlgorithmOnCells(cells[rowI])));
		}
				
		// second, check on the columns
		for(int colI = 0; colI<dimension;colI++) {
			SudokuCell[] toUpdate = new SudokuCell[dimension];
			for(int cellI = 0; cellI<dimension;cellI++) {
				toUpdate[cellI] = cells[cellI][colI];
			}
			toReturn.addAll(Arrays.asList(performAlgorithmOnCells(toUpdate)));
		}
		
		// finally, check on the blocks
		
		int blockLen = (int)(Math.sqrt(dimension));
		for(int block = 0; block<dimension;block++) {
			SudokuCell[] toUpdate = new SudokuCell[dimension];
			int updateI=0;
			for(int row = (int)(block/blockLen)*blockLen;row<(int)((block/blockLen)*blockLen)+blockLen;row++){
				for(int col = (int)(block%blockLen)*blockLen;col<(int)((block%blockLen)*blockLen)+blockLen;col++){
					toUpdate[updateI] = cells[row][col];
					updateI++;
				}
			}
			toReturn.addAll(Arrays.asList(performAlgorithmOnCells(toUpdate)));
		}
				
		SudokuCell[] finalArr=new SudokuCell[toReturn.size()];
		toReturn.toArray(finalArr);
		return finalArr;
	}
}
