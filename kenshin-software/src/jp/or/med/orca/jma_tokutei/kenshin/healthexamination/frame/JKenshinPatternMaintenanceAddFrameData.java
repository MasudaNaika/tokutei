package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JKenshinPatternMaintenanceAddFrameData {
	private String patternNumber = new String("");
	private String patternName = new String("");
	private String note = new String("");
	private boolean isValidateAsDataSet = false;
	/**
	 * @return the patternNumber
	 */
	public String getPatternNumber() {
		return patternNumber;
	}
	/**
	 * @return the patternName
	 */
	public String getPatternName() {
		return patternName;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}
	
	/**
	 * @param patternNumber the patternNumber to set
	 */
	public boolean setPatternNumber(String patternNumber) {
		this.isValidateAsDataSet = false;
		this.patternNumber = JValidate.validatePatternNumber(patternNumber);
		
		if( this.patternNumber == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M5511", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param patternName the patternName to set
	 */
	public boolean setPatternName(String patternName) {
		this.isValidateAsDataSet = false;
		this.patternName = JValidate.validatePatternName(patternName);
		
		if( this.patternName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M5512", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param note the note to set
	 */
	public boolean setNote(String note) {
		this.isValidateAsDataSet = false;
		this.note = JValidate.validateNote(note);
		
		if( this.note == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M5513", null);
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
