package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import model.Classifier;
import model.DocumentClass;
import model.DocumentUtils;
import model.FilteredDocument;

public class TestAll {

//	@Test
//	public void FilterSplitTextTest() throws IOException {
//		String toFilter = "Yesterday, the:\\*// Friends, we@n$t home()";
//		ArrayList<String> result = new ArrayList<String>();
//		result.add("yesterday");
//		result.add("friends");
//		result.add("went");
//		result.add("home");
//		ArrayList<String> result2 = DocumentUtils.FilterSplitText(toFilter);
//		for (String wor : result2)
//		System.out.println(wor);
//		assertTrue(result2.equals(result));
//	}
//	
//	@Test
//	public void classCheckerTest() {
//		DocumentClass c = new DocumentClass("F");
//		ArrayList<String> words = new ArrayList<String>();
//		words.add("yesterday");
//		words.add("friends");
//		words.add("went");
//		words.add("home");
//		FilteredDocument d = new FilteredDocument(words, "txt/blogs/F/doc.txt", new DocumentClass("F"));
//		FilteredDocument d2 = new FilteredDocument(words, "txt/blogs/M/doc.txt", new DocumentClass("M"));
//		assertFalse(DocumentUtils.classChecker(d2, c));
//		assertTrue(DocumentUtils.classChecker(d, c));
//		
//	}
//	
//	@Test
//	public void countDocsInClassTest() throws IOException {
//		DocumentClass c = new DocumentClass("F");
//		ArrayList<FilteredDocument> d = DocumentUtils.createFilteredDocuments(DocumentUtils.loadDocuments("txt/blogs/train"));
//		assertEquals(300, DocumentUtils.countDocsInClass(d, c));
//	}
//	
//	@Test
//	public void countTokensOfTermTest() {
//		ArrayList<String> words = new ArrayList<String>();
//		words.add("yesterday");
//		words.add("friends");
//		words.add("went");
//		words.add("home");
//		words.add("yesterday");
//		words.add("friends");
//		words.add("went");
//		words.add("home");
//		words.add("yesterday");
//		words.add("friends");
//		words.add("went");
//		words.add("home");
//		words.add("home");
//		words.add("home");
//		words.add("home");
//		words.add("home");
//		assertEquals(7, DocumentUtils.countTokensOfTerm(words, "home"));
//	}
//	
//	@Test
//	public void extractVocabularyTest() throws IOException{
//		ArrayList<FilteredDocument> paths = DocumentUtils.createFilteredDocuments(DocumentUtils.loadDocuments("txt/train"
//				+ ""));
//		ArrayList<String> allwords = DocumentUtils.extractVocabulary(paths);
//		System.out.println("Er zijn " + allwords.size() + " woorden");
//	}
//	
//	@Test
//	public void createFilteredDocumentsTest() throws IOException{
//		ArrayList<String> paths = DocumentUtils.loadDocuments("txt/blogs/train");
//		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(paths);
//		for(FilteredDocument d : docs) {
//			System.out.println(d.getPath() + ": " + d.getWords().size() + " words");
//		}
//	}
	
	@Test
	public void classifierTest() throws IOException{
		ArrayList<FilteredDocument> docs = DocumentUtils.createFilteredDocuments(DocumentUtils.loadDocuments("txt/corpus-mails/test"));
		int i = 0;
		DocumentClass spam = new DocumentClass("Spam");
		DocumentClass ham = new DocumentClass("Ham");
		DocumentClass[] c = { spam, ham } ;
		for (FilteredDocument d : docs) {
			String guessed = Classifier.ApplyMultinomialNaiveBayes(d, c);
			if (guessed.equals(d.getDocumentlass().getName())) {
				i++;
			}
		}
		System.out.println((double) (i*100)/ docs.size());
	}

}
