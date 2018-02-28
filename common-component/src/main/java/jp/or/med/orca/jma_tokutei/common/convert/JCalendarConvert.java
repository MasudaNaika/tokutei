package jp.or.med.orca.jma_tokutei.common.convert;

import java.io  .*;
import java.util.*;
import java.text.*;
import java.util.regex.*;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

// ----------------------------------------------------------------------------
/**
 * class::name JCalendarConvert
 *
 * class::expl 暦変換クラス
 */
// ----------------------------------------------------------------------------
public class JCalendarConvert{
	/* Modified 2008/05/05 若月  */
	/* --------------------------------------------------- */
//	private static final String YEAR_PATTERN1 = "^(M|T|S|H|明治|大正|昭和|平成|[1-4])([0-9]{1,2}年|[0-9]{1,2}\\.|[0-9]{1,2}/)([0-9]{1,2}月|[0-9]{1,2}\\.|[0-9]{1,2}/)([0-9]+日?)";
//	private static final String YEAR_PATTERN2 = "^(M|T|S|H|明治|大正|昭和|平成|[1-4])([0-9]+)";
	/* --------------------------------------------------- */
	private static final String YEAR_PATTERN1 = "^(M|T|S|H|m|t|s|h|明治|大正|昭和|平成|[1-4])([0-9]{1,2}年|[0-9]{1,2}\\.|[0-9]{1,2}/)([0-9]{1,2}月|[0-9]{1,2}\\.|[0-9]{1,2}/)([0-9]+日?)";
	private static final String YEAR_PATTERN2 = "^(M|T|S|H|m|t|s|h|明治|大正|昭和|平成|[1-4])([0-9]+)";
	/* --------------------------------------------------- */

	enum CALENDAR_TYPE	{
		MEIGI,    // 明治
		TAISYO,   // 大正
		SYOWA,    // 昭和
		HEISEI    // 平成
	};

	/**
	 * 歴データ型
	 */
	protected class JCalenderDate	{
		protected int m_iYear = 0;
		protected int m_iMon  = 0;
		protected int m_iDay  = 0;

		protected CALENDAR_TYPE m_enumCalType = null;

		/**
		 * コンストラクタ
		 *
		 * @param 歴
		 * @param 日付
		 *
		 * @return none
		 */
		public JCalenderDate( CALENDAR_TYPE enumType, int iYear, int iMon, int iDay ){
			m_iYear = iYear;
			m_iMon  = iMon;
			m_iDay  = iDay;

			m_enumCalType = enumType;
		}

		/**
		 * 日付取得
		 *
		 * @param none
		 *
		 * @return String
		 */
		public String getDate()	{
			DecimalFormat formatYear = new DecimalFormat("0000");
			DecimalFormat formatMon  = new DecimalFormat("00");
			DecimalFormat formatDay  = new DecimalFormat("00");

			return new String( formatYear.format(m_iYear) + formatMon.format(m_iMon) + formatDay.format(m_iDay) );
		}
	}

	/**
	 * 和暦か西暦を判断
	 * @param 暦文字列
	 */
	public static boolean JCorAD( String date )	{
		boolean retCorAD = false;

		JCalendarConvert converter = new JCalendarConvert();

		// パース処理
		JCalenderDate calender = converter.CalendarParser( date );

		// 和暦
		if( calender.m_enumCalType != null ){
			switch (calender.m_enumCalType) {
			case MEIGI:retCorAD = true;
			case TAISYO:retCorAD = true;
			case SYOWA:retCorAD = true;
			case HEISEI:retCorAD = true;
			default:
				break;
			}
		}else{
			retCorAD = false;
		}

		return retCorAD;
	}
// edit s.inoue 2009/10/27
	/**
	 * 西暦妥当性チェック
	 *
	 * @param 暦文字列,検証西暦(true 始め false 終わり),完全一致
	 * @return 妥当性 正:日付 誤:null
	 */
	public static String ADvalidate( String strDate,boolean startflg ,boolean perfectflg)	{

		String year  = "0";String month  = "0";String day  = "0";

		// 空値を許可する
		if ( strDate.isEmpty() )
			return strDate;

		// 完全一致の場合
		if(perfectflg){
			if( strDate.length() != 8)
				return null;
		}else{
			if( strDate.length() != 8 && strDate.length() != 6 && strDate.length() != 4)
				return null;
		}

		if( !JValidate.isNumber(strDate) )
			return null;

		// 年度計算
		year = strDate.substring(0,4);

		// 月計算  6 and 8 or 4
		if (strDate.length() == 6 || strDate.length() == 8){
			// calendarクラスの月は0始まりなので予め-1した後に計算する
			month = String.valueOf(Integer.parseInt(strDate.substring(4,6))-1);
		}else{
			if (startflg){
				month="0";
			}else{
				month="11";
			}
		}

		// 日付計算 8 or 4 and 6(1 or 末日)
		if (strDate.length() == 8){
			day = strDate.substring(6,8);
		}else{
			if (startflg){
				day="1";
			}else{
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.YEAR, Integer.valueOf(year));
				cal.set(Calendar.MONTH, Integer.valueOf(month));
				day = String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
		}

		Calendar c1 = Calendar.getInstance();
	    c1.set(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(day));
	    c1.setLenient(false);

	    try{
	    	Date d = c1.getTime();
	    }catch(IllegalArgumentException a){
	    	return null;
	    }
	    // 妥当性が通れば、戻り値には+1で返す
	    month = String.valueOf(Integer.valueOf(month)+1);

	    // 1桁の場合、頭ゼロ埋
		if (month.length()==1){
			month = "0".concat(month);
        }
        if (day.length()==1){
        	day = "0".concat(day);
        }

	    return 	String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
	}

	/**
	 * 和暦西暦変換 ( Japanese calender to A.D. )
	 * @param 暦文字列
	 * @return 西暦
	 * @return 空値 : unknown calender format.
	 */
	public static String JCtoAD( String date )	{
		JCalendarConvert converter = new JCalendarConvert();

		// パース処理
		JCalenderDate calender = converter.CalendarParser( date );

		if( calender == null )		{
			return null;
		}

		// 和暦
		if( calender.m_enumCalType != null ){
			switch (calender.m_enumCalType) {
			case MEIGI:
				// 明治４５年対策
				if( calender.m_iYear > 45){
					return null;
				}
				else if( calender.m_iYear == 45 && calender.m_iMon == 7 && calender.m_iDay > 30 ){
					return null;
				}
				// 明治０１年対策
				else if( calender.m_iYear == 1 && calender.m_iMon == 9 && calender.m_iDay < 8 ){
					return null;
				}

				// 西暦変換
				calender.m_iYear += 1867;

				break;

			case TAISYO:
				// 大正１５年対策
				if( calender.m_iYear > 15){
					return null;
				}
				else if( calender.m_iYear == 15 && calender.m_iMon == 12 && calender.m_iDay > 25 ){
					return null;
				}
				// 大正０１年対策
				else if( calender.m_iYear == 1 && calender.m_iMon == 7 && calender.m_iDay < 30 ){
					return null;
				}

				// 西暦変換
				calender.m_iYear += 1911;

				break;

			case SYOWA:
				// 昭和６５年対策
				if( calender.m_iYear >= 65 ){
					return null;
				}

				// 昭和０１年対策
				if( calender.m_iYear == 1  ){
					if( calender.m_iMon < 12 ){
						return null;
					}
					else {
						if( calender.m_iDay < 25 ){
							return null;
						}
					}
				}

				// 昭和６４年対策
				if( calender.m_iYear == 64 ){
					if( calender.m_iMon > 1 ){
						return null;
					}
					else{
						if( calender.m_iDay > 7 ){
							return null;
						}
					}
				}

				// 西暦変換
				calender.m_iYear += 1925;
				break;

			case HEISEI:
				// 平成０１年対策
				if( calender.m_iYear == 1 && calender.m_iMon == 1 && calender.m_iDay <= 7 ){
					return null;
				}

				// 西暦変換
				calender.m_iYear += 1988;

				break;
			default:
				break;
			}
			/* --------------------------------------------------- */
		}
		// 西暦
		else{
			// 短縮西暦
			if( calender.m_iYear < 100 ){
				if( calender.m_iYear < Calendar.getInstance().get(Calendar.YEAR) - 2000 ){
					calender.m_iYear += 2000;
				}
				else{
					calender.m_iYear += 1900;
				}
			}
		}

		// 値チェック
		if( calender.m_iMon <= 0 || calender.m_iMon >= 13 ){
			return null;
		}

		int iMaxDay = converter.getDayCount( calender.m_iYear, calender.m_iMon );

		if( calender.m_iYear <= 0 || calender.m_iYear >= 3000   ||
			calender.m_iDay  <= 0 || calender.m_iDay  > iMaxDay  ){
			return null;
		}

		return calender.getDate();
	}

//	/* Added 2008/05/08 若月  */
//	/* --------------------------------------------------- */
//	public static String ADtoJC( String date )
//
//	{
//		return null;
//	}
//	/* --------------------------------------------------- */

	/**
	 * パーサ
	 *
	 * @param 日付文字列
	 *
	 * @return JCalenderDate
	 */
	private JCalenderDate CalendarParser( String date ){
		int iYear = 0;
		int iMon  = 0;
		int iDay  = 0;

		CALENDAR_TYPE enumCalType = null;

		// 全角半角変換
		date = digitFulltoHalf( date );

		// 和暦パターン
		/* Modified 2008/05/05 若月  */
		/* --------------------------------------------------- */
//		Pattern JCpattern = Pattern.compile( "^(M|T|S|H|明治|大正|昭和|平成|[1-4])([0-9]{1,2}年|[0-9]{1,2}\\.|[0-9]{1,2}/)([0-9]{1,2}月|[0-9]{1,2}\\.|[0-9]{1,2}/)([0-9]+日?)" );
		/* --------------------------------------------------- */
		Pattern JCpattern = Pattern.compile( YEAR_PATTERN1 );
		/* --------------------------------------------------- */

		Matcher matcher = JCpattern.matcher( date );

		if( matcher.lookingAt() ){
			matcher.reset();
			matcher.find ();
			String nengo = matcher.group(1);

			// 年号取得
			/* Modified 2008/05/05 若月 */
			/* --------------------------------------------------- */
// if ( JCmatcher.group(1).equals("1") || JCmatcher.group(1).equals("M") ||
// JCmatcher.group(1).equals("明治") )
// {
// enumCalType = CALENDAR_TYPE.MEIGI;
// }
// else if( JCmatcher.group(1).equals("2") || JCmatcher.group(1).equals("T") ||
// JCmatcher.group(1).equals("大正") )
// {
// enumCalType = CALENDAR_TYPE.TAISYO;
// }
// else if( JCmatcher.group(1).equals("3") || JCmatcher.group(1).equals("S") ||
// JCmatcher.group(1).equals("昭和") )
// {
// enumCalType = CALENDAR_TYPE.SYOWA;
// }
// else if( JCmatcher.group(1).equals("4") || JCmatcher.group(1).equals("H") ||
// JCmatcher.group(1).equals("平成") )
// {
// enumCalType = CALENDAR_TYPE.HEISEI;
// }
			/* --------------------------------------------------- */

//			/* Modified 2008/05/05 若月  */
//			/* --------------------------------------------------- */
//			if     ( nengo.equals("1") || nengo.equalsIgnoreCase("M") || nengo.equals("明治") ){
//				enumCalType = CALENDAR_TYPE.MEIGI;
//			}
//			else if( nengo.equals("2") || nengo.equalsIgnoreCase("T") || nengo.equals("大正") ){
//				enumCalType = CALENDAR_TYPE.TAISYO;
//			}
//			else if( nengo.equals("3") || nengo.equalsIgnoreCase("S") || nengo.equals("昭和") ){
//				enumCalType = CALENDAR_TYPE.SYOWA;
//			}
//			else if( nengo.equals("4") || nengo.equalsIgnoreCase("H") || nengo.equals("平成") ){
//				enumCalType = CALENDAR_TYPE.HEISEI;
//			}
//			/* --------------------------------------------------- */
//			else{
//				return null;
//			}

			enumCalType = this.setCalType(nengo);
			if (enumCalType == null) {
				return null;
			}

			// 年
			Pattern yearPattern = Pattern.compile( "([0-9]{1,2})(年|\\.|/)" );
			Matcher yearMatcher = yearPattern.matcher( matcher.group(2) );

			if( yearMatcher.lookingAt() ){
				yearMatcher.reset();
				yearMatcher.find ();

				iYear = Integer.valueOf( yearMatcher.group(1) );
			}
			else			{
				return null;
			}

			// 月
			Pattern monPattern = Pattern.compile( "([0-9]{1,2})(月|\\.|/)" );
			Matcher monMatcher = monPattern.matcher( matcher.group(3) );

			if( monMatcher.lookingAt() ){
				monMatcher.reset();
				monMatcher.find ();

				iMon = Integer.valueOf( monMatcher.group(1) );
			}
			else			{
				return null;
			}

			// 日
			Pattern dayPattern = Pattern.compile( "([0-9]+)日?" );
			Matcher dayMatcher = dayPattern.matcher( matcher.group(4) );

			if( dayMatcher.lookingAt() )
			{
				dayMatcher.reset();
				dayMatcher.find ();

				// ２桁チェック
				if     ( checkDigitPlace( dayMatcher.group(1), 2 ) )
				{
					iDay = Integer.valueOf( dayMatcher.group(1) );
				}
				// １桁チェック
				else if( checkDigitPlace( dayMatcher.group(1), 1 ) )
				{
					iDay = Integer.valueOf( dayMatcher.group(1) );
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}

			return new JCalenderDate( enumCalType, iYear, iMon, iDay );
		}
		else
		{
			// 和暦パターン
			/* Modified 2008/05/05 若月  */
			/* --------------------------------------------------- */
//			JCpattern = Pattern.compile( "^(M|T|S|H|明治|大正|昭和|平成|[1-4])([0-9]+)" );
			/* --------------------------------------------------- */
			JCpattern = Pattern.compile( YEAR_PATTERN2 );
			/* --------------------------------------------------- */

			matcher = JCpattern.matcher( date );

			if( matcher.lookingAt() )
			{
				matcher.reset();
				matcher.find ();
				String nengo = matcher.group(1);

				enumCalType = this.setCalType(nengo);
				if (enumCalType == null) {
					return null;
				}

				if( checkDigitPlace( matcher.group(2), 6 ) )
				{
					JCpattern = Pattern.compile( "([0-9]{2})([0-9]{2})([0-9]{2})" );
					matcher = JCpattern.matcher( matcher.group(2) );

					if( matcher.lookingAt() )
					{
						matcher.reset();
						matcher.find ();

						// 年
						iYear = Integer.valueOf( matcher.group(1) );

						// 月
						iMon  = Integer.valueOf( matcher.group(2) );

						// 日
						iDay  = Integer.valueOf( matcher.group(3) );

						return new JCalenderDate( enumCalType, iYear, iMon, iDay );
					}
				}
			}
		}

		// 西暦パターン
		JCpattern = Pattern.compile( "([0-9]+)" );
		matcher = JCpattern.matcher( date );

		if( matcher.lookingAt() )
		{
			matcher.reset();
			matcher.find ();
//			String nengo = matcher.group(1);

			// 年号取得
			enumCalType = null;

			// ８桁チェック
			if     ( checkDigitPlace( date, 8 ) )
			{
				JCpattern = Pattern.compile( "([0-9]{4})([0-9]{2})([0-9]{2})" );
				matcher = JCpattern.matcher( date );

				if( matcher.lookingAt() )
				{
					matcher.reset();
					matcher.find ();

					// 年
					iYear = Integer.valueOf( matcher.group(1) );

					// 月
					iMon  = Integer.valueOf( matcher.group(2) );

					// 日
					iDay  = Integer.valueOf( matcher.group(3) );

					return new JCalenderDate( enumCalType, iYear, iMon, iDay );
				}
			}
			// ６桁チェック
			else if( checkDigitPlace( date, 6 ) )
			{
				JCpattern = Pattern.compile( "([0-9]{2})([0-9]{2})([0-9]{2})" );
				matcher = JCpattern.matcher( date );

				if( matcher.lookingAt() )
				{
					matcher.reset();
					matcher.find ();

					// 年
					iYear = Integer.valueOf( matcher.group(1) );

					// 月
					iMon  = Integer.valueOf( matcher.group(2) );

					// 日
					iDay  = Integer.valueOf( matcher.group(3) );

					return new JCalenderDate( enumCalType, iYear, iMon, iDay );
				}
			}
		}

		return null;
	}

	private CALENDAR_TYPE setCalType(String nengo) {
		CALENDAR_TYPE enumCalType = null;

		// 年号取得
		if     ( nengo.equals("1") || nengo.equalsIgnoreCase("M") || nengo.equals("明治") )
		{
			enumCalType = CALENDAR_TYPE.MEIGI;
		}
		else if( nengo.equals("2") || nengo.equalsIgnoreCase("T") || nengo.equals("大正") )
		{
			enumCalType = CALENDAR_TYPE.TAISYO;
		}
		else if( nengo.equals("3") || nengo.equalsIgnoreCase("S") || nengo.equals("昭和") )
		{
			enumCalType = CALENDAR_TYPE.SYOWA;
		}
		else if( nengo.equals("4") || nengo.equalsIgnoreCase("H") || nengo.equals("平成") )
		{
			enumCalType = CALENDAR_TYPE.HEISEI;
		}
		return enumCalType;
	}

	/**
	 * 数値データの桁数チェック
	 *
	 * @param 測定桁数
	 *
	 * @return boolean
	 */
	private boolean checkDigitPlace( String str, int iPlace )
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
	 * 数値データの全角半角変換
	 *
	 * @param 変換元
	 *
	 * @return String
	 */
	private String digitFulltoHalf( String str )
	{
		StringBuffer strbuf = new StringBuffer( str );

		for( int i=0; i<strbuf.length(); ++i )
		{
			char ch = strbuf.charAt( i );

			if( ch >= '０' && ch <= '９' )
			{
				strbuf.setCharAt( i, (char)(ch - '０' + '0') );
			}
		}

		return strbuf.toString();
	}

	/**
	 * 日数取得（閏年対応）
	 *
	 * @param 年
	 * @param 月
	 *
	 * @return int
	 */
	private int getDayCount( int iYear, int iMon )
	{
		final int iMonthDays[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if( (((iYear % 4) == 0) && (((iYear % 100) != 0) || ((iYear % 400) == 0))) && iMon == 2 )
		{
			return 29;
		}

		return iMonthDays[ iMon ];
	}
}



// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

