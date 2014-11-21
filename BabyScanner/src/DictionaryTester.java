import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DictionaryTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String fileContent = new Scanner(new File("data/dictionary.csv")).useDelimiter("\\Z").next() + "\n";
		TokenDictionary dict = new TokenDictionary();
		dict.makeFromCSVString(fileContent);
		
		System.out.println(dict);
	}

}
