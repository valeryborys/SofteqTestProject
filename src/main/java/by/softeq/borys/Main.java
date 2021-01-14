package by.softeq.borys;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import by.softeq.borys.counter.CountManager;
import by.softeq.borys.entity.Page;

public class Main {

	public static void main(String[] args) {
		String[] wordsArray = { "при", "Musk", "is", "Mercedes", "Program" };
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : wordsArray) {
			map.put(s, Integer.valueOf(0));
		}
		// URL url = new URL("https://av.by");
		// URL url = new URL("https://en.wikipedia.org/wiki/Elon_Musk");
		// URL url = new URL("https://en.wikipedia.org/wiki/Aerospace");
		//https://habr.com/ru/
		CountManager counter = new CountManager(map);

		Set<String> resultSet = counter.countPageWords("https://habr.com/ru/", new HashSet<String>());
		serialize(resultSet);
//		for (String s : resultSet) {
//			System.out.println(s);
//		}
		Map<Integer, String> topWordUsages = counter.getTopUsages();
		counter.printTopToCSV(topWordUsages);
		for (Entry<Integer, String> entry:topWordUsages.entrySet()) {
			System.out.println(entry.getValue());
		}
	}


	private static void serialize(Set<String> resultSet) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D://csv's/serialize.csv")))
        {
            oos.writeObject(resultSet);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
	}

}
