package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

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
		String filepath = "txt/blogs";
		loadDocuments(filepath);
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
	 * @throws IOException 
	 */
	public static void loadDocuments(String parentfolder) throws IOException {
		Stream<Path> paths = Files.walk(Paths.get(parentfolder));
		ArrayList<String> filepaths = new ArrayList<String>();
		paths.forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				filepaths.add(filePath.normalize().toString());
			}
		});
		for (String s : filepaths) {
			System.out.println(s);
		}
		paths.close();
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
