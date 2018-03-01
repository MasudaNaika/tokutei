package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select;

import java.util.Hashtable;

public class JSelectShiharaiFrameData {

	public String shiharai_daiko_no;
	public String shiharai_daiko_name;
	public String shiharai_daiko_zipcd;
	public String shiharai_daiko_adr;
	public String shiharai_daiko_tel;

	/**
	 * データベース上のデータを保持。最終的に選択された受診者を判定する際に使用。
	 */
	public Hashtable<String, String> DatabaseItem;
}
