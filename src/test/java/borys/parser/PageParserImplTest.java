package borys.parser;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;

import org.junit.Test;

import by.softeq.borys.parser.HtmlErrorsFixer;
import by.softeq.borys.parser.PageParserImpl;

public class PageParserImplTest {
	private PageParserImpl parser = new PageParserImpl();

	@Test
	public void deleteScriptsInSourceCodeTest() {
		parser = new PageParserImpl();
		String deleted = parser.deleteTagsWithContent(ConstantStringValues.SCRIPT_DELETION_INPUT, "script");
		assertEquals(ConstantStringValues.SCRIPT_DELETION_EXPECTATION, deleted);
		
	}
	
	@Test
	public void getPageText() {
		XMLStreamReader reader = HtmlErrorsFixer.fixer.getFixedHtmlReader(ConstantStringValues.TEXT_SEPARATION_INPUT);
		String pageText = parser.getPageText(ConstantStringValues.TEXT_SEPARATION_INPUT, reader);
		assertEquals(ConstantStringValues.TEXT_SEPARATION_EXPECTATION, pageText);
		
	}
	
	@Test
	public void countGivenWordsTest() {
		Map<String, Integer> expect = new HashMap<String, Integer>(){{
			this.put("Java", 9);
			this.put("was", 3);
			this.put("OAK", 3);
			this.put("is", 11);
		}};
		
		Map<String, Integer> raw = new HashMap<String, Integer>(){{
			this.put("Java", 0);
			this.put("was", 0);
			this.put("OAK", 0);
			this.put("is", 0);
		}};
		
		raw = parser.countGivenWords(raw, ConstantStringValues.TEXT_FOR_WORDS_COUNT);
		assertEquals(expect, raw);
	}
	

}
