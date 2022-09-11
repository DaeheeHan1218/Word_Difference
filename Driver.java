
public class Driver {
	static FileIO fileTest = new FileIO();
	static Hashing hashTest = new Hashing();
	//part 1
	static void readFiles() {
		if(fileTest.openTextFile("PT1.txt") && fileTest.openTextFile("YT1.txt")) {
			fileTest.readTextFile("PT1.txt");
			fileTest.readTextFile("YT1.txt");
		}
	}
	static void debug() {
		fileTest.writeData();
	}
	//part 2
	static void createListEqual() {
		hashTest.writeListEqual();
	}
	static void createListDiff() {
		hashTest.writeListDifference();
	}
	//part 3
	static void createEqual() {
		hashTest.writeEqual();
	}
	static void createDiff() {
		hashTest.writeDifference();
	}
	
	public static void main(String[] args) {
		readFiles();
		debug();
		createListEqual();
		createListDiff();
		createEqual();
		createDiff();
	}

}
