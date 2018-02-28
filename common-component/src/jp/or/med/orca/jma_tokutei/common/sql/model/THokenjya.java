package jp.or.med.orca.jma_tokutei.common.sql.model;

/**
 * T_HOKENJYAレコードを表すクラス
 *
 */
public class THokenjya implements RecordModel {

	private String HKNJA_NO;
	private String HISTORY_NO;

	public String getHKNJA_NO() {
		return HKNJA_NO;
	}
	public void setHKNJA_NO(String hknja_no) {
		HKNJA_NO = hknja_no;
	}

	public String getHISTORY_NO() {
		return HISTORY_NO;
	}
	public void setUKETUKE_ID(String history_no) {
		HISTORY_NO = history_no;
	}
}
