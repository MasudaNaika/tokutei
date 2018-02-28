package jp.or.med.orca.jma_tokutei.common.convert;

public class JZenkakuKatakanaToHankakuKatakana
{
	private static char[] Zenkaku = {
		'ア','イ','ウ','エ','オ',
		'カ','キ','ク','ケ','コ',
		'サ','シ','ス','セ','ソ',
		'タ','チ','ツ','テ','ト',
		'ナ','ニ','ヌ','ネ','ノ',
		'ハ','ヒ','フ','ヘ','ホ',
		'マ','ミ','ム','メ','モ',
		'ヤ','ユ','ヨ',
		'ラ','リ','ル','レ','ロ',
		'ワ','ン','ヴ',
		'ガ','ギ','グ','ゲ','ゴ',
		'ザ','ジ','ズ','ゼ','ゾ',
		'ダ','ヂ','ヅ','デ','ド',
		'バ','ビ','ブ','ベ','ボ',
		'ャ','ュ','ョ',
		'ァ','ィ','ゥ','ェ','ォ',
		'パ','ピ','プ','ペ','ポ',
		'ッ','　'
	};


	private static String[] Hankaku = {
		"ｱ","ｲ","ｳ","ｴ","ｵ",
		"ｶ","ｷ","ｸ","ｹ","ｺ",
		"ｻ","ｼ","ｽ","ｾ","ｿ",
		"ﾀ","ﾁ","ﾂ","ﾃ","ﾄ",
		"ﾅ","ﾆ","ﾇ","ﾈ","ﾉ",
		"ﾊ","ﾋ","ﾌ","ﾍ","ﾎ",
		"ﾏ","ﾐ","ﾑ","ﾒ","ﾓ",
		"ﾔ","ﾕ","ﾖ",
		"ﾗ","ﾘ","ﾙ","ﾚ","ﾛ",
		"ﾜ","ﾝ","ｳﾞ",
		"ｶﾞ","ｷﾞ","ｸﾞ","ｹﾞ","ｺﾞ",
		"ｻﾞ","ｼﾞ","ｽﾞ","ｾﾞ","ｿﾞ",
		"ﾀﾞ","ﾁﾞ","ﾂﾞ","ﾃﾞ","ﾄﾞ",
		"ﾊﾞ","ﾋﾞ","ﾌﾞ","ﾍﾞ","ﾎﾞ",
		"ｬ","ｭ","ｮ",
		"ｧ","ｨ","ｩ","ｪ","ｫ",
		"ﾊﾟ","ﾋﾟ","ﾌﾟ","ﾍﾟ","ﾎﾟ",
		"ｯ"," "
	};
	// add start 20080903 s.inoue
	//	 半角←→全角変換テーブル
	private static String eisukigoHanZenTbl[][] =         {
	 {"!","！"},{"\"","”"},{"#","＃"},{"$","＄"},{"%","％"},
	{"&","＆"},{"'","’"},{"(","（"},{")","）"},{"*","＊"} ,
	{"+","＋"},{".","．"},{"-","－"},{".","．"},{"/","／"},
	{":","："},{";","；"},{">","＞"},{"=","＝"},{"<","＜"},
	{"?","？"},{"@","＠"},{"[","［"},{"\\","￥"},{"]","］"},
	{"^","＾"},{"_","＿"},{"`","‘"},{"{","｛"},{"|","｜"},
	{"}","｝"},{"~","～"},{",","、"},{"1","１"},{"2","２"},{"3","３"},{"4","４"},{"5","５"},
	{"6","６"},{"7","７"},{"8","８"},{"9","９"},{"0","０"},{"A","Ａ"},{"B","Ｂ"},{"C","Ｃ"},{"D","Ｄ"},{"E","Ｅ"},
	{"F","Ｆ"},{"G","Ｇ"},{"H","Ｈ"},{"I","Ｉ"},{"J","Ｊ"},{"K","Ｋ"},{"L","Ｌ"},{"M","Ｍ"},{"N","Ｎ"},{"O","Ｏ"},
	{"P","Ｐ"},{"Q","Ｑ"},{"R","Ｒ"},{"S","Ｓ"},{"T","Ｔ"},{"U","Ｕ"},{"V","Ｖ"},{"W","Ｗ"},{"X","Ｘ"},{"Y","Ｙ"},
	{"Z","Ｚ"},{"a","ａ"},{"b","ｂ"},{"c","ｃ"},{"d","ｄ"},{"e","ｅ"},
	{"f","ｆ"},{"g","ｇ"},{"h","ｈ"},{"i","ｉ"},{"j","ｊ"},
	{"k","ｋ"},{"l","ｌ"},{"m","ｍ"},{"n","ｎ"},{"o","ｏ"},
	{"p","ｐ"},{"q","ｑ"},{"r","ｒ"},{"s","ｓ"},{"t","ｔ"},
	{"u","ｕ"},{"v","ｖ"},{"w","ｗ"},{"x","ｘ"},{"y","ｙ"},{"z","ｚ"},{"I","Ⅰ"}
	};

//	// 全角かどうか
//	public static String isZenkakuKatakana(String zenkaku){
//		String retstr = "";
//		for (int i = 0; i < Zenkaku.length; i++) {
//			if (zenkaku.equals(Zenkaku[i]))
//				retstr = String.valueOf(Zenkaku[i]);
//		}
//		return retstr;
//	}
//	// 半角かどうか
//	public static String isHankakuKatakana(String hankaku){
//		String retstr = "";
//		for (int i = 0; i < Hankaku.length; i++) {
//			if (hankaku.equals(Hankaku[i]))
//				retstr = String.valueOf(Hankaku[i]);
//		}
//		return retstr;
//	}

	/**
	* 全角文字チェック
	* @param sBuf
	* @return boolean
	*/
	public static boolean isWideChar(String sBuf){

		int iLen;
		byte[] byteLen;
		iLen = sBuf.length(); //文字長取得
		byteLen = sBuf.getBytes(); //バイト数取得

		//文字数の２倍＝バイト数　または　空文字
		if ((iLen * 2) != byteLen.length || sBuf.length() == 0){
			return false;
		}
		return true;
	}

	public static String Convert(String str)
	{
		String OutputString = "";

		for(int i = 0 ; i < str.length() ; i++)
		{
			OutputString += GetHankakuKatakana(str.charAt(i));
		}

		return OutputString;
	}

	private static String GetHankakuKatakana(char c)
	{
		for(int j = 0 ; j < Zenkaku.length ; j++)
		{
			if(c == Zenkaku[j])
			{
				return Hankaku[j];
			}
		}

		return String.valueOf(c);
	}


	/*** 半角英数記号を全角英数記号に変換する
	* @param   String str  文字列
	* @return  String 変換後の文字列
	*/
	public static String eisukigoHanToZen(String str) {
		String zenstr = "";
		String chkstr = "";

		// strを1文字づつ eisukigouHanZenTblと照らし合わせて変換する
		for (int i = 0; i < str.length(); i++) {
		   chkstr = str.substring(i, i+1);
		   for (int j = 0; j < eisukigoHanZenTbl.length; j++) {
		       if (chkstr.equals(eisukigoHanZenTbl[j][0])) {
		           chkstr = eisukigoHanZenTbl[j][1];
		           break;
		       }
		   }
		   zenstr = zenstr + chkstr;
		}
		return zenstr;
	}

	/*** 全角英数記号を半角英数記号に変換する
	* @param   String str  文字列
	* @return  String 変換後の文字列
	*/
	public static String eisukigoZenToHan(String str) {
		String hanstr = "";
		String chkstr = "";
		// strを1文字づつ eisukigouHanZenTblと照らし合わせて変換する
		for (int i = 0; i < str.length(); i++) {
		   chkstr = str.substring(i, i+1);
		   for (int j = 0; j < eisukigoHanZenTbl.length; j++) {
		       if (chkstr.equals(eisukigoHanZenTbl[j][1])) {
		           chkstr = eisukigoHanZenTbl[j][0];
		           break;
		       }
		   }
		   hanstr = hanstr + chkstr;
		}
		return hanstr;
	}
	//	 addend 20080903 s.inoue
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

