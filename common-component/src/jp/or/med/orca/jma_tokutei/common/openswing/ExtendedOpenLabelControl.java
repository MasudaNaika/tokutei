// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.openswing;

import org.openswing.swing.client.LabelControl;
import org.openswing.swing.util.client.ClientSettings;

public class ExtendedOpenLabelControl extends LabelControl
{

    public ExtendedOpenLabelControl()
    {
        label = null;
        toolTipText = null;
        setTextAlignment(2);
        if(ClientSettings.TEXT_ORIENTATION != null)
            setComponentOrientation(ClientSettings.TEXT_ORIENTATION);
    }

    private String label;
    private String toolTipText;
}
