// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 

package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.*;
import javax.swing.border.EmptyBorder;

class DotBorder extends EmptyBorder
{

    public DotBorder(int i, int j, int k, int l)
    {
        super(i, j, k, l);
        isLastCell = false;
    }

    public void setLastCellFlag(boolean flag)
    {
        isLastCell = flag;
    }

    public boolean isBorderOpaque()
    {
        return true;
    }

    public void paintBorder(Component component, Graphics g, int i, int j, int k, int l)
    {
        Graphics2D graphics2d = (Graphics2D)g;
        graphics2d.translate(i, j);
        graphics2d.setPaint(dotColor);
        graphics2d.setStroke(dashed);
        int i1 = component.getBounds().x;
        if(i1 == 0)
            graphics2d.drawLine(0, 0, 0, l);
        if(isLastCell)
            graphics2d.drawLine(k - 1, 0, k - 1, l);
        if(i1 % 2 == 0)
        {
            graphics2d.drawLine(0, 0, k, 0);
            graphics2d.drawLine(0, l - 1, k, l - 1);
        } else
        {
            graphics2d.drawLine(1, 0, k, 0);
            graphics2d.drawLine(1, l - 1, k, l - 1);
        }
        graphics2d.translate(-i, -j);
    }

    private static final BasicStroke dashed = new BasicStroke(1.0F, 0, 0, 10F, new float[] {
        1.0F
    }, 0.0F);
    private static final Color dotColor = new Color(200, 150, 150);
    private boolean isLastCell;

}
