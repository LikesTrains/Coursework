package src;

import java.util.Scanner;

public class Driver {
	public static void main(String[] args) throws Exception {
		
		String port = "12000";
		System.out.println("Select a port (12000 by default)");
		Scanner in = new Scanner(System.in);
		String newP = in.nextLine();
		
		if(!newP.trim().equals("")) {
			port = newP.trim();
		}
		
		RaceTracker test = new RaceTracker(port);
        
        
        System.out.println("Press enter to terminate this program");
        while(true) {
        	String word = in.nextLine();
        	
        	if (word.equals("")) {
            	test.stop();
            	in.close();
            	System.exit(0);
        	}
        }
	}
}
