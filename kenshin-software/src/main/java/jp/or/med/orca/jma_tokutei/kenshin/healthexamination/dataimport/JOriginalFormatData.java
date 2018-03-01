package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.util.Vector;

/**
 * 独自フォーマットデータの各受信者ごとのヘッダ部。
 */
public class JOriginalFormatData {
	/**
	 * 患者氏名
	 */
	public String Name;
	/**
	 * 患者誕生日
	 */
	public String BirthDay;
	
	/**
	 * ボディ部に関する情報
	 */
	public Vector<JOriginalFormatDataBody> Body = new Vector<JOriginalFormatDataBody>();
}
