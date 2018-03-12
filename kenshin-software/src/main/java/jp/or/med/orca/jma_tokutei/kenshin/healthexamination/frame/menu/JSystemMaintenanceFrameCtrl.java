package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JKikanDBBackupFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JKikanLogListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JUsabilityFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JUserMaintenanceListFrameCtl;

import org.apache.log4j.Logger;

/**
 * システムメンテナンスメニューのフレームのコントロール
 */
public class JSystemMaintenanceFrameCtrl extends JSystemMaintenanceFrame {

	private static Logger logger = Logger.getLogger(JSystemMaintenanceFrameCtrl.class);
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	public JSystemMaintenanceFrameCtrl() {
	    functionListner();
	}

	private void functionListner(){
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jButton_Usability);
		this.focusTraversalPolicy.addComponent(this.jButton_Usability);
		this.focusTraversalPolicy.addComponent(this.jButton_Maintenance);
		this.focusTraversalPolicy.addComponent(this.jButton_Backup);
		// add s.inoue 2013/11/07
//		this.focusTraversalPolicy.addComponent(this.jButton_Log);
		this.focusTraversalPolicy.addComponent(this.jButton_End);

		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * ユーザビリティボタン
	 */
	public void pushedUsabilityButton(ActionEvent e) {
	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JUsabilityFrameCtrl());
	}
	/**
	 * システム利用者メンテナンスボタン
	 */
	public void pushedMaintenanceButton(ActionEvent e) {
//		JScene.CreateDialog(this,
//				new JUserMaintenanceFrameCtrl());
	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
		// open s.inoue 2011/02/18
		JUserMaintenanceListFrameCtl ctl = new JUserMaintenanceListFrameCtl(
				JApplication.kikanDatabase.getMConnection());
		JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}
	/**
	 * バックアップ・復元ボタン
	 */
	public void pushedBackupButton(ActionEvent e) {
	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
		JScene.CreateDialog(this,
				new JKikanDBBackupFrameCtrl());
	}

	// add s.inoue 2013/11/07
	/**
	 * ログファイル管理ボタン
	 */
	public void pushedLogButton(ActionEvent e) {
		// edit s.inoue 2014/04/25
		JKikanLogListFrameCtl ctl = new JKikanLogListFrameCtl();
//		JProgressListFrameCtl ctl = new JProgressListFrameCtl(
//				JApplication.kikanDatabase.getMConnection());
		JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		if (source == jButton_Usability) {
			logger.info(jButton_Usability.getText());
			pushedUsabilityButton(e);
		}
		if (source == jButton_Maintenance) {
			logger.info(jButton_Maintenance.getText());
			pushedMaintenanceButton(e);
		}
		if (source == jButton_Backup) {
			logger.info(jButton_Backup.getText());
			pushedBackupButton(e);
		}
		// add s.inoue 2013/11/07
		if (source == jButton_Log) {
			logger.info(jButton_Log.getText());
			pushedLogButton(e);
		}
	}


	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int mod = keyEvent.getModifiersEx();
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_Usability.getText());
				pushedUsabilityButton(null);break;
			}
			logger.info(jButton_End.getText());
			pushedEndButton(null);
			break;
		}
	}
}
