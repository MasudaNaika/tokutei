package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Hashtable;

public class JImportErrorResultFrameData {
	public int rowNo;
	public String errField;
	public Integer errCase;
	// 制御用
	public String uketukeNo;
	public String jisiKikanNo;
	public String jusinSeiriNo;
	public String jishiDate;
	public String kanaShimei;
	public String seiNenGapi;
	public String seiBetu;
	public String nyuBi;
	public String shokuZenGo;
	public String jisiKbn;
	public String koumokuCd;
	public String ijyoKbn;
	public String kekkaTiKeitai;
	public String kekkaTi;
	
	// del s.inoue
	// public String HiHokenjyasyoKigo;
	// public String HiHokenjyasyoNumber;
	
	/**
	 * データベース上のデータを保持。最終的に選択された受診者を判定する際に使用。
	 */
	public Hashtable<String, String> DatabaseItem;
}
