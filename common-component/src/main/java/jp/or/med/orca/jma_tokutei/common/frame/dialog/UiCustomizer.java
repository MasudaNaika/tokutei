package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class UiCustomizer
{
    public static void setFontSize(final int fontSize)
    {
        UIDefaults defaultTable = UIManager.getLookAndFeelDefaults();
        for(Object o: defaultTable.keySet() )
        {
            if(o.toString().toLowerCase().endsWith("font") )
            {
                FontUIResource font = (FontUIResource)UIManager.get(o);
                font = new FontUIResource(font.getName(), font.getStyle(), fontSize);
                UIManager.put(o, font);
            }
        }
    }

    public static void setScrollBarWidth(final int width)
    {
        UIManager.put("ScrollBar.width", width);
    }

    public static void updateUiAll(JComponent component)
    {
        for(Component child : component.getComponents() )
        {
            if(child instanceof JComponent)
            {
                JComponent updateComp = (JComponent)child;
                updateComp.updateUI();
                updateUiAll(updateComp);
            }
        }
    }
}