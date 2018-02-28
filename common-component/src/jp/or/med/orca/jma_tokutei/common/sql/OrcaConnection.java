package jp.or.med.orca.jma_tokutei.common.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 * ORCA DB 接続
 */
public class OrcaConnection {
	private static final String TABLE_NAME_TBL_SYSUSER = "tbl_sysuser";
	private static final String COLUMN_NAME_HOSPNUM = "hospnum";
	private static final String COLUMN_NAME_USERID = "userid";

	private static final String URL_JDBC_POSTGRESQL_PREFIX = "jdbc:postgresql://";

	private static final String DRIVER_POSTGRESQL = "org.postgresql.Driver";

	private static final int REVISION_MIN = 0;

	private static final int MINOR_VERSION_MIN = 2;

	private static final int MAJOR_VERSION_MIN = 4;

	private JConnection con = null;
	private JConnection testcon = null;

	private static final String SELECT_HOSPNUM_PREPARED_SQL =
		createSelectHospnumPreparedSql();

	private static final String SELECT_SYSUSER_PREPARED_SQL =
		createSelectSysuserPreparedSql();

	private static final String SELECT_PATIENT_DATA_PREPARED_SQL =
		createSelectPatientDataPreparedSql();

	private static String createSelectHospnumPreparedSql() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" SELECT ");
		buffer.append(COLUMN_NAME_HOSPNUM);
		buffer.append(" FROM ");
		buffer.append(TABLE_NAME_TBL_SYSUSER);
		buffer.append(" WHERE ");
		buffer.append(TABLE_NAME_TBL_SYSUSER);
		buffer.append(".");
		buffer.append(COLUMN_NAME_USERID);
		buffer.append(" = ? ");
		String sql = buffer.toString();
		return sql;
	}

	private static String createSelectSysuserPreparedSql() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" SELECT ");
		buffer.append(" 1 ");
		buffer.append(" FROM ");
		buffer.append(TABLE_NAME_TBL_SYSUSER);
		buffer.append(" WHERE ");
		buffer.append(COLUMN_NAME_USERID);
		buffer.append(" = ? ");
		String sql = buffer.toString();
		return sql;
	}

	private static String createSelectPatientDataPreparedSql(){
		StringBuffer buffer = new StringBuffer();

		/* Modified 2008/05/05 若月  */
		/* --------------------------------------------------- */
//		buffer.append(" SELECT * ");
		/* --------------------------------------------------- */
		buffer.append(" SELECT ");
		buffer.append(" PTNUM, ");
		buffer.append(" KANANAME, ");
		buffer.append(" NAME, ");
		buffer.append(" NICKNAME, ");
		buffer.append(" BIRTHDAY, ");
		buffer.append(" HOME_POST, ");
		buffer.append(" HOME_ADRS, ");
		buffer.append(" HOME_BANTI, ");
		buffer.append(" HOME_TEL1, ");
		buffer.append(" HOME_TEL2, ");
		buffer.append(" KEITAI_TEL, ");
		buffer.append(" FAX, ");
		buffer.append(" SEX, ");

		// edit s.inoue 2010/02/08
		buffer.append(" EMAIL, ");

		buffer.append(" KIGO, ");
		buffer.append(" NUM, ");
		buffer.append(" TEKEDYMD ");
		/* --------------------------------------------------- */

		buffer.append(" FROM TBL_PTNUM AS NUM ");

		buffer.append(" INNER JOIN TBL_PTINF AS INF ");
		buffer.append(" ON ");
		buffer.append(" NUM.PTID = INF.PTID ");

		// eidt s.inoue 2012/08/20
		buffer.append(" AND INF.HOSPNUM = NUM.HOSPNUM ");

		buffer.append(" INNER JOIN TBL_PTHKNINF AS HKNINF ");
		buffer.append(" ON ");
		buffer.append(" NUM.PTID = HKNINF.PTID ");

		// eidt s.inoue 2012/08/20
		buffer.append(" AND HKNINF.HOSPNUM = NUM.HOSPNUM ");

		buffer.append(" WHERE ");
		buffer.append(" NUM.PTNUM = ?");
		buffer.append(" AND ");
		buffer.append(" NUM.HOSPNUM = ( ");

		buffer.append(SELECT_HOSPNUM_PREPARED_SQL);

		buffer.append(" )");

		return buffer.toString();
	}

	public JConnection getOrcaDbConnectionFromConfigFile() {
		/* Modified 2008/05/06 若月  */
		/* --------------------------------------------------- */
//		if (this.con == null) {
//			try {
//				String jdbcPostgresqlUrl = URL_JDBC_POSTGRESQL_PREFIX
//						+ JApplication.orcaSetting.getIpAddress() + ":"
//						+ JApplication.orcaSetting.getPort() + "/"
//						+ JApplication.orcaSetting.getDatabase();
//
//				String user = JApplication.orcaSetting.getUser();
//				String password = JApplication.orcaSetting.getPassword();
//
//				this.con = new JConnection(DRIVER_POSTGRESQL, jdbcPostgresqlUrl, user,
//						password);
//
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//				JErrorMessage.show("M4390", null);
//			}
//		}
		/* --------------------------------------------------- */
		String ipAddress = JApplication.orcaSetting.getOrcaIpAddress();
		String port = JApplication.orcaSetting.getOrcaPort();
		String database = JApplication.orcaSetting.getOrcaDatabase();
		String user = JApplication.orcaSetting.getOrcaUser();
		String password = JApplication.orcaSetting.getOrcaPassword();

		this.getOrcaDbConnection(ipAddress, port, database, user, password);
		/* --------------------------------------------------- */

		return this.con;
	}

	public JConnection getOrcaDbTestConnection(
			String ipAddress,
			String port,
			String database,
			String user,
			String password
			) {

		if (this.testcon == null) {

			/* Modified 2008/05/21 若月  */
			/* --------------------------------------------------- */
//			String jdbcPostgresqlUrl = URL_JDBC_POSTGRESQL_PREFIX
//			+ ipAddress + ":"
//			+ port
//			+ "/"
//			+ password;
			/* --------------------------------------------------- */
			String jdbcPostgresqlUrl = URL_JDBC_POSTGRESQL_PREFIX
				+ ipAddress + ":"
				+ port
				+ "/"
				+ database;
			/* --------------------------------------------------- */

			try {
				this.testcon = new JConnection(
						DRIVER_POSTGRESQL,
						jdbcPostgresqlUrl,
						user,
						password);

			} catch (SQLException e1) {
				e1.printStackTrace();
				// del s.inoue 2010/04/23
				// JErrorMessage.show("M4390", null);
			}
		}

		return this.testcon;
	}

	public JConnection getOrcaDbConnection(
			String ipAddress,
			String port,
			String database,
			String user,
			String password
			) {

		if (this.con == null) {

			/* Modified 2008/06/08 若月  */
			/* --------------------------------------------------- */
//			String jdbcPostgresqlUrl = URL_JDBC_POSTGRESQL_PREFIX
//			+ ipAddress + ":"
//			+ port + "/"
//			+ password;
			/* --------------------------------------------------- */
			String jdbcPostgresqlUrl = URL_JDBC_POSTGRESQL_PREFIX
			+ ipAddress + ":"
			+ port + "/"
			+ database;
			/* --------------------------------------------------- */

			try {
				this.con = new JConnection(
						DRIVER_POSTGRESQL,
						jdbcPostgresqlUrl,
						user,
						password);

			} catch (SQLException e1) {
				e1.printStackTrace();
				JErrorMessage.show("M4390", null);
			}
		}

		return this.con;
	}

	public String getHospnum(JConnection con, String userId){
		/* 医療機関番号 */
		String hospNum = null;

		ArrayList<Hashtable<String, String>> result = null;
		try {
			String[] params = { userId };

			result = con.sendExecuteQuery(SELECT_HOSPNUM_PREPARED_SQL, params);

		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M4391", null);
			try {
				con.Shutdown();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		}

		if (result.size() == 1) {
			hospNum = result.get(0).get(COLUMN_NAME_HOSPNUM);
		}
//		else {
//			JErrorMessage.show("M9630", null);
//		}

		return hospNum;
	}

	public boolean existsUserId(JConnection con, String userId){
		boolean exists = false;

		ArrayList<Hashtable<String, String>> result = null;
		try {
			String[] params = { userId };

			result = con.sendExecuteQuery(SELECT_SYSUSER_PREPARED_SQL, params);

		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M4391", null);
			try {
				con.Shutdown();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		}

		if (result.size() == 1) {
			exists = true;
		}

		return exists;
	}

	/**
	 * ORCA のバージョンを取得する。
	 */
	public String getOrcaVersion(JConnection con) {

//		JConnection con = this.getOrcaDbConnection();
//		if (con == null) {
//			return null;
//		}

		/* バージョン情報 */
		String version = null;

		ArrayList<Hashtable<String, String>> Result = null;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append(" SELECT version ");
			buffer.append(" FROM tbl_dbkanri AS kanri ");

			Result = con.sendExecuteQuery(buffer.toString());

		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M4391", null);
			try {
				con.Shutdown();

			} catch (SQLException e2) {

				e2.printStackTrace();
			}
		}

		if (Result.size() == 1) {
			version = Result.get(0).get("version");

		} else {
			JErrorMessage.show("M9629", null);
		}

		return version;
	}

	public ArrayList<Hashtable<String, String>> getOrcaData(String orcaId) {

		String nichireseUser = JApplication.orcaSetting.getNichireseUser();

		if (orcaId == null || orcaId.isEmpty()) {
			return null;
		}

//		orcaId = JValidate.fillZero(orcaId, 5);

		String[] params = new String[]{ orcaId, nichireseUser };

		ArrayList<Hashtable<String, String>> result = null;
		try {
			result = con.sendExecuteQuery(
				SELECT_PATIENT_DATA_PREPARED_SQL,
				params);

		} catch (SQLException e1) {
			e1.printStackTrace();
			JErrorMessage.show("M4391", null);
			try {
				con.Shutdown();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * ORCA のバージョン番号を検証する。
	 */
	public boolean validateOrcaVersion(String version) {
		boolean validVersion = false;

		if (version != null
				&& ! version.isEmpty()
				&& version.length() >= 6) {

			String majorVersionString = version.substring(0,2);
			String minorVersionString = version.substring(2,4);
			String revisionString = version.substring(4,6);

			int majorVersion = Integer.parseInt(majorVersionString);
			int minorVersion = Integer.parseInt(minorVersionString);
			int revision = Integer.parseInt(revisionString);

			if (majorVersion == MAJOR_VERSION_MIN) {
				if (minorVersion == MINOR_VERSION_MIN) {
					if (revision >= REVISION_MIN) {
						validVersion = true;
					}
				}
				else if(minorVersion > MINOR_VERSION_MIN){
					validVersion = true;
				}
			}
			else if(majorVersion > MAJOR_VERSION_MIN){
				validVersion = true;
			}
		}
		return validVersion;
	}

//	public boolean shutdown(){
//		boolean success = false;
//		try {
//			this.con.Shutdown();
//			success = true;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return success;
//	}
}
