package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

//import java.awt.event.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;

/**
 * ユーザメンテナンス
 */
public class JUserMaintenanceFrameCtrl extends JUserMaintenanceFrame {
	private JSimpleTable model = new JSimpleTable();

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JUserMaintenanceFrameCtrl.class);

	public JUserMaintenanceFrameCtrl() {
		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_Table.add(scroll);

		model.addHeader("ユーザ名");

		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = { new JSimpleTableCellPosition(
				-1, 0) };
		model.setCellEditForbid(iSetColumnList);

		init();
		model.addKeyListener(new JEnterEvent(this, jButton_Change));

		// ダブルクリックの処理
		model.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Change));
		model.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.model);
		this.focusTraversalPolicy.addComponent(this.model);
		this.focusTraversalPolicy.addComponent(this.jButton_Add);
		this.focusTraversalPolicy.addComponent(this.jButton_Change);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		this.setTitle(ViewSettings.getAdminCommonTitleWithVersion());

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
        }
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * 追加ボタン
	 */
	public void pushedAddButton(ActionEvent e) {
		JScene.CreateDialog(this, new JRegisterUserFrameCtrl(),
				new RefreshEvent());
	}

	/**
	 * 変更ボタン
	 */
	public void pushedChangeButton(ActionEvent e) {
		int Row = model.getSelectedRow();
		if (Row < 0) {
			return;
		}

		String name = (String) model.getData(Row, 0);
		JScene.CreateDialog(
				this,
				new JRegisterUserFrameCtrl(name),
				new RefreshEvent()
			);
	}

	/**
	 * 削除ボタン
	 */
	public void pushedDeleteButton(ActionEvent e) {
		int[] Rows = model.getSelectedRows();
		if (Rows.length <= 0) {
			return;
		}

		if (model.getRowCount() == model.getSelectedRowCount()) {
			JErrorMessage.show("M7410", this);
			return;
		}

		RETURN_VALUE Ret = JErrorMessage.show("M7402", this);

		if (Ret == RETURN_VALUE.YES) {
			for (int i = 0; i < Rows.length; i++) {
				String UserName = (String) model.getData(Rows[i], 0);

				try {
					JApplication.systemDatabase
							.sendNoResultQuery("DELETE FROM T_SYS_USER WHERE USER_NAME = "
									+ JQueryConvert.queryConvert(UserName));
				} catch (Exception e2) {
					e2.printStackTrace();
					JErrorMessage.show("M7401", this);
				}
			}

			init();
		}
	}

	/**
	 * テーブルの中身を設定する
	 */
	private void init() {
		model.clearAllRow();

		ArrayList<Hashtable<String, String>> result = null;

		try {
			result = JApplication.systemDatabase
					.sendExecuteQuery("SELECT * FROM T_SYS_USER");
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M7400", this);
			return;
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String ItemArray[] = { item.get("USER_NAME") };
			model.addData(ItemArray);
		}
		// 初期選択
		if (model.getRowCount() > 0) {
			model.setRowSelectionInterval(0, 0);
		} else {
			jButton_Add.requestFocus();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (e.getSource() == jButton_Add) {
			logger.info(jButton_Add.getText());
			pushedAddButton(e);
		}
		else if (e.getSource() == jButton_Change) {
			logger.info(jButton_Change.getText());
			pushedChangeButton(e);
		}
		else if (e.getSource() == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
	}

	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Add.getText());
			pushedAddButton(null);break;
		case KeyEvent.VK_F11:
			logger.info(jButton_Change.getText());
			pushedChangeButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(null);break;
		}
	}

	/**
	 * ユーザ情報の登録画面を開いた後など、登録画面が閉じたときに呼び出されるイベント
	 */
	public class RefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			init();
		}
	}
}

//Source Code Version Tag System v1.00
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}
