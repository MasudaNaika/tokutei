// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.openswing;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.openswing.swing.client.LabelControl;

public class ExtendedOpenLabel extends LabelControl
{

    public ExtendedOpenLabel()
    {
        Initialize();
    }

    private void Initialize()
    {
        java.awt.Font font = ViewSettings.getCommonUserInputFont();
        setFont(font);
        setFontStyle(-1);
        setFocusable(false);
        setHorizontalAlignment(2);
    }

    public ExtendedOpenLabel(String s, int i)
    {
        this(s);
        setFontStyle(i);
        setFocusable(false);
    }

    public ExtendedOpenLabel(int i)
    {
        setFontStyle(i);
        setFocusable(false);
    }

    private void setFontStyle(int i)
    {
        java.awt.Font font = ViewSettings.getCommonUserInputFont(i);
        setFont(font);
    }

    public ExtendedOpenLabel(String s)
    {
        this();
        if(s != null && !s.isEmpty())
            setTextFromKey(s);
    }

    public void setHtmlText(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("<html>");
        stringbuffer.append(s);
        stringbuffer.append("</html>");
        super.setText(stringbuffer.toString());
    }

    public void setTextFromKey(String s)
    {
        String s1 = ViewSettings.getUsingValueString(s);
        if(s1 == null)
            s1 = "";
        setHtmlText(s1);
    }
}
