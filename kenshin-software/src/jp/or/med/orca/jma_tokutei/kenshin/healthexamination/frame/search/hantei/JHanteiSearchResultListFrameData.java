package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JHanteiSearchResultListFrameData extends ValueObjectImpl {
	
	private static final long serialVersionUID = 3467593862512060040L;	// edit n.ohkubo 2014/10/01　追加
	
	private String CHECKBOX_COLUMN;
	private String UKETUKE_ID;
	private String NAME;
	private String HIHOKENJYASYO_KIGOU;
	private String HIHOKENJYASYO_NO;
	private String KENSA_NENGAPI;
	private String BIRTHDAY;
	private String SEX;
	private String KEKA_INPUT_FLG;
	private String JYUSHIN_SEIRI_NO;
	private String HKNJANUM;
	private String SIHARAI_DAIKOU_BANGO;
	private String KANANAME;
	private String HANTEI_NENGAPI;
	private String TUTI_NENGAPI;
	private String NENDO;
	private String KOMENTO;
	private String METABO;
	private String HOKENSIDO;

	/**
	 * @return the CHECKBOX_COLUMN
	 */
	public String getCHECKBOX_COLUMN() {
		if (CHECKBOX_COLUMN == null){
			CHECKBOX_COLUMN ="";
		}
		return CHECKBOX_COLUMN;
	}

	/**
	 * @return the UKETUKE_ID
	 */
	public String getUKETUKE_ID() {
		return UKETUKE_ID;
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
	 * @return the HKNJANAME
	 */
	public String getHIHOKENJYASYO_KIGOU() {
		if (HIHOKENJYASYO_KIGOU == null ) {
			HIHOKENJYASYO_KIGOU = "";
		}
		return HIHOKENJYASYO_KIGOU;
	}
	/**
	 * @return the zipcode
	 */
	public String getHIHOKENJYASYO_NO() {
		if (HIHOKENJYASYO_NO == null ) {
			HIHOKENJYASYO_NO = "";
		}
		return HIHOKENJYASYO_NO;
	}
	/**
	 * @return the KENSA_NENGAPI
	 */
	public String getKENSA_NENGAPI() {
		return KENSA_NENGAPI;
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
// eidt s.inoue 2011/04/08
		String strSex = "";
		if (SEX != null ) {
			strSex = SEX;
		}
//		if (SEX != null ) {
//			if( SEX.equals("男性") ){
//				strSex = "1";
//			}else if( SEX.equals("女性")){
//				strSex = "2";
//			}
//		}
		return strSex;
	}
	/**
	 * @return the KEKA_INPUT_FLG
	 */
	public String getKEKA_INPUT_FLG() {
		if (KEKA_INPUT_FLG == null ){
			KEKA_INPUT_FLG="";
		}
		return KEKA_INPUT_FLG;
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
	 * @return the HKNJANUM
	 */
	public String getHKNJANUM() {
		if (HKNJANUM == null ) {
			HKNJANUM = "";
		}
		return HKNJANUM;
	}

	/**
	 * @return the SIHARAI_DAIKOU_BANGO
	 */
	public String getSIHARAI_DAIKOU_BANGO() {
		if (SIHARAI_DAIKOU_BANGO == null ) {
			SIHARAI_DAIKOU_BANGO = "";
		}
		return SIHARAI_DAIKOU_BANGO;
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
	 * @return the HANTEI_NENGAPI
	 */
	public String getHANTEI_NENGAPI() {
		if (HANTEI_NENGAPI == null ) {
			HANTEI_NENGAPI = "";
		}
		return HANTEI_NENGAPI;
	}
	/**
	 * @return the TUTI_NENGAPI
	 */
	public String getTUTI_NENGAPI() {
		if (TUTI_NENGAPI == null ) {
			TUTI_NENGAPI = "";
		}
		return TUTI_NENGAPI;
	}
	/**
	 * @return the NENDO
	 */
	public String getNENDO() {
		if (NENDO == null ) {
			NENDO = "";
		}
		return NENDO;
	}
	/**
	 * @return the KOMENTO
	 */
	public String getKOMENTO() {
		if (KOMENTO == null ) {
			KOMENTO = "";
		}
		return KOMENTO;
	}
	/**
	 * @return the KOMENTO
	 */
	public String getMETABO() {
		if (METABO == null ) {
			METABO = "";
		}
		return METABO;
	}
	/**
	 * @return the KOMENTO
	 */
	public String getHOKENSIDO() {
		if (HOKENSIDO == null ) {
			HOKENSIDO = "";
		}
		return HOKENSIDO;
	}

	/**
	 * @param CHECKBOX_COLUMN the CHECKBOX_COLUMN to set
	 */
	public void setCHECKBOX_COLUMN(String CHECKBOX_COLUMN) {
		this.CHECKBOX_COLUMN = CHECKBOX_COLUMN;
	}
	/**
	 * @param UKETUKE_ID the UKETUKE_ID to set
	 */
	public void setUKETUKE_ID(String UKETUKE_ID) {

		this.UKETUKE_ID = UKETUKE_ID;

	}
	/**
	 * @param NAME the NAME to set
	 */
	public void setNAME(String NAME) {

		this.NAME = NAME;

	}

	/**
	 * @param HKNJANAME the HKNJANAME to set
	 */
	public void setHIHOKENJYASYO_KIGOU(String HIHOKENJYASYO_KIGOU) {

		this.HIHOKENJYASYO_KIGOU = HIHOKENJYASYO_KIGOU;

	}

	/**
	 * @param zipcode the post to set
	 */
	public void setHIHOKENJYASYO_NO(String HIHOKENJYASYO_NO) {

		this.HIHOKENJYASYO_NO = HIHOKENJYASYO_NO;

	}

	/**
	 * @param zipcode the post to set
	 */
	public void setKENSA_NENGAPI(String KENSA_NENGAPI) {

		this.KENSA_NENGAPI = KENSA_NENGAPI;

	}

	/**
	 * @param BIRTHDAY the BIRTHDAY to set
	 */
	public void setBIRTHDAY(String BIRTHDAY) {

		this.BIRTHDAY = BIRTHDAY;

	}
	/**
	 * @param BIRTHDAY the BIRTHDAY to set
	 */
	public void setSEX(String SEX) {

		this.SEX = SEX;

	}
	/**
	 * @return the KEKA_INPUT_FLG
	 */
	public void setKEKA_INPUT_FLG(String KEKA_INPUT_FLG) {
		this.KEKA_INPUT_FLG = KEKA_INPUT_FLG;
	}
	/**
	 * @param JYUSHIN_SEIRI_NO the JYUSHIN_SEIRI_NO to set
	 */
	public void setJYUSHIN_SEIRI_NO(String JYUSHIN_SEIRI_NO) {

		this.JYUSHIN_SEIRI_NO = JYUSHIN_SEIRI_NO;

	}
	/**
	 * @param HKNJANUM the HKNJANUM to set
	 */
	public void setHKNJANUM(String HKNJANUM) {

		this.HKNJANUM = HKNJANUM;

	}
	/**
	 * @param SIHARAI_DAIKOU_BANGO the SIHARAI_DAIKOU_BANGO to set
	 */
	public void setSIHARAI_DAIKOU_BANGO(String SIHARAI_DAIKOU_BANGO) {

		this.SIHARAI_DAIKOU_BANGO = SIHARAI_DAIKOU_BANGO;

	}
	/**
	 * @param KANANAME the KANANAME to set
	 */
	public void setKANANAME(String KANANAME) {

		this.KANANAME = KANANAME;

	}
	/**
	 * @param HKNJANUM the HKNJANUM to set
	 */
	public void setHANTEI_NENGAPI(String HANTEI_NENGAPI) {

		this.HANTEI_NENGAPI = HANTEI_NENGAPI;

	}
	/**
	 * @param TUTI_NENGAPI the TUTI_NENGAPI to set
	 */
	public void setTUTI_NENGAPI(String TUTI_NENGAPI) {

		this.TUTI_NENGAPI = TUTI_NENGAPI;

	}
	/**
	 * @param NENDO the NENDO to set
	 */
	public void setNENDO(String NENDO) {

		this.NENDO = NENDO;

	}
	/**
	 * @param KOMENTO the KOMENTO to set
	 */
	public void setKOMENTO(String KOMENTO) {

		this.KOMENTO = KOMENTO;

	}

	/**
	 * @param KOMENTO the KOMENTO to set
	 */
	public void setMETABO(String METABO) {

		this.METABO = METABO;

	}
	/**
	 * @param KOMENTO the KOMENTO to set
	 */
	public void setHOKENSIDO(String HOKENSIDO) {

		this.HOKENSIDO = HOKENSIDO;

	}
	
	// edit n.ohkubo 2014/10/01　追加　キーは「xxxx2」だが、中身は2が付いていなやつになる（変数の宣言がないとヌルポになる。宣言だけだと「使われていないワーニング」になるので、セッターでむりやり使用している）
	private String KENSA_NENGAPI2;
	public String getKENSA_NENGAPI2() {
		return getKENSA_NENGAPI();
	}
	public void setKENSA_NENGAPI2(String KENSA_NENGAPI2) {
		this.KENSA_NENGAPI2 = KENSA_NENGAPI2;
		setKENSA_NENGAPI(this.KENSA_NENGAPI2);
	}
}