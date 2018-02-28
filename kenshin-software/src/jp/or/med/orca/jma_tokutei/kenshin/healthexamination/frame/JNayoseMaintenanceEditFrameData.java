package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

public class JNayoseMaintenanceEditFrameData {
	private String nayoseNo = new String("");
	private boolean isValidateAsDataSet = false;
	/**
	 * @return the patternNumber
	 */
	public String getNayoseNumber() {
		return nayoseNo;
	}

	/**
	 * @param jyusinken_ID the jyusinken_ID to set
	 */
	public boolean setNayoseNumber(String nayoseNo) {
		this.isValidateAsDataSet = false;
		this.nayoseNo = JValidate.validateNayoseNo(nayoseNo);

		if( this.nayoseNo == null ) {
			JErrorMessage.show("M9800", null);
			return false;
		}

		return true;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = true;
	}
}
