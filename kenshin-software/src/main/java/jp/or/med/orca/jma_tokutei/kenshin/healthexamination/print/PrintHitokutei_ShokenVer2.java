//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.TreeMap;
//
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//import jp.or.med.orca.jma_tokutei.common.printer.JTKenshinPrint;
//import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.AddMedical;
//
//import org.apache.log4j.Logger;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.AcroFields;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfStamper;
//
///**
// * 非特定健診項目のページ作成
// * @author DevMaster
// *
// */
//public class PrintHitokutei_ShokenVer2 extends JTKenshinPrint {
//
//	public static final String WORK_PDF_HITOKUTEI_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"hitokutei.pdf";
//
//	// add s.inoue 2014/02/21
//	public static final String WORK_SHOKEN_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"shokenList.pdf";
//	
//	// 実施区分
//	private static final String KEKKA_OUTPUT_JISI_KBN_0 = "未実施";
//	private static final String KEKKA_OUTPUT_JISI_KBN_2 = "測定不可能";
//	private static final String BASEVALUPPLIMIT = "baseValUppLimit";
//	private static final String BASEVALDWNLIMIT = "baseValDwnLimit";
//
//	AddMedical addMedical = new AddMedical();
//
//	List pdfPathList = new ArrayList();
//
//	String kihonPdf = "";
//
//	PrintKihonChkList pKChkList = new PrintKihonChkList();
//
//	// add s.inoue 2014/02/18
//	PrintShokenList pShokenList = new PrintShokenList();
//	
//	//allマップ項目を詰めて入れる
//	Hashtable<String, Hashtable<String, String>> allMap =
//		new Hashtable<String, Hashtable<String, String>> ();
//
//	public final static String PATH_PRINT_PROPERTIES = "print.properties";
//	
//	private static org.apache.log4j.Logger logger = Logger.getLogger(PrintHitokutei_ShokenVer2.class);
//	/**
//	 * 非特定健診項目のページ作成
//	 * @param kensaNenList
//	 * @param kojinData
//	 * @return pdfPathList
//	 */
//	public List startAddMedical(List kensaNenList, Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData, String ishiName,int printSelect ){
//
//		String kensaNengappi = "";
//		String zKensaNengappi = "";
//		String zzKensaNengappi = "";
//		String tmpPath = WORK_PDF_HITOKUTEI_PDF;
//		// edit ver2 s.inoue 2009/06/22
//		TreeMap<String, String> resultItem = new TreeMap<String, String>();
//		TreeMap<String, String> zResultItem = new TreeMap<String, String>();
//		TreeMap<String, String> zzResultItem = new TreeMap<String, String>();
//
//		//今回の値のある項目コードのリスト
//		ArrayList<String> itemCodes = new ArrayList<String> ();
//		
//		// add s.inoue 2014/02/24
//		//今回の値のある項目コードのリスト
//		TreeMap<String, ArrayList<String>> mapKonkai = new TreeMap<String, ArrayList<String>>();
//		TreeMap<String, ArrayList<String>> mapZenkai = new TreeMap<String, ArrayList<String>>();
//		TreeMap<String, ArrayList<String>> mapZenzenkai = new TreeMap<String, ArrayList<String>>();
//		
//		ArrayList<String> itemShokenCodes = new ArrayList<String> ();
//		
//		//項目用
//		ArrayList<String> koumokuCd = new ArrayList<String> ();
//
//		//今回用table
//		Hashtable<String, String>  koumokuName = new Hashtable<String, String>();
//		Hashtable<String, String>  taniList = new Hashtable<String, String>();
//		Hashtable<String, String>  baseVal = new Hashtable<String, String>();
//		Hashtable<String, String>  baseValUppLimit = new Hashtable<String, String>();
//		Hashtable<String, String>  baseValDwnLimit = new Hashtable<String, String>();
//		Hashtable<String, String>  HL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  valsesList = new Hashtable<String, String>();
//		Hashtable<String, String>  DATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  JISI_KBN = new Hashtable<String, String>();
//
//		//前回用table
//		Hashtable<String, String> zHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> zValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> zDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  zJISI_KBN = new Hashtable<String, String>();
//
//		//前々回用table
//		Hashtable<String, String> zzHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> zzValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> zzDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  zzJISI_KBN = new Hashtable<String, String>();
//
//		kensaNengappi = replaseNenGaPii(kensaNenList,0);
//
//		//追加の健診今回tmp(データ取得）
//		// edit ver2 s.inoue 2009/06/22
//		List<TreeMap<String, String>> tmpTuikaList = addMedical.tuika(kojinData,kensaNengappi,0,koumokuCd);
//		//追加の健診今回
//		List<TreeMap<String, String>> tuikaList = new ArrayList<TreeMap<String, String>>() ;
//		//追加の健診前回
//		List<TreeMap<String, String>> zTuikaList = new ArrayList<TreeMap<String, String>>() ;
//		//追加の健診前々回
//		List<TreeMap<String, String>> zzTuikaList = new ArrayList<TreeMap<String, String>>() ;
//		//生活基本チェックリスト
//		List<TreeMap<String, String>> seikatuKihonList = new ArrayList<TreeMap<String, String>>() ;
//		//所見有無用リスト
//		List<String> syokenUmuList = new ArrayList<String>();
//		//所見用リスト
//		List<TreeMap<String, String>> syokenList = new ArrayList<TreeMap<String, String>>() ;
//
//		//所見、生活基本チェックリストは退避しておく
//		for(int i = 0; i<tmpTuikaList.size();i++){
//			resultItem = tmpTuikaList.get(i);
//			
//			// System.out.println(resultItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//			
//			String val = resultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//			String code = resultItem.get(PrintDefine.CODE_KOUMOKU);
//			// add ver2 s.inoue 2009/07/15
//			String kensaNengapi = resultItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//			if(!val.isEmpty()){
//// edit s.inoue 2014/02/26
//// 有無もlistに含める
////				if(PrintDefine.syoken.contains(code)){
////					//所見の有無
////					syokenUmuList.add(code);
////				}
//				if(PrintDefine.syoken.contains(code)){
//					//所見
//					// edit s.inoue 2014/03/07
//					// 過去も取込
//					// if(kensaNengapi.equals(kensaNengappi)){
//						syokenList.add(tmpTuikaList.get(i));
//						// add s.inoue 2014/02/24
//						itemShokenCodes.add(resultItem.get(PrintDefine.CODE_KOUMOKU));
//					// }
//				}else if(PrintDefine.seikatuKihon.contains(code)){
//					//生活基本チェックリスト
//					seikatuKihonList.add(tmpTuikaList.get(i));
//				}else{
//					//追加の健診
//					tuikaList.add(tmpTuikaList.get(i));
//					itemCodes.add(resultItem.get(PrintDefine.CODE_KOUMOKU));
//				}
//			}
//		}
//
//		// 結果表出力処理
//		// 件数表示
//		// 今回と比較して一致すれば、同列に表示。一致しなければ新たに行を追加
//		int keyCnt = 0;
//		List<String> itemMergedCodes = new ArrayList<String> ();
//
//		for (String keyItem :itemCodes){
//			keyCnt = 0;
//			for (String item :itemCodes){
//				if (keyItem.equals(item)) {
//					if (keyCnt == 0 && !itemMergedCodes.contains(keyItem)){
//						itemMergedCodes.add(item);
//					}
//					keyCnt ++;
//				}
//			}
//		}
//
//		// add s.inoue 2014/02/24
//		// 所見の履歴処理
//		// 項目コード上5桁でGroup(所見の有無)
//		// 医師の氏名は別フィールド
//		// 項目順にしてマージするのが見やすくベスト
//		int keyShokenCnt = 0;
//		// del s.inoue 2014/03/17 マージするとずれる使わない
//		List<String> itemShokenMergedCodes = new ArrayList<String> ();
//		for (String keyItem :itemShokenCodes){
//			keyShokenCnt = 0;
//			for (String item :itemShokenCodes){
//				if (keyItem.equals(item)) {
//					if (keyShokenCnt == 0 && !itemShokenMergedCodes.contains(keyItem)){
//						itemShokenMergedCodes.add(item);
//					}
//					keyShokenCnt ++;
//				}
//			}
//		}
//		
//		//ページ枚数カウント
//		int templistNum =itemMergedCodes.size();//項目の数
//		// edit s.inoue 2014/03/19
//		// int div = templistNum/20;//項目で割ったページ枚数
//		// int surPlus = templistNum%20;//項目で割ったページ枚数の余り
//		int div = templistNum/30;//項目で割ったページ枚数
//		int surPlus = templistNum%30;//項目で割ったページ枚数の余り
//		
//		int startNum = 0;//１ページに印字する枚数の最大数字
//		int cnt = 0;//ページのカウンター
//		int pageNum = 0; //ページ印字の分子
//		int koumokuMaxPageNum = div;
//		if (surPlus > 0) {
//			koumokuMaxPageNum++;
//		}
//		
//// del s.inoue 2014/03/18
////		//所見
//		int sNum = syokenList.size();//所見の数
//////		int sDiv = sNum/4;//MAXを４として４で割った所見数
//////		int sSurPlus = sNum%4;//MAXを４として４で割った所見数の余り
////		int sDiv = sNum/3;//MAXを３として３で割った所見数
////		int sSurPlus = sNum%3;//MAXを３として３で割った所見数の余り
////		int sStrNum = 0;//１ページに印字する枚数の最大数字
////
////		int syokenMaxPageNum = sDiv;
////		if (sSurPlus > 0) {
////			syokenMaxPageNum++;
////		}
//
//		int commonMaxPageNum = 0;
//// del s.inoue 2014/03/18		
////		if (syokenMaxPageNum>koumokuMaxPageNum)
////		{
////			commonMaxPageNum=syokenMaxPageNum;
////		}else{
//			commonMaxPageNum=koumokuMaxPageNum;
////		}
//
//		// add s.inoue 2014/03/18
//		int pNum_page = itemShokenCodes.size();
//		int pDiv_page = pNum_page/15;	 //MAXを15として15で割った所見数
//		
////		int pSurPlus_page = pNum_page%15;//MAXを15として15で割った所見数の余り
//		// 1page目
//		if (pNum_page > 0){
//			if (pDiv_page == 0) {
//				++pDiv_page;
//			}
//		}
//		koumokuMaxPageNum+=pDiv_page;
//		commonMaxPageNum +=pDiv_page;
//			
//// del s.inoue 2014/03/18			
//		//生活基本の項目がひとつでもあれば、基本チェックリスト作成。
////		if (div > sDiv) {
//			if(seikatuKihonList.size()>0){
//				kihonPdf = pKChkList.createChkList(kensaNenList, kojinData, kikanData, ishiName ,commonMaxPageNum++,printSelect);
////				syokenMaxPageNum++;
//				// add ver2 s.inoue 2009/06/25
//				koumokuMaxPageNum++;
//			}
////		}else {
//			if(seikatuKihonList.size()>0){
//				kihonPdf = pKChkList.createChkList(kensaNenList, kojinData, kikanData, ishiName ,commonMaxPageNum++,printSelect);
//				// edit ver2 s.inoue 2009/07/28 所見ページもカウント
////				syokenMaxPageNum++;
//				koumokuMaxPageNum++;
//			}
////		}
//
//		//追加の健診と所見が無い場合は、戻る
//		if(tuikaList.size()==0 && sNum == 0){
//			//生活基本のページが作成されていれば最後に追加
//			if(isVal(kihonPdf)){
//				pdfPathList.add(kihonPdf);
//			}
//			return pdfPathList;
//		}
//
////		//前回、前々回取得
////		int zenkaiFlg = 0;
////		String tmpKensaNenGappi = "";
////		zKensaNengappi = replaseNenGaPii(kensaNenList,1);
////		zzKensaNengappi = replaseNenGaPii(kensaNenList,2);
////
////		if(!zKensaNengappi.equals("") ){
////			tmpKensaNenGappi = zKensaNengappi;
////			zTuikaList = addMedical.tuika(kojinData,tmpKensaNenGappi,1,itemCodes);
////			zenkaiFlg = 1;
////		}
////		if(!zzKensaNengappi.equals("") ){
////			tmpKensaNenGappi = zzKensaNengappi;
////			zzTuikaList = addMedical.tuika(kojinData,tmpKensaNenGappi,1,itemCodes);
////			zenkaiFlg = 2;
////		}
//
//		boolean isMale = false;
//		// edit s.inoue 2009/11/11
//		/* 性別 */
//		if (kojinData.get("SEX").equals("男性")) {
//			isMale = true;
//		}
//
//		/* 基準値上限,下限 */
//		String kenshinDate ="";
//		String prekenshinDate ="";
//		int historyCnt = 0;
//
//		// カウント用-健診年月日
////		for (int kenCnt=0;kenCnt < kensaNenList.size(); kenCnt++){
////			kensaNengappi = replaseNenGaPii(kensaNenList,historyCnt);
//
//			for(int i = 0; i<tuikaList.size();i++){
//				resultItem = tuikaList.get(i);
//				// System.out.println(resultItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//				if (resultItem == null)
//					break;
//
//				// 追加健診項目リスト
//				kenshinDate =resultItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//
//				// 健診実施日毎に履歴表示する
//				if (!kenshinDate.equals(prekenshinDate)){
//					// キー情報
//					prekenshinDate=resultItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//					// edit ver2 s.inoue 2009/06/22
//					// カウント用-健診年月日
//					for (int hisCnt=0;hisCnt< kensaNenList.size(); hisCnt++){
//						kensaNengappi = replaseNenGaPii(kensaNenList,historyCnt);
//						
////						// add s.inoue 2014/06/11
////						historyCnt++;
//// del s.inoue 2014/05/26							
//						if (kensaNengappi.equals(kenshinDate)){
//							historyCnt++;break;
//						}else{
//							historyCnt++;
//						}
//					}
//				}
//
//				// 今回値
//				// 固定情報 項目名、単位、基準値
//				String cd = resultItem.get(PrintDefine.CODE_KOUMOKU);
//				koumokuCd.add(cd);
//				String name = resultItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME);
//				// delete ver2 s.inoue 2009/06/26
//				//resultItem.get(PrintDefine.COLUMN_NAME_KENSA_HOUHOU);
//				koumokuName.put(cd, name);
//
//				String tani = resultItem.get(PrintDefine.COLUMN_NAME_TANI);
//				taniList.put(cd, tani);
//
//// edit s.inoue 2009/10/28
//// 修正漏れ
////				if (isMale) {
////					kagen = resultItem.get(PrintDefine.COLUMN_NAME_JS_KAGEN);
////					jyogen = resultItem.get(PrintDefine.COLUMN_NAME_JS_JYOUGEN);
////				}else{
////					kagen = resultItem.get(PrintDefine.COLUMN_NAME_DS_KAGEN);
////					jyogen = resultItem.get(PrintDefine.COLUMN_NAME_DS_JYOUGEN);
////				}
//				// edit s.inoue 2009/10/28
//				// データタイプが数値のみ
//				String kagen = "";
//				String jyogen = "";
//				if ("1".equals(resultItem.get(PrintDefine.DATA_TYPE))) {
//					// edit s.inoue 2009/10/28
//					// 基準値フォーマット対応
//					String kekkaFormat = resultItem.get(PrintDefine.COLUMN_MAX_BYTE_LENGTH);
//					kekkaFormat = kekkaFormat != null ? kekkaFormat: "";
//
//					if (isMale){
//						kagen = resultItem.get(PrintDefine.COLUMN_NAME_DS_KAGEN);
//						kagen = JValidate.validateKensaKekkaDecimal(kagen,kekkaFormat);
//						kagen = kagen != null ? kagen:"";
//						jyogen = resultItem.get(PrintDefine.COLUMN_NAME_DS_JYOUGEN);
//						jyogen = JValidate.validateKensaKekkaDecimal(jyogen,kekkaFormat);
//						jyogen = jyogen != null ? jyogen:"";
//					}else{
//						kagen = resultItem.get(PrintDefine.COLUMN_NAME_JS_KAGEN);
//						kagen = JValidate.validateKensaKekkaDecimal(kagen,kekkaFormat);
//						kagen = kagen != null ? kagen:"";
//						jyogen = resultItem.get(PrintDefine.COLUMN_NAME_JS_JYOUGEN);
//						jyogen = JValidate.validateKensaKekkaDecimal(jyogen,kekkaFormat);
//						jyogen = jyogen != null ? jyogen:"";
//					}
//				}
//
//				if(isVal(kagen)||isVal(jyogen)){
//					baseVal.put(cd,kagen +"～" +jyogen);
//					baseValUppLimit.put(cd, jyogen);
//					baseValDwnLimit.put(cd, kagen);
//				}else{
//					baseVal.put(cd,"");
//					baseValUppLimit.put(cd,"");
//					baseValDwnLimit.put(cd,"");
//				}
//
//				// 履歴結果値毎に格納する
//				String HL = resultItem.get(PrintDefine.STR_H_L);
//				// HL 履歴毎に格納
//				if (historyCnt == 1){
//					HL_LIST.put(cd,HL);
//				}else if (historyCnt == 2){
//					zHL_LIST.put(cd,HL);
//				}else if (historyCnt == 3){
//					zzHL_LIST.put(cd,HL);
//				}
//
//				String vales = resultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//				// 結果値 履歴毎に格納
//				if (historyCnt == 1){
//					valsesList.put(cd,vales);
//				}else if (historyCnt == 2){
//					zValsesList.put(cd,vales);
//				}else if (historyCnt == 3){
//					zzValsesList.put(cd,vales);
//				}
//
//				String dataType = resultItem.get(PrintDefine.DATA_TYPE);
//				// データ型 履歴毎に格納
//				if (historyCnt == 1){
//					DATATYPE_LIST.put(cd,dataType);
//				}else if (historyCnt == 2){
//					zDATATYPE_LIST.put(cd,dataType);
//				}else if (historyCnt == 3){
//					zzDATATYPE_LIST.put(cd,dataType);
//				}
//
//				String jisiKbn = resultItem.get(PrintDefine.COLUMN_NAME_JISI_KBN);
//				// データ型 履歴毎に格納
//				if (historyCnt == 1){
//					JISI_KBN.put(cd,jisiKbn);
//				}else if (historyCnt == 2){
//					zJISI_KBN.put(cd,jisiKbn);
//				}else if (historyCnt == 3){
//					zzJISI_KBN.put(cd,jisiKbn);
//				}
//			}
////		}
//
////		//前回、前々回の値のある健診項目を抜き出す
////		if(!zTuikaList.isEmpty()){
////			for(int i = 0; i<zTuikaList.size();i++){
////				zResultItem = (Hashtable<String, String>)zTuikaList.get(i);
////				String cd = zResultItem.get(PrintDefine.CODE_KOUMOKU);
////
////				String HL = zResultItem.get(PrintDefine.STR_H_L);
////				zHL_LIST.put(cd,HL);
////
////				String vales = zResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
////				zValsesList.put(cd,vales);
////
////				String dataType = zResultItem.get("DATA_TYPE");
////				zDATATYPE_LIST.put(cd,dataType);
////
////				String zjisiKbn = resultItem.get("JISI_KBN");
////				zJISI_KBN.put(cd,zjisiKbn);
////			}
////		}
////		//前々回取得
////		if(!zzTuikaList.isEmpty()){
////			for(int i = 0; i<zzTuikaList.size();i++){
////				zzResultItem = (Hashtable<String, String>)zzTuikaList.get(i);
////				String cd = zzResultItem.get(PrintDefine.CODE_KOUMOKU);
////
////				String HL = zzResultItem.get(PrintDefine.STR_H_L);
////				zzHL_LIST.put(cd,HL);
////
////				String vales = zzResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
////				zzValsesList.put(cd,vales);
////
////				String dataType = zzResultItem.get("DATA_TYPE");
////				zzDATATYPE_LIST.put(cd,dataType);
////
////				// add s.inoue 20080909
////				String zzjisiKbn = resultItem.get("JISI_KBN");
////				zzJISI_KBN.put(cd,zzjisiKbn);
////
////			}
////		}
//
//		//固定
//		allMap.put("koumokuName", koumokuName);
//		allMap.put("taniList", taniList);
//		allMap.put("baseVal", baseVal);
//		allMap.put("baseValUppLimit", baseValUppLimit);
//		allMap.put("baseValDwnLimit", baseValDwnLimit);
//		//今回分
//		allMap.put("HL_LIST", HL_LIST);
//		allMap.put("valsesList", valsesList);
//		allMap.put("DATATYPE_LIST", DATATYPE_LIST);
//		allMap.put("JISI_KBN",JISI_KBN);
//		//前回
//		allMap.put("zHL_LIST", zHL_LIST);
//		allMap.put("zValsesList", zValsesList);
//		allMap.put("zDATATYPE_LIST", zDATATYPE_LIST);
//		allMap.put("zJISI_KBN",zJISI_KBN);
//		//前々回分
//		allMap.put("zzHL_LIST", zzHL_LIST);
//		allMap.put("zzValsesList", zzValsesList);
//		allMap.put("zzDATATYPE_LIST", zzDATATYPE_LIST);
//		allMap.put("zzJISI_KBN",zzJISI_KBN);
//
//		// add s.inoue 2014/02/26
//		// 所見用履歴
//		// edit ver2 s.inoue 2009/06/22
//		//今回用table
//		//項目用
//		ArrayList<String> sKoumokuCd = new ArrayList<String> ();
//		Hashtable<String, String>  sKoumokuName = new Hashtable<String, String>();
////		Hashtable<String, String>  sTaniList = new Hashtable<String, String>();
////		Hashtable<String, String>  sBaseVal = new Hashtable<String, String>();
////		Hashtable<String, String>  sBaseValUppLimit = new Hashtable<String, String>();
////		Hashtable<String, String>  sBaseValDwnLimit = new Hashtable<String, String>();
////		Hashtable<String, String>  sHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  sValsesList = new Hashtable<String, String>();
//		Hashtable<String, String>  sDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  sJISI_KBN = new Hashtable<String, String>();
//
//		//前回用table
////		Hashtable<String, String> szHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> szValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> szDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  szJISI_KBN = new Hashtable<String, String>();
//
//		//前々回用table
////		Hashtable<String, String> szzHL_LIST = new Hashtable<String, String>();
//		Hashtable<String, String> szzValsesList = new Hashtable<String, String>();
//		Hashtable<String, String> szzDATATYPE_LIST = new Hashtable<String, String>();
//		Hashtable<String, String>  szzJISI_KBN = new Hashtable<String, String>();
//		
//		TreeMap<String, String> resultShokenItem = new TreeMap<String, String>();
////		TreeMap<String, String> zResultShokenItem = new TreeMap<String, String>();
////		TreeMap<String, String> zzResultShokenItem = new TreeMap<String, String>();
//		
//		//allマップ項目を詰めて入れる
//		Hashtable<String, Hashtable<String, String>> allMapShoken =
//			new Hashtable<String, Hashtable<String, String>> ();
//		
//		String shokenkenshinDate ="";
//		String preShokenkenshinDate ="";
//		int historyShokenCnt = 0;
//		
//		for(int i = 0; i<syokenList.size();i++){
//			resultShokenItem = syokenList.get(i);
//
//			if (resultShokenItem == null)
//				break;
//
//			shokenkenshinDate =resultShokenItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//
//			// 健診実施日毎に履歴表示する
//			if (!shokenkenshinDate.equals(preShokenkenshinDate)){
//				// キー情報
//				preShokenkenshinDate=resultShokenItem.get(PrintDefine.COLUMN_NAME_KENSA_NENGAPI);
//				// カウント用-健診年月日
//				for (int hisCnt=0;hisCnt< kensaNenList.size(); hisCnt++){
//					kensaNengappi = replaseNenGaPii(kensaNenList,historyShokenCnt);
//					
//					// add s.inoue 2014/06/11
////					historyShokenCnt++;
//// del s.inoue 2014/05/26						
//					if (kensaNengappi.equals(shokenkenshinDate)){
//						historyShokenCnt++;break;
//					}else{
//						historyShokenCnt++;
//					}
//				}
//			}
//
//			// 今回値
//			// 固定情報 項目名、単位、基準値
//			String cd = resultShokenItem.get(PrintDefine.CODE_KOUMOKU);
//			sKoumokuCd.add(cd);
//			String name = resultShokenItem.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME);
//			sKoumokuName.put(cd, name);
//			
//			String vales = resultShokenItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//			// 結果値 履歴毎に格納
//			if (vales.equals("")){
//				System.out.println("");
//			}
//			if (historyShokenCnt == 1){
//				sValsesList.put(cd,vales);
//			}else if (historyShokenCnt == 2){
//				szValsesList.put(cd,vales);
//			}else if (historyShokenCnt == 3){
//				szzValsesList.put(cd,vales);
//			}
//			
//			String dataType = resultShokenItem.get(PrintDefine.DATA_TYPE);
//			// データ型 履歴毎に格納
//			if (historyShokenCnt == 1){
//				sDATATYPE_LIST.put(cd,dataType);
//			}else if (historyShokenCnt == 2){
//				szDATATYPE_LIST.put(cd,dataType);
//			}else if (historyShokenCnt == 3){
//				szzDATATYPE_LIST.put(cd,dataType);
//			}
//			
//			String jisiKbn = resultShokenItem.get(PrintDefine.COLUMN_NAME_JISI_KBN);
//			// データ型 履歴毎に格納
//			if (historyShokenCnt == 1){
//				sJISI_KBN.put(cd,jisiKbn);
//			}else if (historyShokenCnt == 2){
//				szJISI_KBN.put(cd,jisiKbn);
//			}else if (historyShokenCnt == 3){
//				szzJISI_KBN.put(cd,jisiKbn);
//			}
//
//		}
//		
//		//固定
//		allMapShoken.put("koumokuName", sKoumokuName);
////		allMapShoken.put("taniList", taniList);
////		allMapShoken.put("baseVal", baseVal);
////		allMapShoken.put("baseValUppLimit", baseValUppLimit);
////		allMapShoken.put("baseValDwnLimit", baseValDwnLimit);
//		//今回分
////		allMapShoken.put("HL_LIST", HL_LIST);
//		allMapShoken.put("valsesList", sValsesList);
//		allMapShoken.put("DATATYPE_LIST", sDATATYPE_LIST);
//		allMapShoken.put("JISI_KBN",sJISI_KBN);
//		//前回
////		allMapShoken.put("zHL_LIST", zHL_LIST);
//		allMapShoken.put("zValsesList", szValsesList);
//		allMapShoken.put("zDATATYPE_LIST", szDATATYPE_LIST);
//		allMapShoken.put("zJISI_KBN",szJISI_KBN);
//		//前々回分
////		allMapShoken.put("zzHL_LIST", zzHL_LIST);
//		allMapShoken.put("zzValsesList", szzValsesList);
//		allMapShoken.put("zzDATATYPE_LIST", szzDATATYPE_LIST);
//		allMapShoken.put("zzJISI_KBN",szzJISI_KBN);
//		// add s.inoue 2014/02/26 ↑↑↑↑↑↑↑
//		
//		int kFlg = 0;
//// del s.inoue 2014/03/18		
////		int syokenFlg = 0;
//		if(surPlus>0){
//			kFlg = 1;
//		}
//		
//// del s.inoue 2014/03/18		
////		if(sSurPlus>0){
////			syokenFlg = 1;
////		}
//
//		// 所見ベースのループを実装する。項目を駆動にする
////		if(sDiv+syokenFlg<=div+kFlg){
//
//		// add s.inoue 2014/03/18
//		int sStrNum = 0;
//		
//			for(int i = 0;i<div;i++){
//				pageNum++;
//				cnt++;
//				// edit s.inoue 2014/03/19
//				// startNum = 20*cnt;
//				startNum = 30*cnt;
//
////				System.out.println("startNum:" + startNum
////						+"pageNum" + pageNum
////						+"koumokuMaxPageNum" + koumokuMaxPageNum
////						+"sStrNum" + sStrNum
////						);
//
//				// edit s.inoue 2014/03/19
//				// 20→30
//				createPdf(kensaNenList, tmpPath, allMap,startNum,
//						i,surPlus,kojinData,kikanData,ishiName,pageNum,koumokuMaxPageNum,0,syokenList,sStrNum,itemMergedCodes,printSelect,30);
//
//				sStrNum = 3*cnt;
//			}
//			
//			if(surPlus>0){
//				pageNum++;
//				if(div==0){
//					div = 1;
//				}
//
//				// add ver2 s.inoue 2009/07/27
////				if (cnt >= 4){
////					sStrNum ++;
////				}
//
//				// edit s.inoue 2014/03/19
//				// int sabunNum = itemMergedCodes.size() - 20*cnt;
//				int sabunNum = itemMergedCodes.size() - 30*cnt;
//				startNum = itemMergedCodes.size();
//
////				System.out.println("startNum:" + startNum
////						+"pageNum" + pageNum
////						+"koumokuMaxPageNum" + koumokuMaxPageNum
////						+"sStrNum" + sStrNum
////						);
//
//				if (cnt >= 3){
//					sStrNum ++;
//				}
//				createPdf(kensaNenList, tmpPath, allMap,startNum,
//						cnt,surPlus,kojinData,kikanData,ishiName,pageNum,koumokuMaxPageNum,1,syokenList,sStrNum,itemMergedCodes,printSelect,sabunNum);
//			}
//			
//// del s.inoue 2014/03/18
////		//所見を駆動にする
////		}else {
////			if (sSurPlus > 0) {
////				++sDiv;
////			}
////
////			for(int i = 0;i < sDiv;i++){
////				pageNum++;
////				startNum = 20 * cnt++;
////
////				if (templistNum > startNum) {
////					if (templistNum - startNum == surPlus) {
////						startNum += surPlus;
////					}
////				}
////				else {
////					startNum = -20;
////				}
//////				sStrNum = 4 * cnt;
////				sStrNum = 3 * cnt;
////				createPdfBasedSyokenRoop(kensaNenList, tmpPath, allMap,startNum + 20,
////						i,surPlus,kojinData,kikanData,ishiName,pageNum,syokenMaxPageNum,0,syokenList,sStrNum,itemMergedCodes,printSelect);
////			}
////		}
//		
//		// add s.inoue 2014/02/21
//		// 所見ページで別処理を行う
//		// edit s.inoue 2014/03/11 itemShokenMergedCodesマージはしないx
//		// int pTemplistNum =itemShokenCodes.size();//項目の数
//		int pTemplistNum =itemShokenMergedCodes.size();//項目の数
//		
//		int pStartNum = 0;//１ページに印字する枚数の最大数字
//		// int pCnt = 0;//ページのカウンター
//		int pNum = itemShokenCodes.size();
//		
//		int pDiv = pNum/15;//MAXを15として15で割った所見数
//		int pSurPlus = pNum%15;//MAXを15として15で割った所見数の余り
//		int pSubun = 0; //マージ項目でのカウント（ページまたぎ
//		
//		// add s.inoue 2014/03/18
//		int syokenMaxPageNum = commonMaxPageNum;
//		
//		if (pNum > 0){
//			
//			if (pSurPlus > 0) {
//				++pDiv;
//			}
//
//			for(int i = 0;i < pDiv;i++){
//				pageNum++;
//				
//				pStartNum = pSubun;
//				
//				pSubun = createPdfPageSyokenRoop(kensaNenList, tmpPath, allMapShoken,pStartNum,
//						i,pSurPlus,kojinData,kikanData,ishiName,pageNum,syokenMaxPageNum,0,syokenUmuList,syokenList,itemShokenMergedCodes,printSelect);
//			}
//		}
//
//		//生活基本のページが作成されていれば最後に追加
//		if(isVal(kihonPdf)){
//			pdfPathList.add(kihonPdf);
//		}
//		return pdfPathList;
//	}
//
//	/**
//	 * 前回結果取得
//	 * @param zResultItem
//	 * @param zHL_LIST
//	 * @param zValsesList
//	 * @param zDATATYPE_LIST
//	 * @param zzHL_LIST
//	 * @param zzValsesList
//	 * @param zzDATATYPE_LIST
//	 */
//	private void getZenkaiKekka(Hashtable<String, String> zResultItem,
//			ArrayList<String> zHL_LIST, ArrayList<String> zValsesList,
//			ArrayList<String> zDATATYPE_LIST) {
//
//		String zhl = zResultItem.get(PrintDefine.STR_H_L);
//		zHL_LIST.add(zhl);
//
//		String zVal = zResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//		zValsesList.add(zVal);
//
//		String zData = zResultItem.get(PrintDefine.DATA_TYPE);
//		zDATATYPE_LIST.add(zData);
//
//	}
//
//	/**
//	 * 前々回結果取得
//	 * @param zResultItem
//	 * @param zHL_LIST
//	 * @param zValsesList
//	 * @param zDATATYPE_LIST
//	 * @param zzHL_LIST
//	 * @param zzValsesList
//	 * @param zzDATATYPE_LIST
//	 */
//	private void getZZenkaiKekka(Hashtable<String, String> zzResultItem,
//			ArrayList<String> zzHL_LIST,ArrayList<String> zzValsesList,
//			ArrayList<String> zzDATATYPE_LIST) {
//
//		String zzhl = zzResultItem.get(PrintDefine.STR_H_L);
//		zzHL_LIST.add(zzhl);
//
//		String zzVal = zzResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI);
//		zzValsesList.add(zzVal);
//
//		String zzData = zzResultItem.get("DATA_TYPE");
//		zzDATATYPE_LIST.add(zzData);
//	}
//
//	/**
//	 * PDFを実際に印字する。
//	 *
//	 * @param kensaNenList　
//	 * @param tmpPath　ファイル枚数
//	 * @param allMap
//	 * @param startNum　出力項目数
//	 * @param cnt　PDFの枚目
//	 * @param surplus　余り数
//	 * @param kojinData　個人データ
//	 * @param kikanData　機関データ
//	 * @param ishiName　医師の名前
//	 * @param pageNum　ページカウント
//	 * @param div　余り以外のページ数
//	 * @param isflg ２枚目以降の余りを判断
//	 * @param tmpSyoken　所見データ
//	 * @param itemCodes　取得する今回の項目コードが入ったリスト
//	 */
//	private void createPdf(List kensaNenList, String tmpPath, Hashtable allMap,
//			int startNum,int cnt,int surplus,Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData,String ishiName,int pageNum,
//			int div,int isflg, List<TreeMap<String, String>> tmpSyoken, int sStrNum, List<String> itemCodes ,int printSelect,int sabunNum) {
//
//		try {
//			PdfReader reader = new PdfReader(WORK_PDF_HITOKUTEI_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tmpPath+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			//ヘッダー項目、ページ数など印字
//			makeHaeder(kensaNenList, kojinData, kikanData, ishiName, pageNum, div, form, printSelect);
//			// edit s.inoue 2010/04/20
//			int num = sabunNum;
//			// int num = 20;
//
//			// edit s.inoue 2010/04/20
//			// startNum = startNum - 20;
//			startNum = startNum - sabunNum;
//			if (startNum < sabunNum){
//				startNum = 0;
//			}else{
//				if(startNum<=0&&isflg==1){
//					num = surplus;
//					// edit s.inoue 2014/03/19
//					// startNum = startNum + 20;
//					startNum = startNum + 30;
//
//				}else if(startNum<0){
//					num = surplus;
//					startNum = 0;
//				}
//			}
//
//			// edit ver2 s.inoue 2009/06/11
//			// sort用
//			/*
//			 * 項目リストを順番号に従ってソートする。
//			 */
////			ArrayList<String> mergedCodes = new ArrayList<String> ();
////			Object objCodes[] =itemMergedCodes.toArray();
////			Arrays.sort(objCodes);
////
////			for (int itmCnt=0;itmCnt<objCodes.length;itmCnt++){
////				mergedCodes.add(objCodes[itmCnt].toString());
////			}
//
//			for(int kekkaCnt=0;kekkaCnt<num;kekkaCnt++){
//
//				// edit s.inoue 2014/03/19
//				if(itemCodes.size() == startNum)break;
//				
//				// 全体のKeyMapから順に項目コードを取得
//				String koumokuCd = itemCodes.get(startNum);
//
//				// 今回分を取得
//				Hashtable fstMap = (Hashtable)allMap.get("DATATYPE_LIST");
//				// 前回分を取得
//				Hashtable secMap = (Hashtable)allMap.get("zDATATYPE_LIST");
//				// 前々回分を取得
//				Hashtable thrdMap = (Hashtable)allMap.get("zzDATATYPE_LIST");
//
//				/*** 固定情報 ***/
//				//項目名 印字
//				Hashtable koumokuMeiMap = (Hashtable)allMap.get("koumokuName");
//				form.setField("KOUMOKU_" + Integer.toString(kekkaCnt+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//				//単位 印字
//				Hashtable taniMap = (Hashtable)allMap.get("taniList");
//				form.setField("TANI_" + Integer.toString(kekkaCnt+1),(String)taniMap.get(itemCodes.get(startNum)));
//				//基準値 印字
//				Hashtable kijyunMap = (Hashtable)allMap.get("baseVal");
//				form.setField("KIJYUN_" + Integer.toString(kekkaCnt+1),(String)kijyunMap.get(itemCodes.get(startNum)));
//
//				/*** 今回分情報 ***/
//				// 全体のKeyMapに今回用のKeyが含まれているかどうか
//				// System.out.println(koumokuCd);
//				if (fstMap.containsKey(koumokuCd)){
//					//結果 印字
//					String strKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"DATATYPE_LIST","valsesList","KEKA_","JISI_KBN",itemCodes);
//					if (strKekka.equals(""))
//						continue;
//					//H_L 印字
//					hlOut(allMap, startNum, form, kekkaCnt, strKekka,"DATATYPE_LIST","HL_LIST","HL_","JISI_KBN",itemCodes);
//
//					// 同項目が前回に含む場合
//					if (secMap.containsKey(koumokuCd)){
//						//結果(前回）
//						String strZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zDATATYPE_LIST","zValsesList","zKEKA_","zJISI_KBN",itemCodes);
//						if (strZKekka.equals(""))
//							continue;
//						//H_L(前回）
//						hlOut(allMap, startNum, form, kekkaCnt, strZKekka,"zDATATYPE_LIST","zHL_LIST","zHL_","zJISI_KBN",itemCodes);
//					}
//
//					// 同項目が前回に含む場合
//					if (thrdMap.containsKey(koumokuCd)){
//						//結果（前々回）
//						String strZZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zzDATATYPE_LIST","zzValsesList","zzKEKA_","zzJISI_KBN",itemCodes);
//						if (strZZKekka.equals(""))
//							continue;
//						//H_L（前々回）
//						hlOut(allMap, startNum, form, kekkaCnt, strZZKekka,"zzDATATYPE_LIST","zzHL_LIST","zzHL_","zzJISI_KBN",itemCodes);
//					}
//				}else{
//
//					/*** 前回分情報 ***/
//					if (secMap.containsKey(koumokuCd)){
//						//結果（前回）
//						String strZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zDATATYPE_LIST","zValsesList","zKEKA_","zJISI_KBN",itemCodes);
//						if (strZKekka.equals(""))
//							continue;
//						//H_L（前回）
//						hlOut(allMap, startNum, form, kekkaCnt, strZKekka,"zDATATYPE_LIST","zHL_LIST","zHL_","zJISI_KBN",itemCodes);
//					}
//
//					/*** 前々回分情報 ***/
//					if (thrdMap.containsKey(koumokuCd)){
//						//結果（前々回）
//						String strZZKekka = kekkaOut(allMap, startNum, form, kekkaCnt,"zzDATATYPE_LIST","zzValsesList","zzKEKA_","zzJISI_KBN",itemCodes);
//						if (strZZKekka.equals(""))
//							continue;
//						//H_L（前々回）
//						hlOut(allMap, startNum, form, kekkaCnt, strZZKekka,"zzDATATYPE_LIST","zzHL_LIST","zzHL_","zzJISI_KBN",itemCodes);
//					}
//
//				}
//				startNum++;
//			}
//// del s.inoue 2014/03/18
////			String strSyoken = "";
////			strSyoken = strSyoken + cntSyoken(tmpSyoken,sStrNum);
////			if(strSyoken.equals("null")){
////				strSyoken = "";
////			}
////			form.setField("SYHOKEN", strSyoken);
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//
//		pdfPathList.add(tmpPath+Integer.toString(cnt));
//	}
//
//
//
//	private void createPdfBasedSyokenRoop(List kensaNenList, String tmpPath, Hashtable allMap,
//			int startNum,int cnt,int surplus,Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData,String ishiName,int pageNum,
//			int div,int isflg, List tmpSyoken, int sStrNum, List<String> itemCodes,int printSelect ) {
//
//		try {
//			PdfReader reader = new PdfReader(WORK_PDF_HITOKUTEI_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tmpPath+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			//ヘッダー項目、ページ数など印字
//			makeHaeder(kensaNenList, kojinData, kikanData, ishiName, pageNum, div, form, printSelect);
//
//			// edit s.inoue 2014/03/19
//			// int num = 20;
//			int num = 30;
//			int itemCodesSize = itemCodes.size();
//
//			if (itemCodesSize != 0) {
//				if (startNum > 0) {
//					// edit s.inoue 2014/03/19
//					// if (startNum % 20 != 0) {
//					if (startNum % 30 != 0) {
//						num = surplus;
//					}
//				}
//				else {
//					num = 0;
//				}
//			}
//			else {
//				num = 0;
//			}
//			
//			// edit s.inoue 2014/03/19
//			// startNum = (pageNum - 1) * 20;
//			startNum = (pageNum - 1) * 30;
//
//			for(int i=0;i<num;i++){
//
//				//結果 印字
//				String strKekka = kekkaOut(allMap, startNum, form, i,"DATATYPE_LIST","valsesList","KEKA_","JISI_KBN",itemCodes);
//				// edit s.inoue 2009/11/30
//				if (strKekka.equals("")){
//					startNum++;continue;
//				}
//
//				// move s.inoue 2009/11/30
//				//項目名 印字
//				Hashtable koumokuMeiMap = (Hashtable)allMap.get("koumokuName");
//				form.setField("KOUMOKU_" + Integer.toString(i+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//
//				//単位 印字
//				Hashtable taniMap = (Hashtable)allMap.get("taniList");
//				form.setField("TANI_" + Integer.toString(i+1),(String)taniMap.get(itemCodes.get(startNum)));
//
//				//H_L 印字
//				hlOut(allMap, startNum, form, i, strKekka,"DATATYPE_LIST","HL_LIST","HL_","JISI_KBN",itemCodes);
//
//				//基準値 印字
//				Hashtable kijyunMap = (Hashtable)allMap.get("baseVal");
//				form.setField("KIJYUN_" + Integer.toString(i+1),(String)kijyunMap.get(itemCodes.get(startNum)));
//
//				//前回分
//				Hashtable zTmpKekkaMap = (Hashtable)allMap.get("zValsesList");
//				if(!zTmpKekkaMap.isEmpty()){
//					//結果(前回）
//					String strZKekka = kekkaOut(allMap, startNum, form, i,"zDATATYPE_LIST","zValsesList","zKEKA_","zJISI_KBN",itemCodes);
//
//					// edit s.inoue 2009/11/30
//					if (strZKekka.equals("")){
//						startNum++;continue;
//					}
//					//H_L(前回）
//					hlOut(allMap, startNum, form, i, strZKekka,"zDATATYPE_LIST","zHL_LIST","zHL_","zJISI_KBN",itemCodes);
//				}
//
//				//前々回
//				Hashtable zzTmpKekkaMap = (Hashtable)allMap.get("zzValsesList");
//				if(!zzTmpKekkaMap.isEmpty()){
//					//結果（前々回）
//					String strZZKekka = kekkaOut(allMap, startNum, form, i,"zzDATATYPE_LIST","zzValsesList","zzKEKA_","zzJISI_KBN",itemCodes);
//
//					// edit s.inoue 2009/11/30
//					if (strZZKekka.equals("")){
//						startNum++;continue;
//					}
//					//H_L（前々回）
//					hlOut(allMap, startNum, form, i, strZZKekka,"zzDATATYPE_LIST","zzHL_LIST","zzHL_","zzJISI_KBN",itemCodes);
//				}
//
//				startNum++;
//			}
//
//			// edit h.yoshitama 2009/09/15
//			String strSyoken = "";
////			strSyoken = strSyoken + cntSyoken(tmpSyoken,sStrNum - 4);
//			strSyoken = strSyoken + cntSyoken(tmpSyoken,sStrNum - 3);
//			if(strSyoken.equals("null")){
//				strSyoken = "";
//			}
//			form.setField("SYHOKEN", strSyoken);
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//
//		pdfPathList.add(tmpPath+Integer.toString(cnt));
//	}
//
//	// add s.inoue 2014/03/18
//	private int createPdfPageSyokenRoop(List kensaNenList, String tmpPath, Hashtable allMapShoken,
//			int startNum,int cnt,int surplus,Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData,String ishiName,int pageNum,
//			int div,int isflg,List<String> tmpSyokenUmu, List<TreeMap<String, String>> tmpSyoken,List<String> itemCodes,int printSelect ) {
//
//		try {
//			PdfReader reader = new PdfReader(WORK_SHOKEN_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(WORK_SHOKEN_PDF+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			//ヘッダー項目、ページ数など印字
//			makeHaeder(kensaNenList, kojinData, kikanData, ishiName, pageNum, div, form, printSelect);
//
//			int cntKoumoku = 0;
//			
//			// 1page0-14でループ（項目カウントは履歴でずれる）startNumを項目(マージ)
//			for(int i=0;i<15;i++){
//				
//				if (cntKoumoku == 14)break;
//				if (itemCodes.size()==startNum)break;
//				
//				//今回分
//				Hashtable TmpKekkaMap = (Hashtable)allMapShoken.get("valsesList");
//				Hashtable kekkaMap = (Hashtable)allMapShoken.get("valsesList");
//				String strtmpKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//				Hashtable zkekkaMap = (Hashtable)allMapShoken.get("zValsesList");
//				String strztmpKekka = (String)zkekkaMap.get(itemCodes.get(startNum));
//				Hashtable zzkekkaMap = (Hashtable)allMapShoken.get("zzValsesList");
//				String strtzzmpKekka = (String)zzkekkaMap.get(itemCodes.get(startNum));
//				//今回分
//				if(!TmpKekkaMap.isEmpty()){
//					// edit s.inoue 2014/04/25
//					if (strtmpKekka != null){
//					// if (!strtmpKekka.equals("")){
//						if( i>0 )
//						cntKoumoku++;
//						//結果 印字
//						String strKekka = kekkaShokenOut(allMapShoken, startNum, form, cntKoumoku,"DATATYPE_LIST","valsesList","KEKA_","JISI_KBN",itemCodes);
//						//項目名 印字
//						Hashtable koumokuMeiMap = (Hashtable)allMapShoken.get("koumokuName");
//						form.setField("KOUMOKU_" + Integer.toString(cntKoumoku+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//						//項目名 印字
//						form.setField("STR_" + Integer.toString(cntKoumoku+1),"今回受診");
//						//項目名 印字
//						String sDate = kensaNenList.get(0).toString();
//						form.setField("DATE_" + Integer.toString(cntKoumoku+1),sDate);
//					}
//				}
//				
//				//前回分
//				Hashtable zTmpKekkaMap = (Hashtable)allMapShoken.get("zValsesList");
//				if(!zTmpKekkaMap.isEmpty()){
//					if (strztmpKekka!= null){
//						
//						// add s.inoue 2014/05/26
//						if (kensaNenList.size() > 1){
//						
//							if( i>=0 && strtmpKekka!= null)
//							cntKoumoku++;
//							String strZKekka = kekkaShokenOut(allMapShoken, startNum, form, cntKoumoku,"zDATATYPE_LIST","zValsesList","KEKA_","zJISI_KBN",itemCodes);
//							//項目名 印字
//							Hashtable koumokuMeiMap = (Hashtable)allMapShoken.get("koumokuName");
//							form.setField("KOUMOKU_" + Integer.toString(cntKoumoku+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//							form.setField("STR_" + Integer.toString(cntKoumoku+1),"前回受診");
//							String sDate = kensaNenList.get(1).toString();
//							form.setField("DATE_" + Integer.toString(cntKoumoku+1),sDate);
//						}
//					}
//				}
//
//				//前々回
//				Hashtable zzTmpKekkaMap = (Hashtable)allMapShoken.get("zzValsesList");
//				if(!zzTmpKekkaMap.isEmpty()){
//					if (strtzzmpKekka!= null){
//						
//						// add s.inoue 2014/05/26
//						if (kensaNenList.size() > 2){
//							
//							if( i>=0 && strztmpKekka!= null)
//							cntKoumoku++;
//							String strZZKekka = kekkaShokenOut(allMapShoken, startNum, form, cntKoumoku,"zzDATATYPE_LIST","zzValsesList","KEKA_","zzJISI_KBN",itemCodes);
//							//項目名 印字
//							Hashtable koumokuMeiMap = (Hashtable)allMapShoken.get("koumokuName");
//							form.setField("KOUMOKU_" + Integer.toString(cntKoumoku+1),(String)koumokuMeiMap.get(itemCodes.get(startNum)));
//							form.setField("STR_" + Integer.toString(cntKoumoku+1),"前々回受診");
//							String sDate = kensaNenList.get(2).toString();
//							form.setField("DATE_" + Integer.toString(cntKoumoku+1),sDate);
//						}
//					}
//				}
//				
//				startNum++;
//			}
//
//			stamp.setFormFlattening(true);
//
//			stamp.close();
//			reader.close();
//
//		}
//		catch (Exception e){
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		pdfPathList.add(WORK_SHOKEN_PDF+Integer.toString(cnt));
//		return startNum;
//	}
//
//	/* 入力上限値、下限値
//	 * 結果値が下限か上限か判断し、HL判定する
//	 *    @param パターン（数値のみ）
//	 *    @param 基準値上限
//	 *    @param 基準値下限
//	 *    @param 結果
//	 *    @return H:基準値より高,L:基準値より低,NULL:その他
//	 */
//	private static String HLHantei(
//			String inputRangeLowValue,
//			String inputRangeHighValue,
//			String result)
//	{
//		boolean inInputRange = true;
//
//		String hlHanteiFlg = null;
//
//
//			double resultValue = 0;
//
//			try {
//				resultValue = Double.parseDouble(result);
//
//				/* 入力上限値、下限値 */
//				/* 結果値が入力下限値よりも小さい場合 */
//				if (inputRangeLowValue != null && ! inputRangeLowValue.isEmpty()) {
//					try {
//						double inputLowLimit = Double.parseDouble(inputRangeLowValue);
//						// edit s.inoue 20081125 境界含まず
//						//if (resultValue <= inputLowLimit ) {
//						if (resultValue < inputLowLimit ) {
//							inInputRange = false;
//							hlHanteiFlg = "L";
//						}
//
//					} catch (NumberFormatException e) {
//						/* 数値に変換できなかった場合 */
//						e.printStackTrace();
//					}
//				}
//
//				if (inInputRange) {
//					/* 入力上限値範囲外 */
//					if (inputRangeHighValue != null && ! inputRangeHighValue.isEmpty()) {
//						try {
//							double inputHighLimit = Double.parseDouble(inputRangeHighValue);
//							// edit s.inoue 20081125 境界含まず
//							//if (resultValue >= inputHighLimit ) {
//							if (resultValue > inputHighLimit ) {
//								inInputRange = false;
//								hlHanteiFlg = "H";
//							}
//						} catch (NumberFormatException e) {
//							/* 数値に変換できなかった場合 */
//							e.printStackTrace();
//						}
//					}
//				}
//
//			} catch (NumberFormatException e) {
//				/* 数値に変換できなかった場合 */
//				e.printStackTrace();
//			}
//
//		return hlHanteiFlg;
//	}
//
//	/**
//	 * 非特定健診のヘッダー
//	 * ページ、年月日などを印字
//	 *
//	 * @param kensaNenList
//	 * @param kojinData
//	 * @param kikanData
//	 * @param ishiName
//	 * @param pageNum
//	 * @param div
//	 * @param form
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private void makeHaeder(List kensaNenList, Hashtable<String, String> kojinData, Hashtable<String, String>
//	kikanData, String ishiName, int pageNum, int div, AcroFields form, int printSelect)
//	throws IOException, DocumentException {
//		form.setField("KENSHIN" , kikanData.get("KIKAN_NAME"));
//		form.setField("KANA_NAME",kojinData.get("KANANAME"));
//		form.setField("ISI_NAME", ishiName);
//		form.setField("NENGAPPI", isVal(kensaNenList,0));
//		form.setField("kNENGAPPI",isVal(kensaNenList,0));
//		form.setField("zNENGAPPI",isVal(kensaNenList,1));
//		form.setField("zzNENGAPPI", isVal(kensaNenList,2));
//
//		// edit s.inoue 2010/01/05
//		// ページ設定（印刷方式変更対応）
//		int sabunPageNum = 1;
//		int sabunDivNum = 1;
//		if (printSelect == 2) {
//			sabunPageNum++;
//			sabunDivNum++;
//		}
//		form.setField("PAGE_KO",  Integer.toString(pageNum+sabunPageNum));
//		form.setField("PAGE_OYA", Integer.toString(div+sabunDivNum));
//	}
//
//	/**
//	 * 所見を改行を入れ項目名、値で成型する。
//	 *
//	 *
//	 * @param syokenList
//	 */
//	private String cntSyoken(List<TreeMap<String, String>> syokenList,int sStrNum) {
//
//		TreeMap<String, String> tmpSyoken = new TreeMap<String, String>();
//		StringBuffer buffer = new StringBuffer();
//		// edit h.yoshitama 2009/09/15
//		// add ver2 s.inoue 2009/07/15
//		//sStrNum=sStrNum-1;
////		for(int i = 0;i<4;i++){
//		for(int i = 0;i<3;i++){
//
//			try {
//				tmpSyoken = syokenList.get(sStrNum);
//				buffer.append(tmpSyoken.get(PrintDefine.COLUMN_NAME_KOUMOKU_NAME));
//				buffer.append(JApplication.LINE_SEPARATOR);
//				buffer.append(tmpSyoken.get(PrintDefine.COLUMN_NAME_KEKA_TI));
//				buffer.append(JApplication.LINE_SEPARATOR);
//				buffer.append(JApplication.LINE_SEPARATOR);
//				tmpSyoken.put(Integer.toString(i+1), buffer.toString());
//			} catch (IndexOutOfBoundsException e) {
//				// TODO 自動生成された catch ブロック
//				buffer.append("");
//			}
//			sStrNum++;
//		}
//		return buffer.toString();
//	}
//
//	/**
//	 * 結果値を出力する。
//	 *
//	 * @param allMap
//	 * @param startNum
//	 * @param form
//	 * @param i
//	 * @param dataType
//	 * @param valList
//	 * @param setForm
//	 * @param itemCodes
//	 * @return
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private String kekkaOut(Hashtable allMap, int startNum, AcroFields form,
//			int i, String dataType, String valList, String setForm, String jisiKbn, List itemCodes)
//	throws IOException, DocumentException {
//
//		//データタイプ取得
//		Hashtable taniMap = (Hashtable)allMap.get(dataType);
//		String strDataType = (String)taniMap.get(itemCodes.get(startNum));
//
//		// 実施区分取得
//		Hashtable HsjisiKbn = (Hashtable)allMap.get(jisiKbn);
//		String strjisiKbn = (String)HsjisiKbn.get(itemCodes.get(startNum));
//
//		// add s.inoue 2009/11/27
//		if (strjisiKbn == null || strDataType == null)
//			return "";
//
//		if (strjisiKbn.equals(""))
//			return "";
//
//		String strKekka = "";
//
//		//	結果取得
//		switch (Integer.valueOf(strjisiKbn)) {
//			case 0:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_0;
//				break;
//			case 1:
//				Hashtable kekkaMap = (Hashtable)allMap.get(valList);
//				strKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//				break;
//			case 2:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_2;
//				break;
//		}
//
//		//項目コード取得
//		String strCd =  (String)itemCodes.get(startNum);
//
//		if(strKekka==null||strKekka.equals("")){
//			return "";
//		}
//
//		// edit h.yoshitama 2009/09/11
//		/*
//		 * 結果値が数値かコード化を判別し、適切な結果値を表示する
//		 */
//		if (Integer.valueOf(strjisiKbn) == 1 ){
//
//			double kekatiDouble = 0;
//			/*
//			 * 判別 1=>数字, 2=>コード, 3=>文字列
//			 */
//
//			if (strDataType.equals("1") || strDataType.equals("2")) {
//
//				// Hashtable kekkaMap = (Hashtable)allMap.get(valList);
//				// strKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//
//				kekatiDouble = Double.parseDouble(strKekka);
//			}
//
//			if (strDataType.equals("2")) {
//				String code_name = addMedical.getCodeName(strCd, (int)kekatiDouble);
//
//				code_name = code_name.replaceFirst("\\d+:", "");
//
////				if (code_name != null && code_name.length() >= 5) {
////					code_name = code_name.substring(0, 5);
////				}
//
//				form.setField(setForm + Integer.toString(i+1), code_name);
//			} else {
////				if (strDataType.equals("3")) {
////					if (strKekka != null && strKekka.length() >= 5) {
////						strKekka = strKekka.substring(0, 5);
////					}
////				}
//
//				form.setField(setForm + Integer.toString(i+1),strKekka);
//			}
//		}else{
//
//			form.setField(setForm + Integer.toString(i+1),strKekka);
//		}
//
//		return strKekka;
//
//	}
//
//	// add s.inoue 2014/02/26
//	private String kekkaShokenOut(Hashtable allMap, int startNum, AcroFields form,
//			int i, String dataType, String valList, String setForm, String jisiKbn, List itemCodes)
//	throws IOException, DocumentException {
//		
//		//データタイプ取得
//		Hashtable taniMap = (Hashtable)allMap.get(dataType);
//		String strDataType = (String)taniMap.get(itemCodes.get(startNum));
//
//		// 実施区分取得
//		Hashtable HsjisiKbn = (Hashtable)allMap.get(jisiKbn);
//		String strjisiKbn = (String)HsjisiKbn.get(itemCodes.get(startNum));
//
//		if (strjisiKbn == null || strDataType == null)
//			return "";
//		if (strjisiKbn.equals(""))
//			return "";
//
//		String strKekka = "";
//
//		//	結果取得
//		switch (Integer.valueOf(strjisiKbn)) {
//			case 0:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_0;
//				break;
//			case 1:
//				Hashtable kekkaMap = (Hashtable)allMap.get(valList);
//				strKekka = (String)kekkaMap.get(itemCodes.get(startNum));
//				break;
//			case 2:
//				strKekka = KEKKA_OUTPUT_JISI_KBN_2;
//				break;
//		}
//
//		//項目コード取得
//		String strCd =  (String)itemCodes.get(startNum);
//
//		if(strKekka==null||strKekka.equals("")){
//			return "";
//		}
//
//		/*
//		 * 結果値が数値かコード化を判別し、適切な結果値を表示する
//		 */
//		if (Integer.valueOf(strjisiKbn) == 1 ){
//
//			double kekatiDouble = 0;
//			/*
//			 * 判別 1=>数字, 2=>コード, 3=>文字列
//			 */
//
//			if (strDataType.equals("1") || strDataType.equals("2")) {
//				kekatiDouble = Double.parseDouble(strKekka);
//			}
//
//			if (strDataType.equals("2")) {
//				String code_name = addMedical.getCodeName(strCd, (int)kekatiDouble);
//
//				code_name = code_name.replaceFirst("\\d+:", "");
//
////				if (code_name != null && code_name.length() >= 5) {
////					code_name = code_name.substring(0, 5);
////				}
//
//				form.setField(setForm + Integer.toString(i+1), code_name);
//			} else {
////				if (strDataType.equals("3")) {
////					if (strKekka != null && strKekka.length() >= 5) {
////						strKekka = strKekka.substring(0, 5);
////					}
////				}
//
//				form.setField(setForm + Integer.toString(i+1),strKekka);
//			}
//		}else{
//
//			form.setField(setForm + Integer.toString(i+1),strKekka);
//		}
//
//		return strKekka;
//
//	}
//	
//	/**
//	 * HLをデータタイプで判定して印字
//	 *
//	 * @param allMap
//	 * @param startNum
//	 * @param form
//	 * @param i
//	 * @param strKekka
//	 * @param strDataTypeList
//	 * @param strHlList
//	 * @param strHl
//	 * @param itemCodes
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private void hlOut(Hashtable allMap, int startNum, AcroFields form,
//			int i, String strKekka, String strDataTypeList, String strHlList, String strHl, String strJisiKbn,List itemCodes)
//	throws IOException, DocumentException {
//		try {
//
//			//データタイプ取得
//			Hashtable taniMap = (Hashtable)allMap.get(strDataTypeList);
//			String strDataType = (String)taniMap.get(itemCodes.get(startNum));
//
//			// 基準値上限、下限取得
//			Hashtable uppLimit = (Hashtable)allMap.get(BASEVALUPPLIMIT);
//			String struppLimit = (String)uppLimit.get(itemCodes.get(startNum));
//
//			Hashtable dwnLimt = (Hashtable)allMap.get(BASEVALDWNLIMIT);
//			String strdwnLimit = (String)dwnLimt.get(itemCodes.get(startNum));
//
//			int dataType = Integer.parseInt(strDataType);
//
//			// 実施区分取得
//			Hashtable HsjisiKbn = (Hashtable)allMap.get(strJisiKbn);
//			String strjisiKbn = (String)HsjisiKbn.get(itemCodes.get(startNum));
//
//			Integer jisiKbn= Integer.valueOf(strjisiKbn);
//
//			if(!strKekka.isEmpty()){
//				/*
//				 * 判別 1=>数値, 2=>コード, 3=>文字列
//				 */
//				/* コード値 */
//				if (dataType == 2) {
//					/* 数値または文字列 */
//				} else {
//					/* 数値 */
//					// 実施のみ(結果値がある為)
//					if (dataType == 1 && jisiKbn == 1) {
//						if (strKekka.startsWith(".")) {
//							strKekka = "0" + strKekka;
//						}
//
//						/* HL判定 */
//						String hl = "";
//						Hashtable hlMap = (Hashtable)allMap.get(strHlList);
//						String hlCode = (String)hlMap.get(itemCodes.get(startNum));
//						hl = HLHantei(strdwnLimit,struppLimit,strKekka);
//
//						form.setField(strHl + Integer.toString(i + 1), hl);
//					}
//				}
//			}
//		} catch (NumberFormatException ee) {
//		}
//	}
//
//	private String replaseNenGaPii(List kensaNenList,int i ) {
//		String kensaNengappi;
//		try {
//			kensaNengappi = (String)kensaNenList.get(i);
//			kensaNengappi = kensaNengappi.replaceAll("年", "");
//			kensaNengappi = kensaNengappi.replaceAll("月", "");
//			kensaNengappi = kensaNengappi.replaceAll("日", "");
//		} catch (RuntimeException e) {
//			return "";
//		}
//		return kensaNengappi;
//	}
//
//	private String isVal(List valList,int i ) {
//		String strVal;
//		try {
//			strVal = (String)valList.get(i);
//		} catch (RuntimeException e) {
//			return "";
//		}
//		return strVal;
//	}
//
//	private boolean isVal(String val ) {
//		try {
//			if(val.equals("")){
//				return false;
//			}
//			if(val==null){
//				return false;
//			}
//		} catch (RuntimeException e) {
//			return false;
//		}
//		return true;
//	}
//}
