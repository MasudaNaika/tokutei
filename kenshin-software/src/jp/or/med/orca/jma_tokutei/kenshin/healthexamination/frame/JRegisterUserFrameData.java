package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JRegisterUserFrameData {
	private String userName = new String("");
	private String password = new String("");
	private String kengen = new String("");
	private boolean isValidateAsDataSet = false;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the kengen
	 */
	public String getKengen() {
		return kengen;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}
	
	/**
	 * @param userName the userName to set
	 */
	public boolean setUserName(String userName) {
		this.isValidateAsDataSet = false;
		this.userName = JValidate.validateUserName(userName);
		
		if( this.userName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M4811", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param password the password to set
	 */
	public boolean setPassword(String password) {
		this.isValidateAsDataSet = false;
		this.password = JValidate.validatePassword(password);
		
		if( this.password == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M4812", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param kengen the kengen to set
	 */
	public boolean setKengen(String kengen) {
		this.isValidateAsDataSet = false;
		this.kengen = JValidate.validateUserKengen(kengen);
		
		if( this.kengen == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M4813", null);
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
