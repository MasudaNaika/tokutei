package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JKekkaListFrameData {

	// 性別名(男,女)と性別コードを定義したマップ
	// コードマスタができるまで暫定的に定義
	private static Map<String, String> sexNameMap = new HashMap<String, String>();

	static {
		sexNameMap.put("1", "男性");
		sexNameMap.put("2", "女性");
	}

	private static Hashtable<String, String> metaboCodeMap = new Hashtable<String, String>();
	private static Hashtable<String, String> hokenshidowLevelCodeMap = new Hashtable<String, String>();

	static {
		/* 1:基準該当､2:予備群該当､3:非該当､4:判定不能 */
		metaboCodeMap.put("1", "基準該当");
		metaboCodeMap.put("2", "予備群該当");
		metaboCodeMap.put("3", "非該当");
		metaboCodeMap.put("4", "判定不能");

		/* 1:積極的支援､2:動機づけ支援､3:なし､4:判定不能 */
		hokenshidowLevelCodeMap.put("1", "積極的支援");
		hokenshidowLevelCodeMap.put("2", "動機づけ支援");
		hokenshidowLevelCodeMap.put("3", "なし（情報提供）");
		hokenshidowLevelCodeMap.put("4", "判定不能");
	}

	private String uketuke_id = "";
	private String uketukePre_id = "";
	private String jyushinkenID = new String("");
	private String hokenjyaNumber = new String("");
	private String hihokenjyaNumber = new String("");
	private String hihokenjyaCode = new String("");
	private String name = new String("");
	private String sex;
	private String kensaBeginDate = new String("");
	private String kensaEndDate = new String("");
	private String hanteiBeginDate = new String("");
	private String hanteiEndDate = new String("");
	private String tsuuchiBeginDate = new String("");
	private String tsuuchiEndDate = new String("");
	private boolean isValidateAsDataSet = false;
	private String birthDay = "";
	private String age = "";
	private String ageStart = "";
	private String ageEnd = "";

	/**
	 * 受付ID取得
	 * @return 受付ID
	 */
	public String getUketuke_id() {
		return uketuke_id;
	}

	/**
	 * 受付IDセット
	 * @param uketuke_id 受付ID
	 */
	public void setUketuke_id(String uketuke_id) {
		this.uketuke_id = uketuke_id;
	}

	/**
	 * 前回(コピー元)受付ID取得
	 * @return 受付ID
	 */
	public String getUketukePre_id() {
		return uketukePre_id;
	}

	/**
	 * 前回(コピー元)受付IDセット
	 * @param uketuke_id 受付ID
	 */
	public void setUketukePre_id(String uketukePre_id) {
		this.uketukePre_id = uketukePre_id;
	}

	/**
	 * @return the jyushinkenID
	 */
	public String getJyushinkenID() {
		return jyushinkenID;
	}
	/**
	 * @return the hokenjyaNumber
	 */
	public String getHokenjyaNumber() {
		return hokenjyaNumber;
	}
	/**
	 * @return the hihokenjyaNumber
	 */
	public String getHihokenjyaNumber() {
		return hihokenjyaNumber;
	}
	/**
	 * @return the hihokenjyaCode
	 */
	public String getHihokenjyaCode() {
		return hihokenjyaCode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	public String getSex() {
		return sex;
	}

	public boolean setSex(String sex) {
		this.sex = sex;

		return true;
	}

	public String getSexName() {
		return sexNameMap.get(getSex());
	}
	/**
	 * @return the kensaBeginDate
	 */
	public String getKensaBeginDate() {
		return kensaBeginDate;
	}
	/**
	 * @return the kensaEndDate
	 */
	public String getKensaEndDate() {
		return kensaEndDate;
	}
	/**
	 * @return the hanteiBeginDate
	 */
	public String getHanteiBeginDate() {
		return hanteiBeginDate;
	}
	/**
	 * @return the hanteiEndDate
	 */
	public String getHanteiEndDate() {
		return hanteiEndDate;
	}
	/**
	 * @return the tsuuchiBeginDate
	 */
	public String getTsuuchiBeginDate() {
		return tsuuchiBeginDate;
	}
	/**
	 * @return the tsuuchiEndDate
	 */
	public String getTsuuchiEndDate() {
		return tsuuchiEndDate;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/* Added 2008/03/30 若月  */
	/* --------------------------------------------------- */
	/**
	 * メタボ判定のコード値から表示名を取得する。
	 */
	public static String getMetaboHanteiDisplayName(String code){
		String retValue = "";

		if (code == null || code.isEmpty()) {
			retValue = "未判定";
		}
		else {
			retValue = metaboCodeMap.get(code);
		}

		return retValue;
	}

	/**
	 * 保健指導レベルのコード値から表示名を取得する。
	 */
	public static String getHokenshidowLebelDisplayName(String code){
		String retValue = "";

		if (code == null || code.isEmpty()) {
			retValue = "未判定";
		}
		else {
			retValue = hokenshidowLevelCodeMap.get(code);
		}

		return retValue;
	}
	/* --------------------------------------------------- */

	/**
	 * @param jyushinkenID the jyushinkenID to set
	 */
	public boolean setJyushinkenID(String jyushinkenID) {
		this.isValidateAsDataSet = false;

		/* Modified 2008/07/16 若月 部分検索に対応する */
		/* --------------------------------------------------- */
//		this.jyushinkenID = JValidate.validateJyushinkenID(jyushinkenID);
//
//		if( this.jyushinkenID == null ) {
//			JErrorMessage.show("M3501", null);
//			return false;
//		}
		/* --------------------------------------------------- */
		if (JValidate.isNumber(jyushinkenID)) {
			this.jyushinkenID = jyushinkenID;
		}
		else {
			JErrorMessage.show("M3501", null);
			return false;
		}
		/* --------------------------------------------------- */


		return true;
	}

	/**
	 * @param hokenjyaNumber the hokenjyaNumber to set
	 */
	public boolean setHokenjyaNumber(String hokenjyaNumber) {
		this.isValidateAsDataSet = false;

		/* Modified 2008/07/16 若月  */
		/* --------------------------------------------------- */
//		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);
		/* --------------------------------------------------- */
		/* 先頭の 0 を削除する。 */
		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber).replaceAll("^0+", "");
		/* --------------------------------------------------- */

		if( this.hokenjyaNumber == null ) {
			JErrorMessage.show("M3502", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyaNumber the hihokenjyaNumber to set
	 */
	public boolean setHihokenjyaNumber(String hihokenjyaNumber) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaNumber = JValidate.validateHihokenjyaNumber(hihokenjyaNumber);

		if( this.hihokenjyaNumber == null ) {
			JErrorMessage.show("M3503", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyaCode the hihokenjyaCode to set
	 */
	public boolean setHihokenjyaCode(String hihokenjyaCode) {
		this.isValidateAsDataSet = false;
		this.hihokenjyaCode = JValidate.validateHihokenjyaKigou(hihokenjyaCode);

		if( this.hihokenjyaCode == null ) {
			JErrorMessage.show("M3504", null);
			return false;
		}

		return true;
	}

	/**
	 * @param name the name to set
	 */
	public boolean setName(String name) {
		this.isValidateAsDataSet = false;
		this.name = JValidate.validateNameKana(name);

		if( this.name == null ) {
			JErrorMessage.show("M3505", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaBeginDate the kensaBeginDate to set
	 */
	public boolean setKensaBeginDate(String kensaBeginDate) {
		this.isValidateAsDataSet = false;
		this.kensaBeginDate = JValidate.validateDate(kensaBeginDate,true,false);

		if( this.kensaBeginDate == null ) {
			JErrorMessage.show("M3506", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaEndDate the kensaEndDate to set
	 */
	public boolean setKensaEndDate(String kensaEndDate) {
		this.isValidateAsDataSet = false;
		this.kensaEndDate = JValidate.validateDate(kensaEndDate,false,false);

		if( this.kensaEndDate == null ) {
			JErrorMessage.show("M3507", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hanteiBeginDate the hanteiBeginDate to set
	 */
	public boolean setHanteiBeginDate(String hanteiBeginDate) {
		this.isValidateAsDataSet = false;
		this.hanteiBeginDate = JValidate.validateDate(hanteiBeginDate,true,false);

		if( this.hanteiBeginDate == null ) {
			JErrorMessage.show("M3508", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hanteiEndDate the hanteiEndDate to set
	 */
	public boolean setHanteiEndDate(String hanteiEndDate) {
		this.isValidateAsDataSet = false;
		this.hanteiEndDate = JValidate.validateDate(hanteiEndDate,false,false);

		if( this.hanteiEndDate == null ) {
			JErrorMessage.show("M3509", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tsuuchiBeginDate the tsuuchiBeginDate to set
	 */
	public boolean setTsuuchiBeginDate(String tsuuchiBeginDate) {
		this.isValidateAsDataSet = false;
		this.tsuuchiBeginDate = JValidate.validateDate(tsuuchiBeginDate,true,false);

		if( this.tsuuchiBeginDate == null ) {
			JErrorMessage.show("M3510", null);
			return false;
		}

		return true;
	}

	/**
	 * @param tsuuchiEndDate the tsuuchiEndDate to set
	 */
	public boolean setTsuuchiEndDate(String tsuuchiEndDate) {
		this.isValidateAsDataSet = false;
		this.tsuuchiEndDate = JValidate.validateDate(tsuuchiEndDate,false,false);

		if( this.tsuuchiEndDate == null ) {
			JErrorMessage.show("M3511", null);
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

	public String getBirthDay() {
		return birthDay;
	}

	/* Modified 2008/07/19 若月  */
	/* --------------------------------------------------- */
//	public void setBirthDay(String birthDay) {
//		this.birthDay = birthDay;
//	}
	/* --------------------------------------------------- */
	public boolean setBirthDay(String birthDay) {

		boolean retValue = false;

		this.isValidateAsDataSet = false;
		this.birthDay = JValidate.validateDate(birthDay,false,true);

		if (this.birthDay != null) {
			retValue = true;
		}
		else {
			JErrorMessage.show("M3545", null);
		}

		return retValue;
	}
	/* --------------------------------------------------- */

	/* Added 2008/07/19 若月  */
	/* --------------------------------------------------- */
	public String getAge() {
		return age;
	}

	public boolean setAge(String age) {
		boolean retValue = false;

		this.isValidateAsDataSet = false;
		this.age = JValidate.validateAge(age);

		if (this.age != null) {
			retValue = true;
		}
		else {
			JErrorMessage.show("M3546", null);
		}

		return retValue;
	}
	/* --------------------------------------------------- */

	public String getAgeEnd() {
		return ageEnd;
	}

	public boolean setAgeEnd(String ageEnd) {
		boolean retValue = false;

		this.isValidateAsDataSet = false;
		this.ageEnd = JValidate.validateAge(ageEnd);

		if (this.ageEnd != null) {
			retValue = true;
		}
		else {
			JErrorMessage.show("M3546", null);
		}

		return retValue;
	}

	public String getAgeStart() {
		return ageStart;
	}

	public boolean setAgeStart(String ageStart) {
		boolean retValue = false;

		this.isValidateAsDataSet = false;
		this.ageStart = JValidate.validateAge(ageStart);

		if (this.ageStart != null) {
			retValue = true;
		}
		else {
			JErrorMessage.show("M3546", null);
		}

		return retValue;
	}
}



