package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import javax.swing.JFrame;

import org.openswing.swing.client.GridControl;
import org.openswing.swing.message.receive.java.ValueObject;

public interface IHokenjyaMasterMaintenanceDetailFrame{

	/* control制御 */
	public void setControl(Connection conn,
			JHokenjyaMasterMaintenanceListFrameCtrl controller);

	/* リロード */
	public void reloadData();

	/* 初期化 */
	void jbInit();

	/* グリッドgetter */
	public GridControl getGrid();

	/* JFramegetter*/
	public void setJFrameeDitable();
}