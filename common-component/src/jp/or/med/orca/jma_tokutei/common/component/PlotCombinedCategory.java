// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.component;

import java.util.Iterator;
import java.util.List;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.data.Range;

/**
 * グラフ用プロットクラス
 */
public class PlotCombinedCategory extends CombinedDomainCategoryPlot
{

    public PlotCombinedCategory(CategoryAxis categoryaxis, ValueAxis valueaxis)
    {
        super(categoryaxis);
        super.setGap(10D);
        super.setRangeAxis(valueaxis);
    }

    public void add(CategoryPlot categoryplot)
    {
        add(categoryplot, 1);
    }

    public void add(CategoryPlot categoryplot, int i)
    {
        super.add(categoryplot, i);
        ValueAxis valueaxis = super.getRangeAxis();
        categoryplot.setRangeAxis(0, valueaxis, false);
        super.setRangeAxis(valueaxis);
        if(null == valueaxis)
        {
            return;
        } else
        {
            valueaxis.configure();
            return;
        }
    }

    public Range getDataRange(ValueAxis valueaxis)
    {
        Range range = null;
        for(Iterator iterator = getSubplots().iterator(); iterator.hasNext();)
        {
            CategoryPlot categoryplot = (CategoryPlot)iterator.next();
            range = Range.combine(range, categoryplot.getDataRange(valueaxis));
        }

        return range;
    }

    public void setRangeAxis(ValueAxis valueaxis)
    {
        CategoryPlot categoryplot;
        for(Iterator iterator = getSubplots().iterator(); iterator.hasNext(); categoryplot.setRangeAxis(0, valueaxis, false))
            categoryplot = (CategoryPlot)iterator.next();

        super.setRangeAxis(valueaxis);
        if(null == valueaxis)
        {
            return;
        } else
        {
            valueaxis.configure();
            return;
        }
    }
}
