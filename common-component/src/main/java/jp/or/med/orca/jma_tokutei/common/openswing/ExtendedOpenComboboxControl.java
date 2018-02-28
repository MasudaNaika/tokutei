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
import java.util.*;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.openswing.swing.client.ComboBoxControl;

public class ExtendedOpenComboboxControl extends ComboBoxControl
    implements FocusListener
{

    public ExtendedOpenComboboxControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        imeController = null;
        setFont(ViewSettings.getCommonUserInputFont());
        addEnterPolicy(this);
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);
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
    }

    public void focusLost(FocusEvent focusevent)
    {
    }

    private ImeController imeController;
}
