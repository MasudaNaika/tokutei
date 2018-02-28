package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;

/**
 * 名寄せマスタメンテナンス
 */
public class JNayoseMaintenanceEditFrame extends JFrame implements ActionListener,KeyListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JPanel jPanel_NaviArea = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_DrawArea = null;
	protected ExtendedLabel jLabel_Title = null;
	protected ExtendedLabel jLabel_MainExpl = null;
	protected ExtendedLabel jLabal_SubExpl = null;

	protected ExtendedTextField jTextField_Name = null;
	protected ExtendedTextField jTextField_KanaName = null;
	protected ExtendedTextField jTextField_Birthday = null;
	protected ExtendedTextField jTextField_Sex = null;
	protected ExtendedTextField jTextField_Home = null;
	protected ExtendedTextField jTextField_HiKigou = null;
	protected ExtendedTextField jTextField_HiBangou = null;
	protected ExtendedTextField jTextField_HimotukeID = null;

	protected ExtendedButton jButton_End = null;
	protected ExtendedButton jButton_Register = null;
	private ExtendedLabel jLabel = null;
	private ExtendedLabel jLabel1 = null;
	private ExtendedLabel jLabel2 = null;
	private ExtendedLabel jLabel3 = null;
	private ExtendedLabel jLabel4 = null;
	private ExtendedLabel jLabel5 = null;
	private ExtendedLabel jLabel6 = null;
	private ExtendedLabel jLabel7 = null;

	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public JNayoseMaintenanceEditFrame() {
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

//	/**
//	 * This method initializes jPanel_ButtonArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel_ButtonArea2() {
//		if (jPanel_ButtonArea == null) {
//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridy = 0;
//			gridBagConstraints7.gridx = 0;
//			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridy = 0;
//			gridBagConstraints6.weightx = 1.0D;
//			gridBagConstraints6.anchor = GridBagConstraints.EAST;
//			gridBagConstraints6.gridx = 1;
//			jPanel_ButtonArea = new JPanel();
//			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel_ButtonArea.setLayout(new GridBagLayout());
//			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints6);
//			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints7);
//		}
//		return jPanel_ButtonArea;
//	}


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
			jLabel_MainExpl.setText("編集する受診者紐付けIDを入力してください。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			// edit ver2 s.inoue 2009/06/23
//			jLabel_Title = new JLabel();
//			jLabel_Title.setText("受診者紐付けID編集");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_NAYOSE_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
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

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 1;
			gridBagConstraints.weightx = 1.0;
//			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
// edit ver2 s.inoue 2009/06/24
//			gridBagConstraints.weightx = 1.0D;

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 8;
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;

//			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
//			gridBagConstraints7.gridy = 5;
//			gridBagConstraints7.gridx = 0;
//			gridBagConstraints7.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridy = 9;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.weighty = 1.0D;

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.weightx = 1.0D;

			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.NONE;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.weightx = 1.0D;
			//gridBagConstraints12.gridwidth = 1;

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.NONE;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.weightx = 1.0D;

			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.NONE;
			gridBagConstraints14.gridy = 3;
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.weightx = 1.0D;

			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.NONE;
			gridBagConstraints15.gridy = 8;
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.weightx = 1.0D;

			// add ver2 s.inoue 2009/07/28
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.fill = GridBagConstraints.NONE;
			gridBagConstraints19.gridy = 5;
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.weightx = 1.0D;

			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = GridBagConstraints.NONE;
			gridBagConstraints20.gridy = 6;
			gridBagConstraints20.gridx = 1;
			gridBagConstraints20.anchor = GridBagConstraints.WEST;
			gridBagConstraints20.weightx = 1.0D;

			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.NONE;
			gridBagConstraints21.gridy = 7;
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.anchor = GridBagConstraints.WEST;
			gridBagConstraints21.weightx = 1.0D;

			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.insets = new Insets(0, 0, 0, 10);

			// add ver2 s.inoue 2009/07/28
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridy = 5;
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridy = 6;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.anchor = GridBagConstraints.WEST;

			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridy = 7;
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.anchor = GridBagConstraints.WEST;

			jLabel = new ExtendedLabel();
			jLabel.setText("氏名(漢字)");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel1 = new ExtendedLabel();
			jLabel1.setText("氏名(カナ)");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel2 = new ExtendedLabel();
			jLabel2.setText("性別");
			jLabel2.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel3 = new ExtendedLabel();
			jLabel3.setText("生年月日");
			jLabel3.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel5 = new ExtendedLabel();
			jLabel5.setText("住所");
			jLabel5.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel6 = new ExtendedLabel();
			jLabel6.setText("被保険者証記号");
			jLabel6.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel7 = new ExtendedLabel();
			jLabel7.setText("被保険者証番号");
			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));

			jLabel4 = new ExtendedLabel();
			jLabel4.setText("受診者紐付けID");
			jLabel4.setFont(new Font("Dialog", Font.PLAIN, 12));

			jPanel_DrawArea = new JPanel();
			jPanel_DrawArea.setLayout(new GridBagLayout());
			jPanel_DrawArea.setName("jPanel_DrawArea");

			jPanel_DrawArea.add(jLabel, gridBagConstraints10);
			jPanel_DrawArea.add(jLabel1, gridBagConstraints3);
			jPanel_DrawArea.add(jLabel2, gridBagConstraints4);
			jPanel_DrawArea.add(jLabel3, gridBagConstraints5);
			jPanel_DrawArea.add(jLabel4, gridBagConstraints6);
			// add ver2 s.inoue 2009/07/28
			jPanel_DrawArea.add(jLabel5, gridBagConstraints16);
			jPanel_DrawArea.add(jLabel6, gridBagConstraints17);
			jPanel_DrawArea.add(jLabel7, gridBagConstraints18);

			jPanel_DrawArea.add(getJTextField_Name(), gridBagConstraints);
			jPanel_DrawArea.add(getJTextField_KanaName(), gridBagConstraints12);
			jPanel_DrawArea.add(getJTextField_Sex(), gridBagConstraints13);
			jPanel_DrawArea.add(getJTextField_Birthday(), gridBagConstraints14);
			jPanel_DrawArea.add(getJTextField_HimotukeID(), gridBagConstraints15);
			jPanel_DrawArea.add(getJTextField_Home(), gridBagConstraints19);
			jPanel_DrawArea.add(getJTextField_HiKigou(), gridBagConstraints20);
			jPanel_DrawArea.add(getJTextField_HiBangou(), gridBagConstraints21);
			jPanel_DrawArea.add(getJPanel(), gridBagConstraints8);
		}
		return jPanel_DrawArea;
	}

	public void actionPerformed( ActionEvent e )
	{

	}

	/**
	 * This method initializes jTextField_Name
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_Name() {
		if (jTextField_Name == null) {
			jTextField_Name = new ExtendedTextField("", 50, ImeMode.IME_ZENKAKU);
			jTextField_Name.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_Name;
	}

	/**
	 * This method initializes jTextField_NameKana
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_KanaName() {
		if (jTextField_KanaName == null) {
			jTextField_KanaName = new ExtendedTextField("", 50, ImeMode.IME_ZENKAKU);
			jTextField_KanaName.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_KanaName;
	}

	/**
	 * This method initializes jTextField_NameKana
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_Birthday() {
		if (jTextField_Birthday == null) {
			jTextField_Birthday = new ExtendedTextField("", 8, ImeMode.IME_ZENKAKU);
			jTextField_Birthday.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_Birthday;
	}

	/**
	 * This method initializes jTextField_NameKana
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_Home() {
		if (jTextField_Home == null) {
			jTextField_Home = new ExtendedTextField("", 100, ImeMode.IME_ZENKAKU);
			jTextField_Home.setPreferredSize(new Dimension(200, 20));
			jTextField_Home.addActionListener(this);
		}
		return jTextField_Home;
	}

	/**
	 * This method initializes jTextField_NameKana
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_HiKigou() {
		if (jTextField_HiKigou == null) {
			jTextField_HiKigou = new ExtendedTextField("", 20, ImeMode.IME_ZENKAKU);
			jTextField_HiKigou.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_HiKigou;
	}

	/**
	 * This method initializes jTextField_NameKana
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_HiBangou() {
		if (jTextField_HiBangou == null) {
			jTextField_HiBangou = new ExtendedTextField("", 20, ImeMode.IME_ZENKAKU);
			jTextField_HiBangou.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_HiBangou;
	}

	/**
	 * This method initializes jTextField_NameKana
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_Sex() {
		if (jTextField_Sex == null) {
			jTextField_Sex = new ExtendedTextField("", 1, ImeMode.IME_ZENKAKU);
			jTextField_Sex.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_Sex;
	}

	/**
	 * This method initializes jTextField_PetternName
	 *
	 * @return javax.swing.JTextField
	 */
	private ExtendedTextField getJTextField_HimotukeID() {
		if (jTextField_HimotukeID == null) {
			jTextField_HimotukeID = new ExtendedTextField("", 12, ImeMode.IME_OFF);
			jTextField_HimotukeID.setPreferredSize(new Dimension(200, 20));
		}
		return jTextField_HimotukeID;
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
