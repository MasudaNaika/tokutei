// edit n.ohkubo 2014/08/21　削除　v2.1.1のjarに無かったのでコメントアウト

//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.TreeMap;
//
//import jp.or.med.orca.jma_tokutei.common.app.JConstantString;
//import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
//import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.AddMedical;
//
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.AcroFields;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfStamper;
//
//public class PrintShokenList {
//
//	String pdfPathList = "";
//
//	AddMedical addM = new AddMedical();
//	Hashtable<String, String> resultItem = new Hashtable<String, String>();
//	Hashtable<String, String> zResultItem = new Hashtable<String, String>();
//	Hashtable<String, String> zzResultItem = new Hashtable<String, String>();
//
//	public static final String WORK_SHOKEN_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"shokenList.pdf";
//
//	/**
//	 * 所見リストのページ作成
//	 *
//	 * @param kensaNenList
//	 * @param kojinData
//	 * @return pdfPathList
//	 */
//	public String createChkList(List kensaNenList, Hashtable<String, String> kojinData,
//			Hashtable<String, String> kikanData, String ishiName, int div, int printSelect){
//
//		String tmpPath = WORK_SHOKEN_PDF;
//		String kensaNengappi = replaseNenGaPii(kensaNenList,0);
//		String zKensaNengappi = replaseNenGaPii(kensaNenList,1);
//		String zzKensaNengappi = replaseNenGaPii(kensaNenList,2);
//
//		String[] params = new String[1];
//		params[0] = kojinData.get("UKETUKE_ID");
//
//		// edit ver2 s.inoue 2009/06/23
//		List<TreeMap<String, String>> tmpChkListKeys = addM.getKihonChkKeys(params,kensaNengappi,kensaNenList);
//		//今回
////		if(!tmpChkListKeys.isEmpty()){
////
////			TreeMap<String, String> resultItemKeys = new TreeMap<String, String>();
////			for(int i = 0;i<tmpChkListKeys.size();i++){
////				resultItemKeys = tmpChkListKeys.get(i);
////			}
////		}
//
//		boolean blnYear = FiscalYearUtil.getJugeThisYear(kensaNengappi);
//
//		// 今年度過去3年分
//		List<TreeMap<String, String>> tmpChkList = null;
//		List<TreeMap<String, String>> zTmpChkList = null;
//		List<TreeMap<String, String>> zzTmpChkList = null;
//
//		if(blnYear && kensaNenList.size() > 1){
//			if (tmpChkListKeys.size() > 0){
//				tmpChkList = addM.getKihonChk(kojinData, kensaNengappi, Arrays.asList(JConstantString.codesSeikatuKinou),tmpChkListKeys.get(0));
//			}
//			if  (tmpChkListKeys.size() > 1){
//				zTmpChkList = addM.getKihonChk(kojinData, zKensaNengappi, Arrays.asList(JConstantString.codesSeikatuKinou),tmpChkListKeys.get(1));
//			}
//			if  (tmpChkListKeys.size() > 2){
//				zzTmpChkList = addM.getKihonChk(kojinData, zzKensaNengappi, Arrays.asList(JConstantString.codesSeikatuKinou),tmpChkListKeys.get(2));
//			}
//			createPdf(kensaNenList,tmpPath,tmpChkList,zTmpChkList,zzTmpChkList,kikanData,kojinData,ishiName,div,printSelect);
//		}else{
//			if (tmpChkListKeys.size() > 0){
//				tmpChkList = addM.getKihonChk(kojinData, kensaNengappi, Arrays.asList(JConstantString.codesSeikatuKinou),tmpChkListKeys.get(0));
//			}
//			createPdf(kensaNenList,tmpPath,tmpChkList,null,null,kikanData,kojinData,ishiName,div,printSelect);
//		}
//
//		return pdfPathList;
//	}
//
//	/**
//	 * PDFを実際に印字する。
//	 *
//	 */
//	private void createPdf(List kensaNenList, String tmpPath,
//			List<TreeMap<String, String>> tmpChkList,
//			List<TreeMap<String, String>> zTmpChkList,
//			List<TreeMap<String, String>> zzTmpChkList,
//			Hashtable<String, String> kikanData,
//			Hashtable<String, String> kojinData,
//			String ishiName,
//			int cnt,
//			int printSelect
//	) {
//
//		String kensaNengappi;
//
//		TreeMap<String, String> resultItem = new TreeMap<String, String>();
//		TreeMap<String, String> zResultItem = new TreeMap<String, String>();
//		TreeMap<String, String> zzResultItem = new TreeMap<String, String>();
//
//		Hashtable<String, String> valuesItem = new Hashtable<String, String>();
//		Hashtable<String, String> zValuesItem = new Hashtable<String, String>();
//		Hashtable<String, String> zzValuesItem = new Hashtable<String, String>();
//
//		Hashtable<String, String> typeItem = new Hashtable<String, String>();
//
//		try {
//			PdfReader reader = new PdfReader(WORK_SHOKEN_PDF);
//
//			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tmpPath+Integer.toString(cnt)));
//			AcroFields form = stamp.getAcroFields();
//
//			form.setField("KENSHIN" , kikanData.get("KIKAN_NAME"));
//			form.setField("KANA_NAME",kojinData.get("KANANAME"));
//			form.setField("ISI_NAME", ishiName);
//			form.setField("NENGAPPI", isVal(kensaNenList,0));
//			form.setField("kNENGAPPI",isVal(kensaNenList,0));
//			form.setField("zNENGAPPI",isVal(kensaNenList,1));
//			form.setField("zzNENGAPPI", isVal(kensaNenList,2));
//
//			// edit s.inoue 2010/01/05
//			// ページ設定（印刷方式変更対応）
//			int sabunPageNum = cnt+2;
//			int sabunDivNum = cnt+2;
//			if (printSelect == 2) {
//				sabunPageNum++;
//				sabunDivNum++;
//			}
//			form.setField("PAGE_KO",  Integer.toString(sabunPageNum));
//			form.setField("PAGE_OYA", Integer.toString(sabunDivNum));
//
//			//今回
//			if(!tmpChkList.isEmpty()){
//				for(int i = 0;i<tmpChkList.size();i++){
//					resultItem.clear();
//					resultItem = tmpChkList.get(i);
//					valuesItem.put(resultItem.get(PrintDefine.CODE_KOUMOKU),
//							resultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI));
//					typeItem.put(resultItem.get(PrintDefine.CODE_KOUMOKU),
//							resultItem.get(PrintDefine.DATA_TYPE));
//				}
//				setYesNo(valuesItem, form, "KEKA", "Y_N" );
//			}
//
//			//配点
//			int num1 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_1)));
//			int num2 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_2)));
//			int num3 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_3)));
//			int num4 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_4)));
//			int num5 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_5)));
//			int num6 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_6)));
//			int num7 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_7)));
//			int num8 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_8)));
//			int num9 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_9)));
//			int num10 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_10)));
//			int num11 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_11)));
//			int num12 = 0;
//			/* MOD 2008/07/30 yabu */
//			/* --------------------------------------------------- */
////			if(Double.valueOf(isNum(valuesItem.get(PrintDefine.KINOU_12)))<18.5){
////				num12 = 1;
////			}else{
////				num12 = 0;
////			}
//			/* MOD 2008/07/31 yabu */
//			/* --------------------------------------------------- */
//			if(Double.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_12)))<18.5
//					&& Double.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_12)))>0){
//				num12 = 1;
////			}else{
////				num12 = 0;
////			}
//			}else if(Double.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_12)))>=18.5){
//				num12 = 0;
//			}
//
//			int num13 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_13)));
//			int num14 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_14)));
//			int num15 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_15)));
//			int num16 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_16)));
//			int num17 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_17)));
//			int num18 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_18)));
//			int num19 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_19)));
//			int num20 = Integer.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_20)));
//
//			int sogo = num1+num2+num3+num4+num5+num6+num7+num8+num9+num10+num11
//			+num12+num13+num14+num15+num16+num17+num18+num19+num20;
//			// add ver2 s.inoue 2009/06/25
//			if (resultItem.size() > 0){
//				form.setField("SAITEN_1", Integer.toString(num6+num7+num8+num9+num10));
//				form.setField("SAITEN_2", Integer.toString(num11+num12));
//				form.setField("SAITEN_3", Integer.toString(num13+num14+num15));
//				form.setField("SAITEN_SOGO", Integer.toString(sogo));
//			}
//			//生活機能評価の結果１
//			/* MOD 2008/07/30 yabu */
//			/* --------------------------------------------------- */
////			if(!typeItem.get(PrintDefine.SEIKATU_HYOKA_1).isEmpty()){
////			if(typeItem.get(PrintDefine.SEIKATU_HYOKA_1)!=null){
////				if(typeItem.get(PrintDefine.SEIKATU_HYOKA_1).equals("1")){
////					form.setField("SEIKATU_1_1", "○");
////				}else if(typeItem.get(PrintDefine.SEIKATU_HYOKA_1).equals("2")){
////					form.setField("SEIKATU_1_2", "○");
////				}else if(typeItem.get(PrintDefine.SEIKATU_HYOKA_1).equals("3")){
////					form.setField("SEIKATU_1_3", "○");
////				}
////			}
//			if(typeItem.get(JConstantString.SEIKATU_HYOKA_1)!=null){
//				if(valuesItem.get(JConstantString.SEIKATU_HYOKA_1).equals("1")){
//					form.setField("SEIKATU_1_1", "○");
//				}else if(valuesItem.get(JConstantString.SEIKATU_HYOKA_1).equals("2")){
//					form.setField("SEIKATU_1_2", "○");
//				}else if(valuesItem.get(JConstantString.SEIKATU_HYOKA_1).equals("3")){
//					form.setField("SEIKATU_1_3", "○");
//				}
//			}
//
//			//生活機能評価の結果２
//			/* MOD 2008/07/30 yabu */
//			/* --------------------------------------------------- */
////			if(!valuesItem.get(JConstantString.SEIKATU_HYOKA_2).isEmpty()){
//			if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2)!=null){
//
//				if(typeItem.get(JConstantString.SEIKATU_HYOKA_1)!=null){
//					if(valuesItem.get(JConstantString.SEIKATU_HYOKA_1).equals("2")){
//
//						if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2).equals("1")){
//							form.setField("SEIKATU_2_1", "○");
//						}else if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2).equals("2")){
//							form.setField("SEIKATU_2_2", "○");
//						}else if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2).equals("3")){
//							form.setField("SEIKATU_2_3", "○");
//						}else if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2).equals("4")){
//							form.setField("SEIKATU_2_4", "○");
//						}else if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2).equals("5")){
//							form.setField("SEIKATU_2_5", "○");
//						}
//					}
//				}
//			}
//
//			//生活機能評価の結果３
//			/* MOD 2008/07/30 yabu */
//			/* --------------------------------------------------- */
////			if(valuesItem.get(PrintDefine.SEIKATU_HYOKA_2).equals("5")){
//			// edit s.inoue 20080911
//			if(isVal(valuesItem.get(JConstantString.SEIKATU_HYOKA_1))){
//				if(valuesItem.get(JConstantString.SEIKATU_HYOKA_1).equals("2")){
//				if(isVal(valuesItem.get(JConstantString.SEIKATU_HYOKA_2))){
//					if(valuesItem.get(JConstantString.SEIKATU_HYOKA_2).equals("5")){
//						form.setField("SEIKATU_KEKA_3",valuesItem.get(JConstantString.SEIKATU_HYOKA_3));
//					}
//				}
//				}
//			}
//			//医師の判断
//			form.setField("ISHI_HANDAN",valuesItem.get(JConstantString.ISHINO_HANDAN));
//
//
//			//前回
//			// edit ver2 s.inoue 2009/06/23
//			if(zTmpChkList != null){
//				if(!zTmpChkList.isEmpty()){
//					for(int i = 0;i<zTmpChkList.size();i++){
//						zResultItem.clear();
//						zResultItem = zTmpChkList.get(i);
//						zValuesItem.put(zResultItem.get(PrintDefine.CODE_KOUMOKU),
//								zResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI));
//					}
//					setYesNo(zValuesItem, form, "zKEKA", "zY_N" );
//				}
//			}
//			//前々回
//			// edit ver2 s.inoue 2009/06/23
//			if(zzTmpChkList != null){
//				if(!zzTmpChkList.isEmpty()){
//					for(int i = 0;i<zzTmpChkList.size();i++){
//						zzResultItem.clear();
//						zzResultItem = zzTmpChkList.get(i);
//						zzValuesItem.put(zzResultItem.get(PrintDefine.CODE_KOUMOKU),
//								zzResultItem.get(PrintDefine.COLUMN_NAME_KEKA_TI));
//					}
//					setYesNo(zzValuesItem, form, "zzKEKA", "zzY_N" );
//				}
//			}
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
//		pdfPathList=tmpPath+Integer.toString(cnt);
//	}
//
//	/**
//	 * １なら「はい」、０なら「いいえ」を返す
//	 *
//	 * @param values
//	 * @return
//	 */
//	private String isOne(String values) {
//		String rtnVal = "";
//		try {
//			if(values.equals("1")){
//				rtnVal = PrintDefine.ANSWER_YES;;
//			}else if(values.equals("0")){
//				rtnVal = PrintDefine.ANSWER_NO;;
//			}
//		} catch (RuntimeException e) {
//			return "";
//		}
//		return rtnVal;
//	}
//
//	/**
//	 * ０なら「はい」、１なら「いいえ」を返す
//	 *
//	 * @param values
//	 * @return
//	 */
//	private String isZero(String values) {
//		String rtnVal = "";
//		try {
//			if(values.equals("0")){
//				rtnVal = PrintDefine.ANSWER_YES;;
//			}else if(values.equals("1")){
//				rtnVal = PrintDefine.ANSWER_NO;;
//			}
//		} catch (RuntimeException e) {
//			return "";
//		}
//		return rtnVal;
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
//
//	private String isNum(String val ) {
//		try {
//			if(val.equals("")){
//				return "0";
//			}
//			if(val==null){
//				return "0";
//			}
//		} catch (RuntimeException e) {
//			return "0";
//		}
//		return val;
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
//			return "19010101";
//		}
//		return kensaNengappi;
//	}
//
//	/**
//	 * 問診の結果印字
//	 *
//	 * @param valuesItem
//	 * @param form
//	 * @param keka
//	 * @param yn
//	 * @throws IOException
//	 * @throws DocumentException
//	 */
//	private void setYesNo(Hashtable<String, String> valuesItem, AcroFields form,
//			String keka,String yn)
//
//	throws IOException, DocumentException {
//
//		// edit ver2 s.inoue 2009/06/23
//		int num12=0;
//		boolean blnnum12 = false;
//
//		/* MOD 2008/07/30 yabu */
//		/* --------------------------------------------------- */
////		if(Double.valueOf(isNum(valuesItem.get(PrintDefine.KINOU_12)))<18.5	){
//		if(Double.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_12)))<18.5
//				&& Double.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_12)))>0){
//			num12 = 1;
//			blnnum12 = true;
////		}else{
////			num12 = 0;
////		}
//		}else if(Double.valueOf(isNum(valuesItem.get(JConstantString.MONSHIN_KINOU_12)))>=18.5){
//			num12 = 0;
//			blnnum12 = true;
//		}
//
//		//結果値
//		form.setField(keka+"_1",valuesItem.get(JConstantString.MONSHIN_KINOU_1));
//		form.setField(keka+"_2",valuesItem.get(JConstantString.MONSHIN_KINOU_2));
//		form.setField(keka+"_3",valuesItem.get(JConstantString.MONSHIN_KINOU_3));
//		form.setField(keka+"_4",valuesItem.get(JConstantString.MONSHIN_KINOU_4));
//		form.setField(keka+"_5",valuesItem.get(JConstantString.MONSHIN_KINOU_5));
//		form.setField(keka+"_6",valuesItem.get(JConstantString.MONSHIN_KINOU_6));
//		form.setField(keka+"_7",valuesItem.get(JConstantString.MONSHIN_KINOU_7));
//		form.setField(keka+"_8",valuesItem.get(JConstantString.MONSHIN_KINOU_8));
//		form.setField(keka+"_9",valuesItem.get(JConstantString.MONSHIN_KINOU_9));
//		form.setField(keka+"_10",valuesItem.get(JConstantString.MONSHIN_KINOU_10));
//		form.setField(keka+"_11",valuesItem.get(JConstantString.MONSHIN_KINOU_11));
//
//		/* MOD 2008/07/30 yabu */
//		/* --------------------------------------------------- */
////		form.setField(keka+"_12",Integer.toString(num12));
////		if(num12>0){
//			if (blnnum12){
//				form.setField(keka+"_12",Integer.toString(num12));
//			}
////		}
//
//		form.setField(keka+"_13",valuesItem.get(JConstantString.MONSHIN_KINOU_13));
//		form.setField(keka+"_14",valuesItem.get(JConstantString.MONSHIN_KINOU_14));
//		form.setField(keka+"_15",valuesItem.get(JConstantString.MONSHIN_KINOU_15));
//		form.setField(keka+"_16",valuesItem.get(JConstantString.MONSHIN_KINOU_16));
//		form.setField(keka+"_17",valuesItem.get(JConstantString.MONSHIN_KINOU_17));
//		form.setField(keka+"_18",valuesItem.get(JConstantString.MONSHIN_KINOU_18));
//		form.setField(keka+"_19",valuesItem.get(JConstantString.MONSHIN_KINOU_19));
//		form.setField(keka+"_20",valuesItem.get(JConstantString.MONSHIN_KINOU_20));
//		form.setField(keka+"_21",valuesItem.get(JConstantString.MONSHIN_KINOU_21));
//		form.setField(keka+"_22",valuesItem.get(JConstantString.MONSHIN_KINOU_22));
//		form.setField(keka+"_23",valuesItem.get(JConstantString.MONSHIN_KINOU_23));
//		form.setField(keka+"_24",valuesItem.get(JConstantString.MONSHIN_KINOU_24));
//		form.setField(keka+"_25",valuesItem.get(JConstantString.MONSHIN_KINOU_25));
//
//		//はい、いいえ
//		form.setField(yn+"_1",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_1)));
//		form.setField(yn+"_2",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_2)));
//		form.setField(yn+"_3",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_3)));
//		form.setField(yn+"_4",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_4)));
//		form.setField(yn+"_5",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_5)));
//		// edit s.inoue 20080910
//		form.setField(yn+"_6",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_6)));
//		form.setField(yn+"_7",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_7)));
//		form.setField(yn+"_8",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_8)));
//
//		form.setField(yn+"_9",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_9)));
//		form.setField(yn+"_10",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_10)));
//		form.setField(yn+"_11",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_11)));
//
//		form.setField(yn+"_12",valuesItem.get(JConstantString.MONSHIN_KINOU_12));
//
//		form.setField(yn+"_13",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_13)));
//		form.setField(yn+"_14",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_14)));
//		form.setField(yn+"_15",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_15)));
//		form.setField(yn+"_16",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_16)));
//		form.setField(yn+"_17",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_17)));
//		form.setField(yn+"_18",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_18)));
//		form.setField(yn+"_19",isZero(valuesItem.get(JConstantString.MONSHIN_KINOU_19)));
//		form.setField(yn+"_20",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_20)));
//		form.setField(yn+"_21",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_21)));
//		form.setField(yn+"_22",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_22)));
//		form.setField(yn+"_23",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_23)));
//		form.setField(yn+"_24",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_24)));
//		form.setField(yn+"_25",isOne(valuesItem.get(JConstantString.MONSHIN_KINOU_25)));
//	}
//
//}
