package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Iraisho;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kikan;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PrintIraisho extends JTKenshinPrint {

	/* Modified 2008/09/16 薮  */
	/* --------------------------------------------------- */
//	private static final String OUT_IRAISYO_PDF = ".\\Data\\PDF\\out_iraisyo.pdf";
	private static final String OUT_IRAISYO_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_iraisyo.pdf";	
	/* Modified 2008/08/07 若月  */
	/* --------------------------------------------------- */
	/* Modified 2008/09/16 薮  */
//	private static final String TEMPLATE_IRAISYO_PDF = ".\\Work\\PDF\\iraisyo.pdf";
	/* --------------------------------------------------- */
	private static final String TEMPLATE_IRAISYO_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"iraisyo.pdf";
	/* --------------------------------------------------- */
	private Hashtable<String, String> kikanData = new Hashtable<String, String>();

	
	// 依頼書データ
	//private Iraisho iraisho = new Iraisho();

	PrinterJob job = PrinterJob.getPrinterJob();

	File printFile = null;

	
	/* MOD 2008/07/30 yabu */
//	private TreeSet<String> saiketubiList = new TreeSet<String>(); 
	private ArrayList<String> saiketubiList = new ArrayList<String>(); 
	
	private int pageBunshi = 1; 

	/* ADD 2008/07/30 yabu */
	//PDFを採血日別で、項目分印字する
	private List fileList = new ArrayList();

	
	/* 検査依頼書の 1 ページあたりの最大件数 */
	private final int MAX_KENSU_PER_PAGE = 20;
//	private final int MAX_KENSU_PER_PAGE = 10;

	/**
	 * 依頼票の項目を作成する
	 */
	public File printKekka() {

		File file = null;

		File outfile = new File(".\\Data\\PDF");
		outfile.mkdirs();

		//機関情報出力
		Kikan tmpKikan = (Kikan) printData.get("Kikan");
		kikanData = tmpKikan.getPrintKikanData();

		/* MOD 2008/07/30 yabu */
		//採血日が変わる数をカウント
//		for(int i=0; i<keyList.size();i++){
//			saiketubiList.add(keyList.get(i).substring(0,8));
//		}

		Iterator ite = keyList.iterator();
		while(ite.hasNext()){
			saiketubiList.add((String)ite.next());
			
		}

//		int saiketuCnt =0;
//		for(Iterator  ite = saiketubiList.iterator() ; ite.hasNext();){
//		String saiketuBi = (String)ite.next();
//		for( saiketuCnt=saiketuCnt; saiketuCnt<keyList.size();saiketuCnt++){
//		if(keyList.get(saiketuCnt).substring(0,8).contains(saiketuBi)){
//		iraiDateCounter.put(saiketuBi, saiketuCnt);
//		}
//		//saiketubiList.add(keyList.get(i).substring(0,8));
//		}

//		}

//		for(int i = 0; i<keyList.size();i++){
//		iraisho = (Iraisho)printIraiData.get(keyList.get(i));
//		Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

//		// 採血日
//		iraiData.get("KENSA_NENGAPI");

////		// 受診券整理番号
////		form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));
////		// カナ氏名
////		form.setField("KANA_MEI_"+Integer.toString(i+1),iraiData.get("KANANAME"));
////		// 生年月日
////		form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
////		// 性別
////		form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
////		// 備考
////		form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));

//		}

		/* 検査年月日がキー、その日の依頼書データのリストを値に持つ Map */
		HashMap<String, List> nengapiIraishoMap = new HashMap<String, List>();

		/* 依頼書データのリスト */
		List<Iraisho> iraishoList = null;
		/* 処理中の検査年月日 */
		String currentNengapi = null;

		/* 総ページ数 */
		int allPageCount = 0;
		/* 1 ページごとの項目のインデックス */
//		int indexPerPage = 0;
		int indexPerPage = 1;

//		iraishoList = new ArrayList<Iraisho>();

		/* MOD 2008/07/30 yabu */
//		for(int i = 0; i<keyList.size();i++){
		for(int i = 0; i<saiketubiList.size();i++){
			String saiketubiAndUketsukeId = saiketubiList.get(i);
//			String saiketubiAndUketsukeId = keyList.get(i);
			Iraisho iraisho = (Iraisho)printIraiData.get(saiketubiAndUketsukeId);
			Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

			// 採血日
			String nengapi = iraiData.get("KENSA_NENGAPI");
			if (currentNengapi == null) {
				currentNengapi = nengapi;

				/* 
				 * 最初は一度新しい
				 * 新しい List を 生成する。
				 */
//				iraishoList.add(iraisho);
//				nengapiIraishoMap.put(nengapi, iraishoList);

				iraishoList = new ArrayList<Iraisho>();
				nengapiIraishoMap.put(nengapi, iraishoList);
			}

			/* 
			 * 現在の日付が、これまでの日付と異なる場合は、
			 * 新しい List を 生成する。
			 */
			if (! currentNengapi.equals(nengapi)) {
				iraishoList = new ArrayList<Iraisho>();
				nengapiIraishoMap.put(nengapi, iraishoList);

				/* 総ページ数をインクリメントする。 */
				++allPageCount;
				indexPerPage = 0;

				/* ADD 2008/07/30 yabu */
				currentNengapi = nengapi;
			}
			/* ADD 2008/07/30 yabu */
			indexPerPage++;

			iraishoList.add(iraisho);

			/* 1 ページごとの件数が、最大値を超えた場合は、総ページ数を
			 * インクリメントする。 */
			if (indexPerPage > MAX_KENSU_PER_PAGE - 1) {
				indexPerPage = 0;
				++allPageCount;
			}
		}

		/* MOD 2008/07/30 yabu */
		//PDFを採血日別で、項目分印字する
		//List fileList = new ArrayList();

		//採血日がひと種類しかない場合
//		if(allPageCount==0){
//		int pdfCnt = 0;
//		if(keyList.size()>0){
//		if(keyList.size()/20>0){
//		max =keyList.size()/20;
//		amari =keyList.size()%20;
//		pdfCnt++;
//		fileList=createPdf(max,amari,pdfCnt,jyushinsuu);
//		}else{
//		max =keyList.size()/20;
//		amari =keyList.size()%20;
//		pdfCnt++;
//		fileList=createPdf(max,amari,pdfCnt,jyushinsuu);
//		}
//		}else{
//		//	fileList=createPdf(max,amari,pdfCnt,jyushinsuu);
//		}
//		}else{
////		for (int i = 0; i < allPageCount; i++) {
////		for (int i = 0; i < nengapiIraishoMap.size(); i++) {
//		int i = 0;
//		for (Iterator<String> ite = nengapiSet.iterator();ite.hasNext();){
////		fileList=createPdfDeffDay(ite.next());

//		String nengapi = ite.next();

//		fileList=createPdfDeffDay(nengapiIraishoMap.get(nengapi),i);
//		i++;
//		}

		Set<String> nengapiSet = nengapiIraishoMap.keySet();
//		Iterator<String> ite = nengapiSet.iterator();
		int i = 0;
//		while (ite.hasNext()) {
//		String nengapi = ite.next();

//		fileList=createPdfDeffDay(nengapiIraishoMap.get(nengapi),i);
//		i++;				
//		}

//		for (String nengapi : nengapiSet) {
//		fileList.add(createPdfDeffDay(nengapiIraishoMap.get(nengapi),i));
//		i++;				
//		}
//		}
		int cnt = 0;
		for (String nengapi : nengapiSet) {
			/* MOD 2008/07/30 yabu */
//			fileList.add(createPdfDeffDay(nengapiIraishoMap.get(nengapi),cnt,allPageCount));
			createPdfDeffDay(nengapiIraishoMap.get(nengapi),cnt,allPageCount);
			cnt++;				
			pageBunshi++;
		}

		printFile = new File(OUT_IRAISYO_PDF);

		//PDF結合
		unitPdf(fileList);

		return printFile;

	}


	/**
	 * PDF作成メソッド(日付が違うとき）
	 */
	private void createPdfDeffDay(Object iraisyo, int cnt,int allPageCount) {
//		ArrayList<String> filePathList = new ArrayList<String>();
//		String filePath = "";
		PdfReader reader;
		PdfStamper stamp = null;
		List iraisyoList =  (List)iraisyo;
		int koumokuSuu =  iraisyoList.size();
		int pageSuu = koumokuSuu/20;
		int pageAmari = koumokuSuu%20;

		try {

			if(pageSuu>0){
				/* MOD 2008/07/30 yabu */
				for(int i = 0;i<pageSuu;i++){
//				for(int i = 0;i<=pageSuu;i++){
					reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
					/* MOD 2008/07/30 yabu */
//					stamp = new PdfStamper(reader, new FileOutputStream(
//							PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i)));
					stamp = new PdfStamper(reader, new FileOutputStream(
							PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi)));
					AcroFields form = stamp.getAcroFields();
					//フォームセット
					setFormDiffDay(iraisyoList,form, pageSuu, pageAmari,allPageCount,koumokuSuu);

					stamp.setFormFlattening(true);
					stamp.close();
					/* MOD 2008/07/30 yabu */
//					filePath=PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i);
					fileList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi));
					pageBunshi++;
				}
				/* MOD 2008/07/30 yabu */
				if(pageAmari>0){
					reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
					stamp = new PdfStamper(reader, new FileOutputStream(
							PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi)));
					AcroFields form = stamp.getAcroFields();
					//フォームセット
					setFormDiffDay(iraisyoList,form, -1, pageAmari,allPageCount,koumokuSuu);

					stamp.setFormFlattening(true);
					stamp.close();
					fileList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi));
					pageBunshi++;
				}
				
			}else{
				reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
				/* MOD 2008/07/30 yabu */
//				stamp = new PdfStamper(reader, new FileOutputStream(
//						PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(cnt)));
				stamp = new PdfStamper(reader, new FileOutputStream(
						PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi)));
				AcroFields form = stamp.getAcroFields();
				//フォームセット
				setFormDiffDay(iraisyoList,form, pageSuu, pageAmari,allPageCount,-1);

				stamp.setFormFlattening(true);
				stamp.close();
				/* MOD 2008/07/30 yabu */
//				filePath=PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(cnt);
				fileList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(pageBunshi));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				stamp.close();
			} catch (DocumentException e) {
				//スローする必要なし
			} catch (IOException e) {
				//スローする必要なし
			}
		}
//		return filePathList;
	}

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
	private void setFormDiffDay(List iraisyoList, AcroFields form,
			int pageSuu, int amari, int allPageCount,int koumokuSuu) 
	throws IOException, DocumentException {

		Iraisho iraisho;
		/*
		 * 項目印字ループの最初の位置
		 */
		int roopi = koumokuSuu;

		/*
		 * その日の総ページ数が 1 ページの場合、項目数は 0 または amari、
		 * その日の総ページ数が 1 ページ以上の場合、項目数は 20*ページ数 20項目無い場合は総項目数、
		 */
		int roopSize = 0;
		if(pageSuu>0){
			roopSize = 20*pageSuu;
			roopi = 0;
		}else if(amari>0&&pageSuu<0) {
			roopSize = amari;
			roopi = roopi -1 ;
		}else if(amari>0) {
			roopSize = amari;
			koumokuSuu = 0;
			roopi = 0;
		}

		
		// 検査機関名
//		form.setField("K_KIKAN_NAME",iraiData.get(""));
		// 健診実施機関名
		form.setField("K_J_KIKAN_NAME",kikanData.get("KIKAN_NAME"));

		// 採血日
		iraisho = (Iraisho)iraisyoList.get(0);
		Hashtable<String, String> irai = iraisho.getPrintIraiData();
		form.setField("SAIKETUBI",irai.get("KENSA_NENGAPI"));

		//iraiから取得する位置用
		int roop = roopi;
		/*
		 *  項目数分ループ
		 */
		for(int i = 0; i < roopSize ;i++){
			
			iraisho = (Iraisho)iraisyoList.get(roop);
			Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

			/*
			 * printIraiDataから依頼情報を抽出
			 */
			// 受診券整理番号
			form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));

			// カナ氏名
			form.setField("KANA_MEI_"+Integer.toString(i+1),JZenkakuKatakanaToHankakuKatakana.Convert(iraiData.get("KANANAME")));
			// 生年月日
			form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
			// 性別
			form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
			// 備考
			form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));
			
			roop++;

		}
		
		//ページ
		form.setField("PAGE", Integer.toString(pageBunshi)+"／"+Integer.toString(allPageCount+1));

	}
//	/**
//	* PDF作成メソッド(日付が違わないとき、予備）
//	*/
//	private List createPdf(int max, int amari, int pdfCnt,int jyushinsuu ) {
//	ArrayList<String> filePathList = new ArrayList<String>();
//	PdfReader reader;
//	PdfStamper stamp = null; 
//	try {

//	for(int i = 0;i<pdfCnt;i++){
//	reader = new PdfReader(PrintIraisho.TEMPLATE_IRAISYO_PDF);
//	stamp = new PdfStamper(reader, new FileOutputStream(
//	PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i)));
//	AcroFields form = stamp.getAcroFields();
//	//フォームセット
//	setForm(form, max, amari,jyushinsuu);

//	stamp.setFormFlattening(true);
//	stamp.close();
//	filePathList.add(PrintIraisho.OUT_IRAISYO_PDF + Integer.toString(i));
//	}

//	} catch (Exception e) {
//	e.printStackTrace();
//	} finally{
//	try {
//	stamp.close();
//	} catch (DocumentException e) {
//	//スローする必要なし
//	} catch (IOException e) {
//	//スローする必要なし
//	}
//	}
//	return filePathList;
//	}


//	/**
//	* @param form
//	* @throws IOException
//	* @throws DocumentException
//	*/
//	private void setForm(AcroFields form,int max, int amari, int jyushinsuu) 
//	throws IOException, DocumentException {

//	Iraisho iraisho;
//	int size = 0;
//	if(max>0){
//	size = 20;
//	}else if(amari>0) {
//	size = amari;
//	}else if(jyushinsuu>0){
//	size = jyushinsuu;
//	}

//	for(int i = 0; i<size;i++){
//	iraisho = (Iraisho)printIraiData.get(keyList.get(i));
//	Hashtable<String, String> iraiData = iraisho.getPrintIraiData();

//	/*
//	* printIraiDataから依頼情報を抽出
//	*/
//	// 採血日
//	form.setField("SAIKETUBI"+Integer.toString(i+1),iraiData.get("KENSA_NENGAPI"));

//	// 受診券整理番号
//	form.setField("J_S_K_S_N_"+Integer.toString(i+1),iraiData.get("JYUSHIN_SEIRI_NO"));
//	// カナ氏名
//	form.setField("KANA_MEI_"+Integer.toString(i+1),iraiData.get("KANANAME"));
//	// 生年月日
//	form.setField("SEINEN_GAPPI_"+Integer.toString(i+1),iraiData.get("BIRTHDAY"));
//	// 性別
//	form.setField("SEIBETU_"+Integer.toString(i+1),iraiData.get("SEX"));
//	// 備考
//	form.setField("BIKOU_"+Integer.toString(i+1),iraiData.get("BIKOU"));

//	}
//	}


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


}
