//ooku
//v2.1.1のjarとあわなかった。修正前のバックアップ


//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen;
//
//import javax.swing.*;
//
//import org.apache.log4j.Logger;
//import org.openswing.swing.client.*;
//
//import java.awt.*;
//import java.sql.*;
//import java.awt.event.*;
//import org.openswing.swing.table.columns.client.*;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.app.JPath;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedDelButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenDeleteButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenImportButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenReloadButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
//import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
//import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
//import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
//
///**
// * 一覧List画面
// * @author s.inoue
// * @version 2.0
// */
//public class CopyOfJKeinenMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {
//
//	private Connection conn = null;
//	private static Logger logger = Logger.getLogger(CopyOfJKeinenMasterMaintenanceListFrame.class);
//
//	protected GridControl grid = new GridControl();
//
//	/* button */
//	// 1段
////	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
////	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
//	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
//	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);
//
//	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
//	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
//	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
//	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
//	protected NavigatorBar navigatorBar = new NavigatorBar();
//	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
//	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
//
//	// 2段
//	protected GenericButton buttonNayose = null;
//	protected GenericButton buttonClose = null;
//	protected JPanel buttonOriginePanel = new JPanel();
//	protected JPanel buttonPanel = new JPanel();
//
//	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();  //  @jve:decl-index=0:
//	protected FlowLayout flowLayoutPanel = new FlowLayout();
//
//	private static String CONST_DATA_VALUE
//		="jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.keinen.JKeinenMasterMaintenanceListData";
//
//	/* control */
//	protected TextColumn textColumn_NayoseNo = new TextColumn();
//	protected TextColumn textColumn_JyusinseiriNo = new TextColumn();
//	protected TextColumn textColumn_KananName = new TextColumn();
//	protected TextColumn textColumn_Name = new TextColumn();
//	protected TextColumn textColumn_BirthDay = new TextColumn();
//	protected TextColumn textColumn_Sex = new TextColumn();
//	protected TextColumn textColumn_HomeAdrs = new TextColumn();
//	protected TextColumn textColumn_HihokenKigo = new TextColumn();
//	protected TextColumn textColumn_HihokenNo = new TextColumn();
//	protected TextColumn textColumn_Update = new TextColumn();
//
//	/* コンストラクタ */
//	public CopyOfJKeinenMasterMaintenanceListFrame(Connection conn,
//			JKeinenMasterMaintenanceListFrameCtrl controller) {
//		setControl(conn,controller);
//	}
//
//	/* リロード */
//	public void reloadData() {
//		grid.reloadData();
//	}
//
//	/* グリッドgetter */
//	public GridControl getGrid() {
//		return grid;
//	}
//
//	/* 制御メソッド */
//	public void setControl(Connection conn,
//			JKeinenMasterMaintenanceListFrameCtrl controller){
//		this.conn = conn;
//		try {
//			jbInit();
//
//			this.setSize(ViewSettings.getFrameCommonSize());
//			this.setLocationRelativeTo( null );
//			grid.setController(controller);
//			grid.setGridDataLocator(controller);
//
//			setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/* 初期化処理 */
//	public void jbInit() throws Exception {
//
//		// grid.setHeaderHeight(40);
//	    grid.setMaxNumberOfRowsOnInsert(50);
//
//	    // del grid property openswing s.inoue 2011/02/09
////	    grid.setMaxSortedColumns(1);
////	    grid.setEditOnSingleRow(true);
////	    grid.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
////	    grid.setEditOnSingleRow(true);
////	    grid.setInsertRowsOnTop(false);
////	    grid.setAllowInsertInEdit(true);
//
//		/* control ※ClientApplicationと一致*/
//		textColumn_NayoseNo.setColumnFilterable(true);
//		textColumn_NayoseNo.setColumnName("NAYOSE_NO");
//		textColumn_NayoseNo.setColumnSortable(true);
//		textColumn_NayoseNo.setPreferredWidth(100);
//		textColumn_NayoseNo.setEditableOnEdit(true);
//		textColumn_NayoseNo.setColumnRequired(true);
//
//		textColumn_JyusinseiriNo.setColumnFilterable(true);
//		textColumn_JyusinseiriNo.setColumnName("JYUSHIN_SEIRI_NO");
//		textColumn_JyusinseiriNo.setColumnSortable(true);
//		textColumn_JyusinseiriNo.setPreferredWidth(100);
//		textColumn_JyusinseiriNo.setColumnRequired(false);
//
//		textColumn_KananName.setColumnFilterable(true);
//		textColumn_KananName.setColumnName("NAME");
//		textColumn_KananName.setColumnSortable(true);
//		textColumn_KananName.setPreferredWidth(150);
//		textColumn_KananName.setColumnRequired(false);
//
//		textColumn_Name.setColumnFilterable(true);
//		textColumn_Name.setColumnName("KANANAME");
//		textColumn_Name.setColumnSortable(true);
//		textColumn_Name.setPreferredWidth(150);
//		textColumn_Name.setColumnRequired(false);
//
//		textColumn_BirthDay.setColumnFilterable(true);
//		textColumn_BirthDay.setColumnName("BIRTHDAY");
//		textColumn_BirthDay.setColumnSortable(true);
//		textColumn_BirthDay.setPreferredWidth(80);
//		textColumn_BirthDay.setColumnRequired(false);
//
//		textColumn_Sex.setColumnFilterable(true);
//		textColumn_Sex.setColumnName("SEX");
//		textColumn_Sex.setColumnSortable(true);
//		textColumn_Sex.setPreferredWidth(40);
//		textColumn_Sex.setColumnRequired(false);
//
//		textColumn_HomeAdrs.setColumnFilterable(true);
//		textColumn_HomeAdrs.setColumnName("HOME_ADRS");
//		textColumn_HomeAdrs.setColumnSortable(true);
//		textColumn_HomeAdrs.setPreferredWidth(250);
//		textColumn_HomeAdrs.setColumnRequired(false);
//
////		textColumn_HihokenKigo.setColumnFilterable(true);
//		textColumn_HihokenKigo.setColumnName("HIHOKENJYASYO_KIGOU");
////		textColumn_HihokenKigo.setColumnSortable(true);
//		textColumn_HihokenKigo.setPreferredWidth(150);
//		textColumn_HihokenKigo.setColumnRequired(false);
//		// eidt s.inoue 2012/10/25
//		textColumn_HihokenKigo.setColumnVisible(false);
//		textColumn_HihokenKigo.setColumnFilterable(false);
//		textColumn_HihokenKigo.setColumnSortable(false);
//
////		textColumn_HihokenNo.setColumnFilterable(true);
//		textColumn_HihokenNo.setColumnName("HIHOKENJYASYO_NO");
////		textColumn_HihokenNo.setColumnSortable(true);
//		textColumn_HihokenNo.setPreferredWidth(150);
//		textColumn_HihokenNo.setColumnRequired(false);
//		// eidt s.inoue 2012/10/25
//		textColumn_HihokenNo.setColumnVisible(false);
//		textColumn_HihokenNo.setColumnFilterable(false);
//		textColumn_HihokenNo.setColumnSortable(false);
//
////		textColumn_Update.setColumnFilterable(true);
//		textColumn_Update.setColumnName("UPDATE_TIMESTAMP");
////		textColumn_Update.setColumnSortable(true);
//		textColumn_Update.setPreferredWidth(150);
//		textColumn_Update.setColumnRequired(false);
//		// eidt s.inoue 2012/10/25
//		textColumn_Update.setColumnVisible(false);
//		textColumn_Update.setColumnFilterable(false);
//		textColumn_Update.setColumnSortable(false);
//
//		/* button */
//		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
//		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
//
////		buttonOriginePanel.add(insertButton, null);
//		buttonOriginePanel.add(editButton, null);
//		buttonOriginePanel.add(saveButton, null);
//		buttonOriginePanel.add(reloadButton, null);
//		buttonOriginePanel.add(deleteButton, null);
//		buttonOriginePanel.add(exportButton, null);
//		buttonOriginePanel.add(navigatorBar, null);
//
//		// button生成
//		setJButtons();
//
//	    // openswing s.inoue 2011/02/03
//	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
//	    buttonPanel.setLayout(flowLayoutPanel);
//	    buttonPanel.add(buttonClose, null);
//	    buttonPanel.add(buttonNayose, null);
//
//		// action設定
//		buttonNayose.addActionListener(new ListFrame_closeButton_actionAdapter(this));
//		buttonClose.addActionListener(new ListFrame_closeButton_actionAdapter(this));
//
//		Box origineBox = new Box(BoxLayout.X_AXIS);
//		origineBox.add(buttonOriginePanel);
//		origineBox.add(Box.createVerticalStrut(2));
//
//		// box2行目
//		Box recordBox = new Box(BoxLayout.X_AXIS);
//		recordBox.add(buttonPanel);
//
//		Box buttonBox = Box.createVerticalBox();
//		buttonBox.add(origineBox);
//		buttonBox.add(new JSeparator());
//		buttonBox.add(recordBox);
//
//		/* list */
//		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);
////		grid.setInsertButton(insertButton);
//		grid.setEditButton(editButton);
//		grid.setSaveButton(saveButton);
//		grid.setReloadButton(reloadButton);
//		grid.setDeleteButton(deleteButton);
//		grid.setExportButton(exportButton);
//		grid.setNavBar(navigatorBar);
//
//		grid.setMaxSortedColumns(5);
//		grid.setValueObjectClassName(CONST_DATA_VALUE);
//		grid.setOrderWithLoadData(false);
//
//		grid.getColumnContainer().add(textColumn_NayoseNo, null);
//		grid.getColumnContainer().add(textColumn_JyusinseiriNo, null);
//		grid.getColumnContainer().add(textColumn_KananName, null);
//		grid.getColumnContainer().add(textColumn_Name, null);
//		grid.getColumnContainer().add(textColumn_BirthDay, null);
//		grid.getColumnContainer().add(textColumn_Sex, null);
//		grid.getColumnContainer().add(textColumn_HomeAdrs, null);
//		grid.getColumnContainer().add(textColumn_HihokenKigo, null);
//		grid.getColumnContainer().add(textColumn_HihokenNo, null);
//		grid.getColumnContainer().add(textColumn_Update, null);
//
//		// add s.inoue 2012/11/12
//		jLabel_Title = new TitleLabel("tokutei.nayose-code-mastermaintenance.frame.title","tokutei.nayose-code-mastermaintenance.frame.guidance");
//		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
//
//		this.getContentPane().add(grid, BorderLayout.CENTER);
//		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);
//		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//	}
//
//	/**
//	 * This method initializes jPanel_TitleArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	protected JPanel jPanel_TitleArea = null;
//	protected ExtendedLabel jLabel_Title = null;
//
//	private JPanel getJPanel_TitleArea(Box buttonBox) {
//		if (jPanel_TitleArea == null) {
//			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
//			gridBagConstraints26.insets = new Insets(0, 0, 0, 0);
//			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints26.gridy = 1;
//			gridBagConstraints26.gridx = 0;
//
//			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
//			gridBagConstraints25.gridy = 0;
////			gridBagConstraints25.ipady = 10;
//			gridBagConstraints25.anchor = GridBagConstraints.WEST;
//			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
//			gridBagConstraints25.insets = new Insets(5, 5, 0, 0);
//			gridBagConstraints25.weightx = 1.0D;
//			gridBagConstraints25.gridx = 0;
//
//			jPanel_TitleArea = new JPanel();
//			jPanel_TitleArea.setLayout(new GridBagLayout());
//			jPanel_TitleArea.setName("jPanel2");
//			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
//			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
//		}
//		return jPanel_TitleArea;
//	}
//
//	/* ボタン群 */
//	/**
//	 * This method initializes jButton_Close
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
//	 */
//	private void setJButtons() {
//
////		if (buttonNayose == null) {
////			// 経年処理button
////			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_NAYOSE);
////			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
////			buttonNayose = new GenericButton();
////			buttonNayose.setVerticalAlignment(SwingConstants.BOTTOM);
////			buttonNayose.setHorizontalTextPosition(JLabel.CENTER);
////			buttonNayose.setVerticalTextPosition(SwingConstants.BOTTOM);
////			buttonNayose.setAttributeName("Close");
////			buttonNayose.setMnemonic(KeyEvent.VK_N);
////			buttonNayose.setText("名寄せ(N)");
////			buttonNayose.setToolTipText("名寄せ(ALT+N)");
////			buttonNayose.setIcon(icon);
////		}
//
////		if (buttonClose == null) {
////			// 閉じるbutton
////			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.IcoClose);
////			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
////			buttonClose = new GenericButton();
////			buttonClose.setVerticalAlignment(SwingConstants.BOTTOM);
////			buttonClose.setHorizontalTextPosition(JLabel.CENTER);
////			buttonClose.setVerticalTextPosition(SwingConstants.BOTTOM);
////			buttonClose.setAttributeName("Close");
////			buttonClose.setMnemonic(KeyEvent.VK_C);
////			buttonClose.setText("閉じる(C)");
////			buttonClose.setToolTipText("閉じる(ALT+C)");
////			// openswing s.inoue 2011/02/09
////			buttonClose.setPreferredSize(new Dimension(83,50));
////
////			buttonClose.setIcon(icon);
////		}
//		if (buttonNayose == null) {
//			// 名寄せbutton
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.IcoNayose);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			buttonNayose= new ExtendedOpenGenericButton(
//					"Nayose","名寄せ(N)","名寄せ(ALT+N)",KeyEvent.VK_N,icon);
//		}
//		if (buttonClose == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			buttonClose= new ExtendedOpenGenericButton(
//					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
//		}
//
//
//	}
//
//	/* ボタンアクション用内部クラス */
//	class ListFrame_closeButton_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
//	  CopyOfJKeinenMasterMaintenanceListFrame adaptee;
//
//		  ListFrame_closeButton_actionAdapter(CopyOfJKeinenMasterMaintenanceListFrame adaptee) {
//		    this.adaptee = adaptee;
//		  }
//		  public void actionPerformed(ActionEvent e) {
//
//			  Object source = e.getSource();
//			  if (source == buttonNayose){
//				  logger.info(buttonNayose.getText());
//				  pushedNayoseButton();
//			  }else if (source == buttonClose){
//				  logger.info(buttonClose.getText());
//				  adaptee.closeButtton_actionPerformed(e);
//			  }
//
//		  }
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
//	}
//
//	// 名寄せ処理
//	public void pushedNayoseButton() {
//
//		DBYearAdjuster yajuster = new DBYearAdjuster();
//		// データベース切断
//		try {
//			JApplication.kikanDatabase.Shutdown();
//		} catch (SQLException ex) {
//			logger.error(ex.getMessage());
//		}
//
//		yajuster.callfixedNayose(JApplication.kikanNumber);
//
//		try {
//			JApplication.kikanDatabase = JConnection
//				.ConnectKikanDatabase(JApplication.kikanNumber);
//
//			// add s.inoue 2013/02/25
//			this.conn = JApplication.kikanDatabase.getMConnection();
//		} catch (SQLException ex) {
//			logger.error(ex.getMessage());
//		}
//	}
//
//    // add s.inoue 2013/02/25
//    public Connection getConnection(){
//    	return conn;
//    }
//
//	/* ボタンアクション */
//	public void closeButtton_actionPerformed(ActionEvent e) {
//		dispose();
//	}
//	/* ボタンアクション */
//	public void closeButtton_keyPerformed(KeyEvent e) {
//		dispose();
//	}
//
//	// イベント処理
//	public void actionPerformed(ActionEvent ae) {
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//	}
//}
