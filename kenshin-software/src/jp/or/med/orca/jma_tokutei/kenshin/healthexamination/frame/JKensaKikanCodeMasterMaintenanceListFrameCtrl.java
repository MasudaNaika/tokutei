package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * 健診機関コードマスタメンテナンス
 */
public class JKensaKikanCodeMasterMaintenanceListFrameCtrl extends
		JKensaKikanCodeMasterMaintenanceListFrame {
	private JSimpleTable model = new JSimpleTable();

	public JKensaKikanCodeMasterMaintenanceListFrameCtrl() {
		/* Added 2008/03/10 若月 */
		/* --------------------------------------------------- */
		model.setPreferedColumnWidths(new int[] { 200, 200 });
		/* --------------------------------------------------- */

		model.addHeader("検査センターコード");
		model.addHeader("検査センター名称");

		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_MainArea.add(scroll);

		refreshTable();

		// エンターキーの設定
		// jButton_End.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Add.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Edit.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Delete.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		model
				.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(
						this, jButton_Edit));

		// ダブルクリックの処理
		model
				.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent(
						this, jButton_Edit));

		// 初期選択
		if (model.getRowCount() > 0) {
			model.setRowSelectionInterval(0, 0);
		} else {
			jButton_Add.requestFocus();
		}
	}

	/**
	 * テーブルのリフレッシュを行う
	 */
	public void refreshTable() {
		ArrayList<Hashtable<String, String>> Result = null;
		Hashtable<String, String> ResultItem = null;
		String Query = new String(
				"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME FROM T_KENSACENTER_MASTER");

		// テーブルの削除
		model.clearAllRow();

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] row = new String[2];
		for (int i = 0; i < Result.size(); i++) {
			ResultItem = Result.get(i);
			row[0] = ResultItem.get("KENSA_CENTER_CD");
			row[1] = ResultItem.get("CENTER_NAME");
			model.addData(row);
		}
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * 検査センターの削除
	 */
	public void pushedDeleteButton(ActionEvent e) {
		String insertQuery = null;
		int selectedRowCount = model.getSelectedRowCount();

		if (selectedRowCount > 0) {
			RETURN_VALUE Ret = jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
					.show("M5601", this);

			if (Ret == RETURN_VALUE.YES) {
				for (int i = 0; i < selectedRowCount; i++) {
					String DeleteItem = (String) model.getData(model
							.getSelectedRows()[i], 0);
					insertQuery = new String(
							"DELETE FROM T_KENSACENTER_MASTER WHERE KENSA_CENTER_CD = "
									+ JQueryConvert.queryConvert(DeleteItem));

					try {
						String confirmQuery = new String(
								"SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE KENSA_CENTER_CD = "
										+ JQueryConvert
												.queryConvert(DeleteItem));

						// 特定テーブルにおいて該当の検査センターコードが使用されていなかったら削除を行う
						if (!JConnection.IsExistDatabaseItem(
								JApplication.kikanDatabase, confirmQuery)) {
							JApplication.kikanDatabase
									.sendNoResultQuery(insertQuery);
						} else {
							jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
									.show("M5603", this);
						}
					} catch (SQLException f) {
						f.printStackTrace();

						jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
								.show("M5602", this);

						return;
					}
				}

				refreshTable();
			}
		}
	}

	/**
	 * 追加ボタン
	 */
	public void pushedAddButton(ActionEvent e) {
		int maxCodeNumber = GetKensaCenterNumber();

		String centerCode = String.valueOf(maxCodeNumber);
		String centerName = "～ここに新規登録する検査センターの名称を入力してください～";

		jp.or.med.orca.jma_tokutei.common.scene.JScene
				.CreateDialog(
						this,
						new JKensaKikanCodeMasterMaintenanceEditFrameCtrl(
								centerCode,
								centerName,
								JKensaKikanCodeMasterMaintenanceEditFrameCtrl.ADD_MODE),
						new WindowRefreshEvent());
	}

	/**
	 * 変更ボタン
	 */
	public void pushedEditButton(ActionEvent e) {
		if (model.getSelectedRowCount() > 0) {
			String centerCode = (String) model.getData(model.getSelectedRow(),
					0);
			String centerName = (String) model.getData(model.getSelectedRow(),
					1);

			jp.or.med.orca.jma_tokutei.common.scene.JScene
					.CreateDialog(
							this,
							new JKensaKikanCodeMasterMaintenanceEditFrameCtrl(
									centerCode,
									centerName,
									JKensaKikanCodeMasterMaintenanceEditFrameCtrl.CHANGE_MODE),
							new WindowRefreshEvent());
		}
	}

	/**
	 * 検査センターコードの空き番号の取得
	 */
	private int GetKensaCenterNumber() {
		ArrayList<Hashtable<String, String>> Items;
		try {
			Items = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT DISTINCT KENSA_CENTER_CD FROM T_KENSACENTER_MASTER");
		} catch (SQLException e) {
			e.printStackTrace();
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show(
					"M5604", this);
			return -1;
		}

		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			boolean FindFlag = false;

			for (int j = 0; j < Items.size(); j++) {
				if (Items.get(j).get("KENSA_CENTER_CD").equals(
						String.valueOf(i))) {
					FindFlag = true;
				}
			}

			// trueでなければ空きがあるとする。
			if (FindFlag == false) {
				return i;
			}
		}

		return -1;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			pushedEndButton(e);
		}

		else if (source == jButton_Delete) {
			pushedDeleteButton(e);
		}

		else if (source == jButton_Add) {
			pushedAddButton(e);
		}

		else if (source == jButton_Edit) {
			pushedEditButton(e);
		}
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			// テーブルの再読み込みを行う
			refreshTable();
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

