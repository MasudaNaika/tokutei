package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JUserMaintenanceListFrameData extends ValueObjectImpl {

	private String USER_NAME;
	private String PASS;
	private String KENGEN;

	/**
	 * @return the USER_NAME
	 */
	public String getUSER_NAME() {
		return USER_NAME;
	}
	/**
	 * @return the PASS
	 */
	public String getPASS() {
		return PASS;
	}
	/**
	 * @return the kengen
	 */
	public String getKENGEN() {
		return KENGEN;
	}

	/**
	 * @param USER_NAME the USER_NAME to set
	 */
	public void setUSER_NAME(String USER_NAME) {
		this.USER_NAME = USER_NAME;
	}
	/**
	 * @param PASS to set
	 */
	public void setPASS(String PASS) {
		this.PASS = PASS;
	}
	/**
	 * @param KENGEN the post to set
	 */
	public void setKENGEN(String KENGEN) {
		this.KENGEN = KENGEN;
	}
}