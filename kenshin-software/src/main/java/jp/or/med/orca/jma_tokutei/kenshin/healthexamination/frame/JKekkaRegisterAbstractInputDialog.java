//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import javax.swing.JPanel;
//
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.Frame;
//import java.awt.BorderLayout;
//import javax.swing.JDialog;
//import java.awt.Font;
//import java.awt.GridBagLayout;
//
//import javax.swing.JComponent;
//import javax.swing.JEditorPane;
//import javax.swing.JLabel;
//import javax.swing.JScrollPane;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//
//import javax.swing.SwingConstants;
//import java.awt.CardLayout;
//import java.awt.GridLayout;
//import javax.swing.BorderFactory;
//import javax.swing.border.EtchedBorder;
//
//public abstract class JKekkaRegisterAbstractInputDialog extends JDialog
//	implements ActionListener, KeyListener,MouseListener, IKekkaRegisterInputDialog {
//
//	private static final long serialVersionUID = 1L;
//	private JPanel jContentPane = null;
//	private JPanel jPanelNaviArea = null;
//	private JPanel jPanelMainArea = null;
//	private JPanel jPanelButtonArea = null;
//	protected JScrollPane jScrollPane = null;
//	protected ExtendedButton jButtonOK = null;
//	protected ExtendedButton jButtonSelect = null;
//	protected ExtendedButton jButtonCancel = null;
//	protected ExtendedButton jButtonClear = null;
//	protected RETURN_VALUE ReturnValue;  //  @jve:decl-index=0:
//	private Font guidanceFont;
//	private Font defaultFont;
//	private JPanel jPanelLabel = null;
//	private ExtendedLabel jLabelTitle = null;
//	private ExtendedLabel jLabelGuidance = null;
//
//	// edit s.inoue 2009/12/15
//	protected ExtendedEditorPane jEditorPane_Comment = null;
//
//	/**
//	 * @param owner
//	 */
//	public JKekkaRegisterAbstractInputDialog(Frame owner) {
//		super(owner);
//		defaultFont = ViewSettings.getCommonUserInputFont();
//		guidanceFont = new Font("Dialog", Font.PLAIN, 14);
//	}
//
//	protected void initialize(String title, String guidance, Component component) {
//		this.setSize(480, 420);
//		this.setContentPane(getJContentPane(title,guidance));
//		this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//		this.setLocationRelativeTo( null );
//
//		jButtonOK.addKeyListener(new JEnterEvent(this));
//		jButtonCancel.addKeyListener(new JEnterEvent(this));
//		// edit s.inoue 2009/12/21
//		// jLabelTitle = new TitleLabel("tokutei.shoken.dialog.title");
//		// jLabelTitle.setText(title);
//		// jLabelTitle = new TitleLabel(title);
//		// jLabelGuidance.setText(guidance);
//		jScrollPane.setViewportView(component);
//		// del s.inoue 2009/12/21
//		// jButtonOK.setSelected(true);
//		setModal(true);
//	}
//
//	/**
//	 * This method initializes jContentPane
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJContentPane(String title,String guidance) {
//		if (jContentPane == null) {
//			jContentPane = new JPanel();
//			jContentPane.setLayout(new BorderLayout());
//			jContentPane.add(getJPanelNaviArea(title,guidance), BorderLayout.NORTH);
//			jContentPane.add(getJPanelMainArea(), BorderLayout.CENTER);
//			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
//		}
//		return jContentPane;
//	}
//
//	/**
//	 * This method initializes jPanelNaviArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelNaviArea(String title,String guidance) {
//		if (jPanelNaviArea == null) {
//			CardLayout cardLayout = new CardLayout();
//			cardLayout.setHgap(5);
//			cardLayout.setVgap(5);
//			jPanelNaviArea = new JPanel();
//			jPanelNaviArea.setLayout(cardLayout);
//			jPanelNaviArea.add(getJPanelLabel(title,guidance), getJPanelLabel(title,guidance).getName());
//		}
//		return jPanelNaviArea;
//	}
//
//	/**
//	 * This method initializes jPanelMainArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelMainArea() {
//		if (jPanelMainArea == null) {
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.fill = GridBagConstraints.BOTH;
//			gridBagConstraints.weightx = 1.0;
//			gridBagConstraints.weighty = 1.0;
//			gridBagConstraints.insets = new Insets(0, 5, 0, 5);
//			// edit s.inoue 2009/12/15
//			gridBagConstraints.gridx = 0;
//			gridBagConstraints.gridy = 0;
//
//			// edit s.inoue 2009/12/15
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.fill = GridBagConstraints.BOTH;
//			gridBagConstraints2.weightx = 1.0;
//			gridBagConstraints2.weighty = 1.0;
//			gridBagConstraints2.insets = new Insets(0, 5, 0, 5);
//			gridBagConstraints2.gridx = 0;
//			gridBagConstraints2.gridy = 1;
//
//
//			jPanelMainArea = new JPanel();
//			jPanelMainArea.setLayout(new GridBagLayout());
//			jPanelMainArea.add(getJScrollPane(), gridBagConstraints);
//			// edit s.inoue 2009/12/15
//			jPanelMainArea.add(getJEditorPane_Comment(),gridBagConstraints2);
//		}
//		return jPanelMainArea;
//	}
//
//	/**
//	 * This method initializes jPanelButtonArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelButtonArea() {
//		if (jPanelButtonArea == null) {
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridx = 0;
//			gridBagConstraints2.gridy = 0;
//
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.weightx = 1.0D;
//			gridBagConstraints1.anchor = GridBagConstraints.EAST;
//			gridBagConstraints1.gridx = 2;
//			gridBagConstraints1.gridy = 0;
//
//			// edit s.inoue 2009/12/15
//			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridx = 3;
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.insets = new Insets(0,5,0,0);
//
//			// edit s.inoue 2010/04/27
//			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridx = 1;
//			gridBagConstraints4.gridy = 0;
//			gridBagConstraints4.insets = new Insets(0,5,0,0);
//
//			jPanelButtonArea = new JPanel();
//			jPanelButtonArea.setLayout(new GridBagLayout());
//			jPanelButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraints2);
//			// edit s.inoue 2010/04/27
//			jPanelButtonArea.add(getJButtonClear(), gridBagConstraints4);
//			jPanelButtonArea.add(getJButtonOK(), gridBagConstraints3);
//			// edit s.inoue 2009/12/15
//			jPanelButtonArea.add(getJButtonSelect(), gridBagConstraints1);
//		}
//		return jPanelButtonArea;
//	}
//
//	/**
//	 * This method initializes jScrollPane
//	 *
//	 * @return javax.swing.JScrollPane
//	 */
//	private JScrollPane getJScrollPane() {
//		if (jScrollPane == null) {
//			jScrollPane = new JScrollPane();
//			// edit s.inoue 2009/12/11
//			jScrollPane.addKeyListener(this);
//		}
//		return jScrollPane;
//	}
//
//	// edit s.inoue 2009/12/15
//	/**
//	 * This method initializes jEditorPane_Comment
//	 *
//	 * @return javax.swing.JEditorPane
//	 */
//	private JEditorPane getJEditorPane_Comment() {
//		if (jEditorPane_Comment == null) {
//			jEditorPane_Comment = new ExtendedEditorPane(ImeMode.IME_ZENKAKU);
//			jEditorPane_Comment.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
//			// edit s.inoue 2009/11/11
//			jEditorPane_Comment.addMouseListener(this); // マウスリスナを登録
//			jEditorPane_Comment.addKeyListener(this);
//		}
//		return jEditorPane_Comment;
//	}
//
//	/**
//	 * This method initializes jButtonOK
//	 *
//	 * @return javax.swing.JButton
//	 */
//	private ExtendedButton getJButtonOK() {
//		if (jButtonOK == null) {
//			jButtonOK = new ExtendedButton();
//			jButtonOK.setText("確定(F12)");
//			jButtonOK.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButtonOK.setActionCommand("終了");
//			jButtonOK.addActionListener(this);
//			// edit s.inoue 2009/12/11
//			jButtonOK.addKeyListener(this);
//		}
//		return jButtonOK;
//	}
//
//	// edit s.inoue 2009/12/15
//	/**
//	 * This method initializes jButtonOK
//	 *
//	 * @return javax.swing.JButton
//	 */
//	private ExtendedButton getJButtonSelect() {
//		if (jButtonSelect == null) {
//			jButtonSelect = new ExtendedButton();
//			jButtonSelect.setText("選択(F11)");
//			jButtonSelect.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButtonSelect.addActionListener(this);
//			// edit s.inoue 2009/12/11
//			jButtonSelect.addKeyListener(this);
//		}
//		return jButtonSelect;
//	}
//
//	/**
//	 * This method initializes jButtonCancel
//	 *
//	 * @return javax.swing.JButton
//	 */
//	private ExtendedButton getJButtonClear() {
//		if (jButtonClear == null) {
//			jButtonClear = new ExtendedButton();
//			jButtonClear.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButtonClear.setText("クリア(F2)");
//			jButtonClear.addActionListener(this);
//			// edit s.inoue 2009/12/11
//			jButtonClear.addKeyListener(this);
//		}
//		return jButtonClear;
//	}
//	/**
//	 * This method initializes jButtonCancel
//	 *
//	 * @return javax.swing.JButton
//	 */
//	private ExtendedButton getJButtonCancel() {
//		if (jButtonCancel == null) {
//			jButtonCancel = new ExtendedButton();
//			jButtonCancel.setFont(new Font("Dialog", Font.PLAIN, 12));
//			jButtonCancel.setText("キャンセル(F1)");
//			jButtonCancel.addActionListener(this);
//			// edit s.inoue 2009/12/11
//			jButtonCancel.addKeyListener(this);
//		}
//		return jButtonCancel;
//	}
//	public void keyPressed(KeyEvent arg0) {
//
//	}
//
//	public void keyReleased(KeyEvent arg0) {
//
//	}
//
//	public void keyTyped(KeyEvent arg0) {
//
//	}
//	// add s.inoue 2009/11/11
//	public void mousePressed(MouseEvent e) {
//	}
//	public void mouseReleased(MouseEvent e) {
//	}
//
//	/**
//	 * 戻り値を取得する
//	 * @return 戻り値
//	 */
//	public RETURN_VALUE getStatus() {
//		return ReturnValue;
//	}
//
//// move s.inoue 2009/12/15
////	/**
////	 * 戻り値を格納
////	 */
////	public void actionPerformed(ActionEvent e) {
////		// edit s.inoue 2009/12/15
////		if(e.getSource() == jButtonSelect){
////		}else if(e.getSource() == jButtonOK) {
////			// edit s.inoue 2009/12/06
////			// ReturnValue = RETURN_VALUE.OK;
////			ReturnValue = RETURN_VALUE.YES;
////		}
////		else if(e.getSource() == jButtonCancel) {
////			ReturnValue = RETURN_VALUE.CANCEL;
////		}
////
////		// モーダルダイアログの制御解除。
////		setVisible(false);
////	}
//
//	/**
//	 * 選択中セルのテキストをテキストエリア/リストボックスにセットする
//	 * @param text 選択中セルテキスト
//	 */
//	public abstract void setText(String text);
//
//	/**
//	 * ダイアログから入力/選択されたテキストを取得する
//	 * @return 入力/選択テキスト
//	 */
//	public abstract String getText();
//
//	/**
//	 * This method initializes jPanelLabel
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelLabel(String title,String guidance) {
//		if (jPanelLabel == null) {
//			jLabelGuidance = new ExtendedLabel();
//			jLabelGuidance.setFont(guidanceFont);
//			jLabelGuidance.setText(guidance);
//			// edit s.inoue 2009/12/21
//			// jLabelTitle.setText("tokutei.shoken.dialog.title");
//			jLabelTitle = new TitleLabel();
//			jLabelTitle.setText(title);
//
//			GridLayout gridLayout = new GridLayout();
//			gridLayout.setRows(2);
//			gridLayout.setVgap(5);
//			jPanelLabel = new JPanel();
//			jPanelLabel.setLayout(gridLayout);
//			jPanelLabel.setName("jPanelLabel");
//			jPanelLabel.add(jLabelTitle, null);
//			jPanelLabel.add(jLabelGuidance, null);
//		}
//		return jPanelLabel;
//	}
//
//}
////@jve:decl-index=0:
////  @jve:decl-index=0:visual-constraint="10,10"