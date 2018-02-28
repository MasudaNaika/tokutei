package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.BorderFactory;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.EtchedBorder;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 健診結果データ入力画面
 */
public class JKekkaRegisterFrame extends JFrame implements ActionListener,ItemListener, KeyListener, MouseListener
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
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel3 = null;
	// add h.yoshitama 2009/09/24
	protected JPanel jPanel8 = null;
	protected JPanel jPanel9 = null;

	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedLabel jLabel2 = null;
	protected ExtendedLabel jLabelSeirino = null;
	protected ExtendedTextField jTextField_HokenjyaCode = null;
	protected ExtendedTextField jTextField_Date = null;
	protected ExtendedTextField jTextField_SeiriNo = null;
	protected ExtendedComboBox jComboBox_Pattern = null;
	protected ExtendedLabel jLabel3 = null;
	protected ExtendedLabel jLabel4 = null;
	protected ExtendedTextField jTextField_HokenjyaNumber = null;
	protected JPanel jPanel12 = null;
	protected JPanel jPanel_Table = null;
	protected ExtendedLabel jLabel6 = null;
	protected ExtendedButton jButton_Register = null;
	protected ExtendedLabel jLabel5 = null;
	protected ExtendedTextField jTextField_Name = null;

	protected ExtendedLabel jLabel16;
	protected ExtendedTextField jTextFieldsexName;

	protected JPanel jPanel11 = null;
	protected ExtendedLabel jLabel8 = null;
	protected ExtendedEditorPane jEditorPane_Comment = null;
	protected JPanel jPanel15 = null;
	protected ExtendedLabel jLabel9 = null;
	protected JScrollPane jScrollPane1 = null;
	protected ExtendedComboBox jComboBox_KensaKikanName = null;
	protected ExtendedLabel jLabel11 = null;
	protected ExtendedComboBox jComboBox_HokenSidouLevel = null;
	protected ExtendedComboBox jComboBox_MetaboHantei = null;
	protected ExtendedComboBox jComboBox_SeikyuKubun = null;
	protected ExtendedButton jButton_Cancel = null;

	// edit h.yoshitama 2009/10/19
//	protected JLabel jLabelSub2Expl = null;
//	protected JLabel jLabelBirthDay = null;
//	protected JTextField jTextFieldBirthDay = null;
//	protected JLabel jLabelNenrei = null;
//	protected JTextField jTextFieldNenrei = null;

	protected ExtendedLabel jLabelSub2Expl = null;
	protected ExtendedLabel jLabelBirthDay = null;
	protected ExtendedTextField jTextFieldBirthDay = null;
	protected ExtendedLabel jLabelNenrei = null;
	protected ExtendedTextField jTextFieldNenrei = null;

	protected ExtendedButton jButton_Clear = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel41 = null;
	//private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
    // add h.yoshitama 2009/09/24
	protected ExtendedLabel jLabelhuka = null;
	protected ExtendedLabel jLabelhanrei = null;
	protected ExtendedLabel jLabelkanou = null;
	protected ExtendedLabel jLabelhisu = null;
	protected ExtendedLabel jLabel_ableExample = null;
	protected ExtendedLabel jLabel_requiredExample = null;
	protected ExtendedLabel jLabel_disableExample = null;

	/**
	 * This is the default constructor
	 */
	public JKekkaRegisterFrame() {
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
		// edit h.yoshitama 2009/10/19
//		this.setSize(new Dimension(907, 610));
		this.setPreferredSize(ViewSettings.getFrameCommonSize());
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
			// edit s.inoue 20110328
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
			// jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel(), BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridy = 0;
			gridBagConstraints33.gridx = 0;

			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints32.gridy = 0;
			gridBagConstraints32.gridx = 2;

			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.gridx = 2;
			gridBagConstraints31.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints31.anchor = GridBagConstraints.WEST;
			gridBagConstraints31.weightx = 1.0D;

			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints34.gridy = 0;
			gridBagConstraints34.gridx = 1;

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints31);
			jPanel_ButtonArea.add(getJButton_Cancel(), gridBagConstraints32);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints33);
			jPanel_ButtonArea.add(getJButton_Clear(), gridBagConstraints34);
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
			// edit s.inoue 20110328
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

	/**
	 * This method initializes jButton_Clear
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Clear() {
		if (jButton_Clear == null) {
			// edit s.inoue 20110328
//			jButton_Clear = new ExtendedButton();
//			jButton_Clear.setText("クリア(F2)");
//			jButton_Clear.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Clear= new ExtendedButton(
					"Clear","クリア(E)","クリア(ALT+E)",KeyEvent.VK_E,icon);
			jButton_Clear.addActionListener(this);
		}
		return jButton_Clear;
	}

	/**
	 * This method initializes jPanel_NaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_NaviArea() {
		if (jPanel_NaviArea == null) {
			CardLayout cardLayout2 = new CardLayout();
			// edit h.yoshitama 2009/09/24
			cardLayout2.setHgap(10);
//			cardLayout2.setVgap(10);
			cardLayout2.setVgap(5);
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("健診パターンを選択して、健診項目を表示します。値を入力して登録ボタンを押してください。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");

			jLabel_Title = new TitleLabel("tokutei.kekkainput.frame.title");
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
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints26.gridy = 1;
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridx = 0;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.fill = GridBagConstraints.BOTH;
			gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;
			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
			jPanel_TitleArea.add(getJPanel_ExplArea1(), gridBagConstraints26);
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
			jLabelSub2Expl = new ExtendedLabel();
			jLabelSub2Expl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabelSub2Expl.setText("総合コメント欄・結果(文字列)欄でCtrlキー+Enterキー押下して入力ウインドウを表示します。");
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			jPanel_ExplArea2 = new JPanel();
			jPanel_ExplArea2.setName("jPanel4");
			jPanel_ExplArea2.setLayout(gridLayout1);
			jPanel_ExplArea2.add(jLabel_MainExpl, null);
			jPanel_ExplArea2.add(jLabelSub2Expl, null);
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
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			CardLayout cardLayout = new CardLayout();
			// edit s.inoue 20110328
			cardLayout.setHgap(5);
			cardLayout.setVgap(5);
			jPanel = new JPanel();
			jPanel.setLayout(cardLayout);
			jPanel.add(getJPanel1(), getJPanel1().getName());
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setName("jPanel1");
			jPanel1.add(getJPanel2(), BorderLayout.NORTH);
			jPanel1.add(getJPanel12(), BorderLayout.CENTER);
			// add h.yoshitama 2009/09/24
			jPanel1.add(getJPanel9(), BorderLayout.SOUTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.fill = GridBagConstraints.BOTH;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints8.gridwidth = 1;
			gridBagConstraints8.gridy = 0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJPanel3(), gridBagConstraints8);
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

			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.gridx = 8;
			gridBagConstraints36.weighty = 1.0D;
			gridBagConstraints36.weightx = 1.0D;
			gridBagConstraints36.gridy = 0;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.gridx = 4;
			gridBagConstraints35.gridwidth = 4;
			gridBagConstraints35.anchor = GridBagConstraints.WEST;
			gridBagConstraints35.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints35.gridy = 0;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 0;
			gridBagConstraints34.gridy = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 3;

			// edit h.yoshitama 2009/10/19
			//jLabelNenrei = new JLabel();
			jLabelNenrei = new ExtendedLabel();

			jLabelNenrei.setText("年齢");
			jLabelNenrei.setFont(new Font("Dialog", Font.PLAIN, 12));

			// edit h.yoshitama 2009/10/19
			//jLabelBirthDay = new JLabel();
			jLabelBirthDay = new ExtendedLabel();

			jLabelBirthDay.setText("生年月日");
			jLabelBirthDay.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = 0;


			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 6;
			gridBagConstraints12.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints12.gridy = 2;

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 2;

			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.gridx = 7;
			gridBagConstraints14.gridy = 2;
			gridBagConstraints14.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.gridwidth = 3;
			gridBagConstraints15.insets = new Insets(0, 10, 0, 25);

			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.fill = GridBagConstraints.NONE;
			gridBagConstraints16.gridx = 5;
			gridBagConstraints16.gridy = 2;
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 4;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 2;


			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			//gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.insets = new Insets(0, 10, 0, 25);
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 4;
			gridBagConstraints5.weightx = 1.0D;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 4;
			gridBagConstraints6.gridx = 0;

			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 6;
			gridBagConstraints18.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridy = 4;

			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 4;
			gridBagConstraints19.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridy = 3;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.gridy = 4;
			gridBagConstraints20.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints20.gridx = 7;

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints21.gridy = 4;
			gridBagConstraints21.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridx = 3;

			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.gridy = 0;
			gridBagConstraints22.insets = new Insets(0, 10, 0, 25);
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridx = 1;

			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridx = 4;
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.gridy = 4;

			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.fill = GridBagConstraints.NONE;
			gridBagConstraints24.gridx = 5;
			gridBagConstraints24.gridy = 4;
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.insets = new Insets(0, 10, 0, 0);

			jLabel2 = new ExtendedLabel();
			jLabel2.setText("健診実施日");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabelSeirino = new ExtendedLabel();
			jLabelSeirino.setText("受診券整理番号");
			jLabelSeirino.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel1 = new ExtendedLabel();
			jLabel1.setText("健診パターン");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel = new ExtendedLabel();
			jLabel.setText("被保険者証等記号");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel4 = new ExtendedLabel();
			jLabel4.setText("検査センター名称");
			jLabel4.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel4.setVisible(false);
			jLabel3 = new ExtendedLabel();
			jLabel3.setText("被保険者証等番号");
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel5 = new ExtendedLabel();
			jLabel5.setText("受診者氏名");
			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel16 = new ExtendedLabel();
			jLabel16.setText("性別");
			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));

			/* Modified 2008/03/12 若月 VisualEditor 上で各コンポーネントを
			 * 表示できないため、独自メソッドによるインスタンス生成をやめる */
			/* --------------------------------------------------- */
			// 各テキストフィールドを生成・セットアップ
//			setUpExtendedTextField();
			/* --------------------------------------------------- */
			/* 保険者証等記号 */
			jTextField_HokenjyaCode = new ExtendedTextField();
			jTextField_HokenjyaCode.setPreferredSize(new Dimension(140, 20));
			jTextField_HokenjyaCode.setEditable(false);

			/* 保険者証等番号 */
			jTextField_HokenjyaNumber = new ExtendedTextField();
			jTextField_HokenjyaNumber.setPreferredSize(new Dimension(140, 20));
			jTextField_HokenjyaNumber.setEditable(false);

			/* 健診実施日 */
			jTextField_Date = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_Date.setPreferredSize(new Dimension(100, 20));
			jTextField_Date.addActionListener(this);

			/* 氏名 */
			jTextField_Name = new ExtendedTextField();
			jTextField_Name.setPreferredSize(new Dimension(240, 20));
			jTextField_Name.setEditable(false);

			/* 性別 */
			jTextFieldsexName = new ExtendedTextField();
			jTextFieldsexName.setPreferredSize(new Dimension(50,20));
			jTextFieldsexName.setEditable(false);

			/* 受診券整理番号 */
			jTextField_SeiriNo = new ExtendedTextField("", 11, ImeMode.IME_OFF);
			jTextField_SeiriNo.setPreferredSize(new Dimension(140, 20));
			jTextField_SeiriNo.addActionListener(this);
			// add ver2 s.inoue 2009/06/29
			jTextField_SeiriNo.setVisible(false);
			jLabelSeirino.setVisible(false);
			/* --------------------------------------------------- */

			jLabel11 = new ExtendedLabel();
			jLabel11.setText("請求区分");

			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel3.add(jLabel, gridBagConstraints12);
			jPanel3.add(jLabel1, gridBagConstraints13);
			jPanel3.add(jLabel2, gridBagConstraints17);
			// add ver2 s.inoue 2009/06/29
			jPanel3.add(jLabelSeirino, gridBagConstraints23);

			jPanel3.add(jTextField_HokenjyaCode, gridBagConstraints14);
			jPanel3.add(getJComboBox_Pattern(), gridBagConstraints15);
			jPanel3.add(jTextField_Date, gridBagConstraints16);
			// add ver2 s.inoue 2009/06/29
			jPanel3.add(jTextField_SeiriNo, gridBagConstraints24);

//			jPanel3.add(jLabel11, gridBagConstraints6);
//			jPanel3.add(getJComboBox_SeikyuKubun(), gridBagConstraints5);
			jPanel3.add(jLabel11, gridBagConstraints6);
			jPanel3.add(getJComboBox_SeikyuKubun(), gridBagConstraints5);

			jPanel3.add(jLabel3, gridBagConstraints18);
			jPanel3.add(jLabel4, gridBagConstraints19);
			jPanel3.add(jTextField_HokenjyaNumber, gridBagConstraints20);
			jPanel3.add(getJComboBox_KensaKikanName(), gridBagConstraints21);
			jPanel3.add(jLabel5, gridBagConstraints9);
			jPanel3.add(jTextField_Name, gridBagConstraints22);
			jPanel3.add(getJPanel4(), gridBagConstraints10);
			jPanel3.add(getJPanel41(), gridBagConstraints34);
			jPanel3.add(getJPanel6(), gridBagConstraints35);
			jPanel3.add(getJPanel7(), gridBagConstraints36);


		}
		return jPanel3;
	}

//	/**
//	 * 	 各テキストフィールド欄を生成する
//	 */
//	private void setUpExtendedTextField() {
//
//		// 保険者コード表示欄
//		jTextField_HokenjyaCode =
//			getExtendedTextField(new Dimension(100,20),SwingConstants.LEFT,false);
//		jTextField_HokenjyaCode.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//		/* Modified 2008/03/11 若月 健診実施日を編集可能にする。 */
//		/* --------------------------------------------------- */
////		jTextField_Date = getExtendedTextField(null,SwingConstants.LEFT,false);
//		/* --------------------------------------------------- */
//		jTextField_Date = getExtendedTextField(null,SwingConstants.LEFT,true);
//		/* --------------------------------------------------- */
//
//		jTextField_Date.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//		jTextField_HokenjyaNumber =
//			getExtendedTextField(new Dimension(100,20),SwingConstants.LEFT,false);
//		jTextField_HokenjyaNumber.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//		// 氏名欄
//		jTextField_Name =
//			getExtendedTextField(new Dimension(100,20),SwingConstants.LEFT,false);
//		jTextField_Name.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//		// 性別表示欄
//		jTextFieldsexName = getExtendedTextField(new Dimension(30,20),SwingConstants.CENTER,false);
//		jTextFieldsexName.setText("男");
//		jTextFieldsexName.setFont(new Font("Dialog", Font.PLAIN, 12));
//	}


// del s.inoue 2009/12/25 いらない?削除
//	/**
//	 * 指定サイズ・アライメント・ReadOnly属性の
//	 * テキストフィールドを生成する
//	 * @param dimension 幅・高さ
//	 * @param alignment アライメント
//	 * @param isEditable ReadOnlyフラグ
//	 * @return テキストフィールド
//	 */
//	private ExtendedTextField getExtendedTextField(
//			Dimension dimension,
//			int alignment,
//			boolean isEditable) {
//
//		ExtendedTextField text = new ExtendedTextField();
//		text.setHorizontalAlignment(alignment);
//		if (dimension != null) text.setPreferredSize(dimension);
//		text.setEditable(isEditable);
//		return text;
//	}

//	/**
//	 * This method initializes jTextField_HokenjyaCode
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_HokenjyaCode() {
//		if (jTextField_HokenjyaCode == null) {
//			jTextField_HokenjyaCode = new ExtendedTextField();
//			jTextField_HokenjyaCode.setPreferredSize(new Dimension(100, 20));
//		}
//		return jTextField_HokenjyaCode;
//	}

//	/**
//	 * This method initializes jTextField_Date
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_Date() {
//		if (jTextField_Date == null) {
//			jTextField_Date = new ExtendedTextField();
//		}
//		return jTextField_Date;
//	}

	/**
	 * This method initializes jComboBox_Pattern
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_Pattern() {
		if (jComboBox_Pattern == null) {
			jComboBox_Pattern = new ExtendedComboBox();
			//jComboBox_Pattern.setPreferredSize(new Dimension(150, 20));
			jComboBox_Pattern.setPreferredSize(new Dimension(240, 20));
			jComboBox_Pattern.addItemListener(this);
			// del s.inoue 2009/12/21
			// jComboBox_Pattern.addKeyListener(this);
		}
		return jComboBox_Pattern;
	}

	/**
	 * This method initializes jPanel8
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel8() {
//		if (jPanel8 == null) {
//			jLabel4 = new ExtendedLabel();
//			jLabel4.setText("健診機関名称");
//			jLabel4.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel3 = new ExtendedLabel();
//			jLabel3.setText("被保険者証等番号");
//			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));
//		}
//		return jPanel8;
//	}

//	/**
//	 * This method initializes jTextField_HokenjyaNumber
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_HokenjyaNumber() {
//		if (jTextField_HokenjyaNumber == null) {
//			jTextField_HokenjyaNumber = new ExtendedTextField();
//			jTextField_HokenjyaNumber.setPreferredSize(new Dimension(100, 20));
//		}
//		return jTextField_HokenjyaNumber;
//	}

	/**
	 * This method initializes jPanel12
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			// edit h.yoshitama 2009/09/24
			//jPanel12.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			jPanel12.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			jPanel12.add(getJPanel15(), BorderLayout.NORTH);
			jPanel12.add(getJPanel_Table(), BorderLayout.CENTER);
		}
		return jPanel12;
	}

	// add h.yoshitama 2009/09/24
	/**
	 * This method initializes jPanel12
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			jPanel9.add(getJPanel8(), BorderLayout.WEST);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel13
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel13() {
//		if (jPanel13 == null) {
////			jLabel6 = new ExtendedLabel();
////			jLabel6.setText("判定結果");
////			jLabel6.setFont(new Font("Dialog", Font.PLAIN, 12));
//		}
//		return jPanel13;
//	}

	/**
	 * This method initializes jPanel_Table
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Table() {
		if (jPanel_Table == null) {
			jPanel_Table = new JPanel();
			jPanel_Table.setLayout(new BorderLayout());
			// add h.yoshitama 2009/09/24
			//jPanel_Table.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			jPanel_Table.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		}
		return jPanel_Table;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			// edit s.inoue 20110328
//			jButton_Register = new ExtendedButton();
//			jButton_Register.setText("登録(F12)");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Register.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Register= new ExtendedButton(
					"Close","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	/**
	 * This method initializes jPanel10
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel10() {
//		if (jPanel10 == null) {
//			jLabel5 = new ExtendedLabel();
//			jLabel5.setText("受診者氏名");
//			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));
//		}
//		return jPanel10;
//	}

//	/**
//	 * This method initializes jTextField_Name
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_Name() {
//		if (jTextField_Name == null) {
//			jTextField_Name = new ExtendedTextField();
//			jTextField_Name.setPreferredSize(new Dimension(100, 20));
//		}
//		return jTextField_Name;
//	}

	/**
	 * This method initializes jPanel11
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints2.gridx = 0;
			jLabel8 = new ExtendedLabel();
			jLabel8.setText("総合コメント");
			jLabel8.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel11 = new JPanel();
			jPanel11.setLayout(new GridBagLayout());
			jPanel11.add(jLabel8, gridBagConstraints2);
			jPanel11.add(getJScrollPane1(), gridBagConstraints7);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jEditorPane_Comment
	 *
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane_Comment() {
		if (jEditorPane_Comment == null) {
			jEditorPane_Comment = new ExtendedEditorPane(ImeMode.IME_ZENKAKU);
			jEditorPane_Comment.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			// edit s.inoue 2009/11/11
			jEditorPane_Comment.addMouseListener(this); // マウスリスナを登録
		}
		return jEditorPane_Comment;
	}

	/**
	 * This method initializes jPanel15
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel15() {
		if (jPanel15 == null) {
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.gridx = 0;
			gridBagConstraints110.gridwidth = 2;
			gridBagConstraints110.anchor = GridBagConstraints.WEST;
			gridBagConstraints110.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints110.gridy = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.gridheight = 3;
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 0;
			jLabel6 = new ExtendedLabel();
			jLabel6.setText("保健指導階層化");
//			jLabel6.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel9 = new ExtendedLabel();
			jLabel9.setText("メタボリック<br>シンドローム判定");
//			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));

//			jLabel11 = new ExtendedLabel();
//			jLabel11.setText("請求区分");
//			jLabel11.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.NONE;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.weightx = 0.0D;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 0.0D;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints3.gridx = 1;
			jPanel15 = new JPanel();
			jPanel15.setLayout(new GridBagLayout());
			jPanel15.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel15.add(jLabel9, gridBagConstraints);
			jPanel15.add(getJComboBox_MetaboHantei(), gridBagConstraints3);
			jPanel15.add(getJComboBox_HokenSidouLevel(), gridBagConstraints4);
			jPanel15.add(jLabel6, gridBagConstraints1);
			jPanel15.add(getJPanel11(), gridBagConstraints11);
			//jPanel15.add(getJPanel5(), gridBagConstraints110);
		}
		return jPanel15;
	}

	/**
	 * This method initializes jPanel16
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel16() {
//		if (jPanel16 == null) {
//			jLabel9 = new ExtendedLabel();
//			jLabel9.setText("メタボリックシンドローム判定");
//			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));
//		}
//		return jPanel16;
//	}

	public void actionPerformed( ActionEvent e )
	{

	}

	public void itemStateChanged(ItemEvent e)
	{

	}

	/**
	 * This method initializes jScrollPane1
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new Dimension(300, 23));
			jScrollPane1.setViewportView(getJEditorPane_Comment());
		}
		return jScrollPane1;
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
	 * This method initializes jComboBox_KensaKikanName
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_KensaKikanName() {
		if (jComboBox_KensaKikanName == null) {
			jComboBox_KensaKikanName = new ExtendedComboBox();
			jComboBox_KensaKikanName.setPreferredSize(new Dimension(140, 20));
			jComboBox_KensaKikanName.setVisible(false);
			jComboBox_KensaKikanName.addItemListener(this);
			jComboBox_KensaKikanName.addKeyListener(this);
		}
		return jComboBox_KensaKikanName;
	}

	/**
	 * This method initializes jPanel14
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel14() {
//		if (jPanel14 == null) {
//			jLabel11 = new ExtendedLabel();
//			jLabel11.setText("請求区分");
//			jLabel11.setFont(new Font("Dialog", Font.PLAIN, 12));
//		}
//		return jPanel14;
//	}

	/**
	 * This method initializes jComboBox_HokenSidouLevel
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_HokenSidouLevel() {
		if (jComboBox_HokenSidouLevel == null) {
			jComboBox_HokenSidouLevel = new ExtendedComboBox();
			jComboBox_HokenSidouLevel.setPreferredSize(new Dimension(260, 20));
			jComboBox_HokenSidouLevel.addKeyListener(this);
		}
		return jComboBox_HokenSidouLevel;
	}

	/**
	 * This method initializes jComboBox_MetaboHantei
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_MetaboHantei() {
		if (jComboBox_MetaboHantei == null) {
			jComboBox_MetaboHantei = new ExtendedComboBox();
			jComboBox_MetaboHantei.setPreferredSize(new Dimension(260, 20));
			jComboBox_MetaboHantei.addKeyListener(this);
		}
		return jComboBox_MetaboHantei;
	}

	/**
	 * This method initializes jComboBox_SeikyuKubun
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_SeikyuKubun() {
		if (jComboBox_SeikyuKubun == null) {
			jComboBox_SeikyuKubun = new ExtendedComboBox();
			//jComboBox_SeikyuKubun.setPreferredSize(new Dimension(260, 20));
			jComboBox_SeikyuKubun.setPreferredSize(new Dimension(240, 20));
			jComboBox_SeikyuKubun.addKeyListener(this);
		}
		return jComboBox_SeikyuKubun;
	}

	/**
	 * This method initializes jButton_Cancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new ExtendedButton();
			jButton_Cancel.setText("キャンセル");
			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Cancel.setVisible(false);
			jButton_Cancel.addActionListener(this);
		}
		return jButton_Cancel;
	}

	/* Added 2008/03/12 若月 ComboBox でのEnterキーによるフォーカス移動機能追加 */
	/* --------------------------------------------------- */
	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
	/* --------------------------------------------------- */

	/**
	 * This method initializes jTextFieldBirthDay
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldBirthDay() {
		if (jTextFieldBirthDay == null) {
			// edit h.yoshitama 2009/10/19
			//jTextFieldBirthDay = new JTextField();
			jTextFieldBirthDay = new ExtendedTextField();

			jTextFieldBirthDay.setPreferredSize(new Dimension(80, 20));
			jTextFieldBirthDay.setEditable(false);
		}
		return jTextFieldBirthDay;
	}

	/**
	 * This method initializes jTextFieldNenrei
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNenrei() {
		if (jTextFieldNenrei == null) {
			// edit h.yoshitama 2009/10/19v
			//jTextFieldNenrei = new JTextField();
			jTextFieldNenrei = new ExtendedTextField();

			jTextFieldNenrei.setPreferredSize(new Dimension(50, 20));
			jTextFieldNenrei.setEditable(false);
		}
		return jTextFieldNenrei;
	}

	/**
	 * This method initializes jPanel4
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.setPreferredSize(new Dimension(5, 5));
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel41
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel41() {
		if (jPanel41 == null) {
			jPanel41 = new JPanel();
			jPanel41.setLayout(new GridBagLayout());
			jPanel41.setPreferredSize(new Dimension(5, 5));
		}
		return jPanel41;
	}

	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanel5() {
//		if (jPanel5 == null) {
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.anchor = GridBagConstraints.EAST;
//			gridBagConstraints5.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints5.gridx = 1;
//			gridBagConstraints5.gridy = 0;
//			gridBagConstraints5.weightx = 1.0D;
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.anchor = GridBagConstraints.WEST;
//			gridBagConstraints6.gridy = 0;
//			gridBagConstraints6.gridx = 0;
//			jPanel5 = new JPanel();
//			jPanel5.setLayout(new GridBagLayout());
//			jPanel5.add(jLabel11, gridBagConstraints6);
//			jPanel5.add(getJComboBox_SeikyuKubun(), gridBagConstraints5);
//		}
//		return jPanel5;
//	}

    // add h.yoshitama 2009/09/24
	/**
	 * This method initializes jPanel8
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
			gridBagConstraints88.gridx = 0;
			gridBagConstraints88.gridy = 0;
			jLabelhanrei = new ExtendedLabel();
			jLabelhanrei.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabelhanrei.setPreferredSize(new Dimension(50, 16));
			jLabelhanrei.setText("凡例");
			GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
			gridBagConstraints89.gridx = 6;
			gridBagConstraints89.anchor = GridBagConstraints.WEST;
			gridBagConstraints89.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints89.gridy = 0;
			jLabelhuka = new ExtendedLabel();
			jLabelhuka.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelhuka.setText("入力不可能項目");
			GridBagConstraints gridBagConstraints87 = new GridBagConstraints();
			gridBagConstraints87.gridx = 4;
			gridBagConstraints87.anchor = GridBagConstraints.WEST;
			gridBagConstraints87.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints87.gridy = 0;
			jLabelkanou = new ExtendedLabel();
			jLabelkanou.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelkanou.setText("入力項目");
			GridBagConstraints gridBagConstraints86 = new GridBagConstraints();
			gridBagConstraints86.gridx = 2;
			gridBagConstraints86.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints86.gridy = 0;
			jLabelhisu = new ExtendedLabel();
			jLabelhisu.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelhisu.setText("入力必須項目");
			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
			gridBagConstraints90.gridx = 5;
			gridBagConstraints90.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints90.gridy = 0;
			jLabel_disableExample = new ExtendedLabel();
			jLabel_disableExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_disableExample.setPreferredSize(new Dimension(50, 20));
			jLabel_disableExample.setOpaque(true);
			GridBagConstraints gridBagConstraints85 = new GridBagConstraints();
			gridBagConstraints85.gridx = 3;
			gridBagConstraints85.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints85.gridy = 0;
			jLabel_ableExample = new ExtendedLabel();
			jLabel_ableExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_ableExample.setPreferredSize(new Dimension(50, 20));
			jLabel_ableExample.setOpaque(true);
			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
			gridBagConstraints84.gridx = 1;
			gridBagConstraints84.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints84.gridy = 0;
			jLabel_requiredExample = new ExtendedLabel();
			jLabel_requiredExample.setPreferredSize(new Dimension(50, 20));
			jLabel_requiredExample.setOpaque(true);
			jLabel_requiredExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jPanel8 = new JPanel();
			jPanel8.setLayout(new GridBagLayout());
			jPanel8.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel8.setOpaque(false);
			jPanel8.add(jLabel_requiredExample, gridBagConstraints84);
			jPanel8.add(jLabel_ableExample, gridBagConstraints85);
			jPanel8.add(jLabel_disableExample, gridBagConstraints90);
			jPanel8.add(jLabelhisu, gridBagConstraints86);
			jPanel8.add(jLabelkanou, gridBagConstraints87);
			jPanel8.add(jLabelhanrei, gridBagConstraints88);
			jPanel8.add(jLabelhuka, gridBagConstraints89);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints30.gridx = 5;
			gridBagConstraints30.gridy = 0;
			gridBagConstraints30.weightx = 1.0;
			gridBagConstraints30.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.anchor = GridBagConstraints.WEST;
			gridBagConstraints29.gridx = 4;
			gridBagConstraints29.gridy = 0;
			gridBagConstraints29.insets = new Insets(0, 20, 0, 0);
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.anchor = GridBagConstraints.WEST;
			gridBagConstraints28.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints28.gridx = 3;
			gridBagConstraints28.gridy = 0;
			gridBagConstraints28.weightx = 1.0;
			gridBagConstraints28.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.anchor = GridBagConstraints.WEST;
			gridBagConstraints27.gridx = 2;
			gridBagConstraints27.gridy = 0;
			gridBagConstraints27.insets = new Insets(0, 20, 0, 0);
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridx = 1;
			gridBagConstraints24.gridy = 0;
			gridBagConstraints24.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.gridy = 0;
			gridBagConstraints23.fill = GridBagConstraints.NONE;
			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.add(jLabel16, gridBagConstraints23);
			jPanel6.add(jTextFieldsexName, gridBagConstraints24);
			jPanel6.add(jLabelBirthDay, gridBagConstraints27);
			jPanel6.add(getJTextFieldBirthDay(), gridBagConstraints28);
			jPanel6.add(jLabelNenrei, gridBagConstraints29);
			jPanel6.add(getJTextFieldNenrei(), gridBagConstraints30);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new GridBagLayout());
		}
		return jPanel7;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
