package jp.or.med.orca.jma_tokutei.common.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JChangeKikanInformationFrameData {
	private String kikanNumber = "";
	private String sendSourceKikan = "";
	private String kikanName = "";
	private String zipcode = "";
	private String address = "";
	private String chiban = "";
	private String tel = "";
	private String ORCA = "";
	private String ORCAIpAddress = ""; 
	private String ORCAPort = ""; 
	private String ORCADatabase = ""; 
	private String ORCAProtocol = ""; 
	private String ORCAUser = ""; 
	private String ORCAPassword = ""; 
	private String ORCAEncode = ""; 
	private boolean isValidateAsDataSet = false;
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
			JErrorMessage.show("M9710", null);
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
			JErrorMessage.show("M9711", null);
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
			JErrorMessage.show("M9712", null);
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
			JErrorMessage.show("M9713", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param address the address to set
	 */
	public boolean setAddress(String address) {
		this.isValidateAsDataSet = false;
		this.address = JValidate.validateAddress(address);
		
		if( this.address == null )
		{
			JErrorMessage.show("M9714", null);
			return false;
		}
		
		
		return true;
	}
	
	/**
	 * @param chiban the chiban to set
	 */
	public boolean setChiban(String chiban) {
		this.isValidateAsDataSet = false;
		this.chiban = JValidate.validateChiban(chiban);
		
		if( this.chiban == null )
		{
			JErrorMessage.show("M9715", null);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param tel the tel to set
	 */
	public boolean setTel(String tel) {
		this.isValidateAsDataSet = false;
		this.tel = JValidate.validatePhoneNumber(tel);
		
		if( this.tel == null )
		{
			JErrorMessage.show("M9716", null);
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
	 * @return the oRCApassword
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
			JErrorMessage.show("M9720", null);
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
			JErrorMessage.show("M9721", null);
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
			JErrorMessage.show("M9722", null);
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
			JErrorMessage.show("M9723", null);
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
			JErrorMessage.show("M9724", null);
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
			JErrorMessage.show("M9725", null);
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
			JErrorMessage.show("M9726", null);
			return false;
		}
		
		return true;
	}

	/**
	 * @param orca the oRCA to set
	 */
	public boolean setORCA(boolean orca) {
		this.isValidateAsDataSet = false;
		this.ORCA = JValidate.validateORCAFlag(orca);
		
		if( this.ORCA == null )
		{
			JErrorMessage.show("M9717", null);
			return false;
		}
		
		return true;
	}
}