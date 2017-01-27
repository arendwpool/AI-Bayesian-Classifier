package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import view.GUI;

public class InteractiveLearner {
	public static void learn(String file, String corpus, DocumentClass[] c, DocumentClass wishedClass, GUI gui, boolean train, double k) throws IOException {
		ArrayList<String> words = DocumentUtils.readDocument(file);
		File dir = new File("txt/Learner/"+wishedClass.getName());
		dir.mkdirs();
		PrintWriter writer = new PrintWriter("txt/Learner/"+ wishedClass.getName()+ "/" + System.currentTimeMillis() +".txt", "UTF-8");
		for (String w : words) {
			writer.write(w+"\n");		
		}
		writer.close();
		writer.println();
                String root = Classifier.getRoot(corpus);
		ArrayList<String> docs = DocumentUtils.loadDocuments(root);
		for (String path : DocumentUtils.loadDocuments("txt/Learner/")) {
			for (DocumentClass dc : c) {
				String[] split = path.split("\\\\");
				if (split[split.length-2].equals(dc.getName())) {
					if (!docs.contains(path)) {
						docs.add(path);
					}
				}
			}
		}
                if (train == true)
		Classifier.TrainMultinomialNaiveBayes(c, docs, Classifier.getVocFromFile(corpus).size(), gui, k);
	}

    public static void deleteFiles() throws IOException {
        ArrayList<String> files = DocumentUtils.loadDocuments("txt/Learner");
        for (String path : files) {
            File file = new File(path);
            file.delete();
        }
    }
}
