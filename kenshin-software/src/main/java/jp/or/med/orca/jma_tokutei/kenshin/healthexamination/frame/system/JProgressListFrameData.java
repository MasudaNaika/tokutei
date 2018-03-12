package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JProgressListFrameData extends ValueObjectImpl {
	
	private String HKNJANUM;
	private String JYUSHIN_SEIRI_NO;
	private String HIHOKENJYASYO_KIGOU;
	private String HIHOKENJYASYO_NO;
	private String KENSA_NENGAPI;
	private Integer BIRTHDAY;
	private String SEX;
	
	private String KANANAME;
	private String KEKA_FLG;
	private String HANTEI_FLG;
	private String NITIJI_FLG;
	private String GETUJI_FLG;
	
	public String getHKNJANUM() {
		return HKNJANUM;
	}
	public void setHKNJANUM(String hKNJANUM) {
		HKNJANUM = hKNJANUM;
	}
	public String getJYUSHIN_SEIRI_NO() {
		return JYUSHIN_SEIRI_NO;
	}
	public void setJYUSHIN_SEIRI_NO(String jYUSHIN_SEIRI_NO) {
		JYUSHIN_SEIRI_NO = jYUSHIN_SEIRI_NO;
	}
	public String getHIHOKENJYASYO_KIGOU() {
		return HIHOKENJYASYO_KIGOU;
	}
	public void setHIHOKENJYASYO_KIGOU(String hIHOKENJYASYO_KIGOU) {
		HIHOKENJYASYO_KIGOU = hIHOKENJYASYO_KIGOU;
	}
	public String getHIHOKENJYASYO_NO() {
		return HIHOKENJYASYO_NO;
	}
	public void setHIHOKENJYASYO_NO(String hIHOKENJYASYO_NO) {
		HIHOKENJYASYO_NO = hIHOKENJYASYO_NO;
	}
	public String getKENSA_NENGAPI() {
		return KENSA_NENGAPI;
	}
	public void setKENSA_NENGAPI(String kENSA_NENGAPI) {
		KENSA_NENGAPI = kENSA_NENGAPI;
	}
	public Integer getBIRTHDAY() {
		return BIRTHDAY;
	}
	public void setBIRTHDAY(Integer bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getHANTEI_FLG() {
		return HANTEI_FLG;
	}
	public void setHANTEI_FLG(String HANTEI_FLG) {
		this.HANTEI_FLG = HANTEI_FLG;
	}
	public String getNITIJI_FLG() {
		return NITIJI_FLG;
	}
	public void setNITIJI_FLG(String NITIJI_FLG) {
		this.NITIJI_FLG = NITIJI_FLG;
	}
	public String getGETUJI_FLG() {
		return GETUJI_FLG;
	}
	public void setGETUJI_FLG(String GETUJI_FLG) {
		this.GETUJI_FLG = GETUJI_FLG;
	}
	/**
	 * @return the ERROR_PLACE
	 */
	public String getKANANAME() {
		if (KANANAME == null ) {
			KANANAME = "";
		}
		return KANANAME;
	}

	public String getKEKA_FLG() {
		if (KEKA_FLG == null)
			KEKA_FLG = "";

		return KEKA_FLG;
	}
	/**
	 * @param ERROR_PLACE to set
	 */
	public void setKANANAME(String KANANAME) {

		this.KANANAME = KANANAME;

	}

	public void setKEKA_FLG(String KEKA_FLG) {
		this.KEKA_FLG = KEKA_FLG;
	}
}