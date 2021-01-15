package by.softeq.borys.counter;

import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import by.softeq.borys.entity.Page;
import by.softeq.borys.handler.PageHandler;
import by.softeq.borys.handler.PageHandlerImpl;

/**
 * Implementation of {@link CountManager} interface}
 * 
 * @author Valery Borys
 * @version 1.0
 * 
 */

public class CountManagerImpl implements CountManager {
	private Map<String, Integer> wordsToCount;
	private static final int MAX_DEPTH_LEVEL = 8;
	private static final int VISITED_PAGES_LIMIT = 10000;
	private static final int TOP_WORDS_USAGES_QUANTITY = 10;
	private Set<String> checkedPages = new HashSet<String>();
	private Map<Integer, String> topWordUsages = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
	private int depthLevel = 0;
	private int visitedPages = 0;

	public CountManagerImpl(Map<String, Integer> map) {
		this.wordsToCount = map;

	}

	/**
	 * Method check out all the pages and collect given words usages statistics.
	 * 
	 * @param {@link String} url to match words
	 * @param {@link Set} containing global statistic information
	 * @return {@link Set} containing global statistic information
	 */
	public Set<String> countPageWords(String urlString, Set<String> resultSet) {
		try {
			visitedPages++;
			System.out.printf("Total visited pages(%d/%d)%n", visitedPages, VISITED_PAGES_LIMIT);
			if (!checkedPages.contains(urlString)) {
				depthLevel++;
				URL url = new URL(urlString);
				Page page = new Page(url, this.wordsToCount);
				PageHandler handler = PageHandlerImpl.HANDLER;
				handler.handlePage(page);
				System.out.println(page);
				Deque<String> pageURLs = page.getAnoterUrlsDeque();
				if (pageURLs == null) {
					depthLevel--;
					return resultSet;
				}
				resultSet.add(page.toString());
				topWordUsages.put(page.getTotal(), page.getClarification());
				while (!pageURLs.isEmpty() && visitedPages < (VISITED_PAGES_LIMIT - 1)) {
					if (depthLevel < MAX_DEPTH_LEVEL) {
						String poll = pageURLs.pollFirst();
						page.setAnoterUrlsDeque(pageURLs);
						countPageWords(poll, resultSet);
					} else if (depthLevel == MAX_DEPTH_LEVEL) {
						depthLevel--;
						return resultSet;
					}
				}
				depthLevel--;
			}
			return resultSet;
		} catch (IOException e) {
			depthLevel--;
			return resultSet; // Skips link if cannot connect or have another input error
		}
	}

	/**
	 * 
	 * @return {@link Map} statistics information sorted by total usages.
	 */
	public Map<Integer, String> getTopUsages() {
		Map<Integer, String> topUsages = new TreeMap<Integer, String>((o1, o2) -> o2.compareTo(o1));
		int count = 0;
		for (Entry<Integer, String> entry : topWordUsages.entrySet()) {
			if (count < TOP_WORDS_USAGES_QUANTITY) {
				topUsages.put(entry.getKey(), entry.getValue());
				count++;
			} else {
				break;
			}
		}
		return topUsages;
	}
}
