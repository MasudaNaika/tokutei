package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;


public class JSyuukeiProcess {

	private static org.apache.log4j.Logger logger =
		Logger.getLogger(JSyuukeiProcess.class);

	public static boolean RunProcess(Vector<JKessaiProcessData> data)
	{
		// 集計データテーブルの中身を削除
// del s.inoue 2009/08/20
//		try {
//			// edit ver2 s.inoue 2009/08/20
//			JApplication.kikanDatabase.sendNoResultQuery("DELETE FROM T_SYUKEI_WK");
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M8701", null);
//			return false;
//		}

		// <保険者番号,対応するオブジェクト>
		Hashtable<String,Vector<JKessaiProcessData>> ArgHokenjyaList = new Hashtable<String,Vector<JKessaiProcessData>>();

		// <代行機関番号,対応するオブジェクト>
		Hashtable<String,Vector<JKessaiProcessData>> ArgDaikouList = new Hashtable<String,Vector<JKessaiProcessData>>();

		for(int i = 0 ; i < data.size() ; i++)
		{
			JKessaiProcessData KessaiData = data.get(i);

			if(KessaiData.daikouKikanNumber.isEmpty())
			{
				// 保険者情報から情報を取得
				if(!ArgHokenjyaList.containsKey(String.valueOf(KessaiData.hokenjyaNumber)))
				{
					ArgHokenjyaList.put(
							String.valueOf(KessaiData.hokenjyaNumber),
							new Vector<JKessaiProcessData>()
							);
				}

				ArgHokenjyaList.get(String.valueOf(KessaiData.hokenjyaNumber)).add(KessaiData);
			}else{
				// 代行機関番号から情報を取得
				if(!ArgDaikouList.containsKey(String.valueOf(KessaiData.daikouKikanNumber)))
				{
					ArgDaikouList.put(
							String.valueOf(KessaiData.daikouKikanNumber),
							new Vector<JKessaiProcessData>()
							);
				}

				ArgDaikouList.get(String.valueOf(KessaiData.daikouKikanNumber)).add(KessaiData);
			}
		}

		// 代行機関
		Enumeration<String> ArgDaikou = ArgDaikouList.keys();
		while(ArgDaikou.hasMoreElements())
		{
			String Daikou = ArgDaikou.nextElement();

			/* 代行機関ごとの集計を行う */
			if(HokenjyaProcess(Daikou,ArgDaikouList.get(Daikou),"1") == false)
			{
				return false;
			}
		}

		// 保険者
		Enumeration<String> ArgHokenjya = ArgHokenjyaList.keys();
		while(ArgHokenjya.hasMoreElements())
		{
			String Hokenjya = ArgHokenjya.nextElement();

			/* 保険者ごとの集計を行う */
			if(HokenjyaProcess(Hokenjya,ArgHokenjyaList.get(Hokenjya),"6") == false)
			{
				return false;
			}
		}

		return true;
	}

	// edit s.inoue 2009/09/16
	/**
	 * 削除処理を行う
	 */
	public static void deleteSyukei(String HokenjyaNumber)
	{
		// T_KESAIデータ削除
		StringBuilder workKesaiQuery = new StringBuilder();
		try{

			workKesaiQuery.append("DELETE FROM T_SYUKEI ");
			workKesaiQuery.append(" WHERE HKNJANUM = ");
			workKesaiQuery.append(JQueryConvert.queryConvert(HokenjyaNumber));

			JApplication.kikanDatabase.sendNoResultQuery(workKesaiQuery.toString());

		}catch(SQLException err)
		{
			err.printStackTrace();
			logger.error(err.getMessage());
		}
	}

	// edit s.inoue 2009/09/16
	/**
	 * 削除処理を行う
	 */
	public static void deleteSyukeiWork(String HokenjyaNumber)
	{
		// T_KESAIデータ削除
		StringBuilder workKesaiQuery = new StringBuilder();
		try{

			workKesaiQuery.append("DELETE FROM T_SYUKEI_WK ");
			workKesaiQuery.append(" WHERE HKNJANUM = ");
			workKesaiQuery.append(JQueryConvert.queryConvert(HokenjyaNumber));

			JApplication.kikanDatabase.sendNoResultQuery(workKesaiQuery.toString());

		}catch(SQLException err)
		{
			err.printStackTrace();
			logger.error(err.getMessage());
		}
	}

	// edit ver2 s.inoue 2009/09/10
	/**
	 * 集計確定処理 (work → kessai)
	 * @param kessaiDatas 処理を行う受信者のリスト
	 * @param tKikanNumber 健診を行った機関の番号
	 */
	public static boolean runWorkToSyukeiProcess(Vector<JKessaiProcessData> data) throws Exception
	{
		// <保険者番号,対応するオブジェクト>
		Hashtable<String,Vector<JKessaiProcessData>> ArgHokenjyaList = new Hashtable<String,Vector<JKessaiProcessData>>();

		// <代行機関番号,対応するオブジェクト>
		Hashtable<String,Vector<JKessaiProcessData>> ArgDaikouList = new Hashtable<String,Vector<JKessaiProcessData>>();

		for(int i = 0 ; i < data.size() ; i++)
		{
			JKessaiProcessData KessaiData = data.get(i);

			if(KessaiData.daikouKikanNumber.isEmpty())
			{
				// 保険者情報から情報を取得
				if(!ArgHokenjyaList.containsKey(String.valueOf(KessaiData.hokenjyaNumber)))
				{
					ArgHokenjyaList.put(
							String.valueOf(KessaiData.hokenjyaNumber),
							new Vector<JKessaiProcessData>()
							);
				}

				ArgHokenjyaList.get(String.valueOf(KessaiData.hokenjyaNumber)).add(KessaiData);
			}else{
				// 代行機関番号から情報を取得
				if(!ArgDaikouList.containsKey(String.valueOf(KessaiData.daikouKikanNumber)))
				{
					ArgDaikouList.put(
							String.valueOf(KessaiData.daikouKikanNumber),
							new Vector<JKessaiProcessData>()
							);
				}

				ArgDaikouList.get(String.valueOf(KessaiData.daikouKikanNumber)).add(KessaiData);
			}
		}

		// edit s.inoue 2009/09/11
		// 集計登録処理

		// 代行機関
		Enumeration<String> ArgDaikou = ArgDaikouList.keys();
		while(ArgDaikou.hasMoreElements())
		{
			String Daikou = ArgDaikou.nextElement();

			/* 代行機関ごとの集計を行う */
			if(HokenjyaWorkToSyukeiProcess(Daikou,ArgDaikouList.get(Daikou),"1") == false)
			{
				return false;
			}
		}

		// 保険者
		Enumeration<String> ArgHokenjya = ArgHokenjyaList.keys();
		while(ArgHokenjya.hasMoreElements())
		{
			String Hokenjya = ArgHokenjya.nextElement();

			/* 保険者ごとの集計を行う */
			if(HokenjyaWorkToSyukeiProcess(Hokenjya,ArgHokenjyaList.get(Hokenjya),"6") == false)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * 保険者・代行機関ごとの集計を行う
	 * @param HokenjyaNumber 保険者番号・代行機関番号
	 * @param KensaDate 実施日
	 * @param SyubetuCode 種別コード
	 */
	private static boolean HokenjyaProcess(String HokenjyaNumber,
			Vector<JKessaiProcessData> Data,String SyubetuCode)
	{
		String HKNJANUM = HokenjyaNumber;
		String KENSA_JISI_KUBUN = "1";
		String KENSA_JISI_SOUSU = "";
		String KENSA_TANKA_SOUKEI = "";
		String KENSA_MADO_SOUKEI = "";
		String KENSA_SEIKYU_SOUKEI = "";
		String KENSA_SONOTA_SOUKEI = "";

		Hashtable<String,String> KessaiItem = null;

		try {
			int iKENSA_JISI_SOUSU = 0;
			int iKENSA_TANKA_SOUKEI = 0;
			int iKENSA_MADO_SOUKEI = 0;
			int iKENSA_SEIKYU_SOUKEI = 0;
			int iKENSA_SONOTA_SOUKEI = 0;

			for(int i = 0 ; i < Data.size() ; i++)
			{
				// edit ver2 s.inoue 2009/08/21
				StringBuilder sb = new StringBuilder();

				sb.append("SELECT TKIKAN_NO, JISI_KBN, KENSA_NENGAPI, SEIKYU_KBN, SYUBETU_CD, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, ITAKU_KBN,");
				sb.append(" TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, MADO_FUTAN_K_SYUBETU,");
				sb.append(" MADO_FUTAN_KIHON, MADO_FUTAN_KIHON_OUT, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_SYOUSAI_OUT,");
				sb.append(" MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_TSUIKA_OUT, TANKA_GOUKEI, MADO_FUTAN_GOUKEI, SEIKYU_KINGAKU,");
				sb.append(" SIHARAI_DAIKOU_BANGO, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI,");
				sb.append(" HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC, TANKA_NINGENDOC, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, MADO_FUTAN_DOC_OUT,UPDATE_TIMESTAMP ");
				// keyが不正な為修正
				sb.append(" FROM T_KESAI_WK ");
				sb.append(" WHERE HKNJANUM = ");
				sb.append(JQueryConvert.queryConvert(Data.get(i).hokenjyaNumber));
				sb.append(" AND UKETUKE_ID = ");
			  	sb.append( JQueryConvert.queryConvert(Data.get(i).uketukeId));
			  	sb.append(" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(Data.get(i).KensaDate));

				KessaiItem = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);

//				KessaiItem = JApplication.kikanDatabase.sendExecuteQuery(
//						"SELECT * FROM T_KESAI_WK WHERE " +
//						"KENSA_NENGAPI = " + JQueryConvert.queryConvert(Data.get(i).KensaDate) +
//						" AND HKNJANUM = " + JQueryConvert.queryConvert(Data.get(i).hokenjyaNumber) +
//						" AND UKETUKE_ID = " + JQueryConvert.queryConvert(Data.get(i).uketukeId)).get(0);

				iKENSA_JISI_SOUSU++;
				iKENSA_TANKA_SOUKEI += JInteger.valueOf(KessaiItem.get("TANKA_GOUKEI"));
				iKENSA_MADO_SOUKEI += JInteger.valueOf(KessaiItem.get("MADO_FUTAN_GOUKEI"));
				iKENSA_SEIKYU_SOUKEI += JInteger.valueOf(KessaiItem.get("SEIKYU_KINGAKU"));
				iKENSA_SONOTA_SOUKEI += JInteger.valueOf(KessaiItem.get("MADO_FUTAN_SONOTA"));
			}

			KENSA_JISI_SOUSU = String.valueOf(iKENSA_JISI_SOUSU);
			KENSA_TANKA_SOUKEI = String.valueOf(iKENSA_TANKA_SOUKEI);
			KENSA_MADO_SOUKEI = String.valueOf(iKENSA_MADO_SOUKEI);
			KENSA_SEIKYU_SOUKEI = String.valueOf(iKENSA_SEIKYU_SOUKEI);
			KENSA_SONOTA_SOUKEI = String.valueOf(iKENSA_SONOTA_SOUKEI);

		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			// del s.inoue 2009/10/29
			// jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M8703", null);
			return false;
		}

		try {

			// edit ver2 s.inoue 2009/08/21
			StringBuilder sb = new StringBuilder();
			// edit s.inoue 2009/10/17
			// sb.append("SELECT COUNT(HKNJANUM) SYUKEI_CNT FROM T_SYUKEI_WK ");
			// edit s.inoue 2009/10/19 T_SYUKEI→T_SYUKEI_WKへ再び
			// sb.append("SELECT COUNT(HKNJANUM) SYUKEI_CNT FROM T_SYUKEI ");
			sb.append("SELECT COUNT(HKNJANUM) SYUKEI_CNT FROM T_SYUKEI_WK ");
			sb.append(" WHERE HKNJANUM = ");
			sb.append(JQueryConvert.queryConvert(HKNJANUM));

			// edit s.inoue 2009/09/16
			sb.append(" AND ROW_ID = (select MAX(ROW_ID) from T_SYUKEI_WK) ");

			KessaiItem = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);
			// edit ver2 s.inoue 2009/08/27
			int SyukeiCnt = Integer.parseInt(KessaiItem.get("SYUKEI_CNT"));

			// timestamp取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			StringBuilder sbSyukei = new StringBuilder();

			// edit ver2 s.inoue 2009/08/24
			// 新規又は更新の判別を行う
			if (SyukeiCnt > 0){

				sbSyukei.append(" UPDATE T_SYUKEI_WK SET ");
//				sbSyukei.append(" HKNJANUM = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(HKNJANUM));
				sbSyukei.append(" KENSA_JISI_KUBUN = ");
				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_JISI_KUBUN));
				sbSyukei.append(" KENSA_JISI_SOUSU = ");
				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_JISI_SOUSU));
				// edit ver2 s.inoue 2009/08/24
				// sbSyukei.append(" KENSA_NENGAPI = ");
				// sbSyukei.append( JQueryConvert.queryConvertAppendComma("000000"));
				sbSyukei.append(" KENSA_TANKA_SOUKEI = ");
				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_TANKA_SOUKEI));
				sbSyukei.append(" KENSA_MADO_SOUKEI = ");
				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_MADO_SOUKEI));
				sbSyukei.append(" KENSA_SEIKYU_SOUKEI = ");
				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_SEIKYU_SOUKEI));
				sbSyukei.append(" KENSA_SONOTA_SOUKEI = ");
				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_SONOTA_SOUKEI));
				sbSyukei.append(" UPDATE_TIMESTAMP = ");
				sbSyukei.append( JQueryConvert.queryConvert(stringTimeStamp));

				sbSyukei.append(" WHERE HKNJANUM = ");
				sbSyukei.append( JQueryConvert.queryConvert(HKNJANUM));
				sbSyukei.append(" AND KENSA_NENGAPI = ");
				sbSyukei.append( JQueryConvert.queryConvert("000000"));

				// edit s.inoue 2009/09/16
				sbSyukei.append(" AND ROW_ID = (select MAX(ROW_ID) from T_SYUKEI_WK) ");


			}else{

				// edit s.inoue 2009/09/16
				// ROW_ID追加
				sbSyukei.append(" INSERT INTO T_SYUKEI_WK");
				sbSyukei.append(" (ROW_ID,HKNJANUM,KENSA_JISI_KUBUN,KENSA_JISI_SOUSU,KENSA_NENGAPI,KENSA_TANKA_SOUKEI,");
				sbSyukei.append(" KENSA_MADO_SOUKEI,KENSA_SEIKYU_SOUKEI,KENSA_SONOTA_SOUKEI,UPDATE_TIMESTAMP ");
				sbSyukei.append(" ) ");
				sbSyukei.append("SELECT case when max(row_id) is null then 1 else MAX(ROW_ID)+1 end, ");
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(HKNJANUM));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_JISI_KUBUN));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_JISI_SOUSU));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma("000000"));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_TANKA_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_MADO_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_SEIKYU_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_SONOTA_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvert(stringTimeStamp));
				sbSyukei.append(" FROM T_SYUKEI_WK");
// edit ver2 s.inoue 2009/08/21
//				JApplication.kikanDatabase.sendNoResultQuery("INSERT INTO T_SYUKEI_WK " +
//						"(HKNJANUM," +
//						"KENSA_JISI_KUBUN," +
//						"KENSA_JISI_SOUSU," +
//						"KENSA_NENGAPI," +
//						"KENSA_TANKA_SOUKEI," +
//						"KENSA_MADO_SOUKEI," +
//						"KENSA_SEIKYU_SOUKEI," +
//						"KENSA_SONOTA_SOUKEI" +
//						")" +
//						"VALUES (" +
//						JQueryConvert.queryConvertAppendComma(HKNJANUM) +
//						JQueryConvert.queryConvertAppendComma(KENSA_JISI_KUBUN) +
//						JQueryConvert.queryConvertAppendComma(KENSA_JISI_SOUSU) +
//						JQueryConvert.queryConvertAppendComma("000000") +
//						JQueryConvert.queryConvertAppendComma(KENSA_TANKA_SOUKEI) +
//						JQueryConvert.queryConvertAppendComma(KENSA_MADO_SOUKEI) +
//						JQueryConvert.queryConvertAppendComma(KENSA_SEIKYU_SOUKEI) +
//						JQueryConvert.queryConvert(KENSA_SONOTA_SOUKEI) + ")");
			}

			JApplication.kikanDatabase.sendNoResultQuery(sbSyukei.toString());

		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			// del s.inoue 2009/08/27
			// jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M8704", null);
			return false;
		}

		return true;
	}

	/**
	 * 保険者・代行機関ごとの集計を行う work→Syukei
	 * @param HokenjyaNumber 保険者番号・代行機関番号
	 * @param KensaDate 実施日
	 * @param SyubetuCode 種別コード
	 */
	private static boolean HokenjyaWorkToSyukeiProcess(String HokenjyaNumber,
			Vector<JKessaiProcessData> Data,String SyubetuCode)
	{
		String HKNJANUM = HokenjyaNumber;
		String KENSA_JISI_KUBUN = "1";
		String KENSA_JISI_SOUSU = "";
		String KENSA_TANKA_SOUKEI = "";
		String KENSA_MADO_SOUKEI = "";
		String KENSA_SEIKYU_SOUKEI = "";
		String KENSA_SONOTA_SOUKEI = "";

		Hashtable<String,String> KessaiItem = null;

		try {
			int iKENSA_JISI_SOUSU = 0;
			int iKENSA_TANKA_SOUKEI = 0;
			int iKENSA_MADO_SOUKEI = 0;
			int iKENSA_SEIKYU_SOUKEI = 0;
			int iKENSA_SONOTA_SOUKEI = 0;

			for(int i = 0 ; i < Data.size() ; i++)
			{
				StringBuilder sb = new StringBuilder();
				 ArrayList<Hashtable<String, String>> kesaiWk =new ArrayList<Hashtable<String,String>>();

				// T_KESAI_WK(未コミットの為、_WKからデータ取得)
				sb.append("SELECT TKIKAN_NO, JISI_KBN, KENSA_NENGAPI, SEIKYU_KBN, SYUBETU_CD, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, ITAKU_KBN,");
				sb.append(" TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, MADO_FUTAN_K_SYUBETU,");
				sb.append(" MADO_FUTAN_KIHON, MADO_FUTAN_KIHON_OUT, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_SYOUSAI_OUT,");
				sb.append(" MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_TSUIKA_OUT, TANKA_GOUKEI, MADO_FUTAN_GOUKEI, SEIKYU_KINGAKU,");
				sb.append(" SIHARAI_DAIKOU_BANGO, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI,");
				sb.append(" HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC, TANKA_NINGENDOC, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, MADO_FUTAN_DOC_OUT,UPDATE_TIMESTAMP ");
				// edit s.inoue 2009/10/17
				// edit s.inoue 2009/09/16
				sb.append(" FROM T_KESAI ");
				// sb.append(" FROM T_KESAI_WK ");
				sb.append(" WHERE HKNJANUM = ");
				sb.append(JQueryConvert.queryConvert(Data.get(i).hokenjyaNumber));
				sb.append(" AND UKETUKE_ID = ");
			  	sb.append( JQueryConvert.queryConvert(Data.get(i).uketukeId));
			  	sb.append(" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(Data.get(i).KensaDate));

			  	kesaiWk = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
			  	if (kesaiWk.size() > 0) {
			  		KessaiItem = kesaiWk.get(0);
				}else{
					// JErrorMessage.show("M8703", null);
					return false;
				}

				iKENSA_JISI_SOUSU++;
				iKENSA_TANKA_SOUKEI += JInteger.valueOf(KessaiItem.get("TANKA_GOUKEI"));
				iKENSA_MADO_SOUKEI += JInteger.valueOf(KessaiItem.get("MADO_FUTAN_GOUKEI"));
				iKENSA_SEIKYU_SOUKEI += JInteger.valueOf(KessaiItem.get("SEIKYU_KINGAKU"));
				iKENSA_SONOTA_SOUKEI += JInteger.valueOf(KessaiItem.get("MADO_FUTAN_SONOTA"));
			}

			KENSA_JISI_SOUSU = String.valueOf(iKENSA_JISI_SOUSU);
			KENSA_TANKA_SOUKEI = String.valueOf(iKENSA_TANKA_SOUKEI);
			KENSA_MADO_SOUKEI = String.valueOf(iKENSA_MADO_SOUKEI);
			KENSA_SEIKYU_SOUKEI = String.valueOf(iKENSA_SEIKYU_SOUKEI);
			KENSA_SONOTA_SOUKEI = String.valueOf(iKENSA_SONOTA_SOUKEI);

			// add s.inoue 2009/10/17
			deleteSyukei(HKNJANUM);
		}catch(Exception ex){
			logger.error(ex.getMessage());
			// JErrorMessage.show("M8703", null);
			return false;
		}

		try {

			// T_SYUKEIカウント(未コミットの為、_WKからデータ取得)
			StringBuilder sb = new StringBuilder();
			// edit s.inoue 2009/10/17
			// sb.append("SELECT COUNT(HKNJANUM) SYUKEI_CNT FROM T_SYUKEI_WK ");
			sb.append("SELECT COUNT(HKNJANUM) SYUKEI_CNT FROM T_SYUKEI ");

			sb.append(" WHERE HKNJANUM = ");
			sb.append(JQueryConvert.queryConvert(HKNJANUM));

			KessaiItem = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);
			int SyukeiCnt = Integer.parseInt(KessaiItem.get("SYUKEI_CNT"));

			// timestamp取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			StringBuilder sbSyukei = new StringBuilder();
// edit s.inoue 2009/10/17
// delete insertへ
			// 新規又は更新の判別を行う
//			if (SyukeiCnt > 0){
//
//				// T_SYUKEI 実テーブル更新
//				sbSyukei.append(" UPDATE T_SYUKEI SET ");
//				sbSyukei.append(" KENSA_JISI_KUBUN = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_JISI_KUBUN));
//				sbSyukei.append(" KENSA_JISI_SOUSU = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_JISI_SOUSU));
//				sbSyukei.append(" KENSA_TANKA_SOUKEI = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_TANKA_SOUKEI));
//				sbSyukei.append(" KENSA_MADO_SOUKEI = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_MADO_SOUKEI));
//				sbSyukei.append(" KENSA_SEIKYU_SOUKEI = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_SEIKYU_SOUKEI));
//				sbSyukei.append(" KENSA_SONOTA_SOUKEI = ");
//				sbSyukei.append( JQueryConvert.queryConvertAppendComma(KENSA_SONOTA_SOUKEI));
//				sbSyukei.append(" UPDATE_TIMESTAMP = ");
//				sbSyukei.append( JQueryConvert.queryConvert(stringTimeStamp));
//
//				sbSyukei.append(" WHERE HKNJANUM = ");
//				sbSyukei.append( JQueryConvert.queryConvert(HKNJANUM));
//				sbSyukei.append(" AND KENSA_NENGAPI = ");
//				sbSyukei.append( JQueryConvert.queryConvert("000000"));
//
//
//			}else{

				// edit s.inoue 2009/09/16
				// T_SYUKEI 実テーブルへ新規登録
				sbSyukei.append(" INSERT INTO T_SYUKEI ");
				sbSyukei.append(" (ROW_ID,HKNJANUM,KENSA_JISI_KUBUN,KENSA_JISI_SOUSU,KENSA_NENGAPI,KENSA_TANKA_SOUKEI,");
				sbSyukei.append(" KENSA_MADO_SOUKEI,KENSA_SEIKYU_SOUKEI,KENSA_SONOTA_SOUKEI,UPDATE_TIMESTAMP ");
				sbSyukei.append(" )");
				sbSyukei.append("SELECT case when max(row_id) is null then 1 else MAX(ROW_ID)+1 end, ");
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(HKNJANUM));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_JISI_KUBUN));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_JISI_SOUSU));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma("000000"));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_TANKA_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_MADO_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_SEIKYU_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvertAppendComma(KENSA_SONOTA_SOUKEI));
				sbSyukei.append(JQueryConvert.queryConvert(stringTimeStamp));
				// edit s.inoue 2009/10/18
				sbSyukei.append(" FROM T_SYUKEI_WK ");
//			}

			JApplication.kikanDatabase.sendNoResultQuery(sbSyukei.toString());

			deleteSyukeiWork(HKNJANUM);

		}catch(Exception ex){
			logger.error(ex.getMessage());
			return false;
		}

		return true;
	}
}
