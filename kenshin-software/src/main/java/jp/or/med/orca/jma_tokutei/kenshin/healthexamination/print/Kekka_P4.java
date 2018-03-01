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

public class Kekka_P4 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P4();
	}
	
	public Kekka_P4(){}
	
	/**
	 * 特定健診結果通知表　ページ4
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{
		if( pageIndex >= 4 )
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
			int base = 24;
			
			/*
			 * 個人情報部分の高さ
			 */
			int height_info = 20;
			
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
				/* yabu 2008/03/12*/
//				g2d.drawRect( 10, base_y, (x_w-20), 19*base);
				g2d.drawRect( 10, base_y, (x_w-20), 26*base);
				/* yabu 2008/03/12*/

				//中横線
				/* yabu 2008/03/12*/
				for (int i = 0;i < 26; i++){
				//for (int i = 0;i < 18; i++){
					if (i==4||i==6||i==8){
					}else{
						g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
					}
				}
				/* yabu 2008/03/12*/
				
				//縦
				/* yabu 2008/03/12*/
//				g2d.drawLine( 25, base_y + base,  25, base_y + 19*base);
//				g2d.drawLine(351, base_y, 351, base_y + 19*base);
//				g2d.drawLine(381, base_y, 381, base_y + 19*base);
//				g2d.drawLine(411, base_y, 411, base_y + 19*base);
				g2d.drawLine( 25, base_y + base,  25, base_y + 26*base);
				g2d.drawLine(266, base_y, 266, base_y + 26*base);
				g2d.drawLine(326, base_y, 326, base_y + 26*base);
				g2d.drawLine(381, base_y, 381, base_y + 26*base);
				/* yabu 2008/03/12*/

		/*
		 * フォーマット
		 */
			/*
			 * 問診項目
			 */
				//1行目
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("問診内容"	, 10+(266-10)/2 - 4*fontsize/2, base_y);
				g2d.drawString("今回"	, 266+(326-266)/2  - 2*fontsize/2, base_y);
				g2d.drawString("前回"	, 326+(381-326)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回"	, 381+(441-381)/2 - 3*fontsize/2, base_y);
				
//				g2d.drawString("問診内容"	, 10+(351-10)/2 - 4*fontsize/2, base_y);
//				g2d.drawString("今回"	, 351+(381-351)/2 - 2*fontsize/2, base_y);
//				g2d.drawString("前回"	, 381+(411-381)/2 - 2*fontsize/2, base_y);
//				g2d.drawString("前々回"	, 411+(441-411)/2 - 3*fontsize/2, base_y);
				
				
				/* yabu 2008/03/12*/
				/* 7.貧血、19.飲酒量、21.生活習慣改善、22.保健指導をついか*/
				/* 上記の4項目追加により項目番号修正*/
				//2列目
				String[][] koumoku_name = {
							 {"1-1"	, "血圧を下げる薬を服用している。"}
							,{"1-2"	, "インスリン注射又は血糖を下げる薬を使用している。"}
							,{"1-3"	, "コレステロールを下げる薬を服用している。"}
							,{"4"	, "医師から、脳卒中（脳出血、脳梗塞等）にかかっているといわれたり、"}
							,{""	, "治療を受けたことがある。"}
							,{"5"	, "医師から、心臓病（狭心症、心筋梗塞）にかかっているといわれたり、"}
							,{""	, "治療を受けたことがある。"}
							,{"6"	, "医師から、慢性の腎不全にかかっているといわれたり、"}
							,{""	, "治療（人工透析）を受けたことがある。"}
							,{"7"	, "医師から貧血といわれたことがある。"}
							,{"8"	, "現在、たばこを習慣的に吸っている。"}
							,{"9"	, "２０歳の時の体重から１０キロ以上増加している。"}
							,{"10"	, "１回３０分以上の軽く汗をかく運動を週２日以上、１年以上実施している。"}
							,{"11"	, "日常生活において歩行又は同等の身体活動を１日１時間以上実施している。"}
							,{"12"	, "同世代の同姓と比較して歩く速度が速い。"}
							,{"13"	, "この１年間で体重の増減が±３kg以上ある。"}
							,{"14"	, "早食い・ドカ食い・ながら食いが多い。"}
							,{"15"	, "就寝前の２時間以内に夕食をとることが週に３回以上ある。"}
							,{"16"	, "夜食や間食が多い。"}
							,{"17"	, "朝食を抜くことが多い。"}
							,{"18"	, "ほぼ毎日アルコール飲料を飲む。"}
							,{"19"	, "飲酒する日の1日あたりの飲酒量。"}
							,{"20"	, "睡眠で休養が得られている。"}
							,{"21"	, "運動や食生活等の生活習慣を改善してみようかと思いますか。"}
							,{"22"	, "生活習慣の改善について保健指導を受ける機会があれば、利用しますか。"}
						};
				/* yabu 2008/03/12*/
				
				base_y = height_info + base + (base + fontsize)/2;
				
				/* yabu 2008/03/12*/
				//for(int i = 0;i < 18;i++){
				for(int i = 0;i < 25;i++){
				/* yabu 2008/03/12*/
					g2d.drawString(koumoku_name[i][0], 10+ 2 , base_y);
					g2d.drawString(koumoku_name[i][1], 25+ 2 , base_y);
					base_y += base;
				}
		
		/*
		 * データの移送と表示
		 */
			try{
			/*
			 * 問診結果一覧を印刷する
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
				 * 健診年月日ごとに出力
				 */
				/* yabu 2008/03/12*/
				/* 7.貧血、19.飲酒量、21.生活習慣改善、22.保健指導を追加*/
				/* 上記の4項目追加により項目番号修正*/
				base_y = height_info + base + (base + fontsize)/2;
				for (int i=0;i < KensaNengapi.length; i++){
					int j=0;
					try {
						//1-1 血圧を下げる薬を服用している
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N701000000000011"), 268 + i*30, base_y + j*base); j++;
						//1-2 インスリン注射又は血糖を下げる薬を使用している
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N706000000000011"), 268 + i*30, base_y + j*base); j++;
						//1-3 コレステロールを下げる薬を服用している
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N711000000000011"), 268 + i*30, base_y + j*base); j++;
						//4 医師から、脳卒中（脳出血、脳梗塞等）にかかっているといわれたり、治療を受けたことがある
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N716000000000011"), 268 + i*30, base_y + j*base); j++;
						//5 医師から、心臓病（狭心症、心筋梗塞）にかかっているといわれたり、治療を受けたことがある
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N721000000000011"), 268 + i*30, base_y +base+ j*base); j++;
						j++;
						//6 医師から、慢性の腎不全にかかっているといわれたり、治療（人工透析）を受けたことがある
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N726000000000011"), 268 + i*30, base_y +base+ j*base); j++;
						j++;
						//7 医師から貧血といわれたことがある
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N731000000000011"), 268 + i*30, base_y + j*base); j++;
						j++;
						//8 現在、たばこを習慣的に吸っている
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N736000000000011"), 268 + i*30, base_y + j*base); j++;
						//9 ２０歳の時の体重から１０キロ以上増加している
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N741000000000011"), 268 + i*30, base_y + j*base); j++;
						//10 １回３０分以上の軽く汗をかく運動を週２日以上、１年以上実施している
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N746000000000011"), 268 + i*30, base_y + j*base); j++;
						//11 日常生活において歩行又は同等の身体活動を１日１時間以上実施している
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N751000000000011"), 268 + i*30, base_y + j*base); j++;
						//12 同世代の同姓と比較して歩く速度が速い
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N756000000000011"), 268 + i*30, base_y + j*base); j++;
						//13 この１年間で体重の増減が±３kg以上ある
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N761000000000011"), 268 + i*30, base_y + j*base); j++;
						//14 早食い・ドカ食い・ながら食いが多い
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N766000000000011"), 268 + i*30, base_y + j*base); j++;
						//15 就寝前の２時間以内に夕食をとることが週に３回以上ある
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N771000000000011"), 268 + i*30, base_y + j*base); j++;
						//16 夜食や間食が多い
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N776000000000011"), 268 + i*30, base_y + j*base); j++;
						//17 朝食を抜くことが多い
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N781000000000011"), 268 + i*30, base_y + j*base); j++;
						//18 ほぼ毎日アルコール飲料を飲む
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N786000000000011"), 268 + i*30, base_y + j*base); j++;
						//19 飲酒日の1日あたりの飲酒量
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N791000000000011"), 268 + i*30, base_y + j*base); j++;
						//20 睡眠で休養が得られている
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N796000000000011"), 268 + i*30, base_y + j*base); j++;
						//21 運動や生活活動の生活習慣を改善してみようと思いますか
						//g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N801000000000011"), 268 + i*30, base_y + j*base); j++;
		    			if (tmpKenshinKekka.getMonshin(i,"9N801000000000011").length() >= 9) {
			    			int tmp = fontsize;
		    				fontsize = 6;
			    			f = new Font("Dialog" , Font.PLAIN, fontsize);
			    			g2d.setFont(f);
				    		g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N801000000000011"), 268 + i*30, base_y + j*base); j++;
			    			fontsize = tmp;
			    			f = new Font("Dialog" , Font.PLAIN, fontsize);
			    			g2d.setFont(f);
		    			} else {
				    		g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N801000000000011"), 268 + i*30, base_y + j*base); j++;
		    			}
						//22 生活習慣病の改善について保健指導を受ける機会があれば、利用しますか
						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N806000000000011"), 268 + i*30, base_y + j*base); j++;

//						//1-1 血圧を下げる薬を服用している
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N701000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//1-2 インスリン注射又は血糖を下げる薬を使用している
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N706000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//1-3 コレステロールを下げる薬を服用している
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N711000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//4 医師から、脳卒中（脳出血、脳梗塞等）にかかっているといわれたり、治療を受けたことがある
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N716000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//5 医師から、心臓病（狭心症、心筋梗塞）にかかっているといわれたり、治療を受けたことがある
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N721000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//6 医師から、慢性の腎不全にかかっているといわれたり、治療（人工透析）を受けたことがある
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N726000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//7 医師から貧血といわれたことがある
//						g2d.drawString(tmpKenshinKekka.getMonshin(i,"9N731000000000011"), 352 + i*30, base_y + j*base); j++;
//						//8 現在、たばこを習慣的に吸っている
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N736000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//9 ２０歳の時の体重から１０キロ以上増加している
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N741000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//10 １回３０分以上の軽く汗をかく運動を週２日以上、１年以上実施している
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N746000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//11 日常生活において歩行又は同等の身体活動を１日１時間以上実施している
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N751000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//12 同世代の同姓と比較して歩く速度が速い
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N756000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//13 この１年間で体重の増減が±３kg以上ある
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N761000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//14 早食い・ドカ食い・ながら食いが多い
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N766000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//15 就寝前の２時間以内に夕食をとることが週に３回以上ある
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N771000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//16 夜食や間食が多い
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N776000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//17 朝食を抜くことが多い
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N781000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//18 ほぼ毎日アルコール飲料を飲む
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N786000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//19 飲酒日の1日あたりの飲酒量
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N791000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//20 睡眠で休養が得られている
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N796000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//21 運動や生活活動の生活習慣を改善してみようと思いますか
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N801000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;
//						//22 生活習慣病の改善について保健指導を受ける機会があれば、利用しますか
//						g2d.drawString(tmpKenshinKekka.chkNull(KenshinKekkaData.get(i).get("9N806000000000011").get("CODE_NAME")), 352 + i*30, base_y + j*base); j++;

					} catch (IndexOutOfBoundsException e) {
						
					}
				}
				/* yabu 2008/03/12*/

			/*
			 * 個人情報
			 * PrintDataから個人情報を抽出
			 */	
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
				/*
				 * 受診券整理番号とページ番号
				 */
				g2d.drawString(KojinData.get("JYUSHIN_SEIRI_NO"), 380, 670);
				g2d.drawString("Page.4", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

