package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;

import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;

import org.openswing.swing.client.GridControl;
import org.openswing.swing.form.client.FormController;
import org.openswing.swing.message.receive.java.ValueObject;

public interface IKenshinKekkaSearchListFrame{

	/* control制御 */
	public void setControl(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller);

	/* リロード */
	public void reloadData();

	/* 初期化 */
	void jbInit();

	/* グリッドgetter */
	public FormController getGrid();

	/* JFramegetter*/
	public void setJFrameeDitable();
}