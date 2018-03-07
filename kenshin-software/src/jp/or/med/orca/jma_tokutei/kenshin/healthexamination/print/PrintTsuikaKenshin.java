package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.printer.JGraphicPrinter;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 追加健診項目表を作成する
 * ※検査項目は全てブランクで作成する（受診者の情報のみ設定）
 * 
 */
public class PrintTsuikaKenshin extends JGraphicPrinter {

	private static final String OUTPUT_PATH = "." + File.separator + "Data" + File.separator + "PDF" + File.separator; 		//出力先のパス（ディレクトリ）
	private static final String TEMPLATE_PATH = "." + File.separator + "work" + File.separator + "PDF" + File.separator;	//テンプレートファイルのディレクトリパス
	private static final String IN_FILE_NAME = "inNyuryokuTemplate_Add.pdf";	//入力テンプレートファイル名
	private static final String OUT_FILE_NAME = "outNyuryokuTemplate_Add.pdf";	//出力ファイル名

	//入力（フォーム）データ
	private Hashtable<String, Object> printData = null;
	
	
	/**
	 * コンストラクタ
	 * 
	 * @param printData	フォームデータ
	 */
	public PrintTsuikaKenshin(Hashtable<String, Object> printData) {
		this.printData = printData;
	}

	/**
	 * PDFファイル（追加健診項目表）の作成
	 * ※検査項目は全てブランクで作成する（受診者の情報のみ設定）
	 */
	@Override
	public File printKekka() {

		//出力先ディレクトリの作成
		File outfile = new File(OUTPUT_PATH);
		outfile.mkdirs();

		//ファイル群
		PdfReader reader = null;
		FileOutputStream fileOutputStream = null;
		PdfStamper stamp = null;
		File file = null;
		
		try {
			//個人情報 PrintDataから個人情報を抽出
			Kojin tmpKojin = (Kojin) printData.get("Kojin");
			Hashtable<String, String> kojinData = tmpKojin.getPrintKojinData();

			//ファイルを作成し、フォームへ埋め込む
			file = new File(OUTPUT_PATH, OUT_FILE_NAME + kojinData.get("UKETUKE_ID") + replaseNenGaPii(kojinData.get("KENSA_NENGAPI")));
			reader = new PdfReader(TEMPLATE_PATH + IN_FILE_NAME);
			fileOutputStream = new FileOutputStream(file);
			stamp = new PdfStamper(reader, fileOutputStream);
			AcroFields form = stamp.getAcroFields();
			// T31:実施日			
			form.setField("T31" , kojinData.get("KENSA_NENGAPI"));
			// T32:受診券番号
			form.setField("T32" , kojinData.get("JYUSHIN_SEIRI_NO"));
			// T33:保険者番号
			form.setField("T33" , kojinData.get("HKNJANUM"));
			// T34:被保険者証記号
			form.setField("T34" , kojinData.get("HIHOKENJYASYO_KIGOU"));
			// T35:番号
			form.setField("T35" , kojinData.get("HIHOKENJYASYO_NO"));
			// T36:ふりがな
			form.setField("T36" , kojinData.get("KANANAME"));
			// T37:氏名
			form.setField("T37" , kojinData.get("NAME"));
			// T38:生年月日
			form.setField("T38" , kojinData.get("BIRTHDAY"));
			// T39:性別
			form.setField("T39" , kojinData.get("SEX"));
			// T40:年齢
			form.setField("T40" , kojinData.get("AGE"));
			
			//埋め込み後にフラット化（変更不可）する
			stamp.setFormFlattening(true);

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (stamp != null) {
				try {
					stamp.close();
				} catch (DocumentException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return file;
	}
	
	/**
	 * 全角の"年月日"を削除
	 * @param kensaNengappi
	 * @return
	 */
	private String replaseNenGaPii(String kensaNengappi) {
		String result;
		if ((kensaNengappi != null) && (kensaNengappi.trim().length() != 0)) {
			result = kensaNengappi.replaceAll("年", "").replaceAll("月", "").replaceAll("日", "");
		} else {
			result = "";
		}
		return result;
	}
}
