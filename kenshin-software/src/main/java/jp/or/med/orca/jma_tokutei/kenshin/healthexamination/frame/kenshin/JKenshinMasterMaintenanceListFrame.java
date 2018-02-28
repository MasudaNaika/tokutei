package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.openswing.swing.client.*;
import org.openswing.swing.domains.java.Domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import org.openswing.swing.table.columns.client.*;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.send.java.GridParams;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenDeleteButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenImportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenReloadButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorKenshinResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrame;

import org.openswing.swing.util.client.ClientUtils;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	private Connection conn = null;

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
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

			grid.addKeyListener(new KeyAdapter() {
			      public void keyPressed(KeyEvent e) {
			        if (e.getKeyCode()==e.VK_CANCEL || e.getKeyCode()==e.VK_BACK_SPACE || e.getKeyCode()==e.VK_DELETE)
			          System.out.println("成功");
			      }
			    });

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

		// 項目コード(*)
		textColumn_KoumokuCd.setColumnFilterable(true);
		textColumn_KoumokuCd.setColumnName("KOUMOKU_CD");
		textColumn_KoumokuCd.setColumnSortable(true);
		textColumn_KoumokuCd.setPreferredWidth(150);
		textColumn_KoumokuCd.setColumnRequired(true);

		// eidt s.inoue 2011/06/02
		// 項目名
		textColumn_KoumokuNm.setColumnFilterable(true);
		textColumn_KoumokuNm.setColumnName("KOUMOKU_NAME");
		textColumn_KoumokuNm.setColumnSortable(true);
		textColumn_KoumokuNm.setPreferredWidth(200);
		textColumn_KoumokuNm.setColumnRequired(false);

		// 検査方法
		textColumn_KensaHouhou.setColumnFilterable(true);
		textColumn_KensaHouhou.setColumnName("KENSA_HOUHOU");
		textColumn_KensaHouhou.setColumnSortable(true);
		textColumn_KensaHouhou.setPreferredWidth(180);
		textColumn_KensaHouhou.setColumnRequired(false);

		// 必須フラグ(編集可)
		textColumn_HisuFlg.setColumnFilterable(true);
		textColumn_HisuFlg.setColumnName("HISU_FLG");
		textColumn_HisuFlg.setColumnSortable(true);
		textColumn_HisuFlg.setPreferredWidth(70);
	    textColumn_HisuFlg.setEditableOnEdit(true);
	    textColumn_HisuFlg.setEditableOnInsert(true);
	    textColumn_HisuFlg.setColumnRequired(false);

	    textColumn_HisuFlg.setDomainId("HISU_FLG");

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

		// action設定
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonExport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonImport.addActionListener(new ListFrame_button_actionAdapter(this));

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
		  public void actionPerformed(ActionEvent e) {
			  Object source = e.getSource();
			  if (source == buttonClose){
				  logger.info(buttonClose.getText());
				  adaptee.closeButtton_actionPerformed(e);
			  }else if(source == buttonExport){
				  logger.info(buttonExport.getText());
				  pushedExportButton(e);
			  }else if(source == buttonImport){
				  logger.info(buttonImport.getText());
				  pushedImportButton(e);
			  }
		  }
		@Override
		public void keyPressed(KeyEvent e) {
			adaptee.closeButtton_keyPerformed(e);

		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ

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
	public void actionPerformed(ActionEvent ae) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

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
//			// TODO 自動生成されたメソッド・スタブ
//		}
//		public void keyTyped(KeyEvent e) {
//			// TODO 自動生成されたメソッド・スタブ
//
//		}
//	}
//	/* ボタンアクション */
//	public void closeButtton_keyPerformed(KeyEvent e) {
//		dispose();
//	}
}
