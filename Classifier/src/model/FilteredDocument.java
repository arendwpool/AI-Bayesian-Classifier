package model;

import java.util.ArrayList;

public class FilteredDocument {
	
	public FilteredDocument (ArrayList<String> words, String path, DocumentClass c) {
		setWords(words);
		setPath(path);
		setDocumentClass(c);
	}
	private ArrayList<String> wordsInDocument = new ArrayList<String>();
	private String path;
	private DocumentClass c;

	public String getPath() {
		return path;
	}
	
	public DocumentClass getDocumentlass() {
		return c;
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
	
	public void setDocumentClass(DocumentClass c) {
		this.c = c;
	}
}
