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
	private static final String CHARSET_TAG = "charset=\"";
	private Pattern pattern;
	private Matcher matcher;

	public PageParserImpl() {

	}

	public String getPageText(String sourceCode, XMLStreamReader reader) {
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

	public String getSourceCode(URL url) {
		String sourceCode = getCode(url, "");
		pattern = Pattern.compile(CHARSET_TAG);
		matcher = pattern.matcher(sourceCode);
		matcher.find();
		int end = matcher.end();
		StringBuilder charset = new StringBuilder();
		char c = '\0';
		while ((c = sourceCode.charAt(end)) != '"') {
			charset.append(c);
			end++;
		}
		String encodedCode = getCode(url, charset.toString());
		return encodedCode.toString();
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

	public Set<String> matchURLs(Page page, XMLStreamReader reader) {
		Set<String> urlSet = new HashSet<String>();
		int type = 0;
		try {
			while (reader.hasNext()) {
				HtmlTagName element = null;
				type = reader.next();
				if (type == XMLStreamReader.START_ELEMENT) {
					element = HtmlTagName.getElementTagName(reader.getLocalName());
					if (element.equals(HtmlTagName.A)) {
						String attributeValue = reader.getAttributeValue(null, "href");
						if (attributeValue == null) {
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

	private String getCode(URL url, String charset) {
		StringBuilder code = new StringBuilder();
		String string;
		BufferedReader in;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code.toString();

	}

//	public void tagParce() throws IOException, SAXException, TransformerException {
//	StringBuilder sb = new StringBuilder();
//	XMLStreamReader reader = null;
//	String attributeValue;
//	try {
//		reader = HtmlErrorsFixer.getFixedHtmlReader(url);
//		while (reader.hasNext()) {
//			HtmlTagName element = null;
//			int type = reader.next();
//			switch (type) {
//			case XMLStreamReader.START_ELEMENT:
//				element = HtmlTagName.getElementTagName(reader.getLocalName());
//				switch (element) {
//				case META:
//					attributeValue = reader.getAttributeValue(null, "charset");
//					System.out.println(attributeValue);
//					break;
//				case A:
//					attributeValue = reader.getAttributeValue(null, "href");
//					System.out.println(attributeValue);
//				}
//				break;
//			case XMLStreamReader.CHARACTERS:
//				String text = reader.getText().trim();
//				if (!text.isEmpty()) {
//					sb.append(text);
//					sb.append(" ");
//				}
//				break;
//			// default:
//			// System.out.println("ERROR");//TODO fix
//			}
//		}
//		System.out.println(sb);
//	} catch (XMLStreamException e) {
//		e.printStackTrace();
//	} finally {
//		if (reader != null)
//			try {
//				reader.close();
//			} catch (XMLStreamException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	}
//}
}