package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

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
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorKenshinPatternResultFrameData;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.table.columns.client.TextColumn;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinpatternMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = -6351587079483065671L;	// edit n.ohkubo 2014/10/01　追加
	
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
//	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
//	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonExport = null;
	protected ExtendedOpenGenericButton buttonImport = null;

	protected ExtendedOpenGenericButton buttonDeplicate = null;

	/* control */
	protected TextColumn textColumn_kpNo = new TextColumn();
	protected TextColumn textColumn_kpName = new TextColumn();
	protected TextColumn textColumn_Bikou = new TextColumn();

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();

	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	protected static final String CONST_DATA_STRING
		= "jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListData";

	private static org.apache.log4j.Logger logger = Logger.getLogger(JKenshinpatternMasterMaintenanceListFrame.class);
	private IDialog addPatternDialog;

	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;
	private static Vector<Vector<String>> CSVItems = null;
	private IDialog filePathDialog;
	private JKenshinpatternMasterMaintenanceListData validatedData = new JKenshinpatternMasterMaintenanceListData();

	private static String saveTitle= "csvファイル保存先選択";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	private static final String[] TABLE_COLUMNS = {
		"K_P_NO", "K_P_NAME", "BIKOU"
		 };
	private static final String[] TABLE_DETAIL_COLUMNS = {
		"LOW_ID", "KOUMOKU_CD"
	};

	/* コンストラクタ */
	public JKenshinpatternMasterMaintenanceListFrame(Connection conn,
			JKenshinpatternMasterMaintenanceListFrameCtrl controller) {
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
			JKenshinpatternMasterMaintenanceListFrameCtrl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			
			// edit n.ohkubo 2014/10/01　追加
			//検索機能を使用不可にする
			grid.setShowFilterPanelOnGrid(false);
			
			setVisible(true);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/* 初期化処理 */
	/**
	 * @throws Exception
	 */
	public void jbInit() throws Exception {
		/* control ※ClientApplicationと一致*/
		textColumn_kpNo.setColumnFilterable(true);
		textColumn_kpNo.setColumnName("K_P_NO");
		textColumn_kpNo.setColumnSortable(true);
		textColumn_kpNo.setEditableOnInsert(true);
		textColumn_kpNo.setPreferredWidth(120);

		textColumn_kpName.setColumnFilterable(true);
		textColumn_kpName.setColumnName("K_P_NAME");
		textColumn_kpName.setColumnSortable(true);
		textColumn_kpName.setEditableOnEdit(true);
		textColumn_kpName.setEditableOnInsert(true);
		textColumn_kpName.setPreferredWidth(300);

		textColumn_Bikou.setColumnFilterable(false);
		textColumn_Bikou.setColumnName("BIKOU");
		textColumn_Bikou.setColumnSortable(false);
		textColumn_Bikou.setEditableOnEdit(true);
		textColumn_Bikou.setEditableOnInsert(true);
		textColumn_Bikou.setPreferredWidth(950);
		textColumn_Bikou.setColumnRequired(false);

		/* button */
		buttonOriginePanel.add(insertButton, null);
		buttonOriginePanel.add(editButton, null);
		buttonOriginePanel.add(saveButton, null);
		buttonOriginePanel.add(reloadButton, null);
		buttonOriginePanel.add(deleteButton, null);
//		buttonOriginePanel.add(exportButton, null);
		buttonOriginePanel.add(navigatorBar, null);

		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);

		// button生成
		setJButtons();

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonImport,null);
	    buttonPanel.add(buttonExport,null);
	    buttonPanel.add(buttonDeplicate, null);

		// action設定
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonExport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonImport.addActionListener(new ListFrame_button_actionAdapter(this));

		buttonDeplicate.addActionListener(new ListFrame_button_actionAdapter(this));
//		buttonKekkaInputCall.addActionListener(new ListFrame_closeButton_actionAdapter(this));

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
		grid.setDeleteButton(deleteButton);
		grid.setEditButton(editButton);
		grid.setSaveButton(saveButton);
//		grid.setExportButton(exportButton);

		grid.setInsertButton(insertButton);
		// add s.inoue 2012/10/25 項目絞る
		grid.setMaxSortedColumns(2);

		grid.setNavBar(navigatorBar);
		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName(CONST_DATA_STRING);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_kpNo, null);
		grid.getColumnContainer().add(textColumn_kpName, null);
		grid.getColumnContainer().add(textColumn_Bikou, null);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.kenshinpattern-mastermaintenance.frame.title","tokutei.kenshinpattern-mastermaintenance.frame.guidance");
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

	/* ダイアログ & 複製 */
	private void deplicatePattern(){

		try {
			int curIdx = grid.getSelectedRow();

			JKenshinpatternMasterMaintenanceListData vo =
				(JKenshinpatternMasterMaintenanceListData)grid.getVOListTableModel().getObjectForRow(curIdx);
			curIdx = Integer.parseInt(vo.getK_P_NO());
			int newKP_No = getNextPatternNumber();
			addPatternDialog = DialogFactory.getInstance().createDialog(this, new JButton(),curIdx,newKP_No);

			addPatternDialog.setShowCancelButton(false);
			// 健診実施日入力ダイアログを表示
			addPatternDialog.setMessageTitle("健診パターン複製");
			addPatternDialog.setVisible(true);

			if (addPatternDialog.getStatus() == RETURN_VALUE.YES)
				pushedOKButton(
						addPatternDialog.getK_PName(),
						String.valueOf(newKP_No),
						curIdx,
						addPatternDialog.getK_PNote()
						);

			// String kenshinDate = addPatternDialog.getKenshinDate();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}


	/**
	 * 健診パターンの空き番号の取得
	 */
	private int getNextPatternNumber() {
		ArrayList<Hashtable<String, String>> Items;
		try {
			Items = JApplication.kikanDatabase
					.sendExecuteQuery("SELECT K_P_NO FROM T_KENSHIN_P_M");
		} catch (SQLException e) {
			JErrorMessage.show("M3920", this);
			return -1;
		}
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			boolean FindFlag = false;

			for (int j = 0; j < Items.size(); j++) {
				if (Items.get(j).get("K_P_NO").equals(String.valueOf(i))) {
					FindFlag = true;
				}
			}
			// trueでなければ空きがあるとする。
			if (FindFlag == false)
				return i;
		}

		return -1;
	}
	/* ボタンアクション用内部クラス */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JKenshinpatternMasterMaintenanceListFrame adaptee;

		  ListFrame_button_actionAdapter(JKenshinpatternMasterMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
		public void actionPerformed(ActionEvent e) {
		    adaptee.closeButtton_actionPerformed(e);

		    Object source = e.getSource();
			if (source == buttonClose){
				logger.info(buttonClose.getText());
				dispose();
			}else if (source == buttonDeplicate){
				logger.info(buttonDeplicate.getText());
				deplicatePattern();
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
		if (buttonClose == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
		}

		if (buttonDeplicate == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Deplicate);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDeplicate= new ExtendedOpenGenericButton(
					"Deplicate","複製(D)","戻る(ALT+D)",KeyEvent.VK_D,icon);
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

	/**
	 * OKボタン
	 */
	public void pushedOKButton(String pt_nm,String pt_no,int pre_pt_no,String note) {

		validatedData.setK_P_NAME(pt_nm);
		validatedData.setK_P_NO(pt_no);
		validatedData.setBIKOU(note);

//		if (validatedData.setSourcePatternNumber(((String) jComboBox_Target.getSelectedItem()).substring(0, 3))
//				&& validatedData.setNewPatternName(jTextField_Name.getText())
//				&& validatedData.setPatternNumber(String.valueOf(patternNumber))
//				&& validatedData.setNote(jTextField_Note.getText())) {
			if (validateAsOKPushed(validatedData)) {
				if (validatedData.isValidateAsDataSet()) {
					try {

						// add s.inoue 20081217
						// 健診パターン名称の重複チェック
						try
						{
							ArrayList kikanItem =  JApplication.kikanDatabase.sendExecuteQuery(
									"SELECT * FROM T_KENSHIN_P_M WHERE K_P_NAME =" +
									JQueryConvert.queryConvert(validatedData.getK_P_NAME()));

							if (kikanItem.size() > 0){
								JErrorMessage.show("M9637", this);
								return;
							}

						}catch(Exception err){
							err.printStackTrace();
							JErrorMessage.show("M9601", this);
							return;
						}

						JApplication.kikanDatabase.Transaction();

						// 健診パターンマスタに新規パターンを登録する
						String Query = new String(
								"INSERT INTO T_KENSHIN_P_M (K_P_NO,K_P_NAME,BIKOU) VALUES ("
										+ JQueryConvert.queryConvertAppendComma(validatedData.getK_P_NO())
										+ JQueryConvert.queryConvertAppendComma(validatedData.getK_P_NAME())
										+ JQueryConvert.queryConvert(validatedData.getBIKOU()) + ")");

						JApplication.kikanDatabase.sendNoResultQuery(Query);

						ArrayList<Hashtable<String, String>> Result = new ArrayList<Hashtable<String, String>>();
						Hashtable<String, String> ResultItem = new Hashtable<String, String>();

						// 健診パターン詳細から複製元のパターンNoが付いているものだけを抽出する
						Query = new String(
								"SELECT * FROM T_KENSHIN_P_S WHERE K_P_NO = "
								+ JQueryConvert.queryConvert(String.valueOf(pre_pt_no)));

						Result = JApplication.kikanDatabase.sendExecuteQuery(Query);

						// edit n.ohkubo 2014/10/01　未使用なので削除　start
//						// 健診パターンNoの最大値を取得しインクリメント後新規パターンの登録に使う
//						ArrayList<Hashtable<String, String>> maxNo = new ArrayList<Hashtable<String, String>>();
//						Hashtable<String, String> maxNoItem = new Hashtable<String, String>();
//						Query = new String(
//								"SELECT MAX(K_P_NO) FROM T_KENSHIN_P_S");
//
//						maxNo = JApplication.kikanDatabase
//								.sendExecuteQuery(Query);
//						maxNoItem = maxNo.get(0);
						// edit n.ohkubo 2014/10/01　未使用なので削除　end

						// eidt s.inoue 2011/04/08
						// int max = this.patternNumber;
						int max = Integer.parseInt(validatedData.getK_P_NO());

						// 複製元の健診パターン詳細の行の健診パターンNoを新規パターンNoに書き換え挿入を行う
						for (int i = 0; i < Result.size(); i++) {
							ResultItem = Result.get(i);
							Query = new String(
									"INSERT INTO T_KENSHIN_P_S (K_P_NO,KOUMOKU_CD,LOW_ID) VALUES ("
											+ Integer.toString(max)
											+ ","
											+ JQueryConvert.queryConvertAppendComma(ResultItem.get("KOUMOKU_CD"))
											+ JQueryConvert.queryConvert(ResultItem.get("LOW_ID"))
											+ ")");
							JApplication.kikanDatabase.sendNoResultQuery(Query);
						}

						JApplication.kikanDatabase.Commit();

						// add s.inoue 2013/02/12
						Domain dm = JApplication.clientSettings.getDomain(JApplication.patternDomain.getDomainId());
						dm.addDomainPair(validatedData.getK_P_NO(), validatedData.getK_P_NAME());
						JApplication.domains.put(
									JApplication.patternDomain.getDomainId(),JApplication.patternDomain);

						// eidt s.inoue 2011/04/08
//						dispose();
						reloadButton.doClick();

					} catch (SQLException f) {
						logger.error(f.getMessage());
						JErrorMessage.show("M3921", this);
						try {
							JApplication.kikanDatabase.rollback();
						} catch (SQLException g) {
							logger.error(f.getMessage());
							JErrorMessage.show("M0000", this);
							System.exit(1);
						}
						return;
					}

				}
			}
//		}

	}

	// OKボタンを押した際の必須項目を検証する
	protected boolean validateAsOKPushed(
			JKenshinpatternMasterMaintenanceListData data) {
		if (validatedData.getK_P_NO().equals("")) {
			JErrorMessage.show("M3901", this);
			return false;
		}
		if (validatedData.getK_P_NAME().equals("")) {
			JErrorMessage.show("M3902", this);
			return false;
		}
//		if (validatedData.getSourcePatternNumber().equals("")) {
//			JErrorMessage.show("M3903", this);
//			return false;
//		}

		data.setValidateAsDataSet();
		return true;
	}

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
			// init();
		} catch (Exception ex) {
			JErrorMessage.show("M3937",this);
			logger.error(ex.getMessage());
		}
	}

	// edit s.inoue 2010/03/04
	/* 取込処理 */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M3938", this);

		// cancel時
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorKenshinPatternResultFrameData data = new JImportMasterErrorKenshinPatternResultFrameData();

			// CSV読込処理
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
			} catch (IOException e) {
				JErrorMessage.show("M3939",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M3940",this);
					return;
				}

				// 既存保険者-健診マスタ全削除
				kenshinMasterDelete();

				// 検査結果データ取込処理
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
					setCSVItemsToDB(data,insertRow);

					// validate処理
					if (!validateData(data))
						return;

					kenshinPatternMasterRegister(data);
					kenshinPatternMasterDetailRegister(data);
					// del s.inoue 2010/03/09
					// kenshinMasterRegister(data);

				    // eidt s.inoue 2012/06/29
					Domain dm = JApplication.clientSettings.getDomain(JApplication.patternDomain.getDomainId());
					dm.addDomainPair(data.CSV_COLUMN_KENSHIN_PM_K_P_NO, data.CSV_COLUMN_KENSHIN_K_P_NAME);
					JApplication.domains.put(
							JApplication.patternDomain.getDomainId(),JApplication.patternDomain);
				}

				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();
				reloadButton.doClick();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M3941",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M3942",this);
				logger.error(e.getMessage());
			}
		}
	}

	// edit s.inoue 2010/03/04
	/* csvデータ取込 */
	private void setCSVItemsToDB(JImportMasterErrorKenshinPatternResultFrameData data,
			Vector<String> insertRow){

		data.CSV_COLUMN_KENSHIN_PM_K_P_NO = reader.rmQuart(insertRow.get(0));
		data.CSV_COLUMN_KENSHIN_K_P_NAME = reader.rmQuart(insertRow.get(1));
		data.CSV_COLUMN_KENSHIN_BIKOU = reader.rmQuart(insertRow.get(2));

		data.CSV_COLUMN_KENSHIN_LOW_ID = new String[ insertRow.size() ];
		data.CSV_COLUMN_KENSHIN_KOUMOKU_CD = new String[ insertRow.size() ];

		for (int i = 0; i < insertRow.size()-3; i++) {
			data.CSV_COLUMN_KENSHIN_LOW_ID[i] = String.valueOf(i);
			data.CSV_COLUMN_KENSHIN_KOUMOKU_CD[i] = insertRow.get(3 + i);
		}
	}

	// edit s.inoue 2010/03/04
	/* validate処理 */
	private boolean validateData(JImportMasterErrorKenshinPatternResultFrameData data) {

		boolean rettanka = false;

		rettanka= validatedData.setK_P_NO(data.CSV_COLUMN_KENSHIN_PM_K_P_NO)
			&& validatedData.setK_P_NAME(data.CSV_COLUMN_KENSHIN_K_P_NAME)
			&& validatedData.setBIKOU(data.CSV_COLUMN_KENSHIN_BIKOU);
		// パターン詳細は別途処理
//			&& validatedData.set(data.CSV_COLUMN_KENSHIN_LOW_ID)
//			&& validatedData.setShindenzuCode(data.CSV_COLUMN_KENSHIN_KOUMOKU_CD);
		return rettanka;
	}

	/**
	 * 既存保険者,健診マスタ削除
	 * @throws SQLException
	 */
	private void kenshinMasterDelete(){
		// hknjyanumによる削除※履歴を含め削除
		deleteKenshinPattern();
	}

//	private void kenshinMasterRegister(JImportMasterErrorKenshinPatternResultFrameData data)
//		throws SQLException
//	{
//
//		try {
//				// 新規追加の場合はさらに健診項目マスタへの追加も行う
//				ArrayList<Hashtable<String, String>> resultKenshin = new ArrayList<Hashtable<String, String>>();
//				Hashtable<String, String> resultItemKenshin = new Hashtable<String, String>();
//				StringBuffer buffer = new StringBuffer(
//						"SELECT * FROM T_KENSHINMASTER WHERE HKNJANUM = "
//								+ JQueryConvert.queryConvert("99999999"));
//
//				resultKenshin = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
//
//				for (int i = 0; i < resultKenshin.size(); i++) {
//					resultItemKenshin = resultKenshin.get(i);
//
//					buffer = new StringBuffer("INSERT INTO T_KENSHINMASTER ( "
//							+ "HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,DATA_TYPE,MAX_BYTE_LENGTH,XML_PATTERN,"
//							+ "XML_DATA_TYPE,XML_KENSA_CD,OBSERVATION,AUTHOR,AUTHOR_KOUMOKU_CD,"
//							+ "KENSA_GROUP,KENSA_GROUP_CD,KEKKA_OID,KOUMOKU_OID,HISU_FLG,KAGEN,"
//							+ "JYOUGEN,DS_JYOUGEN,JS_JYOUGEN,DS_KAGEN,JS_KAGEN,KIJYUNTI_HANI,"
//							+ "TANI,KENSA_HOUHOU,TANKA_KENSIN,BIKOU,XMLITEM_SEQNO"
//							+ " )VALUES( "
//							+ JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_NAME"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DATA_TYPE"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("MAX_BYTE_LENGTH"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_PATTERN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_DATA_TYPE"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_KENSA_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("OBSERVATION"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR_KOUMOKU_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP_CD"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KEKKA_OID"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_OID"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("HISU_FLG"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_JYOUGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_KAGEN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KIJYUNTI_HANI"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANI"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_HOUHOU"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANKA_KENSIN"))
//							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("BIKOU"))
//							+ JQueryConvert.queryConvert(resultItemKenshin.get("XMLITEM_SEQNO")) +
//							")");
//
//					JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
//				}
//		} catch (SQLException f) {
//			f.printStackTrace();
//			JErrorMessage.show("M3212", this);
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

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void kenshinPatternMasterRegister(JImportMasterErrorKenshinPatternResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		if( validateAsRegisterPushed( validatedData ) )
		{
			if( validatedData.isValidateAsDataSet() )
			{

			buffer = new StringBuffer("INSERT INTO T_KENSHIN_P_M (K_P_NO,K_P_NAME,BIKOU) VALUES (" +
					JQueryConvert.queryConvertAppendComma(validatedData.getK_P_NO()) +
					JQueryConvert.queryConvertAppendComma(validatedData.getK_P_NAME()) +
					JQueryConvert.queryConvert(validatedData.getBIKOU()) +
					")");
			}
		}
		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

	}

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void kenshinPatternMasterDetailRegister(JImportMasterErrorKenshinPatternResultFrameData data)
		throws SQLException
	{

		StringBuilder sb = new StringBuilder();

		// 複製元の健診パターン詳細の行の健診パターンNoを新規パターンNoに書き換え挿入を行う
		String strPM_KPNO = data.CSV_COLUMN_KENSHIN_PM_K_P_NO;

		for (int i = 0; i < data.CSV_COLUMN_KENSHIN_LOW_ID.length; i++) {

			String koumokuCd = data.CSV_COLUMN_KENSHIN_KOUMOKU_CD[i];
			String lowId = data.CSV_COLUMN_KENSHIN_LOW_ID[i];
			if (lowId == null)
				break;

			if (sb.length() > 0)
				sb.delete(0, sb.length() );

			sb.append("INSERT INTO T_KENSHIN_P_S (K_P_NO,KOUMOKU_CD,LOW_ID) VALUES ("
							+ JQueryConvert.queryConvertAppendComma(strPM_KPNO)
							+ JQueryConvert.queryConvertAppendComma(koumokuCd)
							+ JQueryConvert.queryConvert(lowId)
							+ ")");
			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());
		}
	}

	/**
	 * 登録ボタンを押した際の必須項目等を検証
	 */
	protected boolean validateAsRegisterPushed(JKenshinpatternMasterMaintenanceListData data)
	{
		if( validatedData.getK_P_NO().equals("") )
		{
			JErrorMessage.show("M5501",this);
			return false;
		}

		if( validatedData.getK_P_NAME().equals("") )
		{
			JErrorMessage.show("M5502",this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}
	/* all削除 */
	private void deleteKenshinPattern(){

		try {

			StringBuilder sb = new StringBuilder();
			StringBuilder sbWhere = new StringBuilder();

			sb.append("DELETE FROM T_KENSHIN_P_M ");
			sbWhere.append(" WHERE K_P_NO <> '-1' ");
			sbWhere.append(" AND K_P_NO <> '1' ");
			sbWhere.append(" AND K_P_NO <> '2' ");
//			// add s.inoue 2012/04/27
//			sbWhere.append(" AND K_P_NO <> '9999' ");

			JApplication.kikanDatabase.sendNoResultQuery(sb.toString() + sbWhere.toString());

			StringBuilder sbKP = new StringBuilder();
			sbKP.append("DELETE FROM T_KENSHIN_P_S ");

			JApplication.kikanDatabase.sendNoResultQuery(sbKP.toString() + sbWhere.toString());

		    // add s.inoue 2012/06/29
			// 削除できないので、作り直す処理
			JApplication.domains.remove(JApplication.patternDomain.getDomainId());

			// 健診パターンリスト
			ArrayList<Hashtable<String, String>> items = null;
			String pattern = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO";
			try {
				items = JApplication.kikanDatabase.sendExecuteQuery(pattern);
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

			// patternDomain = new Domain("T_KENSHIN_P_M");
			JApplication.patternDomain = new Domain("T_KENSHIN_P_M");

			for (int i = 0; i < items.size(); i++) {
				Hashtable<String, String> Item;
				Item = items.get(i);

				String kpNO = Item.get("K_P_NO");
				String kpNAME = Item.get("K_P_NAME");
				// add s.inoue 2011/11/28
				if(!kpNO.equals("9999")){
					JApplication.patternDomain.addDomainPair(kpNO,kpNAME);
				}
			}
			JApplication.domains.put(
					JApplication.patternDomain.getDomainId(),JApplication.patternDomain);


		} catch (SQLException f) {
			f.printStackTrace();
			JErrorMessage.show("M3922", this);
		}
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
			String saveFileName = JPath.createDirectoryUniqueName("KenshinPatternMaster");

			String defaltPath = JPath.getDesktopPath() +
				File.separator +
				saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
			filePathDialog.setMessageTitle(saveTitle);
			filePathDialog.setDialogTitle(savaDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(false);
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
			JErrorMessage.show("M3943", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
//		JImportMasterErrorHokenjyaResultFrameData data = new JImportMasterErrorHokenjyaResultFrameData();	// edit n.ohkubo 2014/10/01　未使用なので削除

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M3944", this);
			logger.error(e.getMessage());
		}
	}

	/* DBよりデータ取得*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> tmpTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT PM.K_P_NO, PM.K_P_NAME, PM.BIKOU, PS.LOW_ID, PS.KOUMOKU_CD ");
		sb.append(" from T_KENSHIN_P_M PM,T_KENSHIN_P_S PS ");
		sb.append(" where PM.K_P_NO = PS.K_P_NO ");
		sb.append(" and PM.K_P_NO <> '-1' ");
		sb.append(" and PM.K_P_NO <> '1' ");
		sb.append(" and PM.K_P_NO <> '2' ");
		sb.append(" order by PM.k_p_no,PS.LOW_ID");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		// ↓header設定
		String[] allArray = new String[TABLE_COLUMNS.length + TABLE_DETAIL_COLUMNS.length];
		System.arraycopy(TABLE_COLUMNS, 0, allArray, 0, TABLE_COLUMNS.length);
		System.arraycopy(TABLE_DETAIL_COLUMNS, 0, allArray, TABLE_COLUMNS.length, TABLE_DETAIL_COLUMNS.length);

		Vector colmn = new Vector<String>();
		List l = java.util.Arrays.asList(allArray);
		for (Iterator item = l.iterator(); item.hasNext();) {
			colmn.add((String)item.next());
		}
		tmpTable.add(colmn);
		// ↑

		Hashtable<String, String> resultItem = new Hashtable<String, String>();
		// edit s.inoue 2010/05/07
		// Hashtable<String, String> resultItemDetail = new Hashtable<String, String>();

		String preKPNo= "";

		for( int i=0; i<result.size(); ++i )
		{
			resultItem = result.get(i);

			Vector<String> data = new Vector<String>();

			String keyKPNo =resultItem.get(TABLE_COLUMNS[0]).trim();

			if (keyKPNo.equals(preKPNo))
				continue;

			data.add(keyKPNo);
			data.add(resultItem.get(TABLE_COLUMNS[1]).trim());
			data.add(resultItem.get(TABLE_COLUMNS[2]).trim());

			if (resultItem.get(TABLE_DETAIL_COLUMNS[0]) == null)
				break;

			// 詳細
			// edit s.inoue 2010/05/07
			Hashtable<String, String> resultItemDetail = new Hashtable<String, String>();

			for (int j = 0; j < result.size(); j++) {
				resultItemDetail = result.get(j);
				System.out.println("項目コード：" + resultItemDetail.get(TABLE_DETAIL_COLUMNS[1]).trim());
				// edit s.inoue 2010/05/07
				if (keyKPNo.equals(resultItemDetail.get(TABLE_COLUMNS[0]).trim())){
					data.add(resultItemDetail.get(TABLE_DETAIL_COLUMNS[0]).trim());
					data.add(resultItemDetail.get(TABLE_DETAIL_COLUMNS[1]).trim());
				}
			}

			tmpTable.add(data);
			preKPNo = resultItem.get(TABLE_COLUMNS[0]).trim();

		}

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();
		newTable.addAll(tmpTable);

		return newTable;
	}
	/**ExportEnd↑↑↑↑↑**********************************************************/


	/* ボタンアクション */
	public void closeButtton_actionPerformed(ActionEvent e) {
//		dispose();
	}
	/* ボタンアクション */
	public void closeButtton_keyPerformed(KeyEvent e) {
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
