package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedPasswordField;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.GuidanceLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedToggleButton;
import java.awt.Insets;
import javax.swing.BorderFactory;


/**
 * ログインフレーム
 */
public class JLoginFrame extends JFrame implements ActionListener,KeyListener {

	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected ExtendedLabel jLabel_Title = null;
	protected ExtendedButton jButton_End = null;
	protected GuidanceLabel jLabel_MainExpl = null;
	protected ExtendedButton jButton_Login = null;
	protected JPanel jPanel = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedTextField jTextField_UserName = null;
	protected ExtendedPasswordField jPasswordField_Password = null;

	/**
	 * This is the default constructor
	 */
	public  JLoginFrame() {
		super();
		this.initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(ViewSettings.getAdminLoginFrameSize());
		this.setContentPane(getJPanel_Content());
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		setLocationRelativeTo( null );
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
			// eidt s.inoue 2011/04/08
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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints3.gridx = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Login(), gridBagConstraints1);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints2);
			// del y.okano 2010/05/24
			//jPanel_ButtonArea.add(getJButton_Version(), gridBagConstraints3);
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
//			jButton_End.setPreferredSize(new Dimension(100, 25));
//			jButton_End.setText("終了(F1)");
//			jButton_End.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Exit);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","終了(E)","終了(ALT+E)",KeyEvent.VK_E,icon);
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

			jLabel_MainExpl = new GuidanceLabel("admin.login.frame.guidance.main");
			jLabel_Title = new TitleLabel("admin.login.frame.title");

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
			jLabal_SubExpl.setText("終了ボタンを押すと、操作を終了することができます。");
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
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("パスワード");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1.setName("jLabel1");
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(10);
			cardLayout.setVgap(10);
			jPanel_MainArea = new JPanel();
			jPanel_MainArea.setLayout(cardLayout);
			jPanel_MainArea.add(getJPanel(), getJPanel().getName());
		}
		return jPanel_MainArea;
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
//			jButton_Login.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Login.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Login_Login);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Login= new ExtendedButton(
					"Login","ログイン(L)","ログイン(ALT+L)",KeyEvent.VK_L,icon);
			jButton_Login.addActionListener(this);
		}
		return jButton_Login;
	}

	/*
	 * FrameSize Control
	 */
	public void validate()
	{
		Rectangle rect = getBounds();

		super.validate();

		if( ViewSettings.getAdminLoginFrameWidth() > rect.width  ||
				ViewSettings.getAdminLoginFrameHeight() > rect.height )
		{
			setBounds( rect.x,
					   rect.y,
					   ViewSettings.getAdminLoginFrameWidth(),
					   ViewSettings.getAdminLoginFrameHeight() );
		}
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			jLabel = new ExtendedLabel();
			jLabel.setText("ユーザ名");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel.setName("jLabel");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setName("jPanel");
			jPanel.add(jLabel, gridBagConstraints);
			jPanel.add(jLabel1, gridBagConstraints8);
			jPanel.add(getJTextField_UserName(), gridBagConstraints9);
			jPanel.add(getJPasswordField_Password(), gridBagConstraints10);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField_UserName
	 *
	 * @return javax.swing.EventHandleTextField
	 */
	private ExtendedTextField getJTextField_UserName() {
		if (jTextField_UserName == null) {
			jTextField_UserName = new ExtendedTextField();
			jTextField_UserName.addActionListener(this);
		}
		return jTextField_UserName;
	}

	/**
	 * This method initializes jPasswordField_Password
	 *
	 * @return javax.swing.EventHandlePasswordField
	 */
	private ExtendedPasswordField getJPasswordField_Password() {
		if (jPasswordField_Password == null) {
			jPasswordField_Password = new ExtendedPasswordField();
			jPasswordField_Password.addActionListener(this);
		}
		return jPasswordField_Password;
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
