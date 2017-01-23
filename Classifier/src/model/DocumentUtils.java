package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentUtils {
	private static File document;
	private static FileReader documentReader;
	private static BufferedReader bf;
	
	public static ArrayList<String> readDocument(String filePath) throws IOException {
		document = new File(filePath);
		documentReader = new FileReader(document);
		bf = new BufferedReader(documentReader);
		String line;
		String totalText = null;
		while((line = bf.readLine()) != null) {
			totalText += line;
		}
		FilterSplitText(totalText);
		return new ArrayList<String>();
	}

	public static void FilterSplitText(String totalText) {
		totalText = totalText.replaceAll("[,.\\\\/\\[\\]\".,'{};:<>?!@#$%^&()\\-=+_`€¤]", "");
		String[] words = totalText.split(" ");
		System.out.println(totalText);
		
	}
	
	public static void main(String[] args) throws IOException {
		String filepath = "txt/blogs/F/F-test2.txt";
		readDocument(filepath);
	}
	
	/**
	 * Verwijder stopwoorden Nick
	 */
	public static void removeStopwords() {
		
	}
	
	/**
	 * Verwijder hoofdletter Nick
	 */
	public static void normalizeText() {
		
	}
	
	/**
	 * tel aantal documenten Nick
	 * @return
	 */
	public static int countDocs(ArrayList<String> filepaths) {
		return 0;
	}
	
	/**
	 * arend
	 * @param ic 
	 * @param d 
	 * @return
	 */
	public static int countDocsInClass(ArrayList<String> d, DocumentClass ic) {
		return 0;
	}
	/**
	 * Arend
	 */
	public static ArrayList<String> ConcatenateAllTextsOfDocsInClass(ArrayList<String> filepaths, DocumentClass ic) {
		return null;
	}
	
	/**
	 * Arend
	 * @param word 
	 * @param allWordsInClass 
	 * @return
	 */
	public static int countTokensOfTerm(ArrayList<String> allWordsInClass, String word) {
		return 0;
	}
	
	/**
	 * Laad alle docs in filepath Arend
	 */
	public static void loadDocuments() {
		
	}
	
	/**
	 * Returnt alle woorden in een gefilterde zak met woorden
	 * @param filePaths
	 */
	public static ArrayList<String> extractVocabulary(ArrayList<String> filePaths) {
		return null;
	}

	public static ArrayList<String> ExtractTokensFromDoc(FilteredDocument d) {
		// TODO Auto-generated method stub
		return null;
	}
}
