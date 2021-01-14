package by.softeq.borys;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import by.softeq.borys.counter.TotalWordsCounter;

public class Main {

	public static void main(String[] args) {

		String[] wordsArray = { "Elon", "Musk", "Aerospace", "Mercedes" };
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : wordsArray) {
			map.put(s, Integer.valueOf(0));
		}
		// URL url = new URL("https://av.by");
		// URL url = new URL("https://en.wikipedia.org/wiki/Elon_Musk");
		// URL url = new URL("https://en.wikipedia.org/wiki/Aerospace");
		TotalWordsCounter counter = new TotalWordsCounter(map);

		Set<String> resultSet = counter.countPageWords("https://av.by", new HashSet<String>());
		for (String s : resultSet) {
			System.out.println(s);
		}

	}

}
