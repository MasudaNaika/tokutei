package jp.or.med.orca.jma_tokutei.common.sql.model;

/**
 * T_KENSAKEKA_SONOTAを表すレコードモデル
 * @author nishiyama
 *
 */
public class TKensakekaSonota implements RecordModel {

	private String HIHOKENJYASYO_KIGOU;

	private String HIHOKENJYASYO_NO;

	private Integer KENSA_NENGAPI;

	private String KOUMOKU_CD;

	private String KEKA_TI;

	private String KOMENTO;

	private Integer H_L;

	private Integer HANTEI;

	private Integer NENDO;

	private Long UKETUKE_ID;
	/* Edit 2008/07/23 井上 */
	/* --------------------------------------------------- */
	private Integer JISI_KBN;
	private Integer KEKA_TI_KEITAI; 
	/* --------------------------------------------------- */
	
	public Integer getH_L() {
		return H_L;
	}

	public void setH_L(Integer h_l) {
		H_L = h_l;
	}

	public Integer getHANTEI() {
		return HANTEI;
	}

	public void setHANTEI(Integer hantei) {
		HANTEI = hantei;
	}

	public String getHIHOKENJYASYO_KIGOU() {
		return HIHOKENJYASYO_KIGOU;
	}

	public void setHIHOKENJYASYO_KIGOU(String hihokenjyasyo_kigou) {
		HIHOKENJYASYO_KIGOU = hihokenjyasyo_kigou;
	}

	public String getHIHOKENJYASYO_NO() {
		return HIHOKENJYASYO_NO;
	}

	public void setHIHOKENJYASYO_NO(String hihokenjyasyo_no) {
		HIHOKENJYASYO_NO = hihokenjyasyo_no;
	}

	public String getKEKA_TI() {
		return KEKA_TI;
	}

	public void setKEKA_TI(String keka_ti) {
		KEKA_TI = keka_ti;
	}

	public Integer getKENSA_NENGAPI() {
		return KENSA_NENGAPI;
	}

	public void setKENSA_NENGAPI(Integer kensa_nengapi) {
		KENSA_NENGAPI = kensa_nengapi;
	}

	public String getKOMENTO() {
		return KOMENTO;
	}

	public void setKOMENTO(String komento) {
		KOMENTO = komento;
	}

	public String getKOUMOKU_CD() {
		return KOUMOKU_CD;
	}

	public void setKOUMOKU_CD(String koumoku_cd) {
		KOUMOKU_CD = koumoku_cd;
	}

	public Integer getNENDO() {
		return NENDO;
	}

	public void setNENDO(Integer nendo) {
		NENDO = nendo;
	}

	public Long getUKETUKE_ID() {
		return UKETUKE_ID;
	}

	public void setUKETUKE_ID(Long uketuke_id) {
		UKETUKE_ID = uketuke_id;
	}
	/* ADD 2008/07/28 井上 */
	/* --------------------------------------------------- */
	public Integer getKensaJISI_KBN() {
		if (JISI_KBN == null){
			JISI_KBN = 1;
		}
		return JISI_KBN;
		//return (JISI_KBN != null) ? JISI_KBN : 1;		
	}
	public void setJISI_KBN(Integer jisi_kbn) {
		if (jisi_kbn == null){
			JISI_KBN = 1;
		}else{
			JISI_KBN = jisi_kbn;	
		}
	}
	
	public Integer getKEKA_TI_KEITAI() {
		return (KEKA_TI_KEITAI != null) ? KEKA_TI_KEITAI : null;		
	}
	public void setKEKA_TI_KEITAI(Integer keka_ti_keitai) {
		KEKA_TI_KEITAI = keka_ti_keitai;
	}	
	/* --------------------------------------------------- */
}
