package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import org.openswing.swing.client.GridControl;

public interface IKenshinpatternMasterMaintenanceFrame{

	/* control制御 */
	public void setControl(Connection conn,
			JKenshinpatternMasterMaintenanceListFrameCtrl controller);

	/* リロード */
	public void reloadData();

	/* 初期化 */
	void jbInit();

	/* グリッドgetter */
	public GridControl getGrid();

}
