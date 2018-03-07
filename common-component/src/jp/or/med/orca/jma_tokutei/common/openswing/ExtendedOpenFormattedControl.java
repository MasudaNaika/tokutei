// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import org.apache.log4j.Logger;
import org.openswing.swing.client.FormattedTextControl;

public class ExtendedOpenFormattedControl extends FormattedTextControl
    implements FocusListener
{

    public ExtendedOpenFormattedControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode, boolean flag)
    {
        mask = null;
        imeController = null;
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
        addEnterPolicy(this);
        addFocusListener(this);
    }

    public ExtendedOpenFormattedControl()
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedOpenFormattedControl(boolean flag)
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedOpenFormattedControl(String s, int i)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedOpenFormattedControl(String s, int i, boolean flag)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedOpenFormattedControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this(s, i, imemode, true);
    }

    public ExtendedOpenFormattedControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this("", -1, imemode, true);
    }

    public void setTELFormat()
    {
        try
        {
            mask = new MaskFormatter("###-###-###");
            mask.setValidCharacters("0123456789");
        }
        catch(ParseException parseexception)
        {
            logger.error(parseexception.getMessage());
        }
        setFormatter(mask);
    }

    public void setPostCodeFormat()
    {
        try
        {
            mask = new MaskFormatter("###-####");
            mask.setValidCharacters("0123456789");
        }
        catch(ParseException parseexception)
        {
            logger.error(parseexception.getMessage());
        }
        setFormatter(mask);
    }

    public void setPostCodeFormatText(String s)
    {
        try
        {
            String s1 = String.format("%3s-%4s", new Object[] {
                s.substring(0, 3), s.substring(3, 7)
            });
            setText(s1);
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage());
        }
    }

    public final String getTextTrim()
    {
        return getText().replaceAll("-", "");
    }

	// enterキー制御
	private void addEnterPolicy(JComponent comp) {
		  //次へのフォーカス設定
		  Set<AWTKeyStroke> keystrokes = new HashSet<AWTKeyStroke>();
		  Set<AWTKeyStroke> oldKeyStrokes = comp
		          .getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
		  if (oldKeyStrokes != null) {
		      //既に登録されているKeySetをがあればコピーする。
		  //標準であればTabKeyなどが入っているはず
		      for (AWTKeyStroke akw : oldKeyStrokes) {
		          keystrokes.add(akw);
		      }
		  }

		  //ENTERを追加
		  keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
		  comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keystrokes);

		  //前へのフォーカス設定
		  keystrokes = new HashSet<AWTKeyStroke>();
		  oldKeyStrokes = comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
		  if (oldKeyStrokes != null) {
		      //既に登録されているKeySetをがあればコピーする。
		  //標準であればShft+TabKeyなどが入っているはず
		      for (AWTKeyStroke akw : oldKeyStrokes) {
		          keystrokes.add(akw);
		      }
		  }

		  // Shift+Enterを追加
		  keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK));
		  comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keystrokes);
	}

    public void focusGained(FocusEvent focusevent)
    {
        ((JTextField)focusevent.getSource()).setBackground(JApplication.backColor_Focus);
    }

    public void focusLost(FocusEvent focusevent)
    {
        ((JTextField)focusevent.getSource()).setBackground(JApplication.backColor_UnFocus);
// del s.inoue 2013/03/09 Linuxでカーソルが残る
        // add s.inoue 2012/11/26
		String osname = System.getProperty("os.name");
		if(osname.indexOf("Windows")>=0){
			getInputContext().setCompositionEnabled(false);
			getInputContext().setCharacterSubsets(null);
		}
    }

    private static final String FORMAT_TEL_STRING = "###-###-###";
    private static final String FORMAT_POST_STRING = "###-####";
    private static final String FORMAT_TEXT_ARROWED = "0123456789";
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/openswing/ExtendedOpenFormattedControl");
    private MaskFormatter mask;
    private ImeController imeController;

}
