package com.justing.poem.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.justing.poem.bean.Poem;

/**
 * @des 解析古诗词的xml文件
 * @author justingboy
 * 
 */
public class ParseXmlPome {

	/** 　古诗词内容存放的位置 */
	private static final String POME_XML_PATH = "com/justing/poem/assets/poem.xml";

	/** 解析xml中的作者的标签 */
	public List<String> getAuthors() {
		ArrayList<String> authorList = new ArrayList<String>();
		NodeList localNodeList = parseXmlFirstNode();
		int count = localNodeList.getLength();
		for (int i = 0; i < count; i++) {
			String str = ((Element) localNodeList.item(i))
					.getElementsByTagName("auth").item(0).getTextContent();
			if (!authorList.contains(str))
				authorList.add(str);
		}
		return authorList;
	}

	/**
	 * 获取诗词的集合
	 * 包含 作者 、诗词名字、描述
	 * @return List<Poem>
	 */
	public List<Poem> getPomeList() {
		ArrayList<Poem> localArrayList = new ArrayList<Poem>();
		NodeList localNodeList = parseXmlFirstNode();
		int count = localNodeList.getLength();
		for (int i = 0; i < count; i++) {
			Element localElement = (Element) localNodeList.item(i);
			localArrayList.add(new Poem(localElement
					.getElementsByTagName("title").item(0).getTextContent(),
					localElement.getElementsByTagName("auth").item(0)
							.getTextContent(), localElement
							.getElementsByTagName("desc").item(0)
							.getTextContent()));
		}
		return localArrayList;
	}

	/**
	 * 获取作者诗词的集合
	 * @param authorName
	 * @return
	 */
	public List<Poem> getPomeListByAuthor(String authorName) {
		ArrayList<Poem> localArrayList = new ArrayList<Poem>();
		NodeList localNodeList = parseXmlFirstNode();
		int count = localNodeList.getLength();
			for (int i = 0; i < count; i++) {
				Element localElement = (Element) localNodeList.item(i);
				if (authorName.equals(localElement
						.getElementsByTagName("auth").item(0).getTextContent()))
					localArrayList.add(new Poem(localElement
							.getElementsByTagName("title").item(0)
							.getTextContent(), authorName, localElement
							.getElementsByTagName("desc").item(0)
							.getTextContent()));
			}
	
		return localArrayList;
	}
	/** 
	 * 根据诗词的名字得到
	 * @param title
	 * @return
	 */
	public List<Poem> getListByTitle(String title) {
		ArrayList<Poem> localArrayList = new ArrayList<Poem>();
		NodeList localNodeList = parseXmlFirstNode();
		int count = localNodeList.getLength();
			for (int i = 0; i < count; i++) {
				Element localElement = (Element) localNodeList.item(i);
				String str = localElement.getElementsByTagName("title").item(0)
						.getTextContent();
				if (str.indexOf(title) != -1)
					localArrayList.add(new Poem(str, localElement
							.getElementsByTagName("auth").item(0)
							.getTextContent(), localElement
							.getElementsByTagName("desc").item(0)
							.getTextContent()));
			}
		return localArrayList;
	}

	/**
	 *  获取所有诗词的名字
	 * @return
	 */
	public List<String> getOPomeTitlesList() {
		ArrayList<String> localArrayList = new ArrayList<String>();
		NodeList localNodeList = parseXmlFirstNode();
		int count = localNodeList.getLength();
			for (int j = 0;; j++) {
				if (j >= count)
					break;
				String str = ((Element) localNodeList.item(j))
						.getElementsByTagName("title").item(0).getTextContent();
				if (str.indexOf("·") == -1)
					break;
				str = str.substring(0, str.indexOf("·"));
				if (!localArrayList.contains(str))
					localArrayList.add(str);
			}
		return localArrayList;
	}

	/** 解析Pome中最外层标签 */
	private NodeList parseXmlFirstNode() {
		DocumentBuilderFactory docBuiFactory = DocumentBuilderFactory
				.newInstance();
		Document document = null;
		try {
			document = docBuiFactory.newDocumentBuilder().parse(
					getClass().getClassLoader().getResourceAsStream(
							POME_XML_PATH));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList localNodeList = document.getElementsByTagName("node");
		return localNodeList;
	}

}