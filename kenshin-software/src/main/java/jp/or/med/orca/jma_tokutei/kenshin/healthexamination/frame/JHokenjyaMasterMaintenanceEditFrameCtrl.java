//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.event.*;
//import java.lang.reflect.InvocationTargetException;
//import java.sql.SQLException;
//import java.text.NumberFormat;
//import java.util.*;
//
//import javax.swing.ButtonGroup;
//import javax.swing.JCheckBox;
//import javax.swing.JRadioButton;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JViewport;
//import javax.swing.ListSelectionModel;
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
//import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
//import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
//import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
//import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//
///**
// * 保険者情報の編集のコントロール
// */
//public class JHokenjyaMasterMaintenanceEditFrameCtrl extends
//		JHokenjyaMasterMaintenanceEditFrame {
//
//	private JHokenjyaMasterMaintenanceEditFrameData validatedData = new JHokenjyaMasterMaintenanceEditFrameData();  //  @jve:decl-index=0:
//	private static org.apache.log4j.Logger logger =	Logger.getLogger(JHokenjyaMasterMaintenanceEditFrameCtrl.class);
//	private int mode;
//	private static final int ADD_MODE = 1;
//	private static final int CHANGE_MODE = 2;
//	private static final int ADD_MODE_TANKA = 3;
//	private static final int CHANGE_MODE_TANKA = 4;
//
//	// add s.inoue 2010/01/12
//	private int modeYukouKigen = 0;
//
//	// add s.inoue 2010/01/20
//	private boolean initializeTanka = false;
//
//	// edit s.inoue 2009/10/01
//	private String[] itakuKubunStr = { "1:個別", "2:集団" };
//	private JSimpleTable model = new JSimpleTable();
//	private DefaultTableModel dmodel = null;
//	private TableRowSorter<TableModel> sorter =null;
//	private ArrayList<Hashtable<String, String>> result;
//
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	// edit s.inoue 2010/01/29
//	private String[] header = {
//		"有効/無効","有効期限開始","有効期限終了","履歴番号","委託単価区分","基本的な健診","貧血検査","心電図検査","眼底検査","人間ドック" };
//	// 検索ボタン押下時のSQLで使用
//	private static final String[] TABLE_COLUMNS = {
//		"YUKOU_FLG","HKNJYA_LIMITDATE_START","HKNJYA_LIMITDATE_END","HKNJYA_HISTORY_NO","ITAKU_KBN",
//		"TANKA_KIHON","TANKA_HINKETU","TANKA_SINDENZU","TANKA_GANTEI","TANKA_NINGENDOC" };
//	Object[][] rowcolumn = null;
//
//	private ExtendedCheckBox JRadioYukou = new ExtendedCheckBox();
//	private THokenjyaDao tHokenjyaDao = null;
//
//	// add s.inoue 2010/01/13
//	private ButtonGroup radioButtonGroup = new ButtonGroup();
//	private String hokenjyaNumber =  "";
//	// add s.inoue 2010/01/13
//	ArrayList<Integer> arrYukou = new ArrayList<Integer>();
//	// del s.inoue 2010/01/14
//	// private ExtendedCheckBox checkedTable = new ExtendedCheckBox();
//
//	// add s.inoue 2010/01/15
//	private static final String TANKA_HANTEI_KIHON = "1";
//	private static final String TANKA_HANTEI_DOC = "2";
//
//	private static final int COLUMUN_YUKOUKIGEN_FROM = 1;
//	private static final int COLUMUN_YUKOUKIGEN_TO = 2;
//	private static final int COLUMUN_HISTORY_NO = 3;
//	private static final int COLUMUN_ITAKU_KBN = 4;
//
//	/**
//	 * 保険者情報追加の場合のコンストラクタ（遷移元：マスタメンテナンス）
//	 */
//	public JHokenjyaMasterMaintenanceEditFrameCtrl() {
//		super();
//
//		mode = ADD_MODE;
//		init();
//
//		// jPanel11.add(new JSimpleTableScrollPanel(model));
//
//		// add s.inoue 2010/01/12
//		initilizeTableSettings(hokenjyaNumber);
//		functionListner();
//
//		jTextField_HokenjyaNumber.setBackground(ViewSettings.getRequiedItemBgColor());
//		this.jButton_Add.setVisible(false);
//		// del s.inoue 2010/02/02
////		this.jButton_RegisterYukoukigen.setVisible(false);
//		this.jButton_RegisterYukoukigenEdit.setVisible(false);
//		// edit s.inoue 2010/01/21
//		this.jButton_RegisterYukoukigenDelete.setVisible(false);
//		setModeAppearSettings(true);
//	}
//
//	/**
//	 * 保険者情報追加の場合のコンストラクタ（遷移元：個人情報登録フレーム）
//	 */
//	public JHokenjyaMasterMaintenanceEditFrameCtrl(
//			JKojinRegisterFrameData ptData) {
//		super();
//
//		mode = ADD_MODE;
//		// move s.inoue 2010/02/16
//		this.jButton_ORCA.setVisible(false);
//		this.jButton_Add.setVisible(false);
//
//		init();
//
//		// del s.inoue 2010/01/21
//		// jPanel11.add(new JSimpleTableScrollPanel(model));
//		// add s.inoue 2010/01/12
//		initilizeTableSettings(hokenjyaNumber);
//
//		// edit s.inoue 2010/02/12
//		jTextField_HokenjyaNumber.setText(ptData.getHokenjyaNumber());
//		jTextField_HokenjyaNumber.setBackground(ViewSettings.getRequiedItemBgColor());
//
//		// del s.inoue 2010/02/02
////		this.jButton_RegisterYukoukigen.setVisible(false);
//
//		// edit s.inoue 2010/02/10
//		this.jButton_RegisterYukoukigenEdit.setVisible(false);
//		this.jButton_RegisterYukoukigenDelete.setVisible(false);
//		// add s.inoue 2010/01/20
//		initializeTanka = true;
//		setModeAppearSettings(true);
//		// move s.inoue 2010/04/14
//		functionListner();
//	}
//
//	// add s.inoue 2010/01/22
//	private void setModeAppearSettings(boolean pflg){
//		this.jButton_ClearAdd.setVisible(pflg);
//		this.jButton_RegisterAdd.setVisible(pflg);
//		this.jButton_Register.setVisible(!pflg);
//		this.jButton_Clear.setVisible(!pflg);
//	}
//	/**
//	 * 保険者情報変更の場合のコンストラクタ（遷移元：マスタメンテナンス）
//	 */
//	public JHokenjyaMasterMaintenanceEditFrameCtrl(String hokenjyaNumber) {
//
//		super();
//
//		mode = CHANGE_MODE;
//		init();
//
//		// add s.inoue 2010/01/12
//		initilizeTableSettings(hokenjyaNumber);
//
//		// 遷移元フレームから得た保険者番号から既存のデータを取得
//		jTextField_HokenjyaNumber.setText(hokenjyaNumber);
//		jTextField_HokenjyaNumber.setEditable(false);
//
//		// add s.inoue 2010/01/14
//		setTankaTable(mode,hokenjyaNumber);
//
//		functionListner();
//
//		this.jTextField_HokenjyaNumber.addKeyListener(new KeyAdapter(){
//
//			@Override
//			public void keyPressed(KeyEvent keyEvent) {
//				String number =
//					JHokenjyaMasterMaintenanceEditFrameCtrl.
//					this.jTextField_HokenjyaNumber.getText();
//
//				if (number == null || number.isEmpty()) {
//					JHokenjyaMasterMaintenanceEditFrameCtrl.this.jButton_ORCA.setEnabled(false);
//				}
//			}
//		});
//
//		this.jButton_ORCA.setVisible(false);
//		// add s.inoue 2010/01/20
//		initializeTanka = true;
//
//		// add s.inoue 2010/01/21
//		initilizeFocus();
//		setModeAppearSettings(false);
//	}
//
//	// add s.inoue 2010/01/14
//	private void initilizeTableSettings(String hokenjyaNumber){
//
//		// edit s.inoue 2010/01/21
//		initilizeFocus();
//
//		// edit s.inoue 2010/01/14
//		this.hokenjyaNumber = hokenjyaNumber;
//
//		/* T_HOKENJYA Dao を作成する。 */
//		try {
//			tHokenjyaDao = (THokenjyaDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), new THokenjya());
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//		}
//
//		// edit s.inoue 2010/01/08
//		if (mode == CHANGE_MODE){
//			initializeSetting(hokenjyaNumber);
//
//			// add s.inoue 2010/01/11
//			model.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//				public void valueChanged(ListSelectionEvent e) {
//					if(e.getValueIsAdjusting()) return;
//
//					int irow = model.getSelectedRow();
//					if (irow < 0) return;
//
//					// del s.inoue 2010/02/02
////					jButton_RegisterYukoukigenEdit.setVisible(true);
////					jButton_RegisterYukoukigen.setVisible(false);
//
//					// edit s.inoue 2010/01/26
//					jButton_Clear.setEnabled(true);
//					jButton_Register.setEnabled(true);
//					jTextField_HokenjyaName.setEditable(true);
//					jTextField_Zipcode.setEditable(true);
//					jTextField_Address.setEditable(true);
//					jTextField_Chiban.setEditable(true);
//					jTextField_TEL.setEditable(true);
//					jTextField_Kigo.setEditable(true);
//
//					// 選択行を入力フィールドへ反映
//					jTextField_YukoukigenFrom.setText(String.valueOf(model.getValueAt(irow, 1)));
//					jTextField_YukoukigenTo.setText(String.valueOf(model.getValueAt(irow, 2)));
//					jComboBox_ItakuKubun.setSelectedIndex(Integer.parseInt((String) model.getValueAt(irow, 4))-1);
//					// del s.inoue 2010/01/20
//					// jComboBox_ItakuKubun.setEditable(false);
//
//					jTextField_KihonTanka.setText(String.valueOf(model.getValueAt(irow, 5)).replaceAll(",", ""));
//					jTextField_HinketsuTanka.setText(String.valueOf(model.getValueAt(irow, 6)).replaceAll(",", ""));
//					jTextField_SindenzuTanka.setText(String.valueOf(model.getValueAt(irow, 7)).replaceAll(",", ""));
//					jTextField_GanteiTanka.setText(String.valueOf(model.getValueAt(irow, 8)).replaceAll(",", ""));
//					jTextField_NingenDocTanka.setText(String.valueOf(model.getValueAt(irow, 9)).replaceAll(",", ""));
//
//				}
//			});
//		}
//	}
//
//	// add s.inoue 2010/01/14
//	/* 単価コントロール表示処理 */
//	private void setTankaTable(int pmode,String hokenjyaNumber){
//
//		ArrayList<Hashtable<String, String>> result
//		= new ArrayList<Hashtable<String, String>>();
//
//		Hashtable<String, String> resultItem = new Hashtable<String, String>();
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT * FROM T_HOKENJYA ");
//		sb.append(" WHERE HKNJANUM = ");
//		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
//		sb.append(" AND HKNJYA_HISTORY_NO = ");
//		sb.append(JQueryConvert.queryConvert(getmodelColumnNumber(COLUMUN_HISTORY_NO)));
//		sb.append(" AND ITAKU_KBN = ");
//		sb.append(JQueryConvert.queryConvert(getmodelColumnNumber(COLUMUN_ITAKU_KBN)));
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		// edit s.inoue 2010/01/13
//		if (arrYukou.size() <= 0){
//			JErrorMessage.show("M3158", null);
//			return;
//		}
//
//		// edit s.inoue 2010/01/15
//		// resultItem = result.get(arrYukou.get(0));
//		resultItem = result.get(0);
//
//		jTextField_HokenjyaName.setText(resultItem.get("HKNJANAME"));
//		jTextField_Zipcode.setText(resultItem.get("POST").trim());
//		jTextField_Address.setText(resultItem.get("ADRS"));
//		jTextField_Chiban.setText(resultItem.get("BANTI"));
//		jTextField_TEL.setText(resultItem.get("TEL"));
//		jTextField_Kigo.setText(resultItem.get("KIGO"));
//
//		// edit s.inoue 2010/02/02
//		if(pmode == ADD_MODE_TANKA ||
//				pmode == CHANGE_MODE_TANKA){
//			validatedData.setHokenjyaName(resultItem.get("HKNJANAME").trim());
//			validatedData.setZipcode(resultItem.get("POST").trim());
//			validatedData.setAddress(resultItem.get("ADRS").trim());
//			validatedData.setChiban(resultItem.get("BANTI").trim());
//			validatedData.setTEL(resultItem.get("TEL").trim());
//			validatedData.setKigo(resultItem.get("KIGO").trim());
//			return;
//		}
//
//		// add s.inoue 2010/01/14
//		jTextField_YukoukigenFrom.setText(resultItem.get("HKNJYA_LIMITDATE_START"));
//		jTextField_YukoukigenTo.setText(resultItem.get("HKNJYA_LIMITDATE_END"));
//
//		if (!resultItem.get("ITAKU_KBN").isEmpty()) {
//			jComboBox_ItakuKubun.setSelectedIndex(Integer.valueOf(resultItem.get("ITAKU_KBN")) - 1);
//		}
//
//		// del s.inoue 2010/01/20
//		// 1:基本、2:ドック
//		String hani = resultItem.get("TANKA_HANTEI");
//		hani = (hani == null)?TANKA_HANTEI_KIHON: hani;
//
//		// edit s.inoue 2010/01/20
//		if(hani.equals(TANKA_HANTEI_KIHON)){
//			jRadioButton_Kihon.setSelected(true);
//		}else if (hani.equals(TANKA_HANTEI_DOC)){
//			jRadioButton_Doc.setSelected(true);
//		}
//
//		// edit s.inoue 2010/01/20
//		jTextField_NingenDocTanka.setText(resultItem.get("TANKA_NINGENDOC"));
//		// 基本健診のみ設定
//		jTextField_KihonTanka.setText(resultItem.get("TANKA_KIHON"));
//		jTextField_HinketuCode.setText(resultItem.get("HINKETU_CD"));
//		jTextField_SindenzuCode.setText(resultItem.get("SINDENZU_CD"));
//		jTextField_GanteiCode.setText(resultItem.get("GANTEI_CD"));
//		jTextField_HinketsuTanka.setText(resultItem.get("TANKA_HINKETU"));
//		jTextField_SindenzuTanka.setText(resultItem.get("TANKA_SINDENZU"));
//		jTextField_GanteiTanka.setText(resultItem.get("TANKA_GANTEI"));
//		jTextField_HonninGairai.setText(resultItem.get("HON_GAIKYURATE"));
//		jTextField_HonninNyuin.setText(resultItem.get("HON_NYUKYURATE"));
//		jTextField_KazokuGairai.setText(resultItem.get("KZK_GAIKYURATE"));
//		jTextField_KazokuNyuin.setText(resultItem.get("KZK_NYUKYURATE"));
//	}
//
//	/* 単価テーブル一覧表示処理 */
//	private void initializeSetting(String hokenjyaNumber){
//
//		// edit s.inoue 2010/01/14
//		Object[][] hokenjyaData = null;
//		hokenjyaData = this.resultRefresh(hokenjyaNumber);
//		dmodel = new DefaultTableModel(hokenjyaData,header){
//		public boolean isCellEditable(int row, int column) {
//			boolean retflg = false;
//			if ( column >9 ){
//				retflg = true;
//			}
//			return retflg;
//			}
//		};
//
//		sorter = new TableRowSorter<TableModel>(dmodel);
//		model = new JSimpleTable(dmodel);
//		model.setPreferedColumnWidths(new int[]{70,80,80,70,80,120,100,110,110,110});
//		model.setRowSorter(sorter);
//		model.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//		final JScrollPane scroll = new JScrollPane(model);
//		JViewport viewport = new JViewport();
//		scroll.setRowHeader(viewport);
//		// edit s.inoue 2010/01/26
//		// scroll.setPreferredSize(new Dimension(963, 130));
//		scroll.setPreferredSize(new Dimension(950, 115));
//		if(mode == CHANGE_MODE)
//		dmodel.setDataVector(hokenjyaData,header);
//
//		jPanel_TableArea.add(scroll);
//
//		TableColumnModel columns = model.getColumnModel();
//		for(int i=0;i<columns.getColumnCount();i++) {
//			columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//					(DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//		}
//
//		// edit s.inoue 2010/01/13
//		// JRadioYukou.addItemListener(this);
//		JRadioYukou.addKeyListener(this);
//
//		model.setCellRadioButtonEditor(JRadioYukou,0);
//		radioButtonGroup.add(JRadioYukou);
//
//		// del s.inoue 2010/01/14
//		// checkedTable.addKeyListener(this);
//		// model.setCellCheckBoxEditor(checkedTable, 1);
//
//		model.setselectedRows(model, arrYukou);
//		model.addHeader(this.header);
//		model.refreshTable();
//
//	}
//
//	// add s.inoue 2010/01/14
//	/* 一覧リフレッシュ処理 */
//	private void searchReflesh(String hokenjyaNumber){
//
//		Object[][] hokenjyaData = this.resultRefresh(hokenjyaNumber);
//		dmodel.setDataVector(hokenjyaData,header);
//
//		TableColumnModel columns = model.getColumnModel();
//		for(int i=0;i<columns.getColumnCount();i++) {
//			columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//					(DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
//		}
//
//		JRadioYukou.addItemListener(this);
//		model.setCellRadioButtonEditor(JRadioYukou,0);
//		radioButtonGroup.add(JRadioYukou);
//		model.setselectedRows(model, arrYukou);
//		model.addHeader(this.header);
//		model.refreshTable();
//	}
//
//	// add s.inoue 2010/01/15
//	/* 一覧用データ取得 */
//	private Object[][] resultRefresh(String hokenjyaNumber)
//	{
//		// edit s.inoue 2010/01/08
//		StringBuilder sb = new StringBuilder();
//		sb.append(" SELECT distinct ");
//		sb.append(" YUKOU_FLG,HKNJYA_LIMITDATE_START,HKNJYA_LIMITDATE_END,HKNJYA_HISTORY_NO,ITAKU_KBN,TANKA_KIHON,");
//		sb.append(" TANKA_HINKETU,TANKA_SINDENZU,TANKA_GANTEI,TANKA_NINGENDOC");
//		sb.append(" FROM T_HOKENJYA ");
//		sb.append(" WHERE HKNJANUM = ");
//		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
//		sb.append(" ORDER BY HKNJYA_HISTORY_NO DESC,ITAKU_KBN ");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		rowcolumn = new Object[result.size()][16];
//		int counti = 0;
//		List<String> fields = new ArrayList<String>();
//
//		for (Hashtable<String, String> record : result) {
//			for (String fieldName : TABLE_COLUMNS) {
//				fields.add(record.get(fieldName));
//			}
//
//			// edit s.inoue 2010/01/13
//			model.addData(fields.toArray(new String[0]));
//
//			String[] col = fields.toArray(new String[0]);
//			for (int i = 0; i < col.length; i++) {
//
//				Object val = col[i]==null?"":col[i];
//					if (i == 0){
//						// edit s.inoue 2010/01/13
//						val = new JRadioButton(String.valueOf(i));
//						if (col[i].equals("1")){
//							// add s.inoue 2010/01/21
//							if (arrYukou.size() > 0){
//								arrYukou.remove(0);
//							}
//							arrYukou.add(counti);
//						}
//					}else if ((i==5 || i==6 || i==7|| i==8 || i==9) && !(val.equals(""))){
//							NumberFormat kingakuFormat = NumberFormat.getNumberInstance();
//							val = kingakuFormat.format(Long.parseLong(String.valueOf(val)));
//					}
//					rowcolumn[counti][i] = val;
//				}
//
//			counti ++;
//
//			fields.clear();
//		}
//
//		return rowcolumn;
//	}
//
//	/**
//	 * 印刷時の必須項目をチェックする
//	 */
//	public boolean validateAsPushedORCAButton(
//			JHokenjyaMasterMaintenanceEditFrameData data) {
//		if (data.getHokenjyaNumber().isEmpty()) {
//			return false;
//		}
//
//		return true;
//	}
//
//	/**
//	 * 初期化
//	 */
//	private void init() {
//		jComboBox_ItakuKubun.addItem(itakuKubunStr[0]);
//		jComboBox_ItakuKubun.addItem(itakuKubunStr[1]);
//		// del s.inoue 2010/01/19
//		// if ((modeYukouKigen == CHANGE_MODE) ||
//		// 		(mode == CHANGE_MODE))
//		// jComboBox_ItakuKubun.setEnabled(false);
//
//		jTextField_HinketuCode.setText("1");
//		jTextField_SindenzuCode.setText("2");
//		jTextField_GanteiCode.setText("3");
//
//		// add s.inoue 2009/10/01
//		jRadioButton_Kihon.setSelected(true);
//
//		// del s.inoue 2010/01/30
//		// this.jTextField_KihonTanka.setBackground(ViewSettings.getRequiedItemBgColor());
//
//		// edit s.inoue 2010/01/21
//		initilizeFocus();
//	}
//
//	/* focus初期化 */
//	private void initilizeFocus(){
//		// focus制御
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		// edit s.inoue 2009/12/02
//		if(mode == ADD_MODE){
//			this.focusTraversalPolicy.setDefaultComponent(jTextField_HokenjyaNumber);
//			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
//			// edit s.inoue 2010/02/16
//			if (jButton_ORCA.isVisible())
//				this.focusTraversalPolicy.addComponent(jButton_ORCA);
//
//			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
//		}else if (mode == CHANGE_MODE){
//			this.focusTraversalPolicy.setDefaultComponent(jTextField_HokenjyaName);
//		}
//
//		// edit s.inoue 2010/02/16
////		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		this.focusTraversalPolicy.addComponent(jButton_ORCA);
////		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
//		this.focusTraversalPolicy.addComponent(jTextField_Zipcode);
//		this.focusTraversalPolicy.addComponent(jTextField_TEL);
//		this.focusTraversalPolicy.addComponent(jTextField_Address);
//		this.focusTraversalPolicy.addComponent(jTextField_Chiban);
//		this.focusTraversalPolicy.addComponent(jTextField_Kigo);
//		// edit s.inoue 2010/01/22
//		if(mode == CHANGE_MODE){
//			this.focusTraversalPolicy.addComponent(jButton_Register);
//			this.focusTraversalPolicy.addComponent(jButton_Clear);
//		}
//		// edit s.inoue 2010/01/21
//		if(mode == CHANGE_MODE)
//			this.focusTraversalPolicy.addComponent(this.model);
//
//		this.focusTraversalPolicy.addComponent(jTextField_YukoukigenFrom);
//		this.focusTraversalPolicy.addComponent(jTextField_YukoukigenTo);
//		this.focusTraversalPolicy.addComponent(jTextField_TankaHantei);
//		this.focusTraversalPolicy.addComponent(jComboBox_ItakuKubun);
//		// edit s.inoue 2010/01/21
//		if (jRadioButton_Doc.isSelected()){
//			this.focusTraversalPolicy.addComponent(jTextField_NingenDocTanka);
//		}else{
//			this.focusTraversalPolicy.addComponent(jTextField_KihonTanka);
//			this.focusTraversalPolicy.addComponent(jTextField_HinketsuTanka);
//			this.focusTraversalPolicy.addComponent(jTextField_SindenzuTanka);
//			this.focusTraversalPolicy.addComponent(jTextField_GanteiTanka);
//		}
//
//		// edit s.inoue 2010/01/21
//		if (mode != ADD_MODE){
//			// del s.inoue 2010/02/02
////			if (modeYukouKigen == ADD_MODE){
////				this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigen);
////			}else{
//				this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigenEdit);
////			}
//			this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigenDelete);
//			// edit s.inoue 2010/02/01
//			this.focusTraversalPolicy.addComponent(jButton_Add);
//		}else{
//			// edit s.inoue 2010/02/16
//			this.focusTraversalPolicy.addComponent(jButton_RegisterAdd);
//			this.focusTraversalPolicy.addComponent(jButton_ClearAdd);
//		}
//
//		this.focusTraversalPolicy.addComponent(jButton_End);
//
//		// edit s.inoue 2010/01/21
//		if(mode == ADD_MODE){
//			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
//		}else if (mode == CHANGE_MODE){
//			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
//		}
//
//		// edit s.inoue 2010/02/16
////		// focus制御
////		this.focusTraversalPolicy = new JFocusTraversalPolicy();
////		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
////		// edit s.inoue 2009/12/02
////		if(mode == ADD_MODE){
////			this.focusTraversalPolicy.setDefaultComponent(jTextField_HokenjyaNumber);
////			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		}else if (mode == CHANGE_MODE){
////			this.focusTraversalPolicy.setDefaultComponent(jTextField_HokenjyaName);
////		}
////		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		this.focusTraversalPolicy.addComponent(jButton_ORCA);
////		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
////		this.focusTraversalPolicy.addComponent(jTextField_Zipcode);
////		this.focusTraversalPolicy.addComponent(jTextField_TEL);
////		this.focusTraversalPolicy.addComponent(jTextField_Address);
////		this.focusTraversalPolicy.addComponent(jTextField_Chiban);
////		this.focusTraversalPolicy.addComponent(jTextField_Kigo);
////		// edit s.inoue 2010/01/22
////		if(mode == CHANGE_MODE){
////			this.focusTraversalPolicy.addComponent(jButton_Register);
////			this.focusTraversalPolicy.addComponent(jButton_Clear);
////		}
////		// edit s.inoue 2010/01/21
////		if(mode == CHANGE_MODE)
////			this.focusTraversalPolicy.addComponent(this.model);
////
////		this.focusTraversalPolicy.addComponent(jTextField_YukoukigenFrom);
////		this.focusTraversalPolicy.addComponent(jTextField_YukoukigenTo);
////		this.focusTraversalPolicy.addComponent(jTextField_TankaHantei);
////		this.focusTraversalPolicy.addComponent(jComboBox_ItakuKubun);
////		// edit s.inoue 2010/01/21
////		if (jRadioButton_Doc.isSelected()){
////			this.focusTraversalPolicy.addComponent(jTextField_NingenDocTanka);
////		}else{
////			this.focusTraversalPolicy.addComponent(jTextField_KihonTanka);
////			this.focusTraversalPolicy.addComponent(jTextField_HinketsuTanka);
////			this.focusTraversalPolicy.addComponent(jTextField_SindenzuTanka);
////			this.focusTraversalPolicy.addComponent(jTextField_GanteiTanka);
////		}
////
////		// edit s.inoue 2010/01/21
////		if (mode != ADD_MODE){
////			// del s.inoue 2010/02/02
//////			if (modeYukouKigen == ADD_MODE){
//////				this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigen);
//////			}else{
////				this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigenEdit);
//////			}
////			this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigenDelete);
////			// edit s.inoue 2010/02/01
////			this.focusTraversalPolicy.addComponent(jButton_Add);
////		}else{
////			// edit s.inoue 2010/02/16
////			this.focusTraversalPolicy.addComponent(jButton_RegisterAdd);
////			this.focusTraversalPolicy.addComponent(jButton_ClearAdd);
////		}
////
////		this.focusTraversalPolicy.addComponent(jButton_End);
////
////		// edit s.inoue 2010/01/21
////		if(mode == ADD_MODE){
////			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		}else if (mode == CHANGE_MODE){
////			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
////		}
//	}
//
//	private void functionListner(){
//		// edit s.inoue 2010/04/21
//		boolean flg = false;
//
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//
//			if (comp.equals(jTextField_HokenjyaNumber)){
//				if (!flg)
//					comp.addKeyListener(this);
//
//				flg = true;
//			}else{
//				comp.addKeyListener(this);
//			}
//		}
//	}
//
//	/**
//	 * 保険者番号が入力された場合
//	 */
//	public void enterKeyPushedHokenjyaNumber() {
//		/* 保険者番号が未入力の場合は、保険者名称欄にフォーカスを移動する。 */
//		String hokenjaNumber = this.jTextField_HokenjyaNumber.getText();
//
//		if (hokenjaNumber == null || hokenjaNumber.isEmpty()) {
//			this.jTextField_HokenjyaName.grabFocus();
//		}
//
//		if (validatedData.setHokenjyaNumber(hokenjaNumber)) {
//
//			pushedORCAButton();
//		}
//	}
//
//	/**
//	 * 登録ボタンを押した際の必須項目の検証
//	 */
//	protected boolean validateAsRegisterPushed(int pmode,
//			JHokenjyaMasterMaintenanceEditFrameData data) {
//		if (pmode == ADD_MODE ||
//				pmode == CHANGE_MODE){
//			if (data.getHokenjyaNumber().equals("")) {
//				JErrorMessage.show(
//						"M3120", null);
//				return false;
//			}
//		}
//		// edit s.inoue 2010/01/27
//		// }else{
//			// add s.inoue 2010/01/15
//
//			// 有効期限必須
//			if (data.getYukouKigenFrom().equals("")){
//				JErrorMessage.show(
//						"M3125", null);
//				return false;
//			}
//			if (data.getYukouKigenTo().equals("")){
//				JErrorMessage.show(
//						"M3126", null);
//				return false;
//			}
//			// 有効期限の範囲チェック
//			if(Integer.parseInt(data.getYukouKigenFrom()) > Integer.parseInt(data.getYukouKigenTo())){
//				JErrorMessage.show("M3159", null);
//				return false;
//			}
//
//			// 人間ドック単価又は基本単価必須
//			if (data.getKihonTanka().equals("")
//					&& data.getNingenDocTanka().equals("")) {
//				JErrorMessage.show("M3125", null);
//				return false;
//			}
//		// edit s.inoue 2010/01/27
//		// }
//		validatedData.setValidationAsDataSet();
//		return true;
//	}
//
//	private static String lastAddedNumber = null;
//
//	public static String getLastAddedNumber() {
//		return lastAddedNumber;
//	}
//
//	private static boolean registered = false;
//	public static boolean isRegistered() {
//		return registered;
//	}
//
//	/**
//	 * 登録処理を行う
//	 */
//	public void register(int pmode) {
//		if (!this.validateData(pmode)) {
//			return;
//		}
//
//		// 各フィールドについて検証済み
//		if (!validateAsRegisterPushed(pmode,validatedData)) {
//			return;
//		}
//
//		// 必須項目についても抜けがない
//		if (!validatedData.isValidationAsDataSet()) {
//			return;
//		}
//
//		String inputHokenjyaNumber = validatedData.getHokenjyaNumber();
//		// edit s.inoue 2010/01/12
//		String inputHokenjyaHistoryNumber = validatedData.getHknjyaHistoryNumber();
//
//		String inputItakuKubun = jComboBox_ItakuKubun.getSelectedItem().toString().substring(0, 1);
//		// add s.inoue 2010/01/19
//		boolean itakuEditFlg = false;
//		String itakuKubunList = getmodelColumnNumber(4);
//		if (!itakuKubunList.equals(inputItakuKubun))
//			itakuEditFlg = true;
//
//
//		/* 登録済みの保険者か確認する。 */
//
//		StringBuilder sbCount = new StringBuilder();
//		sbCount.append("select count(HKNJANUM) as ROWCOUNT from T_HOKENJYA ");
//		sbCount.append(" where HKNJANUM = ? ");
//		// del s.inoue 2010/01/19
//		// sbCount.append("and HKNJYA_HISTORY_NO = ?");
//		if(pmode == CHANGE_MODE ||
//			  pmode == ADD_MODE_TANKA ||
//				 pmode == CHANGE_MODE_TANKA){
//			sbCount.append(" and ITAKU_KBN = ? ");
//			// edit s.inoue 2010/01/29
//			sbCount.append(" and (( HKNJYA_LIMITDATE_START <= ? and HKNJYA_LIMITDATE_END >= ? ) ");
//			sbCount.append(" or ( HKNJYA_LIMITDATE_START <= ? and HKNJYA_LIMITDATE_END >= ? ) ");
//			// sbCount.append(" and (HKNJYA_LIMITDATE_START  between ? and ? ");
//			// sbCount.append(" or HKNJYA_LIMITDATE_END between ? and ? )");
//			sbCount.append(" or ( HKNJYA_LIMITDATE_START >= ? and HKNJYA_LIMITDATE_END <= ? ))");
//
//			// del s.inoue 2010/01/29
////			if(pmode == CHANGE_MODE_TANKA){
////				sbCount.append(" and HKNJYA_HISTORY_NO <> ? ");
////			}
//		}
//
//		String[] params = null;
//
//		// edit s.inoue 2010/01/22
//		if(pmode == CHANGE_MODE ||
//				pmode == ADD_MODE_TANKA ||
//					 pmode == CHANGE_MODE_TANKA){
//
//			// del s.inoue 2010/01/29
////			if(pmode == CHANGE_MODE_TANKA){
////				params = new String[9];
////			}else{
//			params = new String[8];
////			}
//
//			params[0]=inputHokenjyaNumber;
//			params[1]=inputItakuKubun;
//			params[2]=jTextField_YukoukigenFrom.getText();
//			params[3]=jTextField_YukoukigenFrom.getText();
//			params[4]=jTextField_YukoukigenTo.getText();
//			params[5]=jTextField_YukoukigenTo.getText();
//			params[6]=jTextField_YukoukigenFrom.getText();
//			params[7]=jTextField_YukoukigenTo.getText();
//
//			// del s.inoue 2010/01/29
////			if(pmode == CHANGE_MODE_TANKA){
////				params[8]=getmodelColumnNumber(COLUMUN_HISTORY_NO);
////			}
//		}else{
//			params = new String[1];
//			params[0]=inputHokenjyaNumber;
//		}
//
////		params = { inputHokenjyaNumber , inputItakuKubun,
////				jTextField_YukoukigenFrom.getText(),jTextField_YukoukigenTo.getText(),
////				jTextField_YukoukigenFrom.getText(),jTextField_YukoukigenTo.getText(),
////				jTextField_YukoukigenFrom.getText(),jTextField_YukoukigenTo.getText()};
//
//		ArrayList<Hashtable<String, String>> result = null;
//		try{
//			result = JApplication.kikanDatabase.sendExecuteQuery(sbCount.toString(), params);
//		}catch(SQLException e){
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		// add s.inoue 2010/01/30
//		// getmodelColumnNumber(COLUMUN_HISTORY_NO)
//
//		boolean existsNumber = false;
//		int count = 0;
//		if (result != null && result.size() > 0) {
//			String countString = result.get(0).get("ROWCOUNT");
//
//			// edit s.inoue 2010/02/02
//			boolean duplicateFlg =checkmodelColumnDuplicate(
//					jTextField_YukoukigenFrom.getText(),
//					jTextField_YukoukigenTo.getText(),
//					getmodelColumnNumber(COLUMUN_HISTORY_NO),
//					String.valueOf(jComboBox_ItakuKubun.getSelectedIndex()+1)
//					);
////			boolean updateflg =(jTextField_YukoukigenFrom.getText().equals(
////					getmodelColumnNumber(COLUMUN_YUKOUKIGEN_FROM)) &&
////				jTextField_YukoukigenTo.getText().equals(
////					getmodelColumnNumber(COLUMUN_YUKOUKIGEN_TO)) &&
////				getmodelColumnNumber(COLUMUN_ITAKU_KBN).equals(
////					String.valueOf(jComboBox_ItakuKubun.getSelectedIndex()+1)));
//
//			try {
//				count = Integer.valueOf(countString);
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			}
//			// edit s.inoue 2010/01/28
//			if(((count == 0) &&
//					(pmode == CHANGE_MODE_TANKA)) ){
//				RETURN_VALUE Ret = JErrorMessage.show("M3165", this);
//				if (Ret == RETURN_VALUE.YES) {
//					// edit s.inoue 2010/02/01
//					// add s.inoue 2010/01/29
//					pmode = ADD_MODE_TANKA;
//					controlSettingData(pmode);
//
//// del s.inoue 2010/04/21
////				// edit s.inoue 2010/04/21
////				}else if (Ret == RETURN_VALUE.NO){
//
////					if (!this.validateData(pmode)) {
////						return;
////					}
//				}else if (Ret == RETURN_VALUE.CANCEL){
//					return;
//				}
//			// edit s.inoue 2010/01/30
//			}else if((count > 0) &&
//				(pmode == CHANGE_MODE_TANKA) &&
//				// edit s.inoue 2010/02/02
//				duplicateFlg){
//					JErrorMessage.show("M3163", this);
//					return;
//			// edit s.inoue 2010/01/29
//			}else if(count > 0){
//				existsNumber = true;
//			}
//		}
//
//		// edit s.inoue 2010/01/21
//		if ((pmode == ADD_MODE_TANKA && existsNumber)
////				|| (pmode == ADD_MODE && existsNumber)
////		if ((modeYukouKigen == ADD_MODE && existsNumber)
////				|| (mode == ADD_MODE && existsNumber)
//				|| inputHokenjyaNumber.equals("99999999")
//				|| inputHokenjyaNumber.equals("00000000")) {
//			JErrorMessage.show("M3163", this);
//			return;
//		}else if(pmode == ADD_MODE && existsNumber){
//			JErrorMessage.show("M3164", this);
//			return;
//		}
//
//		// add s.inoue 2010/01/13
//		/* 期限有効フラグを全て"0"にセットする */
//// edit s.inoue 2010/01/21
//		if (pmode == ADD_MODE_TANKA ||
//				pmode == CHANGE_MODE_TANKA){
//			try {
//				JApplication.kikanDatabase.Transaction();
//
//				StringBuilder sb = new StringBuilder();
//				sb.append("UPDATE T_HOKENJYA SET ");
//				sb.append(" YUKOU_FLG = '0' ");
//				sb.append(" WHERE HKNJANUM = ");
//				sb.append(JQueryConvert.queryConvert(inputHokenjyaNumber));
//				JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
//
//				JApplication.kikanDatabase.Commit();
//			} catch (SQLException e) {
//				logger.error(e.getMessage());
//				e.printStackTrace();
//			}
//		}
//		StringBuffer buffer = new StringBuffer();
//		String address = JValidate.encodeUNICODEtoConvert(validatedData.getAddress());
//		String tiban = JValidate.encodeUNICODEtoConvert(validatedData.getChiban());
//
//		// edit s.inoue 2010/01/12
//		if (pmode == ADD_MODE ||
//				pmode == ADD_MODE_TANKA) {
////		if (mode == ADD_MODE ||
////				modeYukouKigen == ADD_MODE) {
//			// edit s.inoue 2010/01/19
//			long historyNum = createHistoryNo(inputHokenjyaNumber,inputHokenjyaHistoryNumber,inputItakuKubun);
//			if (historyNum == 0)
//				return;
//
//			buffer.append("INSERT INTO T_HOKENJYA (");
//			buffer.append("HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL,");
//			buffer.append("KIGO,HON_GAIKYURATE,HON_NYUKYURATE,");
//			buffer.append("KZK_GAIKYURATE,KZK_NYUKYURATE,ITAKU_KBN,");
//			buffer.append("TANKA_KIHON,HINKETU_CD,TANKA_HINKETU,");
//			buffer.append("SINDENZU_CD,TANKA_SINDENZU,GANTEI_CD,");
//			buffer.append("TANKA_GANTEI,TANKA_NINGENDOC,TANKA_HANTEI,");
//			buffer.append("HKNJYA_HISTORY_NO,");
//			buffer.append("HKNJYA_LIMITDATE_START,HKNJYA_LIMITDATE_END,");
//			buffer.append("YUKOU_FLG)");
//			buffer.append("VALUES ( "
//				+ JQueryConvert.queryConvertAppendComma(inputHokenjyaNumber)
//				+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getHokenjyaName())
//				+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
//				+ JQueryConvert.queryConvertAppendBlankAndComma(address)
//				+ JQueryConvert.queryConvertAppendBlankAndComma(tiban)
//				+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getTEL())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getCode())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninGairai())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninNyuin())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuGairai())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuNyuin())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuTanka())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getShindenzuCode())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getNingenDocTanka())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getTankaHantei())
//				// edit s.inoue 2010/01/19
//				+ JQueryConvert.queryConvertAppendComma(String.valueOf(historyNum))
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenFrom())
//				+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenTo())
//				// edit s.inoue 2010/01/13
//				// 有効フラグを"1"で登録する。validatedData.getYukouFlg()
//				+ JQueryConvert.queryConvert("1")
//				+ ")");
//		}
//		// edit s.inoue 2010/01/21
//		else if (pmode == CHANGE_MODE ||
//				pmode == CHANGE_MODE_TANKA) {
////		else if (mode == CHANGE_MODE ||
////				modeYukouKigen == CHANGE_MODE) {
//
//			buffer.append("UPDATE T_HOKENJYA SET ");
//					// edit s.inoue 2010/01/15
//					// + "HKNJANUM = "
//					// + JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
//
//					// edit s.inoue 2010/01/15
//					// + "ITAKU_KBN = "+ JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun())
//// edit s.inoue 2010/01/21
//			if (pmode == CHANGE_MODE_TANKA){
////				if (modeYukouKigen == CHANGE_MODE){
//					buffer.append("TANKA_KIHON = "+ JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka()));
//					buffer.append("HINKETU_CD = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode()));
//					buffer.append("TANKA_HINKETU = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuTanka()));
//					buffer.append("SINDENZU_CD = "+ JQueryConvert.queryConvertAppendComma(validatedData.getShindenzuCode()));
//					buffer.append("TANKA_SINDENZU = "+ JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka()));
//					buffer.append("GANTEI_CD = "+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode()));
//					buffer.append("TANKA_GANTEI = "+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka()));
//					buffer.append("TANKA_NINGENDOC = "+ JQueryConvert.queryConvertAppendComma(validatedData.getNingenDocTanka()));
//					buffer.append("TANKA_HANTEI = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTankaHantei()));
//					buffer.append("HKNJYA_LIMITDATE_START = "+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenFrom()));
//					buffer.append("HKNJYA_LIMITDATE_END = "+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenTo()));
//					// add s.inoue 2010/04/21
//					if (itakuEditFlg)
//						buffer.append(" ITAKU_KBN = " +JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun()));
//
//					buffer.append("YUKOU_FLG = "+ JQueryConvert.queryConvert("1"));
//
//				}else{
//					buffer.append("HKNJANAME = "+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getHokenjyaName()));
//					buffer.append("POST = "+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode()));
//					buffer.append("ADRS = "+ JQueryConvert.queryConvertAppendBlankAndComma(address));
//					buffer.append("BANTI = "+ JQueryConvert.queryConvertAppendBlankAndComma(tiban));
//					buffer.append("TEL = "+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getTEL()));
//					buffer.append("KIGO = "+ JQueryConvert.queryConvertAppendComma(validatedData.getCode()));
//					buffer.append("HON_GAIKYURATE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninGairai()));
//					buffer.append("HON_NYUKYURATE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninNyuin()));
//					buffer.append("KZK_GAIKYURATE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuGairai()));
//					buffer.append("KZK_NYUKYURATE = "+ JQueryConvert.queryConvert(validatedData.getKazokuNyuin()));
//				}
//
//				buffer.append(" WHERE HKNJANUM = "+ JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
//					// edit s.inoue 2010/01/21
//				if (pmode == CHANGE_MODE_TANKA){
////					if (modeYukouKigen == CHANGE_MODE){
//						// edit s.inoue 2010/04/21
//					if (!itakuEditFlg)
//						buffer.append(" AND ITAKU_KBN = " +JQueryConvert.queryConvert(validatedData.getItakuKubun()));
//
//						buffer.append(" AND HKNJYA_HISTORY_NO = " +JQueryConvert.queryConvert(String.valueOf(inputHokenjyaHistoryNumber)));
//					}
//		}
//
//		try {
//
//			JApplication.kikanDatabase.Transaction();
//			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//
//			// 新規追加の場合はさらに健診項目マスタへの追加も行う
//			// edit s.inoue 2010/01/21
//			if (pmode == ADD_MODE) {
////			if (mode == ADD_MODE) {
//				ArrayList<Hashtable<String, String>> resultKenshin = new ArrayList<Hashtable<String, String>>();
//				Hashtable<String, String> resultItemKenshin = new Hashtable<String, String>();
//				buffer = new StringBuffer(
//						"SELECT * FROM T_KENSHINMASTER WHERE HKNJANUM = "
//								+ JQueryConvert.queryConvert("99999999"));
//
//				resultKenshin = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
//
//				for (int i = 0; i < resultKenshin.size(); i++) {
//					resultItemKenshin = resultKenshin.get(i);
//
//					buffer = new StringBuffer("INSERT INTO T_KENSHINMASTER ( "
//							+ "HKNJANUM,"
//							+ "KOUMOKU_CD,"
//							+ "KOUMOKU_NAME,"
//							+ "DATA_TYPE,"
//							+ "MAX_BYTE_LENGTH,"
//							+ "XML_PATTERN,"
//							+ "XML_DATA_TYPE,"
//							+ "XML_KENSA_CD,"
//							+ "OBSERVATION,"
//							+ "AUTHOR,"
//							+ "AUTHOR_KOUMOKU_CD,"
//							+ "KENSA_GROUP,"
//							+ "KENSA_GROUP_CD,"
//							+ "KEKKA_OID,"
//							+ "KOUMOKU_OID,"
//							+ "HISU_FLG,"
//							+ "KAGEN,"
//							+ "JYOUGEN,"
//							+ "DS_JYOUGEN,"
//							+ "JS_JYOUGEN,"
//							+ "DS_KAGEN,"
//							+ "JS_KAGEN,"
//							+ "KIJYUNTI_HANI,"
//							+ "TANI,"
//							+ "KENSA_HOUHOU,"
//							+ "TANKA_KENSIN,"
//							+ "BIKOU,"
//							+ "XMLITEM_SEQNO"
//							+ ")"
//							+ "VALUES( "
//							+ JQueryConvert.queryConvertAppendComma(validatedData
//									.getHokenjyaNumber())
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KOUMOKU_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KOUMOKU_NAME"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("DATA_TYPE"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("MAX_BYTE_LENGTH"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("XML_PATTERN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("XML_DATA_TYPE"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("XML_KENSA_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("OBSERVATION"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("AUTHOR"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("AUTHOR_KOUMOKU_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KENSA_GROUP"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KENSA_GROUP_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KEKKA_OID"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KOUMOKU_OID"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("HISU_FLG"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("DS_JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("JS_JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("DS_KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("JS_KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KIJYUNTI_HANI"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("TANI"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("KENSA_HOUHOU"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("TANKA_KENSIN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
//									.get("BIKOU"))
//							+ JQueryConvert.queryConvert(resultItemKenshin
//									.get("XMLITEM_SEQNO")) +
//							")");
//
//					JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//				}
//			}
//			JApplication.kikanDatabase.Commit();
//			// edit s.inoue 2010/01/21
//			modeYukouKigen = CHANGE_MODE;
//
//			registered = true;
//			lastAddedNumber = validatedData.getHokenjyaNumber();
//			// edit s.inoue 2010/01/21
//			if(pmode == ADD_MODE_TANKA ||
//					pmode == CHANGE_MODE_TANKA){
////			if(modeYukouKigen == ADD_MODE ||
////					modeYukouKigen == CHANGE_MODE){
//				searchReflesh(validatedData.getHokenjyaNumber());
//				// add s.inoue 2010/01/26
//				controlSetting(true);
//			}
//			if(pmode == ADD_MODE ||
//					pmode == CHANGE_MODE){
//				dispose();
//			}
//		} catch (SQLException f) {
//			f.printStackTrace();
//			JErrorMessage.show(
//					"M3130", this);
//			try {
//				JApplication.kikanDatabase.rollback();
//				return;
//			} catch (SQLException g) {
//				g.printStackTrace();
//				JErrorMessage.show("M0000", this);
//				System.exit(1);
//			}
//		}
//	}
//
//	// add s.inoue 2010/01/12
//	// 新規登録 委託区分含めレコードがあった場合、エラー
//	//
//	/* 履歴番号採番 */
//	private long createHistoryNo(String inputHokenjyaNumber,String inputHistoryNo,String inputItakuKubun){
//		int historyCnt = 0;
//		long historyNo = -1L;
//
//		try{
//			historyCnt = tHokenjyaDao.selectByCountUniqueKey(inputHokenjyaNumber,inputHistoryNo,inputItakuKubun);
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//		// edit s.inoue 2010/01/15
//		if (historyCnt > 0){
//			try {
//				historyNo = tHokenjyaDao.selectNewHistoryId(inputHokenjyaNumber);
//				logger.info("保険者番号採番:" +historyNo);
//			}catch(Exception ex){
//				logger.error(ex.getMessage());
//			}
//		}else{
//			// edit s.inoue 2010/01/20
//			// historyNo = 0;
//			historyNo = Long.parseLong(inputHistoryNo);
//		}
//		return historyNo;
//	}
//
//	private boolean validateData(int pmode) {
//		String radioHantei = jRadioButton_Kihon.isSelected() ? "1" : "2";
//		// edit s.inoue 2009/12/02 基本単価必須の為、人間ドックの場合ゼロ登録させる
//		String kihontanka = jTextField_KihonTanka.getText();
//		kihontanka = (kihontanka.equals("")) ? "0" : kihontanka;
//
//		String historyNumber = (mode==CHANGE_MODE) ? getmodelColumnNumber(COLUMUN_HISTORY_NO): "1";
//
//		boolean rettanka = false;
//		boolean rethokenjya = false;
//
//		// edit s.inoue 2010/04/27
//		String address = jTextField_Address.getText();
//		if (!JValidate.isAllZenkaku(address)){
//			address = JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(address);
//		}
//		address = JValidate.encodeUNICODEtoConvert(address);
//
//		rettanka= validatedData.setHokenjyaName(jTextField_HokenjyaName.getText())
//			&& validatedData.setHokenjyaNumber(jTextField_HokenjyaNumber.getText())
//			&& validatedData.setHonninGairai(jTextField_HonninGairai.getText())
//			&& validatedData.setHinketsuCode(jTextField_HinketuCode.getText())
//			&& validatedData.setShindenzuCode(jTextField_SindenzuCode.getText())
//			&& validatedData.setGanteiCode(jTextField_GanteiCode.getText())
//			&& validatedData.setTEL(jTextField_TEL.getText())
//			&& validatedData.setZipcode(jTextField_Zipcode.getText())
//			// edit s.inoue 2010/02/01
//			&& validatedData.setAddress(address)
//			&& validatedData.setChiban(jTextField_Chiban.getText())
//			&& validatedData.setKigo(jTextField_Kigo.getText());
//
//	// edit s.inoue 2010/02/01
//	rethokenjya =
////	rethokenjya = validatedData.setAddress(jTextField_Address.getText())
////			&& validatedData.setChiban(jTextField_Chiban.getText())
////			&& validatedData.setKigo(jTextField_Kigo.getText())
//
//			// edit s.inoue 2009/12/02
//			// && validatedData.setKihonTanka(jTextField_KihonTanka.getText())
//		validatedData.setHonninNyuin(jTextField_HonninNyuin.getText())
//			&& validatedData.setKazokuGairai(jTextField_KazokuNyuin.getText())
//			&& validatedData.setKazokuNyuin(jTextField_KazokuNyuin.getText())
//			&& validatedData.setItakuKubun((String) jComboBox_ItakuKubun.getSelectedItem())
//			&& validatedData.setKihonTanka(kihontanka)
//			&& validatedData.setGanteiTanka(jTextField_GanteiTanka.getText())
//			&& validatedData.setHinketsuTanka(jTextField_HinketsuTanka.getText())
//			&& validatedData.setNingenDocTanka(jTextField_NingenDocTanka.getText())
//			&& validatedData.setSindenzuTanka(jTextField_SindenzuTanka.getText())
//			&& validatedData.setTankaHantei(radioHantei)
//			&& validatedData.setHknjyaHistoryNumber(historyNumber)
//			&& validatedData.setYukouKigenFrom(jTextField_YukoukigenFrom.getText())
//			&& validatedData.setYukouKigenTo(jTextField_YukoukigenTo.getText());
//
//		return rettanka && rethokenjya;
//	}
//
//	// add s.inoue 2010/01/12
//	/* 選択行指定列データ取得 */
//	private String getmodelColumnNumber(int colNumber){
//		return String.valueOf(model.getData(model.getSelectedRow(),colNumber));
//	}
//
//	// add s.inoue 2010/02/02
//	/* 期間重複チェック */
//	private boolean checkmodelColumnDuplicate(
//			String yukouKigenFrom,String yukouKigenTo,String historyNo,String itakuKbn){
//		boolean retflg = false;
//
//			for (int i = 0; i < model.getRowCount(); i++) {
//				String itakuTable = (String) model.getData(i,COLUMUN_ITAKU_KBN);
//				String historyNoTable = (String) model.getData(i,COLUMUN_HISTORY_NO);
//
//				// edit s.inoue 2010/02/09
//				if((itakuTable.equals(itakuKbn) &&
//						!(historyNoTable.equals(historyNo)))){
//					int yukouKigenFromTable = Integer.parseInt(
//							(String) model.getData(i,COLUMUN_YUKOUKIGEN_FROM));
//					int yukouKigenToTable = Integer.parseInt(
//							(String) model.getData(i,COLUMUN_YUKOUKIGEN_TO));
////				if(!(itakuTable.equals(itakuKbn) &&
////						historyNoTable.equals(historyNo))){
////					int yukouKigenFromTable = Integer.parseInt(
////							(String) model.getData(i,COLUMUN_YUKOUKIGEN_FROM));
////					int yukouKigenToTable = Integer.parseInt(
////							(String) model.getData(i,COLUMUN_YUKOUKIGEN_TO));
//				// edit s.inoue 2010/02/10
//				if ((yukouKigenFromTable <= Integer.valueOf(yukouKigenFrom) &&
//						 Integer.valueOf(yukouKigenFrom) <= yukouKigenToTable ) ||
//
//					(yukouKigenFromTable <= Integer.valueOf(yukouKigenTo) &&
//						 Integer.valueOf(yukouKigenTo) <= yukouKigenToTable ) ||
//					// edit s.inoue 2010/02/09
//					((yukouKigenFromTable >= Integer.valueOf(yukouKigenFrom)) &&
//						(yukouKigenToTable <= Integer.valueOf(yukouKigenTo)))){
//					retflg = true;
//					break;
//				}
////					if ((yukouKigenFromTable <= Integer.valueOf(yukouKigenFrom) &&
////							 Integer.valueOf(yukouKigenFrom) <= yukouKigenToTable ) ||
////						(yukouKigenFromTable <= Integer.valueOf(yukouKigenTo) &&
////							 Integer.valueOf(yukouKigenTo) <= yukouKigenToTable ) ||
////						((yukouKigenFromTable >= Integer.valueOf(yukouKigenFrom)) &&
////							(yukouKigenFromTable >= Integer.valueOf(yukouKigenFrom)))){
////						retflg = true;
////						break;
////					}
//				}
//			}
//		return retflg;
//	}
//
//	/**
//	 * ORCAボタン
//	 */
//	public void pushedORCAButton() {
//		if (JApplication.useORCA) {
//			JConnection sql = null;
//			try {
//				sql = new JConnection("org.postgresql.Driver", "jdbc:postgresql://"
//						+ JApplication.orcaSetting.getOrcaIpAddress() + ":"
//						+ JApplication.orcaSetting.getOrcaPort() + "/"
//						+ JApplication.orcaSetting.getOrcaDatabase(),
//						JApplication.orcaSetting.getOrcaUser(),
//						JApplication.orcaSetting.getOrcaPassword());
//
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//				/* M1103=エラー,機関データベースへの接続が失敗しました。,0,0 */
//				JErrorMessage.show("M1103", this);
//
//				return;
//			}
//
//			ArrayList<Hashtable<String, String>> result = null;
//			if (validatedData.setHokenjyaNumber(
//					jTextField_HokenjyaNumber.getText())) {
//
//				if (validateAsPushedORCAButton(validatedData)) {
//					String hokenjyaNumber = validatedData.getHokenjyaNumber();
//
//					/* 0 を全て削除する。 */
//					hokenjyaNumber = hokenjyaNumber.replaceAll("^0+", "");
//
//					try {
//						while (hokenjyaNumber.length() <= 8) {
//							result = this.getHokenjaInfo(sql, hokenjyaNumber);
//							if (result.size() != 0) {
//								break;
//							}
//
//							/* 先頭に 0 を追加する。 */
//							hokenjyaNumber = "0" + hokenjyaNumber;
//						}
//
//						if (result.size() != 0) {
//							this.setDbDataToComponent(result);
//							// edit s.inoue 2010/02/19
//							this.jTextField_YukoukigenFrom.grabFocus();
//							// this.jComboBox_ItakuKubun.grabFocus();
//						}
//						else {
//							/* エラー,入力された保険者番号に該当する保険者情報が存在しません。,0,0 */
//							JErrorMessage.show("M3140", this);
//
//							/* 保険者名称入力欄にフォーカスを移動する。 */
//							this.jTextField_HokenjyaName.grabFocus();
//						}
//
//					} catch (SQLException e1) {
//
//						e1.printStackTrace();
//						try {
//							sql.Shutdown();
//						} catch (SQLException e2) {
//							e2.printStackTrace();
//							return;
//						}
//						return;
//					}
//				}
//			}
//
//			try {
//				sql.Shutdown();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//		else {
//			/* 保険者名称入力欄にフォーカスを移動する。 */
//			this.jTextField_HokenjyaName.grabFocus();
//		}
//	}
//
//	private void setDbDataToComponent(ArrayList<Hashtable<String, String>> result) {
//		jTextField_HokenjyaName.setText(result.get(0).get("hknjaname"));
//		jTextField_Zipcode.setText(result.get(0).get("post").trim());
//		jTextField_Address.setText(result.get(0).get("adrs"));
//		jTextField_Chiban.setText(result.get(0).get("banti"));
//		jTextField_Kigo.setText(result.get(0).get("kigo"));
//		jTextField_HonninGairai.setText(result.get(0).get("hon_gaikyurate"));
//		jTextField_HonninNyuin.setText(result.get(0).get("hon_nyukyurate"));
//		jTextField_KazokuGairai.setText(result.get(0).get("kzk_gaikyurate"));
//		jTextField_KazokuNyuin.setText(result.get(0).get("kzk_nyukyurate"));
//	}
//
//	private ArrayList<Hashtable<String, String>> getHokenjaInfo(
//			JConnection sql,
//			String hokenjyaNumber
//			) throws SQLException {
//
//		String numberForSql =
//			JQueryConvert.queryConvert(hokenjyaNumber);
//
//		ArrayList<Hashtable<String, String>> result =
//			sql.sendExecuteQuery(
//				"SELECT * FROM TBL_HKNJAINF WHERE HKNJANUM = "
//						+ numberForSql);
//
//		return result;
//	}
//
//	/**
//	 * 登録ボタン
//	 */
//	public void pushedRegisterButton(ActionEvent e) {
//		register(mode);
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
//	 * キャンセルボタン
//	 */
//	public void pushedCancelButton(ActionEvent e) {
//		RETURN_VALUE Ret = JErrorMessage.show("M3151", this);
//		if (Ret == RETURN_VALUE.YES) {
//			dispose();
//		}
//	}
//
//	/**
//	 * クリアボタン
//	 */
//	public void pushedClearButton(ActionEvent e) {
//		if (mode == ADD_MODE) {
//			this.jTextField_HokenjyaNumber.setText("");
//		}
//		// edit s.inoue 2010/01/21
//		// if (modeYukouKigen != ADD_MODE){
//		if (mode == ADD_MODE ||
//				mode == CHANGE_MODE){
//			this.jTextField_HokenjyaName.setText("");
//			this.jTextField_Zipcode.setText("");
//			this.jTextField_Address.setText("");
//			this.jTextField_Chiban.setText("");
//			this.jTextField_TEL.setText("");
//			this.jTextField_Kigo.setText("");
//			// edit s.inoue 2010/02/19
//			if (mode == ADD_MODE){
//				jTextField_HokenjyaNumber.grabFocus();
//			}else{
//				jTextField_HokenjyaName.grabFocus();
//			}
//		}
//		// edit s.inoue 2010/01/22
//		if(mode == ADD_MODE){
//			this.jTextField_YukoukigenFrom.setText("");
//			this.jTextField_YukoukigenTo.setText("");
//			this.jComboBox_ItakuKubun.setSelectedIndex(0);
//			this.jTextField_KihonTanka.setText("");
//			this.jTextField_HinketsuTanka.setText("");
//			this.jTextField_SindenzuTanka.setText("");
//			this.jTextField_GanteiTanka.setText("");
//			this.jTextField_NingenDocTanka.setText("");
//			this.jRadioButton_Kihon.setSelected(true);
//		}
//	}
//
//	// add s.inoue 2010/01/11
//	public void addNewClearButton() {
//		this.jTextField_YukoukigenFrom.setText("");
//		this.jTextField_YukoukigenTo.setText("");
//		this.jComboBox_ItakuKubun.setSelectedIndex(0);
//		this.jTextField_KihonTanka.setText("");
//		this.jTextField_HinketsuTanka.setText("");
//		this.jTextField_SindenzuTanka.setText("");
//		this.jTextField_GanteiTanka.setText("");
//		this.jTextField_NingenDocTanka.setText("");
//		this.jRadioButton_Kihon.setSelected(true);
//	}
//
//	// add s.inoue 2010/01/14
//	private void checkItakuKubunRecord(ItemEvent e){
//		if(mode == ADD_MODE ||
//				modeYukouKigen == ADD_MODE)
//			return;
//
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//
//			int selectIdx = 0;
//			ArrayList<Integer> selectedRecord = new ArrayList<Integer>();
//
//			selectIdx = jComboBox_ItakuKubun.getSelectedIndex()+1;
//			String historyNo = getmodelColumnNumber(COLUMUN_HISTORY_NO);
//			for (int i = 0; i < model.getRowCount(); i++) {
//				if (model.getData(i, COLUMUN_HISTORY_NO).equals(historyNo) &&
//						model.getData(i, COLUMUN_ITAKU_KBN).equals(String.valueOf(selectIdx))){
//					selectedRecord.add(i);
//					 model.setselectedRows(model, selectedRecord);
//				}
//			}
//
//			// del s.inoue 2010/01/28
////			if (selectedRecord.size() == 0 &&
////					initializeTanka){
////				JErrorMessage.show("M3160", this);
////				jComboBox_ItakuKubun.setSelectedIndex(Integer.parseInt(getmodelColumnNumber(COLUMUN_ITAKU_KBN)) - 1);
////			}
//		}
//	}
//
//	// add s.inoue 2010/01/19
//	private void controlSetting(boolean flg){
//		// テキストコントロール制御
//		// del s.inoue 2010/01/29
//		// this.jTextField_HokenjyaNumber.setEditable(flg);
//		this.jTextField_HokenjyaName.setEditable(flg);
//		this.jTextField_Zipcode.setEditable(flg);
//		this.jTextField_Address.setEditable(flg);
//		this.jTextField_Chiban.setEditable(flg);
//		this.jTextField_TEL.setEditable(flg);
//		this.jTextField_Kigo.setEditable(flg);
//		// edit s.inoue 2010/01/26
//		this.jButton_Clear.setEnabled(flg);
//		this.jButton_Register.setEnabled(flg);
//		// del s.inoue 2010/01/20
//		// jComboBox_ItakuKubun.setEnabled(!flg);
//	}
//
//	// add s.inoue 2010/01/19
//	private void controlSettingData(int pmode){
//		setTankaTable(pmode,jTextField_HokenjyaNumber.getText());
//	}
//
//	// 削除ボタン
//	private void pushedYukoukigenDeleteButton(){
//		logger.info(jButton_RegisterYukoukigenDelete.getText());
//
//		// 確認メッセージ
//		if (model.getSelectedRow() == arrYukou.get(0)){
//			JErrorMessage.show("M3162", this);
//			return;
//		}
//
//		RETURN_VALUE Ret = JErrorMessage.show("M3161", this);
//
//		if (Ret == RETURN_VALUE.NO) {
//			return;
//		}
//
//		// 履歴No,委託区分
//		try {
//			JApplication.kikanDatabase.Transaction();
//			tHokenjyaDao.delete(this.hokenjyaNumber,
//					getmodelColumnNumber(COLUMUN_HISTORY_NO),
//					getmodelColumnNumber(COLUMUN_ITAKU_KBN));
//			JApplication.kikanDatabase.Commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//
//		// edit s.inoue 2010/01/21
//		searchReflesh(this.hokenjyaNumber);
//	}
//
//	// 更新ボタン
//	private void pushedYukoukigenEditButton(){
//		logger.info(jButton_RegisterYukoukigenEdit.getText());
//		modeYukouKigen = CHANGE_MODE;
//		register(CHANGE_MODE_TANKA);
//	}
//
//	// del s.inoue 2010/02/02
////	// 新規追加ボタン
////	private void pushedYukoukigenButton(){
////		logger.info(jButton_RegisterYukoukigen.getText());
////		register(ADD_MODE_TANKA);
////	}
//
//	// 新規追加ボタン
//	private void pushedAddButton(){
//		logger.info(jButton_Add.getText());
//		modeYukouKigen = ADD_MODE;
//		// edit s.inoue 2010/01/21
//		initilizeFocus();
//		// add s.inoue 2010/01/19
//		controlSettingData(modeYukouKigen);
//
//		// add s.inoue 2010/01/19
//		// テキストコントロール制御
//		controlSetting(false);
//
//		addNewClearButton();
//		// del s.inoue 2010/02/02
////		jButton_RegisterYukoukigen.setVisible(true);
////		jButton_RegisterYukoukigenEdit.setVisible(false);
//		this.jTextField_YukoukigenFrom.grabFocus();
//	}
//
//
//	public void actionPerformed(ActionEvent e) {
//		Object source = e.getSource();
//		if(source == jButton_Add){
//			logger.info(jButton_Add.getText());
//			pushedAddButton();
//		// del s.inoue 2010/02/02
////		}else if (source == jButton_RegisterYukoukigen){
////			pushedYukoukigenButton();
//		}else if (source == jButton_RegisterYukoukigenEdit){
//			logger.info(jButton_RegisterYukoukigenEdit.getText());
//			pushedYukoukigenEditButton();
//		}else if (source == jButton_RegisterYukoukigenDelete){
//			logger.info(jButton_RegisterYukoukigenDelete.getText());
//			pushedYukoukigenDeleteButton();
//		}else if (source == jButton_End) {
//			logger.info(jButton_End.getText());
//			pushedEndButton(e);
//		}
//		else if (source == jButton_ORCA) {
//			logger.info(jButton_ORCA.getText());
//			pushedORCAButton();
//		}
//		else if (source == jButton_Register ||
//				source == jButton_RegisterAdd) {
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton(e);
//		}
//		else if (source == jTextField_HokenjyaNumber) {
//			logger.info(jTextField_HokenjyaNumber.getText());
//			enterKeyPushedHokenjyaNumber();
//		}
//		else if (source == jButton_Clear ||
//				source == jButton_ClearAdd) {
//			logger.info(jButton_Clear.getText());
//			pushedClearButton(e);
//		}
//// del s.inoue 2010/01/14
////		}else if (source == jButton_RegisterYukoukigenDelete){
////			logger.info(jButton_RegisterYukoukigenDelete);
////			String[] messageParams = { model.getData(model.getSelectedRow(),1) + "-" + model.getData(model.getSelectedRow(),2) };
////			RETURN_VALUE Ret = JErrorMessage.show("M3157", this, messageParams);
////
////			if (Ret == RETURN_VALUE.YES) {
////				deleteYukouKigen();
////				searchReflesh(this.hokenjyaNumber);
////			}
//	}
//
//	// edit s.inoue 2010/04/05
//	@Override
//	public void focusLost(FocusEvent event) {
//		Object source = event.getSource();
//
//		// add s.inoue 2010/03/29
//		if(source.equals(this.jTextField_Zipcode)){
//			if (jTextField_Zipcode.getText().equals(""))
//				return;
//
//			String zipText = setZipCodeAddress(this.jTextField_Zipcode.getText());
//			if (zipText.equals(""))
//				return;
//
//			jTextField_Address.setText(zipText);
//		}
//	}
//
//	// edit s.inoue 2010/04/05
//	private String setZipCodeAddress(String zipCode){
//
//		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
//		Hashtable<String, String> resultItem = new Hashtable<String, String>();
//
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("SELECT ADDRESS FROM T_POST,");
//		sb.append("(SELECT SUBSTRING(POST_CD FROM 1 FOR 3) || SUBSTRING(POST_CD FROM 5 FOR 4) POSTCD,");
//		sb.append(" POST_CD SP_POSTCD FROM T_POST) SUB_POST");
//		sb.append(" where SUB_POST.POSTCD = ");
//		sb.append(JQueryConvert.queryConvert(zipCode));
//		sb.append(" and SUB_POST.SP_POSTCD = T_POST.POST_CD ");
//
//		try {
//			result = JApplication.systemDatabase.sendExecuteQuery(sb.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		if (result.size() <= 0)
//			return "";
//
//		resultItem = result.get(0);
//		return resultItem.get("ADDRESS");
//
//	}
//
//	// add s.inoue 2009/10/01
//	// ラジオボタンイベント
//	public void itemStateChanged(ItemEvent e) {
//		Object source = e.getSource();
//
//		if (source == jRadioButton_Kihon ||
//				source == jRadioButton_Doc) {
//			radioItemChangeColor();
//			// edit s.inoue 2010/01/21
//			initilizeFocus();
//		}
//		if (source == jRadioButton_Kihon) {
//			changedKihonState(e);
//		}
//		else if (source == jRadioButton_Doc) {
//			changedDocState(e);
//		}
//		else if (source == jComboBox_ItakuKubun) {
//			checkItakuKubunRecord(e);
//		}
//	}
//	/**
//	 * 基本のラジオボタンが押された場合
//	 */
//	public void changedKihonState(ItemEvent e) {
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			jTextField_TankaHantei.setText("1");
//		} else {
//			// del s.inoue 2010/01/14
//			// clearJTextfield_Tanka(false);
//		}
//	}
//
//	/**
//	 * 人間ドックのラジオボタンが押された場合
//	 */
//	public void changedDocState(ItemEvent e) {
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			jTextField_TankaHantei.setText("2");
//		} else {
//			// del s.inoue 2010/01/14
//			// clearJTextfield_Tanka(true);
//		}
//	}
//
//	// add s.inoue 2009/10/01
//	private void radioItemChangeColor(){
//		Color kihonItem = null;
//		Color docItem = null;
//		boolean selectedItem = false;
//
//		if (jRadioButton_Kihon.isSelected()) {
//			kihonItem = ViewSettings.getRequiedItemBgColor();
//			// edit s.inoue 2010/01/30
//			// docItem = ViewSettings.getDisableItemBgColor();
//			docItem = Color.lightGray;
//
//			selectedItem = true;
//		}else if(jRadioButton_Doc.isSelected()){
//			// edit s.inoue 2010/01/30
//			// kihonItem = ViewSettings.getDisableItemBgColor();
//			kihonItem = Color.lightGray;
//			docItem = ViewSettings.getRequiedItemBgColor();
//
//			selectedItem = false;
//		}else{
//			return;
//		}
//
//		this.jTextField_KihonTanka.setEditable(selectedItem);
//		this.jTextField_SindenzuTanka.setEditable(selectedItem);
//		this.jTextField_GanteiTanka.setEditable(selectedItem);
//		this.jTextField_HinketsuTanka.setEditable(selectedItem);
//		this.jTextField_NingenDocTanka.setEditable(!selectedItem);
//
//		// 入力制御
////		this.jTextField_KihonTanka.setEnabled(selectedItem);
////		this.jTextField_SindenzuTanka.setEnabled(selectedItem);
////		this.jTextField_GanteiTanka.setEnabled(selectedItem);
////		this.jTextField_HinketsuTanka.setEnabled(selectedItem);
////		this.jTextField_NingenDocTanka.setEnabled(!selectedItem);
//
//		// 背景制御
//		// add s.inoue 2010/01/20
//		this.jTextField_YukoukigenFrom.setBackground(ViewSettings.getRequiedItemBgColor());
//		this.jTextField_YukoukigenTo.setBackground(ViewSettings.getRequiedItemBgColor());
//
//// edit s.inoue 2010/01/30
//		this.jTextField_KihonTanka.setBackground(kihonItem);
//		this.jTextField_SindenzuTanka.setBackground(kihonItem);
//		this.jTextField_GanteiTanka.setBackground(kihonItem);
//		this.jTextField_HinketsuTanka.setBackground(kihonItem);
//		this.jTextField_NingenDocTanka.setBackground(docItem);
//	}
//
//	// add s.inoue 2009/10/01
//	@Override
//	public void keyTyped(KeyEvent e) {
//		Object source = e.getSource();
//		char keyChar = e.getKeyChar();
//
//		/* 基本ラジオボタン */
//		if (source == this.jRadioButton_Kihon) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Kihon.setSelected(true);
//			}
//		/* Docラジオボタン */
//		} else if (source == this.jRadioButton_Doc) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Doc.setSelected(true);
//			}
//		/* 性別入力欄 */
//		} else if (source == this.jTextField_TankaHantei) {
//
//			if (keyChar == KeyEvent.VK_1) {
//				this.jTextField_TankaHantei.setText(TANKA_HANTEI_KIHON);
//				this.jRadioButton_Kihon.setSelected(true);
//			} else if (keyChar == KeyEvent.VK_2) {
//				// edit s.inoue 2010/01/20
//				// this.jRadioButton_Doc.setText("2");
//				this.jTextField_TankaHantei.setText(TANKA_HANTEI_DOC);
//				this.jRadioButton_Doc.setSelected(true);
//			} else {
//				String currentValue = this.jTextField_TankaHantei.getText();
//				if (!currentValue.equals(TANKA_HANTEI_KIHON) &&
//						!currentValue.equals(TANKA_HANTEI_DOC)) {
//					this.groupHantei.clearSelection();
//				}
//			}
//		}
//	}
//
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);
//			break;
//		case KeyEvent.VK_F2:
//			logger.info(jButton_Clear.getText());
//			pushedClearButton(null);
//			break;
//		case KeyEvent.VK_F7:
//			if(jButton_Add.isVisible()){
//				logger.info(jButton_Add.getText());
//				pushedAddButton();
//			}
//			break;
//		case KeyEvent.VK_F9:
//			if(jButton_RegisterYukoukigenDelete.isVisible()){
//				logger.info(jButton_RegisterYukoukigenDelete.getText());
//				pushedYukoukigenDeleteButton();
//			}
//			break;
//		case KeyEvent.VK_F12:
//			if(jButton_RegisterYukoukigenEdit.isVisible()){
//				logger.info(jButton_RegisterYukoukigenEdit.getText());
//				pushedYukoukigenEditButton();
//			// edit s.inoue 2010/04/14
//			}else if (jButton_RegisterAdd.isVisible() && (mode == ADD_MODE)){
//				logger.info(jButton_RegisterAdd.getText());
//				register(mode);
//			}
//			break;
//		case KeyEvent.VK_F11:
//			// edit s.inoue 2010/04/14
//			if (jButton_Register.isVisible()){
//				logger.info(jButton_Register.getText());
//				register(mode);
//			}
//			break;
//		}
//	}
//}
//// edit s.inoue 2010/02/02
////package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
////
////import java.awt.Color;
////import java.awt.Component;
////import java.awt.Dimension;
////import java.awt.event.*;
////import java.lang.reflect.InvocationTargetException;
////import java.sql.SQLException;
////import java.text.NumberFormat;
////import java.util.*;
////
////import javax.swing.ButtonGroup;
////import javax.swing.JCheckBox;
////import javax.swing.JRadioButton;
////import javax.swing.JScrollPane;
////import javax.swing.JTable;
////import javax.swing.JViewport;
////import javax.swing.ListSelectionModel;
////import javax.swing.event.ListSelectionEvent;
////import javax.swing.event.ListSelectionListener;
////import javax.swing.table.DefaultTableCellRenderer;
////import javax.swing.table.DefaultTableModel;
////import javax.swing.table.TableColumnModel;
////import javax.swing.table.TableModel;
////import javax.swing.table.TableRowSorter;
////
////import org.apache.log4j.Logger;
////
////import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
////import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
////import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
////import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
////import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
////import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
////import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
////import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
////import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
////import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
////import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
////import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
////import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
////import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
////import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
////import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
////import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
////import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
////import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
////import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
////import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
////import jp.or.med.orca.jma_tokutei.common.app.JApplication;
////
/////**
//// * 保険者情報の編集のコントロール
//// */
////public class JHokenjyaMasterMaintenanceEditFrameCtrl extends
////		JHokenjyaMasterMaintenanceEditFrame {
////
////	private JHokenjyaMasterMaintenanceEditFrameData validatedData = new JHokenjyaMasterMaintenanceEditFrameData();  //  @jve:decl-index=0:
////	private static org.apache.log4j.Logger logger =	Logger.getLogger(JHokenjyaMasterMaintenanceEditFrameCtrl.class);
////	private int mode;
////	private static final int ADD_MODE = 1;
////	private static final int CHANGE_MODE = 2;
////	private static final int ADD_MODE_TANKA = 3;
////	private static final int CHANGE_MODE_TANKA = 4;
////
////	// add s.inoue 2010/01/12
////	private int modeYukouKigen = 0;
////
////	// add s.inoue 2010/01/20
////	private boolean initializeTanka = false;
////
////	// edit s.inoue 2009/10/01
////	private String[] itakuKubunStr = { "1:個別", "2:集団" };
////	private JSimpleTable model = new JSimpleTable();
////	private DefaultTableModel dmodel = null;
////	private TableRowSorter<TableModel> sorter =null;
////	private ArrayList<Hashtable<String, String>> result;
////
////	/* フォーカス移動制御 */
////	private JFocusTraversalPolicy focusTraversalPolicy = null;
////
////	// edit s.inoue 2010/01/29
////	private String[] header = {
////		"有効/無効","有効期限開始","有効期限終了","履歴番号","委託単価区分","基本的な健診","貧血検査","心電図検査","眼底検査","人間ドック" };
////	// 検索ボタン押下時のSQLで使用
////	private static final String[] TABLE_COLUMNS = {
////		"YUKOU_FLG","HKNJYA_LIMITDATE_START","HKNJYA_LIMITDATE_END","HKNJYA_HISTORY_NO","ITAKU_KBN",
////		"TANKA_KIHON","TANKA_HINKETU","TANKA_SINDENZU","TANKA_GANTEI","TANKA_NINGENDOC" };
////	Object[][] rowcolumn = null;
////
////	private ExtendedCheckBox JRadioYukou = new ExtendedCheckBox();
////	private THokenjyaDao tHokenjyaDao = null;
////
////	// add s.inoue 2010/01/13
////	private ButtonGroup radioButtonGroup = new ButtonGroup();
////	private String hokenjyaNumber =  "";
////	// add s.inoue 2010/01/13
////	ArrayList<Integer> arrYukou = new ArrayList<Integer>();
////	// del s.inoue 2010/01/14
////	// private ExtendedCheckBox checkedTable = new ExtendedCheckBox();
////
////	// add s.inoue 2010/01/15
////	private static final String TANKA_HANTEI_KIHON = "1";
////	private static final String TANKA_HANTEI_DOC = "2";
////
////	private static final int COLUMUN_HISTORY_NO = 3;
////	private static final int COLUMUN_ITAKU_KBN = 4;
////
////	/**
////	 * 保険者情報追加の場合のコンストラクタ（遷移元：マスタメンテナンス）
////	 */
////	public JHokenjyaMasterMaintenanceEditFrameCtrl() {
////		super();
////
////		mode = ADD_MODE;
////		init();
////
////		// jPanel11.add(new JSimpleTableScrollPanel(model));
////
////		// add s.inoue 2010/01/12
////		initilizeTableSettings(hokenjyaNumber);
////		functionListner();
////
////		jTextField_HokenjyaNumber.setBackground(ViewSettings.getRequiedItemBgColor());
////		this.jButton_Add.setVisible(false);
////		this.jButton_RegisterYukoukigen.setVisible(false);
////		this.jButton_RegisterYukoukigenEdit.setVisible(false);
////		// edit s.inoue 2010/01/21
////		this.jButton_RegisterYukoukigenDelete.setVisible(false);
////		setModeAppearSettings(true);
////	}
////
////	/**
////	 * 保険者情報追加の場合のコンストラクタ（遷移元：個人情報登録フレーム）
////	 */
////	public JHokenjyaMasterMaintenanceEditFrameCtrl(
////			JKojinRegisterFrameData ptData) {
////		super();
////
////		mode = ADD_MODE;
////		init();
////
////		// del s.inoue 2010/01/21
////		// jPanel11.add(new JSimpleTableScrollPanel(model));
////		// add s.inoue 2010/01/12
////		initilizeTableSettings(hokenjyaNumber);
////
////		functionListner();
////
////		jTextField_HokenjyaNumber.setText(ptData.getHokenjyaNumber());
////		jTextField_HokenjyaNumber.setBackground(ViewSettings.getRequiedItemBgColor());
////
////		this.jButton_ORCA.setVisible(false);
////		this.jButton_Add.setVisible(false);
////		this.jButton_RegisterYukoukigen.setVisible(false);
////		this.jButton_RegisterYukoukigenEdit.setVisible(false);
////		// edit s.inoue 2010/01/21
////		this.jButton_RegisterYukoukigenDelete.setVisible(false);
////		// add s.inoue 2010/01/20
////		initializeTanka = true;
////		setModeAppearSettings(true);
////	}
////
////	// add s.inoue 2010/01/22
////	private void setModeAppearSettings(boolean pflg){
////		this.jButton_ClearAdd.setVisible(pflg);
////		this.jButton_RegisterAdd.setVisible(pflg);
////		this.jButton_Register.setVisible(!pflg);
////		this.jButton_Clear.setVisible(!pflg);
////	}
////	/**
////	 * 保険者情報変更の場合のコンストラクタ（遷移元：マスタメンテナンス）
////	 */
////	public JHokenjyaMasterMaintenanceEditFrameCtrl(String hokenjyaNumber) {
////
////		super();
////
////		mode = CHANGE_MODE;
////		init();
////
////		// add s.inoue 2010/01/12
////		initilizeTableSettings(hokenjyaNumber);
////
////		// 遷移元フレームから得た保険者番号から既存のデータを取得
////		jTextField_HokenjyaNumber.setText(hokenjyaNumber);
////		jTextField_HokenjyaNumber.setEditable(false);
////
////		// add s.inoue 2010/01/14
////		setTankaTable(hokenjyaNumber);
////
////		functionListner();
////
////		this.jTextField_HokenjyaNumber.addKeyListener(new KeyAdapter(){
////
////			@Override
////			public void keyPressed(KeyEvent arg0) {
////				String number =
////					JHokenjyaMasterMaintenanceEditFrameCtrl.
////					this.jTextField_HokenjyaNumber.getText();
////
////				if (number == null || number.isEmpty()) {
////					JHokenjyaMasterMaintenanceEditFrameCtrl.this.jButton_ORCA.setEnabled(false);
////				}
////			}
////		});
////
////		this.jButton_ORCA.setVisible(false);
////		// add s.inoue 2010/01/20
////		initializeTanka = true;
////
////		// add s.inoue 2010/01/21
////		initilizeFocus();
////		setModeAppearSettings(false);
////	}
////
////	// add s.inoue 2010/01/14
////	private void initilizeTableSettings(String hokenjyaNumber){
////
////		// edit s.inoue 2010/01/21
////		initilizeFocus();
////
////		// edit s.inoue 2010/01/14
////		this.hokenjyaNumber = hokenjyaNumber;
////
////		/* T_HOKENJYA Dao を作成する。 */
////		try {
////			tHokenjyaDao = (THokenjyaDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), new THokenjya());
////		} catch (Exception ex) {
////			logger.error(ex.getMessage());
////		}
////
////		// edit s.inoue 2010/01/08
////		if (mode == CHANGE_MODE){
////			initializeSetting(hokenjyaNumber);
////
////			// add s.inoue 2010/01/11
////			model.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
////				public void valueChanged(ListSelectionEvent e) {
////					if(e.getValueIsAdjusting()) return;
////
////					int irow = model.getSelectedRow();
////					if (irow < 0) return;
////
////					// edit s.inoue 2010/01/12
////					jButton_RegisterYukoukigenEdit.setVisible(true);
////					jButton_RegisterYukoukigen.setVisible(false);
////
////					// edit s.inoue 2010/01/26
////					jButton_Clear.setEnabled(true);
////					jButton_Register.setEnabled(true);
////					jTextField_HokenjyaName.setEditable(true);
////					jTextField_Zipcode.setEditable(true);
////					jTextField_Address.setEditable(true);
////					jTextField_Chiban.setEditable(true);
////					jTextField_TEL.setEditable(true);
////					jTextField_Kigo.setEditable(true);
////
////					// 選択行を入力フィールドへ反映
////					jTextField_YukoukigenFrom.setText(String.valueOf(model.getValueAt(irow, 1)));
////					jTextField_YukoukigenTo.setText(String.valueOf(model.getValueAt(irow, 2)));
////					jComboBox_ItakuKubun.setSelectedIndex(Integer.parseInt((String) model.getValueAt(irow, 4))-1);
////					// del s.inoue 2010/01/20
////					// jComboBox_ItakuKubun.setEditable(false);
////
////					jTextField_KihonTanka.setText(String.valueOf(model.getValueAt(irow, 5)).replaceAll(",", ""));
////					jTextField_HinketsuTanka.setText(String.valueOf(model.getValueAt(irow, 6)).replaceAll(",", ""));
////					jTextField_SindenzuTanka.setText(String.valueOf(model.getValueAt(irow, 7)).replaceAll(",", ""));
////					jTextField_GanteiTanka.setText(String.valueOf(model.getValueAt(irow, 8)).replaceAll(",", ""));
////					jTextField_NingenDocTanka.setText(String.valueOf(model.getValueAt(irow, 9)).replaceAll(",", ""));
////
////				}
////			});
////		}
////	}
////
////	// add s.inoue 2010/01/14
////	/* 単価コントロール表示処理 */
////	private void setTankaTable(String hokenjyaNumber){
////
////		ArrayList<Hashtable<String, String>> result
////		= new ArrayList<Hashtable<String, String>>();
////
////		Hashtable<String, String> resultItem = new Hashtable<String, String>();
////		StringBuilder sb = new StringBuilder();
////		sb.append("SELECT * FROM T_HOKENJYA ");
////		sb.append(" WHERE HKNJANUM = ");
////		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
////		sb.append(" AND HKNJYA_HISTORY_NO = ");
////		sb.append(JQueryConvert.queryConvert(getmodelColumnNumber(COLUMUN_HISTORY_NO)));
////		sb.append(" AND ITAKU_KBN = ");
////		sb.append(JQueryConvert.queryConvert(getmodelColumnNumber(COLUMUN_ITAKU_KBN)));
////
////		try {
////			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
////		} catch (SQLException e) {
////			e.printStackTrace();
////		}
////
////		// edit s.inoue 2010/01/13
////		if (arrYukou.size() <= 0){
////			JErrorMessage.show("M3158", null);
////			return;
////		}
////
////		// edit s.inoue 2010/01/15
////		// resultItem = result.get(arrYukou.get(0));
////		resultItem = result.get(0);
////
////		jTextField_HokenjyaName.setText(resultItem.get("HKNJANAME"));
////		jTextField_Zipcode.setText(resultItem.get("POST").trim());
////		jTextField_Address.setText(resultItem.get("ADRS"));
////		jTextField_Chiban.setText(resultItem.get("BANTI"));
////		jTextField_TEL.setText(resultItem.get("TEL"));
////		jTextField_Kigo.setText(resultItem.get("KIGO"));
////		// add s.inoue 2010/01/14
////		jTextField_YukoukigenFrom.setText(resultItem.get("HKNJYA_LIMITDATE_START"));
////		jTextField_YukoukigenTo.setText(resultItem.get("HKNJYA_LIMITDATE_END"));
////
////		if (!resultItem.get("ITAKU_KBN").isEmpty()) {
////			jComboBox_ItakuKubun.setSelectedIndex(Integer.valueOf(resultItem.get("ITAKU_KBN")) - 1);
////		}
////
////		// del s.inoue 2010/01/20
////		// 1:基本、2:ドック
////		String hani = resultItem.get("TANKA_HANTEI");
////		hani = (hani == null)?TANKA_HANTEI_KIHON: hani;
////
////		// edit s.inoue 2010/01/20
////		if(hani.equals(TANKA_HANTEI_KIHON)){
////			jRadioButton_Kihon.setSelected(true);
////		}else if (hani.equals(TANKA_HANTEI_DOC)){
////			jRadioButton_Doc.setSelected(true);
////		}
////
////		// edit s.inoue 2010/01/20
////		jTextField_NingenDocTanka.setText(resultItem.get("TANKA_NINGENDOC"));
////		// 基本健診のみ設定
////		jTextField_KihonTanka.setText(resultItem.get("TANKA_KIHON"));
////		jTextField_HinketuCode.setText(resultItem.get("HINKETU_CD"));
////		jTextField_SindenzuCode.setText(resultItem.get("SINDENZU_CD"));
////		jTextField_GanteiCode.setText(resultItem.get("GANTEI_CD"));
////		jTextField_HinketsuTanka.setText(resultItem.get("TANKA_HINKETU"));
////		jTextField_SindenzuTanka.setText(resultItem.get("TANKA_SINDENZU"));
////		jTextField_GanteiTanka.setText(resultItem.get("TANKA_GANTEI"));
////		jTextField_HonninGairai.setText(resultItem.get("HON_GAIKYURATE"));
////		jTextField_HonninNyuin.setText(resultItem.get("HON_NYUKYURATE"));
////		jTextField_KazokuGairai.setText(resultItem.get("KZK_GAIKYURATE"));
////		jTextField_KazokuNyuin.setText(resultItem.get("KZK_NYUKYURATE"));
////	}
////
////	/* 単価テーブル一覧表示処理 */
////	private void initializeSetting(String hokenjyaNumber){
////
////		// edit s.inoue 2010/01/14
////		Object[][] hokenjyaData = null;
////		hokenjyaData = this.resultRefresh(hokenjyaNumber);
////		dmodel = new DefaultTableModel(hokenjyaData,header){
////		public boolean isCellEditable(int row, int column) {
////			boolean retflg = false;
////			if ( column >9 ){
////				retflg = true;
////			}
////			return retflg;
////			}
////		};
////
////		sorter = new TableRowSorter<TableModel>(dmodel);
////		model = new JSimpleTable(dmodel);
////		model.setPreferedColumnWidths(new int[]{70,80,80,70,80,120,100,110,110,110});
////		model.setRowSorter(sorter);
////		model.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
////		model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
////
////		final JScrollPane scroll = new JScrollPane(model);
////		JViewport viewport = new JViewport();
////		scroll.setRowHeader(viewport);
////		// edit s.inoue 2010/01/26
////		// scroll.setPreferredSize(new Dimension(963, 130));
////		scroll.setPreferredSize(new Dimension(950, 115));
////		if(mode == CHANGE_MODE)
////		dmodel.setDataVector(hokenjyaData,header);
////
////		jPanel_TableArea.add(scroll);
////
////		TableColumnModel columns = model.getColumnModel();
////		for(int i=0;i<columns.getColumnCount();i++) {
////			columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
////					(DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
////		}
////
////		// edit s.inoue 2010/01/13
////		// JRadioYukou.addItemListener(this);
////		JRadioYukou.addKeyListener(this);
////
////		model.setCellRadioButtonEditor(JRadioYukou,0);
////		radioButtonGroup.add(JRadioYukou);
////
////		// del s.inoue 2010/01/14
////		// checkedTable.addKeyListener(this);
////		// model.setCellCheckBoxEditor(checkedTable, 1);
////
////		model.setselectedRows(model, arrYukou);
////		model.addHeader(this.header);
////		model.refreshTable();
////
////	}
////
////	// add s.inoue 2010/01/14
////	/* 一覧リフレッシュ処理 */
////	private void searchReflesh(String hokenjyaNumber){
////
////		Object[][] hokenjyaData = this.resultRefresh(hokenjyaNumber);
////		dmodel.setDataVector(hokenjyaData,header);
////
////		TableColumnModel columns = model.getColumnModel();
////		for(int i=0;i<columns.getColumnCount();i++) {
////			columns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
////					(DefaultTableCellRenderer)model.getDefaultRenderer(model.getColumnClass(i))));
////		}
////
////		JRadioYukou.addItemListener(this);
////		model.setCellRadioButtonEditor(JRadioYukou,0);
////		radioButtonGroup.add(JRadioYukou);
////		model.setselectedRows(model, arrYukou);
////		model.addHeader(this.header);
////		model.refreshTable();
////	}
////
////	// add s.inoue 2010/01/15
////	/* 一覧用データ取得 */
////	private Object[][] resultRefresh(String hokenjyaNumber)
////	{
////		// edit s.inoue 2010/01/08
////		StringBuilder sb = new StringBuilder();
////		sb.append(" SELECT distinct ");
////		sb.append(" YUKOU_FLG,HKNJYA_LIMITDATE_START,HKNJYA_LIMITDATE_END,HKNJYA_HISTORY_NO,ITAKU_KBN,TANKA_KIHON,");
////		sb.append(" TANKA_HINKETU,TANKA_SINDENZU,TANKA_GANTEI,TANKA_NINGENDOC");
////		sb.append(" FROM T_HOKENJYA ");
////		sb.append(" WHERE HKNJANUM = ");
////		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
////		sb.append(" ORDER BY HKNJYA_HISTORY_NO DESC,ITAKU_KBN ");
////
////		try {
////			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
////		} catch (SQLException e) {
////			e.printStackTrace();
////			return null;
////		}
////
////		rowcolumn = new Object[result.size()][16];
////		int counti = 0;
////		List<String> fields = new ArrayList<String>();
////
////		for (Hashtable<String, String> record : result) {
////			for (String fieldName : TABLE_COLUMNS) {
////				fields.add(record.get(fieldName));
////			}
////
////			// edit s.inoue 2010/01/13
////			model.addData(fields.toArray(new String[0]));
////
////			String[] col = fields.toArray(new String[0]);
////			for (int i = 0; i < col.length; i++) {
////
////				Object val = col[i]==null?"":col[i];
////					if (i == 0){
////						// edit s.inoue 2010/01/13
////						val = new JRadioButton(String.valueOf(i));
////						if (col[i].equals("1")){
////							// add s.inoue 2010/01/21
////							if (arrYukou.size() > 0){
////								arrYukou.remove(0);
////							}
////							arrYukou.add(counti);
////						}
////					}else if ((i==5 || i==6 || i==7|| i==8 || i==9) && !(val.equals(""))){
////							NumberFormat kingakuFormat = NumberFormat.getNumberInstance();
////							val = kingakuFormat.format(Long.parseLong(String.valueOf(val)));
////					}
////					rowcolumn[counti][i] = val;
////				}
////
////			counti ++;
////
////			fields.clear();
////		}
////
////		return rowcolumn;
////	}
////
////	/**
////	 * 印刷時の必須項目をチェックする
////	 */
////	public boolean validateAsPushedORCAButton(
////			JHokenjyaMasterMaintenanceEditFrameData data) {
////		if (data.getHokenjyaNumber().isEmpty()) {
////			return false;
////		}
////
////		return true;
////	}
////
////	/**
////	 * 初期化
////	 */
////	private void init() {
////		jComboBox_ItakuKubun.addItem(itakuKubunStr[0]);
////		jComboBox_ItakuKubun.addItem(itakuKubunStr[1]);
////		// del s.inoue 2010/01/19
////		// if ((modeYukouKigen == CHANGE_MODE) ||
////		// 		(mode == CHANGE_MODE))
////		// jComboBox_ItakuKubun.setEnabled(false);
////
////		jTextField_HinketuCode.setText("1");
////		jTextField_SindenzuCode.setText("2");
////		jTextField_GanteiCode.setText("3");
////
////		// add s.inoue 2009/10/01
////		jRadioButton_Kihon.setSelected(true);
////
////		this.jTextField_KihonTanka.setBackground(ViewSettings.getRequiedItemBgColor());
////
////		// edit s.inoue 2010/01/21
////		initilizeFocus();
////	}
////
////	/* focus初期化 */
////	private void initilizeFocus(){
////		// focus制御
////		this.focusTraversalPolicy = new JFocusTraversalPolicy();
////		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
////		// edit s.inoue 2009/12/02
////		if(mode == ADD_MODE){
////			this.focusTraversalPolicy.setDefaultComponent(jTextField_HokenjyaNumber);
////			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		}else if (mode == CHANGE_MODE){
////			this.focusTraversalPolicy.setDefaultComponent(jTextField_HokenjyaName);
////		}
////		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		this.focusTraversalPolicy.addComponent(jButton_ORCA);
////		this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
////		this.focusTraversalPolicy.addComponent(jTextField_Zipcode);
////		this.focusTraversalPolicy.addComponent(jTextField_TEL);
////		this.focusTraversalPolicy.addComponent(jTextField_Address);
////		this.focusTraversalPolicy.addComponent(jTextField_Chiban);
////		this.focusTraversalPolicy.addComponent(jTextField_Kigo);
////		// edit s.inoue 2010/01/22
////		if(mode == CHANGE_MODE){
////			this.focusTraversalPolicy.addComponent(jButton_Register);
////			this.focusTraversalPolicy.addComponent(jButton_Clear);
////		}
////		// edit s.inoue 2010/01/21
////		if(mode == CHANGE_MODE)
////			this.focusTraversalPolicy.addComponent(this.model);
////
////		this.focusTraversalPolicy.addComponent(jTextField_YukoukigenFrom);
////		this.focusTraversalPolicy.addComponent(jTextField_YukoukigenTo);
////		this.focusTraversalPolicy.addComponent(jTextField_TankaHantei);
////		this.focusTraversalPolicy.addComponent(jComboBox_ItakuKubun);
////		// edit s.inoue 2010/01/21
////		if (jRadioButton_Doc.isSelected()){
////			this.focusTraversalPolicy.addComponent(jTextField_NingenDocTanka);
////		}else{
////			this.focusTraversalPolicy.addComponent(jTextField_KihonTanka);
////			this.focusTraversalPolicy.addComponent(jTextField_HinketsuTanka);
////			this.focusTraversalPolicy.addComponent(jTextField_SindenzuTanka);
////			this.focusTraversalPolicy.addComponent(jTextField_GanteiTanka);
////		}
////
////		// edit s.inoue 2010/01/21
////		if (mode != ADD_MODE){
////			this.focusTraversalPolicy.addComponent(jButton_Add);
////			if (modeYukouKigen == ADD_MODE){
////				this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigen);
////			}else{
////				this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigenEdit);
////			}
////			this.focusTraversalPolicy.addComponent(jButton_RegisterYukoukigenDelete);
////		}else{
////			this.focusTraversalPolicy.addComponent(jButton_ClearAdd);
////			this.focusTraversalPolicy.addComponent(jButton_RegisterAdd);
////		}
////
////		this.focusTraversalPolicy.addComponent(jButton_End);
////
////		// edit s.inoue 2010/01/21
////		if(mode == ADD_MODE){
////			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaNumber);
////		}else if (mode == CHANGE_MODE){
////			this.focusTraversalPolicy.addComponent(jTextField_HokenjyaName);
////		}
////	}
////
////	private void functionListner(){
////		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
////			Component comp = focusTraversalPolicy.getComponent(i);
////			comp.addKeyListener(this);
////		}
////	}
////
////	/**
////	 * 保険者番号が入力された場合
////	 */
////	public void enterKeyPushedHokenjyaNumber() {
////		/* 保険者番号が未入力の場合は、保険者名称欄にフォーカスを移動する。 */
////		String hokenjaNumber = this.jTextField_HokenjyaNumber.getText();
////
////		if (hokenjaNumber == null || hokenjaNumber.isEmpty()) {
////			this.jTextField_HokenjyaName.grabFocus();
////		}
////
////		if (validatedData.setHokenjyaNumber(hokenjaNumber)) {
////
////			pushedORCAButton();
////		}
////	}
////
////	/**
////	 * 登録ボタンを押した際の必須項目の検証
////	 */
////	protected boolean validateAsRegisterPushed(int pmode,
////			JHokenjyaMasterMaintenanceEditFrameData data) {
////		if (pmode == ADD_MODE ||
////				pmode == CHANGE_MODE){
////			if (data.getHokenjyaNumber().equals("")) {
////				JErrorMessage.show(
////						"M3120", null);
////				return false;
////			}
////		}else{
////			// add s.inoue 2010/01/15
////			// 有効期限必須
////			if (data.getYukouKigenFrom().equals("")){
////				JErrorMessage.show(
////						"M3125", null);
////				return false;
////			}
////			if (data.getYukouKigenTo().equals("")){
////				JErrorMessage.show(
////						"M3126", null);
////				return false;
////			}
////			// 有効期限の範囲チェック
////			if(Integer.parseInt(data.getYukouKigenFrom()) > Integer.parseInt(data.getYukouKigenTo())){
////				JErrorMessage.show("M3159", null);
////				return false;
////			}
////
////			// 人間ドック単価又は基本単価必須
////			if (data.getKihonTanka().equals("")
////					&& data.getNingenDocTanka().equals("")) {
////				JErrorMessage.show("M3125", null);
////				return false;
////			}
////		}
////		validatedData.setValidationAsDataSet();
////		return true;
////	}
////
////	private static String lastAddedNumber = null;
////
////	public static String getLastAddedNumber() {
////		return lastAddedNumber;
////	}
////
////	private static boolean registered = false;
////	public static boolean isRegistered() {
////		return registered;
////	}
////
////	/**
////	 * 登録処理を行う
////	 */
////	public void register(int pmode) {
////		if (!this.validateData(pmode)) {
////			return;
////		}
////
////		// 各フィールドについて検証済み
////		if (!validateAsRegisterPushed(pmode,validatedData)) {
////			return;
////		}
////
////		// 必須項目についても抜けがない
////		if (!validatedData.isValidationAsDataSet()) {
////			return;
////		}
////
////		String inputHokenjyaNumber = validatedData.getHokenjyaNumber();
////		// edit s.inoue 2010/01/12
////		String inputHokenjyaHistoryNumber = validatedData.getHknjyaHistoryNumber();
////		// add s.inoue 2010/01/19
////		String inputItakuKubun = jComboBox_ItakuKubun.getSelectedItem().toString().substring(0, 1);
////
////		/* 登録済みの保険者か確認する。 */
////
////		StringBuilder sbCount = new StringBuilder();
////		sbCount.append("select count(HKNJANUM) as ROWCOUNT from T_HOKENJYA ");
////		sbCount.append(" where HKNJANUM = ? ");
////		// del s.inoue 2010/01/19
////		// sbCount.append("and HKNJYA_HISTORY_NO = ?");
////		if(pmode == CHANGE_MODE ||
////			pmode == ADD_MODE_TANKA ||
////				 pmode == CHANGE_MODE_TANKA){
////			sbCount.append(" and ITAKU_KBN = ? ");
////			sbCount.append(" and (HKNJYA_LIMITDATE_START  between ? and ? ");
////			sbCount.append(" or HKNJYA_LIMITDATE_END between ? and ? )");
////			sbCount.append(" or ( HKNJYA_LIMITDATE_START < ? ");
////			sbCount.append(" and HKNJYA_LIMITDATE_END > ? )");
////		}
////
////		String[] params = null;
////
////		// edit s.inoue 2010/01/22
////		if(pmode == CHANGE_MODE ||
////				pmode == ADD_MODE_TANKA ||
////					 pmode == CHANGE_MODE_TANKA){
////
////			params = new String[8];
////			params[0]=inputHokenjyaNumber;
////			params[1]=inputItakuKubun;
////			params[2]=jTextField_YukoukigenFrom.getText();
////			params[3]=jTextField_YukoukigenTo.getText();
////			params[4]=jTextField_YukoukigenFrom.getText();
////			params[5]=jTextField_YukoukigenTo.getText();
////			params[6]=jTextField_YukoukigenFrom.getText();
////			params[7]=jTextField_YukoukigenTo.getText();
////		}else{
////			params = new String[1];
////			params[0]=inputHokenjyaNumber;
////		}
////
//////		params = { inputHokenjyaNumber , inputItakuKubun,
//////				jTextField_YukoukigenFrom.getText(),jTextField_YukoukigenTo.getText(),
//////				jTextField_YukoukigenFrom.getText(),jTextField_YukoukigenTo.getText(),
//////				jTextField_YukoukigenFrom.getText(),jTextField_YukoukigenTo.getText()};
////
////		ArrayList<Hashtable<String, String>> result = null;
////		try{
////			result = JApplication.kikanDatabase.sendExecuteQuery(sbCount.toString(), params);
////		}catch(SQLException e){
////			logger.error(e.getMessage());
////			e.printStackTrace();
////		}
////
////		boolean existsNumber = false;
////		int count = 0;
////		if (result != null && result.size() > 0) {
////			String countString = result.get(0).get("ROWCOUNT");
////
////			try {
////				count = Integer.valueOf(countString);
////			} catch (NumberFormatException e) {
////				e.printStackTrace();
////			}
////
////			if (count > 0) {
////				existsNumber = true;
////			}
////		}
////
////		// edit s.inoue 2010/01/21
////		if ((pmode == ADD_MODE_TANKA && existsNumber)
//////				|| (pmode == ADD_MODE && existsNumber)
//////		if ((modeYukouKigen == ADD_MODE && existsNumber)
//////				|| (mode == ADD_MODE && existsNumber)
////				|| inputHokenjyaNumber.equals("99999999")
////				|| inputHokenjyaNumber.equals("00000000")) {
////			JErrorMessage.show("M3163", this);
////			return;
////		}else if(pmode == ADD_MODE && existsNumber){
////			JErrorMessage.show("M3164", this);
////			return;
////		}
////
////		// add s.inoue 2010/01/13
////		/* 期限有効フラグを全て"0"にセットする */
////// edit s.inoue 2010/01/21
////		if (pmode == ADD_MODE_TANKA ||
////				pmode == CHANGE_MODE_TANKA){
////			try {
////				JApplication.kikanDatabase.Transaction();
////
////				StringBuilder sb = new StringBuilder();
////				sb.append("UPDATE T_HOKENJYA SET ");
////				sb.append(" YUKOU_FLG = '0' ");
////				sb.append(" WHERE HKNJANUM = ");
////				sb.append(JQueryConvert.queryConvert(inputHokenjyaNumber));
////				JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
////
////				JApplication.kikanDatabase.Commit();
////			} catch (SQLException e) {
////				logger.error(e.getMessage());
////				e.printStackTrace();
////			}
////		}
////		StringBuffer buffer = new StringBuffer();
////		String address = JValidate.encodeUNICODEtoConvert(validatedData.getAddress());
////		String tiban = JValidate.encodeUNICODEtoConvert(validatedData.getChiban());
////
////		// edit s.inoue 2010/01/12
////		if (pmode == ADD_MODE ||
////				pmode == ADD_MODE_TANKA) {
//////		if (mode == ADD_MODE ||
//////				modeYukouKigen == ADD_MODE) {
////			// edit s.inoue 2010/01/19
////			long historyNum = createHistoryNo(inputHokenjyaNumber,inputHokenjyaHistoryNumber,inputItakuKubun);
////			if (historyNum == 0)
////				return;
////
////			buffer.append("INSERT INTO T_HOKENJYA (");
////			buffer.append("HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL,");
////			buffer.append("KIGO,HON_GAIKYURATE,HON_NYUKYURATE,");
////			buffer.append("KZK_GAIKYURATE,KZK_NYUKYURATE,ITAKU_KBN,");
////			buffer.append("TANKA_KIHON,HINKETU_CD,TANKA_HINKETU,");
////			buffer.append("SINDENZU_CD,TANKA_SINDENZU,GANTEI_CD,");
////			buffer.append("TANKA_GANTEI,TANKA_NINGENDOC,TANKA_HANTEI,");
////			buffer.append("HKNJYA_HISTORY_NO,");
////			buffer.append("HKNJYA_LIMITDATE_START,HKNJYA_LIMITDATE_END,");
////			buffer.append("YUKOU_FLG)");
////			buffer.append("VALUES ( "
////				+ JQueryConvert.queryConvertAppendComma(inputHokenjyaNumber)
////				+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getHokenjyaName())
////				+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
////				+ JQueryConvert.queryConvertAppendBlankAndComma(address)
////				+ JQueryConvert.queryConvertAppendBlankAndComma(tiban)
////				+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getTEL())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getCode())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninGairai())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninNyuin())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuGairai())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuNyuin())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuTanka())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getShindenzuCode())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getNingenDocTanka())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getTankaHantei())
////				// edit s.inoue 2010/01/19
////				+ JQueryConvert.queryConvertAppendComma(String.valueOf(historyNum))
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenFrom())
////				+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenTo())
////				// edit s.inoue 2010/01/13
////				// 有効フラグを"1"で登録する。validatedData.getYukouFlg()
////				+ JQueryConvert.queryConvert("1")
////				+ ")");
////		}
////		// edit s.inoue 2010/01/21
////		else if (pmode == CHANGE_MODE ||
////				pmode == CHANGE_MODE_TANKA) {
//////		else if (mode == CHANGE_MODE ||
//////				modeYukouKigen == CHANGE_MODE) {
////
////			buffer.append("UPDATE T_HOKENJYA SET ");
////					// edit s.inoue 2010/01/15
////					// + "HKNJANUM = "
////					// + JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
////
////					// edit s.inoue 2010/01/15
////					// + "ITAKU_KBN = "+ JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun())
////// edit s.inoue 2010/01/21
////			if (pmode == CHANGE_MODE_TANKA){
//////				if (modeYukouKigen == CHANGE_MODE){
////					buffer.append("TANKA_KIHON = "+ JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka()));
////					buffer.append("HINKETU_CD = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode()));
////					buffer.append("TANKA_HINKETU = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuTanka()));
////					buffer.append("SINDENZU_CD = "+ JQueryConvert.queryConvertAppendComma(validatedData.getShindenzuCode()));
////					buffer.append("TANKA_SINDENZU = "+ JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka()));
////					buffer.append("GANTEI_CD = "+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode()));
////					buffer.append("TANKA_GANTEI = "+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka()));
////					buffer.append("TANKA_NINGENDOC = "+ JQueryConvert.queryConvertAppendComma(validatedData.getNingenDocTanka()));
////					buffer.append("TANKA_HANTEI = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTankaHantei()));
////					buffer.append("HKNJYA_LIMITDATE_START = "+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenFrom()));
////					buffer.append("HKNJYA_LIMITDATE_END = "+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenTo()));
////					buffer.append("YUKOU_FLG = "+ JQueryConvert.queryConvert("1"));
////				}else{
////					buffer.append("HKNJANAME = "+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getHokenjyaName()));
////					buffer.append("POST = "+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode()));
////					buffer.append("ADRS = "+ JQueryConvert.queryConvertAppendBlankAndComma(address));
////					buffer.append("BANTI = "+ JQueryConvert.queryConvertAppendBlankAndComma(tiban));
////					buffer.append("TEL = "+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getTEL()));
////					buffer.append("KIGO = "+ JQueryConvert.queryConvertAppendComma(validatedData.getCode()));
////					buffer.append("HON_GAIKYURATE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninGairai()));
////					buffer.append("HON_NYUKYURATE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninNyuin()));
////					buffer.append("KZK_GAIKYURATE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuGairai()));
////					buffer.append("KZK_NYUKYURATE = "+ JQueryConvert.queryConvert(validatedData.getKazokuNyuin()));
////				}
////
////				buffer.append(" WHERE HKNJANUM = "+ JQueryConvert.queryConvert(validatedData.getHokenjyaNumber()));
////					// edit s.inoue 2010/01/21
////				if (pmode == CHANGE_MODE_TANKA){
//////					if (modeYukouKigen == CHANGE_MODE){
////						// edit s.inoue 2010/01/21
////						buffer.append(" AND ITAKU_KBN = " +JQueryConvert.queryConvert(validatedData.getItakuKubun()));
////						buffer.append(" AND HKNJYA_HISTORY_NO = " +JQueryConvert.queryConvert(String.valueOf(inputHokenjyaHistoryNumber)));
////					}
////		}
////
////		try {
////
////			JApplication.kikanDatabase.Transaction();
////			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
////
////			// 新規追加の場合はさらに健診項目マスタへの追加も行う
////			// edit s.inoue 2010/01/21
////			if (pmode == ADD_MODE) {
//////			if (mode == ADD_MODE) {
////				ArrayList<Hashtable<String, String>> resultKenshin = new ArrayList<Hashtable<String, String>>();
////				Hashtable<String, String> resultItemKenshin = new Hashtable<String, String>();
////				buffer = new StringBuffer(
////						"SELECT * FROM T_KENSHINMASTER WHERE HKNJANUM = "
////								+ JQueryConvert.queryConvert("99999999"));
////
////				resultKenshin = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
////
////				for (int i = 0; i < resultKenshin.size(); i++) {
////					resultItemKenshin = resultKenshin.get(i);
////
////					buffer = new StringBuffer("INSERT INTO T_KENSHINMASTER ( "
////							+ "HKNJANUM,"
////							+ "KOUMOKU_CD,"
////							+ "KOUMOKU_NAME,"
////							+ "DATA_TYPE,"
////							+ "MAX_BYTE_LENGTH,"
////							+ "XML_PATTERN,"
////							+ "XML_DATA_TYPE,"
////							+ "XML_KENSA_CD,"
////							+ "OBSERVATION,"
////							+ "AUTHOR,"
////							+ "AUTHOR_KOUMOKU_CD,"
////							+ "KENSA_GROUP,"
////							+ "KENSA_GROUP_CD,"
////							+ "KEKKA_OID,"
////							+ "KOUMOKU_OID,"
////							+ "HISU_FLG,"
////							+ "KAGEN,"
////							+ "JYOUGEN,"
////							+ "DS_JYOUGEN,"
////							+ "JS_JYOUGEN,"
////							+ "DS_KAGEN,"
////							+ "JS_KAGEN,"
////							+ "KIJYUNTI_HANI,"
////							+ "TANI,"
////							+ "KENSA_HOUHOU,"
////							+ "TANKA_KENSIN,"
////							+ "BIKOU,"
////							+ "XMLITEM_SEQNO"
////							+ ")"
////							+ "VALUES( "
////							+ JQueryConvert.queryConvertAppendComma(validatedData
////									.getHokenjyaNumber())
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KOUMOKU_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KOUMOKU_NAME"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("DATA_TYPE"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("MAX_BYTE_LENGTH"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("XML_PATTERN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("XML_DATA_TYPE"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("XML_KENSA_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("OBSERVATION"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("AUTHOR"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("AUTHOR_KOUMOKU_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KENSA_GROUP"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KENSA_GROUP_CD"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KEKKA_OID"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KOUMOKU_OID"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("HISU_FLG"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KAGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("JYOUGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("DS_JYOUGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("JS_JYOUGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("DS_KAGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("JS_KAGEN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KIJYUNTI_HANI"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("TANI"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("KENSA_HOUHOU"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("TANKA_KENSIN"))
////							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin
////									.get("BIKOU"))
////							+ JQueryConvert.queryConvert(resultItemKenshin
////									.get("XMLITEM_SEQNO")) +
////							")");
////
////					JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
////				}
////			}
////			JApplication.kikanDatabase.Commit();
////			// edit s.inoue 2010/01/21
////			modeYukouKigen = CHANGE_MODE;
////
////			registered = true;
////			lastAddedNumber = validatedData.getHokenjyaNumber();
////			// edit s.inoue 2010/01/21
////			if(pmode == ADD_MODE_TANKA ||
////					pmode == CHANGE_MODE_TANKA){
//////			if(modeYukouKigen == ADD_MODE ||
//////					modeYukouKigen == CHANGE_MODE){
////				searchReflesh(validatedData.getHokenjyaNumber());
////				// add s.inoue 2010/01/26
////				controlSetting(true);
////			}
////			if(pmode == ADD_MODE ||
////					pmode == CHANGE_MODE){
////				dispose();
////			}
////		} catch (SQLException f) {
////			f.printStackTrace();
////			JErrorMessage.show(
////					"M3130", this);
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
////
////	// add s.inoue 2010/01/12
////	// 新規登録 委託区分含めレコードがあった場合、エラー
////	//
////	/* 履歴番号採番 */
////	private long createHistoryNo(String inputHokenjyaNumber,String inputHistoryNo,String inputItakuKubun){
////		int historyCnt = 0;
////		long historyNo = -1L;
////
////		try{
////			historyCnt = tHokenjyaDao.selectByCountUniqueKey(inputHokenjyaNumber,inputHistoryNo,inputItakuKubun);
////		}catch(Exception ex){
////			logger.error(ex.getMessage());
////		}
////
////		// edit s.inoue 2010/01/15
////		if (historyCnt > 0){
////			try {
////				historyNo = tHokenjyaDao.selectNewHistoryId(inputHokenjyaNumber);
////				logger.info("保険者番号採番:" +historyNo);
////			}catch(Exception ex){
////				logger.error(ex.getMessage());
////			}
////		}else{
////			// edit s.inoue 2010/01/20
////			// historyNo = 0;
////			historyNo = Long.parseLong(inputHistoryNo);
////		}
////		return historyNo;
////	}
////
////	private boolean validateData(int pmode) {
////		String radioHantei = jRadioButton_Kihon.isSelected() ? "1" : "2";
////		// edit s.inoue 2009/12/02 基本単価必須の為、人間ドックの場合ゼロ登録させる
////		String kihontanka = jTextField_KihonTanka.getText();
////		kihontanka = (kihontanka.equals("")) ? "0" : kihontanka;
////
////		String historyNumber = (mode==CHANGE_MODE) ? getmodelColumnNumber(COLUMUN_HISTORY_NO): "1";
////
////		boolean rettanka = false;
////		boolean rethokenjya = false;
////
////		rettanka= validatedData.setHokenjyaName(jTextField_HokenjyaName.getText())
////				&& validatedData.setHokenjyaNumber(jTextField_HokenjyaNumber.getText())
////				&& validatedData.setHonninGairai(jTextField_HonninGairai.getText())
////				&& validatedData.setTEL(jTextField_TEL.getText())
////				&& validatedData.setZipcode(jTextField_Zipcode.getText())
////				&& validatedData.setHinketsuCode(jTextField_HinketuCode.getText())
////				&& validatedData.setShindenzuCode(jTextField_SindenzuCode.getText())
////				&& validatedData.setGanteiCode(jTextField_GanteiCode.getText());
////
////		rethokenjya = validatedData.setAddress(jTextField_Address.getText())
////				&& validatedData.setChiban(jTextField_Chiban.getText())
////				&& validatedData.setKigo(jTextField_Kigo.getText())
////				// edit s.inoue 2009/12/02
////				// && validatedData.setKihonTanka(jTextField_KihonTanka.getText())
////				&& validatedData.setHonninNyuin(jTextField_HonninNyuin.getText())
////				&& validatedData.setKazokuGairai(jTextField_KazokuNyuin.getText())
////				&& validatedData.setKazokuNyuin(jTextField_KazokuNyuin.getText())
////				&& validatedData.setItakuKubun((String) jComboBox_ItakuKubun.getSelectedItem())
////				&& validatedData.setKihonTanka(kihontanka)
////				&& validatedData.setGanteiTanka(jTextField_GanteiTanka.getText())
////				&& validatedData.setHinketsuTanka(jTextField_HinketsuTanka.getText())
////				&& validatedData.setNingenDocTanka(jTextField_NingenDocTanka.getText())
////				&& validatedData.setSindenzuTanka(jTextField_SindenzuTanka.getText())
////				&& validatedData.setTankaHantei(radioHantei)
////				&& validatedData.setHknjyaHistoryNumber(historyNumber)
////				&& validatedData.setYukouKigenFrom(jTextField_YukoukigenFrom.getText())
////				&& validatedData.setYukouKigenTo(jTextField_YukoukigenTo.getText());
////
////		if(pmode == ADD_MODE_TANKA ||
////				pmode == CHANGE_MODE_TANKA ||
////					pmode == ADD_MODE){
////			return rettanka && rethokenjya;
////		}else{
////			return rettanka;
////		}
////	}
////
////	// add s.inoue 2010/01/12
////	/* 選択行指定列データ取得 */
////	private String getmodelColumnNumber(int colNumber){
////		return String.valueOf(model.getData(model.getSelectedRow(),colNumber));
////	}
////
////	/**
////	 * ORCAボタン
////	 */
////	public void pushedORCAButton() {
////		if (JApplication.useORCA) {
////			JConnection sql = null;
////			try {
////				sql = new JConnection("org.postgresql.Driver", "jdbc:postgresql://"
////						+ JApplication.orcaSetting.getOrcaIpAddress() + ":"
////						+ JApplication.orcaSetting.getOrcaPort() + "/"
////						+ JApplication.orcaSetting.getOrcaDatabase(),
////						JApplication.orcaSetting.getOrcaUser(),
////						JApplication.orcaSetting.getOrcaPassword());
////
////			} catch (SQLException e1) {
////				e1.printStackTrace();
////				/* M1103=エラー,機関データベースへの接続が失敗しました。,0,0 */
////				JErrorMessage.show("M1103", this);
////
////				return;
////			}
////
////			ArrayList<Hashtable<String, String>> result = null;
////			if (validatedData.setHokenjyaNumber(
////					jTextField_HokenjyaNumber.getText())) {
////
////				if (validateAsPushedORCAButton(validatedData)) {
////					String hokenjyaNumber = validatedData.getHokenjyaNumber();
////
////					/* 0 を全て削除する。 */
////					hokenjyaNumber = hokenjyaNumber.replaceAll("^0+", "");
////
////					try {
////						while (hokenjyaNumber.length() <= 8) {
////							result = this.getHokenjaInfo(sql, hokenjyaNumber);
////							if (result.size() != 0) {
////								break;
////							}
////
////							/* 先頭に 0 を追加する。 */
////							hokenjyaNumber = "0" + hokenjyaNumber;
////						}
////
////						if (result.size() != 0) {
////							this.setDbDataToComponent(result);
////							this.jComboBox_ItakuKubun.grabFocus();
////						}
////						else {
////							/* エラー,入力された保険者番号に該当する保険者情報が存在しません。,0,0 */
////							JErrorMessage.show("M3140", this);
////
////							/* 保険者名称入力欄にフォーカスを移動する。 */
////							this.jTextField_HokenjyaName.grabFocus();
////						}
////
////					} catch (SQLException e1) {
////
////						e1.printStackTrace();
////						try {
////							sql.Shutdown();
////						} catch (SQLException e2) {
////							e2.printStackTrace();
////							return;
////						}
////						return;
////					}
////				}
////			}
////
////			try {
////				sql.Shutdown();
////			} catch (SQLException e1) {
////				e1.printStackTrace();
////			}
////		}
////		else {
////			/* 保険者名称入力欄にフォーカスを移動する。 */
////			this.jTextField_HokenjyaName.grabFocus();
////		}
////	}
////
////	private void setDbDataToComponent(ArrayList<Hashtable<String, String>> result) {
////		jTextField_HokenjyaName.setText(result.get(0).get("hknjaname"));
////		jTextField_Zipcode.setText(result.get(0).get("post").trim());
////		jTextField_Address.setText(result.get(0).get("adrs"));
////		jTextField_Chiban.setText(result.get(0).get("banti"));
////		jTextField_Kigo.setText(result.get(0).get("kigo"));
////		jTextField_HonninGairai.setText(result.get(0).get("hon_gaikyurate"));
////		jTextField_HonninNyuin.setText(result.get(0).get("hon_nyukyurate"));
////		jTextField_KazokuGairai.setText(result.get(0).get("kzk_gaikyurate"));
////		jTextField_KazokuNyuin.setText(result.get(0).get("kzk_nyukyurate"));
////	}
////
////	private ArrayList<Hashtable<String, String>> getHokenjaInfo(
////			JConnection sql,
////			String hokenjyaNumber
////			) throws SQLException {
////
////		String numberForSql =
////			JQueryConvert.queryConvert(hokenjyaNumber);
////
////		ArrayList<Hashtable<String, String>> result =
////			sql.sendExecuteQuery(
////				"SELECT * FROM TBL_HKNJAINF WHERE HKNJANUM = "
////						+ numberForSql);
////
////		return result;
////	}
////
////	/**
////	 * 登録ボタン
////	 */
////	public void pushedRegisterButton(ActionEvent e) {
////		register(mode);
////	}
////
////	/**
////	 * 終了ボタン
////	 */
////	public void pushedEndButton(ActionEvent e) {
////		dispose();
////	}
////
////	/**
////	 * キャンセルボタン
////	 */
////	public void pushedCancelButton(ActionEvent e) {
////		RETURN_VALUE Ret = JErrorMessage.show("M3151", this);
////		if (Ret == RETURN_VALUE.YES) {
////			dispose();
////		}
////	}
////
////	/**
////	 * クリアボタン
////	 */
////	public void pushedClearButton(ActionEvent e) {
////		if (mode == ADD_MODE) {
////			this.jTextField_HokenjyaNumber.setText("");
////		}
////		// edit s.inoue 2010/01/21
////		// if (modeYukouKigen != ADD_MODE){
////		if (mode == ADD_MODE ||
////				mode == CHANGE_MODE){
////			this.jTextField_HokenjyaName.setText("");
////			this.jTextField_Zipcode.setText("");
////			this.jTextField_Address.setText("");
////			this.jTextField_Chiban.setText("");
////			this.jTextField_TEL.setText("");
////			this.jTextField_Kigo.setText("");
////		}
////		// edit s.inoue 2010/01/22
////		if(mode == ADD_MODE){
////			this.jTextField_YukoukigenFrom.setText("");
////			this.jTextField_YukoukigenTo.setText("");
////			this.jComboBox_ItakuKubun.setSelectedIndex(0);
////			this.jTextField_KihonTanka.setText("");
////			this.jTextField_HinketsuTanka.setText("");
////			this.jTextField_SindenzuTanka.setText("");
////			this.jTextField_GanteiTanka.setText("");
////			this.jTextField_NingenDocTanka.setText("");
////			this.jRadioButton_Kihon.setSelected(true);
////		}
////	}
////
////	// add s.inoue 2010/01/11
////	public void addNewClearButton() {
////		this.jTextField_YukoukigenFrom.setText("");
////		this.jTextField_YukoukigenTo.setText("");
////		this.jComboBox_ItakuKubun.setSelectedIndex(0);
////		this.jTextField_KihonTanka.setText("");
////		this.jTextField_HinketsuTanka.setText("");
////		this.jTextField_SindenzuTanka.setText("");
////		this.jTextField_GanteiTanka.setText("");
////		this.jTextField_NingenDocTanka.setText("");
////		this.jRadioButton_Kihon.setSelected(true);
////	}
////
////	// add s.inoue 2010/01/14
////	private void checkItakuKubunRecord(ItemEvent e){
////		if(mode == ADD_MODE ||
////				modeYukouKigen == ADD_MODE)
////			return;
////
////		if (ItemEvent.SELECTED == e.getStateChange()) {
////
////			int selectIdx = 0;
////			ArrayList<Integer> selectedRecord = new ArrayList<Integer>();
////
////			selectIdx = jComboBox_ItakuKubun.getSelectedIndex()+1;
////			String historyNo = getmodelColumnNumber(COLUMUN_HISTORY_NO);
////			for (int i = 0; i < model.getRowCount(); i++) {
////				if (model.getData(i, COLUMUN_HISTORY_NO).equals(historyNo) &&
////						model.getData(i, COLUMUN_ITAKU_KBN).equals(String.valueOf(selectIdx))){
////					selectedRecord.add(i);
////					 model.setselectedRows(model, selectedRecord);
////				}
////			}
////			// add s.inoue 2010/01/20
////			if (selectedRecord.size() == 0 &&
////					initializeTanka){
////				JErrorMessage.show("M3160", this);
////				jComboBox_ItakuKubun.setSelectedIndex(Integer.parseInt(getmodelColumnNumber(COLUMUN_ITAKU_KBN)) - 1);
////			}
////		}
////	}
////
////	// add s.inoue 2010/01/19
////	private void controlSetting(boolean flg){
////		// テキストコントロール制御
////		this.jTextField_HokenjyaNumber.setEditable(flg);
////		this.jTextField_HokenjyaName.setEditable(flg);
////		this.jTextField_Zipcode.setEditable(flg);
////		this.jTextField_Address.setEditable(flg);
////		this.jTextField_Chiban.setEditable(flg);
////		this.jTextField_TEL.setEditable(flg);
////		this.jTextField_Kigo.setEditable(flg);
////		// edit s.inoue 2010/01/26
////		this.jButton_Clear.setEnabled(flg);
////		this.jButton_Register.setEnabled(flg);
////		// del s.inoue 2010/01/20
////		// jComboBox_ItakuKubun.setEnabled(!flg);
////	}
////
////	// add s.inoue 2010/01/19
////	private void controlSettingData(){
////		setTankaTable(jTextField_HokenjyaNumber.getText());
////	}
////
////	// 削除ボタン
////	private void pushedYukoukigenDeleteButton(){
////		logger.info(jButton_RegisterYukoukigenDelete.getText());
////
////		// 確認メッセージ
////		if (model.getSelectedRow() == arrYukou.get(0)){
////			JErrorMessage.show("M3162", this);
////			return;
////		}
////
////		RETURN_VALUE Ret = JErrorMessage.show("M3161", this);
////
////		if (Ret == RETURN_VALUE.NO) {
////			return;
////		}
////
////		// 履歴No,委託区分
////		try {
////			JApplication.kikanDatabase.Transaction();
////			tHokenjyaDao.delete(this.hokenjyaNumber,
////					getmodelColumnNumber(COLUMUN_HISTORY_NO),
////					getmodelColumnNumber(COLUMUN_ITAKU_KBN));
////			JApplication.kikanDatabase.Commit();
////		} catch (SQLException e) {
////			e.printStackTrace();
////			logger.error(e.getMessage());
////		}
////
////		// edit s.inoue 2010/01/21
////		searchReflesh(this.hokenjyaNumber);
////	}
////
////	// 更新ボタン
////	private void pushedYukoukigenEditButton(){
////		logger.info(jButton_RegisterYukoukigenEdit.getText());
////		modeYukouKigen = CHANGE_MODE;
////		register(CHANGE_MODE_TANKA);
////	}
////	// 新規追加ボタン
////	private void pushedYukoukigenButton(){
////		logger.info(jButton_RegisterYukoukigen.getText());
////		register(ADD_MODE_TANKA);
////	}
////
////	// 新規追加ボタン
////	private void pushedAddButton(){
////		logger.info(jButton_Add.getText());
////		modeYukouKigen = ADD_MODE;
////		// edit s.inoue 2010/01/21
////		initilizeFocus();
////		// add s.inoue 2010/01/19
////		controlSettingData();
////
////		// add s.inoue 2010/01/19
////		// テキストコントロール制御
////		controlSetting(false);
////
////		addNewClearButton();
////		jButton_RegisterYukoukigen.setVisible(true);
////		jButton_RegisterYukoukigenEdit.setVisible(false);
////		this.jTextField_YukoukigenFrom.grabFocus();
////	}
////
////
////	public void actionPerformed(ActionEvent e) {
////		Object source = e.getSource();
////		if(source == jButton_Add){
////			pushedAddButton();
////		}else if (source == jButton_RegisterYukoukigen){
////			pushedYukoukigenButton();
////		}else if (source == jButton_RegisterYukoukigenEdit){
////			pushedYukoukigenEditButton();
////		}else if (source == jButton_RegisterYukoukigenDelete){
////			pushedYukoukigenDeleteButton();
////		}else if (source == jButton_End) {
////			logger.info(jButton_End.getText());
////			pushedEndButton(e);
////		}
////		else if (source == jButton_ORCA) {
////			logger.info(jButton_ORCA.getText());
////			pushedORCAButton();
////		}
////		else if (source == jButton_Register ||
////				source == jButton_RegisterAdd) {
////			logger.info(jButton_Register.getText());
////			pushedRegisterButton(e);
////		}
////		else if (source == jTextField_HokenjyaNumber) {
////			logger.info(jTextField_HokenjyaNumber.getText());
////			enterKeyPushedHokenjyaNumber();
////		}
////		else if (source == jButton_Clear ||
////				source == jButton_ClearAdd) {
////			logger.info(jButton_Clear.getText());
////			pushedClearButton(e);
////		}
////// del s.inoue 2010/01/14
//////		}else if (source == jButton_RegisterYukoukigenDelete){
//////			logger.info(jButton_RegisterYukoukigenDelete);
//////			String[] messageParams = { model.getData(model.getSelectedRow(),1) + "-" + model.getData(model.getSelectedRow(),2) };
//////			RETURN_VALUE Ret = JErrorMessage.show("M3157", this, messageParams);
//////
//////			if (Ret == RETURN_VALUE.YES) {
//////				deleteYukouKigen();
//////				searchReflesh(this.hokenjyaNumber);
//////			}
////	}
////
////	// add s.inoue 2009/10/01
////	// ラジオボタンイベント
////	public void itemStateChanged(ItemEvent e) {
////		Object source = e.getSource();
////
////		if (source == jRadioButton_Kihon ||
////				source == jRadioButton_Doc) {
////			radioItemChangeColor();
////			// edit s.inoue 2010/01/21
////			initilizeFocus();
////		}
////		if (source == jRadioButton_Kihon) {
////			changedKihonState(e);
////		}
////		else if (source == jRadioButton_Doc) {
////			changedDocState(e);
////		}
////		else if (source == jComboBox_ItakuKubun) {
////			checkItakuKubunRecord(e);
////		}
////	}
////	/**
////	 * 基本のラジオボタンが押された場合
////	 */
////	public void changedKihonState(ItemEvent e) {
////		if (ItemEvent.SELECTED == e.getStateChange()) {
////			jTextField_TankaHantei.setText("1");
////		} else {
////			// del s.inoue 2010/01/14
////			// clearJTextfield_Tanka(false);
////		}
////	}
////
////	/**
////	 * 人間ドックのラジオボタンが押された場合
////	 */
////	public void changedDocState(ItemEvent e) {
////		if (ItemEvent.SELECTED == e.getStateChange()) {
////			jTextField_TankaHantei.setText("2");
////		} else {
////			// del s.inoue 2010/01/14
////			// clearJTextfield_Tanka(true);
////		}
////	}
////
////	// add s.inoue 2009/10/01
////	private void radioItemChangeColor(){
////		Color kihonItem = null;
////		Color docItem = null;
////		boolean selectedItem = false;
////
////		if (jRadioButton_Kihon.isSelected()) {
////			kihonItem = ViewSettings.getRequiedItemBgColor();
////			docItem = ViewSettings.getDisableItemBgColor();
////			selectedItem = true;
////		}else if(jRadioButton_Doc.isSelected()){
////			kihonItem = ViewSettings.getDisableItemBgColor();
////			docItem = ViewSettings.getRequiedItemBgColor();
////			selectedItem = false;
////		}
////
////		// 背景制御
////		// add s.inoue 2010/01/20
////		this.jTextField_YukoukigenFrom.setBackground(ViewSettings.getRequiedItemBgColor());
////		this.jTextField_YukoukigenTo.setBackground(ViewSettings.getRequiedItemBgColor());
////
////		this.jTextField_KihonTanka.setBackground(kihonItem);
////		this.jTextField_SindenzuTanka.setBackground(kihonItem);
////		this.jTextField_GanteiTanka.setBackground(kihonItem);
////		this.jTextField_HinketsuTanka.setBackground(kihonItem);
////		this.jTextField_NingenDocTanka.setBackground(docItem);
////		// 入力制御
////		this.jTextField_KihonTanka.setEnabled(selectedItem);
////		this.jTextField_SindenzuTanka.setEnabled(selectedItem);
////		this.jTextField_GanteiTanka.setEnabled(selectedItem);
////		this.jTextField_HinketsuTanka.setEnabled(selectedItem);
////		this.jTextField_NingenDocTanka.setEnabled(!selectedItem);
////	}
////
////	// add s.inoue 2009/10/01
////	@Override
////	public void keyTyped(KeyEvent e) {
////		Object source = e.getSource();
////		char keyChar = e.getKeyChar();
////
////		/* 基本ラジオボタン */
////		if (source == this.jRadioButton_Kihon) {
////			if (keyChar == KeyEvent.VK_SPACE) {
////				this.jRadioButton_Kihon.setSelected(true);
////			}
////		/* Docラジオボタン */
////		} else if (source == this.jRadioButton_Doc) {
////			if (keyChar == KeyEvent.VK_SPACE) {
////				this.jRadioButton_Doc.setSelected(true);
////			}
////		/* 性別入力欄 */
////		} else if (source == this.jTextField_TankaHantei) {
////
////			if (keyChar == KeyEvent.VK_1) {
////				this.jTextField_TankaHantei.setText(TANKA_HANTEI_KIHON);
////				this.jRadioButton_Kihon.setSelected(true);
////			} else if (keyChar == KeyEvent.VK_2) {
////				// edit s.inoue 2010/01/20
////				// this.jRadioButton_Doc.setText("2");
////				this.jTextField_TankaHantei.setText(TANKA_HANTEI_DOC);
////				this.jRadioButton_Doc.setSelected(true);
////			} else {
////				String currentValue = this.jTextField_TankaHantei.getText();
////				if (!currentValue.equals(TANKA_HANTEI_KIHON) &&
////						!currentValue.equals(TANKA_HANTEI_DOC)) {
////					this.groupHantei.clearSelection();
////				}
////			}
////		}
////	}
////
////	@Override
////	public void keyPressed(KeyEvent keyEvent) {
////		switch(keyEvent.getKeyCode()){
////		case KeyEvent.VK_F1:
////			pushedEndButton(null);
////			break;
////		case KeyEvent.VK_F2:
////			pushedClearButton(null);
////			break;
////		case KeyEvent.VK_F7:
////			if(jButton_Add.isVisible()){
////				pushedAddButton();
////			}
////			break;
////		case KeyEvent.VK_F8:
////			if(jButton_RegisterYukoukigen.isVisible()){
////				pushedYukoukigenButton();
////			}
////			break;
////		case KeyEvent.VK_F9:
////			if(jButton_RegisterYukoukigenEdit.isVisible()){
////				pushedYukoukigenEditButton();
////			}
////			break;
////		case KeyEvent.VK_F10:
////			if(jButton_RegisterYukoukigenDelete.isVisible()){
////				pushedYukoukigenDeleteButton();
////			}
////			break;
////		case KeyEvent.VK_F12:
////			register(mode);
////			break;
////		}
////	}
////
////}