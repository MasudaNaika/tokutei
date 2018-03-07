package jp.or.med.orca.jma_tokutei.kenshin.admin;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.frame.JSplashFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.admin.frame.JLoginFrameCtrl;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;

public class JAdminSoftware {

	private static JSplashFrameCtrl splashFrame = null;

	public static JSplashFrameCtrl getSplashFrame() {
		return splashFrame;
	}

	public static void main(String[] args) {
		/* エラーメッセージ読込 */
		String messageVersion = JErrorMessage.load();
		if (messageVersion == null || messageVersion.isEmpty()) {
			System.exit(1);
		}

		JApplication.loadVersionFile("SystemVersion.properties");

		try {

			JApplication.load();

			// yoshida 20090105 s.inoue
			JApplication.systemDatabase.Shutdown();

	        ShcDBAdjust adjuster = new ShcDBAdjust();
	        adjuster.call("System");    //System.fdbを対象
	        adjuster.call("Kikan");     //Kikan.fdbを対象

	        JApplication.systemDatabase = JConnection.ConnectSystemDatabase();
	        JApplication.loadDBVersions(JApplication.systemDatabase);

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

			splashFrame = new JSplashFrameCtrl("MainVersion "
					+ JApplication.versionNumber,
					"        Module " + JApplication.Moduleversion,
					strSchema,
					strDBData,
					JPath.SplashPath,false
					) {
				public void loadingProc() {
				}
			};
		} catch (Exception e) {
			return;
		}

		// ログイン画面に遷移
		JScene.ChangeScene(new JLoginFrameCtrl());
	}
}

// Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}
