package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AbstractDocument;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettingsKey;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import org.apache.log4j.Logger;

/**
 * HTTP通信(ログイン時のバージョン取得)
 */
public class DngPreviewHtml extends JDialog implements ActionListener,KeyListener,IDialog{

  protected Component html;
  protected RETURN_VALUE ReturnValue;
  protected ExtendedButton jButtonOK = null;

  private static org.apache.log4j.Logger logger = Logger.getLogger(DngPreviewHtml.class);
  private JLabel jLabelTitle = null;

  public DngPreviewHtml(Frame owner) {
	super(owner);
    setLayout(new BorderLayout());
    initComponents();
    setLayout(owner,PropertyUtil.getProperty("infomation.url"));
  }

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jButtonOK) {
			ReturnValue = RETURN_VALUE.YES;
		}
		setVisible(false);
	}

	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			ReturnValue = RETURN_VALUE.YES;
		}
		setVisible(false);
	}

  public void setLayout(Frame owner,String uri){

	Container contentPane = this.getContentPane();

	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

	// eidt s.inoue 2011/04/08
//	final ExtendedButton jButtonOK = new ExtendedButton("閉じる(F1)");
//	jButtonOK.setFont(new Font("Dialog",Font.PLAIN,12));
//	jButtonOK.addActionListener(this);
//	jButtonOK.addKeyListener(this);
	ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
	ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);
	jButtonOK= new ExtendedButton(
			"End","戻る(R)","戻る(ALT+R)",KeyEvent.VK_R,icon);
	jButtonOK.addActionListener(this);

	jButtonOK.setVerticalAlignment(SwingConstants.CENTER);
	jButtonOK.setHorizontalAlignment(SwingConstants.CENTER);
//	jButtonOK.setMnemonic(KeyEvent.VK_F1);


	try {
		setURI(new URL(uri));
	} catch (MalformedURLException ex) {
		logger.error(ex.getMessage());
		JErrorMessage.show("M9400", null);
	}

	contentPane.add(jButtonOK);
	// edit s.inoue 2010/11/05
	contentPane.addKeyListener(this);

	this.setSize(655,400);
	initializeFrameTitle();

	Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension sz = this.getSize();
	this.setLocation((sc.width-sz.width)/2,(sc.height-sz.height)/2);
	this.setVisible(true);
  }

  // add s.inoue 2010/08/02
	private void initializeFrameTitle() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(ViewSettings.getTokuteFrameTitle());
		buffer.append(" (Version ");
		buffer.append(JApplication.versionNumber);
		buffer.append(")");

		String title = buffer.toString();
		this.setTitle(title);
	}


  // 初期化
  protected void initComponents() {
    JEditorPane editorPane = new JEditorPane();
    AbstractDocument doc = (AbstractDocument)editorPane.getDocument();
    doc.setAsynchronousLoadPriority(1);
    editorPane.setEditable(false);
    editorPane.addHyperlinkListener(new HyperlinkListener() {
      public void hyperlinkUpdate(HyperlinkEvent event) {
        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
          setURI(event.getURL());
        }
      }
    });
    html = editorPane;
    JScrollPane scroll = new JScrollPane();
    scroll.setViewportView(editorPane);
    add(scroll, BorderLayout.CENTER);
  }

  // URL設定
  public void setURI(URL uri) {
    Runnable loader = createLoader(uri);
    SwingUtilities.invokeLater(loader);
  }

  protected Runnable createLoader(final URL uri) {
    return new Runnable() {
      public void run() {
        try {
          assert html instanceof JEditorPane;
          JEditorPane editorPane = (JEditorPane)html;
          editorPane.setPage(uri);
        } catch (IOException e) {
        }
      }
    };
  }

@Override
public String getFilePath() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public String getKenshinDate() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public Integer getPrintSelect() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public RETURN_VALUE getStatus() {
	return ReturnValue;
}

@Override
public void setDialogSelect(boolean enabled) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void setDialogTitle(String title) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void setMessageTitle(String title) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void setSaveFileName(String title) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void setShowCancelButton(boolean isShowCancel) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void setText(String text) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void keyReleased(KeyEvent e) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public void keyTyped(KeyEvent e) {
	// TODO 自動生成されたメソッド・スタブ

}

@Override
public String getK_PNo() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public String getK_PName() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public String getK_PNote() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}

@Override
public Vector getDataSelect() {
	// TODO 自動生成されたメソッド・スタブ
	return null;
}
}
