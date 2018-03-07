package jp.or.med.orca.jma_tokutei.common.app;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

import org.apache.log4j.Logger;

/**
 * ファイルパスの管理クラス
 * 各ソフトウェアで利用する共通パスはここで管理する。
 * 共通で利用しないパスに関してはJApplicationで保持する。
 */
/**
 * @author asc
 *
 */
/**
 * @author asc
 *
 */
public class JPath {
	/**
	 *  フォルダの区切り文字
	 */
//	final public static String Separator =
//		System.getProperties().getProperty("file.separator");

	/**
	 *  データベース拡張子
	 */
	final public static String DATA_BASE_EXTENSION =".fdb";
// del s.inoue 2011/04/12
//	public static final String PATH_FILE_PROPERTIES = "file.properties";
	public static final String CURRENT_DIR_PATH = getCurrnetPath();
	public static final double CONST_FIX_ICON =0.9;
	public static final double CONST_FIX_ICON_SABU =0.8;
	// add s.inoue 2012/07/06
	public static final double CONST_SYSTEM_FIX_ICON =0.6;
	public static final double CONST_KENSHIN_FIX_ICON =0.3;

	private static org.apache.log4j.Logger logger = Logger.getLogger(JPath.class);

	private static String getCurrnetPath(){
		String path = null;

		File currentDir = new File(".");
		try {
			path = currentDir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			JErrorMessage.show("M0003", null);
		}

		return path;
	}

	/**
	 *  Imagesフォルダ
	 */
	final public static String IcoPath = "Images" + JApplication.FILE_SEPARATOR;
	final public static String IcoNayose = IcoPath + "keinenExcute.png";

	final public static String Ico_Common_METABO =IcoPath + "icon.png";
	/* 共通静的 */
	final public static String Ico_Common_Print1 =IcoPath + "common_Print1.png";
	final public static String Ico_Common_Print2 =IcoPath + "common_Print2.png";
	final public static String Ico_Common_Detail =IcoPath + "common_Detail.png";
	final public static String Ico_Common_Select =IcoPath + "common_Select.png";
	final public static String Ico_Common_Decide =IcoPath + "common_Decide.png";
	final public static String Ico_Common_HL7Export =IcoPath + "common_HL7Export.png";
	final public static String Ico_Common_Delete =IcoPath + "del.png";
	final public static String Ico_Common_Reload =IcoPath + "reload.png";

	final public static String Ico_Common_Check =IcoPath + "common_check.png";
	final public static String Ico_Common_Exit =IcoPath + "common_Exit.png";
	final public static String Ico_Common_Back =IcoPath + "common_back.png";
	final public static String Ico_Common_Add =IcoPath + "common_add.png";
//	final public static String Ico_Common_DialogClose = IcoPath + "closewindow.png";
//	final public static String Ico_Common_HelpBook = IcoPath + "common_Help.png";
	// eidt s.inoue 2013/03/12 大文字
	final public static String Ico_Common_Redo = IcoPath + "common_redo.png";
	final public static String Ico_Common_Deplicate = IcoPath + "common_Deplicate.png";
	final public static String Ico_Common_Clear = IcoPath + "common_Clear.png";
	final public static String Ico_Common_Register = IcoPath + "common_Save.png";
//	final public static String Ico_Common_Title1 = IcoPath + "common_help_base02.gif";
	final public static String Ico_Common_Orca= IcoPath + "common_orca.png";
	final public static String Ico_Common_Version= IcoPath + "common_Version.png";

	// add s.inoue 2012/07/04
	final public static String Ico_Common_Pattern= IcoPath + "kekkapattern_AddPattern.png";
	final public static String Ico_Common_Proxy= IcoPath + "proxy.png";
	final public static String Ico_Common_Update= IcoPath + "onlineupdate.png";

	/* ログイン */
	final public static String Ico_Login_Setting=IcoPath + "login_Setting.png";
	final public static String Ico_Login_Login=IcoPath + "common_Login.png";

	/* バックアップ＆復元 */
	final public static String Ico_Common_Import =IcoPath + "common_Imort.png";
	final public static String Ico_Common_Export =IcoPath + "common_Export.png";
	/* 受診券 */
	final public static String Ico_Jusin_Call =IcoPath + "jusinken_call.png";
	final public static String Ico_Jusin_Orca =IcoPath + "jusinken_orca.png";

	final public static String Ico_Seikyu_Calc =IcoPath + "seikyu_calc.png";

	/* メニュー */
	final public static String Ico_System_Kikan =IcoPath + "7-1.png";
	final public static String Ico_System_User =IcoPath + "7-2.png";
	final public static String Ico_System_Backup =IcoPath + "7-3.png";
	final public static String Ico_System_Usability =IcoPath + "7-4.png";
	// add s.inoue 2013/11/07
	final public static String Ico_System_Log =IcoPath + "7-5.png";
	
	// edit n.ohkubo 2015/08/01　追加
	final public static String Ico_System_NetworkDB = IcoPath + "7-6.png";	//システム管理ソフトの4番（DB接続情報メンテナンス）のボタン

	/* 結果入力 */
	final public static String Ico_Kekka_Delete =IcoPath + "kekkaInput_DelKekka.png";
	final public static String Ico_Kekka_DeleteJusinken = IcoPath + "kekkaInput_DelJusinken.png";
	final public static String Ico_Kekka_AddJusinken = IcoPath + "kekkaInput_AddJusinken.png";
	final public static String Ico_Kekka_Copy =IcoPath + "kekkaInput_CopyKekka.png";
	final public static String Ico_Kekka_InputJusinken =IcoPath + "kekkaInput_CallJusinken.png";
	final public static String Ico_Hantei_Graph =IcoPath + "hantei_Graph.png";

	/* メタボ判定 */
	final public static String Ico_Hantei_Detail =IcoPath + "hantei_Metabohantei.png";
	
	// edit n.ohkubo 2015/03/01　追加
	final public static String Ico_Default = IcoPath + "default.png";	//「健診項目マスタメンテナンス　｜　編集」画面の「初期値」ボタン

	/**
	 *  スプラッシュイメージ
	 */
	// edit s.inoue 2010/11/10
	final public static String SplashPath =
		IcoPath + "SplashImage.png";

	/**
	 *  データベースフォルダ
	 */
	final public static String DatabaseFolder =
		"DB" + JApplication.FILE_SEPARATOR;

	/**
	 *  機関情報データベース
	 */
	final public static String BaseKikanDatabaseFilePath =
		DatabaseFolder + "Kikan" + DATA_BASE_EXTENSION;

	/**
	 *  システムデータベース
	 */
	final public static String SystemDatabaseFilePath =
		DatabaseFolder + "System" + DATA_BASE_EXTENSION;

	/**
	 *  データベースバックアップフォルダ
	 */
	final public static String BackupDatabaseFolder =
		"Backup" + JApplication.FILE_SEPARATOR;

	/**
	 *  システムデータベースバックアップフォルダ
	 */
	final public static String BackupSystemDatabaseFolder =
		BackupDatabaseFolder + "System" + JApplication.FILE_SEPARATOR;

	/**
	 *  機関データベースファイルパス
	 */
	final public static String BackupKikanDataBaseFolder =
		BackupDatabaseFolder + "Kikan" + JApplication.FILE_SEPARATOR;

	/* Modified 2008/08/07 若月  */
	/* --------------------------------------------------- */
//	/**
//	 *  一時ディレクトリ
//	 */
//	final public static String TEMP_SYSTEM_DATABASEFILE_PATH =
//		"Work" + JApplication.FILE_SEPARATOR + "Temp" + JApplication.FILE_SEPARATOR;
//
//	/**
//	 * 一時的にHL7を出力するフォルダ
//	 */
//	public final static String TEMP_OUTPUT_HL7_DIRECOTRY_PATH =
//		"Work" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */
	/**
	 *  一時ディレクトリ
	 */
	final public static String TEMP_SYSTEM_DATABASEFILE_PATH =
		"work" + JApplication.FILE_SEPARATOR + "Temp" + JApplication.FILE_SEPARATOR;

	/**
	 * 一時的にHL7を出力するフォルダ
	 */
	public final static String TEMP_OUTPUT_HL7_DIRECOTRY_PATH =
		"work" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */

	/**
	 * HL7のZipファイルの出力フォルダ
	 */
	public final static String ZIP_OUTPUT_DIRECTORY_PATH =
		"Data" + JApplication.FILE_SEPARATOR + "HL7" + JApplication.FILE_SEPARATOR;
	/* --------------------------------------------------- */

	/**
	 * オンラインアップデート用出力フォルダ
	 */
	public static final String MODULE_TEMP = "Module";

	/**
	 *  機関データベース
	 *
	 *    @param 機関番号
	 */
	public static String GetKikanDatabaseFilePath( String number )
	{
		return DatabaseFolder + number + DATA_BASE_EXTENSION;
	}

	/**
	 *  JSql システムデータベースのフルパスを取得する
	 */
	public static String GetSystemDatabaseFilePathToInitJSql()
	{
		String dbDirectory = JApplication.systemDBPath;
		StringBuffer buffer = new StringBuffer();
		buffer.append(dbDirectory);
		buffer.append("System");
		buffer.append(DATA_BASE_EXTENSION);

		String path = buffer.toString();

		return path;
	}

	// add s.inoue 2012/07/04
    public static String GetHokenjyaDatabaseFilePathToInitJSql()
    {
		String dbDirectory = JApplication.systemDBPath;
		StringBuffer buffer = new StringBuffer();
		buffer.append(dbDirectory);
		buffer.append("M_INSURER");
		buffer.append(DATA_BASE_EXTENSION);

		String path = buffer.toString();

		return path;
    }

	/**
	 *  JSql 機関情報データベースのフルパスを取得する
	 *
	 *    @param 機関番号
	 */
	public static String GetKikanDatabaseFilePathToInitJSql( String number )
	{
		/* Modified 2008/07/29 若月  */
		/* --------------------------------------------------- */
//		return JFile.GetCurrentDirectory() + "DB" + JApplication.FILE_SEPARATOR + number + DATA_BASE_EXTENSION;
		/* --------------------------------------------------- */
		String dbDirectory = JApplication.systemDBPath;

		StringBuffer buffer = new StringBuffer();
		buffer.append(dbDirectory);
//		if (! dbDirectory.endsWith(JApplication.FILE_SEPARATOR) ) {
//			buffer.append(JApplication.FILE_SEPARATOR);
//		}
		buffer.append(number);
		buffer.append(DATA_BASE_EXTENSION);

		String path = buffer.toString();

		return path;
	}
	/**
	 *  JSql レジストリより、デスクトップを取得する
	 *
	 *    @param デスクトップパス
	 */
	public static String getDesktopPath() {
	    String ret = "";
	  	try{
	  		// eidt s.inoue 2013/03/09
			// add s.inoue 2013/03/09
			String osname = System.getProperty("os.name");
			if(osname.indexOf("Windows")>=0){
				// デスクトップ
			    File fs= new File(System.getProperty("user.home"), "Desktop");
				ret = fs.getAbsolutePath();

				if(!fs.exists()){
			  		// デスクトップ
				    File ffs= new File(System.getProperty("user.home"), "デスクトップ");
					ret = ffs.getAbsolutePath();
			  		logger.info("use vistaデスクトップ");

			  		// ユーザホーム
			  		if(!ffs.exists()){
				  		File fffs= new File(System.getProperty("user.home"));
				  		ret = fffs.getAbsolutePath();
				  		logger.info("use user.home");
			  		}
				}
			}else{
				// Linux等対応
				File fffs= new File(System.getProperty("user.home"));
		  		ret = fffs.getAbsolutePath();
		  		logger.info("use user.home");
			}
	  	} catch (Exception e) {
	  		// ユーザホーム
	  		File fs= new File(System.getProperty("user.home"));
	  		ret = fs.getAbsolutePath();
	  		logger.info("use user.home");
	  	}

//	    try {
//	      String osname = System.getProperty("os.name");
//	      // "Linux","Mac"
//	      if(osname.indexOf("Windows")>=0){
//	    	  // Windowsであったときの処理
//	    	  File fs= new File(System.getProperty("user.home"), "Desktop");
//	    	  ret = fs.getAbsolutePath();
//
////	    	} else if(osname.indexOf("Linux")>=0){
////	    	  // Linuxであったときの処理
////	    	  System.getProperty(“user.home”)
////	    	} else if(osname.indexOf("Mac")>=0){
////	    	  // MacOSであったときの処理
//	    	} else {
//	    		// その他の環境だったときの処理
//	    		ret = System.getProperty("user.home");
//	    	}
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }

	    return ret;
	}

	// add s.inoue 2010/03/09
	/**
	 * 出力用のフォルダ名を生成する(_1-10)
	 * @return エラーコード
	 * ret = 0 -> 正常終了
	 * ret = 1 -> フォルダの作成に失敗
	 * ret = 2 -> フォルダ作成の上限に達した
	 * ret = 4 -> スキーマのファイルコピーに失敗
	 */
	public static String createDirectoryUniqueName(String uniqueName)
	{
		String retFileName = "";

		// 変換日時
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String CreateDate = format.format(new Date());

		int count;

		for(count = 1 ; count < 10000 ; count++)
		{
			retFileName = uniqueName + CreateDate + "_" + String.valueOf(count) + ".csv";

			File newFile = new File(getDesktopPath()+ File.separator + retFileName);

			if(newFile.exists() == false)
				break;
		}

		return retFileName;
	}
//	      String cmdline = "";
//	      osname = "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v Desktop";
//
//	      String line = "";
//
//	      Process pc = Runtime.getRuntime().exec(cmdline);
//	      BufferedReader br = new BufferedReader(new InputStreamReader(pc.getInputStream()));
//
//	      while ((line = br.readLine()) != null) {
//	        if (line.indexOf("Desktop") != -1) {
//	          ret = line.substring(line.indexOf("C"), line.length());
//	        }
//	      }
//	      br.close();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    return ret;
//	  }
}
