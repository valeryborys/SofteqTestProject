package by.softeq.borys.handler;

import java.io.IOException;


import javax.xml.stream.XMLStreamException;

import by.softeq.borys.entity.Page;

import by.softeq.borys.parser.PageParserImpl;
import by.softeq.borys.parser.Parser;

public class PageHandlerImpl implements PageHandler {
	private static Parser parser = PageParserImpl.parser;
	private static final String SCRIPT_TAG_NAME = "script";
	private static final String STYLE_TAG_NAME = "style";
	

	public void handlePage(Page page) throws IOException {
		try {
		String sourceCode = parser.getSourceCode(page.getUrl());
		sourceCode = parser.deleteTagsWithContent(sourceCode, SCRIPT_TAG_NAME);
		sourceCode = parser.deleteTagsWithContent(sourceCode, STYLE_TAG_NAME);
		page.setSourceCode(parser.getSourceCode(page.getUrl()));
		page.setAnotherURLs(parser.matchURLs(page));
		page.fillDeque();
		String pageContent = parser.getPageText(page.getSourceCode());
		page.setPageContent(pageContent);
		page.setWords(parser.countGivenWords(page.getWords(), page.getPageContent()));
		} catch (XMLStreamException e) {
			throw new IOException(e);
		}
	}


}
