package jp.or.med.orca.jma_tokutei.onlineupdate;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import jp.or.med.orca.jma_tokutei.onlineupdate.task.*;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.XMLDocumentUtil;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.HttpConnecter;
import jp.or.med.orca.jma_tokutei.onlineupdate.util.LastError;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsFile;

/**
 *  JMainFrame
 *
 *    オンラインアップデート基本画面
 *    Modified 2008/03/09 井上
 */
public class JMainFrame extends JFrame implements ActionListener,KeyListener {
	private static final long serialVersionUID = 1L;

	private JLabel m_guiStatus = null;
	private static final String FILENAME_UPDATE_TASK = "updatetask.xml";
	private JProgressBar m_guiProgress = null;

	// loggerの objectを作る。
	// s.inoue 20080620
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JMainFrame.class);

	/**
	 *  メイン関数
	 */
	public static void main(String[] args) {
		new JMainFrame("アップデートソフトウェア");
	}

	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JMainFrame(String strTitle) {
		super(strTitle);

		HttpConnecter.setProxyHost(PropertyUtil.getProperty("http.proxyHost"));
		HttpConnecter.setProxyPort(PropertyUtil.getProperty("http.proxyPort"));
		HttpConnecter.setNonProxyHosts(PropertyUtil
				.getProperty("http.nonProxyHosts"));
		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		// 実行ボタン
		// eidt s.inoue 2011/12/19
		// ExtendedButton update = new ExtendedButton("アップデート実行(F9)");
		ExtendedImageIcon iIconUpdate = new ExtendedImageIcon(JPath.Ico_Common_Update);
		ImageIcon iconUpdate = iIconUpdate.setStrechIcon(this, 0.7);
		ExtendedButton update= new ExtendedButton(
				"Decide","実行(D)","実行(ALT+D)",KeyEvent.VK_D,iconUpdate);

		update.setActionCommand("update");
		update.addActionListener(this);

		// 設定ボタン
		// ExtendedButton proxy = new ExtendedButton("プロキシ設定(F11)");
		ExtendedImageIcon iIconproxy = new ExtendedImageIcon(JPath.Ico_Common_Proxy);
		ImageIcon iconproxy = iIconproxy.setStrechIcon(this, 0.7);
		ExtendedButton proxy= new ExtendedButton(
				"Proxy","プロキシ(P)","プロキシ(ALT+P)",KeyEvent.VK_P,iconproxy);

		proxy.setActionCommand("proxy");
		proxy.addActionListener(this);

		// 終了ボタン
		// ExtendedButton cancel = new ExtendedButton("終了(F12)");
		ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Exit);
		ImageIcon icon = iIcon.setStrechIcon(this, 0.7);
		ExtendedButton cancel= new ExtendedButton(
				"End","終了(E)","終了(ALT+E)",KeyEvent.VK_E,icon);

		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);

		// 進行状況
		m_guiStatus = new JLabel();
		m_guiProgress = new JProgressBar(0, 10);

		m_guiProgress.setStringPainted(true);
		m_guiProgress.setIndeterminate(false);

		// 書式設定
		update.setFont(new Font("Dialog", Font.PLAIN, 12));
		proxy.setFont(new Font("Dialog", Font.PLAIN, 12));
		cancel.setFont(new Font("Dialog", Font.PLAIN, 12));

		// add s.inoue 2009/12/03
		// functionキー
		update.addKeyListener(this);
		proxy.addKeyListener(this);
		cancel.addKeyListener(this);

		m_guiStatus.setFont(new Font("Dialog", Font.PLAIN, 12));

		// パネル作成
		getContentPane().setLayout(new CardLayout(3, 3));
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		panelButton.add(update);
		panelButton.add(proxy);
		panelButton.add(cancel);

		// eidt s.inoue 2011/12/19
		// JPanel panelScreen = new JPanel(new GridLayout(3, 1));
		JPanel panelScreen = new JPanel(new GridBagLayout());

		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.weightx = 1.0D;

		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.BOTH;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.weightx = 1.0D;

		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 2;
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.weightx = 1.0D;

		panelScreen.add(m_guiStatus,gridBagConstraints1);
		panelScreen.add(m_guiProgress,gridBagConstraints2);
		panelScreen.add(panelButton,gridBagConstraints3);

		getContentPane().add("ScreenGap", panelScreen);

		// フレーム作成
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(new Dimension(600, 150));

		setLocationRelativeTo(null);

		setVisible(true);

		// 起動直後のエラー取得
		if (LastError.getErrorMessage() != null) {
			m_guiStatus.setText("Exception : " + LastError.getErrorMessage());
		}
	}

	/**
	 *  アクションイベント
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void actionPerformed(ActionEvent e) {
		try {
				if ("update".equals(e.getActionCommand())) {
					executeTaskXML();
				} else if ("proxy".equals(e.getActionCommand())) {
					new ProxySelect(this);
				} else if ("cancel".equals(e.getActionCommand())) {
					System.exit(0);
				}
			} catch (Exception err) {
				// s.inoue 20080620
				logger.warn(err.getMessage());
			}
	}

	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		try {
			switch(keyEvent.getKeyCode()){
				case KeyEvent.VK_F9:
					logger.info("アップデート実行(F9)");
					executeTaskXML();break;
				case KeyEvent.VK_F11:
					logger.info("プロキシ設定(F11)");
					new ProxySelect(this);break;
				case KeyEvent.VK_F12:
					logger.info("終了(F12)");
					System.exit(0);break;
			}
			}catch (Exception err){
				logger.error(err.getMessage());
			}

	}

	/**
	 *  アップデート実行
	 *    @param  none
	 *    @return none
	 */
	public void executeTaskXML() {
		System.out.println("executeTaskXML():start");

		String strErrorMessage = null;
		XMLDocumentUtil xmlDoc =null;
		ArrayList<AbstractTask> tasks =null;

		try {

			// add start s.inoue 20090119 バックアップ
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			JApplication.versionNumber = doc.getNodeValue("ModuleVersion", "no");
			JErrorMessage.load();

			RETURN_VALUE Ret = JErrorMessage.show("M10003", this);
			if (Ret == RETURN_VALUE.NO) {
				System.exit(0);
			}
			// add end s.inoue 20090119 バックアップ

			boolean isUpdate = false;

			// add s.inoue 20080730
			JApplication.load();

			// FDBへの接続を閉じる必要。共通で使用している為、
			// オンラインアップデート処理ではクローズして処理を行う。
			// del 20090108
			// JApplication.systemDatabase.Shutdown();

			// 更新先
			xmlDoc = new XMLDocumentUtil(
					PropertyUtil.getProperty("update.url"));

			/////////////////////////////////////////////////
			// updatetask.xmlのコピー処理を行う
			/////////////////////////////////////////////////
			String strURL = xmlDoc.getXmlFileName();
			HttpConnecter.getFile(strURL, FILENAME_UPDATE_TASK);

			// 更新数
			m_guiProgress.setValue(0);

			tasks = xmlDoc.parseModuleTask();

			int taskCount = tasks.size();
			m_guiProgress.setMaximum(taskCount);

			for (int i = 0; i < taskCount; ++i) {
				AbstractTask task = tasks.get(i);

				if (task.runTask()) {
					// アップデート完了
					isUpdate = true;
				}

				m_guiProgress.setValue(JApplication.PROGRESS_CNT + 1);JApplication.PROGRESS_CNT++;
				m_guiProgress.paintImmediately(m_guiProgress.getVisibleRect());
			}

			// FDBへの接続を閉じる必要。共通で使用している為、
			// オンラインアップデート処理終了後オープンする。
			// del 20090108
			// JApplication.systemDatabase = JConnection.ConnectSystemDatabase();

			m_guiStatus.setText(isUpdate ? "アップデート完了" : "既に最新バージョンです");

		} catch (Exception err) {
			err.printStackTrace();

			// 最終エラー取得
			strErrorMessage = LastError.getErrorMessage();

			logger.error(strErrorMessage);
			m_guiStatus.setText((strErrorMessage == null ? "アップデートに失敗しました。ログファイルを確認して下さい。" : strErrorMessage));
		}

		System.out.println("executeTaskXML():end");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
