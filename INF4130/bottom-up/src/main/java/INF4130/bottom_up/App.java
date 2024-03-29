package INF4130.bottom_up;

import INF4130.bottom_up.SOS;

/**
 * Exercise 1b - Mandatory Assignment 1, INF4130, 2014
 *
 * @author Olafab
 */
public class App {
    public static void main( String[] args ) {
    	
    	SOS exercise1b = new SOS();
    	
    	// I want two arguments only
    	// name of input file first, then output 
    	int len = args.length;
    	if (len == 2) {
    		exercise1b.setInput(args[0]);
    		exercise1b.setOutput(args[1]);
    		
        	// Initiate program!
        	exercise1b.init();
    	}
    } 
}
