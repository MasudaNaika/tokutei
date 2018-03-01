package jp.or.med.orca.jma_tokutei.common.sql.model;

public class TDataTypeCode implements RecordModel {

	private String KOUMOKU_CD;
	private Integer CODE_NUM;
	private String CODE_NAME;
	
	public String getCODE_NAME() {
		return CODE_NAME;
	}
	public void setCODE_NAME(String code_name) {
		CODE_NAME = code_name;
	}
	public Integer getCODE_NUM() {
		return CODE_NUM;
	}
	public void setCODE_NUM(Integer code_num) {
		CODE_NUM = code_num;
	}
	public String getKOUMOKU_CD() {
		return KOUMOKU_CD;
	}
	public void setKOUMOKU_CD(String koumoku_cd) {
		KOUMOKU_CD = koumoku_cd;
	}
	
	
}
