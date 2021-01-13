package by.softeq.borys;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import by.softeq.borys.entity.Page;
import by.softeq.borys.handler.PageHandler;
import by.softeq.borys.handler.PageHandlerIImpl;

public class ProgramInitializer {
	

	public static void main(String[] args) {

		try {
			String[] wordsArray = {"Elon", "Musk", "Aerospace", "Mercedes"};
			//URL url = new URL("https://av.by");
			URL url = new URL("https://en.wikipedia.org/wiki/Elon_Musk");
			//URL url = new URL("https://en.wikipedia.org/wiki/Aerospace");
			Page page = new Page(url, wordsArray);
			PageHandlerIImpl handler = new PageHandlerIImpl();
			handler.handlePage(page);
			System.out.println(page);
						
		} catch (MalformedURLException e) {
			//TODO fix
			e.printStackTrace();
		}
	

	}

}
