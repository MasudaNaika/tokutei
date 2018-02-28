package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class Iraisho {
	
	// 依頼書データ
	private Hashtable<String, String> IraiData = new Hashtable<String, String>();
	
	/**
	 * 個人データ(依頼書) set
	 * DBから取得する
	 * @param	HashTable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO、KENSA_NENGAPI
	 * @return	boolean
	 */
	public boolean setPrintKojinIraiDataSQL(Hashtable<String, String> data) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		String query = null;

		// 個人データ取得
		try{
			query = " SELECT KOJIN.UKETUKE_ID,KOJIN.JYUSHIN_SEIRI_NO,KOJIN.NAME,KOJIN.KANANAME,KOJIN.BIRTHDAY,KOJIN.SEX," +
			" TOKUTEI.NYUBI_YOUKETU,TOKUTEI.SYOKUZEN_SYOKUGO " +
			" FROM T_KOJIN AS KOJIN " +
			" LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI " +
			" ON(TOKUTEI.UKETUKE_ID = KOJIN.UKETUKE_ID) " +
			" WHERE " +
			" KOJIN.UKETUKE_ID = " + JQueryConvert.queryConvert(data.get("UKETUKE_ID")) +
			" AND TOKUTEI.KENSA_NENGAPI = " + JQueryConvert.queryConvert(data.get("KENSA_NENGAPI"));
			
			Result = JApplication.kikanDatabase.sendExecuteQuery(query);
			ResultItem = Result.get(0);
			
		} catch (Exception ex){
			return false;
		}
		
		/***** ヘッダ情報 *****/
		// 検査機関名
		// 健診実施期間名
		
		// 採血日
		if(!data.get("KENSA_NENGAPI").isEmpty()){
			this.IraiData.put("KENSA_NENGAPI",
					data.get("KENSA_NENGAPI").substring(0, 4) + "年" 
					+ data.get("KENSA_NENGAPI").substring(4, 6) + "月"
					+ data.get("KENSA_NENGAPI").substring(6, 8) + "日");
			
			this.IraiData.put("UKETUKE_NENGAPI", data.get("KENSA_NENGAPI"));
		} else {
			Calendar cal = Calendar.getInstance();
			String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4) + 
			JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2) +
			JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);
			
			this.IraiData.put("KENSA_NENGAPI",
					date.substring(0, 4) + "年" 
					+ date.substring(4, 6) + "月"
					+ date.substring(6, 8) + "日");
			this.IraiData.put("UKETUKE_NENGAPI", date);
		}
		
		/***** 明細情報 *****/		
		// 受診券整理番号
		this.IraiData.put("JYUSHIN_SEIRI_NO", ResultItem.get("JYUSHIN_SEIRI_NO"));
		// 氏名
		this.IraiData.put("NAME", ResultItem.get("NAME"));
		// フリガナ
		this.IraiData.put("KANANAME", ResultItem.get("KANANAME"));
		
		// 生年月日
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
		this.IraiData.put("BIRTHDAY", birthday);
		
		// 性別
		if(Integer.valueOf(ResultItem.get("SEX")) == 1){
			this.IraiData.put("SEX", "男性");
		} else {
			this.IraiData.put("SEX", "女性");
		}
		
		// 備考(乳ビ、溶血)
		this.IraiData.put("BIKOU", ResultItem.get("NYUBI_YOUKETU") + JApplication.LINE_SEPARATOR + ResultItem.get("SYOKUZEN_SYOKUGO"));


		return true;
	}

	/**
	 * 依頼データ get
	 * @return	Hashtable<String, String> KojinData
	 */
	public Hashtable<String, String> getPrintIraiData(){
		return this.IraiData;
	}

}
