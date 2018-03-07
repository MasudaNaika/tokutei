package jp.or.med.orca.jma_tokutei.common.frame;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

/**
 * 登録、一覧画面で使用する共通ダイアログのインタフェース
 *
 */
public interface ISelectInputDialog {

	/**
	 * ダイアログ表示/非表示
	 * @param isVisible 表示/非表示フラグ
	 */
	void setVisible(boolean isVisible);

	/**
	 * ボタン押下状態取得
	 * @return ボタン押下状態
	 */
	RETURN_VALUE getStatus();

	/**
	 * ダイアログのテキストエリア/リストボックスに
	 * 選択中セルのテキストを設定する
	 * @param text 選択中セルテキスト
	 */
	void setText(String text);

	/**
	 * ダイアログから入力/選択されたテキストを
	 * 取得する
	 * @return 入力/選択テキスト
	 */
	String getText();

	/**
	 * 印刷用のクエリ
	 */
	String getPrintQuery();
}
