package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.BorderLayout;
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
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_KouteiMaster;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorTeikeiResultFrameData;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GenericButton;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JProgressListFrame extends JFrame implements KeyListener,ActionListener {

	/* 接続 */
	protected Connection conn = null;

	protected GridControl grid = new GridControl();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

//	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
//	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
//	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected GenericButton buttonExport = null;
	protected GenericButton buttonImport = null;
	protected GenericButton buttonClose = null;

	/* control */
//	protected ComboColumn textColumn_MessageLevel = new ComboColumn();
//	protected TextColumn textColumn_Message = new TextColumn();
	protected ComboColumn textColumn_hokenjaNo = new ComboColumn();
	protected TextColumn textColumn_jyushinSeiriNo = new TextColumn();
	protected TextColumn textColumn_birthdayFrom = new TextColumn();
	protected TextColumn textColumn_birthdayTo = new TextColumn();
	protected ComboColumn textColumn_sex = new ComboColumn();
	protected TextColumn textColumn_HokensyoCode = new TextColumn();
	protected TextColumn textColumn_HokensyoNumber = new TextColumn();
	protected TextColumn dateColumn_KenshinDate = new TextColumn();
	protected TextColumn dateColumn_KenshinDateTo = new TextColumn();

	protected TextColumn textColumn_kanaName = new TextColumn();
	protected ComboColumn textColumn_inputFlg = new ComboColumn();
	protected TextColumn textColumn_hanteiNengapi = new TextColumn();
	protected ComboColumn textColumn_nitijiFlg = new ComboColumn();
	protected ComboColumn textColumn_getujiFlg = new ComboColumn();

	private JProgressListFrameData validatedData = new JProgressListFrameData();
	private IDialog filePathDialog;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

	private static Vector<Vector<String>> CSVItems = null;
	private static String saveTitle= "csvファイル保存先選択";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	// 検索ボタン押下時のSQLで使用
	private static final String[] TABLE_COLUMNS = {
		"SYOKEN_TYPE","SYOKEN_TYPE_NAME","SYOKEN_NO","SYOKEN_NAME","UPDATE_TIMESTAMP" };

	private static Logger logger = Logger.getLogger(JProgressListFrame.class);

	/* コンストラクタ */
	public JProgressListFrame(
			JProgressListFrameCtl controller) {
		setControl(controller);
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
	public void setControl(
			JProgressListFrameCtl controller){
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

		textColumn_hokenjaNo.setColumnFilterable(true);
		textColumn_hokenjaNo.setColumnName("HKNJANUM");
		textColumn_hokenjaNo.setColumnSortable(true);
		textColumn_hokenjaNo.setPreferredWidth(140);
		textColumn_hokenjaNo.setDomainId("T_HOKENJYA");
		textColumn_hokenjaNo.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HKNJANUM));
		
		textColumn_jyushinSeiriNo.setColumnFilterable(true);
		textColumn_jyushinSeiriNo.setColumnName("JYUSHIN_SEIRI_NO");
		textColumn_jyushinSeiriNo.setColumnSortable(true);
		textColumn_jyushinSeiriNo.setPreferredWidth(95);
		textColumn_jyushinSeiriNo.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO));
		
		textColumn_birthdayFrom.setColumnFilterable(true);
		textColumn_birthdayFrom.setColumnName("BIRTHDAY");
		textColumn_birthdayFrom.setColumnSortable(true);
		textColumn_birthdayFrom.setPreferredWidth(65);
		
		textColumn_birthdayFrom.setColumnVisible(false);
		textColumn_birthdayFrom.setColumnFilterable(true);
		textColumn_birthdayFrom.setColumnSortable(false);
		textColumn_birthdayFrom.setColumnSelectable(false);
		
		textColumn_birthdayTo.setColumnName("BIRTHDAY");
		textColumn_birthdayTo.setPreferredWidth(65);

		textColumn_birthdayTo.setColumnFilterable(true);
		textColumn_birthdayTo.setColumnSortable(true);
		textColumn_birthdayTo.setColumnSelectable(true);
		textColumn_birthdayTo.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.BIRTHDAY));
		
		textColumn_sex.setColumnFilterable(true);
		textColumn_sex.setColumnName("SEX");
		textColumn_sex.setColumnSortable(true);
		textColumn_sex.setPreferredWidth(40);
	    textColumn_sex.setEditableOnEdit(true);
	    textColumn_sex.setEditableOnInsert(true);
	    textColumn_sex.setColumnRequired(false);
	    textColumn_sex.setDomainId("SEX");
	    textColumn_sex.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.SEX));

		textColumn_HokensyoCode.setColumnFilterable(true);
		textColumn_HokensyoCode.setColumnName("HIHOKENJYASYO_KIGOU");
		textColumn_HokensyoCode.setColumnSortable(true);
		textColumn_HokensyoCode.setPreferredWidth(110);
		textColumn_HokensyoCode.setColumnFilterable(false);
		textColumn_HokensyoCode.setColumnSortable(false);
		textColumn_HokensyoCode.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU));

		textColumn_HokensyoNumber.setColumnFilterable(true);
		textColumn_HokensyoNumber.setColumnName("HIHOKENJYASYO_NO");
		textColumn_HokensyoNumber.setColumnSortable(true);
		textColumn_HokensyoNumber.setPreferredWidth(110);
		textColumn_HokensyoNumber.setColumnFilterable(false);
		textColumn_HokensyoNumber.setColumnSortable(false);
		textColumn_HokensyoNumber.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO));
		
		dateColumn_KenshinDate.setColumnFilterable(true);
		dateColumn_KenshinDate.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDate.setColumnSortable(true);
		dateColumn_KenshinDate.setPreferredWidth(65);

		dateColumn_KenshinDate.setColumnFilterable(true);
		dateColumn_KenshinDate.setColumnSortable(false);
		dateColumn_KenshinDate.setColumnSelectable(false);
		dateColumn_KenshinDate.setColumnVisible(false);
		
		dateColumn_KenshinDateTo.setColumnFilterable(true);
		dateColumn_KenshinDateTo.setColumnName("KENSA_NENGAPI");
		dateColumn_KenshinDateTo.setColumnSortable(true);
		dateColumn_KenshinDateTo.setColumnSelectable(true);
		dateColumn_KenshinDateTo.setPreferredWidth(70);
		dateColumn_KenshinDateTo.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KENSA_NENGAPI));
		
		textColumn_kanaName.setColumnFilterable(true);
		textColumn_kanaName.setColumnName("KANANAME");
		textColumn_kanaName.setColumnSortable(true);
		textColumn_kanaName.setPreferredWidth(175);
		textColumn_kanaName.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KANANAME));

		textColumn_inputFlg.setColumnFilterable(true);
		textColumn_inputFlg.setColumnName("KEKA_FLG");
		textColumn_inputFlg.setColumnSortable(true);
		
		textColumn_inputFlg.setPreferredWidth(40);
	    textColumn_inputFlg.setEditableOnEdit(true);
	    textColumn_inputFlg.setEditableOnInsert(true);
	    textColumn_inputFlg.setColumnRequired(false);
	    textColumn_inputFlg.setDomainId("KEKA_FLG");
	    textColumn_inputFlg.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KEKA_FLG));
	    
		textColumn_hanteiNengapi.setColumnFilterable(true);
		textColumn_hanteiNengapi.setColumnName("HANTEI_FLG");
		textColumn_hanteiNengapi.setColumnSortable(true);
		textColumn_hanteiNengapi.setPreferredWidth(35);
		textColumn_hanteiNengapi.setColumnFilterable(false);
		textColumn_hanteiNengapi.setColumnSortable(false);
		textColumn_hanteiNengapi.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HANTEI_FLG));

		textColumn_nitijiFlg.setColumnFilterable(true);
		textColumn_nitijiFlg.setColumnName("NITIJI_FLG");
		textColumn_nitijiFlg.setColumnSortable(true);
		textColumn_nitijiFlg.setPreferredWidth(40);
		textColumn_nitijiFlg.setDomainId("NITIJI_FLG");
		textColumn_nitijiFlg.setColumnVisible(true);
		textColumn_nitijiFlg.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.NITIJI_FLG));
		
		textColumn_getujiFlg.setColumnFilterable(true);
		textColumn_getujiFlg.setColumnName("GETUJI_FLG");
		textColumn_getujiFlg.setColumnSortable(true);
		textColumn_getujiFlg.setPreferredWidth(40);
		textColumn_getujiFlg.setDomainId("GETUJI_FLG");
		textColumn_getujiFlg.setColumnVisible(!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.GETUJI_FLG));
		
		/* button */
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
//		buttonOriginePanel.add(insertButton, null);
//		buttonOriginePanel.add(editButton, null);
//		buttonOriginePanel.add(filterButton, null);
		buttonOriginePanel.add(reloadButton, null);
//		buttonOriginePanel.add(deleteButton, null);
//		buttonOriginePanel.add(exportButton, null);
//		buttonOriginePanel.add(importButton, null);
		buttonOriginePanel.add(navigatorBar, null);
//		buttonOriginePanel.add(saveButton, null);

		// button生成
		setJButtons();

	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
//	    buttonPanel.add(buttonExport,null);
//	    buttonPanel.add(buttonImport,null);

		// action設定
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonExport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonImport.addActionListener(new ListFrame_button_actionAdapter(this));

		Box origineBox = new Box(BoxLayout.X_AXIS);
		origineBox.add(buttonOriginePanel);
		origineBox.add(Box.createVerticalStrut(2));

		// box2行目
		Box recordBox = new Box(BoxLayout.X_AXIS);
		recordBox.add(buttonPanel);

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
//		grid.setDeleteButton(deleteButton);
//		grid.setEditButton(editButton);
//		grid.setExportButton(exportButton);
//		grid.setImportButton(importButton);
		grid.setFilterButton(filterButton);
//		grid.setSaveButton(saveButton);

//		grid.setInsertButton(insertButton);
		// add s.inoue 2012/10/25 項目絞る
		grid.setMaxSortedColumns(2);

		grid.setNavBar(navigatorBar);
		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName("jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JProgressListFrameData");
		grid.setOrderWithLoadData(false);

//		grid.getColumnContainer().add(textColumn_MessageLevel, null);
//		grid.getColumnContainer().add(textColumn_Message, null);
		
		// add s.inoue 2014/02/17
		grid.getColumnContainer().add(textColumn_hokenjaNo, null);
		grid.getColumnContainer().add(textColumn_jyushinSeiriNo, null);
		grid.getColumnContainer().add(textColumn_kanaName, null);
		grid.getColumnContainer().add(textColumn_birthdayFrom, null);
		grid.getColumnContainer().add(textColumn_birthdayTo, null);
		grid.getColumnContainer().add(textColumn_sex, null);
		grid.getColumnContainer().add(textColumn_HokensyoCode, null);
		grid.getColumnContainer().add(textColumn_HokensyoNumber, null);
		grid.getColumnContainer().add(dateColumn_KenshinDate, null);
		grid.getColumnContainer().add(dateColumn_KenshinDateTo, null);
		
		grid.getColumnContainer().add(textColumn_inputFlg, null);
		grid.getColumnContainer().add(textColumn_hanteiNengapi, null);
		grid.getColumnContainer().add(textColumn_nitijiFlg, null);
		grid.getColumnContainer().add(textColumn_getujiFlg, null);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.teikei-mastermaintenance.frame.title","tokutei.teikei-mastermaintenance.frame.guidance");
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

	/* ボタン群 */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {
		if (buttonClose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			buttonClose.addActionListener(this);
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
//			importCsvData(filePath);
			// del s.inoue 2011/04/07
			// initTable();
		} catch (Exception ex) {
			JErrorMessage.show("M9912",this);
			logger.error(ex.getMessage());
		}
	}

//	// edit s.inoue 2010/03/04
//	/* 取込処理 */
//	private void importCsvData(String filePath){
//
//		RETURN_VALUE retValue = JErrorMessage.show("M9916", this);
//
//		// cancel時
//		if (retValue == RETURN_VALUE.NO){
//			return;
//		}else if (retValue == RETURN_VALUE.YES){
//
//			JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();
//
//			// CSV読込処理
//			reader = new JCSVReaderStream();
//
//			try {
//				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
//			} catch (IOException e) {
//				JErrorMessage.show("M9913",this);
//				logger.error(e.getMessage());
//			}
//
//			CSVItems = reader.readAllTable();
//
//			try {
//				// eidt s.inoue 2011/06/07
//				// JApplication.kikanDatabase.Transaction();
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//
//				int csvCount = CSVItems.size();
//
//				if(csvCount == 0){
//					JErrorMessage.show("M9914",this);
//					return;
//				}
//
//				// 定型文マスタ全削除
//				teikeiMasterDelete();
//
//				// データ取込処理
//				for (int i = 1; i < csvCount; i++) {
//
//					Vector<String> insertRow = new Vector<String>();
//
//					insertRow =CSVItems.get(i);
//
//					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
////					setCSVItemsToDB(data,insertRow);
//
////					// validate処理
////					if (!validateData(data))
////						return;
//
////					// 定型文登録
////					teikeiMasterRegister(data);
//				}
//
//				// eidt s.inoue 2011/06/07
//				// JApplication.kikanDatabase.Commit();
//				JApplication.kikanDatabase.getMConnection().commit();
//				reloadButton.doClick();
//
//				String[] messageParams = {String.valueOf(csvCount-1)};
//				JErrorMessage.show("M9915",this,messageParams);
//			} catch (SQLException e) {
//				try {
//					// eidt s.inoue 2011/06/07
//					// JApplication.kikanDatabase.rollback();
//					JApplication.kikanDatabase.getMConnection().rollback();
//				} catch (SQLException e1) {}
//				JErrorMessage.show("M9913",this);
//				logger.error(e.getMessage());
//			}
//		}
//	}

//	// edit s.inoue 2010/03/04
//	/* csvデータ取込 */
//	private void setCSVItemsToDB(JImportMasterErrorTeikeiResultFrameData data,
//			Vector<String> insertRow){
//
//		data.CSV_COLUMN_SYOKEN_TYPE = reader.rmQuart(insertRow.get(0));
//		data.CSV_COLUMN_SYOKEN_TYPE_NAME = reader.rmQuart(insertRow.get(1));
//		data.CSV_COLUMN_SYOKEN_NO = reader.rmQuart(insertRow.get(2));
//		data.CSV_COLUMN_SYOKEN_NAME = reader.rmQuart(insertRow.get(3));
//	}

//	// edit s.inoue 2010/03/04
//	/* validate処理 */
//	private boolean validateData(JImportMasterErrorTeikeiResultFrameData data) {
//
//		boolean rettanka = false;
//		// edit s.inoue 2010/05/19
//		rettanka= validatedData.setTeikeiType(data.CSV_COLUMN_SYOKEN_TYPE)
//			&& validatedData.setTeikeiTypeName(data.CSV_COLUMN_SYOKEN_TYPE_NAME)
//			&& validatedData.setTeikeiNumber(data.CSV_COLUMN_SYOKEN_NO)
//			&& validatedData.setTeikeibun(data.CSV_COLUMN_SYOKEN_NAME);
//		return rettanka;
//	}

//	/**
//	 * 既存定型文マスタ削除
//	 * @throws SQLException
//	 */
//	private void teikeiMasterDelete(){
//		try {
//
//			String query = new String("DELETE FROM T_SYOKENMASTER ");
//			JApplication.kikanDatabase.sendNoResultQuery(query);
//		} catch (SQLException e) {
//			JErrorMessage.show("M9908",this);
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//	}

//	/**
//	 * CSVデータ登録
//	 * @throws SQLException
//	 */
//	private void teikeiMasterRegister(JImportMasterErrorTeikeiResultFrameData data)
//		throws SQLException
//	{
//		StringBuffer buffer = new StringBuffer();
//
//		// timestamp取得
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());
//
//		buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP)");
//		buffer.append("VALUES (");
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType()));
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiTypeName()));
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber()));
//		buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun()));
//		buffer.append(JQueryConvert.queryConvert(stringTimeStamp));
//		buffer.append(")");
//
//		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//
//	}
	/**ImportEnd↑↑↑↑↑**********************************************************/

	/**ExportStart↓↓↓↓↓**********************************************************/
	// add s.inoue 2010/02/25
	/**
	 * 書出ボタン
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("SyokenMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
			filePathDialog.setMessageTitle(saveTitle);
			filePathDialog.setDialogTitle(savaDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
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
			JErrorMessage.show("M9909", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
		JImportMasterErrorTeikeiResultFrameData data = new JImportMasterErrorTeikeiResultFrameData();

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M9909", this);
			logger.error(e.getMessage());
		}
	}

	/* DBよりデータ取得*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

		// add s.inoue 2010/03/04
		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP ");
		sb.append(" FROM T_SYOKENMASTER ");
		sb.append(" ORDER BY SYOKEN_TYPE,SYOKEN_NO ");

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


	/* ボタンアクション用内部クラス */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JProgressListFrame adaptee;

		  ListFrame_button_actionAdapter(JProgressListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  public void actionPerformed(ActionEvent e) {
			  Object source = e.getSource();
			  if (source == buttonClose){
				  logger.info(buttonClose.getText());
				  adaptee.closeButtton_actionPerformed(e);
				  // add s.inoue 2014/02/17
				  preservColumnStatus();
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

	// add s.inoue 2014/02/17
	private void preservColumnStatus(){
		if (textColumn_hokenjaNo.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.HKNJANUM));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HKNJANUM))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HKNJANUM));
		}

		if (textColumn_jyushinSeiriNo.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.JYUSHIN_SEIRI_NO));
		}

		if (textColumn_birthdayTo.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.BIRTHDAY));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.BIRTHDAY))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.BIRTHDAY));
		}

		if (textColumn_sex.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.SEX));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.SEX))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.SEX));
		}

		if (textColumn_HokensyoCode.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_KIGOU));
		}

		if (textColumn_HokensyoNumber.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HIHOKENJYASYO_NO));
		}

		if (dateColumn_KenshinDateTo.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.KENSA_NENGAPI));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KENSA_NENGAPI))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KENSA_NENGAPI));
		}

		if (textColumn_kanaName.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.KANANAME));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KANANAME))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KANANAME));
		}

		if (textColumn_inputFlg.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.KEKA_FLG));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.KEKA_FLG))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.KEKA_FLG));
		}

		if (textColumn_hanteiNengapi.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.HANTEI_FLG));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.HANTEI_FLG))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.HANTEI_FLG));
		}

		if (textColumn_nitijiFlg.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.NITIJI_FLG));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.NITIJI_FLG))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.NITIJI_FLG));
		}

		if (textColumn_getujiFlg.isVisible()){
			JApplication.flag_KouteiMaster.removeAll(EnumSet.of(FlagEnum_KouteiMaster.GETUJI_FLG));
		}else{
			if (!JApplication.flag_KouteiMaster.contains(FlagEnum_KouteiMaster.GETUJI_FLG))
				JApplication.flag_KouteiMaster.addAll(EnumSet.of(FlagEnum_KouteiMaster.GETUJI_FLG));
		}
		
	}	
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
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
