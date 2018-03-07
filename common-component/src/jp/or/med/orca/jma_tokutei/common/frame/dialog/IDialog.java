package jp.or.med.orca.jma_tokutei.common.frame.dialog;

import java.util.Vector;

import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;

/**
 * messageDialogInterface
 */
public interface IDialog {

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
	 * ファイルパス取得
	 * @return ファイルパス
	 */
	String getFilePath();

	/**
	 * 指定健診日取得
	 * @return 健診日
	 */
	String getKenshinDate();

	/**
	 * 印刷方法取得 枚数(1 or 2)
	 * @return 印刷方法
	 */
	Integer getPrintSelect();

	/*
	 * 健診パターン番号
	 */
	String getK_PNo();

	/*
	 * 健診パターン名
	 */
	String getK_PName();

	/*
	 * 健診パターン:備考
	 */
	String getK_PNote();

	/**
	 * ダイアログのテキストエリア/リストボックスに
	 * 選択中セルのテキストを設定する
	 * @param text 選択中セルテキスト
	 */
	void setText(String text);

	/**
	 * タイトル部分に表示する文字列を設定
	 * @param title タイトル文字列
	 */
	void setMessageTitle(String title);

	/**
	 * 文字列を非活性設定
	 * @param title タイトル文字列
	 */
	void setEnabled(boolean enabled);

	/**
	 * 文字列を非活性設定
	 * @param title タイトル文字列
	 */
	void setDialogSelect(boolean enabled);

	/**
	 * タイトル部分に表示する文字列を設定
	 * @param title タイトル文字列
	 */
	void setDialogTitle(String title);

	/**
	 * タイトル部分に表示する文字列を設定
	 * @param title タイトル文字列
	 */
	void setSaveFileName(String title);

	/**
	 * キャンセルボタン表示/非表示設定
	 * @param isShowCancel
	 */
	void setShowCancelButton(boolean isShowCancel);

	// add s.inoue 2012/07/04
	Vector getDataSelect();
}