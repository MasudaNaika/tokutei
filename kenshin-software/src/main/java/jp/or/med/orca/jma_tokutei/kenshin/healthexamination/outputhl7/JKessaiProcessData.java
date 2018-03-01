package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7;

/**
 * 決済処理のときに使用するデータ
 * これを用いて、決済処理の受信者リストを作成する。
 */
public class JKessaiProcessData {
	public String uketukeId;
	public String hiHokenjyaKigo;
	public String hiHokenjyaNumber;
	public String hokenjyaNumber;
	public String KensaDate;
	public String daikouKikanNumber;
	public String syubetuCode;
	public String seikyuKubun;
	public String jissiKubun;

	public int outputFutanSyubetsuKihon = 1;
	public int outputFutanSyubetsuSyousai = 1;
	public int outputFutanSyubetsuTsuika = 1;
	public int outputFutanSyubetsuDoc = 1;
	public String kanaName;
	public String sex;
	public String birthday;
}
