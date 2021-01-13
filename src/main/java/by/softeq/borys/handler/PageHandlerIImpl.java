package by.softeq.borys.handler;

import java.net.URL;
import java.util.Set;

import javax.xml.stream.XMLStreamReader;

import by.softeq.borys.entity.Page;
import by.softeq.borys.parser.HtmlErrorsFixer;
import by.softeq.borys.parser.PageParserImpl;
import by.softeq.borys.parser.Parser;

public class PageHandlerIImpl implements PageHandler{
	private static Parser parser = PageParserImpl.parser;
	private static final String SCRIPT_TAG_NAME = "script";
	private static final String STYLE_TAG_NAME = "style";
	private static HtmlErrorsFixer fixer = HtmlErrorsFixer.fixer;
	private XMLStreamReader htmlReader;
	
	public void handlePage(Page page) {
			String sourceCode = handleSourceCode(page.getUrl());
			htmlReader = fixer.getFixedHtmlReader(sourceCode);
			page.setSourceCode(fixer.getOutput().toString());
			page.setAnotherURLs(parser.matchURLs(page, htmlReader));
			htmlReader = fixer.getFixedHtmlReader(sourceCode);
			String pageContent = parser.getPageText(page.getSourceCode(), htmlReader);
			page.setPageContent(pageContent);
			page.setWords(parser.countGivenWords(page.getWords(), page.getPageContent()));
	}
	
	private String handleSourceCode(URL url) {
		String sourceCode = parser.getSourceCode(url);
		sourceCode = parser.deleteTagsWithContent(sourceCode,  SCRIPT_TAG_NAME);
		sourceCode = parser.deleteTagsWithContent(sourceCode,  STYLE_TAG_NAME);
		return sourceCode;
	}
}
