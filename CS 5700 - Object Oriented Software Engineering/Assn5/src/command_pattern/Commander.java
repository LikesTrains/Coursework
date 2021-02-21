package command_pattern;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import sudoku.SudokuBoard;
import sudoku.SudokuSolver;

public class Commander {

	public SudokuBoard solved, toSolve;
	private CommandFactory factory;
	private Invoker invoker;
	
	public static InputStream is;
	public static OutputStream os;
	
	
	public Commander(String[] args) throws Exception {
		SudokuSolver solver = null;
		is = null;
		os = System.out;
		processFileInput(args);
		if(is!= null) {
			solver = new SudokuSolver(is,os);
			if(!"Solved".equals(solver.getStatus())) {
				System.out.println("Board is unsolvable");
				return;
			}
		}
		else {
			System.out.println("File was not found");
			return;
		}			
		
		System.out.println("Board was loaded correctly");
		solved = solver.getBoard();
		toSolve = new SudokuBoard(new ByteArrayInputStream(solver.getOriginalBoard().getBytes()));
		factory = new CommandFactory(toSolve,solved);
		invoker = new Invoker();
		this.setMod();
	}
	private void setMod() {
		for(int i = 0; i<toSolve.getDimension();i++) {
			for(int j = 0; j<toSolve.getDimension();j++) {
				if("-".equals(toSolve.getCells()[i][j].getValue())) {
					toSolve.getCells()[i][j].setModifiable(true);
				}
			}
		}
	}
	
	public void playGame() {
		if(solved == null) {
			System.out.println("Board construction was not successful, game will not be initiated");
			return;
		}		
		Scanner scanner = new Scanner(System.in);
		while(!gameOver()) {
			System.out.println(toSolve.lightToString());
			if(!scanner.hasNextLine()) {
				break;
			}
			Command toExecute = factory.makeCommand(scanner.nextLine().split(" "));
			invoker.runCommand(toExecute);
			if(toExecute instanceof CommandExit) {
				break;
			}
		}
		System.out.println("Game over!");
		scanner.close();
	}
	
	public boolean gameOver(){
		return toSolve.toString().equals(solved.toString());
	}
	
	
	public static void processFileInput(String[] args) {
		PrintWriter pw = new PrintWriter(os);
		if(args.length>0) {
			if("-h".equals(args[0].toLowerCase().trim())) {				
				pw.println("The valid command line arguments to start this program are:");
				pw.println("\t-h for help");
				pw.println("\t<inputfile> for the solver to solve a given puzzle and output the result to the screen");
			}
			else if(args.length==1) {
				try {
				is = new FileInputStream(args[0]);
				}
				catch(Exception e) {
					pw.println(e.getLocalizedMessage());
				}
			}
			else {
				pw.println("Error, more than 1 argument specified");
			}
		}
		else {
			pw.println("Error, no argumens specified");
			return;
		}
	}
}
