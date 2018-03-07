package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JButton の独自拡張
 */
public class ExtendedButton extends JButton implements KeyListener {

	private ImageIcon icon = null;

	public ExtendedButton() {
		super();
		// eidt s.inoue 2012/11/26
		this.initActions();
		// addEnterPolicy(this);
		addKeyListener(this);
		// eidt s.inoue 2012/07/06
		// this.setFont(ViewSettings.getCommonUserInputFont());
	}

	// openswing s.inoue 2011/02/16
	// backup向け初期化
	public ExtendedButton(String text, String tooltip, int Mnic, ImageIcon icon) {
		// del s.inoue 2012/12/03
		// super();
		// eidt s.inoue 2012/11/26
		initActions();
		// addEnterPolicy(this);

		addKeyListener(this);
		createButton(text, tooltip, Mnic, icon);
		setPreferredSize(new Dimension(100, 50));
	}

// del s.inoue 2012/11/26
//	public ExtendedButton(String text, String tooltip, int Mnic,
//			ImageIcon icon, Dimension dm) {
//		super();
//		// eidt s.inoue 2012/07/09
//		// initActions();
//		addEnterPolicy(this);
//		addKeyListener(this);
//		createButton(text, tooltip, Mnic, icon);
//		setPreferredSize(dm);
//	}

	// add s.inoue 2012/11/26
	// メニュー系のボタン
	public ExtendedButton(String text, ImageIcon defaultIcon) {
		// del s.inoue 2012/12/03
		// this();
		this.setIcon(defaultIcon);
		this.setText(text);
		// eidt s.inoue 2012/11/26
		initActions();
		// addEnterPolicy(this);

		// del s.inoue 2012/12/03
		// addKeyListener(this);

		// eidt s.inoue 2011/04/08
		this.setPreferredSize(new Dimension(200, 300));
	}

	private void createButton(String text, String tooltip, int Mnic,
			ImageIcon icon) {
		// eidt s.inoue 2012/07/06
		// setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		setFont(JApplication.FONT_COMMON_BUTTON);
		setText(text);
		setToolTipText(tooltip);
		setMnemonic(Mnic);
		setVerticalAlignment(SwingConstants.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setIcon(icon);
	}

	public ExtendedButton(String text) {
		// del s.inoue 2012/12/03
		// this();
		this.setText(text);
		addKeyListener(this);
		// eidt s.inoue 2012/12/03
		// addEnterPolicy(this);
		initActions();
	}

	// eidt s.inoue 2012/11/26	2012/07/09修正分を再度戻す
	private void initActions() {
		ActionMap actions = getActionMap();

		actions.put("focusOutNext", new FocusOutNextAction()); //$NON-NLS-1$
		actions.put("focusOutPrevious", new FocusOutPreviousAction()); //$NON-NLS-1$

		InputMap inputs = this.getInputMap(JComponent.WHEN_FOCUSED);
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext"); //$NON-NLS-1$
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious"); //$NON-NLS-1$

		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed"); //$NON-NLS-1$
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released"); //$NON-NLS-1$
	}

//	// enterキー制御
//	private void addEnterPolicy(JComponent comp) {
//		// 次へのフォーカス設定
//		Set<AWTKeyStroke> keystrokes = new HashSet<AWTKeyStroke>();
//		Set<AWTKeyStroke> oldKeyStrokes = comp
//				.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
//		if (oldKeyStrokes != null) {
//			// 既に登録されているKeySetをがあればコピーする。
//			// 標準であればTabKeyなどが入っているはず
//			for (AWTKeyStroke akw : oldKeyStrokes) {
//				keystrokes.add(akw);
//			}
//		}
//
//		// ENTERを追加
//		keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
//		comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
//				keystrokes);
//
//		// 前へのフォーカス設定
//		keystrokes = new HashSet<AWTKeyStroke>();
//		oldKeyStrokes = comp
//				.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
//		if (oldKeyStrokes != null) {
//			// 既に登録されているKeySetをがあればコピーする。
//			// 標準であればShft+TabKeyなどが入っているはず
//			for (AWTKeyStroke akw : oldKeyStrokes) {
//				keystrokes.add(akw);
//			}
//		}
//
//		// Shift+Enterを追加
//		keystrokes.add(KeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER,
//				InputEvent.SHIFT_MASK));
//		comp.setFocusTraversalKeys(
//				KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keystrokes);
//	}

	// add s.inoue
	// 上層ボタン用
	public ExtendedButton(String attName, String text, String tooltip,
			int Mnic, ImageIcon icon) {
		createButton(attName, text, tooltip, Mnic, icon);
		setPreferredSize(new Dimension(100, 50));
		// eidt s.inoue 2012/12/13
		// ２重メッセージ問題
		// addEnterPolicy(this);
		addKeyListener(this);
	}

// del s.inoue 2012/11/26
//	// メニューボタン用
//	public ExtendedButton(String attName, String text, String tooltip,
//			int Mnic, ImageIcon icon, Dimension dm) {
//		createButton(attName, text, tooltip, Mnic, icon);
//		setPreferredSize(dm);
//		// addEnterPolicy(this);
//		addKeyListener(this);
//	}

	//
	private void createButton(String attName, String text, String tooltip,
			int Mnic, ImageIcon icon) {
		// eidt s.inoue 2012/07/06
		// setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 10));
		setFont(JApplication.FONT_COMMON_BUTTON);

		setText(text);
		setToolTipText(tooltip);
		setMnemonic(Mnic);
		setVerticalAlignment(SwingConstants.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setIcon(icon);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
// del s.inoue 2012/12/13
// 二重メッセージの原因
		// add s.inoue 2012/07/10
		if (KeyEvent.VK_ENTER != e.getKeyCode()) return;
		int mod = e.getModifiersEx();
		  if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
		  }else{
			  doClick();
		  }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
