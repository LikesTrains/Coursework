package sudoku;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class SudokuSolver {
	
	private String originalBoard;
	public AlgorithmManager algManager;
	protected SudokuBoard board;
	protected String status = "Not Solved";
	OutputStream out;
	
	
	public SudokuSolver(InputStream in, OutputStream out) throws Exception {
		board = new SudokuBoard(in);
		this.out = out;
		if(!"valid".equals(board.validationMessage)) {
			outputFailed();
		}
		else {
			originalBoard = board.toString();
			algManager = new AlgorithmManager(board,this);
			solveBoard();
			outputSolved();
		}
	}
	
	private void solveBoard() {
		if(algManager.solveBoard())
			status = "Solved";
	}
	
	private String getTimeReport() {
		String toReturn = "";
		
		toReturn+= algManager.getTimeReport();
		return toReturn;
	}
	private String getFinalStatus() {
		String toReturn = status + "\n";
		for(SudokuCell[] row:board.getCells()) {
			for(SudokuCell cell:row) {
				toReturn += cell.getValue() + " ";
			}
			toReturn = toReturn.substring(0, toReturn.length()-1);
			toReturn += "\n";
		}
		toReturn = toReturn.substring(0, toReturn.length()-1);
		toReturn+="\n\n";
		return toReturn;
	}
	
	public String toString(){
		String toReturn = originalBoard+"\n\n";
		toReturn += getFinalStatus();
		toReturn+=getTimeReport();
		return toReturn;
	}
	public void outputSolved() throws IOException {
		PrintWriter pw = new PrintWriter(out);
		String out = this.toString();
		pw.print(out);
		pw.close();
	}
	public void outputFailed() throws IOException {
		PrintWriter pw = new PrintWriter(out);
		String out = board.stringInput +"\nInvalid: "+ board.validationMessage;
		pw.print(out);
		pw.close();
	}
}
