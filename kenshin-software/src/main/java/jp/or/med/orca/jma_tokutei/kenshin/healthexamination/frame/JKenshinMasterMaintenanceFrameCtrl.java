package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRendererData;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorHokenjyaResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorKenshinResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorTeikeiResultFrameData;

/**
 * 健診項目マスタメンテナンス制御
 */
public class JKenshinMasterMaintenanceFrameCtrl extends
		JKenshinMasterMaintenanceFrame {

	private static final int COLUMN_INDEX_KOUMOKU_CD = 0;
	private static final int COLUMN_INDEX_KOUMOKU_NAME = 1;
	private static final int COLUMN_INDEX_KENSA_HOUHOU = 2;
	private static final int COLUMN_INDEX_HISU_FLG = 3;
	private static final int COLUMN_INDEX_DS_KAGEN = 4;
	private static final int COLUMN_INDEX_DS_JYOUGEN = 5;
	private static final int COLUMN_INDEX_JS_KAGEN = 6;
	private static final int COLUMN_INDEX_JS_JYOUGEN = 7;
	private static final int COLUMN_INDEX_TANI = 8;
	private static final int COLUMN_INDEX_KAGEN = 9;
	private static final int COLUMN_INDEX_JYOUGEN = 10;
	private static final int COLUMN_INDEX_KIJYUNTI_HANI = 11;
	private static final int COLUMN_INDEX_TANKA_KENSIN = 12;
	private static final int COLUMN_INDEX_BIKOU = 13;

	// edit s.inoue 2009/11/14
	// private JSimpleTable model = new JSimpleTable();
	private DefaultTableModel dmodel = null;
	private TableRowSorter<TableModel> sorter =null;

	private JSimpleTable table=null;
	private JSimpleTable modelfixed= null;
	Object[][] rowcolumn = null;

	private static Logger logger = Logger.getLogger(JKenshinMasterMaintenanceFrameCtrl.class);

	// add h.yoshitama 2009/09/18
	private Vector<JSimpleTableCellRendererData> cellColors = new Vector<JSimpleTableCellRendererData>();
	private final Color COLOR_ABLE = ViewSettings.getAbleItemBgColor();

	private JKenshinMasterMaintenanceFrameData validatedData = new JKenshinMasterMaintenanceFrameData();

	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private ArrayList<Hashtable<String, String>> result;
	private String[] header = { "項目コード", "項目名","検査方法","必須フラグ[入力]","基準値下限（男性）[入力]", "基準値上限（男性）[入力]",
			"基準値下限（女性）[入力]","基準値上限（女性）[入力]" , "単位", "入力下限値", "入力上限値", "基準値範囲", "単価（円）[入力]", "備考[入力]" };

	private IDialog filePathDialog;

	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

	private static Vector<Vector<String>> CSVItems = null;

	private static String saveTitle= "csvファイル保存先選択";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	// 検索ボタン押下時のSQLで使用
	// edit s.inoue 2010/07/07
	private static final String[] TABLE_COLUMNS = {
		"HKNJANUM","KOUMOKU_CD","HISU_FLG",
		"DS_KAGEN","DS_JYOUGEN","JS_KAGEN","JS_JYOUGEN","TANI","TANKA_KENSIN","BIKOU"
		};

	public JKenshinMasterMaintenanceFrameCtrl() {
		initializeSetting();
		initializeCombobox();
		// add h.yoshitama 2009/11/27
		/* Fキー用イベント設定 */
//		functionListner();
	}

	// add s.inoue 2009/11/17
	// combobox初期化処理
	private void initializeCombobox(){
		ArrayList<Hashtable<String, String>> Result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> ResultItem = new Hashtable<String, String>();
		// add s.inoue 2010/01/21
		String Query = new String("SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA WHERE YUKOU_FLG = '1'");

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M3820", this);
			dispose();
			return;
		}

		for (int i = 0; i < Result.size(); i++) {
			ResultItem = Result.get(i);
			jComboBox_HokenjyaNumber.addItem(ResultItem.get("HKNJANUM") + ":"
					+ ResultItem.get("HKNJANAME"));
		}
		jComboBox_HokenjyaNumber.addItem("99999999:マスタデータ編集");
		jLabel_ableExample.setBackground(ViewSettings.getAbleItemBgColor());
	}

	// add s.inoue 2009/11/14
	private void initializeSetting(){

		dmodel = new DefaultTableModel(resultRefresh(),header){
	      public boolean isCellEditable(int row, int column) {
	    	boolean retflg = false;
	    	if ( column >13 ||
	    			column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 12 || column == 13){
	    		retflg = true;
	      	}
	        return retflg;
	      }
	    };

		sorter = new TableRowSorter<TableModel>(dmodel);
		table = new JSimpleTable(dmodel);
		modelfixed = new JSimpleTable(dmodel);

		modelfixed.setPreferedColumnWidths(new int[]{150, 150});
		table.setPreferedColumnWidths(new int[] { 80, 80, 80, 80, 80, 80, 50,80, 80,  120, 120 });

		modelfixed.setSelectionModel(table.getSelectionModel());

		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<4) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	    table.setRowSorter(sorter);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    modelfixed.setRowSorter(sorter);
	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    initilizeFocus();
	    // add s.inoue 2009/12/03
	    functionListner();
        final JScrollPane scroll = new JScrollPane(table);
        JViewport viewport = new JViewport();
        viewport.setView(modelfixed);
        viewport.setPreferredSize(modelfixed.getPreferredSize());
        scroll.setRowHeader(viewport);

        modelfixed.setPreferredScrollableViewportSize(modelfixed.getPreferredSize());
        scroll.setRowHeaderView(modelfixed);
        scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, modelfixed.getTableHeader());

        scroll.getRowHeader().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JViewport viewport = (JViewport) e.getSource();
                scroll.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
            }
        });

        jPanel_Main.add(scroll);

		dmodel.setDataVector(resultRefresh(),header);

		// 4列以下は固定、以降は変動
		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<2) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	   TableColumnModel columnsfix = modelfixed.getColumnModel();
	   for(int i=0;i<columnsfix.getColumnCount();i++) {

		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
	   }

	   TableColumnModel columns = table.getColumnModel();
	   for(int i=0;i<columns.getColumnCount();i++) {

		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
	   }

		// 初期選択
		if (table.getRowCount() > 0) {
			table.setRowSelectionInterval(0, 0);
		}

		// add s.inoue 2009/11/12
		table.addHeader(this.header);
		table.refreshTable(); modelfixed.refreshTable();

		/* セルの色を更新する。 */
		this.refreshTableCellColor();
	}

	/*
	 * 検索リフレッシュ処理
	 */
	private void searchRefresh(){

		modelfixed.setPreferedColumnWidths(new int[]{150, 150});
		table.setPreferedColumnWidths(new int[] { 80, 80, 80, 80, 80, 80, 50,80, 80,  120, 120 });

		// add s.inoue 2009/10/23
	    modelfixed.setSelectionModel(table.getSelectionModel());

	    // validationCheck
	    Object [][] resultref=resultRefresh();
	    if (resultref == null)
	    	return;

	    // add s.inoue 2009/10/26
	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<2) {
                table.removeColumn(table.getColumnModel().getColumn(i));
            }
        }

	    dmodel.setDataVector(resultref,header);

		// add s.inoue 2009/10/26
		// 4列以下は固定、以降は変動
		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
            if(i<2) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                modelfixed.getColumnModel().getColumn(i).setResizable(false);
            }else{
                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
            }
        }

	   // add s.inoue 2009/10/26
	   TableColumnModel columnsfix = modelfixed.getColumnModel();
	   for(int i=1;i<columnsfix.getColumnCount();i++) {

		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
	   }

	   TableColumnModel columns = table.getColumnModel();
	   for(int i=0;i<columns.getColumnCount();i++) {

		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
	   }

	    // add s.inoue 2009/11/12
	    table.addHeader(this.header);
	    modelfixed.refreshTable();table.refreshTable();

		// 初期選択
		if (table.getRowCount() > 0) {
			table.setRowSelectionInterval(0, 0);
		}
	}
	/* focus初期化 */
	private void initilizeFocus(){
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(jComboBox_HokenjyaNumber);
		this.focusTraversalPolicy.addComponent(jComboBox_HokenjyaNumber);
		this.focusTraversalPolicy.addComponent(table);
		this.focusTraversalPolicy.addComponent(jButton_Import);
		this.focusTraversalPolicy.addComponent(jButton_Export);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);
	}

	// add h.yoshitama 2009/11/27
	private void functionListner(){

		// add s.inoue 2009/12/02
		Component comp =null;
		HashMap<String, Component> compHm = new HashMap<String, Component>();

		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			comp = focusTraversalPolicy.getComponent(i);
			System.out.println(comp.getName());

			if (!compHm.containsKey(comp.getName())){
				comp.addKeyListener(this);
				compHm.put(comp.getName(), comp);
			}
		}
		// edit s.inoue 2010/04/26
		table.addKeyListener(this);
		modelfixed.addKeyListener(this);
	}

	// 一覧用データ取得
	private Object[][] resultRefresh()
	{
		if (jComboBox_HokenjyaNumber.getItemCount() <= 0)
			return null;

		try {
			String curHokenjyaNumber = new String(
					((String) jComboBox_HokenjyaNumber.getSelectedItem()).substring(0, 8));

			String query = new String("SELECT HKNJANUM, KOUMOKU_CD, KOUMOKU_NAME, DATA_TYPE, MAX_BYTE_LENGTH, XML_PATTERN, XML_DATA_TYPE,"
				 +	" XML_KENSA_CD, OBSERVATION, AUTHOR, AUTHOR_KOUMOKU_CD, KENSA_GROUP, KENSA_GROUP_CD, KEKKA_OID,"
				 +	" KOUMOKU_OID, HISU_FLG, KAGEN, JYOUGEN, DS_KAGEN,DS_JYOUGEN, JS_KAGEN,JS_JYOUGEN, KIJYUNTI_HANI,"
				 +	" TANI, KENSA_HOUHOU, TANKA_KENSIN, BIKOU, XMLITEM_SEQNO"
				 +	" FROM T_KENSHINMASTER WHERE HKNJANUM = "
				 + JQueryConvert.queryConvert(curHokenjyaNumber))
				 // edit s.inoue 2010/02/16
				 +  " ORDER BY XMLITEM_SEQNO ";

			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M3821", this);
			dispose();
		}

		rowcolumn = new Object[result.size()][14];
		Hashtable<String, String> resultItem;

		for (int i = 0; i < result.size(); i++) {
			resultItem = result.get(i);

			rowcolumn[i][COLUMN_INDEX_KOUMOKU_CD] = resultItem.get("KOUMOKU_CD");
			rowcolumn[i][COLUMN_INDEX_KOUMOKU_NAME] = resultItem.get("KOUMOKU_NAME");
			rowcolumn[i][COLUMN_INDEX_KENSA_HOUHOU] = resultItem.get("KENSA_HOUHOU");
			rowcolumn[i][COLUMN_INDEX_HISU_FLG] = resultItem.get("HISU_FLG");
			rowcolumn[i][COLUMN_INDEX_TANI] = resultItem.get("TANI");
			rowcolumn[i][COLUMN_INDEX_KIJYUNTI_HANI] = resultItem.get("KIJYUNTI_HANI");
			rowcolumn[i][COLUMN_INDEX_TANKA_KENSIN] = resultItem.get("TANKA_KENSIN");
			rowcolumn[i][COLUMN_INDEX_BIKOU] = resultItem.get("BIKOU");

			String LimitFormat = resultItem.get("MAX_BYTE_LENGTH");
			String limitValeDsKagen = resultItem.get("DS_KAGEN");
			String limitValeDsJyougen = resultItem.get("DS_JYOUGEN");
			String limitValeJsKagen = resultItem.get("JS_KAGEN");
			String limitValeJsJyougen = resultItem.get("JS_JYOUGEN");
			String maxValeKagen = resultItem.get("KAGEN");
			String maxValeJyougen = resultItem.get("JYOUGEN");

			// データタイプ 入力上限下限 数字:1の時
			if (resultItem.get("DATA_TYPE").equals("1")){
				if (!maxValeKagen.equals("")){
					rowcolumn[i][COLUMN_INDEX_KAGEN] = JValidate.DecimalFormatValue(maxValeKagen,LimitFormat);
				}else{
					rowcolumn[i][COLUMN_INDEX_KAGEN] = "";
				}

				if (rowcolumn[i][COLUMN_INDEX_KOUMOKU_CD].equals("2A040000001930102")){
					rowcolumn[i][COLUMN_INDEX_JYOUGEN] = "99.9";
				}else{
					if (!maxValeJyougen.equals("")){
						rowcolumn[i][COLUMN_INDEX_JYOUGEN] = JValidate.DecimalFormatValue(maxValeJyougen,LimitFormat);
					}else{
						rowcolumn[i][COLUMN_INDEX_JYOUGEN] = "";
					}
				}

				// edit s.inoue 2010/07/22 逆順
				if (!limitValeDsKagen.equals("")){
					rowcolumn[i][COLUMN_INDEX_DS_KAGEN] = JValidate.DecimalFormatValue(limitValeDsKagen,LimitFormat);
				}else{
					rowcolumn[i][COLUMN_INDEX_DS_KAGEN] = "";
				}

				if (!limitValeDsJyougen.equals("")){
					rowcolumn[i][COLUMN_INDEX_DS_JYOUGEN] = JValidate.DecimalFormatValue(limitValeDsJyougen,LimitFormat);
				}else{
					rowcolumn[i][COLUMN_INDEX_DS_JYOUGEN] = "";
				}

				if (!limitValeJsKagen.equals("")){
					rowcolumn[i][COLUMN_INDEX_JS_KAGEN] = JValidate.DecimalFormatValue(limitValeJsKagen,LimitFormat);
				}else{
					rowcolumn[i][COLUMN_INDEX_JS_KAGEN] = "";
				}

				if (!limitValeJsJyougen.equals("")){
					rowcolumn[i][COLUMN_INDEX_JS_JYOUGEN] = JValidate.DecimalFormatValue(limitValeJsJyougen,LimitFormat);
				}else{
					rowcolumn[i][COLUMN_INDEX_JS_JYOUGEN] = "";
				}

			}else{
				rowcolumn[i][COLUMN_INDEX_KAGEN] = "";
				rowcolumn[i][COLUMN_INDEX_JYOUGEN] = "";
				rowcolumn[i][COLUMN_INDEX_DS_KAGEN] = "";
				rowcolumn[i][COLUMN_INDEX_DS_JYOUGEN] = "";
				rowcolumn[i][COLUMN_INDEX_JS_KAGEN] = "";
				rowcolumn[i][COLUMN_INDEX_JS_JYOUGEN] = "";
			}
		}

		return rowcolumn;
	}

	/**
	 * 各行に関して必須項目のチェックを行う
	 */
	public boolean validateAsRegisterPushed(
			JKenshinMasterMaintenanceFrameData data) {
		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
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
			// add s.inoue 2010/03/15
			initializeSetting();
			initializeCombobox();

		} catch (Exception ex) {
			JErrorMessage.show("M3824",this);
			logger.error(ex.getMessage());
		}
	}

	/* 取込処理 */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M3825", this);

		// cancel時
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorKenshinResultFrameData data = new JImportMasterErrorKenshinResultFrameData();

			// CSV読込処理
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET);
			} catch (IOException e) {
				JErrorMessage.show("M3824",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M3827",this);
					return;
				}

				// データ取込処理
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
					setCSVItemsToDB(data,insertRow);

					if(!validateData(data))
						return;

					// 健診マスタ登録
					kenshinMasterRegister(data);
				}
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M3828",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M3826",this);
				logger.error(e.getMessage());
			}
		}
	}

	// edit s.inoue 2010/03/04
	/* csvデータ取込 */
	private void setCSVItemsToDB(JImportMasterErrorKenshinResultFrameData data,
			Vector<String> insertRow){

			data.CSV_COLUMN_HKNJANUM = (reader.rmQuart(insertRow.get(0)));
			data.CSV_COLUMN_KOUMOKU_CD = (reader.rmQuart(insertRow.get(1)));
			data.CSV_COLUMN_HISU_FLG = (reader.rmQuart(insertRow.get(2)));
			data.CSV_COLUMN_DS_KAGEN = (reader.rmQuart(insertRow.get(3)));
			data.CSV_COLUMN_DS_JYOUGEN = (reader.rmQuart(insertRow.get(4)));
			data.CSV_COLUMN_JS_KAGEN = (reader.rmQuart(insertRow.get(5)));
			data.CSV_COLUMN_JS_JYOUGEN = (reader.rmQuart(insertRow.get(6)));
			data.CSV_COLUMN_TANI = (reader.rmQuart(insertRow.get(7)));
			// edit s.inoue 2010/07/06
			data.CSV_TANKA_KENSIN = (reader.rmQuart(insertRow.get(8)));
			data.CSV_COLUMN_BIKOU = (reader.rmQuart(insertRow.get(9)));
	}

	// edit s.inoue 2010/03/04
	/* validate処理 */
	private boolean validateData(JImportMasterErrorKenshinResultFrameData data) {

		boolean rettanka = false;

		rettanka= validatedData.setHokenjyaNumber(data.CSV_COLUMN_HKNJANUM)
			&& validatedData.setKensaKoumokuCode(data.CSV_COLUMN_KOUMOKU_CD)
			&& validatedData.setHisuFlag(data.CSV_COLUMN_HISU_FLG)
			&& validatedData.setJsKagen(data.CSV_COLUMN_JS_KAGEN)
			&& validatedData.setJsJyougen(data.CSV_COLUMN_JS_JYOUGEN)
			&& validatedData.setDsKagen(data.CSV_COLUMN_DS_KAGEN)
			&& validatedData.setDsJyougen(data.CSV_COLUMN_DS_JYOUGEN)
			// edit s.inoue 2010/07/06
			&& validatedData.setTanni(data.CSV_COLUMN_TANI)
			&& validatedData.setKensaTanka(data.CSV_TANKA_KENSIN)
			&& validatedData.setNote(data.CSV_COLUMN_BIKOU);
		return rettanka;
	}

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void kenshinMasterRegister(JImportMasterErrorKenshinResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		buffer = new StringBuffer("");
		buffer.append("UPDATE T_KENSHINMASTER SET ");
		buffer.append(" HISU_FLG = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getHisuFlag()));
		buffer.append(" DS_KAGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getDsKagen()));
		buffer.append(" DS_JYOUGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getDsJyougen()));
		buffer.append(" JS_KAGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getJsKagen()));
		buffer.append(" JS_JYOUGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getJsJyougen()));
		// edit s.inoue 2010/07/06
		buffer.append(" TANI = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getTanni()));
		buffer.append(" TANKA_KENSIN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getKensaTanka()));
		buffer.append(" BIKOU = "
				+ JQueryConvert.queryConvert(validatedData.getNote()));
		buffer.append(" WHERE HKNJANUM ="
				+ JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
		buffer.append(" AND KOUMOKU_CD ="
				+ JQueryConvert.queryConvert(validatedData.getKensaKoumokuCode()));

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
			String saveFileName = JPath.createDirectoryUniqueName("KenshinMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
			filePathDialog.setMessageTitle(saveTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(false);
			filePathDialog.setDialogTitle(savaDialogTitle);
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
			JErrorMessage.show("M3829", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
		// JImportMasterErrorKenshinResultFrameData data = new JImportMasterErrorKenshinResultFrameData();

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M3829", this);
			logger.error(e.getMessage());
		}
	}

	/* DBよりデータ取得*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

		if (jComboBox_HokenjyaNumber.getItemCount() <= 0)
			return null;

		String curHokenjyaNumber = new String(
					((String) jComboBox_HokenjyaNumber.getSelectedItem()).substring(0, 8));

		StringBuilder sb = new StringBuilder();
		// edit s.inoue 2010/07/07
		sb.append(" SELECT HKNJANUM,KOUMOKU_CD,HISU_FLG,DS_KAGEN,DS_JYOUGEN,JS_KAGEN,JS_JYOUGEN,TANI,TANKA_KENSIN,BIKOU");
		sb.append(" FROM T_KENSHINMASTER WHERE HKNJANUM = ");
		sb.append(JQueryConvert.queryConvert(curHokenjyaNumber));
		sb.append(" ORDER BY XMLITEM_SEQNO ");

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
	 * 登録ボタン
	 */
	public void pushedRegisterButton(ActionEvent e) {
		String Query = null;

		try {
			JApplication.kikanDatabase.Transaction();

			for (int i = 0; i < table.getRowCount(); i++) {

				if (
				// edit ver2 s.inoue 2009/07/22
				validatedData.setKensaKoumokuCode((String) modelfixed.getData(i,COLUMN_INDEX_KOUMOKU_CD))
						&& validatedData.setKensaKoumokuName((String) modelfixed.getData(i, COLUMN_INDEX_KOUMOKU_NAME))
						&& validatedData.setKensaHouhou((String) modelfixed.getData(i,COLUMN_INDEX_KENSA_HOUHOU))
						&& validatedData.setHisuFlag((String) table.getData(i,COLUMN_INDEX_HISU_FLG))
						&& validatedData.setKagen((String) table.getData(i,COLUMN_INDEX_KAGEN))
						&& validatedData.setJyougen((String) table.getData(i,COLUMN_INDEX_JYOUGEN))
						&& validatedData.setDsJyougen((String) table.getData(i,COLUMN_INDEX_DS_JYOUGEN))
						&& validatedData.setDsKagen((String) table.getData(i,COLUMN_INDEX_DS_KAGEN))
						&& validatedData.setJsJyougen((String) table.getData(i,COLUMN_INDEX_JS_JYOUGEN))
						&& validatedData.setJsKagen((String) table.getData(i,COLUMN_INDEX_JS_KAGEN))
						&& validatedData.setTanni((String) table.getData(i,COLUMN_INDEX_TANI))
						&& validatedData.setKijyunchiHanni((String) table.getData(i, COLUMN_INDEX_KIJYUNTI_HANI))
						&& validatedData.setKensaTanka((String) table.getData(i, COLUMN_INDEX_TANKA_KENSIN))
						&& validatedData.setNote((String) table.getData(i,COLUMN_INDEX_BIKOU))
				) {
					if (validateAsRegisterPushed(validatedData)) {
						if (validatedData.isValidateAsDataSet()) {
							String curHokenjyaNumber = new String(
									((String) jComboBox_HokenjyaNumber.getSelectedItem()).substring(0, 8));

							Query = new String(
									"UPDATE T_KENSHINMASTER SET "
											+ "KOUMOKU_NAME = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getKensaKoumokuName())
											+ "HISU_FLG = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getHisuFlag())
											+ "KAGEN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getKagen())
											+ "JYOUGEN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getJyougen())
											+ "DS_JYOUGEN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getDsJyougen())
											+ "DS_KAGEN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getDsKagen())
											+ "JS_JYOUGEN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getJsJyougen())
											+ "JS_KAGEN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getJsKagen())
											+ "TANI = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getTanni())
											+ "KIJYUNTI_HANI = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getKijyunchiHanni())
											+ "TANKA_KENSIN = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getKensaTanka())
											+ "KENSA_HOUHOU = "
											+ JQueryConvert.queryConvertAppendComma(validatedData.getKensaHouhou())
											+ "BIKOU = "
											+ JQueryConvert.queryConvert(validatedData.getNote())
											+ "WHERE "
											+ "HKNJANUM = "
											+ JQueryConvert.queryConvert(curHokenjyaNumber)
											+ " AND "
											+ "KOUMOKU_CD = "
											+ JQueryConvert.queryConvert(validatedData.getKensaKoumokuCode()));

							JApplication.kikanDatabase.sendNoResultQuery(Query);
						}
					}
				} else {
					// 値のチェックでエラーが発生した段階でロールバックする。
					try {
						JApplication.kikanDatabase.rollback();
					} catch (Exception e1) {
						JErrorMessage.show("M0000", this);
						System.exit(1);
					}
					return;
				}
			}

			JApplication.kikanDatabase.Commit();
			dispose();

		} catch (SQLException f) {
			f.printStackTrace();
			JErrorMessage.show("M3822", this);
			try {
				System.out.println("RollBack!@202");
				JApplication.kikanDatabase.rollback();
			} catch (SQLException g) {
				g.printStackTrace();
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}

			return;
		}
	}

	/**
	 * コンボボックスのイベント処理
	 */
	public void changedHokenjyaNumber(ItemEvent e) {
		// edit s.inoue 2009/11/15
		// initTable();
		searchRefresh();
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == jComboBox_HokenjyaNumber) {
			changedHokenjyaNumber(e);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}else if (source == jButton_Import) {
			logger.info(jButton_Import.getText());
			pushedImportButton(e);
		}else if (source == jButton_Export){
			logger.info(jButton_Export.getText());
			pushedExportButton(e);
		}else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);
		}
	}


	// add h.yoshitama 2009/09/18
	/**
	 * セルの色に最新の状態を反映する。
	 */
	private void refreshTableCellColor() {

		cellColors.clear();
		for (int i = 0; i < table.getRowCount(); i++) {
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 3)));
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 4)));
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 5)));
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 6)));
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 9)));
			cellColors.add(new JSimpleTableCellRendererData(COLOR_ABLE,new JSimpleTableCellPosition(i, 10)));
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
			case KeyEvent.VK_F1:
				logger.info(jButton_End.getText());
				dispose();break;
			case KeyEvent.VK_F4:
				logger.info(jButton_Import.getText());
				pushedImportButton(null);break;
			case KeyEvent.VK_F5:
				logger.info(jButton_Export.getText());
				pushedExportButton(null);break;
			case KeyEvent.VK_F12:
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

