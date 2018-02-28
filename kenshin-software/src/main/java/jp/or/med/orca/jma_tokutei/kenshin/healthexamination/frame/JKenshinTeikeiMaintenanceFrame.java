//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.BorderLayout;
//import javax.swing.JPanel;
//import javax.swing.JFrame;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.FlowLayout;
//import javax.swing.SwingConstants;
//import javax.swing.JLabel;
//import java.awt.CardLayout;
//import java.awt.Font;
//import java.awt.Color;
//import java.awt.Rectangle;
//import javax.swing.JTextField;
//import java.awt.event.*;
//
//import javax.swing.BoxLayout;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import java.awt.Dimension;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import javax.swing.BorderFactory;
//import javax.swing.JTextArea;
//
//public class JKenshinTeikeiMaintenanceFrame extends JFrame implements ActionListener
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
//	protected ExtendedButton jButton_Register = null;
//	protected ExtendedButton jButton_Del = null;
//	protected JPanel jPanel = null;
//	protected JPanel jPanel1 = null;
//	protected JPanel jPanel2 = null;
//	protected JPanel jPanel3 = null;
//	protected ExtendedButton jButton_ToLeft = null;
//	protected ExtendedButton jButton_ToRight = null;
//	protected JPanel jPanel11 = null;
//	protected JPanel jPanel12 = null;
//	protected JPanel jPanel_Left = null;
//	protected JPanel jPanel_Right = null;
//	//private JLabel jLabel1 = null;
//	private JLabel jLabel11 = null;
//	private JLabel jLabel12 = null;
//	private JTextArea jTeikeiArea= null;
//	/**
//	 * This is the default constructor
//	 */
//	public JKenshinTeikeiMaintenanceFrame() {
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
//		this.setContentPane(getJPanel_Content());
//		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//		this.setSize(ViewSettings.getFrameCommonSize());
//		this.setLocationRelativeTo( null );
//		this.setVisible(true);
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
//
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
//
//			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
//			gridBagConstraints8.gridy = 1;
//			gridBagConstraints8.gridx = 0;
//
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.gridy = 1;
//			gridBagConstraints5.gridx = 1;
//			gridBagConstraints5.weightx = 1.0D;
//			gridBagConstraints5.anchor = GridBagConstraints.EAST;
//
//			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
//			gridBagConstraints9.gridy = 1;
//			gridBagConstraints9.gridx = 2;
//			gridBagConstraints9.anchor = GridBagConstraints.EAST;
//
//			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
//			gridBagConstraints10.gridy = 1;
//			gridBagConstraints10.gridx = 3;
//
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints8);
//			jPanel_ButtonArea.add(getJButton_Add(), gridBagConstraints9);
//			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints5);
//			jPanel_ButtonArea.add(getJButton_Del(), gridBagConstraints10);
//
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
//			jButton_End.setText("戻る");
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
//			jLabel_MainExpl.setText("健診・問診結果データ入力画面の「総合コメント」、「結果（文字列）」欄でCTLキー+ENTERキーで表示される画面の");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("所見マスタメンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setEnabled(true);
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
//			jLabal_SubExpl.setText("定型文を編集します。[変更]、[削除]を行う場合、リストより選択して下さい。新規の場合、追加ボタンを押して下さい。");
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
//	 * This method initializes jButton_Register
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Register() {
//		if (jButton_Register == null) {
//			jButton_Register = new ExtendedButton();
//			jButton_Register.setText("新規追加");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Register.addActionListener(this);
//		}
//		return jButton_Register;
//	}
//	/**
//	 * This method initializes jButton_Register
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Add() {
//		if (jButton_Add == null) {
//			jButton_Add = new ExtendedButton();
//			jButton_Add.setText("登録");
//			jButton_Add.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Add.addActionListener(this);
//		}
//		return jButton_Add;
//	}
//	/**
//	 * This method initializes jButton_Register
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Del() {
//		if (jButton_Del == null) {
//			jButton_Del = new ExtendedButton();
//			jButton_Del.setText("削除");
//			jButton_Del.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Del.addActionListener(this);
//		}
//		return jButton_Del;
//	}
//	/**
//	 * This method initializes jPanel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel() {
//		if (jPanel == null) {
//			CardLayout cardLayout4 = new CardLayout();
//			cardLayout4.setHgap(10);
//			cardLayout4.setVgap(10);
//			jPanel = new JPanel();
//			jPanel.setLayout(cardLayout4);
//			jPanel.add(getJPanel12(), getJPanel12().getName());
//		}
//		return jPanel;
//	}
//
//	/**
//	 * This method initializes jPanel1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel1() {
//		if (jPanel1 == null) {
//			jPanel1 = new JPanel();
//			jPanel1.setName("jPanel1");
//			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.X_AXIS));
//		}
//		return jPanel1;
//	}
//
//	/**
//	 * This method initializes jPanel2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel2() {
//		if (jPanel2 == null) {
//
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.gridwidth = 1;
//			gridBagConstraints1.gridy = 0;
//
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridx = 0;
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.weighty = 1.0D;
//			gridBagConstraints.fill = GridBagConstraints.BOTH;
//			gridBagConstraints.gridy = 0;
//
//			jPanel2 = new JPanel();
//			jPanel2.setName("jPanel2");
//			jPanel2.setLayout(new GridBagLayout());
//			jPanel2.add(getJPanel11(), gridBagConstraints);
//			jPanel2.add(getJPanel3(), gridBagConstraints1);
//		}
//		return jPanel2;
//	}
//
//	/**
//	 * This method initializes jPanel3
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel3() {
//		if (jPanel3 == null) {
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.insets = new Insets(50, 20, 0, 20);
//			gridBagConstraints4.gridy = 1;
//			gridBagConstraints4.gridx = 0;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridx = 0;
//			gridBagConstraints3.insets = new Insets(0, 20, 0, 20);
//			gridBagConstraints3.gridy = 0;
//			jPanel3 = new JPanel();
//			jPanel3.setPreferredSize(new Dimension(80, 294));
//			jPanel3.setLayout(new GridBagLayout());
//			jPanel3.add(getJButton_ToLeft(), gridBagConstraints3);
//			jPanel3.add(getJButton_ToRight(), gridBagConstraints4);
//		}
//		return jPanel3;
//	}
//
//	/**
//	 * This method initializes jButton_ToLeft
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_ToLeft() {
//		if (jButton_ToLeft == null) {
//			jButton_ToLeft = new ExtendedButton();
//			jButton_ToLeft.setName("jButton2");
//			jButton_ToLeft.setFont(new Font("Dialog", Font.PLAIN, 24));
//			jButton_ToLeft.setText("↑");
//			jButton_ToLeft.addActionListener(this);
//		}
//		return jButton_ToLeft;
//	}
//
//	/**
//	 * This method initializes jButton_ToRight
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_ToRight() {
//		if (jButton_ToRight == null) {
//			jButton_ToRight = new ExtendedButton();
//			jButton_ToRight.setName("jButton3");
//			jButton_ToRight.setFont(new Font("Dialog", Font.PLAIN, 24));
//			jButton_ToRight.setText("↓");
//			jButton_ToRight.addActionListener(this);
//		}
//		return jButton_ToRight;
//	}
//
//	/**
//	 * This method initializes jPanel11
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel11() {
//		if (jPanel11 == null) {
//			jPanel11 = new JPanel();
//			jPanel11.setLayout(new BoxLayout(getJPanel11(), BoxLayout.Y_AXIS));
//			jPanel11.add(getJPanel_Left(), null);
//		}
//		return jPanel11;
//	}
//
//	/**
//	 * This method initializes jPanel12
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel12() {
//		if (jPanel12 == null) {
//			jPanel12 = new JPanel();
//			jPanel12.setLayout(new BorderLayout());
//			jPanel12.setName("jPanel12");
//			jPanel12.add(getJPanel1(), BorderLayout.NORTH);
//			jPanel12.add(getJPanel2(), BorderLayout.CENTER);
//			jPanel12.add(jTeikeiArea, BorderLayout.SOUTH);
//		}
//		return jPanel12;
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
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
//				ViewSettings.getFrameCommonHeight() > rect.height )
//		{
//			setBounds( rect.x,
//					   rect.y,
//					   ViewSettings.getFrameCommonWidth(),
//					   ViewSettings.getFrameCommonHeight() );
//		}
//	}
//
//	/**
//	 * This method initializes jPanel_Left
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Left() {
//		if (jPanel_Left == null) {
//			jLabel11 = new JLabel();
//			jLabel11.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel11.setText("<<定型文一覧の内容>>");
//
//			jLabel12 = new JLabel();
//			jLabel12.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel12.setText("<<選択行定型文の内容>>");
//
//			jTeikeiArea = new JTextArea();
//			jTeikeiArea.setRows(3);
//			jTeikeiArea.setLineWrap(true);
//			jTeikeiArea.setName("jTeikeiArea");
//			jTeikeiArea.setText("");
//			jTeikeiArea.setDisabledTextColor(Color.black);
//
//			jTeikeiArea.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jTeikeiArea.setText("<<\u5b9a\u578b\u5206\u4e00\u89a7\u306e\u5185\u5bb9>>");
//			jTeikeiArea.setSize(500,500);
//
//			jPanel_Left = new JPanel();
//			jPanel_Left.setLayout(new BorderLayout(2,1));
//			jPanel_Left.add(jLabel12, BorderLayout.SOUTH);
//			jPanel_Left.add(jLabel11, BorderLayout.NORTH);
//		}
//		return jPanel_Left;
//	}
//}