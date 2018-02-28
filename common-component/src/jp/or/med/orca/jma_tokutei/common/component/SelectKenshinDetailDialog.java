package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.*;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.apache.log4j.Logger;

public class SelectKenshinDetailDialog extends JDialog
    implements ActionListener, KeyListener, ItemListener, IDialog
{
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
    protected ExtendedComboBox jComboBox_Pattern;
    private ExtendedLabel jLabel_PatternSelect;
    private ExtendedLabel jLabel_Taisyosha;
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/component/SelectKenshinDetailDialog");

    public SelectKenshinDetailDialog(JFrame jframe, String s)
    {
        super(jframe);
        jContentPane = null;
        jPanelNaviArea = null;
        jPanelButtonArea = null;
        jButtonOK = null;
        jButtonCancel = null;
        jLabelTitle = null;
        jPanel1 = null;
        JPanelPrint = null;
        jComboBox_Pattern = null;
        jLabel_PatternSelect = null;
        jLabel_Taisyosha = null;
        defaultFont = ViewSettings.getCommonUserInputFont();
        initialize(s);
        setLocationRelativeTo(null);
    }

    private void initialize(String s)
    {
        setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setSize(376, 193);
        setResizable(false);
        setContentPane(getJContentPane());
        jButtonOK.grabFocus();
        setinitilizeCombobox();
        jLabel_Taisyosha.setText(s);
        setModal(true);
    }

    private void setinitilizeCombobox()
    {
        jComboBox_Pattern.removeAllItems();
        jComboBox_Pattern.addItem("可視吸光光度法（コレステロール酸化酵素法)");
        jComboBox_Pattern.addItem("紫外吸光光度法（コレステロール脱水素酵素法)");
        jComboBox_Pattern.addItem("その他");
        System.out.println("追加完了");
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

    private ExtendedComboBox getJCombobox_Pattern()
    {
        if(jComboBox_Pattern == null)
        {
            jComboBox_Pattern = new ExtendedComboBox();
            jComboBox_Pattern.setPreferredSize(new Dimension(300, 20));
            jComboBox_Pattern.addItemListener(this);
            jComboBox_Pattern.addKeyListener(this);
        }
        return jComboBox_Pattern;
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
            returnPageSelect = jComboBox_Pattern.getSelectedIndex() + 1;
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
            returnPageSelect = jComboBox_Pattern.getSelectedIndex() + 1;
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
            gridbagconstraints1.gridwidth = 2;
            gridbagconstraints1.anchor = 17;
            jPanel1 = new JPanel();
            jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.setPreferredSize(new Dimension(400, 80));
            jPanel1.add(getJPanelPrint(), gridbagconstraints);
            jPanel1.add(getJCombobox_Pattern(), gridbagconstraints1);
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
            JPanelPrint.add(getJLabel_PageSelect(), gridbagconstraints);
            JPanelPrint.add(getJLabel_Taisyosya(), gridbagconstraints1);
        }
        return JPanelPrint;
    }

    private ExtendedLabel getJLabel_PageSelect()
    {
        if(jLabel_PatternSelect == null)
        {
            jLabel_PatternSelect = new ExtendedLabel();
            jLabel_PatternSelect.setText("検査方法を選択して下さい。");
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
}
