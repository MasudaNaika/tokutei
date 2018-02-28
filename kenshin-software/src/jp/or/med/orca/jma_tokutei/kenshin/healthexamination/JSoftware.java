package jp.or.med.orca.jma_tokutei.kenshin.healthexamination;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.frame.JSplashFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.SettingDialog;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JLoginFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JLoginFrameCtrl;

/**
 * 特定健診ソフトウエア
 *
 * Modified 2008/03/09 若月
 * 　二重起動防止機能変更対応。
 *
 * 　・１台の端末で同じ健診機関の健診機関用システムを起動するのを防止する。
 * 　・１台の端末でシステム管理者用システムと健診機関用システムを起動する
 * 　　のを防止する。
 * 　・１台の端末でシステム管理者用システムを複数起動するのを防止する。
 * 　・１台の端末で別々の健診機関用システムを起動するのを許可する。
 *
 * 機関番号はユーザの入力値から取得するため、処理を JApplication クラスへ移動した。
 */
public class JSoftware{
//	static {
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Throwable tw) { }
//	}

	private  static JSplashFrameCtrl splashFrame = null;

	public static JSplashFrameCtrl getSplashFrame(){
		// add s.inoue 20090113 reload処理
		return splashFrame;
	}

	public static void main(String[] args) {
		initialize(args);
	}

	public static void reloadSplashFlame(){
		// add s.inoue 20090113 reload処理
		loadVersions(true);
	}

	private static void initialize(String[] args){

		try {
			/* エラーメッセージ読込 */
			String messageVersion = JErrorMessage.load();
			if (messageVersion == null || messageVersion.isEmpty()) {
				System.exit(1);
			}

			if (args.length >= 1) {
				// rds test switch
				JApplication.runtimeTest(args[0]);
			}

			// バージョン情報の読み込み
			JApplication.loadVersionFile("MainVersion.properties");

			// メッセージファイルのバージョンを検証する。
			if (! messageVersion.equals(JApplication.versionNumber)) {
				JErrorMessage.show("M0001", null);
			}

			// move s.inoue 2013/03/12 先に行う
			// add s.inoue 2009/12/18
			String lookAndFeel = getAppSettings();
			if (!lookAndFeel.equals("")){
				SettingDialog sd = new SettingDialog();
				sd.changeTheLookAndFeel(lookAndFeel,true);
			}

			// 設定ファイルロード
			JApplication.load();

			// アップデートソフトウェア呼出
// 20081111 add s.inoue
//
//			// s.inoue 20080821
//			// FDBへの接続を閉じる必要。共通で使用している為、
//			// オンラインアップデート処理ではクローズして処理を行う。
//			JApplication.systemDatabase.Shutdown();
//
//			JMainFrame.executeApplicationTaskXML();
//
//			// FDBへの接続を閉じる必要。共通で使用している為、
//			// オンラインアップデート処理終了後オープンする。
//			JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
//			// 更新処理後に再ロード
//			JApplication.loadVersionFile("MainVersion.properties");

			// add s.inoue 20090113
			loadVersions(false);
		} catch (Exception e) {
			System.exit(1);
		}

		// 機関データベースが登録されているか確認
		if (JConnection.IsExistKikanDatabase() == false) {
			JErrorMessage.show("M1001", null);
			System.exit(1);
		}

		JScene.ChangeScene(new JLoginFrameCtrl());
	}

	// add s.inoue 2009/12/18
	private static String getAppSettings(){
		return PropertyUtil.getProperty( "setting.lookAndFeel");
	}

	public static void loadVersions(boolean flgReload){

		// edit s.inoue 20090113
		String strSchema = "";
		String strDBData = "";

		if (!JApplication.SchemaVersion.equals(""))
		{
			strSchema = "      Schema " + JApplication.SchemaVersion;
		}
		if (!JApplication.DBDataVersion.equals(""))
		{
			strDBData = "      DBData " + JApplication.DBDataVersion;
		}
		// 20090113 s.inoue reload用
		if (flgReload){
			splashFrame = null;
		}
		// スプラッシュ画面表示
		splashFrame = new JSplashFrameCtrl(
				"MainVersion " + JApplication.versionNumber,
				"        Module " + JApplication.Moduleversion,
				strSchema,
				strDBData,
				//"      Schema " + JApplication.SchemaVersion,
				//"       DBData " + JApplication.DBDataVersion,
				JPath.SplashPath,flgReload) {

			public void loadingProc() {
//				// 設定ファイルロード
//				JApplication.load();
			}
		};
	}
}



// Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

