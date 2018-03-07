package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JKenshinMasterMaintenanceListData extends ValueObjectImpl {
	protected String HKNJANUM;
	protected String KOUMOKU_CD;
	protected String KOUMOKU_NAME;
	protected String KENSA_HOUHOU;
	protected String HISU_FLG;
	protected String DS_KAGEN;
	protected String DS_JYOUGEN;
	protected String JS_KAGEN;
	protected String JS_JYOUGEN;
	protected String TANI;
	protected String KAGEN;
	protected String JYOUGEN;
	protected String KIJYUNTI_HANI;
	protected String TANKA_KENSIN;
	protected String BIKOU;

	private boolean isValidateAsDataSet = false;

	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @return the HKNJANUM
	 */
	public String getHKNJANUM() {
		if (HKNJANUM == null ) {
			HKNJANUM = "";
		}
		return HKNJANUM;
	}
	/**
	 * @return the KOUMOKU_CD
	 */
	public String getKOUMOKU_CD() {
		if (KOUMOKU_CD == null ) {
			KOUMOKU_CD = "";
		}
		return KOUMOKU_CD;
	}
	/**
	 * @return the KOUMOKU_NM
	 */
	public String getKOUMOKU_NAME() {
		if (KOUMOKU_NAME == null ) {
			KOUMOKU_NAME = "";
		}
		return KOUMOKU_NAME;
	}
	/**
	 * @return the KENSA_HOUHOU
	 */
	public String getKENSA_HOUHOU() {
		if (KENSA_HOUHOU == null)
			return KENSA_HOUHOU;

		if (KENSA_HOUHOU.equals(""))
			KENSA_HOUHOU = null;
		return KENSA_HOUHOU;
	}
	/**
	 * @return the HISU_FLG
	 */
	public String getHISU_FLG() {

		if (HISU_FLG == null)
			return HISU_FLG;

		if (HISU_FLG.equals(""))
			HISU_FLG = null;

		return HISU_FLG;
	}
	/**
	 * @return the DS_KAGEN
	 */
	public String getDS_KAGEN() {

		if (DS_KAGEN == null)
			return DS_KAGEN;

		if (DS_KAGEN.equals(""))
			DS_KAGEN = null;

		return DS_KAGEN;
	}
	/**
	 * @return the DS_JYOUGEN
	 */
	public String getDS_JYOUGEN() {

		if (DS_JYOUGEN == null)
			return DS_JYOUGEN;

		if (DS_JYOUGEN.equals("")) {
			DS_JYOUGEN = null;
		}
		return DS_JYOUGEN;
	}
	/**
	 * @return the JS_KAGEN
	 */
	public String getJS_KAGEN() {

		if (JS_KAGEN == null)
			return JS_KAGEN;

		if (JS_KAGEN.equals(""))
			JS_KAGEN = null;
		return JS_KAGEN;
	}
	/**
	 * @return the JS_JYOUGEN
	 */
	public String getJS_JYOUGEN() {

		if (JS_JYOUGEN == null)
			return JS_JYOUGEN;

		if (JS_JYOUGEN.equals(""))
			JS_JYOUGEN = null;
		return JS_JYOUGEN;
	}
	/**
	 * @return the TANI
	 */
	public String getTANI() {

		if (TANI == null ) {
			TANI = "";
		}
		return TANI;
	}
	/**
	 * @return the KAGEN
	 */
	public String getKAGEN() {

		if (KAGEN == null)
			return KAGEN;
		if (KAGEN.equals(""))
			KAGEN = null;
		return KAGEN;
	}
	/**
	 * @return the JYOUGEN
	 */
	public String getJYOUGEN() {
		if (JYOUGEN == null)
			return JYOUGEN;
		if (JYOUGEN.equals(""))
			JYOUGEN = null;
		return JYOUGEN;
	}
	/**
	 * @return the KIJYUNTI_HANI
	 */
	public String getKIJYUNTI_HANI() {
		if (KIJYUNTI_HANI == null ) {
			KIJYUNTI_HANI = "";
		}
		return KIJYUNTI_HANI;
	}
	/**
	 * @return the TANKA_KENSIN
	 */
	public String getTANKA_KENSIN() {
		if (TANKA_KENSIN == null)
			return TANKA_KENSIN;

		if (TANKA_KENSIN.equals(""))
			TANKA_KENSIN = null;
		return TANKA_KENSIN;
	}
	/**
	 * @return the BIKOU
	 */
	public String getBIKOU() {
		if (BIKOU == null ) {
			BIKOU = "";
		}
		return BIKOU;
	}

	/**
	 * @param HKNJANUM the HKNJANUM to set
	 */
	public boolean setHKNJANUM(String HokenjyaNumber) {
		// eidt s.inoue 2011/06/02
		// this.HKNJANUM = HokenjyaNumber;
		if (HokenjyaNumber == null)return true;

		this.isValidateAsDataSet = false;
		this.HKNJANUM = JValidate.validateHokenjyaNumber(HokenjyaNumber);

		if( this.HKNJANUM == null ) {
			JErrorMessage.show("M3101", null);
			return false;
		}

		return true;
	}

	/**
	 * @param KOUMOKU_CD the KOUMOKU_CD to set
	 */
	public boolean setKOUMOKU_CD(String kensaKoumokuCode) {
		// eidt s.inoue 2011/06/02
		// this.KOUMOKU_CD = kensaKoumokuCode;
		this.isValidateAsDataSet = false;
		this.KOUMOKU_CD = JValidate.validateKensaKoumokuCode(kensaKoumokuCode);

		if( this.KOUMOKU_CD == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3801", null);
			return false;
		}

		return true;
	}

	/**
	 * @return the KOUMOKU_NM
	 */
	public boolean setKOUMOKU_NAME(String kensaKoumokuName) {
		this.isValidateAsDataSet = false;
		this.KOUMOKU_NAME = JValidate.validateKensaKoumokuName(kensaKoumokuName);

		if( this.KOUMOKU_NAME == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3802", null);
			return false;
		}

		return true;
	}

	/**
	 * @return the KENSA_HOUHOU
	 */
	public boolean setKENSA_HOUHOU(String kensaHouhou) {

		if (kensaHouhou == null)return true;

		this.isValidateAsDataSet = false;
		this.KENSA_HOUHOU = JValidate.validateKensaHouhou(kensaHouhou);

		if( this.KENSA_HOUHOU == null ) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3812", null);
			return false;
		}

		return true;
	}
	/**
	 * @param HISU_FLG the HISU_FLG to set
	 */
	public boolean setHISU_FLG(String hisuFlag) {

		this.isValidateAsDataSet = false;
		this.HISU_FLG =  JValidate.validateKenshinHisuFlag(hisuFlag);

		if( this.HISU_FLG.equals("") ) {
			JErrorMessage.show("M3803", null);
			return false;
		}

		return true;
	}

	/**
	 * @param DS_KAGEN the DS_KAGEN to set
	 */
	public boolean setDS_KAGEN(String dsKagen) {

		if (dsKagen == null)return true;

		boolean isNumber =JValidate.isNumber(dsKagen) || JValidate.isSyousu(dsKagen);

		if (!isNumber){
			JErrorMessage.show("M3807", null);
			return false;
		}
		this.DS_KAGEN = dsKagen;

		return true;
	}

	/**
	 * @param DS_JYOUGEN the DS_JYOUGEN to set
	 */
	public boolean setDS_JYOUGEN(String dsJyougen) {

		if (dsJyougen == null)return true;

		boolean isNumber =JValidate.isNumber(dsJyougen) || JValidate.isSyousu(dsJyougen);

		if (!isNumber){
			JErrorMessage.show("M3807", null);
			return false;
		}
		this.DS_JYOUGEN = dsJyougen;

		return true;
	}

	/**
	 * @param JS_KAGEN the JS_KAGEN to set
	 */
	public boolean setJS_KAGEN(String jsKagen) {

		if (jsKagen == null)return true;

		boolean isNumber =JValidate.isNumber(jsKagen) || JValidate.isSyousu(jsKagen);

		if (!isNumber){
			JErrorMessage.show("M3808", null);
			return false;
		}
		this.JS_KAGEN = jsKagen;
		return true;
	}

	/**
	 * @param tel the JS_JYOUGEN to set
	 */
	public boolean setJS_JYOUGEN(String jsJyougen) {

		if (jsJyougen == null)return true;

		boolean isNumber =JValidate.isNumber(jsJyougen) || JValidate.isSyousu(jsJyougen);

		if (!isNumber){
			JErrorMessage.show("M3808", null);
			return false;
		}
		this.JS_JYOUGEN = jsJyougen;

		return true;
	}
	/**
	 * @param TANKA_KENSIN the TANKA_KENSIN to set
	 */
	public boolean setTANKA_KENSIN(String kensaTanka) {

		if (kensaTanka == null)return true;

		this.isValidateAsDataSet = false;
		this.TANKA_KENSIN = JValidate.validateKensaTanka(kensaTanka);

		if( this.TANKA_KENSIN == null ) {
			JErrorMessage.show("M3811", null);
			return false;
		}
		return true;
	}
	/**
	 * @param tel the TANI to set
	 */
	public boolean setTANI(String tanni) {

		if (tanni == null)return true;

		this.isValidateAsDataSet = false;
		this.TANI = JValidate.validateKensaUnit(tanni);

		if( this.TANI == null ) {
			JErrorMessage.show("M3809", null);
			return false;
		}
		return true;
	}
	/**
	 * @param tel the BIKOU to set
	 */
	public boolean setBIKOU(String bikou) {

		if (bikou == null)return true;

		this.isValidateAsDataSet = false;
		this.BIKOU = JValidate.validateNote(bikou);

		if( this.BIKOU == null ) {
			JErrorMessage.show("M3813", null);
			return false;
		}
		return true;
	}
	/**
	 * @param tel the KAGEN to set
	 */
	public boolean setKAGEN(String kagen) {

		if (kagen == null)return true;

		this.isValidateAsDataSet = false;
		this.KAGEN = JValidate.validateKensaResultLimit(kagen);

		if( this.KAGEN == null ) {
			JErrorMessage.show("M3804", null);
			return false;
		}
		return true;
	}
	/**
	 * @param tel the JYOUGEN to set
	 */
	public boolean setJYOUGEN(String jyougen) {

		if (jyougen == null)return true;

		this.isValidateAsDataSet = false;
		this.JYOUGEN = JValidate.validateKensaResultLimit(jyougen);

		if( this.JYOUGEN == null ) {
			JErrorMessage.show("M3805", null);
			return false;
		}
		return true;
	}
	/**
	 * @param tel the BIKOU to set
	 */
	public boolean setKIJYUNTI_HANI(String kijyunchiHanni) {

		this.isValidateAsDataSet = false;
		this.KIJYUNTI_HANI = JValidate.validateKensaKijyunHanni(kijyunchiHanni);

		if( this.KIJYUNTI_HANI == null ) {
			JErrorMessage.show("M3810", null);
			return false;
		}
		return true;
	}
}