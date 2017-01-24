package tests;

import static org.junit.Assert.*;

import java.io.IOException;
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
		FilteredDocument d = new FilteredDocument(words, "txt/blogs/F");
		FilteredDocument d2 = new FilteredDocument(words, "txt/blogs/M");
		assertFalse(DocumentUtils.classChecker(d2.getPath(), c));
		assertTrue(DocumentUtils.classChecker(d.getPath(), c));
		
	}

}
