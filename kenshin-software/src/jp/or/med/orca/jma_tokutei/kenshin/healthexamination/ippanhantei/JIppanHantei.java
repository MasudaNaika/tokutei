package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei;

import java.sql.SQLException;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * Modified 2008/03/30
 * ソースコードの視認性が低く、バグ及び処理抜けがあったため、全面的に書き換えた。
 */
public class JIppanHantei {

	public static boolean Hantei(Vector<JIppanHanteiStartData> dataSet)
	{
		ArrayList<Hashtable<String,String>> result = null;
		Hashtable<String,String> resultItem = null;
		JIppanHanteiStartData data;
		String sex = null;
		String Query = null;

		for(int j = 0;j < dataSet.size();j++)
		{
			data = dataSet.get(j);

			/* まず判定対象が男性か女性かを判別する */
			Query =
			"SELECT SEX FROM T_KOJIN WHERE " +
			"UKETUKE_ID = " + JQueryConvert.queryConvert(data.getUketukeId());

			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(Query);
				resultItem = result.get(0);
			}catch(SQLException e){
				e.printStackTrace();
			}
			sex = resultItem.get("SEX");

			StringBuffer buffer = new StringBuffer();

			/* 項目コードごとに、結果値、上限値、下限値、HL判定を取得しているようだ。
			 * HL判定はこれから行なうはずなのに、この時点で取得している理由は不明。 */
			buffer.append("SELECT ");
//			buffer.append("KOJIN.HIHOKENJYASYO_KIGOU,");
//			buffer.append("KOJIN.HIHOKENJYASYO_NO, ");
			buffer.append("MASTER.DS_JYOUGEN,");
			buffer.append("MASTER.DS_KAGEN, ");

			/* Added 2008/05/08 若月  */
			/* --------------------------------------------------- */
			buffer.append("MASTER.JS_JYOUGEN,");
			buffer.append("MASTER.JS_KAGEN, ");
			/* --------------------------------------------------- */

			buffer.append("SONOTA.KEKA_TI,");
			buffer.append("SONOTA.H_L,SONOTA.HANTEI,");
			buffer.append("SONOTA.KOUMOKU_CD, ");
			// edit 20080918 s.inoue
			buffer.append("SONOTA.JISI_KBN, ");
			buffer.append("TOKUTEI.KENSA_NENGAPI ");
			buffer.append("FROM T_KOJIN AS KOJIN ");
			buffer.append("INNER JOIN T_KENSAKEKA_SONOTA AS SONOTA ");
			buffer.append("ON ");
			buffer.append(" ( ");
			buffer.append("    KOJIN.UKETUKE_ID = SONOTA.UKETUKE_ID ");
			buffer.append(" ) ");
			buffer.append("INNER JOIN T_KENSHINMASTER MASTER ");
			buffer.append("ON ");
			buffer.append("( ");
			buffer.append("    KOJIN.HKNJANUM = MASTER.HKNJANUM ");
			buffer.append("    AND ");
			buffer.append("    SONOTA.KOUMOKU_CD = MASTER.KOUMOKU_CD ");
			buffer.append(") ");

			buffer.append("INNER JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ");
			buffer.append("ON  ");
			buffer.append("( ");
			buffer.append("    KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
			buffer.append(") ");
			buffer.append("WHERE ");
//			buffer.append("( ");
			buffer.append("    KOJIN.UKETUKE_ID = ");
			buffer.append(JQueryConvert.queryConvert(data.getUketukeId()));
			buffer.append("    AND ");
			buffer.append("    TOKUTEI.KENSA_NENGAPI = ");
			buffer.append(JQueryConvert.queryConvert(data.getKensaJissiDate()));
			buffer.append("AND ");
			buffer.append("    SONOTA.KEKA_TI IS NOT NULL ");

						// edit s.inoue 20081126 基準値がない結果データが取得できなくなる為、この部分は不要
						/* Deleted 2008/03/30 若月 上限値、下限値は、片方だけでも有効なので、
						 * オリジナルのソースに有るこの部分の処理は不要。 */
						/* --------------------------------------------------- */
//						buffer.append("    AND  ");
//						/* 男性の場合 */
//						if( sex.equals("1") ){
//							buffer.append("    MASTER.DS_JYOUGEN IS NOT NULL ");
//							buffer.append("    AND ");
//							buffer.append("    MASTER.DS_KAGEN IS NOT NULL ");
//						/* 女性の場合 */
//						}else if( sex.equals("2") ){
//							buffer.append("    MASTER.JS_JYOUGEN IS NOT NULL ");
//							buffer.append("    AND ");
//							buffer.append("    MASTER.JS_KAGEN IS NOT NULL ");
//						}
						/* --------------------------------------------------- */

//			buffer.append(")");

			/*
			 *
			 */
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
			}catch(SQLException e){
				e.printStackTrace();
			}

			try{
				JApplication.kikanDatabase.Transaction();

				for( int i = 0;i < result.size();i++ )
				{
					/* 比較結果を判定処理クラスのオブジェクトにデフォルト値を設定する。 */
					JIppanHanteiProcessData updateData = new JIppanHanteiProcessData();
					try {
						updateData.setH_l("");
						updateData.setHantei("異常なし");

						/* 結果値を数値に変換する */
						resultItem = result.get(i);
						// add 20080917 s.inoue
						updateData.setJisi_KBN(resultItem.get("JISI_KBN"));

						String itemKekati = resultItem.get("KEKA_TI");

						if (itemKekati == null || itemKekati.equals("")) {
							continue;
						}

						Double kekati = 0d;
						try {
							kekati = Double.valueOf(itemKekati);

						} catch (NumberFormatException e) {
							/* 変換に失敗した場合は、この項目の処理を中止する。 */
							System.out.println("continue to next roop.");
							continue;
						}

						/* 数値の比較による判定を行なう。 */
						resultItem = result.get(i);

						String itemJyougen = null;
						String itemKagen = null;
						/* 男性の場合 */
						if( sex.equals("1") ){
							itemJyougen = resultItem.get("DS_JYOUGEN");
							itemKagen = resultItem.get("DS_KAGEN");

						/* 女性の場合 */
						}else if( sex.equals("2") ){
							itemJyougen = resultItem.get("JS_JYOUGEN");
							itemKagen = resultItem.get("JS_KAGEN");
						}

						/* Modified 2008/03/31 若月 null 値チェック追加 */
						/* --------------------------------------------------- */
//						/* 上限値を数値に変換する。 */
//						boolean jyougenExists = false;
//						Double jyougen = 0d;
//						try {
//							jyougen = Double.valueOf(itemJyougen);
//							jyougenExists = true;
//
//						} catch (NumberFormatException e) {
//							/* 変換に失敗した場合は、この項目の処理を中止する。 */
//							e.printStackTrace();
//							System.out.println("do nothing.");
//						}
//
//						/* 下限値を数値に変換する。 */
//						boolean kagenExists = false;
//						Double kagen = 0d;
//						try {
//							kagen = Double.valueOf(itemKagen);
//							kagenExists = true;
//
//						} catch (NumberFormatException e) {
//							/* 変換に失敗した場合は、この項目の処理を中止する。 */
//							e.printStackTrace();
//							System.out.println("do nothing.");
//						}
						/* --------------------------------------------------- */
						/* 上限値を数値に変換する。 */
						boolean jyougenExists = false;
						Double jyougen = 0d;

						if (itemJyougen != null && ! itemJyougen.isEmpty()) {
							try {
								jyougen = Double.valueOf(itemJyougen);
								jyougenExists = true;

							} catch (NumberFormatException e) {
								/* 変換に失敗した場合は、この項目の処理を中止する。 */
								e.printStackTrace();
								System.out.println("do nothing.");
							}
						}

						/* 下限値を数値に変換する。 */
						boolean kagenExists = false;
						Double kagen = 0d;

						if (itemKagen != null && ! itemKagen.isEmpty()) {
							try {
								kagen = Double.valueOf(itemKagen);
								kagenExists = true;

							} catch (NumberFormatException e) {
								/* 変換に失敗した場合は、この項目の処理を中止する。 */
								e.printStackTrace();
								System.out.println("do nothing.");
							}
						}
						/* --------------------------------------------------- */

						/* 上限値が存在する場合 */
						if (jyougenExists) {
							/* 上限値と下限値が存在する場合 */
							if (kagenExists) {
								/* 結果値が上限値より大きい場合 */
								if( kekati > jyougen ){
									updateData.setH_l("H");
									updateData.setHantei("異常");
								}
								/* 結果値が下限値より小さい場合 */
								else if(kekati < kagen){
									updateData.setH_l("L");
									updateData.setHantei("異常");
								}
								else {
									/* 正常値（デフォルト） */
								}
							}
							/* 上限値のみが存在する場合 */
							else {
								/* 結果値が上限値より大きい場合 */
								if( kekati > jyougen ){
									updateData.setH_l("H");
									updateData.setHantei("異常");
								}
								else {
									/* 正常値（デフォルト） */
								}
							}
						}
						/* 上限値が存在しない場合 */
						else {
							/* 下限値のみが存在する場合 */
							if (kagenExists) {
								/* 結果値が下限値より小さい場合 */
								if(kekati < kagen){
									updateData.setH_l("L");
									updateData.setHantei("異常");
								}
								else {
									/* 正常値（デフォルト） */
								}
							}
							/* 上限値も下限値も存在しない場合 */
							else {
								/* 正常値（デフォルト） */
							}
						}
					}
					finally {

						/* Modified 2008/05/08 若月  */
						/* --------------------------------------------------- */
//						String updateQuery =
//							"UPDATE T_KENSAKEKA_SONOTA " +
//							"SET " +
//							"H_L = " + JQueryConvert.queryConvertAppendComma(updateData.getH_l()) +
//							"HANTEI = " + JQueryConvert.queryConvert(updateData.getHantei()) +
//							"WHERE " +
//							"UKETUKE_ID = " + JQueryConvert.queryConvert(data.getUketukeId()) + " " +
//							"AND " +
//							"KENSA_NENGAPI = " + JQueryConvert.queryConvert(data.getKensaJissiDate()) +
//							"AND " +
//							"KOUMOKU_CD = " + JQueryConvert.queryConvert(resultItem.get("KOUMOKU_CD"));
						/* --------------------------------------------------- */
						StringBuffer buffer2 = new StringBuffer();

						buffer2.append("UPDATE T_KENSAKEKA_SONOTA ");
						buffer2.append("SET ");
						buffer2.append("H_L = ");
						buffer2.append(JQueryConvert.queryConvertAppendComma(updateData.getH_l()));
						buffer2.append("HANTEI = ");
						buffer2.append(JQueryConvert.queryConvertAppendComma(updateData.getHantei()));
						// add s.inoeu 20080901
						// 実施区分がnull値又は空の場合は「実施:1」を設定する。
						buffer2.append("JISI_KBN = ");
						buffer2.append(JQueryConvert.queryConvert(updateData.getJISI_KBN()));
						// add s.inoeu 20080901
						buffer2.append("WHERE ");
						buffer2.append("UKETUKE_ID = ");
						buffer2.append(JQueryConvert.queryConvert(data.getUketukeId()));
						buffer2.append(" AND ");
						buffer2.append("KENSA_NENGAPI = ");
						buffer2.append(JQueryConvert.queryConvert(data.getKensaJissiDate()));
						buffer2.append("AND ");
						buffer2.append("KOUMOKU_CD = ");
						buffer2.append(JQueryConvert.queryConvert(resultItem.get("KOUMOKU_CD")));


						String updateQuery = buffer2.toString();
						/* --------------------------------------------------- */

						JApplication.kikanDatabase.sendNoResultQuery(updateQuery);
					}
				}

				JApplication.kikanDatabase.Commit();

			}catch(SQLException e){
				e.printStackTrace();
				try{
					JApplication.kikanDatabase.rollback();
					return false;
				}catch(SQLException f){
					f.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
}
