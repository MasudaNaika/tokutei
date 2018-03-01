package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.Insets;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.BoxLayout;

/**
 * 健診結果データ入力画面
 */
public class JKekkaListFrame extends JFrame
implements ActionListener, KeyListener, ItemListener
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
	protected ExtendedTextField jTextField_Jyusinken_ID = null;
// edit s.inoue 2010/02/23
//	protected ExtendedTextField jTextField_HokenjyaNumber = null;
	protected ExtendedComboBox jComboBox_HokenjyaNumber = null;

	protected ExtendedTextField jTextField_KensaBeginDate = null;
	protected ExtendedTextField jTextField_HanteiBeginDate = null;
	protected ExtendedTextField jTextField_TsuuchiBeginDate = null;
	protected ExtendedTextField jTextField_HokensyoCode = null;
	protected ExtendedTextField jTextField_Name = null;
	protected ExtendedTextField jTextField_KensaEndDate = null;
	protected ExtendedTextField jTextField_HanteiEndDate = null;
	protected ExtendedTextField jTextField_TsuuchiEndDate = null;
	protected ExtendedTextField jTextField_HokensyoNumber = null;
	protected ExtendedButton jButton_Search = null;
	protected ExtendedButton jButton_OK = null;
	protected ExtendedButton jButton_Print = null;
	protected ExtendedCheckBox jCheckBox_InputDone = null;
	// del s.inoue 2009/10/07
//	protected ExtendedCheckBox jCheckBox_InputYet = null;
	protected ExtendedCheckBox jCheckBox_JisiYear = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedLabel jLabel2 = null;
	protected ExtendedLabel jLabel3 = null;
	protected ExtendedLabel jLabel4 = null;
	protected ExtendedLabel jLabel5 = null;
	protected ExtendedLabel jLabel6 = null;
	protected ExtendedLabel jLabel7 = null;
	protected ExtendedLabel jLabel8 = null;
	protected ExtendedLabel jLabel9 = null;
	protected ExtendedLabel jLabel10 = null;
	//protected ExtendedLabel jLabel12 = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel_TableArea = null;
	protected ExtendedButton jButton_Kojin = null;
	protected ExtendedButton jButton_AllSelect = null;
	protected ExtendedButton jButton_Irai = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel3 = null;
	protected JPanel jPanel4 = null;
	protected JPanel jPanel5 = null;
	protected JPanel jPanel6 = null;
	protected JPanel jPanel7 = null;
	protected JPanel jPanel1 = null;
	protected ExtendedButton jButton_KojinAdd = null;
	protected JPanel jPanel_RadioPanel = null;
	protected ExtendedTextField jTextField_sex = null;
	protected JPanel jPanel21 = null;
	protected ExtendedRadioButton jRadioButton_Male = null;
	protected JPanel jPanel11 = null;
	protected ExtendedRadioButton jRadioButton_Female = null;
	protected ButtonGroup groupSex = new ButtonGroup();
	protected ExtendedLabel jLabel31 = null;
	protected JPanel jPanel22 = null;
	protected ExtendedCheckBox jCheckBox_male = null;
	protected ExtendedCheckBox jCheckBox_female = null;
	protected ExtendedButton jButton_DeleteKekka = null;
	protected ExtendedButton jButton_DeleteKojin = null;
	protected ExtendedButton jButton_Copy = null;
	protected ExtendedLabel jLabel311 = null;
	protected ExtendedTextField jTextField_birthday = null;
	protected ExtendedLabel jLabel3111 = null;
	protected JPanel jPanel23 = null;
	protected ExtendedTextField jTextField_ageStart = null;
	protected ExtendedLabel jLabel61 = null;
	protected ExtendedTextField jTextField_ageEnd = null;
	protected ExtendedLabel jLabel_count = null;

	/**
	 * This is the default constructor
	 */
	public JKekkaListFrame() {
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
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridy = 0;
			gridBagConstraints39.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints39.weightx = 1.0D;
			gridBagConstraints39.anchor = GridBagConstraints.EAST;
			gridBagConstraints39.gridx = 0;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJPanel1(), gridBagConstraints39);
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
			cardLayout2.setVgap(5);
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("検索条件を入力し、検索ボタンを押して受診者を検索します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 13));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel("tokutei.kekkalist.frame.title");

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
			jLabal_SubExpl.setText("受診者を選択し、画面下部のボタンを押して各処理を開始します。");
			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 13));
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
			cardLayout1.setHgap(10);
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
			if (jPanel_MainArea == null) {
				jPanel_MainArea = new JPanel();
				jPanel_MainArea.setLayout(new BorderLayout());
				jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				jPanel_MainArea.add(getJPanel(), BorderLayout.NORTH);
				jPanel_MainArea.add(getJPanel_TableArea(), BorderLayout.CENTER);
			}
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jTextField_Jyusinken_ID
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_Jyusinken_ID() {
		if (jTextField_Jyusinken_ID == null) {
			jTextField_Jyusinken_ID = new ExtendedTextField("", 11, ImeMode.IME_OFF);
			jTextField_Jyusinken_ID.setPreferredSize(new Dimension(130, 20));
			jTextField_Jyusinken_ID.addActionListener(this);
		}
		return jTextField_Jyusinken_ID;
	}

// edit s.inoue 2010/02/23
//	/**
//	 * This method initializes jTextField_HokenjyaNumber
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_HokenjyaNumber() {
//		if (jTextField_HokenjyaNumber == null) {
//			jTextField_HokenjyaNumber = new ExtendedTextField("", 8, ImeMode.IME_OFF);
//			jTextField_HokenjyaNumber.setPreferredSize(new Dimension(130, 20));
//			jTextField_HokenjyaNumber.addActionListener(this);
//		}
//		return jTextField_HokenjyaNumber;
//	}
  	 /**
     * This method initializes jComboBox_HokenjyaNumber
     *
     * @return javax.swing.ExtendedComboBox
     */
	private ExtendedComboBox getJComboBox_HokenjyaNumber() {
		if (jComboBox_HokenjyaNumber == null) {
		    jComboBox_HokenjyaNumber = new ExtendedComboBox();
			jComboBox_HokenjyaNumber.setMinimumSize(new Dimension(200, 20));
			jComboBox_HokenjyaNumber.setPreferredSize(new Dimension(240, 20));
		    jComboBox_HokenjyaNumber.addItemListener(this);
		}
		return jComboBox_HokenjyaNumber;
	}

	/**
	 * This method initializes jTextField_KensaBeginDate
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_KensaBeginDate() {
		if (jTextField_KensaBeginDate == null) {
			jTextField_KensaBeginDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_KensaBeginDate.setPreferredSize(new Dimension(100, 20));
			jTextField_KensaBeginDate.addActionListener(this);
		}
		return jTextField_KensaBeginDate;
	}

	/**
	 * This method initializes jTextField_HanteiBeginDate
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HanteiBeginDate() {
		if (jTextField_HanteiBeginDate == null) {
			jTextField_HanteiBeginDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_HanteiBeginDate.setName("jTextField_HanteiBeginDate");
			jTextField_HanteiBeginDate.setPreferredSize(new Dimension(100, 20));
			jTextField_HanteiBeginDate.addActionListener(this);
		}
		return jTextField_HanteiBeginDate;
	}

	/**
	 * This method initializes jTextField_TsuuchiBeginDate
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_TsuuchiBeginDate() {
		if (jTextField_TsuuchiBeginDate == null) {
			jTextField_TsuuchiBeginDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_TsuuchiBeginDate.setPreferredSize(new Dimension(100, 20));
			jTextField_TsuuchiBeginDate.addActionListener(this);
		}
		return jTextField_TsuuchiBeginDate;
	}

	/**
	 * This method initializes jTextField_HokensyoCode
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HokensyoCode() {
		if (jTextField_HokensyoCode == null) {
			jTextField_HokensyoCode = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			jTextField_HokensyoCode.setPreferredSize(new Dimension(130, 20));
			jTextField_HokensyoCode.setVisible(false);
			jTextField_HokensyoCode.addActionListener(this);
		}
		return jTextField_HokensyoCode;
	}

	/**
	 * This method initializes jTextField_Name
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_Name() {
		if (jTextField_Name == null) {
			jTextField_Name = new ExtendedTextField(ImeMode.IME_ZENKAKU_KANA);
			jTextField_Name.setPreferredSize(new Dimension(130, 20));
			jTextField_Name.addActionListener(this);
		}
		return jTextField_Name;
	}

	/**
	 * This method initializes jTextField_KensaEndDate
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_KensaEndDate() {
		if (jTextField_KensaEndDate == null) {
			jTextField_KensaEndDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_KensaEndDate.setPreferredSize(new Dimension(100, 20));
			jTextField_KensaEndDate.addActionListener(this);
		}
		return jTextField_KensaEndDate;
	}

	/**
	 * This method initializes jTextField_HanteiEndDate
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HanteiEndDate() {
		if (jTextField_HanteiEndDate == null) {
			jTextField_HanteiEndDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_HanteiEndDate.setPreferredSize(new Dimension(100, 20));
			jTextField_HanteiEndDate.addActionListener(this);
		}
		return jTextField_HanteiEndDate;
	}

	/**
	 * This method initializes jTextField_TsuuchiEndDate
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_TsuuchiEndDate() {
		if (jTextField_TsuuchiEndDate == null) {
			jTextField_TsuuchiEndDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_TsuuchiEndDate.setPreferredSize(new Dimension(100, 20));
			jTextField_TsuuchiEndDate.addActionListener(this);
		}
		return jTextField_TsuuchiEndDate;
	}

	/**
	 * This method initializes jTextField_HokensyoNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HokensyoNumber() {
		if (jTextField_HokensyoNumber == null) {
			jTextField_HokensyoNumber = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			jTextField_HokensyoNumber.setPreferredSize(new Dimension(130, 20));
			jTextField_HokensyoNumber.setVisible(false);
			jTextField_HokensyoNumber.addActionListener(this);
		}
		return jTextField_HokensyoNumber;
	}

	/**
	 * This method initializes jButton_Serach
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Serach() {
		if (jButton_Search == null) {
			jButton_Search = new ExtendedButton();
			jButton_Search.setText("検索(S)");
//			jButton_Search.setPreferredSize(new Dimension(58, 20));
//			jButton_Search.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Search.addActionListener(this);
			jButton_Search.setMnemonic(KeyEvent.VK_S);
		}
		return jButton_Search;
	}

	/**
     * This method initializes jButton_AllSelect
     *
     * @return javax.swing.ExtendedButton
     */
	private ExtendedButton getJButton_AllSelect() {
		if (jButton_AllSelect == null) {
		    jButton_AllSelect = new ExtendedButton();
		    jButton_AllSelect.setText("全て選択(A)");
		    jButton_AllSelect.setMinimumSize(new Dimension(82, 20));
		    jButton_AllSelect.setFont(new Font("Dialog", Font.PLAIN, 12));
		    jButton_AllSelect.addActionListener(this);
		    jButton_AllSelect.setMnemonic(KeyEvent.VK_A);
		}
		return jButton_AllSelect;
	}

	/**
	 * This method initializes jButton_OK
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_OK() {
		if (jButton_OK == null) {
			jButton_OK = new ExtendedButton();
//			jButton_OK.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_OK.setText("結果入力(F12)");
//			jButton_OK.setPreferredSize(new Dimension(100, 25));
			jButton_OK.addActionListener(this);
		}
		return jButton_OK;
	}

	/**
	 * This method initializes jButton_Sheet
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Sheet() {
		if (jButton_Print == null) {
			jButton_Print = new ExtendedButton();
			jButton_Print.setText("入力票印刷(F5)");
//			jButton_Print.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Print.addActionListener(this);
		}
		return jButton_Print;
	}

	/**
	 * This method initializes jButton_Kojin
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Kojin() {
		if (jButton_Kojin == null) {
			jButton_Kojin = new ExtendedButton();
//			jButton_Kojin.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Kojin.setActionCommand("受診券呼出");
			jButton_Kojin.setText("受診券呼出(F7)");
			jButton_Kojin.addActionListener(this);
		}
		return jButton_Kojin;
	}

	/**
	 * This method initializes jButton_Irai
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Irai() {
		if (jButton_Irai == null) {
			jButton_Irai = new ExtendedButton();
			jButton_Irai.setActionCommand("依頼書印刷");
			jButton_Irai.setText("依頼書印刷(F9)");
			jButton_Irai.addActionListener(this);
		}
		return jButton_Irai;
	}

	public void actionPerformed( ActionEvent e )
	{

	}

	/**
	 * This method initializes jCheckBox_InputDone
	 *
	 * @return javax.swing.ExtendedCheckBox
	 */
	private ExtendedCheckBox getJCheckBox_InputDone() {
		if (jCheckBox_InputDone == null) {
			jCheckBox_InputDone = new ExtendedCheckBox();
			jCheckBox_InputDone.setText("結果入力済");
		    // add h.yoshitama 2009/11/11
		    jCheckBox_InputDone.addItemListener(this);
		}
		return jCheckBox_InputDone;
	}

	/**
	 * This method initializes jCheckBox_InputDone
	 *
	 * @return javax.swing.ExtendedCheckBox
	 */
	private ExtendedCheckBox getJCheckBox_JisiYear() {
		if (jCheckBox_JisiYear == null) {
			jCheckBox_JisiYear = new ExtendedCheckBox();
			jCheckBox_JisiYear.setText("今年度");
		}
		return jCheckBox_JisiYear;
	}

//	/**
//	 * This method initializes jCheckBox_InputYet
//	 *
//	 * @return javax.swing.ExtendedCheckBox
//	 */
//	private ExtendedCheckBox getJCheckBox_InputYet() {
//		if (jCheckBox_InputYet == null) {
//			jCheckBox_InputYet = new ExtendedCheckBox();
//			jCheckBox_InputYet.setText("未");
//		}
//		return jCheckBox_InputYet;
//	}

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
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridx = 5;
			gridBagConstraints46.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridy = 1;
			GridBagConstraints gridBagConstraints311 = new GridBagConstraints();
			gridBagConstraints311.gridx = 2;
			gridBagConstraints311.anchor = GridBagConstraints.WEST;
			gridBagConstraints311.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints311.gridy = 1;
			jLabel3111 = new ExtendedLabel();
			jLabel3111.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel3111.setText("生年月日");
			GridBagConstraints gridBagConstraints211 = new GridBagConstraints();
			gridBagConstraints211.gridy = 1;
			gridBagConstraints211.gridx = 3;
			gridBagConstraints211.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.gridx = 4;
			gridBagConstraints111.anchor = GridBagConstraints.WEST;
			gridBagConstraints111.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints111.gridy = 1;
			jLabel311 = new ExtendedLabel();
			jLabel311.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel311.setText("年齢");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 1;
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridy = 1;
			GridBagConstraints gridBagConstraints310 = new GridBagConstraints();
			gridBagConstraints310.gridx = 0;
			gridBagConstraints310.anchor = GridBagConstraints.WEST;
			gridBagConstraints310.gridy = 1;
			jLabel31 = new ExtendedLabel();
			jLabel31.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel31.setText("性別");
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.gridx = 7;
			gridBagConstraints110.gridwidth = 2;
			gridBagConstraints110.anchor = GridBagConstraints.WEST;
			gridBagConstraints110.insets = new Insets(0, 30, 0, 0);
			gridBagConstraints110.gridy = 1;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = 4;
			gridBagConstraints33.ipadx = 10;
			gridBagConstraints33.ipady = 0;
			gridBagConstraints33.anchor = GridBagConstraints.WEST;
//			gridBagConstraints33.fill = GridBagConstraints.BOTH;
			gridBagConstraints33.gridwidth = 2;
			gridBagConstraints33.gridy = 2;
			gridBagConstraints33.insets = new Insets(0, 70, 0, 0);

			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridx = 0;
			gridBagConstraints29.gridheight = 3;
			gridBagConstraints29.gridwidth = 6;
			gridBagConstraints29.anchor = GridBagConstraints.NORTHWEST;
//			gridBagConstraints29.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints29.gridy = 2;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 6;
			gridBagConstraints31.ipadx = 10;
			gridBagConstraints31.ipady = 10;
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			gridBagConstraints31.weightx = 1.0D;
			gridBagConstraints31.gridy = 2;

			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 4;
//			gridBagConstraints34.ipadx = 10;
//			gridBagConstraints34.ipady = 10;
			gridBagConstraints34.anchor = GridBagConstraints.WEST;
			gridBagConstraints34.fill = GridBagConstraints.BOTH;
			gridBagConstraints34.gridwidth = 2;
			gridBagConstraints34.gridy = 4;

			jLabel10 = new ExtendedLabel();
			jLabel10.setText("～");
			jLabel10.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel9 = new ExtendedLabel();

//			jLabel12 = new ExtendedLabel();
//			jLabel12.setText("今年度");
//			jLabel12.setPreferredSize(new Dimension(80, 16));
//			jLabel12.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel9.setText("結果通知日");
			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel8 = new ExtendedLabel();
			jLabel8.setText("～");
			jLabel8.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel7 = new ExtendedLabel();
			jLabel7.setText("判定日");
			jLabel7.setPreferredSize(new Dimension(100, 20));
			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel6 = new ExtendedLabel();
			jLabel6.setText("～");
			jLabel6.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel5 = new ExtendedLabel();
			jLabel5.setText("健診実施日");

			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel4 = new ExtendedLabel();
			jLabel4.setText("氏名（カナ）");
			jLabel4.setPreferredSize(new Dimension(100, 20));
			jLabel4.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel3 = new ExtendedLabel();
			jLabel3.setText("保険者番号");
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel2 = new ExtendedLabel();
			jLabel2.setText("被保険者証等番号");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel2.setVisible(false);
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("被保険者証等記号");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1.setVisible(false);
			jLabel = new ExtendedLabel();
			jLabel.setText("受診券整理番号");
			jLabel.setPreferredSize(new Dimension(100, 20));
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			// edit s.inoue 2010/02/24
			jLabel_count = new ExtendedLabel();
			jLabel_count.setPreferredSize(new Dimension(100, 20));
			jLabel_count.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints210 = new GridBagConstraints();
			gridBagConstraints210.gridx = 2;
			gridBagConstraints210.fill = GridBagConstraints.BOTH;
			gridBagConstraints210.gridwidth = 2;
			gridBagConstraints210.gridy = 5;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 6;
			gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
//		    gridBagConstraints.insets = new Insets(0, 0, 0, 0);
//			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.gridy = 4;

			// edit s.inoue 2010/02/24
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.gridx = 6;
			gridBagConstraints50.gridy = 4;
			gridBagConstraints50.anchor = GridBagConstraints.SOUTHEAST;
			gridBagConstraints50.insets = new Insets(0, 0, 30, 0);

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 0;
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 5;
			gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 4;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints19.gridy = 0;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.gridx = 5;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 4;
			gridBagConstraints18.anchor = GridBagConstraints.EAST;
			gridBagConstraints18.insets = new Insets(0, 20, 0, 10);
			gridBagConstraints18.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.gridx = 5;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 4;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.insets = new Insets(0, 20, 0, 10);
			gridBagConstraints17.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 3;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 2;
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.insets = new Insets(0, 20, 0, 0);

			// add s.inoue 2010/03/24
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridy = 4;
			gridBagConstraints35.gridx = 5;
			gridBagConstraints35.anchor = GridBagConstraints.WEST;
			gridBagConstraints35.insets = new Insets(20, 100, 0, 0);

			// add s.inoue 2010/03/24
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridy = 4;
			gridBagConstraints36.gridx = 5;
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.insets = new Insets(20, 7, 0, 0);

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel.add(jLabel, gridBagConstraints16);
			jPanel.add(getJTextField_Jyusinken_ID(), gridBagConstraints1);
			jPanel.add(jLabel1, gridBagConstraints17);
			jPanel.add(getJTextField_HokensyoCode(), gridBagConstraints5);
			jPanel.add(jLabel2, gridBagConstraints18);
			jPanel.add(getJTextField_HokensyoNumber(), gridBagConstraints9);
			// del s.inoue 2010/03/23
			jPanel.add(jLabel3, gridBagConstraints19);
			// edit s.inoue 2010/02/23
			// jPanel.add(getJTextField_HokenjyaNumber(), gridBagConstraints2);
			// del s.inoue 2010/03/23
			jPanel.add(getJComboBox_HokenjyaNumber(), gridBagConstraints2);

			jPanel.add(jLabel4, gridBagConstraints20);
			jPanel.add(getJTextField_Name(), gridBagConstraints6);

			// edit s.inoue 2010/02/24
			jPanel.add(jLabel_count,gridBagConstraints50);

			jPanel.add(getJButton_Serach(), gridBagConstraints);

			jPanel.add(getJPanel3(), gridBagConstraints31);
			jPanel.add(getJPanel4(), gridBagConstraints29);
			jPanel.add(getJPanel6(), gridBagConstraints33);
			// del s.inoue 2010/03/24
//			jPanel.add(getJPanel7(), gridBagConstraints34);

			jPanel.add(getJPanel_RadioPanel(), gridBagConstraints110);
			jPanel.add(jLabel31, gridBagConstraints310);
			jPanel.add(getJPanel22(), gridBagConstraints41);
			jPanel.add(jLabel311, gridBagConstraints111);
			jPanel.add(getJTextField_birthday(), gridBagConstraints211);
			jPanel.add(jLabel3111, gridBagConstraints311);
			jPanel.add(getJPanel23(), gridBagConstraints46);

			// add s.inoue 2010/03/24
			jPanel.add(getJCheckBox_JisiYear(), gridBagConstraints35);
			jPanel.add(getJCheckBox_InputDone(), gridBagConstraints36);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel_TableArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TableArea() {
		if (jPanel_TableArea == null) {
			jPanel_TableArea = new JPanel();
			jPanel_TableArea.setLayout(new BorderLayout());
			jPanel_TableArea.setName("jPanel_TableArea");
			jPanel_TableArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		}
		return jPanel_TableArea;
	}

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.gridx = 1;
			gridBagConstraints30.fill = GridBagConstraints.BOTH;
			gridBagConstraints30.weightx = 1.0D;
			gridBagConstraints30.gridy = 0;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridx = 0;
			gridBagConstraints27.fill = GridBagConstraints.BOTH;
			gridBagConstraints27.weightx = 1.0D;
			gridBagConstraints27.gridy = 0;
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 4;
			gridBagConstraints32.ipadx = 10;
			gridBagConstraints32.ipady = 10;
			gridBagConstraints32.weightx = 1.0D;
			gridBagConstraints32.gridy = 0;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridy = 1;
			gridBagConstraints24.gridx = 0;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.anchor = GridBagConstraints.WEST;
			gridBagConstraints26.gridy = 2;
			gridBagConstraints26.gridx = 0;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridy = 0;
			gridBagConstraints22.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.gridy = 2;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridx = 2;
			gridBagConstraints25.gridy = 1;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints23.gridy = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridy = 2;
			gridBagConstraints10.gridx = 3;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.gridx = 3;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.gridx = 3;
// del s.inoue 2010/03/24
//			// add s.inoue 2010/03/24
//			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
//			gridBagConstraints33.gridy = 2;
//			gridBagConstraints33.gridx = 4;
//			gridBagConstraints33.insets = new Insets(0, 370, 0, 0);
//
//			// add s.inoue 2010/03/24
//			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
//			gridBagConstraints34.gridy = 2;
//			gridBagConstraints34.gridx = 4;
//			gridBagConstraints34.insets = new Insets(0, 220, 0, 0);

			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.add(getJTextField_KensaEndDate(), gridBagConstraints7);
			jPanel4.add(getJTextField_HanteiEndDate(), gridBagConstraints8);
			jPanel4.add(getJTextField_TsuuchiEndDate(), gridBagConstraints10);
			jPanel4.add(jLabel6, gridBagConstraints23);
			jPanel4.add(jLabel8, gridBagConstraints25);
			jPanel4.add(jLabel10, gridBagConstraints13);
			jPanel4.add(getJTextField_KensaBeginDate(), gridBagConstraints3);
			jPanel4.add(getJTextField_HanteiBeginDate(), gridBagConstraints11);
			jPanel4.add(getJTextField_TsuuchiBeginDate(), gridBagConstraints4);
			jPanel4.add(jLabel5, gridBagConstraints22);
			jPanel4.add(jLabel9, gridBagConstraints26);
			jPanel4.add(jLabel7, gridBagConstraints24);
			jPanel4.add(getJPanel5(), gridBagConstraints32);

			// del s.inoue 2010/03/24
//			jPanel4.add(getJCheckBox_JisiYear(), gridBagConstraints33);
//			jPanel4.add(getJCheckBox_InputDone(), gridBagConstraints34);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.gridy = 0;
			gridBagConstraints21.weightx = 1.0D;
			gridBagConstraints21.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.insets = new Insets(0, 10, 0, 0);

			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			// del s.inoue 2009/10/08
			// jPanel6.add(getJCheckBox_InputDone(), gridBagConstraints12);
//			jPanel6.add(getJCheckBox_InputYet(), gridBagConstraints21);
		}
		return jPanel6;
	}

// del s.inoue 2010/03/24
//	/**
//	 * This method initializes jPanel7
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel7() {
//		if (jPanel7 == null) {
//			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
//			gridBagConstraints27.anchor = GridBagConstraints.EAST;
//			gridBagConstraints27.gridx = 1;
//			gridBagConstraints27.gridy = 0;
//			gridBagConstraints27.weightx = 1.0D;
//			gridBagConstraints27.insets = new Insets(0, 85, 0, 0);
//
//			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
//			gridBagConstraints28.anchor = GridBagConstraints.WEST;
//			gridBagConstraints28.gridx = 0;
//			gridBagConstraints28.gridy = 0;
//			gridBagConstraints28.insets = new Insets(0, 85, 0, 0);
//
//			jPanel7 = new JPanel();
//			jPanel7.setLayout(new GridBagLayout());
//			jPanel7.add(getJCheckBox_JisiYear(), gridBagConstraints27);
//			jPanel7.add(getJCheckBox_InputDone(), gridBagConstraints28);
//
//			//jPanel7.add(k, gridBagConstraints28);
//		}
//		return jPanel7;
//	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.gridx = 7;
			gridBagConstraints45.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints45.weightx = 1.0D;
			gridBagConstraints45.anchor = GridBagConstraints.EAST;
			gridBagConstraints45.gridy = 0;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.gridx = 2;
			gridBagConstraints44.anchor = GridBagConstraints.WEST;
			gridBagConstraints44.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints44.gridy = 0;
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.gridx = 1;
			gridBagConstraints43.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints43.gridy = 0;
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.gridx = 4;
			gridBagConstraints38.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints38.gridy = 0;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridx = 6;
			gridBagConstraints39.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints39.gridy = 0;
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.gridx = 5;
			gridBagConstraints37.anchor = GridBagConstraints.WEST;
			gridBagConstraints37.insets = new Insets(0, 5, 0, 0);
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridy = 0;
			gridBagConstraints36.gridx = 0;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints35.anchor = GridBagConstraints.EAST;
			gridBagConstraints35.gridx = 8;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints34.gridy = 0;
			gridBagConstraints34.weightx = 1.0D;
			gridBagConstraints34.anchor = GridBagConstraints.EAST;
			gridBagConstraints34.gridx = 3;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel1.add(getJButton_Kojin(), gridBagConstraints37);
			jPanel1.add(getJButton_Sheet(), gridBagConstraints34);
			jPanel1.add(getJButton_OK(), gridBagConstraints35);
			jPanel1.add(getJButton_End(), gridBagConstraints36);
			jPanel1.add(getJButton_KojinAdd(), gridBagConstraints38);
			jPanel1.add(getJButton_Irai(), gridBagConstraints39);
			jPanel1.add(getJButton_Delete(), gridBagConstraints43);
			jPanel1.add(getJButton_DeleteKojin(), gridBagConstraints44);
			jPanel1.add(getJButton_Copy(), gridBagConstraints45);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton_KojinAdd
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_KojinAdd() {
		if (jButton_KojinAdd == null) {
			jButton_KojinAdd = new ExtendedButton();
			jButton_KojinAdd.setActionCommand("\u53d7\u8a3a\u5238\u547c\u51fa");
			jButton_KojinAdd.setText("受診券追加(F6)");
			jButton_KojinAdd.addActionListener(this);
		}
		return jButton_KojinAdd;
	}

	/**
	 * This method initializes jPanel_RadioPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_RadioPanel() {
		if (jPanel_RadioPanel == null) {
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.gridx = 2;
			gridBagConstraints63.gridy = 0;
			gridBagConstraints63.weightx = 1.0D;
			gridBagConstraints63.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridx = 1;
			gridBagConstraints62.gridy = 0;
			GridBagConstraints gridBagConstraints321 = new GridBagConstraints();
			gridBagConstraints321.gridy = 0;
			gridBagConstraints321.ipadx = 5;
			gridBagConstraints321.gridx = 0;
			jPanel_RadioPanel = new JPanel();
			jPanel_RadioPanel.setLayout(new GridBagLayout());
			jPanel_RadioPanel.setOpaque(false);
			jPanel_RadioPanel.setVisible(false);
			jPanel_RadioPanel.add(getJTextField_sex(), gridBagConstraints321);
			jPanel_RadioPanel.add(getJPanel21(), gridBagConstraints62);
			jPanel_RadioPanel.add(getJPanel11(), gridBagConstraints63);
		}
		return jPanel_RadioPanel;
	}

	/**
	 * This method initializes jTextField_sex
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_sex() {
		if (jTextField_sex == null) {
			jTextField_sex = new ExtendedTextField(
					"",
					1,
					ImeMode.IME_OFF);
			jTextField_sex.addKeyListener(this);
			jTextField_sex.setColumns(1);
		}
		return jTextField_sex;
	}

	/**
	 * This method initializes jPanel21
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jPanel21 = new JPanel();
			jPanel21.setLayout(new BoxLayout(getJPanel21(), BoxLayout.X_AXIS));
			jPanel21.add(getJRadioButton_Male(), null);
		}
		return jPanel21;
	}

	/**
	 * This method initializes jRadioButton_Male
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Male() {
		if (jRadioButton_Male == null) {
			jRadioButton_Male = new ExtendedRadioButton();
			jRadioButton_Male.setPreferredSize(new Dimension(73, 20));
			jRadioButton_Male.setText("1\uff1a\u7537\u6027");

			jRadioButton_Male.addKeyListener(this);
			jRadioButton_Male.addItemListener(this);
			groupSex.add(jRadioButton_Male);
		}
		return jRadioButton_Male;
	}

	/**
	 * This method initializes jPanel11
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.anchor = GridBagConstraints.WEST;
			gridBagConstraints91.gridx = 0;
			gridBagConstraints91.gridy = 0;
			gridBagConstraints91.weightx = 1.0D;
			gridBagConstraints91.fill = GridBagConstraints.HORIZONTAL;
			jPanel11 = new JPanel();
			jPanel11.setLayout(new GridBagLayout());
			jPanel11.setOpaque(true);
			jPanel11.setPreferredSize(new Dimension(73, 20));
			jPanel11.add(getJRadioButton_Female(), gridBagConstraints91);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jRadioButton_Female
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Female() {
		if (jRadioButton_Female == null) {
			jRadioButton_Female = new ExtendedRadioButton();
			jRadioButton_Female.setPreferredSize(new Dimension(73, 20));
			jRadioButton_Female.setText("2\uff1a\u5973\u6027");

			jRadioButton_Female.addItemListener(this);
			groupSex.add(jRadioButton_Female);
			jRadioButton_Female.addKeyListener(this);
		}
		return jRadioButton_Female;
	}

	public void itemStateChanged(ItemEvent arg0) {

	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * This method initializes jPanel22
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel22() {
		if (jPanel22 == null) {
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.gridx = 1;
			gridBagConstraints42.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints42.gridy = 0;
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.gridx = 0;
			gridBagConstraints40.gridy = 0;
			jPanel22 = new JPanel();
			jPanel22.setLayout(new GridBagLayout());
			jPanel22.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel22.add(getJCheckBox_male(), gridBagConstraints40);
			jPanel22.add(getJCheckBox_female(), gridBagConstraints42);
		}
		return jPanel22;
	}

	/**
	 * This method initializes jCheckBox_male
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox
	 */
	private ExtendedCheckBox getJCheckBox_male() {
		if (jCheckBox_male == null) {
			jCheckBox_male = new ExtendedCheckBox();
			jCheckBox_male.setText("男性");
		}
		return jCheckBox_male;
	}

	/**
	 * This method initializes jCheckBox_female
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox
	 */
	private ExtendedCheckBox getJCheckBox_female() {
		if (jCheckBox_female == null) {
			jCheckBox_female = new ExtendedCheckBox();
			jCheckBox_female.setText("女性");
		}
		return jCheckBox_female;
	}

	/**
	 * This method initializes jButton_Delete
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_Delete() {
		if (jButton_DeleteKekka == null) {
			jButton_DeleteKekka = new ExtendedButton();
			jButton_DeleteKekka.setText("結果削除(F3)");
			jButton_DeleteKekka.setActionCommand("結果データ削除");
//			jButton_DeleteKekka.setPreferredSize(new Dimension(100, 25));
			jButton_DeleteKekka.addActionListener(this);
		}
		return jButton_DeleteKekka;
	}

	/**
	 * This method initializes jButton_DeleteKojin
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_DeleteKojin() {
		if (jButton_DeleteKojin == null) {
			jButton_DeleteKojin = new ExtendedButton();
			jButton_DeleteKojin.setActionCommand("\u53d7\u8a3a\u5238\u547c\u51fa");
			jButton_DeleteKojin.setText("受診券削除(F4)");
			jButton_DeleteKojin.addActionListener(this);
		}
		return jButton_DeleteKojin;
	}

	/**
	 * This method initializes jButton_Copy
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_Copy() {
		if (jButton_Copy == null) {
			jButton_Copy = new ExtendedButton();
//			jButton_Copy.setPreferredSize(new Dimension(100, 25));
			jButton_Copy.setText("結果複製(F11)");
			jButton_Copy.addActionListener(this);
		}
		return jButton_Copy;
	}

	/**
	 * This method initializes jTextField_birthday
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_birthday() {
		if (jTextField_birthday == null) {
			jTextField_birthday = new ExtendedTextField(
					"",
					8,
					ImeMode.IME_OFF);
			jTextField_birthday.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_birthday;
	}

	/**
	 * This method initializes jPanel23
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel23() {
		if (jPanel23 == null) {
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints49.gridy = 0;
			gridBagConstraints49.weightx = 1.0;
			gridBagConstraints49.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints49.gridx = 2;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.gridx = 1;
			gridBagConstraints48.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints48.gridy = 0;
			jLabel61 = new ExtendedLabel();
			jLabel61.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel61.setText("\uff5e");
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints47.gridy = 0;
			gridBagConstraints47.weightx = 1.0;
			gridBagConstraints47.gridx = 0;
			jPanel23 = new JPanel();
			jPanel23.setLayout(new GridBagLayout());
			jPanel23.add(getJTextField_ageStart(), gridBagConstraints47);
			jPanel23.add(jLabel61, gridBagConstraints48);
			jPanel23.add(getJTextField_ageEnd(), gridBagConstraints49);
		}
		return jPanel23;
	}

	/**
	 * This method initializes jTextField_ageStart
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_ageStart() {
		if (jTextField_ageStart == null) {
			jTextField_ageStart = new ExtendedTextField(
					"",
					3,
					ImeMode.IME_OFF);
			jTextField_ageStart.setPreferredSize(new Dimension(50, 20));
		}
		return jTextField_ageStart;
	}

	/**
	 * This method initializes jTextField_ageEnd
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_ageEnd() {
		if (jTextField_ageEnd == null) {
			jTextField_ageEnd = new ExtendedTextField(
					"",
					3,
					ImeMode.IME_OFF);
			jTextField_ageEnd.setPreferredSize(new Dimension(50, 20));
		}
		return jTextField_ageEnd;
	}
}
