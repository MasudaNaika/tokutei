//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
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
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
//
//import java.awt.Dimension;
//import javax.swing.BorderFactory;
//import java.awt.GridBagLayout;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//
//
//public class JKenshinPatternMaintenanceListFrame extends JFrame implements ActionListener,KeyListener
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
//	protected JPanel jPanel = null;
//	protected ExtendedButton jButton_Add = null;
//	protected ExtendedButton jButton_Copy = null;
//	protected ExtendedButton jButton_Delete = null;
//	protected ExtendedButton jButton_Edit = null;
//	protected ExtendedButton jButton_NoteEdit = null;
//
//	protected ExtendedButton jButton_Import = null;
//	protected ExtendedButton jButton_Export = null;
//
//	/**
//	 * This is the default constructor
//	 */
//	public JKenshinPatternMaintenanceListFrame() {
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
//		/* Modified 2008/03/22 若月  */
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
//			jPanel_Content.add(getJPanel(), BorderLayout.CENTER);
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
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.gridy = 0;
//			gridBagConstraints5.gridx = 0;
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = 0;
//			gridBagConstraints4.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints4.gridx = 7;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints3.gridx = 6;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints2.gridx = 5;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridy = 0;
//			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints1.gridx = 4;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			// del s.inoue 2010/03/09
////			gridBagConstraints.weightx = 1.0D;
////			gridBagConstraints.anchor = GridBagConstraints.EAST;
//			gridBagConstraints.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints.gridx = 3;
//
//			// add s.inoue 2010/02/25
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridy = 0;
//			gridBagConstraints6.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints6.gridx = 1;
//			gridBagConstraints6.anchor = GridBagConstraints.EAST;
//			gridBagConstraints6.weightx = 1.0D;
//
//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridy = 0;
//			gridBagConstraints7.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints7.gridx = 2;
//
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			// add s.inoue 2010/02/25
//			jPanel_ButtonArea.add(getJButton_Import(), gridBagConstraints6);
//			jPanel_ButtonArea.add(getJButton_Export(), gridBagConstraints7);
//
//			jPanel_ButtonArea.add(getJButton_Add(), gridBagConstraints);
//			jPanel_ButtonArea.add(getJButton_NoteEdit(), gridBagConstraints1);
//			jPanel_ButtonArea.add(getJButton_Edit(), gridBagConstraints2);
//			jPanel_ButtonArea.add(getJButton_Copy(), gridBagConstraints3);
//			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints4);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints5);
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
////			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
////			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
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
//			jLabel_MainExpl.setText("一覧からパターンを選択し、画面下部のボタンを押して各処理を実行します。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			// edit ver2 s.inoue 2009/06/04
////			jLabel_Title = new JLabel();
////			jLabel_Title.setText("健診パターンメンテナンス");
////			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
////			jLabel_Title.setBackground(new Color(153, 204, 255));
////			jLabel_Title.setForeground(new Color(51, 51, 51));
////			jLabel_Title.setOpaque(true);
////			jLabel_Title.setName("jLabel");
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_FRAME_TITLE);
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
//			// edit h.yoshitama 2009/11/20
//			jLabal_SubExpl.setText("健診パターンNo1（特定健診）,No2（特定健診＋詳細健診）は基本パターンの為、削除・編集は出来ません。");
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
//	 * This method initializes jPanel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel() {
//		if (jPanel == null) {
//			jPanel = new JPanel();
//			jPanel.setLayout(new BorderLayout());
//			jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		}
//		return jPanel;
//	}
//
//	/**
//	 * This method initializes jButton_Import
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Import() {
//		if (jButton_Import == null) {
//			jButton_Import = new ExtendedButton();
//			jButton_Import.setText("取込(F4)");
//			jButton_Import.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Import.addActionListener(this);
//		}
//		return jButton_Import;
//	}
//
//	/**
//	 * This method initializes jButton_Export
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Export() {
//		if (jButton_Export == null) {
//			jButton_Export = new ExtendedButton();
//			jButton_Export.setText("書出(F5)");
//			jButton_Export.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Export.addActionListener(this);
//		}
//		return jButton_Export;
//	}
//
//
//	/**
//	 * This method initializes jButton_Add
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Add() {
//		if (jButton_Add == null) {
//			jButton_Add = new ExtendedButton();
//			jButton_Add.setText("追加(F6)");
////			jButton_Add.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Add.addActionListener(this);
//		}
//		return jButton_Add;
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//	}
//
//	/**
//	 * This method initializes jButton_Copy
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Copy() {
//		if (jButton_Copy == null) {
//			jButton_Copy = new ExtendedButton();
//			jButton_Copy.setText("複製(F11)");
////			jButton_Copy.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Copy.addActionListener(this);
//		}
//		return jButton_Copy;
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
////			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Delete.addActionListener(this);
//		}
//		return jButton_Delete;
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
//		/* Modified 2008/03/09 若月  */
//		/* --------------------------------------------------- */
////		if( ViewSettings.NORMALFRAME_MINSIZE_W > rect.width  ||
////		ViewSettings.NORMALFRAME_MINSIZE_H > rect.height )
////{
////	setBounds( rect.x,
////			   rect.y,
////			   ViewSettings.NORMALFRAME_MINSIZE_W,
////			   ViewSettings.NORMALFRAME_MINSIZE_H );
////}
//		/* --------------------------------------------------- */
//		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
//				ViewSettings.getFrameCommonHeight() > rect.height )
//		{
//			setBounds( rect.x,
//					   rect.y,
//					   ViewSettings.getFrameCommonWidth(),
//					   ViewSettings.getFrameCommonHeight() );
//		}
//		/* --------------------------------------------------- */
//	}
//
//	/**
//	 * This method initializes jButton_Edit
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Edit() {
//		if (jButton_Edit == null) {
//			jButton_Edit = new ExtendedButton();
//			jButton_Edit.setText("パターン編集(F9)");
//			jButton_Edit.setPreferredSize(new Dimension(110, 25));
////			jButton_Edit.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Edit.addActionListener(this);
//			jButton_Edit.setMnemonic(KeyEvent.VK_E);
//		}
//		return jButton_Edit;
//	}
//
//	/**
//	 * This method initializes jButton_NoteEdit
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_NoteEdit() {
//		if (jButton_NoteEdit == null) {
//			jButton_NoteEdit = new ExtendedButton();
//			jButton_NoteEdit.setText("パターン名編集(F7)");
//			jButton_NoteEdit.setPreferredSize(new Dimension(120, 25));
////			jButton_NoteEdit.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_NoteEdit.addActionListener(this);
//		}
//		return jButton_NoteEdit;
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
//

