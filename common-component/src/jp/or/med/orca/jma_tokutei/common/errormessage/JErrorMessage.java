package jp.or.med.orca.jma_tokutei.common.errormessage;

import java.util.*;
import java.io.*;
import javax.swing.JFrame;

public class JErrorMessage {

	private static final String KEY_VERSION_NUMBER = "VersionNumber";

	private JErrorMessage() {
	}

	private static Properties mProperties;

	private static String LoadFile = "ErrorMessage.properties";

	/**
	 * メッセージファイルを読み込む
	 */
	public static String load() {
		Properties prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(LoadFile);
			InputStreamReader in = new InputStreamReader(fs, "SJIS");
			prop.load(in);
			mProperties = prop;

		} catch (Exception e) {
			new JErrorMessageDialogFrameCtrl(null, "エラー",
					"エラーメッセージ定義ファイルのロードに失敗しました。ファイルを確認してください。", 0, 0, "M0100");
			System.exit(1);
		}

		/* 各行のシンタックスを検証する。 */
		boolean success = syntaxCheck();

		/* バージョン番号 */
		String versionNumuber = null;
		if (success) {
			/* メッセージファイルのバージョンを取得る。 */
			versionNumuber = prop.getProperty(KEY_VERSION_NUMBER);
		}

		return versionNumuber;
	}

	/**
	 * メッセージ定義ファイルの文法チェックを行う
	 */
	private static boolean syntaxCheck() {
		boolean validated = true;

		Enumeration<Object> items = mProperties.keys();

		while (items.hasMoreElements() == true) {
			String keyName = (String) items.nextElement();
			String right = mProperties.getProperty(keyName);

			if (keyName.equals(KEY_VERSION_NUMBER)) {
				continue;
			}

			String[] values = right.split(",");

			try {
				// 配列の要素外参照エラーが起きる可能性あり
				if (values[0].length() <= 0 || values[1].length() <= 0
						|| values[2].length() <= 0 || values[3].length() <= 0) {
					throw new RuntimeException();
				}

				// 要素の値チェック
				// edit s.inoue 2010/02/09
				// 要素数追加 3⇒4
				if (Integer.valueOf(values[2]) < 0
						|| 4 < Integer.valueOf(values[2])) {
					throw new RuntimeException();
				}
			} catch (Exception e) {
				validated = false;
				new JErrorMessageDialogFrameCtrl(null, "エラー",
						"メッセージの解析に失敗しました。" + "エラーが発生した箇所のメッセージIDは「" + keyName
								+ "」です。", 0, 0, "M0101");

				e.printStackTrace();
				break;
				// System.exit(1);
			}
		}

		return validated;
	}

	// add s.inoue 2010/10/27
	public static String getMessageValue(String ID){
		String retErrMessage = "";
		try{
			String right = (String) mProperties.get(ID);
			if (right == null) {
				throw new RuntimeException();
			}
			String[] values = right.split(",");
			retErrMessage = values[1];
		}catch(Exception ex){
			new JErrorMessageDialogFrameCtrl(null, "エラー",
					"メッセージの解析に失敗しました。" + "エラーが発生した箇所のメッセージIDは「" + ID
							+ "」です。", 0, 0, "M0101");

			ex.printStackTrace();
		}
		return retErrMessage;
	}
	/**
	 * ダイアログの表示
	 *
	 * @param ID
	 *            エラーID
	 * @param ParentFrame
	 *            親フレーム
	 * @return 押したボタンの値
	 */
	public static RETURN_VALUE show(String ID, JFrame ParentFrame) {
		String right = (String) mProperties.get(ID);
		if (right == null) {
			new JErrorMessageDialogFrameCtrl(ParentFrame, "エラー",
					"メッセージの解析に失敗しました。", 0, 0, "M0102");
			throw new RuntimeException();
		}

		String[] values = right.split(",");

		// 各成文へ分割
		String ErrorTitle = values[0];
		String ErrorMessage = values[1];
		int buttonType = Integer.valueOf(values[2]);
		int defaultButton = Integer.valueOf(values[3]);

		JErrorMessageDialogFrameCtrl Dialog = new JErrorMessageDialogFrameCtrl(
				ParentFrame, ErrorTitle, ErrorMessage, buttonType,
				defaultButton, ID);

		return Dialog.getStatus();
	}

	/**
	 * ダイアログの表示
	 *
	 * @param ID
	 *            エラーID
	 * @param ParentFrame
	 *            親フレーム
	 * @return 押したボタンの値
	 */
	public static RETURN_VALUE show(
			String ID, JFrame ParentFrame, Object[] params) {

		String right = (String) mProperties.get(ID);
		if (right == null) {
			new JErrorMessageDialogFrameCtrl(ParentFrame, "エラー",
					"メッセージの解析に失敗しました。", 0, 0, "M0102");
			throw new RuntimeException();
		}

		String[] values = right.split(",");

		// 各成文へ分割
		String ErrorTitle = values[0];
		String ErrorMessage = values[1];

		int buttonType = Integer.valueOf(values[2]);
		int defaultButton = Integer.valueOf(values[3]);

//		JErrorMessageDialogFrameCtrl Dialog = new JErrorMessageDialogFrameCtrl(
//				ParentFrame,
//				ErrorTitle,
//				ErrorMessage,
//				buttonType,
//				defaultButton,
//				ID);

		JErrorMessageDialogFrameCtrl Dialog = new JErrorMessageDialogFrameCtrl(
				ParentFrame,
				ErrorTitle,
				ErrorMessage,
				params,
				buttonType,
				defaultButton,
				ID);

		return Dialog.getStatus();
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

