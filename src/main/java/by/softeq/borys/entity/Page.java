package by.softeq.borys.entity;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * JavaBean domain object that represents regular web-page.
 * @author Valery Borys
 * @version 1.0
 */
public class Page implements Serializable {

	private static final long serialVersionUID = -57473167692038719L;

	private URL url;
	private String sourceCode;
	private Map<String, Integer> words;
	private Set<String> anotherURLs;
	private transient Deque<String> anoterUrlsDeque;
	private String pageContent;
	private Integer total;

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
		this.anoterUrlsDeque = urls;

	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal() {
		Integer total = 0;
		for (Entry<String, Integer> entry : words.entrySet()) {
			total += entry.getValue();
		}
		this.total = total;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(url.getProtocol() + "://" + url.getHost() + url.getPath() + ", ");
		String delimiter = "";
		for (Entry<String, Integer> entry : words.entrySet()) {
			sb.append(delimiter);
			sb.append(entry.getKey() + "=" + entry.getValue());
			delimiter = ", ";
		}
		return sb.toString();
	}

	public String getClarification() {
		StringBuilder sb = new StringBuilder();
		sb.append(url.getProtocol() + "://" + url.getHost() + url.getPath() + "\n");
		// String delimiter = "";
		for (Entry<String, Integer> entry : words.entrySet()) {
			sb.append(entry.getKey() + " - " + entry.getValue() + " hits\n");
		}
		sb.append("Total - " + total + " hits");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anotherURLs == null) ? 0 : anotherURLs.hashCode());
		result = prime * result + ((pageContent == null) ? 0 : pageContent.hashCode());
		result = prime * result + ((sourceCode == null) ? 0 : sourceCode.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((words == null) ? 0 : words.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
			}
		if (obj == null) {
			return false;
			}
		if (getClass() != obj.getClass()) {
			return false;
			}
		Page other = (Page) obj;
		if (anotherURLs == null) {
			if (other.anotherURLs != null) {
				return false;
				}
		} else if (!anotherURLs.equals(other.anotherURLs)) {
			return false;
			}
		if (pageContent == null) {
			if (other.pageContent != null) {
				return false;
				}
		} else if (!pageContent.equals(other.pageContent)) {
			return false;
			}
		if (sourceCode == null) {
			if (other.sourceCode != null) {
				return false;
				}
		} else if (!sourceCode.equals(other.sourceCode)) {
			return false;
			}
		if (total == null) {
			if (other.total != null) {
				return false;
				}
		} else if (!total.equals(other.total)) {
			return false;
			}
		if (url == null) {
			if (other.url != null) {
				return false;
				}
		} else if (!url.equals(other.url)) {
			return false;
			}
		if (words == null) {
			if (other.words != null) {
				return false;
				}
		} else if (!words.equals(other.words)) {
			return false;
			}
		return true;
	}

}
