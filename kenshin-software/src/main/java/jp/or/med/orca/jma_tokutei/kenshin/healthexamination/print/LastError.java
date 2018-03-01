package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

public class LastError
{
	static private String m_strErrorMessage = null;
	
	/**
	 *  エラーメッセージ設定
	 *  
	 *    @param  エラーメッセージ
	 *    
	 *    @return none
	 */
	static public void setErrorMessage( String strError )
	{
		m_strErrorMessage = strError;
	}
	
	/**
	 *  エラーメッセージ取得
	 * 
	 *    @param  none
	 *    
	 *    @return エラーメッセージ
	 */
	static public String getErrorMessage()
	{
		return m_strErrorMessage;
	}
}
