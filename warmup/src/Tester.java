import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Tester {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanny = new Scanner(new File("input.txt")).useDelimiter("\\n");
		StringCutter cutter = new StringCutter();
		
		while(scanny.hasNext()) {
			cutter.addLine(scanny.next());
		}
		
		System.out.printf("%s", cutter.getTextBlock());
		
	}

}
