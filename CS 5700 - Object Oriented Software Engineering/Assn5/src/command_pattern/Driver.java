package command_pattern;

public interface Driver {	
	public static void main(String[] args) throws Exception {
		Commander comm = new Commander(args);
		comm.playGame();
	}
}