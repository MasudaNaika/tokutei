package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class JImportErrorResultFrame extends JFrame implements ActionListener
{
	protected static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected JButton jButton_End = null;
	protected JPanel jPanel_NaviArea = null;
	protected JLabel jLabel_Title = null;
	protected JLabel jLabel_MainExpl = null;
	protected JPanel jPanel_TitleArea = null;
	protected JPanel jPanel_ExplArea2 = null;
	protected JLabel jLabal_SubExpl = null;
	protected JPanel jPanel_ExplArea1 = null;
	protected JPanel jPanel_MainArea = null;
	protected JPanel jPanel_Table = null;
	protected JButton jButton_Select = null;

	public JImportErrorResultFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		// edit s.inoue 2009/10/13
//		this.setSize(800, 600);
		this.setSize(ViewSettings.getFrameCommonSize());
		this.setContentPane(getJPanel_Content());
		this.setTitle("特定健診システム");
		this.setLocationRelativeTo( null );
		this.setVisible(false);
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
			// eidt s.inoue 2011/06/07
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
			FlowLayout flowLayout = new FlowLayout();
			// eidt s.inoue 2011/06/07
			flowLayout.setAlignment(FlowLayout.LEFT);
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setLayout(flowLayout);
			jPanel_ButtonArea.add(getJButton_End(), null);
		}
		return jPanel_ButtonArea;
	}

	/**
	 * This method initializes jButton_End
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_End() {
		if (jButton_End == null) {
			// eidt s.inoue 2011/06/07
//			jButton_End = new JButton();
//			jButton_End.setHorizontalAlignment(SwingConstants.CENTER);
//			jButton_End.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButton_End.setText("終了");
//			jButton_End.addActionListener(this);
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Exit);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
			jButton_End= new ExtendedButton(
					"End","終了(E)","終了(ALT+E)",KeyEvent.VK_E,icon);
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
			jLabel_MainExpl.setText("検査結果に不正なデータが存在します。");
			jLabel_MainExpl.setFont(new Font("Dialog", Font.PLAIN, 14));
			jLabel_MainExpl.setName("jLabel1");
			jLabel_Title = new JLabel();
			jLabel_Title.setText("検査結果データ取り込み");
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
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			jPanel_ExplArea2 = new JPanel();
			jPanel_ExplArea2.setName("jPanel4");
			jPanel_ExplArea2.setLayout(gridLayout1);
			jPanel_ExplArea2.add(jLabel_MainExpl, null);
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
			jPanel_MainArea.add(getJPanel_Table(), getJPanel_Table().getName());
		}
		return jPanel_MainArea;
	}

	/**
	 * This method initializes jPanel_Table
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Table() {
		if (jPanel_Table == null) {
			jPanel_Table = new JPanel();
			jPanel_Table.setLayout(new BorderLayout());
			jPanel_Table.setName("jPanel_DrawArea");
		}
		return jPanel_Table;
	}

	public void actionPerformed( ActionEvent e )
	{

	}

	/**
	 * This method initializes jButton_Select
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Select() {
		if (jButton_Select == null) {
			jButton_Select = new JButton();
			jButton_Select.setText("強制取込");
			jButton_Select.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButton_Select.addActionListener(this);
		}
		return jButton_Select;
	}
}
