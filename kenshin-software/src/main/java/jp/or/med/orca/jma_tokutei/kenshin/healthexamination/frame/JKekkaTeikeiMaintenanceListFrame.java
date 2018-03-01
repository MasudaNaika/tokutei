package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;

import javax.swing.BorderFactory;

public class JKekkaTeikeiMaintenanceListFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_MainArea = null;
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
	protected ExtendedButton jButton_Edit = null;
	protected ExtendedButton jButton_Add = null;
	protected ExtendedButton jButton_Delete = null;

	protected JPanel jPanel = null;
	protected JPanel jPanel1 = null;
	protected JPanel jPanel2 = null;
	protected JPanel jPanel3 = null;
	protected ExtendedButton jButton_ToLeft = null;
	protected ExtendedButton jButton_ToRight = null;
	protected JPanel jPanel11 = null;
	protected JPanel jPanel12 = null;
	protected JPanel jPanel_Left = null;
	protected JPanel jPanel_Right = null;

	protected ExtendedButton jButton_Import = null;
	protected ExtendedButton jButton_Export = null;

	/**
	 * This is the default constructor
	 */
	public JKekkaTeikeiMaintenanceListFrame() {
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
			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);
			jPanel_Content.add(getJPanel_MainArea(), BorderLayout.CENTER);
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
		}
		return jPanel_Content;
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
			jPanel_MainArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		}
		return jPanel_MainArea;
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
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints5.gridx = 2;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 0;
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
			// del s.inoue 2010/03/12
			// gridBagConstraints.weightx = 1.0D;
			// gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.gridx = 3;
			gridBagConstraints.insets = new Insets(0, 5, 0, 0);

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			// add s.inoue 2010/02/25
			jPanel_ButtonArea.add(getJButton_Import(), gridBagConstraints4);
			jPanel_ButtonArea.add(getJButton_Export(), gridBagConstraints5);

			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints3);
			jPanel_ButtonArea.add(getJButton_Add(), gridBagConstraints);
			jPanel_ButtonArea.add(getJButton_Edit(), gridBagConstraints1);
			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints2);
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
			jLabel_MainExpl = new JLabel();
			jLabel_MainExpl.setText("健診・問診結果データ入力画面の「総合コメント」、「結果（文字列）」欄でCTLキー+ENTERキーで表示される画面です。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_TEIKEI_MASTERMAINTENANCE_FRAME_TITLE);
			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
			jLabel_Title.setBackground(new Color(153, 204, 255));
			jLabel_Title.setForeground(new Color(51, 51, 51));
			jLabel_Title.setOpaque(true);
			jLabel_Title.setEnabled(true);
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
			jLabal_SubExpl.setText("追加ボタンで、<<[総合コメント]、[結果（文字列）]の内容>>欄へ追加します。編集後、登録ボタンを押下して下さい。");
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
	 * This method initializes jButton_Add
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Add() {
		if (jButton_Add == null) {
			jButton_Add = new ExtendedButton();
			jButton_Add.setText("追加(F9)");
			jButton_Add.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Add.addActionListener(this);
		}
		return jButton_Add;
	}

	/**
	 * This method initializes jButton_Edit
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Edit() {
		if (jButton_Edit == null) {
			jButton_Edit = new ExtendedButton();
			jButton_Edit.setText("編集(F11)");
			jButton_Edit.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Edit.addActionListener(this);
		}
		return jButton_Edit;
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