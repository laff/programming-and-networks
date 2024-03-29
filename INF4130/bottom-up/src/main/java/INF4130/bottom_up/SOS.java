package INF4130.bottom_up;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Sum of Selection class. 
 * Holds variables and functions used to solve this problem with a bottom-up approach.
 * 
 * Solution to exercise 1b of Mandatory Assignment 1, INF4130, 2014
 * 
 * @author Olafab
 */
public class SOS {
	
	// amount of integers to be checked.
	private int n;
	
	// given sum
	private int K;
	
	// Two dimensional boolean array
	private boolean[][] U;

	private String input, output;
	
	// Print stream
	private PrintWriter outputStream = null;
	
	// File stream
	private BufferedReader inputStream = null;
	
	private int[] integers = null;
	
	/**
	 * Initial function with little to no purpose other than structure while developing.
	 * Calls input() which reads input though!
	 */
	public void init() {
		try {
			input();
		} catch (IOException Ioe) {
			System.out.println("did not find file?");
		}
	}
	
	public void setInput(String file) {
		input = file;
	}
	
	public void setOutput(String file) {
		output = file;
	}
	
	/**
	 * Managing the dynamic method.
	 * Also here for structure while developing...
	 */
	public void dynamic() {
		try {
			output(dynamicAlg());
		} catch (IOException Ioee) {
			System.out.println("cant write to file sry");
		}
	}

	/**
	 * The algorithm that solves the problem. 
	 * Returns the "answer"-cell and its contents upon completion.
	 * 
	 * Solution is based on theory explained in class.
	 * 
	 */
	public boolean dynamicAlg() {
		
		int i, j, rest;
		
		// Creating a boolean array where the first key is to represent the sums
		//
		// The second key represents each integer, and the last (n+1) says if the number has been summed successfully.
		U = new boolean[K][n+1];
		
		// Starting out with filling the array with false
		for (i = 0; i < K; i++) {
			for (j = 0; j <= n; j++) {
				U[i][j] = false;
			}
		}
		
		// Going through the sums, lowest first.
		for (i = 0; i < K; i++) {
			
			// going through the integers, highest first.
			for (j = (n - 1); 0 <= j; j--) {
				
				// calculate rest.
				rest = (i + 1) - (integers[j]);
				
				// if the current sum equals the current integer then sum i is complete/true.
				// Proceed to end inner loop by setting j to 0.
				if (rest == 0) {
					U[i][j] = true;
					U[i][n] = true;
					
					j = 0;
				
				// if the sum is larger than the integer, and there still is integers left to check
				//
				//then check if the remainder is complete/true
				} else if (rest > 0 && j > 0) {
					
					// if remainder array is true, set current sum&integer to true
					if (U[rest - 1][n]) {
						
						U[i][j] = true;
						U[i][n] = true;
						
						// copy rest of the remainder sum/integer combo
						while (j-- > 0) {
							U[i][j] = U[rest - 1][j];
						}
					}	
				}
			}
		}
		return U[K-1][n];
	}
	
	/**
	 * Function that reads from file and sets variables.
	 * @throws IOException
	 */
	public void input() throws IOException {

        try {
        	if (inputStream == null) {
        		inputStream = new BufferedReader(new FileReader(input));
        	}
            
            String l;
            while ((l = inputStream.readLine()) != null) {
            	String[] data = l.split("\\ ");
            	
            	// setting variables.
            	n = Integer.parseInt(data[0]);
            	K = Integer.parseInt(data[1]);
            	
            	// setting up array with appropriate size.
            	integers = new int[n];
            	
            	// Skipping the first two characters of the line.
            	for (int i = 2; i < data.length; i++) {
            		integers[i-2] = Integer.parseInt(data[i]);
            	}
            	
            	// Sort the integer array 
            	Arrays.sort(integers);
            	
            	// Proceeding to calling the actual functionality that solves this line of "troubles".
            	dynamic();
            }
            
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
	}
	
	/**
	 * Function that writes to file.
	 * Format as described by assignment details.
	 * 
	 * @param answer - if the table ended up with being solved or not. Another useless solution at this point in time.
	 * @throws IOException
	 */
	public void output(boolean answer) throws IOException {
		
        try {
        	
        	// Decide upon writing new file or adding to previous.
        	boolean add = (outputStream != null);
        	outputStream = new PrintWriter(new BufferedWriter(new FileWriter(output, add)));

    		outputStream.print("INSTANCE " + n + " " + K);
    		
    		for (int i = 0; i < n; i++) {
    			outputStream.print(" " + integers[i]);
    		}
        	
        	if (answer) {
                outputStream.print("\r\nYES");
                
                for (int i = 0; i < n; i++) {
                	
                	int bool = (U[K-1][i]) ? 1 : 0;
                	
                	outputStream.print(" (" + integers[i] + "," + bool + ")");
                }
                
                outputStream.print("\r\n");

        	} else {

        		outputStream.print("\r\nNO\r\n");
        
        	}

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }		
	}
}