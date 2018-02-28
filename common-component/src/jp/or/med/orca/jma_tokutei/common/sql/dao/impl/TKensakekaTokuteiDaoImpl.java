package jp.or.med.orca.jma_tokutei.common.sql.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.*;

import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;

/**
 * T_KENSAKEKA_TOKUTEIへのデータアクセスオブジェクト
 *
 */
public class TKensakekaTokuteiDaoImpl extends DaoImpl implements
		TKensakekaTokuteiDao {

	private static final String SELECT_BY_PRIMARYKEY_SQL
		= "SELECT "
			+ " HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, K_P_NO, HANTEI_NENGAPI,"
			+ " TUTI_NENGAPI, KENSA_CENTER_CD, KEKA_INPUT_FLG, SEIKYU_KBN, KOMENTO, NENDO,"
			+ " UKETUKE_ID, NYUBI_YOUKETU, SYOKUZEN_SYOKUGO, HENKAN_NITIJI"
			+ " FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID=? AND KENSA_NENGAPI=?";

	// edit ver2 s.inoue 2009/08/26
	private static final String SELECT_BY_PRIMARYKEY_BY_UKETUKEID_SQL
	= "SELECT "
		+ " HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, K_P_NO, HANTEI_NENGAPI,"
		+ " TUTI_NENGAPI, KENSA_CENTER_CD, KEKA_INPUT_FLG, SEIKYU_KBN, KOMENTO, NENDO,"
		+ " UKETUKE_ID, NYUBI_YOUKETU, SYOKUZEN_SYOKUGO, HENKAN_NITIJI"
		+ " FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID=? ";

	private static final String SELECT_BY_COUNT_SQL
		= "SELECT COUNT(UKETUKE_ID) CNT FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID=? AND KENSA_NENGAPI=?";

	private static final String SELECT_BY_ALL_SQL
		= "SELECT "
			+ " HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI, K_P_NO, HANTEI_NENGAPI,"
			+ " TUTI_NENGAPI, KENSA_CENTER_CD, KEKA_INPUT_FLG, SEIKYU_KBN, KOMENTO, NENDO,"
			+ " UKETUKE_ID, NYUBI_YOUKETU, SYOKUZEN_SYOKUGO, HENKAN_NITIJI"
			+ " FROM T_KENSAKEKA_TOKUTEI";

	private static final String INSERT_SQL
		= "INSERT INTO T_KENSAKEKA_TOKUTEI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// add ver2 s.inoue 2009/07/07
	private static final String UPDATE_KEY_SQL
		= "UPDATE T_KENSAKEKA_TOKUTEI SET " +
		"UKETUKE_ID = ? " +
		"WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? ";

	// add s.inoue 2009/10/04
	private static final String UPDATE_KENSHINDATE_SQL
		= "UPDATE T_KENSAKEKA_TOKUTEI SET " +
		"KENSA_NENGAPI = ? " +
		"WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? ";

	private static final String UPDATE_SQL
		= "UPDATE T_KENSAKEKA_TOKUTEI SET " +
		//"HIHOKENJYASYO_KIGOU = ?, " +
		//"HIHOKENJYASYO_NO = ?, " +
		//"KENSA_NENGAPI = ?, " +
		//"K_P_NO = ?, " +
		//"HANTEI_NENGAPI = ?, " +
		//"TUTI_NENGAPI = ?, " +
		//"KENSA_CENTER_CD = ?, " +
		//"KEKA_INPUT_FLG = ?, " +
		//"SEIKYU_KBN = ?, " +
		//"KOMENTO = ?, " +
		//"NENDO = ?, " +
		"NYUBI_YOUKETU = ?, " +
		"SYOKUZEN_SYOKUGO = ? " +
		"WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? ";
	private static final String UPDATE_SQL_INPUT
		= "UPDATE T_KENSAKEKA_TOKUTEI SET " +
		"HIHOKENJYASYO_KIGOU = ?, " +
		"HIHOKENJYASYO_NO = ?, " +
		"KENSA_NENGAPI = ?, " +
		"K_P_NO = ?, " +
		"HANTEI_NENGAPI = ?, " +
		"TUTI_NENGAPI = ?, " +
		"KENSA_CENTER_CD = ?, " +
		"KEKA_INPUT_FLG = ?, " +
		"SEIKYU_KBN = ?, " +
		"KOMENTO = ?, " +
		"NENDO = ?, " +
		// add ver2 s.inoue 2009/04/02
		"HENKAN_NITIJI = ? " +
		"WHERE " +
		"UKETUKE_ID = ? " +
		"AND " +
		"KENSA_NENGAPI = ? ";

	private static final String DELETE_SQL
		= "DELETE FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID = ?";

	private static final String DELETE_SQL2
		= "DELETE FROM T_KENSAKEKA_TOKUTEI WHERE UKETUKE_ID = ? AND KENSA_NENGAPI = ?";

	/**
	 * コンストラクタ
	 * @param con 接続済コネクション
	 */
	public TKensakekaTokuteiDaoImpl(Connection con) {
		super(con);
	}

	/**
	 * 検査結果データ特定レコード前件削除
	 * @param uketukeId 受付ID
	 */
	public void delete(String uketukeId) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(DELETE_SQL);
		stmt.setLong(1, new Long(uketukeId).longValue());
		setNumber(stmt, 1, new Long(uketukeId), BIGINT);
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * 検査結果データ特定レコード1件削除
	 * @param uketukeId 受付ID
	 */
	public void delete(String uketukeId, String jissibi) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(DELETE_SQL2);
		stmt.setLong(1, new Long(uketukeId).longValue());
		setNumber(stmt, 1, new Long(uketukeId), BIGINT);

		stmt.setString(2, jissibi);

		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * 検査結果データ特定レコード1件挿入
	 * @param tKensakekaTokutei 検査結果データ特定レコードモデル
	 */
	public void insert(TKensakekaTokutei tKensakekaTokutei) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		PreparedStatement stmt = con.prepareStatement(INSERT_SQL);
		stmt.setString(1, tKensakekaTokutei.getHIHOKENJYASYO_KIGOU());
		stmt.setString(2, tKensakekaTokutei.getHIHOKENJYASYO_NO());
		setNumber(stmt, 3, tKensakekaTokutei.getKENSA_NENGAPI(), INTEGER);
		setNumber(stmt, 4, tKensakekaTokutei.getK_P_NO(), INTEGER);
		setNumber(stmt, 5, tKensakekaTokutei.getHANTEI_NENGAPI(), INTEGER);
		setNumber(stmt, 6, tKensakekaTokutei.getTUTI_NENGAPI(), INTEGER);
		stmt.setString(7, tKensakekaTokutei.getKENSA_CENTER_CD());
		setNumber(stmt, 8, tKensakekaTokutei.getKEKA_INPUT_FLG(), INTEGER);
		setNumber(stmt, 9, tKensakekaTokutei.getSEIKYU_KBN(), INTEGER);
		stmt.setString(10, tKensakekaTokutei.getKOMENTO());
		setNumber(stmt, 11, tKensakekaTokutei.getNENDO(), INTEGER);
		setNumber(stmt, 12, tKensakekaTokutei.getUKETUKE_ID(), BIGINT);
		stmt.setString(13, tKensakekaTokutei.getNYUBI_YOUKETU());
		stmt.setString(14, tKensakekaTokutei.getSYOKUZEN_SYOKUGO());
		// add ver2 s.inoue 2009/04/02
		setNumber(stmt,15, tKensakekaTokutei.getHENKAN_NITIJI(), INTEGER);

		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * 全件レコード取得
	 * @return 検査結果データ特定レコード全件
	 */
	public List<TKensakekaTokutei> selectAll() throws SQLException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_ALL_SQL);
		ResultSet rs = stmt.executeQuery();
		List<TKensakekaTokutei> resultList = new ArrayList<TKensakekaTokutei>();
		while (rs.next()) {
			resultList.add(
				setValueForRecordModel(new TKensakekaTokutei(), rs));
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return ((!resultList.isEmpty()) ? resultList : null);
	}

	/** add s.inoue 20081125
	 * プライマリーキー指定でレコード件数取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	public Long selectByCountPrimaryKey(Long uketukeId,
			Integer kensaNengapi)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_SQL);
		stmt.setLong(1, uketukeId);
		setNumber(stmt, 2, kensaNengapi, INTEGER);
		ResultSet rs = stmt.executeQuery();

		Long tokuteiCnt = 0L;
		if (rs.next()) {
			tokuteiCnt = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return tokuteiCnt;

	}

	// edit ver2 s.inoue 2009/08/27
	/**
	 * プライマリーキー指定でレコード件数取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査実施年月日
	 * @param koumokuCd 項目コード
	 * @return TKensakekaSonota
	 * @throws SQLException
	 */
	public Long selectByCountPrimaryKey(Long uketukeId)
		throws SQLException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_COUNT_SQL);
		stmt.setLong(1, uketukeId);
		ResultSet rs = stmt.executeQuery();

		Long tokuteiCnt = 0L;
		if (rs.next()) {
			tokuteiCnt = rs.getLong(1);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return tokuteiCnt;

	}

	/**
	 * 指定キー(受付ID,検査年月日)を指定してレコード取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査年月日
	 */
	public TKensakekaTokutei selectByPrimaryKey(Long uketukeId,
			Integer kensaNengapi) throws SQLException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_PRIMARYKEY_SQL);
		setNumber(stmt, 1, uketukeId, BIGINT);
		setNumber(stmt, 2, kensaNengapi, INTEGER);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return setValueForRecordModel(new TKensakekaTokutei(), rs);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return null;
	}

	// add s.inoue 2012/07/04
    public TKensakekaTokutei selectKekkaTokuteiByPrimaryKey(Long long1, Integer integer)
    throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
	    Connection connection = getConnection();
	    if(connection == null)
	        return null;
	    PreparedStatement preparedstatement = connection.prepareStatement("SELECT TOKUTEI.*,PM.K_P_NO  FROM T_KENSAKEKA_TOKUTEI AS TOKUTEI  LEFT JOIN T_KENSHIN_P_M AS PM  ON TOKUTEI.K_P_NO = PM.K_P_NO  WHERE UKETUKE_ID=? AND KENSA_NENGAPI=? ");
	    setNumber(preparedstatement, 1, long1, -5);
	    setNumber(preparedstatement, 2, integer, 4);
	    ResultSet resultset = preparedstatement.executeQuery();
	    if(resultset.next())
	    {
	        return (TKensakekaTokutei)setValueForRecordModel(new TKensakekaTokutei(), resultset);
	    } else
	    {
	        resultset.close();
	        preparedstatement.close();
	        resultset = null;
	        preparedstatement = null;
	        return null;
	    }
	}
	// add ver2 s.inoue 2009/08/26
	/**
	 * 指定キー(受付ID,検査年月日)を指定してレコード取得
	 * @param uketukeId 受付ID
	 * @param kensaNengapi 検査年月日
	 */
	public TKensakekaTokutei selectByPrimaryKey(Long uketukeId) throws SQLException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Connection con = getConnection();
		if (con == null) return null;

		PreparedStatement stmt = con.prepareStatement(SELECT_BY_PRIMARYKEY_BY_UKETUKEID_SQL);
		setNumber(stmt, 1, uketukeId, BIGINT);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return setValueForRecordModel(new TKensakekaTokutei(), rs);
		}
		rs.close();
		stmt.close();
		rs = null;
		stmt = null;
		return null;
	}

	/**
	 * 更新処理
	 * @param tKensakekaTokutei 更新値をセットしたTKensakekaTokutei
	 */
	public void update(TKensakekaTokutei tKensakekaTokutei,boolean blnInput) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		String updateSqlTmp ="";
		if (blnInput){
			updateSqlTmp = UPDATE_SQL_INPUT;
		}else{
			updateSqlTmp = UPDATE_SQL;
		}

		PreparedStatement stmt = con.prepareStatement(updateSqlTmp);

		if (blnInput){
			stmt.setString(1, tKensakekaTokutei.getHIHOKENJYASYO_KIGOU());
			stmt.setString(2, tKensakekaTokutei.getHIHOKENJYASYO_NO());
			setNumber(stmt, 3, tKensakekaTokutei.getKENSA_NENGAPI(), INTEGER);
			setNumber(stmt, 4, tKensakekaTokutei.getK_P_NO(), INTEGER);
			setNumber(stmt, 5, tKensakekaTokutei.getHANTEI_NENGAPI(), INTEGER);
			setNumber(stmt, 6, tKensakekaTokutei.getTUTI_NENGAPI(), INTEGER);
			stmt.setString(7, tKensakekaTokutei.getKENSA_CENTER_CD());
			setNumber(stmt, 8, tKensakekaTokutei.getKEKA_INPUT_FLG(), INTEGER);
			setNumber(stmt, 9, tKensakekaTokutei.getSEIKYU_KBN(), INTEGER);
			stmt.setString(10, tKensakekaTokutei.getKOMENTO());
			setNumber(stmt, 11, tKensakekaTokutei.getNENDO(), INTEGER);
			// add ver2 s.inoue 2009/04/02
			setNumber(stmt, 12, tKensakekaTokutei.getHENKAN_NITIJI(), INTEGER);

		}else{
			stmt.setString(1, tKensakekaTokutei.getNYUBI_YOUKETU());
			stmt.setString(2, tKensakekaTokutei.getSYOKUZEN_SYOKUGO());
			setNumber(stmt, 3, tKensakekaTokutei.getUKETUKE_ID(), BIGINT);
			setNumber(stmt, 4, tKensakekaTokutei.getKENSA_NENGAPI(), INTEGER);
		}
		stmt.executeUpdate();
		stmt.close();
		stmt = null;
	}

	/**
	 * 更新処理
	 * @param tKensakekaTokutei 更新値をセットしたTKensakekaTokutei
	 */
	@Override
	public void updatekey(Long newUketukeId, Long curUketukeID,
			Integer kensaNengapi) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		String updateSqlTmp ="";
		updateSqlTmp = UPDATE_KEY_SQL;

		PreparedStatement stmt = con.prepareStatement(updateSqlTmp);

		setNumber(stmt, 1, newUketukeId, BIGINT);
		setNumber(stmt, 2, curUketukeID, BIGINT);
		setNumber(stmt, 3, kensaNengapi, INTEGER);

		stmt.executeUpdate();
		stmt.close();
		stmt = null;

	}

	// add s.inoue 2009/10/04
	/**
	 * 更新処理
	 * @param tKensakekaTokutei 更新値をセットしたTKensakekaTokutei
	 */
	public void updateKenshinDate(Integer InputkensaNengapi, Long curUketukeID,
			Integer kensaNengapi) throws SQLException {
		Connection con = getConnection();
		if (con == null) return;

		String updateSqlTmp ="";
		updateSqlTmp = UPDATE_KENSHINDATE_SQL;

		PreparedStatement stmt = con.prepareStatement(updateSqlTmp);

		setNumber(stmt, 1, InputkensaNengapi, INTEGER);
		setNumber(stmt, 2, curUketukeID, BIGINT);
		setNumber(stmt, 3, kensaNengapi, INTEGER);

		stmt.executeUpdate();
		stmt.close();
		stmt = null;

	}

}
