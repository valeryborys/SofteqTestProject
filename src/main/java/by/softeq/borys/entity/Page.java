package by.softeq.borys.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Page {
	private URL url;
	private String sourceCode;
	private Map<String, Integer> words;
	private Set<String> anotherURLs;
	private String pageContent;

	public Page(URL url, String[] wordsArray) {
		this.url = url;
		this.words = new HashMap<String, Integer>();
		for (String s : wordsArray) {
			words.put(s, Integer.valueOf(0));
		}
	}

	public URL getUrl() {
		return url;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Map<String, Integer> getWords() {
		return words;
	}

	public void setWords(Map<String, Integer> words) {
		this.words = words;
	}

	public Set<String> getAnotherURLs() {
		return anotherURLs;
	}

	public void setAnotherURLs(Set<String> anotherURLs) {
		this.anotherURLs = anotherURLs;
	}

	
	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(url.getProtocol()+"://"+url.getHost()+url.getPath());
		sb.append(": ");
		String delimiter = "";
		for (Entry<String, Integer> entry : words.entrySet()) {
			sb.append(delimiter);
			sb.append(entry.getKey() + "=" + entry.getValue());
			delimiter=", ";
		}
		return sb.toString();
	}

}
