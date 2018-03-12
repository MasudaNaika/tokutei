// edit n.ohkubo 2014/08/21　削除　v2.1.1のjarに無かったのでコメントアウト

//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.TreeMap;
//
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.origine.JKenshinPatternMaintenanceEditFrameData;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintDefine;
//
//import org.apache.log4j.Logger;
//
///**
// * 非特定検診（追加の検診）分のデータを取得するクラス
// */
//public class AddMedical_ShokenVer {
//
//	// 過去３回分取得用
//	private static final String SELECT_KENSAKEKKA_ALL_SQL = getSelectKensaKekkaAllSql();
//	// 今回分取得用
//	private static final String SELECT_KENSAKEKKA_SQL = getSelectKensaKekkaSql();
//
//    private static org.apache.log4j.Logger logger = Logger
//	.getLogger(AddMedical_ShokenVer.class);
//
////	/**
////	 * 入力票の追加の健診項目を作成する。
////	 *
////	 * @param KojinData
////	 * @param kensaNenGappi
////	 * @param inFlg
////	 * @param coumokuCd
////	 * @return
////	 */
////	public List<Hashtable<String, String>> tuika(
////			Hashtable<String, String> KojinData,
////			String kensaNenGappi,
////			int inFlg,
////			List coumokuCd) {
////
////		ArrayList<Hashtable<String, String>> result = null;
////		Hashtable<String, String> item = null;
////
////		String strCoumokuCd ="";
////
////		ArrayList<Hashtable<String, String>> rtnList = new ArrayList<Hashtable<String, String>>();
////
////		try {
////			/* 前回、前々回用  */
////			if(inFlg>0){
////
////				for (int i = 0 ;i<coumokuCd.size();i++){
////					strCoumokuCd = strCoumokuCd + (String)coumokuCd.get(i);
////
////					if(coumokuCd.size()-1!=i){
////						strCoumokuCd = strCoumokuCd + ",";
////					}
////				}
////
////				String[] codes = strCoumokuCd.split(",");
////				String[] nengappis = kensaNenGappi.split(",");
////
////				ArrayList<String> paramList = new ArrayList<String>();
////
////				paramList.add(KojinData.get("UKETUKE_ID"));
////				paramList.add(KojinData.get("HKNJANUM"));
////
////				paramList.addAll(Arrays.asList(nengappis));
////				paramList.addAll(Arrays.asList(codes));
////
////				String[] params = new String[paramList.size()];
////				for (int i = 0; i < params.length; i++) {
////					params[i] = paramList.get(i);
////				}
////
////				result = JApplication.kikanDatabase.sendExecuteQuery(
////						getZenGetuKensaKekkaSql(coumokuCd.size(),inFlg), params);
////			/* 今回用  */
////			}else{
////
////				String[] params = {
////						KojinData.get("UKETUKE_ID"),
////						// delete ver2 s.inoue 2009/06/09
////						//kensaNenGappi,
////						KojinData.get("HKNJANUM")
////				};
////
////				result = JApplication.kikanDatabase.sendExecuteQuery(
////						SELECT_KENSAKEKKA_SQL, params);
////			}
////
////		} catch (SQLException e) {
////			e.printStackTrace();
////		}
////
////		for (int i = 0; i < result.size(); i++) {
////			item = result.get(i);
////
////			String code = item.get(PrintDefine.CODE_KOUMOKU);
////
////			if (KihonKensaKoumoku.TSUIKA_CODES.contains(code)) {
////				rtnList.add(item);
////			}
////		}
////		return rtnList;
////	}
//
//// edit ver2 s.inoue 2009/06/09
//	/**
//	 * 入力票の追加の健診項目を作成する。
//	 *
//	 * @param kojinData
//	 * @param kensaNenGappi
//	 * @param inFlg
//	 * @param coumokuCd
//	 * @return
//	 */
//	public List<TreeMap<String, String>> tuika(
//			Hashtable<String, String> kojinData,
//			String kensaNenGappi,
//			int inFlg,
//			List coumokuCd) {
//
//		ArrayList<TreeMap<String, String>> result = null;
//		TreeMap<String, String> item = null;
//
//		ArrayList<TreeMap<String, String>> rtnList
//			= new ArrayList<TreeMap<String, String>>();
//
////		String strCoumokuCd ="";
//		try {
////			/* 前回、前々回用  */
////			if(inFlg>0){
////
////				for (int i = 0 ;i<coumokuCd.size();i++){
////					strCoumokuCd = strCoumokuCd + (String)coumokuCd.get(i);
////
////					if(coumokuCd.size()-1!=i){
////						strCoumokuCd = strCoumokuCd + ",";
////					}
////				}
////
////				String[] codes = strCoumokuCd.split(",");
////				String[] nengappis = kensaNenGappi.split(",");
////
////				ArrayList<String> paramList = new ArrayList<String>();
////
////				paramList.add(KojinData.get("UKETUKE_ID"));
////				paramList.add(KojinData.get("HKNJANUM"));
////
////				paramList.addAll(Arrays.asList(nengappis));
////				paramList.addAll(Arrays.asList(codes));
////
////				String[] params = new String[paramList.size()];
////				for (int i = 0; i < params.length; i++) {
////					params[i] = paramList.get(i);
////				}
////
////				result = JApplication.kikanDatabase.sendExecuteQuery(
////						getZenGetuKensaKekkaSql(coumokuCd.size(),inFlg), params);
////			/* 今回用  */
////			}else{
//			
//// edit s.inoue 2014/05/09
////			// 今年度かどうか
////			boolean blnYear = FiscalYearUtil.getJugeThisYear(kensaNenGappi);
////
////			// 今年度⇒過去3件分,それ以外⇒該当年度分
////			if (blnYear){
//				String[] params = {kojinData.get("UKETUKE_ID"), kojinData.get("HKNJANUM")};
//				result = JApplication.kikanDatabase.sendExecuteSortedQuery(SELECT_KENSAKEKKA_ALL_SQL, params);
////			}
////			// edit ver2 s.inoue 2009/07/06
////			if (!blnYear || result.size() == 0){
////				String[] params = {kojinData.get("UKETUKE_ID"),kensaNenGappi,kojinData.get("HKNJANUM")};
////				result = JApplication.kikanDatabase.sendExecuteSortedQuery(SELECT_KENSAKEKKA_SQL, params);
////			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		// edit s.inoue 2010/04/21
//		JKenshinPatternMaintenanceEditFrameData kenshinmast
//			= new JKenshinPatternMaintenanceEditFrameData();
//		HashSet<String> dokuji= kenshinmast.getDokujiTuikaCD();
//
//		// 追加健診コード一覧比較処理
//		for (int i = 0; i < result.size(); i++) {
//			item = result.get(i);
//
//			String code = item.get(PrintDefine.CODE_KOUMOKU);
//			// edit s.inoue 2010/04/21
//			if (KihonKensaKoumoku.TSUIKA_CODES.contains(code) ||
//					dokuji.contains(code)) {
//				rtnList.add(item);
//				// System.out.println("追" + item.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//			}else{
//				// add s.inoue 2014/02/20
//				// System.out.println("基" + item.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//			}
//		}
//		return rtnList;
//	}
//
//	private static String getSelectKensaKekkaAllSql() {
//		StringBuilder queryAll = new StringBuilder();
//
//		queryAll.append("SELECT DISTINCT ");
//		// edit s.inoue 2009/10/28
//		queryAll.append("TK.MAX_BYTE_LENGTH,TS.KENSA_NENGAPI,TK.KOUMOKU_CD,TK.KOUMOKU_NAME,TK.KENSA_HOUHOU, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, ");
//		queryAll.append("TK.JS_JYOUGEN, TK.JS_KAGEN, TS.JISI_KBN, TS.H_L, TK.HISU_FLG, TK.DATA_TYPE ");
//		queryAll.append(",TS.KEKA_TI ");
//// del s.inoue 2014/03/19 なぜ下のコードを追加したのか謎(エラーの原因)なので、削除
////		// add s.inoue 2009/09/15 TS.KEKA_TIの代わり
////		queryAll.append(" ,case when TK.KOUMOKU_CD = '9N401000000000011' then ");
////		queryAll.append(" (SELECT CODE_NAME FROM T_DATA_TYPE_CODE ");
////		queryAll.append(" WHERE KOUMOKU_CD = '9N401000000000011' AND CODE_NUM = TS.KEKA_TI) ");
////		queryAll.append(" else TS.KEKA_TI end KEKA_TI ");
//		queryAll.append("FROM T_KENSAKEKA_SONOTA TS,T_KENSHINMASTER TK, ");
//		queryAll.append("  (SELECT TN.UKETUKE_ID,TK.KENSA_NENGAPI ");
//		queryAll.append("  FROM T_NAYOSE TN,T_KENSAKEKA_TOKUTEI TK ");
//		queryAll.append("  WHERE TN.NAYOSE_NO = ");
//		queryAll.append("   (SELECT NAYOSE_NO FROM T_NAYOSE TN ");
//		queryAll.append("   WHERE TN.UKETUKE_ID = ? )");
//		queryAll.append("  AND TN.UKETUKE_ID = TK.UKETUKE_ID) TN_KEY ");
//		queryAll.append("WHERE TS.UKETUKE_ID = TN_KEY.UKETUKE_ID ");
//		queryAll.append("AND TS.KENSA_NENGAPI = TN_KEY.KENSA_NENGAPI ");
//		queryAll.append("AND TS.KEKA_TI <> '' ");
//		queryAll.append("AND HISU_FLG <> '1' ");
//		queryAll.append("AND TS.KOUMOKU_CD = TK.KOUMOKU_CD ");
//		queryAll.append("AND TK.HKNJANUM = ? ");
//		queryAll.append("ORDER BY KENSA_NENGAPI DESC,XMLITEM_SEQNO ");
//
//		return queryAll.toString();
//	}
//
//
//	private static String getSelectKensaKekkaSql() {
//		StringBuilder queryFirst = new StringBuilder();
//
//		queryFirst.append("SELECT DISTINCT ");
//		// edit s.inoue 2009/10/28
//		queryFirst.append("TK.MAX_BYTE_LENGTH,TS.KENSA_NENGAPI,TK.KOUMOKU_CD,TK.KOUMOKU_NAME,TK.KENSA_HOUHOU, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, ");
//		queryFirst.append("TK.JS_JYOUGEN, TK.JS_KAGEN, TS.JISI_KBN, TS.H_L, TK.HISU_FLG, TK.DATA_TYPE,TK.XMLITEM_SEQNO ");
//		queryFirst.append(",TS.KEKA_TI ");
//		// del s.inoue 2014/03/19 なぜ下のコードを追加したのか謎(エラーの原因)なので、削除
////		// add s.inoue 2009/09/15 TS.KEKA_TIの代わり
////		queryFirst.append(" ,case when TK.KOUMOKU_CD = '9N401000000000011' then ");
////		queryFirst.append(" (SELECT CODE_NAME FROM T_DATA_TYPE_CODE ");
////		queryFirst.append(" WHERE KOUMOKU_CD = '9N401000000000011' AND CODE_NUM = TS.KEKA_TI) ");
////		queryFirst.append(" else TS.KEKA_TI end KEKA_TI");
//		queryFirst.append(" From T_KENSAKEKA_SONOTA TS LEFT JOIN T_KENSHINMASTER TK ");
//		queryFirst.append(" ON TS.KOUMOKU_CD = TK.KOUMOKU_CD ");
//
//		queryFirst.append(" WHERE TS.UKETUKE_ID = ?");
//		queryFirst.append(" AND TS.KENSA_NENGAPI = ?");
//		queryFirst.append(" AND TK.HKNJANUM = ?");
//		queryFirst.append(" AND TS.KEKA_TI <> '' ");
//		queryFirst.append(" AND TK.HISU_FLG <> '1' ");
//
//		queryFirst.append(" ORDER BY KENSA_NENGAPI DESC,XMLITEM_SEQNO ");
//
//		String query = queryFirst.toString();
//
//		return query;
//	}
//
//	private static String getZenGetuKensaKekkaSql(int cd,int nengappi) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("SELECT");
//		buffer.append(" T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, ");
//		buffer.append(" T_KENSAKEKA_SONOTA.JISI_KBN AS JISI_KBN,");
//		buffer.append(" T_KENSAKEKA_SONOTA.H_L AS H_L,");
//		// buffer.append(" T_KENSAKEKA_SONOTA.KEKA_TI AS KEKA_TI,");
//
//		// add s.inoue 2009/09/15 TS.KEKA_TIの代わり
//		buffer.append(" case when T_KENSHINMASTER.KOUMOKU_CD = '9N401000000000011' then ");
//		buffer.append(" (SELECT CODE_NAME FROM T_DATA_TYPE_CODE ");
//		buffer.append(" WHERE KOUMOKU_CD = '9N401000000000011' AND CODE_NUM = T_KENSAKEKA_SONOTA.KEKA_TI) ");
//		buffer.append(" else T_KENSAKEKA_SONOTA.KEKA_TI end KEKA_TI,");
//
//		buffer.append(" T_KENSHINMASTER.DATA_TYPE AS DATA_TYPE");
//		buffer.append(" From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD)");
//		buffer.append(" WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = ?");
//		buffer.append(" AND T_KENSHINMASTER.HKNJANUM = ?");
//
//		// edit ver2 s.inoue 2009/06/08
//		buffer.append(" AND T_KENSAKEKA_SONOTA.KEKA_TI <> ''");
//
//		if(nengappi==2){
//			buffer.append(" AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI IN (?,?)");
//		} else {
//			buffer.append(" AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI IN (?)");
//		}
//
//		buffer.append(" AND T_KENSAKEKA_SONOTA.KOUMOKU_CD IN (");
//		buffer.append("?");
//		for(int i = 0; i<cd-1 ; i++ ){
//			buffer.append(",?");
//		}
//		buffer.append(")");
//		buffer.append(" ORDER BY T_KENSAKEKA_SONOTA.KOUMOKU_CD");
//
//		String query = buffer.toString();
//		return query;
//	}
//
//	/**
//	 * データタイプコード検索。
//	 */
//	public String getCodeName(String kensaCd, int code_num) {
//
//		ArrayList<Hashtable<String, String>> Result = null;
//		Hashtable<String, String> ResultItem = null;
//		String Query = null;
//		String code_name = new String();
//
//		try {
//			Query = "SELECT CODE_NAME" + " FROM T_DATA_TYPE_CODE"
//			+ " WHERE KOUMOKU_CD = "
//			+ JQueryConvert.queryConvert(kensaCd) + " AND CODE_NUM = '"
//			+ code_num + "'";
//		} catch (NullPointerException e) {
//			return "";
//		}
//		try {
//			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//			ResultItem = Result.get(0);
//			/*
//			 * 健診年月日を保持
//			 */
//			code_name = ResultItem.get("CODE_NAME");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return code_name;
//	}
//
//
//	private static final String SELECT_KIHON_CHK_SQL = getSelectKihonChkSql();
//	private static final String SELECT_ALL_KIHON_CHK_SQL = getAllSelectKihonChkSql();
//	private static final String SELECT_ALL_KIHON_CHK_KEYS_SQL = getKihonChkKeysSql();
//	private static final String SELECT_ALL_KIHON_CHK_SINGLEKEY_SQL = getKihonChkSingleKeySql();
//
//	// add s.inoue 2013/01/21
////	private static int paramLength = 0;
//
//	// add s.inoue 2009/09/20
////	private static final String SELECT_SYUKEI_LIST_SQL = getSyukeiListSql();
//
//	// edit s.inoue 2009/09/22
//	// 集計表データ取得
//	private static String getSyukeiListSql(ArrayList<String> ShukeiKey){
//
//		StringBuilder sb = new StringBuilder();
//		// edit s.inoue 2009/09/30
//		sb.append(" SELECT COUNT(*),ROW_ID,HKNJANUM, KENSA_NENGAPI, KENSA_JISI_KUBUN, KENSA_JISI_SOUSU,");
//		sb.append(" KENSA_TANKA_SOUKEI, KENSA_MADO_SOUKEI, KENSA_SEIKYU_SOUKEI, KENSA_SONOTA_SOUKEI");
//		sb.append(" from T_SYUKEI");
//		sb.append(" group by ROW_ID,HKNJANUM, KENSA_NENGAPI, KENSA_JISI_KUBUN, KENSA_JISI_SOUSU,KENSA_TANKA_SOUKEI,");
//		sb.append(" KENSA_MADO_SOUKEI, KENSA_SEIKYU_SOUKEI, KENSA_SONOTA_SOUKEI");
//		sb.append(" having COUNT(*) <= 20 AND ");
//
//		String tmpshukei = "";
//		for (int i = 0; i < ShukeiKey.size(); i++) {
//			if (i > 0)
//				tmpshukei = tmpshukei + ",";
//			tmpshukei += JQueryConvert.queryConvert(ShukeiKey.get(i));
//		}
//
//		sb.append(" HKNJANUM IN (");
//		sb.append(tmpshukei);
//		sb.append(" )");
//
//		sb.append(" order by ROW_ID ");
//		return sb.toString();
//	}
//
//	// add s.inoue 2009/09/21
//	// 集計表
//	public List<TreeMap<String, String>> getShukeiList(ArrayList<String> ShukeiKey){
//		ArrayList<TreeMap<String, String>> rtnList
//			= new ArrayList<TreeMap<String, String>>();
//		try {
//			rtnList = JApplication.kikanDatabase.sendExecuteSortedQuery( getSyukeiListSql(ShukeiKey), null);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//
//		return rtnList;
//	}
//
//	// 基本チェックリスト
//	private static String getKihonChkKeysSql(){
//		StringBuilder buffer = new StringBuilder();
//		buffer.append(" SELECT TN.UKETUKE_ID,TK.KENSA_NENGAPI");
//		buffer.append(" FROM T_NAYOSE TN,T_KENSAKEKA_TOKUTEI TK");
//		buffer.append(" WHERE TN.NAYOSE_NO = (SELECT NAYOSE_NO FROM T_NAYOSE TN WHERE TN.UKETUKE_ID = ? )");
//		buffer.append(" AND TN.UKETUKE_ID = TK.UKETUKE_ID");
//		buffer.append(" ORDER BY KENSA_NENGAPI DESC");
//		return buffer.toString();
//	}
//
//	// 基本チェックリストキー設定
//	private static String getKihonChkSingleKeySql(){
//		StringBuilder buffer = new StringBuilder();
//		buffer.append(" SELECT UKETUKE_ID,KENSA_NENGAPI");
//		buffer.append(" FROM T_KENSAKEKA_TOKUTEI");
//		buffer.append(" WHERE UKETUKE_ID = ? ");
//		return buffer.toString();
//	}
//
//	// edit ver2 s.inoue 2009/06/22
//	private static String getSelectKihonChkSql() {
//		StringBuilder buffer = new StringBuilder();
////		buffer.append("SELECT");
////		buffer.append(" T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, ");
////		buffer.append(" T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, ");
////		buffer.append(" T_KENSAKEKA_SONOTA.KEKA_TI AS KEKA_TI,");
////		buffer.append(" T_KENSHINMASTER.DATA_TYPE AS DATA_TYPE");
////		buffer.append(" From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD)");
////		buffer.append(" WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = ?");
////		buffer.append(" AND T_KENSHINMASTER.HKNJANUM = ?");
////		buffer.append(" AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = ?");
////		buffer.append(" AND T_KENSAKEKA_SONOTA.KOUMOKU_CD IN  ");
////		buffer.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
////		buffer.append(" ORDER BY T_KENSAKEKA_SONOTA.KOUMOKU_CD");
//
//		buffer.append("SELECT DISTINCT TS.KENSA_NENGAPI,TK.KOUMOKU_CD,");
//		buffer.append("TK.KOUMOKU_NAME, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, TS.KEKA_TI, TK.DATA_TYPE");
//
//		buffer.append(" From T_KENSAKEKA_SONOTA TS,T_KENSHINMASTER TK ");
//		buffer.append(" WHERE TS.UKETUKE_ID = ?");
//		buffer.append(" AND TS.KOUMOKU_CD = TK.KOUMOKU_CD ");
//		buffer.append(" AND TK.HKNJANUM = ?");
//		buffer.append(" AND TS.KENSA_NENGAPI = ?");
//		buffer.append(" AND TS.KEKA_TI <> '' ");
//		buffer.append(" AND TK.HISU_FLG <> '1' ");
//		buffer.append(" AND TS.KOUMOKU_CD IN");
//		// eidt s.inoue 2013/01/22
//		// 29個から25個へ変更？？？なぜ変更
//		// buffer.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
//		buffer.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//
//		// add s.inoue 2013/01/21
////		String bf = "";
////		for (int i = 0; i < paramLength; i++) {
////			bf +="?";
////			if(i != paramLength-1)
////				bf += ",";
////		}
////		if (paramLength > 0)
////		buffer.append("(" + bf + ")");
//		buffer.append(" ORDER BY KENSA_NENGAPI DESC,XMLITEM_SEQNO ");
//		System.out.println(buffer.toString());
//		return buffer.toString();
//	}
//
//	// edit ver2 s.inoue 2009/06/22
//	private static String getAllSelectKihonChkSql() {
//		StringBuilder buffer = new StringBuilder();
//
//		buffer.append("SELECT DISTINCT TS.KENSA_NENGAPI,TK.KOUMOKU_CD,");
//		buffer.append("TK.KOUMOKU_NAME, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, TS.KEKA_TI, TK.DATA_TYPE");
//		buffer.append(" FROM T_KENSAKEKA_SONOTA TS,T_KENSHINMASTER TK,");
//		buffer.append(" (SELECT TN.UKETUKE_ID,TK.KENSA_NENGAPI");
//		buffer.append(" FROM T_NAYOSE TN,T_KENSAKEKA_TOKUTEI TK");
//		buffer.append(" WHERE TN.NAYOSE_NO = (SELECT NAYOSE_NO FROM T_NAYOSE TN WHERE TN.UKETUKE_ID = ? )");
//		buffer.append(" AND TN.UKETUKE_ID = TK.UKETUKE_ID) TN_KEY");
//		buffer.append(" WHERE TS.UKETUKE_ID = TN_KEY.UKETUKE_ID");
//		buffer.append(" AND TS.KENSA_NENGAPI = TN_KEY.KENSA_NENGAPI");
//		buffer.append(" AND TS.KEKA_TI <> ''");
//		buffer.append(" AND HISU_FLG <> '1'");
//		buffer.append(" AND TS.KOUMOKU_CD = TK.KOUMOKU_CD");
//		buffer.append(" AND TK.HKNJANUM = ? ");
//		buffer.append(" AND TS.KOUMOKU_CD IN");
//		buffer.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
//
//		return buffer.toString();
//	}
//
//	// 生活基本チェックリスト
//	public List<TreeMap<String,String>> getKihonChkKeys(String[] params,String kensaNenGappi,List kensaNenList){
//		ArrayList<TreeMap<String, String>> rtnList = new ArrayList<TreeMap<String, String>>();
//
//		try {
//			// 今年度かどうか
//			boolean blnYear = FiscalYearUtil.getJugeThisYear(kensaNenGappi);
//			// edit ver2 s.inoue 2009/07/06
//			// 該当件数が1件かどうか
//			if(blnYear && kensaNenList.size() > 1){
//				rtnList = JApplication.kikanDatabase.sendExecuteSortedQuery(SELECT_ALL_KIHON_CHK_KEYS_SQL, params);
//			}else{
//				rtnList = JApplication.kikanDatabase.sendExecuteSortedQuery(SELECT_ALL_KIHON_CHK_SINGLEKEY_SQL, params);
//			}
//
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//
//		return rtnList;
//	}
//	/**
//	 * 生活基本チェックリスト項目取得
//	 *
//	 * @param KojinData
//	 * @param kensaNenGappi
//	 * @param inFlg
//	 * @param coumokuCd
//	 * @return
//	 */
//	public List<TreeMap<String, String>> getKihonChk(
//			Hashtable<String, String> KojinData,
//			String kensaNenGappi,
//			List coumokuCd,
//			TreeMap<String, String> resultItemKeys) {
//		// edit ver2 s.inoue 2009/06/22
//		//ArrayList<Hashtable<String, String>> result = null;
//		ArrayList<TreeMap<String, String>> result = null;
//
//		TreeMap<String, String> item = null;
//
//		String strCoumokuCd ="";
//
//		ArrayList<TreeMap<String, String>> rtnList = new ArrayList<TreeMap<String, String>>();
//
//		try {
//
//				for (int i = 0 ;i<coumokuCd.size();i++){
//					strCoumokuCd = strCoumokuCd + (String)coumokuCd.get(i);
//
//					if(coumokuCd.size()-1!=i){
//						strCoumokuCd = strCoumokuCd + ",";
//					}
//				}
////				// 今年度かどうか
////				boolean blnYear = FiscalYearUtil.getJugeThisYear(kensaNenGappi);
//
//				String[] codes = strCoumokuCd.split(",");
//				String[] nengappis = kensaNenGappi.split(",");
//
//				ArrayList<String> paramList = new ArrayList<String>();
//
//				// edit ver2 s.inoue 2009/06/23
//				//paramList.add(KojinData.get("UKETUKE_ID"));
//				paramList.add(resultItemKeys.get("UKETUKE_ID"));
//				paramList.add(KojinData.get("HKNJANUM"));
//
////				if (!blnYear){
//					// edit ver2 s.inoue 2009/06/23
//					//paramList.addAll(Arrays.asList(nengappis));
//					paramList.addAll(Arrays.asList(resultItemKeys.get("KENSA_NENGAPI")));
////				}
//				paramList.addAll(Arrays.asList(codes));
//
//				String[] params = new String[paramList.size()];
//				for (int i = 0; i < params.length; i++) {
//					params[i] = paramList.get(i);
//				}
//
//				// 今年度⇒過去3件分,それ以外⇒該当年度分
//				//result = JApplication.kikanDatabase.sendExecuteQuery(SELECT_KIHON_CHK_SQL, params);
////				if (blnYear){
////					//String[] params = {kojinData.get("UKETUKE_ID"), kojinData.get("HKNJANUM")};
////					result = JApplication.kikanDatabase.sendExecuteSortedQuery(SELECT_ALL_KIHON_CHK_SQL, params);
////				}else{
//					//String[] params = {kojinData.get("UKETUKE_ID"),kensaNenGappi,kojinData.get("HKNJANUM")};
//
//				// add s.inoue 2013/01/21
////				this.paramLength = params.length;
////				String ss = getSelectKihonChkSql();
//
//				result = JApplication.kikanDatabase.sendExecuteSortedQuery(SELECT_KIHON_CHK_SQL, params);
//				// result = JApplication.kikanDatabase.sendExecuteSortedQuery(buffer.toString(), params);
//
//				// ex
////				String sb = "SELECT DISTINCT TS.KENSA_NENGAPI,TK.KOUMOKU_CD,TK.KOUMOKU_NAME, TK.TANI, TK.DS_JYOUGEN, TK.DS_KAGEN, TS.KEKA_TI, TK.DATA_TYPE From T_KENSAKEKA_SONOTA TS,T_KENSHINMASTER TK  WHERE TS.UKETUKE_ID = '201209060002' AND TS.KOUMOKU_CD = TK.KOUMOKU_CD  AND TK.HKNJANUM = '34420018' AND TS.KENSA_NENGAPI = '20120824' --AND TS.KEKA_TI <> ''"
////				 + "AND TK.HISU_FLG <> '1'  AND TS.KOUMOKU_CD IN"
////				 // + "('9N811000000000011','9N816000000000011','9N821000000000011','9N826000000000011','9N831000000000011','9N836000000000011','9N841000000000011','9N846000000000011','9N851000000000011','9N856000000000011','9N861000000000011','9N866000000000001','9N871000000000011','9N876000000000011','9N881000000000011','9N886000000000011','9N891000000000011','9N896000000000011','9N901000000000011','9N906000000000011','9N911000000000011','9N916000000000011','9N921000000000011','9N926000000000011','9N931000000000011')"
////				 + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
////				 + "ORDER BY KENSA_NENGAPI DESC,XMLITEM_SEQNO ";
////
////				ArrayList<Hashtable<String, String>> as = JApplication.kikanDatabase.sendExecuteQuery(getSelectKihonChkSql(),params);
////				System.out.println(as.get(0));
////				}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			item = result.get(i);
//
//			String code = item.get(PrintDefine.CODE_KOUMOKU);
//
//			if (KihonKensaKoumoku.TSUIKA_CODES.contains(code)) {
//				rtnList.add(item);
//			}
//		}
//
//		return rtnList;
//	}
//
//}
