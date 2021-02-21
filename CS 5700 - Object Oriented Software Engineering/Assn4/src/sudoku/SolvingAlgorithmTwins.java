package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

public class SolvingAlgorithmTwins extends SolvingAlgorithm {

	public SolvingAlgorithmTwins() {
		// TODO Auto-generated constructor stub
	}

	private SudokuCell[] performAlgorithmOnCells(SudokuCell[] cells) {
		HashSet<SudokuCell> toReturn = new HashSet<SudokuCell>();
		
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
		ArrayList <Map.Entry<String, ArrayList<SudokuCell>>> toRemove = new ArrayList<Map.Entry<String, ArrayList<SudokuCell>>>();
		
		for (Map.Entry<String, ArrayList<SudokuCell>> entry : dict.entrySet()) {
			if(entry.getValue().size()!=2){
				toRemove.add(entry);
			}
		}
		for(Object o:toRemove) {
			dict.entrySet().remove(o);
		}
		Object[] entries = new Object[dict.entrySet().size()];
		dict.entrySet().toArray(entries);
		for(int i1 = 0; i1<dict.entrySet().size();i1++) {
			for(int i2=i1+1; i2<dict.entrySet().size();i2++) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, ArrayList<SudokuCell>> entr1 = (Entry<String, ArrayList<SudokuCell>>) entries[i1];
				@SuppressWarnings("unchecked")
				Map.Entry<String, ArrayList<SudokuCell>> entr2 = (Entry<String, ArrayList<SudokuCell>>) entries[i2];
				if(entr1.getValue().equals(entr2.getValue())) {
					String v1 = entr1.getKey();
					String v2 = entr2.getKey();
					for(SudokuCell cell:entr1.getValue()) {
						ArrayList<String> stringsToRemove = new ArrayList<String>();
						boolean changed = false;
						for(String sub:cell.getSubscript()) {
							if(sub.equals(v1)||sub.equals(v2)) {
								continue;
							}
							else {
								stringsToRemove.add(sub);
								changed = true;
							}
						}
						cell.getSubscript().removeAll(stringsToRemove);
						if(changed) {
							toReturn.add(cell);
						}						
					}
				}
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
