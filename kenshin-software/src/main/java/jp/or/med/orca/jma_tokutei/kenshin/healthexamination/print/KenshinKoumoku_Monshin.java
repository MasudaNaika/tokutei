package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import java.util.*;

import jp.or.med.orca.jma_tokutei.common.printer.*;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.*;

/**
 * 特定健診項目入力シート（問診）
 */
public class KenshinKoumoku_Monshin extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new KenshinKoumoku_Monshin();
	}
	
	public KenshinKoumoku_Monshin(){}
	
	/**
	 * 健診項目　問診印刷
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin
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
			//int base = 24;
			int base = 20;
			
			/*
			 * 個人情報部分の高さ
			 */
			int height_info = 82;
			
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
			 * 問診項目
			 */
				base_y = height_info;
				//枠
//				g2d.drawRect( 10, base_y, (x_w-20), 19*base);
				g2d.drawRect( 10, base_y, (x_w-20), 30*base);
				g2d.drawLine(10, base_y + (1)*base, 441, base_y + (1)*base);			
				g2d.drawLine(10, base_y + (2)*base, 441, base_y + (2)*base);			
				g2d.drawLine(10, base_y + (3)*base, 441, base_y + (3)*base);			
				g2d.drawLine(10, base_y + (4)*base, 441, base_y + (4)*base);			
				g2d.drawLine(10, base_y + (5)*base, 441, base_y + (5)*base);			
				g2d.drawLine(10, base_y + (6)*base, 441, base_y + (6)*base);			
				g2d.drawLine(10, base_y + (7)*base, 441, base_y + (7)*base);			
				g2d.drawLine(10, base_y + (8)*base, 441, base_y + (8)*base);			
				g2d.drawLine(10, base_y + (10)*base, 441, base_y + (10)*base);			
				g2d.drawLine(10, base_y + (11)*base, 441, base_y + (11)*base);			
				g2d.drawLine(10, base_y + (12)*base, 441, base_y + (12)*base);			
				g2d.drawLine(10, base_y + (13)*base, 441, base_y + (13)*base);			
				g2d.drawLine(10, base_y + (14)*base, 441, base_y + (14)*base);			
				g2d.drawLine(10, base_y + (15)*base, 441, base_y + (15)*base);			
				g2d.drawLine(10, base_y + (16)*base, 441, base_y + (16)*base);			
				g2d.drawLine(10, base_y + (17)*base, 441, base_y + (17)*base);			
				g2d.drawLine(10, base_y + (18)*base, 441, base_y + (18)*base);			
				g2d.drawLine(10, base_y + (19)*base, 441, base_y + (19)*base);			
				g2d.drawLine(10, base_y + (21)*base, 441, base_y + (21)*base);			
				//g2d.drawLine(10, base_y + (22)*base, 441, base_y + (22)*base);			
				g2d.drawLine(10, base_y + (23)*base, 441, base_y + (23)*base);			
				g2d.drawLine(10, base_y + (24)*base, 441, base_y + (24)*base);			

				g2d.drawLine(10, base_y + (29)*base, 441, base_y + (29)*base);			
				g2d.drawLine(10, base_y + (30)*base, 441, base_y + (30)*base);			

				//中横線
//				for (int i = 0;i < 18; i++){
//					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
//				}
				
				//縦
				g2d.drawLine( 25, base_y + base,  25, base_y + 30*base);
				g2d.drawLine(340, base_y, 340, base_y + 30*base);
		
		/*
		 * フォーマット
		 */	
			/*
			 * 表題
			 */
				fontsize = 14;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				
				g2d.drawString("特定健康診査質問票", 10 + (410-10)/2 - 7*fontsize/2, 27);
				
				fontsize = 7;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
			/*
			 * 個人情報
			 */
				//一列目
				g2d.drawString("受診券番号", 10, 50);
				g2d.drawLine(10, 52, 110, 52);
				g2d.drawString("氏名", 120, 50);
				g2d.drawLine(120, 52, 280, 52);
				g2d.drawString("受診日", 290, 50);
				g2d.drawLine(290, 52, 441, 52);
				
				//二列目
				g2d.drawString("保険者番号", 10, 68);
				g2d.drawLine(10, 70, 110, 70);
				g2d.drawString("被保険者証等記号", 120, 68);
				g2d.drawLine(120, 70, 280, 70);
				g2d.drawString("被保険者証等番号", 290, 68);
				g2d.drawLine(290, 70, 441, 70);
				
			/*
			 * 問診項目
			 */
				//1行目
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("問診内容"	, 10+(340-10)/2 - 4*fontsize/2, base_y);
				g2d.drawString("選択肢"	, 340+(441-340)/2 - 3*fontsize/2, base_y);
				
				
				//2列目
				base_y = height_info + base + (base + fontsize)/2;
				String[][] koumoku_name = {
							 {"1-1"	, "血圧を下げる薬を服用している。"}
							,{"1-2"	, "インスリン注射又は血糖を下げる薬を使用している。"}
							,{"1-3"	, "コレステロールを下げる薬を服用している。"}
							,{"4"	, "医師から、脳卒中（脳出血、脳梗塞等）にかかっているといわれたり、治療を受けたことがある。"}
							,{"5"	, "医師から、心臓病（狭心症、心筋梗塞）にかかっているといわれたり、治療を受けたことがある。"}
							,{"6"	, "医師から、慢性の腎不全にかかっているといわれたり、治療（人工透析）を受けたことがある。"}
							,{"7"	, "医師から、貧血といわれたことがある。"}
							,{"8"	, "現在、たばこを習慣的に吸っている。"}
							,{"9"	, "２０歳の時の体重から１０キロ以上増加している。"}
							,{"10"	, "１回３０分以上の軽く汗をかく運動を週２日以上、１年以上実施している。"}
							,{"11"	, "日常生活において歩行又は同等の身体活動を１日１時間以上実施している。"}
							,{"12"	, "ほぼ同世代の同姓と比較して歩く速度が速い。"}
//masuda^ 2018/03/02
                                                        ,{"13"	, "食事をかんで食べる時の状態はどれにあてはまりますか。"}
                                                        //,{"13"	, "この１年間で体重の増減が±３kg以上ある。"}
//masuda$
							,{"14"	, "人と比較して食べる速度が速い。"}
							,{"15"	, "就寝前の２時間以内に夕食をとることが週に３回以上ある。"}
//masuda^ 2018/03/02
							,{"16"	, "朝昼夕の３食以外に間食や甘い飲み物を摂取していますか。"}
                                                        //,{"16"	, "夕食後に間食（３食以外の夜食）をとることが週に３回以上ある。"}
//masuda$
							,{"17"	, "朝食を抜くことが週に３回以上ある。"}
							,{"18"	, "お酒（清酒、焼酎、ビール、洋酒など）を飲む頻度。"}
							,{"19"	, "飲酒日の１日あたりの飲酒量。"}
							,{"20"	, "睡眠で休養が得られている。"}
							,{"21"	, "運動や食生活等の生活習慣を改善してみようと思いますか。"}
							,{"22"	, "生活習慣の改善について保健指導を受ける機会があれば利用しますか。"}
						};
				int base_k = height_info + base + (base + fontsize)/4;
				String[][] koumoku_komoji = {
						{""	, "(※ 「現在、習慣的に喫煙している者」とは「合計100本以上、又は６ヶ月以上吸っている者」"}
						,{""	, "であり、最近１ヶ月も吸っている者）"}
						,{""	, "清酒一合（180ml）の目安：ビール中瓶１本（約500ml）、焼酎35度（80ml）、"}
						,{""	, "ウイスキーダブル一杯（60ml）、ワイン2杯（240ml）"}
						,{""	, ""} //ブランク用
				};
				g2d.drawString(koumoku_name[0][0], 10+ 2 , base_y);
				g2d.drawString(koumoku_name[0][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[1][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[1][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[2][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[2][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[3][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[3][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[4][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[4][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[5][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[5][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[6][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[6][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[7][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[7][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_komoji[0][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[0][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_komoji[1][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[1][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_name[8][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[8][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[9][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[9][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[10][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[10][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[11][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[11][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[12][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[12][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[13][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[13][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[14][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[14][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[15][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[15][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[16][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[16][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[17][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[17][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_komoji[4][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_komoji[4][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_name[18][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[18][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_komoji[2][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[2][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_komoji[3][0], 10+ 2 , base_y+=base/2);
				g2d.drawString(koumoku_komoji[3][1], 25+ 2 , base_y);

				g2d.drawString(koumoku_name[19][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[19][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[20][0], 10+ 2 , base_y+=base);
				g2d.drawString(koumoku_name[20][1], 25+ 2 , base_y);
				g2d.drawString(koumoku_name[21][0], 10+ 2 , base_y+=base*5);
				g2d.drawString(koumoku_name[21][1], 25+ 2 , base_y);
//				for(int i = 0;i < 19;i++){
//					g2d.drawString(koumoku_name[i][0], 10+ 2 , base_y);
//					g2d.drawString(koumoku_name[i][1], 25+ 2 , base_y);
//					base_y += base;
//				}
				
				//3列目
//				base_y = height_info + base + (base + fontsize)/2;
//				String[][] koumoku_select = {
//						 	 {"1-1"	, "1:服薬あり　　2:服薬なし"}
//							,{"1-2"	, "1:服薬あり　　2:服薬なし"}
//							,{"1-3"	, "1:服薬あり　　2:服薬なし"}
//							,{"4"	, "1:はい　　2:いいえ"}
//							,{"5"	, "1:はい　　2:いいえ"}
//							,{"6"	, "1:はい　　2:いいえ"}
//							,{"7"	, "1:はい　　2:いいえ"}
//							,{"8"	, "1:はい　　2:いいえ"}
//							,{"9"	, "1:はい　　2:いいえ"}
//							,{"10"	, "1:はい　　2:いいえ"}
//							,{"11"	, "1:はい　　2:いいえ"}
//							,{"12"	, "1:はい　　2:いいえ"}
//							,{"13"	, "1:速い　2:ふつう　3:遅い"}
//							,{"14"	, "1:はい　　2:いいえ"}
//							,{"15"	, "1:はい　　2:いいえ"}
//							,{"16"	, "1:はい　　2:いいえ"}
//							,{"17"	, "1:はい　　2:いいえ"}
//							,{"18"	, "1:はい　　2:いいえ"}
//						};				
				base_y = height_info + base + (base + fontsize)/2;
				String[][] koumoku_select_1 = {
						 	 {"1-1"	, "1:服薬あり　　2:服薬なし"}
							,{"1-2"	, "1:服薬あり　　2:服薬なし"}
							,{"1-3"	, "1:服薬あり　　2:服薬なし"}
							,{"4"	, "1:はい　　2:いいえ"}
							,{"5"	, "1:はい　　2:いいえ"}
							,{"6"	, "1:はい　　2:いいえ"}
							,{"7"	, "1:はい　　2:いいえ"}
							,{"8"	, "1:はい　　2:いいえ"}
						};				
				base_y = height_info + base + (base + fontsize)/2;
				String[][] koumoku_select_2 = {
							{"9"	, "1:はい　　2:いいえ"}
							,{"10"	, "1:はい　　2:いいえ"}
							,{"11"	, "1:はい　　2:いいえ"}
//masuda^ 2018/03/02                   
							//,{"12"	, "1:速い　2:ふつう　3:遅い"}
                                                        //,{"13"	, "1:はい　　2:いいえ"}
							//,{"14"	, "1:はい　　2:いいえ"}
                                        
                                                        ,{"12"	, "1:はい　2:いいえ"}
                                                        ,{"13"  , "1:何でもかんで食べることが"}
                                                        ,{""    , "　できる"}
                                                        ,{""    , "2:歯や歯ぐき、かみあわせな"}
                                                        ,{""    , "　ど気になる部分があり、か"}
                                                        ,{""    , "　みにくいことがある"}
                                                        ,{""    , "3:ほとんどかめない"}
                                                        ,{"14"	, "1:速い　2:ふつう　3:遅い"}
							,{"15"	, "1:はい　　2:いいえ"}
                                        
							//,{"16"	, "1:はい　　2:いいえ"}
                                                        ,{"16"	, "1:毎日　2:時々"}
                                                        ,{""   , "3:ほとんど摂取しない"}
//masuda$
							,{"17"	, "1:はい　　2:いいえ"}
							,{"18"	, "1:はい　　2:いいえ"}
						};				
				int base_3 = 0;//三つ目の位置
				base_3 = height_info + base + (base + fontsize);
				String[][] koumoku_select_3 = {
							{"19"	, "1:毎日　　2:時々"}
							,{""	, "3:ほとんど飲まない"}
							,{"20"	, "1:1合未満   2:1～2合未満"}
							,{""	, "3:2～3合未満 　4:3合以上"}
							,{"22"	, "1:はい　　2:いいえ"}
							,{"23"	, "1:改善するつもりはない"}
							,{""	, "2:改善するつもりである"}
							,{""	, " （概ね６ヶ月以内）"}
							,{""	, "3:近いうちに(概ね１ヶ月"}
							,{""	, " 以内)改善するつもりであり､"}
							,{""	, " 少しずつ始めている"}
							,{""	, "4:既に改善に取り組んでいる"}
							,{""	, "　（６ヶ月未満）"}
							,{""	, "5:既に改善に取り組んでいる"}
							,{""	, "　（６ヶ月以上）"}
							,{""	, " "}//ブランク用(15)
					};				
//				for(int i = 0;i < 18;i++){
//				g2d.drawString(koumoku_select[i][1], 347 , base_y);
//				base_y += base;
//			}
				int base_2 = 0;//二つ目の位置
				for(int i = 0;i < 8;i++){
					g2d.drawString(koumoku_select_1[i][1], 347 , base_y);
					base_y += base;
					base_2 = base;
				}
				base_2 = base_2 + base_y;
// masuda TO-DO need to change
				for(int i = 0;i < 9;i++){
					g2d.drawString(koumoku_select_2[i][1], 347 , base_2);
					base_2 += base;
					base_3 = base_2;
				}
				g2d.drawString(koumoku_select_3[0][1], 347 , base_3);
				g2d.drawString(koumoku_select_3[1][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base);//ブランク
				g2d.drawString(koumoku_select_3[2][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[3][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base);//ブランク
				g2d.drawString(koumoku_select_3[4][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base/2);//ブランク
				g2d.drawString(koumoku_select_3[5][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[6][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[7][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[8][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[9][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[11][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[12][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[13][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[14][1], 347 , base_3+=base/2);
				g2d.drawString(koumoku_select_3[15][1], 347 , base_3+=base/2);//ブランク
				g2d.drawString(koumoku_select_3[4][1], 347 , base_3+=base/2);
					
		/*
		 * データの移送と表示
		 */	
			try{
			/*
			 * 個人情報
			 * 受診券番号　　氏名　　健診実施日　　保険者番号　　被保険者証等記号　　被保険者証等番号
			 * 受診日はこの用紙が印刷される日付を指定する。
			 */
				/*
				 * PrintDataから個人情報を抽出
				 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
				//一列目
				//受診券番号
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 50, 50);
				//氏名
				g2d.drawString(KojinData.get("KANANAME"), 180, 50);
				//受診日
				g2d.drawString(KojinData.get("KENSA_NENGAPI"), 360, 50);
					
				//二列目
				//保険者番号
				g2d.drawString(KojinData.get("HKNJANUM"), 50, 68);
				//被保険者証等記号
				g2d.drawString(KojinData.get("HIHOKENJYASYO_KIGOU"), 180, 68);
				//被保険者証等番号
				g2d.drawString(KojinData.get("HIHOKENJYASYO_NO"), 360, 68);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

