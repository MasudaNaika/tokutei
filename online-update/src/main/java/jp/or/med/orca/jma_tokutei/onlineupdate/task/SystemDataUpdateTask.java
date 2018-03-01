package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;

/**
 *  DateUpdateTask
 * 
 *    データ更新タスク
 */
public class SystemDataUpdateTask extends AbstractTask {
	// s.inoue 20080620
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(SystemDataUpdateTask.class);
    
	private ArrayList<UpdateTask> m_aryTaskList = new ArrayList<UpdateTask>();

	private static final String DB_FILENAME_SYSTEM_FDB = "System";

	/**
	 *  タスク追加
	 *  
	 *    @param データ更新前に実行するクエリ
	 *    @param データ更新時に実行するクエリ
	 *    @param データ更新時に使用するデータURL
	 *    
	 *    @return none
	 */
	public void putTask(String strPreQuery, String strQuery, String strDataURL) {
		System.out.printf("DataUpdateTask#putTask: %s, %s, %s", strPreQuery, strQuery, strDataURL);
		System.out.println();
		
		m_aryTaskList.add(new UpdateTask(strPreQuery, strQuery, strDataURL));
	}

	/**
	 *  タスク実行
	 *    @param none
	 *    @param none
	 */
	public boolean runTask() throws Exception {
		System.out.println("DataUpdateTask#runTask()");
		
		XMLDocumentUtil doc = new XMLDocumentUtil( PropertyUtil.getProperty("property.filename") );
        String strVersion = doc.getNodeValue( "DBDataVersion", "no" );
		
        if (strVersion.compareTo(getVersion()) >= 0) {
			// 既にアップデート済み
			return false;
		}
		
		// s.inoue 20080730
		File f = new File(".");
		String getRef = f.getCanonicalPath();
		String dbPath =JApplication.systemDBPath;
		dbPath = dbPath.replace(getRef+JApplication.FILE_SEPARATOR, "");
		File directory = new File(dbPath);

		File[] dbList = directory.listFiles();

		if (dbList == null) {
			LastError.setErrorMessage("データベースファイルが見つかりませんでした");

			throw new Exception();
		}

		for (int i = 0; i < dbList.length; ++i) {
			File dbFile = dbList[i];
			String fileName = dbFile.getName();
			
			
			if (dbFile.isFile())
			{
				// system.fdbの更新処理
				if (fileName.equals(DB_FILENAME_SYSTEM_FDB + JPath.DATA_BASE_EXTENSION))
				{
					JConnection con = JConnection.ConnectSystemDatabase();
					
					try {
						
						con.Transaction();

						// 登録　実際のコピー処理 
						for (int j = 0; j < m_aryTaskList.size(); ++j) {
							UpdateTask task = (UpdateTask) m_aryTaskList.get(j);

							task.runTask(con);
						}
						
						con.Commit();
						
						} catch (Exception err) {
							
							LastError.setErrorMessage("システムデータベースのデータ更新に失敗しました");
							logger.warn("システムデータベースのデータのデータ更新に失敗しました " + JApplication.LINE_SEPARATOR + err.getMessage());
							throw err;
						} finally {
							if (con != null)
							con.Shutdown();						
						}
				}
			}
		}

		logger.info("システムデータベースのデータ更新が完了しました");
		
		return true;
	}
}
