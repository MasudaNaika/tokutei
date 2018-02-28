package jp.or.med.orca.jma_tokutei.common.hl7.summary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Summary {
	public Element getElement(Document doc,Element element)
	{
		Element element_service = doc.createElement("serviceEventType");
		element_service.setAttribute("code", ServiceEventType);
		element.appendChild(element_service);

		Element element_subject = doc.createElement("totalSubjectCount");
		element_subject.setAttribute("value", TotalSubjectCount);
		element.appendChild(element_subject);
		
		Element element_cost = doc.createElement("totalCostAmount");
		element_cost.setAttribute("value", TotalCostAmount);
		element.appendChild(element_cost);
		
		Element element_payment = doc.createElement("totalPaymentAmount");
		element_payment.setAttribute("value", TotalPaymentAmount);
		element.appendChild(element_payment);
		
		Element element_other_payment = doc.createElement("totalPaymentByOtherProgram");
		element_other_payment.setAttribute("value", TotalPaymentByOtherProgram);
		element.appendChild(element_other_payment);
		
		Element element_claim = doc.createElement("totalClaimAmount");
		element_claim.setAttribute("value", TotalClaimAmount);
		element.appendChild(element_claim);
		
		return element;
	}
	
	public boolean Check()
	{
		if(
				ServiceEventType == null ||
				TotalSubjectCount == null ||
				TotalCostAmount == null ||
				TotalPaymentAmount == null ||
				TotalPaymentByOtherProgram == null ||
				TotalClaimAmount == null
		)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @param Value 実施区分
	 */
	public void setServiceEventType(int Value)
	{
		if(1 <= Value && Value <= 4)
		{
			ServiceEventType = String.valueOf(Value);
		}
	}
	public void setServiceEventType(String Value)
	{
		ServiceEventType = Value;
	}
	private String ServiceEventType = null;
	
	/**
	 * @param Value アーカイブに含まれる受診者・利用者の総数
	 */
	public void setTotalSubjectCount(long Value)
	{
		TotalSubjectCount = String.valueOf(Value);
	}
	public void setTotalSubjectCount(String Value)
	{
		TotalSubjectCount = Value;
	}
	private String TotalSubjectCount = null;
	
	/**
	 * @param Value
	 * 特定健診：アーカイブに含まれる単価の金額総計
	 * 保健指導：アーカイブに含まれる算定金額の総計
	 */
	public void setTotalCostAmount(long Value)
	{
		TotalCostAmount = String.valueOf(Value);
	}
	public void setTotalCostAmount(String Value)
	{
		TotalCostAmount = Value;
	}
	private String TotalCostAmount = null;
	
	/**
	 * @param Value アーカイブに含まれる窓口支払金額の集計
	 */
	public void setTotalPaymentAmount(long Value)
	{
		TotalPaymentAmount = String.valueOf(Value);
	}
	public void setTotalPaymentAmount(String Value)
	{
		TotalPaymentAmount = Value;
	}
	private String TotalPaymentAmount = null;
	
	/**
	 * @param Value 共同実施した他の健診側で負担する金額の集計
	 */
	public void setTotalPaymentByOtherProgram(long Value)
	{
		TotalPaymentByOtherProgram = String.valueOf(Value);
	}
	public void setTotalPaymentByOtherProgram(String Value)
	{
		TotalPaymentByOtherProgram = Value;
	}
	private String TotalPaymentByOtherProgram = null;
	
	/**
	 * @param Value アーカイブに含まれる保険者への請求金額の集計
	 */
	public void setTotalClaimAmount(long Value)
	{
		TotalClaimAmount = String.valueOf(Value);
	}
	public void setTotalClaimAmount(String Value)
	{
		TotalClaimAmount = Value;
	}
	private String TotalClaimAmount = null;
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


