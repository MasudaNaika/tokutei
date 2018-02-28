package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei;

import java.util.ArrayList;

import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;

/**
 * 階層化判定処理
 */
public class JKaisoukaHantei {
	private static final String CODE_KITSUEN = "9N736000000000011";
	private static final String CODE_HDL_1 = "3F070000002327101";
	private static final String CODE_CYUSEI_SHIBOU_1 = "3F015000002327101";
	private static final String CODE_KAKUCHOKI_KETSUATSU_1 = "9A761000000000001";
	private static final String CODE_SYUSYUKUKI_KETSUATSU_1 = "9A751000000000001";

	// eidt s.inoue 2012/09/03
	private static final String CODE_HBA1C_JDS = "3D045000001906202";
	private static final String CODE_HBA1C_NGSP = "3D046000001906202";

	private static final String CODE_KUHUKUZI_KETO_1 = "3D010000001926101";
	private static final String CODE_BMI = "9N011000000000001";
	private static final String CODE_HUKUI_ZISOKU = "9N016160100000001";
	private static final String CODE_NAIZOU_SHIBOU_MENSEKI = "9N021000000000001";
	private static final String CODE_HUKUYAKU_3 = "9N711000000000011";
	private static final String CODE_HUKUYAKU_2 = "9N706000000000011";
	private static final String CODE_HUKUYAKU_1 = "9N701000000000011";
	public final static int SEKKYOKUTEKI_SHIEN = 1;
	public final static int DOUKIDUKE_SHIEN = 2;
	public final static int JYOUHOU_TEIKYOU = 3;
	public final static int HANTEI_HUNOU = 4;

	public static int Hantei(JKaisoukaHanteiData data)
	{
		/*
		 * 以下の階層判定はtokuteikenshin.jpのhttp://tokuteikenshin.jp/update/2-1/latest/systemdoc-appe20080205.pdfの
		 * 資料を元にして、判定を行っている。
		 * コメントの多くは、この資料の中の階層判定の項を参照しており、このソースコードに手を加える
		 * 必要がある場合、その資料と見比べながら作業をしていただきたい。
		 * 欠損値項目の処理についても、上記の資料をもとに作成している。
		 */

		// 判定結果
		int Code = 0;

		/* 性別 */
		String gender = data.getGender();
		// eidt s.inoue 2011/04/14
		if (gender != null ) {
			if( gender.equals("男") ){
				gender = "1";
			}else if( gender.equals("女")){
				gender = "2";
			}
		}
		double ketsuatsuHukuyaku = data.getKetsuatsuHukuyaku();
		double kettoHukuyaku = data.getKettoHukuyaku();
		double shishitsuHukuyaku = data.getShishitsuHukuyaku();
		// 服薬のチェック
		// 質問票服薬のいずれかが「服薬あり」であれば、情報提供レベルとして。判定終了
		// これに関しては、一番先にチェックを行う。
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,	kettoHukuyaku) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
		)
		{
			Code = JYOUHOU_TEIKYOU;
			return Code;
		}

		// ステップ1
		// 該当なし = 0
		int Step1 = 0;
		boolean Step1Kesson = false;

		double naizouShibou = data.getNaizouShibou();
		double hukui = data.getHukui();
		// 内臓脂肪面積
		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_NAIZOU_SHIBOU_MENSEKI, gender,naizouShibou) == JKaisoukaHanteiItemCheck.TRUE)
		{
			Step1 = 1;
		}else{
			// 内蔵脂肪面積が無い場合
			if(JKaisoukaHanteiItemCheck.CheckItem(CODE_NAIZOU_SHIBOU_MENSEKI, gender,naizouShibou) == JKaisoukaHanteiItemCheck.KESSON)
			{
				// 腹囲
				if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUI_ZISOKU, gender,hukui) == JKaisoukaHanteiItemCheck.TRUE)
				{
					Step1 = 1;
				}
				else if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUI_ZISOKU, gender,hukui) == JKaisoukaHanteiItemCheck.KESSON){
					Step1Kesson = true;
				}
			}
		}

		double bmi = data.getBMI();
		// パターン1に該当しない場合
		if(Step1 == 0)
		{
			// BMI
			if(JKaisoukaHanteiItemCheck.CheckItem(CODE_BMI, gender,	bmi) == JKaisoukaHanteiItemCheck.TRUE)
			{
				Step1 = 2;
			}
		}

		if(Step1 == 0)
		{
			return 3;
		}

		// ステップ2

		// カテゴリ1
		boolean Category1 = false;
		boolean Category1Kesson = false;

		double kuhukujiKetto = data.getKuhukujiKetto();
		int kuhukuziKeto1Check = JKaisoukaHanteiItemCheck.CheckItem(CODE_KUHUKUZI_KETO_1, gender, kuhukujiKetto);

		// 空腹時血糖
		if(kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.TRUE)
		{
			Category1 = true;
		}else{
			if(kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.KESSON)
			{
				// add s.inoue 2012/09/04
				int tDate = 20130401;
				String sDate = data.getKensinJisiDate();

				// 健診実施日が'130401以降であれば、H,Lの場合もPQ(結果値)を出力する

				String CODE_HBA1C=CODE_HBA1C_JDS;
				if (tDate <= Integer.parseInt(sDate)){
					CODE_HBA1C = CODE_HBA1C_NGSP;
				}

				/* 空腹時血糖が存在しない場合のみ、カテゴリー１を欠損としていたため、修正 */
				// HbA1c
				int hba1cCheck = JKaisoukaHanteiItemCheck.CheckItem(CODE_HBA1C, gender, data.getHbA1c());
				if(hba1cCheck == JKaisoukaHanteiItemCheck.TRUE)
				{
					Category1 = true;
				}

				// 欠損チェック
				// NOの場合は、判定不能となる場合があるので、ここでチェックする。最終的に使用するのは、一番最後の欠損チェック
				if( kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.KESSON
						&& hba1cCheck == JKaisoukaHanteiItemCheck.KESSON )
				{
					Category1Kesson = true;
				}
			}

			if(kuhukuziKeto1Check == JKaisoukaHanteiItemCheck.FALSE)
			{
				Category1 = false;
			}
		}

		// 質問票服薬2（血糖）
		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,
				kettoHukuyaku) == JKaisoukaHanteiItemCheck.TRUE && Category1 == false)
		{
			Category1 = true;
		}else{
			// 欠損チェック
			if(JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,kettoHukuyaku) == JKaisoukaHanteiItemCheck.KESSON && Category1 == false)
			{
				Category1Kesson = true;
			}
		}

		// カテゴリ2
		boolean Category2 = false;
		boolean Category2Kesson = false;
		double shushukuKetsuatsu = data.getShushukuKetsuatsu();
		double kakutyoKetsuatsu = data.getKakutyoKetsuatsu();
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_SYUSYUKUKI_KETSUATSU_1, gender,	shushukuKetsuatsu) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_KAKUCHOKI_KETSUATSU_1, gender,kakutyoKetsuatsu) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
		)
		{
			Category2 = true;
		}else{
			if(
					JKaisoukaHanteiItemCheck.CheckItem(CODE_SYUSYUKUKI_KETSUATSU_1, gender,	shushukuKetsuatsu) == JKaisoukaHanteiItemCheck.KESSON ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_KAKUCHOKI_KETSUATSU_1, gender, kakutyoKetsuatsu) == JKaisoukaHanteiItemCheck.KESSON ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.KESSON
			)
			{
				Category2Kesson = true;
			}
		}

		// カテゴリ3
		boolean Category3 = false;
		boolean Category3Kesson = false;
		double chuseiShibou = data.getChuseiShibou();
		double cholesterol = data.getHDLCholesterol();
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_CYUSEI_SHIBOU_1, gender,chuseiShibou) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HDL_1, gender,cholesterol) == JKaisoukaHanteiItemCheck.TRUE ||
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
		)
		{
			Category3 = true;
		}else{
			if(
					JKaisoukaHanteiItemCheck.CheckItem(CODE_CYUSEI_SHIBOU_1, gender,chuseiShibou) == JKaisoukaHanteiItemCheck.TRUE ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_HDL_1, gender,cholesterol) == JKaisoukaHanteiItemCheck.TRUE ||
					JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.TRUE
			)
			{
				Category3Kesson = true;
			}
		}

		// カテゴリ4
		boolean Category4 = false;
		boolean Category4Kesson = false;
		double kitsuen = data.getKitsuen();

		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.TRUE)
		{
			Category4 = true;
		}
		if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.KESSON)
		{
			Category4Kesson = true;
		}

		// ステップ3

		// コードの判定

		if(Step1 == 0)
		{
			Code = JYOUHOU_TEIKYOU;
		}

		// ステップ2の該当する数を計算
		int Step2Count = 0;

		if(Category1 == true)
		{
			Step2Count++;
		}
		if(Category2 == true)
		{
			Step2Count++;
		}
		if(Category3 == true)
		{
			Step2Count++;
		}

		// 結果の判定

		if(Step1 == 1)
		{
			if(Step2Count == 0)
			{
				Code = JYOUHOU_TEIKYOU;
			}
			if(Step2Count == 1)
			{
				if(Category4 == true)
				{
					Code = SEKKYOKUTEKI_SHIEN;
				}else{
					Code = DOUKIDUKE_SHIEN;
				}

				// 欠損の判定
				if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.KESSON)
				{
					Code = HANTEI_HUNOU;
					return Code;
				}
			}

			if(Step2Count >= 2)
			{
				Code = SEKKYOKUTEKI_SHIEN;
			}
		}

		if(Step1 == 2)
		{
			if(Step2Count == 0)
			{
				Code = JYOUHOU_TEIKYOU;
			}

			if(Step2Count == 1)
			{
				Code = DOUKIDUKE_SHIEN;
			}

			if(Step2Count == 2)
			{
				if(Category4 == true)
				{
					Code = SEKKYOKUTEKI_SHIEN;
				}else{
					Code = DOUKIDUKE_SHIEN;
				}

				// 欠損の判定
				if(JKaisoukaHanteiItemCheck.CheckItem(CODE_KITSUEN, gender,	kitsuen) == JKaisoukaHanteiItemCheck.KESSON)
				{
					Code = HANTEI_HUNOU;
					return Code;
				}
			}

			if(Step2Count >= 3)
			{
				Code = SEKKYOKUTEKI_SHIEN;
			}
		}

		// ステップ4
		if(Code == SEKKYOKUTEKI_SHIEN || Code == DOUKIDUKE_SHIEN)
		{
			// edit ver2 s.inoue 2009/08/19 75歳条件追加
			if((65 <= data.getAge() && data.getAge() <= 74) ||
					data.getTargetAge())
			{
				Code = DOUKIDUKE_SHIEN;
				return Code;
			}

			return Code;
		}

		// 欠損値チェック1

		// 1、内臓脂肪面積、腹囲に関する情報のどちらもまったく存在しない場合には「判定不能」として完了
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_NAIZOU_SHIBOU_MENSEKI, gender,naizouShibou) == JKaisoukaHanteiItemCheck.KESSON &&
				JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUI_ZISOKU, gender,hukui) == JKaisoukaHanteiItemCheck.KESSON
		)
		{
			Code = HANTEI_HUNOU;
			return Code;
		}

		double shintyou = data.getShintyou();
		double taijyu = data.getTaijyu();
		// 2、STEP1でパターン1に該当しない場合で、BMIが欠損しており、身長、体重のどちらかが存在しない場合
		if(
				JKaisoukaHanteiItemCheck.CheckItem(CODE_BMI, gender,bmi) == JKaisoukaHanteiItemCheck.KESSON && (shintyou == -1 || taijyu == -1)
		)
		{
			Code = HANTEI_HUNOU;
			return Code;
		}

		// 3、判定に使った検査、および質問票の服薬関係の3問、喫煙において欠損値がない場合
		if(JKaisoukaHanteiItemCheck.IsKesson() == false)
		{
			return Code;
		}

		// 4、STEP2のカテゴリー4が欠損値項目であるために特定ができない状況が発生する場合
		// カテゴリー4を使って判定を行う部分で、欠損のチェックを行っている。

		// 服薬の欠損
		boolean HukuyakuKesson = false;

		if(
			JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_2, gender,	kettoHukuyaku) == JKaisoukaHanteiItemCheck.KESSON ||
			JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_1, gender,	ketsuatsuHukuyaku) == JKaisoukaHanteiItemCheck.KESSON ||
			JKaisoukaHanteiItemCheck.CheckItem(CODE_HUKUYAKU_3, gender,	shishitsuHukuyaku) == JKaisoukaHanteiItemCheck.KESSON
		)
		{
			HukuyakuKesson = true;
		}

		// 判定結果が「情報提供レベル」
		if(Code == JYOUHOU_TEIKYOU)
		{
			// 5、欠損値が服薬に関するものだけであって、服薬以外の項目には欠損値がない場合
			if(
					HukuyakuKesson == true &&
					(Step1Kesson == false && Category1Kesson == false && Category2Kesson == false &&
					Category3Kesson == false && Category4Kesson == false)
			)
			{
				return Code;
			}

			// 6、服薬以外の項目に欠損値がある場合
			if(
					(Step1Kesson == true || Category1Kesson == true || Category2Kesson == true ||
					Category3Kesson == true || Category4Kesson == true)
			)
			{
				Code = HANTEI_HUNOU;
				return Code;
			}
		}

		// 7、判定結果が「情報提供レベル」以外であって、かつ、服薬が一つでも欠損している場合
		if(Code != JYOUHOU_TEIKYOU && HukuyakuKesson == true)
		{
			Code = HANTEI_HUNOU;
			return Code;
		}

		// 9、65～74歳であって、動機づけ支援レベルであれば、欠損値項目の有無にかかわらず完了
		// edit ver2 s.inoue 2009/08/19 75歳条件含む
		// if(65 <= data.getAge() && data.getAge() <= 74 && ( Code == DOUKIDUKE_SHIEN || Code == SEKKYOKUTEKI_SHIEN ) )
		// {
		if( ((65 <= data.getAge() && data.getAge() <= 74) || data.getTargetAge())
				 && ( Code == DOUKIDUKE_SHIEN || Code == SEKKYOKUTEKI_SHIEN ))
		{
			Code = DOUKIDUKE_SHIEN;
			return Code;
		}

		// 8、積極的支援レベルであった場合、欠損値項目の有無にかかわらず完了
		if(Code == SEKKYOKUTEKI_SHIEN)
		{
			return Code;
		}

		// 欠損チェック2
		// 欠損値項目が1個以上あり、結果が「非該当」または「動機づけ支援レベル」であればチェック
		if(JKaisoukaHanteiItemCheck.IsKesson() == true && (Code == JYOUHOU_TEIKYOU || Code == DOUKIDUKE_SHIEN))
		{
			if(Step1 == 1)
			{
				// 欠損値項目の属するカテゴリーのひとつでもSTEP2でNOである場合
				if(
						(Category1 == false && Category1Kesson == true) ||
						(Category2 == false && Category2Kesson == true) ||
						(Category3 == false && Category3Kesson == true)
				)
				{
					Code = HANTEI_HUNOU;
					return Code;
				}

				// それ以外は変更なし
				return Code;
			}

			if(Step1 == 2)
			{
				if(Step2Count == 0)
				{
					return Code;
				}

				if(Step2Count == 1)
				{
					// Step2でNoとなっている2カテゴリの両方に欠損値がある
					if(
							(Category1 == true && Category2Kesson == true && Category3Kesson == true) ||
							(Category2 == true && Category1Kesson == true && Category3Kesson == true) ||
							(Category3 == true && Category2Kesson == true && Category1Kesson == true)
					)
					{
						Code = HANTEI_HUNOU;
						return Code;
					}

					// Step2でNoとなっている2カテゴリのうち1カテゴリだけに欠損値がある
					if(
							(Category1 == true && (Category2Kesson == true || Category3Kesson == true)) ||
							(Category2 == true && (Category1Kesson == true || Category3Kesson == true)) ||
							(Category3 == true && (Category2Kesson == true || Category1Kesson == true))
					)
					{
						if(Category4 == false)
						{
							return Code;
						}else{
							Code = HANTEI_HUNOU;
							return Code;
						}
					}

					return Code;
				}

				if(Step2Count == 2)
				{
					if(
							(Category1 == false && Category1Kesson == true) ||
							(Category2 == false && Category2Kesson == true) ||
							(Category3 == false && Category3Kesson == true)
					)
					{
						Code = HANTEI_HUNOU;
						return Code;
					}

					return Code;
				}
			}

		}

		return Code;
	}
}
