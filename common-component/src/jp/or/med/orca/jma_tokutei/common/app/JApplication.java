package jp.or.med.orca.jma_tokutei.common.app;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessageDialogFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.orca.JORCASetting;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.XMLDocumentUtil;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import org.apache.log4j.PropertyConfigurator;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.util.client.ClientSettings;

/**
 * グローバル情報の保持を行う
 */
public class JApplication {

	/**
	 * インスタンスを生成できないようにする
	 */
	protected JApplication() {
	}

	// ソフトウェア全体に係わる情報
	public static int authority;
	public final static int authorityAdmin = 1;
	public final static int authorityUser = 2;
	public static String systemDBUserName;
	public static String systemDBPassword;
	public static String systemDBCharSet;
	public static String systemDBPath;
	public static String systemDBLoginTimeOut;
	public static String systemDBServer;
	public static String systemDBMaxPoolSize;
	public static String systemDBPort;
	public static String userID;
	public static String password;
	public static String kikanNumber;
	public static int debugCode = 0;
	public static String kikanName;

	// データベース
	public static JConnection systemDatabase = null;
	public static JConnection kikanDatabase = null;
	public static JConnection hokenjyaDatabase = null;

	// データベース周りの設定
	public static boolean useORCA = false;
	public static JORCASetting orcaSetting = null;
	public static String versionNumber;
	public static String Moduleversion;
	public static String SchemaVersion;
	public static String DBDataVersion;

	// t_version rowid
	public static HashMap<String, String>kikanSchemaVersionRowIds;
	public static String kikanSchemaVersionRowId= null;
	public static String kikanDataVersionRowId= null;
	public static String systemSchemaVersionRowId= null;
	public static String systemDataVersionRowId= null;
	public static String moduleDataVersionRowId= null;
	// 初期flg
	public static boolean kikanVersionFlg= false;
	public static boolean MultiKikanVersionFlg= false;
	public static boolean systemVersionFlg= false;
	public static boolean moduleVersionFlg= false;

	public static String SCHEMA_VERSION = "SCHEMA_VERSION";
	public static String DB_VERSION = "DB_VERSION";
	public static String MODULE_VERSION = "MODULE_VERSION";

	// 初期フラグ
	public static boolean initTVersionInsFlg =true;

	// 定数項目
	public static String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	public static String KAISOUHANTEI_CSV_PATH = "." + FILE_SEPARATOR + "CSV" + FILE_SEPARATOR + "KAISOU_HANTEI.csv";
	public static String CHECK_CSV_PATH = "." + FILE_SEPARATOR + "CSV" + FILE_SEPARATOR + "CHECK.csv";

	/* 改行文字 */
	public static String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");
	public static int PROGRESS_CNT= 0;
	// DBカラム(検査項目情報)
	public static final String COLUMN_NAME_UKETUKE_ID = "UKETUKE_ID";
	public static final String COLUMN_NAME_KOUMOKUCD = "KOUMOKU_CD";
	public static final String COLUMN_NAME_KOUMOKUNAME = "KOUMOKU_NAME";
	public static final String COLUMN_NAME_KENSA_NENGAPI = "KENSA_NENGAPI";
	//public static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
	//public static final String COLUMN_NAME_IJYOTI_KBN = "IJYOTI_KBN";
	public static final String COLUMN_NAME_IJYOTI_KBN = "IJYO_TI";
	public static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
	public static final String COLUMN_NAME_TANI = "TANI";
	public static final String COLUMN_NAME_HYOUJI_KIJYUNTI = "HYOUJI_KIJYUNTI";
	public static final String COLUMN_NAME_KANANAME = "KANANAME";
	public static final String COLUMN_NAME_BIRTHDAY = "BIRTHDAY";
	public static final String COLUMN_NAME_SEX = "SEX";
	// add s.inoue 2010/01/26
	// 日医フォーマット
	public static final Integer CSV_NITII_ZOKUSEI_JISIKIKAN_NO = 0;
	public static final Integer CSV_NITII_ZOKUSEI_JISI_DATE = 2;
	public static final Integer CSV_NITII_ZOKUSEI_JUSIN_SEIRI_NO = 125;
	public static final Integer CSV_NITII_ZOKUSEI_SHIMEI = 5;
	public static final Integer CSV_NITII_ZOKUSEI_SEINENGAPI = 3;
	public static final Integer CSV_NITII_ZOKUSEI_SEIBETU = 4;
	// CSVフォーマット
	public static final Integer CSV_ZOKUSEI_KENSA_KIKAN = 0;
	public static final Integer CSV_ZOKUSEI_JISIKIKAN_NO = 1;
	public static final Integer CSV_ZOKUSEI_JISIKIKAN = 2;
	public static final Integer CSV_ZOKUSEI_KENSA_UKETUKE_DATE = 3;
	public static final Integer CSV_ZOKUSEI_KENSA_UKETUKE_NO = 4;
	public static final Integer CSV_ZOKUSEI_JISI_DATE = 5;
	public static final Integer CSV_ZOKUSEI_JUSIN_UKETUKE_NO = 6;
	public static final Integer CSV_ZOKUSEI_JUSIN_SEIRI_NO = 7;
	public static final Integer CSV_ZOKUSEI_SHIMEI = 8;
	public static final Integer CSV_ZOKUSEI_SEINENGAPI = 9;
	public static final Integer CSV_ZOKUSEI_SEIBETU = 10;
	public static final Integer CSV_ZOKUSEI_NYUBI_YOUKETU = 11;
	public static final Integer CSV_ZOKUSEI_SHOKUZEN_SHOKUGO = 12;
	public static final Integer CSV_KENSA_KOUMOKU_CD = 13;
	public static final Integer CSV_KENSA_KOUMOKU = 14;
	public static final Integer CSV_KENSA_JISI_KBN = 15;
	public static final Integer CSV_KENSA_IJYO_KBN = 16;
	public static final Integer CSV_KENSA_KEKATI_KEITAI = 17;
	public static final Integer CSV_KENSA_KEKATI = 18;
	public static final Integer CSV_KENSA_TANI = 19;
	public static final Integer CSV_KENSA_KIJUN = 20;
	// 検査項目情報:1～22,検査項目個数:8
	// eidt s.inoue 2013/02/26
	// public static final Integer CSV_KENSA_LOOP = 22;
	public static final Integer CSV_KENSA_LOOP = 280;
	public static final Integer CSV_KENSA_KOUMOKU_LOOP = 8;

	public static final Integer CSV_ZOKUSEI_COUNT = 12;

	// エラーfield
	public static final String ZOKUSEI_FIELD_JISI_DATE = "健診実施年月日";
	public static final String ZOKUSEI_FIELD_JISI_KIKAN_NO = "健診実施機関番号";
	public static final String ZOKUSEI_FIELD_JUSIN_SEIRI_NO = "受診券整理番号";
	public static final String ZOKUSEI_FIELD_SHIMEI = "氏名";
	public static final String ZOKUSEI_FIELD_SEINENGAPI = "生年月日";
	public static final String ZOKUSEI_FIELD_SEIBETU = "性別";
	public static final String ZOKUSEI_FIELD_YOUKETU = "乳び・溶血";
	public static final String ZOKUSEI_FIELD_SHOKUZENGO = "食前/食後";

	public static final String KENSA_FIELD_KOUMOKU_CD = "検査項目コード";
	public static final String KENSA_FIELD_JISI_KBN = "実施区分";
	//public static final String KENSA_FIELD_IJYO_KBN = "異常値区分";
	//public static final String KENSA_FIELD_KEKATI_KEITAI = "結果値形態";
	public static final String KENSA_FIELD_KEKATI = "検査結果";
	// エラー種別
	public static final Integer ERR_KIND_NUMBER = 0;
	public static final Integer ERR_KIND_DATE = 1;
	public static final Integer ERR_KIND_DIGIT = 2;
	public static final Integer ERR_KIND_NAME = 3;
	public static final Integer ERR_KIND_BIRTHDAY = 4;
	public static final Integer ERR_KIND_SEX = 5;
	public static final Integer ERR_KIND_NOT_EXIST = 6;
	public static final Integer ERR_KIND_KEY_EXIST = 7;
	public static final Integer ERR_KIND_KEY_DOUBLE = 8;
	public static final Integer ERR_KIND_HALF = 9;
	public static final Integer ERR_KIND_EMPTY = 10;
	public static final Integer ERR_KIND_TYPE = 11;
	public static final Integer ERR_KIND_DATA = 12;
	public static final String CSV_CHARSET ="SHIFT-JIS";

	public static final String DB_SCHEMA_VERSION = "SCHEMA_VERSION";
	public static final String DB_DATA_VERSION = "DB_VERSION";

	public static final String[] jishiKBNTable = { "0:未実施", "1:実施","2:測定不可能" };

	// 基本的な健診,詳細な健診,追加健診項目,人間ドック
	public static final String SEIKYU_KBN_BASE = "1";
	public static final String SEIKYU_KBN_SYOSAI = "2";
	public static final String SEIKYU_KBN_TUIKA = "3";
	public static final String SEIKYU_KBN_SYOSAI_TUIKA = "4";
	public static final String SEIKYU_KBN_DOC = "5";
	public static final String HISU_FLG_BASE = "1";
	public static final String HISU_FLG_SYOSAI = "2";
	public static final String HISU_FLG_TUIKA = "3";
	public static final String HISU_FLG_MONSHIN = "4";
	//	private static String VERSION_FILE = "MainVersion.properties";
	private static final String DB_FILENAME_SYSTEM_FDB = "System.fdb";

	// add s.inoue 2010/03/18
	// 画面リスト対応コード一覧
	public static final String SCREEN_JYUSINKEN_CODE = "001";     // 受診券入力画面
	public static final String SCREEN_KEKKATOUROKU_CODE = "002";  // 健診結果データ入力
	public static final String SCREEN_KEKKATORIKOMI_CODE = "003"; // 検査データ取り込み
	public static final String SCREEN_HANTEI_CODE = "004"; // メタボ判定・階層化
	public static final String SCREEN_SHOSAI_CODE = "005"; // 詳細表示
	public static final String SCREEN_NITIJI_CODE = "006"; // 日時処理(請求)
	public static final String SCREEN_GETUJI_CODE = "007"; // 月時処理(請求確定/HL7出力)

	public static final String SCREEN_SEIKYU_EDIT_CODE = "008";    // 請求データ編集
	public static final String SCREEN_KIKAN_DBBACKUP_CODE = "009"; // 機関ＤＢバックアップ
	public static final String SCREEN_LOGIN_CODE = "010"; 		  // ログイン
	public static final String SCREEN_NYURYOKUHYO_CODE = "011";   // 入力票
	public static final String SCREEN_KEKKATUTIHYO_CODE = "012";  // 結果通知表
	public static final String SCREEN_ONLINE_UPDATE_CODE = "013"; // オンラインアップデート

	public static final String SCREEN_MASTER_KIKAN_CODE = "101"; // 機関情報メンテナンス
	public static final String SCREEN_MASTER_KENSHIN_CODE = "102"; // 健診項目マスタメンテナンス
	public static final String SCREEN_MASTER_PATTERN_CODE = "103"; // 健診パターンメンテナンス
	public static final String SCREEN_MASTER_SHIHARAI_CODE = "104"; // 支払代行機関マスタメンテ
	public static final String SCREEN_MASTER_HOKENJYA_CODE = "105"; // 保険者情報マスタメンテナンス
	public static final String SCREEN_MASTER_USER_CODE = "106"; // システム管理ユーザ情報メンテナンス
	public static final String SCREEN_MASTER_SYSTEM_CODE = "107"; // システム利用者メンテナンス
	public static final String SCREEN_MASTER_KEINEN_CODE = "108"; // 経年マスタメンテナンス
	public static final String SCREEN_MASTER_USEBILITY_CODE = "109"; // ユーザビリティメンテナンス

	// add s.inoue 2011/05/10
	// 検索系一覧カウント
	public static int gridViewSearchCount = 0;
	public static int gridViewMasterCount = 0;

    public static String func_keiyakuCode = "01";
    public static String func_futanCode = "02";
    public static String func_nyuryokuCode = "03";
    public static String func_yearOldCode = "05";
    public static String func_printCode = "06";
    public static String func_orijinCode = "04";
    public static Color backColor_Focus = new Color(195, 229, 254);
    public static Color backColor_UnFocus = new Color(255, 255, 255);
    // eidt s.inoue 2012/11/16
    public static ClientSettings clientSettings = null;
    public static Domain patternDomain = null;
    public static Domain graphDomain = null;
    // add s.inoue 2012/10/24
    public static Domain hokenjaDomain = null;
    public static Domain shiharaiDomain = null;

    public static Hashtable domains = null;

//     add s.inoue 2013/03/12
//    public static boolean blnLookFeelMetal = false;

    // add s.inoue 2013/03/12
    public static Font FONT_COMMON_BUTTON = null;
    public static Font FONT_COMMON_MENU_BUTTON = null;

    // eidt s.inoue 2012/11/28
//    public static ArrayList<Integer> selectedHistoryRows = null;
    public static ArrayList<Integer> selectedHistoryRows = new ArrayList<Integer>();

    // add s.inoue 2012/11/29
    public static ArrayList<Integer> selectedPreservRows = new ArrayList<Integer>();

    public static boolean callValidateCancelFlg = false;
    public static boolean firstCheckedFlg =false;
	/**
	 * 設定ファイルなどを読み込むa。
	 */
	public static void load() {

		/* property.xml から DB 設定を取得する。 */
		try {

			// eidt s.inoue 2013/03/12
//			FONT_COMMON_BUTTON = new Font("ＭＳ ゴシック", 0, blnLookFeelMetal?10:12);
//			FONT_COMMON_MENU_BUTTON = new Font("ＭＳ ゴシック", 0, blnLookFeelMetal?11:13);

			// add ver2 s.inoue 2009/05/19
			PropertyConfigurator.configure("log4j.properties");

			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));

			systemDBUserName = doc.getNodeValue("DBConfig", "UserName");
			systemDBPassword = doc.getNodeValue("DBConfig", "Password");
			systemDBCharSet = doc.getNodeValue("DBConfig", "CharSet");

			/*
			 * AbsolutePath から絶対パスを取得する。
			 * 取得できなければ、Path から相対パスを取得する。
			 */
			String path = doc.getNodeValue("DBConfig", "AbsolutePath");

			if (path == null ||  path.isEmpty()) {

				path = doc.getNodeValue("DBConfig", "Path");

				/* 設定がない場合は、デフォルトの場所（カレントディレクトリ/DB）を使用する。 */
				if (path == null || path.isEmpty()) {

					StringBuffer buffer = new StringBuffer();
					buffer.append(JPath.CURRENT_DIR_PATH);

					if (! buffer.toString().endsWith( JApplication.FILE_SEPARATOR)) {
						buffer.append(JApplication.FILE_SEPARATOR);
					}
					buffer.append("DB");
					buffer.append(JApplication.FILE_SEPARATOR);

					path = buffer.toString();
				}
				/* 設定がある場合は、カレントディレクトリからの相対パスとする。 */
				else {
					File relativePath = new File(path);

					StringBuffer buffer = new StringBuffer();
					String canonicalPath = relativePath.getCanonicalPath();
					buffer.append(canonicalPath);

					if (! canonicalPath.endsWith(JApplication.FILE_SEPARATOR)) {
						buffer.append(JApplication.FILE_SEPARATOR);
					}

					path = buffer.toString();
				}
			}

			systemDBPath = path;
			systemDBLoginTimeOut = doc.getNodeValue("DBConfig", "LoginTimeOut");
			systemDBServer = doc.getNodeValue("DBConfig", "Server");
			systemDBMaxPoolSize = doc.getNodeValue("DBConfig", "MaxPoolSize");
			systemDBPort = doc.getNodeValue("DBConfig", "Port");

		} catch (Exception e1) {
			e1.printStackTrace();
			JErrorMessage.show("M0104", null);
			System.exit(1);
		}

		// システムデータベースへ接続
		try {
			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			JErrorMessage.show("M1000", null);
			System.exit(1);
		}
	}

	/**
	 * デバッグ用ランタイムスイッチ (動作内容の変更) 開発時用
	 */
	public static void runtimeTest(String debugString) {
		final String debugData = "NagcmWT_`Z```R_]Ppbf_oh`\\bUZ`XVg`V^T[jd\\qd`uZ["; // 制御フラグ
		int i, code = 0;
		String dst = "";
		for (i = 0; i < debugData.length(); i++)
			dst += (char) (debugData.charAt(i) + debugString.charAt(i) - 'a' + 1);
		for (i = 0; i < dst.length(); i++)
			code += (int) (dst.charAt(i) * dst.charAt(i) * (i + 1) + dst
					.charAt(i));
		debugCode = code;
		if (code == 0xC29F36) {
			new JErrorMessageDialogFrameCtrl(null, "TokuteiKenshin", dst
					.toUpperCase()
					+ "  ", 0, 0, "");
		}
	}

	/**
	 * ORCAのデータをロードする。 ログイン後にロードすること。
	 */
	public static void loadORCAData() {
		String sUseORCA = null;
		try {
			String query = "SELECT NITI_RESE FROM T_F_KIKAN WHERE TKIKAN_NO = "
										+ JQueryConvert.queryConvert(kikanNumber);

			ArrayList<Hashtable<String, String>> result = systemDatabase.sendExecuteQuery(query);
			if (result != null && result.size() > 0) {
				Hashtable<String, String> resultItem = result.get(0);

				if (resultItem != null && resultItem.size() > 0) {
					sUseORCA = resultItem.get("NITI_RESE");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			useORCA = false;
			return;
		}

		if (sUseORCA != null) {
			if (sUseORCA.equals("1")) {
				useORCA = true;
			} else {
				useORCA = false;
			}
		}
		else {
			useORCA = false;
		}

		// ORCAのデータを読み込む
		if (useORCA == true) {
			JORCASetting orca = new JORCASetting(kikanNumber);
			if (orca.Load() == true) {
				orcaSetting = orca;
			} else {
				// ORCAの読込みに失敗したら、ORCAを使用できないようにする。
				useORCA = false;
			}
		}
	}

	/**
	 * バージョン情報を読み込む。 ログイン前にロードすること。
	 */
	public static void loadVersionFile(String versionFile) {
		// バージョンファイル
		Properties PropVersion = new Properties();
		try {
			PropVersion.load(new FileInputStream(versionFile));

			versionNumber = PropVersion.getProperty("VersionNumber");
			Moduleversion = PropVersion.getProperty("ModuleVersion");
			SchemaVersion = "";
			DBDataVersion = "";

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * バージョン情報を読み込む。 ログイン前にロードすること。
	 */
	public static void checkVersionFile(String versionFile) {
		// バージョンファイル
		Properties PropVersion = new Properties();
		try {
			PropVersion.load(new FileInputStream(versionFile));
			Moduleversion = PropVersion.getProperty("ModuleVersion");


		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 健診ソフト、管理用ソフト起動時、バージョン情報を書き込み。
	 */
	public static void setVersionFile(String versionFile,String updTskSchemaVersion,String updTskDBDataVersion) {
		// バージョンファイル
		Properties PropVersion = new Properties();
		try {

			PropVersion.load(new FileInputStream(versionFile));

			if (updTskSchemaVersion != null){
				PropVersion.setProperty("SchemaVersion", updTskSchemaVersion);
			}
			if (updTskDBDataVersion != null){
				PropVersion.setProperty("DBDataVersion", updTskDBDataVersion);
			}
			PropVersion.store( new FileOutputStream(versionFile), null );

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @return versionNumber
	 */
	public static String getVersionNumber() {
		return versionNumber;
	}

	/* edit 2009/01/13 井上  */
	/**
	 * SCHEMA_VERSION,DB_VERSIONをT_VERSIONより取得する
	 * @throws SQLException
	 * @return ROW_ID
	 * @throws Exception
	 */
	public static boolean loadDBVersions(JConnection dbcon) throws Exception {
		String verScv = null;
		String verDbv = null;
		String tVersionRowId = null;

		boolean flgRead = false;

		try {

			// t_version存在
			String exQuery = "SELECT RDB$RELATION_NAME AS TableName FROM RDB$RELATIONS WHERE RDB$RELATION_NAME ='T_VERSION' " +
			   				 " ORDER BY RDB$RELATION_NAME ";
			try{
				ArrayList<Hashtable<String, String>> result =
					dbcon.sendExecuteQuery(exQuery);

				if (result != null && result.size() > 0) {
					flgRead = true;
				}

			}catch(Exception err){
				throw err;
			}

			if (flgRead){
				// t_version読込
				StringBuffer buffer = new StringBuffer();

				buffer.append("select ROW_ID,SCHEMA_VERSION,DB_VERSION ");
				buffer.append(" from T_VERSION ");
				buffer.append(" where ROW_ID = (select MAX(ROW_ID) from T_VERSION) ");

				try{
					ArrayList<Hashtable<String, String>> result =
						dbcon.sendExecuteQuery(buffer.toString());

					if (result != null && result.size() > 0) {
						Hashtable<String, String> resultItem = result.get(0);

						if (resultItem != null && resultItem.size() > 0) {
							verScv = resultItem.get("SCHEMA_VERSION");
							verDbv = resultItem.get("DB_VERSION");
							if (resultItem.get("ROW_ID") != null)
							{
								tVersionRowId = resultItem.get("ROW_ID");
							}

							JApplication.SchemaVersion=verScv;
							JApplication.DBDataVersion=verDbv;
						}
					}
				}catch(SQLException err){
					throw err;
				}
			}
		} catch (Exception err) {
			throw err;
		}

		return true;
	}


	/**
	 * 機関DB(T_F_KIKAN)をリスト化する
	 * @param Database 接続先のデータベース
	 * @param Query クエリ
	 * @return 存在すればtrue
	 * @throws Exception
	 */
	public static ArrayList<Hashtable<String, String>> KikanDatabaseItem(JConnection con)
		throws Exception {

		ArrayList<Hashtable<String, String>> arrTK =
			 new  ArrayList<Hashtable<String, String>>();

		// ディレクトリ検索
		File directory = new File(JApplication.systemDBPath);
		File[] DBlist = directory.listFiles();

		try{
			for (int n = 0; n < DBlist.length; ++n) {

				File dbFile = DBlist[n];
				String fileName = dbFile.getName();

				if (dbFile.isFile() && fileName.equals(DB_FILENAME_SYSTEM_FDB)) {
					arrTK = con.sendExecuteQuery("select TKIKAN_NO from T_F_KIKAN ");
				}
			}
		}catch( Exception err ){
			throw err;
		}finally{
			con.Shutdown();
			con = null;
		}
		return arrTK;
	}

	/**
	 * 機関DB(T_F_KIKAN)へT_Versionにデータ登録
	 * @param Database 接続先のデータベース
	 * @return 存在すればtrue
	 * @throws Exception
	 */
	public static boolean tVersionRegister(JConnection con,
			String strVersionRowID,
			String strColumnver,
			String strVersion)

		throws Exception {

		StringBuffer buffer = new StringBuffer();

		// version取得
		String verScv = JApplication.SchemaVersion;
		String verDbv = JApplication.DBDataVersion;

		try
		{
			con.Transaction();
			// timestamp取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			if (strVersionRowID.equals("0"))
			{
				buffer.append("INSERT INTO T_VERSION (ROW_ID");
				buffer.append(",SCHEMA_VERSION");
				buffer.append(",DB_VERSION");
				buffer.append(",UPDATE_TIMESTAMP ");
				buffer.append(") ");
				buffer.append("SELECT case when max(row_id) is null then 1 else MAX(ROW_ID)+1 end, ");

				if (strColumnver.equals(JApplication.SCHEMA_VERSION)){
					buffer.append(JQueryConvert.queryConvertAppendComma(strVersion));
				}else{
					buffer.append(JQueryConvert.queryConvertAppendComma(verScv));
				}
				if (strColumnver.equals(JApplication.DB_DATA_VERSION)){
					buffer.append(JQueryConvert.queryConvertAppendComma(strVersion));
				}else{
					buffer.append(JQueryConvert.queryConvertAppendComma(verDbv));
				}
				buffer.append(JQueryConvert.queryConvert(stringTimeStamp));

				buffer.append(" FROM T_VERSION");

			}else{

				buffer.append("UPDATE T_VERSION ");
				buffer.append("SET ");
				if (strColumnver.equals(JApplication.SCHEMA_VERSION)){
					buffer.append("SCHEMA_VERSION = ");
					buffer.append(JQueryConvert.queryConvert(strVersion));
				}
				if (strColumnver.equals(JApplication.DB_DATA_VERSION)){
					buffer.append("DB_VERSION = ");
					buffer.append(JQueryConvert.queryConvert(strVersion));
				}

				buffer.append(" WHERE ROW_ID = ");
				buffer.append(strVersionRowID);

			}
			con.sendNoResultQuery(buffer.toString());

			con.Commit();
			System.out.println("T_Version regist" + "Sc:" + verScv + " "+ "Data:" + verDbv);

		} catch (Exception err) {
			con.rollback();
			err.printStackTrace();
			JErrorMessage.show("M9602", null);

			try {
				con.Shutdown();
			} catch (SQLException er) {
				er.printStackTrace();
				JErrorMessage.show("M0000", null);
				System.exit(1);
			}
		}
		return true;
	}

	public static void exit(int returnValue){
		System.out.println("アプリケーション終了");
		System.out.printf("終了コード[%d]", returnValue);
		System.exit(returnValue);
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

