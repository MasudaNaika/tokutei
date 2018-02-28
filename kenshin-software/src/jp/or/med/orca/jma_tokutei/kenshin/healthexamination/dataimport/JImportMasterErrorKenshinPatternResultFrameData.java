package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorKenshinPatternResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	/* 健診パターンマスタ */
	public String CSV_COLUMN_KENSHIN_PM_K_P_NO;
	public String CSV_COLUMN_KENSHIN_K_P_NAME;
	public String CSV_COLUMN_KENSHIN_BIKOU;

	public String[] CSV_COLUMN_KENSHIN_LOW_ID;
	public String[] CSV_COLUMN_KENSHIN_KOUMOKU_CD;

	/**
	 * データベース上のデータを保持。最終的に選択された受診者を判定する際に使用。
	 */
	public Hashtable<String, String> DatabaseItem;
}
