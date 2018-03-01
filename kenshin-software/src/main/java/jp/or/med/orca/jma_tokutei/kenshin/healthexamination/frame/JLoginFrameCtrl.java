package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.component.DngPreviewHtml;
import jp.or.med.orca.jma_tokutei.common.component.IDialog;
import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu.JMenuFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;

/**
 * 特定健診ソフトウエアのログイン画面のフレームのコントロール
 *
 * Modified 2008/04/01 文字列を定数に変更。 Modified 2008/04/06 視認性向上のため、不要なコメントを削除し、Java
 * の規約に基づいて フォーマットを行なう。
 *
 * Modified 2008/05/14 メニュー画面から戻る機能への対応
 */
public class JLoginFrameCtrl extends JLoginFrame {
	private static final String TEXT_NO_NAME = "（名称が設定されていません）";

	/** 健診機関番号の正規表現 */
	private static final String REGEX_KENSHINKIKAN_NO = "^\\d{10}.(fdb|FDB)";
	private static int lastSelectedKikanInfomationIndex = 0;
	private static Pattern kikanInfomationPattern = Pattern.compile("^.* - (\\d{10})$");
	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	// edit s.inoue 2009/12/12
	private static org.apache.log4j.Logger logger = Logger.getLogger(JLoginFrameCtrl.class);
	// edit s.inoue 2009/12/18
	private IDialog settingDialog;

	/**
	 * コンストラクタ
	 */
	public JLoginFrameCtrl() {

		jButton_Login.setEnabled(false);

		jTextField_UserName.getDocument().addDocumentListener(
				new LoginButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(
				new LoginButtonStatus());

		// /* 機関番号の一覧を設定する。 */
		// File file = new File(JPath.DatabaseFolder);
		// File[] files = file.listFiles();
		//
		// if (files != null) {
		// for (int i = 0; files.length > i; i++) {
		// String filename = files[i].getName();
		// if (Pattern.matches(REGEX_KENSHINKIKAN_NO, filename)) {
		// /* 機関DBなら、機関番号をコンボボックスに設定する。 */
		//
		// String kikanNumber = filename.substring(
		// 0, filename.length() - 4);
		// jComboBox_kikanNumber.addItem(kikanNumber);
		// }
		// }
		// }

		/* 機関選択欄を初期化する。 */
		this.initializeKikanComboBox();

		/* タイトルバーを初期化する。 */
		this.initializeFrameTitle();

		/* スプラッシュ画面が非表示になった場合、バージョンボタンを有効にする。 */
		// del y.okano 2010/05/24
		//JSoftware.getSplashFrame().addComponentListener(new ComponentAdapter() {
		//	@Override
		//	public void componentHidden(ComponentEvent arg0) {
		//		JLoginFrameCtrl.this.jButton_Version.setSelected(false);
		//	}
		//});

		/* ログイン画面がアクティブで無くなったとき、スプラッシュ画面を非表示にする。 */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		this.setVisible(true);

		/* ユーザ名入力欄にフォーカスを移動する。 */
		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
		this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		this.focusTraversalPolicy.addComponent(this.jButton_Login);
		// del y.okano 2010/05/24
		//this.focusTraversalPolicy.addComponent(this.jButton_Version);
		this.focusTraversalPolicy.addComponent(jButton_End);
		jTextField_UserName.requestFocus();
		this.jComboBox_kikanNumber.transferFocus();

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * 機関選択欄を初期化する。
	 */
	private boolean initializeKikanComboBox() {
		boolean retValue = false;

		ArrayList<Hashtable<String, String>> items = null;
		try {
			items = JApplication.systemDatabase
					.sendExecuteQuery("SELECT * FROM T_F_KIKAN");
			retValue = true;

		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M7200", this);
		}

		if (retValue && items != null) {
			for (int i = items.size() - 1; i >= 0; i--) {
				Hashtable<String, String> item = items.get(i);

				String kikanNumber = item.get("TKIKAN_NO");
				String kikanName = item.get("KIKAN_NAME");

				StringBuffer buffer = new StringBuffer();
				if (kikanName != null && !kikanName.isEmpty()) {
					buffer.append(kikanName);
				} else {
					buffer.append(TEXT_NO_NAME);
				}
				buffer.append(" - ");
				buffer.append(kikanNumber);

				this.jComboBox_kikanNumber.addItem(buffer.toString());
			}
		}

		this.jComboBox_kikanNumber.setSelectedIndex(lastSelectedKikanInfomationIndex);

		return retValue;
	}

	private void initializeFrameTitle() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(ViewSettings.getTokuteFrameTitle());
		buffer.append(" (Version ");
		buffer.append(JApplication.versionNumber);
		buffer.append(")");

		String title = buffer.toString();
		this.setTitle(title);
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * バージョンボタン
	 */
	// del y.okano 2010/05/24
	//public void pushedVersionButton(ActionEvent e) {
	//	if (jButton_Version.isSelected()) {
	//		JSoftware.getSplashFrame().showSplashWindow();
	//	} else {
	//		JSoftware.getSplashFrame().hideSplashWindow();
	//	}
	//}

	// add s.inoue 2009/12/18
	/**
	 * 環境設定ボタン
	 */
	public void pushedSettingButton(ActionEvent e) {
		try {
			settingDialog
				= DialogFactory.getInstance().createDialog(this, e, null);

			settingDialog.setMessageTitle("環境設定画面");

			settingDialog.setVisible(true);
			// del s.inoue 2010/04/07
			// if (settingDialog.getStatus().equals(RETURN_VALUE.CANCEL))
			// 	return;

			// add s.inoue 2009/12/18
			reloginSystem();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/* 再ログイン */
	private void reloginSystem(){
		try{
			// windowを全て閉じるLookAndFeel
			java.awt.Window[] win =this.getWindows();
			for (int i = 0; i < win.length; i++) {
				win[i].dispose();
			}

			JScene.ChangeScene(new JLoginFrameCtrl());

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	/**
	 * ログインボタン
	 */
	public void pushedLoginButton() {
		/* ユーザ名 */
		String userName = jTextField_UserName.getText();
		// 入力文字列の長さをチェック
		if (userName.length() <= 0) {
			JErrorMessage.show("M1100", this);
			return;
		}

		/* パスワード */
		String password = new String(this.jPasswordField_Password.getPassword());
		if (password.length() <= 0) {
			JErrorMessage.show("M1101", this);
			return;
		}

		/* 機関番号 */
		String kikan = (String) jComboBox_kikanNumber.getSelectedItem();

		Matcher m = kikanInfomationPattern.matcher(kikan);
		String kikanNumber = null;
		if (m.matches() && m.groupCount() > 0) {
			kikanNumber = m.group(1);
		}

		if (kikanNumber == null || kikanNumber.length() < 10) {
			JErrorMessage.show("M1102", this);
			return;
		}

		// データベースに接続
		try {
			JApplication.kikanDatabase = JConnection
					.ConnectKikanDatabase(kikanNumber);
		} catch (SQLException e1) {
			JErrorMessage.show("M1103", this);
			return;
		}

		// ログインをする
		ArrayList<Hashtable<String, String>> result;
		try {
			result = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT * FROM T_USER WHERE USER_NAME = "
							+ JQueryConvert.queryConvert(userName));
		} catch (SQLException e1) {
			JErrorMessage.show("M1104", this);
			return;
		}

		if (result.size() <= 0) {
			JErrorMessage.show("M1104", this);
			return;
		}

		Hashtable<String, String> resultItem = result.get(0);
		String dbPassword = resultItem.get("PASS");

		if (!dbPassword.equals(password)) {
			// edit h.yoshitama 2009/11/10
			JErrorMessage.show("M1104", this);
			return;
		}

		JApplication.kikanNumber = kikanNumber;
		JApplication.userID = resultItem.get("USER_NAME");
		JApplication.password = resultItem.get("PASS");
		JApplication.authority = JInteger.valueOf(resultItem.get("KENGEN"));

		JApplication.loadORCAData();

		try {
			result = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT KIKAN_NAME FROM T_KIKAN");
		} catch (SQLException e1) {
			JErrorMessage.show("M1104", this);
			return;
		}

		if (result.size() == 1) {
			JApplication.kikanName = result.get(0).get("KIKAN_NAME");
		}

		// yoshida 20090105 s.inoue
		// 機関番号.fdbを対象 kikanNumber は String
		// 負荷をかけない為、DBを一旦停止
		try{
			JApplication.kikanDatabase.Shutdown();
		} catch (SQLException ex) {
			JErrorMessage.show("M1103", this);
			return;
		}

		// yoshida 20090105 s.inoue
		ShcDBAdjust adjuster = new ShcDBAdjust();
        adjuster.call(JApplication.kikanNumber);

		// add ver2 s.inoue 2009/07/06
		DBYearAdjuster yajuster = new DBYearAdjuster();
		yajuster.call(JApplication.kikanNumber);

        // 負荷をかけない為、DBを一旦再起動
        try{
        	JApplication.kikanDatabase = JConnection.ConnectKikanDatabase(kikanNumber);

		} catch (SQLException ex) {
			JErrorMessage.show("M1103", this);
			return;
		}

        // バージョン(スキーマ、データ)をT_Versionより取得
        try {
			JApplication.loadDBVersions(JApplication.kikanDatabase);
		} catch (Exception ex) {
			JErrorMessage.show("M1106", this);
			return;
		}

// edit s.inoue 2009/09/29
// 開発時はコメントアウトする
		/* 二重起動制限 */
		try {
			String lockfileName = JExecLockerConfig.LOCK_FILENAME_BASE + "."
					+ kikanNumber;
			JExecLocker.getLockWithName(lockfileName);
		} catch (Exception e) {
			JErrorMessage.show("M1002", null);
			return;
		}

		lastSelectedKikanInfomationIndex = this.jComboBox_kikanNumber.getSelectedIndex();

		logger.info("機関番号:" + kikanNumber + "へログイン");

		JScene.ChangeScene(new JMenuFrameCtrl());
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_Login) {
			logger.info(jButton_Login.getText());
			pushedLoginButton();
		}
		else if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		// del y.okano 2010/05/24
		//else if (source == jButton_Version) {
		//	logger.info(jButton_Version.getText());
		//	pushedVersionButton(e);
		//}

		// add s.inoue 2010/08/02
		else if (source == jButton_UpdateInfo){
			logger.info(jButton_UpdateInfo.getText());
    		// edit s.inoue 2010/08/02
//			DngPreviewHtml dng = new DngPreviewHtml();
//    		dng.setLayout("http://www.orca.med.or.jp/ikensyo/info/");
			try {
				settingDialog
					= DialogFactory.getInstance().createDialog(this, e, null);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		// add s.inoue 2009/12/18
		else if (source == jButton_Setting) {
			logger.info(jButton_Setting.getText());
			pushedSettingButton(e);
		}
		else if (source == jTextField_UserName) {
			logger.info(jTextField_UserName.getText());
			jTextField_UserName.transferFocus();
		}
		else if (source == jPasswordField_Password) {
			// ログインボタンが有効なときだけ処理を行う
			if (jButton_Login.isEnabled()) {
				pushedLoginButton();
			} else {
				jPasswordField_Password.transferFocus();
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		Object source = e.getSource();
		char keyChar = e.getKeyChar();

		if (source == this.jComboBox_kikanNumber) {
			if (keyChar == KeyEvent.VK_ENTER) {
				this.jComboBox_kikanNumber.transferFocus();
			}
		}
	}

	// edit s.inoue 2010/04/16
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;

		// edit s.inoue 2010/08/02
		case KeyEvent.VK_F7:
			logger.info(jButton_UpdateInfo.getText());
    		DngPreviewHtml dng = new DngPreviewHtml(this);
    		// dng.setLayout("http://www.orca.med.or.jp/ikensyo/info/");

		case KeyEvent.VK_F8:
			logger.info(jButton_Setting.getText());
			pushedSettingButton(null);break;
		case KeyEvent.VK_F12:
			if(jButton_Login.isEnabled()){
				logger.info(jButton_Login.getText());
				pushedLoginButton();
			}
			break;
		// del y.okano 2010/05/24
		//case KeyEvent.VK_F9:
		//	logger.info(jButton_Version.getText());
		//	if(jButton_Version.isSelected()){
		//		jButton_Version.setSelected(false);
		//	}else{
		//		jButton_Version.setSelected(true);
		//	}
		//	pushedVersionButton(null);break;
		case KeyEvent.VK_ENTER:
			if (keyEvent.getComponent()== jPasswordField_Password){
				logger.info(jButton_Login.getText());
				// ログインボタンが有効なときだけ処理を行う
				if (jButton_Login.isEnabled()) {
					pushedLoginButton();
				} else {
					jPasswordField_Password.transferFocus();
				}
			}
		}
	}

	/**
	 * ログインボタンのステータスをテキストフィールドの内容によって 変更するためのイベントリスナ
	 */
	public class LoginButtonStatus implements DocumentListener {
		public void changedUpdate(DocumentEvent arg0) {
		}

		public void insertUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}

		public void removeUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}
	}

	/**
	 * ログインボタンのステータスチェックを行う。 LoginButtonStatusイベントから呼び出すための関数。
	 */
	public void setLoginButtonStatus(DocumentEvent e) {
		// ログインボタンの有効化
		if (e.getDocument() == jTextField_UserName.getDocument()
				|| e.getDocument() == jPasswordField_Password.getDocument()) {
			if (jTextField_UserName.getText().length() > 0 &&

			/* Modified 2008/03/09 若月 機関番号をコンボボックスから選択できるように変更 */
			(new String(jPasswordField_Password.getPassword())).length() > 0) {
				jButton_Login.setEnabled(true);
			} else {
				jButton_Login.setEnabled(false);
			}
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

