// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class JSelectHokenjyaOrcaDialog extends JDialog
    implements ActionListener, KeyListener
{

    public JSelectHokenjyaOrcaDialog()
    {
        jPanel_Content = null;
        jPanel_ButtonArea = null;
        jPanel_NaviArea = null;
        jLabel_Title = null;
        jLabel_MainExpl = null;
        jPanel_TitleArea = null;
        jPanel_ExplArea2 = null;
        jLabal_SubExpl = null;
        jPanel_ExplArea1 = null;
        jPanel_MainArea = null;
        jPanel_Table = null;
        jButton_End = null;
        jButton_Select = null;
        initialize(null);
    }

    public JSelectHokenjyaOrcaDialog(String s)
    {
        jPanel_Content = null;
        jPanel_ButtonArea = null;
        jPanel_NaviArea = null;
        jLabel_Title = null;
        jLabel_MainExpl = null;
        jPanel_TitleArea = null;
        jPanel_ExplArea2 = null;
        jLabal_SubExpl = null;
        jPanel_ExplArea1 = null;
        jPanel_MainArea = null;
        jPanel_Table = null;
        jButton_End = null;
        jButton_Select = null;
        initialize(s);
    }

    private void initialize(String s)
    {
        setSize(ViewSettings.getFrameCommonSize());
        setContentPane(getJPanel_Content(s));
        setModal(true);
        setLocationRelativeTo(null);
        setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setVisible(false);
    }

    private JPanel getJPanel_Content(String s)
    {
        if(jPanel_Content == null)
        {
            BorderLayout borderlayout = new BorderLayout();
            borderlayout.setVgap(2);
            jPanel_Content = new JPanel();
            jPanel_Content.setLayout(borderlayout);
            jPanel_Content.add(getJPanel_ButtonArea(), "South");
            jPanel_Content.add(getJPanel_NaviArea(s), "North");
            jPanel_Content.add(getJPanel_MainArea(), "Center");
        }
        return jPanel_Content;
    }

    private JPanel getJPanel_ButtonArea()
    {
        if(jPanel_ButtonArea == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridy = 0;
            gridbagconstraints.gridx = 0;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridy = 0;
            gridbagconstraints1.gridx = 2;
            GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
            gridbagconstraints2.gridy = 0;
            gridbagconstraints2.weightx = 1.0D;
            gridbagconstraints2.anchor = 13;
            gridbagconstraints2.gridx = 1;
            jPanel_ButtonArea = new JPanel();
            jPanel_ButtonArea.setLayout(new GridBagLayout());
            jPanel_ButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jPanel_ButtonArea.add(getJButton_Select(), gridbagconstraints2);
            jPanel_ButtonArea.add(getJButton_End(), gridbagconstraints);
        }
        return jPanel_ButtonArea;
    }

    private ExtendedButton getJButton_End()
    {
        if(jButton_End == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Back);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButton_End = new ExtendedButton("Close", "戻る(R)", "戻る(ALT+R)", 82, imageicon);
            jButton_End.addActionListener(this);
        }
        return jButton_End;
    }

    private JPanel getJPanel_NaviArea(String s)
    {
        if(jPanel_NaviArea == null)
        {
            CardLayout cardlayout = new CardLayout();
            cardlayout.setHgap(10);
            cardlayout.setVgap(10);
            jLabel_MainExpl = new JLabel();
            jLabel_MainExpl.setText("保険者を選択してください。");
            jLabel_MainExpl.setFont(new Font("Dialog", 0, 14));
            jLabel_MainExpl.setName("jLabel1");
            jLabel_Title = new JLabel();
            jLabel_Title.setText("保険者選択一覧");
            jLabel_Title.setFont(new Font("Dialog", 0, 18));
            jLabel_Title.setBackground(new Color(153, 204, 255));
            jLabel_Title.setForeground(new Color(51, 51, 51));
            jLabel_Title.setOpaque(true);
            jLabel_Title.setName("jLabel");
            jPanel_NaviArea = new JPanel();
            jPanel_NaviArea.setLayout(cardlayout);
            jPanel_NaviArea.add(getJPanel_TitleArea(), getJPanel_TitleArea().getName());
        }
        return jPanel_NaviArea;
    }

    private JPanel getJPanel_TitleArea()
    {
        if(jPanel_TitleArea == null)
        {
            GridLayout gridlayout = new GridLayout();
            gridlayout.setRows(2);
            gridlayout.setHgap(0);
            gridlayout.setColumns(0);
            gridlayout.setVgap(10);
            jPanel_TitleArea = new JPanel();
            jPanel_TitleArea.setLayout(gridlayout);
            jPanel_TitleArea.setName("jPanel2");
            jPanel_TitleArea.add(jLabel_Title, null);
            jPanel_TitleArea.add(getJPanel_ExplArea1(), null);
        }
        return jPanel_TitleArea;
    }

    private JPanel getJPanel_ExplArea2()
    {
        if(jPanel_ExplArea2 == null)
        {
            jLabal_SubExpl = new JLabel();
            jLabal_SubExpl.setFont(new Font("Dialog", 0, 14));
            GridLayout gridlayout = new GridLayout();
            gridlayout.setRows(2);
            jPanel_ExplArea2 = new JPanel();
            jPanel_ExplArea2.setName("jPanel4");
            jPanel_ExplArea2.setLayout(gridlayout);
            jPanel_ExplArea2.add(jLabel_MainExpl, null);
            jPanel_ExplArea2.add(jLabal_SubExpl, null);
        }
        return jPanel_ExplArea2;
    }

    private JPanel getJPanel_ExplArea1()
    {
        if(jPanel_ExplArea1 == null)
        {
            CardLayout cardlayout = new CardLayout();
            cardlayout.setHgap(20);
            jPanel_ExplArea1 = new JPanel();
            jPanel_ExplArea1.setLayout(cardlayout);
            jPanel_ExplArea1.add(getJPanel_ExplArea2(), getJPanel_ExplArea2().getName());
        }
        return jPanel_ExplArea1;
    }

    private JPanel getJPanel_MainArea()
    {
        if(jPanel_MainArea == null)
        {
            CardLayout cardlayout = new CardLayout();
            cardlayout.setHgap(10);
            cardlayout.setVgap(10);
            jPanel_MainArea = new JPanel();
            jPanel_MainArea.setLayout(cardlayout);
            jPanel_MainArea.add(getJPanel_Table(), getJPanel_Table().getName());
        }
        return jPanel_MainArea;
    }

    private JPanel getJPanel_Table()
    {
        if(jPanel_Table == null)
        {
            jPanel_Table = new JPanel();
            jPanel_Table.setLayout(new BorderLayout());
            jPanel_Table.setName("jPanel_DrawArea");
        }
        return jPanel_Table;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
    }

    private ExtendedButton getJButton_Select()
    {
        if(jButton_Select == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Select);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButton_Select = new ExtendedButton("Select", "選択(S)", "戻る(ALT+S)", 83, imageicon);
            jButton_Select.addActionListener(this);
        }
        return jButton_Select;
    }

    public void keyPressed(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    protected static final long serialVersionUID = 1L;
    protected JPanel jPanel_Content;
    protected JPanel jPanel_ButtonArea;
    protected JPanel jPanel_NaviArea;
    protected JLabel jLabel_Title;
    protected JLabel jLabel_MainExpl;
    protected JPanel jPanel_TitleArea;
    protected JPanel jPanel_ExplArea2;
    protected JLabel jLabal_SubExpl;
    protected JPanel jPanel_ExplArea1;
    protected JPanel jPanel_MainArea;
    protected JPanel jPanel_Table;
    protected ExtendedButton jButton_End;
    protected ExtendedButton jButton_Select;
}
