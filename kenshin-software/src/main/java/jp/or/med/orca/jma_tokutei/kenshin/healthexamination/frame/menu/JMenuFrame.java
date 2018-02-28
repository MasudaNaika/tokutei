package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedToggleButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;

public class JMenuFrame extends JFrame implements ActionListener,WindowListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected ExtendedLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_main = null;
	protected ExtendedButton jButton_InputJyusinken = null;

	protected ExtendedButton jButton_InputMonshinKekka = null;
	protected ExtendedButton jButton_ImportData = null;
	protected ExtendedButton jButton_ShowHantei = null;
	// edit ver2 s.inoue 2009/08/03
	// jButton_OutputToHL7→jButton_OutputNitiji
	protected ExtendedButton jButton_OutputNitiji = null;
	protected ExtendedButton jButton_OutputGetuji = null;

	protected ExtendedButton jButton_MasterMaintenance = null;
	protected ExtendedButton jButton_SystemMaintenance = null;
	protected ExtendedToggleButton jButton_Version = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedLabel jLabel2 = null;
	protected ExtendedLabel jLabel3 = null;
	protected ExtendedLabel jLabel4 = null;
	// edit ver2 s.inoue 2009/08/03
	protected ExtendedLabel jLabel5 = null;
	protected ExtendedButton jButton_ReturnLogin = null;
	/**
	 * This is the default constructor
	 */
	public JMenuFrame() {
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
			// edit
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
			// jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
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
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints13.gridy = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.gridx = 3;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridx = 0;
			// edit 20110317
			gridBagConstraints10.insets = new Insets(0, 0, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints10);
			jPanel_ButtonArea.add(getJButton_Version(), gridBagConstraints12);
			jPanel_ButtonArea.add(getJButton_ReturnLogin(), gridBagConstraints13);
		}
		return jPanel_ButtonArea;
	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			// edit 20110317
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setPreferredSize(new Dimension(95, 25));
//			jButton_End.setText("終了(F1)");
//			jButton_End.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Exit);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","終了(E)","終了(ALT+E)",KeyEvent.VK_E,icon);
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
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("ボタンを押して、各機能を実行します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel("tokutei.mainmenu.frame.title");
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
			jLabal_SubExpl = new ExtendedLabel();
			jLabal_SubExpl.setText("　");
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
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.gridx = 0;

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.NORTH;
			gridBagConstraints1.gridy = 0;

			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new GridBagLayout());
//			jPanel_MainArea.add(getJPanel_DrawArea(), gridBagConstraints9);
			jPanel_MainArea.add(getJPanel_Left(), gridBagConstraints1);
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_Left
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Left() {
		if (jPanel_main == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.gridwidth = 2;
			// edit ver2 s.inoue 2009/08/03
			// gridBagConstraints51.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints51.insets = new Insets(1, 0, 1, 0);
			gridBagConstraints51.gridy = 5;
			jLabel4 = new ExtendedLabel();
			jLabel4.setText("↓");
			jLabel4.setFont(new Font("Dialog", Font.BOLD, 18));

			// edit ver2 s.inoue 2009/08/03
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 0;
			gridBagConstraints61.gridwidth = 2;
			gridBagConstraints61.insets = new Insets(1, 0, 1, 0);
			gridBagConstraints61.gridy = 7;
//			jLabel5 = new ExtendedLabel();
//			jLabel5.setText("↓");
//			jLabel5.setFont(new Font("Dialog", Font.BOLD, 18));

			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 1;
			// edit ver2 s.inoue 2009/08/03
			// gridBagConstraints41.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints41.insets = new Insets(1, 0, 1, 0);
			gridBagConstraints41.gridy = 3;
			jLabel3 = new ExtendedLabel();
			jLabel3.setText("↓");
			jLabel3.setFont(new Font("Dialog", Font.BOLD, 18));
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			// edit ver2 s.inoue 2009/08/03
			// gridBagConstraints31.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints31.insets = new Insets(1, 0, 1, 0);

			gridBagConstraints31.gridy = 3;
			jLabel2 = new ExtendedLabel();
			jLabel2.setText("↓");
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 18));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			// gridBagConstraints2.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints2.insets = new Insets(1, 0, 1, 0);
			gridBagConstraints2.gridy = 1;
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("↓");
			jLabel1.setFont(new Font("Dialog", Font.BOLD, 18));
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			// edit ver2 s.inoue 2009/08/03
			// gridBagConstraints11.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints11.insets = new Insets(1, 0, 1, 0);
			gridBagConstraints11.gridy = 1;
			jLabel = new ExtendedLabel();
			jLabel.setText("↓");
			jLabel.setFont(new Font("Dialog", Font.BOLD, 18));

			// edit ver2 s.inoue 2009/08/03
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridwidth = 2;
			// edit ver2 s.inoue 2009/08/03
			// gridBagConstraints12.insets = new Insets(5, 0, 5, 0);
			gridBagConstraints12.insets = new Insets(1, 0, 1, 0);
			gridBagConstraints12.gridy = 7;
			jLabel5 = new ExtendedLabel();
			jLabel5.setText("↓");
			jLabel5.setFont(new Font("Dialog", Font.BOLD, 18));

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			// edit ver2 s.inoue 2009/08/03 50→10
			gridBagConstraints8.insets = new Insets(20, 5, 0, 0);
			// edit ver2 s.inoue 2009/08/03 7 → 9
			gridBagConstraints8.gridy = 9;

			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			// edit ver2 s.inoue 2009/08/03 50→20
			gridBagConstraints7.insets = new Insets(20, 0, 0, 5);
			// edit ver2 s.inoue 2009/08/03 7 → 9
			gridBagConstraints7.gridy = 9;

			// edit ver2 s.inoue 2009/08/03
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridwidth = 2;
			gridBagConstraints9.gridy = 8;

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridy = 6;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridwidth = 2;
			gridBagConstraints6.gridy = 4;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints4.gridy = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.gridy = 0;
			jPanel_main = new JPanel();
			jPanel_main.setLayout(new GridBagLayout());
			jPanel_main.add(getJButton_InputJyusinken(), gridBagConstraints3);
			jPanel_main.add(getJButton_InputMonshinKekka(), gridBagConstraints4);
			jPanel_main.add(getJButton_ImportData(), gridBagConstraints5);
			jPanel_main.add(getJButton_ShowHantei(), gridBagConstraints6);
			// edit ver2 s.inoue 2009/08/03
			jPanel_main.add(getJButton_OutputNitiji(), gridBagConstraints);
			jPanel_main.add(getJButton_OutputGetuji(), gridBagConstraints9);

			jPanel_main.add(getJButton_MasterMaintenance(), gridBagConstraints7);
			jPanel_main.add(getJButton_SystemMaintenance(), gridBagConstraints8);
			jPanel_main.add(jLabel, gridBagConstraints11);
			jPanel_main.add(jLabel1, gridBagConstraints2);
			jPanel_main.add(jLabel2, gridBagConstraints31);
			jPanel_main.add(jLabel3, gridBagConstraints41);
			jPanel_main.add(jLabel4, gridBagConstraints51);
			// edit ver2 s.inoue 2009/08/03
			jPanel_main.add(jLabel5, gridBagConstraints61);

		}
		return jPanel_main;
	}

	private ExtendedButton getJButton_InputJyusinken() {
		if (jButton_InputJyusinken == null) {
			// openswing s.inoue 2010/11/26
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/jyushinken.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_InputJyusinken = new ExtendedButton("１．受診券入力（個人情報登録）(1)",icon);
//			jButton_InputJyusinken.setText("１．受診券入力（個人情報登録）(1)");
//			jButton_InputJyusinken.setPreferredSize(new Dimension(500, 50));
//			jButton_InputJyusinken.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_InputJyusinken.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_InputJyusinken.addActionListener(this);
			jButton_InputJyusinken.addKeyListener(this);
			jButton_InputJyusinken.setMnemonic(KeyEvent.VK_1);
		}
		return jButton_InputJyusinken;
	}

	/**
	 * This method initializes jButton_InputMonshinKekka
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_InputMonshinKekka() {
		if (jButton_InputMonshinKekka == null) {
			// openswing s.inoue 2010/11/26
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/kekkaInput.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);
			jButton_InputMonshinKekka = new ExtendedButton("２-１．健診・問診結果データ入力(2)",icon);

//			jButton_InputMonshinKekka.setPreferredSize(new Dimension(245, 50));
			jButton_InputMonshinKekka.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_InputMonshinKekka.addActionListener(this);
			jButton_InputMonshinKekka.setMnemonic(KeyEvent.VK_2);
			// openswing s.inoue 2011/02/16
//			jButton_InputMonshinKekka = new ExtendedOpenGenericButton(
//					"jButton_InputMonshinKekka",
//					"２-１．健診・問診結果データ入力(2)",
//					"２-１．健診・問診結果データ入力(2)",
//					KeyEvent.VK_2,
//					icon,
//					new Dimension(245,80)
//					);
		}
		return jButton_InputMonshinKekka;
	}

	/**
	 * This method initializes jButton_ImportData
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_ImportData() {
		if (jButton_ImportData == null) {
			// openswing s.inoue 2010/11/26
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/kekkaImport.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_ImportData = new ExtendedButton("２-２．検査データ、HL7取り込み(3)",icon);

//			jButton_ImportData = new ExtendedButton();
//			jButton_ImportData.setText("２-２．検査データ取り込み(3)");
//			jButton_ImportData.setPreferredSize(new Dimension(245, 50));
			// edit ver2 s.inoue 2009/04/17 9999
			//jButton_ImportData.setEnabled(false);
			jButton_ImportData.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_ImportData.addActionListener(this);
			jButton_ImportData.addKeyListener(this);
			jButton_ImportData.setMnemonic(KeyEvent.VK_3);
		}
		return jButton_ImportData;
	}

	/**
	 * This method initializes jButton_ShowHantei
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_ShowHantei() {
		if (jButton_ShowHantei == null) {
			// openswing s.inoue 2010/11/26
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/metaboHantei.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_ShowHantei = new ExtendedButton("３．メタボリックシンドローム判定・階層化(4)",icon);

//			jButton_ShowHantei = new ExtendedButton();
//			jButton_ShowHantei.setText("３．メタボリックシンドローム判定・階層化(4)");
//			jButton_ShowHantei.setPreferredSize(new Dimension(500, 50));
			// jButton_ShowHantei.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_ShowHantei.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_ShowHantei.addActionListener(this);
			jButton_ShowHantei.setMnemonic(KeyEvent.VK_4);
		}
		return jButton_ShowHantei;
	}

	/**
	 * This method initializes jButton_OutputToHL7
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_OutputNitiji() {
		if (jButton_OutputNitiji == null) {
			// openswing s.inoue 2010/11/26
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/nitiji.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_OutputNitiji = new ExtendedButton("４．日次処理（請求）(5)",icon);
//			jButton_OutputNitiji = new ExtendedButton();
//			jButton_OutputNitiji.setText("４．日次処理（請求）(5)");
//			jButton_OutputNitiji.setPreferredSize(new Dimension(500, 50));
			// jButton_OutputNitiji.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_OutputNitiji.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_OutputNitiji.addActionListener(this);
			jButton_OutputNitiji.setMnemonic(KeyEvent.VK_5);
		}
		return jButton_OutputNitiji;
	}

	/**
	 * This method initializes jButton_OutputGetuji
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_OutputGetuji() {
		if (jButton_OutputGetuji == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/getuji.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_OutputGetuji = new ExtendedButton("５．月次処理（請求確定/HL7出力）(6)",icon);
//			jButton_OutputGetuji = new ExtendedButton();
//			jButton_OutputGetuji.setText("５．月次処理（請求確定/HL7出力）(6)");
//			jButton_OutputGetuji.setPreferredSize(new Dimension(500, 50));
			// jButton_OutputGetuji.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_OutputGetuji.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_OutputGetuji.addActionListener(this);
			jButton_OutputGetuji.setMnemonic(KeyEvent.VK_6);
		}
		return jButton_OutputGetuji;
	}

	/**
	 * This method initializes jButton_MasterMaintenance
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_MasterMaintenance() {
		if (jButton_MasterMaintenance == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/masterMainte.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_MasterMaintenance = new ExtendedButton("６．マスタメンテナンス(7)",icon);
//			jButton_MasterMaintenance = new ExtendedButton();
//			jButton_MasterMaintenance.setText("６．マスタメンテナンス(7)");
//			jButton_MasterMaintenance.setPreferredSize(new Dimension(245, 50));
			// jButton_MasterMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_MasterMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_MasterMaintenance.addActionListener(this);
			jButton_MasterMaintenance.setMnemonic(KeyEvent.VK_7);
		}
		return jButton_MasterMaintenance;
	}

	/**
	 * This method initializes jButton_SystemMaintenance
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_SystemMaintenance() {
		if (jButton_SystemMaintenance == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/systemMeinte.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 0.5);

			jButton_SystemMaintenance = new ExtendedButton("７．システムメンテナンス(8)",icon);
//			jButton_SystemMaintenance = new ExtendedButton();
//			jButton_SystemMaintenance.setText("７．システムメンテナンス(8)");
//			jButton_SystemMaintenance.setPreferredSize(new Dimension(245, 50));
			// jButton_SystemMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_SystemMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_SystemMaintenance.addActionListener(this);
			jButton_SystemMaintenance.setMnemonic(KeyEvent.VK_8);
		}
		return jButton_SystemMaintenance;
	}

	public void actionPerformed(ActionEvent e)
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

	/**
	 * This method initializes jButton_Version
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedToggleButton
	 */
	private ExtendedToggleButton getJButton_Version() {
		if (jButton_Version == null) {
			// edit 20110317
//			jButton_Version = new ExtendedToggleButton();
//			jButton_Version.setText("バージョン(F12)");
//			jButton_Version.setVisible(true);
//			jButton_Version.setPreferredSize(new Dimension(120, 25));
//			jButton_Version.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Version);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Version= new ExtendedToggleButton(
					"Version","バージョン(V)","バージョン(ALT+V)",KeyEvent.VK_V,icon);
			jButton_Version.addActionListener(this);
		}
		return jButton_Version;
	}

	/**
	 * This method initializes jButton_ReturnLogin
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_ReturnLogin() {
		if (jButton_ReturnLogin == null) {
			// edit 20110317
//			jButton_ReturnLogin = new ExtendedButton();
//			jButton_ReturnLogin.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_ReturnLogin.setActionCommand("\u7d42\u4e86");
//			jButton_ReturnLogin.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_ReturnLogin.setPreferredSize(new Dimension(170, 25));
//			jButton_ReturnLogin.setText("ログイン画面に戻る(F2)");
//			jButton_ReturnLogin.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_ReturnLogin= new ExtendedButton(
					"Return","ログインへ(R)","ログインへ(ALT+R)",KeyEvent.VK_R,icon);
			jButton_ReturnLogin.addActionListener(this);
		}
		return jButton_ReturnLogin;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowOpened(WindowEvent e) {
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
}
