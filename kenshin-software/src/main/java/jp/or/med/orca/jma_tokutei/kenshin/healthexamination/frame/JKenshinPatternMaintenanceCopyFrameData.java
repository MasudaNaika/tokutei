package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JKenshinPatternMaintenanceCopyFrameData {
	private String sourcePatternNumber = new String("");
	private String newPatternName = new String("");
	private String note = new String("");
	private String patternNumber = new String("");
	private boolean isValidateAsDataSet = false;
	/**
	 * @return the newPatternName
	 */
	public String getNewPatternName() {
		return newPatternName;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @return the patternNumber
	 */
	public String getPatternNumber() {
		return patternNumber;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}
	/**
	 * @return the sourcePatternNumber
	 */
	public String getSourcePatternNumber() {
		return sourcePatternNumber;
	}
	
	
	/**
	 * @param sourcePatternName the sourcePatternName to set
	 */
	
	/**
	 * @param newPatternName the newPatternName to set
	 */
	public boolean setNewPatternName(String newPatternName) {
		this.isValidateAsDataSet = false;
		this.newPatternName = JValidate.validatePatternName(newPatternName);
		
		if( this.newPatternName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3911", null);
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
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3912", null);
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

	/**
	 * @param patternNumber the patternNumber to set
	 */
	public boolean setPatternNumber(String patternNumber) {
		this.isValidateAsDataSet = false;
		this.patternNumber = JValidate.validatePatternNumber(patternNumber);
		
		if( this.patternNumber == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3913", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param sourcePatternNumber the sourcePatternNumber to set
	 */
	public boolean setSourcePatternNumber(String sourcePatternNumber) {
		this.isValidateAsDataSet  = false;
		this.sourcePatternNumber = JValidate.validatePatternNumber(sourcePatternNumber);
		
		if( this.sourcePatternNumber == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3914", null);
			return false;
		}
		
		return true;
	}
	
	
	
	
}
