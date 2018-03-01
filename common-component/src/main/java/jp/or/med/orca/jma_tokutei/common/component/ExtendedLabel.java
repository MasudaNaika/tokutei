package jp.or.med.orca.jma_tokutei.common.component;

import javax.swing.JLabel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class ExtendedLabel extends JLabel
{

    public ExtendedLabel()
    {
        java.awt.Font font = ViewSettings.getCommonUserInputFont();
        setFont(font);
        setFontStyle(-1);
        setFocusable(false);
    }

    public ExtendedLabel(String s, int i)
    {
        this(s);
        setFontStyle(i);
    }

    public ExtendedLabel(int i)
    {
        setFontStyle(i);
    }

    private void setFontStyle(int i)
    {
        java.awt.Font font = ViewSettings.getCommonUserInputFont(i);
        setFont(font);
    }

    public ExtendedLabel(String s)
    {
        this();
        if(s != null && !s.isEmpty())
            setTextFromKey(s);
    }

    public void setHtmlText(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("<html>");
        stringbuffer.append(s);
        stringbuffer.append("</html>");
        super.setText(stringbuffer.toString());
    }

    public void setText(String s)
    {
        super.setText(s);
        super.setToolTipText(s);
    }

    public String getText()
    {
        return super.getText();
    }

    public void setTextFromKey(String s)
    {
        String s1 = ViewSettings.getUsingValueString(s);
        if(s1 == null)
            s1 = "";
        setHtmlText(s1);
    }
}
