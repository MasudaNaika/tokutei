package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/**
 * ÉÜÅ[ÉUìoò^
 */
public class JRegisterUserFrameData {
	private String UserName = new String("");
	private String Password = new String("");
	private boolean isValidateAsDataset = false;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param userName the userName to set
	 */
	public boolean setUserName(String userName) {
		isValidateAsDataset = false;
		this.UserName = JValidate.validateUserName(userName);

		if( this.UserName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M7301", null);
			return false;
		}

		return true;
	}

	/**
	 * @param password the password to set
	 */
	public boolean setPassword(String password) {
		isValidateAsDataset = false;
		this.Password = JValidate.validatePassword(password);

		if( this.Password == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M7302", null);
			return false;
		}

		return true;
	}
	/**
	 * @return the isValidateAsDataset
	 */
	public boolean isValidateAsDataset() {
		return isValidateAsDataset;
	}
	/**
	 * @param isValidateAsDataset the isValidateAsDataset to set
	 */
	public void setValidateAsDataset() {
		this.isValidateAsDataset = true;
	}
}
