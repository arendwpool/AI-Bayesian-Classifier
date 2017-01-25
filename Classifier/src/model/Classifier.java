package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class Classifier {
	private static ArrayList<String> filepaths;
	private static ArrayList<String> allWords;
	private static int numberOfDocuments;
	private static HashMap<DocumentClass, Double> classes = new HashMap<DocumentClass, Double>();
	private static HashMap<HashMap<DocumentClass, String>, Double> probability = new HashMap<HashMap<DocumentClass, String>, Double>();
	
	public static void main(String[] args) throws IOException {
		DocumentClass class1 = new DocumentClass("F");
		DocumentClass class2 = new DocumentClass("M");
		DocumentClass[] classes = {class1, class2};
		String path = "txt\\blogs\\test\\F\\F-test1.txt";
		ArrayList<String> words = DocumentUtils.readDocument(path);
		FilteredDocument d = DocumentUtils.toFilteredDocument(words, path, class1);
		TrainMultinomialNaiveBayes(classes, DocumentUtils.loadDocuments("txt/blogs/test"));
		System.out.println(ApplyMultinomialNaiveBayes(d));
		
	}
	
	public static void TrainMultinomialNaiveBayes(DocumentClass[] c, ArrayList<String> d) throws IOException {
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(d);
		allWords = DocumentUtils.extractVocabulary(docs);
		numberOfDocuments = d.size();
		int i = 0;
		for(DocumentClass ic : c) {
			int n = DocumentUtils.countDocsInClass(docs, ic);
			double classPrior = n/numberOfDocuments;
			classes.put(ic,  classPrior);
			ArrayList<String> allWordsInClass = DocumentUtils.concatenateAllTextsOfDocsInClass(docs, ic);
			for(String word : allWords) {
				i++;
				System.out.println((double)(i*50)/allWords.size());
				int tokensOfTerm = DocumentUtils.countTokensOfTerm(allWordsInClass, word);
				HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
				classTerm.put(ic, word);
				double prob = calcProbability(tokensOfTerm, word);
				probability.put(classTerm, prob);
			}
		}
		writeToDoc();
	}

	private static void writeToDoc() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("probs.txt", "UTF-8");
		for (HashMap<DocumentClass, String> c : probability.keySet()) {
			for (DocumentClass c1 : c.keySet()) {
				writer.write(c.get(c1) + " : " + c1.getName() + " : " + probability.get(c)+"\n");
			}
		}
	    writer.println();
	    writer.close();
		
	}

	private static double calcProbability(int tokensOfTerm, String word) {
		double numerator = tokensOfTerm + 1;
		double denominator = allWords.size();
		return numerator/denominator;
	}
	
	public static double ApplyMultinomialNaiveBayes(FilteredDocument d) throws IOException {
		ArrayList<String> wordsInDocument = DocumentUtils.readDocument(d.getPath());
		double score = 0;
		for (DocumentClass ic : classes.keySet()) {
			score = classes.get(ic);
			System.out.println(score);
			for (String word : wordsInDocument) {
				HashMap<DocumentClass, String> wordInDoc = new HashMap<DocumentClass, String>();
				wordInDoc.put(ic, word);
				score += probability.get(wordInDoc);

				System.out.println(score);
			}
		}
		return score;
	}
	
}
