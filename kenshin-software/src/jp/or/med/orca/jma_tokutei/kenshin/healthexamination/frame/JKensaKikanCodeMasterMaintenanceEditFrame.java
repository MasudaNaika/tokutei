package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.JTextField;
import java.awt.Insets;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import javax.swing.BorderFactory;

/**
 * 検査センター検査項目コードマスタメンテナンス 
 */
public class JKensaKikanCodeMasterMaintenanceEditFrame extends JFrame implements ActionListener
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
	protected ExtendedButton jButton_Register = null;
	protected ExtendedButton jButton_Delete = null;
	protected JPanel jPanel_Input = null;
	protected ExtendedLabel jLabel = null;
	protected ExtendedLabel jLabel1 = null;
	protected JTextField jTextField_CenterName = null;
	protected JPanel jPanel_TableArea = null;
	protected JTextField jTextField_CenterCode = null;
	protected ExtendedButton jButton_Cancel = null;
	/**
	 * This is the default constructor
	 */
	public JKensaKikanCodeMasterMaintenanceEditFrame() {
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridx = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints5.gridx = 3;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.gridx = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridx = 1;
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel_ButtonArea.setLayout(new GridBagLayout());
			jPanel_ButtonArea.add(getJButton_Register(), gridBagConstraints2);
			jPanel_ButtonArea.add(getJButton_Cancel(), gridBagConstraints4);
			jPanel_ButtonArea.add(getJButton_Delete(), gridBagConstraints5);
			jPanel_ButtonArea.add(getJButton_End(), gridBagConstraints6);
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
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_End.setText("戻る");
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
			jLabel_MainExpl.setText("検査センター独自のコードを項目コード（JLAC10）に紐付けして下さい。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			
			/* Modified 2008/03/22 若月  */
			/* --------------------------------------------------- */
//			jLabel_Title = new ExtendedLabel();
//			jLabel_Title.setText("検査センター検査項目コードマスタメンテナンス");
//			jLabel_Title.setFont(new Font("Dialog", Font.PLAIN, 18));
//			jLabel_Title.setBackground(new Color(153, 204, 255));
//			jLabel_Title.setForeground(new Color(51, 51, 51));
//			jLabel_Title.setOpaque(true);
//			jLabel_Title.setName("jLabel");
			/* --------------------------------------------------- */
			jLabel_Title = new TitleLabel("tokutei.kensacenter-kensaitem-code-mastermaintenance.frame.title");
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
			jPanel_DrawArea.add(getJPanel_Input(), BorderLayout.NORTH);
			jPanel_DrawArea.add(getJPanel_TableArea(), BorderLayout.CENTER);
		}
		return jPanel_DrawArea;
	}

	public void actionPerformed( ActionEvent e )
	{
		
	}

	/**
	 * This method initializes jButton_Register	
	 * 	
	 * @return javax.swing.ExtendedButton	
	 */
	private ExtendedButton getJButton_Register() {
		if (jButton_Register == null) {
			jButton_Register = new ExtendedButton();
			jButton_Register.setText("登録");
			jButton_Register.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Register.addActionListener(this);
		}
		return jButton_Register;
	}

	/**
	 * This method initializes jButton_Delete	
	 * 	
	 * @return javax.swing.ExtendedButton	
	 */
	private ExtendedButton getJButton_Delete() {
		if (jButton_Delete == null) {
			jButton_Delete = new ExtendedButton();
			jButton_Delete.setText("削除");
			jButton_Delete.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Delete.addActionListener(this);
		}
		return jButton_Delete;
	}

	/**
	 * This method initializes jPanel_Input	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Input() {
		if (jPanel_Input == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints1.gridy = 1;
			jLabel1 = new ExtendedLabel();
			jLabel1.setText("検査センター名称");
			jLabel1.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints.gridy = 0;
			jLabel = new ExtendedLabel();
			jLabel.setText("検査センターコード");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanel_Input = new JPanel();
			jPanel_Input.setLayout(new GridBagLayout());
			jPanel_Input.add(jLabel, gridBagConstraints);
			jPanel_Input.add(jLabel1, gridBagConstraints1);
			jPanel_Input.add(getJTextField_CenterName(), gridBagConstraints3);
			jPanel_Input.add(getJTextField_CenterCode(), gridBagConstraints11);
		}
		return jPanel_Input;
	}

	/**
	 * This method initializes jTextField_CenterName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_CenterName() {
		if (jTextField_CenterName == null) {
			jTextField_CenterName = new JTextField();
		}
		return jTextField_CenterName;
	}

	/**
	 * This method initializes jPanel_TableArea	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_TableArea() {
		if (jPanel_TableArea == null) {
			jPanel_TableArea = new JPanel();
			jPanel_TableArea.setLayout(new BorderLayout());
		}
		return jPanel_TableArea;
	}

	/**
	 * This method initializes jTextField_CenterCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_CenterCode() {
		if (jTextField_CenterCode == null) {
			jTextField_CenterCode = new JTextField();
		}
		return jTextField_CenterCode;
	}

	/**
	 * This method initializes jButton_Cancel	
	 * 	
	 * @return javax.swing.ExtendedButton	
	 */
	private ExtendedButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new ExtendedButton();
			jButton_Cancel.setText("キャンセル");
			jButton_Cancel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Cancel.setVisible(false);
			jButton_Cancel.addActionListener(this);
		}
		return jButton_Cancel;
	}
}
