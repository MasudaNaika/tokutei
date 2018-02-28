package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JKeinenMasterMaintenanceListData extends ValueObjectImpl {
	private String NAYOSE_NO;
	private String JYUSHIN_SEIRI_NO;
	private String KANANAME;
	private String NAME;
	private String BIRTHDAY;
	private String SEX;
	private String HOME_ADRS;
	private String HIHOKENJYASYO_KIGOU;
	private String HIHOKENJYASYO_NO;
	private String UPDATE_TIMESTAMP;
	private String UKETUKE_ID;

	/**
	 * @return the NAYOSE_NO
	 */
	public String getNAYOSE_NO() {
		if (NAYOSE_NO == null ) {
			NAYOSE_NO = "";
		}
		return NAYOSE_NO;
	}
	/**
	 * @return the JYUSHIN_SEIRI_NO
	 */
	public String getJYUSHIN_SEIRI_NO() {
		if (JYUSHIN_SEIRI_NO == null ) {
			JYUSHIN_SEIRI_NO = "";
		}
		return JYUSHIN_SEIRI_NO;
	}
	/**
	 * @return the KANANAME
	 */
	public String getKANANAME() {
		if (KANANAME == null ) {
			KANANAME = "";
		}
		return KANANAME;
	}
	/**
	 * @return the NAME
	 */
	public String getNAME() {
		if (NAME == null ) {
			NAME = "";
		}
		return NAME;
	}
	/**
	 * @return the BIRTHDAY
	 */
	public String getBIRTHDAY() {
		if (BIRTHDAY == null ) {
			BIRTHDAY = "";
		}
		return BIRTHDAY;
	}
	/**
	 * @return the SEX
	 */
	public String getSEX() {
		if (SEX == null ) {
			SEX = "";
		}
		return SEX;
	}
	/**
	 * @return the HOME_ADRS
	 */
	public String getHOME_ADRS() {
		if (HOME_ADRS == null ) {
			HOME_ADRS = "";
		}
		return HOME_ADRS;
	}
	/**
	 * @return the HIHOKENJYASYO_KIGOU
	 */
	public String getHIHOKENJYASYO_KIGOU() {
		if (HIHOKENJYASYO_KIGOU == null ) {
			HIHOKENJYASYO_KIGOU = "";
		}
		return HIHOKENJYASYO_KIGOU;
	}
	/**
	 * @return the HIHOKENJYASYO_NO
	 */
	public String getHIHOKENJYASYO_NO() {
		if (HIHOKENJYASYO_NO == null ) {
			HIHOKENJYASYO_NO = "";
		}
		return HIHOKENJYASYO_NO;
	}
	/**
	 * @return the UPDATETIME
	 */
	public String getUPDATE_TIMESTAMP() {
		if (UPDATE_TIMESTAMP == null ) {
			UPDATE_TIMESTAMP = "";
		}
		return UPDATE_TIMESTAMP;
	}
	/**
	 * @return the UPDATETIME
	 */
	public String getUKETUKE_ID() {
		if (UKETUKE_ID == null ) {
			UKETUKE_ID = "";
		}
		return UKETUKE_ID;
	}
	/**
	 * @param NAYOSE_NO the NAYOSE_NO to set
	 */
	public void setNAYOSE_NO(String NAYOSE_NO) {
		this.NAYOSE_NO = NAYOSE_NO;
	}

	/**
	 * @param JYUSHIN_SEIRI_NO the JYUSHIN_SEIRI_NO to set
	 */
	public void setJYUSHIN_SEIRI_NO(String JYUSHIN_SEIRI_NO) {
		this.JYUSHIN_SEIRI_NO = JYUSHIN_SEIRI_NO;
	}

	/**
	 * @param KANANAME the KANANAME to set
	 */
	public void setKANANAME(String KANANAME) {
		this.KANANAME = KANANAME;
	}

	/**
	 * @param NAME the NAME to set
	 */
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	/**
	 * @param BIRTHDAY the BIRTHDAY to set
	 */
	public void setBIRTHDAY(String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}

	/**
	 * @param SEX the SEX to set
	 */
	public void setSEX(String SEX) {
		this.SEX = SEX;
	}
	/**
	 * @param HOME_ADRS the HOME_ADRS to set
	 */
	public void setHOME_ADRS(String HOME_ADRS) {
		this.HOME_ADRS = HOME_ADRS;
	}
	/**
	 * @param HIHOKENJYASYO_KIGOU the HIHOKENJYASYO_KIGOU to set
	 */
	public void setHIHOKENJYASYO_KIGOU(String HIHOKENJYASYO_KIGOU) {
		this.HIHOKENJYASYO_KIGOU = HIHOKENJYASYO_KIGOU;
	}
	/**
	 * @param HIHOKENJYASYO_NO the HIHOKENJYASYO_NO to set
	 */
	public void setHIHOKENJYASYO_NO(String HIHOKENJYASYO_NO) {
		this.HIHOKENJYASYO_NO = HIHOKENJYASYO_NO;
	}
	/**
	 * @param UPDATE_TIMESTAMP the UPDATE_TIMESTAMP to set
	 */
	public void setUPDATE_TIMESTAMP(String UPDATE_TIMESTAMP) {
		this.UPDATE_TIMESTAMP = UPDATE_TIMESTAMP;
	}
	/**
	 * @param UKETUKE_ID the UKETUKE_ID to set
	 */
	public void setUKETUKE_ID(String UKETUKE_ID) {
		this.UKETUKE_ID = UKETUKE_ID;
	}
}