package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


public class FocusOutPreviousAction extends AbstractAction {
	public void actionPerformed(ActionEvent e) {
		Component c = (Component)e.getSource();
		c.transferFocusBackward();
	}
}