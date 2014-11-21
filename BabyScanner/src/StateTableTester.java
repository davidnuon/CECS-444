import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class StateTableTester {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String fileContent = new Scanner(new File("data/action.csv")).useDelimiter("\\Z").next();
		StateTable stateTable = new StateTable();
		stateTable.makeFromCSVString(fileContent);
		
		System.out.println(stateTable);
		System.out.println(stateTable.getState(0, 1));
	}

}
