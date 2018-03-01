package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;

import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.BorderFactory;

/**
 * 健診項目マスタメンテナンス
 */
public class JKenshinPatternMaintenanceAddFrame extends JFrame implements ActionListener,KeyListener
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
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected JTextField jTextField_PatternNumber = null;
	protected JTextField jTextField_PetternName = null;
	protected JTextField jTextField_Note = null;
	protected ExtendedButton jButton_Register = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public JKenshinPatternMaintenanceAddFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		/* Modified 2008/03/22 若月  */
		/* --------------------------------------------------- */
//		this.setSize(600, 300);
//		this.setContentPane(getJPanel_Content());
//		this.setTitle("特定健診システム");
//		setLocationRelativeTo( null );
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
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.gridx = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridx = 1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints6);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints7);
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
			jButton_End.setMnemonic(KeyEvent.VK_R);
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
			jLabel_MainExpl.setText("登録する健診パターンの情報を入力してください。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			// edit ver2 s.inoue 2009/06/04
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("パターン情報登録");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_ADD_FRAME_TITLE);
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
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints3.gridy = 0;
			jLabel2 = new JLabel();
			jLabel2.setText("備考");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel1 = new JLabel();
			jLabel1.setText("パターン名");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel = new JLabel();
			jLabel.setText("パターン番号");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.weightx = 1.0D;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.weightx = 1.0D;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 1;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.weightx = 1.0D;
			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new GridBagLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");
			jPanel_DrawArea.add(jLabel, gridBagConstraints3);
			jPanel_DrawArea.add(getJTextField_PatternNumber(), gridBagConstraints);
			jPanel_DrawArea.add(jLabel1, gridBagConstraints4);
			jPanel_DrawArea.add(getJTextField_PetternName(), gridBagConstraints1);
			jPanel_DrawArea.add(jLabel2, gridBagConstraints5);
			jPanel_DrawArea.add(getJTextField_Note(), gridBagConstraints2);
			jPanel_DrawArea.add(getJPanel(), gridBagConstraints21);
		}
		return jPanel_DrawArea;
	}

	public void actionPerformed( ActionEvent e )
	{

	}

	/**
	 * This method initializes jTextField_PatternNumber
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_PatternNumber() {
		if (jTextField_PatternNumber == null) {
			jTextField_PatternNumber = new JTextField();
			jTextField_PatternNumber.setPreferredSize(new Dimension(50, 20));
			jTextField_PatternNumber.addActionListener(this);
		}
		return jTextField_PatternNumber;
	}

	/**
	 * This method initializes jTextField_PetternName
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_PetternName() {
		if (jTextField_PetternName == null) {
			jTextField_PetternName = new JTextField();
			jTextField_PetternName.addActionListener(this);
		}
		return jTextField_PetternName;
	}

	/**
	 * This method initializes jTextField_Note
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_Note() {
		if (jTextField_Note == null) {
			jTextField_Note = new JTextField();
			jTextField_Note.addActionListener(this);
		}
		return jTextField_Note;
	}

	/**
	 * This method initializes jButton_Register
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			jButton_Register = new ExtendedButton();
			jButton_Register.setText("登録(F12)");
			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
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
