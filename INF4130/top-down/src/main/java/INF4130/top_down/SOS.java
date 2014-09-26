package INF4130.top_down;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Class holding all variables and functions necessary to solve the problem "Sum of Selections" with a top-down approach.
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

	// strings describing filename.
	private String input, output;
	
	// Print stream
	private PrintWriter outputStream = null;
	
	// File stream
	private BufferedReader inputStream = null;
	
	// global array containing the integers to select from.
	private int[] integers = null;
	
	/**
	 * Initial function that does nothing but trying to call the input file that reads from file.
	 * 
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
	 * Managing the recursive method.
	 * 
	 * Basically connecting the recursive method "recursiveAlg" with the method that prints to file (output).
	 */
	public void recursive() {
		
		// setting up size of boolean array!
		U = new boolean[K][n+1];

		try {
			output(recursiveAlg(K, n-1, n-1));	
			
		} catch (IOException Ioee) {
			System.out.println("couldnt write to file!");
		}
	}

	/**
	 * Recursive method that finds sum of selection equal to @param sum.
	 * 
	 * 
	 * 
	 * @param num - current key for integers array.
	 * @param sum - current sum
	 * @param first - the highest (first) number used in current selection
	 * @return
	 */
	public boolean recursiveAlg(int sum, int num, int first) {
		
		// if the num is.. less than zero and the sum is not found?
		if (num < 0 && sum != 0) {
			
			// if "first" already is key 0, return false
			if (first < 0) {
				return false;
			}
			
			// setting "first used" to false as it failed, same with its solution cell.
			U[K-1][first] = false;
			U[K-1][n] = false;
			
			// trying next integer, but same sum.
			return recursiveAlg(K, first-1, first-1);
		}
		
		// if the num is less than zero, and the sum is as it was back in the day.. return false.
		if (num < 0 && sum == K) {
			return false;
		}
		
		// If the cell is true, then mark it as false and go to next number
		// Why? Because this means that the cell was used previously in some fashion - but failed.
		if (U[sum-1][num] == true) {
			U[sum-1][num] = false;
			
			return recursiveAlg(sum, num-1, first);
		}
		
		// If the current sum is larger than the current integer
		// store as used and jump to the new sum and integer/Number cell!
		if (sum > integers[num] && num > 0) {
			U[sum-1][num] = true;
			U[K-1][num] = true;

			return recursiveAlg(sum - integers[num], num-1, first);
		
		// if the current sum equals the current integer
		// store integer as used and sum solved.
		} else if (sum == integers[num]) {
			U[sum-1][num] = true;
			U[K-1][num] = true;
			U[K-1][n] = true;
			return true;
		
		// if the sum is smaller than the integer
		// proceed to next
		} else {
			U[sum-1][num] = false;

			return recursiveAlg(sum, num-1, first);
		}
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
            	
            	integers = new int[n];
            	
            	for (int i = 2; i < data.length; i++) {
            		integers[i-2] = Integer.parseInt(data[i]);
            	}
            	
            	
            	// Sort the integer array 
            	Arrays.sort(integers);
            	
            	// Proceeding to calling the actual functionality that solves this line of "troubles".
            	recursive();
            	
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();

            }
        }
	}
	
	/**
	 * Function that writes to file.
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