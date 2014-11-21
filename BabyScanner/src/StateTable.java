/**
 * Class for a state table
 * @author davidnuon
 *
 */
public class StateTable {

	int[][] table;
	
	public StateTable() {
	
	}
	
	/**
	 * Takes in a CSV file and makes it into a graph table
	 * @param fileContent
	 */
	public void makeFromCSVString(String fileContent) {
		
		String[] lines = fileContent.split("\n");
		int rows = lines.length;
		int cols = lines[0].trim().split(",").length;
		
		table = new int[rows][cols];
		
		// Going through the rows 
		for (int i = 0; i < lines.length; i++) {
			String[] states = lines[i].trim().split(",");
			assert(states.length == cols);
			
			// Going through the elements in each row
			for (int j = 0; j < states.length; j++) {
				
				// We add each to the table.
				table[i][j] = Integer.parseInt(states[j].trim());
			}
		}
	}
	
	/*
	 * String representation of state table
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String out = ""; 
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				out = out + String.format("%3d", table[i][j]);
			
				if( (j + 1) % table[0].length == 0) {
					out = out + "\n";
				} else {
					out = out + ", ";
				}
			}
		}
		return out;
	}
	
	/**
	 * Returns a state in the table
	 * @param new_state
	 * @param new_char
	 * @return
	 */
	public int getState(int new_state, int new_char) {
		System.out.println("GETSTATE " + new_state + " " + new_char + " " + (int)new_char);
		return table[new_state][new_char];
	}
	
}
