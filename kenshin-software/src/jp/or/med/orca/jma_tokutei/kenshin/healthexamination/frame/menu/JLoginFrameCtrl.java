package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.internationalization.java.XMLResourcesFactory;
import org.openswing.swing.lookup.client.LookupController;
import org.openswing.swing.permissions.java.ButtonsAuthorizations;
import org.openswing.swing.table.profiles.client.FileGridProfileManager;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.java.Consts;

import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DngPreviewHtml;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.SettingDialog;
import jp.or.med.orca.jma_tokutei.common.origine.JKenshinPatternMaintenanceEditFrameData;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;
//import jp.or.med.orca.jma_tokutei.common.app.ClientSettings;
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

// del s.inoue 2013/03/12
//		try{
//		  String osname = System.getProperty("os.name");
//	      if(osname.indexOf("Linux")>=0){
//	    	  this.jButton_Setting.setEnabled(false);
//	      }
//		}catch (Exception e) {
//			logger.error(e.getMessage());
//		}

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

		// openswing s.inoue 2010/12/21
		setClientSetting();

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


		// add s.inoue 2011/05/09
		String gridSearchCnt = PropertyUtil.getProperty( "grid.search.viewCount");
		JApplication.gridViewSearchCount =Integer.parseInt(gridSearchCnt);

		String gridMasterCnt = PropertyUtil.getProperty( "grid.master.viewCount");
		JApplication.gridViewMasterCount =Integer.parseInt(gridMasterCnt);

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

		// openswing s.inoue 2011/02/15
		String lookAndFeel = PropertyUtil.getProperty( "setting.lookAndFeel");
		if (!lookAndFeel.equals("")){
			SettingDialog sd = new SettingDialog();
			sd.changeTheLookAndFeel(lookAndFeel,true);
		}
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

			/* 機関番号をコンボボックスから選択できるように変更 */
			(new String(jPasswordField_Password.getPassword())).length() > 0) {
				jButton_Login.setEnabled(true);
			} else {
				jButton_Login.setEnabled(false);
			}
		}
	}

	// openswing s.inoue 2010/11/29
	/*
	 * ClientSetting設定
	 */
	private static void setClientSetting(){

		JApplication.domains = new Hashtable();
		Properties props = new Properties();

		// add s.inoue 2012/10/26
	    Domain jisiDomain = new Domain("JISI_KBN");
	    jisiDomain.addDomainPair("2","未");
	    jisiDomain.addDomainPair("1","済");
	    JApplication.domains.put(
	    	jisiDomain.getDomainId(),
	    	jisiDomain
	    );

	    Domain getujiDomain = new Domain("JISI_KBN_WK");
	    getujiDomain.addDomainPair("2","未");
	    getujiDomain.addDomainPair("1","済");
	    JApplication.domains.put(
	    		getujiDomain.getDomainId(),
	    		getujiDomain
	    );

		// add s.inoue 2012/10/23
	    Domain inputDomain = new Domain("KEKA_INPUT_FLG");
	    inputDomain.addDomainPair("1","未");
	    inputDomain.addDomainPair("2","済");
	    JApplication.domains.put(
    		inputDomain.getDomainId(),
    		inputDomain
	    );

		// add s.inoue 2012/10/23
	    Domain sexDomain = new Domain("SEX");
	    sexDomain.addDomainPair("1","男");
	    sexDomain.addDomainPair("2","女");
	    JApplication.domains.put(
	      sexDomain.getDomainId(),
	      sexDomain
	    );

		// add s.inoue 2011/04/15
	    Domain kengenDomain = new Domain("KENGEN");
	    kengenDomain.addDomainPair("1","Administrator");
	    kengenDomain.addDomainPair("2","User");
	    JApplication.domains.put(
	      kengenDomain.getDomainId(),
	      kengenDomain
	    );

		// add s.inoue 2011/04/15
	    Domain hisuDomain = new Domain("HISU_FLG");
	    hisuDomain.addDomainPair("1","Basic");
	    hisuDomain.addDomainPair("2","Detail");
	    hisuDomain.addDomainPair("3","Additional");
	    JApplication.domains.put(
    		hisuDomain.getDomainId(),
    		hisuDomain
	    );

	    // add s.inoue 2011/10/13
	    Domain hokensidoDomain = new Domain("HOKENSIDO_LEVEL");
	    hokensidoDomain.addDomainPair("1","HokenSido_Mihantei");
	    hokensidoDomain.addDomainPair("2","HokenSido_SekyokuSien");
	    hokensidoDomain.addDomainPair("3","HokenSido_DoukiSien");
	    hokensidoDomain.addDomainPair("4","HokenSido_JyohoTeikyo");
	    hokensidoDomain.addDomainPair("5","HokenSido_HanteiFunou");
	    JApplication.domains.put(
    		hokensidoDomain.getDomainId(),
    		hokensidoDomain
	    );

	    // add s.inoue 2011/10/13
	    Domain metaboDomain = new Domain("METABOHANTEI_LEVEL");
	    metaboDomain.addDomainPair("1","Metabo_Mihantei");
	    metaboDomain.addDomainPair("2","Metabo_KijyunGaito");
	    metaboDomain.addDomainPair("3","Metabo_YobigunGaito");
	    metaboDomain.addDomainPair("4","Metabo_Higaito");
	    metaboDomain.addDomainPair("5","Metabo_HanteiFunou");
	    JApplication.domains.put(
    		metaboDomain.getDomainId(),
    		metaboDomain
	    );

	    // add s.inoue 2011/10/13
	    Domain seikyuDomain = new Domain("SEIKYUKBN_LEVEL");
	    seikyuDomain.addDomainPair("1","Seikyu_Kihon");
	    seikyuDomain.addDomainPair("2","Seikyu_KihonSyosai");
	    seikyuDomain.addDomainPair("3","Seikyu_KihonTuika");
	    seikyuDomain.addDomainPair("4","Seikyu_KihonSyosaiTuika");
	    seikyuDomain.addDomainPair("5","Seikyu_NingenDoc");
	    JApplication.domains.put(
    		seikyuDomain.getDomainId(),
    		seikyuDomain
	    );

	    // add s.inoue 2012/11/15
	    Domain itakuDomain = new Domain("ITAKU_KBN");
	    itakuDomain.addDomainPair("1","Itaku_Kobetu");
	    itakuDomain.addDomainPair("2","Itaku_Syudan");
	    JApplication.domains.put(
	    	itakuDomain.getDomainId(),
	    	itakuDomain
	    );

// del s.inoue 2012/02/20
//	    // eidt s.inoue 2011/10/18
//		// 検査項目の選択肢取得
//		ArrayList<Hashtable<String, String>> codeResult = null;
//		Hashtable<String, String> codeResultItem;
//
//		String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE order by KOUMOKU_CD desc,CODE_NUM");
//		try {
//			codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//
//		String preKoumokuCD = "";
//		String nextKoumokuCD = "";
//		Domain koumokuDomain = null;
//		Domain patternDomain = null;
//
//		for (int j = 0; j < codeResult.size(); j++) {
//			codeResultItem = codeResult.get(j);
//
//			String koumokuCD = codeResultItem.get("KOUMOKU_CD");
//			String codeNUM = codeResultItem.get("CODE_NUM");
//			String codeNAME = codeResultItem.get("CODE_NAME");
//
//			if (!koumokuCD.equals(preKoumokuCD))
//				koumokuDomain = new Domain(koumokuCD);
//
//			koumokuDomain.addDomainPair(codeNUM,codeNAME);
//			System.out.println(koumokuCD + " "+codeNUM + " " + codeNAME);
//
//			if (j+1 < codeResult.size())
//				nextKoumokuCD = codeResult.get(j + 1).get("KOUMOKU_CD");
//
//			// eidt s.inoue 2012/02/09
//			// 最終項目が抜けるのを防ぐ
//			if (!koumokuCD.equals(nextKoumokuCD) ||
//					(j == codeResult.size() - 1))
//				domains.put(koumokuDomain.getDomainId(),koumokuDomain);
//
//			preKoumokuCD = koumokuCD;
//		}


//	 // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//	 // add s.inoue 2012/06/11
//
//		// 独自追加項目フラグ取得
//	    ArrayList<Hashtable<String, String>> results = null;
//	    boolean origin_flg = false;
//
//		try{
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
//			sb.append(" FROM T_SCREENFUNCTION ");
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert("004"));
//
//			results = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//		for (int i = 0; i < results.size(); i++) {
//			Hashtable<String, String> item = results.get(i);
//
//			String functionCd = item.get("FUNCTION_CD");
//
//			if (JApplication.func_orijinCode.equals(functionCd)){
//				origin_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
//			}
//		}
//
//		// グラフ用健診項目リスト
//		ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> resultItem = new Hashtable<String,String>();
//
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("SELECT distinct case MS.HISU_FLG when 1 then '基本' when 2 then '詳細' else '追加' end as HISU_FLG,");
//		sb.append("MS.KOUMOKU_CD,MS.KOUMOKU_NAME,MS.KENSA_HOUHOU,XMLITEM_SEQNO ");
//		sb.append("FROM T_KENSHINMASTER MS ");
//		sb.append("INNER JOIN T_KENSHIN_P_S PS ");
//		sb.append("ON MS.KOUMOKU_CD = PS.KOUMOKU_CD ");
//		sb.append(" AND MS.HKNJANUM = ");
//		sb.append(JQueryConvert.queryConvert("99999999"));
//		sb.append(" ORDER BY K_P_NO, LOW_ID ");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//
//		JKenshinPatternMaintenanceEditFrameData tuikaCode =
//			new JKenshinPatternMaintenanceEditFrameData();
//
//		HashSet<String> dokujiKoumokuCD = tuikaCode.getDokujiTuikaCD();
//
//		JApplication.graphDomain = new Domain("T_KENSHINMASTER");
//
//		for( int i = 0;i < result.size();i++ )
//		{
//			resultItem = result.get(i);
//
//			String koumokuCD = resultItem.get("KOUMOKU_CD");
//			String combItem = "";
//			if(dokujiKoumokuCD.contains(koumokuCD)){
//				if (origin_flg)
//				combItem = koumokuCD +":" + resultItem.get("KOUMOKU_NAME") +":"+ resultItem.get("KENSA_HOUHOU");
//			}else{
//				combItem = koumokuCD +":" + resultItem.get("KOUMOKU_NAME") +":"+ resultItem.get("KENSA_HOUHOU");
//			}
//			if (!koumokuCD.equals("") && !combItem.equals(""))
//			JApplication.graphDomain.addDomainPair(koumokuCD,combItem);
//		}
//
//		JApplication.domains.put(
//				JApplication.graphDomain.getDomainId(),JApplication.graphDomain);
//
//	 // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	    // add s.inoue 2012/10/25
		// 支払代行
		ArrayList<Hashtable<String, String>> siharaiItems = null;
		String shiharai = "SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO DESC";
		try {
			siharaiItems = JApplication.kikanDatabase.sendExecuteQuery(shiharai);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		JApplication.shiharaiDomain = new Domain("T_SHIHARAI");

		for (int i = 0; i < siharaiItems.size(); i++) {
			Hashtable<String, String> Item;
			Item = siharaiItems.get(i);

			String siharaiNO = Item.get("SHIHARAI_DAIKO_NO");
			String shiharaiNAME = Item.get("SHIHARAI_DAIKO_NAME");

			if (shiharaiNAME.length() >10)
				shiharaiNAME = shiharaiNAME.substring(0, 10);

			JApplication.shiharaiDomain.addDomainPair(siharaiNO,siharaiNO +":"+ shiharaiNAME);
		}
		JApplication.domains.put(
				JApplication.shiharaiDomain.getDomainId(),JApplication.shiharaiDomain);

	    // add s.inoue 2012/10/24
		// 保険者
		ArrayList<Hashtable<String, String>> hokenjaItems = null;
		String hokenja = "SELECT DISTINCT(HKNJANUM) HKNJANUM, HKNJANAME, POST, ADRS, TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC";
		try {
			hokenjaItems = JApplication.kikanDatabase.sendExecuteQuery(hokenja);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		JApplication.hokenjaDomain = new Domain("T_HOKENJYA");

		for (int i = 0; i < hokenjaItems.size(); i++) {
			Hashtable<String, String> Item;
			Item = hokenjaItems.get(i);

			String hokenjaNO = Item.get("HKNJANUM");
			String kpNAME = Item.get("HKNJANAME");

			if (kpNAME.length() >10)
				kpNAME = kpNAME.substring(0, 10);

			JApplication.hokenjaDomain.addDomainPair(hokenjaNO,hokenjaNO +":"+ kpNAME);
		}
		JApplication.domains.put(
				JApplication.hokenjaDomain.getDomainId(),JApplication.hokenjaDomain);
		// ↑↑↑↑↑↑↑↑↑↑↑↑↑


		// 健診パターンリスト
		ArrayList<Hashtable<String, String>> items = null;
		String pattern = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO";
		try {
			items = JApplication.kikanDatabase.sendExecuteQuery(pattern);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		// patternDomain = new Domain("T_KENSHIN_P_M");
		JApplication.patternDomain = new Domain("T_KENSHIN_P_M");

		for (int i = 0; i < items.size(); i++) {
			Hashtable<String, String> Item;
			Item = items.get(i);

			String kpNO = Item.get("K_P_NO");
			String kpNAME = Item.get("K_P_NAME");
			// add s.inoue 2011/11/28
			if(!kpNO.equals("9999")){
				JApplication.patternDomain.addDomainPair(kpNO,kpNAME);
			}
		}
		JApplication.domains.put(
				JApplication.patternDomain.getDomainId(),JApplication.patternDomain);

		// ボタンの制御（初期表示）
		ButtonsAuthorizations auth = new ButtonsAuthorizations();
		auth.addButtonAuthorization("F1",true,true,true);
		// 日本語を使用
	    Hashtable xmlFiles = new Hashtable();
	    xmlFiles.put("JP","resources_jp.xml");

	    JApplication.clientSettings = new ClientSettings(
	        new XMLResourcesFactory(xmlFiles,true),
	        JApplication.domains,
	        auth
 	    );

// combobox
//	    Domain orderStateDomain = new Domain("ORDERSTATE");
//	    orderStateDomain.addDomainPair("O","opened");
//	    orderStateDomain.addDomainPair("S","suspended");
//	    orderStateDomain.addDomainPair("D","delivered");
//	    orderStateDomain.addDomainPair("ABC","closed");
//	    domains.put(
//	      orderStateDomain.getDomainId(),
//	      orderStateDomain
//	    );

	    // add s.inoue 2012/10/11
	    // 必須マーク
		ClientSettings.VIEW_MANDATORY_SYMBOL = true;
		// 検索窓
		ClientSettings.FILTER_PANEL_ON_GRID = true;
		// 窓ロック(0:closeOnExit,1:useClose,2:pressed,3unPressed)
		ClientSettings.FILTER_PANEL_ON_GRID_POLICY = Consts.FILTER_PANEL_ON_GRID_USE_PADLOCK_UNPRESSED;

		// セル色
		ClientSettings.VIEW_BACKGROUND_SEL_COLOR = true;
		// buttonText
		ClientSettings.BUTTON_BEHAVIOR = Consts.BUTTON_IMAGE_AND_TEXT;
		// ソート表記
		ClientSettings.SHOW_SORTING_ORDER = true;
		ClientSettings.SHOW_FOCUS_BORDER_ON_FORM = true;
		// コントロール群へフォーカス出来るかどうか
		ClientSettings.DISABLED_INPUT_CONTROLS_FOCUSABLE = false;

	    ClientSettings.ALLOW_OR_OPERATOR = false;
	    ClientSettings.INCLUDE_IN_OPERATOR = false;

		ClientSettings.GRID_PROFILE_MANAGER = new FileGridProfileManager();
	    ClientSettings.SELECT_DATA_IN_EDITABLE_GRID = true;

        ClientSettings.GRID_SCROLL_BLOCK_INCREMENT = Consts.GRID_SCROLL_BLOCK_INCREMENT_PAGE;

	    // filter機能
	    ClientSettings.GRID_PROFILE_MANAGER = new FileGridProfileManager();
	    ClientSettings.LOOKUP_FRAME_CONTENT = LookupController.GRID_AND_FILTER_FRAME;

//	    // add s.inoue 2012/11/14
//	    /** flag used to add a filter panel on top of the exported grid, in order to show filtering conditions;
//	     this pane is visibile only whether there is at least one filtering condition applied; default value: <code>false</code> */
//	    ClientSettings.SHOW_FILTERING_CONDITIONS_IN_EXPORT = true;
//
//	    /** flag used to show the title of the frame that contains the grid component currently exported;
//	     title is showed on top of the exported grid, in order to show filtering conditions; default value: <code>false</code> */
//	    ClientSettings.SHOW_FRAME_TITLE_IN_EXPORT = false;

		// 言語
		ClientSettings.getInstance().setLanguage("JP");

		// デフォルトの設定
	    // ClientSettings.GRID_CELL_BACKGROUND = new Color(220,220,220);
	    // ClientSettings.GRID_CELL_FOREGROUND = Color.BLACK;
	    // ClientSettings.GRID_EDITABLE_CELL_BACKGROUND = Color.WHITE;
	    // ClientSettings.GRID_NOT_EDITABLE_CELL_BACKGROUND = new Color(220,220,220);
		ClientSettings.GRID_REQUIRED_CELL_BORDER = Color.red;
		ClientSettings.GRID_NO_FOCUS_BORDER = Color.lightGray;
		ClientSettings.SHOW_PAGE_NUMBER_IN_GRID = true;
		ClientSettings.VIEW_BACKGROUND_SEL_COLOR = true;
		ClientSettings.BACKGROUND_SEL_COLOR = new Color(220,220,220);
		ClientSettings.SHOW_FOCUS_BORDER_ON_FORM = false;
		ClientSettings.ASK_BEFORE_CLOSE = true;

// eidt s.inoue 2013/03/12
		try{
//		  String osname = System.getProperty("os.name");
//	      if(osname.indexOf("Windows")>=0){
	    	  ClientSettings.LOOK_AND_FEEL_CLASS_NAME =PropertyUtil.getProperty( "setting.lookAndFeel");
	    	  Class.forName(ClientSettings.LOOK_AND_FEEL_CLASS_NAME).getMethod(
			      "setCurrentTheme", new Class[] {Properties.class}).invoke(null,
			      new Object[] {props});
			UIManager.setLookAndFeel(ClientSettings.LOOK_AND_FEEL_CLASS_NAME);
//	      }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
