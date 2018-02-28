package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei;

import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JIppanHanteiProcessData {
	
	private static final String JISI_KBN = "1"; //jisi
	
	String h_l = new String("");
	String hantei = new String("");
	// add s.inoue 20080901 
	String jishi_Kbn = new String("");
	
	/**
	 * @return the h_l
	 */
	public String getH_l() {
		return h_l;
	}
	/**
	 * @return the hantei
	 */
	public String getHantei() {
		return hantei;
	}
	
	//	 add s.inoue 20080901 
	/**
	 * @return the hantei
	 */
	public String getJISI_KBN() {
//		if( this.jishi_Kbn == null ||
//				this.jishi_Kbn.equals(""))
//			return JISI_KBN;
		if ( this.jishi_Kbn == null){
			return JISI_KBN;
		}else if (this.jishi_Kbn.equals("")){
			return JISI_KBN;
		}
			return jishi_Kbn;
	}
	
	/**
	 * @param h_l the h_l to set
	 */
	public boolean setH_l(String h_l) {
		this.h_l = JValidate.validateHLKubun(h_l);
		
		if( this.h_l == null )
			return false;
		
		return true;
	}
	/**
	 * @param hantei the hantei to set
	 */
	public boolean setHantei(String hantei) {
		this.hantei = JValidate.validatehanteiKekka(hantei);
		
		if( this.hantei == null )
			return false;
		
		return true;
	}
	
	//	 add s.inoue 20080901 
	/** 
	 * @param jishi_kbn to set
	 */
	public boolean setJisi_KBN(String jishi_Kbn) {
		//this.jishi_Kbn = JValidate.validatehanteiKekka(jishi_Kbn);
		this.jishi_Kbn = jishi_Kbn;
		if( this.jishi_Kbn == null )
			return false;
		
		return true;
	}
	
	
}
