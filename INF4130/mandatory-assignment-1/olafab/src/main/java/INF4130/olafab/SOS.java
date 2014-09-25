package INF4130.olafab;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Exercise 1 (Dynamic Programming)
 * 
 * b) a standard bottom up dynamic programming algorithm that solves SOS.
 * 
 */
public class SOS {

	// boolean that tells the initialInput() to proceed with either dynamic or recursive solution.
	private boolean DP;
	
	// amount of integers to be checked.
	private int n;
	
	// given sum
	private int K;
	
	// Two dimensional boolean array
	private boolean[][] U;

	// Print stream
	private PrintWriter outputStream = null;
	
	// File stream
	private BufferedReader inputStream = null;
	
	private int[] integers = null;
	
	/**
	 * Initial function that stores choice (DP or recursive function),
	 * Then calling the function that reads input and further calls method of choice.
	 * 
	 * @param choice : true = dynamic programming version, false = recursive.
	 */
	public void init(boolean choice) {
		
		DP = choice;
	
		try {
			initialInput();
		} catch (IOException Ioe) {
			System.out.println("did not find file?");
		}
		
	}
	
	/**
	 * Managing the dynamic method.
	 */
	public void dynamic() {
		try {
			finalOutput(dynamicAlg());
		} catch (IOException Ioee) {
			System.out.println("cant write to file sry");
		}
	}
	
	/**
	 * Managing the recursive method.
	 */
	public void recursive() {
		
		U = new boolean[K][n+1];
		
		// Starting out with filling the array with false
		for (int i = 0; i < K; i++) {
			for (int j = 0; j <= n; j++) {
				U[i][j] = false;
			}
		}

		try {
			finalOutput(recursiveAlg(n, K));	
			
		} catch (IOException Ioee) {
			System.out.println("couldnt write to file!");
		}

		
	}

	/**
	 * 
	 * @param num
	 * @param sum
	 * @return
	 */
	public boolean recursiveAlg(int num, int sum) {
		
		// Is the sum zero?
		// then we won!
		if (sum == 0) {
			return true;
			
		// sum is not zero, and there is no more numbers left.
		} else if (num == 0) {
			return false;
		}
		
		// if num is larger than sum, skip it.
		if (integers[num-1] > sum) {
			recursiveAlg(num-1, sum);
		}
		
		
		// if the first function call returns false, call next function.
		//
		// The first call is proceeding to next number while keeping the sum
		//
		// second call proceeding to next number, but subtracts current integer.
		return recursiveAlg(num-1, sum) || recursiveAlg(num-1, (sum - integers[num-1]));
	}
	
	/**
	 * 
	 * @param n
	 * @param sum
	 * @return
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
	public void initialInput() throws IOException {

        try {
        	if (inputStream == null) {
        		inputStream = new BufferedReader(new FileReader("input.txt"));
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
            	
            	// Proceeding to call either the dynamic or recursive method.
            	if (DP) {
            		dynamic();
            	} else {
            		recursive();
            	}
            	
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
	public void finalOutput(boolean answer) throws IOException {
		
		// Deciding upon what file to save as.
		// this should be done manually by command line.
		String output = DP ? "outputDP.txt" : "outputREC.txt";
		
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
                
                
                int h = U.length;                
                int w = U[0].length;
                
                for (int i = 0; i < (w - 1); i++) {
                	outputStream.print("\t__" + integers[i] + "__");
                }
                outputStream.print("\r\n");
                for (int i = 0; i < h; i++) {
                	
                	outputStream.print((i+1) + "\t");
                	for (int j = 0; j < w; j++) {
                		
                		outputStream.print(U[i][j] + "\t");
                		
                	}
                	outputStream.print("\r\n");
                }
                

        	} else {

        		outputStream.print("\r\nNO\r\n");
        		
                int h = U.length;                
                int w = U[0].length;
                for (int i = 0; i < h; i++) {
                	
                	outputStream.print((i+1) + "\t");
                	for (int j = 0; j < w; j++) {
                		
                		outputStream.print(U[i][j] + "\t");
                		
                	}
                	outputStream.print("\r\n");
                }
        	}

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }		
	}
}