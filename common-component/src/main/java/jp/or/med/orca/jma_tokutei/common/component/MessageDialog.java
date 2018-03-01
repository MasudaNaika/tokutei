package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * メッセージダイアログ画面
 */
public class MessageDialog extends JDialog
	implements ActionListener, KeyListener, IDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel jPanelNaviArea = null;

	private JPanel jPanelButtonArea = null;

	protected ExtendedButton jButtonOK = null;

	protected ExtendedButton jButtonCancel = null;

	protected RETURN_VALUE ReturnValue;  //  @jve:decl-index=0:

	private Font defaultFont;

	private ExtendedLabel jLabelTitle = null;

	private boolean isShowCancel;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private ExtendedLabel jLabelMessage = null;

	/**
	 * @param owner
	 */
	public MessageDialog(Frame owner) {
		super(owner);
		defaultFont = ViewSettings.getCommonUserInputFont();
		initialize();
		this.setLocationRelativeTo(null);
	}

	private void initialize() {
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(376, 193);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

		/* Deleted 2008/03/22 若月  */
		/* --------------------------------------------------- */
//		jButtonOK.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
//		jButtonCancel.addKeyListener(new jp.or.med.orca.jma_tokutei.common.event.JEnterEvent(this));
		/* --------------------------------------------------- */

		/* Modified 2008/03/22 若月  */
		/* --------------------------------------------------- */
//		jButtonOK.setSelected(true);
		/* --------------------------------------------------- */
		this.jButtonOK.grabFocus();
		/* --------------------------------------------------- */

		setModal(true);
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelNaviArea(), BorderLayout.NORTH);
			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelNaviArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNaviArea() {
		if (jPanelNaviArea == null) {
			CardLayout cardLayout = new CardLayout();
			cardLayout.setHgap(5);
			cardLayout.setVgap(5);
			jPanelNaviArea = new JPanel();
			jPanelNaviArea.setLayout(cardLayout);
			jLabelTitle = new TitleLabel("");
			jLabelTitle.setText("");
			jLabelTitle.setName("jLabelTitle");
			jPanelNaviArea.add(jLabelTitle, jLabelTitle.getName());
		}
		return jPanelNaviArea;
	}

	/**
	 * This method initializes jPanelButtonArea
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtonArea() {
		if (jPanelButtonArea == null) {
			GridBagConstraints gridBagConstraintsOkBtn = new GridBagConstraints();
//			gridBagConstraintsOkBtn.insets = new Insets(5, 0, 5, 0);
			gridBagConstraintsOkBtn.gridx = (isShowCancel ? 1 : 2);
			gridBagConstraintsOkBtn.gridy = 0;

			GridBagConstraints gridBagConstraintsCancelBtn = new GridBagConstraints();
			gridBagConstraintsCancelBtn.gridx = 3;
//			gridBagConstraintsCancelBtn.insets = new Insets(5, 20, 5, 0);
			gridBagConstraintsCancelBtn.gridy = 0;

			jButtonCancel = getJButtonCancel();
			jButtonCancel.setVisible(isShowCancel);

			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraintsOkBtn);
			jPanelButtonArea.add(jButtonCancel, gridBagConstraintsCancelBtn);
		}
		return jPanelButtonArea;
	}

	/**
	 * This method initializes jButtonOK
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new ExtendedButton();
			jButtonOK.setText("OK(Y)");
//			jButtonOK.setFont(defaultFont);
			jButtonOK.setActionCommand("終了");
			jButtonOK.addActionListener(this);
			jButtonOK.setMnemonic(KeyEvent.VK_Y);
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 *
	 * @return javax.swing.ExtendedButton
	 */
	private ExtendedButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new ExtendedButton();
//			jButtonCancel.setFont(defaultFont);
			jButtonCancel.setText("キャンセル[C]");
			jButtonCancel.addActionListener(this);
			jButtonCancel.setMnemonic(KeyEvent.VK_C);
		}
		return jButtonCancel;
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * 戻り値を取得する
	 * @return 戻り値
	 */
	public RETURN_VALUE getStatus() {
		return ReturnValue;
	}

	/**
	 * 戻り値を格納
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			// edit s.inoue 2009/12/06
			// ReturnValue = RETURN_VALUE.OK;
			ReturnValue = RETURN_VALUE.YES;
		}
		else if(e.getSource() == jButtonCancel) {
			ReturnValue = RETURN_VALUE.CANCEL;
		}
		// モーダルダイアログの制御解除。
		setVisible(false);
	}

	/**
	 * 選択中セルのテキストをテキストエリア/リストボックスにセットする
	 * @param text 選択中セルテキスト
	 */
	public void setText(String text) {
		jLabelMessage.setText(text);
	}


	public void setMessageTitle(String messageTitle) {
		jLabelTitle.setText(messageTitle);
	}

	/**
	 * キャンセルボタン表示/非表示の設定
	 * @param isShowCancel
	 */
	public void setShowCancelButton(boolean isShowCancel) {
		this.isShowCancel = isShowCancel;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			CardLayout cardLayout1 = new CardLayout();
			cardLayout1.setHgap(5);
			jPanel = new JPanel();
//			jPanel.setBackground(new Color(238, 238, 238));
			jPanel.setLayout(cardLayout1);
			jPanel.add(getJPanel1(), getJPanel1().getName());
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabelMessage = new ExtendedLabel();
			jLabelMessage.setText("");
			jLabelMessage.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelMessage.setName("jLabelMessage");
			jLabelMessage.setFont(defaultFont);
			CardLayout cardLayout2 = new CardLayout();
			cardLayout2.setHgap(5);
			jPanel1 = new JPanel();
			jPanel1.setBackground(Color.white);
			jPanel1.setLayout(cardLayout2);
			jPanel1.setName("jPanel1");
			jPanel1.add(jLabelMessage, jLabelMessage.getName());
		}
		return jPanel1;
	}

	@Override
	public String getKenshinDate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Integer getPrintSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

//	@Override
//	public String getFilePath() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}

	@Override
	public void setDialogTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setDialogSelect(boolean enabled) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setSaveFileName(String title) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getTextValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
