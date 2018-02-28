//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Component;
//import java.awt.event.*;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.*;
//
//import javax.swing.JButton;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.event.TableModelListener;
//
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
//import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorHokenjyaResultFrameData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorKenshinPatternResultFrameData;
//
///**
// * 健診パターンメンテナンス（一覧）
// */
//public class JKenshinPatternMaintenanceListFrameCtrl extends
//		JKenshinPatternMaintenanceListFrame {
//	private JSimpleTable model = new JSimpleTable();
//
//	private ArrayList<Hashtable<String, String>> result = null;  //  @jve:decl-index=0:
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	// add s.inoue 2010/03/05
//	private static org.apache.log4j.Logger logger =
//		Logger.getLogger(JKenshinPatternMaintenanceListFrameCtrl.class);
//
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
//	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;
//	private static Vector<Vector<String>> CSVItems = null;
//	private IDialog filePathDialog;
//	private JKenshinPatternMaintenanceAddFrameData validatedData = new JKenshinPatternMaintenanceAddFrameData();
//
//	private static String saveTitle= "csvファイル保存先選択";
//	private static String selectTitle= "csvファイル選択";
//	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
//	private static String selectDialogTitle= "csvファイルを選択してください";
//
//	private static final String[] TABLE_COLUMNS = {
//		"K_P_NO", "K_P_NAME", "BIKOU"
//		 };
//	private static final String[] TABLE_DETAIL_COLUMNS = {
//		"LOW_ID", "KOUMOKU_CD"
//	};
//
//
//
//	public JKenshinPatternMaintenanceListFrameCtrl() {
//		//テーブルの登録
//		JSimpleTableScrollPanel table = new JSimpleTableScrollPanel(model);
//		jPanel.add(table);
//
//		init();
//
//		model.addKeyListener(new JEnterEvent(this, jButton_Edit));
//
//		// ダブルクリックの処理
//		model.addMouseListener(new JSingleDoubleClickEvent(this, jButton_Edit));
//
//		// focus制御
//		// edit s.inoue 2009/10/07
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		// edit s.inoue 2009/11/09
//		this.focusTraversalPolicy.setDefaultComponent(model);
//		this.focusTraversalPolicy.addComponent(model);
//		// edit s.inoue 2010/04/23
//		this.focusTraversalPolicy.addComponent(this.jButton_Import);
//		this.focusTraversalPolicy.addComponent(this.jButton_Export);
//		this.focusTraversalPolicy.addComponent(this.jButton_Add);
//		this.focusTraversalPolicy.addComponent(this.jButton_NoteEdit);
//		this.focusTraversalPolicy.addComponent(this.jButton_Edit);
//		this.focusTraversalPolicy.addComponent(this.jButton_Copy);
//		this.focusTraversalPolicy.addComponent(this.jButton_Delete);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//
//		// add s.inoue 2009/12/02
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//	}
//
//	/**
//	 * テーブルの初期化
//	 */
//	private void init() {
//		String query =
//			"SELECT * FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO ASC";
//
//		this.result = new ArrayList<Hashtable<String, String>>();
//		Hashtable<String, String> ResultItem = new Hashtable<String, String>();
//		String[] row = new String[3];
//		JSimpleTableCellPosition[] forbitCells = { new JSimpleTableCellPosition(-1, -1) };
//		model.setPreferedColumnWidths(new int[] { 100, 150, 300 });
//
//		// テーブルヘッダの初期化
//		model.clearAllColumn();
//		model.clearAllRow();
//
//		model.addHeader("健診パターンNo");
//		model.addHeader("健診パターン名称");
//		model.addHeader("備考");
//
//		model.setCellEditForbid(forbitCells);
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show(
//					"M3920", this);
//			dispose();
//			return;
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			ResultItem = result.get(i);
//			row[0] = new String(ResultItem.get("K_P_NO"));
//			row[1] = new String(ResultItem.get("K_P_NAME"));
//			row[2] = new String(ResultItem.get("BIKOU"));
//			model.addData(row);
//		}
//
//		this.model.getSelectionModel().addListSelectionListener(
//				new ListSelectionListener() {
//
//					public void valueChanged(ListSelectionEvent e) {
//						updateButtonsState();
//					}
//				});
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
//
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton(ActionEvent e) {
//		dispose();
//	}
//
//	// add s.inoue 2010/03/09
//	/**ImportStart↓↓↓↓↓**********************************************************/
//	/**
//	 * 取込ボタン
//	 */
//	public void pushedImportButton( ActionEvent e )
//	{
//		try {
//			filePathDialog = DialogFactory.getInstance().createDialog(this,null);
//
//			filePathDialog.setMessageTitle(selectTitle);
//			filePathDialog.setDialogTitle(selectDialogTitle);
//			filePathDialog.setEnabled(false);
//			filePathDialog.setDialogSelect(true);
//			filePathDialog.setVisible(true);
//
//			// edit s.inoue 2010/03/25
//			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
//				return;
//
//			String filePath = filePathDialog.getFilePath();
//			// add s.inoue 2010/03/05
//			File fileImport = new File(filePath);
//			if(!fileImport.exists())
//				return;
//			importCsvData(filePath);
//			init();
//		} catch (Exception ex) {
//			JErrorMessage.show("M3937",this);
//			logger.error(ex.getMessage());
//		}
//	}
//
//	// edit s.inoue 2010/03/04
//	/* 取込処理 */
//	private void importCsvData(String filePath){
//
//		RETURN_VALUE retValue = JErrorMessage.show("M3938", this);
//
//		// cancel時
//		if (retValue == RETURN_VALUE.NO){
//			return;
//		}else if (retValue == RETURN_VALUE.YES){
//
//			JImportMasterErrorKenshinPatternResultFrameData data = new JImportMasterErrorKenshinPatternResultFrameData();
//
//			// CSV読込処理
//			reader = new JCSVReaderStream();
//
//			try {
//				reader.openCSV(filePath,JApplication.CSV_CHARSET);
//			} catch (IOException e) {
//				JErrorMessage.show("M3939",this);
//				logger.error(e.getMessage());
//			}
//
//			CSVItems = reader.readAllTable();
//
//			try {
//				JApplication.kikanDatabase.Transaction();
//
//				int csvCount = CSVItems.size();
//
//				if(csvCount == 0){
//					JErrorMessage.show("M3940",this);
//					return;
//				}
//
//				// 既存保険者-健診マスタ全削除
//				kenshinMasterDelete();
//
//				// 検査結果データ取込処理
//				for (int i = 1; i < csvCount; i++) {
//
//					Vector<String> insertRow = new Vector<String>();
//
//					insertRow =CSVItems.get(i);
//
//					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
//					setCSVItemsToDB(data,insertRow);
//
//					// validate処理
//					if (!validateData(data))
//						return;
//
//					kenshinPatternMasterRegister(data);
//					kenshinPatternMasterDetailRegister(data);
//					// del s.inoue 2010/03/09
//					// kenshinMasterRegister(data);
//				}
//
//				JApplication.kikanDatabase.Commit();
//
//				String[] messageParams = {String.valueOf(csvCount-1)};
//				JErrorMessage.show("M3941",this,messageParams);
//			} catch (SQLException e) {
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e1) {}
//				JErrorMessage.show("M3942",this);
//				logger.error(e.getMessage());
//			}
//		}
//	}
//
//	// edit s.inoue 2010/03/04
//	/* csvデータ取込 */
//	private void setCSVItemsToDB(JImportMasterErrorKenshinPatternResultFrameData data,
//			Vector<String> insertRow){
//
//		data.CSV_COLUMN_KENSHIN_PM_K_P_NO = reader.rmQuart(insertRow.get(0));
//		data.CSV_COLUMN_KENSHIN_K_P_NAME = reader.rmQuart(insertRow.get(1));
//		data.CSV_COLUMN_KENSHIN_BIKOU = reader.rmQuart(insertRow.get(2));
//
//		data.CSV_COLUMN_KENSHIN_LOW_ID = new String[ insertRow.size() ];
//		data.CSV_COLUMN_KENSHIN_KOUMOKU_CD = new String[ insertRow.size() ];
//
//		for (int i = 0; i < insertRow.size()-3; i++) {
//			data.CSV_COLUMN_KENSHIN_LOW_ID[i] = String.valueOf(i);
//			data.CSV_COLUMN_KENSHIN_KOUMOKU_CD[i] = insertRow.get(3 + i);
//		}
//	}
//
//	// edit s.inoue 2010/03/04
//	/* validate処理 */
//	private boolean validateData(JImportMasterErrorKenshinPatternResultFrameData data) {
//
//		boolean rettanka = false;
//
//		rettanka= validatedData.setPatternNumber(data.CSV_COLUMN_KENSHIN_PM_K_P_NO)
//			&& validatedData.setPatternName(data.CSV_COLUMN_KENSHIN_K_P_NAME)
//			&& validatedData.setNote(data.CSV_COLUMN_KENSHIN_BIKOU);
//		// パターン詳細は別途処理
////			&& validatedData.set(data.CSV_COLUMN_KENSHIN_LOW_ID)
////			&& validatedData.setShindenzuCode(data.CSV_COLUMN_KENSHIN_KOUMOKU_CD);
//		return rettanka;
//	}
//
//	/**
//	 * 既存保険者,健診マスタ削除
//	 * @throws SQLException
//	 */
//	private void kenshinMasterDelete(){
//		// hknjyanumによる削除※履歴を含め削除
//		deleteKenshinPattern();
//	}
//
////	private void kenshinMasterRegister(JImportMasterErrorKenshinPatternResultFrameData data)
////		throws SQLException
////	{
////
////		try {
////				// 新規追加の場合はさらに健診項目マスタへの追加も行う
////				ArrayList<Hashtable<String, String>> resultKenshin = new ArrayList<Hashtable<String, String>>();
////				Hashtable<String, String> resultItemKenshin = new Hashtable<String, String>();
////				StringBuffer buffer = new StringBuffer(
////						"SELECT * FROM T_KENSHINMASTER WHERE HKNJANUM = "
////								+ JQueryConvert.queryConvert("99999999"));
////
////				resultKenshin = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
////
////				for (int i = 0; i < resultKenshin.size(); i++) {
////					resultItemKenshin = resultKenshin.get(i);
////
////					buffer = new StringBuffer("INSERT INTO T_KENSHINMASTER ( "
////							+ "HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,DATA_TYPE,MAX_BYTE_LENGTH,XML_PATTERN,"
////							+ "XML_DATA_TYPE,XML_KENSA_CD,OBSERVATION,AUTHOR,AUTHOR_KOUMOKU_CD,"
////							+ "KENSA_GROUP,KENSA_GROUP_CD,KEKKA_OID,KOUMOKU_OID,HISU_FLG,KAGEN,"
////							+ "JYOUGEN,DS_JYOUGEN,JS_JYOUGEN,DS_KAGEN,JS_KAGEN,KIJYUNTI_HANI,"
////							+ "TANI,KENSA_HOUHOU,TANKA_KENSIN,BIKOU,XMLITEM_SEQNO"
////							+ " )VALUES( "
////							+ JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_NAME"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DATA_TYPE"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("MAX_BYTE_LENGTH"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_PATTERN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_DATA_TYPE"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_KENSA_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("OBSERVATION"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR_KOUMOKU_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KEKKA_OID"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_OID"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("HISU_FLG"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KAGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JYOUGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_JYOUGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_JYOUGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_KAGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_KAGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KIJYUNTI_HANI"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANI"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_HOUHOU"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANKA_KENSIN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("BIKOU"))
////							+ JQueryConvert.queryConvert(resultItemKenshin.get("XMLITEM_SEQNO")) +
////							")");
////
////					JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
////				}
////		} catch (SQLException f) {
////			f.printStackTrace();
////			JErrorMessage.show("M3212", this);
////			try {
////				JApplication.kikanDatabase.rollback();
////				return;
////			} catch (SQLException g) {
////				g.printStackTrace();
////				JErrorMessage.show("M0000", this);
////				System.exit(1);
////			}
////		}
////	}
//
//	/**
//	 * CSVデータ登録
//	 * @throws SQLException
//	 */
//	private void kenshinPatternMasterRegister(JImportMasterErrorKenshinPatternResultFrameData data)
//		throws SQLException
//	{
//		StringBuffer buffer = new StringBuffer();
//
//		if( validateAsRegisterPushed( validatedData ) )
//		{
//			if( validatedData.isValidateAsDataSet() )
//			{
//
//			buffer = new StringBuffer("INSERT INTO T_KENSHIN_P_M (K_P_NO,K_P_NAME,BIKOU) VALUES (" +
//					JQueryConvert.queryConvertAppendComma(validatedData.getPatternNumber()) +
//					JQueryConvert.queryConvertAppendComma(validatedData.getPatternName()) +
//					JQueryConvert.queryConvert(validatedData.getNote()) +
//					")");
//			}
//		}
//		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//
//	}
//
//	/**
//	 * CSVデータ登録
//	 * @throws SQLException
//	 */
//	private void kenshinPatternMasterDetailRegister(JImportMasterErrorKenshinPatternResultFrameData data)
//		throws SQLException
//	{
//
//		StringBuilder sb = new StringBuilder();
//
//		// 複製元の健診パターン詳細の行の健診パターンNoを新規パターンNoに書き換え挿入を行う
//		String strPM_KPNO = data.CSV_COLUMN_KENSHIN_PM_K_P_NO;
//
//		for (int i = 0; i < data.CSV_COLUMN_KENSHIN_LOW_ID.length; i++) {
//
//			String koumokuCd = data.CSV_COLUMN_KENSHIN_KOUMOKU_CD[i];
//			String lowId = data.CSV_COLUMN_KENSHIN_LOW_ID[i];
//			if (lowId == null)
//				break;
//
//			if (sb.length() > 0)
//				sb.delete(0, sb.length() );
//
//			sb.append("INSERT INTO T_KENSHIN_P_S (K_P_NO,KOUMOKU_CD,LOW_ID) VALUES ("
//							+ JQueryConvert.queryConvertAppendComma(strPM_KPNO)
//							+ JQueryConvert.queryConvertAppendComma(koumokuCd)
//							+ JQueryConvert.queryConvert(lowId)
//							+ ")");
//			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
//		}
//	}
//
//	/**
//	 * 登録ボタンを押した際の必須項目等を検証
//	 */
//	protected boolean validateAsRegisterPushed(JKenshinPatternMaintenanceAddFrameData data)
//	{
//		if( validatedData.getPatternNumber().equals("") )
//		{
//			JErrorMessage.show("M5501",this);
//			return false;
//		}
//
//		if( validatedData.getPatternName().equals("") )
//		{
//			JErrorMessage.show("M5502",this);
//			return false;
//		}
//
//		data.setValidateAsDataSet();
//		return true;
//	}
//	/**ImportEnd↑↑↑↑↑**********************************************************/
//
//
//	/**ExportStart↓↓↓↓↓**********************************************************/
//	// add s.inoue 2010/02/25
//	/**
//	 * 書出ボタン
//	 */
//	public void pushedExportButton( ActionEvent e )
//	{
//		try {
//			String saveFileName = JPath.createDirectoryUniqueName("KenshinPatternMaster");
//
//			String defaltPath = JPath.getDesktopPath() +
//				File.separator +
//				saveFileName;
//
//			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
//			filePathDialog.setMessageTitle(saveTitle);
//			filePathDialog.setDialogTitle(savaDialogTitle);
//			filePathDialog.setEnabled(false);
//			filePathDialog.setDialogSelect(false);
//			filePathDialog.setVisible(true);
//
//			// edit s.inoue 2010/03/25
//			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
//				return;
//
//			String filePath = filePathDialog.getFilePath();
//			if (filePath.equals(""))
//				return;
//			exportCsvData(filePath);
//
//		} catch (Exception ex) {
//			JErrorMessage.show("M3943", this);
//			logger.error(ex.getMessage());
//		}
//	}
//
//	/* export処理 */
//	private void exportCsvData(String filePath){
//		JImportMasterErrorHokenjyaResultFrameData data = new JImportMasterErrorHokenjyaResultFrameData();
//
//		// CSV読込処理
//		writer = new JCSVWriterStream();
//
//		try {
//			writer.writeTable(getExportData());
//			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
//		} catch (IOException e) {
//			JErrorMessage.show("M3944", this);
//			logger.error(e.getMessage());
//		}
//	}
//
//	/* DBよりデータ取得*/
//	private Vector<Vector<String>> getExportData(){
//
//		Vector<Vector<String>> tmpTable = new Vector<Vector<String>>();
//
//		ArrayList<Hashtable<String, String>> result
//			= new ArrayList<Hashtable<String, String>>();
//
//		StringBuilder sb = new StringBuilder();
//
//		sb.append(" SELECT PM.K_P_NO, PM.K_P_NAME, PM.BIKOU, PS.LOW_ID, PS.KOUMOKU_CD ");
//		sb.append(" from T_KENSHIN_P_M PM,T_KENSHIN_P_S PS ");
//		sb.append(" where PM.K_P_NO = PS.K_P_NO ");
//		sb.append(" and PM.K_P_NO <> '-1' ");
//		sb.append(" and PM.K_P_NO <> '1' ");
//		sb.append(" and PM.K_P_NO <> '2' ");
//		sb.append(" order by PM.k_p_no,PS.LOW_ID");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		// ↓header設定
//		String[] allArray = new String[TABLE_COLUMNS.length + TABLE_DETAIL_COLUMNS.length];
//		System.arraycopy(TABLE_COLUMNS, 0, allArray, 0, TABLE_COLUMNS.length);
//		System.arraycopy(TABLE_DETAIL_COLUMNS, 0, allArray, TABLE_COLUMNS.length, TABLE_DETAIL_COLUMNS.length);
//
//		Vector colmn = new Vector<String>();
//		List l = java.util.Arrays.asList(allArray);
//		for (Iterator item = l.iterator(); item.hasNext();) {
//			colmn.add((String)item.next());
//		}
//		tmpTable.add(colmn);
//		// ↑
//
//		Hashtable<String, String> resultItem = new Hashtable<String, String>();
//		// edit s.inoue 2010/05/07
//		// Hashtable<String, String> resultItemDetail = new Hashtable<String, String>();
//
//		String preKPNo= "";
//
//		for( int i=0; i<result.size(); ++i )
//		{
//			resultItem = result.get(i);
//
//			Vector<String> data = new Vector<String>();
//
//			String keyKPNo =resultItem.get(TABLE_COLUMNS[0]).trim();
//
//			if (keyKPNo.equals(preKPNo))
//				continue;
//
//			data.add(keyKPNo);
//			data.add(resultItem.get(TABLE_COLUMNS[1]).trim());
//			data.add(resultItem.get(TABLE_COLUMNS[2]).trim());
//
//			if (resultItem.get(TABLE_DETAIL_COLUMNS[0]) == null)
//				break;
//
//			// 詳細
//			// edit s.inoue 2010/05/07
//			Hashtable<String, String> resultItemDetail = new Hashtable<String, String>();
//
//			for (int j = 0; j < result.size(); j++) {
//				resultItemDetail = result.get(j);
//				System.out.println("項目コード：" + resultItemDetail.get(TABLE_DETAIL_COLUMNS[1]).trim());
//				// edit s.inoue 2010/05/07
//				if (keyKPNo.equals(resultItemDetail.get(TABLE_COLUMNS[0]).trim())){
//					data.add(resultItemDetail.get(TABLE_DETAIL_COLUMNS[0]).trim());
//					data.add(resultItemDetail.get(TABLE_DETAIL_COLUMNS[1]).trim());
//				}
//			}
//
//			tmpTable.add(data);
//			preKPNo = resultItem.get(TABLE_COLUMNS[0]).trim();
//
//		}
//
//		Vector<Vector<String>> newTable = new Vector<Vector<String>>();
//		newTable.addAll(tmpTable);
//
//		return newTable;
//	}
//	/**ExportEnd↑↑↑↑↑**********************************************************/
//
//	/**
//	 * 追加ボタン
//	 */
//	public void pushedAddButton(ActionEvent e) {
//		int PatterNumber = getNextPatternNumber();
//
//		if (PatterNumber != -1) {
//			JScene.CreateDialog(this,
//					new JKenshinPatternMaintenanceAddFrameCtrl(PatterNumber,
//							new String(""), new String(""),
//							JKenshinPatternMaintenanceAddFrameCtrl.ADD_MODE),
//					new WindowRefreshEvent());
//		}
//	}
//
//	/**
//	 * 複製ボタン
//	 */
//	public void pushedCopyButton(ActionEvent e) {
//
//		int nextNumber = getNextPatternNumber();
//
//		int selectedRowIndex = this.model.getSelectedRow();
//		String patternNo = (String)this.model.getData(selectedRowIndex, 0);
//
//		if (model.getRowCount() == 0)
//			return;
//		if (nextNumber != -1) {
//			JScene.CreateDialog(this,
//					new JKenshinPatternMaintenanceCopyFrameCtrl(
//							nextNumber, patternNo),
//					new WindowRefreshEvent());
//		}
//	}
//
//	/**
//	 * 削除ボタン
//	 */
//	public void pushedDeleteButton(ActionEvent e) {
//		if (model.getSelectedRowCount() > 0) {
//			RETURN_VALUE Ret = jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage
//					.show("M3925", this);
//
//			if (Ret == RETURN_VALUE.YES) {
//				int[] selectedRows = model.getSelectedRows();
//
//				for (int i = 0; i < model.getSelectedRowCount(); i++) {
//					String KenshinPatternNumber = (String) model.getData(
//							selectedRows[i], 0);
//					deleteKenshinPattern(KenshinPatternNumber);
//				}
//
//				init();
//			}
//		}
//	}
//
//	/* 健診パターン指定番号削除 */
//	private void deleteKenshinPattern(String KenshinPatternNumber){
//
//		try {
//
//			StringBuilder sb = new StringBuilder();
//
//			sb.append("DELETE FROM T_KENSHIN_P_M WHERE K_P_NO = ");
//			sb.append(JQueryConvert.queryConvert(KenshinPatternNumber));
//
//			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
//
//			StringBuilder sbKP = new StringBuilder();
//			sbKP.append("DELETE FROM T_KENSHIN_P_S WHERE K_P_NO = ");
//			sbKP.append(JQueryConvert.queryConvert(KenshinPatternNumber));
//
//			JApplication.kikanDatabase.sendNoResultQuery(sbKP.toString());
//		} catch (SQLException f) {
//			f.printStackTrace();
//			JErrorMessage.show("M3922", this);
//		}
//	}
//
//	/* all削除 */
//	private void deleteKenshinPattern(){
//
//		try {
//
//			StringBuilder sb = new StringBuilder();
//			StringBuilder sbWhere = new StringBuilder();
//
//			sb.append("DELETE FROM T_KENSHIN_P_M ");
//			sbWhere.append(" WHERE K_P_NO <> '-1' ");
//			sbWhere.append(" AND K_P_NO <> '1' ");
//			sbWhere.append(" AND K_P_NO <> '2' ");
//
//			JApplication.kikanDatabase.sendNoResultQuery(sb.toString() + sbWhere.toString());
//
//			StringBuilder sbKP = new StringBuilder();
//			sbKP.append("DELETE FROM T_KENSHIN_P_S ");
//
//			JApplication.kikanDatabase.sendNoResultQuery(sbKP.toString() + sbWhere.toString());
//		} catch (SQLException f) {
//			f.printStackTrace();
//			JErrorMessage.show("M3922", this);
//		}
//	}
//	/**
//	 * 備考編集ボタン
//	 */
//	public void pushedNoteEditButton(ActionEvent e) {
//		if (model.getSelectedRowCount() > 0) {
//			int KenshinPatterNumber = model.getSelectedRow() + 1;
//			String KenshinPatternName = (String) model.getData(model
//					.getSelectedRow(), 1);
//			String KenshinPatternNote = (String) model.getData(model
//					.getSelectedRow(), 2);
//
//			JScene.CreateDialog(this,
//					new JKenshinPatternMaintenanceAddFrameCtrl(
//							KenshinPatterNumber, KenshinPatternName,
//							KenshinPatternNote,
//							JKenshinPatternMaintenanceAddFrameCtrl.EDIT_MODE),
//					new WindowRefreshEvent());
//		}
//	}
//
//	/**
//	 * 編集ボタン
//	 */
//	public void pushedEditButton(ActionEvent e) {
//		if (model.getSelectedRowCount() > 0) {
//			String KenshinPatterNumber = (String) model.getData(model
//					.getSelectedRow(), 0);
//			JScene.CreateDialog(this,
//					new JKenshinPatternMaintenanceEditFrameCtrl(
//							KenshinPatterNumber));
//		}
//	}
//
//	/**
//	 * 健診パターンの空き番号の取得
//	 */
//	private int getNextPatternNumber() {
//		ArrayList<Hashtable<String, String>> Items;
//		try {
//			Items = JApplication.kikanDatabase
//					.sendExecuteQuery("SELECT K_P_NO FROM T_KENSHIN_P_M");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show(
//					"M3920", this);
//			return -1;
//		}
//
//		for (int i = 1; i < Integer.MAX_VALUE; i++) {
//			boolean FindFlag = false;
//
//			for (int j = 0; j < Items.size(); j++) {
//				if (Items.get(j).get("K_P_NO").equals(String.valueOf(i))) {
//					FindFlag = true;
//				}
//			}
//
//			// trueでなければ空きがあるとする。
//			if (FindFlag == false) {
//				return i;
//			}
//		}
//
//		return -1;
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		Object source = e.getSource();
//		if (source == jButton_End) {
//			pushedEndButton(e);
//		}
//		else if (source == jButton_Import)
//		{
//			logger.info(jButton_Import.getText());
//			pushedImportButton(e);
//		}
//		else if (source == jButton_Export)
//		{
//			logger.info(jButton_Export.getText());
//			pushedExportButton(e);
//		}
//		else if (source == jButton_Add) {
//			pushedAddButton(e);
//		}
//
//		else if (source == jButton_Copy) {
//			pushedCopyButton(e);
//		}
//
//		else if (source == jButton_Delete) {
//			pushedDeleteButton(e);
//		}
//
//		else if (source == jButton_NoteEdit) {
//			pushedNoteEditButton(e);
//		}
//
//		else if (source == jButton_Edit) {
//			pushedEditButton(e);
//		}
//	}
//
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		// edit s.inoue 2010/03/24
//		case KeyEvent.VK_F4:
//			logger.info(jButton_Import.getText());
//			pushedImportButton(null);break;
//		case KeyEvent.VK_F5:
//			logger.info(jButton_Export.getText());
//			pushedExportButton(null);break;
//		case KeyEvent.VK_F6:
//			logger.info(jButton_Add.getText());
//			pushedAddButton(null);break;
//		case KeyEvent.VK_F7:
//			if (jButton_NoteEdit.isEnabled()){
//				logger.info(jButton_NoteEdit.getText());
//				pushedNoteEditButton(null);
//			}
//			break;
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Edit.getText());
//			pushedEditButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Copy.getText());
//			pushedCopyButton(null);break;
//		case KeyEvent.VK_F12:
//			if (jButton_Delete.isEnabled()){
//				logger.info(jButton_Delete.getText());
//				pushedDeleteButton(null);
//			}
//			break;
//		}
//	}
//
//	/**
//	 * 遷移先の画面から戻ってきた場合
//	 */
//	public class WindowRefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			//テーブルの再読み込みを行う
//			init();
//		}
//	}
//
//	/**
//	 * ボタンの状態を更新する。
//	 */
//	private void updateButtonsState() {
//
//		int[] selectedRowIndeces = model.getSelectedRows();
//		boolean singleRowButtonEnable = false;
//		boolean deleteButtonEnable = false;
//		// add h.yoshitama 2009/11/20
//		boolean noteEditButtonEnable = false;
//
//		/* 選択中の行数が 1 の場合 */
//		if (selectedRowIndeces.length == 1) {
//			singleRowButtonEnable = true;
//
//			String kpNo = this.result.get(selectedRowIndeces[0]).get("K_P_NO");
//
//			if (!kpNo.equals("1") && !kpNo.equals("2")) {
//				deleteButtonEnable = true;
//				// add h.yoshitama 2009/11/20
//				noteEditButtonEnable = true;
//			}
//		}
//
//		jButton_NoteEdit.setEnabled(singleRowButtonEnable);
//		jButton_Edit.setEnabled(singleRowButtonEnable);
//		jButton_Copy.setEnabled(singleRowButtonEnable);
//
//		jButton_Delete.setEnabled(deleteButtonEnable);
//		// add h.yoshitama 2009/11/20
//		jButton_NoteEdit.setEnabled(noteEditButtonEnable);
//	}
//}
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

