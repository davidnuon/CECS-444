/**
 * Class for a Token Object
 * @author davidnuon
 *
 */
public class Token {
	int tokenID;
	String type;
	String token;
	
	/**
	 * 
	 * @param tokenID
	 * @param type
	 * @param token
	 */
	public Token(int tokenID, String type, String token) {
		this.tokenID = tokenID;
		this.type = type;
		this.token = token;
	}

	/**
	 * @return the tokenID
	 */
	public int getTokenID() {
		return tokenID;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%-15s: %-10s", type, token);
	}
	
	
}