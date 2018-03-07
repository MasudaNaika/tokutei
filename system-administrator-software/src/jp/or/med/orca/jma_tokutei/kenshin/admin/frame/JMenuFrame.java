package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedToggleButton;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * メニュー
 */
public class JMenuFrame extends JFrame implements ActionListener,KeyListener{

	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected JLabel jLabel_Title = null;
	protected JLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_Center = null;
	protected ExtendedButton jButton_KikanMaintenance = null;
	protected ExtendedButton jButton_KanriUserMaintenance = null;
	protected ExtendedButton jButton_DBBackup = null;
	protected ExtendedToggleButton jButton_Version = null;
	protected ExtendedButton jButton_BackLogin = null;
	protected ExtendedButton jButton_NetworkDBConnection = null;	// edit n.ohkubo 2015/08/01　追加

	/**
	 * This is the default constructor
	 */
	public JMenuFrame() {
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
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
//			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
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
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints11.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints);
			jPanel_ButtonArea.add(getJButton_Version(), gridBagConstraints1);
			jPanel_ButtonArea.add(getJButton_BackLogin(), gridBagConstraints11);
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
			// eidt s.inoue 2011/04/08
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("終了(F1)");
//			jButton_End.addActionListener(this);
//			jButton_End.addKeyListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Exit);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","終了(E)","終了(ALT+E)",KeyEvent.VK_E,icon);
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}
	
	// edit n.ohkubo 2015/08/01　未使用なので削除　start
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
//			jLabel_MainExpl.setText("　");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("システム管理者用メンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
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
//
//	/**
//	 * This method initializes jPanel_ExplArea2
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ExplArea2() {
//		if (jPanel_ExplArea2 == null) {
//			jLabal_SubExpl = new JLabel();
//			jLabal_SubExpl.setText("　");
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
//
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
	// edit n.ohkubo 2015/08/01　未使用なので削除　end

	/**
	 * This method initializes jPanel_MainArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_MainArea() {
		if (jPanel_MainArea == null) {
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(new BorderLayout());
			jPanel_MainArea.add(getJPanel_Center(), BorderLayout.CENTER);
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_Center
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Center() {
		if (jPanel_Center == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.insets = new Insets(20, 0, 0, 0);
			gridBagConstraints4.gridx = 0;
			// eidt s.inoue 2012/07/06
//			gridBagConstraints4.weightx = 1.0D;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.insets = new Insets(20, 0, 0, 0);
			gridBagConstraints3.gridx = 0;
			// eidt s.inoue 2012/07/06
//			gridBagConstraints3.weightx = 1.0D;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 0;
			// eidt s.inoue 2012/07/06
//			gridBagConstraints2.weightx = 1.0D;
			
			// edit n.ohkubo 2015/08/01　追加　start
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.NORTH;
			gridBagConstraints5.weighty = 10.0D;
			// edit n.ohkubo 2015/08/01　追加　end

			jPanel_Center = new JPanel();
			jPanel_Center.setName("jPanel_Center");
			jPanel_Center.setLayout(new GridBagLayout());
			jPanel_Center.add(getJButton_KikanMaintenance(), gridBagConstraints2);
			jPanel_Center.add(getJButton_KanriUserMaintenance(), gridBagConstraints3);
			jPanel_Center.add(getJButton_DBBackup(), gridBagConstraints4);
			jPanel_Center.add(getJButton_NetworkDBConnection(), gridBagConstraints5);	// edit n.ohkubo 2015/08/01　追加
		}
		return jPanel_Center;
	}

	/**
	 * This method initializes jButton_KikanMaintenance
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_KikanMaintenance() {
		if (jButton_KikanMaintenance == null) {
			// eidt s.inoue 2011/04/08
//			jButton_KikanMaintenance = new ExtendedButton();
//			jButton_KikanMaintenance.setText("１．健診機関情報メンテナンス(1)");
//			jButton_KikanMaintenance.setPreferredSize(new Dimension(500, 50));
//			jButton_KikanMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_KikanMaintenance.addActionListener(this);
//			jButton_KikanMaintenance.addKeyListener(this);
//			jButton_KikanMaintenance.setMnemonic(KeyEvent.VK_1);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_Kikan);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_SYSTEM_FIX_ICON);
			jButton_KikanMaintenance = new ExtendedButton("１．健診機関情報メンテナンス(1)",icon);
			// jButton_KikanMaintenance.setFont(new Font("UserMaintenance", Font.PLAIN, 12));
			jButton_KikanMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			// jButton_KikanMaintenance.setPreferredSize(new Dimension(330, 70));
			jButton_KikanMaintenance.addActionListener(this);
			jButton_KikanMaintenance.setMnemonic(KeyEvent.VK_1);
		}
		return jButton_KikanMaintenance;
	}

	/**
	 * This method initializes jButton_KanriUserMaintenance
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_KanriUserMaintenance() {
		if (jButton_KanriUserMaintenance == null) {
			// eidt s.inoue 2011/04/08
//			jButton_KanriUserMaintenance = new ExtendedButton();
//			jButton_KanriUserMaintenance.setText("２．システム管理ユーザー情報メンテナンス(2)");
//			jButton_KanriUserMaintenance.setPreferredSize(new Dimension(500, 50));
//			jButton_KanriUserMaintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_KanriUserMaintenance.addActionListener(this);
//			jButton_KanriUserMaintenance.addKeyListener(this);
//			jButton_KanriUserMaintenance.setMnemonic(KeyEvent.VK_2);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_User);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_SYSTEM_FIX_ICON);
			jButton_KanriUserMaintenance = new ExtendedButton("２．システム管理ユーザー情報メンテナンス(2)",icon);
			// jButton_KanriUserMaintenance.setFont(new Font("UserMaintenance", Font.PLAIN, 12));
			jButton_KanriUserMaintenance.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			// jButton_KanriUserMaintenance.setPreferredSize(new Dimension(330, 70));
			jButton_KanriUserMaintenance.addActionListener(this);
			jButton_KanriUserMaintenance.setMnemonic(KeyEvent.VK_2);
		}
		return jButton_KanriUserMaintenance;
	}

	/**
	 * This method initializes jButton_DBBackup
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_DBBackup() {
		if (jButton_DBBackup == null) {
			// eidt s.inoue 2011/04/08
//			jButton_DBBackup = new ExtendedButton();
//			jButton_DBBackup.setText("３．システムDBバックアップ(3)");
//			jButton_DBBackup.setPreferredSize(new Dimension(500, 50));
//			jButton_DBBackup.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_DBBackup.addActionListener(this);
//			jButton_DBBackup.addKeyListener(this);
//			jButton_DBBackup.setMnemonic(KeyEvent.VK_3);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_Backup);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_SYSTEM_FIX_ICON);
			jButton_DBBackup = new ExtendedButton("３．バックアップ＆復元(3)",icon);
			// jButton_DBBackup.setFont(new Font("Backup", Font.PLAIN, 12));
			// jButton_DBBackup.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_DBBackup.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			// jButton_DBBackup.setPreferredSize(new Dimension(330, 70));
			jButton_DBBackup.addActionListener(this);
			jButton_DBBackup.setMnemonic(KeyEvent.VK_3);
		}
		return jButton_DBBackup;
	}
	
	// edit n.ohkubo 2015/08/01　追加　start
	private ExtendedButton getJButton_NetworkDBConnection() {
		if (jButton_NetworkDBConnection == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_System_NetworkDB);
			ImageIcon icon = iIcon.setStrechIcon(this, 1.2d);
			jButton_NetworkDBConnection = new ExtendedButton("４．DB接続情報メンテナンス(4)", icon);
			jButton_NetworkDBConnection.setFont(JApplication.FONT_COMMON_MENU_BUTTON);
			jButton_NetworkDBConnection.addActionListener(this);
			jButton_NetworkDBConnection.setMnemonic(KeyEvent.VK_4);
		}
		return jButton_NetworkDBConnection;
	}
	// edit n.ohkubo 2015/08/01　追加　end

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

	@Override
	public void actionPerformed(ActionEvent e)
	{
	}

	/**
	 * This method initializes jButton_Version
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedToggleButton
	 */
	private ExtendedToggleButton getJButton_Version() {
		if (jButton_Version == null) {
			// eidt s.inoue 2011/04/08
//			jButton_Version = new ExtendedToggleButton();
//			jButton_Version.setText("バージョン(F9)");
//			jButton_Version.setVisible(true);
//			jButton_Version.setPreferredSize(new Dimension(120, 25));
//			jButton_Version.addActionListener(this);
//			jButton_Version.addKeyListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Version);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Version= new ExtendedToggleButton(
					"Version","バージョン(V)","バージョン(ALT+V)",KeyEvent.VK_V,icon);
			jButton_Version.addActionListener(this);
			jButton_Version.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		}
		return jButton_Version;
	}

	/**
	 * This method initializes jButton_BackLogin
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private ExtendedButton getJButton_BackLogin() {
		if (jButton_BackLogin == null) {
			// eidt s.inoue 2011/04/08
//			jButton_BackLogin = new ExtendedButton();
//			jButton_BackLogin.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_BackLogin.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_BackLogin.setText("ログイン画面に戻る(F2)");
//			jButton_BackLogin.setPreferredSize(new Dimension(160, 25));
//			jButton_BackLogin.setActionCommand("\u7d42\u4e86");
//			jButton_BackLogin.addActionListener(this);
//			jButton_BackLogin.addKeyListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_BackLogin= new ExtendedButton(
					"Return","ログインへ(R)","ログインへ(ALT+R)",KeyEvent.VK_R,icon);
			jButton_BackLogin.addActionListener(this);
			jButton_BackLogin.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		}
		return jButton_BackLogin;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
