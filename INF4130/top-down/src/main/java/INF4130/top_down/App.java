package INF4130.top_down;

import INF4130.top_down.SOS;

/**
 * Solution for exercise 1d for Mandatory Assignment 1, INF4130, 2014
 * 
 * @author Olafab
 */
public class App 
{
    public static void main( String[] args )
    {
    	SOS exercise1d = new SOS();
    	
    	// I want two arguments only
    	// name of input file first, then output 
    	int len = args.length;
    	if (len == 2) {
    		exercise1d.setInput(args[0]);
    		exercise1d.setOutput(args[1]);
    		
        	// Initiate program!
        	exercise1d.init();
    	}
    }
}
