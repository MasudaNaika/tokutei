package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KenshinKekka;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * 特定健診結果通知表　2ページ
 */
public class Kekka_P2 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P2();
	}
	
	public Kekka_P2(){}

	/**
	 * 特定健診結果通知表　ページ2
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * Printdate Keys => Kojin, Kikan, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{
		if( pageIndex >= 2 )
		{
			return Printable.NO_SUCH_PAGE;
		}

		g.translate( (int)(pf.getImageableX()), (int)(pf.getImageableY()) );

		((Graphics2D)g).transform( java.awt.geom.AffineTransform.getScaleInstance( 1, 1 ) );

		// 直線を印刷
		Graphics2D g2d = (Graphics2D)g;
		
		/*
		 * 初期設定
		 * A4の枠を描画(72dpi 595px*842px)
		 * width : 451	余白25.4mm四方
		 * height: 695	余白25.4mm四方	
		 */
			final int x_w = 451;
			final int y_h = 695;
			
			/*
			 * 各項目の幅
			 */
			int base = 14;
			
			/*
			 * 個人情報部分の高さ
			 */
			int height_info = 20;
			
			/*
			 * 枠と枠の幅
			 */
			int height_middle = 10;
			
			/*
			 * 描画する線の太さ
			 */
			BasicStroke stroke;
			stroke = new BasicStroke(0.2f);
			g2d.setStroke(stroke);
			
			/*
			 * 文字の設定
			 */
			int fontsize = 7;
			Font f = new Font("Dialog" , Font.PLAIN, fontsize);
			g2d.setFont(f);
			
			/*
			 * y座標基準点
			 */
			int base_y = 0;
			
			/*
			 * 行数基準点
			 */
			int base_row = 0;
			
			/*
			 * ドラフト
			 */	

					fontsize = 64;
					f = new Font("Dialog" , Font.PLAIN, fontsize);
					g2d.setFont(f);
					Color c = g2d.getColor();
					g2d.setColor(Color.gray);
					g2d.drawString("ＤＲＡＦＴ", 80, 300);
					g2d.setColor(c);
					fontsize = 7;
					f = new Font("Dialog" , Font.PLAIN, fontsize);
					g2d.setFont(f);

		/*
		 * 枠の描画
		 */
			//外枠
				g2d.drawRect( 0, 0, x_w, y_h);
			
			/*
			 * 健診項目
			 */
				base_y = height_info;
				base_row = 5;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row * base);

				//横
				g2d.drawLine(275, base_y + 1*base, 441, base_y + base);
				g2d.drawLine(10, base_y + 2*base, 441, base_y + 2*base);
				g2d.drawLine(70, base_y + 3*base, 441, base_y + 3*base);
				g2d.drawLine(70, base_y + 4*base, 441, base_y + 4*base);
				
				//縦
				g2d.drawLine( 70, base_y + 2*base,  70, base_y + base_row*base);
				/* yabu 2008/03/12*/
				//g2d.drawLine(165, base_y, 165, base_y + base_row*base);
				g2d.drawLine(140, base_y, 140, base_y + base_row*base);
				/* yabu 2008/03/12*/
				g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(275, base_y, 275, base_y + base_row*base);
				g2d.drawLine(330, base_y, 330, base_y + base_row*base);
				g2d.drawLine(385, base_y, 385, base_y + base_row*base);
			/*
			 * 心電図、眼底検査　所見
			 */
				base_y += base_row * base + height_middle;
				base_row = 19;
				
				//枠
				g2d.drawRect(10, base_y, (x_w-20), base_row * base);
				
				//横
				g2d.drawLine(10, base_y + 1*base, 441, base_y + 1*base);
				g2d.drawLine(10, base_y + 10*base, 441, base_y + 10*base);
				
				//縦
				g2d.drawLine(100, base_y, 100, base_y + base_row*base);
				g2d.drawLine(213, base_y, 213, base_y + base_row*base);
				g2d.drawLine(326, base_y, 326, base_y + base_row*base);

			/*
			 * メタボリックシンドローム判定
			 */
				base_y += base_row*base + height_middle;
				base_row = 1;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);

				//縦
				g2d.drawLine(275, base_y,  275, base_y + base_row*base);
				g2d.drawLine(330, base_y,  330, base_y + base_row*base);
				g2d.drawLine(385, base_y,  385, base_y + base_row*base);
			
			/*
			 * 医師の判断
			 */
				base_y += base_row*base + height_middle;
				base_row = 17;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row * base);

				//横
				g2d.drawLine(10, base_y + 15*base, 441, base_y + 15*base);
				
				//縦
				g2d.drawLine(100, base_y, 100, base_y + base_row*base);
				
		/*
		 * フォーマット
		 */	
			/*
			 * 健診項目
			 */
				//1行目
				base_y = (base + fontsize)/2 + height_info;
				base_row = 5;
				
				g2d.drawString("項目"		, 10+(165-10)/2 - 2*fontsize/2, base_y + base/2);
				/* yabu 2008/03/12*/
				//g2d.drawString("基準値"		, 165+(220-165)/2 - 3*fontsize/2, base_y + base/2);
				g2d.drawString("基準値"		, 140+(220-140)/2 - 3*fontsize/2, base_y + base/2);
				/* yabu 2008/03/12*/
				g2d.drawString("単位"		, 220+(275-220)/2 - 2*fontsize/2, base_y + base/2);
				
				g2d.drawString("今回"		, 275+(330-275)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前回"		, 330+(385-330)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回"		, 385+(441-385)/2 - 3*fontsize/2, base_y);
				
				//1列目
				g2d.drawString("貧血検査"		, 10+(70-10)/2 - 4*fontsize/2, base_y + 2*base);
				
				//2列目
				g2d.drawString("赤血球数"			, 72, base_y + 2*base);
				g2d.drawString("血色素数"			, 72, base_y + 3*base);
				g2d.drawString("ヘマトクリット値"		, 72, base_y + 4*base);
				
				//4列目　単位
				g2d.drawString("万/㎡", 232, base_y + 2*base);
				g2d.drawString("g/dl", 232, base_y + 3*base);
				g2d.drawString("%", 232, base_y + 4*base);
				

			/*
			 * 心電図、眼底検査　所見
			 */
				base_y += base_row * base + height_middle;
				base_row = 19;

				//1行目
				g2d.drawString("今回", 100+(213-100)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前回", 213+(326-213)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回", 326+(441-326)/2 - 2*fontsize/2, base_y);
				
				//1列目
				g2d.drawString("心電図検査　所見"	, 10+(100-10)/2 - 8*fontsize/2, base_y + 5*base);
				g2d.drawString("眼底検査　所見"		, 10+(100-10)/2 - 7*fontsize/2, base_y + 14*base);
				
				
			/*
			 * メタボリックシンドローム判定
			 */
				base_y += base_row * base + height_middle;
				base_row = 1;
				
				g2d.drawString("メタボリックシンドローム判定", 10+(275-10)/2 - 13*fontsize/2, base_y);
			
			/*
			 * 医師の判断
			 */
				//1行目
				base_y += base_row*base + height_middle;
				base_row = 17;
				
				//1列目
				g2d.drawString("医師の判断"		, 10+(100-10)/2 - 5*fontsize/2, base_y + 7*base);
				g2d.drawString("判断した医師名"		, 10+(100-10)/2 - 7*fontsize/2, base_y + 15*base + base/2);
		
		/*
		 * データの移送と表示
		 */
			try{
			/*
			 * 個人情報
			 * PrintDataから個人情報を抽出
			 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
			/*
			 * 健診項目
			 * PrintDataから健診結果情報を抽出
			 */
				KenshinKekka tmpKenshinKekka = (KenshinKekka)printData.get("KenshinKekka");
				
				/*
				 * 健診年月日ごとのデータを取得
				 * 0=>今回
				 * 1=>前回
				 * 2=>前々回
				 */
				ArrayList<Hashtable<String, Hashtable<String, String>>> KenshinKekkaData = tmpKenshinKekka.getPrintKenshinKekkaData();

				/*
				 * 健診年月日を取得
				 * 0=>今回
				 * 1=>前回
				 * 2=>前々回
				 */
				String[] KensaNengapi = tmpKenshinKekka.getKensaNengapi();
				
				/*
				 * 貧血検査
				 * 赤血球数、血色素数、ヘマトクリット値
				 */
				base_y = (base + fontsize)/2 + height_info;
				base_row = 5;
				
					/*
					 * 健診年月日セット
					 * 今回、前回、前々回
					 */
					try {
						//今回
						g2d.drawString(KensaNengapi[0], 277, base_y + base);
						//前回
						g2d.drawString(KensaNengapi[1], 332, base_y + base);
						//前々回
						g2d.drawString(KensaNengapi[2], 387, base_y + base);
					} catch (NullPointerException e) {
					}
					/*
					 * 基準値をセット
					 */
					try {
						ArrayList<String> tmp = new ArrayList<String>();
						if (KojinData.get("SEX").equals("男性")){
							//赤血球数
							tmp.clear(); tmp.add("2A020000001930101"); tmp.add("2A020000001930101");
							if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
										+ tmpKenshinKekka.getDsJyogen(tmp, 0), 142, base_y + 2*base);
							}
							//血色素数
							tmp.clear(); tmp.add("2A030000001930101"); tmp.add("2A030000001930101");
							if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
										+ tmpKenshinKekka.getDsJyogen(tmp, 0), 142, base_y + 3*base);
							}
							//ヘマトクリット値
							tmp.clear(); tmp.add("2A040000001930102"); tmp.add("2A040000001930102");
							if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
										+ tmpKenshinKekka.getDsJyogen(tmp, 0), 142, base_y + 4*base);
							}
						}else if (KojinData.get("SEX").equals("女性")){
							//赤血球数
							tmp.clear(); tmp.add("2A020000001930101"); tmp.add("2A020000001930101");
							if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
										+ tmpKenshinKekka.getJsJyogen(tmp, 0), 142, base_y + 2*base);
							}
							//血色素数
							tmp.clear(); tmp.add("2A030000001930101"); tmp.add("2A030000001930101");
							if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
							}else{
								g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
										+ tmpKenshinKekka.getJsJyogen(tmp, 0), 142, base_y + 3*base);
							}
							//ヘマトクリット値
							tmp.clear(); tmp.add("2A040000001930102"); tmp.add("2A040000001930102");
							if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
							}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 142, base_y + 4*base);
							}
						}
//						//赤血球数
//						g2d.drawString(KenshinKekkaData.get(0).get("2A020000001930101").get("KIJYUNTI_HANI") , 222, base_y + 2*base);
//						//血色素数
//						g2d.drawString(KenshinKekkaData.get(0).get("2A030000001930101").get("KIJYUNTI_HANI") , 222, base_y + 3*base);
//						//ヘマトクリット値
//						g2d.drawString(KenshinKekkaData.get(0).get("2A040000001930102").get("KIJYUNTI_HANI") , 222, base_y + 4*base);
					} catch (NullPointerException e) {
					}
					/*
					 * 結果値をセット
					 */
					try {
						for(int i=0;i < KensaNengapi.length; i++){
							//赤血球数
							g2d.drawString(KenshinKekkaData.get(i).get("2A020000001930101").get("KEKA_TI") , 277 + i*55, base_y + 2*base);
							//ヘマトクリット値
							g2d.drawString(KenshinKekkaData.get(i).get("2A030000001930101").get("KEKA_TI") , 277 + i*55, base_y + 3*base);
							//血色素数
							g2d.drawString(KenshinKekkaData.get(i).get("2A040000001930102").get("KEKA_TI") , 277 + i*55, base_y + 4*base);
//							//赤血球数
//							g2d.drawString(KenshinKekkaData.get(i).get("2A020000001930101").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
//							//ヘマトクリット値
//							g2d.drawString(KenshinKekkaData.get(i).get("2A030000001930101").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
//							//血色素数
//							g2d.drawString(KenshinKekkaData.get(i).get("2A040000001930102").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
						}
					} catch (NullPointerException e) {
					}
				/*
				 * 心電図、眼底検査　所見
				 */
				base_y += base_row * base + height_middle;
				base_row = 19;
				
					/*
					 * 結果値をセット
					 * 所見
					 */
					for(int i=0;i < KensaNengapi.length; i++){
						/*
						 * 改行処理
						 * 15文字x17行
						 */
						//心電図
						try{
							if (!KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").isEmpty()){
								for(int j=0;j < 17;j++){
									/*
									 * 文字列の取得
									 */
									try {
										KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").substring(j*15, 15+j*15);
									} catch (IndexOutOfBoundsException e) {
										g2d.drawString(KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").substring(j*15), 102 + j*113, base_y + fontsize + fontsize/2 + 2 +  j*fontsize);
										break;
									} catch (NullPointerException e){
										break;
									}
									g2d.drawString(KenshinKekkaData.get(i).get("9A110160800000049").get("KEKA_TI").substring(j*15, 15+j*15), 102 + j*113, base_y + fontsize + fontsize/2 + 2 +  j*fontsize);
								}
							}
						} catch	(Exception e){
						}
						//眼底検査
						try{
							if (!KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").isEmpty()){
								for(int j=0;j < 17;j++){
									/*
									 * 文字列の取得
									 */
									try {
										KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").substring(j*15, 15+j*15);
									} catch (java.lang.StringIndexOutOfBoundsException e) {
										g2d.drawString(KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").substring(j*15), 102 + j*113, base_y  + 9*base + fontsize + fontsize/2 + 2 +  j*fontsize);
										break;
									} catch (NullPointerException e){
										break;
									}
									g2d.drawString(KenshinKekkaData.get(i).get("9E100160900000049").get("KEKA_TI").substring(j*15, 15+j*15), 102 + j*113, base_y  + 9*base + fontsize + fontsize/2 + 2 +  j*fontsize);
								}
							}
						} catch	(Exception e){
						}
					}
				/*
				 * メタボリックシンドローム判定
				 */
					base_y += base_row * base + height_middle;
					base_row = 1;
					/*
					 * 結果値をセット
					 */
					try {
						for(int i=0;i < KensaNengapi.length; i++){
							g2d.drawString(KenshinKekkaData.get(i).get("9N501000000000011").get("CODE_NAME"), 277 + i*55, base_y);
						}
					}catch (IndexOutOfBoundsException e){
						
					}
					
				/*
				 * 医師の判断
				 * 44文字*41行まで
				 * value_bottom[2]
				 */
					base_y += base_row*base + height_middle;
					base_row = 17;
					
					/*
					 * 医師の判断（今回の健診のみ）
					 * 改行処理
					 * 44文字x40行
					 */
					for(int i = 0;i < 40;i++){
						try {
							KenshinKekkaData.get(0).get("9N511000000000049").get("KEKA_TI").substring(i*44, 44+i*44);
						} catch (java.lang.StringIndexOutOfBoundsException e) {
							g2d.drawString(KenshinKekkaData.get(0).get("9N511000000000049").get("KEKA_TI").substring(i*44), 112, base_y + i*fontsize + 7 - base/2);
							break;
						}
						g2d.drawString(KenshinKekkaData.get(0).get("9N511000000000049").get("KEKA_TI").substring(i*44, 44+i*44), 112, base_y + i*fontsize + 7 - base/2);
					}
					
					//判断した医師名
					g2d.drawString(KenshinKekkaData.get(0).get("9N516000000000049").get("KEKA_TI"), 112, base_y + 15*base + base/2);

//			/*
//			 * 個人情報
//			 * PrintDataから個人情報を抽出
//			 */
//				Kojin tmpKojin = (Kojin)PrintData.get("Kojin");
//				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
				/*
				 * 受診券整理番号とページ番号
				 */
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 380, 670);
				g2d.drawString("Page.2", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

