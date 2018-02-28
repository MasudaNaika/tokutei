// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import javax.swing.JFrame;

// Referenced classes of package jp.or.med.orca.jma_tokutei.common.frame.dialog:
//            JKekkaRegisterKekkaMojiretsuInputDialog, JKekkaRegisterCommentInputDialog, IKekkaRegisterInputDialog

public class JKekkaRegisterInputDialogFactory
{

    public JKekkaRegisterInputDialogFactory()
    {
    }

    public static IKekkaRegisterInputDialog createDialog(JFrame jframe, int i, String s)
    {
        return new JKekkaRegisterKekkaMojiretsuInputDialog(jframe, s);
    }

    public static IKekkaRegisterInputDialog createDialogComment(JFrame jframe, String s)
    {
        return new JKekkaRegisterKekkaMojiretsuInputDialog(jframe, s);
    }

    public static IKekkaRegisterInputDialog createDialogSogoComment(JFrame jframe, String s)
    {
        return new JKekkaRegisterCommentInputDialog(jframe, s);
    }
}
