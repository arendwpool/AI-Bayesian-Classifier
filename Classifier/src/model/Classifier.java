package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import view.GUI;

public class Classifier {
	private static ArrayList<String> filepaths;
	private static ArrayList<String> allWords = new ArrayList<String>();
	private static int numberOfDocuments;
	private static HashMap<DocumentClass, Double> classes = new HashMap<DocumentClass, Double>();
	private static HashMap<HashMap<DocumentClass, String>, Double> probability = new HashMap<HashMap<DocumentClass, String>, Double>();
        private static GUI gui;
	
	public static void main(String[] args) throws IOException {
				
		DocumentClass class1 = new DocumentClass("Ham");
		DocumentClass class2 = new DocumentClass("Spam");
		DocumentClass[] classes = {class1, class2};
		//TrainMultinomialNaiveBayes(classes, DocumentUtils.loadDocuments("txt/corpus-mails/train"), 300);
		
	}
	
        public static void setGUI(GUI guiTL) {
            gui = guiTL;
        }
	public static void TrainMultinomialNaiveBayes(DocumentClass[] c, ArrayList<String> d, int trim, GUI gui) throws IOException {
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(d);
<<<<<<< HEAD
        String corpus = d.get(1).split("\\\\")[d.get(1).split("\\\\").length-4];
        writePath(docs.get(1));
        setGUI(gui);
        long start = System.currentTimeMillis();
=======
                setGUI(gui);
                long start = System.currentTimeMillis();
>>>>>>> parent of 6f4cc7a... Fixt de shit
		allWords = DocumentUtils.finalVocabulary(d, trim, gui);
		numberOfDocuments = docs.size();
		int i = 0;
		for(DocumentClass ic : c) {
			int n = DocumentUtils.countDocsInClass(docs, ic);
			double classPrior = (double) n/numberOfDocuments;
			classes.put(ic,  classPrior);
			ArrayList<String> allWordsInClass = DocumentUtils.concatenateAllTextsOfDocsInClass(docs, ic);
			for(String word : allWords) {
				i++;
				gui.setTrainProgress((i*50)/allWords.size());

<<<<<<< HEAD
				calculate(ic, docs, word, corpus, false);
			}
		}
=======
				calculate(ic, docs, word);
			}
		}
		writeToDoc();
>>>>>>> parent of 6f4cc7a... Fixt de shit
                long end = System.currentTimeMillis();
                gui.setTrainingReady((double) (end-start));
	}
	
<<<<<<< HEAD
	public static void calculate(DocumentClass ic,ArrayList<FilteredDocument> docs, String w, String corpus, boolean fromIL ) throws IOException{
			probability = new HashMap<HashMap<DocumentClass, String>, Double>();
            HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
            classTerm.put(ic, w);
            double prob = calcProbability(w, ic, docs, getVocFromFile(corpus));
            probability.put(classTerm, prob);
            writeToDoc(corpus, fromIL);
		
	}
        private static void writePath(FilteredDocument d) throws FileNotFoundException {
            String corpus = d.getPath().split("\\\\")[d.getPath().split("\\\\").length-4];
            PrintWriter w = new PrintWriter(corpus + ".txt");
            for (int i = 0; i < d.getPath().split("\\\\").length-3 ; i++) {
            	if (i == d.getPath().split("\\\\").length-4)
                	w.write(d.getPath().split("\\\\")[i]);
            	else
                	w.write(d.getPath().split("\\\\")[i]+"\\");
            		
            }
            w.println();
            w.close();
        }
    
	public static void writeToDoc(String corpus, boolean fromIL) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(corpus+"probs.txt", "UTF-8");
		PrintWriter writer2 = new PrintWriter(corpus+"prior.txt", "UTF-8");
		PrintWriter writer3 = new PrintWriter(corpus+"voc.txt", "UTF-8");
=======
	public static void calculate(DocumentClass ic,ArrayList<FilteredDocument> docs, String w ) throws IOException{
			HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
			classTerm.put(ic, w);
			double prob = calcProbability(w, ic, docs);
			probability.put(classTerm, prob);
	writeToDoc();
		
	}

	private static void writeToDoc() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("probs.txt", "UTF-8");
		PrintWriter writer2 = new PrintWriter("prior.txt", "UTF-8");
		PrintWriter writer3 = new PrintWriter("voc.txt", "UTF-8");
>>>>>>> parent of 6f4cc7a... Fixt de shit
		for (HashMap<DocumentClass, String> c : probability.keySet()) {
			for (DocumentClass c1 : c.keySet()) {
				writer.write(c.get(c1) + " : " + c1.getName() + " : " + probability.get(c)+"\n");
			}
		}
		writer.close();
<<<<<<< HEAD
		if (fromIL == false) {
			for (DocumentClass c : classes.keySet()) {
				writer2.write(c.getName() + " : " + classes.get(c)+System.lineSeparator());
			}
			writer2.close();
			for (String w :  allWords) {
				writer3.write(w+System.lineSeparator());
			}
=======
		for (DocumentClass c : classes.keySet()) {
			writer2.write(c.getName() + " : " + classes.get(c)+"\n");
		}
		writer2.close();
		for (String w :  allWords) {
			writer3.write(w+"\n");
>>>>>>> parent of 6f4cc7a... Fixt de shit
		}
		writer3.close();
	    writer.println();
	    writer2.println();
	    writer3.println();
		
	}

	private static double calcProbability(String word, DocumentClass c, ArrayList<FilteredDocument> d,  ArrayList<String> allWords) throws IOException {
		int numerator = DocumentUtils.countOccurancesInClass(word, d, c) + 2;
		int denominator = 2;
		for (String w : allWords) {
			denominator += DocumentUtils.countOccurancesInClass(w, d, c);
		}
		return (double) numerator/denominator;
	}
	
	public static String ApplyMultinomialNaiveBayes(FilteredDocument d, DocumentClass[] c) throws IOException {
<<<<<<< HEAD
                String corpus = d.getPath().split("\\\\")[d.getPath().split("\\\\").length-4];
		HashMap<HashMap<DocumentClass, String>, Double> probs = getProbsFromFile(corpus);
		ArrayList<String> voc =getVocFromFile(corpus);
		HashMap<DocumentClass, Double> prior =getPriorFromFile(corpus);
		ArrayList<String> wordsInDocument = DocumentUtils.readDocument(d.getPath(), false);
=======
		getProbsFromFile();
		getVocFromFile();
		getPriorFromFile();
		ArrayList<String> wordsInDocument = DocumentUtils.readDocument(d.getPath());
>>>>>>> parent of 6f4cc7a... Fixt de shit
		ArrayList<String> wordsInDocInVoc = new ArrayList<String>();
		for (String w : wordsInDocument) {
			for (String w2 : allWords) {
				if (w.equals(w2))
					wordsInDocInVoc.add(w);
			}
		}
		String highesClass = "";
		double highScore = 0;
		for(DocumentClass dc : c) {
			double score = (double) -Math.log(getPrior(dc));
			for (String word : wordsInDocInVoc) {
				double prob = getProbability(dc, word);
				score += -Math.log(prob);
			}
			if (score < highScore || highScore == 0) {
				highScore = score;
				highesClass = dc.getName();
			}
		}
		return highesClass;
	}
	
	public static double getProbability(DocumentClass c, String w) throws IOException {
		for (HashMap<DocumentClass, String> hd : probability.keySet()) {
			for (DocumentClass dc : hd.keySet()) {
				if (dc.getName().equals(c.getName())) {
					if (hd.get(dc).equals(w)) {
						return probability.get(hd);
					}
				}
			}
		}
		return 0;
	}
	
	public static double getPrior(DocumentClass c) {
			for (DocumentClass dc : classes.keySet()) {
				if (dc.getName().equals(c.getName())) {
						return classes.get(dc);
			}
		}
		return 0;
	}
	
	public static void getProbsFromFile() throws IOException {
		probability.clear();
		File document = new File("probs.txt");
		FileReader documentReader = new FileReader(document);
		BufferedReader bf = new BufferedReader(documentReader);
		String line;
		String probs = "";
		while ((line = bf.readLine()) != null) {
			probs += line+ "\n";
		}
		String[] probss = probs.split("\n");
		for (String p : probss) {
			String[] a = p.split(" : ");
			HashMap<DocumentClass, String> dw = new HashMap<DocumentClass, String>();
			DocumentClass c = new DocumentClass(a[1]);
			dw.put(c, a[0]);
			double pv = Double.parseDouble(a[2]);
			probability.put(dw, pv);
		}
	}
	public static void getPriorFromFile() throws IOException{
		classes.clear();
		File document = new File("prior.txt");
		FileReader documentReader = new FileReader(document);
		BufferedReader bf = new BufferedReader(documentReader);
		String line;
		String prior = "";
		while ((line = bf.readLine()) != null) {
			prior += line + "\n";
		}
		String[] priorr = prior.split("\n");
		for (String p : priorr) {
			String[] a = p.split(" : ");
			double pr = Double.parseDouble(a[1]);
			DocumentClass c = new DocumentClass(a[0]);
			classes.put(c, pr);
		}
	}
	public static void getVocFromFile() throws IOException{
		if (allWords != null)
			allWords.clear();
		File document = new File("voc.txt");
		FileReader documentReader = new FileReader(document);
		BufferedReader bf = new BufferedReader(documentReader);
		String line;
		String voc = "";
		while ((line = bf.readLine()) != null) {
			voc += line+"\n";
		}
		String[] vocs = voc.split("\n");
		for (String w : vocs) {
			allWords.add(w);
		}
	}
}
