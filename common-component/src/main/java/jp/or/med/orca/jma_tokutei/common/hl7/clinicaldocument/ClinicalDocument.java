package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

//import jp.or.med.orca.jma_tokutei.common.hl7.ClinicalDocumentWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import com.lowagie.text.ElementTags;


public class ClinicalDocument {

	public static Element appendChild(Document doc,Element element)
	{
		// add s.inoue 2009/12/29
		element.appendChild(doc.createComment("CDAのデフォルト"));
		element.appendChild(Typeid.getElement(doc));

		Element element_id = doc.createElement("id");
		element_id.setAttribute("nullFlavor", "NI");
		element.appendChild(element_id);

		// add s.inoue 2009/12/29
		element.appendChild(doc.createComment("報告区分コード"));
		element.appendChild(Code.getElement(doc));

		// add s.inoue 2009/12/29
		element.appendChild(doc.createComment("文書発行日（西暦）"));
		Element element_time = doc.createElement("effectiveTime");
		element_time.setAttribute("value", Utility.NowDate());
		element.appendChild(element_time);

		Element element_code = doc.createElement("confidentialityCode");
		element_code.setAttribute("code", "N");
		element.appendChild(element_code);

		return element;
	}
}
