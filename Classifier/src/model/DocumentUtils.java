package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


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
	
	public static ArrayList<String> finalVocabulary(ArrayList<String> filepaths, int trim) throws IOException {
		ArrayList<FilteredDocument> docs = createFilteredDocuments(filepaths);
		ArrayList<DocumentClass> c = new ArrayList<DocumentClass>();
		int i = 0;
		for (FilteredDocument d : docs) {
			i++;
			System.out.println("load docs: " + (double)(i*100)/docs.size());
			if (c.size() == 0) {
				c.add(d.getDocumentlass());
			} else {
				boolean exists = false;
				for (DocumentClass dc : c) {
					if (dc.getName().equals(d.getDocumentlass().getName())) {
						exists = true;
					}
				}
				if (exists == false ){
					c.add(d.getDocumentlass());
				}
			}
		}
		ArrayList<String> vocab = orderByChiSquare(docs, c, trim);
		for(String v : vocab) {
			//System.out.println(v);
		}
		return vocab;
	}
	
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
		totalText = totalText.replaceAll("[,.\\\\/\\[\\]|\".,'{};:<>0-9?!@#$%^&()*\\-=+_`€¤]", " ");
		totalText = Normalizer.normalize(totalText, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		String lowercased = totalText.toLowerCase();
		lowercased = lowercased.trim();
		String[] words = lowercased.split(" ");
		String[] stopwoo = stopwords.split("\n"); //IMPROVEME
		for (String word : words) {
            wordsList.add(word);
        }
		
		for (String w :  words) 
		{
			if (!Arrays.asList(stopwoo).contains(w)) {
				if (w.length() > 2)
				result.add(w);
			}
		}
        return result;
	}
	
	public static boolean classChecker(FilteredDocument doc, DocumentClass ic){
		String[] deelpath = null;
		String s = doc.getPath();
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
	public static int countDocsInClass(ArrayList<FilteredDocument> d, DocumentClass ic) {
		int counter = 0;
		for(FilteredDocument doc: d){
			if(classChecker(doc, ic) == true){
				counter += 1;	
			}
		}
		return counter;
	}
	/**
	 * Arend
	 * @throws IOException 
	 */
	public static ArrayList<String> concatenateAllTextsOfDocsInClass(ArrayList<FilteredDocument> docs, DocumentClass ic) throws IOException {
		ArrayList<FilteredDocument> docsInClass = new ArrayList<FilteredDocument>();
		for(FilteredDocument doc : docs) {
			if (classChecker(doc, ic)) {
				docsInClass.add(doc);
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
	 * Returnt alle woorden in een gefilterde zak met woorden
	 * @param filePaths
	 * @throws IOException 
	 */
	public static ArrayList<String> extractVocabulary(ArrayList<FilteredDocument> docs) {
		ArrayList<String> allWords = new ArrayList<String>();
		for (FilteredDocument d : docs) {
			ArrayList<String> words = d.getWords();
			allWords.addAll(words);
		}
		return allWords;
	}

	public static ArrayList<FilteredDocument> createFilteredDocuments(ArrayList<String> paths) throws IOException {
		ArrayList<FilteredDocument> docs = new ArrayList<FilteredDocument>();
		for(String path : paths) {
			String[] deelpath = null;
			if (path.contains("/"))
				deelpath = path.split("/");
			else if (path.contains("\\"))
				deelpath = path.split("\\\\");
			String clas = deelpath[deelpath.length -2];
			DocumentClass c = new DocumentClass(clas);
			ArrayList<String> words = readDocument(path);
			FilteredDocument doc = toFilteredDocument(words, path,c);
			docs.add(doc);
		}
		return docs;
	}
	
	public static FilteredDocument toFilteredDocument(ArrayList<String> words, String path, DocumentClass c) {
		return new FilteredDocument(words, path,c);
	}
	
	public static void main(String[] args) throws IOException {
		finalVocabulary(loadDocuments("txt/blogs/train"), 100);
		
	}
	
	public static ArrayList<String> orderByChiSquare(ArrayList<FilteredDocument> docs, ArrayList<DocumentClass> c, Integer trimlevel) throws IOException {
		ArrayList<String> allwords = extractVocabulary(docs);
		Set<String> words = new HashSet<String>(allwords);
		HashMap<String, Double> chis = new HashMap<String, Double>();
		int i = 0;
		for(String word : words) {
			i++;
			System.out.println("chi: "+(double) (i*100)/words.size());
			double chi = chiSquare(word, c, docs);
			chis.put(word, chi);
		}
		ArrayList<String> result = orderHashmap(chis, trimlevel);
		return result;
	}
	
	public static ArrayList<String> orderHashmap(HashMap<String, Double> chis, Integer trim){
		ArrayList<String> orderd = new ArrayList<String>();
		Object[] a = chis.entrySet().toArray();
		Arrays.sort(a, new Comparator() {
		    public int compare(Object o1, Object o2) {
		        return ((Map.Entry<String, Double>) o2).getValue()
		                   .compareTo(((Map.Entry<String, Double>) o1).getValue());
		    }
		});
		int i = 0;
		for (Object e : a) {
			if(i < trim){
				i += 1;
				orderd.add(((Map.Entry<String, Double>) e).getKey());
		    }
		}
		return orderd;
	}

	
	public static int countAllWords(ArrayList<FilteredDocument> d) {
		int i = 0;
		for (FilteredDocument doc : d) {
			i += doc.getWords().size();
		}
		return i;
	}
	
	public static int countOccurancesInClass (String word, ArrayList<FilteredDocument> d, DocumentClass c) {
		int W1 = 0;
		for (FilteredDocument fd : d) {
			if (fd.getDocumentlass().getName().equals(c.getName())){
			for (String w2 : fd.getWords()) {
				if (w2.equals(word)) {
					W1++;
					break;
				}
			}
			}
		}
		return W1;
	}
	
	public static double chiSquare(String w, ArrayList<DocumentClass> c, ArrayList<FilteredDocument> d) throws IOException{
		HashMap<DocumentClass, Integer> M1 = new HashMap<DocumentClass, Integer>();
		HashMap<DocumentClass, Integer> M2 = new HashMap<DocumentClass, Integer>();
		int N = d.size();
		int totalW1 = 0;
		int totalW2 = 0;
		double X2 = 0;
		for (DocumentClass dc : c) {
			int W1 = 0;
			int C = 0;
			for (FilteredDocument fd : d) {
				if (dc.getName().equals(fd.getDocumentlass().getName())) {
					C++;
				for (String w2 : fd.getWords()) {
					if (w2.equals(w)) {
						W1++;
						break;
					}
				}
				}
			}
			M1.put(dc, W1);
			M2.put(dc, C);
			totalW1 += W1;
		}
		totalW2 = N - totalW1;
		for (DocumentClass dc2 : c) {
			int val = M1.get(dc2); // gelijk aan W1
			int val2 = M2.get(dc2); // gelijk aan W2
			double exp = expectedValue(totalW1, val2, N);
			double teller = (val - exp);
			double tellerSquared = teller*teller;
			X2 += tellerSquared / exp; 
			int val3 = val2 - val;
			double exp2 = expectedValue(totalW2, val2, N);
			double teller2 = (val3 - exp2);
			double teller2Squared = teller2*teller2;
			X2 += teller2Squared / exp2;
		}
		
		
		return X2;
	}
	
	public static double expectedValue(int W, int C, int N){
		return (double) (W * C)/ N;
	}
}
