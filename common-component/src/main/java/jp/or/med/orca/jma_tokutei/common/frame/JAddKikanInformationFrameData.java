package jp.or.med.orca.jma_tokutei.common.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JAddKikanInformationFrameData {
	private String kikanNumber = null;
	private String sendSourceKikan = null;
	private String kikanName = null;
	private String zipcode = null;
	private String address = null;
	private String chiban = null;
	private String tel = null;
	private String ORCA = null;
	private String ORCAIpAddress = null; 
	private String ORCAPort = null; 
	private String ORCADatabase = null; 
	private String ORCAProtocol = null; 
	private String ORCAUser = null; 
	private String ORCAPassword = null; 
	private String ORCAEncode = null; 

	/* Added 2008/04/20 若月 日レセユーザ・パスワード */
	/* --------------------------------------------------- */
	private String ORCANichireseUser = null; 
	private String ORCANichiresePassword = null; 
	/* --------------------------------------------------- */
	
	/* Added 2008/07/22 若月  */
	/* --------------------------------------------------- */
	private boolean useOrcaIdDigit = false; 
	private String orcaIdDigit = null; 
//	private int orcaIdDigit = 0; 
	/* --------------------------------------------------- */
	
	private boolean isValidateAsDataSet = false;
	
	/* Added 2008/05/04 若月  */
	/* --------------------------------------------------- */
	public JAddKikanInformationFrameData() {
		this.initialize();
	}

	public void initialize(){
		this.kikanNumber = "";
		this.sendSourceKikan = "";
		this.kikanName = "";
		this.zipcode = "";
		this.address = "";
		this.chiban = "";
		this.tel = "";
		this.ORCA = "";
		this.ORCAIpAddress = ""; 
		this.ORCAPort = ""; 
		this.ORCADatabase = ""; 
		this.ORCAProtocol = ""; 
		this.ORCAUser = ""; 
		this.ORCAPassword = ""; 
		this.ORCAEncode = ""; 
		
		/* Added 2008/05/21 若月 日レセユーザ・パスワード */
		/* --------------------------------------------------- */
		this.ORCANichireseUser = ""; 
		this.ORCANichiresePassword = ""; 
		/* --------------------------------------------------- */
	}
	/* --------------------------------------------------- */
	
	/**
	 * @return the kikanNumber
	 */
	public String getKikanNumber() {
		return kikanNumber;
	}
	/**
	 * @return the sourceKikan
	 */
	public String getSendSourceKikan() {
		return sendSourceKikan;
	}
	/**
	 * @return the kikanName
	 */
	public String getKikanName() {
		return kikanName;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return the chiban
	 */
	public String getChiban() {
		return chiban;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}
	/**
	 * @return the oRCA
	 */
	public String getORCA() {
		return ORCA;
	}
	
	/**
	 * @param kikanNumber the kikanNumber to set
	 */
	public boolean setKikanNumber(String kikanNumber) {
		this.isValidateAsDataSet = false;
		this.kikanNumber = JValidate.validateKikanNumber(kikanNumber);
		
		if( this.kikanNumber == null )
		{
			JErrorMessage.show("M9610", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param sourceKikan the sourceKikan to set
	 */
	public boolean setSendSourceKikan(String sendsourceKikan) {
		this.isValidateAsDataSet = false;
		this.sendSourceKikan = JValidate.validateSendSourceKikan(sendsourceKikan);
		
		if( this.sendSourceKikan == null )
		{
			JErrorMessage.show("M9611", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param kikanName the kikanName to set
	 */
	public boolean setKikanName(String kikanName) {
		this.isValidateAsDataSet = false;
		this.kikanName = JValidate.validateKikanName(kikanName);
		
		if( this.kikanName == null )
		{
			JErrorMessage.show("M9612", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param zipcode the zipcode to set
	 */
	public boolean setZipcode(String zipcode) {
		this.isValidateAsDataSet = false;
		this.zipcode = JValidate.validateZipcode(zipcode);
		
		if( this.zipcode == null )
		{
			JErrorMessage.show("M9613", null);
			return false;
		}
		
		return true;
	}
	
//	/**
//	 * @param address the address to set
//	 */
//	public boolean setAddress(String address) {
//		this.isValidateAsDataSet = false;
//		this.address = JValidate.validateAddress(address);
//		
//		if( this.address == null )
//		{
//			JErrorMessage.show("M9614", null);
//			return false;
//		}
//		
//		
//		return true;
//	}
//	
//	/**
//	 * @param chiban the chiban to set
//	 */
//	public boolean setChiban(String chiban) {
//		this.isValidateAsDataSet = false;
//		this.chiban = JValidate.validateChiban(chiban);
//		
//		if( this.chiban == null )
//		{
//			JErrorMessage.show("M9615", null);
//			return false;
//		}
//		
//		return true;
//	}

	/**
	 * @param chiban the chiban to set
	 */
	public boolean setAddressAndChiban(String address, String chiban) {
		this.isValidateAsDataSet = false;
		
		boolean retValue = false;
		
		/* 住所の検証を行なう。 */
		this.address = JValidate.validateKikanAddress(address);
		if (this.address != null) {
			
			/* 地番方書の検証を行なう。 */
			this.chiban = JValidate.validateKikanChiban(chiban);
			if (this.chiban != null) {
				
				/* 住所と地番方書を合わせた文字列の検証を行なう。 */
				boolean validate = JValidate.validateKikanAddressAndChiban(address, chiban);
				if( validate ){
					retValue = true;
				}
				else {
					JErrorMessage.show("M9618", null);
				}
			}
			else {
				JErrorMessage.show("M9615", null);
			}
		}
		else {
			JErrorMessage.show("M9614", null);
		}
		
		return retValue;
	}
	
	/**
	 * @param tel the tel to set
	 */
	public boolean setTel(String tel) {
		this.isValidateAsDataSet = false;
		this.tel = JValidate.validatePhoneNumber(tel);
		
		if( this.tel == null )
		{
			JErrorMessage.show("M9616", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param orca the ORCA to set
	 */
	public boolean setORCA(boolean orca) {
		this.isValidateAsDataSet = false;
		this.ORCA = JValidate.validateORCAFlag(orca);
		
		if( this.ORCA == null )
		{
			JErrorMessage.show("M9617", null);
			return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * @return the oRCAIpAddress
	 */
	public String getORCAIpAddress() {
		return ORCAIpAddress;
	}
	/**
	 * @return the oRCAPort
	 */
	public String getORCAPort() {
		return ORCAPort;
	}
	/**
	 * @return the oRCADatabase
	 */
	public String getORCADatabase() {
		return ORCADatabase;
	}
	/**
	 * @return the oRCAProtocol
	 */
	public String getORCAProtocol() {
		return ORCAProtocol;
	}
	/**
	 * @return the oRCAUser
	 */
	public String getORCAUser() {
		return ORCAUser;
	}
	/**
	 * @return the oRCAPassword
	 */
	public String getORCAPassword() {
		return ORCAPassword;
	}
	/**
	 * @return the oRCAEncode
	 */
	public String getORCAEncode() {
		return ORCAEncode;
	}
	
	/**
	 * @param ipAddress the oRCAIpAddress to set
	 */
	public boolean setORCAIpAddress(String ipAddress) {
		this.isValidateAsDataSet = false;
		this.ORCAIpAddress = JValidate.validateIPAddress(ipAddress);
		
		if( this.ORCAIpAddress == null ) {
			JErrorMessage.show("M9620", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param port the oRCAPort to set
	 */
	public boolean setORCAPort(String port) {
		this.isValidateAsDataSet = false;
		this.ORCAPort = JValidate.validatePortNumber(port);
		
		if( this.ORCAPort == null ) {
			JErrorMessage.show("M9621", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param database the oRCADatabase to set
	 */
	public boolean setORCADatabase(String database) {
		this.isValidateAsDataSet = false;
		this.ORCADatabase = JValidate.validateORCADBName(database);
		
		if( this.ORCADatabase == null ) {
			JErrorMessage.show("M9622", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param protocol the oRCAProtocol to set
	 */
	public boolean setORCAProtocol(String protocol) {
		this.isValidateAsDataSet = false;
		this.ORCAProtocol = JValidate.validateORCAProtocol(protocol);
		
		if( this.ORCAProtocol == null ) {
			JErrorMessage.show("M9623", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param user the oRCAUser to set
	 */
	public boolean setORCAUser(String user) {
		this.isValidateAsDataSet = false;
		this.ORCAUser = JValidate.validateORCADBUserName(user);
		
		if( this.ORCAUser == null ) {
			JErrorMessage.show("M9624", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param password the oRCApassword to set
	 */
	public boolean setORCAPassword(String password) {
		this.isValidateAsDataSet = false;
		this.ORCAPassword = JValidate.validateORCAPassword(password);
		
		if( this.ORCAPassword == null ) {
			JErrorMessage.show("M9625", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param encode the oRCAEncode to set
	 */
	public boolean setORCAEncode(String encode) {
		this.isValidateAsDataSet = false;
		this.ORCAEncode = JValidate.validateORCAEncord(encode);
		
		if( this.ORCAEncode == null ) {
			JErrorMessage.show("M9626", null);
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
	
	/* Added 2008/04/20 若月 日レセユーザ・パスワード */
	/* --------------------------------------------------- */
	public String getORCANichiresePassword() {
		return ORCANichiresePassword;
	}
	
	public boolean setORCANichiresePassword(String password) {
		
		this.isValidateAsDataSet = false;
		this.ORCANichiresePassword = JValidate.validateORCANichiresePassword(password);
		
		if( this.ORCANichiresePassword == null ) {
			JErrorMessage.show("M9628", null);
			return false;
		}
		
		return true;		
	}
	
	public String getORCANichireseUser() {
		return ORCANichireseUser;
	}
	
	public boolean setORCANichireseUser(String user) {
	
		this.isValidateAsDataSet = false;
		this.ORCANichireseUser = JValidate.validateORCANichireseUserName(user);
		
		if( this.ORCANichireseUser == null ) {
			JErrorMessage.show("M9627", null);
			return false;
		}
		
		return true;	
	}	
	/* --------------------------------------------------- */

	/* Added 2008/07/22 若月  */
	/* --------------------------------------------------- */
//	public String getOrcaIdDigit() {
//		return orcaIdDigit;
//	}
//
//	public boolean setOrcaIdDigit(String orcaIdDigit) {
//		this.isValidateAsDataSet = false;
//		this.orcaIdDigit = JValidate.validateOrcaIdDigit(orcaIdDigit);
//		
//		if( this.ORCANichireseUser == null ) {
//			JErrorMessage.show("M9634", null);
//			return false;
//		}
//		
//		return true;	
//	}

	public String getOrcaIdDigit() {
		return orcaIdDigit;
	}
	
//	public int getOrcaIdDigit() {
//		return orcaIdDigit;
//	}

	public boolean setOrcaIdDigit(String orcaIdDigit) {
		this.isValidateAsDataSet = false;
		
		this.orcaIdDigit = JValidate.validateOrcaIdDigit(orcaIdDigit);
//		this.orcaIdDigit = JValidate.validateOrcaIdDigit(orcaIdDigit);
		
		return true;	
	}

	public boolean isUseOrcaIdDigit() {
		return useOrcaIdDigit;
	}

	public boolean setUseOrcaIdDigit(boolean useOrcaIdDigit) {
		this.isValidateAsDataSet = useOrcaIdDigit;
		this.useOrcaIdDigit = useOrcaIdDigit;
		
		return true;
	}	
	/* --------------------------------------------------- */
}




