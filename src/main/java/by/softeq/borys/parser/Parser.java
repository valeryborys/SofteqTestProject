package by.softeq.borys.parser;

import java.io.IOException;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import by.softeq.borys.entity.Page;

/**
 * Interface declares methods for object responsible for page parsing.
 * 
 * @author Valery Borys
 * @version 1.0
 */

public interface Parser {
	
	/**
	 * Returns page source code
	 * 
	 * @param {@link URL} object of {@link Page} to get source code
	 * @return {@link String} of HTML source code
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	
	String getSourceCode(URL url) throws IOException, XMLStreamException;
	
	/**
	 * Parse {@link Page}, find all another URL's, and returns it's {@link Set}
	 * @param source {@link Page} 
	 * @return {@link Set} of all the {@link Page}  URL's found
	 * @throws XMLStreamException
	 */

	Set<String> findAllURLs(Page page) throws XMLStreamException;

	/**
	 * Method delete all information from open tag to close tag including content.
	 * @param source code
	 * @param startTagName
	 * @param endTagName
	 * @return
	 */
	
	String deleteTagsWithContent(String sourceCode, String startTagName, String endTagName);
	
	/**
	 * Returns clean page content without any HTML tags or attributes.
	 * 
	 * @param {@link String} of HTML source code
	 * @return {@link String} of cleaned from HTML page content
	 * @throws XMLStreamException
	 */
	
	String getPageText(String sourceCode) throws XMLStreamException;
	
	/**
	 * Method provides page content parsing, looking for words matches and word counter increasing.
	 * @param {@link Map} containing words as a key and empty counter values
	 * @param {@link String} of cleaned from HTML page content
	 * @return {@link Map} containing words as a key and counted values
	 */

	Map<String, Integer> countGivenWords(Map<String, Integer> words, String pageContent);
}
