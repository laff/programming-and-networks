package INF4130.Tries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Tries {

    private PrintWriter outputStream;
    private BufferedReader inputStream;
    
    private String input = "input.txt";//null;
    private String output = "output.txt";//null;
    
    public void setInput(String file) {
    	input = file;
    }
    
    public void setOutput(String file) {
    	output = file;
    }
    
    public void init() {
    	
    	try {
    		input();
    	} catch (IOException ioee) {
    		System.out.println("cant read file");
    	}
    }
    
    public void exercise(String someshit) {
    	
    	try {
    		output(someshit);
    	} catch (IOException ioee) {
    		System.out.println("cant write file");
    	}	
    	
    }
    
	public void input() throws IOException {

        try {
        	if (inputStream == null) {
        		inputStream = new BufferedReader(new FileReader(input));
        	}
            
            String l;
            while ((l = inputStream.readLine()) != null) {
            	
            	exercise(l);
            	
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();

            }
        }
	}
    
	public void output(String line) throws IOException {
		
        try {
        	
        	// Decide upon writing new file or adding to previous.
        	boolean add = (outputStream != null);
        	outputStream = new PrintWriter(new BufferedWriter(new FileWriter(output, add)));
        	
    		outputStream.print(line + "\r\n");


        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }		
	}
}
