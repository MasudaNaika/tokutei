package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai;

import java.awt.BorderLayout;
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
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorSiharaiResultFrameData;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.table.columns.client.Column;
import org.openswing.swing.table.columns.client.TextColumn;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JShiharaiMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = 2184629844979718100L;	// edit n.ohkubo 2014/10/01　追加
	
	protected Connection conn = null;
	protected GridControl grid = new GridControl();

	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
//	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
	protected ExtendedOpenGenericButton buttonAdd = null;
	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonExport = null;
	protected ExtendedOpenGenericButton buttonImport = null;

	/* control */
	protected TextColumn textColumn_DaikoNumber = new TextColumn();
	protected TextColumn textColumn_DaikoName = new TextColumn();
	protected TextColumn textColumn_Zipcode = new TextColumn();
	protected TextColumn textColumn_Address = new TextColumn();
	protected TextColumn textColumn_TEL = new TextColumn();

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	// add s.inoue 2010/03/05
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;
	private static Vector<Vector<String>> CSVItems = null;
	private IDialog filePathDialog;
	private JShiharaiMasterMaintenanceEditFrameData validatedData = new JShiharaiMasterMaintenanceEditFrameData();  //  @jve:decl-index=0:

	private static String saveTitle= "csvファイル保存先選択";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	private static final String[] TABLE_COLUMNS = {
		"SHIHARAI_DAIKO_NO", "SHIHARAI_DAIKO_NAME", "SHIHARAI_DAIKO_ZIPCD",
		"SHIHARAI_DAIKO_ADR", "SHIHARAI_DAIKO_TEL"
		 };

	private static Logger logger = Logger.getLogger(JShiharaiMasterMaintenanceListFrame.class);
	
	// edit n.ohkubo 2014/10/01　追加 start
	private ColumnContainer columnContainer;
	public ColumnContainer getColumnContainer() {
		return this.columnContainer;
	}
	// edit n.ohkubo 2014/10/01　追加 end

	/* コンストラクタ */
	public JShiharaiMasterMaintenanceListFrame(Connection conn,
			JShiharaiMasterMaintenanceListFrameCtrl controller) {
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
			JShiharaiMasterMaintenanceListFrameCtrl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);
			
			// edit n.ohkubo 2014/10/01　追加 start　検索画面のボタンの大きさを変更
			//フィルターパネル用のWindowListener
			SpecialFilterPanel specialFilterPanel = new SpecialFilterPanel(null, grid.getParent().getComponents());
			
			//このフレーム（一覧画面）がアクティブ化（画面右の検索ウィンドウ）　or　非アクティブ化（検索ウィンドウがポップアップで開かれ場合）されたときに動作するように、Listenerを設定
			this.addWindowListener(specialFilterPanel);
			// edit n.ohkubo 2014/10/01　追加 end　検索画面のボタンの大きさを変更

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 初期化処理 */
	public void jbInit() throws Exception {
		/* control ※ClientApplicationと一致*/
		textColumn_DaikoNumber.setColumnFilterable(true);
		textColumn_DaikoNumber.setColumnName("SHIHARAI_DAIKO_NO");
		textColumn_DaikoNumber.setColumnSortable(true);
		textColumn_DaikoNumber.setPreferredWidth(120);
//		textColumn_DaikoNumber.setEditableOnEdit(true);
//		textColumn_DaikoNumber.setEditableOnInsert(true);
		textColumn_DaikoNumber.setColumnRequired(false);
		textColumn_DaikoNumber.setMaxCharacters(8);// edit n.ohkubo 2014/10/01　追加

		textColumn_DaikoName.setColumnFilterable(true);
		textColumn_DaikoName.setColumnName("SHIHARAI_DAIKO_NAME");
		textColumn_DaikoName.setColumnSortable(true);
		textColumn_DaikoName.setEditableOnEdit(true);
		textColumn_DaikoName.setColumnRequired(false);
		textColumn_DaikoName.setPreferredWidth(200);
		textColumn_DaikoName.setMaxCharacters(100);// edit n.ohkubo 2014/10/01　追加

		textColumn_Zipcode.setColumnFilterable(true);
		textColumn_Zipcode.setColumnName("SHIHARAI_DAIKO_ZIPCD");
		textColumn_Zipcode.setColumnSortable(true);
		textColumn_Zipcode.setEditableOnEdit(true);
		textColumn_Zipcode.setColumnRequired(false);
		textColumn_Zipcode.setPreferredWidth(60);
		textColumn_Zipcode.setMaxCharacters(7);// edit n.ohkubo 2014/10/01　追加

		textColumn_Address.setColumnFilterable(true);
		textColumn_Address.setColumnName("SHIHARAI_DAIKO_ADR");
		textColumn_Address.setColumnSortable(true);
		textColumn_Address.setEditableOnEdit(true);
		textColumn_Address.setColumnRequired(false);
		textColumn_Address.setPreferredWidth(490);
		textColumn_Address.setMaxCharacters(100);// edit n.ohkubo 2014/10/01　追加

		textColumn_TEL.setColumnFilterable(true);
		textColumn_TEL.setColumnName("SHIHARAI_DAIKO_TEL");
		textColumn_TEL.setColumnSortable(true);
		textColumn_TEL.setEditableOnEdit(true);
		textColumn_TEL.setColumnRequired(false);
		textColumn_TEL.setPreferredWidth(80);
		textColumn_TEL.setMaxCharacters(11);// edit n.ohkubo 2014/10/01　追加

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
	    buttonOriginePanel.add(deleteButton, null);
	    buttonOriginePanel.add(exportButton, null);
//	    buttonOriginePanel.add(importButton, null);
	    buttonOriginePanel.add(navigatorBar, null);
	    buttonOriginePanel.add(Box.createVerticalStrut(2));

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonAdd, null);
	    buttonPanel.add(buttonImport, null);
	    buttonPanel.add(buttonExport, null);

		// action設定
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonAdd.addActionListener(new ListFrame_button_actionAdapter(this));
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
		grid.setInsertButton(insertButton);
		grid.setEditButton(editButton);
		grid.setReloadButton(reloadButton);
		grid.setDeleteButton(deleteButton);
		grid.setSaveButton(saveButton);
//		grid.setImportButton(importButton);
		grid.setExportButton(exportButton);
		grid.setFilterButton(filterButton);
		grid.setNavBar(navigatorBar);

		/* list */
		grid.setMaxSortedColumns(5);
		grid.setValueObjectClassName("jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceListData");
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_DaikoNumber, null);
		grid.getColumnContainer().add(textColumn_DaikoName, null);
		grid.getColumnContainer().add(textColumn_Zipcode, null);
		grid.getColumnContainer().add(textColumn_Address, null);
//		grid.getColumnContainer().add(textColumn_Banti,null);
		grid.getColumnContainer().add(textColumn_TEL, null);

		// edit n.ohkubo 2014/10/01　追加　start　openswingのバグ対応（putする数と"並び順"で指定した個数が一致するとIndexOutOfBoundsExceptionが発生し、検索画面が開かなくなる対応）
//		Column dummy = new Column();		// edit n.ohkubo 2015/03/01　削除　一覧をクリックしても反応しない対応
		Column dummy = new TextColumn();	// edit n.ohkubo 2015/03/01　追加　一覧をクリックしても反応しない対応
		dummy.setColumnName("DUMMY");
		dummy.setAutoFitColumn(false);
		dummy.setAutoscrolls(false);
		dummy.setColumnDuplicable(false);
		dummy.setColumnFilterable(false);
		dummy.setColumnRequired(false);
		dummy.setColumnSelectable(false);
		dummy.setColumnSortable(false);
		dummy.setColumnVisible(false);
		dummy.setDoubleBuffered(false);
		dummy.setEditableOnEdit(false);
		dummy.setEditableOnInsert(false);
		dummy.setEnabled(false);
		dummy.setFocusable(false);
		dummy.setFocusCycleRoot(false);
		dummy.setFocusTraversalKeysEnabled(false);
		dummy.setIgnoreRepaint(false);
		dummy.setInheritsPopupMenu(false);
		dummy.setOpaque(false);
		dummy.setRequestFocusEnabled(false);
		dummy.setVisible(false);
		grid.getColumnContainer().add(dummy, null);
		// edit n.ohkubo 2014/10/01　追加　end　openswingのバグ対応（putする数と"並び順"で指定した個数が一致するとIndexOutOfBoundsExceptionが発生し、検索画面が開かなくなる対応）
		
		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01　追加

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.shiharai-mastermaintenance.frame.title","tokutei.shiharai-mastermaintenance.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);

		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

		// add s.inoue 2013/03/01
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
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
		if (buttonClose == null) {
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
		}
		if (buttonAdd == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Add);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonAdd= new ExtendedOpenGenericButton(
					"Add","追加(A)","追加(ALT+A)",KeyEvent.VK_A,icon);
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

	/* ボタンアクション用内部クラス */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JShiharaiMasterMaintenanceListFrame adaptee;

		  ListFrame_button_actionAdapter(JShiharaiMasterMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
		public void actionPerformed(ActionEvent e) {
			  Object source = e.getSource();
			  if (source == buttonClose){
				  logger.info(buttonClose.getText());
				  adaptee.closeButtton_actionPerformed(e);
			  }else if(source == buttonAdd){
				  logger.info(buttonAdd.getText());
				  pushedAddButton(e);
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
			System.out.println("ok");
		}
		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("ok");
		}
		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("ok");
		}
	}

	// 未実装部分 edit s.inoue 2010/03/04
	/**ImportStart↓↓↓↓↓**********************************************************/
	/**
	 * 取込ボタン
	 */
	public void pushedImportButton( ActionEvent e )
	{
		try {
			filePathDialog = DialogFactory.getInstance().createDialog(this ,null);

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
			// 再描画処理
			// this.initializeTable();
		} catch (Exception ex) {
			JErrorMessage.show("M5105",this);
			logger.error(ex.getMessage());
		}
	}

	// edit s.inoue 2010/03/04
	/* 取込処理 */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M5107", this);

		// cancel時
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorSiharaiResultFrameData data = new JImportMasterErrorSiharaiResultFrameData();

			// CSV読込処理
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
			} catch (IOException e) {
				JErrorMessage.show("M5105",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M5111",this);
					return;
				}

				// 既存データall削除
				shiharaiMasterDelete();

				// 検査結果データ取込処理
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
					setCSVItemsToDB(data,insertRow);

					// validate処理
					if (!validateData(data))
						return;

					shiharaiMasterRegister(data);

				}

				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();
				reloadButton.doClick();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M5108",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M5105",this);
				logger.error(e.getMessage());
			}
		}
	}

	/* csvデータ取込 */
	private void setCSVItemsToDB(JImportMasterErrorSiharaiResultFrameData data,
			Vector<String> insertRow){

		data.CSV_COLUMN_SHIHARAI_DAIKO_NO = reader.rmQuart(insertRow.get(0));
		data.CSV_COLUMN_SHIHARAI_DAIKO_NAME = reader.rmQuart(insertRow.get(1));
		data.CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD = reader.rmQuart(insertRow.get(2));

		// eidt s.inoue 2012/11/15
		// 受診者住所 ～、ハイフンを長音へ変換
		String address = JValidate.encodeUNICODEtoConvert(insertRow.get(3));
		data.CSV_COLUMN_SHIHARAI_DAIKO_ADR = reader.rmQuart(address);
		// data.CSV_COLUMN_SHIHARAI_DAIKO_ADR = reader.rmQuart(insertRow.get(3));
		data.CSV_COLUMN_SHIHARAI_DAIKO_TEL = reader.rmQuart(insertRow.get(4));
	}

	// edit s.inoue 2010/03/04
	/* validate処理 */
	private boolean validateData(JImportMasterErrorSiharaiResultFrameData data) {

		boolean rettanka = false;
		// edit s.inoue 2010/04/27
		String address = data.CSV_COLUMN_SHIHARAI_DAIKO_ADR;
		if (!JValidate.isAllZenkaku(address)){
			address = JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(address);
		}
		address = JValidate.encodeUNICODEtoConvert(address);

		rettanka= validatedData.setDaikouNumber(data.CSV_COLUMN_SHIHARAI_DAIKO_NO)
			&& validatedData.setDaikouName(data.CSV_COLUMN_SHIHARAI_DAIKO_NAME)
			&& validatedData.setZipcode(data.CSV_COLUMN_SHIHARAI_DAIKO_ZIPCD)
			&& validatedData.setAddress(address)
			&& validatedData.setTEL(data.CSV_COLUMN_SHIHARAI_DAIKO_TEL);
		return rettanka;
	}

//	/*
//	 * 接続処理
//	 */
//	private void getconnectDao(){
//		try {
//			Connection connection = JApplication.kikanDatabase.getMConnection();
//		} catch (Exception e) {
//			logger.error("機関FDBへの接続処理に失敗しました。");
//			JErrorMessage.show("M3326",this);
//			return;
//		}
//	}

	/**
	 * 既存保険者削除
	 * @throws SQLException
	 */
	private void shiharaiMasterDelete(){
		try {
			String query = new String(
					"DELETE FROM T_SHIHARAI ");
			JApplication.kikanDatabase.sendNoResultQuery(query);
		} catch (SQLException e) {
			JErrorMessage.show("M5109",this);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void shiharaiMasterRegister(JImportMasterErrorSiharaiResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append(" INSERT INTO T_SHIHARAI ");
		buffer.append(" (SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD,");
		buffer.append("SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL ");
		buffer.append(" )VALUES( "
			+ JQueryConvert.queryConvertAppendComma(validatedData.getDaikouNumber())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getDaikouName())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getAddress())
			+ JQueryConvert.queryConvert(validatedData.getTEL())
			+ ")");

		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

		// add s.inoue 2012/10/24
		Domain dm = JApplication.clientSettings.getDomain(JApplication.shiharaiDomain.getDomainId());
		dm.addDomainPair(validatedData.getDaikouNumber(), validatedData.getDaikouNumber() + ":" +validatedData.getDaikouName());

	}
	/**ImportEnd↑↑↑↑↑**********************************************************/

	/**ExportStart↓↓↓↓↓**********************************************************/
	/**
	 * 書出ボタン
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("SiharaiMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this,defaltPath);
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
			JErrorMessage.show("M5109", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
//		JImportMasterErrorSiharaiResultFrameData data = new JImportMasterErrorSiharaiResultFrameData();	// edit n.ohkubo 2014/10/01　未使用なので削除

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M5110", this);
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
		sb.append(" SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME,");
		sb.append(" SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL");
		sb.append(" FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");

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
				// 空要素
				String exportItem =resultItem.get(TABLE_COLUMNS[j]).trim();

				data.add(exportItem);
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

	/**
	 * 追加ボタン
	 */
	public void pushedAddButton(ActionEvent e) {
		JScene.CreateDialog(this,
				new JShiharaiMasterMaintenanceEditFrameCtrl(),
				new WindowRefreshEvent());
	}
	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter
	{
		@Override
		public void windowClosed(WindowEvent e)
		{
			grid.setEnabled(true);
			// eidt s.inoue 2011/05/12
			// grid.reloadData();
			reloadButton.doClick();
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
}
