package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JShokenMasterMaintenanceListData extends ValueObjectImpl {
	private String SYOKEN_TYPE;
	private String SYOKEN_TYPE_NAME;
	private String SYOKEN_NO;
	private String SYOKEN_NAME;
	private String UPDATE_TIMESTAMP;

	/**
	 * @return the SYOKEN_TYPE
	 */
	public String getSYOKEN_TYPE() {
		if (SYOKEN_TYPE == null ) {
			SYOKEN_TYPE = "";
		}
		return SYOKEN_TYPE;
	}
	/**
	 * @return the SYOKEN_TYPE_NAME
	 */
	public String getSYOKEN_TYPE_NAME() {
		if (SYOKEN_TYPE_NAME == null ) {
			SYOKEN_TYPE_NAME = "";
		}
		return SYOKEN_TYPE_NAME;
	}
	/**
	 * @return the zipcode
	 */
	public String getSYOKEN_NO() {
		if (SYOKEN_NO == null ) {
			SYOKEN_NO = "";
		}
		return SYOKEN_NO;
	}
	/**
	 * @return the ADRS
	 */
	public String getSYOKEN_NAME() {
		if (SYOKEN_NAME == null ) {
			SYOKEN_NAME = "";
		}
		return SYOKEN_NAME;
	}
	/**
	 * @return the tEL
	 */
	public String getUPDATE_TIMESTAMP() {
		if (UPDATE_TIMESTAMP == null ) {
			UPDATE_TIMESTAMP = "";
		}
		return UPDATE_TIMESTAMP;
	}


	/**
	 * @param HKNJANUM the HKNJANUM to set
	 */
	public void setSYOKEN_TYPE(String SYOKEN_TYPE) {

		this.SYOKEN_TYPE = SYOKEN_TYPE;

	}

	/**
	 * @param SYOKEN_TYPE_NAME to set
	 */
	public void setSYOKEN_TYPE_NAME(String SYOKEN_TYPE_NAME) {

		this.SYOKEN_TYPE_NAME = SYOKEN_TYPE_NAME;

	}

	/**
	 * @param zipcode the post to set
	 */
	public void setSYOKEN_NO(String SYOKEN_NO) {

		this.SYOKEN_NO = SYOKEN_NO;

	}

	/**
	 * @param ADRS the address to set
	 */
	public void setSYOKEN_NAME(String SYOKEN_NAME) {

		this.SYOKEN_NAME = SYOKEN_NAME;

	}

	/**
	 * @param tel the tEL to set
	 */
	public void setUPDATE_TIMESTAMP(String UPDATE_TIMESTAMP) {

		this.UPDATE_TIMESTAMP = UPDATE_TIMESTAMP;

	}

}