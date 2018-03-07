package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedDateChooser;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenComboboxControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenDnDTabbedPane;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenLabel;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenLabelControl;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;

/**
 * 登録画面
 * 概要:健診パターンにより、動的コントロール配置を行う
 */
public class JRegisterFlame extends JFrame implements ActionListener, KeyListener,MouseListener
{
	protected static final long serialVersionUID = 1L;

	// タブ構成のパネル群
	protected JPanel jMainPanel = null;
	protected JPanel jPanelCenter = null;
	protected JPanel jInnerPanelKenshin = null;
	protected JPanel jInnerPanelSyosai = null;
	protected JPanel jInnerPanelTuika = null;
	protected JPanel jInnerPanelMonshin = null;
	protected JPanel jInnerPanelMyTab = null;

	protected JPanel jInnerTranPanelKenshin = null;
	protected JPanel jInnerTranPanelSyosai = null;
	protected JPanel jInnerTranPanelTuika = null;
	protected JPanel jInnerTranPanelMonshin = null;
	protected JPanel jInnerTranPanelMyTab = null;

	protected JPanel jPanelLeft = null;
	protected JPanel jPanelInnerLeftTop = null;
	protected JPanel jPanelInnerLeftDown_Top = null;
	protected JPanel jPanelInnerLeftDown_Down = null;
	protected JPanel jPanelInnerLeftCenter = null;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel = null;

	// コントロール群
	protected ExtendedButton jButton_End = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected ExtendedOpenLabelControl jLabal_SubExpl = null;

	// ボタン群
	protected ExtendedButton jButton_Setting = null;
	protected ExtendedButton jButton_Clear = null;
	protected ExtendedButton jButton_Register = null;
//	protected ExtendedButton jButton_PrintRyoshu = null;
//	protected ExtendedButton jButton_mytabClear = null;

	// ドロップダウンタブ
	protected ExtendedOpenDnDTabbedPane jPanelRegisterCenter = null;
//	protected ExtendedOpenDnDTabbedPane jTabRegisterCenter3 = null;
	protected JPanel jPanelRegisterCenterTitleTran = null;

	// 左上パネル
	protected ExtendedOpenLabel jLabel_KenshinJisiDay = null;
	protected ExtendedOpenLabel jLabel_KensinPattern = null;
	protected ExtendedOpenLabel jLabel_HihokenjyaKigo = null;
	protected ExtendedOpenLabel jLabel_HihokenjyaNo = null;
	protected ExtendedOpenLabel jLabel_JusinsyaNm = null;
	protected ExtendedOpenLabel jLabel_Sex = null;
	protected ExtendedOpenLabel jLabel_Nenrei = null;
	protected ExtendedOpenLabel jLabel_BirthDay = null;
	protected ExtendedOpenLabel jLabel_JusinSeiriNo = null;
	protected ExtendedOpenLabel jLabel_SeikyuKBN = null;
	protected ExtendedOpenLabel jLabel_SeiriNO = null;

	protected ExtendedOpenLabel jLabel_MetaboHantei = null;
	protected ExtendedOpenLabel jLabel_Hokensidou = null;
	protected ExtendedOpenLabel jLabel_SougouComment = null;

	protected ExtendedOpenLabel jLabel_KensaKoumoku = null;
	protected ExtendedOpenLabel jLabel_KensaHouhou = null;
	protected ExtendedOpenLabel jLabel_KensaHouhou_Value = null;

	protected ExtendedLabel jLabel_HiHokenjyaCode = null;
	protected ExtendedLabel jLabel_HiHokenjyaNumber = null;
//	protected ExtendedTextField jTextField_Date = null;
	protected ExtendedLabel jLabel_Name = null;
	protected ExtendedLabel jLabel_Field_Sex = null;
	protected ExtendedLabel jLabel_Field_Nenrei = null;
	protected ExtendedLabel jLable_Field_BirthDate = null;
	protected ExtendedLabel jLabel_Field_JusinSeiriNo = null;
	protected ExtendedDateChooser jTextField_KenshinJisiDay = null;
	protected ExtendedOpenTextControl jTextField_SeiriNo = null;
	protected ExtendedTextArea jTextArea_SougouComment = null;
//	protected ExtendedComboBox jComboBox_SeikyuKubun = null;
//	protected JComboBox jComboBox_SeikyuKubun = null;
//	protected ListVOControl jComboBox_SeikyuKubun = null;
//	protected ListVOControl jComboBox_SeikyuKubun = new ListVOControl();

	protected ExtendedOpenComboboxControl jComboBox_SeikyuKubun = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
	protected ExtendedOpenComboboxControl jComboBox_Pattern = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
	protected ExtendedOpenComboboxControl jComboBox_MetaboHantei = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
	protected ExtendedOpenComboboxControl jComboBox_HokenSidouLevel = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
//	protected static Color backColor_Focus =  new Color(195,229,254);
//	protected static Color backColor_UnFocus =  new Color(255,255,255);  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public JRegisterFlame() {
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
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation()+"|結果登録画面");
		this.setPreferredSize(ViewSettings.getFrameCommonSize());
		this.setMinimumSize(ViewSettings.getFrameCommonSize());
		this.setLocationRelativeTo( null );
	}

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
//	/**
//	 * This method initializes jPanel_ExplArea2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea2() {
//		if (jPanel_ExplArea2 == null) {
//			jLabelSub2Expl = new ExtendedLabel();
//			jLabelSub2Expl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabelSub2Expl.setText("総合コメント欄・結果(文字列)欄でCtrlキー+Enterキー押下して入力ウインドウを表示します。");
//			GridLayout gridLayout1 = new GridLayout();
//			gridLayout1.setRows(2);
//			jPanel_ExplArea2 = new JPanel();
//			jPanel_ExplArea2.setName("jPanel4");
//			jPanel_ExplArea2.setLayout(gridLayout1);
//
//			jLabel_MainExpl = new ExtendedLabel();
//			jLabel_MainExpl.setText("健診パターンを選択して、健診項目を表示します。値を入力して登録ボタンを押してください。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//
//			jPanel_ExplArea2.add(jLabel_MainExpl, null);
//			jPanel_ExplArea2.add(jLabelSub2Expl, null);
//		}
//		return jPanel_ExplArea2;
//	}

	// add s.inoue 2012/11/12 ↑↑↑↑↑↑↑↑↑↑

	/**
	 * This method initializes jPanel_Content
	 *
	 * @return javax.swing.JPanel
	 */
	protected ExtendedLabel jLabel_Title = null;
	protected ExtendedLabel jLabelSub2Expl = null;

	private JPanel getJPanel_Content() {
		if (jPanel_Content == null) {
			BorderLayout borderLayout = new BorderLayout();
//			borderLayout.setVgap(2);
			jPanel_Content = new JPanel();
			jPanel_Content.setLayout(borderLayout);
			// add s.inoue 2012/11/12
			jLabel_Title = new TitleLabel("tokutei.kekkainput.frame.title");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));
			// jPanel_Content.add(getJPanel_TitleArea(), BorderLayout.NORTH);

			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJMainPanel(),BorderLayout.CENTER);
		}
		return jPanel_Content;
	}


	/*
	 * ButtonArea
	 */
	private JPanel getJPanel_ButtonArea() {
		if (jPanel_ButtonArea == null) {
			// eidt s.inoue 2012/11/12 y:0→1
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.gridx = 0;

			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.weightx = 1.0D;
			// add s.inoue 2012/01/20
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());

			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints7);
			jPanel_ButtonArea.add(getJButton_Clear(), gridBagConstraints9);
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints6);
			// add s.inoue 2012/01/20
//			jPanel_ButtonArea.add(getJButton_PrintRyoshu(), gridBagConstraints8);

			// add s.inoue 2012/11/12
			jPanel_ButtonArea.add(getJPanel_TitleArea(),gridBagConstraints5);
		}
		return jPanel_ButtonArea;
	}

	/*
	 * メインパネル (基本情報エリア,登録エリア,参照エリア)
	 */
	private JPanel getJMainPanel(){
		if (jMainPanel == null){
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 1;
//			gridBagConstraints1.weightx = 1.0;
//			gridBagConstraints1.weighty = 1.0;

//			gridBagConstraints1.anchor = GridBagConstraints.WEST;
//			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridheight = 1;
			gridBagConstraints1.weightx = 1.0d;
			gridBagConstraints1.weighty = 1.0d;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;

			// パネル毎に上下の伸び設定を行う
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 2;
//			gridBagConstraints2.weightx = 1.0;
//			gridBagConstraints2.weighty = 1.0;

//			gridBagConstraints2.anchor = GridBagConstraints.WEST;
//			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
//			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridheight = 1;
			gridBagConstraints2.weightx = 1.0d;
			gridBagConstraints2.weighty = 1.0d;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;


			jMainPanel = new JPanel();
			jMainPanel.setLayout(new GridBagLayout());
			jMainPanel.setName("jPanel_MainPanel");
//			jMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jMainPanel.setOpaque(false);
			jMainPanel.add(getJPanelLeft(), gridBagConstraints1);
			jMainPanel.add(getJPanelRegisterCenter(), gridBagConstraints2);
		}
		return jMainPanel;
	}

	/*
	 * Panel Left
	 */
	private JPanel getJPanelLeft() {
//		int splitLeftArea = 45;
//		int splitLeftAreaDown = 5;

		int leftAreaWidth = 250;
		int leftAreaHight = 550;


		if (jPanelLeft == null) {

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 2);
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
//			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridheight = 1;
			gridBagConstraints1.weightx = 1.0d;
			gridBagConstraints1.weighty = 1.0d;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.insets = new Insets(2, 5, 0, 2);
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
//			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridheight = 1;
			gridBagConstraints2.weightx = 1.0d;
			gridBagConstraints2.weighty = 1.0d;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.insets = new Insets(2, 5, 2, 2);
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
//			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridheight = 1;
			gridBagConstraints3.weightx = 1.0d;
			gridBagConstraints3.weighty = 1.0d;

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 3;
			gridBagConstraints4.insets = new Insets(2, 5, 2, 2);
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
//			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridheight = 1;
			gridBagConstraints4.weightx = 1.0d;
			gridBagConstraints4.weighty = 1.0d;

			jPanelLeft = new JPanel();
			jPanelLeft.setLayout(new GridBagLayout());
			jPanelLeft.setName("jInnerPanelLeft");
//			jPanelLeft.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanelLeft.setOpaque(false);
			jPanelLeft.setPreferredSize(new Dimension(leftAreaWidth, leftAreaHight));

			jPanelLeft.add(getJPanelInnerLeftTop(), gridBagConstraints1);
			jPanelLeft.add(getJPanelInnerLeftCenter(), gridBagConstraints2);
			jPanelLeft.add(getJPanelInnerLeftDown_Top(), gridBagConstraints3);
			jPanelLeft.add(getJPanelInnerLeftDown_Down(), gridBagConstraints4);

		}
		return jPanelLeft;
	}

	/* Panel LeftTop top */

	/*
	 * Panel LeftTop
	 */
	private JPanel getJPanelInnerLeftTop() {
		if (jPanelInnerLeftTop == null) {

			GridBagConstraints[] gridBagConstraintsR = null;
			GridBagConstraints[] gridBagConstraintsL = null;
			gridBagConstraintsL = new GridBagConstraints[8];
			gridBagConstraintsR = new GridBagConstraints[8];

			for (int i = 0; i < 8; i++) {
				gridBagConstraintsR[i] = new GridBagConstraints();
				gridBagConstraintsR[i].anchor = GridBagConstraints.WEST;
				gridBagConstraintsR[i].fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraintsR[i].weightx = 1.0;
				gridBagConstraintsR[i].gridx = 0;
				gridBagConstraintsR[i].gridy = i;

				gridBagConstraintsL[i] = new GridBagConstraints();
				gridBagConstraintsL[i].anchor = GridBagConstraints.WEST;
				gridBagConstraintsL[i].fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraintsL[i].weightx = 1.0;
				gridBagConstraintsL[i].gridx = 1;
				gridBagConstraintsL[i].gridy = i;
			}

//			jLabel_SeiriNO = new ExtendedOpenLabel();
//			jLabel_SeiriNO.setText("整理番号");
//			jLabel_SeiriNO.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel_SeiriNO.setHorizontalAlignment(SwingConstants.LEFT);

//			jLabel_KenshinJisiDay = new ExtendedLabel();
//			jLabel_KenshinJisiDay.setText("実施日");
//			jLabel_KenshinJisiDay.setFont(new Font("Dialog", Font.PLAIN, 12));

//			jLabel_KensinPattern = new ExtendedLabel();
//			jLabel_KensinPattern.setText("健診パターン");
//			jLabel_KensinPattern.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_HihokenjyaKigo = new ExtendedOpenLabel();
			jLabel_HihokenjyaKigo.setText("被保険者証記号");
			jLabel_HihokenjyaKigo.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_HihokenjyaNo = new ExtendedOpenLabel();
			jLabel_HihokenjyaNo.setText("被保険者証番号");
			jLabel_HihokenjyaNo.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_JusinsyaNm = new ExtendedOpenLabel();
//			jLabel_JusinsyaNm.setText("氏名");		// edit n.ohkubo 2015/03/01　削除
			jLabel_JusinsyaNm.setText("氏名（カナ）");	// edit n.ohkubo 2015/03/01　追加
			jLabel_JusinsyaNm.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_Sex = new ExtendedOpenLabel();
			jLabel_Sex.setText("性別");
			jLabel_Sex.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_Nenrei = new ExtendedOpenLabel();
			jLabel_Nenrei.setText("年齢");
			jLabel_Nenrei.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_BirthDay = new ExtendedOpenLabel();
			jLabel_BirthDay.setText("生年月日");
			jLabel_BirthDay.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_JusinSeiriNo = new ExtendedOpenLabel();
			jLabel_JusinSeiriNo.setText("受診券整理番号");
			jLabel_JusinSeiriNo.setFont(new Font("Dialog", Font.PLAIN, 12));

//			jLabel_SeikyuKBN = new ExtendedLabel();
//			jLabel_SeikyuKBN.setText("請求区分");

			jPanelInnerLeftTop = new JPanel();
			jPanelInnerLeftTop.setLayout(new GridBagLayout());
			jPanelInnerLeftTop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			// 氏名,性別,生年月日,年齢,被保険者記号,被保険者番号,実施日,健診パターン,請求区分
			jPanelInnerLeftTop.add(jLabel_JusinsyaNm, gridBagConstraintsR[0]);
			jPanelInnerLeftTop.add(getJTextField_Name(), gridBagConstraintsL[0]);
			jPanelInnerLeftTop.add(jLabel_Sex, gridBagConstraintsR[1]);
			jPanelInnerLeftTop.add(getJTextField_Sex(), gridBagConstraintsL[1]);
			jPanelInnerLeftTop.add(jLabel_BirthDay, gridBagConstraintsR[2]);
			jPanelInnerLeftTop.add(getJTextField_BirthDay(), gridBagConstraintsL[2]);
			jPanelInnerLeftTop.add(jLabel_Nenrei, gridBagConstraintsR[3]);
			jPanelInnerLeftTop.add(getJTextField_Nenrei(), gridBagConstraintsL[3]);
			jPanelInnerLeftTop.add(jLabel_HihokenjyaKigo, gridBagConstraintsR[4]);
			jPanelInnerLeftTop.add(getJTextField_HokenjyaCode(), gridBagConstraintsL[4]);
			jPanelInnerLeftTop.add(jLabel_HihokenjyaNo, gridBagConstraintsR[5]);
			jPanelInnerLeftTop.add(getJTextField_HokenjyaNumber(), gridBagConstraintsL[5]);
			jPanelInnerLeftTop.add(jLabel_JusinSeiriNo, gridBagConstraintsR[6]);
			jPanelInnerLeftTop.add(getJTextField_JusinkenSeiriNo(), gridBagConstraintsL[6]);
//			jPanelInnerLeftTop.add(jLabel_KenshinJisiDay, gridBagConstraintsR[6]);
//			jPanelInnerLeftTop.add(getJTextField_KenshinJisiDay(), gridBagConstraintsL[6]);
//			jPanelInnerLeftTop.add(jLabel_KensinPattern, gridBagConstraintsR[7]);
//			jPanelInnerLeftTop.add(getJComboBox_Pattern(), gridBagConstraintsL[7]);
//			jPanelInnerLeftTop.add(jLabel_SeikyuKBN, gridBagConstraintsR[8]);
//			jPanelInnerLeftTop.add(getJComboBox_SeikyuKubun(), gridBagConstraintsL[8]);

		    jComboBox_SeikyuKubun.setCanCopy(true);
		    jComboBox_SeikyuKubun.setAttributeName("SeikyuKBM_Level");
		    jComboBox_SeikyuKubun.setDomainId("SEIKYUKBN_LEVEL");
//		    jComboBox_SeikyuKubun.setLinkLabel(labelLabel);
//		    jComboBox_SeikyuKubun.setRequired(true);

		    jComboBox_MetaboHantei.setCanCopy(true);
		    jComboBox_MetaboHantei.setAttributeName("MetaboHantei_Level");
		    jComboBox_MetaboHantei.setDomainId("METABOHANTEI_LEVEL");
//		    jComboBox_Metabo.setLinkLabel(labelLabel);
//		    jComboBox_MetaboHantei.setRequired(true);

		    jComboBox_HokenSidouLevel.setCanCopy(true);
		    jComboBox_HokenSidouLevel.setAttributeName("HokenSido_Level");
		    jComboBox_HokenSidouLevel.setDomainId("HOKENSIDO_LEVEL");
//		    jComboBox_Hokensido.setLinkLabel(labelLabel);
//		    jComboBox_HokenSidouLevel.setRequired(true);

		    jComboBox_Pattern.setCanCopy(true);
		    jComboBox_Pattern.setAttributeName("Tkensin_Pattern");
		    jComboBox_Pattern.setName("jComboBox_Pattern");
		    jComboBox_Pattern.setDomainId("T_KENSHIN_P_M");
//		    jComboBox_Pattern.setLinkLabel(labelLabel);
//		    jComboBox_Pattern.setRequired(true);

//			jPanelInnerLeftTop.add(jTextField_HokenjyaCode, gridBagConstraints14);
//			jPanelInnerLeftTop.add(jTextField_HokenjyaNumber, gridBagConstraints20);
//			jPanelInnerLeftTop.add(getJComboBox_Pattern(), gridBagConstraints15);
//			jPanelInnerLeftTop.add(getJComboBox_SeikyuKubun(), gridBagConstraints5);
//			jPanelInnerLeftTop.add(jLabel_HihokenjyaKigo, gridBagConstraints12);
//			jPanelInnerLeftTop.add(jLabel_SeikyuKBN, gridBagConstraints6);
//			jPanelInnerLeftTop.add(jLabel_KensinPattern, gridBagConstraints13);
//			jPanelInnerLeftTop.add(jLabel_KenshinJisiDay, gridBagConstraints17);
//			jPanelInnerLeftTop.add(jLabel_Seirino, gridBagConstraints23);
//			jPanelInnerLeftTop.add(jTextField_Date, gridBagConstraints16);
//			jPanelInnerLeftTop.add(jTextField_SeiriNo, gridBagConstraints24);
//			jPanelInnerLeftTop.add(jLabel_HihokenjyaNo, gridBagConstraints18);
////			jPanelInnerLeftTop.add(getJComboBox_KensaKikanName(), gridBagConstraints21);
//			jPanelInnerLeftTop.add(getJPanel_LeftTopInnerPanel1(), gridBagConstraints10);
//			jPanelInnerLeftTop.add(getJPanel_LeftTopInnerPanel2(), gridBagConstraints34);
//			jPanelInnerLeftTop.add(getJPanel_LeftTopInnerPanel4(), gridBagConstraints35);
//			jPanelInnerLeftTop.add(getJPanel_LeftTopInnerPanel3(), gridBagConstraints36);

		}
		return jPanelInnerLeftTop;
	}
	/* Panel LeftTop end */

	/* Panel LeftCenter start */
	/*
	 * Panel LeftCenter
	 */
	private JPanel getJPanelInnerLeftCenter() {

		int innerLeftCenterPanelHeight = 200;
		int innerLeftCenterPanelWidth = 260;

		if (jPanelInnerLeftCenter == null) {

			GridBagConstraints[] gridBagConstraintsL = null;
			gridBagConstraintsL = new GridBagConstraints[10];

			for (int i = 0; i < 10; i++) {
				gridBagConstraintsL[i] = new GridBagConstraints();
				gridBagConstraintsL[i].anchor = GridBagConstraints.WEST;
				gridBagConstraintsL[i].fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraintsL[i].weightx = 1.0;
				gridBagConstraintsL[i].gridx = 0;
				gridBagConstraintsL[i].gridy = i;
			}

			jLabel_SeiriNO = new ExtendedOpenLabel();
			jLabel_SeiriNO.setText("整理番号");
			jLabel_SeiriNO.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_SeiriNO.setHorizontalAlignment(SwingConstants.LEFT);

			jLabel_KenshinJisiDay = new ExtendedOpenLabel();
			jLabel_KenshinJisiDay.setText("実施日");
			// add s.inoue 2012/05/14
			jLabel_KenshinJisiDay.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_KenshinJisiDay.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel_KensinPattern = new ExtendedOpenLabel();
			jLabel_KensinPattern.setText("健診パターン");
			// add s.inoue 2012/05/14
			jLabel_KensinPattern.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_KensinPattern.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel_SeikyuKBN = new ExtendedOpenLabel();
			jLabel_SeikyuKBN.setText("請求区分");
			// add s.inoue 2012/05/14
			jLabel_SeikyuKBN.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_SeikyuKBN.setForeground(ViewSettings.getRequiedItemFrColor());

			jLabel_SougouComment = new ExtendedOpenLabel();
			jLabel_SougouComment.setText("総合コメント");
			jLabel_SougouComment.setFont(new Font("Dialog", Font.PLAIN, 12));

			jPanelInnerLeftCenter = new JPanel();
			jPanelInnerLeftCenter.setLayout(new GridBagLayout());
			jPanelInnerLeftCenter.setName("jInnerPanelLeftCenter");
			jPanelInnerLeftCenter.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanelInnerLeftCenter.setPreferredSize(new Dimension(innerLeftCenterPanelWidth, innerLeftCenterPanelHeight));

			jPanelInnerLeftCenter.add(jLabel_KenshinJisiDay, gridBagConstraintsL[4]);
			jPanelInnerLeftCenter.add(getJTextField_KenshinJisiDay(), gridBagConstraintsL[5]);
			jLabel_SeiriNO.setVisible(false);
			jPanelInnerLeftCenter.add(jLabel_SeiriNO, gridBagConstraintsL[6]);
			jPanelInnerLeftCenter.add(getJTextField_SeiriNo(), gridBagConstraintsL[7]);
			jPanelInnerLeftCenter.add(jLabel_KensinPattern, gridBagConstraintsL[0]);
			jPanelInnerLeftCenter.add(getJComboBox_Pattern(), gridBagConstraintsL[1]);
			jPanelInnerLeftCenter.add(jLabel_SeikyuKBN, gridBagConstraintsL[2]);
			jPanelInnerLeftCenter.add(getJComboBox_SeikyuKubun(), gridBagConstraintsL[3]);
			jPanelInnerLeftCenter.add(jLabel_SougouComment, gridBagConstraintsL[8]);
			jPanelInnerLeftCenter.add(getJTextArea_SougouComment(), gridBagConstraintsL[9]);
		}
		return jPanelInnerLeftCenter;
	}

	/* Panel LeftDown start */
	/*
	 * Panel LeftDown
	 */
	private JPanel getJPanelInnerLeftDown_Top() {

		int innerLeftDownPanelHight = 140/2;
		int innerLeftDownPanelWidth = 260/2;

		if (jPanelInnerLeftDown_Top == null) {

			GridBagConstraints[] gridBagConstraintsL = null;
			gridBagConstraintsL = new GridBagConstraints[4];

			for (int i = 0; i < 4; i++) {
				gridBagConstraintsL[i] = new GridBagConstraints();
				gridBagConstraintsL[i].anchor = GridBagConstraints.WEST;
				gridBagConstraintsL[i].fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraintsL[i].weightx = 1.0;
				gridBagConstraintsL[i].gridx = 0;
				gridBagConstraintsL[i].gridy = i;
			}
			jLabel_MetaboHantei = new ExtendedOpenLabel();
			jLabel_MetaboHantei.setText("メタボ判定");
			jLabel_MetaboHantei.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_Hokensidou = new ExtendedOpenLabel();
			jLabel_Hokensidou.setText("保健指導階層化");
			jLabel_Hokensidou.setFont(new Font("Dialog", Font.PLAIN, 12));

			jPanelInnerLeftDown_Top = new JPanel();
			jPanelInnerLeftDown_Top.setLayout(new GridBagLayout());
			jPanelInnerLeftDown_Top.setName("jInnerPanelLeftDown");
			jPanelInnerLeftDown_Top.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanelInnerLeftDown.setPreferredSize(new Dimension(260, 280));
			jPanelInnerLeftDown_Top.setPreferredSize(new Dimension(innerLeftDownPanelHight, innerLeftDownPanelWidth));

			jPanelInnerLeftDown_Top.add(jLabel_MetaboHantei, gridBagConstraintsL[0]);
			jPanelInnerLeftDown_Top.add(getJComboBox_Metabo(), gridBagConstraintsL[1]);
			jPanelInnerLeftDown_Top.add(jLabel_Hokensidou, gridBagConstraintsL[2]);
			jPanelInnerLeftDown_Top.add(getJComboBox_Hokenshido(), gridBagConstraintsL[3]);
		}
		return jPanelInnerLeftDown_Top;
	}

	/*
	 * Panel LeftDown
	 */
	private JPanel getJPanelInnerLeftDown_Down() {

		int innerLeftDownPanelHight = 140/2;
		int innerLeftDownPanelWidth = 260/2;

		if (jPanelInnerLeftDown_Down == null) {

			GridBagConstraints[] gridBagConstraintsL = null;
			gridBagConstraintsL = new GridBagConstraints[3];

			for (int i = 0; i < 3; i++) {
				gridBagConstraintsL[i] = new GridBagConstraints();
				gridBagConstraintsL[i].anchor = GridBagConstraints.WEST;
				gridBagConstraintsL[i].fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraintsL[i].weightx = 1.0;
				gridBagConstraintsL[i].gridx = 0;
				gridBagConstraintsL[i].gridy = i;
			}

			jLabel_KensaKoumoku = new ExtendedOpenLabel();
			jLabel_KensaHouhou = new ExtendedOpenLabel();
			jLabel_KensaHouhou.setText("検査方法（選択項目の情報）");
//			jLabel_KensaHouhou.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_KensaHouhou_Value = new ExtendedOpenLabel();

			jPanelInnerLeftDown_Down = new JPanel();
			jPanelInnerLeftDown_Down.setLayout(new GridBagLayout());
			jPanelInnerLeftDown_Down.setName("jInnerPanelLeftDown_Down");
			jPanelInnerLeftDown_Down.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanelInnerLeftDown_Down.setPreferredSize(new Dimension(260, 280));
			jPanelInnerLeftDown_Down.setPreferredSize(new Dimension(innerLeftDownPanelHight, innerLeftDownPanelWidth));

			jPanelInnerLeftDown_Down.add(jLabel_KensaKoumoku, gridBagConstraintsL[0]);
			jPanelInnerLeftDown_Down.add(jLabel_KensaHouhou, gridBagConstraintsL[1]);
			jPanelInnerLeftDown_Down.add(jLabel_KensaHouhou_Value, gridBagConstraintsL[2]);
		}
		return jPanelInnerLeftDown_Down;
	}

	/* LeftPanel End */

	/*
	 * 問診タブ
	 */
	protected JPanel getJInnerPanelMonshin() {
		if (jInnerPanelMonshin == null) {

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 0, 0, 10);

			jLabel_MetaboHantei = new ExtendedOpenLabel();
			jLabel_MetaboHantei.setText("メタボ判定");
			jLabel_MetaboHantei.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_Hokensidou = new ExtendedOpenLabel();
			jLabel_Hokensidou.setText("保健指導階層化");
			jLabel_Hokensidou.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel_SougouComment = new ExtendedOpenLabel();
			jLabel_SougouComment.setText("総合コメント");
			jLabel_SougouComment.setFont(new Font("Dialog", Font.PLAIN, 12));

			jInnerPanelMonshin = new JPanel();
			jInnerPanelMonshin.setLayout(new GridBagLayout());
			jInnerPanelMonshin.setName("jInnerPanelMonshin");

			jInnerPanelMonshin.add(jLabel_MetaboHantei, gridBagConstraints2);
			jInnerPanelMonshin.add(jLabel_Hokensidou, gridBagConstraints1);
			jInnerPanelMonshin.add(jLabel_SougouComment, gridBagConstraints3);
		}
		return jInnerPanelMonshin;
	}

	/*
	 * CenterPanel
	 */
	private ExtendedOpenDnDTabbedPane getJPanelRegisterCenter() {
		if (jPanelRegisterCenter == null) {
			jPanelRegisterCenter = new ExtendedOpenDnDTabbedPane();
			// del s.inoue 2012/02/06
			// jPanelRegisterCenter.addKeyListener(this);
			jPanelRegisterCenter.setFocusable(true);
		}
		return jPanelRegisterCenter;
	}

	/*
	 * CenterPanel Title
	 */
	protected JPanel getJPanelRegisterCenterTitle(JPanel jPanelRegisterCenterTitle) {

		int cntrolTop = 5;
		int controlLeft = 10;
		int controlLabelHeight = 20;
		int koumoku_chk_Width = 10;
		int koumoku_nm_Width = 200;
		int kensahouhou_Width = 50;
		int kekka_Width = 70;
		int tani_Width = 64;
		int zenkai_Width = 90;

		GridBagConstraints gridCtl1 = new GridBagConstraints();
		gridCtl1.gridx = 0;
		gridCtl1.gridy = 0;
		gridCtl1.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// eidt s.inoue 2011/10/12
//		gridCtl1.anchor = GridBagConstraints.NORTH;
//		gridCtl1.fill = GridBagConstraints.BOTH;
		gridCtl1.gridheight = 1;
		gridCtl1.weightx = 1.0d;
		gridCtl1.weighty = 1.0d;

		GridBagConstraints gridCtl2 = new GridBagConstraints();
		gridCtl2.gridx = 1;
		gridCtl2.gridy = 0;
		gridCtl2.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// eidt s.inoue 2011/10/12
//		gridCtl2.anchor = GridBagConstraints.NORTH;
//		gridCtl2.fill = GridBagConstraints.BOTH;
		gridCtl2.gridheight = 1;
		gridCtl2.weightx = 1.0d;
		gridCtl2.weighty = 1.0d;

		GridBagConstraints gridCtl3 = new GridBagConstraints();
		gridCtl3.gridx = 2;
		gridCtl3.gridy = 0;
		gridCtl3.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// eidt s.inoue 2011/10/12
//		gridCtl3.anchor = GridBagConstraints.NORTH;
//		gridCtl3.fill = GridBagConstraints.BOTH;
		gridCtl3.gridheight = 1;
		gridCtl3.weightx = 1.0d;
		gridCtl3.weighty = 1.0d;

		GridBagConstraints gridCtl4 = new GridBagConstraints();
		gridCtl4.gridx = 3;
		gridCtl4.gridy = 0;
		gridCtl4.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// edit s.inoue 2011/10/12
//		gridCtl4.anchor = GridBagConstraints.NORTH;
//		gridCtl4.fill = GridBagConstraints.BOTH;
		gridCtl4.gridheight = 1;
		gridCtl4.weightx = 1.0d;
		gridCtl4.weighty = 1.0d;

		GridBagConstraints gridCtl5 = new GridBagConstraints();
		gridCtl5.gridx = 4;
		gridCtl5.gridy = 0;
		gridCtl5.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// eidt s.inoue 2011/10/12
//		gridCtl5.anchor = GridBagConstraints.NORTH;
//		gridCtl5.fill = GridBagConstraints.BOTH;
		gridCtl5.gridheight = 1;
		gridCtl5.weightx = 1.0d;
		gridCtl5.weighty = 1.0d;

		GridBagConstraints gridCtl6 = new GridBagConstraints();
		gridCtl6.gridx = 5;
		gridCtl6.gridy = 0;
		gridCtl6.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// eidt s.inoue 2011/10/12
//		gridCtl6.anchor = GridBagConstraints.NORTH;
//		gridCtl6.fill = GridBagConstraints.BOTH;
		gridCtl6.gridheight = 1;
		gridCtl6.weightx = 1.0d;
		gridCtl6.weighty = 1.0d;

		/* ヘッダ項目 */
		ExtendedOpenLabelControl jLabel_1 = new ExtendedOpenLabelControl();
		jLabel_1.setText("");
		jLabel_1.setPreferredSize(new Dimension(koumoku_chk_Width, controlLabelHeight));

		ExtendedOpenLabelControl jLabel_2 = new ExtendedOpenLabelControl();
		jLabel_2.setText("項目名");
		jLabel_2.setPreferredSize(new Dimension(koumoku_nm_Width, controlLabelHeight));

		ExtendedOpenLabelControl jLabel_3 = new ExtendedOpenLabelControl();
		jLabel_3.setText("検査方法");
		jLabel_3.setPreferredSize(new Dimension(kensahouhou_Width, controlLabelHeight));

		ExtendedOpenLabelControl jLabel_4 = new ExtendedOpenLabelControl();
		jLabel_4.setText("結果値");
		jLabel_4.setPreferredSize(new Dimension(kekka_Width, controlLabelHeight));

		ExtendedOpenLabelControl jLabel_5 = new ExtendedOpenLabelControl();
		jLabel_5.setText("単位");
		jLabel_5.setPreferredSize(new Dimension(tani_Width, controlLabelHeight));

		ExtendedOpenLabelControl jLabel_6 = new ExtendedOpenLabelControl();
		jLabel_6.setText("前回値");
		jLabel_6.setPreferredSize(new Dimension(zenkai_Width, controlLabelHeight));

		jPanelRegisterCenterTitle = new JPanel();
		// eidt s.inoue 2011/10/12
//		jPanelRegisterCenterTitle.setPreferredSize(new Dimension(300, 50));
		jPanelRegisterCenterTitle.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		// del s.inoue 2012/02/06
		// jPanelRegisterCenterTitle.addKeyListener(this);

		jPanelRegisterCenterTitle.add(jLabel_1, gridCtl1);
		jPanelRegisterCenterTitle.add(jLabel_2, gridCtl2);
		jPanelRegisterCenterTitle.add(jLabel_5, gridCtl3);
		jPanelRegisterCenterTitle.add(jLabel_6, gridCtl4);
		jPanelRegisterCenterTitle.add(jLabel_4, gridCtl5);
		jPanelRegisterCenterTitle.add(jLabel_3, gridCtl6);

		return jPanelRegisterCenterTitle;
	}

	/* field定義 */
	/**
	 * This method initializes jTextField_HokenjyaCode
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedOpenTextControl getJTextField_SeiriNo() {
		if (jTextField_SeiriNo == null) {
			jTextField_SeiriNo = new ExtendedOpenTextControl(ImeMode.IME_OFF);
			jTextField_SeiriNo.setMaxCharacters(11);
			jTextField_SeiriNo.setVisible(false);
		}
		return jTextField_SeiriNo;
	}

	/**
	 * This method initializes jTextFieldNenrei
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_Nenrei() {
		if (jLabel_Field_Nenrei == null) {
			jLabel_Field_Nenrei = new ExtendedLabel();
		}
		return jLabel_Field_Nenrei;
	}

	/**
	 * This method initializes jTextField_Name
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_Name() {
		if (jLabel_Name == null) {
			jLabel_Name = new ExtendedLabel();
		}
		return jLabel_Name;
	}

	/**
	 * This method initializes jTextField_Sex
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_Sex() {
		if (jLabel_Field_Sex == null) {
			jLabel_Field_Sex = new ExtendedLabel();
		}
		return jLabel_Field_Sex;
	}

	/**
	 * This method initializes jTextFieldBirthDay
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_BirthDay() {
		if (jLable_Field_BirthDate == null) {
			jLable_Field_BirthDate = new ExtendedLabel();
		}
		return jLable_Field_BirthDate;
	}

	/**
	 * This method initializes jTextField_HokenjyaCode
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_HokenjyaCode() {
		if (jLabel_HiHokenjyaCode == null) {
			jLabel_HiHokenjyaCode = new ExtendedLabel();
		}
		return jLabel_HiHokenjyaCode;
	}

	/**
	 * This method initializes jTextField_HokenjyaCode
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_HokenjyaNumber() {
		if (jLabel_HiHokenjyaNumber == null) {
			jLabel_HiHokenjyaNumber = new ExtendedLabel();
		}
		return jLabel_HiHokenjyaNumber;
	}

	/**
	 * This method initializes jTextField_HokenjyaCode
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedLabel getJTextField_JusinkenSeiriNo() {
		if (jLabel_Field_JusinSeiriNo == null) {
			jLabel_Field_JusinSeiriNo = new ExtendedLabel();
		}
		return jLabel_Field_JusinSeiriNo;
	}


	/**
	 * This method initializes jTextField_HokenjyaCode
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedDateChooser getJTextField_KenshinJisiDay() {
		if (jTextField_KenshinJisiDay == null) {
			jTextField_KenshinJisiDay = new ExtendedDateChooser(ImeMode.IME_OFF);
		}
		return jTextField_KenshinJisiDay;
	}

	/**
	 * This method initializes jComboBox_Hokensido
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private JScrollPane getJTextArea_SougouComment() {
		JScrollPane scrollPane = null;
		if (jTextArea_SougouComment == null) {
			jTextArea_SougouComment = new ExtendedTextArea(ImeMode.IME_ZENKAKU);
			jTextArea_SougouComment.setPreferredSize(new Dimension(150, 50));
			jTextArea_SougouComment.addKeyListener(this);
//			jTextArea_SougouComment.setAutoscrolls(false);
//			jTextArea_SougouComment.setMaximumSize(new Dimension(200,500));
			// jTextArea_SougouComment.setMaxCharacters(10);
//			jTextArea_SougouComment.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			jTextArea_SougouComment.addMouseListener(this);
// del s.inoue 2011/12/13
//			jTextArea_SougouComment.setLineWrap(true);
//			jTextArea_SougouComment.addFocusListener(new FocusListener() {
//				@Override
//				public void focusLost(FocusEvent e) {((JTextArea)e.getSource()).setBackground(backColor_UnFocus);}
//				@Override
//				public void focusGained(FocusEvent e) {((JTextArea)e.getSource()).setBackground(backColor_Focus);}
//			});
		    scrollPane = new JScrollPane();
		    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		    scrollPane.setViewportView(jTextArea_SougouComment);
		    scrollPane.setPreferredSize(new Dimension(150,50));
		}
		return scrollPane;
	}

	/**
	 * This method initializes jComboBox_Hokensido
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedOpenComboboxControl getJComboBox_Hokenshido() {
		if (jComboBox_HokenSidouLevel == null) {
			jComboBox_HokenSidouLevel = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
//			jComboBox_HokenSidouLevel.addKeyListener(this);
		}
		return jComboBox_HokenSidouLevel;
	}

	/**
	 * This method initializes jComboBox_Metabo
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedOpenComboboxControl getJComboBox_Metabo() {
		if (jComboBox_MetaboHantei == null) {
			jComboBox_MetaboHantei = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
//			jComboBox_MetaboHantei.addKeyListener(this);
		}
		return jComboBox_MetaboHantei;
	}

	/**
	 * This method initializes jComboBox_Pattern
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedOpenComboboxControl getJComboBox_Pattern() {
		if (jComboBox_Pattern == null) {
			jComboBox_Pattern = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
//			jComboBox_Pattern.addKeyListener(this);
		}
		return jComboBox_Pattern;
	}

	/**
	 * This method initializes jComboBox_SeikyuKubun
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedOpenComboboxControl getJComboBox_SeikyuKubun() {
		if (jComboBox_SeikyuKubun == null) {
			jComboBox_SeikyuKubun = new ExtendedOpenComboboxControl(ImeMode.IME_OFF);
//			jComboBox_SeikyuKubun.addKeyListener(this);
		}
		return jComboBox_SeikyuKubun;
	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			jButton_End.addActionListener(this);
			// eidt s.inoue 2012/11/07
			jButton_End.setFocusable(false);
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
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Clear= new ExtendedButton(
					"Clear","クリア(E)","クリア(ALT+E)",KeyEvent.VK_E,icon);
			jButton_Clear.addActionListener(this);
			// eidt s.inoue 2012/11/07
			jButton_Clear.setFocusable(false);
		}
		return jButton_Clear;
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
					"Close","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

//	/**
//	 * This method initializes jButton_Register
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_PrintRyoshu() {
//		if (jButton_PrintRyoshu == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			jButton_PrintRyoshu= new ExtendedButton(
//					"Close","領収書(P)","領収書(ALT+P)",KeyEvent.VK_P,icon);
//			jButton_PrintRyoshu.addActionListener(this);
//			jButton_PrintRyoshu.setVisible(false);
//		}
//		return jButton_PrintRyoshu;
//	}

	// edit n.ohkubo 2015/03/01　未使用なので削除
//	/**
//	 * This method initializes jButton_Register
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_RyoshuPrint() {
//		if (jButton_Register == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			jButton_Register= new ExtendedButton(
//					"Close","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
//			jButton_Register.addActionListener(this);
//		}
//		return jButton_Register;
//	}

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
		if( ViewSettings.getTokuteiLoginFrameWidth() > rect.width  ||
				ViewSettings.getTokuteiLoginFrameHeight() > rect.height )
			{
				setBounds( rect.x,
						   rect.y,
						   ViewSettings.getTokuteiLoginFrameWidth(),
						   ViewSettings.getTokuteiLoginFrameHeight() );
			}
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
		mousePopup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}

	private void mousePopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			// ポップアップメニューを表示する
			JComponent c = (JComponent)e.getSource();
			jTextArea_SougouComment.showPopup(c, e.getX(), e.getY());
			e.consume();
		}
	}

}
