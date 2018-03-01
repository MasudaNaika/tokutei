// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.Dimension;
import javax.swing.JFrame;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import org.openswing.swing.client.DeleteButton;

public class ExtendedDelButton extends DeleteButton
{
    public ExtendedDelButton()
    {
    }
    public ExtendedDelButton(JFrame jframe, String s)
    {
        setPreferredSize(new Dimension(100, 50));
        setFont(JApplication.FONT_COMMON_BUTTON);
        setVerticalAlignment(3);
        setHorizontalTextPosition(0);
        setVerticalTextPosition(3);
        ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(s);
        javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(jframe, JPath.CONST_FIX_ICON);
        setIcon(imageicon);
    }
}
