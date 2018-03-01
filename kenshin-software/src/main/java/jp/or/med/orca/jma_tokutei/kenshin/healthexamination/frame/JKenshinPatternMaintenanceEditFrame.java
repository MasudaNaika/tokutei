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
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
//
//import javax.swing.BorderFactory;
//
//public class JKenshinPatternMaintenanceEditFrame extends JFrame implements ActionListener,KeyListener
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
//	protected ExtendedButton jButton_Cancel = null;
//	protected ExtendedButton jButton_Register = null;
//	protected JPanel jPanel = null;
//	protected JPanel jPanel1 = null;
//	protected JLabel jLabel1 = null;
//	protected JPanel jPanel2 = null;
//	protected JPanel jPanel3 = null;
//	protected ExtendedButton jButton_ToLeft = null;
//	protected ExtendedButton jButton_ToRight = null;
//	protected JPanel jPanel10 = null;
//	protected JPanel jPanel11 = null;
//	protected JPanel jPanel12 = null;
//	protected JLabel jLabel = null;
//	protected JLabel jLabel2 = null;
//	protected JTextField jTextField_PatternName = null;
//	protected JPanel jPanel_Left = null;
//	protected JPanel jPanel_Right = null;
//	protected ExtendedButton jButton_Clear = null;
//
//	// 20090707
//	protected ExtendedButton jButton_ToTop = null;
//	protected ExtendedButton jButton_ToDown = null;
//	protected JLabel jLabel_Info1 = null;
//	protected JLabel jLabel_Info2 = null;
//	protected JLabel jLabel_Info3 = null;
//	protected JLabel jLabel_Info4 = null;
//
//	/**
//	 * This is the default constructor
//	 */
//	public JKenshinPatternMaintenanceEditFrame() {
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
//			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
//			gridBagConstraints8.gridy = 0;
//			gridBagConstraints8.gridx = 0;
//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridy = 0;
//			gridBagConstraints7.gridx = 4;
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridy = 0;
//			gridBagConstraints6.weightx = 1.0D;
//			gridBagConstraints6.anchor = GridBagConstraints.EAST;
//			gridBagConstraints6.gridx = 2;
//			gridBagConstraints6.insets = new Insets(0, 5, 0, 0);
//
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints5.gridy = 0;
//			gridBagConstraints5.gridx = 3;
//
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			jPanel_ButtonArea.add(getJButton_Cancel(), gridBagConstraints7);
//			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints5);
//			jPanel_ButtonArea.add(getJButton_Clear(), gridBagConstraints6);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints8);
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
//			jButton_End.setMnemonic(KeyEvent.VK_R);
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
//			jLabel_MainExpl.setText("右表<<健診項目マスタの内容>>から登録する健診項目を選択し、左矢印(←)ボタンを押下し左表<<選択パターンの内容>>");
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
//			//jLabel_Title = new JLabel("tokutei.kenshinpattern-mastermaintenance.frame.title");
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
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
//			jLabal_SubExpl.setText("へ追加後、登録ボタンを押下してください。項目順を変更する場合は、上(↑)下(↓)移動して下さい。");
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
//	// s.inoue
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
//	 * This method initializes jButton_Cancel
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Cancel() {
//		if (jButton_Cancel == null) {
//			jButton_Cancel = new ExtendedButton();
//			jButton_Cancel.setText("キャンセル");
//			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Cancel.setVisible(false);
//			jButton_Cancel.addActionListener(this);
//		}
//		return jButton_Cancel;
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
//			jButton_Register.setText("登録(F12)");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Register.addActionListener(this);
//		}
//		return jButton_Register;
//	}
//
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
//			jLabel1 = new JLabel();
//			jLabel1.setText("パターン名");
//			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jPanel1 = new JPanel();
//			jPanel1.setName("jPanel1");
//			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.X_AXIS));
//			jPanel1.add(jLabel1, null);
//			jPanel1.add(getJTextField_PatternName(), null);
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
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.insets = new Insets(0, 0, 0, 1);
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.weightx = 1.0D;
//			gridBagConstraints2.weighty = 1.0D;
//			gridBagConstraints2.fill = GridBagConstraints.BOTH;
//			gridBagConstraints2.gridx = 2;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.gridwidth = 1;
//			gridBagConstraints1.gridy = 0;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridx = 0;
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.weighty = 1.0D;
//			gridBagConstraints.fill = GridBagConstraints.BOTH;
//			gridBagConstraints.gridy = 0;
//			jPanel2 = new JPanel();
//			jPanel2.setName("jPanel2");
//			jPanel2.setLayout(new GridBagLayout());
//			jPanel2.add(getJPanel11(), gridBagConstraints);
//			jPanel2.add(getJPanel3(), gridBagConstraints1);
//			jPanel2.add(getJPanel10(), gridBagConstraints2);
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
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.insets = new Insets(20, 20, 0, 20);
//			gridBagConstraints2.gridx = 0;
//			gridBagConstraints2.gridy = 0;
//
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.insets = new Insets(0, 20, 0, 20);
//			gridBagConstraints3.gridx = 0;
//			gridBagConstraints3.gridy = 2;
//
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.insets = new Insets(0, 20, 0, 20);
//			gridBagConstraints4.gridx = 0;
//			gridBagConstraints4.gridy = 4;
//
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.insets = new Insets(0, 20, 0, 20);
//			gridBagConstraints5.gridx = 0;
//			gridBagConstraints5.gridy = 6;
//
//			// add s.inoue 2009/12/02
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.insets = new Insets(0, 0, 20, 0);
//			gridBagConstraints6.gridx = 0;
//			gridBagConstraints6.gridy = 1;
//			// add s.inoue 2009/12/02
//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.insets = new Insets(0, 0, 20, 0);
//			gridBagConstraints7.gridx = 0;
//			gridBagConstraints7.gridy = 3;
//
//			// add s.inoue 2009/12/02
//			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
//			gridBagConstraints8.insets = new Insets(0, 0, 20, 0);
//			gridBagConstraints8.gridx = 0;
//			gridBagConstraints8.gridy = 5;
//
//			// add s.inoue 2009/12/02
//			GridBagConstraints gridBagConstraints9= new GridBagConstraints();
//			gridBagConstraints9.insets = new Insets(0, 0, 20, 0);
//			gridBagConstraints9.gridx = 0;
//			gridBagConstraints9.gridy = 7;
//
//			jPanel3 = new JPanel();
//			jPanel3.setPreferredSize(new Dimension(80, 300));
//			jPanel3.setLayout(new GridBagLayout());
//			jPanel3.add(getJButton_ToLeft(), gridBagConstraints3);
//			jPanel3.add(getJButton_ToRight(), gridBagConstraints4);
//			// 20090707
//			jPanel3.add(getJButton_ToTop(), gridBagConstraints2);
//			jPanel3.add(getJButton_ToDown(), gridBagConstraints5);
//
//			// add s.inoue 2009/12/02
//			jPanel3.add(getJLabel_Info1(),gridBagConstraints6);
//			jPanel3.add(getJLabel_Info2(),gridBagConstraints7);
//			jPanel3.add(getJLabel_Info3(),gridBagConstraints8);
//			jPanel3.add(getJLabel_Info4(),gridBagConstraints9);
//		}
//		return jPanel3;
//	}
//	// 20090707
//	/**
//	 * This method initializes jButton_ToTop
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_ToTop() {
//		if (jButton_ToTop == null) {
//			jButton_ToTop = new ExtendedButton();
//			jButton_ToTop.setName("jButton1");
//			jButton_ToTop.setFont(new Font("Dialog", Font.PLAIN, 24));
//			jButton_ToTop.setText("↑");
//			jButton_ToTop.addActionListener(this);
//			jButton_ToTop.setMnemonic(KeyEvent.VK_W);
//		}
//		return jButton_ToTop;
//	}
//
//	/**
//	 * This method initializes jButton_ToTop
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_ToDown() {
//		if (jButton_ToDown == null) {
//			jButton_ToDown = new ExtendedButton();
//			jButton_ToDown.setName("jButton4");
//			jButton_ToDown.setFont(new Font("Dialog", Font.PLAIN, 24));
//			jButton_ToDown.setText("↓");
//			jButton_ToDown.addActionListener(this);
//			jButton_ToDown.setMnemonic(KeyEvent.VK_Z);
//		}
//		return jButton_ToDown;
//	}
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
//			jButton_ToLeft.setText("←");
//			jButton_ToLeft.addActionListener(this);
//			jButton_ToLeft.setMnemonic(KeyEvent.VK_A);
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
//			jButton_ToRight.setText("→");
//			jButton_ToRight.addActionListener(this);
//			jButton_ToRight.setMnemonic(KeyEvent.VK_S);
//		}
//		return jButton_ToRight;
//	}
//
//	/**
//	 * This method initializes jButton_ToTop
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private JLabel getJLabel_Info1() {
//		if (jLabel_Info1 == null) {
//			jLabel_Info1 = new JLabel();
//			jLabel_Info1.setFont(new Font("Dialog", Font.PLAIN, 10));
//			jLabel_Info1.setText("(Alt+W)");
//		}
//		return jLabel_Info1;
//	}
//
//	/**
//	 * This method initializes jButton_ToTop
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private JLabel getJLabel_Info2() {
//		if (jLabel_Info2 == null) {
//			jLabel_Info2 = new JLabel();
//			jLabel_Info2.setFont(new Font("Dialog", Font.PLAIN, 10));
//			jLabel_Info2.setText("(Alt+A)");
//		}
//		return jLabel_Info2;
//	}
//
//	/**
//	 * This method initializes jButton_ToTop
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private JLabel getJLabel_Info3() {
//		if (jLabel_Info3 == null) {
//			jLabel_Info3 = new JLabel();
//			jLabel_Info3.setFont(new Font("Dialog", Font.PLAIN, 10));
//			jLabel_Info3.setText("(Alt+S)");
//		}
//		return jLabel_Info3;
//	}
//
//	/**
//	 * This method initializes jButton_ToTop
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private JLabel getJLabel_Info4() {
//		if (jLabel_Info4 == null) {
//			jLabel_Info4 = new JLabel();
//			jLabel_Info4.setFont(new Font("Dialog", Font.PLAIN, 10));
//			jLabel_Info4.setText("(Alt+Z)");
//		}
//		return jLabel_Info4;
//	}
//	/**
//	 * This method initializes jPanel10
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel10() {
//		if (jPanel10 == null) {
//			jLabel2 = new JLabel();
//			jLabel2.setText("<<健診項目マスタの内容>>");
//			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jPanel10 = new JPanel();
//			jPanel10.setLayout(new BoxLayout(getJPanel10(), BoxLayout.Y_AXIS));
//			jPanel10.add(jLabel2, null);
//			jPanel10.add(getJPanel_Right(), null);
//		}
//		return jPanel10;
//	}
//
//	/**
//	 * This method initializes jPanel11
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel11() {
//		if (jPanel11 == null) {
//			jLabel = new JLabel();
//			jLabel.setText("<<選択パターンの内容>>");
//			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jPanel11 = new JPanel();
//			jPanel11.setLayout(new BoxLayout(getJPanel11(), BoxLayout.Y_AXIS));
//			jPanel11.add(jLabel, null);
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
//	 * This method initializes jTextField_PatternName
//	 *
//	 * @return javax.swing.JTextField
//	 */
//	private JTextField getJTextField_PatternName() {
//		if (jTextField_PatternName == null) {
//			jTextField_PatternName = new JTextField();
//		}
//		return jTextField_PatternName;
//	}
//
//	/**
//	 * This method initializes jPanel_Left
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Left() {
//		if (jPanel_Left == null) {
//			jPanel_Left = new JPanel();
//			jPanel_Left.setLayout(new BorderLayout());
//		}
//		return jPanel_Left;
//	}
//
//	/**
//	 * This method initializes jPanel_Right
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Right() {
//		if (jPanel_Right == null) {
//			jPanel_Right = new JPanel();
//			jPanel_Right.setLayout(new BorderLayout());
//		}
//		return jPanel_Right;
//	}
//
//	/**
//	 * This method initializes jButton_Clear
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Clear() {
//		if (jButton_Clear == null) {
//			jButton_Clear = new ExtendedButton();
//			jButton_Clear.setText("操作取消(F11)");
//			jButton_Clear.setPreferredSize(new Dimension(120, 25));
//			jButton_Clear.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Clear.addActionListener(this);
//		}
//		return jButton_Clear;
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