package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.sql.*;
import java.awt.Component;
import java.awt.event.*;
import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.execlocker.JExecLocker;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.JChangeKikanInformationFrameCtrl;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;

import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JKekkaTeikeiMaintenanceListFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JNayoseMaintenanceListFrameCtl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceListFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kikan.*;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.xJHokenjyaMasterMaintenanceDetailCtl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.master.JKekkaTeikeiMaintenanceListFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.master.JKenshinPatternMaintenanceListFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.master.JNayoseMaintenanceListFrameCtl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.*;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken.JShokenMasterMaintenanceListFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.JKenshinMasterMaintenanceListFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen.JKeinenMasterMaintenanceListFrameCtrl;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.*;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen.JKeinenMasterMaintenanceListFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.JKenshinMasterMaintenanceListFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shoken.JShokenMasterMaintenanceListFrameCtrl;


/**
 * マスタメンテナンスのメニューフレームのコントロール
 */
public class JMasterMaintenanceFrameCtrl extends JMasterMaintenanceFrame
{
	private static Logger logger = Logger.getLogger(JMasterMaintenanceFrameCtrl.class);
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	public JMasterMaintenanceFrameCtrl()
	{
		functionListner();

		// openswing s.inoue 2010/12/16
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void functionListner(){
		Component comp = null;
		this.focusTraversalPolicy = new JFocusTraversalPolicy();

		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		// eidt s.inoue 2011/05/10
		dispose();
		JScene.ChangeScene(new JMenuFrameCtrl());
	}

	/**
	 * 機関情報メンテナンスボタン
	 */
	public void pushedKikanButton( ActionEvent e )
	{
		JScene.CreateDialog(this, new JChangeKikanInformationFrameCtrl(
				JApplication.systemDatabase,JApplication.kikanNumber));
	}

	/**
	 * 健診パターンメンテナンスボタン
	 */
	public void pushedPatternButton( ActionEvent e )
	{

// open s.inoue 2011/02/18
//		JScene.CreateDialog(this, new JKenshinPatternMaintenanceListFrameCtrl());
//		try {
//			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//		} catch (SQLException e1) {
//		}

		JKenshinpatternMasterMaintenanceListFrameCtrl ctl = new JKenshinpatternMasterMaintenanceListFrameCtrl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	/**
	 * 健診項目マスタメンテナンスボタン
	 */
	public void pushedKoumokuButton( ActionEvent e )
	{

// open s.inoue 2011/02/18
//		JScene.CreateDialog(this, new JKenshinMasterMaintenanceFrameCtrl());
		JKenshinMasterMaintenanceListFrameCtrl ctl = new JKenshinMasterMaintenanceListFrameCtrl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,ctl.getGridControl()
				);
	}

	/**
	 * 保険者情報メンテナンスボタン
	 */
	public void pushedHokenjyaButton( ActionEvent e )
	{
//		JScene.CreateDialog(this, new JHokenjyaMasterMaintenanceListFrameCtrl());
// open s.inoue 2011/02/18
			JHokenjyaMasterMaintenanceListFrameCtrl ctl = new JHokenjyaMasterMaintenanceListFrameCtrl(
					JApplication.kikanDatabase.getMConnection());
			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
					this,ctl.getGridControl()
					);
	}

	/**
	 * 支払代行メンテナンスボタン
	 */
	public void pushedShiharaiButton( ActionEvent e )
	{
//		JScene.CreateDialog(this, new JShiharaiMasterMaintenanceListFrameCtrl());

// open s.inoue 2011/02/18
		JShiharaiMasterMaintenanceListFrameCtrl ctl = new JShiharaiMasterMaintenanceListFrameCtrl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	/**
	 * 名寄せメンテナンスボタン
	 */
	public void pushedNayoseButton( ActionEvent e )
	{
//		JScene.CreateDialog(this, new JNayoseMaintenanceListFrameCtl());
// open s.inoue 2011/02/18
	    try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException e1) {
		}
		// 経年マスタ
		JKeinenMasterMaintenanceListFrameCtrl ctl = new JKeinenMasterMaintenanceListFrameCtrl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	// add s.inoue 20090317
	/**
	 * 定型文メンテナンスボタン
	 */
	public void pushedTeikeiButton( ActionEvent e )
	{
//		JScene.CreateDialog(this, new JKekkaTeikeiMaintenanceListFrameCtrl());
// open s.inoue 2011/02/18
		// 所見マスタ
		JShokenMasterMaintenanceListFrameCtrl ctl = new JShokenMasterMaintenanceListFrameCtrl(
				JApplication.kikanDatabase.getMConnection());
		jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
				this,
				ctl.getGridControl()
				);
	}

	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		if( e.getSource() == jButton_KikanMaintenance )
		{
			logger.info(jButton_KikanMaintenance.getText());
			pushedKikanButton( e );
		}
		if( e.getSource() == jButton_KenshinPatternMaintenance )
		{
			logger.info(jButton_KenshinPatternMaintenance.getText());
			pushedPatternButton( e );
		}
		if( e.getSource() == jButton_KenshinKoumokuMaintenance )
		{
			logger.info(jButton_KenshinKoumokuMaintenance.getText());
			pushedKoumokuButton( e );
		}
		if( e.getSource() == jButton_HokenjyaMaintenance )
		{
			logger.info(jButton_HokenjyaMaintenance.getText());
			pushedHokenjyaButton( e );
		}
		if( e.getSource() == jButton_ShiharaiMaintenance )
		{
			logger.info(jButton_ShiharaiMaintenance.getText());
			pushedShiharaiButton( e );
		}
		if( e.getSource() == jButton_NayoseMaintenance )
		{
			logger.info(jButton_NayoseMaintenance.getText());
			pushedNayoseButton( e );
		}
		if( e.getSource() == jButton_TeikeiMaintenance )
		{
			logger.info(jButton_TeikeiMaintenance.getText());
			pushedTeikeiButton( e );
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

		int mod = keyEvent.getModifiersEx();
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KenshinKoumokuMaintenance.getText());
				pushedKoumokuButton(null);
				break;
			}
			logger.info(jButton_End.getText());
			pushedEndButton(null);
			break;
		case KeyEvent.VK_F2:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedHokenjyaButton(null);
			}
			break;
		case KeyEvent.VK_F3:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KenshinPatternMaintenance.getText());
				pushedPatternButton(null);
			}
			break;
		case KeyEvent.VK_F4:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_ShiharaiMaintenance.getText());
				pushedShiharaiButton(null);
			}
			break;
		case KeyEvent.VK_F5:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_KikanMaintenance.getText());
				pushedKikanButton(null);
			}
			break;
		case KeyEvent.VK_F6:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_NayoseMaintenance.getText());
				pushedNayoseButton(null);
			}
			break;
		case KeyEvent.VK_F7:
			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
				logger.info(jButton_TeikeiMaintenance.getText());
				pushedTeikeiButton(null);
			}
			break;
		}
	}

}
