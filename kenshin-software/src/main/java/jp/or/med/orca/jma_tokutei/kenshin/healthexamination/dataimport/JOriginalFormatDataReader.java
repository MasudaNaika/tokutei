package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * 独自フォーマットのデータを読み込むクラス
 */
public class JOriginalFormatDataReader {
	
	/**
	 * @param FileName 読み込むファイル
	 * @return 受診者ごとのリスト
	 */
	public static Vector<JOriginalFormatData> Read(String FileName) throws Exception
	{
		// 半角文字列しかくることが無いと仮定しています。
		FileReader input = new FileReader(FileName);
		
		Vector<JOriginalFormatData> ReturnItems = new Vector<JOriginalFormatData>();
		JOriginalFormatData Item = null;

		while(true)
		{
			// Bufferの中身は項目を取る前に初期化するようにする。
			char[] Buffer = new char[256];
			if(input.read(Buffer,0,2) <= 0)
			{
				break;
			}
			
			String Type = new String(Buffer).trim();
			if(Type.equals("A1"))
			{
				if(Item != null)
				{
					ReturnItems.add(Item);
				}
				Item = new JOriginalFormatData();
				
				Buffer = new char[256];
				input.skip(154);
				input.read(Buffer,0,20);
				Item.Name = new String(Buffer).trim();
				
				// ここの変換で元のデータに年の項目が2桁しかないため、
				// 不具合を起こす可能性あり。
				Buffer = new char[256];
				input.skip(6);
				input.read(Buffer,0,6);
				SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
				Date date = df.parse(new String(Buffer).trim());
				
				SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
				Item.BirthDay = df2.format(date);
				
				input.skip(68);
			}
			
			if(Type.equals("A2"))
			{
				JOriginalFormatDataBody BodyData = new JOriginalFormatDataBody();
				
				// 検査センターコード
				Buffer = new char[256];
				input.read(Buffer,0,5);
				BodyData.KensaCenterCode = new String(Buffer).trim();

				// 結果値
				Buffer = new char[256];
				input.read(Buffer,0,8);
				BodyData.Kekka = new String(Buffer).trim();
				
				Item.Body.add(BodyData);

				// ボディ部の後半部については存在しない可能性があるので、それに対する処理。
				Buffer = new char[256];
				input.skip(114);
				input.read(Buffer,0,5);
				
				if(!(new String(Buffer)).trim().equals(""))
				{
					JOriginalFormatDataBody BodyData2 = new JOriginalFormatDataBody();
					
					BodyData2.KensaCenterCode = new String(Buffer).trim();
	
					Buffer = new char[256];
					input.read(Buffer,0,8);
					BodyData2.Kekka = new String(Buffer).trim();
					
					Item.Body.add(BodyData2);
					input.skip(114);
				}else{
					input.skip(122);
				}
			}
		}

		ReturnItems.add(Item);
		
		return ReturnItems;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

