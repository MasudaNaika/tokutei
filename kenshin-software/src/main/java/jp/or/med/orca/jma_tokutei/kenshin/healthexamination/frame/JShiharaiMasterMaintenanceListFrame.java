package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

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
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;

import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


/**
 * 支払代行一覧画面
 */
public class JShiharaiMasterMaintenanceListFrame extends JFrame implements ActionListener,KeyListener
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
	protected ExtendedButton jButton_Add = null;
	protected ExtendedButton jButton_Change = null;
	protected ExtendedButton jButton_Delete = null;
	protected JPanel jPanel_MainArea = null;
	// add s.inoue 2010/02/25
	protected ExtendedButton jButton_Import = null;
	protected ExtendedButton jButton_Export = null;

	/**
	 * This is the default constructor
	 */
	public JShiharaiMasterMaintenanceListFrame() {
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
			// add s.inoue 2010/02/25
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.weightx = 1.0D;

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints5.gridx = 2;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 0);

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints2.gridx = 5;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints1.gridx = 4;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 3;
			gridBagConstraints.insets = new Insets(0, 5, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());

			// add s.inoue 2010/02/25
			jPanel_ButtonArea.add(getJButton_Import(), gridBagConstraints4);
			jPanel_ButtonArea.add(getJButton_Export(), gridBagConstraints5);

			jPanel_ButtonArea.add(getJButton_Add(), gridBagConstraints);
			jPanel_ButtonArea.add(getJButton_Change(), gridBagConstraints1);
			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints2);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints3);
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
			// eidt s.inoue 2011/05/26
//			jButton_End = new ExtendedButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setActionCommand("終了");
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("戻る(F1)");
//			jButton_End.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			jButton_End= new ExtendedButton(
					"Close","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
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
			jLabel_MainExpl.setText("支払代行機関情報のメンテナンスを行います。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("支払代行メンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_SHIHARAI_MASTERMAINTENANCE_FRAME_TITLE);

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
	 * This method initializes jButton_Import
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Import() {
		if (jButton_Import == null) {
			jButton_Import = new ExtendedButton();
			jButton_Import.setText("取込(F4)");
			jButton_Import.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Import.addActionListener(this);
		}
		return jButton_Import;
	}

	/**
	 * This method initializes jButton_Export
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Export() {
		if (jButton_Export == null) {
			jButton_Export = new ExtendedButton();
			jButton_Export.setText("書出(F5)");
			jButton_Export.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Export.addActionListener(this);
		}
		return jButton_Export;
	}

	/**
	 * This method initializes jButton_Add
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Add() {
		if (jButton_Add == null) {
			// eidt s.inoue 2011/05/26
//			jButton_Add = new ExtendedButton();
//			jButton_Add.setText("追加(F9)");
//			jButton_Add.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_Add.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Add);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_Add= new ExtendedButton(
					"Add","追加(A)","追加(ALT+A)",KeyEvent.VK_A,icon);
		}
		return jButton_Add;
	}

	/**
	 * This method initializes jButton_Change
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Change() {
		if (jButton_Change == null) {
			jButton_Change = new ExtendedButton();
			jButton_Change.setText("変更(F11)");
			jButton_Change.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Change.addActionListener(this);
		}
		return jButton_Change;
	}

	/**
	 * This method initializes jButton_Delete
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Delete() {
		if (jButton_Delete == null) {
			jButton_Delete = new ExtendedButton();
			jButton_Delete.setText("削除(F12)");
			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Delete.addActionListener(this);
		}
		return jButton_Delete;
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
			jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}
		return jPanel_MainArea;
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
