package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JShokenMasterMaintenanceEditFrameData {
	private String teikeiType = new String("");  //  @jve:decl-index=0:
	private String teikeiTypeName = new String("");  //  @jve:decl-index=0:
	private String teikeiNo = new String("");  //  @jve:decl-index=0:
	private String teikeiBun = new String("");  //  @jve:decl-index=0:

	private boolean isValidateAsDataSet = false;
	/**
	 * @return the teikeiNo
	 */
	public String getTeikeiType() {
		return teikeiType;
	}

	/**
	 * @param teikeiNo the teikeiNo to set
	 */
	public boolean setTeikeiType(String teikeiType) {
		this.isValidateAsDataSet = false;
		this.teikeiType = JValidate.validateTeikeiType(teikeiType);

		if( this.teikeiType == null ) {
			JErrorMessage.show("M9911", null);
			return false;
		}

		return true;
	}

	/**
	 * @return the teikeiBun
	 */
	public String getTeikeiTypeName() {
		return teikeiTypeName;
	}

	/**
	 * @param note the teikeibun to set
	 */
	public boolean setTeikeiTypeName(String teikeiTypeName) {
		this.isValidateAsDataSet = false;
		this.teikeiTypeName = JValidate.validateTeikeibunShubetu(teikeiTypeName);

		if( this.teikeiTypeName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M9921", null);
			return false;
		}

		return true;
	}


	/**
	 * @return the teikeiNo
	 */
	public String getTeikeiNumber() {
		return teikeiNo;
	}

	/**
	 * @param teikeiNo the teikeiNo to set
	 */
	public boolean setTeikeiNumber(String teikeiNo) {
		this.isValidateAsDataSet = false;
		// edit s.inoue 2010/05/19
		if (teikeiNo.length() == 1)
			teikeiNo = "0" + teikeiNo;

		this.teikeiNo = JValidate.validateTeikeiNumber(teikeiNo);

		if( this.teikeiNo == null ) {
			JErrorMessage.show("M9900", null);
			return false;
		}

		return true;
	}

	/**
	 * @return the teikeiBun
	 */
	public String getTeikeibun() {
		return teikeiBun;
	}

	/**
	 * @param note the teikeibun to set
	 */
	public boolean setTeikeibun(String teikeibun) {
		this.isValidateAsDataSet = false;
		this.teikeiBun = JValidate.validateTeikeibun(teikeibun);

		if( this.teikeiBun == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M9901", null);
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