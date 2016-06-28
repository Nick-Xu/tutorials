package com.baeldung.xml;

import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Dom4JParserTest {

	private static final String FILE_NAME = "src/test/resources/example.xml";

	private Dom4JParser parser;

	@Test
	public void getRootElementTest() {
		parser = new Dom4JParser(new File(FILE_NAME));
		Element root = parser.getRootElement();

		assertNotNull(root);
		assertTrue(root.elements().size() == 4);
	}

	@Test
	public void getFirstElementListTest() {
		parser = new Dom4JParser(new File(FILE_NAME));
		List<Element> firstList = parser.getFirstElementList();

		assertNotNull(firstList);
		assertTrue(firstList.size() == 4);
		assertTrue(firstList.get(0).attributeValue("type").equals("java"));
	}

	@Test
	public void getElementByIdTest() {
		parser = new Dom4JParser(new File(FILE_NAME));
		Node element = parser.getNodeById("03");

		String type = element.valueOf("@type");
		assertEquals("android", type);
	}

	@Test
	public void getElementsListByTitleTest() {
		parser = new Dom4JParser(new File(FILE_NAME));
		Node element = parser.getElementsListByTitle("XML");

		assertEquals("java", element.valueOf("@type"));
		assertEquals("02", element.valueOf("@tutId"));
		assertEquals("XML", element.selectSingleNode("title").getText());
		assertEquals("title", element.selectSingleNode("title").getName());
	}

	@Test
	public void generateModifiedDocumentTest() {
		parser = new Dom4JParser(new File(FILE_NAME));
		parser.generateModifiedDocument();

		File generatedFile = new File("src/test/resources/example_updated.xml");
		assertTrue(generatedFile.exists());

		parser.setFile(generatedFile);
		Node element = parser.getNodeById("02");

		assertEquals("XML updated", element.selectSingleNode("title").getText());

	}
	
	@Test
	public void generateNewDocumentTest() {
		parser = new Dom4JParser(new File(FILE_NAME));
		parser.generateNewDocument();

		File newFile = new File("src/test/resources/example_new.xml");
		assertTrue(newFile.exists());

		parser.setFile(newFile);
		Node element = parser.getNodeById("01");

		assertEquals("XML with Dom4J", element.selectSingleNode("title").getText());

	}
}