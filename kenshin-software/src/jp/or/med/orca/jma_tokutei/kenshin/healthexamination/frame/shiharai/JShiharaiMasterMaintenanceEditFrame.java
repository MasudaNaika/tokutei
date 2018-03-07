package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFormattedControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

import javax.swing.BorderFactory;
import java.awt.Insets;

/**
 * 保険者マスタメンテナンス画面
 */
public class JShiharaiMasterMaintenanceEditFrame
extends JFrame
implements ActionListener,FocusListener {

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
	protected JPanel jPanel_DaikouNumber2_1 = null;
	protected JPanel jPanel_DaikouNumber2_2 = null;
	protected JPanel jPanel_DaikouNumber2_3 = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedOpenTextControl jTextField_DaikouNumber = null;
	protected ExtendedOpenTextControl jTextField_DaikouName = null;
	protected ExtendedOpenFormattedControl jTextField_Zipcode = null;
	protected ExtendedOpenTextControl jTextField_Address = null;
	protected ExtendedOpenTextControl jTextField_TEL = null;
	protected ExtendedButton jButton_ORCA = null;
	protected ExtendedButton jButton_Register = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel11 = null;
	protected ExtendedLabel jLabel13 = null;
	protected ExtendedLabel jLabel14 = null;
	protected ExtendedLabel jLabel15 = null;
	protected ExtendedLabel jLabel16 = null;
	protected ExtendedLabel jLabel18 = null;
	protected ExtendedLabel jLabel112 = null;
	protected ExtendedLabel jLabel113 = null;
	protected ExtendedLabel jLabel114 = null;
	protected ExtendedLabel jLabel115 = null;
	protected ExtendedLabel jLabel116 = null;
	protected ExtendedLabel jLabel120 = null;
	protected ExtendedLabel jLabel121 = null;
	protected ExtendedLabel jLabel123 = null;
	protected ExtendedLabel jLabel124 = null;
	protected ExtendedButton jButton_Cancel = null;
	protected ExtendedLabel jLabel1231 = null;
	protected ExtendedLabel jLabel1232 = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel3 = null;
	protected JPanel jPanel4 = null;
	protected JPanel jPanel5 = null;
	protected JPanel jPanel6 = null;
	protected JPanel jPanel8 = null;
	protected JPanel jPanel9 = null;
	protected JPanel jPanel7 = null;
	protected ExtendedButton jButton_Clear = null;
	/**
	 * This is the default constructor
	 */
	public JShiharaiMasterMaintenanceEditFrame() {
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

			// add s.inoue 2012/11/12
			jLabel_Title = new TitleLabel("tokutei.shiharai-mastermaintenance-edit.frame.title","tokutei.shiharai-mastermaintenance-edit.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

			// jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
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
			// add s.inoue 2012/11/13
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridy = 0;
			gridBagConstraints53.gridx = 0;
			gridBagConstraints53.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints53.anchor = GridBagConstraints.WEST;
			gridBagConstraints53.gridwidth=5;

			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints9.gridy = 1;

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.gridx = 1;

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 1;
			// edit 20110317
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints6.gridx = 2;
			// edit 20110317
//			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.gridheight = -1;
//			gridBagConstraints5.gridx = 1;
//			gridBagConstraints5.gridy = 1;
//			gridBagConstraints5.ipadx = -75;
//			gridBagConstraints5.ipady = -25;
//			gridBagConstraints5.gridwidth = -1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
//			jPanel_ButtonArea.add(getJButton_ORCA(), gridBagConstraints5);
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints6);
//			jPanel_ButtonArea.add(getJButton_Cancel(), gridBagConstraints7);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints8);
			jPanel_ButtonArea.add(getJButton_Clear(), gridBagConstraints9);

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
			// edit 20110317
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"Nitirese","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
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
//			jLabel_MainExpl.setText("代行機関番号を入力後、Enterキーを押して代行機関情報を作成します。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new ExtendedLabel();
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_SHIHARAI_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
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

	/**
	 * This method initializes jPanel_ExplArea2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ExplArea2() {
		if (jPanel_ExplArea2 == null) {
			jLabal_SubExpl = new ExtendedLabel();
			jLabal_SubExpl.setText("各項目を入力後、「登録」ボタンを押して入力内容を登録します。");
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
			jPanel_MainArea.add(getJPanel_DrawArea(), BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.ipadx = 10;
			gridBagConstraints13.ipady = 10;
			gridBagConstraints13.gridy = 8;
			GridBagConstraints gridBagConstraints410 = new GridBagConstraints();
			gridBagConstraints410.gridx = 2;
			gridBagConstraints410.weightx = 1.0D;
			gridBagConstraints410.gridy = 0;
			GridBagConstraints gridBagConstraints310 = new GridBagConstraints();
			gridBagConstraints310.gridx = 0;
			gridBagConstraints310.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints310.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints310.gridy = 0;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 1;
			gridBagConstraints18.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints18.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints18.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints18.gridy = 0;
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
			gridBagConstraints82.gridx = 1;
			gridBagConstraints82.fill = GridBagConstraints.BOTH;
			gridBagConstraints82.gridy = 9;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 1;
			gridBagConstraints71.fill = GridBagConstraints.BOTH;
			gridBagConstraints71.gridy = 18;
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 1;
			gridBagConstraints51.fill = GridBagConstraints.BOTH;
			gridBagConstraints51.gridy = 17;
			GridBagConstraints gridBagConstraints49 = new GridBagConstraints();
			gridBagConstraints49.gridx = 1;
			gridBagConstraints49.fill = GridBagConstraints.BOTH;
			gridBagConstraints49.gridy = 16;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 14;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.ipady = 20;
			gridBagConstraints4.ipadx = 10;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.gridy = 8;
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
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.gridx = 2;
			gridBagConstraints40.gridy = 16;
			gridBagConstraints40.anchor = GridBagConstraints.EAST;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.gridx = 2;
			gridBagConstraints39.gridy = 12;
			gridBagConstraints39.anchor = GridBagConstraints.EAST;
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.gridx = 2;
			gridBagConstraints38.gridy = 10;
			gridBagConstraints38.anchor = GridBagConstraints.EAST;
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
			jLabel18 = new ExtendedLabel();
			jLabel18.setText("電話番号");
			jLabel18.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel16 = new ExtendedLabel();
			jLabel16.setText("所在地");
			jLabel16.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel15 = new ExtendedLabel();
			jLabel15.setText("郵便番号");
			jLabel15.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel14 = new ExtendedLabel();
			jLabel14.setText("代行機関名称");
			jLabel14.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel13 = new ExtendedLabel();
			jLabel13.setText("代行機関番号");
			jLabel13.setFont(new Font("Dialog", Font.PLAIN, 12));
//			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
//			gridBagConstraints27.gridx = 1;
//			gridBagConstraints27.gridy = 15;
//			jLabel11 = new ExtendedLabel();
//			jLabel11.setText(" ");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 10;
			jLabel = new ExtendedLabel();
			jLabel.setText(" ");
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.gridx = 3;
			gridBagConstraints25.gridy = 16;
			gridBagConstraints25.weightx = 1.0;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints24.gridx = 3;
			gridBagConstraints24.gridy = 12;
			gridBagConstraints24.weightx = 1.0;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.gridx = 3;
			gridBagConstraints23.gridy = 10;
			gridBagConstraints23.weightx = 1.0;
			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new GridBagLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");
			jPanel_DrawArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			jPanel_DrawArea.add(jLabel112, gridBagConstraints38);
//			jPanel_DrawArea.add(getJTextField_HinketuCode(), gridBagConstraints23);
			jPanel_DrawArea.add(jLabel113, gridBagConstraints39);
//			jPanel_DrawArea.add(getJTextField_SindenzuCode(), gridBagConstraints24);
			jPanel_DrawArea.add(jLabel114, gridBagConstraints40);
//			jPanel_DrawArea.add(getJTextField_GanteiCode(), gridBagConstraints25);
			jPanel_DrawArea.add(jLabel, gridBagConstraints);
//			jPanel_DrawArea.add(jLabel11, gridBagConstraints27);
			jPanel_DrawArea.add(getJPanel(), gridBagConstraints4);
			jPanel_DrawArea.add(getJPanel1(), gridBagConstraints3);
			jPanel_DrawArea.add(getJPanel2(), gridBagConstraints49);
			jPanel_DrawArea.add(getJPanel3(), gridBagConstraints51);
			jPanel_DrawArea.add(getJPanel4(), gridBagConstraints71);
			jPanel_DrawArea.add(getJPanel5(), gridBagConstraints82);
			jPanel_DrawArea.add(getJPanel6(), gridBagConstraints18);
			jPanel_DrawArea.add(getJPanel8(), gridBagConstraints310);
			jPanel_DrawArea.add(getJPanel9(), gridBagConstraints410);
			jPanel_DrawArea.add(getJPanel7(), gridBagConstraints13);
		}
		return jPanel_DrawArea;
	}

	/**
	 * This method initializes jTextField_HokenjyaNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedOpenTextControl getJTextField_DaikouNumber() {
		if (jTextField_DaikouNumber == null) {
			// jTextField_DaikouNumber = new ExtendedTextField("", 8, ImeMode.IME_OFF, true);
			jTextField_DaikouNumber = new ExtendedOpenTextControl("", 8, ImeMode.IME_OFF);
			jTextField_DaikouNumber.setPreferredSize(new Dimension(100, 20));
			jTextField_DaikouNumber.addActionListener(this);
			jTextField_DaikouNumber.addFocusListener(this);
		}
		return jTextField_DaikouNumber;
	}

	/**
	 * This method initializes jTextField_HokenjyaName
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedOpenTextControl getJTextField_DaikouName() {
		if (jTextField_DaikouName == null) {
			// jTextField_DaikouName = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			jTextField_DaikouName = new ExtendedOpenTextControl("",200,ImeMode.IME_ZENKAKU);
			jTextField_DaikouName.setPreferredSize(new Dimension(360, 20));
		}
		return jTextField_DaikouName;
	}

	/**
	 * This method initializes jTextField_Zipcode
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedOpenFormattedControl getJTextField_Zipcode() {
		if (jTextField_Zipcode == null) {
			jTextField_Zipcode = new ExtendedOpenFormattedControl("", 7, ImeMode.IME_OFF);
			jTextField_Zipcode.setPostCodeFormat();
			jTextField_Zipcode.addFocusListener(this);
		}
		return jTextField_Zipcode;
	}

	/**
	 * This method initializes jTextField_Address
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedOpenTextControl getJTextField_Address() {
		if (jTextField_Address == null) {
			// jTextField_Address = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			jTextField_Address = new ExtendedOpenTextControl("",200,ImeMode.IME_ZENKAKU);
			jTextField_Address.setPreferredSize(new Dimension(360, 20));
		}
		return jTextField_Address;
	}

	/**
	 * This method initializes jTextField_TEL
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedOpenTextControl getJTextField_TEL() {
		if (jTextField_TEL == null) {
			// eidt s.inoue 2012/07/10
			// jTextField_TEL = new ExtendedTextField("", 11, ImeMode.IME_OFF);
			// eidt s.inoue 2013/03/09
			jTextField_TEL = new ExtendedOpenTextControl("", 11,ImeMode.IME_OFF);
			// jTextField_TEL = new ExtendedOpenTextControl(11,ImeMode.IME_OFF);
			jTextField_TEL.setPreferredSize(new Dimension(360, 20));
		}
		return jTextField_TEL;
	}

	/**
	 * This method initializes jButton_ORCA
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ORCA() {
		if (jButton_ORCA == null) {
			// edit 20110316
//			jButton_ORCA = new ExtendedButton();
//			jButton_ORCA.setText("日レセ読込");
//			jButton_ORCA.setVisible(false);
//			jButton_ORCA.addActionListener(this);
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_ORCA= new ExtendedButton(
					"Nitirese","日レセ読込(N)","日レセ読込(ALT+N)",KeyEvent.VK_N,icon);
			jButton_ORCA.addActionListener(this);
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
			// edit 20110316
//			jButton_Register = new ExtendedButton();
//			jButton_Register.setText("登録(F12)");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Register.addActionListener(this);
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Register= new ExtendedButton(
					"Save","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
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
	 * This method initializes jButton_Cancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			// edit 20110316
//			jButton_Cancel = new ExtendedButton();
//			jButton_Cancel.setText("キャンセル");
//			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Cancel.setVisible(false);
//			jButton_Cancel.addActionListener(this);
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Cancel= new ExtendedButton(
					"Cancel","キャンセル(C)","キャンセル(ALT+C)",KeyEvent.VK_C,icon);
			jButton_Cancel.addActionListener(this);
		}
		return jButton_Cancel;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setPreferredSize(new Dimension(450, 0));
		}
		return jPanel;
	}

	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
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

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
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
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
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
//			jPanel6.add(getJTextField_HonninGairai(), gridBagConstraints19);
			jPanel6.add(jLabel1231, gridBagConstraints11);
			jPanel6.add(jLabel116, gridBagConstraints27);
			jPanel6.add(jLabel120, gridBagConstraints28);
			jPanel6.add(jLabel121, gridBagConstraints41);
//			jPanel6.add(getJTextField_KazokuGairai(), gridBagConstraints20);
//			jPanel6.add(getJTextField_HonninNyuin(), gridBagConstraints42);
//			jPanel6.add(getJTextField_KazokuNyuin(), gridBagConstraints22);
			jPanel6.add(jLabel1232, gridBagConstraints21);
			jPanel6.add(jLabel123, gridBagConstraints1);
			jPanel6.add(jLabel124, gridBagConstraints2);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel8
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.gridy = 5;
			gridBagConstraints12.weighty = 1.0D;
			gridBagConstraints12.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 3;
			gridBagConstraints10.gridwidth = 1;
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			gridBagConstraints48.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints48.gridy = 2;
			gridBagConstraints48.gridx = 1;
			GridBagConstraints gridBagConstraints47 = new GridBagConstraints();
			gridBagConstraints47.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints47.gridx = 1;
			gridBagConstraints47.gridy = 1;
			gridBagConstraints47.gridwidth = 1;
			GridBagConstraints gridBagConstraints46 = new GridBagConstraints();
			gridBagConstraints46.gridy = 0;
			gridBagConstraints46.weightx = 1.0D;
			gridBagConstraints46.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints46.gridx = 1;
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints34.gridy = 5;
			gridBagConstraints34.gridx = 0;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.anchor = GridBagConstraints.WEST;
			gridBagConstraints32.gridy = 3;
			gridBagConstraints32.gridx = 0;
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.anchor = GridBagConstraints.WEST;
			gridBagConstraints31.gridy = 2;
			gridBagConstraints31.gridx = 0;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.anchor = GridBagConstraints.WEST;
			gridBagConstraints30.gridy = 1;
			gridBagConstraints30.gridx = 0;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.anchor = GridBagConstraints.WEST;
			gridBagConstraints29.gridy = 0;
			gridBagConstraints29.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints29.gridx = 0;
			jPanel8 = new JPanel();
			jPanel8.setLayout(new GridBagLayout());
			jPanel8.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel8.add(jLabel13, gridBagConstraints29);
			jPanel8.add(jLabel14, gridBagConstraints30);
			jPanel8.add(jLabel15, gridBagConstraints31);
			jPanel8.add(jLabel16, gridBagConstraints32);
			jPanel8.add(jLabel18, gridBagConstraints34);
			jPanel8.add(getJTextField_DaikouNumber(), gridBagConstraints46);
			jPanel8.add(getJTextField_DaikouName(), gridBagConstraints47);
			jPanel8.add(getJTextField_Zipcode(), gridBagConstraints48);
			jPanel8.add(getJTextField_Address(), gridBagConstraints10);
			jPanel8.add(getJTextField_TEL(), gridBagConstraints12);
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
	 * This method initializes jButton_Clear
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_Clear() {
		if (jButton_Clear == null) {
//			jButton_Clear = new ExtendedButton();
//			jButton_Clear.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Clear.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_Clear.setText("クリア(F2)");
//			jButton_Clear.setActionCommand("\u7d42\u4e86");
//			jButton_Clear.addActionListener(this);
			// 閉じるbutton
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Clear= new ExtendedButton(
					"Clear","クリア(E)","クリア(ALT+E)",KeyEvent.VK_E,icon);
			jButton_Clear.addActionListener(this);
		}
		return jButton_Clear;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
