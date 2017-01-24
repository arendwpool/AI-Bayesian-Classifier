package model;

import java.util.ArrayList;

public class FilteredDocument {
	
	public FilteredDocument (ArrayList<String> words, String path) {
		setWords(words);
		setPath(path);
	}
	private ArrayList<String> wordsInDocument = new ArrayList<String>();
	private String path;

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	public ArrayList<String> getWords(){
		return wordsInDocument;
	}
	
	public void setWords(ArrayList<String> words) {
		wordsInDocument = words;
	}
}
