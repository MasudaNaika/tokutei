package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

/**
 * 健診項目項目入力シート（検査結果）
 */
public class KenshinKoumoku_Kensa extends JTKenshinPrint
{

	public static void main( String[] argv )
	{
		new KenshinKoumoku_Kensa();
	}
	
	  public KenshinKoumoku_Kensa(){}
	
	/**
	 * 健診項目　検査
	 * @param Graphics g, PageFormat pf, int pageIndex
	 * PrintData Keys => Kojin
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
			KenshinKoumoku_Monshin Monshin = new KenshinKoumoku_Monshin();
			Monshin.setQueryResult(printData);
			Monshin.print(g, pf, pageIndex);
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
		 * width : 451	余白25.4mm四方
		 * height: 695	余白25.4mm四方	
		 */
			final int x_w = 451;
			final int y_h = 695;
			
			/*
			 * 各項目の幅
			 */
			int base = 15;
			
			/*
			 * 個人情報部分の高さ
			 */
			int height_info = 82;
			
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
			int fontsize = 7;
			Font f = new Font("Dialog" , Font.PLAIN, fontsize);
			g2d.setFont(f);
			
			/*
			 * 追加健診項目項目数
			 * 10行程度にする
			 */
			int num_rows = (y_h - height_info - 31*base - height_middle - 10)/base;
			
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
			 * 健診項目
			 */
				base_y = height_info;
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), 31*base);

				//項目名横線
				g2d.drawLine( 10, base_y + 1*base,  75, base_y + 1*base);
				g2d.drawLine( 10, base_y + 5*base,  75, base_y + 5*base);
				g2d.drawLine( 10, base_y + 8*base,  75, base_y + 8*base);
				g2d.drawLine( 10, base_y + 11*base,  75, base_y + 11*base);
				g2d.drawLine( 10, base_y + 14*base,  75, base_y + 14*base);
				g2d.drawLine( 10, base_y + 16*base,  75, base_y + 16*base);
				g2d.drawLine( 10, base_y + 18*base,  75, base_y + 18*base);
				g2d.drawLine( 10, base_y + 22*base,  75, base_y + 22*base);
				g2d.drawLine( 10, base_y + 25*base,  75, base_y + 25*base);
				
				//中横線
				for (int i = 0;i < 30; i++){
					g2d.drawLine(75, base_y + (i+1)*base, 441, base_y + (i+1)*base);			
				}
				
				//縦
				g2d.drawLine( 75, base_y,  75, base_y + 31*base);
				g2d.drawLine(170, base_y, 170, base_y + 31*base);
				g2d.drawLine(250, base_y, 250, base_y + 31*base);
				g2d.drawLine(280, base_y, 280, base_y + 31*base);
				g2d.drawLine(320, base_y, 320, base_y + 31*base);
				g2d.drawLine(360, base_y, 360, base_y + 31*base);
				g2d.drawLine(400, base_y, 400, base_y + 31*base);
				
			/*
			 * 任意追加項目
			 */
				base_y = height_info + 31*base + height_middle;
				//枠
				g2d.drawRect( 10, base_y, (x_w-20), num_rows * base);
				
				//横
				for (int i = 0;i < num_rows; i++){
					g2d.drawLine(10, base_y + i*base, 441, base_y + i*base);			
				}
				//縦
				g2d.drawLine( 75, base_y,  75, base_y + num_rows*base);
				g2d.drawLine(170, base_y, 170, base_y + num_rows*base);
				g2d.drawLine(250, base_y, 250, base_y + num_rows*base);
				g2d.drawLine(280, base_y, 280, base_y + num_rows*base);
				g2d.drawLine(320, base_y, 320, base_y + num_rows*base);
				g2d.drawLine(360, base_y, 360, base_y + num_rows*base);
				g2d.drawLine(400, base_y, 400, base_y + num_rows*base);
		/*
		 * フォーマット
		 */
			/*
			 * 表題
			 */
				fontsize = 14;
				f = new Font("Dialog" , Font.PLAIN, fontsize);
				g2d.setFont(f);
				
				g2d.drawString("特定健康診査用入力票", 10 + (410-10)/2 - 8*fontsize/2, 27);
				
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
			 * 健診項目
			 */
				//1行目
				base_y = height_info + (base + fontsize)/2;
				g2d.drawString("区分"	, 10+(75-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("項目名"	, 75+(170-75)/2 - 3*fontsize/2, base_y);
				g2d.drawString("結果値"	, 170+(250-170)/2 - 3*fontsize/2, base_y);
				g2d.drawString("単位"	, 250+(280-250)/2 - 2*fontsize/2, base_y);
				g2d.drawString("基準値"	, 280+(320-280)/2 - 3*fontsize/2, base_y);
				g2d.drawString("判定"	, 320+(360-320)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前回"	, 360+(400-360)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回"	, 400+(441-400)/2 - 3*fontsize/2, base_y);
				
				//1列目　区分
				base_y = height_info + fontsize/2;
				g2d.drawString("身体測定"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 5*base/2 );	base_y += 5*base;
				g2d.drawString("血圧"		, 10+(75-10)/2 - 2*fontsize/2, base_y + 3*base/2 );	base_y += 3*base;
				g2d.drawString("血中脂質検査"	, 10+(75-10)/2 - 6*fontsize/2, base_y + 3*base/2 ); base_y += 3*base;
				g2d.drawString("肝機能検査"	, 10+(75-10)/2 - 5*fontsize/2, base_y + 3*base/2 ); base_y += 3*base;
				g2d.drawString("血糖検査"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 2*base/2 ); base_y += 2*base;
				g2d.drawString("尿検査"		, 10+(75-10)/2 - 3*fontsize/2, base_y + 2*base/2 ); base_y += 2*base;
				g2d.drawString("貧血検査"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 4*base/2 ); base_y += 4*base;
				g2d.drawString("心電図"		, 10+(75-10)/2 - 3*fontsize/2, base_y + 3*base/2 ); base_y += 3*base;
				g2d.drawString("眼底検査"		, 10+(75-10)/2 - 4*fontsize/2, base_y + 6*base/2 );
				
				//2列目　項目名
				String[] koumoku_name = 
						{
							"身長","体重","胸囲","BMI","収縮期血圧",
							"拡張期血圧","採血時間(食後)","中性脂肪","HDL-ｺﾚｽﾃﾛｰﾙ-","LDL-ｺﾚｽﾃﾛｰﾙ-"
							,"GOT","GTP","γ-GTP","空腹時血糖","ﾍﾓｸﾞﾛﾋﾞﾝAｌc"
							,"糖","蛋白","ヘマトクリット値","血色素量[ﾍﾓｸﾞﾛﾋﾞﾝ値]","赤血球数"
							,"貧血検査(実施理由)","心電図","心電図(所見)","心電図(実施理由)","眼底検査(ｷｰｽﾜｸﾞﾅｰ分解)"
							,"眼底検査(ｼｪｲｴ分類:H)","眼底検査(ｼｪｲｴ分類:S)","眼底検査(SCOTT分類)","眼底検査(その他の所見)","眼底検査(実施理由)"
						};
				base_y = height_info + base + (base + fontsize)/2;
				for(int i = 0;i < 30;i++){
					g2d.drawString(koumoku_name[i], 77, base_y);
					base_y += base;
				}
				
				//4列目　単位
				base_y = height_info + fontsize/2;
				g2d.drawString("cm"		, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("Kg"		, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("cm"		, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("Kg/㎡"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mmHg"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mmHg"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString(" "	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mmHg"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mg/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mg/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("U/l"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("U/l"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("U/l"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("mg/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("g/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString(" "	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString(" "	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("%"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("g/dl"	, 252, base_y + 3*base/2 ); base_y += base;
				g2d.drawString("万/mm3"	, 252, base_y + 3*base/2 ); base_y += base;

			/*
			 * 任意追加項目
			 */
				//1行目
				base_y = height_info + 31*base + height_middle + (base + fontsize)/2;
				g2d.drawString("任意追加項目"	, 10+(75-10)/2 - 6*fontsize/2, base_y - base);
				g2d.drawString("区分"		, 10+(75-10)/2 - 2*fontsize/2, base_y);
				g2d.drawString("項目名"		, 75+(170-75)/2 - 3*fontsize/2, base_y);
				g2d.drawString("結果値"		, 170+(250-170)/2 - 3*fontsize/2, base_y);
				g2d.drawString("単位"		, 250+(280-250)/2 - 2*fontsize/2, base_y);
				g2d.drawString("基準値"		, 280+(320-280)/2 - 3*fontsize/2, base_y);
				g2d.drawString("判定"		, 320+(360-320)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前回"		, 360+(400-360)/2 - 2*fontsize/2, base_y);
				g2d.drawString("前々回"		, 400+(441-400)/2 - 3*fontsize/2, base_y);
		
		/*
		 * データの移送と表示
		 */
			try{
			/*
			 * 個人情報
			 * 受診券番号、氏名、受診日、保険者番号、被保険者証等記号、被保険者証等番号
			 * 受診日は、この用紙が印刷される日付を指定する。
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

