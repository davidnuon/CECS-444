/**
 * David Nuon 
 * Main Program
 */

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUIRunner {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// SWING!
		JOptionPane myWindow = new JOptionPane();
		JTextArea outputArea = new JTextArea(10, 60);
		Font font            = new Font("Monospaced", Font.PLAIN, 14);
		outputArea.setFont(font);
		
		JScrollPane scroller = new JScrollPane(outputArea);
		
		// 1. Get the file and the stuff inside
		String filename    = myWindow.showInputDialog("Select input file");
		Scanner scanny     = new Scanner(new File(filename)).useDelimiter("\\n");
		String fileContent = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		
		// 2. Let's make the container and add the words in
		ReservedWordList wordList = new ReservedWordList();
		while(scanny.hasNext()) {
			wordList.add(scanny.next().trim());
		}		
		
		// 3. We sort the list of words and get the output
		wordList.sort();
		String output = wordList.getStringList();
		
		// 4. We show the contents of the file and the sorted version
		outputArea.setText(fileContent);
		myWindow.showMessageDialog(null, scroller, "File Content of " + filename, JOptionPane.NO_OPTION);
		
		outputArea.setText(output);
		myWindow.showMessageDialog(null, scroller, "Sorted List of words in " + filename , JOptionPane.NO_OPTION);
		
		// 5. We ask the user to search for a word and tell them if it exists in there
		String searchItem = myWindow.showInputDialog("Please enter a keyword to search");
		String message = wordList.has(searchItem)
							? "True  - " + searchItem + " is in the word list."
							: "False - " + searchItem + " is not in the word list.";
		myWindow.showMessageDialog(null, message, "Result", JOptionPane.NO_OPTION);
		
		System.exit(0);
	}

}
