import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that handles token storage and lookup
 * 
 * @author davidnuon
 * 
 */
public class TokenDictionary {
	// Instance Variables
	HashMap<Integer, String> tokens;
	ArrayList<Integer> tokenIDs;
	int[] tokenLookup;
	int otherCase;
	final int SPACE_CHAR = -1;

	/**
	 * Default constructor
	 */
	public TokenDictionary() {
		tokens = new HashMap<Integer, String>();
		tokenIDs = new ArrayList<Integer>();
		tokenLookup = new int[256];
	}
	
	/**
	 * Takes a CSV file and makes our dictionary
	 * @param fileContent
	 */
	public void makeFromCSVString(String fileContent) {
		String[] lines = fileContent.split("\n");
	
		// We go through the CSV and add them to our dictionary
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] tokenInfo = line.split(",");
			
			int      tokenID = Integer.parseInt(tokenInfo[0].trim());
			String tokenName = tokenInfo[1].trim();
			
			tokens.put(tokenID, tokenName);
			tokenIDs.add(tokenID);
		}
		
		// We now also need the OTHER case
		otherCase = 29;
		String otherValue = "OTHER";
		
		tokens.put(otherCase, otherValue);
		tokenIDs.add(otherCase);
	}
	
	/**
	 * Method to check to see if we have an ID
	 * @param id
	 * @return 
	 */
	public boolean hasToken(int id) {
		return tokenIDs.contains(id);
	}
	
	/**
	 * Get the human readable name of a token type
	 * @param id
	 * @return
	 */
	public String getTokenName(int id) {
		if(!hasToken(id)) {
			return "";
		}
		
		return tokens.get(id);
	}
	
	/**
	 * String representation
	 */
	public String toString(){
		String out = "";
		for (int i = 0; i < tokenIDs.size(); i++) {
			int id   = tokenIDs.get(i);
			String name = getTokenName(id);
			out = out + String.format("%2d: %10s\n", id, name);
		}
		
		return out;
	}
	
	/**
	 * Creates the character lookup table
	 * @param fileContent
	 */
	public void buildCharacterLookup(String fileContent)
	{
		// We fill the lookup table with other cases
		for (int i = 0; i < tokenLookup.length; i++) {
			tokenLookup[i] = tokenIDs.get(tokenIDs.size() - 1);
		}
		
		// We give a special case for whitespace characters
		tokenLookup[' ']  = SPACE_CHAR;
		tokenLookup['\t'] = SPACE_CHAR;
		tokenLookup['\n'] = SPACE_CHAR;
		tokenLookup['\f'] = SPACE_CHAR;
		tokenLookup['\r'] = SPACE_CHAR;
		
		String[] lines = fileContent.split("\n");
		
		// We go through the CSV and add them to our dictionary
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String[] tokenInfo = line.split("\t");
			/*
			 * The first element is an ID for a type
			 * The second element is a list of characters
			 */
			int      tokenID = Integer.parseInt(tokenInfo[0].trim());
			String charList = tokenInfo[1].replace("\n", "");
		
			for (int j = 0; j < charList.length(); j++) {
				tokenLookup[(int)charList.charAt(j)] = tokenID;
			}
		}
	}
	
	// Get the character ID
	public int getCharTokenID(char c) {
		return tokenLookup[c] == SPACE_CHAR ? otherCase : tokenLookup[c];
	}
	
	// Check to see if a character is a space
	public boolean isSpace(char c)
	{
		return tokenLookup[c] == SPACE_CHAR;
	}
}