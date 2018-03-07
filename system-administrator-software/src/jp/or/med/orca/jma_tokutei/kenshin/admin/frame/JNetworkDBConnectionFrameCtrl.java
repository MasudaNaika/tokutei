package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.io.File;
import java.sql.SQLException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.common.util.XMLDocumentUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

import org.apache.log4j.Logger;

/**
 * DB情報接続画面のビジネスロジッククラス
 */
public class JNetworkDBConnectionFrameCtrl {
	private static Logger logger = Logger.getLogger(JNetworkDBConnectionFrameCtrl.class);
	
	/**
	 * コンストラクタ
	 */
	public JNetworkDBConnectionFrameCtrl() {
	}

	
	/**
	 * 画面表示用のデータを取得する
	 * 
	 * @return
	 */
	public JNetworkDBConnectionFrameData getFrameData() {
		
		JNetworkDBConnectionFrameData bean = new JNetworkDBConnectionFrameData();
		
		//property.xmlの読込
		getPropertyData(bean);
		
		return bean;
	}
	
	/**
	 * property.xmlから情報を読み込み、Beanへ設定する
	 * 
	 * @param bean
	 */
	private void getPropertyData(JNetworkDBConnectionFrameData bean) {
		try {
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			
			String userName = doc.getNodeValue("DBConfig", "UserName");
			String password = doc.getNodeValue("DBConfig", "Password");
			String absolutePath = doc.getNodeValue("DBConfig", "AbsolutePath");
			String server = doc.getNodeValue("DBConfig", "Server");
			String port = doc.getNodeValue("DBConfig", "Port");
//			System.out.println("property.xml情報　UserName:[" + userName + "] Password:[" + password + "] AbsolutePath:[" + absolutePath + "] Server:[" + server + "] Port:[" + port + "]");
			
			bean.setFdbUserName(userName);
			bean.setFdbPass(password);
			bean.setFdbFolderPath(isEmpty(absolutePath) ? "C:/NITTOKU/DB/" : absolutePath);	//未設定の場合は初期値を設定
			bean.setFdbIPAddress(server);
			bean.setFdbPortNumber(port);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("property.xmlの読込に失敗");
			JErrorMessage.show("M10103", null);
			throw new RuntimeException("property.xmlの読込に失敗", ex);
		}
	}

	
	/**
	 * 登録ボタン押下処理
	 * 
	 * @param bean	画面情報
	 * @return	登録完了：true、登録未完了（入力チェックでエラーとか）：false
	 */
	public boolean register(JNetworkDBConnectionFrameData bean) {
//		System.out.println("register:[" + bean.toString() + "]");
		
		//戻り値用
		boolean isResutl;
		
		try {
			//入力チェック（FDB関連）
			isResutl = validateFDB(bean);
			
			//入力チェックがOKの場合
			if (isResutl) {
				
				//指定されたFDBへ接続できるか確認
				boolean isConnectOK = connectTestFDB(bean.getFdbUserName(), bean.getFdbPass(), bean.getFdbFolderPath(), bean.getFdbIPAddress(), bean.getFdbPortNumber());
				
				//接続OK
				if (isConnectOK) {
					
					//FDBの接続情報（property.xml）を更新
					updatePropertyFile(bean);
					
					//新しいFDB情報で、システムデータベースを再接続する
					reConnectSystemDatabase();
					
				//接続NG
				} else {
					isResutl = false;
					JErrorMessage.show("M10102", null);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			isResutl = false;
		}
		return isResutl;
	}

	/**
	 * 接続先DB情報項目の入力チェック
	 * 
	 * @param bean	画面情報
	 * @return	全項目入力チェックOK：true、一つでも入力チェックNG有り：false
	 */
	private boolean validateFDB(JNetworkDBConnectionFrameData bean) {

		//戻り値用
		boolean isResutl = true;
		
		//フォルダパス
		String folderPath = getFolderPath(bean.getFdbFolderPath());
		if (isEmpty(folderPath) || isEmpty(JValidate.validateFilePath(folderPath))) {
			JErrorMessage.show("M10108", null);
			isResutl = false;
		}
		if (isResutl) {
			//ホスト名 or IPアドレス
			String fdbIPAddress = bean.getFdbIPAddress();
			if (isEmpty(fdbIPAddress) || isEmpty(JValidate.validateIPAddress(fdbIPAddress))) {
				JErrorMessage.show("M10109", null);
				isResutl = false;
			}
		}
		if (isResutl) {
			//ポート番号
			String portNumber = bean.getFdbPortNumber();
			if (isEmpty(portNumber) || isEmpty(JValidate.validatePortNumber(portNumber))) {
				JErrorMessage.show("M10110", null);
				isResutl = false;
			}
		}
		if (isResutl) {
			//ユーザID
			String userName = bean.getFdbUserName();
			if (isEmpty(userName) || isEmpty(JValidate.validateORCANichireseUserName(userName))) {
				JErrorMessage.show("M10111", null);
				isResutl = false;
			}
		}
		if (isResutl) {
			//パスワード
			String pass = bean.getFdbPass();
			if (isEmpty(pass) || isEmpty(JValidate.validateORCANichiresePassword(pass))) {
				JErrorMessage.show("M10112", null);
				isResutl = false;
			}
		}
		return isResutl;
	}
	

	/**
	 * Beanから情報を読み込み、property.xmlファイル（FDBの接続情報）を更新する
	 * 
	 * @param bean
	 */
	private void updatePropertyFile(JNetworkDBConnectionFrameData bean) {
		
		try {
			String userName = bean.getFdbUserName();
			String password = bean.getFdbPass();
			String absolutePath = getFolderPath(bean.getFdbFolderPath());
			String server = bean.getFdbIPAddress();
			String port = bean.getFdbPortNumber();
//			System.out.println("Bean情報　UserName:[" + userName + "] Password:[" + password + "] AbsolutePath:[" + absolutePath + "] Server:[" + server + "] Port:[" + port + "]");

			//値を設定
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			doc.setNodeValueEmptyTag("DBConfig", "UserName", userName);
			doc.setNodeValueEmptyTag("DBConfig", "Password", password);
			doc.setNodeValueEmptyTag("DBConfig", "AbsolutePath", absolutePath);
			doc.setNodeValueEmptyTag("DBConfig", "Server", server);
			doc.setNodeValueEmptyTag("DBConfig", "Port", port);
			
			//ファイル保存
			doc.store();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("property.xmlの書き込みに失敗");
			JErrorMessage.show("M10104", null);
			throw new RuntimeException("property.xmlの書き込みに失敗", ex);
		}
	}
	
	/**
	 * property.xmlの内容で、システムデータベースを再接続する
	 * ※JApplication.javaのloadメソッドの一部をコピーし修正
	 */
	private void reConnectSystemDatabase() {
		
		XMLDocumentUtil doc;
		try {
			doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			JApplication.systemDBUserName = doc.getNodeValue("DBConfig", "UserName");
			JApplication.systemDBPassword = doc.getNodeValue("DBConfig", "Password");

			//AbsolutePathから絶対パスを取得
			String path = doc.getNodeValue("DBConfig", "AbsolutePath");
			
			//絶対パスが設定されていない場合
			if (path == null ||  path.isEmpty()) {
			
				//デフォルトのパスを取得
				path = doc.getNodeValue("DBConfig", "Path");
			
				//設定がない場合は、デフォルトの場所（カレントディレクトリ/DB）を使用する
				if (path == null || path.isEmpty()) {
			
					StringBuilder sb = new StringBuilder();
					sb.append(getFolderPath(JPath.CURRENT_DIR_PATH));
					sb.append(getFolderPath("DB"));
					path = sb.toString();
					
				//設定がある場合は、カレントディレクトリからの相対パスとする
				} else {
					File relativePath = new File(path);
					path = getFolderPath(relativePath.getCanonicalPath());
				}
			}
			
			JApplication.systemDBPath = path;
			JApplication.systemDBServer = doc.getNodeValue("DBConfig", "Server");
			JApplication.systemDBPort = doc.getNodeValue("DBConfig", "Port");
			
			//システムデータベースを再接続
			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage(), sqlEx);
			JErrorMessage.show("M10105", null);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			JErrorMessage.show("M10105", null);
		}
	}
	
	
	/**
	 * 接続先DBと接続テストを行う
	 * 
	 * @param fdbUserName
	 * @param fdbUserPass
	 * @param fdbFolderPath
	 * @param fdbIPAddress
	 * @param fdbPortNumber
	 * @return	接続OK：true、接続NG：false
	 */
	public boolean connectTestFDB(String fdbUserName, String fdbUserPass, String fdbFolderPath, String fdbIPAddress, String fdbPortNumber) {
		
		//戻り値用
		boolean isResutl = true;
		
		try {
			StringBuilder url = new StringBuilder();
			url.append("jdbc:firebirdsql:");
			url.append(fdbIPAddress);
			url.append("/");
			url.append(fdbPortNumber);
			url.append(":");
			url.append(getFolderPath(fdbFolderPath));
			url.append("System");
			url.append(JPath.DATA_BASE_EXTENSION);
			
			//接続できるか確認
			JConnection connection = new JConnection("org.firebirdsql.jdbc.FBDriver", url.toString(), fdbUserName, fdbUserPass);
			connection.Transaction();
			connection.rollback();
			connection.Shutdown();
			
		} catch(SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage());
			isResutl = false;
		}
		return isResutl;
	}
	
	/**
	 * パス文字列の最後がフォルダ区切り文字で無い場合、区切り文字を付加する
	 * ※パス文字列がブランクの場合は、そのままの文字列を返却する
	 * ※付加する区切り文字列は「/」（Unix系の区切り文字列だが、Windowsでも大丈夫のはず）で、File.separatorとかにすると、サーバがUnix系かつローカルがWindowsだとエラーになる
	 * 
	 * @param path	パス文字列
	 * @return	区切り文字を付加したパス文字列
	 */
	private String getFolderPath(String path) {
//		System.out.println("getFolderPath path:[" + path + "]");
		
		String result = path;
		if (!isEmpty(path)) {
			if (!path.endsWith("\\") && !path.endsWith("/")) {
				result = path.concat("/");
//				System.out.println("付加  result:[" + result + "]");
			}
		}
		return result;
	}
	
	/**
	 * 空文字とnullチェック
	 * 
	 * @param str
	 * @return		空文字 or Nullの場合：true、何かしら値が有る場合：false
	 */
	private boolean isEmpty(String str) {
		return (str != null) ? str.isEmpty() : true;
	}
}
