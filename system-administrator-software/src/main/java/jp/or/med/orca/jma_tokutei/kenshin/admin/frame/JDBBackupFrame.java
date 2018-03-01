package jp.or.med.orca.jma_tokutei.kenshin.admin.frame;
import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * データベースバックアップ
 */
public class JDBBackupFrame extends JFrame implements ActionListener,KeyListener
{
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
	protected JPanel jPanel_Table = null;
	protected ExtendedButton jButton_Reload = null;
	protected ExtendedButton jButton_Backup = null;
	protected ExtendedButton jButton_Restore = null;
	protected ExtendedButton jButton_Delete = null;
	protected FlowLayout flowLayoutPanel = new FlowLayout();

	/**
	 * This is the default constructor
	 */
	public JDBBackupFrame() {
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
		this.setTitle(ViewSettings.getAdminFrameCommonTitle());
		this.setPreferredSize(ViewSettings.getFrameCommonSize());
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
			jLabel_Title = new TitleLabel("admin.backup-mastermaintenance.frame.title","admin.backup-mastermaintenance.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.NORTH);
//			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel_Table(), BorderLayout.CENTER);
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
			// eidt s.inoue 2011/04/08
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = 0;
//			gridBagConstraints4.gridx = 0;
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints3.gridx = 4;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints2.gridx = 3;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridy = 0;
//			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints1.gridx = 2;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.anchor = GridBagConstraints.WEST;
//			gridBagConstraints.gridx = 1;
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
////			jPanel_ButtonArea.add(getJButton_Reload(), gridBagConstraints);
//			jPanel_ButtonArea.add(getJButton_Backup(), gridBagConstraints1);
//			jPanel_ButtonArea.add(getJButton_Restore(), gridBagConstraints2);
//			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints3);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints4);


// eidt s.inoue 2012/11/13
//		    jPanel_ButtonArea = new JPanel();
//
//		    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
//		    jPanel_ButtonArea.setLayout(flowLayoutPanel);
//		    jPanel_ButtonArea.add(getJButton_End());
//			jPanel_ButtonArea.add(getJButton_Backup());
//			jPanel_ButtonArea.add(getJButton_Restore());
//			jPanel_ButtonArea.add(getJButton_Delete());
//
//			Box recordBox = new Box(BoxLayout.X_AXIS);
//			recordBox.add(jPanel_ButtonArea);
//
//			Box buttonBox = Box.createVerticalBox();
//			buttonBox.add(recordBox);
//
//			// eidt s.inoue 2012/11/13
//			jPanel_ButtonArea.add(getJPanel_TitleArea());

			// add s.inoue 2012/11/13
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridy = 0;
			gridBagConstraints53.gridx = 0;
			gridBagConstraints53.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints53.anchor = GridBagConstraints.WEST;
			gridBagConstraints53.gridwidth=5;

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints3.gridx = 4;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.weightx = 1.0D;

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints2.gridx = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints1.gridx = 2;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 1;
//			gridBagConstraints.weightx = 1.0D;
//			gridBagConstraints.anchor = GridBagConstraints.WEST;
//			gridBagConstraints.gridx = 1;
		    jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());

		    jPanel_ButtonArea.add(getJButton_End(),gridBagConstraints4);
			jPanel_ButtonArea.add(getJButton_Backup(),gridBagConstraints1);
			jPanel_ButtonArea.add(getJButton_Restore(),gridBagConstraints2);
			jPanel_ButtonArea.add(getJButton_Delete(),gridBagConstraints3);
			// add s.inoue 2012/11/13
			jPanel_ButtonArea.add(getJPanel_TitleArea(), gridBagConstraints53);

			Box recordBox = new Box(BoxLayout.X_AXIS);
			recordBox.add(jPanel_ButtonArea);

			Box buttonBox = Box.createVerticalBox();
			buttonBox.add(recordBox);
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
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End = new ExtendedButton("戻る(R)", "戻る(R)", KeyEvent.VK_R, icon);
			jButton_End.addActionListener(this);
		}
		return jButton_End;
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
//			jLabel_MainExpl.setText("システムDBバックアップのメンテナンスを行います。");
//			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
//			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("システムDBバックアップ");
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

	/**
	 * This method initializes jPanel_ExplArea2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ExplArea2() {
		if (jPanel_ExplArea2 == null) {
			jLabal_SubExpl = new JLabel();
			jLabal_SubExpl.setText(" ");
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
	 * This method initializes jPanel_Table
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Table() {
		if (jPanel_Table == null) {
			CardLayout cardLayout = new CardLayout();
//			cardLayout.setVgap(10);
//			cardLayout.setHgap(10);
			jPanel_Table = new JPanel();
			jPanel_Table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel_Table.setLayout(cardLayout);
		}
		return jPanel_Table;
	}

//	/**
//	 * This method initializes jButton_Reload
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Reload() {
//		if (jButton_Reload == null) {
//			jButton_Reload = new ExtendedButton();
//			jButton_Reload.setText("再表示(F7)");
//			jButton_Reload.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Reload.addActionListener(this);
//		}
//		return jButton_Reload;
//	}

	/**
	 * This method initializes jButton_Backup
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Backup() {
		if (jButton_Backup == null) {
//			jButton_Backup = new ExtendedButton();
//			jButton_Backup.setText("バックアップ(F9)");
//			jButton_Backup.setPreferredSize(new Dimension(130, 25));
//			jButton_Backup.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Backup.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Export);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Backup = new ExtendedButton("ﾊﾞｯｸｱｯﾌﾟ(B)", "ﾊﾞｯｸｱｯﾌﾟ(B)", KeyEvent.VK_B, icon);
			jButton_Backup.addActionListener(this);
		}
		return jButton_Backup;
	}

	/**
	 * This method initializes jButton_Restore
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Restore() {
		if (jButton_Restore == null) {
//			jButton_Restore = new ExtendedButton();
//			jButton_Restore.setText("復元(F11)");
//			jButton_Restore.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Restore.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Import);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Restore = new ExtendedButton("復元(C)", "復元(C)", KeyEvent.VK_C, icon);
			jButton_Restore.addActionListener(this);
		}
		return jButton_Restore;
	}

	/**
	 * This method initializes jButton_Delete
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Delete() {
		if (jButton_Delete == null) {
//			jButton_Delete = new ExtendedButton();
//			jButton_Delete.setText("削除(F12)");
//			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Delete.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Delete);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Delete = new ExtendedButton("削除(D)", "削除(D)", KeyEvent.VK_D, icon);
			jButton_Delete.addActionListener(this);
		}
		return jButton_Delete;
	}

//	/*
//	 * FrameSize Control
//	 */
//	public void validate()
//	{
//		Rectangle rect = getBounds();
//
//		super.validate();
//
//		if( ViewSettings.getFrameCommonWidth() > rect.width  ||
//			ViewSettings.getFrameCommonHeight() > rect.height )
//		{
//			setBounds( rect.x,
//					   rect.y,
//					   ViewSettings.getFrameCommonWidth(),
//					   ViewSettings.getFrameCommonHeight() );
//		}
//	}

	public void actionPerformed( ActionEvent e )
	{
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
