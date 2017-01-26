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
	private static ArrayList<String> allWords;
	private static int numberOfDocuments;
	private static HashMap<DocumentClass, Double> classes;
	private static HashMap<HashMap<DocumentClass, String>, Double> probability;
        private static GUI gui;
	
        public static void setGUI(GUI guiTL) {
            gui = guiTL;
        }
	public static void TrainMultinomialNaiveBayes(DocumentClass[] c, ArrayList<String> d, int trim, GUI gui) throws IOException {
		allWords  = new ArrayList<String>();
		classes  = new HashMap<DocumentClass, Double>();
		probability = new HashMap<HashMap<DocumentClass, String>, Double>();
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(d);
        String corpus = d.get(1).split("\\\\")[d.get(1).split("\\\\").length-4];
        setGUI(gui);
        long start = System.currentTimeMillis();
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

				calculate(ic, docs, word, corpus);
			}
		}
		writeToDoc(corpus);
                long end = System.currentTimeMillis();
                gui.setTrainingReady((double) (end-start));
	}
	
	public static void calculate(DocumentClass ic,ArrayList<FilteredDocument> docs, String w, String corpus ) throws IOException{
            HashMap<DocumentClass, String> classTerm = new HashMap<DocumentClass, String>();
            classTerm.put(ic, w);
            double prob = calcProbability(w, ic, docs);
            probability.put(classTerm, prob);
            writeToDoc(corpus);
		
	}

	private static void writeToDoc(String corpus) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(corpus+"probs.txt", "UTF-8");
		PrintWriter writer2 = new PrintWriter(corpus+"prior.txt", "UTF-8");
		PrintWriter writer3 = new PrintWriter(corpus+"voc.txt", "UTF-8");
		for (HashMap<DocumentClass, String> c : probability.keySet()) {
			for (DocumentClass c1 : c.keySet()) {
				writer.write(c.get(c1) + " : " + c1.getName() + " : " + probability.get(c)+System.lineSeparator());
			}
		}
		writer.close();
		for (DocumentClass c : classes.keySet()) {
			writer2.write(c.getName() + " : " + classes.get(c)+System.lineSeparator());
		}
		writer2.close();
		for (String w :  allWords) {
			writer3.write(w+System.lineSeparator());
		}
		writer3.close();
	    writer.println();
	    writer2.println();
	    writer3.println();
		
	}

	private static double calcProbability(String word, DocumentClass c, ArrayList<FilteredDocument> d) throws IOException {
		int numerator = DocumentUtils.countOccurancesInClass(word, d, c) + 2;
		int denominator = 2;
		for (String w : allWords) {
			denominator += DocumentUtils.countOccurancesInClass(w, d, c);
		}
		return (double) numerator/denominator;
	}
	
	public static String ApplyMultinomialNaiveBayes(FilteredDocument d, DocumentClass[] c) throws IOException {
                String corpus = d.getPath().split("\\\\")[d.getPath().split("\\\\").length-4];
		HashMap<HashMap<DocumentClass, String>, Double> probs = getProbsFromFile(corpus);
		ArrayList<String> voc =getVocFromFile(corpus);
		HashMap<DocumentClass, Double> prior =getPriorFromFile(corpus);
		ArrayList<String> wordsInDocument = DocumentUtils.readDocument(d.getPath());
		ArrayList<String> wordsInDocInVoc = new ArrayList<String>();
		for (String w : wordsInDocument) {
			for (String w2 : voc) {
				if (w.equals(w2))
					wordsInDocInVoc.add(w);
			}
		}
		String highesClass = "";
		double highScore = 0;
		for(DocumentClass dc : c) {
			double score = (double) -Math.log(getPrior(prior, dc));
			for (String word : wordsInDocInVoc) {
				double prob = getProbability(probs, dc, word);
				score += -Math.log(prob);
			}
			if (score < highScore || highScore == 0) {
				highScore = score;
				highesClass = dc.getName();
			}
		}
		return highesClass;
	}
	
	public static double getProbability(HashMap<HashMap<DocumentClass, String>, Double> probability, DocumentClass c, String w) throws IOException {
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
	
	public static double getPrior(HashMap<DocumentClass, Double> prior, DocumentClass c) {
			for (DocumentClass dc : prior.keySet()) {
				if (dc.getName().equals(c.getName())) {
						return prior.get(dc);
			}
		}
		return 0;
	}
	
	public static HashMap<HashMap<DocumentClass, String>, Double> getProbsFromFile(String corpus) throws IOException {
		File document = new File(corpus+"probs.txt");
		FileReader documentReader = new FileReader(document);
		BufferedReader bf = new BufferedReader(documentReader);
		String line;
		String probs = "";
		HashMap<HashMap<DocumentClass, String>, Double> probability = new HashMap<HashMap<DocumentClass, String>, Double>();
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
                return probability;
	}
	public static HashMap<DocumentClass, Double> getPriorFromFile(String corpus) throws IOException{
		File document = new File(corpus+"prior.txt");
		FileReader documentReader = new FileReader(document);
		BufferedReader bf = new BufferedReader(documentReader);
		String line;
		HashMap<DocumentClass, Double> classes= new HashMap<DocumentClass, Double>();
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
                return classes;
	}
	public static ArrayList<String> getVocFromFile(String corpus) throws IOException{
		File document = new File(corpus+"voc.txt");
		FileReader documentReader = new FileReader(document);
		BufferedReader bf = new BufferedReader(documentReader);
		String line;
		String voc = "";
		ArrayList<String> allWords = new ArrayList<String>();
		while ((line = bf.readLine()) != null) {
			voc += line+"\n";
		}
		String[] vocs = voc.split("\n");
		for (String w : vocs) {
			allWords.add(w);
		}
                return allWords;
	}
}
