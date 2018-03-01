package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorHokenjyaResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	// add s.inoue 2010/03/01
	/* 保険者マスタ */
	public String CSV_COLUMN_HKNJANUM;
	public String CSV_COLUMN_HKNJANAME;
	public String CSV_COLUMN_POST;
	public String CSV_COLUMN_ADRS;
	public String CSV_COLUMN_BANTI;
	public String CSV_COLUMN_TEL;
	public String CSV_COLUMN_KIGO;
	public String CSV_COLUMN_HON_GAIKYURATE;
	public String CSV_COLUMN_HON_NYUKYURATE;
	public String CSV_COLUMN_KZK_GAIKYURATE;
	public String CSV_COLUMN_KZK_NYUKYURATE;
	public String CSV_COLUMN_ITAKU_KBN;
	public String CSV_COLUMN_TANKA_KIHON;
	public String CSV_COLUMN_HINKETU_CD;
	public String CSV_COLUMN_TANKA_HINKETU;
	public String CSV_COLUMN_SINDENZU_CD;
	public String CSV_COLUMN_TANKA_SINDENZU;
	public String CSV_COLUMN_GANTEI_CD;
	public String CSV_COLUMN_TANKA_GANTEI;
	public String CSV_COLUMN_TANKA_NINGENDOC;
	public String CSV_COLUMN_TANKA_HANTEI;
	public String CSV_COLUMN_HKNJYA_HISTORY_NO;
	public String CSV_COLUMN_HKNJYA_LIMITDATE_START;
	public String CSV_COLUMN_HKNJYA_LIMITDATE_END;
	public String CSV_COLUMN_YUKOU_FLG;

//	/* 支払代行マスタ */
//	public String CSV_COLUMN_SHIHARAI_DAIKO_NO;
//	public String CSV_COLUMN_SHIHARAI_DAIKO_NAME;
//	public String CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD;
//	public String CSV_COLUMN_SHIHARAI_DAIKO_ADR;
//	public String CSV_COLUMN_SHIHARAI_DAIKO_TEL;

	/**
	 * データベース上のデータを保持。最終的に選択された受診者を判定する際に使用。
	 */
	public Hashtable<String, String> DatabaseItem;
}
