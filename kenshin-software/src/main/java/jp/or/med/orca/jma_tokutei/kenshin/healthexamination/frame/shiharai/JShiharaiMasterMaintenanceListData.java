package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JShiharaiMasterMaintenanceListData extends ValueObjectImpl {

	private static final long serialVersionUID = 3710680936346405726L;	// edit n.ohkubo 2014/10/01　追加
	
	private String SHIHARAI_DAIKO_NO;
	private String SHIHARAI_DAIKO_NAME;
	private String SHIHARAI_DAIKO_ZIPCD;
	private String SHIHARAI_DAIKO_ADR;
	private String SHIHARAI_DAIKO_TEL;

	/**
	 * @return the HKNJANUM
	 */
	public String getSHIHARAI_DAIKO_NO() {
		return SHIHARAI_DAIKO_NO;
	}
	/**
	 * @return the HKNJANAME
	 */
	public String getSHIHARAI_DAIKO_NAME() {
		if (SHIHARAI_DAIKO_NAME == null ) {
			SHIHARAI_DAIKO_NAME = "";
		}
		return SHIHARAI_DAIKO_NAME;
	}
	/**
	 * @return the zipcode
	 */
	public String getSHIHARAI_DAIKO_ZIPCD() {
		if (SHIHARAI_DAIKO_ZIPCD == null ) {
			SHIHARAI_DAIKO_ZIPCD = "";
		}
		return SHIHARAI_DAIKO_ZIPCD;
	}
	/**
	 * @return the ADRS
	 */
	public String getSHIHARAI_DAIKO_ADR() {
		if (SHIHARAI_DAIKO_ADR == null ) {
			SHIHARAI_DAIKO_ADR = "";
		}
		return SHIHARAI_DAIKO_ADR;
	}
	/**
	 * @return the tEL
	 */
	public String getSHIHARAI_DAIKO_TEL() {
		if (SHIHARAI_DAIKO_TEL == null ) {
			SHIHARAI_DAIKO_TEL = "";
		}
		return SHIHARAI_DAIKO_TEL;
	}


	/**
	 * @param HKNJANUM the HKNJANUM to set
	 */
	public void setSHIHARAI_DAIKO_NO(String SHIHARAI_DAIKO_NO) {

		this.SHIHARAI_DAIKO_NO = SHIHARAI_DAIKO_NO;

	}

	/**
	 * @param HKNJANAME the HKNJANAME to set
	 */
	public void setSHIHARAI_DAIKO_NAME(String SHIHARAI_DAIKO_NAME) {

		this.SHIHARAI_DAIKO_NAME = SHIHARAI_DAIKO_NAME;

	}

	/**
	 * @param zipcode the post to set
	 */
	public void setSHIHARAI_DAIKO_ZIPCD(String SHIHARAI_DAIKO_ZIPCD) {

		this.SHIHARAI_DAIKO_ZIPCD = SHIHARAI_DAIKO_ZIPCD;

	}

	/**
	 * @param ADRS the address to set
	 */
	public void setSHIHARAI_DAIKO_ADR(String SHIHARAI_DAIKO_ADR) {

		this.SHIHARAI_DAIKO_ADR = SHIHARAI_DAIKO_ADR;

	}

	/**
	 * @param tel the tEL to set
	 */
	public void setSHIHARAI_DAIKO_TEL(String SHIHARAI_DAIKO_TEL) {

		this.SHIHARAI_DAIKO_TEL = SHIHARAI_DAIKO_TEL;

	}

	// edit n.ohkubo 2014/10/01　追加　start　openswingのバグ対応で"DUMMY"を追加したので、そのゲッター/セッターがないと、画面オープン時にヌルポが発生する
	private String DUMMY;
	public String getDUMMY() {
		return DUMMY;
	}
	public void setDUMMY(String dUMMY) {
		DUMMY = dUMMY;
	}
	// edit n.ohkubo 2014/10/01　追加　start　openswingのバグ対応で"DUMMY"を追加したので、そのゲッター/セッターがないと、画面オープン時にヌルポが発生する
}