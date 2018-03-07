package jp.or.med.orca.jma_tokutei.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;

import org.apache.log4j.Logger;

public class FiscalYearUtil {

	private static final String LATEST_YEAR = "0331";
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static final String DATE_JUN = "01";
	private static final String DATE_FEB = "02";
	private static final String DATE_MER = "03";

	private static org.apache.log4j.Logger logger = Logger.getLogger(FiscalYearUtil.class);
	/**
	 *  今年度取得
	 *    @param  キー
	 *    @return VALUE
	 */
	public static int getSystemYear()
	{
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);
		return thisYear;
	}

	/**
	* システム日付を返します。
	* @param format 戻り値で返す日付の書式を指定します。yyyymmddまたはyyyy/mm/ddです。
	* @return 引数で指定されたformatの書式でシステム日付を返します。
	*/
	public static String getSystemDate(String format) {
	  String sysDate = "";


	  Calendar cal = Calendar.getInstance();
	  int year = cal.get(Calendar.YEAR);
	  int month = cal.get(Calendar.MONTH) + 1;
	  int day = cal.get(Calendar.DATE);


	  if (format.equals("yyyy/mm/dd")) {
	    sysDate= new DecimalFormat("0000").format(year) + "/"
	       + new DecimalFormat("00").format(month) + "/"
	       + new DecimalFormat("00").format(day);
	  } else {
	    sysDate = new DecimalFormat("0000").format(year)
	        + new DecimalFormat("00").format(month)
	        + new DecimalFormat("00").format(day);
	  }

	  return sysDate;
	}


	/**
	 *  年度末年度取得
	 *    @return 年度末年度
	 */
	public static int getThisYear()
	{
		int thisYear =0;
		int tmpNenDo = 0;
		int tmpGetu = 0;

		DateFormat format = new SimpleDateFormat(DATE_FORMAT);

		try {

			tmpNenDo = Integer.parseInt(format.format(new Date()).substring(0,4));
			tmpGetu = Integer.parseInt(format.format(new Date()).substring(4,6));
			// 1～3月⇒ -1
			if (tmpGetu >=1 && tmpGetu <=3){
				tmpNenDo--;
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
		thisYear = tmpNenDo;

		return thisYear;
	}

	/**
	 *  年度末年度取得
	 *  @param 健診実施日 or 生年月日等
	 *  @return 年度
	 */
	public static int getThisYear(String nendoMatuDate)
	{
		int thisYear =0;
		int tmpNenDo = 0;
		int tmpGetu = 0;

		try {
			String splitDate =nendoMatuDate.substring(0, 4) +
			"/" + nendoMatuDate.substring(4, 6) +
			"/" +nendoMatuDate.substring(6, 8);

			DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		    Date kensinDt = DateFormat.getDateInstance().parse(splitDate);

			tmpNenDo = Integer.parseInt(format.format(kensinDt).substring(0,4));
			tmpGetu = Integer.parseInt(format.format(kensinDt).substring(4,6));
			// 1～3月⇒ -1
			if (tmpGetu >=1 && tmpGetu <=3){
				tmpNenDo--;
			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}
		thisYear = tmpNenDo;

		return thisYear;
	}

	/**
	 *  指定日付が今年度かどうか判断
	 *    @return 年度
	 */
	public static boolean getJugeThisYear( String dateValue )
	{
		int thisYear =0;
		int thisMonth =0;
		int tmpNenDo = 0;
		int tmpGetu = 0;
		boolean blnJugethisYear = false;

		try {

			DateFormat format = new SimpleDateFormat(DATE_FORMAT);

			if (dateValue != null){

				tmpNenDo = Integer.parseInt(dateValue.substring(0,4));
				tmpGetu = Integer.parseInt(dateValue.substring(4,6));
				thisYear = Integer.parseInt(format.format(new Date()).substring(0,4));
				// edit ver2 s.inoue 2009/08/19
				thisMonth = Integer.parseInt(format.format(new Date()).substring(4,6));

				// 1～3月⇒ -1
				if (tmpGetu >=1 && tmpGetu <=3){
					tmpNenDo--;
				}

				// edit ver2 s.inoue 2009/08/19
				// 1～3月⇒ -1
				if (thisMonth >=1 && thisMonth <=3){
					thisYear--;
				}

				if (thisYear == tmpNenDo){
					blnJugethisYear = true;
				}

			}
		}catch(Exception ex){
			logger.warn(ex.getMessage());
		}

		return blnJugethisYear;
	}

	// add ver2 s.inoue 2009/08/19
	/**
	 *  保発1118001号75歳の誕生日までに健診実施がある場合
	 *  動機付けの判定となる
	 *    @param  生年月日
	 *    @param  健診実施日
	 *    @return 判定対象かどうか
	 */
	public static boolean getJugeDate(String birthday ,String kensinJisiDate){

		boolean retTarget = false;

		// 生年月日と健診実施日を比較
		int intBirthday = Integer.parseInt(birthday.substring(4));
		int intJishiDate = Integer.parseInt(kensinJisiDate.substring(4));

		/* 健診実施日 < 生年月日 → 判定対象
		   健診実施日 >= 生年月日 → 判定対象外 */
		if ( intJishiDate < intBirthday ){
			retTarget = true;
		}

		return retTarget;
	}

	/**
	 *  年度末年齢取得
	 *    @param  生年月日
	 *    @param  健診実施日
	 *    @return 年度末年齢
	 */
	public static int getFiscalYear( String birthday ,String kensinJisiDate)
	{
		int Age =0;
		int birthDayInt = Integer.valueOf(birthday);
		int intYear = 10000;

		try {

			if (birthday == "")
				return 0;

			DateFormat format = new SimpleDateFormat(DATE_FORMAT);

		    // edit ver2 s.inoue 2009/06/24
			// 前のロジック
//		    String tmpGetu = format.format(new Date()).substring(4,6);
//		    if (tmpGetu.equals(DATE_JUN) || tmpGetu.equals(DATE_FEB) || tmpGetu.equals(DATE_MER)) {
//		    	intYear = 0;
//			}
//		    String tmpJuge = format.format(new Date()).substring(0,4) + LATEST_YEAR;
//		    Age = ((Integer.valueOf(tmpJuge)+10000) - birthDayInt)/10000;

		    // ここまで
		    String splitDate =kensinJisiDate.substring(0, 4) + "/" + kensinJisiDate.substring(4, 6) + "/" +kensinJisiDate.substring(6, 8);

		    Date kensinDt = DateFormat.getDateInstance().parse(splitDate);
		    String tmpGetu = kensinJisiDate.substring(4, 6);

		    if (tmpGetu.equals(DATE_JUN) || tmpGetu.equals(DATE_FEB) || tmpGetu.equals(DATE_MER)) {
		    	intYear = 0;
			}

		    String tmpJuge = format.format(kensinDt).substring(0,4) + LATEST_YEAR;

		    Age = ((Integer.valueOf(tmpJuge)+intYear) - birthDayInt)/10000;

		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

	    return Age;
	}

	/**
	 *  年度末年齢取得
	 *
	 *    @param  キー
	 *
	 *    @return VALUE
	 */
	public static int getFiscalYear( String birthday )
	{
		int Age =0;

		try {

			if (birthday == "")
				return 0;

			//	String birthday = searchResult.get(i).get("BIRTHDAY");
			int birthDayInt = Integer.valueOf(birthday);
			int intYear = 10000;

		    DateFormat format = new SimpleDateFormat("yyyyMMdd");

		    String tmpGetu = format.format(new Date()).substring(4,6);

		    if (tmpGetu.equals("01") ||
		    		tmpGetu.equals("02") ||
		    			tmpGetu.equals("03")) {
		    	intYear = 0;
			}

		    String tmpJuge = format.format(new Date()).substring(0,4) + "0331";

			//int Age = ((Integer.valueOf(tmpJuge)+10000) - birthDayInt)/10000;
		    Age = ((Integer.valueOf(tmpJuge)+intYear) - birthDayInt)/10000;

		} catch (NumberFormatException e) {
			logger.warn(e.getMessage());
		}

	    return Age;
	}

}
