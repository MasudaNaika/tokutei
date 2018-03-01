package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.apache.log4j.Logger;

/**
 * 検査方法ダイアログクラス
 */
public class SelectKenshinHouhouDialog extends JDialog
    implements ActionListener, KeyListener, ItemListener, IDialog
{

    public SelectKenshinHouhouDialog(JFrame jframe, ArrayList arraylist)
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
        jComboBox_KensaHouhou = null;
        jLabel_PatternSelect = null;
        jLabel_Taisyosha = null;
        defaultFont = ViewSettings.getCommonUserInputFont();
        initialize(arraylist);
        setLocationRelativeTo(null);
    }

    private void initialize(ArrayList arraylist)
    {
        setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setSize(fixedWidth, fixedHight);
        setResizable(false);
        setContentPane(getJContentPane());
        jButtonOK.grabFocus();
        setinitilizeCombobox(arraylist);
        setModal(true);
    }

    private void setinitilizeCombobox(ArrayList arraylist)
    {
        jComboBox_KensaHouhou.removeAllItems();
        for(int i = 1; i < arraylist.size(); i++)
        {
            String s = String.valueOf(arraylist.get(i));
            jComboBox_KensaHouhou.addItem(s);
            jComboBox_KensaHouhou.setToolTipText(s);
        }

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
        }
        return jPanelButtonArea;
    }

    private ExtendedComboBox getJCombobox_Pattern()
    {
        if(jComboBox_KensaHouhou == null)
        {
            jComboBox_KensaHouhou = new ExtendedComboBox();
            jComboBox_KensaHouhou.addItemListener(this);
            jComboBox_KensaHouhou.addKeyListener(this);
        }
        return jComboBox_KensaHouhou;
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
            returnKensaHouhou = (String)jComboBox_KensaHouhou.getSelectedItem();
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

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == jButtonOK)
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
            gridbagconstraints.fill = 1;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridy = 1;
            gridbagconstraints1.gridx = 0;
            gridbagconstraints1.gridwidth = 2;
            gridbagconstraints1.anchor = 17;
            jPanel1 = new JPanel();
            jPanel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jPanel1.setLayout(new GridBagLayout());
            jPanel1.setPreferredSize(new Dimension(fixedWidth, fixedHight));
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
            gridbagconstraints.ipadx = 400;
            gridbagconstraints.fill = 1;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridy = 1;
            gridbagconstraints1.gridx = 0;
            gridbagconstraints1.fill = 1;
            JPanelPrint = new JPanel();
            JPanelPrint.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JPanelPrint.setLayout(new GridBagLayout());
            JPanelPrint.setPreferredSize(new Dimension(550, 200));
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
            String s = "検査方法は以下の通りです。<br>変更を行う場合、健診パターンマスタメンテナンスにて実施して下さい";
            jLabel_PatternSelect.setHtmlText(s);
            jLabel_PatternSelect.setPreferredSize(new Dimension(fixedWidth, fixedHight));
        }
        return jLabel_PatternSelect;
    }

    private ExtendedLabel getJLabel_Taisyosya()
    {
        if(jLabel_Taisyosha == null)
        {
            jLabel_Taisyosha = new ExtendedLabel();
            jLabel_Taisyosha.setPreferredSize(new Dimension(fixedWidth, fixedHight));
            jLabel_Taisyosha.setFont(new Font("Dialog", 1, 13));
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
        return returnKensaHouhou;
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

    public Integer getPrintSelect()
    {
        return null;
    }

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JPanel jPanelNaviArea;
    private JPanel jPanelButtonArea;
    protected ExtendedButton jButtonOK;
    protected ExtendedButton jButtonCancel;
    private ExtendedLabel jLabelTitle;
    protected RETURN_VALUE ReturnValue;
    protected String returnKensaHouhou;
    private JPanel jPanel1;
    private JPanel JPanelPrint;
    private Font defaultFont;
    protected ExtendedComboBox jComboBox_KensaHouhou;
    private ExtendedLabel jLabel_PatternSelect;
    private ExtendedLabel jLabel_Taisyosha;
    private static int fixedWidth = 450;
    private static int fixedHight = 200;
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/component/SelectKenshinHouhouDialog");

}
