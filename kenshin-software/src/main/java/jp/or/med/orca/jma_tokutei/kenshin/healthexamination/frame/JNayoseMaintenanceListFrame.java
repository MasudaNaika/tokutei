package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;

import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class JNayoseMaintenanceListFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JLabel jLabel_Title = null;
	protected JLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected ExtendedButton jButton_End = null;
	protected ExtendedButton jButton_Change = null;
	// add ver2 s.inoue 2009/07/24
	protected ExtendedButton jButton_Nayose = null;
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

	/**
	 * This is the default constructor
	 */
	public JNayoseMaintenanceListFrame() {
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

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.gridx = 0;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints11.weightx = 1.0D;
//			gridBagConstraints11.insets = new Insets(0, 0, 0, 80);
//			gridBagConstraints11.anchor = GridBagConstraints.EAST;

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.gridx = 3;
			gridBagConstraints12.insets = new Insets(0, 5, 0, 0);
//			gridBagConstraints12.weightx = 1.0D;
//			gridBagConstraints12.anchor = GridBagConstraints.EAST;

			// add ver2 s.inoue 2009/07/24
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.weightx = 1.0D;
//			gridBagConstraints13.insets = new Insets(0, 0, 0, 190);
			gridBagConstraints13.anchor = GridBagConstraints.EAST;

			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());

			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints8);
			jPanel_ButtonArea.add(getJButton_Edit(), gridBagConstraints11);
			jPanel_ButtonArea.add(getJButton_Del(), gridBagConstraints12);
			jPanel_ButtonArea.add(getJButton_Nayose(), gridBagConstraints13);

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
			jLabel_MainExpl = new JLabel();
			jLabel_MainExpl.setText("一覧からレコードを選択し、画面下部のボタンを押して受診者の紐付けを実行します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("経年管理メンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_NAYOSE_MASTERMAINTENANCE_FRAME_TITLE);
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
	 * This method initializes jButton_Nayose
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Nayose() {
		if (jButton_Nayose == null) {
			jButton_Nayose = new ExtendedButton();
			jButton_Nayose.setText("名寄せ(F9)");
			jButton_Nayose.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Nayose.addActionListener(this);
		}
		return jButton_Nayose;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Edit() {
		if (jButton_Change == null) {
			jButton_Change = new ExtendedButton();
			jButton_Change.setText("編集(F11)");
			jButton_Change.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Change.addActionListener(this);
		}
		return jButton_Change;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Del() {
		if (jButton_Delete == null) {
			jButton_Delete = new ExtendedButton();
			jButton_Delete.setText("削除(F12)");
			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Delete.addActionListener(this);
		}
		return jButton_Delete;
	}
	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}
		return jPanel;
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


