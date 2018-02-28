//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
//
//import java.awt.print.PrinterJob;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
//import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.AcroFields;
//import com.lowagie.text.pdf.PdfCopyFields;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfStamper;
//
//public class PrintRyosyusyo extends JTKenshinPrint {
//
////	private static final String OUT_RYOSYUSYO_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_ryosyusyo.pdf";
////	private static final String TEMPLATE_RYOSYUSYO_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"ryosyusyo.pdf";
//	private Hashtable<String, String> kikanData = new Hashtable<String, String>();
////	private ArrayList<String> saiketubiList = new ArrayList<String>();
////	private int pageBunshi = 1;
////	//PDFを採血日別で、項目分印字する
//	private List fileList = new ArrayList();
////	/* 検査依頼書の 1 ページあたりの最大件数 */
////	private final int MAX_KENSU_PER_PAGE = 20;
//
//	PrinterJob job = PrinterJob.getPrinterJob();
//	File printFile = null;
//
//	public File printKekka() {
//
//		File file = null;
//
//		File outfile = new File(".\\Data\\PDF");
//		outfile.mkdirs();
//
//		//機関情報出力
//		Kikan tmpKikan = (Kikan) printData.get("Kikan");
//		kikanData = tmpKikan.getPrintKikanData();
//
//// del s.inoue 2012/01/27
////		Iterator ite = keyList.iterator();
////		while(ite.hasNext()){
////			saiketubiList.add((String)ite.next());
////		}
////
////		/* 検査年月日がキー、その日の依頼書データのリストを値に持つ Map */
////		HashMap<String, List> nengapiIraishoMap = new HashMap<String, List>();
////
////		/* 依頼書データのリスト */
////		List<Iraisho> iraishoList = null;
////		/* 処理中の検査年月日 */
////		String currentNengapi = null;
////
////		/* 総ページ数 */
////		int allPageCount = 0;
////		/* 1 ページごとの項目のインデックス */
////		int indexPerPage = 1;
////		for(int i = 0; i<saiketubiList.size();i++){
////			String saiketubiAndUketsukeId = saiketubiList.get(i);
////			Iraisho iraisho = (Iraisho)printIraiData.get(saiketubiAndUketsukeId);
////			Hashtable<String, String> iraiData = iraisho.getPrintIraiData();
////
////			// 採血日
////			String nengapi = iraiData.get("KENSA_NENGAPI");
////			if (currentNengapi == null) {
////				currentNengapi = nengapi;
////
////				/*
////				 * 最初は一度新しい
////				 * 新しい List を 生成する。
////				 */
////				iraishoList = new ArrayList<Iraisho>();
////				nengapiIraishoMap.put(nengapi, iraishoList);
////			}
////
////			/*
////			 * 現在の日付が、これまでの日付と異なる場合は、
////			 * 新しい List を 生成する。
////			 */
////			if (! currentNengapi.equals(nengapi)) {
////				iraishoList = new ArrayList<Iraisho>();
////				nengapiIraishoMap.put(nengapi, iraishoList);
////
////				/* 総ページ数をインクリメントする。 */
////				++allPageCount;
////				indexPerPage = 0;
////
////				currentNengapi = nengapi;
////			}
////
////			indexPerPage++;
////			iraishoList.add(iraisho);
////
////			/* 1 ページごとの件数が、最大値を超えた場合は、総ページ数を
////			 * インクリメントする。 */
////			if (indexPerPage > MAX_KENSU_PER_PAGE - 1) {
////				indexPerPage = 0;
////				++allPageCount;
////			}
////		}
////
////		Set<String> nengapiSet = nengapiIraishoMap.keySet();
////		int cnt = 0;
////		for (String nengapi : nengapiSet) {
////			createPdfDeffDay(nengapiIraishoMap.get(nengapi),cnt,allPageCount);
////			cnt++;
////			pageBunshi++;
////		}
//
//		PdfReader reader;
//		PdfStamper stamp = null;
//
//		//フォームセット
//		// 健診実施機関名
//		try {
//			reader = new PdfReader(PrintDefine.WORK_RYOSYU_PDF);
//			stamp = new PdfStamper(reader, new FileOutputStream(PrintDefine.OUT_RYOSYUSYO_PDF+Integer.toString(0)));
//			AcroFields form = stamp.getAcroFields();
//
////			form.setField("K_J_KIKAN_NAME",kikanData.get("KIKAN_NAME"));
////			form.setField("SHIMEI_1",printRyosyusyoData.get("SHIMEI_1"));
//			// 発行日
//			form.setField("HAKOUBI_1", printRyosyusyoData.get("HAKOUBI_1"));
//			// 氏名 _2:控え
//			form.setField("SHIMEI_1", printRyosyusyoData.get("SHIMEI_1"));
//			form.setField("SHIMEI_2", printRyosyusyoData.get("SHIMEI_2"));
//			// 健診実施日
//			form.setField("JISHIBI_1",printRyosyusyoData.get("JISHIBI_1") );
//			form.setField("JISHIBI_2",printRyosyusyoData.get("JISHIBI_2") );
//			// 受診券整理番号
//			form.setField("SEIRINO_1",printRyosyusyoData.get("SEIRINO_1") );
//			form.setField("SEIRINO_2",printRyosyusyoData.get("SEIRINO_2") );
//			// 単価合計
//			form.setField("TANKA_SUM_1",printRyosyusyoData.get("TANKA_SUM_1") );
//			form.setField("TANKA_SUM_2",printRyosyusyoData.get("TANKA_SUM_2") );
//			// 窓口合計
//			form.setField("MADOGUTI_SUM_1",printRyosyusyoData.get("MADOGUTI_SUM_1") );
//			form.setField("MADOGUTI_SUM_2",printRyosyusyoData.get("MADOGUTI_SUM_2") );
//			// 請求合計
//			form.setField("SEIKYU_SUM_1",printRyosyusyoData.get("SEIKYU_SUM_1") );
//			form.setField("SEIKYU_SUM_2",printRyosyusyoData.get("SEIKYU_SUM_2") );
//			// その他合計
//			form.setField("SONOTA_SUM_1",printRyosyusyoData.get("SONOTA_SUM_1") );
//			form.setField("SONOTA_SUM_2",printRyosyusyoData.get("SONOTA_SUM_2") );
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				stamp.setFormFlattening(true);
//				stamp.close();
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		printFile = new File(PrintDefine.OUT_RYOSYUSYO_PDF);
//		stamp.setFormFlattening(true);
//		fileList.add(PrintDefine.OUT_RYOSYUSYO_PDF +Integer.toString(0));
//
//		//PDF結合
//		unitPdf(fileList);
//
//		return printFile;
//	}
//
//
////	/**
////	 * PDF作成メソッド(日付が違うとき）
////	 */
////	private void createPdfDeffDay(Object iraisyo, int cnt,int allPageCount) {
////		PdfReader reader;
////		PdfStamper stamp = null;
////		List iraisyoList =  (List)iraisyo;
////		int koumokuSuu =  iraisyoList.size();
////		int pageSuu = koumokuSuu/20;
////		int pageAmari = koumokuSuu%20;
////
////		try {
////
////			if(pageSuu>0){
////				for(int i = 0;i<pageSuu;i++){
////					reader = new PdfReader(PrintRyosyusyo.TEMPLATE_RYOSYUSYO_PDF);
////					stamp = new PdfStamper(reader, new FileOutputStream(
////							PrintRyosyusyo.OUT_RYOSYUSYO_PDF + Integer.toString(pageBunshi)));
////					AcroFields form = stamp.getAcroFields();
////					//フォームセット
////					setFormDiffDay(iraisyoList,form, pageSuu, pageAmari,allPageCount,koumokuSuu);
////
////					stamp.setFormFlattening(true);
////					stamp.close();
////					fileList.add(PrintRyosyusyo.OUT_RYOSYUSYO_PDF + Integer.toString(pageBunshi));
////					pageBunshi++;
////				}
////				if(pageAmari>0){
////					reader = new PdfReader(PrintRyosyusyo.TEMPLATE_RYOSYUSYO_PDF);
////					stamp = new PdfStamper(reader, new FileOutputStream(
////							PrintRyosyusyo.OUT_RYOSYUSYO_PDF + Integer.toString(pageBunshi)));
////					AcroFields form = stamp.getAcroFields();
////					//フォームセット
////					setFormDiffDay(iraisyoList,form, -1, pageAmari,allPageCount,koumokuSuu);
////
////					stamp.setFormFlattening(true);
////					stamp.close();
////					fileList.add(PrintRyosyusyo.OUT_RYOSYUSYO_PDF + Integer.toString(pageBunshi));
////					pageBunshi++;
////				}
////
////			}else{
////				reader = new PdfReader(PrintRyosyusyo.TEMPLATE_RYOSYUSYO_PDF);
////				stamp = new PdfStamper(reader, new FileOutputStream(
////						PrintRyosyusyo.OUT_RYOSYUSYO_PDF + Integer.toString(pageBunshi)));
////				AcroFields form = stamp.getAcroFields();
////				//フォームセット
////				setFormDiffDay(iraisyoList,form, pageSuu, pageAmari,allPageCount,-1);
////
////				stamp.setFormFlattening(true);
////				stamp.close();
////				fileList.add(PrintRyosyusyo.OUT_RYOSYUSYO_PDF + Integer.toString(pageBunshi));
////			}
////
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally{
////			try {
////				stamp.close();
////			} catch (DocumentException e) {
////				//スローする必要なし
////			} catch (IOException e) {
////				//スローする必要なし
////			}
////		}
//////		return filePathList;
////	}
//
////	/**
////	 * FormTextに値をセット
////	 *
////	 * @param iraisyoList
////	 * @param form
////	 * @param pageSuu
////	 * @param amari 項目の余り
////	 * @param allPageCount 総ページ数
////	 * @param koumokuSuu 同一日で複数ページある場合の項目数
////	 * @throws IOException
////	 * @throws DocumentException
////	 */
////	private void setFormDiffDay(List iraisyoList, AcroFields form,
////			int pageSuu, int amari, int allPageCount,int koumokuSuu)
////	throws IOException, DocumentException {
////
////		Iraisho iraisho;
////		/*
////		 * 項目印字ループの最初の位置
////		 */
////		int roopi = koumokuSuu;
////
////		/*
////		 * その日の総ページ数が 1 ページの場合、項目数は 0 または amari、
////		 * その日の総ページ数が 1 ページ以上の場合、項目数は 20*ページ数 20項目無い場合は総項目数、
////		 */
////		int roopSize = 0;
////		if(pageSuu>0){
////			roopSize = 20*pageSuu;
////			roopi = 0;
////		}else if(amari>0&&pageSuu<0) {
////			roopSize = amari;
////			roopi = roopi -1 ;
////		}else if(amari>0) {
////			roopSize = amari;
////			koumokuSuu = 0;
////			roopi = 0;
////		}
////
////
////		// 検査機関名
//////		form.setField("K_KIKAN_NAME",iraiData.get(""));
////		// 健診実施機関名
////		form.setField("K_J_KIKAN_NAME",kikanData.get("KIKAN_NAME"));
////
////		// 採血日
////		iraisho = (Iraisho)iraisyoList.get(0);
////		Hashtable<String, String> irai = iraisho.getPrintIraiData();
////		form.setField("SAIKETUBI",irai.get("KENSA_NENGAPI"));
////
////		//iraiから取得する位置用
////		int roop = roopi;
////		/*
////		 *  項目数分ループ
////		 */
////		for(int i = 0; i < roopSize ;i++){
////
////			iraisho = (Iraisho)iraisyoList.get(roop);
////			Hashtable<String, String> iraiData = iraisho.getPrintIraiData();
////
////			/*
////			 * printIraiDataから依頼情報を抽出
////			 */
////			// 受診券整理番号
////			form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));
////
////			// カナ氏名
////			form.setField("KANA_MEI_"+Integer.toString(i+1),JZenkakuKatakanaToHankakuKatakana.Convert(iraiData.get("KANANAME")));
////			// 生年月日
////			form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
////			// 性別
////			form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
////			// 備考
////			form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));
////
////			roop++;
////
////		}
////
////		//ページ
////		form.setField("PAGE", Integer.toString(pageBunshi)+"／"+Integer.toString(allPageCount+1));
////
////	}
////	/**
////	* PDF作成メソッド(日付が違わないとき、予備）
////	*/
////	private List createPdf(int max, int amari, int pdfCnt,int jyushinsuu ) {
////	ArrayList<String> filePathList = new ArrayList<String>();
////	PdfReader reader;
////	PdfStamper stamp = null;
////	try {
//
////	for(int i = 0;i<pdfCnt;i++){
////	reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
////	stamp = new PdfStamper(reader, new FileOutputStream(
////	PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i)));
////	AcroFields form = stamp.getAcroFields();
////	//フォームセット
////	setForm(form, max, amari,jyushinsuu);
//
////	stamp.setFormFlattening(true);
////	stamp.close();
////	filePathList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i));
////	}
//
////	} catch (Exception e) {
////	e.printStackTrace();
////	} finally{
////	try {
////	stamp.close();
////	} catch (DocumentException e) {
////	//スローする必要なし
////	} catch (IOException e) {
////	//スローする必要なし
////	}
////	}
////	return filePathList;
////	}
//
//
////	/**
////	* @param form
////	* @throws IOException
////	* @throws DocumentException
////	*/
////	private void setForm(AcroFields form,int max, int amari, int jyushinsuu)
////	throws IOException, DocumentException {
//
////	Iraisho iraisho;
////	int size = 0;
////	if(max>0){
////	size = 20;
////	}else if(amari>0) {
////	size = amari;
////	}else if(jyushinsuu>0){
////	size = jyushinsuu;
////	}
//
////	for(int i = 0; i<size;i++){
////	iraisho = (Iraisho)printIraiData.get(keyList.get(i));
////	Hashtable<String, String> iraiData = iraisho.getPrintIraiData();
//
////	/*
////	* printIraiDataから依頼情報を抽出
////	*/
////	// 採血日
////	form.setField("SAIKETUBI"+Integer.toString(i+1),iraiData.get("KENSA_NENGAPI"));
//
////	// 受診券整理番号
////	form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));
////	// カナ氏名
////	form.setField("KANA_MEI_"+Integer.toString(i+1),iraiData.get("KANANAME"));
////	// 生年月日
////	form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
////	// 性別
////	form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
////	// 備考
////	form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));
//
////	}
////	}
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
////				new File((String)filePathList.get(i)).deleteOnExit();
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
//
//
//}
