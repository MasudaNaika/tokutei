package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.Enumeration;
//import java.util.Properties;
//
//import javax.swing.JFrame;
//
//import jp.or.med.orca.jma_tokutei.errormessage.JErrorMessageDialogFrameCtrl;

/**
 * 画面クラス設定
 *
 * New 2008/03/07 若月
 * 　現在、各メソッドで固定値を返しているが、将来的にはプロパティファイルまたは
 * 　データベースから値を読み込むようにする予定。
 */
public class ViewSettings {

//	private static final String PROPERTY_FILE_NAME = "view.properties";
//	private static Properties properties;

	private static String commonTitle = null;
	private static String adminCommonTitleWithVersion = null;
	private static String commonTitleWithKikanInfomation = null;

	/**
	 * キーを指定して、設定ファイルから画面用設定値を取得する。
	 * 設定用ファイルを読み込めない場合は、デフォルト値クラスからデフォルト値を
	 * 取得する。
     */
	private static String getUsingValue(String key) {

		String value = ViewSettingsFile.getProperty(key);

		if (value == null || value.isEmpty()) {
			value = ViewSettingsDefault.getPorperty(key);
		}

		return value;
	}

	/**
	 * <p>システム管理者用ソフトウエアの Frame の共通タイトルを取得する</p>
	 * @eturn String 共通タイトル
	 */
	public static String getAdminFrameCommonTitle() {
		String value = commonTitle;

		if (commonTitle == null || commonTitle.isEmpty()) {
			value = getUsingValue("admin.frame.title.text");
		}

		return value;
	}

	public static void setAdminFrameTitle(String title){
		commonTitle = title;
	}

	public static void setAdminFrameTitleWithVersion(String title){
		adminCommonTitleWithVersion = title;
	}

	/**
	 * <p>特定健診ソフトウエアの Frame の共通タイトルを取得する</p>
	 * @eturn String 共通タイトル
	 */
	public static String getTokuteFrameTitle() {
		String value = commonTitle;
		value = getUsingValue("tokutei.frame.title.text");
		return value;
	}

	public static void setTokuteFrameTitleWithKikanInfomation(String title){
		commonTitleWithKikanInfomation = title;
	}
	public static String getTokuteFrameTitleWithKikanInfomation(){
		return commonTitleWithKikanInfomation;
	}

// del s.inoue 2011/05/10
//	/**
//	 * <p>Form の共通グリッドサイズを取得する</p>
//	 * @eturn String 共通グリッドサイズ
//	 */
//	public static int getGridPageSize() {
//		return getUsingValueInt("common.form.grid.view.pagesize");
//	}

	/**
	 * <p>Frame の共通サイズを取得する</p>
	 * @eturn String 共通サイズ
	 */
	public static Dimension getFrameCommonSize() {
		int width = getUsingValueInt("common.frame.width");
		int height = getUsingValueInt("common.frame.height");

		return new Dimension(width, height);
	}

	/**
	 * <p>Form の共通サイズを取得する</p>
	 * @eturn String 共通サイズ
	 */
	public static Dimension getFormCommonSize() {
		int width = getUsingValueInt("common.form.width");
		int height = getUsingValueInt("common.form.height");

		return new Dimension(width, height);
	}

	/**
	 * <p>機関情報画面の共通サイズを取得する</p>
	 * @eturn String 共通タイトル
	 */
	public static Dimension getKikanInformationFrameSize() {
		int width = getUsingValueInt("tokutei.kikaninformation.frame.width");
		int height = getUsingValueInt("tokutei.kikaninformation.frame.height");

		return new Dimension(width, height);
	}

	/**
	 * int 型の設定値を取得する。
     */
	public static int getUsingValueInt(String key) {
		int value = 0;

		try {
			value = Integer.parseInt(getUsingValue(key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * 必須項目の背景色を取得する。
	 * @return Color
	 */
	public static Color getRequiedItemBgColor() {
		Color color = getUsingValueColor("common.frame.requireditem.bgcolor");

		return color;
	}


    /*
     * カラーテーブル前景色
     */
    public static Color getRequiedItemFrColor()
    {
        Color color = getUsingValueColor("common.frame.requireditem.frcolor");
        return color;
    }
    public static Color getSyosaiItemFrColor()
    {
        Color color = getUsingValueColor("common.frame.syosai.frcolor");
        return color;
    }
    public static Color getTuikaItemFrColor()
    {
        Color color = getUsingValueColor("common.frame.tuika.frcolor");
        return color;
    }

    /*
     * カラーテーブル背景色
     */
    public static Color getKihonItemBgColor()
    {
        Color color = getUsingValueColor("common.frame.kihon.bgcolor");
        return color;
    }
    public static Color getSyosaiItemBgColor()
    {
        Color color = getUsingValueColor("common.frame.syosai.bgcolor");
        return color;
    }
    public static Color getTuikaItemBgColor()
    {
        Color color = getUsingValueColor("common.frame.tuika.bgcolor");
        return color;
    }

	/**
	 * 重要項目の背景色を取得する。
	 * @return Color
	 */
	public static Color getImportantItemBgColor() {
		Color color = getUsingValueColor("common.frame.importantitem.bgcolor");

		return color;
	}

	/**
	 * 入力不可項目の背景色を取得する。
	 * @return Color
	 */
	public static Color getDisableItemBgColor() {
		Color color = getUsingValueColor("common.frame.disableitem.bgcolor");

		return color;
	}

	// add h.yoshitama 2009/09/18
	/**
	 * 入力可能項目の背景色を取得する。
	 * @return Color
	 */
	public static Color getAbleItemBgColor() {
		Color color = getUsingValueColor("common.frame.ableitem.bgcolor");

		return color;
	}

	/**
	 * String 型の設定値を取得する。
     */
	public static String getUsingValueString(String key) {
		String value = getUsingValue(key);

		if (value == null) {
			value = "";
		}

		return value;
	}

	/**
	 * Color 型の設定値を取得する。
     */
	public static Color getUsingValueColor(String key) {
		String value = getUsingValue(key).trim();

		int r = 0;
		int g = 0;
		int b = 0;

		Color color = Color.BLACK;

		if (value != "") {
			String[] values = value.split(",");

			if (values != null && values.length == 3) {

				try {
					r = Integer.parseInt(values[0]);
					g = Integer.parseInt(values[1]);
					b = Integer.parseInt(values[2]);

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				if (r >= 0 && g >= 0 && b >= 0
						&& r <= 255 && g <= 255 && b <= 255) {

					color = new Color(r,g,b);
				}
			}
		}

		return color;
	}

	/**
	 * 共通の Frame の幅を取得する。
     */
	public static int getFrameCommonWidth() {
		int width = getUsingValueInt("common.frame.width");
		return width;
	}

	/**
	 * 共通の Frame の高さを取得する。
     */
	public static int getFrameCommonHeight() {
		int height = getUsingValueInt("common.frame.height");
		return height;
	}

	/**
	 * システム管理者用ソフトウエアのログイン画面の Frame のサイズを取得する。
     */
	public static Dimension getAdminLoginFrameSize() {
		int width = getAdminLoginFrameWidth();
		int height = getAdminLoginFrameHeight();

		Dimension size = new Dimension(width, height);
		return size;
	}

	/**
	 * システム管理者用ソフトウエアのログイン画面の Frame の高さを取得する。
     */
	public static int getAdminLoginFrameWidth() {
		int width = getUsingValueInt("admin.login.frame.width");
		return width;
	}

	/**
	 * システム管理者用ソフトウエアのログイン画面の Frame の高さを取得する。
     */
	public static int getAdminLoginFrameHeight() {
		int height = getUsingValueInt("admin.login.frame.height");
		return height;
	}

	/**
	 * システム管理者用ソフトウエアのスプラッシュ画面の表示時間を取得する。
     */
	public static int getAdminSplashDisplayTime() {
		int height = getUsingValueInt("admin.splash.display-time");
		return height;
	}

	/**
	 * 特定健診ソフトウエアのスプラッシュ画面の表示時間を取得する。
     */
	public static int getTokuteiSplashDisplayTime() {
		int height = getUsingValueInt("tokutei.splash.display-time");
		return height;
	}

	/**
	 * 特定健診ソフトウエアのログイン画面の Frame の幅を取得する。
     */
	public static int getTokuteiLoginFrameWidth() {
		int width = getUsingValueInt("tokutei.login.frame.width");
		return width;
	}

	/**
	 * 特定健診ソフトウエアのログイン画面の Frame の高さを取得する。
     */
	public static int getTokuteiLoginFrameHeight() {
		int height = getUsingValueInt("tokutei.login.frame.height");
		return height;
	}

	/**
	 * 特定健診ソフトウエアのログイン画面の Frame のサイズを取得する。
     */
	public static Dimension getTokuteiLoginFrameSize() {
		Dimension d = new Dimension(getTokuteiLoginFrameWidth(), getTokuteiLoginFrameHeight());
		return d;
	}

//	/**
//	 * 共通ガイダンス用フォントを取得する。
//	 * @return Font
//	 */
//	public static Font getGuidanceLableFont() {
//		return getFontFromId("common.frame.guidance-lable.font");
//	}

	/**
	 * 共通入力用フォントを取得する。
	 * @return Font
	 */
	public static Font getCommonUserInputFont() {
		return getFontFromId("common.frame.user-input.font");
	}

	/**
	 * 共通入力用フォントを取得する。
	 * @return Font
	 */
	public static Font getCommonUserInputFont(int style) {
		return getFontFromId("common.frame.user-input.font", style);
	}

	/**
	 * タイトルラベル用共通フォントを取得する。
	 * @return Font
	 */
	public static Font getCommonTitleLabelFont() {
		return getFontFromId("common.frame.title-label.font");
	}

	/**
	 * ガイダンスラベル用共通フォントを取得する。
	 * @return Font
	 */
	public static Font getGuidanceLabelFont() {
		return getFontFromId("common.frame.guidance-label.font");
	}

//	/**
//	 * 画面用設定ファイルの ID を指定してフォントを取得する。
//	 */
//	private static Font getFontFromId(String fontId) {
//		Font font = null;
//
//		String[] properties = getUsingValue(fontId).split(",");
//
//		String name = "";
//		int style = 0;
//		int size = 0;
//
//		if (properties != null &&  properties.length == 3) {
//			/* フォント名 */
//			name = properties[0].trim();
//
//			/* フォントスタイル */
//			try {
//				style = Font.class.getDeclaredField(properties[1].trim()).getInt(null);
//
//			} catch (Exception e) {
//				/* デフォルト値を使用する。 */
//			}
//
//			/* フォントサイズ */
//			try {
//				size = Integer.parseInt(properties[2].trim());
//			} catch (NumberFormatException e) {
//				/* デフォルト値を使用する。 */
//			}
//
//			font = new Font(name, style, size);
//		}
//
//		return font;
//	}

	/**
	 * 画面用設定ファイルの ID とスタイルを指定してフォントを取得する。
	 */
	private static Font getFontFromId(String fontId) {
		return getFontFromId(fontId, -1);

//		Font font = null;
//
//		String name = "";
//		int size = 0;
//		int style = 0;
//
//		String[] properties = getUsingValue(fontId).split(",");
//
//		if (properties != null &&  properties.length == 3) {
//			/* フォント名 */
//			name = properties[0].trim();
//
//			/* フォントスタイル */
//			try {
//				style = Font.class.getDeclaredField(properties[1].trim()).getInt(null);
//
//			} catch (Exception e) {
//				/* デフォルト値を使用する。 */
//			}
//
//			/* フォントサイズ */
//			try {
//				size = Integer.parseInt(properties[2].trim());
//			} catch (NumberFormatException e) {
//				/* デフォルト値を使用する。 */
//			}
//
//			font = new Font(name, style, size);
//		}
//
//		return font;
	}

	/**
	 * 画面用設定ファイルの ID とスタイルを指定してフォントを取得する。
	 */
	private static Font getFontFromId(String fontId, int style) {
		Font font = null;

		String name = "";
		int size = 0;
		int useStyle = style;

		String[] properties = getUsingValue(fontId).split(",");

		if (properties != null &&  properties.length == 3) {
			/* フォント名 */
			name = properties[0].trim();

			if (style == -1) {
				/* フォントスタイル */
				try {
					String settingStyle = properties[1].trim();
					useStyle = Font.class.getDeclaredField(settingStyle).getInt(null);

				} catch (Exception e) {
					/* デフォルト値を使用する。 */
					useStyle = Font.PLAIN;
				}
			}

			/* フォントサイズ */
			try {
				size = Integer.parseInt(properties[2].trim());
			} catch (NumberFormatException e) {
				/* デフォルト値を使用する。 */
			}

			font = new Font(name, useStyle, size);
		}

		return font;
	}

	public static String getAdminCommonTitleWithVersion() {
		return adminCommonTitleWithVersion;
	}

	public static void setAdminCommonTitleWithVersion(String title) {
		ViewSettings.adminCommonTitleWithVersion = title;
	}
	/* --------------------------------------------------- */

//	/**
//	 * 画面用設定ファイルの ID を指定してフォントを取得する。
//	 */
//	private static Font getFontFromId(String fontId, int style) {
//		Font font = null;
//
//		String name = "";
//		int size = 0;
//
//		String[] properties = getUsingValue(fontId).split(",");
//
//		if (properties != null &&  properties.length == 3) {
//			/* フォント名 */
//			name = properties[0].trim();
//
//			font = new Font(name, style, size);
//		}
//
//		return font;
//	}

//	/**
//	 * 初期化
//	 */
//	public static boolean initialize(){
//		boolean success = false;
//
//		properties = new Properties();
//		try	{
//			FileInputStream fs = new FileInputStream(PROPERTY_FILE_NAME);
//			InputStreamReader in = new InputStreamReader(fs, "Shift_JIS");
//			properties.load(in);
//			success = true;
//		}
//		catch(Exception e)	{
//			success = false;
//		}
//
//		if (success) {
//			syntaxCheck();
//		}
//
//		return success;
//	}
//
//	/**
//	 * プロパティファイルの文法チェックを行う
//	 */
//	private static boolean syntaxCheck()	{
//		boolean success = false;
//
//		if (properties != null){
//			Enumeration<Object> items = properties.keys();
//
//			while(items.hasMoreElements()){
//
//				String keyName = (String)items.nextElement();
//				String right = properties.getProperty(keyName);
//				String[] values = right.split(",");
//
//				// 配列の要素外参照エラーが起きる可能性あり
//				if(values[0].length() <= 0 ){
//					new JErrorMessageDialogFrameCtrl(null,
//							"エラー","画面用設定ファイルの読み込みに失敗しました。ファイルを確認してください。" +
//									"エラーが発生したIDは「" + keyName + "」です。" ,0,0,"M0103");
//					break;
//				}
//			}
//
//			success = true;
//		}
//
//		return success;
//	}

//	/**
//	 * <p>画面用設定値を取得する。</p>
//	 * @return String 画面用設定値
//	 */
//	public static String getProperty(String id, JFrame parent) {
//
//		String value = (String)properties.get(id);
//
//		if(value == null){
//			new JErrorMessageDialogFrameCtrl(
//					parent,
//					"エラー","メッセージの解析に失敗しました。",0,0,"M0102");
//
//			value = "";
//		}
//
//		return value;
//	}
//
//	public static String getProperty(String id) {
//		String value = ViewSettings.getProperty(id, null);
//		return value;
//	}
}


