package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Serche;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenNavigatorBar;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintIraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintNyuryoku;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.table.client.Grid;
import org.openswing.swing.table.columns.client.CheckBoxColumn;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.util.java.Consts;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinKekkaSearchListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = 3980065657202166734L;	// edit n.ohkubo 2014/10/01　追加
	
	protected Connection conn = null;
	// eidt s.inoue 2012/02/27
	protected GridControl grid = new GridControl();
//	protected ExtendendGridControl grid = new ExtendendGridControl(new DefaultTableCellRenderer());

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();  //  @jve:decl-index=0:
	protected FlowLayout flowLayoutPanel = new FlowLayout();  //  @jve:decl-index=0:

	/* button 順番 */
//	protected InsertButton insertButton = new InsertButton();
//	protected EditButton editButton = new EditButton();
//	protected SaveButton saveButton = new SaveButton();
//	protected DeleteButton deleteButton = new DeleteButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	// protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	// del s.inoue 2012/11/14
//	protected ExportButton exportButton = new ExportButton();
	protected ExtendedOpenNavigatorBar navigatorBar = new ExtendedOpenNavigatorBar();
	protected TextControl countText = new TextControl();

	// buttonコントロール
	protected ExtendedOpenGenericButton buttonCheck = null;
	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonDeleteKekka = null;
	protected ExtendedOpenGenericButton buttonDeleteKojin = null;
	protected ExtendedOpenGenericButton buttonJusinkenAdd = null;
	protected ExtendedOpenGenericButton buttonNyuryokuPrint = null;
	protected ExtendedOpenGenericButton buttonIraiPrint = null;
	protected ExtendedOpenGenericButton buttonKekkaCopy = null;
	protected ExtendedOpenGenericButton buttonJusinkenCall = null;
	protected ExtendedOpenGenericButton buttonKekkaInputCall = null;
// edit s.inoue 2011/02/16 保留
//	protected CheckBoxControl checkBoxSelect = null;

	/* control */
	protected TextColumn textColumn_Name = new TextColumn();
	protected TextColumn textColumn_HokensyoCode = new TextColumn();
	protected TextColumn textColumn_HokensyoNumber = new TextColumn();
	protected TextColumn dateColumn_KenshinDate = new TextColumn();
	protected TextColumn dateColumn_KenshinDateTo = new TextColumn();

	// eidt s.inoue 2012/10/23
	// protected TextColumn textColumn_sex = new TextColumn();
	protected ComboColumn textColumn_sex = new ComboColumn();
	protected ComboColumn textColumn_inputFlg = new ComboColumn();
	protected ComboColumn textColumn_hokenjaNo = new ComboColumn();

	protected TextColumn textColumn_birthdayFrom = new TextColumn();
	protected TextColumn textColumn_birthdayTo = new TextColumn();

	protected TextColumn textColumn_jyushinSeiriNo = new TextColumn();
	protected TextColumn textColumn_shiharaiDaikouNo = new TextColumn();
	protected TextColumn textColumn_kanaName = new TextColumn();
	protected TextColumn textColumn_hanteiNengapi = new TextColumn();
	protected TextColumn textColumn_tutiNengapi = new TextColumn();
	protected TextColumn textColumn_nendo = new TextColumn();
	protected CheckBoxColumn checkColumn_checkBox = new CheckBoxColumn();

	private IDialog dateSelectDialog;

	protected int currentRow = 0;
	protected int currentPage = 0;
	protected int currentTotalRows = 0;

	private static final String SQL_DELETE_KOJIN ="delete from T_KOJIN where UKETUKE_ID = ? ";
	private static final String SQL_DELETE_NAYOSE ="delete from T_NAYOSE where UKETUKE_ID = ? ";
	private static final String SQL_SELECT_TOKUTEI ="select count(1) as num from T_KENSAKEKA_TOKUTEI where UKETUKE_ID = ? ";  //  @jve:decl-index=0:
	private static final String SQL_DELETE_TOKUTEI ="delete from T_KENSAKEKA_TOKUTEI where UKETUKE_ID = ? and KENSA_NENGAPI = ? ";
	private static final String SQL_DELETE_SONOTA ="delete from T_KENSAKEKA_SONOTA where UKETUKE_ID = ? and KENSA_NENGAPI = ? ";

	protected final static String CONST_VALUE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameData";

	private static Logger logger = Logger.getLogger(JKenshinKekkaSearchListFrame.class);  //  @jve:decl-index=0:
	private boolean chkFlg = false;
	
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
	
	// edit n.ohkubo 2015/03/01　追加 start
	private boolean isKeyPressed = false;
	public boolean isKeyPressed() {
		return this.isKeyPressed;
	}
	// edit n.ohkubo 2015/03/01　追加 end

	/* コンストラクタ */
	public JKenshinKekkaSearchListFrame(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller) {
		setControl(conn,controller);

		addKeyListener(this);
	}

	/* リロード */
	public void reloadData() {
		grid.reloadData();
	}

	/* グリッドgetter */
	public GridControl getGrid() {
		return grid;
	}

//	private JKenshinKekkaSearchListFrameCtl controller = null;  //  @jve:decl-index=0:	// edit n.ohkubo 2014/10/01　未使用なので削除

	/* 制御メソッド */
	public void setControl(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

//			this.controller = controller;	// edit n.ohkubo 2014/10/01　未使用なので削除
			
			// edit n.ohkubo 2014/10/01　追加 start　検索画面にチェックボックスを追加
			//フィルターパネル用のWindowListener
			SpecialFilterPanel specialFilterPanel = new SpecialFilterPanel(savedJCheckBox, grid.getParent().getComponents());
			
			//このフレーム（一覧画面）がアクティブ化（画面右の検索ウィンドウ）　or　非アクティブ化（検索ウィンドウがポップアップで開かれ場合）されたときに動作するように、Listenerを設定（FilterPanelへのチェックボックス追加はListener内で行う）
			this.addWindowListener(specialFilterPanel);
			// edit n.ohkubo 2014/10/01　追加 end　検索画面にチェックボックスを追加
			
			// edit n.ohkubo 2015/03/01　追加　start　「Alt+E」等が正常に動作しない対応（キー押下でチェックボックスの値が反転する）
			Grid table = grid.getTable().getGrid();
			table.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {
					isKeyPressed = false;
				}
				@Override
				public void keyPressed(KeyEvent e) {
					isKeyPressed = true;
				}
			});
			// edit n.ohkubo 2015/03/01　追加　end　「Alt+E」等が正常に動作しない対応（キー押下でチェックボックスの値が反転する）

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 初期化処理 */
	/**
	 * @throws Exception
	 */
	public void jbInit() throws Exception {
		/* control ※ClientApplicationと一致*/
		textColumn_Name.setColumnFilterable(true);
		textColumn_Name.setColumnName("NAME");
		textColumn_Name.setColumnSortable(true);
		textColumn_Name.setPreferredWidth(120);
		// eidt s.inoue 2012/10/24
		textColumn_Name.setColumnFilterable(false);
		textColumn_Name.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_Name.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.NAME));
		// textColumn_Name.setColumnVisible(false);
		
		textColumn_HokensyoCode.setColumnFilterable(true);
		textColumn_HokensyoCode.setColumnName("HIHOKENJYASYO_KIGOU");
		textColumn_HokensyoCode.setColumnSortable(true);
		textColumn_HokensyoCode.setPreferredWidth(110);
		// eidt s.inoue 2012/10/24
		textColumn_HokensyoCode.setColumnFilterable(false);
		textColumn_HokensyoCode.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_HokensyoCode.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));

		textColumn_HokensyoNumber.setColumnFilterable(true);
		textColumn_HokensyoNumber.setColumnName("HIHOKENJYASYO_NO");
		textColumn_HokensyoNumber.setColumnSortable(true);
		textColumn_HokensyoNumber.setPreferredWidth(110);
		// eidt s.inoue 2012/10/24
		textColumn_HokensyoNumber.setColumnFilterable(false);
		textColumn_HokensyoNumber.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_HokensyoNumber.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_NO));

		dateColumn_KenshinDate.setColumnFilterable(true);
//		dateColumn_KenshinDate.setColumnName("KENSA_NENGAPI");	// edit n.ohkubo 2014/10/01　削除
		dateColumn_KenshinDate.setColumnName("KENSA_NENGAPI2");	// edit n.ohkubo 2014/10/01　追加
		dateColumn_KenshinDate.setColumnSortable(true);
		dateColumn_KenshinDate.setPreferredWidth(80);

		// edit s.inoue 2013/11/12
		dateColumn_KenshinDate.setColumnFilterable(true);
		dateColumn_KenshinDate.setColumnSortable(false);
		dateColumn_KenshinDate.setColumnSelectable(false);
		dateColumn_KenshinDate.setColumnVisible(false);
		dateColumn_KenshinDate.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01　追加
		
		// add s.inoue 2012/10/23
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateTo.setColumnSortable(true);
		dateColumn_KenshinDateTo.setColumnSelectable(true);//eidt tanaka 2013/11/08

		//add tanaka 2013/11/06
		dateColumn_KenshinDateTo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.KENSA_NENGAPI));
		dateColumn_KenshinDateTo.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01　追加

// eidt s.inoue 2012/10/23
//		textColumn_sex.setColumnFilterable(true);
//		textColumn_sex.setColumnName("SEX");
//		textColumn_sex.setColumnSortable(true);
//		textColumn_sex.setPreferredWidth(40);
		textColumn_sex.setColumnFilterable(true);
		textColumn_sex.setColumnName("SEX");
		textColumn_sex.setColumnSortable(true);
		textColumn_sex.setPreferredWidth(40);
	    textColumn_sex.setEditableOnEdit(true);
	    textColumn_sex.setEditableOnInsert(true);
	    textColumn_sex.setColumnRequired(false);
	    textColumn_sex.setDomainId("SEX");
	    //add tanaka 2013/11/06
	    textColumn_sex.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.SEX));

		textColumn_birthdayFrom.setColumnFilterable(true);
//		textColumn_birthdayFrom.setColumnName("BIRTHDAY");// edit n.ohkubo 2014/10/01　削除
		textColumn_birthdayFrom.setColumnName("BIRTHDAY2");// edit n.ohkubo 2014/10/01　追加
		textColumn_birthdayFrom.setColumnSortable(true);
		textColumn_birthdayFrom.setPreferredWidth(80);

		// edit s.inoue 2013/11/12
		textColumn_birthdayFrom.setColumnVisible(false);
		textColumn_birthdayFrom.setColumnFilterable(true);
		textColumn_birthdayFrom.setColumnSortable(false);
		textColumn_birthdayFrom.setColumnSelectable(false);
		textColumn_birthdayFrom.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01　追加
		
		// eidt s.inoue 2012/10/24
		textColumn_birthdayTo.setColumnName("BIRTHDAY");
		textColumn_birthdayTo.setPreferredWidth(80);

		//add tanaka 2013/11/06
		textColumn_birthdayTo.setColumnFilterable(true);
		textColumn_birthdayTo.setColumnSortable(true);
		textColumn_birthdayTo.setColumnSelectable(true);//eidt tanaka 2013/11/08
		textColumn_birthdayTo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.BIRTHDAY));
		textColumn_birthdayTo.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01　追加

		// eidt s.inoue 2012/10/23
//		textColumn_inputFlg.setColumnFilterable(true);
//		textColumn_inputFlg.setColumnName("KEKA_INPUT_FLG");
//		textColumn_inputFlg.setColumnSortable(true);
//		textColumn_inputFlg.setPreferredWidth(40);
		textColumn_inputFlg.setColumnFilterable(true);
		textColumn_inputFlg.setColumnName("KEKA_INPUT_FLG");
		textColumn_inputFlg.setColumnSortable(true);
		
		textColumn_inputFlg.setPreferredWidth(40);
	    textColumn_inputFlg.setEditableOnEdit(true);
	    textColumn_inputFlg.setEditableOnInsert(true);
	    textColumn_inputFlg.setColumnRequired(false);
	    textColumn_inputFlg.setDomainId("KEKA_INPUT_FLG");
	    //add tanaka2013/11/06
	    textColumn_inputFlg.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.KEKA_INPUT_FLG));

		textColumn_jyushinSeiriNo.setColumnFilterable(true);
		textColumn_jyushinSeiriNo.setColumnName("JYUSHIN_SEIRI_NO");
		textColumn_jyushinSeiriNo.setColumnSortable(true);
		textColumn_jyushinSeiriNo.setPreferredWidth(100);

		// add s.inoue 2013/10/29
		textColumn_jyushinSeiriNo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
		textColumn_jyushinSeiriNo.setMaxCharacters(11);	// edit n.ohkubo 2014/10/01　追加

		textColumn_hokenjaNo.setColumnFilterable(true);
		textColumn_hokenjaNo.setColumnName("HKNJANUM");
		textColumn_hokenjaNo.setColumnSortable(true);
		textColumn_hokenjaNo.setPreferredWidth(250);
		textColumn_hokenjaNo.setDomainId("T_HOKENJYA");
		//add tanaka 2013/11/06
		textColumn_hokenjaNo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HKNJANUM));

		textColumn_shiharaiDaikouNo.setColumnFilterable(true);
		textColumn_shiharaiDaikouNo.setColumnName("SIHARAI_DAIKOU_BANGO");
		textColumn_shiharaiDaikouNo.setColumnSortable(true);
		textColumn_shiharaiDaikouNo.setPreferredWidth(150);
		// eidt s.inoue 2012/10/24
		textColumn_shiharaiDaikouNo.setColumnVisible(false);
		textColumn_shiharaiDaikouNo.setColumnFilterable(false);
		textColumn_shiharaiDaikouNo.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_shiharaiDaikouNo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));

		textColumn_kanaName.setColumnFilterable(true);
		textColumn_kanaName.setColumnName("KANANAME");
		textColumn_kanaName.setColumnSortable(true);
		textColumn_kanaName.setPreferredWidth(175);
		//add tanaka 2013/11/06
		textColumn_kanaName.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.KANANAME));
		textColumn_kanaName.setMaxCharacters(50);	// edit n.ohkubo 2014/10/01　追加
		
		textColumn_hanteiNengapi.setColumnFilterable(true);
		textColumn_hanteiNengapi.setColumnName("HANTEI_NENGAPI");
		textColumn_hanteiNengapi.setColumnSortable(true);
		textColumn_hanteiNengapi.setPreferredWidth(80);
		// eidt s.inoue 2012/10/24
		textColumn_hanteiNengapi.setColumnFilterable(false);
		textColumn_hanteiNengapi.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_hanteiNengapi.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.HANTEI_NENGAPI));

		textColumn_tutiNengapi.setColumnFilterable(true);
		textColumn_tutiNengapi.setColumnName("TUTI_NENGAPI");
		textColumn_tutiNengapi.setColumnSortable(true);
		textColumn_tutiNengapi.setPreferredWidth(80);
		// eidt s.inoue 2012/10/24
		textColumn_tutiNengapi.setColumnFilterable(false);
		textColumn_tutiNengapi.setColumnSortable(false);
		//add tanaka 2013/11/06
		textColumn_tutiNengapi.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.TUTI_NENGAPI));

		textColumn_nendo.setColumnFilterable(true);
		textColumn_nendo.setColumnName("NENDO");
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setPreferredWidth(40);
		// add s.inoue 2012/10/24
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setColumnSelectable(true);

		textColumn_nendo.setEditableOnEdit(true);
		textColumn_nendo.setEditableOnInsert(true);
		textColumn_nendo.setColumnDuplicable(true);
		textColumn_nendo.setColumnRequired(false);
		textColumn_nendo.setSelectDataOnEdit(true);
		//add tanaka 2013/11/06
		textColumn_nendo.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.NENDO));
		textColumn_nendo.setMaxCharacters(4);	// edit n.ohkubo 2014/10/01　追加

// del s.inoue 2012/11/28
		checkColumn_checkBox.setColumnFilterable(false);
		checkColumn_checkBox.setColumnSortable(false);
		checkColumn_checkBox.setColumnName("CHECKBOX_COLUMN");
		checkColumn_checkBox.setPreferredWidth(50);
		//add tanaka 2013/11/06
		checkColumn_checkBox.setColumnVisible(!JApplication.flag.contains(FlagEnum_Serche.CHECKBOX_COLUMN));
		// eidt s.inoue 2012/11/28
		checkColumn_checkBox.setEditableOnEdit(true);
		checkColumn_checkBox.setEditableOnInsert(true);
		checkColumn_checkBox.setPositiveValue("Y");
		checkColumn_checkBox.setNegativeValue("N");
		// checkColumn_checkBox.setListFilter(null);

		// add s.inoue 2012/11/29
		checkColumn_checkBox.setFocusable(false);
		checkColumn_checkBox.setEnabled(false);

		// add
		checkColumn_checkBox.setColumnDuplicable(true);
		checkColumn_checkBox.setColumnRequired(false);
		// eidt s.inoue 2012/11/29
		checkColumn_checkBox.setEnableInReadOnlyMode(true);
		checkColumn_checkBox.setAllowNullValue(false);

		// add s.inoue 2013/11/19
		if(JApplication.currentSortedColumns != null){
			
			for (int i = 0; i < JApplication.currentSortedColumns.size(); i++) {
    			String sortclumn = (String)JApplication.currentSortedColumns.get(i);
    			if(sortclumn.equals("KANANAME")){
    				textColumn_kanaName.setSortingOrder(i+1);
    				
    				String sort = JApplication.currentSorted.get(i);
    	  			if(sort.equals("ASC")){
        				textColumn_kanaName.setSortVersus(Consts.ASC_SORTED);
        			}else if (sort.equals("DESC")){
        				textColumn_kanaName.setSortVersus(Consts.DESC_SORTED);
        			}
    			}
    			
    			if(sortclumn.equals("JYUSHIN_SEIRI_NO")){
    				textColumn_kanaName.setSortingOrder(i+1);
    				
    				String sort = JApplication.currentSorted.get(i);
    	  			if(sort.equals("ASC")){
        				textColumn_jyushinSeiriNo.setSortVersus(Consts.ASC_SORTED);
        			}else if (sort.equals("DESC")){
        				textColumn_jyushinSeiriNo.setSortVersus(Consts.DESC_SORTED);
        			}
    			}
			}
		}

		// openswing s.inoue 2011/01/25
		/* button */
		// closeボタン
		setJButtons();

		// openswing s.inoue 2011/02/03
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
//	    buttonsPanel.add(insertButton, null);
//	    buttonOriginePanel.add(editButton, null);
//	    buttonOriginePanel.add(saveButton, null);
//	    buttonOriginePanel.add(deleteButton, null);
	    buttonOriginePanel.add(filterButton, null);
	    buttonOriginePanel.add(reloadButton, null);
//	    buttonOriginePanel.add(exportButton, null);
	    buttonOriginePanel.add(buttonCheck,null);
	    buttonOriginePanel.add(navigatorBar, null);
	    buttonOriginePanel.add(countText, null);

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);

	    // eidt s.inoue 2011/04/21
//	    buttonPanel.add(buttonCheck,null);

	    buttonPanel.add(buttonClose, null);
//	    buttonPanel.add(checkBoxSelect,null);
	    buttonPanel.add(buttonJusinkenCall, null);
	    buttonPanel.add(buttonJusinkenAdd, null);
	    buttonPanel.add(buttonDeleteKojin, null);
	    buttonPanel.add(buttonKekkaInputCall, null);
	    buttonPanel.add(buttonKekkaCopy, null);
	    buttonPanel.add(buttonDeleteKekka, null);
	    buttonPanel.add(buttonNyuryokuPrint, null);
	    buttonPanel.add(buttonIraiPrint, null);

//	    checkBoxSelect.addActionListener(new ListFrame_closeButton_actionAdapter(this));
	    buttonCheck.addActionListener(new ListFrame_button_actionAdapter(this));
	    buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonKekkaInputCall.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonJusinkenCall.addActionListener(new ListFrame_button_actionAdapter(this));

		buttonJusinkenAdd.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonDeleteKekka.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonDeleteKojin.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonKekkaCopy.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonNyuryokuPrint.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonIraiPrint.addActionListener(new ListFrame_button_actionAdapter(this));

		// add s.inoue 2012/11/21
		navigatorBar.addAfterActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	if (JApplication.selectedHistoryRows == null)return;
	    		for (int i = 0; i < JApplication.selectedHistoryRows.size(); i++) {
	    			grid.getVOListTableModel().setValueAt("N", JApplication.selectedHistoryRows.get(i), 0);
	    			// eidt s.inoue 2013/11/07
	    			// JApplication.selectedHistoryRows.remove(i);
	    		}
	    		JApplication.selectedHistoryRows.removeAll(JApplication.selectedHistoryRows);
	          }
	    });

		Box origineBox = new Box(BoxLayout.X_AXIS);
		origineBox.add(buttonOriginePanel);
		origineBox.add(Box.createVerticalStrut(2));

		// box2行目
		Box recordBox = new Box(BoxLayout.X_AXIS);
		recordBox.add(buttonPanel);
		recordBox.add(Box.createVerticalStrut(2));

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		// openswing s.inoue 2011/01/25
//		buttonsPanel.add(closeButton,null);

		/* list */
		// eidt s.inoue 2012/10/11
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
		// grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.EQUALS);

//		grid.setInsertButton(insertButton);
//		grid.setEditButton(editButton);
//		grid.setDeleteButton(deleteButton);
		grid.setFilterButton(filterButton);

		grid.setReloadButton(reloadButton);
//		grid.setExportButton(exportButton);
		grid.setNavBar(navigatorBar);

		/* list */
//		grid.setInsertButton(insertButton);
		grid.setMaxSortedColumns(5);
		grid.setNavBar(navigatorBar);
//		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName(CONST_VALUE);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(checkColumn_checkBox, null);
		grid.getColumnContainer().add(textColumn_nendo, null);
		grid.getColumnContainer().add(textColumn_jyushinSeiriNo, null);
		grid.getColumnContainer().add(textColumn_kanaName, null);
		grid.getColumnContainer().add(textColumn_Name, null);
		grid.getColumnContainer().add(textColumn_birthdayFrom, null);
		grid.getColumnContainer().add(textColumn_birthdayTo, null);

		grid.getColumnContainer().add(textColumn_sex, null);
		grid.getColumnContainer().add(textColumn_inputFlg, null);
		grid.getColumnContainer().add(textColumn_HokensyoCode, null);
		grid.getColumnContainer().add(textColumn_HokensyoNumber, null);
		grid.getColumnContainer().add(dateColumn_KenshinDate, null);
		// eidt s.inoue 2012/10/23
		grid.getColumnContainer().add(dateColumn_KenshinDateTo, null);

		grid.getColumnContainer().add(textColumn_hanteiNengapi, null);
		grid.getColumnContainer().add(textColumn_tutiNengapi, null);
		grid.getColumnContainer().add(textColumn_hokenjaNo, null);
		grid.getColumnContainer().add(textColumn_shiharaiDaikouNo, null);
		
		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01　追加

//		// openswing s.inoue 2011/01/26
//		grid.addMouseListener(new JSingleDoubleClickEvent(this, button,modelfixed));

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.kekkalist.frame.title","tokutei.kekkalist.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		// openswing s.inoue 2011/01/25
		// 2段構えのボタン構成
		// this.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);

		// this.setTitle(ClientSettings.getInstance().getResources().getResource("UKETUKE_ID"));
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
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener {
	  JKenshinKekkaSearchListFrame adaptee;

		  ListFrame_button_actionAdapter(JKenshinKekkaSearchListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  // buttonアクション
		  @Override
		public void actionPerformed(ActionEvent e) {
		   	Object source = e.getSource();

		   	// del s.inoue 2011/05/09
		   	// selectedData = new ArrayList<Integer>();
		   	// eidt s.inoue 2011/04/21
		   	if (source == buttonCheck){
				logger.info(buttonCheck.getText());
				setCheckBoxValue();
		   	}else if (source == buttonClose){
				logger.info(buttonClose.getText());
				// add s.inoue 2013/10/29
				preservColumnStatus();
				adaptee.closeButtton_actionPerformed(e);
			/* 結果データ入力ボタン */
			}else if (source == buttonKekkaInputCall){
				logger.info(buttonKekkaInputCall.getText());
				if (!getCheckBoxCount())
					showKekkaRegisterFrame(false);
			/* 受診券呼出 */
			}else if (source == buttonJusinkenCall){
				logger.info(buttonJusinkenCall.getText());
				// add s.inoue 2012/11/30
				if (JApplication.selectedPreservRows.size() == 0)
					setSelectedRow(JApplication.selectedPreservRows);
				if (!getCheckBoxCount())
					pushedKojinButton(e);
			/* 受診券追加 */
			}else if (source == buttonJusinkenAdd) {
				logger.info(buttonJusinkenAdd.getText());
				pushedKojinAddButton(e);
			}
			/* 健診データ削除 */
			else if (source == buttonDeleteKekka) {
				logger.info(buttonDeleteKekka.getText());
				pushedDeleteKekkaButton();
				// edit s.inoue 2012/11/21
				// grid.reloadData();
				reloadButton.doClick();
				/* 受診券削除 */
			} else if (source == buttonDeleteKojin) {
				logger.info(buttonDeleteKojin.getText());
				pushedDeleteKojinButton();
				// edit s.inoue 2012/11/21
				// grid.reloadData();
				reloadButton.doClick();
				/* 結果データ複製ボタン */
			} else if (source == buttonKekkaCopy) {
				logger.info(buttonKekkaCopy.getText());
				showKekkaRegisterFrame(true);
		   	/* 入力票印刷ボタン */
			} else if (source == buttonNyuryokuPrint) {
				logger.info(buttonNyuryokuPrint.getText());
				pushedPrintButton(e);
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			/* 依頼書ボタン */
			} else if (source == buttonIraiPrint) {
				logger.info(buttonIraiPrint.getText());
				pushedPrintIraiButton(e);
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			}

// del s.inoue 2012/11/05
//			if (source != buttonDeleteKojin) {
//				// eidt s.inoue 2011/04/28
//				// if (!chkFlg)
//				if (chkFlg)
//				selectedData = getSelectedRow();
//			}
//				reloadButton.doClick();
//
//				// 自動リロードするスレッド
//				Thread reload = new ActionAutoReloadThread();
//				reload.start();

		  }
//		@Override
//		public void keyPressed(KeyEvent e) {
//			adaptee.closeButtton_keyPerformed(e);
//		}
//		@Override
//		public void keyReleased(KeyEvent e) {
//		}
//		@Override
//		public void keyTyped(KeyEvent e) {
//		}
	}

	// add s.inoue 2012/11/28
	// チェックボックス設定
	private void setSelectedRow(ArrayList<Integer> selectedRows){
		if (selectedRows == null)return;
		int ival = selectedRows.size();
		for (int i = 0; i < ival; i++) {
			grid.getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
		}
	}

	// eidt s.inoue 2011/04/21
	private void setCheckBoxValue(){
//		JKenshinKekkaSearchListFrameData chk = null;	// edit n.ohkubo 2014/10/01　未使用なので削除
		// eidt s.inoue 2011/04/27
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (chk.getCHECKBOX_COLUMN().equals("Y")){
//				if (selectedData.size() == 0)break;
//				selectedData.remove(i);
//			}else{
//				selectedData.add(i);
//			}
//		}
		if (chkFlg){
			for (int i = grid.getVOListTableModel().getRowCount()-1; i >= 0; i--) {
				// eidt s.inoue 2012/11/05
				// chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (chk.getCHECKBOX_COLUMN().equals("Y"))
					// eidt s.inoue 2012/11/05
					// selectedData.remove(i);
					grid.getVOListTableModel().setValueAt("N", i, 0);
			}
			chkFlg = false;
		}else{
			// eidt s.inoue 2012/11/05
			for (int i = grid.getVOListTableModel().getRowCount()-1; i >= 0; i--) {
				grid.getVOListTableModel().setValueAt("N", i, 0);
			}

			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				// eidt s.inoue 2012/11/05
				// chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (!chk.getCHECKBOX_COLUMN().equals("Y"))
					// eidt s.inoue 2012/11/05
					grid.getVOListTableModel().setValueAt("Y", i, 0);
					// selectedData.add(i);
			}
			chkFlg = true;
		}
	}

	// チェック状態を取得
	private boolean getCheckBoxCount(){
		boolean ret = false;
		JKenshinKekkaSearchListFrameData chk = null;
		int jcnt = 0;

		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y")){
				jcnt++;
			}
		}
		// eidt s.inoue 2012/11/28
		if (jcnt != 1 ){
			JErrorMessage.show("M3520", this);
			ret = true;
			// add s.inoue 2012/11/28
//			JApplication.callValidateCancelFlg = false;
		}
		return ret;
	}

	/* ボタン群 */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {

		// 標準buttonSize
		filterButton.setPreferredSize(new Dimension(100,50));
		reloadButton.setPreferredSize(new Dimension(100,50));
//		exportButton.setPreferredSize(new Dimension(100,50));
		// eidt s.inoue 2012/11/20
		countText.setPreferredSize(new Dimension(80,50));
		countText.setEnabled(false);

// del s.inoue 2012/06/21
//		filterButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
//		reloadButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
//		exportButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
//		countText.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));

		// チェックボックス
//		if (checkBoxSelect == null) {
//			checkBoxSelect= new CheckBoxControl();
//			checkBoxSelect.setText("全て");
//			checkBoxSelect.setBorderPainted(false);
//			checkBoxSelect.setVerticalAlignment(SwingConstants.BOTTOM);
//			checkBoxSelect.setHorizontalTextPosition(JLabel.CENTER);
//			checkBoxSelect.setVerticalTextPosition(SwingConstants.BOTTOM);
//		}

		// チェックbutton
		if (buttonCheck == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Check);
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			buttonCheck= new ExtendedOpenGenericButton(
					"Check","","全チェック(ALT+C)",KeyEvent.VK_C,icon,new Dimension(30,30));
			buttonCheck.addActionListener(this);
		}

		// 閉じるbutton
		if (buttonClose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			buttonClose.addActionListener(this);
			buttonClose.addKeyListener(this);
		}
		// 結果削除
		if (buttonDeleteKekka == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_Delete);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDeleteKekka= new ExtendedOpenGenericButton(
					"KekkaDelete","結果削除(D)","結果削除(ALT+D)",KeyEvent.VK_D,icon);
			buttonDeleteKekka.addActionListener(this);
		}
		// 受診券追加
		if (buttonJusinkenAdd == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_AddJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonJusinkenAdd= new ExtendedOpenGenericButton(
					"JusinkenAdd","受診券追加(A)","受診券追加(ALT+A)",KeyEvent.VK_A,icon);
			buttonJusinkenAdd.addActionListener(this);
		}
		// 受診券削除
		if (buttonDeleteKojin == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_DeleteJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDeleteKojin= new ExtendedOpenGenericButton(
					"JusinkenDelete","受診券削除(J)","受診券削除(ALT+J)",KeyEvent.VK_J,icon);
			buttonDeleteKojin.addActionListener(this);
		}
		// 入力票印刷
		if (buttonNyuryokuPrint == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonNyuryokuPrint= new ExtendedOpenGenericButton(
					"NyuryokuhyoPrint","入力票印刷(P)","入力票印刷(ALT+P)",KeyEvent.VK_P,icon);
			buttonNyuryokuPrint.addActionListener(this);
		}
		// 依頼書印刷
		if (buttonIraiPrint == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print2);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonIraiPrint= new ExtendedOpenGenericButton(
					"IraiPrint","依頼書印刷(Q)","依頼書印刷(ALT+Q)",KeyEvent.VK_Q,icon);
			buttonIraiPrint.addActionListener(this);
		}
		// 結果入力呼出
		if (buttonKekkaInputCall == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Detail);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonKekkaInputCall= new ExtendedOpenGenericButton(
					"KekkaEdit","結果入力(E)","結果入力(ALT+E)",KeyEvent.VK_E,icon);
			buttonKekkaInputCall.addActionListener(this);
		}
		// 結果複製
		if (buttonKekkaCopy == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_Copy);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonKekkaCopy= new ExtendedOpenGenericButton(
					"KekkaCopy","結果複製(K)","結果複製(ALT+K)",KeyEvent.VK_K,icon);
			buttonKekkaCopy.addActionListener(this);
		}
		// 受診券呼出
		if (buttonJusinkenCall == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_InputJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonJusinkenCall= new ExtendedOpenGenericButton(
					"JusinkenCall","受診券呼出(W)","受診券呼出(ALT+W)",KeyEvent.VK_W,icon);
			// del s.inoue 2012/12/03
			// buttonJusinkenCall.addActionListener(this);
		}
	}

//	private int[] selectedRows = null;	// edit n.ohkubo 2014/10/01　未使用なので削除
	// edit s.inoue 2011/07/19
//	private TKojinDao tKojinDao;	// edit n.ohkubo 2014/10/01　未使用なので削除
//	private ArrayList<Hashtable<String, String>> result;	// edit n.ohkubo 2014/10/01　未使用なので削除
//	private Hashtable<String, String> resultItem;  //  @jve:decl-index=0:	// edit n.ohkubo 2014/10/01　未使用なので削除
	private JKenshinKekkaSearchListFrameData validatedData = new JKenshinKekkaSearchListFrameData();  //  @jve:decl-index=0:

	/**
	 * 結果データ入力ボタン
	 */
	public void showKekkaRegisterFrame(boolean isCopy) {


		// 選択行取得
		// JKenshinKekkaSearchListFrameData srcData = null;

		// selectedRows = this.grid.getSelectedRows();

		// eidt s.inoue 2012/04/27
		// selectedRows = this.grid.getSelectedRows();
		// 選択状態を保持する
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		// eidt s.inoue 2011/04/05
		// selectedRows = table.getselectedRows(modelfixed,table);
		// srcData = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows[0]);
		JKenshinKekkaSearchListFrameData srcData = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			srcData = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (srcData.getCHECKBOX_COLUMN().equals("Y")){
				selectedRows.add(i);break;
			}
		}

		if (isCopy){
			srcData.setUKETUKEPRE_ID(srcData.getUKETUKE_ID());
		}else{
			srcData.setUKETUKE_ID(srcData.getUKETUKE_ID());
		}
// del s.inoue 2011/12/07
//		String kensaJissiDate = srcData.getKENSA_NENGAPI();
//		if (kensaJissiDate == null) {
//			kensaJissiDate = new String("");
//		}

		WindowRefreshEvent win = new WindowRefreshEvent();
		// win.setselectedData(selectedRows.get(0));

		// eidt s.inoue 2012/03/29 copyの時へ変更
		if (isCopy) {
			/* 健診実施日が空の場合 */
			if (srcData.getKENSA_NENGAPI().equals("")) {

				/* システム日時を入力する。 */
				Calendar cal = Calendar.getInstance();

				String year = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
				String month = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
				String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

				String jissiDateString = year + month + date;
				srcData.setKENSA_NENGAPI(jissiDateString);
//			/* 健診実施日が指定されている場合 */
//			} else {
//				validatedData.setKensaJissiDate(kensaJissiDate,false);
//				jTextField_Date.setText(validatedData.getKensaJissiDate());
			}
		}
//		/* 結果データ複製の場合 */
//		else {
//			validatedData.setKensaJissiDate(kensaJissiDate,false);
//		}

		// eidt s.inoue 2011/12/06
		JScene.CreateDialog(
				this,
				new JRegisterFlameCtrl(srcData, isCopy),
				win);
	}

	/**
	 * 受診券情報ボタン
	 */
	public void pushedKojinButton(ActionEvent e) {

		// eidt s.inoue 2012/04/27
		// selectedRows = this.grid.getSelectedRows();
		// 選択状態を保持する
		// eidt s.inoue 2012/12/27
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		// eidt s.inoue 2011/04/05
		// selectedRows = table.getselectedRows(modelfixed,table);
		JKenshinKekkaSearchListFrameData testVO = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			testVO = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			// eidt s.inoue 2012/12/27
			if (testVO.getCHECKBOX_COLUMN().equals("Y")){
				break;
				// selectedRows.add(i);break;
			}
		}

		// 一つ選択されている場合のみ画面遷移を行う
//		if (selectedRows.length == 1) {
// del s.inoue 2012/04/27
//			JKenshinKekkaSearchListFrameData testVO = null;
//			testVO = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows[0]);
			// resultItem = result.get(selectedRows.get(0));

			// edit s.inoue 20110328
			// this.validatedData.setUketuke_id(resultItem.get("UKETUKE_ID"));
			// this.validatedData.setHihokenjyaCode(resultItem.get("HIHOKENJYASYO_KIGOU"));
			// this.validatedData.setHihokenjyaNumber(resultItem.get("HIHOKENJYASYO_NO"));
			// this.validatedData.setName(resultItem.get("KANANAME"));
			// this.validatedData.setHokenjyaNumber(resultItem.get("HKNJANUM"));
			this.validatedData.setUKETUKE_ID(testVO.getUKETUKE_ID());
			this.validatedData.setHIHOKENJYASYO_KIGOU(testVO.getHIHOKENJYASYO_KIGOU());
			this.validatedData.setHIHOKENJYASYO_NO(testVO.getHIHOKENJYASYO_NO());
			this.validatedData.setNAME(testVO.getNAME());
			this.validatedData.setHKNJANUM(testVO.getHKNJANUM());

			// edit s.inoue 20110328 受診券
			// String kensaJissiDate = resultItem.get("KENSA_NENGAPI");
			String kensaJissiDate = testVO.getKENSA_NENGAPI();

			if (kensaJissiDate == null) {
				kensaJissiDate = new String("");
			}

			// add s.inoue 2011/08/03　
			this.validatedData.setKENSA_NENGAPI(kensaJissiDate);

			WindowRefreshEvent win = new WindowRefreshEvent();
			// eidt s.inoue 2012/12/27
//			win.setselectedData(selectedRows.get(0));

			JScene.CreateDialog(this, new JKojinRegisterFrameCtrl(
					this.validatedData), win);
//		} else {
//			JErrorMessage.show("M3520", this);
//		}
	}


	public void pushedPrintButton(ActionEvent e) {

		// カレンダは中止で普通のダイアログを表示
		// calendarDialog();
		try {
			// eidt s.inoue 2011/03/29
			// int rowCount = table.getRowCount();
			int rowCount = grid.getVOListTableModel().getRowCount();

			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
			ArrayList<Hashtable<String, String>> selectResult = new ArrayList<Hashtable<String,String>>();

			// チェックボックス判定
//			for (int i = 0; i < rowCount; i++) {
//				// 現在チェックされている行のリストを抽出
//				if ((Boolean) table.getData(i, 0) == Boolean.TRUE) {
//					selectedRows.add(i);
//					selectResult.add(result.get(i));
//				}
//			}
//			JKenshinKekkaSearchListFrameData chk = null;
//			int jcnt = 0;
//			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//				chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//				if (chk.getCHECKBOX_COLUMN().equals("Y"))
//					selectResult.add(chk);
//			}

			dateSelectDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
			// eidt s.inoue 2011/12/16
//			dateSelectDialog.setShowCancelButton(true);

			// 健診実施日入力ダイアログを表示
			dateSelectDialog.setMessageTitle("健診実施日入力画面");
			dateSelectDialog.setVisible(true);

			// eidt s.inoue 2011/12/16
			if (dateSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;
			String kenshinDate = dateSelectDialog.getKenshinDate();
			// 印刷処理
			printInputData(kenshinDate);
			// del s.inoue 2009/10/16
			// リフレッシュ 更新が必要ないので、必要なし
			// pushedSearchButton(true);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 印刷機能
	 * ページ単位 検査依頼書
	 */
	public void pushedPrintIraiButton(ActionEvent e) {

		// 選択行を抽出する。
		// eidt s.inoue 2011/03/29
		// int rowCount = table.getRowCount();
//		int rowCount = grid.getVOListTableModel().getRowCount();	// edit n.ohkubo 2014/10/01　未使用なので削除

		// 印刷用
		TreeMap<String, Object> printDataIrai = new TreeMap<String, Object>();
		// ステータス画面
		ProgressWindow progressWindow = new ProgressWindow(this, false,true);

		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		// edit s.inoue 2009/11/02
		ArrayList<String> kyeList =  new ArrayList<String>();

		// 選択チェック
		// eidt s.inoue 2011/03/29
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				// ｋ番号「空」,結果入力「未」の場合エラー
//				if (table.getData(i, 7).equals("未")){
//					JErrorMessage.show("M3547", this);return;
//				}
//					selectedRows.add(i);
//			}
//		}
		JKenshinKekkaSearchListFrameData vo = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (vo.getCHECKBOX_COLUMN().equals("Y")){
				if (vo.getKEKA_INPUT_FLG().equals("未")){
					JErrorMessage.show("M3547", this);
					return;
				}
				selectedRows.add(i);
			}
		}

		// 選択行なし
		int selectedCount = selectedRows.size();
		if (selectedCount == 0) {
			JErrorMessage.show("M3548", this);
			return;
		}

		// 依頼書データ作成
		for (int j = 0; j < selectedCount; j++) {

			int k = selectedRows.get(j);
			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(k);

			progressWindow.setGuidanceByKey("tokutei.iraisho.frame.progress.guidance");
			progressWindow.setVisible(true);

			// eidt s.inoue 2011/03/29
			// validatedData.setUketuke_id(result.get(k).get("UKETUKE_ID"));
			validatedData.setUKETUKE_ID(vo.getUKETUKE_ID());

			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

			// eidt s.inoue 2011/03/29
			// 受付ID
			// String uketukeId = validatedData.getUketuke_id();
			String uketukeId = validatedData.getUKETUKE_ID();

			System.out.println(j + "行目" + uketukeId);

			tmpKojin.put("UKETUKE_ID", uketukeId);
			// 健診年月日
			// eidt s.inoue 2011/03/29
			// String kensaNengapi = result.get(k).get("KENSA_NENGAPI");
			String kensaNengapi = vo.getKENSA_NENGAPI();
			tmpKojin.put("KENSA_NENGAPI",kensaNengapi );

			Iraisho IraiData = new Iraisho();

			if (!IraiData.setPrintKojinIraiDataSQL(tmpKojin)) {
				// データ移送失敗
				// edit s.inoue 2009/10/16
				progressWindow.setVisible(false);
				JErrorMessage.show("M3530", this);
				return;
			}

			// 印刷obj key:検査年月日+受付ID value:印刷データ
			printDataIrai.put(String.valueOf(kensaNengapi + uketukeId), IraiData);
			kyeList.add(String.valueOf(kensaNengapi + uketukeId));
			System.out.println(String.valueOf(kensaNengapi + uketukeId));
		}

		/*
		 * Kikan作成
		 */
		Kikan kikanData = new Kikan();
		if (!kikanData.setPrintKikanDataSQL()) {
			// データ移送失敗
			JErrorMessage.show("M4941", this);
		}
		Hashtable<String, Object> printData = new Hashtable<String, Object>();
		printData.put("Kikan", kikanData);

		// 印刷実行
		PrintIraisho kensa = new PrintIraisho();
		kensa.setQueryIraiResult(printDataIrai);
		kensa.setQueryResult(printData);
		kensa.setKeys(kyeList);

		progressWindow.setVisible(false);

		try {
			kensa.beginPrint();
		} catch (PrinterException err) {
			err.printStackTrace();
			JErrorMessage.show("M3531", this);
		} finally {
			progressWindow.setVisible(false);
		}

	}

	/**
	 * 印刷機能
	 *
	 * 1ページ 健診項目入力シート（検査結果） 必須データ：保険者番号、被保険者証等記号、被保険者証等番号、検査年月日 import
	 * Print.KenshinKoumoku_Kensa class KenshinKoumoku_Kensa
	 *
	 * 2ページ 健診項目入力シート（問診） 必須データ：保険者番号、被保険者証等記号、被保険者証等番号、健診年月日 import
	 * Print.KenshinKoumoku_Monshin class KenshinKoumoku_Monshin
	 */
	private void printInputData(String kenshinDate){
		/*
		 * 選択行を抽出する。
		 */
//		int rowCount = table.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) table.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}
		// チェックボックス判定
		JKenshinKekkaSearchListFrameData selectDt = null;
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		int jcnt = 0;	// edit n.ohkubo 2014/10/01　未使用なので削除
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (selectDt.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}

		int selectedCount = selectedRows.size();
		// int selectedCount = grid.getVOListTableModel().getRowCount();

		if (selectedCount == 0) {

			ProgressWindow progressWindow = new ProgressWindow(this, false, true);
			progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
			progressWindow.setVisible(true);

			Kojin KojinData = new Kojin();
			Hashtable<String, Object> printData = new Hashtable<String, Object>();
			printData.put("Kojin", KojinData);

			PrintNyuryoku kensa = new PrintNyuryoku();

			kensa.setQueryResult(printData);
			// edit s.inoue 2009/10/16
			kensa.setKenshinDate(kenshinDate);

			progressWindow.setVisible(false);

			try {
				kensa.beginPrint();

			} catch (PrinterException err) {
				err.printStackTrace();
				JErrorMessage
						.show("M3531", this);
			} finally {
				progressWindow.setVisible(false);
			}

			return;
		}

		for (int j = 0; j < selectedCount; j++) {
			int k = selectedRows.get(j);

			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(k);

			/*
			 * 個人情報データ作成
			 */
			ProgressWindow progressWindow = new ProgressWindow(this, false,true);
			progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
			progressWindow.setVisible(true);

			try {
			// eidt s.inoue 2011/03/29
			// validatedData.setUketuke_id(result.get(k).get("UKETUKE_ID"));
			// validatedData.setHihokenjyaCode(result.get(k).get("HIHOKENJYASYO_KIGOU"));
			// validatedData.setHihokenjyaNumber(result.get(k).get("HIHOKENJYASYO_NO"));
			validatedData.setUKETUKE_ID(selectDt.getUKETUKE_ID());
			validatedData.setHIHOKENJYASYO_KIGOU(selectDt.getHIHOKENJYASYO_KIGOU());
			validatedData.setHIHOKENJYASYO_NO(selectDt.getHIHOKENJYASYO_NO());

			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();
			// tmpKojin.put("UKETUKE_ID", validatedData.getUketuke_id());
			tmpKojin.put("UKETUKE_ID",selectDt.getUKETUKE_ID());
			// 被保険者証等記号
			// tmpKojin.put("HIHOKENJYASYO_KIGOU", validatedData.getHihokenjyaCode());
			tmpKojin.put("HIHOKENJYASYO_KIGOU", selectDt.getHIHOKENJYASYO_KIGOU());
			// 被保険者証等番号
			// tmpKojin.put("HIHOKENJYASYO_NO", validatedData.getHihokenjyaNumber());
			tmpKojin.put("HIHOKENJYASYO_NO", selectDt.getHIHOKENJYASYO_NO());

			// 健診年月日
			// edit ver2 s.inoue 2009/05/29
			//tmpKojin.put("KENSA_NENGAPI", (String) model.getData(k, 5));
			// edit ver2 s.inoue 2009/07/14
			// tmpKojin.put("KENSA_NENGAPI", kensaNengapi);
			if (!kenshinDate.equals("")) {
				tmpKojin.put("KENSA_NENGAPI", kenshinDate);
			}else{
				// tmpKojin.put("KENSA_NENGAPI", result.get(k).get("KENSA_NENGAPI"));
				tmpKojin.put("KENSA_NENGAPI", selectDt.getKENSA_NENGAPI());
			}

			Kojin KojinData = new Kojin();
			if (!KojinData.setPrintKojinDataSQL(tmpKojin)) {
				// データ移送失敗
				JErrorMessage.show("M3530", this);
			}

			// add s.inoue 2009/10/15
			// 結果登録済みで無い場合終了　未登録でも出力
//			if (KojinData.getPrintKojinData().size() < 1){
//				progressWindow.setVisible(false);
//				JErrorMessage.show("M3549", this);
//				return;
//			}

			// add s.inoue 2009/10/15　同上
//			String kekaInputFlg = KojinData.getPrintKojinData().get("KEKA_INPUT_FLG");
//			if (!kekaInputFlg.equals("2")){
//				return;
//			}

			/*
			 * 印刷データ生成 個人データを格納する
			 */
			Hashtable<String, Object> printData = new Hashtable<String, Object>();
			printData.put("Kojin", KojinData);

			/*
			 * 印刷 1ページ目を印刷すると、自動的に最終ページまで出力される
			 */
			PrintNyuryoku kensa = new PrintNyuryoku();
			kensa.setQueryResult(printData);
			// edit s.inoue 2009/10/16
			// kensa.setKenshinDate(kenshinDate);
			progressWindow.setVisible(false);


				kensa.beginPrint();
			} catch (PrinterException err) {
				err.printStackTrace();
				JErrorMessage.show("M3531", this);
			} finally {
				progressWindow.setVisible(false);
			}
		}
	}

	/**
	 * 受診券情報追加ボタン
	 */
	public void pushedKojinAddButton(ActionEvent e) {
		JScene.CreateDialog(this, new JKojinRegisterFrameCtrl(),
				new WindowRefreshEvent());
	}

	/**
	 * 健診データ削除ボタン
	 */
	public void pushedDeleteKekkaButton() {
		this.deleteKekka();
	}

	/**
	 * 受診券データ削除ボタン
	 */
	public void pushedDeleteKojinButton() {
		this.deleteKojin();
	}

	/**
	 * 健診結果を削除する。
	 */
	private void deleteKekka() {
		/*
		 * 選択行を抽出する。
		 */
		// int rowCount = modelfixed.getRowCount();
//		int rowCount = grid.getVOListTableModel().getRowCount();	// edit n.ohkubo 2014/10/01　未使用なので削除

		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JKenshinKekkaSearchListFrameData chk = null;
//		int jcnt = 0;	// edit n.ohkubo 2014/10/01　未使用なので削除
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}
//		for (int i = 0; i < rowCount; i++) {
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}


		/* 結果データを削除したか */
		boolean deletedData = false;

		/* 選択された行数 */
		int selectedCount = selectedRows.size();

		JKenshinKekkaSearchListFrameData selectDt = null;

		for (int j = 0; j < selectedCount; j++) {
			/* 選択された行の行インデックス */
			int selectedRowIndex = selectedRows.get(j);


			// eidt s.inoue 2011/03/29
			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRowIndex);
			// Hashtable<String, String> resultItem = result.get(selectedRowIndex);

			String uketukeId = selectDt.getUKETUKE_ID();
			String hihokenjyasyoKigou = selectDt.getHIHOKENJYASYO_KIGOU();
			String hihokenjasyoNo = selectDt.getHIHOKENJYASYO_NO();
			String namekana = selectDt.getKANANAME();
			String kesaNagapi = selectDt.getKENSA_NENGAPI();
			// String uketukeId = resultItem.get("UKETUKE_ID");
			// String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
			// String hihokenjasyoNo = resultItem.get("HIHOKENJYASYO_NO");
			// String namekana = resultItem.get("KANANAME");
			// String kesaNagapi = resultItem.get("KENSA_NENGAPI");
			// String hokenjyaNumber = resultItem.get("HKNJANUM");
			// String sex = resultItem.get("SEX");
			// String birthday = resultItem.get("BIRTHDAY");

			String[] params = { uketukeId, kesaNagapi };
			// String[] messageParams = { namekana, kesaNagapi };

			String displayKigou = null;
			if (hihokenjyasyoKigou == null || hihokenjyasyoKigou.isEmpty()) {
				displayKigou = "(無し)";
			}
			else {
				displayKigou = hihokenjyasyoKigou;
			}

			String displayNumber = null;
			if (hihokenjasyoNo == null || hihokenjasyoNo.isEmpty()) {
				displayNumber = "(無し)";
			}
			else {
				displayNumber = hihokenjasyoNo;
			}

			String[] messageParams = { namekana, displayKigou, displayNumber, kesaNagapi };


			/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）には健診データがありません */
			if (kesaNagapi == null || kesaNagapi.isEmpty()) {
				JErrorMessage.show("M3537", this, messageParams);
				continue;
			}

			/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）（%s）の健診データを削除してもよろしいですか？ */
			RETURN_VALUE retValue = JErrorMessage.show("M3542", this, messageParams);
			if (retValue != RETURN_VALUE.YES) {
				continue;
			}

			int count = 0;
			boolean successDeleteKekka = false;
			try {
				JApplication.kikanDatabase.Transaction();

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_SONOTA, params);

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_TOKUTEI, params);

				if (count == 1) {
					successDeleteKekka = true;

					if (! deletedData) {
						deletedData = true;
					}

					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）（%s）の健診データを削除しました。 */
					JErrorMessage.show("M3535", this, messageParams);
				}

				JApplication.kikanDatabase.Commit();

			} catch (SQLException e) {
				e.printStackTrace();
				try {
					JApplication.kikanDatabase.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			finally {
				if (! successDeleteKekka) {
					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）（%s）の健診データの削除に失敗しました。 */
					JErrorMessage.show("M3536", this, messageParams);
					break;
				}
			}
		}

		/* 1 件以上の結果または受診券情報を削除していた場合 */
		if (deletedData) {
			/* テーブルの再読み込みを行なう */
//			model.clearAllRow();
			// del s.inoue 2011/03/29
//			pushedSearchButton(false);
		}
	}

	/**
	 * 受診券情報を削除する。
	 */
	private void deleteKojin() {
		/*
		 * 選択行を抽出する。
		 */

		// eidt s.inoue 2011/03/29

		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JKenshinKekkaSearchListFrameData chk = null;
//		int jcnt = 0;	// edit n.ohkubo 2014/10/01　未使用なので削除
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y"))
				selectedRows.add(i);
		}
//		int rowCount = modelfixed.getRowCount();
//		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++) {
//
//			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
//				selectedRows.add(i);
//			}
//		}

		/* 選択された行数 */
		int selectedCount = selectedRows.size();
		JKenshinKekkaSearchListFrameData selectDt = null;

		/* 処理済みの受診券情報の受付ID */
		ArrayList<String> chekedKojinIds = new ArrayList<String>();

		/* データを削除したか */
		boolean deletedData = false;

		for (int j = 0; j < selectedCount; j++) {
			/* 選択された行の行インデックス */
			int selectedRowIndex = selectedRows.get(j);

			// eidt s.inoue 2011/03/29
			selectDt = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRowIndex);
			// Hashtable<String, String> resultItem = result.get(selectedRowIndex);

			String uketukeId = selectDt.getUKETUKE_ID();
			String hihokenjyasyoKigou = selectDt.getHIHOKENJYASYO_KIGOU();
			String hihokenjasyoNo = selectDt.getHIHOKENJYASYO_NO();
			String namekana = selectDt.getKANANAME();
			String kesaNagapi = selectDt.getKENSA_NENGAPI();
			// String uketukeId = resultItem.get("UKETUKE_ID");
			// String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
			// String hihokenjasyoNo = resultItem.get("HIHOKENJYASYO_NO");
			// String namekana = resultItem.get("KANANAME");
			// String kesaNagapi = resultItem.get("KENSA_NENGAPI");
			// String hokenjyaNumber = resultItem.get("HKNJANUM");
			// String sex = resultItem.get("SEX");
			// String birthday = resultItem.get("BIRTHDAY");

			String[] paramsDeleteKojin = { uketukeId };
			String[] paramsDeleteKekka = { uketukeId, kesaNagapi };

			String displayKigou = null;
			if (hihokenjyasyoKigou == null || hihokenjyasyoKigou.isEmpty()) {
				displayKigou = "(無し)";
			}
			else {
				displayKigou = hihokenjyasyoKigou;
			}

			String displayNumber = null;
			if (hihokenjasyoNo == null || hihokenjasyoNo.isEmpty()) {
				displayNumber = "(無し)";
			}
			else {
				displayNumber = hihokenjasyoNo;
			}

			String[] messageParams = { namekana, displayKigou, displayNumber, "" };

			/* 処理済みの受付IDの場合は、何もしない */
			if (chekedKojinIds.contains(uketukeId)) {
				continue;
			}

			/* 受診者に健診データがあるか調べる。 */
			ArrayList<Hashtable<String, String>> selectTokuteiResult =
				new ArrayList<Hashtable<String, String>>();
			try {
				selectTokuteiResult = JApplication.kikanDatabase.sendExecuteQuery(
						SQL_SELECT_TOKUTEI, paramsDeleteKojin);

			} catch (SQLException e) {
				e.printStackTrace();

				/* 氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）の受診データの有無の取得に失敗しました。 */
				JErrorMessage.show("M3538", this, messageParams);

				return;
			}

			Hashtable<String, String> item = selectTokuteiResult.get(0);
			String kekkaCount = item.get("NUM");

			boolean needDeleteKekkaData = false;
			try {
				if (kekkaCount.equals("0")) {
					/* 結果データが存在しない場合 */

					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）受診券データを削除してよろしいですか？ */
					RETURN_VALUE retValue = JErrorMessage.show("M3534", this, messageParams);
					if (retValue != RETURN_VALUE.YES) {
						continue;
					}
				}
				else {
					/* 結果データが存在する場合 */

					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）には、
					 * 健診結果データが[%s]件あります。受診券データを削除してよろしいですか？ */
					messageParams[3] = kekkaCount;

					RETURN_VALUE retValue = JErrorMessage.show("M3539", this, messageParams);
					if (retValue == RETURN_VALUE.YES) {
						needDeleteKekkaData = true;
					}
					else {
						continue;
					}
				}
			} finally {
				/* 処理済みとして登録する。 */
				chekedKojinIds.add(uketukeId);
			}

			/*
			 * 結果データを削除する。
			 */
			if (needDeleteKekkaData) {
				int count = 0;
				boolean successDeleteKekka = false;
				try {
					JApplication.kikanDatabase.Transaction();

					count = JApplication.kikanDatabase.sendNoResultQuery(
							SQL_DELETE_SONOTA, paramsDeleteKekka);

					count = JApplication.kikanDatabase.sendNoResultQuery(
							SQL_DELETE_TOKUTEI, paramsDeleteKekka);

					if (count == 1) {
						successDeleteKekka = true;

						if (! deletedData) {
							deletedData = true;
						}
					}

					JApplication.kikanDatabase.Commit();

				} catch (SQLException e) {
					logger.error(e.getMessage());
					try {
						JApplication.kikanDatabase.rollback();
					} catch (SQLException ex) {
						logger.error(ex.getMessage());
					}
				}
				finally {
					if (! successDeleteKekka) {
						/* 確認,氏名（カナ）[%s]さん（%s）の健診データの削除に失敗しました。 */
						JErrorMessage.show("M3536", this, messageParams);
						return;
					}
				}
			}

			/*
			 * 受診券データを削除する。
			 */
			boolean successDeleteData = false;
			int count = 0;

			try {

				// add ver2 s.inoue 2009/06/02
				JApplication.kikanDatabase.Transaction();

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_NAYOSE, paramsDeleteKojin);

				count = JApplication.kikanDatabase.sendNoResultQuery(
						SQL_DELETE_KOJIN, paramsDeleteKojin);

				if (count == 1) {
					successDeleteData = true;
					if (! deletedData) {
						deletedData = true;
					}

					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）の受診券データを削除しました。 */
					JErrorMessage.show("M3541", this, messageParams);
				}

				// add ver2 s.inoue 2009/06/02
				JApplication.kikanDatabase.Commit();

			} catch (SQLException e) {
				logger.error(e.getMessage());
				try {
					JApplication.kikanDatabase.rollback();
				} catch (SQLException ex) {
					logger.error(ex.getMessage());
				}
			}
			finally {
				if (! successDeleteData) {
					/* 確認,氏名（カナ）[%s]さん（被保険者証等記号[%s]番号[%s]）の受診券データの削除に失敗しました。 */
					JErrorMessage.show("M3540", this, messageParams);
					return;
				}
			}
		}

		/* 1 件以上の結果または受診券情報を削除していた場合 */
		if (deletedData) {
			/* テーブルの再読み込みを行なう */
//			model.clearAllRow();
			// del s.inoue 2011/03/29
//			pushedSearchButton(false);
		}
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */

	public class WindowRefreshEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/05/09
			// selectedData = getselectedData();
			// selectedData.add(0, getselectedData());

			// ArrayList<Integer> selectedHistoryRows = new ArrayList<Integer>();
			// eidt s.inoue 2012/11/28
			JApplication.selectedHistoryRows = new ArrayList<Integer>();
			JKenshinKekkaSearchListFrameData chk = null;
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (chk.getCHECKBOX_COLUMN().equals("Y"))
					JApplication.selectedHistoryRows.add(i);
			}

			// eidt s.inoue 2011/05/10
			// ViewSettings.getGridPageSize()を訂正
		   	int rowCount = ((currentPage -1)*JApplication.gridViewSearchCount) + currentRow + 1;
			currentTotalRows = rowCount;

//			String cntTxt = countText.getText();
//			countText.setText(rowCount + "件目");
//			reloadButton.doClick();
			countText.setText(currentTotalRows + "件目");

			// eidt s.inoue 2012/11/21
			reloadButton.doClick();
			// grid.reloadData();

// del s.inoue 2012/11/05
//			Thread reload = new ActionAutoReloadThread();
//			reload.start();

// del s.inoue 2012/11/07
//			setSelectedRow(selectedRows);
		}

		// eidt s.inoue 2012/12/27
//		private int sel = 0;
//		public void setselectedData(Integer selIdx){
//			sel = selIdx;
//		}
//		public int getselectedData(){
//			return sel;
//		}
	}
	
	// add s.inoue 2013/10/29
	private void preservColumnStatus(){
		// JApplication.flag = EnumSet.noneOf(FlagEnum.class);
		if (textColumn_jyushinSeiriNo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.JYUSHIN_SEIRI_NO));
		}
		
		//add tanaka 2013/11/06
		if (textColumn_HokensyoCode.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_KIGOU))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_KIGOU));
		}
		
		if (textColumn_HokensyoNumber.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_NO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_NO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HIHOKENJYASYO_NO));
		}
		
		if (dateColumn_KenshinDateTo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.KENSA_NENGAPI));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.KENSA_NENGAPI))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KENSA_NENGAPI));
		}
		
		if (textColumn_sex.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.SEX));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.SEX))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SEX));
		}
		
		if (textColumn_birthdayTo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.BIRTHDAY));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.BIRTHDAY))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.BIRTHDAY));
		}
		
		if (textColumn_inputFlg.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.KEKA_INPUT_FLG));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.KEKA_INPUT_FLG))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KEKA_INPUT_FLG));
		}
		
		if (textColumn_hokenjaNo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HKNJANUM));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HKNJANUM))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HKNJANUM));
		}
		
		if (textColumn_shiharaiDaikouNo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO));
		}
		
		if (textColumn_Name.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.NAME));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.NAME))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NAME));
		}

		if (textColumn_kanaName.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.KANANAME));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.KANANAME))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.KANANAME));
		}

		if (textColumn_hanteiNengapi.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.HANTEI_NENGAPI));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.HANTEI_NENGAPI))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.HANTEI_NENGAPI));
		}
		
		if (textColumn_tutiNengapi.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.TUTI_NENGAPI));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.TUTI_NENGAPI))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.TUTI_NENGAPI));
		}
		
		if (textColumn_nendo.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.NENDO));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.NENDO))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.NENDO));
		}
		
		if (checkColumn_checkBox.isVisible()){
			JApplication.flag.removeAll(EnumSet.of(FlagEnum_Serche.CHECKBOX_COLUMN));
		}else{
			if (!JApplication.flag.contains(FlagEnum_Serche.CHECKBOX_COLUMN))
				JApplication.flag.addAll(EnumSet.of(FlagEnum_Serche.CHECKBOX_COLUMN));
		}
		
		// edit n.ohkubo 2014/10/01　追加 start
		//「表示 or 非表示」をDBへ登録する
		((JKenshinKekkaSearchListFrameCtl)grid.getController()).preserveColumnStatus();
		// edit n.ohkubo 2014/10/01　追加 end
	}

	/* ボタンアクション */
	public void closeButtton_actionPerformed(ActionEvent e) {
		
		grid = null;	// edit n.ohkubo 2015/08/01　追加　メニューから「ログインへ戻る」でエラーが表示される対応
		
		dispose();

		// add s.inoue 2013/04/05
		if (JApplication.selectedHistoryRows.size() == 0)return;
		// eidt s.inoue 2013/11/07
//		for (int i=0;i < grid.getVOListTableModel().getRowCount(); i++) {
//			JApplication.selectedHistoryRows.remove(i);
//		}
		JApplication.selectedHistoryRows.removeAll(JApplication.selectedHistoryRows);
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

// del s.inoue 2012/11/05
//	// チェックボックス保持
//	private ArrayList<Integer> getSelectedRow(){
//		JKenshinKekkaSearchListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y"))
//				selectedData.add(i);
//		}
//		return selectedData;
//	}
//
//	// チェックボックス設定
//	private void setSelectedRow(ArrayList<Integer> selectedRows){
//		// JKenshinKekkaSearchListFrameData vo = null;
//		for (int i = 0; i < selectedRows.size(); i++) {
//			// eidt s.inoue 2012/11/02
//			// vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(i));
//			// eidt s.inoue 2012/11/07
//			grid.getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
//			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
//		}
//	}
//
//	// add s.inoue 2011/04/11
//	private class ActionAutoReloadThread extends Thread {
//	    public ActionAutoReloadThread () {
//	    }
//	    public void run() {
//            try {
//                Thread.sleep(100);
//                setSelectedRow(selectedData);
//            } catch (InterruptedException e) {
//                 // Interruptedされた場合は、特に何もせずに終了する
//            }
//	    }
//	}
}
