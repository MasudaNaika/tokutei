package jp.or.med.orca.jma_tokutei.common.sql;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Pattern;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;

/**
 * データベースのコネクションを管理するためのクラス
 */
public class JConnection {
	private static final String JDBC_DRIVER_NAME_FIREBIRD = "org.firebirdsql.jdbc.FBDriver";
	private Connection mConnection;

	private PreparedStatement m_PreparedState = null;

	/**
	 * コンストラクタ
	 * @param driver JDBCドライバ名
	 * @param url 接続先URL
	 * @param user
	 * @param password
	 * @throws SQLException
	 */
	public JConnection(String driver, String url, String user, String password)
			throws SQLException {

		/* Added 2008/07/29 若月  */
		/* --------------------------------------------------- */
		StringBuffer buffer = new StringBuffer();
		buffer.append("driver:");
		buffer.append(driver);
		buffer.append(JApplication.LINE_SEPARATOR);

		buffer.append("url:");
		buffer.append(url);
		buffer.append(JApplication.LINE_SEPARATOR);

		buffer.append("user:");
		buffer.append(user);
		buffer.append(JApplication.LINE_SEPARATOR);

		buffer.append("password:");
		buffer.append(password);
		buffer.append(JApplication.LINE_SEPARATOR);

		System.out.println(buffer.toString());
		/* --------------------------------------------------- */

		// SQL初期化
		try {
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mConnection = DriverManager.getConnection(url, user, password);
	}

	/**
	 * コネクションを閉じる
	 * @throws SQLException
	 */
	public void Shutdown() throws SQLException {
		// 2008/3/17 西山 修正
		// クローズのみではコネクションへの参照が保持され続け
		// GCの対象にならない(メモリリーク発生の原因となる)
		// クローズ後、強制的にnullを代入
		// ----------------------------------------------------
		//		mConnection.close();
		try {
			mConnection.close();
		} finally {
			mConnection = null;
		}
		// ----------------------------------------------------
	}

	/**
	 * Selectなどの結果が返ってくるクエリを実行する。
	 * @param query クエリ文字列
	 * @return カラム名をキーとしたハッシュテーブルのリスト
	 * @throws SQLException
	 */
	public ArrayList<Hashtable<String, String>> sendExecuteQuery(String query)
		throws SQLException{

		return this.sendExecuteQuery(query, null);
	}


	/**
	 * Selectなどの結果が返ってくるクエリを実行する。
	 * @param query クエリ文字列
	 * @return カラム名をキーとしたハッシュテーブルのリスト
	 * @throws SQLException
	 */
	public ArrayList<Hashtable<String, String>> sendExecuteQuery(String query, String[] params)
			throws SQLException {
		// クエリの表示
		System.out.println(query);

		/* Added 2008/07/16 若月  */
		/* --------------------------------------------------- */
		if (params != null) {
			System.out.print("params: ");
			for (int i = 0; i < params.length; i++) {

				if (i != params.length - 1) {
					System.out.print(params[i]);
					System.out.print(", ");
				}
				else {
					System.out.println(params[i]);
				}
			}
		}
		/* --------------------------------------------------- */

		ArrayList<Hashtable<String, String>> returnList
			= new ArrayList<Hashtable<String, String>>();
		PreparedStatement ps = mConnection.prepareStatement(query);

		if (params != null ){
			if ( params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		}

		ResultSet result = ps.executeQuery();

		//データ取得
		ResultSetMetaData resultData = result.getMetaData();
		int count = resultData.getColumnCount();

		while (result.next()) {
			Hashtable<String, String> rowData = new Hashtable<String, String>();

			for (int i = 1; i <= count; i++) {
				// nullのチェック
				if (result.getObject(i) == null) {
					rowData.put(resultData.getColumnName(i), "");
				} else {
					rowData.put(resultData.getColumnName(i),
							result.getObject(i).toString());
				}
			}

			returnList.add(rowData);
		}

		result.close();
		ps.close();

		return returnList;
	}

	// add ver2 s.inoue 2009/07/07
	/**
	 * Sort順を考慮したSelectなどの結果が返ってくるクエリを実行する。
	 * @param query クエリ文字列
	 * @return カラム名をキーとしたハッシュテーブルのリスト
	 * @throws SQLException
	 */
	public ArrayList<TreeMap<String, String>> sendExecuteSortedQuery(String query, String[] params)
			throws SQLException {
		// クエリの表示
		System.out.println(query);

		if (params != null) {
			System.out.print("params: ");
			for (int i = 0; i < params.length; i++) {

				if (i != params.length - 1) {
					System.out.print(params[i]);
					System.out.print(", ");
				}
				else {
					System.out.println(params[i]);
				}
			}
		}

		ArrayList<TreeMap<String, String>> returnList
			= new ArrayList<TreeMap<String, String>>();
		PreparedStatement ps = mConnection.prepareStatement(query);

		if (params != null ){
			if ( params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		}

		ResultSet result = ps.executeQuery();

		//データ取得
		ResultSetMetaData resultData = result.getMetaData();
		int count = resultData.getColumnCount();

		while (result.next()) {
			TreeMap<String, String> rowData = new TreeMap<String, String>();

			for (int i = 1; i <= count; i++) {
				// nullのチェック
				if (result.getObject(i) == null) {
					rowData.put(resultData.getColumnName(i), "");
				} else {
					rowData.put(resultData.getColumnName(i),
							result.getObject(i).toString());
				}
			}
			returnList.add(rowData);
		}

		result.close();
		ps.close();

		return returnList;
	}

	/* addd 2008/08/06 井上  */
	/* --------------------------------------------------- */
	/**
	 *  prepareStatement 設定
	 *    @param  SQL文
	 *    @return none
	 */
	public void setPrepareQuery(String sql) throws Exception {
		try {
			m_PreparedState = mConnection.prepareStatement(sql);
		} catch (Exception err) {
			throw err;
		}
	}

	/**
	 *  prepareStatement 実行
	 *    @param  設定リスト
	 *    @return none
	 */
	public void execPrepareQuery(ArrayList<String> aryList) throws Exception {
		// System.out.printf("con.execPrepareQuery()[]");
		if (m_PreparedState == null) {
			return;
		}

		if (aryList == null) {
			return;
		}

		try {
			for (int i = 0; i < aryList.size(); ++i) {
				m_PreparedState.setString(i + 1, aryList.get(i).toString());
			}

			m_PreparedState.execute();
		} catch (Exception err) {
			throw err;
		}
	}

	/**
	 *  SQL実行
	 *    @param  SQL文
	 *    @return none
	 */
	public boolean exec(String sql) throws Exception {
		boolean retValue = false;

		Statement stmt = null;
		try {
			stmt = mConnection.createStatement();
			if (stmt != null) {
				System.out.println("exec: " + sql);
			}

			stmt.executeUpdate(sql);

			stmt.close();
			retValue = true;

		} catch (Exception err) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				this.mConnection.close();
			} catch (SQLException e) {
			}

			throw err;
		}

		return retValue;
	}

	/* --------------------------------------------------- */

	/* Modified 2008/06/26 若月  */
	/* --------------------------------------------------- */
//	/**
//	 * Insertなどの結果が返ってこないクエリを送信する。
//	 * @param query クエリ文字列
//	 * @throws SQLException
//	 */
//	public void sendNoResultQuery(String query) throws SQLException {
//		// クエリの表示
//		System.out.println(query);
//
//		Statement st = mConnection.createStatement();
//		st.executeUpdate(query);
//
//		st.close();
//	}
	/* --------------------------------------------------- */
	/**
	 * Insertなどの結果が返ってこないクエリを送信する。
	 * @param query クエリ文字列
	 * @throws SQLException
	 */
	public int sendNoResultQuery(String query, String[] params)
		throws SQLException {

		// クエリの表示
		System.out.println(query);

		/* Added 2008/07/16 若月  */
		/* --------------------------------------------------- */
		if (params != null) {
			System.out.print("params: ");
			for (int i = 0; i < params.length; i++) {

				if (i != params.length - 1) {
					System.out.print(params[i]);
					System.out.print(", ");
				}
				else {
					System.out.println(params[i]);
				}
			}
		}
		/* --------------------------------------------------- */

		PreparedStatement ps = mConnection.prepareStatement(query);

		if (params != null ){
			if ( params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					ps.setString(i + 1, params[i]);
				}
			}
		}

		int count = ps.executeUpdate();
		ps.close();

		return count;
	}

	/**
	 * Insertなどの結果が返ってこないクエリを送信する。
	 * @throws SQLException
	 */
	public int sendNoResultQuery(String query) throws SQLException {
		int count = this.sendNoResultQuery(query, null);
		return count;
	}
	/* --------------------------------------------------- */

	// トランザクション関連

	/**
	 * トランザクションを行っている最中かどうか。
	 */
	private boolean IsTransaction = false;

	/**
	 * トランザクションを開始する。
	 * @throws SQLException
	 */
	public void Transaction() throws SQLException {
		if (IsTransaction == true) {
			throw new RuntimeException();
		}

		mConnection.setAutoCommit(false);
		IsTransaction = true;
	}

	/**
	 * ロールバックを行う。
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		if (IsTransaction == false) {
			throw new RuntimeException();
		}

		mConnection.rollback();
		mConnection.setAutoCommit(true);

		IsTransaction = false;
	}

	/**
	 * コミットを行う。
	 * @throws SQLException
	 */
	public void Commit() throws SQLException {
		if (IsTransaction == false) {
			throw new RuntimeException();
		}

		mConnection.commit();
		mConnection.setAutoCommit(true);

		IsTransaction = false;
	}

	/**
	 * 機関データベースへの接続
	 * @param KikanNumber 機関番号
	 */
	/* Modified 2008/07/28 若月  */
	/* --------------------------------------------------- */
//	public static JConnection ConnectKikanDatabase(String KikanNumber)
//	throws SQLException {
//System.out.println("org.firebirdsql.jdbc.FBDriver : "
//		+ "jdbc:firebirdsql:localhost/3050:"
//		+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber) + ","
//		+ "SYSDBA ," + "masterkey");
//return new JConnection(
//		"org.firebirdsql.jdbc.FBDriver",
//		"jdbc:firebirdsql:localhost/3050:"
//				+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber),
//		"SYSDBA", "masterkey");
//}
	/* --------------------------------------------------- */
	public static JConnection ConnectKikanDatabase(String KikanNumber)
	throws SQLException {

//		String url = "jdbc:firebirdsql:localhost/3050:"
//				+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber);
//
//		String user = "SYSDBA";
//		String password = "masterkey";
//		System.out.println("org.firebirdsql.jdbc.FBDriver : "
//		+ url + ","	+ user + "," + password);

		String driver = JDBC_DRIVER_NAME_FIREBIRD;
		String url =
			"jdbc:firebirdsql:" +
			JApplication.systemDBServer +
			"/" +
			JApplication.systemDBPort +
			":"
			+ JPath.GetKikanDatabaseFilePathToInitJSql(KikanNumber);

		String user = JApplication.systemDBUserName;
		String password = JApplication.systemDBPassword;

		JConnection connection = new JConnection(
				driver,
				url,
				user,
				password
				);

		return connection;
}
	/* --------------------------------------------------- */

	/**
	 * システムデータベースへの接続
	 */
	/* Modified 2008/07/28 若月  */
	/* --------------------------------------------------- */
//	public static JConnection ConnectSystemDatabase() throws SQLException {
//		return new JConnection("org.firebirdsql.jdbc.FBDriver",
//				"jdbc:firebirdsql:localhost/3050:"
//						+ JPath.GetSystemDatabaseFilePathToInitJSql(),
//				"SYSDBA", "masterkey");
//	}
	/* --------------------------------------------------- */
	public static JConnection ConnectSystemDatabase() throws SQLException {

		String driver = JDBC_DRIVER_NAME_FIREBIRD;

		String url =
			"jdbc:firebirdsql:" +
			JApplication.systemDBServer +
			"/" +
			JApplication.systemDBPort +
			":"
			+ JPath.GetSystemDatabaseFilePathToInitJSql();

		String user = JApplication.systemDBUserName;
		String password = JApplication.systemDBPassword;

		JConnection connection = new JConnection(
						driver,
						url,
						user,
						password);

		return connection;
	}
	/* --------------------------------------------------- */

	// add s.inoue 2012/07/04
	public static JConnection ConnectHokenjyaDatabase() throws SQLException {

		String driver = JDBC_DRIVER_NAME_FIREBIRD;

		String url =
			"jdbc:firebirdsql:" +
			JApplication.systemDBServer +
			"/" +
			JApplication.systemDBPort +
			":"
			+ JPath.GetHokenjyaDatabaseFilePathToInitJSql();

		String user = JApplication.systemDBUserName;
		String password = JApplication.systemDBPassword;

		JConnection connection = new JConnection(
						driver,
						url,
						user,
						password);

		return connection;
	}
	/**
	 * 接続することができる機関データベースが存在するか確認する。
	 */
	public static boolean IsExistKikanDatabase() {
		File file = new File(JPath.DatabaseFolder);
		File[] files = file.listFiles();

		if (files == null) {
			return false;
		}

		if (files.length <= 1) {
			return false;
		}

		for (int i = 0; files.length > i; i++) {
			if (Pattern.matches("^\\d{10}.(fdb|FDB)", files[i].getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * データベース上にデータが存在するかどうかを調べる
	 * @param Database 接続先のデータベース
	 * @param Query クエリ
	 * @return 存在すればtrue
	 */
	public static boolean IsExistDatabaseItem(JConnection Database, String Query) {
		ArrayList<Hashtable<String, String>> Item;
		try {
			Item = Database.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		if (Item.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 2008/03/17 西山追加
	// Connectionの取得
	// -------------------------------------------------------------
	public Connection getMConnection() {
		return mConnection;
	}
	// -------------------------------------------------------------

}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

