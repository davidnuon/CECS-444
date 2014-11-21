/**
 *    David Nuon
 *    Baby Parser
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class BabyParser {
	// Parse table that grabs from an external file
	Table parseTable;
	
	// Stack we're using 
    Stack the_stack;
	
	String current_token;
	int table_entry;
	int token_number;
	int stacktop;
	int wait;
	
	// Terminals
	int TERMINAL_PLUS     = -1;
	int TERMINAL_MINUS    = -2;
	int TERMINAL_MULTIPLY = -3;
	int TERMINAL_DIVIDE   = -4;
	int TERMINAL_LEFT     = -5;
	int TERMINAL_RIGHT    = -6;
	int TERMINAL_NUMBER   = -7;
	int TERMINAL_POWER    = -8;
	int TERMINAL_OTHER    = -9;
	
	// NonTerminals
	int PRODUCTION_E      = 1; // GOAL
	int PRODUCTION_Eprime = 2;
	int PRODUCTION_T      = 3;
	int PRODUCTION_Tprime = 4;
	int PRODUCTION_P      = 5;
	int PRODUCTION_Pprime = 6;
	int PRODUCTION_F      = 7;
	
	// Constructor
	public BabyParser(String parseTableFile) throws FileNotFoundException {
		// Setup
		String parseTableContent = new Scanner(new File(parseTableFile))
			.useDelimiter("\\Z").next();
		
		the_stack = new Stack<Integer>();
		
		parseTable = new Table();
		parseTable.makeFromCSVString(parseTableContent);
		
	}
	
	// Method to read in the file and parse it
	public void read_characters(String filename) throws FileNotFoundException 
	{
		// Start parse
		// Push the Goal on top
		push(PRODUCTION_E);
		
		// We grab the files content at once
		// Since we're not using our own lexer, we have to break up the tokens by something
		// and that delimiter is a space
		Scanner fileContent = new Scanner(new File(filename)).useDelimiter(" ");
		
		// Get our first token
		current_token = fileContent.next();
		
		// Going through getting the tokens...
		while(fileContent.hasNext()) {
			
			// Get the token type
			if		 (current_token.equals("+") )  { token_number = TERMINAL_PLUS;     }
			else if (current_token.equals("-") )  { token_number = TERMINAL_MINUS;    }
			else if (current_token.equals("*") )  { token_number = TERMINAL_MULTIPLY; }
			else if (current_token.equals("/") )  { token_number = TERMINAL_DIVIDE;   }
			else if (current_token.equals("(") )  { token_number = TERMINAL_LEFT;     }
			else if (current_token.equals(")") )  { token_number = TERMINAL_RIGHT;    }
			else if (current_token.equals("id"))  { token_number = TERMINAL_NUMBER;   }
			else if (isNum(current_token      ))  { token_number = TERMINAL_NUMBER;   }
			else if (current_token.equals("^"))   { token_number = TERMINAL_POWER;    }
			else                                  { token_number = TERMINAL_OTHER;    }
			
			// Peek at the top
			int stacktop = top();
			
			// debug stubs
			// System.out.print("The top: " + stacktop + " " + token_number + " ");
			// System.out.print(the_stack);
			
			// If we have a nonterminal...
			if(stacktop > 0) {
				// Get the result from the parse table
				table_entry = parseTable.getCell(stacktop, Math.abs(token_number));
				
				// and push certain productions based on result
				// we push back the production backwards
				switch(table_entry) {
					case 1:
						System.out.println("Fire 1");
						stacktop = pop();
						push(PRODUCTION_Eprime);
						push(PRODUCTION_T);
						break;
					case 2:
						System.out.println("Fire 2");
						stacktop = pop();
						push(PRODUCTION_Eprime);
						push(PRODUCTION_T);
						push(TERMINAL_PLUS);
						break;
					case 3:
						System.out.println("Fire 3");
						stacktop = pop();
						push(PRODUCTION_Eprime);
						push(PRODUCTION_T);
						push(TERMINAL_MINUS);
						break;
					case 4:
						System.out.println("Fire 4");
						stacktop = pop();
						break;
					case 5:
						System.out.println("Fire 5");
						stacktop = pop();
						push(PRODUCTION_Tprime);
						push(PRODUCTION_P);
						break;
					case 6:
						System.out.println("Fire 6");
						stacktop = pop();
						push(PRODUCTION_Tprime);
						push(PRODUCTION_P);
						push(TERMINAL_MULTIPLY);
						break;
					case 7:
						System.out.println("Fire 7");
						stacktop = pop();
						push(PRODUCTION_Tprime);
						push(PRODUCTION_P);
						push(TERMINAL_DIVIDE);
						break;
					case 8:
						System.out.println("Fire 8");
						stacktop = pop();
						break;
					case 9:
						System.out.println("Fire 9");
						stacktop = pop();
						push(PRODUCTION_Pprime);
						push(PRODUCTION_F);
						break;
					case 10:
						System.out.println("Fire 10");
						stacktop = pop();
						push(PRODUCTION_Pprime);
						push(PRODUCTION_F);
						push(TERMINAL_POWER);
						break;
					case 11:
						System.out.println("Fire 11");
						stacktop = pop();
						break;
					case 12:
						System.out.println("Fire 12");
						stacktop = pop();
						push(TERMINAL_RIGHT);
						push(1);
						push(TERMINAL_LEFT);
						break;
					case 13:
						System.out.println("Fire 13");
						stacktop = pop();
						push(TERMINAL_NUMBER);
						break;
					case 98:
						System.out.println("Scan Error");
						break;
					case 99:
						System.out.println("Pop Error");
						break;
					default:
						System.out.println("Error");
				}
			} 
			
			// If we have a terminal, we match and pop 
			else if( stacktop == token_number ) 
			{
				System.out.println("Match and pop " + current_token);
				stacktop = pop();
				current_token = fileContent.next();
			}
		}
	}
	
	public static boolean isNum(String str) {
	  return str.matches("-?\\d+(\\.\\d+)?");  
	}
	
	void push(int n) {
		the_stack.push(new Integer(n));
	}
	
	int pop() {
		return ( (Integer) the_stack.pop() ).intValue();
	}
	
	int top() {
		return ( (Integer) the_stack.peek() ).intValue();
	}
}
