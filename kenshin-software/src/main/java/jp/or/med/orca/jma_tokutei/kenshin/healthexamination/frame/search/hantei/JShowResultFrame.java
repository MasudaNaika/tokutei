package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import java.awt.event.*;

import javax.swing.border.EtchedBorder;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import javax.swing.JViewport;
import javax.swing.JEditorPane;


/**
 * 健診結果表示画面
 */
public class JShowResultFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel3 = null;
	protected JPanel jPanel6 = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedLabel jLabel2 = null;
	protected ExtendedTextField jTextField_HihokenjyaCode = null;
	protected ExtendedTextField jTextField_Date = null;
	protected ExtendedLabel jLabel3 = null;
	protected ExtendedLabel jLabel4 = null;
	protected ExtendedTextField jTextField_HihokenjyaNumber = null;
	protected ExtendedTextField jTextField_KensaCenterName = null;
	protected JPanel jPanel13 = null;
	protected JPanel jPanel14 = null;
	protected ExtendedLabel jLabel6 = null;
	protected ExtendedButton jButton_Print = null;
	protected ExtendedButton jButton_Fix = null;
	protected ExtendedLabel jLabel5 = null;
	protected ExtendedTextField jTextField_Name = null;
	protected ExtendedLabel jLabel8 = null;
	protected ExtendedEditorPane jEditorPane_Comment = null;  //  @jve:decl-index=0:visual-constraint="10,620"
	protected JPanel jPanel15 = null;
	protected JPanel jPanel16 = null;
	protected ExtendedLabel jLabel9 = null;
	protected JScrollPane jScrollPane1 = null;
	protected ExtendedButton jButton_Next = null;
	protected ExtendedButton jButton_Prev = null;
	protected ExtendedTextField jTextField_PatternName = null;
	protected JPanel jPanel_TableArea = null;
	protected JPanel jPanel4 = null;
	protected ExtendedComboBox jComboBox_MetaboHantei = null;
	protected ExtendedComboBox jComboBox_HokenSidouLevel = null;
	private JEditorPane jEditorPane = null;
	private JPanel jPanel5 = null;

	/**
	 * This is the default constructor
	 */
	public JShowResultFrame() {
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
			// eidt s.inoue 2011/03/30
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
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			gridBagConstraints28.gridy = 0;
			gridBagConstraints28.gridx = 0;
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
			gridBagConstraints25.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints25.gridx = 3;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints9.gridx = 2;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints8.weightx = 1.0D;
//			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
			gridBagConstraints27.gridy = 0;
			gridBagConstraints27.gridx = 4;
			gridBagConstraints27.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints27.weightx = 1.0D;
			gridBagConstraints27.anchor = GridBagConstraints.WEST;

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Prev(), gridBagConstraints8);
			jPanel_ButtonArea.add(getJButton_Next(), gridBagConstraints9);
			jPanel_ButtonArea.add(getJButton_Print(), gridBagConstraints25);
			jPanel_ButtonArea.add(getJButton_Fix(), gridBagConstraints27);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints28);
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
			// eidt s.inoue 2011/03/30
//			jButton_End = new ExtendedButton();
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

	/**
	 * This method initializes jPanel_NaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_NaviArea() {
		if (jPanel_NaviArea == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints7.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 20, 0, 20);
			gridBagConstraints2.gridy = -1;
			gridBagConstraints2.ipadx = 312;
			gridBagConstraints2.gridx = -1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints6.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.insets = new Insets(0, 0, 5, 0);
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("検索条件を入力して「検索」ボタンを押して、健診結果の一覧を表示します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");

			/* Modified 2008/03/19 若月  */
			/* --------------------------------------------------- */
//			jLabel_Title = new ExtendedLabel();
//			jLabel_Title.setText("健診結果表示");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			/* --------------------------------------------------- */
			jLabel_Title = new TitleLabel("tokutei.showresult.frame.title");
			jLabel_Title.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
			/* --------------------------------------------------- */

			jLabal_SubExpl = new ExtendedLabel();
			jLabal_SubExpl.setText("一覧から受診者を選択し、画面下部のボタンを押して処理を実行します。");
			jLabal_SubExpl.setFont(new Font("Dialog", Font.PLAIN, 14));

			jPanel_NaviArea = new JPanel();
			jPanel_NaviArea.setLayout(new GridBagLayout());
			jPanel_NaviArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			jPanel_NaviArea.add(jLabel_Title, gridBagConstraints3);
			jPanel_NaviArea.add(jLabel_MainExpl, gridBagConstraints6);
			jPanel_NaviArea.add(jLabal_SubExpl, gridBagConstraints7);
		}
		return jPanel_NaviArea;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJPanel1(), gridBagConstraints4);
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
			jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
			jPanel1.add(getJPanel2(), BorderLayout.NORTH);
			jPanel1.add(getJPanel4(), BorderLayout.SOUTH);
			jPanel1.add(getJPanel14(),BorderLayout.CENTER);
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
			GridBagConstraints gridBagConstraints171 = new GridBagConstraints();
			gridBagConstraints171.gridx = 5;
			gridBagConstraints171.ipadx = 10;
			gridBagConstraints171.weightx = 1.0;
			gridBagConstraints171.anchor = GridBagConstraints.WEST;
			gridBagConstraints171.gridy = 0;
			GridBagConstraints gridBagConstraints161 = new GridBagConstraints();
			gridBagConstraints161.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints161.gridy = 0;
			gridBagConstraints161.gridx = 4;
			GridBagConstraints gridBagConstraints151 = new GridBagConstraints();
			gridBagConstraints151.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints151.gridy = 1;
			gridBagConstraints151.anchor = GridBagConstraints.WEST;
			gridBagConstraints151.gridx = 5;
			GridBagConstraints gridBagConstraints141 = new GridBagConstraints();
			gridBagConstraints141.anchor = GridBagConstraints.WEST;
			gridBagConstraints141.gridy = 0;
			gridBagConstraints141.gridx = 3;
			GridBagConstraints gridBagConstraints131 = new GridBagConstraints();
			gridBagConstraints131.anchor = GridBagConstraints.WEST;
			gridBagConstraints131.gridx = 4;
			gridBagConstraints131.gridy = 1;
			gridBagConstraints131.insets = new Insets(0, 20, 0, 0);
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 20, 0, 10);
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.gridx = 2;
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.anchor = GridBagConstraints.WEST;
			gridBagConstraints111.gridx = 3;
			gridBagConstraints111.gridy = 1;
			GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
			gridBagConstraints101.anchor = GridBagConstraints.WEST;
			gridBagConstraints101.gridx = 1;
			gridBagConstraints101.gridy = 1;
			gridBagConstraints101.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.anchor = GridBagConstraints.WEST;
			gridBagConstraints91.gridx = 1;
			gridBagConstraints91.gridy = 0;
			gridBagConstraints91.insets = new Insets(0, 10, 0, 0);
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.anchor = GridBagConstraints.WEST;
			gridBagConstraints81.gridy = 1;
			gridBagConstraints81.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints81.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 6;
			gridBagConstraints10.fill = GridBagConstraints.BOTH;
			gridBagConstraints10.gridy = 0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			jPanel2.add(getJPanel3(), gridBagConstraints10);
			jPanel2.add(jLabel, gridBagConstraints);
			jPanel2.add(jLabel1, gridBagConstraints1);
			jPanel2.add(jLabel2, gridBagConstraints81);
			jPanel2.add(getJTextField_HokenjyaCode(), gridBagConstraints91);
			jPanel2.add(getJTextField_PatternName(), gridBagConstraints101);
			jPanel2.add(getJTextField_Date(), gridBagConstraints111);
			jPanel2.add(jLabel3, gridBagConstraints12);
			jPanel2.add(jLabel4, gridBagConstraints131);
			jPanel2.add(getJTextField_HokenjyaNumber(), gridBagConstraints141);
			jPanel2.add(getJTextField_KikanNumber(), gridBagConstraints151);
			jPanel2.add(jLabel5, gridBagConstraints161);
			jPanel2.add(getJTextField_Name(), gridBagConstraints171);
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
			jPanel3.setLayout(new BorderLayout());

			jLabel2 = new ExtendedLabel();
			jLabel2.setText("健診実施日");
			jLabel2.setPreferredSize(new Dimension(100, 20));
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("健診パターン");
			jLabel1.setPreferredSize(new Dimension(100, 20));
			jLabel = new ExtendedLabel();
			jLabel.setText("被保険者証等記号");
			jLabel.setPreferredSize(new Dimension(100, 20));

			jLabel4 = new ExtendedLabel();

			/* Modified 2008/03/19 若月 表記修正 */
			/* --------------------------------------------------- */
//			jLabel4.setText("健診機関No.");
			/* --------------------------------------------------- */
			jLabel4.setText("検査センター名称");
			jLabel4.setVisible(false);
			/* --------------------------------------------------- */

			jLabel3 = new ExtendedLabel();
			jLabel3.setText("被保険者証等番号");
//			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel5 = new ExtendedLabel();
			jLabel5.setText("受診者氏名");
			jLabel5.setPreferredSize(new Dimension(70, 20));
//			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel8 = new ExtendedLabel();
			jLabel8.setText("総合コメント");
			jLabel8.setPreferredSize(new Dimension(120, 20));

//			jPanel6 = new JPanel();
//			jPanel6.setLayout(new GridBagLayout());
//			jPanel3.add(getJPanel6(), BorderLayout.WEST);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jTextField_HokenjyaCode
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HokenjyaCode() {
		if (jTextField_HihokenjyaCode == null) {
			jTextField_HihokenjyaCode = new ExtendedTextField("", 11, ImeMode.IME_OFF);
			jTextField_HihokenjyaCode.setPreferredSize(new Dimension(130, 20));
		}
		return jTextField_HihokenjyaCode;
	}

	/**
	 * This method initializes jTextField_Date
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_Date() {
		if (jTextField_Date == null) {
			jTextField_Date = new ExtendedTextField();
			jTextField_Date.setPreferredSize(new Dimension(100, 20));
		}
		return jTextField_Date;
	}

	/**
	 * This method initializes jTextField_HokenjyaNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_HokenjyaNumber() {
		if (jTextField_HihokenjyaNumber == null) {
			jTextField_HihokenjyaNumber = new ExtendedTextField();
			jTextField_HihokenjyaNumber.setPreferredSize(new Dimension(130, 20));
		}
		return jTextField_HihokenjyaNumber;
	}

	/**
	 * This method initializes jTextField_KikanNumber
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_KikanNumber() {
		if (jTextField_KensaCenterName == null) {
			jTextField_KensaCenterName = new ExtendedTextField();
			jTextField_KensaCenterName.setPreferredSize(new Dimension(130, 20));
			jTextField_KensaCenterName.setVisible(false);
		}
		return jTextField_KensaCenterName;
	}

	/**
	 * This method initializes jPanel14
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel14() {
		if (jPanel14 == null) {
			jPanel14 = new JPanel();
			jPanel14.setLayout(new BorderLayout());
			jPanel14.add(getJPanel_TableArea(), BorderLayout.CENTER);
		}
		return jPanel14;
	}

	/**
	 * This method initializes jButton_Print
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Print() {
		if (jButton_Print == null) {
			// eidt s.inoue 2011/03/30
//			jButton_Print = new ExtendedButton();
//			jButton_Print.setText("通知表印刷(F11)");
//			jButton_Print.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Print1);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Print= new ExtendedButton(
					"TutiPrint","通知表印刷(P)","通知表印刷(ALT+P)",KeyEvent.VK_P,icon);
			jButton_Print.addActionListener(this);
		}
		return jButton_Print;
	}

	/**
	 * This method initializes jButton_Fix
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Fix() {
		if (jButton_Fix == null) {
			// eidt s.inoue 2011/03/30
//			jButton_Fix = new ExtendedButton();
//			jButton_Fix.setText("修正(F12)");
//			jButton_Fix.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Detail);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Fix= new ExtendedButton(
					"KekkaEdit","修正(E)","修正(ALT+E)",KeyEvent.VK_E,icon);
			jButton_Fix.addActionListener(this);
		}
		return jButton_Fix;
	}

	/**
	 * This method initializes jTextField_Name
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_Name() {
		if (jTextField_Name == null) {
			jTextField_Name = new ExtendedTextField();
			jTextField_Name.setPreferredSize(new Dimension(130, 20));
		}
		return jTextField_Name;
	}

	/**
	 * This method initializes jEditorPane_Comment
	 *
	 * @return javax.swing.ExtendedEditorPane
	 */
	private ExtendedEditorPane getJEditorPane_Comment() {
		if (jEditorPane_Comment == null) {
			jEditorPane_Comment = new ExtendedEditorPane();
			jEditorPane_Comment.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return jEditorPane_Comment;
	}

	public void actionPerformed( ActionEvent e )
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
			jScrollPane1.setPreferredSize(new Dimension(100, 60));
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
	 * This method initializes jButton_Next
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Next() {
		if (jButton_Next == null) {
			// eidt s.inoue 2011/03/30
//			jButton_Next = new ExtendedButton();
//			jButton_Next.setText("次のデータ(F9)");
//			jButton_Next.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_InputJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Next= new ExtendedButton(
					"nextRecord","次のデータ(N)","次のデータ(ALT+N)",KeyEvent.VK_N,icon);
			jButton_Next.addActionListener(this);
		}
		return jButton_Next;
	}

	/**
	 * This method initializes jButton_Prev
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Prev() {
		if (jButton_Prev == null) {
			// eidt s.inoue 2011/03/30
//			jButton_Prev = new ExtendedButton();
//			jButton_Prev.setText("前のデータ(F7)");
//			jButton_Prev.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Kekka_InputJusinken);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Prev= new ExtendedButton(
					"preRecord","前のデータ(B)","前のデータ(ALT+B)",KeyEvent.VK_B,icon);
			jButton_Prev.addActionListener(this);
		}
		return jButton_Prev;
	}

	/**
	 * This method initializes jTextField_PatternName
	 *
	 * @return javax.swing.ExtendedTextField
	 */
	private ExtendedTextField getJTextField_PatternName() {
		if (jTextField_PatternName == null) {
			jTextField_PatternName = new ExtendedTextField();
			// edit ver2 s.inoue 2009/08/26
			jTextField_PatternName.setHorizontalAlignment(SwingConstants.LEFT);
			jTextField_PatternName.setPreferredSize(new Dimension(130, 20));
		}
		return jTextField_PatternName;
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
		}
		return jPanel_TableArea;
	}

	/**
	 * This method initializes jPanel4
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.weighty = 1.0D;
			gridBagConstraints5.ipady = 20;
			gridBagConstraints5.ipadx = 10;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.gridy = 0;
			gridBagConstraints26.gridheight = 3;
			gridBagConstraints26.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints26.fill = GridBagConstraints.BOTH;
			gridBagConstraints26.weightx = 1.0D;
			gridBagConstraints26.ipadx = -100;
			gridBagConstraints26.ipady = -100;
			gridBagConstraints26.gridx = 3;
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.gridx = 2;
			gridBagConstraints24.insets = new Insets(0, 20, 0, 0);
			gridBagConstraints24.gridy = 0;
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.gridy = 1;
			gridBagConstraints23.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints23.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints23.gridx = 1;
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridy = 0;
			gridBagConstraints22.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.gridx = 1;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 4;
			gridBagConstraints21.gridy = 1;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 3;
			gridBagConstraints20.gridy = 0;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridy = 1;
			gridBagConstraints19.gridx = 1;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.gridx = 1;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridy = 1;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 6;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.gridheight = 2;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.gridy = 0;

//			jLabel10 = new ExtendedLabel();
//			jLabel10.setText("（０：未判定　１：基準該当　２：予備軍該当　３：非該当　４：判定不能）");
//			jLabel10.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel10.setVisible(false);

			jLabel9 = new ExtendedLabel();
			jLabel9.setText("メタボリックシンドローム判定");
//			jLabel9.setFont(new Font("Dialog", Font.PLAIN, 12));

//			jLabel7 = new ExtendedLabel();
//			jLabel7.setText("（０：未判定　１：積極的支援　２：動機づけ支援　３：正常　４：判定不能 ）");
//			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jLabel7.setVisible(false);
			jLabel6 = new ExtendedLabel();
			jLabel6.setText("保健指導レベル");
			jLabel6.setPreferredSize(new Dimension(100, 20));
//			jLabel6.setFont(new Font("Dialog", Font.PLAIN, 12));

			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//			jPanel4.add(getJPanel15(), gridBagConstraints16);
			jPanel4.add(jLabel9, gridBagConstraints13);
			jPanel4.add(jLabel6, gridBagConstraints17);
			jPanel4.add(getJComboBox_MetaboHantei(), gridBagConstraints22);
			jPanel4.add(getJComboBox_HokenSidouLevel(), gridBagConstraints23);
			jPanel4.add(jLabel8, gridBagConstraints24);
			jPanel4.add(getJScrollPane1(), gridBagConstraints26);
			jPanel4.add(getJPanel5(), gridBagConstraints5);

//			jPanel4.add(getJTextField_MetaboHantei(), gridBagConstraints18);
//			jPanel4.add(getJTextField5(), gridBagConstraints19);


//			jPanel4.add(jLabel7, gridBagConstraints21);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jComboBox_MetaboHantei
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_MetaboHantei() {
		if (jComboBox_MetaboHantei == null) {
			jComboBox_MetaboHantei = new ExtendedComboBox();
			jComboBox_MetaboHantei.setPreferredSize(new Dimension(150, 20));
			jComboBox_MetaboHantei.setEnabled(false);
		}
		return jComboBox_MetaboHantei;
	}

	/**
	 * This method initializes jComboBox_HokenSidouLevel
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox
	 */
	private ExtendedComboBox getJComboBox_HokenSidouLevel() {
		if (jComboBox_HokenSidouLevel == null) {
			jComboBox_HokenSidouLevel = new ExtendedComboBox();
			jComboBox_HokenSidouLevel.setPreferredSize(new Dimension(150, 20));
			jComboBox_HokenSidouLevel.setEnabled(false);
		}
		return jComboBox_HokenSidouLevel;
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