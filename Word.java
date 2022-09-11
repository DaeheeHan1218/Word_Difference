
public class Word {
	private String value;
	private int countPT;
	private int countYT;
	
	//getters and setters
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value.toLowerCase(); }
	public int getCountPT() { return countPT; }
	public void setCountPT(int countPT) { this.countPT = countPT; }
	public int getCountYT() { return countYT; }
	public void setCountYT(int countYT) { this.countYT = countYT; }
	
	public Word(String value, int countPT, int countYT) {
		this.value = value;
		this.countPT = countPT;
		this.countYT = countYT;
	}
	
	public Word() {
		value = "";
		countPT = 0;
		countYT = 0;
	}
	
	public String toString() {
		return "----------\n"
				+ value + "\n"
				+ countPT + "\n"
				+ countYT + "\n";
	}
	
	public boolean equals(Word wrd2) {
		return this.value.equals(wrd2.getValue());
	}
}
