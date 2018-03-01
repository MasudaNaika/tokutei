package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorSiharaiResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	/* 支払代行マスタ */
	public String CSV_COLUMN_SHIHARAI_DAIKO_NO;
	public String CSV_COLUMN_SHIHARAI_DAIKO_NAME;
	public String CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD;
	public String CSV_COLUMN_SHIHARAI_DAIKO_ADR;
	public String CSV_COLUMN_SHIHARAI_DAIKO_TEL;

	/**
	 * データベース上のデータを保持。最終的に選択された受診者を判定する際に使用。
	 */
	public Hashtable<String, String> DatabaseItem;
}
