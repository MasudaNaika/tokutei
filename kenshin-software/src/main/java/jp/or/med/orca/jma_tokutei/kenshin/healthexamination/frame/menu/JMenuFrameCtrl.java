package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrameCtl;
// del s.inoue 2012/06/07
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JOutputNitijiSearchListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.imports.JImportDataFrameCtrl;
// open s.inoue 2011/02/18
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrameCtl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrameCtl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrame;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameCtl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JOutputNitijiSearchListFrameCtl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListFrameCtrl;

// sample s.inoue 2010/11/15

/**
 * メニューフレームのコントロール
 */
public class JMenuFrameCtrl extends JMenuFrame {

	// add h.yoshitama 2009/09/30
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static org.apache.log4j.Logger logger = Logger.getLogger(JMenuFrameCtrl.class);

	public JMenuFrameCtrl() {

		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jButton_InputJyusinken);
		this.focusTraversalPolicy.addComponent(jButton_InputMonshinKekka);
		this.focusTraversalPolicy.addComponent(jButton_ImportData);
		this.focusTraversalPolicy.addComponent(jButton_ShowHantei);
		this.focusTraversalPolicy.addComponent(jButton_OutputNitiji);
		this.focusTraversalPolicy.addComponent(jButton_OutputGetuji);
		this.focusTraversalPolicy.addComponent(jButton_MasterMaintenance);
		this.focusTraversalPolicy.addComponent(jButton_SystemMaintenance);
		this.focusTraversalPolicy.addComponent(jButton_Version);
		this.focusTraversalPolicy.addComponent(jButton_ReturnLogin);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		// 権限によってボタンの変更
		if (JApplication.authority == JApplication.authorityAdmin) {
			jButton_SystemMaintenance.setEnabled(true);
		}

		if (JApplication.authority == JApplication.authorityUser) {
			jButton_SystemMaintenance.setEnabled(false);
		}

		/* Added 2008/03/09 若月 Frame のタイトルに、
		 * アプリケーションバージョン、機関番号、機関名称を表示 */
		/* --------------------------------------------------- */
		StringBuffer buffer = new StringBuffer();

		buffer.append(ViewSettings.getTokuteFrameTitle());
		buffer.append(" (Version ");
		buffer.append(JApplication.versionNumber);
		buffer.append(") [");

		if (JApplication.kikanName != null && !JApplication.kikanName.isEmpty()) {
			buffer.append(JApplication.kikanName);
			buffer.append(" - ");
		}

		buffer.append(JApplication.kikanNumber);

		buffer.append("]");

		String title = buffer.toString();

		this.setTitle(title);

		ViewSettings.setTokuteFrameTitleWithKikanInfomation(title);
		JSoftware.getSplashFrame().addComponentListener(new ComponentAdapter() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				JMenuFrameCtrl.this.jButton_Version.setSelected(false);
			}
		});

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JSoftware.getSplashFrame().hideSplashWindow();
			}
		});

	}

// open s.inoue 2011/02/18
//	/* ボタンアクション用内部クラス */
//	class ListFrame_closeButton_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
//	  JKenshinKekkaSearchListFrame adaptee;
//
//		  ListFrame_closeButton_actionAdapter(JKenshinKekkaSearchListFrame adaptee) {
//		    this.adaptee = adaptee;
//		  }
//		  // buttonアクション
//		  public void actionPerformed(ActionEvent e) {
//		   	Object source = e.getSource();
//
//		   	/* 閉じる */
//			if (source == jButton_InputMonshinKekka){
//				adaptee.closeButtton_actionPerformed(e);
//			}
//		  }
//		@Override
//		public void keyPressed(KeyEvent e) {
//			// TODO 自動生成されたメソッド・スタブ
//
//		}
//		@Override
//		public void keyReleased(KeyEvent e) {
//			// TODO 自動生成されたメソッド・スタブ
//
//		}
//		@Override
//		public void keyTyped(KeyEvent e) {
//			// TODO 自動生成されたメソッド・スタブ
//
//		}
//	}

	public void pushedJyusinkenButton(ActionEvent e) {
		logger.info(jButton_InputJyusinken.getText());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JKojinRegisterFrameCtrl());
	}

	public void pushedInputButton(ActionEvent e) {
		logger.info(jButton_InputMonshinKekka.getText());

//		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
//				new JKekkaListFrameCtrl());

	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
// open s.inoue 2011/02/18
		JKenshinKekkaSearchListFrameCtl ctl = new JKenshinKekkaSearchListFrameCtl(
				JApplication.kikanDatabase.getMConnection());
		JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	public void pushedImportButton(ActionEvent e) {
		logger.info(jButton_ImportData.getText());

		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JImportDataFrameCtrl());
	}

	public void pushedHanteiButton(ActionEvent e) {
		logger.info(jButton_ShowHantei.getText());
//		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
//				new JSearchResultListFrameCtrl());
	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
		JHanteiSearchResultListFrameCtl ctl = new JHanteiSearchResultListFrameCtl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	public void pushedNitijiButton(ActionEvent e) {
		logger.info(jButton_OutputGetuji.getText());
//		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
//				new JOutputNitijiFrameCtrl());
//	    try {
//			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//		} catch (SQLException ex) {
//			logger.warn(ex.getMessage());
//		}
// open s.inoue 2011/02/18
		JOutputNitijiSearchListFrameCtl ctl = new JOutputNitijiSearchListFrameCtl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	public void pushedGetujiButton(ActionEvent e) {
		logger.info(jButton_OutputGetuji.getText());
//		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
//				new JOutputGetujiFrameCtrl());
	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
// open s.inoue 2011/02/18
		JOutputGetujiSearchListFrameCtl ctl = new JOutputGetujiSearchListFrameCtl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	public void pushedMasterButton(ActionEvent e) {
		logger.info(jButton_MasterMaintenance.getText());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JMasterMaintenanceFrameCtrl());
	}

	public void pushedSystemButton(ActionEvent e) {
		logger.info(jButton_SystemMaintenance.getText());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,
				new JSystemMaintenanceFrameCtrl());
	}

	public void pushedEndButton(ActionEvent e) {
		logger.info(jButton_End.getText());
		Connection con = null;
		try {
			con = JApplication.kikanDatabase.getMConnection();
			if (con != null)
				con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			con = null;
		}
		System.exit(0);
	}

	/**
	 * バージョンボタン
	 */
	public void pushedVersionButton(ActionEvent e) {
		if (jButton_Version.isSelected()) {
			// add s.inoue 20090113 reload処理
			JSoftware.reloadSplashFlame();
			JSoftware.getSplashFrame().addComponentListener(new ComponentAdapter() {

				@Override
				public void componentHidden(ComponentEvent arg0) {
					JMenuFrameCtrl.this.jButton_Version.setSelected(false);
				}
			});

			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowDeactivated(WindowEvent arg0) {
					JSoftware.getSplashFrame().hideSplashWindow();
				}
			});
			JSoftware.getSplashFrame().showSplashWindow();
		} else {
			JSoftware.getSplashFrame().hideSplashWindow();
		}
	}

//	/**
//	 * 「ログイン画面に戻る」ボタン
//	 */
//	public void pushedReturnLoginButton(ActionEvent e) {
//		// データベースとの接続を解除
//		JApplication.kikanDatabase = null;
//
//		JApplication.kikanNumber = null;
//		JApplication.userID = null;
//		JApplication.password = null;
//		JApplication.authority = 0;
//
//		JApplication.useORCA = false;
//		JApplication.orcaSetting = null;
//
//		JApplication.kikanName = null;
//
//		/*
//		 * ロックファイルを削除する。
//		 * 削除に失敗しても、ログイン画面に戻る。
//		 */
//		boolean isDeleted = JExecLocker.getLockFile().delete();
//		if (! isDeleted) {
//			JErrorMessage.show("M2000", this);
//		}
//
//		dispose();
//
//		JScene.ChangeScene(new JLoginFrameCtrl());
//	}
	/* 再ログイン */
	private void pushedReturnLoginButton(ActionEvent e) {
		try{
			// windowを全て閉じるLookAndFeel
			java.awt.Window[] win =this.getWindows();
			for (int i = 0; i < win.length; i++) {
				win[i].dispose();
			}

			/*
			 * ロックファイルを削除する。
			 * 削除に失敗しても、ログイン画面に戻る。
			 */
			// eidt s.inoue 2012/11/01
			boolean isDeleted = false;
			if (JExecLocker.getLockFile() != null){
				isDeleted = JExecLocker.getLockFile().delete();
				if (! isDeleted) {
					JErrorMessage.show("M2000", this);
				}
			}
			JScene.ChangeScene(new JLoginFrameCtrl());

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		} else if (e.getSource() == jButton_ShowHantei) {
			logger.info(jButton_ShowHantei.getText());
			pushedHanteiButton(e);
		} else if (e.getSource() == jButton_OutputNitiji) {
			logger.info(jButton_OutputNitiji.getText());
			pushedNitijiButton(e);
		} else if (e.getSource() == jButton_OutputGetuji) {
			logger.info(jButton_OutputGetuji.getText());
			pushedGetujiButton(e);
		} else if (e.getSource() == jButton_InputJyusinken) {
			logger.info(jButton_InputJyusinken.getText());
			pushedJyusinkenButton(e);
		} else if (e.getSource() == jButton_MasterMaintenance) {
			logger.info(jButton_MasterMaintenance.getText());
			pushedMasterButton(e);
		} else if (e.getSource() == jButton_SystemMaintenance) {
			logger.info(jButton_SystemMaintenance.getText());
			pushedSystemButton(e);
		} else if (e.getSource() == jButton_InputMonshinKekka) {
			logger.info(jButton_InputMonshinKekka.getText());
			pushedInputButton(e);
		} else if (e.getSource() == jButton_ImportData) {
			logger.info(jButton_ImportData.getText());
			pushedImportButton(e);
		} else if (e.getSource() == jButton_Version) {
			logger.info(jButton_Version.getText());
			pushedVersionButton(e);
		} else if (e.getSource() == jButton_ReturnLogin) {
			logger.info(jButton_ReturnLogin.getText());
			pushedReturnLoginButton(e);
		}
	}

	// add s.inoue 2009/12/02
	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int mod = keyEvent.getModifiersEx();
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_InputJyusinken.getText());
				pushedJyusinkenButton(null);
				break;
			}
			logger.info(jButton_End.getText());
			pushedEndButton(null);
			break;
		case KeyEvent.VK_F2:
// edit s.inoue 2010/04/22
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_InputMonshinKekka.getText());
//				pushedInputButton(null);
//				break;
//			}
			logger.info(jButton_ReturnLogin.getText());
			pushedReturnLoginButton(null);
			break;
//		case KeyEvent.VK_F3:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_ImportData.getText());
//				pushedImportButton(null);
//			}
//			break;
//		case KeyEvent.VK_F4:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_ShowHantei.getText());
//				pushedHanteiButton(null);
//			}
//			break;
//		case KeyEvent.VK_F5:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_OutputNitiji.getText());
//				pushedNitijiButton(null);
//			}
//			break;
//		case KeyEvent.VK_F6:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_OutputGetuji.getText());
//				pushedGetujiButton(null);
//			}
//			break;
//		case KeyEvent.VK_F7:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_MasterMaintenance.getText());
//				pushedMasterButton(null);
//			}
//			break;
//		case KeyEvent.VK_F9:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				if (jButton_SystemMaintenance.isEnabled()){
//					logger.info(jButton_SystemMaintenance.getText());
//					pushedSystemButton(null);
//				}
//			}
//			break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Version.getText());
			if (jButton_Version.isSelected()){
				jButton_Version.setSelected(false);
			}else{
				jButton_Version.setSelected(true);
			}
			pushedVersionButton(null);break;

// edit s.inoue 2010/04/01
//		case KeyEvent.VK_F3:
//			pushedJyusinkenButton(null);break;
//		case KeyEvent.VK_F4:
//			pushedInputButton(null);break;
//		case KeyEvent.VK_F5:
//			pushedImportButton(null);break;
//		case KeyEvent.VK_F6:
//			pushedHanteiButton(null);break;
//		case KeyEvent.VK_F7:
//			pushedNitijiButton(null);break;
//		case KeyEvent.VK_F8:
//			pushedGetujiButton(null);break;
//		case KeyEvent.VK_F9:
//			pushedMasterButton(null);break;
//		case KeyEvent.VK_F11:
//			if (jButton_SystemMaintenance.isEnabled()){
//				pushedSystemButton(null);
//			}
//			break;
		}
	}

}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

