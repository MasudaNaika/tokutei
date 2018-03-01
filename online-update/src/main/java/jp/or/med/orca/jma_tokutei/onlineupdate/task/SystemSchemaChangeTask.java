package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.File;
import java.util.Vector;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.DBConnect;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.LastError;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.Util;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.XMLDocumentUtil;

/**
 *  SchemaChangeTask
 * 
 *    スキーマ変更タスク
 */
public class SystemSchemaChangeTask extends AbstractTask {
	// s.inoue 20080620
    private static org.apache.log4j.Logger logger =
    	Logger.getLogger(SystemSchemaChangeTask.class);

	private Vector<String> m_vectTaskList = new Vector<String>();
	private static final String DB_FILENAME_SYSTEM_FDB = "System";
	
	/**
	 *  タスク追加
	 *    @param 実行クエリ
	 *    @return none
	 */
	public void putTask(String strURL) {
		System.out.printf("Schema	ChangeTask#putTask: %s", strURL);
		System.out.println();

		m_vectTaskList.add(strURL);
	}

	/**
	 *  タスク実行
	 *    @param none
	 *    @param none
	 */
	public boolean runTask() throws Exception {
		System.out.println("SchemaChangeTask#runTask()");
        
		XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
        String strVersion = doc.getNodeValue("SchemaVersion", "no");
        
        if( strVersion.compareTo(getVersion()) >= 0 )
        {
        	// 既にアップデート済み
        	return false;
        }
        
		// s.inoue 20080730
		File f = new File(".");
		String getRef = f.getCanonicalPath();
		String dbPath =JApplication.systemDBPath;
		dbPath = dbPath.replace(getRef+JApplication.FILE_SEPARATOR, "");
		
		File directory = new File(dbPath);
		File[] DBlist = directory.listFiles();

		if (DBlist == null) {
			LastError.setErrorMessage("データベースファイルが見つかりませんでした");

			throw new Exception();
		}
		
		//SYSTEM.FDBの場合
		for (int i = 0; i < DBlist.length; ++i) {
			File dbFile = DBlist[i];
			
			String fileName = dbFile.getName();

			if (dbFile.isFile())
			{
				// system.fdbの更新処理
				if (fileName.equals(DB_FILENAME_SYSTEM_FDB + JPath.DATA_BASE_EXTENSION))
				{
					JConnection consk = JConnection.ConnectSystemDatabase();
					
					try {
						
						consk.Transaction();
						
						// 登録 実際のコピー処理 
						/* Task ごとの処理 */
						for (int j = 0; j < m_vectTaskList.size(); ++j) {
							String queryString = (String) m_vectTaskList.get(j);

							String[] querys = queryString.split(";");
							for (int k = 0; k < querys.length; k++) {
								String query = querys[k];
								if (!Util.isNull(query)) {
									consk.exec(query);
								}
							}
						}
						
						consk.Commit();
						
						} catch (Exception err) {
							
							LastError.setErrorMessage("システムデータベースのスキーマ更新に失敗しました");
							logger.warn("システムデータベースのスキーマ更新に失敗しました" + JApplication.LINE_SEPARATOR + err.getMessage());
							throw err;
						} finally {
							if (consk != null)
							consk.Shutdown();						
						}
				}
				
			}
		}				

		doc.setNodeValue("SchemaVersion", "no", getVersion());
		doc.store();
 
		logger.info("システムデータベースのスキーマ更新が完了しました");
		return true;
	}
}
