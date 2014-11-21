import java.io.FileNotFoundException;


public class CLIRunner {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException  {
		BabyScanner baby = new BabyScanner("data/dictionary.csv", 
										   "data/transition.csv",
										   "data/action.csv",
										   "data/lookup.csv",
										   "data/characters.csv");
		baby.read_characters("test_big_test.txt");
		Object[] tokenList = baby.getTokenList();
		
		for (int i = 0; i < tokenList.length; i++) {
			System.out.println(tokenList[i]);
		}
	}
}
