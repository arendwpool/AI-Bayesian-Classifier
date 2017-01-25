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
		String path2 = "txt\\blogs\\test\\M\\M-test3.txt";
		ArrayList<String> words = DocumentUtils.readDocument(path);
		ArrayList<String> words2 = DocumentUtils.readDocument(path2);
		FilteredDocument d = DocumentUtils.toFilteredDocument(words, path, class1);
		FilteredDocument d2 = DocumentUtils.toFilteredDocument(words2, path2, class2);
		String path3 = "txt\\blogs\\test\\F\\F-test2.txt";
		String path4 = "txt\\blogs\\test\\M\\M-test7.txt";
		ArrayList<String> words3 = DocumentUtils.readDocument(path3);
		ArrayList<String> words4 = DocumentUtils.readDocument(path4);
		FilteredDocument d3 = DocumentUtils.toFilteredDocument(words3, path3, class1);
		FilteredDocument d4 = DocumentUtils.toFilteredDocument(words4, path4, class2);
		String path5 = "txt\\blogs\\test\\F\\F-test4.txt";
		String path6 = "txt\\blogs\\test\\M\\M-test8.txt";
		ArrayList<String> words5 = DocumentUtils.readDocument(path5);
		ArrayList<String> words6 = DocumentUtils.readDocument(path6);
		FilteredDocument d5 = DocumentUtils.toFilteredDocument(words5, path5, class1);
		FilteredDocument d6 = DocumentUtils.toFilteredDocument(words6, path6, class2);
		String path7 = "txt\\blogs\\test\\F\\F-test5.txt";
		String path8 = "txt\\blogs\\test\\M\\M-test9.txt";
		ArrayList<String> words7 = DocumentUtils.readDocument(path7);
		ArrayList<String> words8 = DocumentUtils.readDocument(path8);
		FilteredDocument d7 = DocumentUtils.toFilteredDocument(words7, path7, class1);
		FilteredDocument d8 = DocumentUtils.toFilteredDocument(words8, path8, class2);
		String path9 = "txt\\blogs\\test\\F\\F-test6.txt";
		String path10 = "txt\\blogs\\test\\M\\M-test11.txt";
		ArrayList<String> words9 = DocumentUtils.readDocument(path9);
		ArrayList<String> words10 = DocumentUtils.readDocument(path10);
		FilteredDocument d9 = DocumentUtils.toFilteredDocument(words9, path9, class1);
		FilteredDocument d10 = DocumentUtils.toFilteredDocument(words10, path10, class2);
		TrainMultinomialNaiveBayes(classes, DocumentUtils.loadDocuments("txt/blogs/test"));
		System.out.println(ApplyMultinomialNaiveBayes(d));
		System.out.println(ApplyMultinomialNaiveBayes(d2));
		System.out.println(ApplyMultinomialNaiveBayes(d3));
		System.out.println(ApplyMultinomialNaiveBayes(d4));
		System.out.println(ApplyMultinomialNaiveBayes(d5));
		System.out.println(ApplyMultinomialNaiveBayes(d6));
		System.out.println(ApplyMultinomialNaiveBayes(d7));
		System.out.println(ApplyMultinomialNaiveBayes(d8));
		System.out.println(ApplyMultinomialNaiveBayes(d9));
		System.out.println(ApplyMultinomialNaiveBayes(d10));
		
	}
	
	public static void TrainMultinomialNaiveBayes(DocumentClass[] c, ArrayList<String> d) throws IOException {
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(d);
		allWords = DocumentUtils.extractVocabulary(docs);
		numberOfDocuments = docs.size();
		int i = 0;
		for(DocumentClass ic : c) {
			int n = DocumentUtils.countDocsInClass(docs, ic);
			double classPrior = (double) n/numberOfDocuments;
			classes.put(ic,  classPrior);
			ArrayList<String> allWordsInClass = DocumentUtils.concatenateAllTextsOfDocsInClass(docs, ic);
			for(String word : allWords) {
				i++;
				System.out.println((double)(i*50)/allWords.size());
				HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
				classTerm.put(ic, word);
				double prob = calcProbability(word, ic, docs);
				probability.put(classTerm, prob);
			}
		}
		//writeToDoc();
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

	private static double calcProbability(String word, DocumentClass c, ArrayList<FilteredDocument> d) throws IOException {
		ArrayList<String> allWordsInClass = DocumentUtils.concatenateAllTextsOfDocsInClass(d, c);
		int tokensOfTerm = DocumentUtils.countTokensOfTerm(allWordsInClass, word);
		double numerator = tokensOfTerm + 1;
		double denominator = DocumentUtils.concatenateAllTextsOfDocsInClass(d, c).size();
		return numerator/denominator;
	}
	
	public static double ApplyMultinomialNaiveBayes(FilteredDocument d) throws IOException {
		ArrayList<String> wordsInDocument = DocumentUtils.readDocument(d.getPath());
		double score = 0;
		for (DocumentClass ic : classes.keySet()) {
			score += (double) Math.log(classes.get(ic));
			for (String word : wordsInDocument) {
				HashMap<DocumentClass, String> wordInDoc = new HashMap<DocumentClass, String>();
				wordInDoc.put(ic, word);
				score += (double) Math.log(probability.get(wordInDoc));
			}
		}
		return score;
	}
	
}
