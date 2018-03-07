package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KenshinKekka;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * 特定健診結果通知表　3ページ
 */
public class Kekka_P3 extends JTKenshinPrint
{
	public static void main( String[] argv )
	{
		new Kekka_P3();
	}
	
	public Kekka_P3(){}
	
	/**
	 * 特定健診結果通知表　ページ3
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin, KenshinKekka
	 */
	public int print( Graphics g, PageFormat pf, int pageIndex)
	{
		if( pageIndex >= 3 )
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
				base_row = 42;
				
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), base_row*base);
				
				//線
				/*add yabu 2008/03/12*/
				//g2d.drawLine(10, base_y + base, 150, base_y + base);
				g2d.drawLine(10, base_y + base, 100, base_y + base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(212, base_y + base, 441, base_y + base);
				for (int i = 1;i < base_row; i++){
					g2d.drawLine(10, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//縦
				/*add yabu 2008/03/12*/
				g2d.drawLine(100, base_y, 100, base_y + base_row*base);
				/*add yabu 2008/03/12*/
				g2d.drawLine(150, base_y, 150, base_y + base_row*base);
				g2d.drawLine(180, base_y, 180, base_y + base_row*base);
				g2d.drawLine(212, base_y, 212, base_y + base_row*base);
				g2d.drawLine(288, base_y, 288, base_y + base_row*base);
				g2d.drawLine(364, base_y, 364, base_y + base_row*base);

		/*
		 * フォーマット
		 */
			/*
			 * 健診項目
			 */
				//1行目
				base_y = height_info + (base + fontsize)/2;
				/*add yabu 2008/03/12*/
				//g2d.drawString("項目"		, 10+(110-10)/2 - 2*fontsize/2, base_y);
				//g2d.drawString("基準値"	, 150+(180-150)/2 - 3*fontsize/2, base_y + base/2);
				//g2d.drawString("単位"		, 180+(212-180)/2 - 2*fontsize/2, base_y + base/2);
				g2d.drawString("項目"		, 10+(100-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("基準値"		, 110+(180-150)/2 - 3*fontsize/2, base_y + base/2);
				g2d.drawString("単位"		, 150+(212-180)/2 - 2*fontsize/2, base_y + base/2);
				g2d.drawString("H/L"		, 180+(212-180)/2 - 2*fontsize/2, base_y + base/2);
				/*add yabu 2008/03/12*/
				g2d.drawString("今回"		, 212+(288-212)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前回"		, 288+(364-288)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回"		, 364+(441-364)/2 - 3*fontsize/2, base_y);
				
				//2行目
				base_y += base;
				/*add yabu 2008/03/12*/
				//g2d.drawString("項目名"			, 10+(110-10)/2 - 3*fontsize/2, base_y);
				g2d.drawString("項目名"			, 10+(100-10)/2 - 3*fontsize/2, base_y);
				/*add yabu 2008/03/12*/

		/*
		 * データの移送と表示
		 */
			try{
			/*
			 * 事前に印刷されていない健診項目コードを印刷する
			 * PrintDataから健診結果情報を抽出
			 */
				KenshinKekka tmpKenshinKekka = (KenshinKekka)printData.get("KenshinKekka");
			/*
			 * 個人情報
			 * PrintDataから個人情報を抽出
			 */
				Kojin tmpKojin = (Kojin)printData.get("Kojin");
				Hashtable<String, String> KojinData = tmpKojin.getPrintKojinData();
				
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
				 * 健診年月日セット
				 * 今回、前回、前々回
				 */
				try {
					base_y = height_info + (base + fontsize)/2;
					//今回
					g2d.drawString(KensaNengapi[0], 214+(290-214)/2 - 8*fontsize/2, base_y + base);
					//前回
					g2d.drawString(KensaNengapi[1], 290+(366-290)/2 - 8*fontsize/2, base_y + base);
					//前々回
					g2d.drawString(KensaNengapi[2], 366+(441-366)/2 - 8*fontsize/2, base_y + base);
				} catch (NullPointerException e) {
				}
				
				/*
				 * 項目名、基準値、単位、結果値
				 */
				base_y = height_info + base + base + (base + fontsize)/2;
				for(int i=0;i < KensaNengapi.length; i++){
					/*
					 * 項目コード抽出
					 */
			    	try {
			    		/*
			    		 * 健診項目コード一覧を取得
			    		 */
			    		Enumeration<String> e = KenshinKekkaData.get(i).keys();

				    	int j=0;
				    	while (e.hasMoreElements()){
					    	/*
					    	 * 健診項目コードを取得
					    	 */
					    	String KenshinCD = e.nextElement();
	
					    	/*
					    	 * 項目コードが印刷済みかを調べる
					    	 */
					    	boolean b =tmpKenshinKekka.checkKenshinCD(KenshinCD) && !KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI").isEmpty();
					    	if(b) {
						    	/*
						    	 * 印刷されていないので印刷する
						    	 * 1週目のみ項目名、基準値、単位を印刷
						    	 */
					    		if(i == 0){
									/*
									 * 項目名
									 * 項目名が長すぎる（１２文字以上）のときはフォントサイズを下げる
									 * 一番名代項目名（２４文字）に対応
									 * 子宮頸部細胞診 (細胞診婦人科材料)(ベセスダ分類)
									 */
					    			if (KenshinKekkaData.get(i).get(KenshinCD).get("KOUMOKU_NAME").length() >= 12) {
						    			int tmp = fontsize;
					    				fontsize = 6;
						    			f = new Font("Dialog" , Font.PLAIN, fontsize);
						    			g2d.setFont(f);
							    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KOUMOKU_NAME"), 12, base_y + j*base);
						    			fontsize = tmp;
						    			f = new Font("Dialog" , Font.PLAIN, fontsize);
						    			g2d.setFont(f);
					    			} else {
							    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KOUMOKU_NAME"), 12, base_y + j*base);
					    			}
									/*add yabu 2008/03/13*/
//									//基準値
//									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KIJYUNTI_HANI"), 152, base_y + j*base);
//									//単位
//									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("TANI"), 182, base_y + j*base);
									//基準値
									if (KojinData.get("SEX").equals("男性")){
										if(KenshinKekkaData.get(i).get(KenshinCD).get("DS_KAGEN").isEmpty()){
										}else{
											g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("DS_KAGEN")  + "～" +
													KenshinKekkaData.get(i).get(KenshinCD).get("DS_JYOUGEN"), 102, base_y + j*base);
										}
									}else if (KojinData.get("SEX").equals("女性")){
										g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("JS_KAGEN")  + "～" +
												KenshinKekkaData.get(i).get(KenshinCD).get("JS_JYOUGEN"), 102, base_y + j*base);
									}
									//単位
									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("TANI"), 152, base_y + j*base);
									//H/L
									g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("H_L"), 182, base_y + j*base);
									/*add yabu 2008/03/13*/
						    	}
								
						    	/*
						    	 * 結果値が数値かコード化を判別し、適切な結果値を表示する
						    	 */
						    	try {
						    		/*
						    		 * 判別
						    		 * 1=>数字, 2=>コード, 3=>文字列
						    		 */
						    		if (Integer.parseInt(KenshinKekkaData.get(i).get(KenshinCD).get("DATA_TYPE")) == 2) {
						    			/*
						    			 * コード
						    			 * 長すぎるときは（９文字以上）フォントサイズを下げる
						    			 */
						    			if (KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME").length() >= 9) {
							    			int tmp = fontsize;
						    				fontsize = 6;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME") , 214 + i*76, base_y + j*base);
							    			fontsize = tmp;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
						    			} else {
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME") , 214 + i*76, base_y + j*base);
						    			}
							    	} else {
							    		/*
							    		 * 数字 or 文字列
							    		 * 長すぎるときは（９文字以上）フォントサイズを下げる
							    		 */
						    			if (KenshinKekkaData.get(i).get(KenshinCD).get("CODE_NAME").length() >= 9) {
							    			int tmp = fontsize;
						    				fontsize = 6;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI") , 214 + i*76, base_y + j*base);
							    			fontsize = tmp;
							    			f = new Font("Dialog" , Font.PLAIN, fontsize);
							    			g2d.setFont(f);
						    			} else {
								    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI") , 214 + i*76, base_y + j*base);
						    			}
							    	}
						    	} catch (NumberFormatException ee){
						    		g2d.drawString(KenshinKekkaData.get(i).get(KenshinCD).get("KEKA_TI") , 214 + i*76, base_y + j*base);
						    	}
						    	j++;
					    	}
					    }
			    	} catch (IndexOutOfBoundsException err){
			    		break;
			    	}
				}
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
				g2d.drawString("Page.3", 380, 685);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		return Printable.PAGE_EXISTS;
	}

}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

