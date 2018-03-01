package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import javax.swing.*;

import java.awt.*;
import java.sql.*;
import java.util.Hashtable;
import java.awt.event.*;

import org.openswing.swing.client.*;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.logger.client.Logger;
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
	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
	protected GenericButton buttonClose = null;

	/* control */
	protected TextColumn textColumn_User = new TextColumn();
	protected TextColumn textColumn_Pass = new TextColumn();
//	protected TextColumn textColumn_Kengen = new TextColumn();
	protected ComboColumn textColumn_Kengen = new ComboColumn();
	protected Hashtable domains = new Hashtable();

	private static String CONST_DATA_VALUE =
		"jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system.JUserMaintenanceListFrameData";
	/* コンストラクタ */
	public JUserMaintenanceListFrame(Connection conn,
			JUserMaintenanceListFrameCtl controller) {
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
			JUserMaintenanceListFrameCtl controller){
		this.conn = conn;
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);
		} catch (Exception e) {
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

		textColumn_Kengen.setColumnFilterable(true);
		textColumn_Kengen.setColumnName("KENGEN");
		textColumn_Kengen.setColumnSortable(true);
		textColumn_Kengen.setPreferredWidth(100);
		textColumn_Kengen.addKeyListener(this);
		textColumn_Kengen.setEditableOnEdit(true);
		textColumn_Kengen.setEditableOnInsert(true);

		textColumn_Kengen.setDomainId("KENGEN");

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
		grid.getColumnContainer().add(textColumn_Kengen, null);

		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.user-mastermaintenance.frame.title","tokutei.user-mastermaintenance.frame.guidance");
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
