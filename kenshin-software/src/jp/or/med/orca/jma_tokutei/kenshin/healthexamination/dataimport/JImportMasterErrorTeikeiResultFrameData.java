package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportMasterErrorTeikeiResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;

	public String CSV_COLUMN_SYOKEN_TYPE;
	public String CSV_COLUMN_SYOKEN_TYPE_NAME;
	public String CSV_COLUMN_SYOKEN_NO;
	public String CSV_COLUMN_SYOKEN_NAME;

	/**
	 * データベース上のデータを保持。最終的に選択された受診者を判定する際に使用。
	 */
	public Hashtable<String, String> DatabaseItem;
}
