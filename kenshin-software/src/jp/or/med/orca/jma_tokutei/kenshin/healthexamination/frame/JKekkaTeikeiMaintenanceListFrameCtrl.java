package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorHokenjyaResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorTeikeiResultFrameData;

/**
 * 定型文マスタメンテナンス
 */
public class JKekkaTeikeiMaintenanceListFrameCtrl extends JKekkaTeikeiMaintenanceListFrame
{
	private JSimpleTable model = new JSimpleTable();
	private int COLUMN_IDX_SYOKEN_NO = 0;
	private static Logger logger = Logger.getLogger(JKekkaTeikeiMaintenanceListFrameCtrl.class);

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	// edit s.inoue 2009/12/14
	private final String[] SYOKENCODE= {"その他の既往歴","自覚症状所見","他覚症状所見","心電図所見"};

	private JKekkaTeikeiMaintenanceEditFrameData validatedData = new JKekkaTeikeiMaintenanceEditFrameData();

	private IDialog filePathDialog;

	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

	private static Vector<Vector<String>> CSVItems = null;

	private static String saveTitle= "csvファイル保存先選択";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	// 検索ボタン押下時のSQLで使用
	private static final String[] TABLE_COLUMNS = {
		"SYOKEN_TYPE","SYOKEN_TYPE_NAME","SYOKEN_NO","SYOKEN_NAME","UPDATE_TIMESTAMP" };

	/**
	 * @param PatternNum 編集する健診パターンの番号
	 */
	public JKekkaTeikeiMaintenanceListFrameCtrl()
	{
		//テーブルの登録
		JSimpleTableScrollPanel table = new JSimpleTableScrollPanel(model);
		jPanel_MainArea.add(table);

		// keyイベント登録
		model.addKeyListener(new JEnterEvent(this,jButton_Edit));
		// ダブルクリックの処理
		model.addMouseListener(new JSingleDoubleClickEvent(this,jButton_Edit));

		initTable();
	}

	// edit s.inoue 2010/03/12
	private void initTable(){
		this.refreshTable();

		// edit s.inoue 2009/12/01
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model.getModel());
		model.setRowSorter(sorter);

		// focus制御
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(model);
		this.focusTraversalPolicy.addComponent(model);
		// edit s.inoue 2010/04/23
		this.focusTraversalPolicy.addComponent(this.jButton_Import);
		this.focusTraversalPolicy.addComponent(this.jButton_Export);
		this.focusTraversalPolicy.addComponent(this.jButton_Add);
		this.focusTraversalPolicy.addComponent(this.jButton_Edit);
		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		// del s.inoue 2010/05/18
		// initializeColumnWidth();
		model.setAutoCreateRowSorter(true);
		model.refreshTable();
	    // 初期選択
	    if (model.getRowCount() > 0) {
		   model.setRowSelectionInterval(0, 0);
	    }
	}

// del s.inoue 2010/05/18
//	/**
//	 * 列サイズを初期化する。
//	 */
//	private void initializeColumnWidth() {
//		this.model.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_OFF);
//
//		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.model.getColumnModel();
//		// edit s.inoue 2009/11/11
//		columnModel.getColumn(COLUMN_IDX_SYOKEN_NO).setMinWidth(0);
//		columnModel.getColumn(COLUMN_IDX_SYOKEN_NO).setPreferredWidth(0);
//		columnModel.getColumn(COLUMN_IDX_SYOKEN_NO).setMaxWidth(0);
//		columnModel.getColumn(COLUMN_IDX_SYOKEN_NO).setWidth(0);
//	}

	/**
	 * テーブルのリフレッシュを行う
	 */
	public void refreshTable()
	{
		model.setPreferedColumnWidths(new int[] { 80,100, 60, 500, 200 });

		String[] row = new String[5];

		// テーブルヘッダの初期化
		model.clearAllColumn();
		model.clearAllRow();
		model.addHeader("所見種別No");
		model.addHeader("所見種別");
		model.addHeader("所見No");
		model.addHeader("所見");
		model.addHeader("更新日時");

		model.getTableHeader().addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
					  int viewColumn = model.getTableHeader().columnAtPoint(e.getPoint());
					  int modelColumn = model.convertColumnIndexToModel(viewColumn);

					  int[] rows = model.getSelectedRows();
					  if (rows.length == 0) return;
					  for (int i = 0, n = rows.length; i < n; i++) {
						  // edit s.inoue 2009/12/15
					      // rows[i] = model.convertRowIndexToModel(rows[i]);
						  int modelRow = model.convertRowIndexToModel(rows[i]);
					      System.out.println("View Row: " + rows[i] + " Model Row: " + modelRow);
					  }

					  // del s.inoue 2009/12/15
					  // Arrays.sort(rows);
			  }
			});

		// セルの編集禁止
		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {
				new JSimpleTableCellPosition(-1,-1)
				};
		model.setCellEditForbid(iSetColumnList);

		ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
		Hashtable<String,String> ResultItem = new Hashtable<String,String>();

		try{
			StringBuilder buffer = new StringBuilder();
			buffer.append("SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME,");
// edit s.inoue 2010/05/18
//			buffer.append(" case SYOKEN_TYPE ");
//			buffer.append(" when 1 then 'その他の既往歴' ");
//			buffer.append(" when 2 then '自覚症状所見' ");
//			buffer.append(" when 3 then '他覚症状所見' ");
//			buffer.append(" when 4 then '心電図所見' ");
//			buffer.append(" end SYOKEN_TYPE_STR, ");
			buffer.append(" SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP");
			buffer.append(" FROM T_SYOKENMASTER ");
			buffer.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");

			result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());

			for( int i = 0;i < result.size();i++ )
			{
				ResultItem = result.get(i);
				row[0] = new String(ResultItem.get("SYOKEN_TYPE"));
				row[1] = new String(ResultItem.get("SYOKEN_TYPE_NAME"));
				row[2] = new String(ResultItem.get("SYOKEN_NO"));
				row[3] = new String(ResultItem.get("SYOKEN_NAME"));
				row[4] = new String(ResultItem.get("UPDATE_TIMESTAMP").replaceAll("-", ""));

				model.addData(row);
			}

		}catch(SQLException e){
			e.printStackTrace();
			JErrorMessage.show("M9906",this);
			dispose();
			return;
		}

		try{

			// add ver2 s.inoue 2009/05/08
			TableColumnModel columns = model.getColumnModel();
	        for(int i=0;i<columns.getColumnCount();i++) {

	            columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	                (DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
	        }

// del s.inoue 2009/12/15
//	        initializeColumnWidth();
//			model.setAutoCreateRowSorter(true);
//			model.refreshTable();
//
//			// 初期選択
//		    if (model.getRowCount() > 0) {
//			   model.setRowSelectionInterval(0, 0);
//		    }

		}catch(Exception e){
			JErrorMessage.show("M9906",this);
			e.printStackTrace();
			dispose();
			return;
		}
	}

	/**
	 * 編集画面を呼び出す
	 */
	public void register()
	{
		if( model.getSelectedRowCount() > 0 )
		{
			int[] rows = model.getSelectedRows();
			if (rows.length == 0) return;
//			for (int i = 0, n = rows.length; i < n; i++) {
//			    rows[i] = model.convertRowIndexToModel(rows[i]);
//			}

			// edit s.inoue 2009/12/15
			// model.getSelectedRow()→ rows
			JScene.CreateDialog(this, new JKekkaTeikeiMaintenanceEditFrameCtrl(
					(String)model.getData(rows[0], 0),
					(String)model.getData(rows[0], 2)),
					new WindowRefreshEvent());
		}
	}

	// add s.inoue 2010/03/12
	/**ImportStart↓↓↓↓↓**********************************************************/
	/**
	 * 取込ボタン
	 */
	public void pushedImportButton( ActionEvent e )
	{
		try {
			filePathDialog = DialogFactory.getInstance().createDialog(this,null);

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
			initTable();
		} catch (Exception ex) {
			JErrorMessage.show("M9912",this);
			logger.error(ex.getMessage());
		}
	}

	// edit s.inoue 2010/03/04
	/* 取込処理 */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M9916", this);

		// cancel時
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();

			// CSV読込処理
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET);
			} catch (IOException e) {
				JErrorMessage.show("M9913",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M9914",this);
					return;
				}

				// 定型文マスタ全削除
				teikeiMasterDelete();

				// データ取込処理
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
					setCSVItemsToDB(data,insertRow);

					// validate処理
					if (!validateData(data))
						return;

					// 定型文登録
					teikeiMasterRegister(data);
				}
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M9915",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M9913",this);
				logger.error(e.getMessage());
			}
		}
	}

	// edit s.inoue 2010/03/04
	/* csvデータ取込 */
	private void setCSVItemsToDB(JImportMasterErrorTeikeiResultFrameData data,
			Vector<String> insertRow){

		data.CSV_COLUMN_SYOKEN_TYPE = reader.rmQuart(insertRow.get(0));
		data.CSV_COLUMN_SYOKEN_TYPE_NAME = reader.rmQuart(insertRow.get(1));
		data.CSV_COLUMN_SYOKEN_NO = reader.rmQuart(insertRow.get(2));
		data.CSV_COLUMN_SYOKEN_NAME = reader.rmQuart(insertRow.get(3));
	}

	// edit s.inoue 2010/03/04
	/* validate処理 */
	private boolean validateData(JImportMasterErrorTeikeiResultFrameData data) {

		boolean rettanka = false;
		// edit s.inoue 2010/05/19
		rettanka= validatedData.setTeikeiType(data.CSV_COLUMN_SYOKEN_TYPE)
			&& validatedData.setTeikeiTypeName(data.CSV_COLUMN_SYOKEN_TYPE_NAME)
			&& validatedData.setTeikeiNumber(data.CSV_COLUMN_SYOKEN_NO)
			&& validatedData.setTeikeibun(data.CSV_COLUMN_SYOKEN_NAME);
		return rettanka;
	}

	/**
	 * 既存定型文マスタ削除
	 * @throws SQLException
	 */
	private void teikeiMasterDelete(){
		try {

			String query = new String("DELETE FROM T_SYOKENMASTER ");
			JApplication.kikanDatabase.sendNoResultQuery(query);
		} catch (SQLException e) {
			JErrorMessage.show("M9908",this);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void teikeiMasterRegister(JImportMasterErrorTeikeiResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		// timestamp取得
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
		buffer.append("VALUES (");
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType()));
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiTypeName()));
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber()));
		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun()));
		buffer.append(JQueryConvert.queryConvert(stringTimeStamp));
		buffer.append(")");

		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

	}
	/**ImportEnd↑↑↑↑↑**********************************************************/

	/**ExportStart↓↓↓↓↓**********************************************************/
	// add s.inoue 2010/02/25
	/**
	 * 書出ボタン
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("SyokenMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
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
			JErrorMessage.show("M9909", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
		JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M9909", this);
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

		sb.append(" SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP ");
		sb.append(" FROM T_SYOKENMASTER ");
		sb.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");

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
				data.add(resultItem.get(TABLE_COLUMNS[j]).trim());
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
	/**
	 * 追加ボタン
	 */
	public void pushedAddButton( ActionEvent e )
	{
		JScene.CreateDialog(
				this,
				new JKekkaTeikeiMaintenanceEditFrameCtrl(
						getNextShubetuNumber()),
				new WindowRefreshEvent());
	}

	/**
	 * 所見種別の空き番号の取得
	 */
	private String getNextShubetuNumber() {
		ArrayList<Hashtable<String, String>> Items;
		try {
			Items = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT SYOKEN_TYPE FROM T_SYOKENMASTER");
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M9919", this);
			return null;
		}

		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			boolean FindFlag = false;

			for (int j = 0; j < Items.size(); j++) {
				if (Items.get(j).get("SYOKEN_TYPE").equals(String.valueOf(i))) {
					FindFlag = true;
				}
			}

			// trueでなければ空きがあるとする。
			if (FindFlag == false) {
				return String.valueOf(i);
			}
		}

		return null;
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			//テーブルの再読み込みを行う
			refreshTable();
			// del s.inoue 2010/05/18
			// initializeColumnWidth();
			model.setAutoCreateRowSorter(true);
			model.refreshTable();

			// 初期選択
			if (model.getRowCount() > 0) {
				model.setRowSelectionInterval(0, 0);
		    }
		}
	}

	/**
	 * 削除ボタン
	 */
	public void pushedDeleteButton( ActionEvent e )
	{
		if ( model.getSelectedRowCount() <= 0 )
			return;

		RETURN_VALUE Ret = JErrorMessage.show("M9907", this);

		if(Ret == RETURN_VALUE.YES)
		{
			int[] selectedRow = model.getSelectedRows();

			for( int i = 0;i < model.getSelectedRowCount();i++)
			{
				try{
					JApplication.kikanDatabase.Transaction();

					//T_TEIKEIBUNテーブルから削除
					String syokenType = (String)model.getData(selectedRow[i], 0);
					String syokenNo = (String)model.getData(selectedRow[i], 2);

					StringBuilder sb = new StringBuilder();
					sb.append("DELETE FROM T_SYOKENMASTER ");
					sb.append(" WHERE SYOKEN_TYPE = ");
					sb.append(JQueryConvert.queryConvert(syokenType));
					sb.append(" AND SYOKEN_NO = ");
					sb.append(JQueryConvert.queryConvert(syokenNo));

					JApplication.kikanDatabase.sendNoResultQuery(sb.toString());

					JApplication.kikanDatabase.Commit();
				}catch(SQLException f){
					f.printStackTrace();
					JErrorMessage.show("M9908", this);
					try{
						JApplication.kikanDatabase.rollback();
					}catch(SQLException g){
						JErrorMessage.show("M0000", this);
						g.printStackTrace();
						System.exit(1);
					}
					return;
				}
			}

			refreshTable();

			// del s.inoue 2010/05/18
			// initializeColumnWidth();
			model.setAutoCreateRowSorter(true);
			model.refreshTable();
		    // 初期選択
			// del s.inoue 2010/04/01
//		    if (model.getRowCount() > 0) {
//			   model.setRowSelectionInterval(0, 0);
//		    }
		}
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}

	/**
	 * キャンセルボタン
	 */
	public void pushedCancelButton( ActionEvent e )
	{
		dispose();
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton( ActionEvent e )
	{
		register();
	}

	/**
	 * 取り消しボタン
	 */
	public void pushedClearButton( ActionEvent e )
	{
		refreshTable();

		// del s.inoue 2010/05/18
		// initializeColumnWidth();
		model.setAutoCreateRowSorter(true);
		model.refreshTable();
	    // 初期選択
	    if (model.getRowCount() > 0) {
		   model.setRowSelectionInterval(0, 0);
	    }
	}

	public void actionPerformed( ActionEvent e )
	{
		Object source = e.getSource();
		if( source == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}
		else if (source == jButton_Import)
		{
			logger.info(jButton_Import.getText());
			pushedImportButton(e);
		}
		else if (source == jButton_Export)
		{
			logger.info(jButton_Export.getText());
			pushedExportButton(e);
		}
		else if( source == jButton_Add )
		{
			logger.info(jButton_Add.getText());
			pushedAddButton( e );
		}
		else if( source == jButton_Edit )
		{
			logger.info(jButton_Edit.getText());
			pushedRegisterButton( e );
		}
		else if( source == jButton_Delete )
		{
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
			logger.info(jButton_Edit.getText());
			pushedRegisterButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Delete.getText());
			pushedDeleteButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

