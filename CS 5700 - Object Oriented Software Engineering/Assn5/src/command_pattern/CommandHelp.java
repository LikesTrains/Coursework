package command_pattern;

public class CommandHelp implements Command {

	public CommandHelp() {
	}

	@Override
	public void execute() {
		System.out.println("List of commands:");
		System.out.println("\trow column value - Places the specified value onto the cell determined by the row and column");
		System.out.println("\tundo - Undoes the last change performed on the board");
		System.out.println("\thelp - Displays list of commands");
		System.out.println("\thint - Gives user the value of an empty cell");
		System.out.println("\texit - Ends game");
	}

}
