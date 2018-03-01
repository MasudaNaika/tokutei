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
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * 特定健診結果通知表　1ページ
 */
public class Kekka_P1 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P1();
	}
	
	public Kekka_P1(){}

	/**
	 * 特定健診結果通知表　ページ1
	 * @param Graphics g, PageFormat pf, int pageIndex, Hashtable<String, Object>PrintData 
	 * Printdate Keys => Kojin, Kikan, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{

		/*
		 * 印刷ページ振り分け
		 */
		if( pageIndex == 0){
			/*
			 * 1ページ目を印刷
			 */
		} else if( pageIndex == 1){
			/*
			 * 2ページ目を印刷
			 */
			Kekka_P2 Kekka = new Kekka_P2();
			Kekka.setQueryResult(printData);
			Kekka.print(g, pf, pageIndex);
			return Printable.PAGE_EXISTS;
		} else if( pageIndex == 2){
			/*
			 * 3ページ目を印刷
			 */
			Kekka_P3 Kekka = new Kekka_P3();
			Kekka.setQueryResult(printData);
			Kekka.print(g, pf, pageIndex);
			return Printable.PAGE_EXISTS;
		} else
		if( pageIndex == 3){
			/*
			 * 4ページ目を印刷
			 */
			Kekka_P4 Kekka = new Kekka_P4();
			Kekka.setQueryResult(printData);
			Kekka.print(g, pf, pageIndex);
			return Printable.PAGE_EXISTS;
		} else {
			/*
			 * 終了
			 */
			return Printable.NO_SUCH_PAGE;
		}

		g.translate( (int)(pf.getImageableX()), (int)(pf.getImageableY()) );

		((Graphics2D)g).transform( java.awt.geom.AffineTransform.getScaleInstance( 1, 1 ) );

		// 直線を印刷
		Graphics2D g2d = (Graphics2D)g;
		
		
		/*
		 * 初期設定
		 * A4の枠を描画(72dpi 595px*842px)
		 * width : 451px	余白25.4mm四方
		 * height: 695px	余白25.4mm四方	
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
			int height_info = 112;
			
			/*
			 * 枠と枠の幅
			 */
			int height_middle = 20;
			
			/*
			 * 描画する線の太さ
			 */
			BasicStroke stroke;
			stroke = new BasicStroke(0.2f);
			g2d.setStroke(stroke);
			
			/*
			 * 文字の設定
			 */
			int fontsize = 8;
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
					fontsize = 8;

			
		/*
		 * 枠の描画
		 */
			//外枠
				g2d.drawRect( 0, 0, x_w, y_h);
			
			/*
			 * 個人情報
			 */
				base_y = height_info;
				base_row = 2;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);

				//横
				for (int i = 0;i < base_row; i++){
					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//縦
				g2d.drawLine( 70, base_y,  70, base_y + base_row*base);
				g2d.drawLine(170, base_y, 170, base_y + base_row*base);
				g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(310, base_y, 310, base_y + base_row*base);
				g2d.drawLine(370, base_y, 370, base_y + base_row*base);

			/*
			 * 既往歴
			 */
				base_y += base_row*base + height_middle;
				base_row = 4;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);

				//横
				for (int i = 0;i < base_row; i++){
					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//縦
				g2d.drawLine( 70, base_y,  70, base_y + base_row*base);
				g2d.drawLine(310, base_y + 1*base, 310, base_y + 2*base);
				g2d.drawLine(370, base_y + 1*base, 370, base_y + 2*base);
			
				
			
			/*
			 * 測定結果簡易
			 */
				base_y += base_row*base + height_middle;
				base_row = 18;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row * base);

				base_row = 2;
				
				//横
				g2d.drawLine(275, base_y + base, 441, base_y + base);
				
				//縦
				/*add yabu 2008/03/12*/
				//g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(240, base_y, 240, base_y + base_row*base);
				g2d.drawLine(170, base_y, 170, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(275, base_y, 275, base_y + base_row*base);
				g2d.drawLine(330, base_y, 330, base_y + base_row*base);
				g2d.drawLine(385, base_y, 385, base_y + base_row*base);
				
				base_y += 2*base;
				base_row = 16;
				
				//横
				g2d.drawLine(10, base_y, 70, base_y);
				g2d.drawLine(10, base_y + 4*base, 70, base_y + 4*base);
				g2d.drawLine(10, base_y + 6*base, 70, base_y + 6*base);
				g2d.drawLine(10, base_y + 9*base, 70, base_y + 9*base);
				g2d.drawLine(10, base_y + 12*base, 70, base_y + 12*base);
				g2d.drawLine(10, base_y + 14*base, 70, base_y + 14*base);
				g2d.drawLine(10, base_y + 16*base, 70, base_y + 16*base);
				for (int i = 0;i < base_row; i++){
					g2d.drawLine(70, base_y + i*base, 441, base_y + i*base);			
				}
				
				//縦
				g2d.drawLine( 70, base_y,  70, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(120, base_y, 120, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(170, base_y, 170, base_y + base_row*base);
				/* yabu 2008/03/12*/
				//g2d.drawLine(220, base_y, 220, base_y + base_row*base);
				g2d.drawLine(240, base_y, 240, base_y + base_row*base);
				/* yabu 2008/03/12*/
				g2d.drawLine(275, base_y, 275, base_y + base_row*base);
				g2d.drawLine(330, base_y, 330, base_y + base_row*base);
				g2d.drawLine(385, base_y, 385, base_y + base_row*base);

				
				
		/*
		 * フォーマット
		 */	
			/*
			 * 健診機関情報
			 */
				/* yabu 2008/03/12 */
				//g2d.drawString("特定健診結果通知表", 441/2 - 9*fontsize/2, 20);
				fontsize = 12;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
    			g2d.drawString("特定健診結果通知表", 441/2 - 9*fontsize, 20);
				fontsize = 8;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				/* yabu 2008/03/12 */
				
				//文字の設定
				fontsize = 7;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				
				g2d.drawString("健診機関名"	, 250, 40);
				g2d.drawString("〒"			, 250, 50);
				g2d.drawString("電話番号"		, 250, 60);
				g2d.drawString("住所"		, 250, 80);
		
			/*
			 * 個人情報
			 */
				//1行目
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("フリガナ"		, 10+(70-10)/2 - 4*fontsize/2, base_y);
				g2d.drawString("生年月日"		, 170+(220-170)/2 - 4*fontsize/2, base_y);
				g2d.drawString("受診日"		, 310+(370-310)/2 - 3*fontsize/2, base_y);
				
				//2行目
				base_y += base;
				g2d.drawString("氏名"			, 10+(70-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("性別・年齢"		, 170+(220-170)/2 - 5*fontsize/2, base_y);
				g2d.drawString("受診券整理番号"	, 310+(370-310)/2 - 7*fontsize/2, base_y);

			/*
			 * 既往歴
			 */
				//1列目
				base_y += height_middle + base;
				g2d.drawString("既往歴"		, 10+(70-10)/2 - 3*fontsize/2, base_y); base_y += base;
				g2d.drawString("既往症"		, 10+(70-10)/2 - 3*fontsize/2, base_y);
				g2d.drawString("喫煙歴"		, 310+(370-310)/2 - 3*fontsize/2, base_y); base_y += base;
				g2d.drawString("自覚症状"		, 10+(70-10)/2 - 4*fontsize/2, base_y); base_y += base;
				g2d.drawString("他覚症状"		, 10+(70-10)/2 - 4*fontsize/2, base_y);
			
			/*
			 * 測定結果簡易
			 */
				//1行目
				base_y += height_middle + base;
				/* yabu 2008/03/12 */
				//g2d.drawString("項目"		, 70+(170-70)/2 - 2*fontsize/2, base_y + base/2);
				//g2d.drawString("基準値"		, 220+(275-220)/2 - 3*fontsize/2, base_y + base/2);
				g2d.drawString("項目"		, 84, base_y + base/2);
				g2d.drawString("基準値"		, 194, base_y + base/2);
				g2d.drawString("H/L"		, 254, base_y + base/2);
				/* yabu 2008/03/12 */
				g2d.drawString("今回"		, 275+(330-275)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前回"		, 330+(385-330)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回"		, 385+(441-385)/2 - 3*fontsize/2, base_y);
				
				//1列目
				base_y += base + base/2;
				g2d.drawString("身体計測"		, 10+(70-10)/2 - 4*fontsize/2, base_y + 4*base/2); base_y += 4*base;
				g2d.drawString("血圧"		, 10+(70-10)/2 - 2*fontsize/2, base_y + 2*base/2); base_y += 2*base;
				g2d.drawString("血中脂質検査"	, 10+(70-10)/2 - 6*fontsize/2, base_y + 3*base/2); base_y += 3*base;
				g2d.drawString("肝機能検査"	, 10+(70-10)/2 - 5*fontsize/2, base_y + 3*base/2); base_y += 3*base;
				g2d.drawString("血糖検査"		, 10+(70-10)/2 - 4*fontsize/2, base_y + 2*base/2); base_y += 2*base;
				g2d.drawString("尿検査"		, 10+(70-10)/2 - 3*fontsize/2, base_y + 2*base/2);
				
				//2列目
				base_y -= 13*base + base/2;
				String[][] koumoku_name = {
							 {"身長", "cm"},{"体重", "kg"},{"腹囲","cm"},{"BMI", ""}
							,{"収縮期血圧", "mmHg"},{"拡張期血圧", "mmHg"}
							,{"中性脂肪", "mg/dl"},{"HDL-ｺﾚｽﾃﾛｰﾙ-", "mg/dl"},{"LDL-ｺﾚｽﾃﾛｰﾙ-", "mg/dl"}
							,{"GOT", "U/I"},{"GPT", "U/I"},{"γ-GTP", "U/I"}
							,{"空腹時血糖", "mg/dl"},{"ﾍﾓｸﾞﾛﾋﾞﾝAｌc", "%"}
							,{"糖", ""},{"蛋白", ""}
						};
				for(int i = 0;i < 16;i++){
					g2d.drawString(koumoku_name[i][0], 72, base_y + i*base);
					//** yabu 2008/03/12**//
					//g2d.drawString(koumoku_name[i][1], 182, base_y + i*base);
					g2d.drawString(koumoku_name[i][1], 132, base_y + i*base);
					//** yabu 2008/03/12**//
				}
		
		/*
		 * データの移送と表示
		 */
			try{
			/*
			 * 健診機関情報
			 * PrintDataから健診機関情報を抽出
			 */
				Kikan tmpKikan = (Kikan)printData.get("Kikan");
				Hashtable<String, String> KikanData = tmpKikan.getPrintKikanData();
				
				//健診機関名
				g2d.drawString(KikanData.get("KIKAN_NAME"), 300, 40);
				//郵便番号
				g2d.drawString(KikanData.get("POST"), 300, 50);
				//電話番号
				g2d.drawString(KikanData.get("TEL"), 300, 60);
				//住所
				g2d.drawString(KikanData.get("ADR"), 300, 80);
				//地番方書
				g2d.drawString(KikanData.get("TIBAN"), 300, 80 + fontsize);
				
			/*
			 * 個人情報
			 * PrintDataから個人情報を抽出
			 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();

				//一列目
				base_y = height_info + (base + fontsize)/2;
				//フリガナ
				g2d.drawString(KojinData.get("KANANAME"), 72, base_y);
				//生年月日
				g2d.drawString(KojinData.get("BIRTHDAY"), 222, base_y);
				//健診年月日
				g2d.drawString(KojinData.get("KENSA_NENGAPI"), 372, base_y);
				
				//二列目
				base_y = height_info + (base + fontsize)/2 + base;
				//氏名
				g2d.drawString(KojinData.get("NAME"), 72, base_y);
				//性別・年齢
				g2d.drawString(KojinData.get("SEX")+ "  " +KojinData.get("AGE"), 222, base_y);
				//受診券整理番号
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 372, base_y);
			
			/*
			 * 健診結果情報
			 * PrintDataから健診結果情報を抽出
			 */
				ArrayList<String> tmp = new ArrayList<String>();
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
				 * 既往歴、既往症、自覚症状、他覚症状、喫煙歴
				 */
					base_y = height_info + (base + fontsize)/2 + 2*base + height_middle;
					if(KenshinKekkaData.size()>0){
						//既往歴
						g2d.drawString(KenshinKekkaData.get(0).get("9N056000000000011").get("CODE_NAME"), 72, base_y + 0*base);
						//既往症　仕様未定義のため未実装
						//g2d.drawString("", 72, base_y + 0*base);
						//自覚症状
						g2d.drawString(KenshinKekkaData.get(0).get("9N061000000000011").get("CODE_NAME"), 72, base_y + 2*base);
						//他覚症状
						g2d.drawString(KenshinKekkaData.get(0).get("9N066000000000011").get("CODE_NAME"), 72, base_y + 3*base);
						//喫煙歴
						g2d.drawString(KenshinKekkaData.get(0).get("9N736000000000011").get("CODE_NAME"), 372, base_y + 1*base);
					}
				
				/*
				 * 健診年月日セット
				 * 今回、前回、前々回
				 */
					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 1*base;
					
					try {
						//今回
						g2d.drawString(KensaNengapi[0], 277, base_y);
						//前回
						g2d.drawString(KensaNengapi[1], 332, base_y);
						//前々回
						g2d.drawString(KensaNengapi[2], 387, base_y);
					} catch (NullPointerException e) {
					}
				/*
				 * 基準値セット
				 * 身体測定、血圧、血中脂質検査、肝機能検査、血糖検査、尿検査
				 * 身長、体重、腹囲はいらないとのこと
				 */
					/*2008/02/12yabu 追加
					 * 基準値
					 * 男性、女性
					 */
					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
					if (KojinData.get("SEX").equals("男性")){
						//身長
//						g2d.drawString(KenshinKekkaData.get(0).get("9N001000000000001").get("DS_KAGEN")+"～"
//								+ KenshinKekkaData.get(0).get("9N001000000000001").get("DS_JYOUGEN") , 172, base_y + 0*base);
//						//体重
//						g2d.drawString(KenshinKekkaData.get(0).get("9N006000000000001").get("DS_KAGEN")+"～"
//								+ KenshinKekkaData.get(0).get("9N006000000000001").get("DS_JYOUGEN") , 172, base_y + 1*base);
//						// 腹囲（実測）		9N016160100000001
//						g2d.drawString(KenshinKekkaData.get(0).get("9N016160100000001").get("DS_KAGEN")+"～"
//								+ KenshinKekkaData.get(0).get("9N016160100000001").get("DS_JYOUGEN") , 172, base_y + 2*base);
//						//BMI
						tmp.clear(); tmp.add("9N011000000000001"); tmp.add("9N011000000000001");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0) , 172, base_y + 3*base);
						}
						/*
						 * 収縮期血圧（1回目）	9A751000000000001
						 * 収縮期血圧（2回目）	9A752000000000001
						 * 収縮期血圧（その他）	9A755000000000001
						 */
						tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 4*base);
						}
						/*
						 * 拡張期血圧（一回目）	9A761000000000001
						 * 拡張期血圧（二回目）	9A762000000000001
						 * 拡張期血圧（その他）	9A765000000000001
						 */
						tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 5*base);
						}
						/*
						 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去））	3F015000002327101
						 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去））	3F015000002327201
						 * 中性脂肪（その他）								3F015000002399901
						 */
						tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 6*base);
						}
						/*
						 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）））	3F070000002327101
						 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）））	3F070000002327201
						 * HDL-コレステロール-（その他）						3F070000002399901
						 */
						tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 7*base);
						}
						/*
						 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））	3F077000002327101
						 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））	3F077000002327201
						 * LDL-コレステロール-（その他）						3F077000002399901
						 */
						tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 8*base);
						}
						/*
						 * GOT（紫外吸光光度法（JSCC標準化対応法））				3B035000002327201
						 * GOT（その他）									3B035000002399901
						 */
						tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 9*base);
						}
						/*
						 * GPT（紫外吸光光度法（JSCC標準化対応法））				3B045000002327201
						 * GPT（その他）									3B045000002399901
						 */
						tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 10*base);
						}
						/*
						/*
						 * y-GTP（可視吸光光度法（JSCC標準化対応法））			3B090000002327101
						 * y-GTP（その他）									3B090000002399901
						 */
						tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 11*base);
						}
						/*
						 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法））			3D010000001926101
						 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法））			3D010000001727101
						 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））	3D010000001927201
						 * 空腹時血糖（その他）								3D010000001999901
						 */
						tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000001727101");
						tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 12*base);
						}
						/*
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	免疫学的方法（ラテックス凝集比濁法等）	3D045000001906202
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	HPLC(不安定分画除去HPLC法）			3D045000001920402
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	酵素法							3D045000001927102
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	その他							3D045000001999902
						 */
						tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402");
						tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getDsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getDsJyogen(tmp, 0), 172, base_y + 13*base);
						}
//						/*
//						 * 糖	試験紙法（機械読み取り）						1A020000000191111
//						 * 糖	試験紙法（目視法）							1A020000000190111
//						 */
//						tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "～"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 14*base);
//						/*
//						 * 蛋白	試験紙法（機械読み取り）						1A010000000191111
//						 * 蛋白	試験紙法（目視法）							1A010000000190111
//						 */
//						tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "～"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 15*base);

						//女性の場合
					}else if(KojinData.get("SEX").equals("女性")){
//						//BMI
						tmp.clear(); tmp.add("9N011000000000001"); tmp.add("9N011000000000001");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0)+"～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0) , 172, base_y + 3*base);
						}
						/*
						 * 収縮期血圧（1回目）	9A751000000000001
						 * 収縮期血圧（2回目）	9A752000000000001
						 * 収縮期血圧（その他）	9A755000000000001
						 */
						tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 4*base);
						}
						/*
						 * 拡張期血圧（一回目）	9A761000000000001
						 * 拡張期血圧（二回目）	9A762000000000001
						 * 拡張期血圧（その他）	9A765000000000001
						 */
						tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 5*base);
						}
						/*
						 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去））	3F015000002327101
						 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去））	3F015000002327201
						 * 中性脂肪（その他）								3F015000002399901
						 */
						tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 6*base);
						}
						/*
						 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）））	3F070000002327101
						 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）））	3F070000002327201
						 * HDL-コレステロール-（その他）						3F070000002399901
						 */
						tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 7*base);
						}
						/*
						 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））	3F077000002327101
						 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））	3F077000002327201
						 * LDL-コレステロール-（その他）						3F077000002399901
						 */
						tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 8*base);
						}
						/*
						 * GOT（紫外吸光光度法（JSCC標準化対応法））				3B035000002327201
						 * GOT（その他）									3B035000002399901
						 */
						tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 9*base);
						}
						/*
						 * GPT（紫外吸光光度法（JSCC標準化対応法））				3B045000002327201
						 * GPT（その他）									3B045000002399901
						 */
						tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 10*base);
						}
						/*
						/*
						 * y-GTP（可視吸光光度法（JSCC標準化対応法））			3B090000002327101
						 * y-GTP（その他）									3B090000002399901
						 */
						tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
						if(tmpKenshinKekka.getJsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 11*base);
						}
						/*
						 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法））			3D010000001926101
						 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法））			3D010000001727101
						 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））	3D010000001927201
						 * 空腹時血糖（その他）								3D010000001999901
						 */
						tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000001727101");
						tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 12*base);
						}
						/*
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	免疫学的方法（ラテックス凝集比濁法等）	3D045000001906202
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	HPLC(不安定分画除去HPLC法）			3D045000001920402
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	酵素法							3D045000001927102
						 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	その他							3D045000001999902
						 */
						tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402");
						tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
						if(tmpKenshinKekka.getDsKagen(tmp, 0).isEmpty()){
						}else{
							g2d.drawString(tmpKenshinKekka.getJsKagen(tmp, 0) + "～"
									+ tmpKenshinKekka.getJsJyogen(tmp, 0), 172, base_y + 13*base);
						}
//						/*
//						 * 糖	試験紙法（機械読み取り）						1A020000000191111
//						 * 糖	試験紙法（目視法）							1A020000000190111
//						 */
//						tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "～"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 14*base);
//						/*
//						 * 蛋白	試験紙法（機械読み取り）						1A010000000191111
//						 * 蛋白	試験紙法（目視法）							1A010000000190111
//						 */
//						tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
//						g2d.drawString(tmpKenshinKekka.getBikou(tmp, 0) + "～"
//								+ tmpKenshinKekka.getBikou(tmp, 0), 172, base_y + 15*base);

					}

//					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
//					//身長
//					g2d.drawString(KenshinKekkaData.get(0).get("9N001000000000001").get("KIJYUNTI_HANI") , 222, base_y + 0*base);
//					//体重
//					g2d.drawString(KenshinKekkaData.get(0).get("9N006000000000001").get("KIJYUNTI_HANI") , 222, base_y + 1*base);
//					/*
//					 * 腹囲（実測）		9N016160100000001
//					 * 腹囲（自己判定）	9N016160200000001
//					 * 腹囲（自己申告）	9N016160300000001
//					 */
//					tmp.clear(); tmp.add("9N016160100000001"); tmp.add("9N016160200000001"); tmp.add("9N016160300000001");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0), 222, base_y + 2*base);
//					//BMI
//					g2d.drawString(KenshinKekkaData.get(0).get("9N011000000000001").get("KIJYUNTI_HANI") , 222, base_y + 3*base);
//					/*
//					 * 収縮期血圧（1回目）	9A751000000000001
//					 * 収縮期血圧（2回目）	9A752000000000001
//					 * 収縮期血圧（その他）	9A755000000000001
//					 */
//					tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0) , 222, base_y + 4*base);
//					/*
//					 * 拡張期血圧（一回目）	9A761000000000001
//					 * 拡張期血圧（二回目）	9A762000000000001
//					 * 拡張期血圧（その他）	9A765000000000001
//					 */
//					tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 5*base);
//					/*
//					 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去））	3F015000002327101
//					 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去））	3F015000002327201
//					 * 中性脂肪（その他）								3F015000002399901
//					 */
//					tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 6*base);
//					/*
//					 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）））	3F070000002327101
//					 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）））	3F070000002327201
//					 * HDL-コレステロール-（その他）						3F070000002399901
//					 */
//					tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 7*base);
//					/*
//					 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））	3F077000002327101
//					 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））	3F077000002327201
//					 * LDL-コレステロール-（その他）						3F077000002399901
//					 */
//					tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 8*base);
//					/*
//					 * GOT（紫外吸光光度法（JSCC標準化対応法））				3B035000002327201
//					 * GOT（その他）									3B035000002399901
//					 */
//					tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 9*base);
//					/*
//					 * GPT（紫外吸光光度法（JSCC標準化対応法））				3B045000002327201
//					 * GPT（その他）									3B045000002399901
//					 */
//					tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 10*base);
//					/*
//					 * y-GTP（可視吸光光度法（JSCC標準化対応法））			3B090000002327101
//					 * y-GTP（その他）									3B090000002399901
//					 */
//					tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 11*base);
//					/*
//					 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法））			3D010000001926101
//					 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法））			3D010000002227101
//					 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））	3D010000001927201
//					 * 空腹時血糖（その他）								3D010000001999901
//					 */
//					tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000002227101"); tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 12*base);
//					/*
//					 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	免疫学的方法（ラテックス凝集比濁法等）	3D045000001906202
//					 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	HPLC(不安定分画除去HPLC法）			3D045000001920402
//					 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	酵素法							3D045000001927102
//					 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	その他							3D045000001999902
//					 */
//					tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402"); tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 13*base);
//					/*
//					 * 糖	試験紙法（機械読み取り）						1A020000000191111
//					 * 糖	試験紙法（目視法）							1A020000000190111
//					 */
//					tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 14*base);
//					/*
//					 * 蛋白	試験紙法（機械読み取り）						1A010000000191111
//					 * 蛋白	試験紙法（目視法）							1A010000000190111
//					 */
//					tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
//					g2d.drawString(tmpKenshinKekka.getKijyunti(tmp, 0)  , 222, base_y + 15*base);
				/*2008/02/12yabu 追加*/
				
				/*　
				 * Ｈ/Ｌ判定 
				 * 身長、体重、腹囲はいらない
				 */
				base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
//				身長
//				g2d.drawString(KenshinKekkaData.get(0).get("9N001000000000001").get("H_L"), 250, base_y + 0*base);
//				体重
//				g2d.drawString(KenshinKekkaData.get(0).get("9N006000000000001").get("H_L"), 250, base_y + 1*base);
//				 腹囲（実測）		9N016160100000001
//				g2d.drawString(KenshinKekkaData.get(0).get("9N016160100000001").get("H_L"), 250, base_y + 2*base);
				// BMI
				tmp.clear(); tmp.add("9N011000000000001");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 3*base);
				/*
				 * 収縮期血圧（1回目）	9A751000000000001
				 * 収縮期血圧（2回目）	9A752000000000001
				 * 収縮期血圧（その他）	9A755000000000001
				 */
				tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 4*base);
				/*
				 * 拡張期血圧（一回目）	9A761000000000001
				 * 拡張期血圧（二回目）	9A762000000000001
				 * 拡張期血圧（その他）	9A765000000000001
				 */
				tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 5*base);
				/*
				 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去））	3F015000002327101
				 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去））	3F015000002327201
				 * 中性脂肪（その他）								3F015000002399901
				 */
				tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 6*base);
				/*
				 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）））	3F070000002327101
				 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）））	3F070000002327201
				 * HDL-コレステロール-（その他）						3F070000002399901
				 */
				tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 7*base);
				/*
				 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））	3F077000002327101
				 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））	3F077000002327201
				 * LDL-コレステロール-（その他）						3F077000002399901
				 */
				tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 8*base);
				/*
				 * GOT（紫外吸光光度法（JSCC標準化対応法））				3B035000002327201
				 * GOT（その他）									3B035000002399901
				 */
				tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 9*base);
				/*
				 * GPT（紫外吸光光度法（JSCC標準化対応法））				3B045000002327201
				 * GPT（その他）									3B045000002399901
				 */
				tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 10*base);
				/*
				/*
				 * y-GTP（可視吸光光度法（JSCC標準化対応法））			3B090000002327101
				 * y-GTP（その他）									3B090000002399901
				 */
				tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 11*base);
				/*
				 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法））			3D010000001926101
				 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法））			3D010000001727101
				 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））	3D010000001927201
				 * 空腹時血糖（その他）								3D010000001999901
				 */
				tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000001727101");
				tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 12*base);
				/*
				 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	免疫学的方法（ラテックス凝集比濁法等）	3D045000001906202
				 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	HPLC(不安定分画除去HPLC法）			3D045000001920402
				 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	酵素法							3D045000001927102
				 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	その他							3D045000001999902
				 */
				tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402");
				tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 13*base);
				/*
				 * 糖	試験紙法（機械読み取り）						1A020000000191111
				 * 糖	試験紙法（目視法）							1A020000000190111
				 */
				tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 14*base);
				/*
				 * 蛋白	試験紙法（機械読み取り）						1A010000000191111
				 * 蛋白	試験紙法（目視法）							1A010000000190111
				 */
				tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
				g2d.drawString(tmpKenshinKekka.getHL(tmp, 0) , 250, base_y + 15*base);

				/*
				 * 身体測定、血圧、血中脂質検査、肝機能検査、血糖検査、尿検査
				 * 今回、前回、前々回
				 */
					base_y = (base + fontsize)/2 + height_info + 2*base + height_middle + 4*base + height_middle + 2*base;
					for(int i=0;i < KensaNengapi.length; i++){
						try {
							//身長
							g2d.drawString(KenshinKekkaData.get(i).get("9N001000000000001").get("KEKA_TI") , 277 + i*55, base_y + 0*base);
							//体重
							g2d.drawString(KenshinKekkaData.get(i).get("9N006000000000001").get("KEKA_TI") , 277 + i*55, base_y + 1*base);
							/*
							 * 腹囲（実測）		9N016160100000001
							 * 腹囲（自己判定）	9N016160200000001
							 * 腹囲（自己申告）	9N016160300000001
							 */
							tmp.clear(); tmp.add("9N016160100000001"); tmp.add("9N016160200000001"); tmp.add("9N016160300000001");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 2*base);
							//BMI
							g2d.drawString(KenshinKekkaData.get(i).get("9N011000000000001").get("KEKA_TI") , 277 + i*55, base_y + 3*base);
							/*
							 * 収縮期血圧（1回目）	9A751000000000001
							 * 収縮期血圧（2回目）	9A752000000000001
							 * 収縮期血圧（その他）	9A755000000000001
							 */
							tmp.clear(); tmp.add("9A751000000000001"); tmp.add("9A752000000000001"); tmp.add("9A755000000000001");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 4*base);
							/*
							 * 拡張期血圧（一回目）	9A761000000000001
							 * 拡張期血圧（二回目）	9A762000000000001
							 * 拡張期血圧（その他）	9A765000000000001
							 */
							tmp.clear(); tmp.add("9A761000000000001"); tmp.add("9A762000000000001"); tmp.add("9A765000000000001");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 5*base);
							/*
							 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去））	3F015000002327101
							 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去））	3F015000002327201
							 * 中性脂肪（その他）								3F015000002399901
							 */
							tmp.clear(); tmp.add("3F015000002327101"); tmp.add("3F015000002327201"); tmp.add("3F015000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 6*base);
							//HDL-コレステロール-
							/*
							 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）））	3F070000002327101
							 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）））	3F070000002327201
							 * HDL-コレステロール-（その他）						3F070000002399901
							 */
							tmp.clear(); tmp.add("3F070000002327101"); tmp.add("3F070000002327201"); tmp.add("3F070000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 7*base);
							/*
							 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））	3F077000002327101
							 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））	3F077000002327201
							 * LDL-コレステロール-（その他）						3F077000002399901
							 */
							tmp.clear(); tmp.add("3F077000002327101"); tmp.add("3F077000002327201"); tmp.add("3F077000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 8*base);
							/*
							 * GOT（紫外吸光光度法（JSCC標準化対応法））				3B035000002327201
							 * GOT（その他）									3B035000002399901
							 */
							tmp.clear(); tmp.add("3B035000002327201"); tmp.add("3B035000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 9*base);
							/*
							 * GPT（紫外吸光光度法（JSCC標準化対応法））				3B045000002327201
							 * GPT（その他）									3B045000002399901
							 */
							tmp.clear(); tmp.add("3B045000002327201"); tmp.add("3B045000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 10*base);
							/*
							 * y-GTP（可視吸光光度法（JSCC標準化対応法））			3B090000002327101
							 * y-GTP（その他）									3B090000002399901
							 */
							tmp.clear(); tmp.add("3B090000002327101"); tmp.add("3B090000002399901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 11*base);
							/*
							 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法））			3D010000001926101
							 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法））			3D010000002227101
							 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））	3D010000001927201
							 * 空腹時血糖（その他）								3D010000001999901
							 */
							tmp.clear(); tmp.add("3D010000001926101"); tmp.add("3D010000002227101"); tmp.add("3D010000001927201"); tmp.add("3D010000001999901");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 12*base);
							/*
							 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	免疫学的方法（ラテックス凝集比濁法等）	3D045000001906202
							 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	HPLC(不安定分画除去HPLC法）			3D045000001920402
							 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	酵素法							3D045000001927102
							 * ﾍﾓｸﾞﾛﾋﾞﾝA1c	その他							3D045000001999902
							 */
							tmp.clear(); tmp.add("3D045000001906202"); tmp.add("3D045000001920402"); tmp.add("3D045000001927102"); tmp.add("3D045000001999902");
							g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 13*base);

							
							/*
							 * 糖	試験紙法（機械読み取り）						1A020000000191111
							 * 糖	試験紙法（目視法）							1A020000000190111
							 */
							tmp.clear(); tmp.add("1A020000000191111"); tmp.add("1A020000000190111");
							//g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 14*base);
			    			if (tmpKenshinKekka.getKekati(tmp, i).length() >= 9) {
				    			int fn = fontsize;
			    				fontsize = 4;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 14*base);
				    			fontsize = fn;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
			    			} else {
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 14*base);
			    			}
							/*
							 * 蛋白	試験紙法（機械読み取り）						1A010000000191111
							 * 蛋白	試験紙法（目視法）							1A010000000190111
							 */
							tmp.clear(); tmp.add("1A010000000191111"); tmp.add("1A010000000190111");
							//g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 15*base);
			    			if (tmpKenshinKekka.getKekati(tmp, i).length() >= 9) {
				    			int fn = fontsize;
			    				fontsize = 4;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 15*base);
				    			fontsize = fn;
				    			f = new Font("Dialog" , Font.PLAIN, fontsize);
				    			g2d.setFont(f);
			    			} else {
					    		g2d.drawString(tmpKenshinKekka.getKekati(tmp, i) , 277 + i*55, base_y + 15*base);
			    			}

						} catch (IndexOutOfBoundsException e) {
						}
					}

				/*
				 * 受診券整理番号とページ番号
				 */
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 380, 670);
				g2d.drawString("Page.1", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}

}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

