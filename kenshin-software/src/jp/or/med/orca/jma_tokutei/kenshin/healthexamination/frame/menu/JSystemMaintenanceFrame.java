package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

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
import javax.swing.BorderFactory;

/**
 * システムメンテナンス画面
 */
public class JSystemMaintenanceFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected ExtendedButton jButton_Maintenance = null;
	protected ExtendedButton jButton_Usability = null;
	protected ExtendedButton jButton_Backup = null;
	// add s.inoue 2013/11/07
	protected ExtendedButton jButton_Log = null;
	protected TitleLabel jLabel_Title = null;
	protected JPanel jPanel_NaviArea = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_Left = null;
	protected JPanel jPanel_Right = null;
	protected JPanel jPanel_Center = null;

	/**
	 * This is the default constructor
	 */
	public JSystemMaintenanceFrame() {
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
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
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
			// edit 20110317
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
			jLabel_MainExpl.setText("ボタンを押して、各機能を実行します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel("tokutei.systemmaintenance-menu.frame.title");

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
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(10);
			cardLayout.setVgap(10);
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(cardLayout);
			jPanel_MainArea.add(getJPanel_DrawArea(), getJPanel_DrawArea().getName());
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
			jPanel_DrawArea.add(getJPanel_Left(), BorderLayout.WEST);
			jPanel_DrawArea.add(getJPanel_Right(), BorderLayout.EAST);
			jPanel_DrawArea.add(getJPanel_Center(), BorderLayout.CENTER);
		}
		return jPanel_DrawArea;
	}

	/**
	 * This method initializes jPanel_Left
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Left() {
		if (jPanel_Left == null) {
			jPanel_Left = new JPanel();
			jPanel_Left.setLayout(new GridBagLayout());
			jPanel_Left.setPreferredSize(new Dimension(200, 0));
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
			jPanel_Right.setLayout(new GridBagLayout());
			jPanel_Right.setPreferredSize(new Dimension(200, 0));
		}
		return jPanel_Right;
	}

	/**
	 * This method initializes jPanel_Center
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Center() {
		if (jPanel_Center == null) {
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(7);
			gridLayout2.setVgap(15);
			gridLayout2.setColumns(1);
			jPanel_Center = new JPanel();
			jPanel_Center.setLayout(gridLayout2);

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.gridx = 0;
			// add s.inoue 2013/11/07
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.gridx = 0;

			jPanel_Center.add(getJButton_Usability(),gridBagConstraints1);
			jPanel_Center.add(getJButton_Maintenance(), gridBagConstraints2);
			jPanel_Center.add(getJButton_Backup(), gridBagConstraints3);
			// add s.inoue 2013/11/07
			jPanel_Center.add(getJButton_Log(), gridBagConstraints4);
		}
		return jPanel_Center;
	}

	// add s.inoue 2009/11/25
	/**
	 * This method initializes jButton_Usability
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Usability() {
		if (jButton_Usability == null) {
			// edit 20110317
//			jButton_Usability = new ExtendedButton();
//			jButton_Usability.setText("７-１．ユーザビリティメンテナンス(1)");
//			jButton_Usability.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Usability.addActionListener(this);
//			jButton_Usability.addKeyListener(this);
//			jButton_Usability.setMnemonic(KeyEvent.VK_1);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_Usability);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_KENSHIN_FIX_ICON);
			jButton_Usability = new ExtendedButton("７-１．ユーザビリティメンテナンス(1)",icon);
			// jButton_Usability.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Usability.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_Usability.addActionListener(this);
			jButton_Usability.setMnemonic(KeyEvent.VK_1);
		}
		return jButton_Usability;
	}

	/**
	 * This method initializes jButton_Maintenance
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Maintenance() {
		if (jButton_Maintenance == null) {
			// edit 20110317
//			jButton_Maintenance = new ExtendedButton();
//			jButton_Maintenance.setText("７-２．システム利用者メンテナンス(2)");
//			jButton_Maintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Maintenance.addActionListener(this);
//			jButton_Maintenance.addKeyListener(this);
//			jButton_Maintenance.setMnemonic(KeyEvent.VK_2);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_User);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_KENSHIN_FIX_ICON);
			jButton_Maintenance = new ExtendedButton("７-２．システム利用者メンテナンス(2)",icon);
			// jButton_Maintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Maintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_Maintenance.addActionListener(this);
			jButton_Maintenance.setMnemonic(KeyEvent.VK_2);
		}
		return jButton_Maintenance;
	}

	/**
	 * This method initializes jButton_Backup
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Backup() {
		if (jButton_Backup == null) {
			// edit 20110317
//			jButton_Backup = new ExtendedButton();
//			jButton_Backup.setText("７-３．バックアップ＆復元(3)");
//			jButton_Backup.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Backup.addActionListener(this);
//			jButton_Backup.addKeyListener(this);
//			jButton_Backup.setMnemonic(KeyEvent.VK_3);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_Backup);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_KENSHIN_FIX_ICON);
			jButton_Backup = new ExtendedButton("７-３．バックアップ＆復元(3)",icon);
			// jButton_Backup.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Backup.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_Backup.addActionListener(this);
			jButton_Backup.setMnemonic(KeyEvent.VK_3);
		}
		return jButton_Backup;
	}

	// add s.inoue 2013/11/07
	/**
	 * This method initializes jButton_Backup
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Log() {
		if (jButton_Log == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_Log);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_KENSHIN_FIX_ICON);
			jButton_Log = new ExtendedButton("７-４．ログファイル管理(4)",icon);
			jButton_Log.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_Log.addActionListener(this);
			jButton_Log.setMnemonic(KeyEvent.VK_4);
		}
		return jButton_Log;
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

}  //  @jve:decl-index=0:visual-constraint="16,8"
