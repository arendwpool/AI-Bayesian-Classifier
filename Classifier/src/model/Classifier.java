package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Classifier {
	private ArrayList<String> filepaths;
	private ArrayList<String> allWords = DocumentUtils.extractVocabulary(filepaths);
	private int numberOfDocuments = filepaths.size();
	private HashMap<DocumentClass, Double> classes = new HashMap<DocumentClass, Double>();
	private HashMap<HashMap<DocumentClass, String>, Double> probability = new HashMap<HashMap<DocumentClass, String>, Double>();
	
	public void TrainMultinomialNaiveBayes(DocumentClass[] c, ArrayList<String> d) throws IOException {
		for(DocumentClass ic : c) {
			int n = DocumentUtils.countDocsInClass(d, ic);
			double classPrior = n/numberOfDocuments;
			classes.put(ic,  classPrior);
			ArrayList<String> allWordsInClass = DocumentUtils.concatenateAllTextsOfDocsInClass(filepaths, ic);
			for(String word : allWords) {
				int tokensOfTerm = DocumentUtils.countTokensOfTerm(allWordsInClass, word);
				for(String word2 : allWords) {
					HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
					classTerm.put(ic, word2);
					double prob = calcProbability(tokensOfTerm, word2);
					probability.put(classTerm, prob);
				}
			}
		}
	}

	private double calcProbability(int tokensOfTerm, String word) {
		double numerator = tokensOfTerm + 1;
		double denominator = allWords.size();
		return numerator/denominator;
	}
	
	public double ApplyMultinomialNaiveBayes(FilteredDocument d) throws IOException {
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
