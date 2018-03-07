package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Code {
	public static Element getElement(Document doc)
	{
		Element element = doc.createElement("code");

		element.setAttribute("code", "10");
		element.setAttribute("codeSystem", "1.2.392.200119.6.1001");
		
		return element;
	}
}
