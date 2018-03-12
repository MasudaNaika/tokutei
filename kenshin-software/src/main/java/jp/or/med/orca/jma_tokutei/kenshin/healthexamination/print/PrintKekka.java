package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.hl7.common.Utility;
import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.AddMedical;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KenshinKekka;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import org.apache.log4j.Logger;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PrintKekka extends JTKenshinPrint {
//	private final static String PATH_FILE_PROPERTIES = "file.properties";

	/* 改行文字 */
//	public static String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");

	private static org.apache.log4j.Logger logger = Logger.getLogger(PrintKekka.class);

	private String templatePath = ""; // ディレクトリパス
	private String outputPath = ""; // ディレクトリパス
	private String infilename = "inKekkaTemplate.pdf";
	private String outfilename = "outKekkaTemplate.pdf";
	// add s.inoue 2009/12/25
	private String templatePath_1 = ""; // ディレクトリパス
	private String templatePath_2 = ""; // ディレクトリパス
	private String infilename_single = "inKekkaTemplate_1.pdf";
	private String infilename_double = "inKekkaTemplate_2.pdf";

	private String kensaNengapiLast = "";
	private String kensaNengapiLast2 = "";
	private String kensaNengapiLast3 = "";
	// edit s.inoue 2009/12/25
	// 印刷方法
	private int printSelect;

	File printFile = null;
	//	 add s.inoue 20080904
	// 実施区分
	private static final String KEKKA_OUTPUT_JISI_KBN_0 = "未実施";
	private static final String KEKKA_OUTPUT_JISI_KBN_2 = "測定不可能";

	KenshinKekka kenshinKekka = new KenshinKekka();
	AddMedical addMedical = new AddMedical();
	PrintHitokutei printHitokutei = new PrintHitokutei();

	/* Added 2008/05/08 若月  */
	/* --------------------------------------------------- */
	private static final HashMap<String, String> TANI_MAP = createTaniMap();
	private static HashMap<String, String> createTaniMap(){
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("{times}", "回");
		map.put("mm[Hg]", "mmHg");
		map.put("{h`b}/min", "拍／分");
		map.put("mg/dL", "mg/dl");
		map.put("U/L", "U/l");
		map.put("g/dL", "g/dl");
		map.put("ng/mL", "ng/ml");
		map.put("10*4/mm3", "万/mm3");
		map.put("fl", "ｆｌ");
		map.put("L", "ｌ");

		return map;
	}

	String ishiName = "";

	PrinterJob job = PrinterJob.getPrinterJob();

	public File printKekka() {
		// eidt s.inoue 2011/04/12
		// PropertyUtil util = new PropertyUtil(JPath.PATH_FILE_PROPERTIES);
		// templatePath = util.getProperty("filePath");
		templatePath = "./work/PDF/";

		// edit s.inoue 2009/12/25
		if (this.printSelect == 2){
			templatePath_1 = templatePath + infilename_single;
			templatePath_2 = templatePath + infilename_double;
		}else{
			templatePath = templatePath + infilename;
		}

		outputPath = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename;

		File outfile = new File("."+File.separator+"Data"+File.separator+"PDF");
		outfile.mkdirs();

		File tmpDir = new File("."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp");
		tmpDir.mkdirs();

		PdfStamper stamp = null;

		/*
		 * 健診機関情報 PrintDataから健診機関情報を抽出
		 */
		Kikan tmpKikan = (Kikan) printData.get("Kikan");
		Hashtable<String, String> KikanData = tmpKikan.getPrintKikanData();

		/*
		 * 個人情報 PrintDataから個人情報を抽出
		 */
		Kojin tmpKojin = (Kojin) printData.get("Kojin");
		Hashtable<String, String> kojinData = tmpKojin.getPrintKojinData();

		String uketukeId = kojinData.get("UKETUKE_ID");
		String kensaNengapi = kojinData.get("KENSA_NENGAPI");

		// add s.inoue 2009/10/15
		// 日付が無い場合は無効にする
		if (kensaNengapi == null) {
			return null;
		}

		/*
		 * 1page目の特定健康診査受診結果を印刷
		 * 医師の氏名を取得するため暫定的に起動する。
		 */
		// move s.inoue 2010/05/11
		List ketuatuHL_List = new ArrayList();
		List KetyuHL_List = new ArrayList();
		List kankinouHL_List = new ArrayList();
		List KetouHL_List = new ArrayList();
		List NyoHL_List = new ArrayList();
		ketuatuHL_List.clear();
		KetyuHL_List.clear();
		kankinouHL_List.clear();
		KetouHL_List.clear();
		NyoHL_List.clear();

		List ketuatuVal_List = new ArrayList();
		List KetyuVal_List = new ArrayList();
		List kankinouVal_List = new ArrayList();
		List KetouVal_List = new ArrayList();
		List NyoVal_List = new ArrayList();
		ketuatuVal_List.clear();
		KetyuVal_List.clear();
		kankinouVal_List.clear();
		KetouVal_List.clear();
		NyoVal_List.clear();

		create1Page( stamp, KikanData, kojinData, uketukeId, kensaNengapi,0,
				ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
				ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List);

		int jugePdfUnit = 0;//PDFを結合するか判定

		//非特定健診ページ追加
		List filePathList = new ArrayList();//非特定健診のファイルパスのリスト
		List kensaNenList = new ArrayList();//検査年月日のリスト
		if(kensaNengapiLast!=null){
			kensaNenList.add(kensaNengapiLast);
		}
		if(kensaNengapiLast2!=null){
			kensaNenList.add(kensaNengapiLast2);
		}
		if(kensaNengapiLast3!=null){
			kensaNenList.add(kensaNengapiLast3);
		}

		// edit s.inoue 2009/12/29
		List pdfPathSplitList = new ArrayList();
		List pdfPathHitokuteiList = new ArrayList();

		// edit s.inoue 2010/01/05
		pdfPathHitokuteiList = printHitokutei.startAddMedical(kensaNenList,kojinData,KikanData,ishiName,printSelect);

		if (printSelect == 2){
			filePathList.add(
					create2Page(stamp, KikanData, kojinData, uketukeId, kensaNengapi,pdfPathHitokuteiList.size()+printSelect,
							ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
							ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List).get(0));
		}
		for (int i = 0; i < pdfPathHitokuteiList.size(); i++) {
			filePathList.add(pdfPathHitokuteiList.get(i));
		}

		/*
		 * 1page目の特定健康診査受診結果を印刷
		 */
		create1Page( stamp, KikanData, kojinData, uketukeId, kensaNengapi,filePathList.size(),
				ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
				ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List);
		// ファイル結合
		unitPdf(jugePdfUnit,filePathList);

		// 印刷実施した結果通知日を登録
		try {
			JApplication.kikanDatabase.Transaction();
			// フォーマット変換
			String y = kensaNengapi.substring(0,4);
			String m = kensaNengapi.substring(5,7);
			String d = kensaNengapi.substring(8,10);
			String kensaNen = y + m +d;
			JApplication.kikanDatabase.sendNoResultQuery(
			"UPDATE T_KENSAKEKA_TOKUTEI SET " +
			"TUTI_NENGAPI = " + JQueryConvert.queryConvert(Utility.NowDate()) +
			" WHERE UKETUKE_ID = " + JQueryConvert.queryConvert(uketukeId) +
			" AND KENSA_NENGAPI = " + JQueryConvert.queryConvert(kensaNen));

			JApplication.kikanDatabase.Commit();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try{
				JApplication.kikanDatabase.rollback();
			}catch(Exception exr){
				exr.printStackTrace();
			}
		} catch(NumberFormatException nex){
			nex.printStackTrace();
		}

		return printFile;
	}

//	// add s.inoue 2009/12/25
//	private List create2Page(
//			AcroFields form,
//			KenshinKekka tmpKenshinKekka,
//			ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData,
//			String[] kensaNengapiAll,
//			Hashtable<String, Hashtable<String, String>> dataItemMap,
//			List ketuatuHL_List,
//			List KetyuHL_List,
//			List kankinouHL_List,
//			List KetouHL_List,
//			List NyoHL_List,
//			List ketuatuVal_List,
//			List KetyuVal_List,
//			List kankinouVal_List,
//			List KetouVal_List,
//			List NyoVal_List
//	) throws Exception{
//
//		List pdfPathList = new ArrayList();
//
//		try {
//			// edit s.inoue 2010/01/05
//			PdfReader reader = new PdfReader(PrintDefine.WORK_PDF_DUMMY2_PDF);
//
//			// edit s.inoue 2009/12/29
//			FileOutputStream fileOutputStream = new FileOutputStream(templatePath_2 + this.printSelect);
//			// FileOutputStream fileOutputStream = new FileOutputStream(templatePath_2);
//
//			// edit s.inoue 2009/12/29
//			// PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(templatePath_2 + this.printSelect));
//			PdfStamper stamp = new PdfStamper(reader, fileOutputStream);
//
//			// del s.inoue 2010/01/05
//			// AcroFields form = stamp.getAcroFields();
//
//			/* ヘッダー情報出力 */
//			this.printKikanData(KikanData, form);
//
//			create2PageSetting(form, tmpKenshinKekka,kenshinKekkaData,kensaNengapiAll,dataItemMap,
//					ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
//					ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List);
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//			fileOutputStream.close();
//		}
//		catch (Exception e){
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//		// edit s.inoue 2010/01/05
//		 pdfPathList.add(templatePath_2 + this.printSelect);
//		return pdfPathList;
//	}
	/**
	 * 特定健康診査受診結果(右ページ)を作成する　
	 *
	 * @param stamp
	 * @param KikanData
	 * @param kojinData
	 * @param uketukeId
	 * @param kensaNengapi
	 * @return
	 */
	private List create2Page(PdfStamper stamp, Hashtable<String, String> KikanData,
			Hashtable<String, String> kojinData,String uketukeId, String kensaNengapi,int maxPage,
			List ketuatuHL_List,List KetyuHL_List,List kankinouHL_List,List KetouHL_List,List NyoHL_List,
			List ketuatuVal_List,List KetyuVal_List,List kankinouVal_List,List KetouVal_List,List NyoVal_List
			) {

		Map<String,ArrayList<String>>ResultMap  = new HashMap<String,ArrayList<String>>();
// del s.inoue 2010/05/11
//		List ketuatuHL_List = new ArrayList();
//		List KetyuHL_List = new ArrayList();
//		List kankinouHL_List = new ArrayList();
//		List KetouHL_List = new ArrayList();
//		List NyoHL_List = new ArrayList();
//		ketuatuHL_List.clear();
//		KetyuHL_List.clear();
//		kankinouHL_List.clear();
//		KetouHL_List.clear();
//		NyoHL_List.clear();
//
//		List ketuatuVal_List = new ArrayList();
//		List KetyuVal_List = new ArrayList();
//		List kankinouVal_List = new ArrayList();
//		List KetouVal_List = new ArrayList();
//		List NyoVal_List = new ArrayList();
//		ketuatuVal_List.clear();
//		KetyuVal_List.clear();
//		kankinouVal_List.clear();
//		KetouVal_List.clear();
//		NyoVal_List.clear();

		List pdfPathList = new ArrayList();

		try {
			if (KikanData == null) {
				System.out.println("KikanData is null.");
				return null;
			}

			// ファイル作成
			// edit s.inoue 2009/12/25
			String temppath_1 ="";
			String temppath_2 ="";
			if (printSelect == 2){
				temppath_1 = templatePath_1;
				temppath_2 = templatePath_2;
			}else{
				temppath_1 = templatePath;
			}

			// PdfReader reader = new PdfReader(temppath_1);
			PdfReader reader = new PdfReader(PrintDefine.WORK_PDF_DUMMY2_PDF);

			/*
			kensaNengapi = kensaNengapi.replaceAll("年", "");
			kensaNengapi = kensaNengapi.replaceAll("月", "");
			kensaNengapi = kensaNengapi.replaceAll("日", "");

			StringBuffer buffer = new StringBuffer();
			buffer.append("."+File.separator+"Data"+File.separator+"PDF"+File.separator+"kekka");
			buffer.append( uketukeId );
			buffer.append( "-" );
			buffer.append( kensaNengapi );

			// 同じ受付ID、健診年月日のファイルを開けるようにする。
			Calendar cal = Calendar.getInstance();
			buffer.append( "-" );
			buffer.append( cal.getTimeInMillis() );

			buffer.append(".pdf");
			String kekkaFilePath = buffer.toString();

			// 2枚目追加の為1枚目は、名前をdummyで作成する。
			FileOutputStream fileOutputStream = new FileOutputStream(PrintDefine.WORK_PDF_TMP_DUMMY_PDF);
 			*/
			FileOutputStream fileOutputStream = new FileOutputStream(templatePath_2 + this.printSelect);

			stamp = new PdfStamper(
					reader,
					fileOutputStream);

			AcroFields form = stamp.getAcroFields();

			// printFile = new File(kekkaFilePath);


			//ページ番号を設定する
			// edit s.inoue 2010/01/05
			if (printSelect ==2){
				int maxP = Integer.valueOf(maxPage);
				String maxSt = Integer.toString(maxP);
				// eidt s.inoue 2013/10/28
				form.setField("PAGE_NUM", "( 2 / "+maxSt+" )");
				// form.setField("PAGE_NUM", "（2／"+maxSt+"）");
				// form.setField("PAGE_NUM", "（ ２／"+JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(maxSt)+" ）");
			}

			/* 機関情報を出力する。 */
			this.printKikanData(KikanData, form);

			/* 個人情報を出力する。 */
			this.printKojinData(kojinData, kensaNengapi, form);

			/*
			 * 健診結果情報 PrintDataから健診結果情報を抽出
			 */
			ArrayList<String> itemCodes = new ArrayList<String>();
			KenshinKekka tmpKenshinKekka = (KenshinKekka) printData
			.get("KenshinKekka");

			if (tmpKenshinKekka == null) {
				System.out.println("tmpKenshinKekka is null.");
				// edit s.inoue 2010/01/05
				return null;
				// return printFile;
			}

			// edit s.inoue 2010/01/05
			// 機能外だし
			ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData =
				tmpKenshinKekka.getPrintKenshinKekkaData();
			String[] kensaNengapiAll = tmpKenshinKekka.getKensaNengapi();
			Hashtable<String, Hashtable<String, String>> dataItemMap = kenshinKekkaData.get(0);

			// edit s.inoue 2010/01/05
			// 左ページを外出し
			/*
			try {
				create1PageSetting(form, tmpKenshinKekka,kenshinKekkaData,kensaNengapiAll,dataItemMap,
						ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
						ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List,itemCodes,kojinData,ResultMap);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			*/

			// edit s.inoue 2010/01/05
			// 右ページを外出し
			try {
				create2PageSetting(form, tmpKenshinKekka,kenshinKekkaData,kensaNengapiAll,dataItemMap,
						ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
						ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List,itemCodes);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			stamp.setFormFlattening(true);
			stamp.close();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stamp != null) {
					stamp.setFormFlattening(true);
					stamp.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				/* 何もしない */
			}
		}
		// edit s.inoue 2010/01/05
		pdfPathList.add(templatePath_2 + this.printSelect);
		return pdfPathList;
	}
	// add s.inoue 2010/01/05
	private File create1PageSetting(
			AcroFields form,
			KenshinKekka tmpKenshinKekka,
			ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData,
			String[] kensaNengapiAll,
			Hashtable<String, Hashtable<String, String>> dataItemMap,
			List ketuatuHL_List,
			List KetyuHL_List,
			List kankinouHL_List,
			List KetouHL_List,
			List NyoHL_List,
			List ketuatuVal_List,
			List KetyuVal_List,
			List kankinouVal_List,
			List KetouVal_List,
			List NyoVal_List,
			ArrayList<String> itemCodes,
			Hashtable<String, String> kojinData,
			Map<String,ArrayList<String>>ResultMap
	) throws Exception{

		/*
		 * 健診年月日ごとのデータを取得 0=>今回 1=>前回 2=>前々回
		 */
		// del s.inoue 2010/01/05
		// ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData =
		//	tmpKenshinKekka.getPrintKenshinKekkaData();
		if (kenshinKekkaData == null || kenshinKekkaData.size() < 1) {
			return printFile;
		}

		/*
		 * 健診年月日を取得 0=>今回 1=>前回 2=>前々回
		 */
		// del s.inoue 2010/01/05
		// String[] kensaNengapiAll = tmpKenshinKekka.getKensaNengapi();
		if (kensaNengapiAll == null || kensaNengapiAll.length == 0) {
			return printFile;
		}

		// del s.inoue 2010/01/05
		// Hashtable<String, Hashtable<String, String>> dataItemMap = kenshinKekkaData.get(0);
		if (dataItemMap == null) {
			return printFile;
		}

		/*
		 * 既往歴、既往症、自覚症状、他覚症状、喫煙歴
		 */
		// 既往歴
		if (kenshinKekkaData.size() != 0) {

			this.printKiourekiGroup(form, itemCodes, tmpKenshinKekka, dataItemMap);
		}

		/*
		 * 健診年月日セット 今回、前回、前々回
		 */
		kensaNengapiLast = kensaNengapiAll[0];
		kensaNengapiLast2 = kensaNengapiAll[1];
		kensaNengapiLast3 = kensaNengapiAll[2];
		try {
			// 今回
			form.setField("T183", this.getYmdRemoved0(kensaNengapiLast) );
			// 前回
			form.setField("T300", this.getYmdRemoved0(kensaNengapiLast2));
			// 前々回
			form.setField("T339", this.getYmdRemoved0(kensaNengapiLast3));

		} catch (NullPointerException e) {
		}

		/*
		 * 全ての表示項目を出力する。
		 * 基準値については、身体測定、血圧、血中脂質検査、肝機能検査、
		 * 血糖検査、尿検査 身長、体重、腹囲は不要。
		 */
		boolean isMail = true;
		if (kojinData.get("SEX").equals("女性")) {
			isMail= false;
		}


		//身長
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_HIGHT);
		String[][] forms = new String[][]{
				/* 基準値 */
				null,
				/* H/L 判定 */
				{ "T184", "T301", "T340",  },
				/* 結果値 */
				{ "T185", "T320", "T361",  },
		};
		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);

		//体重
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_WEIGHT);
		forms = new String[][]{
				/* 基準値 */
				null,
				/* H/L 判定 */
				{ "T186", "T302", "T341",  },
				/* 結果値 */
				{ "T187", "T321", "T362",  },
		};
		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);

		/*
		 * 腹囲（実測） 9N016160100000001 腹囲（自己判定） 9N016160200000001
		 * 腹囲（自己申告） 9N016160300000001
		 */
		itemCodes.clear();
		itemCodes.add("9N016160100000001");
		itemCodes.add("9N016160200000001");
		itemCodes.add("9N016160300000001");
		forms = new String[][]{
				/* 基準値 */
				null,
				/* H/L 判定 */
				{ "T188", "T303", "T342",  },
				/* 結果値 */
				{ "T189", "T322", "T363",  },
		};
		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);


		// BMI
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_BMI);

		forms = new String[][]{
				/* 基準値 */
				{ "T253" },
				/* H/L 判定 */
				{ "T190", "T304", "T343",  },
				/* 結果値 */
				{ "T191", "T323", "T364",  },
		};

		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);

		/*
		 * 収縮期血圧（1回目） 9A751000000000001 収縮期血圧（2回目） 9A752000000000001
		 * 収縮期血圧（その他） 9A755000000000001
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_SYUSYUKUKI_KETUATU_SONOTA);
		itemCodes.add(PrintDefine.CODE_SYUSYUKUKI_KETUATU_2);
		itemCodes.add(PrintDefine.CODE_SYUSYUKUKI_KETUATU_1);

		forms = new String[][]{
				/* 基準値 */
				{ "T254" },
				/* H/L 判定 */
				{ "T192", "T305", "T345",  },
				/* 結果値 */
				{ "T193", "T324", "T365",  },
		};

		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ketuatuHL_List.add(ResultMap.get("HL").get(0));
		ketuatuVal_List.add(ResultMap.get("Value").get(0));


		/*
		 * 拡張期血圧（一回目） 9A761000000000001 拡張期血圧（二回目） 9A762000000000001
		 * 拡張期血圧（その他） 9A765000000000001
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_KAKUCHOKI_KETUATU_SONOTA);
		itemCodes.add(PrintDefine.CODE_KAKUCHOKI_KETUATU_2);
		itemCodes.add(PrintDefine.CODE_KAKUCHOKI_KETUATU_1);

		forms = new String[][]{
				/* 基準値 */
				{ "T255" },
				/* H/L 判定 */
				{ "T194", "T306", "T346",  },
				/* 結果値 */
				{ "T195", "T325", "T366",  },
		};

		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ketuatuHL_List.add(ResultMap.get("HL").get(0));
		ketuatuVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去）） 3F015000002327101
		 * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去）） 3F015000002327201 中性脂肪（その他）
		 * 3F015000002399901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_CHUSEISIBOU_KASI);
		itemCodes.add(PrintDefine.CODE_CHUSEISIBOU_SIGAI);
		itemCodes.add(PrintDefine.CODE_CHUSEISIBOU_SONOTA);

		forms = new String[][]{
				/* 基準値 */
				{ "T256" },
				/* H/L 判定 */
				{ "T196", "T307", "T347",  },
				/* 結果値 */
				{ "T197", "T326", "T367",  },
		};
		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		KetyuHL_List.add(ResultMap.get("HL").get(0));

		/*
		 * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））） 3F070000002327101
		 * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））） 3F070000002327201
		 * HDL-コレステロール-（その他） 3F070000002399901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_HDL_KASI);
		itemCodes.add(PrintDefine.CODE_HDL_SIGAI);
		itemCodes.add(PrintDefine.CODE_HDL_SONOTA);

		forms = new String[][]{
				/* 基準値 */
				{ "T257" },
				/* H/L 判定 */
				{ "T198", "T308", "T348",  },
				/* 結果値 */
				{ "T199", "T327", "T368",  },
		};
		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		KetyuHL_List.add(ResultMap.get("HL").get(0));
		KetyuVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）） 3F077000002327101
		 * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）） 3F077000002327201
		 * LDL-コレステロール-（その他） 3F077000002399901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_LDL_KASI);
		itemCodes.add(PrintDefine.CODE_LDL_SIGAI);
		itemCodes.add(PrintDefine.CODE_LDL_SONOTA);
		forms = new String[][]{
				/* 基準値 */
				{ "T258" },
				/* H/L 判定 */
				{ "T200", "T309", "T349",  },
				/* 結果値 */
				{ "T201", "T328", "T369",  },
		};
		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		KetyuHL_List.add(ResultMap.get("HL").get(0));
		KetyuVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * GOT（紫外吸光光度法（JSCC標準化対応法）） 3B035000002327201 GOT（その他）
		 * 3B035000002399901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_GOT_SIGAI);
		itemCodes.add(PrintDefine.CODE_GOT_SONOTA);
		forms = new String[][]{
				/* 基準値 */
				{ "T259" },
				/* H/L 判定 */
				{ "T202", "T310", "T350",  },
				/* 結果値 */
				{ "T203", "T329", "T370",  },
		};
		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		kankinouHL_List.add(ResultMap.get("HL").get(0));
		kankinouVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * GPT（紫外吸光光度法（JSCC標準化対応法）） 3B045000002327201 GPT（その他）
		 * 3B045000002399901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_GPT_SIGAI);
		itemCodes.add(PrintDefine.CODE_GPT_SONOTA);
		forms = new String[][]{
				/* 基準値 */
				{ "T260" },
				/* H/L 判定 */
				{ "T204", "T311", "T351",  },
				/* 結果値 */
				{ "T205", "T330", "T371",  },
		};
		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		kankinouHL_List.add(ResultMap.get("HL").get(0));
		kankinouVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * /* y-GTP（可視吸光光度法（JSCC標準化対応法）） 3B090000002327101 y-GTP（その他）
		 * 3B090000002399901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_GAMMA_GTP_KASI);
		itemCodes.add(PrintDefine.CODE_GAMMA_GTP_SONOTA);
		forms = new String[][]{
				/* 基準値 */
				{ "T261" },
				/* H/L 判定 */
				{ "T206", "T312", "T352",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T207", "T331", "T372",  },
		};
		//this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		kankinouHL_List.add(ResultMap.get("HL").get(0));
		kankinouVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法）） 3D010000001926101
		 * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法）） 3D010000001727101
		 * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法））
		 * 3D010000001927201 空腹時血糖（その他） 3D010000001999901
		 */
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_KUHUKUZI_KETO_DENNI);
		itemCodes.add(PrintDefine.CODE_KUHUKUZI_KETO_KASI);
		itemCodes.add(PrintDefine.CODE_KUHUKUZI_KETO_SIGAI);
		itemCodes.add(PrintDefine.CODE_KUHUKUZI_KETO_SONOTA);
		forms = new String[][]{
				/* 基準値 */
				{ "T262" },
				/* H/L 判定 */
				{ "T208", "T313", "T353",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T209", "T332", "T373",  },
		};

		//List kufukuList = this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		List kufukuList = ResultMap.get("Value");
		KetouHL_List.add(ResultMap.get("HL").get(0));
		KetouVal_List.add(ResultMap.get("Value").get(0));

		/*
		 * ﾍﾓｸﾞﾛﾋﾞﾝA1c 免疫学的方法（ラテックス凝集比濁法等） 3D045000001906202 ﾍﾓｸﾞﾛﾋﾞﾝA1c
		 * HPLC(不安定分画除去HPLC法） 3D045000001920402 ﾍﾓｸﾞﾛﾋﾞﾝA1c 酵素法
		 * 3D045000001927102 ﾍﾓｸﾞﾛﾋﾞﾝA1c その他 3D045000001999902
		 */

		itemCodes.clear();
		// eidt s.inoue 2013/01/29
		itemCodes.add(PrintDefine.CODE_HBA1C_RATEX_JDS);
		itemCodes.add(PrintDefine.CODE_HBA1C_HPLC_JDS);
		itemCodes.add(PrintDefine.CODE_HBA1C_COUSOHO_JDS);
		itemCodes.add(PrintDefine.CODE_HBA1C_SONOTA_JDS);
		forms = new String[][]{
				/* 基準値 */
				{ "T263" },
				/* H/L 判定 */
				{ "T210", "T314", "T354",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T211", "T333", "T374",  },
		};
//		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		KetouHL_List.add(ResultMap.get("HL").get(0));
		KetouVal_List.add(ResultMap.get("Value").get(0));

		// add s.inoue 2013/01/29
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_HBA1C_RATEX_NGSP);
		itemCodes.add(PrintDefine.CODE_HBA1C_HPLC_NGSP);
		itemCodes.add(PrintDefine.CODE_HBA1C_COUSOHO_NGSP);
		itemCodes.add(PrintDefine.CODE_HBA1C_SONOTA_NGSP);
		forms = new String[][]{
				/* 基準値 */
				{ "T263" },
				/* H/L 判定 */
				{ "T210", "T314", "T354",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T211", "T333", "T374",  },
		};
//		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		ResultMap=this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);
		KetouHL_List.add(ResultMap.get("HL").get(0));
		KetouVal_List.add(ResultMap.get("Value").get(0));


		// 赤血球数
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_SEKEKYUSU);
//		itemCodes.add(CODE_SEKEKYUSU);
		forms = new String[][]{
				/* 基準値 */
				{ "T264" },
				/* H/L 判定 */
				{ "T216", "T317", "T358",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T217", "T336", "T377",  },
		};
		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);

		// 血色素数
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_KESIKISOSU);
//		itemCodes.add(CODE_KESIKISOSU);
		forms = new String[][]{
				/* 基準値 */
				{ "T265" },
				/* H/L 判定 */
				{ "T218", "T318", "T359",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T219", "T337", "T378",  },
		};
		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);

		// ヘマトクリット値
		itemCodes.clear();
		itemCodes.add(PrintDefine.CODE_HEMATCLIT);
//		itemCodes.add(CODE_HEMATCLIT);
		forms = new String[][]{
				/* 基準値 */
				{ "T266" },
				/* H/L 判定 */
				{ "T220", "T319", "T360",  },
				/* 結果値 */
				// edit ver2 s.inoue 2009/06/22
				{ "T221", "T338", "T379",  },
		};
		this.setAllToForm(form, itemCodes, tmpKenshinKekka, isMail, forms);


		/*
		 * 身体測定、血圧、血中脂質検査、肝機能検査、血糖検査、尿検査 今回、前回、前々回
		 */
		for (int i = 0; i < kensaNengapiAll.length; i++) {
			try {
				/*
				 * 糖 試験紙法（機械読み取り） 1A020000000191111 糖 試験紙法（目視法）
				 * 1A020000000190111
				 */
				itemCodes.clear();
				itemCodes.add(PrintDefine.TOU_KIKAI);
				itemCodes.add(PrintDefine.TOU_MOKUSHI);

				String formId = null;
				switch (i) {
				case 0:
					formId = "T213";
					break;
				case 1:
					// edit ver2 s.inoue 2009/06/22
					formId = "T334";
					break;
				case 2:
					formId = "T375";
					break;
				default:
					break;
				}

				String kekati = tmpKenshinKekka.getKekati(itemCodes, i);

				// edit s.inoue 20080904
				if (tmpKenshinKekka.getJisiKbn(itemCodes,i).equals(""))
				{
					continue;
				}

				Integer jisiKbnTou= Integer.valueOf(tmpKenshinKekka.getJisiKbn(itemCodes,i));
				switch (jisiKbnTou) {
					case 0:
						kekati = KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						break;
					case 2:
						kekati = KEKKA_OUTPUT_JISI_KBN_2;
						break;
				}

				// edit s.inoue 20080904
				if (jisiKbnTou == 1)
				{
					if (kekati != null && ! kekati.isEmpty()) {
						int kekatiInt = -1;
						try {
							kekatiInt = Integer.parseInt(kekati);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							continue;
						}

						for (Iterator iter = itemCodes.iterator(); iter.hasNext();) {
							String code = (String) iter.next();

							String codeName = tmpKenshinKekka.getCodeName(code, kekatiInt);

							if (codeName != null && ! codeName.isEmpty()) {

								StringBuffer buffer = new StringBuffer();
								buffer.append("(");
								buffer.append(codeName.replaceFirst("\\d+:", ""));

								buffer.append(")");
								String displayCodeName = buffer.toString();

								form.setField(formId,displayCodeName);
								break;
							}
						}
					}

				}else{
					form.setField(formId,kekati);
				}

				/*
				 * 蛋白 試験紙法（機械読み取り） 1A010000000191111 蛋白 試験紙法（目視法）
				 * 1A010000000190111
				 */
				itemCodes.clear();
				itemCodes.add(PrintDefine.CODE_NYOTANPAKU_KIKAI);
				itemCodes.add(PrintDefine.CODE_NYOTANPAKU_MOKUSI);

				formId = null;
				switch (i) {
				case 0:
					formId = "T215";
					break;
				case 1:
					// edit ver2 s.inoue 2009/06/22
					formId = "T335";
					break;
				case 2:
					formId = "T376";
					break;
				default:
					break;
				}

				kekati = tmpKenshinKekka.getKekati(itemCodes, i);

				// edit s.inoue 20080904
				if(tmpKenshinKekka.getJisiKbn(itemCodes,i).equals("")){
					continue;
				}

				Integer jisiKbnTanpaku= Integer.valueOf(tmpKenshinKekka.getJisiKbn(itemCodes,i));
				switch (jisiKbnTanpaku) {
					case 0:
						kekati = KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						break;
					case 2:
						kekati = KEKKA_OUTPUT_JISI_KBN_2;
						break;
				}

				// edit s.inoue 20080904
				if (jisiKbnTanpaku == 1)
				{
					if (kekati != null && ! kekati.isEmpty()) {
						int kekatiInt = -1;
						try {
							kekatiInt = Integer.parseInt(kekati);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							continue;
						}

						for (Iterator iter = itemCodes.iterator(); iter.hasNext();) {
							String code = (String) iter.next();

							String codeName = tmpKenshinKekka.getCodeName(code, kekatiInt);

							if (codeName != null && ! codeName.isEmpty()) {

								StringBuffer buffer = new StringBuffer();
								buffer.append("(");

								codeName = codeName.replaceFirst("\\d+:", "");

								buffer.append(codeName);

								buffer.append(")");
								String displayCodeName = buffer.toString();

								form.setField(formId,displayCodeName);
								break;
							}
						}
					}
				}else{
					form.setField(formId,kekati);
				}

			} catch (IndexOutOfBoundsException e) {
			}

			/*　2008/06/09 藪 空腹時血糖追加
			 *  空腹時血糖
			 */
			try {
				if(kufukuList.size() > 0)
				{
					if(!kufukuList.get(0).equals("")){
						if (kenshinKekkaData.get(0).get(PrintDefine.CODE_COLLECTING_BLOOD_TIME) != null){
							if (PrintDefine.ONE.equals(kenshinKekkaData.get(0)
									.get(PrintDefine.CODE_COLLECTING_BLOOD_TIME)
									.get(PrintDefine.COLUMN_NAME_KEKA_TI))){
								form.setField("T208",PrintDefine.asuta);
							}
						}
					}
				}
				if(kufukuList.size() > 1)
				{
					if(!kufukuList.get(1).equals("")){
						if (kenshinKekkaData.get(1).get(PrintDefine.CODE_COLLECTING_BLOOD_TIME) != null){
							if (PrintDefine.ONE.equals(kenshinKekkaData.get(1)
									.get(PrintDefine.CODE_COLLECTING_BLOOD_TIME)
									.get(PrintDefine.COLUMN_NAME_KEKA_TI))){
								form.setField("T313",PrintDefine.asuta);
							}
						}
					}
				}
				if(kufukuList.size() > 2)
				{
					if(!kufukuList.get(2).equals("")){
						if (kenshinKekkaData.get(2).get(PrintDefine.CODE_COLLECTING_BLOOD_TIME) != null){
							if (PrintDefine.ONE.equals(kenshinKekkaData.get(2)
									.get(PrintDefine.CODE_COLLECTING_BLOOD_TIME)
									.get(PrintDefine.COLUMN_NAME_KEKA_TI))){
								form.setField("T353",PrintDefine.asuta);
							}
						}
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return printFile;
	}

	// add s.inoue 2010/01/05
	private void create2PageSetting(
			AcroFields form,
			KenshinKekka tmpKenshinKekka,
			ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData,
			String[] kensaNengapiAll,
			Hashtable<String, Hashtable<String, String>> dataItemMap,
			List ketuatuHL_List,
			List KetyuHL_List,
			List kankinouHL_List,
			List KetouHL_List,
			List NyoHL_List,
			List ketuatuVal_List,
			List KetyuVal_List,
			List kankinouVal_List,
			List KetouVal_List,
			List NyoVal_List,
			ArrayList<String> itemCodes
	) throws Exception{
		//今回、前回、前々回　日付印字
		try {
			// 今回
			form.setField("T227", this.getYmdRemoved0(kensaNengapiLast));
			// 前回
			form.setField("T400", this.getYmdRemoved0(kensaNengapiLast2));
			// 前々回
			form.setField("T401", this.getYmdRemoved0(kensaNengapiLast3));
		} catch (NullPointerException e) {
		}

		/*
		 * 心電図検査、眼底検査、メタボリックシンドローム判定 今回、前回、前々回
		 */
		for (int i = 0; i < kensaNengapiAll.length; i++) {
			if (kensaNengapiAll[i] != null){
				shindenzuGanteiKensa(form, tmpKenshinKekka, kenshinKekkaData, kensaNengapiAll, dataItemMap,i);
			}
		}

		/*
		 * メタボリックシンドローム判定
		 */
		/*
		 * 結果値をセット
		 */
		try {
			for (int i = 0; i < kensaNengapiAll.length; i++) {
				if (kensaNengapiAll[i] != null){
					String kekka="";
					// edit ver2 s.inoue 2009/07/27
					// kekka = (dataItemMap.get("9N501000000000011").get("CODE_NAME").replaceFirst("\\d+:", ""));
					if (kenshinKekkaData.get(0).size() > 0){
						kekka = (kenshinKekkaData.get(0).get("9N501000000000011").get("CODE_NAME").replaceFirst("\\d+:", ""));
						form.setField("T241",kekka );
					}

					if (i > 0) {
						if (i > 1) {
							if (kenshinKekkaData.get(2).size() > 0){
								String kekka2="";
								kekka2 = (kenshinKekkaData.get(2).get("9N501000000000011").get("CODE_NAME").replaceFirst("\\d+:", ""));
								form.setField("T407", kekka2);
							}
						}
						if (kenshinKekkaData.get(1).size() > 0){
							String kekka1="";
							kekka1 = (kenshinKekkaData.get(1).get("9N501000000000011").get("CODE_NAME").replaceFirst("\\d+:", ""));
							form.setField("T406", kekka1);
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {

		}

		// 検査分野別判定
		try {
			List kensaContain = new ArrayList();
			kensaContain.add("");
//			kensaContain.add(null);

			String strValues = "";

			//血圧
			strValues = kensabunyaBetuHntei(ketuatuHL_List, ketuatuVal_List, kensaContain, strValues,PrintDefine.KETUATU_SEIJYO,PrintDefine.KETUATU_IJYO);
			//血中脂質検査
			strValues = kensabunyaBetuHntei(KetyuHL_List, KetyuVal_List, kensaContain, strValues,PrintDefine.KETTYU_SEIJYO,PrintDefine.KETTYU_IJYO);
			//肝機能検査
			strValues = kensabunyaBetuHntei(kankinouHL_List, kankinouVal_List, kensaContain, strValues,PrintDefine.KANKINOU_SEIJYO,PrintDefine.KANKINOU_IJYO);
			//血糖検査
			strValues = kensabunyaBetuHntei(KetouHL_List, KetouVal_List, kensaContain, strValues,PrintDefine.KETTOU_SEIJYO,PrintDefine.KETTOU_IJYO);

			form.setField("T236",strValues);

		} catch (Exception e) {
		}
		/*
		 * 医師の判断（今回の健診のみ）
		 */
		if (kenshinKekkaData.size() > 0) {
			/* --------------------------------------------------- */
			Hashtable<String, Hashtable<String, String>> kekkaData = dataItemMap;

			StringBuffer buffer = new StringBuffer();
			if (kekkaData.keySet().contains(PrintDefine.CODE_ISHINO_HANDAN)) {
				Hashtable<String, String> item = kekkaData
				.get(PrintDefine.CODE_ISHINO_HANDAN);

				if (item.keySet().contains(PrintDefine.COLUMN_NAME_KEKA_TI)) {
					String kekati = item.get(PrintDefine.COLUMN_NAME_KEKA_TI);

					if (kekati != null && ! kekati.isEmpty()) {
//						form.setField("T242", item.get(COLUMN_NAME_KEKA_TI));
						buffer.append(kekati);
						buffer.append(JApplication.LINE_SEPARATOR);
					}
				}
			}

			// 貧血検査（実施理由）
			if (kekkaData.keySet().contains("2A020161001930149")) {
				Hashtable<String, String> item = kekkaData.get("2A020161001930149");

				if (item.keySet().contains(PrintDefine.COLUMN_NAME_KEKA_TI)) {
					String kekati = item.get(PrintDefine.COLUMN_NAME_KEKA_TI);

					if (kekati != null && ! kekati.isEmpty()) {
						buffer.append("貧血検査　："+kekati);
						buffer.append(JApplication.LINE_SEPARATOR);
					}
				}
			}

			// 心電図（実施理由）
			if (kekkaData.keySet().contains("9A110161000000049")) {
				Hashtable<String, String> item = kekkaData.get("9A110161000000049");

				if (item.keySet().contains(PrintDefine.COLUMN_NAME_KEKA_TI)) {
					String kekati = item.get(PrintDefine.COLUMN_NAME_KEKA_TI);

					if (kekati != null && ! kekati.isEmpty()) {
						buffer.append("心電図検査："+kekati);
						buffer.append(JApplication.LINE_SEPARATOR);
					}
				}
			}
			// 眼底検査（実施理由）
			if (kekkaData.keySet().contains("9E100161000000049")) {
				Hashtable<String, String> item = kekkaData.get("9E100161000000049");

				if (item.keySet().contains(PrintDefine.COLUMN_NAME_KEKA_TI)) {
					String kekati = item.get(PrintDefine.COLUMN_NAME_KEKA_TI);

					if (kekati != null && ! kekati.isEmpty()) {
						buffer.append("眼底検査　："+kekati);
						buffer.append(JApplication.LINE_SEPARATOR);
					}
				}
			}

			String riyuAll = buffer.toString();
			if (riyuAll != null && ! riyuAll.isEmpty()) {
				form.setField("T242", riyuAll);
			}
			/* --------------------------------------------------- */

			// 判断した医師名
			if (kekkaData.keySet().contains(PrintDefine.CODE_KENKOUSINDAN_WO_JISISITA_ISI_NO_SHIMEI)) {
				Hashtable<String, String> item = kekkaData
				.get(PrintDefine.CODE_KENKOUSINDAN_WO_JISISITA_ISI_NO_SHIMEI);

				if (item.keySet().contains(PrintDefine.COLUMN_NAME_KEKA_TI)) {
					ishiName = item.get(PrintDefine.COLUMN_NAME_KEKA_TI);
					form.setField("T252", ishiName);
//					form.setField("T252", item.get(PrintDefine.COLUMN_NAME_KEKA_TI));
				}
			}

			// add s.inoue 2010/05/11
			/*
			 * 既往歴、既往症、自覚症状、他覚症状、喫煙歴
			 */
			// 既往歴
			if (kenshinKekkaData.size() != 0) {

				this.printKiourekiGroup(form, itemCodes, tmpKenshinKekka, dataItemMap);
			}
		}
	}



	/**
	 * 特定健康診査受診結果を作成する　
	 *
	 * @param stamp
	 * @param KikanData
	 * @param kojinData
	 * @param uketukeId
	 * @param kensaNengapi
	 * @return
	 */
	private File create1Page(PdfStamper stamp, Hashtable<String, String> KikanData,
			Hashtable<String, String> kojinData,String uketukeId, String kensaNengapi,int maxPage,
			List ketuatuHL_List,List KetyuHL_List,List kankinouHL_List,List KetouHL_List,List NyoHL_List,
			List ketuatuVal_List,List KetyuVal_List,List kankinouVal_List,List KetouVal_List,List NyoVal_List) {

		Map<String,ArrayList<String>>ResultMap  = new HashMap<String,ArrayList<String>>();
// move s.inoue 2010/05/11
//		List ketuatuHL_List = new ArrayList();
//		List KetyuHL_List = new ArrayList();
//		List kankinouHL_List = new ArrayList();
//		List KetouHL_List = new ArrayList();
//		List NyoHL_List = new ArrayList();
//		ketuatuHL_List.clear();
//		KetyuHL_List.clear();
//		kankinouHL_List.clear();
//		KetouHL_List.clear();
//		NyoHL_List.clear();
//
//		List ketuatuVal_List = new ArrayList();
//		List KetyuVal_List = new ArrayList();
//		List kankinouVal_List = new ArrayList();
//		List KetouVal_List = new ArrayList();
//		List NyoVal_List = new ArrayList();
//		ketuatuVal_List.clear();
//		KetyuVal_List.clear();
//		kankinouVal_List.clear();
//		KetouVal_List.clear();
//		NyoVal_List.clear();

		try {
			if (KikanData == null) {
				System.out.println("KikanData is null.");
				return null;
			}

			// ファイル作成
			// edit s.inoue 2009/12/25
			String temppath_1 ="";
			String temppath_2 ="";
			if (printSelect == 2){
				temppath_1 = templatePath_1;
				temppath_2 = templatePath_2;
// del s.inoue 2009/12/25
//				if (maxPage == 0){
//					try{
//						PdfReader reader = new PdfReader(temppath_2);
//
//						PdfStamper stamp_2 = new PdfStamper(reader, new FileOutputStream(temppath_2));
//						AcroFields form_2 = stamp.getAcroFields();
//						stamp.close();
//						reader.close();
//
//						pdfPathList.add(tmpPath+Integer.toString(cnt));
//					}catch (Exception e){
//						e.printStackTrace();
//					}
//				}
			}else{
				temppath_1 = templatePath;
			}

			// PdfReader reader = new PdfReader(templatePath);
			PdfReader reader = new PdfReader(temppath_1);

			kensaNengapi = kensaNengapi.replaceAll("年", "");
			kensaNengapi = kensaNengapi.replaceAll("月", "");
			kensaNengapi = kensaNengapi.replaceAll("日", "");

			StringBuffer buffer = new StringBuffer();
			buffer.append("."+File.separator+"Data"+File.separator+"PDF"+File.separator+"kekka");
			buffer.append( uketukeId );
			buffer.append( "-" );
			buffer.append( kensaNengapi );

			/* 同じ受付ID、健診年月日のファイルを開けるようにする。 */
			Calendar cal = Calendar.getInstance();
			buffer.append( "-" );
			buffer.append( cal.getTimeInMillis() );

			buffer.append(".pdf");
			String kekkaFilePath = buffer.toString();

			/* 2枚目追加の為1枚目は、名前をdummyで作成する。 */
			FileOutputStream fileOutputStream = new FileOutputStream(PrintDefine.WORK_PDF_TMP_DUMMY_PDF);

			stamp = new PdfStamper(
					reader,
					fileOutputStream);

			AcroFields form = stamp.getAcroFields();

			printFile = new File(kekkaFilePath);


			//ページ番号を設定する
			int maxP = Integer.valueOf(maxPage);
			String maxSt = Integer.toString(maxP+1);
			// eidt s.inoue 2013/10/28
			form.setField("PAGE_NUM", "( 1 / "+maxSt+" )");
			// form.setField("PAGE_NUM", "（1／"+maxSt+"）");
			// form.setField("PAGE_NUM", "（ １／"+JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(maxSt)+" ）");

			// add s.inoue 2012/09/10
			// String comment = "※ヘモグロビンA1c検査の結果値はNGSP法による検査結果値が記載されていますが、☆が併記されているものは、JDS法による検査結果値のため基準値が異なります．（JDS法基準値3.9～5.2）";
			String comment = "※ヘモグロビンA1c検査の結果値はNGSP値による検査結果値が記載されていますが、☆が併記されているものは、JDS値による検査結果値のため基準値が異なります．";
			form.setField("txt_HbA1c_Comment", comment);

			// add s.inoue 2013/03/18
			form.setField("txt_HbA1c_Comment_2", "6.0");

			/* 機関情報を出力する。 */
			this.printKikanData(KikanData, form);

			/* 個人情報を出力する。 */
			this.printKojinData(kojinData, kensaNengapi, form);

			/*
			 * 健診結果情報 PrintDataから健診結果情報を抽出
			 */
			ArrayList<String> itemCodes = new ArrayList<String>();
			KenshinKekka tmpKenshinKekka = (KenshinKekka) printData
			.get("KenshinKekka");

			if (tmpKenshinKekka == null) {
				System.out.println("tmpKenshinKekka is null.");
				return printFile;
			}

			// edit s.inoue 2010/01/05
			// 機能外だし
			ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData =
				tmpKenshinKekka.getPrintKenshinKekkaData();
			String[] kensaNengapiAll = tmpKenshinKekka.getKensaNengapi();
			Hashtable<String, Hashtable<String, String>> dataItemMap = kenshinKekkaData.get(0);

			// edit s.inoue 2010/01/05
			// 左ページを外出し
			try {
				create1PageSetting(form, tmpKenshinKekka,kenshinKekkaData,kensaNengapiAll,dataItemMap,
						ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
						ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List,itemCodes,kojinData,ResultMap);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}

			// edit s.inoue 2010/01/05
			// 右ページを外出し
			try {
				create2PageSetting(form, tmpKenshinKekka,kenshinKekkaData,kensaNengapiAll,dataItemMap,
						ketuatuHL_List,KetyuHL_List,kankinouHL_List,KetouHL_List,NyoHL_List,
						ketuatuVal_List,KetyuVal_List,kankinouVal_List,KetouVal_List,NyoVal_List,itemCodes);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
			stamp.setFormFlattening(true);
			stamp.close();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stamp != null) {
					stamp.setFormFlattening(true);
					stamp.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				/* 何もしない */
			}
		}
		return printFile;
	}

	/**
	 * 検査分野別判定
	 *
	 * @param hL_List
	 * @param val_List
	 * @param valContein
	 * @param strValues
	 * @param seijo
	 * @param ijyo
	 * @return
	 */
	private String kensabunyaBetuHntei(List hL_List, List val_List,
			List valContein, String strValues, String seijo, String ijyo ) {

		//異常あり
		if(hL_List.contains("H")||hL_List.contains("L")){
			strValues = strValues + ijyo+JApplication.LINE_SEPARATOR;

		//異常なし
		}else if(!isValue(val_List)){
			strValues = strValues + seijo+JApplication.LINE_SEPARATOR;
		}else{
		}

		return strValues;
	}

	private boolean isValue(List val){
		List tmp = new ArrayList();

		for (int i = 0;i<val.size();i++){
			if(!val.get(i).equals("")){
				tmp.add(val.get(i));
			}
		}
		return tmp.isEmpty();
	}

	/**
	 * 心電図検査、眼底検査の今回、前回、前々回
	 * を印字する。
	 *
	 * @param form
	 * @param tmpKenshinKekka
	 * @param kenshinKekkaData
	 * @param kensaNengapiAll
	 * @param dataItemMap
	 */
	private void shindenzuGanteiKensa(AcroFields form, KenshinKekka tmpKenshinKekka,
			ArrayList<Hashtable<String, Hashtable<String, String>>> kenshinKekkaData,
			String[] kensaNengapiAll,Hashtable<String, Hashtable<String, String>> dataItemMap, int i) {

		// 心電図
		try {

			String sindenzuSyokenKekati ="";
			String zenzenGetu ="";
			String zenGetu ="";

			// edit ver2 s.inoue 2009/07/15
			// dataItemMap.get ⇒ kenshinKekkaDataget
			String sindenzuSyokenUmu = kenshinKekkaData.get(0).get(
					PrintDefine.CODE_SINDENZU_SYOKEN_UMU).get(
							PrintDefine.COLUMN_NAME_KEKA_TI);

			Integer jisiKbn= Integer.valueOf(kenshinKekkaData.get(0).get(
					PrintDefine.CODE_SINDENZU_SYOKEN).get(
							PrintDefine.COLUMN_NAME_JISI_KBN));

			switch (jisiKbn) {
				case 0:
					sindenzuSyokenKekati= KEKKA_OUTPUT_JISI_KBN_0;
					break;
				case 1:
					sindenzuSyokenKekati = kenshinKekkaData.get(0).get(
							PrintDefine.CODE_SINDENZU_SYOKEN).get(
									PrintDefine.COLUMN_NAME_KEKA_TI);
					break;
				case 2:
					sindenzuSyokenKekati= KEKKA_OUTPUT_JISI_KBN_2;
					break;
			}

			// 1:所見あり､2:所見なし
			if ((sindenzuSyokenUmu.equals("1") && !sindenzuSyokenKekati.isEmpty()) ||
					(sindenzuSyokenUmu.equals("2") && !sindenzuSyokenKekati.isEmpty()))
					{
				form.setField("T229", sindenzuSyokenKekati);
			}else if ((sindenzuSyokenUmu.equals("2")	&& sindenzuSyokenKekati.isEmpty()) ||
				(sindenzuSyokenUmu.equals("1") 	&& sindenzuSyokenKekati.isEmpty())) {
				form.setField("T229", PrintDefine.SYOKEN_NASHI);
			}

			if (i > 0) {
				if (i > 1) {
					// edit s.inoue 20080902
					// 1:所見あり､2:所見なし
					String zenzenGetuUmu = (kenshinKekkaData.get(2)
							.get(PrintDefine.CODE_SINDENZU_SYOKEN_UMU)
							.get(PrintDefine.COLUMN_NAME_KEKA_TI));

					Integer jisiKbn1= Integer.valueOf((kenshinKekkaData.get(2)
							.get(PrintDefine.CODE_SINDENZU_SYOKEN)
							.get(PrintDefine.COLUMN_NAME_JISI_KBN)));

					switch (jisiKbn1) {
					case 0:
						zenzenGetu= KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						zenzenGetu = (kenshinKekkaData.get(2)
								.get(PrintDefine.CODE_SINDENZU_SYOKEN)
								.get(PrintDefine.COLUMN_NAME_KEKA_TI));
						break;
					case 2:
						zenzenGetu= KEKKA_OUTPUT_JISI_KBN_2;
						break;
					}

					if ((zenzenGetuUmu.equals("1") && !zenzenGetu.isEmpty()) ||
							(zenzenGetuUmu.equals("2") && !zenzenGetu.isEmpty())){
						form.setField("T404", zenzenGetu);
					}else if ((zenzenGetuUmu.equals("2") && zenzenGetu.isEmpty()) ||
							(zenzenGetuUmu.equals("1") && zenzenGetu.isEmpty())) {
						form.setField("T404", PrintDefine.SYOKEN_NASHI);
					}
				}

				// edit s.inoue 20080902
				// 1:所見あり､2:所見なし
				String zenGetuUmu = (kenshinKekkaData.get(1)
									.get(PrintDefine.CODE_SINDENZU_SYOKEN_UMU)
									.get(PrintDefine.COLUMN_NAME_KEKA_TI));

				Integer jisiKbn2= Integer.valueOf(kenshinKekkaData.get(1)
									.get(PrintDefine.CODE_SINDENZU_SYOKEN_UMU)
									.get(PrintDefine.COLUMN_NAME_JISI_KBN));

				switch (jisiKbn2) {
				case 0:
					zenGetu= KEKKA_OUTPUT_JISI_KBN_0;
					break;
				case 1:
					zenGetu = (kenshinKekkaData.get(1).get(PrintDefine.CODE_SINDENZU_SYOKEN).get(PrintDefine.COLUMN_NAME_KEKA_TI));

					break;
				case 2:
					zenGetu= KEKKA_OUTPUT_JISI_KBN_2;
					break;
				}

				if ((zenGetuUmu.equals("1") && !zenGetu.isEmpty()) ||
						(zenGetuUmu.equals("2") && !zenGetu.isEmpty())){
					form.setField("T402", zenGetu);
				}else if ((zenGetuUmu.equals("1") && zenGetu.isEmpty()) ||
						(zenGetuUmu.equals("2") && zenGetu.isEmpty())) {
					form.setField("T402", PrintDefine.SYOKEN_NASHI);
				}

			}
		} catch (Exception e) {
		}

		//眼底検査用変数
		String eyeGround = "";
		String strKekkaKeith ="";
		String strKekkaSheie ="";
		String strKekkaSheieS ="";
		String strKekkaScott="";

		//	 start edit s.inoue 20080905
		boolean blnIjyoNasiFlg = false;

		//9E100166000000011	眼底検査（キースワグナー分類）
		try{
//			String strKeith = kenshinKekkaData.get(i).get(PrintDefine.CODE_KEITH_WAGNER).get(PrintDefine.COLUMN_NAME_KEKA_TI);
//			if (!strKeith.isEmpty()){

				// edit s.inoue 20080905
				Integer jisiKbnKeith= Integer.valueOf(kenshinKekkaData.get(i).get(
						PrintDefine.CODE_KEITH_WAGNER).get(
								PrintDefine.COLUMN_NAME_JISI_KBN));

				switch (jisiKbnKeith) {
					case 0:
						strKekkaKeith = KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						strKekkaKeith =JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(tmpKenshinKekka.getKomokuMeisho(
								PrintDefine.CODE_KEITH_WAGNER,kenshinKekkaData.get(i).get(
										PrintDefine.CODE_KEITH_WAGNER).get(
												PrintDefine.COLUMN_NAME_KEKA_TI)).replaceFirst("\\d+:",""));
						break;
					case 2:
						strKekkaKeith = KEKKA_OUTPUT_JISI_KBN_2;
						break;
				}
				// edit 20080911 s.inoue
				if (!strKekkaKeith.isEmpty()){
					switch (i){
					case 0:
						eyeGround = eyeGround + PrintDefine.VALUE_KEITH_WAGNER +
						strKekkaKeith + JApplication.LINE_SEPARATOR ;
						form.setField("T231",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					case 1:
						eyeGround = eyeGround + PrintDefine.VALUE_KEITH_WAGNER +
						strKekkaKeith + JApplication.LINE_SEPARATOR ;
						form.setField("T504",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					case 2:
						eyeGround = eyeGround + PrintDefine.VALUE_KEITH_WAGNER +
						strKekkaKeith + JApplication.LINE_SEPARATOR ;
						form.setField("T509",eyeGround);
						blnIjyoNasiFlg=true;
						break;
				}
			}
		} catch	(Exception e){
		}
		//9E100166100000011	眼底検査（シェイエ分類：Ｈ）
		try{
//			if (!kenshinKekkaData.get(i).get(PrintDefine.CODE_SHEIE_H).get(PrintDefine.COLUMN_NAME_KEKA_TI).isEmpty()){

				//	edit s.inoue 20080905
				Integer jisiKbnSheie= Integer.valueOf(kenshinKekkaData.get(i).get(
						PrintDefine.CODE_SHEIE_H).get(
								PrintDefine.COLUMN_NAME_JISI_KBN));

				switch (jisiKbnSheie) {
					case 0:
						strKekkaSheie = KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						strKekkaSheie =JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(tmpKenshinKekka.getKomokuMeisho(
								PrintDefine.CODE_SHEIE_H,kenshinKekkaData.get(i).get(
										PrintDefine.CODE_SHEIE_H).get(
												PrintDefine.COLUMN_NAME_KEKA_TI)).replaceFirst("\\d+:",""));
						break;
					case 2:
						strKekkaSheie = KEKKA_OUTPUT_JISI_KBN_2;
						break;
				}

				//	edit 20080911 s.inoue
				if (!strKekkaSheie.isEmpty()){
					switch (i){
					case 0:
						eyeGround = eyeGround + PrintDefine.VALUE_SHEIE_H +
						strKekkaSheie+JApplication.LINE_SEPARATOR ;
						form.setField("T231",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					case 1:
						eyeGround = eyeGround + PrintDefine.VALUE_SHEIE_H +
						strKekkaSheie+JApplication.LINE_SEPARATOR ;
						form.setField("T504",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					case 2:
						eyeGround = eyeGround + PrintDefine.VALUE_SHEIE_H +
						strKekkaSheie+JApplication.LINE_SEPARATOR ;
						form.setField("T509",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					}
				}
		} catch	(Exception e){
		}
		//9E100166200000011	眼底検査（シェイエ分類：Ｓ）
		try{
//			if (!kenshinKekkaData.get(i).get(PrintDefine.CODE_SHEIE_S).get(PrintDefine.COLUMN_NAME_KEKA_TI).isEmpty()){

				//	edit s.inoue 20080905
				Integer jisiKbnSheieS= Integer.valueOf(kenshinKekkaData.get(i).get(
						PrintDefine.CODE_SHEIE_S).get(
								PrintDefine.COLUMN_NAME_JISI_KBN));

				switch (jisiKbnSheieS) {
					case 0:
						strKekkaSheieS = KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						strKekkaSheieS =JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(tmpKenshinKekka.getKomokuMeisho(
								PrintDefine.CODE_SHEIE_S,kenshinKekkaData.get(i).get(
										PrintDefine.CODE_SHEIE_S).get(
												PrintDefine.COLUMN_NAME_KEKA_TI)).replaceFirst("\\d+:",""));
						break;
					case 2:
						strKekkaSheieS = KEKKA_OUTPUT_JISI_KBN_2;
						break;
				}
				//	edit 20080911 s.inoue
				if (!strKekkaSheieS.isEmpty()){
					switch (i){
					case 0:
						eyeGround = eyeGround + PrintDefine.VALUE_SHEIE_S +
						strKekkaSheieS+JApplication.LINE_SEPARATOR ;

						form.setField("T231",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					case 1:
						eyeGround = eyeGround + PrintDefine.VALUE_SHEIE_S +
						strKekkaSheieS+JApplication.LINE_SEPARATOR ;

						form.setField("T504",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					case 2:
						eyeGround = eyeGround + PrintDefine.VALUE_SHEIE_S +
						strKekkaSheieS+JApplication.LINE_SEPARATOR ;

						form.setField("T509",eyeGround);
						blnIjyoNasiFlg=true;
						break;
					}
				}
		} catch	(Exception e){
		}
		//9E100166300000011	眼底検査（SCOTT分類）

		try{
			// del s.inoue 20080903
			//			if (!kenshinKekkaData.get(i).get(PrintDefine.CODE_SCOTT).get(PrintDefine.COLUMN_NAME_KEKA_TI).isEmpty()){

				// edit s.inoue 20080905
				Integer jisiKbnScott= Integer.valueOf(kenshinKekkaData.get(i).get(
						PrintDefine.CODE_SCOTT).get(
								PrintDefine.COLUMN_NAME_JISI_KBN));

				switch (jisiKbnScott) {
					case 0:
						strKekkaScott = KEKKA_OUTPUT_JISI_KBN_0;
						break;
					case 1:
						strKekkaScott =JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(tmpKenshinKekka.getKomokuMeisho(
								PrintDefine.CODE_SCOTT,kenshinKekkaData.get(i).get(
										PrintDefine.CODE_SCOTT).get(
												PrintDefine.COLUMN_NAME_KEKA_TI)).replaceFirst("\\d+:",""));
						break;
					case 2:
						strKekkaScott = KEKKA_OUTPUT_JISI_KBN_2;
						break;
				}

				// edit s.inoue 20080903
				String ganteiSyokenKekati = kenshinKekkaData.get(i).get(PrintDefine.CODE_GANTEI_SONOTA_SYOKEN).get(PrintDefine.COLUMN_NAME_KEKA_TI);

				switch (i){
				case 0:
					// edit s.inoue 20080902
					if(blnIjyoNasiFlg && strKekkaScott.equals("") && ganteiSyokenKekati.equals("")){
						strKekkaScott = PrintDefine.IJYO_NASHI;
					}
					if (!strKekkaScott.equals("")){
						eyeGround = eyeGround + PrintDefine.VALUE_SCOTT +
						strKekkaScott+JApplication.LINE_SEPARATOR ;
						form.setField("T231",eyeGround);
						blnIjyoNasiFlg=false;
					}
					break;
				case 1:
					// edit s.inoue 20080902
					if(blnIjyoNasiFlg && strKekkaScott.equals("") && ganteiSyokenKekati.equals("")){
						strKekkaScott = PrintDefine.IJYO_NASHI;
					}
					if (!strKekkaScott.equals("")){
						eyeGround = eyeGround + PrintDefine.VALUE_SCOTT +
						strKekkaScott+JApplication.LINE_SEPARATOR ;
						form.setField("T504",eyeGround);
						blnIjyoNasiFlg=false;
					}
					break;
				case 2:
					// edit s.inoue 20080902
					if(blnIjyoNasiFlg && strKekkaScott.equals("") && ganteiSyokenKekati.equals("")){
						strKekkaScott = PrintDefine.IJYO_NASHI;
					}
					if (!strKekkaScott.equals("")){
						eyeGround = eyeGround + PrintDefine.VALUE_SCOTT +
						strKekkaScott+JApplication.LINE_SEPARATOR ;
						form.setField("T509",eyeGround);
						blnIjyoNasiFlg=false;
					}
					break;
				}
				//			}
		} catch	(Exception e){
		}
		// 眼底検査
		try {
			String ganteiSyokenKekati = kenshinKekkaData.get(i).get(PrintDefine.CODE_GANTEI_SONOTA_SYOKEN).get(PrintDefine.COLUMN_NAME_KEKA_TI);

			if (!ganteiSyokenKekati.isEmpty()) {
				switch (i){
				case 0:
					eyeGround = eyeGround + dataItemMap.get(
							PrintDefine.CODE_GANTEI_SONOTA_SYOKEN).get(PrintDefine.COLUMN_NAME_KEKA_TI) ;
					form.setField("T231",eyeGround);
					break;
				case 1:
					eyeGround = eyeGround + (kenshinKekkaData.get(1).get(
							PrintDefine.CODE_GANTEI_SONOTA_SYOKEN).get(PrintDefine.COLUMN_NAME_KEKA_TI));
					form.setField("T504",eyeGround);
					break;
				case 2:
					eyeGround = eyeGround + (kenshinKekkaData.get(2).get(
							PrintDefine.CODE_GANTEI_SONOTA_SYOKEN).get(PrintDefine.COLUMN_NAME_KEKA_TI));
					form.setField("T509",eyeGround);
					break;
				}
			}
//			eyeGround = "";
		} catch (Exception e) {
		}
// del s.inoue 20080903
//		// eyeGroundがemptyなら「所見なし」をセット
//		try {
//			if(eyeGround.isEmpty()&&!kensaNengapiAll[i].isEmpty()){
//				switch (i){
//				case 0:
//					form.setField("T231",PrintDefine.SYOKEN_NASHI);
//					break;
//				case 1:
//					form.setField("T504",PrintDefine.SYOKEN_NASHI);
//					break;
//				case 2:
//					form.setField("T509",PrintDefine.SYOKEN_NASHI);
//					break;
//				}
//			}
//		} catch (Exception e) {
//		}
		eyeGround = "";//初期化
	}
	/**
	 * @param strValues
	 * @param ketuatuValue
	 * @return
	 */
	private String hanteiValue(
			String strValues, String ketuatuValue,
			String normal, String abnormal) {
		if (! ketuatuValue.isEmpty()) {
			if(ketuatuValue.equals(PrintDefine.ONE)){
				strValues = normal;
			}else if (ketuatuValue.equals(PrintDefine.TWO)){
				strValues = abnormal;
			}
			strValues = strValues + JApplication.LINE_SEPARATOR;
		}
		return strValues;
	}

	/**
	 * ページ番号を印字する。
	 *
	 * @param kojinData
	 * @param form
	 * @param mySelfPage
	 * @throws IOException
	 * @throws DocumentException
	 */
//	private void createPage(Hashtable<String, String> kojinData, AcroFields form, int mySelfPage)
//	throws IOException, DocumentException {
//		List data = addMedical.tuika(kojinData);
//		int size = data.size();
//		int intDivid = size/88;
//		int intTooMuch = size%88;
//		String strBunbo = Integer.toString(intDivid+1);
//		String strBunbo1 = Integer.toString(intDivid+2);
//		int dispNum = mySelfPage - 1;
//		if(intDivid>0){
//			if(intTooMuch>0){
//				form.setField("PAGE", mySelfPage+"/"+strBunbo1);
//				form.setField("HAED_P", PrintDefine.PAGE_ITEM_LEFT+ dispNum+PrintDefine.PAGE_ITEM_RIGHT);
//			}else{
//				form.setField("PAGE", mySelfPage+"/"+strBunbo);
//				form.setField("HAED_P", PrintDefine.PAGE_ITEM_LEFT+ dispNum+PrintDefine.PAGE_ITEM_RIGHT);
//			}
//		}
//		if(size==0){
//			form.setField("PAGE", PrintDefine.PAGE_ITEM_1);
//		}
//	}

	/**
	 * 3枚目を作成する。
	 *
	 * @param KikanData
	 * @param kojinData
	 * @param kensaNengapi
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
//	private void maek3Page(Hashtable<String, String> KikanData, Hashtable<String, String> kojinData, String kensaNengapi ) throws IOException, FileNotFoundException, DocumentException {
//		PdfReader reader = new PdfReader(PrintDefine.WORK_PDF_INKEKKA3PAGE_PDF);
//		FileOutputStream fileStream = new FileOutputStream(PrintDefine.WORK_PDF_TMP_INKEKKA3PAGE_PDF);
//		PdfStamper st = new PdfStamper(reader, fileStream);
//		AcroFields fm = st.getAcroFields();
//
//		/* 機関情報を出力する。 */
//		this.printKikanData(KikanData, fm);
//		/* 個人情報を出力する。 */
//		this.printKojinData(kojinData, kensaNengapi, fm);
//		// 追加の項目
//		additionCheckUp(kojinData, fm, 88);
//		//ページの印字
//		this.createPage(kojinData, fm, 3);
//
//		st.setFormFlattening(true);
//		st.close();
//		fileStream.close();
//	}

	/**
	 * 4枚目を作成する。
	 *
	 * @param KikanData
	 * @param kojinData
	 * @param kensaNengapi
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
//	private void maek4Page(Hashtable<String, String> KikanData, Hashtable<String, String> kojinData, String kensaNengapi ) throws IOException, FileNotFoundException, DocumentException {
//		PdfReader reader = new PdfReader(PrintDefine.WORK_PDF_INKEKKA4PAGE_PDF);
//		FileOutputStream fileStream = new FileOutputStream(PrintDefine.WORK_PDF_TMP_INKEKKA4PAGE_PDF);
//		PdfStamper st = new PdfStamper(reader, fileStream);
//		AcroFields fm = st.getAcroFields();
//
//		/* 機関情報を出力する。 */
//		this.printKikanData(KikanData, fm);
//		/* 個人情報を出力する。 */
//		this.printKojinData(kojinData, kensaNengapi, fm);
//		// 追加の項目
//		additionCheckUp(kojinData, fm, 176);
//		//ページの印字
//		this.createPage(kojinData, fm, 4);
//
//		st.setFormFlattening(true);
//		st.close();
//		fileStream.close();
//	}

	/**
	 * 引数によりＰＤＦを結合するか判断して結合する。
	 */
	private void unitPdf(int juge, List filePathList) {
		PdfReader addpage1 ;
		PdfReader addpage2 ;
		PdfReader addpage3 ;
		PdfReader addpage4 ;
		// edit s.inoue 2009/12/29
		PdfReader addpage5 ;

		int fileAddCnt = filePathList.size();

		try {
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(printFile));
			addpage1 = new PdfReader(PrintDefine.WORK_PDF_TMP_DUMMY_PDF);

			copy.addDocument(addpage1);

			for(int i = 0;i<fileAddCnt;i++){
				PdfReader reader = new PdfReader((String)filePathList.get(i));
				copy.addDocument(reader);
				new File((String)filePathList.get(i)).deleteOnExit();
				new File((String)filePathList.get(i)).deleteOnExit();
				reader.close();
			}

			// edit s.inoue 2009/12/29
			if(juge==1){
				addpage2 = new PdfReader(PrintDefine.WORK_PDF_DUMMY2_PDF);
				copy.addDocument(addpage2);
			}else if(juge==2){
				addpage2 = new PdfReader(PrintDefine.WORK_PDF_DUMMY2_PDF);
				addpage3 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA2PAGE_PDF);
				copy.addDocument(addpage2);
				copy.addDocument(addpage3);
			}else if(juge==3){
				addpage2 = new PdfReader(PrintDefine.WORK_PDF_DUMMY2_PDF);
				addpage3 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA2PAGE_PDF);
				addpage4 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA3PAGE_PDF);
				copy.addDocument(addpage2);
				copy.addDocument(addpage3);
				copy.addDocument(addpage4);
			}else if(juge==4){
				addpage2 = new PdfReader(PrintDefine.WORK_PDF_DUMMY2_PDF);
				addpage3 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA2PAGE_PDF);
				addpage4 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA3PAGE_PDF);
				addpage5 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA4PAGE_PDF);
				copy.addDocument(addpage2);
				copy.addDocument(addpage3);
				copy.addDocument(addpage4);
				copy.addDocument(addpage5);
			}
//			if(juge==1){
//				addpage2 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA2PAGE_PDF);
//				copy.addDocument(addpage2);
//			}else if(juge==2){
//				addpage2 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA2PAGE_PDF);
//				addpage3 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA3PAGE_PDF);
//				copy.addDocument(addpage2);
//				copy.addDocument(addpage3);
//			}else if(juge==3){
//				addpage2 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA2PAGE_PDF);
//				addpage3 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA3PAGE_PDF);
//				addpage4 = new PdfReader(PrintDefine.WORK_PDF_TMP_INKEKKA4PAGE_PDF);
//				copy.addDocument(addpage2);
//				copy.addDocument(addpage3);
//				copy.addDocument(addpage4);
//			}
			copy.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 追加の健診をセットする
	 *
	 * @param kojinData
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 */
//	private int additionCheckUp(Hashtable<String, String> kojinData, AcroFields form, int cnt)
//	throws IOException, DocumentException {
//
//		int retn = 0;
//
//		StringBuffer buffer;
//		// 項目名
//		List data = addMedical.tuika(kojinData);
////		System.out.println(data);
//		int size = data.size();
//		if(size<1){
//			return retn ;
//		}else if (size<=88){
//			retn = 1;
//		}else if (88 < size  && size <= 176 ){
//			retn = 2;
//		}else if (size>176){
//			retn = 3;
//		}
//		if (size > 0) {
//			for (int i = cnt; i < size; i++) {
//				Hashtable<String, String> resultItem =
//					(Hashtable<String, String>)data.get(i);
//
//				form.setField("N" + Integer.toString(i + 1), resultItem
//						.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//
//				/* Modified 2008/05/08 若月  */
//				String tani = resultItem.get("TANI");
//
//				String displayTani = TANI_MAP.get(tani);
//				if (tani != null) {
//					tani = displayTani;
//				}
//
//				if (tani != null && ! tani.isEmpty()) {
//					buffer = new StringBuffer();
//					buffer.append("(");
//					buffer.append(tani);
//					buffer.append(")");
//					String pdfTani = buffer.toString();
//					form.setField("M" + Integer.toString(i + 1), pdfTani);
//				}
//				/* --------------------------------------------------- */
//
//				String kekati = resultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//				String kekaCntString = "O" + Integer.toString(i + 1);
//
//				try {
//					int dataType = Integer.parseInt(resultItem.get("DATA_TYPE"));
//
//					if(!kekati.isEmpty()){
//						/*
//						 * 判別 1=>数値, 2=>コード, 3=>文字列
//						 */
//						/* コード値 */
//						if (dataType == 2) {
//							String code_name = addMedical.getCodeName(
//									resultItem.get(PrintDefine.CODE_KOUMOKU),
//									Integer.valueOf(kekati));
//
//							code_name = code_name.replaceFirst("\\d+:", "");
//
//							form.setField(kekaCntString,code_name);
//
//							/* 数値または文字列 */
//						} else {
//							/* 数値 */
//							if (dataType == 1) {
//								if (kekati.startsWith(".")) {
//									kekati = "0" + kekati;
//								}
//
//								/* HL判定 */
//								String hl = "";
//								String hlCode = resultItem.get(PrintDefine.STR_H_L).toString();
//								if (hlCode != null && ! hlCode.isEmpty()) {
//									if (hlCode.equals(PrintDefine.TWO)) {
//										hl = "H";
//									}
//									else if(hlCode.equals(PrintDefine.ONE)){
//										hl = "L";
//									}
//								}
//								/* --------------------------------------------------- */
//
//								form.setField("A" + Integer.toString(i + 1), hl);
//							}
//
//							form.setField(kekaCntString,kekati);
//						}
//					}
//				} catch (NumberFormatException ee) {
//
//					form.setField(kekaCntString,kekati);
//				}
//			}
//		}
//		return retn;
//	}

	/**
	 * 既往歴、服薬歴、喫煙歴、飲酒、自覚症状、他覚症状
	 * 総合コメントを印字する。
	 *
	 * @param form
	 * @param tmp
	 * @param tmpKenshinKekka
	 * @param dataItemMap
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void printKiourekiGroup(AcroFields form, ArrayList<String> tmp,
			KenshinKekka tmpKenshinKekka, Hashtable<String, Hashtable<String, String>> dataItemMap)
	throws IOException, DocumentException {

		StringBuffer buffer;
		/* 既往歴（全体） */
		ArrayList<String> kiourekiList = new ArrayList<String>();

		/* 既往歴１（脳血管） */
		tmp.clear();
		tmp.add(PrintDefine.CODE_KIOUREKI1_NOKEKAN);

		String kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null && kekati.equals(PrintDefine.ONE)) {
			kiourekiList.add(PrintDefine.CEREBRAL_BLOOD_VESSEL);
		// add h.yoshitama 2009/11/24
		}else if(kekati != null && kekati.equals(PrintDefine.TWO)) {
			kiourekiList.add(PrintDefine.NO_CEREBRAL_BLOOD_VESSEL);
		}

		/* 既往歴２（心血管） */
		tmp.clear();
		tmp.add(PrintDefine.CODE_KIOUREKI2_SINKEKAN);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null && kekati.equals(PrintDefine.ONE)) {
			kiourekiList.add(PrintDefine.CARDIOVASCULAR_SYSTEM);
		}else if(kekati != null && kekati.equals(PrintDefine.TWO)) {
			kiourekiList.add(PrintDefine.NO_CARDIOVASCULAR_SYSTEM);
		}

		/* 既往歴３（腎不全・人工透析） */
		tmp.clear();
		tmp.add(PrintDefine.CODE_KIOUREKI3_ZINHUZEN_ZINKOTOSEKI);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null && kekati.equals(PrintDefine.ONE)) {
			kiourekiList.add(PrintDefine.LIVER_FAILURE);
		}else if(kekati != null && kekati.equals(PrintDefine.TWO)) {
			kiourekiList.add(PrintDefine.NO_LIVER_FAILURE);
		}

		/* 貧血 */
		tmp.clear();
		tmp.add(PrintDefine.CODE_HINKETU);

		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null && kekati.equals(PrintDefine.ONE)) {
			kiourekiList.add(PrintDefine.ANEMIA);
		// add h.yoshitama 2009/11/24
		}else if(kekati != null && kekati.equals(PrintDefine.TWO)) {
			kiourekiList.add(PrintDefine.NO_ANEMIA);
		}

		/* 既往歴 */
		tmp.clear();
		tmp.add(PrintDefine.CODE_KIOREKI);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati.equals(PrintDefine.ONE)) {

			/* （具体的な既往歴） */
			tmp.clear();
			tmp.add(PrintDefine.CODE_GUTAITEKINA_KIOUREKI);

			kekati = tmpKenshinKekka.getKekati(tmp, 0);
			if (kekati != null && ! kekati.isEmpty()) {
				String[] kiourekis = kekati.split(JApplication.LINE_SEPARATOR);
				for (int i = 0; i < kiourekis.length; i++) {
					kiourekiList.add(kiourekis[i]);
				}
			}
		// add h.yoshitama 2009/11/24
		}else if (kekati.equals(PrintDefine.TWO)){
			/* （具体的な既往歴） */
			tmp.clear();
			tmp.add(PrintDefine.CODE_GUTAITEKINA_KIOUREKI);

			kekati = tmpKenshinKekka.getKekati(tmp, 0);
			if (kekati != null && ! kekati.isEmpty()) {
				kiourekiList.add(PrintDefine.NO_TOKKIJIKO);

			}
		}

		buffer = new StringBuffer();
		for (int i = 0; i < kiourekiList.size() && i < 6; i++) {
			String line = kiourekiList.get(i);
			buffer.append(line);
			if (i < 5) {
				buffer.append(JApplication.LINE_SEPARATOR);
			}
		}

		form.setField("T171", buffer.toString());

		// 自覚症状
		// edit h.yoshitama 2009/11/24
		tmp.clear();
		tmp.add(PrintDefine.CODE_ZIKAKU_UMU);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		Hashtable<String, String> item = dataItemMap.get(
				PrintDefine.CODE_ZIKAKUSYOJOU);
		if (kekati.equals(PrintDefine.ONE)) {
			if (item != null) {
				form.setField("T179", item.get(PrintDefine.COLUMN_NAME_KEKA_TI));
			}
		}else if(kekati.equals(PrintDefine.TWO)) {
			form.setField("T179", PrintDefine.NO_TOKKIJIKO);
		}


		// 他覚症状
		// edit h.yoshitama 2009/11/24
		tmp.clear();
		tmp.add(PrintDefine.CODE_TAKAKU_UMU);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		item = dataItemMap.get(PrintDefine.CODE_TAKAKUSYOJOU);
		if (kekati.equals(PrintDefine.ONE)) {
			if (item != null) {
				form.setField("T181", item.get(PrintDefine.COLUMN_NAME_KEKA_TI));
			}
		}else if(kekati.equals(PrintDefine.TWO)) {
			form.setField("T181", PrintDefine.NO_TOKKIJIKO);
		}

		/* 服薬歴 */
		this.setFukuyakuToFields(form, tmpKenshinKekka);

		// 喫煙歴
		item = dataItemMap.get(PrintDefine.CODE_KITUENREKI);
		if (item != null) {
			String codeName = item.get("CODE_NAME");
			if (codeName != null) {
				String kitsuenreki = codeName.replaceFirst("\\d+:", "");

				if (kitsuenreki != null && ! kitsuenreki.isEmpty()) {
					if (kitsuenreki.equals(PrintDefine.ANSWER_YES)) {
						kitsuenreki = PrintDefine.VALUES_ARI;
					}
					else {
						kitsuenreki = PrintDefine.VALUES_NASHI;
					}

					form.setField("T178", kitsuenreki);
				}
			}
		}

		// 飲酒
		String keka = tmpKenshinKekka.getMonshin(0,PrintDefine.CODE_INSYU);
		if (keka != null) {
			form.setField("T273", keka.replaceFirst("\\d+:", ""));
		}
		/* --------------------------------------------------- */

		/* Added 2008/05/08 若月  */
		/* --------------------------------------------------- */
		/* 総合コメント */
		String comment = tmpKenshinKekka.getComment();

		if (comment != null && ! comment.isEmpty()) {
//			comment = comment.replaceAll("[改行]", JApplication.LINE_SEPARATOR);

			form.setField("AA1",PrintDefine.GENERAL_COMMENT + JApplication.LINE_SEPARATOR + comment);
		}
		/* --------------------------------------------------- */
	}

	private void printKojinData(Hashtable<String, String> kojinData, String kensaNengapi, AcroFields form) throws IOException, DocumentException {

		StringBuffer buffer = null;

		// フリガナ
		form.setField("T164", kojinData.get("KANANAME"));

		// 生年月日
		String birthday = kojinData.get("BIRTHDAY");

		String displayBirthday = this.getYmdRemoved0(birthday);

		form.setField("T166", displayBirthday);
		/* --------------------------------------------------- */


		// 健診年月日
		buffer = new StringBuffer();
		String displayKensaNengapi = null;

		if (kensaNengapi.length() == 8) {

			String year = kensaNengapi.substring(0, 4);
			year = year.replaceAll("^0+", " ");
			buffer.append(year);
			buffer.append("年");

			String month = kensaNengapi.substring(4, 6);
			month = month.replaceAll("^0", " ");
			buffer.append(month);
			buffer.append("月");

			String day = kensaNengapi.substring(6, 8);
			day = day.replaceAll("^0", " ");
			buffer.append(day);
			buffer.append("日");

			displayKensaNengapi = buffer.toString();
		}
		else {
			displayKensaNengapi = kensaNengapi;
		}

		form.setField("T169", displayKensaNengapi);

		// 性別・年齢
		form.setField("T167", kojinData.get("SEX"));
		form.setField("T168", kojinData.get("AGE"));


		// 受診券整理番号
		form.setField("T170", kojinData.get("JYUSHIN_SEIRI_NO"));
	}


	private String getYmdRemoved0(String ymd) {

		// Pattern p = Pattern.compile("^([^\\d]*)(\\d+)年(\\d+)月(\\d+)日$");
		Pattern p = Pattern.compile("([^\\d]*)(\\d+)年(\\d+)月(\\d+)日");
		Matcher m = p.matcher(ymd);
		m.matches();

		int groupCount = m.groupCount();

		String displayBirthday = null;

		if (groupCount == 4) {
			StringBuffer buffer = new StringBuffer();

			String gengo = null;
			String year = null;
			String month = null;
			String day = null;

			gengo = m.group(1);
			year = m.group(2).replaceAll("^0+", " ");
			month = m.group(3).replaceAll("^0", " ");
			day = m.group(4).replaceAll("^0", " ");

			if (! gengo.isEmpty()) {
				/* 和暦の場合 */
				if (year.length() == 1) {
					year = " " + year;
				}

				buffer.append(gengo);
			}

			if (month.length() == 1) {
				month = " " + month;
			}
			if (day.length() == 1) {
				day = " " + day;
			}

			buffer.append(year);
			buffer.append("年");
			buffer.append(month);
			buffer.append("月");
			buffer.append(day);
			buffer.append("日");

			displayBirthday = buffer.toString();

		} else {
			displayBirthday = ymd;
		}
		return displayBirthday;
	}

	/**
	 * @param KikanData
	 * @param form
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void printKikanData(Hashtable<String, String> KikanData, AcroFields form) throws IOException, DocumentException {
		// 健診機関名
		form.setField("T222", KikanData.get("KIKAN_NAME"));
		// 郵便番号
		String postNo = KikanData.get("POST");
		if (postNo != null) {
			postNo = "〒" + postNo.substring(0, 3) + "-"
			+ postNo.substring(3, 7);
		}

		form.setField("T223", postNo);

		/* Modified 2008/06/16 若月 画面端に印字しないようにするため、
		 * 1 個のフォームに住所、地番方書を出力する。 */
		// String jusyoAll = KikanData.get("ADR") + KikanData.get("TIBAN");
		/* 住所 + 地番方書 */
		// form.setField("T225", jusyoAll);
		form.setField("T225", KikanData.get("ADR"));
		form.setField("T228", KikanData.get("TIBAN"));
		/* --------------------------------------------------- */

		// 電話番号
		form.setField("T226", KikanData.get("TEL"));
	}


	private void setKijyuntiToForm(
			AcroFields form,
			ArrayList<String> tmp,
			KenshinKekka tmpKenshinKekka,
			String formId,
			boolean isMale
	) throws IOException, DocumentException {


		/* 基準値下限 */
		String kagen = null;

		/* 基準値上限 */
		String jyogen = null;
		if (isMale) {
			kagen = tmpKenshinKekka.getDsKagen(tmp, 0);
			jyogen = tmpKenshinKekka.getDsJyogen(tmp, 0);
		}
		else {
			kagen = tmpKenshinKekka.getJsKagen(tmp, 0);
			jyogen = tmpKenshinKekka.getJsJyogen(tmp, 0);
		}

		StringBuffer buffer = new StringBuffer();
		if (kagen != null && ! kagen.isEmpty()) {
			buffer.append(kagen);
		}

		buffer.append("～");

		if (jyogen != null && ! jyogen.isEmpty()) {
			buffer.append(jyogen);
		}

		if (! kagen.isEmpty() || ! jyogen.isEmpty()) {
			String kijyunti = buffer.toString();
			form.setField(formId, kijyunti);
		}
	}

	// edit start s.inoue 20081117
	private Map setAllToForm(
			AcroFields form,
			ArrayList<String> codes,
			KenshinKekka tmpKenshinKekka,
			boolean isMale,
			String[][] forms

	) throws IOException, DocumentException {
		Map<String,ArrayList<String>>rtnResultMap  = new HashMap<String,ArrayList<String>>();
		ArrayList<String> rtnResultKekkaTi = new ArrayList<String> ();
		ArrayList<String> rtnResultH_L = new ArrayList<String> ();
		ArrayList<String> tmpcodes=new ArrayList<String>();

		// 画面の項目順にソートする
		if (codes.contains("9A755000000000001") ||
				codes.contains("9A765000000000001") ||
					codes.contains("1A020000000191111") ||
						codes.contains("1A010000000191111")){
			Collections.sort(codes);
		}else if (codes.contains("3D010000001926101")){
			codes.clear();
			tmpcodes.add("3D010000001999901");
			tmpcodes.add("3D010000001927201");
			tmpcodes.add("3D010000002227101");
			tmpcodes.add("3D010000001926101");
			codes.addAll(tmpcodes);
		}else if (codes.contains("3D010129901926101")){
			codes.clear();
			tmpcodes.add("3D010129901999901");
			tmpcodes.add("3D010129901927201");
			tmpcodes.add("3D010129902227101");
			tmpcodes.add("3D010129901926101");
			codes.addAll(tmpcodes);
		}else{
			Comparator r = Collections.reverseOrder();
			Collections.sort(codes,r);
		}

		/*
		 * 結果値と H/L を出力する。
		 */
		String kekati ="";
		String jisiKbn="";
		String kagen = "";
		String jyogen = "";

		// eidt s.inoue 2011/09/13
		// 0→3を3→0へ修正
		for (int i = 0; i < 3; i++) {
		// for (int i = 2; i >= 0; i--) {

			/*
			 * 結果値
			 */
			String strOld = "";

			Hashtable<String,String> kekkaData= null;

			kekkaData = tmpKenshinKekka.getKekkData(codes, i, isMale);
			if (kekkaData.size()<=0)
			{
				continue;
			}

			// 結果をセット
			jisiKbn = kekkaData.get("JISI_KBN");
			kekati = kekkaData.get("KEKA_TI");
			kagen = kekkaData.get("KAGEN");
			jyogen = kekkaData.get("JYOGEN");

			if (!kekati.equals("")){
				// edit s.inoue 2013/01/28
				int tDate = 20130401;
				String sDate = tmpKenshinKekka.getKensaNengapi()[i];

				// 健診実施日が'130401以降
				if (sDate != null){
					sDate = sDate.replace("年", "").replace("月", "").replace("日", "");
//					if (tDate <= Integer.parseInt(sDate)){
//						PrintDefine.CODE_HBA1C_SONOTA = PrintDefine.CODE_HBA1C_SONOTA_NGSP;
//						PrintDefine.CODE_HBA1C_COUSOHO = PrintDefine.CODE_HBA1C_COUSOHO_NGSP;
//						PrintDefine.CODE_HBA1C_HPLC = PrintDefine.CODE_HBA1C_HPLC_NGSP;
//						PrintDefine.CODE_HBA1C_RATEX = PrintDefine.CODE_HBA1C_RATEX_NGSP;
//					}

					if ((codes.contains(PrintDefine.CODE_HBA1C_RATEX_JDS)
						||codes.contains(PrintDefine.CODE_HBA1C_HPLC_JDS)
						||codes.contains(PrintDefine.CODE_HBA1C_COUSOHO_JDS)
						||codes.contains(PrintDefine.CODE_HBA1C_SONOTA_JDS))&&
					(PrintDefine.CODE_HBA1C_RATEX.equals(PrintDefine.CODE_HBA1C_RATEX_JDS)
					|| PrintDefine.CODE_HBA1C_HPLC.equals(PrintDefine.CODE_HBA1C_HPLC_JDS)
					|| PrintDefine.CODE_HBA1C_COUSOHO.equals(PrintDefine.CODE_HBA1C_COUSOHO_JDS)
					|| PrintDefine.CODE_HBA1C_SONOTA.equals(PrintDefine.CODE_HBA1C_SONOTA_JDS)))
						strOld = " ☆";
				}

				// eidt s.inoue 2012/09/10
				form.setField(forms[2][i], kekati.replaceFirst("\\d+:", "")+ strOld);
			}

			boolean existJougen = false;
			boolean existkagen = false;

			StringBuffer buffer = new StringBuffer();
			if (kagen != null && ! kagen.isEmpty()) {
				existkagen = true;
				buffer.append(kagen);
			}

			buffer.append("～");

			if (jyogen != null && ! jyogen.isEmpty()) {
				existJougen = true;
				buffer.append(jyogen);
			}

			/* 印字するフォームが存在する場合は、印字する。 */
			if (forms[0] != null) {
				if (existkagen || existJougen) {
					String kijyunti = buffer.toString();
					form.setField(forms[0][0], kijyunti);
				}
			}

			/*
			 *  H/L判定
			 */
			String hl = null;

			if (jisiKbn.equals("1") &&
					!kekati.equals(""))
			{
				try {
					double kekatiDouble = Double.parseDouble(kekati);

					/* 下限値が存在する場合 */
					if (existkagen) {
						double kagenDouble = Double.parseDouble(kagen);

						/* 結果値が下限値以下の場合は「L」 */
						if (kekatiDouble < kagenDouble) {
							hl = "L";
						}
						/* 結果値が下限値より大きい場合 */
						else  {
							/* 上限値が存在する場合 */
							if (existJougen) {
								double jyougenDouble = Double.parseDouble(jyogen);

								/* 結果値が上限値より大きい場合は「H」 */
								if (kekatiDouble > jyougenDouble) {
									hl = "H";
								}
							}
						}
					}
					/* 下限値が存在しない場合 */
					else {
						/* 上限値のみが存在する場合 */
						if (existJougen) {
							double jyougenDouble = Double.parseDouble(jyogen);

							/* 結果値が上限値より大きい場合は「H」 */
							if (kekatiDouble > jyougenDouble) {
								hl = "H";
							}
						}
					}

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				form.setField(forms[1][i], hl);

			}
			// edit s.inoue 20080904
			//戻り値の二つ目にH/L追加
			rtnResultH_L.add(hl);

			// eidt s.inoue 2011/09/13 0番目(今回)のみへ変更
			if ( i == 0)
			rtnResultKekkaTi.add(kekati);
		}
		// move s.inoue 2011/09/13
//		rtnResultKekkaTi.add(kekati);

		rtnResultMap.put("HL", rtnResultH_L);
		rtnResultMap.put("Value", rtnResultKekkaTi);

		return rtnResultMap;
	}
	// edit end s.inoue 20081117

	private void setFukuyakuToFields(AcroFields form, KenshinKekka tmpKenshinKekka)
	throws IOException, DocumentException {
		// add h.yoshitama 2009/11/24
		boolean ketsuatsu = false;
		boolean ketto = false;
		boolean shishitsu = false;

		if (tmpKenshinKekka == null) {
			return;
		}

		ArrayList<String> tmp = new ArrayList<String>();
		tmp.clear();

		// 服薬歴
		ArrayList<String> hukuyakuList = new ArrayList<String>();

		// 血圧を下げる薬を服用している
		tmp.add(PrintDefine.FUKUTO_BLODD);
		String kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null) {
			if (kekati.equals(PrintDefine.ONE)) {
				hukuyakuList.add(PrintDefine.NAME_KETUATU);
			// add h.yoshitama 2009/11/24
			}else if (kekati.equals(PrintDefine.TWO)) {
				ketsuatsu = true;
			}
		}

		// インスリン注射又は血糖を下げる薬を使用している
		tmp.clear();
		tmp.add(PrintDefine.INSURIN);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null) {
			if (kekati.equals(PrintDefine.ONE)) {
				hukuyakuList.add(PrintDefine.NAME_KETTOU);
			// add h.yoshitama 2009/11/24
			}else if (kekati.equals(PrintDefine.TWO)) {
				ketto = true;
			}
		}

		// 1-3 コレステロールを下げる薬を服用している
		tmp.clear();
		tmp.add(PrintDefine.COLESTEROLL_DOWN);
		kekati = tmpKenshinKekka.getKekati(tmp, 0);
		if (kekati != null) {
			if (kekati.equals(PrintDefine.ONE)) {
				hukuyakuList.add(PrintDefine.NAME_SHISHITU);
			// add h.yoshitama 2009/11/24
			}else if (kekati.equals(PrintDefine.TWO)) {
				shishitsu = true;
			}
		}
		/* --------------------------------------------------- */

		StringBuffer buffer = new StringBuffer();
		for (Iterator<String> iter = hukuyakuList.iterator(); iter.hasNext();) {
			String item = iter.next();
			buffer.append(item);

			if (iter.hasNext()) {
				buffer.append("　");
			}
		}

		String fukuyakureki = buffer.toString();
		// edit h.yoshitama 2009/11/24
		if(ketsuatsu && ketto && shishitsu){
			form.setField("T177", PrintDefine.NO_HUKUYAKU);
		}else{
			form.setField("T177", fukuyakureki);
		}
	}

	/**
	 * 健診結果通知表を印刷する。
	 */
	public void printResultCard(String kensaNengapi, String uketukeId,
			String hihokenjyaCode, String hihokenjyaNumber,
			String hokenjyaNumber, JFrame owner, int printSelect) {

		// edit s.inoue 2009/12/25
		this.printSelect = printSelect;

		/* 進捗通知画面を表示する。 */
		ProgressWindow progressWindow = new ProgressWindow(owner, false, true);
		progressWindow.setGuidanceByKey("tokutei.showresult.frame.progress.guidance.print1");
		progressWindow.setVisible(true);

		try {
			/*
			 * Kojin作成 HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, KENSA_NENGAPI
			 */
			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

			// 受付ID
			tmpKojin.put("UKETUKE_ID", uketukeId);

			// 被保険者証等記号
			tmpKojin.put("HIHOKENJYASYO_KIGOU", hihokenjyaCode);
			// 被保険者証等番号
			tmpKojin.put("HIHOKENJYASYO_NO", hihokenjyaNumber);
			// 健診年月日
			tmpKojin.put("KENSA_NENGAPI", kensaNengapi);

			Kojin KojinData = new Kojin();
			if (!KojinData.setPrintKojinDataSQL(tmpKojin)) {
				// データ移送失敗
				JErrorMessage.show("M4940", owner);
			}

			/*
			 * Kikan作成
			 */
			Kikan kikanData = new Kikan();
			if (!kikanData.setPrintKikanDataSQL()) {
				// データ移送失敗
				JErrorMessage.show("M4941", owner);
			}

			/*
			 * KensaKekka作成 HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO,
			 * KENSA_NENGAPI, HKNJANUM
			 */
			Hashtable<String, String> tmpKenshinKekka =
				new Hashtable<String, String>();

			// 受付ID
			tmpKenshinKekka.put("UKETUKE_ID", uketukeId);
			// 被保険者証等記号
			tmpKenshinKekka.put("HIHOKENJYASYO_KIGOU", hihokenjyaCode);
			// 被保険者証等番号
			tmpKenshinKekka.put("HIHOKENJYASYO_NO", hihokenjyaNumber);
			// 健診年月日
			tmpKenshinKekka.put("KENSA_NENGAPI", kensaNengapi);
			// 保険者番号
			tmpKenshinKekka.put("HKNJANUM", hokenjyaNumber);

			KenshinKekka kenshinKekkaData = new KenshinKekka();
			if (!kenshinKekkaData.setPrintKenshinKekkaSQL(tmpKenshinKekka)) {
				// データ移送失敗
				JErrorMessage.show("M4942", owner);
			}

			/*
			 * 印刷データ生成
			 */
			Hashtable<String, Object> printData = new Hashtable<String, Object>();
			printData.put("Kikan", kikanData);
			printData.put("Kojin", KojinData);
			printData.put("KenshinKekka", kenshinKekkaData);
			/*
			 * 印刷 1ページ目を印刷すると、自動的に最終ページまで出力される
			 */
			this.setQueryResult(printData);

			progressWindow.setVisible(false);

			try {
				this.beginPrint();

			} catch (PrinterException err) {
				err.printStackTrace();
				JErrorMessage.show("M4943", owner);
			}
		// eidt s.inoue 2013/05/23
		} catch (Exception err) {
			err.printStackTrace();
			logger.error(err.getMessage());

		} finally {
			progressWindow.setVisible(false);
		}
	}
}
