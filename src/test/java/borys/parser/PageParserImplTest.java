package borys.parser;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import by.softeq.borys.parser.PageParserImpl;

public class PageParserImplTest {
	private PageParserImpl parser = new PageParserImpl();

	@Test
	public void getSourceCodeTest() throws XMLStreamException, IOException {
		URL url = new URL("http://bash.org/");
		StringBuilder sb = new StringBuilder();
		try (DataInputStream input = new DataInputStream(
				new BufferedInputStream(new FileInputStream(getDir(PageParserImplTest.class) + "sourceCode.txt")))) {
			InputStreamReader isr = new InputStreamReader(input, "UTF-8");
			int b = 0;
			while ((b = isr.read()) != -1) {
				sb.append((char) b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sourceCode = parser.getSourceCode(url);
		assertEquals(sb.toString().trim(), sourceCode.trim());

	}

	@Test
	public void deleteScriptsInSourceCodeTest() {
		parser = new PageParserImpl();
		StringBuilder sb = new StringBuilder();
		try (DataInputStream input = new DataInputStream(
				new BufferedInputStream(new FileInputStream(getDir(PageParserImplTest.class) + "scriptDelete.txt")))) {
			InputStreamReader isr = new InputStreamReader(input, "UTF-8");
			int b = 0;
			while ((b = isr.read()) != -1) {
				sb.append((char) b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String deleted = parser.deleteTagsWithContent(sb.toString(), "<script", "</script>");
		deleted = parser.deleteTagsWithContent(deleted, "<script ", ">");
		assertEquals(ConstantStringValues.SCRIPT_DELETION_EXPECTATION.trim(), deleted.trim());

	}

	@Test
	public void getPageTextTest() throws XMLStreamException {
		parser = new PageParserImpl();
		StringBuilder sb = new StringBuilder();
		try (DataInputStream input = new DataInputStream(
				new BufferedInputStream(new FileInputStream(getDir(PageParserImplTest.class) + "contentfind.txt")))) {
			InputStreamReader isr = new InputStreamReader(input, "UTF-8");
			int b = 0;
			while ((b = isr.read()) != -1) {
				sb.append((char) b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String pageText = parser.getPageText(sb.toString());
		assertEquals(ConstantStringValues.TEXT_SEPARATION_EXPECTATION, pageText);

	}

	@Test
	public void countGivenWordsTest() {
		Map<String, Integer> expect = new HashMap<String, Integer>() {
			{
				this.put("Java", 9);
				this.put("was", 3);
				this.put("OAK", 3);
				this.put("is", 11);
			}
		};

		Map<String, Integer> raw = new HashMap<String, Integer>() {
			{
				this.put("Java", 0);
				this.put("was", 0);
				this.put("OAK", 0);
				this.put("is", 0);
			}
		};

		raw = parser.countGivenWords(raw, ConstantStringValues.TEXT_FOR_WORDS_COUNT);
		assertEquals(expect, raw);
	}

	public static String getDir(Class<?> cl) {
		String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
				+ "java" + File.separator;
		String clD = cl.getName().replace(".", File.separator).replace(cl.getSimpleName(), "");
		clD = clD + "txt" + File.separator;
		return path + clD;
	}

}
