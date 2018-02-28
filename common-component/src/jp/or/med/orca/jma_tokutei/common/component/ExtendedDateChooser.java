package jp.or.med.orca.jma_tokutei.common.component;

import com.toedter.calendar.JDateChooser;
import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

import org.apache.log4j.Logger;

/**
 * カレンダーコントロールクラス
 */
public class ExtendedDateChooser extends JDateChooser
    implements FocusListener,KeyListener
{
    private ImeController imeController;
    private static Logger logger = Logger.getLogger("jp/or/med/orca/jma_tokutei/common/component/ExtendedDateChooser");

    public ExtendedDateChooser(String s, int i, ImeController.ImeMode imemode, boolean flag)
    {
        // imeController = null;
        setDateFormatString("yyyyMMdd");
        setToolTipText("yyyyMMdd形式で入力");
        setFont(ViewSettings.getCommonUserInputFont());
        imeController = new ImeController();
        imeController.addFocusListenerForCharcterSubsets(this, ImeMode.IME_OFF);
        addEnterPolicy(this);
        addFocusListener(this);
//        setInputVerifier(new IntegerInputVerifier());
    }

//    // add s.inoue 2012/11/27
//    class IntegerInputVerifier extends InputVerifier{
//    	  @Override public boolean verify(JComponent c) {
//    	    boolean verified = false;
//    	    JTextField textField = (JTextField)c;
//    	    try{
//    	      Integer.parseInt(textField.getText());
//
//    	      if (textField.getText().length() > 8)
//    	    	  throw new NullPointerException();
//
//    	      verified = true;
//    	    }catch(NumberFormatException e) {
//    	      UIManager.getLookAndFeel().provideErrorFeedback(c);
//    	      //Toolkit.getDefaultToolkit().beep();
//    	      System.out.println("aaaaaaaaaaaaaaaaaa");
//    	    }
//    	    return verified;
//    	  }
//    	}

    public ExtendedDateChooser()
    {
        imeController = null;
    }

    public ExtendedDateChooser(boolean flag)
    {
        this("", -1, ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedDateChooser(String s, int i)
    {
        this(s, i, ImeController.ImeMode.IME_NO_CONTROLL, true);
    }

    public ExtendedDateChooser(String s, int i, boolean flag)
    {
        this(s, i, ImeController.ImeMode.IME_NO_CONTROLL, flag);
    }

    public ExtendedDateChooser(String s, int i, ImeController.ImeMode imemode)
    {
        this(s, i, imemode, true);
    }

    public ExtendedDateChooser(ImeController.ImeMode imemode)
    {
        this("", -1, imemode, true);
    }

    public Date getTextToDate(String s)
        throws ParseException
    {
        if(s.equals(""))
            return null;
        if(s.indexOf("/") > 0)
            s = s.replaceAll("/", "");
        int i = Integer.parseInt(s.substring(0, 4));
        int j = Integer.parseInt(s.substring(5, 6));
        int k = Integer.parseInt(s.substring(7, 8));
        Calendar calendar = Calendar.getInstance();
        Calendar _tmp = calendar;
        calendar.set(2, k);
        Calendar _tmp1 = calendar;
        calendar.set(5, j);
        Calendar _tmp2 = calendar;
        calendar.set(1, i);
        SimpleDateFormat simpledateformat = new SimpleDateFormat();
        simpledateformat.setLenient(true);
        simpledateformat.applyPattern("yyyyMMdd");
        Date date = null;
        date = simpledateformat.parse(s);
        return date;
    }

    public void setDate(Date date)
    {
        super.setDate(date);
    }

    public String getDateText()
    {
        String s = null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        if(getDate() == null)
            return "";
        try
        {
            s = simpledateformat.format(getDate());
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage());
        }
        return s;
    }

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

    public void focusGained(FocusEvent focusevent)
    {
        ((JTextField)focusevent.getSource()).setBackground(JApplication.backColor_Focus);
    }

    public void focusLost(FocusEvent focusevent)
    {
        ((JTextField)focusevent.getSource()).setBackground(JApplication.backColor_UnFocus);
// del s.inoue 2013/03/09 Linuxでカーソルが残る
        // add s.inoue 2012/11/26
     // add s.inoue 2013/03/09
		String osname = System.getProperty("os.name");
		if(osname.indexOf("Windows")>=0){
	        getInputContext().setCompositionEnabled(false);
	        getInputContext().setCharacterSubsets(null);
		}
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
//		System.out.println(e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
//		System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
//		System.out.println(e.getKeyCode());
	}
}
