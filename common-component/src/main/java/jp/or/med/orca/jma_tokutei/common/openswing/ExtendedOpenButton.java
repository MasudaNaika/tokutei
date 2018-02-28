//package jp.or.med.orca.jma_tokutei.common.openswing;
//
//import java.awt.Dimension;
//import java.awt.Insets;
//import java.awt.event.KeyEvent;
//
//import javax.swing.ActionMap;
//import javax.swing.ImageIcon;
//import javax.swing.InputMap;
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.KeyStroke;
//
//import org.openswing.swing.client.GenericButton;
//
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//
///**
// * JButton の独自拡張
// */
//public class ExtendedOpenButton extends GenericButton {
//
//	public ExtendedOpenButton() {
//		super();
////		this.initActions();
//		this.setMargin(new Insets(2, 2, 2, 2));
//
//		// edit s.inoue 2009/11/09
//		// this.setPreferredSize(new Dimension(75,25));
//		// this.setPreferredSize(new Dimension(100,25));
//		this.setFont(ViewSettings.getCommonUserInputFont());
//	}
//
//	public ExtendedOpenButton(ImageIcon defaultIcon) {
//		this.setIcon(defaultIcon);
//	}
//	public ExtendedOpenButton(String text) {
//		this();
//		this.setText(text);
//	}
//
////	private void initActions() {
////		ActionMap actions = getActionMap();
////
////		actions.put("focusOutNext", new FocusOutNextAction()); //$NON-NLS-1$
////		actions.put("focusOutPrevious", new FocusOutPreviousAction()); //$NON-NLS-1$
////
////		InputMap inputs = this.getInputMap(JComponent.WHEN_FOCUSED);
////		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext"); //$NON-NLS-1$
////		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious"); //$NON-NLS-1$
////
////		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "pressed"); //$NON-NLS-1$
////		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "released"); //$NON-NLS-1$
////	}
//}
