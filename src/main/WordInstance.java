package main;

public class WordInstance {
	private String word;
	private int count = 1;
	
	public WordInstance(String word) {
		this.word = word;
	}
	
	public void increment() {
		this.count++;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public int getCount() {
		return this.count;
	}
}
