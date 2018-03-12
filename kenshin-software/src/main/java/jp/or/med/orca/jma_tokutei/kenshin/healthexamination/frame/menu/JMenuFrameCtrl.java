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

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.imports.JImportDataFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.getuji.JOutputGetujiSearchListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JOutputNitijiSearchListFrameCtl;

import org.apache.log4j.Logger;

// sample s.inoue 2010/11/15

/**
 * メニューフレームのコントロール
 */
public class JMenuFrameCtrl extends JMenuFrame {

	private static final long serialVersionUID = 8134613049058465526L;	// edit n.ohkubo 2014/10/01　追加
	
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
//
//		}
//		@Override
//		public void keyReleased(KeyEvent e) {
//
//		}
//		@Override
//		public void keyTyped(KeyEvent e) {
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
		
		// edit n.ohkubo 2014/10/01　削除　各画面の「戻る」ボタンの処理へ移動
//		// add s.inoue 2013/11/22
//		preservGridColumns();
		
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
	
	// edit n.ohkubo 2014/10/01　削除 start　各画面の「戻る」ボタンの処理へ移動
//	// add s.inoue 2013/11/22
//	// グリッド項目（表示、非表示）
//	private void preservGridColumns(){
//		
//		// 005:健診・問診結果データ表示
//		try {
//			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//
//			StringBuilder sbwhere = new StringBuilder();
//			
//			
//			// 特定健診結果一覧
//			if (JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO)){
//				// 非表示
//				sbwhere.append(" JYUSHIN_SEIRI_NO = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" JYUSHIN_SEIRI_NO = '1'");
//			}
//			// add tanaka 2013/11/27
//			if (JApplication.flag.contains(FlagEnum_Serche.NENDO)){
//				// 非表示
//				sbwhere.append(" ,NENDO = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,NENDO = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_KIGOU)){
//				// 非表示
//				sbwhere.append(" ,HIHOKENJYASYO_KIGOU = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,HIHOKENJYASYO_KIGOU = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_NO)){
//				// 非表示
//				sbwhere.append(" ,HIHOKENJYASYO_NO = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,HIHOKENJYASYO_NO = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.KENSA_NENGAPI)){
//				// 非表示
//				sbwhere.append(" ,KENSA_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,KENSA_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.SEX)){
//				// 非表示
//				sbwhere.append(" ,SEX = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,SEX = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.BIRTHDAY)){
//				// 非表示
//				sbwhere.append(" ,BIRTHDAY = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,BIRTHDAY = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.KEKA_INPUT_FLG)){
//				// 非表示
//				sbwhere.append(" ,KEKA_INPUT_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,KEKA_INPUT_FLG = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.HKNJANUM)){
//				// 非表示
//				sbwhere.append(" ,HKNJANUM = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,HKNJANUM = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO)){
//				// 非表示
//				sbwhere.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.KANANAME)){
//				// 非表示
//				sbwhere.append(" ,KANANAME = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,KANANAME = '1'");
//			}
//			
//			if(JApplication.flag.contains(FlagEnum_Serche.HANTEI_NENGAPI)){
//				sbwhere.append(" ,HANTEI_NENGAPI = '0'");
//			}else{
//				sbwhere.append(" ,HANTEI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.TUTI_NENGAPI)){
//				// 非表示
//				sbwhere.append(" ,TUTI_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,TUTI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.CHECKBOX_COLUMN)){
//				// 非表示
//				sbwhere.append(" ,CHECKBOX_COLUMN = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,CHECKBOX_COLUMN = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.TANKA_GOUKEI)){
//				// 非表示
//				sbwhere.append(" ,TANKA_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,TANKA_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.MADO_FUTAN_GOUKEI)){
//				// 非表示
//				sbwhere.append(" ,MADO_FUTAN_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,MADO_FUTAN_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.SEIKYU_KINGAKU)){
//				// 非表示
//				sbwhere.append(" ,SEIKYU_KINGAKU = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,SEIKYU_KINGAKU = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.UPDATE_TIMESTAMP)){
//				// 非表示
//				sbwhere.append(" ,UPDATE_TIMESTAMP = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,UPDATE_TIMESTAMP = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.JISI_KBN)){
//				// 非表示
//				sbwhere.append(" ,JISI_KBN = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,JISI_KBN = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.HENKAN_NITIJI)){
//				// 非表示
//				sbwhere.append(" ,HENKAN_NITIJI = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,HENKAN_NITIJI = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.METABO)){
//				// 非表示
//				sbwhere.append(" ,METABO = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,METABO = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.HOKENSIDO_LEVEL)){
//				// 非表示
//				sbwhere.append(" ,HOKENSIDO_LEVEL = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,HOKENSIDO_LEVEL = '1'");
//			}
//			
//			if (JApplication.flag.contains(FlagEnum_Serche.KOMENTO)){
//				// 非表示
//				sbwhere.append(" ,KOMENTO = '0'");
//			}else{
//				// 表示
//				sbwhere.append(" ,KOMENTO = '1'");
//			}
//
//			//add tanaka 2013/12/03
//			StringBuilder sbwhere_hantei = new StringBuilder();
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.JYUSHIN_SEIRI_NO)){
//				// 非表示
//				sbwhere_hantei.append(" JYUSHIN_SEIRI_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" JYUSHIN_SEIRI_NO = '1'");
//			}
//			// add tanaka 2013/11/27
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.NENDO)){
//				// 非表示
//				sbwhere_hantei.append(" ,NENDO = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,NENDO = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HIHOKENJYASYO_KIGOU)){
//				// 非表示
//				sbwhere_hantei.append(" ,HIHOKENJYASYO_KIGOU = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,HIHOKENJYASYO_KIGOU = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HIHOKENJYASYO_NO)){
//				// 非表示
//				sbwhere_hantei.append(" ,HIHOKENJYASYO_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,HIHOKENJYASYO_NO = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KENSA_NENGAPI)){
//				// 非表示
//				sbwhere_hantei.append(" ,KENSA_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,KENSA_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.SEX)){
//				// 非表示
//				sbwhere_hantei.append(" ,SEX = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,SEX = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.BIRTHDAY)){
//				// 非表示
//				sbwhere_hantei.append(" ,BIRTHDAY = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,BIRTHDAY = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KEKA_INPUT_FLG)){
//				// 非表示
//				sbwhere_hantei.append(" ,KEKA_INPUT_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,KEKA_INPUT_FLG = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HKNJANUM)){
//				// 非表示
//				sbwhere_hantei.append(" ,HKNJANUM = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,HKNJANUM = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.SIHARAI_DAIKOU_BANGO)){
//				// 非表示
//				sbwhere_hantei.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KANANAME)){
//				// 非表示
//				sbwhere_hantei.append(" ,KANANAME = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,KANANAME = '1'");
//			}
//			
//			if(JApplication.flag_Hantei.contains(FlagEnum_Hantei.HANTEI_NENGAPI)){
//				sbwhere_hantei.append(" ,HANTEI_NENGAPI = '0'");
//			}else{
//				sbwhere_hantei.append(" ,HANTEI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.TUTI_NENGAPI)){
//				// 非表示
//				sbwhere_hantei.append(" ,TUTI_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,TUTI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.CHECKBOX_COLUMN)){
//				// 非表示
//				sbwhere_hantei.append(" ,CHECKBOX_COLUMN = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,CHECKBOX_COLUMN = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.TANKA_GOUKEI)){
//				// 非表示
//				sbwhere_hantei.append(" ,TANKA_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,TANKA_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.MADO_FUTAN_GOUKEI)){
//				// 非表示
//				sbwhere_hantei.append(" ,MADO_FUTAN_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,MADO_FUTAN_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.SEIKYU_KINGAKU)){
//				// 非表示
//				sbwhere_hantei.append(" ,SEIKYU_KINGAKU = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,SEIKYU_KINGAKU = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.UPDATE_TIMESTAMP)){
//				// 非表示
//				sbwhere_hantei.append(" ,UPDATE_TIMESTAMP = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,UPDATE_TIMESTAMP = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.JISI_KBN)){
//				// 非表示
//				sbwhere_hantei.append(" ,JISI_KBN = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,JISI_KBN = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HENKAN_NITIJI)){
//				// 非表示
//				sbwhere_hantei.append(" ,HENKAN_NITIJI = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,HENKAN_NITIJI = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.METABO)){
//				// 非表示
//				sbwhere_hantei.append(" ,METABO = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,METABO = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HOKENSIDO_LEVEL)){
//				// 非表示
//				sbwhere_hantei.append(" ,HOKENSIDO_LEVEL = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,HOKENSIDO_LEVEL = '1'");
//			}
//			
//			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KOMENTO)){
//				// 非表示
//				sbwhere_hantei.append(" ,KOMENTO = '0'");
//			}else{
//				// 表示
//				sbwhere_hantei.append(" ,KOMENTO = '1'");
//			}
//			
//			//add tanaka 2013/12/03
//			StringBuilder sbwhere_nitiji = new StringBuilder();
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO)){
//				// 非表示
//				sbwhere_nitiji.append(" JYUSHIN_SEIRI_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" JYUSHIN_SEIRI_NO = '1'");
//			}
//			// add tanaka 2013/11/27
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.NENDO)){
//				// 非表示
//				sbwhere_nitiji.append(" ,NENDO = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,NENDO = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU)){
//				// 非表示
//				sbwhere_nitiji.append(" ,HIHOKENJYASYO_KIGOU = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,HIHOKENJYASYO_KIGOU = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HIHOKENJYASYO_NO)){
//				// 非表示
//				sbwhere_nitiji.append(" ,HIHOKENJYASYO_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,HIHOKENJYASYO_NO = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KENSA_NENGAPI)){
//				// 非表示
//				sbwhere_nitiji.append(" ,KENSA_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,KENSA_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SEX)){
//				// 非表示
//				sbwhere_nitiji.append(" ,SEX = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,SEX = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.BIRTHDAY)){
//				// 非表示
//				sbwhere_nitiji.append(" ,BIRTHDAY = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,BIRTHDAY = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KEKA_INPUT_FLG)){
//				// 非表示
//				sbwhere_nitiji.append(" ,KEKA_INPUT_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,KEKA_INPUT_FLG = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HKNJANUM)){
//				// 非表示
//				sbwhere_nitiji.append(" ,HKNJANUM = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,HKNJANUM = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO)){
//				// 非表示
//				sbwhere_nitiji.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KANANAME)){
//				// 非表示
//				sbwhere_nitiji.append(" ,KANANAME = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,KANANAME = '1'");
//			}
//			
//			if(JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HANTEI_NENGAPI)){
//				sbwhere_nitiji.append(" ,HANTEI_NENGAPI = '0'");
//			}else{
//				sbwhere_nitiji.append(" ,HANTEI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.TUTI_NENGAPI)){
//				// 非表示
//				sbwhere_nitiji.append(" ,TUTI_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,TUTI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.CHECKBOX_COLUMN)){
//				// 非表示
//				sbwhere_nitiji.append(" ,CHECKBOX_COLUMN = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,CHECKBOX_COLUMN = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.TANKA_GOUKEI)){
//				// 非表示
//				sbwhere_nitiji.append(" ,TANKA_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,TANKA_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI)){
//				// 非表示
//				sbwhere_nitiji.append(" ,MADO_FUTAN_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,MADO_FUTAN_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SEIKYU_KINGAKU)){
//				// 非表示
//				sbwhere_nitiji.append(" ,SEIKYU_KINGAKU = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,SEIKYU_KINGAKU = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.UPDATE_TIMESTAMP)){
//				// 非表示
//				sbwhere_nitiji.append(" ,UPDATE_TIMESTAMP = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,UPDATE_TIMESTAMP = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.JISI_KBN)){
//				// 非表示
//				sbwhere_nitiji.append(" ,JISI_KBN = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,JISI_KBN = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HENKAN_NITIJI)){
//				// 非表示
//				sbwhere_nitiji.append(" ,HENKAN_NITIJI = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,HENKAN_NITIJI = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.METABO)){
//				// 非表示
//				sbwhere_nitiji.append(" ,METABO = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,METABO = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HOKENSIDO_LEVEL)){
//				// 非表示
//				sbwhere_nitiji.append(" ,HOKENSIDO_LEVEL = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,HOKENSIDO_LEVEL = '1'");
//			}
//			
//			if (JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KOMENTO)){
//				// 非表示
//				sbwhere_nitiji.append(" ,KOMENTO = '0'");
//			}else{
//				// 表示
//				sbwhere_nitiji.append(" ,KOMENTO = '1'");
//			}
//			
//			//add tanaka 2013/12/03
//			StringBuilder sbwhere_getuji = new StringBuilder();
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.JYUSHIN_SEIRI_NO)){
//				// 非表示
//				sbwhere_getuji.append(" JYUSHIN_SEIRI_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" JYUSHIN_SEIRI_NO = '1'");
//			}
//			// add tanaka 2013/11/27
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.NENDO)){
//				// 非表示
//				sbwhere_getuji.append(" ,NENDO = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,NENDO = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_KIGOU)){
//				// 非表示
//				sbwhere_getuji.append(" ,HIHOKENJYASYO_KIGOU = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,HIHOKENJYASYO_KIGOU = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HIHOKENJYASYO_NO)){
//				// 非表示
//				sbwhere_getuji.append(" ,HIHOKENJYASYO_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,HIHOKENJYASYO_NO = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KENSA_NENGAPI)){
//				// 非表示
//				sbwhere_getuji.append(" ,KENSA_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,KENSA_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEX)){
//				// 非表示
//				sbwhere_getuji.append(" ,SEX = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,SEX = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.BIRTHDAY)){
//				// 非表示
//				sbwhere_getuji.append(" ,BIRTHDAY = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,BIRTHDAY = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KEKA_INPUT_FLG)){
//				// 非表示
//				sbwhere_getuji.append(" ,KEKA_INPUT_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,KEKA_INPUT_FLG = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HKNJANUM)){
//				// 非表示
//				sbwhere_getuji.append(" ,HKNJANUM = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,HKNJANUM = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.SIHARAI_DAIKOU_BANGO)){
//				// 非表示
//				sbwhere_getuji.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KANANAME)){
//				// 非表示
//				sbwhere_getuji.append(" ,KANANAME = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,KANANAME = '1'");
//			}
//			
//			if(JApplication.flag_Getuji.contains(FlagEnum_Getuji.HANTEI_NENGAPI)){
//				sbwhere_getuji.append(" ,HANTEI_NENGAPI = '0'");
//			}else{
//				sbwhere_getuji.append(" ,HANTEI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.TUTI_NENGAPI)){
//				// 非表示
//				sbwhere_getuji.append(" ,TUTI_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,TUTI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.CHECKBOX_COLUMN)){
//				// 非表示
//				sbwhere_getuji.append(" ,CHECKBOX_COLUMN = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,CHECKBOX_COLUMN = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.TANKA_GOUKEI)){
//				// 非表示
//				sbwhere_getuji.append(" ,TANKA_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,TANKA_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.MADO_FUTAN_GOUKEI)){
//				// 非表示
//				sbwhere_getuji.append(" ,MADO_FUTAN_GOUKEI = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,MADO_FUTAN_GOUKEI = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.SEIKYU_KINGAKU)){
//				// 非表示
//				sbwhere_getuji.append(" ,SEIKYU_KINGAKU = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,SEIKYU_KINGAKU = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.UPDATE_TIMESTAMP)){
//				// 非表示
//				sbwhere_getuji.append(" ,UPDATE_TIMESTAMP = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,UPDATE_TIMESTAMP = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.JISI_KBN)){
//				// 非表示
//				sbwhere_getuji.append(" ,JISI_KBN = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,JISI_KBN = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HENKAN_NITIJI)){
//				// 非表示
//				sbwhere_getuji.append(" ,HENKAN_NITIJI = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,HENKAN_NITIJI = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.METABO)){
//				// 非表示
//				sbwhere_getuji.append(" ,METABO = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,METABO = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.HOKENSIDO_LEVEL)){
//				// 非表示
//				sbwhere_getuji.append(" ,HOKENSIDO_LEVEL = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,HOKENSIDO_LEVEL = '1'");
//			}
//			
//			if (JApplication.flag_Getuji.contains(FlagEnum_Getuji.KOMENTO)){
//				// 非表示
//				sbwhere_getuji.append(" ,KOMENTO = '0'");
//			}else{
//				// 表示
//				sbwhere_getuji.append(" ,KOMENTO = '1'");
//			}
//			
//
//			// add s.inoue 2013/12/19
//			StringBuilder sbwhere_master = new StringBuilder();
//			if (JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_CD)){
//				// 非表示
//				sbwhere_master.append(" KOUMOKU_CD = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" KOUMOKU_CD = '1'");
//			}
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_NAME)){
//				// 非表示
//				sbwhere_master.append(" ,KOUMOKU_NAME = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,KOUMOKU_NAME = '1'");
//			}
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.KENSA_HOUHOU)){
//				// 非表示
//				sbwhere_master.append(" ,KENSA_HOUHOU = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,KENSA_HOUHOU = '1'");
//			}
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.HISU_FLG)){
//				// 非表示
//				sbwhere_master.append(" ,HISU_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,HISU_FLG = '1'");
//			}
//					
//			if (JApplication.flag_Master.contains(FlagEnum_Master.DS_KAGEN)){
//				// 非表示
//				sbwhere_master.append(" ,DS_KAGEN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,DS_KAGEN = '1'");
//			}		
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.DS_JYOUGEN)){
//				// 非表示
//				sbwhere_master.append(" ,DS_JYOUGEN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,DS_JYOUGEN = '1'");
//			}		
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.JS_KAGEN)){
//				// 非表示
//				sbwhere_master.append(" ,JS_KAGEN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,JS_KAGEN = '1'");
//			}		
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.JS_JYOUGEN)){
//				// 非表示
//				sbwhere_master.append(" ,JS_JYOUGEN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,JS_JYOUGEN = '1'");
//			}		
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.TANI)){
//				// 非表示
//				sbwhere_master.append(" ,TANI = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,TANI = '1'");
//			}		
//					
//			if (JApplication.flag_Master.contains(FlagEnum_Master.KAGEN)){
//				// 非表示
//				sbwhere_master.append(" ,KAGEN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,KAGEN = '1'");
//			}		
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.JYOUGEN)){
//				// 非表示
//				sbwhere_master.append(" ,JYOUGEN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,JYOUGEN = '1'");
//			}	
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.KIJYUNTI_HANI)){
//				// 非表示
//				sbwhere_master.append(" ,KIJYUNTI_HANI = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,KIJYUNTI_HANI = '1'");
//			}			
//			
//			if (JApplication.flag_Master.contains(FlagEnum_Master.TANKA_KENSIN)){
//				// 非表示
//				sbwhere_master.append(" ,TANKA_KENSIN = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,TANKA_KENSIN = '1'");
//			}	
//
//			if (JApplication.flag_Master.contains(FlagEnum_Master.BIKOU)){
//				// 非表示
//				sbwhere_master.append(" ,BIKOU = '0'");
//			}else{
//				// 表示
//				sbwhere_master.append(" ,BIKOU = '1'");
//			}
//			
//			// add s.inoue 2014/02/17
//			StringBuilder sbwhere_kouteiMaster = new StringBuilder();
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HKNJANUM)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" HKNJANUM = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" HKNJANUM = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,JYUSHIN_SEIRI_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,JYUSHIN_SEIRI_NO = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.BIRTHDAY)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,BIRTHDAY = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,BIRTHDAY = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.SEX)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,SEX = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,SEX = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,HIHOKENJYASYO_KIGOU = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,HIHOKENJYASYO_KIGOU = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,HIHOKENJYASYO_NO = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,HIHOKENJYASYO_NO = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KENSA_NENGAPI)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,KENSA_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,KENSA_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KANANAME)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" ,KANANAME = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" ,KANANAME = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KEKA_FLG)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" KEKA_INPUT_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" KEKA_INPUT_FLG = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HANTEI_FLG)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" HANTEI_NENGAPI = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" HANTEI_NENGAPI = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.NITIJI_FLG)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" NITIJI_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" NITIJI_FLG = '1'");
//			}
//			
//			if (JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.GETUJI_FLG)){
//				// 非表示
//				sbwhere_kouteiMaster.append(" GETUJI_FLG = '0'");
//			}else{
//				// 表示
//				sbwhere_kouteiMaster.append(" GETUJI_FLG = '1'");
//			}
//			
//			StringBuilder sb = new StringBuilder();
//			sb.append("UPDATE T_SCREENCOLUMNS SET ");
//			sb.append(sbwhere.toString());
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert("005"));
//			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
//			
//			StringBuilder sb_hantei = new StringBuilder();
//			sb_hantei.append("UPDATE T_SCREENCOLUMNS SET ");
//			sb_hantei.append(sbwhere_hantei.toString());
//			sb_hantei.append(" WHERE SCREEN_CD = ");
//			sb_hantei.append(JQueryConvert.queryConvert("004"));
//			JApplication.kikanDatabase.sendNoResultQuery(sb_hantei.toString());
//			
//			StringBuilder sb_nitiji = new StringBuilder();
//			sb_nitiji.append("UPDATE T_SCREENCOLUMNS SET ");
//			sb_nitiji.append(sbwhere_nitiji.toString());
//			sb_nitiji.append(" WHERE SCREEN_CD = ");
//			sb_nitiji.append(JQueryConvert.queryConvert("006"));
//			JApplication.kikanDatabase.sendNoResultQuery(sb_nitiji.toString());
//
//			StringBuilder sb_getuji = new StringBuilder();
//			sb_getuji.append("UPDATE T_SCREENCOLUMNS SET ");
//			sb_getuji.append(sbwhere_getuji.toString());
//			sb_getuji.append(" WHERE SCREEN_CD = ");
//			sb_getuji.append(JQueryConvert.queryConvert("007"));
//			JApplication.kikanDatabase.sendNoResultQuery(sb_getuji.toString());
//			
//			StringBuilder sb_master = new StringBuilder();
//			sb_master.append("UPDATE T_SCREENCOLUMNS SET ");
//			sb_master.append(sbwhere_master.toString());
//			sb_master.append(" WHERE SCREEN_CD = ");
//			sb_master.append(JQueryConvert.queryConvert("102"));
//			JApplication.kikanDatabase.sendNoResultQuery(sb_master.toString());
//			
//// del s.inoue 2014/04/30		
////			// add s.inoue 2014/02/17
////			StringBuilder sb_kouteiMaster = new StringBuilder();
////			sb_kouteiMaster.append("UPDATE T_SCREENCOLUMNS SET ");
////			sb_kouteiMaster.append(sbwhere_kouteiMaster.toString());
////			sb_kouteiMaster.append(" WHERE SCREEN_CD = ");
////			sb_kouteiMaster.append(JQueryConvert.queryConvert("110"));
////			JApplication.kikanDatabase.sendNoResultQuery(sb_kouteiMaster.toString());
//
//			
//			// eidt s.inoue 2011/06/07
//			// JApplication.kikanDatabase.Commit();
//			JApplication.kikanDatabase.getMConnection().commit();
//		} catch (SQLException e) {
//			try {
//				JApplication.kikanDatabase.getMConnection().rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			logger.error(e.getMessage());
//		}
//	}
	// edit n.ohkubo 2014/10/01　削除 end　各画面の「戻る」ボタンの処理へ移動

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
	@Override
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

