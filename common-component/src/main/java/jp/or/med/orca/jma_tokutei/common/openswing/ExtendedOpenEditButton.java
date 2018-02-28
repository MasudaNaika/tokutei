// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.Dimension;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import org.openswing.swing.client.EditButton;

public class ExtendedOpenEditButton extends EditButton
{

    public ExtendedOpenEditButton()
    {
        setPreferredSize(new Dimension(100, 50));
        setFont(JApplication.FONT_COMMON_BUTTON);
        setHorizontalTextPosition(0);
        setVerticalAlignment(3);
    }
}
