package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.admin.JAdminSoftware;

import org.apache.log4j.Logger;

/**
 * メニュー
 */
public class JMenuFrameCtrl extends JMenuFrame
{
	private static final long serialVersionUID = 392792505923527679L;	// edit n.ohkubo 2015/08/01　追加

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JMenuFrameCtrl.class);

	public JMenuFrameCtrl()
	{
		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());

		JAdminSoftware.getSplashFrame().addComponentListener(new ComponentAdapter(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				JMenuFrameCtrl.this.jButton_Version.setSelected(false);
			}
		});

		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JAdminSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.addComponent(this.jButton_KikanMaintenance);
		this.focusTraversalPolicy.addComponent(this.jButton_KanriUserMaintenance);
		this.focusTraversalPolicy.addComponent(this.jButton_DBBackup);
		this.focusTraversalPolicy.addComponent(this.jButton_Version);
		this.focusTraversalPolicy.addComponent(this.jButton_BackLogin);
		this.focusTraversalPolicy.addComponent(jButton_End);
		this.jButton_KikanMaintenance.requestFocus();
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		System.exit(0);
	}

	/**
	 * 複数機関メンテナンスボタン
	 */
	public void pushedHukusuuButton( ActionEvent e )
	{
		JScene.CreateDialog(this, new JKikanMaintenanceFrameCtrl());
	}

	/**
	 * ユーザメンテナンスボタン
	 */
	public void pushedKanriButton( ActionEvent e )
	{
//		JScene.CreateDialog(this, new JUserMaintenanceListFrameCtrl());
	    try {
			JApplication.systemDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
		JUserMaintenanceListFrameCtrl ctl = new JUserMaintenanceListFrameCtrl(
				JApplication.systemDatabase.getMConnection());
		JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	/**
	 * データベースバックアップボタン
	 */
	public void pushedDBButton( ActionEvent e )
	{
		JScene.CreateDialog(this, new JDBBackupFrameCtrl());
	}

	/**
	 * バージョンボタン
	 */
	public void pushedVersionButton( ActionEvent e )
	{
		if (jButton_Version.isSelected()) {
			JAdminSoftware.getSplashFrame().showSplashWindow();
		}
		else {
			JAdminSoftware.getSplashFrame().hideSplashWindow();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// add s.inoue 2009/12/17
		Object source = e.getSource();
		if(source == jButton_End)
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		else if(source == jButton_KikanMaintenance)
		{
			logger.info(jButton_KikanMaintenance.getText());
			pushedHukusuuButton( e );
		}
		else if(source == jButton_KanriUserMaintenance)
		{
			logger.info(jButton_KanriUserMaintenance.getText());
			pushedKanriButton( e );
		}
		else if(source == jButton_DBBackup)
		{
			logger.info(jButton_DBBackup.getText());
			pushedDBButton( e );
		}
		else if(source == jButton_Version)
		{
			logger.info(jButton_Version.getText());
			pushedVersionButton( e );
		}
		else if(source == jButton_BackLogin)
		{
			logger.info(jButton_BackLogin.getText());
			backLogin();
		}
		// edit n.ohkubo 2015/08/01　追加　start
		else if (source == jButton_NetworkDBConnection) {
			logger.info(jButton_NetworkDBConnection.getText());
			pushedNetworkDBConnectionButton();
		}
		// edit n.ohkubo 2015/08/01　追加　end
	}

	/* ログイン画面へ */
	private void backLogin(){
		JApplication.userID = null;
		JApplication.password = null;

		/* ロックを解除する。 */
		File lockFile = JExecLocker.getLockFile();
		if (lockFile != null ) {
			lockFile.delete();
		}

		JScene.ChangeScene(new JLoginFrameCtrl());
	}
	
	// edit n.ohkubo 2015/08/01　追加　start
	/**
	 * 接続先DB情報メンテナンスボタン押下処理
	 */
	private void pushedNetworkDBConnectionButton() {
		JScene.CreateDialog(this, new JNetworkDBConnectionFrame());
	}
	// edit n.ohkubo 2015/08/01　追加　end

	// add s.inoue 2009/12/04
	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int mod = keyEvent.getModifiersEx();

		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedHukusuuButton(null);
				break;
			}
			logger.info(jButton_End.getText());
			pushedEndButton(null);
			break;
		case KeyEvent.VK_F2:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedKanriButton(null);
				break;
			}
			logger.info(jButton_BackLogin.getText());
			backLogin();
			break;
//		case KeyEvent.VK_F3:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_DBBackup.getText());
//				pushedDBButton(null);
//			}
//			break;


// edit s.inoue 2010/04/01
//		case KeyEvent.VK_F3:
//			pushedHukusuuButton(null);break;
//		case KeyEvent.VK_F4:
//			pushedKanriButton(null);break;
//		case KeyEvent.VK_F5:
//			pushedDBButton(null);break;
//		case KeyEvent.VK_F2:
//			backLogin();break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Version.getText());

			if (jButton_Version.isSelected()){
				jButton_Version.setSelected(false);
			}else{
				jButton_Version.setSelected(true);
			}
			pushedVersionButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

