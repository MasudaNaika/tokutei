package jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SubjectPerson {
	public Element GetElement(Document doc)
	{
		Element element = doc.createElement("subjectPerson");

		Element element_performer = doc.createElement("performerOrganization");
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("実施機関番号"));

		Element element_id = doc.createElement("id");
		element_id.setAttribute("root", "1.2.392.200119.6.102");
		element_id.setAttribute("extension", OrganizationId);
		element_performer.appendChild(element_id);

		element.appendChild(element_performer);

		Element element_card = doc.createElement("insuranceCard");
		// add s.inoue 2010/01/18
		element_card.appendChild(doc.createComment("保険者番号"));
		Element element_insure = doc.createElement("insurerNumber");
		element_insure.setAttribute("root", "1.2.392.200119.6.101");
		element_insure.setAttribute("extension", InsureNumber);
		element_card.appendChild(element_insure);

		// 被保険者証等記号が必須ではなくなったので、それの対応。
		/* Modified 2008/03/31 若月  */
		/* --------------------------------------------------- */
//		if(InsurerCardSymbol != null)
//		{
//			Element element_symbol = doc.createElement("symbol");
//			element_symbol.setAttribute("root", "1.2.392.200119.6.204");
//			element_symbol.setAttribute("extension", InsurerCardSymbol);
//			element_card.appendChild(element_symbol);
//		}
		/* --------------------------------------------------- */
		if(InsurerCardSymbol != null && ! InsurerCardSymbol.isEmpty())
		{
			// add s.inoue 2010/01/18
			element_card.appendChild(doc.createComment("被保険者証等記号"));
			Element element_symbol = doc.createElement("symbol");
			element_symbol.setAttribute("root", "1.2.392.200119.6.204");
			element_symbol.setAttribute("extension", InsurerCardSymbol);
			element_card.appendChild(element_symbol);
		}
		/* --------------------------------------------------- */

		/* Modified 2008/03/31 若月  */
		/* --------------------------------------------------- */
//		Element element_number = doc.createElement("number");
//		element_number.setAttribute("root", "1.2.392.200119.6.205");
//		element_number.setAttribute("extension", InsurerCardNumber);
//		element_card.appendChild(element_number);
		/* --------------------------------------------------- */
		if (InsurerCardNumber != null && ! InsurerCardNumber.isEmpty()) {
			// add s.inoue 2010/01/18
			element_card.appendChild(doc.createComment("被保険者証等番号"));
			Element element_number = doc.createElement("number");
			element_number.setAttribute("root", "1.2.392.200119.6.205");
			element_number.setAttribute("extension", InsurerCardNumber);
			element_card.appendChild(element_number);
		}
		/* --------------------------------------------------- */

		element.appendChild(element_card);

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("氏名"));
		Element element_name = doc.createElement("name");

		/* Modified 2008/06/08 若月  */
		/* --------------------------------------------------- */
//		element_name.setTextContent(Name);
		/* --------------------------------------------------- */
		/* 空白文字を削除する。 */
		String name = Name.replaceAll("[ 　\\t]", "");
		element_name.setTextContent(name);
		/* --------------------------------------------------- */

		element.appendChild(element_name);
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("住所と郵便番号"));
		Element element_addr = doc.createElement("addr");

		/* Modified 2008/06/08 若月  */
		/* --------------------------------------------------- */
//		element_addr.setTextContent(Address);
		/* --------------------------------------------------- */
		/* 空白文字を削除する。 */
		String address = Address.replaceAll("[ 　\\t]", "");
		element_addr.setTextContent(address);
		/* --------------------------------------------------- */

		Element element_postal = doc.createElement("postalCode");
		element_postal.setTextContent(PostalCode);
		element_addr.appendChild(element_postal);
		element.appendChild(element_addr);

		Element element_birth = doc.createElement("birthTime");
		element_birth.setAttribute("value", BirthTime);
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("生年月日"));
		element.appendChild(element_birth);

		Element element_gender = doc.createElement("administrativeGender");
		element_gender.setAttribute("code", Gender);
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("性別"));
		element.appendChild(element_gender);

		return element;
	}

	public boolean Check()
	{
		if(
				OrganizationId == null ||
				InsureNumber == null ||
				InsurerCardNumber == null ||
				Name == null ||
				Address == null ||
				PostalCode == null ||
				BirthTime == null ||
				Gender == null
		)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value 機関番号
	 */
	public void setOrganizationId(long Value)
	{
		OrganizationId = Utility.FillZero(Value, 10);
	}
	public void setOrganizationId(String Value)
	{
		OrganizationId = Value;
	}
	private String OrganizationId = null;

	/**
	 * @param Value 保険者番号
	 */
	public void setInsureNumber(long Value)
	{
		InsureNumber = Utility.FillZero(Value, 8);
	}
	public void setInsureNumber(String Value)
	{
		InsureNumber = Value;
	}
	private String InsureNumber = null;

	/**
	 * @param Value 被保険者証等記号
	 */
	public void setInsurerCardSymbol(String Value)
	{
		InsurerCardSymbol = Value;
	}
	private String InsurerCardSymbol = null;

	/**
	 * @param Value 被保険者証等番号
	 */
	public void setInsurerCardNumber(String Value)
	{
		InsurerCardNumber = Value;
	}
	private String InsurerCardNumber = null;

	/**
	 * @param Value 名前
	 */
	public void setName(String Value)
	{
		Name = Value;
	}
	private String Name = null;

	/**
	 * @param Value 住所
	 */
	public void setAddress(String Value)
	{
		Address = Value;
	}
	private String Address = null;

	/**
	 * @param Value 郵便番号
	 */
	public void setPostalCode(String Value)
	{
		PostalCode = Utility.ConvertPostalCode(Value);
	}
	private String PostalCode = null;

	/**
	 * @param Value 生年月日
	 */
	public void setBirthTime(String Value)
	{
		BirthTime = Value;
	}
	private String BirthTime = null;

	/**
	 * @param Value 性別
	 */
	public void setGender(String Value)
	{
		Gender = Value;
	}
	private String Gender = null;
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

