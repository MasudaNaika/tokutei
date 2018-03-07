package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.BoxLayout;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.BorderFactory;
// add h.yoshitama 2009/11/27
import java.awt.event.KeyEvent;


/**
 * 健診項目マスタメンテナンス
 *
 */
// edit h.yoshitama 2009/11/27
public class JKenshinMasterMaintenanceFrame extends JFrame implements ActionListener,ItemListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel_InputZone = null;
	protected JPanel jPanelBottom = null;
	protected ExtendedLabel jLabel = null;  //  @jve:decl-index=0:visual-constraint="-4,620"
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedButton jButton_Register = null;
	protected JPanel jPanel1 = null;
	protected ExtendedComboBox jComboBox_HokenjyaNumber = null;
	protected JPanel jPanel_Main = null;
//	// add s.inoue 2009/09/20
//	protected ExtendedLabel jLabelable = null;
//	protected ExtendedTextField jTextField_AbleListColumn = null;
	// add h.yoshitama 2009/09/25
	protected JPanel jPanel99 = null;
	protected JPanel jPanel999 = null;
	protected JPanel jPanel100 = null;
	protected ExtendedLabel jLabelhanrei = null;
	protected ExtendedLabel jLabelkanou = null;
	protected ExtendedLabel jLabel_ableExample = null;

	// add s.inoue 2010/02/25
	protected ExtendedButton jButton_Import = null;
	protected ExtendedButton jButton_Export = null;

	/**
	 * This is the default constructor
	 */
	public JKenshinMasterMaintenanceFrame() {
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
			jPanel_Content.add(getJPanelBottom(), BorderLayout.SOUTH);
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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			// del s.inoue 2010/03/15
//			gridBagConstraints2.weightx = 1.0D;
//			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridx = 3;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);

			// add s.inoue 2010/02/25
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints5.gridx = 2;

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			// add s.inoue 2010/02/25
			jPanel_ButtonArea.add(getJButton_Import(), gridBagConstraints4);
			jPanel_ButtonArea.add(getJButton_Export(), gridBagConstraints5);

			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints2);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints3);
		}
		return jPanel_ButtonArea;
	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			jButton_End = new ExtendedButton();
			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
			// edit h.yoshitama 2009/11/27
			jButton_End.setText("戻る(F1)");
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

	// edit h.yoshitama 2009/09/25
	/**
	 * This method initializes jPanel_NaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_NaviArea() {
		if (jPanel_NaviArea == null) {
			CardLayout cardLayout2 = new CardLayout();
			cardLayout2.setHgap(10);
			cardLayout2.setVgap(5);
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("下表の健診項目（必須フラグ、基準値、単価、備考）を入力後、登録します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");

			jLabel_Title = new TitleLabel("tokutei.kenshin-item-mastermaintenance.frame.title");
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
			// del h.yoshitama 2009/09/25
//			jLabal_SubExpl = new ExtendedLabel();
//			jLabal_SubExpl.setText("　");
//			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			GridLayout gridLayout1 = new GridLayout();
			// edit s.inoue 2009/10/01 1行から2行へ
			gridLayout1.setRows(2);
			jPanel_ExplArea2 = new JPanel();
			jPanel_ExplArea2.setName("jPanel4");
			jPanel_ExplArea2.setLayout(gridLayout1);
			jPanel_ExplArea2.add(jLabel_MainExpl, null);
			// del h.yoshitama 2009/09/25
//			jPanel_ExplArea2.add(jLabal_SubExpl, null);
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
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(10);
			cardLayout.setVgap(0);
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(cardLayout);
			jPanel_MainArea.add(getJPanel(), getJPanel().getName());
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setName("jPanel");
			jPanel.add(getJPanel_InputZone(), BorderLayout.NORTH);
			jPanel.add(getJPanel_Main(), BorderLayout.CENTER);
			// add h.yoshitama 2009/09/25
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel_InputZone
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_InputZone() {
		if (jPanel_InputZone == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("保険者番号");
			jPanel_InputZone = new JPanel();
			jPanel_InputZone.setLayout(new GridBagLayout());
			jPanel_InputZone.add(jLabel1, gridBagConstraints);
			jPanel_InputZone.add(getJComboBox_HokenjyaNumber(), gridBagConstraints1);
		}
		return jPanel_InputZone;
	}

	// edit h.yoshitama 2009/09/25
	/**
	 * This method initializes jPanelBottom
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelBottom() {
		if (jPanelBottom == null) {

			jPanelBottom = new JPanel();
			jPanelBottom.setLayout(new BoxLayout(getJPanelBottom(), BoxLayout.Y_AXIS));
			jPanelBottom.add(getJPanel_ButtonArea(), null);
		}
		return jPanelBottom;
	}

//	// add s.inoue 2009/09/20
//	private ExtendedTextField getJTextField_AbleListColumn() {
//		if (jTextField_AbleListColumn == null) {
//			jTextField_AbleListColumn = new ExtendedTextField("",	11,	ImeMode.IME_OFF);
//			jTextField_AbleListColumn.addActionListener(this);
//		}
//		return jTextField_AbleListColumn;
//	}

	/**
	 * This method initializes jButton_Import
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Import() {
		if (jButton_Import == null) {
			jButton_Import = new ExtendedButton();
			jButton_Import.setText("取込(F4)");
			jButton_Import.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Import.addActionListener(this);
		}
		return jButton_Import;
	}

	/**
	 * This method initializes jButton_Export
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Export() {
		if (jButton_Export == null) {
			jButton_Export = new ExtendedButton();
			jButton_Export.setText("書出(F5)");
			jButton_Export.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Export.addActionListener(this);
		}
		return jButton_Export;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			jButton_Register = new ExtendedButton();
			jButton_Register.setText("登録(F12)");
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	// add h.yoshitama 2009/09/25
	/**
	 * This method initializes jPanel12
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			jPanel1.add(getJPanel100(), BorderLayout.WEST);
		}
		return jPanel1;
	}

	// add h.yoshitama 2009/09/25
	/**
	 * This method initializes jPanel12
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel100() {
		if (jPanel100 == null) {
			jPanel100 = new JPanel();
			jPanel100.setLayout(new BorderLayout());
			jPanel100.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
			jPanel100.add(getJPanel99(), BorderLayout.NORTH);
			jPanel100.add(getJPanel999(), BorderLayout.SOUTH);
		}
		return jPanel100;
	}

	// edit h.yoshitama 2009/09/25
	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel99() {
		if (jPanel99 == null) {
//			FlowLayout flowLayout2 = new FlowLayout();
//			flowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
//			jPanel1 = new JPanel();
//			jPanel1.setLayout(flowLayout2);

			jLabel = new ExtendedLabel();
			// edit s.inoue 2009/09/20
			// jLabel.setText("※必須フラグ　…　１：特定健診　２：詳細な健診　３：追加の健診<br>※基準値は整数部分7桁、小数点以下3桁まで入力できます。");
			jLabel.setText("※必須フラグ　…　１：特定健診　２：詳細な健診　３：追加の健診");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel99 = new JPanel();
			jPanel99.setLayout(new GridBagLayout());
			jPanel99.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			jPanel99.setOpaque(false);

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 0;

			jPanel99.add(jLabel, gridBagConstraints1);

		}
		return jPanel99;
	}
	// add h.yoshitama 2009/09/25
	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel999() {
		if (jPanel999 == null) {

			jPanel999 = new JPanel();
			jPanel999.setLayout(new GridBagLayout());
			jPanel999.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
			jPanel999.setOpaque(false);

			// add h.yoshitama 2009/09/25
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			jLabelhanrei = new ExtendedLabel();
			jLabelhanrei.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabelhanrei.setPreferredSize(new Dimension(50, 16));
			jLabelhanrei.setText("凡例");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(0, 10, 0, 180);
			jLabelkanou = new ExtendedLabel();
			jLabelkanou.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelkanou.setText("入力項目");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.insets = new Insets(0, 20, 0, 0);
			jLabel_ableExample = new ExtendedLabel();
			jLabel_ableExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_ableExample.setPreferredSize(new Dimension(50, 20));
			jLabel_ableExample.setOpaque(true);

			// add h.yoshitama 2009/09/25
			jPanel999.add(jLabel_ableExample, gridBagConstraints6);
			jPanel999.add(jLabelkanou, gridBagConstraints5);
			jPanel999.add(jLabelhanrei, gridBagConstraints4);
		}
		return jPanel999;
	}


	public void actionPerformed( ActionEvent e )
	{
	}

	public void itemStateChanged(ItemEvent e)
	{

	}
	// add h.yoshitama 2009/11/27
	public void keyPressed(KeyEvent arg0) {
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
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

	/**
	 * This method initializes jComboBox_HokenjyaNumber
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_HokenjyaNumber() {
		if (jComboBox_HokenjyaNumber == null) {
			jComboBox_HokenjyaNumber = new ExtendedComboBox();
			jComboBox_HokenjyaNumber.setPreferredSize(new Dimension(300, 25));
			jComboBox_HokenjyaNumber.addItemListener(this);
		}
		return jComboBox_HokenjyaNumber;
	}

	/**
	 * This method initializes jPanel_Main
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Main() {
		if (jPanel_Main == null) {
			jPanel_Main = new JPanel();
			jPanel_Main.setLayout(new BorderLayout());
			jPanel_Main.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		}
		return jPanel_Main;
	}
}
