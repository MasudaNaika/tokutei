//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Component;
//import java.awt.event.ActionEvent;
//import java.awt.event.InputEvent;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.print.PrinterException;
//import java.sql.SQLException;
//
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.TreeMap;
//import java.util.Iterator;
//
//import javax.swing.AbstractButton;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JViewport;
//import javax.swing.ListSelectionModel;
//import javax.swing.WindowConstants;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
//import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
//import jp.or.med.orca.jma_tokutei.common.scene.JScene;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintIraisho;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintNyuryoku;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
//import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//// del s.inoue 2009/10/08
//// リスト廃止
///* カレンダー関係 */
////import org.eclipse.swt.SWT;
////import org.eclipse.swt.events.ShellAdapter;
////import org.eclipse.swt.events.ShellEvent;
////import org.eclipse.swt.layout.FillLayout;
////import org.eclipse.swt.widgets.Display;
////import org.eclipse.swt.widgets.MessageBox;
////import org.eclipse.swt.widgets.Shell;
////import org.eclipse.swt.widgets.Text;
////import org.eclipse.swt.widgets.Button;
////import org.eclipse.swt.widgets.Listener;
////import org.eclipse.swt.widgets.Event;
////import java.text.ParseException;
////import java.text.SimpleDateFormat;
////import java.util.Date;
//
///**
// * 検査結果一覧
// */
//public class JKekkaListFrameCtrl extends JKekkaListFrame {
//
//	private static Logger logger = Logger.getLogger(JKekkaListFrameCtrl.class);
//
//	private static final String SQL_DELETE_KOJIN ="delete from T_KOJIN where UKETUKE_ID = ? ";
//	private static final String SQL_DELETE_NAYOSE ="delete from T_NAYOSE where UKETUKE_ID = ? ";  //  @jve:decl-index=0:
//	private static final String SQL_SELECT_TOKUTEI ="select count(1) as num from T_KENSAKEKA_TOKUTEI where UKETUKE_ID = ? ";
//	private static final String SQL_DELETE_TOKUTEI ="delete from T_KENSAKEKA_TOKUTEI where UKETUKE_ID = ? and KENSA_NENGAPI = ? ";
//	private static final String SQL_DELETE_SONOTA ="delete from T_KENSAKEKA_SONOTA where UKETUKE_ID = ? and KENSA_NENGAPI = ? ";
//
//	// add s.inoue 2010/02/23
//	/** 保険者番号、支払代行機関選択欄の、番号と名称の区切り文字列 */
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";
//
//	// 全選択ボタンの状態
//	boolean isAllSelect = true;
//
//	// edit s.inoue 2010/04/27
//	private ArrayList<Integer> selectedRows = null;
//
//	// 検索ボタン押下時のSQLで使用
//	private static final String[] SQL_SELECT_PART = {
//			"'' as CHECKBOX_COLUMN,",
//			"kojin.UKETUKE_ID,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.BIRTHDAY,",
//			"kojin.SEX,",
//			"case kojin.SEX when 1 then '男性' when 2 then '女性' end as SEX_NAME,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.JYUSHIN_SEIRI_NO,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.HKNJANUM,",
//			// edit s.inoue 2009/10/31
//			"kojin.SIHARAI_DAIKOU_BANGO,",
//			// edit s.inoue 2009/10/31
//			"kojin.NAME,",
//			"kojin.KANANAME,",
//			"tokutei.KENSA_NENGAPI,",
//			"tokutei.HANTEI_NENGAPI,",
//			"tokutei.TUTI_NENGAPI,",
//			// add ver2 s.inoue 2009/05/22
//			"GET_NENDO.NENDO, ",
//			"case tokutei.KEKA_INPUT_FLG when 1 then '未' "
//					+ "when 2 then '済' else '未' end as KEKA_INPUT_FLG " };
//
//	// 検索ボタン押下時のSQLで使用
//	private static final String[] TABLE_COLUMNS = { "CHECKBOX_COLUMN",
//			"NENDO","JYUSHIN_SEIRI_NO", "KANANAME","NAME", "BIRTHDAY","SEX_NAME","KEKA_INPUT_FLG", "HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_NO",
//			"KENSA_NENGAPI", "HANTEI_NENGAPI", "TUTI_NENGAPI", "HKNJANUM","SIHARAI_DAIKOU_BANGO" };
//
//	private JKekkaListFrameData validatedData = new JKekkaListFrameData();
//	private String[] header = { "", "年度","受診券整理番号", "氏名（カナ）","氏名（漢字）","生年月日", "性別", "入力","被保険者証等記号","被保険者証等番号", "健診実施日", "判定日", "結果通知日","保険者番号","代行機関番号" };
//	private ArrayList<Hashtable<String, String>> result;
//	private Hashtable<String, String> resultItem;
//	private static final int inputflgColumn = 7;
//
//	// edit s.inoue 2009/12/11
//	private ExtendedCheckBox checkedTable = new ExtendedCheckBox();
//	private ExtendedCheckBox checkedmodel = new ExtendedCheckBox();
//
//	// add s.inoue 2009/10/26
//	// private JSimpleTable model = new JSimpleTable();
//	private DefaultTableModel dmodel = null;
//	private TableRowSorter<TableModel> sorter =null;
//	private JSimpleTable table=null;
//	private JSimpleTable modelfixed= null;
//
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//	private TKojinDao tKojinDao;
//	private IDialog dateSelectDialog;
//	Object[][] rowcolumn = null;
//	/**
//	 * デフォルトコンストラクタ
//	 */
//	public JKekkaListFrameCtrl() {
//		initializeSetting();
//	}
//
//	// edit s.inoue 2009/10/31
//	private void initializeSetting(){
//		// edit s.inoue 2009/10/27
//		dmodel = new DefaultTableModel(resultRefresh(false),header){
//	      public boolean isCellEditable(int row, int column) {
//	    	boolean retflg = false;
//	    	if (column == 0 || column >14){
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
//		modelfixed = new JSimpleTable(dmodel);
//
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
//		table.setPreferedColumnWidths(new int[] {120,80, 40, 40,110, 110, 80, 80, 80,80,80});
//		// add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<5) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
//            }
//        }
//
//	    table.setRowSorter(sorter);
//	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    modelfixed.setRowSorter(sorter);
//	    modelfixed.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    modelfixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//	    initilizeFocus();
//	    // add s.inoue 2009/12/01
//		functionListner();
//	   	// add s.inoue 2010/02/23
//		initializeHokenjaNumComboBox();
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
//		jCheckBox_InputDone.setSelected(true);
//		jCheckBox_JisiYear.setSelected(true);
//		jCheckBox_male.setSelected(true);
//		jCheckBox_female.setSelected(true);
//		addWindowListener(new ClosingWindowListener());
//
//		// del s.inoue 2009/12/21
//		// table.addKeyListener(new JEnterEvent(this, jButton_OK));
//
//		// edit s.inoue 2009/12/12
//		// ダブルクリックの処理
//		table.addMouseListener(new JSingleDoubleClickEvent(this, jButton_OK,modelfixed));
//		modelfixed.addMouseListener(new JSingleDoubleClickEvent(this, jButton_OK,modelfixed));
//
//		dmodel.setDataVector(resultRefresh(false),header);
//
//		// add s.inoue 2009/10/26
//		// 4列以下は固定、以降は変動
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
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
//	   // editing s.inoue 2009/12/07
//	   // modelfixed.setCellCheckBoxEditor(new JCheckBox(), 0);
//	   // ExtendedCheckBox checkedmodel=new ExtendedCheckBox();
//
//	   modelfixed.setCellCheckBoxEditor(checkedmodel, 0);
//
//	   // add s.inoue 2009/11/12
//	   table.addHeader(this.header);
//	   // del s.inoue 2010/07/07
//	   // table.setAutoCreateRowSorter(true);
//	   table.refreshTable();
//
//	   // edit s.inoue 2009/11/09
//	   // 初期選択
//	   int count = 0;
//	   if (table.getRowCount() > 0) {
//		   table.setRowSelectionInterval(0, 0);
//		   count = table.getRowCount();
//// del s.inoue 2009/12/03
////	   } else {
////		   jTextField_Name.requestFocus();
//	   }
//	   jLabel_count.setText(count + " 件");
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
//
//		     if(i <= 0) return;
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
//
//	private class ClosingWindowListener extends WindowAdapter
//	   {
//	      public void windowClosing(WindowEvent e)
//	      {
//	         System.exit(0);
//	      }
//	}
//
//	/* focus初期化 */
//	private void initilizeFocus(){
//
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_Name);
//		this.focusTraversalPolicy.addComponent(jTextField_Name);
//		this.focusTraversalPolicy.addComponent(jTextField_Jyusinken_ID);
//		// edit s.inoue 2010/02/23
//		this.focusTraversalPolicy.addComponent(jComboBox_HokenjyaNumber);
//
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_male);
//		this.focusTraversalPolicy.addComponent(this.jCheckBox_female);
//		this.focusTraversalPolicy.addComponent(this.jTextField_birthday);
//		this.focusTraversalPolicy.addComponent(this.jTextField_ageStart);
//		this.focusTraversalPolicy.addComponent(this.jTextField_ageEnd);
//
//		this.focusTraversalPolicy.addComponent(jTextField_KensaBeginDate);
//		this.focusTraversalPolicy.addComponent(jTextField_KensaEndDate);
//		this.focusTraversalPolicy.addComponent(jTextField_HanteiBeginDate);
//		this.focusTraversalPolicy.addComponent(jTextField_HanteiEndDate);
//		this.focusTraversalPolicy.addComponent(jTextField_TsuuchiBeginDate);
//		this.focusTraversalPolicy.addComponent(jTextField_TsuuchiEndDate);
//		this.focusTraversalPolicy.addComponent(jCheckBox_InputDone);
//		// del s.inoue 2009/10/07
//		// this.focusTraversalPolicy.addComponent(jCheckBox_InputYet);
//		this.focusTraversalPolicy.addComponent(jCheckBox_JisiYear);
//		this.focusTraversalPolicy.addComponent(jButton_Search);
//		this.focusTraversalPolicy.addComponent(this.table);
//		this.focusTraversalPolicy.addComponent(jButton_Copy);
//		this.focusTraversalPolicy.addComponent(jButton_OK);
//		this.focusTraversalPolicy.addComponent(jButton_Print);
//		this.focusTraversalPolicy.addComponent(jButton_KojinAdd);
//		this.focusTraversalPolicy.addComponent(jButton_Kojin);
//		this.focusTraversalPolicy.addComponent(jButton_Irai);
//		this.focusTraversalPolicy.addComponent(jButton_DeleteKekka);
//		this.focusTraversalPolicy.addComponent(jButton_DeleteKojin);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//
//	}
//
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
//	// add s.inoue 2010/02/23
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
//	 * 検索ボタンを押した際の必須項目の検証
//	 */
//	public boolean validateAsSearchPushed(JKekkaListFrameData data) {
//		data.setValidateAsDataSet();
//		return true;
//	}
//
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton(ActionEvent e) {
//		dispose();
//	}
//
//	/**
//	 * 検索ボタン
//	 */
//	public void pushedSearchButton(boolean refleshFlg) {
//		searchRefresh(refleshFlg);
//	}
//	/*
//	 * 検索リフレッシュ処理
//	 */
//	private void searchRefresh(boolean refleshFlg){
//
//		modelfixed.setPreferedColumnWidths(new int[] {50, 50, 100, 175});
//		table.setPreferedColumnWidths(new int[] {120, 80, 40, 40, 100, 100, 80, 80, 80, 100, 100, 200, 0});
//
//		// add s.inoue 2009/10/23
//	    modelfixed.setSelectionModel(table.getSelectionModel());
//
//	    // validationCheck
//	    Object [][] resultref=resultRefresh(refleshFlg);
//	    if (resultref == null)
//	    	return;
//
//	    // edit s.inoue 2010/02/16
//	    if (resultref.length == 0){
//	    	JErrorMessage.show("M3550", this);
//	    }
//
//	    // add s.inoue 2009/10/26
//	    for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<5) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//            }
//        }
//
//	    dmodel.setDataVector(resultref,header);
//
//		// add s.inoue 2009/10/26
//		// 4列以下は固定、以降は変動
//		for(int i=dmodel.getColumnCount()-1;i>=0;i--) {
//            if(i<4) {
//                table.removeColumn(table.getColumnModel().getColumn(i));
//                modelfixed.getColumnModel().getColumn(i).setResizable(false);
//            }else{
//                modelfixed.removeColumn(modelfixed.getColumnModel().getColumn(i));
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
////		this.initializeColumnWidth();
//
//		// 固定,変動リフレッシュ
//	   // edit s.inoue 2009/12/11
//	   // checkedTable = new ExtendedCheckBox();
//
//	   // del s.inoue 2009/12/21
//	   checkedTable.addKeyListener(this);
//
//	   // modelfixed.setCellCheckBoxEditor(new JCheckBox(), 0);
//	   modelfixed.setCellCheckBoxEditor(checkedTable, 0);
//
//	    // add s.inoue 2009/11/12
//	    table.addHeader(this.header);
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
//	// edit s.inoue 2009/10/26
//	// 一覧用データ取得
//	private Object[][] resultRefresh(boolean refleshFlg)
//	{
//		// add s.inoue 2009/10/06
//		ArrayList<Integer> selectedRows = null;
//		if (refleshFlg) {
//			// 選択状態を保持する
//			selectedRows = modelfixed.getselectedRows(modelfixed);
//		}
//
//		if (!isSearchOK())
//			return null;
//
//		String sql = buildSQL();
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		//	model.clearAllRow();
//		rowcolumn = new Object[result.size()][17];
//		int counti = 0;
//		List<String> fields = new ArrayList<String>();
//		for (Hashtable<String, String> record : result) {
//			for (String fieldName : TABLE_COLUMNS) {
//				fields.add(record.get(fieldName));
//			}
//			fields.set(0, null);
//			table.addData(fields.toArray(new String[0]));
//			//rowcolumn[i][COLUMN_INDEX_CHECK] = null;
//
//			String[] col = fields.toArray(new String[0]);
//			for (int i = 0; i < col.length; i++) {
//				rowcolumn[counti][i] = col[i];
//			}
//
//			counti ++;
//
//			fields.clear();
//		}
//
//		// add s.inoue 2009/10/06
//		if (refleshFlg) {
//			modelfixed.setselectedRows(modelfixed,selectedRows);
//		}
//
//		return rowcolumn;
//	}
//
//	/**
//	 * 検索実行OKか検証する
//	 *
//	 * @return
//	 */
//	private boolean isSearchOK() {
//		String sex = "";
//
//		if (this.jCheckBox_male.isSelected()) {
//			if (this.jCheckBox_female.isSelected()) {
//				sex = "";
//			}
//			else {
//				sex = "1";
//			}
//		}
//		else {
//			if(this.jCheckBox_female.isSelected()){
//				sex = "2";
//			}
//			else {
//				sex = "0";
//			}
//		}
//
//		if (this.jRadioButton_Male.isSelected()) {
//			sex = "1";
//		}
//		else if(this.jRadioButton_Female.isSelected()){
//			sex = "2";
//		}
//
//		// edit s.inoue 2010/02/23
//		/* コンボボックスで選択されている保険者番号、名称を取得する。 */
//		String hokenjaNumberAndNameString = (String)jComboBox_HokenjyaNumber.getSelectedItem();
//		String hokenjaNumber = "";
//		String hokenjaName = "";
//		if (hokenjaNumberAndNameString != null && ! hokenjaNumberAndNameString.isEmpty()) {
//			String[] hokenjaNumberAndName = hokenjaNumberAndNameString.split(COMBOBOX_NUMBER_NAME_SEPARATOR);
//			hokenjaNumber = hokenjaNumberAndName[0];
//
//			if (hokenjaNumberAndName.length > 1) {
//				hokenjaName = hokenjaNumberAndName[1];
//			}
//		}
//
//		return
//
//		validatedData.setJyushinkenID(jTextField_Jyusinken_ID.getText())
//		&& validatedData.setHokenjyaNumber(hokenjaNumber)
//		&& validatedData.setName(jTextField_Name.getText())
//		&& validatedData.setSex(sex)
//		&& validatedData.setBirthDay(jTextField_birthday.getText())
//		&& validatedData.setAgeStart(jTextField_ageStart.getText())
//		&& validatedData.setAgeEnd(jTextField_ageEnd.getText())
//		&& validatedData.setKensaBeginDate(jTextField_KensaBeginDate.getText())
//		&& validatedData.setKensaEndDate(jTextField_KensaEndDate.getText())
//		&& validatedData.setHanteiBeginDate(jTextField_HanteiBeginDate.getText())
//		&& validatedData.setHanteiEndDate(jTextField_HanteiEndDate.getText())
//		&& validatedData.setTsuuchiBeginDate(jTextField_TsuuchiBeginDate.getText())
//		&& validatedData.setTsuuchiEndDate(jTextField_TsuuchiEndDate.getText());
//	}
//
//	/**
//	 * SQL文を組み立てる
//	 *
//	 * @return SQL文
//	 */
//	private String buildSQL() {
//		StringBuilder query = new StringBuilder("select ");
//		for (String element : SQL_SELECT_PART) {
//			query.append(element);
//		}
//		query.append(" from T_KOJIN AS kojin ");
//		query.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI AS tokutei ");
//		query.append(" ON kojin.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		// add ver2 s.inoue 2009/05/22
//		query.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
//		// edit ver2 s.inoue 2009/07/13
//		//query.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
//		query.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//		query.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//		query.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//		query.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
//		query.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
//
//		query.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
//		query.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		query.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
//
//		query.append(buildWherePart());
//
//		// edit ver2 s.inoue 2009/06/29
//		query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		return query.toString();
//	}
//
//	/**
//	 * 設定条件に合わせてWHERE節を組み立てる
//	 */
//	private String buildWherePart() {
//
//		ArrayList<String> conditions = new ArrayList<String>();
//
//		String jyushinkenId = jTextField_Jyusinken_ID.getText();
//		if (jyushinkenId != null && !jyushinkenId.isEmpty()) {
//			// edit 20081226 s.inoue あいまい検索不備
//			// conditions.add(" kojin.JYUSHIN_SEIRI_NO like "
//			//		+ JQueryConvert.queryLikeConvert(jyushinkenId));
//			 conditions.add(" kojin.JYUSHIN_SEIRI_NO STARTING WITH "
//					+ JQueryConvert.queryConvert(jyushinkenId));
//		}
//		if (!validatedData.getHokenjyaNumber().isEmpty()) {
//			// edit 20081226 s.inoue あいまい検索不備
//			// conditions.add(" kojin.HKNJANUM like "
//			//		+ JQueryConvert.queryLikeConvert(validatedData
//			//				.getHokenjyaNumber()));
//			// edit s.inoue 2009/11/02
//			conditions.add(" kojin.HKNJANUM STARTING WITH "
//					+ JQueryConvert.queryConvert(JValidate.fillZero(validatedData
//							.getHokenjyaNumber(), 8)));
//		}
//		if (!validatedData.getName().isEmpty()) {
//			// edit 20081226 s.inoue あいまい検索不備
//			// conditions.add(" kojin.KANANAME like "
//			//		+ JQueryConvert.queryLikeConvert(validatedData.getName()));
//			 conditions.add(" kojin.KANANAME STARTING WITH "
//					+ JQueryConvert.queryConvert(validatedData.getName()));
//		}
//
//		/* 性別 */
//		String sex = validatedData.getSex();
//
//		if (! sex.isEmpty()) {
//			conditions.add(" kojin.SEX = "	+ JQueryConvert.queryConvert(sex));
//		}
//
//		/* 生年月日 */
//		String birthday = validatedData.getBirthDay();
//		if (! birthday.isEmpty()) {
//			conditions.add(" kojin.BIRTHDAY = "	+ JQueryConvert.queryConvert(birthday));
//		}
//
//		/* 年齢（下限） */
//		String ageStart = validatedData.getAgeStart();
//		if(! ageStart.isEmpty()){
//			conditions.add(JQueryConvert.queryConvert(ageStart)
//					+ " <= CAST( ( tokutei.KENSA_NENGAPI - kojin.BIRTHDAY ) / 10000 - 0.5 AS INTEGER) ");
//		}
//
//		/* 年齢（上限） */
//		String ageEnd = validatedData.getAgeEnd();
//		if(! ageEnd.isEmpty()){
//			conditions.add(JQueryConvert.queryConvert(ageEnd)
//					+" >= CAST( ( tokutei.KENSA_NENGAPI - kojin.BIRTHDAY ) / 10000 - 0.5 AS INTEGER) ");
//		}
//
//		/* 健診実施日（開始） */
//		if (!validatedData.getKensaBeginDate().isEmpty()) {
//			conditions.add(" tokutei.KENSA_NENGAPI >= "
//					+ JQueryConvert.queryConvert(validatedData
//							.getKensaBeginDate()));
//		}
//		/* 健診実施日（終了） */
//		if (!validatedData.getKensaEndDate().isEmpty()) {
//			conditions.add(" tokutei.KENSA_NENGAPI <= "
//					+ JQueryConvert.queryConvert(validatedData
//							.getKensaEndDate()));
//		}
//
//		/* 判定日（開始） */
//		if (!validatedData.getHanteiBeginDate().isEmpty()) {
//			conditions.add(" tokutei.HANTEI_NENGAPI >= "
//					+ JQueryConvert.queryConvert(validatedData
//							.getHanteiBeginDate()));
//		}
//		/* 判定日（終了） */
//		if (!validatedData.getHanteiEndDate().isEmpty()) {
//			conditions.add(" tokutei.HANTEI_NENGAPI <= "
//					+ JQueryConvert.queryConvert(validatedData
//							.getHanteiEndDate()));
//		}
//		/* 通知年月日（開始） */
//		if (!validatedData.getTsuuchiBeginDate().isEmpty()) {
//			conditions.add(" tokutei.TUTI_NENGAPI >= "
//					+ JQueryConvert.queryConvert(validatedData
//							.getTsuuchiBeginDate()));
//		}
//		/* 通知年月日（終了） */
//		if (!validatedData.getTsuuchiEndDate().isEmpty()) {
//			conditions.add(" tokutei.TUTI_NENGAPI <= "
//					+ JQueryConvert.queryConvert(validatedData
//							.getTsuuchiEndDate()));
//		}
//
//		// edit s.inoue 2009/10/08
//		// メタボ画面より適用
//		if( jCheckBox_InputDone.isSelected() )
//		{
//			// 再修正 フラグ'2'制限からnullを許可する
//			// 登録した時は、健診実施日が無い為
//			if (jCheckBox_JisiYear.isSelected()) {
//				conditions.add("( GET_NENDO.NENDO is null or GET_NENDO.NENDO = "
//						+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear()))+ ")");
//			}
//		}else{
//			if (jCheckBox_JisiYear.isSelected()) {
//				conditions.add(" ( tokutei.KEKA_INPUT_FLG = '1' or tokutei.KEKA_INPUT_FLG is NULL) and (GET_NENDO.NENDO is null or  GET_NENDO.NENDO = "
//						+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())) + ")");
//			}else{
//				conditions.add(" ( tokutei.KEKA_INPUT_FLG = '1' or tokutei.KEKA_INPUT_FLG is NULL ) ");
//			}
//		}
//
//// edit s.inoue 2009/10/07
////		/* 済・未の条件 */
////
////		// 両方とも選択されていない場合
////		if (!jCheckBox_InputDone.isSelected() && !jCheckBox_InputYet.isSelected()) {
////			// この場合は何も出力されないはず
////			//conditions.add(" tokutei.KEKA_INPUT_FLG = '2' AND tokutei.KEKA_INPUT_FLG = '1' ");
////			// edit ver2 s.inoue 2009/07/06
////			if (jCheckBox_JisiYear.isSelected()) {
////				conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
////			}else{
////				conditions.add("'1' = '2' ");
////			}
////		}
////		// 両方とも選択されている場合
////		else if (jCheckBox_InputDone.isSelected() && jCheckBox_InputYet.isSelected()) {
////			/*
////			 * 特に制限を加える必要がないので、常に真となる条件式を追加
////			 * この部分がないとANDが存在しなくなったりWHERE句の中身が空になったりする可能性があります
////			 */
////
////			// edit ver2 s.inoue 2009/07/22
////			if (jCheckBox_JisiYear.isSelected()) {
////				conditions.add(" ( tokutei.KEKA_INPUT_FLG = '1' or tokutei.KEKA_INPUT_FLG is NULL or GET_NENDO.NENDO = "
////						+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear()))+ ") ");
////			}else{
////				conditions.add("'1' = '1' ");
////			}
////		}
////		// 済のみ選択されている場合
////		else if (jCheckBox_InputDone.isSelected()) {
////			conditions.add(" tokutei.KEKA_INPUT_FLG = '2'");
////			// edit ver2 s.inoue 2009/07/06
////			if (jCheckBox_JisiYear.isSelected()) {
////				conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
////			}
////		}
////		// 未のみ選択されている場合
////		else if (jCheckBox_InputYet.isSelected()) {
////			conditions.add(" ( tokutei.KEKA_INPUT_FLG <> '2' or tokutei.KEKA_INPUT_FLG is NULL ) ");
////			// edit ver2 s.inoue 2009/07/06
////			if (jCheckBox_JisiYear.isSelected()) {
////				conditions.add(" GET_NENDO.NENDO = "	+ JQueryConvert.queryConvert(String.valueOf(FiscalYearUtil.getThisYear())));
////			}
////		}
//
//		String retValue = "";
//		if (! conditions.isEmpty()) {
//			StringBuffer buffer = new StringBuffer();
//			buffer.append(" where ");
//
//			for (Iterator<String> iter = conditions.iterator(); iter.hasNext();) {
//				String condition = iter.next();
//				buffer.append(condition);
//
//				if (iter.hasNext()) {
//					buffer.append(" AND ");
//				}
//			}
//
//			retValue = buffer.toString();
//		}
//
//		return retValue;
//	}
//
//	/**
//	 * 結果データ入力ボタン
//	 */
//	public void showKekkaRegisterFrame(boolean isCopy) {
//		// edit s.inoue 2010/04/27
////		ArrayList<Integer> selectedRow = new ArrayList<Integer>();
////
////		// 現在チェックされている行のリストを抽出
////		for (int i = 0; i < table.getRowCount(); i++) {
////			if (table.getData(i, 0) == Boolean.TRUE) {
////				selectedRow.add(i);
////			}
////		}
//		setSelectedRows();
//
//		// 一つ選択されている場合のみ画面遷移を行う
//		if (selectedRows.size() == 1) {
//			resultItem = result.get(selectedRows.get(0));
//
//			// edit ver2 s.inoue 2009/06/01
//			if (isCopy){
//				/* T_KOJIN Dao を作成する。 */
//				try {
//					tKojinDao = (TKojinDao) DaoFactory.createDao(
//							JApplication.kikanDatabase.getMConnection(), new TKojin());
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
//
//				// 複製コピー時、受付ID採番
//				long uketukeId = -1L;
//				try {
//					uketukeId = tKojinDao.selectNewUketukeId();
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
//
//				// add ver2 s.inoue 2009/06/01
//				validatedData.setUketukePre_id(resultItem.get("UKETUKE_ID"));
//				validatedData.setUketuke_id(new Long(uketukeId).toString());
//
//			}else{
//				validatedData.setUketuke_id(resultItem.get("UKETUKE_ID"));
//			}
//
//			validatedData.setHihokenjyaCode(resultItem.get("HIHOKENJYASYO_KIGOU"));
//			validatedData.setHihokenjyaNumber(resultItem.get("HIHOKENJYASYO_NO"));
//			validatedData.setName(resultItem.get("KANANAME"));
//			validatedData.setHokenjyaNumber(resultItem.get("HKNJANUM"));
//			validatedData.setSex(resultItem.get("SEX"));
//			validatedData.setBirthDay(resultItem.get("BIRTHDAY"));
//
//			String kensaJissiDate = resultItem.get("KENSA_NENGAPI");
//
//			if (kensaJissiDate == null) {
//				kensaJissiDate = new String("");
//			}
//
//			JScene.CreateDialog(
//					this,
//					new JKekkaRegisterFrameCtrl(validatedData, kensaJissiDate, isCopy),
//					new WindowRefreshEvent());
//		} else {
//			JErrorMessage.show(
//					"M3520", this);
//		}
//	}
//
//	/**
//	 * 印刷機能
//	 *
//	 * 1ページ 健診項目入力シート（検査結果） 必須データ：保険者番号、被保険者証等記号、被保険者証等番号、検査年月日 import
//	 * Print.KenshinKoumoku_Kensa class KenshinKoumoku_Kensa
//	 *
//	 * 2ページ 健診項目入力シート（問診） 必須データ：保険者番号、被保険者証等記号、被保険者証等番号、健診年月日 import
//	 * Print.KenshinKoumoku_Monshin class KenshinKoumoku_Monshin
//	 */
//	private void printInputData(String kenshinDate){
//		/*
//		 * 選択行を抽出する。
//		 */
//		int rowCount = table.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) table.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
//
//		int selectedCount = selectedRows.size();
//		if (selectedCount == 0) {
//
//			ProgressWindow progressWindow = new ProgressWindow(this, false, true);
//			progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
//			progressWindow.setVisible(true);
//
//			Kojin KojinData = new Kojin();
//			Hashtable<String, Object> printData = new Hashtable<String, Object>();
//			printData.put("Kojin", KojinData);
//
//			PrintNyuryoku kensa = new PrintNyuryoku();
//
//			kensa.setQueryResult(printData);
//			// edit s.inoue 2009/10/16
//			kensa.setKenshinDate(kenshinDate);
//
//			progressWindow.setVisible(false);
//
//			try {
//				kensa.beginPrint();
//
//			} catch (PrinterException err) {
//				err.printStackTrace();
//				JErrorMessage
//						.show("M3531", this);
//			} finally {
//				progressWindow.setVisible(false);
//			}
//
//			return;
//		}
//
//		for (int j = 0; j < selectedCount; j++) {
//			int k = selectedRows.get(j);
//
//			/*
//			 * 個人情報データ作成
//			 */
//			ProgressWindow progressWindow = new ProgressWindow(this, false,true);
//			progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
//			progressWindow.setVisible(true);
//
//			try {
//			validatedData.setUketuke_id(result.get(k).get("UKETUKE_ID"));
//			validatedData.setHihokenjyaCode(result.get(k).get("HIHOKENJYASYO_KIGOU"));
//			validatedData.setHihokenjyaNumber(result.get(k).get("HIHOKENJYASYO_NO"));
//
//			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
//			tmpKojin.put("UKETUKE_ID", validatedData.getUketuke_id());
//
//			// 被保険者証等記号
//			tmpKojin.put("HIHOKENJYASYO_KIGOU", validatedData.getHihokenjyaCode());
//			// 被保険者証等番号
//			tmpKojin.put("HIHOKENJYASYO_NO", validatedData.getHihokenjyaNumber());
//
//			// 健診年月日
//			// edit ver2 s.inoue 2009/05/29
//			//tmpKojin.put("KENSA_NENGAPI", (String) model.getData(k, 5));
//			// edit ver2 s.inoue 2009/07/14
//			// tmpKojin.put("KENSA_NENGAPI", kensaNengapi);
//			if (!kenshinDate.equals("")) {
//				tmpKojin.put("KENSA_NENGAPI", kenshinDate);
//			}else{
//				tmpKojin.put("KENSA_NENGAPI", result.get(k).get("KENSA_NENGAPI"));
//			}
//
//			Kojin KojinData = new Kojin();
//			if (!KojinData.setPrintKojinDataSQL(tmpKojin)) {
//				// データ移送失敗
//				JErrorMessage.show("M3530", this);
//			}
//
//			// add s.inoue 2009/10/15
//			// 結果登録済みで無い場合終了　未登録でも出力
////			if (KojinData.getPrintKojinData().size() < 1){
////				progressWindow.setVisible(false);
////				JErrorMessage.show("M3549", this);
////				return;
////			}
//
//			// add s.inoue 2009/10/15　同上
////			String kekaInputFlg = KojinData.getPrintKojinData().get("KEKA_INPUT_FLG");
////			if (!kekaInputFlg.equals("2")){
////				return;
////			}
//
//			/*
//			 * 印刷データ生成 個人データを格納する
//			 */
//			Hashtable<String, Object> printData = new Hashtable<String, Object>();
//			printData.put("Kojin", KojinData);
//
//			/*
//			 * 印刷 1ページ目を印刷すると、自動的に最終ページまで出力される
//			 */
//			PrintNyuryoku kensa = new PrintNyuryoku();
//			kensa.setQueryResult(printData);
//			// edit s.inoue 2009/10/16
//			// kensa.setKenshinDate(kenshinDate);
//			progressWindow.setVisible(false);
//
//
//				kensa.beginPrint();
//			} catch (PrinterException err) {
//				err.printStackTrace();
//				JErrorMessage.show("M3531", this);
//			} finally {
//				progressWindow.setVisible(false);
//			}
//		}
//	}
//// edit s.inoue 2009/10/04
////	private void calendarDialog(){
////		final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
////        final Display display = new Display();
////
////        final Shell shell = new Shell(display);
////        final SWTCalendarDialog cal = new SWTCalendarDialog(display);
////
////        FillLayout layout = new FillLayout();
////        final Text t = new Text(shell, SWT.BORDER);
////        final Button b = new Button(shell, SWT.PUSH);
////        final Button bb = new Button(shell, SWT.PUSH);
////
////        shell.setLayout(layout);
////
////        b.setText("カレンダー");
////
////        b.addListener(SWT.Selection, new Listener() {
////            public void handleEvent(Event event) {
////            	// delete ver2 s.inoue 2009/07/14
////                // final SWTCalendarDialog cal = new SWTCalendarDialog(display);
////                cal.addDateChangedListener(new SWTCalendarListener() {
////
////                    public void dateChanged(SWTCalendarEvent calendarEvent) {
////                    	if (cal.getselectionControl()){
////                        	t.setText(formatter.format(calendarEvent.getCalendar().getTime()));
////                        	cal.close();
////                        }
////                    }
////                });
////                if (t.getText() != null && t.getText().length() > 0) {
////                try {
////                        Date d = formatter.parse(t.getText());
////                        cal.setDate(d);
////                    } catch (ParseException pe) {
////                    }
////                }
////                cal.open();
////            }
////        });
////
////        // 閉じる
////        bb.addListener(SWT.Selection, new Listener() {
////            public void handleEvent(Event event) {
////            	System.out.println("CloseCheck called");
////            	setcalDate(String.valueOf(t.getText()));
////        		MessageBox box =
////        			new MessageBox(shell,SWT.APPLICATION_MODAL | SWT.ICON_QUESTION | SWT.YES | SWT.NO);
////
////        		// ボタンが押されたことを示すフラグをセット。
////				bb.setData(new Boolean(true));
////
////        		box.setMessage("本当に終了する？");
////        		box.setText("Application の終了");
////        		int answer = box.open();
////        		if (answer == SWT.NO) {
////        			event.doit = false;
////        		}else{
////        			event.doit = true;
////        		}
////        		shell.dispose();
////        		printInputData(getcalDate());
////            }
////        });
////
////        shell.addShellListener(new ShellAdapter() {
////			/*
////			 * ウィンドウが現れた時に発生。
////			 * (プログラム開始時、最小化解除時、
////			 * 他のアプリケーションからフォーカスが移った時。)
////			 */
////			public void shellActivated(ShellEvent e) {
////				System.out.println("activated");
////			}
////			/*
////			 * ウィンドウが隠れた時に発生。
////			 * (最小化時、他のアプリケーションからフォーカスが移った時。)
////			 * shell.dispose()では呼び出されない。
////			 */
////			public void shellDeactivated(ShellEvent e) {
////				System.out.println("deactivated");
////			}
////			/*
////			 * ウィンドウ最小化時に発生。
////			 */
////			public void shellIconified(ShellEvent e) {
////				System.out.println("iconified");
////			}
////			/*
////			 * ウィンドウ最小化解除時に発生。
////			 */
////			public void shellDeiconified(ShellEvent e) {
////				System.out.println("deiconified");
////			}
////			/*
////			 * shell.disposed()が呼ばれた時、
////			 * ウィンドウの閉じるボタンクリック時、
////			 * Alt+F4押下時に発生。
////			 */
////			public void shellClosed(ShellEvent e) {
////				System.out.println("closed");
////				// ボタンが押されたことを示すフラグをチェック。
////				if (new Boolean(true).equals(bb.getData())) {
////					e.doit = true;
////				} else {
////					e.doit = false;
////				}
////			}
////		});
////
////        shell.open();
////        shell.pack();
////        shell.setBounds(500, 500, 200, 30);
////
////        while (!shell.isDisposed()) {
////            if (!display.readAndDispatch()) display.sleep();
////        }
////
////        display.dispose();
////	}
//
////	private String calDate ="";
////	private String getcalDate(){
////
////		return this.calDate;
////	}
////	private void setcalDate(String calDate){
////
////		this.calDate = calDate;
////	}
//
//	public void pushedPrintButton(ActionEvent e) {
//
//		// カレンダは中止で普通のダイアログを表示
//		// calendarDialog();
//
//		try {
//			// edit s.inoue 2009/10/14
//			int rowCount = table.getRowCount();
//			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//			ArrayList<Hashtable<String, String>> selectResult = new ArrayList<Hashtable<String,String>>();
//
//			for (int i = 0; i < rowCount; i++) {
//				// 現在チェックされている行のリストを抽出
//				if ((Boolean) table.getData(i, 0) == Boolean.TRUE) {
//					selectedRows.add(i);
//					selectResult.add(result.get(i));
//				}
//			}
//
//			dateSelectDialog = DialogFactory.getInstance().createDialog(this, new JButton(),selectResult);
//			dateSelectDialog.setShowCancelButton(false);
//
//			// 健診実施日入力ダイアログを表示
//			dateSelectDialog.setMessageTitle("健診実施日入力画面");
//			dateSelectDialog.setVisible(true);
//
//			String kenshinDate = dateSelectDialog.getKenshinDate();
//			// 印刷処理
//			printInputData(kenshinDate);
//			// del s.inoue 2009/10/16
//			// リフレッシュ 更新が必要ないので、必要なし
//			// pushedSearchButton(true);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//		}
//
//	}
//
//	/**
//	 * 印刷機能
//	 * ページ単位 検査依頼書
//	 */
//	public void pushedPrintIraiButton(ActionEvent e) {
//
//		// 選択行を抽出する。
//		int rowCount = table.getRowCount();
//		// 印刷用
//		TreeMap<String, Object> printDataIrai = new TreeMap<String, Object>();
//		// ステータス画面
//		ProgressWindow progressWindow = new ProgressWindow(this, false,true);
//
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		// edit s.inoue 2009/11/02
//		ArrayList<String> kyeList =  new ArrayList<String>();
//
//		// 選択チェック
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				// ｋ番号「空」,結果入力「未」の場合エラー
//				if (table.getData(i, inputflgColumn).equals("未")){
//					JErrorMessage.show("M3547", this);return;
//				}
//					selectedRows.add(i);
//			}
//		}
//
//		// 選択行なし
//		int selectedCount = selectedRows.size();
//		if (selectedCount == 0) {
//			JErrorMessage.show("M3548", this);return;
//		}
//
//		// 依頼書データ作成
//		for (int j = 0; j < selectedCount; j++) {
//
//			int k = selectedRows.get(j);
//
//			progressWindow.setGuidanceByKey("tokutei.iraisho.frame.progress.guidance");
//			progressWindow.setVisible(true);
//
//			validatedData.setUketuke_id(result.get(k).get("UKETUKE_ID"));
//
//			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
//
//			// 受付ID
//			String uketukeId = validatedData.getUketuke_id();
//			tmpKojin.put("UKETUKE_ID", uketukeId);
//			// 健診年月日
//			// edit ver2 s.inoue 2009/05/29
//			//String kensaNengapi = (String) model.getData(k, 5);
//			String kensaNengapi = result.get(k).get("KENSA_NENGAPI");
//
//			tmpKojin.put("KENSA_NENGAPI",kensaNengapi );
//
//			Iraisho IraiData = new Iraisho();
//
//			if (!IraiData.setPrintKojinIraiDataSQL(tmpKojin)) {
//				// データ移送失敗
//				// edit s.inoue 2009/10/16
//				progressWindow.setVisible(false);
//				JErrorMessage.show("M3530", this);
//				return;
//			}
//
//			// 印刷obj key:検査年月日+受付ID value:印刷データ
//			printDataIrai.put(String.valueOf(kensaNengapi + uketukeId), IraiData);
//			kyeList.add(String.valueOf(kensaNengapi + uketukeId));
//			System.out.println(String.valueOf(kensaNengapi + uketukeId));
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
//		Hashtable<String, Object> printData = new Hashtable<String, Object>();
//		printData.put("Kikan", kikanData);
//
//		// 印刷実行
//		PrintIraisho kensa = new PrintIraisho();
//		kensa.setQueryIraiResult(printDataIrai);
//		kensa.setQueryResult(printData);
//		kensa.setKeys(kyeList);
//
//		progressWindow.setVisible(false);
//
//		try {
//			kensa.beginPrint();
//		} catch (PrinterException err) {
//			err.printStackTrace();
//			JErrorMessage.show("M3531", this);
//		} finally {
//			progressWindow.setVisible(false);
//		}
//
//	}
//
//	/**
//	 * 受診券情報ボタン
//	 */
//	public void pushedKojinButton(ActionEvent e) {
//		// edit s.inoue 2010/04/27
////		selectedRows =  new ArrayList<Integer>();
////
////		// 現在チェックされている行のリストを抽出
////		for (int i = 0; i < this.modelfixed.getRowCount(); i++) {
////			if (this.modelfixed.getData(i, 0) == Boolean.TRUE) {
////				selectedRows.add(i);
////			}
////		}
//		setSelectedRows();
//
//		// 一つ選択されている場合のみ画面遷移を行う
//		if (selectedRows.size() == 1) {
//			resultItem = result.get(selectedRows.get(0));
//			this.validatedData.setUketuke_id(resultItem.get("UKETUKE_ID"));
//			this.validatedData.setHihokenjyaCode(resultItem.get("HIHOKENJYASYO_KIGOU"));
//			this.validatedData.setHihokenjyaNumber(resultItem.get("HIHOKENJYASYO_NO"));
//			this.validatedData.setName(resultItem.get("KANANAME"));
//			this.validatedData.setHokenjyaNumber(resultItem.get("HKNJANUM"));
//			String kensaJissiDate = resultItem.get("KENSA_NENGAPI");
//
//			if (kensaJissiDate == null) {
//				kensaJissiDate = new String("");
//			}
//
//			JScene.CreateDialog(this, new JKojinRegisterFrameCtrl(
//					this.validatedData), new WindowRefreshEvent());
//		} else {
//			JErrorMessage.show("M3520", this);
//		}
//
//	}
//
//	/**
//	 * 受診券情報追加ボタン
//	 */
//	public void pushedKojinAddButton(ActionEvent e) {
//		JScene.CreateDialog(this, new JKojinRegisterFrameCtrl(),
//				new WindowRefreshEvent());
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		Object source = e.getSource();
//		if (source == jButton_End) {
//			logger.info(jButton_End.getText());
//			pushedEndButton(e);
//		/* 検索ボタン */
//		} else if (source == jButton_Search) {
//			logger.info(jButton_Search.getText());
//			pushedSearchButton(false);
//		/* 結果データ入力ボタン */
//		} else if (source == jButton_OK) {
//			logger.info(jButton_OK.getText());
//			showKekkaRegisterFrame(false);
//
//		/* 結果データ複製ボタン */
//		} else if (source == jButton_Copy) {
//			logger.info(jButton_Copy.getText());
//			showKekkaRegisterFrame(true);
//
//		/* 入力票印刷ボタン */
//		} else if (source == jButton_Print) {
//			logger.info(jButton_Print.getText());
//			pushedPrintButton(e);
//		}
//
//		else if (source == jButton_Kojin) {
//			logger.info(jButton_Kojin.getText());
//			pushedKojinButton(e);
//		}
//		else if (source == jButton_KojinAdd) {
//			logger.info(jButton_KojinAdd.getText());
//			pushedKojinAddButton(e);
//		}
//
//		/* 依頼書ボタン */
//		else if (source == jButton_Irai) {
//			logger.info(jButton_Irai.getText());
//			pushedPrintIraiButton(e);
//		}
//
//		/* 健診データ削除 */
//		else if (source == jButton_DeleteKekka) {
//			logger.info(jButton_DeleteKekka.getText());
//			pushedDeleteKekkaButton();
//		}
//		/* 受診券削除 */
//		else if (source == jButton_DeleteKojin) {
//			logger.info(jButton_DeleteKojin.getText());
//			pushedDeleteKojinButton();
//		}
//		else if (source == jButton_AllSelect) {
//			logger.info(jButton_AllSelect.getText());
//			pushedAllSelectButton(e);
//		}
//	}
//
//	/**
//	 * 遷移先の画面から戻ってきた場合にテーブルの再描画を行う
//	 */
//	public class WindowRefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			//テーブルの再読み込みを行う
////			model.clearAllRow();
//			pushedSearchButton(false);
//			// edit s.inoue 2010/04/27
//			if (selectedRows != null)
//				modelfixed.setselectedRows(modelfixed,selectedRows);
//		}
//	}
//
//	// add s.inoue 2010/04/27
//	private void setSelectedRows(){
//		selectedRows =  new ArrayList<Integer>();
//
//		// 現在チェックされている行のリストを抽出
//		for (int i = 0; i < this.modelfixed.getRowCount(); i++) {
//			if (this.modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
//	}
//	/**
//	 * keyTyped イベントハンドラ
//	 */
//	public void keyTyped(java.awt.event.KeyEvent e) {
//
//		Object source = e.getSource();
//		char keyChar = e.getKeyChar();
//
//		/* 男性ラジオボタン */
//		if (source == this.jRadioButton_Male) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Male.setSelected(true);
//			}
//		/* 女性ラジオボタン */
//		} else if (source == this.jRadioButton_Female) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Female.setSelected(true);
//			}
//		/* 性別入力欄 */
//		} else if (source == jTextField_sex) {
//
//			if (keyChar == KeyEvent.VK_1) {
//				this.jTextField_sex.setText("1");
//				this.jRadioButton_Male.setSelected(true);
//			} else if (keyChar == KeyEvent.VK_2) {
//				this.jTextField_sex.setText("2");
//				this.jRadioButton_Female.setSelected(true);
//			} else {
//				String currentValue = this.jTextField_sex.getText();
//				if (!currentValue.equals("1") && !currentValue.equals("2")) {
//					this.groupSex.clearSelection();
//				}
//			}
//		}
//	}
//
//	public void itemStateChanged(ItemEvent e) {
//
//		Object source = e.getSource();
//		if (source == jRadioButton_Male) {
//			changedSexState(e, true);
//		}
//
//		else if (source == jRadioButton_Female) {
//			changedSexState(e, false);
//		}
//
//		// add h.yoshitama 2009/11/11
//		if ((e.getSource()) == jCheckBox_InputDone) {
//			if( !jCheckBox_InputDone.isSelected() )
//			{
//				/* 健診実施日 */
//				jTextField_KensaBeginDate.setEditable(false);
//				jTextField_KensaBeginDate.setEnabled(false);
//				jTextField_KensaBeginDate.setText("");
//				jTextField_KensaEndDate.setEditable(false);
//				jTextField_KensaEndDate.setEnabled(false);
//				jTextField_KensaEndDate.setText("");
//				/* 判定日 */
//				jTextField_HanteiBeginDate.setEditable(false);
//				jTextField_HanteiBeginDate.setEnabled(false);
//				jTextField_HanteiBeginDate.setText("");
//				jTextField_HanteiEndDate.setEditable(false);
//				jTextField_HanteiEndDate.setEnabled(false);
//				jTextField_HanteiEndDate.setText("");
//				/* 結果通知日 */
//				jTextField_TsuuchiBeginDate.setEditable(false);
//				jTextField_TsuuchiBeginDate.setEnabled(false);
//				jTextField_TsuuchiBeginDate.setText("");
//				jTextField_TsuuchiEndDate.setEditable(false);
//				jTextField_TsuuchiEndDate.setEnabled(false);
//				jTextField_TsuuchiEndDate.setText("");
//			} else {
//				/* 健診実施日 */
//				jTextField_KensaBeginDate.setEditable(true);
//				jTextField_KensaBeginDate.setEnabled(true);
//				jTextField_KensaEndDate.setEditable(true);
//				jTextField_KensaEndDate.setEnabled(true);
//				/* 判定日 */
//				jTextField_HanteiBeginDate.setEditable(true);
//				jTextField_HanteiBeginDate.setEnabled(true);
//				jTextField_HanteiEndDate.setEditable(true);
//				jTextField_HanteiEndDate.setEnabled(true);
//				/* 結果通知日 */
//				jTextField_TsuuchiBeginDate.setEditable(true);
//				jTextField_TsuuchiBeginDate.setEnabled(true);
//				jTextField_TsuuchiEndDate.setEditable(true);
//				jTextField_TsuuchiEndDate.setEnabled(true);
//
//			}
//		}
//	}
//
//	/**
//	 * 男性または女性のラジオボタンが押された場合
//	 */
//	public void changedSexState(ItemEvent e, boolean isMale) {
//		String text = "";
//
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			if (isMale) {
//				text = "1";
//			}
//			else {
//				text = "2";
//			}
//		}
//
//		jTextField_sex.setText(text);
//	}
//
//	/**
//	 * 健診データ削除ボタン
//	 */
//	public void pushedDeleteKekkaButton() {
//		this.deleteKekka();
//	}
//
//	/**
//	 * 受診券データ削除ボタン
//	 */
//	public void pushedDeleteKojinButton() {
//		this.deleteKojin();
//	}
//
//	// add s.inoue 2009/12/01
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		int mod = keyEvent.getModifiersEx();
//
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			dispose();break;
//		case KeyEvent.VK_F3:
//			logger.info(jButton_DeleteKekka.getText());
//			pushedDeleteKekkaButton();break;
//		case KeyEvent.VK_F4:
//			logger.info(jButton_DeleteKojin.getText());
//			pushedDeleteKojinButton();break;
//		case KeyEvent.VK_F5:
//			logger.info(jButton_Print.getText());
//			pushedPrintButton(null);break;
//		case KeyEvent.VK_F6:
//			logger.info(jButton_KojinAdd.getText());
//			pushedKojinAddButton(null);break;
//		case KeyEvent.VK_F7:
//			logger.info(jButton_Kojin.getText());
//			pushedKojinButton(null);break;
//		case KeyEvent.VK_F9:
//			logger.info(jButton_Irai.getText());
//			pushedPrintIraiButton(null);break;
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Copy.getText());
//			showKekkaRegisterFrame(true);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Copy.getText());
//			showKekkaRegisterFrame(false);break;
////		case KeyEvent.VK_S:
////			if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
////				logger.info(jButton_Search.getText());
////				pushedSearchButton(false);
////			}
////			break;
//		// del s.inoue 2009/12/21
//		// case KeyEvent.VK_S:
//		//	pushedSearchButton(false);break;
//		}
//	}
//
//	/**
//	 * 全て選択ボタンが押された場合 7
//	 */
//	public void pushedAllSelectButton( ActionEvent e )
//	{
//		for(int i = 0;i < modelfixed.getRowCount();i++ )
//		{
//			if( isAllSelect )
//				modelfixed.setData(true, i, 0);
//			else
//				modelfixed.setData(false, i, 0);
//		}
//
//		if( isAllSelect )
//		{
//			isAllSelect = false;
//		}else{
//			isAllSelect = true;
//		}
//	}
//
//	/**
//	 * 健診結果を削除する。
//	 */
//	private void deleteKekka() {
//		/*
//		 * 選択行を抽出する。
//		 */
//		int rowCount = modelfixed.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
//
//		/* 結果データを削除したか */
//		boolean deletedData = false;
//
//		/* 選択された行数 */
//		int selectedCount = selectedRows.size();
//
//		for (int j = 0; j < selectedCount; j++) {
//			/* 選択された行の行インデックス */
//			int selectedRowIndex = selectedRows.get(j);
//
//			Hashtable<String, String> resultItem = result.get(selectedRowIndex);
//
//			String uketukeId = resultItem.get("UKETUKE_ID");
//			String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
//			String hihokenjasyoNo = resultItem.get("HIHOKENJYASYO_NO");
//			String namekana = resultItem.get("KANANAME");
//			// String hokenjyaNumber = resultItem.get("HKNJANUM");
//			// String sex = resultItem.get("SEX");
//			// String birthday = resultItem.get("BIRTHDAY");
//			String kesaNagapi = resultItem.get("KENSA_NENGAPI");
//
//			String[] params = { uketukeId, kesaNagapi };
//			// String[] messageParams = { namekana, kesaNagapi };
//
//			String displayKigou = null;
//			if (hihokenjyasyoKigou == null || hihokenjyasyoKigou.isEmpty()) {
//				displayKigou = "(無し)";
//			}
//			else {
//				displayKigou = hihokenjyasyoKigou;
//			}
//
//			String displayNumber = null;
//			if (hihokenjasyoNo == null || hihokenjasyoNo.isEmpty()) {
//				displayNumber = "(無し)";
//			}
//			else {
//				displayNumber = hihokenjasyoNo;
//			}
//
//			String[] messageParams = { namekana, displayKigou, displayNumber, kesaNagapi };
//
//
//			/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）には健診データがありません */
//			if (kesaNagapi == null || kesaNagapi.isEmpty()) {
//				JErrorMessage.show("M3537", this, messageParams);
//				continue;
//			}
//
//			/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）（%s）の健診データを削除してもよろしいですか？ */
//			RETURN_VALUE retValue = JErrorMessage.show("M3542", this, messageParams);
//			if (retValue != RETURN_VALUE.YES) {
//				continue;
//			}
//
//			int count = 0;
//			boolean successDeleteKekka = false;
//			try {
//				JApplication.kikanDatabase.Transaction();
//
//				count = JApplication.kikanDatabase.sendNoResultQuery(
//						SQL_DELETE_SONOTA, params);
//
//				count = JApplication.kikanDatabase.sendNoResultQuery(
//						SQL_DELETE_TOKUTEI, params);
//
//				if (count == 1) {
//					successDeleteKekka = true;
//
//					if (! deletedData) {
//						deletedData = true;
//					}
//
//					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）（%s）の健診データを削除しました。 */
//					JErrorMessage.show("M3535", this, messageParams);
//				}
//
//				JApplication.kikanDatabase.Commit();
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			finally {
//				if (! successDeleteKekka) {
//					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）（%s）の健診データの削除に失敗しました。 */
//					JErrorMessage.show("M3536", this, messageParams);
//					break;
//				}
//			}
//		}
//
//		/* 1 件以上の結果または受診券情報を削除していた場合 */
//		if (deletedData) {
//			/* テーブルの再読み込みを行なう */
////			model.clearAllRow();
//			pushedSearchButton(false);
//		}
//	}
//
//	/**
//	 * 受診券情報を削除する。
//	 */
//	private void deleteKojin() {
//		/*
//		 * 選択行を抽出する。
//		 */
//		int rowCount = modelfixed.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
//
//		/* 選択された行数 */
//		int selectedCount = selectedRows.size();
//
//		/* 処理済みの受診券情報の受付ID */
//		ArrayList<String> chekedKojinIds = new ArrayList<String>();
//
//		/* データを削除したか */
//		boolean deletedData = false;
//
//		for (int j = 0; j < selectedCount; j++) {
//			/* 選択された行の行インデックス */
//			int selectedRowIndex = selectedRows.get(j);
//
//			Hashtable<String, String> resultItem = result.get(selectedRowIndex);
//
//			String uketukeId = resultItem.get("UKETUKE_ID");
//			String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
//			String hihokenjasyoNo = resultItem.get("HIHOKENJYASYO_NO");
//			String namekana = resultItem.get("KANANAME");
//			// String hokenjyaNumber = resultItem.get("HKNJANUM");
//			// String sex = resultItem.get("SEX");
//			// String birthday = resultItem.get("BIRTHDAY");
//			String kesaNagapi = resultItem.get("KENSA_NENGAPI");
//
//			String[] paramsDeleteKojin = { uketukeId };
//			String[] paramsDeleteKekka = { uketukeId, kesaNagapi };
//
//			String displayKigou = null;
//			if (hihokenjyasyoKigou == null || hihokenjyasyoKigou.isEmpty()) {
//				displayKigou = "(無し)";
//			}
//			else {
//				displayKigou = hihokenjyasyoKigou;
//			}
//
//			String displayNumber = null;
//			if (hihokenjasyoNo == null || hihokenjasyoNo.isEmpty()) {
//				displayNumber = "(無し)";
//			}
//			else {
//				displayNumber = hihokenjasyoNo;
//			}
//
//			String[] messageParams = { namekana, displayKigou, displayNumber, "" };
//
//			/* 処理済みの受付IDの場合は、何もしない */
//			if (chekedKojinIds.contains(uketukeId)) {
//				continue;
//			}
//
//			/* 受診者に健診データがあるか調べる。 */
//			ArrayList<Hashtable<String, String>> selectTokuteiResult =
//				new ArrayList<Hashtable<String, String>>();
//			try {
//				selectTokuteiResult = JApplication.kikanDatabase.sendExecuteQuery(
//						SQL_SELECT_TOKUTEI, paramsDeleteKojin);
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//
//				/* 氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）の受診データの有無の取得に失敗しました。 */
//				JErrorMessage.show("M3538", this, messageParams);
//
//				return;
//			}
//
//			Hashtable<String, String> item = selectTokuteiResult.get(0);
//			String kekkaCount = item.get("NUM");
//
//			boolean needDeleteKekkaData = false;
//			try {
//				if (kekkaCount.equals("0")) {
//					/* 結果データが存在しない場合 */
//
//					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）受診券データを削除してよろしいですか？ */
//					RETURN_VALUE retValue = JErrorMessage.show("M3534", this, messageParams);
//					if (retValue != RETURN_VALUE.YES) {
//						continue;
//					}
//				}
//				else {
//					/* 結果データが存在する場合 */
//
//					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）には、
//					 * 健診結果データが[%s]件あります。受診券データを削除してよろしいですか？ */
//					messageParams[3] = kekkaCount;
//
//					RETURN_VALUE retValue = JErrorMessage.show("M3539", this, messageParams);
//					if (retValue == RETURN_VALUE.YES) {
//						needDeleteKekkaData = true;
//					}
//					else {
//						continue;
//					}
//				}
//			} finally {
//				/* 処理済みとして登録する。 */
//				chekedKojinIds.add(uketukeId);
//			}
//
//			/*
//			 * 結果データを削除する。
//			 */
//			if (needDeleteKekkaData) {
//				int count = 0;
//				boolean successDeleteKekka = false;
//				try {
//					JApplication.kikanDatabase.Transaction();
//
//					count = JApplication.kikanDatabase.sendNoResultQuery(
//							SQL_DELETE_SONOTA, paramsDeleteKekka);
//
//					count = JApplication.kikanDatabase.sendNoResultQuery(
//							SQL_DELETE_TOKUTEI, paramsDeleteKekka);
//
//					if (count == 1) {
//						successDeleteKekka = true;
//
//						if (! deletedData) {
//							deletedData = true;
//						}
//					}
//
//					JApplication.kikanDatabase.Commit();
//
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//					try {
//						JApplication.kikanDatabase.rollback();
//					} catch (SQLException ex) {
//						logger.error(ex.getMessage());
//					}
//				}
//				finally {
//					if (! successDeleteKekka) {
//						/* 確認,氏名（カナ）[%s]さん（%s）の健診データの削除に失敗しました。 */
//						JErrorMessage.show("M3536", this, messageParams);
//						return;
//					}
//				}
//			}
//
//			/*
//			 * 受診券データを削除する。
//			 */
//			boolean successDeleteData = false;
//			int count = 0;
//
//			try {
//
//				// add ver2 s.inoue 2009/06/02
//				JApplication.kikanDatabase.Transaction();
//
//				count = JApplication.kikanDatabase.sendNoResultQuery(
//						SQL_DELETE_NAYOSE, paramsDeleteKojin);
//
//				count = JApplication.kikanDatabase.sendNoResultQuery(
//						SQL_DELETE_KOJIN, paramsDeleteKojin);
//
//				if (count == 1) {
//					successDeleteData = true;
//					if (! deletedData) {
//						deletedData = true;
//					}
//
//					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）の受診券データを削除しました。 */
//					JErrorMessage.show("M3541", this, messageParams);
//				}
//
//				// add ver2 s.inoue 2009/06/02
//				JApplication.kikanDatabase.Commit();
//
//			} catch (SQLException e) {
//				logger.error(e.getMessage());
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException ex) {
//					logger.error(ex.getMessage());
//				}
//			}
//			finally {
//				if (! successDeleteData) {
//					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）の受診券データの削除に失敗しました。 */
//					JErrorMessage.show("M3540", this, messageParams);
//					return;
//				}
//			}
//		}
//
//		/* 1 件以上の結果または受診券情報を削除していた場合 */
//		if (deletedData) {
//			/* テーブルの再読み込みを行なう */
////			model.clearAllRow();
//			pushedSearchButton(false);
//		}
//	}
//}
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}