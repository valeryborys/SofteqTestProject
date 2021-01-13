package by.softeq.borys.parser;


import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamReader;

import by.softeq.borys.entity.Page;


public interface Parser {
	String getSourceCode(URL url);
	Set<String> matchURLs(Page page, XMLStreamReader reader);
	String deleteTagsWithContent(String sourceCode, String tagName);
	String getPageText(String sourceCode, XMLStreamReader reader);
	Map<String, Integer> countGivenWords(Map<String, Integer> words, String pageContent);
	

}
