package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 検査データ取り込み画面
 * Modified 2008/03/18 若月
 *  UI 修正
 */
public class JImportDataFrame extends JFrame implements ActionListener,ItemListener,KeyListener {

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
	protected ExtendedButton jButton_Import = null;
	protected JPanel jPanel_InputFiled = null;
	protected JPanel jPanel_LabelFiled = null;
	protected JPanel jPanel_FileLabel = null;
	protected JPanel jPanel_NumberLabel = null;
	protected JPanel jPanel_DateLabel = null;
	protected ExtendedLabel jLabel_FileName = null;
	protected ExtendedRadioButton jRadio_SelectNitii = null;
	protected ExtendedRadioButton jRadio_SelectDokuji = null;

	/* Del 2008/07/23 井上 */
	// protected ExtendedLabel jLabel_KikanNumber = null;
	//protected ExtendedLabel jLabel_Date = null;
	protected JPanel jPanel_FileInput = null;
	protected JPanel jPanel_NumberInput = null;
	protected JPanel jPanel_DateInput = null;
	protected ExtendedTextField jTextField_FileName = null;
	protected ExtendedTextField jTextField_Date = null;
	protected ExtendedComboBox jComboBox_KensaCenterName = null;
	protected ExtendedButton jButton_OpenFile = null;
	protected JPanel jPanel_Tools2 = null;
	protected JPanel jPanel_Tools1 = null;
	protected JPanel jPanel_Format = null;
	protected ExtendedLabel jLabel = null;
	protected JPanel jPanel_format = null;
	// del s.inoue 2010/01/26
//	protected ExtendedRadioButton jRadioButton_Kousei = null;
//	protected ExtendedRadioButton jRadioButton_Dokuji = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;

	// add s.inoue 20081001
	protected ExtendedLabel m_guiStatus = null;

	/**
	 * This is the default constructor
	 */
	public JImportDataFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		/* Modified 2008/03/18 若月 共通処理に置き換え */
		/* --------------------------------------------------- */
//		this.setSize(800, 600);
//		this.setContentPane(getJPanel_Content());
//		this.setTitle("特定健診システム");
//		setLocationRelativeTo( null );
//		this.setVisible(true);
		/* --------------------------------------------------- */
		this.setContentPane(getJPanel_Content());
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(ViewSettings.getFrameCommonSize());
		this.setLocationRelativeTo( null );
		this.setVisible(true);
		/* --------------------------------------------------- */
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
//			jPanel_Content.add(getJPanel_Tools1(), BorderLayout.CENTER);
			jPanel_Content.add(getJPanel_Tools2(), BorderLayout.CENTER);
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

			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridy = 0;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;

			// add 20081001 s.inoue
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.weightx = 2.0D;
			gridBagConstraints18.anchor = GridBagConstraints.CENTER;
			gridBagConstraints18.gridx = 0;

			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.weightx = 1.0D;
			gridBagConstraints16.anchor = GridBagConstraints.EAST;
			gridBagConstraints16.gridx = 0;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Import(), gridBagConstraints16);
			// add 20081001 s.inoue
			jPanel_ButtonArea.add(getExtendedLabel(), gridBagConstraints18);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints17);
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
			jButton_End.setText("戻る(F1)");
			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

	/**
	 * This method initializes jButton_End
	 * add 20081001 s.inoue
	 * @return javax.swing.ExtendedLabel
	 */
	private ExtendedLabel getExtendedLabel() {
		if (m_guiStatus == null) {
			m_guiStatus = new ExtendedLabel();
			m_guiStatus.setText("");
			m_guiStatus.setHorizontalAlignment(SwingConstants.RIGHT);
			// edit 20081002 s.inoue
			//m_guiStatus.setFont(new Font("Dialog", Font.PLAIN, 14));
			m_guiStatus.setFont(new Font("Dialog", Font.BOLD, 16));
			m_guiStatus.setOpaque(true);
		}
		return m_guiStatus;
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

			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_IMPORTDATA_FRAME_TITLE);

//			jLabel_Title = new ExtendedLabel();
//			jLabel_Title.setText("外部検査結果データ取込み");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");

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
			jLabal_SubExpl.setText("検査結果情報ファイルを指定してください。");
			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);

			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("検査センターからのデータファイル(txt,csv)、本システムより出力済みHL7ファイル(xml,zip)を取り込みます。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");

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
	 * This method initializes jButton_Import
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Import() {
		if (jButton_Import == null) {
			jButton_Import = new ExtendedButton();
			jButton_Import.setText("取り込み(F12)");
			jButton_Import.setPreferredSize(new Dimension(120,25));
			jButton_Import.setActionCommand("取り込み");
//			jButton_Import.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Import.addActionListener(this);
		}
		return jButton_Import;
	}

//	/**
//	 * This method initializes jPanel_InputFiled
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_InputFiled() {
//		if (jPanel_InputFiled == null) {
//			GridLayout gridLayout2 = new GridLayout();
//			gridLayout2.setRows(10);
//			jPanel_InputFiled = new JPanel();
//			jPanel_InputFiled.setLayout(gridLayout2);
//			jPanel_InputFiled.add(getJPanel_format(), null);
//		}
//		return jPanel_InputFiled;
//	}

//	/**
//	 * This method initializes jPanel_LabelFiled
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_LabelFiled() {
//		if (jPanel_LabelFiled == null) {
//			jLabel_FileName = new ExtendedLabel();
//			jLabel_FileName.setText("ファイル名");
//			jLabel_FileName.setFont(new Font("Dialog", Font.PLAIN, 14));
//			GridLayout gridLayout3 = new GridLayout();
//			gridLayout3.setRows(10);
//			gridLayout3.setColumns(0);
//			jPanel_LabelFiled = new JPanel();
//			jPanel_LabelFiled.setLayout(gridLayout3);
//		}
//		return jPanel_LabelFiled;
//	}

//	/**
//	 * This method initializes jPanel_FileLabel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_FileLabel() {
//		if (jPanel_FileLabel == null) {
//
//			jLabel_FileName = new ExtendedLabel();
//			jLabel_FileName.setText("ファイル名");
//			jLabel_FileName.setFont(new Font("Dialog", Font.PLAIN, 14));
//
//			jPanel_FileLabel = new JPanel();
//			jPanel_FileLabel.setLayout(new GridBagLayout());
//		}
//		return jPanel_FileLabel;
//	}

//	/**
//	 * This method initializes jPanel_NumberLabel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_NumberLabel() {
//		if (jPanel_NumberLabel == null) {
//			jLabel_KikanNumber = new ExtendedLabel();
//			jLabel_KikanNumber.setText("検査センター No.");
//			jLabel_KikanNumber.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jPanel_NumberLabel = new JPanel();
//			jPanel_NumberLabel.setLayout(new GridBagLayout());
//		}
//		return jPanel_NumberLabel;
//	}

//	/**
//	 * This method initializes jPanel_DateLabel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_DateLabel() {
//		if (jPanel_DateLabel == null) {
//			jLabel_Date = new ExtendedLabel();
//			jLabel_Date.setText("検査年月日");
//			jLabel_Date.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jPanel_DateLabel = new JPanel();
//			jPanel_DateLabel.setLayout(new GridBagLayout());
//		}
//		return jPanel_DateLabel;
//	}

	/**
	 * This method initializes jPanel_FileInput
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_FileInput() {
		if (jPanel_FileInput == null) {
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.weightx = 1.0D;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			jPanel_FileInput = new JPanel();
			jPanel_FileInput.setLayout(new GridBagLayout());
			jPanel_FileInput.add(getJTextField_FileName(), gridBagConstraints14);
			jPanel_FileInput.add(getJButton_OpenFile(), gridBagConstraints15);
		}
		return jPanel_FileInput;
	}

//	/**
//	 * This method initializes jPanel_NumberInput
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_NumberInput() {
//		if (jPanel_NumberInput == null) {
//			FlowLayout flowLayout2 = new FlowLayout();
//			flowLayout2.setAlignment(FlowLayout.LEFT);
//			jPanel_NumberInput = new JPanel();
//			jPanel_NumberInput.setLayout(flowLayout2);
//		}
//		return jPanel_NumberInput;
//	}

//	/**
//	 * This method initializes jPanel_DateInput
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_DateInput() {
//		if (jPanel_DateInput == null) {
//			FlowLayout flowLayout3 = new FlowLayout();
//			flowLayout3.setAlignment(FlowLayout.LEFT);
//			jPanel_DateInput = new JPanel();
//			jPanel_DateInput.setLayout(flowLayout3);
//		}
//		return jPanel_DateInput;
//	}

	/**
	 * This method initializes jTextField_FileName
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_FileName() {
		if (jTextField_FileName == null) {
			jTextField_FileName = new ExtendedTextField();
			jTextField_FileName.setPreferredSize(new Dimension(100, 20));
			jTextField_FileName.setColumns(35);
		}
		return jTextField_FileName;
	}

	/**
	 * This method initializes jTextField_Date
	 *
	 * @return javax.swing.ExtendedTextField
	 */
//	private ExtendedTextField getJTextField_Date() {
//		if (jTextField_Date == null) {
//			jTextField_Date = new ExtendedTextField();
//			jTextField_Date.setColumns(35);
//		}
//		return jTextField_Date;
//	}

	/**
	 * This method initializes jComboBox_KensaCenterName
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
//	private ExtendedComboBox getJComboBox_KensaCenterName() {
//		if (jComboBox_KensaCenterName == null) {
//			jComboBox_KensaCenterName = new ExtendedComboBox();
//			jComboBox_KensaCenterName.setMaximumRowCount(10);
//			jComboBox_KensaCenterName.setPreferredSize(new Dimension(200, 20));
//		}
//		return jComboBox_KensaCenterName;
//	}

	// del s.inoue 2010/04/02
//	/**
//	 * This method initializes jRadio_SelectDokuji
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedRadioButton getJRadio_SelectDokuji() {
//		if (jRadio_SelectDokuji == null) {
//			jRadio_SelectDokuji = new ExtendedRadioButton();
//			jRadio_SelectDokuji.setText("検査結果");
//			jRadio_SelectDokuji.addActionListener(this);
//		}
//		return jRadio_SelectDokuji;
//	}
//
//	/**
//	 * This method initializes jButton_OpenFile
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedRadioButton getJRadio_SelectNitiji() {
//		if (jRadio_SelectNitii == null) {
//			jRadio_SelectNitii = new ExtendedRadioButton();
//			jRadio_SelectNitii.setText("日医フォーマット");
//			jRadio_SelectNitii.addActionListener(this);
//		}
//		return jRadio_SelectNitii;
//	}

	/**
	 * This method initializes jButton_OpenFile
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_OpenFile() {
		if (jButton_OpenFile == null) {
			jButton_OpenFile = new ExtendedButton();
			jButton_OpenFile.setText("参照(O)");
//			jButton_OpenFile.setPreferredSize(new Dimension(58, 20));
//			jButton_OpenFile.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_OpenFile.addActionListener(this);
			jButton_OpenFile.setMnemonic(KeyEvent.VK_O);
		}
		return jButton_OpenFile;
	}

	/**
	 * This method initializes jPanel_Tools2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Tools2() {
		if (jPanel_Tools2 == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints141 = new GridBagConstraints();
			gridBagConstraints141.gridx = 0;
			gridBagConstraints141.weighty = 1.0D;
			gridBagConstraints141.gridy = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints13.weightx = 1.0D;
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridy = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 5;
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.fill = GridBagConstraints.BOTH;
			gridBagConstraints111.gridy = 4;
			gridBagConstraints111.weightx = 1.0D;
			gridBagConstraints111.gridx = 1;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.weightx = 1.0D;
			gridBagConstraints41.fill = GridBagConstraints.BOTH;
			gridBagConstraints41.gridy = 5;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.gridy = 4;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.weightx = 1.0D;
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridy = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.ipadx = 189;
			gridBagConstraints1.ipady = -61;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			jPanel_Tools2 = new JPanel();
			jPanel_Tools2.setName("jPanel8");
			jPanel_Tools2.setLayout(new GridBagLayout());
			jPanel_Tools2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			jPanel_Tools2.add(getJPanel(), gridBagConstraints13);
			jPanel_Tools2.add(getJPanel1(), gridBagConstraints141);

		}
		return jPanel_Tools2;
	}

//	/**
//	 * This method initializes jPanel_Tools1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Tools1() {
//		if (jPanel_Tools1 == null) {
//			CardLayout cardLayout = new CardLayout();
//			cardLayout.setHgap(10);
//			jPanel_Tools1 = new JPanel();
//			jPanel_Tools1.setLayout(cardLayout);
//		}
//		return jPanel_Tools1;
//	}

	/**
	 * This method initializes jPanel_Format
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Format() {
		if (jPanel_Format == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 0;
			jPanel_Format = new JPanel();
			jPanel_Format.setLayout(new GridBagLayout());
			/* Del 2008/07/23 井上 */
			/* --------------------------------------------------- */
			// jPanel_Format.add(getJRadioButton_Kousei(), gridBagConstraints2);
			// jPanel_Format.add(getJRadioButton_Dokuji(), gridBagConstraints3);
			/* --------------------------------------------------- */
		}
		return jPanel_Format;
	}

//	/**
//	 * This method initializes jPanel_format
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_format() {
//		if (jPanel_format == null) {
//			GridLayout gridLayout4 = new GridLayout();
//			gridLayout4.setRows(2);
//			gridLayout4.setColumns(1);
//			jPanel_format = new JPanel();
//			jPanel_format.setLayout(gridLayout4);
//		}
//		return jPanel_format;
//	}

	/**
	 * This method initializes jRadioButton_Kousei
	 *
	 * @return javax.swing.ExtendedRadioButton
	 */
//	private ExtendedRadioButton getJRadioButton_Kousei() {
//		if (jRadioButton_Kousei == null) {
//			jRadioButton_Kousei = new ExtendedRadioButton();
//			jRadioButton_Kousei.setText("厚労省準拠フォーマット");
//			jRadioButton_Kousei.setPreferredSize(new Dimension(160, 20));
//			jRadioButton_Kousei.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jRadioButton_Kousei.addItemListener(this);
//		}
//		return jRadioButton_Kousei;
//	}

	/**
	 * This method initializes jRadioButton_Dokuji
	 *
	 * @return javax.swing.ExtendedRadioButton
	 */
//	private ExtendedRadioButton getJRadioButton_Dokuji() {
//		if (jRadioButton_Dokuji == null) {
//			jRadioButton_Dokuji = new ExtendedRadioButton();
//			jRadioButton_Dokuji.setText("独自フォーマット");
//			jRadioButton_Dokuji.setPreferredSize(new Dimension(150, 20));
//			jRadioButton_Dokuji.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jRadioButton_Dokuji.addItemListener(this);
//		}
//		return jRadioButton_Dokuji;
//	}

	public void actionPerformed(ActionEvent e)
	{

	}

	public void itemStateChanged(ItemEvent e)
	{

	}

	/*
	 * FrameSize Control
	 */
	public void validate()
	{
		Rectangle rect = getBounds();

		super.validate();

		/* Modified 2008/03/09 若月  */
		/* --------------------------------------------------- */
//		if( ViewSettings.NORMALFRAME_MINSIZE_W > rect.width  ||
//		ViewSettings.NORMALFRAME_MINSIZE_H > rect.height )
//{
//	setBounds( rect.x,
//			   rect.y,
//			   ViewSettings.NORMALFRAME_MINSIZE_W,
//			   ViewSettings.NORMALFRAME_MINSIZE_H );
//}
		/* --------------------------------------------------- */
		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
				ViewSettings.getFrameCommonHeight() > rect.height )
		{
			setBounds( rect.x,
					   rect.y,
					   ViewSettings.getFrameCommonWidth(),
					   ViewSettings.getFrameCommonHeight() );
		}
		/* --------------------------------------------------- */

	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			/* Del 2008/07/23 井上 */
			/* --------------------------------------------------- */
//			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
//			gridBagConstraints11.gridy = 3;
//			gridBagConstraints11.weightx = 1.0;
//			gridBagConstraints11.anchor = GridBagConstraints.WEST;
//			gridBagConstraints11.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints11.gridx = 1;
//			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
//			gridBagConstraints10.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints10.gridy = 2;
//			gridBagConstraints10.weightx = 1.0;
//			gridBagConstraints10.anchor = GridBagConstraints.WEST;
//			gridBagConstraints10.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 1;
//			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
//			gridBagConstraints8.gridy = 3;
//			gridBagConstraints8.anchor = GridBagConstraints.WEST;
//			gridBagConstraints8.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints8.gridx = 0;
//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridy = 2;
//			gridBagConstraints7.anchor = GridBagConstraints.WEST;
//			gridBagConstraints7.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints7.gridx = 0;
			/* --------------------------------------------------- */

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints6.gridx = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = -1;
//			gridBagConstraints4.gridx = -1;

			// add s.inoue 2010/01/25
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(5, 0, 0, 0);

			// add s.inoue 2010/01/25
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.insets = new Insets(5, 0, 0, 0);

			jLabel_FileName = new ExtendedLabel();
			jLabel_FileName.setText("ファイル名");

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel.add(getJPanel_Format(), gridBagConstraints5);
			jPanel.add(getJPanel_FileInput(), gridBagConstraints9);
			jPanel.add(jLabel_FileName, gridBagConstraints6);

// del s.inoue 2010/04/02
//			jPanel.add(getJRadio_SelectDokuji(), gridBagConstraints7);
//			jPanel.add(getJRadio_SelectNitiji(), gridBagConstraints8);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
		}
		return jPanel1;
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

//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.BorderLayout;
//import javax.swing.JPanel;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//
//import java.awt.Dimension;
//import javax.swing.JButton;
//import java.awt.GridLayout;
//import java.awt.FlowLayout;
//import javax.swing.SwingConstants;
//import java.awt.CardLayout;
//import java.awt.Font;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
//
//import javax.swing.BorderFactory;
//import java.awt.Color;
//import java.awt.GridBagLayout;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//
///**
// * 検査データ取り込み画面
// * Modified 2008/03/18 若月
// *  UI 修正
// */
//public class JImportDataFrame extends JFrame implements ActionListener,ItemListener,KeyListener {
//
//	protected static final long serialVersionUID = 1L;
//	protected JPanel jPanel_Content = null;
//	protected JPanel jPanel_ButtonArea = null;
//	protected ExtendedButton jButton_End = null;
//	protected JPanel jPanel_NaviArea = null;
//	protected TitleLabel jLabel_Title = null;
//	protected ExtendedLabel jLabel_MainExpl = null;
//	protected JPanel jPanel_TitleArea = null;
//	protected JPanel jPanel_ExplArea2 = null;
//	protected ExtendedLabel jLabal_SubExpl = null;
//	protected JPanel jPanel_ExplArea1 = null;
//	protected ExtendedButton jButton_Import = null;
//	protected JPanel jPanel_InputFiled = null;
//	protected JPanel jPanel_LabelFiled = null;
//	protected JPanel jPanel_FileLabel = null;
//	protected JPanel jPanel_NumberLabel = null;
//	protected JPanel jPanel_DateLabel = null;
//	protected ExtendedLabel jLabel_FileName = null;
//	/* Del 2008/07/23 井上 */
//	// protected ExtendedLabel jLabel_KikanNumber = null;
//	//protected ExtendedLabel jLabel_Date = null;
//	protected JPanel jPanel_FileInput = null;
//	protected JPanel jPanel_NumberInput = null;
//	protected JPanel jPanel_DateInput = null;
//	protected ExtendedTextField jTextField_FileName = null;
//	protected ExtendedTextField jTextField_Date = null;
//	protected ExtendedComboBox jComboBox_KensaCenterName = null;
//	protected ExtendedButton jButton_OpenFile = null;
//	protected JPanel jPanel_Tools2 = null;
//	protected JPanel jPanel_Tools1 = null;
//	protected JPanel jPanel_Format = null;
//	protected ExtendedLabel jLabel = null;
//	protected JPanel jPanel_format = null;
//	protected ExtendedRadioButton jRadioButton_Kousei = null;
//	protected ExtendedRadioButton jRadioButton_Dokuji = null;
//	private JPanel jPanel = null;
//	private JPanel jPanel1 = null;
//
//	// add s.inoue 20081001
//	protected ExtendedLabel m_guiStatus = null;
//
//	/**
//	 * This is the default constructor
//	 */
//	public JImportDataFrame() {
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
//		/* Modified 2008/03/18 若月 共通処理に置き換え */
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
////			jPanel_Content.add(getJPanel_Tools1(), BorderLayout.CENTER);
//			jPanel_Content.add(getJPanel_Tools2(), BorderLayout.CENTER);
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
//			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
//			gridBagConstraints17.gridy = 0;
//			gridBagConstraints17.gridx = 0;
//			gridBagConstraints17.anchor = GridBagConstraints.WEST;
//
//			// add 20081001 s.inoue
//			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
//			gridBagConstraints18.gridy = 0;
//			gridBagConstraints18.weightx = 2.0D;
//			gridBagConstraints18.anchor = GridBagConstraints.CENTER;
//			gridBagConstraints18.gridx = 0;
//
//			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
//			gridBagConstraints16.gridy = 0;
//			gridBagConstraints16.weightx = 1.0D;
//			gridBagConstraints16.anchor = GridBagConstraints.EAST;
//			gridBagConstraints16.gridx = 0;
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			jPanel_ButtonArea.add(getJButton_Import(), gridBagConstraints16);
//			// add 20081001 s.inoue
//			jPanel_ButtonArea.add(getExtendedLabel(), gridBagConstraints18);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints17);
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
//			jButton_End.setText("戻る(F1)");
//			jButton_End.setActionCommand("終了");
////			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.addActionListener(this);
//		}
//		return jButton_End;
//	}
//
//	/**
//	 * This method initializes jButton_End
//	 * add 20081001 s.inoue
//	 * @return javax.swing.ExtendedLabel
//	 */
//	private ExtendedLabel getExtendedLabel() {
//		if (m_guiStatus == null) {
//			m_guiStatus = new ExtendedLabel();
//			m_guiStatus.setText("");
//			m_guiStatus.setHorizontalAlignment(SwingConstants.RIGHT);
//			// edit 20081002 s.inoue
//			//m_guiStatus.setFont(new Font("Dialog", Font.PLAIN, 14));
//			m_guiStatus.setFont(new Font("Dialog", Font.BOLD, 16));
//			m_guiStatus.setOpaque(true);
//		}
//		return m_guiStatus;
//	}
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
//
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_IMPORTDATA_FRAME_TITLE);
//
////			jLabel_Title = new ExtendedLabel();
////			jLabel_Title.setText("外部検査結果データ取込み");
////			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
////			jLabel_Title.setBackground(new Color(153, 204, 255));
////			jLabel_Title.setForeground(new Color(51, 51, 51));
////			jLabel_Title.setOpaque(true);
////			jLabel_Title.setName("jLabel");
//
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
//
//			jLabal_SubExpl = new ExtendedLabel();
//			jLabal_SubExpl.setText("検査結果情報ファイルを指定してください。");
//			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			GridLayout gridLayout1 = new GridLayout();
//			gridLayout1.setRows(2);
//
//			jLabel_MainExpl = new ExtendedLabel();
//			jLabel_MainExpl.setText("検査センターからのデータファイルを取り込みます。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//
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
//	 * This method initializes jButton_Import
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Import() {
//		if (jButton_Import == null) {
//			jButton_Import = new ExtendedButton();
//			jButton_Import.setText("取り込み(F12)");
//			jButton_Import.setPreferredSize(new Dimension(120,25));
//			jButton_Import.setActionCommand("取り込み");
////			jButton_Import.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Import.addActionListener(this);
//		}
//		return jButton_Import;
//	}
//
////	/**
////	 * This method initializes jPanel_InputFiled
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_InputFiled() {
////		if (jPanel_InputFiled == null) {
////			GridLayout gridLayout2 = new GridLayout();
////			gridLayout2.setRows(10);
////			jPanel_InputFiled = new JPanel();
////			jPanel_InputFiled.setLayout(gridLayout2);
////			jPanel_InputFiled.add(getJPanel_format(), null);
////		}
////		return jPanel_InputFiled;
////	}
//
////	/**
////	 * This method initializes jPanel_LabelFiled
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_LabelFiled() {
////		if (jPanel_LabelFiled == null) {
////			jLabel_FileName = new ExtendedLabel();
////			jLabel_FileName.setText("ファイル名");
////			jLabel_FileName.setFont(new Font("Dialog", Font.PLAIN, 14));
////			GridLayout gridLayout3 = new GridLayout();
////			gridLayout3.setRows(10);
////			gridLayout3.setColumns(0);
////			jPanel_LabelFiled = new JPanel();
////			jPanel_LabelFiled.setLayout(gridLayout3);
////		}
////		return jPanel_LabelFiled;
////	}
//
////	/**
////	 * This method initializes jPanel_FileLabel
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_FileLabel() {
////		if (jPanel_FileLabel == null) {
////
////			jLabel_FileName = new ExtendedLabel();
////			jLabel_FileName.setText("ファイル名");
////			jLabel_FileName.setFont(new Font("Dialog", Font.PLAIN, 14));
////
////			jPanel_FileLabel = new JPanel();
////			jPanel_FileLabel.setLayout(new GridBagLayout());
////		}
////		return jPanel_FileLabel;
////	}
//
////	/**
////	 * This method initializes jPanel_NumberLabel
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_NumberLabel() {
////		if (jPanel_NumberLabel == null) {
////			jLabel_KikanNumber = new ExtendedLabel();
////			jLabel_KikanNumber.setText("検査センター No.");
////			jLabel_KikanNumber.setFont(new Font("Dialog", Font.PLAIN, 14));
////			jPanel_NumberLabel = new JPanel();
////			jPanel_NumberLabel.setLayout(new GridBagLayout());
////		}
////		return jPanel_NumberLabel;
////	}
//
////	/**
////	 * This method initializes jPanel_DateLabel
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_DateLabel() {
////		if (jPanel_DateLabel == null) {
////			jLabel_Date = new ExtendedLabel();
////			jLabel_Date.setText("検査年月日");
////			jLabel_Date.setFont(new Font("Dialog", Font.PLAIN, 14));
////			jPanel_DateLabel = new JPanel();
////			jPanel_DateLabel.setLayout(new GridBagLayout());
////		}
////		return jPanel_DateLabel;
////	}
//
//	/**
//	 * This method initializes jPanel_FileInput
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_FileInput() {
//		if (jPanel_FileInput == null) {
//			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
//			gridBagConstraints15.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints15.gridy = 0;
//			gridBagConstraints15.weightx = 1.0D;
//			gridBagConstraints15.anchor = GridBagConstraints.WEST;
//			gridBagConstraints15.gridx = 1;
//			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
//			gridBagConstraints14.gridx = 0;
//			gridBagConstraints14.gridy = 0;
//			gridBagConstraints14.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints14.anchor = GridBagConstraints.WEST;
//			jPanel_FileInput = new JPanel();
//			jPanel_FileInput.setLayout(new GridBagLayout());
//			jPanel_FileInput.add(getJTextField_FileName(), gridBagConstraints14);
//			jPanel_FileInput.add(getJButton_OpenFile(), gridBagConstraints15);
//		}
//		return jPanel_FileInput;
//	}
//
////	/**
////	 * This method initializes jPanel_NumberInput
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_NumberInput() {
////		if (jPanel_NumberInput == null) {
////			FlowLayout flowLayout2 = new FlowLayout();
////			flowLayout2.setAlignment(FlowLayout.LEFT);
////			jPanel_NumberInput = new JPanel();
////			jPanel_NumberInput.setLayout(flowLayout2);
////		}
////		return jPanel_NumberInput;
////	}
//
////	/**
////	 * This method initializes jPanel_DateInput
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_DateInput() {
////		if (jPanel_DateInput == null) {
////			FlowLayout flowLayout3 = new FlowLayout();
////			flowLayout3.setAlignment(FlowLayout.LEFT);
////			jPanel_DateInput = new JPanel();
////			jPanel_DateInput.setLayout(flowLayout3);
////		}
////		return jPanel_DateInput;
////	}
//
//	/**
//	 * This method initializes jTextField_FileName
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_FileName() {
//		if (jTextField_FileName == null) {
//			jTextField_FileName = new ExtendedTextField();
//			jTextField_FileName.setColumns(35);
//		}
//		return jTextField_FileName;
//	}
//
//	/**
//	 * This method initializes jTextField_Date
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
////	private ExtendedTextField getJTextField_Date() {
////		if (jTextField_Date == null) {
////			jTextField_Date = new ExtendedTextField();
////			jTextField_Date.setColumns(35);
////		}
////		return jTextField_Date;
////	}
//
//	/**
//	 * This method initializes jComboBox_KensaCenterName
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
////	private ExtendedComboBox getJComboBox_KensaCenterName() {
////		if (jComboBox_KensaCenterName == null) {
////			jComboBox_KensaCenterName = new ExtendedComboBox();
////			jComboBox_KensaCenterName.setMaximumRowCount(10);
////			jComboBox_KensaCenterName.setPreferredSize(new Dimension(200, 20));
////		}
////		return jComboBox_KensaCenterName;
////	}
//
//	/**
//	 * This method initializes jButton_OpenFile
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_OpenFile() {
//		if (jButton_OpenFile == null) {
//			jButton_OpenFile = new ExtendedButton();
//			jButton_OpenFile.setText("参照");
////			jButton_OpenFile.setPreferredSize(new Dimension(58, 20));
////			jButton_OpenFile.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_OpenFile.addActionListener(this);
//		}
//		return jButton_OpenFile;
//	}
//
//	/**
//	 * This method initializes jPanel_Tools2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Tools2() {
//		if (jPanel_Tools2 == null) {
//			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
//			gridBagConstraints11.gridx = 0;
//			gridBagConstraints11.gridy = 1;
//			GridBagConstraints gridBagConstraints141 = new GridBagConstraints();
//			gridBagConstraints141.gridx = 0;
//			gridBagConstraints141.weighty = 1.0D;
//			gridBagConstraints141.gridy = 1;
//			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
//			gridBagConstraints13.gridx = 0;
//			gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
//			gridBagConstraints13.weightx = 1.0D;
//			gridBagConstraints13.fill = GridBagConstraints.BOTH;
//			gridBagConstraints13.gridy = 0;
//			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
//			gridBagConstraints12.gridx = 1;
//			gridBagConstraints12.gridy = 5;
//			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
//			gridBagConstraints111.fill = GridBagConstraints.BOTH;
//			gridBagConstraints111.gridy = 4;
//			gridBagConstraints111.weightx = 1.0D;
//			gridBagConstraints111.gridx = 1;
//			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
//			gridBagConstraints41.gridx = 0;
//			gridBagConstraints41.weightx = 1.0D;
//			gridBagConstraints41.fill = GridBagConstraints.BOTH;
//			gridBagConstraints41.gridy = 5;
//			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
//			gridBagConstraints31.gridx = 0;
//			gridBagConstraints31.fill = GridBagConstraints.BOTH;
//			gridBagConstraints31.gridy = 4;
//			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
//			gridBagConstraints21.gridx = 0;
//			gridBagConstraints21.weightx = 1.0D;
//			gridBagConstraints21.fill = GridBagConstraints.BOTH;
//			gridBagConstraints21.gridy = 3;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridx = 2;
//			gridBagConstraints1.ipadx = 189;
//			gridBagConstraints1.ipady = -61;
//			gridBagConstraints1.gridy = 0;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridx = 1;
//			gridBagConstraints.gridy = 0;
//			jPanel_Tools2 = new JPanel();
//			jPanel_Tools2.setName("jPanel8");
//			jPanel_Tools2.setLayout(new GridBagLayout());
//			jPanel_Tools2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//			jPanel_Tools2.add(getJPanel(), gridBagConstraints13);
//			jPanel_Tools2.add(getJPanel1(), gridBagConstraints141);
//
//		}
//		return jPanel_Tools2;
//	}
//
////	/**
////	 * This method initializes jPanel_Tools1
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_Tools1() {
////		if (jPanel_Tools1 == null) {
////			CardLayout cardLayout = new CardLayout();
////			cardLayout.setHgap(10);
////			jPanel_Tools1 = new JPanel();
////			jPanel_Tools1.setLayout(cardLayout);
////		}
////		return jPanel_Tools1;
////	}
//
//	/**
//	 * This method initializes jPanel_Format
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Format() {
//		if (jPanel_Format == null) {
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.anchor = GridBagConstraints.WEST;
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.weightx = 1.0D;
//			gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints3.gridx = 1;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.anchor = GridBagConstraints.WEST;
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.gridx = 0;
//			jPanel_Format = new JPanel();
//			jPanel_Format.setLayout(new GridBagLayout());
//			/* Del 2008/07/23 井上 */
//			/* --------------------------------------------------- */
//			// jPanel_Format.add(getJRadioButton_Kousei(), gridBagConstraints2);
//			// jPanel_Format.add(getJRadioButton_Dokuji(), gridBagConstraints3);
//			/* --------------------------------------------------- */
//		}
//		return jPanel_Format;
//	}
//
////	/**
////	 * This method initializes jPanel_format
////	 *
////	 * @return javax.swing.JPanel
////	 */
////	private JPanel getJPanel_format() {
////		if (jPanel_format == null) {
////			GridLayout gridLayout4 = new GridLayout();
////			gridLayout4.setRows(2);
////			gridLayout4.setColumns(1);
////			jPanel_format = new JPanel();
////			jPanel_format.setLayout(gridLayout4);
////		}
////		return jPanel_format;
////	}
//
//	/**
//	 * This method initializes jRadioButton_Kousei
//	 *
//	 * @return javax.swing.ExtendedRadioButton
//	 */
////	private ExtendedRadioButton getJRadioButton_Kousei() {
////		if (jRadioButton_Kousei == null) {
////			jRadioButton_Kousei = new ExtendedRadioButton();
////			jRadioButton_Kousei.setText("厚労省準拠フォーマット");
////			jRadioButton_Kousei.setPreferredSize(new Dimension(160, 20));
////			jRadioButton_Kousei.setFont(new Font("Dialog", Font.PLAIN, 12));
////			jRadioButton_Kousei.addItemListener(this);
////		}
////		return jRadioButton_Kousei;
////	}
//
//	/**
//	 * This method initializes jRadioButton_Dokuji
//	 *
//	 * @return javax.swing.ExtendedRadioButton
//	 */
////	private ExtendedRadioButton getJRadioButton_Dokuji() {
////		if (jRadioButton_Dokuji == null) {
////			jRadioButton_Dokuji = new ExtendedRadioButton();
////			jRadioButton_Dokuji.setText("独自フォーマット");
////			jRadioButton_Dokuji.setPreferredSize(new Dimension(150, 20));
////			jRadioButton_Dokuji.setFont(new Font("Dialog", Font.PLAIN, 12));
////			jRadioButton_Dokuji.addItemListener(this);
////		}
////		return jRadioButton_Dokuji;
////	}
//
//	public void actionPerformed(ActionEvent e)
//	{
//
//	}
//
//	public void itemStateChanged(ItemEvent e)
//	{
//
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
//
//	}
//
//	/**
//	 * This method initializes jPanel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel() {
//		if (jPanel == null) {
//			/* Del 2008/07/23 井上 */
//			/* --------------------------------------------------- */
////			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
////			gridBagConstraints11.gridy = 3;
////			gridBagConstraints11.weightx = 1.0;
////			gridBagConstraints11.anchor = GridBagConstraints.WEST;
////			gridBagConstraints11.insets = new Insets(5, 0, 0, 0);
////			gridBagConstraints11.gridx = 1;
////			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
////			gridBagConstraints10.fill = GridBagConstraints.VERTICAL;
////			gridBagConstraints10.gridy = 2;
////			gridBagConstraints10.weightx = 1.0;
////			gridBagConstraints10.anchor = GridBagConstraints.WEST;
////			gridBagConstraints10.insets = new Insets(5, 0, 0, 0);
////			gridBagConstraints10.gridx = 1;
//			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
//			gridBagConstraints9.fill = GridBagConstraints.BOTH;
//			gridBagConstraints9.gridy = 1;
//			gridBagConstraints9.weightx = 1.0D;
//			gridBagConstraints9.gridx = 1;
////			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
////			gridBagConstraints8.gridy = 3;
////			gridBagConstraints8.anchor = GridBagConstraints.WEST;
////			gridBagConstraints8.insets = new Insets(5, 0, 0, 0);
////			gridBagConstraints8.gridx = 0;
////			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
////			gridBagConstraints7.gridy = 2;
////			gridBagConstraints7.anchor = GridBagConstraints.WEST;
////			gridBagConstraints7.insets = new Insets(5, 0, 0, 0);
////			gridBagConstraints7.gridx = 0;
//			/* --------------------------------------------------- */
//
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridy = 1;
//			gridBagConstraints6.anchor = GridBagConstraints.WEST;
//			gridBagConstraints6.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints6.gridx = 0;
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.anchor = GridBagConstraints.WEST;
//			gridBagConstraints5.gridx = 1;
//			gridBagConstraints5.gridy = 0;
//			gridBagConstraints5.fill = GridBagConstraints.BOTH;
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = -1;
//			gridBagConstraints4.gridx = -1;
//
//			jLabel_FileName = new ExtendedLabel();
//			jLabel_FileName.setText("ファイル名");
//			/* Del 2008/07/23 井上 */
//			/* --------------------------------------------------- */
//			// jLabel_KikanNumber = new ExtendedLabel();
//			// jLabel_KikanNumber.setText("検査センター No.");
//			//jLabel_Date = new ExtendedLabel();
//			//jLabel_Date.setText("検査年月日");
//			// jLabel = new ExtendedLabel();
//			// jLabel.setText("取込フォーマット");
//			// jLabel.setPreferredSize(new Dimension(120, 16));
//			// jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			/* --------------------------------------------------- */
//
//			jPanel = new JPanel();
//			jPanel.setLayout(new GridBagLayout());
//			jPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			/* Del 2008/07/23 井上 */
//			/* --------------------------------------------------- */
//			// jPanel.add(jLabel, gridBagConstraints4);
//			jPanel.add(getJPanel_Format(), gridBagConstraints5);
//			jPanel.add(jLabel_FileName, gridBagConstraints6);
//			// jPanel.add(jLabel_KikanNumber, gridBagConstraints7);
//			// jPanel.add(jLabel_Date, gridBagConstraints8);
//			jPanel.add(getJPanel_FileInput(), gridBagConstraints9);
//			// jPanel.add(getJComboBox_KensaCenterName(), gridBagConstraints10);
//			// jPanel.add(getJTextField_Date(), gridBagConstraints11);
//			/* --------------------------------------------------- */
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
//			jPanel1.setLayout(new GridBagLayout());
//		}
//		return jPanel1;
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
