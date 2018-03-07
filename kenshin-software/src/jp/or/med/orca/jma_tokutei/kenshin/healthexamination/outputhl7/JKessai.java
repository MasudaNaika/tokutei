package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

import java.math.BigDecimal;

/**
 * ŒˆÏî•ñ‚ğŒvZ‚·‚é
 */
public class JKessai {
	/**
	 * ŒˆÏî•ñ‚Ìˆ—
	 * @param input ŒˆÏƒf[ƒ^‚Ì“ü—Í
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

		/* ‚»‚Ì‘¼‚ÌŒ’f‚É‚æ‚é•‰’S‹àŠz */
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
		/* lŠÔƒhƒbƒNŒvZ */
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
	 * •‰’SŠz‚ğZo‚·‚é
	 * @param tankaParam ’P‰¿
	 * @param inputHutanParam •‰’S‚Ì’l
	 * @param inputCodeParam •‰’S‚ÌƒR[ƒh
	 * @return ƒR[ƒh‚©‚ç”»•Ê‚µ‚½•‰’SŠz
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

		/* •‰’SŠz */
		long hutan = 0;
		long syubetu = inputCodeParam;

		/* •‰’S‚È‚µi{•ÛŒ¯Ò•‰’SãŒÀŠzj */
		if(inputCodeParam == 0 || inputCodeParam == 1 ){
			/* 1.•‰’S‚È‚µ */
			if(jyougenParam == 0){
				/* ‘‹Œû•‰’S‹àŠz = 0 */
				hutan = 0;
			}
			/* 4.•ÛŒ¯Ò•‰’SãŒÀŠz */
			else {
				syubetu = 4;

				/* A ’P‰¿„‚»‚Ì‘¼ */
				if (tankaParam > sonotaParam) {
					/* a ’P‰¿|‚»‚Ì‘¼„•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
					if ((tankaParam - sonotaParam) > jyougenParam) {
						/* ‘‹Œû•‰’S‹àŠz = ’P‰¿ - •ÛŒ¯Ò•‰’SãŒÀŠz */
						hutan = ( tankaParam - sonotaParam ) - jyougenParam;
					}
					/* b ’P‰¿|‚»‚Ì‘¼ƒ•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
					else {
						/* ‘‹Œû•‰’S‹àŠz‚O */
						hutan = 0;
					}
				}
				/* B ’P‰¿ƒ‚»‚Ì‘¼ */
				else {
					/* ‘‹Œû•‰’S‹àŠz‚O */
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* ’èŠz•‰’Si{•ÛŒ¯Ò•‰’SãŒÀŠzj */
		else if(inputCodeParam == 2){
			/* •ÛŒ¯Ò•‰’SãŒÀŠz‚ª 0 -> ’èŠz•‰’S‚Ì‚İ */
			/* 2.’èŠz•‰’S */

			if (jyougenParam == 0){

				/* A ’P‰¿„‚»‚Ì‘¼ */
				if (tankaParam > sonotaParam) {
					/* a ’P‰¿ - ‚»‚Ì‘¼„’èŠz•‰’S‹àŠz */
					if ((tankaParam - sonotaParam) > inputHutanParam) {
						hutan = inputHutanParam;
					}
					/* b ’P‰¿ - ‚»‚Ì‘¼ƒ’èŠz•‰’S‹àŠz */
					else {
						hutan = tankaParam - sonotaParam;
					}
				}
				/* B ’P‰¿ƒ‚»‚Ì‘¼ */
				else {
					/* ‘‹Œû•‰’S‹àŠz‚O */
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 5.’èŠz•‰’S{•ÛŒ¯Ò•‰’SãŒÀŠz */
			else {

				syubetu = 2;

				/* A ’P‰¿„‚»‚Ì‘¼ */
				if (tankaParam > sonotaParam) {
					/* a ’P‰¿|‚»‚Ì‘¼„’èŠz•‰’S‹àŠz‚Ì‚Æ‚« */
					if ((tankaParam - sonotaParam) > inputHutanParam) {
						/* ƒAj’P‰¿|‚»‚Ì‘¼|’èŠz•‰’S‹àŠz„•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
						if (tankaParam - sonotaParam - inputHutanParam > jyougenParam) {
							hutan = tankaParam - sonotaParam - jyougenParam;
							syubetu = 4;
						}
						/* ƒCj’P‰¿|‚»‚Ì‘¼|’èŠz•‰’S‹àŠzƒ•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
						else {
							hutan = inputHutanParam;
						}
					}
					/* b ’P‰¿|‚»‚Ì‘¼ƒ’èŠz•‰’S‹àŠz‚Ì‚Æ‚« */
					else {
						hutan = tankaParam - sonotaParam;
					}
				}
				/* B ’P‰¿ƒ‚»‚Ì‘¼ */
				else {
					/* ‘‹Œû•‰’S‹àŠz‚O */
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* ’è—¦•‰’Si+ •ÛŒ¯Ò•‰’SãŒÀŠzj */
		else if(inputCodeParam == 3){

			/* •‰’S—¦‚ğ‹àŠz‚É•ÏŠ·‚·‚é */
			double dHutan = (double)inputHutanParam;
			double wariai = dHutan / 100000.0;
			double dTanka = (double)tankaParam;
			double madoHutanDouble = (tankaParam - sonotaParam) * wariai;
			BigDecimal bd = new BigDecimal(String.valueOf(madoHutanDouble));
			long hutanGaku = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();


			/* •ÛŒ¯Ò•‰’SãŒÀŠz‚ª 0 -> ’è—¦•‰’S‚Ì‚İ */
			/* 3.’è—¦•‰’S */
			if (jyougenParam == 0){
				/* ‘‹Œû•‰’S‹àŠz’P‰¿‚w’è—¦ */
				/* A ’P‰¿„‚»‚Ì‘¼ */
				if (tankaParam > sonotaParam) {
					/* ‘‹Œû•‰’S‹àŠzo’P‰¿|‘¼‚ÌŒ’f‚É‚æ‚é•‰’S‹àŠzp‚w’è—¦ */
					hutan = hutanGaku;
				}
				/* B ’P‰¿ƒ‚»‚Ì‘¼ */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 6.’è—¦•‰’S{•ÛŒ¯Ò•‰’SãŒÀŠz */
			else {
				syubetu = 3;

				/* A ’P‰¿„‚»‚Ì‘¼ */
				if (tankaParam > sonotaParam) {
					/* a ’P‰¿|‚»‚Ì‘¼|i’P‰¿‚w’è—¦j„•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
					if ((tankaParam - sonotaParam) - hutanGaku > jyougenParam) {
						hutan = tankaParam - sonotaParam - jyougenParam;
						syubetu = 4;
					}
					/* b ’P‰¿|‚»‚Ì‘¼|i’P‰¿‚w’è—¦jƒ•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
					else {
						hutan = hutanGaku;
					}
				}
				/* B ’P‰¿ƒ‚»‚Ì‘¼ */
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
	 * •‰’SŠzilŠÔƒhƒbƒNj‚ğZo‚·‚é
	 * @param tankaParam ’P‰¿
	 * @param inputHutanParam •‰’S‚Ì’l
	 * @param inputCodeParam •‰’S‚ÌƒR[ƒh
	 * @return ƒR[ƒh‚©‚ç”»•Ê‚µ‚½•‰’SŠz
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

		/* •‰’SŠz */
		long hutan = 0;
		long syubetu = inputCodeParam;

		/* •‰’S‚È‚µi{•ÛŒ¯Ò•‰’SãŒÀŠzj */
		if(inputCodeParam == 0 || inputCodeParam == 1 ){
			/* 1.•‰’S‚È‚µ */
			if(jyougenParam == 0){
				/* ‘‹Œû•‰’S‹àŠz = 0 */
				hutan = 0;
			}
			/* 4.•ÛŒ¯Ò•‰’SãŒÀŠz */
			else {
				syubetu = 4;

				/* lŠÔƒhƒbƒN‚Ì‚Æ‚« */
				/* a ’P‰¿|‚»‚Ì‘¼„•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
				if ((tankaParam - sonotaParam) > jyougenParam) {
					/* ‘‹Œû•‰’S‹àŠz = ’P‰¿ - •ÛŒ¯Ò•‰’SãŒÀŠz */
					hutan = ( tankaParam - sonotaParam ) - jyougenParam;
				}
				/* b ’P‰¿|‚»‚Ì‘¼ƒ•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
				else {
					/* ‘‹Œû•‰’S‹àŠz‚O */
					hutan = 0;
				}
			}
		}
		/* ’èŠz•‰’Si{•ÛŒ¯Ò•‰’SãŒÀŠzj */
		else if(inputCodeParam == 2){
			/* •ÛŒ¯Ò•‰’SãŒÀŠz‚ª 0 -> ’èŠz•‰’S‚Ì‚İ */
			/* 2.’èŠz•‰’S */

			if (jyougenParam == 0){
				/* a ’P‰¿ - ‚»‚Ì‘¼„’èŠz•‰’S‹àŠz */
				if ((tankaParam - sonotaParam) > inputHutanParam) {
					hutan = inputHutanParam;
				}
				/* b ’P‰¿ - ‚»‚Ì‘¼ƒ’èŠz•‰’S‹àŠz */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
			/* 5.’èŠz•‰’S{•ÛŒ¯Ò•‰’SãŒÀŠz */
			else {

				syubetu = 2;

				/* a ’P‰¿|‚»‚Ì‘¼„’èŠz•‰’S‹àŠz‚Ì‚Æ‚« */
				if ((tankaParam - sonotaParam) > inputHutanParam) {
					/* ƒAj’P‰¿|‚»‚Ì‘¼|’èŠz•‰’S‹àŠz„•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
					if (tankaParam - sonotaParam - inputHutanParam > jyougenParam) {
						hutan = tankaParam - sonotaParam - jyougenParam;
						syubetu = 4;
					}
					/* ƒCj’P‰¿|‚»‚Ì‘¼|’èŠz•‰’S‹àŠzƒ•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
					else {
						hutan = inputHutanParam;
					}
				}
				/* b ’P‰¿|‚»‚Ì‘¼ƒ’èŠz•‰’S‹àŠz‚Ì‚Æ‚« */
				else {
					hutan = tankaParam - sonotaParam;
				}
			}
		}
		/* ’è—¦•‰’Si+ •ÛŒ¯Ò•‰’SãŒÀŠzj */
		else if(inputCodeParam == 3){

			/* •‰’S—¦‚ğ‹àŠz‚É•ÏŠ·‚·‚é */
			double dHutan = (double)inputHutanParam;
			double wariai = dHutan / 100000.0;
			double dTanka = (double)tankaParam;
			double madoHutanDouble = (tankaParam - sonotaParam) * wariai;
			BigDecimal bd = new BigDecimal(String.valueOf(madoHutanDouble));
			long hutanGaku = bd.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();


			/* •ÛŒ¯Ò•‰’SãŒÀŠz‚ª 0 -> ’è—¦•‰’S‚Ì‚İ */
			/* 3.’è—¦•‰’S */
			if (jyougenParam == 0){
				/* ‘‹Œû•‰’S‹àŠzo’P‰¿|‘¼‚ÌŒ’f‚É‚æ‚é•‰’S‹àŠzp‚w’è—¦ */
				hutan = hutanGaku;
			}
			/* 6.’è—¦•‰’S{•ÛŒ¯Ò•‰’SãŒÀŠz */
			else {
				syubetu = 3;

				/* a ’P‰¿|‚»‚Ì‘¼|i’P‰¿‚w’è—¦j„•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
				if ((tankaParam - sonotaParam) - hutanGaku > jyougenParam) {
					hutan = tankaParam - sonotaParam - jyougenParam;
					syubetu = 4;
				}
				/* b ’P‰¿|‚»‚Ì‘¼|i’P‰¿‚w’è—¦jƒ•ÛŒ¯Ò•‰’SãŒÀŠz‚Ì‚Æ‚« */
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
