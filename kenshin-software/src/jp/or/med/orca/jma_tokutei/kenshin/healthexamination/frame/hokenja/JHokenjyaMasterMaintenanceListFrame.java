package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.openswing.swing.client.*;
import org.openswing.swing.domains.java.Domain;

//import java.awt.*;

import org.openswing.swing.table.columns.client.*;

import java.sql.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenDeleteButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenReloadButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorHokenjyaResultFrameData;

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JHokenjyaMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	private Connection conn = null;

	GridControl grid = new GridControl();

	/* button */
	// eidt s.inoue 2011/12/14
	// protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
	// protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	// eidt s.inoue 2012/07/04
	 protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
	 protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);
//	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton();
//	protected ExtendedDelButton deleteButton = new ExtendedDelButton();

	// protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
	// del s.inoue 2011/02/24
	// protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	// protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();

	protected ExtendedOpenGenericButton buttonClose = null;
	protected ExtendedOpenGenericButton buttonAdd = null;
	protected ExtendedOpenGenericButton buttonExport = null;
	protected ExtendedOpenGenericButton buttonImport = null;

	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();

	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	/* control */
	protected TextColumn textColumn_HokenjyaNumber = new TextColumn();
	protected TextColumn textColumn_HokenjyaName = new TextColumn();
	protected TextColumn textColumn_Zipcode = new TextColumn();
	protected TextColumn textColumn_Address = new TextColumn();
	protected TextColumn textColumn_TEL = new TextColumn();
	protected FlowLayout flowLayout1 = new FlowLayout();
	private IDialog filePathDialog;

	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;
	private static Vector<Vector<String>> CSVItems = null;
	private THokenjyaDao hokenjyaDao = null;
	private THokenjya hokenjya = new THokenjya();
	private JHokenjyaMasterMaintenanceEditFrameData validatedData = new JHokenjyaMasterMaintenanceEditFrameData();
	private ArrayList<Integer> selectedData = null;

	private static final String[] TABLE_COLUMNS = {
		"HKNJANUM", "HKNJANAME", "POST", "ADRS", "BANTI", "TEL", "KIGO", "HON_GAIKYURATE", "HON_NYUKYURATE", "KZK_GAIKYURATE", "KZK_NYUKYURATE",
		"ITAKU_KBN", "TANKA_KIHON", "HINKETU_CD", "TANKA_HINKETU", "SINDENZU_CD", "TANKA_SINDENZU", "GANTEI_CD", "TANKA_GANTEI",
		"TANKA_NINGENDOC", "TANKA_HANTEI", "HKNJYA_HISTORY_NO", "HKNJYA_LIMITDATE_START", "HKNJYA_LIMITDATE_END", "YUKOU_FLG"
		 };

	private static Logger logger = Logger.getLogger(JHokenjyaMasterMaintenanceListFrame.class);

	/* コンストラクタ */
	public JHokenjyaMasterMaintenanceListFrame(Connection conn,
			JHokenjyaMasterMaintenanceListFrameCtrl controller) {
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

	/* JFramegetter */
	public void setJFrameeDitable(){
		this.enableInputMethods(false);
	}

	/* 制御メソッド */
	public void setControl(Connection conn,
			JHokenjyaMasterMaintenanceListFrameCtrl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/* 初期化処理 */
	public void jbInit() throws Exception {

		/* control ※ClientApplicationと一致*/
		textColumn_HokenjyaNumber.setColumnFilterable(true);
		// eidt s.inoue 2012/10/29
		textColumn_HokenjyaNumber.setColumnName("HKNJANUM_M");
		textColumn_HokenjyaNumber.setColumnSortable(true);
		textColumn_HokenjyaNumber.setPreferredWidth(80);

		textColumn_HokenjyaName.setColumnFilterable(true);
		textColumn_HokenjyaName.setColumnName("HKNJANAME");
		textColumn_HokenjyaName.setColumnSortable(true);
		textColumn_HokenjyaName.setPreferredWidth(220);

		textColumn_Zipcode.setColumnFilterable(true);
		textColumn_Zipcode.setColumnName("POST");
		textColumn_Zipcode.setColumnSortable(true);
		textColumn_Zipcode.setPreferredWidth(100);

		textColumn_Address.setColumnFilterable(true);
		textColumn_Address.setColumnName("ADRS");
		textColumn_Address.setColumnSortable(true);
		textColumn_Address.setPreferredWidth(450);

		textColumn_TEL.setColumnFilterable(true);
		textColumn_TEL.setColumnName("TEL");
		textColumn_TEL.setColumnSortable(true);
		textColumn_TEL.setPreferredWidth(100);

		/* button */
		buttonOriginePanel.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.LEFT);

//		buttonOriginePanel.add(insertButton, null);
//		buttonOriginePanel.add(editButton, null);
		buttonOriginePanel.add(reloadButton, null);
		buttonOriginePanel.add(deleteButton, null);
		buttonOriginePanel.add(exportButton, null);
		buttonOriginePanel.add(navigatorBar, null);

		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);

		// button生成
		setJButtons();

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonAdd, null);

	    // move s.inoue 2011/04/05
	    exportButton.setMnemonic(KeyEvent.VK_X);
//	    importButton.setMnemonic(KeyEvent.VK_I);
	    buttonPanel.add(buttonExport,null);
	    buttonPanel.add(buttonImport,null);

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

		// openswing s.inoue 2011/01/26
		// buttonsPanel.add(closeButton,null);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
		grid.setDeleteButton(deleteButton);
		// grid.setEditButton(editButton);
		 grid.setExportButton(exportButton);
		// grid.setImportButton(importButton);

//		grid.setInsertButton(insertButton);
		grid.setMaxSortedColumns(5);
		grid.setNavBar(navigatorBar);
		grid.setReloadButton(reloadButton);
		grid.setValueObjectClassName("jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceListData");
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_HokenjyaNumber, null);
		grid.getColumnContainer().add(textColumn_HokenjyaName, null);
		grid.getColumnContainer().add(textColumn_Zipcode, null);
		grid.getColumnContainer().add(textColumn_Address, null);
//		grid.getColumnContainer().add(textColumn_Banti,null);
		grid.getColumnContainer().add(textColumn_TEL, null);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.hokenja-mastermaintenance.frame.title","tokutei.hokenja-mastermaintenance.frame.title.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		// openswing s.inoue 2011/01/26
		// this.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
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
	  JHokenjyaMasterMaintenanceListFrame adaptee;

		  ListFrame_button_actionAdapter(JHokenjyaMasterMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
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
		}
		if (buttonImport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Import);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonImport= new ExtendedOpenGenericButton(
					"Import","取込(I)","取込(ALT+I)",KeyEvent.VK_I,icon);
		}
	}

	/**
	 * 追加ボタン
	 */
	public void pushedAddButton( ActionEvent e )
	{
		// eidt s.inoue 2011/04/08
//		JScene.CreateDialog(this, new JHokenjyaMasterMaintenanceEditFrameCtrl(),new WindowRefreshEvent());
//		JHokenjyaMasterMaintenanceListData vo = (JHokenjyaMasterMaintenanceListData)persistentObject;
//		JScene.ChangeScene(grid,new JHokenjyaMasterMaintenanceEditFrameCtrl(vo.getHKNJANUM()),new WindowRefreshEvent());
		JScene.CreateDialog(
				this,
				new JHokenjyaMasterMaintenanceEditFrameCtrl(),
				new WindowRefreshEvent());
	}

	/**ExportStart↓↓↓↓↓**********************************************************/
	private static String saveTitle= "csvファイル名を指定して保存";
	private static String selectTitle= "csvファイル選択";
	private static String savaDialogTitle= "csvファイルの保存先を選択、ファイル名を指定してください";
	private static String selectDialogTitle= "csvファイルを選択してください";

	// add s.inoue 2010/02/25
	/**
	 * 書出ボタン
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("HokenjyaMaster");

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
			JErrorMessage.show("M3206", this);
			logger.error(ex.getMessage());
		}
	}

	/* export処理 */
	private void exportCsvData(String filePath){
		JImportMasterErrorHokenjyaResultFrameData data = new JImportMasterErrorHokenjyaResultFrameData();

		// CSV読込処理
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M3206", this);
			logger.error(e.getMessage());
		}
	}

	/* DBよりデータ取得*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

		StringBuilder sb = new StringBuilder();
		// eidt s.inoue 2012/10/29
		sb.append(" SELECT HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL, KIGO, HON_GAIKYURATE, HON_NYUKYURATE,");
		sb.append(" KZK_GAIKYURATE, KZK_NYUKYURATE, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD,");
		sb.append(" TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, TANKA_NINGENDOC, TANKA_HANTEI, HKNJYA_HISTORY_NO, HKNJYA_LIMITDATE_START, HKNJYA_LIMITDATE_END, YUKOU_FLG ");
		sb.append(" FROM T_HOKENJYA ");
		sb.append(" ORDER BY HKNJANUM,HKNJYA_HISTORY_NO DESC");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
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
				if (TABLE_COLUMNS[j].equals("ADRS") ||
						TABLE_COLUMNS[j].equals("BANTI"))
					exportItem = JValidate.encodeUNICODEtoConvert(exportItem).trim();

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

	// add s.inoue 2010/02/25
	/**ImportStart↓↓↓↓↓**********************************************************/
	/**
	 * 取込ボタン
	 */
	public void pushedImportButton( ActionEvent e )
	{
		try {
			filePathDialog = DialogFactory.getInstance().createDialog(this, null);

			filePathDialog.setMessageTitle(selectTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
			filePathDialog.setDialogTitle(selectDialogTitle);
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
			// del s.inoue 2011/04/05
			// initTable();
		} catch (Exception ex) {
			JErrorMessage.show("M3207",this);
			logger.error(ex.getMessage());
		}
	}

	// edit s.inoue 2010/03/04
	/* 取込処理 */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M3205", this);

		// cancel時
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorHokenjyaResultFrameData data = new JImportMasterErrorHokenjyaResultFrameData();

			// CSV読込処理
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET);
			} catch (IOException e) {
				JErrorMessage.show("M3207",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			// db接続
			getconnectDao();

			try {

				// eidt s.inoue 2011/06/07
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M3211",this);
					return;
				}

				// 既存保険者-健診マスタ全削除
				hokenjyaMasterDelete();

				HashMap hmMap =  new HashMap<String, String>();

				// 検査結果データ取込処理
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
					setCSVItemsToDB(data,insertRow);

					// validate処理
					if (!validateData(data))
						return;

					hokenjyaMasterRegister(data);
					if (!hmMap.containsKey(data.CSV_COLUMN_HKNJANUM))
						kenshinMasterRegister(data);
					hmMap.put(data.CSV_COLUMN_HKNJANUM,data.CSV_COLUMN_HKNJANUM);
				}

				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();
				reloadButton.doClick();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M3209",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M3207",this);
				logger.error(e.getMessage());
			}
		}
	}

	// edit s.inoue 2010/03/04
	/* csvデータ取込 */
	private void setCSVItemsToDB(JImportMasterErrorHokenjyaResultFrameData data,
			Vector<String> insertRow){

		data.CSV_COLUMN_HKNJANUM = reader.rmQuart(insertRow.get(0));
		data.CSV_COLUMN_HKNJANAME = reader.rmQuart(insertRow.get(1));
		data.CSV_COLUMN_POST = reader.rmQuart(insertRow.get(2));

		// eidt s.inoue 2012/11/15
		// 受診者住所 ～、ハイフンを長音へ変換
		String address = JValidate.encodeUNICODEtoConvert(insertRow.get(3));
		String banti = JValidate.encodeUNICODEtoConvert(insertRow.get(4));
		data.CSV_COLUMN_ADRS = reader.rmQuart(address);
		data.CSV_COLUMN_BANTI = reader.rmQuart(banti);
//		data.CSV_COLUMN_ADRS = reader.rmQuart(insertRow.get(3));
//		data.CSV_COLUMN_BANTI = reader.rmQuart(insertRow.get(4));

		data.CSV_COLUMN_TEL = reader.rmQuart(insertRow.get(5));
		data.CSV_COLUMN_KIGO = reader.rmQuart(insertRow.get(6));
		data.CSV_COLUMN_HON_GAIKYURATE = reader.rmQuart(insertRow.get(7));
		data.CSV_COLUMN_HON_NYUKYURATE = reader.rmQuart(insertRow.get(8));
		data.CSV_COLUMN_KZK_GAIKYURATE = reader.rmQuart(insertRow.get(9));
		data.CSV_COLUMN_KZK_NYUKYURATE = reader.rmQuart(insertRow.get(10));
		data.CSV_COLUMN_ITAKU_KBN = reader.rmQuart(insertRow.get(11));
		data.CSV_COLUMN_TANKA_KIHON = reader.rmQuart(insertRow.get(12));
		data.CSV_COLUMN_HINKETU_CD = reader.rmQuart(insertRow.get(13));
		data.CSV_COLUMN_TANKA_HINKETU = reader.rmQuart(insertRow.get(14));
		data.CSV_COLUMN_SINDENZU_CD = reader.rmQuart(insertRow.get(15));
		data.CSV_COLUMN_TANKA_SINDENZU = reader.rmQuart(insertRow.get(16));
		data.CSV_COLUMN_GANTEI_CD = reader.rmQuart(insertRow.get(17));
		data.CSV_COLUMN_TANKA_GANTEI = reader.rmQuart(insertRow.get(18));
		data.CSV_COLUMN_TANKA_NINGENDOC = reader.rmQuart(insertRow.get(19));
		data.CSV_COLUMN_TANKA_HANTEI = reader.rmQuart(insertRow.get(20));
		data.CSV_COLUMN_HKNJYA_HISTORY_NO = reader.rmQuart(insertRow.get(21));
		data.CSV_COLUMN_HKNJYA_LIMITDATE_START = reader.rmQuart(insertRow.get(22));
		data.CSV_COLUMN_HKNJYA_LIMITDATE_END = reader.rmQuart(insertRow.get(23));
		data.CSV_COLUMN_YUKOU_FLG = reader.rmQuart(insertRow.get(24));
	}

	// edit s.inoue 2010/03/04
	/* validate処理 */
	private boolean validateData(JImportMasterErrorHokenjyaResultFrameData data) {

		boolean rettanka = false;

		// edit s.inoue 2010/04/27
		String address = data.CSV_COLUMN_ADRS;
		if (!JValidate.isAllZenkaku(address)){
			address = JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(address);
		}
		address = JValidate.encodeUNICODEtoConvert(address);

		rettanka= validatedData.setHokenjyaName(data.CSV_COLUMN_HKNJANAME)
			&& validatedData.setHokenjyaNumber(data.CSV_COLUMN_HKNJANUM)
			&& validatedData.setHonninGairai(data.CSV_COLUMN_HON_GAIKYURATE)
			// edit s.inoue 2010/08/30
			&& validatedData.setHinketsuCode(data.CSV_COLUMN_HINKETU_CD)
			// && validatedData.setHinketsuCode(data.CSV_COLUMN_HON_NYUKYURATE)
			&& validatedData.setShindenzuCode(data.CSV_COLUMN_SINDENZU_CD)
			&& validatedData.setGanteiCode(data.CSV_COLUMN_GANTEI_CD)
			&& validatedData.setTEL(data.CSV_COLUMN_TEL)
			&& validatedData.setZipcode(data.CSV_COLUMN_POST)
			&& validatedData.setAddress(address)
			&& validatedData.setChiban(data.CSV_COLUMN_BANTI)
			&& validatedData.setKigo(data.CSV_COLUMN_KIGO)
			&& validatedData.setHonninNyuin(data.CSV_COLUMN_HON_NYUKYURATE)
			&& validatedData.setKazokuGairai(data.CSV_COLUMN_HON_GAIKYURATE)
			&& validatedData.setKazokuNyuin(data.CSV_COLUMN_KZK_NYUKYURATE)
			&& validatedData.setItakuKubunCode(data.CSV_COLUMN_ITAKU_KBN)
			&& validatedData.setKihonTanka(data.CSV_COLUMN_TANKA_KIHON)
			&& validatedData.setGanteiTanka(data.CSV_COLUMN_TANKA_GANTEI)
			&& validatedData.setHinketsuTanka(data.CSV_COLUMN_TANKA_HINKETU)
			&& validatedData.setNingenDocTanka(data.CSV_COLUMN_TANKA_NINGENDOC)
			&& validatedData.setSindenzuTanka(data.CSV_COLUMN_TANKA_SINDENZU)
			&& validatedData.setTankaHantei(data.CSV_COLUMN_TANKA_HANTEI)
			&& validatedData.setHknjyaHistoryNumber(data.CSV_COLUMN_HKNJYA_HISTORY_NO)
			&& validatedData.setYukouKigenFrom(data.CSV_COLUMN_HKNJYA_LIMITDATE_START)
			&& validatedData.setYukouKigenTo(data.CSV_COLUMN_HKNJYA_LIMITDATE_END)
			&& validatedData.setYukouFlg(data.CSV_COLUMN_YUKOU_FLG);
		return rettanka;
	}
	/*
	 * 接続処理
	 */
	private void getconnectDao(){
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();

			hokenjyaDao = (THokenjyaDao) DaoFactory.createDao(
					connection,
					hokenjya);

		} catch (Exception e) {
			logger.error("機関FDBへの接続処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}
	}

	/**
	 * 既存保険者,健診マスタ削除
	 * @throws SQLException
	 */
	private void hokenjyaMasterDelete(){
		// hknjyanumによる削除※履歴を含め削除
		try {
			// T_HOKENJA削除
			hokenjyaDao.delete();

			// T_KENSHINMASTER削除
			String query = new String("DELETE FROM T_KENSHINMASTER WHERE HKNJANUM <> '99999999'");
			JApplication.kikanDatabase.sendNoResultQuery(query);

		} catch (SQLException e) {
			JErrorMessage.show("M3208",this);
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	private void kenshinMasterRegister(JImportMasterErrorHokenjyaResultFrameData data)
		throws SQLException
	{

		try {
				// 新規追加の場合はさらに健診項目マスタへの追加も行う
				ArrayList<Hashtable<String, String>> resultKenshin = new ArrayList<Hashtable<String, String>>();
				Hashtable<String, String> resultItemKenshin = new Hashtable<String, String>();
				StringBuffer buffer = new StringBuffer(
						"SELECT * FROM T_KENSHINMASTER WHERE HKNJANUM = "
								+ JQueryConvert.queryConvert("99999999"));

				resultKenshin = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());

				for (int i = 0; i < resultKenshin.size(); i++) {
					resultItemKenshin = resultKenshin.get(i);

					buffer = new StringBuffer("INSERT INTO T_KENSHINMASTER ( "
							+ "HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,DATA_TYPE,MAX_BYTE_LENGTH,XML_PATTERN,"
							+ "XML_DATA_TYPE,XML_KENSA_CD,OBSERVATION,AUTHOR,AUTHOR_KOUMOKU_CD,"
							+ "KENSA_GROUP,KENSA_GROUP_CD,KEKKA_OID,KOUMOKU_OID,HISU_FLG,KAGEN,"
							+ "JYOUGEN,DS_JYOUGEN,JS_JYOUGEN,DS_KAGEN,JS_KAGEN,KIJYUNTI_HANI,"
							+ "TANI,KENSA_HOUHOU,TANKA_KENSIN,BIKOU,XMLITEM_SEQNO"
							+ " )VALUES( "
							+ JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_CD"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_NAME"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DATA_TYPE"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("MAX_BYTE_LENGTH"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_PATTERN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_DATA_TYPE"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("XML_KENSA_CD"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("OBSERVATION"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("AUTHOR_KOUMOKU_CD"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_GROUP_CD"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KEKKA_OID"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KOUMOKU_OID"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("HISU_FLG"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KAGEN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JYOUGEN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_JYOUGEN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_JYOUGEN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("DS_KAGEN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("JS_KAGEN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KIJYUNTI_HANI"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANI"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("KENSA_HOUHOU"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("TANKA_KENSIN"))
							+ JQueryConvert.queryConvertAppendComma(resultItemKenshin.get("BIKOU"))
							+ JQueryConvert.queryConvert(resultItemKenshin.get("XMLITEM_SEQNO")) +
							")");

					JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
				}
		} catch (SQLException f) {
			f.printStackTrace();
			JErrorMessage.show("M3212", this);
			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.rollback();
				JApplication.kikanDatabase.getMConnection().rollback();
				return;
			} catch (SQLException g) {
				g.printStackTrace();
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
	}

	/**
	 * CSVデータ登録
	 * @throws SQLException
	 */
	private void hokenjyaMasterRegister(JImportMasterErrorHokenjyaResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		String address = JValidate.encodeUNICODEtoConvert(validatedData.getAddress());
		String tiban = JValidate.encodeUNICODEtoConvert(validatedData.getChiban());

		buffer.append("INSERT INTO T_HOKENJYA (");
		buffer.append("HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL,");
		buffer.append("KIGO,HON_GAIKYURATE,HON_NYUKYURATE,");
		buffer.append("KZK_GAIKYURATE,KZK_NYUKYURATE,ITAKU_KBN,");
		buffer.append("TANKA_KIHON,HINKETU_CD,TANKA_HINKETU,");
		buffer.append("SINDENZU_CD,TANKA_SINDENZU,GANTEI_CD,");
		buffer.append("TANKA_GANTEI,TANKA_NINGENDOC,TANKA_HANTEI,");
		buffer.append("HKNJYA_HISTORY_NO,");
		buffer.append("HKNJYA_LIMITDATE_START,HKNJYA_LIMITDATE_END,");
		buffer.append("YUKOU_FLG)");
		buffer.append("VALUES ( "
			+ JQueryConvert.queryConvertAppendComma(validatedData.getHokenjyaNumber())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getHokenjyaName())
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
			+ JQueryConvert.queryConvertAppendBlankAndComma(address)
			+ JQueryConvert.queryConvertAppendBlankAndComma(tiban)
			+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getTEL())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getCode())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninGairai())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getHonninNyuin())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuGairai())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getKazokuNyuin())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getItakuKubun())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getKihonTanka())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuCode())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getHinketsuTanka())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getShindenzuCode())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getSindenzuTanka())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiCode())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getGanteiTanka())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getNingenDocTanka())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getTankaHantei())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getHknjyaHistoryNumber())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenFrom())
			+ JQueryConvert.queryConvertAppendComma(validatedData.getYukouKigenTo())
			+ JQueryConvert.queryConvert(validatedData.getYukouFlg())
			+ ")");

		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

		// add s.inoue 2012/10/24
		Domain dm = JApplication.clientSettings.getDomain(JApplication.hokenjaDomain.getDomainId());
		dm.addDomainPair(validatedData.getHokenjyaNumber(), validatedData.getHokenjyaNumber() + ":" +validatedData.getHokenjyaName());
	}
	/**ImportEnd↑↑↑↑↑**********************************************************/

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter
	{
		public void windowClosed(WindowEvent e)
		{
			grid.setEnabled(true);
//			reloadButton.doClick();
			// eidt s.inoue 2011/04/12
			// grid.reloadData();
			// eidt s.inoue 2011/06/07
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
