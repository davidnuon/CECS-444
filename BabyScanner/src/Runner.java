import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
 
public class Runner extends JPanel implements ActionListener {
    private JTextArea sourceCode;
    private JTextArea tokenDisplay;
    private JTextField filenameField;
    private JButton getFileNameButton;
    private BabyScanner baby;
    
    public Runner() {
    	super(new BorderLayout());
		Font font = new Font("Monospaced", Font.PLAIN, 14);
    	sourceCode = new JTextArea(20, 40);
    	tokenDisplay = new JTextArea(20, 40);
    	filenameField = new JTextField(70);
    	getFileNameButton = new JButton("Run");
    	getFileNameButton.addActionListener(this);
    	
    	sourceCode.setFont(font);
    	tokenDisplay.setFont(font);
    	filenameField.setFont(font);
    	
    	JPanel left = new JPanel(new BorderLayout());
    	JPanel right = new JPanel(new BorderLayout());
    	JPanel bottom = new JPanel(new BorderLayout());
    	
    	JLabel inputText = new JLabel("Input Text");
    	inputText.setHorizontalAlignment( SwingConstants.CENTER );
    	inputText.setBorder(new EmptyBorder(10, 10, 10, 10) );
    	left.add(inputText, BorderLayout.NORTH);
        left.add(new JScrollPane(sourceCode));
        
        
    	JLabel tokenText = new JLabel("Token List");
    	tokenText.setHorizontalAlignment( SwingConstants.CENTER );
    	tokenText.setBorder(new EmptyBorder(10, 10, 10, 10) );

        right.add(tokenText, BorderLayout.NORTH);
        right.add(new JScrollPane(tokenDisplay));
             
        bottom.add(filenameField, BorderLayout.WEST);
        bottom.add(getFileNameButton, BorderLayout.EAST);
        filenameField.setBorder(new EmptyBorder(10, 10, 10, 10) );
        
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }
 
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
 
        //Create and set up the window.
        JFrame frame = new JFrame("Baby Scanner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        Runner runner= new Runner();
        frame.add(runner);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		 String action = e.getActionCommand();
		 if (action.equals("Run")) {
				try {
					baby = new BabyScanner("data/dictionary.csv", 
							   "data/transition.csv",
							   "data/action.csv",
							   "data/lookup.csv",
							   "data/characters.csv");
			
					Map<String, Integer> table = new HashMap<String, Integer>();
					
					String filename = filenameField.getText();
					baby.read_characters(filename);
					Object[] tokenList = baby.getTokenList();
					String tokenContent = "";
					
					Scanner scanny     = new Scanner(new File("reservedWord.txt")).useDelimiter("\\n");
					
					// 2. Let's make the container and add the words in
					ReservedWordList wordList = new ReservedWordList();
					while(scanny.hasNext()) {
						wordList.add(scanny.next().trim());
					}		
					
					for (int i = 0; i < tokenList.length; i++) {
						boolean exists = false;
						String the_token = ((Token) tokenList[i]).getToken().trim();
						boolean is_id = ((Token) tokenList[i]).getType().compareTo("IDENTIFIER") == 0;
						is_id &= !wordList.has(the_token);
							
						
						if(is_id) {
							System.out.println("ID!");
							System.out.println(the_token);
							if (table.get(the_token) == null) {
								table.put(the_token, 1);
							} else {
								exists = true;
								table.put(the_token, table.get(the_token) + 1);
							}
						}
						
						tokenContent = tokenContent + tokenList[i];
						if(wordList.has(the_token)) {
							tokenContent += " Reserved Word";	
						}
						
						if(exists) {
							tokenContent += " Seen";
						}
						tokenContent += "\n";
					}			
									
					
					// Get entries and sort them.
					ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(table.entrySet());
					Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
					    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
					        return e2.getValue().compareTo(e1.getValue());
					    }
					});

					// Put entries back in an ordered map.
					Map<String, Integer> orderedMap = new LinkedHashMap<String, Integer>();
					for (Map.Entry<String, Integer> entry : entries) {
					    orderedMap.put(entry.getKey(), entry.getValue());
					}

					tokenContent += "\n\n\nIdentifiers List\n";
					
					
					for (Map.Entry<String, Integer> entry : orderedMap.entrySet()) {
					    String key = entry.getKey();
					    Object value = entry.getValue();
					    tokenContent += key + "\t";
					    tokenContent += value + "\n";
					}
					
					tokenDisplay.setText(tokenContent);
					sourceCode.setText(new Scanner(new File(filename)).useDelimiter("\\Z").next());
					baby = null;
					
					// Write to a file
					PrintWriter writer = new PrintWriter((new Date()).toString() + ".txt", "UTF-8");
					writer.println(tokenContent);
					writer.close();
					
				} catch (FileNotFoundException e1) {
					tokenDisplay.setText("File not found");
					sourceCode.setText("File not found");
				} catch (IOException exp) {
					sourceCode.setText("Unable to Write File");
				}
		 }
	}
	
}
