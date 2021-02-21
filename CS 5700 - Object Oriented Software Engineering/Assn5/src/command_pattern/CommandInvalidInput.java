package command_pattern;

public class CommandInvalidInput implements Command{
	String message;
	
	public CommandInvalidInput(String message) {
		this.message = message;
	}

	@Override
	public void execute() {
		System.out.println(message);
	}

}
