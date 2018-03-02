package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.util.ArrayList;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JImportDataFrameData {
	String importFormat = new String("");	//1:厚労省準拠、2:独自
	String kensaCenterNumber = new String("");
	String kensaJissidate = new String("");
	String filePath = new String("");
	public boolean isValidateAsDataSet = false;

	// edit s.inoue 2010/02/10
	/* 質問票 */
	protected static final String CODE_FUKUYAKU_1 ="9N701000000000011";		// 服薬1(血圧)
	// protected static final String CODE_FUKUYAKU_1 ="9N701167000000049";	// 服薬1(血圧)(薬剤名)
	// protected static final String CODE_FUKUYAKU_1 ="9N701167100000049";	// 服薬1(血圧)(実施理由)
	protected static final String CODE_FUKUYAKU_2 ="9N706000000000011";		// 服薬2(血糖)
	// protected static final String CODE_FUKUYAKU_2 ="9N706167000000049";	// 服薬2(血糖)(薬剤名)
	// protected static final String CODE_FUKUYAKU_2 ="9N706167100000049";	// 服薬2(血糖)(実施理由)
	protected static final String CODE_FUKUYAKU_3 ="9N711000000000011";		// 服薬3(脂質)
	// protected static final String CODE_FUKUYAKU_3 ="9N711167000000049";	// 服薬3(脂質)(薬剤名)
	// protected static final String CODE_FUKUYAKU_3 ="9N711167100000049";	// 服薬3(脂質)(実施理由)
	protected static final String CODE_KIOUREKI_1 ="9N716000000000011";		// 既往歴1(脳血管)
	protected static final String CODE_KIOUREKI_2 ="9N721000000000011";		// 既往歴2(心血管)
	protected static final String CODE_KIOUREKI_3 ="9N726000000000011";		// 既往歴3(腎不全･人工透析)
	protected static final String CODE_HINKETU	 ="9N731000000000011";		// 貧血
	protected static final String CODE_KITUEN 	 ="9N736000000000011";		// 喫煙
	protected static final String CODE_INSHU	 ="9N786000000000011";		// 飲酒
	// protected static final String CODE_INSHU_RYO ="9N791000000000011";	// 飲酒量
	protected static final String CODE_HOKENSIDO ="9N806000000000011";		// 保健指導の希望

	/* 身体計測 */
	protected static final String CODE_SINCHO ="9N001000000000001";			// 身長
	protected static final String CODE_TAIJYU ="9N006000000000001";			// 体重
	protected static final String CODE_BMI ="9N011000000000001";			// BMI※要計算
	// protected static final String CODE_NAIZO ="9N021000000000001";		// 内臓脂肪面積
	protected static final String CODE_FUKUI_JISOKU ="9N016160100000001";	// 腹囲(実測）
	protected static final String CODE_FUKUI_HANTEI ="9N016160200000001";	// 腹囲(自己判定）
	protected static final String CODE_HIGHT_SINKOKU ="9N016160300000001";	// 腹囲(自己申告）
	// protected static final String CODE_HIMANDO ="9N026000000000002";		// 肥満度
	// protected static final String CODE_GYOMUREKI ="9N051000000000049";	// 業務歴

	/* 診察 */
	protected static final String CODE_KIOUREKI ="9N056000000000011";		// 既往歴
	protected static final String CODE_KIOUREKI_GUTAI ="9N056160400000049";	// 具体的な既往歴（３項目を結合）
	protected static final String CODE_JIKAKU ="9N061000000000011";			// 自覚症状
	protected static final String CODE_JIKAKU_SYOKEN ="9N061160800000049";	// 自覚症状所見（３項目を結合）
	protected static final String CODE_TAKAKU ="9N066000000000011";			// 他覚症状
	protected static final String CODE_TAKAKU_SYOKEN ="9N066160800000049";	// 他覚所見（３項目を結合）

	// protected static final String CODE_HIGHT ="9N071000000000049";		// その他(家族歴等)
	// protected static final String CODE_HIGHT ="9N076000000000049";		// 視診(口腔内含む)
	// protected static final String CODE_HIGHT ="9N081000000000049";		// 打聴診
	// protected static final String CODE_HIGHT ="9N086000000000049";		// 触診(関節可動域含む)
	// protected static final String CODE_HIGHT ="9N091000000000001";		// 反復唾液嚥下テスト

	/* 血圧 */
	protected static final String CODE_SYUSYUKU_SONOTA ="9A755000000000001";	// 収縮期血圧(その他)
	protected static final String CODE_SYUSYUKU_NIKAI ="9A752000000000001";		// 収縮期血圧(2回目)
	protected static final String CODE_SYUSYUKU_IKAI ="9A751000000000001";		// 収縮期血圧(1回目)
	protected static final String CODE_KAKUCYO_SONOTA ="9A765000000000001";		// 拡張期血圧(その他)
	protected static final String CODE_KAKUCYO_NIKAI ="9A762000000000001";		// 拡張期血圧(2回目)
	protected static final String CODE_KAKUCYO_IKAI ="9A761000000000001";		// 拡張期血圧(1回目)

	/* 尿 */
	protected static final String CODE_NYOTOU ="1A020000000191111";		// 尿糖※一番先頭を使用
	// protected static final String CODE_HIGHT ="1A020000000190111";	// 尿糖
	protected static final String CODE_NYOTANPAKU ="1A010000000191111";	// 尿蛋白
	// protected static final String CODE_HIGHT ="1A010000000190111";	// 尿蛋白

	/* 血糖 */
	protected static final String CODE_KUFUKUJIKETOU ="3D010000001926101";	// 空腹時血糖※一番先頭を使用
	// protected static final String CODE_HIGHT ="3D010000002227101";		// 空腹時血糖
	// protected static final String CODE_HIGHT ="3D010000001927201";		// 空腹時血糖
	// protected static final String CODE_HIGHT ="3D010000001999901";		// 空腹時血糖
	protected static final String CODE_SAIKETU ="9N141000000000011";		// 採血時間(食後)
	protected static final String CODE_HBA1C ="3D045000001906202";			// HbA1c※一番先頭を使用
	// protected static final String CODE_HIGHT ="3D045000001920402";	// HbA1c
	// protected static final String CODE_HIGHT ="3D045000001927102";	// HbA1c
	// protected static final String CODE_HIGHT ="3D045000001999902";	// HbA1c

	/* 血中糖質 */
	protected static final String CODE_CYUSEISIBOU ="3F015000002327101";	// 中性脂肪（トリグリセリド）※一番先頭を使用
	// protected static final String CODE_HIGHT ="3F015000002327201";		// 中性脂肪（トリグリセリド）
	// protected static final String CODE_HIGHT ="3F015000002399901";		// 中性脂肪（トリグリセリド）
	protected static final String CODE_HDL ="3F070000002327101";			// HDLコレステロール※一番先頭を使用
	// protected static final String CODE_HIGHT ="3F070000002327201";		// HDLコレステロール
	// protected static final String CODE_HIGHT ="3F070000002399901";		// HDLコレステロール
	protected static final String CODE_LDL ="3F077000002327101";			// LDLコレステロール※一番先頭を使用
	// protected static final String CODE_HIGHT ="3F077000002327201";		// LDLコレステロール
	// protected static final String CODE_HIGHT ="3F077000002399901";		// LDLコレステロール
        
//masuda^ 2018/03/02 non-HDL 
        protected static final String CODE_NON_HDL = "3F069000002391901";
//masuda$

	/* 肝機能 */
	protected static final String CODE_GOT ="3B035000002327201";		// GOT(AST)
	// protected static final String CODE_HIGHT ="3B035000002399901";	// GOT(AST)
	protected static final String CODE_GPT ="3B045000002327201";		// GPT(ALT)
	// protected static final String CODE_HIGHT ="3B045000002399901";	// GPT(ALT)
	protected static final String CODE_GANMA ="3B090000002327101";		// γ-GT(γ-GTP)
	// protected static final String CODE_HIGHT ="3B090000002399901";	// γ-GT(γ-GTP)

	/* 医師判断 */
	protected static final String CODE_METABO ="9N501000000000011";			// メタボリックシンドローム判定
	// protected static final String CODE_HIGHT ="9N506000000000011";		// 保健指導レベル
	protected static final String CODE_ISINO_HANDAN ="9N511000000000049";	// 医師の診断(判定)
	protected static final String CODE_ISINO_SIMEI ="9N516000000000049";	// 健康診断を実施した医師の氏名
	// protected static final String CODE_HIGHT ="9N521000000000049";	// 医師の意見
	// protected static final String CODE_HIGHT ="9N526000000000049";	// 意見を述べた医師の氏名
	// protected static final String CODE_HIGHT ="9N531000000000049";	// 歯科医師による健康診断
	// protected static final String CODE_HIGHT ="9N536000000000049";	// 健康診断を実施した歯科医師の氏名
	// protected static final String CODE_HIGHT ="9N541000000000049";	// 歯科医師の意見
	// protected static final String CODE_HIGHT ="9N546000000000049";	// 意見を述べた歯科医師の氏名
	// protected static final String CODE_HIGHT ="9N551000000000049";	// 備考

	/* 貧血 */
	protected static final String CODE_HINKETU_JISIRIYU ="2A020161001930149";	// 貧血検査実施理由
	protected static final String CODE_SEKESYU ="2A020000001930101";			// 赤血球数
	protected static final String CODE_HEMATORIC ="2A040000001930102";			// ヘマトクリット値
	protected static final String CODE_KESYOKUSORYOU ="2A030000001930101";		// 血色素量(ヘモグロビン値)

	/* 心電図 */
	protected static final String CODE_SINDENZU_JISIRIYU ="9A110161000000049";	// 心電図実施理由
	protected static final String CODE_SINDENZU ="9A110160700000011";			// 心電図(所見の有無)
	protected static final String CODE_SINDENZU_SYOKEN ="9A110160800000049";	// 心電図所見

	/* 眼底 */
	protected static final String CODE_GANTEI_KENSA ="9E100166000000011";	// 眼底検査(キースワグナー分類)
	protected static final String CODE_GANTEI_H ="9E100166100000011";		// 眼底検査(シェイエ分類:H)
	protected static final String CODE_GANTEI_S ="9E100166200000011";		// 眼底検査(シェイエ分類:S)
	protected static final String CODE_GANTEI_SCOTT ="9E100166300000011";	// 眼底検査(SCOTT分類)
	protected static final String CODE_GANTEI_SONOTA ="9E100160900000049";	// 眼底検査(その他の所見)
	protected static final String CODE_GANTEI_JISIRIYU ="9E100161000000049";// 眼底検査実施理由
	// ※Davos分類が無いため、「その他の所見に記載」

	/* 非特定健診 */
	protected static final String CODE_NYOSENKETU ="1A100000000191111";	// 尿潜血
	// protected static final String CODE_HIGHT ="1A100000000190111";	// 尿潜血
	protected static final String CODE_KESEI_C ="3C015000002327101";	// 血清クレアチニン※小数点以下により判断する
	// protected static final String CODE_HIGHT ="3C015000002399901";	// 血清クレアチニン
	protected static final String CODE_KESEI_N ="3C020000002327101";	// 血清尿酸
	// protected static final String CODE_HIGHT ="3C020000002399901";	// 血清尿酸
	protected static final String CODE_KORESTEROL ="3F050000002327101";	// 総コレステロール
	// protected static final String CODE_HIGHT ="3F050000002327201";	// 総コレステロール
	// protected static final String CODE_HIGHT ="3F050000002399901";	// 総コレステロール
	protected static final String CODE_BIRIRUBIN ="3J010000002327101";	// 総ビリルビン
	// protected static final String CODE_HIGHT ="3J010000002399901";	// 総ビリルビン
	protected static final String CODE_ALP ="3B070000002327101";		// ALP
	// protected static final String CODE_HIGHT ="3B070000002399901";	// ALP
	protected static final String CODE_SOUTANPAKU ="3A010000002327101";	// 総蛋白
	// protected static final String CODE_HIGHT ="3A010000002399901";	// 総蛋白
	protected static final String CODE_FERITIRIN ="5C095000002302301";	// 血清フェリチン
	// protected static final String CODE_HIGHT ="5C095000002399901";	// 血清フェリチン
	protected static final String CODE_ARUBUMIN ="3A015000002327101";	// アルブミン
	// protected static final String CODE_HIGHT ="3A015000002399901";	// アルブミン

	// add s.inoue 2010/02/10
	public static final String[] koumokuCD_Situmonhyo = {
		"9N701000000000011","9N706000000000011","9N711000000000011",
		"9N716000000000011","9N721000000000011","9N726000000000011",
		"9N731000000000011","9N736000000000011","9N786000000000011",
		"9N806000000000011"
	};
	// add s.inoue 2010/02/10
	public static final String[] koumokuCD_Situmonhyo_Order = {
		"7","8","9","10","11","12","13","14","15","16"
	};

	/**
	 * @return the importFormat
	 */
	public String getImportFormat() {
		return importFormat;
	}
	/**
	 * @return the kensaCenterNumber
	 */
	public String getKensaCenterNumber() {
		return kensaCenterNumber;
	}
	/**
	 * @return the kensaJissidate
	 */
	public String getKensaJissidate() {
		return kensaJissidate;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param importFormat the importFormat to set
	 */
	public boolean setImportFormat(String importFormat) {
		this.isValidateAsDataSet = false;
		this.importFormat = JValidate.validateImportFormat(importFormat);

		if( this.importFormat == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3310", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaCenterNumber the kensaCenterNumber to set
	 */
	public boolean setKensaCenterNumber(String kensaCenterNumber) {
		this.isValidateAsDataSet = false;
		this.kensaCenterNumber = JValidate.validateKensaCenterCode(kensaCenterNumber);

		if( this.kensaCenterNumber == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3311", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaJissidate the kensaJissidate to set
	 */
	public boolean setKensaJissidate(String kensaJissidate) {
		this.isValidateAsDataSet = false;
		this.kensaJissidate = JValidate.validateDate(kensaJissidate);

		if( this.kensaJissidate == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3312", null);
			return false;
		}

		return true;
	}

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = true;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public boolean setFilePath(String filePath) {
		this.isValidateAsDataSet = false;
		this.filePath = JValidate.validateFilePath(filePath);

		if( this.filePath == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3313", null);
			return false;
		}

		return true;
	}
}