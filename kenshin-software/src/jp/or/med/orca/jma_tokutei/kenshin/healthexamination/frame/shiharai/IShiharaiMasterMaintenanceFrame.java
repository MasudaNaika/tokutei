package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import org.openswing.swing.client.GridControl;

public interface IShiharaiMasterMaintenanceFrame{

	/* control制御 */
	public void setControl(Connection conn,
			JShiharaiMasterMaintenanceListFrameCtrl controller);

	/* リロード */
	public void reloadData();

	/* 初期化 */
	void jbInit();

	/* グリッドgetter */
	public GridControl getGrid();

}
