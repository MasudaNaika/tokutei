package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * 印刷に用いる健診結果データを受け渡すクラス
 */
public class KenshinKekka {

	private static Logger logger = Logger.getLogger(KenshinKekka.class);

	//健診結果データ
	private ArrayList<Hashtable<String, Hashtable<String, String>>> KenshinKekkaData
	= new ArrayList<Hashtable<String, Hashtable<String, String>>>();
	//健診年月日
	private int KensaNengapiNum = 0;
	private String[] uketukeID = new String[3];
	private String[] KensaNengapi = new String[3];
	//印刷済み検査項目コード一覧
	private ArrayList<String>PrintedCD = new ArrayList<String>();
	//印刷済み検査項目コードがセットされているかのフラグ
	private boolean flg = false;
	private String comment = null;

	// 実施区分
	private static final String KEKKA_OUTPUT_JISI_KBN_0 = "未実施";
	private static final String KEKKA_OUTPUT_JISI_KBN_2 = "測定不可能";

	/**
	 * コンストラクタ
	 * 印刷済み検査項目コード一覧を生成する
	 * @see	setKenshinCD()
	 */
	public KenshinKekka() {
		this.setKenshinCD();
		this.flg = true;
	}

	/**
	 * 健診結果データ set
	 * DBから取得
	 * @param	Hashtable<String, String>
	 * 			Keys => HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, HKNJANUM
	 * @return	boolean
	 */
	public boolean setPrintKenshinKekkaSQL(Hashtable<String, String> data){
		ArrayList<Hashtable<String,String>> resultCount = null;
		ArrayList<Hashtable<String,String>> resultList = null;
		ArrayList<Hashtable<String,String>> resultHistoryList = null;

		Hashtable<String,String> resultItem = null;
		Hashtable<String,String> tmp1 = new Hashtable<String,String>();
		Hashtable<String, Hashtable<String,String>> tmp2 = new Hashtable<String, Hashtable<String,String>>();

		/*
		 * 健診年月日を取得
		 * 今回、前回、前々回
		 */
//		try{
// edit ver2 s.inoue 2009/06/03
//			Query =
//			"SELECT DISTINCT KENSA_NENGAPI FROM T_KENSAKEKA_SONOTA WHERE UKETUKE_ID = "
//			+ JQueryConvert.queryConvert(data.get("UKETUKE_ID")) +
//			" ORDER BY KENSA_NENGAPI DESC";

// edit s.inoue 2010/04/06
			// 指定レコードより過去３件分表示
			// 今年度かどうか
//			String kensaNenGappi = data.get("KENSA_NENGAPI");
//			boolean blnYear = FiscalYearUtil.getJugeThisYear(kensaNenGappi);

//			// 1件データかどうか
//			if (blnYear) {
//				// 1.受付IDから紐付けIDを取得
//				sbNengapi.append(" SELECT KENSA_NENGAPI,GET_NAYOSE.UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI TS, ");
//				sbNengapi.append(" (SELECT UKETUKE_ID FROM T_NAYOSE TN ");
//				sbNengapi.append(" WHERE TN.NAYOSE_NO = ");
//				sbNengapi.append(" (SELECT NAYOSE_NO FROM T_NAYOSE TN WHERE TN.UKETUKE_ID = ");
//				sbNengapi.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
//				sbNengapi.append("  )) GET_NAYOSE WHERE TS.UKETUKE_ID = GET_NAYOSE.UKETUKE_ID ");
//				sbNengapi.append("  ORDER BY KENSA_NENGAPI DESC");
//			}else{
//				sbNengapi.append("SELECT DISTINCT KENSA_NENGAPI,UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID = ");
//				sbNengapi.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
//				sbNengapi.append(" ORDER BY KENSA_NENGAPI DESC");
//			}
//		} catch (Exception ex){
//			logger.warn(ex.getMessage());
//			return false;
//		}

		// edit s.inoue 2010/04/13
		int cntNayose = 0;

		try {
			StringBuilder sbCount = new StringBuilder();
			sbCount.append("SELECT COUNT(UKETUKE_ID) N_COUNT FROM T_NAYOSE ");
			sbCount.append(" WHERE UKETUKE_ID = ");
			sbCount.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));

			resultCount = JApplication.kikanDatabase.sendExecuteQuery(sbCount.toString());

			Hashtable<String, String> Ncount = resultCount.get(0);
			cntNayose = Integer.parseInt(Ncount.get("N_COUNT").toString());

		}catch(Exception ex){
			logger.warn(ex.getMessage());
			return false;
		}

		// edit s.inoue 2010/04/13
		if (cntNayose == 0){
			// edit s.inoue 2010/04/06
			// 結果通知表-選択した履歴より過去３回分
			try {
				StringBuilder sbHistoryKey = new StringBuilder();

				// 1.受付IDから取得
				sbHistoryKey.append(" SELECT  TS.KENSA_NENGAPI, TS.UKETUKE_ID ");
				sbHistoryKey.append(" FROM T_KENSAKEKA_TOKUTEI TS");
				sbHistoryKey.append(" WHERE TS.UKETUKE_ID = ");
				sbHistoryKey.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
				sbHistoryKey.append("  ORDER BY KENSA_NENGAPI DESC");

				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbHistoryKey.toString());

			}catch (Exception ex){
				logger.warn(ex.getMessage());
				return false;
			}
		}else{
			// edit s.inoue 2010/04/06
			// 結果通知表-選択した履歴より過去３回分
			try {
				StringBuilder sbHistoryKey = new StringBuilder();

				// 1.受付IDから紐付けIDを取得
				sbHistoryKey.append(" SELECT KENSA_NENGAPI,GET_NAYOSE.UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI TS, ");
				sbHistoryKey.append(" (SELECT UKETUKE_ID FROM T_NAYOSE TN ");
				sbHistoryKey.append(" WHERE TN.NAYOSE_NO = ");
				sbHistoryKey.append(" (SELECT NAYOSE_NO FROM T_NAYOSE TN WHERE TN.UKETUKE_ID = ");
				sbHistoryKey.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
				sbHistoryKey.append("  )) GET_NAYOSE WHERE TS.UKETUKE_ID = GET_NAYOSE.UKETUKE_ID ");
				sbHistoryKey.append("  ORDER BY KENSA_NENGAPI DESC");

				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbHistoryKey.toString());

			}catch (Exception ex){
				logger.warn(ex.getMessage());
				return false;
			}
		}

		// 2.取得したデータから指定レコードの健診実施日より
		// 過去３件のキーを割り出しWhere句を生成
		int resultSize = 0;
		StringBuilder sbWhere = new StringBuilder();

		String kenshinDate = data.get("KENSA_NENGAPI");
		try {
			for (int i = 0; i < resultList.size(); i++) {
				Hashtable<String, String> history_rec = resultList.get(i);
				if (Integer.parseInt(history_rec.get("KENSA_NENGAPI"))
						<= Integer.parseInt(kenshinDate)){
					resultSize++;
					if ( i == resultList.size() -1){
						sbWhere.append(JQueryConvert.queryConvert(history_rec.get("UKETUKE_ID")));
					}else{
						sbWhere.append(JQueryConvert.queryConvertAppendComma(history_rec.get("UKETUKE_ID")));
					}
				}
			}
		}catch (Exception ex){
			logger.warn(ex.getMessage());
			return false;
		}

		// 3.取得キーの受付IDを指定してデータを取得する
		try {
			StringBuilder sbHistoryData = new StringBuilder();

			sbHistoryData.append("SELECT DISTINCT KENSA_NENGAPI,UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI ");
			sbHistoryData.append(" WHERE UKETUKE_ID IN ( ");
			sbHistoryData.append(sbWhere.toString());
			sbHistoryData.append(" ) ");
			sbHistoryData.append(" ORDER BY KENSA_NENGAPI DESC");
			resultList = JApplication.kikanDatabase.sendExecuteQuery(sbHistoryData.toString());

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

// del s.inoue 2010/04/06
//			if(resultSize == 0){
//				sbNengapi.delete(0, sbNengapi.length());
//				sbNengapi.append("SELECT DISTINCT KENSA_NENGAPI,UKETUKE_ID FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID = ");
//				sbNengapi.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
//				sbNengapi.append(" ORDER BY KENSA_NENGAPI DESC");
//				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbNengapi.toString());
//				resultSize = resultList.size();
//			}

		try{

			int j = 0;
			resultSize = resultList.size();

			for (int i =0; i < resultSize; i++){
				resultItem = resultList.get(i);
				/*
				 * 健診年月日を保持
				 * 引数で渡された健診年月日より古いやつだけを格納
				 */
				this.uketukeID[j] = resultItem.get("UKETUKE_ID");
				this.KensaNengapi[j] = resultItem.get("KENSA_NENGAPI");
				j++;
				this.KensaNengapiNum = j;
				if(j == 3){
					break;
				}
			}
		} catch (Exception ex){
			logger.warn(ex.getMessage());
		}

		/*
		 * 特定の健診年月日の検査結果を取得
		 * 取得するのは検査結果値のみ
		 */
		StringBuilder sbKekka = new StringBuilder();
		StringBuilder sbTokutei = new StringBuilder();

		for(int i=0; i < this.KensaNengapiNum; i++){
			try{

				/*
				 * 項目コード、項目名、結果値、基準値範囲、データタイプ、単位、データタイプコード名
				 */
				if (sbKekka.length() > 0){
					sbKekka.delete(0, sbKekka.length()-1);
				}
				sbKekka.append("SELECT ");
				sbKekka.append(" T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, T_KENSAKEKA_SONOTA.KEKA_TI AS KEKA_TI, T_KENSAKEKA_SONOTA.JISI_KBN AS JISI_KBN,");
				sbKekka.append(" T_KENSAKEKA_SONOTA.H_L AS H_L , T_KENSHINMASTER.BIKOU AS BIKOU, ");
				sbKekka.append(" T_KENSHINMASTER.DS_JYOUGEN AS DS_JYOUGEN, T_KENSHINMASTER.DS_KAGEN AS DS_KAGEN, ");
				sbKekka.append(" T_KENSHINMASTER.JS_JYOUGEN AS JS_JYOUGEN, T_KENSHINMASTER.JS_KAGEN AS JS_KAGEN, ");
				// edit ver2 s.inoue 2009/09/17
				sbKekka.append(" T_KENSHINMASTER.MAX_BYTE_LENGTH, ");
				sbKekka.append(" T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, T_KENSHINMASTER.KIJYUNTI_HANI AS KIJYUNTI_HANI, T_KENSHINMASTER.DATA_TYPE AS DATA_TYPE,");
				sbKekka.append(" T_KENSHINMASTER.TANI AS TANI ");
				sbKekka.append(" From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD) ");
				sbKekka.append(" WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = ");
				// edit ver2 s.inoue 2009/06/03
				//sbKekka.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
				sbKekka.append(JQueryConvert.queryConvert(uketukeID[i]));
				sbKekka.append(" AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = " );
				// add ver2 s.inoue 2009/07/15
				sbKekka.append(JQueryConvert.queryConvert(KensaNengapi[i]));
				sbKekka.append(" AND T_KENSHINMASTER.HKNJANUM = ");
				sbKekka.append(JQueryConvert.queryConvert(data.get("HKNJANUM")));

			} catch (NullPointerException e){
				logger.warn(e.getMessage());
				return false;
			}

			try{
				resultList = JApplication.kikanDatabase.sendExecuteQuery(sbKekka.toString());
			}catch(SQLException e){
				logger.warn(e.getMessage());
			}

			/*
			 * 項目コード, 結果値
			 */
			resultSize = resultList.size();
			for (int j =0; j < resultSize; j++){
				// System.out.println( j + "行目");
				try{
					resultItem = resultList.get(j);
					tmp1.clear();
					tmp1.put("KOUMOKU_CD", resultItem.get("KOUMOKU_CD"));
					tmp1.put("KOUMOKU_NAME", resultItem.get("KOUMOKU_NAME"));

					// 実施区分 0:未実施,1:実施,2:測定不可能
					tmp1.put("KEKA_TI", resultItem.get("KEKA_TI"));
					tmp1.put("JISI_KBN", resultItem.get("JISI_KBN"));
					tmp1.put("KIJYUNTI_HANI", resultItem.get("KIJYUNTI_HANI"));
					tmp1.put("DATA_TYPE", resultItem.get("DATA_TYPE"));
					tmp1.put("TANI", resultItem.get("TANI"));
					tmp1.put("BIKOU", resultItem.get("BIKOU"));
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}

				// edit ver2 s.inoue 2009/09/17
				int data_type = 0;

				try{
					data_type = Integer.valueOf(resultItem.get("DATA_TYPE")).intValue();
					if (data_type == 2){
						String code_name = this.getCodeName(resultItem.get("KOUMOKU_CD"), Integer.valueOf(resultItem.get("KEKA_TI")));
						tmp1.put("CODE_NAME", code_name);
					} else {
						tmp1.put("CODE_NAME", "");
					}

				} catch(NumberFormatException e) {
					tmp1.put("CODE_NAME", "");
				}

				try{
					int H_L = Integer.valueOf(resultItem.get("H_L")).intValue();
					if (H_L == 1){
						tmp1.put("H_L", "L");
					} else if (H_L == 2){
						tmp1.put("H_L", "H");
					}

				} catch(NumberFormatException e) {
					tmp1.put("H_L", "");
				}

				// edit ver2 s.inoue 2009/09/17
				try{
					if (data_type == 1){
						String koumokuFormat = resultItem.get("MAX_BYTE_LENGTH").toString();
						tmp1.put("MAX_BYTE_LENGTH",koumokuFormat);
					}else{
						tmp1.put("MAX_BYTE_LENGTH", "");
					}
				} catch(NumberFormatException e){
					tmp1.put("MAX_BYTE_LENGTH", "");
				}

				try{//男性上限値
					double DS_JYOUGEN = Double.valueOf(resultItem.get("DS_JYOUGEN")).doubleValue();
					String ds_jyougen = Double.toString(DS_JYOUGEN);
					tmp1.put("DS_JYOUGEN",ds_jyougen.toString());
				} catch(NumberFormatException e) {
					tmp1.put("DS_JYOUGEN", "");
				}

				try{//男性下限値
					double DS_KAGEN = Double.valueOf(resultItem.get("DS_KAGEN")).doubleValue();
					String ds_kagen = Double.toString(DS_KAGEN);
					tmp1.put("DS_KAGEN", ds_kagen);
				} catch(NumberFormatException e) {
					tmp1.put("DS_KAGEN", "");
				}

				try{//女性上限値
					double JS_JYOUGEN = Double.valueOf(resultItem.get("JS_JYOUGEN")).doubleValue();
					String js_jyougen = Double.toString(JS_JYOUGEN);
					tmp1.put("JS_JYOUGEN", js_jyougen);
				} catch(NumberFormatException e) {
					tmp1.put("JS_JYOUGEN", "");
				}

				try{//女性下限値
					double JS_KAGEN = Double.valueOf(resultItem.get("JS_KAGEN")).doubleValue();
					String js_kagen = Double.toString(JS_KAGEN);
					tmp1.put("JS_KAGEN", js_kagen);
				} catch(NumberFormatException e) {
					tmp1.put("JS_KAGEN", "");
				}

				/*
				 * tmp2に代入
				 */
				tmp2.put(resultItem.get("KOUMOKU_CD"), (Hashtable<String, String>) tmp1.clone());
			}

			// i 0:今回,1:前回,2:前々回
			this.KenshinKekkaData.add(i, (Hashtable<String, Hashtable<String,String>>)tmp2.clone());
			// add ver2 s.inoue 2009/07/15
			tmp2.clear();
			if (sbTokutei.length() > 0){
				sbTokutei.delete(0, sbTokutei.length() -1);
			}
			sbTokutei.append(" select ");
			sbTokutei.append(" HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, K_P_NO, HANTEI_NENGAPI, TUTI_NENGAPI, KENSA_CENTER_CD, ");
			sbTokutei.append(" KEKA_INPUT_FLG, SEIKYU_KBN, KOMENTO, NENDO, UKETUKE_ID, NYUBI_YOUKETU, SYOKUZEN_SYOKUGO, HENKAN_NITIJI ");
			sbTokutei.append(" from T_KENSAKEKA_TOKUTEI as tokutei");
			sbTokutei.append(" where  tokutei.UKETUKE_ID = ");
			// edit ver2 s.inoue 2009/06/03
			//buffer.append(JQueryConvert.queryConvert(data.get("UKETUKE_ID")));
			sbTokutei.append(JQueryConvert.queryConvert(uketukeID[i]));
			sbTokutei.append(" AND tokutei.KENSA_NENGAPI = ");
			sbTokutei.append(JQueryConvert.queryConvert(KensaNengapi[i]));

			String query = sbTokutei.toString();
			try{
				resultList = JApplication.kikanDatabase.sendExecuteQuery(query);
			}catch(SQLException e){
				logger.warn(e.getMessage());
			}

			// edit ver2 s.inoue 2009/07/16
			if (i == 0 &&
				resultList != null && ! resultList.isEmpty()) {
				this.comment = resultList.get(0).get("KOMENTO");
			}
		}
		try {
			this.KensaNengapi[0] = this.KensaNengapi[0].substring(0, 4) + "年" + this.KensaNengapi[0].substring(4, 6) + "月" + this.KensaNengapi[0].substring(6, 8) + "日";
			// edit s.inoue 2010/05/07
			if (this.KensaNengapi[1] != null)
			this.KensaNengapi[1] = this.KensaNengapi[1].substring(0, 4) + "年" + this.KensaNengapi[1].substring(4, 6) + "月" + this.KensaNengapi[1].substring(6, 8) + "日";

			if (this.KensaNengapi[2] != null)
			this.KensaNengapi[2] = this.KensaNengapi[2].substring(0, 4) + "年" + this.KensaNengapi[2].substring(4, 6) + "月" + this.KensaNengapi[2].substring(6, 8) + "日";

		} catch (Exception e){
			logger.warn(e.getMessage());
		}
		return true;
	}

	/**
	 * 項目コード名を取得
	 * @param KOUMOKU_CD, CODE_NUM(KEKA_TI)
	 *
	 * @return CODE_NAME
	 */
	public String getCodeName(String koumoku_cd, int code_num) {
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem = null;
		StringBuilder sb = null;
		String code_name = new String();

		try{
			sb = new StringBuilder();
			sb.append("SELECT CODE_NAME ");
			sb.append(" FROM T_DATA_TYPE_CODE ");
			sb.append(" WHERE KOUMOKU_CD = ");
			sb.append(JQueryConvert.queryConvert(koumoku_cd));
			sb.append(" AND CODE_NUM = ");
			sb.append( JQueryConvert.queryConvert(String.valueOf(code_num)));
		} catch (NullPointerException e){
			return "";
		}
		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
			ResultItem = Result.get(0);
			/*
			 * 健診年月日を保持
			 */
			code_name = ResultItem.get("CODE_NAME");
		} catch (SQLException e){
			logger.warn(e.getMessage());
		}

		return code_name;
	}

	/**
	 * 健診結果データ get
	 * @return	ArrayList<Hashtable<String, Hashtable<String, String>>> KenshinKekkaData
	 * 			<KENSANENGAPI, <KOUMOKU_CD, <KEKATI, String>>
	 */
	public ArrayList<Hashtable<String, Hashtable<String, String>>> getPrintKenshinKekkaData() {
		return this.KenshinKekkaData;
	}
	/**
	 * 健診年月日を取得
	 * @return KensaNengapi[]
	 */
	public String[] getKensaNengapi(){
		return this.KensaNengapi;
	}

	/**
	 * 印刷済み項目　add
	 * @param void
	 */
	public void setKenshinCD() {
		/*
		 * 一ページ目
		 */
		//既往歴
		this.PrintedCD.add("9N056000000000011");
		//自覚症状
		this.PrintedCD.add("9N061000000000011");
		//他覚症状
		this.PrintedCD.add("9N066000000000011");
		//喫煙歴
		this.PrintedCD.add("9N736000000000011");
		//身長
		this.PrintedCD.add("9N001000000000001");
		//体重
		this.PrintedCD.add("9N006000000000001");
		/*
		 * 腹囲（実測）		9N016160100000001
		 * 腹囲（自己判定）	9N016160200000001
		 * 腹囲（自己申告）	9N016160300000001
		 */
		this.PrintedCD.add("9N016160100000001");
		this.PrintedCD.add("9N016160200000001");
		this.PrintedCD.add("9N016160300000001");
		//BMI
		this.PrintedCD.add("9N011000000000001");
		/*
		 * 収縮期血圧（1回目）	9A751000000000001
		 * 収縮期血圧（2回目）	9A752000000000001
		 * 収縮期血圧（その他）	9A755000000000001
		 */
		this.PrintedCD.add("9A751000000000001");
		this.PrintedCD.add("9A752000000000001");
		this.PrintedCD.add("9A755000000000001");
		/*
		 * 拡張期血圧（一回目）	9A761000000000001
		 * 拡張期血圧（二回目）	9A762000000000001
		 * 拡張期血圧（その他）	9A765000000000001
		 */
		this.PrintedCD.add("9A761000000000001");
		this.PrintedCD.add("9A762000000000001");
		this.PrintedCD.add("9A765000000000001");
		/*
		 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去））	3F015000002327101
		 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去））	3F015000002327201
		 * 中性脂肪（その他）								3F015000002399901
		 */
		this.PrintedCD.add("3F015000002327101");
		this.PrintedCD.add("3F015000002327201");
		this.PrintedCD.add("3F015000002399901");
		/*
		 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）））	3F070000002327101
		 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）））	3F070000002327201
		 * HDL-コレステロール-（その他）						3F070000002399901
		 */
		this.PrintedCD.add("3F070000002327101");
		this.PrintedCD.add("3F070000002327201");
		this.PrintedCD.add("3F070000002399901");
		/*
		 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））	3F077000002327101
		 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））	3F077000002327201
		 * LDL-コレステロール-（その他）						3F077000002399901
		 */
		this.PrintedCD.add("3F077000002327101");
		this.PrintedCD.add("3F077000002327201");
		this.PrintedCD.add("3F077000002399901");
		/*
		 * GOT（紫外吸光光度法（JSCC標準化対応法））				3B035000002327201
		 * GOT（その他）									3B035000002399901
		 */
		this.PrintedCD.add("3B035000002327201");
		this.PrintedCD.add("3B035000002399901");
		/*
		 * GPT（紫外吸光光度法（JSCC標準化対応法））				3B045000002327201
		 * GPT（その他）									3B045000002399901
		 */
		this.PrintedCD.add("3B045000002327201");
		this.PrintedCD.add("3B045000002399901");
		/*
		 * y-GTP（可視吸光光度法（JSCC標準化対応法））			3B090000002327101
		 * y-GTP（その他）									3B090000002399901
		 */
		this.PrintedCD.add("3B090000002327101");
		this.PrintedCD.add("3B090000002399901");
		/*
		 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法））			3D010000001926101
		 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法））			3D010000002227101
		 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））	3D010000001927201
		 * 空腹時血糖（その他）								3D010000001999901
		 */
		this.PrintedCD.add("3D010000001926101");
		this.PrintedCD.add("3D010000002227101");
		this.PrintedCD.add("3D010000001927201");
		this.PrintedCD.add("3D010000001999901");
		/*
		 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	免疫学的方法（ラテックス凝集比濁法等）	3D045000001906202
		 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	HPLC(不安定分画除去HPLC法）			3D045000001920402
		 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	酵素法							3D045000001927102
		 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	その他							3D045000001999902
		 */
		this.PrintedCD.add("3D045000001906202");
		this.PrintedCD.add("3D045000001920402");
		this.PrintedCD.add("3D045000001927102");
		this.PrintedCD.add("3D045000001999902");
		/*
		 * 糖	試験紙法（機械読み取り）						1A020000000191111
		 * 糖	試験紙法（目視法）							1A020000000190111
		 */
		this.PrintedCD.add("1A020000000191111");
		this.PrintedCD.add("1A020000000190111");
		/*
		 * 蛋白	試験紙法（機械読み取り）						1A010000000191111
		 * 蛋白	試験紙法（目視法）							1A010000000190111
		 */
		this.PrintedCD.add("1A010000000191111");
		this.PrintedCD.add("1A010000000190111");

		/*
		 * 2ページ目
		 */
		//赤血球数
		this.PrintedCD.add("2A020000001930101");
		//血色素数
		this.PrintedCD.add("2A030000001930101");
		//ヘマトクリット値
		this.PrintedCD.add("2A040000001930102");
		//心電図所見
		this.PrintedCD.add("9A110160800000049");
		//眼底検査所見
		this.PrintedCD.add("9E100160900000049");
		//メタボリックシンドローム判定
		this.PrintedCD.add("9N501000000000011");
		//医師の判断
		this.PrintedCD.add("9N511000000000049");
		//判断した医師名
		this.PrintedCD.add("9N516000000000049");

		/* 7.貧血、19.飲酒量、21.生活習慣改善、22.保健指導を追加*/
		/* 上記の4項目追加により項目番号修正*/
		/*
		 * 4ページ目
		 */
		//1-1 血圧を下げる薬を服用している
		this.PrintedCD.add("9N701000000000011");
		//1-2 インスリン注射又は血糖を下げる薬を使用している
		this.PrintedCD.add("9N706000000000011");
		//1-3 コレステロールを下げる薬を服用している
		this.PrintedCD.add("9N711000000000011");
		//4 医師から、脳卒中（脳出血、脳梗塞等）にかかっているといわれたり、治療を受けたことがある
		this.PrintedCD.add("9N716000000000011");
		//5 医師から、心臓病（狭心症、心筋梗塞）にかかっているといわれたり、治療を受けたことがある
		this.PrintedCD.add("9N721000000000011");
		//6 医師から、慢性の腎不全にかかっているといわれたり、治療（人工透析）を受けたことがある
		this.PrintedCD.add("9N726000000000011");
		//7 医師から貧血といわれたことがある
		this.PrintedCD.add("9N731000000000011");
		//8 現在、たばこを習慣的に吸っている
		this.PrintedCD.add("9N736000000000011");
		//9 ２０歳の時の体重から１０キロ以上増加している
		this.PrintedCD.add("9N741000000000011");
		//10 １回３０分以上の軽く汗をかく運動を週２日以上、１年以上実施している
		this.PrintedCD.add("9N746000000000011");
		//11 日常生活において歩行又は同等の身体活動を１日１時間以上実施している
		this.PrintedCD.add("9N751000000000011");
		//12 同世代の同姓と比較して歩く速度が速い
		this.PrintedCD.add("9N756000000000011");
		//13 この１年間で体重の増減が±３kg以上ある
		this.PrintedCD.add("9N761000000000011");
		//14 早食い・ドカ食い・ながら食いが多い
		this.PrintedCD.add("9N766000000000011");
		//15 就寝前の２時間以内に夕食をとることが週に３回以上ある
		this.PrintedCD.add("9N771000000000011");
		//16 夜食や間食が多い
		this.PrintedCD.add("9N776000000000011");
		//17 朝食を抜くことが多い
		this.PrintedCD.add("9N781000000000011");
		//18 ほぼ毎日アルコール飲料を飲む
		this.PrintedCD.add("9N786000000000011");
		//19 飲酒日の1日あたりの飲酒量
		this.PrintedCD.add("9N791000000000011");
		//20 睡眠で休養が得られている
		this.PrintedCD.add("9N796000000000011");
		//21 運動や生活活動の生活習慣を改善してみようと思いますか
		this.PrintedCD.add("9N801000000000011");
		//21 生活習慣病の改善について保健指導を受ける機会があれば、利用しますか
		this.PrintedCD.add("9N806000000000011");
	}


	/**
	 * 印刷済みコード　add
	 * @param KenshinKoumoku_CD
	 * @return boolean
	 */
	public boolean addKenshinCD(String CD) {
		this.PrintedCD.add(CD);
		return true;
	}

	/**
	 * 印刷済みコード　check
	 * 3ページ目には、1ページ・2ページ・4ページの項目コードを除いたものを印刷する
	 * そのため、ある検査項目コードが他のページに含まれているか確認する
	 * @param KenshinKoumoku_CD
	 * @return	boolean already printed is true
	 */
	public boolean checkKenshinCD(String CD){
		/*
		 * flgを確認
		 */
		if (!this.flg) {
			this.setKenshinCD();
			this.flg = true;
		}
		/*
		 * コードが含まれているかを確認
		 */
		if (this.PrintedCD.contains(CD)){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 結果値、実施区分、下限、上限値を取得する
	 * @param CD:検査項目コード,KenshinDateNum:健診実施日,isMale:男,女
	 * @return KEKA_TI,JISI_KBN,XX_KAGEN,XX_JYOUGEN
	 */
	public Hashtable<String,String> getKekkData(ArrayList<String> CD, int KenshinDateNum, boolean isMale) {

		boolean jisiExists = false;
		boolean sokuExists = false;
		boolean miExists = false;
		String jisiKbn ="";
		String kekati ="";
		String jisikekati ="";
		String Kagen ="";
		String Jogen ="";

		String jisiKagen ="";
		String jisiJogen ="";
		String sonotaKagen ="";
		String sonotaJogen ="";

		String sokuJogen ="";
		String miJogen="";
		String sokuKagen="";
		String miKagen="";

		Hashtable<String,String> kekkaTbl = new Hashtable<String, String>();

		if (this.KenshinKekkaData.size() > KenshinDateNum) {

			for(int i=0;i < CD.size(); i++){

				Hashtable<String, Hashtable<String, String>> kekkaData =
					this.KenshinKekkaData.get(KenshinDateNum);

				/*
				 * 検査項目コードが含まれているかをチェックする
				 */
				if(kekkaData.containsKey(CD.get(i))){

					jisiKbn = kekkaData.get(CD.get(i)).get("JISI_KBN");
					kekati = kekkaData.get(CD.get(i)).get("KEKA_TI");

					// edit ver2 s.inoue 2009/09/17
					// 基準値フォーマット対応
					String kekkaFormat = kekkaData.get(CD.get(i)).get("MAX_BYTE_LENGTH");
					kekkaFormat = kekkaFormat != null ? kekkaFormat: "";

					if (isMale){
						Kagen = kekkaData.get(CD.get(i)).get("DS_KAGEN");
						Kagen = JValidate.validateKensaKekkaDecimal(Kagen,kekkaFormat);
						Kagen = Kagen != null ? Kagen:"";
						Jogen = kekkaData.get(CD.get(i)).get("DS_JYOUGEN");
						Jogen = JValidate.validateKensaKekkaDecimal(Jogen,kekkaFormat);
						Jogen = Jogen != null ? Jogen:"";
					}else{
						Kagen = kekkaData.get(CD.get(i)).get("JS_KAGEN");
						Kagen = JValidate.validateKensaKekkaDecimal(Kagen,kekkaFormat);
						Kagen = Kagen != null ? Kagen:"";
						Jogen = kekkaData.get(CD.get(i)).get("JS_JYOUGEN");
						Jogen = JValidate.validateKensaKekkaDecimal(Jogen,kekkaFormat);
						Jogen = Jogen != null ? Jogen:"";
					}

					if (!kekati.equals("") && jisiKbn.equals("1")){
						jisiExists = true;
						jisikekati = kekati;
						jisiKagen = Kagen;
						jisiJogen = Jogen;

					}else if (kekati.equals("") && jisiKbn.equals("1")){
						sonotaKagen = Kagen;sonotaJogen = Jogen;
						continue;
					}else if (jisiKbn.equals("2")){
						sokuKagen = Kagen;sokuJogen = Jogen;
						sokuExists = true;
					}else if (jisiKbn.equals("0")){
						miKagen = Kagen;miJogen = Jogen;
						miExists = true;
					}
				}
			}

			if (jisiExists){
				kekkaTbl.put("KEKA_TI", jisikekati);
				kekkaTbl.put("JISI_KBN", "1");
				kekkaTbl.put("KAGEN", jisiKagen);
				kekkaTbl.put("JYOGEN", jisiJogen);
			}else if (sokuExists) {
				kekkaTbl.put("KEKA_TI", KEKKA_OUTPUT_JISI_KBN_2);
				kekkaTbl.put("JISI_KBN", "2");
				kekkaTbl.put("KAGEN", sokuKagen);
				kekkaTbl.put("JYOGEN", sokuJogen);
			}else if (miExists) {
				kekkaTbl.put("KEKA_TI", KEKKA_OUTPUT_JISI_KBN_0);
				kekkaTbl.put("JISI_KBN", "0");
				kekkaTbl.put("KAGEN", miKagen);
				kekkaTbl.put("JYOGEN", miJogen);
			}else{
				kekkaTbl.put("KEKA_TI", "");
				kekkaTbl.put("JISI_KBN", "1");
				kekkaTbl.put("KAGEN", sonotaKagen);
				kekkaTbl.put("JYOGEN", sonotaJogen);
			}

		}

		return kekkaTbl;
	}

	/**
	 * 複数のJLA10コードがある場合に、結果値が含まれる項目の結果値を返す
	 * 結果値が、文字列かコードかを判別して、結果値かデータタイプコード名を返す
	 * @param	検査項目コード, 健診年月日番号（今回=>0、前回=>1、前々回=>2）
	 */
	public String getKekati(ArrayList<String> CD, int KenshinDateNum) {

		String ret = "";

		if (this.KenshinKekkaData.size() > KenshinDateNum) {

			for(int i=0;i < CD.size(); i++){

				Hashtable<String, Hashtable<String, String>> kekkaData =
					this.KenshinKekkaData.get(KenshinDateNum);

				/*
				 * 検査項目コードが含まれているかをチェックする
				 */
				if(kekkaData.containsKey(CD.get(i))){
					/*
					 * 含まれている
					 * 結果値が文字列か、コードかを識別する
					 */
					String kekati = kekkaData.get(CD.get(i)).get("KEKA_TI");
					if(! kekati.isEmpty()){
						ret = kekati;
						break;
					}
				}
			}
		}

		return ret;
	}

	public String getJisiKbn(ArrayList<String> CD, int KenshinDateNum) {

	String ret = "";

	if (this.KenshinKekkaData.size() > KenshinDateNum) {

		for(int i=0;i < CD.size(); i++){

			Hashtable<String, Hashtable<String, String>> kekkaData =
				this.KenshinKekkaData.get(KenshinDateNum);

			/*
			 * 検査項目コードが含まれているかをチェックする
			 */
			if(kekkaData.containsKey(CD.get(i))){
				if(kekkaData.get(CD.get(i)).get("KOUMOKU_CD").equals(CD.get(i))){
					/*
					 * 含まれている
					 * 結果値が文字列か、コードかを識別する
					 * 実施区分(0:未実施,1:実施,2:測定不可能)
					 */
					String jisiKbn = kekkaData.get(CD.get(i)).get("JISI_KBN");
					if(! jisiKbn.isEmpty()){
						ret = jisiKbn;
						break;
					}
				}
			}
		}
	}

	return ret;
	}

	public ArrayList<String> getArrJisiKbn(ArrayList<String> CD, int KenshinDateNum) {

		ArrayList<String> retArrLst= null;

		if (this.KenshinKekkaData.size() > KenshinDateNum) {

			for(int i=0;i < CD.size(); i++){

				Hashtable<String, Hashtable<String, String>> kekkaData =
					this.KenshinKekkaData.get(KenshinDateNum);

				/*
				 * 検査項目コードが含まれているかをチェックする
				 */
				if(kekkaData.containsKey(CD.get(i))){
						/*
						 * 含まれている
						 * 結果値が文字列か、コードかを識別する
						 * 実施区分(0:未実施,1:実施,2:測定不可能)
						 */
						String jisiKbn = kekkaData.get(CD.get(i)).get("JISI_KBN");
						if(! jisiKbn.isEmpty()){
							retArrLst.add(jisiKbn);
						}
				}
			}
		}

		return retArrLst;
	}

	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の男性の基準値下限を返す
	 * @param	検査項目コード
	 */
	public String getDsKagen(ArrayList<String> CD, int KenshinDateNum) {

		String key = "DS_KAGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);

		return item;
	}

	/**
	 * 結果関連の項目値を取得する。
	 */
	private String getKekkaItem(ArrayList<String> CD, int KenshinDateNum, String key) {
		String value = "";

		for(int i=0;i < CD.size(); i++){
			/*
			 * 検査項目コードが含まれているかをチェックする
			 */
//			if (this.KenshinKekkaData.contains(KenshinDateNum)) {
//				Hashtable<String, Hashtable<String, String>> dateNum = this.KenshinKekkaData.get(KenshinDateNum);
//				String cdItem = CD.get(i);
//
//				if(dateNum.containsKey(cdItem)){
//					//含まれている
//					value = KenshinKekkaData.get(KenshinDateNum).get(cdItem).get(key);
//					break;
//				}
//			}

			if (this.KenshinKekkaData != null && this.KenshinKekkaData.size() > KenshinDateNum) {

				Hashtable<String, Hashtable<String, String>> kekkaData = this.KenshinKekkaData.get(KenshinDateNum);

				if(kekkaData.containsKey(CD.get(i))){
					value = KenshinKekkaData.get(KenshinDateNum).get(CD.get(i)).get(key);
					break;
				}
			}
		}

		return value;
	}

	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の男性の基準値上限を返す
	 * @param	検査項目コード
	 */
	public String getDsJyogen(ArrayList<String> CD, int KenshinDateNum) {

		String key = "DS_JYOUGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}

	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の女性の基準値下限を返す
	 * @param	検査項目コード
	 */
	public String getJsKagen(ArrayList<String> CD, int KenshinDateNum) {
		String key = "JS_KAGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}

	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の女性の基準値上限を返す
	 * @param	検査項目コード
	 */
	public String getJsJyogen(ArrayList<String> CD, int KenshinDateNum) {
		String key = "JS_JYOUGEN";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}
	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の備考を返す
	 * @param	検査項目コード
	 */
	public String getBikou(ArrayList<String> CD, int KenshinDateNum) {
		String key = "BIKOU";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}
	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の備考を返す
	 * @param	検査項目コード
	 */
	public String getHL(ArrayList<String> CD, int KenshinDateNum) {
		String key = "H_L";
		String item = getKekkaItem(CD, KenshinDateNum, key);
		return item;
	}
	/**
	 * 複数のJLAC10コードがある場合に、結果値が含まれる項目の備考を返す
	 * @param	検査項目コード
	 */
	public String getKekkati(ArrayList<String> CD, int KenshinDateNum) {
		String key = "KEKA_TI";
		String item = getKekkaItem(CD, KenshinDateNum, key);

		return item;
	}
	/**
	 * 問診出力の為作成
	 * @param	検査項目コード
	 */
	public String getMonshin(int i, String code) {
		String codeName = "";

		/*
		 * 項目コードが含まれているかをチェックする
		 */
		Hashtable<String, String> item = KenshinKekkaData.get(i).get(code);
		if (item != null) {
			codeName = item.get("CODE_NAME");
		}

		return codeName;
	}
	/**
	 * 項目名取得する。
	 * @param	検査項目コード、結果値
	 */
	public String getKomokuMeisho(String kensaCd, String kekkati ) {
		int numKekka = 0;
		if (!kekkati.equals("")){
			numKekka = Integer.parseInt(kekkati);
		}else{
			return "";
		}
		return this.getCodeName(kensaCd.toString(), numKekka);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


