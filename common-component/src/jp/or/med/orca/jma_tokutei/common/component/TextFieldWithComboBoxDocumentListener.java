package jp.or.med.orca.jma_tokutei.common.component;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * コンボボックスと連動したテキストフィールドへの入力を通知する DocumentListner
 */
public class TextFieldWithComboBoxDocumentListener 
	implements DocumentListener {

	private JComboBox comboBox = null;
	private int newItemIndex = 0;
	
	/**
	 * コンストラクタ
	 */
	public TextFieldWithComboBoxDocumentListener(
			JComboBox comboBox,
			int newItemIndex){
		
		this.comboBox = comboBox;
		this.newItemIndex = newItemIndex;
	}
	
	public void changedUpdate(DocumentEvent e) {
		
	}

	public void insertUpdate(DocumentEvent e) {
		if (! comboBox.hasFocus()) {
			changeSelectedItem(e);
		}
	}

	public void removeUpdate(DocumentEvent e) {
		if (! comboBox.hasFocus()) {
			changeSelectedItem(e);
		}
	}
	
	/**
	 * 入力したテキストに合わせて、コンボボックスの項目を検索し、
	 * 見つかった最初の項目を選択する。 
	 */
//	private void changeSelectedItem(final JComboBox comboBox, DocumentEvent e) {
	private void changeSelectedItem(DocumentEvent e) {
		String inputText = this.getInputText(e);
		
		if (inputText == null || inputText.isEmpty()) {
			comboBox.setSelectedIndex(0);
		}
		else {
			int findIndex = this.searchComoboBoxItem(inputText, true);
			
			if (findIndex < 0) {
				comboBox.setSelectedIndex(this.newItemIndex);
			}
			else {
				comboBox.setSelectedIndex(findIndex);
			}
		}
	}

	/**
	 * 入力済みのテキストを取得する。
	 */
	private String getInputText(DocumentEvent e){
		String text = null;
		
		int length = e.getDocument().getLength();
		
		try {
			text = e.getDocument().getText(0, length);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		return text;
	}
	
	/**
	 * 指定したテキストに合わせて、コンボボックス内の項目を検索し、
	 * 見つかった最初の項目のインデックスを取得する。
	 * 
	 * @param boolean trimBeforeZero 先頭の連続する 0 を削除する。
	 */
//	private int searchComoboBoxItem(String text){
//		int retValue = this.searchComoboBoxItem(text, false);
//		return retValue;
//	}

	private int searchComoboBoxItem(String text, boolean trimBeforeZero){
		int findIndex = -1;
		int count = comboBox.getItemCount();
		
		for (int i = 0; i < count; i++) {
			String itemText = (String)comboBox.getItemAt(i);
			
			/* 指定したテキストが 0 で開始していない場合 */
			if (
					text != null 
					&& ! text.isEmpty()
//					&& String.valueOf(text.charAt(0)).startsWith("0")) {
					&& ! text.startsWith("0")) {
				
				/* コンボボックスの項目の先頭の 0 を削除して比較する場合 */
				if (trimBeforeZero) {
					itemText = itemText.replaceFirst("^0+", "");
				}
			}
			
			if (itemText.indexOf(text) == 0) {
				findIndex = i;
				break;
			}
		}
		
		return findIndex;
	}
}
