package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class RepresentedOrganization {
	public Element getElement(Document doc)
	{
		// 設定のチェック
		if(Check() == false)
		{
			return null;
		}

		Element element_representedOrg = doc.createElement("representedOrganization");

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("ファイル作成機関番号"));
		Element element_id2 = doc.createElement("id");
		element_id2.setAttribute("extension", NumberExtension);
		element_id2.setAttribute("root", "1.2.392.200119.6.102");
		element_representedOrg.appendChild(element_id2);

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("ファイル作成機関名称"));
		Element element_name = doc.createElement("name");
		element_name.setTextContent(Name);
		element_representedOrg.appendChild(element_name);

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("ファイル作成機関電話番号"));
		Element element_telecom = doc.createElement("telecom");
		element_telecom.setAttribute("value", Telecom);
		element_representedOrg.appendChild(element_telecom);

		Element element_addr = doc.createElement("addr");

		/* 空白文字を削除する。 */
		String address = Address.replaceAll("[ 　\\t]", "");
		element_addr.setTextContent(address);

		// add s.inoue 2009/12/29
		element_representedOrg.appendChild(doc.createComment("ファイル作成機関住所、郵便番号"));
		Element element_postalCode = doc.createElement("postalCode");
		element_postalCode.setTextContent(PostalCode);
		element_addr.appendChild(element_postalCode);

		element_representedOrg.appendChild(element_addr);

		return element_representedOrg;
	}

	public boolean Check()
	{
		if(
				NumberExtension == null ||
				Name == null ||
				Telecom == null ||
				Address == null ||
				PostalCode == null
		)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value 機関の番号
	 */
	public void setNumber(long Value)
	{
		NumberExtension = Utility.FillZero(Value, 10);
	}
	public void setNumber(String Value)
	{
		NumberExtension = Value;
	}
	private String NumberExtension = null;

	/**
	 * @param Value 機関の名称
	 */
	public void setName(String Value)
	{
		Name = Value;
	}
	private String Name = null;

	/**
	 * @param Value 機関の連絡先情報
	 */
	public void setTel(String Value)
	{
		Telecom = "tel:" + Value;
	}
	private String Telecom = null;

	/**
	 * @param Value 機関の所在地情報
	 */
	public void setAddress(String Value)
	{
		Address = Value;
	}
	private String Address = null;

	/**
	 * @param Value 機関の郵便番号情報
	 */
	public void setPostalCode(String Value)
	{
		Value = Utility.ConvertPostalCode(Value);

		PostalCode = Value;
	}
	private String PostalCode = null;
}
