package jp.or.med.orca.jma_tokutei.onlineupdate.task;

import java.io.*;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.*;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;

import org.apache.log4j.Logger;

/**
 *  2008/03/10 yabu 追加
 *  トランザクション、KIKAN　MDB用のデータ更新クラス
 *  KikanDataUpdateTask
 *
 *    データ更新タスク
 */

/**
 *  KikanDataUpdateTask
 *
 *    データ更新タスク
 */
public class KikanDataUpdateTask extends AbstractTask {

	// s.inoue 20080620 log処理
	// private static org.apache.log4j.Logger logger = Logger.getLogger(KikanDataUpdateTask.class);
	private ArrayList<UpdateTask> m_aryTaskList = new ArrayList<UpdateTask>();

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
		System.out.printf("KikanDataUpdateTask#putTask: %s, %s, %s", strPreQuery, strQuery, strDataURL);
		System.out.println();

		m_aryTaskList.add(new UpdateTask(strPreQuery, strQuery, strDataURL));
	}


	/**
	 *  タスク実行
	 *
	 *    @param none
	 *
	 *    @param none
	 */
	public boolean runTask() throws Exception
	{

		System.out.println("KikanDataUpdateTask#runTask()");
		// 1.property.xml読込
	    XMLDocumentUtil doc = new XMLDocumentUtil( PropertyUtil.getProperty("property.filename") );
	    String strVersion = doc.getNodeValue( "DBDataKikanVersion", "no" );

        System.out.println(m_aryTaskList);

        if( strVersion.compareTo(getVersion()) >= 0 )
        {
        	// 既にアップデート済み
        	return false;
        }

        // 2.DBフォルダ読込
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

		// 3.ログイン機関の取得
		for( int i=0; i<DBlist.length; ++i )
		{
			if( DBlist[ i ].isFile() && (DBlist[ i ].getName().equals(JApplication.kikanNumber)) )
			{
				String s = DBlist[ i ].getName();

				// データベース接続
				JConnection con = JConnection.ConnectKikanDatabase(JApplication.kikanNumber);

			   try
		       {
		        	// データ更新処理
		        	con.Transaction();

		        	for( int j=0; j<m_aryTaskList.size(); ++j )
		        	{
						UpdateTask task = (UpdateTask)m_aryTaskList.get( j );
						task.runTask( con );
		        	}

		        	con.Commit();
		        }
		        catch( Exception err )
		        {
		        	err.printStackTrace();
		        	con.rollback();
		        	// logger.warn("データベースのデータ更新に失敗しました");
		        	LastError.setErrorMessage("データベースのデータ更新に失敗しました");
		        	throw err;
		        }
		        finally
		        {
		        	con = null;
		        }
			}
		}

        doc.setNodeValue( "DBDataKikanVersion", "no", getVersion() );

        doc.store();

		return true;
	}

}
