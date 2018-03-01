package jp.or.med.orca.jma_tokutei.common.sql.model;

/**
 * T_SHIHARAIレコードを表すクラス
 *
 */
public class TShiharai implements RecordModel {

	private String SHIHARAI_NO;

	public String getHKNJA_NO() {
		return SHIHARAI_NO;
	}
	public void setHKNJA_NO(String hknja_no) {
		SHIHARAI_NO = hknja_no;
	}
}
