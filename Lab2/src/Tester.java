
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReservedWordList listy = new ReservedWordList();
		
		for(int i = 0; i < 20; i ++) {
			listy.add("" + (int)(Math.random() * 100) );
		}
		
		listy.sort();
		
		Object[] arr =  listy.getArray();
		for(int i = 0; i < arr.length; i++)
		{
			System.out.println(arr[i]);
		}
		
	}

}
