package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.math.BigDecimal;

/**
 * 決済情報を計算する
 */
public class JKessai {
	/**
	 * 決済情報の処理
	 * @param input 決済データの入力
	 * @return
	 */
	public static JKessaiDataOutput Kessai(JKessaiDataInput input,
			JKessaiProcessData processData )
	{
		JKessaiDataOutput outputData = new JKessaiDataOutput();

		if(input.Check() == false)
		{
			throw new RuntimeException();
		}

		long tsuikaTanka = input.getTsuikaTanka();
		outputData.setTsuikaTankaGoukei(tsuikaTanka);

		long kihonTanka = input.getKihonTanka();
		long syousaiTanka = input.getSyousaiTanka();
		long tankaGoukei = 	kihonTanka + syousaiTanka +	tsuikaTanka;
		long docTanka = input.getDockTanka();
		// add ver2 s.inoue 2009/07/09
		outputData.setDocTankaGoukei(docTanka);

		/* その他の健診による負担金額 */
		long madoFutanSonota = input.getMadoFutanSonota();
		long sonota = madoFutanSonota;
		long[] madoFutanKihon = getModifiedMadoFutan(
				kihonTanka,
				input.getKihonMadoFutan(),
				input.getKihonMadoFutanSyubetu(),
				input.getKihonHokenjyaJyougen(),
				sonota
			);

		if (madoFutanKihon[0] >= 0) {
			sonota = 0;
		}
		else {
			sonota = - madoFutanKihon[0];
			madoFutanKihon[0] = 0;
		}

		long[] madoFutanSyousai = {0, 0};
		if (input.isExistsSyousai()) {
			madoFutanSyousai = getModifiedMadoFutan(
					syousaiTanka,
					input.getSyousaiMadoFutan(),
					input.getSyousaiMadoFutanSyubetu(),
					input.getSyousaiHokenjyaJyougen(),
					sonota
				);

			if (madoFutanSyousai[0] >= 0) {
				sonota = 0;
			}
			else {
				sonota = - madoFutanSyousai[0];
				madoFutanSyousai[0] = 0;
			}
		}

		long madoFutanTsuika[] = {0, 0};
		if (input.isExistsTsuika()) {
			madoFutanTsuika = getModifiedMadoFutan(
					tsuikaTanka,
					input.getTsuikaMadoFutan(),
					input.getTsuikaMadoFutanSyubetu(),
					input.getTsuikaHokenjyaJyougen(),
					sonota
				);

			if (madoFutanTsuika[0] < 0) {
				madoFutanTsuika[0] = 0;
			}
		}

		long madoFutanGoukei = madoFutanKihon[0] + madoFutanSyousai[0] + madoFutanTsuika[0];
		long seikyuKingaku = tankaGoukei - madoFutanGoukei - madoFutanSonota;
		if (seikyuKingaku <= 0) {
			seikyuKingaku = 0;

			madoFutanGoukei = tankaGoukei - madoFutanSonota;
			if (madoFutanGoukei < 0) {
				madoFutanGoukei = 0;
			}
		}

		// add ver2 s.inoue 2009/07/09
		/* 人間ドック計算 */
		long madoFutanDoc[] = {0, 0};
		if (input.isExistsDoc()) {
			madoFutanDoc = getModifiedMadoFutanNingenDoc(
					docTanka,
					input.getDocMadoFutan(),
					input.getDockMadoFutanSyubetu(),
					input.getDockHokenjyaJyougen(),
					sonota
				);

			if (madoFutanDoc[0] < 0) {
				madoFutanDoc[0] = 0;
			}
		}

		long madoFutanDocGoukei = madoFutanDoc[0];
		long seikyuKingakuDoc = docTanka - madoFutanDocGoukei - madoFutanSonota;
		if (seikyuKingakuDoc <= 0) {
			seikyuKingakuDoc = 0;

			madoFutanDocGoukei = docTanka - madoFutanSonota;
			if (madoFutanDocGoukei < 0) {
				madoFutanDocGoukei = 0;
			}
		}

		outputData.setMadoFutanDoc(madoFutanDoc[0]);

		outputData.setMadoFutanSonota(madoFutanSonota);
		outputData.setTankaGoukei(tankaGoukei);
		outputData.setMadoFutanKihon(madoFutanKihon[0]);
		outputData.setMadoFutanSyousai(madoFutanSyousai[0]);
		outputData.setMadoFutanTsuika(madoFutanTsuika[0]);

		// add ver2 s.inoue 2009/07/09
		if (input.isExistsDoc()){
			// edit ver2 s.inoue 2009/07/21
			outputData.setMadoFutanGoukei(madoFutanDocGoukei);
			outputData.setSeikyuKingaku(seikyuKingakuDoc);
		}else{
			// edit ver2 s.inoue 2009/07/21
			outputData.setMadoFutanGoukei(madoFutanGoukei);
			outputData.setSeikyuKingaku(seikyuKingaku);
		}

		processData.outputFutanSyubetsuKihon = (int)madoFutanKihon[1];
		processData.outputFutanSyubetsuSyousai = (int)madoFutanSyousai[1];
		processData.outputFutanSyubetsuTsuika = (int)madoFutanTsuika[1];

		processData.outputFutanSyubetsuDoc = (int)madoFutanDoc[1];

		return outputData;
	}

	/**
	 * 負担額を算出する
	 * @param tankaParam 単価
	 * @param inputHutanParam 負担の値
	 * @param inputCodeParam 負担のコード
	 * @return コードから判別した負担額
	 */
	public static long[] getModifiedMadoFutan(
			long tanka,
			long inputHutan,
			int inputCode,
			long jyougen,
			long sonota
		){

		long tankaParam = tanka < 0 ? 0 : tanka;
		long inputHutanParam = inputHutan < 0 ? 0 : inputHutan;
		int inputCodeParam = inputCode < 1 ? 1 : inputCode;
		long jyougenParam = jyougen < 0 ? 0 : jyougen;
		long sonotaParam = sonota < 0 ? 0 : sonota;

		/* 負担額 */
		long hutan = 0;
		long syubetu = inputCodeParam;

		/* 負担なし（＋保険者負担上限額） */
		if(inputCodeParam == 0 || inputCodeParam == 1 ){
			/* 1.負担なし */
			if(jyougenParam == 0){
				/* 窓口負担金額 = 0 */
				hutan = 0;
			}
			/* 4.保険者負担上限額 */
			else {
				syubetu = 4;

				/* A 単価＞その他 */
				if (tankaParam > sonotaParam) {
					/* a 単価－その他＞保険者負担上限額のとき */
					if ((tankaParam - sonotaParam) > jyougenParam) {
						/* 窓口負担金額 = 単価 - 保険者負担上限額 */
						hutan = ( tankaParam - sonotaParam ) - jyougenParam;
					}
					/* b 単価－その他＜＝保険者負担上限額のとき */
					else {
						/* 窓口負担金額＝０ */
						hutan = 0;
					}
				}
				/* B 単価＜＝その他 */
				else {
					/* 窓口負担金額＝０ */
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* 定額負担（＋保険者負担上限額） */
		else if(inputCodeParam == 2){
			/* 保険者負担上限額が 0 -> 定額負担のみ */
			/* 2.定額負担 */

			if (jyougenParam == 0){

				/* A 単価＞その他 */
				if (tankaParam > sonotaParam) {
					/* a 単価 - その他＞定額負担金額 */
					if ((tankaParam - sonotaParam) > inputHutanParam) {
						hutan = inputHutanParam;
					}
					/* b 単価 - その他＜＝定額負担金額 */
					else {
						hutan = tankaParam - sonotaParam;
					}
				}
				/* B 単価＜＝その他 */
				else {
					/* 窓口負担金額＝０ */
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 5.定額負担＋保険者負担上限額 */
			else {

				syubetu = 2;

				/* A 単価＞その他 */
				if (tankaParam > sonotaParam) {
					/* a 単価－その他＞定額負担金額のとき */
					if ((tankaParam - sonotaParam) > inputHutanParam) {
						/* ア）単価－その他－定額負担金額＞保険者負担上限額のとき */
						if (tankaParam - sonotaParam - inputHutanParam > jyougenParam) {
							hutan = tankaParam - sonotaParam - jyougenParam;
							syubetu = 4;
						}
						/* イ）単価－その他－定額負担金額＜＝保険者負担上限額のとき */
						else {
							hutan = inputHutanParam;
						}
					}
					/* b 単価－その他＜＝定額負担金額のとき */
					else {
						hutan = tankaParam - sonotaParam;
					}
				}
				/* B 単価＜＝その他 */
				else {
					/* 窓口負担金額＝０ */
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* 定率負担（+ 保険者負担上限額） */
		else if(inputCodeParam == 3){

			/* 負担率を金額に変換する */
			double dHutan = (double)inputHutanParam;
			double wariai = dHutan / 100000.0;
			double dTanka = (double)tankaParam;
			double madoHutanDouble = (tankaParam - sonotaParam) * wariai;
			BigDecimal bd = new BigDecimal(String.valueOf(madoHutanDouble));
			long hutanGaku = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();


			/* 保険者負担上限額が 0 -> 定率負担のみ */
			/* 3.定率負担 */
			if (jyougenParam == 0){
				/* 窓口負担金額＝単価Ｘ定率 */
				/* A 単価＞その他 */
				if (tankaParam > sonotaParam) {
					/* 窓口負担金額＝｛単価－他の健診による負担金額｝Ｘ定率 */
					hutan = hutanGaku;
				}
				/* B 単価＜＝その他 */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 6.定率負担＋保険者負担上限額 */
			else {
				syubetu = 3;

				/* A 単価＞その他 */
				if (tankaParam > sonotaParam) {
					/* a 単価－その他－（単価Ｘ定率）＞保険者負担上限額のとき */
					if ((tankaParam - sonotaParam) - hutanGaku > jyougenParam) {
						hutan = tankaParam - sonotaParam - jyougenParam;
						syubetu = 4;
					}
					/* b 単価－その他－（単価Ｘ定率）＜＝保険者負担上限額のとき */
					else {
						hutan = hutanGaku;
					}
				}
				/* B 単価＜＝その他 */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
		}

		long[] ret = { 0, 0 };

		ret[0] = hutan;
		ret[1] = syubetu;

		return ret;
	}

	// add ver2 s.inoue 2009/07/08
	/**
	 * 負担額（人間ドック）を算出する
	 * @param tankaParam 単価
	 * @param inputHutanParam 負担の値
	 * @param inputCodeParam 負担のコード
	 * @return コードから判別した負担額
	 */
	public static long[] getModifiedMadoFutanNingenDoc(
			long tankaDoc,
			long inputHutanDoc,
			int inputCodeDoc,
			long jyougenDoc,
			long sonota
		){

		long tankaParam = tankaDoc < 0 ? 0 : tankaDoc;
		long inputHutanParam = inputHutanDoc < 0 ? 0 : inputHutanDoc;
		int inputCodeParam = inputCodeDoc < 1 ? 1 : inputCodeDoc;
		long jyougenParam = jyougenDoc < 0 ? 0 : jyougenDoc;
		long sonotaParam = sonota < 0 ? 0 : sonota;

		/* 負担額 */
		long hutan = 0;
		long syubetu = inputCodeParam;

		/* 負担なし（＋保険者負担上限額） */
		if(inputCodeParam == 0 || inputCodeParam == 1 ){
			/* 1.負担なし */
			if(jyougenParam == 0){
				/* 窓口負担金額 = 0 */
				hutan = 0;
			}
			/* 4.保険者負担上限額 */
			else {
				syubetu = 4;

				/* 人間ドックのとき */
				/* a 単価－その他＞保険者負担上限額のとき */
				if ((tankaParam - sonotaParam) > jyougenParam) {
					/* 窓口負担金額 = 単価 - 保険者負担上限額 */
					hutan = ( tankaParam - sonotaParam ) - jyougenParam;
				}
				/* b 単価－その他＜＝保険者負担上限額のとき */
				else {
					/* 窓口負担金額＝０ */
					hutan = 0;
				}
			}
		}
		/* 定額負担（＋保険者負担上限額） */
		else if(inputCodeParam == 2){
			/* 保険者負担上限額が 0 -> 定額負担のみ */
			/* 2.定額負担 */

			if (jyougenParam == 0){
				/* a 単価 - その他＞定額負担金額 */
				if ((tankaParam - sonotaParam) > inputHutanParam) {
					hutan = inputHutanParam;
				}
				/* b 単価 - その他＜＝定額負担金額 */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 5.定額負担＋保険者負担上限額 */
			else {

				syubetu = 2;

				/* a 単価－その他＞定額負担金額のとき */
				if ((tankaParam - sonotaParam) > inputHutanParam) {
					/* ア）単価－その他－定額負担金額＞保険者負担上限額のとき */
					if (tankaParam - sonotaParam - inputHutanParam > jyougenParam) {
						hutan = tankaParam - sonotaParam - jyougenParam;
						syubetu = 4;
					}
					/* イ）単価－その他－定額負担金額＜＝保険者負担上限額のとき */
					else {
						hutan = inputHutanParam;
					}
				}
				/* b 単価－その他＜＝定額負担金額のとき */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* 定率負担（+ 保険者負担上限額） */
		else if(inputCodeParam == 3){

			/* 負担率を金額に変換する */
			double dHutan = (double)inputHutanParam;
			double wariai = dHutan / 100000.0;
			double dTanka = (double)tankaParam;
			double madoHutanDouble = (tankaParam - sonotaParam) * wariai;
			BigDecimal bd = new BigDecimal(String.valueOf(madoHutanDouble));
			long hutanGaku = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();


			/* 保険者負担上限額が 0 -> 定率負担のみ */
			/* 3.定率負担 */
			if (jyougenParam == 0){
				/* 窓口負担金額＝｛単価－他の健診による負担金額｝Ｘ定率 */
				hutan = hutanGaku;
			}
			/* 6.定率負担＋保険者負担上限額 */
			else {
				syubetu = 3;

				/* a 単価－その他－（単価Ｘ定率）＞保険者負担上限額のとき */
				if ((tankaParam - sonotaParam) - hutanGaku > jyougenParam) {
					hutan = tankaParam - sonotaParam - jyougenParam;
					syubetu = 4;
				}
				/* b 単価－その他－（単価Ｘ定率）＜＝保険者負担上限額のとき */
				else {
					hutan = hutanGaku;
				}
			}
		}

		long[] ret = { 0, 0 };

		ret[0] = hutan;
		ret[1] = syubetu;

		return ret;
	}


}
