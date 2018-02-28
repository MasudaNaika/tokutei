package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JKenshinpatternMasterMaintenanceListData extends ValueObjectImpl {
	private String K_P_NO;
	private String K_P_NAME;
	private String BIKOU;
	private boolean isValidateAsDataSet = false;

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

	/**
	 * @return the HKNJANUM
	 */
	public String getK_P_NO() {
		if (K_P_NO == null ) {
			K_P_NO = "";
		}
		return K_P_NO;
	}
	/**
	 * @return the HKNJANAME
	 */
	public String getK_P_NAME() {
		if (K_P_NAME == null ) {
			K_P_NAME = "";
		}
		return K_P_NAME;
	}
	/**
	 * @return the zipcode
	 */
	public String getBIKOU() {
		if (BIKOU == null ) {
			BIKOU = "";
		}
		return BIKOU;
	}

	/**
	 * @param K_P_NO the K_P_NO to set
	 */
	public boolean setK_P_NO(String K_P_NO) {
		this.isValidateAsDataSet = false;
		this.K_P_NO =  JValidate.validatePatternNumber(K_P_NO);

		// eidt s.inoue 2011/10/07
//		if( this.K_P_NO.equals("") ) {
		if( this.K_P_NO == null ) {
			JErrorMessage.show("M5511", null);
			return false;
		}
		return true;
	}

	/**
	 * @param K_P_NAME the K_P_NAME to set
	 */
	public boolean setK_P_NAME(String K_P_NAME) {

		this.isValidateAsDataSet = false;
		this.K_P_NAME = JValidate.validatePatternName(K_P_NAME);

		if( this.K_P_NAME == null ) {
			JErrorMessage.show("M5512", null);
			return false;
		}

		return true;
	}

	/**
	 * @param BIKOU the BIKOU to set
	 */
	public boolean setBIKOU(String BIKOU) {
		this.isValidateAsDataSet = false;
		this.BIKOU = JValidate.validateNote(BIKOU);

		if( this.BIKOU == null ) {
			JErrorMessage.show("M5513", null);
			return false;
		}

		return true;
	}
}