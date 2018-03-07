package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JHokenjyaMasterMaintenanceEditFrameData {
	private String HokenjyaNumber = new String("");
	private String HokenjyaName = new String("");
	private String Zipcode = new String("");
	private String Address = new String("");
	private String Chiban = new String("");
	private String TEL = new String("");
	private String Code = new String("");
	private String HonninGairai = new String("");
	private String KazokuGairai = new String("");
	private String HonninNyuin = new String("");
	private String KazokuNyuin = new String("");
	private String ItakuKubun = new String("");
	private String KihonTanka = new String("");
	private String HinketsuTanka = new String("");
	private String SindenzuTanka = new String("");
	private String GanteiTanka = new String("");
	private String NingenDocTanka = new String("");
	private String HinketsuCode = new String("");
	private String ShindenzuCode = new String("");
	private String GanteiCode = new String("");

	// edit s.inoue 2010/01/12
	private String HknjyaHistoryNumber = new String("");
	private String YukoukigenFrom = new String("");
	private String YukoukigenTo = new String("");
	private String YukouFlg = new String("");

	// add s.inoue 2009/10/01
	private String HanteiTanka = new String("");
	private boolean isValidationAsDataSet = false;

	/**
	 * @return the isValidationAsDataSet
	 */
	public boolean isValidationAsDataSet() {
		return isValidationAsDataSet;
	}
	/**
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return HokenjyaNumber;
	}
	/**
	 * @return the hokenjyaName
	 */
	public String getHokenjyaName() {
		/* Added 2008/03/13 ŽáŒŽ  */
		/* --------------------------------------------------- */
		if (HokenjyaName == null ) {
			HokenjyaName = "";
		}
		/* --------------------------------------------------- */
		return HokenjyaName;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		/* Added 2008/03/13 ŽáŒŽ  */
		/* --------------------------------------------------- */
		if (Zipcode == null ) {
			Zipcode = "";
		}
		/* --------------------------------------------------- */

		return Zipcode;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		/* Added 2008/03/13 ŽáŒŽ  */
		/* --------------------------------------------------- */
		if (Address == null ) {
			Address = "";
		}
		/* --------------------------------------------------- */
		return Address;
	}
	/**
	 * @return the chiban
	 */
	public String getChiban() {
		return Chiban;
	}
	/**
	 * @return the tEL
	 */
	public String getTEL() {
		/* Added 2008/03/13 ŽáŒŽ  */
		/* --------------------------------------------------- */
		if (TEL == null ) {
			TEL = "";
		}
		/* --------------------------------------------------- */

		return TEL;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return Code;
	}
	/**
	 * @return the honninGairai
	 */
	public String getHonninGairai() {
		return HonninGairai;
	}
	/**
	 * @return the kazokuGairai
	 */
	public String getKazokuGairai() {
		return KazokuGairai;
	}
	/**
	 * @return the honninNyuin
	 */
	public String getHonninNyuin() {
		return HonninNyuin;
	}
	/**
	 * @return the kazokuNyuin
	 */
	public String getKazokuNyuin() {
		return KazokuNyuin;
	}
	/**
	 * @return the itakuKubun
	 */
	public String getItakuKubun() {
		return ItakuKubun;
	}
	/**
	 * @return the kihonTanka
	 */
	public String getKihonTanka() {
		return KihonTanka;
	}
	/**
	 * @return the hinketsuTanka
	 */
	public String getHinketsuTanka() {
		return HinketsuTanka;
	}
	/**
	 * @return the sindenzuTanka
	 */
	public String getSindenzuTanka() {
		return SindenzuTanka;
	}
	/**
	 * @return the ganteiTanka
	 */
	public String getGanteiTanka() {
		return GanteiTanka;
	}
	/**
	 * @return the NingenDocTanka
	 */
	public String getNingenDocTanka() {
		return NingenDocTanka;
	}

	// add s.inoue 2009/10/01
	/**
	 * @return the HanteiTanka
	 */
	public String getTankaHantei() {
		return HanteiTanka;
	}
	/**
	 * @return the hinketsuCode
	 */
	public String getHinketsuCode() {
		return HinketsuCode;
	}

	/**
	 * @return the shindenzuCode
	 */
	public String getShindenzuCode() {
		return ShindenzuCode;
	}
	/**
	 * @return the ganteiCode
	 */
	public String getGanteiCode() {
		return GanteiCode;
	}

	// add s.inoue 2010/01/12
	/**
	 * @return the HknjyaHistoryNumber
	 */
	public String getHknjyaHistoryNumber() {
		return HknjyaHistoryNumber;
	}
	/**
	 * @return the YukoukigenFrom
	 */
	public String getYukouKigenFrom() {
		return YukoukigenFrom;
	}
	/**
	 * @return the YukoukigenTo
	 */
	public String getYukouKigenTo() {
		return YukoukigenTo;
	}
	/**
	 * @return the YukouFlg
	 */
	public String getYukouFlg() {
		return YukouFlg;
	}

	// add s.inoue 2010/01/12
	/**
	 * @param hknjyaHistoryNumber the hokenjyaNumber to set
	 */
	public boolean setHknjyaHistoryNumber(String hknjyahistoryNumber) {
		this.isValidationAsDataSet = false;
		this.HknjyaHistoryNumber = JValidate.validateHokenjyaHistoryNumber(hknjyahistoryNumber);

		if( this.HknjyaHistoryNumber == null ) {
			JErrorMessage.show("M3156", null);
			return false;
		}

		return true;
	}

	/**
	 * @param yukouKigenFrom the yukouKigenFrom to set
	 */
	public boolean setYukouKigenFrom(String yukouKigenFrom) {
		this.isValidationAsDataSet = false;
		this.YukoukigenFrom = JValidate.validateDate(yukouKigenFrom,true,true);

		if( this.YukoukigenFrom == null ) {
			JErrorMessage.show("M3153", null);
			return false;
		}

		return true;
	}

	/**
	 * @param yukouKigenTo the yukouKigenTo to set
	 */
	public boolean setYukouKigenTo(String yukouKigenTo) {
		this.isValidationAsDataSet = false;
		this.YukoukigenTo = JValidate.validateDate(yukouKigenTo,true,true);

		if( this.YukoukigenTo == null ) {
			JErrorMessage.show("M3154", null);
			return false;
		}

		return true;
	}

	/**
	 * @param yukouFlg is "0" or "1"
	 */
	public boolean setYukouFlg(String yukouFlg) {
		isValidationAsDataSet = false;
		this.YukouFlg = JValidate.validateYukouFlg(yukouFlg);

		if( this.YukouFlg == null ) {
			JErrorMessage.show("M3155", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidationAsDataSet = false;
		this.HokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);

		if( this.HokenjyaNumber == null ) {
			JErrorMessage.show("M3101", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hokenjyaName the hokenjyaName to set
	 */
	public boolean setHokenjyaName(String hokenjyaName) {
		this.isValidationAsDataSet = false;
		this.HokenjyaName = JValidate.validateHokenjyaName(hokenjyaName);

		if( this.HokenjyaName == null ) {
			JErrorMessage.show("M3102", null);
			return false;
		}

		return true;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public boolean setZipcode(String zipcode) {
		this.isValidationAsDataSet = false;
		this.Zipcode = JValidate.validateZipcode(zipcode);

		if( this.Zipcode == null ) {
			JErrorMessage.show("M3103", null);
			return false;
		}

		return true;
	}

	/**
	 * @param address the address to set
	 */
	public boolean setAddress(String address) {
		this.isValidationAsDataSet = false;
		this.Address = JValidate.validateAddress(address);

		if( this.Address == null ) {
			JErrorMessage.show("M3104", null);
			return false;
		}

		return true;
	}

	/**
	 * @param chiban the chiban to set
	 */
	public boolean setChiban(String chiban) {
		this.isValidationAsDataSet = false;
		this.Chiban = JValidate.validateChiban(chiban);

		if( this.Chiban == null ) {
			JErrorMessage.show("M3105", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tel the tEL to set
	 */
	public boolean setTEL(String tel) {
		this.isValidationAsDataSet = false;
		this.TEL = JValidate.validatePhoneNumber(tel);

		if( this.TEL == null ) {
			JErrorMessage.show("M3106", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kigo the code to set
	 */
	public boolean setKigo(String kigo) {
		this.isValidationAsDataSet = false;
		this.Code = JValidate.validateHokenjyaKigou(kigo);

		if( this.Code == null ) {
			JErrorMessage.show("M3107", null);
			return false;
		}

		return true;
	}

	/**
	 * @param honninGairai the honninGairai to set
	 */
	public boolean setHonninGairai(String honninGairai) {
		this.isValidationAsDataSet = false;
		this.HonninGairai = JValidate.validateKyuuhuPercent(honninGairai);

		if( this.HonninGairai == null ) {
			JErrorMessage.show("M3108", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kazokuGairai the kazokuGairai to set
	 */
	public boolean setKazokuGairai(String kazokuGairai) {
		this.isValidationAsDataSet = false;
		this.KazokuGairai = JValidate.validateKyuuhuPercent(kazokuGairai);

		if( this.KazokuGairai == null ) {
			JErrorMessage.show("M3109", null);
			return false;
		}

		return true;
	}

	/**
	 * @param honninNyuin the honninNyuin to set
	 */
	public boolean setHonninNyuin(String honninNyuin) {
		this.isValidationAsDataSet = false;
		this.HonninNyuin = JValidate.validateKyuuhuPercent(honninNyuin);

		if( this.HonninNyuin == null ) {
			JErrorMessage.show("M3110", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kazokuNyuin the kazokuNyuin to set
	 */
	public boolean setKazokuNyuin(String kazokuNyuin) {
		this.isValidationAsDataSet = false;
		this.KazokuNyuin = JValidate.validateKyuuhuPercent(kazokuNyuin);

		if( this.KazokuNyuin == null ) {
			JErrorMessage.show("M3111", null);
			return false;
		}

		return true;
	}

	/**
	 * @param itakuKubun the itakuKubun to set
	 */
	public boolean setItakuKubun(String itakuKubun) {
		this.isValidationAsDataSet = false;
		this.ItakuKubun = JValidate.validateItakuKubun(itakuKubun);

		if( this.ItakuKubun == null ) {
			JErrorMessage.show("M3112", null);
			return false;
		}

		return true;
	}

	/**
	 * @param itakuKubun the itakuKubun to set
	 */
	public boolean setItakuKubunCode(String itakuKubunCode) {
		this.isValidationAsDataSet = false;
		this.ItakuKubun = JValidate.validateItakuKubunCode(itakuKubunCode);

		if( this.ItakuKubun == null ) {
			JErrorMessage.show("M3112", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kihonTanka the kihonTanka to set
	 */
	public boolean setKihonTanka(String kihonTanka) {
		this.isValidationAsDataSet = false;
		this.KihonTanka = JValidate.validateKensaTanka(kihonTanka);

		if( this.KihonTanka == null ) {
			JErrorMessage.show("M3113", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hinketsuTanka the hinketsuTanka to set
	 */
	public boolean setHinketsuTanka(String hinketsuTanka) {
		this.isValidationAsDataSet = false;
		this.HinketsuTanka = JValidate.validateKensaTanka(hinketsuTanka);

		if( this.HinketsuTanka == null ) {
			JErrorMessage.show("M3114", null);
			return false;
		}

		return true;
	}

	/**
	 * @param sindenzuTanka the sindenzuTanka to set
	 */
	public boolean setSindenzuTanka(String sindenzuTanka) {
		this.isValidationAsDataSet = false;
		this.SindenzuTanka = JValidate.validateKensaTanka(sindenzuTanka);

		if( this.SindenzuTanka == null ) {
			JErrorMessage.show("M3115", null);
			return false;
		}

		return true;
	}

	/**
	 * @param ganteiTanka the ganteiTanka to set
	 */
	public boolean setGanteiTanka(String ganteiTanka) {
		this.isValidationAsDataSet = false;
		this.GanteiTanka = JValidate.validateKensaTanka(ganteiTanka);

		if( this.GanteiTanka == null ) {
			JErrorMessage.show("M3116", null);
			return false;
		}

		return true;
	}

	/**
	 * @param ganteiTanka the ningenDocTanka to set
	 */
	public boolean setNingenDocTanka(String ningenDocTanka) {
		this.isValidationAsDataSet = false;
		this.NingenDocTanka = JValidate.validateKensaTanka(ningenDocTanka);

		if( this.NingenDocTanka == null ) {
			JErrorMessage.show("M3152", null);
			return false;
		}

		return true;
	}

	// add s.inoue 2009/10/01
	/**
	 * @param Tanka the setTankaHantei to set
	 */
	public boolean setTankaHantei(String hanteiTanka) {
		this.isValidationAsDataSet = false;
		this.HanteiTanka = JValidate.validateTankaHantei(hanteiTanka);

		if( this.HanteiTanka == null ) {
			return false;
		}

		return true;
	}
	/**
	 * @param isValidationAsDataSet the isValidationAsDataSet to set
	 */
	public void setValidationAsDataSet() {
		this.isValidationAsDataSet = true;
	}

	/**
	 * @param hinketsuCode the hinketsuCode to set
	 */
	public boolean setHinketsuCode(String hinketsuCode) {
		this.isValidationAsDataSet = false;
		this.HinketsuCode = JValidate.validateKensaCode(hinketsuCode);

		if( this.HinketsuCode == null ) {
			JErrorMessage.show("M3117", null);
			return false;
		}

		return true;
	}

	/**
	 * @param shindenzuCode the shindenzuCode to set
	 */
	public boolean setShindenzuCode(String shindenzuCode) {
		this.isValidationAsDataSet = false;
		this.ShindenzuCode = JValidate.validateKensaCode(shindenzuCode);

		if( this.ShindenzuCode == null ) {
			JErrorMessage.show("M3118", null);
			return false;
		}

		return true;
	}

	/**
	 * @param ganteiCode the ganteiCode to set
	 */
	public boolean setGanteiCode(String ganteiCode) {
		this.isValidationAsDataSet = false;
		this.GanteiCode = JValidate.validateKensaCode(ganteiCode);

		if( this.GanteiCode == null ) {
			JErrorMessage.show("M3119", null);
			return false;
		}

		return true;
	}
}