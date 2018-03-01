package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/*
 * 一般判定の際に対象となる個人を特定するためのデータを受け渡すクラス
 */
public class JIppanHanteiData {
	private String hokenjyaNumber= new String("");
	private String hihokenjyaCode= new String("");
	private String hihokenjyaNumber= new String("");
	private String kensaJissiDate = new String("");
	private boolean isValidateAsDataSet = false;
	/**
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return hokenjyaNumber;
	}
	/**
	 * @return the hihokenjyaCode
	 */
	public String getHihokenjyaCode() {
		return hihokenjyaCode;
	}
	/**
	 * @return the hihokenjyaNumber
	 */
	public String getHihokenjyaNumber() {
		return hihokenjyaNumber;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);

		if( this.hokenjyaNumber == null )
			return false;

		return true;
	}

	/**
	 * @param hihokenjyaCode the hihokenjyaCode to set
	 */
	public boolean setHihokenjyaCode(String hihokenjyaCode) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaCode = JValidate.validateHihokenjyaKigou(hihokenjyaCode);

		if( this.hihokenjyaCode == null )
			return false;

		return true;
	}

	/**
	 * @param hihokenjyaNumber the hihokenjyaNumber to set
	 */
	public boolean setHihokenjyaNumber(String hihokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaNumber = JValidate.validateHihokenjyaNumber(hihokenjyaNumber);

		if( this.hihokenjyaNumber == null )
			return false;

		return true;
	}

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = false;
	}
	/**
	 * @return the kensaJissiDate
	 */
	public String getKensaJissiDate() {
		return kensaJissiDate;
	}

	/**
	 * @param kensaJissiDate the kensaJissiDate to set
	 */
	public boolean setKensaJissiDate(String kensaJissiDate) {
		this.isValidateAsDataSet = false;
		this.kensaJissiDate = JValidate.validateDate(kensaJissiDate,true,true);

		if( this.kensaJissiDate == null )
			return false;

		return true;
	}


}
