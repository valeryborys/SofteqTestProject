package by.softeq.borys.handler;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import by.softeq.borys.entity.Page;

import by.softeq.borys.parser.PageParserImpl;
import by.softeq.borys.parser.Parser;

/**
 * Implementation of {@link PageHandler} interface. Object of this class
 * responsible for page processing and it's fields filling.
 * 
 * @author Valery Borys
 * @version 1.0
 * 
 */
public class PageHandlerImpl implements PageHandler {
	public static final PageHandlerImpl HANDLER = new PageHandlerImpl();
	private static Parser parser = PageParserImpl.parser;

	private PageHandlerImpl() {
	}

	/**
	 * Method handles page and fills all the empty fields of the Page JavaBean
	 * 
	 * @param Page object needed to process
	 */
	public void handlePage(Page page) throws IOException {
		try {
			String sourceCode = parser.getSourceCode(page.getUrl());
			sourceCode = deleteWasteTags(sourceCode);
			page.setSourceCode(sourceCode);
			page.setAnotherURLs(parser.findAllURLs(page));
			String pageContent = parser.getPageText(page.getSourceCode());
			page.setPageContent(pageContent);
			page.fillDeque();
			page.setWords(parser.countGivenWords(page.getWords(), page.getPageContent()));
			page.setTotal();
		} catch (XMLStreamException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Method delete waste information between open and close tag with content.
	 * between.
	 * 
	 * @param String source code of the page
	 * @return cleaned page source code
	 */
	private String deleteWasteTags(String sourceCode) {
		String[][] tagsToDelete = new String[][] { { "<head", "</head>" }, { "<script", "</script>" },
				{ "<style", "</style>" }, { "<style", ">" }, { "<meta", "</meta>" }, { "<meta", ">" },
				{ "<option", "</option>" }, { "<option", ">" } };
		for (int i = 0; i < tagsToDelete.length; i++) {
			sourceCode = parser.deleteTagsWithContent(sourceCode, tagsToDelete[i][0], tagsToDelete[i][1]);
		}

		return sourceCode;

	}

}
