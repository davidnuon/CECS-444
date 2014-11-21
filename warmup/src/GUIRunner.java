/**
 * David Nuon 
 * CECS 444
 * Warmup Lab
 * 
 * Program that opens up a specified file, cuts it up and displays a certain set of characters
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
		// SWING! -_-
		JOptionPane myWindow = new JOptionPane();
		JTextArea outputArea = new JTextArea(15, 40);
		Font font = new Font("Monospaced", Font.PLAIN, 14);
		outputArea.setFont(font);
		
		JScrollPane scroller = new JScrollPane(outputArea);
		
		// 1. Get the file and the stuff inside
		String filename = myWindow.showInputDialog("Select input file");
		Scanner scanny = new Scanner(new File(filename)).useDelimiter("\\n");
		String fileContent = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		
		// 2. Use the StingCutter to break it up
		StringCutter cutter = new StringCutter();
		
		while(scanny.hasNext()) {
			cutter.addLine(scanny.next());
		}
		
		// 3. Display it
		String output = cutter.getTextBlock();
		
		outputArea.setText(fileContent);
		myWindow.showMessageDialog(null, scroller, "File Content", JOptionPane.NO_OPTION);
		
		outputArea.setText(output);
		myWindow.showMessageDialog(null, scroller, "Break up", JOptionPane.NO_OPTION);
		
		System.exit(0);
	}

}
