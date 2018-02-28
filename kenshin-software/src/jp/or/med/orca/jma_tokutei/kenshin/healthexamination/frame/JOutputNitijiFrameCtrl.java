//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JViewport;
//import javax.swing.ListSelectionModel;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.event.RowSorterEvent;
//import javax.swing.event.RowSorterListener;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.ListIterator;
//import java.util.TreeMap;
//import java.util.Vector;
//import java.awt.Component;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.InputEvent;
//import java.awt.event.ItemEvent;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.print.PrinterException;
//import java.sql.SQLException;
//import org.apache.log4j.Logger;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintSeikyuNitiji;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Nikeihyo;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JInputKessaiDataFrameCtrl;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellEditor;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
///**
// * 請求・HL7出力画面制御
// *
// * 処理が複雑なため、全体的にリファクタリングと処理の見直しを行なった。
// */
//public class JOutputNitijiFrameCtrl extends JOutputNitijiFrame
//{
//	private static Logger logger = Logger.getLogger(JOutputNitijiFrameCtrl.class);
//
//	// edit s.inoue 2009/10/26
//	// 一覧データ列項目
//	private static final int COLUMN_INDEX_CHECK = 0;
//	private static final int COLUMN_INDEX_NENDO = 1;
//	private static final int COLUMN_INDEX_JYUSHIN_SEIRI_NO = 2;
//	private static final int COLUMN_INDEX_KANANAME = 3;
//	private static final int COLUMN_INDEX_NAME = 4;
//	private static final int COLUMN_INDEX_SEX = 5;
//	private static final int COLUMN_INDEX_BIRTHDAY = 6;
//	private static final int COLUMN_INDEX_KENSA_NENGAPI = 7;
//	private static final int COLUMN_INDEX_HKNJANUM = 8;
//	private static final int COLUMN_INDEX_SIHARAI_DAIKOU_BANGO = 9;
//	private static final int COLUMN_INDEX_HIHOKENJYASYO_KIGOU = 10;
//	private static final int COLUMN_INDEX_HIHOKENJYASYO_NO = 11;
//	private static final int COLUMN_INDEX_TANKA_GOUKEI = 12;
//	private static final int COLUMN_INDEX_MADO_FUTAN_GOUKEI = 13;
//	private static final int COLUMN_INDEX_SEIKYU_KINGAKU = 14;
//	private static final int COLUMN_INDEX_UPDATE_TIMESTAMP = 15;
//
//	/** 保険者番号、支払代行機関選択欄の、番号と名称の区切り文字列 */
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";
//	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";
//	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から保険者";
//	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から代行機関";
//	// edit s.inoue 2009/10/26
//	private static final String[] header = {"", "年度", "受診券整理番号", "氏名（カナ）","氏名（漢字）", "性別",
//			"生年月日", "健診実施日","保険者番号","代行機関番号","被保険者証等記号", "被保険者証等番号",
//			"単価合計","窓口負担合計", "請求金額合計", "更新日付"};
//	// edit s.inoue 2009/10/26
//	private DefaultTableModel dmodel =null;
//	// edit s.inoue 2009/10/26
//    private TableRowSorter<TableModel> sorter = null;
//
//    // edit s.inoue 2009/10/23
//	// private JSimpleTable model = new JSimpleTable();
//	// private JSimpleTable modelfixed = new JSimpleTable();
//    private JSimpleTable table =null;
//	private JSimpleTable modelfixed =null;
//
//	// フレームの状態を管理する
//	public enum JOutputHL7FrameState {
//		/* 初期状態 */
//		STATUS_INITIALIZED,
//		/* 検索後 */
//		STATUS_AFTER_SEARCH,
//		/* 請求後 */
//		STATUS_AFTER_SEIKYU,
//		/* HL7変換後 */
//		STATUS_AFTER_HL7
//	}
//
//	// 全選択ボタンの状態
//	boolean isAllSelect = true;
//
//	/** 検索結果（画面上のデータ） */
//	private ArrayList<Hashtable<String, String>> result = null;
//
//	private JOutputHL7FrameState state = JOutputHL7FrameState.STATUS_INITIALIZED;
////	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//
//	private IDialog messageDialog;
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	/**
//	 * フレームの状態によって押せるボタンなどを制御する
//	 */
//	public void buttonCtrl()
//	{
//		switch(state)
//		{
//		case STATUS_INITIALIZED:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_SEARCH:
//			jButton_Seikyu.setEnabled(true);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
////			forbitCells.clear();
//			break;
//
//		case STATUS_AFTER_SEIKYU:
//			jButton_Seikyu.setEnabled(true);
//			jButton_SeikyuEdit.setEnabled(true);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_HL7:
//			jButton_Seikyu.setEnabled(false);
//			jButton_SeikyuEdit.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//		}
//	}
//
//	// edit s.inoue 2009/10/26
//	/*
//	 * 検索リフレッシュ処理
//	 */
//	private void searchRefresh(){
//
//		/* 列幅の設定 */
//		// edit s.inoue 2010/07/07
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
//		table.setPreferedColumnWidths(new int[] {100, 40, 70, 70, 70,80, 110, 110, 80, 80, 80, 140});
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//	    dmodel.setDataVector(resultRefresh(),header);
//
//	    // edit s.inoue 2010/02/16
//	    if (resultRefresh().length == 0){
//	    	JErrorMessage.show("M4757", this);
//	    }
//
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i < 4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                // edit s.inoue 2010/07/07
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//		    }else{
//	            modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//	        }
//        }
//
//	   // add s.inoue 2009/10/26
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=1;i<columnsfix.getColumnCount();i++) {
//
//		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//	   }
//
//	   TableColumnModel columns = table.getColumnModel();
//	   for(int i=0;i<columns.getColumnCount();i++) {
//
//		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
//	   }
//
//	    // edit s.inoue 2009/12/07
//		// 固定,変動リフレッシュ
//	    modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
////		table.setCellEditForbid(forbitCells);
////		modelfixed.setCellEditForbid(forbitFixedCells);
//	    initilizeSeikyu();
//	    table.refreshTable();
//
//	    // edit s.inoue 2010/02/24
//		// 初期選択
//	    int count = 0;
//		if (table.getRowCount() > 0) {
//			table.setRowSelectionInterval(0, 0);
//			count = table.getRowCount();
//			} else {
//				jTextField_Name.requestFocus();
//		}
//		jLabel_count.setText(count + " 件");
//
//	}
//
//	// add s.inoue 2010/01/19
//	private void checkKigenSetting(String kenshinDate,String limitDataStart,String limitDataEnd){
//		if (!kenshinDate.equals("") &&
//			!limitDataStart.equals("") &&
//				!limitDataEnd.equals("")){
//			if(!(Integer.parseInt(limitDataStart) <= Integer.parseInt(kenshinDate) &&
//					Integer.parseInt(kenshinDate) <= Integer.parseInt(limitDataEnd))){
//				JErrorMessage.show("M4756", this);
//			}
//		}
//	}
//
//	/**
//	 * テーブルに表示する内容を更新する。
//	 */
//	public Object[][] resultRefresh()
//	{
//		// edit s.inoue 2009/10/26
//		Hashtable<String, String> resultItem;
//		Object[][] rowcolumn = null;
//
//		try {
//			JOutputNitijiFrameData validatedData = new JOutputNitijiFrameData();
//
//			/* コンボボックスで選択されている保険者番号、名称を取得する。 */
//			String hokenjaNumberAndNameString = (String)jComboBox_HokenjyaNumber.getSelectedItem();
//			String hokenjaNumber = "";
//			String hokenjaName = "";
//			if (hokenjaNumberAndNameString != null && ! hokenjaNumberAndNameString.isEmpty()) {
//				String[] hokenjaNumberAndName = hokenjaNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				hokenjaNumber = hokenjaNumberAndName[0];
//
//				if (hokenjaNumberAndName.length > 1) {
//					hokenjaName = hokenjaNumberAndName[1];
//				}
//			}
//
//			/* コンボボックスで選択されている支払代行機関番号、名称を取得する。 */
//			String daikoNumberAndNameString = (String)jComboBox_SeikyuKikanNumber.getSelectedItem();
//			String daikoNumber = "";
//			String daikoName = "";
//			if (daikoNumberAndNameString != null && ! daikoNumberAndNameString.isEmpty()) {
//				String[] daikoNumberAndName = daikoNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				daikoNumber = daikoNumberAndName[0];
//
//				if (daikoNumberAndName.length > 1) {
//					daikoName = daikoNumberAndName[1];
//				}
//			}
//
//			if(
//					/* 被保険者証等記号 */
//					validatedData.setHihokenjyasyo_kigou(jTextField_Hihokenjyasyo_kigou.getText()) &&
//					/* 被保険者証等番号 */
//					validatedData.setHihokenjyasyo_Number(jTextField_Hihokenjyasyo_Number.getText()) &&
//					/* 実施日（開始） */
//					validatedData.setJissiBeginDate(jTextField_JissiBeginDate.getText()) &&
//					/* 実施日（終了） */
//					validatedData.setJissiEndDate(jTextField_JissiEndDate.getText()) &&
//					/* 保険証番号 */
//					validatedData.setHokenjyaNumber(hokenjaNumber) &&
//					/* 保険者名称 */
//					validatedData.setHokenjyaName(hokenjaName) &&
//					/* 請求先機関番号 */
//					validatedData.setSeikyuKikanNumber(daikoNumber) &&
//					/* 請求先機関名称 */
//					validatedData.setSeikyuKikanName(daikoName) &&
//					/* 氏名 */
//					validatedData.setName(jTextField_Name.getText())
//			){
//				/* Where 句を除いたSQLを作成する。 */
//				StringBuffer queryBuffer = this.createSearchQueryWithoutCondition();
//
//				/* 条件文を付加する。 */
//				this.appendCondition(queryBuffer, validatedData);
//
//				/* SQLを実行する。 */
//				result = JApplication.kikanDatabase.sendExecuteQuery(queryBuffer.toString());
//
//				// edit s.inoue 2009/10/26
//				/* 検索結果をテーブルに追加する。 */
//				// retData = addRowToTable();
//				rowcolumn = new Object[result.size()][16];
//				// edit s.inoue 2009/11/14
//				NumberFormat kingakuFormat = NumberFormat.getNumberInstance();
//
//				for(int i = 0;i < result.size();i++ )
//				{
//					// edit s.inoue 2009/10/26
//					// Vector<String> rowcolumn = new Vector<String>();
//					// Vector<String> rowfixed = new Vector<String>();
//
//					resultItem = result.get(i);
//
//					// edit s.inoue 2009/10/26
//					/* チェックボックス */
//					// rowcolumn.add(null);
//					rowcolumn[i][COLUMN_INDEX_CHECK] = null;
//					/* 年度 */
//					// rowcolumn.add(resultItem.get("NENDO"));
//					rowcolumn[i][COLUMN_INDEX_NENDO] = resultItem.get("NENDO");
//					/* 受診券整理番号 */
//					// rowcolumn.add(resultItem.get("JYUSHIN_SEIRI_NO"));
//					rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = resultItem.get("JYUSHIN_SEIRI_NO");
//					/* 氏名（カナ） */
//					// rowcolumn.add(resultItem.get("KANANAME"));
//					rowcolumn[i][COLUMN_INDEX_KANANAME] = resultItem.get("KANANAME");
//					/* 氏名（名前） */
//					// rowcolumn.add(resultItem.get("NAME"));
//					rowcolumn[i][COLUMN_INDEX_NAME] = resultItem.get("NAME");
//
//					/* 性別 */
//					// edit s.inoue 2009/10/26
//					// String sexCode = resultItem.get("SEX");
//					// String sexText = "";
//					// if (sexCode.equals("1")) {
//					//	sexText = "男性";
//					// }
//					// else if(sexCode.equals("2")){
//					// 	sexText = "女性";
//					// }
//					// rowcolumn.add(sexText);
//					rowcolumn[i][COLUMN_INDEX_SEX] = resultItem.get("SEX").equals("1") ? "男性" : "女性";
//					/* 生年月日 */
//					// rowcolumn.add(resultItem.get("BIRTHDAY"));
//					rowcolumn[i][COLUMN_INDEX_BIRTHDAY] = resultItem.get("BIRTHDAY");
//					/* 健診実施日 */
//					// rowcolumn.add(resultItem.get("KENSA_NENGAPI"));
//					rowcolumn[i][COLUMN_INDEX_KENSA_NENGAPI] = resultItem.get("KENSA_NENGAPI");
//					/* 保険者番号 */
//					// rowcolumn.add(resultItem.get("HKNJANUM"));
//					rowcolumn[i][COLUMN_INDEX_HKNJANUM] = resultItem.get("HKNJANUM");
//					/* 支払代行機関番号 */
//					// rowcolumn.add(resultItem.get("SIHARAI_DAIKOU_BANGO"));
//					rowcolumn[i][COLUMN_INDEX_SIHARAI_DAIKOU_BANGO] = resultItem.get("SIHARAI_DAIKOU_BANGO");
//					/* 被保険者証等記号 */
//					// rowcolumn.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
//					rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_KIGOU] = resultItem.get("HIHOKENJYASYO_KIGOU");
//					/* 被保険者証等番号 */
//					// rowcolumn.add(resultItem.get("HIHOKENJYASYO_NO"));
//					rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_NO] = resultItem.get("HIHOKENJYASYO_NO");
//					/* 単価合計 */
//					// rowcolumn.add(resultItem.get("TANKA_GOUKEI"));
//					String tankaGoukei = resultItem.get("TANKA_GOUKEI");
//					tankaGoukei = (tankaGoukei.equals("")) ? "0": tankaGoukei;
//					rowcolumn[i][COLUMN_INDEX_TANKA_GOUKEI] =kingakuFormat.format(Long.parseLong(tankaGoukei));
//					/* 窓口負担合計 */
//					// rowcolumn.add(resultItem.get("MADO_FUTAN_GOUKEI"));
//					String madogutiGoukei = resultItem.get("MADO_FUTAN_GOUKEI");
//					madogutiGoukei = (madogutiGoukei.equals("")) ? "0": madogutiGoukei;
//					rowcolumn[i][COLUMN_INDEX_MADO_FUTAN_GOUKEI] = kingakuFormat.format(Long.parseLong(madogutiGoukei));
//					/* 請求金額合計 */
//					// rowcolumn.add(resultItem.get("SEIKYU_KINGAKU"));
//					String seikyuGoukei = resultItem.get("SEIKYU_KINGAKU");
//					seikyuGoukei = (seikyuGoukei.equals("")) ? "0": seikyuGoukei;
//					rowcolumn[i][COLUMN_INDEX_SEIKYU_KINGAKU] =kingakuFormat.format(Long.parseLong(seikyuGoukei));
//					/* 更新日時 */
//					// rowcolumn.add(resultItem.get("UPDATE_TIMESTAMP").replaceAll("-",""));
//					rowcolumn[i][COLUMN_INDEX_UPDATE_TIMESTAMP] = resultItem.get("UPDATE_TIMESTAMP").replaceAll("-","");
//					// edit s.inoue 2009/10/26
//					// model.addData(rowcolumn);
//					// modelfixed.addData(rowfixed);
//				}
//			}else{
//				return null;
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//
//		return rowcolumn;
//	}
//
//	/**
//	 * 条件文を付加する。
//	 */
//	private void appendCondition(StringBuffer query, JOutputNitijiFrameData validatedData) {
//		/* 検索条件を付加する。 */
//		ArrayList<String> conditions = new ArrayList<String>();
//
//		/* 空値のテキストフィールドは条件から除外する */
//
//		String jyushinkenId = jTextField_JyushinKenID.getText();
//		if (jyushinkenId != null && ! jyushinkenId.isEmpty()) {
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.JYUSHIN_SEIRI_NO CONTAINING " + JQueryConvert.queryConvert(jyushinkenId));
//			conditions.add(" KOJIN.JYUSHIN_SEIRI_NO STARTING WITH "
//					+ JQueryConvert.queryConvert(jyushinkenId));
//		}
//
//		String hihokenjyasyo_kigou = validatedData.getHihokenjyasyo_kigou();
//		if( !hihokenjyasyo_kigou.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.HIHOKENJYASYO_KIGOU CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_kigou));
//			conditions.add(" KOJIN.HIHOKENJYASYO_KIGOU STARTING WITH "
//					+ JQueryConvert.queryConvert(hihokenjyasyo_kigou));
//		}
//		String hihokenjyasyo_Number = validatedData.getHihokenjyasyo_Number();
//		if( !hihokenjyasyo_Number.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.HIHOKENJYASYO_NO CONTAINING " + JQueryConvert.queryConvert(hihokenjyasyo_Number));
//			conditions.add(" KOJIN.HIHOKENJYASYO_NO STARTING WITH "
//					+ JQueryConvert.queryConvert(hihokenjyasyo_Number));
//		}
//		String hokenjyaNumber = validatedData.getHokenjyaNumber();
//		if( !hokenjyaNumber.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.HKNJANUM CONTAINING " + JQueryConvert.queryConvert(hokenjyaNumber));
//			conditions.add(" KOJIN.HKNJANUM STARTING WITH "
//					+ JQueryConvert.queryConvert(hokenjyaNumber));
//		}
//		String seikyuKikanNumber = validatedData.getSeikyuKikanNumber();
//		if( !seikyuKikanNumber.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.SIHARAI_DAIKOU_BANGO CONTAINING " + JQueryConvert.queryConvert(seikyuKikanNumber));
//			conditions.add(" KOJIN.SIHARAI_DAIKOU_BANGO STARTING WITH "
//					+ JQueryConvert.queryConvert(seikyuKikanNumber));
//		}
//		String name2 = validatedData.getName();
//		if( !name2.isEmpty() )
//		{
//			// edit s.inoue 2009/10/27
//			// conditions.add(" KOJIN.KANANAME CONTAINING " + JQueryConvert.queryConvert(name2));
//			conditions.add(" KOJIN.KANANAME STARTING WITH "
//					+ JQueryConvert.queryConvert(name2));
//		}
//
//		String jissiBeginDate = validatedData.getJissiBeginDate();
//		if( !jissiBeginDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI >= " + JQueryConvert.queryConvert(jissiBeginDate));
//		}
//		String jissiEndDate = validatedData.getJissiEndDate();
//		if( !jissiEndDate.isEmpty() )
//		{
//			conditions.add(" TOKUTEI.KENSA_NENGAPI <= " + JQueryConvert.queryConvert(jissiEndDate));
//		}
//
//		/* 今年度 */
//		if (jCheckBox_JisiYear.isSelected()) {
//			conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
//		}
//
//		// 結果登録「未、済」の「未」のデータは表示しない
//		conditions.add(" TOKUTEI.KEKA_INPUT_FLG = '2' ");
//
//		/* 検索条件を付加する */
//		if (conditions.size() > 0) {
//			query.append(" WHERE ");
//
//			for (ListIterator<String> iter = conditions.listIterator(); iter.hasNext();) {
//				String condition = iter.next();
//				query.append(condition);
//
//				if (iter.hasNext()) {
//					query.append(" AND ");
//				}
//			}
//			// 表示順
//			query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		}
//	}
//
//	/**
//	 * 検索用の SQL を作成する。
//	 */
//	private StringBuffer createSearchQueryWithoutCondition() {
//
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT DISTINCT ");
//		sb.append("KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.UKETUKE_ID,KOJIN.BIRTHDAY,KOJIN.SEX,KOJIN.NAME,KOJIN.JYUSHIN_SEIRI_NO,KOJIN.HIHOKENJYASYO_KIGOU,KOJIN.HIHOKENJYASYO_NO,");
//		sb.append("TOKUTEI.KENSA_NENGAPI,TOKUTEI.HENKAN_NITIJI,KOJIN.HKNJANUM,KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.KANANAME,GET_NENDO.NENDO,");
//		// add s.inoue 2010/01/19
//		sb.append("HOKENJYA.HKNJYA_LIMITDATE_START,HOKENJYA.HKNJYA_LIMITDATE_END, ");
//		// edit s.inoue 2009/10/28
//		// sb.append(" case when TANKA_NINGENDOC is not null then TANKA_NINGENDOC else KESAI.TANKA_GOUKEI end TANKA_GOUKEI,");
//	 	sb.append(" case when HOKENJYA.TANKA_HANTEI = '2' then KESAI.TANKA_NINGENDOC else KESAI.TANKA_GOUKEI end TANKA_GOUKEI,");
//		sb.append("KESAI.MADO_FUTAN_GOUKEI,KESAI.SEIKYU_KINGAKU,KESAI.UPDATE_TIMESTAMP ");
//		sb.append(" FROM T_KOJIN AS KOJIN ");
//		sb.append("LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
//		sb.append("LEFT JOIN T_KESAI_WK AS KESAI ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID ");
//		// edit s.inoue 2009/10/28
//		sb.append("LEFT JOIN T_HOKENJYA HOKENJYA ON KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
//		// add s.inoue 2010/01/15
//		sb.append(" AND HOKENJYA.YUKOU_FLG = '1' ");
//
//		sb.append("LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
//		sb.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//		sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//		sb.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//		sb.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
//		sb.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
//		sb.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
//		sb.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		sb.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
//
//		return sb;
//	}
//
//	/**
//	 * コンストラクタ
//	 */
//	public JOutputNitijiFrameCtrl()
//	{
//		this.initializeCtrl();
//	}
//
//	/**
//	 * 制御クラスの初期化
//	 */
//	private void initializeCtrl() {
//
//		// edit s.inoue 2009/10/27
//		initializeTable();
//
//		// edit s.inoue 2009/10/27
//		dmodel = new DefaultTableModel(resultRefresh(),header){
//	      public boolean isCellEditable(int row, int column) {
//	    	boolean retflg = false;
//	    	if (column == 0 || column >12){
//	    		retflg = true;
//	      	}else{
//	      		retflg = false;
//	      	}
//	        return retflg;
//	      }
//	    };
//
//		sorter = new TableRowSorter<TableModel>(dmodel);
//		table = new JSimpleTable(dmodel);
//		modelfixed= new JSimpleTable(dmodel);
//
//		// edit s.inoue 2009/10/26
//		// state = JOutputHL7FrameState.STATUS_INITIALIZED;
//		/* ボタンの状態を変更する。 */
//		// this.buttonCtrl();
//
//		/* 列幅の設定 */
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
//		table.setPreferedColumnWidths(new int[] {100, 40, 70, 70, 70,80, 110, 110, 80, 80, 80, 140});
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//	    	// edit s.inoue 2010/07/07
//	    	if(i < 4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            // edit s.inoue 2010/07/07
//            }else if(i != 4) {
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	    // add s.inoue 2009/10/23
//	    table.setRowSorter(sorter);
//	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setRowSorter(sorter);
//	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        // edit s.inoue 2009/10/26
//		// modelfixed.setCellCheckBoxEditor(new JCheckBox(), 0);
//		/* カラムヘッダのクリックによる並び替えを可能にする。 */
//		// model.setAutoCreateRowSorter(true);
//
//		/* 今年度 */
//		jCheckBox_JisiYear.setSelected(true);
//	    // edit s.inoue 2009/12/12
//		/* ワンクリックの処理 */
//		// table.setAutoCreateRowSorter(true);
//
//		// edit s.inoue 2010/07/06
//		table.addMouseListener(new JSingleDoubleClickEvent(this, jButton_SeikyuEdit,modelfixed));
//		modelfixed.addMouseListener(new JSingleDoubleClickEvent(this, jButton_SeikyuEdit,modelfixed));
//
////		// ソートされたときに発生するイベント処理
////		sorter.addRowSorterListener(new RowSorterListener() {
////		    public void sorterChanged(RowSorterEvent event) {
////		        System.out.println("aaaaType: " + event.getType());
////		        if(event.getType().equals(RowSorterEvent.Type.SORTED)){
////		        	sorter.sort()
////		        }
////		    }
////		});
////		modelfixed.setSelectionModel(table.getSelectionModel());
////		modelfixed.getColumnModel().getColumn(0).setResizable(false);
////		modelfixed.getColumnModel().getColumn(1).setResizable(false);
////		modelfixed.getColumnModel().getColumn(2).setResizable(false);
////		modelfixed.getColumnModel().getColumn(3).setResizable(false);
//
//		this.initilizeSeikyu();
//		this.initilizeFocus();
//		// add s.inoue 2009/12/01
//		this.functionListner();
//
//		// add s.inoue 2009/10/26
//        final JScrollPane scroll = new JScrollPane(table);
//        JViewport viewport = new JViewport();
//        viewport.setView(modelfixed);
//        viewport.setPreferredSize(modelfixed.getPreferredSize());
//        scroll.setRowHeader(viewport);
//
//        modelfixed.setPreferredScrollableViewportSize(modelfixed.getPreferredSize());
//        scroll.setRowHeaderView(modelfixed);
//        scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, modelfixed.getTableHeader());
//
//        scroll.getRowHeader().addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                JViewport viewport = (JViewport) e.getSource();
//                scroll.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
//            }
//        });
//
//		jPanel_TableArea.add(scroll);
//
//		/* 保険者番号コンボボックスの設定 */
//		this.initializeHokenjaNumComboBox();
//
//		/* 請求機関番号コンボボックスを初期化する。 */
//		this.initializeSeikyukikanNumberComboBox();
//
//		// 種別コードコンボボックスの設定
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);
//
//		/* メッセージダイアログを初期化する。ｌ */
//		try {
//			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
//			messageDialog.setShowCancelButton(false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		/* ボタンの状態を変更する。 */
//		this.buttonCtrl();
//
//		dmodel.setDataVector(resultRefresh(),header);
//
//		// add s.inoue 2009/10/26
//		// 4列以下は固定、以降は変動
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//            	// edit s.inoue 2010/07/07
//            	// modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            	modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	   // add s.inoue 2009/10/26
//	   TableColumnModel columnsfix = modelfixed.getColumnModel();
//	   for(int i=1;i<columnsfix.getColumnCount();i++) {
//
//		   columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//	   }
//
//	   TableColumnModel columns = table.getColumnModel();
//	   for(int i=0;i<columns.getColumnCount();i++) {
//
//		   columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   			(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
//	   }
//
//	   modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
//
//	   // add s.inoue 2009/12/07
//	   table.refreshTable();
//	   modelfixed.refreshTable();
//
//		 // edit s.inoue 2009/11/09
//		 // 初期選択
//	     int count = 0;
//		 if (table.getRowCount() > 0) {
//			 table.setRowSelectionInterval(0, 0);
//			 count = table.getRowCount();
//		 } else {
//			 jTextField_JyushinKenID.requestFocus();
//		 }
//		 jLabel_count.setText(count + " 件");
//
//		  	// add s.inoue 2009/12/12
//		   // selectedrowの初期化(simpletableとの連携)
//		   table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			   public void valueChanged(ListSelectionEvent e) {
//			     if(e.getValueIsAdjusting()) return;
//
//			     int i = table.getSelectedRow();
//			     if (i <= 0) return;
//
//			     if(modelfixed.getValueAt(i, 0) == null){
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
//			    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
//			     }else{
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }
//			   }
//			 });
//		   modelfixed.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			   public void valueChanged(ListSelectionEvent e) {
//			     if(e.getValueIsAdjusting()) return;
//
//			     int i = table.getSelectedRow();
//			     if (i <= 0) return;
//
//			     if(modelfixed.getValueAt(i, 0) == null){
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
//			    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
//			     }else{
//			    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//			     }
//			   }
//			 });
//
//	}
//
//	/**
//	 * フォーカスを初期化する。
//	 */
//	private void initilizeFocus(){
//		// edit s.inoue 2009/10/07
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_Name);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Name);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JyushinKenID);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiBeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_JissiEndDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_kigou);
//		this.focusTraversalPolicy.addComponent(this.jTextField_Hihokenjyasyo_Number);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_SeikyuKikanNumber);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_JisiYear);
//		this.focusTraversalPolicy.addComponent(this.jButton_AllSelect);
//		this.focusTraversalPolicy.addComponent(this.jButton_Search);
//		this.focusTraversalPolicy.addComponent(this.table);
//		this.focusTraversalPolicy.addComponent(this.jButton_Seikyu);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuListPrint);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuEdit);
//		this.focusTraversalPolicy.addComponent(this.jButton_End);
//	}
//	// add s.inoue 2009/12/01
//	/* focus初期化 */
//	private void functionListner(){
//		Component comp = null;
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//		modelfixed.addKeyListener(this);
//	}
//
//	/**
//	 * テーブルを初期化する。
//	 */
//	private void initializeTable() {
//
//        searchCondition(true);
//	}
//
//	private void initilizeSeikyu(){
//	      table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//				@Override
//				public void valueChanged(ListSelectionEvent e) {
//
//					if(e.getValueIsAdjusting()) return;
//		       	    int intTanka = 0;
//		       	    int intMadoguti = 0;
//		       	    int intSeikyu = 0;
//
//		       	    for(int i =0;i < table.getRowCount();i++){
//		       	    	if((Boolean)modelfixed.getData(i,0) == Boolean.TRUE)
//		    			{
//		       	    		// 単価、窓口負担、請求額
//		       	    		String sumTanka="";
//		       	    		String sumMadoguti="";
//		       	    		String sumSeikyu="";
//
//		       	    		// edit s.inoue 2009/11/14
//		       	    		if(table.getData(i,COLUMN_INDEX_TANKA_GOUKEI) != null){
//		       	    			sumTanka = String.valueOf(table.getData(i,COLUMN_INDEX_TANKA_GOUKEI)).replaceAll(",", "");
//		       	    		}
//		       	    		if(table.getData(i,COLUMN_INDEX_MADO_FUTAN_GOUKEI) != null){
//		       	    			sumMadoguti = String.valueOf(table.getData(i,COLUMN_INDEX_MADO_FUTAN_GOUKEI)).replaceAll(",", "");
//		       	    		}
//		       	    		if(table.getData(i,COLUMN_INDEX_SEIKYU_KINGAKU) != null){
//		       	    			sumSeikyu = String.valueOf(table.getData(i,COLUMN_INDEX_SEIKYU_KINGAKU)).replaceAll(",", "");
//		       	    		}
//
//		       	    		// 選択行の合計処理
//		       	    		if ( !sumTanka.equals("") ){
//		       	    			intTanka += Integer.parseInt(sumTanka);
//		       	    		}
//		       	    		if ( !sumMadoguti.equals("") ){
//		       	    			intMadoguti += Integer.parseInt(sumMadoguti);
//		       	    		}
//		       	    		if ( !sumSeikyu.equals("") ){
//		       	    			intSeikyu += Integer.parseInt(sumSeikyu);
//		       	    		}
//		    			}
//		       	    }
//		       	    // del s.inoue 2009/08/28
////		       	    // 単価、窓口負担、請求額
////		       	    if (intTanka > 0){
////		       	    	jTextField_TankaGoukei.setText(String.valueOf(intTanka));
////		       	    }
////		       	    if (intMadoguti > 0){
////		       	    	jTextField_FutanGoukei.setText(String.valueOf(intMadoguti));
////		       	    }
////		       	    if (intSeikyu > 0){
////		       	    	jTextField_SeikyuGoukei.setText(String.valueOf(intSeikyu));
////		       	    }
////	        	    System.out.println(model.getSelectedRow() +"行選択イベント成功");
//				}
//	        	});
//	}
//	/**
//	 * 支払代行機関コンボボックスを初期化する
//	 */
//	private void initializeSeikyukikanNumberComboBox() {
//		ArrayList<Hashtable<String, String>> result = null;
//
//		String query = new String("SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//
//		jComboBox_SeikyuKikanNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> ResultItem = result.get(i);
//			String num = ResultItem.get("SHIHARAI_DAIKO_NO");
//			String name = ResultItem.get("SHIHARAI_DAIKO_NAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_SeikyuKikanNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * 保険者番号、名称の一覧を取得する。
//	 */
//	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
//		ArrayList<Hashtable<String, String>> result = null;
//		// add s.inoue 2010/01/15
//		String query = new String("SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA WHERE YUKOU_FLG = '1' ORDER BY HKNJANUM");
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException e){
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//		return result;
//	}
//
//	/**
//	 * 保険者番号コンボボックスの初期化
//	 */
//	private void initializeHokenjaNumComboBox() {
//		ArrayList<Hashtable<String, String>> result = this.getHokenjaNumAndNames();
//
//		jComboBox_HokenjyaNumber.addItem("");
//
//		for(int i = 0;i < result.size();i++ )
//		{
//			Hashtable<String,String> resultItem = result.get(i);
//			String num = resultItem.get("HKNJANUM");
//			String name = resultItem.get("HKNJANAME");
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(num);
//			if (name != null && ! num.isEmpty()) {
//				buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);
//				buffer.append(name);
//			}
//
//			jComboBox_HokenjyaNumber.addItem(buffer.toString());
//		}
//	}
//
//	/**
//	 * 終了ボタンが押された場合 ５
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	/**
//	 * 請求処理ボタンが押された場合
//	 *
//	 * Modified 2008/04/01 若月 可読性が低く、障害の調査が困難なため、全面的に書き直す。
//	 */
//	public void pushedSeikyuButton( ActionEvent e )
//	{
//		// 選択状態を保持する
//		ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
//
//		// 請求ボタン制限解除
//
//		// switch(state)
//		// {
//		// case STATUS_AFTER_SEARCH:
//			int count = 0;
//
//			boolean notExtMessage = false;
//
//			datas = new Vector<JKessaiProcessData>();
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				if( Boolean.TRUE == (Boolean)modelfixed.getData(i,0)  )
//				{
//					count++;
//
//					Hashtable<String, String> item = result.get(i);
//
//					JKessaiProcessData dataItem = new JKessaiProcessData();
//
//					// add s.inoue 2010/01/19
//					checkKigenSetting(
//							item.get("KENSA_NENGAPI"),
//							item.get("HKNJYA_LIMITDATE_START"),
//							item.get("HKNJYA_LIMITDATE_END"));
//
//					/* 受付ID */
//					dataItem.uketukeId = item.get("UKETUKE_ID");
//					/* 被保険者証等記号 */
//					dataItem.hiHokenjyaKigo = item.get("HIHOKENJYASYO_KIGOU");
//					/* 被保険者証等番号 */
//					dataItem.hiHokenjyaNumber = item.get("HIHOKENJYASYO_NO");
//					/* 保険者番号（個人） */
//					dataItem.hokenjyaNumber = item.get("HKNJANUM");
//					/* 検査実施日 */
//					dataItem.KensaDate = item.get("KENSA_NENGAPI");
//					/* 支払代行機関番号 */
//					dataItem.daikouKikanNumber = item.get("SIHARAI_DAIKOU_BANGO");
//
//					/* カナ氏名 */
//					dataItem.kanaName = item.get("KANANAME");
//					/* 性別 */
//					dataItem.sex = item.get("SEX");
//					/* 生年月日 */
//					dataItem.birthday = item.get("BIRTHDAY");
//
//					if (dataItem.hiHokenjyaNumber == null || dataItem.hiHokenjyaNumber.isEmpty()) {
//						/* 未入力エラー,被保険者証等番号が入力されていません。一覧で被保険者証等番号を確認してください。
//						 * [改行]　氏名（カナ）[%s]、性別[%s]、生年月日[%s] */
//						String sexName = dataItem.sex.equals("1") ? "男性" : "女性";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4751", this, params);
//						return;
//					}
//
//					if (dataItem.KensaDate == null || dataItem.KensaDate.isEmpty()) {
//						/* 入力エラー,健診結果データが存在しません。[改行]　氏名（カナ）[%s]、性別[%s]、生年月日[%s] */
//						String sexName = dataItem.sex.equals("1") ? "男性" : "女性";
//						String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };
//
//						JErrorMessage.show("M4753", this, params);
//						return;
//					}
//
//					/*
//					 * 種別コードを格納する
//					 */
//					if( dataItem.daikouKikanNumber != null && ! dataItem.daikouKikanNumber.isEmpty() )
//					{
//						dataItem.syubetuCode = "1";
//					}else{
//						dataItem.syubetuCode = "6";
//					}
//
//					// 実施区分を格納する（特定健診は 1 固定）
//					dataItem.jissiKubun = JISSIKUBUN_TOKUTEIKENSHIN;
//
//					/* 請求区分を格納する。 */
//					String seikyuKubun = getSeikyuKubun(i);
//
//					if (seikyuKubun == null || seikyuKubun.isEmpty()) {
//						/* M4732：エラー,請求区分の取得に失敗しました。,0,0 */
//						JErrorMessage.show("M4733", this);
//						return;
//					}
//
//					dataItem.seikyuKubun = seikyuKubun;
//
//					String[] registKensa= JInputKessaiDataFrameCtrl.isNotExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (registKensa != null){
//						// edit s.inoue 20081119
//						if (!notExtMessage){
//							JErrorMessage.show("M3635", this);
//							notExtMessage = true;
//						}
//					}
//
//					String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
//							dataItem.hokenjyaNumber,
//							dataItem.uketukeId,
//							dataItem.KensaDate,
//							dataItem.seikyuKubun);
//
//					if (existKensa != null){
//						if (!notExtMessage){
//							JErrorMessage.show("M3636", this );
//							notExtMessage = true;
//						}
//					}
//
//					datas.add(dataItem);
//				}
//			}
//
//			if( count != 0 )
//			{
//				try{
//					// 決済、集計総tran
//					JApplication.kikanDatabase.Transaction();
//
//					JOutputNitijiFrameData validatedData = new JOutputNitijiFrameData();
//
//					if( validatedData.setSyubetuCode((String)jComboBox_SyubetuCode.getSelectedItem()))
//					{
//						try {
//							/* 決済処理 */
//							JKessaiProcess.runProcess(datas, JApplication.kikanNumber);
//
//						} catch (Exception e1) {
//							logger.error(e1.getMessage());
//							JApplication.kikanDatabase.rollback();
//							JErrorMessage.show("M4730", this);
//							return;
//						}
//
//						/* 集計処理 */
//						try{
//							if(JSyuukeiProcess.RunProcess(datas) == false)
//							{
//								JApplication.kikanDatabase.rollback();
//								JErrorMessage.show("M4731", this);
//								return;
//							}
//						} catch (Exception e2){
//							JApplication.kikanDatabase.rollback();
//							logger.error(e2.getMessage());
//							JErrorMessage.show("M4731", this);
//							return;
//						}
//
//						state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//
//						messageDialog.setMessageTitle("請求処理");
//						messageDialog.setText("請求処理が終了しました");
//						messageDialog.setVisible(true);
//					}
//					// 決済、集計総Commit
//					JApplication.kikanDatabase.Commit();
//					// edit s.inoue 2009/10/26
//					searchRefresh();
//					table.setselectedRows(modelfixed,selectedRows);
//
//				}catch (Exception ex){
//					try{
//						JApplication.kikanDatabase.rollback();
//					}catch(SQLException exx){
//					}
//					ex.printStackTrace();
//					logger.error(ex.getMessage());
//				}
//			}
//		buttonCtrl();
//	}
//
//	private String getSeikyuKubun(int i) {
//		// 請求区分を格納する
//		String query = "SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE " +
//				"UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//				"KENSA_NENGAPI = " + JQueryConvert.queryConvert(result.get(i).get("KENSA_NENGAPI"));
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(query);
//		}catch(SQLException ex){
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//
//		String seikyuKubun = null;
//		if (tbl != null && tbl.size() == 1) {
//			seikyuKubun = tbl.get(0).get("SEIKYU_KBN");
//		}
//
//		return seikyuKubun;
//	}
//
//	/**
//	 * 支払代行機関が設定されているかを調べる。
//	 */
//	private boolean existsShiharaiDaikoKikan(int i) {
//
//		/* 支払代行機関番号の有無を調べる */
//		String Query = "select * from T_KOJIN " +
//									" where " +
//									" UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
//									" SIHARAI_DAIKOU_BANGO IS NULL";
//
//		ArrayList<Hashtable<String, String>> tbl = null;
//		try{
//			tbl = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		}catch(SQLException f){
//			f.printStackTrace();
//		}
//
//		if( ! tbl.isEmpty() ){
//			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * 請求データ編集処理ボタンが押された場合 2
//	 */
//	public void pushedSeikyuEditButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEIKYU:
//			// 一つだけ選択されている場合のみ
//			if( table.getSelectedRowCount() == 1 )
//			{
//				int[] selection = table.getSelectedRows();
//				int modelRow=0;
//	            for (int i = 0; i < selection.length; i++) {
//
//	                // テーブルモデルの行数に変換
//	                modelRow = table.convertRowIndexToModel(selection[i]);
//	                System.out.println("View Row: " + selection[i] + " Model Row: " + modelRow);
//	            }
//
//	            Hashtable<String, String> resultItem = result.get(modelRow);
//
//				String uketukeId = resultItem.get("UKETUKE_ID");
//				String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
//				String hihokenjyasyoNo = resultItem.get("HIHOKENJYASYO_NO");
//				String kensaNengapi = resultItem.get("KENSA_NENGAPI");
//
//				JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
//						uketukeId,
//						hihokenjyasyoKigou,
//						hihokenjyasyoNo,
//						kensaNengapi,
//						datas);
//
//				// edit s.inoue 2009/11/25
//				JScene.CreateDialog(this,ctrl,new WindowRefreshEvent());
//
//				state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//				break;
//			}
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * 全て選択ボタンが押された場合 7
//	 */
//	public void pushedAllSelectButton( ActionEvent e )
//	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEARCH:
//			// edit h.yoshitama 2009/09/03
//			for(int i = 0;i < modelfixed.getRowCount();i++ )
//			{
//				if( isAllSelect )
//					modelfixed.setData(true, i, 0);
//				else
//					modelfixed.setData(false, i, 0);
//			}
//
//			if( isAllSelect )
//			{
//				isAllSelect = false;
//			}else{
//				isAllSelect = true;
//			}
//
//			break;
//		}
//		buttonCtrl();
//	}
//
//	/**
//	 * 検索ボタンが押された場合 ６
//	 */
//	public void pushedSearchButton( ActionEvent e )
//	{
//		// add s.inoue 2009/10/27
//		if(!searchCondition(false))
//			searchRefresh();
//	}
//
//	// add s.inoue 2010/02/03
//	/**
//	 * 遷移先の画面から戻ってきた場合
//	 */
//	public class WindowRefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			// 選択状態を保持する
//			ArrayList<Integer> selectedRows = null;
//			selectedRows = table.getselectedRows(modelfixed,table);
//
//			// edit s.inoue 2010/02/03
//			state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//
//			// edit s.inoue 2010/02/03
//			// if(!searchCondition(false))
//			searchRefresh();
//			buttonCtrl();
//
//			table.setselectedRows(modelfixed,selectedRows);
//		}
//	}
//
//	// edit s.inoue 2009/10/27
//	// 検索不正処理追加
//	private boolean searchCondition(boolean initialize){
//		boolean retflg = false;
//
//		buttonCtrl();
//
//		switch(state)
//		{
//		/* FALLTHROUGH */
//		case STATUS_INITIALIZED:
//		case STATUS_AFTER_SEARCH:
//		case STATUS_AFTER_SEIKYU:
//		case STATUS_AFTER_HL7:
//			// edit s.inoue 2009/10/26
//			// rowCount = tableRefresh();
//			// if( rowCount == 0 )
//			// {
//			// edit s.inoue 2009/10/27
//			// resultRefresh();
//			// state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//			if (resultRefresh() == null){
//				// del s.inoue 2009/10/28
//				// state = JOutputHL7FrameState.STATUS_INITIALIZED;
//				// edit s.inoue 2009/10/27
//				retflg= true;
//			}else{
//				state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//			}
//			break;
//		}
//		buttonCtrl();
//
//		return retflg;
//	}
//
//// del s.inoue 2009/09/14
////	/**
////	 * 入力ダイアログ
////	 */
////	private ISelectInputDialog dialog;
//
//	public void pushedSeikyuPrintButton( ActionEvent e )
//	{
//
//// del s.inoue 2009/09/14
//// 印刷選択方式の廃止
////		dialog = JSelectInputDialogFactory.createDialog(this);
////		dialog.setVisible(true);
////		String printQuery = dialog.getPrintQuery();
////		System.out.println( dialog.getStatus() +  "");
////		if (dialog.getStatus() != RETURN_VALUE.OK){
////			break;
////		}
//
//		// edit ver2 s.inoue 2009/09/01
//		// 選択行を抽出する。
//		int rowCount = table.getRowCount();
//		// 印刷用
//		TreeMap<String, Object> printDataNitiji = new TreeMap<String, Object>();
//		// ステータス画面
//		ProgressWindow progressWindow = new ProgressWindow(this, false,true);
//
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		// edit s.inoue 2009/11/02
//		ArrayList<String> keyList =  new ArrayList<String>();
//
//		// 選択チェック
//		// edit h.yoshitama 2009/09/03
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
//
//		// 選択行なし
//		int selectedCount = selectedRows.size();
//		if (selectedCount == 0) {
//			JErrorMessage.show("M3548", this);return;
//		}
//
//		// edit ver2 s.inoue 2009/09/08
//		Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
//
//		// 日計データ作成
//		for (int j = 0; j < selectedCount; j++) {
//
//			int k = selectedRows.get(j);
//
//			// del s.inoue 2009/09/01
//			progressWindow.setGuidanceByKey("common.frame.progress.guidance.main");
//			progressWindow.setVisible(true);
//
//			// 受付ID
//			String uketukeId = result.get(k).get("UKETUKE_ID");
//			tmpKojin.put("UKETUKE_ID", uketukeId);
//
//			// edit ver2 s.inoue 2009/09/07
//			// 健診年月日
//			// String kensaNengapi = result.get(k).get("KENSA_NENGAPI");
//			// tmpKojin.put("KENSA_NENGAPI",kensaNengapi );
//
//			Nikeihyo nikeihyo = new Nikeihyo();
//
//			if (!nikeihyo.setPrintSeikyuNitijiDataSQL(tmpKojin)){
//			 	// データ移送失敗
//				logger.warn("日次データ作成失敗");
//			 	JErrorMessage.show("M3530", this);
//			}
//
//			// edit ver2 s.inoue 2009/09/01
//			// Iraisho IraiData = new Iraisho();
//			// if (!IraiData.setPrintKojinIraiDataSQL(tmpKojin)) {
//			// 	// データ移送失敗
//			// 	JErrorMessage.show("M3530", this);
//			// }
//			// PrintSeikyuNitiji seikyu = new PrintSeikyuNitiji();
//			// if(!seikyu.setPrintSeikyuDataSQL(printQuery)){
//			//
//			// }
//
//			// 印刷 受付ID:印刷データ
//			printDataNitiji.put(String.valueOf(uketukeId), nikeihyo);
//			keyList.add(String.valueOf(uketukeId));
//		}
//
//		/*
//		 * Kikan作成
//		 */
//		Kikan kikanData = new Kikan();
//		if (!kikanData.setPrintKikanDataSQL()) {
//			// データ移送失敗
//			JErrorMessage.show("M4941", this);
//		}
//
//		Hashtable<String, Object> printData = new Hashtable<String, Object>();
//		printData.put("Kikan", kikanData);
//
//		// 印刷実行
//		// edit ver2 s.inoue 2009/09/03
//		PrintSeikyuNitiji nitiji = new PrintSeikyuNitiji();
//		nitiji.setQueryNitijiResult(printDataNitiji);
//		nitiji.setQueryResult(printData);
//		nitiji.setKeys(keyList);
//
//		// PrintSeikyuNitiji seikyu = new PrintSeikyuNitiji();
//		progressWindow.setVisible(false);
//
//		try {
//			nitiji.beginPrint();
//		} catch (PrinterException err) {
//			err.printStackTrace();
//			JErrorMessage.show("M3531", this);
//		} finally {
//			// progressWindow.setVisible(false);
//		}
//	}
//
//	public void itemStateChanged( ItemEvent e )
//	{
//	}
//
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		int mod = keyEvent.getModifiersEx();
//
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Seikyu.getText());
//			pushedSeikyuButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_SeikyuListPrint.getText());
//			pushedSeikyuPrintButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_SeikyuEdit.getText());
//			pushedSeikyuEditButton(null);break;
//
////		case KeyEvent.VK_S:
////			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
////				logger.info(jButton_Search.getText());
////				pushedSearchButton( null );
////			}
////			break;
//		}
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//
//		if( source == jButton_End			)
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//		// 請求処理ボタン
//		else if( source == jButton_Seikyu		)
//		{
//			logger.info(jButton_Seikyu.getText());
//			pushedSeikyuButton( e );
//		}
//		else if( source == jButton_SeikyuEdit		)
//		{
//			logger.info(jButton_SeikyuEdit.getText());
//			pushedSeikyuEditButton( e );
//		}
//		else if( source == jButton_SeikyuListPrint		)
//		{
//			logger.info(jButton_SeikyuListPrint.getText());
//			pushedSeikyuPrintButton( e );
//		}
//		else if( source == jButton_AllSelect	)
//		{
//			logger.info(jButton_AllSelect.getText());
//			pushedAllSelectButton( e );
//		}
//		else if( source == jButton_Search		)
//		{
//			logger.info(jButton_Search.getText());
//			pushedSearchButton( e );
//		}
//
//	}
//// edit s.inoue 2009/10/26
////	/**
////	 * 検索結果をテーブルに追加する。
////	 */
////	private Object[][] addRowToTable() {
////		// del s.inoue 2009/10/26
////		// model.clearAllRow();
////		// modelfixed.clearAllRow();
////
////		// edit s.inoue 2009/10/26
////		Hashtable<String, String> resultItem;
////		Object[][] rowcolumn = new Object[result.size()][16];
////
////		for(int i = 0;i < result.size();i++ )
////		{
////			// edit s.inoue 2009/10/26
////			// Vector<String> rowcolumn = new Vector<String>();
////			// Vector<String> rowfixed = new Vector<String>();
////
////			resultItem = result.get(i);
////
////			// edit s.inoue 2009/10/26
////			/* チェックボックス */
////			// rowcolumn.add(null);
////			rowcolumn[i][COLUMN_INDEX_CHECK] = null;
////			/* 年度 */
////			// rowcolumn.add(resultItem.get("NENDO"));
////			rowcolumn[i][COLUMN_INDEX_NENDO] = resultItem.get("NENDO");
////			/* 受診券整理番号 */
////			// rowcolumn.add(resultItem.get("JYUSHIN_SEIRI_NO"));
////			rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = resultItem.get("JYUSHIN_SEIRI_NO");
////			/* 氏名（カナ） */
////			// rowcolumn.add(resultItem.get("KANANAME"));
////			rowcolumn[i][COLUMN_INDEX_KANANAME] = resultItem.get("KANANAME");
////			/* 氏名（名前） */
////			// rowcolumn.add(resultItem.get("NAME"));
////			rowcolumn[i][COLUMN_INDEX_NAME] = resultItem.get("NAME");
////
////			/* 性別 */
////			// edit s.inoue 2009/10/26
////			// String sexCode = resultItem.get("SEX");
////			// String sexText = "";
////			// if (sexCode.equals("1")) {
////			//	sexText = "男性";
////			// }
////			// else if(sexCode.equals("2")){
////			// 	sexText = "女性";
////			// }
////			// rowcolumn.add(sexText);
////			rowcolumn[i][COLUMN_INDEX_SEX] = resultItem.get("SEX").equals("1") ? "男性" : "女性";
////			/* 生年月日 */
////			// rowcolumn.add(resultItem.get("BIRTHDAY"));
////			rowcolumn[i][COLUMN_INDEX_BIRTHDAY] = resultItem.get("BIRTHDAY");
////			/* 健診実施日 */
////			// rowcolumn.add(resultItem.get("KENSA_NENGAPI"));
////			rowcolumn[i][COLUMN_INDEX_KENSA_NENGAPI] = resultItem.get("KENSA_NENGAPI");
////			/* 保険者番号 */
////			// rowcolumn.add(resultItem.get("HKNJANUM"));
////			rowcolumn[i][COLUMN_INDEX_HKNJANUM] = resultItem.get("HKNJANUM");
////			/* 支払代行機関番号 */
////			// rowcolumn.add(resultItem.get("SIHARAI_DAIKOU_BANGO"));
////			rowcolumn[i][COLUMN_INDEX_SIHARAI_DAIKOU_BANGO] = resultItem.get("SIHARAI_DAIKOU_BANGO");
////			/* 被保険者証等記号 */
////			// rowcolumn.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
////			rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_KIGOU] = resultItem.get("HIHOKENJYASYO_KIGOU");
////			/* 被保険者証等番号 */
////			// rowcolumn.add(resultItem.get("HIHOKENJYASYO_NO"));
////			rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_NO] = resultItem.get("HIHOKENJYASYO_NO");
////			/* 単価合計 */
////			// rowcolumn.add(resultItem.get("TANKA_GOUKEI"));
////			rowcolumn[i][COLUMN_INDEX_TANKA_GOUKEI] = resultItem.get("TANKA_GOUKEI");
////			/* 窓口負担合計 */
////			// rowcolumn.add(resultItem.get("MADO_FUTAN_GOUKEI"));
////			rowcolumn[i][COLUMN_INDEX_MADO_FUTAN_GOUKEI] = resultItem.get("MADO_FUTAN_GOUKEI");
////			/* 請求金額合計 */
////			// rowcolumn.add(resultItem.get("SEIKYU_KINGAKU"));
////			rowcolumn[i][COLUMN_INDEX_SEIKYU_KINGAKU] = resultItem.get("SEIKYU_KINGAKU");
////			/* 更新日時 */
////			// rowcolumn.add(resultItem.get("UPDATE_TIMESTAMP").replaceAll("-",""));
////			rowcolumn[i][COLUMN_INDEX_UPDATE_TIMESTAMP] = resultItem.get("UPDATE_TIMESTAMP").replaceAll("-","");
////			// edit s.inoue 2009/10/26
////			// model.addData(rowcolumn);
////			// modelfixed.addData(rowfixed);
////		}
////		return rowcolumn;
////	}
//	// del s.inoue 2009/10/26
////	/**
////	 * ＨＬ７外部出力ボタンが押された場合 ４
////	 */
////	public void pushedHL7CopyButton( ActionEvent e )
////	{
////		JDirChooser dirSelect = new JDirChooser();
////
////		switch(state)
////		{
////		case STATUS_AFTER_HL7:
////			JFileChooser Chooser = new JFileChooser(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
////
////			// ファイル選択ダイアログの表示
////			// TODO 出力するファイルを選択してください。
////			if( Chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
////			{
////				// TODO 出力先のフォルダを選択してください。
////				if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION )
////				{
////					try {
////						if( JFileCopy.copyFile(
////								Chooser.getSelectedFile().getAbsolutePath(),			// コピー元
////								dirSelect.getSelectedFile().getPath() + File.separator +
////								Chooser.getSelectedFile().getName() ) != true )
////																						// コピー先
////						{
////							JErrorMessage.show("M4720", this);
////						}else{
////							JErrorMessage.show("M4721", this);
////						}
////					} catch (IOException e1) {
////						e1.printStackTrace();
////						JErrorMessage.show("M4720", this);
////					}
////				}
////			}
////
////			state = JOutputHL7FrameState.STATUS_AFTER_HL7;
////		}
////
////		buttonCtrl();
////	}
//
//// del s.inoue 2009/09/18
////	/**
////	 * ＨＬ７出力ボタンが押された場合 ３
////	 */
////	public void pushedHL7OutputButton( ActionEvent e )
////	{
////		switch(state)
////		{
////			case STATUS_AFTER_SEIKYU:
////				/* datas は、HL7 出力時に必要な情報 */
////				if( JOutputHL7.RunProcess(datas) )
////				{
////					// 正常に終了した場合
////					state = JOutputHL7FrameState.STATUS_AFTER_HL7;
////					tableRefresh();
////				}
////
////				break;
////		}
////		buttonCtrl();
////
////		// HL7出力処理終了時にメッセージボックスを表示
////		messageDialog.setMessageTitle("ＨＬ７出力");
////
////		String message = "";
////		if (state == JOutputHL7FrameState.STATUS_AFTER_HL7) {
////			message = "HL7出力処理が終了しました";
////		}
////		else {
////			message = "HL7出力処理に失敗しました";
////		}
////		messageDialog.setText(message);
////		messageDialog.setVisible(true);
////	}
//
//// del s.inoue 2009/09/14
////	/**
////	 * 印刷用の SQL を作成する。
////	 */
////	private String createPrintQuery() {
////		// 検索項目を抜いたクエリ
////		StringBuilder strsb = new StringBuilder(
////				"SELECT DISTINCT " +
////
////				/* 選択した印刷条件でデータを取得する。その後そのまま印刷処理へ。
////				 * 受付ID,生年月日,性別,氏名,受診券整理番号,被保険者証等記号,被保険者証等番号,検査年月日,
////				 * 変換日時,保険者番号（個人）,支払代行機関番号,カナ氏名,年度,単価合計,窓口負担合計,請求金額合計 */
////				dialog.getPrintQuery() +
////
////				"FROM T_KOJIN AS KOJIN " +
////				"LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI " +
////				"ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID " +
////				"LEFT JOIN T_KESAI_WK AS KESAI " +
////				" ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID " +
////				" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO " +
////				" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR " +
////				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR " +
////				" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' " +
////				" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 " +
////				" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO " +
////				" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO " +
////				" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID " +
////				" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI "
////		);
////		return strsb.toString();
////	}
//}
