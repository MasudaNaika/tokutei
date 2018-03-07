package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedDateChooser;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFormattedControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

/**
 * 受診券入力画面
 */
public class JKojinRegisterFrame extends JFrame
	implements ActionListener,ItemListener,KeyListener,PopupMenuListener,
		FocusListener {

	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected ExtendedLabel jLabel_Title = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	private ExtendedLabel jLabel = null;
	private ExtendedLabel jLabel1 = null;
	private ExtendedLabel jLabel2 = null;
	private ExtendedLabel jLabel3 = null;
	private ExtendedLabel jLabel4 = null;
	private ExtendedLabel jLabel5 = null;
	private ExtendedLabel jLabel6 = null;
	private ExtendedLabel jLabel7 = null;
	private ExtendedLabel jLabel8 = null;
	private ExtendedLabel jLabel9 = null;
	private ExtendedLabel jLabel10 = null;
	private ExtendedLabel jLabel11 = null;
	private ExtendedLabel jLabel12 = null;
	private ExtendedLabel jLabel13 = null;
	private ExtendedLabel jLabel14 = null;
	private ExtendedLabel jLabel15 = null;
	private ExtendedLabel jLabel16 = null;
	private ExtendedLabel jLabel17 = null;
	private ExtendedLabel jLabel25 = null;
	private ExtendedLabel jLabel24 = null;
	private ExtendedLabel jLabel26 = null;
	private ExtendedLabel jLabel27 = null;
	private ExtendedLabel jLabel29 = null;
	private ExtendedLabel jLabel31 = null;
	private ExtendedLabel jLabel33 = null;
	protected ExtendedOpenTextControl jTextField_ORCAID = null;
	protected ExtendedOpenTextControl jTextField_JyushinkenID = null;
	protected ExtendedDateChooser jTextField_IssueDate = null;
	protected ExtendedDateChooser jTextField_LimitDate = null;
	protected ExtendedOpenTextControl jTextField_TorimatomeName = null;
	protected ExtendedOpenTextControl jTextField_HihokenjyaCode = null;
	protected ExtendedOpenTextControl jTextField_HihokenjyaNumber = null;
	protected ExtendedOpenTextControl jTextField_NameKana = null;
	protected ExtendedOpenTextControl jTextField_NameKanji = null;
	protected ExtendedOpenTextControl jTextField_NameTsushou = null;
	protected ExtendedOpenTextControl jTextField_Birthday = null;
	protected ExtendedTextField jTextField_sex = null;

	protected ExtendedOpenTextControl jTextField_YearOld = null;
	protected ExtendedOpenFormattedControl jTextField_ZipCode = null;
	protected ExtendedOpenTextControl jTextField_Address = null;
	protected ExtendedOpenTextControl jTextField_HomePhone = null;
	protected ExtendedOpenTextControl jTextField_CellPhone = null;
	protected ExtendedOpenTextControl jTextField_FAXNumber = null;
	protected ExtendedOpenTextControl jTextField_EMail = null;
	protected ExtendedOpenTextControl jTextField_MobileMail = null;
	protected ExtendedOpenTextControl jTextField_MadoguchiKihon = null;
	protected ExtendedOpenTextControl jTextField_MadoguchiShousai = null;
	protected ExtendedOpenTextControl jTextField_MadoguchiTsuika = null;
	protected ExtendedOpenTextControl jTextField_MadoguchiDock = null;
	protected ExtendedComboBox jComboBox_MadoguchiKihonSyubetu = null;
	protected ExtendedComboBox jComboBox_MadoguchiShousaiSyubetu = null;
	protected ExtendedComboBox jComboBox_MadoguchiTsuikaSyubetu = null;
	protected ExtendedComboBox jComboBox_MadoguchiDockSyubetu = null;
	protected ExtendedButton jButton_Register = null;
	protected ExtendedButton jButton_Call = null;
	protected ExtendedButton jButton_Clear = null;
	protected ExtendedButton jButton_ORCA = null;
//	protected ExtendedButton jButton_QR = null;
	protected ExtendedButton jButton_Print = null;
	protected JPanel jPanel_RadioPanel = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
	protected ExtendedRadioButton jRadioButton_Male = null;
	protected ExtendedRadioButton jRadioButton_Female = null;
	protected ButtonGroup groupSex = new ButtonGroup();  //  @jve:decl-index=0:
	protected JPanel jPanel_madoguchikihon_value = null;
	protected ExtendedLabel jLabel_madoguchikihon_unit = null;
	protected JPanel jPanel_madoguchishosai_value = null;
	protected JPanel jPanel_madoguchitsuika_value = null;
	protected JPanel jPanel_madoguchidock_value = null;
	protected ExtendedLabel jLabel_madoguchishosai_unit = null;
	protected ExtendedLabel jLabel_madoguchitsuika_unit = null;
	protected ExtendedLabel jLabel_madoguchidock_unit = null;
	protected JPanel jPanel3 = null;
	protected JPanel jPanel5 = null;
	protected JPanel jPanel6 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel7 = null;
	protected JPanel jPanel9 = null;
	protected JPanel jPanel4 = null;
	protected JPanel jPanel10 = null;
	protected JPanel jPanel11 = null;
	protected JPanel jPanel12 = null;
	protected JPanel jPanel20 = null;
	protected JPanel jPanelsep = null;

//	protected ExtendedLabel jLabel_requiredExample = null;
//	protected ExtendedLabel jLabel_importantExample = null;

// del s.inoue 2012/05/10
//	protected ExtendedLabel jLabel151 = null;
//	protected ExtendedLabel jLabel1511 = null;
	protected ExtendedLabel jLabel1512 = null;
	protected ExtendedLabel jLabel_guidance1 = null;
	// del s.inoue 2009/09/24
	// 場所を空けるため削除
//	protected ExtendedLabel jLabel_guidance2 = null;
	protected JPanel jPanel114 = null;
	protected JPanel jPanel113 = null;
	protected ExtendedLabel jLabel_uketsukeid = null;
	protected ExtendedLabel jLabel_Nendo = null;
	protected ExtendedLabel jLabel1513 = null;
	protected ExtendedComboBox jComboBox_HokenjyaNumber = null;
	protected ExtendedComboBox jComboBox_DaikouNumber = null;
	protected ExtendedButton jButton_HokenjaTsuika = null;
	protected ExtendedButton jButton_DaikouTsuika = null;
	protected JPanel jPanel2111 = null;
	protected JPanel jPanel2112 = null;
//	protected ExtendedButton jButton_Cancel = null;
	protected ExtendedLabel jLabel271 = null;
	protected ExtendedOpenTextControl jTextField_Uketsukeid = null;
	protected ExtendedOpenTextControl jTextField_Nendo = null;
	protected ExtendedTextField jTextField_hokenjaNumber = null;
	protected ExtendedTextField jTextField_daikouNumber = null;
	protected ExtendedOpenTextControl jTextField_KihonJougen = null;
	protected ExtendedOpenTextControl jTextField_ShousaiJougen = null;
	protected ExtendedOpenTextControl jTextField_TsuikaJyougen = null;
	protected ExtendedOpenTextControl jTextField_DockJougen = null;
	protected ExtendedOpenTextControl jTextField_KihonSyubetsuNum = null;
	protected ExtendedOpenTextControl jTextField_SyousaiSyubetsuNum = null;
	protected ExtendedOpenTextControl jTextField_TsuikaSyubetsuNum = null;
	protected ExtendedOpenTextControl jTextField_DockSyubetsuNum = null;
	protected ExtendedOpenTextControl jTextField_sonotaHutangaku = null;
	protected ExtendedLabel jLabel_madoguchikihon_unit1 = null;
	protected ExtendedLabel jLabel_madoguchikihon_unit11 = null;
	protected ExtendedLabel jLabel_madoguchikihon_unit12 = null;
	protected ExtendedLabel jLabel_madoguchikihon_unit13 = null;
	protected ExtendedLabel jLabel2711 = null;
	protected ExtendedLabel jLabel2712 = null;
	protected ExtendedLabel jLabel18 = null;
	protected ExtendedLabel jLabel19 = null;
	protected ExtendedLabel jLabel20 = null;

	protected ExtendedLabel jLabel15111 = null;
	protected JPanel jPanel_buttons = null;
//	protected ExtendedLabel jLabel181 = null;
//	protected ExtendedLabel jLabel151111 = null;
	protected ExtendedLabel jLabel331 = null;
	protected ExtendedLabel jLabel_madoguchikihon_unit131 = null;
	protected ExtendedLabel jLabel_jyusinken_format = null;
	protected ExtendedLabel jLabel_kouhubi_format = null;
	protected ExtendedLabel jLabel_yukokigen_format1 = null;
	protected ExtendedLabel jLabel_kigo_format = null;
	protected ExtendedLabel jLabel_bango_format = null;
	protected ExtendedLabel jLabel_kana_format = null;
	protected ExtendedLabel jLabel_kanji_format = null;
	protected ExtendedLabel jLabel_tsusho_format = null;
	protected ExtendedLabel jLabel_birthday_format = null;
	protected ExtendedLabel jLabel_yearold_format = null;
	protected ExtendedLabel jLabel71 = null;
	protected ExtendedLabel jLabel_sex_format = null;
	protected ExtendedLabel jLabel141 = null;
	protected ExtendedLabel jLabel711 = null;
	protected ExtendedLabel jLabel7111 = null;
	protected ExtendedLabel jLabel_post_format = null;
	protected ExtendedLabel jLabel_orcaid_format = null;
	protected ExtendedLabel jLabel_hokenjyanum_format = null;
	protected ExtendedLabel jLabel_futangaku_format = null;
	protected ExtendedLabel jLabel272 = null;
	protected ExtendedLabel jLabel_spacer = null;

	protected ExtendedLabel jLabel_address_format1 = null;
	protected ExtendedLabel jLabel51 = null;
	protected ExtendedLabel jLabel_hometel_format = null;
	protected ExtendedLabel jLabel_kigo_format1 = null;
	protected ExtendedLabel jLabel_number_format = null;
	protected JPanel jPanel8 = null;
	protected ExtendedButton jButton_CallHokenjya = null;
	// add s.inoue 2009/10/11
	protected ExtendedLabel jLabel131 = null;

	protected ExtendedLabel jLabel110 = null;
	protected ExtendedLabel jLabel123 = null;
	protected ExtendedLabel jLabel111 = null;
	protected ExtendedLabel jLabel117 = null;
	protected ExtendedLabel jLabel118 = null;
	protected ExtendedLabel jLabel119 = null;
	protected ExtendedLabel jLabel130 = null;

	protected ExtendedLabel jLabel122 = null;
	// 円
	protected ExtendedLabel jLabel200 = null;
	protected ExtendedLabel jLabel125 = null;
	protected ExtendedLabel jLabel129 = null;
	protected ExtendedLabel jLabel127 = null;
	protected ExtendedLabel jLabel128 = null;

//	private ExtendedLabel jLabelKihonNum = null;
//	private ExtendedLabel jLabelHinketuNum = null;
//	private ExtendedLabel jLabelSindenzuNum = null;
//	private ExtendedLabel jLabelGanteiNum = null;
//	private ExtendedLabel jLabelNingenDocNum = null;

	// add s.inoue 2009/10/01
	protected JPanel jPanelKihon = null;
	protected JPanel jPanelDoc = null;
	protected ButtonGroup groupHantei = new ButtonGroup();
	protected ExtendedRadioButton jRadioButton_Kihon = null;
	protected ExtendedRadioButton jRadioButton_Doc = null;

	protected ExtendedComboBox jComboBox_ItakuKubun = null;
	protected ExtendedOpenTextControl jTextField_TankaHantei = null;
	protected ExtendedOpenTextControl jTextField_KihonTanka = null;
	protected ExtendedOpenTextControl jTextField_HinketsuTanka = null;
	protected ExtendedOpenTextControl jTextField_SindenzuTanka = null;
	protected ExtendedOpenTextControl jTextField_GanteiTanka = null;
	protected ExtendedOpenTextControl jTextField_NingenDocTanka = null;
	
	// edit n.ohkubo 2015/08/01　追加　start
	protected ExtendedButton jButton_Print_tsuikakenshin = null;
	private ExtendedButton getJButton_Print_tsuikakenshin() {
		if (jButton_Print_tsuikakenshin == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Print_tsuikakenshin= new ExtendedButton(
					"IraiPrint","追加健診(O)","追加健診(ALT+O)",KeyEvent.VK_O,icon);
			jButton_Print_tsuikakenshin.addActionListener(this);
		}
		return jButton_Print_tsuikakenshin;
	}
	// edit n.ohkubo 2015/08/01　追加　end

	/**
	 * This is the default constructor
	 */
	public JKojinRegisterFrame() {
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
		// edit s.inoue 2010/04/14
//		this.setSize(new Dimension(1182, 857));
		// this.setSize(new Dimension(920, 660));
		this.setPreferredSize(ViewSettings.getFrameCommonSize());
		this.setMinimumSize(ViewSettings.getFrameCommonSize());
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
			// eidt s.inoue 2011/03/29
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
			GridBagConstraints gridBagConstraints131 = new GridBagConstraints();
			gridBagConstraints131.gridx = 2;
			gridBagConstraints131.anchor = GridBagConstraints.WEST;
			gridBagConstraints131.weightx = 1.0D;
			gridBagConstraints131.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints131.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints131.gridy = 0;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJPanel_buttons(), gridBagConstraints131);
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
			// edit s.inoue 20110328
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
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

//	/**
//	 * This method initializes jPanel_NaviArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_NaviArea() {
//		if (jPanel_NaviArea == null) {
//			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
//			gridBagConstraints64.insets = new Insets(10, 10, 0, 10);
//			gridBagConstraints64.gridy = 0;
//			gridBagConstraints64.weightx = 1.0D;
//			gridBagConstraints64.fill = GridBagConstraints.BOTH;
//			gridBagConstraints64.gridx = 0;
//			jLabel_Title = new TitleLabel("tokutei.jushinken.frame.title");
//			jPanel_NaviArea = new JPanel();
//			jPanel_NaviArea.setLayout(new GridBagLayout());
//			jPanel_NaviArea.setOpaque(false);
//			jPanel_NaviArea.add(getJPanel_TitleArea(), gridBagConstraints64);
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
//			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
//			gridBagConstraints90.gridx = 0;
//			gridBagConstraints90.anchor = GridBagConstraints.WEST;
//			gridBagConstraints90.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints90.fill = GridBagConstraints.BOTH;
//			gridBagConstraints90.gridy = 2;
//			// del s.inoue 2009/09/24
////			jLabel_guidance2 = new ExtendedLabel();
////			jLabel_guidance2.setPreferredSize(new Dimension(100, 20));
////			jLabel_guidance2.setFont(new Font("Dialog", Font.PLAIN, 14));
//			// edit s.inoue 2009/09/24
//			// jLabel_guidance2.setText("日レセ、登録済みデータ、ＱＲコードから受診者の情報を取得することができます。");
//			GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
//			gridBagConstraints89.gridx = 0;
//			gridBagConstraints89.anchor = GridBagConstraints.WEST;
//			gridBagConstraints89.fill = GridBagConstraints.BOTH;
//			gridBagConstraints89.insets = new Insets(5, 10, 0, 0);
//			gridBagConstraints89.gridy = 1;
//			jLabel_guidance1 = new ExtendedLabel();
//			jLabel_guidance1.setPreferredSize(new Dimension(100, 20));
//			jLabel_guidance1.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_guidance1.setText("受診者の情報を入力し、「登録」ボタンを押して情報を登録します。日レセ、登録済みデータから受診者情報を取得できます。");
//			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
//			gridBagConstraints65.gridx = 0;
//			gridBagConstraints65.fill = GridBagConstraints.BOTH;
//			gridBagConstraints65.weightx = 1.0D;
//			// edit s.inoue 2009/10/11
//			gridBagConstraints65.ipady = 5;
//			gridBagConstraints65.insets = new Insets(0, 0, 0, 0);
//			gridBagConstraints65.ipadx = 0;
//			gridBagConstraints65.gridy = 0;
//			jPanel_TitleArea = new JPanel();
//			jPanel_TitleArea.setLayout(new GridBagLayout());
//			jPanel_TitleArea.setName("jPanel2");
//			jPanel_TitleArea.setOpaque(false);
//			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints65);
//			jPanel_TitleArea.add(jLabel_guidance1, gridBagConstraints89);
//			// del s.inoue 2009/09/24
////			jPanel_TitleArea.add(jLabel_guidance2, gridBagConstraints90);
//		}
//		return jPanel_TitleArea;
//	}

	/**
	 * This method initializes jPanel_MainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_MainArea() {
		if (jPanel_MainArea == null) {
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new BorderLayout());
			jPanel_MainArea.setOpaque(false);
			jPanel_MainArea.add(getJPanel_DrawArea(), BorderLayout.NORTH);
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_DrawArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_DrawArea() {
		if (jPanel_DrawArea == null) {
			GridBagConstraints gridBagConstraints83 = new GridBagConstraints();
			gridBagConstraints83.gridx = 0;
			gridBagConstraints83.gridy = 2;
			gridBagConstraints83.weighty = 1.0D;
			gridBagConstraints83.gridwidth = 2;
			gridBagConstraints83.anchor = GridBagConstraints.WEST;
			gridBagConstraints83.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints83.weightx = 1.0D;
			GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
			gridBagConstraints92.fill = GridBagConstraints.BOTH;
			gridBagConstraints92.gridy = 0;
			gridBagConstraints92.weightx = 1.0D;
			gridBagConstraints92.gridwidth = 2;
			gridBagConstraints92.insets = new Insets(0, 0, 10, 0);
			gridBagConstraints92.gridx = 0;
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			gridBagConstraints79.gridx = 1;
			gridBagConstraints79.fill = GridBagConstraints.BOTH;
			gridBagConstraints79.weightx = 1.0D;
			gridBagConstraints79.gridy = 1;
			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.gridx = 0;
			// edit s.inoue 2009/10/12
			// gridBagConstraints70.fill = GridBagConstraints.BOTH;
			// gridBagConstraints70.weighty = 1.0D;
			gridBagConstraints70.fill = GridBagConstraints.WEST;
			gridBagConstraints70.gridy = 1;
			GridBagConstraints gridBagConstraints410 = new GridBagConstraints();
			gridBagConstraints410.anchor = GridBagConstraints.WEST;
			GridBagConstraints gridBagConstraints312 = new GridBagConstraints();
			gridBagConstraints312.gridx = 6;
			gridBagConstraints312.anchor = GridBagConstraints.WEST;
			gridBagConstraints312.gridy = 15;
			GridBagConstraints gridBagConstraints211 = new GridBagConstraints();
			gridBagConstraints211.gridx = 6;
			gridBagConstraints211.anchor = GridBagConstraints.WEST;
			gridBagConstraints211.gridy = 13;
			GridBagConstraints gridBagConstraints112 = new GridBagConstraints();
			gridBagConstraints112.gridx = 6;
			gridBagConstraints112.anchor = GridBagConstraints.WEST;
			gridBagConstraints112.gridy = 11;
			jLabel33 = new ExtendedLabel();
			jLabel33.setText("人間ドック");
			jLabel31 = new ExtendedLabel();
			jLabel31.setText("追加健診");
			jLabel29 = new ExtendedLabel();
			jLabel29.setText("詳細な健診");
			jLabel27 = new ExtendedLabel();
			jLabel27.setText("基本的な健診");
			jLabel27.setPreferredSize(new Dimension(80, 16));
//			jLabel26 = new ExtendedLabel();
//			jLabel26.setText("携帯E-Mail");
//			jLabel24 = new ExtendedLabel();
//			jLabel24.setText("E-Mail");
//			jLabel25 = new ExtendedLabel();
//			jLabel25.setText("FAX番号");
//			jLabel17 = new ExtendedLabel();
//			jLabel17.setText("携帯電話番号");
//			jLabel16 = new ExtendedLabel();
//			jLabel16.setText("自宅電話番号　　 ");
			jLabel15 = new ExtendedLabel();
			jLabel15.setText("住所");
			// add s.inoue 2012/05/10
			jLabel15.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel14 = new ExtendedLabel();
			jLabel14.setText("郵便番号");
			// add s.inoue 2012/05/10
			jLabel14.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel13 = new ExtendedLabel();
			jLabel13.setText("男女区分");
			// add s.inoue 2012/05/10
			jLabel13.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel12 = new ExtendedLabel();
			jLabel12.setText("生年月日(年度年齢)");
			// add s.inoue 2012/05/10
			jLabel12.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel11 = new ExtendedLabel();
			jLabel11.setText("氏名（通称）");
			jLabel10 = new ExtendedLabel();
			jLabel10.setText("氏名（漢字）");
			jLabel9 = new ExtendedLabel();
			jLabel9.setText("氏名（カナ）");
			jLabel9.setForeground(ViewSettings.getRequiedItemFrColor());	// edit n.ohkubo 2014/10/01　追加
			jLabel8 = new ExtendedLabel();
			jLabel8.setText("被保険者証等番号");
			jLabel8.setForeground(ViewSettings.getRequiedItemFrColor());	// edit n.ohkubo 2015/03/01　追加
			jLabel7 = new ExtendedLabel();
			jLabel7.setText("被保険者証等記号");
			jLabel6 = new ExtendedLabel();
			jLabel6.setHtmlText("契約取りまとめ<br>機関名");
			jLabel5 = new ExtendedLabel();
			jLabel5.setText("支払代行機関");
			jLabel4 = new ExtendedLabel();
			jLabel4.setText("保険者");
			// add s.inoue 2012/05/10
			jLabel4.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel3 = new ExtendedLabel();
			jLabel3.setText("有効期限");
			jLabel2 = new ExtendedLabel();
			jLabel2.setText("交付日");
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("受診券整理番号");
			// del s.inoue 2012/05/29
			// jLabel1.setForeground(Color.magenta);

			jLabel = new ExtendedLabel();
			jLabel.setText("患者ID（日レセ連携）");
			jLabel.setForeground(Color.blue);

			jLabel.setPreferredSize(new Dimension(130, 16));
			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new GridBagLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");
			jPanel_DrawArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_DrawArea.setOpaque(false);
			jPanel_DrawArea.add(getJPanel4(), gridBagConstraints70);
			jPanel_DrawArea.add(getJPanel10(), gridBagConstraints79);
			jPanel_DrawArea.add(getJPanel12(), gridBagConstraints92);
			jPanel_DrawArea.add(getJPanel11(), gridBagConstraints83);
		}
		return jPanel_DrawArea;
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
	}
	@Override
	public void itemStateChanged(ItemEvent e)
	{
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	private ExtendedOpenTextControl getJTextField_ORCAID() {
		if (jTextField_ORCAID == null) {
			jTextField_ORCAID = new ExtendedOpenTextControl("",20, ImeMode.IME_OFF, false);
			jTextField_ORCAID.setPreferredSize(new Dimension(150, 20));
			jTextField_ORCAID.addActionListener(this);
		}
		return jTextField_ORCAID;
	}

	private ExtendedOpenTextControl getJTextField_JyushinkenID() {
		if (jTextField_JyushinkenID == null) {
			jTextField_JyushinkenID = new ExtendedOpenTextControl("",11,ImeMode.IME_OFF);
			jTextField_JyushinkenID.setPreferredSize(new Dimension(150, 20));
			jTextField_JyushinkenID.addActionListener(this);
		}
		return jTextField_JyushinkenID;
	}

	private ExtendedDateChooser getJTextField_IssueDate() {
		if (jTextField_IssueDate == null) {
			jTextField_IssueDate = new ExtendedDateChooser("", 8, ImeMode.IME_OFF);
			// edit s.inoue 2010/01/15
			jTextField_IssueDate.setPreferredSize(new Dimension(150, 20));
//			jTextField_IssueDate.addActionListener(this);
		}
		return jTextField_IssueDate;
	}

	private ExtendedDateChooser getJTextField_LimitDate() {
		if (jTextField_LimitDate == null) {
			jTextField_LimitDate = new ExtendedDateChooser("", 8, ImeMode.IME_OFF);
			// edit s.inoue 2010/01/15
			jTextField_LimitDate.setPreferredSize(new Dimension(150, 20));
//			jTextField_LimitDate.addActionListener(this);
		}
		return jTextField_LimitDate;
	}


	private ExtendedOpenTextControl getJTextField_TorimatomeName() {
		if (jTextField_TorimatomeName == null) {
			jTextField_TorimatomeName = new ExtendedOpenTextControl(ImeMode.IME_ZENKAKU);
			jTextField_TorimatomeName.setPreferredSize(new Dimension(280, 20));
			jTextField_TorimatomeName.addActionListener(this);
		}
		return jTextField_TorimatomeName;
	}

	private ExtendedOpenTextControl getJTextField_HihokenjyaCode() {
		if (jTextField_HihokenjyaCode == null) {
			jTextField_HihokenjyaCode = new ExtendedOpenTextControl(ImeMode.IME_ZENKAKU);
			jTextField_HihokenjyaCode.setPreferredSize(new Dimension(165, 20));
			jTextField_HihokenjyaCode.addActionListener(this);
		}
		return jTextField_HihokenjyaCode;
	}

	private ExtendedOpenTextControl getJTextField_HihokenjyaNumber() {
		if (jTextField_HihokenjyaNumber == null) {
			jTextField_HihokenjyaNumber = new ExtendedOpenTextControl(ImeMode.IME_ZENKAKU);
			jTextField_HihokenjyaNumber.setPreferredSize(new Dimension(165, 20));
			jTextField_HihokenjyaNumber.addActionListener(this);
			// add s.inoue 2012/05/14
			jTextField_HihokenjyaNumber.setBackground(ViewSettings.getRequiedItemBgColor());

		}
		return jTextField_HihokenjyaNumber;
	}

	private ExtendedOpenTextControl getJTextField_NameKana() {
		if (jTextField_NameKana == null) {
			jTextField_NameKana = new ExtendedOpenTextControl(ImeMode.IME_ZENKAKU_KANA);
			// edit s.inoue 2010/01/15
			jTextField_NameKana.setPreferredSize(new Dimension(150, 20));
			// add ver2 s.inoue 2009/05/07
			jTextField_NameKana.addFocusListener(this);
			jTextField_NameKana.addActionListener(this);
		}
		return jTextField_NameKana;
	}

	private ExtendedOpenTextControl getJTextField_NameKanji() {
		if (jTextField_NameKanji == null) {
			jTextField_NameKanji = new ExtendedOpenTextControl(ImeMode.IME_ZENKAKU);
			jTextField_NameKanji.setPreferredSize(new Dimension(165, 20));
			jTextField_NameKanji.addActionListener(this);
		}
		return jTextField_NameKanji;
	}

	private ExtendedOpenTextControl getJTextField_NameTsushou() {
		if (jTextField_NameTsushou == null) {
			jTextField_NameTsushou = new ExtendedOpenTextControl("",50,ImeMode.IME_ZENKAKU);
			jTextField_NameTsushou.setPreferredSize(new Dimension(165, 20));
			jTextField_NameTsushou.addActionListener(this);
		}
		return jTextField_NameTsushou;
	}

	private ExtendedOpenTextControl getJTextField_Birthday() {
		if (jTextField_Birthday == null) {
			jTextField_Birthday = new ExtendedOpenTextControl("",8, ImeMode.IME_OFF);
			jTextField_Birthday.setPreferredSize(new Dimension(120, 20));
			jTextField_Birthday.addActionListener(this);
			jTextField_Birthday.addFocusListener(this);
		}
		return jTextField_Birthday;
	}
// eidt s.inoue 2012/06/27 datechooser廃止
//	/**
//	 * This method initializes jTextField_Birthday
//	 *
//	 * @return javax.swing.JTextField
//	 */
//	private ExtendedDateChooser getJTextField_Birthday() {
//		if (jTextField_Birthday == null) {
//			// eidt s.inoue 2012/02/27
//			// jTextField_Birthday = new ExtendedDateChooser("",8,ImeMode.IME_OFF,true);
//			jTextField_Birthday = new ExtendedDateChooser(ImeMode.IME_OFF);
//
//			jTextField_Birthday.setPreferredSize(new Dimension(120, 20));
//			// add s.inoue 2012/06/26
//			jTextField_Birthday.addFocusListener(this);
////			jTextField_Birthday.addPropertyChangeListener(new PropertyChangeListener() {
////				@Override
////				public void propertyChange(PropertyChangeEvent propertychangeevent) {
////					System.out.println(propertychangeevent.getNewValue());
////				}
////			});
//
//		}
//		return jTextField_Birthday;
//	}

	// add ver2 s.inoue 2009/07/29
	private ExtendedOpenTextControl getJTextField_YearOld() {
		if (jTextField_YearOld == null) {
			jTextField_YearOld = new ExtendedOpenTextControl();
			jTextField_YearOld.setPreferredSize(new Dimension(50, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_YearOld.setEditable(false);
			jTextField_YearOld.setEnabled(false);
			jTextField_YearOld.setFocusable(false);
		}
		return jTextField_YearOld;
	}
	private ExtendedOpenFormattedControl getJTextField_ZipCode() {
		if (jTextField_ZipCode == null) {
			jTextField_ZipCode = new ExtendedOpenFormattedControl(ImeMode.IME_OFF);
			jTextField_ZipCode.setPostCodeFormat();
			jTextField_ZipCode.setPreferredSize(new Dimension(80, 20));
			jTextField_ZipCode.addActionListener(this);
			jTextField_ZipCode.addFocusListener(this);
		}
		return jTextField_ZipCode;
	}

	private ExtendedOpenTextControl getJTextField_Address() {
		if (jTextField_Address == null) {
			jTextField_Address = new ExtendedOpenTextControl(ImeMode.IME_ZENKAKU);
			jTextField_Address.setPreferredSize(new Dimension(300, 20));
			jTextField_Address.addActionListener(this);
		}
		return jTextField_Address;
	}

	private ExtendedOpenTextControl getJTextField_HomePhone() {
		if (jTextField_HomePhone == null) {
			// eidt s.inoue 2012/11/20
			jTextField_HomePhone = new ExtendedOpenTextControl(11,ImeMode.IME_OFF);
			jTextField_HomePhone.setPreferredSize(new Dimension(80, 20));
			jTextField_HomePhone.addActionListener(this);
		}
		return jTextField_HomePhone;
	}

	private ExtendedOpenTextControl getJTextField_CellPhone() {
		if (jTextField_CellPhone == null) {
			// eidt s.inoue 2012/11/20
			jTextField_CellPhone = new ExtendedOpenTextControl(11,ImeMode.IME_OFF);
			jTextField_CellPhone.setPreferredSize(new Dimension(80, 20));
			jTextField_CellPhone.addActionListener(this);
		}
		return jTextField_CellPhone;
	}

	private ExtendedOpenTextControl getJTextField_FAXNumber() {
		if (jTextField_FAXNumber == null) {
			// eidt s.inoue 2012/11/20
			jTextField_FAXNumber = new ExtendedOpenTextControl(11,ImeMode.IME_OFF);
			jTextField_FAXNumber.setPreferredSize(new Dimension(80, 20));
			jTextField_FAXNumber.addActionListener(this);
		}
		return jTextField_FAXNumber;
	}

	private ExtendedOpenTextControl getJTextField_EMail() {
		if (jTextField_EMail == null) {
			jTextField_EMail = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			jTextField_EMail.setPreferredSize(new Dimension(100, 20));
			jTextField_EMail.addActionListener(this);
		}
		return jTextField_EMail;
	}

	private ExtendedOpenTextControl getJTextField_MobileMail() {
		if (jTextField_MobileMail == null) {
			jTextField_MobileMail = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			jTextField_MobileMail.setPreferredSize(new Dimension(100, 20));
			jTextField_MobileMail.addActionListener(this);
		}
		return jTextField_MobileMail;
	}

	/**
	 * This method initializes jComboBox_MadoguchiKihonSyubetu
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_MadoguchiKihonSyubetu() {
		if (jComboBox_MadoguchiKihonSyubetu == null) {
			jComboBox_MadoguchiKihonSyubetu = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_MadoguchiKihonSyubetu.setPreferredSize(new Dimension(80, 20));
			jComboBox_MadoguchiKihonSyubetu.addItemListener(this);
//			jComboBox_MadoguchiKihonSyubetu.addKeyListener(this);
		}
		return jComboBox_MadoguchiKihonSyubetu;
	}

	/**
	 * This method initializes jComboBox_MadoguchiShousaiSyubetu
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_MadoguchiShousaiSyubetu() {
		if (jComboBox_MadoguchiShousaiSyubetu == null) {
			jComboBox_MadoguchiShousaiSyubetu = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_MadoguchiShousaiSyubetu.setPreferredSize(new Dimension(80, 20));
			jComboBox_MadoguchiShousaiSyubetu.addItemListener(this);
//			jComboBox_MadoguchiShousaiSyubetu.addKeyListener(this);
		}
		return jComboBox_MadoguchiShousaiSyubetu;
	}

	/**
	 * This method initializes jComboBox_MadoguchiTsuikaSyubetu
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_MadoguchiTsuikaSyubetu() {
		if (jComboBox_MadoguchiTsuikaSyubetu == null) {
			jComboBox_MadoguchiTsuikaSyubetu = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_MadoguchiTsuikaSyubetu.setPreferredSize(new Dimension(80, 20));
			jComboBox_MadoguchiTsuikaSyubetu.addItemListener(this);
//			jComboBox_MadoguchiTsuikaSyubetu.addKeyListener(this);
		}
		return jComboBox_MadoguchiTsuikaSyubetu;
	}

	/**
	 * This method initializes jComboBox_MadoguchiDockSyubetu
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_MadoguchiDockSyubetu() {
		if (jComboBox_MadoguchiDockSyubetu == null) {
			jComboBox_MadoguchiDockSyubetu = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_MadoguchiDockSyubetu.setPreferredSize(new Dimension(80, 20));
			jComboBox_MadoguchiDockSyubetu.addItemListener(this);
//			jComboBox_MadoguchiDockSyubetu.addKeyListener(this);
		}
		return jComboBox_MadoguchiDockSyubetu;
	}

	private ExtendedOpenTextControl getJTextField_MadoguchiKihon() {

		if (jTextField_MadoguchiKihon == null) {
			jTextField_MadoguchiKihon = new ExtendedOpenTextControl("", 6, ImeMode.IME_OFF);
			jTextField_MadoguchiKihon.setPreferredSize(new Dimension(70, 20));
			jTextField_MadoguchiKihon.addActionListener(this);
			// eidt s.inoue 2011/12/13
			// jTextField_MadoguchiKihon.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_MadoguchiKihon.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_MadoguchiKihon;
	}

	private ExtendedOpenTextControl getJTextField_MadoguchiShousai() {
		if (jTextField_MadoguchiShousai == null) {
			jTextField_MadoguchiShousai = new ExtendedOpenTextControl("", 6, ImeMode.IME_OFF);
			jTextField_MadoguchiShousai.setPreferredSize(new Dimension(70, 20));
			jTextField_MadoguchiShousai.addActionListener(this);
			// eidt s.inoue 2011/12/13
			// jTextField_MadoguchiShousai.setHorizontalAlignment(JLabel.RIGHT);
			jTextField_MadoguchiShousai.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_MadoguchiShousai;
	}

	private ExtendedOpenTextControl getJTextField_MadoguchiTsuika() {
		if (jTextField_MadoguchiTsuika == null) {
			jTextField_MadoguchiTsuika = new ExtendedOpenTextControl("", 6, ImeMode.IME_OFF);
			jTextField_MadoguchiTsuika.setPreferredSize(new Dimension(70, 20));
			jTextField_MadoguchiTsuika.addActionListener(this);
			// eidt s.inoue 2011/12/13
			// jTextField_MadoguchiTsuika.setHorizontalAlignment(JLabel.RIGHT);
			jTextField_MadoguchiTsuika.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_MadoguchiTsuika;
	}

	private ExtendedOpenTextControl getJTextField_MadoguchiDock() {
		if (jTextField_MadoguchiDock == null) {
			jTextField_MadoguchiDock = new ExtendedOpenTextControl("", 6, ImeMode.IME_OFF);
			jTextField_MadoguchiDock.setPreferredSize(new Dimension(70, 20));
			jTextField_MadoguchiDock.addActionListener(this);
			// eidt s.inoue 2011/12/13
			// jTextField_MadoguchiDock.setHorizontalAlignment(JLabel.RIGHT);
			jTextField_MadoguchiDock.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_MadoguchiDock;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			// edit s.inoue 20110328
//			jButton_Register = new ExtendedButton();
//			jButton_Register.setText("登録(F12)");
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
	 * This method initializes jButton_Call
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Call() {
		if (jButton_Call == null) {
			// edit s.inoue
//			jButton_Call = new ExtendedButton();
//			jButton_Call.setText("データ呼出(F6)");
//			jButton_Call.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Jusin_Call);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Call= new ExtendedButton(
					"uketukeCall","データ呼出(J)","データ呼出(ALT+J)",KeyEvent.VK_J,icon);
			jButton_Call.addActionListener(this);

		}
		return jButton_Call;
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
	 * This method initializes jButton_ORCA
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_ORCA() {
		if (jButton_ORCA == null) {
			// eidt s.inoue 2011/04/06
//			jButton_ORCA = new ExtendedButton();
//			jButton_ORCA.setText("日レセ読込(N)");
//			jButton_ORCA.addActionListener(this);
//			jButton_ORCA.setMnemonic(KeyEvent.VK_N);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Jusin_Orca);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_ORCA= new ExtendedButton(
					"uketukeCall","日レセ読込(N)","日レセ読込(ALT+N)",KeyEvent.VK_N,icon);
			jButton_ORCA.addActionListener(this);
		}
		return jButton_ORCA;
	}

//	/**
//	 * This method initializes jButton_QR
//	 *
//	 * @return javax.swing.EventHandleButton
//	 */
//	private ExtendedButton getJButton_QR() {
//		if (jButton_QR == null) {
//			// eidt s.inoue 2011/03/29
////			jButton_QR = new ExtendedButton();
////			jButton_QR.setText("QR");
////			jButton_QR.addActionListener(this);
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.IcoCommonRegister);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			jButton_QR= new ExtendedButton(
//					"QR","QR(Q)","QR(Q)",KeyEvent.VK_Q,icon);
//		}
//		return jButton_QR;
//	}

	/**
	 * This method initializes jButton_Print
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Print() {
		if (jButton_Print == null) {
			// edit s.inoue 20110328
//			jButton_Print = new ExtendedButton();
//			jButton_Print.setText("入力票印刷(F5)");
//			jButton_Print.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Print= new ExtendedButton(
					"IraiPrint","入力票印刷(P)","入力票印刷(ALT+P)",KeyEvent.VK_P,icon);
			jButton_Print.addActionListener(this);
		}
		return jButton_Print;
	}

	/**
	 * This method initializes jPanel_RadioPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_RadioPanel() {
		if (jPanel_RadioPanel == null) {
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.gridy = 0;
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.weightx = 1.0D;
			gridBagConstraints63.fill = GridBagConstraints.WEST;
			gridBagConstraints63.gridx = 2;
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridy = 0;
			gridBagConstraints62.gridx = 1;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridx = 0;
			gridBagConstraints32.gridy = 0;
			gridBagConstraints32.ipadx = 5;
			gridBagConstraints32.insets = new Insets(0, 10, 0, 0);
			jPanel_RadioPanel = new JPanel();
			jPanel_RadioPanel.setLayout(new GridBagLayout());
			jPanel_RadioPanel.setOpaque(false);
			jPanel_RadioPanel.add(getJTextField_sex(), gridBagConstraints32);
			jPanel_RadioPanel.add(getJPanel(), gridBagConstraints62);
			jPanel_RadioPanel.add(getJPanel1(), gridBagConstraints63);
		}
		return jPanel_RadioPanel;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.X_AXIS));
			jPanel.add(getJRadioButton_Male(), null);
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
			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.gridy = 0;
			gridBagConstraints91.fill = GridBagConstraints.WEST;
			gridBagConstraints91.anchor = GridBagConstraints.WEST;
			gridBagConstraints91.weightx = 1.0D;
			gridBagConstraints91.gridx = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setOpaque(true);
			jPanel1.setPreferredSize(new Dimension(73, 20));
			jPanel1.add(getJRadioButton_Female(), gridBagConstraints91);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton_Male
	 *
	 * @return javax.swing.JRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Male() {
		if (jRadioButton_Male == null) {
			jRadioButton_Male = new ExtendedRadioButton();
			jRadioButton_Male.setText("1：男性");
			jRadioButton_Male.setPreferredSize(new Dimension(73, 20));
			// del s.inoue 2010/05/10
//			jRadioButton_Male.addKeyListener(this);
			jRadioButton_Male.addItemListener(this);
			jRadioButton_Male.setFocusable(false);
			// add s.inoue 2012/05/10
			// jRadioButton_Male.setForeground(ViewSettings.getRequiedItemFrColor());
			groupSex.add(jRadioButton_Male);

		}
		return jRadioButton_Male;
	}

	/**
	 * This method initializes jRadioButton_Female
	 *
	 * @return javax.swing.EventHandleRadioButton
	 */
	private ExtendedRadioButton getJRadioButton_Female() {
		if (jRadioButton_Female == null) {
			jRadioButton_Female = new ExtendedRadioButton();
			jRadioButton_Female.setText("2：女性");
			jRadioButton_Female.setPreferredSize(new Dimension(73, 20));
			jRadioButton_Female.addItemListener(this);
			jRadioButton_Female.setFocusable(false);
			// add s.inoue 2012/05/10
			// jRadioButton_Female.setForeground(ViewSettings.getRequiedItemFrColor());

			groupSex.add(jRadioButton_Female);
			// del s.inoue 2010/05/10
			// jRadioButton_Female.addKeyListener(this);
		}
		return jRadioButton_Female;
	}

	/**
	 * This method initializes jTextField_sex
	 *
	 * @return EventHandleTextField
	 */
	private ExtendedTextField getJTextField_sex() {
		if (jTextField_sex == null) {
			// jTextField_sex = new ExtendedOpenFormattedFloatControl("",1, ImeMode.IME_OFF);
			jTextField_sex = new ExtendedTextField("",1,ImeMode.IME_OFF);

			jTextField_sex.addActionListener(this);
			jTextField_sex.addKeyListener(this);
			jTextField_sex.setColumns(1);
		}
		return jTextField_sex;
	}

	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints153 = new GridBagConstraints();
			gridBagConstraints153.gridx = 7;
			gridBagConstraints153.gridy = 0;
			jLabel_spacer = new ExtendedLabel();
			jLabel_spacer.setPreferredSize(new Dimension(10, 10));
			jLabel_spacer.setText("");
			GridBagConstraints gridBagConstraints152 = new GridBagConstraints();
			gridBagConstraints152.gridx = 5;
			gridBagConstraints152.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints152.anchor = GridBagConstraints.WEST;
			gridBagConstraints152.gridwidth = 3;
			gridBagConstraints152.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints152.gridy = 6;
			jLabel272 = new ExtendedLabel();
			jLabel272.setText("（半角数字9桁以下）");
			GridBagConstraints gridBagConstraints151 = new GridBagConstraints();
			gridBagConstraints151.gridx = 2;
			gridBagConstraints151.anchor = GridBagConstraints.WEST;
			gridBagConstraints151.gridwidth = 4;
			gridBagConstraints151.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints151.gridy = 0;
			jLabel_futangaku_format = new ExtendedLabel();
			jLabel_futangaku_format.setText("（金額は半角数字6桁以内、割合は小数点1桁以内）");
			GridBagConstraints gridBagConstraints148 = new GridBagConstraints();
			gridBagConstraints148.gridx = 0;
			gridBagConstraints148.anchor = GridBagConstraints.WEST;
			gridBagConstraints148.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints148.gridy = 0;
			jLabel7111 = new ExtendedLabel(null, Font.BOLD);
			jLabel7111.setText("負担金額・割合");
			GridBagConstraints gridBagConstraints136 = new GridBagConstraints();
			gridBagConstraints136.gridx = 4;
			gridBagConstraints136.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints136.gridy = 6;
			jLabel_madoguchikihon_unit131 = new ExtendedLabel();
			jLabel_madoguchikihon_unit131.setPreferredSize(new Dimension(20, 16));
			jLabel_madoguchikihon_unit131.setText("\u5186");
			GridBagConstraints gridBagConstraints135 = new GridBagConstraints();
			gridBagConstraints135.gridy = 6;
			gridBagConstraints135.weightx = 1.0;
			gridBagConstraints135.insets = new Insets(10, 10, 0, 0);
			gridBagConstraints135.anchor = GridBagConstraints.WEST;
			gridBagConstraints135.gridx = 3;
			GridBagConstraints gridBagConstraints134 = new GridBagConstraints();
			gridBagConstraints134.gridx = 0;
			gridBagConstraints134.anchor = GridBagConstraints.WEST;
			gridBagConstraints134.gridwidth = 3;
			gridBagConstraints134.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints134.gridy = 6;
			jLabel331 = new ExtendedLabel();
			jLabel331.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel331.setText("その他の健診による負担金額");
			GridBagConstraints gridBagConstraints120 = new GridBagConstraints();
			gridBagConstraints120.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints120.gridy = 5;
			gridBagConstraints120.weightx = 1.0;
			gridBagConstraints120.gridx = 1;
			GridBagConstraints gridBagConstraints119 = new GridBagConstraints();
			gridBagConstraints119.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints119.gridy = 4;
			gridBagConstraints119.weightx = 1.0;
			gridBagConstraints119.gridx = 1;
			GridBagConstraints gridBagConstraints118 = new GridBagConstraints();
			gridBagConstraints118.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints118.gridy = 3;
			gridBagConstraints118.weightx = 1.0;
			gridBagConstraints118.gridx = 1;
			GridBagConstraints gridBagConstraints117 = new GridBagConstraints();
			gridBagConstraints117.gridy = 2;
			gridBagConstraints117.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints117.gridx = 1;
			GridBagConstraints gridBagConstraints116 = new GridBagConstraints();
			gridBagConstraints116.gridx = 5;
			gridBagConstraints116.gridwidth = 2;
			gridBagConstraints116.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints116.anchor = GridBagConstraints.WEST;
			gridBagConstraints116.gridy = 1;
			jLabel2712 = new ExtendedLabel();
			jLabel2712.setText("保険者負担上限額");
			GridBagConstraints gridBagConstraints115 = new GridBagConstraints();
			gridBagConstraints115.gridx = 3;
			gridBagConstraints115.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints115.anchor = GridBagConstraints.CENTER;
			gridBagConstraints115.gridwidth = 2;
			gridBagConstraints115.gridy = 1;
			jLabel2711 = new ExtendedLabel();
			jLabel2711.setText("金額または割合");
			GridBagConstraints gridBagConstraints114 = new GridBagConstraints();
			gridBagConstraints114.gridy = 5;
			gridBagConstraints114.anchor = GridBagConstraints.WEST;
			gridBagConstraints114.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints114.gridx = 5;
			GridBagConstraints gridBagConstraints113 = new GridBagConstraints();
			gridBagConstraints113.gridy = 4;
			gridBagConstraints113.anchor = GridBagConstraints.WEST;
			gridBagConstraints113.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints113.gridx = 5;
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.gridx = 6;
			gridBagConstraints111.anchor = GridBagConstraints.WEST;
			gridBagConstraints111.gridy = 5;
			jLabel_madoguchikihon_unit13 = new ExtendedLabel();
			jLabel_madoguchikihon_unit13.setPreferredSize(new Dimension(20, 16));
			jLabel_madoguchikihon_unit13.setText("円");
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			gridBagConstraints110.gridx = 6;
			gridBagConstraints110.anchor = GridBagConstraints.WEST;
			gridBagConstraints110.gridy = 4;
			jLabel_madoguchikihon_unit12 = new ExtendedLabel();
			jLabel_madoguchikihon_unit12.setPreferredSize(new Dimension(20, 16));
			jLabel_madoguchikihon_unit12.setText("円");
			GridBagConstraints gridBagConstraints109 = new GridBagConstraints();
			gridBagConstraints109.gridx = 6;
			gridBagConstraints109.anchor = GridBagConstraints.WEST;
			gridBagConstraints109.gridy = 3;
			jLabel_madoguchikihon_unit11 = new ExtendedLabel();
			jLabel_madoguchikihon_unit11.setPreferredSize(new Dimension(20, 16));
			jLabel_madoguchikihon_unit11.setText("円");
			GridBagConstraints gridBagConstraints108 = new GridBagConstraints();
			gridBagConstraints108.gridy = 3;
			gridBagConstraints108.anchor = GridBagConstraints.WEST;
			gridBagConstraints108.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints108.gridx = 5;
			GridBagConstraints gridBagConstraints107 = new GridBagConstraints();
			gridBagConstraints107.gridx = 6;
			gridBagConstraints107.anchor = GridBagConstraints.WEST;
			gridBagConstraints107.gridy = 2;
			jLabel_madoguchikihon_unit1 = new ExtendedLabel();
			jLabel_madoguchikihon_unit1.setPreferredSize(new Dimension(20, 16));
			jLabel_madoguchikihon_unit1.setText("円");
			GridBagConstraints gridBagConstraints106 = new GridBagConstraints();
			gridBagConstraints106.gridy = 2;
			gridBagConstraints106.anchor = GridBagConstraints.WEST;
			gridBagConstraints106.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints106.gridx = 5;
			GridBagConstraints gridBagConstraints103 = new GridBagConstraints();
			gridBagConstraints103.gridx = 1;
			gridBagConstraints103.anchor = GridBagConstraints.WEST;
			gridBagConstraints103.gridwidth = 2;
			gridBagConstraints103.gridy = 1;
			jLabel271 = new ExtendedLabel();
			jLabel271.setText("受診者の窓口負担");

			// add ver2 s.inoue 2009/07/29 年齢追加 gridx4→5
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.anchor = GridBagConstraints.WEST;
			gridBagConstraints59.gridx = 4;
			gridBagConstraints59.gridy = 2;
			gridBagConstraints59.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.gridx = 4;
			gridBagConstraints61.gridy = 3;
			gridBagConstraints61.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.anchor = GridBagConstraints.WEST;
			gridBagConstraints67.gridx = 4;
			gridBagConstraints67.gridy = 4;
			gridBagConstraints67.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.anchor = GridBagConstraints.WEST;
			gridBagConstraints69.gridx = 4;
			gridBagConstraints69.gridy = 5;
			gridBagConstraints69.fill = GridBagConstraints.VERTICAL;
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.gridy = 5;
			gridBagConstraints68.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints68.anchor = GridBagConstraints.WEST;
			gridBagConstraints68.gridx = 3;
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.gridy = 4;
			gridBagConstraints66.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints66.anchor = GridBagConstraints.WEST;
			gridBagConstraints66.gridx = 3;
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.anchor = GridBagConstraints.CENTER;
			gridBagConstraints57.gridy = 5;
			gridBagConstraints57.gridx = 2;
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.anchor = GridBagConstraints.CENTER;
			gridBagConstraints56.gridy = 4;
			gridBagConstraints56.gridx = 2;
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.anchor = GridBagConstraints.WEST;
			gridBagConstraints50.gridy = 4;
			gridBagConstraints50.gridx = 0;
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			gridBagConstraints60.anchor = GridBagConstraints.WEST;
			gridBagConstraints60.gridx = 3;
			gridBagConstraints60.gridy = 3;
			gridBagConstraints60.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.anchor = GridBagConstraints.CENTER;
			gridBagConstraints55.gridy = 3;
			gridBagConstraints55.gridx = 2;
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.gridy = 2;
			gridBagConstraints58.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints58.anchor = GridBagConstraints.WEST;
			gridBagConstraints58.gridx = 3;
			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridy = 2;
			gridBagConstraints54.anchor = GridBagConstraints.CENTER;
			gridBagConstraints54.gridx = 2;
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.anchor = GridBagConstraints.WEST;
			gridBagConstraints52.gridy = 5;
			gridBagConstraints52.gridx = 0;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.anchor = GridBagConstraints.WEST;
			gridBagConstraints48.gridy = 3;
			gridBagConstraints48.gridx = 0;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridy = 2;
			gridBagConstraints46.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints46.weightx = 1.0D;
			gridBagConstraints46.gridx = 0;

			jLabel_madoguchikihon_unit = new ExtendedLabel();
			jLabel_madoguchikihon_unit.setText("");
			jLabel_madoguchikihon_unit.setPreferredSize(new Dimension(20, 16));

			jLabel_madoguchishosai_unit = new ExtendedLabel();
			jLabel_madoguchishosai_unit.setText("");
			jLabel_madoguchishosai_unit.setPreferredSize(new Dimension(20, 16));

			jLabel_madoguchitsuika_unit = new ExtendedLabel();
			jLabel_madoguchitsuika_unit.setText("");
			jLabel_madoguchitsuika_unit.setPreferredSize(new Dimension(20, 16));

			jLabel_madoguchidock_unit = new ExtendedLabel();
			jLabel_madoguchidock_unit.setText("");
			jLabel_madoguchidock_unit.setPreferredSize(new Dimension(20, 16));

			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel3.setOpaque(false);
			jPanel3.add(jLabel27, gridBagConstraints46);
			jPanel3.add(jLabel29, gridBagConstraints48);
			jPanel3.add(jLabel33, gridBagConstraints52);
			jPanel3.add(getJComboBox_MadoguchiKihonSyubetu(), gridBagConstraints54);
			jPanel3.add(getJTextField_MadoguchiKihon(), gridBagConstraints58);
			jPanel3.add(getJComboBox_MadoguchiShousaiSyubetu(), gridBagConstraints55);
			jPanel3.add(getJTextField_MadoguchiShousai(), gridBagConstraints60);
			jPanel3.add(jLabel31, gridBagConstraints50);
			jPanel3.add(getJComboBox_MadoguchiTsuikaSyubetu(), gridBagConstraints56);
			jPanel3.add(getJComboBox_MadoguchiDockSyubetu(), gridBagConstraints57);
			jPanel3.add(getJTextField_MadoguchiTsuika(), gridBagConstraints66);
			jPanel3.add(getJTextField_MadoguchiDock(), gridBagConstraints68);
			jPanel3.add(jLabel_madoguchidock_unit, gridBagConstraints69);
			jPanel3.add(jLabel_madoguchitsuika_unit, gridBagConstraints67);
			jPanel3.add(jLabel_madoguchishosai_unit, gridBagConstraints61);
			jPanel3.add(jLabel_madoguchikihon_unit, gridBagConstraints59);
			jPanel3.add(jLabel271, gridBagConstraints103);
			jPanel3.add(getJTextField_MadoguchiKihon1(), gridBagConstraints106);
			jPanel3.add(jLabel_madoguchikihon_unit1, gridBagConstraints107);
			jPanel3.add(getJTextField_ShousaiJougen(), gridBagConstraints108);
			jPanel3.add(jLabel_madoguchikihon_unit11, gridBagConstraints109);
			jPanel3.add(jLabel_madoguchikihon_unit12, gridBagConstraints110);
			jPanel3.add(jLabel_madoguchikihon_unit13, gridBagConstraints111);
			jPanel3.add(getJTextField_TsuikaJyougen(), gridBagConstraints113);
			jPanel3.add(getJTextField_DockJougen(), gridBagConstraints114);
			jPanel3.add(jLabel2711, gridBagConstraints115);
			jPanel3.add(jLabel2712, gridBagConstraints116);
			jPanel3.add(getJTextField_KihonSyubetsuNum(), gridBagConstraints117);
			jPanel3.add(getJTextField_SyousaiSyubetsuNum(), gridBagConstraints118);
			jPanel3.add(getJTextField_KihonTsuikaNum(), gridBagConstraints119);
			jPanel3.add(getJTextField_DockSyubetsuNum(), gridBagConstraints120);
			jPanel3.add(jLabel331, gridBagConstraints134);
			jPanel3.add(getJTextField_sonotaHutangaku(), gridBagConstraints135);
			jPanel3.add(jLabel_madoguchikihon_unit131, gridBagConstraints136);
			jPanel3.add(jLabel7111, gridBagConstraints148);
			jPanel3.add(jLabel_futangaku_format, gridBagConstraints151);
			jPanel3.add(jLabel272, gridBagConstraints152);
			jPanel3.add(jLabel_spacer, gridBagConstraints153);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			// del s.inoue 2009/10/11
//			GridBagConstraints gridBagConstraints158 = new GridBagConstraints();
//			gridBagConstraints158.gridx = 2;
//			gridBagConstraints158.anchor = GridBagConstraints.WEST;
//			gridBagConstraints158.gridy = 6;
//			jLabel_fax_format = new ExtendedLabel();
//			jLabel_fax_format.setText("（半角数字のみ）");
//			GridBagConstraints gridBagConstraints157 = new GridBagConstraints();
//			gridBagConstraints157.gridx = 2;
//			gridBagConstraints157.anchor = GridBagConstraints.WEST;
//			gridBagConstraints157.gridy = 5;
//			jLabel_kigo_format1 = new ExtendedLabel();
//			jLabel_kigo_format1.setText("（半角数字のみ）");
//			GridBagConstraints gridBagConstraints156 = new GridBagConstraints();
//			gridBagConstraints156.gridx = 2;
//			gridBagConstraints156.anchor = GridBagConstraints.WEST;
//			gridBagConstraints156.gridy = 4;
//			jLabel_hometel_format = new ExtendedLabel();
//			jLabel_hometel_format.setText("（半角数字のみ）");
			GridBagConstraints gridBagConstraints154 = new GridBagConstraints();
			gridBagConstraints154.gridx = 4;
			gridBagConstraints154.anchor = GridBagConstraints.EAST;
			gridBagConstraints154.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints154.gridwidth = 2;
			gridBagConstraints154.gridy = 3;
			jLabel_address_format1 = new ExtendedLabel();
			jLabel_address_format1.setText("（↑全角のみ100文字以内）");
			GridBagConstraints gridBagConstraints149 = new GridBagConstraints();
			gridBagConstraints149.gridx = 3;
			gridBagConstraints149.anchor = GridBagConstraints.WEST;
			gridBagConstraints149.gridy = 1;
			jLabel_post_format = new ExtendedLabel();
			jLabel_post_format.setText("（半角数字7桁）");
			GridBagConstraints gridBagConstraints146 = new GridBagConstraints();
			gridBagConstraints146.gridx = 0;
			gridBagConstraints146.anchor = GridBagConstraints.WEST;
			gridBagConstraints146.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints146.gridy = 0;
			jLabel141 = new ExtendedLabel(Font.BOLD);
			jLabel141.setText("受診者情報");
//			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
//			gridBagConstraints45.anchor = GridBagConstraints.WEST;
//			gridBagConstraints45.gridwidth = 1;
//			gridBagConstraints45.gridx = 1;
//			gridBagConstraints45.gridy = 8;
//			gridBagConstraints45.weightx = 1.0;
//			gridBagConstraints45.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints45.fill = GridBagConstraints.HORIZONTAL;
//			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
//			gridBagConstraints44.anchor = GridBagConstraints.WEST;
//			gridBagConstraints44.gridwidth = 1;
//			gridBagConstraints44.gridx = 1;
//			gridBagConstraints44.gridy = 7;
//			gridBagConstraints44.weightx = 1.0;
//			gridBagConstraints44.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints44.fill = GridBagConstraints.HORIZONTAL;
//			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
//			gridBagConstraints40.anchor = GridBagConstraints.WEST;
//			gridBagConstraints40.gridx = 1;
//			gridBagConstraints40.gridy = 6;
//			gridBagConstraints40.weightx = 1.0;
//			gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints40.fill = GridBagConstraints.BOTH;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.anchor = GridBagConstraints.WEST;
			gridBagConstraints39.gridx = 3;
			gridBagConstraints39.gridy = 5;
			gridBagConstraints39.weightx = 1.0;
			gridBagConstraints39.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints39.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.anchor = GridBagConstraints.WEST;
			gridBagConstraints38.gridx = 1;
			gridBagConstraints38.gridy = 5;
			gridBagConstraints38.weightx = 1.0;
			gridBagConstraints38.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints38.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.anchor = GridBagConstraints.WEST;
			gridBagConstraints37.gridwidth = 5;
			gridBagConstraints37.gridx = 1;
			gridBagConstraints37.gridy = 2;
			gridBagConstraints37.weightx = 1.0;
//			gridBagConstraints37.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints37.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.gridwidth = 1;
			gridBagConstraints36.gridx = 1;
			gridBagConstraints36.gridy = 1;
			gridBagConstraints36.weightx = 1.0;
//			gridBagConstraints36.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints36.insets = new Insets(0, 10, 0, 0);
//			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
//			gridBagConstraints43.anchor = GridBagConstraints.WEST;
//			gridBagConstraints43.gridx = 0;
//			gridBagConstraints43.gridy = 8;
//			gridBagConstraints43.insets = new Insets(0, 0, 0, 5);
//			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
//			gridBagConstraints42.anchor = GridBagConstraints.WEST;
//			gridBagConstraints42.gridx = 0;
//			gridBagConstraints42.gridy = 7;
//			gridBagConstraints42.insets = new Insets(0, 0, 0, 0);
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 5;
			gridBagConstraints30.gridx = 2;
			gridBagConstraints30.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 5;
			gridBagConstraints17.gridx = 4;
			gridBagConstraints17.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridy = 5;
			gridBagConstraints16.gridx = 0;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.gridwidth = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.gridx = 0;

//			GridBagConstraints gridBagConstraints118 = new GridBagConstraints();
//			gridBagConstraints118.anchor = GridBagConstraints.WEST;
//			gridBagConstraints118.gridy = 4;
//			gridBagConstraints118.gridx = 0;
//			gridBagConstraints118.gridwidth = 3;

			// add s.inoue 2009/10/11
			jLabel26 = new ExtendedLabel();
			jLabel26.setText("携帯E-Mail");
			jLabel24 = new ExtendedLabel();
			jLabel24.setText("E-Mail");

			GridBagConstraints gridBagConstraints158 = new GridBagConstraints();
			gridBagConstraints158.gridx = 0;
			gridBagConstraints158.gridy = 3;
			gridBagConstraints158.gridwidth = 4;
			gridBagConstraints158.anchor = GridBagConstraints.WEST;
			jLabel_number_format = new ExtendedLabel();
			jLabel_number_format.setText("（↓番号は半角数字のみ）");

			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.anchor = GridBagConstraints.WEST;
			gridBagConstraints44.gridwidth = 2;
			gridBagConstraints44.gridx = 1;
			gridBagConstraints44.gridy = 6;
			gridBagConstraints44.weightx = 1.0;
			gridBagConstraints44.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints44.fill = GridBagConstraints.HORIZONTAL;

			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.anchor = GridBagConstraints.WEST;
			gridBagConstraints43.gridx = 3;
			gridBagConstraints43.gridy = 6;
			gridBagConstraints43.insets = new Insets(0, 10, 0, 5);
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.anchor = GridBagConstraints.WEST;
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.gridy = 6;
			gridBagConstraints42.insets = new Insets(0, 0, 0, 0);

			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.anchor = GridBagConstraints.WEST;
			gridBagConstraints45.gridwidth = 2;
			gridBagConstraints45.gridx = 4;
			gridBagConstraints45.gridy = 6;
			gridBagConstraints45.weightx = 1.0;
			gridBagConstraints45.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints45.fill = GridBagConstraints.HORIZONTAL;

			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridy = 5;
			gridBagConstraints46.gridx = 0;
			gridBagConstraints46.gridwidth = 4;

			// add s.inoue 2009/10/11
			jLabel25 = new ExtendedLabel();
			jLabel25.setText("FAX番号");
			jLabel17 = new ExtendedLabel();
			jLabel17.setText("携帯電話番号");
			jLabel16 = new ExtendedLabel();
			jLabel16.setText("自宅電話番号 ");

			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.anchor = GridBagConstraints.WEST;
			gridBagConstraints40.gridx = 5;
			gridBagConstraints40.gridy = 5;
			gridBagConstraints40.weightx = 1.0;
			gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints40.fill = GridBagConstraints.WEST;


			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
			jPanel5.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel5.setOpaque(false);
			jPanel5.add(jLabel14, gridBagConstraints14);
			jPanel5.add(jLabel15, gridBagConstraints15);
			// edit s.inoue 2009/10/11
//			jPanel5.add(getjPanel114(),gridBagConstraints118);
//			jPanel5.add(getjPanel113(),gridBagConstraints118);

			// del s.inoue 2009/10/11
//			 jPanel5.add(jLabel16, gridBagConstraints16);
//			 jPanel5.add(jLabel17, gridBagConstraints17);
//			 jPanel5.add(jLabel25, gridBagConstraints30);
//			 jPanel5.add(jLabel24, gridBagConstraints42);
//			 jPanel5.add(jLabel26, gridBagConstraints43);
			jPanel5.add(getJTextField_ZipCode(), gridBagConstraints36);
			jPanel5.add(getJTextField_Address(), gridBagConstraints37);
			// del s.inoue 2009/10/11
//			jPanel5.add(getJTextField_HomePhone(), gridBagConstraints38);
//			jPanel5.add(getJTextField_CellPhone(), gridBagConstraints39);
//			jPanel5.add(getJTextField_FAXNumber(), gridBagConstraints40);
//			jPanel5.add(getJTextField_EMail(), gridBagConstraints44);
//			jPanel5.add(getJTextField_MobileMail(), gridBagConstraints45);
			jPanel5.add(jLabel141, gridBagConstraints146);
			jPanel5.add(jLabel_post_format, gridBagConstraints149);
			jPanel5.add(jLabel_address_format1, gridBagConstraints154);
			// del s.inoue 2009/10/11
//			jPanel5.add(jLabel_hometel_format, gridBagConstraints156);
//			jPanel5.add(jLabel_kigo_format1, gridBagConstraints157);
//			jPanel5.add(jLabel_fax_format, gridBagConstraints158);

			// add s.inoue 2009/10/11
			jPanel5.add(jLabel24, gridBagConstraints42);
			jPanel5.add(jLabel26, gridBagConstraints43);

			jPanel5.add(getJTextField_EMail(), gridBagConstraints44);
			jPanel5.add(getJTextField_MobileMail(), gridBagConstraints45);
//			jPanel5.add(getjPanel113(),gridBagConstraints46);
//			jPanel12.add(jLabel_hometel_format, gridBagConstraints156);
//			jPanel12.add(jLabel_kigo_format1, gridBagConstraints157);
			jPanel5.add(jLabel_number_format, gridBagConstraints158);
			// add s.inoue 2009/10/11
			jPanel5.add(jLabel16, gridBagConstraints16);
			jPanel5.add(jLabel17, gridBagConstraints17);
			jPanel5.add(jLabel25, gridBagConstraints30);

			jPanel5.add(getJTextField_HomePhone(), gridBagConstraints38);
			// edit s.inoue 2010/02/08 不具合対応
			jPanel5.add(getJTextField_FAXNumber(), gridBagConstraints39);
			jPanel5.add(getJTextField_CellPhone(), gridBagConstraints40);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel20
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getjPanel20() {
		if (jPanel20 == null) {
			// add s.inoue 2009/10/15
			jLabel131 = new ExtendedLabel(null, Font.BOLD);
			jLabel131.setText("単価情報");

			jLabel110 = new ExtendedLabel();
			jLabel110.setText("委託料単価区分");
			jLabel110.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel111 = new ExtendedLabel();
			jLabel111.setText("単価（基本的な健診）");
			jLabel111.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel117 = new ExtendedLabel();
			jLabel117.setText("単価（貧血検査）");
			jLabel117.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel118 = new ExtendedLabel();
			jLabel118.setText("単価（心電図検査）");
			jLabel118.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel119 = new ExtendedLabel();
			jLabel119.setText("単価（眼底検査）");
			jLabel119.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel130 = new ExtendedLabel();
			jLabel130.setText("単価（人間ドック）");
			jLabel130.setFont(new Font("Dialog", Font.PLAIN, 12));

//			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
//			gridBagConstraints71.gridx = 5;
//			gridBagConstraints71.anchor = GridBagConstraints.WEST;
//			gridBagConstraints71.gridy = 5;
//			jLabelNingenDocNum = new ExtendedLabel();
//			jLabelNingenDocNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelNingenDocNum.setText("（半角数字9桁以内）");
//			jLabelNingenDocNum.setFont(new Font("Dialog", Font.PLAIN, 12));
//
//			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
//			gridBagConstraints70.gridx = 5;
//			gridBagConstraints70.anchor = GridBagConstraints.WEST;
//			gridBagConstraints70.gridy = 4;
//			jLabelGanteiNum = new ExtendedLabel();
//			jLabelGanteiNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelGanteiNum.setText("（半角数字9桁以内）");
//			jLabelGanteiNum.setFont(new Font("Dialog", Font.PLAIN, 12));
//			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
//			gridBagConstraints69.gridx = 5;
//			gridBagConstraints69.anchor = GridBagConstraints.WEST;
//			gridBagConstraints69.gridy = 3;
//			jLabelSindenzuNum = new ExtendedLabel();
//			jLabelSindenzuNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelSindenzuNum.setText("（半角数字9桁以内）");
//			jLabelSindenzuNum.setFont(new Font("Dialog", Font.PLAIN, 12));
//			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
//			gridBagConstraints68.gridx = 5;
//			gridBagConstraints68.anchor = GridBagConstraints.WEST;
//			gridBagConstraints68.gridy = 2;
//			jLabelHinketuNum = new ExtendedLabel();
//			jLabelHinketuNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelHinketuNum.setText("（半角数字9桁以内）");
//			jLabelHinketuNum.setFont(new Font("Dialog", Font.PLAIN, 12));
//			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
//			gridBagConstraints67.gridx = 5;
//			gridBagConstraints67.weightx = 1.0D;
//			gridBagConstraints67.anchor = GridBagConstraints.WEST;
//			gridBagConstraints67.gridy = 1;
//			jLabelKihonNum = new ExtendedLabel();
//			jLabelKihonNum.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//			jLabelKihonNum.setText("（半角数字9桁以内）");
//			jLabelKihonNum.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel122 = new ExtendedLabel();
			jLabel122.setText("（1：個別健診 2：集団健診）");
			jLabel122.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel122.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jLabel200 = new ExtendedLabel();
			jLabel200.setText("円");
			jLabel125 = new ExtendedLabel();
			jLabel125.setText("円");
			jLabel129 = new ExtendedLabel();
			jLabel129.setText("円");
			jLabel127 = new ExtendedLabel();
			jLabel127.setText("円");
			jLabel128 = new ExtendedLabel();
			jLabel128.setText("円");

			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridy = 5;
			gridBagConstraints18.gridx = 4;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridy = 4;
			gridBagConstraints16.gridx = 4;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridx = 3;
			gridBagConstraints9.gridy = 4;
			gridBagConstraints9.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.gridy = 5;
			gridBagConstraints10.fill = GridBagConstraints.NONE;
			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
			gridBagConstraints45.anchor = GridBagConstraints.WEST;
			gridBagConstraints45.gridy = 4;
			gridBagConstraints45.gridx = 2;
			gridBagConstraints45.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.anchor = GridBagConstraints.WEST;
			gridBagConstraints46.gridy = 5;
			gridBagConstraints46.gridx = 2;
			gridBagConstraints46.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridy = 3;
			gridBagConstraints15.gridx = 4;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 3;
			gridBagConstraints8.gridx = 3;
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.anchor = GridBagConstraints.WEST;
			gridBagConstraints44.gridy = 3;
			gridBagConstraints44.gridx = 2;
			gridBagConstraints44.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.gridx = 4;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.gridx = 3;
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.anchor = GridBagConstraints.WEST;
			gridBagConstraints43.gridy = 2;
			gridBagConstraints43.gridx = 2;
			gridBagConstraints43.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.gridx = 4;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.gridx = 3;
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.gridy = 1;
			gridBagConstraints36.gridx = 2;
			gridBagConstraints36.insets = new Insets(0, 10, 0, 0);

			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 0;
			gridBagConstraints17.gridwidth = 2;
			gridBagConstraints17.gridx = 4;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.gridwidth = 2;
			gridBagConstraints14.gridx = 3;
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.anchor = GridBagConstraints.WEST;
			gridBagConstraints37.gridy = 0;
			gridBagConstraints37.gridx = 2;
			gridBagConstraints37.insets = new Insets(0, 10, 0, 0);

			// add s.inoue 2009/10/15
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.anchor = GridBagConstraints.WEST;
			gridBagConstraints38.fill = GridBagConstraints.WEST;
			gridBagConstraints38.gridy = 0;
			gridBagConstraints38.gridx = 1;

			// add s.inoue 2009/10/01
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.gridy = 0;
			gridBagConstraints62.gridx = 1;
			// add s.inoue 2009/10/15
			gridBagConstraints62.anchor = GridBagConstraints.WEST;
			gridBagConstraints62.fill = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.gridy = 5;
			gridBagConstraints63.gridx = 1;
			// add s.inoue 2009/10/15
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.fill = GridBagConstraints.WEST;

			// edit s.inoue 2010/04/14
//			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
//			gridBagConstraints64.fill = GridBagConstraints.EAST;
//			gridBagConstraints64.anchor = GridBagConstraints.EAST;
//			gridBagConstraints64.insets = new Insets(0, 183, 0, 0);
//			gridBagConstraints64.gridwidth = 5;
//			gridBagConstraints64.gridy = 0;
//			gridBagConstraints64.gridx = 6;

			// edit s.inoue 2009/10/01
//			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
//			gridBagConstraints64.anchor = GridBagConstraints.WEST;
//			gridBagConstraints64.gridx = 0;
//			gridBagConstraints64.gridy = 0;
//			gridBagConstraints64.gridwidth = 2;

			jPanel20 = new JPanel();
			jPanel20.setLayout(new GridBagLayout());
			jPanel20.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			// add s.inoue 2009/10/15
			jPanel20.add(jLabel131, gridBagConstraints38);

			jPanel20.add(jLabel110, gridBagConstraints37);
			jPanel20.add(getJComboBox_ItakuKubun(), gridBagConstraints14);
			jPanel20.add(jLabel122, gridBagConstraints17);
			jPanel20.add(jLabel111, gridBagConstraints36);
			jPanel20.add(getJTextField_KihonTanka(), gridBagConstraints5);
			jPanel20.add(jLabel200, gridBagConstraints6);
			jPanel20.add(jLabel117, gridBagConstraints43);
			jPanel20.add(getJTextField_HinketsuTanka(), gridBagConstraints7);
			jPanel20.add(jLabel125, gridBagConstraints13);
			jPanel20.add(jLabel118, gridBagConstraints44);
			jPanel20.add(getJTextField_SindenzuTanka(), gridBagConstraints8);
			jPanel20.add(jLabel129, gridBagConstraints15);
			jPanel20.add(jLabel119, gridBagConstraints45);
			jPanel20.add(jLabel130, gridBagConstraints46);
			jPanel20.add(getJTextField_GanteiTanka(), gridBagConstraints9);
			jPanel20.add(jLabel127, gridBagConstraints16);
			jPanel20.add(getJTextField_NingenDocTanka(), gridBagConstraints10);
			jPanel20.add(jLabel128, gridBagConstraints18);
			// edit s.inoue 2010/04/14
//			jPanel20.add(getJPanelsep(), gridBagConstraints64);

			// del s.inoue 2009/10/15
//			jPanel20.add(getJPanelKihon(), gridBagConstraints62);
//			jPanel20.add(getJPanelDoc(), gridBagConstraints63);
		}
		return jPanel20;
	}

	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getJPanelsep() {
//		if (jPanelsep == null) {
//			jPanelsep = new JPanel();
//			jPanelsep.setLayout(new GridBagLayout());
////			jPanelsep.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//		}
//		return jPanelsep;
//	}

	/**
	 * This method initializes jComboBox_ItakuKubun
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_ItakuKubun() {
		if (jComboBox_ItakuKubun == null) {
			// del s.inoue 2009/10/06
			// jComboBox_ItakuKubun = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_ItakuKubun = new ExtendedComboBox();
			jComboBox_ItakuKubun.setPreferredSize(new Dimension(100, 20));
			jComboBox_ItakuKubun.setMinimumSize(new Dimension(31, 20));
		}
		return jComboBox_ItakuKubun;
	}

	/**
	 * This method initializes jTextField_KihonTanka
	 *
	 * @return javax.swing.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_KihonTanka() {
		if (jTextField_KihonTanka == null) {
			jTextField_KihonTanka = new ExtendedOpenTextControl("", 9, ImeMode.IME_OFF);
			jTextField_KihonTanka.setPreferredSize(new Dimension(100, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_KihonTanka.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_KihonTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
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
			jTextField_HinketsuTanka.setPreferredSize(new Dimension(100, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_HinketsuTanka.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_HinketsuTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
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
			jTextField_SindenzuTanka.setPreferredSize(new Dimension(100, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_SindenzuTanka.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_SindenzuTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
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
			jTextField_GanteiTanka.setPreferredSize(new Dimension(100, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_GanteiTanka.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_GanteiTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
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
			jTextField_NingenDocTanka.setPreferredSize(new Dimension(100, 20));
			// move s.inoue 2011/12/13
			// jTextField_NingenDocTanka.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_NingenDocTanka.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
			// add ver2 s.inoue 2009/07/10
			jTextField_NingenDocTanka.addFocusListener(this);
		}
		return jTextField_NingenDocTanka;
	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// add s.inoue 2009/10/01
//	/**
//	 * This method initializes jPanel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelKihon() {
//		if (jPanelKihon == null) {
//			jPanelKihon = new JPanel();
//			jPanelKihon.setLayout(new BoxLayout(getJPanelKihon(), BoxLayout.X_AXIS));
//			jPanelKihon.add(getJRadioButton_Kihon(), null);
//		}
//		return jPanelKihon;
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// add s.inoue 2009/10/01
//	/**
//	 * This method initializes jPanel1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelDoc() {
//		if (jPanelDoc == null) {
//			jPanelDoc = new JPanel();
//			jPanelDoc.setLayout(new BoxLayout(getJPanelDoc(), BoxLayout.X_AXIS));
//			jPanelDoc.add(getJRadioButton_Doc(), null);
//		}
//		return jPanelDoc;
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// add s.inoue 2009/10/01
//	/**
//	 * This method initializes jRadioButton_Male
//	 *
//	 * @return javax.swing.JRadioButton
//	 */
//	private ExtendedRadioButton getJRadioButton_Kihon() {
//		if (jRadioButton_Kihon == null) {
//			jRadioButton_Kihon = new ExtendedRadioButton();
//			jRadioButton_Kihon.setText("1：基本健診");
//			jRadioButton_Kihon.setPreferredSize(new Dimension(105, 20));
//			// del s.inoue 2010/05/10
////			jRadioButton_Kihon.addKeyListener(this);
//			jRadioButton_Kihon.addItemListener(this);
//			groupHantei.add(jRadioButton_Kihon);
//		}
//		return jRadioButton_Kihon;
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// add s.inoue 2009/10/01
//	/**
//	 * This method initializes jRadioButton_Female
//	 *
//	 * @return javax.swing.EventHandleRadioButton
//	 */
//	private ExtendedRadioButton getJRadioButton_Doc() {
//		if (jRadioButton_Doc == null) {
//			jRadioButton_Doc = new ExtendedRadioButton();
//			jRadioButton_Doc.setText("2：人間ドック");
//			jRadioButton_Doc.setPreferredSize(new Dimension(105, 20));
//			jRadioButton_Doc.addItemListener(this);
//			// del s.inoue 2010/05/10
////			jRadioButton_Doc.addKeyListener(this);
//			groupHantei.add(jRadioButton_Doc);
//		}
//		return jRadioButton_Doc;
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// add s.inoue 2009/10/01
//	/**
//	 * This method initializes jTextField_KihonTanka
//	 *
//	 * @return javax.swing.ExtendedOpenTextControl
//	 */
//	private ExtendedOpenTextControl getJTextField_TankaHantei() {
//		if (jTextField_TankaHantei == null) {
//			jTextField_TankaHantei = new ExtendedOpenTextControl("", 1, ImeMode.IME_OFF);
//			// eidt s.inoue 2011/12/13
//			// jTextField_TankaHantei.setHorizontalAlignment(JTextField.RIGHT);
//			jTextField_TankaHantei.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
//
//			jTextField_TankaHantei.setPreferredSize(new Dimension(20, 20));
//			jTextField_TankaHantei.addActionListener(this);
//			// del s.inoue 2010/05/10
////			jTextField_TankaHantei.addKeyListener(this);
//			jTextField_TankaHantei.setColumns(1);
//		}
//		return jTextField_TankaHantei;
//	}
	// add s.inoue 2009/10/11
//	/**
//	 * This method initializes jPanel12
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getjPanel114() {
//		if (jPanel114 == null) {
//			jLabel26 = new ExtendedLabel();
//			jLabel26.setText("携帯E-Mail");
//			jLabel24 = new ExtendedLabel();
//			jLabel24.setText("E-Mail");
//
//			GridBagConstraints gridBagConstraints158 = new GridBagConstraints();
//			gridBagConstraints158.gridx = 0;
//			gridBagConstraints158.gridy = 0;
//			gridBagConstraints158.gridwidth = 4;
//			gridBagConstraints158.anchor = GridBagConstraints.WEST;
//			jLabel_number_format = new ExtendedLabel();
//			jLabel_number_format.setText("（番号は半角数字のみ）");
//
////			GridBagConstraints gridBagConstraints157 = new GridBagConstraints();
////			gridBagConstraints157.gridx = 2;
////			gridBagConstraints157.anchor = GridBagConstraints.WEST;
////			gridBagConstraints157.gridy = 1;
////			jLabel_kigo_format1 = new ExtendedLabel();
////			jLabel_kigo_format1.setText("（半角数字のみ）");
//
////			GridBagConstraints gridBagConstraints156 = new GridBagConstraints();
////			gridBagConstraints156.gridx = 2;
////			gridBagConstraints156.anchor = GridBagConstraints.WEST;
////			gridBagConstraints156.gridy = 0;
////			jLabel_hometel_format = new ExtendedLabel();
////			jLabel_hometel_format.setText("（半角数字のみ）");
//
//			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
//			gridBagConstraints44.anchor = GridBagConstraints.WEST;
//			gridBagConstraints44.gridwidth = 2;
//			gridBagConstraints44.gridx = 1;
//			gridBagConstraints44.gridy = 2;
//			gridBagConstraints44.weightx = 1.0;
//			gridBagConstraints44.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints44.fill = GridBagConstraints.HORIZONTAL;
//
//			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
//			gridBagConstraints43.anchor = GridBagConstraints.WEST;
//			gridBagConstraints43.gridx = 3;
//			gridBagConstraints43.gridy = 2;
//			gridBagConstraints43.insets = new Insets(0, 10, 0, 5);
//			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
//			gridBagConstraints42.anchor = GridBagConstraints.WEST;
//			gridBagConstraints42.gridx = 0;
//			gridBagConstraints42.gridy = 2;
//			gridBagConstraints42.insets = new Insets(0, 0, 0, 0);
//
//			GridBagConstraints gridBagConstraints45 = new GridBagConstraints();
//			gridBagConstraints45.anchor = GridBagConstraints.WEST;
//			gridBagConstraints45.gridwidth = 2;
//			gridBagConstraints45.gridx = 4;
//			gridBagConstraints45.gridy = 2;
//			gridBagConstraints45.weightx = 1.0;
//			gridBagConstraints45.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints45.fill = GridBagConstraints.HORIZONTAL;
//
//			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
//			gridBagConstraints46.gridy = 1;
//			gridBagConstraints46.gridx = 0;
//			gridBagConstraints46.gridwidth = 4;
//
//			// add s.inoue 2009/10/11
//			jLabel25 = new ExtendedLabel();
//			jLabel25.setText("FAX番号");
//			jLabel17 = new ExtendedLabel();
//			jLabel17.setText("携帯電話番号");
//			jLabel16 = new ExtendedLabel();
//			jLabel16.setText("自宅電話番号 ");
//
//			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
//			gridBagConstraints40.anchor = GridBagConstraints.WEST;
//			gridBagConstraints40.gridx = 5;
//			gridBagConstraints40.gridy = 1;
//			gridBagConstraints40.weightx = 1.0;
//			gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints40.fill = GridBagConstraints.WEST;
//			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
//			gridBagConstraints39.anchor = GridBagConstraints.WEST;
//			gridBagConstraints39.gridx = 3;
//			gridBagConstraints39.gridy = 1;
//			gridBagConstraints39.weightx = 1.0;
//			gridBagConstraints39.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints39.fill = GridBagConstraints.WEST;
//			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
//			gridBagConstraints38.anchor = GridBagConstraints.WEST;
//			gridBagConstraints38.gridx = 1;
//			gridBagConstraints38.gridy = 1;
//			gridBagConstraints38.weightx = 1.0;
//			gridBagConstraints38.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints38.fill = GridBagConstraints.WEST;
//
//			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
//			gridBagConstraints30.anchor = GridBagConstraints.WEST;
//			gridBagConstraints30.gridy = 1;
//			gridBagConstraints30.gridx = 4;
//			gridBagConstraints30.insets = new Insets(0, 10, 0, 0);
//
//			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
//			gridBagConstraints17.anchor = GridBagConstraints.WEST;
//			gridBagConstraints17.gridy = 1;
//			gridBagConstraints17.gridx = 2;
//			gridBagConstraints17.insets = new Insets(0, 10, 0, 0);
//
//			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
//			gridBagConstraints16.anchor = GridBagConstraints.WEST;
//			gridBagConstraints16.gridy = 1;
//			gridBagConstraints16.gridx = 0;
//
//
//			jPanel114 = new JPanel();
//			jPanel114.setLayout(new GridBagLayout());
//			//jPanel12.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel114.setOpaque(false);
//			jPanel114.add(jLabel24, gridBagConstraints42);
//			jPanel114.add(jLabel26, gridBagConstraints43);
//
//			jPanel114.add(getJTextField_EMail(), gridBagConstraints44);
//			jPanel114.add(getJTextField_MobileMail(), gridBagConstraints45);
////			jPanel114.add(getjPanel113(),gridBagConstraints46);
////			jPanel12.add(jLabel_hometel_format, gridBagConstraints156);
////			jPanel12.add(jLabel_kigo_format1, gridBagConstraints157);
//			jPanel114.add(jLabel_number_format, gridBagConstraints158);
//			// add s.inoue 2009/10/11
//			jPanel114.add(jLabel16, gridBagConstraints16);
//			jPanel114.add(jLabel17, gridBagConstraints17);
//			jPanel114.add(jLabel25, gridBagConstraints30);
//
//			jPanel114.add(getJTextField_HomePhone(), gridBagConstraints38);
//			jPanel114.add(getJTextField_CellPhone(), gridBagConstraints39);
//			jPanel114.add(getJTextField_FAXNumber(), gridBagConstraints40);
//
//		}
//		return jPanel114;
//	}

	// add s.inoue 2009/10/11
	/**
	 * This method initializes jPanel13
	 *
	 * @return javax.swing.JPanel
	 */
//	private JPanel getjPanel113() {
//		if (jPanel113 == null) {
//			jLabel25 = new ExtendedLabel();
//			jLabel25.setText("FAX番号");
//			jLabel17 = new ExtendedLabel();
//			jLabel17.setText("携帯電話番号");
//			jLabel16 = new ExtendedLabel();
//			jLabel16.setText("自宅電話番号　　 ");
//			jLabel15 = new ExtendedLabel();
//
//			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
//			gridBagConstraints40.anchor = GridBagConstraints.WEST;
//			gridBagConstraints40.gridx = 5;
//			gridBagConstraints40.gridy = 0;
//			gridBagConstraints40.weightx = 1.0;
//			gridBagConstraints40.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints40.fill = GridBagConstraints.WEST;
//			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
//			gridBagConstraints39.anchor = GridBagConstraints.WEST;
//			gridBagConstraints39.gridx = 3;
//			gridBagConstraints39.gridy = 0;
//			gridBagConstraints39.weightx = 1.0;
//			gridBagConstraints39.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints39.fill = GridBagConstraints.WEST;
//			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
//			gridBagConstraints38.anchor = GridBagConstraints.WEST;
//			gridBagConstraints38.gridx = 1;
//			gridBagConstraints38.gridy = 0;
//			gridBagConstraints38.weightx = 1.0;
//			gridBagConstraints38.insets = new Insets(0, 10, 0, 0);
////			gridBagConstraints38.fill = GridBagConstraints.WEST;
//
//			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
//			gridBagConstraints30.anchor = GridBagConstraints.WEST;
//			gridBagConstraints30.gridy = 0;
//			gridBagConstraints30.gridx = 4;
//			gridBagConstraints30.insets = new Insets(0, 10, 0, 0);
//
//			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
//			gridBagConstraints17.anchor = GridBagConstraints.WEST;
//			gridBagConstraints17.gridy = 0;
//			gridBagConstraints17.gridx = 2;
//			gridBagConstraints17.insets = new Insets(0, 10, 0, 0);
//
//			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
//			gridBagConstraints16.anchor = GridBagConstraints.WEST;
//			gridBagConstraints16.gridy = 0;
//			gridBagConstraints16.gridx = 0;
////			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
////			gridBagConstraints15.anchor = GridBagConstraints.WEST;
////			gridBagConstraints15.gridx = 0;
////			gridBagConstraints15.gridy = 2;
////			gridBagConstraints15.gridwidth = 1;
////			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
////			gridBagConstraints14.anchor = GridBagConstraints.WEST;
////			gridBagConstraints14.gridy = 1;
////			gridBagConstraints14.gridx = 0;
//
//			jPanel113 = new JPanel();
//			jPanel113.setLayout(new GridBagLayout());
//			jPanel113.setOpaque(false);
//			jPanel113.add(jLabel16, gridBagConstraints16);
//			jPanel113.add(jLabel17, gridBagConstraints17);
//			jPanel113.add(jLabel25, gridBagConstraints30);
//
//			jPanel113.add(getJTextField_HomePhone(), gridBagConstraints38);
//			jPanel113.add(getJTextField_CellPhone(), gridBagConstraints39);
//			jPanel113.add(getJTextField_FAXNumber(), gridBagConstraints40);
//			}
//		return jPanel113;
//	}
	/**
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {

			// add ver2 s.inoue 2009/07/29
			GridBagConstraints gridBagConstraints145 = new GridBagConstraints();
			gridBagConstraints145.gridx = 5;
			gridBagConstraints145.anchor = GridBagConstraints.WEST;
			gridBagConstraints145.gridy = 7;
			jLabel_sex_format = new ExtendedLabel();
			jLabel_sex_format.setText("（1 または 2）");
			GridBagConstraints gridBagConstraints144 = new GridBagConstraints();
			gridBagConstraints144.gridx = 1;
			gridBagConstraints144.anchor = GridBagConstraints.WEST;
			gridBagConstraints144.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints144.gridy = 0;
			jLabel71 = new ExtendedLabel(null, Font.BOLD);
			jLabel71.setText("保険証情報");
			
			// edit n.ohkubo 2015/03/01　追加　start
			ExtendedLabel hokenshoInfo = new ExtendedLabel();
			hokenshoInfo.setText("（被保険者証等の記号と番号は、全角と半角の混在は出来ません）");
			GridBagConstraints gridBagConstraintsHokenshoInfo = new GridBagConstraints();
			gridBagConstraintsHokenshoInfo.gridx = 2;
			gridBagConstraintsHokenshoInfo.anchor = GridBagConstraints.WEST;
			gridBagConstraintsHokenshoInfo.insets = new Insets(0, 0, 5, 0);
			gridBagConstraintsHokenshoInfo.gridy = 0;
			gridBagConstraintsHokenshoInfo.gridwidth = 4;
			// edit n.ohkubo 2015/03/01　追加　end
			
			GridBagConstraints gridBagConstraints143 = new GridBagConstraints();
			gridBagConstraints143.gridx = 5;
			gridBagConstraints143.anchor = GridBagConstraints.WEST;
			gridBagConstraints143.gridy = 6;
			jLabel_birthday_format = new ExtendedLabel();
			jLabel_birthday_format.setText("（日レセフォーマット）");
			GridBagConstraints gridBagConstraints142 = new GridBagConstraints();
			gridBagConstraints142.gridx = 5;
			gridBagConstraints142.anchor = GridBagConstraints.WEST;
			gridBagConstraints142.gridy = 5;
			jLabel_tsusho_format = new ExtendedLabel();
			jLabel_tsusho_format.setText("（全・半角50文字以内）");
			GridBagConstraints gridBagConstraints141 = new GridBagConstraints();
			gridBagConstraints141.gridx = 5;
			gridBagConstraints141.anchor = GridBagConstraints.WEST;
			gridBagConstraints141.gridy = 4;
			jLabel_kanji_format = new ExtendedLabel();
			jLabel_kanji_format.setText("（全角のみ50文字以内）");

			GridBagConstraints gridBagConstraints139 = new GridBagConstraints();
			gridBagConstraints139.gridx = 5;
			gridBagConstraints139.anchor = GridBagConstraints.WEST;
			gridBagConstraints139.gridy = 2;
			jLabel_bango_format = new ExtendedLabel();
//			jLabel_bango_format.setText("（全角のみ20文字以内）");			// edit n.ohkubo 2015/03/01　削除
			jLabel_bango_format.setText("（全角20文字/半角40文字以内）");		// edit n.ohkubo 2015/03/01　追加
			
			GridBagConstraints gridBagConstraints138 = new GridBagConstraints();
			gridBagConstraints138.gridx = 5;
			gridBagConstraints138.anchor = GridBagConstraints.WEST;
			gridBagConstraints138.gridy = 1;
			jLabel_kigo_format = new ExtendedLabel();
//			jLabel_kigo_format.setText("（全角のみ20文字以内）");			// edit n.ohkubo 2015/03/01　削除
			jLabel_kigo_format.setText("（全角20文字/半角40文字以内）");		// edit n.ohkubo 2015/03/01　追加

			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.anchor = GridBagConstraints.WEST;
			gridBagConstraints29.gridy = 7;
			gridBagConstraints29.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints29.gridx = 3;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridy = 7;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
			gridBagConstraints35.anchor = GridBagConstraints.WEST;
			gridBagConstraints35.gridx = 3;
			gridBagConstraints35.gridy = 6;
			gridBagConstraints35.weightx = 1.0;
			gridBagConstraints35.insets = new Insets(0, 10, 0, 0);
//			gridBagConstraints35.fill = GridBagConstraints.HORIZONTAL;

			// add ver2 s.inoue 2009/07/29
			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
			gridBagConstraints36.anchor = GridBagConstraints.WEST;
			gridBagConstraints36.gridx = 3;
			gridBagConstraints36.gridy = 6;
//			gridBagConstraints36.weightx = 1.0;
			gridBagConstraints36.insets = new Insets(0, 130, 0, 0);
			gridBagConstraints36.fill = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 6;
			gridBagConstraints12.gridwidth = 1;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.anchor = GridBagConstraints.WEST;
			gridBagConstraints34.gridx = 3;
			gridBagConstraints34.gridy = 5;
			gridBagConstraints34.weightx = 1.0;
			gridBagConstraints34.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints34.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 5;
			gridBagConstraints11.gridwidth = 2;
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.anchor = GridBagConstraints.WEST;
			gridBagConstraints33.gridx = 3;
			gridBagConstraints33.gridy = 4;
			gridBagConstraints33.weightx = 1.0;
			gridBagConstraints33.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints33.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.anchor = GridBagConstraints.WEST;
			gridBagConstraints47.gridx = 3;
			gridBagConstraints47.gridy = 3;
			gridBagConstraints47.weightx = 1.0;
			gridBagConstraints47.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints47.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.anchor = GridBagConstraints.WEST;
			gridBagConstraints28.gridx = 3;
			gridBagConstraints28.gridy = 2;
			gridBagConstraints28.weightx = 1.0;
			gridBagConstraints28.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints28.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.anchor = GridBagConstraints.WEST;
			gridBagConstraints27.gridx = 3;
			gridBagConstraints27.gridy = 1;
			gridBagConstraints27.weightx = 1.0;
			gridBagConstraints27.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints27.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 4;
			gridBagConstraints10.gridwidth = 2;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.gridwidth = 2;

			jLabel_yearold_format = new ExtendedLabel();
			jLabel_yearold_format.setText("歳");
			GridBagConstraints gridBagConstraints146 = new GridBagConstraints();
			gridBagConstraints146.gridx = 3;
			gridBagConstraints146.anchor = GridBagConstraints.WEST;
			gridBagConstraints146.gridy = 6;
			gridBagConstraints146.insets = new Insets(0, 180, 0, 5);

			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel6.setOpaque(false);
			jPanel6.add(jLabel7, gridBagConstraints7);
			jPanel6.add(jLabel8, gridBagConstraints8);
			jPanel6.add(jLabel10, gridBagConstraints10);
			jPanel6.add(getJTextField_HihokenjyaCode(), gridBagConstraints27);
			jPanel6.add(getJTextField_HihokenjyaNumber(), gridBagConstraints28);
			// delete ver2 s.inoue 2009/05/07
			//jPanel6.add(getJTextField_NameKana(), gridBagConstraints47);
			jPanel6.add(getJTextField_NameKanji(), gridBagConstraints33);
			jPanel6.add(jLabel11, gridBagConstraints11);
			jPanel6.add(getJTextField_NameTsushou(), gridBagConstraints34);
			jPanel6.add(jLabel12, gridBagConstraints12);
			jPanel6.add(getJTextField_Birthday(), gridBagConstraints35);
			// add ver2 s.inoue 2009/07/29
			jPanel6.add(getJTextField_YearOld(), gridBagConstraints36);
			jPanel6.add(jLabel13, gridBagConstraints13);
			jPanel6.add(getJPanel_RadioPanel(), gridBagConstraints29);
			jPanel6.add(jLabel_kigo_format, gridBagConstraints138);
			jPanel6.add(jLabel_bango_format, gridBagConstraints139);
			jPanel6.add(jLabel_kanji_format, gridBagConstraints141);
			jPanel6.add(jLabel_tsusho_format, gridBagConstraints142);
			jPanel6.add(jLabel_birthday_format, gridBagConstraints143);
			// add ver2 s.inoue 2009/07/29
			jPanel6.add(jLabel_yearold_format, gridBagConstraints146);
			jPanel6.add(jLabel71, gridBagConstraints144);
			jPanel6.add(hokenshoInfo, gridBagConstraintsHokenshoInfo);	// edit n.ohkubo 2015/03/01　追加
			jPanel6.add(jLabel_sex_format, gridBagConstraints145);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints155 = new GridBagConstraints();
			gridBagConstraints155.gridx = 1;
			gridBagConstraints155.anchor = GridBagConstraints.EAST;
			gridBagConstraints155.gridwidth = 2;
			gridBagConstraints155.gridy = 11;
			jLabel51 = new ExtendedLabel();
			jLabel51.setText("（全角のみ50文字以内）");
			GridBagConstraints gridBagConstraints150 = new GridBagConstraints();
			gridBagConstraints150.gridx = 1;
			gridBagConstraints150.anchor = GridBagConstraints.WEST;
			gridBagConstraints150.gridwidth = 2;
			gridBagConstraints150.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints150.gridy = 0;
			jLabel_hokenjyanum_format = new ExtendedLabel();
			jLabel_hokenjyanum_format.setText("（保険者は半角数字8桁以内、支払代行機関は半角数字8桁）");
			GridBagConstraints gridBagConstraints147 = new GridBagConstraints();
			gridBagConstraints147.gridx = 0;
			gridBagConstraints147.anchor = GridBagConstraints.WEST;
			gridBagConstraints147.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints147.gridy = 0;
			jLabel711 = new ExtendedLabel(null, Font.BOLD);
			jLabel711.setText("契約情報");
			GridBagConstraints gridBagConstraints105 = new GridBagConstraints();
			gridBagConstraints105.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints105.gridy = 6;
			gridBagConstraints105.gridx = 1;
			GridBagConstraints gridBagConstraints104 = new GridBagConstraints();
			gridBagConstraints104.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints104.gridy = 1;
			gridBagConstraints104.gridx = 1;
			GridBagConstraints gridBagConstraints102 = new GridBagConstraints();
			gridBagConstraints102.gridx = 0;
			gridBagConstraints102.ipadx = 10;
			gridBagConstraints102.ipady = 5;
			gridBagConstraints102.gridy = 2;
			GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
			gridBagConstraints101.gridx = 0;
			gridBagConstraints101.ipadx = 10;
			gridBagConstraints101.ipady = 5;
			gridBagConstraints101.gridy = 7;
			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
			gridBagConstraints73.gridx = 3;
			gridBagConstraints73.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints73.gridy = 5;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 3;
			gridBagConstraints51.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints51.gridy = 0;
			GridBagConstraints gridBagConstraints99 = new GridBagConstraints();
			gridBagConstraints99.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints99.gridy = 6;
			gridBagConstraints99.weightx = 1.0;
			gridBagConstraints99.gridx = 2;
			GridBagConstraints gridBagConstraints98 = new GridBagConstraints();
			gridBagConstraints98.gridy = 1;
			gridBagConstraints98.weightx = 1.0;
			gridBagConstraints98.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints98.gridx = 2;
			GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
			gridBagConstraints72.gridx = 0;
			gridBagConstraints72.gridy = 6;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints26.gridx = 1;
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridwidth = 2;
			gridBagConstraints26.gridy = 10;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints6.gridheight = 2;
			gridBagConstraints6.gridy = 10;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			gridBagConstraints25.gridx = 2;
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints25.gridy = 7;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.anchor = GridBagConstraints.WEST;
			gridBagConstraints71.gridy = 7;
			gridBagConstraints71.gridx = 1;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.gridx = 2;
			gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints24.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints24.gridy = 6;
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.anchor = GridBagConstraints.WEST;
			gridBagConstraints53.gridy = 6;
			gridBagConstraints53.gridx = 1;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.gridy = 5;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridx = 1;
			gridBagConstraints41.gridy = 5;
			gridBagConstraints41.insets = new Insets(0, 0, 0, 0);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 6;
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridx = 2;
			gridBagConstraints22.weightx = 1.0D;
			gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints22.gridy = 3;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.anchor = GridBagConstraints.WEST;
			gridBagConstraints31.gridy = 3;
			gridBagConstraints31.gridx = 1;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridx = 2;
			gridBagConstraints20.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints20.gridy = 2;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridy = 2;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridx = 2;
			gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints19.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints19.gridy = 1;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.anchor = GridBagConstraints.WEST;
			gridBagConstraints18.gridx = 1;
			gridBagConstraints18.gridy = 1;
			gridBagConstraints18.insets = new Insets(0, 0, 0, 0);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 1;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel2.setOpaque(false);
			jPanel2.add(jLabel4, gridBagConstraints4);
			jPanel2.add(jLabel5, gridBagConstraints5);
			jPanel2.add(jLabel6, gridBagConstraints6);
			jPanel2.add(getJTextField_TorimatomeName(), gridBagConstraints26);
			jPanel2.add(getJPanel7(), gridBagConstraints72);
			jPanel2.add(getJComboBox_HokenjyaNumber(), gridBagConstraints98);
			jPanel2.add(getJComboBox_SeikyuKikanNumber(), gridBagConstraints99);
			jPanel2.add(getJButton_hokenjaTsuika(), gridBagConstraints51);
			jPanel2.add(getJButton_DaikouTsuika(), gridBagConstraints73);
			jPanel2.add(getJPanel2111(), gridBagConstraints101);
			jPanel2.add(getJPanel2112(), gridBagConstraints102);
			jPanel2.add(getJTextField_hokenjaNumber(), gridBagConstraints104);
			jPanel2.add(getJTextField_shiharaiNumber(), gridBagConstraints105);
			jPanel2.add(jLabel711, gridBagConstraints147);
			jPanel2.add(jLabel_hokenjyanum_format, gridBagConstraints150);
			jPanel2.add(jLabel51, gridBagConstraints155);
		}
		return jPanel2;
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

	/**
	 * This method initializes jPanel9
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			GridBagConstraints gridBagConstraints810 = new GridBagConstraints();
			gridBagConstraints810.anchor = GridBagConstraints.WEST;
			gridBagConstraints810.gridy = 3;
			gridBagConstraints810.gridx = 3;
			GridBagConstraints gridBagConstraints160 = new GridBagConstraints();
			gridBagConstraints160.anchor = GridBagConstraints.WEST;
			gridBagConstraints160.gridx = 1;
			gridBagConstraints160.gridy = 3;
			gridBagConstraints160.gridwidth = 2;
			GridBagConstraints gridBagConstraints411 = new GridBagConstraints();
			gridBagConstraints411.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints411.gridx = 3;
			gridBagConstraints411.gridy = 0;
			gridBagConstraints411.insets = new Insets(0, 5, 0, 0);
			GridBagConstraints gridBagConstraints311 = new GridBagConstraints();
			gridBagConstraints311.gridx = 2;
			gridBagConstraints311.fill = GridBagConstraints.BOTH;
			gridBagConstraints311.gridy = 0;
			jLabel_orcaid_format = new ExtendedLabel();
			jLabel_orcaid_format.setText("（日レセフォーマット）");
			GridBagConstraints gridBagConstraints310 = new GridBagConstraints();
			gridBagConstraints310.gridx = 3;
			gridBagConstraints310.anchor = GridBagConstraints.WEST;
			gridBagConstraints310.gridy = 5;
			jLabel_yukokigen_format1 = new ExtendedLabel();
			jLabel_yukokigen_format1.setText("（半角数字8桁）");
			GridBagConstraints gridBagConstraints210 = new GridBagConstraints();
			gridBagConstraints210.gridx = 3;
			gridBagConstraints210.anchor = GridBagConstraints.WEST;
			gridBagConstraints210.gridy = 4;
			jLabel_kouhubi_format = new ExtendedLabel();
			jLabel_kouhubi_format.setText("（半角数字8桁）");
			GridBagConstraints gridBagConstraints137 = new GridBagConstraints();
			gridBagConstraints137.gridx = 3;
			gridBagConstraints137.anchor = GridBagConstraints.WEST;
			gridBagConstraints137.gridy = 2;
			jLabel_jyusinken_format = new ExtendedLabel();
			jLabel_jyusinken_format.setText("（半角数字11桁）");
			jLabel_jyusinken_format.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
			gridBagConstraints77.anchor = GridBagConstraints.WEST;
			gridBagConstraints77.gridx = 2;
			gridBagConstraints77.gridy = 5;
			gridBagConstraints77.weightx = 1.0;
			gridBagConstraints77.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints76 = new GridBagConstraints();
			gridBagConstraints76.anchor = GridBagConstraints.WEST;
			gridBagConstraints76.gridx = 2;
			gridBagConstraints76.gridy = 4;
			gridBagConstraints76.weightx = 1.0;
			gridBagConstraints76.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
			gridBagConstraints75.anchor = GridBagConstraints.WEST;
			gridBagConstraints75.gridx = 2;
			gridBagConstraints75.gridy = 2;
			gridBagConstraints75.weightx = 1.0;
			gridBagConstraints75.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 5;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 4;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;

			GridBagConstraints gridBagConstraints159 = new GridBagConstraints();
			gridBagConstraints159.gridx = 2;
			gridBagConstraints159.anchor = GridBagConstraints.EAST;
			gridBagConstraints159.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints159.gridy = 1;

			GridBagConstraints gridBagConstraints412 = new GridBagConstraints();
			gridBagConstraints412.anchor = GridBagConstraints.WEST;
			gridBagConstraints412.gridx = 2;
			gridBagConstraints412.gridy = 3;
			gridBagConstraints412.weightx = 1.0;
			gridBagConstraints412.fill = GridBagConstraints.HORIZONTAL;
			jLabel_kana_format = new ExtendedLabel();
			jLabel_kana_format.setText("（全角のみ50文字以内）");

			jPanel9 = new JPanel();
			jPanel9.setLayout(new GridBagLayout());
			jPanel9.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel9.setOpaque(false);
			jPanel9.add(jLabel, gridBagConstraints);
			jPanel9.add(jLabel1, gridBagConstraints1);
			jPanel9.add(jLabel2, gridBagConstraints2);
			jPanel9.add(jLabel3, gridBagConstraints3);
			jPanel9.add(getJTextField_JyushinkenID(), gridBagConstraints75);
			// add ver2 s.inoue 2009/05/07
			jPanel9.add(getJTextField_NameKana(), gridBagConstraints412);
			jPanel9.add(getJTextField_IssueDate(), gridBagConstraints76);
			jPanel9.add(getJTextField_LimitDate(), gridBagConstraints77);

			jPanel9.add(jLabel_jyusinken_format, gridBagConstraints137);
			jPanel9.add(jLabel_kouhubi_format, gridBagConstraints210);
			jPanel9.add(jLabel_yukokigen_format1, gridBagConstraints310);
			jPanel9.add(jLabel_orcaid_format, gridBagConstraints159);
			jPanel9.add(getJPanel8(), gridBagConstraints311);
			jPanel9.add(getJButton_ORCA(), gridBagConstraints411);
			jPanel9.add(jLabel9, gridBagConstraints160);
			jPanel9.add(jLabel_kana_format, gridBagConstraints810);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jPanel4
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
			gridBagConstraints82.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints82.gridy = 3;
			gridBagConstraints82.insets = new Insets(2, 0, 2, 0);
			gridBagConstraints82.gridx = 0;
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints49.gridy = 2;
			gridBagConstraints49.insets = new Insets(2, 0, 0, 0);
			gridBagConstraints49.gridx = 0;
			GridBagConstraints gridBagConstraints78 = new GridBagConstraints();
//			gridBagConstraints78.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints78.gridx = 0;
			gridBagConstraints78.gridy = 1;
			gridBagConstraints78.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints49.insets = new Insets(2, 0, 0, 0);
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.setOpaque(false);
			jPanel4.add(getJPanel9(), gridBagConstraints78);
			jPanel4.add(getJPanel2(), gridBagConstraints49);
			jPanel4.add(getJPanel3(), gridBagConstraints82);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel10
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			// add s.inoue 2009/10/11
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
			gridBagConstraints82.fill = GridBagConstraints.WEST;
			gridBagConstraints82.gridy = 2;
			gridBagConstraints82.insets = new Insets(0, 2, 2, 0);
			gridBagConstraints82.weighty = 1.0D;
			gridBagConstraints82.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints82.gridx = 0;

			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints81.gridy = 0;
			gridBagConstraints81.insets = new Insets(0, 2, 2, 0);
//			gridBagConstraints81.weighty = 1.0D;
			gridBagConstraints81.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints81.gridx = 0;
			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
//			gridBagConstraints80.gridy = 1;
			gridBagConstraints80.weightx = 1.0D;
			gridBagConstraints80.fill = GridBagConstraints.BOTH;
			gridBagConstraints80.insets = new Insets(0, 2, 2, 0);
			gridBagConstraints80.gridx = 0;
			jPanel10 = new JPanel();
			jPanel10.setLayout(new GridBagLayout());
			jPanel10.setOpaque(false);
			jPanel10.add(getJPanel6(), gridBagConstraints80);
			jPanel10.add(getJPanel5(), gridBagConstraints81);
			// add s.inoue 2009/10/11
			jPanel10.add(getjPanel20(),gridBagConstraints82);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jPanel11
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			GridBagConstraints gridBagConstraints133 = new GridBagConstraints();
			gridBagConstraints133.gridx = 6;
			gridBagConstraints133.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints133.gridy = 0;
			// add s.inoue 2012/05/10
			gridBagConstraints133.anchor = GridBagConstraints.WEST;

//			jLabel151111 = new ExtendedLabel();
//			jLabel151111.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel151111.setText("DB呼出で検索に使用する項目");
			GridBagConstraints gridBagConstraints132 = new GridBagConstraints();
			gridBagConstraints132.gridx = 5;
			gridBagConstraints132.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints132.gridy = 0;
//			jLabel181 = new ExtendedLabel();
//			jLabel181.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel181.setText("ピンク");
//			jLabel181.setForeground(Color.magenta);
			GridBagConstraints gridBagConstraints122 = new GridBagConstraints();
			gridBagConstraints122.gridx = 4;
			gridBagConstraints122.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints122.gridy = 0;
			jLabel15111 = new ExtendedLabel();
			jLabel15111.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel15111.setText("ORCA連携で検索に使用する項目");
			GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
			gridBagConstraints121.gridx = 3;
			gridBagConstraints121.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints121.gridy = 0;
			jLabel18 = new ExtendedLabel();
			jLabel18.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel18.setText("青字");
			jLabel18.setForeground(Color.blue);
			GridBagConstraints gridBagConstraints97 = new GridBagConstraints();
			gridBagConstraints97.gridx = 0;
			gridBagConstraints97.gridwidth = 6;
			gridBagConstraints97.anchor = GridBagConstraints.WEST;
			gridBagConstraints97.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints97.gridy = 1;
			jLabel1513 = new ExtendedLabel();
			jLabel1513.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1513.setText("　　※被保険者証等番号は、HL7 を出力するためには必須です。");
			GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
			gridBagConstraints88.gridx = 0;
			gridBagConstraints88.gridy = 0;
			jLabel1512 = new ExtendedLabel();
			jLabel1512.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel1512.setPreferredSize(new Dimension(50, 16));
			jLabel1512.setText("凡例");
			GridBagConstraints gridBagConstraints87 = new GridBagConstraints();
			gridBagConstraints87.gridx = 4;
			gridBagConstraints87.anchor = GridBagConstraints.WEST;
			gridBagConstraints87.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints87.gridy = 0;
//			jLabel1511 = new ExtendedLabel();
//			jLabel1511.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel1511.setText("重要項目");
			GridBagConstraints gridBagConstraints86 = new GridBagConstraints();
			gridBagConstraints86.gridx = 2;
			gridBagConstraints86.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints86.gridy = 0;
//			jLabel151 = new ExtendedLabel();
//			jLabel151.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel151.setText("入力必須項目");
			GridBagConstraints gridBagConstraints85 = new GridBagConstraints();
			gridBagConstraints85.gridx = 2;
			gridBagConstraints85.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints85.gridy = 0;
// del s.inoue 2012/05/10
//			jLabel_importantExample = new ExtendedLabel();
//			jLabel_importantExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
//			jLabel_importantExample.setPreferredSize(new Dimension(50, 20));
//			jLabel_importantExample.setOpaque(true);
			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
			gridBagConstraints84.gridx = 1;
			gridBagConstraints84.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints84.gridy = 0;
// del s.inoue 2012/05/10
//			jLabel_requiredExample = new ExtendedLabel();
//			jLabel_requiredExample.setPreferredSize(new Dimension(50, 20));
//			jLabel_requiredExample.setOpaque(true);
//			jLabel_requiredExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

			// add s.inoue 2012/05/10
			jLabel19 = new ExtendedLabel();
			jLabel19.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel19.setText("ピンク");
			jLabel19.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel20 = new ExtendedLabel();
			jLabel20.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel20.setText("必須項目");

			jPanel11 = new JPanel();
			jPanel11.setLayout(new GridBagLayout());
			jPanel11.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel11.setOpaque(false);
// edit s.inoue 2012/05/10
//			jPanel11.add(jLabel_requiredExample, gridBagConstraints84);
//			jPanel11.add(jLabel_importantExample, gridBagConstraints85);
//			jPanel11.add(jLabel151, gridBagConstraints86);
//			jPanel11.add(jLabel1511, gridBagConstraints87);

			jPanel11.add(jLabel1512, gridBagConstraints88);
			jPanel11.add(jLabel19, gridBagConstraints84);
			jPanel11.add(jLabel20, gridBagConstraints85);

			jPanel11.add(jLabel1513, gridBagConstraints97);
			jPanel11.add(jLabel18, gridBagConstraints121);
			jPanel11.add(jLabel15111, gridBagConstraints122);
//			jPanel11.add(jLabel181, gridBagConstraints132);
//			jPanel11.add(jLabel151111, gridBagConstraints133);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jPanel12
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			GridBagConstraints gridBagConstraints96 = new GridBagConstraints();
			gridBagConstraints96.gridy = 0;
			gridBagConstraints96.anchor = GridBagConstraints.WEST;
			gridBagConstraints96.gridx = 0;
			GridBagConstraints gridBagConstraints95 = new GridBagConstraints();
			gridBagConstraints95.gridx = 1;
			gridBagConstraints95.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints95.anchor = GridBagConstraints.WEST;
			gridBagConstraints95.gridy = 0;
			jLabel_Nendo = new ExtendedLabel();
			jLabel_Nendo.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_Nendo.setText("年度");
			GridBagConstraints gridBagConstraints94 = new GridBagConstraints();
			gridBagConstraints94.gridy = 0;
			gridBagConstraints94.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints94.anchor = GridBagConstraints.WEST;
			gridBagConstraints94.weightx = 1.0D;
			gridBagConstraints94.gridx = 3;
			GridBagConstraints gridBagConstraints93 = new GridBagConstraints();
			gridBagConstraints93.gridx = 2;
			gridBagConstraints93.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints93.gridy = 0;
			jLabel_uketsukeid = new ExtendedLabel();
			jLabel_uketsukeid.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_uketsukeid.setText("受付ID");
			jPanel12 = new JPanel();
			jPanel12.setLayout(new GridBagLayout());
			jPanel12.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel12.setVisible(false);
			jPanel12.setOpaque(false);
			jPanel12.add(jLabel_uketsukeid, gridBagConstraints93);
			jPanel12.add(getJTextField_Uketsukeid(), gridBagConstraints94);
			jPanel12.add(jLabel_Nendo, gridBagConstraints95);
			jPanel12.add(getJTextField_Nendo(), gridBagConstraints96);
		}
		return jPanel12;
	}

	/**
	 * This method initializes jTextField_Uketsukeid
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_Uketsukeid() {
		if (jTextField_Uketsukeid == null) {
			jTextField_Uketsukeid = new ExtendedOpenTextControl();
			jTextField_Uketsukeid.setPreferredSize(new Dimension(200, 16));
			// eidt s.inoue 2011/12/13
			// jTextField_Uketsukeid.setEditable(false);
			jTextField_Uketsukeid.setEnabled(false);
		}
		return jTextField_Uketsukeid;
	}

	/**
	 * This method initializes jTextField_Nendo
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_Nendo() {
		if (jTextField_Nendo == null) {
			jTextField_Nendo = new ExtendedOpenTextControl();
			jTextField_Nendo.setPreferredSize(new Dimension(100, 16));
			// eidt s.inoue 2011/12/13
			// jTextField_Nendo.setEditable(false);
			jTextField_Nendo.setEnabled(false);
		}
		return jTextField_Nendo;
	}

	/**
	 * This method initializes jComboBox_HokenjyaNumber
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_HokenjyaNumber() {
		if (jComboBox_HokenjyaNumber == null) {
			jComboBox_HokenjyaNumber = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_HokenjyaNumber.setMinimumSize(new Dimension(31, 20));
			jComboBox_HokenjyaNumber.setPreferredSize(new Dimension(150, 20));
			jComboBox_HokenjyaNumber.addPopupMenuListener(this);
			// edit s.inoue 2012/11/27←2010/05/10 再有効化
			jComboBox_HokenjyaNumber.addKeyListener(this);
			jComboBox_HokenjyaNumber.addFocusListener(this);
		}
		return jComboBox_HokenjyaNumber;
	}

	/**
	 * This method initializes jComboBox_SeikyuKikanNumber
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_SeikyuKikanNumber() {
		if (jComboBox_DaikouNumber == null) {
			jComboBox_DaikouNumber = new ExtendedComboBox(ImeMode.IME_OFF);
			jComboBox_DaikouNumber.setMinimumSize(new Dimension(31, 20));
			jComboBox_DaikouNumber.setPreferredSize(new Dimension(150, 20));
			jComboBox_DaikouNumber.addPopupMenuListener(this);
			// edit s.inoue 2012/11/27←2010/05/10 再有効化
			jComboBox_DaikouNumber.addKeyListener(this);
			jComboBox_DaikouNumber.addFocusListener(this);
		}
		return jComboBox_DaikouNumber;
	}

	/**
	 * This method initializes jButton_hokenjaTsuika
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_hokenjaTsuika() {
		if (jButton_HokenjaTsuika == null) {
			jButton_HokenjaTsuika = new ExtendedButton("追加");
			jButton_HokenjaTsuika.setPreferredSize(new Dimension(40, 25));
			jButton_HokenjaTsuika.setVisible(false);
			jButton_HokenjaTsuika.addActionListener(this);
		}
		return jButton_HokenjaTsuika;
	}

	/**
	 * This method initializes jButton_DaikouTsuika
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_DaikouTsuika() {
		if (jButton_DaikouTsuika == null) {
			jButton_DaikouTsuika = new ExtendedButton("追加");
			jButton_DaikouTsuika.setPreferredSize(new Dimension(40, 25));
			jButton_DaikouTsuika.setVisible(false);
			jButton_DaikouTsuika.addActionListener(this);
		}
		return jButton_DaikouTsuika;
	}

	/**
	 * This method initializes jPanel2111
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2111() {
		if (jPanel2111 == null) {
			jPanel2111 = new JPanel();
			jPanel2111.setLayout(new GridBagLayout());
			jPanel2111.setOpaque(false);
		}
		return jPanel2111;
	}

	/**
	 * This method initializes jPanel2112
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2112() {
		if (jPanel2112 == null) {
			jPanel2112 = new JPanel();
			jPanel2112.setLayout(new GridBagLayout());
			jPanel2112.setOpaque(false);
		}
		return jPanel2112;
	}

//	/**
//	 * This method initializes jButton_Cancel
//	 *
//	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Cancel() {
//		if (jButton_Cancel == null) {
//			jButton_Cancel = new ExtendedButton();
//			jButton_Cancel.setText("キャンセル");
//			jButton_Cancel.setVisible(false);
//			jButton_Cancel.addActionListener(this);
//		}
//		return jButton_Cancel;
//	}

	/**
	 * This method initializes jTextField_hokenjaNumber
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedTextField getJTextField_hokenjaNumber() {
		if (jTextField_hokenjaNumber == null) {

			jTextField_hokenjaNumber = new ExtendedTextField("",8,ImeMode.IME_OFF);
			jTextField_hokenjaNumber.setPreferredSize(new Dimension(75, 20));
			// add s.inoue 2012/11/27
			// jTextField_hokenjaNumber.addActionListener(this);
		}
		return jTextField_hokenjaNumber;
	}

	/**
	 * This method initializes jTextField_shiharaiNumber
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedTextField getJTextField_shiharaiNumber() {
		if (jTextField_daikouNumber == null) {
			jTextField_daikouNumber = new ExtendedTextField("",8,ImeMode.IME_OFF);
			jTextField_daikouNumber.setPreferredSize(new Dimension(75, 20));
		}
		return jTextField_daikouNumber;
	}

	/**
	 * This method initializes jTextField_MadoguchiKihon1
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_MadoguchiKihon1() {
		if (jTextField_KihonJougen == null) {
			jTextField_KihonJougen = new ExtendedOpenTextControl(
					"",
					6,
					ImeMode.IME_OFF);
			jTextField_KihonJougen.setPreferredSize(new Dimension(70, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_KihonJougen.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_KihonJougen.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_KihonJougen;
	}

	/**
	 * This method initializes jTextField_ShousaiJougen
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_ShousaiJougen() {
		if (jTextField_ShousaiJougen == null) {
			jTextField_ShousaiJougen = new ExtendedOpenTextControl(
					"",
					6,
					ImeMode.IME_OFF);
			jTextField_ShousaiJougen.setPreferredSize(new Dimension(70, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_ShousaiJougen.setHorizontalAlignment(JLabel.RIGHT);
			jTextField_ShousaiJougen.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_ShousaiJougen;
	}

	/**
	 * This method initializes jTextField_TsuikaJyougen
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_TsuikaJyougen() {
		if (jTextField_TsuikaJyougen == null) {
			jTextField_TsuikaJyougen = new ExtendedOpenTextControl(
					"",
					6,
					ImeMode.IME_OFF);
			jTextField_TsuikaJyougen.setPreferredSize(new Dimension(70, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_TsuikaJyougen.setHorizontalAlignment(JLabel.RIGHT);
			jTextField_TsuikaJyougen.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_TsuikaJyougen;
	}

	/**
	 * This method initializes jTextField_DockJougen
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_DockJougen() {
		if (jTextField_DockJougen == null) {
			jTextField_DockJougen = new ExtendedOpenTextControl(
					"",
					6,
					ImeMode.IME_OFF);
			jTextField_DockJougen.setPreferredSize(new Dimension(70, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_DockJougen.setHorizontalAlignment(JLabel.RIGHT);
			jTextField_DockJougen.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_DockJougen;
	}

	/**
	 * This method initializes jTextField_KihonSyubetsuNum
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_KihonSyubetsuNum() {
		if (jTextField_KihonSyubetsuNum == null) {
			jTextField_KihonSyubetsuNum = new ExtendedOpenTextControl(
					"",
					1,
					ImeMode.IME_OFF);
			jTextField_KihonSyubetsuNum.setColumns(1);
			jTextField_KihonSyubetsuNum.setVisible(false);

			jTextField_KihonSyubetsuNum.addActionListener(this);
			// del s.inoue 2010/05/10
//			jTextField_KihonSyubetsuNum.addKeyListener(this);
		}
		return jTextField_KihonSyubetsuNum;
	}

	/**
	 * This method initializes jTextField_SyousaiSyubetsuNum
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_SyousaiSyubetsuNum() {
		if (jTextField_SyousaiSyubetsuNum == null) {
			jTextField_SyousaiSyubetsuNum = new ExtendedOpenTextControl(
					"",
					1,
					ImeMode.IME_OFF);
			jTextField_SyousaiSyubetsuNum.setPreferredSize(new Dimension(20, 20));
			jTextField_SyousaiSyubetsuNum.setColumns(1);
			jTextField_SyousaiSyubetsuNum.setVisible(false);

			jTextField_SyousaiSyubetsuNum.addActionListener(this);
			// del s.inoue 2010/05/10
//			jTextField_SyousaiSyubetsuNum.addKeyListener(this);
		}
		return jTextField_SyousaiSyubetsuNum;
	}

	/**
	 * This method initializes jTextField_KihonTsuikaNum
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_KihonTsuikaNum() {
		if (jTextField_TsuikaSyubetsuNum == null) {
			jTextField_TsuikaSyubetsuNum = new ExtendedOpenTextControl(
					"",
					1,
					ImeMode.IME_OFF);
			jTextField_TsuikaSyubetsuNum.setPreferredSize(new Dimension(20, 20));
			jTextField_TsuikaSyubetsuNum.setColumns(1);
			jTextField_TsuikaSyubetsuNum.setVisible(false);

			jTextField_TsuikaSyubetsuNum.addActionListener(this);
			// del s.inoue 2010/05/10
//			jTextField_TsuikaSyubetsuNum.addKeyListener(this);
		}
		return jTextField_TsuikaSyubetsuNum;
	}

	/**
	 * This method initializes jTextField_DockSyubetsuNum
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_DockSyubetsuNum() {
		if (jTextField_DockSyubetsuNum == null) {
			jTextField_DockSyubetsuNum = new ExtendedOpenTextControl(
					"",
					1,
					ImeMode.IME_OFF);
			jTextField_DockSyubetsuNum.setPreferredSize(new Dimension(20, 20));
			jTextField_DockSyubetsuNum.setColumns(1);
			jTextField_DockSyubetsuNum.setVisible(false);

			jTextField_DockSyubetsuNum.addActionListener(this);
			// del s.inoue 2010/05/10
//			jTextField_DockSyubetsuNum.addKeyListener(this);
		}
		return jTextField_DockSyubetsuNum;
	}

	/**
	 * This method initializes jPanel_buttons
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_buttons() {
		if (jPanel_buttons == null) {
			// add s.inoue 2012/11/12
			GridBagConstraints gridBagConstraints120 = new GridBagConstraints();
			gridBagConstraints120.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints120.gridy = 0;
			gridBagConstraints120.gridx = 0;
			// eidt s.inoue 2012/11/12
			gridBagConstraints120.anchor = GridBagConstraints.WEST;
			gridBagConstraints120.weightx = 1.0D;

			GridBagConstraints gridBagConstraints125 = new GridBagConstraints();
			gridBagConstraints125.gridx = 2;
			gridBagConstraints125.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints125.gridy = 1;

			GridBagConstraints gridBagConstraints130 = new GridBagConstraints();
			gridBagConstraints130.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints130.gridy = 1;
			gridBagConstraints130.gridx = 0;
			// eidt s.inoue 2012/11/12
			gridBagConstraints130.anchor = GridBagConstraints.WEST;
			gridBagConstraints130.weightx = 1.0D;

			GridBagConstraints gridBagConstraints128 = new GridBagConstraints();
			gridBagConstraints128.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints128.gridy = 1;
			gridBagConstraints128.gridx = 7;
//			gridBagConstraints128.gridx = 1;
			gridBagConstraints128.anchor = GridBagConstraints.EAST;
			gridBagConstraints128.weightx = 1.0D;

//			GridBagConstraints gridBagConstraints129 = new GridBagConstraints();
//			gridBagConstraints129.insets = new Insets(0, 5, 0, 0);
////			gridBagConstraints129.gridy = -1;
//			gridBagConstraints129.gridy = 1;
//			gridBagConstraints129.gridx = 9;

			GridBagConstraints gridBagConstraints127 = new GridBagConstraints();
			gridBagConstraints127.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints127.gridy = -1;
			gridBagConstraints127.gridy = 1;
			gridBagConstraints127.gridx = 8;
//			gridBagConstraints127.weightx = 1.0D;
			// gridBagConstraints127.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints127.insets = new Insets(0, 0, 0, 5);
//			gridBagConstraints127.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints126 = new GridBagConstraints();
			gridBagConstraints126.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints126.gridy = 1;
			gridBagConstraints126.gridx = 5;
			// gridBagConstraints126.gridwidth = 1;

//			GridBagConstraints gridBagConstraints124 = new GridBagConstraints();
//			gridBagConstraints124.insets = new Insets(0, 5, 0, 0);
////			gridBagConstraints124.gridy = -1;
//			gridBagConstraints124.gridy = 1;
////			gridBagConstraints124.weightx = 1.0D;
////			gridBagConstraints124.anchor = GridBagConstraints.WEST;
//			gridBagConstraints124.gridx = 6;

			GridBagConstraints gridBagConstraints123 = new GridBagConstraints();
			gridBagConstraints123.gridx = 3;
			gridBagConstraints123.insets = new Insets(0, 5, 0, 0);
			// eidt s.inoue 2011/03/29
			// gridBagConstraints123.weightx = 1.0D;
//			gridBagConstraints123.gridy = -1;
			gridBagConstraints123.gridy = 1;

			jPanel_buttons = new JPanel();
			jPanel_buttons.setLayout(new GridBagLayout());
			jPanel_buttons.add(getJButton_Print(), gridBagConstraints126);
//			jPanel_buttons.add(getJButton_QR(), gridBagConstraints124);
			jPanel_buttons.add(getJButton_Call(), gridBagConstraints123);
			jPanel_buttons.add(getJButton_Register(), gridBagConstraints127);
//			jPanel_buttons.add(getJButton_Cancel(), gridBagConstraints129);
			jPanel_buttons.add(getJButton_Clear(), gridBagConstraints128);
			jPanel_buttons.add(getJButton_End(), gridBagConstraints130);
			jPanel_buttons.add(getJButton_CallHokenjya(), gridBagConstraints125);
			
			// edit n.ohkubo 2015/08/01　追加　start
			GridBagConstraints gridBagConstraints131 = new GridBagConstraints();
			gridBagConstraints131.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints131.gridy = 1;
			gridBagConstraints131.gridx = 6;
			jPanel_buttons.add(getJButton_Print_tsuikakenshin(), gridBagConstraints131);
			// edit n.ohkubo 2015/08/01　追加　end

			// add s.inoue 2012/11/12
			// add s.inoue 2012/11/12
			jLabel_Title = new TitleLabel("tokutei.jushinken.frame.title","tokutei.jushinken.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

			jPanel_buttons.add(getJPanel_TitleArea(),gridBagConstraints120);
		}
		return jPanel_buttons;
	}

	// add s.inoue 2012/11/12
	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TitleArea() {
		if (jPanel_TitleArea == null) {
//			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
//			gridBagConstraints26.insets = new Insets(5, 0, 0, 0);
//			gridBagConstraints26.gridy = 1;
//			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints26.gridx = 0;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
//			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.fill = GridBagConstraints.BOTH;
			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;
			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
			// jPanel_TitleArea.add(getJPanel_ExplArea1(), gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

	/**
	 * This method initializes jTextField_sonotaHutangaku
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedOpenTextControl
	 */
	private ExtendedOpenTextControl getJTextField_sonotaHutangaku() {
		if (jTextField_sonotaHutangaku == null) {
			jTextField_sonotaHutangaku = new ExtendedOpenTextControl(
					"",
					9,
					ImeMode.IME_OFF);
			jTextField_sonotaHutangaku.setPreferredSize(new Dimension(70, 20));
			// eidt s.inoue 2011/12/13
			// jTextField_sonotaHutangaku.setHorizontalAlignment(JTextField.RIGHT);
			jTextField_sonotaHutangaku.setAlignmentX(ExtendedOpenTextControl.RIGHT_ALIGNMENT);
		}
		return jTextField_sonotaHutangaku;
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent arg0) {
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
	}
	@Override
	public void focusGained(FocusEvent arg0) {
	}
	@Override
	public void focusLost(FocusEvent arg0) {
	}

	/**
	 * This method initializes jPanel8
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			GridBagConstraints gridBagConstraints74 = new GridBagConstraints();
			gridBagConstraints74.anchor = GridBagConstraints.WEST;
			gridBagConstraints74.gridy = 0;
			gridBagConstraints74.weightx = 1.0;
			gridBagConstraints74.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints74.gridx = 0;
			jPanel8 = new JPanel();
			jPanel8.setLayout(new GridBagLayout());
			jPanel8.add(getJTextField_ORCAID(), gridBagConstraints74);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jButton_CallHokenjya
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_CallHokenjya() {
		if (jButton_CallHokenjya == null) {
			jButton_CallHokenjya = new ExtendedButton();
			jButton_CallHokenjya.setText("保険者呼出");
			jButton_CallHokenjya.setVisible(false);
			jButton_CallHokenjya.addActionListener(this);
		}
		return jButton_CallHokenjya;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"


