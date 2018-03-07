package jp.or.med.orca.jma_tokutei.common.hl7.common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class Utility {
	/**
	 * 郵便番号を「###-####」という形式に出力する
	 * @param Value 適当な形式の数字
	 */
	public static String ConvertPostalCode(String Value)
	{
		String Ret = "";

		// 4文字目にハイフンが入っているかで処理を振り分け
		if(Value.charAt(3) == '-')
		{
			Ret = Value;
		}else{
			String Left = Value.substring(0, 3);
			String Right = Value.substring(3, 7);

			Ret = Left + "-" + Right;
		}

		return Ret;
	}

	/**
	 * カレンダークラスから、「yyyyMMdd」形式の日付を出力する
	 * @param Value カレンダークラス
	 * @return 日付の文字列
	 */
	public static String GetTimeString(Calendar Value)
	{
		// 1月分多くなる問題を丸める
		Value.set(Calendar.MONTH, Value.get(Calendar.MONTH) - 1);
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String Temp = format.format(Value.getTime());

		return Temp;
	}

	/**
	 * 入力した値に対して、必要桁数分左から0で埋める
	 * @param Value 値
	 * @param Figure 桁数
	 * @return 0で埋めた文字列
	 */
	public static String FillZero(long Value,int Figure)
	{
		DecimalFormat df = new DecimalFormat();
		String FigurePattern = "";

		// 必要桁分のパターンを生成
		for(int i = 0 ; i < Figure ; i++)
		{
			FigurePattern += "0";
		}

		df.applyLocalizedPattern(FigurePattern);
		return df.format(Value);
	}

	/* Added 2008/04/04 若月  */
	/* --------------------------------------------------- */
	public static String FillZero(String Value, int digit) {

		String valueString = "";
		String formattedValue = null;

		if (Value != null && ! Value.isEmpty()) {
			valueString = Value.trim();

			long valueLong = 0;
			if (Value != null && ! Value.isEmpty()) {
				valueLong = Long.parseLong(valueString);
			}

			formattedValue = Utility.FillZero(valueLong, digit);
		}
		else {
			for (int i = 0; i < digit; i++) {
				formattedValue += "0";
			}
		}

		return formattedValue;
	}
	/* --------------------------------------------------- */

	/**
	 * @return 現在の時間をHL7で使用するフォーマットで取得する
	 */
	public static String NowDate()
	{
		// 日付の取得
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
	}

	/**
	 * 数字を全角に変換する
	 * @param str 数字を含んだ文字列
	 * @return 文字列
	 */
	public static String NumberUpper(String str)
	{
		StringBuffer Ret = new StringBuffer(str);
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if ('0' <= c && c <= '9')
			{

				Ret.setCharAt(i, (char)(c - '0' + '０'));
			}
		}
		return Ret.toString();
	}

	/**
	 * 数字を半角に変換する
	 * @param str 数字を含んだ文字列
	 * @return 文字列
	 */
	public static String NumberLower(String str)
	{
		StringBuffer Ret = new StringBuffer(str);
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			if ('０' <= c && c <= '９')
			{

				Ret.setCharAt(i, (char)(c - '０' + '0'));
			}
		}
		return Ret.toString();
	}

	/**
	 * @param Value ###.### 形式の数字
	 * @return ###### 形式の数字
	 * パーセントの表記を変更する
	 */
	public static String getPercentFormat(double Value)
	{
		Value = Value * 1000;
		return Utility.FillZero((long)Value, 6);
	}

	/**
	 * スキーマを元にXMLファイルを検証する
	 * @param XmlFileName XMLファイル
	 * @param SchemaFileName スキーマのファイル
	 */
	public static void checkXmlSchema(String XmlFileName,String SchemaFileName)
	{
		// XMLスキーマの検証
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	        factory.setValidating(true);
	        factory.setNamespaceAware(true);
	        factory.setAttribute(
	        		"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
	        		"http://www.w3.org/2001/XMLSchema");
	        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
	        		SchemaFileName);
	        factory.setAttribute("http://apache.org/xml/features/validation/schema",true);

            DocumentBuilder builder = factory.newDocumentBuilder();
	        builder.setErrorHandler(new MyHandler(XmlFileName));

	        builder.parse(XmlFileName);
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

