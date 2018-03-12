package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
import jp.or.med.orca.jma_tokutei.common.table.*;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;

import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorHokenjyaResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorSiharaiResultFrameData;

/**
 * 支払い代行機関マスタメンテナンス
 */
public class JShiharaiMasterMaintenanceListFrameCtrl extends
		JShiharaiMasterMaintenanceListFrame {
	private JSimpleTable model = new JSimpleTable();
	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JShiharaiMasterMaintenanceListFrameCtrl.class);

	// add s.inoue 2010/03/05
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;
	private static Vector<Vector<String>> CSVItems = null;
	private IDialog filePathDialog;
	private JShiharaiMasterMaintenanceEditFrameData validatedData = new JShiharaiMasterMaintenanceEditFrameData();  //  @jve:decl-index=0:

	private static String saveTitle= "csvファイル保存先選択";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	private static final String[] TABLE_COLUMNS = {
		"SHIHARAI_DAIKO_NO", "SHIHARAI_DAIKO_NAME", "SHIHARAI_DAIKO_ZIPCD",
		"SHIHARAI_DAIKO_ADR", "SHIHARAI_DAIKO_TEL"
		 };

	public JShiharaiMasterMaintenanceListFrameCtrl() {
		JSimpleTableScrollPanel scroll = new JSimpleTableScrollPanel(model);
		jPanel_MainArea.add(scroll);

		this.initializeTable();
		initilizefocus();
		// edit s.inoue 2009/12/02
		functionListner();

		model.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(
						this, jButton_Change));

		// ダブルクリックの処理
		model.addMouseListener(new jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent(
						this, jButton_Change));
	}

	// add s.inoue 2009/12/02
	private void initilizefocus(){
		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(model);
		this.focusTraversalPolicy.addComponent(this.model);
		// edit s.inoue 2010/04/23
		this.focusTraversalPolicy.addComponent(this.jButton_Import);
		this.focusTraversalPolicy.addComponent(this.jButton_Export);
		this.focusTraversalPolicy.addComponent(this.jButton_Add);
		this.focusTraversalPolicy.addComponent(this.jButton_Change);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);
	}

	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * テーブルの初期化
	 */
	public void initializeTable() {
		//テーブルの再読み込みを行う
		model.clearAllColumn();
		model.clearAllRow();
		model.setPreferedColumnWidths(new int[] { 150, 200, 100, 200, 150 });

		//テーブルの初期設定
		model.addHeader("支払代行機関番号");
		model.addHeader("支払代行機関名称");
		model.addHeader("郵便番号");
		model.addHeader("所在地");
		model.addHeader("電話番号");

		//DBにアクセスしテーブルの要素を取得する
		try {
			String query = new String(
					"SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO DESC");
			String[] row = new String[5];
			ArrayList<Hashtable<String, String>> Result;
			Hashtable<String, String> ResultItem;

			Result = JApplication.kikanDatabase.sendExecuteQuery(query);

			for (int i = 0; i < Result.size(); i++) {
				ResultItem = Result.get(i);

				row[0] = ResultItem.get("SHIHARAI_DAIKO_NO");
				row[1] = ResultItem.get("SHIHARAI_DAIKO_NAME");
				row[2] = ResultItem.get("SHIHARAI_DAIKO_ZIPCD");
				row[3] = ResultItem.get("SHIHARAI_DAIKO_ADR");
				row[4] = ResultItem.get("SHIHARAI_DAIKO_TEL");

				model.addData(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M5101", this);
			return;
		}

		Vector<JSimpleTableCellPosition> forbitList = new Vector<JSimpleTableCellPosition>();
		forbitList.add(new JSimpleTableCellPosition(-1, -1));

		model.setCellEditForbid(forbitList);
		// edit s.inoue 2009/10/31
		model.setAutoCreateRowSorter(true);
		model.refreshTable();

		// add ver2 s.inoue 2009/05/08
		TableColumnModel columns = model.getColumnModel();
        for(int i=0;i<columns.getColumnCount();i++) {

            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
        }
		// 初期選択
		if (model.getRowCount() > 0) {
			model.setRowSelectionInterval(0, 0);
		} else {
			jButton_Add.requestFocus();
		}
	}
// edit s.inoue 2010/04/20
// 登録済みの代行機関を優先的に上位へ表示する
//	/**
//	 * テーブルの初期化
//	 */
//	public void initializeTable() {
//		//テーブルの再読み込みを行う
//		model.clearAllColumn();
//		model.clearAllRow();
//		model.setPreferedColumnWidths(new int[] { 150, 200, 100, 200, 150 });
//
//		//テーブルの初期設定
//		model.addHeader("支払代行機関番号");
//		model.addHeader("支払代行機関名称");
//		model.addHeader("郵便番号");
//		model.addHeader("所在地");
//		model.addHeader("電話番号");
//
//		//DBにアクセスしテーブルの要素を取得する
//		try {
//
//			// edit s.inoue 2009/12/22
//			// オーダー順を登録順に変更 in句用にキーを取得する
//			ArrayList<Hashtable<String, String>> result_keys;
//			Hashtable<String, String> resultItem_keys;
//			StringBuilder sb_keys = new StringBuilder();
//			sb_keys.append("SELECT distinct (SIHARAI_DAIKOU_BANGO) ");
//			sb_keys.append("FROM T_KOJIN where SIHARAI_DAIKOU_BANGO is not null ");
//			// String query = new String(
//			//		"SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO DESC");
//
//			result_keys = JApplication.kikanDatabase.sendExecuteQuery(sb_keys.toString());
//			String strWhere = "";
//			for (int i = 0; i < result_keys.size(); i++) {
//				resultItem_keys = result_keys.get(i);
//				strWhere += JQueryConvert.queryConvertAppendComma(
//						resultItem_keys.get("SIHARAI_DAIKOU_BANGO"));
//			}
//			if (strWhere.length() > 0)
//				strWhere = strWhere.substring(0, strWhere.lastIndexOf(","));
//
//			// edit s.inoue 2009/12/22
//			// 登録済みを上位にunionしデータを取得する
//			String[] row = new String[5];
//			ArrayList<Hashtable<String, String>> result;
//			Hashtable<String, String> resultItem;
//
//			StringBuilder sb = new StringBuilder();
//// edit s.inoue 2010/04/20
////			if (strWhere.length() > 0){
////				sb.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
////				sb.append(" FROM T_SHIHARAI ");
////
////				sb.append(" Where SHIHARAI_DAIKO_NO IN (");
////				sb.append(strWhere);
////				sb.append(")");
////				sb.append(" union all ");
////				sb.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
////				sb.append(" FROM T_SHIHARAI ");
////				sb.append(" Where SHIHARAI_DAIKO_NO NOT IN (");
////				sb.append(strWhere);
////				sb.append(")");
////			}else{
////				sb.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
////				sb.append(" FROM T_SHIHARAI ");
////			}
//			sb.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
//			sb.append(" FROM T_SHIHARAI ");
//			sb.append(" Where SHIHARAI_DAIKO_NO IN (");
//			sb.append(strWhere);
//			sb.append(")");
//			sb.append(" union all ");
//			sb.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
//			sb.append(" FROM T_SHIHARAI ");
//			sb.append(" Where SHIHARAI_DAIKO_NO NOT IN (");
//			sb.append(strWhere);
//			sb.append(")");
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//
//			for (int i = 0; i < result.size(); i++) {
//				resultItem = result.get(i);
//
//				row[0] = resultItem.get("SHIHARAI_DAIKO_NO");
//				row[1] = resultItem.get("SHIHARAI_DAIKO_NAME");
//				row[2] = resultItem.get("SHIHARAI_DAIKO_ZIPCD");
//				row[3] = resultItem.get("SHIHARAI_DAIKO_ADR");
//				row[4] = resultItem.get("SHIHARAI_DAIKO_TEL");
//
//				model.addData(row);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JErrorMessage.show("M5101", this);
//			return;
//		}
//
//		Vector<JSimpleTableCellPosition> forbitList = new Vector<JSimpleTableCellPosition>();
//		forbitList.add(new JSimpleTableCellPosition(-1, -1));
//
//		model.setCellEditForbid(forbitList);
//		// edit s.inoue 2009/10/31
//		model.setAutoCreateRowSorter(true);
//		model.refreshTable();
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel columns = model.getColumnModel();
//        for(int i=0;i<columns.getColumnCount();i++) {
//
//            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//        }
//		// 初期選択
//		if (model.getRowCount() > 0) {
//			model.setRowSelectionInterval(0, 0);
//		} else {
//			jButton_Add.requestFocus();
//		}
//	}

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
		JScene.CreateDialog(this,
				new JShiharaiMasterMaintenanceEditFrameCtrl(),
				new WindowRefreshEvent());
	}

	/**
	 * 変更ボタン
	 */
	public void pushedChangeButton(ActionEvent e) {
		if (model.getSelectedRowCount() == 1) {
			// edit s.inoue 2010/07/07
			String daikouNumber = (String) model.getData(model.getDoubleClickedSelectedRow()).get(0);

			JScene.CreateDialog(this,
					new JShiharaiMasterMaintenanceEditFrameCtrl(daikouNumber),
					new WindowRefreshEvent());
		}
	}

	/**
	 * 削除ボタン
	 */
	public void pushedDeleteButton(ActionEvent e) {
		if (model.getSelectedRowCount() == 0) {
			return;
		}

		RETURN_VALUE Ret = JErrorMessage.show("M5102", this);

		if (Ret == RETURN_VALUE.YES) {

			int[] selectedRowIndeces = model.getSelectedRows();

			for (int i = selectedRowIndeces.length - 1; i >= 0; i--) {

				int selectedIndex = selectedRowIndeces[i];

				String Key = (String) model.getData(selectedIndex, 0);
				String Query = new String(
						"DELETE FROM T_SHIHARAI WHERE SHIHARAI_DAIKO_NO = "
								+ JQueryConvert.queryConvert(Key));

				try {
					JApplication.kikanDatabase.sendNoResultQuery(Query);
				} catch (SQLException e1) {
					e1.printStackTrace();
					JErrorMessage.show("M5103", this);
				}
			}
			initializeTable();
		}
	}

	// 未実装部分 edit s.inoue 2010/03/04
	/**ImportStart↓↓↓↓↓**********************************************************/
	/**
	 * 取込ボタン
	 */
	public void pushedImportButton( ActionEvent e )
	{
		try {
			filePathDialog = DialogFactory.getInstance().createDialog(this ,null);

			filePathDialog.setMessageTitle(selectTitle);
			filePathDialog.setDialogTitle(selectDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
			filePathDialog.setVisible(true);

			// eidt s.inoue 2012/07/06
			if (filePathDialog.getStatus() == null)return;
			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;

			String filePath = filePathDialog.getFilePath();
			// add s.inoue 2010/03/05
			File fileImport = new File(filePath);
			if(!fileImport.exists())
				return;
			importCsvData(filePath);
			// 再描画処理
			this.initializeTable();
		} catch (Exception ex) {
			JErrorMessage.show("M5105",this);
			logger.error(ex.getMessage());
		}
	}

	// edit s.inoue 2010/03/04
	/* 取込処理 */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M5107", this);

		// cancel時
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorSiharaiResultFrameData data = new JImportMasterErrorSiharaiResultFrameData();

			// CSV読込処理
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
			} catch (IOException e) {
				JErrorMessage.show("M5105",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M5111",this);
					return;
				}

				// 既存データall削除
				shiharaiMasterDelete();

				// 検査結果データ取込処理
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
					setCSVItemsToDB(data,insertRow);

					// validate処理
					if (!validateData(data))
						return;

					shiharaiMasterRegister(data);

				}
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M5108",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M5105",this);
				logger.error(e.getMessage());
			}
		}
	}

	/* csvデータ取込 */
	private void setCSVItemsToDB(JImportMasterErrorSiharaiResultFrameData data,
			Vector<String> insertRow){

		data.CSV_COLUMN_SHIHARAI_DAIKO_NO = reader.rmQuart(insertRow.get(0));
		data.CSV_COLUMN_SHIHARAI_DAIKO_NAME = reader.rmQuart(insertRow.get(1));
		data.CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD = reader.rmQuart(insertRow.get(2));
		data.CSV_COLUMN_SHIHARAI_DAIKO_ADR = reader.rmQuart(insertRow.get(3));
		data.CSV_COLUMN_SHIHARAI_DAIKO_TEL = reader.rmQuart(insertRow.get(4));
	}

	// edit s.inoue 2010/03/04
	/* validate処理 */
	private boolean validateData(JImportMasterErrorSiharaiResultFrameData data) {

		boolean rettanka = false;
		// edit s.inoue 2010/04/27
		String address = data.CSV_COLUMN_SHIHARAI_DAIKO_ADR;
		if (!JValidate.isAllZenkaku(address)){
			address = JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(address);
		}
		address = JValidate.encodeUNICODEtoConvert(address);

		rettanka= validatedData.setDaikouNumber(data.CSV_COLUMN_SHIHARAI_DAIKO_NO)
			&& validatedData.setDaikouName(data.CSV_COLUMN_SHIHARAI_DAIKO_NAME)
			&& validatedData.setZipcode(data.CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD)
			&& validatedData.setAddress(address)
			&& validatedData.setTEL(data.CSV_COLUMN_SHIHARAI_DAIKO_TEL);
		return rettanka;
	}

//	/*
//	 * 接続処理
//	 */
//	private void getconnectDao(){
//		try {
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//		} catch (Exception e) {
//			logger.error("機関FDBへの接続処理に失敗しました。");
//			JErrorMessage.show("M3326",this);
//			return;
//		}
//	}

	/**
	 * 既存保険者削除
	 * @throws SQLException
	 */
	private void shiharaiMasterDelete(){
		try {
			String query = new String(
					"DELETE FROM T_SHIHARAI ");
			JApplication.kikanDatabase.sendNoResultQuery(query);
		} catch (SQLException e) {
			JErrorMessage.show("M5109",this);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void shiharaiMasterRegister(JImportMasterErrorSiharaiResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append(" INSERT INTO T_SHIHARAI ");
		buffer.append(" (SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD,");
		buffer.append("SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL ");
		buffer.append(" )VALUES( "
			+ JQueryConvert.queryConvertAppendComma(validatedData.getDaikouNumber())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getDaikouName())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getAddress())
			+ JQueryConvert.queryConvert(validatedData.getTEL())
			+ ")");

		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

	}
	/**ImportEnd↑↑↑↑↑**********************************************************/

	/**ExportStart↓↓↓↓↓**********************************************************/
	/**
	 * 書出ボタン
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("SiharaiMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this,defaltPath);
			filePathDialog.setMessageTitle(saveTitle);
			filePathDialog.setDialogTitle(savaDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
			filePathDialog.setVisible(true);

			// eidt s.inoue 2012/07/06
			if (filePathDialog.getStatus() == null)return;
			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;

			String filePath = filePathDialog.getFilePath();
			if (filePath.equals(""))
				return;

			exportCsvData(filePath);

		} catch (Exception ex) {
			JErrorMessage.show("M5109", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
		JImportMasterErrorSiharaiResultFrameData data = new JImportMasterErrorSiharaiResultFrameData();

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M5110", this);
			logger.error(e.getMessage());
		}
	}

	/* DBよりデータ取得*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

		// add s.inoue 2010/03/04
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME,");
		sb.append(" SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
		sb.append(" FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		Hashtable<String, String> resultItem = new Hashtable<String, String>();
		for( int i=0; i<result.size(); ++i )
		{
			resultItem = result.get(i);

			Vector<String> data = new Vector<String>();

			for (int j=0; j<resultItem.size(); ++j){
				// 空要素
				String exportItem =resultItem.get(TABLE_COLUMNS[j]).trim();

				data.add(exportItem);
			}

			// header設定
			if (i==0){
				Vector colmn = new Vector<String>();
				List l = java.util.Arrays.asList(TABLE_COLUMNS);
				for (Iterator item = l.iterator(); item.hasNext();) {
					colmn.add((String)item.next());
				}
				newTable.add(colmn);
			}
			newTable.add(data);
		}
		return newTable;
	}
	/**ExportEnd↑↑↑↑↑**********************************************************/
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jButton_Import)
		{
			logger.info(jButton_Import.getText());
			pushedImportButton(e);
		}
		else if (e.getSource() == jButton_Export)
		{
			logger.info(jButton_Export.getText());
			pushedExportButton(e);
		}

		if (e.getSource() == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		if (e.getSource() == jButton_Add) {
			logger.info(jButton_Add.getText());
			pushedAddButton(e);
		}
		if (e.getSource() == jButton_Change) {
			logger.info(jButton_Change.getText());
			pushedChangeButton(e);
		}
		if (e.getSource() == jButton_Delete) {
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		// edit s.inoue 2010/03/24
		case KeyEvent.VK_F4:
			logger.info(jButton_Import.getText());
			pushedImportButton(null);break;
		case KeyEvent.VK_F5:
			logger.info(jButton_Export.getText());
			pushedExportButton(null);break;
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
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			initializeTable();
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

