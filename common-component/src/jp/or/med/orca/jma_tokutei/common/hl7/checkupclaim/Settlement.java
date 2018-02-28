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
		element.appendChild(doc.createComment("¿‹‹æ•ªƒR[ƒh"));
		Element element_claimType = doc.createElement("claimType");
		element_claimType.setAttribute("code", ClaimType);
		element.appendChild(element_claimType);

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("ˆÏ‘õ—¿’P‰¿(ŒÂ•ÊŒ’fEW’cŒ’f)‹æ•ªƒR[ƒh"));
		Element element_commissionType = doc.createElement("commissionType");
		element_commissionType.setAttribute("code", CommissionType);
		element.appendChild(element_commissionType);

		int iClaimType = Integer.valueOf(ClaimType);

		if(1 <= iClaimType && iClaimType <= 4)
		{
			Element element_priceBasic = doc.createElement("unitPriceBasic");
			// add s.inoue 2010/01/18
			element_priceBasic.appendChild(doc.createComment("’P‰¿(Šî–{“I‚ÈŒ’f)"));
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
							element_priceDetail.appendChild(doc.createComment("’P‰¿(Ú×‚ÈŒ’f)"));
							element_priceDetail.appendChild(element_amount);

							Element element_observation = doc.createElement("observation");
							element_observation.setAttribute("code", Code);
							// add s.inoue 2010/01/18
							element_priceDetail.appendChild(doc.createComment("Ú×‚ÈŒ’f€–ÚƒR[ƒh"));
							element_priceDetail.appendChild(element_observation);

							element.appendChild(element_priceDetail);

						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		// edit ver2 s.inoue 2009/07/21 doc‚ğ•Êˆ—
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
					element_priceOther.appendChild(doc.createComment("’P‰¿(’Ç‰ÁŒ’f)"));

					Element element_amount = doc.createElement("amount");
					element_amount.setAttribute("value", Amount);
					element_amount.setAttribute("currency", "JPY");
					element_priceOther.appendChild(element_amount);

					if(3 <= iClaimType && iClaimType <= 4)
					{
						Element element_observation = doc.createElement("observation");
						// add s.inoue 2010/01/18
						element_priceOther.appendChild(doc.createComment("’Ç‰ÁŒ’f€–ÚƒR[ƒh"));

						element_observation.setAttribute("code", Code);
						element_observation.setAttribute("codeSystem", "1.2.392.200119.6.1005");
						element_priceOther.appendChild(element_observation);
					}

					element.appendChild(element_priceOther);
				}
			}
		}
		// edit ver2 s.inoue 2009/07/21
		// lŠÔdoc—pXV
		if(iClaimType == 5)
		{
			if(PriceOtherDoc > 0)
			{
				String Amount = String.valueOf(this.PriceOtherDoc);

				Element element_priceOther = doc.createElement("unitPriceOther");
				Element element_amount = doc.createElement("amount");
				// add s.inoue 2010/01/18
				element_priceOther.appendChild(doc.createComment("lŠÔƒhƒbƒN€–ÚƒR[ƒh"));
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
			element_basic.appendChild(doc.createComment("‘‹Œû•‰’S‹àŠz(Šî–{“I‚ÈŒ’f)"));
			Element element_amount = doc.createElement("amount");
			element_amount.setAttribute("value", PaymentBasic);
			element_amount.setAttribute("currency", "JPY");

			element_basic.appendChild(element_amount);
			element.appendChild(element_basic);
		}

		if(iClaimType == 2 || iClaimType == 4)
		{

			Element element_detail = doc.createElement("paymentForDetail");
			element_detail.appendChild(doc.createComment("‘‹Œû•‰’S‹àŠz(Ú×‚ÈŒ’f)"));
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
			element_other.appendChild(doc.createComment("‘‹Œû•‰’S‹àŠz(’Ç‰ÁŒ’f)"));
			Element element_amount = doc.createElement("amount");
			element_amount.setAttribute("value", PaymentOther);
			element_amount.setAttribute("currency", "JPY");

			element_other.appendChild(element_amount);
			element.appendChild(element_other);
		}

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("’P‰¿‡Œv‹àŠz"));
		Element element_unitAmount = doc.createElement("unitAmount");
		element_unitAmount.setAttribute("value", UnitAmount);
		element_unitAmount.setAttribute("currency", "JPY");
		element.appendChild(element_unitAmount);

		// add s.inoue 2010/01/18
		element.appendChild(doc.createComment("‘‹Œû•‰’S‹àŠz"));
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
		element.appendChild(doc.createComment("•ÛŒ¯Ò‚Ö‚Ì¿‹‹àŠz"));

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
	 * @param Value ¿‹‹æ•ª
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
	 * @param Value ˆÏ‘õ—¿’P‰¿‹æ•ªƒR[ƒh
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
	 * @param Value Šî–{“I‚ÈŒ’f‚Ì‹àŠz
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
	 * @param Code Ú×‚ÈŒ’f‚Ì€–ÚƒR[ƒh
	 * @param Value ’P‰¿
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
	 * lŠÔƒhƒbƒN—p
	 * @param Value ’P‰¿
	 */
	public void setPriceOther(long Value)
	{
		this.PriceOtherDoc = Value;
	}
	private Long PriceOtherDoc = 0L;

	/**
	 * @param Code ’Ç‰Á‚ÌŒ’f‚Ì€–ÚƒR[ƒh
	 * @param Value ’P‰¿
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
	 * @param Value Šî–{“I‚ÈŒ’f€–Ú‚ÉŒW‚í‚é‘‹Œû•‰’S‹àŠz
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
	 * @param Value Ú×‚ÈŒ’f€–Ú‚ÉŒW‚í‚é‘‹Œû•‰’S‹àŠz
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
	 * @param Value ’Ç‰ÁŒ’f–”‚ÍlŠÔƒhƒbƒO‚ÉŒW‚í‚é‘‹Œû•‰’S‹àŠz
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
	 * @param Value ’P‰¿‡Œv‹àŠz
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
	 * @param Value ‘‹Œû•‰’S‹àŠz
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
	 * @param Value ‹¤“¯À{‚µ‚½‘¼‚ÌŒ’f‘¤‚Å•‰’S‚·‚é‹àŠz
	 */
	public void setPaymentOtherProgram(String Value)
	{
		PaymentOtherProgram = Value;
	}
	private String PaymentOtherProgram = null;

	/**
	 * @param Value •ÛŒ¯Ò‚Ö‚Ì¿‹‹àŠz
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

