/**
 * David Nuon 
 * ReservedWordList - Convenience class for dealing with list of words
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservedWordList {
	// Instance Variables
	List<String> wordList;
	
	/**
	 * 	The constructor
	 */
	public ReservedWordList() {
		this.wordList = new ArrayList<String>();
	}
	
	/**
	 * Method to add a word to the list of words
	 * @param word
	 * @return void
	 */
	public void add(String word)
	{
		this.wordList.add(word);
	}

	
	/*
	 *  Method to sort list of words
	 *  @return void 
	 */
	public void sort() 
	{
		Collections.sort(this.wordList);
	}
	
	/**
	 * Method to check if an item exists in the array
	 * @param String item
	 * @return boolean - true if item in wordList
	 */
	public boolean has(String item)
	{
		return this.wordList.contains(item);
	}
	
	
	/**
	 * Method to return a list of all the words, in current order,
	 * delimited by line-breaks, as a string
	 * @return String 
	 */
	public String getStringList()
	{
		String out = "";
		
		for(int i = 0; i < this.wordList.size(); i++)
		{
			out += this.wordList.get(i);
			out += "\n";
		}
		
		return out;
	}
	
	/**
	 * Method to return the wordList an array of strings 
	 * @return Object[]
	 */
	public Object[] getArray()
	{
		return this.wordList.toArray();
	}
}
