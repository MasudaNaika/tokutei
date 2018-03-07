package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;

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

	// add s.inoue 2009/11/25
	/**
	 * ユーザビリティボタン
	 */
	public void pushedUsabilityButton(ActionEvent e) {
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JUsabilityFrameCtrl());
	}
	/**
	 * システム利用者メンテナンスボタン
	 */
	public void pushedMaintenanceButton(ActionEvent e) {
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JUserMaintenanceFrameCtrl());
	}
	/**
	 * バックアップ・復元ボタン
	 */
	public void pushedBackupButton(ActionEvent e) {
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JKikanDBBackupFrameCtrl());
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
	}

// del s.inoue 2012/07/11
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//
//		int mod = keyEvent.getModifiersEx();
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_Usability.getText());
//				pushedUsabilityButton(null);break;
//			}
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);
//			break;
//// del s.inoue 2010/04/19
////		case KeyEvent.VK_F2:
////			pushedUsabilityButton(null);break;
////		case KeyEvent.VK_F2:
////			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
////				logger.info(jButton_Maintenance.getText());
////				pushedMaintenanceButton(null);
////			}
////			break;
////		case KeyEvent.VK_F3:
////			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
////				logger.info(jButton_Usability.getText());
////				pushedBackupButton(null);
////			}
////			break;
//		}
//	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

