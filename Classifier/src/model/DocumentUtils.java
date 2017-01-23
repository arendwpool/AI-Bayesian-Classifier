package model;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.HashSet;


public class DocumentUtils {
	private static File document;
	private static FileReader documentReader;
	private static BufferedReader bf;
	public static ArrayList<String> wordsList = new ArrayList<String>();
	private static String stopwords = ("\n" +
            "a\n" +
            "about\n" +
            "above\n" +
            "after\n" +
            "again\n" +
            "against\n" +
            "all\n" +
            "am\n" +
            "an\n" +
            "and\n" +
            "any\n" +
            "are\n" +
            "arent\n" +
            "as\n" +
            "at\n" +
            "be\n" +
            "because\n" +
            "been\n" +
            "before\n" +
            "being\n" +
            "below\n" +
            "between\n" +
            "both\n" +
            "but\n" +
            "by\n" +
            "cant\n" +
            "cannot\n" +
            "could\n" +
            "couldnt\n" +
            "did\n" +
            "didnt\n" +
            "do\n" +
            "does\n" +
            "doesnt\n" +
            "doing\n" +
            "dont\n" +
            "down\n" +
            "during\n" +
            "each\n" +
            "few\n" +
            "for\n" +
            "from\n" +
            "further\n" +
            "had\n" +
            "hadnt\n" +
            "has\n" +
            "hasnt\n" +
            "have\n" +
            "havent\n" +
            "having\n" +
            "he\n" +
            "hed\n" +
            "hell\n" +
            "hes\n" +
            "her\n" +
            "here\n" +
            "heres\n" +
            "hers\n" +
            "herself\n" +
            "him\n" +
            "himself\n" +
            "his\n" +
            "how\n" +
            "hows\n" +
            "i\n" +
            "id\n" +
            "ill\n" +
            "im\n" +
            "ive\n" +
            "if\n" +
            "in\n" +
            "into\n" +
            "is\n" +
            "isnt\n" +
            "it\n" +
            "its\n" +
            "its\n" +
            "itself\n" +
            "lets\n" +
            "me\n" +
            "more\n" +
            "most\n" +
            "mustnt\n" +
            "my\n" +
            "myself\n" +
            "no\n" +
            "nor\n" +
            "not\n" +
            "of\n" +
            "off\n" +
            "on\n" +
            "once\n" +
            "only\n" +
            "or\n" +
            "other\n" +
            "ought\n" +
            "our\n" +
            "ours\tourselves\n" +
            "out\n" +
            "over\n" +
            "own\n" +
            "same\n" +
            "shant\n" +
            "she\n" +
            "shed\n" +
            "shell\n" +
            "shes\n" +
            "should\n" +
            "shouldnt\n" +
            "so\n" +
            "some\n" +
            "such\n" +
            "than\n" +
            "that\n" +
            "thats\n" +
            "the\n" +
            "their\n" +
            "theirs\n" +
            "them\n" +
            "themselves\n" +
            "then\n" +
            "there\n" +
            "theres\n" +
            "these\n" +
            "they\n" +
            "theyd\n" +
            "theyll\n" +
            "theyre\n" +
            "theyve\n" +
            "this\n" +
            "those\n" +
            "through\n" +
            "to\n" +
            "too\n" +
            "under\n" +
            "until\n" +
            "up\n" +
            "very\n" +
            "was\n" +
            "wasnt\n" +
            "we\n" +
            "wed\n" +
            "well\n" +
            "were\n" +
            "weve\n" +
            "were\n" +
            "werent\n" +
            "what\n" +
            "whats\n" +
            "when\n" +
            "whens\n" +
            "where\n" +
            "wheres\n" +
            "which\n" +
            "while\n" +
            "who\n" +
            "whos\n" +
            "whom\n" +
            "why\n" +
            "whys\n" +
            "with\n" +
            "wont\n" +
            "would\n" +
            "wouldnt\n" +
            "you\n" +
            "youd\n" +
            "youll\n" +
            "youre\n" +
            "youve\n" +
            "your\n" +
            "yours\n" +
            "yourself\n" +
            "yourselves");
	
	
	public static ArrayList<String> readDocument(String filePath) throws IOException {
		document = new File(filePath);
		documentReader = new FileReader(document);
		bf = new BufferedReader(documentReader);
		String line;
		String totalText = "";
		while((line = bf.readLine()) != null) {
			totalText += line;
		}
		FilterSplitText(totalText);
		return new ArrayList<String>();
	}

	public static void FilterSplitText(String totalText) throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		totalText = totalText.replaceAll("[,.\\\\/\\[\\]\".,'{};:<>?!@#$%^&()\\-=+_`€¤]", "");
		String lowercased = totalText.toLowerCase();
		lowercased.trim();
		String[] words = lowercased.split(" ");
		String[] stopwoo = stopwords.split("\n");
		for (String word : words) {
            wordsList.add(word);
        }
		for (int i = 0; i < wordsList.size(); i++) {
			 for (int j = 0; j < stopwoo.length; j++) {
	                if (wordsList.contains(stopwoo[j])) {
	                    wordsList.remove(stopwoo[j]);
	                }
	            }
        }
        for (String str : wordsList) {
        	result.add(str);
        }
        System.out.print(result);
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
