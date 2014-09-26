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
    
    
    public Node root = null;
    private String charLines[];
    private String characters[];
    
    public void setInput(String file) {
    	input = file;
    }
    
    public void setOutput(String file) {
    	output = file;
    }
    
    public void init() {
    	
    	// Starting off with creating the root node.
    	root = new Node();

    	// Then getting the input from file.
    	try {
    		input();
    	} catch (IOException ioee) {
    		System.out.println("cant read file");
    	}
    }
    
    /**
     * Receives lines from the input function, splits them up to characters and adds them one by one to the node structure
     * 
     * @param line
     */
    public void insert(String line) {
    	
    	characters = line.split("");
    	
    	// Temporary node is stored so that Node.addchar() can be called on the proper node.
    	// Node.addchar() either returns a newly created node or a node matching the character sent.
    	Node tmpNode = null;
    	
    	for (int i = 0; i < characters.length; i++) {
    		
    		// Is this character the last character in string?
    		boolean end = (i == characters.length-1);
    		
    		// if tmpNode is null, it is the first loop. Else call addchar on the temporary node.
        	tmpNode = (tmpNode == null) ? root.addChar(characters[i], end) : tmpNode.addChar(characters[i],  end);
    	}
    	
    	try {
    		output();
    	} catch (IOException ioee) {
    		System.out.println("cant write file");
    	}	
    	
    }
    
    /**
     * Function that presents the structure of the tree/strings.
     */
    public void presentTrie() {
    	
    	/**
    	 * TODO!
    	 */
   
    }
    
    /**
     * Not implemented.
     * 
     * @param line
     */
    public void search(String line) {
    	
    	System.out.println("searching for: " + line);
    	
    }
    
    /**
     * reading input from file.
     * 
     * Storing and inserting lines into tree.
     * Also invoking search when it comes to those strings - OBS! OBS!
     * - This is where I gave up at 21:45 26.09.14.
     * 	Some how I can not manage the while loop, and my head can not wrap around it. 
     * 	I need rest.
     * 
     * @throws IOException
     */
	public void input() throws IOException {

        try {
        	if (inputStream == null) {
        		inputStream = new BufferedReader(new FileReader(input));
        	}
            
            String l;
            int count = 0;
            int N = 0;
            while ((l = inputStream.readLine()) != null) {
            	
            	System.out.println(count + " " + N);
            	
            	if (count == 0) {
            		N = Integer.parseInt(l);
            		
            		charLines = new String[N];
            		
            	} else if (count < N) {
            		
            		// Storing lines
            		charLines[count] = l;
            		
            		// inserting lines.
            		insert(l);
            	
            	} else if (count >= N) {
            		
            		System.out.println(count + " " + N);
            		
            		search(l);
            	}
            	count++;
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
                
                // presenting how characters/strings were inserted!
                presentTrie();
                

            }
        }
	}
    
	public void output() throws IOException {
		
        try {
        	
        	// Decide upon writing new file or adding to previous.
        	boolean add = (outputStream != null);
        	outputStream = new PrintWriter(new BufferedWriter(new FileWriter(output, add)));
        	
        	
        	
        	
        	//System.out.println();
        	
    		//outputStream.print();


        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }		
	}
}
