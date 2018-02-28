package jp.or.med.orca.jma_tokutei.common.frame;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 * 画面用設定ファイル
 */
public class ViewSettingsFile {

	private static final String PROPERTY_FILE_NAME = "view.properties";
	private static Properties properties;

//	static {
//		initialize();
//	}

	/**
	 * 初期化
	 */
	private static boolean initialize(){
		boolean success = false;

		try	{
			FileInputStream fs = new FileInputStream(PROPERTY_FILE_NAME);
			InputStreamReader in = new InputStreamReader(fs, "Shift_JIS");
			properties.load(in);

			/* メッセージファイルのバージョンを検証する。 */
			String fileVersionNumber = getProperty("VersionNumber");

			if (fileVersionNumber.equals(JApplication.versionNumber)) {
				success = true;
			}
			else {
				JErrorMessage.show("M0002", null);
			}
		}
		catch(Exception e)	{
			success = false;
		}

		if (success) {
			success = syntaxCheck();
		}

		return success;
	}

	/**
	 * properties を取得する。
	 */
	private static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			initialize();
		}

		return properties;
	}

	/**
	 * プロパティファイルの文法チェックを行う
	 */
	private static boolean syntaxCheck()	{
		boolean success = false;

		if (properties != null){
			Enumeration<Object> items = properties.keys();

			success = true;
			while(items.hasMoreElements()){

				String keyName = (String)items.nextElement();
				String right = properties.getProperty(keyName);
				String[] values = right.split(",");

				// 配列の要素外参照エラーが起きる可能性あり
				if(values[0].length() <= 0 ){
//					new JErrorMessageDialogFrameCtrl(null,
//							"エラー","画面用設定ファイルの読み込みに失敗しました。ファイルを確認してください。" +
//									"エラーが発生したIDは「" + keyName + "」です。" ,0,0,"M0103");

					success = false;
					break;
				}
			}
		}

		return success;
	}

	/**
	 * <p>画面用設定値を取得する。</p>
	 * @return String 画面用設定値
	 */
	public static String getProperty(String id) {

		String value = (String)getProperties().get(id);

		if(value == null){
			value = "";
		}

		return value;
	}
}
