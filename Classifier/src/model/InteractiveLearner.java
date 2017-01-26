package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import view.GUI;

public class InteractiveLearner {
	
	public static void learn(String file, DocumentClass[] c, DocumentClass wishedClass, String corpusName, GUI gui) throws IOException {
		System.out.println("voor");
		String root = DocumentUtils.readDocument(corpusName+".txt", true).get(0);
		System.out.println("na");
		ArrayList<String> words = DocumentUtils.readDocument(file, false);
        ArrayList<String> allwords = Classifier.getVocFromFile(corpusName);
		File dir = new File("txt/Learner/"+wishedClass.getName());
		dir.mkdirs();
		PrintWriter writer = new PrintWriter("txt/Learner/"+ wishedClass.getName()+ "/" + System.currentTimeMillis() +".txt", "UTF-8");
		for (String w : words) {
			writer.write(w+System.lineSeparator());		
		}
		writer.close();
		writer.println();
		ArrayList<String> docss = DocumentUtils.loadDocuments(root);
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(docss);
		for (String path : DocumentUtils.loadDocuments("txt/Learner/")) {
			for (DocumentClass dc : c) {
				String[] split = path.split("\\\\");
				if (split[split.length-2].equals(dc.getName())) {
					if (!docs.contains(path)) {
						docss.add(path);
					}
				}
			}
		}
		for(DocumentClass dc: c){
                    int i = 0;
			for(String word : allwords){
				Classifier.calculate( dc, docs ,word, corpusName, true);
                                i++;
                                gui.setLearningProgress((i*100)/allwords.size());
                    }
		}
		
	}
}
