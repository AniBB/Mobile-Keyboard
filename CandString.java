/**@title CandString
 * 
 * @author Anita Bourzutschky
 * A CandString consists of a String and its confidence.
 */
public class CandString implements Candidate, Comparable<CandString>{

	/*Count is the confidence*/
	String word;
	Integer count;
	
	/**Initializes a new CandString with the given String and confidence*/
	public CandString(String w, Integer c) {
		
		word = w;
		count = c;
	}
	
	/**Returns the String*/
	public String getWord() {
		
		return word;
	}

	/**Returns the confidence*/
	public Integer getConfidence() {

		return count;
	}

	/**Compares the current CandString to another CandString.
	 * @param other The CandString to be compared to the current object
	 * @return The other CandString's confidence minus the current 
	 * CandString's confidence. (Higher confidences should come first.)
	 */
	public int compareTo(CandString other) {
			
		return other.count - count;	
	}

	/**Returns a String representation of the current CandString. */
	public String toString() {
		
		/*Displays the CandString the way they were displayed in the sample
		 * output */
		return "\""+word+"\"" + " ("+count+")";
	}
}
