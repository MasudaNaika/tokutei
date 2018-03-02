package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JLong;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.hl7.CheckupClaimWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.ClinicalDocumentWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.IndexWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.SummaryWriter;
import jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.CheckupCard;
import jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.Settlement;
import jp.or.med.orca.jma_tokutei.common.hl7.checkupclaim.SubjectPerson;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.DocumentationOf;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.Participant;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.RecordTarget;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.RepresentedOrganization;
import jp.or.med.orca.jma_tokutei.common.hl7.clinicaldocument.component.Observation;
import jp.or.med.orca.jma_tokutei.common.hl7.index.Index;
import jp.or.med.orca.jma_tokutei.common.hl7.summary.Summary;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JOutputGetujiFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrameData;

public class JOutputHL7 {

	private static final String CODE_HUKUI_JIKOSINKOKU = "9N016160300000001";
	private static final String CODE_HUKUI_JIKOHANTEI = "9N016160200000001";
	private static final String CODE_HUKUI_JISOKU = "9N016160100000001";
	private static final String CODE_NAIZOUSIBOUMENSEKI = "9N021000000000001";
	private static final String SQL_SELECT_KIHON_DATA = createSelectTKensinData(true);
	private static final String SQL_SELECT_KENSHIN_DATA = createSelectTKensinData(false);

	private static final String OUTPUTHL7_CHARACTER_SLASH = "／";
	private static final String OUTPUTHL7_CHARACTER_HAIFUN = "‐";

	// add s.inoue 2010/01/15
	private static final String TANKA_HANTEI_KIHON = "1";
	private static final String TANKA_HANTEI_DOC = "2";

	// loggerの objectを作る。
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JOutputHL7.class);

	private static String createSelectTKensinData(boolean isTokutei){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT ");
		// edit s.inoue 2009/09/16
		buffer.append(" KOJIN.PTNUM, KOJIN.JYUSHIN_SEIRI_NO, KOJIN.YUKOU_KIGEN, KOJIN.HKNJANUM, KOJIN.HIHOKENJYASYO_KIGOU, KOJIN.HIHOKENJYASYO_NO, KOJIN.NAME, KOJIN.KANANAME, KOJIN.NICKNAME, KOJIN.BIRTHDAY, KOJIN.SEX, KOJIN.HOME_POST, KOJIN.HOME_ADRS, KOJIN.HOME_BANTI, KOJIN.HOME_TEL1, KOJIN.KEITAI_TEL, KOJIN.FAX, ");
		buffer.append(" KOJIN.EMAIL, KOJIN.KEITAI_EMAIL, KOJIN.KEIYAKU_TORIMATOME, KOJIN.KOUHUBI, KOJIN.SIHARAI_DAIKOU_BANGO, KOJIN.MADO_FUTAN_K_SYUBETU, KOJIN.MADO_FUTAN_KIHON, KOJIN.MADO_FUTAN_S_SYUBETU, KOJIN.MADO_FUTAN_SYOUSAI, KOJIN.MADO_FUTAN_T_SYUBETU, KOJIN.MADO_FUTAN_TSUIKA, KOJIN.MADO_FUTAN_D_SYUBETU, ");
		buffer.append(" KOJIN.MADO_FUTAN_DOC, KOJIN.NENDO, KOJIN.UKETUKE_ID, KOJIN.MADO_FUTAN_SONOTA, KOJIN.HOKENJYA_FUTAN_KIHON, KOJIN.HOKENJYA_FUTAN_SYOUSAI, KOJIN.HOKENJYA_FUTAN_TSUIKA, KOJIN.HOKENJYA_FUTAN_DOC, ");
		buffer.append(" TOKUTEI.KENSA_NENGAPI, TOKUTEI.K_P_NO, TOKUTEI.HANTEI_NENGAPI, TOKUTEI.TUTI_NENGAPI, TOKUTEI.KENSA_CENTER_CD, TOKUTEI.KEKA_INPUT_FLG, TOKUTEI.SEIKYU_KBN, TOKUTEI.KOMENTO, TOKUTEI.NENDO, TOKUTEI.UKETUKE_ID, TOKUTEI.NYUBI_YOUKETU, TOKUTEI.SYOKUZEN_SYOKUGO, TOKUTEI.HENKAN_NITIJI, ");
		buffer.append(" SONOTA.HIHOKENJYASYO_KIGOU, SONOTA.HIHOKENJYASYO_NO, SONOTA.KENSA_NENGAPI, SONOTA.KOUMOKU_CD, SONOTA.KEKA_TI, SONOTA.KOMENTO, SONOTA.H_L, SONOTA.HANTEI, SONOTA.NENDO, SONOTA.UKETUKE_ID, SONOTA.JISI_KBN, SONOTA.KEKA_TI_KEITAI, ");
		buffer.append(" MASTER.HKNJANUM, MASTER.KOUMOKU_CD, MASTER.KOUMOKU_NAME, MASTER.DATA_TYPE, MASTER.MAX_BYTE_LENGTH, MASTER.XML_PATTERN, MASTER.XML_DATA_TYPE, MASTER.XML_KENSA_CD, MASTER.OBSERVATION, MASTER.AUTHOR, MASTER.AUTHOR_KOUMOKU_CD,");
		buffer.append(" MASTER.KENSA_GROUP, MASTER.KENSA_GROUP_CD, MASTER.KEKKA_OID, MASTER.KOUMOKU_OID, MASTER.HISU_FLG, MASTER.KAGEN, MASTER.JYOUGEN, MASTER.DS_JYOUGEN, MASTER.DS_KAGEN, MASTER.JS_JYOUGEN, MASTER.JS_KAGEN, MASTER.KIJYUNTI_HANI, MASTER.TANI, MASTER.KENSA_HOUHOU, MASTER.TANKA_KENSIN, MASTER.BIKOU, MASTER.XMLITEM_SEQNO ");

		buffer.append(" FROM T_KENSAKEKA_TOKUTEI AS TOKUTEI");
		buffer.append(" INNER JOIN T_KENSAKEKA_SONOTA AS SONOTA");
		buffer.append(" ON");
		buffer.append(" (");
		buffer.append("  TOKUTEI.UKETUKE_ID = SONOTA.UKETUKE_ID");
		buffer.append(" )");
		buffer.append(" INNER JOIN T_KENSHINMASTER AS MASTER");
		buffer.append(" ON");
		buffer.append(" (");
		buffer.append("  SONOTA.KOUMOKU_CD = MASTER.KOUMOKU_CD");
		buffer.append(" )");
		buffer.append(" INNER JOIN T_KOJIN AS KOJIN ");
		buffer.append(" ON ");
		buffer.append(" ( ");
		buffer.append(" SONOTA.UKETUKE_ID = KOJIN.UKETUKE_ID ");
		buffer.append(" ) ");
		buffer.append(" WHERE");
		buffer.append(" TOKUTEI.UKETUKE_ID = ? ");
		buffer.append(" AND");
		buffer.append(" TOKUTEI.KENSA_NENGAPI = ? ");
		buffer.append(" AND");
		buffer.append(" SONOTA.KENSA_NENGAPI = ? ");
		buffer.append(" AND");
		buffer.append(" MASTER.HKNJANUM = ? ");
		buffer.append(" AND");
		buffer.append(" SONOTA.KOUMOKU_CD IN ");
		buffer.append(" (");
		buffer.append("  SELECT PT.KOUMOKU_CD ");
		buffer.append("  FROM T_KENSHIN_P_S AS PT ");
		buffer.append("  WHERE TOKUTEI.K_P_NO = PT.K_P_NO ");
		buffer.append(" )");

		if (isTokutei) {
			buffer.append(" AND ");
			buffer.append(" SONOTA.KOUMOKU_CD ");
			buffer.append(" IN ( ");
			buffer.append("     SELECT PT2.KOUMOKU_CD ");
			buffer.append("     FROM T_KENSHIN_P_S AS PT2 ");
			buffer.append("     WHERE PT2.K_P_NO = '-1' ");
			// edit s.inoue 2010/09/08
			buffer.append("		AND KOUMOKU_CD NOT IN('3D010129901926101','3D010129902227101','3D010129901927201','3D010129901999901')");
			buffer.append(" ) ");
		}

		String sql = buffer.toString();
		return sql;
	}

// del ver2 s.inoue 2009/04/02
//	private static final String SQL_CREATE_UDPATE_TOKUTEI_WITH_TUTI_SQL =
//		createUpdateTokuteiWithTutiSql();
//	private static String createUpdateTokuteiWithTutiSql(){
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(" UPDATE ");
//		buffer.append(" T_KENSAKEKA_TOKUTEI ");
//		buffer.append(" SET ");
//		buffer.append(" TUTI_NENGAPI ");
//		buffer.append(" = ");
//		buffer.append(" ? ");
//		buffer.append(" WHERE ");
//		buffer.append(" UKETUKE_ID ");
//		buffer.append(" = ");
//		buffer.append(" ? ");
//		buffer.append(" AND ");
//		buffer.append(" KENSA_NENGAPI ");
//		buffer.append(" = ");
//		buffer.append(" ? ");
//		String query = buffer.toString();
//
//		return query;
//	}

	/**
	 * HL7出力を行う。
	 */
	// edit s.inoue 2009/09/18
	public static boolean RunProcess(
			Vector<JKessaiProcessData> data,
			// eidt s.inoue 2011/04/04
			// JOutputGetujiFrameData jOutData) {
			JOutputGetujiSearchListFrameData jOutData){

		// 提出先機関ごとのリストを集計情報から作成
		ArrayList<Hashtable<String, String>> SouhusakiList = null;
		try {

			/* 集計テーブルの HKNJANUM には、請求先の機関番号が格納されている。（保険者番号 or 支払代行機関） */
			// edit s.inoue 2009/10/15

			// del s.inoue 2009/10/18
			//	String cunma = "";
			//		cunma = selectedRows.get(i);
			//	for (int i = 0; i < selectedRows.size(); i++) {
			//		if (i > 0)
			//			cunma += ",";
			//	}

			// edit s.inoue 2009/10/18
			String cunmaunit = "";
			for (int j = 0; j < data.size(); j++) {
				String cunma = "";
				if (j > 0)
					cunmaunit += ",";
				cunma = data.get(j).daikouKikanNumber.toString();
				if (cunma.equals(""))
					cunma = data.get(j).hokenjyaNumber.toString();
				cunmaunit += JQueryConvert.queryConvert(cunma);
			}

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT HKNJANUM,KENSA_NENGAPI FROM T_SYUKEI ");
			sb.append(" WHERE HKNJANUM IN (");
			sb.append(cunmaunit);
			sb.append(" )");
			SouhusakiList = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			JErrorMessage.show("M8501", null);
			return false;
		}

		// add s.inoue 2009/10/16
		// 未入力の場合、からで出力
		if (SouhusakiList.size() <= 0) {
			JErrorMessage.show("M8523", null);
			return false;
		}

		// 保険者ごとに処理を行う
		for (int i = 0; i < SouhusakiList.size(); i++) {

			// add s.inoue 2009/10/18

			/* 集計テーブルから請求先機関番号を取得する */
			String seikyusakiNum = SouhusakiList.get(i).get("HKNJANUM");
			String syubetuCode = "";
			Vector<JKessaiProcessData> kojinDatas = new Vector<JKessaiProcessData>();

			// 種別コード
			JKessaiProcessData processData = null;
			for (Iterator iter = data.iterator(); iter.hasNext();) {
				processData = (JKessaiProcessData) iter.next();
				// edit s.inoue 2009/10/20
				// 優先順位 代行機関があれば種別コード → 1、保険者ならば種別コード → 6
				// 代行機関が入っている場合は、保険者に設定する事
				if (processData.daikouKikanNumber.equals(seikyusakiNum)) {
					syubetuCode = processData.syubetuCode;
					break;
				}else if(processData.daikouKikanNumber.equals("") &&
						processData.hokenjyaNumber.equals(seikyusakiNum)){
					syubetuCode = processData.syubetuCode;
					break;
				}
//				if (processData.hokenjyaNumber.equals(seikyusakiNum)
//						|| processData.daikouKikanNumber.equals(seikyusakiNum)) {
//
//					syubetuCode = processData.syubetuCode;
//					break;
//				}
			}

			if (syubetuCode.isEmpty() || processData == null) {
				JErrorMessage.show("M8505", null);
				return false;
			}

			if (syubetuCode.equals("1")) {
				for (int j = 0; j < data.size(); j++) {
					if (data.get(j).daikouKikanNumber.toString().equals(
							seikyusakiNum)) {
						kojinDatas.add(data.get(j));
					}
				}
			}

			if (syubetuCode.equals("6")) {
				for (int j = 0; j < data.size(); j++) {
					// edit s.inoue 2009/10/20
					// 優先順位 代行機関があれば種別コード → 1、保険者ならば種別コード → 6
					// 代行機関が入っていない場合、保険者に設定する事
					if (data.get(j).daikouKikanNumber.equals("") &&
							data.get(j).hokenjyaNumber.toString().equals(
							seikyusakiNum)) {
						kojinDatas.add(data.get(j));
					}
				}
			}

			// フォルダの確保
			JOutputHL7Directory dir = new JOutputHL7Directory(
					JApplication.kikanNumber, seikyusakiNum);

			// edit s.inoue 2009/09/18
			jOutData.setJOutputDir(dir);

			int ret = dir.AllocOutputDirectory();

			// add s.inoue 2009/10/18
			// リセット後、再度呼び出し
			if (ret == 2) {
				ret = dir.AllocOutputDirectory();
			}

			if (ret == 1) {
				JErrorMessage.show("M8502", null);
				return false;
			}
			if (ret == 2) {
				// edit s.inoue 2009/10/02
				// 処理を中断しないでそのまま継続
				// JErrorMessage.show("M8503", null);
				// return false;

			}
			if (ret == 4) {
				JErrorMessage.show("M8504", null);
				return rollback(dir);
			}

			/* 基本情報データ */
			if (outputIndex(JApplication.kikanNumber, seikyusakiNum, dir
					.GetIndexPath(), syubetuCode) == false) {
				return rollback(dir);
			}

			/* 集計データ */
			if (outputSummaryData(seikyusakiNum, dir.GetSummaryPath()) == false) {
				return rollback(dir);
			}

			for (int j = 0; j < kojinDatas.size(); j++) {
				JKessaiProcessData kessaiData = kojinDatas.get(j);

				if (syubetuCode.equals("1")) {
					/* 決済データ */
					boolean checkupClaim = outputCheckupClaim(
							kessaiData.uketukeId,
							kessaiData.daikouKikanNumber,
							kessaiData.hiHokenjyaKigo,
							kessaiData.hiHokenjyaNumber,
							JApplication.kikanNumber,
							dir.GetCheckupClaimPath(),
							kessaiData
						);

					if (checkupClaim == false) {
						return rollback(dir);
					}

					/* 健診データ */
					boolean outputClinicalDocument = outputClinicalDocument(
							kessaiData.uketukeId, kessaiData.daikouKikanNumber,
							kessaiData.hiHokenjyaKigo, kessaiData.hiHokenjyaNumber,
							JApplication.kikanNumber, kessaiData.KensaDate, dir
									.GetClinicalDocumentPath(),
							processData.hokenjyaNumber);
					if (outputClinicalDocument == false) {
						return rollback(dir);
					}
				}

				if (syubetuCode.equals("6")) {
					boolean outputCheckupClaim = outputCheckupClaim(
							kessaiData.uketukeId, kessaiData.hokenjyaNumber,
							kessaiData.hiHokenjyaKigo, kessaiData.hiHokenjyaNumber,
							JApplication.kikanNumber,
							dir.GetCheckupClaimPath(),
							kessaiData
						);

					if (outputCheckupClaim == false) {
						return rollback(dir);
					}

					boolean outputClinicalDocument = outputClinicalDocument(
							kessaiData.uketukeId, kessaiData.hokenjyaNumber,
							kessaiData.hiHokenjyaKigo, kessaiData.hiHokenjyaNumber,
							JApplication.kikanNumber, kessaiData.KensaDate, dir
									.GetClinicalDocumentPath(),
							processData.hokenjyaNumber);
					if (outputClinicalDocument == false) {
						return rollback(dir);
					}
				}

				// 出力が完了したら、HL7変換日時を設定する
				DateFormat format = new SimpleDateFormat("yyyyMMdd");
				String createDate = format.format(new Date());

				try {
					// edit s.inoue 2009/09/16
					JApplication.kikanDatabase.Transaction();

					JApplication.kikanDatabase.sendNoResultQuery(
							"UPDATE T_KENSAKEKA_TOKUTEI SET " +
							"HENKAN_NITIJI = " + JQueryConvert.queryConvert(createDate) +
							" WHERE UKETUKE_ID = " + JQueryConvert.queryConvert(kessaiData.uketukeId) +
							" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kessaiData.KensaDate));

//					// move s.inoue 2012/11/20
//					// _WKテーブルの削除
//					deleteWork(kessaiData.uketukeId);

					// edit s.inoue 2009/09/16
					JApplication.kikanDatabase.Commit();
				} catch (SQLException e) {
					e.printStackTrace();
					JErrorMessage.show("M8506", null);
					return rollback(dir);
				}

// del ver2 s.inoue 2009/04/02
				/* 検査結果データ */
//				String[] params = { createDate, kessaiData.uketukeId, kessaiData.KensaDate };
//
//				boolean success = false;
//				try {
//					int rowCount = JApplication.kikanDatabase.sendNoResultQuery(
//							SQL_CREATE_UDPATE_TOKUTEI_WITH_TUTI_SQL, params);
//					if (rowCount == 1) {
//						success = true;
//					}
//
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//
//				if (! success) {
//					String[] errorParams = { kessaiData.kanaName, kessaiData.sex, kessaiData.birthday, kessaiData.KensaDate };
//					JErrorMessage.show("M8521", null, errorParams);
//
//					return rollback(dir);
//				}
			}

			try {
				dir.CompressFile();
			} catch (IOException e) {
				e.printStackTrace();
				JErrorMessage.show("M8507", null);
				return rollback(dir);
			}
			dir.DeleteFolder();
		}

		return true;
	}

	// add s.inoue 2012/11/02
	/**
	 * 削除処理を行う
	 */
	public static void deleteWork(String uketukeId)
	{
		// T_KESAIデータ削除
		StringBuilder workKesaiQuery = new StringBuilder();
		try{

			workKesaiQuery.append("DELETE FROM T_KESAI_WK");
			workKesaiQuery.append(" WHERE UKETUKE_ID = ");
			workKesaiQuery.append(JQueryConvert.queryConvert(uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(workKesaiQuery.toString());

		}catch(SQLException err)
		{
			logger.error(err.getMessage());
		}

		// T_KESAI_SYOUSAIデータ削除
		StringBuilder workSyousaiQuery = new StringBuilder();
		try{

			workSyousaiQuery.append("DELETE FROM T_KESAI_SYOUSAI_WK");
			workSyousaiQuery.append(" WHERE UKETUKE_ID = ");
			workSyousaiQuery.append(JQueryConvert.queryConvert(uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(workSyousaiQuery.toString());

		}catch(SQLException err)
		{
			logger.error(err.getMessage());
		}
	}

	/**
	 * ロールバックの処理を行う。 HL7出力に失敗したら、戻り値としてこの関数を使用すること。
	 * この関数自体がfalseを返すので、戻り値が正常に与えられる。
	 */
	private static boolean rollback(JOutputHL7Directory dir) {
		dir.DeleteFolder();
		return false;
	}

	/**
	 * 集計情報の出力を行う
	 *
	 * @param KensaNengapi
	 *            健診年月日
	 */
	private static boolean outputSummaryData(String SouhuSaki, String savePath) {
		SummaryWriter writer = new SummaryWriter();
		Summary summary = new Summary();

		Hashtable<String, String> syukeiData = new Hashtable<String, String>();
		try {

			// edit ver2 s.inoue 2009/08/21
			StringBuilder sb = new StringBuilder();

			sb.append("SELECT ");
			sb.append(" HKNJANUM, KENSA_NENGAPI, KENSA_JISI_KUBUN, KENSA_JISI_SOUSU,");
			sb.append(" KENSA_TANKA_SOUKEI, KENSA_MADO_SOUKEI, KENSA_SEIKYU_SOUKEI, KENSA_SONOTA_SOUKEI");
			sb.append(" FROM T_SYUKEI ");
			sb.append(" WHERE HKNJANUM = ");
			sb.append(JQueryConvert.queryConvert(SouhuSaki));

			syukeiData = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);

			// 主キー指定しているので一意に定まる
			// syukeiData = JApplication.kikanDatabase.sendExecuteQuery(
			// 		"SELECT * FROM T_SYUKEI WHERE HKNJANUM = "
			// 				+ JQueryConvert.queryConvert(SouhuSaki)).get(0);

		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8508", null);
			return false;
		}

		summary.setServiceEventType(syukeiData.get("KENSA_JISI_KUBUN"));
		summary.setTotalSubjectCount(syukeiData.get("KENSA_JISI_SOUSU"));
		summary.setTotalCostAmount(syukeiData.get("KENSA_TANKA_SOUKEI"));
		summary.setTotalPaymentAmount(syukeiData.get("KENSA_MADO_SOUKEI"));
		summary.setTotalPaymentByOtherProgram(syukeiData.get("KENSA_SONOTA_SOUKEI"));
		summary.setTotalClaimAmount(syukeiData.get("KENSA_SEIKYU_SOUKEI"));

		writer.setSummary(summary);

		try {
			writer.createXml(savePath);
		} catch (Exception e) {
			e.printStackTrace();
			JErrorMessage.show("M8509", null);
			return false;
		}

		return true;
	}

	/**
	 * 交換用基本情報ファイルの作成
	 */
	private static boolean outputIndex(String SouhuMoto, String SouhuSaki,
			String SavePath, String SyubetuCode) {
		IndexWriter writer = new IndexWriter();
		Index index = new Index();

		Hashtable<String, String> SyukeiData = null;
		try {
			StringBuilder sb = new StringBuilder();

			// edit ver2 s.inoue 2009/08/21
			sb.append("SELECT ");
			sb.append(" HKNJANUM, KENSA_NENGAPI, KENSA_JISI_KUBUN, KENSA_JISI_SOUSU,");
			sb.append(" KENSA_TANKA_SOUKEI, KENSA_MADO_SOUKEI, KENSA_SEIKYU_SOUKEI, KENSA_SONOTA_SOUKEI");
			sb.append(" FROM T_SYUKEI ");
			sb.append(" WHERE HKNJANUM = ");
			sb.append(JQueryConvert.queryConvert(SouhuSaki));

			SyukeiData = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);

			// edit ver2 s.inoue 2009/08/21
			// SyukeiData = JApplication.kikanDatabase.sendExecuteQuery(
			// 		"SELECT * FROM T_SYUKEI WHERE HKNJANUM = "
			// 				+ JQueryConvert.queryConvert(SouhuSaki)).get(0);

		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M8510", null);
			return false;
		}

		index.setInteractionType(JInteger.valueOf(SyubetuCode));
		index.setSenderExtension(JLong.valueOf(SouhuMoto));
		index.setReceiverExtension(JLong.valueOf(SouhuSaki));
		index.setServiceEventType(JInteger.valueOf("1"));

		String kensaJisiSousu = SyukeiData.get("KENSA_JISI_SOUSU");
		int sousuInt = Integer.parseInt(kensaJisiSousu);

		String kensaJisiSousu2 = Integer.toString(sousuInt * 2);
		index.setTotalRecordCount(kensaJisiSousu2);

		writer.setIndex(index);
		try {
			writer.createXml(SavePath);
		} catch (Exception e) {
			e.printStackTrace();
			JErrorMessage.show("M8511", null);
			return false;
		}

		return true;
	}

	private static boolean outputCheckupClaim(
			String uketukeId,
			String souhuSaki,
			String hiHokenjyaKigo,
			String hiHokenjyaNumber,
			String souhuMoto,
			String savePath,
			JKessaiProcessData processData
		) {

		CheckupClaimWriter writer = new CheckupClaimWriter();
		writer.setServiceEventType("1");

		Hashtable<String, String> personalData = null;

		try {
			// edit ver2 s.inoue 2009/08/31
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME,");
			sql.append(" BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME,");
			sql.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI,");
			sql.append(" MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA,");
			sql.append(" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
			sql.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
			sql.append(JQueryConvert.queryConvert(uketukeId));

			personalData = JApplication.kikanDatabase.sendExecuteQuery(sql.toString()).get(0);

		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8512", null);
			return false;
		}

		// 披保険者証等記号がない場合の対応
		String displayHiHokenjyasyoKigou = personalData.get("HIHOKENJYASYO_KIGOU");

		//add ver2 20090326 s.inoue
		// 郵政仕様により、全角'/''-'一文字は、半角文字に置き換える
		if(displayHiHokenjyasyoKigou.equals(OUTPUTHL7_CHARACTER_SLASH) ||
				displayHiHokenjyasyoKigou.equals(OUTPUTHL7_CHARACTER_HAIFUN)){
			displayHiHokenjyasyoKigou =JZenkakuKatakanaToHankakuKatakana.eisukigoZenToHan(displayHiHokenjyasyoKigou);
		}

		SubjectPerson subjectPerson = new SubjectPerson();

		// edit s.inoue 2009/10/22
		// 受診者住所 ～、ハイフンを長音へ変換
		String address = JValidate.encodeUNICODEtoConvert(personalData.get("HOME_ADRS")
							+ personalData.get("HOME_BANTI"));
		subjectPerson.setAddress(address);

		subjectPerson.setBirthTime(personalData.get("BIRTHDAY"));
		subjectPerson.setGender(personalData.get("SEX"));
		subjectPerson
				.setInsurerCardNumber(personalData.get("HIHOKENJYASYO_NO"));

		if (displayHiHokenjyasyoKigou != null
				&& !displayHiHokenjyasyoKigou.isEmpty()) {
			subjectPerson.setInsurerCardSymbol(displayHiHokenjyasyoKigou);
		}

		String kojinHokenjaNum = personalData.get("HKNJANUM");
		subjectPerson.setInsureNumber(kojinHokenjaNum);
		subjectPerson.setName(personalData.get("KANANAME"));
		subjectPerson.setOrganizationId(souhuMoto);
		subjectPerson.setPostalCode(personalData.get("HOME_POST"));

		writer.setSubjectPerson(subjectPerson);

		Hashtable<String, String> kessaiData = null;
		try {

			StringBuilder sb = new StringBuilder();
			// edit ver2 s.inoue 2009/08/21
			// String selectTKesaiSql = "SELECT * FROM T_KESAI WHERE HKNJANUM = "
			// 		+ JQueryConvert.queryConvert(kojinHokenjaNum)
			// 		+ " AND UKETUKE_ID = "
			// 		+ JQueryConvert.queryConvert(uketukeId);

			sb.append("SELECT HOKENJYA.TANKA_HANTEI,KESAI.TKIKAN_NO, KESAI.JISI_KBN, KESAI.KENSA_NENGAPI, KESAI.SEIKYU_KBN, KESAI.SYUBETU_CD, KESAI.HKNJANUM, KESAI.HIHOKENJYASYO_KIGOU, KESAI.HIHOKENJYASYO_NO, KESAI.ITAKU_KBN,");
			sb.append(" KESAI.TANKA_KIHON, KESAI.HINKETU_CD, KESAI.TANKA_HINKETU, KESAI.SINDENZU_CD, KESAI.TANKA_SINDENZU, KESAI.GANTEI_CD, KESAI.TANKA_GANTEI, KESAI.MADO_FUTAN_K_SYUBETU,");
			sb.append(" KESAI.MADO_FUTAN_KIHON, KESAI.MADO_FUTAN_KIHON_OUT, KESAI.MADO_FUTAN_S_SYUBETU, KESAI.MADO_FUTAN_SYOUSAI, KESAI.MADO_FUTAN_SYOUSAI_OUT,");
			sb.append(" KESAI.MADO_FUTAN_T_SYUBETU, KESAI.MADO_FUTAN_TSUIKA, KESAI.MADO_FUTAN_TSUIKA_OUT, KESAI.TANKA_GOUKEI, KESAI.MADO_FUTAN_GOUKEI, KESAI.SEIKYU_KINGAKU,");
			sb.append(" KESAI.SIHARAI_DAIKOU_BANGO, KESAI.NENDO, KESAI.UKETUKE_ID, KESAI.MADO_FUTAN_SONOTA, KESAI.HOKENJYA_FUTAN_KIHON, KESAI.HOKENJYA_FUTAN_SYOUSAI,");
			sb.append(" KESAI.HOKENJYA_FUTAN_TSUIKA, KESAI.HOKENJYA_FUTAN_DOC, KESAI.TANKA_NINGENDOC, KESAI.MADO_FUTAN_D_SYUBETU, KESAI.MADO_FUTAN_DOC, KESAI.MADO_FUTAN_DOC_OUT,KESAI.UPDATE_TIMESTAMP ");
			sb.append(" FROM T_KESAI KESAI");
			// edit s.inoue 2009/10/28
			sb.append(" LEFT JOIN T_HOKENJYA HOKENJYA ON KESAI.HKNJANUM = HOKENJYA.HKNJANUM ");
			sb.append(" WHERE HOKENJYA.HKNJANUM = ");
			sb.append(JQueryConvert.queryConvert(kojinHokenjaNum));
			// add s.inoue 2010/01/20
			sb.append(" AND HOKENJYA.YUKOU_FLG = '1'");

			sb.append(" AND KESAI.UKETUKE_ID =  ");
			sb.append(JQueryConvert.queryConvert(uketukeId));

			// edit s.inoue 2009/10/16
			// kessaiData = JApplication.kikanDatabase.sendExecuteQuery(sb.toString()).get(0);
			ArrayList<Hashtable<String, String>> arr = new ArrayList<Hashtable<String,String>>();
			arr = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
			if (arr.size() > 0) {
				kessaiData = arr.get(0);
			}else{
				JErrorMessage.show("M8522", null);
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8513", null);
			return false;
		}

		CheckupCard checkupCard = new CheckupCard();

		/* ddModified 2008/06/02 若月
		 * 窓口負担種別を計算で求めるようにする。 */
		checkupCard.setId(personalData.get("JYUSHIN_SEIRI_NO"));
		checkupCard.setLimitTime(personalData.get("YUKOU_KIGEN"));
		checkupCard.setClaimType(JInteger.valueOf(kessaiData.get("SEIKYU_KBN")));

		// add s.inoue 2009/10/30
		// chargeTypeの反映用にセットする
		/* 窓口負担種別（基本的な健診） */
		String inputMadoHutanKihonSyubetsu = kessaiData.get("MADO_FUTAN_K_SYUBETU");
		// edit s.inoue 2010/02/01
		String hokenjyaFutanKihon = kessaiData.get("HOKENJYA_FUTAN_KIHON");
		if (hokenjyaFutanKihon != null && ! hokenjyaFutanKihon.isEmpty()) {
			hokenjyaFutanKihon = hokenjyaFutanKihon.replaceAll("^0+", "");
			// edit s.inoue 2010/04/06
			if (!hokenjyaFutanKihon.equals("")){
				if (Integer.valueOf(hokenjyaFutanKihon) >= 0)
					inputMadoHutanKihonSyubetsu = "4";
			}
		}
		processData.outputFutanSyubetsuKihon = JInteger.valueOf(inputMadoHutanKihonSyubetsu);

		/* 窓口負担種別（詳細な健診） */
		String inputMadoHutanSyosaiSyubetsu = kessaiData.get("MADO_FUTAN_S_SYUBETU");
		// edit s.inoue 2010/02/01
		String hokenjyaFutanSyousai = kessaiData.get("HOKENJYA_FUTAN_SYOUSAI");
		if (hokenjyaFutanSyousai != null && ! hokenjyaFutanSyousai.isEmpty()) {
			hokenjyaFutanSyousai = hokenjyaFutanSyousai.replaceAll("^0+", "");
			// edit s.inoue 2010/04/06
			if (!hokenjyaFutanSyousai.equals("")){
				if (Integer.valueOf(hokenjyaFutanSyousai) >= 0)
					inputMadoHutanSyosaiSyubetsu = "4";
			}
		}
		processData.outputFutanSyubetsuSyousai = JInteger.valueOf(inputMadoHutanSyosaiSyubetsu);

		/* 窓口負担種別（追加の健診） */
		String inputMadoHutanTsuikaSyubetu = kessaiData.get("MADO_FUTAN_T_SYUBETU");
		// edit s.inoue 2010/02/01
		String hokenjyaFutanTsuika = kessaiData.get("HOKENJYA_FUTAN_TSUIKA");
		if (hokenjyaFutanTsuika != null && ! hokenjyaFutanTsuika.isEmpty()) {
			hokenjyaFutanTsuika = hokenjyaFutanTsuika.replaceAll("^0+", "");
			// edit s.inoue 2010/04/06
			if (!hokenjyaFutanTsuika.equals("")){
				if (Integer.valueOf(hokenjyaFutanTsuika) >= 0)
					inputMadoHutanTsuikaSyubetu = "4";
			}
		}
		processData.outputFutanSyubetsuTsuika = JInteger.valueOf(inputMadoHutanTsuikaSyubetu);

		/* 窓口負担種別（人間ドック） */
		String inputMadoHutanDockSyubetu = kessaiData.get("MADO_FUTAN_D_SYUBETU");
		// edit s.inoue 2010/02/01
		String hokenjyaFutanDoc = kessaiData.get("HOKENJYA_FUTAN_DOC");
		if (hokenjyaFutanDoc != null && ! hokenjyaFutanDoc.isEmpty()) {
			hokenjyaFutanDoc = hokenjyaFutanDoc.replaceAll("^0+", "");
			// edit s.inoue 2010/04/06
			if (!hokenjyaFutanDoc.equals("")){
				if (Integer.valueOf(hokenjyaFutanDoc) >= 0)
					inputMadoHutanDockSyubetu = "4";
			}
		}
		processData.outputFutanSyubetsuDoc = JInteger.valueOf(inputMadoHutanDockSyubetu);

		/* 基本的な健診 */
		if (processData.outputFutanSyubetsuKihon == 0) {
			processData.outputFutanSyubetsuKihon = 1;
		}

		checkupCard.setBasicCode(String.valueOf(processData.outputFutanSyubetsuKihon));
		checkupCard.setBasicRate(kessaiData.get("MADO_FUTAN_KIHON"));
		if (processData.outputFutanSyubetsuKihon == 4) {
			checkupCard.setBasicAmount(kessaiData.get("HOKENJYA_FUTAN_KIHON"));
		}
		else {
			checkupCard.setBasicAmount(kessaiData.get("MADO_FUTAN_KIHON"));
		}

		/* 詳細な健診 */
		if (processData.outputFutanSyubetsuSyousai == 0) {
			processData.outputFutanSyubetsuSyousai = 1;
		}

		checkupCard.setDetailCode(String.valueOf(processData.outputFutanSyubetsuSyousai));
		checkupCard.setDetailRate(kessaiData.get("MADO_FUTAN_SYOUSAI"));
		if (processData.outputFutanSyubetsuSyousai == 4) {
			checkupCard.setDetailAmount(kessaiData.get("HOKENJYA_FUTAN_SYOUSAI"));
		}
		else {
			checkupCard.setDetailAmount(kessaiData.get("MADO_FUTAN_SYOUSAI"));
		}

		// edit s.inoue 2009/10/29
		if (kessaiData.get("TANKA_HANTEI") != null){
			if (kessaiData.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
				/* 人間ドック */
				// add ver2 s.inoue 2009/07/22
				if (processData.outputFutanSyubetsuDoc == 0) {
					processData.outputFutanSyubetsuDoc = 1;
				}

				checkupCard.setOtherRate(kessaiData.get("MADO_FUTAN_DOC"));
				checkupCard.setOtherCode(String.valueOf(processData.outputFutanSyubetsuDoc));

				if (processData.outputFutanSyubetsuDoc == 4) {
					// edit s.inoue 2009/12/03
					// checkupCard.setDockAmount(kessaiData.get("HOKENJYA_FUTAN_DOC"));
					// checkupCard.setOtherAmount(kessaiData.get("HOKENJYA_FUTAN_DOC"));

					// add s.inoue 2010/02/03
					// setする場所をdocamountからmaxinsuranceへ
					checkupCard.setDockMaxInsurance(kessaiData.get("HOKENJYA_FUTAN_DOC"));

				}
				else {
					checkupCard.setDockAmount(kessaiData.get("MADO_FUTAN_DOC"));
//					checkupCard.setOtherAmount(kessaiData.get("MADO_FUTAN_DOC"));
				}
				// edit s.inoue 2009/12/03
				//checkupCard.setDockCode("1");
				checkupCard.setDockRate(kessaiData.get("MADO_FUTAN_DOC"));
				checkupCard.setDockCode(String.valueOf(processData.outputFutanSyubetsuDoc));

			}else{
				/* 追加の健診 */
				// edit ver2 s.inoue 2009/06/18
				if (processData.outputFutanSyubetsuTsuika == 0) {
					processData.outputFutanSyubetsuTsuika = 1;
				}
				checkupCard.setOtherCode(String.valueOf(processData.outputFutanSyubetsuTsuika));
				checkupCard.setOtherRate(kessaiData.get("MADO_FUTAN_TSUIKA"));

				if (processData.outputFutanSyubetsuTsuika == 4) {
					checkupCard.setOtherAmount(kessaiData.get("HOKENJYA_FUTAN_TSUIKA"));
				}
				else {
					checkupCard.setOtherAmount(kessaiData.get("MADO_FUTAN_TSUIKA"));
				}
			}
		}else{
			/* 追加の健診 */
			// edit ver2 s.inoue 2009/06/18
			if (processData.outputFutanSyubetsuTsuika == 0) {
				processData.outputFutanSyubetsuTsuika = 1;
			}
			checkupCard.setOtherCode(String.valueOf(processData.outputFutanSyubetsuTsuika));
			checkupCard.setOtherRate(kessaiData.get("MADO_FUTAN_TSUIKA"));

			if (processData.outputFutanSyubetsuTsuika == 4) {
				checkupCard.setOtherAmount(kessaiData.get("HOKENJYA_FUTAN_TSUIKA"));
			}
			else {
				checkupCard.setOtherAmount(kessaiData.get("MADO_FUTAN_TSUIKA"));
			}
		}

		writer.setCheckupCard(checkupCard);

		Settlement settlement = new Settlement();

		settlement.setPriceBasic(JLong.valueOf(kessaiData.get("TANKA_KIHON")));

		// 詳細な健診は項目ごとで出力するか分ける
		String tankaHinketu = kessaiData.get("TANKA_HINKETU");

		if (JLong.valueOf(tankaHinketu) >= 0) {
			settlement.addPriceDetail(kessaiData.get("HINKETU_CD"), tankaHinketu);
		}

		String tankaSindenzu = kessaiData.get("TANKA_SINDENZU");
		if (JLong.valueOf(tankaSindenzu) >= 0) {
			settlement.addPriceDetail(kessaiData.get("SINDENZU_CD"), tankaSindenzu);
		}

		String tankaGantei = kessaiData.get("TANKA_GANTEI");
		if (JLong.valueOf(tankaGantei) >= 0) {
			settlement.addPriceDetail(kessaiData.get("GANTEI_CD"), tankaGantei);
		}

		// 追加健診の情報を取得する
		ArrayList<Hashtable<String, String>> TuikaData = null;

		StringBuffer buffer = new StringBuffer();

		buffer.append(" SELECT TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN ");
		buffer.append(" FROM T_KESAI_SYOUSAI WHERE ");
		buffer.append(" TKIKAN_NO = " + JQueryConvert.queryConvert(souhuMoto));
		buffer.append(" AND UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId));
		buffer.append(" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kessaiData.get("KENSA_NENGAPI")));

		String sql = buffer.toString();

		Long sumTanka = 0L;
		try {
			TuikaData = JApplication.kikanDatabase.sendExecuteQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8514", null);
			return false;
		}

		for (int i = 0; i < TuikaData.size(); i++) {
			String tanka = TuikaData.get(i).get("TANKA_TUIKA_KENSIN");

			if (tanka != null && !tanka.isEmpty()) {

				try {
					String code = TuikaData.get(i).get("TUIKA_KENSIN_CD");

					settlement.addPriceOther(code, tanka);
					sumTanka +=Integer.valueOf(tanka);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}

		// add s.inoue 2009/09/16
		// エラーハンドリング
		try{

			settlement.setClaimType(kessaiData.get("SEIKYU_KBN"));
			settlement.setCommissionType(kessaiData.get("ITAKU_KBN"));

			Long madoKihon = 0L;
			Long madoSyosai = 0L;
			Long madoTsuika = 0L;
			Long madoDoc = 0L;
			Long madoFutanSonota = 0L;

			if (kessaiData.get("MADO_FUTAN_KIHON_OUT") != null ){
				madoKihon =JLong.valueOf(kessaiData.get("MADO_FUTAN_KIHON_OUT").trim());
				settlement.setPaymentBasic(madoKihon);
			}
			if (kessaiData.get("MADO_FUTAN_SYOUSAI_OUT") != null ){
				madoSyosai =JLong.valueOf(kessaiData.get("MADO_FUTAN_SYOUSAI_OUT").trim());
				settlement.setPaymentDetail(madoSyosai);
			}

			// edit s.inoue 2009/10/29
			if (kessaiData.get("TANKA_HANTEI") != null){
				if (kessaiData.get("TANKA_HANTEI").equals("2")){
					// add ver2 s.inoue 2009/06/18
					if (kessaiData.get("MADO_FUTAN_DOC_OUT") != null ){
						madoDoc =JLong.valueOf(kessaiData.get("MADO_FUTAN_DOC_OUT").trim());
						settlement.setPaymentOther(madoDoc);
					}
				}else{
					if (kessaiData.get("MADO_FUTAN_TSUIKA_OUT") != null ){
						madoTsuika =JLong.valueOf(kessaiData.get("MADO_FUTAN_TSUIKA_OUT").trim());
						settlement.setPaymentOther(madoTsuika);
					}
				}
			}else{
				if (kessaiData.get("MADO_FUTAN_TSUIKA_OUT") != null ){
					madoTsuika =JLong.valueOf(kessaiData.get("MADO_FUTAN_TSUIKA_OUT").trim());
					settlement.setPaymentOther(madoTsuika);
				}
			}

			settlement.setPaymentAmount(kessaiData.get("MADO_FUTAN_GOUKEI"));

			if (kessaiData.get("MADO_FUTAN_SONOTA") != null ){
				madoFutanSonota =JLong.valueOf(kessaiData.get("MADO_FUTAN_SONOTA").trim());
				settlement.setPaymentOtherProgram(String.valueOf(madoFutanSonota));
			}

			// ------単価合計------
			long syousaiTanka = 0L;
			long kihonTanka =0L;

			if (tankaHinketu != null ){
				syousaiTanka =JInteger.valueOf(tankaHinketu);
			}
			if (tankaSindenzu != null ){
				syousaiTanka +=JInteger.valueOf(tankaSindenzu);
			}
			if (tankaGantei != null ){
				syousaiTanka +=JInteger.valueOf(tankaGantei);
			}

			// 基本単価
			// edit ver2 s.inoue 2009/07/22 人間ドック対応
			long tankaGoukei = 0;
			// edit s.inoue 2009/10/28
			// 回避できていない問題を修正
			//if (!kessaiData.get("TANKA_NINGENDOC").equals("")){
			if (kessaiData.get("TANKA_HANTEI") != null){
				if (kessaiData.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
					tankaGoukei = Long.valueOf(kessaiData.get("TANKA_NINGENDOC"));
					settlement.setPriceOther(tankaGoukei);
				}else{
					// edit s.inoue 2009/10/28
					kihonTanka = Long.valueOf(kessaiData.get("TANKA_KIHON"));
					tankaGoukei = 	kihonTanka + syousaiTanka +	sumTanka;
				}
			}else{
				//if (kessaiData.get("TANKA_KIHON") != null ){
				kihonTanka = Long.valueOf(kessaiData.get("TANKA_KIHON"));
				tankaGoukei = 	kihonTanka + syousaiTanka +	sumTanka;
			}

			// edit ver2 s.inoue 2009/07/22
			//long tankaGoukei = 	kihonTanka + syousaiTanka +	sumTanka;
			// 単価合計出力
			settlement.setUnitAmount(tankaGoukei);

			// -------請求金額合計-------
			long madoFutanGoukei = 0L;

			if (madoKihon > 0 ){
				madoFutanGoukei +=madoKihon;
			}
			if (madoSyosai > 0 ){
				madoFutanGoukei +=madoSyosai;
			}
			if (madoTsuika > 0 ){
				madoFutanGoukei +=madoTsuika;
			}
			// edit ver2 s.inoue 2009/07/22
			long seikyuKingaku = 0;

			// edit s.inoue 2009/10/28
			// kessaiData.get("TANKA_HANTEI")
			if (kessaiData.get("TANKA_HANTEI") != null){
				if (kessaiData.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
					// if (kessaiData.get("TANKA_NINGENDOC") != null ){
					seikyuKingaku = tankaGoukei - madoDoc - madoFutanSonota;
				}else{
					// edit s.inoue 2009/10/28
					seikyuKingaku = tankaGoukei - madoFutanGoukei - madoFutanSonota;
				}
			}else{
				seikyuKingaku = tankaGoukei - madoFutanGoukei - madoFutanSonota;
			}

			// 請求金額出力
			settlement.setClaimAmount(seikyuKingaku);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			JErrorMessage.show("M8515", null);
			return false;
		}

		try {
			writer.setSettlement(settlement);
			writer.createXml(savePath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			JErrorMessage.show("M8515", null);
			return false;
		}

		return true;
	}

	protected static boolean outputClinicalDocument(String uketukeId,
			String SouhuSaki, String HiHokenjyaKigo, String HiHokenjyaNumber,
			String SouhuMoto, String KensaDate, String SavePath,
			String hokenjaNum)
	{
		Hashtable<String, String> personalData = null;
		try {

			// edit ver2 s.inoue 2009/08/31
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME,");
			sql.append(" BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME,");
			sql.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI,");
			sql.append(" MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA,");
			sql.append(" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
			sql.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
			sql.append(JQueryConvert.queryConvert(uketukeId));

			ArrayList<Hashtable<String, String>> result = JApplication.kikanDatabase
					.sendExecuteQuery(sql.toString());

			if (result == null || result.size() == 0) {
				JErrorMessage.show("M8516", null);
				return false;
			}
			personalData = result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8516", null);
			return false;
		}

		String DisplayHiHokenjyasyoKigou = personalData.get("HIHOKENJYASYO_KIGOU");

		// add ver2 s.inoue 2009/08/31
		// 郵政仕様により、全角'/''-'一文字は、半角文字に置き換える
		if(DisplayHiHokenjyasyoKigou.equals(OUTPUTHL7_CHARACTER_SLASH) ||
				DisplayHiHokenjyasyoKigou.equals(OUTPUTHL7_CHARACTER_HAIFUN)){
			DisplayHiHokenjyasyoKigou =JZenkakuKatakanaToHankakuKatakana.eisukigoZenToHan(DisplayHiHokenjyasyoKigou);
		}

		ClinicalDocumentWriter writer = new ClinicalDocumentWriter();

		RecordTarget patientRole = new RecordTarget();
		patientRole.setHokenjyaNumber(personalData.get("HKNJANUM"));
		patientRole.setHokensyoCode(DisplayHiHokenjyasyoKigou);
		patientRole.setHokensyoNumber(personalData.get("HIHOKENJYASYO_NO"));

		// edit s.inoue 2009/10/22
		// 受診者住所～、ハイフンを長音へ変換
		String address = JValidate.encodeUNICODEtoConvert(
				personalData.get("HOME_ADRS") + personalData.get("HOME_BANTI"));
		patientRole.setAddress(address);

		patientRole.setPostalCode(personalData.get("HOME_POST"));
		patientRole.setName(personalData.get("KANANAME"));
		patientRole.setGender(personalData.get("SEX"));
		patientRole.setBirthTime(personalData.get("BIRTHDAY"));

		writer.setPatientRole(patientRole);

		Hashtable<String, String> kikanData = null;
		try {
			// edit ver2 s.inoue 2009/08/31
			String sql = "SELECT TKIKAN_NO, SMOTO_KIKAN, SAKI_KIKAN, KIKAN_NAME, POST, ADR, TIBAN, TEL FROM T_KIKAN WHERE TKIKAN_NO = '"
					+ SouhuMoto + "'";
			kikanData = JApplication.kikanDatabase.sendExecuteQuery(sql).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8517", null);
			return false;
		}

		RepresentedOrganization assignedAuthor = new RepresentedOrganization();

		assignedAuthor.setNumber(kikanData.get("TKIKAN_NO"));
		assignedAuthor.setName(kikanData.get("KIKAN_NAME"));
		assignedAuthor.setTel(kikanData.get("TEL"));

		StringBuffer buffer = new StringBuffer(kikanData.get("ADR"));
		String tiban = kikanData.get("TIBAM");
		if (tiban != null && !tiban.isEmpty()) {
			buffer.append(tiban);
		}
		String addressAll = buffer.toString();
		// edit s.inoue 2009/10/22
		// 機関情報住所 ～、ハイフンを長音へ変換
		addressAll = JValidate.encodeUNICODEtoConvert(addressAll);
		assignedAuthor.setAddress(addressAll);

		assignedAuthor.setPostalCode(kikanData.get("POST"));

		writer.setAssignedAuthor(assignedAuthor);

		Participant participant = new Participant();

		participant.setTime(personalData.get("YUKOU_KIGEN"));

		String jyushinSeiriNo = personalData.get("JYUSHIN_SEIRI_NO");
		if (jyushinSeiriNo != null && !jyushinSeiriNo.isEmpty()) {
			participant.setSeiriNumber(jyushinSeiriNo);
		}

		participant.setHokenjyaNumber(personalData.get("HKNJANUM"));

		writer.setAssociatedEntity(participant);

		DocumentationOf documentationOf = new DocumentationOf();
		documentationOf.setEffectiveTime(KensaDate);

		RepresentedOrganization performer = new RepresentedOrganization();

		performer.setNumber(kikanData.get("TKIKAN_NO"));
		performer.setName(kikanData.get("KIKAN_NAME"));
		performer.setTel(kikanData.get("TEL"));
		performer.setAddress(addressAll);
		performer.setPostalCode(kikanData.get("POST"));

		documentationOf.setRepresentedOrganization(performer);

		writer.setDocumentationOf(documentationOf);

		// 実施した検診パターンにしたがって出力を行う。
		ArrayList<Hashtable<String, String>> kihonData = null;
		ArrayList<Hashtable<String, String>> kenshinData = null;

		try {
			String[] params = new String[] { personalData.get("UKETUKE_ID"),
					KensaDate, KensaDate, personalData.get("HKNJANUM"), };

			kihonData = JApplication.kikanDatabase.sendExecuteQuery(
					SQL_SELECT_KIHON_DATA, params);

			kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
					SQL_SELECT_KENSHIN_DATA, params);

		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M8518", null);
			return false;
		}

		/* 必須コードに関わらず、基本的な健診のパターンに含まれているか否かで
		 * 検査タイプ（セクションに影響）を再設定する。 */
		Iterator<Hashtable<String, String>> iter = kihonData.iterator();
		while (iter.hasNext()) {
			Hashtable<String, String> item = iter.next();
			String kensaType = item.get("HISU_FLG");

			if (kensaType.equals("3")) {

				String code = item.get("KOUMOKU_CD");

				Iterator<Hashtable<String, String>> kIter = kenshinData.iterator();
				while (kIter.hasNext()) {
					Hashtable<String, String> item2 = kIter.next();
					String code2 = item2.get("KOUMOKU_CD");

					if (code.equals(code2)) {
						item2.put("HISU_FLG", "1");
						break;
					}
				}
			}
		}

		kihonData.addAll(kenshinData);

		ArrayList<Hashtable<String, String>> DataTypeCodeList = null;
		try {
			// edit ver2 s.inoue 2009/08/31
			DataTypeCodeList = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE");
		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M8519", null);
			return false;
		}

		// いったん、検査データをすべてVectorに入れて、付属資料7で
		// 複数セルにまたがって○が入っているものに対しての処理を行う。
		// 項目コードをキーとしたハッシュテーブルにデータを格納する。
		Hashtable<String, Observation> observationList = new Hashtable<String, Observation>();

		for (int i = 0; i < kihonData.size(); i++) {
			Hashtable<String, String> item = kihonData.get(i);

			Observation observation = new Observation();
			observation.setCode(item.get("KOUMOKU_CD"));
			observation.setDataType(item.get("XML_DATA_TYPE"));
			observation.setPattern(item.get("XML_PATTERN"));
			observation.setResult(item.get("KEKA_TI"));
			observation.setJISI_KBN(item.get("JISI_KBN"));
			observation.setCodeSystem(item.get("KOUMOKU_OID"));
			observation.setEntryCode(item.get("KENSA_GROUP_CD"));
			observation.setEntryType(item.get("KENSA_GROUP"));
			observation.setInterpretationCode(item.get("H_L"));
			observation.setMethodCode(item.get("XML_KENSA_CD"));

			observation.setObservationRangeHighUnit(item.get("TANI"));
			observation.setObservationRangeLowUnit(item.get("TANI"));

			String sexCode = item.get("SEX");
			String rangeHighValue = null;
			String rangeRowValue = null;

			// edit ver2 s.inoue 2009/09/17
			// 基準値フォーマット対応
			String kekkaFormat = item.get("MAX_BYTE_LENGTH");
			kekkaFormat = kekkaFormat != null ? kekkaFormat: "";

			if (sexCode.equals("1")) {

				// edit s.inoue 2009/09/18
				// rangeHighValue = item.get("DS_JYOUGEN");
				// rangeRowValue = item.get("DS_KAGEN");
				rangeHighValue = item.get("DS_JYOUGEN");
				rangeHighValue = JValidate.validateKensaKekkaDecimal(rangeHighValue,kekkaFormat);
				rangeHighValue = rangeHighValue != null ? rangeHighValue:"";
				rangeRowValue = item.get("DS_KAGEN");
				rangeRowValue = JValidate.validateKensaKekkaDecimal(rangeRowValue,kekkaFormat);
				rangeRowValue = rangeRowValue != null ? rangeRowValue:"";
			}
			else {
				// edit s.inoue 2009/09/18
				// rangeHighValue = item.get("JS_JYOUGEN");
				// rangeRowValue = item.get("JS_KAGEN");
				rangeHighValue = item.get("JS_JYOUGEN");
				rangeHighValue = JValidate.validateKensaKekkaDecimal(rangeHighValue,kekkaFormat);
				rangeHighValue = rangeHighValue != null ? rangeHighValue:"";
				rangeRowValue = item.get("JS_KAGEN");
				rangeRowValue = JValidate.validateKensaKekkaDecimal(rangeRowValue,kekkaFormat);
				rangeRowValue = rangeRowValue != null ? rangeRowValue:"";
			}
			observation.setObservationRangeHighValue(rangeHighValue);
			observation.setObservationRangeLowValue(rangeRowValue);

			observation.setResultCode(item.get("KEKKA_OID"));
			observation.setResultUnit(item.get("TANI"));
			observation.setKensaType(item.get("HISU_FLG"));

			// edit s.inoue 2009/09/18
			// observation.setInputRangeHighValue(item.get("JYOUGEN"));
			// observation.setInputRangeLowValue(item.get("KAGEN"));

			String highValue = item.get("JYOUGEN");
			highValue = JValidate.validateKensaKekkaDecimal(highValue,kekkaFormat);
			highValue = highValue != null ? highValue:"";
			observation.setInputRangeHighValue(highValue);

			String lowValue = item.get("KAGEN");
			lowValue = JValidate.validateKensaKekkaDecimal(lowValue,kekkaFormat);
			lowValue = lowValue != null ? lowValue:"";
			// edit h.yoshitama 2009/10/30
			observation.setInputRangeLowValue(lowValue);

			observation.setCodeDisplayName(item.get("KOUMOKU_NAME"));

			// add s.inoue 2012/09/04
			observation.setKensanengapi(item.get("KENSA_NENGAPI"));

			int xmlItemSeqNo = -1;
			try {
				xmlItemSeqNo = Integer.valueOf(item.get("XMLITEM_SEQNO"));
			} catch (NumberFormatException e) {
				/* 何もしない */
			}
			observation.setXmlItemSeqNo(xmlItemSeqNo);

			// 検査結果データがコードの場合、項目コードの名称を出力する
			if ((item.get("XML_DATA_TYPE").equals("CD") || item.get(
					"XML_DATA_TYPE").equals("CO"))
					&& !item.get("KEKA_TI").equals("")) {
				for (int j = 0; j < DataTypeCodeList.size(); j++) {
					Hashtable<String, String> DataTypeCode = DataTypeCodeList
							.get(j);
					if (DataTypeCode.get("KOUMOKU_CD").equals(
							item.get("KOUMOKU_CD"))
							&& DataTypeCode.get("CODE_NUM").equals(
									item.get("KEKA_TI"))) {
						observation.setItemCodeDisplayName(DataTypeCode
								.get("CODE_NAME"));
						break;
					}
				}
			}

			observationList.put(item.get("KOUMOKU_CD"), observation);
		}

		ArrayList<String> keys = new ArrayList<String>();

		//内臓脂肪面積、腹囲
		keys.add(CODE_NAIZOUSIBOUMENSEKI);
		keys.add(CODE_HUKUI_JISOKU);
		keys.add(CODE_HUKUI_JIKOHANTEI);
		keys.add(CODE_HUKUI_JIKOSINKOKU);
		removeRepeatObservation(observationList, keys);

		// 血圧等（収縮血圧、拡張血圧、両方がペアになるようにする）
		// 1.実施かつ結果値あり 2.測定不能 3.未実施
		boolean kExists = false;
		boolean kfuExists1 = false;
		boolean kmiExists1 = false;
		boolean kdefExists1 = false;
		boolean kfuExists2 = false;
		boolean kmiExists2 = false;
		boolean kdefExists2 = false;
		boolean kfuExists3 = false;
		boolean kmiExists3 = false;
		boolean kdefExists3 = false;

		// 収縮期血圧（その他）
		if (observationList.containsKey("9A755000000000001")) {

			String kekati1 = observationList.get("9A755000000000001").getResult();
			String jisikbn1 = observationList.get("9A755000000000001").getJISI_KBN();

			if (!kekati1.equals("") && jisikbn1.equals("1")){
				kExists = true;
				observationList.remove("9A751000000000001");
				observationList.remove("9A752000000000001");
				observationList.remove("9A761000000000001");
				observationList.remove("9A762000000000001");
			}else if (kekati1.equals("") && jisikbn1.equals("1")){
				kdefExists1 = true;
			}else if (jisikbn1.equals("2")){
				kfuExists1 = true;
			}else if (jisikbn1.equals("0")){
				kmiExists1 = true;
			}
		}

		// 収縮期血圧（２回目）
		if (!kExists)
		{
			if (observationList.containsKey("9A752000000000001")) {

				String kekati2 = observationList.get("9A752000000000001").getResult();
				String jisikbn2 = observationList.get("9A752000000000001").getJISI_KBN();

				if (!kekati2.equals("") && jisikbn2.equals("1")){
					kExists = true;
					observationList.remove("9A755000000000001");
					observationList.remove("9A765000000000001");
					observationList.remove("9A751000000000001");
					observationList.remove("9A761000000000001");
				}else if (kekati2.equals("") && jisikbn2.equals("1")){
					kdefExists2 = true;
				}else if (jisikbn2.equals("2")){
					kfuExists2 = true;
				}else if (jisikbn2.equals("0")){
					kmiExists2 = true;
				}
			}
		}

		// 収縮期血圧（１回目）
		if (!kExists)
		{
			if (observationList.containsKey("9A751000000000001")) {

				String kekati3 = observationList.get("9A751000000000001").getResult();
				String jisikbn3 = observationList.get("9A751000000000001").getJISI_KBN();

				if (!kekati3.equals("") && jisikbn3.equals("1")){
					kExists = true;
					observationList.remove("9A755000000000001");
					observationList.remove("9A765000000000001");
					observationList.remove("9A752000000000001");
					observationList.remove("9A762000000000001");
				}else if (kekati3.equals("") && jisikbn3.equals("1")){
					kdefExists3 = true;
				}else if (jisikbn3.equals("2")){
					kfuExists3 = true;
				}else if (jisikbn3.equals("0")){
					kmiExists3 = true;
				}
			}
		}

		// 未実施⇒測定不能
		if (!kExists){
			if (kdefExists1 && kdefExists2 && kdefExists3){
				observationList.remove("9A755000000000001");
				observationList.remove("9A765000000000001");
				observationList.remove("9A751000000000001");
				observationList.remove("9A752000000000001");
				observationList.remove("9A761000000000001");
				observationList.remove("9A762000000000001");
				// 1番目残す
			}else if ((kmiExists1 && kmiExists2 && (kmiExists3 || kdefExists3)) ||
					(kmiExists1 && kdefExists2 && (kmiExists3 || kdefExists3)) ||
				(kfuExists1 && kfuExists2 && kfuExists3) ||
				(kfuExists1 && (kfuExists2 || kdefExists1)) ||
				(kfuExists1 && kmiExists2 && (kmiExists3 || kfuExists3 || kdefExists3)) ||
				(kfuExists1 && kfuExists2 && (kmiExists3 || kfuExists3 || kdefExists3)) ||
				(kfuExists1 && kdefExists2 && (kmiExists3 || kfuExists3 || kdefExists3)) ||
				(kdefExists2 && kdefExists2 && kdefExists3)){
				observationList.remove("9A751000000000001");
				observationList.remove("9A752000000000001");
				observationList.remove("9A761000000000001");
				observationList.remove("9A762000000000001");
			// 2番目残す
			}else if ((kmiExists1 && kfuExists2) ||
				(kdefExists1 && kfuExists2) ||
				(kdefExists1 && kmiExists2 && (kdefExists3 || kmiExists3))){
				observationList.remove("9A755000000000001");
				observationList.remove("9A765000000000001");
				observationList.remove("9A751000000000001");
				observationList.remove("9A761000000000001");
			// 3番目残す
			}else if( (kfuExists3 ) ||
					(kdefExists1 && kdefExists2) ||
					(kmiExists1 && kmiExists2) ||
					(kdefExists1 && kmiExists2 && kfuExists3)){
				observationList.remove("9A755000000000001");
				observationList.remove("9A765000000000001");
				observationList.remove("9A752000000000001");
				observationList.remove("9A762000000000001");
			}
		}
		keys.clear();
		keys.add("3F015000002327101");
		keys.add("3F015000002327201");
		keys.add("3F015000002399901");
		removeRepeatObservation(observationList, keys);

		// HDLコレステロール
		keys.clear();
		keys.add("3F070000002327101");
		keys.add("3F070000002327201");
		keys.add("3F070000002399901");
//masuda^ 2018/03/02 non-HDL 
                keys.add("3F069000002391901");
//masuda$
		removeRepeatObservation(observationList, keys);

		// LDLコレステロール
		keys.clear();
		keys.add("3F077000002327101");
		keys.add("3F077000002327201");
		keys.add("3F077000002399901");
		removeRepeatObservation(observationList, keys);

		// GOT
		keys.clear();
		keys.add("3B035000002327201");
		keys.add("3B035000002399901");
		removeRepeatObservation(observationList, keys);

		// GPT
		keys.clear();
		keys.add("3B045000002327201");
		keys.add("3B045000002399901");
		removeRepeatObservation(observationList, keys);

		// γ-GT
		keys.clear();
		keys.add("3B090000002327101");
		keys.add("3B090000002399901");
		removeRepeatObservation(observationList, keys);

		// 空腹時血糖
		keys.clear();
		keys.add("3D010000001926101");
		keys.add("3D010000002227101");
		keys.add("3D010000001927201");
		keys.add("3D010000001999901");
		removeRepeatObservation(observationList, keys);

		// 随時血糖
		keys.clear();
		keys.add("3D010129901926101");
		keys.add("3D010129902227101");
		keys.add("3D010129901927201");
		keys.add("3D010129901999901");
		removeRepeatObservation(observationList, keys);

		// HbA1c
		keys.clear();
		keys.add("3D045000001906202");
		keys.add("3D045000001920402");
		keys.add("3D045000001927102");
		keys.add("3D045000001999902");
		removeRepeatObservation(observationList, keys);

		/* 尿糖 */
		keys.clear();
		keys.add("1A020000000191111");
		keys.add("1A020000000190111");
		removeRepeatObservation(observationList, keys);

		/* 尿蛋白 */
		keys.clear();
		keys.add("1A010000000191111");
		keys.add("1A010000000190111");
		removeRepeatObservation(observationList, keys);

		// 選択処理済みのデータを出力する
		Enumeration<Observation> EnumObersvation = observationList.elements();
		while (EnumObersvation.hasMoreElements() == true) {
			Observation ob = EnumObersvation.nextElement();
			if (ob.getKensaType().equals("3")) {
				writer.addOtherObservation(ob);
			} else {
				writer.addTKenshinObservation(ob);
			}
		}

		try {
			writer.createXml(SavePath);
		} catch (Exception e) {
			e.printStackTrace();
			JErrorMessage.show("M8520", null);
			return false;
		}

		return true;
	}

	private static void removeRepeatObservation(Hashtable<String, Observation> observationList, ArrayList<String> keys) {

		/* 結果値が存在する項目の有無 */
		boolean codeExists = false;
		String jisiCode ="";
		boolean sokuExists = false;
		String sokuCode ="";
		boolean mijiExists = false;
		String mijiCode ="";

		// 実施区分探索
		for (String code : keys) {
			/* 項目が存在する場合 */
			if (observationList.containsKey(code)) {
				Observation observation = observationList.get(code);
				String result = observation.getResult();

				/* 結果値が存在する項目がまだ存在しない場合 */
				if (! codeExists) {
					String jisiKbn = observation.getJISI_KBN();

					if (jisiKbn.equals("1")){
						/* 結果値が存在する場合は、以降の項目を削除する。 */
						if (result != null && ! result.isEmpty()) {
							codeExists = true;
							jisiCode= code;
						}
					}else if (jisiKbn.equals("2")){
						if (!sokuExists) {
							sokuCode = code;
							sokuExists = true;
						}
					}else if (jisiKbn.equals("0")){
						if (!mijiExists) {
							mijiCode = code;
							mijiExists = true;
						}
					}
				}
			}
		}

		// 削除処理 実施⇒測定不能⇒未実施
		if (codeExists){
			for (String code : keys) {
				/* 実施以外削除 */
				if (observationList.containsKey(code)) {
					if (!jisiCode.equals(code)){
						observationList.remove(code);
					}
				}
			}
		}else if (sokuExists){
			for (String code : keys) {
				/* 測定不能以外削除 */
				if (observationList.containsKey(code)) {
					if (!sokuCode.equals(code)){
						observationList.remove(code);
					}
				}
			}
		}else if (mijiExists){
			for (String code : keys) {
				/* 未実施以外削除 */
				if (observationList.containsKey(code)) {
					if (!mijiCode.equals(code)){
						observationList.remove(code);
					}
				}
			}
		}else{
			for (String code : keys) {
				/* 全削除 */
				if (observationList.containsKey(code)) {
					observationList.remove(code);
				}
			}
		}

	}
}
