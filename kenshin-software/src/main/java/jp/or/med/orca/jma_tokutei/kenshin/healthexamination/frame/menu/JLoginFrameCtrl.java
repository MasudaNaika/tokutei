package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Getuji;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Hantei;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_KouteiMaster;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Master;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Nitiji;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Serche;
import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLockerConfig;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecUnlocker;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.SettingDialog;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
import jp.or.med.orca.jma_tokutei.dbfix.ShcDBAdjust;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;

import org.apache.log4j.Logger;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.internationalization.java.XMLResourcesFactory;
import org.openswing.swing.lookup.client.LookupController;
import org.openswing.swing.permissions.java.ButtonsAuthorizations;
import org.openswing.swing.table.profiles.client.FileGridProfileManager;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.java.Consts;

/**
 * 特定健診ソフトウエアのログイン画面のフレームのコントロール
 *
 * Modified 2008/04/01 文字列を定数に変更。 Modified 2008/04/06 視認性向上のため、不要なコメントを削除し、Java
 * の規約に基づいて フォーマットを行なう。
 *
 * Modified 2008/05/14 メニュー画面から戻る機能への対応
 */
public class JLoginFrameCtrl extends JLoginFrame {
	
	private static final long serialVersionUID = -6098106062110461819L;	// edit n.ohkubo 2014/10/01　追加

	private static final String TEXT_NO_NAME = "（名称が設定されていません）";

	/** 健診機関番号の正規表現 */
//	private static final String REGEX_KENSHINKIKAN_NO = "^\\d{10}.(fdb|FDB)";	// edit n.ohkubo 2014/10/01　未使用なので削除
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
			
			// edit n.ohkubo 2015/08/01　追加　start　JSoftware.javaのinitializeメソッド内でチェックしているので、通常0件にはならないが、他PCのFDBを使用している場合0件になる可能性があるのでチェックする
			if (items.size() == 0) {
				JErrorMessage.show("M1001", null);
				System.exit(1);
			}
			// edit n.ohkubo 2015/08/01　追加　end　JSoftware.javaのinitializeメソッド内でチェックしているので、通常0件にはならないが、他PCのFDBを使用している場合0件になる可能性があるのでチェックする

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
        
        // add s.inoue 2014/04/30
        setScreenColumns();

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
			// edit n.ohkubo 2015/08/01　削除　メッセージを変更して、実行ロック解除を行えるようにする
//			JErrorMessage.show("M1002", null);
			
			// edit n.ohkubo 2015/08/01　追加　実行ロック解除の起動　start
			//※LookAndFeelの設定を行わないと、メッセージのダイアログが白（描画が中途半端）で表示されたり、メッセージを"いいえ"で戻ったときに健診機関のプルダウンでヌルポが発生する（Goodiesのみ発生する現象）
			String lookAndFeel = PropertyUtil.getProperty( "setting.lookAndFeel");
			if (!lookAndFeel.equals("")) {
				SettingDialog sd = new SettingDialog();
				sd.changeTheLookAndFeel(lookAndFeel, true);
			}
			
			//メッセージの表示
			RETURN_VALUE val = JErrorMessage.show("M1004", this);
			if (val == RETURN_VALUE.YES) {
				JExecUnlocker.main(null);
			}
			// edit n.ohkubo 2015/08/01　追加　実行ロック解除の起動　end
			
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

	@Override
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

	@Override
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
//    		DngPreviewHtml dng = new DngPreviewHtml(this);	// edit n.ohkubo 2014/10/01　未使用なので削除
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
		@Override
		public void changedUpdate(DocumentEvent arg0) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}

		@Override
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

	    // add s.inoue 2013/11/08
	    Domain logLevelDomain = new Domain("LOG_LEVEL");
	    logLevelDomain.addDomainPair("INFO","正常");
	    logLevelDomain.addDomainPair("WARN","警告");
	    logLevelDomain.addDomainPair("ERROR","異常");
	    JApplication.domains.put(
    		logLevelDomain.getDomainId(),
    		logLevelDomain
	    );

	    // add s.inoue 2013/11/08
	    Domain logPlaceDomain = new Domain("LOG_PLACE");
	    logPlaceDomain.addDomainPair("JPath","パス");
	    logPlaceDomain.addDomainPair("JKekkaRegisterCommentInputDialog","コメント（dialog）");
	    logPlaceDomain.addDomainPair("JKekkaRegisterKekkaMojiretsuInputDialog","文字列（dialog）");
	    logPlaceDomain.addDomainPair("JSelectHokenjyaOrcaDialogCtrl","orca選択");
	    logPlaceDomain.addDomainPair("JAddKikanInformationFrameCtrl","機関情報追加");
	    logPlaceDomain.addDomainPair("DBYearAdjuster","経年処理（年度調整）");
	    logPlaceDomain.addDomainPair("JHokenjyaMasterMaintenanceEditFrameCtrl","保険者情報（編集）");
	    logPlaceDomain.addDomainPair("JHokenjyaMasterMaintenanceListFrame","保険者情報（編集）");
	    logPlaceDomain.addDomainPair("JImportDataFrameCtrl","健診結果データ取り込み");
	    logPlaceDomain.addDomainPair("JKeinenMasterMaintenanceListFrame","経年マスタメンテナンス");
	    logPlaceDomain.addDomainPair("JKenshinMasterMaintenanceListFrame","健診項目マスタメンテナンス");
	    logPlaceDomain.addDomainPair("JKenshinpatternMasterMaintenanceListFrame","健診パターンメンテナンス");
	    logPlaceDomain.addDomainPair("JKenshinPatternMaintenanceEditFrameCtrl","健診パターンメンテナンス（編集）");
	    logPlaceDomain.addDomainPair("JLoginFrameCtrl","ログイン");
	    logPlaceDomain.addDomainPair("JMasterMaintenanceFrameCtrl","マスタメンテナンスメニュー");
	    logPlaceDomain.addDomainPair("JMenuFrameCtrl","メインメニュー");
	    logPlaceDomain.addDomainPair("JSystemMaintenanceFrameCtrl","システムメンテナンスメニュー");
	    logPlaceDomain.addDomainPair("JOutputGetujiSearchListFrame","月次処理");
	    logPlaceDomain.addDomainPair("JHanteiSearchResultListFrame","メタボリックシンドローム判定・階層化");
	    logPlaceDomain.addDomainPair("JShowResultFrameCtrl","健診結果表示・自動判定");
	    logPlaceDomain.addDomainPair("JKekkaRegisterFrameCtrl","健診結果データ入力");
	    logPlaceDomain.addDomainPair("JKenshinKekkaSearchListFrame","健診結果データ一覧");
	    logPlaceDomain.addDomainPair("JKojinRegisterFrameCtrl","受診券入力画面");
	    logPlaceDomain.addDomainPair("JRegisterFlameCtrl","健診結果データ入力");
	    logPlaceDomain.addDomainPair("JInputKessaiDataFrameCtrl","請求データ編集");
	    logPlaceDomain.addDomainPair("JOutputNitijiSearchListFrame","日次処理");
	    logPlaceDomain.addDomainPair("JSelectHokenjyaFrameCtrl","保険者情報（dialog）");
	    logPlaceDomain.addDomainPair("JSelectKojinFrameCtrl","受診者（dialog）");
	    logPlaceDomain.addDomainPair("JSelectShiharaiFrameCtrl","支払代行機関（dialog）");
	    logPlaceDomain.addDomainPair("JShiharaiMasterMaintenanceEditFrameCtrl","支払代行情報メンテナンス（編集）");
	    logPlaceDomain.addDomainPair("JShiharaiMasterMaintenanceListFrame","支払代行情報メンテナンス");
	    logPlaceDomain.addDomainPair("JShokenMasterMaintenanceEditFrameCtrl","所見マスタメンテナンス（編集）");
	    logPlaceDomain.addDomainPair("JShokenMasterMaintenanceListFrame","所見マスタメンテナンス");
	    logPlaceDomain.addDomainPair("JKikanDBBackupFrameCtrl","機関DBバックアップ＆復元");
	    logPlaceDomain.addDomainPair("JKikanLogListFrame","ログファイル管理");
	    logPlaceDomain.addDomainPair("JKikanLogListFrameCtl","ログファイル管理");	// edit n.ohkubo 2014/10/01　追加
	    logPlaceDomain.addDomainPair("JUsabilityFrameCtrl","ユーザビリティメンテナンス");
	    logPlaceDomain.addDomainPair("JKekkaTeikeiMaintenanceEditFrameCtrl","所見マスタメンテナンス（編集）");
	    logPlaceDomain.addDomainPair("JKekkaTeikeiMaintenanceListFrameCtrl","所見マスタメンテナンス");
	    logPlaceDomain.addDomainPair("JKenshinMasterMaintenanceFrameCtrl","健診項目マスタメンテナンス");
	    logPlaceDomain.addDomainPair("JKenshinPatternMaintenanceAddFrameCtrl","機関情報メンテナンス編集（追加）");
	    logPlaceDomain.addDomainPair("JKenshinPatternMaintenanceCopyFrameCtrl","機関情報メンテナンス編集（複製）");
	    logPlaceDomain.addDomainPair("JKikanDBBackupFrameCtrl","健診結果データ取り込み");
	    logPlaceDomain.addDomainPair("JNayoseMaintenanceEditFrameCtl","経年マスタメンテナンス（編集）");
	    logPlaceDomain.addDomainPair("JNayoseMaintenanceListFrameCtl","経年マスタメンテナンス");
	    logPlaceDomain.addDomainPair("JRegisterUserFrameCtrl","ユーザマスタメンテナンス");
	    logPlaceDomain.addDomainPair("JSystemMaintenanceFrameCtrl","健診結果データ取り込み");
	    logPlaceDomain.addDomainPair("JUserMaintenanceFrameCtrl","健診結果データ取り込み");
	    // onlineupdate
	    logPlaceDomain.addDomainPair("KikanSchemaChangeTask","機関スキーマ処理/onlineupdate");
	    logPlaceDomain.addDomainPair("ModuleCopyTask","ファイル処理/onlineupdate");
	    logPlaceDomain.addDomainPair("SystemDataUpdateTask","システムデータ処理/onlineupdate");
	    logPlaceDomain.addDomainPair("SystemSchemaChangeTask","システムスキーマ処理/onlineupdate");
	    logPlaceDomain.addDomainPair("JMainFrame","メイン画面/onlineupdate");
	    // admin
	    logPlaceDomain.addDomainPair("JDBBackupFrameCtrl","システムバックアップ＆復元/管理");
	    logPlaceDomain.addDomainPair("JKikanMaintenanceFrameCtrl","機関情報メンテナンス/管理");
	    logPlaceDomain.addDomainPair("JLoginFrameCtrl","ログイン/管理");
	    logPlaceDomain.addDomainPair("JMenuFrameCtrl","メインメニュー/管理");
	    logPlaceDomain.addDomainPair("JRegisterUserFrameCtrl","健診結果データ取り込み/管理");
	    logPlaceDomain.addDomainPair("JNetworkDBConnectionFrame","DB接続情報メンテナンス（編集）");		// edit n.ohkubo 2015/08/01　追加
	    logPlaceDomain.addDomainPair("JNetworkDBConnectionFrameCtrl","DB接続情報メンテナンス（編集）");	// edit n.ohkubo 2015/08/01　追加
	    
	    JApplication.domains.put(
    		logPlaceDomain.getDomainId(),
    		logPlaceDomain
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
	    
	    // move s.inoue 2014/04/30
	    // ログイン処理時だとタイミング悪い
	    
	    
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

		// add s.inoue 2013/11/14
		// getFunctionSetting();
		
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

		// add s.inoue 2013/10/28

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
	    // add s.inoue 2013/10/28
	    ClientSettings.STORE_INTERNAL_FRAME_PROFILE = true;


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
			// eidt n.ohkubo 2014/08/22
			//Ver2.1.1に合わせるためにコメントアウト
//			logger.error(e.getMessage());
		}
	}
	
	// add s.inoue 2014/04/30
	private static void setScreenColumns(){
		
	    // add s.inoue 2013/11/22
		ArrayList<Hashtable<String, String>> searchColumnItems = null;
		String sql005 ="SELECT SCREEN_CD, NAME,JYUSHIN_SEIRI_NO,NENDO, HIHOKENJYASYO_NO,HIHOKENJYASYO_KIGOU, KENSA_NENGAPI, SEX, BIRTHDAY, "
				+ "KEKA_INPUT_FLG, HKNJANUM, SIHARAI_DAIKOU_BANGO, KANANAME, HANTEI_NENGAPI, TUTI_NENGAPI,JISI_KBN, CHECKBOX_COLUMN, "
				+ "TANKA_GOUKEI, MADO_FUTAN_GOUKEI, SEIKYU_KINGAKU, UPDATE_TIMESTAMP, HENKAN_NITIJI, METABO, HOKENSIDO_LEVEL, KOMENTO, "
				+ "KOUMOKU_CD, KOUMOKU_NAME, KENSA_HOUHOU, HISU_FLG, DS_KAGEN, DS_JYOUGEN, JS_KAGEN, JS_JYOUGEN, TANI, KAGEN, JYOUGEN, KIJYUNTI_HANI, TANKA_KENSIN, BIKOU, "
				// add s.inoue 2014/02/17
				+ "NITIJI_FLG,GETUJI_FLG "
				+ "FROM T_SCREENCOLUMNS "
				+ "WHERE SCREEN_CD ='005' or SCREEN_CD = '004' or SCREEN_CD = '006' or SCREEN_CD = '007' or SCREEN_CD = '102' or SCREEN_CD = '110'";

		try {
			searchColumnItems = JApplication.kikanDatabase.sendExecuteQuery(sql005);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < searchColumnItems.size(); i++) {
			Hashtable<String, String> Items;
			Items = searchColumnItems.get(i);
			// 結果一覧
			if (Items.get("SCREEN_CD").equals("005")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KOMENTO));
			}else if (Items.get("SCREEN_CD").equals("004")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag_Hantei.addAll(EnumSet.of(FlagEnum_Hantei.KOMENTO));
			}else if (Items.get("SCREEN_CD").equals("006")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KOMENTO));
				
			}else if (Items.get("SCREEN_CD").equals("007")){
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.JYUSHIN_SEIRI_NO));
				if(Items.get("NAME").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.NAME));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KENSA_NENGAPI));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SEX));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.BIRTHDAY));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KEKA_INPUT_FLG));
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HKNJANUM));
				if(Items.get("SIHARAI_DAIKOU_BANGO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KANANAME));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HANTEI_NENGAPI));
				if(Items.get("TUTI_NENGAPI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.TUTI_NENGAPI));
				if(Items.get("NENDO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.NENDO));
				if(Items.get("TANKA_GOUKEI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.TANKA_GOUKEI));
				if(Items.get("MADO_FUTAN_GOUKEI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.MADO_FUTAN_GOUKEI));
				if(Items.get("SEIKYU_KINGAKU").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.SEIKYU_KINGAKU));
				if(Items.get("CHECKBOX_COLUMN").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.CHECKBOX_COLUMN));
				if(Items.get("UPDATE_TIMESTAMP").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.UPDATE_TIMESTAMP));
				if(Items.get("JISI_KBN").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.JISI_KBN));
				if(Items.get("HENKAN_NITIJI").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HENKAN_NITIJI));
				if(Items.get("METABO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.METABO));
				if(Items.get("HOKENSIDO_LEVEL").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.HOKENSIDO_LEVEL));
				if(Items.get("KOMENTO").equals("0"))
					JApplication.flag_Getuji.addAll(EnumSet.of(FlagEnum_Getuji.KOMENTO));
			// add s.inoue 2013/12/19	
			}else if (Items.get("SCREEN_CD").equals("102")){
				if(Items.get("KOUMOKU_CD").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_CD));
				if(Items.get("KOUMOKU_NAME").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_NAME));
				if(Items.get("KENSA_HOUHOU").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KENSA_HOUHOU));
				if(Items.get("HISU_FLG").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.HISU_FLG));
				if(Items.get("DS_KAGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_KAGEN));
				if(Items.get("DS_JYOUGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_JYOUGEN));
				if(Items.get("JS_KAGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_KAGEN));
				if(Items.get("JS_JYOUGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_JYOUGEN));
				if(Items.get("TANI").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANI));
				if(Items.get("KAGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KAGEN));
				if(Items.get("JYOUGEN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JYOUGEN));
				if(Items.get("KIJYUNTI_HANI").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KIJYUNTI_HANI));
				if(Items.get("TANKA_KENSIN").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANKA_KENSIN));
				if(Items.get("BIKOU").equals("0"))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.BIKOU));
		    // add s.inoue 2014/02/17
			}else if (Items.get("SCREEN_CD").equals("110")){
				if(Items.get("HKNJANUM").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HKNJANUM));
				if(Items.get("JYUSHIN_SEIRI_NO").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO));
				if(Items.get("BIRTHDAY").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.BIRTHDAY));
				if(Items.get("SEX").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.SEX));
				if(Items.get("HIHOKENJYASYO_KIGOU").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU));
				if(Items.get("HIHOKENJYASYO_NO").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO));
				if(Items.get("KENSA_NENGAPI").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KENSA_NENGAPI));
				if(Items.get("KANANAME").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KANANAME));
				if(Items.get("KEKA_INPUT_FLG").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KEKA_FLG));
				if(Items.get("HANTEI_NENGAPI").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HANTEI_FLG));
				if(Items.get("NITIJI_FLG").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.NITIJI_FLG));
				if(Items.get("GETUJI_FLG").equals("0"))
					JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.GETUJI_FLG));
			}
		}
	}
	
	// add s.inoue 2013/11/14
//	private static boolean getFunctionSetting(){
//
//		ArrayList<Hashtable<String, String>> result = null;
//		boolean retflg = false;
//
//		try{
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT CHECKBOX_COLUMN, NENDO, JYUSHIN_SEIRI_NO, NAME, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO,");
//			sb.append("KENSA_NENGAPI, SEX, BIRTHDAY, KEKA_INPUT_FLG, HKNJANUM, SIHARAI_DAIKOU_BANGO, KANANAME, HANTEI_NENGAPI,");
//			sb.append("TUTI_NENGAPI, TANKA_GOUKEI, MADO_FUTAN_GOUKEI, SEIKYU_KINGAKU, JISI_KBN, HENKAN_NITIJI, METABO,");
//			sb.append("HOKENSIDO_LEVEL, KOMENTO, UPDATE_TIMESTAMP");
//			sb.append(" FROM T_SCREENCOLUMNS ");
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_SHOSAI_CODE));
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//			
//			Hashtable<String, String> item = result.get(0);
//			retflg = item.get("CHECKBOX_COLUMN").equals("1")?true:false;
//			
//			if (item.get("FUNCTION_FLG").equals("1")){
//				JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
//			}else{
//				if (!JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO))
//					JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
//			}
//			
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//		Hashtable<String, String> item = result.get(0);
//		retflg = item.get("FUNCTION_FLG").equals("1")?true:false;
//
//		return retflg;
//	}
}
