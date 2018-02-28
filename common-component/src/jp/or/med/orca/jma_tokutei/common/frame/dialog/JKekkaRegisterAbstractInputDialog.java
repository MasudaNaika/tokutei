// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.*;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JEnterEvent;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

// Referenced classes of package jp.or.med.orca.jma_tokutei.common.frame.dialog:
//            IKekkaRegisterInputDialog

public abstract class JKekkaRegisterAbstractInputDialog extends JDialog
    implements ActionListener, KeyListener, MouseListener, IKekkaRegisterInputDialog
{

    public JKekkaRegisterAbstractInputDialog(Frame frame)
    {
        super(frame);
        jContentPane = null;
        jPanelNaviArea = null;
        jPanelMainArea = null;
        jPanelButtonArea = null;
        jScrollPane = null;
        jButtonOK = null;
        jButtonSelect = null;
        jButtonCancel = null;
        jButtonClear = null;
        jPanelLabel = null;
        jLabelTitle = null;
        jLabelGuidance = null;
        jEditorPane_Comment = null;
        defaultFont = ViewSettings.getCommonUserInputFont();
        guidanceFont = new Font("Dialog", 0, 14);
    }

    protected void initialize(String s, String s1, Component component)
    {
        setSize(480, 420);
        setContentPane(getJContentPane(s, s1));
        setText(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        setLocationRelativeTo(null);
        jButtonOK.addKeyListener(new JEnterEvent(this));
        jButtonCancel.addKeyListener(new JEnterEvent(this));
        jScrollPane.setViewportView(component);
        setModal(true);
    }

    private JPanel getJContentPane(String s, String s1)
    {
        if(jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanelMainArea(), "Center");
            jContentPane.add(getJPanelButtonArea(), "North");
            setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());
        }
        return jContentPane;
    }

    private JPanel getJPanelNaviArea(String s, String s1)
    {
        if(jPanelNaviArea == null)
        {
            CardLayout cardlayout = new CardLayout();
            cardlayout.setHgap(5);
            cardlayout.setVgap(5);
            jPanelNaviArea = new JPanel();
            jPanelNaviArea.setLayout(cardlayout);
            jPanelNaviArea.add(getJPanelLabel(s, s1), getJPanelLabel(s, s1).getName());
        }
        return jPanelNaviArea;
    }

    private JPanel getJPanelMainArea()
    {
        if(jPanelMainArea == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.fill = 1;
            gridbagconstraints.weightx = 1.0D;
            gridbagconstraints.weighty = 1.0D;
            gridbagconstraints.insets = new Insets(0, 5, 0, 5);
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.fill = 1;
            gridbagconstraints1.weightx = 1.0D;
            gridbagconstraints1.weighty = 1.0D;
            gridbagconstraints1.insets = new Insets(0, 5, 0, 5);
            gridbagconstraints1.gridx = 0;
            gridbagconstraints1.gridy = 1;
            jPanelMainArea = new JPanel();
            jPanelMainArea.setLayout(new GridBagLayout());
            jPanelMainArea.add(getJScrollPane(), gridbagconstraints);
            jPanelMainArea.add(getJEditorPane_Comment(), gridbagconstraints1);
        }
        return jPanelMainArea;
    }

    private JPanel getJPanelButtonArea()
    {
        if(jPanelButtonArea == null)
        {
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 0;
            gridbagconstraints.gridy = 0;
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridx = 2;
            gridbagconstraints1.gridy = 0;
            gridbagconstraints1.insets = new Insets(0, 5, 0, 0);
            GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
            gridbagconstraints2.gridx = 3;
            gridbagconstraints2.gridy = 0;
            gridbagconstraints2.insets = new Insets(0, 5, 0, 0);
            gridbagconstraints2.weightx = 1.0D;
            gridbagconstraints2.anchor = 17;
            GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
            gridbagconstraints3.gridx = 1;
            gridbagconstraints3.gridy = 0;
            gridbagconstraints3.insets = new Insets(0, 5, 0, 0);
            jPanelButtonArea = new JPanel();
            jPanelButtonArea.setLayout(new GridBagLayout());
            jPanelButtonArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            jPanelButtonArea.add(getJButtonCancel(), gridbagconstraints);
            jPanelButtonArea.add(getJButtonClear(), gridbagconstraints3);
            jPanelButtonArea.add(getJButtonOK(), gridbagconstraints2);
            jPanelButtonArea.add(getJButtonSelect(), gridbagconstraints1);
        }
        return jPanelButtonArea;
    }

    private JScrollPane getJScrollPane()
    {
        if(jScrollPane == null)
        {
            jScrollPane = new JScrollPane();
            jScrollPane.addKeyListener(this);
        }
        return jScrollPane;
    }

    private JEditorPane getJEditorPane_Comment()
    {
        if(jEditorPane_Comment == null)
        {
            jEditorPane_Comment = new ExtendedEditorPane(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_ZENKAKU);
            jEditorPane_Comment.setBorder(BorderFactory.createEtchedBorder(1));
            jEditorPane_Comment.addMouseListener(this);
            jEditorPane_Comment.addKeyListener(this);
        }
        return jEditorPane_Comment;
    }

    private ExtendedButton getJButtonOK()
    {
        if(jButtonOK == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Decide);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButtonOK = new ExtendedButton("Decide", "確定(D)", "確定(ALT+D)", 68, imageicon);
            jButtonOK.addActionListener(this);
        }
        return jButtonOK;
    }

    private ExtendedButton getJButtonSelect()
    {
        if(jButtonSelect == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Select);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButtonSelect = new ExtendedButton("Select", "選択(S)", "選択(ALT+S)", 83, imageicon);
            jButtonSelect.addActionListener(this);
        }
        return jButtonSelect;
    }

    private ExtendedButton getJButtonClear()
    {
        if(jButtonClear == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Clear);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            // eidt s.inoue 2012/07/10
            jButtonClear = new ExtendedButton("Clear", "クリア(E)", "クリア(ALT+E)", KeyEvent.VK_E, imageicon);
            jButtonClear.addActionListener(this);
        }
        return jButtonClear;
    }

    private ExtendedButton getJButtonCancel()
    {
        if(jButtonCancel == null)
        {
            ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(JPath.Ico_Common_Back);
            javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(this, 0.80000000000000004D);
            jButtonCancel = new ExtendedButton("Return", "戻る(R)", "戻る(ALT+R)", 82, imageicon);
            jButtonCancel.addActionListener(this);
        }
        return jButtonCancel;
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

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public RETURN_VALUE getStatus()
    {
        return ReturnValue;
    }

    public abstract void setText(String s);

    public abstract String getText();

    private JPanel getJPanelLabel(String s, String s1)
    {
        if(jPanelLabel == null)
        {
            jLabelGuidance = new ExtendedLabel();
            jLabelGuidance.setFont(guidanceFont);
            jLabelGuidance.setText(s1);
            jLabelTitle = new TitleLabel();
            jLabelTitle.setText(s);
            GridLayout gridlayout = new GridLayout();
            gridlayout.setRows(2);
            gridlayout.setVgap(5);
            jPanelLabel = new JPanel();
            jPanelLabel.setLayout(gridlayout);
            jPanelLabel.setName("jPanelLabel");
            jPanelLabel.add(jLabelTitle, null);
            jPanelLabel.add(jLabelGuidance, null);
        }
        return jPanelLabel;
    }

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JPanel jPanelNaviArea;
    private JPanel jPanelMainArea;
    private JPanel jPanelButtonArea;
    protected JScrollPane jScrollPane;
    protected ExtendedButton jButtonOK;
    protected ExtendedButton jButtonSelect;
    protected ExtendedButton jButtonCancel;
    protected ExtendedButton jButtonClear;
    protected RETURN_VALUE ReturnValue;
    private Font guidanceFont;
    private Font defaultFont;
    private JPanel jPanelLabel;
    private ExtendedLabel jLabelTitle;
    private ExtendedLabel jLabelGuidance;
    protected ExtendedEditorPane jEditorPane_Comment;
}
