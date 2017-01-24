package tests;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import model.DocumentClass;
import model.DocumentUtils;
import model.FilteredDocument;

public class TestAll {

	@Test
	public void FilterSplitTextTest() throws IOException {
		String toFilter = "Yesterday, the:\\*// Friends, we@n$t home()";
		ArrayList<String> result = new ArrayList<String>();
		result.add("yesterday");
		result.add("friends");
		result.add("went");
		result.add("home");
		ArrayList<String> result2 = DocumentUtils.FilterSplitText(toFilter);
		assertTrue(result2.equals(result));
	}
	
	@Test
	public void classCheckerTest() {
		DocumentClass c = new DocumentClass("F");
		ArrayList<String> words = new ArrayList<String>();
		words.add("yesterday");
		words.add("friends");
		words.add("went");
		words.add("home");
		FilteredDocument d = new FilteredDocument(words, "txt/blogs/F/doc.txt");
		FilteredDocument d2 = new FilteredDocument(words, "txt/blogs/M/doc.txt");
		assertFalse(DocumentUtils.classChecker(d2.getPath(), c));
		assertTrue(DocumentUtils.classChecker(d.getPath(), c));
		
	}
	
	@Test
	public void countDocsInClassTest() throws IOException {
		DocumentClass c = new DocumentClass("F");
		DocumentClass c2 = new DocumentClass("M");
		ArrayList<String> d = DocumentUtils.loadDocuments("txt/blogs");
		assertEquals(325, DocumentUtils.countDocsInClass(d, c));
	}
	
	@Test
	public void countTokensOfTermTest() {
		ArrayList<String> words = new ArrayList<String>();
		words.add("yesterday");
		words.add("friends");
		words.add("went");
		words.add("home");
		words.add("yesterday");
		words.add("friends");
		words.add("went");
		words.add("home");
		words.add("yesterday");
		words.add("friends");
		words.add("went");
		words.add("home");
		words.add("home");
		words.add("home");
		words.add("home");
		words.add("home");
		assertEquals(7, DocumentUtils.countTokensOfTerm(words, "home"));
	}
	
	@Test
	public void extractVocabularyTest() throws IOException{
		ArrayList<String> paths = DocumentUtils.loadDocuments("txt/blogs");
		ArrayList<String> allwords = DocumentUtils.extractVocabulary(paths);
		for (String word : allwords)
		System.out.println(word);
	}
	
	@Test
	public void createFilteredDocumentsTest() throws IOException{
		ArrayList<String> paths = DocumentUtils.loadDocuments("txt/blogs/F");
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(paths);
		for(FilteredDocument d : docs) {
			System.out.println(d.getPath() + ": " + d.getWords().size() + " words");
		}
	}

}
