package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.sql.*;
import java.util.*;

/**
 *  DBConnect
 *  
 *    データベース設定クラス
 */
public class DBConnect {

	private Connection m_sqlConnection = null;

	private PreparedStatement m_PreparedState = null;

	/**
	 *  コンストラクタ
	 * 
	 *    @param  none
	 *    
	 *    @return none
	 */
	public DBConnect() {
	}

	/**
	 *  接続開始
	 * 
	 *    @param  データベースパス
	 *    
	 *    @return none
	 */
	private void beginConnection(String strPath) throws Exception {
		try {
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil
					.getProperty("property.filename"));

			// UserName
			String id = doc.getNodeValue("DBConfig", "UserName");

			// UserPass
			String pass = doc.getNodeValue("DBConfig", "Password");

			// DB Information
			String jdbc = "jdbc" + ":" + "firebirdsql" + ":"
					+ doc.getNodeValue("DBConfig", "Server") + "/"
					+ doc.getNodeValue("DBConfig", "Port") + ":" + strPath;

			Class.forName("org.firebirdsql.jdbc.FBDriver");

			m_sqlConnection = DriverManager.getConnection(jdbc, id, pass);
			m_sqlConnection.setAutoCommit(false);
		} catch (Exception err) {
			
			m_sqlConnection.close();
			m_sqlConnection=null;
			throw err;
		}
	}

	/**
	 *  SQL実行
	 *  
	 *    @param  SQL文
	 *    
	 *    @return none
	 */
	public boolean exec(String sql) throws Exception {
		/* Modified 2008/04/09 若月  */
		/* --------------------------------------------------- */
//		try {
//		Statement stmt = m_sqlConnection.createStatement();
//
//		/* Added 2008/04/08 若月  */
//		/* --------------------------------------------------- */
//		System.out.println("exec: " + sql);
//		System.out.println();
//		/* --------------------------------------------------- */
//
//		stmt.execute(sql);
//
//		stmt.close();
//	} catch (Exception err) {
//		throw err;
//	}
		/* --------------------------------------------------- */
		boolean retValue = false;
		
		Statement stmt = null;
		try {
			stmt = m_sqlConnection.createStatement();
			if (stmt != null) {
				/* Added 2008/04/08 若月  */
				/* --------------------------------------------------- */
				System.out.println("exec: " + sql);
				System.out.println();
				/* --------------------------------------------------- */
			}

//			retValue = stmt.execute(sql);
			
			stmt.executeUpdate(sql);
			
			stmt.close();
			
			retValue = true;
			
		} catch (Exception err) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
			try {
				this.m_sqlConnection.close();
			} catch (SQLException e) {
			}
			
			throw err;
		}
		
		return retValue;
		/* --------------------------------------------------- */
	}

	/**
	 *  SQL実行後に取得レコードの先頭行の先頭列を取得
	 *  
	 *    @param  SQL文
	 *    
	 *    @return 取得データ
	 */
	public String execQuerySingle(String sql) throws Exception {
		String strResult = null;

		try {
			Statement st = m_sqlConnection.createStatement();

			/* Added 2008/04/08 若月  */
			/* --------------------------------------------------- */
			System.out.println("execQuerySingle: " + sql);
			System.out.println();
			/* --------------------------------------------------- */

			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				strResult = rs.getString(1);
			}

			rs.close();
			st.close();
		} catch (Exception err) {
			throw err;
		}

		return strResult;
	}

	/**
	 *    @param  SQL文
	 *    
	 *    @return 取得データ
	 */
	public ArrayList<String> execQueryDouble(String sql) throws Exception {
		
		ArrayList<String> arrTK = new ArrayList<String>();
		
		try {
			Statement st = m_sqlConnection.createStatement();

			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				arrTK.add(rs.getString(1));
			}

			rs.close();
			st.close();
		} catch (Exception err) {
			throw err;
		}

		return arrTK;
	}	
	
	/**
	 *  prepareStatement 設定
	 *
	 *    @param  SQL文
	 *    
	 *    @return none
	 */
	public void setPrepareQuery(String sql) throws Exception {
		try {
			m_PreparedState = m_sqlConnection.prepareStatement(sql);
		} catch (Exception err) {
			throw err;
		}
	}

	/**
	 *  prepareStatement 実行
	 *
	 *    @param  設定リスト
	 *    
	 *    @return none
	 */
	public void execPrepareQuery(ArrayList<String> aryList) throws Exception {
//		System.out.printf("con.execPrepareQuery()[]");
		
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
			m_PreparedState.close();
			throw err;
		}
	}

	/**
	 *  トランザクションを明示的に開始
	 *  
	 *    @param  データベースパス
	 *    
	 *    @return none
	 */
	public boolean begin(String strPath) throws Exception {
		boolean retValue = false;
		
		if (m_sqlConnection != null) {
			commit();
		}

		beginConnection(strPath);
		
		if (! m_sqlConnection.isClosed()) {
			retValue = true; 
		}
		
		return retValue;
	}

	/**
	 *  トランザクションをコミット
	 *  
	 *    @param  none
	 *    
	 *    @return none
	 */
	public void commit() {
		if (m_sqlConnection != null) {
			try {
				if (!m_sqlConnection.isClosed()) {
					m_sqlConnection.commit();
					m_sqlConnection.close();
				}
			} catch (Exception err) {
				err.printStackTrace();
			}

			m_sqlConnection = null;
		}
	}

	/**
	 *  トランザクションをロールバック
	 *  
	 *    @param  none
	 * 
	 *    @return none
	 */
	public void rollback() {
		if (m_sqlConnection != null) {
			try {
				if (!m_sqlConnection.isClosed()) {
					m_sqlConnection.rollback();
					m_sqlConnection.close();
				}

			} catch (Exception err) {
				err.printStackTrace();
			}

			m_sqlConnection = null;
		}
	}

	/**
	 *  トランザクションを明示的に終了
	 *  
	 *    @param  none
	 * 
	 *    @return none
	 */
	protected void finalize() {
		commit();
	}
	
	/**
	 *  トランザクションを明示的に終了
	 *  
	 *    @param  none
	 * 
	 *    @return none
	 * @throws SQLException 
	 */
	public void close() throws SQLException {
		if (m_sqlConnection != null){
			if (!m_sqlConnection.isClosed()) {
				m_sqlConnection.close();
			}	
		}
	}
}
