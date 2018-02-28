// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.*;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.apache.log4j.Logger;

public class ExtendedOpenFormattedFloatControl extends JFormattedTextField
    implements DocumentListener, FocusListener
{

    public ExtendedOpenFormattedFloatControl(int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        imeController = null;
        LengthLimitableDocument lengthlimitabledocument = new LengthLimitableDocument();
        if(i > 0)
            lengthlimitabledocument.setLimit(i);
        setDocument(lengthlimitabledocument);
        setTextProperty();
        imeProperty(imemode);
        addFocusListener(this);
    }

    public ExtendedOpenFormattedFloatControl()
    {
        imeController = null;
        addFocusListener(this);
        setTextProperty();
        initActions(true);
    }

    public ExtendedOpenFormattedFloatControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        imeController = null;
        imeProperty(imemode);
        initActions(true);
    }

    private void setTextProperty()
    {
        setFont(ViewSettings.getCommonUserInputFont());
    }

    private void imeProperty(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
    }

    private void setFormattedString(String s)
    {
        try
        {
            if(s.indexOf("0") > 0)
                s.replace("0", "#");
            s = "###.#";
            MaskFormatter maskformatter = new MaskFormatter(s);
            maskformatter.setValidCharacters("0123456789");
            setFormatter(maskformatter);
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage());
        }
    }

    private void initActions(boolean flag)
    {
        ActionMap actionmap = getActionMap();
        InputMap inputmap = getInputMap();
        actionmap.put("focusOutNext", new FocusOutNextAction());
        actionmap.put("focusOutPrevious", new FocusOutPreviousAction());
        inputmap.put(KeyStroke.getKeyStroke(10, 0), "focusOutNext");
        inputmap.put(KeyStroke.getKeyStroke(10, 1), "focusOutPrevious");
    }

    public void changedUpdate(DocumentEvent documentevent)
    {
    }

    public void insertUpdate(DocumentEvent documentevent)
    {
    }

    public void removeUpdate(DocumentEvent documentevent)
    {
    }

    public void focusGained(FocusEvent focusevent)
    {
        setBackground(JApplication.backColor_Focus);
    }

    public void focusLost(FocusEvent focusevent)
    {
        setBackground(JApplication.backColor_UnFocus);
// del s.inoue 2013/03/09 Linuxでカーソルが残る
        // add s.inoue 2012/11/26
		String osname = System.getProperty("os.name");
		if(osname.indexOf("Windows")>=0){
	        getInputContext().setCompositionEnabled(false);
	        getInputContext().setCharacterSubsets(null);
		}
    }

    private static final String FORMAT_TEXT_ARROWED = "0123456789";
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/openswing/ExtendedOpenFormattedFloatControl");
    private ImeController imeController;

}
