//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.util.HashMap;
//import java.util.Iterator;
//
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//
///**
// * 受診券入力画面データ
// *
// * Modified 2008/06/02 若月 コンボボックス、ラジオボタンの文字列による検証を廃止する。
// */
//public class JKojinRegisterFrameData {
//	private static final String COLUMN_NAME_UKETUKE_ID = "UKETUKE_ID";
//	private static final String COLUMN_NAME_NENDO = "NENDO";
//	private static final String COLUMN_NAME_MADO_FUTAN_DOC = "MADO_FUTAN_DOC";
//	private static final String COLUMN_NAME_MADO_FUTAN_D_SYUBETU = "MADO_FUTAN_D_SYUBETU";
//	private static final String COLUMN_NAME_MADO_FUTAN_TSUIKA = "MADO_FUTAN_TSUIKA";
//	private static final String COLUMN_NAME_MADO_FUTAN_T_SYUBETU = "MADO_FUTAN_T_SYUBETU";
//	private static final String COLUMN_NAME_MADO_FUTAN_SYOUSAI = "MADO_FUTAN_SYOUSAI";
//	private static final String COLUMN_NAME_MADO_FUTAN_S_SYUBETU = "MADO_FUTAN_S_SYUBETU";
//	private static final String COLUMN_NAME_MADO_FUTAN_KIHON = "MADO_FUTAN_KIHON";
//	private static final String COLUMN_NAME_MADO_FUTAN_K_SYUBETU = "MADO_FUTAN_K_SYUBETU";
//	private static final String COLUMN_NAME_SIHARAI_DAIKOU_BANGO = "SIHARAI_DAIKOU_BANGO";
//	private static final String COLUMN_NAME_KOUHUBI = "KOUHUBI";
//	private static final String COLUMN_NAME_KEIYAKU_TORIMATOME = "KEIYAKU_TORIMATOME";
//	private static final String COLUMN_NAME_KEITAI_EMAIL = "KEITAI_EMAIL";
//	private static final String COLUMN_NAME_EMAIL = "EMAIL";
//	private static final String COLUMN_NAME_FAX = "FAX";
//	private static final String COLUMN_NAME_KEITAI_TEL = "KEITAI_TEL";
//	private static final String COLUMN_NAME_HOME_TEL1 = "HOME_TEL1";
//	private static final String COLUMN_NAME_HOME_BANTI = "HOME_BANTI";
//	private static final String COLUMN_NAME_HOME_ADRS = "HOME_ADRS";
//	private static final String COLUMN_NAME_HOME_POST = "HOME_POST";
//	private static final String COLUMN_NAME_SEX = "SEX";
//	private static final String COLUMN_NAME_BIRTHDAY = "BIRTHDAY";
//	private static final String COLUMN_NAME_NICKNAME = "NICKNAME";
//	private static final String COLUMN_NAME_KANANAME = "KANANAME";
//	private static final String COLUMN_NAME_NAME = "NAME";
//	private static final String COLUMN_NAME_HIHOKENJYASYO_NO = "HIHOKENJYASYO_NO";
//	private static final String COLUMN_NAME_HIHOKENJYASYO_KIGOU = "HIHOKENJYASYO_KIGOU";
//	private static final String COLUMN_NAME_HKNJANUM = "HKNJANUM";
//	private static final String COLUMN_NAME_YUKOU_KIGEN = "YUKOU_KIGEN";
//	private static final String COLUMN_NAME_JYUSHIN_SEIRI_NO = "JYUSHIN_SEIRI_NO";
//	private static final String COLUMN_NAME_PTNUM = "PTNUM";
//	private static final String COLUMN_NAME_MADO_FUTAN_SONOTA = "MADO_FUTAN_SONOTA";
//	private static final String COLUMN_NAME_HOKENJYA_FUTAN_KIHON = "HOKENJYA_FUTAN_KIHON";
//	private static final String COLUMN_NAME_HOKENJYA_FUTAN_SYOUSAI = "HOKENJYA_FUTAN_SYOUSAI";
//	private static final String COLUMN_NAME_HOKENJYA_FUTAN_TSUIKA = "HOKENJYA_FUTAN_TSUIKA";
//	private static final String COLUMN_NAME_HOKENJYA_FUTAN_DOC = "HOKENJYA_FUTAN_DOC";
//
//	private String ORCAID = "";
//	private String jyushinkenID = "";
//	private String prejyushinkenID = "";
//	private String IssueDate = "";
//	private String limitDate = "";
//	private String hokenjyaNumber = "";
//	private String hokenjyaName = "";
//	private String hokenjyaPhone = "";
//	private String DaikouNumber = "";
//	private String DaikouName = "";
//	private String DaikouPhone = "";
//	private String torimatomeName = "";
//	private String hihokenjyaCode = "";
//	private String hihokenjyaNumber = "";
//	private String nameKanji = "";
//	private String nameKana = "";
//	private String nameTsusyou = "";
//	private String birthday = "";
//	private String sex = "";
//	private String zipcode = "";
//	private String address = "";
//	private String homePhone = "";
//	private String faxNumber = "";
//	private String EMail = "";
//	private String CellPhone = "";
//	private String mobileMail = "";
//	private String madoguchiKihonSyubetu = "";
//	private String madoguchiSyousaiSyubetu = "";
//	private String madoguchiTsuikaSyubetu = "";
//	private String madoguchiDockSyubetu = "";
//	private String madoguchiKihon = "";
//	private String madoguchiSyousai = "";
//	private String madoguchiTsuika = "";
//	private String madoguchiDock = "";
//	private String chiban = "";
//	private HashMap<String, String> errorMessage =null;
//
//	public JKojinRegisterFrameData(){
//		errorMessage = new HashMap<String, String>();
//	}
//
//	// 値初期化
//	public void clearColumnData(){
//		errorMessage.put(null,null);
//	}
//	/* メッセージ詰込用 */
//	public Integer getErrorMessageCount(){
//		return this.errorMessage.size();
//	}
//	public HashMap<String, String> getErrorMessage(){
//		return this.errorMessage;
//	}
//
//	private static final String[] T_KOJIN_COLUMNNAMES = {
//			COLUMN_NAME_PTNUM,
//			COLUMN_NAME_JYUSHIN_SEIRI_NO,
//			COLUMN_NAME_YUKOU_KIGEN,
//			COLUMN_NAME_HKNJANUM,
//			COLUMN_NAME_HIHOKENJYASYO_KIGOU,
//			COLUMN_NAME_HIHOKENJYASYO_NO,
//			COLUMN_NAME_NAME,
//			COLUMN_NAME_KANANAME,
//			COLUMN_NAME_NICKNAME,
//			COLUMN_NAME_BIRTHDAY,
//			COLUMN_NAME_SEX,
//			COLUMN_NAME_HOME_POST,
//			COLUMN_NAME_HOME_ADRS,
//			COLUMN_NAME_HOME_BANTI,
//			COLUMN_NAME_HOME_TEL1,
//			COLUMN_NAME_KEITAI_TEL,
//			COLUMN_NAME_FAX,
//			COLUMN_NAME_EMAIL,
//			COLUMN_NAME_KEITAI_EMAIL,
//			COLUMN_NAME_KEIYAKU_TORIMATOME,
//			COLUMN_NAME_KOUHUBI,
//			COLUMN_NAME_SIHARAI_DAIKOU_BANGO,
//			COLUMN_NAME_MADO_FUTAN_K_SYUBETU,
//			COLUMN_NAME_MADO_FUTAN_KIHON,
//			COLUMN_NAME_MADO_FUTAN_S_SYUBETU,
//			COLUMN_NAME_MADO_FUTAN_SYOUSAI,
//			COLUMN_NAME_MADO_FUTAN_T_SYUBETU,
//			COLUMN_NAME_MADO_FUTAN_TSUIKA,
//			COLUMN_NAME_MADO_FUTAN_D_SYUBETU,
//			COLUMN_NAME_MADO_FUTAN_DOC,
//			COLUMN_NAME_NENDO,
//			COLUMN_NAME_UKETUKE_ID,
//			COLUMN_NAME_MADO_FUTAN_SONOTA,
//			COLUMN_NAME_HOKENJYA_FUTAN_KIHON,
//			COLUMN_NAME_HOKENJYA_FUTAN_SYOUSAI,
//			COLUMN_NAME_HOKENJYA_FUTAN_TSUIKA,
//			COLUMN_NAME_HOKENJYA_FUTAN_DOC,
//	};
//
//	public static enum T_KOJIN_COLUMN {
//		COLUMN_PTNUM,
//		COLUMN_JYUSHIN_SEIRI_NO,
//		COLUMN_YUKOU_KIGEN,
//		COLUMN_HKNJANUM,
//		COLUMN_HIHOKENJYASYO_KIGOU,
//		COLUMN_HIHOKENJYASYO_NO,
//		COLUMN_NAME,
//		COLUMN_KANANAME,
//		COLUMN_NICKNAME,
//		COLUMN_BIRTHDAY,
//		COLUMN_SEX,
//		COLUMN_HOME_POST,
//		COLUMN_HOME_ADRS,
//		COLUMN_HOME_BANTI,
//		COLUMN_HOME_TEL1,
//		COLUMN_KEITAI_TEL,
//		COLUMN_FAX,
//		COLUMN_EMAIL,
//		COLUMN_KEITAI_EMAIL,
//		COLUMN_KEIYAKU_TORIMATOME,
//		COLUMN_KOUHUBI,
//		COLUMN_SIHARAI_DAIKOU_BANGO,
//		COLUMN_MADO_FUTAN_K_SYUBETU,
//		COLUMN_MADO_FUTAN_KIHON,
//		COLUMN_MADO_FUTAN_S_SYUBETU,
//		COLUMN_MADO_FUTAN_SYOUSAI,
//		COLUMN_MADO_FUTAN_T_SYUBETU,
//		COLUMN_MADO_FUTAN_TSUIKA,
//		COLUMN_MADO_FUTAN_D_SYUBETU,
//		COLUMN_MADO_FUTAN_DOC,
//		COLUMN_NENDO,
//		COLUMN_UKETUKE_ID,
//		COLUMN_MADO_FUTAN_SONOTA,
//		COLUMN_HOKENJYA_FUTAN_KIHON,
//		COLUMN_HOKENJYA_FUTAN_SYOUSAI,
//		COLUMN_HOKENJYA_FUTAN_TSUIKA,
//		COLUMN_HOKENJYA_FUTAN_DOC,
//	};
//
//	private static HashMap<T_KOJIN_COLUMN, String> columnNames = null;
//	static {
//		columnNames = new HashMap<T_KOJIN_COLUMN, String>();
//
//		for (int i = 0; i < T_KOJIN_COLUMN.values().length; i++) {
//			columnNames.put(
//					T_KOJIN_COLUMN.values()[i],
//					T_KOJIN_COLUMNNAMES[i]);
//		}
//	}
//
//	public static String getColumnName(T_KOJIN_COLUMN column){
//		String columnName = columnNames.get(column);
//		return columnName;
//	}
//
//	public String get(T_KOJIN_COLUMN column){
//		String value = null;
//
//		switch (column) {
//		case COLUMN_PTNUM:
//			value = this.getORCAID();
//			break;
//
//		case COLUMN_JYUSHIN_SEIRI_NO:
//			value = this.getJyushinkenID();
//			break;
//
//		case COLUMN_YUKOU_KIGEN:
//			value = this.getLimitDate();
//			break;
//
//		case COLUMN_HKNJANUM:
//			value = this.getHokenjyaNumber();
//			break;
//
//		case COLUMN_HIHOKENJYASYO_KIGOU:
//			value = this.getHihokenjyaCode();
//			break;
//
//		case COLUMN_HIHOKENJYASYO_NO:
//			value = this.getHihokenjyaNumber();
//			break;
//
//		case COLUMN_NAME:
//			value = this.getNameKanji();
//			break;
//
//		case COLUMN_KANANAME:
//			value = this.getNameKana();
//			break;
//
//		case COLUMN_NICKNAME:
//			value = this.getNameTsusyou();
//			break;
//
//		case COLUMN_BIRTHDAY:
//			value = this.getBirthday();
//			break;
//
//		case COLUMN_SEX:
//			value = this.getSex();
//			break;
//
//		case COLUMN_HOME_POST:
//			value = this.getZipcode();
//			break;
//
//		case COLUMN_HOME_ADRS:
//			value = this.getAddress();
//			break;
//
//		case COLUMN_HOME_BANTI:
//			value = this.getChiban();
//			break;
//
//		case COLUMN_HOME_TEL1:
//			value = this.getHomePhone();
//			break;
//
//		case COLUMN_KEITAI_TEL:
//			value = this.getCellPhone();
//			break;
//
//		case COLUMN_FAX:
//			value = this.getFaxNumber();
//			break;
//
//		case COLUMN_EMAIL:
//			value = this.getEMail();
//			break;
//
//		case COLUMN_KEITAI_EMAIL:
//			value = this.getMobileMail();
//			break;
//
//		case COLUMN_KEIYAKU_TORIMATOME:
//			value = this.getTorimatomeName();
//			break;
//
//		case COLUMN_KOUHUBI:
//			value = this.getIssueDate();
//			break;
//
//		case COLUMN_SIHARAI_DAIKOU_BANGO:
//			value = this.getDaikouNumber();
//			break;
//
//		case COLUMN_MADO_FUTAN_K_SYUBETU:
//			value = this.getMadoguchiKihonSyubetu();
//			break;
//
//		case COLUMN_MADO_FUTAN_KIHON:
//			value = this.getMadoguchiKihon();
//			break;
//
//		case COLUMN_MADO_FUTAN_S_SYUBETU:
//			value = this.getMadoguchiSyousaiSyubetu();
//			break;
//
//		case COLUMN_MADO_FUTAN_SYOUSAI:
//			value = this.getMadoguchiSyousai();
//			break;
//
//		case COLUMN_MADO_FUTAN_T_SYUBETU:
//			value = this.getMadoguchiTsuikaSyubetu();
//			break;
//
//		case COLUMN_MADO_FUTAN_TSUIKA:
//			value = this.getMadoguchiTsuika();
//			break;
//
//		case COLUMN_MADO_FUTAN_D_SYUBETU:
//			value = this.getMadoguchiDockSyubetu();
//			break;
//
//		case COLUMN_MADO_FUTAN_DOC:
//			value = this.getMadoguchiDock();
//			break;
//
//		case COLUMN_NENDO:
//			value = this.getNendo();
//			break;
//
//		case COLUMN_UKETUKE_ID:
//			value = this.getUketukeID();
//			break;
//
//		case COLUMN_MADO_FUTAN_SONOTA:
//			value = this.getMadoguchiSonota();
//			break;
//
//		case COLUMN_HOKENJYA_FUTAN_KIHON:
//			value = this.getHokenjyaJougenKihon();
//			break;
//
//		case COLUMN_HOKENJYA_FUTAN_SYOUSAI:
//			value = this.getHokenjyaJougenSyousai();
//			break;
//
//		case COLUMN_HOKENJYA_FUTAN_TSUIKA:
//			value = this.getHokenjyaJougenTsuika();
//			break;
//
//		case COLUMN_HOKENJYA_FUTAN_DOC:
//			value = this.getHokenjyaJougenDoc();
//			break;
//
//		default:
//			break;
//		}
//
//		return value;
//	}
//
//	private String orcaVersion = "";
//	private String uketukeID = "";
//	private String preUketukeID="";
//	private String nendo = "";
//	private boolean isValidationAsDataSet = false;
//	private int diff = 0;
//
//	public static String[][] MADOGUCHI_HUTAN_SYUBETSU_ITEMS = {
//		{"", ""},
//		{"1", "1:無し"},
//		{"2", "2:定額"},
//		{"3", "3:定率"},
//	};
//
//	private String hokenjyaJougenKihon = "";
//	private String hokenjyaJougenSyousai = "";
//	private String hokenjyaJougenTsuika = "";
//	private String hokenjyaJougenDoc = "";
//	private String madoguchiSonota = "";
//
//	/**
//	 * @return the chiban
//	 */
//	public String getChiban() {
//		return chiban;
//	}
//
//	/**
//	 * @return the ORCAID
//	 */
//	public String getORCAID() {
//		return ORCAID;
//	}
//	/**
//	 * @return the jyushinkenID
//	 */
//	public String getJyushinkenID() {
//		return jyushinkenID;
//	}
//	/**
//	 * @return the jyushinkenID
//	 */
//	public String getpreJyushinkenID() {
//		return prejyushinkenID;
//	}
//
//	/**
//	 * @return the issueDate
//	 */
//	public String getIssueDate() {
//		return IssueDate;
//	}
//	/**
//	 * @return the limitDate
//	 */
//	public String getLimitDate() {
//		return limitDate;
//	}
//	/**
//	 * @return the hokenjyaNumber
//	 */
//	public String getHokenjyaNumber() {
//		return hokenjyaNumber;
//	}
//	/**
//	 * @return the hokenjyaName
//	 */
//	public String getHokenjyaName() {
//		return hokenjyaName;
//	}
//	/**
//	 * @return the hokenjyaPhone
//	 */
//	public String getHokenjyaPhone() {
//		return hokenjyaPhone;
//	}
//	/**
//	 * @return the daikouNumber
//	 */
//	public String getDaikouNumber() {
//		return DaikouNumber;
//	}
//	/**
//	 * @return the daikouName
//	 */
//	public String getDaikouName() {
//		return DaikouName;
//	}
//	/**
//	 * @return the daikouPhone
//	 */
//	public String getDaikouPhone() {
//		return DaikouPhone;
//	}
//	/**
//	 * @return the torimatomeName
//	 */
//	public String getTorimatomeName() {
//		return torimatomeName;
//	}
//	/**
//	 * @return the hihokenjyaCode
//	 */
//	public String getHihokenjyaCode() {
//		return hihokenjyaCode;
//	}
//	/**
//	 * @return the hihokenjyaNumber
//	 */
//	public String getHihokenjyaNumber() {
//		return hihokenjyaNumber;
//	}
//	/**
//	 * @return the nameKanji
//	 */
//	public String getNameKanji() {
//		return nameKanji;
//	}
//	/**
//	 * @return the nameKana
//	 */
//	public String getNameKana() {
//		return nameKana;
//	}
//	/**
//	 * @return the nameTsusyou
//	 */
//	public String getNameTsusyou() {
//		return nameTsusyou;
//	}
//	/**
//	 * @return the birthday
//	 */
//	public String getBirthday() {
//		return birthday;
//	}
//	/**
//	 * @return the sex
//	 */
//	public String getSex() {
//		return sex;
//	}
//	/**
//	 * @return the zipcode
//	 */
//	public String getZipcode() {
//		return zipcode;
//	}
//	/**
//	 * @return the address
//	 */
//	public String getAddress() {
//		return address;
//	}
//	/**
//	 * @return the homePhone
//	 */
//	public String getHomePhone() {
//		return homePhone;
//	}
//	/**
//	 * @return the faxNumber
//	 */
//	public String getFaxNumber() {
//		return faxNumber;
//	}
//	/**
//	 * @return the eMail
//	 */
//	public String getEMail() {
//		return EMail;
//	}
//	/**
//	 * @return the cellPhone
//	 */
//	public String getCellPhone() {
//		return CellPhone;
//	}
//	/**
//	 * @return the mobileMail
//	 */
//	public String getMobileMail() {
//		return mobileMail;
//	}
//	/**
//	 * @return the madoguchiKihonSyubetu
//	 */
//	public String getMadoguchiKihonSyubetu() {
//		return madoguchiKihonSyubetu;
//	}
//	/**
//	 * @return the madoguchiSyousaiSyubetu
//	 */
//	public String getMadoguchiSyousaiSyubetu() {
//		return madoguchiSyousaiSyubetu;
//	}
//	/**
//	 * @return the madoguchiTsuikaSyubetu
//	 */
//	public String getMadoguchiTsuikaSyubetu() {
//		return madoguchiTsuikaSyubetu;
//	}
//	/**
//	 * @return the madoguchiDockSyubetu
//	 */
//	public String getMadoguchiDockSyubetu() {
//		return madoguchiDockSyubetu;
//	}
//	/**
//	 * @return the madoguchiKihon
//	 */
//	public String getMadoguchiKihon() {
//		return madoguchiKihon;
//	}
//	/**
//	 * @return the madoguchiSyousai
//	 */
//	public String getMadoguchiSyousai() {
//		return madoguchiSyousai;
//	}
//	/**
//	 * @return the madoguchiTsuika
//	 */
//	public String getMadoguchiTsuika() {
//		return madoguchiTsuika;
//	}
//	/**
//	 * @return the madoguchiDock
//	 */
//	public String getMadoguchiDock() {
//		return madoguchiDock;
//	}
//	/**
//	 * @return the isValidationAsDataSet
//	 */
//	public boolean isValidationAsDataSet() {
//		return isValidationAsDataSet;
//	}
//
//	/**
//	 * @param orcaid the ORCAID to set
//	 */
//	public boolean setORCAID(String orcaid) {
//		isValidationAsDataSet = false;
//		this.ORCAID = JValidate.validateORCAID(orcaid);
//
//		if( this.ORCAID == null ) {
//			JErrorMessage.show("M4301", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param jyushinkenID the jyushinkenID to set
//	 */
//	public boolean setJyushinkenID(String jyushinkenID,boolean flg) {
//		isValidationAsDataSet = false;
//		this.jyushinkenID = JValidate.validateJyushinkenID(jyushinkenID);
//
//		if( this.jyushinkenID == null) {
//			if (flg){
//				errorMessage.put("M4302", JErrorMessage.getMessageValue("M4302"));
//			}else{
//				JErrorMessage.show("M4302", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * @param jyushinkenID the jyushinkenID to set
//	 */
//	public boolean setpreJyushinkenID(String prejyushinkenID,boolean flg) {
//		isValidationAsDataSet = false;
//		this.prejyushinkenID = JValidate.validateJyushinkenID(prejyushinkenID);
//
//		if( this.prejyushinkenID == null ) {
//			if (flg){
//				errorMessage.put("M4302",  JErrorMessage.getMessageValue("M4302"));
//			}else{
//				JErrorMessage.show("M4302", null);
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param issueDate the issueDate to set
//	 */
//	public boolean setIssueDate(String issueDate) {
//		isValidationAsDataSet = false;
//		this.IssueDate = JValidate.validateDate(issueDate,true,true);
//
//		if( this.IssueDate == null ) {
//			JErrorMessage.show("M4303", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param limitDate the limitDate to set
//	 */
//	public boolean setLimitDate(String limitDate,boolean flg) {
//		isValidationAsDataSet = false;
//		this.limitDate = JValidate.validateDate(limitDate,true,true);
//
//		if( this.limitDate == null ) {
//			if (flg){
//				errorMessage.put("M4304", JErrorMessage.getMessageValue("M4304"));
//			}else{
//				JErrorMessage.show("M4304", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * @param hokenjyaNumber the hokenjyaNumber to set
//	 */
//	public boolean setHokenjyaNumber(String hokenjyaNumber,boolean flg) {
//		isValidationAsDataSet = false;
//		this.hokenjyaNumber = JValidate.validateHokenjyaNumber(hokenjyaNumber);
//
//		if( this.hokenjyaNumber == null) {
//			if (flg){
//				errorMessage.put("M4305",  JErrorMessage.getMessageValue("M4305"));
//			}else{
//				JErrorMessage.show("M4305", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * @param hokenjyaName the hokenjyaName to set
//	 */
//	public boolean setHokenjyaName(String hokenjyaName) {
//		isValidationAsDataSet = false;
//		this.hokenjyaName =  JValidate.validateHokenjyaName(hokenjyaName);
//
//		if( this.hokenjyaName == null ) {
//			JErrorMessage.show("M4306", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param hokenjyaPhone the hokenjyaPhone to set
//	 */
//	public boolean setHokenjyaPhone(String hokenjyaPhone) {
//		isValidationAsDataSet = false;
//		this.hokenjyaPhone = JValidate.validatePhoneNumber(hokenjyaPhone);
//
//		if( this.hokenjyaPhone == null ) {
//			JErrorMessage.show("M4307", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param daikouNumber the daikouNumber to set
//	 */
//	public boolean setDaikouNumber(String daikouNumber) {
//		isValidationAsDataSet = false;
//
//		if (daikouNumber == null) {
//			daikouNumber = "";
//		}
//
//		this.DaikouNumber = JValidate.validateDaikouNumber(daikouNumber);
//
//		if( this.DaikouNumber == null ) {
//			JErrorMessage.show("M4308", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param daikouName the daikouName to set
//	 */
//	public boolean setDaikouName(String daikouName) {
//		isValidationAsDataSet = false;
//		this.DaikouName = JValidate.validateDaikouName(daikouName);
//
//		if( this.DaikouName == null ) {
//			JErrorMessage.show("M4309", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param daikouPhone the daikouPhone to set
//	 */
//	public boolean setDaikouPhone(String daikouPhone) {
//		isValidationAsDataSet = false;
//		this.DaikouPhone = JValidate.validatePhoneNumber(daikouPhone);
//
//		if( this.DaikouPhone == null ) {
//			JErrorMessage.show("M4310", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param torimatomeName the torimatomeName to set
//	 */
//	public boolean setTorimatomeName(String torimatomeName) {
//		isValidationAsDataSet = false;
//		this.torimatomeName = JValidate.validateTorimatomeName(torimatomeName);
//
//		if( this.torimatomeName == null ) {
//			JErrorMessage.show("M4311", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param hihokenjyaCode the hihokenjyaCode to set
//	 */
//	public boolean setHihokenjyaCode(String hihokenjyaCode,boolean flg) {
//
//		isValidationAsDataSet = false;
//		if (hihokenjyaCode != null) {
//			this.hihokenjyaCode = JValidate.validateHihokenjyaKigou(hihokenjyaCode);
//
//			if( this.hihokenjyaCode == null ) {
//				if (flg){
//					errorMessage.put("M4312", JErrorMessage.getMessageValue("M4312"));
//				}else{
//					JErrorMessage.show("M4312", null);
//					return false;
//				}
//			}else{
//			}
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param hihokenjyaNumber the hihokenjyaNumber to set
//	 */
//	public boolean setHihokenjyaNumber(String hihokenjyaNumber,boolean flg) {
//		isValidationAsDataSet = false;
//
//		if (hihokenjyaNumber != null) {
//			this.hihokenjyaNumber = JValidate.validateHihokenjyaNumber(hihokenjyaNumber);
//
//			if( this.hihokenjyaNumber == null) {
//				if (flg){
//					errorMessage.put("M4313", JErrorMessage.getMessageValue("M4313"));
//				}else{
//					JErrorMessage.show("M4313", null);
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * @param nameKanji the nameKanji to set
//	 */
//	public boolean setNameKanji(String nameKanji) {
//		isValidationAsDataSet = false;
//		this.nameKanji = JValidate.validateNameKanji(nameKanji);
//
//		if( this.nameKanji == null ) {
//			JErrorMessage.show("M4314", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param nameKana the nameKana to set
//	 */
//	public boolean setNameKana(String nameKana,boolean flg) {
//		isValidationAsDataSet = false;
//		this.nameKana = JValidate.validateNameKana(nameKana);
//
//		if( this.nameKana == null ) {
//			if (flg){
//				errorMessage.put("M4315", JErrorMessage.getMessageValue("M4315"));
//			}else{
//				JErrorMessage.show("M4315", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * @param nameTsusyou the nameTsusyou to set
//	 */
//	public boolean setNameTsusyou(String nameTsusyou) {
//		isValidationAsDataSet = false;
//		this.nameTsusyou = JValidate.validateNameTsusyou(nameTsusyou);
//
//		if( this.nameTsusyou == null ) {
//			JErrorMessage.show("M4316", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param birthday the birthday to set
//	 */
//	public boolean setBirthday(String birthday,boolean flg) {
//		isValidationAsDataSet = false;
//		this.birthday = JValidate.validateDate(birthday);
//
//		if( this.birthday == null) {
//			if (flg){
//				errorMessage.put("M4317", JErrorMessage.getMessageValue("M4317"));
//			}else{
//				JErrorMessage.show("M4317", null);
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param sex is "Male" or "Female"
//	 */
//	public boolean setSex(String sex,boolean flg) {
//		isValidationAsDataSet = false;
//		this.sex = JValidate.validateSex(sex);
//
//		if( this.sex == null ) {
//			if (flg){
//				errorMessage.put("M4318", JErrorMessage.getMessageValue("M4318"));
//			}else{
//				JErrorMessage.show("M4318", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * @param zipcode the zipcode to set
//	 */
//	public boolean setZipcode(String zipcode,boolean flg) {
//		isValidationAsDataSet = false;
//		this.zipcode = JValidate.validateZipcode(zipcode);
//
//		if( this.zipcode == null ) {
//			if (flg){
//				errorMessage.put("M4319", JErrorMessage.getMessageValue("M4319"));
//			}else{
//				JErrorMessage.show("M4319", null);
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param address the address to set
//	 */
//	public boolean setAddress(String address,boolean flg) {
//		isValidationAsDataSet = false;
//		String wk_address = JValidate.validateAddress(address);
//
//		if( wk_address == null ) {
//			if(flg){
//				errorMessage.put("M4320", JErrorMessage.getMessageValue("M4320"));
//			}else{
//				JErrorMessage.show("M4320", null);
//				return false;
//			}
//		}
//		// del s.inoue 2009/10/21
//		// this.address = JValidate.cov_unicodesjis(wk_address);
//		this.address = wk_address;
//
//		return true;
//	}
//
//	/**
//	 * @param homePhone the homePhone to set
//	 */
//	public boolean setHomePhone(String homePhone) {
//		isValidationAsDataSet = false;
//		this.homePhone = JValidate.validatePhoneNumber(homePhone);
//
//		if( this.homePhone == null ) {
//			JErrorMessage.show("M4321", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param faxNumber the faxNumber to set
//	 */
//	public boolean setFaxNumber(String faxNumber) {
//		isValidationAsDataSet = false;
//		this.faxNumber = JValidate.validatePhoneNumber(faxNumber);
//
//		if( this.faxNumber == null ) {
//			JErrorMessage.show("M4322", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param cellPhone the cellPhone to set
//	 */
//	public boolean setCellPhone(String cellPhone) {
//		isValidationAsDataSet = false;
//		this.CellPhone = JValidate.validatePhoneNumber(cellPhone);
//
//		if( this.CellPhone == null ) {
//			JErrorMessage.show("M4323", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param mail the eMail to set
//	 */
//	public boolean setEMail(String mail) {
//		isValidationAsDataSet = false;
//		this.EMail = JValidate.validateEMAIL(mail);
//
//		if( this.EMail == null ) {
//			JErrorMessage.show("M4324", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param mobileMail the mobileMail to set
//	 */
//	public boolean setMobileMail(String mobileMail) {
//		isValidationAsDataSet = false;
//		this.mobileMail = JValidate.validateEMAIL(mobileMail);
//
//		if( this.mobileMail == null ) {
//			JErrorMessage.show("M4325", null);
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * @param madoguchiKihonSyubetu the madoguchiKihonSyubetu to set
//	 */
//	public boolean setMadoguchiKihonSyubetu(int madoguchiKihonSyubetu) {
//		isValidationAsDataSet = false;
//
//		this.madoguchiKihonSyubetu = String.valueOf(madoguchiKihonSyubetu);
//
//		return true;
//	}
//
//	/**
//	 * @param madoguchiSyousaiSyubetu the madoguchiSyousaiSyubetu to set
//	 */
//	public boolean setMadoguchiSyousaiSyubetu(int madoguchiSyousaiSyubetu) {
//		isValidationAsDataSet = false;
//		this.madoguchiSyousaiSyubetu = String.valueOf(madoguchiSyousaiSyubetu);
//
//		return true;
//	}
//
//	/**
//	 * @param madoguchiTsuikaSyubetu the madoguchiTsuikaSyubetu to set
//	 */
//	public boolean setMadoguchiTsuikaSyubetu(int madoguchiTsuikaSyubetu) {
//		isValidationAsDataSet = false;
//		this.madoguchiTsuikaSyubetu = String.valueOf(madoguchiTsuikaSyubetu);
//
//		return true;
//	}
//
//	/**
//	 * @param madoguchiDockSyubetu the madoguchiDockSyubetu to set
//	 */
//	public boolean setMadoguchiDockSyubetu(int madoguchiDockSyubetu) {
//		isValidationAsDataSet = false;
//		this.madoguchiDockSyubetu = String.valueOf(madoguchiDockSyubetu);
//
//		return true;
//	}
//
//	/**
//	 *
//	 * @param madoguchiKihon the madoguchiKihon to set
//	 */
//	public boolean setMadoguchiKihon(String madoguchiKihon,boolean flg,boolean hl7flg) {
//		isValidationAsDataSet = false;
//
//
//		//１：受診者の負担無しの場合
//		switch (Integer.valueOf(this.getMadoguchiKihonSyubetu()))
//		{
//
//		//受診者の負担無しの場合
//		case 0:
//		case 1:
//
//			//０を格納する
//			this.madoguchiKihon = "000000";
//			return true;
//
//		//受診者は定額負担の場合
//		case 2:
//			if (hl7flg){
//				this.madoguchiKihon = JValidate.validateMadoguchi_HL7(madoguchiKihon);
//			}else{
//				this.madoguchiKihon = JValidate.validateMadoguchiAmount(madoguchiKihon);
//			}
//			if( this.madoguchiKihon == null ) {
//				if (flg){
//					errorMessage.put("M4330", JErrorMessage.getMessageValue("M4330"));
//				}else{
//					JErrorMessage.show("M4330", null);
//					return false;
//				}
//			}
//			return true;
//
//		//受診者は定率負担
//		case 3:
//			if (hl7flg){
//				this.madoguchiKihon = JValidate.validateMadoguchi_HL7(madoguchiKihon);
//			}else{
//				this.madoguchiKihon = JValidate.validateMadoguchiPercent(madoguchiKihon);
//			}
//			if( this.madoguchiKihon == null ) {
//				if (flg){
//					errorMessage.put("M4331", JErrorMessage.getMessageValue("M4331"));
//				}else{
//					JErrorMessage.show("M4331", null);
//					return false;
//				}
//			}
//
//			return true;
//
////		//保険者の上限負担額
////		case 4:
////			this.madoguchiKihon = JValidate.validateMadoguchiAmount(madoguchiKihon);
////			if( this.madoguchiKihon == null ) {
////				JErrorMessage.show("M4332", null);
////				return false;
////			}
////
////			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 *
//	 * @param madoguchiSyousai the madoguchiSyousai to set
//	 */
//	public boolean setMadoguchiSyousai(String madoguchiSyousai,boolean flg,boolean hl7flg) {
//		isValidationAsDataSet = false;
//
//
//		//１：受診者の負担無しの場合
//		switch (Integer.valueOf(this.getMadoguchiSyousaiSyubetu()))
//		{
//
//		//受診者の負担無しの場合
//		case 0:
//		case 1:
//			//０を格納する
//			this.madoguchiSyousai = "000000";
//			return true;
//
//		//受診者は定額負担の場合
//		case 2:
//			if (hl7flg){
//				this.madoguchiSyousai = JValidate.validateMadoguchi_HL7(madoguchiSyousai);
//			}else{
//				this.madoguchiSyousai = JValidate.validateMadoguchiAmount(madoguchiSyousai);
//			}
//			if( this.madoguchiSyousai == null ) {
//				if (flg){
//					errorMessage.put("M4333", JErrorMessage.getMessageValue("M4333"));
//				}else{
//					JErrorMessage.show("M4333", null);
//					return false;
//				}
//			}
//			return true;
//
//		//受診者は定率負担
//		case 3:
//			if (hl7flg){
//				this.madoguchiSyousai = JValidate.validateMadoguchi_HL7(madoguchiSyousai);
//			}else{
//				this.madoguchiSyousai = JValidate.validateMadoguchiPercent(madoguchiSyousai);
//			}
//			if( this.madoguchiSyousai == null ) {
//				if (flg){
//					errorMessage.put("M4334", JErrorMessage.getMessageValue("M4334"));
//				}else{
//					JErrorMessage.show("M4334", null);
//					return false;
//				}
//			}
//			return true;
//
////		//保険者の上限負担額
////		case 4:
////			this.madoguchiSyousai = JValidate.validateMadoguchiAmount(madoguchiSyousai);
////			if( this.madoguchiSyousai == null ) {
////				JErrorMessage.show("M4335", null);
////				return false;
////			}
////
////			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * @param madoguchiTsuika the madoguchiTsuika to set
//	 */
//	public boolean setMadoguchiTsuika(String madoguchiTsuika,boolean flg,boolean hl7flg) {
//		isValidationAsDataSet = false;
//
//
//		//１：受診者の負担無しの場合
//		switch (Integer.valueOf(this.getMadoguchiTsuikaSyubetu()))
//		{
//
//		//受診者の負担無しの場合
//		case 0:
//		case 1:
//
//			//０を格納する
//			this.madoguchiTsuika = "000000";
//			return true;
//
//		//受診者は定額負担の場合
//		case 2:
//			if (hl7flg){
//				this.madoguchiTsuika = JValidate.validateMadoguchi_HL7(madoguchiTsuika);
//			}else{
//				this.madoguchiTsuika = JValidate.validateMadoguchiAmount(madoguchiTsuika);
//			}
//			if( this.madoguchiTsuika == null ) {
//				if (flg){
//					errorMessage.put("M4336", JErrorMessage.getMessageValue("M4336"));
//				}else{
//					JErrorMessage.show("M4336", null);
//					return false;
//				}
//			}
//			return true;
//
//		//受診者は定率負担
//		case 3:
//			if (hl7flg){
//				this.madoguchiTsuika = JValidate.validateMadoguchi_HL7(madoguchiTsuika);
//			}else{
//				this.madoguchiTsuika = JValidate.validateMadoguchiPercent(madoguchiTsuika);
//			}
//			if( this.madoguchiTsuika == null ) {
//				if (flg){
//					errorMessage.put("M4337", JErrorMessage.getMessageValue("M4337"));
//				}else{
//					JErrorMessage.show("M4337", null);
//					return false;
//				}
//			}
//
//			return true;
//
////		//保険者の上限負担額
////		case 4:
////			this.madoguchiTsuika = JValidate.validateMadoguchiAmount(madoguchiTsuika);
////			if( this.madoguchiTsuika == null ) {
////				JErrorMessage.show("M4338", null);
////				return false;
////			}
////
////			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * @param madoguchiDock the madoguchiDock to set
//	 */
//	public boolean setMadoguchiDock(String madoguchiDock,boolean flg,boolean hl7flg) {
//		isValidationAsDataSet = false;
//
//
//		//１：受診者の負担無しの場合
//		switch (Integer.valueOf(this.getMadoguchiDockSyubetu()))
//		{
//
//		//受診者の負担無しの場合
//		case 0:
//		case 1:
//
//			//０を格納する
//			this.madoguchiDock = "000000";
//			return true;
//
//		//受診者は定額負担の場合
//		case 2:
//			// edit s.inoue 2010/11/24
//			if (hl7flg){
//				this.madoguchiDock = JValidate.validateMadoguchi_HL7(madoguchiDock);
//			}else{
//				this.madoguchiDock = JValidate.validateMadoguchiAmount(madoguchiDock);
//			}
//			if( this.madoguchiDock == null ) {
//				if (flg){
//					errorMessage.put("M4339", JErrorMessage.getMessageValue("M4339"));
//				}else{
//					JErrorMessage.show("M4339", null);
//					return false;
//				}
//			}
//
//			return true;
//
//		//受診者は定率負担
//		case 3:
//			if (hl7flg){
//				this.madoguchiDock = JValidate.validateMadoguchi_HL7(madoguchiDock);
//			}else{
//				this.madoguchiDock = JValidate.validateMadoguchiPercent(madoguchiDock);
//			}
//			if( this.madoguchiDock == null ) {
//				if (flg){
//					errorMessage.put("M4340", JErrorMessage.getMessageValue("M4340"));
//				}else{
//					JErrorMessage.show("M4340", null);
//					return false;
//				}
//			}
//			return true;
//
////		//保険者の上限負担額
////		case 4:
////			this.madoguchiDock = JValidate.validateMadoguchiAmount(madoguchiDock);
////			if( this.madoguchiDock == null ) {
////				JErrorMessage.show("M4341", null);
////				return false;
////			}
////
////			return true;
//		}
//
//		return false;
//	}
//
//	// add s.inoue 2010/12/01
//	/*
//	 * 受付ID採番用diff
//	 */
//	public int getdiff() {
//		return diff;
//	}
//	/**
//	 * @param diff the chiban to set
//	 */
//	public void setDiff(int diff) {
//		this.diff = diff;
//	}
//
//	/**
//	 * @param chiban the chiban to set
//	 */
//	public boolean setChiban(String chiban) {
//		this.isValidationAsDataSet = false;
//		this.chiban = JValidate.validateChiban(chiban);
//
//		if( this.chiban == null ) {
//			JErrorMessage.show("M4342", null);
//			return false;
//		}
//
//		return true;
//	}
//
//
//	/**
//	 * @param isValidationAsDataSet the isValidationAsDataSet to set
//	 */
//	public void setValidationAsDataSet() {
//		this.isValidationAsDataSet = true;
//	}
//
//	public String getNendo() {
//		return nendo;
//	}
//
//	public boolean setNendo(String nendo) {
//		this.isValidationAsDataSet = false;
//		this.nendo = nendo;
//		return true;
//	}
//
//	public String getUketukeID() {
//		return uketukeID;
//	}
//
//	public boolean setUketukeID(String uketukeID) {
//		this.isValidationAsDataSet = false;
//		this.uketukeID = uketukeID;
//		return true;
//	}
//
//	// add ver2 s.inoue 2009/05/18
//	public String getpreUketukeID() {
//		return preUketukeID;
//	}
//
//	public void setpreUketukeID(String uketukeID) {
//		this.preUketukeID = uketukeID;
//	}
//
//	/**
//	 * @return orcaVersion
//	 */
//	public String getOrcaVersion() {
//		return orcaVersion;
//	}
//
//	/**
//	 * @param orcaVersion 設定する orcaVersion
//	 */
//	public void setOrcaVersion(String orcaVersion) {
//		this.orcaVersion = orcaVersion;
//	}
//
//	/* Added 2008/04/28 若月  */
//	/* --------------------------------------------------- */
//	public String getHokenjyaJougenDoc() {
//		return hokenjyaJougenDoc;
//	}
//
//	public boolean setHokenjyaJougenDoc(String hokenjyaJougenDoc,boolean flg) {
//		this.isValidationAsDataSet = false;
//
//		this.hokenjyaJougenDoc = JValidate.validateMadoguchiAmount(hokenjyaJougenDoc);
//		if( this.hokenjyaJougenDoc == null ) {
//			if (flg){
//				errorMessage.put("M4341", JErrorMessage.getMessageValue("M4341"));
//			}else{
//				JErrorMessage.show("M4341", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public String getHokenjyaJougenKihon() {
//		return hokenjyaJougenKihon;
//	}
//
//	// add s.inoue 2010/11/24
//	public boolean setHokenjyaJougenKihon_HL7(String hokenjyaJougenKihon) {
//		this.isValidationAsDataSet = false;
//
//		this.hokenjyaJougenKihon = JValidate.validateMadoguchi_HL7(hokenjyaJougenKihon);
//		if( this.hokenjyaJougenKihon == null ) {
//			JErrorMessage.show("M4332", null);
//			return false;
//		}
//		return true;
//	}
//
//	public boolean setHokenjyaJougenKihon(String hokenjyaJougenKihon,boolean flg) {
//		this.isValidationAsDataSet = false;
//
//		this.hokenjyaJougenKihon = JValidate.validateMadoguchiAmount(hokenjyaJougenKihon);
//		if( this.hokenjyaJougenKihon == null ) {
//			if (flg){
//				errorMessage.put("M4332", JErrorMessage.getMessageValue("M4332"));
//			}else{
//				JErrorMessage.show("M4332", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public String getHokenjyaJougenSyousai() {
//		return hokenjyaJougenSyousai;
//	}
//
//	public boolean setHokenjyaJougenSyousai(String hokenjyaJougenSyousai,boolean flg) {
//		this.isValidationAsDataSet = false;
//
//		this.hokenjyaJougenSyousai = JValidate.validateMadoguchiAmount(hokenjyaJougenSyousai);
//		if( this.hokenjyaJougenSyousai == null ) {
//			if (flg){
//				errorMessage.put("M4335", JErrorMessage.getMessageValue("M4335"));
//			}else{
//				JErrorMessage.show("M4335", null);
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	public String getHokenjyaJougenTsuika() {
//		return hokenjyaJougenTsuika;
//	}
//
//	public boolean setHokenjyaJougenTsuika(String hokenjyaJougenTsuika,boolean flg) {
//		this.isValidationAsDataSet = false;
//
//		this.hokenjyaJougenTsuika = JValidate.validateMadoguchiAmount(hokenjyaJougenTsuika);
//		if( this.hokenjyaJougenTsuika == null ) {
//			if (flg){
//				errorMessage.put("M4338", JErrorMessage.getMessageValue("M4338"));
//			}else{
//				JErrorMessage.show("M4338", null);
//				return false;
//			}
//		}
//		return true;
//	}
//
//	public String getMadoguchiSonota() {
//		return madoguchiSonota;
//	}
//
//	public boolean setMadoguchiSonota(String madoguchiSonota,boolean flg) {
//		this.isValidationAsDataSet = false;
//
//		this.madoguchiSonota = JValidate.validateMadoguchiSonota(madoguchiSonota);
//		if( this.madoguchiSonota == null ) {
//			if (flg){
//				errorMessage.put("M4343", JErrorMessage.getMessageValue("M4343"));
//			}else{
//				JErrorMessage.show("M4343", null);
//				return false;
//			}
//		}
//
//		return true;
//	}
//}