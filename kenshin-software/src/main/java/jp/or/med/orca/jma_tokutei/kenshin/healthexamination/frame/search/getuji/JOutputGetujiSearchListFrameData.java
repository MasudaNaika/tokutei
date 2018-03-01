package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji;

import java.sql.Date;
import java.util.ArrayList;

import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7Directory;

import org.openswing.swing.message.receive.java.ValueObjectImpl;
import org.openswing.swing.table.columns.client.CheckBoxColumn;

public class JOutputGetujiSearchListFrameData extends ValueObjectImpl {
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
	private String HKNJYA_LIMITDATE_START;
	private String HKNJYA_LIMITDATE_END;
	private String HANTEI_NENGAPI;
	private String TUTI_NENGAPI;
	private String NENDO;
	private String TANKA_GOUKEI;
	private String MADO_FUTAN_GOUKEI;
	private String SEIKYU_KINGAKU;
	private String UPDATE_TIMESTAMP;
	// 月次
	private String SHUBETU_CODE;
	private String JISI_KBN;
	private String OUTPUT_HL7;
	// edit s.inoue 2009/09/18
	private ArrayList<JOutputHL7Directory> JOutputDirs
		= new ArrayList<JOutputHL7Directory>();


	/**
	 * @return the CHECKBOX_COLUMN
	 */
	public String getCHECKBOX_COLUMN() {
		if (CHECKBOX_COLUMN == null)
			CHECKBOX_COLUMN ="";
		return CHECKBOX_COLUMN;
	}

	public ArrayList<JOutputHL7Directory> getJOutputDir(){
		return JOutputDirs;
	}
	public void setJOutputDir(JOutputHL7Directory value){
		this.JOutputDirs.add(value);
	}

	/**
	 * @return the UKETUKE_ID
	 */
	public String getUKETUKE_ID() {
		if (UKETUKE_ID == null ) {
			UKETUKE_ID = "";
		}
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
		String strSex = "";
		if (SEX != null ) {
			if( SEX.equals("1") ){
				strSex = "男";
			}else if( SEX.equals("2")){
				strSex = "女";
			}
		}
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
	 * @return the HKNJYA_LIMITDATE_START
	 */
	public String getHKNJYA_LIMITDATE_START() {
		if (HKNJYA_LIMITDATE_START == null ) {
			HKNJYA_LIMITDATE_START = "";
		}
		return HKNJYA_LIMITDATE_START;
	}

	/**
	 * @return the HKNJYA_LIMITDATE_END
	 */
	public String getHKNJYA_LIMITDATE_END() {
		if (HKNJYA_LIMITDATE_END == null ) {
			HKNJYA_LIMITDATE_END = "";
		}
		return HKNJYA_LIMITDATE_END;
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
	 * @return the TANKA_GOUKEI
	 */
	public String getTANKA_GOUKEI() {
		if (TANKA_GOUKEI == null ) {
			TANKA_GOUKEI = "";
		}
		return TANKA_GOUKEI;
	}
	/**
	 * @return the MADO_FUTAN_GOUKEI
	 */
	public String getMADO_FUTAN_GOUKEI() {
		if (MADO_FUTAN_GOUKEI == null ) {
			MADO_FUTAN_GOUKEI = "";
		}
		return MADO_FUTAN_GOUKEI;
	}
	/**
	 * @return the SEIKYU_KINGAKU
	 */
	public String getSEIKYU_KINGAKU() {
		if (SEIKYU_KINGAKU == null ) {
			SEIKYU_KINGAKU = "";
		}
		return SEIKYU_KINGAKU;
	}
	/**
	 * @return the UPDATE_TIMESTAMP
	 */
	public String getUPDATE_TIMESTAMP() {
		if (UPDATE_TIMESTAMP == null ) {
			UPDATE_TIMESTAMP = "";
		}
		return UPDATE_TIMESTAMP;
	}

	/**
	 * @return the SHUBETU_CODE
	 */
	public String getSHUBETU_CODE() {
		if (SHUBETU_CODE == null ) {
			SHUBETU_CODE = "";
		}
		return SHUBETU_CODE;
	}

	// eidt s.inoue 2011/04/05
	/**
	 * @return the JISI_KBN
	 */
	public String getJISI_KBN() {
		// eidt s.inoue 2011/05/28
//		if (NITIJI_FLG != null ) {
//			if( NITIJI_FLG.equals("0") ){
//				NITIJI_FLG = "未";
//			}else if( NITIJI_FLG.equals("1")){
//				NITIJI_FLG = "済";
//			}
//		}
		if (JISI_KBN == null ) {
			JISI_KBN = "";
		}
		return JISI_KBN;
	}
	/**
	 * @return the OUTPUT_HL7
	 */
	public String getOUTPUT_HL7() {
		if (OUTPUT_HL7 == null ) {
			OUTPUT_HL7 = "";
		}
		return OUTPUT_HL7;
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
	 * @param HKNJYA_LIMITDATE_START the HKNJYA_LIMITDATE_START to set
	 */
	public void setHKNJYA_LIMITDATE_START(String HKNJYA_LIMITDATE_START) {

		this.HKNJYA_LIMITDATE_START = HKNJYA_LIMITDATE_START;

	}

	/**
	 * @param HKNJYA_LIMITDATE_END the HKNJYA_LIMITDATE_END to set
	 */
	public void setHKNJYA_LIMITDATE_END(String HKNJYA_LIMITDATE_END) {

		this.HKNJYA_LIMITDATE_END = HKNJYA_LIMITDATE_END;

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
	 * @param TANKA_GOUKEI the TANKA_GOUKEI to set
	 */
	public void setTANKA_GOUKEI(String TANKA_GOUKEI) {

		this.TANKA_GOUKEI = TANKA_GOUKEI;

	}
	/**
	 * @param MADO_FUTAN_GOUKEI the MADO_FUTAN_GOUKEI to set
	 */
	public void setMADO_FUTAN_GOUKEI(String MADO_FUTAN_GOUKEI) {

		this.MADO_FUTAN_GOUKEI = MADO_FUTAN_GOUKEI;

	}
	/**
	 * @param SEIKYU_KINGAKU the SEIKYU_KINGAKU to set
	 */
	public void setSEIKYU_KINGAKU(String SEIKYU_KINGAKU) {

		this.SEIKYU_KINGAKU = SEIKYU_KINGAKU;

	}
	/**
	 * @param UPDATE_TIMESTAMP the UPDATE_TIMESTAMP to set
	 */
	public void setUPDATE_TIMESTAMP(String UPDATE_TIMESTAMP) {

		this.UPDATE_TIMESTAMP = UPDATE_TIMESTAMP;

	}

	/**
	 * @param SHUBETU_CODE the SHUBETU_CODE to set
	 */
	public void setSHUBETU_CODE(String SHUBETU_CODE) {

		this.SHUBETU_CODE = SHUBETU_CODE;

	}

	/**
	 * @param JISI_KBN the JISI_KBN to set
	 */
	public void setJISI_KBN(String JISI_KBN) {

		this.JISI_KBN = JISI_KBN;

	}
	/**
	 * @param OUTPUT_HL7 the OUTPUT_HL7 to set
	 */
	public void setOUTPUT_HL7(String OUTPUT_HL7) {

		this.OUTPUT_HL7 = OUTPUT_HL7;

	}
}