package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

public class ExtendedRadioButton extends JRadioButton{
	public ExtendedRadioButton() {
		super();

		Font font = ViewSettings.getCommonUserInputFont();
		this.setFont(font);

		// eidt s.inoue 2012/07/09
		// initActions();
		addEnterPolicy(this);
	}

//	private void initActions() {
//		ActionMap actions = getActionMap();
//		actions.put("focusOutNext", new FocusOutNextAction()); //$NON-NLS-1$
//		actions.put("focusOutPrevious", new FocusOutPreviousAction()); //$NON-NLS-1$
//
//		InputMap inputs = getInputMap(JComponent.WHEN_FOCUSED);
//
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed"); //$NON-NLS-1$
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released"); //$NON-NLS-1$
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext"); //$NON-NLS-1$
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious"); //$NON-NLS-1$
//	}
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
	public void setSize(Dimension d) {
		if (d.width == 0) {
			d.width = d.height;
		}
		super.setSize(d);
	}

}
