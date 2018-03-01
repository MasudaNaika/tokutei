// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

/**
 * ファイル選択ダイアログ画面
 */
public interface IKekkaRegisterInputDialog
{

    public abstract void setVisible(boolean flag);

    public abstract RETURN_VALUE getStatus();

    public abstract void setText(String s);

    public abstract void setCommentText(String s);

    public abstract String getText();
}
