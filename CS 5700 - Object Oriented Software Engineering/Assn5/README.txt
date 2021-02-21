Changes:
- Game is now playable by user through the command line. This was implemented using a mix of the command and the factory pattern
- SudokuCells now hold a boolean variable to determine whether or not they can be modified. Users are only allowed to modify cells that were originally empty

Design desicions:
- Commander class in in charge of passing user's input to command factory, this factory is entirely in charge of parsing the user's input into a command. From here, the Commander class
proceeds to pass that command to the Invoker, whose job it is to execute the commands and log cell modifications in case the users looks to undo previous changes
- This design allows for a very modular approach, as it ensures the decisions to do certain actions are all in the classes one would expect to be in charge of these actions.
- Extending the list of commands could easily be done by making small changes to the CommandFactory class, all other classes don't care for the most part about the type of command being used