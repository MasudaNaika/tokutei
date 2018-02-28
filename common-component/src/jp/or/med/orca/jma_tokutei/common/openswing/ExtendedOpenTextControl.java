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
import java.awt.im.InputSubset;
import java.util.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.openswing.swing.client.TextControl;

public class ExtendedOpenTextControl extends TextControl
    implements FocusListener, DocumentListener
{

//	// imeモード解除
//	public void setImeModeClear(){
//	    imeController.addFocusListenerForCharcterSubsets(this, ImeMode.IME_OFF);
//        addEnterPolicy(this);
//        addFocusListener(this);
//	}

	/* addEnterPolicyあり */
    public ExtendedOpenTextControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode, boolean flag)
    {
        imeController = null;
        setFont(ViewSettings.getCommonUserInputFont());
        if(i > 0)
            setMaxCharacters(i);
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
        addEnterPolicy(this);
        addFocusListener(this);
    }
    // add s.inoue 2012/07/10
    /* addEnterPolicyなし */
    public ExtendedOpenTextControl(int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        imeController = null;
        setFont(ViewSettings.getCommonUserInputFont());
        if(i > 0)
            setMaxCharacters(i);
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
        // add s.inoue 2012/11/26
        addEnterPolicy(this);
        addFocusListener(this);
    }

    public ExtendedOpenTextControl()
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
        addFocusListener(this);
    }

    public ExtendedOpenTextControl(boolean flag)
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
        addFocusListener(this);
    }

    public ExtendedOpenTextControl(String s, int i)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
        addFocusListener(this);
    }

    public ExtendedOpenTextControl(String s, int i, boolean flag)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
        addFocusListener(this);
    }

    public ExtendedOpenTextControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this(s, i, imemode, true);
        addFocusListener(this);
    }

    public ExtendedOpenTextControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this("", -1, imemode, true);
        addFocusListener(this);
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
// del s.inoue 2013/03/09 Linux版でカーソルが残る
//        // add s.inoue 2012/11/26
//        getInputContext().setCompositionEnabled(false);
//        getInputContext().setCharacterSubsets(null);
        // add s.inoue 2012/11/26
        String osname = System.getProperty("os.name");
        if(osname.indexOf("Windows")>=0){
            getInputContext().setCompositionEnabled(false);
            getInputContext().setCharacterSubsets(null);
        }
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

    private ImeController imeController;
}
