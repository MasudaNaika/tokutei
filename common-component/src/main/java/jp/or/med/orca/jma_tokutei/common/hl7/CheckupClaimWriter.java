package jp.or.med.orca.jma_tokutei.common.hl7;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.CheckupCard;
import jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.SubjectPerson;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;


public class CheckupClaimWriter {
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
		Document document = impl.createDocument("http://tokuteikenshin.jp/checkup/2007","checkupClaim",null);
		Element root = document.getDocumentElement();
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

		// edit s.inoue 2010/07/05
		// edit s.inoue 2009/12/09
		// edit s.inoue 2009/12/09 XSD相対パス回避
		// root.setAttribute("xsi:schemaLocation", "http://tokuteikenshin.jp/checkup/2007 ./XSD/cc08_V08.xsd");
		root.setAttribute("xsi:schemaLocation", "http://tokuteikenshin.jp/checkup/2007 ../XSD/cc08_V08.xsd");

		// add s.inoue 2010/01/18
		root.appendChild(document.createComment("受診情報"));
		// 要素の追加
		Element element_encounter = document.createElement("encounter");
		Element element_service = document.createElement("serviceEventType");

		// add s.inoue 2010/01/18
		element_encounter.appendChild(document.createComment("実施区分"));
		element_service.setAttribute("code", ServiceEventType);
		element_encounter.appendChild(element_service);
		root.appendChild(element_encounter);

		root.appendChild(document.createComment("受診者情報"));
		root.appendChild(subjectPerson.GetElement(document));
		root.appendChild(document.createComment("受診情報"));
		root.appendChild(chckupCard.GetElement(document));
		root.appendChild(document.createComment("決済情報"));
		root.appendChild(settlement.GetElement(document));

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
		Utility.checkXmlSchema(FileName,"./XSD/cc08_V08.xsd");
	}

	public boolean check()
	{
		if(
				chckupCard == null ||
				settlement == null ||
				subjectPerson == null ||
				ServiceEventType == null
		)
		{
			return false;
		}

		if(
				chckupCard.Check() == false ||
				settlement.Check() == false ||
				subjectPerson.Check() == false
		)
		{
			return false;
		}

		return true;
	}

	public void setCheckupCard(CheckupCard Card)
	{
		chckupCard = Card;
	}
	private CheckupCard chckupCard = null;

	public void setSettlement(jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.Settlement set)
	{
		settlement = set;
	}
	private jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.Settlement settlement = null;

	public void setSubjectPerson(SubjectPerson person)
	{
		subjectPerson = person;
	}
	private SubjectPerson subjectPerson = null;

	public void setServiceEventType(String Value)
	{
		ServiceEventType = Value;
	}
	private String ServiceEventType = null;
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

