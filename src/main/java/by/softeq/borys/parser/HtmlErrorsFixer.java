package by.softeq.borys.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 
 * @author Valery Borys This class is provided for checking out and fixing HTML
 *         errors of the parsing pages.
 */

public class HtmlErrorsFixer {
	public static final HtmlErrorsFixer fixer = new HtmlErrorsFixer();
	private static final XMLInputFactory INPUT_FACTORY = XMLInputFactory.newInstance();
	private static final XMLOutputFactory OUTPUT_FACTORY = XMLOutputFactory.newInstance();
	private static final String TAGSOUP_PARSER_CLASS = "org.ccil.cowan.tagsoup.Parser";
	private static XMLReader xmlReader;
	private ByteArrayOutputStream output;

	public ByteArrayOutputStream getOutput() {
		return output;
	}

	private void setOutput(ByteArrayOutputStream out) {
		this.output = out;
	}

	public XMLStreamReader getFixedHtmlReader(String sourceCode) {
		XMLStreamReader reader = null;
		output = new ByteArrayOutputStream();
		try {
			xmlReader = XMLReaderFactory.createXMLReader(TAGSOUP_PARSER_CLASS);
			Source input = new SAXSource(xmlReader, new InputSource(new ByteArrayInputStream(sourceCode.getBytes())));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			XMLStreamWriter xmlStreamWriter = OUTPUT_FACTORY.createXMLStreamWriter(output);
			StAXResult staxResult = new StAXResult(xmlStreamWriter);
			transformer.transform(input, staxResult);
			setOutput(output);
			InputStream inputStream = new ByteArrayInputStream(output.toByteArray());
			reader = INPUT_FACTORY.createXMLStreamReader(inputStream);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return reader;
	}
}
