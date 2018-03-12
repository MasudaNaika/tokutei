package jp.or.med.orca.jma_tokutei.common.printer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

import org.apache.log4j.Logger;

import com.lowagie.tools.Executable;

// ----------------------------------------------------------------------------
/**
 class::name	JGraphicPrinter
 class::expl	グラフィック印刷クラス
 */
// ----------------------------------------------------------------------------
public class JGraphicPrinter implements Printable {

	private static final String WORK_PDF_TMP_DUMMY_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"dummy.pdf";
	private static final String WORK_PDF_TMP_INKEKKA2PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka2page.pdf";
	private static final String WORK_PDF_TMP_INKEKKA3PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka3page.pdf";
	private static final String WORK_PDF_TMP_INKEKKA4PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka4page.pdf";
	private static org.apache.log4j.Logger logger = Logger.getLogger(JGraphicPrinter.class);

	public final static double A4_WIDTH = 595.2755905511810;
	public final static double A4_HEIGHT = 841.8897637795276;
	public final static double A4_IX = 15.0;
	public final static double A4_IY = 15.0;
	public final static double A4_IW = A4_WIDTH - A4_IX * 2;
	public final static double A4_IH = A4_HEIGHT - A4_IY * 2;
	PrinterJob job = PrinterJob.getPrinterJob();
	PageFormat pageFormat = job.defaultPage();
	File rtn = new File("");
	JFrame ParentFrame = null;

	/*
	 *  コンストラクタ
	 */
	public JGraphicPrinter() {
	}

	/*
	 *  印刷開始
	 */
	public void beginPrint() throws PrinterException {

		/*
		 *  印刷設定
		 */
		PrinterJob job = PrinterJob.getPrinterJob();

		PageFormat pageFormat = job.defaultPage();
		Paper pFormat = pageFormat.getPaper();

		/*A4縦*/
		pFormat.setSize(A4_WIDTH, A4_HEIGHT);
		pFormat.setImageableArea(A4_IX, A4_IY, A4_IW, A4_IH);
		pageFormat.setPaper(pFormat);

			try {
				rtn = this.printKekka();

				// REedit s.inoue 2009/10/15 再度復活
				if (rtn == null || ! rtn.exists()) {
					JErrorMessage.show("M4954",ParentFrame);
					return;
				}

				Executable pdfexe = new Executable();
				Process process=null;
				if (!pdfexe.isWindows() && !pdfexe.isMac()) {

					// edit s.inoue 20090119 adobe優先
					// Process process0=  Runtime.getRuntime().exec("which acroread xpdf");
					// Process process0=  Runtime.getRuntime().exec("which xpdf acroread");

					// add s.inoue 2014/07/23
					Process process2 = Runtime.getRuntime().exec("uname -r");
					InputStream is2 = process2.getInputStream();
					InputStreamReader isr2 = new InputStreamReader(is2);
					BufferedReader br2 = new BufferedReader(isr2);
					String answer2;
					String cmd2=null;
					while ( (answer2 = br2.readLine()) !=null) {
						cmd2 = answer2;
					}
					if (cmd2 != null)  {
						answer2 = cmd2.substring(0,2);
					}
					String exeStr = "";
					if (Float.parseFloat(answer2) < 3.0){
						exeStr = "which acroread xpdf";
					}else{
						exeStr = "which evince";
					}
					Process process0=  Runtime.getRuntime().exec(exeStr);
					// edit s.inoue 2014/07/23
					// Process process0=  Runtime.getRuntime().exec("which evince");

					InputStream is = process0.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String answer;
					String cmd=null;
					while ( (answer = br.readLine()) !=null) {
						cmd = answer;
					}

					if (cmd != null)  {

						pdfexe.acroread = cmd;
						process = Runtime.getRuntime().exec(cmd + " "+ rtn.getPath());

// del s.inoue 2009/12/07
//								try{
//									String pdfName = rtn.getPath().substring(rtn.getPath().lastIndexOf('\\') +1);
//									if (pdfName.equals("out_getkei.pdf")){
//										logger.info("月計印刷 開始");
//										System.out.println("月計印刷 開始");
//										try {
//											logger.info("待機中");
//											System.out.println("待機中");
//											// process.waitFor();
//											Thread.sleep(3000); //ミリ秒単位で設定
//										} catch(InterruptedException ie) {
//											logger.error(ie.getMessage() + "タイムアウト");
//											System.out.println(ie.getMessage() + "タイムアウト");
//										}
//										is = process.getErrorStream();
//										logger.info("getErrorStream取得");
//										System.out.println("getErrorStream取得");
//										isr = new InputStreamReader(is);
//										logger.info("InputStreamReader取得");
//										System.out.println("InputStreamReader取得");
//										br = new BufferedReader(isr);
//										while ( (answer = br.readLine()) !=null) {
//											logger.info(answer);
//										}
//									}
//								}catch(Exception ex){
//									logger.error(ex.getMessage() + "タイムアウト");
//									System.out.println(ex.getMessage() + "タイムアウト");
//								}
					}
				}else process = pdfexe.openDocument(rtn, true);

				// edit s.inoue 2009/12/07
        		// if (process==null || process.exitValue() == 1) {
				if (process==null) {
        			JErrorMessage.show("M9727", ParentFrame);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			//ファイルを削除
			try {
				File f1 = new File(WORK_PDF_TMP_DUMMY_PDF);
				File f2 = new File(WORK_PDF_TMP_INKEKKA2PAGE_PDF);
				File f3 = new File(WORK_PDF_TMP_INKEKKA3PAGE_PDF);
				File f4 = new File(WORK_PDF_TMP_INKEKKA4PAGE_PDF);

				f1.deleteOnExit();
				f2.deleteOnExit();
				f3.deleteOnExit();
				f4.deleteOnExit();
				rtn.deleteOnExit();

			} catch (RuntimeException e) {
				e.printStackTrace();
			}
	}

	public File printKekka() {

		return rtn;

	}

	/*
	 *  印刷内容
	 */
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		if (pageIndex >= 1) {
			return Printable.NO_SUCH_PAGE;
		}
		g.translate((int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableY()));

		((Graphics2D) g).transform(java.awt.geom.AffineTransform.getScaleInstance(1, 1));

		//
		// ここに印刷処理を記述する.
		//

		return Printable.PAGE_EXISTS;
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

