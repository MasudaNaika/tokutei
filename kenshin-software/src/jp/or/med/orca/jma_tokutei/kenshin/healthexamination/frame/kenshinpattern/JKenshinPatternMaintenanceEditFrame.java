package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class JKenshinPatternMaintenanceEditFrame extends JFrame implements ActionListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JLabel jLabel_Title = null;
	protected JLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
	protected JLabel jLabel1 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel3 = null;
	protected JPanel jPanel_hanrei = null;
	// add s.inoue 2012/05/09
	protected JLabel jLabel4 = null;
	protected JPanel jPanel10 = null;
	protected JPanel jPanel11 = null;
	protected JPanel jPanel12 = null;
	protected JLabel jLabel = null;
	protected JLabel jLabel2 = null;

	protected JLabel jLabel_hanrei1 = null;
	protected JLabel jLabel_hanrei2 = null;
	protected JLabel jLabel_hanrei3 = null;
	protected JLabel jLabel_hanrei4 = null;
	protected JLabel jLabel_syosaiExample = null;
	protected JLabel jLabel_addExample = null;
	protected JLabel jLabel_kihonExample = null;

	protected JTextField jTextField_PatternName = null;
	protected JPanel jPanel_Left = null;
	protected JPanel jPanel_Right = null;
	protected ExtendedButton jButton_End = null;
	// add s.inoue 2011/08/26
	protected ExtendedButton jButton_Combination = null;

	protected ExtendedButton jButton_Cancel = null;
	protected ExtendedButton jButton_Register = null;
	protected ExtendedButton jButton_ToLeft = null;
	protected ExtendedButton jButton_ToRight = null;
	protected ExtendedButton jButton_Clear = null;

	// eidt s.inoue 2011/09/02
	protected ExtendedButton jButton_Sort = null;
	protected ExtendedButton jButton_ToTop = null;
	protected ExtendedButton jButton_ToDown = null;
	protected JLabel jLabel_Info0 = null;
	protected JLabel jLabel_Info1 = null;
	protected JLabel jLabel_Info2 = null;
	protected JLabel jLabel_Info3 = null;
	protected JLabel jLabel_Info4 = null;
	
	// edit n.ohkubo 2014/10/01　追加
	protected JTextField jText_filter = null;
	protected ExtendedButton jButton_filter = null;

	/**
	 * This is the default constructor
	 */
	public JKenshinPatternMaintenanceEditFrame() {
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
			jLabel_Title = new TitleLabel("tokutei.kenshinpattern-mastermaintenance-add.frame.title","tokutei.kenshinpattern-mastermaintenance-add.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
//			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
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
			// add s.inoue 2012/11/13
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints9.gridwidth = 5;

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 0);

			// add s.inoue 2011/08/26
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.gridx = 4;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;

//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridy = 1;
//			gridBagConstraints7.gridx = 5;

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints8);
			jPanel_ButtonArea.add(getJButton_Clear(), gridBagConstraints6);
//			// add s.inoue 2011/08/26
			jPanel_ButtonArea.add(getJButton_Combination(), gridBagConstraints4);
//
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints5);

			// add s.inoue 2012/11/13
			jPanel_ButtonArea.add(getJPanel_TitleArea(), gridBagConstraints9);

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
			// edit s.inoue 2011/03/10
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
//			jButton_End.setMnemonic(KeyEvent.VK_R);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_End= new ExtendedButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

	// add s.inoue 2011/08/26
	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Combination() {
		if (jButton_Combination == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Pattern);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Combination= new ExtendedButton(
					"Combination","複合(M)","複合(ALT+M)",KeyEvent.VK_M,icon);
			jButton_Combination.addActionListener(this);
			// add s.inoue 2012/07/10
			jButton_Combination.setFocusable(false);
		}
		return jButton_Combination;
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
//			jLabel_MainExpl = new JLabel();
//			jLabel_MainExpl.setText("右表<<健診項目マスタの内容>>から登録する健診項目を選択し、左矢印(←)ボタンを押下し左表<<選択パターンの内容>>");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			// edit ver2 s.inoue 2009/06/04
////			jLabel_Title = new JLabel();
////			jLabel_Title.setText("健診パターンメンテナンス");
////			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
////			jLabel_Title.setBackground(new Color(153, 204, 255));
////			jLabel_Title.setForeground(new Color(51, 51, 51));
////			jLabel_Title.setOpaque(true);
////			jLabel_Title.setName("jLabel");
//			//jLabel_Title = new JLabel("tokutei.kenshinpattern-mastermaintenance.frame.title");
//			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
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

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	/**
//	 * This method initializes jPanel_ExplArea2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea2() {
//		if (jPanel_ExplArea2 == null) {
//			jLabal_SubExpl = new JLabel();
//			jLabal_SubExpl.setText("へ追加後、登録ボタンを押下してください。項目順を変更する場合は、上(↑)下(↓)移動して下さい。");
//			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			GridLayout gridLayout1 = new GridLayout();
//			gridLayout1.setRows(2);
//			jPanel_ExplArea2 = new JPanel();
//			jPanel_ExplArea2.setName("jPanel4");
//			jPanel_ExplArea2.setLayout(gridLayout1);
//			jPanel_ExplArea2.add(jLabel_MainExpl, null);
//			jPanel_ExplArea2.add(jLabal_SubExpl, null);
//		}
//		return jPanel_ExplArea2;
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	/**
//	 * This method initializes jPanel_ExplArea1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	// s.inoue
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
//	 * This method initializes jButton_Cancel
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Cancel() {
//		if (jButton_Cancel == null) {
//			// edit s.inoue 2011/03/10
////			jButton_Cancel = new ExtendedButton();
////			jButton_Cancel.setText("キャンセル");
////			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
////			jButton_Cancel.setVisible(false);
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Redo);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			jButton_End= new ExtendedButton(
//					"Close","キャンセル(C)","キャンセル(ALT+C)",KeyEvent.VK_C,icon);
//		}
//		return jButton_Cancel;
//	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			// edit s.inoue 2011/03/10
//			jButton_Register = new ExtendedButton();
//			jButton_Register.setText("登録(F12)");
//			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Register);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Register= new ExtendedButton(
					"Register","登録(S)","登録(ALT+S)",KeyEvent.VK_S,icon);
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	/**
	 * This method initializes jButton_Clear
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Clear() {
		if (jButton_Clear == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Redo);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Clear= new ExtendedButton(
					"Clear","操作取消(C)","操作取消(ALT+C)",KeyEvent.VK_C,icon);
			jButton_Clear.addActionListener(this);
			// add s.inoue 2012/07/10
			jButton_Clear.setFocusable(false);
		}
		return jButton_Clear;
	}
	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			CardLayout cardLayout4 = new CardLayout();
			cardLayout4.setHgap(10);
			cardLayout4.setVgap(10);
			jPanel = new JPanel();
			jPanel.setLayout(cardLayout4);
			jPanel.add(getJPanel12(), getJPanel12().getName());
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
			jLabel1 = new JLabel();
			jLabel1.setText("パターン名");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel1 = new JPanel();
			jPanel1.setName("jPanel1");
			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.X_AXIS));
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJTextField_PatternName(), null);
			
			// edit n.ohkubo 2014/10/01　追加　start
			jPanel1.add(new JLabel("　　　　　　"));	//パターンの名称とフィルターの間のスペース
			
			JPanel jPanel_filter = new JPanel();
			jPanel_filter.setLayout(new BoxLayout(jPanel_filter, BoxLayout.X_AXIS));
			
			jText_filter = new JTextField(20);
			jText_filter.setMaximumSize(new Dimension(130, 25));
			jPanel_filter.add(jText_filter);

			jButton_filter = new ExtendedButton();
			jButton_filter.setText("絞込み");
			jButton_filter.addActionListener(this);
			jPanel_filter.add(jButton_filter);
			
			jPanel1.add(jPanel_filter);
			// edit n.ohkubo 2014/10/01　追加　end
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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 0, 1);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridwidth = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			jPanel2 = new JPanel();
			jPanel2.setName("jPanel2");
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJPanel11(), gridBagConstraints);
			jPanel2.add(getJPanel3(), gridBagConstraints1);
			jPanel2.add(getJPanel10(), gridBagConstraints2);
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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 4;

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 6;

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 8;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 1;

			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 3;

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 5;

			GridBagConstraints gridBagConstraints9= new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 7;

			GridBagConstraints gridBagConstraints10= new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 9;

			jPanel3 = new JPanel();
			// eidt s.inoue 2012/05/09 80→60
			jPanel3.setPreferredSize(new Dimension(60, 300));
			jPanel3.setLayout(new GridBagLayout());

			// eidt s.inoue 2011/09/02
			jPanel3.add(getJButton_ToSort(), gridBagConstraints2);
			jPanel3.add(getJButton_ToTop(), gridBagConstraints3);
			jPanel3.add(getJButton_ToLeft(), gridBagConstraints4);
			jPanel3.add(getJButton_ToRight(), gridBagConstraints5);
			jPanel3.add(getJButton_ToDown(), gridBagConstraints6);

			// add s.inoue 2011/09/02
			jPanel3.add(getJLabel_Info0(),gridBagConstraints11);
			jPanel3.add(getJLabel_Info1(),gridBagConstraints7);
			jPanel3.add(getJLabel_Info2(),gridBagConstraints10);
			jPanel3.add(getJLabel_Info3(),gridBagConstraints8);
			jPanel3.add(getJLabel_Info4(),gridBagConstraints9);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton_ToSort
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ToSort() {
		if (jButton_Sort == null) {
			jButton_Sort = new ExtendedButton();
			jButton_Sort.setFont(new Font("Sort", Font.BOLD, 14));
			jButton_Sort.setText("整列");
			jButton_Sort.addActionListener(this);
			jButton_Sort.setMnemonic(KeyEvent.VK_O);
		}
		return jButton_Sort;
	}

	/**
	 * This method initializes jButton_ToTop
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ToTop() {
		if (jButton_ToTop == null) {
			jButton_ToTop = new ExtendedButton();
			jButton_ToTop.setName("jButton1");
			jButton_ToTop.setFont(new Font("Dialog", Font.PLAIN, 24));
			jButton_ToTop.setText("↑");
			jButton_ToTop.addActionListener(this);
			jButton_ToTop.setMnemonic(KeyEvent.VK_W);
		}
		return jButton_ToTop;
	}

	/**
	 * This method initializes jButton_ToTop
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ToDown() {
		if (jButton_ToDown == null) {
			jButton_ToDown = new ExtendedButton();
			jButton_ToDown.setName("jButton4");
			jButton_ToDown.setFont(new Font("Dialog", Font.PLAIN, 24));
			jButton_ToDown.setText("↓");
			jButton_ToDown.addActionListener(this);
			jButton_ToDown.setMnemonic(KeyEvent.VK_E);
		}
		return jButton_ToDown;
	}
	/**
	 * This method initializes jButton_ToLeft
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ToLeft() {
		if (jButton_ToLeft == null) {
			jButton_ToLeft = new ExtendedButton();
			jButton_ToLeft.setName("jButton2");
			jButton_ToLeft.setFont(new Font("Dialog", Font.PLAIN, 24));
			jButton_ToLeft.setText("←");
			jButton_ToLeft.addActionListener(this);
			jButton_ToLeft.setMnemonic(KeyEvent.VK_A);
		}
		return jButton_ToLeft;
	}

	/**
	 * This method initializes jButton_ToRight
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ToRight() {
		if (jButton_ToRight == null) {
			jButton_ToRight = new ExtendedButton();
			jButton_ToRight.setName("jButton3");
			jButton_ToRight.setFont(new Font("Dialog", Font.PLAIN, 24));
			jButton_ToRight.setText("→");
			jButton_ToRight.addActionListener(this);
			jButton_ToRight.setMnemonic(KeyEvent.VK_D);
		}
		return jButton_ToRight;
	}

	/**
	 * This method initializes jButton_Sort
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private JLabel getJLabel_Info0() {
		if (jLabel_Info0 == null) {
			jLabel_Info0 = new JLabel();
			jLabel_Info0.setFont(new Font("Sort", Font.PLAIN, 10));
			// eidt s.inoue 2012/07/10
			jLabel_Info0.setText("(Alt+O)");
		}
		return jLabel_Info0;
	}

	/**
	 * This method initializes jButton_ToTop
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private JLabel getJLabel_Info1() {
		if (jLabel_Info1 == null) {
			jLabel_Info1 = new JLabel();
			jLabel_Info1.setFont(new Font("Dialog", Font.PLAIN, 10));
			jLabel_Info1.setText("(Alt+W)");
		}
		return jLabel_Info1;
	}

	/**
	 * This method initializes jButton_ToTop
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private JLabel getJLabel_Info2() {
		if (jLabel_Info2 == null) {
			jLabel_Info2 = new JLabel();
			jLabel_Info2.setFont(new Font("Dialog", Font.PLAIN, 10));
			jLabel_Info2.setText("(Alt+E)");
		}
		return jLabel_Info2;
	}

	/**
	 * This method initializes jButton_ToTop
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private JLabel getJLabel_Info3() {
		if (jLabel_Info3 == null) {
			jLabel_Info3 = new JLabel();
			jLabel_Info3.setFont(new Font("Dialog", Font.PLAIN, 10));
			jLabel_Info3.setText("(Alt+A)");
		}
		return jLabel_Info3;
	}

	/**
	 * This method initializes jButton_ToTop
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private JLabel getJLabel_Info4() {
		if (jLabel_Info4 == null) {
			jLabel_Info4 = new JLabel();
			jLabel_Info4.setFont(new Font("Dialog", Font.PLAIN, 10));
			jLabel_Info4.setText("(Alt+D)");
		}
		return jLabel_Info4;
	}
	/**
	 * This method initializes jPanel10
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("<<健診項目マスタの内容>>");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel10 = new JPanel();
			jPanel10.setLayout(new BoxLayout(getJPanel10(), BoxLayout.Y_AXIS));
			jPanel10.add(jLabel2, null);
			jPanel10.add(getJPanel_Right(), null);
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
			jLabel = new JLabel();
			jLabel.setText("<<選択パターンの内容>>");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BoxLayout(getJPanel11(), BoxLayout.Y_AXIS));
			jPanel11.add(jLabel, null);
			jPanel11.add(getJPanel_Left(), null);
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
			jPanel12 = new JPanel();
			jPanel12.setLayout(new BorderLayout());
			jPanel12.setName("jPanel12");
			jPanel12.add(getJPanel1(), BorderLayout.NORTH);
			jPanel12.add(getJPanel2(), BorderLayout.CENTER);
			// add s.inoue 2012/05/09
			jPanel12.add(getJPanel_hanrei(), BorderLayout.SOUTH);
		}
		return jPanel12;
	}

	@Override
	public void actionPerformed( ActionEvent e )
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

	/**
	 * This method initializes jTextField_PatternName
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_PatternName() {
		if (jTextField_PatternName == null) {
			jTextField_PatternName = new JTextField();
			// eidt s.inoue 2012/07/10
			jTextField_PatternName.setFocusable(false);
			jTextField_PatternName.setMaximumSize(new Dimension(700, 25));	// edit n.ohkubo 2014/10/01　追加
		}
		return jTextField_PatternName;
	}

	/**
	 * This method initializes jPanel_hanrei
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_hanrei() {
		if (jPanel_hanrei == null) {
			GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
			gridBagConstraints88.gridx = 0;
			gridBagConstraints88.gridy = 0;

			jLabel_hanrei1 = new ExtendedLabel();
			jLabel_hanrei1.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel_hanrei1.setText("凡例");
			jLabel_hanrei1.setPreferredSize(new Dimension(60, 16));

			GridBagConstraints gridBagConstraints86 = new GridBagConstraints();
			gridBagConstraints86.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints86.gridy = 0;
			gridBagConstraints86.gridx = 2;
			jLabel_hanrei2 = new ExtendedLabel();
			jLabel_hanrei2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_hanrei2.setText("基本健診項目");
			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
			gridBagConstraints84.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints84.gridy = 0;
			gridBagConstraints84.gridx = 1;

			GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
			gridBagConstraints89.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints89.gridy = 0;
			gridBagConstraints89.gridx = 4;
			jLabel_hanrei3 = new ExtendedLabel();
			jLabel_hanrei3.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_hanrei3.setText("詳細健診項目");
			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
			gridBagConstraints90.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints90.gridy = 0;
			gridBagConstraints90.gridx = 3;

			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints91.gridy = 0;
			gridBagConstraints91.gridx = 6;

			// add s.inoue 2012/05/09
			gridBagConstraints91.weightx = 1;
			gridBagConstraints91.anchor = GridBagConstraints.WEST;

			jLabel_hanrei4 = new ExtendedLabel();
			jLabel_hanrei4.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel_hanrei4.setText("追加健診項目");
			GridBagConstraints gridBagConstraints92 = new GridBagConstraints();
			gridBagConstraints92.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints92.gridy = 0;
			gridBagConstraints92.gridx = 5;

			jLabel_kihonExample = new ExtendedLabel();
			jLabel_kihonExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_kihonExample.setPreferredSize(new Dimension(50, 20));
			jLabel_kihonExample.setOpaque(true);
			jPanel_hanrei = new JPanel();
			jPanel_hanrei.setLayout(new GridBagLayout());
			jPanel_hanrei.setOpaque(false);
			jPanel_hanrei.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel_hanrei.add(jLabel_kihonExample, gridBagConstraints84);
			jPanel_hanrei.add(jLabel_hanrei2, gridBagConstraints86);
			jPanel_hanrei.add(jLabel_hanrei1, gridBagConstraints88);

			jLabel_syosaiExample = new ExtendedLabel();
			jLabel_syosaiExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_syosaiExample.setPreferredSize(new Dimension(50, 20));
			jLabel_syosaiExample.setOpaque(true);

			jPanel_hanrei.add(jLabel_syosaiExample, gridBagConstraints90);
			jPanel_hanrei.add(jLabel_hanrei3, gridBagConstraints89);

			jLabel_addExample = new ExtendedLabel();
			jLabel_addExample.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jLabel_addExample.setPreferredSize(new Dimension(50, 20));
			jLabel_addExample.setOpaque(true);

			jPanel_hanrei.add(jLabel_addExample, gridBagConstraints92);
			jPanel_hanrei.add(jLabel_hanrei4, gridBagConstraints91);

			// add s.inoue 2012/05/09
			// this.jTextField_kikanNumber.setBackground(requiedItemColor);
			jLabel_syosaiExample.setBackground(ViewSettings.getSyosaiItemBgColor());
			jLabel_kihonExample.setBackground(ViewSettings.getKihonItemBgColor());
			jLabel_addExample.setBackground(ViewSettings.getTuikaItemBgColor());
		}
		return jPanel_hanrei;
	}

	/**
	 * This method initializes jPanel_Left
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Left() {
		if (jPanel_Left == null) {
			jPanel_Left = new JPanel();
			jPanel_Left.setLayout(new BorderLayout());
		}
		return jPanel_Left;
	}

	/**
	 * This method initializes jPanel_Right
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Right() {
		if (jPanel_Right == null) {
			jPanel_Right = new JPanel();
			jPanel_Right.setLayout(new BorderLayout());
		}
		return jPanel_Right;
	}


}