package by.softeq.borys.counter;

import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import by.softeq.borys.entity.Page;
import by.softeq.borys.handler.PageHandlerImpl;

public class TotalWordsCounter {
	private Map<String, Integer> wordsToCount;
	private static final int MAX_DEPTH_LEVEL = 8;
	private static final int VISITED_PAGES_LIMIT = 10_000;
	private Set<String> checkedPages = new HashSet<String>();
	private int depthLevel = 0;
	private int visitedPages = 0;

	public TotalWordsCounter(Map<String, Integer> map) {
		this.wordsToCount = map;

	}

	public Set<String> countPageWords(String urlString, Set<String> resultSet) {
		try {
			if (!checkedPages.contains(urlString)) {
			depthLevel++;
			visitedPages++;
			URL url = new URL(urlString);
			Page page = new Page(url, this.wordsToCount);
			PageHandlerImpl handler = new PageHandlerImpl();
			handler.handlePage(page);
			Deque<String> pageURLs = page.getAnoterUrlsDeque();
			if (pageURLs == null) {
				depthLevel--;
				return resultSet;
			}
			resultSet.add(page.toString());
			System.out.println(depthLevel);//TODO delete
			System.out.println(visitedPages);//TODO delete
			System.out.println(page.toString());//TODO delete
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

}
