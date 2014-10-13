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
    
    private String input = null;
    private String output = null;
    
    private Node root = null;
    private String charLines[];
    
    private String characters[];
    
    public static String presentStr = "";
    
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
    	
    	boolean prefix, extension = false;
    	
    	characters = line.split("");
    	
    	// Temporary node is stored so that Node.addchar() can be called on the proper node.
    	// Node.addchar() either returns a newly created node or a node matching the character sent.
    	Node tmpNode = null;
    	
    	String tmpChar;
    	
    	for (int i = 0; i < characters.length; i++) {
    		
    		// Is this character the last character in string?
    		boolean end = (i == characters.length-1);

    		// Store current character to be searched for.
    		tmpChar = characters[i];
    		
    		// if tmpNode is null, it is the first loop. Else call addchar on the temporary node.
        	tmpNode = (tmpNode == null) ? root.addChar(tmpChar, end) : tmpNode.addChar(tmpChar,  end);
        	
        	// check each node if it is has en ending character (while not being the last loop)
        	if (tmpNode.end && !end) {
        		extension = true;
        	}
    	}
 
    	// checking if the last tmpnode had children; determening if prefix or not.
    	prefix = (!tmpNode.children.isEmpty());
    	
    	
    	
    	/**
    	 * For each of the inserted strings, output them in a format that represents their status as either PREFIX or EXTENSION.
    	 * 
    	 */
    	try {
    		output(line, prefix, extension, false, false);
    	} catch (IOException ioee) {
    		System.out.println("cant write file");
    	}	
    	
    }
    
    
    /**
     * Not implemented.
     * 
     * @param line
     */
    public boolean search(String line) {
    	
    	String tmpChar;
    	Node tmpNode = null;
    	boolean end;
    	characters = line.split("");
    	
    	for (int i = 0; i < characters.length; i++) {
    	
    		// Is this character the last character in string?
    		end = (i == characters.length-1);
    		
    		// Store current character to be searched for.
    		tmpChar = characters[i];
    		
    		/**
    		 * Algorithm!!!1111111
    		 * 
    		 * Essentially searching for each character amongst the children of tmpNode (searchChar).
    		 * 
    		 * The function called returns a Node and stores it to tmpNode.
    		 * 
    		 * If the stored node has the wanted character and is marked as an end-node, check if it is supposed to be the ending character (and return true).
    		 * if it is not the ending character do nothing, and let the search continue.
    		 * 
    		 * If the stored node does NOT have the character we are searching for, stop search and return false.
    		 * 
    		 */
    		tmpNode = (tmpNode == null) ? root.searchChar(tmpChar) : tmpNode.searchChar(tmpChar);
    		
    		try {
        		if (tmpNode.character.equals(tmpChar) && tmpNode.end) {
        			
        			if (end) {
        				return true;
        			}
        		}
    		} catch (NullPointerException npe) {
    			
    			return false;
    		}
    	}
    	
    	return false;
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
            	
            	/**
            	 * Storing first line as amount of string to insert into trie.
            	 */
            	if (count == 0) {
            		N = Integer.parseInt(l);
            		
            		charLines = new String[N];
            	
            	/**
            	 * Storing the next N strings to charLiens array, also inserting them.
            	 */
            	} else if (count <= N) {
            		
            		// Storing lines
            		charLines[count-1] = l;
            		
            		// inserting lines.
            		insert(l);
            	
            	/**
            	 * The rest of the strings are to be searched for.
            	 */
            	} else if (count > N) {
            		output(l, false, false, search(l), true);
            	}
            	
            	// count up!
            	count++;
            }

            

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
	}
    
	
	/**
	 * Overloaded function. Includes alot of funky postfixes to the printed string.
	 * Functionality regarding printing the string representation is exactly the same as "presentTrie()" but splitted while development.
	 * 
	 * @param l
	 * @param pre
	 * @param ext
	 * @param found
	 * @param searched
	 * @throws IOException
	 */
	public void output(String l, boolean pre, boolean ext, boolean found, boolean searched) throws IOException {
		
        try {
        	
        	// Decide upon writing new file or adding to previous.
        	boolean add = (outputStream != null);
        	outputStream = new PrintWriter(new BufferedWriter(new FileWriter(output, add)));
        	
        	// Determines the postfix to our output.
        	String navlastraing = (pre) ? "PREFIX" : (ext) ? "EXTENSION" : (found) ? "YES" : (searched) ? "NO" : "";
        	
        		outputStream.print(l + " " + navlastraing);
        		
        		// adding trie presentation if the current line (l) is not searched (added).
        		if (!searched && !pre && !ext) {
        			
        			// cleans global string.
        			presentStr = "";
        			
        			// calls recursive string creator on current root!
        			root.recurseChars();
        			
        			// adds presentation
        			outputStream.print(presentStr);
        		}
        		
        		outputStream.print("\r\n");

        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }		
	}
}
