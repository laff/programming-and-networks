package INF4130.Tries;

import java.util.ArrayList;

public class Node {

	private Node parent = null;
	private ArrayList<Node> children = new ArrayList<Node>();
	private boolean end = false;
	private String character = null;
	
	
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
		
		Node match = null;
		
		// going through all the children of this node.
		// trying to determine if there is any matching nodes.
		for (Node n : children) {
			
			if (n.getChar().equals(c)) {
				match = n;
			}	
		}
		
		// if there was no matching nodes, add child and return it to tries.
		if (match != null) {
			
			return addChild(c, e);
			
		// if there was a match, return the match.
		} else {
			
			return match;
		}
		
		
	}
	
	public String searchChar(String c, boolean e) {
		
		return c;
		
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
