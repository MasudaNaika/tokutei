package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JSearchResultListFrameData {
//	2008/3/19 西山 修正
//	T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//	-------------------------------------------------------------------------------------------------------
	private String uketukeId = "";
//	-------------------------------------------------------------------------------------------------------

	private String jyushinkenID = new String("");
	private String hokenjyaNumber = new String("");
	private String hihokenjyaNumber = new String("");
	private String hihokenjyaCode = new String("");
	private String name = new String("");
	private String kensaBeginDate = new String("");
	private String kensaEndDate = new String("");
	private String hanteiBeginDate = new String("");
	private String hanteiEndDate = new String("");
	private String tsuuchiBeginDate = new String("");
	private String tsuuchiEndDate = new String("");
	private String hokenSidouLevel = new String("");
	private boolean isValidateAsDataSet = false;

//	2008/3/19 西山 修正
//	T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//	-------------------------------------------------------------------------------------------------------
	public String getUketukeId() {
		return uketukeId;
	}
	public void setUketukeId(String uketukeId) {
		this.uketukeId = uketukeId;
	}
//	-------------------------------------------------------------------------------------------------------

	/**
	 * @return the jyushinkenID
	 */
	public String getJyushinkenID() {
		return jyushinkenID;
	}
	/**
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return hokenjyaNumber;
	}
	/**
	 * @return the hihokenjyaNumber
	 */
	public String getHihokenjyaNumber() {
		return hihokenjyaNumber;
	}
	/**
	 * @return the hihokenjyaCode
	 */
	public String getHihokenjyaCode() {
		return hihokenjyaCode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the kensaBeginDate
	 */
	public String getKensaBeginDate() {
		return kensaBeginDate;
	}
	/**
	 * @return the kensaEndDate
	 */
	public String getKensaEndDate() {
		return kensaEndDate;
	}
	/**
	 * @return the hanteiBeginDate
	 */
	public String getHanteiBeginDate() {
		return hanteiBeginDate;
	}
	/**
	 * @return the hanteiEndDate
	 */
	public String getHanteiEndDate() {
		return hanteiEndDate;
	}
	/**
	 * @return the tsuuchiBeginDate
	 */
	public String getTsuuchiBeginDate() {
		return tsuuchiBeginDate;
	}
	/**
	 * @return the tsuuchiEndDate
	 */
	public String getTsuuchiEndDate() {
		return tsuuchiEndDate;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}



	/**
	 * @param jyushinkenID the jyushinkenID to set
	 */
	public boolean setJyushinkenID(String jyushinkenID) {
		this.isValidateAsDataSet = false;

		/* Modified 2008/08/02 若月  */
		/* --------------------------------------------------- */
//		this.jyushinkenID = JValidate.validateJyushinkenID(jyushinkenID);
//
//		if( this.jyushinkenID == null ) {
//			JErrorMessage.show("M4901", null);
//			return false;
//		}
		/* --------------------------------------------------- */
		this.jyushinkenID = JValidate.validateJyushinkenIDforSearch(jyushinkenID);

		if( this.jyushinkenID == null ) {
			JErrorMessage.show("M4912", null);
			return false;
		}
		/* --------------------------------------------------- */

		return true;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);

		if( this.hokenjyaNumber == null ) {
			JErrorMessage.show("M4902", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyaNumber the hihokenjyaNumber to set
	 */
	public boolean setHihokenjyaNumber(String hihokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaNumber = JValidate.validateHihokenjyaNumber(hihokenjyaNumber);

		if( this.hihokenjyaNumber == null ) {
			JErrorMessage.show("M4903", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyaCode the hihokenjyaCode to set
	 */
	public boolean setHihokenjyaCode(String hihokenjyaCode) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaCode = JValidate.validateHihokenjyaKigou(hihokenjyaCode);

		if( this.hihokenjyaCode == null ) {
			JErrorMessage.show("M4904", null);
			return false;
		}

		return true;
	}

	/**
	 * @param name the name to set
	 */
	public boolean setName(String name) {
		this.isValidateAsDataSet = false;
		this.name = JValidate.validateNameKana(name);

		if( this.name == null ) {
			JErrorMessage.show("M4905", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaBeginDate the kensaBeginDate to set
	 */
	public boolean setKensaBeginDate(String kensaBeginDate) {
		this.isValidateAsDataSet = false;
		this.kensaBeginDate = JValidate.validateDate(kensaBeginDate,true,false);

		if( this.kensaBeginDate == null ) {
			JErrorMessage.show("M4906", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaEndDate the kensaEndDate to set
	 */
	public boolean setKensaEndDate(String kensaEndDate) {
		this.isValidateAsDataSet = false;
		this.kensaEndDate = JValidate.validateDate(kensaEndDate,false,false);

		if( this.kensaEndDate == null ) {
			JErrorMessage.show("M4907", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hanteiBeginDate the hanteiBeginDate to set
	 */
	public boolean setHanteiBeginDate(String hanteiBeginDate) {
		this.isValidateAsDataSet = false;
		this.hanteiBeginDate = JValidate.validateDate(hanteiBeginDate,true,false);

		if( this.hanteiBeginDate == null ) {
			JErrorMessage.show("M4908", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hanteiEndDate the hanteiEndDate to set
	 */
	public boolean setHanteiEndDate(String hanteiEndDate) {
		this.isValidateAsDataSet = false;
		this.hanteiEndDate = JValidate.validateDate(hanteiEndDate,false,false);

		if( this.hanteiEndDate == null ) {
			JErrorMessage.show("M4909", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tsuuchiBeginDate the tsuuchiBeginDate to set
	 */
	public boolean setTsuuchiBeginDate(String tsuuchiBeginDate) {
		this.isValidateAsDataSet = false;
		this.tsuuchiBeginDate = JValidate.validateDate(tsuuchiBeginDate,true,true);

		if( this.tsuuchiBeginDate == null ) {
			JErrorMessage.show("M4910", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tsuuchiEndDate the tsuuchiEndDate to set
	 */
	public boolean setTsuuchiEndDate(String tsuuchiEndDate) {
		this.isValidateAsDataSet = false;
		this.tsuuchiEndDate = JValidate.validateDate(tsuuchiEndDate,false,false);

		if( this.tsuuchiEndDate == null ) {
			JErrorMessage.show("M4911", null);
			return false;
		}

		return true;
	}



	/**
	 * @return the hanteiKekka
	 */
	public String getHanteiKekka() {
		return hokenSidouLevel;
	}

	/**
	 * @param hanteiKekka the hanteiKekka to set
	 */
	public boolean setHokenSidouLevel(String hanteiKekka) {
		this.isValidateAsDataSet = false;

//		if( hanteiKekka.equals("指定無し"))
//		{
//			/* Modified 2008/08/07 若月  */
//			/* --------------------------------------------------- */
////			this.hokenSidouLevel = new String("");
//			/* --------------------------------------------------- */
//			this.hokenSidouLevel = "";
//			/* --------------------------------------------------- */
//
//			return true;
//		}

		this.hokenSidouLevel = JValidate.validateHokenSidouLevel(hanteiKekka);

		if( this.hokenSidouLevel == null ) {
			JErrorMessage.show("M4920", null);
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
