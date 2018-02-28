package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.awt.event.*;

import org.apache.log4j.Logger;
import org.openswing.swing.client.*;
import org.openswing.swing.table.columns.client.*;

import com.lowagie.tools.Executable;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenReloadButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei.JIppanHantei;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.ippanhantei.JIppanHanteiStartData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei.JKaisoukaHantei;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.kekkahantei.JKaisoukaHanteiData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei.JMTHantei;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.mthantei.JMTHanteiData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintKekka;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JHanteiSearchResultListFrame extends JFrame implements KeyListener,ActionListener {

	protected Connection conn = null;
	protected GridControl grid = new GridControl();

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

//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
	// del s.inoue 2012/11/14
//	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected TextControl countText = new TextControl();

	protected ExtendedOpenGenericButton closeButton = null;

	// buttonコントロール
	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonHantei = null;
	// add s.inoue 2012/06/07
	protected ExtendedOpenGenericButton buttonGraph = null;
	protected ExtendedOpenGenericButton buttonPrintTuti = null;
	protected ExtendedOpenGenericButton buttonPrintSetumei = null;
	protected ExtendedOpenGenericButton buttonInputDetail = null;
	// buttonコントロール
	protected ExtendedOpenGenericButton buttonCheck = null;

	/* control */
	protected TextColumn textColumn_Name = new TextColumn();
	protected TextColumn textColumn_HokensyoCode = new TextColumn();
	protected TextColumn textColumn_HokensyoNumber = new TextColumn();
	protected TextColumn dateColumn_KenshinDateFrom = new TextColumn();
	// add s.inoue 2012/10/25
	protected TextColumn dateColumn_KenshinDateTo = new TextColumn();
	protected TextColumn textColumn_sex = new TextColumn();
	protected TextColumn textColumn_birthday = new TextColumn();
	protected TextColumn textColumn_inputFlg = new TextColumn();

	// eidt s.inoue 2012/10/25
	protected ComboColumn textColumn_hokenjaNo = new ComboColumn();
	protected ComboColumn textColumn_hsido = new ComboColumn();
	protected ComboColumn textColumn_metabo = new ComboColumn();

	protected TextColumn textColumn_jyushinSeiriNo = new TextColumn();
	protected TextColumn textColumn_shiharaiDaikouNo = new TextColumn();
	protected TextColumn textColumn_comment = new TextColumn();
	protected TextColumn textColumn_kanaName = new TextColumn();
	protected TextColumn textColumn_hanteiNengapi = new TextColumn();
	protected TextColumn textColumn_tutiNengapi = new TextColumn();
	protected TextColumn textColumn_nendo = new TextColumn();
	protected CheckBoxColumn checkColumn_checkBox = new CheckBoxColumn();

	private static final String CODE_BMI = "9N011000000000001";
	private static final int COLUMN_INDEX_CHECK = 0;
	private static final int COLUMN_INDEX_NENDO = 1;
	private static final int COLUMN_INDEX_JYUSHIN_SEIRI_NO = 2;
	private static final int COLUMN_INDEX_KANANAME = 3;
	private static final int COLUMN_INDEX_NAME = 4;
	private static final int COLUMN_INDEX_BIRTHDAY = 5;
	private static final int COLUMN_INDEX_SEX = 6;
	private static final int COLUMN_INDEX_INPUT_FLAG = 7;
	private static final int COLUMN_INDEX_METBO_HANTEI = 8;
	private static final int COLUMN_INDEX_HOKENSHIDOU_LEVEL = 9;
	private static final int COLUMN_INDEX_KENSA_NENGAPI = 10;
	private static final int COLUMN_INDEX_HANTEI_NENGAPI = 11;
	private static final int COLUMN_INDEX_TUTI_NENGAPI = 12;
	private static final int COLUMN_INDEX_HIHOKENSHA_KIGO = 13;
	private static final int COLUMN_INDEX_HIHOKENSHA_NO = 14;
	private static final int COLUMN_INDEX_HKNJANUM = 15;
	private static final int COLUMN_INDEX_DAIKOU = 16;
	private static final int COLUMN_INDEX_KOMENTO = 17;
	private static final int COLUMN_INDEX_UKETUKE_ID = 18;

	private static final String CODE_METABO_HANTEI = "9N501000000000011";  //  @jve:decl-index=0:
	private static final String CODE_HOKENSHIDOU_LEVEL = "9N506000000000011";
	private static final String CODE_TAIJU = "9N006000000000001";
	private static final String CODE_SHINCHOU = "9N001000000000001";
	private static final String CODE_HUKUYAKU_3 = "9N711000000000011";
	private static final String CODE_HUKUYAKU_2 = "9N706000000000011";
	private static final String CODE_HUKUYAKU_1 = "9N701000000000011";

	// eidt s.inoue 2012/09/03
	private static final String CODE_HBA1C_4_JDS = "3D045000001999902";
	private static final String CODE_HBA1C_3_JDS = "3D045000001927102";
	private static final String CODE_HBA1C_2_JDS = "3D045000001920402";
	private static final String CODE_HBA1C_1_JDS = "3D045000001906202";
	private static String CODE_HBA1C_4 = CODE_HBA1C_4_JDS;
	private static String CODE_HBA1C_3 = CODE_HBA1C_3_JDS;
	private static String CODE_HBA1C_2 = CODE_HBA1C_2_JDS;
	private static String CODE_HBA1C_1 = CODE_HBA1C_1_JDS;
	private static final String CODE_HBA1C_4_NGSP = "3D046000001999902";
	private static final String CODE_HBA1C_3_NGSP = "3D046000001927102";
	private static final String CODE_HBA1C_2_NGSP = "3D046000001920402";
	private static final String CODE_HBA1C_1_NGSP = "3D046000001906202";

	private static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
	private static final String CODE_SHUSHUKUKI_KETSUATSU_1 = "9A751000000000001";
	private static final String CODE_SHUSHUKUKI_KETSUATSU_2 = "9A752000000000001";
	private static final String CODE_SHUSHUKUKI_KETSUATSU_3 = "9A755000000000001";
	private static final String CODE_KAKUCHOKI_KETSUATSU_1 = "9A761000000000001";
	private static final String CODE_KAKUCHOKI_KETSUATSU_2 = "9A762000000000001";
	private static final String CODE_KAKUCHOKI_KETSUATSU_3 = "9A765000000000001";  //  @jve:decl-index=0:
	private static final String CODE_HDL_3 = "3F070000002399901";
	private static final String CODE_HDL_2 = "3F070000002327201";
	private static final String CODE_HDL_1 = "3F070000002327101";
	private static final String CODE_NAIZO_SHIBOU_MENSEKI = "9N021000000000001";
	private static final String CODE_KUHUKUJI_KETTO_4 = "3D010000001999901";
	private static final String CODE_KUHUKUJI_KETTO_3 = "3D010000001927201";
	private static final String CODE_KUHUKUJI_KETTO_2 = "3D010000002227101";
	private static final String CODE_KUHUKUJI_KETTO_1 = "3D010000001926101";
	private static final String CODE_CYUSEISHIBOU_3 = "3F015000002399901";
	private static final String CODE_CYUSEISHIBOU_2 = "3F015000002327201";
	private static final String CODE_CYUSEISHIBOU_1 = "3F015000002327101";
	private static final String CODE_HUKUI_ZIKOSHINKOKU = "9N016160300000001";
	private static final String CODE_HUKUI_ZIKO_HANTEI = "9N016160200000001";
	private static final String CODE_HUKUI_ZISSOKU = "9N016160100000001";
	private static final String WORK_PDF_TMP_13KEKKA_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"13_kekkaura.pdf";
	protected final static String CONST_VALUE= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei.JHanteiSearchResultListFrameData";

	protected int currentRow = 0;
	protected int currentPage = 0;
	protected int currentTotalRows = 0;

	private IDialog pageSelectDialog;
	private static Logger logger = Logger.getLogger(JHanteiSearchResultListFrame.class);
	private ArrayList<Integer> selectedData = new ArrayList<Integer>();

	/* コンストラクタ */
	public JHanteiSearchResultListFrame(Connection conn,
			JHanteiSearchResultListFrameCtl controller) {
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
			JHanteiSearchResultListFrameCtl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);
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
		textColumn_Name.setPreferredWidth(120);
		// eidt s.inoue 2012/10/25
		textColumn_Name.setColumnVisible(false);
		textColumn_Name.setColumnFilterable(false);
		textColumn_Name.setColumnSortable(false);

		textColumn_HokensyoCode.setColumnFilterable(true);
		textColumn_HokensyoCode.setColumnName("HIHOKENJYASYO_KIGOU");
		textColumn_HokensyoCode.setColumnSortable(true);
		textColumn_HokensyoCode.setPreferredWidth(110);
		// eidt s.inoue 2012/10/25
		textColumn_HokensyoCode.setColumnVisible(false);
		textColumn_HokensyoCode.setColumnFilterable(false);
		textColumn_HokensyoCode.setColumnSortable(false);

		textColumn_HokensyoNumber.setColumnFilterable(true);
		textColumn_HokensyoNumber.setColumnName("HIHOKENJYASYO_NO");
		textColumn_HokensyoNumber.setColumnSortable(true);
		textColumn_HokensyoNumber.setPreferredWidth(110);
		// eidt s.inoue 2012/10/25
		textColumn_HokensyoNumber.setColumnVisible(false);
		textColumn_HokensyoNumber.setColumnFilterable(false);
		textColumn_HokensyoNumber.setColumnSortable(false);

		dateColumn_KenshinDateFrom.setColumnFilterable(true);
		dateColumn_KenshinDateFrom.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateFrom.setColumnSortable(true);
		dateColumn_KenshinDateFrom.setPreferredWidth(80);

		// add s.inoue 2012/10/23
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateTo.setColumnSortable(false);

		dateColumn_KenshinDateTo.setColumnVisible(false);
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnSortable(false);

		textColumn_sex.setColumnFilterable(true);
		textColumn_sex.setColumnName("SEX");
		textColumn_sex.setColumnSortable(true);
		textColumn_sex.setPreferredWidth(40);
		// eidt s.inoue 2012/10/25
		textColumn_sex.setColumnVisible(false);
		textColumn_sex.setColumnFilterable(false);
		textColumn_sex.setColumnSortable(false);

		textColumn_birthday.setColumnFilterable(true);
		textColumn_birthday.setColumnName("BIRTHDAY");
		textColumn_birthday.setColumnSortable(true);
		textColumn_birthday.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_birthday.setColumnVisible(false);
		textColumn_birthday.setColumnFilterable(false);
		textColumn_birthday.setColumnSortable(false);

		// eidt s.inoue 2011/04/26
		textColumn_inputFlg.setColumnFilterable(false);
		textColumn_inputFlg.setColumnName("KEKA_INPUT_FLG");
		textColumn_inputFlg.setColumnSortable(true);
		textColumn_inputFlg.setPreferredWidth(40);
		// eidt s.inoue 2012/10/25
		textColumn_inputFlg.setColumnVisible(false);
		textColumn_inputFlg.setColumnFilterable(false);
		textColumn_inputFlg.setColumnSortable(false);

		textColumn_metabo.setColumnFilterable(true);
		textColumn_metabo.setColumnName("METABO");
		textColumn_metabo.setColumnSortable(true);
		textColumn_metabo.setPreferredWidth(80);
		// add s.inoue 2012/10/25
		textColumn_metabo.setDomainId("METABOHANTEI_LEVEL");

		textColumn_hsido.setColumnFilterable(true);
		textColumn_hsido.setColumnName("HOKENSIDO");
		textColumn_hsido.setColumnSortable(true);
		textColumn_hsido.setPreferredWidth(100);
		// add s.inoue 2012/10/25
		textColumn_hsido.setDomainId("HOKENSIDO_LEVEL");

		textColumn_jyushinSeiriNo.setColumnFilterable(true);
		textColumn_jyushinSeiriNo.setColumnName("JYUSHIN_SEIRI_NO");
		textColumn_jyushinSeiriNo.setColumnSortable(true);
		textColumn_jyushinSeiriNo.setPreferredWidth(100);

		textColumn_hokenjaNo.setColumnFilterable(true);
		textColumn_hokenjaNo.setColumnName("HKNJANUM");
		textColumn_hokenjaNo.setColumnSortable(true);
		textColumn_hokenjaNo.setPreferredWidth(250);
		textColumn_hokenjaNo.setDomainId("T_HOKENJYA");

		textColumn_shiharaiDaikouNo.setColumnFilterable(true);
		textColumn_shiharaiDaikouNo.setColumnName("SIHARAI_DAIKOU_BANGO");
		textColumn_shiharaiDaikouNo.setColumnSortable(true);
		textColumn_shiharaiDaikouNo.setPreferredWidth(150);
		// eidt s.inoue 2012/10/25
		textColumn_shiharaiDaikouNo.setColumnVisible(false);
		textColumn_shiharaiDaikouNo.setColumnFilterable(false);
		textColumn_shiharaiDaikouNo.setColumnSortable(false);

		textColumn_comment.setColumnFilterable(true);
		textColumn_comment.setColumnName("KOMENTO");
		textColumn_comment.setColumnSortable(true);
		textColumn_comment.setPreferredWidth(150);
		// eidt s.inoue 2012/10/25
		textColumn_comment.setColumnVisible(false);
		textColumn_comment.setColumnFilterable(false);
		textColumn_comment.setColumnSortable(false);

		textColumn_kanaName.setColumnFilterable(true);
		textColumn_kanaName.setColumnName("KANANAME");
		textColumn_kanaName.setColumnSortable(true);
		textColumn_kanaName.setPreferredWidth(175);

		textColumn_hanteiNengapi.setColumnFilterable(true);
		textColumn_hanteiNengapi.setColumnName("HANTEI_NENGAPI");
		textColumn_hanteiNengapi.setColumnSortable(true);
		textColumn_hanteiNengapi.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_hanteiNengapi.setColumnVisible(false);
		textColumn_hanteiNengapi.setColumnFilterable(false);
		textColumn_hanteiNengapi.setColumnSortable(false);

		textColumn_tutiNengapi.setColumnFilterable(true);
		textColumn_tutiNengapi.setColumnName("TUTI_NENGAPI");
		textColumn_tutiNengapi.setColumnSortable(true);
		textColumn_tutiNengapi.setPreferredWidth(80);
		// eidt s.inoue 2012/10/25
		textColumn_tutiNengapi.setColumnVisible(false);
		textColumn_tutiNengapi.setColumnFilterable(false);
		textColumn_tutiNengapi.setColumnSortable(false);

		textColumn_nendo.setColumnFilterable(true);
		textColumn_nendo.setColumnName("NENDO");
		textColumn_nendo.setColumnSortable(true);
		textColumn_nendo.setPreferredWidth(40);

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
	    buttonPanel.add(buttonClose, null);
	    // add s.inoue 2012/06/07
	    buttonPanel.add(buttonGraph, null);

	    buttonPanel.add(buttonHantei, null);
	    buttonPanel.add(buttonPrintTuti, null);
	    buttonPanel.add(buttonPrintSetumei, null);
	    buttonPanel.add(buttonInputDetail, null);

		// action設定
		buttonClose.addActionListener(new ListFrame_closeButton_actionAdapter(this));
		// add s.inoue 2012/06/07
		buttonGraph.addActionListener(new ListFrame_closeButton_actionAdapter(this));
		buttonHantei.addActionListener(new ListFrame_closeButton_actionAdapter(this));
		buttonPrintTuti.addActionListener(new ListFrame_closeButton_actionAdapter(this));
		buttonPrintSetumei.addActionListener(new ListFrame_closeButton_actionAdapter(this));
		buttonInputDetail.addActionListener(new ListFrame_closeButton_actionAdapter(this));
		buttonCheck.addActionListener(new ListFrame_closeButton_actionAdapter(this));

		// add s.inoue 2012/11/21
		navigatorBar.addAfterActionListener(new ActionListener() {
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

		// openswing s.inoue 2011/01/25
//		buttonsPanel.add(closeButton,null);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);

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
		grid.getColumnContainer().add(textColumn_birthday, null);
		grid.getColumnContainer().add(textColumn_sex, null);
		grid.getColumnContainer().add(textColumn_inputFlg, null);
		grid.getColumnContainer().add(textColumn_metabo, null);
		grid.getColumnContainer().add(textColumn_hsido, null);
		grid.getColumnContainer().add(dateColumn_KenshinDateFrom, null);
		// add s.inoue 2012/10/25
		grid.getColumnContainer().add(dateColumn_KenshinDateTo, null);
		grid.getColumnContainer().add(textColumn_hanteiNengapi, null);
		grid.getColumnContainer().add(textColumn_tutiNengapi, null);
		grid.getColumnContainer().add(textColumn_HokensyoCode, null);
		grid.getColumnContainer().add(textColumn_HokensyoNumber, null);
		grid.getColumnContainer().add(textColumn_hokenjaNo, null);
		grid.getColumnContainer().add(textColumn_shiharaiDaikouNo, null);
		grid.getColumnContainer().add(textColumn_comment, null);

//		// openswing s.inoue 2011/01/26
//		grid.addMouseListener(new JSingleDoubleClickEvent(this, button,modelfixed));

		this.getContentPane().add(grid, BorderLayout.CENTER);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.searchresult.frame.title","tokutei.searchresult.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

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
	class ListFrame_closeButton_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
		JHanteiSearchResultListFrame adaptee;

		  ListFrame_closeButton_actionAdapter(JHanteiSearchResultListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  // buttonアクション
		  public void actionPerformed(ActionEvent e) {

			Object source = e.getSource();
			// eidt s.inoue 2011/05/09
			selectedData = new ArrayList<Integer>();
		   	/* 閉じる */
		   	// eidt s.inoue 2011/04/21
		   	if (source == buttonCheck){
				logger.info(buttonCheck.getText());
				setCheckBoxValue();
		  	}else if (source == buttonClose){
				logger.info(buttonClose.getText());
				adaptee.closeButtton_actionPerformed(e);
			// add s.inoue 2012/06/07
		  	}else if (source == buttonGraph){
		  		logger.info(buttonGraph.getText());
		  		// add s.inoue 2012/11/30
		  		// setSelectedRow(JApplication.selectedPreservRows);
		  		// eidt s.inoue 2012/11/09
		  		if (!getCheckBoxCount())
		  			pushedGraphButton(null);
			}else if (source == buttonHantei){
				logger.info(buttonHantei.getText());
				pushedMetaboButton(null);
				// add s.inoue 2012/11/21
				reloadButton.doClick();
				// grid.reloadData();
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			}else if (source == buttonPrintTuti){
				logger.info(buttonPrintTuti.getText());
				pushedPrintButton( null);
				// eidt s.inoue 2011/05/09
				chkFlg = true;
			}else if (source == buttonPrintSetumei){
				logger.info(buttonPrintSetumei.getText());
				pushePrintSetsumei(null);
			}else if (source == buttonInputDetail){
				logger.info(buttonInputDetail.getText());
				pushedOKButton(null);
			}

// del s.inoue 2012/11/05
//		   	if (chkFlg)
//		   	selectedData = getSelectedRow();
//
//			// grid.reloadData();
//		    reloadButton.doClick();
//
//			// 自動リロードするスレッド
//			Thread reload = new ActionAutoReloadThread();
//			reload.start();
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

	// チェック状態を取得
	private boolean getCheckBoxCount(){
		boolean ret = false;
		JHanteiSearchResultListFrameData chk = null;
		int jcnt = 0;

		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y")){
				jcnt++;
			}
		}
		// eidt s.inoue 2012/03/01
		if (jcnt != 1 ){
			JErrorMessage.show("M4956", this);
			ret = true;
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
		}

		// add s.inoue 2012/06/07
		if (buttonGraph == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Hantei_Graph);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonGraph= new ExtendedOpenGenericButton(
					"graph","グラフ(G)","グラフ(ALT+G)",KeyEvent.VK_G,icon);
		}
		// メタボ判定
		if (buttonHantei == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Hantei_Detail);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonHantei= new ExtendedOpenGenericButton(
					"hanteiMetabo","判定・階層化(E)","メタボリックシンドローム判定・階層化(ALT+E)",KeyEvent.VK_E,icon);
			buttonHantei.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 11));
		}
		// 通知表印刷
		if (buttonPrintTuti == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonPrintTuti= new ExtendedOpenGenericButton(
					"hanteiPrint","通知表印刷(T)","通知表印刷(ALT+T)",KeyEvent.VK_T,icon);
		}
		// 説明用印刷
		if (buttonPrintSetumei == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print2);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonPrintSetumei= new ExtendedOpenGenericButton(
					"hanteiSetumei","説明用印刷(S)","説明用印刷(ALT+S)",KeyEvent.VK_S,icon);
		}
		// 詳細
		if (buttonInputDetail == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Detail);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			buttonInputDetail= new ExtendedOpenGenericButton(
					"hanteiShosai","詳細(D)","詳細(ALT+D)",KeyEvent.VK_D,icon);
		}
	}

//	private int[] selectedRows = null;
//	private TKojinDao tKojinDao;
//	private ArrayList<Hashtable<String, String>> result;
//	private Hashtable<String, String> resultItem;
	private JHanteiSearchResultListFrameData validatedData = new JHanteiSearchResultListFrameData();
	private boolean chkFlg = false;
//	private ArrayList<Integer> selDat = new ArrayList<Integer>();

	// eidt s.inoue 2011/04/21
	private void setCheckBoxValue(){
		JHanteiSearchResultListFrameData chk = null;
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
				// chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
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
				// chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				// if (!chk.getCHECKBOX_COLUMN().equals("Y"))
				//	selectedData.add(i);
				grid.getVOListTableModel().setValueAt("Y", i, 0);
			}
			chkFlg = true;
		}
	}

	/** add s.inoue 2012/06/07 グラフ系表示処理 **/
	/**
	 * グラフ表示
	 */
	public void pushedGraphButton( ActionEvent e )
	{
		try {
			// add s.inoue 2012/06/21 引数対応
			JHanteiSearchResultListFrameData vo = null;
			Hashtable<String, String> hm = null;
			for(int i = 0 ; i < grid.getVOListTableModel().getRowCount(); i++){
				vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if(vo.getCHECKBOX_COLUMN().equals("Y"))
				{
					hm = new Hashtable<String, String>();
					hm.put("UKETUKE_ID", vo.getUKETUKE_ID());
					hm.put("HKNJANUM", vo.getHKNJANUM());
				}
			}
			ArrayList<Hashtable<String,String>> arr = new ArrayList<Hashtable<String,String>>();
			arr.add(hm);

			IDialog dialogs = DialogFactory.getInstance().createDialog(this,null,arr);
			dialogs.setMessageTitle("経年データグラフ");
			dialogs.setDialogTitle("経年データグラフ");
			dialogs.setDialogSelect(true);
			dialogs.setVisible(true);
// del s.inoue 2013/02/13
			// 戻値格納
//			if(dialogs.getStatus().equals(RETURN_VALUE.YES)){
//				Vector<String> vec = dialogs.getDataSelect();
//				System.out.println(vec.get(0));
//			}else if (dialogs.getStatus().equals(RETURN_VALUE.CANCEL)){
//				return;
//			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

//	    ChartPanel chartPanel = (ChartPanel) createDemoPanel();
//	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//	    setContentPane(chartPanel);
	}

	/**
	 * メタボリックシンドローム判定、階層化
	 *
	 * Modified 2008/04/01 若月
	 * 視認性向上のため、不要なコメントを削除した。
	 * また、処理のメソッド化などのりファクタリングを行なった。
	 */
	public void pushedMetaboButton( ActionEvent e )
	{
		Vector<JIppanHanteiStartData> ipDataSet = new Vector<JIppanHanteiStartData>();

		boolean existsSuccessResult = false;

		// add s.inoue 2012/11/08
		JApplication.selectedHistoryRows = new ArrayList<Integer>();
		JHanteiSearchResultListFrameData chk = null;

		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (chk.getCHECKBOX_COLUMN().equals("Y"))
				JApplication.selectedHistoryRows.add(i);
		}

		// edit s.inoue 2009/09/25
		// 選択状態を保持する
		// ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
//		ArrayList<Integer> selectedRows = null;
//		JHanteiSearchResultListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y"))
//				selectedRows.add(i);
//		}
		// eidt s.inoue 2011/05/09
//		int selectedRowCount = 0;
//		// ArrayList<Integer> selectedRows = new ArrayList<Integer>();
//		JHanteiSearchResultListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y")){
//				selDat.add(i);
//				System.out.println(selDat.add(i) +  "行目");
//				selectedRowCount++;
//			}
//		}

		/* 一般判定 */
		boolean success = this.ipanHantei(ipDataSet);
		if (! success) {
			JErrorMessage.show("M4953", this);
			return;
		}

		try
		{
			/* メタボ判定 */
			this.metaboHantei();

		}catch(Exception e1)
		{
			e1.printStackTrace();
			JErrorMessage.show("M4950", this);
			return;
		}

		try
		{
			/* 階層化 */
			this.kaisouka(existsSuccessResult);

		}catch(Exception e2)
		{
			e2.printStackTrace();
			JErrorMessage.show("M4951", this);
			return;
		}
		// del s.inoue 2011/03/30
		// チェック状態を保持
		// searchRefresh();
		// edit s.inoue 2009/09/25
		// table.setselectedRows(modelfixed,selectedRows);
		// eidt s.inoue 2011/05/09
		// setSelectedRow(selDat);
	}

	/**
	 * 階層化
	 */
	private void kaisouka(boolean existsSuccessResult) throws SQLException {

		JHanteiSearchResultListFrameData vo = null;

		// eidt s.inoue 2011/03/30
		for(int i = 0 ; i < grid.getVOListTableModel().getRowCount(); i++)
		{
			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);

			if(vo.getCHECKBOX_COLUMN().equals("Y"))
			{
				// eidt s.inoue 2011/03/30
				// String uketukeId = searchResult.get(i).get("UKETUKE_ID");
				// String KensaDate = searchResult.get(i).get("KENSA_NENGAPI");
				String uketukeId = vo.getUKETUKE_ID();
				String KensaDate = vo.getKENSA_NENGAPI();

				// eidt s.inoue 2013/01/29
				int tDate = 20130401;

				// 健診実施日が'130401以降であれば、H,Lの場合もPQ(結果値)を出力する
				if (tDate <= Integer.parseInt(KensaDate)){
					CODE_HBA1C_4 = CODE_HBA1C_4_NGSP;
					CODE_HBA1C_3 = CODE_HBA1C_3_NGSP;
					CODE_HBA1C_2 = CODE_HBA1C_2_NGSP;
					CODE_HBA1C_1 = CODE_HBA1C_1_NGSP;
				}

				int hokenLevelResult = 0;

				//階層化判定を行う
				JKaisoukaHanteiData kaisouData = new JKaisoukaHanteiData();

				// add s.inoue 2013/01/29
				kaisouData.setKensinJisiDate(KensaDate);

				// 年齢
				// String birthday = searchResult.get(i).get("BIRTHDAY");
				String birthday = vo.getBIRTHDAY();

				// edit s.inoue 20081113
				//ここから共通化getFiscalYear
				int Age = FiscalYearUtil.getFiscalYear(birthday,KensaDate);

				kaisouData.setAge(Age);

				// add ver2 s.inoue 2009/08/19
				if (Age == 75){
					kaisouData.setTargetAge(FiscalYearUtil.getJugeDate(birthday,KensaDate));
				}

				// BMI
				String BMI = GetResultValue(uketukeId, KensaDate, CODE_BMI);
				if(!BMI.equals(""))
				{
					kaisouData.setBMI(Double.valueOf(BMI));
				}

				// 中性脂肪
				String chuseiShibou = GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_1);
				if(chuseiShibou.equals(""))
				{
					String cyuseishibou2 = GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_2);
					if(! cyuseishibou2.equals("") )
					{
						chuseiShibou = cyuseishibou2;
					}else{
						String cyuseishibou3 = GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_3);
						if(!cyuseishibou3.equals("")){
							chuseiShibou = cyuseishibou3;
						}
					}
				}

				if(!chuseiShibou.equals("")){
					kaisouData.setChuseiShibou(Double.valueOf(chuseiShibou));
				}

				// 性別
				// eidt s.inoue 2011/03/30
				// kaisouData.setGender(searchResult.get(i).get("SEX"));
				kaisouData.setGender(vo.getSEX());

				// HbA1c
				String hba1c = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_1);
				if(hba1c.equals(""))
				{
					String hba1c_2 = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_2);
					if(hba1c_2.equals(""))
					{
						String hba1c_3 = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_3);
						if(hba1c_3.equals(""))
						{
							String hba1c_4 = GetResultValue(uketukeId, KensaDate, CODE_HBA1C_4);
							if(hba1c_4.equals(""))
							{
								// 該当項目なし
							}else{
								hba1c = hba1c_4;
							}
						}else{
							hba1c = hba1c_3;
						}
					}else{
						hba1c = hba1c_2;
					}
				}

				if(!hba1c.equals(""))
				{
					kaisouData.setHbA1c(Double.valueOf(hba1c));
				}

				// HDLコレステロール
				String HDLCholesterol = "";
				if(GetResultValue(uketukeId, KensaDate, CODE_HDL_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_HDL_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_HDL_3).equals(""))
						{
							// 該当項目なし
						}else{
							HDLCholesterol = GetResultValue(uketukeId, KensaDate, CODE_HDL_3);
						}
					}else{
						HDLCholesterol = GetResultValue(uketukeId, KensaDate, CODE_HDL_2);
					}
				}else{
					HDLCholesterol = GetResultValue(uketukeId, KensaDate, CODE_HDL_1);
				}
				if(!HDLCholesterol.equals(""))
				{
					kaisouData.setHDLCholesterol(Double.valueOf(HDLCholesterol));
				}

				// 腹囲（自己測定）
				String Hukui_JikoSokutei = GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKO_HANTEI);
				if(!Hukui_JikoSokutei.equals(""))
				{
					kaisouData.setHukui_JikoSokutei(Double.valueOf(Hukui_JikoSokutei));
				}

				// 腹囲（自己申告）
				String Hukui_JikoShinkoku = GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKOSHINKOKU);
				if(!Hukui_JikoShinkoku.equals(""))
				{
					kaisouData.setHukui_JikoShinkoku(Double.valueOf(Hukui_JikoShinkoku));
				}

				// 腹囲（実測）
				String Hukui_Jissoku = GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZISSOKU);
				if(!Hukui_Jissoku.equals(""))
				{
					kaisouData.setHukui_Jissoku(Double.valueOf(Hukui_Jissoku));
				}

				// 拡張時血圧（1回目）
				String KakutyoKetsuatsuNo1 = GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_1);
				if(!KakutyoKetsuatsuNo1.equals(""))
				{
					kaisouData.setKakutyoKetsuatsuNo1(Double.valueOf(KakutyoKetsuatsuNo1));
				}

				// 拡張時血圧（2回目）
				String KakutyoKetsuatsuNo2 = GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_2);
				if(!KakutyoKetsuatsuNo2.equals(""))
				{
					kaisouData.setKakutyoKetsuatsuNo2(Double.valueOf(KakutyoKetsuatsuNo2));
				}

				// 拡張時血圧（その他）
				String KakutyoKetsuatsuOther = GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_3);
				if(!KakutyoKetsuatsuOther.equals(""))
				{
					kaisouData.setKakutyoKetsuatsuOther(Double.valueOf(KakutyoKetsuatsuOther));
				}

				// 服薬（血圧）
				String KetsuatsuHukuyaku = GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_1);
				if(!KetsuatsuHukuyaku.equals(""))
				{
					kaisouData.setKetsuatsuHukuyaku(Double.valueOf(KetsuatsuHukuyaku));
				}

				// 服薬（血糖）
				String KettoHukuyaku = GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_2);
				if(!KettoHukuyaku.equals(""))
				{
					kaisouData.setKettoHukuyaku(Double.valueOf(KettoHukuyaku));
				}

				// 喫煙
				String Kitsuen = GetResultValue(uketukeId, KensaDate, "9N736000000000011");
				if(!Kitsuen.equals(""))
				{
					kaisouData.setKitsuen(Double.valueOf(Kitsuen));
				}

				// 空腹時血糖
				String kuhukujiKeto = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_1);
				if(kuhukujiKeto.equals(""))
				{
					String kuhukujiKeto2 = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_2);
					if(kuhukujiKeto2.equals(""))
					{
						String kuhukujiKeto3 = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_3);
						if(kuhukujiKeto3.equals(""))
						{
							String kuhukujiKeto4 = GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_4);
							if(! kuhukujiKeto4.equals("")){
								kuhukujiKeto = kuhukujiKeto4;
							}
						}else{
							kuhukujiKeto = kuhukujiKeto3;
						}
					}else{
						kuhukujiKeto = kuhukujiKeto2;
					}
				}

				if(!kuhukujiKeto.equals(""))
				{
					kaisouData.setKuhukujiKetto(Double.valueOf(kuhukujiKeto));
				}

				// 内臓脂肪面積
				String NaizouShibou = GetResultValue(uketukeId, KensaDate, CODE_NAIZO_SHIBOU_MENSEKI);
				if(!NaizouShibou.equals(""))
				{
					kaisouData.setNaizouShibou(Double.valueOf(NaizouShibou));
				}

				// 身長
				String Shintyou = GetResultValue(uketukeId, KensaDate, CODE_SHINCHOU);
				if(!Shintyou.equals(""))
				{
					kaisouData.setShintyou(Double.valueOf(Shintyou));
				}

				// 服薬（脂質）
				String ShishitsuHukuyaku = GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_3);
				if(!ShishitsuHukuyaku.equals(""))
				{
					kaisouData.setShishitsuHukuyaku(Double.valueOf(ShishitsuHukuyaku));
				}

				// 収縮時血糖（1回目）
				String ShushukuKetsuatsuNo1 = GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_1);
				if(!ShushukuKetsuatsuNo1.equals(""))
				{
					kaisouData.setShushukuKetsuatsuNo1(Double.valueOf(ShushukuKetsuatsuNo1));
				}

				// 収縮時血糖（2回目）
				String ShushukuKetsuatsuNo2 = GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_2);
				if(!ShushukuKetsuatsuNo2.equals(""))
				{
					kaisouData.setShushukuKetsuatsuNo2(Double.valueOf(ShushukuKetsuatsuNo2));
				}

				// 収縮時血糖（その他）
				String ShushukuKetsuatsuOther = GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_3);
				if(!ShushukuKetsuatsuOther.equals(""))
				{
					kaisouData.setShushukuKetsuatsuOther(Double.valueOf(ShushukuKetsuatsuOther));
				}

				// 体重
				String Taijyu = GetResultValue(uketukeId, KensaDate, CODE_TAIJU);
				if(!Taijyu.equals(""))
				{
					kaisouData.setTaijyu(Double.valueOf(Taijyu));
				}

				/* 判定処理 */
				hokenLevelResult = JKaisoukaHantei.Hantei(kaisouData);

				String Query = "UPDATE T_KENSAKEKA_SONOTA SET "
						+ "KEKA_TI = "
						+ JQueryConvert.queryConvert(String
								.valueOf(hokenLevelResult))
						+ " WHERE "
						+ "UKETUKE_ID = "
						+ JQueryConvert.queryConvert(uketukeId)
						+ " AND "
						+ "KENSA_NENGAPI = "
						+ JQueryConvert.queryConvert(KensaDate)
						+ " AND "
						+ "KOUMOKU_CD = "
						+ JQueryConvert
								.queryConvert(CODE_HOKENSHIDOU_LEVEL);


				JApplication.kikanDatabase.sendNoResultQuery(Query);

				/* 判定日を更新する */
				Calendar cal = Calendar.getInstance();
				String yearText = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
				String monthText = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
				String dayText = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

				String hanteibiText = yearText + monthText + dayText;

				Query =
					"UPDATE T_KENSAKEKA_TOKUTEI SET " +
					"HANTEI_NENGAPI = " + JQueryConvert.queryConvert(hanteibiText) +
					" WHERE " +
					"UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId) + " AND " +
					"KENSA_NENGAPI = " + JQueryConvert.queryConvert(KensaDate);
				JApplication.kikanDatabase.sendNoResultQuery(Query);

				existsSuccessResult = true;
			}
		}

		if (existsSuccessResult) {
			JErrorMessage.show("M4952", this);
		}
	}

	/**
	 * メタボ判定を行なう。
	 */
	private void metaboHantei() throws SQLException {

		// eidt s.inoue 2011/03/30
		JHanteiSearchResultListFrameData vo = null;

		for( int i = 0;i < grid.getVOListTableModel().getRowCount();i++ )
		{
			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);

			if(vo.getCHECKBOX_COLUMN().equals("Y")){
				// eidt s.inoue 2011/03/30
				// String uketukeId = searchResult.get(i).get("UKETUKE_ID");
				// String KensaDate = searchResult.get(i).get("KENSA_NENGAPI");
				String uketukeId = vo.getUKETUKE_ID();
				String KensaDate = vo.getKENSA_NENGAPI();

				int metaboResult;

				JMTHanteiData mtData = new JMTHanteiData();

				// eidt s.inoue 2011/03/30
				// 性別に関してデータを挿入
				// mtData.setGender(searchResult.get(i).get("SEX"));
				String sex = vo.getSEX().equals("男")?"1":"2";
				mtData.setGender(sex);

				// 腹囲
				mtData.setStomach(
						GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZISSOKU),
						GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKO_HANTEI),
						GetResultValue(uketukeId, KensaDate, CODE_HUKUI_ZIKOSHINKOKU)
				);

				// 中性脂肪
				if(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_3).equals(""))
						{
							// 該当項目なし
						}else{
							mtData.setNaturalFat(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_3));
						}
					}else{
						mtData.setNaturalFat(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_2));
					}
				}else{
					mtData.setNaturalFat(GetResultValue(uketukeId, KensaDate, CODE_CYUSEISHIBOU_1));
				}

				// 血糖値
				if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_3).equals(""))
						{
							if(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_4).equals(""))
							{
								// 該当項目なし
							}else{
								mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_4));
							}
						}else{
							mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_3));
						}
					}else{
						mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_2));
					}
				}else{
					mtData.setBloodSugar(GetResultValue(uketukeId, KensaDate, CODE_KUHUKUJI_KETTO_1));
				}

				// 内臓脂肪面積
				mtData.setInternalFatArea(GetResultValue(uketukeId, KensaDate, CODE_NAIZO_SHIBOU_MENSEKI));

				// HDL
				if(GetResultValue(uketukeId, KensaDate, CODE_HDL_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_HDL_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_HDL_3).equals(""))
						{
							// 該当項目なし
						}else{
							mtData.setHDL(GetResultValue(uketukeId, KensaDate, CODE_HDL_3));
						}
					}else{
						mtData.setHDL(GetResultValue(uketukeId, KensaDate, CODE_HDL_2));
					}
				}else{
					mtData.setHDL(GetResultValue(uketukeId, KensaDate, CODE_HDL_1));
				}

				// 血圧（拡張時）
				mtData.setMaxBloodPressre(
						GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_3),
						GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_2),
						GetResultValue(uketukeId, KensaDate, CODE_KAKUCHOKI_KETSUATSU_1)
				);

				// 血圧（収縮時）
				mtData.setMinBloodPressre(
						GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_3),
						GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_2),
						GetResultValue(uketukeId, KensaDate, CODE_SHUSHUKUKI_KETSUATSU_1)
				);

				// 採血時間
				mtData.setDrawBloodTime(GetResultValue(uketukeId, KensaDate, CODE_SAIKETSU_ZIKAN));

				// HbA1c
				if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_1).equals(""))
				{
					if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_2).equals(""))
					{
						if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_3).equals(""))
						{
							if(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_4).equals(""))
							{
								// 該当項目なし
							}else{
								mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_4));
							}
						}else{
							mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_3));
						}
					}else{
						mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_2));
					}
				}else{
					mtData.setHbA1c(GetResultValue(uketukeId, KensaDate, CODE_HBA1C_1));
				}

				// 服薬質問1（血圧）
				mtData.setQuestion1(GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_1));

				// 服薬質問2（血糖）
				mtData.setQuestion2(GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_2));

				// 服薬質問3（脂質）
				mtData.setQuestion3(GetResultValue(uketukeId, KensaDate, CODE_HUKUYAKU_3));

				// add s.inoue 2012/09/14
				boolean newHbA1c=false;
				if (CODE_HBA1C_1.equals(CODE_HBA1C_1_NGSP))
					newHbA1c = true;

				metaboResult = JMTHantei.checkMT(mtData,newHbA1c);

				String query =
					"UPDATE T_KENSAKEKA_SONOTA SET " +
					"KEKA_TI = " + JQueryConvert.queryConvert(String.valueOf(metaboResult)) +
					" WHERE " +
					"UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId) + " AND " +
					"KENSA_NENGAPI = " + JQueryConvert.queryConvert(KensaDate) + " AND " +
					"KOUMOKU_CD = " + JQueryConvert.queryConvert(CODE_METABO_HANTEI);

				JApplication.kikanDatabase.sendNoResultQuery(query);
			}
		}
	}

	private boolean ipanHantei(Vector<JIppanHanteiStartData> ipDataSet) {
		/*
		 * 一般判定
		 */
		// eidt s.inoue 2011/03/30
		JHanteiSearchResultListFrameData vo = null;

		for(int i = 0;i < grid.getVOListTableModel().getRowCount();i++ )
		{
			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if(vo.getCHECKBOX_COLUMN().equals("Y"))
			{
				JIppanHanteiStartData ipData = new JIppanHanteiStartData();
				// eidt s.inoue 2011/03/30
				// ipData.setUketukeId(searchResult.get(i).get("UKETUKE_ID"));
				// ipData.setKensaJissiDate(searchResult.get(i).get("KENSA_NENGAPI"));
				ipData.setUketukeId(vo.getUKETUKE_ID());
				ipData.setKensaJissiDate(vo.getKENSA_NENGAPI());

				ipDataSet.add(ipData);
			}
		}

		boolean success = JIppanHantei.Hantei(ipDataSet);

		return success;
	}

	/**
	 * 結果の値の取得
	 * @param uketukeId 受付ID
	 * @param KensaDate 検査日時
	 * @param KoumokuCode 項目コード
	 * @return 検査結果(KEKA_TI)
	 */
	private String GetResultValue(String uketukeId, String kensaDate, String koumokuCode) {

		TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
		TKensakekaSonotaDao dao = null;
		String returnValue = null;
		try {
			dao = (TKensakekaSonotaDao)DaoFactory.createDao(
					JApplication.kikanDatabase.getMConnection(), kensakekaSonota);
			kensakekaSonota = dao.selectByPrimaryKey(new Long(uketukeId), new Integer(kensaDate), koumokuCode);
			returnValue = kensakekaSonota.getKEKA_TI();
		} catch (Exception e) {
		}
		return ((returnValue != null) ? returnValue : "");
	}

	/**
	 * 説明用帳票（「受診者の方へ」）を印刷する
	 * @return
	 */
	public void pushePrintSetsumei( ActionEvent e ) {
		File stumeiPdf = new File(WORK_PDF_TMP_13KEKKA_PDF);
		JFrame ParentFrame = null;

		if(!stumeiPdf.exists()){
			JErrorMessage.show("M5204", this);
		}else{
			try {
				// Linux対応 20081017
				//Process process = Executable.openDocument(stumeiPdf, true);
				Executable pdfexe = new Executable();
				Process process=null;
				if (!pdfexe.isWindows() && !pdfexe.isMac()) {
					System.out.println(stumeiPdf.getPath());

					Process process0=  Runtime.getRuntime().exec("which acroread xpdf");
					InputStream is = process0.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String answer;
					String cmd=null;
					while ( (answer = br.readLine()) !=null) {
						cmd = answer;
					}
					if (cmd != null)  {
						pdfexe.acroread = cmd;
						process = Runtime.getRuntime().exec(cmd + " "+ stumeiPdf.getPath());
					}
				}
				else process = pdfexe.openDocument(stumeiPdf, true);

				if (process==null || process.exitValue() == 1) {
//		     		if (process.exitValue() == 1) {
						JErrorMessage.show("M9727", ParentFrame);
				}

			} catch (IOException e1) {
				e1.printStackTrace();
				JErrorMessage.show("M5203", this);
			}
		}
	}

	/*
	 * 印刷機能
	 * 特定健診結果通知
	 * import Print.*;
	 * import PrintData.*;
	 *
	 * 1ページ
	 * 表紙
	 * 必須データ：受診日、被保険者証等記号、被保険者証等番号
	 * import	Print.Kekka_P1
	 * class	Kekka_P1
	 *
	 * 2ページ
	 * 健診結果（詳細）
	 * 必須データ：受診日、被保険者証等記号、被保険者証等番号
	 * import	Print.Kekka_P2
	 * class	Kekka_P2
	 *
	 * 3ページ
	 * 健診結果（一覧）
	 * 必須データ：受診日、被保険者証等記号、被保険者証等番号
	 * import	Print.Kekka_P3
	 * class	Kekka_P3
	 *
	 *
	 * 4ページ
	 * 健診結果（問診）
	 * 必須データ：受診日、被保険者証等記号、被保険者証等番号
	 * import	Print.Kekka_P4
	 * class	Kekka_P4
	 *
	 */
	public void pushedPrintButton( ActionEvent e )
	{
		// eidt s.inoue 2011/03/30
		// 選択状態を保持する
		// ArrayList<Integer> selectedRows = table.getselectedRows(modelfixed,table);
		// ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JHanteiSearchResultListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y"))
//				selDat.add(i);
//		}

		// A4-1枚,2枚選択
		pageSelectDialog = DialogFactory.getInstance().createDialog(this);

		// 印刷方法選択ダイアログを表示
		pageSelectDialog.setMessageTitle("印刷方法選択画面");
		pageSelectDialog.setVisible(true);

		int pageSelect = (pageSelectDialog.getPrintSelect()==2) ?2:1;
		// cancelはbreak
		if(pageSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
			return;
		}

		/*
		 * 個人情報データ作成
		 */
		// eidt s.inoue 2011/03/30
		for(int i = 0;i < grid.getVOListTableModel().getRowCount();i++ )
		{
			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);

			if(vo.getCHECKBOX_COLUMN().equals("Y"))
			{
//				 Hashtable<String, String> searchResultItem = searchResult.get(i);
//				 String kensaNengapi = (String)table.getData(i, COLUMN_INDEX_KENSA_NENGAPI);

//				 String itemJyushinSeiriNo = searchResultItem.get("JYUSHIN_SEIRI_NO");
//				 String itemKanaName = searchResultItem.get("KANANAME");
//				 String itemHokenjaNumber = searchResultItem.get("HKNJANUM");
//				 String itemHihokenjyasyoKigou = searchResultItem.get("HIHOKENJYASYO_KIGOU");
//				 String itemHihokenjyasyoNumber = searchResultItem.get("HIHOKENJYASYO_NO");
//				 String itemUketukeId = searchResultItem.get("UKETUKE_ID");
				String itemJyushinSeiriNo = vo.getJYUSHIN_SEIRI_NO();
				String itemKanaName = vo.getKANANAME();
				String itemHokenjaNumber = vo.getHKNJANUM();
				String itemHihokenjyasyoKigou = vo.getHIHOKENJYASYO_KIGOU();
				String itemHihokenjyasyoNumber = vo.getHIHOKENJYASYO_NO();
				String itemUketukeId = vo.getUKETUKE_ID();

				/* 各値を検証する */
//				 validatedData.setJyushinkenID(itemJyushinSeiriNo);
//				 validatedData.setName(itemKanaName);
//				 validatedData.setHokenjyaNumber(itemHokenjaNumber);
//				 validatedData.setHihokenjyaCode(itemHihokenjyasyoKigou);
//				 validatedData.setHihokenjyaNumber(itemHihokenjyasyoNumber);
//				 validatedData.setUketukeId(itemUketukeId);
				 validatedData.setJYUSHIN_SEIRI_NO(itemJyushinSeiriNo);
				 validatedData.setNAME(itemKanaName);
				 validatedData.setHKNJANUM(itemHokenjaNumber);
				 validatedData.setHIHOKENJYASYO_NO(itemHihokenjyasyoKigou);
				 validatedData.setHIHOKENJYASYO_KIGOU(itemHihokenjyasyoKigou);
				 validatedData.setUKETUKE_ID(itemUketukeId);

				/* 検証用オブジェクトから各値を再取得する。 */
//				 String uketukeId = validatedData.getUketukeId();
//				 String hihokenjyaCode = validatedData.getHihokenjyaCode();
//				 String hihokenjyaNumber = validatedData.getHihokenjyaNumber();
//				 String hokenjyaNumber = validatedData.getHokenjyaNumber();
				 String uketukeId = validatedData.getUKETUKE_ID();
				 String hihokenjyaCode = validatedData.getHIHOKENJYASYO_KIGOU();
				 String hihokenjyaNumber = validatedData.getHIHOKENJYASYO_NO();
				 String hokenjyaNumber = validatedData.getHKNJANUM();

					/* 健診結果通知表を印刷する。 */
					PrintKekka kekka = new PrintKekka();
					kekka.printResultCard(
							// eidt s.inoue 2011/03/30
							// kensaNengapi,
							vo.getKENSA_NENGAPI(),
							uketukeId,
							hihokenjyaCode,
							hihokenjyaNumber,
							hokenjyaNumber,
							this,
							pageSelect);
			}
		}
		// add 20081224 s.inoueチェック状態を保持して処理後状態を戻す
//		resultRefresh();
//		table.setselectedRows(modelfixed,selectedRows);
		// eidt s.inoue 2011/05/09
//		setSelectedRow(selDat);
	}

	public void pushedOKButton( ActionEvent e ) {

		// eidt s.inoue 2011/03/30
//		int selectedRowCount = table.getSelectedRowCount();
		int selectedRowCount = 0;
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		JHanteiSearchResultListFrameData vo = null;
		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
			if (vo.getCHECKBOX_COLUMN().equals("Y")){
				selectedRows.add(i);
				selectedRowCount++;
			}
		}

//		if (selectedRowCount != 1) return;
		int selectedIndex = -1;

//		setSelectedRows();
		if (selectedRows.size() <= 0)
			return;

		selectedIndex = selectedRows.get(0).intValue();

		// eidt s.inoue 2011/03/30
//		List<TreeMap<String, String>> recordList = new ArrayList<TreeMap<String,String>>();
//		for (Hashtable<String, String> table : searchResult) {
//			TreeMap<String, String> recMap = new TreeMap<String, String>(table);
//			recordList.add(recMap);
//		}

		// eidt s.inoue 2011/05/09
		// curRecordを渡す
		WindowRefreshEvent win = new WindowRefreshEvent();
//		win.setselectedData(selectedIndex);

		JScene.CreateDialog(this, new JShowResultFrameCtrl(grid, selectedIndex), win);
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			// eidt s.inoue 2011/04/12
			// selectedData = getSelectedRow();

			// eidt s.inoue 2011/05/09
			// selectedData = getselectedData();
			// selectedData.add(0, getselectedData());

			// add s.inoue 2012/11/08
			JApplication.selectedHistoryRows = new ArrayList<Integer>();
			JHanteiSearchResultListFrameData chk = null;
			for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
				chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
				if (chk.getCHECKBOX_COLUMN().equals("Y"))
					JApplication.selectedHistoryRows.add(i);
			}

			// eidt s.inoue 2011/05/10
		   	int rowCount = ((currentPage -1)*JApplication.gridViewSearchCount) + currentRow + 1;
			currentTotalRows = rowCount;
			countText.setText(currentTotalRows + "件目");

			// eidt s.inoue 2012/11/21
			// grid.reloadData();
			reloadButton.doClick();

//			Thread reload = new ActionAutoReloadThread();
//			reload.start();
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
		dispose();
		// add s.inoue 2013/04/05
		if (JApplication.selectedHistoryRows.size() == 0)return;

		// add s.inoue 2012/11/08
		for (int i=0;i < grid.getVOListTableModel().getRowCount(); i++) {
			JApplication.selectedHistoryRows.remove(i);
		}
	}
	/* ボタンアクション */
	public void closeButtton_keyPerformed(KeyEvent e) {
		dispose();
	}

	// イベント処理
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

// del s.inoue 2012/11/09
//	// チェックボックス保持
//	private ArrayList<Integer> getSelectedRow(){
//		JHanteiSearchResultListFrameData vo = null;
//		for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
//			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
//			if (vo.getCHECKBOX_COLUMN().equals("Y")){
//				selectedData.add(i);
//				// System.out.println(selectedData.get(i) + "行目");
//			}
//		}
//		return selectedData;
//	}
//
//	// チェックボックス設定
//	private void setSelectedRow(ArrayList<Integer> selectedRows){
//		JHanteiSearchResultListFrameData vo = null;
//		for (int i = 0; i < selectedRows.size(); i++) {
//			vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(selectedRows.get(i));
//			grid.getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
////			System.out.println(selectedRows.get(i) + "行目");
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