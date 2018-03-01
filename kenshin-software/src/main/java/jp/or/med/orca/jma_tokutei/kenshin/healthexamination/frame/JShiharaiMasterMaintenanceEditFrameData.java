package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JShiharaiMasterMaintenanceEditFrameData {
	private String DaikouNumber = new String("");
	private String DaikouName = new String("");
	private String Zipcode = new String("");
	private String Address = new String("");
	private String TEL = new String("");
	private boolean isValidationAsDataSet = false;

	/**
	 * @return the isValidationAsDataSet
	 */
	public boolean isValidationAsDataSet() {
		return isValidationAsDataSet;
	}
	/**
	 * @return the hokenjyaNumber
	 */
	public String getDaikouNumber() {
		return DaikouNumber;
	}
	/**
	 * @return the hokenjyaName
	 */
	public String getDaikouName() {
		if (DaikouName == null ) {
			DaikouName = "";
		}
		return DaikouName;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		if (Zipcode == null ) {
			Zipcode = "";
		}
		return Zipcode;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		if (Address == null ) {
			Address = "";
		}
		return Address;
	}

	/**
	 * @return the tEL
	 */
	public String getTEL() {
		if (TEL == null ) {
			TEL = "";
		}
		return TEL;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setDaikouNumber(String hokenjyaNumber) {
		this.isValidationAsDataSet = false;
		this.DaikouNumber = JValidate.validateDaikouNumber(hokenjyaNumber);

		if( this.DaikouNumber == null ) {
			JErrorMessage.show("M5001", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hokenjyaName the hokenjyaName to set
	 */
	public boolean setDaikouName(String hokenjyaName) {
		this.isValidationAsDataSet = false;
		this.DaikouName = JValidate.validateDaikouName(hokenjyaName);

		if( this.DaikouName == null ) {

			JErrorMessage.show("M5002", null);
			return false;
		}

		return true;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public boolean setZipcode(String zipcode) {
		this.isValidationAsDataSet = false;
		this.Zipcode = JValidate.validateZipcode(zipcode);

		if( this.Zipcode == null ) {
			JErrorMessage.show("M5003", null);
			return false;
		}

		return true;
	}

	/**
	 * @param address the address to set
	 */
	public boolean setAddress(String address) {
		this.isValidationAsDataSet = false;
		this.Address = JValidate.validateAddress(address);

		if( this.Address == null ) {
			JErrorMessage.show("M5004", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tel the tEL to set
	 */
	public boolean setTEL(String tel) {
		this.isValidationAsDataSet = false;
		this.TEL = JValidate.validatePhoneNumber(tel);

		if( this.TEL == null ) {
			JErrorMessage.show("M5005", null);
			return false;
		}

		return true;
	}

	/**
	 * @param isValidationAsDataSet the isValidationAsDataSet to set
	 */
	public void setValidationAsDataSet() {
		this.isValidationAsDataSet = true;
	}
}