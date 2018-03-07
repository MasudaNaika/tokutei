package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import java.util.Calendar;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class DocumentationOf {
	public Element getElement(Document doc)
	{
		Element element = doc.createElement("documentationOf");

		Element element_service = doc.createElement("serviceEvent");

		// add s.inoue 2009/12/29
		element_service.appendChild(doc.createComment("åíêfé¿é{éûÇÃÉvÉçÉOÉâÉÄéÌï "));
		Element element_code = doc.createElement("code");
		element_code.setAttribute("code", "010");
		element_code.setAttribute("codeSystem", "1.2.392.200119.6.1002");
		element_code.setAttribute("displayName", "ì¡íËåíêf");
		element_service.appendChild(element_code);

		// add s.inoue 2009/12/29
		element_service.appendChild(doc.createComment("åíêfé¿é{îNåéì˙"));
		Element element_time = doc.createElement("effectiveTime");
		element_time.setAttribute("value", EffectiveTime);
		element_service.appendChild(element_time);

		Element element_performer = doc.createElement("performer");
		element_performer.setAttribute("typeCode", "PRF");

		Element element_assigned = doc.createElement("assignedEntity");

		Element element_id = doc.createElement("id");
		element_id.setAttribute("nullFlavor", "NI");
		element_assigned.appendChild(element_id);

		element_assigned.appendChild(representedOrganization.getElement(doc));

		element_performer.appendChild(element_assigned);

		element_service.appendChild(element_performer);
		element.appendChild(element_service);

		return element;
	}

	public boolean Check()
	{
		if(EffectiveTime == null || representedOrganization == null)
		{
			return false;
		}

		if(representedOrganization.Check() == false)
		{
			return false;
		}

		return true;
	}

	public void setEffectiveTime(Calendar Value)
	{
		EffectiveTime = Utility.GetTimeString(Value);
	}
	public void setEffectiveTime(String Value)
	{
		EffectiveTime = Value;
	}
	private String EffectiveTime = null;

	public void setRepresentedOrganization(RepresentedOrganization Value)
	{
		representedOrganization = Value;
	}
	private RepresentedOrganization representedOrganization = null;
}
