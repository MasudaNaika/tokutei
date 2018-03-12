package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedDateChooser;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenComboboxControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFormattedControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

//import org.eclipse.swt.widgets.IME;

/**
 * 保険者マスタメンテナンス画面
 */
public class JHokenjyaMasterMaintenanceEditFrame
extends JFrame
implements ActionListener,KeyListener,FocusListener,ItemListener {

	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_TableArea = null;
	protected JPanel jPanel_HokenjyaNumber2_1 = null;
	protected JPanel jPanel_HokenjyaNumber2_2 = null;
	protected JPanel jPanel_HokenjyaNumber2_3 = null;
	protected ExtendedLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedOpenTextControl jTextField_HokenjyaNumber = null;
	protected ExtendedOpenTextControl jTextField_HokenjyaName = null;
	protected ExtendedOpenFormattedControl jTextField_Zipcode = null;
	protected ExtendedOpenTextControl jTextField_Address = null;
	protected ExtendedOpenTextControl jTextField_Chiban = null;
	protected ExtendedOpenTextControl jTextField_TEL = null;
	protected ExtendedOpenTextControl jTextField_Kigo = null;
	protected ExtendedOpenTextControl jTextField_HonninGairai = null;
	protected ExtendedOpenTextControl jTextField_KazokuGairai = null;
	protected ExtendedOpenTextControl jTextField_HonninNyuin = null;
	protected ExtendedOpenTextControl jTextField_KazokuNyuin = null;
	protected ExtendedOpenTextControl jTextField_KihonTanka = null;
	protected ExtendedOpenTextControl jTextField_HinketsuTanka = null;
	protected ExtendedOpenTextControl jTextField_SindenzuTanka = null;
	protected ExtendedOpenTextControl jTextField_GanteiTanka = null;
	protected ExtendedOpenTextControl jTextField_NingenDocTanka = null;
	protected ExtendedOpenTextControl jTextField_HinketuCode = null;
	protected ExtendedOpenTextControl jTextField_SindenzuCode = null;
	protected ExtendedOpenTextControl jTextField_GanteiCode = null;
	protected ExtendedOpenTextControl jTextField_NingenDocCode = null;
	protected ExtendedTextField jTextField_TankaHantei = null;

	// edit s.inoue 2011/03/07
	protected ExtendedButton jButton_ORCA = null;
	protected ExtendedButton jButton_Register = null;
	protected ExtendedButton jButton_RegisterAdd = null;
	protected ExtendedButton jButton_Clear = null;
	protected ExtendedButton jButton_RegisterYukoukigenEdit = null;
	protected ExtendedButton jButton_RegisterYukoukigenDelete = null;
	protected ExtendedButton jButton_End = null;
	// eidt s.inoue 2012/11/15
	// protected ExtendedComboBox jComboBox_ItakuKubun = null;
	protected ExtendedOpenComboboxControl jComboBox_ItakuKubun = null;

	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel11 = null;
	protected ExtendedLabel jLabel13 = null;
	protected ExtendedLabel jLabel14 = null;
	protected ExtendedLabel jLabel15 = null;
	protected ExtendedLabel jLabel16 = null;
	protected ExtendedLabel jLabel17 = null;
	protected ExtendedLabel jLabel18 = null;
	protected ExtendedLabel jLabel19 = null;
	protected ExtendedLabel jLabel110 = null;
	protected ExtendedLabel jLabel111 = null;
	protected ExtendedLabel jLabel112 = null;
	protected ExtendedLabel jLabel113 = null;
	protected ExtendedLabel jLabel114 = null;
	protected ExtendedLabel jLabel115 = null;
	protected ExtendedLabel jLabel116 = null;
	protected ExtendedLabel jLabel117 = null;
	protected ExtendedLabel jLabel118 = null;
	protected ExtendedLabel jLabel119 = null;
	protected ExtendedLabel jLabel120 = null;
	protected ExtendedLabel jLabel121 = null;
	protected ExtendedLabel jLabel122 = null;
	protected ExtendedLabel jLabel123 = null;
	protected ExtendedLabel jLabel124 = null;
	protected ExtendedLabel jLabel130 = null;

	protected ExtendedButton jButton_Cancel = null;
	protected ExtendedLabel jLabel1231 = null;
	protected ExtendedLabel jLabel1232 = null;
	protected ExtendedLabel jLabel12 = null;
	protected ExtendedLabel jLabel125 = null;
	protected ExtendedLabel jLabel126 = null;
	protected ExtendedLabel jLabel127 = null;
	protected ExtendedLabel jLabel128 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel1 = null;
	protected JPanel jPanel3 = null;
	protected JPanel jPanel4 = null;
	protected JPanel jPanel5 = null;
	protected JPanel jPanel6 = null;
	protected JPanel jPanel7 = null;
	protected JPanel jPanel8 = null;
	protected JPanel jPanel9 = null;
	protected JPanel jPanel10 = null;
	protected JPanel jPanel11 = null;
	protected JPanel jPanel12 = null;
	protected JPanel jPanel13 = null;
	protected JPanel jPanel14 = null;
	protected JPanel jPanelHidden = null;
	protected JPanel jPanelKihon = null;
	protected JPanel jPanelDoc = null;
	protected ButtonGroup groupHantei = new ButtonGroup();
	protected ExtendedRadioButton jRadioButton_Kihon = null;
	protected ExtendedRadioButton jRadioButton_Doc = null;

	protected ExtendedButton jButton_ClearAdd = null;
	private ExtendedLabel jLabelHokenjyaNum = null;
	private ExtendedLabel jLabelHokenjyaNameNum = null;
	private ExtendedLabel jLabelYubinNum = null;
	private ExtendedLabel jLabelShozaitiNum = null;
	private ExtendedLabel jLabelTibanNum = null;
	private ExtendedLabel jLabelTelNum = null;
	private ExtendedLabel jLabelKigouNum = null;
	private ExtendedLabel jLabelKihonNum = null;
	private ExtendedLabel jLabelHinketuNum = null;
	private ExtendedLabel jLabelSindenzuNum = null;
	private ExtendedLabel jLabelGanteiNum = null;
	private ExtendedLabel jLabelNingenDocNum = null;
	// add s.inoue 2010/01/06
	private ExtendedLabel jLabelYukoukigen = null;
	private ExtendedLabel jLabelTilda = null;
	protected ExtendedDateChooser jTextField_YukoukigenFrom = null;
	protected ExtendedDateChooser jTextField_YukoukigenTo = null;
	protected ExtendedLabel jLabel_HokenjyaTitle = null;
	protected ExtendedLabel jLabel_TankaTitle = null;

	/**
	 * This is the default constructor
	 */
	public JHokenjyaMasterMaintenanceEditFrame() {
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
		this.addKeyListener(this);
	}

	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJPanel_Content() {
		if (jPanel_Content == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(2);
			jPanel_Content = new JPanel();
			jPanel_Content.setLayout(borderLayout);

			// add s.inoue 2012/11/12
			jLabel_Title = new TitleLabel("tokutei.hokenja-mastermaintenance-edit.frame.title","tokutei.hokenja-mastermaintenance-edit.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

//			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel_MainArea(), BorderLayout.CENTER);
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
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
			// add s.inoue 2012/11/13
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridy = 0;
			gridBagConstraints53.gridx = 0;
			gridBagConstraints53.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints53.anchor = GridBagConstraints.WEST;
			gridBagConstraints53.gridwidth=5;

			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.gridx = 1;
			gridBagConstraints57.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints57.gridy = 1;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.gridy = 1;
			gridBagConstraints56.gridx = 0;
			// edit s.inoue
			gridBagConstraints56.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.gridy = 1;
			gridBagConstraints55.gridx = 2;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridy = 1;
			gridBagConstraints54.anchor = GridBagConstraints.WEST;
			gridBagConstraints54.weightx = 1.0D;
			gridBagConstraints54.gridx = 2;
			gridBagConstraints54.insets = new Insets(0, 5, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			// edit s.inoue 2010/01/22
			jPanel_ButtonArea.add(getJPanel1(), gridBagConstraints54);
			jPanel_ButtonArea.add(getJButton_RegisterAdd(), gridBagConstraints54);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints56);
			// edit s.inoue 2010/01/22
			// jPanel_ButtonArea.add(getJButton_ClearAdd(), gridBagConstraints57);

			// add s.inoue 2012/11/13
			jPanel_ButtonArea.add(getJPanel_TitleArea(), gridBagConstraints53);
		}
		return jPanel_ButtonArea;
	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
//	protected JPanel jPanel_TitleArea = null;
//	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea() {
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
//			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

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
//			jLabel_MainExpl = new ExtendedLabel();
//			jLabel_MainExpl.setText("保険者番号を入力後、Enterキーを押して保険者情報を作成します。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			// edit ver2 s.inoue 2009/06/04
////			jLabel_Title = new ExtendedLabel();
////			jLabel_Title.setText("保険者情報編集");
////			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
////			jLabel_Title.setBackground(new Color(153, 204, 255));
////			jLabel_Title.setForeground(new Color(51, 51, 51));
////			jLabel_Title.setOpaque(true);
////			jLabel_Title.setName("jLabel");
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
//
//			jPanel_NaviArea = new JPanel();
//			jPanel_NaviArea.setLayout(cardLayout2);
//			jPanel_NaviArea.add(getJPanel_TitleArea(), getJPanel_TitleArea().getName());
//		}
//		return jPanel_NaviArea;
//	}

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
//			jPanel_TitleArea.add(getJPanel_ExplArea2(), null);
//		}
//		return jPanel_TitleArea;
//	}

	// edit n.ohkubo 2015/03/01　未使用なので削除　start
//	/**
//	 * This method initializes jPanel_ExplArea2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea2() {
//		if (jPanel_ExplArea2 == null) {
//			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
//			gridBagConstraints60.gridx = 0;
//			gridBagConstraints60.weightx = 1.0D;
//			gridBagConstraints60.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints60.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints60.gridy = 1;
//			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
//			gridBagConstraints59.gridx = 0;
//			gridBagConstraints59.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints59.weightx = 1.0D;
//			gridBagConstraints59.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints59.gridy = 0;
//			jLabal_SubExpl = new ExtendedLabel();
//			jLabal_SubExpl.setText("各項目を入力後、「登録」ボタンを押して入力内容を登録します。");
//			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
////			gridLayout1.setRows(2);
//			jPanel_ExplArea2 = new JPanel();
//			jPanel_ExplArea2.setName("jPanel4");
//			jPanel_ExplArea2.setLayout(new GridBagLayout());
//			jPanel_ExplArea2.add(jLabel_MainExpl, gridBagConstraints59);
//			jPanel_ExplArea2.add(jLabal_SubExpl, gridBagConstraints60);
//		}
//		return jPanel_ExplArea2;
//	}
	// edit n.ohkubo 2015/03/01　未使用なので削除　end

//	/**
//	 * This method initializes jPanel_ExplArea1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea1() {
//		if (jPanel_ExplArea1 == null) {
//			jPanel_ExplArea1 = new JPanel();
//			jPanel_ExplArea1.setLayout(new GridBagLayout());
//		}
//		return jPanel_ExplArea1;
//	}

	/**
	 * This method initializes jPanel_MainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_MainArea() {
		if (jPanel_MainArea == null) {
			jPanel_MainArea = new JPanel();
			BorderLayout borderLayout = new BorderLayout();
			// edit s.inoue 2010/01/21 2→1
			borderLayout.setHgap(1);

			jPanel_MainArea.setLayout(borderLayout);
			jPanel_MainArea.add(getJPanel_DrawArea(), BorderLayout.NORTH);
			// edit s.inoue 2010/01/21
			// jPanel_MainArea.add(getJPanel_TableArea(), BorderLayout.NORTH);
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_TableArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TableArea() {
		if (jPanel_TableArea == null) {
			GridBagConstraints gridBagConstraints410 = new GridBagConstraints();
			gridBagConstraints410.gridx = 0;
			gridBagConstraints410.gridy = 0;
			gridBagConstraints410.fill = GridBagConstraints.HORIZONTAL;

			GridBagConstraints gridBagConstraints411 = new GridBagConstraints();
			gridBagConstraints411.gridx = 3;
			gridBagConstraints411.gridy =0;
			gridBagConstraints411.fill = GridBagConstraints.HORIZONTAL;

			jPanel_TableArea = new JPanel();
			jPanel_TableArea.setLayout(new GridBagLayout());
			// del s.inoue 2010/01/21
//			jPanel_TableArea.setName("jPanel_TableArea");
//			jPanel_TableArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel_TableArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//			jPanel_TableArea.add(getJPanel11(),gridBagConstraints410);

//			jPanel_TableArea.add(getJButton_RegisterYukouKigenDelete(),gridBagConstraints411);
//			jPanel_TableArea.setLayout(new GridBagLayout());
		}
		return jPanel_TableArea;
	}

	/**
	 * This method initializes jPanel_DrawArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_DrawArea() {
		if (jPanel_DrawArea == null) {
//			GridBagConstraints gridBagConstraints510 = new GridBagConstraints();
//			gridBagConstraints510.gridx = 2;
//			gridBagConstraints510.weightx = 1.0D;
//			gridBagConstraints510.gridy = 0;
			GridBagConstraints gridBagConstraints410 = new GridBagConstraints();
			gridBagConstraints410.gridx = 2;
			gridBagConstraints410.weightx = 1.0D;
			gridBagConstraints410.gridy = 0;

// del s.inoue 2010/01/25
//			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
//			gridBagConstraints110.fill = GridBagConstraints.BOTH;
//			gridBagConstraints110.insets = new Insets(10, 0, 0, 0);
//			gridBagConstraints110.gridx = 0;
//			// edit s.inoue 2010/01/21 1→2
//			gridBagConstraints110.gridy = 2;

			// add s.inoue 2010/01/21
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.fill = GridBagConstraints.BOTH;
			gridBagConstraints111.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints111.gridx = 0;
			gridBagConstraints111.gridy = 1;

//			GridBagConstraints gridBagConstraints210 = new GridBagConstraints();
//			gridBagConstraints210.fill = GridBagConstraints.BOTH;
//			gridBagConstraints210.insets = new Insets(10, 0, 0, 0);
//			gridBagConstraints210.gridx = 0;
//			gridBagConstraints210.gridy = 2;

			GridBagConstraints gridBagConstraints310 = new GridBagConstraints();
			gridBagConstraints310.fill = GridBagConstraints.BOTH;
			gridBagConstraints310.gridx = 0;
			gridBagConstraints310.gridy = 0;

			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 1;
			gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints18.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints18.gridy = 0;
//			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
//			gridBagConstraints82.gridx = 1;
//			gridBagConstraints82.fill = GridBagConstraints.BOTH;
//			gridBagConstraints82.gridy = 9;
//			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
//			gridBagConstraints71.gridx = 1;
//			gridBagConstraints71.fill = GridBagConstraints.BOTH;
//			gridBagConstraints71.gridy = 18;
//			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
//			gridBagConstraints51.gridx = 1;
//			gridBagConstraints51.fill = GridBagConstraints.BOTH;
//			gridBagConstraints51.gridy = 17;
//			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
//			gridBagConstraints49.gridx = 1;
//			gridBagConstraints49.fill = GridBagConstraints.BOTH;
//			gridBagConstraints49.gridy = 16;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridx = 1;
//			gridBagConstraints3.fill = GridBagConstraints.BOTH;
//			gridBagConstraints3.gridy = 14;
			jLabel1232 = new ExtendedLabel();
			jLabel1232.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1232.setText("\uff05");
			jLabel1231 = new ExtendedLabel();
			jLabel1231.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1231.setText("\uff05");
			jLabel124 = new ExtendedLabel();
			jLabel124.setText("％");
			jLabel124.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel123 = new ExtendedLabel();
			jLabel123.setText("％");
			jLabel123.setFont(new Font("Dialog", Font.PLAIN, 12));
// move s.inoue 2010/01/25
//			jLabel122 = new ExtendedLabel();
//			jLabel122.setText("（1：個別健診 2：集団健診）");
//			jLabel122.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel122.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 2;
			gridBagConstraints41.anchor = GridBagConstraints.EAST;

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.gridx = 2;
			gridBagConstraints40.anchor = GridBagConstraints.EAST;

//			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
//			gridBagConstraints38.gridx = 2;
//			gridBagConstraints38.anchor = GridBagConstraints.EAST;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridx = 2;
			gridBagConstraints39.anchor = GridBagConstraints.EAST;
//			jLabel119 = new ExtendedLabel();
//			jLabel119.setText("単価（眼底検査）");
//			jLabel119.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel118 = new ExtendedLabel();
//			jLabel118.setText("単価（心電図検査）");
//			jLabel118.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel117 = new ExtendedLabel();
//			jLabel117.setText("単価（貧血検査）");
//			jLabel117.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel114 = new ExtendedLabel();
			jLabel114.setText("眼底検査コード");
			jLabel114.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel114.setVisible(false);
			jLabel113 = new ExtendedLabel();
			jLabel113.setText("心電図検査コード");
			jLabel113.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel113.setVisible(false);
			jLabel112 = new ExtendedLabel();
			jLabel112.setText("貧血検査コード");
			jLabel112.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel112.setVisible(false);
			jLabel111 = new ExtendedLabel();
			jLabel111.setText("単価（基本的な健診）");
			jLabel111.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel110 = new ExtendedLabel();
			jLabel110.setText("委託料単価区分");
			jLabel110.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel19 = new ExtendedLabel();
//			jLabel19.setText("記号");
//			jLabel19.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel19.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabel19.setVisible(true);
//			jLabel18 = new ExtendedLabel();
//			jLabel18.setText("電話番号");
//			jLabel18.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel17 = new ExtendedLabel();
//			jLabel17.setText("地番方書");
//			jLabel17.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel16 = new ExtendedLabel();
//			jLabel16.setText("所在地");
//			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel15 = new ExtendedLabel();
//			jLabel15.setText("郵便番号");
//			jLabel15.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel14 = new ExtendedLabel();
//			jLabel14.setText("保険者名称");
//			jLabel14.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel13 = new ExtendedLabel();
//			jLabel13.setText("保険者番号");
//			jLabel13.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel130 = new ExtendedLabel();
//			jLabel130.setText("単価（人間ドック）");
//			jLabel130.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel121 = new ExtendedLabel();
			jLabel121.setText("給付割合（家族・外来）");
			jLabel121.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel120 = new ExtendedLabel();
			jLabel120.setText("給付割合（本人・入院）");
			jLabel120.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel116 = new ExtendedLabel();
			jLabel116.setText("給付割合（家族・外来）");
			jLabel116.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel115 = new ExtendedLabel();
			jLabel115.setText("給付割合（本人・外来）");
			jLabel115.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			jLabel = new ExtendedLabel();
			jLabel.setText(" ");

			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridx = 3;
			gridBagConstraints26.weightx = 1.0;

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.gridx = 3;
			gridBagConstraints25.weightx = 1.0;

			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints24.gridx = 3;
			gridBagConstraints24.weightx = 1.0;

			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.gridx = 3;
			gridBagConstraints23.weightx = 1.0;
			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new GridBagLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");
			jPanel_DrawArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
			// del s.inoue 2010/01/07
//			jPanel_DrawArea.setPreferredSize(new Dimension(420, 430));
//			jPanel_DrawArea.add(jLabel112, gridBagConstraints38);
			jPanel_DrawArea.add(getJTextField_HinketuCode(), gridBagConstraints23);
//			jPanel_DrawArea.add(jLabel113, gridBagConstraints39);
			jPanel_DrawArea.add(getJTextField_SindenzuCode(), gridBagConstraints24);
			jPanel_DrawArea.add(jLabel114, gridBagConstraints40);
			jPanel_DrawArea.add(getJTextField_GanteiCode(), gridBagConstraints25);
			jPanel_DrawArea.add(jLabel, gridBagConstraints);
			jPanel_DrawArea.add(getJTextField_NingenDocCode(), gridBagConstraints26);

//			jPanel_DrawArea.add(getJPanel1(), gridBagConstraints3);
//			jPanel_DrawArea.add(getJPanel2(), gridBagConstraints49);
//			jPanel_DrawArea.add(getJPanel3(), gridBagConstraints51);
//			jPanel_DrawArea.add(getJPanel4(), gridBagConstraints71);
//			jPanel_DrawArea.add(getJPanel5(), gridBagConstraints82);
			jPanel_DrawArea.add(getJPanel6(), gridBagConstraints18);
//			jPanel_DrawArea.add(getJPanel10(), gridBagConstraints510);
			// edit s.inoue 2010/01/07
			// jPanel_DrawArea.add(getJPanel11(), gridBagConstraints110);

			// edit s.inoue 2010/01/25
			// 一番下
			// jPanel_DrawArea.add(getJPanel7(), gridBagConstraints110);

			// jPanel_DrawArea.add(getJPanel7(), gridBagConstraints210);
			// jPanel_DrawArea.add(getJPanel11(), gridBagConstraints210);

			// edit s.inoue 2010/01/25
			// 中
			// jPanel_DrawArea.add(getJPanel_TableArea(), gridBagConstraints111);
			// add s.inoue 2010/01/25
			jPanel_DrawArea.add(getJPanel12(),gridBagConstraints111);

			// 一番上
			jPanel_DrawArea.add(getJPanel8(), gridBagConstraints310);
			jPanel_DrawArea.add(getJPanel9(), gridBagConstraints410);
		}
		return jPanel_DrawArea;
	}

	// add s.inoue 2010/01/11
	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJRabelHidden() {
		if (jPanelHidden == null) {
			jPanelHidden = new JPanel();
			jPanelHidden.setLayout(new GridBagLayout());
		}
		return jPanelHidden;
	}

	// add s.inoue 2010/01/06
	/**
	 * This method initializes jTextField_NingenDocTanka
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedDateChooser getJTextField_YukoukigenFrom() {
		if (jTextField_YukoukigenFrom == null) {
			// jTextField_YukoukigenFrom = new ExtendedDateChooser("", 8, ImeMode.IME_OFF);
			jTextField_YukoukigenFrom = new ExtendedDateChooser(ImeMode.IME_OFF);
			// eidt s.inoue 2011/12/13
			// jTextField_YukoukigenFrom.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_YukoukigenFrom.setAlignmentX(ExtendedDateChooser.RIGHT_ALIGNMENT);
			jTextField_YukoukigenFrom.setPreferredSize(new Dimension(100, 20));
			jTextField_YukoukigenFrom.addFocusListener(this);
		}
		return jTextField_YukoukigenFrom;
	}

	/**
	 * This method initializes jTextField_NingenDocTanka
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedDateChooser getJTextField_YukoukigenTo() {
		if (jTextField_YukoukigenTo == null) {
			// jTextField_YukoukigenTo = new ExtendedDateChooser("", 8, ImeMode.IME_OFF);
			jTextField_YukoukigenTo = new ExtendedDateChooser(ImeMode.IME_OFF);
			// eidt s.inoue 2011/12/13
			// jTextField_YukoukigenTo.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_YukoukigenTo.setAlignmentX(ExtendedDateChooser.RIGHT_ALIGNMENT);
			jTextField_YukoukigenTo.setPreferredSize(new Dimension(100, 20));
			jTextField_YukoukigenTo.addFocusListener(this);
		}
		return jTextField_YukoukigenTo;
	}
	/**
	 * This method initializes jTextField_HokenjyaNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedOpenTextControl getJTextField_HokenjyaNumber() {
		if (jTextField_HokenjyaNumber == null) {
			jTextField_HokenjyaNumber = new ExtendedOpenTextControl("", 8, ImeMode.IME_OFF, false);
			jTextField_HokenjyaNumber.setPreferredSize(new Dimension(100, 20));
			jTextField_HokenjyaNumber.addActionListener(this);
			jTextField_HokenjyaNumber.addFocusListener(this);
		}
		return jTextField_HokenjyaNumber;
	}

	/**
	 * This method initializes jTextField_HokenjyaName
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_HokenjyaName() {
		if (jTextField_HokenjyaName == null) {
			jTextField_HokenjyaName = new ExtendedOpenTextControl("",200,ImeMode.IME_ZENKAKU);
			jTextField_HokenjyaName.setPreferredSize(new Dimension(360, 20));
		}
		return jTextField_HokenjyaName;
	}

	/**
	 * This method initializes jTextField_Zipcode
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenFormattedControl getJTextField_Zipcode() {
		if (jTextField_Zipcode == null) {
			jTextField_Zipcode = new ExtendedOpenFormattedControl(ImeMode.IME_OFF);
			jTextField_Zipcode.setPostCodeFormat();
			jTextField_Zipcode.setPreferredSize(new Dimension(100, 20));
			jTextField_Zipcode.addFocusListener(this);
		}
		return jTextField_Zipcode;
	}

	/**
	 * This method initializes jTextField_Address
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_Address() {
		if (jTextField_Address == null) {
			jTextField_Address = new ExtendedOpenTextControl("",200,ImeMode.IME_ZENKAKU);
			jTextField_Address.setPreferredSize(new Dimension(350, 20));
		}
		return jTextField_Address;
	}

	/**
	 * This method initializes jTextField_Chiban
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_Chiban() {
		if (jTextField_Chiban == null) {
			jTextField_Chiban = new ExtendedOpenTextControl("",200,ImeMode.IME_ZENKAKU);
			jTextField_Chiban.setPreferredSize(new Dimension(350, 20));
		}
		return jTextField_Chiban;
	}

	/**
	 * This method initializes jTextField_TEL
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_TEL() {
		if (jTextField_TEL == null) {
			jTextField_TEL = new ExtendedOpenTextControl("", 11, ImeMode.IME_OFF);
			jTextField_TEL.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_TEL;
	}

	/**
	 * This method initializes jTextField_Code
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_Code() {
		if (jTextField_Kigo == null) {
//			jTextField_Kigo = new ExtendedOpenTextControl("", 20, ImeMode.IME_ZENKAKU);	// edit n.ohkubo 2015/03/01　削除
			jTextField_Kigo = new ExtendedOpenTextControl("", 40, ImeMode.IME_ZENKAKU);	// edit n.ohkubo 2015/03/01　追加
			jTextField_Kigo.setPreferredSize(new Dimension(350, 20));
		}
		return jTextField_Kigo;
	}

	/**
	 * This method initializes jTextField_HonninGairai
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_HonninGairai() {
		if (jTextField_HonninGairai == null) {
			jTextField_HonninGairai = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			// jTextField_HonninGairai.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_HonninGairai.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_HonninGairai.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_HonninGairai;
	}

	/**
	 * This method initializes jTextField_KazokuGairai
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_KazokuGairai() {
		if (jTextField_KazokuGairai == null) {
			jTextField_KazokuGairai = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			jTextField_KazokuGairai.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_KazokuGairai.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_KazokuGairai;
	}

	/**
	 * This method initializes jTextField_HonninNyuin
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_HonninNyuin() {
		if (jTextField_HonninNyuin == null) {
			jTextField_HonninNyuin = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			jTextField_HonninNyuin.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_HonninNyuin.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_HonninNyuin;
	}

/**
	 * This method initializes jTextField_KazokuNyuin
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_KazokuNyuin() {
		if (jTextField_KazokuNyuin == null) {
			jTextField_KazokuNyuin = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			jTextField_KazokuNyuin.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_KazokuNyuin.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_KazokuNyuin;
	}

	/**
	 * This method initializes jTextField_KihonTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_KihonTanka() {
		if (jTextField_KihonTanka == null) {
			jTextField_KihonTanka = new ExtendedOpenTextControl("", 9, ImeMode.IME_OFF);
			jTextField_KihonTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_KihonTanka.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_KihonTanka;
	}

	/**
	 * This method initializes jTextField_HinketsuTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_HinketsuTanka() {
		if (jTextField_HinketsuTanka == null) {
			jTextField_HinketsuTanka = new ExtendedOpenTextControl("", 9, ImeMode.IME_OFF);
			jTextField_HinketsuTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_HinketsuTanka.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_HinketsuTanka;
	}

	/**
	 * This method initializes jTextField_SindenzuTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_SindenzuTanka() {
		if (jTextField_SindenzuTanka == null) {
			jTextField_SindenzuTanka = new ExtendedOpenTextControl("", 9, ImeMode.IME_OFF);
			jTextField_SindenzuTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_SindenzuTanka.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_SindenzuTanka;
	}

	/**
	 * This method initializes jTextField_GanteiTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_GanteiTanka() {
		if (jTextField_GanteiTanka == null) {
			jTextField_GanteiTanka = new ExtendedOpenTextControl("", 9, ImeMode.IME_OFF);
			jTextField_GanteiTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_GanteiTanka.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_GanteiTanka;
	}


	// add ver2 s.inoue 2009/06/16
	/**
	 * This method initializes jTextField_NingenDocTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_NingenDocTanka() {
		if (jTextField_NingenDocTanka == null) {
			jTextField_NingenDocTanka = new ExtendedOpenTextControl("", 9, ImeMode.IME_OFF);
			jTextField_NingenDocTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_NingenDocTanka.setPreferredSize(new Dimension(100, 20));
			// add ver2 s.inoue 2009/07/10
			jTextField_NingenDocTanka.addFocusListener(this);
		}
		return jTextField_NingenDocTanka;
	}

	// add s.inoue 2009/10/01
	/**
	 * This method initializes jTextField_KihonTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedTextField getJTextField_TankaHantei() {
		if (jTextField_TankaHantei == null) {
			jTextField_TankaHantei = new ExtendedTextField("", 1, ImeMode.IME_OFF);
			jTextField_TankaHantei.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			jTextField_TankaHantei.setPreferredSize(new Dimension(20, 20));
			jTextField_TankaHantei.addActionListener(this);
			jTextField_TankaHantei.addKeyListener(this);
			jTextField_TankaHantei.setColumns(1);
		}
		return jTextField_TankaHantei;
	}

	/**
	 * This method initializes jButton_ORCA
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ORCA() {
		if (jButton_ORCA == null) {
//			jButton_ORCA = new ExtendedButton();
//			jButton_ORCA.setText("日レセ読込(N)");
//			jButton_ORCA.addActionListener(this);
//			// edit s.inoue 2010/04/21
//			jButton_ORCA.setMnemonic(KeyEvent.VK_N);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Orca);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

// eidt s.inoue 2012/04/23
//			jButton_ORCA= new ExtendedButton(
//					"Orca","日レセ読込(N)","日レセ読込(ALT+N)",KeyEvent.VK_N,icon);
			jButton_ORCA= new ExtendedButton(
					"Orca","保険者情報読込(N)","保険者情報読込(ALT+N)",KeyEvent.VK_N,icon);
			jButton_ORCA.addActionListener(this);
			jButton_ORCA.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		}
		return jButton_ORCA;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Register= new ExtendedButton(
					"Register","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	/**
	 * This method initializes jButton_RegisterAdd
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_RegisterAdd() {
		if (jButton_RegisterAdd == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_RegisterAdd= new ExtendedButton(
					"RegisterAdd","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
			jButton_RegisterAdd.addActionListener(this);
		}
		return jButton_RegisterAdd;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Add() {
		if (jButton_Clear == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Clear= new ExtendedButton(
					"jButton_Add","クリア(E)","クリア(ALT+E)",KeyEvent.VK_E,icon);
			jButton_Clear.addActionListener(this);

			// eidt s.inoue 2012/07/09
			jButton_Clear.setFocusable(false);
		}
		return jButton_Clear;
	}

	/**
	 * This method initializes jButton_RegisterYukoukigenEdit
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_RegisterYukouKigenEdit() {
		if (jButton_RegisterYukoukigenEdit == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_RegisterYukoukigenEdit= new ExtendedButton(
					"RegisterYukouKigenEdit","登録(N)","登録(ALT+N)",KeyEvent.VK_N,icon);
			jButton_RegisterYukoukigenEdit.addActionListener(this);
		}
		return jButton_RegisterYukoukigenEdit;
	}

	/**
	 * This method initializes jButton_RegisterYukoukigenDelete
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_RegisterYukouKigenDelete() {
		if (jButton_RegisterYukoukigenDelete == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Delete);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_RegisterYukoukigenDelete= new ExtendedButton(
					"jButton_Del","削除(D)","削除(ALT+D)",KeyEvent.VK_D,icon);
			jButton_RegisterYukoukigenDelete.addActionListener(this);
			// eidt s.inoue 2012/07/09
			jButton_RegisterYukoukigenDelete.setFocusable(false);
		}
		return jButton_RegisterYukoukigenDelete;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	}

	/*
	 * FrameSize Control
	 */
	@Override
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

// eidt s.inoue 2012/11/15
//	/**
//	 * This method initializes jComboBox_ItakuKubun
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_ItakuKubun() {
//		if (jComboBox_ItakuKubun == null) {
//			// del s.inoue 2009/10/06
//			// jComboBox_ItakuKubun = new ExtendedComboBox(ImeMode.IME_OFF);
//			jComboBox_ItakuKubun = new ExtendedComboBox();
//			jComboBox_ItakuKubun.setPreferredSize(new Dimension(100, 20));
//			jComboBox_ItakuKubun.setMinimumSize(new Dimension(31, 20));
//			jComboBox_ItakuKubun.addItemListener(this);
//		}
//		return jComboBox_ItakuKubun;
//	}
	/**
	 * This method initializes jComboBox_Hokensido
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedOpenComboboxControl getJComboBox_ItakuKubun() {
		if (jComboBox_ItakuKubun == null) {
			jComboBox_ItakuKubun = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);

			jComboBox_ItakuKubun.setCanCopy(true);
			jComboBox_ItakuKubun.setAttributeName("Itaku_Kbn");
			jComboBox_ItakuKubun.setDomainId("ITAKU_KBN");
			// add s.inoue 2012/11/26
			jComboBox_ItakuKubun.setSelectedIndex(0);
		}
		return jComboBox_ItakuKubun;
	}

	/**
	 * This method initializes jTextField_HinketuCode
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_HinketuCode() {
		if (jTextField_HinketuCode == null) {
			jTextField_HinketuCode = new ExtendedOpenTextControl();
			jTextField_HinketuCode.setVisible(false);
		}
		return jTextField_HinketuCode;
	}

	/**
	 * This method initializes jTextField_SindenzuCode
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_SindenzuCode() {
		if (jTextField_SindenzuCode == null) {
			jTextField_SindenzuCode = new ExtendedOpenTextControl();
			jTextField_SindenzuCode.setVisible(false);
		}
		return jTextField_SindenzuCode;
	}

	/**
	 * This method initializes jTextField_GanteiCode
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_GanteiCode() {
		if (jTextField_GanteiCode == null) {
			jTextField_GanteiCode = new ExtendedOpenTextControl();
			jTextField_GanteiCode.setVisible(false);
		}
		return jTextField_GanteiCode;
	}

	// add ver2 s.inoue 2009/06/16
	/**
	 * This method initializes jTextField_NingenDocCode
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_NingenDocCode() {
		if (jTextField_NingenDocCode == null) {
			jTextField_NingenDocCode = new ExtendedOpenTextControl();
			jTextField_NingenDocCode.setVisible(false);
		}
		return jTextField_NingenDocCode;
	}

	// edit n.ohkubo 2015/03/01　未使用なので削除　start
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
	// edit n.ohkubo 2015/03/01　未使用なので削除　end

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("keyPressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("keyReleased");
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("keyTyped");
	}

	// edit s.inoue 2010/01/21
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

//	/**
//	 * This method initializes jPanel1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel1() {
//		if (jPanel1 == null) {
//			jLabel12 = new ExtendedLabel();
//			jLabel12.setText("円");
//			jPanel1 = new JPanel();
//			jPanel1.setLayout(new GridBagLayout());
//		}
//		return jPanel1;
//	}

//	/**
//	 * This method initializes jPanel2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel2() {
//		if (jPanel2 == null) {
//			jLabel125 = new ExtendedLabel();
//			jLabel125.setText("円");
//			jPanel2 = new JPanel();
//			jPanel2.setLayout(new GridBagLayout());
//		}
//		return jPanel2;
//	}

//	/**
//	 * This method initializes jPanel3
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel3() {
//		if (jPanel3 == null) {
//			jLabel126 = new ExtendedLabel();
//			jLabel126.setText("円");
//			jPanel3 = new JPanel();
//			jPanel3.setLayout(new GridBagLayout());
//		}
//		return jPanel3;
//	}

//	/**
//	 * This method initializes jPanel4
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel4() {
//		if (jPanel4 == null) {
//			jLabel127 = new ExtendedLabel();
//			jLabel127.setText("円");
//			jPanel4 = new JPanel();
//			jPanel4.setLayout(new GridBagLayout());
//		}
//		return jPanel4;
//	}

//	/**
//	 * This method initializes jPanel10
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel10() {
//		if (jPanel10 == null) {
//			jLabel128 = new ExtendedLabel();
//			jLabel128.setText("円");
//			jPanel10= new JPanel();
//			jPanel10.setLayout(new GridBagLayout());
//		}
//		return jPanel10;
//	}

//	/**
//	 * This method initializes jPanel5
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel5() {
//		if (jPanel5 == null) {
//			jPanel5 = new JPanel();
//			jPanel5.setLayout(new GridBagLayout());
//		}
//		return jPanel5;
//	}

	/**
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 3;
			gridBagConstraints2.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.gridx = 2;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.gridx = 2;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridx = 1;
			gridBagConstraints22.gridy = 3;
			gridBagConstraints22.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.anchor = GridBagConstraints.WEST;
			gridBagConstraints42.gridx = 1;
			gridBagConstraints42.gridy = 2;
			gridBagConstraints42.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 1;
			gridBagConstraints20.gridx = 1;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.anchor = GridBagConstraints.EAST;
			gridBagConstraints41.gridy = 3;
			gridBagConstraints41.gridx = 0;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.anchor = GridBagConstraints.EAST;
			gridBagConstraints28.gridy = 2;
			gridBagConstraints28.gridx = 0;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.anchor = GridBagConstraints.EAST;
			gridBagConstraints27.gridy = 1;
			gridBagConstraints27.gridx = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.gridx = 2;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridy = 0;
			gridBagConstraints19.gridx = 1;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.anchor = GridBagConstraints.EAST;
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.gridy = 0;
			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel6.setVisible(false);
			jPanel6.add(jLabel115, gridBagConstraints26);
			jPanel6.add(jLabel1231, gridBagConstraints11);
			jPanel6.add(jLabel116, gridBagConstraints27);
			jPanel6.add(jLabel120, gridBagConstraints28);
			jPanel6.add(jLabel121, gridBagConstraints41);
			jPanel6.add(getJTextField_HonninGairai(), gridBagConstraints19);
			jPanel6.add(getJTextField_KazokuGairai(), gridBagConstraints20);
			jPanel6.add(getJTextField_HonninNyuin(), gridBagConstraints42);
			jPanel6.add(getJTextField_KazokuNyuin(), gridBagConstraints22);
			jPanel6.add(jLabel1232, gridBagConstraints21);
			jPanel6.add(jLabel123, gridBagConstraints1);
			jPanel6.add(jLabel124, gridBagConstraints2);
		}
		return jPanel6;
	}

	// add s.inoue 2010/01/25
	/**
	 * This method initializes jPanel12
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 1.0D;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.weightx = 1.0D;

			// add s.inoue 2010/01/26
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.weightx = 1.0D;

			jPanel12 = new JPanel();
			jPanel12.setLayout(new GridBagLayout());
			jPanel12.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel12.add(getJPanel13(),gridBagConstraints3);
			jPanel12.add(getJPanel7(),gridBagConstraints2);
			jPanel12.add(getJPanel_TableArea(),gridBagConstraints1);
		}
		return jPanel12;
	}

	// add s.inoue 2010/01/26
	/**
	 * This method initializes jPanel13
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel13() {
		if (jPanel13 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.insets = new Insets(0, 0, 5, 5);

			jLabel_TankaTitle = new ExtendedLabel(Font.BOLD);
			jLabel_TankaTitle.setText("単価履歴情報");

			jPanel13 = new JPanel();
			jPanel13.setLayout(new GridBagLayout());
			jPanel13.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel13.add(jLabel_TankaTitle,gridBagConstraints1);
		}
		return jPanel13;
	}

	// edit n.ohkubo 2015/03/01　未使用なので削除　start
//	/**
//	 * This method initializes jPanel11
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel11() {
//		// model用領域
//		if (jPanel11 == null) {
//			// edit s.inoue 2010/01/21
////			jPanel11 = new JPanel();
////			jPanel11.setLayout(new BorderLayout());
////			jPanel11.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
////			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
////			gridBagConstraints1.gridx = 0;
////			gridBagConstraints1.gridy = 0;
////			gridBagConstraints1.anchor = GridBagConstraints.BOTH;
////			gridBagConstraints1.weightx = 1.0D;
//
//			jPanel11 = new JPanel();
//			jPanel11.setLayout(new GridBagLayout());
//			// jPanel11.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//			// jPanel11.add(getJPanel11(),gridBagConstraints1);
//		}
//		return jPanel11;
//	}
	// edit n.ohkubo 2015/03/01　未使用なので削除　end

//	/**
//	 * This method initializes jPanel11
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel11() {
//		if (jPanel11 == null) {
//			jLabelYukoukigen = new ExtendedLabel();
//			jLabelYukoukigen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelYukoukigen.setText("有効期限");
//			jLabelYukoukigen.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
//			gridBagConstraints71.gridx = 0;
//			gridBagConstraints71.gridy = 0;
//			gridBagConstraints71.gridwidth = 2;
//			gridBagConstraints71.anchor = GridBagConstraints.WEST;
//
//			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
//			gridBagConstraints70.gridx = 2;
//			gridBagConstraints70.gridy = 0;
//			gridBagConstraints70.anchor = GridBagConstraints.WEST;
//
//			jLabelTilda = new ExtendedLabel();
//			jLabelTilda.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelTilda.setText("～");
//			jLabelTilda.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//			GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
//			gridBagConstraints72.gridx = 1;
//			gridBagConstraints72.gridy = 0;
//			gridBagConstraints72.anchor = GridBagConstraints.WEST;
////			gridBagConstraints72.insets = new Insets(0, 5, 0, 0);
//
//			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
//			gridBagConstraints73.gridx = 3;
//			gridBagConstraints73.gridy = 0;
//			gridBagConstraints73.anchor = GridBagConstraints.WEST;
//			gridBagConstraints73.weightx = 1.0D;
//
//			jPanel11 = new JPanel();
//			jPanel11.setLayout(new GridBagLayout());
//			jPanel11.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel11.add(jLabelTilda, gridBagConstraints70);
//			jPanel11.add(jLabelYukoukigen, gridBagConstraints71);
//			jPanel11.add(getJTextField_YukoukigenFrom(), gridBagConstraints72);
//			jPanel11.add(getJTextField_YukoukigenTo(), gridBagConstraints73);
//		}
//		return jPanel11;
//	}
	/**
	 * This method initializes jPanel7
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 5;
			gridBagConstraints71.anchor = GridBagConstraints.WEST;
			gridBagConstraints71.gridy = 6;
			jLabelNingenDocNum = new ExtendedLabel();
			jLabelNingenDocNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelNingenDocNum.setText("（半角数字9桁以内）");
			jLabelNingenDocNum.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.gridx = 5;
			gridBagConstraints70.anchor = GridBagConstraints.WEST;
			gridBagConstraints70.gridy = 5;
			jLabelGanteiNum = new ExtendedLabel();
			jLabelGanteiNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelGanteiNum.setText("（半角数字9桁以内）");
			jLabelGanteiNum.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.gridx = 5;
			gridBagConstraints69.anchor = GridBagConstraints.WEST;
			gridBagConstraints69.gridy = 4;
			jLabelSindenzuNum = new ExtendedLabel();
			jLabelSindenzuNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelSindenzuNum.setText("（半角数字9桁以内）");
			jLabelSindenzuNum.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.gridx = 5;
			gridBagConstraints68.anchor = GridBagConstraints.WEST;
			gridBagConstraints68.gridy = 3;
			jLabelHinketuNum = new ExtendedLabel();
			jLabelHinketuNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelHinketuNum.setText("（半角数字9桁以内）");
			jLabelHinketuNum.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.gridx = 5;
			gridBagConstraints67.weightx = 1.0D;
			gridBagConstraints67.anchor = GridBagConstraints.WEST;
			gridBagConstraints67.gridy = 2;
			jLabelKihonNum = new ExtendedLabel();
			jLabelKihonNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelKihonNum.setText("（半角数字9桁以内）");
			jLabelKihonNum.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridy = 6;
			gridBagConstraints18.gridx = 4;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridy = 5;
			gridBagConstraints16.gridx = 4;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridx = 3;
			gridBagConstraints9.gridy = 5;
			gridBagConstraints9.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.gridy = 6;
			gridBagConstraints10.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.anchor = GridBagConstraints.WEST;
			gridBagConstraints45.gridy = 5;
			gridBagConstraints45.gridx = 2;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridy = 6;
			gridBagConstraints46.gridx = 2;

			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridy = 4;
			gridBagConstraints15.gridx = 4;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 4;
			gridBagConstraints8.gridx = 3;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.anchor = GridBagConstraints.WEST;
			gridBagConstraints44.gridy = 4;
			gridBagConstraints44.gridx = 2;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 3;
			gridBagConstraints13.gridx = 4;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.gridx = 3;
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.anchor = GridBagConstraints.WEST;
			gridBagConstraints43.gridy = 3;
			gridBagConstraints43.gridx = 2;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.gridx = 4;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.gridx = 3;
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.gridy = 2;
			gridBagConstraints36.gridx = 2;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 1;
			// del s.inoue 2010/01/11
			gridBagConstraints17.gridwidth = 2;
			gridBagConstraints17.gridx = 5;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.gridwidth = 2;
			gridBagConstraints14.gridx = 3;
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.anchor = GridBagConstraints.WEST;
			gridBagConstraints37.gridy = 1;
			gridBagConstraints37.gridx = 2;

			// add s.inoue 2009/10/01
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridy = 1;
			gridBagConstraints62.gridx = 1;

			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.gridy = 6;
			gridBagConstraints63.gridx = 1;

			// edit s.inoue 2009/10/01
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.anchor = GridBagConstraints.WEST;
			gridBagConstraints64.gridx = 0;
			gridBagConstraints64.gridy = 1;

			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.anchor = GridBagConstraints.WEST;
			gridBagConstraints65.gridx = 0;
			gridBagConstraints65.gridy = 0;

			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.anchor = GridBagConstraints.WEST;
			gridBagConstraints66.gridx = 1;
			gridBagConstraints66.gridy = 0;
			gridBagConstraints66.insets = new Insets(0,10,0,0);

			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.anchor = GridBagConstraints.WEST;
			gridBagConstraints80.gridx = 2;
			gridBagConstraints80.gridy = 0;
			gridBagConstraints80.gridwidth =2;

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.anchor = GridBagConstraints.WEST;
			gridBagConstraints81.gridx = 2;
			gridBagConstraints81.gridy = 0;
			gridBagConstraints81.gridwidth =2;
			gridBagConstraints81.insets = new Insets(0,20,0,0);

			// add s.inoue 2010/01/11 btn追加
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
			gridBagConstraints82.anchor = GridBagConstraints.WEST;
			gridBagConstraints82.gridy = 6;
			gridBagConstraints82.gridx = 6;
			gridBagConstraints82.insets = new Insets(0,5,0,0);

			GridBagConstraints gridBagConstraints83 = new GridBagConstraints();
			gridBagConstraints83.anchor = GridBagConstraints.WEST;
			gridBagConstraints83.gridy = 6;
			gridBagConstraints83.gridx = 7;
			gridBagConstraints83.insets = new Insets(0,5,0,0);

			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
			gridBagConstraints84.anchor = GridBagConstraints.WEST;
			gridBagConstraints84.gridy = 6;
			// edit s.inoue 2010/02/02
			gridBagConstraints84.gridx = 8;
			gridBagConstraints84.insets = new Insets(0,5,0,0);

			GridBagConstraints gridBagConstraints85 = new GridBagConstraints();
			gridBagConstraints85.anchor = GridBagConstraints.WEST;
			gridBagConstraints85.gridy = 6;
			// edit s.inoue 2010/02/02
			gridBagConstraints85.gridx = 7;
			gridBagConstraints85.insets = new Insets(0,5,0,0);

			// edit s.inoue 2010/01/25
			jLabel122 = new ExtendedLabel();
			jLabel122.setText("（1：個別健診 2：集団健診）");
			jLabel122.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel122.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			// edit s.inoue 2010/01/07
			jLabel12 = new ExtendedLabel();
			jLabel12.setText("円");
			jLabel127 = new ExtendedLabel();
			jLabel127.setText("円");
			jLabel125 = new ExtendedLabel();
			jLabel125.setText("円");
			jLabel126 = new ExtendedLabel();
			jLabel126.setText("円");
			jLabel128 = new ExtendedLabel();
			jLabel128.setText("円");

			jLabel119 = new ExtendedLabel();
			jLabel119.setText("単価（眼底検査）");
			jLabel119.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/14
			// jLabel119.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel118 = new ExtendedLabel();
			jLabel118.setText("単価（心電図検査）");
			jLabel118.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/14
			// jLabel118.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel117 = new ExtendedLabel();
			jLabel117.setText("単価（貧血検査）");
			jLabel117.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/14
			// jLabel117.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel114 = new ExtendedLabel();
			jLabel114.setText("眼底検査コード");
			jLabel114.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel114.setVisible(false);
			jLabel113 = new ExtendedLabel();
			jLabel113.setText("心電図検査コード");
			jLabel113.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel113.setVisible(false);
			jLabel112 = new ExtendedLabel();
			jLabel112.setText("貧血検査コード");
			jLabel112.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel112.setVisible(false);
			jLabel111 = new ExtendedLabel();
			jLabel111.setText("単価（基本的な健診）");
			jLabel111.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/14
			// jLabel111.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel110 = new ExtendedLabel();
			jLabel110.setText("委託料単価区分");
			jLabel110.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/14
			jLabel110.setForeground(ViewSettings.getRequiedItemFrColor());

// move s.inoue 2010/01/25
//			jLabel19 = new ExtendedLabel();
//			jLabel19.setText("記号");
//			jLabel19.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel19.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabel19.setVisible(true);
//			jLabel18 = new ExtendedLabel();
//			jLabel18.setText("電話番号");
//			jLabel18.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel17 = new ExtendedLabel();
//			jLabel17.setText("地番方書");
//			jLabel17.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel16 = new ExtendedLabel();
//			jLabel16.setText("所在地");
//			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel15 = new ExtendedLabel();
//			jLabel15.setText("郵便番号");
//			jLabel15.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel14 = new ExtendedLabel();
//			jLabel14.setText("保険者名称");
//			jLabel14.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel13 = new ExtendedLabel();
//			jLabel13.setText("保険者番号");
//			jLabel13.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel130 = new ExtendedLabel();
			jLabel130.setText("単価（人間ドック）");
			jLabel130.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/14
			// jLabel130.setForeground(ViewSettings.getRequiedItemFrColor());

			// edit s.inoue 2010/01/07
			jLabelYukoukigen = new ExtendedLabel();
			jLabelYukoukigen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelYukoukigen.setText("有効期限");
			jLabelYukoukigen.setFont(new Font("Dialog", Font.PLAIN, 12));
			// add s.inoue 2012/05/10
			jLabelYukoukigen.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabelTilda = new ExtendedLabel();
			jLabelTilda.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelTilda.setText("～");
			jLabelTilda.setFont(new Font("Dialog", Font.PLAIN, 12));

			jPanel7 = new JPanel();
			jPanel7.setLayout(new GridBagLayout());
			// edit s.inoue 2010/01/26
			// jPanel7.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel7.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

			jPanel7.add(jLabel110, gridBagConstraints37);
			jPanel7.add(jLabel122, gridBagConstraints17);
			jPanel7.add(jLabel111, gridBagConstraints36);
			jPanel7.add(jLabel12, gridBagConstraints6);
			jPanel7.add(jLabel117, gridBagConstraints43);
			jPanel7.add(jLabel125, gridBagConstraints13);
			jPanel7.add(jLabel118, gridBagConstraints44);
			jPanel7.add(jLabel126, gridBagConstraints15);
			jPanel7.add(jLabel119, gridBagConstraints45);
			jPanel7.add(jLabel130, gridBagConstraints46);
			jPanel7.add(jLabel127, gridBagConstraints16);
			jPanel7.add(jLabel128, gridBagConstraints18);
			jPanel7.add(jLabelKihonNum, gridBagConstraints67);
			jPanel7.add(jLabelHinketuNum, gridBagConstraints68);
			jPanel7.add(jLabelSindenzuNum, gridBagConstraints69);
			jPanel7.add(jLabelGanteiNum, gridBagConstraints70);
			jPanel7.add(jLabelNingenDocNum, gridBagConstraints71);
			jPanel7.add(jLabelYukoukigen,gridBagConstraints65);
			jPanel7.add(jLabelTilda,gridBagConstraints80);
			jPanel7.add(getJComboBox_ItakuKubun(), gridBagConstraints14);
			jPanel7.add(getJTextField_KihonTanka(), gridBagConstraints5);
			jPanel7.add(getJTextField_HinketsuTanka(), gridBagConstraints7);
			jPanel7.add(getJTextField_SindenzuTanka(), gridBagConstraints8);
			jPanel7.add(getJTextField_GanteiTanka(), gridBagConstraints9);
			jPanel7.add(getJTextField_NingenDocTanka(), gridBagConstraints10);
			jPanel7.add(getJPanelKihon(), gridBagConstraints62);
			jPanel7.add(getJPanelDoc(), gridBagConstraints63);
			jPanel7.add(getJTextField_TankaHantei(), gridBagConstraints64);
			jPanel7.add(getJTextField_YukoukigenFrom(),gridBagConstraints66);
			jPanel7.add(getJTextField_YukoukigenTo(),gridBagConstraints81);
			jPanel7.add(getJButton_RegisterYukouKigenEdit(),gridBagConstraints84);
			jPanel7.add(getJButton_Add(),gridBagConstraints82);
			jPanel7.add(getJButton_RegisterYukouKigenDelete(),gridBagConstraints85);
		}
		return jPanel7;
	}

	// add s.inoue 2009/10/01
	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelKihon() {
		if (jPanelKihon == null) {
			jPanelKihon = new JPanel();
			// edit s.inoue 2010/01/07
			// jPanelKihon.setLayout(new BoxLayout(getJPanelKihon(), BoxLayout.X_AXIS));
			jPanelKihon.setLayout(new GridBagLayout());
			jPanelKihon.add(getJRadioButton_Kihon(), null);
		}
		return jPanelKihon;
	}

	// add s.inoue 2009/10/01
	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDoc() {
		if (jPanelDoc == null) {
			jPanelDoc = new JPanel();
			// edit s.inoue 2010/01/07
			// jPanelDoc.setLayout(new BoxLayout(getJPanelDoc(), BoxLayout.X_AXIS));
			jPanelDoc.setLayout(new GridBagLayout());
			jPanelDoc.add(getJRadioButton_Doc(), null);
		}
		return jPanelDoc;
	}

	// add s.inoue 2009/10/01
	/**
	 * This method initializes jRadioButton_Male
	 *
	 * @return javax.swing.JRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Kihon() {
		if (jRadioButton_Kihon == null) {
			jRadioButton_Kihon = new ExtendedRadioButton();
			jRadioButton_Kihon.setText("1：基本健診");
			jRadioButton_Kihon.setPreferredSize(new Dimension(120, 20));
			jRadioButton_Kihon.addKeyListener(this);
			jRadioButton_Kihon.addItemListener(this);
			groupHantei.add(jRadioButton_Kihon);
		}
		return jRadioButton_Kihon;
	}

	// add s.inoue 2009/10/01
	/**
	 * This method initializes jRadioButton_Female
	 *
	 * @return javax.swing.EventHandleRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Doc() {
		if (jRadioButton_Doc == null) {
			jRadioButton_Doc = new ExtendedRadioButton();
			jRadioButton_Doc.setText("2：人間ドック");
			jRadioButton_Doc.setPreferredSize(new Dimension(120, 20));
			jRadioButton_Doc.addItemListener(this);
			jRadioButton_Doc.addKeyListener(this);
			groupHantei.add(jRadioButton_Doc);
		}
		return jRadioButton_Doc;
	}

	/**
	 * This method initializes jPanel8
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.gridx = 5;
			gridBagConstraints66.gridy = 6;
			gridBagConstraints66.anchor = GridBagConstraints.WEST;
			// eidt s.inoue 2011/12/14
			gridBagConstraints66.anchor = GridBagConstraints.NORTHWEST;

			jLabelKigouNum = new ExtendedLabel();
			jLabelKigouNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelKigouNum.setText("（全角40文字以内、半角可）");
			jLabelKigouNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.anchor = GridBagConstraints.WEST;
			// edit s.inoue 2010/01/26
			gridBagConstraints65.gridx = 5;
			gridBagConstraints65.gridwidth = 2;
			gridBagConstraints65.gridy = 3;
			jLabelTelNum = new ExtendedLabel();
			jLabelTelNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelTelNum.setText("（半角数字11桁以内）");
			jLabelTelNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.gridx = 5;
			gridBagConstraints64.anchor = GridBagConstraints.WEST;
			gridBagConstraints64.gridy = 5;
			jLabelTibanNum = new ExtendedLabel();
			jLabelTibanNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelTibanNum.setText("（全角100文字以内、半角可）");	// edit n.ohkubo 2015/03/01　削除
			jLabelTibanNum.setText("（全角100文字以内）");			// edit n.ohkubo 2015/03/01　追加
			jLabelTibanNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.gridx = 5;
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.gridy = 4;
			jLabelShozaitiNum = new ExtendedLabel();
			jLabelShozaitiNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelShozaitiNum.setText("（全角100文字以内、半角可）");
			jLabelShozaitiNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridx = 2;
			gridBagConstraints62.anchor = GridBagConstraints.WEST;
			gridBagConstraints62.gridwidth = 1;
			gridBagConstraints62.gridy = 3;
			jLabelYubinNum = new ExtendedLabel();
			jLabelYubinNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelYubinNum.setText("（半角数字7桁）");
			jLabelYubinNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 5;
			gridBagConstraints61.gridy = 2;
			// edit s.inoue 2010/01/26
			gridBagConstraints61.anchor = GridBagConstraints.WEST;

			jLabelHokenjyaNameNum = new ExtendedLabel();
			jLabelHokenjyaNameNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelHokenjyaNameNum.setText("（全角100文字以内、半角可）");
			jLabelHokenjyaNameNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.gridx = 5;
			gridBagConstraints58.anchor = GridBagConstraints.WEST;
			gridBagConstraints58.gridy = 1;
			gridBagConstraints58.insets = new Insets(0, 0, 0, 0);

			jLabelHokenjyaNum = new ExtendedLabel();
			jLabelHokenjyaNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabelHokenjyaNum.setText("（半角数字8桁）");
			jLabelHokenjyaNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.insets = new Insets(0, 5, 5, 0);
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 2;
			gridBagConstraints53.gridy = 1;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints52.gridy = 6;
			gridBagConstraints52.gridwidth = 4;
			gridBagConstraints52.gridx = 1;
			// eidt s.inoue 2011/12/14
			gridBagConstraints52.anchor = GridBagConstraints.NORTH;

			// edit s.inoue
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.anchor = GridBagConstraints.WEST;
//			gridBagConstraints54.fill = GridBagConstraints.EAST;
			gridBagConstraints54.gridy = 6;
			gridBagConstraints54.gridx = 8;
//			gridBagConstraints54.insets = new Insets(0, 5, 0, 0);

			// edit s.inoue 2010/01/21
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.fill = GridBagConstraints.EAST;
			gridBagConstraints55.gridy = 6;
			gridBagConstraints55.gridx = 7;
			gridBagConstraints55.insets = new Insets(0, 0, 0, 5);

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			// edit s.inoue 2010/01/26
			gridBagConstraints12.gridy = 3;
			gridBagConstraints12.gridx = 3;
			gridBagConstraints12.insets = new Insets(0, 70, 0, 0);

			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints50.gridx = 1;
			gridBagConstraints50.gridy = 5;
			gridBagConstraints50.gridwidth = 4;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 4;
			gridBagConstraints10.gridwidth = 4;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.gridy = 3;
			gridBagConstraints48.anchor = GridBagConstraints.WEST;
			gridBagConstraints48.gridx = 1;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.gridx = 1;
			gridBagConstraints47.gridy = 2;
			gridBagConstraints47.gridwidth = 4;
			// edit s.inoue 2010/01/26
			gridBagConstraints47.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridy = 1;
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridx = 1;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.anchor = GridBagConstraints.WEST;
			gridBagConstraints35.gridy = 6;
			gridBagConstraints35.gridx = 0;
			// eidt s.inoue 2011/12/14
			gridBagConstraints35.anchor = GridBagConstraints.NORTHWEST;

			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.anchor = GridBagConstraints.WEST;
			// edit s.inoue 2010/01/26
			gridBagConstraints34.gridy = 3;
			gridBagConstraints34.gridx = 3;
			gridBagConstraints34.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.anchor = GridBagConstraints.WEST;
			gridBagConstraints33.gridy = 5;
			gridBagConstraints33.gridx = 0;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.anchor = GridBagConstraints.WEST;
			gridBagConstraints32.gridy = 4;
			gridBagConstraints32.gridx = 0;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.anchor = GridBagConstraints.WEST;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.gridx = 0;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 2;
			gridBagConstraints30.gridx = 0;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.anchor = GridBagConstraints.WEST;
			gridBagConstraints29.gridy = 1;
			gridBagConstraints29.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints29.gridx = 0;

			// add s.inoue 2010/01/11
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.gridx = 5;
			gridBagConstraints68.gridy = 1;
			gridBagConstraints68.weightx = 1.0D;

			// edit s.inoue 2010/01/25
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.gridy = 0;
			gridBagConstraints69.gridx = 0;
			gridBagConstraints69.anchor = GridBagConstraints.WEST;
			gridBagConstraints69.insets = new Insets(0, 0, 5, 5);
			jLabel_HokenjyaTitle = new ExtendedLabel(Font.BOLD);
			jLabel_HokenjyaTitle.setText("保険者情報");

			jLabel19 = new ExtendedLabel();
			jLabel19.setText("記号");
			jLabel19.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel19.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabel19.setVisible(true);
			jLabel18 = new ExtendedLabel();
			jLabel18.setText("電話番号");
			jLabel18.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel17 = new ExtendedLabel();
			jLabel17.setText("地番方書");
			jLabel17.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel16 = new ExtendedLabel();
			jLabel16.setText("所在地");
			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel15 = new ExtendedLabel();
			jLabel15.setText("郵便番号");
			jLabel15.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel14 = new ExtendedLabel();
			jLabel14.setText("保険者名称");
			jLabel14.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel13 = new ExtendedLabel();
			jLabel13.setText("保険者番号");
			jLabel13.setFont(new Font("Dialog", Font.PLAIN, 12));

			jPanel8 = new JPanel();
			jPanel8.setLayout(new GridBagLayout());
			jPanel8.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel8.add(jLabel13, gridBagConstraints29);
			jPanel8.add(jLabel14, gridBagConstraints30);
			jPanel8.add(jLabel15, gridBagConstraints31);
			jPanel8.add(jLabel16, gridBagConstraints32);
			jPanel8.add(jLabel17, gridBagConstraints33);
			jPanel8.add(jLabel18, gridBagConstraints34);
			jPanel8.add(jLabel19, gridBagConstraints35);
			jPanel8.add(getJTextField_HokenjyaNumber(), gridBagConstraints46);
			jPanel8.add(getJTextField_HokenjyaName(), gridBagConstraints47);
			jPanel8.add(getJTextField_Zipcode(), gridBagConstraints48);
			jPanel8.add(getJTextField_TEL(), gridBagConstraints12);
			jPanel8.add(getJTextField_Address(), gridBagConstraints10);
			jPanel8.add(getJTextField_Chiban(), gridBagConstraints50);
			jPanel8.add(getJTextField_Code(), gridBagConstraints52);
			jPanel8.add(getJButton_Register(), gridBagConstraints54);
			jPanel8.add(getJButton_ClearAdd(), gridBagConstraints55);

			jPanel8.add(getJButton_ORCA(), gridBagConstraints4);
			jPanel8.add(jLabelHokenjyaNum, gridBagConstraints58);
			jPanel8.add(jLabelHokenjyaNameNum, gridBagConstraints61);
			jPanel8.add(jLabelYubinNum, gridBagConstraints62);
			jPanel8.add(jLabelTelNum, gridBagConstraints65);
			jPanel8.add(jLabelKigouNum, gridBagConstraints66);
			jPanel8.add(jLabelShozaitiNum, gridBagConstraints63);
			jPanel8.add(jLabelTibanNum, gridBagConstraints64);
			jPanel8.add(getJRabelHidden(),gridBagConstraints68);
			jPanel8.add(jLabel_HokenjyaTitle,gridBagConstraints69);

		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel9
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new GridBagLayout());
		}
		return jPanel9;
	}

//	/**
//	 * This method initializes jButton_Clear
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Clear() {
//		if (jButton_Clear == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.IcoCommonClear);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			jButton_Clear= new ExtendedButton(
//					"Clear","クリア(C)","クリア(ALT+C)",KeyEvent.VK_C,icon);
//		}
//		return jButton_Clear;
//	}

	// add s.inoue 2010/01/22
	/**
	 * This method initializes jButton_Clear
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_ClearAdd() {
		// add s.inoue 2011/04/07
		if (jButton_ClearAdd == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_ClearAdd= new ExtendedButton(
					"ClearAdd","クリア(A)","クリア(ALT+A)",KeyEvent.VK_A,icon);
			jButton_ClearAdd.addActionListener(this);
			// eidt s.inoue 2012/07/09
			jButton_ClearAdd.setFocusable(false);
		}
		return jButton_ClearAdd;
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}
}
