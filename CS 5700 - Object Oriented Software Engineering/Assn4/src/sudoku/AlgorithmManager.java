package sudoku;

import java.util.ArrayList;

public class AlgorithmManager {
	protected ArrayList<SolvingAlgorithm> algorithms;
	private SolvingAlgorithmGuess guesser = new SolvingAlgorithmGuess();

	protected SudokuSolver solver;
	
	
	public AlgorithmManager(SudokuBoard board, SudokuSolver solver) {
		algorithms = new ArrayList<SolvingAlgorithm>();
		algorithms.add(new SolvingAlgorithmSingleSolution());
		algorithms.add(new SolvingAlgorithmSingleInstance());
		algorithms.add(new SolvingAlgorithmTwins());
		guesser.setAlgManager(this);	
		this.solver = solver;
	}

	public String getTimeReport() {
		String toReturn = "";
		for(SolvingAlgorithm alg:algorithms) {
			toReturn += alg.getClass().getSimpleName()+ ": "+alg.timesUsed+" uses, "+((double)alg.timeSpent/1000000)+" ms\n";
		}
		toReturn += guesser.getClass().getSimpleName()+ ": "+guesser.timesUsed+" uses, "+((double)guesser.timeSpent/1000000)+" ms\n";
		return toReturn;
	}
	// runs the algorithm structure and returns whether or not the board we reached was solved
	public void runAlgorithm() {
		boolean affected = false;
		
		for(SolvingAlgorithm alg:algorithms) {
			if(alg.Solve(solver.board)) {
				affected = true;
				break;
			}
		}
		if(affected == false) {
			guesser.Solve(solver.board);
		}
		checkDone();
	}
	public boolean solveBoard() {
		while(!"Solved".equals(solver.status)&&!"Unsolvable".equals(solver.status)) {
			runAlgorithm();
		}
		if("Solved".equals(solver.status)) {
			return true;
		}
		else {
			return false;
		}
	}
	// returns whether or not the board has been solved correctly. If not, it checks if we need to move to a different board in the stack
	protected void checkDone() {
		if(solver.board.getOrdered().size()==0) {
			solver.status = "Solved";
			return;
		}		
		if(guesser.boardStates.size()>0&&"[]".equals(solver.board.getOrdered().get(0).toString())) {
			solver.board = guesser.boardStates.pop();
		}
		if(solver.board.getOrdered().get(0).getSubscript().size()==0) {
			solver.status = "Unsolvable";
		}
	}
	
}
