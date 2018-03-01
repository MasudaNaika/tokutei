package jp.or.med.orca.jma_tokutei.common.printer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.TreeSet;

/**
	class::name	JTKenshinPrint
	class::expl	特定健診書類印刷クラス
*/

public class JTKenshinPrint extends JGraphicPrinter
{
	protected Hashtable<String, Object> printData = null;
	protected TreeMap<String, Object> printIraiData = null;
	// edit ver2 s.inoue 2009/09/02
	protected TreeMap<String, Object> printSeikyuNitijiData = null;
	// add s.inoue 2009/09/21
	protected TreeMap<String, Object> printSeikyuGetujiData = null;
	protected TreeMap<String, Object> printRyosyusyoData;

	// edit s.inoue 2009/11/02
	protected ArrayList<String> keyList = new ArrayList<String>();
	protected ArrayList<String> ShukeiKey = new ArrayList<String>();
	// add s.inoue 2009/10/16
	protected String kenshinDate = "";
	/*
	 *  結果設定
	 *		@param  クエリ結果
	 *		@return none
	 */
	 public void setQueryResult( Hashtable<String, Object> queryResult )
	 {
		 printData = queryResult;
	 }
	 public void setQueryIraiResult( TreeMap<String, Object> queryResult )
	 {
		 printIraiData = queryResult;
	 }

	 // edit ver2 s.inoue 2009/09/02
	 public void setQueryNitijiResult( TreeMap<String, Object> queryResult )
	 {
		 printSeikyuNitijiData = queryResult;
	 }

	 // add s.inoue 2009/09/22
	 public void setQueryGetujiResult( TreeMap<String, Object> queryResult )
	 {
		 printSeikyuGetujiData = queryResult;
	 }

	 // add s.inoue 2012/07/04
     public void setQueryRyosyusyoResult(TreeMap<String, Object> queryResult)
     {
         printRyosyusyoData = queryResult;
     }

	 // edit s.inoue 2009/11/02
	 public void setKeys(ArrayList<String> keys )
	 {
		 keyList = keys;
	 }

	 // add s.inoue 2009/10/16
	 public void setKenshinDate(String kenshinDate){
		 this.kenshinDate = kenshinDate;
	 }
	 // add s.inoue 2009/10/16
	 public String getKenshinDate(){
		 return this.kenshinDate;
	 }

	/**
	 * 集計表用キー get
	 * @return	Hashtable<String, String> KojinData
	 */
	public void setPrintShukeiKey(ArrayList<String> selectKey){
		this.ShukeiKey = selectKey;
	}
	/**
	 * 集計表用キー get
	 * @return	Hashtable<String, String> KojinData
	 */
	public ArrayList<String> getPrintShukeiKey(){
		return this.ShukeiKey;
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

