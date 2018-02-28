package jp.or.med.orca.jma_tokutei.common.validate;

import java.io.*;
import java.math.BigDecimal;

import java.text.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;

/**
 * 文字列検証、変換用クラス
 * クラスコメント追加
 */
public class JValidate {
	public static final String USER_KENGEN_IPANUSER = "一般ユーザ";
	public static final String USER_KENGEN_KANRISYA = "管理者";

	private static org.apache.log4j.Logger logger = Logger.getLogger(JValidate.class);

	/* unicodeTO */
	public static String encodeUNICODEtoConvert(String str){
		 StringBuffer ret = new StringBuffer();
		 char c = 0x0000;
		 for(int i=0; i < str.length(); i++){
		  c = str.charAt(i);
		  switch(c){
		  case 0xff5e:    // 波線→長音
		   c = 0x30Fc;
		   break;
		  case 0xff0d:    // ハイフン
		   c = 0x30Fc;
		   break;
		  }
		  ret.append(c);
		 }
		 return ret.toString();
		}

	/**
	 * 指定された文字単数を除去する
	 * @param str		対象となる文字列
	 * @param target	除去したい文字単数の配列
	 * @return			指定文字除去後の文字列
	 */
	public static String eliminateCharacter(String str,char[] target)
	{
		String ret = "";
		boolean isEliminate = false;

		for(int i = 0;i < str.length(); i++ )
		{
			for(int j = 0;j < target.length;j++ )
			{
				if( str.charAt(i) == target[j] )
					isEliminate = true;
			}

			if( isEliminate )
			{
				isEliminate = false;
			}else{
				ret += str.charAt(i);
			}
		}

		return ret;
	}


	/**
	 * 文字列の先頭に０を追加して指定桁数にする
	 * @param str   ０埋め対象文字列
	 * @param digit ０で埋めた後の桁数
	 * @return
	 */
	public static String fillZero(String str,int digit)
	{
		StringBuffer ret = new StringBuffer();
		ret.append(str);

		if( digit < str.length())
		{
			return null;
		}

		for( int i = str.length() ; i < digit ; i++ )
		{
			ret.insert(0,"0");
		}

		return ret.toString();
	}

	/**
	 * 全角かどうかを調べる
	 */
	public static boolean isAllZenkaku(String str)
	{
		byte[] bytes = null;

		// 文字化け対策に、最初に「?」をチェックする
		for(int i = 0 ; i < str.length() ; i++)
		{
			if(str.charAt(i) == '?')
			{
				return false;
			}
		}

		try {
			bytes = str.getBytes("EUC-JP");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		for(int i = 0 ; i < bytes.length ; i++ )
		{
			// 一部の文字がEUC-JPに存在しないので、その時は処理を飛ばす
			if(bytes[i] == 63)
			{
				continue;
			}

			if(bytes[i] >= 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 半角かどうかを調べる。
	 * 対象文字列は UTF-8 でエンコードされていることを前提とする。
	 * UTF-8 以外の場合は、isAllHankaku(String str, String charSet) を
	 * 使用する。
	 */
	public static boolean isAllHankaku(String str)
	{
		return isAllHankaku(str, "UTF-8");
	}

	/* isAllHankaku(String str) を文字コードについて汎用化したメソッド。 */
	/**
	 * 半角かどうかを調べる
	 */
	public static boolean isAllHankaku(String str, String charSet)
	{
		byte[] bytes = null;
		try {
			bytes = str.getBytes(charSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		// 配列の要素数と引数の文字数が等しかったら、全部半角となる
		if(str.length() == bytes.length)
		{
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 数字かどうかを調べる
	 */
	public static boolean isNumber(String str)
	{
		for(int i = 0 ; i < str.length() ; i++)
		{
			char c = str.charAt(i);

			if(c < '0' || '9' < c)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 小数かどうかを調べる
	 */
	public static boolean isSyousu(String str)
	{
		boolean hasPoint = false;
		for(int i = 0 ; i < str.length() ; i++)
		{
			char c = str.charAt(i);
			if( c == '.' )
			{
				if( hasPoint )
				{
					return false;
				}else{
					hasPoint = true;
				}
			}

			if(!(('0' <= c && c <= '9') || c == '.' || c == '-' || c == '+' ))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * バイト数チェック
	 * @param 測定文字列
	 * @param バイト数
	 * @return boolean
	 */
    public static boolean checkByte(String strbt,int byt)
    {
        byte[] bytes = strbt.getBytes();
        int len = bytes.length;

        if (len != byt)
        {
            return false;
        }
        return true;
    }

	/**
	 * 最大値チェック
	 * @param 測定文字列
	 * @param バイト数
	 * @return boolean
	 */
	public static boolean checkMaxArea(String strbt,int max)
    {
        byte[] bytes = strbt.getBytes();
        int len = bytes.length;

        if (len > max)
        {
            return false;
        }
        return true;
    }

	/**
	 * 最大値チェック
	 * @param 測定文字列
	 * @param バイト数
	 * @return boolean
	 */
	public static boolean checkMaxLimit(String strbt,int max)
    {
        byte[] bytes = strbt.getBytes();
        int len = bytes.length;

        if (len > max)
        {
            return false;
        }
        return true;
    }

	/**
	 * 数値データの桁数チェック
	 * @param 測定桁数
	 * @return boolean
	 */
	public static boolean checkDigitNumber( String str, int iPlace )
	{
		int iCount = 0;

		for( int i=0; i<str.length(); ++i )
		{
			char ch = str.charAt( i );

			if( ch < '0' || ch > '9' )
			{
				return false;
			}
			else
			{
				iCount ++;
			}
		}

		if( iCount != iPlace )
		{
			return false;
		}

		return true;
	}

	/**
	 * ６桁パーセント表現から３桁パーセント表現へと変換する
	 */
	public static String to3DigitCode(String str)
	{
		String ret = "";

		int length = str.length();
		if( length <= 3 )
		{
			return "";
		}

		ret += str.substring(0, length - 3);
		ret += ".";
		ret += str.substring(length - 3, length - 1);

		return ret;
	}

	/*
	 * [未実装]intにキャストして.以下４桁以上を防ぐ
	 */
	public static String toSixDigitPercent(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		double value;
		String ret;

		if( !isSyousu(str) )
			return null;

		value = Double.valueOf(str);
		value *= 1000;

		ret = String.valueOf(value);
		ret = ret.substring(0, ret.length() - 2);

		//０埋めする前の桁数をチェック
		if( ret.length() > 6 )
			return null;

		//０で埋めて６ケタにする
		ret = JValidate.fillZero(ret,6);

		if( ret == null )
			return  null;

		return ret;
	}

	/*
	 * 日付フォーマットの検証
	 */
	public static String validateDate(String str,boolean startflg,boolean perfectflg)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		String temp;
		// edit s.inoue 2009/10/27
		temp = JCalendarConvert.ADvalidate(str,startflg,perfectflg);

		if( temp == null ){
			return null;
		}else{
			return temp;
		}
	}
	/*
	 * 日付フォーマットの検証(和暦、西暦対応)
	 */
	public static String validateDate(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		String temp;
		// edit s.inoue 2009/10/27
		temp = JCalendarConvert.JCtoAD(str);

		if( temp == null ){
			return null;
		}else{
			return temp;
		}
	}
	/**
	 * 西暦日付の妥当性チェックを行います。
	 * 指定した日付文字列（yyyy/MM/dd or yyyy-MM-dd）が
	 * カレンダーに存在するかどうかを返します。
	 * @param strDate チェック対象の文字列
	 * @return 存在する日付の場合true
	 */
	public static boolean validateCDate(String strDate) {
	    // 数値のみに変換
		strDate = strDate.replace('-', '/');
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
	    // 日付/時刻解析を厳密に行うかどうかを設定する。
	    format.setLenient(false);
	    try {
	        format.parse(strDate);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

	/*
	 * 電話番号フォーマットの検証を行う
	 * @return str 引数が電話番号として正当か空値
	 * @return null 引数が電話番号として不正かnull
	 */
	public static String validatePhoneNumber(String str)
	{
		char[] target = {'-'};

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		//ハイフンを除去する
		str = JValidate.eliminateCharacter(str, target);

		//数値のみかどうかのチェック
		if( !JValidate.isNumber(str) )
			return null;

		if( str.length() > 11)
		{
			return null;
		}

		return str;
	}


	/*
	 * ORCAIDの検証を行う
	 */
	public static String validateORCAID(String str)
	{
		String retValue = null;

		//空値を許可する
		if ( str.isEmpty() ){
			return str;
		}

		if ( str != null && ! str.isEmpty() ){
			if (str.getBytes().length <= 20) {
				retValue = str;
			}
		}

		return retValue;
	}

	/*
	 * 受診券整理番号の検証を行う
	 */
	public static String validateJyushinkenID(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		// edit ver2 s.inoue 2009/06/24
		if( !JValidate.isNumber(str))
		{
			return null;
		}

		if( str.length() != 11)
		{
			return null;
		}

		return str;
	}

	/*
	 * 名寄番号の検証を行う
	 */
	public static String validateNayoseNo(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isNumber(str))
		{
			return null;
		}

		if( str.length() != 12)
		{
			return null;
		}

		return str;
	}

	/*
	 * 受診券整理番号の検証を行う
	 */
	public static String validateJyushinkenIDforSearch(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 11)
		{
			return null;
		}

		return str;
	}

	/*
	 * 保険者番号の検証を行う
	 */
	public static String validateHokenjyaNumber(String str)
	{
		String ret;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 8 )
		{
			return null;
		}

		if( !JValidate.isNumber(str) )
		{
			return null;
		}

		ret = fillZero(str, 8);

		return ret;
	}

	/*
	 * 保険者名称の検証を行う
	 */
	public static String validateHokenjyaName(String str)
	{
		String retValue = null;

		if( str.getBytes().length <= 200 )
		{
			retValue = str;
		}

		return retValue;
	}

	// add s.inoue 2010/01/12
	/*
	 * 保険者履歴番号の検証を行う
	 */
	public static String validateHokenjyaHistoryNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return null;

		if( !JValidate.isNumber(str) )
		{
			return null;
		}

		return str;
	}
	/*
	 * 被保険者証等記号の検証を行う
	 */
	public static String validateHihokenjyaKigou(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		//全角以外を許可しない
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}

		if( str.getBytes().length > 40)
		{
			return null;
		}

		return str;
	}

	/*
	 * 被保険者証等番号の検証を行う
	 */
	public static String validateHihokenjyaNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		//全角以外を許可しない
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}

		if( str.getBytes().length > 40)
		{
			return null;
		}

		return str;
	}


	/*
	 * 氏名（漢字）の検証を行う
	 */
	public static String validateNameKanji(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !isAllZenkaku(str) )
			return null;
		if( str.getBytes().length > 100)
		{
			return null;
		}

		return str;
	}

	/*
	 * 氏名（カナ）の検証を行う
	 */
	public static String validateNameKana(String str)
	{
		// del s.inoue 2009/09/30
		// char target[] = {' ','　'};

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		//スペースを除去する
		// del s.inoue 2009/09/30
		// str = JValidate.eliminateCharacter(str,target);

		// add s.inoue 2010/10/29
		// 文字送り全角チェック
		if ( !JZenkakuKatakanaToHankakuKatakana.isWideChar(str))
			return null;

		if( !isAllZenkaku(str) )
			return null;

		if( str.getBytes().length > 100 )
		{
			return null;
		}

		return str;
	}

	/*
	 * 氏名（通称）の検証を行う
	 */
	public static String validateNameTsusyou(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 100)
		{
			return null;
		}

		return str;
	}

	/*
	 * 性別の検証を行う
	 */
	public static String validateSex(String str)
	{
		String temp = null;

		if ( str.isEmpty() )
			return str;

		if( str.equals("1") )
		{
			return str;
		}

		if(str.equals("2") )
		{
			return str;
		}
		return temp;
	}

	/*
	 * 生年月日の検証
	 */
	public static String validateSendSeinengapi(String str)
	{
		boolean JorAD = false;

		try {
			//空値を許可する
			if ( str.isEmpty() )
				return null;

			String temp = "";

			JorAD = JCalendarConvert.JCorAD(str);

			// 再変換処理(和暦→西暦)
			if (JorAD){
				str = JCalendarConvert.JCtoAD(str);
				if( str == null ){
					return null;
				}
			}

			if( !isNumber(str) )
				return null;

			if ( str.length() != 8 )
				return null;

			if (!validateCDate(str))
				return null;
		}catch(Exception ex){
			return null;
		}
		return str;
	}

	/*
	 * 郵便番号の検証を行う
	 */
	public static String validateZipcode(String str)
	{
		String ret;
		char[] target = { '-' };

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		// add s.inoue 2012/11/26
		//空値を許可する
		if ( str.trim().isEmpty() )
			return str;

		//ハイフンを除去する
		ret = JValidate.eliminateCharacter(str, target);

		//数字のみかどうかチェックする
		if( !JValidate.isNumber(ret) )
		{
			return null;
		}

		if ( ret.length() != 7 )
		{
			return null;
		}

		return ret;
	}

	/*
	 * 住所の検証を行う
	 */
	public static String validateAddress(String str)
	{
		char target[] = {' ','　'};
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		//スペースを除去する
		str = JValidate.eliminateCharacter(str,target);

		//全角のみかどうかチェックを行う
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}
		if( str.getBytes().length > 200 )
		{
			return null;
		}

		return str;
	}

	/*
	 * 地番方書の検証を行う
	 */
	public static String validateChiban(String str)
	{
		char target[] = {' ','　'};
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		//スペースを除去する
		str = JValidate.eliminateCharacter(str,target);

		//全角のみかどうかチェックを行う
		if( !JValidate.isAllZenkaku(str) )
		{
			return null;
		}

		if( str.getBytes().length > 200 )
		{
			return null;
		}

		return str;
	}

	/*
	 * 健診機関住所の検証を行う
	 */
	public static String validateKikanAddress(String str){

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		/* 全角スペースを削除する。 */
		str = str.replaceAll("　", "");

		// edit s.inoue 2009/09/24
		//全角のみかどうかチェックを行う
		if( !JValidate.isAllZenkaku(str) )	{
			return null;
		}

		return str;
	}

	/*
	 * 健診機関地番方書の検証を行う
	 */
	public static String validateKikanChiban(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		/* 全角スペースを削除する。 */
		str = str.replaceAll("　", "");

		if( !isAllZenkaku(str) )
			return null;

		return str;
	}

	/*
	 * 健診機関住所と地番方書の検証を行う
	 */
	public static boolean validateKikanAddressAndChiban(String address, String chiban)
	{
		boolean retValue = false;

		String addressForValidate = null;
		String chibanForValidate = null;

		/*
		 *  null の場合は、空文字に置き換える。
		 * 全角スペースを削除する。
		 */
		if (address == null) {
			addressForValidate = "";
		}
		else {
			addressForValidate = address.replaceAll("　", "");
		}

		if (chiban == null) {
			chibanForValidate = "";
		}
		else {
			chibanForValidate = chiban.replaceAll("　", "");
		}

		/* バイト数が 80 以内なら OK（） */
		int byteSize = addressForValidate.getBytes().length + chibanForValidate.getBytes().length;
		if (byteSize <= 80) {
			retValue = true;
		}

		return retValue;
	}

	/*
	 * EMAILの検証を行う
	 */
	public static String validateEMAIL(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 100 )
		{
			return null;
		}

		boolean hasDot = false;
		boolean hasAt = false;

		for(int i = 0 ; i < str.length() ; i++)
		{
			char c = str.charAt(i);

			if(!(('0' <= c && c <= '9') || ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') ||
					c == '@' || c == '.' || c == '-' || c == '_'))
			{
				return null;
			}

			if(c == '@')
			{
				hasAt = true;
			}
			if(hasAt == true && c =='.')
			{
				hasDot = true;
			}
		};

		if(hasAt == true && hasDot == true)
		{
			return str;
		}
		return null;
	}

	/*
	 * 契約取りまとめ機関名の検証を行う
	 */
	public static String validateTorimatomeName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 100 )
		{
			return null;
		}

		return str;
	}

	/*
	 * 支払代行機関番号の検証を行う
	 */
	public static String validateDaikouNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() != 8)
		{
			return null;
		}

		if( !JValidate.isNumber(str) )
		{
			return null;
		}

		return str;
	}

	/*
	 * 支払代行機関名称の検証を行う
	 */
	public static String validateDaikouName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
		{
			return null;
		}

		return str;
	}

	/*
	 * 窓口負担金額(HL7用)
	 */
	public static String validateMadoguchi_HL7(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return null;

		if( !isNumber(str) )
			return null;

		if( str.length() != 6 )
		{
			return null;
		}
		return str;
	}

	/*
	 * 窓口負担金額
	 */
	public static String validateMadoguchiAmount(String str)
	{
		String ret;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if( str.length() > 6 )
		{
			return null;
		}

		ret = JValidate.fillZero(str, 6);

		return ret;
	}

	/*
	 * 窓口負担割合の検証
	 */
	public static String validateMadoguchiPercent(String str)
	{
		return toSixDigitPercent(str);
	}

	/*
	 * 窓口負担合計金額の検証
	 */
	public static String validateMadoguchiAmountTotal(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if ( str.length() > 9 )
		{
			return null;
		}

		return str;
	}

	/*
	 * その他の健診の負担額の検証
	 */
	public static String validateMadoguchiSonota(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if ( str.length() > 9 )
		{
			return null;
		}

		return str;
	}

	/*
	 * ユーザー名の検証
	 */
	public static String validateUserName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 20 )
			return null;

		return str;
	}

	/*
	 * パスワードの検証
	 */
	public static String validatePassword(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if ( !isAllHankaku(str) ){
			return null;
		}

		if( str.length() > 20 )
			return null;

		return str;
	}

	/*
	 * 健診機関番号の検証
	 */
	public static String validateKikanNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() != 10 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * 送付元機関の検証
	 * 数値、１０ケタ以内として実装（０詰めなし）
	 */
	public static String validateSendSourceKikan(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return null;

		if( str.getBytes().length > 10 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * 医療機関名称の検証
	 */
	public static String validateKikanName(String str)
	{
		String retValue = null;

		if ( ! str.isEmpty() ){
			String checkStr = str.replaceAll("　", "");

			/* 全角のみかどうかチェックを行う */
			if( JValidate.isAllZenkaku(checkStr) && checkStr.length() <= 20)	{
				retValue = str;
			}
		}

		return retValue;
	}

	/*
	 * ユーザー権限の検証
	 */
	public static String validateUserKengen(String str)
	{
		String ret = "";

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals(USER_KENGEN_KANRISYA))
		{
			ret = "1";
			return ret;
		}

		if( str.equals(USER_KENGEN_IPANUSER))
		{
			ret = "2";
			return ret;
		}

		return null;
	}

	/*
	 * 医療保険者記号の記号
	 */
	public static String validateHokenjyaKigou(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 80 )
		{
			return null;
		}

		return str;
	}

	/*
	 * 給付割合（本人 or 家族・外来 or 入院）の検証
	 */
	public static String validateKyuuhuPercent(String str)
	{
		return toSixDigitPercent(str);
	}

	/*
	 * 委託料単価区分の検証
	 */
	public static String validateItakuKubun(String str)
	{
		String ret = null;

		// add s.inoue 2009/10/01
		if (str.indexOf(":", 0) > 0) {
			str  = str.substring(str.indexOf(":", 0)+1);
		}

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("個別") )
		{
			ret = "1";
		}
		if( str.equals("集団") )
		{
			ret = "2";
		}

		return ret;
	}

	// add s.inoue 2010/03/03
	/*
	 * 委託料単価区分の検証
	 */
	public static String validateItakuKubunCode(String str)
	{
		String ret = "";

		if( str.isEmpty() || str.equals("1") || str.equals("2") )
			ret = str;

		return ret;
	}
	/*
	 * 単価判定
	 * １(基本,詳細,追加) or ２(人間ドック) のみ
	 */
	public static String validateTankaHantei(String str)
	{
		if( str.equals("1") || str.equals("2") )
			return str;

		return null;
	}
	/*
	 * 各検査の単価の検証
	 */
	public static String validateKensaTanka(String str)
	{
		// edit s.inoue 2009/11/12
		// final String KTanka = "0";

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 9 )
		{
			return null;
		}

		if( !isNumber(str) )
		{
			return null;
		}

		return str;
	}

	/*
	 * 各検査単価の合計の検証
	 */
	public static String validateKensaTankaTotal(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 9 )
		{
			return null;
		}

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * 貧血、眼底、心電図検査コードの検証
	 */
	public static String validateKensaCode(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() != 1 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * パターンNoの検証
	 */
	public static String validatePatternNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		// eidt s.inoue 2012/07/04
		if( str.length() > 3  && !str.equals("9999"))
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * パターン名称の検証
	 */
	public static String validatePatternName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * 備考の検証
	 */
	public static String validateNote(String str)
	{
		// edit s.inoue 2011/03/10
		if ( str==null )
			return "";

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}


	/*
	 * 項目コードの検証
	 * 半角のみ
	 */
	public static String validateKensaKoumokuCode(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() != 17 )
			return null;

		if( !isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * 項目名称の検証
	 * 全ての文字
	 */
	public static String validateKensaKoumokuName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * 特定健診必須フラグの検証
	 * １ or ２ or 3 のみ
	 */
	public static String validateKenshinHisuFlag(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("1") || str.equals("2") || str.equals("3") )
			return str;

		return null;
	}

	/*
	 * 特定健診のsex検証
	 * １ or ２ or 3 のみ
	 */
	public static boolean validateSexFlag(String str)
	{
		boolean sexFlag = false;

		if( str.isEmpty() || str.equals("1") || str.equals("2") )
			sexFlag = true;

		return sexFlag;
	}
	/*
	 * 項目上下限、基準値範囲（男性）（女性）（共通）値の検証
	 * 小数関係検討中
	 */
	public static String validateKensaResultLimit(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 10 )
			return null;

		if( !isSyousu(str) )
			return null;

		return str;
	}

	/*
	 * 検査値単位の検証
	 */
	public static String validateKensaUnit(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 10 )
			return null;

		return str;
	}

	/*
	 * 検査値基準値範囲の検証
	 */
	public static String validateKensaKijyunHanni(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 20 )
			return null;

		return str;
	}

	/*
	 * XML検査方法コードの検証
	 */
	public static String validateKensaCd(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 10 )
			return null;

		return str;
	}

	/*
	 * 検査方法の検証
	 */
	public static String validateKensaHouhou(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * 検査センターコードの検証
	 */
	public static String validateKensaCenterCode(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 20 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * 検査センター名称の検証
	 */
	public static String validateKensaCenterName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.getBytes().length > 200 )
			return null;

		return str;
	}

	/*
	 * 検査センター独自項目コードの検証
	 */
	public static String validateKensaCenterKoumokuCode(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 5 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	// add s.inoue 2009/09/28
	public static String DecimalFormatValue(String limitVale,String format){
		//少数点以下の数字の数

		int lowNum = format.length() - format.indexOf(".");

		BigDecimal bd = new BigDecimal(limitVale);
		bd.setScale(lowNum, BigDecimal.ROUND_HALF_UP);

		DecimalFormat decimal = new DecimalFormat(format);
		String result = decimal.format(bd.doubleValue());

		if (result.startsWith(".")) {
			result =  "0" + result;
		}
		return result;
	}

	/*
	 * DecimalFormatに対応した文字のみ受け付け
	 */
	public static String validateKensaKekkaDecimal(String value,String format)
	{
		char[] target = {'.'};
		char ch;
		boolean pointFlag = false;
		int lowNum = 0;
		String result = null;

		//検査値の空値は許可する
		if ( value.isEmpty() )
			return value;

		//フォーマット指定子の空値は許可しない
		if ( format.isEmpty() )
			return null;

		//検査値が.と数値のみかどうかチェックする
		String tmp = JValidate.eliminateCharacter(value, target);
		if( !JValidate.isNumber(tmp) )
		{
			return null;
		}


		//フォーマット指定子が.と0と#のみかどうかのチェック
		for( int i = 0; i < format.length() ;i++ )
		{
			ch = format.charAt(i);

			//.が二回以上出現しないかどうか
			if( ch == '.' )
			{
				if( pointFlag == true)
				{
					return null;
				}
				pointFlag = true;
			}

			if( ch != '0' && ch != '.' && ch != '#' )
			{
				return null;
			}
		}

		//少数点以下の数字の数
		lowNum = format.length() - format.indexOf(".");

		BigDecimal bd = new BigDecimal(value);
		bd.setScale(lowNum, BigDecimal.ROUND_HALF_UP);

		DecimalFormat decimal = new DecimalFormat(format);
		result = decimal.format(bd.doubleValue());

		if (result.startsWith(".")) {
			result =  "0" + result;
		}

		return result;
	}

	/*
	 * 文字列で表わされる検査結果の検証
	 */
	public static String validateKensaKekkaText(String value,String format)
	{
		// edit s.inoue 2009/10/15
		if (value == null)
			return "";

		//空値を許可する
		if ( value.isEmpty() )
			return value;

		//フォーマット指定子の空値は許可しない
		if ( format.isEmpty() )
			return null;

		if( value.getBytes().length > Integer.valueOf(format) )
			return null;

		// edit s.inoue 2009/10/14
		if (!isAllZenkaku(value))
			return null;

		return value;
	}

	/*
	 * コード値で表わされる検査結果の検証
	 */
	public static String validateKensaKekkaCode(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isNumber(str) )
			return null;

		return str;
	}

	/*
	 * コメントの検証
	 */
	public static String validateComment(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;
		if( str.getBytes().length > 256 )
			return null;

		return str;
	}

	/*
	 * 検査結果のH/L検証
	 * 草案：H or L
	 * H or L 一桁
	 */
	public static String validateHLKubun(String str)
	{
		String temp = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("L") )
			temp = "1";

		if( str.equals("H") )
			temp = "2";

		if( str.equals("!") )
			temp = "3";

		return temp;
	}

	/*
	 * 実施区分の検証
	 * 草案：0 or 1 or 2
	 * 0 or 1 or 2 一桁
	 */
	public static String validateJisiKubun(String str)
	{
		// 空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("0") || str.equals("1") || str.equals("2") )
			return str;

		return null;
	}
	/* 実施区分 */
	public static String validateJisiKubunText(String str)
	{

		if( str.equals(JApplication.jishiKBNTable[0]) ||
				str.equals(JApplication.jishiKBNTable[1]) ||
					str.equals(JApplication.jishiKBNTable[2]) )
			// 1文字目を返却
			return str.substring(0, 1);

		return null;
	}

	/*
	 * 検査結果のH/L検証
	 * 草案：H or L
	 * H or L 一桁
	 */
	public static String validateKekkaTiKeitai(String str)
	{
		String temp = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("E") )
			temp = "1";

		if( str.equals("L") )
			temp = "2";

		if( str.equals("U") )
			temp = "3";

		return temp;
	}

	/*
	 * 結果値の検証
	 * 草案：
	 */
	public static boolean validateKekkaTi(String str)
	{
		boolean flgKekkaTi = false;

		if ( str.isEmpty() )
			flgKekkaTi=true;

		if( str.length() > 14 )
		{
			flgKekkaTi = true;
		}
		if( !JValidate.isAllHankaku(str,JApplication.CSV_CHARSET) )
		{
			flgKekkaTi = true;
		}
		return flgKekkaTi;

	}

	/*
	 * 判定結果区分の検証
	 */
	public static String validatehanteiKekka(String str)
	{
		String ret = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("未判定"))
		{
			ret = "";
		}
		if( str.equals("異常なし"))
		{
			ret = "1";
		}
		if( str.equals("軽度異常"))
		{
			ret = "2";
		}
		if( str.equals("要経過観察"))
		{
			ret = "3";
		}
		if( str.equals("異常"))
		{
			ret = "4";
		}
		if( str.equals("要精検"))
		{
			ret = "5";
		}

		return ret;
	}

	/*
	 * メタボ判定結果区分の検証
	 */
	public static String validateMetaboHanteiKubun(String str)
	{
		String ret = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;
		if( str.equals("未判定") )
			ret = "";
		if( str.equals("基準該当") )
			ret = "1";
		if( str.equals("予備群該当") )
			ret = "2";
		if( str.equals("非該当") )
			ret = "3";
		if( str.equals("判定不能") )
			ret = "4";

		return ret;
	}

	// add s.inoue 2012/07/04
    public static String validateMetaboHanteiKubunCode(String s)
    {
        String s1 = null;
        if(s.isEmpty())
            return s;
        int i = Integer.parseInt(s);

        // eidt s.inoue 2013/05/23
        if(0 == i){
        	s1 = "";
        } else if(0 < i && i <= 4){
        	s1 = String.valueOf(i);
        }
        return s1;
    }
    // add s.inoue 2012/07/04
    public static String validateHokenSidouLevelCode(String s)
    {
        String s1 = null;
        if(s.isEmpty())
            return s;
        int i = Integer.parseInt(s);
        // eidt s.inoue 2013/05/23
        if(0 == i){
        	s1 = "";
        } else if(0 < i && i <= 4){
        	s1 = String.valueOf(i);
        }
        return s1;
    }

	/*
	 * 保健指導レベルの検証
	 * 未判定、積極的支援、動機づけ支援、なし、判定不能の４種類
	 * それぞれ空値、１、２、３、４とコード値を出力
	 */
	public static String validateHokenSidouLevel(String str)
	{
		String ret = null;

		//空値を許可する
		if ( str.isEmpty() || str.equals("指定無し"))
			return "0";
		if( str.equals("未判定") )
			ret = "";
		if( str.equals("積極的支援") )
			ret = "1";
		if( str.equals("動機づけ支援") )
			ret = "2";
		if( str.equals("なし（情報提供）") )
			ret = "3";
		if( str.equals("判定不能") )
			ret = "4";

		return ret;
	}

	// add s.inoue 2010/10/25
	/*
	 * 請求区分コードの検証
	 * ０～５の数値のみ一桁
	 */
	public static String validateSeikyuKubunCode(String str)
	{
		String ret = null;

		if( str.equals("1") ||
				str.equals("2") ||
					str.equals("3") ||
						str.equals("4") ||
							str.equals("5"))
			ret = str;

		return ret;
	}

	/*
	 * 請求区分コードの検証
	 * ０～５の数値のみ一桁
	 */
	public static String validateSeikyuKubun(String str)
	{
		String ret = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("基本的な健診"))
			ret = "1";
		if( str.equals("基本的な健診＋詳細な健診"))
			ret = "2";
		if( str.equals("基本的な健診＋追加健診項目"))
			ret = "3";
		if( str.equals("基本的な健診＋詳細な健診＋追加健診項目"))
			ret = "4";
		if( str.equals("人間ドック"))
			ret = "5";

		return ret;
	}

	/*
	 * 請求金額の検証
	 */
	public static String validateSeikyuKingaku(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !isNumber(str) )
			return null;

		if( str.length() > 9 )
			return null;

		return str;
	}

	/*
	 * 特定健診実施種別の検証
	 */
	public static String validateTokuteiKenshinSyubetuKubun(String str)
	{
		String ret = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("特定健診機関又は特定保健指導機関から代行機関") )
			ret = "1";
		if( str.equals("代行機関から特定健診機関又は特定保健指導機関") )
			ret = "2";
		if( str.equals("代行機関から保険者") )
			ret = "3";
		if( str.equals("保険者から代行機関（未決済データの場合）") )
			ret = "4";
		if( str.equals("保険者から代行機関（決済済みデータの場合）") )
			ret = "5";
		if( str.equals("特定健診機関又は特定保健指導機関から保険者") )
			ret = "6";
		if( str.equals("保険者から特定健診機関又は特定保健指導機関") )
			ret = "7";
		if( str.equals("保険者から保険者"))
			ret = "8";
		if( str.equals("その他") )
			ret = "9";
		if( str.equals("保険者から国（支払基金）") )
			ret = "10";
		if( str.equals("代行機関から保険者へ確認依頼") )
			ret = "11";
		if( str.equals("予備") )
			ret = "12";
		if( str.equals("予備") )
			ret = "13";

		return ret;
	}

	/*
	 * 特定健診実施区分の検証
	 */
	public static String validateTokuteiKenshinJissiKubun(String str)
	{
		String ret = null;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("特定健診情報") )
			ret = "1";
		if( str.equals("特定保健指導情報") )
			ret = "2";
		if( str.equals("国（支払基金）への実施結果報告") )
			ret = "3";
		if( str.equals("他の健診結果受領分") )
			ret = "4";

		return ret;
	}


	/*
	 * 取り込みフォーマットの検証
	 * 草案：厚労省準拠フォーマット or 独自フォーマット
	 */
	public static String validateImportFormat(String str)
	{
		String ret;

		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.equals("厚労省準拠フォーマット") )
		{
			ret = "1";
			return ret;
		}
		if( str.equals("独自フォーマット") )
		{
			ret = "2";
			return ret;
		}

		return null;
	}

	/*
	 * ファイルパスの検証
	 */
	public static String validateFilePath(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		return str;
	}

	/*
	 * ORCAフラグの検証
	 */
	public static String validateORCAFlag(boolean flag)
	{
		String ret;

		if(flag)
		{
			ret = "1";
		}else{
			ret = "0";
		}

		return ret;
	}

	/*
	 * ORCA接続用IPアドレスの検証
	 */
	public static String validateIPAddress(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		return str;
	}

	/*
	 * 	ポート番号の検証
	 */
	public static String validatePortNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isNumber(str) )
			return null;

		if( Integer.valueOf(str) > 65535 || Integer.valueOf(str) <  0 )
			return null;

		return str;
	}

	/*
	 * ORCAデータベース名の検証
	 */
	public static String validateORCADBName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA接続用プロトコルの検証
	 */
	public static String validateORCAProtocol(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA接続用ユーザー名の検証
	 */
	public static String validateORCADBUserName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA接続用パスワードの検証
	 */
	public static String validateORCAPassword(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/* Added 2008/04/20 若月 日レセユーザ・パスワード */
	/* --------------------------------------------------- */
	/*
	 * ORCA日レセユーザー名の検証
	 */
	public static String validateORCANichireseUserName(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA日レセパスワードの検証
	 */
	public static String validateORCANichiresePassword(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCA接続用文字エンコードの検証
	 */
	public static String validateORCAEncord(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( !JValidate.isAllHankaku(str) )
			return null;

		return str;
	}

	/*
	 * ORCAバージョンの検証を行う
	 */
	public static String validateORCAVersion(String str){
		return str;
	}

	/*
	 * 年齢フォーマットの検証
	 */
	public static String validateAge(String str)
	{
		String retValue = null;

		if ( str != null && ( str.isEmpty() || JValidate.isNumber(str)) ){
			retValue = str;
		}

		return retValue;
	}

	public static String validateOrcaIdDigit(String str){
		String retValue = null;

		if( isNumber(str) ){
			retValue = str;
		}

		return retValue;
	}

	public static int validateOrcaIdDigit(int digit)
	{
		return digit;
	}

	/*
	 * 定型文種別の検証を行う
	 */
	public static String validateTeikeiType(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		if( str.length() > 2 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * 定型文Noの検証を行う
	 */
	public static String validateTeikeiNumber(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return str;

		// edit s.inoue 2010/05/06
		if( str.length() > 2 )
			return null;

		if( !isNumber(str) )
			return null;

		return str;
	}

	/*
	 * 定型文種別内容の検証
	 */
	public static String validateTeikeibunShubetu(String str)
	{
		//空値を許可する
		if ( str.isEmpty() )
			return null;
		if( str.getBytes().length > 256 )
			return null;

		return str;
	}

	/*
	 * 定型文内容の検証
	 */
	public static String validateTeikeibun(String str)
	{
		// edit s.inoue 2010/03/12 null→str
		//空値を許可する
		if ( str.isEmpty() )
			return str;
		if( str.getBytes().length > 256 )
			return null;

		return str;
	}

	/*
	 * 有効期限フラグ(0:無効 or 1:有効)の検証を行う
	 */
	public static String validateYukouFlg(String str)
	{
		String temp = null;

		if ( str.isEmpty() )
			return str;

		if( str.equals("0") )
		{
			return str;
		}

		if(str.equals("1") )
		{
			return str;
		}
		return temp;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

