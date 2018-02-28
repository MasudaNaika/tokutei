package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JKensaKikanCodeMasterMaintenanceEditFrameData {
	private String kensaCenterCode = new String("");
	private String kensaCenterName = new String("");
	private String kensaCenterKoumokuCode = new String("");
	private String kensaKoumokuCode = new String("");
	private String koumokuName = new String("");
	private boolean isValidateAsDataSet = false;
	/**
	 * @return the kensaCenterCode
	 */
	public String getKensaCenterCode() {
		return kensaCenterCode;
	}
	/**
	 * @return the kensaCenterName
	 */
	public String getKensaCenterName() {
		return kensaCenterName;
	}
	/**
	 * @return the kensaCenterKoumokuCode
	 */
	public String getKensaCenterKoumokuCode() {
		return kensaCenterKoumokuCode;
	}
	/**
	 * @return the kensaKoumokuCode
	 */
	public String getKensaKoumokuCode() {
		return kensaKoumokuCode;
	}
	/**
	 * @return the koumokuName
	 */
	public String getKoumokuName() {
		return koumokuName;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}
	
	/**
	 * @param kensaCenterCode the kensaCenterCode to set
	 */
	public boolean setKensaCenterCode(String kensaCenterCode) {
		this.isValidateAsDataSet = false;
		this.kensaCenterCode = JValidate.validateKensaCenterCode(kensaCenterCode);
		
		if( this.kensaCenterCode == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3710", null);
			return false;
		}
		
		return true;
	}
	/**
	 * @param kensaCenterName the kensaCenterName to set
	 */
	public boolean setKensaCenterName(String kensaCenterName) {
		this.isValidateAsDataSet = false;
		this.kensaCenterName = JValidate.validateKensaCenterName(kensaCenterName);
		
		if( this.kensaCenterName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3711", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param kensaCenterKoumokuCode the kensaCenterKoumokuCode to set
	 */
	public boolean setKensaCenterKoumokuCode(String kensaCenterKoumokuCode) {
		this.isValidateAsDataSet = false;
		this.kensaCenterKoumokuCode = JValidate.validateKensaCenterKoumokuCode(kensaCenterKoumokuCode);
		
		if( this.kensaCenterKoumokuCode == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3712", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param kensaKoumokuCode the kensaKoumokuCode to set
	 */
	public boolean setKensaKoumokuCode(String kensaKoumokuCode) {
		this.isValidateAsDataSet = false;
		this.kensaKoumokuCode = JValidate.validateKensaKoumokuCode(kensaKoumokuCode);
		
		if( this.kensaKoumokuCode == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3713", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param koumokuName the koumokuName to set
	 */
	public boolean setKoumokuName(String koumokuName) {
		this.isValidateAsDataSet = false;
		this.koumokuName = JValidate.validateKensaKoumokuName(koumokuName);
		
		if( this.koumokuName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3714", null);
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
	
	
}