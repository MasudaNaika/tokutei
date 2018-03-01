package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.AddMedical;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Gekeihyo;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

// add s.inoue 2009/09/20
// 集計表用のPDF作成
public class PrintShukeiList {

    private static org.apache.log4j.Logger logger = Logger
    	.getLogger(PrintShukeiList.class);

	String pdfPathList = "";
	Hashtable<String, String> resultItem = new Hashtable<String, String>();

	/**
	 * 集計表のページ作成
	 * @param shukeiData
	 */
	public String createShukeiList(
			Hashtable<String, String> kikanData,
			ArrayList<String> ShukeiKey){

		AddMedical addM = new AddMedical();

		List<TreeMap<String, String>> shukeiData = addM.getShukeiList(ShukeiKey);
		createPdf(kikanData,shukeiData);

		return pdfPathList;
	}

	/**
	 * PDFを実際に印字する。
	 */
	private void createPdf(
			Hashtable<String, String> kikanData,
			List<TreeMap<String, String>> shukeiData) {

		TreeMap<String, String> resultItem = new TreeMap<String, String>();

		String tempPath = PrintDefine.WORK_SHUKEI_PDF;

		try {
			PdfReader reader = new PdfReader(tempPath);

			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tempPath+Integer.toString(0)));
			AcroFields form = stamp.getAcroFields();

			// 年度 (集計データを作成した日付から起算)
			// edit s.inoue 2009/09/22
//			String updateTime = resultItem.get(PrintDefine.UPDATE_TIMESTAMP);
//			jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil.ge
//			FiscalYearUtil.getThisYear(resultItem.get(PrintDefine.J))
			// 年,月
//			form.setField("GETSUBUN_NEN" , kikanData.get("KIKAN_NAME"));
//			form.setField("GETSUBUN_TUKI" , kikanData.get("KIKAN_NAME"));

			// ＰＤＦへ結果表示
			// ヘッダー
			form.setField("KENSHIN" , kikanData.get("KIKAN_NAME"));

			long jisiSyokei = 0;
			long tankaSyokei = 0;
			long madogutiSyokei = 0;
			long seikyuSyokei = 0;
			long sonotaSyokei = 0;

			// add s.inoue 2009/09/16
			NumberFormat kingakuFormat = NumberFormat.getNumberInstance();

			// edit s.inoue 2009/10/15
//			for (int i = 0; i < shukeiData.size(); i++) {
			for (int i = 0; i < shukeiData.size(); i++) {
				if (shukeiData.size() >=20) {
					break;
				}
				resultItem = shukeiData.get(i);

				// NO
				form.setField("NO_" +Integer.toString(i+1),Integer.toString(i+1));

				// 保険者番号、保険者名、実施総数、単価総計、窓口負担総計、請求額総計、その他総計
				form.setField("HKNJYANUM_" + (i+1) ,resultItem.get("HKNJANUM"));
				form.setField("HKNJYANAME_"+ (i+1) ,resultItem.get("HKNJANAME"));

				// form.setField("JISISOUSU_"+ i ,resultItem.get("KENSA_JISI_SOUSU"));
				long jisisousu = Long.parseLong(resultItem.get("KENSA_JISI_SOUSU"));
				form.setField("JISISOUSU_" + (i+1) ,kingakuFormat.format(jisisousu));

				// form.setField("TANKASOUKEI_"  + i ,resultItem.get("KENSA_TANKA_SOUKEI"));
				long tankasoukei = Long.parseLong(resultItem.get("KENSA_TANKA_SOUKEI"));
				form.setField("TANKASOUKEI_" + (i+1) ,kingakuFormat.format(tankasoukei));

				// form.setField("MADOSOUKEI_"   + i ,resultItem.get("KENSA_MADO_SOUKEI"));
				long madosoukei = Long.parseLong(resultItem.get("KENSA_MADO_SOUKEI"));
				form.setField("MADOSOUKEI_" + (i+1) ,kingakuFormat.format(madosoukei));

				// form.setField("SEIKYUSOUKEI_" + i ,resultItem.get("KENSA_SEIKYU_SOUKEI"));
				long seikyusoukei = Long.parseLong(resultItem.get("KENSA_SEIKYU_SOUKEI"));
				form.setField("SEIKYUSOUKEI_" + (i+1) ,kingakuFormat.format(seikyusoukei));

				// form.setField("SONOTASOUKEI_" + i ,resultItem.get("KENSA_SONOTA_SOUKEI"));
				long sonotasoukei = Long.parseLong(resultItem.get("KENSA_SONOTA_SOUKEI"));
				form.setField("SONOTASOUKEI_" + (i+1) ,kingakuFormat.format(sonotasoukei));

				// 合計金額計算
				jisiSyokei += jisisousu;
				tankaSyokei += tankasoukei;
				madogutiSyokei += madosoukei;
				seikyuSyokei += seikyusoukei;
				sonotaSyokei += sonotasoukei;
			}

			// 実施総数合計
			form.setField("JISISOUSUGOUKEI", String.valueOf(kingakuFormat.format(jisiSyokei)));
			// 単価合計
			form.setField("TANKASOUKEIGOUKEI", String.valueOf(kingakuFormat.format(tankaSyokei)));
			// 窓口負担合計
			form.setField("MADOSOUKEIGOUKEI", String.valueOf(kingakuFormat.format(madogutiSyokei)));
			// 請求金額合計
			form.setField("SEIKYUSOUKEIGOUKEI", String.valueOf(kingakuFormat.format(seikyuSyokei)));
			// その他合計
			form.setField("SONOTASOUKEIGOUKEI", String.valueOf(kingakuFormat.format(sonotaSyokei)));

			stamp.setFormFlattening(true);
			stamp.close();
			reader.close();

		}
		catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		// edit s.inoue 2009/09/30
		pdfPathList = tempPath+Integer.toString(0);
	}

	// 年月日フォーマット
	private String replaseNenGaPii(List kensaNenList,int i ) {
		String kensaNengappi;
		try {
			kensaNengappi = (String)kensaNenList.get(i);
			kensaNengappi = kensaNengappi.replaceAll("年", "");
			kensaNengappi = kensaNengappi.replaceAll("月", "");
			kensaNengappi = kensaNengappi.replaceAll("日", "");
		} catch (RuntimeException e) {
			return "19010101";
		}
		return kensaNengappi;
	}
// del s.inoue 2009/09/30
//	/**
//	 * 結果印字
//	 *
//	 * @param valuesItem
//	 * @param form
//	 * @param roopi
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private void setKekka(
//			TreeMap<String, String> valuesItem,
//			AcroFields form,
//			int roopi) throws IOException, DocumentException {
//
//
//		// 保険者番号、保険者名、実施総数、単価総計、窓口負担総計、請求額総計、その他総計
//		form.setField("HKNJYANUM_" + roopi ,valuesItem.get("HKNJANUM"));
//		form.setField("HKNJYANAME_"+ roopi ,valuesItem.get("HKNJANAME"));
//		form.setField("JISISOUSU_"+ roopi ,valuesItem.get("KENSA_JISI_SOUSU"));
//
//		long tankaGoukei = Long.parseLong(valuesItem.get("KENSA_TANKA_SOUKEI"));
//		form.setField("TANKA_GOUKEI_" + roopi ,kingakuFormat.format(tankaGoukei));
//
//		form.setField("TANKASOUKEI_"  + roopi ,valuesItem.get("KENSA_TANKA_SOUKEI"));
//		form.setField("MADOSOUKEI_"   + roopi ,valuesItem.get("KENSA_MADO_SOUKEI"));
//		form.setField("SEIKYUSOUKEI_" + roopi ,valuesItem.get("KENSA_SEIKYU_SOUKEI"));
//		form.setField("SONOTASOUKEI_" + roopi ,valuesItem.get("KENSA_SONOTA_SOUKEI"));
//	}
}
