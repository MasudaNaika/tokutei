package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JLong;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * 決済処理を行う
 */
public class JKessaiProcess {
	private static final String GROUP_CODE_GANTEI = "9E100161000000049";
	private static final String GROUP_CODE_SHINDENZU = "9A110161000000049";
	private static final String GROUP_CODE_HINKETSU = "2A020161001930149";
	private static Logger logger = Logger.getLogger(JKessaiProcess.class);
	// add s.inoue 2010/01/15
	private static final String TANKA_HANTEI_KIHON = "1";
	private static final String TANKA_HANTEI_DOC = "2";

	/**
	 * 決済処理
	 * @param kessaiDatas 処理を行う受信者のリスト
	 * @param tKikanNumber 健診を行った機関の番号
	 *
	 * Modified 2008/04/02 若月
	 * 視認性を向上させるため、変更履歴コメントを削除し、リファクタリングを行なった。
	 */
	public static void runProcess(Vector<JKessaiProcessData> kessaiDatas,String tKikanNumber) throws Exception
	{
		// edit s.inoue 2009/11/14
		// 最初に決済データテーブル、決済データ詳細テーブルから全てのデータを削除する
		// 詳細に単価が残るため（詳細健診→追加健診）、削除を復活させる！！！
		for (JKessaiProcessData kessaiProcessData : kessaiDatas) {
			StringBuilder sbKesaiWk = new StringBuilder();
			StringBuilder sbKesaiSyosaiWk = new StringBuilder();

			sbKesaiWk.append("DELETE FROM T_KESAI_WK");
			sbKesaiWk.append(" WHERE UKETUKE_ID = ");
			sbKesaiWk.append(JQueryConvert.queryConvert(kessaiProcessData.uketukeId));
			sbKesaiSyosaiWk.append("DELETE FROM T_KESAI_SYOUSAI_WK");
			sbKesaiSyosaiWk.append(" WHERE UKETUKE_ID = ");
			sbKesaiSyosaiWk.append(JQueryConvert.queryConvert(kessaiProcessData.uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(sbKesaiWk.toString());
			JApplication.kikanDatabase.sendNoResultQuery(sbKesaiSyosaiWk.toString());
		}

		for(int i = 0 ; i < kessaiDatas.size() ; i++)
		{
			// 個人ごとのデータ
			// これが一つの作業単位となる
			JKessaiProcessData kessaiData = kessaiDatas.get(i);

			/* 被保険者証等記号 */
			String hiHokenjyaKigo = kessaiData.hiHokenjyaKigo;
			if (hiHokenjyaKigo == null) {
				hiHokenjyaKigo = "";
			}

			/* 被保険者証等番号がない場合は、エラーとする。 */
			if (kessaiData.hiHokenjyaNumber == null) {
				JErrorMessage.show("M4751",null);
				throw new Exception();
			}

			/* 保険者の情報 */
			Hashtable<String, String> hokenjyaInfo = getHokenjaInfo(kessaiData.hokenjyaNumber);

			/* 委託区分 */
			String itakuKubun = hokenjyaInfo.get("ITAKU_KBN");

			/* ------------------------------------------------------------------------------------
			 * 決済情報の計算を行う。
			 * ------------------------------------------------------------------------------------
			 */

			/* 入力データ */
			JKessaiDataInput inputData = new JKessaiDataInput();

			/* 個人情報テーブルから窓口負担種別と金額（または率）を取得する。 */
			Hashtable<String, String> kojinInfo = null;
			kojinInfo = JApplication.kikanDatabase.sendExecuteQuery(
					"SELECT * FROM T_KOJIN WHERE " +
					"HKNJANUM = " + JQueryConvert.queryConvert(kessaiData.hokenjyaNumber) + " AND " +
					"UKETUKE_ID = " + JQueryConvert.queryConvert(kessaiData.uketukeId)).get(0);

			/* ------------------------------------------
			 * 基本的な健診
			 * ------------------------------------------
			 */
			String inputTankaKihon = hokenjyaInfo.get("TANKA_KIHON");

			/* 窓口負担（基本的な健診） */
			String inputMadoHutanKihon = kojinInfo.get("MADO_FUTAN_KIHON");
			inputData.setKihonMadoFutan(JLong.valueOf(inputMadoHutanKihon));
			/* 窓口負担種別（基本的な健診） */
			String inputMadoHutanKihonSyubetsu = kojinInfo.get("MADO_FUTAN_K_SYUBETU");
			inputData.setKihonMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanKihonSyubetsu));
			/* 単価（基本的な健診） */
			inputData.setKihonTanka(JLong.valueOf(inputTankaKihon));

			/* 保険者負担上限額（基本的な健診） */
			String hokenjyaHutanJyougenKihon = kojinInfo.get(
					"HOKENJYA_FUTAN_KIHON");
			if (hokenjyaHutanJyougenKihon != null &&
					! hokenjyaHutanJyougenKihon.isEmpty()) {

				long hokenjyaHutanJyougenKihonLong = JLong.valueOf(
						hokenjyaHutanJyougenKihon);
				inputData.setKihonHokenjyaJyougen(hokenjyaHutanJyougenKihonLong);
			}

			/*------------------------------------------
			 * 詳細な健診
			 * ------------------------------------------
			 */
			String inputTankaHinketu = null;
			String inputTankaShindenzu = null;
			String inputTankaGantei = null;

			/* 貧血検査 */
			String inputHinketsuCode = hokenjyaInfo.get("HINKETU_CD");
			String executeTankaHinketsu = null;
			boolean existSyousaiHinketu = isExistSyousaiKensaKoumoku(
								kessaiData.hokenjyaNumber,
								kessaiData.uketukeId,
								kessaiData.KensaDate,
								GROUP_CODE_HINKETSU);
			if(existSyousaiHinketu)
			{
				executeTankaHinketsu = hokenjyaInfo.get("TANKA_HINKETU");
				inputTankaHinketu = hokenjyaInfo.get("TANKA_HINKETU");
			}else{
				executeTankaHinketsu = "000000";
			}

			/* 心電図検査 */
			String inputShindenzuCode = hokenjyaInfo.get("SINDENZU_CD");
			String executeTankaShindenzu = null;
			boolean existSyousaiSindenzu = isExistSyousaiKensaKoumoku(
								kessaiData.hokenjyaNumber,
								kessaiData.uketukeId,
								kessaiData.KensaDate,
								GROUP_CODE_SHINDENZU);
			if(existSyousaiSindenzu)
			{
				executeTankaShindenzu = hokenjyaInfo.get("TANKA_SINDENZU");
				inputTankaShindenzu = hokenjyaInfo.get("TANKA_SINDENZU");
			}else{
				executeTankaShindenzu = "000000";
			}

			/* 眼底検査 */
			String inputGanteiCode = hokenjyaInfo.get("GANTEI_CD");
			String executeTankaGantei = null;
			boolean existSyousaiGantei = isExistSyousaiKensaKoumoku(
								kessaiData.hokenjyaNumber,
								kessaiData.uketukeId,
								kessaiData.KensaDate,
								GROUP_CODE_GANTEI);
			if(existSyousaiGantei)
			{
				executeTankaGantei = hokenjyaInfo.get("TANKA_GANTEI");
				inputTankaGantei = hokenjyaInfo.get("TANKA_GANTEI");
			}else{
				executeTankaGantei = "000000";
			}

			boolean existsSyousai = false;
			if (existSyousaiHinketu || existSyousaiSindenzu
					|| existSyousaiGantei) {
				existsSyousai = true;
			}

			inputData.setExistsSyousai(existsSyousai);

			/* 窓口負担（詳細な健診） */
			String inputMadoHutanSyousai = kojinInfo.get("MADO_FUTAN_SYOUSAI");
			inputData.setSyousaiMadoFutan(JLong.valueOf(inputMadoHutanSyousai));
			/* 窓口負担種別（詳細な健診） */
			String inputMadoHutanSyosaiSyubetsu = kojinInfo.get("MADO_FUTAN_S_SYUBETU");
			inputData.setSyousaiMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanSyosaiSyubetsu));
			/* 単価（詳細な健診） */
			long executeSyousaiTanka = JInteger.valueOf(executeTankaHinketsu) +
								JInteger.valueOf(executeTankaShindenzu) +
								JInteger.valueOf(executeTankaGantei);
			inputData.setSyousaiTanka(executeSyousaiTanka);

			/* 保険者負担上限額（詳細な健診） */
			String hokenjyaHutanJyougenSyousai = kojinInfo.get(
				"HOKENJYA_FUTAN_SYOUSAI");

			if (hokenjyaHutanJyougenSyousai != null &&
					! hokenjyaHutanJyougenSyousai.isEmpty()) {

				long hokenjyaHutanJyougenSyousaiLong = JLong.valueOf(
						hokenjyaHutanJyougenSyousai);
				inputData.setSyousaiHokenjyaJyougen(
						hokenjyaHutanJyougenSyousaiLong);
			}

			/*------------------------------------------
			 * 追加の健診
			 * ------------------------------------------
			 */
			/* 窓口負担（追加の健診） */
			String inputMadoHutanTsuika = kojinInfo.get("MADO_FUTAN_TSUIKA");
			inputData.setTsuikaMadoFutan(JLong.valueOf(inputMadoHutanTsuika));
			/* 窓口負担種別（追加の健診） */
			String inputMadoHutanTsuikaSyubetu = kojinInfo.get("MADO_FUTAN_T_SYUBETU");
			inputData.setTsuikaMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanTsuikaSyubetu));

			/* 単価（追加の健診） */
			String[] params = {
					kessaiData.hokenjyaNumber,
					kessaiData.uketukeId,
					kessaiData.KensaDate
				};

			ArrayList<Hashtable<String,String>> items =
				JApplication.kikanDatabase.sendExecuteQuery(
					SELECT_TSUIKA_ITEM_DATA_SQL, params );

			Hashtable<String,String> item = null;
			if (items != null && items.size() > 0) {
				item = items.get(0);
			}
			else {
				/* メッセージが必要？ */
				return;
			}

			long inputTankaTsuikaKenshin = JLong.valueOf(item.get("TANKA_KENSIN"));
			int tsuikaCount = JInteger.valueOf(item.get("TSUIKA_COUNT"));

			if (tsuikaCount > 0) {
				inputData.setExistsTsuika(true);
			}

			inputData.setTsuikaTanka(inputTankaTsuikaKenshin);

			/* 保険者負担額上限（追加の健診） */
			String hokenjyaHutanJyougenTsuika = kojinInfo.get(
				"HOKENJYA_FUTAN_TSUIKA");

			if (hokenjyaHutanJyougenTsuika != null &&
				! hokenjyaHutanJyougenTsuika.isEmpty()) {

				long hokenjyaHutanJyougenTsuikaLong = JLong.valueOf(
						hokenjyaHutanJyougenTsuika);
				inputData.setTsuikaHokenjyaJyougen(
						hokenjyaHutanJyougenTsuikaLong);
			}

			/*------------------------------------------
			 * 人間ドック
			 * ------------------------------------------*/

			/* 窓口負担（人間ドック） */
			String inputMadoHutanDock = kojinInfo.get("MADO_FUTAN_DOC");
			inputData.setDockMadoFutan(JLong.valueOf(inputMadoHutanDock));

			/* 窓口負担種別（人間ドック） */
			String inputMadoHutanDockSyubetu = kojinInfo.get("MADO_FUTAN_D_SYUBETU");

			inputData.setDockMadoFutanSyubetu(JInteger.valueOf(inputMadoHutanDockSyubetu));

			// add ver2 s.inoue 2009/06/16
			/* 単価（人間ドック）
			 * 現在、保険者情報に人間ドックの単価を設定する場所が無い。
			 * ひとまず 0 円としておく。 */
			String inputTankaDockKenshin = hokenjyaInfo.get("TANKA_NINGENDOC");
			inputData.setDockTanka(JLong.valueOf(inputTankaDockKenshin));

			// edit s.inoue 2010/01/15  人間ドック対応
			// if (inputData.getDockTanka() > 0){
			//	inputData.setExistsDoc(true);
			// }
			if(hokenjyaInfo.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
				inputData.setExistsDoc(true);
			}

			/* 保険者負担額上限（人間ドック） */
			String hokenjyaHutanJyougenDock = kojinInfo.get(
				"HOKENJYA_FUTAN_DOC");

			if (hokenjyaHutanJyougenDock != null &&
				! hokenjyaHutanJyougenDock.isEmpty()) {

				long hokenjyaHutanJyougenDockLong = JLong.valueOf(
						hokenjyaHutanJyougenDock);
				inputData.setDockHokenjyaJyougen(
						hokenjyaHutanJyougenDockLong);
			}

			/*------------------------------------------
			 * その他の健診による負担額
			 * ------------------------------------------
			 */
			String inputMadoHutanSonota = kojinInfo.get("MADO_FUTAN_SONOTA");
			inputData.setMadoFutanSonota(JLong.valueOf(inputMadoHutanSonota));

			/* ------------------------------------------
			 *  決済処理を行なう。
			 * ------------------------------------------
			 */
			/* 決済情報の出力情報 */
			JKessaiDataOutput outputData = new JKessaiDataOutput();

			outputData = JKessai.Kessai(inputData, kessaiData);

			/* 単価合計 */
			String outputTankaGoukei = String.valueOf(outputData.getTankaGoukei());

			/* JKessaiで計算した後は、0埋めがされていないので、必要なところは埋める。 */
			String outputMadoHutanKihon = Utility.FillZero(outputData.getMadoFutanKihon(), 6);
			String outputMadoHutanSyousai = Utility.FillZero(outputData.getMadoFutanSyousai(), 6);
			String outputMadoHutanTsuika = Utility.FillZero(outputData.getMadoFutanTsuika(), 6);
			String outputMadoHutanDoc = Utility.FillZero(outputData.getMadoFutanDoc(), 6);

			/* Added 2008/04/28 若月 その他の健診による負担金額 */
			/* --------------------------------------------------- */
			String outputMadoHutanSonota = String.valueOf(outputData.getMadoFutanSonota());

			/* 窓口負担合計 */
			String outputMadoHutanGoukei = String.valueOf(outputData.getMadoFutanGoukei());

			/* 請求金額 */
			String outputSeikyuKingaku = String.valueOf(outputData.getSeikyuKingaku());

			/* 桁数の確認を行う */
			if(
					outputTankaGoukei.length() > 9 ||
					outputMadoHutanGoukei.length() > 9 ||
					outputSeikyuKingaku.length() > 9
			)
			{
				JErrorMessage.show("M8401", null);
				continue;
			}

			// edit ver2 s.inoue 2009/08/20
			/* 新規及び更新 */
			Hashtable<String, String> kesaiCnt = null;
			kesaiCnt = JApplication.kikanDatabase.sendExecuteQuery(
					" SELECT COUNT(UKETUKE_ID) UKETUKE_CNT FROM T_KESAI_WK "
					+ " WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber)
					+ " AND UKETUKE_ID = " + JQueryConvert.queryConvert(kessaiData.uketukeId)
					+ " AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kessaiData.KensaDate)).get(0);

			int kesaiCount = Integer.parseInt(kesaiCnt.get("UKETUKE_CNT"));

			// edit ver2 s.inoue 2009/08/21
			// timestamp取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			if (kesaiCount > 0){

				StringBuffer bldUPD = new StringBuffer();

				bldUPD.append("UPDATE T_KESAI_WK SET ");
				bldUPD.append("TKIKAN_NO= " + JQueryConvert.queryConvertAppendComma(tKikanNumber));
				bldUPD.append("JISI_KBN= " + JQueryConvert.queryConvertAppendComma(kessaiData.jissiKubun));
//				bldUPD.append("KENSA_NENGAPI= " + JQueryConvert.queryConvertAppendComma(kessaiData.KensaDate));
				bldUPD.append("SEIKYU_KBN= " + JQueryConvert.queryConvertAppendComma(kessaiData.seikyuKubun));
				bldUPD.append("SYUBETU_CD= " + JQueryConvert.queryConvertAppendComma(kessaiData.syubetuCode));
				bldUPD.append("HKNJANUM= " + JQueryConvert.queryConvertAppendComma(kessaiData.hokenjyaNumber));
				bldUPD.append("HIHOKENJYASYO_KIGOU= " + JQueryConvert.queryConvertAppendComma(kessaiData.hiHokenjyaKigo));
				bldUPD.append("HIHOKENJYASYO_NO= " + JQueryConvert.queryConvertAppendComma(kessaiData.hiHokenjyaNumber));
				bldUPD.append("ITAKU_KBN= " + JQueryConvert.queryConvertAppendComma(itakuKubun));
				bldUPD.append("TANKA_KIHON= " + JQueryConvert.queryConvertAppendComma(inputTankaKihon));
				bldUPD.append("HINKETU_CD= " + JQueryConvert.queryConvertAppendComma(inputHinketsuCode));
				bldUPD.append("TANKA_HINKETU= " + JQueryConvert.queryConvertAppendComma(inputTankaHinketu));
				bldUPD.append("SINDENZU_CD= " + JQueryConvert.queryConvertAppendComma(inputShindenzuCode));
				bldUPD.append("TANKA_SINDENZU= " + JQueryConvert.queryConvertAppendComma(inputTankaShindenzu));
				bldUPD.append("GANTEI_CD= " + JQueryConvert.queryConvertAppendComma(inputGanteiCode));
				bldUPD.append("TANKA_GANTEI= " + JQueryConvert.queryConvertAppendComma(inputTankaGantei));
				bldUPD.append("MADO_FUTAN_K_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanKihonSyubetsu));
				bldUPD.append("MADO_FUTAN_KIHON= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanKihon));
//				bldUPD.append("MADO_FUTAN_KIHON_OUT= " + JQueryConvert.queryConvertAppendComma());
				bldUPD.append("MADO_FUTAN_S_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanSyosaiSyubetsu));
				bldUPD.append("MADO_FUTAN_SYOUSAI= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanSyousai));
//				bldUPD.append("MADO_FUTAN_SYOUSAI_OUT= " + JQueryConvert.queryConvertAppendComma());
				bldUPD.append("MADO_FUTAN_T_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuikaSyubetu));
				bldUPD.append("MADO_FUTAN_TSUIKA= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuika));
//				bldUPD.append("MADO_FUTAN_TSUIKA_OUT= " + JQueryConvert.queryConvertAppendComma());
				bldUPD.append("TANKA_GOUKEI= " + JQueryConvert.queryConvertAppendComma(outputTankaGoukei));
				bldUPD.append("MADO_FUTAN_GOUKEI= " + JQueryConvert.queryConvertAppendComma(outputMadoHutanGoukei));
				bldUPD.append("SEIKYU_KINGAKU= " + JQueryConvert.queryConvertAppendComma(outputSeikyuKingaku));
				bldUPD.append("SIHARAI_DAIKOU_BANGO= " + JQueryConvert.queryConvertAppendComma(kessaiData.daikouKikanNumber));
//				bldUPD.append("NENDO= " + JQueryConvert.queryConvertAppendComma());
//				bldUPD.append("UKETUKE_ID= " + JQueryConvert.queryConvertAppendComma(kessaiData.uketukeId));
				bldUPD.append("MADO_FUTAN_SONOTA= " + JQueryConvert.queryConvertAppendComma(outputMadoHutanSonota));
				bldUPD.append("HOKENJYA_FUTAN_KIHON= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenKihon));
				bldUPD.append("HOKENJYA_FUTAN_SYOUSAI= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenSyousai));
				bldUPD.append("HOKENJYA_FUTAN_TSUIKA= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenTsuika));
				bldUPD.append("HOKENJYA_FUTAN_DOC= " + JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenDock));
				bldUPD.append("TANKA_NINGENDOC= " + JQueryConvert.queryConvertAppendComma(inputTankaDockKenshin));
				bldUPD.append("MADO_FUTAN_D_SYUBETU= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanDockSyubetu));
				bldUPD.append("MADO_FUTAN_DOC= " + JQueryConvert.queryConvertAppendComma(inputMadoHutanDock));
				bldUPD.append("MADO_FUTAN_DOC_OUT= " + JQueryConvert.queryConvertAppendComma(outputMadoHutanDoc));
				bldUPD.append("UPDATE_TIMESTAMP = "  + JQueryConvert.queryConvert(stringTimeStamp));
				// edit s.inoue 2009/10/13
				bldUPD.append(" WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber));
				bldUPD.append(" AND UKETUKE_ID= " + JQueryConvert.queryConvert(kessaiData.uketukeId));
				bldUPD.append(" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kessaiData.KensaDate));

				String query = bldUPD.toString();
				JApplication.kikanDatabase.sendNoResultQuery(query);

				// edit ver2 s.inoue 2009/08/21
				/* 追加詳細テーブルに追加健診項目の情報を追加する。 */
				insertKessaiSyousai(tKikanNumber, kessaiData,true);

			}else{

				StringBuffer bldINS = new StringBuffer();

				bldINS.append(INSERT_SQL_BASE);
				bldINS.append(" VALUES (");

				/* 健診機関番号 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(tKikanNumber));
				/* 実施区分 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.jissiKubun));
				/* 健診年月日 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.KensaDate));
				/* 請求区分 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.seikyuKubun));
				/* 種別コード */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.syubetuCode));
				/* 保険者番号 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.hokenjyaNumber));
				/* 被保険者証等記号 */
				bldINS.append(JQueryConvert.queryConvertAppendBlankAndComma(hiHokenjyaKigo));
				/* 被保険者証等番号 */
				bldINS.append(JQueryConvert.queryConvertAppendBlankAndComma(kessaiData.hiHokenjyaNumber));
				/* 委託区分 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(itakuKubun));
				/* 単価（基本的な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaKihon));
				/* 貧血コード */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputHinketsuCode));
				/* 単価（詳細な健診、貧血） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaHinketu));
				/* 心電図コード */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputShindenzuCode));
				/* 単価（詳細な健診、心電図） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaShindenzu));
				/* 眼底コード */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputGanteiCode));
				/* 単価（詳細な健診、眼底） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaGantei));
				/* 窓口負担種別（基本的な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanKihonSyubetsu));
				/* 窓口負担（基本的な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanKihon));
				/* 窓口負担種別（詳細な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanSyosaiSyubetsu));
				/* 窓口負担（詳細な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanSyousai));
				/* 窓口負担種別（追加の健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuikaSyubetu));
				/* 窓口負担（追加の健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanTsuika));
				/* 単価合計 */
				// edit s.inoue 2010/02/10
				if (hokenjyaInfo.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
					bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaDockKenshin));
				}else{
					bldINS.append(JQueryConvert.queryConvertAppendComma(outputTankaGoukei));
				}
				/* 窓口負担合計 */
				// edit s.inoue 2010/02/10
				if (hokenjyaInfo.get("TANKA_HANTEI").equals(TANKA_HANTEI_DOC)){
					bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanDoc));
				}else{
					bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanGoukei));
				}

				/* 請求金額 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputSeikyuKingaku));

				/* 変換日時*/
				// del ver2 s.inoue 2009/04/02
				//buffer.append(JQueryConvert.queryConvertAppendComma(null));
				/* 窓口負担（基本的な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanKihon));
				/* 窓口負担（詳細な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanSyousai));
				/* 窓口負担（追加の健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanTsuika));
				/* 支払代行機関番号 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.daikouKikanNumber));
				/* 受付ID */
				bldINS.append(JQueryConvert.queryConvertAppendComma(kessaiData.uketukeId));
				/* その他の健診による負担金額 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanSonota));
				/* 保険者負担額上限 */
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenKihon));
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenSyousai));
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenTsuika));
				bldINS.append(JQueryConvert.queryConvertAppendComma(hokenjyaHutanJyougenDock));

				/* 単価（人間ドック） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputTankaDockKenshin));
				/* 窓口負担種別（基本的な健診） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanDockSyubetu));

				/* 窓口負担（人間ドック） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(inputMadoHutanDock));
				/* 窓口負担（人間ドック） */
				bldINS.append(JQueryConvert.queryConvertAppendComma(outputMadoHutanDoc));
				// edit ver2 s.inoue 2009/08/24
				bldINS.append(JQueryConvert.queryConvert(stringTimeStamp));
				bldINS.append(")");

				String query = bldINS.toString();
				JApplication.kikanDatabase.sendNoResultQuery(query);
			}

			// edit ver2 s.inoue 2009/08/21
			/* 追加詳細テーブルに追加健診項目の情報を追加する。 */
			insertKessaiSyousai(tKikanNumber, kessaiData,false);
		}
	}

	// add ver2 s.inoue 2009/09/09
	/**
	 * 決済確定処理 (work → kessai)
	 * @param kessaiDatas 処理を行う受信者のリスト
	 * @param tKikanNumber 健診を行った機関の番号
	 */
	public static void runWorkToKessaiProcess(Vector<JKessaiProcessData> kessaiDatas,String tKikanNumber) throws Exception
	{
		// work → kessaiテーブル
		// 1.workテーブルからkessaiテーブルへデータコピー
		// 2.workテーブルからデータ削除
		for(int i = 0 ; i < kessaiDatas.size() ; i++)
		{
			// 個人ごとのデータ
			JKessaiProcessData kessaiData = kessaiDatas.get(i);
			// add s.inoue 2009/10/19
			// 実テーブルの削除
			deleteKesai(kessaiData.uketukeId);
			// _WK → T_KESAI
			registerWorkToKesai(kessaiData);
			// _WK → T_KESAI_SYOUSAI
			registerWorkToKesaiSyousai(kessaiData);
			// del s.inoue 2009/10/18
			// _WKテーブルの削除
			// deleteKesai(kessaiData.uketukeId);

			// move s.inoue 2012/11/02
			// _WKテーブルの削除
			// deleteWork(kessaiData.uketukeId);
		}

	}

	// add ver2 s.inoue 2009/09/09
	/**
	 * WORKテーブルから決済テーブルへ登録処理を実施する。
	 */
	private static void registerWorkToKesai(JKessaiProcessData kessaiData){

		// 履歴テーブル登録処理(元データは、個人テーブルより取得)
		// timestamp取得
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		StringBuilder workQuery = new StringBuilder();
		workQuery.append("INSERT INTO T_KESAI");
		workQuery.append(" (TKIKAN_NO, JISI_KBN, KENSA_NENGAPI, SEIKYU_KBN, SYUBETU_CD, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		workQuery.append(" HIHOKENJYASYO_NO, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD,");
		workQuery.append(" TANKA_GANTEI, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_KIHON_OUT, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI,");
		workQuery.append(" MADO_FUTAN_SYOUSAI_OUT, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_TSUIKA_OUT, TANKA_GOUKEI, MADO_FUTAN_GOUKEI,");
		workQuery.append(" SEIKYU_KINGAKU, SIHARAI_DAIKOU_BANGO, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI,");
		workQuery.append(" HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC, TANKA_NINGENDOC, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, MADO_FUTAN_DOC_OUT,UPDATE_TIMESTAMP)");
		workQuery.append(" SELECT ");
		workQuery.append(" TKIKAN_NO, JISI_KBN, KENSA_NENGAPI, SEIKYU_KBN, SYUBETU_CD, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		workQuery.append(" HIHOKENJYASYO_NO, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD,");
		workQuery.append(" TANKA_GANTEI, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_KIHON_OUT, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI,");
		workQuery.append(" MADO_FUTAN_SYOUSAI_OUT, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_TSUIKA_OUT, TANKA_GOUKEI, MADO_FUTAN_GOUKEI,");
		workQuery.append(" SEIKYU_KINGAKU, SIHARAI_DAIKOU_BANGO, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI,");
		workQuery.append(" HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC, TANKA_NINGENDOC, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, MADO_FUTAN_DOC_OUT,");
		workQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
		workQuery.append(" FROM T_KESAI_WK ");
		workQuery.append(" WHERE TKIKAN_NO = ");
		workQuery.append(JQueryConvert.queryConvert(JApplication.kikanNumber));
		workQuery.append(" AND UKETUKE_ID = ");
		workQuery.append(JQueryConvert.queryConvert(kessaiData.uketukeId));
		workQuery.append(" AND KENSA_NENGAPI = ");
		workQuery.append(JQueryConvert.queryConvert(kessaiData.KensaDate));

		try {
			JApplication.kikanDatabase.sendNoResultQuery(workQuery.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());

			try {
				JApplication.kikanDatabase.rollback();
			} catch (SQLException g) {
				logger.error(g.getMessage());
			}
		}
	}

	// add ver2 s.inoue 2009/09/09
	/**
	 * WORKテーブルから決済詳細テーブルへ登録処理を実施する。
	 */
	private static void registerWorkToKesaiSyousai(JKessaiProcessData kessaiData){

		// 履歴テーブル登録処理(元データは、個人テーブルより取得)
		// timestamp取得
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		StringBuilder workQuery = new StringBuilder();
		workQuery.append("INSERT INTO T_KESAI_SYOUSAI (");
		workQuery.append("TKIKAN_NO,KENSA_NENGAPI,HKNJANUM,HIHOKENJYASYO_KIGOU,HIHOKENJYASYO_NO,TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN,UKETUKE_ID,UPDATE_TIMESTAMP) ");
		workQuery.append(" SELECT ");
		workQuery.append("TKIKAN_NO,KENSA_NENGAPI,HKNJANUM,HIHOKENJYASYO_KIGOU,HIHOKENJYASYO_NO,TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN,UKETUKE_ID,");
		workQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
		workQuery.append(" FROM T_KESAI_SYOUSAI_WK WHERE UKETUKE_ID = ");
		workQuery.append( JQueryConvert.queryConvert(kessaiData.uketukeId));
		workQuery.append(" AND KENSA_NENGAPI = ");
		workQuery.append( JQueryConvert.queryConvert(kessaiData.KensaDate));
		// workQuery.append(" AND TUIKA_KENSIN_CD = ");
		// workQuery.append( JQueryConvert.queryConvert(kessaiData.));
		workQuery.append(" AND HKNJANUM = ");
		workQuery.append( JQueryConvert.queryConvert(kessaiData.hokenjyaNumber));

		try {
			JApplication.kikanDatabase.sendNoResultQuery(workQuery.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	// add s.inoue 2009/10/16
	/**
	 * 削除処理を行う
	 */
	public static void deleteKesai(String uketukeId)
	{
		// T_KESAIデータ削除
		StringBuilder workKesaiQuery = new StringBuilder();
		try{

			workKesaiQuery.append("DELETE FROM T_KESAI");
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

			workSyousaiQuery.append("DELETE FROM T_KESAI_SYOUSAI");
			workSyousaiQuery.append(" WHERE UKETUKE_ID = ");
			workSyousaiQuery.append(JQueryConvert.queryConvert(uketukeId));

			JApplication.kikanDatabase.sendNoResultQuery(workSyousaiQuery.toString());

		}catch(SQLException err)
		{
			logger.error(err.getMessage());
		}
	}
	// add ver2 s.inoue 2009/09/09
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

	private static String INSERT_SQL_BASE = null;
	static {
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT INTO ");
		buffer.append(" T_KESAI_WK ");

		/* 健診機関番号 */
		buffer.append("(TKIKAN_NO,");
		/* 実施区分 */
		buffer.append("JISI_KBN,");
		/* 健診年月日 */
		buffer.append("KENSA_NENGAPI,");
		/* 請求区分 */
		buffer.append("SEIKYU_KBN,");
		/* 種別コード */
		buffer.append("SYUBETU_CD,");
		/* 保険者番号 */
		buffer.append("HKNJANUM,");
		/* 被保険者証等記号 */
		buffer.append("HIHOKENJYASYO_KIGOU,");
		/* 被保険者証等番号 */
		buffer.append("HIHOKENJYASYO_NO,");
		/* 委託区分 */
		buffer.append("ITAKU_KBN,");
		/* 単価（基本的な健診） */
		buffer.append("TANKA_KIHON,");
		/* 貧血コード */
		buffer.append("HINKETU_CD,");
		/* 単価（詳細な健診、貧血） */
		buffer.append("TANKA_HINKETU,");
		/* 心電図コード */
		buffer.append("SINDENZU_CD,");
		/* 単価（詳細な健診、心電図） */
		buffer.append("TANKA_SINDENZU,");
		/* 眼底コード */
		buffer.append("GANTEI_CD,");
		/*単価（詳細な健診、眼底） */
		buffer.append("TANKA_GANTEI,");

		/* 窓口負担種別（基本的な健診） */
		buffer.append("MADO_FUTAN_K_SYUBETU,");
		/* 窓口負担（基本的な健診） */
		buffer.append("MADO_FUTAN_KIHON,");

		/* 窓口負担種別（詳細な健診） */
		buffer.append("MADO_FUTAN_S_SYUBETU,");
		/* 窓口負担（詳細な健診） */
		buffer.append("MADO_FUTAN_SYOUSAI,");

		/* 窓口負担種別（追加の健診） */
		buffer.append("MADO_FUTAN_T_SYUBETU,");
		// edit ver2 s.inoue 2009/06/18
		/* 窓口負担（追加の健診） */
		buffer.append("MADO_FUTAN_TSUIKA,");

		/* 単価合計 */
		buffer.append("TANKA_GOUKEI,");
		/* 窓口負担合計 */
		buffer.append("MADO_FUTAN_GOUKEI,");
		/* 請求金額 */
		buffer.append("SEIKYU_KINGAKU,");
		/*  変換日時*/
		// del ver2 s.inoue 2009/04/02
		//buffer.append("HENKAN_NITIJI,");

		/* 窓口負担（基本的な健診） */
		buffer.append("MADO_FUTAN_KIHON_OUT,");
		/* 窓口負担（詳細な健診） */
		buffer.append("MADO_FUTAN_SYOUSAI_OUT,");
		// edit ver2 s.inoue 2009/06/18
		/* 窓口負担（追加の健診） */
		buffer.append("MADO_FUTAN_TSUIKA_OUT,");

		/* 支払代行機関番号 */
		buffer.append("SIHARAI_DAIKOU_BANGO,");

		/* 受付ID */
		buffer.append("UKETUKE_ID,");

//		/* その他の健診による負担額 */
		buffer.append("MADO_FUTAN_SONOTA, ");

		/* 保険者負担額上限 */
		buffer.append("HOKENJYA_FUTAN_KIHON, ");
		buffer.append("HOKENJYA_FUTAN_SYOUSAI, ");
		buffer.append("HOKENJYA_FUTAN_TSUIKA, ");
		buffer.append("HOKENJYA_FUTAN_DOC,");
		/*単価（人間ドック） */
		buffer.append("TANKA_NINGENDOC,");

		/* 窓口負担種別（人間ドック） */
		buffer.append("MADO_FUTAN_D_SYUBETU,");
		/* 窓口負担（人間ドック） */
		buffer.append("MADO_FUTAN_DOC,");
		/* 窓口負担（人間ドック） */
		buffer.append("MADO_FUTAN_DOC_OUT,");
		// edit ver2 s.inoue 2009/08/24
		/* 更新日付 */
		buffer.append("UPDATE_TIMESTAMP");

		buffer.append(") ");

		INSERT_SQL_BASE = buffer.toString();
	}

	/**
	 * 追加の健診項目のデータを取得する SQL を作成する。
	 */
	private static final String SELECT_TSUIKA_ITEM_DATA_SQL = createTsuikaItemDataSql();

	private static String createTsuikaItemDataSql() {

		StringBuffer buffer = new StringBuffer();
		buffer.append(" select ");
		buffer.append("    sum(TANKA_KENSIN) as TANKA_KENSIN ");

		buffer.append("    , count(1) as TSUIKA_COUNT ");
		buffer.append(" from ");
		buffer.append("    T_KENSAKEKA_SONOTA a ");
		buffer.append(" left join T_KENSHINMASTER b ");
		buffer.append(" on ");
		buffer.append("    a.KOUMOKU_CD = b.KOUMOKU_CD ");

		// edit s.inoue 2009/12/03
		buffer.append(" left join T_HOKENJYA c on b.HKNJANUM = c.HKNJANUM ");
		buffer.append(" where b.HKNJANUM = ? ");
		buffer.append(" and a.UKETUKE_ID = ? ");
		buffer.append(" and a.KENSA_NENGAPI = ? ");
		buffer.append(" and a.KEKA_TI is not null ");
		buffer.append(" and a.KEKA_TI <> '' ");
		// edit s.inoue 2009/12/03
		buffer.append(" and c.TANKA_HANTEI <> '2' ");
		// add s.inoue 2010/01/15
		buffer.append(" and c.YUKOU_FLG = '1' ");

		buffer.append(" and ");
		buffer.append("   ( b.HISU_FLG = '3' ");
		buffer.append(" or ");
		buffer.append("   ( b.HISU_FLG = '2' ");
		buffer.append("     and");
		buffer.append("     b.TANKA_KENSIN is not null ");
		buffer.append("     ) )");
		String sql = buffer.toString();

		return sql;
	}

	// edit ver2 s.inoue 2009/08/21
	// 新規,更新用に更新
	/**
	 * 決済詳細テーブルに追加の健診項目データを追加する。
	 */
	private static void insertKessaiSyousai(String tKikanNumber, JKessaiProcessData kojinData, boolean updateFlg) throws SQLException {

		String selectSql = "select * from T_KENSAKEKA_SONOTA a left join T_KENSHINMASTER b on " +
						"a.KOUMOKU_CD = b.KOUMOKU_CD where b.HKNJANUM = " + JQueryConvert.queryConvert(kojinData.hokenjyaNumber) +
						" and a.UKETUKE_ID = " + JQueryConvert.queryConvert(kojinData.uketukeId) +
						" and a.KENSA_NENGAPI = " + JQueryConvert.queryConvert(kojinData.KensaDate) +
						" and a.KEKA_TI is not null " +
						" and ( HISU_FLG = '3' or ( HISU_FLG = '2' and b.TANKA_KENSIN is not null ) )";

		// 追加の健診項目を決済データ詳細に書き込む
		ArrayList<Hashtable<String,String>> tsuikaItems = JApplication.kikanDatabase.sendExecuteQuery(selectSql);

// add ver2 s.inoue 2009/08/27
		// 決済詳細テーブルにデータがあるかどうか
//		Hashtable<String, String> HokenjyaInfo = null;
//		String sql = " SELECT * FROM T_HOKENJYA WHERE HKNJANUM = " +
//							JQueryConvert.queryConvert(hokenjyaNumber);
//
//		ArrayList<Hashtable<String, String>> queryResult = JApplication.kikanDatabase.sendExecuteQuery(sql);
//		if (queryResult == null || queryResult.size() == 0) {
//			JErrorMessage.show("M3140",null);
//			throw new Exception();
//		}
//		HokenjyaInfo = queryResult.get(0);

		// move s.inoue 2009/11/13
//		/* 新規及び更新 */
//		Hashtable<String, String> kesaiCnt = null;
//		kesaiCnt = JApplication.kikanDatabase.sendExecuteQuery(
//				" SELECT COUNT(UKETUKE_ID) UKETUKE_CNT FROM T_KESAI_SYOUSAI_WK "
//				+ " WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber)
//				+ " AND UKETUKE_ID = " + JQueryConvert.queryConvert(kojinData.uketukeId)
//				+ " AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kojinData.KensaDate)
//				// edit s.inoue 2009/11/13
//				+ " AND TUIKA_KENSIN_CD = " + JQueryConvert.queryConvert(tsuikaItems.get(j).get("KOUMOKU_CD"))
//				).get(0);
//
//		int kesaiCount = Integer.parseInt(kesaiCnt.get("UKETUKE_CNT"));


		for(int j = 0 ; j < tsuikaItems.size() ; j++)
		{
			String kekati = tsuikaItems.get(j).get("KEKA_TI");
			if (kekati == null) {
				continue;
			}else if (kekati.isEmpty()) {
				continue;
			}

			String tankaKenshin = tsuikaItems.get(j).get("TANKA_KENSIN");
			if (tankaKenshin == null){
				tankaKenshin = "0";
			}else if (tankaKenshin.equals("")){
				tankaKenshin = "0";
			}

			// timestamp取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			StringBuilder insertSql = new StringBuilder();

			// move s.inoue 2009/09/17
			/* 新規及び更新 */
			Hashtable<String, String> kesaiCnt = null;
			kesaiCnt = JApplication.kikanDatabase.sendExecuteQuery(
					" SELECT COUNT(UKETUKE_ID) UKETUKE_CNT FROM T_KESAI_SYOUSAI_WK "
					+ " WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(JApplication.kikanNumber)
					+ " AND UKETUKE_ID = " + JQueryConvert.queryConvert(kojinData.uketukeId)
					+ " AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kojinData.KensaDate)
					// edit s.inoue 2009/11/13
					+ " AND TUIKA_KENSIN_CD = " + JQueryConvert.queryConvert(tsuikaItems.get(j).get("KOUMOKU_CD"))
					).get(0);

			int kesaiCount = Integer.parseInt(kesaiCnt.get("UKETUKE_CNT"));

			// edit s.inoue 2009/09/17
			// updateflg→kesaiCount
			if (kesaiCount > 0){
				insertSql.append("UPDATE T_KESAI_SYOUSAI_WK SET ");
				insertSql.append("TKIKAN_NO = " + JQueryConvert.queryConvertAppendComma(tKikanNumber));
				insertSql.append("KENSA_NENGAPI = " + JQueryConvert.queryConvertAppendComma(kojinData.KensaDate));
				// edit ver2 s.inoue 2009/08/24
				// insertSql.append("HKNJANUM = " + JQueryConvert.queryConvertAppendComma(kojinData.hokenjyaNumber));
				insertSql.append("HIHOKENJYASYO_KIGOU = " + JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaKigo));
				insertSql.append("HIHOKENJYASYO_NO = " + JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaNumber));
				// edit ver2 s.inoue 2009/08/24
				// insertSql.append("TUIKA_KENSIN_CD = " + JQueryConvert.queryConvertAppendComma(tsuikaItems.get(j).get("KOUMOKU_CD")));
				insertSql.append("TANKA_TUIKA_KENSIN = " + JQueryConvert.queryConvertAppendComma(tankaKenshin));
				insertSql.append("UPDATE_TIMESTAMP = "+ JQueryConvert.queryConvert(stringTimeStamp));

				// edit ver2 s.inoue 2009/08/21
				// keyが不正な為修正
				insertSql.append(" WHERE UKETUKE_ID = ");
				insertSql.append( JQueryConvert.queryConvert(kojinData.uketukeId));
				insertSql.append(" AND KENSA_NENGAPI = ");
				insertSql.append( JQueryConvert.queryConvert(kojinData.KensaDate));
				insertSql.append(" AND TUIKA_KENSIN_CD = ");
				insertSql.append( JQueryConvert.queryConvert(tsuikaItems.get(j).get("KOUMOKU_CD")));
				insertSql.append(" AND HKNJANUM = ");
				insertSql.append( JQueryConvert.queryConvert(kojinData.hokenjyaNumber));

			}else{
				insertSql.append("INSERT INTO T_KESAI_SYOUSAI_WK (");
				insertSql.append("TKIKAN_NO,KENSA_NENGAPI,HKNJANUM,HIHOKENJYASYO_KIGOU,HIHOKENJYASYO_NO,TUIKA_KENSIN_CD,TANKA_TUIKA_KENSIN,UKETUKE_ID,UPDATE_TIMESTAMP ");
				insertSql.append(" )VALUES( ");
				insertSql.append( JQueryConvert.queryConvertAppendComma(tKikanNumber));
				insertSql.append( JQueryConvert.queryConvertAppendComma(kojinData.KensaDate));
				insertSql.append( JQueryConvert.queryConvertAppendComma(kojinData.hokenjyaNumber));
				insertSql.append( JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaKigo));
				insertSql.append( JQueryConvert.queryConvertAppendBlankAndComma(kojinData.hiHokenjyaNumber));
				insertSql.append( JQueryConvert.queryConvertAppendComma(tsuikaItems.get(j).get("KOUMOKU_CD")));
				insertSql.append( JQueryConvert.queryConvertAppendComma(tankaKenshin));
				insertSql.append( JQueryConvert.queryConvertAppendComma(kojinData.uketukeId));
				insertSql.append( JQueryConvert.queryConvert(stringTimeStamp));
				insertSql.append(" ) ");
			}

			JApplication.kikanDatabase.sendNoResultQuery(insertSql.toString());
		}
	}

	private static Hashtable<String, String> getHokenjaInfo(String hokenjyaNumber) throws SQLException, Exception {
		// 医療保険者情報データテーブルからの項目
		Hashtable<String, String> HokenjyaInfo = null;

		// add s.inoue 2010/01/15
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT * FROM T_HOKENJYA WHERE HKNJANUM = ");
		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
		sb.append(" AND YUKOU_FLG = '1' ");

		ArrayList<Hashtable<String, String>> queryResult = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		if (queryResult == null || queryResult.size() == 0) {
			JErrorMessage.show("M3140",null);
			throw new Exception();
		}

		HokenjyaInfo = queryResult.get(0);
		return HokenjyaInfo;
	}

	/**
	 * 詳細健診の検査が行われているかチェックする
	 * @param hokenjyaNumber 保険者番号
	 * @param HiHokenjyaKigo 被保険者証等記号
	 * @param HiHokenjyaNumber 被保険者証等番号
	 * @param kensaDate 健診日時
	 * @param lensaGroup 検査グループ
	 * @return true -> 存在する
	 */
	public static boolean isExistSyousaiKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String lensaGroup)
	{
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append(" select sonota.KEKA_TI, master.KOUMOKU_CD, master.TANKA_KENSIN ");

			buffer.append(" from T_KENSAKEKA_SONOTA sonota ");

			buffer.append(" left join T_KENSHINMASTER master ");
			buffer.append(" on ");
			buffer.append(" sonota.KOUMOKU_CD = master.KOUMOKU_CD ");

			buffer.append(" where master.HKNJANUM = " + JQueryConvert.queryConvert(hokenjyaNumber));
			buffer.append(" and sonota.UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId));
			buffer.append(" and sonota.KENSA_NENGAPI = " + JQueryConvert.queryConvert(kensaDate));
			buffer.append(" and sonota.KEKA_TI is not null");
			buffer.append(" and HISU_FLG = " + JQueryConvert.queryConvert("2"));
			buffer.append(" and master.KENSA_GROUP = " + JQueryConvert.queryConvert(lensaGroup));

			String query = buffer.toString();

			ArrayList<Hashtable<String, String>> result = JApplication.kikanDatabase.sendExecuteQuery(query);
			for (Hashtable<String, String> item : result) {

				/* 単価を設定している場合は、追加健診項目として扱う。 */
				String tanka = item.get("TANKA_KENSIN");
				if (tanka != null && ! tanka.isEmpty() ) {
					continue;
				}

				/* 結果値を取得し、null か空文字以外なら、入力があったものとしてカウントする。 */
				String kekati = item.get("KEKA_TI");
				if (kekati != null && ! kekati.isEmpty()) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}
}
