package by.softeq.borys.counter;

import java.util.Map;
import java.util.Set;

/**
 * Interface declares methods for managing words usages statistics collection.
 * and {@link PageHandler} service.
 * @author Valery Borys
 * @version 1.0
 */
public interface CountManager {
	/**
	 * Method check out all the pages and collect given words usages statistics. 
	 * @param String URL to match words.
	 * @param Set containing global statistic information.
	 * @return {@link Set} containing global statistic information.
	 */
	Set<String> countPageWords(String urlString, Set<String> resultSet);

	/**
	 * @return {@link Map} statistics information sorted by total usages.
	 */
	Map<Integer, String> getTopUsages();
}