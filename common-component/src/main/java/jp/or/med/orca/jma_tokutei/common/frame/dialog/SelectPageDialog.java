package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.apache.log4j.Logger;

///**
//* 印刷選択ダイアログ画面
//*/
public class SelectPageDialog extends JDialog
    implements ActionListener, KeyListener, ItemListener, IDialog
{

    public SelectPageDialog(Frame frame)
    {
        super(frame);
        jContentPane = null;
        jPanelNaviArea = null;
        jPanelButtonArea = null;
        jButtonOK = null;
        jButtonCancel = null;
        jLabelTitle = null;
        jPanel1 = null;
        JPanelPrint = null;
        groupPrint = new ButtonGroup();
        jRadioButton_A4_double = null;
        jRadioButton_A4_single = null;
        jLabel_pageSelect = null;
        defaultFont = ViewSettings.getCommonUserInputFont();
        initialize();
        setLocationRelativeTo(null);
    }

    private void initialize()
    {
        setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setSize(376, 193);
        setResizable(false);
        setContentPane(getJContentPane());
        jButtonOK.grabFocus();
        setModal(true);
    }

    private JPanel getJContentPane()
    {
        if(jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanelNaviArea(), "North");
            jContentPane.add(getJPanelButtonArea(), "South");
            jContentPane.add(getJPanel1(), "Center");
        }
        return jContentPane;
    }

    private JPanel getJPanelNaviArea()
    {
        if(jPanelNaviArea == null)
        {
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

    private JPanel getJPanelButtonArea()
    {
        if(jPanelButtonArea == null)
        {
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

    private ExtendedRadioButton getJRadioButton_A4_2()
    {
        if(jRadioButton_A4_double == null)
        {
            jRadioButton_A4_double = new ExtendedRadioButton();
            jRadioButton_A4_double.setText("A4-2枚");
            jRadioButton_A4_double.setPreferredSize(new Dimension(100, 20));
            jRadioButton_A4_double.addKeyListener(this);
            jRadioButton_A4_double.addItemListener(this);
            groupPrint.add(jRadioButton_A4_double);
        }
        return jRadioButton_A4_double;
    }

    private ExtendedRadioButton getJRadioButton_A4_1()
    {
        if(jRadioButton_A4_single == null)
        {
            jRadioButton_A4_single = new ExtendedRadioButton();
            jRadioButton_A4_single.setText("A4-1枚");
            jRadioButton_A4_single.setPreferredSize(new Dimension(100, 20));
            jRadioButton_A4_single.addItemListener(this);
            groupPrint.add(jRadioButton_A4_single);
            jRadioButton_A4_single.addKeyListener(this);
            jRadioButton_A4_single.setSelected(true);
        }
        return jRadioButton_A4_single;
    }

    private ExtendedButton getJButtonOK()
    {
        if(jButtonOK == null)
        {
            jButtonOK = new ExtendedButton();
            jButtonOK.setText("OK(Y)");
            jButtonOK.setActionCommand("終了");
            jButtonOK.addActionListener(this);
            jButtonOK.setMnemonic(89);
        }
        return jButtonOK;
    }

    private ExtendedButton getJButtonCancel()
    {
        if(jButtonCancel == null)
        {
            jButtonCancel = new ExtendedButton();
            jButtonCancel.setText("キャンセル[C]");
            jButtonCancel.addActionListener(this);
            jButtonCancel.setMnemonic(67);
        }
        return jButtonCancel;
    }

    public void keyPressed(KeyEvent keyevent)
    {
        switch(keyevent.getKeyCode())
        {
        case 89: // 'Y'
            returnPageSelect = jRadioButton_A4_single.isSelected() ? 1 : 2;
            ReturnValue = RETURN_VALUE.YES;
            setVisible(false);
            break;

        case 67: // 'C'
            ReturnValue = RETURN_VALUE.CANCEL;
            setVisible(false);
            break;
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public RETURN_VALUE getStatus()
    {
        return ReturnValue;
    }

    public Integer getPrintSelect()
    {
        return Integer.valueOf(returnPageSelect);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == jButtonOK)
        {
            returnPageSelect = jRadioButton_A4_double.isSelected() ? 2 : 1;
            ReturnValue = RETURN_VALUE.YES;
        } else
        if(actionevent.getSource() == jButtonCancel)
            ReturnValue = RETURN_VALUE.CANCEL;
        setVisible(false);
    }

    public void setMessageTitle(String s)
    {
        jLabelTitle.setText(s);
    }

    private JPanel getJPanel1()
    {
        if(jPanel1 == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridy = 0;
            gridbagconstraints.gridwidth = 2;
            gridbagconstraints.gridx = 0;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridy = 1;
            gridbagconstraints1.gridx = 0;
            GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
            gridbagconstraints2.gridy = 2;
            gridbagconstraints2.gridx = 0;
            jPanel1 = new JPanel();
            jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.setPreferredSize(new Dimension(300, 80));
            jPanel1.add(getJPanelPrint(), gridbagconstraints);
            jPanel1.add(getJRadioButton_A4_1(), gridbagconstraints1);
            jPanel1.add(getJRadioButton_A4_2(), gridbagconstraints2);
        }
        return jPanel1;
    }

    private JPanel getJPanelPrint()
    {
        if(JPanelPrint == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridy = 0;
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridwidth = 1;
            gridbagconstraints.fill = 1;
            JPanelPrint = new JPanel();
            JPanelPrint.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JPanelPrint.setLayout(new GridBagLayout());
            JPanelPrint.add(getJLabel_PageSelect(), gridbagconstraints);
        }
        return JPanelPrint;
    }

    private ExtendedLabel getJLabel_PageSelect()
    {
        if(jLabel_pageSelect == null)
        {
            jLabel_pageSelect = new ExtendedLabel();
            jLabel_pageSelect.setText("結果通知表出力A4(1枚、2枚)方法を選択して下さい");
        }
        return jLabel_pageSelect;
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
    }

    public String getKenshinDate()
    {
        return null;
    }

    public void setText(String s)
    {
    }

    public void setShowCancelButton(boolean flag)
    {
    }

    public String getTextValue()
    {
        return null;
    }

    public void setDialogTitle(String s)
    {
    }

    public void setDialogSelect(boolean flag)
    {
    }

    public void setSaveFileName(String s)
    {
    }

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JPanel jPanelNaviArea;
    private JPanel jPanelButtonArea;
    protected ExtendedButton jButtonOK;
    protected ExtendedButton jButtonCancel;
    private ExtendedLabel jLabelTitle;
    protected RETURN_VALUE ReturnValue;
    protected int returnPageSelect;
    private JPanel jPanel1;
    private JPanel JPanelPrint;
    private Font defaultFont;
    protected ButtonGroup groupPrint;
    protected ExtendedRadioButton jRadioButton_A4_double;
    protected ExtendedRadioButton jRadioButton_A4_single;
    private ExtendedLabel jLabel_pageSelect;
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/component/SelectPageDialog");
	@Override
	public String getFilePath() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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

}

//package jp.or.med.orca.jma_tokutei.common.frame.dialog;
//
//import java.awt.BorderLayout;
//import java.awt.CardLayout;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Frame;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.Vector;
//
//import javax.swing.BorderFactory;
//import javax.swing.ButtonGroup;
//import javax.swing.JDialog;
//import javax.swing.JPanel;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
//import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;
//import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//
//// add s.inoue 2009/12/24
///**
// * 印刷選択ダイアログ画面
// */
//public class SelectPageDialog extends JDialog
//	implements ActionListener, KeyListener,ItemListener, IDialog {
//
//	private static final long serialVersionUID = 1L;
//
//	private JPanel jContentPane = null;
//	private JPanel jPanelNaviArea = null;
//	private JPanel jPanelButtonArea = null;
//
//	protected ExtendedButton jButtonOK = null;
//	protected ExtendedButton jButtonCancel = null;
//	private ExtendedLabel jLabelTitle = null;
//	protected RETURN_VALUE ReturnValue;  //  @jve:decl-index=0:
//	protected int returnPageSelect;  //  @jve:decl-index=0:
//	private JPanel jPanel1 = null;
//	private JPanel JPanelPrint = null;
//	private Font defaultFont;
//
//	protected ButtonGroup groupPrint = new ButtonGroup();
//	protected ExtendedRadioButton jRadioButton_A4_double = null;
//	protected ExtendedRadioButton jRadioButton_A4_single = null;
//
//
//	private ExtendedLabel jLabel_pageSelect = null;
//
//    private static org.apache.log4j.Logger logger = Logger.getLogger(SelectPageDialog.class);
//	/**
//	 * @param owner
//	 */
//	public SelectPageDialog(Frame owner) {
//		super(owner);
//		defaultFont = ViewSettings.getCommonUserInputFont();
//		initialize();
//		this.setLocationRelativeTo(null);
//	}
//
//	private void initialize() {
//		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
//		this.setSize(400, 200);
//		this.setResizable(false);
//		this.setContentPane(getJContentPane());
//		this.jButtonOK.grabFocus();
//
//		setModal(true);
//	}
//
//	/**
//	 * This method initializes jContentPane
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJContentPane() {
//		if (jContentPane == null) {
//			jContentPane = new JPanel();
//			jContentPane.setLayout(new BorderLayout());
//			jContentPane.add(getJPanelNaviArea(), BorderLayout.NORTH);
//			jContentPane.add(getJPanelButtonArea(), BorderLayout.SOUTH);
//			jContentPane.add(getJPanel1(),BorderLayout.CENTER);
//		}
//		return jContentPane;
//	}
//
//	/**
//	 * This method initializes jPanelNaviArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelNaviArea() {
//		if (jPanelNaviArea == null) {
//			CardLayout cardLayout = new CardLayout();
//			cardLayout.setHgap(5);
//			cardLayout.setVgap(5);
//			jPanelNaviArea = new JPanel();
//			jPanelNaviArea.setLayout(cardLayout);
//			jLabelTitle = new TitleLabel("");
//			jLabelTitle.setText("");
//			jLabelTitle.setName("jLabelTitle");
//			jPanelNaviArea.add(jLabelTitle, jLabelTitle.getName());
//		}
//		return jPanelNaviArea;
//	}
//
//	/**
//	 * This method initializes jPanelButtonArea
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanelButtonArea() {
//		if (jPanelButtonArea == null) {
//			GridBagConstraints gridBagConstraintsOkBtn = new GridBagConstraints();
//			gridBagConstraintsOkBtn.gridx = 1;
//			gridBagConstraintsOkBtn.gridy = 0;
//			// gridBagConstraintsOkBtn.fill = GridBagConstraints.BOTH;
//
//			GridBagConstraints gridBagConstraintsCancelBtn = new GridBagConstraints();
//			gridBagConstraintsCancelBtn.gridx = 2;
//			gridBagConstraintsCancelBtn.gridy = 0;
//			gridBagConstraintsCancelBtn.insets = new Insets(5,5,5,5);
//
//			jPanelButtonArea = new JPanel();
//			jPanelButtonArea.setLayout(new GridBagLayout());
//			jPanelButtonArea.add(getJButtonOK(), gridBagConstraintsOkBtn);
//			jPanelButtonArea.add(getJButtonCancel(), gridBagConstraintsCancelBtn);
//		}
//		return jPanelButtonArea;
//	}
//	/**
//	 * This method initializes jRadioButton_Male
//	 *
//	 * @return javax.swing.JRadioButton
//	 */
//	private ExtendedRadioButton getJRadioButton_A4_2() {
//		if (jRadioButton_A4_double == null) {
//			jRadioButton_A4_double = new ExtendedRadioButton();
//			jRadioButton_A4_double.setText("A4-2枚");
//			jRadioButton_A4_double.setPreferredSize(new Dimension(100, 20));
//			jRadioButton_A4_double.addKeyListener(this);
//			jRadioButton_A4_double.addItemListener(this);
//			groupPrint.add(jRadioButton_A4_double);
//
//		}
//		return jRadioButton_A4_double;
//	}
//
//	/**
//	 * This method initializes jRadioButton_Female
//	 *
//	 * @return javax.swing.EventHandleRadioButton
//	 */
//	private ExtendedRadioButton getJRadioButton_A4_1() {
//		if (jRadioButton_A4_single == null) {
//			jRadioButton_A4_single = new ExtendedRadioButton();
//			jRadioButton_A4_single.setText("A4-1枚");
//			jRadioButton_A4_single.setPreferredSize(new Dimension(100, 20));
//			jRadioButton_A4_single.addItemListener(this);
//			groupPrint.add(jRadioButton_A4_single);
//			jRadioButton_A4_single.addKeyListener(this);
//			// 初期値
//			jRadioButton_A4_single.setSelected(true);
//		}
//		return jRadioButton_A4_single;
//	}
//
//	/**
//	 * This method initializes jButtonOK
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButtonOK() {
//		if (jButtonOK == null) {
//			jButtonOK = new ExtendedButton();
//			jButtonOK.setText("OK(Y)");
//			jButtonOK.setActionCommand("終了");
//			jButtonOK.addActionListener(this);
//			jButtonOK.setMnemonic(KeyEvent.VK_Y);
//		}
//		return jButtonOK;
//	}
//
//	/**
//	 * This method initializes jButtonCancel
//	 *
//	 * @return javax.swing.ExtendedButton
//	 */
//	private ExtendedButton getJButtonCancel() {
//		if (jButtonCancel == null) {
//			jButtonCancel = new ExtendedButton();
//			jButtonCancel.setText("キャンセル[C]");
//			jButtonCancel.addActionListener(this);
//			jButtonCancel.setMnemonic(KeyEvent.VK_C);
//		}
//		return jButtonCancel;
//	}
//
//	// edit s.inoue 2009/09/29
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_Y:
//			// edit s.inoue 2010/04/14
//			returnPageSelect = jRadioButton_A4_single.isSelected() ? 1 :2;
//			ReturnValue = RETURN_VALUE.YES;
//			// モーダルダイアログの制御解除。
//			setVisible(false);break;
//		case KeyEvent.VK_C:
//			ReturnValue = RETURN_VALUE.CANCEL;
//			// モーダルダイアログの制御解除。
//			setVisible(false);break;
//		}
//
//
//	}
//	public void keyReleased(KeyEvent arg0) {
//	}
//	public void keyTyped(KeyEvent arg0) {
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
//	/**
//	 * 戻り値を取得する
//	 * @return 戻り値
//	 */
//	public Integer getPrintSelect() {
//		return returnPageSelect;
//	}
//
//	/**
//	 * 戻り値を格納
//	 */
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == jButtonOK) {
//			returnPageSelect = jRadioButton_A4_double.isSelected() ? 2 :1;
//			ReturnValue = RETURN_VALUE.YES;
//		}
//		else if(e.getSource() == jButtonCancel) {
//			ReturnValue = RETURN_VALUE.CANCEL;
//		}
//		// モーダルダイアログの制御解除。
//		setVisible(false);
//	}
//
//	public void setMessageTitle(String messageTitle) {
//		jLabelTitle.setText(messageTitle);
//	}
//
//	/**
//	 * This method initializes jPanel1
//	 *
//	 * @return javax.swing.JPanel
//	 */
//	private JPanel getJPanel1() {
//		if (jPanel1 == null) {
//
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.gridwidth = 2;
//			gridBagConstraints.gridx = 0;
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridy = 1;
//			gridBagConstraints1.gridx = 0;
//			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 2;
//			gridBagConstraints2.gridx = 0;
//
//			jPanel1 = new JPanel();
//			jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			jPanel1.setLayout(new GridBagLayout());
//			jPanel1.setPreferredSize(new Dimension(300,80));
//			jPanel1.add(getJPanelPrint(), gridBagConstraints);
//			jPanel1.add(getJRadioButton_A4_1(), gridBagConstraints1);
//			jPanel1.add(getJRadioButton_A4_2(), gridBagConstraints2);
//		}
//		return jPanel1;
//	}
//	private JPanel getJPanelPrint() {
//		if (JPanelPrint == null) {
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridy = 0;
//			gridBagConstraints1.gridx = 0;
//			gridBagConstraints1.gridwidth = 1;
//			gridBagConstraints1.ipadx = 277;
//			gridBagConstraints1.fill = GridBagConstraints.BOTH;
//
//			JPanelPrint = new JPanel();
//			JPanelPrint.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//			JPanelPrint.setLayout(new GridBagLayout());
//			JPanelPrint.setPreferredSize(new Dimension(300,80));
//			JPanelPrint.add(getJLabel_PageSelect(), gridBagConstraints1);
//		}
//		return JPanelPrint;
//	}
//	private ExtendedLabel getJLabel_PageSelect() {
//		if (jLabel_pageSelect == null) {
//			jLabel_pageSelect = new ExtendedLabel();
//			jLabel_pageSelect.setText("結果通知表出力A4(1枚、2枚)方法を選択して下さい");
//			jLabel_pageSelect.setPreferredSize(new Dimension(300, 60));
//		}
//		return jLabel_pageSelect;
//	}
//
//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public String getKenshinDate() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public void setText(String text) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void setShowCancelButton(boolean isShowCancel) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public String getFilePath() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public void setDialogTitle(String title) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void setDialogSelect(boolean enabled) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void setSaveFileName(String title) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public String getK_PNo() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public String getK_PName() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public String getK_PNote() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//	public Vector getDataSelect() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//}  //  @jve:decl-index=0:visual-constraint="10,10"
