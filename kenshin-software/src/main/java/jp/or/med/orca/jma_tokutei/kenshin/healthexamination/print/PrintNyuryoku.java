package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KihonKensaKoumoku;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 入力票の項目を作成する
 */
public class PrintNyuryoku extends JTKenshinPrint {

//	private String outputPath_s = ""; // ディレクトリパス
//	private static final String OUT_NYURYOKUHYO_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_nyuryokuhyo.pdf";

	private String outputPath = ""; 							// ディレクトリパス
	private String templatePath = ""; 						// ディレクトリパス
	private String infilename = "inNyuryokuTemplate.pdf";	// 入力テンプレートファイル
	private String outfilename = "outNyuryokuTemplate.pdf";	// 出力ファイル

	private String outputPath_QA = ""; 							// ディレクトリパス
	private String templatePath_QA = ""; 						// ディレクトリパス
	private String infilename_QA = "inNyuryokuTemplate_QA.pdf";	// 入力テンプレートファイル
	private String outfilename_QA = "outNyuryokuTemplate_QA.pdf";	// 出力ファイル

	public static void main(String[] argv) {
		new PrintNyuryoku();
	}

	public PrintNyuryoku() {
	}

	PrinterJob job = PrinterJob.getPrinterJob();
	// edit s.inoue 2011/07/04
	File nyutuokuFile = null;
	File lastFile = null;

	/**
	 * 入力票の項目を作成する
	 */
	public File printKekka() {
		// eidt s.inoue 2011/04/12
		// PropertyUtil util = new PropertyUtil(JPath.PATH_FILE_PROPERTIES);
		// templatePath = util.getProperty("filePath");
		templatePath = "./work/PDF/";
		outputPath = templatePath;
		templatePath = templatePath + infilename;
		outputPath = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename;

		// edit s.inoue 2011/07/08
		templatePath_QA = "./work/PDF/";
		outputPath_QA = templatePath_QA;
		templatePath_QA = templatePath_QA + infilename_QA;
		outputPath_QA = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename_QA;

		File outfile = new File("."+File.separator+"Data"+File.separator+"PDF");

		outfile.mkdirs();

		// add s.inoue 2011/08/19
		Hashtable<String, String> kojinData = null;
		// move s.inoue 2011/08/19
		String strKensaYmd = "";
		// move s.inoue 2011/08/22
		List pdfPathHitokuteiList = new ArrayList();
		List filePathList = new ArrayList();
		try {
			/*
			 * 個人情報 PrintDataから個人情報を抽出
			 */
			Kojin tmpKojin = (Kojin) printData.get("Kojin");
			kojinData = tmpKojin.getPrintKojinData();

			if(kojinData.get("KENSA_NENGAPI")==null){
				strKensaYmd = getKenshinDate();
			}else{
				strKensaYmd = replaseNenGaPii(kojinData.get("KENSA_NENGAPI"));
			}

			// ファイル作成
			PdfReader reader = new PdfReader(templatePath);
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outputPath + kojinData.get("UKETUKE_ID")	+ strKensaYmd));
			AcroFields form = stamp.getAcroFields();

			templatePath = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename + kojinData.get("UKETUKE_ID") + strKensaYmd;
			nyutuokuFile = new File("."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename);

			// edit s.inoue 2011/07/08
			PdfReader reader_QA = new PdfReader(templatePath_QA);
			PdfStamper stamp_QA = new PdfStamper(reader_QA, new FileOutputStream(outputPath_QA + kojinData.get("UKETUKE_ID")	+ strKensaYmd));
			AcroFields form_QA = stamp_QA.getAcroFields();

			templatePath_QA = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename_QA + kojinData.get("UKETUKE_ID") + strKensaYmd;
			lastFile = new File("."+File.separator+"Data"+File.separator+"PDF"+File.separator+"" + outfilename_QA);

			// 健診年月日
			form.setField("T1", kojinData.get("KENSA_NENGAPI"));
			form_QA.setField("T19", kojinData.get("KENSA_NENGAPI"));
			// 受診券番号
			form.setField("T2", kojinData.get("JYUSHIN_SEIRI_NO"));
			form_QA.setField("T20", kojinData.get("JYUSHIN_SEIRI_NO"));
			// 保険者番号
			form.setField("T3", kojinData.get("HKNJANUM"));
			form_QA.setField("T21", kojinData.get("HKNJANUM"));
			// 被保険者証等記号
			form.setField("T4", kojinData.get("HIHOKENJYASYO_KIGOU"));
			form_QA.setField("T22", kojinData.get("HIHOKENJYASYO_KIGOU"));
			// 被保険者証等番号
			form.setField("T5", kojinData.get("HIHOKENJYASYO_NO"));
			form_QA.setField("T23", kojinData.get("HIHOKENJYASYO_NO"));
			// フリガナ
			form.setField("T6", kojinData.get("KANANAME"));
			form_QA.setField("T24", kojinData.get("KANANAME"));
			// 氏名
			form.setField("T7", kojinData.get("NAME"));
			form_QA.setField("T26", kojinData.get("NAME"));

			// 生年月日
			form.setField("T8", kojinData.get("BIRTHDAY"));
			form_QA.setField("T25", kojinData.get("BIRTHDAY"));

			// 性別
			form.setField("T9", kojinData.get("SEX"));
			form_QA.setField("T27", kojinData.get("SEX"));

			// 年齢
			form.setField("T10", kojinData.get("AGE"));
			form_QA.setField("T28", kojinData.get("AGE"));

			// move s.inoue 2011/08/22
			// クローズ処理移動

		// edit s.inoue 2011/07/04
//		List pdfPathSplitList = new ArrayList();

		PrintNyuryokuHitokutei printHitokutei = new PrintNyuryokuHitokutei();
//		Hashtable<String, String> kojinData = new Hashtable<String, String>();
		Hashtable<String, String> KikanData = new Hashtable<String, String>();

		List kensaNenList = new ArrayList();
		kensaNenList.add(strKensaYmd);

// del s.inoue 2011/08/19
//		kojinData.put("SEX","男性");
//		kojinData.put("KENSA_NENGAPI","2011年07月15日");
//		kojinData.put("UKETUKE_ID","201107280001");
//		kojinData.put("HKNJANUM","11111111");
//		kojinData.put("AGE","61歳");
//		kojinData.put("JYUSHIN_SEIRI_NO","00000000001");
//		kojinData.put("HIHOKENJYASYO_NO","あ");
//		kojinData.put("HIHOKENJYASYO_KIGOU","あ");
//		kojinData.put("UKETUKE_NENGAPI","20110410");
//		kojinData.put("KANANAME","ニチイタロウ");
//		kojinData.put("NAME","");
//		kojinData.put("BIRTHDAY","昭和25年5月1日");
//		KikanData.put("TEL","03333333333");
//		KikanData.put("ADR","東京都練馬区南大泉");
//		KikanData.put("TIBAN","");
//		KikanData.put("KIKAN_NAME","日医クリニック");
//		KikanData.put("POST","1780064");
//		KikanData.put("TKIKAN_NO","1111111111");

		pdfPathHitokuteiList = printHitokutei.startAddMedical(kensaNenList,kojinData,KikanData,"お",1);

		for (int i = 0; i < pdfPathHitokuteiList.size(); i++) {
			filePathList.add(pdfPathHitokuteiList.get(i));
		}

		// add s.inoue 2011/08/22
		// ページ番号
		String totalPage = String.valueOf(filePathList.size() + 2);
		form.setField("PAGE_NUM", "(1/" + totalPage +")");
		form_QA.setField("PAGE_NUM","(" + totalPage + "/" + totalPage +")" );

		stamp.setFormFlattening(true);
		stamp.close();
		stamp_QA.setFormFlattening(true);
		stamp_QA.close();

	} catch (Exception e) {
		e.printStackTrace();
	}



		unitPdf(0,filePathList);

		return nyutuokuFile;
	}

//	// edit s.inoue 2011/07/04
//	/**
//	 * 引数によりＰＤＦを結合するか判断して結合する。
//	 */
//	private void unitPdf(List filePathList) {
//		int fileAddCnt = filePathList.size();
//
//		try {
//			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(file));
//			for(int i = 0;i<fileAddCnt;i++){
//				PdfReader reader = new PdfReader((String)filePathList.get(i));
//				copy.addDocument(reader);
//				new File((String)filePathList.get(i)).deleteOnExit();
//				new File((String)filePathList.get(i)).deleteOnExit();
//				reader.close();
//			}
//
//			copy.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(outputPath));
			// edit s.inoue 2011/07/05
			// addpage1 = new PdfReader(PrintDefine.WORK_PDF_TMP_DUMMY_PDF);
			addpage1 = new PdfReader(templatePath);

			copy.addDocument(addpage1);

			for(int i = 0;i<fileAddCnt;i++){
				PdfReader reader = new PdfReader((String)filePathList.get(i));
				copy.addDocument(reader);
				new File((String)filePathList.get(i)).deleteOnExit();
				new File((String)filePathList.get(i)).deleteOnExit();
				reader.close();
			}

			// edit s.inoue 2011/07/08
			addpage2 = new PdfReader(templatePath_QA);
			copy.addDocument(addpage2);

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
	 * 入力票の追加の健診項目を作成する。
	 */
	private List tuika(Hashtable<String, String> KojinData) {

		ArrayList<Hashtable<String, String>> Result = null;
		Hashtable<String, String> ResultItem = null;
		String Query = null;

		ArrayList<Hashtable<String, String>> rtnList = new ArrayList<Hashtable<String, String>>();

		/* Modified 2008/07/15 若月  */
		/* --------------------------------------------------- */
//		Query = "SELECT"
//			+ " T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, "
//			+ " T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, "
//			+ " T_KENSHINMASTER.TANI AS TANI"
//			+ " From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD)"
//			+ " WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = "
//			+ KojinData.get("UKETUKE_ID")
//			+ " AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = "
//			+ KojinData.get("UKETUKE_NENGAPI")
//			+ " AND T_KENSHINMASTER.HKNJANUM = "
//			+ KojinData.get("HKNJANUM");
		/* --------------------------------------------------- */
		Query = "SELECT"
			+ " T_KENSAKEKA_SONOTA.KOUMOKU_CD AS KOUMOKU_CD, "
			+ " T_KENSHINMASTER.KOUMOKU_NAME AS KOUMOKU_NAME, "
			+ " T_KENSHINMASTER.TANI AS TANI"
			+ " From (T_KENSAKEKA_SONOTA INNER JOIN T_KENSHINMASTER ON T_KENSAKEKA_SONOTA.KOUMOKU_CD = T_KENSHINMASTER.KOUMOKU_CD)"
			+ " WHERE T_KENSAKEKA_SONOTA.UKETUKE_ID = '"
			+ KojinData.get("UKETUKE_ID")
			// edit s.inoue 2009/11/13
			// + "' AND T_KENSAKEKA_SONOTA.KENSA_NENGAPI = '"
			// + KojinData.get("UKETUKE_NENGAPI")
			+ "' AND T_KENSHINMASTER.HKNJANUM = '"
			+ KojinData.get("HKNJANUM")
			+ "'";
		/* --------------------------------------------------- */

		try {
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		KihonKensaKoumoku kihonKensaKoumoku = new KihonKensaKoumoku();
		kihonKensaKoumoku.setKenshinCD();
		for (int i = 0; i < Result.size(); i++) {
			ResultItem = Result.get(i);
			if (! kihonKensaKoumoku.TSUIKA_CODES.contains(ResultItem
					.get("KOUMOKU_CD"))) {
			} else {
				rtnList.add(Result.get(i));
			}
		}
		return rtnList;
	}

	private String replaseNenGaPii(String kensaNengappi ) {
		try {
			kensaNengappi = kensaNengappi.replaceAll("年", "");
			kensaNengappi = kensaNengappi.replaceAll("月", "");
			kensaNengappi = kensaNengappi.replaceAll("日", "");
		} catch (RuntimeException e) {
			return "";
		}
		return kensaNengappi;
	}


	// /**
	// * 印刷済み項目 add
	// * @param void
	// */
	// public void setKenshinCD() {
	// //既往歴
	// this.PrintedCD.add("9N056000000000011");
	// //自覚症状
	// this.PrintedCD.add("9N061000000000011");
	// //他覚症状
	// this.PrintedCD.add("9N066000000000011");
	// //喫煙歴
	// this.PrintedCD.add("9N736000000000011");
	// //身長
	// this.PrintedCD.add("9N001000000000001");
	// //体重
	// this.PrintedCD.add("9N006000000000001");
	// /*
	// * 腹囲（実測） 9N016160100000001
	// * 腹囲（自己判定） 9N016160200000001
	// * 腹囲（自己申告） 9N016160300000001
	// */
	// this.PrintedCD.add("9N016160100000001");
	// this.PrintedCD.add("9N016160200000001");
	// this.PrintedCD.add("9N016160300000001");
	// //BMI
	// this.PrintedCD.add("9N011000000000001");
	// /*
	// * 収縮期血圧（1回目） 9A751000000000001
	// * 収縮期血圧（2回目） 9A752000000000001
	// * 収縮期血圧（その他） 9A755000000000001
	// */
	// this.PrintedCD.add("9A751000000000001");
	// this.PrintedCD.add("9A752000000000001");
	// this.PrintedCD.add("9A755000000000001");
	// /*
	// * 拡張期血圧（一回目） 9A761000000000001
	// * 拡張期血圧（二回目） 9A762000000000001
	// * 拡張期血圧（その他） 9A765000000000001
	// */
	// this.PrintedCD.add("9A761000000000001");
	// this.PrintedCD.add("9A762000000000001");
	// this.PrintedCD.add("9A765000000000001");
	// /*
	// * 中性脂肪（可視吸光光度法（酵素比色法・グリセロール消去）） 3F015000002327101
	// * 中性脂肪（紫外吸光光度法（酵素比色法・グリセロール消去）） 3F015000002327201
	// * 中性脂肪（その他） 3F015000002399901
	// */
	// this.PrintedCD.add("3F015000002327101");
	// this.PrintedCD.add("3F015000002327201");
	// this.PrintedCD.add("3F015000002399901");
	// /*
	// * HDL-コレステロール-（可視吸光光度法（直接法（非沈殿法））） 3F070000002327101
	// * HDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法））） 3F070000002327201
	// * HDL-コレステロール-（その他） 3F070000002399901
	// */
	// this.PrintedCD.add("3F070000002327101");
	// this.PrintedCD.add("3F070000002327201");
	// this.PrintedCD.add("3F070000002399901");
	// /*
	// * LDL-コレステロール-（可視吸光光度法（直接法（非沈殿法）） 3F077000002327101
	// * LDL-コレステロール-（紫外吸光光度法（直接法（非沈殿法）） 3F077000002327201
	// * LDL-コレステロール-（その他） 3F077000002399901
	// */
	// this.PrintedCD.add("3F077000002327101");
	// this.PrintedCD.add("3F077000002327201");
	// this.PrintedCD.add("3F077000002399901");
	// /*
	// * GOT（紫外吸光光度法（JSCC標準化対応法）） 3B035000002327201
	// * GOT（その他） 3B035000002399901
	// */
	// this.PrintedCD.add("3B035000002327201");
	// this.PrintedCD.add("3B035000002399901");
	// /*
	// * GPT（紫外吸光光度法（JSCC標準化対応法）） 3B045000002327201
	// * GPT（その他） 3B045000002399901
	// */
	// this.PrintedCD.add("3B045000002327201");
	// this.PrintedCD.add("3B045000002399901");
	// /*
	// * y-GTP（可視吸光光度法（JSCC標準化対応法）） 3B090000002327101
	// * y-GTP（その他） 3B090000002399901
	// */
	// this.PrintedCD.add("3B090000002327101");
	// this.PrintedCD.add("3B090000002399901");
	// /*
	// * 空腹時血糖（電位差法（ブドウ糖酸化酵素電極法）） 3D010000001926101
	// * 空腹時血糖（可視吸光光度法（ブドウ糖酸化酵素法）） 3D010000002227101
	// * 空腹時血糖（紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）） 3D010000001927201
	// * 空腹時血糖（その他） 3D010000001999901
	// */
	// this.PrintedCD.add("3D010000001926101");
	// this.PrintedCD.add("3D010000002227101");
	// this.PrintedCD.add("3D010000001927201");
	// this.PrintedCD.add("3D010000001999901");
	// /*
	// * ﾍﾓｸﾞﾛﾋﾞﾝA1c 免疫学的方法（ラテックス凝集比濁法等） 3D045000001906202
	// * ﾍﾓｸﾞﾛﾋﾞﾝA1c HPLC(不安定分画除去HPLC法） 3D045000001920402
	// * ﾍﾓｸﾞﾛﾋﾞﾝA1c 酵素法 3D045000001927102
	// * ﾍﾓｸﾞﾛﾋﾞﾝA1c その他 3D045000001999902
	// */
	// this.PrintedCD.add("3D045000001906202");
	// this.PrintedCD.add("3D045000001920402");
	// this.PrintedCD.add("3D045000001927102");
	// this.PrintedCD.add("3D045000001999902");
	// /*
	// * 糖 試験紙法（機械読み取り） 1A020000000191111
	// * 糖 試験紙法（目視法） 1A020000000190111
	// */
	// this.PrintedCD.add("1A020000000191111");
	// this.PrintedCD.add("1A020000000190111");
	// /*
	// * 蛋白 試験紙法（機械読み取り） 1A010000000191111
	// * 蛋白 試験紙法（目視法） 1A010000000190111
	// */
	// this.PrintedCD.add("1A010000000191111");
	// this.PrintedCD.add("1A010000000190111");
	//
	// //赤血球数
	// this.PrintedCD.add("2A020000001930101");
	// //血色素数
	// this.PrintedCD.add("2A030000001930101");
	// //ヘマトクリット値
	// this.PrintedCD.add("2A040000001930102");
	// //心電図所見
	// this.PrintedCD.add("9A110160800000049");
	// //眼底検査所見
	// this.PrintedCD.add("9E100160900000049");
	// //メタボリックシンドローム判定
	// this.PrintedCD.add("9N501000000000011");
	// //医師の判断
	// this.PrintedCD.add("9N511000000000049");
	// //判断した医師名
	// this.PrintedCD.add("9N516000000000049");
	//
	// //1-1 血圧を下げる薬を服用している
	// this.PrintedCD.add("9N701000000000011");
	// //1-2 インスリン注射又は血糖を下げる薬を使用している
	// this.PrintedCD.add("9N706000000000011");
	// //1-3 コレステロールを下げる薬を服用している
	// this.PrintedCD.add("9N711000000000011");
	// //4 医師から、脳卒中（脳出血、脳梗塞等）にかかっているといわれたり、治療を受けたことがある
	// this.PrintedCD.add("9N716000000000011");
	// //5 医師から、心臓病（狭心症、心筋梗塞）にかかっているといわれたり、治療を受けたことがある
	// this.PrintedCD.add("9N721000000000011");
	// //6 医師から、慢性の腎不全にかかっているといわれたり、治療（人工透析）を受けたことがある
	// this.PrintedCD.add("9N726000000000011");
	// //7 医師から貧血といわれたことがある
	// this.PrintedCD.add("9N731000000000011");
	// //8 現在、たばこを習慣的に吸っている
	// this.PrintedCD.add("9N736000000000011");
	// //9 ２０歳の時の体重から１０キロ以上増加している
	// this.PrintedCD.add("9N741000000000011");
	// //10 １回３０分以上の軽く汗をかく運動を週２日以上、１年以上実施している
	// this.PrintedCD.add("9N746000000000011");
	// //11 日常生活において歩行又は同等の身体活動を１日１時間以上実施している
	// this.PrintedCD.add("9N751000000000011");
	// //12 同世代の同姓と比較して歩く速度が速い
	// this.PrintedCD.add("9N756000000000011");
	// //13 この１年間で体重の増減が±３kg以上ある
	// this.PrintedCD.add("9N761000000000011");
	// //14 早食い・ドカ食い・ながら食いが多い
	// this.PrintedCD.add("9N766000000000011");
	// //15 就寝前の２時間以内に夕食をとることが週に３回以上ある
	// this.PrintedCD.add("9N771000000000011");
	// //16 夜食や間食が多い
	// this.PrintedCD.add("9N776000000000011");
	// //17 朝食を抜くことが多い
	// this.PrintedCD.add("9N781000000000011");
	//	//18 ほぼ毎日アルコール飲料を飲む
	//	this.PrintedCD.add("9N786000000000011");
	//	//19 飲酒日の1日あたりの飲酒量
	//	this.PrintedCD.add("9N791000000000011");
	//	//20 睡眠で休養が得られている
	//	this.PrintedCD.add("9N796000000000011");
	//	//21 運動や生活活動の生活習慣を改善してみようと思いますか
	//	this.PrintedCD.add("9N801000000000011");
	//	//21 生活習慣病の改善について保健指導を受ける機会があれば、利用しますか
	//	this.PrintedCD.add("9N806000000000011");
	//}

}