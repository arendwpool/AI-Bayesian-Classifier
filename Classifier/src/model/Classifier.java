package model;

import java.io.IOException;
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
		String path = "txt\\blogs\\F\\F-test1.txt";
		ArrayList<String> words = DocumentUtils.readDocument(path);
		FilteredDocument d = DocumentUtils.toFilteredDocument(words, path);
		TrainMultinomialNaiveBayes(classes, DocumentUtils.loadDocuments("txt/blogs/Train"));
		System.out.println(ApplyMultinomialNaiveBayes(d));
		
	}
	
	public static void TrainMultinomialNaiveBayes(DocumentClass[] c, ArrayList<String> d) throws IOException {
		allWords = DocumentUtils.extractVocabulary(d);
		numberOfDocuments = d.size();
		int i = 0;
		for(DocumentClass ic : c) {
			int n = DocumentUtils.countDocsInClass(d, ic);
			double classPrior = n/numberOfDocuments;
			classes.put(ic,  classPrior);
			ArrayList<String> allWordsInClass = DocumentUtils.concatenateAllTextsOfDocsInClass(d, ic);
			for(String word : allWords) {
				i++;
				System.out.println((double)(i*100)/allWords.size());
				int tokensOfTerm = DocumentUtils.countTokensOfTerm(allWordsInClass, word);
				HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
				classTerm.put(ic, word);
				double prob = calcProbability(tokensOfTerm, word);
				probability.put(classTerm, prob);
			}
		}
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
			score = Math.log(classes.get(ic));
			for (String word : wordsInDocument) {
				HashMap<DocumentClass, String> wordInDoc = new HashMap<DocumentClass, String>();
				wordInDoc.put(ic, word);
				score += Math.log(probability.get(wordInDoc));
			}
		}
		return score;
	}
	
}
