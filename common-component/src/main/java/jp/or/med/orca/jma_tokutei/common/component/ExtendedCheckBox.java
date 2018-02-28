package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JComboBox の独自拡張
 */
public class ExtendedCheckBox extends JCheckBox {

	public ExtendedCheckBox() {
		super();
		this.initActions();

		this.setFont(ViewSettings.getCommonUserInputFont());
	}

	private void initActions() {
		ActionMap actions = getActionMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		actions.put("focusOutPrevious", new FocusOutPreviousAction());

		InputMap inputs = this.getInputMap(JComponent.WHEN_FOCUSED);
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed");
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released");
	}
}
