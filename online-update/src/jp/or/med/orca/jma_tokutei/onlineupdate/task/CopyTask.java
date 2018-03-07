package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;

/**
 *  CopyTask
 * 
 *    モジュールコピータスク
 */
class CopyTask
{
	private String m_strModuleURL     = "";
	private String m_strCopyDirectory = "";

	/**
	 *  コンストラクタ
	 *  
	 *    @param  モジュールコピー元URL
	 *    @param  モジュールコピー先ディレクトリ
	 *    
	 *    @return none
	 */
	public CopyTask( String strURL, String strDirectory )
	{
		m_strModuleURL = strURL;
		m_strCopyDirectory = strDirectory;
	}
	
	/**
	 *  タスク実行
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void runTask() throws Exception
	{
		HttpConnecter.getFile( m_strModuleURL, m_strCopyDirectory );
	}
}