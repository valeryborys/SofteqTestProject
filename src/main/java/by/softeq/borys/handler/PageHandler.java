package by.softeq.borys.handler;

import java.io.IOException;

import by.softeq.borys.entity.Page;

/**
 * Interface declares methods for object responsible for page processing and
 * fields.
 * 
 * @author Valery Borys
 * @version 1.0
 */
public interface PageHandler {
	void handlePage(Page page) throws IOException;
}
