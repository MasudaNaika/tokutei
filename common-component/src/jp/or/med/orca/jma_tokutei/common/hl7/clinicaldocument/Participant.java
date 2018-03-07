package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import java.util.Calendar;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Participant {
	public Element getElement(Document doc)
	{
		Element element = doc.createElement("participant");
		element.setAttribute("typeCode", "HLD");

		Element element_func = doc.createElement("functionCode");

		element_func.setAttribute("code", "1");
		element_func.setAttribute("codeSystem", "1.2.392.200119.6.208");

		element.appendChild(element_func);

		Element element_time = doc.createElement("time");

		if (Time != null && ! Time.isEmpty()) {
			// add s.inoue 2009/12/29
			element_time.appendChild(doc.createComment("ófŒ”—LŒøŠúŒÀ"));
			Element element_high = doc.createElement("high");
			element_high.setAttribute("value", Time);
			element_time.appendChild(element_high);
		}

		element.appendChild(element_time);

		Element element_associate = doc.createElement("associatedEntity");
		element_associate.setAttribute("classCode", "IDENT");

		Element element_id = doc.createElement("id");

		if (SeiriNumberExtension != null && ! SeiriNumberExtension.isEmpty()) {
			element_id.setAttribute("extension", SeiriNumberExtension);
		}

		element_id.setAttribute("root", SeiriNumberRoot);

		// add s.inoue 2009/12/29
		element_associate.appendChild(doc.createComment("ófŒ”®—”Ô†"));
		element_associate.appendChild(element_id);

		// add s.inoue 2009/12/29
		element_associate.appendChild(doc.createComment("ófŒ”‚ğ”­s‚µ‚½•ÛŒ¯Ò”Ô†"));
		Element element_scoping = doc.createElement("scopingOrganization");
		Element element_id2 = doc.createElement("id");
		element_id2.setAttribute("extension", HokenjyaNumberExtension);
		element_id2.setAttribute("root", "1.2.392.200119.6.101");
		element_scoping.appendChild(element_id2);
		element_associate.appendChild(element_scoping);

		element.appendChild(element_associate);

		return element;
	}

	public boolean Check()
	{
		if(
				/* Deleted 2008/04/03 áŒ ófŒ””Ô†‚Í•K{‚Å‚Í‚È‚¢B */
				/* --------------------------------------------------- */
//				SeiriNumberExtension == null ||
				/* --------------------------------------------------- */
				HokenjyaNumberExtension == null ||
				SeiriNumberRoot == null ||
				Time == null
		)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value ófŒ”®—”Ô†
	 */
	public void setSeiriNumber(long Value)
	{
		SeiriNumberExtension = Utility.FillZero(Value, 11);
	}
	public void setSeiriNumber(String Value)
	{
		SeiriNumberExtension = Value;
	}
	private String SeiriNumberExtension = null;

	/**
	 * @param Value •ÛŒ¯Ò”Ô†irecordTarget‚Ì•ÛŒ¯Ò”Ô†‚Æ“¯‚¶’l‚ğİ’è‚·‚éj
	 */
	public void setHokenjyaNumber(long Value)
	{
		String Temp = Utility.FillZero(Value, 8);

		HokenjyaNumberExtension = Temp;
		SeiriNumberRoot = "1.2.392.200119.6.209.1" + Temp;
	}
	public void setHokenjyaNumber(String Value)
	{
		HokenjyaNumberExtension = Value;
		SeiriNumberRoot = "1.2.392.200119.6.209.1" + Value;
	}
	private String HokenjyaNumberExtension = null;;
	private String SeiriNumberRoot = null;

	/**
	 * @param Value ófŒ”‚Ì—LŒøŠúŒÀ
	 */
	public void setTime(Calendar Value)
	{
		Time = Utility.GetTimeString(Value);
	}
	public void setTime(String Value)
	{
		Time = Value;
	}
	private String Time = null;

	public String getSeiriNumberExtension() {
		return SeiriNumberExtension;
	}
}
