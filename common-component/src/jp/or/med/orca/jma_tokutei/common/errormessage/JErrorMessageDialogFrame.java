package jp.or.med.orca.jma_tokutei.common.errormessage;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.event.*;

import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import java.awt.ScrollPane;
import java.awt.Dimension;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import jp.or.med.orca.jma_tokutei.common.app.*;

/**
 * エラーメッセージダイアログクラス
 */
public class JErrorMessageDialogFrame extends JDialog implements ActionListener,KeyListener,MouseListener {

	private static final long serialVersionUID = 1L;
	protected JPanel jPanel_Content = null;
	protected JPanel jPanel_ButtonArea = null;
	protected ExtendedButton jButton_OK = null;
	protected JPanel jPanel_NaviArea = null;
	protected TitleLabel jLabel_Title = null;
	protected JPanel jPanel_TitleArea = null;
	protected ExtendedTextArea jTextArea_ErrorMessage = null;
	protected JPanel jPanel = null;
	protected ExtendedButton jButton_Yes = null;
	protected ExtendedButton jButton_No = null;
	protected ExtendedButton jButton_Cancel = null;
	protected ExtendedButton jButton_ALL = null;

	protected JViewport view;
	private JScrollPane scrollpane = null;
	private JPanel jPanel_TextArea = null;

	/**
	 * This is the default constructor
	 */
	public JErrorMessageDialogFrame(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		// edit ver2 s.inoue 2009/04/15
		//this.setSize(440, 200);
		this.setSize(500, 250);
		// del s.inoue 2012/12/21
//		// add s.inoue 2012/07/12
//		setAlwaysOnTop(true);

		this.setContentPane(getJPanel_Content());
		this.setModal(true);
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
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
			borderLayout.setHgap(2);

			jPanel_Content = new JPanel();
			jPanel_Content.setLayout(borderLayout);
			jPanel_Content.add(getJPanel_ButtonArea(), BorderLayout.SOUTH);
			jPanel_Content.add(getJPanel_NaviArea(), BorderLayout.NORTH);

			jPanel_Content.add(getJPanel_TextArea(), BorderLayout.CENTER);
			// add ver2 s.inoue 2009/04/14
			view = scrollpane.getViewport();
			view.setView(this.jTextArea_ErrorMessage);

			getContentPane().add(jPanel_Content,BorderLayout.CENTER);

//			JScrollPane js = new JScrollPane(this.jTextArea_ErrorMessage);
//			js.setPreferredSize(new Dimension(440,200));
//			js.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//			js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//			jPanel_Content.add(js,BorderLayout.CENTER);

		}
		return jPanel_Content;
	}

	/**
	 * This method initializes scrollpane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollpane() {
		if (scrollpane == null) {
			scrollpane = new JScrollPane();
			scrollpane.setPreferredSize(new Dimension(200, 120));
			scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollpane.setName("scrollpane");
			scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane.addKeyListener(this);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipady = 11;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.insets = new Insets(10, 0, 0, 0);

			scrollpane.add(getJTextArea_ErrorMessage(), gridBagConstraints2);
		}
		return scrollpane;
	}

	/**
	 * This method initializes jPanel_TextArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_TextArea() {
		if (jPanel_TextArea == null) {
			CardLayout cLay = new CardLayout();
			cLay.setHgap(10);
			cLay.setVgap(10);
			jPanel_TextArea = new JPanel();
			jPanel_TextArea.setLayout(cLay);
			jPanel_TextArea.add(getScrollpane(), getScrollpane().getName());
		}
		return jPanel_TextArea;
	}

	/**
	 * This method initializes jPanel_ButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_ButtonArea() {
		if (jPanel_ButtonArea == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.CENTER);
			jPanel_ButtonArea = new JPanel();
			jPanel_ButtonArea.setLayout(flowLayout);
			jPanel_ButtonArea.add(getJButton_OK(), null);
			jPanel_ButtonArea.add(getJButton_Yes(), null);
			jPanel_ButtonArea.add(getJButton_No(), null);
			jPanel_ButtonArea.add(getJButton_Cancel(), null);
			jPanel_ButtonArea.add(getJButton_ALL(), null);
		}
		return jPanel_ButtonArea;
	}

	/**
	 * This method initializes jButton_OK
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_OK() {
		if (jButton_OK == null) {
			jButton_OK = new ExtendedButton();
			jButton_OK.setHorizontalAlignment(SwingConstants.CENTER);
			jButton_OK.setActionCommand("終了");
			jButton_OK.setText("OK(Y)");
			jButton_OK.addActionListener(this);
			jButton_OK.addKeyListener(this);
			jButton_OK.setMnemonic(KeyEvent.VK_Y);
		}
		return jButton_OK;
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

			this.jLabel_Title = new TitleLabel();
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.ipady = 10;
			gridBagConstraints1.gridx = 0;
			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(getJPanel(), gridBagConstraints1);
		}
		return jPanel_TitleArea;
	}

	/**
	 * This method initializes jTextArea_ErrorMessage
	 *
	 * @return javax.swing.JTextArea
	 */
	private ExtendedTextArea getJTextArea_ErrorMessage() {
		if (jTextArea_ErrorMessage == null) {
			jTextArea_ErrorMessage = new ExtendedTextArea();
			jTextArea_ErrorMessage.setRows(3);
			jTextArea_ErrorMessage.setLineWrap(true);
			// dell s.inoue 2009/12/07
			jTextArea_ErrorMessage.setName("jTextArea_ErrorMessage");
			jTextArea_ErrorMessage.setText("");
			jTextArea_ErrorMessage.setDisabledTextColor(Color.black);

			// edit s.inoue 2010/10/26
			jTextArea_ErrorMessage.setEditable(false);
			// jTextArea_ErrorMessage.setEnabled(false);
			// jTextArea_ErrorMessage.setFocusable(false);

			// add s.inoue 2009/12/07
			jTextArea_ErrorMessage.addMouseListener(this); // マウスリスナを登録
			// edit s.inoue 2009/12/15
			jTextArea_ErrorMessage.addKeyListener(this);

		}
		return jTextArea_ErrorMessage;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.weighty = 1.0D;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(jLabel_Title, gridBagConstraints);
		}
		return jPanel;
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	/**
	 * This method initializes jButton_Yes
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Yes() {
		if (jButton_Yes == null) {
			jButton_Yes = new ExtendedButton();
			jButton_Yes.setText("はい(Y)");
			jButton_Yes.addActionListener(this);
			jButton_Yes.addKeyListener(this);
			jButton_Yes.setMnemonic(KeyEvent.VK_Y);
		}
		return jButton_Yes;
	}

	/**
	 * This method initializes jButton_No
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_No() {
		if (jButton_No == null) {
			jButton_No = new ExtendedButton();
			jButton_No.setText("いいえ[N]");
			jButton_No.addActionListener(this);
			jButton_No.addKeyListener(this);
			jButton_No.setMnemonic(KeyEvent.VK_N);
		}
		return jButton_No;
	}

	/**
	 * This method initializes jButton_Cancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new ExtendedButton();
			jButton_Cancel.setText("キャンセル[C]");
			jButton_Cancel.addActionListener(this);
			jButton_Cancel.addKeyListener(this);
			jButton_Cancel.setMnemonic(KeyEvent.VK_C);
		}
		return jButton_Cancel;
	}

	/**
	 * This method initializes jButton_Cancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButton_ALL() {
		if (jButton_ALL == null) {
			jButton_ALL = new ExtendedButton();
			jButton_ALL.setText("全て上書き[A]");
			jButton_ALL.addActionListener(this);
			jButton_ALL.addKeyListener(this);
			jButton_ALL.setMnemonic(KeyEvent.VK_A);
		}
		return jButton_ALL;
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

