package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TShiharaiDao;

/**
 * T_SHIHARAIデータアクセスオブジェクト
 */
public class TShiharaiDaoImpl extends DaoImpl implements TShiharaiDao {

    private static final String DELETE_BY_SHIHARAINUM_SQL = "DELETE FROM T_SHIHARAI ";
    private static final String SELECT_BY_COUNT_KEY_SQL = "SELECT COUNT(SHIHARAI_DAIKO_NO) FROM T_SHIHARAI WHERE SHIHARAI_DAIKO_NO=?";

    public TShiharaiDaoImpl(Connection con) {
    	super(con);
    }

    /**
     * T_SHIHARAIレコード削除
     * @param hokenjanum
     * 支払No
     */
	public void delete(String shiharaiNum) throws SQLException {
		Connection con = getConnection();
		if (con == null)
		    throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(DELETE_BY_SHIHARAINUM_SQL);
		stmt.setString(1, shiharaiNum);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * ユニークキー指定でレコード件数取得
	 * @param 支払No
	 * @return 該当レコード
	 * @throws SQLException
	 */
	public int selectByCountUniqueKey(String shiharaiNum)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return 0;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_KEY_SQL);
		stmt.setString(1, shiharaiNum);
		ResultSet rs = stmt.executeQuery();

		int hokenjyaCnt = 0;
		if (rs.next()) {
			hokenjyaCnt = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return hokenjyaCnt;
	}
}
