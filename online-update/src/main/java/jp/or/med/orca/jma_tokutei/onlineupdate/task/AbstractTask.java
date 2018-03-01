package jp.or.med.orca.jma_tokutei.onlineupdate.task;

/**
 *  AbstractTask
 * 
 *    抽象タスク
 */
public abstract class AbstractTask {
	private String m_strVersion = "";

	/**
	 *  実行処理
	 *  
	 *    @param  none
	 *  
	 *    @return タスク実行状態
	 */
	public boolean runTask() throws Exception {
		System.out.println("AbstractTask#runTask()");
		return true;
	}

	/**
	 *  バージョン設定
	 *  
	 *    @param  バージョン情報
	 *    
	 *    @return none
	 */
	public void setVersion(String strVersion) {
		m_strVersion = strVersion;
	}

	/**
	 * バージョン取得
	 * 
	 *   @param  none
	 * 
	 *   @return バージョン情報
	 */
	public String getVersion() {
		return m_strVersion;
	}

}
