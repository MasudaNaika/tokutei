package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedPasswordField;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedToggleButton;
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
 *
 * Modified 2008/03/07 若月 画面コンポーネントを UI 改良版に置き換えた。
 */
public class JLoginFrame extends JFrame implements ActionListener, KeyListener
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
	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
//	protected ExtendedTextField jTextField_KikanNumber = null;
	protected ExtendedTextField jTextField_UserName = null;
	// add s.inoue 2009/12/18
	protected ExtendedButton jButton_Setting = null;
	// add s.inoue 2010/08/02
	protected ExtendedButton jButton_UpdateInfo = null;

	protected ExtendedButton jButton_Login = null;
	protected ExtendedPasswordField jPasswordField_Password = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected ExtendedLabel jLabel2 = null;
	protected ExtendedComboBox jComboBox_kikanNumber = null;
	// del y.okano 2010/05/24
	//protected ExtendedToggleButton jButton_Version = null;

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
		/* Modified 2008/03/10 若月  */
		/* --------------------------------------------------- */
//		this.setSize(600, 280);
//		this.setContentPane(getJPanel_Content());
//		this.setTitle("特定健診システム");
//		setLocationRelativeTo( null );
//		this.setVisible(true);
		/* --------------------------------------------------- */
		this.setContentPane(getJPanel_Content());
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(ViewSettings.getTokuteiLoginFrameSize());
		this.setLocationRelativeTo( null );
//		this.setVisible(true);
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
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_End() {
		if (jButton_End == null) {
			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setPreferredSize(new Dimension(75, 25));
			jButton_End.setText("終了(F1)");
			jButton_End.addActionListener(this);
			// edit s.inoue 2010/11/05
			jButton_End.setMnemonic(KeyEvent.VK_F1);
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

			/* Modified 2008/03/11 若月  */
			/* --------------------------------------------------- */
//			jLabel_MainExpl = new ExtendedLabel();
////			　2008/03/04 yabu
//			/*
//						jLabel_MainExpl.setText("健診機関番号、ユーザー名、パスワードを入力後、確認ボタンを押してください。");
//			*/
//						jLabel_MainExpl.setText("健診機関番号、ユーザー名、パスワードを入力後、ログインを押してください。");
////			　2008/03/04 yabu
//						jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//						jLabel_MainExpl.setName("jLabel1");
			/* --------------------------------------------------- */
			jLabel_MainExpl = new GuidanceLabel("tokutei.login.frame.guidance.main");
			/* --------------------------------------------------- */

			/* Modified 2008/03/10 若月  */
			/* --------------------------------------------------- */
//			jLabel_Title = new ExtendedLabel();
//			jLabel_Title.setText("特定健診ログイン");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			/* --------------------------------------------------- */
			jLabel_Title = new TitleLabel("tokutei.login.frame.title");
			/* --------------------------------------------------- */

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

			/* Deleted 2008/03/09 若月 機関番号をコンボボックスから取得するように変更 */
			/* --------------------------------------------------- */
//			jPanel1.add(getJTextField_KikanNumber(), gridBagConstraints2);
			/* --------------------------------------------------- */
		}
		return jPanel1;
	}

	/* Deleted 2008/03/09 若月 機関番号をコンボボックスから取得するように変更 */
	/* --------------------------------------------------- */
//	/**
//	 * This method initializes jTextField_KikanNumber
//	 *
//	 * @return javax.swing.EventHandleTextField
//	 */
//	private ExtendedTextField getJTextField_KikanNumber() {
//		if (jTextField_KikanNumber == null) {
//			jTextField_KikanNumber = new ExtendedTextField("",10);
//			jTextField_KikanNumber.addActionListener(this);
//		}
//		return jTextField_KikanNumber;
//	}
	/* --------------------------------------------------- */

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

	// add s.inoue 2010/08/02
	/**
	 * This method initializes jButton_Setting
	 *
	 * @return javax.swing.EventHandleButton
	 */
	private ExtendedButton getJButton_UpdateInfo() {
		if (jButton_UpdateInfo == null) {
			jButton_UpdateInfo = new ExtendedButton();
			jButton_UpdateInfo.setText("更新情報(F7)");
			jButton_UpdateInfo.setPreferredSize(new Dimension(120, 25));
			jButton_UpdateInfo.setVisible(true);
			jButton_UpdateInfo.addActionListener(this);
			// edit s.inoue 2010/11/05
			jButton_UpdateInfo.setMnemonic(KeyEvent.VK_F7);
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
			jButton_Setting = new ExtendedButton();
			jButton_Setting.setText("環境設定(F8)");
			jButton_Setting.setPreferredSize(new Dimension(120, 25));
			jButton_Setting.setVisible(true);
			jButton_Setting.addActionListener(this);
			// edit s.inoue 2010/11/05
			jButton_Setting.setMnemonic(KeyEvent.VK_F8);
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
			jButton_Login = new ExtendedButton();
			jButton_Login.setText("ログイン(F12)");
//			jButton_Login.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Login.setPreferredSize(new Dimension(120, 25));
			jButton_Login.setVisible(true);
			jButton_Login.addActionListener(this);
			// edit s.inoue 2010/11/05
			jButton_Login.setMnemonic(KeyEvent.VK_F12);
		}
		return jButton_Login;
	}

	/**
	 * This method initializes jButton_Version
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	// del y.okano 2010/05/24
	//private ExtendedToggleButton getJButton_Version() {
	//	if (jButton_Version == null) {
	//		jButton_Version = new ExtendedToggleButton();
//			jButton_Version.setFont(new Font("Dialog", Font.PLAIN, 12));
	//		jButton_Version.setPreferredSize(new Dimension(120, 25));
	//		jButton_Version.setText("バージョン(F9)");
	//		jButton_Version.setVisible(true);
	//		jButton_Version.addActionListener(this);
	//	}
	//	return jButton_Version;
	//}

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

		/* Modified 2008/03/09 若月  */
		/* --------------------------------------------------- */
//		if( ViewSettings.LOGINFRAME_MINSIZE_W > rect.width  ||
//			ViewSettings.LOGINFRAME_MINSIZE_H > rect.height )
//		{
//			setBounds( rect.x,
//					   rect.y,
//					   ViewSettings.LOGINFRAME_MINSIZE_W,
//					   ViewSettings.LOGINFRAME_MINSIZE_H );
//		}
		/* --------------------------------------------------- */
		if( ViewSettings.getTokuteiLoginFrameWidth() > rect.width  ||
				ViewSettings.getTokuteiLoginFrameHeight() > rect.height )
			{
				setBounds( rect.x,
						   rect.y,
						   ViewSettings.getTokuteiLoginFrameWidth(),
						   ViewSettings.getTokuteiLoginFrameHeight() );
			}
		/* --------------------------------------------------- */
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
