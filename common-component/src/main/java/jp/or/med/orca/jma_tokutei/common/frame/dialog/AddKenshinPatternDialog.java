package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextField;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * 印刷選択ダイアログ画面
 */
public class AddKenshinPatternDialog extends JDialog
	implements ActionListener, KeyListener,ItemListener, IDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanelButtonArea = null;

	protected ExtendedButton jButtonOK = null;
	protected ExtendedButton jButtonCancel = null;
	private ExtendedLabel jLabelTitle = null;
	protected RETURN_VALUE ReturnValue;

	protected String ret_KPNm ="";
	protected String ret_KPNote ="";

	protected int returnPageSelect;
	private JPanel jPanel1 = null;
	private JPanel JPanelPrint = null;
	private Font defaultFont;

	protected ButtonGroup groupPrint = new ButtonGroup();
	protected ExtendedLabel jLabel_PatternNo = null;
	protected ExtendedLabel jLabel_PatternNm = null;
	protected ExtendedLabel jLabel_selectPattern = null;
	protected ExtendedLabel jLabel_Biko = null;

	protected ExtendedTextField jText_DeplicateNm = null;
	protected ExtendedTextField jText_DeplicateBiko = null;



    private static org.apache.log4j.Logger logger = Logger.getLogger(SelectPageDialog.class);  //  @jve:decl-index=0:

	/**
	 * @param owner
	 */
	public AddKenshinPatternDialog(JFrame owner,int curIdx,int newPattern) {
		super(owner);
		defaultFont = ViewSettings.getCommonUserInputFont();
		initialize(curIdx,newPattern);
		this.setLocationRelativeTo(null);
	}

	private void initialize(int curIdx,int newPattern) {
		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
		this.setSize(380, 250);
		// this.setResizable(false);
		this.setContentPane(getJContentPane());

		this.jLabel_PatternNo.setText("健診パターンNo:"
				+ String.valueOf(curIdx)
				+"から健診パターンNo:"+ String.valueOf(newPattern) + "へ複製");
		this.jButtonOK.grabFocus();
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
			jContentPane.add(getJPanel1(),BorderLayout.CENTER);
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
			gridBagConstraintsOkBtn.gridx = 1;
			gridBagConstraintsOkBtn.gridy = 0;
			// gridBagConstraintsOkBtn.fill = GridBagConstraints.BOTH;

			GridBagConstraints gridBagConstraintsCancelBtn = new GridBagConstraints();
			gridBagConstraintsCancelBtn.gridx = 2;
			gridBagConstraintsCancelBtn.gridy = 0;
			gridBagConstraintsCancelBtn.insets = new Insets(5,5,5,5);

			jPanelButtonArea = new JPanel();
			jPanelButtonArea.setLayout(new GridBagLayout());
			jPanelButtonArea.add(getJButtonOK(), gridBagConstraintsOkBtn);
			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraintsCancelBtn);
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
			jButtonCancel.setText("キャンセル[C]");
			jButtonCancel.addActionListener(this);
			jButtonCancel.setMnemonic(KeyEvent.VK_C);
		}
		return jButtonCancel;
	}

	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_Y:
			ReturnValue = RETURN_VALUE.YES;

			// 登録処理
			// eidt s.inoue 2011/04/08
			ret_KPNm = this.jText_DeplicateNm.getText();
			ret_KPNote = this.jText_DeplicateBiko.getText();

			// モーダルダイアログの制御解除。
			setVisible(false);break;
		case KeyEvent.VK_C:
			ReturnValue = RETURN_VALUE.CANCEL;
			// モーダルダイアログの制御解除。
			setVisible(false);break;
		}


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
	 * 戻り値を取得する
	 * @return 戻り値
	 */
	public Integer getPrintSelect() {
		return returnPageSelect;
	}

	/**
	 * 戻り値を格納
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			ReturnValue = RETURN_VALUE.YES;
			// 登録処理
			// eidt s.inoue 2011/04/08
			ret_KPNm = this.jText_DeplicateNm.getText();
			ret_KPNote = this.jText_DeplicateBiko.getText();
		}
		else if(e.getSource() == jButtonCancel) {
			ReturnValue = RETURN_VALUE.CANCEL;
		}
		// モーダルダイアログの制御解除。
		setVisible(false);
	}

	public void setMessageTitle(String messageTitle) {
		jLabelTitle.setText(messageTitle);
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(5,0,5,0);

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.insets = new Insets(0,0,5,0);

			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.insets = new Insets(0,0,5,0);

			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.insets = new Insets(0,0,5,0);

			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 4;
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.insets = new Insets(0,0,5,0);

			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 5;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridwidth = 2;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.insets = new Insets(0,0,5,0);

			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setPreferredSize(new Dimension(300,260));
			jPanel1.add(getJPanelPrint(), gridBagConstraints);
			jPanel1.add(getJLabel_PatternNo(), gridBagConstraints1);
			jPanel1.add(getJLabel_PatternNm(), gridBagConstraints2);
			jPanel1.add(getJTextField_DeplicateNm(), gridBagConstraints3);
			jPanel1.add(getJLabel_Biko(), gridBagConstraints4);
			jPanel1.add(getJText_DeplicateBiko(), gridBagConstraints5);

		}
		return jPanel1;
	}
	private JPanel getJPanelPrint() {
		if (JPanelPrint == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.ipadx = 277;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;

			JPanelPrint = new JPanel();
			// JPanelPrint.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			JPanelPrint.setLayout(new GridBagLayout());
			JPanelPrint.setPreferredSize(new Dimension(300,80));
			JPanelPrint.add(getJLabel_PageSelect(), gridBagConstraints1);
		}
		return JPanelPrint;
	}
	private ExtendedLabel getJLabel_PageSelect() {
		if (jLabel_selectPattern == null) {
			jLabel_selectPattern = new ExtendedLabel();
			jLabel_selectPattern.setText("複製後の健診パターン名を記入して下さい。");
			jLabel_selectPattern.setPreferredSize(new Dimension(300, 100));
		}
		return jLabel_selectPattern;
	}

	private ExtendedLabel getJLabel_PatternNo() {
		if (jLabel_PatternNo == null) {
			jLabel_PatternNo = new ExtendedLabel();
			jLabel_PatternNo.setPreferredSize(new Dimension(350, 100));
		}
		return jLabel_PatternNo;
	}

	private ExtendedLabel getJLabel_PatternNm() {
		if (jLabel_PatternNm == null) {
			jLabel_PatternNm = new ExtendedLabel();
			jLabel_PatternNm.setText("健診パターン名");
			jLabel_PatternNm.setPreferredSize(new Dimension(350, 100));
		}
		return jLabel_PatternNm;
	}

	private ExtendedTextField getJTextField_DeplicateNm() {
		if (jText_DeplicateNm == null) {
			jText_DeplicateNm = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			jText_DeplicateNm.setPreferredSize(new Dimension(200, 100));
		}
		return jText_DeplicateNm;
	}

	private ExtendedLabel getJLabel_Biko() {
		if (jLabel_Biko == null) {
			jLabel_Biko = new ExtendedLabel();
			jLabel_Biko.setText("備考");
			jLabel_Biko.setPreferredSize(new Dimension(350, 100));
		}
		return jLabel_Biko;
	}

	private ExtendedTextField getJText_DeplicateBiko() {
		if (jText_DeplicateBiko == null) {
			jText_DeplicateBiko = new ExtendedTextField(ImeMode.IME_ZENKAKU);
			jText_DeplicateBiko.setPreferredSize(new Dimension(120, 100));
		}
		return jText_DeplicateBiko;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

	@Override
	public String getKenshinDate() {
		return null;
	}
	@Override
	public void setText(String text) {
	}
	@Override
	public void setShowCancelButton(boolean isShowCancel) {
	}
	@Override
	public String getFilePath() {
		return null;
	}
	@Override
	public void setDialogTitle(String title) {
	}
	@Override
	public void setDialogSelect(boolean enabled) {
	}
	@Override
	public void setSaveFileName(String title) {
	}

	@Override
	public String getK_PNo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PName() {
		return ret_KPNm;
	}

	@Override
	public String getK_PNote() {
		return ret_KPNote;
	}

	@Override
	public Vector getDataSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}


