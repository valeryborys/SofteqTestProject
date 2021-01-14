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

public class PageParserImpl implements Parser {
	public static final PageParserImpl parser = new PageParserImpl();
	private static HtmlErrorsFixer fixer = HtmlErrorsFixer.fixer;

	private XMLStreamReader reader;
	private Pattern pattern;
	private Matcher matcher;

	public String getPageText(String sourceCode) {
		reader = fixer.getFixedHtmlReader(sourceCode);
		StringBuilder sb = new StringBuilder();
		try {
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamReader.CHARACTERS) {
					sb.append(reader.getText().trim()).append(" ");
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (XMLStreamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return sb.toString();

	}

	public String getSourceCode(URL url) throws IOException, XMLStreamException {
		String sourceCode = getCode(url, "");
		reader = fixer.getFixedHtmlReader(sourceCode);
		String pageCharset = getPageCharset(sourceCode, reader);
		sourceCode = getCode(url, pageCharset);
		reader.close();
		return sourceCode;
	}

	public String getPageCharset(String sourceCode, XMLStreamReader reader) throws XMLStreamException {
		String attributeValue = null;
		while (reader.hasNext()) {
			int type = reader.next();
			if (type == XMLStreamReader.START_ELEMENT) {
				if (reader.getLocalName().equals("meta")) {
					attributeValue = reader.getAttributeValue(null, "charset");
					if (attributeValue == null) {
						continue;
					}
				}
			}
		}
		return (attributeValue == null) ? "utf-8" : attributeValue;
	}

	public Map<String, Integer> countGivenWords(Map<String, Integer> words, String pageContent) {
		Set<Entry<String, Integer>> entrySet = words.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
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

	public String deleteTagsWithContent(String sourceCode, String tagName) {// TODO replacing by simple matching without
																			// recursion
		String startTagName = "<" + tagName;
		String endTagName = "</" + tagName + ">";
		StringBuilder sb = new StringBuilder(sourceCode);
		pattern = Pattern.compile(startTagName);
		Matcher matcherOpenTag = pattern.matcher(sourceCode);
		if (matcherOpenTag.find()) {
			int startTag = matcherOpenTag.start();
			Pattern p = Pattern.compile(endTagName);
			Matcher matcherCloseTag = p.matcher(sourceCode);
			matcherCloseTag.find();
			int endTag = matcherCloseTag.end();
			sb.replace(startTag, endTag, "");
			sourceCode = deleteTagsWithContent(sb.toString(), tagName);
		}
		return sourceCode;
	}

	public Set<String> matchURLs(Page page) {
		reader = fixer.getFixedHtmlReader(page.getSourceCode());
		Set<String> urlSet = new HashSet<String>();
		int type = 0;
		try {
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
							attributeValue = page.getUrl().getProtocol() + "://" + page.getUrl().getHost()
									+ attributeValue;
							urlSet.add(attributeValue);
						} else if (!Character.isLetter(attributeValue.charAt(0))) {
							continue;
						} else {
							urlSet.add(attributeValue);
						}
					}
				}
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (XMLStreamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return urlSet;
	}

	private String getCode(URL url, String charset) throws IOException {
		StringBuilder code = new StringBuilder();
		String string;
		BufferedReader in = null;
		try {
			if (charset.isEmpty()) {
				in = new BufferedReader(new InputStreamReader(url.openStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
			}
			while ((string = in.readLine()) != null) {
				code.append(string);
				code.append("\n");
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return code.toString();

	}

}