// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.component;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

// Referenced classes of package jp.or.med.orca.jma_tokutei.common.component:
//            ExtendedLabel

public class GuidanceLabel extends ExtendedLabel
{

    public GuidanceLabel(String s)
    {
        java.awt.Font font = ViewSettings.getGuidanceLabelFont();
        setFont(font);
        setText(ViewSettings.getUsingValueString(s));
    }
}
