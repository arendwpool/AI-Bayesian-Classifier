package model;

import java.util.ArrayList;

public class FilteredDocument {
	
	private ArrayList<String> wordsInDocument = new ArrayList<String>();
	private DocumentClass documentClass;

	public ArrayList<String> getWords(){
		return wordsInDocument;
	}
	
	public void setWords(ArrayList<String> words) {
		wordsInDocument = words;
	}
}
