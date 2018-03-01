package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/**
 * 健診項目マスタメンテナンス画面データ
 */
public class JKenshinMasterMaintenanceFrameData {
	private String KensaKoumokuCode = new String("");
	private String KensaKoumokuName = new String("");
	private String HisuFlag = new String("");
	private String kagen = new String("");
	private String jyougen = new String("");
	private String dsJyougen = new String("");
	private String dsKagen = new String("");
	private String jsJyougen = new String("");
	private String jsKagen = new String("");
	private String tanni = new String("");
	private String kijyunchiHanni = new String("");
	private String kensaTanka = new String("");
	private String kensaHouhou = new String("");
	private String note = new String("");
	private String HokenjyaNumber = new String("");

	private boolean isValidateAsDataSet = false;

	/* Added 2008/03/11 若月  */
	/* --------------------------------------------------- */
	private String kensaCode = "";
	/* --------------------------------------------------- */

	/**
	 * @return the kensaKoumokuCode
	 */
	public String getKensaKoumokuCode() {
		return KensaKoumokuCode;
	}
	/**
	 * @return the kensaKoumokuName
	 */
	public String getKensaKoumokuName() {
		return KensaKoumokuName;
	}
	/**
	 * @return the hisuFlag
	 */
	public String getHisuFlag() {
		return HisuFlag;
	}
	/**
	 * @return the kagen
	 */
	public String getKagen() {
		return kagen;
	}
	/**
	 * @return the jyougen
	 */
	public String getJyougen() {
		return jyougen;
	}
	/**
	 * @return the tanni
	 */
	public String getTanni() {
		return tanni;
	}
	/**
	 * @return the kijyunchiHanni
	 */
	public String getKijyunchiHanni() {
		return kijyunchiHanni;
	}
	/**
	 * @return the kensaTanka
	 */
	public String getKensaTanka() {
		return kensaTanka;
	}
	/**
	 * @return the kensaHouhou
	 */
	public String getKensaHouhou() {
		return kensaHouhou;
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
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return HokenjyaNumber;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.HokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);

		if( this.HokenjyaNumber == null ) {
			JErrorMessage.show("M3101", null);
			return false;
		}

		return true;
	}
	/**
	 * @param kensaKoumokuCode the kensaKoumokuCode to set
	 */
	public boolean setKensaKoumokuCode(String kensaKoumokuCode) {
		this.isValidateAsDataSet = false;
		this.KensaKoumokuCode = JValidate.validateKensaKoumokuCode(kensaKoumokuCode);

		if( this.KensaKoumokuCode == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3801", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaKoumokuName the kensaKoumokuName to set
	 */
	public boolean setKensaKoumokuName(String kensaKoumokuName) {
		this.isValidateAsDataSet = false;
		this.KensaKoumokuName = JValidate.validateKensaKoumokuName(kensaKoumokuName);

		if( this.KensaKoumokuName == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3802", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hisuFlag the hisuFlag to set
	 */
	public boolean setHisuFlag(String hisuFlag) {
		this.isValidateAsDataSet = false;
		this.HisuFlag =  JValidate.validateKenshinHisuFlag(hisuFlag);

		if( this.HisuFlag == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3803", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kagen the kagen to set
	 */
	public boolean setKagen(String kagen) {
		this.isValidateAsDataSet = false;
		this.kagen = JValidate.validateKensaResultLimit(kagen);

		if( this.kagen == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3804", null);
			return false;
		}

		return true;
	}

	/**
	 * @param jyougen the jyougen to set
	 */
	public boolean setJyougen(String jyougen) {
		this.isValidateAsDataSet = false;
		this.jyougen = JValidate.validateKensaResultLimit(jyougen);

		if( this.jyougen == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3805", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tanni the tanni to set
	 */
	public boolean setTanni(String tanni) {
		this.isValidateAsDataSet = false;
		this.tanni = JValidate.validateKensaUnit(tanni);

		if( this.tanni == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3809", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kijyunchiHanni the kijyunchiHanni to set
	 */
	public boolean setKijyunchiHanni(String kijyunchiHanni) {
		this.isValidateAsDataSet = false;
		this.kijyunchiHanni = JValidate.validateKensaKijyunHanni(kijyunchiHanni);

		if( this.kijyunchiHanni == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3810", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaTanka the kensaTanka to set
	 */
	public boolean setKensaTanka(String kensaTanka) {
		this.isValidateAsDataSet = false;
		this.kensaTanka = JValidate.validateKensaTanka(kensaTanka);

		if( this.kensaTanka == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3811", null);
			return false;
		}

		return true;
	}

	/* Added 2008/03/11 若月  */
	/* --------------------------------------------------- */
	/**
	 * @param cd the cd to set
	 */
	public boolean setKensaCd(String cd) {
		this.isValidateAsDataSet = false;
		//this.kensaCode = JValidate.validateKensaCd(cd);

		if( this.kensaCode == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3812", null);
			return false;
		}

		return true;
	}
	/* --------------------------------------------------- */

	/**
	 * @param kensaHouhou the kensaHouhou to set
	 */
	public boolean setKensaHouhou(String kensaHouhou) {
		this.isValidateAsDataSet = false;
		this.kensaHouhou = JValidate.validateKensaHouhou(kensaHouhou);

		if( this.kensaHouhou == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3812", null);
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
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3813", null);
			return false;
		}

		return true;
	}



	/**
	 * @return the dsJyougen
	 */
	public String getDsJyougen() {
		return dsJyougen;
	}
	/**
	 * @return the dsKagen
	 */
	public String getDsKagen() {
		return dsKagen;
	}
	/**
	 * @return the jsJyougen
	 */
	public String getJsJyougen() {
		return jsJyougen;
	}
	/**
	 * @return the jsKagen
	 */
	public String getJsKagen() {
		return jsKagen;
	}

	/**
	 * @param dsJyougen the dsJyougen to set
	 */
	public boolean setDsJyougen(String dsJyougen) {
		this.dsJyougen = dsJyougen;
		return true;
	}
	/**
	 * @param dsKagen the dsKagen to set
	 */
	public boolean setDsKagen(String dsKagen) {
		this.dsKagen = new String(dsKagen);
		return true;
	}
	/**
	 * @param jsJyougen the jsJyougen to set
	 */
	public boolean setJsJyougen(String jsJyougen) {
		this.jsJyougen = jsJyougen;
		return true;
	}
	/**
	 * @param jsKagen the jsKagen to set
	 */
	public boolean setJsKagen(String jsKagen) {
		this.jsKagen = jsKagen;
		return true;
	}
	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = true;
	}

	/* Added 2008/03/11 若月  */
	/* --------------------------------------------------- */
	public String getKensaCode() {
		return kensaCode;
	}
	public void setKensaCode(String kensaCode) {
		this.kensaCode = kensaCode;
	}
	/* --------------------------------------------------- */
}
