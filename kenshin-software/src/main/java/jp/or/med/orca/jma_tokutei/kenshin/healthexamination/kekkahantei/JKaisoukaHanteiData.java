package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei;

public class JKaisoukaHanteiData {
	private String Gender = null;
	private double Age = -1;
	private double Shintyou = -1;
	private double Taijyu = -1;
	private double NaizouShibou = -1;
	private double Hukui_Jissoku = -1;
	private double Hukui_JikoSokutei = -1;
	private double Hukui_JikoShinkoku = -1;
	private double BMI = -1;
	private double KuhukujiKetto = -1;
	private double HbA1c = -1;
	private double KettoHukuyaku = -1;
	private double ChuseiShibou = -1;
	private double HDLCholesterol = -1;
	private double ShishitsuHukuyaku = -1;
	private double ShushukuKetsuatsuNo1 = -1;
	private double ShushukuKetsuatsuNo2 = -1;
	private double ShushukuKetsuatsuOther = -1;
	private double KakutyoKetsuatsuNo1 = -1;
	private double KakutyoKetsuatsuNo2 = -1;
	private double KakutyoKetsuatsuOther = -1;
	private double KetsuatsuHukuyaku = -1;
	private double Kitsuen = -1;
	// add s.inoue 2013/01/29
	private String KensinjisiDate = "";

	// add ver2 s.inoue 2009/08/19
	private boolean ageTarget = false;

	/**
	 * @param KensinjisiDate
	 */
	public void setKensinJisiDate(String KensinjisiDate) {
		if( !KensinjisiDate.isEmpty() )
		{
			// add s.inoue 2013/03/06
			// this.が無かったので、エラー
			this.KensinjisiDate = KensinjisiDate;
		}
	}
	public String getKensinJisiDate() {
		return KensinjisiDate;
	}


	/**
	 * @param gender 性別
	 */
	public void setGender(String gender) {
		if( !gender.isEmpty() )
		{
			Gender = gender;
		}
	}
	public String getGender() {
		return Gender;
	}

	/**
	 * @param age 年齢
	 */
	public void setAge(double age){
		Age = age;
	}

	public double getAge() {
		return Age;
	}

	// add ver2 s.inoue 2009/08/19
	/**
	 * @param 保発11180001 75歳の誕生日までの制限
	 */
	public void setTargetAge(boolean age75){
		this.ageTarget = age75;
	}

	public boolean getTargetAge() {
		return ageTarget;
	}

	/**
	 * @param shintyou 身長
	 */
	public void setShintyou(double shintyou) {
		Shintyou = shintyou;
	}
	public double getShintyou() {
		return Shintyou;
	}

	/**
	 * @param taijyu 体重
	 */
	public void setTaijyu(double taijyu) {
		Taijyu = taijyu;
	}
	public double getTaijyu() {
		return Taijyu;
	}

	/**
	 * @param naizouShibou 内臓脂肪面積
	 */
	public void setNaizouShibou(double naizouShibou) {
		NaizouShibou = naizouShibou;
	}
	public double getNaizouShibou() {
		return NaizouShibou;
	}

	/**
	 * @param hukui_Jissoku 腹囲実測
	 */
	public void setHukui_Jissoku(double hukui_Jissoku) {
		Hukui_Jissoku = hukui_Jissoku;
	}
	/**
	 * @param hukui_JikoSokutei 腹囲自己測定
	 */
	public void setHukui_JikoSokutei(double hukui_JikoSokutei) {
		Hukui_JikoSokutei = hukui_JikoSokutei;
	}
	/**
	 * @param hukui_JikoShinkoku 腹囲自己申告
	 */
	public void setHukui_JikoShinkoku(double hukui_JikoShinkoku) {
		Hukui_JikoShinkoku = hukui_JikoShinkoku;
	}
	public double getHukui() {
		// 腹囲実測、腹囲自己測定、腹囲自己申告の順で使用する
		if(Hukui_Jissoku != -1)
		{
			return Hukui_Jissoku;
		}else{
			if(Hukui_JikoSokutei != -1)
			{
				return Hukui_JikoSokutei;
			}else{
				return Hukui_JikoShinkoku;
			}
		}
	}

	/**
	 * @param bmi BMI
	 */
	public void setBMI(double bmi) {
		BMI = bmi;
	}
	public double getBMI() {
		return BMI;
	}

	/**
	 * @param kuhukujiKetto 空腹時血糖
	 */
	public void setKuhukujiKetto(double kuhukujiKetto) {
		KuhukujiKetto = kuhukujiKetto;
	}
	public double getKuhukujiKetto() {
		return KuhukujiKetto;
	}

	/**
	 * @param hbA1c HbA1c検査
	 */
	public void setHbA1c(double hbA1c) {
		HbA1c = hbA1c;
	}
	public double getHbA1c() {
		return HbA1c;
	}

	/**
	 * @param kettoHukuyaku 血糖に関しての服薬
	 */
	public void setKettoHukuyaku(double kettoHukuyaku) {
		KettoHukuyaku = kettoHukuyaku;
	}
	public double getKettoHukuyaku() {
		return KettoHukuyaku;
	}

	/**
	 * @param chuseiShibou 中性脂肪
	 */
	public void setChuseiShibou(double chuseiShibou) {
		ChuseiShibou = chuseiShibou;
	}
	public double getChuseiShibou() {
		return ChuseiShibou;
	}

	/**
	 * @param cholesterol HDLコレステロール
	 */
	public void setHDLCholesterol(double cholesterol) {
		HDLCholesterol = cholesterol;
	}
	public double getHDLCholesterol() {
		return HDLCholesterol;
	}

	/**
	 * @param shishitsuHukuyaku 脂質に関しての服薬
	 */
	public void setShishitsuHukuyaku(double shishitsuHukuyaku) {
		ShishitsuHukuyaku = shishitsuHukuyaku;
	}
	public double getShishitsuHukuyaku() {
		return ShishitsuHukuyaku;
	}

	/**
	 * 収縮時血圧
	 */
	public double getShushukuKetsuatsu() {
		// 収縮時血圧、拡張血圧をペアとして取り扱う。
		// eidt s.inoue 2012/09/25
		// ペアにすると判定に影響が出る
		// if(ShushukuKetsuatsuOther != -1 && KakutyoKetsuatsuOther != -1)
		if(ShushukuKetsuatsuOther != -1)
		{
			return ShushukuKetsuatsuOther;
		}else if(ShushukuKetsuatsuNo2 != -1)
		// }else if(ShushukuKetsuatsuNo2 != -1 && KakutyoKetsuatsuNo2 != -1)
		{
			return ShushukuKetsuatsuNo2;
		}else if(ShushukuKetsuatsuNo1 != -1)
		// }else if(ShushukuKetsuatsuNo1 != -1 && KakutyoKetsuatsuNo1 != -1)
		{
			return ShushukuKetsuatsuNo1;
		}

		return -1;
	}

	/**
	 * @param shushukuKetsuatsuNo1 収縮時血圧（1回目）
	 */
	public void setShushukuKetsuatsuNo1(double shushukuKetsuatsuNo1) {
		ShushukuKetsuatsuNo1 = shushukuKetsuatsuNo1;
	}

	/**
	 * @param shushukuKetsuatsuNo2 収縮時血圧（2回目）
	 */
	public void setShushukuKetsuatsuNo2(double shushukuKetsuatsuNo2) {
		ShushukuKetsuatsuNo2 = shushukuKetsuatsuNo2;
	}

	/**
	 * @param shushukuKetsuatsuOther 収縮時血圧（その他）
	 */
	public void setShushukuKetsuatsuOther(double shushukuKetsuatsuOther) {
		ShushukuKetsuatsuOther = shushukuKetsuatsuOther;
	}

	/**
	 * 拡張血圧
	 */
	public double getKakutyoKetsuatsu() {
		// 収縮時血圧、拡張血圧をペアとして取り扱う。
		// eidt s.inoue 2012/09/25
		// ペアにすると判定に影響が出る
		if(KakutyoKetsuatsuOther != -1)
		// if(KakutyoKetsuatsuOther != -1 && ShushukuKetsuatsuOther != -1)
		{
			return KakutyoKetsuatsuOther;
		}else if(KakutyoKetsuatsuNo2 != -1)
		// }else if(KakutyoKetsuatsuNo2 != -1 && ShushukuKetsuatsuNo2 != -1)
		{
			return KakutyoKetsuatsuNo2;
		}else if(KakutyoKetsuatsuNo1 != -1)
		// }else if(KakutyoKetsuatsuNo1 != -1 && ShushukuKetsuatsuNo1 != -1)
		{
			return KakutyoKetsuatsuNo1;
		}

		return -1;
	}

	/**
	 * @param kakutyoKetsuatsuNo1 拡張血圧（1回目）
	 */
	public void setKakutyoKetsuatsuNo1(double kakutyoKetsuatsuNo1) {
		KakutyoKetsuatsuNo1 = kakutyoKetsuatsuNo1;
	}

	/**
	 * @param kakutyoKetsuatsuNo2 拡張血圧（2回目）
	 */
	public void setKakutyoKetsuatsuNo2(double kakutyoKetsuatsuNo2) {
		KakutyoKetsuatsuNo2 = kakutyoKetsuatsuNo2;
	}

	/**
	 * @param kakutyoKetsuatsuOther 拡張血圧（その他）
	 */
	public void setKakutyoKetsuatsuOther(double kakutyoKetsuatsuOther) {
		KakutyoKetsuatsuOther = kakutyoKetsuatsuOther;
	}
	/**
	 * @param ketsuatsuHukuyaku 血圧に関しての服薬
	 */
	public void setKetsuatsuHukuyaku(double ketsuatsuHukuyaku) {
		KetsuatsuHukuyaku = ketsuatsuHukuyaku;
	}
	public double getKetsuatsuHukuyaku() {
		return KetsuatsuHukuyaku;
	}

	/**
	 * @param kitsuen 喫煙歴
	 */
	public void setKitsuen(double kitsuen) {
		Kitsuen = kitsuen;
	}
	public double getKitsuen() {
		return Kitsuen;
	}
}
