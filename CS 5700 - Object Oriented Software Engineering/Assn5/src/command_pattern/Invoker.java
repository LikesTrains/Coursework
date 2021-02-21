package command_pattern;

import java.util.Stack;

public class Invoker {
	Stack <CommandModifyCell> modifications;
	
	public Invoker() {
		this.modifications = new Stack<CommandModifyCell>();
	}
	
	public void runCommand(Command c) {
		if(c instanceof CommandUndo) {
			if(!modifications.isEmpty()) {
				CommandModifyCell toUndo = modifications.pop();
				toUndo.undo();
			}
			else {
				System.out.println("No command to undo");
			}
		}
		else if(c instanceof CommandModifyCell){
			modifications.push((CommandModifyCell) c);
		}		
		c.execute();
	}
}
