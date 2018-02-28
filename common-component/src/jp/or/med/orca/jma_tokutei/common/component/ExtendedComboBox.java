package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.ComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;

/**
 * JComboBox の独自拡張
 */
public class ExtendedComboBox extends JComboBox {

	private ImeController imeController = null;

	public ExtendedComboBox() {
		super();
		// eidt s.inoue 2012/07/09
		this.initActions(true);
		// addEnterPolicy(this);

		this.setFont(ViewSettings.getCommonUserInputFont());
	}

	// add s.inoue 2009/10/01
	/**
	 * コンストラクタ
	 */
	public ExtendedComboBox(String text, int n, ImeMode mode, boolean handleEnterKey) {
		super();
		setFont(ViewSettings.getCommonUserInputFont());

		// eidt s.inoue 2012/07/09
		this.initActions(handleEnterKey);
//		addEnterPolicy(this);

		this.imeController = new ImeController();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedComboBox(ComboBoxModel model) {
		super(model);
		// eidt s.inoue 2012/07/09
		this.initActions();
//		addEnterPolicy(this);
	}

	public ExtendedComboBox(Object[] objects) {
		super(objects);
		// eidt s.inoue 2012/07/09
		this.initActions();
//		addEnterPolicy(this);
	}

	public ExtendedComboBox(Vector<?> vector) {
		super(vector);
		// eidt s.inoue 2012/07/09
		this.initActions();
//		addEnterPolicy(this);
	}

	// add s.inoue 2009/10/01
	public ExtendedComboBox(ImeMode mode) {
		this("", -1, mode, true);
		// eidt s.inoue 2012/07/09
		this.initActions();
//		addEnterPolicy(this);
	}

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

// del s.inoue 2012/11/06
//		actions.put("focusOutNext", new FocusOutNextAction());
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");
//
//		actions.put("focusOutPrevious", new FocusOutPreviousAction());
//		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");

		if (handleEnterKey) {
			inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
			// add s.inoue 2012/11/06
			// Shift+Enterを追加
			inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK), "focusOutPrevious");
		}
	}

// del s.inoue 2012/11/05
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
}
