package jp.or.med.orca.jma_tokutei.common.hl7.common;

public interface JBaseXmlWriter {
	/**
	 * Xmlファイルを作成する
	 * @param FileName 作成するファイル名
	 * @throws Exception XML生成時の例外
	 */
	public void createXml(String FileName) throws Exception;
	
	/**
	 * 必要項目のチェックを行う
	 * XMLファイルを生成する前に実行してください
	 * @return 必要項目が埋まっていれば「true」が返ってくる
	 */
	public boolean check();
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

