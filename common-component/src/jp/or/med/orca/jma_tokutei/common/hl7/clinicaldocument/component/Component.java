package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Component {
	public static Element getElement(Document doc, List<Observation> TokuteiKenshin, List<Observation> Other)
	{
		Element element = doc.createElement("component");

		Element element_struct = doc.createElement("structuredBody");

		Element element_component1 = doc.createElement("component");
		element_component1.appendChild(Section.getElement(doc, TokuteiKenshin, 0));
		element_struct.appendChild(element_component1);

		Element element_component2 = doc.createElement("component");
		element_component2.appendChild(Section.getElement(doc, Other, 1));
		element_struct.appendChild(element_component2);

		element.appendChild(element_struct);

		return element;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

