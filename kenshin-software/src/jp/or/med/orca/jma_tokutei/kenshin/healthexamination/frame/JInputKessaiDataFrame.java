//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.BorderLayout;
//import javax.swing.JPanel;
//import javax.swing.JFrame;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.FlowLayout;
//import javax.swing.SwingConstants;
//import java.awt.CardLayout;
//import java.awt.Font;
//import java.awt.Color;
//import java.awt.Rectangle;
//import javax.swing.BorderFactory;
//import java.awt.event.*;
//
//import javax.swing.BoxLayout;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
//
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import java.awt.Dimension;
//import javax.swing.JTextField;
//
///**
// * åíêfåãâ ÉfÅ[É^ì¸óÕâÊñ êßå‰
// */
//public class JInputKessaiDataFrame extends JFrame implements ActionListener,ItemListener,KeyListener {
//
//	protected static final long serialVersionUID = 1L;
//	protected JPanel jPanel_Content = null;
//	protected JPanel jPanel_ButtonArea = null;
//	protected ExtendedButton jButton_End = null;
//	protected JPanel jPanel_NaviArea = null;
//	protected ExtendedLabel jLabel_Title = null;
//	protected ExtendedLabel jLabel_MainExpl = null;
//	protected JPanel jPanel_TitleArea = null;
//	protected JPanel jPanel_ExplArea2 = null;
//	protected JPanel jPanel_ExplArea1 = null;
//	protected JPanel jPanel_MainArea = null;
//	protected JPanel jPanel_DrawArea = null;
//	protected JPanel jPanel = null;
//	protected JPanel jPanel1 = null;
//	protected JPanel jPanel2 = null;
//	protected JPanel jPanel3 = null;
//	protected JPanel jPanel4 = null;
//	protected JPanel jPanel5 = null;
//	protected JPanel jPanel6 = null;
//	protected ExtendedLabel jLabel = null;
//	protected ExtendedTextField jTextField_Jyusinken_ID = null;
//	protected ExtendedLabel jLabel1 = null;
//	protected ExtendedTextField jTextField_Name = null;
//	protected ExtendedLabel jLabel2 = null;
//	protected ExtendedTextField jTextField_KensaDate = null;
//	protected JPanel jPanel7 = null;
//	protected JPanel jPanel8 = null;
//	protected JPanel jPanel9 = null;
//	protected ExtendedLabel jLabel3 = null;
//	protected ExtendedTextField jTextField_Birthday = null;
//	protected JPanel jPanel10 = null;
//	protected JPanel jPanel11 = null;
//	protected JPanel jPanel12 = null;
//	protected ExtendedLabel jLabel5 = null;
//	protected JPanel jPanel13 = null;
//	protected JPanel jPanel17 = null;
//	protected JPanel jPanel18 = null;
//	protected JPanel jPanel19 = null;
//	protected JPanel jPanel20 = null;
//	protected JPanel jPanel24 = null;
//	protected ExtendedLabel jLabel7 = null;
//	protected ExtendedLabel jLabel8 = null;
//	protected ExtendedTextField jTextField_HinketsuCode = null;
//	protected ExtendedLabel jLabel9 = null;
//	protected ExtendedTextField jTextField_SindenzuCode = null;
//	protected ExtendedLabel jLabel10 = null;
//	protected ExtendedTextField jTextField_GanteiCode = null;
//	protected ExtendedLabel jLabel11 = null;
//	protected ExtendedLabel jLabel12 = null;
//	protected ExtendedLabel jLabel13 = null;
//	protected ExtendedTextField jTextField_KihonTanka = null;
//	protected ExtendedLabel jLabel15 = null;
//	protected ExtendedTextField jTextField_HinketuTanka = null;
//	protected ExtendedLabel jLabel16 = null;
//	protected ExtendedTextField jTextField_SindenzuTanka = null;
//	protected ExtendedLabel jLabel17 = null;
//	protected ExtendedTextField jTextField_GanteiTanka = null;
//	protected ExtendedLabel jLabel18 = null;
//	protected ExtendedTextField jTextField_MadoguchiKihonKin = null;
//	protected ExtendedLabel jLabel19 = null;
//	protected ExtendedTextField jTextField_MadoguchiSyousaiKin = null;
//	protected ExtendedLabel jLabel20 = null;
//	protected ExtendedTextField jTextField_MadoguchiTsuikaKin = null;
//	protected ExtendedTextField jTextField_MadoguchiNingenDocKin = null;
//	protected ExtendedTextField jTextField_AllTanka = null;
//	protected ExtendedTextField jTextField_AllMadoguchi = null;
//	protected ExtendedLabel jLabel23 = null;
//	protected ExtendedTextField jTextField_TotalFee = null;
//	protected ExtendedButton jButton_Register = null;
//	protected ExtendedButton jButton_Cancel = null;
//	protected JPanel jPanel39 = null;
//	protected JPanel jPanel_Example = null;
//	protected ExtendedRadioButton jRadioButton_ItakuTankaKobetu = null;
//	protected ExtendedRadioButton jRadioButton_ItakuTankaSyudan = null;
//	protected ExtendedComboBox jComboBox_MadoguchiKihonSyubetu = null;
//	protected ExtendedComboBox jComboBox_MadoguchiSyousaiSyubetu = null;
//	protected ExtendedComboBox jComboBox_MadoguchiTsuikaSyubetu = null;
//	protected ExtendedComboBox jComboBox_MadoguchiDocSyubetu = null;
//	protected ExtendedTextField jTextField_Sex = null;
//	protected JPanel jPanel_TableArea = null;
//	protected JPanel jPanel_ExampleArea = null;
//	protected ExtendedButton jButton_ReCalc = null;
//	protected JPanel jPanel210 = null;
//	protected ExtendedLabel jLabel_tanka = null;
//	protected ExtendedLabel jLabel_syubetu = null;
//	protected ExtendedLabel jLabel_kingaku = null;
//	protected ExtendedLabel jLabel_kihon = null;
//	protected ExtendedLabel jLabel_syousai = null;
//	protected ExtendedLabel jLabel_tsuika = null;
//	protected ExtendedLabel jLabel_goukei = null;
//	protected ExtendedLabel jLabel_ningenDoc = null;
//	protected JPanel jPanel21 = null;
//	protected JPanel jPanel22 = null;
//	// del h.yoshitama 2009/09/25
//	//	protected JPanel jPanel23 = null;
//	protected JPanel jPanel25 = null;
//	protected JPanel jPanel26 = null;
//	protected JPanel jPanel28 = null;
//	protected JPanel jPanel211 = null;
//	protected ExtendedLabel jLabel6 = null;
//	protected ExtendedTextField jTextField_TsuikaTankaSum = null;
//	protected JPanel jPanel231 = null;
//	protected ExtendedLabel jLabel151 = null;
//	protected ExtendedLabel jLabel152 = null;
//	protected ExtendedLabel jLabel153 = null;
//	protected ExtendedLabel jLabel154 = null;
//	// del h.yoshitama 2009/09/25
//	//protected ExtendedLabel jLabel155 = null;
//	protected ExtendedTextField jTextField_MadoguchiKihonKinInput = null;
//	protected ExtendedLabel jLabel_kingakuInput = null;
//	protected ExtendedTextField jTextField_MadoguchiSyousaiKinInput = null;
//	protected ExtendedTextField jTextField_MadoguchiTsuikaKinInput = null;
//	protected ExtendedTextField jTextField_MadoguchiDocKinInput = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon = null;
//	protected ExtendedLabel jLabel_UnitMadoSyosai = null;
//	protected ExtendedLabel jLabel_UnitMadoTsuika = null;
//	protected ExtendedLabel jLabel_UnitMadoNingenDoc = null;
//	protected ExtendedLabel jLabel31 = null;
//	protected ExtendedLabel jLabel32 = null;
//	protected ExtendedTextField jTextField_hihokenjasyotouKigou = null;
//	protected ExtendedTextField jTextField_hihokenjasyotouBangou = null;
//	protected ExtendedLabel jLabel_sonotaFutangaku = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon1 = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon2 = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon3 = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon4 = null;
//	protected ExtendedLabel jLabel_UnitMadoGoukei = null;
//	protected ExtendedLabel jLabel1541 = null;
//	protected ExtendedLabel jLabel1542 = null;
//	protected ExtendedLabel jLabel1543 = null;
//	protected ExtendedLabel jLabel1544 = null;
//	protected ExtendedLabel jLabel1545 = null;
//	protected ExtendedLabel jLabel1546 = null;
//	protected ExtendedLabel jLabel1548 = null;
//	protected ExtendedTextField jTextField_MadoguchiSonota = null;
//	protected ExtendedLabel jLabel1547 = null;
//	protected ExtendedLabel jLabel_kingaku1 = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon11 = null;
//	protected ExtendedTextField jTextField_hokenjyaJougenKihon = null;
//	protected ExtendedTextField jTextField_hokenjyaJougenSyosai = null;
//	protected ExtendedTextField jTextField_hokenjyaJougenTsuika = null;
//	protected ExtendedTextField jTextField_hokenjyaJougenDoc = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon111 = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon112 = null;
//	protected ExtendedLabel jLabel_UnitMadoKihon113 = null;
//	protected ExtendedComboBox jComboBox_SeikyuKubun = null;
//	protected ExtendedLabel jLabel33 = null;
//
//	// add h.yoshitama 2009/09/25
//	protected JPanel jPanel100 = null;
//	protected JPanel jPanel200 = null;
//	protected ExtendedLabel jLabelhanrei = null;
//	protected ExtendedLabel jLabelkanou = null;
//	protected ExtendedLabel jLabelhisu = null;
//	protected ExtendedLabel jLabel_ableExample = null;
//	protected ExtendedLabel jLabel_requiredExample = null;
//
//	// del h.yoshitama 2009/09/25
//	//protected ExtendedLabel jLabel40 = null;
//	protected ExtendedTextField jTextField_DocTanka = null;
//	/* ÉtÉHÅ[ÉJÉXà⁄ìÆêßå‰ */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;  //  @jve:decl-index=0:
//
//	/**
//	 * This is the default constructor
//	 */
//	public JInputKessaiDataFrame() {
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
////		this.setPreferredSize( null );
////		this.setSize(new Dimension(850, 600));
//		this.setLocationRelativeTo( null );
//		this.setVisible(true);
//		// edit s.inoue 2009/11/09
////		this.focusInitialize();
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
//			jPanel_Content.add(getJPanel_MainArea(), BorderLayout.CENTER);
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
//			GridBagConstraints gridBagConstraints116 = new GridBagConstraints();
//			gridBagConstraints116.gridy = 0;
//			gridBagConstraints116.gridx = 0;
//			GridBagConstraints gridBagConstraints115 = new GridBagConstraints();
//			gridBagConstraints115.gridy = 0;
//			gridBagConstraints115.gridx = 3;
//			GridBagConstraints gridBagConstraints114 = new GridBagConstraints();
//			gridBagConstraints114.gridy = 0;
//			gridBagConstraints114.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints114.gridx = 2;
//			GridBagConstraints gridBagConstraints113 = new GridBagConstraints();
//			gridBagConstraints113.gridy = 0;
//			gridBagConstraints113.weightx = 1.0D;
//			gridBagConstraints113.anchor = GridBagConstraints.EAST;
//			gridBagConstraints113.gridx = 1;
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			jPanel_ButtonArea.add(getJButton_ReCalc(), gridBagConstraints113);
//			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints114);
//			jPanel_ButtonArea.add(getJButton_Cancel(), gridBagConstraints115);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints116);
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
//			jButton_End.setActionCommand("èIóπ");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("ñﬂÇÈ(F1)");
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
//			jLabel_MainExpl = new ExtendedLabel();
//			jLabel_MainExpl.setText("êøãÅÉfÅ[É^ÇÃï“èWÇçsÇ¢Ç‹Ç∑ÅBílÇì¸óÕÇµÅuçƒåvéZÅvÉ{É^ÉìÇâüÇµÇƒèÓïÒÇìoò^ÇµÇ‹Ç∑ÅB");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_INPUTKESSAI_FRAME_TITLE);
////			jLabel_Title.setPreferredSize(new Dimension(500,40));
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
//			// edit s.inoue 2009/10/13
////			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
////			gridBagConstraints70.insets = new Insets(5, 0, 0, 0);
////			gridBagConstraints70.gridy = 1;
////			gridBagConstraints70.ipadx = 718;
////			gridBagConstraints70.gridx = 0;
////			gridBagConstraints70.fill = GridBagConstraints.WEST;
////			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
////			gridBagConstraints69.insets = new Insets(0, 0, 5, 0);
////			gridBagConstraints69.gridy = 0;
////			gridBagConstraints69.ipady = 10;
////			gridBagConstraints69.fill = GridBagConstraints.BOTH;
////			gridBagConstraints69.weightx = 1.0D;
////			gridBagConstraints69.gridx = 0;
////			jPanel_TitleArea = new JPanel();
////			jPanel_TitleArea.setLayout(new GridBagLayout());
////			jPanel_TitleArea.setName("jPanel2");
////			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints69);
////			jPanel_TitleArea.add(getJPanel_ExplArea1(), gridBagConstraints70);
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
//	// edit h.yoshitama 2009/09/25
//	/**
//	 * This method initializes jPanel_ExplArea2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea2() {
//		if (jPanel_ExplArea2 == null) {
//			GridLayout gridLayout1 = new GridLayout();
//			gridLayout1.setRows(1);
//			jPanel_ExplArea2 = new JPanel();
//			jPanel_ExplArea2.setName("jPanel4");
//			jPanel_ExplArea2.setLayout(gridLayout1);
//			jPanel_ExplArea2.add(jLabel_MainExpl, null);
//		}
//		return jPanel_ExplArea2;
//	}
//
//	// edit h.yoshitama 2009/09/25
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
//			jPanel_ExplArea1.setVisible(true);
//			jPanel_ExplArea1.setLayout(cardLayout1);
//			jPanel_ExplArea1.add(getJPanel_ExplArea2(), getJPanel_ExplArea2().getName());
//		}
//		return jPanel_ExplArea1;
//	}
//
//	/**
//	 * This method initializes jPanel_MainArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_MainArea() {
//		if (jPanel_MainArea == null) {
//			CardLayout cardLayout = new CardLayout();
//			cardLayout.setHgap(10);
//			cardLayout.setVgap(5);
//			jPanel_MainArea = new JPanel();
//			jPanel_MainArea.setLayout(cardLayout);
//			jPanel_MainArea.add(getJPanel_DrawArea(), getJPanel_DrawArea().getName());
//
//		}
//		return jPanel_MainArea;
//	}
//
//	/**
//	 * This method initializes jPanel_DrawArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_DrawArea() {
//		if (jPanel_DrawArea == null) {
//			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
//			gridBagConstraints75.gridx = 0;
//			gridBagConstraints75.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints75.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints75.gridy = 1;
//			jLabel6 = new ExtendedLabel();
//			jLabel6.setText("<b>í«â¡åíêfçÄñ⁄Åiì‡ñÛÅj</b>");
//			GridBagConstraints gridBagConstraints74 = new GridBagConstraints();
//			gridBagConstraints74.gridx = 0;
//			gridBagConstraints74.weightx = 1.0D;
//			gridBagConstraints74.fill = GridBagConstraints.BOTH;
//			gridBagConstraints74.gridy = 3;
//			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
//			gridBagConstraints73.gridx = 0;
//			gridBagConstraints73.fill = GridBagConstraints.BOTH;
//			gridBagConstraints73.weighty = 1.0D;
//			gridBagConstraints73.gridy = 2;
//			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
//			gridBagConstraints67.gridx = 0;
//			gridBagConstraints67.fill = GridBagConstraints.BOTH;
//			gridBagConstraints67.gridy = 0;
//
//			// add h.yoshitama 2009/09/25
//			GridBagConstraints gridBagConstraints999 = new GridBagConstraints();
//			gridBagConstraints999.gridx = 0;
//			gridBagConstraints999.fill = GridBagConstraints.BOTH;
//			gridBagConstraints999.gridy = 4;
//
//			jPanel_DrawArea = new JPanel();
//			jPanel_DrawArea.setLayout(new GridBagLayout());
//			jPanel_DrawArea.setName("jPanel_DrawArea");
//			jPanel_DrawArea.add(getJPanel24(), gridBagConstraints67);
//			jPanel_DrawArea.add(getJPanel_TableArea(), gridBagConstraints73);
//			jPanel_DrawArea.add(getJPanel_ExampleArea(), gridBagConstraints74);
//			jPanel_DrawArea.add(jLabel6, gridBagConstraints75);
//			// add h.yoshitama 2009/09/25
//			//jPanel_DrawArea.add(getJPanel200(), gridBagConstraints999);
//		}
//		return jPanel_DrawArea;
//	}
//
//	/**
//	 * This method initializes jPanel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel() {
//		if (jPanel == null) {
//			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
//			gridBagConstraints110.gridx = 1;
//			gridBagConstraints110.gridy = 0;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.insets = new Insets(0, 0, 0, 1);
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.ipadx = 229;
//			gridBagConstraints2.gridx = 2;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.ipadx = 194;
//			gridBagConstraints1.gridy = 0;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridx = 0;
//			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.gridy = 0;
//			jPanel = new JPanel();
//			jPanel.setLayout(new GridBagLayout());
//			jPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
//			jPanel.add(getJPanel1(), gridBagConstraints);
//			jPanel.add(getJPanel18(), gridBagConstraints110);
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
//			GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
//			gridBagConstraints92.gridy = 0;
//			gridBagConstraints92.anchor = GridBagConstraints.WEST;
//			gridBagConstraints92.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints92.gridx = 5;
//			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
//			gridBagConstraints91.gridy = 0;
//			gridBagConstraints91.anchor = GridBagConstraints.WEST;
//			gridBagConstraints91.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints91.gridx = 3;
//			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
//			gridBagConstraints90.gridx = 4;
//			gridBagConstraints90.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints90.gridy = 0;
//			jLabel32 = new ExtendedLabel();
//			jLabel32.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel32.setText("îÌï€åØé“èÿìôî‘çÜ");
//			jLabel32.setVisible(false);
//			GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
//			gridBagConstraints89.gridx = 2;
//			gridBagConstraints89.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints89.anchor = GridBagConstraints.WEST;
//			gridBagConstraints89.gridy = 0;
//			jLabel31 = new ExtendedLabel();
//			jLabel31.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel31.setText("îÌï€åØé“èÿìôãLçÜ");
//			jLabel31.setVisible(false);
//			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
//			gridBagConstraints17.gridy = 0;
//			gridBagConstraints17.anchor = GridBagConstraints.WEST;
//			gridBagConstraints17.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints17.weightx = 1.0D;
//			gridBagConstraints17.gridx = 7;
//			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
//			gridBagConstraints16.gridx = 6;
//			gridBagConstraints16.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints16.anchor = GridBagConstraints.WEST;
//			gridBagConstraints16.gridy = 0;
//			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
//			gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints15.gridy = 2;
//			gridBagConstraints15.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints15.anchor = GridBagConstraints.WEST;
//			gridBagConstraints15.gridx = 3;
//			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
//			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints14.gridy = 0;
//			gridBagConstraints14.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints14.anchor = GridBagConstraints.WEST;
//			gridBagConstraints14.gridx = 5;
//			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
//			gridBagConstraints13.gridx = 2;
//			gridBagConstraints13.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints13.anchor = GridBagConstraints.WEST;
//			gridBagConstraints13.gridy = 1;
//			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
//			gridBagConstraints12.gridx = 4;
//			gridBagConstraints12.anchor = GridBagConstraints.WEST;
//			gridBagConstraints12.insets = new Insets(0, 10, 0, 10);
//			gridBagConstraints12.gridy = 0;
//			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
//			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints11.gridy = 1;
//			gridBagConstraints11.anchor = GridBagConstraints.WEST;
//			gridBagConstraints11.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints11.gridx = 1;
//			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
//			gridBagConstraints10.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints10.gridy = 0;
//			gridBagConstraints10.anchor = GridBagConstraints.WEST;
//			gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
//			gridBagConstraints10.gridx = 3;
//			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
//			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints9.gridy = 0;
//			gridBagConstraints9.anchor = GridBagConstraints.WEST;
//			gridBagConstraints9.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints9.gridx = 1;
//			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
//			gridBagConstraints8.gridx = 0;
//			gridBagConstraints8.anchor = GridBagConstraints.WEST;
//			gridBagConstraints8.gridy = 1;
//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridx = 2;
//			gridBagConstraints7.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints7.anchor = GridBagConstraints.WEST;
//			gridBagConstraints7.gridy = 0;
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridx = 0;
//			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
//			gridBagConstraints6.gridy = 0;
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.gridx = 2;
//			gridBagConstraints5.ipadx = 193;
//			gridBagConstraints5.gridy = 2;
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridx = 2;
//			gridBagConstraints4.ipadx = 193;
//			gridBagConstraints4.gridy = 1;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridx = 2;
//			gridBagConstraints3.ipadx = 169;
//			gridBagConstraints3.gridy = 0;
//			// edit s.inoue 2009/10/13
////			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
////			gridBagConstraints17.gridy = 1;
////			gridBagConstraints17.anchor = GridBagConstraints.WEST;
////			gridBagConstraints17.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints17.weightx = 1.0D;
////			gridBagConstraints17.gridx = 5;
////			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
////			gridBagConstraints16.gridx = 4;
////			gridBagConstraints16.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints16.anchor = GridBagConstraints.WEST;
////			gridBagConstraints16.gridy = 1;
////			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
////			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
////			gridBagConstraints14.gridy = 2;
////			gridBagConstraints14.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints14.anchor = GridBagConstraints.BOTH;
////			gridBagConstraints14.gridx = 3;
////			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
////			gridBagConstraints13.gridx = 1;
////			gridBagConstraints13.insets = new Insets(0, 20, 0, 0);
////			gridBagConstraints13.anchor = GridBagConstraints.WEST;
////			gridBagConstraints13.gridy = 3;
////			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
////			gridBagConstraints12.gridx = 2;
////			gridBagConstraints12.anchor = GridBagConstraints.WEST;
////			gridBagConstraints12.insets = new Insets(0, 20, 0, 10);
////			gridBagConstraints12.gridy = 1;
////			// edit ver2 s.inoue 2009/08/28
////			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
////			gridBagConstraints11.fill = GridBagConstraints.VERTICAL;
////			gridBagConstraints11.gridy = 0;
////			gridBagConstraints11.anchor = GridBagConstraints.WEST;
////			gridBagConstraints11.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints11.gridx = 5;
////			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
////			gridBagConstraints10.fill = GridBagConstraints.VERTICAL;
////			gridBagConstraints10.gridy = 0;
////			gridBagConstraints10.anchor = GridBagConstraints.WEST;
////			gridBagConstraints10.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints10.gridx = 3;
////			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
////			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
////			gridBagConstraints9.gridy = 0;
////			gridBagConstraints9.anchor = GridBagConstraints.WEST;
////			gridBagConstraints9.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints9.gridx = 1;
////			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
////			gridBagConstraints8.gridx = 4;
////			gridBagConstraints8.anchor = GridBagConstraints.BOTH;
////			gridBagConstraints8.insets = new Insets(0, 20, 0, 10);
////			gridBagConstraints8.gridy = 0;
////			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
////			gridBagConstraints7.gridx = 2;
////			gridBagConstraints7.anchor = GridBagConstraints.BOTH;
////			gridBagConstraints7.gridy = 0;
////			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
////			gridBagConstraints6.gridx = 0;
////			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
////			gridBagConstraints6.gridy = 0;
////			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
////			gridBagConstraints5.gridx = 2;
////			gridBagConstraints5.ipadx = 193;
////			gridBagConstraints5.gridy = 2;
////			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
////			gridBagConstraints4.gridx = 2;
////			gridBagConstraints4.ipadx = 193;
////			gridBagConstraints4.gridy = 1;
////			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
////			gridBagConstraints3.gridx = 2;
////			gridBagConstraints3.ipadx = 169;
////			gridBagConstraints3.gridy = 0;
//
//			jLabel = new ExtendedLabel();
//			jLabel.setText("éÛêfåîêÆóùî‘çÜ");
//
//			jLabel1 = new ExtendedLabel();
//			jLabel1.setText("éÛêfé“éÅñº");
//
//			jLabel2 = new ExtendedLabel();
//			jLabel2.setText("åíêfé¿é{ì˙");
//
//			jLabel3 = new ExtendedLabel();
//			jLabel3.setText("ê∂îNåéì˙");
//
//			jLabel5 = new ExtendedLabel();
//			jLabel5.setText("ê´ï ");
//
//			jPanel1 = new JPanel();
//			jPanel1.setLayout(new GridBagLayout());
//			jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel1.add(jLabel, gridBagConstraints6);
//			jPanel1.add(jLabel1, gridBagConstraints7);
//			jPanel1.add(jLabel2, gridBagConstraints8);
//			jPanel1.add(getJTextField_Jyusinken_ID(), gridBagConstraints9);
//			jPanel1.add(getJTextField_Name(), gridBagConstraints10);
//			jPanel1.add(getJTextField_KensaDate(), gridBagConstraints11);
//			jPanel1.add(jLabel3, gridBagConstraints12);
//			jPanel1.add(getJTextField_Birthday(), gridBagConstraints14);
//			jPanel1.add(jLabel5, gridBagConstraints16);
//			jPanel1.add(getJTextField_Sex(), gridBagConstraints17);
//			jPanel1.add(jLabel31, gridBagConstraints89);
//			jPanel1.add(jLabel32, gridBagConstraints90);
//			jPanel1.add(getJTextField_hihokenjasyotouKigou(), gridBagConstraints91);
//			jPanel1.add(getJTextField_hihokenjasyotouBangou(), gridBagConstraints92);
//		}
//		return jPanel1;
//	}
//
//	/**
//	 * This method initializes jTextField_Jyusinken_ID
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_Jyusinken_ID() {
//		if (jTextField_Jyusinken_ID == null) {
//			jTextField_Jyusinken_ID = new ExtendedTextField("", 11, ImeMode.IME_OFF);
//			jTextField_Jyusinken_ID.setPreferredSize(new Dimension(150, 20));
//		}
//		return jTextField_Jyusinken_ID;
//	}
//
//	/**
//	 * This method initializes jTextField_Name
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_Name() {
//		if (jTextField_Name == null) {
//			jTextField_Name = new ExtendedTextField(ImeMode.IME_ZENKAKU);
//			jTextField_Name.setPreferredSize(new Dimension(150, 20));
//		}
//		return jTextField_Name;
//	}
//
//	/**
//	 * This method initializes jTextField_KensaDate
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_KensaDate() {
//		if (jTextField_KensaDate == null) {
//			jTextField_KensaDate = new ExtendedTextField("", 8, ImeMode.IME_OFF);
//			jTextField_KensaDate.setPreferredSize(new Dimension(100, 20));
//		}
//		return jTextField_KensaDate;
//	}
//
//	/**
//	 * This method initializes jTextField_Birthday
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_Birthday() {
//		if (jTextField_Birthday == null) {
//			jTextField_Birthday = new ExtendedTextField("", 8, ImeMode.IME_OFF);
//			// edit ver2 s.inoue 2009/08/28
//			jTextField_Birthday.setPreferredSize(new Dimension(100, 20));
//		}
//		return jTextField_Birthday;
//	}
//
//	/**
//	 * This method initializes jPanel13
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel13() {
//		if (jPanel13 == null) {
//			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
//			gridBagConstraints28.gridx = 0;
//			gridBagConstraints28.fill = GridBagConstraints.BOTH;
//			gridBagConstraints28.gridwidth = 2;
//			gridBagConstraints28.weightx = 1.0D;
//			gridBagConstraints28.gridy = 1;
//			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
//			gridBagConstraints21.gridx = 0;
//			gridBagConstraints21.gridwidth = 2;
//			gridBagConstraints21.anchor = GridBagConstraints.NORTHWEST;
//			gridBagConstraints21.weightx = 1.0D;
//			gridBagConstraints21.fill = GridBagConstraints.BOTH;
//			gridBagConstraints21.gridy = 0;
//			jPanel13 = new JPanel();
//			jPanel13.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel13.setLayout(new GridBagLayout());
//			jPanel13.add(getJPanel17(), gridBagConstraints21);
//			jPanel13.add(getJPanel210(), gridBagConstraints28);
//		}
//		return jPanel13;
//	}
//
//	/**
//	 * This method initializes jPanel17
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel17() {
//		if (jPanel17 == null) {
//			GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
//			gridBagConstraints72.gridx = 5;
//			gridBagConstraints72.ipadx = 10;
//			gridBagConstraints72.ipady = 10;
//			gridBagConstraints72.weightx = 10.0D;
//			gridBagConstraints72.gridy = 0;
//			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
//			gridBagConstraints44.gridx = 2;
//			gridBagConstraints44.gridy = 0;
//			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
//			gridBagConstraints43.gridx = 1;
//			gridBagConstraints43.gridy = 0;
//			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
//			gridBagConstraints19.insets = new Insets(4, 0, 4, 0);
//			gridBagConstraints19.gridy = 0;
//			gridBagConstraints19.gridx = 0;
//
//			// add s.inoue 20081008
//			GridBagConstraints gridBagConstraints117 = new GridBagConstraints();
//			gridBagConstraints117.fill = GridBagConstraints.BOTH;
//			gridBagConstraints117.insets = new Insets(0, 10, 0, 25);
//			gridBagConstraints117.gridx = 4;
//			gridBagConstraints117.gridy = 0;
//			gridBagConstraints117.weightx = 1.0D;
//			// label
//			GridBagConstraints gridBagConstraints118 = new GridBagConstraints();
//			gridBagConstraints118.anchor = GridBagConstraints.WEST;
//			gridBagConstraints118.gridx = 3;
//			gridBagConstraints118.gridy = 0;
//			gridBagConstraints118.insets = new Insets(0, 10, 0, 0);
//
//			jLabel7 = new ExtendedLabel();
//			jLabel7.setText("<b>àœëıóøíPâøãÊï™</b>");
////			Font currentFont = jLabel7.getFont();
////			jLabel7.setFont(new Font(currentFont.getFontName(), Font.BOLD, currentFont.getSize()));
//			jPanel17 = new JPanel();
//			jPanel17.setLayout(new GridBagLayout());
//			jPanel17.add(jLabel7, gridBagConstraints19);
//			jPanel17.add(getJRadioButton_ItakuTankaKobetu(), gridBagConstraints43);
//			jPanel17.add(getJRadioButton_ItakuTankaSyudan(), gridBagConstraints44);
//			jPanel17.add(getJPanel211(), gridBagConstraints72);
//			jPanel17.add(getJComboBox_SeikyuKubun(), gridBagConstraints117);
//			jLabel33 = new ExtendedLabel();
//			jLabel33.setText("êøãÅãÊï™");
//			jPanel17.add(jLabel33, gridBagConstraints118);
//
//		}
//		return jPanel17;
//	}
//
//	/**
//	 * This method initializes jPanel18
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel18() {
//		if (jPanel18 == null) {
//			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
//			gridBagConstraints27.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints27.weightx = 1.0;
//			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
//			gridBagConstraints26.gridx = 0;
//			gridBagConstraints26.gridy = 0;
//			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
//			gridBagConstraints25.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints25.weightx = 1.0;
//			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
//			gridBagConstraints24.gridx = 0;
//			gridBagConstraints24.gridy = 1;
//			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
//			gridBagConstraints23.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints23.gridy = 0;
//			gridBagConstraints23.weightx = 1.0;
//			gridBagConstraints23.gridx = 1;
//			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
//			gridBagConstraints22.gridx = 0;
//			gridBagConstraints22.ipady = 4;
//			gridBagConstraints22.gridy = 0;
//
//			jLabel9 = new ExtendedLabel();
//			jLabel9.setText("êSìdê}åüç∏ÉRÅ[Éh");
//			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel9.setVisible(false);
//			jLabel10 = new ExtendedLabel();
//			jLabel10.setText("ä·íÍåüç∏ÉRÅ[Éh");
//			jLabel10.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel10.setVisible(false);
//
//			jLabel8 = new ExtendedLabel();
//			jLabel8.setText("ïnåååüç∏ÉRÅ[Éh");
//			jLabel8.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel8.setVisible(false);
//			jPanel18 = new JPanel();
//			jPanel18.setLayout(new GridBagLayout());
//			jPanel18.add(jLabel8, gridBagConstraints22);
//			jPanel18.add(getJTextField_HinketsuCode(), gridBagConstraints23);
//			jPanel18.add(jLabel9, new GridBagConstraints());
//			jPanel18.add(getJTextField_SindenzuCode(), gridBagConstraints27);
//			jPanel18.add(jLabel10, new GridBagConstraints());
//			jPanel18.add(getJTextField_GanteiCode(), gridBagConstraints25);
////			jPanel18.add(getJPanel20(), gridBagConstraints24);
////			jPanel18.add(getJPanel19(), gridBagConstraints26);
//		}
//		return jPanel18;
//	}
//
//	/**
//	 * This method initializes jPanel24
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel24() {
//		if (jPanel24 == null) {
//			BorderLayout borderLayout1 = new BorderLayout();
//			borderLayout1.setVgap(10);
//			jPanel24 = new JPanel();
//			jPanel24.setLayout(borderLayout1);
//			jPanel24.add(getJPanel(), BorderLayout.NORTH);
//			jPanel24.add(getJPanel13(), BorderLayout.CENTER);
//		}
//		return jPanel24;
//	}
//
//	/**
//	 * This method initializes jTextField_HinketsuCode
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_HinketsuCode() {
//		if (jTextField_HinketsuCode == null) {
//			jTextField_HinketsuCode = new ExtendedTextField();
//			jTextField_HinketsuCode.setVisible(false);
//		}
//		return jTextField_HinketsuCode;
//	}
//
//	/**
//	 * This method initializes jTextField_SindenzuCode
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_SindenzuCode() {
//		if (jTextField_SindenzuCode == null) {
//			jTextField_SindenzuCode = new ExtendedTextField();
//			jTextField_SindenzuCode.setVisible(false);
//		}
//		return jTextField_SindenzuCode;
//	}
//
//	/**
//	 * This method initializes jTextField_GanteiCode
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_GanteiCode() {
//		if (jTextField_GanteiCode == null) {
//			jTextField_GanteiCode = new ExtendedTextField();
//			jTextField_GanteiCode.setVisible(false);
//		}
//		return jTextField_GanteiCode;
//	}
//
//	/**
//	 * This method initializes jTextField_KihonTanka
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_KihonTanka() {
//		if (jTextField_KihonTanka == null) {
//			jTextField_KihonTanka = new ExtendedTextField();
//			jTextField_KihonTanka.setPreferredSize(new Dimension(80, 20));
//			jTextField_KihonTanka.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_KihonTanka;
//	}
//
//	/**
//	 * This method initializes jTextField_HinketuTanka
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_HinketuTanka() {
//		if (jTextField_HinketuTanka == null) {
//			jTextField_HinketuTanka = new ExtendedTextField();
//			jTextField_HinketuTanka.setPreferredSize(new Dimension(80, 20));
//			jTextField_HinketuTanka.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_HinketuTanka;
//	}
//
//	/**
//	 * This method initializes jTextField_SindenzuTanka
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_SindenzuTanka() {
//		if (jTextField_SindenzuTanka == null) {
//			jTextField_SindenzuTanka = new ExtendedTextField();
//			jTextField_SindenzuTanka.setPreferredSize(new Dimension(80, 20));
//			jTextField_SindenzuTanka.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_SindenzuTanka;
//	}
//
//	/**
//	 * This method initializes jTextField_GanteiTanka
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_GanteiTanka() {
//		if (jTextField_GanteiTanka == null) {
//			jTextField_GanteiTanka = new ExtendedTextField();
//			jTextField_GanteiTanka.setPreferredSize(new Dimension(80, 20));
//			jTextField_GanteiTanka.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_GanteiTanka;
//	}
//
//	/**
//	 * This method initializes jTextField_NingenDocTanka
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_NingenDocTanka() {
//		if (jTextField_DocTanka == null) {
//			jTextField_DocTanka = new ExtendedTextField();
//			jTextField_DocTanka.setPreferredSize(new Dimension(80, 20));
//			jTextField_DocTanka.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_DocTanka;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiKihonKin
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiKihonKin() {
//		if (jTextField_MadoguchiKihonKin == null) {
//			jTextField_MadoguchiKihonKin = new ExtendedTextField("", 6, ImeMode.IME_OFF);
//			jTextField_MadoguchiKihonKin.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiKihonKin.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiKihonKin;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiShousaiKin
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiShousaiKin() {
//		if (jTextField_MadoguchiSyousaiKin == null) {
//			jTextField_MadoguchiSyousaiKin = new ExtendedTextField("", 6, ImeMode.IME_OFF);
//			jTextField_MadoguchiSyousaiKin.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiSyousaiKin.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiSyousaiKin;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiTuikaKin
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiTuikaKin() {
//		if (jTextField_MadoguchiTsuikaKin == null) {
//			jTextField_MadoguchiTsuikaKin = new ExtendedTextField("", 6, ImeMode.IME_OFF);
//			jTextField_MadoguchiTsuikaKin.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiTsuikaKin.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiTsuikaKin;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiTuikaKin
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiNingenDocKin() {
//		if (jTextField_MadoguchiNingenDocKin == null) {
//			jTextField_MadoguchiNingenDocKin = new ExtendedTextField("", 6, ImeMode.IME_OFF);
//			jTextField_MadoguchiNingenDocKin.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiNingenDocKin.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiNingenDocKin;
//	}
//	/**
//	 * This method initializes jTextField_AllTanka
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_AllTanka() {
//		if (jTextField_AllTanka == null) {
//			jTextField_AllTanka = new ExtendedTextField();
//			jTextField_AllTanka.setPreferredSize(new Dimension(80, 20));
//			jTextField_AllTanka.setEditable(false);
//			jTextField_AllTanka.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_AllTanka;
//	}
//
//	/**
//	 * This method initializes jTextField_AllMadoguchi
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_AllMadoguchi() {
//		if (jTextField_AllMadoguchi == null) {
//			jTextField_AllMadoguchi = new ExtendedTextField();
//			jTextField_AllMadoguchi.setPreferredSize(new Dimension(80, 20));
//			jTextField_AllMadoguchi.setEditable(false);
//			jTextField_AllMadoguchi.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_AllMadoguchi;
//	}
//
//	/**
//	 * This method initializes jTextField_TotalFee
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_TotalFee() {
//		if (jTextField_TotalFee == null) {
//			jTextField_TotalFee = new ExtendedTextField();
//			jTextField_TotalFee.setPreferredSize(new Dimension(80, 20));
//			jTextField_TotalFee.setEditable(false);
//			jTextField_TotalFee.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_TotalFee;
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
//			jButton_Register.setText("ìoò^(F12)");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Register.addActionListener(this);
//		}
//		return jButton_Register;
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
//			jButton_Cancel.setText("ÉLÉÉÉìÉZÉã");
//			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Cancel.setVisible(false);
//			jButton_Cancel.addActionListener(this);
//		}
//		return jButton_Cancel;
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//	}
//
//	public void itemStateChanged( ItemEvent e )
//	{
//	}
//
//	// add s.inoue 2009/09/27
//	/**
//	 * This method initializes getJPanel_ExampleArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExampleArea() {
//		if (jPanel_ExampleArea == null) {
//
//			FlowLayout flowLayout1 = new FlowLayout(0,0,5);
//			flowLayout1.setAlignment(FlowLayout.LEFT);
//			jPanel_ExampleArea = new JPanel();
//			jPanel_ExampleArea.setLayout(flowLayout1);
//
//			jPanel_ExampleArea.add(getJPanel_Example());
//
//		}
//		return jPanel_ExampleArea;
//	}
//
//	// add s.inoue 2009/09/27
//	/**
//	 * This method initializes jPanel_Example
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_Example() {
//		if (jPanel_Example == null) {
//			FlowLayout flowLayout1 = new FlowLayout(0,0,0);
//			flowLayout1.setAlignment(FlowLayout.LEFT);
//			jPanel_Example = new JPanel();
//			jPanel_Example.setLayout(flowLayout1);
//			jPanel_Example.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//
//			jLabelhanrei = new ExtendedLabel();
//			jLabelhanrei.setFont(new Font("Dialog", Font.BOLD, 12));
//			jLabelhanrei.setPreferredSize(new Dimension(50, 16));
//			jLabelhanrei.setText("ñ}ó·");
//
////			jLabelkanou = new ExtendedLabel();
////			jLabelkanou.setFont(new Font("Dialog", Font.PLAIN, 12));
////			jLabelkanou.setText("ì¸óÕâ¬î\çÄñ⁄");
//
//			jLabelkanou = new ExtendedLabel();
//			jLabelkanou.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabelkanou.setText("Å@ì¸óÕçÄñ⁄");
//
//			jLabelhisu = new ExtendedLabel();
//			jLabelhisu.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabelhisu.setText("Å@ì¸óÕïKê{çÄñ⁄Å@");
//
//			jLabel_ableExample = new ExtendedLabel();
//			jLabel_ableExample.setPreferredSize(new Dimension(50, 20));
//			jLabel_ableExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
//			jLabel_ableExample.setSize(new Dimension(50, 20));
//			jLabel_ableExample.setOpaque(true);
//
//			jLabel_requiredExample = new ExtendedLabel();
//			jLabel_requiredExample.setPreferredSize(new Dimension(50, 20));
//			jLabel_requiredExample.setOpaque(true);
//			jLabel_requiredExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
//
//			jPanel_Example.add(jLabelhanrei);
//			jPanel_Example.add(jLabel_requiredExample);
//			jPanel_Example.add(jLabelhisu);
//			jPanel_Example.add(jLabel_ableExample);
//			jPanel_Example.add(jLabelkanou);
//
//		}
//		return jPanel_Example;
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
//	 * This method initializes jRadioButton_ItakuTankaKobetu
//	 *
//	 * @return javax.swing.ExtendedRadioButton
//	 */
//	private ExtendedRadioButton getJRadioButton_ItakuTankaKobetu() {
//		if (jRadioButton_ItakuTankaKobetu == null) {
//			jRadioButton_ItakuTankaKobetu = new ExtendedRadioButton();
//			jRadioButton_ItakuTankaKobetu.setText("å¬ï ");
////			jRadioButton_ItakuTankaKobetu.setEnabled(false);
//			jRadioButton_ItakuTankaKobetu
//					.addItemListener(this);
//		}
//		return jRadioButton_ItakuTankaKobetu;
//	}
//
//	/**
//	 * This method initializes jRadioButton_ItakuTankaSyudan
//	 *
//	 * @return javax.swing.ExtendedRadioButton
//	 */
//	private ExtendedRadioButton getJRadioButton_ItakuTankaSyudan() {
//		if (jRadioButton_ItakuTankaSyudan == null) {
//			jRadioButton_ItakuTankaSyudan = new ExtendedRadioButton();
//			jRadioButton_ItakuTankaSyudan.setText("èWíc");
////			jRadioButton_ItakuTankaSyudan.setEnabled(false);
//			jRadioButton_ItakuTankaSyudan
//					.addItemListener(this);
//		}
//		return jRadioButton_ItakuTankaSyudan;
//	}
//
//	/**
//	 * This method initializes jComboBox_SeikyuKubun
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_SeikyuKubun() {
//		if (jComboBox_SeikyuKubun == null) {
//			jComboBox_SeikyuKubun = new ExtendedComboBox();
//			jComboBox_SeikyuKubun.setPreferredSize(new Dimension(260, 20));
//			//jComboBox_SeikyuKubun.addKeyListener(this);
//		}
//		return jComboBox_SeikyuKubun;
//	}
//
//	/**
//	 * This method initializes jComboBox_MadoguchiKihonSyubetu
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_MadoguchiKihonSyubetu() {
//		if (jComboBox_MadoguchiKihonSyubetu == null) {
//			jComboBox_MadoguchiKihonSyubetu = new ExtendedComboBox();
//			jComboBox_MadoguchiKihonSyubetu.setPreferredSize(new Dimension(80, 20));
//			jComboBox_MadoguchiKihonSyubetu.addItemListener(this);
//		}
//		return jComboBox_MadoguchiKihonSyubetu;
//	}
//
//	/**
//	 * This method initializes jComboBox_MadoguchiSyousaiSyubetu
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_MadoguchiSyousaiSyubetu() {
//		if (jComboBox_MadoguchiSyousaiSyubetu == null) {
//			jComboBox_MadoguchiSyousaiSyubetu = new ExtendedComboBox();
//			jComboBox_MadoguchiSyousaiSyubetu.setPreferredSize(new Dimension(80, 20));
//			jComboBox_MadoguchiSyousaiSyubetu.addItemListener(this);
//		}
//		return jComboBox_MadoguchiSyousaiSyubetu;
//	}
//
//	/**
//	 * This method initializes jComboBox_MadoguchiTsuikaSyubetu
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_MadoguchiTsuikaSyubetu() {
//		if (jComboBox_MadoguchiTsuikaSyubetu == null) {
//			jComboBox_MadoguchiTsuikaSyubetu = new ExtendedComboBox();
//			jComboBox_MadoguchiTsuikaSyubetu.setPreferredSize(new Dimension(80, 20));
//			jComboBox_MadoguchiTsuikaSyubetu.addItemListener(this);
//		}
//		return jComboBox_MadoguchiTsuikaSyubetu;
//	}
//
//	/**
//	 * This method initializes jComboBox_MadoguchiDocSyubetu
//	 *
//	 * @return javax.swing.ExtendedComboBox
//	 */
//	private ExtendedComboBox getJComboBox_MadoguchiNingenDocSyubetu() {
//		if (jComboBox_MadoguchiDocSyubetu== null) {
//			jComboBox_MadoguchiDocSyubetu = new ExtendedComboBox();
//			jComboBox_MadoguchiDocSyubetu.setPreferredSize(new Dimension(80, 20));
//			jComboBox_MadoguchiDocSyubetu.addItemListener(this);
//		}
//		return jComboBox_MadoguchiDocSyubetu;
//	}
//
//	/**
//	 * This method initializes jTextField_Sex
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_Sex() {
//		if (jTextField_Sex == null) {
//			jTextField_Sex = new ExtendedTextField();
//			jTextField_Sex.setPreferredSize(new Dimension(50, 20));
//		}
//		return jTextField_Sex;
//	}
//
//	/**
//	 * This method initializes jPanel_TableArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_TableArea() {
//		if (jPanel_TableArea == null) {
//			jPanel_TableArea = new JPanel();
//			jPanel_TableArea.setLayout(new BorderLayout());
//		}
//		return jPanel_TableArea;
//	}
//
//	/**
//	 * This method initializes jButton_ReCalc
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_ReCalc() {
//		if (jButton_ReCalc == null) {
//			jButton_ReCalc = new ExtendedButton();
//			jButton_ReCalc.setText("çƒåvéZ(F11)");
//			jButton_ReCalc.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_ReCalc.addActionListener(this);
//		}
//		return jButton_ReCalc;
//	}
//
//	/**
//	 * This method initializes jPanel210
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel210() {
//		if (jPanel210 == null) {
//			/* êlä‘ÉhÉbÉN */
//			GridBagConstraints gridBagConstraints113 = new GridBagConstraints();
//			gridBagConstraints113.gridx = 0;
//			gridBagConstraints113.anchor = GridBagConstraints.WEST;
//			gridBagConstraints113.gridy = 8;
//			jLabel_ningenDoc = new ExtendedLabel();
//			jLabel_ningenDoc.setText("<b>êlä‘ÉhÉbÉN</b>");
//			GridBagConstraints gridBagConstraints114 = new GridBagConstraints();
//			gridBagConstraints114.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints114.gridy = 8;
//			gridBagConstraints114.gridx = 3;
//			GridBagConstraints gridBagConstraints115 = new GridBagConstraints();
//			gridBagConstraints115.gridx = 4;
//			gridBagConstraints115.gridy = 8;
//			jLabel1548 = new ExtendedLabel();
//			jLabel1548.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1548.setText("\u5186");
//			/* êlä‘ÉhÉbÉN ëãå˚ïâíS*/
//			GridBagConstraints gridBagConstraints116 = new GridBagConstraints();
//			gridBagConstraints116.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints116.gridy = 8;
//			gridBagConstraints116.gridx = 6;
//
//			GridBagConstraints gridBagConstraints117 = new GridBagConstraints();
//			gridBagConstraints117.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints117.gridy = 8;
//			gridBagConstraints117.gridx = 8;
//			GridBagConstraints gridBagConstraints118 = new GridBagConstraints();
//			gridBagConstraints118.gridx = 9;
//			gridBagConstraints118.gridy = 8;
//			jLabel_UnitMadoNingenDoc = new ExtendedLabel();
//			jLabel_UnitMadoNingenDoc.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoNingenDoc.setText("â~");
//
//			GridBagConstraints gridBagConstraints119 = new GridBagConstraints();
//			gridBagConstraints119.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints119.gridy = 8;
//			gridBagConstraints119.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints119.gridx = 10;
//
//			GridBagConstraints gridBagConstraints120 = new GridBagConstraints();
//			gridBagConstraints120.gridx = 11;
//			gridBagConstraints120.gridy = 7;
//			jLabel_UnitMadoKihon112 = new ExtendedLabel();
//			jLabel_UnitMadoKihon112.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon112.setText("\u5186");
//
//			GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
//			gridBagConstraints121.gridx = 11;
//			gridBagConstraints121.gridy = 8;
//			jLabel_UnitMadoKihon113 = new ExtendedLabel();
//			jLabel_UnitMadoKihon113.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon113.setText("\u5186");
//
//			GridBagConstraints gridBagConstraints122 = new GridBagConstraints();
//			gridBagConstraints122.fill = GridBagConstraints.NONE;
//			gridBagConstraints122.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints122.gridx = 12;
//			gridBagConstraints122.gridy = 8;
//
//			GridBagConstraints gridBagConstraints123 = new GridBagConstraints();
//			gridBagConstraints123.gridx = 13;
//			gridBagConstraints123.gridy = 8;
//
//			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
//			gridBagConstraints111.gridx = 11;
//			gridBagConstraints111.gridy = 3;
//			jLabel_UnitMadoKihon111 = new ExtendedLabel();
//			jLabel_UnitMadoKihon111.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon111.setText("\u5186");
//			GridBagConstraints gridBagConstraints109 = new GridBagConstraints();
//			gridBagConstraints109.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints109.gridy = 7;
//			gridBagConstraints109.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints109.gridx = 10;
//			GridBagConstraints gridBagConstraints108 = new GridBagConstraints();
//			gridBagConstraints108.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints108.gridy = 3;
//			gridBagConstraints108.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints108.gridx = 10;
//			GridBagConstraints gridBagConstraints107 = new GridBagConstraints();
//			gridBagConstraints107.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints107.gridy = 1;
//			gridBagConstraints107.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints107.gridx = 10;
//			GridBagConstraints gridBagConstraints106 = new GridBagConstraints();
//			gridBagConstraints106.gridx = 11;
//			gridBagConstraints106.gridy = 1;
//			jLabel_UnitMadoKihon11 = new ExtendedLabel();
//			jLabel_UnitMadoKihon11.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon11.setText("\u5186");
//			GridBagConstraints gridBagConstraints105 = new GridBagConstraints();
//			gridBagConstraints105.gridx = 10;
//			gridBagConstraints105.gridwidth = 2;
//			gridBagConstraints105.gridy = 0;
//			jLabel_kingaku1 = new ExtendedLabel();
//			jLabel_kingaku1.setText("<center><b>ï€åØé“ïâíS<br>è„å¿äz</b></center>");
//			GridBagConstraints gridBagConstraints104 = new GridBagConstraints();
//			gridBagConstraints104.gridx = 16;
//			gridBagConstraints104.gridy = 1;
//			jLabel1547 = new ExtendedLabel();
//			jLabel1547.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1547.setText("\u5186");
//			GridBagConstraints gridBagConstraints103 = new GridBagConstraints();
//			gridBagConstraints103.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints103.gridy = 1;
//			gridBagConstraints103.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints103.gridx = 15;
//			GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
//			gridBagConstraints101.gridx = 4;
//			gridBagConstraints101.gridy = 11;
//			jLabel1546 = new ExtendedLabel();
//			jLabel1546.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1546.setText("\u5186");
//			GridBagConstraints gridBagConstraints100 = new GridBagConstraints();
//			gridBagConstraints100.gridx = 4;
//			gridBagConstraints100.gridy = 7;
//			jLabel1545 = new ExtendedLabel();
//			jLabel1545.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1545.setText("\u5186");
//			GridBagConstraints gridBagConstraints99 = new GridBagConstraints();
//			gridBagConstraints99.gridx = 4;
//			gridBagConstraints99.gridy = 5;
//			jLabel1544 = new ExtendedLabel();
//			jLabel1544.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1544.setText("\u5186");
//			GridBagConstraints gridBagConstraints98 = new GridBagConstraints();
//			gridBagConstraints98.gridx = 4;
//			gridBagConstraints98.gridy = 4;
//			jLabel1543 = new ExtendedLabel();
//			jLabel1543.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1543.setText("\u5186");
//			GridBagConstraints gridBagConstraints97 = new GridBagConstraints();
//			gridBagConstraints97.gridx = 4;
//			gridBagConstraints97.gridy = 3;
//			jLabel1542 = new ExtendedLabel();
//			jLabel1542.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1542.setText("\u5186");
//			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
//			gridBagConstraints52.gridx = 4;
//			gridBagConstraints52.gridy = 1;
//			jLabel1541 = new ExtendedLabel();
//			jLabel1541.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1541.setText("\u5186");
//			GridBagConstraints gridBagConstraints94 = new GridBagConstraints();
//			gridBagConstraints94.gridx = 13;
//			gridBagConstraints94.gridy = 1;
//
//			GridBagConstraints gridBagConstraints95 = new GridBagConstraints();
//			gridBagConstraints95.gridx = 13;
//			gridBagConstraints95.gridy = 3;
//
//			GridBagConstraints gridBagConstraints96 = new GridBagConstraints();
//			gridBagConstraints96.gridx = 13;
//			gridBagConstraints96.gridy = 7;
//
//			GridBagConstraints gridBagConstraints102 = new GridBagConstraints();
//			gridBagConstraints102.gridx = 13;
//			gridBagConstraints102.gridy = 11;
//
//			jLabel_UnitMadoKihon1 = new ExtendedLabel();
//			jLabel_UnitMadoKihon1.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon1.setText("â~");
//
//			jLabel_UnitMadoKihon2 = new ExtendedLabel();
//			jLabel_UnitMadoKihon2.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon2.setText("â~");
//
//			jLabel_UnitMadoKihon3 = new ExtendedLabel();
//			jLabel_UnitMadoKihon3.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon3.setText("â~");
//
//			jLabel_UnitMadoKihon4 = new ExtendedLabel();
//			jLabel_UnitMadoKihon4.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon4.setText("â~");
//
//			jLabel_UnitMadoGoukei = new ExtendedLabel();
//			jLabel_UnitMadoGoukei.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoGoukei.setText("â~");
//
//			GridBagConstraints gridBagConstraints93 = new GridBagConstraints();
//			gridBagConstraints93.gridx = 15;
//			gridBagConstraints93.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints93.gridwidth = 2;
//			gridBagConstraints93.gridy = 0;
//			jLabel_sonotaFutangaku = new ExtendedLabel();
//			jLabel_sonotaFutangaku.setText("<center><b>ÇªÇÃëºÇÃåíêfÇ…<br>ÇÊÇÈïâíSã‡äz</b></center>");
//			GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
//			gridBagConstraints88.gridx = 9;
//			gridBagConstraints88.gridy = 7;
//			jLabel_UnitMadoTsuika = new ExtendedLabel();
//			jLabel_UnitMadoTsuika.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoTsuika.setText("â~");
//			GridBagConstraints gridBagConstraints87 = new GridBagConstraints();
//			gridBagConstraints87.gridx = 9;
//			gridBagConstraints87.gridy = 3;
//			jLabel_UnitMadoSyosai = new ExtendedLabel();
//			jLabel_UnitMadoSyosai.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoSyosai.setText("â~");
//			GridBagConstraints gridBagConstraints86 = new GridBagConstraints();
//			gridBagConstraints86.gridx = 9;
//			gridBagConstraints86.gridy = 1;
//			jLabel_UnitMadoKihon = new ExtendedLabel();
//			jLabel_UnitMadoKihon.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_UnitMadoKihon.setText("â~");
//			GridBagConstraints gridBagConstraints85 = new GridBagConstraints();
//			gridBagConstraints85.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints85.gridy = 7;
//			gridBagConstraints85.gridx = 8;
//			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
//			gridBagConstraints84.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints84.gridy = 3;
//			gridBagConstraints84.gridx = 8;
//			GridBagConstraints gridBagConstraints83 = new GridBagConstraints();
//			gridBagConstraints83.gridx = 8;
//			gridBagConstraints83.gridwidth = 2;
//			gridBagConstraints83.gridy = 0;
//			jLabel_kingakuInput = new ExtendedLabel();
//			jLabel_kingakuInput.setText("<center><b>ëãå˚ïâíS<br>ÅiéÛêfåîèÓïÒÅj</b></center>");
//			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
//			gridBagConstraints82.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints82.gridy = 1;
//			gridBagConstraints82.gridx = 8;
//			// del h.yoshitama 2009/09/25
////			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
////			gridBagConstraints81.gridx = 12;
////			gridBagConstraints81.gridy = 9;
////			jLabel155 = new ExtendedLabel();
////			jLabel155.setFont(new Font("Dialog", Font.PLAIN, 12));
////			jLabel155.setText("â~");
////			jLabel155.setVisible(false);
//			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
//			gridBagConstraints80.gridx = 16;
//			gridBagConstraints80.anchor = GridBagConstraints.WEST;
//			gridBagConstraints80.gridy = 11;
//			jLabel154 = new ExtendedLabel();
//			jLabel154.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel154.setText("â~");
//			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
//			gridBagConstraints71.gridx = 12;
//			gridBagConstraints71.gridy = 7;
//			jLabel153 = new ExtendedLabel();
//			jLabel153.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel153.setText("â~");
//			jLabel153.setVisible(false);
//			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
//			gridBagConstraints79.gridx = 12;
//			gridBagConstraints79.gridy = 3;
//			jLabel152 = new ExtendedLabel();
//			jLabel152.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel152.setText("â~");
//			jLabel152.setVisible(false);
//			GridBagConstraints gridBagConstraints78 = new GridBagConstraints();
//			gridBagConstraints78.gridx = 12;
//			gridBagConstraints78.gridy = 1;
//			jLabel151 = new ExtendedLabel();
//			jLabel151.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel151.setText("â~");
//			jLabel151.setVisible(false);
//			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
//			gridBagConstraints77.gridx = 0;
//			gridBagConstraints77.ipadx = 10;
//			gridBagConstraints77.ipady = 10;
//			gridBagConstraints77.gridy = 10;
//			GridBagConstraints gridBagConstraints76 = new GridBagConstraints();
//			gridBagConstraints76.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints76.gridy = 7;
//			gridBagConstraints76.gridx = 3;
//			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
//			gridBagConstraints68.gridx = 0;
//			gridBagConstraints68.ipady = 1;
//			gridBagConstraints68.insets = new Insets(10, 0, 0, 0);
//			gridBagConstraints68.gridwidth = 14;
//			gridBagConstraints68.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints68.gridy = 9;
//			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
//			gridBagConstraints66.gridx = 7;
//			gridBagConstraints66.ipadx = 10;
//			gridBagConstraints66.ipady = 10;
//			gridBagConstraints66.gridy = 1;
//			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
//			gridBagConstraints65.gridx = 5;
//			gridBagConstraints65.ipadx = 10;
//			gridBagConstraints65.ipady = 10;
//			gridBagConstraints65.gridy = 1;
//			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
//			gridBagConstraints64.gridx = 0;
//			gridBagConstraints64.ipadx = 10;
//			gridBagConstraints64.ipady = 10;
//			gridBagConstraints64.gridy = 2;
//			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
//			gridBagConstraints63.gridx = 1;
//			gridBagConstraints63.ipadx = 10;
//			gridBagConstraints63.ipady = 10;
//			gridBagConstraints63.gridy = 1;
//			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
//			gridBagConstraints62.gridx = 17;
//			gridBagConstraints62.ipady = 10;
//			gridBagConstraints62.ipadx = 10;
//			gridBagConstraints62.weightx = 1.0D;
//			gridBagConstraints62.gridy = 0;
//			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
//			gridBagConstraints61.gridx = 0;
//			gridBagConstraints61.anchor = GridBagConstraints.WEST;
//			gridBagConstraints61.gridy = 11;
//			jLabel_goukei = new ExtendedLabel();
//			jLabel_goukei.setText("<b>çáåv</b>");
//			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
//			gridBagConstraints60.gridx = 0;
//			gridBagConstraints60.anchor = GridBagConstraints.WEST;
//			gridBagConstraints60.gridy = 7;
//			jLabel_tsuika = new ExtendedLabel();
//			jLabel_tsuika.setText("<b>í«â¡åíêf</b>");
//			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
//			gridBagConstraints59.gridx = 0;
//			gridBagConstraints59.anchor = GridBagConstraints.WEST;
//			gridBagConstraints59.gridy = 3;
//			jLabel_syousai = new ExtendedLabel();
//			jLabel_syousai.setText("<b>è⁄ç◊Ç»åíêf</b>");
//			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
//			gridBagConstraints58.gridx = 0;
//			gridBagConstraints58.gridy = 1;
//			jLabel_kihon = new ExtendedLabel();
//			jLabel_kihon.setText("<b>äÓñ{ìIÇ»åíêf</b>");
//			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
//			gridBagConstraints57.gridx = 12;
//			gridBagConstraints57.gridwidth = 2;
//			gridBagConstraints57.gridy = 0;
//			jLabel_kingaku = new ExtendedLabel();
//			jLabel_kingaku.setText("<center><b>ëãå˚ïâíSã‡äz<br>Åié¿ç€ÇÃïâíSäzÅj</b></center>");
//			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
//			gridBagConstraints56.gridx = 6;
//			gridBagConstraints56.gridy = 0;
//			jLabel_syubetu = new ExtendedLabel();
//			jLabel_syubetu.setText("<center><b>éÛêfé“ÇÃ<br>ëãå˚ïâíS</b></center>");
//			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
//			gridBagConstraints50.gridx = 3;
//			gridBagConstraints50.gridy = 0;
//			jLabel_tanka = new ExtendedLabel();
//			jLabel_tanka.setText("<b>íPâø</b>");
//			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
//			gridBagConstraints49.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints49.gridy = 7;
//			gridBagConstraints49.gridx = 6;
//			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
//			gridBagConstraints48.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints48.gridy = 3;
//			gridBagConstraints48.gridx = 6;
//			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
//			gridBagConstraints46.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints46.gridy = 1;
//			gridBagConstraints46.gridx = 6;
//			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
//			gridBagConstraints47.gridx = 4;
//			gridBagConstraints47.anchor = GridBagConstraints.WEST;
//			gridBagConstraints47.gridy = 6;
//			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
//			gridBagConstraints45.gridx = 4;
//			gridBagConstraints45.anchor = GridBagConstraints.WEST;
//			gridBagConstraints45.gridy = 3;
//			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
//			gridBagConstraints20.gridx = 4;
//			gridBagConstraints20.gridy = 1;
//			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
//			gridBagConstraints55.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints55.gridy = 11;
//			gridBagConstraints55.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints55.gridx = 15;
//			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
//			gridBagConstraints54.gridx = 15;
//			gridBagConstraints54.anchor = GridBagConstraints.CENTER;
//			gridBagConstraints54.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints54.gridy = 9;
//			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
//			gridBagConstraints53.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints53.gridy = 11;
//			gridBagConstraints53.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints53.gridx = 12;
////			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
////			gridBagConstraints52.gridx = 6;
////			gridBagConstraints52.anchor = GridBagConstraints.WEST;
////			gridBagConstraints52.gridy = 9;
//			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
//			gridBagConstraints51.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints51.gridy = 11;
//			gridBagConstraints51.gridx = 3;
//			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
//			gridBagConstraints18.gridx = 2;
//			gridBagConstraints18.anchor = GridBagConstraints.WEST;
//			gridBagConstraints18.gridy = 11;
//			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
//			gridBagConstraints42.fill = GridBagConstraints.NONE;
//			gridBagConstraints42.gridy = 7;
//			gridBagConstraints42.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints42.gridx = 12;
//			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
//			gridBagConstraints41.gridx = 6;
//			gridBagConstraints41.anchor = GridBagConstraints.WEST;
//			gridBagConstraints41.gridy = 6;
//			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
//			gridBagConstraints40.fill = GridBagConstraints.NONE;
//			gridBagConstraints40.gridy = 3;
//			gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints40.gridx = 12;
//			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
//			gridBagConstraints39.gridx = 6;
//			gridBagConstraints39.anchor = GridBagConstraints.WEST;
//			gridBagConstraints39.gridy = 3;
//			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
//			gridBagConstraints38.fill = GridBagConstraints.NONE;
//			gridBagConstraints38.gridy = 1;
//			gridBagConstraints38.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints38.gridx = 12;
//			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
//			gridBagConstraints37.gridx = 6;
//			gridBagConstraints37.anchor = GridBagConstraints.WEST;
//			gridBagConstraints37.gridy = 1;
//			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
//			gridBagConstraints36.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints36.gridy = 5;
//			gridBagConstraints36.gridx = 3;
//			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
//			gridBagConstraints35.gridx = 2;
//			gridBagConstraints35.anchor = GridBagConstraints.WEST;
//			gridBagConstraints35.gridy = 5;
//			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
//			gridBagConstraints34.fill = GridBagConstraints.VERTICAL;
//			gridBagConstraints34.gridy = 4;
//			gridBagConstraints34.gridx = 3;
//			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
//			gridBagConstraints33.gridx = 2;
//			gridBagConstraints33.anchor = GridBagConstraints.WEST;
//			gridBagConstraints33.insets = new Insets(0, 0, 0, 10);
//			gridBagConstraints33.gridy = 4;
//			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
//			gridBagConstraints32.fill = GridBagConstraints.NONE;
//			gridBagConstraints32.gridy = 3;
//			gridBagConstraints32.gridx = 3;
//			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
//			gridBagConstraints31.gridx = 2;
//			gridBagConstraints31.anchor = GridBagConstraints.WEST;
//			gridBagConstraints31.gridy = 3;
//			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
//			gridBagConstraints30.fill = GridBagConstraints.NONE;
//			gridBagConstraints30.gridy = 1;
//			gridBagConstraints30.gridx = 3;
//			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
//			gridBagConstraints29.gridx = 2;
//			gridBagConstraints29.anchor = GridBagConstraints.WEST;
//			gridBagConstraints29.gridy = 1;
//
////			jLabel14 = new ExtendedLabel();
////			jLabel14.setText("íPâøÅiäÓñ{ìIÇ»åíêfÅj");
////			jLabel14.setFont(new Font("Dialog", Font.PLAIN, 12));
////			jLabel14.setVisible(false);
//
//			jLabel15 = new ExtendedLabel();
//			jLabel15.setText("ïnåååüç∏");
//			jLabel15.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//			jLabel16 = new ExtendedLabel();
//			jLabel16.setText("êSìdê}åüç∏");
//			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//			jLabel17 = new ExtendedLabel();
//			jLabel17.setText("ä·íÍåüç∏");
//			jLabel17.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//			jLabel23 = new ExtendedLabel();
//			jLabel23.setText("<b>êøãÅã‡äz</b>");
//
//			jPanel210 = new JPanel();
//			jPanel210.setLayout(new GridBagLayout());
//			jPanel210.add(getJTextField_KihonTanka(), gridBagConstraints30);
//			jPanel210.add(jLabel15, gridBagConstraints31);
//			jPanel210.add(getJTextField_HinketuTanka(), gridBagConstraints32);
//			jPanel210.add(jLabel16, gridBagConstraints33);
//			jPanel210.add(getJTextField_SindenzuTanka(), gridBagConstraints34);
//			jPanel210.add(jLabel17, gridBagConstraints35);
//			jPanel210.add(getJTextField_GanteiTanka(), gridBagConstraints36);
//			jPanel210.add(getJTextField_NingenDocTanka(), gridBagConstraints114);
//			jPanel210.add(getJTextField_MadoguchiKihonKin(), gridBagConstraints38);
//			jPanel210.add(getJTextField_MadoguchiShousaiKin(), gridBagConstraints40);
//			jPanel210.add(getJTextField_MadoguchiTuikaKin(), gridBagConstraints42);
//			jPanel210.add(getJTextField_MadoguchiNingenDocKin(), gridBagConstraints122);
//
//			jPanel210.add(getJTextField_AllTanka(), gridBagConstraints51);
//			jPanel210.add(getJTextField_AllMadoguchi(), gridBagConstraints53);
//			jPanel210.add(jLabel23, gridBagConstraints54);
//			jPanel210.add(getJTextField_TotalFee(), gridBagConstraints55);
//			jPanel210.add(getJComboBox_MadoguchiKihonSyubetu(), gridBagConstraints46);
//			jPanel210.add(getJComboBox_MadoguchiSyousaiSyubetu(), gridBagConstraints48);
//			jPanel210.add(getJComboBox_MadoguchiTsuikaSyubetu(), gridBagConstraints49);
//			jPanel210.add(getJComboBox_MadoguchiNingenDocSyubetu(), gridBagConstraints116);
//
//			jPanel210.add(jLabel_tanka, gridBagConstraints50);
//			jPanel210.add(jLabel_syubetu, gridBagConstraints56);
//			jPanel210.add(jLabel_kingaku, gridBagConstraints57);
//			jPanel210.add(jLabel_kihon, gridBagConstraints58);
//			jPanel210.add(jLabel_syousai, gridBagConstraints59);
//			jPanel210.add(jLabel_tsuika, gridBagConstraints60);
//			jPanel210.add(jLabel_goukei, gridBagConstraints61);
//			jPanel210.add(getJPanel21(), gridBagConstraints62);
//			jPanel210.add(getJPanel22(), gridBagConstraints63);
//			// delete ver2 s.inoue 2009/06/17
//			//jPanel210.add(getJPanel23(), gridBagConstraints64);
//			jPanel210.add(getJPanel25(), gridBagConstraints65);
//			jPanel210.add(getJPanel26(), gridBagConstraints66);
//			jPanel210.add(getJPanel28(), gridBagConstraints68);
//			jPanel210.add(getJTextField_TsuikaTankaSum(), gridBagConstraints76);
//			// delete ver2 s.inoue 2009/06/17
//			jPanel210.add(getJPanel231(), gridBagConstraints77);
//			jPanel210.add(jLabel151, gridBagConstraints78);
//			jPanel210.add(jLabel152, gridBagConstraints79);
//			jPanel210.add(jLabel153, gridBagConstraints71);
//			jPanel210.add(jLabel154, gridBagConstraints80);
//			// delete ver2 s.inoue 2009/06/17
//			//jPanel210.add(jLabel155, gridBagConstraints81);
//			jPanel210.add(getJTextField_MadoguchiKihonKinInput(), gridBagConstraints82);
//			jPanel210.add(getJTextField_MadoguchiNingenDocKinInput(), gridBagConstraints117);
//
//			jPanel210.add(jLabel_kingakuInput, gridBagConstraints83);
//			jPanel210.add(getJTextField_MadoguchiSyhosaiKinInput(), gridBagConstraints84);
//			jPanel210.add(getJTextField_MadoguchiTsuikaKinInput(), gridBagConstraints85);
//			jPanel210.add(jLabel_UnitMadoKihon, gridBagConstraints86);
//			jPanel210.add(jLabel_UnitMadoSyosai, gridBagConstraints87);
//			jPanel210.add(jLabel_UnitMadoTsuika, gridBagConstraints88);
//			jPanel210.add(jLabel_UnitMadoNingenDoc, gridBagConstraints118);
//
//			jPanel210.add(jLabel_sonotaFutangaku, gridBagConstraints93);
//			jPanel210.add(jLabel_UnitMadoKihon1, gridBagConstraints94);
//			jPanel210.add(jLabel_UnitMadoKihon2, gridBagConstraints95);
//			jPanel210.add(jLabel_UnitMadoKihon3, gridBagConstraints96);
//			jPanel210.add(jLabel_UnitMadoKihon4, gridBagConstraints123);
//			jPanel210.add(jLabel_UnitMadoGoukei, gridBagConstraints102);
//			jPanel210.add(jLabel1541, gridBagConstraints52);
//			jPanel210.add(jLabel1542, gridBagConstraints97);
//			jPanel210.add(jLabel1543, gridBagConstraints98);
//			jPanel210.add(jLabel1544, gridBagConstraints99);
//			jPanel210.add(jLabel1545, gridBagConstraints100);
//			jPanel210.add(jLabel1546, gridBagConstraints101);
//			jPanel210.add(getJTextField_MadoguchiSonota(), gridBagConstraints103);
//			jPanel210.add(jLabel1547, gridBagConstraints104);
//			jPanel210.add(jLabel1548, gridBagConstraints115);
//			jPanel210.add(jLabel_kingaku1, gridBagConstraints105);
//			jPanel210.add(jLabel_UnitMadoKihon11, gridBagConstraints106);
//			jPanel210.add(getJTextField_hokenjyaJougenKihon(), gridBagConstraints107);
//			jPanel210.add(getJTextField_hokenjyaJougenSyosai(), gridBagConstraints108);
//			jPanel210.add(getJTextField_hokenjyaJougenTsuika(), gridBagConstraints109);
//			jPanel210.add(getJTextField_hokenjyaJougenNingenDoc(), gridBagConstraints119);
//
//			jPanel210.add(jLabel_UnitMadoKihon111, gridBagConstraints111);
//			jPanel210.add(jLabel_UnitMadoKihon112, gridBagConstraints120);
//			jPanel210.add(jLabel_UnitMadoKihon113, gridBagConstraints121);
//
//			jPanel210.add(jLabel_ningenDoc, gridBagConstraints113);
//		}
//		return jPanel210;
//	}
//
//	/**
//	 * This method initializes jPanel21
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel21() {
//		if (jPanel21 == null) {
//			jPanel21 = new JPanel();
//			jPanel21.setLayout(new GridBagLayout());
//		}
//		return jPanel21;
//	}
//
//	/**
//	 * This method initializes jPanel22
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel22() {
//		if (jPanel22 == null) {
//			jPanel22 = new JPanel();
//			jPanel22.setLayout(new GridBagLayout());
//		}
//		return jPanel22;
//	}
//
//	/**
//	 * This method initializes jPanel25
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel25() {
//		if (jPanel25 == null) {
//			jPanel25 = new JPanel();
//			jPanel25.setLayout(new GridBagLayout());
//		}
//		return jPanel25;
//	}
//
//	/**
//	 * This method initializes jPanel26
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel26() {
//		if (jPanel26 == null) {
//			jPanel26 = new JPanel();
//			jPanel26.setLayout(new GridBagLayout());
//		}
//		return jPanel26;
//	}
//
//	/**
//	 * This method initializes jPanel28
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel28() {
//		if (jPanel28 == null) {
//			jPanel28 = new JPanel();
//			jPanel28.setLayout(new GridBagLayout());
//			jPanel28.setBackground(Color.black);
//		}
//		return jPanel28;
//	}
//
//	/**
//	 * This method initializes jPanel211
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel211() {
//		if (jPanel211 == null) {
//			jPanel211 = new JPanel();
//			jPanel211.setLayout(new GridBagLayout());
//		}
//		return jPanel211;
//	}
//
//	/**
//	 * This method initializes jTextField_TsuikaTankaSum
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_TsuikaTankaSum() {
//		if (jTextField_TsuikaTankaSum == null) {
//			jTextField_TsuikaTankaSum = new ExtendedTextField();
//			jTextField_TsuikaTankaSum.setPreferredSize(new Dimension(80, 20));
//			jTextField_TsuikaTankaSum.setHorizontalAlignment(JTextField.RIGHT);
//			// edit s.inoue 2010/02/16
//			// jTextField_TsuikaTankaSum.setEditable(false);
//		}
//		return jTextField_TsuikaTankaSum;
//	}
//
//	/**
//	 * This method initializes jPanel231
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel231() {
//		if (jPanel231 == null) {
//			jPanel231 = new JPanel();
//			jPanel231.setLayout(new GridBagLayout());
//		}
//		return jPanel231;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiKihonKinInput
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiKihonKinInput() {
//		if (jTextField_MadoguchiKihonKinInput == null) {
//			jTextField_MadoguchiKihonKinInput = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_MadoguchiKihonKinInput.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiKihonKinInput.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiKihonKinInput;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiSyhosaiKinInput
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiSyhosaiKinInput() {
//		if (jTextField_MadoguchiSyousaiKinInput == null) {
//			jTextField_MadoguchiSyousaiKinInput = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_MadoguchiSyousaiKinInput.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiSyousaiKinInput.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiSyousaiKinInput;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiTsuikaKinInput
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiTsuikaKinInput() {
//		if (jTextField_MadoguchiTsuikaKinInput == null) {
//			jTextField_MadoguchiTsuikaKinInput = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_MadoguchiTsuikaKinInput.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiTsuikaKinInput.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiTsuikaKinInput;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiNingenDocKinInput
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiNingenDocKinInput() {
//		if (jTextField_MadoguchiDocKinInput == null) {
//			jTextField_MadoguchiDocKinInput = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_MadoguchiDocKinInput.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiDocKinInput.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiDocKinInput;
//	}
//
//	/**
//	 * This method initializes jTextField_hihokenjasyotouKigou
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_hihokenjasyotouKigou() {
//		if (jTextField_hihokenjasyotouKigou == null) {
//			jTextField_hihokenjasyotouKigou = new ExtendedTextField(
//					"",
//					8,
//					ImeMode.IME_ZENKAKU);
//			jTextField_hihokenjasyotouKigou.setPreferredSize(new Dimension(140, 20));
//			jTextField_hihokenjasyotouKigou.setVisible(false);
//		}
//		return jTextField_hihokenjasyotouKigou;
//	}
//
//	/**
//	 * This method initializes jTextField_hihokenjasyotouBangou
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_hihokenjasyotouBangou() {
//		if (jTextField_hihokenjasyotouBangou == null) {
//			jTextField_hihokenjasyotouBangou = new ExtendedTextField(
//					"",
//					8,
//					ImeMode.IME_ZENKAKU);
//			jTextField_hihokenjasyotouBangou.setPreferredSize(new Dimension(140, 20));
//			jTextField_hihokenjasyotouBangou.setVisible(false);
//		}
//		return jTextField_hihokenjasyotouBangou;
//	}
//
//	/**
//	 * This method initializes jTextField_MadoguchiSonota
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_MadoguchiSonota() {
//		if (jTextField_MadoguchiSonota == null) {
//			jTextField_MadoguchiSonota = new ExtendedTextField(
//					"",
//					9,
//					ImeMode.IME_OFF);
//			jTextField_MadoguchiSonota.setPreferredSize(new Dimension(80, 20));
//			jTextField_MadoguchiSonota.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_MadoguchiSonota;
//	}
//
//	/**
//	 * This method initializes jTextField_hokenjyaJougenKihon
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_hokenjyaJougenKihon() {
//		if (jTextField_hokenjyaJougenKihon == null) {
//			jTextField_hokenjyaJougenKihon = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_hokenjyaJougenKihon.setPreferredSize(new Dimension(80, 20));
//			jTextField_hokenjyaJougenKihon.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_hokenjyaJougenKihon;
//	}
//
//	/**
//	 * This method initializes jTextField_hokenjyaJougenSyosai
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_hokenjyaJougenSyosai() {
//		if (jTextField_hokenjyaJougenSyosai == null) {
//			jTextField_hokenjyaJougenSyosai = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_hokenjyaJougenSyosai.setPreferredSize(new Dimension(80, 20));
//			jTextField_hokenjyaJougenSyosai.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_hokenjyaJougenSyosai;
//	}
//
//	/**
//	 * This method initializes jTextField_hokenjyaJougenTsuika
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_hokenjyaJougenTsuika() {
//		if (jTextField_hokenjyaJougenTsuika == null) {
//			jTextField_hokenjyaJougenTsuika = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_hokenjyaJougenTsuika.setPreferredSize(new Dimension(80, 20));
//			jTextField_hokenjyaJougenTsuika.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_hokenjyaJougenTsuika;
//	}
//
//	/**
//	 * This method initializes jTextField_hokenjyaJougenTsuika
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_hokenjyaJougenNingenDoc() {
//		if (jTextField_hokenjyaJougenDoc == null) {
//			jTextField_hokenjyaJougenDoc = new ExtendedTextField(
//					"",
//					6,
//					ImeMode.IME_OFF);
//			jTextField_hokenjyaJougenDoc.setPreferredSize(new Dimension(80, 20));
//			jTextField_hokenjyaJougenDoc.setHorizontalAlignment(JTextField.RIGHT);
//		}
//		return jTextField_hokenjyaJougenDoc;
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩÉÅÉ\ÉbÉhÅEÉXÉ^Éu
//
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩÉÅÉ\ÉbÉhÅEÉXÉ^Éu
//
//	}
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩÉÅÉ\ÉbÉhÅEÉXÉ^Éu
//
//	}
//}  //  @jve:decl-index=0:visual-constraint="16,75"
