package by.softeq.borys.counter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import by.softeq.borys.entity.Page;
import by.softeq.borys.handler.PageHandlerImpl;

public class CountManager {
	private Map<String, Integer> wordsToCount;
	private static final int MAX_DEPTH_LEVEL = 8;
	private static final int VISITED_PAGES_LIMIT = 10000;// TODO 10000
	private static final int TOP_WORDS_USAGES_QUANTITY = 10;
	private Set<String> checkedPages = new HashSet<String>();
	private Map<Integer, String> topWordUsages = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
	private int depthLevel = 0;
	private int visitedPages = 0;

	public CountManager(Map<String, Integer> map) {
		this.wordsToCount = map;

	}

	public Set<String> countPageWords(String urlString, Set<String> resultSet) {
		try {
			visitedPages++;
			System.out.printf("Total visited pages(%d/%d)%n", visitedPages, VISITED_PAGES_LIMIT);
			if (!checkedPages.contains(urlString)) {
				depthLevel++;
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
	
	public Map<Integer,String> getTopUsages() {
		Map<Integer,String> topUsages = new TreeMap<Integer,String>((o1, o2) -> o2.compareTo(o1));
		int count = 0;
		for(Entry<Integer, String> entry:topWordUsages.entrySet()) {
			if (count<TOP_WORDS_USAGES_QUANTITY) {
				topUsages.put(entry.getKey(),entry.getValue());
				count++;
			}else {
				break;
			}
		}
		return topUsages;
	}
	
	public void printTopToCSV(Map<Integer,String> map) {
		int fileCounter=1;
		for(Entry<Integer, String> entry: map.entrySet()) {
			try (PrintWriter out = new PrintWriter(
					new OutputStreamWriter(
							new FileOutputStream("D://csv's/"+fileCounter+".csv"), "UTF-8"))) {
	            out.print(entry.getValue());
	            fileCounter++;
	        } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
