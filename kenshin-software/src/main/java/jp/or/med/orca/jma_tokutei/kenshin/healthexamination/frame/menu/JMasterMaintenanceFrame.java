package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;

/**
 * マスタメンテナンス画面
 */
public class JMasterMaintenanceFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_center = null;
	protected ExtendedButton jButton_KikanMaintenance = null;
	protected ExtendedButton jButton_TeikeiMaintenance = null;
	protected ExtendedButton jButton_KenshinPatternMaintenance = null;
	protected ExtendedButton jButton_KenshinKoumokuMaintenance = null;
	protected ExtendedButton jButton_HokenjyaMaintenance = null;
	protected ExtendedButton jButton_KensaCenterCodeMaintenance = null;
	protected ExtendedButton jButton_ShiharaiMaintenance = null;
	protected ExtendedButton jButton_NayoseMaintenance = null;

	/**
	 * This is the default constructor
	 */
	public JMasterMaintenanceFrame() {
		super();
		this.initialize();
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
			// edit 20110317
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.gridx = 0;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints);
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
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
//			// add s.inoue 2009/12/02
//			jButton_End.addKeyListener(this);
//			jButton_End.setMnemonic(KeyEvent.VK_F1);
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
			CardLayout cardLayout2 = new CardLayout();
			cardLayout2.setHgap(10);
			cardLayout2.setVgap(10);
			jLabel_MainExpl = new ExtendedLabel();
			jLabel_MainExpl.setText("ボタンを押して、メンテナンスを実行します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel("tokutei.mastermaintenance-menu.frame.title");

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
			jLabal_SubExpl.setText("　");
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
			jPanel_MainArea.add(getJPanel_center(), BorderLayout.CENTER);
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_center
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_center() {
		if (jPanel_center == null) {
			/* 右側のボタン配置 */
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.weighty = 1.0d;
			gridBagConstraints7.anchor = GridBagConstraints.NORTH;
			gridBagConstraints7.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints7.gridy = 6;
			gridBagConstraints7.gridx = 0;

			/* 左側のボタン配置 */
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
//			gridBagConstraints8.anchor = GridBagConstraints.NORTH;
//			gridBagConstraints8.weighty = 1.0d;
			gridBagConstraints8.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.gridx = 1;

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.weighty = 1.0d;
//			gridBagConstraints6.anchor = GridBagConstraints.NORTH;
			gridBagConstraints6.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints6.gridy = 5;
			gridBagConstraints6.gridx = 0;

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.weighty = 1.0d;
//			gridBagConstraints5.anchor = GridBagConstraints.NORTH;
			gridBagConstraints5.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints5.gridy = 4;
			gridBagConstraints5.gridx = 0;

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.weighty = 1.0d;
//			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints4.gridy = 3;
			gridBagConstraints4.gridx = 0;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.weighty = 1.0d;
//			gridBagConstraints3.anchor = GridBagConstraints.NORTH;
			gridBagConstraints3.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.gridx = 0;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.weighty = 1.0d;
//			gridBagConstraints2.anchor = GridBagConstraints.NORTH;
			gridBagConstraints2.insets = new Insets(10, 0, 0, 0);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.gridx = 0;

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.weighty = 1.0d;
//			gridBagConstraints1.anchor = GridBagConstraints.NORTH;
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;

			jPanel_center = new JPanel();
			jPanel_center.setLayout(new GridBagLayout());
			jPanel_center.add(getJButton_KenshinKoumokuMaintenance(), gridBagConstraints1);
			jPanel_center.add(getJButton_HokenjyaMaintenance(), gridBagConstraints2);
			jPanel_center.add(getJButton_KenshinPatternMaintenance(), gridBagConstraints3);
			jPanel_center.add(getJButton_KensaCenterCodeMaintenance(), gridBagConstraints4);
			jPanel_center.add(getJButton_ShiharaiMaintenance(), gridBagConstraints5);
			jPanel_center.add(getJButton_KikanMaintenance(), gridBagConstraints6);
			jPanel_center.add(getJButton_NayoseMaintenance(), gridBagConstraints7);
			jPanel_center.add(getJButton_TeikeiMaintenance(), gridBagConstraints8);
		}
		return jPanel_center;
	}

	/**
	 * This method initializes jButton_KikanMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_KikanMaintenance() {
		if (jButton_KikanMaintenance == null) {

			// openswing s.inoue 2010/12/02
//			jButton_KikanMaintenance = new ExtendedButton();
//			jButton_KikanMaintenance.setText("６-５．機関情報メンテナンス(5)");
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-5.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_KikanMaintenance = new ExtendedButton("６-５．機関情報メンテナンス(5)",icon);
			jButton_KikanMaintenance.setPreferredSize(new Dimension(300, 70));
			// jButton_KikanMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_KikanMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_KikanMaintenance.addActionListener(this);
			jButton_KikanMaintenance.addKeyListener(this);
			jButton_KikanMaintenance.setMnemonic(KeyEvent.VK_5);
		}
		return jButton_KikanMaintenance;
	}

	/**
	 * This method initializes jButton_KikanMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_NayoseMaintenance() {
		if (jButton_NayoseMaintenance == null) {
			// openswing s.inoue 2010/12/02
//			jButton_NayoseMaintenance = new ExtendedButton();
//			jButton_NayoseMaintenance.setText("６-６．経年管理メンテナンス(6)");
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-6.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_NayoseMaintenance = new ExtendedButton("６-６．経年管理メンテナンス(6)",icon);
			jButton_NayoseMaintenance.setPreferredSize(new Dimension(300, 70));
			// jButton_NayoseMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_NayoseMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_NayoseMaintenance.addActionListener(this);
			jButton_NayoseMaintenance.addKeyListener(this);
			jButton_NayoseMaintenance.setMnemonic(KeyEvent.VK_6);
		}
		return jButton_NayoseMaintenance;
	}

	// add s.inoue 2009/11/25
	/**
	 * This method initializes jButton_KikanMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_TeikeiMaintenance() {
		if (jButton_TeikeiMaintenance == null) {
			// openswing s.inoue 2010/12/02
//			jButton_TeikeiMaintenance = new ExtendedButton();
//			jButton_TeikeiMaintenance.setText("６-７．所見マスタメンテナンス(7)");
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-7.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_TeikeiMaintenance = new ExtendedButton("６-７．所見マスタメンテナンス(7)",icon);
			jButton_TeikeiMaintenance.setPreferredSize(new Dimension(300, 70));
			jButton_TeikeiMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			// jButton_TeikeiMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_TeikeiMaintenance.addActionListener(this);
			jButton_TeikeiMaintenance.addKeyListener(this);
			jButton_TeikeiMaintenance.setMnemonic(KeyEvent.VK_7);
		}
		return jButton_TeikeiMaintenance;
	}

	/**
	 * This method initializes jButton_KenshinPatternMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_KenshinPatternMaintenance() {
		if (jButton_KenshinPatternMaintenance == null) {

			// openswing s.inoue 2010/12/02
//			jButton_KenshinPatternMaintenance = new ExtendedButton();
//			jButton_KenshinPatternMaintenance.setText("６-３．健診パターンメンテナンス(3)");
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-3.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_KenshinPatternMaintenance = new ExtendedButton("６-３．健診パターンメンテナンス(3)",icon);
			jButton_KenshinPatternMaintenance.setPreferredSize(new Dimension(300, 70));
			jButton_KenshinPatternMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			// jButton_KenshinPatternMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_KenshinPatternMaintenance.addActionListener(this);
			jButton_KenshinPatternMaintenance.addKeyListener(this);
			jButton_KenshinPatternMaintenance.setMnemonic(KeyEvent.VK_3);
		}
		return jButton_KenshinPatternMaintenance;
	}

	/**
	 * This method initializes jButton_KenshinKoumokuMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_KenshinKoumokuMaintenance() {
		if (jButton_KenshinKoumokuMaintenance == null) {

			// openswing s.inoue 2010/11/26
//			jButton_KenshinKoumokuMaintenance = new ExtendedButton();
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-1.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_KenshinKoumokuMaintenance = new ExtendedButton("６-１．健診項目情報メンテナンス(1)",icon);
			jButton_KenshinKoumokuMaintenance.setPreferredSize(new Dimension(300, 70));
			jButton_KenshinKoumokuMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			// jButton_KenshinKoumokuMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_KenshinKoumokuMaintenance.addActionListener(this);
			jButton_KenshinKoumokuMaintenance.addKeyListener(this);
			jButton_KenshinKoumokuMaintenance.setMnemonic(KeyEvent.VK_1);
		}
		return jButton_KenshinKoumokuMaintenance;
	}

	/**
	 * This method initializes jButton_HokenjyaMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_HokenjyaMaintenance() {
		if (jButton_HokenjyaMaintenance == null) {

			// openswing s.inoue 2010/12/02
//			jButton_HokenjyaMaintenance = new ExtendedButton();
//			jButton_HokenjyaMaintenance.setText("６-２．保険者情報メンテナンス(2)");
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-2.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_HokenjyaMaintenance = new ExtendedButton("６-２．保険者情報メンテナンス(2)",icon);

			jButton_HokenjyaMaintenance.setPreferredSize(new Dimension(300, 70));
			// jButton_HokenjyaMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_HokenjyaMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_HokenjyaMaintenance.addActionListener(this);
			jButton_HokenjyaMaintenance.addKeyListener(this);
			jButton_HokenjyaMaintenance.setMnemonic(KeyEvent.VK_2);
		}
		return jButton_HokenjyaMaintenance;
	}

	/**
	 * This method initializes jButton_KensaCenterCodeMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_KensaCenterCodeMaintenance() {
		if (jButton_KensaCenterCodeMaintenance == null) {
			jButton_KensaCenterCodeMaintenance = new ExtendedButton();
			jButton_KensaCenterCodeMaintenance.setText("６-４．検査センター項目コードメンテナンス");
			jButton_KensaCenterCodeMaintenance.setPreferredSize(new Dimension(300, 70));
			// jButton_KensaCenterCodeMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_KensaCenterCodeMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_KensaCenterCodeMaintenance.setVisible(false);
			jButton_KensaCenterCodeMaintenance.addActionListener(this);
		}
		return jButton_KensaCenterCodeMaintenance;
	}

	/**
	 * This method initializes jButton_ShiharaiMaintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ShiharaiMaintenance() {
		if (jButton_ShiharaiMaintenance == null) {

			// openswing s.inoue 2010/12/02
//			jButton_ShiharaiMaintenance = new ExtendedButton();
//			jButton_ShiharaiMaintenance.setText("６-４．支払代行情報メンテナンス(4)");
			ExtendedImageIcon iIcon = new ExtendedImageIcon("./Images/6-4.png");
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2);
			jButton_ShiharaiMaintenance = new ExtendedButton("６-４．支払代行情報メンテナンス(4)",icon);
			jButton_ShiharaiMaintenance.setPreferredSize(new Dimension(300, 70));

			// jButton_ShiharaiMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_ShiharaiMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_ShiharaiMaintenance.addActionListener(this);
			jButton_ShiharaiMaintenance.addKeyListener(this);
			jButton_ShiharaiMaintenance.setMnemonic(KeyEvent.VK_4);
		}
		return jButton_ShiharaiMaintenance;
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

		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
				ViewSettings.getFrameCommonHeight() > rect.height )
		{
			setBounds( rect.x,
					   rect.y,
					   ViewSettings.getFrameCommonWidth(),
					   ViewSettings.getFrameCommonHeight() );
		}
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
