package jp.or.med.orca.jma_tokutei.common.frame;

import javax.swing.JFrame;

public class JSelectInputDialogFactory {

	/**
	 * ダイアログ生成
	 * @param parent 親ウインドウ
	 * @return 入力用ダイアログ
	 */
	public static ISelectInputDialog createDialog(JFrame parent) {
		// 総合コメント入力用ダイアログ生成
		return new JSelectCommentInputDialog(parent);
	}
}
