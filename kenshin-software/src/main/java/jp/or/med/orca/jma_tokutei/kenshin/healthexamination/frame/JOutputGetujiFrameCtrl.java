//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.*;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.JTableHeader;
//
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.TreeMap;
//import java.util.TreeSet;
//import java.util.Vector;
//
//import java.awt.Component;
//import java.awt.event.*;
//import java.awt.print.PrinterException;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.sql.SQLException;
//import java.util.*;
//
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
//import jp.or.med.orca.jma_tokutei.common.convert.JInteger;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.filectrl.DirectoryChooser;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JDirChooser;
//import jp.or.med.orca.jma_tokutei.common.filectrl.JFileCopy;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JOutputHL7Directory;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintSeikyuGetuji;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Gekeihyo;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JInputKessaiDataFrameCtrl;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
//import org.apache.log4j.Logger;
//
////add ver2 s.inoue 2009/08/20 クラス全体
///**
// * 請求・HL7出力画面制御
// *
// * 処理が複雑なため、全体的にリファクタリングと処理の見直しを行なった。
// */
//public class JOutputGetujiFrameCtrl extends JOutputGetujiFrame
//{
//	/** 保険者番号、支払代行機関選択欄の、番号と名称の区切り文字列 */
//
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";  //  @jve:decl-index=0:
//	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";  //  @jve:decl-index=0:
//	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から保険者";  //  @jve:decl-index=0:
//	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から代行機関";  //  @jve:decl-index=0:
//	// loggerの objectを作る。
//    private static org.apache.log4j.Logger logger = Logger
//    .getLogger(JOutputGetujiFrameCtrl.class);
//
//    // add s.inoue 2010/04/23
//    private static final String INPUT_DONE_FLG = "済";
//    private static final String INPUT_UNDONE_FLG = "未";
//
//	private IDialog filePathDialog;
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
//	private Vector<JSimpleTableCellPosition> forbitCells = new Vector<JSimpleTableCellPosition>();
//	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//
//	// edit s.inoue 2009/10/26
//	// 一覧データ列項目
//	private static final int COLUMN_INDEX_CHECK = 0;
//	private static final int COLUMN_INDEX_NENDO = 1;
//	private static final int COLUMN_INDEX_YUKOU = 2;
//
//	private static final int COLUMN_INDEX_JYUSHIN_SEIRI_NO = 3;
//	private static final int COLUMN_INDEX_KANANAME = 4;
//	private static final int COLUMN_INDEX_NAME = 5;
//	private static final int COLUMN_INDEX_SEX = 6;
//	private static final int COLUMN_INDEX_BIRTHDAY = 7;
//	private static final int COLUMN_INDEX_KENSA_NENGAPI = 8;
//	private static final int COLUMN_INDEX_HENKAN_NITIJI = 9;
//	private static final int COLUMN_INDEX_HKNJANUM = 10;
//	private static final int COLUMN_INDEX_SIHARAI_DAIKOU_BANGO = 11;
//	private static final int COLUMN_INDEX_HIHOKENJYASYO_KIGOU = 12;
//	private static final int COLUMN_INDEX_HIHOKENJYASYO_NO = 13;
//	private static final int COLUMN_INDEX_TANKA_GOUKEI = 14;
//	private static final int COLUMN_INDEX_MADO_FUTAN_GOUKEI = 15;
//	private static final int COLUMN_INDEX_SEIKYU_KINGAKU = 16;
//	private static final int COLUMN_INDEX_UPDATE_TIMESTAMP = 17;
//
//	// edit s.inoue 2010/04/23
//	private static final String[] header = {"", "年度" ,"日次", "受診券整理番号", "氏名（カナ）","氏名（漢字）", "性別",
//			"生年月日", "健診実施日","HL7出力日","保険者番号","代行機関番号","被保険者証等記号", "被保険者証等番号",
//			"単価合計","窓口負担合計", "請求金額合計", "更新日付"};
//
//	private DefaultTableModel dmodel = null;
//    private TableRowSorter<TableModel> sorter = null;
//
//	// edit h.yoshitama 2009/09/03
//	// private JSimpleTable model = new JSimpleTable();
//	// private JSimpleTable modelfixed = new JSimpleTable();
//    private JSimpleTable table = null;
//	private JSimpleTable modelfixed = null;
//
//	private IDialog messageDialog;
//	private static String saveTitle= "HL7ファイルを保存";
//	private static String selectTitle= "保存先を選択";
//	private static String savaDialogTitle= "HL７ファイルの保存先を選択してください";
//	private static String selectDialogTitle= "HL７ファイルを選択してください";
//	private static String saveFileName = "フォルダ";
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
//			// del s.inoue 2010/03/23 初期検索機能
////			jButton_Seikyu.setEnabled(false);
////			// edit ver2 s.inoue 2009/08/31
////			jButton_HL7Output.setEnabled(false);
////			jButton_End.setEnabled(true);
////			jButton_Search.setEnabled(true);
////			jButton_AllSelect.setEnabled(false);
////			forbitCells.clear();
////			forbitCells.add(new JSimpleTableCellPosition(-1,0));
////			break;
//
//		case STATUS_AFTER_SEARCH:
//			jButton_Seikyu.setEnabled(true);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
//			forbitCells.clear();
//			break;
//
//		case STATUS_AFTER_SEIKYU:
//			jButton_Seikyu.setEnabled(false);
//			// edit ver2 s.inoue 2009/08/31
//			jButton_HL7Output.setEnabled(true);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
//
//		case STATUS_AFTER_HL7:
//			jButton_Seikyu.setEnabled(false);
//			jButton_End.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
//			forbitCells.clear();
//			forbitCells.add(new JSimpleTableCellPosition(-1,0));
//			break;
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
//		try{
//			JOutputGetujiFrameData validatedData = new JOutputGetujiFrameData();
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
//				/* 被保険者証等記号 */
//				validatedData.setHihokenjyasyo_kigou(jTextField_Hihokenjyasyo_kigou.getText()) &&
//				/* 被保険者証等番号 */
//				validatedData.setHihokenjyasyo_Number(jTextField_Hihokenjyasyo_Number.getText()) &&
//				/* 実施日（開始） */
//				validatedData.setJissiBeginDate(jTextField_JissiBeginDate.getText()) &&
//				/* 実施日（終了） */
//				validatedData.setJissiEndDate(jTextField_JissiEndDate.getText()) &&
//				/* 変換日（開始） */
//				validatedData.setHL7BeginDate(jTextField_HL7BeginDate.getText()) &&
//				/* 変換日（終了） */
//				validatedData.setHL7EndDate(jTextField_HL7EndDate.getText()) &&
//				/* 保険証番号 */
//				validatedData.setHokenjyaNumber(hokenjaNumber) &&
//				/* 保険者名称 */
//				validatedData.setHokenjyaName(hokenjaName) &&
//				/* 請求先機関番号 */
//				validatedData.setSeikyuKikanNumber(daikoNumber) &&
//				/* 請求先機関名称 */
//				validatedData.setSeikyuKikanName(daikoName) &&
//				/* 氏名 */
//				validatedData.setName(jTextField_Name.getText())
//			){
//			/* Where 句を除いたSQLを作成する。 */
//			StringBuffer queryBuffer = this.createSearchQueryWithoutCondition();
//
//			/* 条件文を付加する。 */
//			this.appendCondition(queryBuffer, validatedData);
//
//			/* SQLを実行する。 */
//			try{
//				result = JApplication.kikanDatabase.sendExecuteQuery(queryBuffer.toString());
//			}catch(SQLException e){
//				e.printStackTrace();
//				logger.error(e.getMessage());
//			}
//
//			// add s.inoue 2010/04/23
//			// 日次請求済みフラグ追加(受付IDの配列をリスト化する)
//			ArrayList<Hashtable<String, String>> resultItemKeysWk
//				= new ArrayList<Hashtable<String, String>>();
//
//			StringBuilder sbWk = new StringBuilder();
//			sbWk.append("select UKETUKE_ID CNT from T_KESAI_WK ");
//
//			try {
//				resultItemKeysWk = JApplication.kikanDatabase.sendExecuteQuery(sbWk.toString());
//			} catch (SQLException e) {
//				logger.error(e.getMessage());
//				e.printStackTrace();
//			}
//
//			// add s.inoue 2010/05/11
////			// T_KESAIのキーリスト取得
////			ArrayList<Hashtable<String, String>> resultItemKeys
////			= new ArrayList<Hashtable<String, String>>();
////
////			StringBuilder sb = new StringBuilder();
////			sb.append("select UKETUKE_ID CNT from T_KESAI ");
////
////			try {
////				resultItemKeys = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
////			} catch (SQLException e) {
////				logger.error(e.getMessage());
////				e.printStackTrace();
////			}
//
//			// edit s.inoue 2009/10/26
//			/* 検索結果をテーブルに追加する。 */
//			// this.addRowToTable();
//			rowcolumn = new Object[result.size()][18];
//			// edit s.inoue 2009/11/14
//			NumberFormat kingakuFormat = NumberFormat.getNumberInstance();
//
//			for(int i = 0;i < result.size();i++ )
//			{
//				resultItem = result.get(i);
//
//				/* チェックボックス */
//				// rowcolumn.add(null);
//				rowcolumn[i][COLUMN_INDEX_CHECK] = null;
//				// edit s.inoue 2010/04/23
//				boolean nitijiFlg = false;
//				// boolean getujiFlg = false;
//
//				String uketukeId = resultItem.get("UKETUKE_ID");
//				for (Hashtable<String, String> key : resultItemKeysWk) {
//					if (key.get("CNT").equals(uketukeId)){
//						nitijiFlg = true;
//						rowcolumn[i][COLUMN_INDEX_YUKOU] = INPUT_DONE_FLG;
//						break;
//					}
//				}
//				if(!nitijiFlg){
//					rowcolumn[i][COLUMN_INDEX_YUKOU] = INPUT_UNDONE_FLG;
//				}
////				if(!nitijiFlg){
////					for (Hashtable<String, String> key : resultItemKeys) {
////						if (key.get("CNT").equals(uketukeId)){
////							getujiFlg = true;
////							rowcolumn[i][COLUMN_INDEX_YUKOU] = "再";
////							break;
////						}
////					}
////					if(!getujiFlg){
////						rowcolumn[i][COLUMN_INDEX_YUKOU] = INPUT_UNDONE_FLG;
////					}
////				}
//
//				/* 年度 */
//				// rowcolumn.add(resultItem.get("NENDO"));
//				rowcolumn[i][COLUMN_INDEX_NENDO] = resultItem.get("NENDO");
//				/* 受診券整理番号 */
//				// rowcolumn.add(resultItem.get("JYUSHIN_SEIRI_NO"));
//				rowcolumn[i][COLUMN_INDEX_JYUSHIN_SEIRI_NO] = resultItem.get("JYUSHIN_SEIRI_NO");
//				/* 氏名（カナ） */
//				// rowcolumn.add(resultItem.get("KANANAME"));
//				rowcolumn[i][COLUMN_INDEX_KANANAME] = resultItem.get("KANANAME");
//				/* 氏名（名前） */
//				// rowcolumn.add(resultItem.get("NAME"));
//				rowcolumn[i][COLUMN_INDEX_NAME] = resultItem.get("NAME");
//				/* 性別 */
//				rowcolumn[i][COLUMN_INDEX_SEX] = resultItem.get("SEX").equals("1") ? "男性" : "女性";
//				/* 生年月日 */
//				// rowcolumn.add(resultItem.get("BIRTHDAY"));
//				rowcolumn[i][COLUMN_INDEX_BIRTHDAY] = resultItem.get("BIRTHDAY");
//				/* 健診実施日 */
//				// rowcolumn.add(resultItem.get("KENSA_NENGAPI"));
//				rowcolumn[i][COLUMN_INDEX_KENSA_NENGAPI] = resultItem.get("KENSA_NENGAPI");
//				/* 変換実施日 */
//				// rowcolumn.add(resultItem.get("HENKAN_NITIJI"));
//				rowcolumn[i][COLUMN_INDEX_HENKAN_NITIJI] = resultItem.get("HENKAN_NITIJI");
//				/* 保険者番号 */
//				// rowcolumn.add(resultItem.get("HKNJANUM"));
//				rowcolumn[i][COLUMN_INDEX_HKNJANUM] = resultItem.get("HKNJANUM");
//				/* 支払代行機関番号 */
//				// rowcolumn.add(resultItem.get("SIHARAI_DAIKOU_BANGO"));
//				rowcolumn[i][COLUMN_INDEX_SIHARAI_DAIKOU_BANGO] = resultItem.get("SIHARAI_DAIKOU_BANGO");
//				/* 被保険者証等記号 */
//				// rowcolumn.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
//				rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_KIGOU] = resultItem.get("HIHOKENJYASYO_KIGOU");
//				/* 被保険者証等番号 */
//				// rowcolumn.add(resultItem.get("HIHOKENJYASYO_NO"));
//				rowcolumn[i][COLUMN_INDEX_HIHOKENJYASYO_NO] = resultItem.get("HIHOKENJYASYO_NO");
//
//				/* 単価合計 */
//				// rowcolumn.add(resultItem.get("TANKA_GOUKEI"));
//				String tankaGoukei = resultItem.get("TANKA_GOUKEI");
//				tankaGoukei = (tankaGoukei.equals("")) ? "0": tankaGoukei;
//				rowcolumn[i][COLUMN_INDEX_TANKA_GOUKEI] =kingakuFormat.format(Long.parseLong(tankaGoukei));
//				/* 窓口負担合計 */
//				// rowcolumn.add(resultItem.get("MADO_FUTAN_GOUKEI"));
//				String madogutiGoukei = resultItem.get("MADO_FUTAN_GOUKEI");
//				madogutiGoukei = (madogutiGoukei.equals("")) ? "0": madogutiGoukei;
//				rowcolumn[i][COLUMN_INDEX_MADO_FUTAN_GOUKEI] = kingakuFormat.format(Long.parseLong(madogutiGoukei));
//				/* 請求金額合計 */
//				// rowcolumn.add(resultItem.get("SEIKYU_KINGAKU"));
//				String seikyuGoukei = resultItem.get("SEIKYU_KINGAKU");
//				seikyuGoukei = (seikyuGoukei.equals("")) ? "0": seikyuGoukei;
//				rowcolumn[i][COLUMN_INDEX_SEIKYU_KINGAKU] =kingakuFormat.format(Long.parseLong(seikyuGoukei));
//				/* 更新日時 */
//				// rowcolumn.add(resultItem.get("UPDATE_TIMESTAMP").replaceAll("-",""));
//				rowcolumn[i][COLUMN_INDEX_UPDATE_TIMESTAMP] = resultItem.get("UPDATE_TIMESTAMP").replaceAll("-","");
//				}
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
//	private void appendCondition(StringBuffer query, JOutputGetujiFrameData validatedData) {
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
//		String beginDate = validatedData.getHL7BeginDate();
//		if( !beginDate.isEmpty() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI >= " + JQueryConvert.queryConvert(beginDate));
//			conditions.add(" TOKUTEI.HENKAN_NITIJI >= " + JQueryConvert.queryConvert(beginDate));
//		}
//		String endDate = validatedData.getHL7EndDate();
//		if( !endDate.isEmpty() )
//		{
//			// edit ver2 s.inoue 2009/04/02
//			//conditions.add(" TOKUTEI.TUTI_NENGAPI <= " + JQueryConvert.queryConvert(endDate));
//			conditions.add(" TOKUTEI.HENKAN_NITIJI <= " + JQueryConvert.queryConvert(endDate));
//		}
//
//		// edit s.inoue 2009/09/16
//		if( !jCheckBox_IsPermitHL7.isSelected() )
//		{
//		//	conditions.add(" TOKUTEI.HENKAN_NITIJI IS not NULL ");
//		// }else{
//			conditions.add(" TOKUTEI.HENKAN_NITIJI IS NULL ");
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
//			// add ver2 s.inoue 2009/06/12
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
//		sb.append("KOJIN.UKETUKE_ID,KOJIN.BIRTHDAY,KOJIN.SEX,KOJIN.NAME,KOJIN.JYUSHIN_SEIRI_NO,KOJIN.HIHOKENJYASYO_KIGOU,KOJIN.HIHOKENJYASYO_NO,");
//		sb.append("TOKUTEI.KENSA_NENGAPI,TOKUTEI.HENKAN_NITIJI,KOJIN.HKNJANUM,KOJIN.SIHARAI_DAIKOU_BANGO,KOJIN.KANANAME,GET_NENDO.NENDO,");
//		sb.append("HOKENJYA.HKNJYA_LIMITDATE_START,HOKENJYA.HKNJYA_LIMITDATE_END,");
//		// edit s.inoue 2009/10/28
//		// sb.append(" case when TANKA_NINGENDOC is not null then TANKA_NINGENDOC else KESAI.TANKA_GOUKEI end TANKA_GOUKEI,");
//	 	sb.append(" case when HOKENJYA.TANKA_HANTEI = '2' then KESAI.TANKA_NINGENDOC else KESAI.TANKA_GOUKEI end TANKA_GOUKEI,");
//		sb.append("KESAI.MADO_FUTAN_GOUKEI,KESAI.SEIKYU_KINGAKU,KESAI.UPDATE_TIMESTAMP ");
//		sb.append(" FROM T_KOJIN AS KOJIN ");
//		sb.append("LEFT JOIN T_KENSAKEKA_TOKUTEI AS TOKUTEI ON KOJIN.UKETUKE_ID = TOKUTEI.UKETUKE_ID ");
//		// edit s.inoue 2009/10/28
//		sb.append("LEFT JOIN T_HOKENJYA HOKENJYA ON KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
//		// add s.inoue 2010/01/15
//		sb.append(" AND HOKENJYA.YUKOU_FLG = '1' ");
//		// edit s.inoue 2009/10/17 _WKからT_KESAIへ
//		sb.append("LEFT JOIN T_KESAI AS KESAI ON KOJIN.UKETUKE_ID = KESAI.UKETUKE_ID ");
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
//	public JOutputGetujiFrameCtrl()
//	{
//		this.initializeCtrl();
//	}
//	// edit s.inoue 2009/10/26
//	/*
//	 * 検索リフレッシュ処理
//	 */
//	private void searchRefresh(){
//
//		/* 列幅の設定 */
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 50, 100, 200});
//		table.setPreferedColumnWidths(new int[] {100, 30, 70, 70, 70,70,80, 110, 110, 80, 80, 80, 140});
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
////	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
////            if(i < 4) {
////                table.removeColumn(table.getColumnModel().getColumn(i));
////            }
////        }
//
//	    dmodel.setDataVector(resultRefresh(),header);
//
//	    // edit s.inoue 2010/02/16
//	    if (resultRefresh().length == 0){
//	    	JErrorMessage.show("M4757", this);
//	    }
//
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            // edit s.inoue 2010/04/23
//	    	// 4 → 5
//	    	if(i < 5) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//		    }else{
//	            modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//	        }
//        }
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
//	}
//
//	/**
//	 * 制御クラスの初期化
//	 */
//	private void initializeCtrl() {
//		// edit s.inoue 2009/10/27
//		dmodel = new DefaultTableModel(resultRefresh(),header){
//	      public boolean isCellEditable(int row, int column) {
//	    	boolean retflg = false;
//	    	if (column == 0 || column >16){
//	    		retflg = true;
//	      	}else{
//	      		retflg = false;
//	      	}
//	        return retflg;
//	      }
//	    };
//		sorter =  new TableRowSorter<TableModel>(dmodel);
//		table  = new JSimpleTable(dmodel);
//		modelfixed = new JSimpleTable(dmodel);
//
//		/* テーブルを初期化する。 */
//		this.initializeTable();
//		// state = JOutputHL7FrameState.STATUS_INITIALIZED;
//
//		/* 列幅の設定 */
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 50, 100, 200});
//		table.setPreferedColumnWidths(new int[] {100, 30, 70, 70, 70,70,80, 110, 110, 80, 80, 80, 140});
//
//	    // add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//        // edit s.inoue 2010/04/23
//    	// 5 → 6,4 → 5
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i < 6) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else if(i != 5) {
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
//		/* フォーカス初期化 */
//		initilizeFocus();
//
//		// add s.inoue 2009/12/01
//		this.functionListner();
//
//	    // edit s.inoue 2009/12/12
//		/* ワンクリックの処理 */
//		table.addMouseListener(new JSingleDoubleClickEvent(null,null,modelfixed));
//		modelfixed.addMouseListener(new JSingleDoubleClickEvent(null, null,modelfixed));
//
//		// add h.yoshitama 2009/09/03
////		modelfixed.setSelectionModel(table.getSelectionModel());
////		modelfixed.getColumnModel().getColumn(0).setResizable(false);
////		modelfixed.getColumnModel().getColumn(1).setResizable(false);
////		modelfixed.getColumnModel().getColumn(2).setResizable(false);
////		modelfixed.getColumnModel().getColumn(3).setResizable(false);
//		/* 今年度 */
//		// edit s.inoue 2009/09/16
//		jCheckBox_IsPermitHL7.setSelected(true);
//		jCheckBox_JisiYear.setSelected(true);
//
//		// 種別コードコンボボックスの設定
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
//		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);
//
//		/* ボタンの状態を変更する。 */
//		this.buttonCtrl();
//
//		/* メッセージダイアログを初期化する。ｌ */
//		try {
//			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
//			messageDialog.setShowCancelButton(false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		dmodel.setDataVector(resultRefresh(),header);
//
//		// 4 → 5
//		// 4列以下は固定、以降は変動
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<5) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	    // add s.inoue 2009/10/26
//	    TableColumnModel columnsfix = modelfixed.getColumnModel();
//	    for(int i=1;i<columnsfix.getColumnCount();i++) {
//
//	    	columnsfix.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   		(DefaultTableCellRenderer)modelfixed.getDefaultRenderer(modelfixed.getColumnClass(i))));
//	    }
//
//	    TableColumnModel columns = table.getColumnModel();
//	    for(int i=0;i<columns.getColumnCount();i++) {
//	    	columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//	   		(DefaultTableCellRenderer)table.getDefaultRenderer(table.getColumnClass(i))));
//	    }
//
//	    // edit s.inoue 2009/12/07
//	    modelfixed.setCellCheckBoxEditor(new ExtendedCheckBox(), 0);
//	    table.refreshTable();
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
//	   // add s.inoue 2009/12/12
//	   // selectedrowの初期化(simpletableとの連携)
//	   table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		   public void valueChanged(ListSelectionEvent e) {
//		     if(e.getValueIsAdjusting()) return;
//
//		     int i = table.getSelectedRow();
//		     if (i <= 0) return;
//
//		     if(modelfixed.getValueAt(i, 0) == null){
//		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//		     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
//		    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
//		     }else{
//		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//		     }
//		   }
//		 });
//	   modelfixed.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		   public void valueChanged(ListSelectionEvent e) {
//		     if(e.getValueIsAdjusting()) return;
//
//		     int i = table.getSelectedRow();
//		     if (i <= 0) return;
//
//		     if(modelfixed.getValueAt(i, 0) == null){
//		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//		     }else if (modelfixed.getValueAt(i, 0).equals(Boolean.TRUE)){
//		    	 modelfixed.setValueAt(Boolean.TRUE, i, 0);
//		     }else{
//		    	 modelfixed.setValueAt(Boolean.FALSE, i, 0);
//		     }
//		   }
//		 });
//
//	}
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
//		this.focusTraversalPolicy.addComponent(this.jTextField_HL7BeginDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_HL7EndDate);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_SeikyuKikanNumber);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_IsPermitHL7);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_JisiYear);
//		this.focusTraversalPolicy.addComponent(this.jButton_AllSelect);
//		this.focusTraversalPolicy.addComponent(this.jButton_Search);
//		this.focusTraversalPolicy.addComponent(this.table);
//		this.focusTraversalPolicy.addComponent(this.jButton_Seikyu);
//		this.focusTraversalPolicy.addComponent(this.jButton_HL7Output);
//		this.focusTraversalPolicy.addComponent(this.jButton_SeikyuListPrint);
//		this.focusTraversalPolicy.addComponent(this.jButton_End);
//	}
//
//	// add s.inoue 2009/12/02
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
//		/* セルの編集可否を設定する。 */
//		JSimpleTableCellPosition[] forbitCells = {
//				new JSimpleTableCellPosition(-1,0),
//				new JSimpleTableCellPosition(-1,1),
//				new JSimpleTableCellPosition(-1,2),
//				new JSimpleTableCellPosition(-1,3),
//				new JSimpleTableCellPosition(-1,4),
//				new JSimpleTableCellPosition(-1,5),
//				new JSimpleTableCellPosition(-1,6),
//				new JSimpleTableCellPosition(-1,7),
//				new JSimpleTableCellPosition(-1,8),
//				new JSimpleTableCellPosition(-1,9),
//				new JSimpleTableCellPosition(-1,10),
//				new JSimpleTableCellPosition(-1,11),
//				new JSimpleTableCellPosition(-1,12),
//				new JSimpleTableCellPosition(-1,13),
//				// edit ver2 s.inoue 2009/08/03
////				new JSimpleTableCellPosition(-1,14),
////				new JSimpleTableCellPosition(-1,15),
//				};
//		// add h.yoshitama 2009/09/30
//		JSimpleTableCellPosition[] forbitFixedCells = {
//				new JSimpleTableCellPosition(-1,1),
//				new JSimpleTableCellPosition(-1,2),
//				new JSimpleTableCellPosition(-1,3),
//				};
//// edit s.inoue 2009/10/26 horyu
//
////		this.table.setCellEditForbid(forbitCells);
//		// add h.yoshitama 2009/09/30
////		this.modelfixed.setCellEditForbid(forbitFixedCells);
////		modelfixed.setCellEditForbid(forbitFixedCells);
////		table.setCellEditForbid(forbitCells);
//
//// edit s.inoue 2009/10/26
////		String[] solidheader = {"", "年度", "受診券整理番号", "氏名（カナ）"};
////		String[] header = {"氏名（漢字）", "性別", "生年月日", "健診実施日",
////				"HL7出力日", "保険者番号","代行機関番号","被保険者証等記号", "被保険者証等番号", "単価合計",
////				"窓口負担合計", "請求金額合計","更新日付"};
////		modelfixed.addHeader(solidheader);
////		model.addHeader(header);
////		modelfixed.setCellCheckBoxEditor(new JCheckBox(), 0);
////		model.setAutoCreateRowSorter(true);
//
//        searchCondition(true);
//	}
//
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
//	// edit ver2 s.inoue 2009/08/31
//	/**
//	 * ＨＬ７出力ボタンが押された場合 ３
//	 */
//	public void pushedHL7OutputButton( ActionEvent e )
//	{
//		boolean outputCansel = false;
//		// 選択状態を保持する
//		ArrayList<Integer> selectedRows = null;
//		switch(state)
//		{
//			case STATUS_AFTER_SEIKYU:
//			case STATUS_AFTER_HL7:
//				// edit s.inoue 2009/10/26
//				selectedRows = table.getselectedRows(modelfixed,table);
//
//				// edit s.inoue 2009/09/18
//				JOutputGetujiFrameData jOutData = new JOutputGetujiFrameData();
//
//				/* datas は、HL7 出力時に必要な情報 */
//				// edit s.inoue 2009/10/15
//				if( JOutputHL7.RunProcess(datas,jOutData) )
//				{
//					// 正常に終了した場合
//					state = JOutputHL7FrameState.STATUS_AFTER_HL7;
//					// move s.inoue 2009/10/26
//					// tableRefresh();
//
//					// move s.inoue 2009/09/17
//					// table.setselectedRows(modelfixed,selectedRows);
//
//					// edit s.inoue 2009/09/18
//					outputCansel = HL7OutputFiles(jOutData);
//				}else{
//					logger.error("HL7出力に失敗しました。");
//					return;
//				}
//
//				break;
//		}
//		buttonCtrl();
//
//		// HL7出力処理終了時にメッセージボックスを表示
//		messageDialog.setMessageTitle("ＨＬ７出力");
//
//		// edit s.inoue 2009/09/18
//		// String message = "";
//		if(outputCansel){
//			JErrorMessage.show("M4722", this);
//		}else if (state == JOutputHL7FrameState.STATUS_AFTER_HL7) {
//			// message = "HL7出力処理が終了しました";
//			JErrorMessage.show("M4721", this);
//
//			// move s.inoue 2009/10/26
//			// 初期化を呼び出す
//			// initializeCtrl();
//			searchRefresh();
//			// move s.inoue 2009/09/17
//			table.setselectedRows(modelfixed,selectedRows);
//
//			// del s.inoue 2009/09/25
//			// messageDialog.setVisible(true);
//			// }else {
//			// message = "HL7出力処理に失敗しました";
//			// del s.inoue 2009/09/25
//			// JErrorMessage.show("M4720", this);
//		}
//
//		// edit s.inoue 2009/09/18
//		// messageDialog.setText(message);
//
//	}
//
//	// add s.inoue 2009/09/18
//	private boolean HL7OutputFiles(JOutputGetujiFrameData JOutputData){
//
//		boolean retCansel = false;
//
//		try {
//
//// edit s.inoue 2010/05/10 独自ダイアログ 封印
////			String defaltPath = JPath.getDesktopPath();
//////			File.separator +
//////			saveFileName;
////
////			// edit s.inoue 2010/04/28
//////			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
//////			filePathDialog.setMessageTitle(saveTitle);
//////			filePathDialog.setEnabled(false);
//////			filePathDialog.setDialogSelect(true);
//////			filePathDialog.setDialogTitle(savaDialogTitle);
//////			filePathDialog.setSaveFileName(saveFileName);
//////			filePathDialog.setVisible(true);
////			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
////			filePathDialog.setMessageTitle(saveTitle);
////			filePathDialog.setEnabled(false);
////			filePathDialog.setDialogSelect(false);
////			filePathDialog.setDialogTitle(savaDialogTitle);
////			filePathDialog.setSaveFileName("フォルダ名");
////			filePathDialog.setVisible(true);
////
////			// edit s.inoue 2010/03/25
////			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
////				return true;
////
////			String filePath = filePathDialog.getFilePath();
////			if (filePath.equals(""))
////				return true;
////
////			ArrayList<JOutputHL7Directory> outputHL7
////				= JOutputData.getJOutputDir();
////
////			// HL7出力処理
////			for (int i = 0; i < outputHL7.size(); i++) {
////				// edit s.inoue 2009/09/25
////				JOutputHL7Directory outputHL7List = outputHL7.get(i);
////
////				// edit s.inoue 2010/01/05
////				// Data/HL7の時はコピー先コピー元が同じ回避
////				if ((JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
////						+ outputHL7List.GetUniqueName() + ".zip").equals(filePath + File.separator
////								+ outputHL7List.GetUniqueName() + ".zip"))break;
////
////				if( JFileCopy.copyFile(
////						// コピー元
////						JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
////						// edit s.inoue 2009/10/18
////						// + outputHL7List.GetUniqueName(i) + ".zip",
////						+ outputHL7List.GetUniqueName() + ".zip",
////
////						// コピー先
////						filePath + File.separator
////						// edit s.inoue 2009/10/18
////						// + outputHL7List.GetUniqueName(i) + ".zip"
////						+ outputHL7List.GetUniqueName() + ".zip"
////						) != true ){
////					JErrorMessage.show("M4720", this);
////				}
////			}
//
//		// edit s.inoue 2010/05/10 このソースを利用
//		JDirChooser dirSelect = new JDirChooser(JPath.getDesktopPath());
//		// edit s.inoue 2010/04/28
//		dirSelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		UIManager.put("FileChooser.readOnly", Boolean.FALSE);
//
//		// ファイル選択ダイアログの表示
//		// 出力するファイルを選択してください。
//		ArrayList<JOutputHL7Directory> outputHL7
//			= JOutputData.getJOutputDir();
//
//			// 出力先のフォルダを選択してください。
//			if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ){
//					// HL7出力処理
//					for (int i = 0; i < outputHL7.size(); i++) {
//						// edit s.inoue 2009/09/25
//						JOutputHL7Directory outputHL7List = outputHL7.get(i);
//
//						// edit s.inoue 2010/01/05
//						// Data/HL7の時はコピー先コピー元が同じ回避
//						if ((JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
//								+ outputHL7List.GetUniqueName() + ".zip").equals(dirSelect.getSelectedFile().getPath() + File.separator
//										+ outputHL7List.GetUniqueName() + ".zip"))break;
//
//						if( JFileCopy.copyFile(
//								// コピー元
//								JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
//								// edit s.inoue 2009/10/18
//								// + outputHL7List.GetUniqueName(i) + ".zip",
//								+ outputHL7List.GetUniqueName() + ".zip",
//
//								// コピー先
//								dirSelect.getSelectedFile().getPath() + File.separator
//								// edit s.inoue 2009/10/18
//								// + outputHL7List.GetUniqueName(i) + ".zip"
//								+ outputHL7List.GetUniqueName() + ".zip"
//								) != true ){
//							JErrorMessage.show("M4720", this);
//						}
//					}
//			}else{
//				retCansel = true;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//			JErrorMessage.show("M4720", this);
//		}
//		return retCansel;
//	}
//
//// edit s.inoue 2010/04/26
//// こちら修正前のソース
////	// add s.inoue 2009/09/18
////	private boolean HL7OutputFiles(JOutputGetujiFrameData JOutputData){
////
////		boolean retCansel = false;
////		// del s.inoue 2009/09/24
////		// String SAVESTRING = "出力するHL7ファイルを選択";
////		// String OUTSTRING = "HL7ファイルの出力先を選択";
////
////		// edit s.inoue 2009/10/16
////		JDirChooser dirSelect = new JDirChooser(JPath.getDesktopPath());
////		// del s.inoue 2009/09/24
////		// JFileChooser chooser = new JFileChooser(JPath.ZIP_OUTPUT_DIRECTORY_PATH);
////
////		// edit s.inoue 2009/09/18
////		// ファイル選択ダイアログの表示
////		// 出力するファイルを選択してください。
////		ArrayList<JOutputHL7Directory> outputHL7
////			= JOutputData.getJOutputDir();
////
////		try{
////			// 出力先のフォルダを選択してください。
////			if( dirSelect.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ){
////					// HL7出力処理
////					for (int i = 0; i < outputHL7.size(); i++) {
////						// edit s.inoue 2009/09/25
////						JOutputHL7Directory outputHL7List = outputHL7.get(i);
////
////						// edit s.inoue 2010/01/05
////						// Data/HL7の時はコピー先コピー元が同じ回避
////						if ((JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
////								+ outputHL7List.GetUniqueName() + ".zip").equals(dirSelect.getSelectedFile().getPath() + File.separator
////										+ outputHL7List.GetUniqueName() + ".zip"))break;
////
////						if( JFileCopy.copyFile(
////								// コピー元
////								JPath.CURRENT_DIR_PATH + File.separator + JPath.ZIP_OUTPUT_DIRECTORY_PATH
////								// edit s.inoue 2009/10/18
////								// + outputHL7List.GetUniqueName(i) + ".zip",
////								+ outputHL7List.GetUniqueName() + ".zip",
////
////								// コピー先
////								dirSelect.getSelectedFile().getPath() + File.separator
////								// edit s.inoue 2009/10/18
////								// + outputHL7List.GetUniqueName(i) + ".zip"
////								+ outputHL7List.GetUniqueName() + ".zip"
////								) != true ){
////							JErrorMessage.show("M4720", this);
////						}
////					}
////			}else{
////				retCansel = true;
////			}
////		} catch (IOException e) {
////			e.printStackTrace();
////			logger.error(e.getMessage());
////			JErrorMessage.show("M4720", this);
////		}
////
////		return retCansel;
////	}
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
//	// edit ver2 s.inoue 2009/08/31
//	// 確定実行時、workフォルダから実テーブルへデータを移行する
//	/**
//	 * 請求確定処理ボタンが押された場合
//	 */
//	public void pushedSeikyuButton( ActionEvent e )
//	{
//		// 選択状態を保持する
//		ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
//
//// del s.inoue 2010/04/06
////		switch(state)
////		{
////		case STATUS_AFTER_SEARCH:
//			int count = 0;
//
//			boolean notExtMessage = false;
//
//			datas = new Vector<JKessaiProcessData>();
//
//			RETURN_VALUE Ret = JErrorMessage.show("M4755", this);
//			if (Ret == RETURN_VALUE.NO) {
//				return;
//			}
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				// edit h.yoshitama 2009/09/03
//				if( Boolean.TRUE == (Boolean)modelfixed.getData(i,0)  )
//				{
//					count++;
//
//					Hashtable<String, String> item = result.get(i);
//
//					// add s.inoue 2010/01/19
//					checkKigenSetting(
//							item.get("KENSA_NENGAPI"),
//							item.get("HKNJYA_LIMITDATE_START"),
//							item.get("HKNJYA_LIMITDATE_END"));
//
//					JKessaiProcessData dataItem = new JKessaiProcessData();
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
//
//					// 決済、集計総tran
//					JApplication.kikanDatabase.Transaction();
//
//					JOutputGetujiFrameData validatedData = new JOutputGetujiFrameData();
//
//					if( validatedData.setSyubetuCode((String)jComboBox_SyubetuCode.getSelectedItem())
//					)
//					{
//						try {
//							/* work→T_KESSAI処理 */
//							// edit ver2 s.inoue 2009/09/09
//							JKessaiProcess.runWorkToKessaiProcess(datas, JApplication.kikanNumber);
//
//						} catch (Exception e1) {
//							logger.error(e1.getMessage());
//							JApplication.kikanDatabase.rollback();
//							JErrorMessage.show("M4730", this);
//							return;
//						}
//
//						try {
//							/* 集計処理 */
//							if(JSyuukeiProcess.runWorkToSyukeiProcess(datas) == false)
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
//						messageDialog.setMessageTitle("請求確定処理");
//						messageDialog.setText("請求確定処理が終了しました");
//						messageDialog.setVisible(true);
//					}
//
//					// 決済、集計総Commit
//					JApplication.kikanDatabase.Commit();
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
//// del s.inoue 2010/04/06
////		}
//		buttonCtrl();
//		// add s.inoue 2009/10/27
//		searchRefresh();
//		table.setselectedRows(modelfixed,selectedRows);
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
//		}catch(SQLException f){
//			f.printStackTrace();
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
//	public void pushedSeikyuPrintButton( ActionEvent e )
//	{
//		// 選択行を抽出する。
//		int rowCount = table.getRowCount();
//		// 印刷用
//		TreeMap<String, Object> printDataGetuji = new TreeMap<String, Object>();
//
//		// ステータス画面
//		ProgressWindow progressWindow = new ProgressWindow(this, false,true);
//		try{
//			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//			// edit s.inoue 2009/11/02
//			ArrayList<String> keyList =  new ArrayList<String>();
//
//			// 選択チェック
//			for (int i = 0; i < rowCount; i++) {
//				if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//					selectedRows.add(i);
//				}
//			}
//
//			// 選択行なし
//			int selectedCount = selectedRows.size();
//			if (selectedCount == 0) {
//				JErrorMessage.show("M3548", this);return;
//			}
//
//			progressWindow.setGuidanceByKey("common.frame.progress.guidance.main");
//			progressWindow.setVisible(true);
//
//			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
//
//			// 月次データ作成
//			for (int j = 0; j < selectedCount; j++) {
//
//				int k = selectedRows.get(j);
//
//				// 受付ID
//				String uketukeId = result.get(k).get("UKETUKE_ID");
//				tmpKojin.put("UKETUKE_ID", uketukeId);
//
//				Gekeihyo gekeihyo = new Gekeihyo();
//
//				if (!gekeihyo.setPrintSeikyuGetujiDataSQL(tmpKojin)){
//				 	// データ移送失敗
//					logger.warn("月次データ作成失敗");
//					progressWindow.setVisible(false);
//				 	JErrorMessage.show("M3530", this);
//				}
//
//				// 印刷 受付ID:印刷データ
//				printDataGetuji.put(String.valueOf(uketukeId), gekeihyo);
//				keyList.add(String.valueOf(uketukeId));
//			}
//
//			/*
//			 * Kikan作成
//			 */
//			Kikan kikanData = new Kikan();
//			if (!kikanData.setPrintKikanDataSQL()) {
//				// データ移送失敗
//				JErrorMessage.show("M4941", this);
//			}
//
//			Hashtable<String, Object> printData = new Hashtable<String, Object>();
//			printData.put("Kikan", kikanData);
//
//			// 印刷実行
//			PrintSeikyuGetuji getuji = new PrintSeikyuGetuji();
//
//			// add s.inoue 2009/10/15
//			// 集計表用キー
//			ArrayList<String> selectColumn = new ArrayList<String>();
//			for (int i = 0; i < table.getRowCount(); i++) {
//				if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//
//					// edit s.inoue 2009/10/27
//					String hokenjya = table.getData(i, COLUMN_INDEX_HKNJANUM).toString();
//					String daikoukikan = table.getData(i, COLUMN_INDEX_SIHARAI_DAIKOU_BANGO).toString();
//
//					if (!daikoukikan.equals("")){
//						selectColumn.add(daikoukikan);
//					}else if(!hokenjya.equals("")){
//						selectColumn.add(hokenjya);
//					}
//				}
//			}
//
//			getuji.setPrintShukeiKey(selectColumn);
//
//			getuji.setQueryGetujiResult(printDataGetuji);
//			getuji.setQueryResult(printData);
//			getuji.setKeys(keyList);
//
//			// edit s.inoue 2009/10/29
//			getuji.beginPrint();
//		} catch (Exception err) {
//			err.printStackTrace();
//			progressWindow.setVisible(false);
//			JErrorMessage.show("M4734", this);
//		} finally {
//			progressWindow.setVisible(false);
//		}
//	}
//
//	// add s.inoue 2009/10/27
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
//		       	    		// edit s.inoue 2009/11/18
//		       	    		if(table.getData(i,COLUMN_INDEX_TANKA_GOUKEI) != null){
//		       	    			sumTanka = String.valueOf(table.getData(i,COLUMN_INDEX_TANKA_GOUKEI)).replaceAll(",", "");;
//		       	    		}
//		       	    		if(table.getData(i,COLUMN_INDEX_MADO_FUTAN_GOUKEI) != null){
//		       	    			sumMadoguti = String.valueOf(table.getData(i,COLUMN_INDEX_MADO_FUTAN_GOUKEI)).replaceAll(",", "");;
//		       	    		}
//		       	    		if(table.getData(i,COLUMN_INDEX_SEIKYU_KINGAKU) != null){
//		       	    			sumSeikyu = String.valueOf(table.getData(i,COLUMN_INDEX_SEIKYU_KINGAKU)).replaceAll(",", "");;
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
//				jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this,ctrl);
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
//// del s.inoue 2010/04/19
////		switch(state)
////		{
////		case STATUS_AFTER_SEARCH:
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
////			break;
////		}
//		buttonCtrl();
//	}
//
//	/**
//	 * 検索ボタンが押された場合 ６
//	 */
//	public void pushedSearchButton( ActionEvent e )
//	{
//		// edit s.inoue 2010/04/06
//		state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
//		if(!searchCondition(false))
//			searchRefresh();
//	}
//
//	// edit s.inoue 2009/10/27
//	private boolean searchCondition(boolean initialize){
//
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
//
//			Object[][] obj = resultRefresh();
//
//			if(!jCheckBox_IsPermitHL7.isSelected())
//				return retflg;
//			if (!initialize){
//				for (int i = 0; i < obj.length; i++) {
//					String tutiNengapi = (String) obj[i][COLUMN_INDEX_HENKAN_NITIJI];
//					if (!tutiNengapi.equals("")){
//						RETURN_VALUE Ret = JErrorMessage.show("M4740", this);
//						if(Ret == RETURN_VALUE.NO){
//							state = JOutputHL7FrameState.STATUS_INITIALIZED;
//							buttonCtrl();
//							retflg = true;
//							break;
//						}else{
//							break;
//						}
//					}
//				}
//			}
//			break;
////			// edit s.inoue 2009/10/26
////			// rowCount = tableRefresh();
////			// if( rowCount == 0 )
////			// {
////			if (resultRefresh() == null){
////				// state = JOutputHL7FrameState.STATUS_INITIALIZED;
////				// edit s.inoue 2009/10/27
////				retflg= true;
////			}else{
////				// add ver2 s.inoue 2009/06/24
////				if (!initialize){
////					// 検索結果にＨＬ７変換済みの受診者がいるかどうかチェックする
////					for(int i = 0; i < table.getRowCount();i++ )
////					{
////						// edit ver2 s.inoue 2009/06/23
////						//Object tutiNengapi = model.getData(i, 7);
////						// edit h.yoshitama 2009/09/03
////						Object tutiNengapi = table.getData(i, COLUMN_INDEX_HENKAN_NITIJI);
////						if(tutiNengapi != null && ! tutiNengapi.equals("") )
////						{
////							// add s.inoue 2009/12/15
////							if(!jCheckBox_IsPermitHL7.isSelected())
////								return retflg;
////
////							RETURN_VALUE Ret = JErrorMessage.show("M4740", this);
////							if(Ret == RETURN_VALUE.NO)
////							{
////								state = JOutputHL7FrameState.STATUS_INITIALIZED;
////								buttonCtrl();
////								// del s.inoue 2010/03/23
//////								table.clearAllRow();
//////								modelfixed.clearAllRow();
////								// edit s.inoue 2010/03/23
////								retflg = false;
////								break;
////							}else{
////								break;
////							}
////						}
////					}
////				}
////				state = JOutputHL7FrameState.STATUS_AFTER_SEARCH;
////			}
////			break;
//		}
//		buttonCtrl();
//		return retflg;
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//		// 5
//		if( source == jButton_End)
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//		// 請求確定処理ボタン
//		else if( source == jButton_Seikyu)
//		{
//			logger.info(jButton_Seikyu.getText());
//			pushedSeikyuButton( e );
//		}
//		// HL7 出力ボタン
//		else if( source == jButton_HL7Output)
//		{
//			logger.info(jButton_HL7Output.getText());
//			pushedHL7OutputButton( e );
//		}
//		// edit ver2 s.inoue 2009/08/17
//		else if( source == jButton_SeikyuListPrint)
//		{
//			logger.info(jButton_SeikyuListPrint.getText());
//			pushedSeikyuPrintButton( e );
//		}
//		// 全選択 ボタン
//		else if( source == jButton_AllSelect)
//		{
//			logger.info(jButton_AllSelect.getText());
//			pushedAllSelectButton( e );
//		}
//		// 検索ボタン
//		else if( source == jButton_Search)
//		{
//			logger.info(jButton_Search.getText());
//			pushedSearchButton( e );
//		}
//	}
//
//	// add h.yoshitama 2009/11/11
//	public void itemStateChanged(ItemEvent e){
//		if ((e.getSource()) == jCheckBox_IsPermitHL7) {
//			if( !jCheckBox_IsPermitHL7.isSelected() )
//			{
//				jTextField_HL7BeginDate.setEditable(false);
//				jTextField_HL7BeginDate.setEnabled(false);
//				jTextField_HL7BeginDate.setText("");
//				jTextField_HL7EndDate.setEditable(false);
//				jTextField_HL7EndDate.setEnabled(false);
//				jTextField_HL7EndDate.setText("");
//			} else {
//				jTextField_HL7BeginDate.setEditable(true);
//				jTextField_HL7BeginDate.setEnabled(true);
//				jTextField_HL7EndDate.setEditable(true);
//				jTextField_HL7EndDate.setEnabled(true);
//			}
//		}
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
//		case KeyEvent.VK_F7:
//			if (jButton_Seikyu.isEnabled()){
//				logger.info(jButton_Seikyu.getText());
//				pushedSeikyuButton(null);
//			}
//			break;
//		case KeyEvent.VK_F9:
//			if (jButton_HL7Output.isEnabled()){
//				logger.info(jButton_HL7Output.getText());
//				pushedHL7OutputButton(null);
//			}
//			break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_SeikyuListPrint.getText());
//			pushedSeikyuPrintButton(null);break;
//
//		case KeyEvent.VK_S:
//			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
//				logger.info(jButton_Search.getText());
//				pushedSearchButton( null );
//			}
//			break;
//		}
//	}
//
//	// del s.inoue 2009/09/14
////	/**
////	 * 支払代行機関が設定されているかを調べる。
////	 */
////	private boolean existsShiharaiDaikoKikan(int i) {
////
////		/* 支払代行機関番号の有無を調べる */
////		String Query = "select * from T_KOJIN " +
////									" where " +
////									" UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
////									" SIHARAI_DAIKOU_BANGO IS NULL";
////
////		ArrayList<Hashtable<String, String>> tbl = null;
////		try{
////			tbl = JApplication.kikanDatabase.sendExecuteQuery(Query);
////		}catch(SQLException f){
////			f.printStackTrace();
////		}
////
////		if( ! tbl.isEmpty() ){
////			return true;
////		}
////
////		return false;
////	}
//
//	// edit s.inoue 2009/10/26
////	/**
////	 * 検索結果をテーブルに追加する。
////	 */
////	private void addRowToTable() {
////		Hashtable<String, String> resultItem;
////		model.clearAllRow();
////		// edit h.yoshitama 2009/09/03
////		modelfixed.clearAllRow();
////		for(int i = 0;i < result.size();i++ )
////		{
////			Vector<String> row = new Vector<String>();
////			Vector<String> rowfixed = new Vector<String>();
////			resultItem = result.get(i);
////
////			// edit h.yoshitama 2009/09/03
//////			row.add(null);
//////			/* 年度 */
//////			row.add(resultItem.get("NENDO"));
//////			/* 受診券整理番号 */
//////			row.add(resultItem.get("JYUSHIN_SEIRI_NO"));
//////			/* 氏名（カナ） */
//////			row.add(resultItem.get("KANANAME"));
////			rowfixed.add(null);
////			/* 年度 */
////			rowfixed.add(resultItem.get("NENDO"));
////			/* 受診券整理番号 */
////			rowfixed.add(resultItem.get("JYUSHIN_SEIRI_NO"));
////			/* 氏名（カナ） */
////			rowfixed.add(resultItem.get("KANANAME"));
////			/* 氏名（名前） */
////			row.add(resultItem.get("NAME"));
////			/* 性別 */
////			String sexCode = resultItem.get("SEX");
////			String sexText = "";
////			if (sexCode.equals("1")) {
////				sexText = "男性";
////			}
////			else if(sexCode.equals("2")){
////				sexText = "女性";
////			}
////			row.add(sexText);
////
////			/* 生年月日 */
////			row.add(resultItem.get("BIRTHDAY"));
////			/* 健診実施日 */
////			row.add(resultItem.get("KENSA_NENGAPI"));
////			/* 変換実施日 */
////			row.add(resultItem.get("HENKAN_NITIJI"));
////			/* 保険者番号 */
////			row.add(resultItem.get("HKNJANUM"));
////			// add s.inoue 2009/10/20
////			/* 代行機関番号 */
////			row.add(resultItem.get("SIHARAI_DAIKOU_BANGO"));
////
////			/* 被保険者証等記号 */
////			row.add(resultItem.get("HIHOKENJYASYO_KIGOU"));
////			/* 被保険者証等番号 */
////			row.add(resultItem.get("HIHOKENJYASYO_NO"));
////
////			// add ver2 s.inoue 2009/08/03
////			/* 単価合計 */
////			row.add(resultItem.get("TANKA_GOUKEI"));
////			/* 窓口負担合計 */
////			row.add(resultItem.get("MADO_FUTAN_GOUKEI"));
////			/* 請求金額合計 */
////			row.add(resultItem.get("SEIKYU_KINGAKU"));
////
////			// edit s.inoue 2009/09/11
////			/* 更新日時 */
////			row.add(resultItem.get("UPDATE_TIMESTAMP").replaceAll("-",""));
////
////			model.addData(row);
////			// add h.yoshitama 2009/09/03
////			modelfixed.addData(rowfixed);
////		}
////	}
//	// del s.inoue 2009/09/29
//	// 仕様から外す（後日必要だったらコメントアウトを外す）
////	        model.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
////				@Override
////				public void valueChanged(ListSelectionEvent e) {
////					// TODO 自動生成されたメソッド・スタブ
////		       	    if(e.getValueIsAdjusting()) return;
////		       	    int intTanka = 0;
////		       	    int intMadoguti = 0;
////		       	    int intSeikyu = 0;
////		       	    // edit h.yoshitama 2009/09/03
////		       	    for(int i =0;i < model.getRowCount();i++){
////		       	    	if((Boolean)modelfixed.getData(i,0) == Boolean.TRUE)
////		    			{
////		       	    		// 単価、窓口負担、請求額
////		       	    		String sumTanka = String.valueOf(model.getData(i,12-modelfixed.getColumnCount()));
////		       	    		String sumMadoguti = String.valueOf(model.getData(i,13-modelfixed.getColumnCount()));
////		       	    		String sumSeikyu = String.valueOf(model.getData(i,14-modelfixed.getColumnCount()));
//	//
////		       	    		// 選択行の合計処理
////		       	    		if (! sumTanka.isEmpty() ){
////		       	    			intTanka += Integer.parseInt(sumTanka);
////		       	    		}
////		       	    		if (! sumMadoguti.isEmpty() ){
////		       	    			intMadoguti += Integer.parseInt(sumMadoguti);
////		       	    		}
////		       	    		if (! sumSeikyu.isEmpty() ){
////		       	    			intSeikyu += Integer.parseInt(sumSeikyu);
////		       	    		}
////		    			}
////		       	    }
//	//
////		       	    // edit s.inoue 2009/09/29
////		       	    // 単価、窓口負担、請求額
////		       	    if (intTanka > 0){
////		       	    	jTextField_TankaGoukei.setText(String.valueOf(intTanka));
////		       	    }else{
////		       	    	jTextField_TankaGoukei.setText("");
////		       	    }
////		       	    if (intMadoguti > 0){
////		       	    	jTextField_FutanGoukei.setText(String.valueOf(intMadoguti));
////		       	    }else{
////		       	    	jTextField_FutanGoukei.setText("");
////		       	    }
////		       	    if (intSeikyu > 0){
////		       	    	jTextField_SeikyuGoukei.setText(String.valueOf(intSeikyu));
////		       	    }else{
////		       	    	jTextField_SeikyuGoukei.setText("");
////		       	    }
////		       	    // add h.yoshitama 2009/09/03
////		       	    System.out.println(modelfixed.getSelectedRow() +"行選択イベント成功");
////	        	    System.out.println(model.getSelectedRow() +"行選択イベント成功");
////				}
////	        	});
//	// del s.inoue 2009/09/18
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
//}
