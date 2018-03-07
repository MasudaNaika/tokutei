package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/**
 * 請求・HL7画面データ
 *
 * Modified 2008/04/04 若月
 * コメント追加
 */
public class JOutputNitijiFrameData {
	private String JyushinKenID = "";
	private String Hihokenjyasyo_kigou = "";
	private String JissiBeginDate = "";
	private String HokenjyaNumber = "";
	private String Name = "";
	private String Hihokenjyasyo_Number = "";
	private String JissiEndDate = "";
	private String HokenjyaName = "";
	private String JissiKubun = "";
	private String SeikyuKikanName = "";
	private String SyubetuCode = "";
	private String SeikyuKubun = "";
	private String SeikyuKikanNumber = "";
	private String HL7BeginDate = "";
	private String HL7EndDate = "";
	private boolean isValidateAsDataSet = false;
	private String SyubetuCodeOutput = "";

	// add ver2 s.inoue 2009/09/07
	private int tankaSyokei = 0;
	private int madogutiSyokei = 0;
	private int seikyuSyokei = 0;

	/**
	 * @return the tankaSyokei
	 */
	public int getTankaSyokei() {
		return tankaSyokei;
	}

	/**
	 * @param tankaSyokei the tankaSyokei to set
	 */
	public void setTankaSyokei(int tankaSyokei) {
		this.tankaSyokei = tankaSyokei;
	}

	/**
	 * @return the madogutiSyokei
	 */
	public int getMadogutiSyokei() {
		return madogutiSyokei;
	}

	/**
	 * @param madogutiSyokei the madogutiSyokei to set
	 */
	public void setMadogutiSyokei(int madogutiSyokei) {
		this.madogutiSyokei = madogutiSyokei;
	}

	/**
	 * @return the madogutiSyokei
	 */
	public int getSeikyuSyokei() {
		return seikyuSyokei;
	}

	/**
	 * @param madogutiSyokei the madogutiSyokei to set
	 */
	public void setSeikyuSyokei(int seikyuSyokei) {
		this.seikyuSyokei = seikyuSyokei;
	}


	/**
	 * @return the jyushinKenID
	 */
	public String getJyushinKenID() {
		return JyushinKenID;
	}
	/**
	 * @return the hihokenjyasyo_kigou
	 */
	public String getHihokenjyasyo_kigou() {
		return Hihokenjyasyo_kigou;
	}
	/**
	 * @return the beginDate
	 */
	public String getJissiBeginDate() {
		return JissiBeginDate;
	}
	/**
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return HokenjyaNumber;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @return the hihokenjyasyo_Number
	 */
	public String getHihokenjyasyo_Number() {
		return Hihokenjyasyo_Number;
	}
	/**
	 * @return the endDate
	 */
	public String getJissiEndDate() {
		return JissiEndDate;
	}
	/**
	 * @return the hokenjyaName
	 */
	public String getHokenjyaName() {
		return HokenjyaName;
	}
	/**
	 * @return the jissiKubun
	 */
	public String getJissiKubun() {
		return JissiKubun;
	}
	/**
	 * @return the seikyuKikanName
	 */
	public String getSeikyuKikanName() {
		return SeikyuKikanName;
	}
	/**
	 * @return the syubetuCode
	 */
	public String getSyubetuCode() {
		return SyubetuCode;
	}
	/**
	 * @return the seikyuKubun
	 */
	public String getSeikyuKubun() {
		return SeikyuKubun;
	}
	/**
	 * @return the seikyuKikanNumber
	 */
	public String getSeikyuKikanNumber() {
		return SeikyuKikanNumber;
	}
	/**
	 * @return the hL7BeginDate
	 */
	public String getHL7BeginDate() {
		return HL7BeginDate;
	}
	/**
	 * @return the hL7EndDate
	 */
	public String getHL7EndDate() {
		return HL7EndDate;
	}
	/**
	 * @return the syubetuCodeOutput
	 */
	public String getSyubetuCodeOutput() {
		return SyubetuCodeOutput;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param jyushinKenID the jyushinKenID to set
	 */
	public boolean setJyushinKenID(String jyushinKenID) {
		this.isValidateAsDataSet = false;
		this.JyushinKenID = JValidate.validateJyushinkenID(jyushinKenID);

		if( this.JyushinKenID == null ) {
			JErrorMessage.show("M4701", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyasyo_kigou the hihokenjyasyo_kigou to set
	 */
	public boolean setHihokenjyasyo_kigou(String hihokenjyasyo_kigou) {
		this.isValidateAsDataSet = false;
		this.Hihokenjyasyo_kigou = JValidate.validateHihokenjyaKigou(hihokenjyasyo_kigou);

		if( this.Hihokenjyasyo_kigou == null ) {
			JErrorMessage.show("M4702", null);
			return false;
		}

		return true;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public boolean setJissiBeginDate(String jissibeginDate) {
		this.isValidateAsDataSet = false;
		this.JissiBeginDate = JValidate.validateDate(jissibeginDate,true,false);

		if( this.JissiBeginDate == null ) {
			JErrorMessage.show("M4703", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.HokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);

		if( this.HokenjyaNumber == null ) {
			JErrorMessage.show("M4704", null);
			return false;
		}

		return true;
	}

	/**
	 * @param name the name to set
	 */
	public boolean setName(String name) {
		this.isValidateAsDataSet = false;
		this.Name = JValidate.validateNameKana(name);

		if( this.Name == null ) {
			JErrorMessage.show("M4705", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyasyo_Number the hihokenjyasyo_Number to set
	 */
	public boolean setHihokenjyasyo_Number(String hihokenjyasyo_Number) {
		this.isValidateAsDataSet = false;
		this.Hihokenjyasyo_Number = JValidate.validateHihokenjyaNumber(hihokenjyasyo_Number);

		if( this.Hihokenjyasyo_Number == null ) {
			JErrorMessage.show("M4706", null);
			return false;
		}

		return true;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public boolean setJissiEndDate(String jissiendDate) {
		this.isValidateAsDataSet = false;
		this.JissiEndDate = JValidate.validateDate(jissiendDate,false,false);

		if( this.JissiEndDate == null ) {
			JErrorMessage.show("M4707", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hokenjyaName the hokenjyaName to set
	 */
	public boolean setHokenjyaName(String hokenjyaName) {
		this.isValidateAsDataSet = false;
		this.HokenjyaName = JValidate.validateHokenjyaName(hokenjyaName);

		if( this.HokenjyaName == null ) {
			JErrorMessage.show("M4708", null);
			return false;
		}

		return true;
	}

	/**
	 * @param jissiKubun the jissiKubun to set
	 */
	public boolean setJissiKubun(String jissiKubun) {
		this.isValidateAsDataSet = false;
		this.JissiKubun = JValidate.validateTokuteiKenshinJissiKubun(jissiKubun);

		if( this.JissiKubun == null ) {
			JErrorMessage.show("M4709", null);
			return false;
		}

		return true;
	}

	/**
	 * @param seikyuKikanName the seikyuKikanName to set
	 */
	public boolean setSeikyuKikanName(String seikyuKikanName) {
		this.isValidateAsDataSet = false;
		this.SeikyuKikanName = JValidate.validateDaikouName(seikyuKikanName);

		if( this.SeikyuKikanName == null ) {
			JErrorMessage.show("M4710", null);
			return false;
		}

		return true;
	}

	/**
	 * @param syubetuCode the syubetuCode to set
	 */
	public boolean setSyubetuCode(String syubetuCode) {
		this.isValidateAsDataSet = false;
		this.SyubetuCode = JValidate.validateTokuteiKenshinSyubetuKubun(syubetuCode);

		if( this.SyubetuCode == null ) {
			JErrorMessage.show("M4711", null);
			return false;
		}

		return true;
	}

	/**
	 * @param seikyuKubun the seikyuKubun to set
	 */
	public boolean setSeikyuKubun(String seikyuKubun) {
		this.isValidateAsDataSet = false;
		this.SeikyuKubun = JValidate.validateSeikyuKubun(seikyuKubun);

		if( this.SeikyuKubun == null ) {
			JErrorMessage.show("M4712", null);
			return false;
		}

		return true;
	}

	/**
	 * @param seikyuKikanNumber the seikyuKikanNumber to set
	 */
	public boolean setSeikyuKikanNumber(String seikyuKikanNumber) {
		this.isValidateAsDataSet = false;
		this.SeikyuKikanNumber = JValidate.validateDaikouNumber(seikyuKikanNumber);

		if( this.SeikyuKikanNumber == null ) {
			JErrorMessage.show("M4713", null);
			return false;
		}

		return true;
	}

	/**
	 * @param beginDate the hL7BeginDate to set
	 */
	public boolean setHL7BeginDate(String beginDate) {
		this.isValidateAsDataSet = false;
		this.HL7BeginDate = JValidate.validateDate(beginDate,true,false);

		if( this.HL7BeginDate == null ) {
			JErrorMessage.show("M4714", null);
			return false;
		}

		return true;
	}

	/**
	 * @param endDate the hL7EndDate to set
	 */
	public boolean setHL7EndDate(String endDate) {
		this.isValidateAsDataSet = false;
		this.HL7EndDate = JValidate.validateDate(endDate,false,false);

		if( this.HL7EndDate == null ) {
			JErrorMessage.show("M4715", null);
			return false;
		}

		return true;
	}

	public boolean setSyubetuCodeOutput(String selectedItem) {
		this.isValidateAsDataSet = false;
		this.SyubetuCodeOutput  = JValidate.validateTokuteiKenshinSyubetuKubun(selectedItem);

		if( this.SyubetuCodeOutput == null ) {
			JErrorMessage.show("M4750", null);
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