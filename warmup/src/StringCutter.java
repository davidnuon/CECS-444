/**
 * David Nuon 
 * CECS 444
 * Warmup Lab
 * 
 * StringCutter - A class that takes strings and cuts it up by their characters
 * and displays it in a character matrix. 
 */

import java.util.ArrayList;

/**
 * @author davidnuon
 *
 */
public class StringCutter {

	// Initialized variables
	ArrayList<String> lines;
	ArrayList<String> tokens;

	
	/**
	 *  Constructor
	 */
	public StringCutter() {
		this.lines = new ArrayList<String>();
		this.tokens = new ArrayList<String>();
	}
	
	/**
	 * Goes through the lines and returns a matrix of characters represented as a string
	 * @return a matrix of characters represented as a string
	 */
	public String getTextBlock() {
		String out = "";
		
		// We cut all the lines
		for (int i = 0; i < this.lines.size(); i++) 
		{
			String theLine = this.lines.get(i);
			this.cut(theLine);
		}
		
		// And add end of file
		this.tokens.add("EOF");
		
		// We make the matrix of characters with the character tokens
		int counter = 0;
		for (int i = 0; i < this.tokens.size() - 1; i++) 
		{
			out += String.format("%-3s", this.tokens.get(i));
			counter++;
			
			if(counter % 20 == 0 && counter > 0) 
			{
				out += "\n";
				counter = 0;
			} else {
				out += " ";
			}
		}
		// We need to add the last one
		out += this.tokens.get(this.tokens.size() - 1);

		return out;
	}
	
	/**
	 * Adds lines to set of strings
	 * @param line
	 */
	public void addLine(String line) {
		// We trim all control characters
		this.lines.add(line.trim());
	}
	
	/**
	 * Subprocedure to cut up each line and sort out characters.
	 * @param line
	 */
	public void cut(String line) {
		if (line.length() == 0) return;
		
		for(int i = 0; i < line.length(); i++)
		{
			char theChar = line.charAt(i);
			
			switch(theChar)
			{
				case ' ':
					this.tokens.add("BL");
					break;
				default:
					this.tokens.add(""+theChar);
			}
			
		}		
		
		// All lines have an end of line
		this.tokens.add("EOL");
	}
}