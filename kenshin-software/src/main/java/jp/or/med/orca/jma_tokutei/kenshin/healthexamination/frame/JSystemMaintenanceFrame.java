package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
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

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
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
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected ExtendedLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JPanel jPanel_Left = null;
	protected JPanel jPanel_Right = null;
	protected JPanel jPanel_Center = null;
	protected ExtendedButton jButton_Maintenance = null;
	protected ExtendedButton jButton_Usability = null;
	protected ExtendedButton jButton_Backup = null;

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
		/* Modified 2008/03/10 若月  */
		/* --------------------------------------------------- */
//		this.setSize(800, 600);
//		this.setContentPane(getJPanel_Content());
//		this.setTitle("特定健診システム");
//		setLocationRelativeTo( null );
//		this.setVisible(true);
		/* --------------------------------------------------- */
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

			/* Modified 2008/03/10 若月  */
			/* --------------------------------------------------- */
//			jLabel_Title = new ExtendedLabel();
//			jLabel_Title.setText("システムメンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			/* --------------------------------------------------- */
			jLabel_Title = new TitleLabel("tokutei.systemmaintenance-menu.frame.title");
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

			jPanel_Center.add(getJButton_Usability(),gridBagConstraints1);
			jPanel_Center.add(getJButton_Maintenance(), gridBagConstraints2);
			jPanel_Center.add(getJButton_Backup(), gridBagConstraints3);
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
			jButton_Usability = new ExtendedButton();
			jButton_Usability.setText("７-１．ユーザビリティメンテナンス(1)");
			jButton_Usability.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Usability.addActionListener(this);
			jButton_Usability.addKeyListener(this);
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
			jButton_Maintenance = new ExtendedButton();
			jButton_Maintenance.setText("７-２．システム利用者メンテナンス(2)");
			jButton_Maintenance.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Maintenance.addActionListener(this);
			jButton_Maintenance.addKeyListener(this);
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
			jButton_Backup = new ExtendedButton();
			jButton_Backup.setText("７-３．バックアップ＆復元(3)");
			jButton_Backup.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Backup.addActionListener(this);
			jButton_Backup.addKeyListener(this);
			jButton_Backup.setMnemonic(KeyEvent.VK_3);
		}
		return jButton_Backup;
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
