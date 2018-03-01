package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;

import org.openswing.swing.client.*;
import org.openswing.swing.table.columns.client.*;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
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

/**
 * 一覧List画面
 * @author s.inoue
 * @version 2.0
 */
public class JUserMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

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

	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	// del s.inoue 2013/05/10
//	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
	protected GenericButton buttonClose = null;

	/* control */
	protected TextColumn textColumn_User = new TextColumn();
	protected TextColumn textColumn_Pass = new TextColumn();
//	protected TextColumn textColumn_Kengen = new TextColumn();

	private static String CONST_DATA_VALUE =
		"jp.or.med.orca.jma_tokutei.kenshin.admin.frame.JUserMaintenanceListFrameData";
	/* コンストラクタ */
	public JUserMaintenanceListFrame(Connection conn,
			JUserMaintenanceListFrameCtrl controller) {
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
			JUserMaintenanceListFrameCtrl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
//			setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 初期化処理 */
	public void jbInit() throws Exception {
		/* control ※ClientApplicationと一致*/
		textColumn_User.setColumnFilterable(true);
		textColumn_User.setColumnName("USER_NAME");
		textColumn_User.setColumnSortable(true);
		textColumn_User.setPreferredWidth(100);
		textColumn_User.addKeyListener(this);
		textColumn_User.setEditableOnEdit(true);
		textColumn_User.setEditableOnInsert(true);

		textColumn_Pass.setColumnFilterable(true);
		textColumn_Pass.setColumnName("PASS");
		textColumn_Pass.setColumnSortable(true);
		textColumn_Pass.setPreferredWidth(200);
		textColumn_Pass.addKeyListener(this);
		textColumn_Pass.setEditableOnEdit(true);
		textColumn_Pass.setEditableOnInsert(true);

	    // eidt s.inoue 2011/04/15
		textColumn_Pass.setMaxCharacters(20);
		textColumn_Pass.setPreferredWidth(90);
		textColumn_Pass.setEncryptText(true);

//		textColumn_Kengen.setColumnFilterable(true);
//		textColumn_Kengen.setColumnName("KENGEN");
//		textColumn_Kengen.setColumnSortable(true);
//		textColumn_Kengen.setPreferredWidth(100);
//		textColumn_Kengen.addKeyListener(this);
//		textColumn_Kengen.setEditableOnEdit(true);
//		textColumn_Kengen.setEditableOnInsert(true);

		/* button */
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
		buttonOriginePanel.add(insertButton, null);
		buttonOriginePanel.add(editButton, null);
		buttonOriginePanel.add(reloadButton, null);
		buttonOriginePanel.add(deleteButton, null);
		buttonOriginePanel.add(saveButton, null);

		// button生成
		setJButtons();

	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);

		// action設定
		buttonClose.addActionListener(new ListFrame_closeButton_actionAdapter(this));

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
		grid.setMaxSortedColumns(5);
		grid.setValueObjectClassName(CONST_DATA_VALUE);
		grid.setOrderWithLoadData(false);
		grid.addKeyListener(this);

		grid.getColumnContainer().add(textColumn_User, null);
		grid.getColumnContainer().add(textColumn_Pass, null);

//		grid.getColumnContainer().add(textColumn_Kengen, null);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("admin.user-mastermaintenance.frame.title","admin.user-mastermaintenance.frame.guidance");
		jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);
		// eidt s.inoue 2011/04/15
//		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		String title = ViewSettings.getTokuteFrameTitleWithKikanInfomation();
		if (title == null || title.isEmpty()) {
			title  = ViewSettings.getAdminCommonTitleWithVersion();
		}
		this.setTitle(title);

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
	  JUserMaintenanceListFrame adaptee;

		  ListFrame_closeButton_actionAdapter(JUserMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  public void actionPerformed(ActionEvent e) {
		    adaptee.closeButtton_actionPerformed(e);
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
		if (buttonClose == null) {
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
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

		int keycode = e.getKeyCode();
	    String presskey = e.getKeyText(keycode);

	    int mod = e.getModifiersEx();

	    if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
	      presskey += " +SHIFT";
	    }

	    if ((mod & InputEvent.ALT_DOWN_MASK) != 0){
	      presskey += " +ALT";
	    }

	    if ((mod & InputEvent.CTRL_DOWN_MASK) != 0){
	      presskey += " +CTRL";
	    }

	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}

//
//import java.awt.BorderLayout;
//import javax.swing.JPanel;
//import javax.swing.JFrame;
//import java.awt.GridLayout;
//import java.awt.FlowLayout;
//import javax.swing.SwingConstants;
//import javax.swing.JLabel;
//import java.awt.CardLayout;
//import java.awt.Font;
//import java.awt.Color;
//import java.awt.Rectangle;
//import java.awt.event.*;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import javax.swing.BorderFactory;
//import java.awt.GridBagLayout;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//
//
///**
// * ユーザメンテナンス
// */
//public class JUserMaintenanceFrame extends JFrame implements ActionListener,KeyListener
//{
//	protected static final long serialVersionUID = 1L;
//	protected JPanel jPanel_Content = null;
//	protected JPanel jPanel_ButtonArea = null;
//	protected ExtendedButton jButton_End = null;
//	protected JPanel jPanel_NaviArea = null;
//	protected JLabel jLabel_Title = null;
//	protected JLabel jLabel_MainExpl = null;
//	protected JPanel jPanel_TitleArea = null;
//	protected JPanel jPanel_ExplArea2 = null;
//	protected JLabel jLabal_SubExpl = null;
//	protected JPanel jPanel_ExplArea1 = null;
//	protected ExtendedButton jButton_Add = null;
//	protected ExtendedButton jButton_Change = null;
//	protected ExtendedButton jButton_Delete = null;
//	protected JPanel jPanel_Table = null;
//
//	/**
//	 * This is the default constructor
//	 */
//	public JUserMaintenanceFrame() {
//		super();
//		initialize();
//	}
//
//	/**
//	 * This method initializes this
//	 *
//	 * @return void
//	 */
//	private void initialize() {
//		/* Modified 2008/05/06 若月  */
//		/* --------------------------------------------------- */
////		this.setSize(800, 600);
////		this.setContentPane(getJPanel_Content());
////		this.setTitle("特定健診システム");
////		setLocationRelativeTo( null );
////		this.setVisible(true);
//		/* --------------------------------------------------- */
//		this.setContentPane(getJPanel_Content());
//		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//		this.setSize(ViewSettings.getFrameCommonSize());
//		this.setLocationRelativeTo( null );
//		this.setVisible(true);
//		/* --------------------------------------------------- */
//	}
//
//	/**
//	 * This method initializes jPanel_Content
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Content() {
//		if (jPanel_Content == null) {
//			BorderLayout borderLayout = new BorderLayout();
//			borderLayout.setVgap(2);
//			jPanel_Content = new JPanel();
//			jPanel_Content.setLayout(borderLayout);
//			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
//			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
//			jPanel_Content.add(getJPanel_Table(), BorderLayout.CENTER);
//		}
//		return jPanel_Content;
//	}
//
//	/**
//	 * This method initializes jPanel_ButtonArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ButtonArea() {
//		if (jPanel_ButtonArea == null) {
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.gridx = 0;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints2.gridx = 3;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridy = 0;
//			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints1.gridx = 2;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.anchor = GridBagConstraints.EAST;
//			gridBagConstraints.gridx = 1;
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			jPanel_ButtonArea.add(getJButton_Add(), gridBagConstraints);
//			jPanel_ButtonArea.add(getJButton_Change(), gridBagConstraints1);
//			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints2);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints3);
//		}
//		return jPanel_ButtonArea;
//	}
//
//	/**
//	 * This method initializes jButton_End
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_End() {
//		if (jButton_End == null) {
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
//		}
//		return jButton_End;
//	}
//
//	/**
//	 * This method initializes jPanel_NaviArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_NaviArea() {
//		if (jPanel_NaviArea == null) {
//			CardLayout cardLayout2 = new CardLayout();
//			cardLayout2.setHgap(10);
//			cardLayout2.setVgap(10);
//			jLabel_MainExpl = new JLabel();
//			jLabel_MainExpl.setText("システム管理者のメンテナンスを行います。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("システム管理者メンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
//			jPanel_NaviArea = new JPanel();
//			jPanel_NaviArea.setLayout(cardLayout2);
//			jPanel_NaviArea.add(getJPanel_TitleArea(), getJPanel_TitleArea().getName());
//		}
//		return jPanel_NaviArea;
//	}
//
//	/**
//	 * This method initializes jPanel_TitleArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_TitleArea() {
//		if (jPanel_TitleArea == null) {
//			GridLayout gridLayout = new GridLayout();
//			gridLayout.setRows(2);
//			gridLayout.setHgap(0);
//			gridLayout.setColumns(0);
//			gridLayout.setVgap(10);
//			jPanel_TitleArea = new JPanel();
//			jPanel_TitleArea.setLayout(gridLayout);
//			jPanel_TitleArea.setName("jPanel2");
//			jPanel_TitleArea.add(jLabel_Title, null);
//			jPanel_TitleArea.add(getJPanel_ExplArea1(), null);
//		}
//		return jPanel_TitleArea;
//	}
//
//	/**
//	 * This method initializes jPanel_ExplArea2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea2() {
//		if (jPanel_ExplArea2 == null) {
//			jLabal_SubExpl = new JLabel();
//			jLabal_SubExpl.setText("　");
//			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			GridLayout gridLayout1 = new GridLayout();
//			gridLayout1.setRows(2);
//			jPanel_ExplArea2 = new JPanel();
//			jPanel_ExplArea2.setName("jPanel4");
//			jPanel_ExplArea2.setLayout(gridLayout1);
//			jPanel_ExplArea2.add(jLabel_MainExpl, null);
//			jPanel_ExplArea2.add(jLabal_SubExpl, null);
//		}
//		return jPanel_ExplArea2;
//	}
//
//	/**
//	 * This method initializes jPanel_ExplArea1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea1() {
//		if (jPanel_ExplArea1 == null) {
//			CardLayout cardLayout1 = new CardLayout();
//			cardLayout1.setHgap(20);
//			jPanel_ExplArea1 = new JPanel();
//			jPanel_ExplArea1.setLayout(cardLayout1);
//			jPanel_ExplArea1.add(getJPanel_ExplArea2(), getJPanel_ExplArea2().getName());
//		}
//		return jPanel_ExplArea1;
//	}
//
//	/**
//	 * This method initializes jButton_Add
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Add() {
//		if (jButton_Add == null) {
//			jButton_Add = new ExtendedButton();
//			jButton_Add.setText("追加(F9)");
//			jButton_Add.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Add.addActionListener(this);
//		}
//		return jButton_Add;
//	}
//
//	/**
//	 * This method initializes jButton_Change
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Change() {
//		if (jButton_Change == null) {
//			jButton_Change = new ExtendedButton();
//			jButton_Change.setText("変更(F11)");
//			jButton_Change.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Change.addActionListener(this);
//		}
//		return jButton_Change;
//	}
//
//	/**
//	 * This method initializes jButton_Delete
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Delete() {
//		if (jButton_Delete == null) {
//			jButton_Delete = new ExtendedButton();
//			jButton_Delete.setText("削除(F12)");
//			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Delete.addActionListener(this);
//		}
//		return jButton_Delete;
//	}
//
//	/**
//	 * This method initializes jPanel_Table
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Table() {
//		if (jPanel_Table == null) {
//			jPanel_Table = new JPanel();
//			jPanel_Table.setLayout(new BorderLayout());
//			jPanel_Table.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
//		}
//		return jPanel_Table;
//	}
//
//	/*
//	 * FrameSize Control
//	 */
//	public void validate()
//	{
//		Rectangle rect = getBounds();
//
//		super.validate();
//
//		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
//			ViewSettings.getFrameCommonHeight() > rect.height )
//		{
//			setBounds( rect.x,
//					   rect.y,
//					   ViewSettings.getFrameCommonWidth(),
//					   ViewSettings.getFrameCommonHeight() );
//		}
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//}
