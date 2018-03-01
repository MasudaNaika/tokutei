package jp.or.med.orca.jma_tokutei.common.hl7;
import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;


import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

import jp.or.med.orca.jma_tokutei.common.hl7.common.JBaseXmlWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.hl7.summary.Summary;

public class SummaryWriter implements JBaseXmlWriter {
	public void createXml(String FileName) throws Exception
	{
		if(check() == false)
		{
			throw new Exception();
		}
		DOMImplementation impl;
		Transformer transformer;
		
		// 初期化処理
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			impl = builder.getDOMImplementation();
		}
		catch(Exception e)
		{
			throw new RuntimeException();
		}
		
		// XML作成
		Document document = impl.createDocument("http://tokuteikenshin.jp/checkup/2007","summary",null);
		Element root = document.getDocumentElement();
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xsi:schemaLocation", "http://tokuteikenshin.jp/checkup/2007 ./XSD/su08_V08.xsd");
		
		// 要素の追加
		summary.getElement(document,root);
		
		// XML出力
		TransformerFactory transFactory = TransformerFactory.newInstance();
		try
		{
			transformer = transFactory.newTransformer();
		}
		catch(Exception e)
		{
			throw new RuntimeException();
		}
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		
		/* Added 2008/04/06 若月  */
		/* --------------------------------------------------- */
		/* インデントの文字数を指定する。 */
		transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");		
		/* --------------------------------------------------- */
				
		DOMSource source = new DOMSource(document);
		File Xml = new File(FileName);
		FileOutputStream stream = new FileOutputStream(Xml);
		StreamResult result = new StreamResult(stream);
		transformer.transform(source, result);

		stream.close();
		
		// XMLスキーマの検証
		Utility.checkXmlSchema(FileName,"./XSD/su08_V08.xsd"); 
	}
	
	public boolean check()
	{
		if(summary == null)
		{
			return false;
		}
		
		if(summary.Check() == false)
		{
			return false;
		}
		
		return true;
	}
	
	public void setSummary(Summary Value)
	{
		summary = Value;
	}
	private Summary summary = null;
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

