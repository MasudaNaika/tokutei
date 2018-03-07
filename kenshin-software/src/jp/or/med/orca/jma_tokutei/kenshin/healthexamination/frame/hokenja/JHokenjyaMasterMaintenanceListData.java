package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JHokenjyaMasterMaintenanceListData extends ValueObjectImpl {
	private String HKNJANUM_M;
	private String HKNJANAME;
	private String POST;
	private String ADRS;
//	private String BANTI;
	private String TEL;
//	private String HonninGairai = new String("");
//	private String KazokuGairai = new String("");
//	private String HonninNyuin = new String("");
//	private String KazokuNyuin = new String("");
//	private String ItakuKubun = new String("");
//	private String KihonTanka = new String("");
//	private String HinketsuTanka = new String("");
//	private String SindenzuTanka = new String("");
//	private String GanteiTanka = new String("");
//	private String NingenDocTanka = new String("");
//	private String HinketsuCode = new String("");
//	private String ShindenzuCode = new String("");
//	private String GanteiCode = new String("");
//	private String HknjyaHistoryNumber = new String("");
//	private String YukoukigenFrom = new String("");
//	private String YukoukigenTo = new String("");
//	private String YukouFlg = new String("");
//	private String HanteiTanka = new String("");

	/**
	 * @return the HKNJANUM_M
	 */
	public String getHKNJANUM_M() {
		return HKNJANUM_M;
	}
	/**
	 * @return the HKNJANAME
	 */
	public String getHKNJANAME() {
		if (HKNJANAME == null ) {
			HKNJANAME = "";
		}
		return HKNJANAME;
	}
	/**
	 * @return the zipcode
	 */
	public String getPOST() {
		if (POST == null ) {
			POST = "";
		}
		return POST;
	}
	/**
	 * @return the ADRS
	 */
	public String getADRS() {
		if (ADRS == null ) {
			ADRS = "";
		}
		return ADRS;
	}
//	/**
//	 * @return the BANTI
//	 */
//	public String getBANTI() {
//		return BANTI;
//	}
//	/**
//	 * @return the tEL
//	 */
	public String getTEL() {
		if (TEL == null ) {
			TEL = "";
		}
		return TEL;
	}
//	/**
//	 * @return the honninGairai
//	 */
//	public String getHonninGairai() {
//		return HonninGairai;
//	}
//	/**
//	 * @return the kazokuGairai
//	 */
//	public String getKazokuGairai() {
//		return KazokuGairai;
//	}
//	/**
//	 * @return the honninNyuin
//	 */
//	public String getHonninNyuin() {
//		return HonninNyuin;
//	}
//	/**
//	 * @return the kazokuNyuin
//	 */
//	public String getKazokuNyuin() {
//		return KazokuNyuin;
//	}
//	/**
//	 * @return the itakuKubun
//	 */
//	public String getItakuKubun() {
//		return ItakuKubun;
//	}
//	/**
//	 * @return the kihonTanka
//	 */
//	public String getKihonTanka() {
//		return KihonTanka;
//	}
//	/**
//	 * @return the hinketsuTanka
//	 */
//	public String getHinketsuTanka() {
//		return HinketsuTanka;
//	}
//	/**
//	 * @return the sindenzuTanka
//	 */
//	public String getSindenzuTanka() {
//		return SindenzuTanka;
//	}
//	/**
//	 * @return the ganteiTanka
//	 */
//	public String getGanteiTanka() {
//		return GanteiTanka;
//	}
//	/**
//	 * @return the NingenDocTanka
//	 */
//	public String getNingenDocTanka() {
//		return NingenDocTanka;
//	}
//
//	/**
//	 * @return the HanteiTanka
//	 */
//	public String getTankaHantei() {
//		return HanteiTanka;
//	}
//	/**
//	 * @return the hinketsuCode
//	 */
//	public String getHinketsuCode() {
//		return HinketsuCode;
//	}
//
//	/**
//	 * @return the shindenzuCode
//	 */
//	public String getShindenzuCode() {
//		return ShindenzuCode;
//	}
//	/**
//	 * @return the ganteiCode
//	 */
//	public String getGanteiCode() {
//		return GanteiCode;
//	}
//
//	/**
//	 * @return the HknjyaHistoryNumber
//	 */
//	public String getHknjyaHistoryNumber() {
//		return HknjyaHistoryNumber;
//	}
//	/**
//	 * @return the YukoukigenFrom
//	 */
//	public String getYukouKigenFrom() {
//		return YukoukigenFrom;
//	}
//	/**
//	 * @return the YukoukigenTo
//	 */
//	public String getYukouKigenTo() {
//		return YukoukigenTo;
//	}
//	/**
//	 * @return the YukouFlg
//	 */
//	public String getYukouFlg() {
//		return YukouFlg;
//	}

/* set */
//	/**
//	 * @param hknjyaHistoryNumber the hokenjyaNumber to set
//	 */
//	public boolean setHknjyaHistoryNumber(String hknjyahistoryNumber) {
//		this.isValidationAsDataSet = false;
//		this.HknjyaHistoryNumber = hknjyahistoryNumber;
//		return true;
//	}
//
//	/**
//	 * @param yukouKigenFrom the yukouKigenFrom to set
//	 */
//	public boolean setYukouKigenFrom(String yukouKigenFrom) {
//		this.isValidationAsDataSet = false;
//		this.YukoukigenFrom = yukouKigenFrom;
//		return true;
//	}
//
//	/**
//	 * @param yukouKigenTo the yukouKigenTo to set
//	 */
//	public boolean setYukouKigenTo(String yukouKigenTo) {
//		this.isValidationAsDataSet = false;
//		this.YukoukigenTo = yukouKigenTo;
//		return true;
//	}
//
//	/**
//	 * @param yukouFlg is "0" or "1"
//	 */
//	public boolean setYukouFlg(String yukouFlg) {
//		isValidationAsDataSet = false;
//		this.YukouFlg = yukouFlg;
//		return true;
//	}

	/**
	 * @param HKNJANUM_M the HKNJANUM_M to set
	 */
	public void setHKNJANUM_M(String HKNJANUM_M) {

		this.HKNJANUM_M = HKNJANUM_M;

	}

	/**
	 * @param HKNJANAME the HKNJANAME to set
	 */
	public void setHKNJANAME(String HKNJANAME) {

		this.HKNJANAME = HKNJANAME;

	}

	/**
	 * @param zipcode the post to set
	 */
	public void setPOST(String POST) {

		this.POST = POST;

	}

	/**
	 * @param ADRS the address to set
	 */
	public void setADRS(String ADRS) {

		this.ADRS = ADRS;

	}

//	/**
//	 * @param chiban the chiban to set
//	 */
//	public void setBANTI(String BANTI) {
//
//		this.BANTI = BANTI;
//
//	}

	/**
	 * @param tel the tEL to set
	 */
	public void setTEL(String tel) {

		this.TEL = tel;

	}

//	/**
//	 * @param honninGairai the honninGairai to set
//	 */
//	public void setHonninGairai(String honninGairai) {
//
//		this.HonninGairai = honninGairai;
//
//	}
//
//	/**
//	 * @param kazokuGairai the kazokuGairai to set
//	 */
//	public void setKazokuGairai(String kazokuGairai) {
//
//		this.KazokuGairai = kazokuGairai;
//
//	}
//
//	/**
//	 * @param honninNyuin the honninNyuin to set
//	 */
//	public void setHonninNyuin(String honninNyuin) {
//
//		this.HonninNyuin = honninNyuin;
//
//	}
//
//	/**
//	 * @param kazokuNyuin the kazokuNyuin to set
//	 */
//	public void setKazokuNyuin(String kazokuNyuin) {
//
//		this.KazokuNyuin = kazokuNyuin;
//
//	}
//
//	/**
//	 * @param itakuKubun the itakuKubun to set
//	 */
//	public void setItakuKubun(String itakuKubun) {
//
//		this.ItakuKubun = itakuKubun;
//
//	}
//
//	/**
//	 * @param itakuKubun the itakuKubun to set
//	 */
//	public void setItakuKubunCode(String itakuKubunCode) {
//
//		this.ItakuKubun = itakuKubunCode;
//
//	}
//
//	/**
//	 * @param kihonTanka the kihonTanka to set
//	 */
//	public void setKihonTanka(String kihonTanka) {
//
//		this.KihonTanka = kihonTanka;
//
//	}
//
//	/**
//	 * @param hinketsuTanka the hinketsuTanka to set
//	 */
//	public void setHinketsuTanka(String hinketsuTanka) {
//
//		this.HinketsuTanka = hinketsuTanka;
//
//	}
//
//	/**
//	 * @param sindenzuTanka the sindenzuTanka to set
//	 */
//	public void setSindenzuTanka(String sindenzuTanka) {
//
//		this.SindenzuTanka = sindenzuTanka;
//
//	}
//
//	/**
//	 * @param ganteiTanka the ganteiTanka to set
//	 */
//	public void setGanteiTanka(String ganteiTanka) {
//
//		this.GanteiTanka = ganteiTanka;
//
//	}
//
//	/**
//	 * @param ganteiTanka the ningenDocTanka to set
//	 */
//	public void setNingenDocTanka(String ningenDocTanka) {
//
//		this.NingenDocTanka = ningenDocTanka;
//
//	}
//
//	// add s.inoue 2009/10/01
//	/**
//	 * @param Tanka the setTankaHantei to set
//	 */
//	public void setTankaHantei(String hanteiTanka) {
//
//		this.HanteiTanka = hanteiTanka;
//
//	}
//	/**
//	 * @param isValidationAsDataSet the isValidationAsDataSet to set
//	 */
//	public void setValidationAsDataSet() {
//		this.isValidationAsDataSet = true;
//	}
//
//	/**
//	 * @param hinketsuCode the hinketsuCode to set
//	 */
//	public void setHinketsuCode(String hinketsuCode) {
//
//		this.HinketsuCode = hinketsuCode;
//
//	}
//
//	/**
//	 * @param shindenzuCode the shindenzuCode to set
//	 */
//	public void setShindenzuCode(String shindenzuCode) {
//
//		this.ShindenzuCode = shindenzuCode;
//
//	}
//
//	/**
//	 * @param ganteiCode the ganteiCode to set
//	 */
//	public void setGanteiCode(String ganteiCode) {
//
//		this.GanteiCode = ganteiCode;
//
//	}
}