package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JTextField の独自拡張
 *
 *  Modified 2008/03/12 若月
 *  IME 制御機能をExtendedEditorPane クラスと共有するため、ImeController クラスに移動。
 */
public class ExtendedTextArea extends JTextArea {

	private ImeController imeController = null;

	/**
	 * コンストラクタ
	 */
	public ExtendedTextArea(String text, int n, ImeMode mode, boolean handleEnterKey) {

		// edit s.inoue 2013/02/19
		// 6列10行
		super(6,10);
		setFont(ViewSettings.getCommonUserInputFont());

		LengthLimitableDocument doc = new LengthLimitableDocument();
		if (n > 0) {
			doc.setLimit(n);
		}
		this.setDocument(doc);

		this.setText(text);

		// add s.inoue 2013/02/14
		this.setAutoscrolls(true);
		this.setLineWrap(true);

		// eidt s.inoue 2012/11/05
		initActions(handleEnterKey);
		// addEnterPolicy(this);

		this.imeController = new ImeController();
		this.imeController.addFocusListenerForCharcterSubsets(this, mode);
	}

	public ExtendedTextArea() {
		this("", -1, ImeMode.IME_NO_CONTROLL, true);
//		addEnterPolicy(this);
		initActions();
	}

	public ExtendedTextArea(boolean handleEnterKey) {
		this("", -1, ImeMode.IME_NO_CONTROLL, handleEnterKey);
//		addEnterPolicy(this);
		initActions();
	}

	public ExtendedTextArea(String text, int n) {
		this(text, n, ImeMode.IME_NO_CONTROLL, true);
//		addEnterPolicy(this);
		initActions();
	}

	public ExtendedTextArea(String text, int n, boolean handleEnterKey) {
		this(text, n, ImeMode.IME_NO_CONTROLL, handleEnterKey);
//		addEnterPolicy(this);
		initActions();
	}

	public ExtendedTextArea(String text, int n, ImeMode mode) {
		this(text, n, mode, true);
//		addEnterPolicy(this);
		initActions();
	}

	public ExtendedTextArea(ImeMode mode) {
		this("", -1, mode, true);
//		addEnterPolicy(this);
		initActions();
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

	/**
	 * エディタのプルダウンメニュー設定
	 */
	public void showPopup(JComponent c, int x, int y) {
		JPopupMenu menu = new JPopupMenu();

		ActionMap am = c.getActionMap();

		Action cut = am.get(DefaultEditorKit.cutAction);
		addPopupMenu(menu, "切り取り(X)", cut, 'X', KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

		Action copy = am.get(DefaultEditorKit.copyAction);
		addPopupMenu(menu, "コピー(C)", copy, 'C', KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

		Action paste = am.get(DefaultEditorKit.pasteAction);
		addPopupMenu(menu, "貼り付け(V)", paste, 'V', KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

		Action all = am.get(DefaultEditorKit.selectAllAction);
		addPopupMenu(menu, "すべて選択(A)", all, 'A', KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

		menu.show(c, x, y);
	}

	// add s.inoue 2009/12/07
	// プルダウンメニュー項目を追加
	protected void addPopupMenu(JPopupMenu pmenu, String text, Action action, int mnemonic, KeyStroke ks) {
		if (action != null) {
			JMenuItem mi = pmenu.add(action);
			if (text != null) {
				mi.setText(text);
			}
			if (mnemonic != 0) {
				mi.setMnemonic(mnemonic);
			}
			if (ks != null) {
				mi.setAccelerator(ks);
			}
		}
	}
	public void setLimit(int limit) {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		doc.setLimit(limit);
	}

	public int getLimit() {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		return doc.getLimit();
	}
}
