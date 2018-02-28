package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.system;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JSeparator;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import java.awt.event.*;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class JKikanDBBackupFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected JLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_Table = null;

	// panel
	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();  //  @jve:decl-index=0:
	protected FlowLayout flowLayoutPanel = new FlowLayout();
	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();

	/* button */
	protected ExtendedButton jButton_Delete = null;
	protected ExtendedButton jButton_Restore = null;
	protected ExtendedButton jButton_Backup = null;
//	protected ExtendedButton jButton_Reload = null;
	protected ExtendedLabel jButton_Title = null;

	/**
	 * This is the default constructor
	 */
	public JKikanDBBackupFrame() {
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
// openswing s.inoue 2011/02/17
//			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
//			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
//			jPanel_Content.add(getJPanel_Table(), BorderLayout.CENTER);


//		    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
//		    buttonPanel.setLayout(flowLayoutPanel);
//		    buttonPanel.add(buttonClose, null);

		    // button
		    jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());

		    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
		    jPanel_ButtonArea.setLayout(flowLayoutPanel);
		    jPanel_ButtonArea.add(getJButton_End());
			jPanel_ButtonArea.add(getJButton_Backup());
			jPanel_ButtonArea.add(getJButton_Restore());
			jPanel_ButtonArea.add(getJButton_Delete());

			// original
			buttonOriginePanel.setLayout(flowLayoutOriginePanel);
			flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
//			buttonOriginePanel.add(getJButton_Reload());
//			buttonOriginePanel.add(getJButton_Delete());

// del s.inoue 2011/04/07
//			buttonOriginePanel.add(Box.createVerticalStrut(50));
//			ExtendedImageIcon icon = new ExtendedImageIcon(JPath.Ico_Common_Title1);
//			jButton_Title = new TitleLabel("tokutei.jushinken.frame.title",icon);
//			buttonOriginePanel.add(jButton_Title);

			// box1行目
			Box origineBox = new Box(BoxLayout.X_AXIS);
			origineBox.add(buttonOriginePanel);
			origineBox.add(Box.createVerticalStrut(2));

			// box2行目
			Box recordBox = new Box(BoxLayout.X_AXIS);
			recordBox.add(jPanel_ButtonArea);

			Box buttonBox = Box.createVerticalBox();
//			buttonBox.add(origineBox);
//			buttonBox.add(new JSeparator());
			buttonBox.add(recordBox);

			// add s.inoue 2012/11/12
			jLabel_Title = new TitleLabel("tokutei.backup-mastermaintenance.frame.title","tokutei.backup-mastermaintenance.frame.guidance");
			jLabel_Title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14));

			// openswing s.inoue 2011/02/17
			jPanel_Content.add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel_Table(), BorderLayout.CENTER);
		}
		return jPanel_Content;
	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
//	protected JPanel jPanel_TitleArea = null;
//	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea(Box buttonBox) {
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
			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}


	/**
	 * This method initializes jPanel_ButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ButtonArea() {
		if (jPanel_ButtonArea == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints3.gridx = 4;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints2.gridx = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints1.gridx = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.gridx = 1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			jPanel_ButtonArea.add(getJButton_Reload(), gridBagConstraints);
			jPanel_ButtonArea.add(getJButton_Backup(), gridBagConstraints1);
			jPanel_ButtonArea.add(getJButton_Restore(), gridBagConstraints2);
			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints3);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints4);
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

			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			// openswing s.inoue 2011/02/17
			jButton_End = new ExtendedButton("戻る(R)", "戻る(R)", KeyEvent.VK_R, icon);
			jButton_End.addActionListener(this);

//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
//			jButton_End.setMnemonic(KeyEvent.VK_R);
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
			jLabel_MainExpl = new JLabel();
			jLabel_MainExpl.setText("機関DBバックアップのメンテナンスを行います。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel();
			jLabel_Title.setText("機関DBバックアップ");
			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
			jLabel_Title.setBackground(new Color(153, 204, 255));
			jLabel_Title.setForeground(new Color(51, 51, 51));
			jLabel_Title.setOpaque(true);
			jLabel_Title.setName("jLabel");
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
			jLabal_SubExpl = new JLabel();
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
	 * This method initializes jButton_Delete
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Delete() {
		if (jButton_Delete == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Delete);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Delete = new ExtendedButton("削除(D)", "削除(D)", KeyEvent.VK_D, icon);
			jButton_Delete.addActionListener(this);

//			jButton_Delete = new ExtendedButton();
//			jButton_Delete.setText("削除(F12)");
//			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return jButton_Delete;
	}

	/**
	 * This method initializes jButton_Restore
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Restore() {
		if (jButton_Restore == null) {

			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Import);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Restore = new ExtendedButton("復元(C)", "復元(C)", KeyEvent.VK_C, icon);
			jButton_Restore.addActionListener(this);

//			jButton_Restore = new ExtendedButton();
//			jButton_Restore.setText("復元(F11)");
//			jButton_Restore.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return jButton_Restore;
	}

	/**
	 * This method initializes jButton_Backup
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Backup() {
		if (jButton_Backup == null) {

			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Export);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_Backup = new ExtendedButton("ﾊﾞｯｸｱｯﾌﾟ(B)", "ﾊﾞｯｸｱｯﾌﾟ(B)", KeyEvent.VK_B, icon);
			jButton_Backup.addActionListener(this);

//			jButton_Backup = new ExtendedButton();
//			jButton_Backup.setText("バックアップ(F9)");
//			jButton_Backup.setPreferredSize(new Dimension(130, 25));
//			jButton_Backup.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return jButton_Backup;
	}

//	/**
//	 * This method initializes jButton_Reload
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButton_Reload() {
//		if (jButton_Reload == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Reload);
//			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
//
//			jButton_Reload = new ExtendedButton("再表示(R)", "再表示(R)", KeyEvent.VK_R, icon);
//			jButton_Reload.addActionListener(this);
//
////			jButton_Reload = new ExtendedButton();
////			jButton_Reload.setText("再表示(F7)");
////			jButton_Reload.setFont(new Font("Dialog", Font.PLAIN, 12));
//		}
//		return jButton_Reload;
//	}

	/**
	 * This method initializes jPanel_Table
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Table() {
		if (jPanel_Table == null) {
			jPanel_Table = new JPanel();
			jPanel_Table.setLayout(new BorderLayout());
			jPanel_Table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		return jPanel_Table;
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
}