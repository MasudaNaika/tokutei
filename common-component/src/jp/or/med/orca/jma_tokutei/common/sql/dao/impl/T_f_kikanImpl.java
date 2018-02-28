package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;

import static jp.or.med.orca.jma_tokutei.common.sql.SqlConstants.*;

/**
 * T_KOJINデータアクセスオブジェクト
 *
 * @author nishiyama
 */
public class T_f_kikanImpl extends DaoImpl implements TKojinDao {

	/* T_F_KIKAN オリジナルセクション */
	private static final String COLUMN_TKIKAN_NO = "TKIKAN_NO";

	private static final String COLUMN_KIKAN_NAME = "KIKAN_NAME";

	private static final String COLUMN_NITI_RESE = "NITI_RESE";

	private static final String TABLE_NAME = "T_F_KIKAN";

	private static final String[] COLUMNS = { COLUMN_TKIKAN_NO,
			COLUMN_KIKAN_NAME, COLUMN_NITI_RESE, };

	private static final String[] PRIMARY_KEYS = { COLUMN_TKIKAN_NO, };

	/* SQL生成セクション */
	private static final String DELETE_SQL = createDeleteSql();

	private static String createDeleteSql() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(DELETE);
		buffer.append(FROM);
		buffer.append(TABLE_NAME);
		buffer.append(WHERE);

		int length = PRIMARY_KEYS.length;
		for (int i = 0; i < length; i++) {
			buffer.append(PRIMARY_KEYS[i]);
			buffer.append(EQUAL);
			buffer.append(QUESTION);
			if (i < length - 1) {
				buffer.append(AND);
			}
		}

		return buffer.toString();
	}

	private static final String INSERT_SQL = createInsertSql();

	private static String createInsertSql() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(INSERT_INTO);
		buffer.append(TABLE_NAME);
		buffer.append(VALUES);
		buffer.append(PARENTHESIS_START);

		int length = COLUMNS.length;
		for (int i = 0; i < length; i++) {
			buffer.append(COLUMNS[i]);
			buffer.append(EQUAL);
			buffer.append(QUESTION);
			if (i < length - 1) {
				buffer.append(COMMA);
			}
		}

		buffer.append(PARENTHESIS_END);

		return buffer.toString();
	}

	private static final String UPDATE_SQL = createUpdateSql();

	private static String createUpdateSql() {

		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE T_F_KIKAN SET ");
		buffer.append(" TKIKAN_NO = ?, ");
		buffer.append(" KIKAN_NAME = ?, ");
		buffer.append(" NITI_RESE = ? ");
		buffer.append(" WHERE " + "TKIKAN_NO = ? ");

		return buffer.toString();
	}

	private static final String SELECT_ALL_SQL = "SELECT * FROM T_F_KIKAN";

	private static final String SELECT_BY_PRIMARYKEY_SQL = "SELECT * FROM T_F_KIKAN WHERE UKETUKE_ID=?";

	private static final String SELECT_NEWUKETUKE_ID_SQL = "SELECT MAX(UKETUKE_ID) FROM T_F_KIKAN WHERE UKETUKE_ID > ?";
	// add ver2 s.inoue 2009/05/01 interface継承
	private static final String SELECT_BY_COUNT_UNIQUEKEY_SQL = "SELECT COUNT(UKETUKE_ID) FROM T_F_KIKAN WHERE TKIKAN_NO=?";

	private static final String UKETUKE_ID_FORMAT = "%d%02d%02d";

	public T_f_kikanImpl(Connection con) {
		super(con);
	}

	/**
	 * T_KOJINレコード削除
	 *
	 * @param uketukeId
	 *                受付ID
	 */
	public void delete(String uketukeId) throws SQLException {
		Connection con = getConnection();
		if (con == null)
			throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(DELETE_SQL);
		stmt.setLong(0, new Long(uketukeId));
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * T_KOJINレコード挿入
	 *
	 * @param tKojin
	 *                新規レコードをセットしたTKojin
	 */
	public void insert(TKojin tKojin) throws SQLException {
		Connection con = getConnection();
		if (con == null)
			throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(INSERT_SQL);
		stmt.setString(1, tKojin.getPTNUM());
		stmt.setString(2, tKojin.getJYUSHIN_SEIRI_NO());
		stmt.setInt(3, tKojin.getYUKOU_KIGEN());
		stmt.setString(4, tKojin.getHKNJANUM());
		stmt.setString(5, tKojin.getHIHOKENJYASYO_KIGOU());
		stmt.setString(6, tKojin.getHIHOKENJYASYO_NO());
		stmt.setString(7, tKojin.getNAME());
		stmt.setString(8, tKojin.getKANANAME());
		stmt.setString(9, tKojin.getNICKNAME());
		stmt.setInt(10, tKojin.getBIRTHDAY());
		stmt.setInt(11, tKojin.getSEX());
		stmt.setString(12, tKojin.getHOME_POST());
		stmt.setString(13, tKojin.getHOME_ADRS());
		stmt.setString(14, tKojin.getHOME_BANTI());
		stmt.setString(15, tKojin.getHOME_TEL1());
		stmt.setString(16, tKojin.getKEITAI_TEL());
		stmt.setString(17, tKojin.getFAX());
		stmt.setString(18, tKojin.getEMAIL());
		stmt.setString(19, tKojin.getKEITAI_EMAIL());
		stmt.setString(20, tKojin.getKEIYAKU_TORIMATOME());
		stmt.setInt(21, tKojin.getKOUHUBI());
		stmt.setString(22, tKojin.getSIHARAI_DAIKOU_BANGO());
		stmt.setInt(23, tKojin.getMADO_FUTAN_K_SYUBETU());
		stmt.setString(24, tKojin.getMADO_FUTAN_KIHON());
		stmt.setInt(25, tKojin.getMADO_FUTAN_S_SYUBETU());
		stmt.setString(26, tKojin.getMADO_FUTAN_SYOUSAI());
		stmt.setInt(27, tKojin.getMADO_FUTAN_T_SYUBETU());
		stmt.setString(28, tKojin.getMADO_FUTAN_TSUIKA());
		stmt.setInt(29, tKojin.getMADO_FUTAN_D_SYUBETU());
		stmt.setString(30, tKojin.getMADO_FUTAN_DOC());
		stmt.setInt(31, tKojin.getNENDO());
		stmt.setLong(32, tKojin.getUKETUKE_ID());
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	// add ver2 s.inoue 2009/05/01
	/**
	 * ユニークキー指定でレコード件数取得
	 * @param jyushinSeiriNo 受診券整理番号
	 * @return kojinCnt 該当レコード件数
	 * @throws SQLException
	 */
	public Long selectByCountUniqueKey(
			String jyushinSeiriNo)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_UNIQUEKEY_SQL);
		stmt.setString(1, jyushinSeiriNo);
		ResultSet rs = stmt.executeQuery();

		Long kojinCnt = 0L;
		if (rs.next()) {
			kojinCnt = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return kojinCnt;
	}

	/**
	 * 指定受付IDのレコードを1件取得
	 *
	 * @param uketukeId
	 * @return 指定受付IDを持つTKojinレコードモデル
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TKojin selectByPrimaryKey(String uketukeId) throws SQLException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		Connection con = getConnection();
		if (con == null)
			throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(SELECT_BY_PRIMARYKEY_SQL);
		stmt.setLong(1, new Long(uketukeId));
		ResultSet rs = stmt.executeQuery();
		if (!rs.next())
			return null;
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return setValueForRecordModel(new TKojin(), rs);
	}

	/**
	 * レコード全件取得
	 *
	 * @return TKojinレコードモデルのリスト
	 * @throws SQLException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<TKojin> selectAll() throws SQLException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null)
			throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(SELECT_ALL_SQL);
		ResultSet rs = stmt.executeQuery();

		List<TKojin> resultList = new ArrayList<TKojin>();
		while (rs.next()) {
			resultList.add(setValueForRecordModel(new TKojin(), rs));
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return ((!resultList.isEmpty()) ? resultList : null);
	}

	/**
	 * レコード更新
	 *
	 * @param tKojin
	 * @throws SQLException
	 */
	public void update(TKojin tKojin) throws SQLException {
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement(UPDATE_SQL);
		stmt.setString(1, tKojin.getPTNUM());
		stmt.setString(2, tKojin.getJYUSHIN_SEIRI_NO());
		stmt.setInt(3, tKojin.getYUKOU_KIGEN());
		stmt.setString(4, tKojin.getHKNJANUM());
		stmt.setString(5, tKojin.getHIHOKENJYASYO_KIGOU());
		stmt.setString(6, tKojin.getHIHOKENJYASYO_NO());
		stmt.setString(7, tKojin.getNAME());
		stmt.setString(8, tKojin.getKANANAME());
		stmt.setString(9, tKojin.getNICKNAME());
		stmt.setInt(10, tKojin.getBIRTHDAY());
		stmt.setInt(11, tKojin.getSEX());
		stmt.setString(12, tKojin.getHOME_POST());
		stmt.setString(13, tKojin.getHOME_ADRS());
		stmt.setString(14, tKojin.getHOME_BANTI());
		stmt.setString(15, tKojin.getHOME_TEL1());
		stmt.setString(16, tKojin.getKEITAI_TEL());
		stmt.setString(17, tKojin.getFAX());
		stmt.setString(18, tKojin.getEMAIL());
		stmt.setString(19, tKojin.getKEITAI_EMAIL());
		stmt.setString(20, tKojin.getKEIYAKU_TORIMATOME());
		stmt.setInt(21, tKojin.getKOUHUBI());
		stmt.setString(22, tKojin.getSIHARAI_DAIKOU_BANGO());
		stmt.setInt(23, tKojin.getMADO_FUTAN_K_SYUBETU());
		stmt.setString(24, tKojin.getMADO_FUTAN_KIHON());
		stmt.setInt(25, tKojin.getMADO_FUTAN_S_SYUBETU());
		stmt.setString(26, tKojin.getMADO_FUTAN_SYOUSAI());
		stmt.setInt(27, tKojin.getMADO_FUTAN_T_SYUBETU());
		stmt.setString(28, tKojin.getMADO_FUTAN_TSUIKA());
		stmt.setInt(29, tKojin.getMADO_FUTAN_D_SYUBETU());
		stmt.setString(30, tKojin.getMADO_FUTAN_DOC());
		stmt.setInt(31, tKojin.getNENDO());
		stmt.setLong(32, tKojin.getUKETUKE_ID());
		stmt.setLong(33, tKojin.getUKETUKE_ID());
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	// add s.inoue 2010/12/01
	/**
	 * 受付IDの採番
	 * @return 受付ID
	 */
	public long selectNewUketukeId() throws SQLException {
		return selectNewUketukeId(1);
	}

	/**
	 * 受付IDの採番
	 * @return 受付ID
	 */
	public long selectNewUketukeId(int diff) throws SQLException {
		Connection con = getConnection();
		if (con == null)
			throw new SQLException("Not connected.");
		PreparedStatement stmt = con.prepareStatement(SELECT_NEWUKETUKE_ID_SQL);

		Calendar calendar = Calendar.getInstance();
		long idValue = new Long(String.format(UKETUKE_ID_FORMAT, calendar
				.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + diff, calendar
				.get(Calendar.DAY_OF_MONTH))).longValue() * 10000;
		stmt.setLong(1, idValue);
		ResultSet rs = stmt.executeQuery();

		long resultValue = 0L;
		if (rs.next()) {
			resultValue = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		idValue = (resultValue > 0) ? resultValue : idValue;
		idValue++;
		return idValue;
	}

}
