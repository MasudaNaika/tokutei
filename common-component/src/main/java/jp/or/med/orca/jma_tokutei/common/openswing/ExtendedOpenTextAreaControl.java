// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.openswing;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

import jp.or.med.orca.jma_tokutei.common.component.FocusOutNextAction;
import jp.or.med.orca.jma_tokutei.common.component.FocusOutPreviousAction;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import org.openswing.swing.client.TextAreaControl;

public class ExtendedOpenTextAreaControl extends TextAreaControl
{

    public ExtendedOpenTextAreaControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode, boolean flag)
    {
        imeController = null;
        setFont(ViewSettings.getCommonUserInputFont());
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, imemode);

        // eidt s.inoue 2013/02/14
        // addEnterPolicy(this);
        this.setAutoscrolls(true);
        initActions();
    }

    public ExtendedOpenTextAreaControl()
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedOpenTextAreaControl(boolean flag)
    {
        this("", -1, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedOpenTextAreaControl(String s, int i)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedOpenTextAreaControl(String s, int i, boolean flag)
    {
        this(s, i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedOpenTextAreaControl(String s, int i, jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this(s, i, imemode, true);
    }

    public ExtendedOpenTextAreaControl(jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode imemode)
    {
        this("", -1, imemode, true);
    }

	// edit s.inoue 2012/11/05
	private void initActions() {
		ActionMap actions = getActionMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		actions.put("focusOutPrevious", new FocusOutPreviousAction());

		InputMap inputs = this.getInputMap(JComponent.WHEN_FOCUSED);
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
		// add s.inoue 2012/11/06
		// Shift+Enterを追加
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK), "focusOutPrevious");
	}
	private void initActions(boolean handleEnterKey) {
		ActionMap actions = getActionMap();
		InputMap inputs = getInputMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");

		actions.put("focusOutPrevious", new FocusOutPreviousAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");

		if (handleEnterKey) {
			inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
			// add s.inoue 2012/11/06
			// Shift+Enterを追加
			inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK), "focusOutPrevious");
		}
	}
//	// enterキー制御
//	private void addEnterPolicy(JComponent comp) {
//		  //次へのフォーカス設定
//		  Set<AWTKeyStroke> keystrokes = new HashSet<AWTKeyStroke>();
//		  Set<AWTKeyStroke> oldKeyStrokes = comp
//		          .getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
//		  if (oldKeyStrokes != null) {
//		      //既に登録されているKeySetをがあればコピーする。
//		  //標準であればTabKeyなどが入っているはず
//		      for (AWTKeyStroke akw : oldKeyStrokes) {
//		          keystrokes.add(akw);
//		      }
//		  }
//
//		  //ENTERを追加
//		  keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
//		  comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keystrokes);
//
//		  //前へのフォーカス設定
//		  keystrokes = new HashSet<AWTKeyStroke>();
//		  oldKeyStrokes = comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
//		  if (oldKeyStrokes != null) {
//		      //既に登録されているKeySetをがあればコピーする。
//		  //標準であればShft+TabKeyなどが入っているはず
//		      for (AWTKeyStroke akw : oldKeyStrokes) {
//		          keystrokes.add(akw);
//		      }
//		  }
//
//		  // Shift+Enterを追加
//		  keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK));
//		  comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keystrokes);
//	}
    public void showPopup(JComponent jcomponent, int i, int j)
    {
        JPopupMenu jpopupmenu = new JPopupMenu();
        ActionMap actionmap = jcomponent.getActionMap();
        Action action = actionmap.get("cut-to-clipboard");
        addPopupMenu(jpopupmenu, "切り取り(X)", action, 88, KeyStroke.getKeyStroke(88, 128));
        Action action1 = actionmap.get("copy-to-clipboard");
        addPopupMenu(jpopupmenu, "コピー(C)", action1, 67, KeyStroke.getKeyStroke(67, 128));
        Action action2 = actionmap.get("paste-from-clipboard");
        addPopupMenu(jpopupmenu, "貼り付け(V)", action2, 86, KeyStroke.getKeyStroke(86, 128));
        Action action3 = actionmap.get("select-all");
        addPopupMenu(jpopupmenu, "すべて選択(A)", action3, 65, KeyStroke.getKeyStroke(65, 128));
        jpopupmenu.show(jcomponent, i, j);
    }

    protected void addPopupMenu(JPopupMenu jpopupmenu, String s, Action action, int i, KeyStroke keystroke)
    {
        if(action != null)
        {
            JMenuItem jmenuitem = jpopupmenu.add(action);
            if(s != null)
                jmenuitem.setText(s);
            if(i != 0)
                jmenuitem.setMnemonic(i);
            if(keystroke != null)
                jmenuitem.setAccelerator(keystroke);
        }
    }

    private ImeController imeController;
}
