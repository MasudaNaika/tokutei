package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Author {
	public static Element GetElement(Document doc, RepresentedOrganization author)
	{
		Element element = doc.createElement("author");

		// add s.inoue 2009/12/29
		element.appendChild(doc.createComment("ファイル作成年月日"));
		Element element_time = doc.createElement("time");
		element_time.setAttribute("value", Utility.NowDate());

		element.appendChild(element_time);

		Element element_auth = doc.createElement("assignedAuthor");

		Element element_id = doc.createElement("id");
		element_id.setAttribute("nullFlavor", "NI");
		element_auth.appendChild(element_id);
		element_auth.appendChild(author.getElement(doc));

		element.appendChild(element_auth);

		return element;
	}
}
