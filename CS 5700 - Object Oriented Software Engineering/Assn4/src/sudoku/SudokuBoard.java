package sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class SudokuBoard {

	private SudokuCell [][] cells;
	
	public String stringInput;
	public String validationMessage;
	
	private ArrayList<HashSet<String>> sets;
	private HashSet<String> allowedValues;
	private int dimension;
	private ArrayList<SudokuCell> orderedCells;
		
	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public SudokuCell[][] getCells() {
		return cells;
	}

	public ArrayList<HashSet<String>> getSets() {
		return sets;
	}

	public HashSet<String> getAllowedValues() {
		return allowedValues;
	}

	public ArrayList<SudokuCell> getOrdered() {
		return orderedCells;
	}	
	
	public SudokuBoard() {
		allowedValues = new HashSet<String>();
		sets = new ArrayList<HashSet<String>>();
	}
	
	public SudokuBoard(InputStream in) throws Exception {
		allowedValues = new HashSet<String>();
		sets = new ArrayList<HashSet<String>>();
		stringInput = getString(in);
		validationMessage = "valid";
		
		validateParams(stringInput);
		if(!"valid".equals(validationMessage)) {
			return;
		}
		loadCells(stringInput);
		validateBoard();
		if(!"valid".equals(validationMessage)) {
			return;
		}
		createSubscripts();
		createSolutionSets();
		makeHeap();
	}
	
	private String getString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String fin = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            fin = fin.concat(line).concat("\n");
        }
		return fin;
	}
	
	public void loadCells(String in) {
		String [] lines = in.split("\n");
		setDimension(Integer.parseInt(lines[0].trim()));
		cells = new SudokuCell[dimension][dimension];
		
		String[] params = lines[1].split(" ");
		for (String param:params) {
			allowedValues.add(param.trim());
		}
		
		for(int i = 0; i<this.dimension;i++) {
			String [] values = lines[i+2].split(" ");
			for(int j = 0; j<this.dimension;j++) {
				if(!"-".equals(values[j])) {
					cells[i][j]= new SudokuCell(values[j].trim());
				}
				else {
					cells[i][j]= new SudokuCell();
				}
				cells[i][j].setCoordinates(i, j);
			}
		}
	}
	
	protected void validateParams(String in){
		
		String[] lines = in.split("\n");
		
		if(lines[0].split(" ").length!=1) {
			validationMessage ="More than one parameter on line one";
			return;
		}
		
		int dimension = Integer.parseInt(lines[0].trim());
		
		if(dimension!=4 && dimension!=9 && dimension!=16 && dimension!=25 && dimension!=36) {
			validationMessage = "Invalid dimension";
			return;
		}
		
		if(lines.length != dimension+2) {
			validationMessage ="Number of rows provided does not match expected sudoku shape";
			return;
		}
		
		HashSet<String> allowedValues = new HashSet<String>();
		String[] params = lines[1].split(" ");
		for (String param:params) {
			allowedValues.add(param.trim());
		}
		
		if(allowedValues.size()!=dimension) {
			validationMessage ="Number of allowed values given does not coincide with dimensions of puzzle";
			return;
		}
		
		
		for(int i = 0; i<dimension;i++) {
			String [] row = lines[i+2].split(" ");
			for(int j = 0; j<dimension;j++) {
				if(row.length!=4 && row.length!=9 && row.length!=16 && row.length!=25 && row.length!=36) {
					validationMessage ="Invalid number of columns";
					return;
				}
			}
		}
	}
	protected void validateBoard(){
		validateCellSymbols();
		validateNoRepeats();
	}
	
	protected void validateCellSymbols(){
		for (SudokuCell[] row:cells) {
			for(SudokuCell cell:row) {
				if(!"-".equals(cell.getValue())) {
					HashSet<String> temp = new HashSet<String>();
					temp.add(cell.getValue());
					temp.removeAll(allowedValues);
					if(temp.size()!=0) {
						validationMessage ="A cell did not contain an allowed value";
					}
				}
			}
		}
	}
	
	private void validateNoRepeats(){
		// first, initialize them based on the row
		for(int rowI = 0; rowI<dimension;rowI++) {
			checkNoRepeatsInCells(cells[rowI]);
		}
		// second, update them based on the column
		for(int colI = 0; colI<dimension;colI++) {
			SudokuCell[] toUpdate = new SudokuCell[dimension];
			for(int cellI = 0; cellI<dimension;cellI++) {
				toUpdate[cellI] = cells[cellI][colI];
			}
			checkNoRepeatsInCells(toUpdate);
		}
		// finally, update them based on the block
		
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
			checkNoRepeatsInCells(toUpdate);
		}
	}
	
	private void checkNoRepeatsInCells(SudokuCell[] cells){
		HashSet<String> vals = new HashSet<String>();
		for(SudokuCell cell:cells) {
			if(!"-".equals(cell.getValue())) {
				if(vals.contains(cell.getValue())) validationMessage="Duplicate values found";
				vals.add(cell.getValue());	
			}
		}
	}
	
	private void createSubscripts() {
		// initialize all cells with the allowed values 
		for(int rowI = 0; rowI<dimension;rowI++) {
			for(int colI = 0; colI<dimension;colI++) {
				cells[rowI][colI].getSubscript().addAll(allowedValues);
			}
		}
		// update all subscripts based on the original 
		updateAllSubscripts();
	}
	
	private void updateAllSubscripts() {
		// first, initialize them based on the row
		for(int rowI = 0; rowI<dimension;rowI++) {
			updateCellsSubscript(cells[rowI]);
		}
		// second, update them based on the column
		for(int colI = 0; colI<dimension;colI++) {
			SudokuCell[] toUpdate = new SudokuCell[dimension];
			for(int cellI = 0; cellI<dimension;cellI++) {
				toUpdate[cellI] = cells[cellI][colI];
			}
			updateCellsSubscript(toUpdate);
		}
		// finally, update them based on the block
		
		for(int blockI = 0; blockI<dimension;blockI++) {
			updateCellsSubscript(getBlock(blockI));
		}
	}
	
	private SudokuCell[] getBlock(int blockI) {
		SudokuCell[] toUpdate = new SudokuCell[dimension];
		int blockLen = (int)(Math.sqrt(dimension));
		int updateI=0;
		for(int row = (int)(blockI/blockLen)*blockLen;row<(int)((blockI/blockLen)*blockLen)+blockLen;row++){
			for(int col = (int)(blockI%blockLen)*blockLen;col<(int)((blockI%blockLen)*blockLen)+blockLen;col++){
				toUpdate[updateI] = cells[row][col];
				updateI++;
			}
		}
		return toUpdate;
	}
	
	protected void updateAllNeighbors(SudokuCell cell) {		
		updateCellsSubscript(cells[cell.getCoordinates()[0]]);
		
		int colI = cell.getCoordinates()[1];
		SudokuCell[] toUpdate = new SudokuCell[dimension];
		for(int cellI = 0; cellI<dimension;cellI++) {
			toUpdate[cellI] = cells[cellI][colI];
		}
		updateCellsSubscript(toUpdate);
		
		int blockI = ((int)(cell.getCoordinates()[0]/Math.sqrt(dimension))*(int)(Math.sqrt(dimension))) + (int)(cell.getCoordinates()[1]/Math.sqrt(dimension));
		updateCellsSubscript(getBlock(blockI));
	}
	
	private void updateCellsSubscript(SudokuCell[] cells) {
		HashSet<String> existingVals = new HashSet<String>();
		for(int cellI = 0; cellI<dimension;cellI++) {
			if(!"-".equals(cells[cellI].getValue())) {
				existingVals.add(cells[cellI].getValue());
			}
		}
		for(int cellI = 0; cellI<dimension;cellI++){
			if("-".equals(cells[cellI].getValue())){
				cells[cellI].getSubscript().removeAll(existingVals);				
			}
		}
	}
	
	protected void createSolutionSets() {
		// update them based on the row
		for(int rowI = 0; rowI<dimension;rowI++) {
			HashSet<String> existingVals = new HashSet<String>();
			for(int cellI = 0; cellI<dimension;cellI++) {
				if(!"-".equals(cells[rowI][cellI].getValue())) {
					existingVals.add(cells[rowI][cellI].getValue());
				}
			}
			HashSet<String> rowSet = new HashSet<String>();
			rowSet.addAll(allowedValues);
			rowSet.removeAll(existingVals);
			sets.add(rowSet);
		}
		// update them based on the column
		for(int colI = 0; colI<dimension;colI++) {
			HashSet<String> existingVals = new HashSet<String>();
			for(int cellI = 0; cellI<dimension;cellI++) {
				if(!"-".equals(cells[cellI][colI].getValue())) {
					existingVals.add(cells[cellI][colI].getValue());
				}
			}
			HashSet<String> colSet = new HashSet<String>();
			colSet.addAll(allowedValues);
			colSet.removeAll(existingVals);
			sets.add(colSet);
		}
		// update them based on the block
		
		int blockLen = (int)(Math.sqrt(dimension));
		for(int block = 0; block<dimension;block++) {
			HashSet<String> existingVals = new HashSet<String>();
			for(int row = (int)(block/blockLen)*blockLen;row<(int)((block/blockLen)*blockLen)+blockLen;row++){
				for(int col = (int)(block%blockLen)*blockLen;col<(int)((block%blockLen)*blockLen)+blockLen;col++){
					if(!"-".equals(cells[row][col].getValue())) {
						existingVals.add(cells[row][col].getValue());
					}
				}
			}
			HashSet<String> blockSet = new HashSet<String>();
			blockSet.addAll(allowedValues);
			blockSet.removeAll(existingVals);
			sets.add(blockSet);
		}
	}
	
	private void makeHeap() {
		//orderedCells = new PriorityQueue<SudokuCell>(dimension*dimension, new SortCells());
		orderedCells = new ArrayList<SudokuCell>(dimension*dimension);		
		for (SudokuCell[] row:cells) {
			for(SudokuCell cell:row) {
				if("-".equals(cell.getValue())) {
					orderedCells.add(cell);
				}
			}
		}
		sortCellList();
	}
	
	protected void sortCellList() {
		orderedCells.sort((c1,c2)->c1.getSubscript().size() - c2.getSubscript().size());
	}

	public String toString() {
		String toReturn = "";
		
		toReturn+=dimension+"\n";
		for (String val:this.allowedValues) {
			toReturn+=val+" ";
		}
		toReturn = toReturn.substring(0, toReturn.length()-1)+"\n";
		
		for(SudokuCell[] row:cells) {
			for(SudokuCell cell:row) {
				toReturn += cell.getValue() + " ";
			}
			toReturn = toReturn.substring(0, toReturn.length()-1);
			toReturn += "\n";
		}
		toReturn = toReturn.substring(0, toReturn.length()-1);
		return toReturn;
	}
	public void outputBoard(OutputStream os) throws IOException {
		PrintWriter pw = new PrintWriter(os);
		String out = this.toString();
		pw.print(out);
		pw.close();
	}
}
