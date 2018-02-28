package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

public class JKensaKikanCodeMasterMaintenanceEditFrameCtrl extends
		JKensaKikanCodeMasterMaintenanceEditFrame {
	public final static int ADD_MODE = 1;// 新規センター追加の場合

	public final static int CHANGE_MODE = 2;// 編集の場合

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JKensaKikanCodeMasterMaintenanceEditFrameCtrl.class);

	JSimpleTable model = new JSimpleTable();

	JKensaKikanCodeMasterMaintenanceEditFrameData validatedData = new JKensaKikanCodeMasterMaintenanceEditFrameData();

	/**
	 * コンストラクタ 検査センター自体の追加、項目の編集によって処理を分ける
	 */
	public JKensaKikanCodeMasterMaintenanceEditFrameCtrl(String centerCode,
			String centerName, int mode) {
		jTextField_CenterCode.setText(centerCode);
		jTextField_CenterName.setText(centerName);
		jTextField_CenterCode.setEditable(false);
		jTextField_CenterName.setEditable(false);

		// 新規追加の際は名称の変更ができるように
		if (mode == ADD_MODE) {
			jTextField_CenterName.setEditable(true);
		}

		// テーブルの初期化
		model.addHeader("検査センター項目コード");
		model.addHeader("項目コード（JLAC10）");
		model.addHeader("項目名");

		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_TableArea.add(scroll);

		refreshTable();

		// エンターキーの処理
		// jButton_Register.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_Delete.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		// jButton_End.addKeyListener(new
		// jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));

		// 初期選択
		jTextField_CenterName.requestFocus();
	}

	/**
	 * テーブルリフレッシュ
	 */
	public void refreshTable() {
		ArrayList<Hashtable<String, String>> Result1 = new ArrayList<Hashtable<String, String>>();
		ArrayList<Hashtable<String, String>> Result2 = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> ResultItem = new Hashtable<String, String>();
		String[] row = new String[3];
		int rowCount;

		// すでに紐付け済みのコードを取得するクエリ
		String Query1 = new String(
				"SELECT CENTER_KOUMOKU_CD,KOUMOKU_CD,KOUMOKU_NAME FROM T_KENSACENTER_MASTER WHERE KENSA_CENTER_CD = "
						+ JQueryConvert.queryConvert(jTextField_CenterCode
								.getText()));

		// まだ紐付けされていないコードを取得するクエリ
		String Query2 = new String("SELECT KOUMOKU_CD,KOUMOKU_NAME "
				+ "FROM T_KENSHINMASTER " + "WHERE KOUMOKU_CD " + "not in "
				+ "( " + "SELECT KOUMOKU_CD " + "FROM T_KENSACENTER_MASTER "
				+ "WHERE KENSA_CENTER_CD = "
				+ JQueryConvert.queryConvert(jTextField_CenterCode.getText())
				+ ") " + "AND HKNJANUM = '99999999' ");

		try {
			Result1 = JApplication.kikanDatabase.sendExecuteQuery(Query1);
			Result2 = JApplication.kikanDatabase.sendExecuteQuery(Query2);
		} catch (SQLException f) {
			f.printStackTrace();
		}

		rowCount = Result1.size();
		model.clearAllRow();

		for (int i = 0; i < rowCount; i++) {
			ResultItem = Result1.get(i);
			row[0] = ResultItem.get("CENTER_KOUMOKU_CD");
			row[1] = ResultItem.get("KOUMOKU_CD");
			row[2] = ResultItem.get("KOUMOKU_NAME");

			model.addData(row);
		}

		rowCount = Result2.size();
		for (int i = 0; i < rowCount; i++) {
			ResultItem = Result2.get(i);
			row[0] = "";
			row[1] = ResultItem.get("KOUMOKU_CD");
			row[2] = ResultItem.get("KOUMOKU_NAME");

			model.addData(row);
		}
	}

	/**
	 * 登録処理を行う
	 */
	public boolean register() {
		int rowCount = model.getRowCount();
		String Query = new String(
				"DELETE FROM T_KENSACENTER_MASTER WHERE KENSA_CENTER_CD = "
						+ JQueryConvert.queryConvert(jTextField_CenterCode
								.getText()));

		// 名称が空値でないかチェック
		validatedData.setKensaCenterName(jTextField_CenterName.getText());
		if (validatedData.getKensaCenterName().equals("")) {
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show(
					"M3702", this);
			return false;
		}

		try {
			JApplication.kikanDatabase.Transaction();

			// まず該当の検査センターに関して全件削除を行う
			JApplication.kikanDatabase.sendNoResultQuery(Query);

			Vector<String> centerCode = new Vector<String>();
			// 次に、テーブルからエントリーを追加する
			for (int i = 0; i < rowCount; i++) {

				if (validatedData.setKensaCenterCode(jTextField_CenterCode
						.getText())
						&& validatedData
								.setKensaCenterName(jTextField_CenterName
										.getText())
						&& validatedData
								.setKensaCenterKoumokuCode((String) model
										.getData(i, 0))
						&& validatedData.setKensaKoumokuCode((String) model
								.getData(i, 1))
						&& validatedData.setKoumokuName((String) model.getData(
								i, 2))) {
					if (validateAsRegisterPushed(validatedData)) {
						if (validatedData.isValidateAsDataSet()) {
							if (centerCode.contains(validatedData
									.getKensaCenterKoumokuCode())) {
								jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
										.show("M3701", this);
								JApplication.kikanDatabase.rollback();
								return false;
							}

							centerCode.add(validatedData
									.getKensaCenterKoumokuCode());
							Query = new String(
									"INSERT INTO T_KENSACENTER_MASTER ( KENSA_CENTER_CD,CENTER_KOUMOKU_CD,CENTER_NAME,KOUMOKU_NAME,KOUMOKU_CD ) VALUES ( "
											+ JQueryConvert
													.queryConvertAppendComma(validatedData
															.getKensaCenterCode())
											+ JQueryConvert
													.queryConvertAppendComma(validatedData
															.getKensaCenterKoumokuCode())
											+ JQueryConvert
													.queryConvertAppendComma(validatedData
															.getKensaCenterName())
											+ JQueryConvert
													.queryConvertAppendComma(validatedData
															.getKoumokuName())
											+ JQueryConvert
													.queryConvert(validatedData
															.getKensaKoumokuCode())
											+ ")");

							JApplication.kikanDatabase.sendNoResultQuery(Query);
						}
					}
				} else {
					return false;
				}
			}
			JApplication.kikanDatabase.Commit();

		} catch (SQLException f) {
			f.printStackTrace();
			try {
				JApplication.kikanDatabase.rollback();
				return false;
			} catch (SQLException g) {
				g.printStackTrace();
				return false;
			}

		}
		return true;
	}

	public boolean validateAsRegisterPushed(
			JKensaKikanCodeMasterMaintenanceEditFrameData data) {
		if (validatedData.getKensaCenterCode().equals("")
				|| validatedData.getKensaCenterName().equals("")
				|| validatedData.getKensaCenterKoumokuCode().equals("")
				|| validatedData.getKensaKoumokuCode().equals("")
				|| validatedData.getKoumokuName().equals("")) {
			return false;
		}
		data.setValidateAsDataSet();
		return true;
	}

	/*
	 * 戻る
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * 削除ボタン
	 */
	public void pushedDeleteButton(ActionEvent e) {
		// ErrorMessage.JErrorMessage.Show("M3721", this);
		int rowCount = model.getSelectedRowCount();

		if (rowCount > 0) {
			for (int i = 0; i < rowCount; i++) {
				model.setData("", model.getSelectedRows()[i], 0);
			}
		}
		pushedRegisterButton();
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton() {
		// ErrorMessage.JErrorMessage.Show("M3722", this);
		register();
	}

	/**
	 * キャンセルボタン
	 */
	public void pushedCancelButton() {
		// ErrorMessage.JErrorMessage.Show("M3723", this);
		dispose();
	}

	/**
	 *
	 */

	public void actionPerformed(ActionEvent e) {
		Object source = (e.getSource());
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (source == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton();
		}
		else if (source == jButton_Cancel) {
			logger.info(jButton_Cancel.getText());
			pushedCancelButton();
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

