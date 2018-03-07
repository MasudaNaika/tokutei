package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Gekeihyo;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

// 請求画面一覧（月次）印刷
public class PrintSeikyuGetuji extends JTKenshinPrint {

	private Hashtable<String, String> kikanData = new Hashtable<String, String>();
	private static org.apache.log4j.Logger logger = Logger.getLogger(PrintSeikyuGetuji.class);

	// 月計表データ
	PrinterJob job = PrinterJob.getPrinterJob();

	File printFile = null;

	private ArrayList<String> saiketubiList = new ArrayList<String>();
	private int pageBunshi = 1;

	private List<String> fileList = new ArrayList<String>();

	/* 日計表の 1 ページあたりの最大件数 */
	// edit h.yoshitama 2009/09/17
//	private final int MAX_KENSU_PER_PAGE = 20;
	private final int MAX_KENSU_PER_PAGE = 25;

	/**
	 * 月計表の項目を作成する
	 */
	public File printKekka() {

		File outfile = new File(".\\Data\\PDF");
		outfile.mkdirs();

		//機関情報出力
		Kikan tmpKikan = (Kikan) printData.get("Kikan");
		kikanData = tmpKikan.getPrintKikanData();

		Iterator<String> ite = keyList.iterator();
		while(ite.hasNext()){
			saiketubiList.add((String)ite.next());
		}

		/* 月次データのリスト */
		List<Gekeihyo> gekeiList = null;

		/* 総ページ数 */
		int allPageCount = 0;

		/* 1 ページごとの項目のインデックス */
		int indexPerPage = 1;

		gekeiList = new ArrayList<Gekeihyo>();

		for(int i = 0; i<saiketubiList.size();i++){

			String saiketubiAndUketsukeId = saiketubiList.get(i);
			if(saiketubiAndUketsukeId != null){
				Gekeihyo gekeihyo = (Gekeihyo)printSeikyuGetujiData.get(saiketubiAndUketsukeId);

				gekeiList.add(gekeihyo);

				/* 1 ページごとの件数が、最大値を超えた場合は、
				 * 総ページ数をインクリメントする。 */
				if (indexPerPage > MAX_KENSU_PER_PAGE - 1) {
					indexPerPage = 0;
					++allPageCount;
				}
				// edit s.inoue 2009/10/31
				indexPerPage++;
			}
		}

		// ページ毎 月計表
		createPdfDeffDay(gekeiList,allPageCount);

		// 集計表
		PrintShukeiList shukeiListPDF = new PrintShukeiList();
		String shukeiPdf = shukeiListPDF.createShukeiList(kikanData,ShukeiKey);
		fileList.add(shukeiPdf);
		printFile = new File(PrintDefine.OUT_GEKEI_PDF);

		//PDF結合
		unitPdf(fileList);

		return printFile;
	}

	/**
	 * PDF作成メソッド(日付が違うとき）
	 */
	private void createPdfDeffDay(List<Gekeihyo> gekeiList,int allPageCount) {
		PdfReader reader;
		PdfStamper stamp = null;

		// edit s.inoue 2009/11/02
		int koumokuSuu =  gekeiList.size();
		int maxPageSuu = koumokuSuu/25;
		int pageAmari = koumokuSuu%25;

		// edit s.inoue 2009/10/31
		if(pageAmari>0)
			allPageCount++;

		try {

			if(maxPageSuu>0){
				for(int i = 0;i<maxPageSuu;i++){
					reader = new PdfReader(PrintDefine.WORK_GEKEI_PDF);
					stamp = new PdfStamper(reader, new FileOutputStream(
							PrintDefine.OUT_GEKEI_PDF + Integer.toString(pageBunshi)));
					AcroFields form = stamp.getAcroFields();
					//フォームセット
					setFormDiffDay(gekeiList,form, pageBunshi, pageAmari,allPageCount,koumokuSuu);

					stamp.setFormFlattening(true);
					stamp.close();
					fileList.add(PrintDefine.OUT_GEKEI_PDF + Integer.toString(pageBunshi));
					pageBunshi++;
				}
				if(pageAmari>0){
					reader = new PdfReader(PrintDefine.WORK_GEKEI_PDF);
					stamp = new PdfStamper(reader, new FileOutputStream(
							PrintDefine.OUT_GEKEI_PDF + Integer.toString(pageBunshi)));
					AcroFields form = stamp.getAcroFields();
					//フォームセット
					setFormDiffDay(gekeiList,form, -1, pageAmari,allPageCount,koumokuSuu);

					stamp.setFormFlattening(true);
					stamp.close();
					fileList.add(PrintDefine.OUT_GEKEI_PDF + Integer.toString(pageBunshi));
					pageBunshi++;
				}

			}else{
				reader = new PdfReader(PrintDefine.WORK_GEKEI_PDF);
				stamp = new PdfStamper(reader, new FileOutputStream(
						PrintDefine.OUT_GEKEI_PDF + Integer.toString(pageBunshi)));
				AcroFields form = stamp.getAcroFields();
				//フォームセット
				// edit s.inoue 2009/10/30
				setFormDiffDay(gekeiList,form, maxPageSuu, pageAmari,allPageCount,-1);
				// setFormDiffDay(gekeiList,form, pageSuu, pageAmari,allPageCount,-1);

				stamp.setFormFlattening(true);
				stamp.close();
				fileList.add(PrintDefine.OUT_GEKEI_PDF + Integer.toString(pageBunshi));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally{
			try {
				stamp.close();
			} catch (DocumentException e) {
				//スローする必要なし
			} catch (IOException e) {
				//スローする必要なし
			}
		}
	}

	/**
	 * PDF作成メソッド(日付が違うとき）
	 */
//	private void createSyukeiPdfDeffDay(int allPageCount) {
//		// add s.inoue 2009/09/21
//		// 集計表を追加
//		// gekeiList 保険者毎にループの予定ですが。。
//		PrintShukeiList shukeiListPDF = new PrintShukeiList();
//		String shukeiPdf = shukeiListPDF.createShukeiList(kikanData);
//		fileList.add(shukeiPdf);
//		printFile = new File(PrintDefine.OUT_GEKEI_PDF);
//	}

	/**
	 * FormTextに値をセット
	 *
	 * @param iraisyoList
	 * @param form
	 * @param pageSuu
	 * @param amari 項目の余り
	 * @param allPageCount 総ページ数
	 * @param koumokuSuu 同一日で複数ページある場合の項目数
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void setFormDiffDay(List gekeiList, AcroFields form,
			int pageSuu, int amari, int allPageCount,int koumokuSuu)
	throws IOException, DocumentException {

		Gekeihyo gekei;

		/*
		 * 項目印字ループの最初の位置
		 */
		int roopi = koumokuSuu;

		/*
		 * その日の総ページ数が 1 ページの場合、項目数は 0 または amari、
		 * その日の総ページ数が 1 ページ以上の場合、項目数は 25*ページ数 25項目無い場合は総項目数、
		 */
		int roopSize = 0;
		int roopStart = 0;
		if(pageSuu>0){
			roopStart = 25*(pageSuu - 1);
			roopSize = 25;
			roopi = roopStart;
		}else if(amari>0&&pageSuu<0) {
			roopSize = amari;
			roopi = roopi -amari;
		}else if(amari>0) {
			roopSize = amari;
			koumokuSuu = 0;
			roopi = 0;
		}

		// 健診実施機関名
		form.setField("KENSHIN",kikanData.get("KIKAN_NAME"));

		int roop = roopi;
		long tankaSyokei = 0;
		long madogutiSyokei = 0;
		long seikyuSyokei = 0;

		// add s.inoue 2009/09/16
		NumberFormat kingakuFormat = NumberFormat.getNumberInstance();

		/*
		 *  項目数分ループ
		 */
		for(int i = 0; i < roopSize ;i++){

			gekei = (Gekeihyo)gekeiList.get(roop);
			Hashtable<String, String> gekeiData = gekei.getPrintGekeiData();

			// NO
			form.setField("NO_" +Integer.toString(i+1),Integer.toString(i + 1));
			// 受診券整理番号
			form.setField("JYUSHIN_SEIRI_NO_" +Integer.toString(i+1),gekeiData.get("JYUSHIN_SEIRI_NO"));
			// 氏名（カナ）
			form.setField("KANANAME_" +Integer.toString(i+1),gekeiData.get("KANANAME"));
			// 生年月日
			form.setField("BIRTHDAY_" +Integer.toString(i+1),gekeiData.get("BIRTHDAY"));
			// 性別
			form.setField("SEX_" +Integer.toString(i+1),gekeiData.get("SEX"));
			// 健診年月日
			form.setField("KENSA_NENGAPI_" +Integer.toString(i+1),gekeiData.get("KENSA_NENGAPI"));

			// HL7出力日
			form.setField("HL7_" +Integer.toString(i+1),gekeiData.get("HENKAN_NITIJI"));

			// 保険者番号
			form.setField("HKNJANUM_" +Integer.toString(i+1),gekeiData.get("HKNJANUM"));
			// add s.inoue 2009/10/20
			// 支払代行機関番号
			form.setField("DAIKOUNUM_" +Integer.toString(i+1),gekeiData.get("SIHARAI_DAIKOU_BANGO"));

			// 単価合計
			// edit s.inoue 2009/09/16
			// form.setField("TANKA_GOUKEI_" +Integer.toString(i+1),nikeiData.get("TANKA_GOUKEI").toString());
			long tankaGoukei = Long.parseLong(gekeiData.get("TANKA_GOUKEI"));
			form.setField("TANKA_GOUKEI_" +Integer.toString(i+1),kingakuFormat.format(tankaGoukei));

			// 窓口負担合計
			// edit s.inoue 2009/09/16
			// form.setField("MADOGUTI_GOUKEI_" +Integer.toString(i+1),nikeiData.get("MADO_FUTAN_GOUKEI"));
			long madogutiGoukei = Long.parseLong(gekeiData.get("MADO_FUTAN_GOUKEI"));
			form.setField("MADOGUTI_GOUKEI_" +Integer.toString(i+1),kingakuFormat.format(madogutiGoukei));

			// 請求金額合計
			// form.setField("SEIKYU_GOUKEI_" +Integer.toString(i+1),nikeiData.get("SEIKYU_KINGAKU"));
			long seikyuGoukei = Long.parseLong(gekeiData.get("SEIKYU_KINGAKU"));
			form.setField("SEIKYU_GOUKEI_" +Integer.toString(i+1),kingakuFormat.format(seikyuGoukei));

			// edit s.inoue 2009/09/16
			tankaSyokei += Long.parseLong(gekeiData.get("TANKA_GOUKEI"));
			madogutiSyokei += Long.parseLong(gekeiData.get("MADO_FUTAN_GOUKEI"));
			seikyuSyokei += Long.parseLong(gekeiData.get("SEIKYU_KINGAKU"));

			roop++;

		}

		// edit s.inoue 2009/09/16
		// edit h.yoshitama 2009/10/05
		// 単価合計
		form.setField("TANKA_GOUKEI", String.valueOf(kingakuFormat.format(tankaSyokei)));
		// 窓口負担合計
		form.setField("MADO_FUTAN_GOUKEI", String.valueOf(kingakuFormat.format(madogutiSyokei)));
		// 請求金額合計
		form.setField("SEIKYU_KINGAKU", String.valueOf(kingakuFormat.format(seikyuSyokei)));

		//ページ
		form.setField("PAGE_KO", Integer.toString(pageBunshi));
		// edit s.inoue 2009/10/30
		form.setField("PAGE_OYA", Integer.toString(allPageCount));
		// form.setField("PAGE_OYA", Integer.toString(allPageCount+1));
	}

	/**
	 * 引数によりＰＤＦを結合するか判断して結合する。
	 */
	private void unitPdf(List filePathList) {
		int fileAddCnt = filePathList.size();

		try {
			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(printFile));
			for(int i = 0;i<fileAddCnt;i++){
				PdfReader reader = new PdfReader((String)filePathList.get(i));
				copy.addDocument(reader);
				new File((String)filePathList.get(i)).deleteOnExit();
				new File((String)filePathList.get(i)).deleteOnExit();
				reader.close();
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

//	/**
//	 * 引数によりＰＤＦを結合するか判断して結合する。
//	 */
//	private void unitPdf(List filePathList) {
//		PdfReader addpage1 ;
//		PdfReader addpage2 ;
//
//		int fileAddCnt = filePathList.size();
//
//		try {
//			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(printFile));
//			addpage1 = new PdfReader(PrintDefine.OUT_GEKEI_PDF+ Integer.toString(pageBunshi));
//			copy.addDocument(addpage1);
////			addpage2 = new PdfReader(PrintDefine.OUT_SHUKEI_PDF);
////			copy.addDocument(addpage2);
//
//			for(int i = 0;i<fileAddCnt;i++){
//				PdfReader reader = new PdfReader((String)filePathList.get(i));
//				copy.addDocument(reader);
//				new File((String)filePathList.get(i)).deleteOnExit();
//				new File((String)filePathList.get(i)).deleteOnExit();
//				reader.close();
//			}
//			copy.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

// del s.inoue 2009/09/29
//	/**
//	 * 引数によりＰＤＦを結合するか判断して結合する。
//	 */
//	private void unitPdf(List filePathList) {
//		int fileAddCnt = filePathList.size();
//
//		try {
//			PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(printFile));
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

}
