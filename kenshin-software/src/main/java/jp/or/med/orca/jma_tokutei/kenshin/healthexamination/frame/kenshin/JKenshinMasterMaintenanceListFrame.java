package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Master;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenImportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorKenshinResultFrameData;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GenericButton;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.table.model.client.VOListTableModel;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.client.ClientUtils;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = -8615537027525763087L;	// edit n.ohkubo 2014/10/01　追加

//	private Connection conn = null;	// edit n.ohkubo 2014/10/01　未使用なので削除

	protected GridControl grid = new GridControl();
	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();

	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();  //  @jve:decl-index=0:

	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
// del s.inoue 2013/02/15
//	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
	protected GenericButton buttonClose = null;

	/* control */
	protected ComboColumn textColumn_HokenjaNumber = new ComboColumn();
	protected TextColumn textColumn_KoumokuCd = new TextColumn();
	// eidt s.inoue 2011/06/02
	protected TextColumn textColumn_KoumokuNm = new TextColumn();
	protected TextColumn textColumn_KensaHouhou = new TextColumn();

	protected ComboColumn textColumn_HisuFlg = new ComboColumn();
	protected TextColumn textColumn_DSKagen = new TextColumn();
	protected TextColumn textColumn_DSJyougen = new TextColumn();
	protected TextColumn textColumn_JSKagen = new TextColumn();
	protected TextColumn textColumn_JSJyougen = new TextColumn();
	protected TextColumn textColumn_Tani = new TextColumn();
	protected TextColumn textColumn_Kagen = new TextColumn();
	protected TextColumn textColumn_Jyogen = new TextColumn();
	protected TextColumn textColumn_KijyuntiHani = new TextColumn();
	protected TextColumn textColumn_TankaKenshin = new TextColumn();
	protected TextColumn textColumn_Bikou = new TextColumn();
//	protected ComboBoxControl comboSelectHokenja = null;
	protected FlowLayout flowLayout1 = new FlowLayout();  //  @jve:decl-index=0:

	protected final static String ICON_DELETE_KEKKA = "./Images/kekkaInput_DelKekka.png";
	protected final static String ICON_DELETE_JUSINKEN = "./Images/kekkaInput_DelJusinken.png";
	protected final static String ICON_PRINT_NYURYOKUHYO = "./Images/kekkaInput_CopyNuryokuhyo.png";

	private IDialog filePathDialog;
	private JKenshinMasterMaintenanceListData validatedData = new JKenshinMasterMaintenanceListData();

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

	private static String CONST_DATA_VALUE =
		"jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.JKenshinMasterMaintenanceListData";

	protected final static double CONST_FIX_ICON =0.8;
	// add s.inoue 2010/02/25
	protected ExtendedOpenGenericButton buttonExport = null;
	protected ExtendedOpenGenericButton buttonImport = null;

	private static org.apache.log4j.Logger logger = Logger.getLogger(JKenshinMasterMaintenanceListFrame.class);
	
	// edit n.ohkubo 2014/10/01　追加 start
	private ColumnContainer columnContainer;
	public ColumnContainer getColumnContainer() {
		return this.columnContainer;
	}
	private JCheckBox savedJCheckBox = new JCheckBox();
	public JCheckBox getSavedJCheckBox() {
		return this.savedJCheckBox;
	}
	// edit n.ohkubo 2014/10/01　追加 end
	
	// edit n.ohkubo 2015/03/01　追加
	protected ExtendedOpenGenericButton buttonDefault = null;		//初期値ボタン
	protected String afterDefaultToolTipText = null;				//初期値ボタン押下後のツールチップ文言
	protected List<TableCellRenderer> tableCellRendererList = null;	//TableCellRendererの初期設定退避用（初期値ボタン押下で設定し、再読込押下時に使用）

	/* コンストラクタ */
	public JKenshinMasterMaintenanceListFrame(Connection conn,
			JKenshinMasterMaintenanceListFrameCtrl controller) {
		setControl(conn,controller);
	}

	/* リロード */
	public void reloadData() {
		grid.reloadData();
	}

	/* グリッドgetter */
	public GridControl getGrid() {
		return grid;
	}

	/* 制御メソッド */
	public void setControl(Connection conn,
			JKenshinMasterMaintenanceListFrameCtrl controller){
//		this.conn = conn;	// edit n.ohkubo 2014/10/01　未使用なので削除
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

			grid.addKeyListener(new KeyAdapter() {
			      @Override
				public void keyPressed(KeyEvent e) {
			        if (e.getKeyCode()==e.VK_CANCEL || e.getKeyCode()==e.VK_BACK_SPACE || e.getKeyCode()==e.VK_DELETE)
			          System.out.println("成功");
			      }
			    });
			
			// edit n.ohkubo 2014/10/01　追加 start　検索画面にチェックボックスを追加
			//フィルターパネル用のWindowListener
			SpecialFilterPanel specialFilterPanel = new SpecialFilterPanel(savedJCheckBox, grid.getParent().getComponents());
			
			//このフレーム（一覧画面）がアクティブ化（画面右の検索ウィンドウ）　or　非アクティブ化（検索ウィンドウがポップアップで開かれ場合）されたときに動作するように、Listenerを設定（FilterPanelへのチェックボックス追加はListener内で行う）
			this.addWindowListener(specialFilterPanel);
			// edit n.ohkubo 2014/10/01　追加 end　検索画面にチェックボックスを追加

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 初期化処理 */
	public void jbInit() throws Exception {

//		// combobox設定
//		Response res = ClientUtils.getData("loadCallOutTypes",new GridParams());
//	    Domain d = new Domain("CALL_OUT_TYPES");
//	    if (!res.isError()) {
//	      JKenshinMasterMaintenanceHokenjyaVO vo = null;
//	      java.util.List list = ((VOListResponse)res).getRows();
//	      for(int i=0;i<list.size();i++) {
//	        vo = (JKenshinMasterMaintenanceHokenjyaVO)list.get(i);
//	        d.addDomainPair(vo.getHokenjyaNumber(),vo.getHokenjyaName());
//	      }
//	    }
//	    comboSelectHokenja = new ComboBoxControl();
//	    comboSelectHokenja.setDomain(d);
//	    comboSelectHokenja.getComboBox().addItemListener(new ItemListener() {
//	      public void itemStateChanged(ItemEvent e) {
//	        if (e.getStateChange()==e.SELECTED) {
////	          hierarTreePanel.setProgressiveHIE02((BigDecimal)comboBoxControl1.getValue());
////	          hierarTreePanel.reloadTree();
//	          grid.clearData();
//	        }
//	      }
//	    });
//	    if (d.getDomainPairList().length==1)
//	      comboSelectHokenja.getComboBox().setSelectedIndex(0);
//	    else
//	      comboSelectHokenja.getComboBox().setSelectedIndex(-1);
//
//	    comboSelectHokenja.setDomainId("SEX");

		/* control ※ClientApplicationと一致*/
		// 保険者番号(*)
		textColumn_HokenjaNumber.setColumnFilterable(true);
		textColumn_HokenjaNumber.setColumnName("HKNJANUM");
		textColumn_HokenjaNumber.setColumnSortable(true);
		textColumn_HokenjaNumber.setPreferredWidth(200);
		textColumn_HokenjaNumber.setColumnRequired(true);
		textColumn_HokenjaNumber.setDomainId("T_HOKENJYA");
		/*add tanaka 2013/11/15*/
//		textColumn_HokenjaNumber.setColumnVisible(true);
		textColumn_HokenjaNumber.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.HKNJANUM));

		// 項目コード(*)
		textColumn_KoumokuCd.setColumnFilterable(true);
		textColumn_KoumokuCd.setColumnName("KOUMOKU_CD");
		textColumn_KoumokuCd.setColumnSortable(true);
		textColumn_KoumokuCd.setPreferredWidth(150);
		textColumn_KoumokuCd.setColumnRequired(true);
		/*add tanaka 2013/11/15*/
//		textColumn_KoumokuCd.setColumnVisible(true);
		textColumn_KoumokuCd.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_CD));
		textColumn_KoumokuCd.setMaxCharacters(17);	// edit n.ohkubo 2014/10/01　追加

		// eidt s.inoue 2011/06/02
		// 項目名
		textColumn_KoumokuNm.setColumnFilterable(true);
		textColumn_KoumokuNm.setColumnName("KOUMOKU_NAME");
		textColumn_KoumokuNm.setColumnSortable(true);
		textColumn_KoumokuNm.setPreferredWidth(200);
		textColumn_KoumokuNm.setColumnRequired(false);
		/*add tanaka 2013/11/15*/
//		textColumn_KoumokuNm.setColumnVisible(true);
		textColumn_KoumokuNm.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_NAME));
		textColumn_KoumokuNm.setMaxCharacters(200);	// edit n.ohkubo 2014/10/01　追加
		
		// 検査方法
		textColumn_KensaHouhou.setColumnFilterable(true);
		textColumn_KensaHouhou.setColumnName("KENSA_HOUHOU");
		textColumn_KensaHouhou.setColumnSortable(true);
		textColumn_KensaHouhou.setPreferredWidth(180);
		textColumn_KensaHouhou.setColumnRequired(false);
		/*add tanaka 2013/11/15*/
//		textColumn_KensaHouhou.setColumnVisible(true);
		textColumn_KensaHouhou.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KENSA_HOUHOU));
		textColumn_KensaHouhou.setMaxCharacters(200);	// edit n.ohkubo 2014/10/01　追加
		
		// 必須フラグ(編集可)
		textColumn_HisuFlg.setColumnFilterable(true);
		textColumn_HisuFlg.setColumnName("HISU_FLG");
		textColumn_HisuFlg.setColumnSortable(true);
		textColumn_HisuFlg.setPreferredWidth(70);
	    textColumn_HisuFlg.setEditableOnEdit(true);
	    textColumn_HisuFlg.setEditableOnInsert(true);
	    textColumn_HisuFlg.setColumnRequired(false);

	    textColumn_HisuFlg.setDomainId("HISU_FLG");
	    /*add tanaka 2013/11/15*/
//	    textColumn_HisuFlg.setColumnVisible(true);
	    textColumn_HisuFlg.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.HISU_FLG));
	    
		// 基準値下限(編集可)
		textColumn_DSKagen.setColumnFilterable(true);
		textColumn_DSKagen.setColumnName("DS_KAGEN");
		textColumn_DSKagen.setColumnSortable(true);
		textColumn_DSKagen.setPreferredWidth(120);
		textColumn_DSKagen.setEditableOnEdit(true);
		textColumn_DSKagen.setEditableOnInsert(true);
		textColumn_DSKagen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_DSKagen.setColumnVisible(true);
		textColumn_DSKagen.setColumnFilterable(false);
		textColumn_DSKagen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_DSKagen.setColumnVisible(true);
		textColumn_DSKagen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.DS_KAGEN));
		
		// 基準値上限(編集可)
		textColumn_DSJyougen.setColumnFilterable(true);
		textColumn_DSJyougen.setColumnName("DS_JYOUGEN");
		textColumn_DSJyougen.setColumnSortable(true);
		textColumn_DSJyougen.setPreferredWidth(120);
		textColumn_DSJyougen.setEditableOnEdit(true);
		textColumn_DSJyougen.setEditableOnInsert(true);
		textColumn_DSJyougen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_DSJyougen.setColumnVisible(true);
		textColumn_DSJyougen.setColumnFilterable(false);
		textColumn_DSJyougen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_DSJyougen.setColumnVisible(true);
		textColumn_DSJyougen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.DS_JYOUGEN));

		// 基準値上限値(編集可)
		textColumn_JSKagen.setColumnFilterable(true);
		textColumn_JSKagen.setColumnName("JS_KAGEN");
		textColumn_JSKagen.setEditableOnEdit(true);
		textColumn_JSKagen.setEditableOnInsert(true);
		textColumn_JSKagen.setPreferredWidth(120);
		textColumn_JSKagen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_JSKagen.setColumnVisible(true);
		textColumn_JSKagen.setColumnFilterable(false);
		textColumn_JSKagen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_JSKagen.setColumnVisible(true);
		textColumn_JSKagen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.JS_KAGEN));

		// 基準値下限値(編集可)
		textColumn_JSJyougen.setColumnFilterable(true);
		textColumn_JSJyougen.setColumnName("JS_JYOUGEN");
		textColumn_JSJyougen.setColumnSortable(true);
		textColumn_JSJyougen.setEditableOnEdit(true);
		textColumn_JSJyougen.setEditableOnInsert(true);
		textColumn_JSJyougen.setPreferredWidth(120);
		textColumn_JSJyougen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_JSJyougen.setColumnVisible(true);
		textColumn_JSJyougen.setColumnFilterable(false);
		textColumn_JSJyougen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_JSJyougen.setColumnVisible(true);
		textColumn_JSJyougen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.JS_JYOUGEN));
		
		// 単位
		textColumn_Tani.setColumnFilterable(true);
		textColumn_Tani.setColumnName("TANI");
		textColumn_Tani.setColumnSortable(true);
		textColumn_Tani.setPreferredWidth(300);
		textColumn_Tani.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Tani.setColumnVisible(true);
		textColumn_Tani.setColumnFilterable(false);
		textColumn_Tani.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Tani.setColumnVisible(true);
		textColumn_Tani.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.TANI));

		// 下限値
		textColumn_Kagen.setColumnFilterable(true);
		textColumn_Kagen.setColumnName("KAGEN");
		textColumn_Kagen.setColumnSortable(true);
		textColumn_Kagen.setPreferredWidth(80);
		textColumn_Kagen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Kagen.setColumnVisible(true);
		textColumn_Kagen.setColumnFilterable(false);
		textColumn_Kagen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Kagen.setColumnVisible(true);
		textColumn_Kagen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KAGEN));

		// 上限値
		textColumn_Jyogen.setColumnFilterable(true);
		textColumn_Jyogen.setColumnName("JYOUGEN");
		textColumn_Jyogen.setColumnSortable(true);
		textColumn_Jyogen.setPreferredWidth(80);
		textColumn_Jyogen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Jyogen.setColumnVisible(true);
		textColumn_Jyogen.setColumnFilterable(false);
		textColumn_Jyogen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Jyogen.setColumnVisible(true);
		textColumn_Jyogen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.JYOUGEN));

		// 基準値範囲
		textColumn_KijyuntiHani.setColumnFilterable(true);
		textColumn_KijyuntiHani.setColumnName("KIJYUNTI_HANI");
		textColumn_KijyuntiHani.setColumnSortable(true);
		textColumn_KijyuntiHani.setPreferredWidth(80);
		textColumn_KijyuntiHani.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_KijyuntiHani.setColumnVisible(true);
		textColumn_KijyuntiHani.setColumnFilterable(false);
		textColumn_KijyuntiHani.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_KijyuntiHani.setColumnVisible(true);
		textColumn_KijyuntiHani.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KIJYUNTI_HANI));

		// 単価健診
		textColumn_TankaKenshin.setColumnFilterable(true);
		textColumn_TankaKenshin.setColumnName("TANKA_KENSIN");
		textColumn_TankaKenshin.setColumnSortable(true);
		textColumn_TankaKenshin.setPreferredWidth(80);
		textColumn_TankaKenshin.setEditableOnEdit(true);
		textColumn_TankaKenshin.setEditableOnInsert(true);
		textColumn_TankaKenshin.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_TankaKenshin.setColumnVisible(true);
		textColumn_TankaKenshin.setColumnFilterable(false);
		textColumn_TankaKenshin.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_TankaKenshin.setColumnVisible(true);
		textColumn_TankaKenshin.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.TANKA_KENSIN));

		// 備考
		textColumn_Bikou.setColumnFilterable(true);
		textColumn_Bikou.setColumnName("BIKOU");
		textColumn_Bikou.setColumnSortable(true);
		textColumn_Bikou.setPreferredWidth(150);
		textColumn_Bikou.setEditableOnEdit(true);
		textColumn_Bikou.setEditableOnInsert(true);
		textColumn_Bikou.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Bikou.setColumnVisible(true);
		textColumn_Bikou.setColumnFilterable(false);
		textColumn_Bikou.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Bikou.setColumnVisible(true);
		textColumn_Bikou.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.BIKOU));

		/* button */
		setJButtons();

		// openswing s.inoue 2011/02/03
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
//	    buttonsPanel.add(insertButton, null);
	    buttonOriginePanel.add(editButton, null);
	    buttonOriginePanel.add(saveButton, null);
	    buttonOriginePanel.add(filterButton, null);
	    buttonOriginePanel.add(reloadButton, null);
//	    buttonOriginePanel.add(deleteButton, null);
	    buttonOriginePanel.add(exportButton, null);
	    buttonOriginePanel.add(navigatorBar, null);

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonExport,null);
	    buttonPanel.add(buttonImport,null);
	    buttonPanel.add(buttonDefault,null);	// edit n.ohkubo 2015/03/01　追加

		// action設定
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonExport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonImport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonDefault.addActionListener(new ListFrame_button_actionAdapter(this));	// edit n.ohkubo 2015/03/01　追加z

		// openswing s.inoue 2011/01/25
		Box origineBox = new Box(BoxLayout.X_AXIS);

//		origineBox.add(saveButton);
//		origineBox.add(editButton);
//		origineBox.add(reloadButton);
//		origineBox.add(deleteButton);
//		origineBox.add(saveButton);
//		origineBox.add(exportButton);
//		origineBox.add(navigatorBar);
		// openswing s.inoue 2011/02/03
		origineBox.add(buttonOriginePanel);

		origineBox.add(Box.createVerticalStrut(2));

//		// box2行目
		Box recordBox = new Box(BoxLayout.X_AXIS);
//		recordBox.add(buttonJusinkenAdd);
//		recordBox.add(buttonKekkaCopy);
//		recordBox.add(buttonJusinkenCall);
//		recordBox.add(buttonKekkaInputCall);
//		recordBox.add(buttonDeleteKekka);
//		recordBox.add(buttonDeleteKojin);
//		recordBox.add(buttonPrint);
//		recordBox.add(buttonIrai);

// openswing s.inoue 2011/02/03 combo未使用
//		LabelControl labelCombo = new LabelControl();
//	    labelCombo.setText("combobox");
//	    comboSelectHokenja.setCanCopy(true);
//	    comboSelectHokenja.setDomainId("ORDERSTATE");
//	    comboSelectHokenja.setLinkLabel(labelCombo);
//	    comboSelectHokenja.setRequired(true);
//	    recordBox.add(Box.createHorizontalStrut(2), BorderLayout.WEST);
//		recordBox.add(comboSelectHokenja);
//		recordBox.add(Box.createHorizontalStrut(2), BorderLayout.WEST);
		recordBox.add(buttonPanel);
//		recordBox.add(Box.createHorizontalStrut(910), BorderLayout.WEST);

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);

		// eidt s.inoue 2012/11/30
		filterButton.setMnemonic(KeyEvent.VK_F);
		reloadButton.setMnemonic(KeyEvent.VK_Z);

		grid.setEditButton(editButton);
		grid.setReloadButton(reloadButton);
//		grid.setDeleteButton(deleteButton);
		grid.setSaveButton(saveButton);
		grid.setNavBar(navigatorBar);
		grid.setExportButton(exportButton);
		grid.setFilterButton(filterButton);

		grid.setMaxSortedColumns(5);

		grid.setValueObjectClassName(CONST_DATA_VALUE);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_HokenjaNumber, null);
		grid.getColumnContainer().add(textColumn_KoumokuCd, null);
		grid.getColumnContainer().add(textColumn_KoumokuNm, null);
		grid.getColumnContainer().add(textColumn_KensaHouhou, null);
		grid.getColumnContainer().add(textColumn_HisuFlg, null);
		grid.getColumnContainer().add(textColumn_DSKagen, null);
		grid.getColumnContainer().add(textColumn_DSJyougen, null);
		grid.getColumnContainer().add(textColumn_JSKagen, null);
		grid.getColumnContainer().add(textColumn_JSJyougen, null);
		grid.getColumnContainer().add(textColumn_Tani, null);
		grid.getColumnContainer().add(textColumn_Kagen, null);
		grid.getColumnContainer().add(textColumn_Jyogen, null);
		grid.getColumnContainer().add(textColumn_KijyuntiHani, null);

		grid.getColumnContainer().add(textColumn_TankaKenshin, null);
		grid.getColumnContainer().add(textColumn_Bikou, null);

		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01　追加
		
		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.kenshin-item-mastermaintenance.frame.title","tokutei.kenshin-item-mastermaintenance.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);

		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel jPanel_TitleArea = null;
	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea(Box buttonBox) {
		if (jPanel_TitleArea == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridy = 1;
			gridBagConstraints26.gridx = 0;

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
//			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;

			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

	/* ボタンアクション用内部クラス */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JKenshinMasterMaintenanceListFrame adaptee;

		  ListFrame_button_actionAdapter(JKenshinMasterMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
		public void actionPerformed(ActionEvent e) {
			  Object source = e.getSource();
			  if (source == buttonClose){
				  logger.info(buttonClose.getText());
				  presevColumnStatus();//add tanaka 2013/11/15
				  adaptee.closeButtton_actionPerformed(e);
			  }else if(source == buttonExport){
				  logger.info(buttonExport.getText());
				  pushedExportButton(e);
			  }else if(source == buttonImport){
				  logger.info(buttonImport.getText());
				  pushedImportButton(e);
			  }
			// edit n.ohkubo 2015/03/01　追加　start
			  else if (source == buttonDefault) {
				  logger.info(buttonDefault.getText());
				  pushedDefaultButton();
			  }
			// edit n.ohkubo 2015/03/01　追加　end
		  }
		private void presevColumnStatus() {
			if(!textColumn_HokenjaNumber.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.HKNJANUM))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.HKNJANUM));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.HKNJANUM));
			}
			
			if(!textColumn_KoumokuCd.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_CD))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_CD));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KOUMOKU_CD));
			}
			
			if(!textColumn_KoumokuNm.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_NAME))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_NAME));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KOUMOKU_NAME));
			}
			
			if(!textColumn_KensaHouhou.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KENSA_HOUHOU))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KENSA_HOUHOU));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KENSA_HOUHOU));
			}
			
			if(!textColumn_HisuFlg.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.HISU_FLG))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.HISU_FLG));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.HISU_FLG));
			}
			
			if(!textColumn_DSKagen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.DS_KAGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_KAGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.DS_KAGEN));
			}
			
			if(!textColumn_DSJyougen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.DS_JYOUGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_JYOUGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.DS_JYOUGEN));
			}
			
			if(!textColumn_JSKagen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.JS_KAGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_KAGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.JS_KAGEN));
			}
			
			if(!textColumn_JSJyougen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.JS_JYOUGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_JYOUGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.JS_JYOUGEN));
			}
			
			if(!textColumn_Tani.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.TANI))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANI));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.TANI));
			}
			
			if(!textColumn_Kagen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KAGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KAGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KAGEN));
			}
			
			if(!textColumn_Jyogen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.JYOUGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JYOUGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.JYOUGEN));
			}
			
			if(!textColumn_KijyuntiHani.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KIJYUNTI_HANI))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KIJYUNTI_HANI));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KIJYUNTI_HANI));
			}
			
			if(!textColumn_TankaKenshin.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.TANKA_KENSIN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANKA_KENSIN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.TANKA_KENSIN));
			}
			
			if(!textColumn_Bikou.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.BIKOU))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.BIKOU));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.BIKOU));
			}
			
			// edit n.ohkubo 2014/10/01　追加 start
			//「表示 or 非表示」をDBへ登録する
			((JKenshinMasterMaintenanceListFrameCtrl)grid.getController()).preserveColumnStatus();
			// edit n.ohkubo 2014/10/01　追加 end
		}
		@Override
		public void keyPressed(KeyEvent e) {
			adaptee.closeButtton_keyPerformed(e);

		}
		@Override
		public void keyReleased(KeyEvent e) {

		}
		@Override
		public void keyTyped(KeyEvent e) {

		}
	}


	/* ボタン群 */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {

		// combobox設定
		Response res = ClientUtils.getData("loadCallOutTypes",new GridParams());
	    Domain d = new Domain("CALL_OUT_TYPES");
	    if (!res.isError()) {
	      JKenshinMasterMaintenanceHokenjyaVO vo = null;
	      java.util.List list = ((VOListResponse)res).getRows();
	      for(int i=0;i<list.size();i++) {
	        vo = (JKenshinMasterMaintenanceHokenjyaVO)list.get(i);
	        d.addDomainPair(vo.getHokenjyaNumber(),vo.getHokenjyaName());
	      }
	    }

//		if (comboSelectHokenja == null) {
//		    comboSelectHokenja = new ComboBoxControl();
//
////		    comboSelectHokenja.setPreferredSize(new Dimension(100,20));
////		    comboSelectHokenja.setVerticalAlignment(SwingConstants.BOTTOM);
////		    comboSelectHokenja.setHorizontalTextPosition(JLabel.CENTER);
////		    comboSelectHokenja.setVerticalTextPosition(SwingConstants.BOTTOM);
//
//		    comboSelectHokenja.setDomain(d);
//		    comboSelectHokenja.getComboBox().addItemListener(new ItemListener() {
//		      public void itemStateChanged(ItemEvent e) {
//		        if (e.getStateChange()==e.SELECTED) {
////		          hierarTreePanel.setProgressiveHIE02((BigDecimal)comboBoxControl1.getValue());
////		          hierarTreePanel.reloadTree();
//		          grid.clearData();
//		        }
//		      }
//		    });
//		    if (d.getDomainPairList().length==1)
//		      comboSelectHokenja.getComboBox().setSelectedIndex(0);
//		    else
//		      comboSelectHokenja.getComboBox().setSelectedIndex(-1);
//
//		    comboSelectHokenja.setDomainId("SEX");
//		}

		if (buttonClose == null) {
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
		}
		if (buttonExport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Export);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonExport= new ExtendedOpenGenericButton(
					"Exort","書出(O)","書出(ALT+O)",KeyEvent.VK_O,icon);
			buttonExport.addActionListener(this);
		}
		if (buttonImport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Import);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonImport= new ExtendedOpenGenericButton(
					"Import","取込(I)","取込(ALT+I)",KeyEvent.VK_I,icon);
			buttonImport.addActionListener(this);
		}
		
		// edit n.ohkubo 2015/03/01　追加　start
		if (buttonDefault == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Default);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDefault= new ExtendedOpenGenericButton(
					"Default","初期値(Q)","初期値(ALT+Q)",KeyEvent.VK_Q,icon);
			buttonDefault.addActionListener(this);
			
			//初期表示時は非活性
			buttonDefault.setEnabled(false);
		}
		//初期値ボタンの制御を追加
		if (editButton != null) {
			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//修正ボタン押下で活性化
					buttonDefault.setEnabled(true);
				}
			});
		}
		// edit n.ohkubo 2015/03/01　追加　end
		
//		// 結果削除
//		if (buttonDeleteKekka == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_DELETE_KEKKA);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonDeleteKekka = new GenericButton();
//			buttonDeleteKekka.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonDeleteKekka.setHorizontalTextPosition(JLabel.CENTER);
//			buttonDeleteKekka.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonDeleteKekka.setAttributeName("Close");
//			buttonDeleteKekka.setMnemonic(KeyEvent.VK_C);
//			buttonDeleteKekka.setText("結果削除");
//			buttonDeleteKekka.setToolTipText("結果削除(ALT+C)");
//			buttonDeleteKekka.setIcon(icon);
//		}
//
//		// 受診券削除
//		if (buttonDeleteKojin == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_DELETE_JUSINKEN);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonDeleteKojin = new GenericButton();
//			buttonDeleteKojin.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonDeleteKojin.setHorizontalTextPosition(JLabel.CENTER);
//			buttonDeleteKojin.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonDeleteKojin.setAttributeName("Close");
//			buttonDeleteKojin.setMnemonic(KeyEvent.VK_C);
//			buttonDeleteKojin.setText("受診券削除");
//			buttonDeleteKojin.setToolTipText("受診券削除(ALT+C)");
//			buttonDeleteKojin.setIcon(icon);
//		}
//
//		// 入力票印刷
//		if (buttonPrint == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_PRINT_NYURYOKUHYO);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonPrint = new GenericButton();
//			buttonPrint.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonPrint.setHorizontalTextPosition(JLabel.CENTER);
//			buttonPrint.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonPrint.setAttributeName("Close");
//			buttonPrint.setMnemonic(KeyEvent.VK_C);
//			buttonPrint.setText("入力票印刷");
//			buttonPrint.setToolTipText("入力票印刷(ALT+C)");
//			buttonPrint.setIcon(icon);
//		}
//
//		// 受診券追加
//		if (buttonJusinkenAdd == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_ADD_JUSINKEN);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonJusinkenAdd = new GenericButton();
//			buttonJusinkenAdd.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonJusinkenAdd.setHorizontalTextPosition(JLabel.CENTER);
//			buttonJusinkenAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonJusinkenAdd.setAttributeName("Close");
//			buttonJusinkenAdd.setMnemonic(KeyEvent.VK_C);
//			buttonJusinkenAdd.setText("受診券追加");
//			buttonJusinkenAdd.setToolTipText("受診券追加(ALT+C)");
//			buttonJusinkenAdd.setIcon(icon);
//		}
//
//		// 依頼書印刷
//		if (buttonIrai == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_PRINT_IRAISHO);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonIrai = new GenericButton();
//			buttonIrai.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonIrai.setHorizontalTextPosition(JLabel.CENTER);
//			buttonIrai.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonIrai.setAttributeName("Close");
//			buttonIrai.setMnemonic(KeyEvent.VK_C);
//			buttonIrai.setText("依頼書印刷");
//			buttonIrai.setToolTipText("依頼書印刷(ALT+C)");
//			buttonIrai.setIcon(icon);
//		}
//
//		// 結果複製
//		if (buttonKekkaCopy == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_COPY_KEKKA);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonKekkaCopy = new GenericButton();
//			buttonKekkaCopy.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonKekkaCopy.setHorizontalTextPosition(JLabel.CENTER);
//			buttonKekkaCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonKekkaCopy.setAttributeName("Close");
//			buttonKekkaCopy.setMnemonic(KeyEvent.VK_C);
//			buttonKekkaCopy.setText("結果複製");
//			buttonKekkaCopy.setToolTipText("結果複製(ALT+C)");
//			buttonKekkaCopy.setIcon(icon);
//		}
//
//		// 結果入力呼出
//		if (buttonKekkaInputCall == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_INPUT_KEKKA);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonKekkaInputCall = new GenericButton();
//			buttonKekkaInputCall.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonKekkaInputCall.setHorizontalTextPosition(JLabel.CENTER);
//			buttonKekkaInputCall.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonKekkaInputCall.setAttributeName("INPUT_KEKKA");
//			buttonKekkaInputCall.setMnemonic(KeyEvent.VK_C);
//			buttonKekkaInputCall.setText("結果入力");
//			buttonKekkaInputCall.setToolTipText("結果入力(ALT+C)");
//			buttonKekkaInputCall.setIcon(icon);
//		}
//
//		// 受診券呼出
//		if (buttonJusinkenCall == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_INPUT_JUSINKEN);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonJusinkenCall = new GenericButton();
//			buttonJusinkenCall.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonJusinkenCall.setHorizontalTextPosition(JLabel.CENTER);
//			buttonJusinkenCall.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonJusinkenCall.setAttributeName("INPUT_KEKKA");
//			buttonJusinkenCall.setMnemonic(KeyEvent.VK_C);
//			buttonJusinkenCall.setText("受診券呼出");
//			buttonJusinkenCall.setToolTipText("受診券呼出(ALT+C)");
//			buttonJusinkenCall.setIcon(icon);
//		}
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
			// del s.inoue 2011/04/07
			// initializeSetting();
			// initializeCombobox();

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
				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
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
// edit s.inoue 2011/04/07
//					if(!validateData(data))
//						return;
					validateData(data);

					// 健診マスタ登録
					kenshinMasterRegister(data);
				}

				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();
				reloadButton.doClick();

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

	/* validate処理 */
	private void validateData(JImportMasterErrorKenshinResultFrameData data) {
// eidt s.inoue 2011/04/07
//		boolean rettanka = false;
//		rettanka= validatedData.setHKNJANUM(data.CSV_COLUMN_HKNJANUM)
//			&& validatedData.setKOUMOKU_CD(data.CSV_COLUMN_KOUMOKU_CD)
//			&& validatedData.setHISU_FLG(data.CSV_COLUMN_HISU_FLG)
//			&& validatedData.setJS_KAGEN(data.CSV_COLUMN_JS_KAGEN)
//			&& validatedData.setJS_JYOUGEN(data.CSV_COLUMN_JS_JYOUGEN)
//			&& validatedData.setDS_KAGEN(data.CSV_COLUMN_DS_KAGEN)
//			&& validatedData.setDS_JYOUGEN(data.CSV_COLUMN_DS_JYOUGEN)
//			&& validatedData.setTANI(data.CSV_COLUMN_TANI)
//			&& validatedData.setTANKA_KENSIN(data.CSV_TANKA_KENSIN)
//			&& validatedData.setBIKOU(data.CSV_COLUMN_BIKOU);

		validatedData.setHKNJANUM(data.CSV_COLUMN_HKNJANUM);
		validatedData.setKOUMOKU_CD(data.CSV_COLUMN_KOUMOKU_CD);
		validatedData.setHISU_FLG(data.CSV_COLUMN_HISU_FLG);
		validatedData.setJS_KAGEN(data.CSV_COLUMN_JS_KAGEN);
		validatedData.setJS_JYOUGEN(data.CSV_COLUMN_JS_JYOUGEN);
		validatedData.setDS_KAGEN(data.CSV_COLUMN_DS_KAGEN);
		validatedData.setDS_JYOUGEN(data.CSV_COLUMN_DS_JYOUGEN);
		validatedData.setTANI(data.CSV_COLUMN_TANI);
		validatedData.setTANKA_KENSIN(data.CSV_TANKA_KENSIN);
		validatedData.setBIKOU(data.CSV_COLUMN_BIKOU);
//		return rettanka;
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
				+ JQueryConvert.queryConvertAppendComma(validatedData.getHISU_FLG()));
		buffer.append(" DS_KAGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getDS_KAGEN()));
		buffer.append(" DS_JYOUGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getDS_JYOUGEN()));
		buffer.append(" JS_KAGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getJS_KAGEN()));
		buffer.append(" JS_JYOUGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getJS_JYOUGEN()));
		buffer.append(" TANI = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getTANI()));
		buffer.append(" TANKA_KENSIN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getTANKA_KENSIN()));
		buffer.append(" BIKOU = "
				+ JQueryConvert.queryConvert(validatedData.getBIKOU()));
		buffer.append(" WHERE HKNJANUM ="
				+ JQueryConvert.queryConvert(validatedData.getHKNJANUM()));
		buffer.append(" AND KOUMOKU_CD ="
				+ JQueryConvert.queryConvert(validatedData.getKOUMOKU_CD()));

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

// del s.inoue 2011/04/07
//		if (jComboBox_HokenjyaNumber.getItemCount() <= 0)
//			return null;
//		String curHokenjyaNumber = new String(
//					((String) jComboBox_HokenjyaNumber.getSelectedItem()).substring(0, 8));

		StringBuilder sb = new StringBuilder();
		// edit s.inoue 2010/07/07
		sb.append(" SELECT HKNJANUM,KOUMOKU_CD,HISU_FLG,DS_KAGEN,DS_JYOUGEN,JS_KAGEN,JS_JYOUGEN,TANI,TANKA_KENSIN,BIKOU");
		sb.append(" FROM T_KENSHINMASTER");
//		sb.append(" WHERE HKNJANUM = " + JQueryConvert.queryConvert(curHokenjyaNumber));
		// eidt s.inoue 2011/04/07
		sb.append(" ORDER BY HKNJANUM,XMLITEM_SEQNO ");

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

	/* ボタンアクション */
	public void closeButtton_actionPerformed(ActionEvent e) {
		dispose();
	}
	/* ボタンアクション */
	public void closeButtton_keyPerformed(KeyEvent e) {
		dispose();
	}

	// イベント処理
	@Override
	public void actionPerformed(ActionEvent ae) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

//	/* ボタンアクション用内部クラス */
//	class ListFrame_closeButton_keyAdapter implements java.awt.event.KeyListener {
//	  JHokenjyaMasterMaintenanceListFrame adaptee;
//
//		ListFrame_closeButton_keyAdapter(JHokenjyaMasterMaintenanceListFrame adaptee) {
//		this.adaptee = adaptee;
//		}
//		@Override
//		public void keyPressed(KeyEvent e) {
//			adaptee.closeButtton_keyPerformed(e);
//		}
//		public void keyReleased(KeyEvent e) {
//		}
//		public void keyTyped(KeyEvent e) {
//
//		}
//	}
//	/* ボタンアクション */
//	public void closeButtton_keyPerformed(KeyEvent e) {
//		dispose();
//	}
	
	// edit n.ohkubo 2015/03/01　追加　start
	/**
	 * 初期値ボタン押下処理
	 */
	private void pushedDefaultButton() {
		
		try {
			//「再読込」実行で、セルの色とツールチップを戻すため、初回実行時に、初期設定のTableCellRendererを退避しておく
			if (tableCellRendererList == null) {
				tableCellRendererList = new ArrayList<TableCellRenderer>();
				if (isColumnVisible("DS_KAGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("DS_KAGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
				if (isColumnVisible("DS_JYOUGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("DS_JYOUGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
				if (isColumnVisible("JS_KAGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("JS_KAGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
				if (isColumnVisible("JS_JYOUGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("JS_JYOUGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
			}
			
			//デフォルト（HKNJANUM=99999999）のデータを取得
			Map<String, List<String>> defaultData = ((JKenshinMasterMaintenanceListFrameCtrl)grid.getController()).getDefaultData();
//			System.out.println(defaultData);
			
			//TableCellRendererで使用する値の保持用BeanList
			List<CellInfoBean> beanList_DS_KAGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			List<CellInfoBean> beanList_DS_JYOUGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			List<CellInfoBean> beanList_JS_KAGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			List<CellInfoBean> beanList_JS_JYOUGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			
			//テーブル（現在表示している値）のデータを取得
			VOListTableModel voListTableModel = grid.getVOListTableModel();
			for (int i = 0; i < voListTableModel.getRowCount(); i++) {
				
				//項目コードをキーにデフォルト値を取得
				List<String> defaultDataList = defaultData.get(voListTableModel.getField(i, "KOUMOKU_CD"));
				
				//デフォルト値と差異が有る場合、Beanをaddする
				//"基準値下限(男性)"
				String dispVal_DS_KAGEN = (voListTableModel.getField(i, "DS_KAGEN") != null) ? (String)voListTableModel.getField(i, "DS_KAGEN") : "";
				if (!defaultDataList.get(0).equals(dispVal_DS_KAGEN)) {
					beanList_DS_KAGEN.add(
							createCellInfoBean(
									"DS_KAGEN", 
									defaultDataList.get(0), 
									dispVal_DS_KAGEN, 
									i
							)
					);
				}
				//"基準値上限(男性)"
				String dispVal_DS_JYOUGEN = (voListTableModel.getField(i, "DS_JYOUGEN") != null) ? (String)voListTableModel.getField(i, "DS_JYOUGEN") : "";
				if (!defaultDataList.get(1).equals(dispVal_DS_JYOUGEN)) {
					beanList_DS_JYOUGEN.add(
							createCellInfoBean(
									"DS_JYOUGEN", 
									defaultDataList.get(1), 
									dispVal_DS_JYOUGEN, 
									i
							)
					);
				}
				//"基準値下限(女性)"
				String dispVal_JS_KAGEN = (voListTableModel.getField(i, "JS_KAGEN") != null) ? (String)voListTableModel.getField(i, "JS_KAGEN") : "";
				if (!defaultDataList.get(2).equals(dispVal_JS_KAGEN)) {
					beanList_JS_KAGEN.add(
							createCellInfoBean(
									"JS_KAGEN", 
									defaultDataList.get(2), 
									dispVal_JS_KAGEN, 
									i
							)
					);
				}
				//"基準値上限(女性)"
				String dispVal_JS_JYOUGEN = (voListTableModel.getField(i, "JS_JYOUGEN") != null) ? (String)voListTableModel.getField(i, "JS_JYOUGEN") : "";
				if (!defaultDataList.get(3).equals(dispVal_JS_JYOUGEN)) {
					beanList_JS_JYOUGEN.add(
							createCellInfoBean(
									"JS_JYOUGEN", 
									defaultDataList.get(3), 
									dispVal_JS_JYOUGEN, 
									i
							)
					);
				}
			}
			
			//デフォルト値と表示値に差異が有る場合、専用のTableCellRendererを設定し、「表示値」「セルの色」「ツールチップ」を変更する
			if (beanList_DS_KAGEN.size() != 0 && isColumnVisible("DS_KAGEN")) {
				createMyTableCellRenderer(beanList_DS_KAGEN);
			}
			if (beanList_DS_JYOUGEN.size() != 0 && isColumnVisible("DS_JYOUGEN")) {
				createMyTableCellRenderer(beanList_DS_JYOUGEN);
			}
			if (beanList_JS_KAGEN.size() != 0 && isColumnVisible("JS_KAGEN")) {
				createMyTableCellRenderer(beanList_JS_KAGEN);
			}
			if (beanList_JS_JYOUGEN.size() != 0 && isColumnVisible("JS_JYOUGEN")) {
				createMyTableCellRenderer(beanList_JS_JYOUGEN);
			}

			//セルの色表示の反映（ボタン押下だけでは表示が反映されないので、テーブルに対して何かアクションをかける（テーブルの行にマウスのカーソルがあたれば表示が反映されるが、ボタン押下のみで反映させる））
			grid.getTable().getGrid().clearSelection();
			
		} catch (Exception ex) {
			JErrorMessage.show("M3820", null);
			ex.printStackTrace();
		}
	}
	
	/**
	 * TableCellRendererの設定と、表示値をデフォルト値へ変える
	 * 
	 * @param cellInfoBeanList
	 */
	private void createMyTableCellRenderer(List<CellInfoBean> cellInfoBeanList) {
		
		//TableCellRendererで、「セルの色」と「ツールチップ」を変更する
		MyTableCellRenderer myTableCellRenderer = new MyTableCellRenderer(cellInfoBeanList);
		grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource(cellInfoBeanList.get(0).getAttributeName())).setCellRenderer(myTableCellRenderer);

		for (int i = 0; i < cellInfoBeanList.size(); i++) {
			//表示値をデフォルト値へ変える
			grid.getVOListTableModel().setField(cellInfoBeanList.get(i).getTargetRow(), cellInfoBeanList.get(i).getAttributeName(), cellInfoBeanList.get(i).getDefaultValue());
		}
	}

	
	/**
	 * カラムが表示されているかの判定
	 * 
	 * @param attributeName		対象のカラム名
	 * @return					表示している：true　非表示：false
	 */
	private boolean isColumnVisible(String attributeName) {
		boolean isResult = true;
		try {
			grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource(attributeName));
		} catch (IllegalArgumentException iex) {
			isResult = false;
		}
		return isResult;
	}

	/**
	 * 引数の値を設定したBeanを作成する
	 * 
	 * @param attributeName
	 * @param defaultValu
	 * @param oldValue
	 * @param row
	 * 
	 * @return
	 */
	private CellInfoBean createCellInfoBean(String attributeName, String defaultValu, String oldValue, int row) {
		
		CellInfoBean bean = new CellInfoBean();
		bean.setAttributeName(attributeName);
		bean.setDefaultValue(defaultValu);
		bean.setOldValue(oldValue);
		bean.setTargetColumn(getColumnindex(attributeName));
		bean.setTargetRow(row);
		return bean;
	}
	
	/**
	 * 基準値（男女の上下限）のカラムのindexを取得する
	 * ※前の項目が「非表示」だったり、カラムの位置を「移動」していても、現在表示しているindexを取得する
	 * 
	 * @param attributeName	基準値（男女の上下限）のカラム名
	 * @return
	 */
	private int getColumnindex(String attributeName) {
		
		int modelColumnIndex = (
				"DS_KAGEN".equals(attributeName) ? 5 : 
					"DS_JYOUGEN".equals(attributeName) ? 6 : 
						"JS_KAGEN".equals(attributeName) ? 7 : 8);
		
		int result = grid.getTable().getGrid().convertColumnIndexToView(modelColumnIndex);
//		System.out.println("attributeName:[" + attributeName + "] index:[" + modelColumnIndex + "] result:[" + result + "]");
		return result;
	}
	
	
	/**
	 * セルの色とツールチップに設定する文言を変更するクラス
	 */
	private class MyTableCellRenderer implements TableCellRenderer {
		
		private int targetColumn;
		private List<CellInfoBean> cellInfoBeanList;
		private List<TableCellRenderer> rendererList;
		
		/**
		 * コンストラクタ
		 * 
		 * @param cellInfoBeanList
		 */
		public MyTableCellRenderer(List<CellInfoBean> cellInfoBeanList) {
			
			this.cellInfoBeanList = cellInfoBeanList;
			this.targetColumn = cellInfoBeanList.get(0).getTargetColumn();	//targetColumnの値は同じはずなので、ここで一度だけセット
			this.rendererList = new ArrayList<TableCellRenderer>();			//「table.prepareRenderer()」内で直接設定すると、「StackOverflowError」になるのでここで設定しておく
			for (int i = 0; i < grid.getTable().getGrid().getModel().getRowCount(); i++) {
				TableCellRenderer renderer = grid.getTable().getGrid().getCellRenderer(i, targetColumn);
				this.rendererList.add(renderer);
			}
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//			System.out.println("MyTableCellRenderer targetColumn:[" + targetColumn + "] value:[" + (String)value + "] row:[" + row + "] column:[" + column + "]");
			
			//セル
			Component component = null;
			try {
				//ツールチップの文言
				String toolTipText = null;
				afterDefaultToolTipText = null;
				
				for (int i = 0; i < cellInfoBeanList.size(); i++) {
					
					//行と列のIndexから、対象のセルを判定
					if ((targetColumn == column) && (row == cellInfoBeanList.get(i).getTargetRow())) {
						
						//セルのツールチップ文言を変更
						toolTipText = "\"" + cellInfoBeanList.get(i).getOldValue() + "\"から\"" + ((cellInfoBeanList.get(i).getDefaultValue() != null) ? cellInfoBeanList.get(i).getDefaultValue() : "") + "\"へ変更";
						afterDefaultToolTipText = toolTipText;
						
						//変更対象のセルを取得（prepareRendererメソッド内でCtrlクラスのgetCellTooltipメソッドが呼ばれる？ので、位置に注意）
						component = table.prepareRenderer(rendererList.get(row), row, column);
						
						//セルの色を変更
						component.setBackground(Color.PINK);
						
						break;
					}
				}
				
				//変更対象のセルが無かった場合
				if (component == null) {
					component = table.prepareRenderer(rendererList.get(row), row, column);
				}
//				System.out.println("ToolTipText:[" + toolTipText + "]　afterDefaultToolTipText:[" + afterDefaultToolTipText + "]");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return component;
		}
	}
	
	/**
	 * セル情報（TableCellRenderer等で使用）を保持するBean
	 */
	private class CellInfoBean {
		private int targetRow;
		private int targetColumn;
		private String oldValue;
		private String defaultValue;
		private String attributeName;
		
		public int getTargetRow() {
			return targetRow;
		}
		public void setTargetRow(int targetRow) {
			this.targetRow = targetRow;
		}
		public int getTargetColumn() {
			return targetColumn;
		}
		public void setTargetColumn(int targetColumn) {
			this.targetColumn = targetColumn;
		}
		public String getOldValue() {
			return oldValue;
		}
		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}
		public String getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
		public String getAttributeName() {
			return attributeName;
		}
		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
		}
	}
	// edit n.ohkubo 2015/03/01　追加　end
}
