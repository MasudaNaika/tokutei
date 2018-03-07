// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import org.openswing.swing.client.ReloadButton;

public class ExtendedReloadButton extends ReloadButton
{
    public ExtendedReloadButton()
    {
    }
    public ExtendedReloadButton(JFrame jframe, String s)
    {
        setPreferredSize(new Dimension(100, 50));
        setFont(JApplication.FONT_COMMON_BUTTON);
        setHorizontalTextPosition(0);
        setVerticalAlignment(3);
        ExtendedImageIcon extendedimageicon = new ExtendedImageIcon(s);
        javax.swing.ImageIcon imageicon = extendedimageicon.setStrechIcon(jframe,JPath.CONST_FIX_ICON);
        setIcon(imageicon);
    }
}
