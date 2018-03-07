// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.component;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

public interface IDialog
{

    public abstract void setVisible(boolean flag);

    public abstract RETURN_VALUE getStatus();

    public abstract String getTextValue();

    public abstract String getKenshinDate();

    public abstract Integer getPrintSelect();

    public abstract void setText(String s);

    public abstract void setMessageTitle(String s);

    public abstract void setEnabled(boolean flag);

    public abstract void setDialogSelect(boolean flag);

    public abstract void setDialogTitle(String s);

    public abstract void setSaveFileName(String s);

    public abstract void setShowCancelButton(boolean flag);
}
