package jp.or.med.orca.jma_tokutei.common.frame;

import java.util.HashMap;

/**
 * 画面用設定のデフォルト値。
 *
 * 画面設定用の外部ファイルを読み込めなかった場合、このクラスが提供する値を使用する。
 * map のキー名は、画面設定用の外部ファイルと同一にする。
 */
public class ViewSettingsDefault implements ViewSettingsKey{

	private static final String VALUE_DIALOG_FONT_PLAIN_14 = "Dialog,Font.PLAIN,14";

	private static HashMap<String, String> map = null;

	private static void initialize(){

		/* --------------------------------------------------
		 *  画面共通
		 * -------------------------------------------------- */
		getMap().put(KEY_COMMON_FRAME_GUIDANCE_LABEL_FONT, VALUE_DIALOG_FONT_PLAIN_14);
		getMap().put(KEY_COMMON_FRAME_TITLE_LABEL_FONT, "Dialog,Font.PLAIN,18");
		getMap().put(KEY_COMMON_FRAME_USER_INPUT_FONT, "Dialog,Font.PLAIN,12");
		getMap().put(KEY_COMMON_FRAME_WIDTH, "850");
		getMap().put(KEY_COMMON_FRAME_HEIGHT, "600");
		getMap().put(KEY_COMMON_FRAME_TITLE_LABEL_FGCOLOR, "51,51,51");
		getMap().put(KEY_COMMON_FRAME_TITLE_LABEL_BGCOLOR, "153,204,255");
		getMap().put(KEY_COMMON_FRAME_REQUIREDITEM_BGCOLOR, "255,255,128");
		getMap().put(KEY_COMMON_FRAME_IMPORTANTITEM_BGCOLOR, "128,255,128");
		getMap().put(KEY_COMMON_FRAME_DISABLEITEM_BGCOLOR, "205,205,205");
		getMap().put(KEY_COMMON_FRAME_PROGRESS_TITLE, "進捗状況");
		getMap().put(KEY_COMMON_FRAME_PROGRESS_GUIDANCE_MAIN, "処理を実行中です。しばらくお待ちください。");

		/* --------------------------------------------------
		 *  システム管理者用ソフトウエア
		 * -------------------------------------------------- */
		getMap().put(KEY_ADMIN_APPLICATION_NAME, "日医特定健康診査システム管理者用ソフトウェア");
		getMap().put(KEY_ADMIN_FRAME_TITLE_TEXT, "日医特定健康診査システム管理");
		getMap().put(KEY_ADMIN_SPLASH_DISPLAY_TIME,"3500");

		getMap().put(KEY_ADMIN_LOGIN_FRAME_TITLE, "システムメンテナンスログイン");
		getMap().put(KEY_ADMIN_LOGIN_FRAME_GUIDANCE_MAIN,
				"ユーザ名、パスワードを入力後、ログインボタンを押してください。");
		getMap().put(KEY_ADMIN_LOGIN_FRAME_WIDTH, "650");
		getMap().put(KEY_ADMIN_LOGIN_FRAME_HEIGHT, "280");

		/* --------------------------------------------------
		 *  特定健診ソフトウエア
		 * -------------------------------------------------- */
		/* 共通 */
		getMap().put(KEY_TOKUTEI_APPLICATION_NAME, "日医特定健康診査システム");
		getMap().put(KEY_TOKUTEI_FRAME_TITLE_TEXT, "日医特定健康診査システム");

		// add s.inoue 2009/12/21
		getMap().put(KEY_TOKUTEI_SYOKEN_DIALOG_TITLE, "所見選択");
		/* スプラッシュ画面 */
		getMap().put(KEY_TOKUTEI_SPLASH_DISPLAY_TIME,"3500");

		/* ログイン画面 */
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_GUIDANCE_MAIN,
				"健診機関番号、ユーザ名、パスワードを入力後、ログインボタンを押してください。");
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_WIDTH, "650");
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_HEIGHT, "280");
		getMap().put(KEY_TOKUTEI_LOGIN_FRAME_TITLE, "日医特定健康診査システムログイン");

		/* メインメニュー */
		getMap().put(KEY_TOKUTEI_MAINMENU_FRAME_TITLE, "メインメニュー");

		/* 受診券入力画面 */
		getMap().put(KEY_TOKUTEI_JUSHINKEN_FRAME_TITLE, "受診券入力（個人情報登録）");
		getMap().put(KEY_TOKUTEI_JUSHINKEN_FRAME_PROGRESS_GUIDANCE_PRINT1,"入力票データを作成しています。");

		/* 健診結果データ一覧画面 */
		getMap().put(KEY_TOKUTEI_KEKKALIST_FRAME_TITLE, "健診・問診結果データ一覧");

		/* 健診結果データ入力画面 */
		getMap().put(KEY_TOKUTEI_KEKKAINPUT_FRAME_TITLE, "健診・問診結果データ入力");

		/* 外部検査結果データ取込み画面 */
		getMap().put(KEY_TOKUTEI_IMPORTDATA_FRAME_TITLE, "外部検査結果データ取り込み");

		/* 健診結果表示・自動判定　検索画面 */
		getMap().put(KEY_TOKUTEI_SEARCHRESULT_FRAME_TITLE, "メタボリックシンドローム判定・階層化");
		getMap().put(KEY_TOKUTEI_SEARCHRESULT_FRAME_GUIDANCE_MAIN,
			"検索条件を入力し、検索ボタンを押して健診データを表示します。<br>健診データを選択し、画面下部のボタンを押して各処理を開始します。");

		/* 健診結果表示・自動判定画面 */
		getMap().put(KEY_TOKUTEI_SHOWRESULT_FRAME_TITLE,"健診・問診結果データ表示");
		getMap().put(KEY_TOKUTEI_SHOWRESULT_FRAME_PROGRESS_GUIDANCE_PRINT1,"健診・問診結果データを作成しています。");

		/* 特定健診検査依頼書画面 */
		getMap().put(KEY_TOKUTEI_IRAISHO_FRAME_TITLE,"検査依頼書データ表示");
		getMap().put(KEY_TOKUTEI_IRAISHO_FRAME_GUIDANCE_MAIN,"検査依頼書データを作成しています。");

		/* マスタメンテナンスメニュー画面 */
		getMap().put(KEY_TOKUTEI_MASTERMAINTENANCE_MENU_FRAME_TITLE, "マスタメンテナンス");

		/* システムメンテナンスメニュー画面 */
		getMap().put(KEY_TOKUTEI_SYSTEMMAINTENANCE_MENU_FRAME_TITLE, "システムメンテナンス");

		/* 請求データ編集画面 */
		// edit ver2 s.inoue 2009/08/06
		getMap().put(KEY_TOKUTEI_INPUTKESSAI_FRAME_TITLE, "日次処理(請求編集)");

		/* 請求・HL7出力画面 */
//		getMap().put(KEY_TOKUTEI_HL7_FRAME_TITLE, "請求・HL7出力");
//		getMap().put(KEY_TOKUTEI_HL7_FRAME_GUIDANCE_MAIN,
//			"検索条件を入力し、検索ボタンを押して健診データを表示します。<br>健診データを選択し、画面下部のボタンを押して各処理を開始します。");
		// edit ver2 s.inoue 2009/08/03
		/* 日次処理(請求) */
		getMap().put(KEY_TOKUTEI_NITIJI_FRAME_TITLE, "日次処理(請求)");
		getMap().put(KEY_TOKUTEI_NITIJI_FRAME_GUIDANCE_MAIN,
		"検索条件を入力し、検索ボタンを押して健診データを表示します。<br>健診データを選択し、画面下部のボタンを押して日次処理(請求)を開始します。");

		/* 月次処理(請求) */
		getMap().put(KEY_TOKUTEI_GETUJI_FRAME_TITLE, "月次処理(請求確定/HL7出力)");
		getMap().put(KEY_TOKUTEI_GETUJI_FRAME_GUIDANCE_MAIN,
		"検索条件を入力し、検索ボタンを押して健診データを表示します。<br>健診データを選択し、画面下部のボタンを押して月次処理(HL7出力)を開始します。");

		/* 機関情報編集画面 */
		getMap().put(KEY_TOKUTEI_KIKANINFORMATION_FRAME_WIDTH, "900");
		getMap().put(KEY_TOKUTEI_KIKANINFORMATION_FRAME_HEIGHT, "620");
		getMap().put("tokutei.kikaninformation.frame.title", "機関情報|追加");
		getMap().put("tokutei.kikaninformation.frame.guidance.main", "機関情報の追加を行います。値を編集後、登録ボタンを押して下さい。");
		getMap().put("tokutei.kikaninformation.frame.guidance.sub", "医師会等でとりまとめて送付される場合以外は、送付元機関番号には、健診機関番号と同じ番号を入れてください。");

		/* --------------------------------------------------
		 *  マスタメンテナンス
		 * -------------------------------------------------- */

		/* 検査センター検査項目コードマスタメンテナンス */
		getMap().put(KEY_TOKUTEI_KENSACENTER_KENSAITEM_CODE_MASTERMAINTENANCE_FRAME_TITLE, "検査センター検査項目コードマスタメンテナンス");

		/* 健診項目マスタメンテナンス画面 */
		getMap().put(KEY_TOKUTEI_KENSHIN_ITEM_MASTERMAINTENANCE_FRAME_TITLE, "健診項目メンテナンス");

		/* 検査項目コードマスタメンテナンス */
		getMap().put(KEY_TOKUTEI_KENSAITEM_CODE_MASTERMAINTENANCE_FRAME_TITLE, "検査項目コードマスタメンテナンス");

		/* 保険者情報マスタメンテナンス */
		getMap().put(KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_FRAME_TITLE, "保険者情報メンテナンス");

		/* 健診パターンメンテナンス */
		getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_FRAME_TITLE, "健診パターンメンテナンス");

		/* 支払代行情報マスタメンテナンス */
		getMap().put(KEY_TOKUTEI_SHIHARAI_MASTERMAINTENANCE_FRAME_TITLE, "支払代行メンテナンス");

		/* 保険者情報マスタメンテナンス */
		//getMap().put(KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_ADD_FRAME_TITLE, "保険者情報メンテナンス|追加");
		/* 保険者情報マスタメンテナンス */
		//getMap().put(KEY_TOKUTEI_HOKENJA_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "保険者情報メンテナンス|登録/更新/削除");
		/* 健診パターンメンテナンス編集 */
		//getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "健診パターンメンテナンス|編集");
		/* 健診パターンメンテナンス追加 */
		//getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_ADD_FRAME_TITLE, "健診パターンメンテナンス|追加");
		/* 健診パターンメンテナンス複製 */
		//getMap().put(KEY_TOKUTEI_KENSHINPATTERN_MASTERMAINTENANCE_COPY_FRAME_TITLE, "健診パターンメンテナンス|複製");
		/* 機関情報メンテナンス追加 */
		//getMap().put(KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_ADD_FRAME_TITLE, "機関情報メンテナンス|追加");
		/* 機関情報メンテナンス */
		//getMap().put(KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_FRAME_TITLE, "機関情報メンテナンス");
		/* 機関情報メンテナンス編集 */
		//getMap().put(KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "機関情報メンテナンス|編集");
		/* 支払代行情報マスタメンテナンス */
		//getMap().put(KEY_TOKUTEI_SHIHARAI_MASTERMAINTENANCE_EDIT_FRAME_TITLE, "支払代行メンテナンス|編集");
	}

	public static String getPorperty(String key){
		String value = getMap().get(key);
		return value;
	}

	private static HashMap<String, String> getMap() {
		if (map == null) {
			map = new HashMap<String, String>();
			initialize();
		}

		return map;
	}
}


