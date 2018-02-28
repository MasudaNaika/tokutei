package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import javax.swing.BorderFactory;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * ユーザビリティメンテナンス画面
 */
public class JUsabilityFrame extends JFrame implements ActionListener,ItemListener,KeyListener,TreeSelectionListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected JLabel jLabel_Title = null;
	protected JLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected ExtendedButton jButton_Register = null;
	// add s.inoue 2009/11/25
	// protected ExtendedComboBox jCombobox_LookFeel = null;
	protected ExtendedLabel jLabelMainArea1 = null;
//	protected JPanel jPanel_LookFeel = null;
	protected JPanel jPanel_Usability = null;

	/**
	 * This is the default constructor
	 */
	public JUsabilityFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel_Content());
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(ViewSettings.getFrameCommonSize());
		this.setLocationRelativeTo( null );
		this.setVisible(true);
	}

	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Content() {
		if (jPanel_Content == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);
			jPanel_Content = new JPanel();
			jPanel_Content.setLayout(borderLayout);
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel_MainArea(), BorderLayout.CENTER);
		}
		return jPanel_Content;
	}

	/**
	 * This method initializes jPanel_ButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ButtonArea() {
		if (jPanel_ButtonArea == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.gridx = 1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Add(), gridBagConstraints);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints3);
		}
		return jPanel_ButtonArea;
	}

//	/**
//	 * This method initializes jCombobox_LookFeel
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_LookFeel() {
//		if (jCombobox_LookFeel == null) {
//			jCombobox_LookFeel = new ExtendedComboBox();
//			jCombobox_LookFeel.setPreferredSize(new Dimension(260, 20));
//			jCombobox_LookFeel.addItemListener(this);
//		}
//		return jCombobox_LookFeel;
//	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			jButton_End = new ExtendedButton();
			jButton_End.setActionCommand("終了");
			jButton_End.setText("戻る(F1)");
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

	/**
	 * This method initializes jPanel_NaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_NaviArea() {
		if (jPanel_NaviArea == null) {
			CardLayout cardLayout2 = new CardLayout();
			cardLayout2.setHgap(10);
			cardLayout2.setVgap(10);
			jLabel_MainExpl = new JLabel();
			jLabel_MainExpl.setText("ユーザビリティのメンテナンスを行います。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new JLabel();
			jLabel_Title.setText("ユーザビリティメンテナンス");
			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
			jLabel_Title.setBackground(new Color(153, 204, 255));
			jLabel_Title.setForeground(new Color(51, 51, 51));
			jLabel_Title.setOpaque(true);
			jLabel_Title.setName("jLabel");
			jPanel_NaviArea = new JPanel();
			jPanel_NaviArea.setLayout(cardLayout2);
			jPanel_NaviArea.add(getJPanel_TitleArea(), getJPanel_TitleArea().getName());
		}
		return jPanel_NaviArea;
	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TitleArea() {
		if (jPanel_TitleArea == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setHgap(0);
			gridLayout.setColumns(0);
			gridLayout.setVgap(10);
			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(gridLayout);
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, null);
			jPanel_TitleArea.add(getJPanel_ExplArea1(), null);
		}
		return jPanel_TitleArea;
	}

	/**
	 * This method initializes jPanel_ExplArea2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ExplArea2() {
		if (jPanel_ExplArea2 == null) {
			jLabal_SubExpl = new JLabel();
			jLabal_SubExpl.setText(" ");
			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			jPanel_ExplArea2 = new JPanel();
			jPanel_ExplArea2.setName("jPanel4");
			jPanel_ExplArea2.setLayout(gridLayout1);
			jPanel_ExplArea2.add(jLabel_MainExpl, null);
			jPanel_ExplArea2.add(jLabal_SubExpl, null);
		}
		return jPanel_ExplArea2;
	}

	/**
	 * This method initializes jPanel_ExplArea1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ExplArea1() {
		if (jPanel_ExplArea1 == null) {
			CardLayout cardLayout1 = new CardLayout();
			cardLayout1.setHgap(20);
			jPanel_ExplArea1 = new JPanel();
			jPanel_ExplArea1.setLayout(cardLayout1);
			jPanel_ExplArea1.add(getJPanel_ExplArea2(), getJPanel_ExplArea2().getName());
		}
		return jPanel_ExplArea1;
	}

	/**
	 * This method initializes jPanel_MainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_MainArea() {
		if (jPanel_MainArea == null) {
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new BorderLayout());
			jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			jPanel_MainArea.setOpaque(false);

//			jPanel_MainArea.add(getJPanelLookFeel(),BorderLayout.NORTH);
			jPanel_MainArea.add(getJPanelUsability(),BorderLayout.NORTH);
		}
		return jPanel_MainArea;
	}

//	/**
//	 * This method initializes jPanel9
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelLookFeel() {
//		if (jPanel_LookFeel == null) {
//			jPanel_LookFeel = new JPanel();
//			jPanel_LookFeel.setLayout(new GridBagLayout());
//			jPanel_LookFeel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
//			jPanel_LookFeel.setOpaque(false);
//
//			jLabelMainArea1 = new ExtendedLabel();
//			jLabelMainArea1.setText("<b>Look&Feel</b>");
//
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.anchor = GridBagConstraints.WEST;
//			gridBagConstraints1.fill = GridBagConstraints.WEST;
//			gridBagConstraints1.weightx = 1.0D;
//			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.gridy = 0;
//
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.anchor = GridBagConstraints.WEST;
//			gridBagConstraints2.gridx = 0;
//			gridBagConstraints2.gridy = 0;
//
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.insets = new Insets(10, 0, 0, 0);
//			gridBagConstraints3.gridx = 0;
//			gridBagConstraints3.gridy = 1;
//			gridBagConstraints3.gridwidth = 4;
//			gridBagConstraints3.weightx = 1.0D;
//			gridBagConstraints3.anchor = GridBagConstraints.WEST;
//
//			// add s.inoue 2009/11/25
////			jPanel_LookFeel.add(getJComboBox_LookFeel(),gridBagConstraints1);
//			jPanel_LookFeel.add(jLabelMainArea1,gridBagConstraints2);
//			jPanel_LookFeel.add(getJPanelUsability(),gridBagConstraints3);
//		}
//		return jPanel_LookFeel;
//	}

	/**
	 * This method initializes jPanel9
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelUsability() {
		if (jPanel_Usability == null) {
			jPanel_Usability = new JPanel();
			jPanel_Usability.setLayout(new GridBagLayout());
			jPanel_Usability.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			jPanel_Usability.setOpaque(false);
			// edit s.inoue 2010/04/06
			jPanel_Usability.addKeyListener(this);
//			jLabelMainArea1 = new ExtendedLabel();
//			jLabelMainArea1.setText("<b>Look&Feel</b>");
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.anchor = GridBagConstraints.WEST;
//			gridBagConstraints1.fill = GridBagConstraints.WEST;
//			gridBagConstraints1.weightx = 1.0D;
//			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.gridy = 0;

//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.anchor = GridBagConstraints.WEST;
//			gridBagConstraints2.gridx = 0;
//			gridBagConstraints2.gridy = 0;
			// add s.inoue 2009/11/25
//			jPanel_Usability.add(getJComboBox_LookFeel(),gridBagConstraints1);
//			jPanel_Usability.add(jLabelMainArea1,gridBagConstraints2);
		}
		return jPanel_Usability;
	}
	/**
	 * This method initializes jButton_Add
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Add() {
		if (jButton_Register == null) {
			jButton_Register = new ExtendedButton();
			jButton_Register.setText("登録(F12)");
//			jButton_Add.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	public void actionPerformed( ActionEvent e )
	{

	}

	/*
	 * FrameSize Control
	 */
	public void validate()
	{
		Rectangle rect = getBounds();

		super.validate();

		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
				ViewSettings.getFrameCommonHeight() > rect.height )
		{
			setBounds( rect.x,
					   rect.y,
					   ViewSettings.getFrameCommonWidth(),
					   ViewSettings.getFrameCommonHeight() );
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
