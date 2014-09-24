package INF4130.olafab;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Exercise 1 (Dynamic Programming)
 * 
 * b) a standard bottom up dynamic programming algorithm that solves SOS.
 * 
 */
public class DynamicSOS {

	// length of integers
	private int n;
	
	// given sum
	private int K;
	
	private int S;
	
	// Two dimensional boolean array
	private boolean[][] U;

	// Print stream
	private PrintWriter outputStream = null;
	
	// File stream
	private BufferedReader inputStream = null;
	
	private int[] integers = null;
	
	/**
	 * Init
	 */
	public void init() {
	
		try {
			initialInput();
		} catch (IOException Ioe) {
			System.out.println("did not find file?");
		}
		
	}
	
	/**
	 * Initialize more!
	 */
	public void dynamic(int[] integers) {
		
		// Sum up integers
		S = 0;
		for (int i = 0; i < n; i++) {
			S += integers[i];
		}
		
		if (isSubsetSum(integers, n, K) == true) {
			try {
				finalOutput(true);
			} catch (IOException Ioee) {
				System.out.println("cant write to file sry");
			}
		} else {
			try {
				finalOutput(false);
			} catch (IOException Ioee) {
				System.out.println("cant write to file sry");
			}
		}
			
		
	}

	/**
	 * Function from: http://www.geeksforgeeks.org/dynamic-programming-U-sum-problem/
	 * Did not manage to figure it out all by my self...
	 * 
	 * @param set
	 * @param n
	 * @param sum
	 * @return
	 */
	public boolean isSubsetSum(int set[], int n, int sum) {
	
		U = new boolean[sum+1][n+1];
		
		for (int i = 0; i <= n; i++) {
			U[0][i] = true;
		}
		
		for (int i = 1; i <= sum; i++) {
			U[i][0] = false;
		}
		
		for (int i = 1; i <= sum; i++) {
			
			for (int j = 1; j <= n; j++) {
				
				U[i][j] = U[i][j-1];
				if (i >= set[j-1]) {
					U[i][j] = U[i][j] || U[i - set[j-1]][j-1];
				}
			}
		}

		for (int i = 0; i <= sum; i++) {
			
			for (int j = 0; j <= n; j++) {
				//System.out.println(U[i][j]);
			}
		}
		
		return U[sum][n];
	}
	
	/**
	 * Function that reads from file and sets variables.
	 * @throws IOException
	 */
	public void initialInput() throws IOException {

        try {
        	if (inputStream == null) {
        		inputStream = new BufferedReader(new FileReader("inputSOS.txt"));
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
            	
            	dynamic(integers);
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
		
        try {
        	
        	// Decide upon writing new file or adding to previous.
        	boolean add = (outputStream != null);
        	outputStream = new PrintWriter(new BufferedWriter(new FileWriter("outputSOS.txt", add)));

    		outputStream.print("INSTANCE " + n + " " + K);
    		
    		for (int i = 0; i < n; i++) {
    			outputStream.print(" " + integers[i]);
    		}
        	
        	if (answer) {
                outputStream.print("\r\nYES\r\n");
                
                /*
                for (int i = 0; i < n; i++) {
             
                	
                	int bool = (U[K][i]) ? 1 : 0;
                	
                	outputStream.print(" (" + integers[i] + "," + bool + ")");
                }
                outputStream.print("\r\n\r\n");
                */
                
                for (int i = 0; i <= K; i++) {
                	
                	outputStream.print(i + "\t");
                	for (int j = 0; j <= n; j++) {
                		
                		outputStream.print(U[i][j] + "\t");
                		
                	}
                	outputStream.print("\r\n");
                }
                

        	} else {

        		outputStream.print("\r\nNO\r\n");
                for (int i = 0; i <= K; i++) {
                	
                	outputStream.print(i + "\t");
                	for (int j = 0; j <= n; j++) {
                		
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