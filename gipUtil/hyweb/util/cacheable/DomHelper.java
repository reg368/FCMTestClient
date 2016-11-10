package hyweb.util.cacheable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.XSLTransformer;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DomHelper {
	static{
		System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
	}

	/**
	 * 回傳一個空的w3c document
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static org.w3c.dom.Document getEmptyDocument() throws ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		return parser.newDocument();
	}
	/**
	 * 依照外部設定可能會使用快取
	 * @param xmlUri 完整的XML本機路徑
	 * @return
	 */
	public static Document getDocument(String xmlUri) throws JDOMException, IOException{
		return DomHelper.newDocument(xmlUri);
	}

	/**
	 * 將輸入的string 轉換為 document
	 * @param xmlString
	 * @return
	 */
	public static Document trans2Document(String xmlString) throws JDOMException, IOException{
		return new SAXBuilder().build(new StringReader(xmlString));
	}
	
	/**
	 * 依照外部設定可能會使用快取
	 * @param xmlFile 完整的XML本機檔案
	 * @return
	 */
	public static Document getDocument(File xmlFile) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		return builder.build(xmlFile);
	}

	/**
	 * 將字串轉換為jdom2 document
	 * @param str
	 * @return
	 */
	public static Document getStringToDom(String str) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		return builder.build(new StringReader(str));
	}

	/**
	 * 不使用快取
	 * @param xmlUri
	 * @return
	 */
	public static Document newDocument(String xmlUri) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		if(xmlUri.startsWith("http")){
			return builder.build(new URL(xmlUri));
		}else{
			return builder.build(new File(xmlUri));
		}
	}

	/**
	 * 依照外部設定可能會使用快取
	 * @param xslUri
	 * @return
	 */
	public static XSLTransformer getXSLTransformer(String xslUri) throws JDOMException, IOException{
		return DomHelper.newXSLTransformer(xslUri);
	}

	/**
	 * 不使用快取
	 * @param xslUri
	 * @return
	 */
	public static XSLTransformer newXSLTransformer(String xslUri) throws JDOMException, IOException{
		return new XSLTransformer(DomHelper.newDocument(xslUri));		
	}

	public static void transformer(String xslUri, Document xml, Writer writer) throws TransformerException, JDOMException{
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer transformer = tfactory.newTemplates(new StreamSource(new File(xslUri))).newTransformer();
		transformer.transform(new DOMSource(new DOMOutputter().output(xml)), new StreamResult(writer));
	}

	/**
	 * 使用jaxp介面 進行xlst,預設會使用saxon
	 * @param xslUri
	 * @param xml
	 * @param os 
	 */
	public static void transformer(String xslUri, Document xml, OutputStream os) throws TransformerException, JDOMException, IOException{
		DomHelper.transformer(xslUri, xml, os, null);
	}
	
	/**
	 * 使用jaxp介面 進行xlst,預設會使用saxon
	 * @param xslUri
	 * @param xml
	 * @param os
	 * @param uri 
	 */
	public static void transformer(String xslUri, Document xml, OutputStream os, String uri) throws TransformerException, JDOMException, IOException{
		InputStream input = null;
		try {
			if(xslUri.startsWith("http")){
				input = new URL(xslUri).openStream();
			}else{
				input = new FileInputStream(new File(xslUri));
			}
			TransformerFactory tfactory = TransformerFactory.newInstance();
			if (uri != null) {
				tfactory.setURIResolver(new BasicURIResolver(uri));
			}
			Transformer transformer = tfactory.newTemplates(new StreamSource(input)).newTransformer();
			transformer.transform(new DOMSource(new DOMOutputter().output(xml)), new StreamResult(os));
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}
	
	/**
	 * 使用jaxp介面 進行xlst,系統預設會使用saxon,但最好在初始化就直接採用
	 * @param xslUri
	 * @param xml
	 * @param os 
	 */
	public static void transformer(Document xsl, Document xml, OutputStream os) throws TransformerException, JDOMException, IOException{
		transformer(xsl, xml, os, null);
	}
	
	/**
	 * 使用jaxp介面 進行xlst,系統預設會使用saxon,但最好在初始化就直接採用
	 * @param xslUri
	 * @param xml
	 * @param os 
	 */
	public static void transformer(Document xsl, Document xml, OutputStream os, String uri) throws TransformerException, JDOMException, IOException{
		TransformerFactory tfactory = TransformerFactory.newInstance();
		if (uri != null) {
			tfactory.setURIResolver(new BasicURIResolver(uri));
		}
		Transformer transformer = tfactory.newTemplates(new DOMSource(new DOMOutputter().output(xsl))).newTransformer();
		transformer.transform(new DOMSource(new DOMOutputter().output(xml)), new StreamResult(os));
	}

	/**
	 * 使用jaxp介面 進行xlst,預設會使用saxon
	 * @param xmlString 內容為xml格式的string
	 * @param xslUri
	 * @param os
	 * @param uri 
	 */
	public static void transformer(String xmlString, String xslUri, OutputStream os, String uri) throws TransformerException, JDOMException, IOException, ParserConfigurationException, SAXException{
		InputStream input = null;
		try {
			//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance("net.sf.saxon.dom.DocumentBuilderFactoryImpl", Thread.currentThread().getContextClassLoader());
			DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			org.w3c.dom.Document xmlDoc = builder.parse(is);
			if(xslUri.startsWith("http")){
				input = new URL(xslUri).openStream();
			}else{
				input = new FileInputStream(new File(xslUri));
			}
			TransformerFactory tfactory = TransformerFactory.newInstance();
			if (uri != null) {
				tfactory.setURIResolver(new BasicURIResolver(uri));
			}
			Transformer transformer = tfactory.newTemplates(new StreamSource(input)).newTransformer();
			transformer.transform(new DOMSource(xmlDoc), new StreamResult(os));
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	public static void w3cTransformer(String xslUri, org.w3c.dom.Document xmlDoc, OutputStream os, String uri) throws TransformerException, IOException{
		InputStream input = null;
		try {
			if(xslUri.startsWith("http")){
				input = new URL(xslUri).openStream();
			}else{
				input = new FileInputStream(new File(xslUri));
			}
			TransformerFactory tfactory = TransformerFactory.newInstance();
			if (uri != null) {
				tfactory.setURIResolver(new BasicURIResolver(uri));
			}
			Transformer transformer = tfactory.newTemplates(new StreamSource(input)).newTransformer();
			transformer.transform(new DOMSource(xmlDoc), new StreamResult(os));
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	/**
	 * 透過xpath取值
	 * @param elm
	 * @param xPath
	 * @return
	 */
	public static List<Element> getListByXpath(String xPath, Element elm){
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPath, Filters.element());
		return xpath.evaluate(elm);
	}

	public static Element getElementByXpath(String xPath, Element elm){
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPath, Filters.element());
		return xpath.evaluateFirst(elm);
	}

	public static String getStringByXpath(String xPath, Element elm){
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPath, Filters.element());
		Element ret = xpath.evaluateFirst(elm);
		if(ret == null){
			return "";
		}
		return ret.getText();
	}
	
	public static String getStringByXpath(String xPath, Document doc){
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPath, Filters.element());
		Element ret = xpath.evaluateFirst(doc);
		if(ret == null){
			return "";
		}
		return ret.getText();
	}

	/**
	 * 透過xpath取值
	 * @param elm
	 * @param xPath
	 * @return
	 */
	public static List<Element> getListByXpath(String xPath, Document doc){
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPath, Filters.element());
		return xpath.evaluate(doc);
	}

	public static Element getElementByXpath(String xPath, Document doc){
		XPathExpression<Element> xpath = XPathFactory.instance().compile(xPath, Filters.element());
		return xpath.evaluateFirst(doc);
	}

	/**
	 * 使用jdom2本身提供的輸出xml string 功能，但是xml會非常見的xml
	 * @param doc
	 * @param out
	 */
	public static void toHTMLWriter(Document doc, Writer out) throws IOException{
		Format format = Format.getPrettyFormat();
		format.setOmitDeclaration(true);
        format.setEncoding("UTF-8");
		XMLOutputter outp = new XMLOutputter(format);
		outp.output(doc, out);
	}

	/**
	 * 使用jdom2本身提供的輸出xml string 功能
	 * @param doc
	 * @param out
	 */
	public static void toHTMLWriter(Document doc, OutputStream out) throws IOException{
		Format format = Format.getPrettyFormat();
		format.setOmitDeclaration(true);
        format.setEncoding("UTF-8");
		XMLOutputter outp = new XMLOutputter(format);
		outp.output(doc, out);
	}

	public static void toXMLWriter(Document doc, Writer out) throws IOException{
		Format format = Format.getPrettyFormat();
		format.setOmitDeclaration(false);
        format.setEncoding("UTF-8");
		XMLOutputter outp = new XMLOutputter(format);
		outp.output(doc, out);
	}
	
	public static void writerFile(Document doc, File file) throws IOException{
		Format format = Format.getPrettyFormat();
		format.setOmitDeclaration(false);
        format.setEncoding("UTF-8");
        FileOutputStream outfile = null;
        try {
        	outfile = new FileOutputStream(file);
        	XMLOutputter outp = new XMLOutputter(format);
    		outp.output(doc, outfile);
        } finally {
        	if (outfile != null) {
        		outfile.close();
        	}
        }
	}

	public static void w3c2OS(org.w3c.dom.Document doc, OutputStream out) throws IOException, TransformerException{
		TransformerFactory tFactory =TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(out);
		transformer.transform(source, result); 
	}
}
class BasicURIResolver implements URIResolver{
	String uri;
	public BasicURIResolver(String uri){
		super();
	}

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		Source source = null;
		try {
			source = new StreamSource(new URL(this.uri + href).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return source;
	}
}