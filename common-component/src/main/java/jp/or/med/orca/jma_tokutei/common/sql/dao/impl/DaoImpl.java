package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import static java.sql.Types.*;

import jp.or.med.orca.jma_tokutei.common.sql.dao.Dao;
import jp.or.med.orca.jma_tokutei.common.sql.model.RecordModel;

/**
 * データアクセスオブジェクト
 * スーパークラス
 * @author nishiyama
 *
 */
public abstract class DaoImpl implements Dao {

	private static final String SELECT_NEW_UKETUKE_ID_SQL
	= "SELECT UKETUKE_ID FROM T_KOJIN WHERE HIHOKENJYASYO_KIGOU=? AND HIHOKENJYASYO_NO=?";

	private Connection con;
	
	/**
	 * コンストラクタ
	 * @param con 接続済コネクション
	 */
	protected DaoImpl(Connection con) {
		this.con = con;
	}

	/**
	 * コネクション取得
	 * @return 接続済コネクション
	 */
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * T_KOJINから受付ID取得
	 * @param hihokenjyasyo_kigou 被保険者証等記号
	 * @param hihokenjyasyo_no 被保険者証等番号
	 * @return 受付ID
	 * @throws SQLException
	 */
	public Long selectNewUketukeId(String hihokenjyasyo_kigou,
			String hihokenjyasyo_no) throws SQLException {
		
		Connection con = getConnection();
		if (con == null) return null;
		
		PreparedStatement stmt = con.prepareStatement(SELECT_NEW_UKETUKE_ID_SQL);
		stmt.setString(1, hihokenjyasyo_kigou);
		stmt.setString(2, hihokenjyasyo_no);
		ResultSet rs = stmt.executeQuery();
		Long uketukeId = null;
		if (rs.next()) {
			uketukeId = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return uketukeId;
	}

	/**
	 * レコードモデルの各フィールドにデータをセットする
	 * @param rs ResultSet
	 * @param metaData ResultSetMetaData
	 * @return データをセットしたTKojinレコードモデル
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected <T extends RecordModel> T setValueForRecordModel (
			T model,
			ResultSet rs)
		throws SQLException, 
				NoSuchMethodException, 
				IllegalAccessException, 
				InvocationTargetException {
		
		if ((rs == null) || (model == null)) return null;
		
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		// レコードから各カラム値を取得し、指定されたレコードモデルの
		// 各フィールドに格納する
		for (int i = 1 ; i <= columnCount ; i++) {
			Object obj = rs.getObject(i);
			String columnName = metaData.getColumnName(i);
			// "setXXXXX(XXXXXはテーブルのカラム名)メソッドを呼び出す
			String setterMethodName = "set" + columnName;
			if (obj != null) {
				Method method = 
					model.getClass().getMethod(setterMethodName,obj.getClass());
				method.invoke(model, rs.getObject(i));
			}
		}
		return model;
	}
	
	/**
	 * 数値型をバインド変数にバインドするためのメソッド
	 * 数値型列にnullが設定された場合にsetNull()を実行する
	 * @param stmt     バインド対象PreparedStatement
	 * @param colIndex バインド変数インデックス
	 * @param value    数値
	 * @param sqlType  バインド指定するSQLの型
	 * @throws SQLException
	 */
	protected void setNumber(
			PreparedStatement stmt, 
			int colIndex, Number value, int sqlType) throws SQLException {
		
		if ((stmt == null) || (colIndex < 1)) return;
		
		if (value == null) {
			stmt.setNull(colIndex, sqlType);
			return;
		}
		
		// 指定されたSQLの型によってPreparedStatement
		// のsetXXXXメソッドを実行する
		if (sqlType == INTEGER) {
			stmt.setInt(colIndex, (Integer)value);
		} else if (sqlType == BIGINT) {
			stmt.setLong(colIndex,(Long)value);
		} else if (sqlType == FLOAT) {
			stmt.setFloat(colIndex, (Float)value);
		} else if (sqlType == DOUBLE) {
			stmt.setDouble(colIndex, (Double)value);
		}
		// 他の型は必要に応じて定義する
	}
}