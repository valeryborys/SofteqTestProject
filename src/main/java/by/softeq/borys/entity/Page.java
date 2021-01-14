package by.softeq.borys.entity;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Page {
	private URL url;
	private String sourceCode;
	private Map<String, Integer> words;
	private Set<String> anotherURLs;
	private Deque<String> anoterUrlsDeque;
	private String pageContent;

	public Page(URL url, Map<String, Integer> wordsMap) {
		this.url = url;
		this.words = wordsMap;
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
	public Deque<String> getAnoterUrlsDeque() {
		return anoterUrlsDeque;
	}

	public void setAnoterUrlsDeque(Deque<String> anoterUrlsDeque) {
		this.anoterUrlsDeque = anoterUrlsDeque;
	}
	
	public void fillDeque() {
		Deque<String> urls = new ArrayDeque<String>(anotherURLs);
		this.anoterUrlsDeque=urls;
		
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
