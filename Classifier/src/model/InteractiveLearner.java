package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InteractiveLearner {
	
	public static void learn(String file, DocumentClass[] c, DocumentClass wishedClass) throws IOException {
		ArrayList<String> words = DocumentUtils.readDocument(file);
		File dir = new File("txt/Learner/"+wishedClass.getName());
		dir.mkdirs();
		PrintWriter writer = new PrintWriter("txt/Learner/"+ wishedClass.getName()+ "/" + System.currentTimeMillis() +".txt", "UTF-8");
		for (String w : words) {
			writer.write(w+"\n");		
		}
		writer.close();
		writer.println();
		ArrayList<String> docs = DocumentUtils.loadDocuments("txt/blogs/train");
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
		
	}
	public static void main(String[] args) throws IOException {
		DocumentClass a = new DocumentClass("F");
		DocumentClass b = new DocumentClass("M");
		DocumentClass[] sadrf = { a, b };
//		learn("txt/blogs/Train/M/M-train4.txt", sadrf, b, 200, false);
//		learn("txt/blogs/Train/M/M-train5.txt", sadrf, b, 200, false);
//		learn("txt/blogs/Train/M/M-train6.txt", sadrf, b, 200, false);
//		learn("txt/blogs/Train/M/M-train8.txt", sadrf, b, 200, true);
	}
}
