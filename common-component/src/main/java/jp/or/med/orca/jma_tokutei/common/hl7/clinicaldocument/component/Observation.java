package jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Observation {
	private static final String DEFAULT_VALUE_CODE_SYSTEM = "1.2.392.200119.6.1005";

	public Element getElement(Document doc)
	{
		// move s.inoue 20080912
		Element element_code = null;

		Element element = doc.createElement("observation");
		element.setAttribute("classCode", "OBS");
		element.setAttribute("moodCode", "EVN");

		boolean existsResult = false;
		if(result != null && ! result.isEmpty()){
			existsResult = true;
		}

		// 健診実施

		// ヘッダ
//		String negationIndValue = existsResult ? "false" : "true";

		/* Modified 2008/06/23 若月  */
		/* --------------------------------------------------- */
//		/* 結果値が存在する場合、サンプルに従い negationInd 属性の付加を省略する。 */
//		if (! existsResult) {
//			element.setAttribute("negationInd", "true");
//		}

		// del s.inoue 20080910
		/* --------------------------------------------------- */
		/* 結果値が存在しない場合、要素を出現させない。 */
//		if (! existsResult) {
//			return null;
//		}
		/* --------------------------------------------------- */

		// add s.inoue 20080910 未実施
		/* 実施区分より「0:未実施」の時、negationInd 属性の付加を省略する。 */
		/*code.equals("9A755000000000001") ||code.equals("9A752000000000001") ||code.equals("9A751000000000001") ||*/
		if (this.JISI_KBN == null ||
				(this.JISI_KBN.equals("1") && !existsResult)){
			if (code.equals("9A765000000000001") ||
				code.equals("9A762000000000001") ||
				code.equals("9A761000000000001")){
				this.JISI_KBN = "0";
			}else{
				return null;
			}
		}

		// add s.inoue 20081002 測定不可能は省略可
		if (this.JISI_KBN.equals("0")) {
			element.setAttribute("negationInd", "true");
		}

		// edit s.inoue 20080912
		// 実施及び測定不可能のみ要素作成
		element_code = doc.createElement("code");
		element_code.setAttribute("code", code);

		/* Modified 2008/04/23 若月  */
		/* --------------------------------------------------- */
//		if (codeSystem != DEFAULT_VALUE_CODE_SYSTEM) {
		/* --------------------------------------------------- */
		if (! codeSystem.equals(DEFAULT_VALUE_CODE_SYSTEM)) {
		/* --------------------------------------------------- */
			element_code.setAttribute("codeSystem", codeSystem);
		}
		element.appendChild(element_code);

		/* Added 2008/04/06 若月 */
		/* --------------------------------------------------- */
		if (this.codeDisplayName != null && !this.codeDisplayName.isEmpty()) {
			element_code.setAttribute("displayName", this.codeDisplayName);
		}
		/* --------------------------------------------------- */

		// 時間情報
		if (effectiveTime != null && ! effectiveTime.isEmpty()) {
			Element element_time = doc.createElement("effectiveTime");
			Element element_width = doc.createElement("width");
			element_width.setAttribute("value", effectiveTime);
			element_width.setAttribute("unit", "min");
			element_time.appendChild(element_width);
			element.appendChild(element_time);
		}

		/* Modified 2008/04/06 若月 */
		/* --------------------------------------------------- */
		// // 結果
		// Element element_value = doc.createElement("value");
		// element_value.setAttribute("xsi:type", DataType);
		// if(Pattern == 1)
		// {
		// // パターン1
		// element_value.setAttribute("value", Result);
		// element_value.setAttribute("unit", Unit);
		// }
		// if(Pattern == 2)
		// {
		// if(DataType.equals("CD") || DataType.equals("CO"))
		// {
		// // パターン2 コード
		// element_value.setAttribute("code", Result);
		// element_value.setAttribute("codeSystem", ResultCode);
		// element_value.setAttribute("displayName", DisplayName);
		// }
		//
		// if(DataType.equals("ST"))
		// {
		// // パターン2 文字列
		// element_value.setTextContent(Result);
		// }
		// }
		// element.appendChild(element_value);
		/* --------------------------------------------------- */

		// edit s.inoue 20080910 未実施 条件修正
//		if (existsResult) {

		if (this.JISI_KBN.equals("1") ||
				this.JISI_KBN.equals("2")) {

			// 結果
			/* value 要素 */
			Element element_value = doc.createElement("value");

			/* xsi:type 属性 */
			element_value.setAttribute("xsi:type", dataType);

			boolean inInputRange = true;

			// add s.inoue 20080910 測定不可能　条件修正
			if (this.JISI_KBN.equals("2")) {
				element_value.setAttribute("nullFlavor", "NI");

				/* value 要素 */
				element.appendChild(element_value);
			}else if (this.JISI_KBN.equals("1") && existsResult){

				if (pattern == 1) {
					/* 数値型 */

					double resultValue = 0;

					/* Added 2008/05/10 若月  */
					/* --------------------------------------------------- */
					/* 入力上限、下限値範囲内 */
	//				boolean inInputRange = true;
					try {
						resultValue = Double.parseDouble(result);

						/* 入力上限値、下限値 */
						/* 結果値が入力下限値よりも小さい場合 */
	//					boolean underLowValue = false;
						if (inputRangeLowValue != null && ! inputRangeLowValue.isEmpty()) {
							try {
								double inputLowLimit = Double.parseDouble(inputRangeLowValue);
								if (resultValue <= inputLowLimit ) {

	//								underLowValue = true;
									inInputRange = false;

									element_value.setAttribute("code", "L");
									element_value.setAttribute("codeSystem", "2.16.840.1.113883.5.83");
									element_value.setAttribute("displayName", "以下");

									/* Added 2008/06/23 若月  */
									/* --------------------------------------------------- */
									element_value.setAttribute("xsi:type", "CD");
									/* --------------------------------------------------- */
								}

							} catch (NumberFormatException e) {
								/* 数値に変換できなかった場合 */
								e.printStackTrace();
							}
						}

						if (inInputRange) {
							/* 入力上限値範囲外 */
	//						boolean overHighValue = false;

							if (inputRangeHighValue != null && ! inputRangeHighValue.isEmpty()) {
								try {
									double inputHighLimit = Double.parseDouble(inputRangeHighValue);
									if (resultValue >= inputHighLimit ) {
										inInputRange = false;

										element_value.setAttribute("code", "H");
										element_value.setAttribute("codeSystem", "2.16.840.1.113883.5.83");
										element_value.setAttribute("displayName", "以上");

										/* Added 2008/06/23 若月  */
										/* --------------------------------------------------- */
										element_value.setAttribute("xsi:type", "CD");
										/* --------------------------------------------------- */
									}
								} catch (NumberFormatException e) {
									/* 数値に変換できなかった場合 */
									e.printStackTrace();
								}
							}

							if (inInputRange) {
								this.outputValueAndUnit(element, element_value);
							}
						}

					} catch (NumberFormatException e) {
						/* 数値に変換できなかった場合 */
						e.printStackTrace();
					}

					/* 入力上限値範囲内 */
					if (inInputRange) {
						if (
								observationRangeHighValue != null
								&& ! observationRangeHighValue.isEmpty()
								&& observationRangeLowValue != null
								&& ! observationRangeLowValue.isEmpty()
								) {
							/* 基準値上限値、下限値の両方が存在する場合 */

							/* HL文字列 */
							String hl = null;

							/* 数値に変換して比較する。 */
							try {
								double highValue = Double.parseDouble(observationRangeHighValue);
								double lowValue = Double.parseDouble(observationRangeLowValue);
	//							double resultValue = Double.parseDouble(result);

								/* 結果値が基準値下限よりも小さい（L） */
								if ( resultValue < lowValue ) {
									hl = "L";
								}
								/* 結果値が基準値下限よりも大きい */
								else {
									/* 結果値が基準値上限以下（N） */
									if( resultValue <= highValue ) {
										hl = "N";
									}
									/* 結果値が基準値上限よりも大きい（H） */
									else {
										hl = "H";
									}
								}

							} catch (NumberFormatException e) {
								/* 数値に変換できなかった場合 */
								e.printStackTrace();
							}

							/* value 要素 */
							element.appendChild(element_value);

							/* 基準値上限値、下限値が存在する場合 */
							Element element_interpret = doc.createElement("interpretationCode");
							element_interpret.setAttribute("code", hl);

							element.appendChild(element_interpret);
						}
						else {
							/* 入力上限値範囲内の場合 */
							/* 結果値と単位を出力する。 */
	//						element_value.setAttribute("value", result);
	//						element_value.setAttribute("unit", unit);
							this.outputValueAndUnit(element, element_value);
						}

					}
					/* Added 2008/06/20 若月  */
					/* --------------------------------------------------- */
					else {
						// String tDate = String.valueOf(FiscalYearUtil.getSystemDate(""));
						int tDate = 20130401;
						String kDate = getKensanengapi();

						System.out.println("当日" + tDate + "健診日" + kDate);

						// add s.inoue 2012/09/03
						// 健診実施日が'130401以降であれば、H,Lの場合もPQ(結果値)を出力する
						if (tDate <= Integer.parseInt(kDate)){
							Element element_highlow_value = doc.createElement("value");
							element_highlow_value.setAttribute("xsi:type", dataType);
							this.outputValueAndUnit(element, element_highlow_value);
							element.appendChild(element_highlow_value);
						}

						// eidt s.inoue 2012/09/03
						// 取り込みを考慮して後出しに変更
						/* value 要素 */
						element.appendChild(element_value);
					}
					/* --------------------------------------------------- */
					/* --------------------------------------------------- */
				}
				else if (pattern == 2) {
					if (dataType.equals("CD") || dataType.equals("CO")) {
						// パターン2 コード
						element_value.setAttribute("code", result);
						element_value.setAttribute("codeSystem", resultCode);

						/* Deleted 2008/04/24 若月  */
						/* --------------------------------------------------- */
	//					element_value.setAttribute("displayName", itemCodeDisplayName);
						/* --------------------------------------------------- */
					}
					else if (dataType.equals("ST")) {
						// パターン2 文字列
						element_value.setTextContent(result);
					}

					/* value 要素 */
					element.appendChild(element_value);
				}
			}
			/* 結果解釈コード */
			/* Modified 2008/05/10 若月  */
			/* --------------------------------------------------- */
//			if (pattern == 1 && ! interpretationCode.equals("")) {
			/* --------------------------------------------------- */
//			if (pattern == 1) {
//				Element element_interpret = doc.createElement("interpretationCode");
//
//				/* Modified 2008/05/09 若月  */
//				/* --------------------------------------------------- */
////				element_interpret.setAttribute("code", interpretationCode);
//				/* --------------------------------------------------- */
//
//				/* 基準値上限値、下限値 */
//				/* 基準値上限値、下限値が存在する場合 */
//				if (
//						observationRangeHighValue != null
//						&& ! observationRangeHighValue.isEmpty()
//						&& observationRangeLowValue != null
//						&& ! observationRangeLowValue.isEmpty()
//						) {
//
//					String hl = null;
//
//					/* 数値に変換して比較する。 */
//					try {
//						double highValue = Double.parseDouble(observationRangeHighValue);
//						double lowValue = Double.parseDouble(observationRangeLowValue);
////						double resultValue = Double.parseDouble(result);
//
//						/* 結果値が基準値下限よりも小さい */
//						if ( resultValue < lowValue ) {
//						}
//						/* 結果値が基準値下限よりも大きい */
//						else {
//							/* 結果値が基準値上限以下 */
//							if( resultValue <= highValue ) {
//
//							}
//							/* 結果値が基準値上限よりも大きい */
//							else {
//
//							}
//						}
//
//					} catch (NumberFormatException e) {
//						/* 数値に変換できなかった場合 */
//						e.printStackTrace();
//					}
//				}
//
//				String hl = null;
//				if (interpretationCode.equals("1")) {
//					hl = "L";
//				}
//				else if(interpretationCode.equals("2")){
//					hl = "H";
//				}
//				else {
//					hl = "N";
//				}
//
//				element_interpret.setAttribute("code", hl);
//
//				element.appendChild(element_interpret);
//			}

			/* 検査方法 */
			if (!methodCode.equals("")) {
				Element element_method = doc.createElement("methodCode");
				element_method.setAttribute("code", methodCode);
				element.appendChild(element_method);
			}

			/* 基準値を出力する。 */
			/* Modified 2008/06/25 若月  */
			/* --------------------------------------------------- */
//			if (pattern == 1 &&
//					!observationRangeHighValue.isEmpty() && !observationRangeLowValue.isEmpty()){
			/* --------------------------------------------------- */
			if (pattern == 1 &&
					!observationRangeHighValue.isEmpty() && !observationRangeLowValue.isEmpty()
					&& inInputRange){
			/* --------------------------------------------------- */

				Element element_ref = doc.createElement("referenceRange");
				Element element_observRange = doc.createElement("observationRange");
				element_observRange.setAttribute("classCode", "OBS");
				element_observRange.setAttribute("moodCode", "EVN.CRT");

				Element element_value2 = doc.createElement("value");
				element_value2.setAttribute("xsi:type", "IVL_PQ");

				/* 下限値 */
				Element element_low = doc.createElement("low");
				element_low.setAttribute("value", observationRangeLowValue);
				if(!observationRangeLowUnit.isEmpty()) {
					element_low.setAttribute("unit", observationRangeLowUnit);
				}
				element_value2.appendChild(element_low);

				/* 上限値 */
				Element element_high = doc.createElement("high");
				element_high.setAttribute("value", observationRangeHighValue);
				if(	!observationRangeHighUnit.isEmpty()){
					element_high.setAttribute("unit", observationRangeHighUnit);
				}
				element_value2.appendChild(element_high);

				element_observRange.appendChild(element_value2);
				element_ref.appendChild(element_observRange);
				element.appendChild(element_ref);
			}
		}
		/* --------------------------------------------------- */

		// 記録者情報
		if (author != null && ! author.isEmpty()) {
			/* Modified 2008/06/08 若月  */
			/* --------------------------------------------------- */
//			Element element_author = doc.createElement("author");
//			element_author.setAttribute("nullFlavor", "NI");
//			Element element_assighend = doc.createElement("assignedAuthor");
//			element_assighend.setAttribute("nullFlavor", "NI");
//			Element element_person = doc.createElement("assignedPerson");
//
//			Element element_name = doc.createElement("name");
//			element_name.setTextContent(author);
//			element_person.appendChild(element_person);
//
//			element_assighend.appendChild(element_person);
//			element_author.appendChild(element_assighend);
//			element.appendChild(element_author);
			/* --------------------------------------------------- */
			/* author 要素 */
			Element element_author = doc.createElement("author");
			element.appendChild(element_author);

			/* time 要素 */
			Element element_time = doc.createElement("time");
			element_time.setAttribute("nullFlavor", "NI");
			element_author.appendChild(element_time);

			/* assignedAuthor 要素 */
			Element element_assighendAuthor = doc.createElement("assignedAuthor");
			element_author.appendChild(element_assighendAuthor);

			/* id 要素 */
			Element element_id = doc.createElement("id");
			element_id.setAttribute("nullFlavor", "NI");
			element_assighendAuthor.appendChild(element_id);

			/* assignedPerson 要素 */
			Element element_person = doc.createElement("assignedPerson");
			element_assighendAuthor.appendChild(element_person);

			/* name 要素 */
			Element element_name = doc.createElement("name");
			element_name.setTextContent(author);
			element_person.appendChild(element_name);
			/* --------------------------------------------------- */
		}

		// }else{
		// 健診未実施
		// element.setAttribute("negationInd", "true");
		//
		// Element element_code = doc.createElement("code");
		// element_code.setAttribute("code", Code);
		//
		// element.appendChild(element_code);
		// }

		return element;
	}

	private void outputValueAndUnit(Element element, Element element_value) {
		/* 入力上限値範囲内の場合 */
		/* 結果値と単位を出力する。 */

		/* Modified 2008/06/10 若月  */
		/* --------------------------------------------------- */
//		element_value.setAttribute("value", result);
		/* --------------------------------------------------- */
		String resultOutput = null;

		if (this.result.startsWith(".")) {
			resultOutput =  "0" + this.result;
		}
		else {
			resultOutput =  this.result;
		}

		element_value.setAttribute("value", resultOutput);
		/* --------------------------------------------------- */


		if (this.unit != null && ! this.unit.isEmpty()) {
			element_value.setAttribute("unit", unit);
		}

		/* value 要素 */
		element.appendChild(element_value);
	}

	public boolean check()
	{
		if(result == null)
		{
			return false;
		}

		if(!result.equals(""))
		{
			// 健診実施

			if(
					code == null ||
					codeSystem == null ||
					dataType == null ||
					pattern < 0
			)
			{
				return false;
			}

			if(pattern == 1)
			{
				// パターン1

				if(
						result == null ||
						unit == null
				)
				{
					return false;
				}
			}
			if(pattern == 2)
			{
				if(dataType == null)
				{
					return false;
				}

				if(dataType.equals("CD") || dataType.equals("CO"))
				{
					// パターン2 コード
					if(
							result == null ||
							resultCode == null ||
							itemCodeDisplayName == null
					)
					{
						return false;
					}
				}

				if(dataType.equals("ST"))
				{
					// パターン2 文字列
					if(result == null)
					{
						return false;
					}
				}
			}

			// 結果解釈コード

			if(interpretationCode == null)
			{
				return false;
			}

			if(pattern == 1 && !interpretationCode.isEmpty())
			{
				if(interpretationCode == null)
				{
					return false;
				}
			}

			// 検査方法
			if(methodCode == null)
			{
				return false;
			}

			if(!methodCode.isEmpty())
			{
				if(methodCode == null)
				{
					return false;
				}
			}

			if(observationRangeHighValue == null || observationRangeLowValue == null)
			{
				return false;
			}

			// 基準値
			if(
					!observationRangeHighValue.isEmpty() && !observationRangeLowValue.isEmpty() &&
					!observationRangeHighUnit.isEmpty() && !observationRangeLowUnit.isEmpty()
					)
			{
				if(
						observationRangeLowValue == null ||
						observationRangeLowUnit == null ||
						observationRangeHighValue == null ||
						observationRangeHighUnit == null
				)
				{
					return false;
				}
			}
		}else{
			// 健診未実施
			if(code == null)
			{
				return false;
			}
		}

		return true;
	}


	/**
	 * @param Value 項目コード
	 */
	public void setCode(String Value)
	{
		code = Value;
	}
	private String code = null;

	/**
	 * @param Value 項目コードOID
	 */
	public void setCodeSystem(String Value)
	{
		codeSystem = Value;
	}
	private String codeSystem = null;

	// add s.inoue 2012/09/04
	/**
	 * 検査項目コード表示名
	 */
	private String kensanengapi = null;
	public String getKensanengapi() {
		return kensanengapi;
	}

	public void setKensanengapi(String pkensanengapi) {
		kensanengapi = pkensanengapi;
	}

	/* Added 2008/04/06 若月  */
	/* --------------------------------------------------- */
	/**
	 * 検査項目コード表示名
	 */
	private String codeDisplayName = null;
	public String getCodeDisplayName() {
		return codeDisplayName;
	}

	public void setCodeDisplayName(String name) {
		codeDisplayName = name;
	}
	/* --------------------------------------------------- */

	/* Added 2008/04/06 若月  */
	/* --------------------------------------------------- */
	private int xmlItemSeqNo = -1;

	public int getXmlItemSeqNo() {
		return xmlItemSeqNo;
	}

	public void setXmlItemSeqNo(int xmlItemSeqNo) {
		this.xmlItemSeqNo = xmlItemSeqNo;
	}
	/* --------------------------------------------------- */

	/**
	 * @param Value 検査パターン
	 */
	public void setPattern(String Value)
	{
		if(Value.trim().equals("1"))
		{
			pattern = 1;
		}
		if(Value.trim().equals("2"))
		{
			pattern = 2;
		}
	}
	private int pattern = -1;

	/**
	 * @param value 時間情報（分単位）
	 * 入力されている場合は使用
	 */
	public void setEffectiveTime(String value)
	{
		effectiveTime = value;
	}
	private String effectiveTime = null;

	/**
	 * @param value 検査結果のデータ型
	 */
	public void setDataType(String value)
	{
		dataType = value.trim();
	}
	private String dataType;

	/**
	 * @param value 結果の値
	 * 全ての結果はここに入れてください
	 */
	public void setResult(String value)
	{
		result = value;
	}
	private String result = null;

	/**
	 * @param value 単位コード
	 * パターン1の場合に使用
	 */
	public void setResultUnit(String value)
	{
		unit = value.trim();
	}
	private String unit = null;

	/**
	 * @param Value 検査結果コードOID
	 * パターン2でタイプがCDの場合に使用
	 *
	 */
	public void setResultCode(String Value)
	{
		resultCode = Value;
	}
	private String resultCode = null;

	/**
	 * @param Value 項目コード表示名
	 * パターン2でタイプがCDの場合に使用
	 */
	public void setItemCodeDisplayName(String Value)
	{
		itemCodeDisplayName = Value;
	}
	private String itemCodeDisplayName = null;

	/**
	 * @param Value 結果の解釈コード
	 * 基準値が設定されている場合に使用
	 */
	public void setInterpretationCode(String Value)
	{
		interpretationCode = Value;
	}
	private String interpretationCode = null;

	/**
	 * @param Value 検査方法コード
	 * 入力されている場合は使用
	 */
	public void setMethodCode(String Value)
	{
		methodCode = Value;
	}
	public String methodCode = null;

	/**
	 * @param Value 結果を記録した医師の名前
	 * 入力されている場合は使用
	 */
	public void setAuthor(String Value)
	{
		author = Value;
	}
	private String author = null;

	/**
	 * @param Value 基準値下限値閾値
	 */
	public void setObservationRangeLowValue(String Value)
	{
		observationRangeLowValue = Value;
	}
	private String observationRangeLowValue = null;

	/**
	 * @param Value 基準値下限値単位
	 */
	public void setObservationRangeLowUnit(String Value)
	{
		observationRangeLowUnit = Value;
	}
	private String observationRangeLowUnit = null;

	/**
	 * @param Value 基準値上限値閾値
	 */
	public void setObservationRangeHighValue(String Value)
	{
		observationRangeHighValue = Value;
	}
	private String observationRangeHighValue = null;

	/**
	 * @param Value 基準値上限値単位
	 */
	public void setObservationRangeHighUnit(String Value)
	{
		observationRangeHighUnit = Value;
	}
	private String observationRangeHighUnit = null;

// 一連検査グループに関する項目
	/**
	 * @param Value 一連検査グループのコード（RSON、COMPなど）
	 */
	public void setEntryCode(String Value)
	{
		EntryCode = Value;
	}
	public String getEntryCode()
	{
		return EntryCode;
	}
	private String EntryCode = null;

	/**
	 * @param value 一連検査グループのタイプ
	 */
	public void setEntryType(String value)
	{
		entryType = value;
	}
	public String getEntryType()
	{
		return entryType;
	}
	private String entryType = null;

// 検査の種類の指定
	public void setKensaType(String value)
	{
		kensaType = value;
	}
	public String getKensaType()
	{
		return kensaType;
	}
	public String kensaType = null;

	// add start s.inoue 20080909
	//	 実施区分
	public void setJISI_KBN(String value)
	{
		JISI_KBN = value;
	}
	public String getJISI_KBN()
	{
		return JISI_KBN;
	}
	public String JISI_KBN = null;
	// add end s.inoue 20080909

	/* Added 2008/05/10 若月  */
	/* --------------------------------------------------- */
	/**
	 * @param Value 入力下限値閾値
	 */
	public void setInputRangeLowValue(String Value)
	{
		inputRangeLowValue = Value;
	}
	private String inputRangeLowValue = null;

	/**
	 * @param Value 入力上限値閾値
	 */
	public void setInputRangeHighValue(String Value)
	{
		inputRangeHighValue = Value;
	}
	private String inputRangeHighValue = null;
	/* --------------------------------------------------- */

	/* Added 2008/06/08 若月  */
	/* --------------------------------------------------- */
	public String getCode() {
		return code;
	}
	public String getResult() {
		return result;
	}
	/* --------------------------------------------------- */
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


