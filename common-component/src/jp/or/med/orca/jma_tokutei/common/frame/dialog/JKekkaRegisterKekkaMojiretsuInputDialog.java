// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.*;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JList;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import org.apache.log4j.Logger;

// Referenced classes of package jp.or.med.orca.jma_tokutei.common.frame.dialog:
//            JKekkaRegisterAbstractInputDialog

public class JKekkaRegisterKekkaMojiretsuInputDialog extends JKekkaRegisterAbstractInputDialog
{

    public JKekkaRegisterKekkaMojiretsuInputDialog(Frame frame, String s)
    {
        super(frame);
        TEIKEIBUN_TEXT = null;
        jListComment = null;
        String s1 = ViewSettings.getUsingValueString("tokutei.shoken.dialog.title");
        String s2 = ViewSettings.getUsingValueString("tokutei.shoken.dialog.guidance");
        TEIKEIBUN_TEXT = getTeikeiMaster();
        JList jlist = getJListComment();
        initialize(s1, s2, jlist);
        jlist.grabFocus();
        jlist.setSelectionInterval(0, 0);
        jEditorPane_Comment.setText(s);
        addComponentListener(new ComponentAdapter() {

            public void componentShown(ComponentEvent componentevent)
            {
                jListComment.grabFocus();
            }

//            final JKekkaRegisterKekkaMojiretsuInputDialog this$0;
//
//
//            {
//                this$0 = JKekkaRegisterKekkaMojiretsuInputDialog.this;
//                super();
//            }
        });
        jListComment.addMouseListener(new JSingleDoubleClickEvent(this, jButtonSelect));
    }

    private void initialize()
    {
        setSize(new Dimension(364, 191));
    }

    public String[] getTeikeiMaster()
    {
        String s = "SELECT SYOKEN_TYPE, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP  FROM T_SYOKENMASTER WHERE SYOKEN_NAME <> '' ";
        ArrayList arraylist = null;
        try
        {
            arraylist = JApplication.kikanDatabase.sendExecuteQuery(s);
        }
        catch(SQLException sqlexception)
        {
            logger.error(sqlexception.getMessage());
            sqlexception.printStackTrace();
        }
        String as[] = new String[arraylist.size()];
        for(int i = 0; i < arraylist.size(); i++)
        {
            Hashtable hashtable = (Hashtable)arraylist.get(i);
            as[i] = (String)hashtable.get("SYOKEN_NAME");
        }

        return as;
    }

    public String getText()
    {
        System.out.println(jEditorPane_Comment.getText());
        return jEditorPane_Comment.getText();
    }

    public void setText(String s)
    {
        if(s == null)
            return;
        for(int i = 0; i < TEIKEIBUN_TEXT.length; i++)
            if(TEIKEIBUN_TEXT[i].equals(s))
            {
                jListComment.setSelectedIndex(i);
                return;
            }

    }

    private JList getJListComment()
    {
        if(jListComment == null)
        {
            jListComment = new JList(TEIKEIBUN_TEXT);
            jListComment.setSelectionMode(1);
            jListComment.setFont(ViewSettings.getCommonUserInputFont());
            jListComment.addKeyListener(this);
        }
        return jListComment;
    }

    public void keyPressed(KeyEvent keyevent)
    {
        switch(keyevent.getKeyCode())
        {
        case KeyEvent.VK_R:
            logger.info(jButtonCancel.getText());
            ReturnValue = RETURN_VALUE.CANCEL;
            setVisible(false);
            break;

        case KeyEvent.VK_C:
            logger.info(jButtonClear.getText());
            jEditorPane_Comment.setText("");
            break;

        case KeyEvent.VK_S:
            logger.info(jButtonSelect.getText());
            setCommentText();
            break;

        case KeyEvent.VK_D:
            logger.info(jButtonOK.getText());
            ReturnValue = RETURN_VALUE.YES;
            setVisible(false);
            break;
        }
    }

    private void setCommentText()
    {
        String s = jEditorPane_Comment.getText().trim();
        if(s.length() > 0)
            s = (new StringBuilder()).append(s).append(JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(",")).toString();
        s = (new StringBuilder()).append(s).append(jListComment.getSelectedValue().toString().trim()).toString();
        jEditorPane_Comment.setText(s);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == jButtonSelect)
        {
            logger.info(jButtonSelect.getText());
            setCommentText();
        } else
        if(actionevent.getSource() == jButtonOK)
        {
            logger.info(jButtonOK.getText());
            ReturnValue = RETURN_VALUE.YES;
            setVisible(false);
        } else
        if(actionevent.getSource() == jButtonCancel)
        {
            logger.info(jButtonCancel.getText());
            ReturnValue = RETURN_VALUE.CANCEL;
            setVisible(false);
        } else
        if(actionevent.getSource() == jButtonClear)
        {
            logger.info(jButtonClear.getText());
            jEditorPane_Comment.setText("");
        }
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void setCommentText(String s)
    {
        jEditorPane_Comment.setText(s);
    }

    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/frame/dialog/JKekkaRegisterKekkaMojiretsuInputDialog");
    private static final long serialVersionUID = 1L;
    private final String TITLE_TEXT_KEY = "tokutei.shoken.dialog.title";
    private final String GUIDANCE_TEXT_KEY = "tokutei.shoken.dialog.guidance";
    private String TEIKEIBUN_TEXT[];
    private JList jListComment;


}
