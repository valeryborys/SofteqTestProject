package by.softeq.borys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import by.softeq.borys.counter.CountManager;
import by.softeq.borys.counter.CountManagerImpl;

/**
 * Program initialization class.
 * @author Valery Borys
 * @version 1.0
 */

public class Main {
	private static String exportPath = "/home/valery/eclipse-workspace2/SofteqTestProject/export";

	/**
	 * Main method. Requests input information from user and initializes pages
	 * processing.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter start page URL:");
		String startPage = scanner.nextLine();
		System.out.println("Enter words separeted with comma to find matches (example \"One, to, play, a car \"):");
		String wordsInput = scanner.nextLine();
		System.out
				.println("Enter package path to export *.csv files(example \"D://export\" or \"/home/user/export\"):");
		String getExportPath = scanner.nextLine();
		scanner.close();
		Map<String, Integer> map = getMap(wordsInput);
		CountManager counter = new CountManagerImpl(map);
		if (!getExportPath.isEmpty()) {
			exportPath = getExportPath;
		}
		Set<String> resultSet = counter.countPageWords(startPage, new HashSet<String>());
		serialize(resultSet);
		printToFile(resultSet);
		Map<Integer, String> topWordUsages = counter.getTopUsages();
		printTopToCSV(topWordUsages);
		System.out.println();
		System.out.println("Top by total hits:");
		for (Entry<Integer, String> entry : topWordUsages.entrySet()) {
			System.out.println();
			System.out.println(entry.getValue());
		}
	}
	/**
	 * Method provides result statistics serialization. 
	 * @param {@link Set} containing global statistic information
	 */
	private static void serialize(Set<String> resultSet) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(exportPath + "/serialize.csv"))) {
			oos.writeObject(resultSet);
		} catch (IOException e) {
			System.err.println(
					"Statistic data serialization connot be provided: the export path given is not avaliable or output file is blocked by another process!");
		}

	}

	/**
	 * Method convert inputed String of words that user wants to find to the
	 * {@link Map}.
	 * @param String of the words list entered by the user
	 * @return {@link Map} of the Strings to parse as a key and {@link Integer} of
	 *         its matches("0" by default)
	 */
	private static Map<String, Integer> getMap(String wordsInput) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] wordsArray = wordsInput.split("[,]");
		for (String s : wordsArray) {
			s = s.trim();
			map.put(s, Integer.valueOf(0));
		}
		return map;
	}

	/**
	 * Method prints top pages by total usages to the *.csv separated files.
	 * @param map of the top usages statistics
	 */
	public static void printTopToCSV(Map<Integer, String> map) {
		int fileCounter = 1;
		for (Entry<Integer, String> entry : map.entrySet()) {
			try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(exportPath + File.separator + fileCounter + ".csv"), "UTF-8"))) {
				out.print(entry.getValue());
				fileCounter++;
			} catch (UnsupportedEncodingException e) {
				System.out.println("UnsupportedEncodingException. Check encode");
			} catch (FileNotFoundException e) {
				System.err.println(
						"Clarification of top usages connot be provided: the export path given is not avaliable or output file is blocked by another process!");
			}
		}
	}

	/**
	 * Method prints all statistics to *.csv file.
	 * @param map of the top usages statistics
	 */

	public static void printToFile(Set<String> set) {
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(exportPath + File.separator + "statistics.csv"), "UTF-8"))) {
			for (String s : set) {
				out.println(s);
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException. Check encode");
		} catch (FileNotFoundException e) {
			System.err.println(
					"Print of statistics connot be provided: the export path given is not avaliable or output file is blocked by another process!");
		}

	}

}
