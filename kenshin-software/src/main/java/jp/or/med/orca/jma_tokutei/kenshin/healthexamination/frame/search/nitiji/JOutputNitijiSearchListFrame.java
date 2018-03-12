package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji;

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
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Nitiji;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcess;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JKessaiProcessData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.outputhl7.JSyuukeiProcess;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintSeikyuNitiji;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Nikeihyo;

import org.apache.log4j.Logger;
import org.openswing.swing.client.EditButton;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.InsertButton;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.client.TextControl;
import org.openswing.swing.table.client.Grid;
import org.openswing.swing.table.columns.client.CheckBoxColumn;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JOutputNitijiSearchListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = 9182606025994796235L;	// edit n.ohkubo 2014/10/01　追加
	
	protected Connection conn = null;
	protected GridControl grid = new GridControl();
	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();
	/* button */
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

//	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
//	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

	protected InsertButton insertButton = new InsertButton();
	protected EditButton editButton = new EditButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
//	protected ImportButton importButton = new ImportButton();
	// del s.inoue 2012/11/14
//	protected ExportButton exportButton = new ExportButton();
	protected TextControl countText = new TextControl();

	protected ExtendedOpenGenericButton buttonclose = null;
	protected ExtendedOpenGenericButton buttonSeikyu = null;
	protected ExtendedOpenGenericButton buttonSeikyuPrint = null;
	protected ExtendedOpenGenericButton buttonSeikyuEdit = null;
	// buttonコントロール
	protected ExtendedOpenGenericButton buttonCheck = null;

	/* control */
	protected TextColumn textColumn_Name = new TextColumn();
	protected TextColumn textColumn_HokensyoCode = new TextColumn();
	protected TextColumn textColumn_HokensyoNumber = new TextColumn();
	protected TextColumn dateColumn_KenshinDateFrom = new TextColumn();
	// add s.inoue 2012/10/25
	protected ComboColumn textColumn_hokenjaNo = new ComboColumn();
	protected ComboColumn textColumn_shiharaiDaikouNo = new ComboColumn();

	protected TextColumn dateColumn_KenshinDateTo = new TextColumn();
	protected TextColumn textColumn_sex = new TextColumn();
	protected TextColumn textColumn_birthday = new TextColumn();
	protected TextColumn textColumn_jyushinSeiriNo = new TextColumn();
	protected TextColumn textColumn_kanaName = new TextColumn();
//	protected TextColumn textColumn_hanteiNengapi = new TextColumn();
//	protected TextColumn textColumn_tutiNengapi = new TextColumn();
	protected TextColumn textColumn_nendo = new TextColumn();
	protected TextColumn textColumn_tankaGoukei = new TextColumn();
	protected TextColumn textColumn_madoGoukei = new TextColumn();
	protected TextColumn textColumn_seikyuGoukei = new TextColumn();
	protected TextColumn textColumn_updateTimeStamp = new TextColumn();
	protected CheckBoxColumn checkColumn_checkBox = new CheckBoxColumn();
	protected ComboColumn textColumn_nitijiFlg = new ComboColumn();
	/* combo */
	protected ExtendedComboBox jComboBox_HokenjyaNumber = null;
    protected ExtendedComboBox jComboBox_SyubetuCode = new ExtendedComboBox();
    protected ExtendedComboBox jComboBox_SeikyuKikanNumber = null;

	protected int currentRow = 0;
	protected int currentPage = 0;
	protected int currentTotalRows = 0;

    /** 保険者番号、支払代行機関選択欄の、番号と名称の区切り文字列 */
//	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";	// edit n.ohkubo 2014/10/01　未使用なので削除
	private static final String JISSIKUBUN_TOKUTEIKENSHIN = "1";
	private static final String SYUBETSU_CODE_6_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から保険者";
	private static final String SYUBETSU_CODE_1_DISPLAY_NAME = "特定健診機関又は特定保健指導機関から代行機関";

	protected final static String CONST_VALUE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.nitiji.JOutputNitijiSearchListFrameData";
	private static Logger logger = Logger.getLogger(JOutputNitijiSearchListFrame.class);

	private Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
	private JOutputHL7FrameState state = JOutputHL7FrameState.STATUS_INITIALIZED;
	private IDialog messageDialog;
	private JOutputNitijiSearchListFrameData vo = null;
//	private ArrayList<Integer> selectedData = new ArrayList<Integer>();	// edit n.ohkubo 2014/10/01　未使用なので削除
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

	// フレームの状態を管理する
	public enum JOutputHL7FrameState {
		/* 初期状態 */
		STATUS_INITIALIZED,
		/* 検索後 */
		STATUS_AFTER_SEARCH,
		/* 請求後 */
		STATUS_AFTER_SEIKYU,
		/* HL7変換後 */
		STATUS_AFTER_HL7
	}

	/* コンストラクタ */
	public JOutputNitijiSearchListFrame(Connection conn,
			JOutputNitijiSearchListFrameCtl controller) {
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
			JOutputNitijiSearchListFrameCtl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);
			
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
	public void jbInit() throws Exception {
		/* control ※ClientApplicationと一致*/
		textColumn_Name.setColumnFilterable(true);
		textColumn_Name.setColumnName("NAME");
		textColumn_Name.setColumnSortable(true);
		textColumn_Name.setPreferredWidth(90);
		// eidt s.inoue 2012/10/25
		textColumn_Name.setColumnVisible(false);
		textColumn_Name.setColumnFilterable(false);
		textColumn_Name.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_Name.setColumnVisible(true);
		textColumn_Name.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.NAME));

		textColumn_HokensyoCode.setColumnFilterable(true);
		textColumn_HokensyoCode.setColumnName("HIHOKENJYASYO_KIGOU");
		textColumn_HokensyoCode.setColumnSortable(true);
		textColumn_HokensyoCode.setPreferredWidth(110);
		// eidt s.inoue 2012/10/25
		textColumn_HokensyoCode.setColumnVisible(false);
		textColumn_HokensyoCode.setColumnFilterable(false);
		textColumn_HokensyoCode.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_HokensyoCode.setColumnVisible(true);
		textColumn_HokensyoCode.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU));

		textColumn_HokensyoNumber.setColumnFilterable(true);
		textColumn_HokensyoNumber.setColumnName("HIHOKENJYASYO_NO");
		textColumn_HokensyoNumber.setColumnSortable(true);
		textColumn_HokensyoNumber.setPreferredWidth(110);
		// eidt s.inoue 2012/10/25
		textColumn_HokensyoNumber.setColumnVisible(false);
		textColumn_HokensyoNumber.setColumnFilterable(false);
		textColumn_HokensyoNumber.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_HokensyoNumber.setColumnVisible(true);
		textColumn_HokensyoNumber.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HIHOKENJYASYO_NO));

//		dateColumn_KenshinDateFrom.setColumnName("KENSA_NENGAPI");	// edit n.ohkubo 2014/10/01　削除
		dateColumn_KenshinDateFrom.setColumnName("KENSA_NENGAPI2");	// edit n.ohkubo 2014/10/01　追加
		dateColumn_KenshinDateFrom.setPreferredWidth(80);
		// edit s.inoue 2013/11/12
		dateColumn_KenshinDateFrom.setColumnFilterable(true);
		dateColumn_KenshinDateFrom.setColumnSortable(false);
		dateColumn_KenshinDateFrom.setColumnSelectable(false);
		dateColumn_KenshinDateFrom.setColumnVisible(false);
		dateColumn_KenshinDateFrom.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01　追加

		// add s.inoue 2012/10/23
		dateColumn_KenshinDateTo.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnSortable(true);
		dateColumn_KenshinDateTo.setColumnSelectable(true);
		
		dateColumn_KenshinDateTo.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KENSA_NENGAPI));
		dateColumn_KenshinDateTo.setMaxCharacters(8);	// edit n.ohkubo 2014/10/01　追加

		textColumn_sex.setColumnFilterable(true);
		textColumn_sex.setColumnName("SEX");
		textColumn_sex.setColumnSortable(true);
		textColumn_sex.setPreferredWidth(40);
		// eidt s.inoue 2012/10/25
		textColumn_sex.setColumnVisible(false);
		textColumn_sex.setColumnFilterable(false);
		textColumn_sex.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_sex.setColumnVisible(true);
		textColumn_sex.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SEX));

		textColumn_birthday.setColumnFilterable(true);
		textColumn_birthday.setColumnName("BIRTHDAY");
		textColumn_birthday.setColumnSortable(true);
		textColumn_birthday.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_birthday.setColumnVisible(false);
		textColumn_birthday.setColumnFilterable(false);
		textColumn_birthday.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_birthday.setColumnVisible(true);
		textColumn_birthday.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.BIRTHDAY));

		textColumn_jyushinSeiriNo.setColumnFilterable(true);
		textColumn_jyushinSeiriNo.setColumnName("JYUSHIN_SEIRI_NO");
		textColumn_jyushinSeiriNo.setColumnSortable(true);
		textColumn_jyushinSeiriNo.setPreferredWidth(100);
		//add tanaka 2013/11/07
		textColumn_jyushinSeiriNo.setColumnVisible(true);
		textColumn_jyushinSeiriNo.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO));
		textColumn_jyushinSeiriNo.setMaxCharacters(11);	// edit n.ohkubo 2014/10/01　追加

		textColumn_hokenjaNo.setColumnFilterable(true);
		textColumn_hokenjaNo.setColumnName("HKNJANUM");
		textColumn_hokenjaNo.setColumnSortable(true);
		textColumn_hokenjaNo.setPreferredWidth(220);
		textColumn_hokenjaNo.setDomainId("T_HOKENJYA");
		//add tanaka 2013/11/07
		textColumn_hokenjaNo.setColumnVisible(true);
		textColumn_hokenjaNo.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HKNJANUM));

		textColumn_shiharaiDaikouNo.setColumnFilterable(true);
		textColumn_shiharaiDaikouNo.setColumnName("SIHARAI_DAIKOU_BANGO");
		textColumn_shiharaiDaikouNo.setColumnSortable(true);
		textColumn_shiharaiDaikouNo.setPreferredWidth(220);
		textColumn_shiharaiDaikouNo.setDomainId("T_SHIHARAI");
		//add tanaka 2013/11/07
		textColumn_shiharaiDaikouNo.setColumnVisible(true);
		textColumn_shiharaiDaikouNo.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO));

		textColumn_kanaName.setColumnFilterable(true);
		textColumn_kanaName.setColumnName("KANANAME");
		textColumn_kanaName.setColumnSortable(true);
		textColumn_kanaName.setPreferredWidth(175);
		//add tanaka 2013/11/07
		textColumn_kanaName.setColumnVisible(true);
		textColumn_kanaName.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KANANAME));
		textColumn_kanaName.setMaxCharacters(50);	// edit n.ohkubo 2014/10/01　追加

//		textColumn_hanteiNengapi.setColumnFilterable(true);
//		textColumn_hanteiNengapi.setColumnName("HANTEI_NENGAPI");
//		textColumn_hanteiNengapi.setColumnSortable(true);
//		textColumn_hanteiNengapi.setPreferredWidth(80);
//
//		textColumn_tutiNengapi.setColumnFilterable(true);
//		textColumn_tutiNengapi.setColumnName("TUTI_NENGAPI");
//		textColumn_tutiNengapi.setColumnSortable(true);
//		textColumn_tutiNengapi.setPreferredWidth(80);

		textColumn_nendo.setColumnFilterable(true);
		textColumn_nendo.setColumnName("NENDO");
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setPreferredWidth(40);
		//add tanaka 2013/11/07
		textColumn_nendo.setColumnVisible(true);
		textColumn_nendo.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.NENDO));
		textColumn_nendo.setMaxCharacters(4);	// edit n.ohkubo 2014/10/01　追加

		textColumn_tankaGoukei.setColumnFilterable(true);
		textColumn_tankaGoukei.setColumnName("TANKA_GOUKEI");
		textColumn_tankaGoukei.setColumnSortable(true);
		textColumn_tankaGoukei.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_tankaGoukei.setColumnVisible(false);
		textColumn_tankaGoukei.setColumnFilterable(false);
		textColumn_tankaGoukei.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_tankaGoukei.setColumnVisible(true);
		textColumn_tankaGoukei.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.TANKA_GOUKEI));

		textColumn_madoGoukei.setColumnFilterable(true);
		textColumn_madoGoukei.setColumnName("MADO_FUTAN_GOUKEI");
		textColumn_madoGoukei.setColumnSortable(true);
		textColumn_madoGoukei.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_madoGoukei.setColumnVisible(false);
		textColumn_madoGoukei.setColumnFilterable(false);
		textColumn_madoGoukei.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_madoGoukei.setColumnVisible(true);
		textColumn_madoGoukei.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI));

		textColumn_seikyuGoukei.setColumnFilterable(true);
		textColumn_seikyuGoukei.setColumnName("SEIKYU_KINGAKU");
		textColumn_seikyuGoukei.setColumnSortable(true);
		textColumn_seikyuGoukei.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_seikyuGoukei.setColumnVisible(false);
		textColumn_seikyuGoukei.setColumnFilterable(false);
		textColumn_seikyuGoukei.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_seikyuGoukei.setColumnVisible(true);
		textColumn_seikyuGoukei.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SEIKYU_KINGAKU));

		textColumn_updateTimeStamp.setColumnFilterable(false);
		textColumn_updateTimeStamp.setColumnName("UPDATE_TIMESTAMP");
		textColumn_updateTimeStamp.setColumnSortable(true);
		textColumn_updateTimeStamp.setPreferredWidth(140);
		// eidt s.inoue 2012/10/25
		textColumn_updateTimeStamp.setColumnVisible(false);
		textColumn_updateTimeStamp.setColumnFilterable(false);
		textColumn_updateTimeStamp.setColumnSortable(false);
		//add tanaka 2013/11/07
		textColumn_updateTimeStamp.setColumnVisible(true);
		textColumn_updateTimeStamp.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.UPDATE_TIMESTAMP));

		textColumn_nitijiFlg.setColumnFilterable(true);
		textColumn_nitijiFlg.setColumnName("JISI_KBN");
		textColumn_nitijiFlg.setColumnSortable(true);
		textColumn_nitijiFlg.setPreferredWidth(40);
		textColumn_nitijiFlg.setDomainId("JISI_KBN");
		//add tanaka 2013/11/07
		textColumn_nitijiFlg.setColumnVisible(true);
		textColumn_nitijiFlg.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.JISI_KBN));

		// eidt s.inoue 2011/04/20
		checkColumn_checkBox.setColumnFilterable(false);
		checkColumn_checkBox.setColumnName("CHECKBOX_COLUMN");
		checkColumn_checkBox.setColumnSortable(false);
		checkColumn_checkBox.setPreferredWidth(50);
		checkColumn_checkBox.setEditableOnEdit(true);
		checkColumn_checkBox.setEditableOnInsert(true);
		checkColumn_checkBox.setPositiveValue("Y");
		checkColumn_checkBox.setNegativeValue("N");
		checkColumn_checkBox.setColumnDuplicable(true);
		checkColumn_checkBox.setColumnRequired(false);
		checkColumn_checkBox.setEnableInReadOnlyMode(true);
		checkColumn_checkBox.setAllowNullValue(false);
		//add tanaka 2013/11/07
		checkColumn_checkBox.setColumnVisible(true);
		checkColumn_checkBox.setColumnVisible(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.CHECKBOX_COLUMN));

		/* button */
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
//	    buttonOriginePanel.add(importButton, null);
	    buttonOriginePanel.add(buttonCheck,null);
	    buttonOriginePanel.add(navigatorBar, null);
		buttonOriginePanel.add(countText, null);

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonclose, null);
	    buttonPanel.add(buttonSeikyu, null);
	    buttonPanel.add(buttonSeikyuPrint, null);
	    buttonPanel.add(buttonSeikyuEdit, null);

		// action設定
		buttonclose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonSeikyu.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonSeikyuPrint.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonSeikyuEdit.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonCheck.addActionListener(new ListFrame_button_actionAdapter(this));

		// add s.inoue 2012/11/21
		navigatorBar.addAfterActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e) {
	        	if (JApplication.selectedHistoryRows == null)return;
	    		for (int i = 0; i < JApplication.selectedHistoryRows.size(); i++) {
	    			grid.getVOListTableModel().setValueAt("N", JApplication.selectedHistoryRows.get(i), 0);
	    			JApplication.selectedHistoryRows.remove(i);
	    		}
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
		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
		grid.setDeleteButton(deleteButton);
		grid.setEditButton(editButton);
//		grid.setExportButton(exportButton);
//		grid.setImportButton(importButton);

		/* list */
		grid.setInsertButton(insertButton);
		grid.setMaxSortedColumns(5);
		grid.setNavBar(navigatorBar);
		grid.setFilterButton(filterButton);
		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName(CONST_VALUE);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(checkColumn_checkBox, null);
		grid.getColumnContainer().add(textColumn_nendo, null);
		grid.getColumnContainer().add(textColumn_nitijiFlg, null);
		grid.getColumnContainer().add(textColumn_jyushinSeiriNo, null);
		grid.getColumnContainer().add(textColumn_kanaName, null);
		grid.getColumnContainer().add(textColumn_Name, null);
		grid.getColumnContainer().add(textColumn_sex, null);
		grid.getColumnContainer().add(textColumn_birthday, null);
		grid.getColumnContainer().add(dateColumn_KenshinDateFrom, null);
		// eidt s.inoue 2012/10/25
		grid.getColumnContainer().add(dateColumn_KenshinDateTo, null);
		grid.getColumnContainer().add(textColumn_hokenjaNo, null);
		grid.getColumnContainer().add(textColumn_shiharaiDaikouNo, null);
		grid.getColumnContainer().add(textColumn_HokensyoCode, null);
		grid.getColumnContainer().add(textColumn_HokensyoNumber, null);
//		grid.getColumnContainer().add(textColumn_hanteiNengapi, null);
//		grid.getColumnContainer().add(textColumn_tutiNengapi, null);

		grid.getColumnContainer().add(textColumn_tankaGoukei, null);
		grid.getColumnContainer().add(textColumn_madoGoukei, null);
		grid.getColumnContainer().add(textColumn_seikyuGoukei, null);
		grid.getColumnContainer().add(textColumn_updateTimeStamp, null);
		
		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01　追加

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.nitiji.frame.title","tokutei.nitiji.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);
		// openswing s.inoue 2011/01/21
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

		// 種別コードコンボボックスの設定
		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_1_DISPLAY_NAME);
		jComboBox_SyubetuCode.addItem(SYUBETSU_CODE_6_DISPLAY_NAME);

		/* メッセージダイアログを初期化する。ｌ */
		try {
			messageDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
			messageDialog.setShowCancelButton(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
//		reloadButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
//		exportButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
//		filterButton.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
//		countText.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));

		// チェックbutton
		if (buttonCheck == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Check);
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			buttonCheck= new ExtendedOpenGenericButton(
					"Check","","全チェック(ALT+C)",KeyEvent.VK_C,icon,new Dimension(30,30));
			buttonCheck.addActionListener(this);
		}
		// 閉じるbutton
		if (buttonclose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonclose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
		}
		// 請求
		if (buttonSeikyu == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Select);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonSeikyu= new ExtendedOpenGenericButton(
					"nitijiSeikyu","請求処理(D)","請求処理(ALT+D)",KeyEvent.VK_D,icon);
		}
		// 請求印刷
		if (buttonSeikyuPrint == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonSeikyuPrint= new ExtendedOpenGenericButton(
					"nitijiSeikyuPrint","日次印刷(P)","日次印刷(ALT+P)",KeyEvent.VK_P,icon);
		}
		// 請求編集
		if (buttonSeikyuEdit == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Detail);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonSeikyuEdit= new ExtendedOpenGenericButton(
					"nitijiEdit","編集(E)","編集(ALT+E)",KeyEvent.VK_E,icon);
		}
	}

	/* ボタンアクション用内部クラス */
	class ListFrame_button_actionAdapter implements ActionListener,KeyListener {
	  JOutputNitijiSearchListFrame adaptee;

		  ListFrame_button_actionAdapter(JOutputNitijiSearchListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
		public void actionPerformed(ActionEvent e) {

		   	Object btn = e.getSource();

		   	// eidt s.inoue 2011/05/09
//		   	selectedData = new ArrayList<Integer>();	// edit n.ohkubo 2014/10/01　未使用なので削除

		   	// eidt s.inoue 2011/04/21
		   	if (btn == buttonCheck){
				logger.info(buttonCheck.getText());
				setCheckBoxValue();
		   	}else if (btn == buttonclose){
				logger.info(buttonclose.getText());
				//add tanaka 2013/11/07
				preservColumnStatus();
				adaptee.closeButtton_actionPerformed(e);
			/* 請求 */
			}else if (btn == buttonSeikyu){
//				logger.info(buttonSeikyu);// edit n.ohkubo 2014/10/01　削除
				logger.info(buttonSeikyu.getText());// edit n.ohkubo 2014/10/01　追加
				pushedSeikyuButton();

				// eidt s.inoue 2012/11/21
				// grid.reloadData();
				reloadButton.doClick();

				// eidt s.inoue 2011/05/09
				chkFlg = true;
			/* 印刷 */
			}else if (btn == buttonSeikyuPrint){
				logger.info(buttonSeikyuPrint.getText());
				pushedSeikyuPrintButton();
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			/* 編集 */
			}else if (btn == buttonSeikyuEdit){
				logger.info(buttonSeikyuEdit.getText());
				// add s.inoue 2012/11/30
				// setSelectedRow(JApplication.selectedPreservRows);
				pushedSeikyuEditButton();
			}

// del s.inoue 2012/11/05
//		   	if (chkFlg)
//			selectedData = getSelectedRow();
//
//			// grid.reloadData();
//			reloadButton.doClick();
//
//			// 自動リロードするスレッド
//			Thread reload = new ActionAutoReloadThread();
//			reload.start();

		  }
		private void preservColumnStatus() {
			try{
			if(textColumn_Name.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.NAME));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.NAME))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.NAME));
			}
			
			if(textColumn_HokensyoCode.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_KIGOU));
			}
			
			if(textColumn_HokensyoNumber.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_NO));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HIHOKENJYASYO_NO))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HIHOKENJYASYO_NO));
			}
			
//			if(dateColumn_KenshinDateFrom.isVisible()) {
//				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.KENSA_NENGAPI));
//			} else {
//				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KENSA_NENGAPI))
//					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KENSA_NENGAPI));
//			}
			
			if(dateColumn_KenshinDateTo.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.KENSA_NENGAPI));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KENSA_NENGAPI))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KENSA_NENGAPI));
			}
			
			if(textColumn_sex.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.SEX));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SEX))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SEX));
			}
			
			if(textColumn_birthday.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.BIRTHDAY));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.BIRTHDAY))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.BIRTHDAY));
			}
			
			if(textColumn_jyushinSeiriNo.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.JYUSHIN_SEIRI_NO));
			}
			
			if(textColumn_hokenjaNo.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.HKNJANUM));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.HKNJANUM))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.HKNJANUM));
			}
			
			if(textColumn_shiharaiDaikouNo.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SIHARAI_DAIKOU_BANGO));
			}
			
			if(textColumn_kanaName.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.KANANAME));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.KANANAME))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.KANANAME));
			}
			
			if(textColumn_nendo.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.NENDO));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.NENDO))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.NENDO));
			}
			
			if(textColumn_tankaGoukei.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.TANKA_GOUKEI));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.TANKA_GOUKEI))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.TANKA_GOUKEI));
			}
			
			if(textColumn_madoGoukei.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.MADO_FUTAN_GOUKEI));
			}
			
			if(textColumn_seikyuGoukei.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.SEIKYU_KINGAKU));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.SEIKYU_KINGAKU))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.SEIKYU_KINGAKU));
			}
			
			if(textColumn_updateTimeStamp.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.UPDATE_TIMESTAMP));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.UPDATE_TIMESTAMP))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.UPDATE_TIMESTAMP));
			}
			
			if(textColumn_nitijiFlg.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.JISI_KBN));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.JISI_KBN))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.JISI_KBN));
			}
			
			if(checkColumn_checkBox.isVisible()) {
				JApplication.flag_Nitiji.removeAll(EnumSet.of(FlagEnum_Nitiji.CHECKBOX_COLUMN));
			} else {
				if(!JApplication.flag_Nitiji.contains(FlagEnum_Nitiji.CHECKBOX_COLUMN))
					JApplication.flag_Nitiji.addAll(EnumSet.of(FlagEnum_Nitiji.CHECKBOX_COLUMN));
			}
			
			// edit n.ohkubo 2014/10/01　追加 start
			//「表示 or 非表示」をDBへ登録する
			((JOutputNitijiSearchListFrameCtl)grid.getController()).preserveColumnStatus();
			// edit n.ohkubo 2014/10/01　追加 end
			
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	}

//	// add s.inoue 2012/11/28	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// チェックボックス設定
//	private void setSelectedRow(ArrayList<Integer> selectedRows){
//		if (selectedRows == null)return;
//		int ival = selectedRows.size();
//		for (int i = 0; i < ival; i++) {
//			grid.getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
//			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
//		}
//	}

	// eidt s.inoue 2011/04/21
	private void setCheckBoxValue(){
//		JOutputNitijiSearchListFrameData chk = null;	// edit n.ohkubo 2014/10/01　未使用なので削除
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
				// chk = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (chk.getCHECKBOX_COLUMN().equals("Y"))
				//	selectedData.remove(i);
				grid.getVOListTableModel().setValueAt("N", i, 0);
			}
			chkFlg = false;
		}else{
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				grid.getVOListTableModel().setValueAt("N", i, 0);
			}

			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				// chk = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (!chk.getCHECKBOX_COLUMN().equals("Y"))
				//	selectedData.add(i);
				grid.getVOListTableModel().setValueAt("Y", i, 0);
			}
			chkFlg = true;
		}
	}

// del s.inoue 2012/11/09
//	// チェックボックス保持
//	private ArrayList<Integer> getSelectedRow(){
//		JOutputNitijiSearchListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y"))
//				selectedData.add(i);
//		}
//		return selectedData;
//	}
//
//	// チェックボックス設定
//	private void setSelectedRow(ArrayList<Integer> selectedRows){
//		JOutputNitijiSearchListFrameData vo = null;
//		for (int i = 0; i < selectedRows.size(); i++) {
//			vo = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(i));
//			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
//		}
//	}
	/**
	 * 請求処理ボタンが押された場合
	 */
	public void pushedSeikyuButton()
	{
		// eidt s.inoue 2012/11/08
		// 選択状態を保持する
		// ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
		// del s.inoue 2013/11/18
//		JApplication.selectedHistoryRows = new ArrayList<Integer>();
//		JOutputNitijiSearchListFrameData chk = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			chk = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (chk.getCHECKBOX_COLUMN().equals("Y"))
//				JApplication.selectedHistoryRows.add(i);
//		}

		// 請求ボタン制限解除
		int count = 0;

		datas = new Vector<JKessaiProcessData>();

			// eidt s.inoue 2011/03/31
			for( int i = 0;i < grid.getVOListTableModel().getRowCount();i++ ){

				vo = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);

				if(vo.getCHECKBOX_COLUMN().equals("Y")){
					count++;

					// eidt s.inoue 2012/01/20
					JKessaiProcessData dataItem = getKesaiItemData(vo);
					if (dataItem == null)
						return;
					datas.add(dataItem);
				}
			}

			if( count != 0 )
			{
				try{
					registerSeikyuData();
				}catch (Exception ex){
					try{
						JApplication.kikanDatabase.rollback();
					}catch(SQLException exx){
					}
					ex.printStackTrace();
					logger.error(ex.getMessage());
				}
			}
			// edit n.ohkubo 2014/10/01　追加　start
			//1件も選択されていない場合、メッセージを表示して、ボタンの制御は行わない
			else{
				JErrorMessage.show("M3548", this);
				return;
			}
			// edit n.ohkubo 2014/10/01　追加　end
			
		buttonCtrl();
	}

	// move s.inoue 2012/01/20
	private void registerSeikyuData() throws SQLException{
		// 決済、集計総tran
		JApplication.kikanDatabase.Transaction();

		JOutputNitijiSearchListFrameData validatedData = new JOutputNitijiSearchListFrameData();

		validatedData.setSYUBETU_CODE(jComboBox_SyubetuCode.getSelectedItem().toString());
		// eidt s.inoue 2011/03/31
		// if( ){
			try {
				/* 決済処理 */
				JKessaiProcess.runProcess(datas, JApplication.kikanNumber);

			} catch (Exception e1) {
				logger.error(e1.getMessage());
				JApplication.kikanDatabase.rollback();
				JErrorMessage.show("M4730", this);
				return;
			}

			/* 集計処理 */
			try{
				if(JSyuukeiProcess.RunProcess(datas) == false)
				{
					JApplication.kikanDatabase.rollback();
					JErrorMessage.show("M4731", this);
					return;
				}
			} catch (Exception e2){
				JApplication.kikanDatabase.rollback();
				logger.error(e2.getMessage());
				JErrorMessage.show("M4731", this);
				return;
			}

			state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;

			messageDialog.setMessageTitle("請求処理");
			messageDialog.setText("請求処理が終了しました");
			messageDialog.setVisible(true);
		// }
		// 決済、集計総Commit
		JApplication.kikanDatabase.Commit();
		// del s.inoue 2011/03/31
		// searchRefresh();
	}

	// eidt s.inoue 2013/06/20
	private boolean existFlg = false;
	private boolean notExistFlg = false;

	public JKessaiProcessData getKesaiItemData(JOutputNitijiSearchListFrameData vo){
		// Hashtable<String, String> item = result.get(i);
		boolean notExtMessage = false;
		JKessaiProcessData dataItem = new JKessaiProcessData();

		/* checkKigenSetting(
				item.get("KENSA_NENGAPI"),
				item.get("HKNJYA_LIMITDATE_START"),
				item.get("HKNJYA_LIMITDATE_END")); */

		checkKigenSetting(vo.getKENSA_NENGAPI(),vo.getHKNJYA_LIMITDATE_START(),vo.getHKNJYA_LIMITDATE_END());

		/* 受付ID */
		// dataItem.uketukeId = item.get("UKETUKE_ID");
		dataItem.uketukeId = vo.getUKETUKE_ID();
		/* 被保険者証等記号 */
		// dataItem.hiHokenjyaKigo = item.get("HIHOKENJYASYO_KIGOU");
		dataItem.hiHokenjyaKigo = vo.getHIHOKENJYASYO_KIGOU();
		/* 被保険者証等番号 */
		// dataItem.hiHokenjyaNumber = item.get("HIHOKENJYASYO_NO");
		dataItem.hiHokenjyaNumber = vo.getHIHOKENJYASYO_NO();
		/* 保険者番号（個人） */
		// dataItem.hokenjyaNumber = item.get("HKNJANUM");
		dataItem.hokenjyaNumber = vo.getHKNJANUM();
		/* 検査実施日 */
		// dataItem.KensaDate = item.get("KENSA_NENGAPI");
		dataItem.KensaDate = vo.getKENSA_NENGAPI();
		/* 支払代行機関番号 */
		// dataItem.daikouKikanNumber = item.get("SIHARAI_DAIKOU_BANGO");
		dataItem.daikouKikanNumber = vo.getSIHARAI_DAIKOU_BANGO();
		/* カナ氏名 */
		// dataItem.kanaName = item.get("KANANAME");
		dataItem.kanaName = vo.getKANANAME();
		/* 性別 */
		// dataItem.sex = item.get("SEX");
		dataItem.sex = vo.getSEX();
		/* 生年月日 */
		// dataItem.birthday = item.get("BIRTHDAY");
		dataItem.birthday = vo.getBIRTHDAY();

		if (dataItem.hiHokenjyaNumber == null || dataItem.hiHokenjyaNumber.isEmpty()) {
			/* 未入力エラー,被保険者証等番号が入力されていません。一覧で被保険者証等番号を確認してください。
			 * [改行]　氏名（カナ）[%s]、性別[%s]、生年月日[%s] */
			String sexName = dataItem.sex.equals("1") ? "男性" : "女性";
			String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };

			JErrorMessage.show("M4751", this, params);
			return null;
		}

		if (dataItem.KensaDate == null || dataItem.KensaDate.isEmpty()) {
			/* 入力エラー,健診結果データが存在しません。[改行]　氏名（カナ）[%s]、性別[%s]、生年月日[%s] */
			String sexName = dataItem.sex.equals("1") ? "男性" : "女性";
			String[] params = { dataItem.kanaName, sexName, dataItem.birthday, };

			JErrorMessage.show("M4753", this, params);
			return null;
		}

		/*
		 * 種別コードを格納する
		 */
		if( dataItem.daikouKikanNumber != null && ! dataItem.daikouKikanNumber.isEmpty() )
		{
			dataItem.syubetuCode = "1";
		}else{
			dataItem.syubetuCode = "6";
		}

		// 実施区分を格納する（特定健診は 1 固定）
		dataItem.jissiKubun = JISSIKUBUN_TOKUTEIKENSHIN;

		/* 請求区分を格納する。 */
		String seikyuKubun = getSeikyuKubun();

		if (seikyuKubun == null || seikyuKubun.isEmpty()) {
			/* M4732：エラー,請求区分の取得に失敗しました。,0,0 */
			JErrorMessage.show("M4733", this);
			return null;
		}

		dataItem.seikyuKubun = seikyuKubun;

		String[] registKensa= JInputKessaiDataFrameCtrl.isNotExistKensaKoumoku(
				dataItem.hokenjyaNumber,
				dataItem.uketukeId,
				dataItem.KensaDate,
				dataItem.seikyuKubun);

		if (registKensa != null && !existFlg){
			// edit s.inoue 20081119
			if (!notExtMessage){
				JErrorMessage.show("M3635", this);
				notExtMessage = true;
				// eidt s.inoue 2013/06/20
				existFlg = true;
			}
		}

		String[] existKensa= JInputKessaiDataFrameCtrl.isExistKensaKoumoku(
				dataItem.hokenjyaNumber,
				dataItem.uketukeId,
				dataItem.KensaDate,
				dataItem.seikyuKubun);

		if (existKensa != null && !notExistFlg){
			if (!notExtMessage){
				JErrorMessage.show("M3636", this );
				notExtMessage = true;
				// eidt s.inoue 2013/06/20
				notExistFlg = true;
			}
		}
		return dataItem;
	}


	private void checkKigenSetting(String kenshinDate,String limitDataStart,String limitDataEnd){
		if (!kenshinDate.equals("") &&
			!limitDataStart.equals("") &&
				!limitDataEnd.equals("")){
			if(!(Integer.parseInt(limitDataStart) <= Integer.parseInt(kenshinDate) &&
					Integer.parseInt(kenshinDate) <= Integer.parseInt(limitDataEnd))){
				JErrorMessage.show("M4756", this);
			}
		}
	}

	// eidt s.inoue 2012/01/20
	// 引数削除 int i
	private String getSeikyuKubun() {
		// eidt s.inoue 2011/03/31
		// 請求区分を格納する
		String query = "SELECT * FROM T_KENSAKEKA_TOKUTEI WHERE " +
				"UKETUKE_ID = " + JQueryConvert.queryConvert(vo.getUKETUKE_ID()) + " AND " +
				"KENSA_NENGAPI = " + JQueryConvert.queryConvert(vo.getKENSA_NENGAPI());
				// "UKETUKE_ID = " + JQueryConvert.queryConvert(result.get(i).get("UKETUKE_ID")) + " AND " +
				// "KENSA_NENGAPI = " + JQueryConvert.queryConvert(result.get(i).get("KENSA_NENGAPI"));

		ArrayList<Hashtable<String, String>> tbl = null;
		try{
			tbl = JApplication.kikanDatabase.sendExecuteQuery(query);
		}catch(SQLException ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}

		String seikyuKubun = null;
		if (tbl != null && tbl.size() == 1) {
			seikyuKubun = tbl.get(0).get("SEIKYU_KBN");
		}

		return seikyuKubun;
	}

	public void pushedSeikyuPrintButton()
	{
		// 選択行を抽出する。
		// int rowCount = table.getRowCount();
		int rowCount = grid.getVOListTableModel().getRowCount();

		// 印刷用
		TreeMap<String, Object> printDataNitiji = new TreeMap<String, Object>();
		ProgressWindow progressWindow = new ProgressWindow(this, false,true);
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		ArrayList<String> keyList =  new ArrayList<String>();

		// 選択チェック
		for (int i = 0; i < rowCount; i++) {
			vo = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (vo.getCHECKBOX_COLUMN().equals("Y")) {
				selectedRows.add(i);
			}
		}

		// 選択行なし
		int selectedCount = selectedRows.size();
		if (selectedCount == 0) {
			JErrorMessage.show("M3548", this);return;
		}

		Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

		// 日計データ作成
		for (int j = 0; j < selectedCount; j++) {

//			int k = selectedRows.get(j);
			vo = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(j));

			progressWindow.setGuidanceByKey("common.frame.progress.guidance.main");
			progressWindow.setVisible(true);

			// eidt s.inoue 2011/03/31
			// 受付ID
			// String uketukeId = result.get(k).get("UKETUKE_ID");
			String uketukeId = vo.getUKETUKE_ID();
			tmpKojin.put("UKETUKE_ID", uketukeId);

			Nikeihyo nikeihyo = new Nikeihyo();

			if (!nikeihyo.setPrintSeikyuNitijiDataSQL(tmpKojin)){
			 	// データ移送失敗
				logger.warn("日次データ作成失敗");
			 	JErrorMessage.show("M3530", this);
			}

			// 印刷 受付ID:印刷データ
			printDataNitiji.put(String.valueOf(uketukeId), nikeihyo);
			keyList.add(String.valueOf(uketukeId));
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
		PrintSeikyuNitiji nitiji = new PrintSeikyuNitiji();
		nitiji.setQueryNitijiResult(printDataNitiji);
		nitiji.setQueryResult(printData);
		nitiji.setKeys(keyList);
		progressWindow.setVisible(false);

		try {
			nitiji.beginPrint();
		} catch (PrinterException err) {
			logger.error(err.getMessage());
			JErrorMessage.show("M3531", this);
			// progressWindow.setVisible(false);
		}
	}

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

	/**
	 * 請求データ編集処理ボタンが押された場合 2
	 */
	public void pushedSeikyuEditButton()
	{
//		switch(state)
//		{
//		case STATUS_AFTER_SEIKYU:

// eidt s.inoue 2012/11/30
//			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//			JOutputNitijiSearchListFrameData vo = null;
//			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//				vo = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//				if (vo.getCHECKBOX_COLUMN().equals("Y"))
//					selectedRows.add(i);
//			}
//
//			// eidt s.inoue 2012/06/29
//			if (selectedRows.size() != 1){
//				JErrorMessage.show("M4758", this);
//				return;
//			}

		// add s.inoue 2013/11/11
		JApplication.selectedHistoryRows = new ArrayList<Integer>();

			ArrayList<Integer> selectedRows = new ArrayList<Integer>();
			JOutputNitijiSearchListFrameData chk = null;
			int jcnt = 0;

			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				chk = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (chk.getCHECKBOX_COLUMN().equals("Y")){
					jcnt++;selectedRows.add(i);
					// add s.inoue 2013/11/11
					JApplication.selectedHistoryRows = new ArrayList<Integer>();
				}
			}
			// eidt s.inoue 2012/03/01
			if (jcnt != 1 ){
				JErrorMessage.show("M4758", this);
				return;
			}

			vo =(JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(0));

			// 一つだけ選択されている場合のみ
//			if( table.getSelectedRowCount() == 1 )
//			{
//				int[] selection = table.getSelectedRows();
//			int[] selection = selectedRows.toArray():
//				int modelRow=0;
//	            for (int i = 0; i < selection.length; i++) {
//	                // テーブルモデルの行数に変換
//	                modelRow = table.convertRowIndexToModel(selection[i]);
//	                System.out.println("View Row: " + selection[i] + " Model Row: " + modelRow);
//	            }
//	            Hashtable<String, String> resultItem = result.get(modelRow);

				// String uketukeId = resultItem.get("UKETUKE_ID");
				// String hihokenjyasyoKigou = resultItem.get("HIHOKENJYASYO_KIGOU");
				// String hihokenjyasyoNo = resultItem.get("HIHOKENJYASYO_NO");
				// String kensaNengapi = resultItem.get("KENSA_NENGAPI");
				String uketukeId = vo.getUKETUKE_ID();
				String hihokenjyasyoKigou = vo.getHIHOKENJYASYO_KIGOU();
				String hihokenjyasyoNo = vo.getHIHOKENJYASYO_NO();
				String kensaNengapi = vo.getKENSA_NENGAPI();


// eidt s.inoue 2012/01/18
				JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
						uketukeId,
						hihokenjyasyoKigou,
						hihokenjyasyoNo,
						kensaNengapi,
						datas);

				// eidt s.inoue 2011/05/09
				WindowRefreshEvent win = new WindowRefreshEvent();

				// win.setselectedData(selectedRows.get(0));
				JScene.CreateDialog(this,ctrl,win);

				state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
//				break;
//			}
//		}
		buttonCtrl();
	}

	/**
	 * フレームの状態によって押せるボタンなどを制御する
	 */
	public void buttonCtrl(){
		switch(state){
		case STATUS_INITIALIZED:
			buttonSeikyu.setEnabled(false);
			buttonSeikyuEdit.setEnabled(false);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
			break;
		case STATUS_AFTER_SEARCH:
			buttonSeikyu.setEnabled(true);
			buttonSeikyuEdit.setEnabled(false);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
			break;
		case STATUS_AFTER_SEIKYU:
			buttonSeikyu.setEnabled(true);
			buttonSeikyuEdit.setEnabled(true);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(true);
			break;
		case STATUS_AFTER_HL7:
			buttonSeikyu.setEnabled(false);
			buttonSeikyuEdit.setEnabled(false);
//			jButton_Search.setEnabled(true);
//			jButton_AllSelect.setEnabled(false);
			break;
		}
		buttonclose.setEnabled(true);
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			// eidt s.inoue 2011/05/09
			// selectedData = getSelectedRow();
			// selectedData.add(0, getselectedData());

			// ArrayList<Integer> selectedHistoryRows = new ArrayList<Integer>();
			JApplication.selectedHistoryRows = new ArrayList<Integer>();
			JOutputNitijiSearchListFrameData chk = null;
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				chk = (JOutputNitijiSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (chk.getCHECKBOX_COLUMN().equals("Y"))
					JApplication.selectedHistoryRows.add(i);
			}

			// eidt s.inoue 2011/05/10
			// ViewSettings.getGridPageSize()
		   	int rowCount = ((currentPage -1)*JApplication.gridViewSearchCount) + currentRow + 1;
			currentTotalRows = rowCount;
			countText.setText(currentTotalRows + "件目");

			// eidt s.inoue 2012/11/08
			// reloadButton.doClick();
			// 自動リロードするスレッド
//			Thread reload = new ActionAutoReloadThread();
//			reload.start();

			// eidt s.inoue 2012/11/21
			// grid.reloadData();
			reloadButton.doClick();

			// 選択状態を保持する
			// ArrayList<Integer> selectedRows = null;
			// selectedRows = table.getselectedRows(modelfixed,table);
			state = JOutputHL7FrameState.STATUS_AFTER_SEIKYU;
			// searchRefresh();
			buttonCtrl();
			// table.setselectedRows(modelfixed,selectedRows);
		}

// del s.inoue 2012/11/08
//		private int sel = 0;
//		public void setselectedData(Integer selIdx){
//			sel = selIdx;
//		}
//		public int getselectedData(){
//			return sel;
//		}
	}

	/* ボタンアクション */
	public void closeButtton_actionPerformed(ActionEvent e) {
		
		grid = null;	// edit n.ohkubo 2015/08/01　追加　メニューから「ログインへ戻る」でエラーが表示される対応
		
		dispose();
		// add s.inoue 2013/04/05
		if (JApplication.selectedHistoryRows.size() == 0)return;

		// add s.inoue 2012/11/08
		for (int i=0;i < grid.getVOListTableModel().getRowCount(); i++) {
			JApplication.selectedHistoryRows.remove(i);
		}
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
