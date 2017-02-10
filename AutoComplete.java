import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/** @title AutoComplete
 * @author Anita Bourzutschky
 * Accepts passages and attempts to match fragments to words in the passage(s) */
public class AutoComplete implements AutocompleteProvider{

	/*Will store words from the passage along with their frequencies.*/
	Node text;
	
	/**Creates a new AutoComplete object. No parameters because internal data 
	 * structure should be empty upon creation. */
	public AutoComplete() {
		
	}
	
	/*Like a Binary Search Tree - used instead of existing Tree-like and Node-like classes
	 * because easier to manipulate. Could have used generic types, but no need because 
	 * this is a specific application. */
	private class Node {
		
		String str;
		Integer count;
		Node left;
		Node right;
		
		private Node(String s, Integer i) {
			
			str = s;
			count = i;
		}
		
		public String toString() {
			
			return "<"+str + ", "+count+">";
		}
	}
	
	/*This function could have been done recursively, but that may be less efficient.
	 * @newString: the String to be inserted into the Node-linked tree.*/
	private void insert(String newString) {
		
		if(text == null) {
			
			text = new Node(newString, 1);
			return;
		}
		Node curr = text;
	
		/*Nodes will eventually become null, so no need for a more specific loop guard.*/
		while(true) {
			
			/*If the string is present, increase the count*/
			if(newString.compareTo(curr.str) == 0) {
				
				curr.count++;
				break;
			} else if(newString.compareTo(curr.str) < 0) {
				
				/*If the string is "less than" the current node's string lexicographically,
				 * insert it to the left - either immediately to the left if the left node
				 * is null, or further down the node-linked tree*/
				if(curr.left == null) {
					curr.left = new Node(newString, 1);
					break;
				} else {
					curr = curr.left;
				}
			} else {
				
				/*If the string is "greater than" the current node's string lexicographically,
				 * insert it to the right - either immediately to the right if the left node
				 * is null, or further down the node-linked tree*/
				if(curr.right == null) {
					
					curr.right = new Node(newString, 1);
					break;
				} else {
					
					curr = curr.right;
				}				
			}
		}
	}
	
	/*Traverses the Node tree, gathering words that match the fragment.
	 * @fragment The String to be matched
	 * @return An ArrayList containing all the Candidates (Strings and confidences)
	 * that matched fragment*/
	private ArrayList<CandString> collectWords(String fragment) {
		
		ArrayList<CandString> candList = new ArrayList<CandString>();
		
		/*No need to search if Node-linked tree is empty*/
		if(text == null) {
			return candList;
		}
		
		Node curr = text;
		Stack<Node> potentialMatches = new Stack<Node>();
		potentialMatches.push(curr);
		
		while(!potentialMatches.isEmpty()) {
				
			/*Redundant for the first iteration because this assigns curr to itself,
			 * but useful because it simplifies the code. This way, we only need to 
			 * check if the stack is empty in the loop guard, instead of anything
			 * more complicated.*/
			curr = potentialMatches.pop();
			
			int minLength = Math.min(fragment.length(), curr.str.length());
			String currSubstring = curr.str.substring(0, minLength);
			
			/*fragment could be longer than the original string. If it is longer, but subFrag
			 * matches currSubstring, algorithm searches for nearby strings that match the 
			 * fragment and are of an appropriate length.*/
			String fragSub = fragment.substring(0, minLength);
				
			/*If the fragment comes before the string lexicographically, examine the 
			 * left Nodes. (Right Nodes can be ignored).*/
			if(fragSub.compareTo(currSubstring) < 0) {
				
				if(curr.left != null) {
					potentialMatches.push(curr.left);
				}
				
			} else if(fragSub.compareTo(currSubstring) > 0) {
				
				/*If the fragment comes after the string lexicographically, examine the 
				 * right Nodes. (Left Nodes can be ignored)*/
				if(curr.right != null) {
					potentialMatches.push(curr.right);
				}
				
			} else {
				
				/*We have found a match. Add it directly to list; no need to bother
				 * putting it on the stack.*/
				if(fragment.length() <= curr.str.length()) {
					
					candList.add(new CandString(curr.str, curr.count));
				}
				
				/*Since this string matched the fragment, BOTH the left and right Nodes could 
				 * have strings matching the fragment. */
				if(curr.left != null) {
					potentialMatches.push(curr.left);
				}
				
				if(curr.right != null) {
					potentialMatches.push(curr.right);
				}
			}
		}
		
		return candList;
	}
	/**Takes in a passage and enters words into internal structure.
	 * @passage The passage of Strings to be entered */
	public void train(String passage) {
		
		/*Punctuation must be ignored*/
		String[] words = passage.split("[^a-zA-Z]");
		
		for(String s : words) {
			
			/*Inserts words into Node-linked tree ignoring case.
			  MAY MODIFY IN THE FUTURE FOR THE SAKE OF EFFICIENCY - may
			  try to prevent the "text" instance variable from being an 
			  imbalanced tree.*/
			insert(s.toLowerCase());
		}
	}

	/**Returns a List of words (and their confidences) that match the provided 
	 * fragment. 
	 * @fragment The fragment to be matched
	 * @return A List of Candidates (Strings and their confidences) that matched the 
	 * provided fragment. This List will be sorted only by confidences (higher 
	 * confidences first)*/
	public List<Candidate> getWords(String fragment) {
		
		ArrayList<CandString> candList = collectWords(fragment);
		Collections.sort(candList);
		return (List)candList;
	}
}
