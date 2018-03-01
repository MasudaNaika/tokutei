package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

/**
 * 共通ダイアログクラス
 *
 */
public class JSelectCommentInputDialog extends JSelectAbstractInputDialog implements ItemListener {

	private static final long serialVersionUID = 1L;
	/**
	 * ダイアログタイトル文字列取得用キー
	 */
	private final String TITLE_TEXT_KEY = "tokutei.kekkainput.frame.commentinput.dialog.title";
	/**
	 * 入力ガイダンス文字列取得用キー
	 */
	private final String GUIDANCE_TEXT_KEY = "tokutei.kekkainput.frame.commentinput.dialog.guidance";
	// edit s.inoue 2009/10/28
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	// 以下暫定的処置
	// コードマスタに定義すべき
	private final String[] SOUGOU_COMMENT_TEXT =
	{
		"血圧は正常です",
		"血圧に異常が見られます",
		"尿検査は正常です",
		"尿検査に異常が認められます",
		"血糖検査は正常です",
		"血糖検査に異常が認められます",
		"脂質検査は正常です",
		"脂質検査に異常が認められます",
		"肝機能検査は正常です",
		"肝機能検査に異常が認められます",
		"今回健診の医師の判断は「異常なし」です",
		"今回健診の医師の判断は「要観察」です",
		"今回健診の医師の判断は「要指導」です",
		"今回健診の医師の判断は「治療中」です",
		"今回健診の医師の判断は「要医療」です"
	};

	private JList jListComment = null;
	/**
	 * コンストラクタ
	 * @param owner
	 */
	public JSelectCommentInputDialog(Frame owner) {
		super(owner);
		String title = ViewSettings.getUsingValueString(TITLE_TEXT_KEY);
		String guidance = ViewSettings.getUsingValueString(GUIDANCE_TEXT_KEY);

		// edit ver2 s.inoue 2009/08/14
		// JList listBox = getJListComment();
		// initialize(title, guidance, listBox);
		// listBox.grabFocus();

//		Checkbox ch[] = new Checkbox[2];
//		ch[0] = (Checkbox)add(new Checkbox("受診券整理番号"));
//		ch[1] = (Checkbox)add(new Checkbox("氏名カナ"));
//		ch[0].addItemListener(this);
//		ch[1].addItemListener(this);
		initialize(title,guidance);

		// edit s.inoue 2009/10/28
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		this.focusTraversalPolicy.setDefaultComponent(this.jListComment);
		this.focusTraversalPolicy.addComponent(this.jListComment);
		this.focusTraversalPolicy.addComponent(this.jButtonOK);
		this.focusTraversalPolicy.addComponent(this.jButtonCancel);

		/* Added 2008/03/14 若月 可視状態になったときに、エディタに
		 * フォーカスを移動するように変更。 */
		/* --------------------------------------------------- */
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent arg0) {
				// del s.inoue 2009/08/17
				// JSelectCommentInputDialog.this.jListComment.grabFocus();
			}
		});
		/* --------------------------------------------------- */
	}

	/**
	 * リストボックスから選択されたテキストを取得する
	 */
	@Override
	public String getText() {
		return (String)jListComment.getSelectedValue();
	}

	/**
	 * 選択中セルのテキストと一致するリストボックスの項目を
	 * 選択状態に設定する
	 * @param 選択中セルテキスト
	 */
	@Override
	public void setText(String text) {
		if (text == null) return;

		for (int i = 0 ; i < SOUGOU_COMMENT_TEXT.length ; i++) {
			if (SOUGOU_COMMENT_TEXT[i].equals(text)) {
				jListComment.setSelectedIndex(i);
				return;
			}
		}
	}

	/**
	 * This method initializes jListComment
	 *
	 * @return javax.swing.JList
	 */
	private JList getJListComment() {
		if (jListComment == null) {
			jListComment = new JList(SOUGOU_COMMENT_TEXT);
			jListComment.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jListComment.setFont(ViewSettings.getCommonUserInputFont());
			jListComment.addKeyListener(this);
		}
		return jListComment;
	}

	/**
	 * リストボックスキーリスナコールバック
	 * Enterキー押下時にOKボタン押下と同じ処理を実行する
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {

		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			ActionEvent event = new ActionEvent(jButtonOK, 0, "exit");
			actionPerformed(event);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getPrintQuery() {

		ArrayList<String> conditions = new ArrayList<String>();

		/* 年度 */
		if (jCheckBox_Nendo.isSelected()){
			conditions.add("GET_NENDO.NENDO ");
		}
		/* 受診券整理番号 */
		if (jCheckBox_SeiriNo.isSelected()){
			conditions.add("KOJIN.JYUSHIN_SEIRI_NO");
		}
		/* カナ氏名 */
		if (jCheckBox_KanaName.isSelected()){
			conditions.add("KOJIN.KANANAME");
		}
		/* 氏名(漢字) */
		if (jCheckBox_Name.isSelected()){
			conditions.add("KOJIN.NAME");
		}
		/* 性別 */
		if (jCheckBox_Sex.isSelected()){
			conditions.add("KOJIN.SEX");
		}
		/* 生年月日 */
		if (jCheckBox_BirthDay.isSelected()){
			conditions.add("KOJIN.BIRTHDAY");
		}
		/* 検査年月日 */
		if (jCheckBox_KenshinNengapi.isSelected()){
			conditions.add("TOKUTEI.KENSA_NENGAPI");
		}
		/* 変換日時 */
		if (jCheckBox_HL7.isSelected()){
			conditions.add("TOKUTEI.HENKAN_NITIJI");
		}
		/* 保険者番号（個人） */
		if (jCheckBox_HokenjaNo.isSelected()){
			conditions.add("KOJIN.HKNJANUM");
		}
		/* 被保険者証等記号 */
		if (jCheckBox_HihokenjyaKigo.isSelected()){
			conditions.add("KOJIN.HIHOKENJYASYO_KIGOU");
		}
		/* 被保険者証等番号 */
		if (jCheckBox_HihokenjyaNo.isSelected()){
			conditions.add("KOJIN.HIHOKENJYASYO_NO");
		}

		/* 単価合計 */
		if (jCheckBox_Tanka.isSelected()){
			conditions.add("KESAI.TANKA_GOUKEI");
		}
		/* 窓口負担合計 */
		if (jCheckBox_Madoguti.isSelected()){
			conditions.add("KESAI.MADO_FUTAN_GOUKEI");
		}
		/* 請求金額合計 */
		if (jCheckBox_Seikyu.isSelected()){
			conditions.add("KESAI.SEIKYU_KINGAKU");
		}

		String retValue = "";
		if (! conditions.isEmpty()) {
			StringBuffer buffer = new StringBuffer();

			for (Iterator<String> iter = conditions.iterator(); iter.hasNext();) {
				String condition = iter.next();
				buffer.append(condition);

				if (iter.hasNext()) {
					buffer.append(" , ");
				}
			}

			retValue = buffer.toString();
		}

		return retValue;
	}
}
