import java.io.*;
import java.util.*;

public class FileIO {
	private ArrayList<Word> v;
	private Scanner input;
	
	public ArrayList<Word> getV() { return v; }
	public void setV(ArrayList<Word> v) { this.v = v; }
	public FileIO() { v = new ArrayList<Word>(); input = null; }
	
	public boolean openTextFile(String filename) {
		try {
			input = new Scanner(new FileReader(filename));
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
			return false;
		}
		input.close();
		return true;
	}
	private String processWord(String word) {//processes each word so they can be put into vector.
		String newWord = "";
		for(int i = 0; i < word.length(); i++) {
			if(newWord == "") {//no letter has been added yet
				if(word.charAt(i) >= 97 && word.charAt(i) <= 122) {//first letter must be alphabet
					newWord = newWord + String.valueOf(word.charAt(i));
				}
			}
			else if(word.charAt(i) >= 97 && word.charAt(i) <= 122) {//If char at i is alphabet
				newWord = newWord + String.valueOf(word.charAt(i));//adds alphabet at i to newWord
			}
			else if(word.charAt(i) == 39) {//if char at i is '
				if(i > 0) {
					if(word.charAt(i-1) >= 97 && word.charAt(i-1) <= 122) {//char at prev index is alphabet
						if((i == word.length() - 1)) {//end of string
							newWord = newWord + String.valueOf(word.charAt(i));
						}
						else if(word.charAt(i+1) >= 97 && word.charAt(i+1) <= 122) {//char at next index is alphabet
							newWord = newWord + String.valueOf(word.charAt(i));
						}
					}
				}
			}
			else if(word.charAt(i) == 45) {//if char at i is -
				if(i > 0 && i < word.length() - 1) {
					if((word.charAt(i-1) >= 97 && word.charAt(i-1) <= 122)
					&& (word.charAt(i+1) >= 97 && word.charAt(i+1) <= 122)) {
						newWord = newWord + String.valueOf(word.charAt(i));
					}
				}
			}
		}
		return newWord;
	}
	
	public ArrayList<Word> readTextFile(String filename) {
		if(openTextFile(filename)) {
			try {
				input = new Scanner(new FileReader(filename));
			}
			catch(FileNotFoundException e) {
				System.out.println("File not found");
			}
			String word;
			StringTokenizer tokenizer = null;
			
			while(input.hasNextLine()) {
				String line = input.nextLine();
				tokenizer = new StringTokenizer(line);
				int totalToken = tokenizer.countTokens();
				for(int j = 0; j < totalToken; j = j + 1){
					Word temp = new Word();
					word = tokenizer.nextToken();
					word = word.toLowerCase();
					word = processWord(word);
					if(word != "") {
						if(v.size() == 0) {//first element
							temp.setValue(word);
							if(filename == "PT1.txt") {
								temp.setCountPT(1);
								temp.setCountYT(0);
							}
							else if(filename == "YT1.txt") {
								temp.setCountPT(0);
								temp.setCountYT(1);
							}
							v.add(temp);
						}
						else{
							for(int i = 0; i < v.size(); i++) {
								if(v.get(i).getValue().compareTo(word) == 0) {//duplicate found
									temp = v.get(i);
									if(filename == "PT1.txt") {
										temp.setCountPT((temp.getCountPT() + 1));
									}
									else if(filename == "YT1.txt") {
										temp.setCountYT((temp.getCountYT() + 1));
									}
									v.set(i, temp);
									break;
								}
								else if(v.get(i).getValue().compareTo(word) > 0){//alphabetically larger word found
									temp.setValue(word);
									if(filename == "PT1.txt") {
										temp.setCountPT(1);
										temp.setCountYT(0);
									}
									else if(filename == "YT1.txt") {
										temp.setCountPT(0);
										temp.setCountYT(1);
									}
									v.add(i, temp);
									break;
								}
								else if (i == (v.size() - 1)){//end of vector reached.
									temp.setValue(word);
									if(filename == "PT1.txt") {
										temp.setCountPT(1);
										temp.setCountYT(0);
									}
									else if(filename == "YT1.txt") {
										temp.setCountPT(0);
										temp.setCountYT(1);
									}
									v.add(temp);
									break;
								}
							}
						}
					}
				}
			}
			input.close();
		}
		
		return v;
	}
	
	public void writeData() {
		PrintWriter output = null;
		try {
			File debug1 = new File("debug1.txt");
			debug1.createNewFile();
			output = new PrintWriter(debug1);
			for(int i = 0; i < v.size(); i++) {
				output.println(v.get(i).getValue());
			}
			output.close();
		}
		catch(IOException e) {
			System.out.println("Error at writeData(). File could not be created.");
		}
		
	}
	
}
