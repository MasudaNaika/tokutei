package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.menu;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedPasswordField;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.GuidanceLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;

/**
 * ログイン画面
 */
public class JLoginFrame extends JFrame implements ActionListener, KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;  //  @jve:decl-index=0:
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;  //  @jve:decl-index=0:
	protected TitleLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;

	protected ExtendedTextField jTextField_UserName = null;
	protected ExtendedPasswordField jPasswordField_Password = null;

	protected ExtendedButton jButton_Setting = null;
	protected ExtendedButton jButton_UpdateInfo = null;
	protected ExtendedButton jButton_Login = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedLabel jLabel2 = null;
	protected ExtendedComboBox jComboBox_kikanNumber = null;
//	protected ExtendedTextField jTextField_KikanNumber = null;

	/**
	 * This is the default constructor
	 */
	public JLoginFrame() {
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
		this.setSize(ViewSettings.getTokuteiLoginFrameSize());
		this.setLocationRelativeTo( null );
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
			// add s.inoue 2010/08/02
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.weightx = 1.0D;

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints8.gridx = 3;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			// edit y.okano 2010/05/24
//			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 0);

			// add s.inoue 2009/12/18
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 0;
			// edit y.okano 2010/05/24
			gridBagConstraints5.gridx = 3;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());

			// add s.inoue 2010/08/02
			jPanel_ButtonArea.add(getJButton_UpdateInfo(), gridBagConstraints9);

			// add s.inoue 2009/12/18
			jPanel_ButtonArea.add(getJButton_Setting(), gridBagConstraints6);
			jPanel_ButtonArea.add(getJButton_Login(), gridBagConstraints5);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints7);
			// del y.okano 2010/05/24
			//jPanel_ButtonArea.add(getJButton_Version(), gridBagConstraints8);
		}
		return jPanel_ButtonArea;
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
			jLabel_MainExpl = new GuidanceLabel("tokutei.login.frame.guidance.main");
			jLabel_Title = new TitleLabel("tokutei.login.frame.title");
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
			jLabal_SubExpl.setText("終了ボタンを押すと、操作を中止することができます。");
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
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(10);
			cardLayout.setVgap(10);
			jPanel = new JPanel();
			jPanel.setLayout(cardLayout);
			jPanel.add(getJPanel1(), getJPanel1().getName());
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 0;
			jLabel2 = new ExtendedLabel();
			jLabel2.setText("機関番号");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints.gridy = 2;
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("パスワード");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridy = 1;
			jLabel = new ExtendedLabel();
			jLabel.setText("ユーザ名");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.weightx = 1.0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setName("jPanel1");
			jPanel1.add(jLabel, gridBagConstraints41);
			jPanel1.add(getJTextField_UserName(), gridBagConstraints3);
			jPanel1.add(jLabel1, gridBagConstraints);
			jPanel1.add(getJTextField_Password(), gridBagConstraints4);
			jPanel1.add(jLabel2, gridBagConstraints5);
			jPanel1.add(getJComboBox_kikanNumber(), gridBagConstraints1);

		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField_UserName
	 *
	 * @return javax.swing.EventHandleTextField
	 */
	private ExtendedTextField getJTextField_UserName() {
		if (jTextField_UserName == null) {
			jTextField_UserName = new ExtendedTextField(ImeMode.IME_OFF);
			jTextField_UserName.setPreferredSize(new Dimension(100, 20));
			jTextField_UserName.addActionListener(this);
		}
		return jTextField_UserName;
	}

	/**
	 * This method initializes jTextField_Password
	 *
	 * @return javax.swing.JPasswordField
	 */
	private ExtendedPasswordField getJTextField_Password() {
		if (jPasswordField_Password == null) {
			jPasswordField_Password = new ExtendedPasswordField();
			jPasswordField_Password.setPreferredSize(new Dimension(100, 20));
			jPasswordField_Password.addActionListener(this);
		}
		return jPasswordField_Password;
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
//			jButton_End.setText("終了(F1)");
//			jButton_End.addActionListener(this);
//			jButton_End.setMnemonic(KeyEvent.VK_F1);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Exit);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","終了(E)","終了(ALT+E)",KeyEvent.VK_E,icon);
			jButton_End.addActionListener(this);
		}
		return jButton_End;
	}

	/**
	 * This method initializes jButton_Setting
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_UpdateInfo() {
		if (jButton_UpdateInfo == null) {
			// eidt s.inoue 2011/04/08
//			jButton_UpdateInfo = new ExtendedButton();
//			jButton_UpdateInfo.setText("更新情報(F7)");
//			jButton_UpdateInfo.setPreferredSize(new Dimension(120, 25));
//			jButton_UpdateInfo.setVisible(true);
//			jButton_UpdateInfo.addActionListener(this);
//			jButton_UpdateInfo.setMnemonic(KeyEvent.VK_F7);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Version);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_UpdateInfo= new ExtendedButton(
					"UpdateInfo","更新情報(I)","更新情報(ALT+I)",KeyEvent.VK_I,icon);
			jButton_UpdateInfo.addActionListener(this);
		}
		return jButton_UpdateInfo;
	}

	/**
	 * This method initializes jButton_Setting
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Setting() {
		if (jButton_Setting == null) {
			// eidt s.inoue 2011/04/08
//			jButton_Setting = new ExtendedButton();
//			jButton_Setting.setText("環境設定(F8)");
//			jButton_Setting.setPreferredSize(new Dimension(120, 25));
//			jButton_Setting.setVisible(true);
//			jButton_Setting.addActionListener(this);
//			jButton_Setting.setMnemonic(KeyEvent.VK_F8);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Login_Setting);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Setting= new ExtendedButton(
					"Setting","環境設定(S)","環境設定(ALT+S)",KeyEvent.VK_S,icon);
			jButton_Setting.addActionListener(this);
		}
		return jButton_Setting;
	}

	/**
	 * This method initializes jButton_Login
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_Login() {
		if (jButton_Login == null) {
			// eidt s.inoue 2011/04/08
//			jButton_Login = new ExtendedButton();
//			jButton_Login.setText("ログイン(F12)");
//			jButton_Login.setPreferredSize(new Dimension(120, 25));
//			jButton_Login.setVisible(true);
//			jButton_Login.addActionListener(this);
//			jButton_Login.setMnemonic(KeyEvent.VK_F12);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Login_Login);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Login= new ExtendedButton(
					"Login","ログイン(L)","ログイン(ALT+L)",KeyEvent.VK_L,icon);
			jButton_Login.addActionListener(this);
		}
		return jButton_Login;
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
		if( ViewSettings.getTokuteiLoginFrameWidth() > rect.width  ||
				ViewSettings.getTokuteiLoginFrameHeight() > rect.height )
			{
				setBounds( rect.x,
						   rect.y,
						   ViewSettings.getTokuteiLoginFrameWidth(),
						   ViewSettings.getTokuteiLoginFrameHeight() );
			}
	}

	/**
	 * This method initializes jComboBox_kikanNumber
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox_kikanNumber() {
		if (jComboBox_kikanNumber == null) {
			jComboBox_kikanNumber = new ExtendedComboBox();
			jComboBox_kikanNumber.setPreferredSize(new Dimension(31, 20));
			jComboBox_kikanNumber.addKeyListener(this);
		}
		return jComboBox_kikanNumber;
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}
}
