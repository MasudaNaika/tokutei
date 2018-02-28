// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.event.ActionEvent;
import org.openswing.swing.client.NavigatorBar;

public interface NavigatorBarController
{

    public abstract int getTotalResultSetLength();

    public abstract int getBlockSize();

    public abstract void loadPage(int i);

    public abstract void firstRow(NavigatorBar navigatorbar);

    public abstract void nextRow(NavigatorBar navigatorbar, ActionEvent actionevent);

    public abstract void previousPage(NavigatorBar navigatorbar);

    public abstract void nextPage(NavigatorBar navigatorbar);

    public abstract void previousRow(NavigatorBar navigatorbar, ActionEvent actionevent);

    public abstract void lastRow(NavigatorBar navigatorbar);
}
