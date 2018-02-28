package jp.or.med.orca.jma_tokutei.common.sql.model;

/**
 * T_NAYOSEレコードを表すクラス
 *
 */
public class TNayose implements RecordModel {

	private Long NAYOSE_NO;
	private Long UKETUKE_ID;
	private String UPDATE_TIMESTAMP;

	public Long getNAYOSE_NO() {
		return NAYOSE_NO;
	}
	public void setNAYOSE_NO(Long nayose_no) {
		NAYOSE_NO = nayose_no;
	}

	public Long getUKETUKE_ID() {
		return UKETUKE_ID;
	}
	public void setUKETUKE_ID(Long uketuke_id) {
		UKETUKE_ID = uketuke_id;
	}

	public String getUPDATE_TIMESTAMP() {
		return UPDATE_TIMESTAMP;
	}
	public void setUKETUKE_ID(String updateTimeStamp) {
		UPDATE_TIMESTAMP = updateTimeStamp;
	}

// T_KOJIN_HISTORY用 廃止
//	private Long NAYOSE_NO;
//	private Long UKETUKE_ID;
//	private String PTNUM;
//	private String JYUSHIN_SEIRI_NO;
//	private Integer YUKOU_KIGEN;
//	private String HKNJANUM;
//	private String HIHOKENJYASYO_KIGOU;
//	private String HIHOKENJYASYO_NO;
//	private String NAME;
//	private String KANANAME;
//	private String NICKNAME;
//	private Integer BIRTHDAY;
//	private Integer SEX;
//	private String HOME_POST;
//	private String HOME_ADRS;
//	private String HOME_BANTI;
//	private String HOME_TEL1;
//	private String KEITAI_TEL;
//	private String FAX;
//	private String EMAIL;
//	private String KEITAI_EMAIL;
//	private String KEIYAKU_TORIMATOME;
//	private Integer KOUHUBI;
//	private String SIHARAI_DAIKOU_BANGO;
//	private Integer MADO_FUTAN_K_SYUBETU;
//	private String MADO_FUTAN_KIHON;
//	private Integer MADO_FUTAN_S_SYUBETU;
//	private String MADO_FUTAN_SYOUSAI;
//	private Integer MADO_FUTAN_T_SYUBETU;
//	private String MADO_FUTAN_TSUIKA;
//	private Integer MADO_FUTAN_D_SYUBETU;
//	private String MADO_FUTAN_DOC;
//	private Integer NENDO;

//	public Long getNAYOSE_NO() {
//		return NAYOSE_NO;
//	}
//	public void setNAYOSE_NO(Long nayose_no) {
//		NAYOSE_NO = nayose_no;
//	}
//	public Integer getBIRTHDAY() {
//		return BIRTHDAY;
//	}
//	public void setBIRTHDAY(Integer birthday) {
//		BIRTHDAY = birthday;
//	}
//	public String getEMAIL() {
//		return EMAIL;
//	}
//	public void setEMAIL(String email) {
//		EMAIL = email;
//	}
//	public String getFAX() {
//		return FAX;
//	}
//	public void setFAX(String fax) {
//		FAX = fax;
//	}
//	public String getHIHOKENJYASYO_KIGOU() {
//		return HIHOKENJYASYO_KIGOU;
//	}
//	public void setHIHOKENJYASYO_KIGOU(String hihokenjyasyo_kigou) {
//		HIHOKENJYASYO_KIGOU = hihokenjyasyo_kigou;
//	}
//	public String getHIHOKENJYASYO_NO() {
//		return HIHOKENJYASYO_NO;
//	}
//	public void setHIHOKENJYASYO_NO(String hihokenjyasyo_no) {
//		HIHOKENJYASYO_NO = hihokenjyasyo_no;
//	}
//	public String getHKNJANUM() {
//		return HKNJANUM;
//	}
//	public void setHKNJANUM(String hknjanum) {
//		HKNJANUM = hknjanum;
//	}
//	public String getHOME_ADRS() {
//		return HOME_ADRS;
//	}
//	public void setHOME_ADRS(String home_adrs) {
//		HOME_ADRS = home_adrs;
//	}
//	public String getHOME_BANTI() {
//		return HOME_BANTI;
//	}
//	public void setHOME_BANTI(String home_banti) {
//		HOME_BANTI = home_banti;
//	}
//	public String getHOME_POST() {
//		return HOME_POST;
//	}
//	public void setHOME_POST(String home_post) {
//		HOME_POST = home_post;
//	}
//	public String getHOME_TEL1() {
//		return HOME_TEL1;
//	}
//	public void setHOME_TEL1(String home_tel1) {
//		HOME_TEL1 = home_tel1;
//	}
//	public String getJYUSHIN_SEIRI_NO() {
//		return JYUSHIN_SEIRI_NO;
//	}
//	public void setJYUSHIN_SEIRI_NO(String jyushin_seiri_no) {
//		JYUSHIN_SEIRI_NO = jyushin_seiri_no;
//	}
//	public String getKANANAME() {
//		return KANANAME;
//	}
//	public void setKANANAME(String kananame) {
//		KANANAME = kananame;
//	}
//	public String getKEITAI_EMAIL() {
//		return KEITAI_EMAIL;
//	}
//	public void setKEITAI_EMAIL(String keitai_email) {
//		KEITAI_EMAIL = keitai_email;
//	}
//	public String getKEITAI_TEL() {
//		return KEITAI_TEL;
//	}
//	public void setKEITAI_TEL(String keitai_tel) {
//		KEITAI_TEL = keitai_tel;
//	}
//	public String getKEIYAKU_TORIMATOME() {
//		return KEIYAKU_TORIMATOME;
//	}
//	public void setKEIYAKU_TORIMATOME(String keiyaku_torimatome) {
//		KEIYAKU_TORIMATOME = keiyaku_torimatome;
//	}
//	public Integer getKOUHUBI() {
//		return KOUHUBI;
//	}
//	public void setKOUHUBI(Integer kouhubi) {
//		KOUHUBI = kouhubi;
//	}
//	public Integer getMADO_FUTAN_D_SYUBETU() {
//		return MADO_FUTAN_D_SYUBETU;
//	}
//	public void setMADO_FUTAN_D_SYUBETU(Integer mado_futan_d_syubetu) {
//		MADO_FUTAN_D_SYUBETU = mado_futan_d_syubetu;
//	}
//	public String getMADO_FUTAN_DOC() {
//		return MADO_FUTAN_DOC;
//	}
//	public void setMADO_FUTAN_DOC(String mado_futan_doc) {
//		MADO_FUTAN_DOC = mado_futan_doc;
//	}
//	public Integer getMADO_FUTAN_K_SYUBETU() {
//		return MADO_FUTAN_K_SYUBETU;
//	}
//	public void setMADO_FUTAN_K_SYUBETU(Integer mado_futan_k_syubetu) {
//		MADO_FUTAN_K_SYUBETU = mado_futan_k_syubetu;
//	}
//	public String getMADO_FUTAN_KIHON() {
//		return MADO_FUTAN_KIHON;
//	}
//	public void setMADO_FUTAN_KIHON(String mado_futan_kihon) {
//		MADO_FUTAN_KIHON = mado_futan_kihon;
//	}
//	public Integer getMADO_FUTAN_S_SYUBETU() {
//		return MADO_FUTAN_S_SYUBETU;
//	}
//	public void setMADO_FUTAN_S_SYUBETU(Integer mado_futan_s_syubetu) {
//		MADO_FUTAN_S_SYUBETU = mado_futan_s_syubetu;
//	}
//	public String getMADO_FUTAN_SYOUSAI() {
//		return MADO_FUTAN_SYOUSAI;
//	}
//	public void setMADO_FUTAN_SYOUSAI(String mado_futan_syousai) {
//		MADO_FUTAN_SYOUSAI = mado_futan_syousai;
//	}
//	public Integer getMADO_FUTAN_T_SYUBETU() {
//		return MADO_FUTAN_T_SYUBETU;
//	}
//	public void setMADO_FUTAN_T_SYUBETU(Integer mado_futan_t_syubetu) {
//		MADO_FUTAN_T_SYUBETU = mado_futan_t_syubetu;
//	}
//	public String getMADO_FUTAN_TSUIKA() {
//		return MADO_FUTAN_TSUIKA;
//	}
//	public void setMADO_FUTAN_TSUIKA(String mado_futan_tsuika) {
//		MADO_FUTAN_TSUIKA = mado_futan_tsuika;
//	}
//	public String getNAME() {
//		return NAME;
//	}
//	public void setNAME(String name) {
//		NAME = name;
//	}
//	public Integer getNENDO() {
//		return NENDO;
//	}
//	public void setNENDO(Integer nendo) {
//		NENDO = nendo;
//	}
//	public String getNICKNAME() {
//		return NICKNAME;
//	}
//	public void setNICKNAME(String nickname) {
//		NICKNAME = nickname;
//	}
//	public String getPTNUM() {
//		return PTNUM;
//	}
//	public void setPTNUM(String ptnum) {
//		PTNUM = ptnum;
//	}
//	public Integer getSEX() {
//		return SEX;
//	}
//	public void setSEX(Integer sex) {
//		SEX = sex;
//	}
//	public String getSIHARAI_DAIKOU_BANGO() {
//		return SIHARAI_DAIKOU_BANGO;
//	}
//	public void setSIHARAI_DAIKOU_BANGO(String siharai_daikou_bango) {
//		SIHARAI_DAIKOU_BANGO = siharai_daikou_bango;
//	}
//	public Long getUKETUKE_ID() {
//		return UKETUKE_ID;
//	}
//	public void setUKETUKE_ID(Long uketuke_id) {
//		UKETUKE_ID = uketuke_id;
//	}
//	public Integer getYUKOU_KIGEN() {
//		return YUKOU_KIGEN;
//	}
//	public void setYUKOU_KIGEN(Integer yukou_kigen) {
//		YUKOU_KIGEN = yukou_kigen;
//	}
}
