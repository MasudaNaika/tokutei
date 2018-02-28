// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import org.openswing.swing.message.receive.java.ValueObjectImpl;

public class SelectOrcaHokenjyaDialogData extends ValueObjectImpl
{

    public SelectOrcaHokenjyaDialogData()
    {
        isValidateAsDataSet = false;
    }

    public boolean isValidateAsDataSet()
    {
        return isValidateAsDataSet;
    }

    public void setValidateAsDataSet()
    {
        isValidateAsDataSet = true;
    }

    public String getK_P_NO()
    {
        if(INSURER_NAME == null)
            INSURER_NAME = "";
        return INSURER_NAME;
    }

    public String getK_P_NAME()
    {
        if(INSURER_NAME_KANA == null)
            INSURER_NAME_KANA = "";
        return INSURER_NAME_KANA;
    }

    public boolean setK_P_NO(String s)
    {
        isValidateAsDataSet = false;
        INSURER_NAME = JValidate.validatePatternNumber(s);
        if(INSURER_NAME == null)
        {
            JErrorMessage.show("M5511", null);
            return false;
        } else
        {
            return true;
        }
    }

    public boolean setK_P_NAME(String s)
    {
        isValidateAsDataSet = false;
        INSURER_NAME_KANA = JValidate.validatePatternName(s);
        if(INSURER_NAME_KANA == null)
        {
            JErrorMessage.show("M5512", null);
            return false;
        } else
        {
            return true;
        }
    }

    private String INSURER_NAME;
    private String INSURER_NAME_KANA;
    private boolean isValidateAsDataSet;
}
