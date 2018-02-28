package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 * 印刷に用いる患者個人データを受け渡すクラス
 */
public class Kojin {
	//患者個人データ
	private Hashtable<String, String> kojinData = new Hashtable<String, String>();
	private static Logger logger = Logger.getLogger(Kojin.class);
	// eidt s.inoue 2011/08/01
	private boolean yearOld_flg = false;

	/**
	 * 患者個人データ set
	 * 受診券入力画面から取得
	 * DBに登録する前なので値は直接渡す
	 * @param	HashTable<String, String>
	 * 			Keys => JYUSHIN_SEIRI_NO, KANANAME, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO
	 * @return	boolean
	 */
	public boolean setPrintKojinData(Hashtable<String, String> data,int regMode) {
		try {

			//受診日
			// edit s.inoue 2009/10/19
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");

			// edit s.inoue 2010/04/20
			if (regMode == 1){
			// if (data.get("UKETUKE_ID").equals("")){
				String dates = data.get("KENSA_NENGAPI").substring(0, 4)+ "年"
				+ data.get("KENSA_NENGAPI").substring(4,6)+ "月"
				+ data.get("KENSA_NENGAPI").substring(6,8)+ "日";

				this.kojinData.put("KENSA_NENGAPI", dates);
				// del s.inoue 2010/04/15
				// return true;
			}else{
				this.kojinData.put("KENSA_NENGAPI", sdf.format(data.get("KENSA_NENGAPI")));
			}

			//受診券整理番号
			this.kojinData.put("JYUSHIN_SEIRI_NO", data.get("JYUSHIN_SEIRI_NO"));
			//氏名
			this.kojinData.put("KANANAME", data.get("KANANAME"));

			//保険者番号
			this.kojinData.put("HKNJANUM", data.get("HKNJANUM"));
			//被保険者証等記号
			this.kojinData.put("HIHOKENJYASYO_KIGOU", data.get("HIHOKENJYASYO_KIGOU"));
			//被保険者証等番号
			this.kojinData.put("HIHOKENJYASYO_NO", data.get("HIHOKENJYASYO_NO"));

			/* Modified 2008/03/25 藪 画面からの印刷項目追加       */
			/* --------------------------------------------------- */
			String nameKanji = data.get("NAME");
			//名前漢字
			this.kojinData.put("NAME", nameKanji);

			String birthday = data.get("Birthday");

			if (birthday != null) {
				if (birthday.length() == 8) {

					String yearString = birthday.substring(0, 4);
					String monthString = birthday.substring(4, 6);
					String dayString = birthday.substring(6, 8);

					//生年月日
					this.kojinData.put("BIRTHDAY", yearString + "年" + monthString + "月" + dayString + "日");

					/* Modified 2008/05/26 若月  */
					/* --------------------------------------------------- */
//					/*
//					 * 年齢
//					 * DBにないので計算する
//					 */
//					Calendar calendar1 = Calendar.getInstance();
//					Calendar calendar2 = Calendar.getInstance();
//
//					int y = Integer.valueOf(birthdayYear);
//					int m = Integer.valueOf(birthdayMonth);
//					int d = Integer.valueOf(birthdayDate);
//
//					calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
//					calendar2.set(y, m-1 ,d);
//
//					long millis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
//
//					long age = (long)millis/1000/60/60/24/365;
//
//					String year = new String();
//					year = Long.toString(age);
//
//					this.kojinData.put("AGE", year + "歳");

					/* --------------------------------------------------- */
					/*
					 * 年齢
					 * DBにないので計算する
					 */
// edit s.inoue 2010/04/15
//					Calendar calendar1 = Calendar.getInstance();
//
//					int birthdayYear = Integer.valueOf(yearString);
//					int birthdayMonth = Integer.valueOf(monthString);
//					int birthdayDay = Integer.valueOf(dayString);
//
//					int todayYear = calendar1.get(Calendar.YEAR);
//					int todayMonth = calendar1.get(Calendar.MONTH) + 1;
//					int todayDay = calendar1.get(Calendar.DATE);
//
//					int age = todayYear - birthdayYear;
//
//					if (todayMonth < birthdayMonth) {
//						--age;
//					}
//					else if(todayMonth == birthdayMonth){
//						if (todayDay < birthdayDay) {
//							--age;
//						}
//					}



					// add s.inoue 2011/08/03
					/* --------------------------------------------------- */
					initilazeFunctionSetting();
					String selectedItem = JValidate.validateSendSeinengapi(birthday);
					String kensaJissiDate = data.get("KENSA_NENGAPI");

					int age =0;
					if (yearOld_flg){
						if (!kensaJissiDate.equals("")){
							age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
						}else{
							age = FiscalYearUtil.getFiscalYear(selectedItem);
						}
					}else{
						if (!kensaJissiDate.equals("")){
							age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
						}else{
							age = Integer.parseInt(JYearAge.getAge(selectedItem));
						}
					}

					System.out.println("age:" + age);
					this.kojinData.put("AGE", age+ "歳");

					// this.kojinData.put("AGE", FiscalYearUtil.getFiscalYear(birthday) + "歳");
					/* --------------------------------------------------- */
				}
			}
			/* --------------------------------------------------- */
			//性別
			String sexString = data.get("SEX");

			if (sexString != null && ! sexString.isEmpty()) {
				if(Integer.valueOf(sexString) == 1){
					this.kojinData.put("SEX", "男性");
				} else {
					this.kojinData.put("SEX", "女性");
				}
			}
			/* --------------------------------------------------- */


		} catch (NullPointerException e){
			return false;
		}

		return true;
	}

	/**
	 * 患者個人データ set
	 * DBから取得する
	 * @param	HashTable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO,KENSA_NENGAPI,
	 * @return	boolean
	 */
	public boolean setPrintKojinDataSQL(Hashtable<String, String> data) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		String query = null;

		try{
			query =
				// edit s.inoue 2009/10/05
				"SELECT HKNJANUM, JYUSHIN_SEIRI_NO, NAME, KANANAME, BIRTHDAY, SEX FROM T_KOJIN WHERE " +
				"UKETUKE_ID = " + JQueryConvert.queryConvert(data.get("UKETUKE_ID"));
				// "SELECT TT.KEKA_INPUT_FLG,TT.KENSA_NENGAPI, TK.HKNJANUM, TK.JYUSHIN_SEIRI_NO, TK.NAME, TK.KANANAME," +
				// " TK.BIRTHDAY, TK.SEX FROM T_KOJIN TK,T_KENSAKEKA_TOKUTEI TT WHERE " +
				// " TK.UKETUKE_ID = " + JQueryConvert.queryConvert(data.get("UKETUKE_ID")) +
				// " AND TK.UKETUKE_ID = TT.UKETUKE_ID ";

		} catch (NullPointerException e){
			logger.error(e.getMessage());
			return false;
		}

		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(query);
			if (Result.size() > 0) {
				ResultItem = Result.get(0);
			}
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		// edit s.inoue 2009/10/16
		// data.put("KENSA_NENGAPI", ResultItem.get("KENSA_NENGAPI"));

		// del s.inoue 2009/10/15
		// 結果入力済みかどうかのフラグ取得
		// this.kojinData.put("KEKA_INPUT_FLG", ResultItem.get("KEKA_INPUT_FLG"));

		//保険者番号
		this.kojinData.put("HKNJANUM", ResultItem.get("HKNJANUM"));
		//受診券整理番号
		this.kojinData.put("JYUSHIN_SEIRI_NO", ResultItem.get("JYUSHIN_SEIRI_NO"));
		//氏名
		this.kojinData.put("NAME", ResultItem.get("NAME"));
		//フリガナ
		this.kojinData.put("KANANAME", ResultItem.get("KANANAME"));

		/* Modified 2008/05/08 若月  */
		/* --------------------------------------------------- */
//		//生年月日
//		this.KojinData.put("BIRTHDAY", ResultItem.get("BIRTHDAY").substring(0, 4) + "年" + ResultItem.get("BIRTHDAY").substring(4, 6) + "月" + ResultItem.get("BIRTHDAY").substring(6, 8) + "日");
		/* --------------------------------------------------- */
		//生年月日
//		String birthday =
//			ResultItem.get("BIRTHDAY").substring(0, 4)
//			+ "年"
//			+ ResultItem.get("BIRTHDAY").substring(4, 6)
//			+ "月"
//			+ ResultItem.get("BIRTHDAY").substring(6, 8)
//			+ "日";

		String yearString = ResultItem.get("BIRTHDAY").substring(0, 4);
		String monthString = ResultItem.get("BIRTHDAY").substring(4, 6);
		String dayString = ResultItem.get("BIRTHDAY").substring(6, 8);

		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString);
		int day = Integer.parseInt(dayString);

		Calendar target = Calendar.getInstance();
		target.set(year, month, day, 0, 0, 0);

		Calendar meijiStart = Calendar.getInstance();
		meijiStart.set(1868, 9, 8, 0, 0, 0);

		Calendar taisyoStart = Calendar.getInstance();
		taisyoStart.set(1912, 7, 30, 0, 0, 0);

		Calendar syowaStart = Calendar.getInstance();
		syowaStart.set(1926, 12, 25, 0, 0, 0);

		Calendar heiseiStart = Calendar.getInstance();
		heiseiStart.set(1989, 1, 8, 0, 0, 0);

		String gengoName = null;
		int gengoYear = -1;
		/* 明治以降 */
		if (target.equals(meijiStart) || target.after(meijiStart)) {
			/* 明治（大正より前） */
			if (target.before(taisyoStart)) {
				gengoName = "明治";
				gengoYear = year - 1867;
			}
			/* 大正以降 */
			else {
				/* 大正（昭和より前） */
				if (target.before(syowaStart)) {
					gengoName = "大正";
					gengoYear = year - 1911;
				}
				/* 昭和以降 */
				else {
					/* 昭和（平成より前） */
					if (target.before(heiseiStart)) {
						gengoName = "昭和";
						gengoYear = year - 1925;
					}
					/* 平成以降 */
					else {
						gengoName = "平成";
						gengoYear = year - 1988;
					}
				}
			}
		}

		if (gengoYear < 0) {
			gengoName = "";
			gengoYear = year;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(gengoName);
		buffer.append(String.valueOf(gengoYear));
		buffer.append("年");
		buffer.append(String.valueOf(month));
		buffer.append("月");
		buffer.append(String.valueOf(day));
		buffer.append("日");

		String birthday = buffer.toString();
		this.kojinData.put("BIRTHDAY", birthday);
		/* --------------------------------------------------- */

		//性別
		if(Integer.valueOf(ResultItem.get("SEX")) == 1){
			this.kojinData.put("SEX", "男性");
		} else {
			this.kojinData.put("SEX", "女性");
		}

		/* Modified 2008/05/26 若月  */
//		/* --------------------------------------------------- */
//		/*
//		 * 年齢
//		 * DBにないので計算する
//		 */
//		Calendar calendar1 = Calendar.getInstance();
//		Calendar calendar2 = Calendar.getInstance();
//
//		int y = Integer.valueOf(ResultItem.get("BIRTHDAY").substring(0, 4));
//		int m = Integer.valueOf(ResultItem.get("BIRTHDAY").substring(4, 6));
//		int d = Integer.valueOf(ResultItem.get("BIRTHDAY").substring(6, 8));
//
//		calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE));
//		calendar2.set(y, m-1 ,d);
//
//		long millis = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
//
//		long age = (long)millis/1000/60/60/24/365;
//
//		String ageYear = Long.toString(age);
//
//		this.kojinData.put("AGE", ageYear + "歳");

		/* --------------------------------------------------- */
		/*
		 * 年齢
		 * DBにないので計算する
		 */
// edit s.inoue 20081113
		// 共通化⇒getFiscalYear

// edit s.inoue 2010/04/15
//		Calendar calendar1 = Calendar.getInstance();
//
//		int birthdayYear = Integer.valueOf(yearString);
//		int birthdayMonth = Integer.valueOf(monthString);
//		int birthdayDay = Integer.valueOf(dayString);
//
//		int todayYear = calendar1.get(Calendar.YEAR);
//		int todayMonth = calendar1.get(Calendar.MONTH) + 1;
//		int todayDay = calendar1.get(Calendar.DATE);
//
//		int age = todayYear - birthdayYear;
//
//		if (todayMonth < birthdayMonth) {
//			--age;
//		}
//		else if(todayMonth == birthdayMonth){
//			if (todayDay < birthdayDay) {
//				--age;
//			}
//		}

		// add s.inoue 2011/08/03
		/* --------------------------------------------------- */
		initilazeFunctionSetting();
		String selectedItem = JValidate.validateSendSeinengapi(birthday);
		String kensaJissiDate = data.get("KENSA_NENGAPI");

		int age =0;
		if (yearOld_flg){
			if (!kensaJissiDate.equals("")){
				age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
			}else{
				age = FiscalYearUtil.getFiscalYear(selectedItem);
			}
		}else{
			if (!kensaJissiDate.equals("")){
				age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
			}else{
				age = Integer.parseInt(JYearAge.getAge(selectedItem));
			}
		}

		System.out.println("age:" + age);
		this.kojinData.put("AGE", age+ "歳");
//		String selectedItem = JValidate.validateSendSeinengapi(birthday);
//		this.kojinData.put("AGE", FiscalYearUtil.getFiscalYear(selectedItem) + "歳");

		/* --------------------------------------------------- */



		this.kojinData.put("UKETUKE_ID",data.get("UKETUKE_ID") );
		/*
		 * 受診日
		 * DBにないので引数で受け取る
		 * 引数が空値なら現在の時刻を入れる
		 */
		if(!data.get("KENSA_NENGAPI").isEmpty()){
			this.kojinData.put("KENSA_NENGAPI", data.get("KENSA_NENGAPI").substring(0, 4) + "年" + data.get("KENSA_NENGAPI").substring(4, 6) + "月" + data.get("KENSA_NENGAPI").substring(6, 8) + "日");
//			2008/3/25 藪 受付年月日　追加
			this.kojinData.put("UKETUKE_NENGAPI", data.get("KENSA_NENGAPI"));
		} else {
			Calendar cal = Calendar.getInstance();
			String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4) + JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2) + JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);
			this.kojinData.put("KENSA_NENGAPI", date.substring(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日");
//			2008/3/25 藪 受付年月日　追加
			this.kojinData.put("UKETUKE_NENGAPI", date);
			}

		//被保険者証等記号
		this.kojinData.put("HIHOKENJYASYO_KIGOU", data.get("HIHOKENJYASYO_KIGOU"));
		//被保険者証等番号
		this.kojinData.put("HIHOKENJYASYO_NO", data.get("HIHOKENJYASYO_NO"));

		return true;
	}

	// eidt s.inoue 2011/08/03
	/* 個別設定用 */
	private void initilazeFunctionSetting(){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert("011"));

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String functionCd = item.get("FUNCTION_CD");

			if (JApplication.func_yearOldCode.equals(functionCd)){
				yearOld_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}
		}

	}

	/**
	 * 患者個人データ get
	 * @return	Hashtable<String, String> KojinData
	 */
	public Hashtable<String, String> getPrintKojinData(){
		return this.kojinData;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

