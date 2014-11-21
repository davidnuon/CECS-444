import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The mini SCanner
 * @author davidnuon
 */
public class BabyScanner {
	
	int currentRead;
	int state;
	String token;
	
	ArrayList<Token> tokenList;
	TokenDictionary dictionary;
	StateTable transitionTable;
	StateTable actionTable;
	StateTable lookupTable;

	final Token ERROR_TOKEN = new Token(0, "ERROR", "");
	
	/**
	 * Default Constructor
	 * 
	 * @param dictionaryFile
	 * @param transitionFile
	 * @param actionFile
	 * @param lookupFile
	 * @param charactersFile
	 * @throws FileNotFoundException
	 */
	public BabyScanner(String dictionaryFile, 
				       String transitionFile,
				       String actionFile,
				       String lookupFile,
				       String charactersFile) throws FileNotFoundException
	{
		state = 0;
		token = "";
		currentRead = 0;
		
		tokenList       = new ArrayList<Token>();
		dictionary      = new TokenDictionary();
		transitionTable = new StateTable();
		actionTable     = new StateTable();
		lookupTable     = new StateTable();

		
		// Make the token dictionary
		String dictionaryFileContent = new Scanner(new File(dictionaryFile)).useDelimiter("\\Z").next();
		dictionary.makeFromCSVString(dictionaryFileContent);
		
		// Get the state table
		String transitionTableContent = new Scanner(new File(transitionFile)).useDelimiter("\\Z").next();
		transitionTable.makeFromCSVString(transitionTableContent);
		
		// get the action table.
		String actionTableContent = new Scanner(new File(actionFile)).useDelimiter("\\Z").next();
		actionTable.makeFromCSVString(actionTableContent);
	
		// get the action table.
		String lookupTableContent = new Scanner(new File(lookupFile)).useDelimiter("\\Z").next();
		lookupTable.makeFromCSVString(lookupTableContent);
		
		// get the action table.
		String characterTableContent = new Scanner(new File(charactersFile)).useDelimiter("\\Z").next();
		dictionary.buildCharacterLookup(characterTableContent);
	}
	
	/**
	 * Takes in a files contents and tokenizes it
	 * 
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public void read_characters(String filename) throws FileNotFoundException 
	{
		char currentChar = ' ';
		boolean buffered = false;
		int position = -1;
		
		// We grab the files content at once
		String fileContent = new Scanner(new File(filename)).useDelimiter("\\Z").next() + "\n";

		// We go through the file
		while(true) {
			// We stop if we're at the end
			if(position == fileContent.length()) break;
			
			// If we don't have a character in the buffer we get a new one
			if(!buffered) {
				position++;
				
				// And we do that iff, we're not at the end
				if(position < fileContent.length()) currentChar = fileContent.charAt(position);
			}
			
			// Get the character type of the character
			currentRead = dictionary.getCharTokenID(currentChar);
			System.out.println("At state " + state + " with CR: " + currentRead + "'" + currentChar  + "'" + " " + (nextState(state, currentRead)) + "  Action:" +  (action(state, currentRead)));
			
			// Hardcode for Comments, treat space as ALPHA
			if(state == 29 && currentChar == ' ') {
				currentRead = 1;
			}

			// Hardcode for strings, treat space as ALPHA
			if(state == 36 && currentChar == ' ') {
				currentRead = 1;
			}
			
			// Hardcode for Comments
			if(state == 29 && currentChar == ' ') {
				currentRead = 1;
			}
			
			// Check for state transitions
			// Standard Transition 
			if( (nextState(state, currentRead) != -1) && (action(state, currentRead) == 1) )
			{
				token = token + currentChar;
				state = nextState(state, currentRead);
				buffered = false;
			}
			// Halting Condition
			else if( (nextState(state, currentRead) == -1) && (action(state, currentRead) == 2) )
			{
				int the_lookup = lookup(state, currentRead);
	
				// We build the token object
				String type = "";
				if(!dictionary.hasToken(the_lookup) || the_lookup == 0)
				{
					tokenList.add(new Token(0, "ERROR", token + currentChar));
					break;
				} else {
					type = dictionary.getTokenName(the_lookup);
					System.out.println("==============" + token + "==============" );
					tokenList.add(new Token(the_lookup, type, token));
				}	
			
				// Resetting
				buffered = true;
				state = 0;
				token = "";
			} 
			// If the character is not in our alphabet
			else {
				// If it's not a space, error
				if(!dictionary.isSpace(currentChar)) {
					tokenList.add(new Token(0, "ERROR", token + currentChar));
					break;
				}
				
				// Otherwise, ignore
				buffered = false;
			}
		}
	}
	
	/**
	 * 
	 * @param new_state
	 * @param new_char
	 * @return
	 */
	int nextState(int new_state, int new_char)
	{
		return transitionTable.getState(new_state, new_char);
	}
	
	/**
	 * 
	 * @param new_state
	 * @param new_char
	 * @return
	 */
	int action(int new_state, int new_char)
	{
		return actionTable.getState(new_state, new_char);
	}
	
	/**
	 * 
	 * @param new_state
	 * @param new_char
	 * @return
	 */
	int lookup(int new_state, int new_char)
	{
		System.out.println("LOOKUP: "  +new_state + " " + new_char + " " + lookupTable.getState(new_state, new_char));
		return lookupTable.getState(new_state, new_char);
	}
	
	public Object[] getTokenList()
	{
		return tokenList.toArray();
	}
}
