package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/**
 * 健診パターン選択ダイアログ画面
 */
public class SelectKenshinPatternDialog extends JDialog
	implements ActionListener, KeyListener,ItemListener, IDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelNaviArea = null;
	private JPanel jPanelButtonArea = null;

	protected ExtendedButton jButtonOK = null;
	protected ExtendedButton jButtonCancel = null;
	private ExtendedLabel jLabelTitle = null;
	protected RETURN_VALUE ReturnValue;
	protected int returnPageSelect;
	private JPanel jPanel1 = null;
	private JPanel JPanelPrint = null;
	private Font defaultFont;

	protected ExtendedComboBox jComboBox_Pattern = null;

	private ExtendedLabel jLabel_PatternSelect = null;
	private ExtendedLabel jLabel_Taisyosha = null;

    private static org.apache.log4j.Logger logger = Logger.getLogger(SelectKenshinPatternDialog.class);

	/**
	 * @param owner
	 */
	public SelectKenshinPatternDialog(JFrame owner, String s, String s1){
		super(owner);
		defaultFont = ViewSettings.getCommonUserInputFont();
		initialize(s,s1);
		this.setLocationRelativeTo(null);
	}

	private void initialize(String s, String s1) {
        setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setSize(376, 193);
        setResizable(false);
        setContentPane(getJContentPane(s1));
        jButtonOK.grabFocus();
        setinitilizeCombobox();
        jLabel_Taisyosha.setText(s);
        setModal(true);
	}

	// 健診パターンデータ初期化
	private void setinitilizeCombobox(){
		jComboBox_Pattern.removeAllItems();

		ArrayList<Hashtable<String, String>> items = null;
		try {
			items = JApplication.kikanDatabase
			.sendExecuteQuery("SELECT * FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 AND K_P_NO <> '9999' ORDER BY K_P_NO");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < items.size(); i++) {
			Hashtable<String, String> Item;
			Item = items.get(i);
			jComboBox_Pattern.addItem(Item.get("K_P_NO") +":"+ Item.get("K_P_NAME"));
		}

		System.out.println("追加完了");
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane(String s) {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelNaviArea(), BorderLayout.NORTH);
			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(s),BorderLayout.CENTER);
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
	        CardLayout cardlayout = new CardLayout();
            cardlayout.setHgap(5);
            cardlayout.setVgap(5);
            jPanelNaviArea = new JPanel();
            jPanelNaviArea.setLayout(cardlayout);
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
			GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 1;
            gridbagconstraints.gridy = 0;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridx = 2;
            gridbagconstraints1.gridy = 0;
            gridbagconstraints1.insets = new Insets(5, 5, 5, 5);
            jPanelButtonArea = new JPanel();
            jPanelButtonArea.setLayout(new GridBagLayout());
            jPanelButtonArea.add(getJButtonOK(), gridbagconstraints);
            jPanelButtonArea.add(getJButtonCancel(), gridbagconstraints1);
		}
		return jPanelButtonArea;
	}

	/**
	 * This method initializes ExtendedComboBox
	 *
	 * @return javax.swing.EventHandleCombobox
	 */
	private ExtendedComboBox getJCombobox_Pattern() {
		if (jComboBox_Pattern == null) {
            jComboBox_Pattern = new ExtendedComboBox();
            jComboBox_Pattern.setPreferredSize(new Dimension(300, 20));
            jComboBox_Pattern.addItemListener(this);
            jComboBox_Pattern.addKeyListener(this);
		}
		return jComboBox_Pattern;
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

			// edit s.inoue 2010/10/20
			// returnPageSelect = jComboboxPattern.isSelected() ? 1 :2;
			returnPageSelect = jComboBox_Pattern.getSelectedIndex() + 1;

			ReturnValue = RETURN_VALUE.YES;
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
			// edit s.inoue 2010/10/20
			returnPageSelect = jComboBox_Pattern.getSelectedIndex() + 1;
			ReturnValue = RETURN_VALUE.YES;
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
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1(String s) {
		if (jPanel1 == null) {
	        GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridy = 0;
            gridbagconstraints.gridwidth = 2;
            gridbagconstraints.gridx = 0;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridy = 1;
            gridbagconstraints1.gridx = 0;
            gridbagconstraints1.gridwidth = 2;
            gridbagconstraints1.anchor = 17;
            jPanel1 = new JPanel();
            jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.setPreferredSize(new Dimension(400, 80));
            jPanel1.add(getJPanelPrint(s), gridbagconstraints);
            jPanel1.add(getJCombobox_Pattern(), gridbagconstraints1);
		}
		return jPanel1;
	}

	private JPanel getJPanelPrint(String s) {
		if (JPanelPrint == null) {
	        GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridy = 0;
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridwidth = 1;
            gridbagconstraints.ipadx = 277;
            gridbagconstraints.fill = 1;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridy = 1;
            gridbagconstraints1.gridx = 0;
            gridbagconstraints1.gridwidth = 2;
            gridbagconstraints1.fill = 1;
            JPanelPrint = new JPanel();
            JPanelPrint.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JPanelPrint.setLayout(new GridBagLayout());
            JPanelPrint.setPreferredSize(new Dimension(300, 80));
            JPanelPrint.add(getJLabel_PageSelect(s), gridbagconstraints);
            JPanelPrint.add(getJLabel_Taisyosya(), gridbagconstraints1);
		}
		return JPanelPrint;
	}

	private ExtendedLabel getJLabel_PageSelect(String s)
    {
        if(jLabel_PatternSelect == null)
        {
            jLabel_PatternSelect = new ExtendedLabel();
            jLabel_PatternSelect.setText(s);
            jLabel_PatternSelect.setPreferredSize(new Dimension(350, 80));
        }
        return jLabel_PatternSelect;
    }

    private ExtendedLabel getJLabel_Taisyosya()
    {
        if(jLabel_Taisyosha == null)
        {
            jLabel_Taisyosha = new ExtendedLabel();
            jLabel_Taisyosha.setPreferredSize(new Dimension(350, 80));
        }
        return jLabel_Taisyosha;
    }

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getKenshinDate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setShowCancelButton(boolean isShowCancel) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getFilePath() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

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
	public String getK_PNo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getK_PNote() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Vector getDataSelect() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
