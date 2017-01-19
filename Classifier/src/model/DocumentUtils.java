package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentUtils {
	private static File document;
	private static FileReader documentReader;
	private static BufferedReader bf;
	
	public static ArrayList<String> readDocument(String filePath) throws IOException {
		document = new File(filePath);
		documentReader = new FileReader(document);
		bf = new BufferedReader(documentReader);
		String line;
		String totalText = null;
		while((line = bf.readLine()) != null) {
			totalText += line;
		}
		SplitText(totalText);
		return new ArrayList<String>();
	}

	public static void SplitText(String totalText) {
		totalText = totalText.replaceAll("[,.\\\\/\\[\\]\".,'{};:<>?!@#$%^&()-=+_~`€¤]", "");
		String[] words = totalText.split(" ");
		System.out.println(totalText);
		
	}
	
	public static void main(String[] args) throws IOException {
		String filepath = "txt/blogs/F/F-test2.txt";
		readDocument(filepath);
	}
	
	public static void NormalizeText() {
		
	}
	
	public static void RemoveStopWords(){
		
	}
}
