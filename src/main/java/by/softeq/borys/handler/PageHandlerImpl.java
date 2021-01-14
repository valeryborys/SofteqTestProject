package by.softeq.borys.handler;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import by.softeq.borys.entity.Page;

import by.softeq.borys.parser.PageParserImpl;
import by.softeq.borys.parser.Parser;

public class PageHandlerImpl implements PageHandler {
	private static Parser parser = PageParserImpl.parser;

	public void handlePage(Page page) throws IOException {
		try {
			String sourceCode = parser.getSourceCode(page.getUrl());
			sourceCode = deleteWasteTags(sourceCode);
			page.setSourceCode(sourceCode);
			page.setAnotherURLs(parser.matchURLs(page));
			String pageContent = parser.getPageText(page.getSourceCode());
			page.setPageContent(pageContent);
			page.fillDeque();
			page.setWords(parser.countGivenWords(page.getWords(), page.getPageContent()));
			page.setTotal();
		} catch (XMLStreamException e) {
			throw new IOException(e);
		}
	}

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
