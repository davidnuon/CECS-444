
public class Runner {
	public static void main( String argv[]) throws Exception {
		BabyParser baby = new BabyParser("parseTable_new.csv");
		baby.read_characters("konig.txt");
	}
	
}
