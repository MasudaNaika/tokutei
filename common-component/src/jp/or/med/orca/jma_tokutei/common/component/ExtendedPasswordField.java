package jp.or.med.orca.jma_tokutei.common.component;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;

public class ExtendedPasswordField extends JPasswordField implements FocusListener {
	public ExtendedPasswordField() {
		super();
		setDocument(new LengthLimitableDocument());
		initActions();
	}

	public ExtendedPasswordField(String text, int n) {
		super();
		LengthLimitableDocument doc = new LengthLimitableDocument();
		doc.setLimit(n);
		setDocument(doc);
		setText(text);
		setColumns(n);
		initActions();
	}

	private void initActions() {
		ActionMap actions = getActionMap();
		InputMap inputs = getInputMap();

		actions.put("focusOutNext", new FocusOutNextAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusOutNext");

		actions.put("focusOutPrevious", new FocusOutPreviousAction());
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "focusOutPrevious");

		// edit s.inoue 2009/12/06
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "focusOutNext");
	}

	public void setLimit(int limit) {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		doc.setLimit(limit);
	}

	public int getLimit() {
		LengthLimitableDocument doc = (LengthLimitableDocument)getDocument();
		return doc.getLimit();
	}
	@Override
    public void focusGained(FocusEvent focusevent)
    {
        setBackground(JApplication.backColor_Focus);
    }
	@Override
    public void focusLost(FocusEvent focusevent)
    {
        setBackground(JApplication.backColor_UnFocus);
// del s.inoue 2013/03/09 Linuxでカーソルが残る
        // add s.inoue 2012/11/26
		String osname = System.getProperty("os.name");
		if(osname.indexOf("Windows")>=0){
	        getInputContext().setCompositionEnabled(false);
	        getInputContext().setCharacterSubsets(null);
		}
    }
}
