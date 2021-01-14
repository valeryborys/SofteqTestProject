package by.softeq.borys.parser;


import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import by.softeq.borys.entity.Page;


public interface Parser {
	String getSourceCode(URL url) throws IOException, XMLStreamException;
	Set<String> matchURLs(Page page);
	String deleteTagsWithContent(String sourceCode, String tagName);
	String getPageText(String sourceCode);
	Map<String, Integer> countGivenWords(Map<String, Integer> words, String pageContent);
}
