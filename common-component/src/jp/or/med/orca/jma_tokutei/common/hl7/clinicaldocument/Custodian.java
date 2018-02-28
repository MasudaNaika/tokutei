package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Custodian {
	public static Element getElement(Document doc)
	{
		Element element = doc.createElement("custodian");
		
		Element element_assigned = doc.createElement("assignedCustodian");
		Element element_represented = doc.createElement("representedCustodianOrganization");
		Element element_id = doc.createElement("id");
		element_id.setAttribute("nullFlavor", "NI");
		
		element_represented.appendChild(element_id);
		element_assigned.appendChild(element_represented);
		element.appendChild(element_assigned);
		
		return element;
	}
}