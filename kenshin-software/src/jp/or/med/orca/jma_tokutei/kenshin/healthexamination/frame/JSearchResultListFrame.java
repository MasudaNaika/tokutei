package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.GuidanceLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import javax.swing.BorderFactory;
import java.awt.Insets;

/**
 * 健診結果表示・自動判定　検索画面
 *
 * Modified 2008/03/19 若月 画面コンポーネント共通化
 */
// edit h.yoshitama 2009/11/11
public class JSearchResultListFrame extends JFrame implements ActionListener, ItemListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected GuidanceLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_YearArea = null;
	protected JPanel jPanel_KekkaArea = null;

	protected JPanel jPanel = null;
	protected ExtendedTextField jTextField_Jyusinken_ID = null;
	protected ExtendedTextField jTextField_HokenjyaNumber = null;
	protected ExtendedTextField jTextField_KensaBeginDate = null;
	protected ExtendedTextField jTextField_HanteiBeginDate = null;
	protected ExtendedTextField jTextField_TsuuchiBeginDate = null;
	protected ExtendedTextField jTextField_HokensyoCode = null;
	protected ExtendedTextField jTextField_Name = null;
	protected ExtendedTextField jTextField_KensaEndDate = null;
	protected ExtendedTextField jTextField_HanteiEndDate = null;
	protected ExtendedTextField jTextField_TsuuchiEndDate = null;
	// edit s.inoue 2010/02/23
	// protected ExtendedTextField jTextField_HokensyoNumber = null;
	protected ExtendedComboBox jComboBox_HokenjyaNumber = null;

	protected ExtendedComboBox jComboBox_HanteiResult = null;
	protected ExtendedButton jButton_Serach = null;
	protected ExtendedButton jButton_Print = null;
	protected ExtendedButton jButton_OK = null;
	protected ExtendedButton jButton_Metabo = null;

	protected JPanel jPanel_Table = null;
	protected JLabel jLabel = null;
	protected JLabel jLabel1 = null;
	protected JLabel jLabel2 = null;
	protected JLabel jLabel3 = null;
	protected JLabel jLabel4 = null;
	protected JLabel jLabel5 = null;
	protected JLabel jLabel6 = null;
	protected JLabel jLabel7 = null;
	protected JLabel jLabel8 = null;
	protected JLabel jLabel9 = null;
	protected JLabel jLabel10 = null;
	protected JLabel jLabel11 = null;
	//protected ExtendedLabel jLabel12 = null;
	//protected ExtendedLabel jLabel13 = null;

	protected ExtendedCheckBox jCheckBox_IsKekkaInput = null;
	protected JPanel jPanel1 = null;
	protected ExtendedButton jButton_PrintSetsumei = null;
	protected ExtendedCheckBox jCheckBox_JisiYear = null;
	// add h.yoshitama 2009/11/26
    protected ExtendedButton jButton_AllSelect = null;
    protected ExtendedLabel jLabel_count = null;

	/**
	 * This is the default constructor
	 */
	public JSearchResultListFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		/* Modified 2008/03/19 若月 画面表示共通化 */
		/* --------------------------------------------------- */
//		this.setSize(800, 600);
//		this.setContentPane(getJPanel_Content());
//		this.setTitle("特定健診システム");
//		setLocationRelativeTo( null );
//		this.setVisible(true);
		/* --------------------------------------------------- */
		this.setContentPane(getJPanel_Content());
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

		this.setPreferredSize(ViewSettings.getFrameCommonSize());

		// replace s.inoue 2010/02/19
//		this.setSize(new Dimension(921, 662));
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
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.gridx = 3;
			gridBagConstraints33.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints33.gridy = 0;
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.gridy = 0;
			gridBagConstraints32.gridx = 0;
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.gridy = 0;
			gridBagConstraints30.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints30.gridx = 4;
			GridBagConstraints gridBagConstraints29 = new GridBagConstraints();
			gridBagConstraints29.gridy = 0;
			gridBagConstraints29.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints29.gridx = 2;
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridy = 0;
			gridBagConstraints28.weightx = 1.0D;
			gridBagConstraints28.anchor = GridBagConstraints.EAST;
			gridBagConstraints28.gridx = 1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Metabo(), gridBagConstraints28);
			jPanel_ButtonArea.add(getJButton_Print(), gridBagConstraints29);
			jPanel_ButtonArea.add(getJButton_OK(), gridBagConstraints30);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints32);
			jPanel_ButtonArea.add(getJButton_PrintSetsumei(), gridBagConstraints33);
		}
		return jPanel_ButtonArea;
	}

	// add h.yoshitama 2009/11/26
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
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			jButton_End = new ExtendedButton();
			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
			jButton_End.setActionCommand("終了");
			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_End.setText("戻る(F1)");
			jButton_End.addActionListener(this);
		}
		return jButton_End;
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
			jCheckBox_JisiYear.setPreferredSize(new Dimension(90, 20));
		}
		return jCheckBox_JisiYear;
	}

	/**
	 * This method initializes jPanel_NaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_NaviArea() {
		if (jPanel_NaviArea == null) {
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.insets = new Insets(10, 10, 0, 10);
			gridBagConstraints25.gridy = 0;
			gridBagConstraints25.ipadx = 498;
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.fill = GridBagConstraints.BOTH;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			gridBagConstraints25.gridx = 0;

			// edit s.inoue 2010/02/24
			jLabel_MainExpl = new GuidanceLabel("tokutei.searchresult.frame.guidance.main");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_MainExpl.setName("jLabel1");

			/* Modified 2008/03/19 若月 UI標準化 */
			/* --------------------------------------------------- */
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("健診結果表示・自動判定　検索");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			/* --------------------------------------------------- */
			jLabel_Title = new TitleLabel("tokutei.searchresult.frame.title");
			/* --------------------------------------------------- */
			jPanel_NaviArea = new JPanel();
			jPanel_NaviArea.setLayout(new GridBagLayout());
			jPanel_NaviArea.add(getJPanel_TitleArea(), gridBagConstraints25);
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
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.weightx = 1.0D;
			gridBagConstraints26.fill = GridBagConstraints.BOTH;
			gridBagConstraints26.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints26.gridy = 1;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints24.gridy = 1;
			gridBagConstraints24.anchor = GridBagConstraints.WEST;
			gridBagConstraints24.weightx = 1.0D;
			gridBagConstraints24.fill = GridBagConstraints.BOTH;
			gridBagConstraints24.gridx = 0;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints23.gridy = 0;
			gridBagConstraints23.weightx = 1.0D;
			gridBagConstraints23.fill = GridBagConstraints.BOTH;
			gridBagConstraints23.anchor = GridBagConstraints.WEST;
			gridBagConstraints23.ipady = 15;
			gridBagConstraints23.gridx = 0;
			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints23);
//			jPanel_TitleArea.add(getJPanel_ExplArea1(), gridBagConstraints24);
			jPanel_TitleArea.add(jLabel_MainExpl, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

//	/**
//	 * This method initializes jPanel_YearArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_YearArea() {
//		if (jPanel_YearArea == null) {
//			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
//			gridBagConstraints34.anchor = GridBagConstraints.EAST;
//			gridBagConstraints34.gridx = 5;
//			gridBagConstraints34.gridy = 3;
//			gridBagConstraints34.weightx = 1.0D;
//			gridBagConstraints34.insets = new Insets(0, 120, 0, 0);
//			gridBagConstraints34.fill = GridBagConstraints.BOTH;
//
////			GridBagConstraints gridBagConstraints35 = new GridBagConstraints();
////			gridBagConstraints35.anchor = GridBagConstraints.WEST;
////			gridBagConstraints35.gridx = 4;
////			gridBagConstraints35.gridy = 3;
////			gridBagConstraints35.insets = new Insets(0, 20, 0, 0);
//
//			jPanel_YearArea = new JPanel();
//			jPanel_YearArea.setLayout(new GridBagLayout());
//			jPanel_YearArea.add(getJCheckBox_JisiYear(), gridBagConstraints34);
//			//jPanel_YearArea.add(jLabel12, gridBagConstraints35);
//		}
//		return jPanel_YearArea;
//	}

//	/**
//	 * This method initializes jPanel_YearArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_KekkaArea() {
//		if (jPanel_KekkaArea == null) {
//			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
//			gridBagConstraints37.anchor = GridBagConstraints.EAST;
//			gridBagConstraints37.gridx = 5;
//			gridBagConstraints37.gridy = 2;
//			gridBagConstraints37.weightx = 1.0D;
//			gridBagConstraints37.insets = new Insets(0, 120, 0, 0);
//			gridBagConstraints37.fill = GridBagConstraints.BOTH;
//
////			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
////			gridBagConstraints38.anchor = GridBagConstraints.WEST;
////			gridBagConstraints38.gridx = 4;
////			gridBagConstraints38.gridy = 2;
////			gridBagConstraints38.insets = new Insets(0, 20, 0, 0);
//
//			jPanel_KekkaArea = new JPanel();
//			jPanel_KekkaArea.setLayout(new GridBagLayout());
//			jPanel_KekkaArea.add(getJCheckBox_IsKekkaInput(), gridBagConstraints37);
//			//jPanel_KekkaArea.add(jLabel13, gridBagConstraints38);
//		}
//		return jPanel_KekkaArea;
//	}

	/**
	 * This method initializes jPanel_MainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_MainArea() {
		if (jPanel_MainArea == null) {
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.insets = new Insets(5, 10, 0, 10);
			gridBagConstraints27.gridy = 0;
			gridBagConstraints27.weightx = 1.0D;
			gridBagConstraints27.weighty = 1.0D;
			gridBagConstraints27.fill = GridBagConstraints.BOTH;
			gridBagConstraints27.gridx = 0;
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new GridBagLayout());
			jPanel_MainArea.add(getJPanel_DrawArea(), gridBagConstraints27);
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
			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new BorderLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");
			jPanel_DrawArea.add(getJPanel(), BorderLayout.NORTH);
			jPanel_DrawArea.add(getJPanel_Table(), BorderLayout.CENTER);
		}
		return jPanel_DrawArea;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			// add h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.anchor = GridBagConstraints.SOUTHEAST;
			gridBagConstraints13.gridy = 3;
			gridBagConstraints13.gridheight = 2;
			gridBagConstraints13.gridx = 7;
			gridBagConstraints13.insets = new Insets(0, 0, 0, 0);
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.gridx = 6;
			gridBagConstraints61.gridy = 1;
			gridBagConstraints61.weightx = 1.0D;
			gridBagConstraints61.insets = new Insets(0, 5, 0, 0);
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.anchor = GridBagConstraints.WEST;
			gridBagConstraints43.gridx = 5;
			gridBagConstraints43.gridy = 1;
			gridBagConstraints43.insets = new Insets(0, 20, 0, 0);

			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridy = 1;
			gridBagConstraints52.gridx = 3;
			// edit s.inoue 2010/02/24
			gridBagConstraints52.fill = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			// edit h.yoshitama 2009/11/26
			gridBagConstraints42.fill = GridBagConstraints.WEST;
			gridBagConstraints42.gridheight = 3;
			gridBagConstraints42.gridwidth = 9;
			gridBagConstraints42.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints42.gridy = 2;
			jLabel11 = new JLabel();
			jLabel11.setText("保健指導レベル");
			jLabel11.setFont(new Font("Dialog", Font.PLAIN, 12));

//			jLabel12 = new ExtendedLabel();
//			jLabel12.setText("今年度");
//			jLabel12.setPreferredSize(new Dimension(80, 16));
//			jLabel12.setFont(new Font("Dialog", Font.PLAIN, 12));

//			jLabel13 = new ExtendedLabel();
//			jLabel13.setText("結果入力済み");
//			jLabel13.setPreferredSize(new Dimension(80, 16));
//			k.setFont(new Font("Dialog", Font.PLAIN, 12));

			// edit s.inoue 2010/02/24
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.anchor = GridBagConstraints.SOUTHEAST;
			gridBagConstraints63.gridy = 3;
			gridBagConstraints63.gridheight = 2;
			gridBagConstraints63.gridx = 7;
			gridBagConstraints63.insets = new Insets(0, 0, 30, 0);

			jLabel10 = new JLabel();
			jLabel10.setText("～");
			jLabel10.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel9 = new JLabel();
			jLabel9.setText("結果通知日");
			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel8 = new JLabel();
			jLabel8.setText("～");
			jLabel8.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel7 = new JLabel();
			jLabel7.setText("判定日");
			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel6 = new JLabel();
			jLabel6.setText("～");
			jLabel6.setPreferredSize(new Dimension(30, 16));
			jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel6.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel5 = new JLabel();
			jLabel5.setText("健診実施日");
			// edit h.yoshitama 2009/11/26
			jLabel5.setPreferredSize(new Dimension(68, 16));
			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));

			// edit s.inoue 2010/02/24
			jLabel_count = new ExtendedLabel();
			jLabel_count.setPreferredSize(new Dimension(100, 20));
			jLabel_count.setFont(new Font("Dialog", Font.PLAIN, 12));

			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 2;
			gridBagConstraints51.anchor = GridBagConstraints.WEST;
			gridBagConstraints51.insets = new Insets(0, 25, 0, 0);
			gridBagConstraints51.gridy = 1;
			jLabel4 = new JLabel();
			jLabel4.setText("氏名（カナ）");
			jLabel4.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridy = 1;
			// edit s.inoue 2010/02/24
			gridBagConstraints41.insets = new Insets(0, 0, 0, 4);

			jLabel3 = new JLabel();
			jLabel3.setText("保険者番号");
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 5;
			gridBagConstraints31.anchor = GridBagConstraints.WEST;
			gridBagConstraints31.insets = new Insets(0, 20, 0, 10);
			gridBagConstraints31.gridy = 0;
			jLabel2 = new JLabel();
			jLabel2.setText("被保険者証等番号");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.insets = new Insets(0, 25, 0, 0);
			gridBagConstraints21.gridy = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("被保険者証等記号");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 6;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 5);

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridx = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			// edit s.inoue 2010/02/24
			gridBagConstraints.insets = new Insets(0, 0, 0, 4);

			// add s.inoue 2010/03/24
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.gridy = 4;
			gridBagConstraints64.gridx = 6;
			gridBagConstraints64.anchor = GridBagConstraints.WEST;
			gridBagConstraints64.insets = new Insets(50, 90, 0, 0);

			// add s.inoue 2010/03/24
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.gridy = 4;
			gridBagConstraints65.gridx = 6;
			gridBagConstraints65.anchor = GridBagConstraints.WEST;
			gridBagConstraints65.insets = new Insets(50, 0, 0, 0);

			jLabel = new JLabel();
			jLabel.setText("受診券整理番号");
			jLabel.setPreferredSize(new Dimension(100, 16));
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setName("jPanel");
			jPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			// edit s.inoue 2010/02/23
			jPanel.add(jLabel, gridBagConstraints21);
			jPanel.add(getJTextField_Jyusinken_ID(), gridBagConstraints6);
			// edit s.inoue 2010/02/23
			jPanel.add(jLabel1, gridBagConstraints51);
			jPanel.add(getJTextField_HokensyoCode(), gridBagConstraints52);
			// edit s.inoue 2010/02/23
			jPanel.add(jLabel2, gridBagConstraints41);
			jPanel.add(getJComboBox_HokenjyaNumber(), gridBagConstraints11);
			// edit s.inoue 2010/02/23
			jPanel.add(jLabel3, gridBagConstraints31);
			jPanel.add(getJTextField_HokenjyaNumber(), gridBagConstraints2);

			// edit s.inoue 2010/02/23
			jPanel.add(jLabel4, gridBagConstraints);
			jPanel.add(getJTextField_Name(), gridBagConstraints1);
// del s.inoue 2010/02/24
			jPanel.add(getJPanel1(), gridBagConstraints42);
			// add h.yoshitama 2009/11/26
			jPanel.add(jLabel11, gridBagConstraints43);
			jPanel.add(getJComboBox_HanteiResult(), gridBagConstraints61);

			// edit s.inoue 2010/02/24
			jPanel.add(jLabel_count,gridBagConstraints63);

			jPanel.add(getJButton_Serach(), gridBagConstraints13);
			// add s.inoue 2010/03/24
			jPanel.add(getJCheckBox_JisiYear(),gridBagConstraints64);
			jPanel.add(getJCheckBox_IsKekkaInput(),gridBagConstraints65);

		}
		return jPanel;
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
		}
		return jTextField_Jyusinken_ID;
	}

	/**
	 * This method initializes jTextField_HokenjyaNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HokenjyaNumber() {
		if (jTextField_HokenjyaNumber == null) {
			jTextField_HokenjyaNumber = new ExtendedTextField("", 8, ImeMode.IME_OFF);
			jTextField_HokenjyaNumber.setPreferredSize(new Dimension(130, 20));
		}
		return jTextField_HokenjyaNumber;
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
			jTextField_HanteiBeginDate.setPreferredSize(new Dimension(100, 20));
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
		}
		return jTextField_TsuuchiEndDate;
	}

// edit s.inoue 2010/02/23
//	/**
//	 * This method initializes jTextField_HokensyoNumber
//	 *
//	 * @return javax.swing.ExtendedTextField
//	 */
//	private ExtendedTextField getJTextField_HokensyoNumber() {
//		if (jTextField_HokensyoNumber == null) {
//			jTextField_HokensyoNumber = new ExtendedTextField(ImeMode.IME_ZENKAKU);
//			jTextField_HokensyoNumber.setPreferredSize(new Dimension(130, 20));
//		}
//		return jTextField_HokensyoNumber;
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
	 * This method initializes jComboBox_HanteiResult
	 *
	 * @return javax.swing.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_HanteiResult() {
		if (jComboBox_HanteiResult == null) {
			jComboBox_HanteiResult = new ExtendedComboBox();
			jComboBox_HanteiResult.setPreferredSize(new Dimension(130, 20));
		}
		return jComboBox_HanteiResult;
	}

	/**
	 * This method initializes jButton_Serach
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Serach() {
		if (jButton_Serach == null) {
			jButton_Serach = new ExtendedButton();
			jButton_Serach.setText("検索(S)");
//			jButton_Serach.setPreferredSize(new Dimension(58, 20));
//			jButton_Serach.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Serach.addActionListener(this);
			jButton_Serach.setMnemonic(KeyEvent.VK_S);
		}
		return jButton_Serach;
	}

	/**
	 * This method initializes jButton_Print
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Print() {
		if (jButton_Print == null) {
			jButton_Print = new ExtendedButton();
			jButton_Print.setText("通知表印刷(F9)");
			jButton_Print.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Print.addActionListener(this);
		}
		return jButton_Print;
	}

	/**
	 * This method initializes jButton_OK
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_OK() {
		if (jButton_OK == null) {
			jButton_OK = new ExtendedButton();
			jButton_OK.setText("詳細(F12)");
			jButton_OK.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_OK.addActionListener(this);
		}
		return jButton_OK;
	}

	/**
	 * This method initializes jButton_Metabo
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Metabo() {
		if (jButton_Metabo == null) {
			jButton_Metabo = new ExtendedButton();
			jButton_Metabo.setText("メタボリックシンドローム判定・階層化(F7)");
//			jButton_Metabo.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Metabo.setPreferredSize(new Dimension(250, 25));
			jButton_Metabo.addActionListener(this);
		}
		return jButton_Metabo;
	}

	public void actionPerformed( ActionEvent e )
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
	 * This method initializes jPanel_Table
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Table() {
		if (jPanel_Table == null) {
			jPanel_Table = new JPanel();
			jPanel_Table.setLayout(new BorderLayout());
			jPanel_Table.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		}
		return jPanel_Table;
	}

	/**
	 * This method initializes jCheckBox_IsKekkaInput
	 *
	 * @return javax.swing.ExtendedCheckBox
	 */
	private ExtendedCheckBox getJCheckBox_IsKekkaInput() {
		if (jCheckBox_IsKekkaInput == null) {
			jCheckBox_IsKekkaInput = new ExtendedCheckBox();
			jCheckBox_IsKekkaInput.setText("結果入力済");
			jCheckBox_IsKekkaInput.setPreferredSize(new Dimension(90, 20));
		    // add h.yoshitama 2009/11/11
		    jCheckBox_IsKekkaInput.addItemListener(this);
		}
		return jCheckBox_IsKekkaInput;
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			// del h.yoshitama 2009/11/26
//			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
//			gridBagConstraints13.anchor = GridBagConstraints.SOUTHEAST;
//			gridBagConstraints13.gridy = 2;
//			gridBagConstraints13.gridheight = 2;
//			gridBagConstraints13.gridx = 9;
//			gridBagConstraints13.insets = new Insets(0, 0, 0, 0);
			// del h.yoshitama 2009/11/26
//			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
//			gridBagConstraints12.anchor = GridBagConstraints.WEST;
//			gridBagConstraints12.gridy = 1;
//			gridBagConstraints12.insets = new Insets(0, 20, 0, 0);
//			gridBagConstraints12.weightx = 1.0D;
//			gridBagConstraints12.gridx = 5;
//			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
//			gridBagConstraints15.anchor = GridBagConstraints.WEST;
//			gridBagConstraints15.gridx = 4;
//			gridBagConstraints15.gridy = 1;
//			gridBagConstraints15.insets = new Insets(0, 20, 0, 0);
			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.anchor = GridBagConstraints.WEST;
			gridBagConstraints22.gridy = 3;
			gridBagConstraints22.gridx = 0;
			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.gridx = 0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.gridy = 1;
			gridBagConstraints19.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			// edit s.inoue 2010/02/24
			gridBagConstraints3.fill = GridBagConstraints.WEST;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.gridx = 1;
			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 2;
			gridBagConstraints18.gridy = 3;
			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 2;
			gridBagConstraints17.gridy = 2;
			gridBagConstraints17.insets = new Insets(0, 9, 0, 9);
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 2;
			gridBagConstraints16.gridy = 1;
			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 3;
			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridx = 3;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridx = 3;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.gridx = 3;

			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.gridx = 1;

			// edit h.yoshitama 2009/11/26
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.gridx = 1;
// del s.inoue 2010/03/24
//			// edit h.yoshitama 2009/11/26
//			GridBagConstraints gridBagConstraints36 = new GridBagConstraints();
//			gridBagConstraints36.gridx = 6;
//			gridBagConstraints36.gridy = 4;
//			gridBagConstraints36.insets = new Insets(0, 265, 0, 0);
//			gridBagConstraints36.anchor = GridBagConstraints.WEST;
//			gridBagConstraints36.gridwidth = 1;
//
//			// edit h.yoshitama 2009/11/26
//			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
//			gridBagConstraints39.gridx = 6;
//			gridBagConstraints39.gridy = 4;
//			gridBagConstraints39.insets = new Insets(0, 175, 0, 0);
//			gridBagConstraints39.anchor = GridBagConstraints.WEST;
//			gridBagConstraints39.gridwidth = 1;

		    GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
		    gridBagConstraints40.gridx = 0;
		    gridBagConstraints40.anchor = GridBagConstraints.WEST;
		    gridBagConstraints40.insets = new Insets(0, 0, 0, 0);
		    gridBagConstraints40.gridy = 4;

			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJTextField_KensaEndDate(), gridBagConstraints8);
			jPanel1.add(getJTextField_HanteiEndDate(), gridBagConstraints9);
			jPanel1.add(getJTextField_TsuuchiEndDate(), gridBagConstraints10);
			jPanel1.add(jLabel6, gridBagConstraints16);
			jPanel1.add(jLabel8, gridBagConstraints17);
			jPanel1.add(jLabel10, gridBagConstraints18);
			jPanel1.add(getJTextField_KensaBeginDate(), gridBagConstraints3);
			jPanel1.add(getJTextField_HanteiBeginDate(), gridBagConstraints4);
			jPanel1.add(getJTextField_TsuuchiBeginDate(), gridBagConstraints5);
			jPanel1.add(jLabel5, gridBagConstraints19);
			jPanel1.add(jLabel7, gridBagConstraints20);
			jPanel1.add(jLabel9, gridBagConstraints22);

			// del h.yoshitama 2009/11/26
//			jPanel1.add(jLabel11, gridBagConstraints15);

			//jPanel1.add(getJCheckBox_IsKekkaInput(), gridBagConstraints14);
			// del h.yoshitama 2009/11/26
//			jPanel1.add(getJComboBox_HanteiResult(), gridBagConstraints12);
			// del h.yoshitama 2009/11/26
//			jPanel1.add(getJButton_Serach(), gridBagConstraints13);
			// del s.inoue 2010/03/24
//			jPanel1.add(getJCheckBox_JisiYear(), gridBagConstraints36);
//			jPanel1.add(getJCheckBox_IsKekkaInput(), gridBagConstraints39);
			// add h.yoshitama 2009/11/26
		    jPanel1.add(getJButton_AllSelect(), gridBagConstraints40);
		}
		return jPanel1;
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	// add h.yoshitama 2009/11/11
	public void itemStateChanged(ItemEvent arg0) {

	}

	/**
	 * This method initializes jButton_PrintSetsumei
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_PrintSetsumei() {
		if (jButton_PrintSetsumei == null) {
			jButton_PrintSetsumei = new ExtendedButton();
//			jButton_PrintSetsumei.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_PrintSetsumei.setPreferredSize(new Dimension(100, 25));
			jButton_PrintSetsumei.setText("説明用印刷(F11)");
			jButton_PrintSetsumei.addActionListener(this);
		}
		return jButton_PrintSetsumei;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
