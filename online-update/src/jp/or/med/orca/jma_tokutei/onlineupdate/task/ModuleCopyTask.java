package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.File;
import java.util.*;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;

/**
 *  ModuleCopyTask
 *   
 *    モジュール更新タスク
 */
public class ModuleCopyTask extends AbstractTask {
	// s.inoue 20080620 log処理
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(ModuleCopyTask.class);
    
	private ArrayList<CopyTask> m_aryTaskList = new ArrayList<CopyTask>();
	
	/**
	 *  タスク追加
	 *    @param  取得先アドレス
	 *    @param  保存先ディレクトリ
	 *    @return none
	 */
	public void putTask(String strURL, String strCopyDir) {
		System.out.printf("ModuleCopyTask#putTask: %s, %s", strURL, strCopyDir);
		System.out.println();
		
		m_aryTaskList.add(new CopyTask(strURL, strCopyDir));
	}
	
	/**
	 *  タスク実行
	 *    @param  none
	 *    @return none
	 */
	public boolean runTask() throws Exception {
		System.out.println("ModuleCopyTask#runTask()");

		try {
			 
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			String strVersion = doc.getNodeValue("ModuleVersion", "no");
			
			if (strVersion.compareTo(getVersion()) >= 0) {
				// 既にアップデート済み
				return false;
			}
			
			// 実際のコピー処理 
			for (int i = 0; i < m_aryTaskList.size(); ++i) {
				CopyTask task = (CopyTask) m_aryTaskList.get(i);
	
				task.runTask();
			}
			
			doc.setNodeValue("ModuleVersion", "no", getVersion());
			doc.store();
			logger.info("ファイル(.xml,.jar等)更新が正常に完了しました");
			
		}catch(Exception err){
			
			LastError.setErrorMessage("ファイル(.xml,.jar等)更新に失敗しました");
			logger.error("ファイル(.xml,.jar等)更新に失敗しました" + JApplication.LINE_SEPARATOR + err.getMessage());
			throw err;
		}

		return true;
	}
}