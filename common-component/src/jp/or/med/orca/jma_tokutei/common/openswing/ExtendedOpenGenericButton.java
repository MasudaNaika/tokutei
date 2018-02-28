// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import org.openswing.swing.client.GenericButton;

public class ExtendedOpenGenericButton extends GenericButton implements KeyListener
{

    public ExtendedOpenGenericButton(String s, String s1, String s2, int i, ImageIcon imageicon)
    {
        icon = null;
        createButton(s, s1, s2, i, imageicon);
        setPreferredSize(new Dimension(100, 50));
        addKeyListener(this);
    }

    public ExtendedOpenGenericButton(String s, String s1, String s2, int i, ImageIcon imageicon, Dimension dimension)
    {
        icon = null;
        createButton(s, s1, s2, i, imageicon);
        setPreferredSize(dimension);
    }

    public ExtendedOpenGenericButton(String s, String s1, String s2, ImageIcon imageicon, Dimension dimension)
    {
        icon = null;
        createButton(s, s1, s2, imageicon);
        setPreferredSize(dimension);
    }

    private void createButton(String s, String s1, String s2, int i, ImageIcon imageicon)
    {
        setText(s1);
        setToolTipText(s2);
        setAttributeName(s);
        setMnemonic(i);
        setFont(JApplication.FONT_COMMON_BUTTON);
        setVerticalAlignment(3);
        setHorizontalTextPosition(0);
        setVerticalTextPosition(3);
        setIcon(imageicon);
    }

    private void createButton(String s, String s1, String s2, ImageIcon imageicon)
    {
        setText(s1);
        setToolTipText(s2);
        setAttributeName(s);
        setFont(JApplication.FONT_COMMON_BUTTON);
        setVerticalAlignment(3);
        setHorizontalTextPosition(0);
        setVerticalTextPosition(3);
        setIcon(imageicon);
    }

    private ImageIcon icon;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
