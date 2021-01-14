package by.softeq.borys.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import by.softeq.borys.entity.Page;

public interface Parser {
	String getSourceCode(URL url) throws IOException, XMLStreamException;

	Set<String> matchURLs(Page page) throws XMLStreamException;

	String deleteTagsWithContent(String sourceCode, String startTagName, String endTagName);

	String getPageText(String sourceCode) throws XMLStreamException;

	Map<String, Integer> countGivenWords(Map<String, Integer> words, String pageContent);
}
