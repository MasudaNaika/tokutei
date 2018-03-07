package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class JKikanLogListFrameData extends ValueObjectImpl {
	
	private static final long serialVersionUID = 9115451489913651093L;	// edit n.ohkubo 2014/10/01　追加
	
//	private String LOG_DATE;
	private String LOG_MESSAGE_LEVEL;
	private String LOG_MESSAGE;
	private String LOG_ERROR_PLACE;
	private String UPDATE_TIMESTAMP;
	private String UPDATE_TIMESTAMP2;	// edit n.ohkubo 2014/10/01　追加

//	/**
//	 * @return the LOG_DATE
//	 */
//	public String getLOG_DATE() {
//		if (LOG_DATE == null ) {
//			LOG_DATE = "";
//		}
//		return LOG_DATE;
//	}
	/**
	 * @return the ERROR_PLACE
	 */
	public String getLOG_ERROR_PLACE() {
		if (LOG_ERROR_PLACE == null ) {
			LOG_ERROR_PLACE = "";
		}
		return LOG_ERROR_PLACE;
	}
	/**
	 * @return the zipcode
	 */
	public String getLOG_MESSAGE_LEVEL() {
		if (LOG_MESSAGE_LEVEL == null ) {
			LOG_MESSAGE_LEVEL = "";
		}
		return LOG_MESSAGE_LEVEL;
	}
	/**
	 * @return the ADRS
	 */
	public String getLOG_MESSAGE() {
		if (LOG_MESSAGE == null ) {
			LOG_MESSAGE = "";
		}
		return LOG_MESSAGE;
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


//	/**
//	 * @param HKNJANUM the HKNJANUM to set
//	 */
//	public void setLOG_DATE(String LOG_DATE) {
//
//		this.LOG_DATE = LOG_DATE;
//
//	}

	/**
	 * @param ERROR_PLACE to set
	 */
	public void setLOG_ERROR_PLACE(String LOG_ERROR_PLACE) {

		this.LOG_ERROR_PLACE = LOG_ERROR_PLACE;

	}

	/**
	 * @param zipcode the post to set
	 */
	public void setLOG_MESSAGE_LEVEL(String LOG_MESSAGE_LEVEL) {

		this.LOG_MESSAGE_LEVEL = LOG_MESSAGE_LEVEL;

	}

	/**
	 * @param ADRS the address to set
	 */
	public void setLOG_MESSAGE(String LOG_MESSAGE) {

		this.LOG_MESSAGE = LOG_MESSAGE;

	}

	/**
	 * @param tel the tEL to set
	 */
	public void setUPDATE_TIMESTAMP(String UPDATE_TIMESTAMP) {

		this.UPDATE_TIMESTAMP = UPDATE_TIMESTAMP;

	}

	// edit n.ohkubo 2014/10/01　追加 start
	public String getUPDATE_TIMESTAMP2() {
		if (UPDATE_TIMESTAMP2 == null ) {
			UPDATE_TIMESTAMP2 = "";
		}
		return UPDATE_TIMESTAMP2;
	}
	public void setUPDATE_TIMESTAMP2(String UPDATE_TIMESTAMP2) {

		this.UPDATE_TIMESTAMP2 = UPDATE_TIMESTAMP2;
	}
	// edit n.ohkubo 2014/10/01　追加 end
}
