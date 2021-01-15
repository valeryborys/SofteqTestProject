package by.softeq.borys.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.softeq.borys.entity.Page;
/**
 * Object of this class provides methods for page parsing.
 * @author Valery Borys
 *	@version 1.0
 */
public class PageParserImpl implements Parser {
	public static final PageParserImpl PARSER = new PageParserImpl();
	private static HtmlErrorsFixer fixer = HtmlErrorsFixer.fixer;

	private XMLStreamReader reader;
	private Pattern pattern;
	private Matcher matcher;

	/**
	 * Returns page source code
	 * @param {@link URL} object of {@link Page} to get source code
	 * @return {@link String} of HTML source code
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public String getSourceCode(URL url) throws IOException, XMLStreamException {
		String sourceCode = getCode(url, "");
		reader = fixer.getFixedHtmlReader(sourceCode);
		String pageCharset = getPageCharset(sourceCode, reader);
		sourceCode = getCode(url, pageCharset);
		reader.close();
		return sourceCode;
	}
	/**
	 * Parse {@link Page}, find all another URL's, and returns it's {@link Set}
	 * @param source {@link Page} 
	 * @return {@link Set} of all the {@link Page}  URL's found
	 * @throws XMLStreamException
	 */
	public Set<String> findAllURLs(Page page) throws XMLStreamException {
		reader = fixer.getFixedHtmlReader(page.getSourceCode());
		Set<String> urlSet = new HashSet<String>();
		int type = 0;
		while (reader.hasNext()) {
			type = reader.next();
			if (type == XMLStreamReader.START_ELEMENT) {
				if (reader.getLocalName().equals("a")) {
					String attributeValue = reader.getAttributeValue(null, "href");
					if (attributeValue == null || attributeValue.length() <= 1) {
						continue;
					}
					if (attributeValue.substring(0, 2).equals("//")) {
						attributeValue = page.getUrl().getProtocol() + ":" + attributeValue;
						urlSet.add(attributeValue);
					} else if (attributeValue.charAt(0) == '/' && Character.isLetter(attributeValue.charAt(1))) {
						attributeValue = page.getUrl().getProtocol() + "://" + page.getUrl().getHost() + attributeValue;
						urlSet.add(attributeValue);
					} else if (!Character.isLetter(attributeValue.charAt(0))) {
						continue;
					} else {
						urlSet.add(attributeValue);
					}
				}
			}
		}
		reader.close();
		return urlSet;
	}
	/**
	 * Method delete all information from open tag to close tag including content.
	 * @param source code
	 * @param startTagName
	 * @param endTagName
	 * @return
	 */
	public String deleteTagsWithContent(String sourceCode, String startTagName, String endTagName) {
		StringBuilder sb = new StringBuilder(sourceCode);
		pattern = Pattern.compile(startTagName, Pattern.CASE_INSENSITIVE);
		Matcher matcherOpenTag = pattern.matcher(sourceCode);
		if (matcherOpenTag.find()) {
			int startTag = matcherOpenTag.start();
			Pattern p = Pattern.compile(endTagName, Pattern.CASE_INSENSITIVE);
			Matcher matcherCloseTag = p.matcher(sourceCode);
			if (matcherCloseTag.find(startTag)) {
				int endTag = matcherCloseTag.end();
				sb.replace(startTag, endTag, "");
				sourceCode = deleteTagsWithContent(sb.toString(), startTagName, endTagName);
			}
		}
		return sourceCode;
	}
	/**
	 * Returns clean page content without any HTML tags or attributes.
	 * @param {@link String} of HTML source code
	 * @return {@link String} of cleaned from HTML page content
	 * @throws XMLStreamException
	 */
	public String getPageText(String sourceCode) throws XMLStreamException {
		reader = fixer.getFixedHtmlReader(sourceCode);
		StringBuilder sb = new StringBuilder();
		while (reader.hasNext()) {
			int type = reader.next();
			if (type == XMLStreamReader.CHARACTERS) {
				sb.append(reader.getText().trim()).append(" ");
			}
		}
		reader.close();
		return sb.toString();
	}
	/**
	 * Method provides page content parsing, looking for words matches and word counter increasing.
	 * @param {@link Map} containing words as a key and empty counter values
	 * @param {@link String} of cleaned from HTML page content
	 * @return {@link Map} containing words as a key and counted values
	 */
	public Map<String, Integer> countGivenWords(Map<String, Integer> words, String pageContent) {
		for (Entry<String, Integer> entry : words.entrySet()) {
			pattern = Pattern.compile(entry.getKey(), Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(pageContent);
			int count = 0;
			while (matcher.find()) {
				count++;
			}
			entry.setValue(count);
		}
		return words;
	}
	private String getPageCharset(String sourceCode, XMLStreamReader reader) throws XMLStreamException {
		String attributeValue = null;
		while (reader.hasNext()) {
			int type = reader.next();
			if (type == XMLStreamReader.START_ELEMENT) {
				if (reader.getLocalName().equals("meta")) {
					attributeValue = reader.getAttributeValue(null, "charset");
					if (attributeValue != null) {
						return attributeValue;
					}
				}
			}
		}
		reader.close();
		return "UTF-8";
	}
	private String getCode(URL url, String charset) throws IOException {
		StringBuilder code = new StringBuilder();
		String string;
		BufferedReader in = null;
		try {
			if (charset.isEmpty()) {
				in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			} else {
				in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
			}
			while ((string = in.readLine()) != null) {
				code.append(string);
				code.append("\n");
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return code.toString();

	}

}