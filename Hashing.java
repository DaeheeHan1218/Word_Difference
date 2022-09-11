import java.io.*;
import java.util.*;

public class Hashing {
	private ArrayList<ArrayList<Word>> listTable;
	private ArrayList<BST> bstTable;//no need for <Word> since BST is coded to have nodes of Word
	
	public ArrayList<ArrayList<Word>> getListTable() { return listTable; }
	public void setListTable(ArrayList<ArrayList<Word>> listTable) { this.listTable = listTable; }
	public ArrayList<BST> getBstTable() { return bstTable; }
	public void setBstTable(ArrayList<BST> bstTable) { this.bstTable = bstTable; }
	
	
	public Hashing() {
		//makes empty table with word list
		listTable = new ArrayList<ArrayList<Word>>();
		ArrayList<Word> list = new ArrayList<Word>();
		listTable.add(list);
		//makes empty table with word BST
		bstTable = new ArrayList<BST>();
		BST tree = new BST();
		bstTable.add(tree);
		
		FileIO file = new FileIO();
		file.readTextFile("PT1.txt");
		file.readTextFile("YT1.txt");
		List<Word> words = file.getV();
		char currLetter = 'a';
		int currIndex = 0;
		Word currWord = new Word();
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).getValue().charAt(0) > currLetter) {//next alphabet started
				currLetter = words.get(i).getValue().charAt(0);
				currIndex++;
				ArrayList<Word> newList = new ArrayList<Word>();
				listTable.add(newList);
				BST newTree = new BST();
				bstTable.add(newTree);
			}
			currWord = words.get(i);
			listTable.get(currIndex).add(currWord);
			bstTable.get(currIndex).insert(currWord);
		}
	}
	
	public void writeListEqual() {//list version
		PrintWriter output = null;
		try {
			File listEqual = new File("resultsListEqual.txt");
			listEqual.createNewFile();
			output = new PrintWriter(listEqual);
		}
		catch(IOException e) {
			System.out.println("Error at writeListEqual(). File could not be created.");
		}
		for(int i = 0; i < listTable.size(); i++) {
			
			List<Word> list = listTable.get(i);
			for(int j = 0; j < list.size(); j++) {
				if(list.get(j).getCountPT() == list.get(j).getCountYT()) {
					output.println(list.get(j).getValue() + "\t\t" + list.get(j).getCountPT());
				}
			}
		}
		output.close();
	}
	public void writeEqual() {//tree version
		PrintWriter output = null;
		try {
			File treeEqual = new File("resultsEqual.txt");
			treeEqual.createNewFile();
			output = new PrintWriter(treeEqual);
			for(int i = 0; i < bstTable.size(); i++) {
				BST tree = bstTable.get(i);
				tree.outputInOrder(tree.getRoot(), output, 0);
			}
			output.close();
		}
		catch(IOException e) {
			System.out.println("Error at writeEqual(). File could not be created.");
		}
	}
	
	public void writeListDifference() {//list version
		PrintWriter output = null;
		try {
			File listDiff = new File("resultsListDiff.txt");
			listDiff.createNewFile();
			output = new PrintWriter(listDiff);
			for(int i = 0; i < listTable.size(); i++) {
				List<Word> list = listTable.get(i);
				for(int j = 0; j < list.size(); j++) {
					if(list.get(j).getCountPT() > list.get(j).getCountYT()) {
						output.print(list.get(j).getValue() + "\t\t" + "+" + (list.get(j).getCountPT() - list.get(j).getCountYT()) + " PT");
						if(list.get(j).getCountYT() == 0) {
							output.println(" - ZERO");
						}
						else {
							output.println("");
						}
					}
					else if(list.get(j).getCountPT() < list.get(j).getCountYT()) {
						output.print(list.get(j).getValue() + "\t\t" + "+" + (list.get(j).getCountYT() - list.get(j).getCountPT()) + " YT");
						if(list.get(j).getCountPT() == 0) {
							output.println(" - ZERO");
						}
						else {
							output.println("");
						}
					}
				}
			}
			output.close();
		}
		catch(IOException e) {
			System.out.println("Error at writeListDifference(). File could not be created.");
		}
	}
	
	public void writeDifference() {//tree version
		PrintWriter output = null;
		try {
			File treeDiff = new File("resultsDiff.txt");
			treeDiff.createNewFile();
			output = new PrintWriter(treeDiff);
			for(int i = 0; i < bstTable.size(); i++) {
				BST tree = bstTable.get(i);
				tree.outputInOrder(tree.getRoot(), output, 1);
			}
			output.close();
		}
		catch(IOException e) {
			System.out.println("Error at writeDifference(). File could not be created.");
		}
	}
	
	
	
}
