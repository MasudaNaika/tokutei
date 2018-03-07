package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * JButton ÇÃì∆é©ägí£
 */
public class ExtendedToggleButton extends JToggleButton {

	public ExtendedToggleButton() {
		super();
		this.initActions();
		this.setMargin(new Insets(2, 2, 2, 2));
		this.setPreferredSize(new Dimension(75,25));
		// del s.inoue 2012/07/06
		// this.setFont(ViewSettings.getCommonUserInputFont());
	}

	// add s.inoue
	// è„ëwÉ{É^Éìóp
	public ExtendedToggleButton(String attName,String text,String tooltip,int Mnic,ImageIcon icon){
		createButton(attName,text,tooltip,Mnic,icon);
		setPreferredSize(new Dimension(100,50));
	}

	// edit 20110317
	private void createButton(String attName,String text,String tooltip,int Mnic,ImageIcon icon){
		// del s.inoue 2012/07/06
		// setFont(new Font("ÇlÇr ÉSÉVÉbÉN", Font.PLAIN, 10));
		setFont(JApplication.FONT_COMMON_BUTTON);
		setText(text);
		setToolTipText(tooltip);
		setMnemonic(Mnic);
		setVerticalAlignment(SwingConstants.BOTTOM);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setIcon(icon);
		// eidt s.inoue 2012/07/06
		setPreferredSize(new Dimension(100,50));
	}

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
}
