package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

public class JInputKessaiDataFrameData {
	private String Jyusinken_ID = "";
	private String Name = "";  //  @jve:decl-index=0:
	private String KensaDate = "";
	private String Birthday = "";
	private String ToHL7Date = "";
	private String HinketsuCode = "";
	private String SindenzuCode = "";
	private String GanteiCode = "";
	private String KihonTanka = "";
	private String HinketuTanka = "";
	private String SindenzuTanka = "";
	private String GanteiTanka = "";
	private String DocTanka = "";

	private String MadoguchiKihonKin = "";
	private String MadoguchiShousaiKin = "";
	private String MadoguchiTuikaKin = "";
	private String MadoguchiDocKin = "";

	private String AllTanka = "";
	private String AllMadoguchi = "";
	private String TotalFee = "";
	private String Sex = "";
	private String ItakuTankaKubun = "";

	private String MadoguchiKihonSyubetu = "";
	private String MadoguchiSyousaiSyubetu = "";
	private String MadoguchiTsuikaSyubetu = "";
	private String MadoguchiDocSyubetu = "";

	private String Hihokenjyasyo_Kigou = "";
	private String Hihokenjyasyo_No = "";
	private String HokenjyaNum = "";

	// add s.inoue 20081008
	private String seikyuKubun = "";
	private String uketuke_id = "";

	private String madoguchiSonota = "";

	private String hokenjyaJougenKihon = "";
	private String hokenjyaJougenSyousai = "";
	private String hokenjyaJougenTsuika = "";
	private String hokenjyaJougenDoc = "";

	private boolean isValidateAsDataSet = false;

	/**
	 * @return the jyusinken_ID
	 */
	public String getJyusinken_ID() {
		return Jyusinken_ID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @return the kensaDate
	 */
	public String getKensaDate() {
		return KensaDate;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return Birthday;
	}
	/**
	 * @return the toHL7Date
	 */
	public String getToHL7Date() {
		return ToHL7Date;
	}
	/**
	 * @return the hinketsuCode
	 */
	public String getHinketsuCode() {
		return HinketsuCode;
	}
	/**
	 * @return the sindenzuCode
	 */
	public String getSindenzuCode() {
		return SindenzuCode;
	}
	/**
	 * @return the ganteiCode
	 */
	public String getGanteiCode() {
		return GanteiCode;
	}
	/**
	 * @return the kihonTanka
	 */
	public String getKihonTanka() {
		return KihonTanka;
	}
	/**
	 * @return the hinketuTanka
	 */
	public String getHinketuTanka() {
		return HinketuTanka;
	}
	/**
	 * @return the sindenzuTanka
	 */
	public String getSindenzuTanka() {
		return SindenzuTanka;
	}
	/**
	 * @return the ganteiTanka
	 */
	public String getGanteiTanka() {
		return GanteiTanka;
	}
	/**
	 * @return the ganteiTanka
	 */
	public String getNingenDocTanka() {
		return DocTanka;
	}

	/**
	 * @return the madoguchiKihonKin
	 */
	public String getMadoguchiKihonKin() {
		return MadoguchiKihonKin;
	}
	/**
	 * @return the madoguchiShousaiKin
	 */
	public String getMadoguchiSyousaiKin() {
		return MadoguchiShousaiKin;
	}
	/**
	 * @return the madoguchiTuikaKin
	 */
	public String getMadoguchiTuikaKin() {
		return MadoguchiTuikaKin;
	}

	/**
	 * @return the MadoguchiDockKin
	 */
	public String getMadoguchiDocKin() {
		return MadoguchiDocKin;
	}

	/**
	 * @return the allTanka
	 */
	public String getAllTanka() {
		return AllTanka;
	}
	/**
	 * @return the allMadoguchi
	 */
	public String getAllMadoguchi() {
		return AllMadoguchi;
	}
	/**
	 * @return the totalFee
	 */
	public String getTotalFee() {
		return TotalFee;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return Sex;
	}
	/**
	 * @return the itakuTankaKubun
	 */
	public String getItakuTankaKubun() {
		return ItakuTankaKubun;
	}
	/**
	 * @return the madoguchiKihonSyubetu
	 */
	public String getMadoguchiKihonSyubetu() {
		return MadoguchiKihonSyubetu;
	}
	/**
	 * @return the madoguchiSyousaiSyubetu
	 */
	public String getMadoguchiSyousaiSyubetu() {
		return MadoguchiSyousaiSyubetu;
	}
	/**
	 * @return the madoguchiTsuikaSyubetu
	 */
	public String getMadoguchiTsuikaSyubetu() {
		return MadoguchiTsuikaSyubetu;
	}

	/**
	 * @return the MadoguchiDockSyubetu
	 */
	public String getMadoguchiDocSyubetu() {
		return MadoguchiDocSyubetu;
	}

	/**
	 * @return the isValidateAsDataSet
	 */
	public boolean isValidateAsDataSet() {
		return isValidateAsDataSet;
	}

	/**
	 * @param jyusinken_ID the jyusinken_ID to set
	 */
	public boolean setJyusinken_ID(String jyusinken_ID) {
		this.isValidateAsDataSet = false;
		this.Jyusinken_ID = JValidate.validateJyushinkenID(jyusinken_ID);

		if( this.Jyusinken_ID == null ) {
			JErrorMessage.show("M3410", null);
			return false;
		}

		return true;
	}

	/**
	 * @param name the name to set
	 */
	public boolean setName(String name) {
		this.isValidateAsDataSet = false;
		this.Name = JValidate.validateNameKana(name);

		if( this.Name == null ) {
			JErrorMessage.show("M3411", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kensaDate the kensaDate to set
	 */
	public boolean setKensaDate(String kensaDate) {
		this.isValidateAsDataSet = false;
		this.KensaDate = JValidate.validateDate(kensaDate,false,true);

		if( this.KensaDate == null ) {
			JErrorMessage.show("M3412", null);
			return false;
		}

		return true;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public boolean setBirthday(String birthday) {
		this.isValidateAsDataSet = false;
		this.Birthday = JValidate.validateDate(birthday,false,true);

		if( this.Birthday == null ) {
			JErrorMessage.show("M3413", null);
			return false;
		}

		return true;
	}

	/**
	 * @param toHL7Date the toHL7Date to set
	 */
	public boolean setToHL7Date(String toHL7Date) {
		this.isValidateAsDataSet = false;
		this.ToHL7Date = JValidate.validateDate(toHL7Date,false,true);

		if( this.ToHL7Date == null ) {
			JErrorMessage.show("M3414", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hinketsuCode the hinketsuCode to set
	 */
	public boolean setHinketsuCode(String hinketsuCode) {
		this.isValidateAsDataSet = false;
		this.HinketsuCode = JValidate.validateKensaCode(hinketsuCode);

		if( this.HinketsuCode == null ) {
			JErrorMessage.show("M3415", null);
			return false;
		}

		return true;
	}

	/**
	 * @param sindenzuCode the sindenzuCode to set
	 */
	public boolean setSindenzuCode(String sindenzuCode) {
		this.isValidateAsDataSet = false;
		this.SindenzuCode = JValidate.validateKensaCode(sindenzuCode);

		if( this.SindenzuCode == null ) {
			JErrorMessage.show("M3416", null);
			return false;
		}

		return true;
	}

	/**
	 * @param ganteiCode the ganteiCode to set
	 */
	public boolean setGanteiCode(String ganteiCode) {
		this.isValidateAsDataSet = false;
		this.GanteiCode = JValidate.validateKensaCode(ganteiCode);

		if( this.GanteiCode == null ) {
			JErrorMessage.show("M3417", null);
			return false;
		}

		return true;
	}

	/**
	 * @param kihonTanka the kihonTanka to set
	 */
	public boolean setKihonTanka(String kihonTanka) {
		this.isValidateAsDataSet = false;
		this.KihonTanka = JValidate.validateKensaTanka(kihonTanka);

		if( this.KihonTanka == null ) {
			JErrorMessage.show("M3418", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hinketuTanka the hinketuTanka to set
	 */
	public boolean setHinketuTanka(String hinketuTanka) {
		this.isValidateAsDataSet = false;
		this.HinketuTanka = JValidate.validateKensaTanka(hinketuTanka);

		if( this.HinketuTanka == null ) {
			JErrorMessage.show("M3419", null);
			return false;
		}

		return true;
	}

	/**
	 * @param sindenzuTanka the sindenzuTanka to set
	 */
	public boolean setSindenzuTanka(String sindenzuTanka) {
		this.isValidateAsDataSet = false;
		this.SindenzuTanka = JValidate.validateKensaTanka(sindenzuTanka);

		if( this.SindenzuTanka == null ) {
			JErrorMessage.show("M3420", null);
			return false;
		}

		return true;
	}

	/**
	 * @param ganteiTanka the ganteiTanka to set
	 */
	public boolean setGanteiTanka(String ganteiTanka) {
		this.isValidateAsDataSet = false;
		this.GanteiTanka = JValidate.validateKensaTanka(ganteiTanka);

		if( this.GanteiTanka == null ) {
			JErrorMessage.show("M3421", null);
			return false;
		}

		return true;
	}

	/**
	 * @param ganteiTanka the ningenDocTanka to set
	 */
	public boolean setDocTanka(String ningenDocTanka) {
		this.isValidateAsDataSet = false;
		this.DocTanka = JValidate.validateKensaTanka(ningenDocTanka);

		if( this.DocTanka  == null ) {
			JErrorMessage.show("M3422", null);
			return false;
		}

		return true;
	}

	/**
	 * @param madoguchiKihonKin the madoguchiKihonKin to set
	 */
	public boolean setMadoguchiKihonKin(String madoguchiKihonKin) {
		this.isValidateAsDataSet = false;

		switch(Integer.valueOf(this.MadoguchiKihonSyubetu))
		{
		//受診者の負担無しの場合
		case 0:
		case 1:
			//０を格納する
			this.MadoguchiKihonKin = "000000";
			return true;

		//受診者は定額負担の場合
		case 2:
			this.MadoguchiKihonKin =  JValidate.validateMadoguchiAmount(madoguchiKihonKin);
			if( this.MadoguchiKihonKin == null ) {
				JErrorMessage.show("M3430", null);
				return false;
			}

			return true;

		//受診者は定率負担
		case 3:
			this.MadoguchiKihonKin = JValidate.validateMadoguchiPercent(madoguchiKihonKin);
			if( this.MadoguchiKihonKin == null ) {
				JErrorMessage.show("M3431", null);
				return false;
			}

			return true;

		//保険者の上限負担額
		case 4:
			this.MadoguchiKihonKin = JValidate.validateMadoguchiAmount(madoguchiKihonKin);
			if( this.MadoguchiKihonKin == null ) {
				JErrorMessage.show("M3432", null);
				return false;
			}

			return true;
		}

		return false;
	}

	// add st 20081008 s.inoue
	/**
	 * @return the seikyuKubun
	 */
	public String getSeikyuKubun() {
		return seikyuKubun;
	}

	/**
	 * @param seikyuKubun the seikyuKubun to set
	 */
	public boolean setSeikyuKubun(String seikyuKubun) {
		this.isValidateAsDataSet = false;
		this.seikyuKubun = JValidate.validateSeikyuKubun(seikyuKubun);

		if( this.seikyuKubun == null )
			return false;

		return true;
	}
	// add ed 20081008 s.inoue

	/**
	 * @param madoguchiShousaiKin the madoguchiShousaiKin to set
	 */
	public boolean setMadoguchiShousaiKin(String madoguchiShousaiKin) {
		this.isValidateAsDataSet = false;

		switch(Integer.valueOf(this.MadoguchiSyousaiSyubetu))
		{
		//受診者の負担無しの場合
		case 0:
		case 1:
			//０を格納する
			this.MadoguchiShousaiKin = "000000";
			return true;

		//受診者は定額負担の場合
		case 2:
			this.MadoguchiShousaiKin =  JValidate.validateMadoguchiAmount(madoguchiShousaiKin);
			if( this.MadoguchiShousaiKin == null ) {
				JErrorMessage.show("M3433", null);
				return false;
			}

			return true;

		//受診者は定率負担
		case 3:
			this.MadoguchiShousaiKin = JValidate.validateMadoguchiPercent(madoguchiShousaiKin);
			if( this.MadoguchiShousaiKin == null ) {
				JErrorMessage.show("M3434", null);
				return false;
			}

			return true;

		//保険者の上限負担額
		case 4:
			this.MadoguchiShousaiKin = JValidate.validateMadoguchiAmount(madoguchiShousaiKin);
			if( this.MadoguchiShousaiKin == null ) {
				JErrorMessage.show("M3435", null);
				return false;
			}

			return true;
		}

		return false;
	}

	/**
	 * @param madoguchiTuikaKin the madoguchiTuikaKin to set
	 */
	public boolean setMadoguchiTuikaKin(String madoguchiTuikaKin) {
		this.isValidateAsDataSet = false;

		switch(Integer.valueOf(this.MadoguchiTsuikaSyubetu))
		{
		//受診者の負担無しの場合
		case 0:
		case 1:
			//０を格納する
			this.MadoguchiTuikaKin = "000000";
			return true;

		//受診者は定額負担の場合
		case 2:
			this.MadoguchiTuikaKin =  JValidate.validateMadoguchiAmount(madoguchiTuikaKin);
			if( this.MadoguchiTuikaKin == null ) {
				JErrorMessage.show("M3436", null);
				return false;
			}

			return true;

		//受診者は定率負担
		case 3:
			this.MadoguchiTuikaKin = JValidate.validateMadoguchiPercent(madoguchiTuikaKin);
			if( this.MadoguchiTuikaKin == null ) {
				JErrorMessage.show("M3437", null);
				return false;
			}

			return true;

		//保険者の上限負担額
		case 4:
			this.MadoguchiTuikaKin = JValidate.validateMadoguchiAmount(madoguchiTuikaKin);
			if( this.MadoguchiTuikaKin == null ) {
				JErrorMessage.show("M3438", null);
				return false;
			}

			return true;
		}

		return false;
	}


	/**
	 * @param madoguchiDocKin to set
	 */
	public boolean setMadoguchiDocKin(String madoguchiDocKin) {
		this.isValidateAsDataSet = false;

		switch(Integer.valueOf(this.MadoguchiDocSyubetu))
		{
		//受診者の負担無しの場合
		case 0:
		case 1:
			//０を格納する
			this.MadoguchiDocKin = "000000";
			return true;

		//受診者は定額負担の場合
		case 2:
			this.MadoguchiDocKin =  JValidate.validateMadoguchiAmount(madoguchiDocKin);
			if( this.MadoguchiDocKin == null ) {
				JErrorMessage.show("M3467", null);
				return false;
			}
			return true;

		//受診者は定率負担
		case 3:
			this.MadoguchiDocKin = JValidate.validateMadoguchiPercent(madoguchiDocKin);
			if( this.MadoguchiDocKin == null ) {
				JErrorMessage.show("M3468", null);
				return false;
			}
			return true;

		//保険者の上限負担額
		case 4:
			this.MadoguchiDocKin = JValidate.validateMadoguchiAmount(madoguchiDocKin);
			if( this.MadoguchiDocKin == null ) {
				JErrorMessage.show("M3469", null);
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * @param allTanka the allTanka to set
	 */
	public boolean setAllTanka(String allTanka) {
		this.isValidateAsDataSet = false;
		this.AllTanka = JValidate.validateKensaTankaTotal(allTanka);

		if( this.AllTanka == null ) {
			JErrorMessage.show("M3439", null);
			return false;
		}
		return true;
	}

	/**
	 * @param allMadoguchi the allMadoguchi to set
	 */
	public boolean setAllMadoguchi(String allMadoguchi) {
		this.isValidateAsDataSet = false;
		this.AllMadoguchi =  JValidate.validateMadoguchiAmountTotal(allMadoguchi);

		if( this.AllMadoguchi == null ) {
			JErrorMessage.show("M3440", null);
			return false;
		}

		return true;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public boolean setTotalFee(String totalFee) {
		this.isValidateAsDataSet = false;
		this.TotalFee = JValidate.validateSeikyuKingaku(totalFee);

		if( this.TotalFee == null ) {
			JErrorMessage.show("M3441", null);
			return false;
		}

		return true;
	}

	/**
	 * @param sex the sex to set
	 */
	public boolean setSex(String sex) {
		this.isValidateAsDataSet = false;
		this.Sex = JValidate.validateSex(sex);

		if( this.Sex == null ) {
			JErrorMessage.show("M3450", null);
			return false;
		}

		return true;
	}

	/**
	 * @param itakuTankaKubun the itakuTankaKubun to set
	 */
	public boolean setItakuTankaKubun(String itakuTankaKubun) {
		this.isValidateAsDataSet = false;
		this.ItakuTankaKubun = JValidate.validateItakuKubun(itakuTankaKubun);

		if( this.ItakuTankaKubun == null ) {
			JErrorMessage.show("M3451", null);
			return false;
		}

		return true;
	}

	/**
	 * @param madoguchiKihonSyubetu the madoguchiKihonSyubetu to set
	 */
	public boolean setMadoguchiKihonSyubetu(int madoguchiKihonSyubetu) {
		this.isValidateAsDataSet = false;
		this.MadoguchiKihonSyubetu = String.valueOf(madoguchiKihonSyubetu);

		return true;
	}

	/**
	 * @param madoguchiSyousaiSyubetu the madoguchiSyousaiSyubetu to set
	 */
	public boolean setMadoguchiSyousaiSyubetu(int madoguchiSyousaiSyubetu) {
		this.isValidateAsDataSet = false;
		this.MadoguchiSyousaiSyubetu = String.valueOf(madoguchiSyousaiSyubetu);

		return true;
	}

	/**
	 * @param madoguchiTsuikaSyubetu the madoguchiTsuikaSyubetu to set
	 */
	public boolean setMadoguchiTsuikaSyubetu(int madoguchiTsuikaSyubetu) {
		this.isValidateAsDataSet = false;
		this.MadoguchiTsuikaSyubetu = String.valueOf(madoguchiTsuikaSyubetu);

		return true;
	}

	/**
	 * @param madoguchiDocSyubetu the madoguchiDocSyubetu to set
	 */
	public boolean setMadoguchiDocSyubetu(int madoguchiDocSyubetu) {
		this.isValidateAsDataSet = false;
		this.MadoguchiDocSyubetu = String.valueOf(madoguchiDocSyubetu);

		return true;
	}

	/**
	 * @return the hihokenjyasyo_Kigou
	 */
	public String getHihokenjyasyo_Kigou() {
		return Hihokenjyasyo_Kigou;
	}

	/**
	 * @return the hihokenjyasyo_No
	 */
	public String getHihokenjyasyo_No() {
		return Hihokenjyasyo_No;
	}

	/**
	 * @return the hokenjyaNum
	 */
	public String getHokenjyaNum() {
		return HokenjyaNum;
	}

	/**
	 * @param hihokenjyasyo_Kigou the hihokenjyasyo_Kigou to set
	 */
	public boolean setHihokenjyasyo_Kigou(String hihokenjyasyo_Kigou) {
		this.isValidateAsDataSet = false;
		this.Hihokenjyasyo_Kigou = JValidate.validateHihokenjyaKigou(hihokenjyasyo_Kigou);

		if( this.Hihokenjyasyo_Kigou == null ) {
			JErrorMessage.show("M3460", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hihokenjyasyo_No the hihokenjyasyo_No to set
	 */
	public boolean setHihokenjyasyo_No(String hihokenjyasyo_No) {
		this.isValidateAsDataSet = false;
		this.Hihokenjyasyo_No = JValidate.validateHihokenjyaNumber(hihokenjyasyo_No);

		if( this.Hihokenjyasyo_No == null ) {
			JErrorMessage.show("M3461", null);
			return false;
		}

		return true;
	}

	/**
	 * @param hokenjyaNum the hokenjyaNum to set
	 */
	public boolean setHokenjyaNum(String hokenjyaNum) {
		this.isValidateAsDataSet = false;
		this.HokenjyaNum = JValidate.validateHokenjyaNumber(hokenjyaNum);

		if( this.HokenjyaNum == null ) {
			JErrorMessage.show("M3462", null);
			return false;
		}

		return true;
	}

	public String getUketuke_id() {
		return uketuke_id;
	}
	public void setUketuke_id(String uketuke_id) {
		this.uketuke_id = uketuke_id;
	}

	/**
	 * @param isValidateAsDataSet the isValidateAsDataSet to set
	 */
	public void setValidateAsDataSet() {
		this.isValidateAsDataSet = true;
	}

	public String getMadoguchiSonota() {
		return madoguchiSonota;
	}

	public boolean setMadoguchiSonota(String madoguchiSonota) {
		this.isValidateAsDataSet = false;

		this.madoguchiSonota = JValidate.validateMadoguchiSonota(
				madoguchiSonota);
		if( this.madoguchiSonota == null ) {
			JErrorMessage.show("M4343", null);
			return false;
		}

		return true;
	}

	public String getHokenjyaJougenDoc() {
		return hokenjyaJougenDoc;
	}

	public boolean setHokenjyaJougenDoc(String hokenjyaJougenDoc) {
		this.isValidateAsDataSet = false;

		this.hokenjyaJougenDoc = JValidate.validateMadoguchiAmount(hokenjyaJougenDoc);
		if( this.hokenjyaJougenDoc == null ) {
			JErrorMessage.show("M3463", null);
			return false;
		}

		return true;
	}

	public String getHokenjyaJougenKihon() {
		return hokenjyaJougenKihon;
	}

	public boolean setHokenjyaJougenKihon(String hokenjyaJougenKihon) {
		this.isValidateAsDataSet = false;

		this.hokenjyaJougenKihon = JValidate.validateMadoguchiAmount(
				hokenjyaJougenKihon);
		if( this.hokenjyaJougenKihon == null ) {
			JErrorMessage.show("M3464", null);
			return false;
		}

		return true;
	}

	public String getHokenjyaJougenSyousai() {
		return hokenjyaJougenSyousai;
	}

	public boolean setHokenjyaJougenSyousai(String hokenjyaJougenSyousai) {
		this.isValidateAsDataSet = false;

		this.hokenjyaJougenSyousai = JValidate.validateMadoguchiAmount(
				hokenjyaJougenSyousai);
		if( this.hokenjyaJougenSyousai == null ) {
			JErrorMessage.show("M3465", null);
			return false;
		}

		return true;
	}

	public String getHokenjyaJougenTsuika() {
		return hokenjyaJougenTsuika;
	}

	public boolean setHokenjyaJougenTsuika(String hokenjyaJougenTsuika) {
		this.isValidateAsDataSet = false;

		this.hokenjyaJougenTsuika = JValidate.validateMadoguchiAmount(
				hokenjyaJougenTsuika);
		if( this.hokenjyaJougenTsuika == null ) {
			JErrorMessage.show("M3466", null);
			return false;
		}

		return true;
	}
}
