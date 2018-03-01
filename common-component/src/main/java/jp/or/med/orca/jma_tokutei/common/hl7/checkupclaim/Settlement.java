package jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim;


import java.util.Enumeration;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Settlement {
	public Settlement()
	{
		PriceDetail = new Hashtable<String, String>();
		PriceOther = new Hashtable<String, String>();
	}

	public Element GetElement(Document doc)
	{
		Element element = doc.createElement("settlement");
		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("請求区分コード"));
		Element element_claimType = doc.createElement("claimType");
		element_claimType.setAttribute("code", ClaimType);
		element.appendChild(element_claimType);

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("委託料単価(個別健診・集団健診)区分コード"));
		Element element_commissionType = doc.createElement("commissionType");
		element_commissionType.setAttribute("code", CommissionType);
		element.appendChild(element_commissionType);

		int iClaimType = Integer.valueOf(ClaimType);

		if(1 <= iClaimType && iClaimType <= 4)
		{
			Element element_priceBasic = doc.createElement("unitPriceBasic");
			// add s.inoue 2010/01/18
			element_priceBasic.appendChild(doc.createComment("単価(基本的な健診)"));
			Element element_amount = doc.createElement("amount");
			element_amount.setAttribute("value", PriceBasic);
			element_amount.setAttribute("currency", "JPY");
			element_priceBasic.appendChild(element_amount);

			element.appendChild(element_priceBasic);
		}

		if(iClaimType == 2 || iClaimType == 4)
		{
			if(PriceDetail.size() > 0)
			{
				Enumeration<String> Keys = PriceDetail.keys();

				while(Keys.hasMoreElements())
				{
					String Code = Keys.nextElement();
					String amount = PriceDetail.get(Code);

					if (! amount.isEmpty() && amount != null) {
						try {
							Element element_priceDetail = doc.createElement("unitPriceDetail");
							Element element_amount = doc.createElement("amount");
							element_amount.setAttribute("value", amount);
							element_amount.setAttribute("currency", "JPY");
							// add s.inoue 2010/01/18
							element_priceDetail.appendChild(doc.createComment("単価(詳細な健診)"));
							element_priceDetail.appendChild(element_amount);

							Element element_observation = doc.createElement("observation");
							element_observation.setAttribute("code", Code);
							// add s.inoue 2010/01/18
							element_priceDetail.appendChild(doc.createComment("詳細な健診項目コード"));
							element_priceDetail.appendChild(element_observation);

							element.appendChild(element_priceDetail);

						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		// edit ver2 s.inoue 2009/07/21 docを別処理
		if(3 <= iClaimType && iClaimType <= 4)
		{
			if(PriceOther.size() > 0)
			{
				Enumeration<String> Keys = PriceOther.keys();

				while(Keys.hasMoreElements())
				{
					String Code = Keys.nextElement();
					String Amount = PriceOther.get(Code);
					Element element_priceOther = doc.createElement("unitPriceOther");
					// add s.inoue 2010/01/18
					element_priceOther.appendChild(doc.createComment("単価(追加健診)"));

					Element element_amount = doc.createElement("amount");
					element_amount.setAttribute("value", Amount);
					element_amount.setAttribute("currency", "JPY");
					element_priceOther.appendChild(element_amount);

					if(3 <= iClaimType && iClaimType <= 4)
					{
						Element element_observation = doc.createElement("observation");
						// add s.inoue 2010/01/18
						element_priceOther.appendChild(doc.createComment("追加健診項目コード"));

						element_observation.setAttribute("code", Code);
						element_observation.setAttribute("codeSystem", "1.2.392.200119.6.1005");
						element_priceOther.appendChild(element_observation);
					}

					element.appendChild(element_priceOther);
				}
			}
		}
		// edit ver2 s.inoue 2009/07/21
		// 人間doc用更新
		if(iClaimType == 5)
		{
			if(PriceOtherDoc > 0)
			{
				String Amount = String.valueOf(this.PriceOtherDoc);

				Element element_priceOther = doc.createElement("unitPriceOther");
				Element element_amount = doc.createElement("amount");
				// add s.inoue 2010/01/18
				element_priceOther.appendChild(doc.createComment("人間ドック項目コード"));
				element_amount.setAttribute("value", Amount);
				element_amount.setAttribute("currency", "JPY");
				element_priceOther.appendChild(element_amount);
				element.appendChild(element_priceOther);
			}
		}

		if(1 <= iClaimType && iClaimType <= 4)
		{

			Element element_basic = doc.createElement("paymentForBasic");
			// add s.inoue 2010/01/18
			element_basic.appendChild(doc.createComment("窓口負担金額(基本的な健診)"));
			Element element_amount = doc.createElement("amount");
			element_amount.setAttribute("value", PaymentBasic);
			element_amount.setAttribute("currency", "JPY");

			element_basic.appendChild(element_amount);
			element.appendChild(element_basic);
		}

		if(iClaimType == 2 || iClaimType == 4)
		{

			Element element_detail = doc.createElement("paymentForDetail");
			element_detail.appendChild(doc.createComment("窓口負担金額(詳細な健診)"));
			Element element_amount = doc.createElement("amount");
			element_amount.setAttribute("value", PaymentDetail);
			element_amount.setAttribute("currency", "JPY");
			// add s.inoue 2010/01/18

			element_detail.appendChild(element_amount);
			element.appendChild(element_detail);
		}

		if(3 <= iClaimType && iClaimType <= 5)
		{

			Element element_other = doc.createElement("paymentForOther");
			// add s.inoue 2010/01/18
			element_other.appendChild(doc.createComment("窓口負担金額(追加健診)"));
			Element element_amount = doc.createElement("amount");
			element_amount.setAttribute("value", PaymentOther);
			element_amount.setAttribute("currency", "JPY");

			element_other.appendChild(element_amount);
			element.appendChild(element_other);
		}

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("単価合計金額"));
		Element element_unitAmount = doc.createElement("unitAmount");
		element_unitAmount.setAttribute("value", UnitAmount);
		element_unitAmount.setAttribute("currency", "JPY");
		element.appendChild(element_unitAmount);

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("窓口負担金額"));
		Element element_paymentAmount = doc.createElement("paymentAmount");
		element_paymentAmount.setAttribute("value", PaymentAmount);
		element_paymentAmount.setAttribute("currency", "JPY");
		element.appendChild(element_paymentAmount);


		Element element_paymentByOtherProgram = doc.createElement("paymentByOtherProgram");

		if (PaymentOtherProgram != null && ! PaymentOtherProgram.isEmpty()) {

			long paymentOtherValue = 0L;
			try {
				paymentOtherValue = Long.parseLong(PaymentOtherProgram);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			if (paymentOtherValue != 0L) {
				element_paymentByOtherProgram.setAttribute("value", PaymentOtherProgram);
				element_paymentByOtherProgram.setAttribute("currency", "JPY");
				element.appendChild(element_paymentByOtherProgram);
			}
		}

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("保険者への請求金額"));

		Element element_claimAmount = doc.createElement("claimAmount");
		element_claimAmount.setAttribute("value", ClaimAmount);
		element_claimAmount.setAttribute("currency", "JPY");
		element.appendChild(element_claimAmount);

		return element;
	}

	public boolean Check()
	{
		if(ClaimType == null || CommissionType == null || ClaimType == null)
		{
			return false;
		}

		int iClaimType = Integer.valueOf(ClaimType);

		if(1 <= iClaimType && iClaimType <= 4)
		{
			if(PriceBasic == null)
			{
				return false;
			}
		}

		if(1 <= iClaimType && iClaimType <= 4)
		{
			if(PaymentBasic == null)
			{
				return false;
			}
		}

		if(iClaimType == 2 || iClaimType == 4)
		{
			if(PaymentDetail == null)
			{
				return false;
			}
		}

		if(3 <= iClaimType && iClaimType <= 5)
		{
			if(PaymentOther == null)
			{
				return false;
			}
		}

		if(UnitAmount == null || PaymentAmount == null || ClaimAmount == null)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param Value 請求区分
	 */
	public void setClaimType(int Value)
	{
		if(1 <= Value && Value <= 5)
		{
			ClaimType = String.valueOf(Value);
		}
	}
	public void setClaimType(String Value)
	{
		ClaimType = Value;
	}
	private String ClaimType = null;

	/**
	 * @param Value 委託料単価区分コード
	 */
	public void setCommissionType(int Value)
	{
		if(1 <= Value && Value <= 2)
		{
			CommissionType = String.valueOf(Value);
		}
	}
	public void setCommissionType(String Value)
	{
		CommissionType = Value;
	}
	private String CommissionType = null;

	/**
	 * @param Value 基本的な健診の金額
	 */
	public void setPriceBasic(long Value)
	{
		PriceBasic = String.valueOf(Value);
	}
	public void setPriceBasic(String Value)
	{
		PriceBasic = Value;
	}
	private String PriceBasic = null;

	/**
	 * @param Code 詳細な健診の項目コード
	 * @param Value 単価
	 */
	public void addPriceDetail(String Code,long Value)
	{
		PriceDetail.put(Code, String.valueOf(Value));
	}
	public void addPriceDetail(String Code,String Value)
	{
		PriceDetail.put(Code, Value);
	}
	private Hashtable<String,String> PriceDetail = null;
	// add ver2 s.inoue 2009/07/22
	/**
	 * 人間ドック用
	 * @param Value 単価
	 */
	public void setPriceOther(long Value)
	{
		this.PriceOtherDoc = Value;
	}
	private Long PriceOtherDoc = 0L;

	/**
	 * @param Code 追加の健診の項目コード
	 * @param Value 単価
	 */
	public void addPriceOther(String Code,long Value)
	{
		PriceOther.put(Code, String.valueOf(Value));
	}
	public void addPriceOther(String Code,String Value)
	{
		PriceOther.put(Code, Value);
	}
	private Hashtable<String,String> PriceOther = null;

	/**
	 * @param Value 基本的な健診項目に係わる窓口負担金額
	 */
	public void setPaymentBasic(long Value)
	{
		PaymentBasic = Utility.FillZero(Value, 6);
	}
	public void setPaymentBasic(String Value)
	{
		PaymentBasic = Utility.FillZero(Value, 6);
	}
	private String PaymentBasic = null;

	/**
	 * @param Value 詳細な健診項目に係わる窓口負担金額
	 */
	public void setPaymentDetail(long Value)
	{
		PaymentDetail = Utility.FillZero(Value, 6);
	}
	public void setPaymentDetail(String Value)
	{
		PaymentDetail = Utility.FillZero(Value, 6);
	}

	private String PaymentDetail = null;

	/**
	 * @param Value 追加健診又は人間ドッグに係わる窓口負担金額
	 */
	public void setPaymentOther(long Value)
	{
		PaymentOther = Utility.FillZero(Value, 6);
	}
	public void setPaymentOther(String Value)
	{
		PaymentOther = Utility.FillZero(Value, 6);
	}
	private String PaymentOther = null;

	/**
	 * @param Value 単価合計金額
	 */
	public void setUnitAmount(long Value)
	{
		UnitAmount = String.valueOf(Value);
	}
	public void setUnitAmount(String Value)
	{
		UnitAmount = Value;
	}
	private String UnitAmount = null;

	/**
	 * @param Value 窓口負担金額
	 */
	public void setPaymentAmount(long Value)
	{
		PaymentAmount = String.valueOf(Value);
	}
	public void setPaymentAmount(String Value)
	{
		PaymentAmount = Value;
	}
	private String PaymentAmount = null;

	/**
	 * @param Value 共同実施した他の健診側で負担する金額
	 */
	public void setPaymentOtherProgram(String Value)
	{
		PaymentOtherProgram = Value;
	}
	private String PaymentOtherProgram = null;

	/**
	 * @param Value 保険者への請求金額
	 */
	public void setClaimAmount(long Value)
	{
		ClaimAmount = String.valueOf(Value);
	}
	public void setClaimAmount(String Value)
	{
		ClaimAmount = Value;
	}
	private String ClaimAmount = null;
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

