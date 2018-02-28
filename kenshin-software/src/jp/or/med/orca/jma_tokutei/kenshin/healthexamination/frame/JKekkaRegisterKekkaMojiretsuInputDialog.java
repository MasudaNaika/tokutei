//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Frame;
//import java.awt.event.ActionEvent;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Hashtable;
//
//import javax.swing.JDialog;
//import javax.swing.JList;
//import javax.swing.ListSelectionModel;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
//import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
//import jp.or.med.orca.jma_tokutei.common.event.JSingleDoubleClickEvent;
//import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKekkaRegisterAbstractInputDialog;
//
//import java.awt.Dimension;
//
///**
// * 総合コメント入力用ダイアログクラス
// */
//public class JKekkaRegisterKekkaMojiretsuInputDialog extends JKekkaRegisterAbstractInputDialog {
//
//	private static org.apache.log4j.Logger logger = Logger.getLogger(JKekkaRegisterKekkaMojiretsuInputDialog.class);
//
//	private static final long serialVersionUID = 1L;
//	/**
//	 * ダイアログタイトル文字列取得用キー
//	 */
//	// edit s.inoue 2009/12/21
//	// private final String TITLE_TEXT_KEY = "tokutei.kekkainput.frame.commentinput.dialog.title";
//	private final String TITLE_TEXT_KEY = "tokutei.shoken.dialog.title";
//
//	/**
//	 * 入力ガイダンス文字列取得用キー
//	 */
//	// edit s.inoue 2009/12/21
//	// private final String GUIDANCE_TEXT_KEY = "tokutei.kekkainput.frame.commentinput.dialog.guidance";
//	private final String GUIDANCE_TEXT_KEY = "tokutei.shoken.dialog.guidance";
//
//	private String[] TEIKEIBUN_TEXT =null;
//
//	private JList jListComment = null;
//	/**
//	 * コンストラクタ
//	 * @param owner
//	 */
//	public JKekkaRegisterKekkaMojiretsuInputDialog(Frame owner, String comment) {
//		super(owner);
//		String title = ViewSettings.getUsingValueString(TITLE_TEXT_KEY);
//		String guidance = ViewSettings.getUsingValueString(GUIDANCE_TEXT_KEY);
//
//		TEIKEIBUN_TEXT =getTeikeiMaster();
//
//		JList listBox = getJListComment();
//
//		initialize(title, guidance, listBox);
//		listBox.grabFocus();
//
//		// edit s.inoue 2010/06/10
//		listBox.setSelectionInterval(0, 0);
//		jEditorPane_Comment.setText(comment);
//
//		/* 可視状態になったときに、エディタに フォーカスを移動するように変更。 */
//		this.addComponentListener(new ComponentAdapter(){
//			@Override
//			public void componentShown(ComponentEvent arg0) {
//				JKekkaRegisterKekkaMojiretsuInputDialog.this.jListComment.grabFocus();
//			}
//		});
//
//		// ダブルクリックの処理
//		jListComment.addMouseListener(new JSingleDoubleClickEvent(this,jButtonSelect));
//
//	}
//
//	/**
//	 * This method initializes this
//	 *
//	 */
//	private void initialize() {
//        this.setSize(new Dimension(364, 191));
//
//	}
//
//	// add s.inoue 2009/12/11
//	/**
//	 * 定型文マスタより取得
//	 */
//	public String[] getTeikeiMaster() {
//
//		// edit s.inoue 2009/12/21 全て
//		// String query = "SELECT SYOKEN_TYPE, SYOKEN_NO, SYOKEN, UPDATE_TIMESTAMP FROM T_SYOKENMASTER WHERE SYOKEN_TYPE = ? ";
//		// String[] params = { teikeiType };
//		String query = "SELECT SYOKEN_TYPE, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP "
//		+ " FROM T_SYOKENMASTER WHERE SYOKEN_NAME <> '' ";
//
//		ArrayList<Hashtable<String, String>> result = null;
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
////		if (result != null && ! result.isEmpty())
////			return null;
//
//		String[] aryItems = new String[ result.size() ];
//
//		for (int cnt = 0;cnt<result.size();cnt++){
//			Hashtable<String, String> item = result.get(cnt);
//
//			// String teikeibunNo = item.get("TEIKEIBUN_NO");
//			// String updatetime = item.get("UPDATE_TIMESTAMP");
//			aryItems[cnt] = item.get("SYOKEN_NAME");
//		}
//
//		return aryItems;
//	}
//
//	/**
//	 * リストボックスから選択されたテキストを取得する
//	 */
//	@Override
//	public String getText() {
//		// edit s.inoue 2009/12/15
//		System.out.println((String)jEditorPane_Comment.getText());
//		return (String)jEditorPane_Comment.getText();
//		// return (String)jListComment.getSelectedValue();
//	}
//
//	/**
//	 * 選択中セルのテキストと一致するリストボックスの項目を
//	 * 選択状態に設定する
//	 * @param 選択中セルテキスト
//	 */
//	@Override
//	public void setText(String text) {
//		if (text == null) return;
//// edit s.inoue 2009/12/11
////		for (int i = 0 ; i < SOUGOU_COMMENT_TEXT.length ; i++) {
////			if (SOUGOU_COMMENT_TEXT[i].equals(text)) {
////				jListComment.setSelectedIndex(i);
////				return;
////			}
////		}
//		for (int i = 0 ; i < TEIKEIBUN_TEXT.length ; i++) {
//			if (TEIKEIBUN_TEXT[i].equals(text)) {
//				jListComment.setSelectedIndex(i);
//				return;
//			}
//		}
//	}
//
//	/**
//	 * This method initializes jListComment
//	 *
//	 * @return javax.swing.JList
//	 */
//	private JList getJListComment() {
//		if (jListComment == null) {
//			// edit s.inoue 2009/12/11
//			// jListComment = new JList(SOUGOU_COMMENT_TEXT);
//			jListComment = new JList(TEIKEIBUN_TEXT);
//
//			jListComment.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//			jListComment.setFont(ViewSettings.getCommonUserInputFont());
//			jListComment.addKeyListener(this);
//		}
//		return jListComment;
//	}
//
//	/**
//	 * リストボックスキーリスナコールバック
//	 * Enterキー押下時にOKボタン押下と同じ処理を実行する
//	 */
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		// edit s.inoue 2009/12/11
//		switch(keyEvent.getKeyCode()){
//			// del s.inoue 2009/12/22 削除する?
////			case KeyEvent.VK_ENTER:
////				ActionEvent event = new ActionEvent(jButtonOK, 0, "exit");
////				actionPerformed(event);
////				break;
//			case KeyEvent.VK_F1:
//				logger.info(jButtonCancel.getText());
//				ReturnValue = RETURN_VALUE.CANCEL;
//				setVisible(false);
//				break;
//			case KeyEvent.VK_F2:
//				logger.info(jButtonClear.getText());
//				// edit s.inoue 2010/04/27
//				this.jEditorPane_Comment.setText("");
//				break;
//			// edit s.inoue 2009/12/15
//			case KeyEvent.VK_F11:
//				logger.info(jButtonSelect.getText());
//				setCommentText();
//				break;
//			case KeyEvent.VK_F12:
//				logger.info(jButtonOK.getText());
//				ReturnValue = RETURN_VALUE.YES;
//				setVisible(false);
//				break;
//		}
//	}
//
//	// add s.inoue 2009/12/15
//	private void setCommentText(){
//		String commText = jEditorPane_Comment.getText().trim();
//
//		if(commText.length() > 0)
//			// edit s.inoue 2010/06/14
//			commText +=  JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(",");
//		commText += jListComment.getSelectedValue().toString().trim();
//		jEditorPane_Comment.setText(commText);
//	}
//
//	// move s.inoue 2009/12/15
//	/**
//	 * 戻り値を格納
//	 */
//	public void actionPerformed(ActionEvent e) {
//		// edit s.inoue 2009/12/15
//		if(e.getSource() == jButtonSelect){
//			logger.info(jButtonSelect.getText());
//			setCommentText();
//		}else if(e.getSource() == jButtonOK) {
//			logger.info(jButtonOK.getText());
//			// edit s.inoue 2009/12/06
//			// ReturnValue = RETURN_VALUE.OK;
//			ReturnValue = RETURN_VALUE.YES;
//			// モーダルダイアログの制御解除。
//			setVisible(false);
//		}
//		else if(e.getSource() == jButtonCancel) {
//			logger.info(jButtonCancel.getText());
//			ReturnValue = RETURN_VALUE.CANCEL;
//			// モーダルダイアログの制御解除。
//			setVisible(false);
//		}
//		// edit s.inoue 2010/05/11
//		else if(e.getSource() == jButtonClear){
//			logger.info(jButtonClear.getText());
//			this.jEditorPane_Comment.setText("");
//		}
//	}
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public void setCommentText(String text) {
//		// TODO 自動生成されたメソッド・スタブ
//		this.jEditorPane_Comment.setText(text);
//	}
//
//}  //  @jve:decl-index=0:visual-constraint="10,10"
////@jve:decl-index=0:
//
//// del s.inoue 2010/06/11
////package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
////
////import java.awt.Frame;
////import java.awt.event.ActionEvent;
////import java.awt.event.ComponentAdapter;
////import java.awt.event.ComponentEvent;
////import java.awt.event.MouseEvent;
////import java.awt.event.MouseListener;
////
////import javax.swing.BorderFactory;
////import javax.swing.JComponent;
////import javax.swing.JTextArea;
////import javax.swing.border.EtchedBorder;
////
////import jp.or.med.orca.jma_tokutei.common.component.ExtendedEditorPane;
////import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
////import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
////import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
////
////public class JKekkaRegisterKekkaMojiretsuInputDialog extends JKekkaRegisterAbstractInputDialog implements MouseListener{
////
////	private static final long serialVersionUID = 1L;
////	/**
////	 * ダイアログタイトル文字列取得用キー
////	 */
////	private final String TITLE_TEXT_KEY = "tokutei.kekkainput.frame.kekkainput.dialog.title";
////	/**
////	 * 入力ガイダンス文字列取得用キー
////	 */
////	private final String GUIDANCE_TEXT_KEY = "tokutei.kekkainput.frame.kekkainput.dialog.guidance";
////
////	private  ExtendedEditorPane jKekkaMojiretsuTextArea = null;
////
////	private JFocusTraversalPolicy focusTraversalPolicy = null;
////
////	/**
////	 * コンストラクタ
////	 * @param owner 健診結果データ入力画面
////	 */
////	public JKekkaRegisterKekkaMojiretsuInputDialog(Frame owner) {
////		super(owner);
////		String title = ViewSettings.getUsingValueString(TITLE_TEXT_KEY);
////		String guidance = ViewSettings.getUsingValueString(GUIDANCE_TEXT_KEY);
////		ExtendedEditorPane textArea = getJKekkaMojiretsuTextArea();
////		initialize(title, guidance, textArea);
////		textArea.grabFocus();
////
////		// edit s.inoue 2009/10/23
////		this.focusTraversalPolicy = new JFocusTraversalPolicy();
////		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
////
////		this.focusTraversalPolicy.setDefaultComponent(this.jKekkaMojiretsuTextArea);
////		this.focusTraversalPolicy.addComponent(this.jKekkaMojiretsuTextArea);
////		this.focusTraversalPolicy.addComponent(this.jButtonOK);
////		this.focusTraversalPolicy.addComponent(this.jButtonCancel);
////
////		/* Added 2008/03/14 若月
////		 * 可視状態になったときに、エディタにフォーカスを移動する。
////		 * 非可視状態になったとき、IME を OFF にする。 */
////		/* --------------------------------------------------- */
////		this.addComponentListener(new ComponentAdapter(){
////			@Override
////			public void componentShown(ComponentEvent arg0) {
////				JKekkaRegisterKekkaMojiretsuInputDialog.this.jKekkaMojiretsuTextArea.grabFocus();
////			}
////
////			@Override
////			public void componentHidden(ComponentEvent arg0) {
////				JKekkaRegisterKekkaMojiretsuInputDialog.this.jKekkaMojiretsuTextArea
////					.getInputContext().setCharacterSubsets(null);
////			}
////		});
////		/* --------------------------------------------------- */
////	}
////
////	/**
////	 * This method initializes jKekkaMojiretsuTextArea
////	 *
////	 * @return javax.swing.JTextArea
////	 */
////	private ExtendedEditorPane getJKekkaMojiretsuTextArea() {
////		if (jKekkaMojiretsuTextArea == null) {
////			jKekkaMojiretsuTextArea = new ExtendedEditorPane(ImeMode.IME_ZENKAKU);
////			jKekkaMojiretsuTextArea.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
////			// edit s.inoue 2009/11/11
////			jKekkaMojiretsuTextArea.addMouseListener(this); // マウスリスナを登録
////
////		}
////		return jKekkaMojiretsuTextArea;
////	}
////
////	/**
////	 * テキストエリアに結果(文字列)をセットする
////	 * @param kekkaMojiretsu 結果(文字列)
////	 */
////	public void setText(String text) {
////		jKekkaMojiretsuTextArea.setText(text);
////	}
////
////	/**
////	 * テキストエリアから入力された結果(文字列)を取得する
////	 * @return テキストエリア入力済 結果(文字列)
////	 */
////	public String getText() {
////		return jKekkaMojiretsuTextArea.getText();
////	}
////
////	// add s.inoue 2009/11/11
////	@Override
////	public void mousePressed(MouseEvent e) {
////		mousePopup(e);
////	}
////	@Override
////	public void mouseReleased(MouseEvent e) {
////		mousePopup(e);
////	}
////	// add s.inoue 2009/11/11
////	private void mousePopup(MouseEvent e) {
////		if (e.isPopupTrigger()) {
////			// ポップアップメニューを表示する
////			JComponent c = (JComponent)e.getSource();
////			jKekkaMojiretsuTextArea.showPopup(c, e.getX(), e.getY());
////			e.consume();
////		}
////	}
////
////	@Override
////	public void mouseClicked(MouseEvent e) {
////		// TODO 自動生成されたメソッド・スタブ
////
////	}
////
////	@Override
////	public void mouseEntered(MouseEvent e) {
////		// TODO 自動生成されたメソッド・スタブ
////
////	}
////
////	@Override
////	public void mouseExited(MouseEvent e) {
////		// TODO 自動生成されたメソッド・スタブ
////
////	}
////
////	@Override
////	public void actionPerformed(ActionEvent e) {
////		// TODO 自動生成されたメソッド・スタブ
////
////	}
////}
//////@jve:decl-index=0:
//////  @jve:decl-index=0:visual-constraint="10,10"