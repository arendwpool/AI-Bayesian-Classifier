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
		bf.close();
		documentReader.close();
		return FilterSplitText(totalText);
	}

	public static ArrayList<String> FilterSplitText(String totalText) throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		totalText = totalText.replaceAll("[,.\\\\/\\[\\]\".,'{};:<>0-9?!@#$%^&()*\\-=+_`��]", "");
		String lowercased = totalText.toLowerCase();
		lowercased.trim();
		String[] words = lowercased.split(" ");
		String[] stopwoo = stopwords.split("\n"); //IMPROVEME
		for (String word : words) {
            wordsList.add(word);
        }
		
		for (String w :  words) 
		{
			if (!Arrays.asList(stopwoo).contains(w)) {
				result.add(w);
			}
		}
        return result;
	}
	
	public static boolean classChecker(String s, DocumentClass ic){
		String[] deelpath = null;
		if (s.contains("/"))
			deelpath = s.split("/");
		else if (s.contains("\\"))
			deelpath = s.split("\\\\");
		return (deelpath[deelpath.length -2].equals(ic.getName()));
	}
	
	/**
	 * arend
	 * @param ic 
	 * @param d 
	 * @return
	 */
	// tellen hoevaak class ic voor komt in alle paths d, door te kijken naar einde van path
	public static int countDocsInClass(ArrayList<String> d, DocumentClass ic) {
		int counter = 0;
		for(String path: d){
			if(classChecker(path, ic) == true){
			counter += 1;	
			}
		}
		return counter;
	}
	/**
	 * Arend
	 * @throws IOException 
	 */
	public static ArrayList<String> concatenateAllTextsOfDocsInClass(ArrayList<String> allpaths, DocumentClass ic) throws IOException {
		ArrayList<String> docsInClass = new ArrayList<String>();
		for(String path : allpaths) {
			if (classChecker(path, ic)) {
				docsInClass.add(path);
			}
		}
		return extractVocabulary(docsInClass);
	}
	
	/**
	 * Arend
	 * @param word 
	 * @param allWordsInClass 
	 * @return
	 */
	public static int countTokensOfTerm(ArrayList<String> allWordsInClass, String word) {
		int tokencount = 0;
		for(String woorden: allWordsInClass){
			if(woorden.equals(word)){
				tokencount += 1;
			}
		}
		return tokencount;
	}
	
	/**
	 * Laad alle docs in filepath Arend
	 * @return 
	 * @throws IOException 
	 */
	public static ArrayList<String> loadDocuments(String parentfolder) throws IOException {
		Stream<Path> paths = Files.walk(Paths.get(parentfolder));
		ArrayList<String> filepaths = new ArrayList<String>();
		paths.forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				filepaths.add(filePath.normalize().toString());
			}
		});
		paths.close();
		return filepaths;
	}
	
	/**
	 * Laad alle docs in filepath Arend
	 * @return 
	 * @throws IOException 
	 */
	public static ArrayList<String> loadDocuments(String parentfolder, DocumentClass ic) throws IOException {
		ArrayList<String> filepaths = loadDocuments(parentfolder);
		ArrayList<String> classFilePaths = new ArrayList<String>();
		for (String path : filepaths) {
			if (classChecker(path, ic)) {
				classFilePaths.add(path);
			}
		}
		return classFilePaths;
	}
	/**
	 * Returnt alle woorden in een gefilterde zak met woorden
	 * @param filePaths
	 * @throws IOException 
	 */
	public static ArrayList<String> extractVocabulary(ArrayList<String> filepaths) {
		ArrayList<String> allWords = new ArrayList<String>();
		int i = 0;
		int i2 = filepaths.size();
		for (String filepath : filepaths) {
			i++;
			System.out.println((i*100)/i2 + "%");
			try {
			ArrayList<String> words = readDocument(filepath);
			allWords.addAll(words);
			} catch (IOException e) {
				System.out.println("FOUT");
			}
		}
		return allWords;
	}

	public static ArrayList<FilteredDocument> createFilteredDocuments(ArrayList<String> paths) throws IOException {
		ArrayList<FilteredDocument> docs = new ArrayList<FilteredDocument>();
		for(String path : paths) {
			ArrayList<String> words = readDocument(path);
			FilteredDocument doc = toFilteredDocument(words, path);
			docs.add(doc);
		}
		return docs;
	}
	
	public static FilteredDocument toFilteredDocument(ArrayList<String> words, String path) {
		return new FilteredDocument(words, path);
	}
}