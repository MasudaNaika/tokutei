package jp.or.med.orca.jma_tokutei.common.sql.model;

/**
 * 検査結果データ特定を表すレコードモデル
 * @author nishiyama
 *
 */
public class TKensakekaTokutei implements RecordModel {

	private String HIHOKENJYASYO_KIGOU;
	private String HIHOKENJYASYO_NO;
	private Integer KENSA_NENGAPI;
	private Integer K_P_NO;
	private Integer HANTEI_NENGAPI;
	private Integer TUTI_NENGAPI;
	private String KENSA_CENTER_CD;
	private Integer KEKA_INPUT_FLG;
	private Integer SEIKYU_KBN;
	private String KOMENTO;
	private Integer NENDO;
	private Long UKETUKE_ID;
	private String NYUBI_YOUKETU;
	private String SYOKUZEN_SYOKUGO;
	private Integer HENKAN_NITIJI;

	public Integer getHANTEI_NENGAPI() {
		return HANTEI_NENGAPI;
	}

	public void setHANTEI_NENGAPI(Integer hantei_nengapi) {
		HANTEI_NENGAPI = hantei_nengapi;
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

	public Integer getK_P_NO() {
		return K_P_NO;
	}

	public void setK_P_NO(Integer k_p_no) {
		K_P_NO = k_p_no;
	}

	public Integer getKEKA_INPUT_FLG() {
		return KEKA_INPUT_FLG;
	}

	public void setKEKA_INPUT_FLG(Integer keka_input_flg) {
		KEKA_INPUT_FLG = keka_input_flg;
	}

	public String getKENSA_CENTER_CD() {
		return KENSA_CENTER_CD;
	}

	public void setKENSA_CENTER_CD(String kensa_center_cd) {
		KENSA_CENTER_CD = kensa_center_cd;
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

	public Integer getNENDO() {
		return NENDO;
	}

	public void setNENDO(Integer nendo) {
		NENDO = nendo;
	}

	public Integer getSEIKYU_KBN() {
		return SEIKYU_KBN;
	}

	public void setSEIKYU_KBN(Integer seikyu_kbn) {
		SEIKYU_KBN = seikyu_kbn;
	}

	public Integer getTUTI_NENGAPI() {
		return TUTI_NENGAPI;
	}

	public void setTUTI_NENGAPI(Integer tuti_nengapi) {
		TUTI_NENGAPI = tuti_nengapi;
	}

	public Long getUKETUKE_ID() {
		return UKETUKE_ID;
	}

	public void setUKETUKE_ID(Long uketuke_id) {
		UKETUKE_ID = uketuke_id;
	}

	public String getNYUBI_YOUKETU() {
		return this.NYUBI_YOUKETU;
	}

	public void setNYUBI_YOUKETU(String nyubi_youketu) {
		NYUBI_YOUKETU = nyubi_youketu;
	}

	public String getSYOKUZEN_SYOKUGO() {
		return SYOKUZEN_SYOKUGO;
	}

	public void setSYOKUZEN_SYOKUGO(String syokuzen_syokugo) {
		SYOKUZEN_SYOKUGO = syokuzen_syokugo;
	}
	// add ver2 s.inoue 2009/04/02
	public Integer getHENKAN_NITIJI() {
		return HENKAN_NITIJI;
	}

	public void setHENKAN_NITIJI(Integer henkan_nitiji) {
		HENKAN_NITIJI = henkan_nitiji;
	}

}
