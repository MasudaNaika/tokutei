package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.util.*;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

public class RecordTarget {
	public Element getElement(Document doc)
	{
		// 設定のチェック
		if(Check() == false)
		{
			return null;
		}

		Element element = doc.createElement("recordTarget");

		Element element_role = doc.createElement("patientRole");

		// add s.inoue 2009/12/29
		element_role.appendChild(doc.createComment("保険者番号"));
		Element element_id = doc.createElement("id");
		element_id.setAttribute("extension", HokenjyaNumberExtension);
		element_id.setAttribute("root", "1.2.392.200119.6.101");
		element_role.appendChild(element_id);

		if(HokensyoCodeExtension != null && ! HokensyoCodeExtension.isEmpty())
		{
			// add s.inoue 2009/12/29
			element_role.appendChild(doc.createComment("被保険者証等記号"));
			Element element_id2 = doc.createElement("id");
			element_id2.setAttribute("extension", HokensyoCodeExtension);
			element_id2.setAttribute("root", "1.2.392.200119.6.204");
			element_role.appendChild(element_id2);
		}

		// add s.inoue 2009/12/29
		element_role.appendChild(doc.createComment("被保険者証等番号"));
		Element element_id3 = doc.createElement("id");
		element_id3.setAttribute("extension", HokensyoNumberExtension);
		element_id3.setAttribute("root", "1.2.392.200119.6.205");
		element_role.appendChild(element_id3);

		Element element_addr = doc.createElement("addr");

		/* 空白文字を削除する。 */
		// add s.inoue 2009/12/29
		element_role.appendChild(doc.createComment("住所、郵便番号"));
		String address = Address.replaceAll("[ 　\\t]", "");
		element_addr.setTextContent(address);

		Element element_addr_postalCode = doc.createElement("postalCode");
		element_addr_postalCode.setTextContent(PostalCode);
		element_addr.appendChild(element_addr_postalCode);
		element_role.appendChild(element_addr);


		Element element_patient = doc.createElement("patient");
		Element element_patient_name = doc.createElement("name");

		/* 空白文字を削除する。 */
		// add s.inoue 2009/12/29
		element_patient.appendChild(doc.createComment("氏名"));
		String name = Name.replaceAll("[ 　\\t]", "");
		element_patient_name.setTextContent(name);
		element_patient.appendChild(element_patient_name);

		// add s.inoue 2009/12/29
		element_patient.appendChild(doc.createComment("性別"));
		Element element_patient_gender = doc.createElement("administrativeGenderCode");
		element_patient_gender.setAttribute("code", GenderCode);
		element_patient_gender.setAttribute("codeSystem", "1.2.392.200119.6.1104");
		element_patient.appendChild(element_patient_gender);

		// add s.inoue 2009/12/29
		element_patient.appendChild(doc.createComment("生年月日"));
		Element element_patient_birthTime = doc.createElement("birthTime");
		element_patient_birthTime.setAttribute("value", BirthTime);
		element_patient.appendChild(element_patient_birthTime);

		element_role.appendChild(element_patient);

		element.appendChild(element_role);

		return element;
	}

	/**
	 * @return すべての項目が設定されているかチェック
	 */
	public boolean Check()
	{
		if(HokenjyaNumberExtension == null)
		{
			return false;
		}

		if(HokensyoNumberExtension == null)
		{
			return false;
		}

		if(Address == null)
		{
			return false;
		}

		if(PostalCode == null)
		{
			return false;
		}

		if(Name == null)
		{
			return false;
		}

		if(GenderCode == null)
		{
			return false;
		}

		if(BirthTime == null)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value 保険者番号
	 */
	public void setHokenjyaNumber(long Value)
	{
		HokenjyaNumberExtension = Utility.FillZero(Value, 8);
	}
	public void setHokenjyaNumber(String Value)
	{
		HokenjyaNumberExtension = Value;
	}
	private String HokenjyaNumberExtension = null;

	/**
	 * @param Value 被保険者証等記号
	 */
	public void setHokensyoCode(String Value)
	{
		HokensyoCodeExtension = Value;
	}
	private String HokensyoCodeExtension = null;

	/**
	 * @param Value 被保険者証等番号
	 */
	public void setHokensyoNumber(String Value)
	{
		HokensyoNumberExtension = Value;
	}
	private String HokensyoNumberExtension = null;

	/**
	 * @param Value 受診者住所
	 */
	public void setAddress(String Value)
	{
		Address = Value;
	}
	private String Address = null;

	/**
	 * @param Value 受診者郵便番号
	 */
	public void setPostalCode(String Value)
	{
		PostalCode = Utility.ConvertPostalCode(Value);
	}
	private String PostalCode = null;


	/**
	 * @param Value 受診者の氏名
	 */
	public void setName(String Value)
	{
		Name = Value;
	}
	private String Name = null;

	/**
	 * @param Value 受診者性別のコード
	 */
	public void setGender(String Value)
	{
		GenderCode = Value;
	}
	private String GenderCode = null;

	/**
	 * @param Value 受診者の生年月日（Calendarクラスの月成分の1月ずれる問題は、内部で修正）
	 */
	public void setBirthTime(Calendar Value)
	{
		BirthTime = Utility.GetTimeString(Value);
	}
	public void setBirthTime(String Value)
	{
		BirthTime = Value;
	}
	private String BirthTime = null;
}
