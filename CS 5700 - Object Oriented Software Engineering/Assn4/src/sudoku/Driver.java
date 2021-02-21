package sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Driver {
	public static InputStream is = null;
	public static OutputStream os = System.out;
	
	public static void processInput(String[] args) {
		PrintWriter pw = new PrintWriter(os);
		if(args.length>0) {
			if("-h".equals(args[0].toLowerCase().trim())) {				
				pw.println("The valid command line arguments for this program are:");
				pw.println("\t-h for help");
				pw.println("\t<inputfile> for the solver to solve a given puzzle and output the result to the screen");
				pw.println("\t<inputfile> <outputfile> for the solver to solve a given puzzle and output the result to the specified output file");
			}
			else if(args.length==1) {
				try {
				is = new FileInputStream(args[0]);
				}
				catch(Exception e) {
					pw.println(e.getLocalizedMessage());
				}
			}
			else if(args.length==2) {
				try {
					is = new FileInputStream(args[0]);
				} catch (FileNotFoundException e) {
					pw.println(e.getLocalizedMessage());
				}
				try {
					os = new FileOutputStream(args[1]);
				} catch (FileNotFoundException e) {
					pw.println(e.getLocalizedMessage());
				}		
			}
			else {
				pw.println("Error, more than 2 arguments specified");
			}
		}
		else {
			pw.println("Error, no argumens specified");
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
		is = null;
		os = System.out;
		processInput(args);
		if(is!= null) {
			new SudokuSolver(is,os);
		}			
	}

}
