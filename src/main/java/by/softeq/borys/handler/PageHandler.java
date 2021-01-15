package by.softeq.borys.handler;

import java.io.IOException;

import by.softeq.borys.entity.Page;

/**
 * Interface declares methods for object responsible for page processing and
 * fields.
 * @author Valery Borys
 * @version 1.0
 */
public interface PageHandler {
	/**
	 * Method handles page and fills all the empty fields of the Page JavaBean
	 * @param Page object needed to process
	 * @throws IOException
	 */
	void handlePage(Page page) throws IOException;
}
