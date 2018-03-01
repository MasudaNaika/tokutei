package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.*;

import jp.or.med.orca.jma_tokutei.common.sql.dao.TDataTypeCodeDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TDataTypeCode;

public class TDataTypeCodeDaoImpl extends DaoImpl implements TDataTypeCodeDao {

	// edit s.inoue 2009/09/29
	private static final String SELECT_BY_ALL_SQL = "select KOUMOKU_CD, CODE_NUM, CODE_NAME from T_DATA_TYPE_CODE";
	private static final String SELECT_BY_PRIMARYKEY_SQL =
		SELECT_BY_ALL_SQL +
		" where KOUMOKU_CD = ? AND CODE_NUM = ?";

	public TDataTypeCodeDaoImpl(Connection con) {
		super(con);
	}

	public List<TDataTypeCode> selectByAll() throws SQLException {

		Connection con = getConnection();
		if (con == null) return null;

		List<TDataTypeCode> recList = new ArrayList<TDataTypeCode>();
		PreparedStatement stmt = con.prepareStatement(SELECT_BY_ALL_SQL);
		ResultSet rs = stmt.executeQuery();
		SQLException ex = null;
		while (rs.next()) {
			TDataTypeCode dataTypeCode = new TDataTypeCode();
			try {
				dataTypeCode = setValueForRecordModel(dataTypeCode, rs);
				recList.add(dataTypeCode);
			} catch (Exception e) {
				ex = new SQLException(e);
				break;
			}
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		if (ex != null) throw ex;
		return ((!recList.isEmpty()) ? recList : null);
	}

	public TDataTypeCode selectByPrimaryKey(String koumokuCd, Integer codeNum)
			throws SQLException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_PRIMARYKEY_SQL);
		stmt.setString(1, koumokuCd);
		setNumber(stmt, 2, codeNum, INTEGER);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			TDataTypeCode dataTypeCode = new TDataTypeCode();
			try {
				dataTypeCode = setValueForRecordModel(dataTypeCode, rs);
			} catch (Exception e) {
				throw new SQLException(e);
			}
		}
		return null;
	}
}