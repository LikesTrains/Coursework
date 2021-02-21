package sudoku;

import java.util.HashSet;

public class SudokuCell {
	
	private boolean modifiable = false;
	private HashSet<String> subscript;
	private String value;
	private int i, j;
	
	public SudokuCell() {
		setSubscript(new HashSet<String>());
		setValue("-");
	}
	public SudokuCell(String value) {
		setSubscript(new HashSet<String>());
		setValue(value);
	}
	

	public HashSet<String> getSubscript() {
		return subscript;
	}

	public void setCoordinates(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public void setSubscript(HashSet<String> subscript) {
		this.subscript = subscript;
	}


	public String getValue() {
		return value;
	}
	
	public int[] getCoordinates() {
		int[] coords = new int[2];
		coords[0] = this.i;
		coords[1] = this.j;
		return coords;
	}


	public void setValue(String value) {
		this.value = value;
		this.subscript = new HashSet<String>();
	}
	public String toString() {
		String returnable = "";
		if("-".equals(this.value)) {
			returnable = this.getSubscript().toString();
		}
		else {
			returnable = this.getValue();
		}
		return returnable;
	}
	public boolean isModifiable() {
		return modifiable;
	}
	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}
}
