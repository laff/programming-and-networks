package INF4130.Tries;

import java.util.ArrayList;

public class Node {
	
	private Node parent = null;
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean end = false;
	public String character = null;
	
	
	public void setChar(String c) {
		character = c;
	}
	
	public String getChar() {
		return character;
	}
	
	public void setParent(Node p) {
		parent = p;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setEnd(boolean e) {
		end = e;
	}
	
	public boolean getEnd() {
		return end;
	}
	
	/**
	 * Function that does everything
	 * @param c
	 * @param e
	 */
	public Node addChar(String c, boolean e) {
		
		Node match = searchChar(c);
		
		// if there was no matching nodes, add child and return it to tries.
		if (match == null) {
			
			return addChild(c, e);
			
		// if there was a match, return the match.
		} else {
			
			return match;
		}
		
		
	}
	
	
	/**
	 * Recursively adding the characters of each node to the global string "Tries.presentStr".
	 * 
	 * Adding characters if they are not null.
	 * 
	 * Then going through its children - further comments inside code.
	 * 
	 */
	public void recurseChars() {
		

		// adding current character to global string.
		if (character != null) {
			Tries.presentStr += character;	
		}

		// for each child, check if child has children.
		for (Node n : children) {
			
			/**
			 * if the child has more than 1 child:
			 *
			 * This means that there are multiple alternatives to which the character in child (n) can create a word.
			 * 
			 * 1. Therefore adding "(" to the global string, representing the start of a word (part of a word).
			 * 2. Then calling the recurseChars() recursively for expanding the children of the child (n).
			 * 3. Finally adding the closure to the part-word ")".
			 */
			if (children.size() > 1) {
				
				Tries.presentStr += "(";
				n.recurseChars();
				Tries.presentStr += ")";
				
			// if the child only has one child - no start/end of a word/part is necessary.
			} else {
				n.recurseChars();
			}
		}


	}
	
	/**
	 * 	going through all the children of this node. trying to determine if there is any matching nodes.
	 * 
	 * returns the matching node if found (quitting the loop) or returns null.
	 * 
	 * @param c
	 * @return
	 */
	
	public Node searchChar(String c) {
	
		for (Node n : children) {
			
			if (n.getChar().equals(c)) {
				return n;
			}	
		}
		
		return null;
	}
	
	/**
	 *  
	 * 
	 * @param c
	 */
	public Node addChild(String c, boolean e) {

		Node tmpNode = new Node();
		tmpNode.setChar(c);
		tmpNode.setEnd(e);
		tmpNode.setParent(this);
		
		children.add(tmpNode);
		
		return tmpNode;
	}

}
