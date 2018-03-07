package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jp.or.med.orca.jma_tokutei.common.app.JConstantString;

public class PrintDefine {

	public static final String PAGE_ITEM_1 = "1/1";
	public static final String PAGE_ITEM_LEFT = "[";
	public static final String PAGE_ITEM_RIGHT = "ページ目] ）";
	public static final String GENERAL_COMMENT = "医師の判断：";
	public static final String VALUES_NASHI = "なし";
	public static final String VALUES_ARI = "あり";
	public static final String ANSWER_YES = "はい";
	public static final String ANSWER_NO = "いいえ";
	public static final String ANEMIA = "貧血について既往歴あり";
	public static final String LIVER_FAILURE = "腎不全・人工透析について既往歴あり";
	public static final String CARDIOVASCULAR_SYSTEM = "心血管について既往歴あり";
	public static final String CEREBRAL_BLOOD_VESSEL = "脳血管について既往歴あり";
	// add h.yoshitama 2009/11/24
	public static final String NO_ANEMIA = "貧血について既往歴なし";
	public static final String NO_LIVER_FAILURE = "腎不全・人工透析について既往歴なし";
	public static final String NO_CARDIOVASCULAR_SYSTEM = "心血管について既往歴なし";
	public static final String NO_CEREBRAL_BLOOD_VESSEL = "脳血管について既往歴なし";
	public static final String NO_TOKKIJIKO = "特記すべきことなし";
	public static final String NO_HUKUYAKU = "服薬なし";

	public static final String WORK_PDF_INKEKKA2PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inkekka2page.pdf";
	public static final String WORK_PDF_INKEKKA3PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inkekka3page.pdf";
	public static final String WORK_PDF_INKEKKA4PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inkekka4page.pdf";
	public static final String WORK_PDF_TMP_DUMMY_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"dummy.pdf";
	// edit s.inoue 2010/05/10
	// public static final String WORK_PDF_DUMMY2_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inKekkaTemplate_2.pdf";
	public static final String WORK_PDF_DUMMY2_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"inKekkaTemplate_2.pdf";

	public static final String WORK_PDF_TMP_INKEKKA2PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka2page.pdf";
	public static final String WORK_PDF_TMP_INKEKKA3PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka3page.pdf";
	public static final String WORK_PDF_TMP_INKEKKA4PAGE_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"tmp"+File.separator+"inkekka4page.pdf";
	// edit s.inoue 2009/09/28
	public static final String WORK_GEKEI_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"gekei.pdf";
	public static final String WORK_SHUKEI_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"shukeihyo.pdf";
	public static final String WORK_RYOSYU_PDF = "."+File.separator+"work"+File.separator+"PDF"+File.separator+"ryosyusyo.pdf";

	public static final String OUT_GEKEI_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_getkei.pdf";
	public static final String OUT_SHUKEI_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_shukeihyo.pdf";
	public static final String OUT_RYOSYUSYO_PDF = "."+File.separator+"Data"+File.separator+"PDF"+File.separator+"out_ryosyusyo.pdf";

	public static final String CODE_GANTEI_SONOTA_SYOKEN = "9E100160900000049";
	public static final String CODE_SINDENZU_SYOKEN_UMU = "9A110160700000011";
	public static final String CODE_SINDENZU_SYOKEN = "9A110160800000049";
	public static final String CODE_NYOTANPAKU_MOKUSI = "1A010000000190111";
	public static final String CODE_NYOTANPAKU_KIKAI = "1A010000000191111";
	public static final String CODE_WEIGHT = "9N006000000000001";
	public static final String CODE_HIGHT = "9N001000000000001";
	public static final String CODE_HEMATCLIT = "2A040000001930102";
	public static final String CODE_KESIKISOSU = "2A030000001930101";
	public static final String CODE_SEKEKYUSU = "2A020000001930101";

	// edit s.inoue 2012/09/04
	public static final String CODE_HBA1C_SONOTA_JDS = "3D045000001999902";
	public static final String CODE_HBA1C_COUSOHO_JDS = "3D045000001927102";
	public static final String CODE_HBA1C_HPLC_JDS = "3D045000001920402";
	public static final String CODE_HBA1C_RATEX_JDS = "3D045000001906202";
	public static String CODE_HBA1C_SONOTA = CODE_HBA1C_SONOTA_JDS;
	public static String CODE_HBA1C_COUSOHO = CODE_HBA1C_COUSOHO_JDS;
	public static String CODE_HBA1C_HPLC = CODE_HBA1C_HPLC_JDS;
	public static String CODE_HBA1C_RATEX = CODE_HBA1C_RATEX_JDS;
	public static final String CODE_HBA1C_SONOTA_NGSP = "3D046000001999902";
	public static final String CODE_HBA1C_COUSOHO_NGSP = "3D046000001927102";
	public static final String CODE_HBA1C_HPLC_NGSP = "3D046000001920402";
	public static final String CODE_HBA1C_RATEX_NGSP = "3D046000001906202";

	public static final String CODE_KUHUKUZI_KETO_SONOTA = "3D010000001999901";
	public static final String CODE_KUHUKUZI_KETO_SIGAI = "3D010000001927201";
	public static final String CODE_KUHUKUZI_KETO_KASI = "3D010000002227101";
	public static final String CODE_KUHUKUZI_KETO_DENNI = "3D010000001926101";
	public static final String CODE_GAMMA_GTP_SONOTA = "3B090000002399901";
	public static final String CODE_GAMMA_GTP_KASI = "3B090000002327101";
	public static final String CODE_GPT_SONOTA = "3B045000002399901";
	public static final String CODE_GPT_SIGAI = "3B045000002327201";
	public static final String CODE_GOT_SONOTA = "3B035000002399901";
	public static final String CODE_GOT_SIGAI = "3B035000002327201";
	public static final String CODE_LDL_SONOTA = "3F077000002399901";
	public static final String CODE_LDL_SIGAI = "3F077000002327201";
	public static final String CODE_LDL_KASI = "3F077000002327101";
	public static final String CODE_HDL_SONOTA = "3F070000002399901";
	public static final String CODE_HDL_SIGAI = "3F070000002327201";
	public static final String CODE_HDL_KASI = "3F070000002327101";
	public static final String CODE_CHUSEISIBOU_SONOTA = "3F015000002399901";
	public static final String CODE_CHUSEISIBOU_SIGAI = "3F015000002327201";
	public static final String CODE_CHUSEISIBOU_KASI = "3F015000002327101";
	public static final String CODE_KAKUCHOKI_KETUATU_SONOTA = "9A765000000000001";
	public static final String CODE_KAKUCHOKI_KETUATU_2 = "9A762000000000001";
	public static final String CODE_KAKUCHOKI_KETUATU_1 = "9A761000000000001";
	public static final String CODE_SYUSYUKUKI_KETUATU_SONOTA = "9A755000000000001";
	public static final String CODE_SYUSYUKUKI_KETUATU_2 = "9A752000000000001";
	public static final String CODE_SYUSYUKUKI_KETUATU_1 = "9A751000000000001";
	public static final String CODE_BMI = "9N011000000000001";
	public static final String CODE_INSYU = "9N791000000000011";
	public static final String CODE_KITUENREKI = "9N736000000000011";
	public static final String CODE_TAKAKUSYOJOU = "9N066160800000049";
	public static final String CODE_ZIKAKUSYOJOU = "9N061160800000049";
	public static final String CODE_GUTAITEKINA_KIOUREKI = "9N056160400000049";
	public static final String CODE_KIOREKI = "9N056000000000011";
	public static final String CODE_HINKETU = "9N731000000000011";
	public static final String CODE_KIOUREKI3_ZINHUZEN_ZINKOTOSEKI = "9N726000000000011";
	public static final String CODE_KIOUREKI2_SINKEKAN = "9N721000000000011";
	public static final String CODE_KIOUREKI1_NOKEKAN = "9N716000000000011";
	// add h.yoshitama 2009/11/24
	public static final String CODE_TAKAKU_UMU = "9N066000000000011";
	public static final String CODE_ZIKAKU_UMU = "9N061000000000011";

	/* テーブル項目 */
	public static final String COLUMN_NAME_KOUMOKU_NAME = "KOUMOKU_NAME";
	public static final String COLUMN_NAME_KENSA_HOUHOU = "KENSA_HOUHOU";
	public static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
	public static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
	public static final String COLUMN_NAME_DS_JYOUGEN = "DS_JYOUGEN";
	public static final String COLUMN_NAME_DS_KAGEN = "DS_KAGEN";
	public static final String COLUMN_NAME_JS_JYOUGEN = "JS_JYOUGEN";
	public static final String COLUMN_NAME_JS_KAGEN = "JS_KAGEN";
	public static final String COLUMN_NAME_TANI = "TANI";
	public static final String COLUMN_NAME_KENSA_NENGAPI = "KENSA_NENGAPI";
	public static final String CODE_KOUMOKU = "KOUMOKU_CD";
	public static final String COLUMN_MAX_BYTE_LENGTH ="MAX_BYTE_LENGTH";
	public static final String STR_H_L = "H_L";
	public static final String DATA_TYPE = "DATA_TYPE";

	// add s.inoue 2009/09/22
	// 集計テーブル項目(T_SYUKEI)
	public static final String ROW_ID = "ROW_ID";
	public static final String HKNJANUM = "HKNJANUM";
	public static final String KENSA_NENGAPI = "KENSA_NENGAPI";
	public static final String KENSA_JISI_KUBUN = "KENSA_JISI_KUBUN";
	public static final String KENSA_JISI_SOUSU = "KENSA_JISI_SOUSU";
	public static final String KENSA_TANKA_SOUKEI = "KENSA_TANKA_SOUKEI";
	public static final String KENSA_MADO_SOUKEI = "KENSA_MADO_SOUKEI";
	public static final String KENSA_SEIKYU_SOUKEI = "KENSA_SEIKYU_SOUKEI";
	public static final String KENSA_SONOTA_SOUKEI = "KENSA_SONOTA_SOUKEI";
	public static final String UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

	public static final String CODE_KENKOUSINDAN_WO_JISISITA_ISI_NO_SHIMEI = "9N516000000000049";
	public static final String CODE_ISHINO_HANDAN = "9N511000000000049";
	public static final String CODE_KEITH_WAGNER = "9E100166000000011";//眼底検査（キースワグナー分類）
	public static final String CODE_SHEIE_H = "9E100166100000011";//眼底検査（シェイエ分類：Ｈ）
	public static final String CODE_SHEIE_S = "9E100166200000011";//眼底検査（シェイエ分類：Ｓ）
	public static final String CODE_SCOTT = "9E100166300000011";//眼底検査（SCOTT分類）
	public static final String CODE_COLLECTING_BLOOD_TIME = "9N141000000000011";//採血時間（食後）
	public static final String COLESTEROLL_DOWN = "9N711000000000011";
	public static final String INSURIN = "9N706000000000011";
	public static final String FUKUTO_BLODD = "9N701000000000011";
	public static final String TOU_MOKUSHI = "1A020000000190111";
	public static final String TOU_KIKAI = "1A020000000191111";
	public static final String VALUE_KEITH_WAGNER = "キースワグナー分類：";//眼底検査（キースワグナー分類）
	public static final String VALUE_SHEIE_H = "シェイエ分類Ｈ：";//眼底検査（シェイエ分類：Ｈ）
	public static final String VALUE_SHEIE_S = "シェイエ分類Ｓ：";//眼底検査（シェイエ分類：Ｓ）
	public static final String VALUE_SCOTT = "SCOTT分類：";//眼底検査（SCOTT分類）
	public static final String  asuta = "*";
	public static final String  ONE = "1"; //1
	public static final String  TWO = "2"; //2
	public static final String NAME_SHISHITU = "脂質";
	public static final String NAME_KETTOU = "血糖";
	public static final String NAME_KETUATU = "血圧";

	/*      検査分野別判定                                                  **/
	public static final String KETUATU_SEIJYO = "血圧は正常です。";
	public static final String KETUATU_IJYO = "血圧に異常が認められます。";
	public static final String KETTYU_SEIJYO = "血中脂質検査は正常です。";
	public static final String KETTYU_IJYO = "血中脂質検査に異常が認められます。";
	public static final String KANKINOU_SEIJYO = "肝機能検査は正常です。";
	public static final String KANKINOU_IJYO = "肝機能検査に異常が認められます。";
	public static final String KETTOU_SEIJYO = "血糖検査は正常です。";
	public static final String KETTOU_IJYO = "血糖検査に異常が認められます。";
	public static final String NYOU_SEIJYO = "尿検査は正常です。";
	public static final String NYOU_IJYO_YOSEI = "尿検査に異常が認められます。尿糖が陽性です。";
	public static final String NYOU_IJYO_TANPAKU = "尿検査に異常が認められます。尿蛋白が陽性です。";
	public static final String NYOU_IJYO = "尿検査に異常が認められます。";
	public static final String SYOKEN_NASHI = "所見なし";
	public static final String IJYO_NASHI = "異常なし";

	/*      非特定検診                                                  **/
	public static final String NYOTINSA_UMU = "1A105160700166211";	//尿沈渣（所見の有無）
	public static final String NYOTINSA_VAL = "1A105160800166249";	//尿沈渣（所見）
	public static final String SHINDENZU_UMU = "9A110160700000011";	//心電図（所見の有無）
	public static final String SHINDENZU_VAL = "9A110160800000049";	//心電図（所見）
	public static final String KYUBU_X_TYUKU_UMU = "9N206160700000011";	//胸部エックス線検査（一般：直接撮影）（所見の有無）
	public static final String KYUBU_X_TYUKU_VAL = "9N206160800000049";	//胸部エックス線検査（一般：直接撮影）（所見）
	public static final String KYUBU_X_KAN_UMU = "9N221160700000011";	//胸部エックス線検査（一般：間接撮影）（所見の有無）
	public static final String KYUBU_X_KAN_VAL = "9N221160800000049";	//胸部エックス線検査（一般：間接撮影）（所見）
	public static final String KAKUTAN_UMU = "6A010160706170411";	//喀痰検査 （塗抹鏡検　一般細菌）（所見の有無）
	public static final String KAKUTAN_VAL = "6A010160806170449";	//喀痰検査 （塗抹鏡検　一般細菌）（所見）
	public static final String KYUBU_CT_UMU = "9N251160700000011";	//胸部ＣＴ検査（所見の有無）
	public static final String KYUBU_CT_VAL = "9N251160800000049";	//胸部ＣＴ検査（所見）
	public static final String JYOUBU_X_TYUKU_UMU = "9N256160700000011";	//上部消化管エックス線（直接撮影）（所見の有無）
	public static final String JYOUBU__X_TYUKU_VAL = "9N256160800000049";	//上部消化管エックス線（直接撮影）（所見）
	public static final String JYOUBU_SYOUKAKAN_X_UMU = "9N261160700000011";	//上部消化管エックス線（間接撮影）（所見の有無）
	public static final String JYOUBU_SYOUKAKAN_X_VAL = "9N261160800000049";	//上部消化管エックス線（間接撮影）（所見）
	public static final String JYOUBU_KANNAISIKYOU_UMU = "9N266160700000011";	//上部消化管内視鏡検査（所見の有無）
	public static final String JYOUBU_KANNAISIKYOU_VAL = "9N266160800000049";	//上部消化管内視鏡検査（所見）
	public static final String FUKUBU_ONPA_UMU = "9F130160700000011";	//腹部超音波（所見の有無）
	public static final String FUKUBU_ONPA_VAL = "9F130160800000049";	//腹部超音波（所見）
	public static final String FIJINKA_UMU = "9N271160700000011";	//婦人科診察（所見の有無）
	public static final String FIJINKA_VAL = "9N271160800000049";	//婦人科診察（所見）
	public static final String NYUBOU_UMU = "9N276160700000011";	//乳房視触診（所見の有無）
	public static final String NYUBOU_VAL = "9N276160800000049";	//乳房視触診（所見）
	public static final String NYUBOU_GAZOU_UMU = "9N281160700000011";	//乳房画像診断（マンモグラフィー）（所見の有無）
	public static final String NYUBOU_GAZOU_VAL = "9N281160800000049";	//乳房画像診断（マンモグラフィー）（所見）
	public static final String NYUBOU_ONPA_UMU = "9F140160700000011";	//乳房超音波検査（所見の有無）
	public static final String NYUBOU_ONPA_VAL = "9F140160800000049";	//乳房超音波検査（所見）
	public static final String SIKYU_KEIBU_UMU = "9N291160700000011";	//子宮頚部視診（所見の有無）
	public static final String SIKYU_KEIBU_VAL = "9N291160800000049";	//子宮頚部視診（所見）
	public static final String SIKYU_NAISHIN_UMU = "9N296160700000011";	//子宮内診（所見の有無）
	public static final String SIKYU_NAISHIN_VAL = "9N296160800000049";	//子宮内診(所見）
	public static final String TYOKU_KOUMON2_UMU = "9Z771160700000011";	//直腸肛門機能（2項目以上）（所見の有無）
	public static final String TYOKU_KOUMON2_VAL = "9Z771160800000049";	//直腸肛門機能（2項目以上）（所見）
	public static final String TYOKU_KOUMON1_UMU = "9Z770160700000011";	//直腸肛門機能（1項目）（所見の有無）
	public static final String TYOKU_KOUMON1_VAL = "9Z770160800000049";	//直腸肛門機能（1項目）（所見）
	//add h.yoshitama 2009.09.15
	public static final String GYOMUREKI_VAL = "9N051000000000049";	//業務歴
	public static final String SONOTA_KAZOKUREKI_VAL = "9N071000000000049";	//その他(家族歴等)
	public static final String SHISHIN_KOUKU_VAL = "9N076000000000049";	//視診(口腔内含む)
	public static final String DATYOSHIN_VAL = "9N081000000000049";	//打聴診
	public static final String SHOKUSHIN_KANSETSUKADO_VAL = "9N086000000000049";	//触診(関節可動域含む)
	public static final String TYORYOKU_SONOTA_VAL = "9D100160900000049";	//聴力（その他の所見）
	public static final String CODE_CGATAKANEN_VAL = "9N401000000000011";	//C型肝炎ウィルス健診の判定
	public static final String SONOTA_TOKUSYUKENSHIN_VAL = "9N406000000000049";	//その他の法廷特殊健康診断
	public static final String SONOTA_HOUTEIKENSA_VAL = "9N411000000000049";	//その他の法廷検査
	public static final String SONOTA_KENSA_VAL = "9N416000000000049";	//その他の検査
	public static final String ISHI_IKEN_VAL = "9N521000000000049";	//医師の意見
	public static final String ISHI_NAME_IKEN_VAL = "9N526000000000049";	//意見を述べた医師の氏名
	public static final String KENSHIN_SHIKAI_VAL = "9N531000000000049";	//歯科医師による健康診断
	public static final String SHIKAI_NAME_KENSHIN_VAL = "9N536000000000049";	//健康診断を実施した歯科医師の氏名
	public static final String SHIKAI_IKEN_VAL = "9N541000000000049";	//歯科医師の意見
	public static final String SHIKAI_NAME_IKEN_VAL = "9N546000000000049";	//意見を述べた歯科医師の氏名
	public static final String BIKOU_VAL = "9N551000000000049";	//備考
	public static final String ISHI_NAME_SHINDAN_VAL = "9N576000000000049";	//診断をした医師の氏名
	public static final String JIYUUKISAI_HAIGAN_VAL = "9N581161400000049";	//医師の診断(肺がん検診)(自由記載)
	public static final String ISHI_NAME_HAIGAN_VAL = "9N586000000000049";	//診断をした医師の氏名(肺がん検診)
	public static final String JIYUUKISAI_IGAN_VAL = "9N591161400000049";	//医師の診断(胃がん検診)(自由記載)
	public static final String ISHI_NAME_IGAN_VAL = "9N596000000000049";	//診断をした医師の氏名(胃がん検診)
	public static final String JIYUUKISAI_NYUGAN_VAL = "9N601161400000049";	//医師の診断(乳がん検診)(自由記載)
	public static final String ISHI_NAME_NYUGAN_VAL = "9N606000000000049";	//診断をした医師の氏名(乳がん検診)
	public static final String JIYUUKISAI_SHIKYUGAN_VAL = "9N611161400000049";	//医師の診断(子宮がん検診)(自由記載)
	public static final String ISHI_NAME_SHIKYUGAN_VAL = "9N616000000000049";	//診断をした医師の氏名(子宮がん検診)
	public static final String JIYUUKISAI_DAITYOGAN_VAL = "9N621161400000049";	//医師の診断(大腸がん検診)(自由記載)
	public static final String ISHI_NAME_DAITYOGAN_VAL = "9N626000000000049";	//診断をした医師の氏名(大腸がん検診)
	public static final String JIYUUKISAI_ZENRITSUSENGAN_VAL = "9N631161400000049";	//医師の診断(前立腺がん検診)(自由記載)
	public static final String ISHI_NAME_ZENRITSUSENGAN_VAL = "9N636000000000049";	//診断をした医師の氏名(前立腺がん検診)
	public static final String JIYUUKISAI_SONOTA_VAL = "9N641000000000049";	//医師の診断(その他)
	public static final String ISHI_NAME_SONOTA_VAL = "9N646000000000049";	//診断をした医師の氏名(その他)

	public static final ArrayList<String> syokenUmu = new ArrayList<String>();
	public static final ArrayList<String> syoken = new ArrayList<String>();

	private static final void initializeSyokenUmu(){

		syokenUmu.add(NYOTINSA_UMU);   //1A105160700166211";	//尿沈渣（所見の有無）
//		syokenUmu.add(SHINDENZU_UMU);   //9A110160700000011";	//心電図（所見の有無）
		syokenUmu.add(KYUBU_X_TYUKU_UMU);   //9N206160700000011";	//胸部エックス線検査（一般：直接撮影）（所見の有無）
		syokenUmu.add(KYUBU_X_KAN_UMU);   //9N221160700000011";	//胸部エックス線検査（一般：間接撮影）（所見の有無）
		syokenUmu.add(KAKUTAN_UMU);   //6A010160706170411";	//喀痰検査 （塗抹鏡検　一般細菌）（所見の有無）
		syokenUmu.add(KYUBU_CT_UMU);   //9N251160700000011";	//胸部ＣＴ検査（所見の有無）
		syokenUmu.add(JYOUBU_X_TYUKU_UMU);   //9N256160700000011";	//上部消化管エックス線（直接撮影）（所見の有無）
		syokenUmu.add(JYOUBU_SYOUKAKAN_X_UMU);   //9N261160700000011";	//上部消化管エックス線（間接撮影）（所見の有無）
		syokenUmu.add(JYOUBU_KANNAISIKYOU_UMU);   //9N266160700000011";	//上部消化管内視鏡検査（所見の有無）
		syokenUmu.add(FUKUBU_ONPA_UMU);   //9F130160700000011";	//腹部超音波（所見の有無）
		syokenUmu.add(FIJINKA_UMU);   //9N271160700000011";	//婦人科診察（所見の有無）
		syokenUmu.add(NYUBOU_UMU);   //9N276160700000011";	//乳房視触診（所見の有無）
		syokenUmu.add(NYUBOU_GAZOU_UMU);   //9N281160700000011";	//乳房画像診断（マンモグラフィー）（所見の有無）
		syokenUmu.add(NYUBOU_ONPA_UMU);   //9F140160700000011";	//乳房超音波検査（所見の有無）
		syokenUmu.add(SIKYU_KEIBU_UMU);   //9N291160700000011";	//子宮頚部視診（所見の有無）
		syokenUmu.add(SIKYU_NAISHIN_UMU);   //9N296160700000011";	//子宮内診（所見の有無）
		syokenUmu.add(TYOKU_KOUMON2_UMU);   //9Z771160700000011";	//直腸肛門機能（2項目以上）（所見の有無）
		syokenUmu.add(TYOKU_KOUMON1_UMU);   //9Z770160700000011";	//直腸肛門機能（1項目）（所見の有無）
	}

	//edit h.yoshitama 2009.09.15
	private static final void initializeSyoken(){
		syoken.add(GYOMUREKI_VAL);   //9N051000000000049;	//業務歴
		syoken.add(SONOTA_KAZOKUREKI_VAL);   //9N071000000000049;	//その他(家族歴等)
		syoken.add(SHISHIN_KOUKU_VAL);   //9N076000000000049;	//視診(口腔内含む)
		syoken.add(DATYOSHIN_VAL);   //9N081000000000049";	//打聴診
		syoken.add(SHOKUSHIN_KANSETSUKADO_VAL);   //9N086000000000049;	//触診(関節可動域含む)
		syoken.add(NYOTINSA_VAL);   //1A105160800166249";	//尿沈渣（所見）
//		syoken.add(SHINDENZU_VAL);   //9A110160800000049";	//心電図（所見）
		syoken.add(KYUBU_X_TYUKU_VAL);   //9N206160800000049";	//胸部エックス線検査（一般：直接撮影）（所見）
		syoken.add(KYUBU_X_KAN_VAL);   //9N221160800000049";	//胸部エックス線検査（一般：間接撮影）（所見）
		syoken.add(KAKUTAN_VAL);   //6A010160806170449";	//喀痰検査 （塗抹鏡検　一般細菌）（所見）
		syoken.add(KYUBU_CT_VAL);   //9N251160800000049";	//胸部ＣＴ検査（所見）
		syoken.add(JYOUBU__X_TYUKU_VAL);   //9N256160800000049";	//上部消化管エックス線（直接撮影）（所見）
		syoken.add(JYOUBU_SYOUKAKAN_X_VAL);   //9N261160800000049";	//上部消化管エックス線（間接撮影）（所見）
		syoken.add(JYOUBU_KANNAISIKYOU_VAL);   //9N266160800000049";	//上部消化管内視鏡検査（所見）
		syoken.add(FUKUBU_ONPA_VAL);   //9F130160800000049";	//腹部超音波（所見）
		syoken.add(FIJINKA_VAL);   //9N271160800000049";	//婦人科診察（所見）
		syoken.add(NYUBOU_VAL);   //9N276160800000049";	//乳房視触診（所見）
		syoken.add(NYUBOU_GAZOU_VAL);   //9N281160800000049";	//乳房画像診断（マンモグラフィー）（所見）
		syoken.add(NYUBOU_ONPA_VAL);   //9F140160800000049";	//乳房超音波検査（所見）
		syoken.add(SIKYU_KEIBU_VAL);   //9N291160800000049";	//子宮頚部視診（所見）
		syoken.add(SIKYU_NAISHIN_VAL);   //9N296160800000049";	//子宮内診(所見）
		syoken.add(TYOKU_KOUMON2_VAL);   //9Z771160800000049";	//直腸肛門機能（2項目以上）（所見）
		syoken.add(TYOKU_KOUMON1_VAL);   //9Z770160800000049";	//直腸肛門機能（1項目）（所見）

		syoken.add(TYORYOKU_SONOTA_VAL);   //9D100160900000049;	//聴力（その他の所見）
		syoken.add(CODE_CGATAKANEN_VAL);   //9N401000000000011;	//C型肝炎ウィルス健診の判定
		syoken.add(SONOTA_TOKUSYUKENSHIN_VAL);   //9N406000000000049;	//その他の法廷特殊健康診断
		syoken.add(SONOTA_HOUTEIKENSA_VAL);   //9N411000000000049;	//その他の法廷検査
		syoken.add(SONOTA_KENSA_VAL);   //9N416000000000049";	//その他の検査
		syoken.add(ISHI_IKEN_VAL);   //9N521000000000049;	//医師の意見
		syoken.add(ISHI_NAME_IKEN_VAL);   //9N526000000000049";	//意見を述べた医師の氏名
		syoken.add(KENSHIN_SHIKAI_VAL);   //9N531000000000049;	//歯科医師による健康診断
		syoken.add(SHIKAI_NAME_KENSHIN_VAL);   //9N536000000000049;	//健康診断を実施した歯科医師の氏名
		syoken.add(SHIKAI_IKEN_VAL);   //9N541000000000049;	//歯科医師の意見
		syoken.add(SHIKAI_NAME_IKEN_VAL);   //9N546000000000049;	//意見を述べた歯科医師の氏名

		syoken.add(BIKOU_VAL);   //9N551000000000049;	//備考
		syoken.add(ISHI_NAME_SHINDAN_VAL);   //9N576000000000049;	//診断をした医師の氏名
		syoken.add(JIYUUKISAI_HAIGAN_VAL);   //9N581161400000049";	//医師の診断(肺がん検診)(自由記載)
		syoken.add(ISHI_NAME_HAIGAN_VAL);   //9N586000000000049";	//診断をした医師の氏名(肺がん検診)
		syoken.add(JIYUUKISAI_IGAN_VAL);   //9N591161400000049";	//医師の診断(胃がん検診)(自由記載)
		syoken.add(ISHI_NAME_IGAN_VAL);   //9N596000000000049;	//診断をした医師の氏名(胃がん検診)
		syoken.add(JIYUUKISAI_NYUGAN_VAL);   //9N601161400000049;	//医師の診断(乳がん検診)(自由記載)
		syoken.add(ISHI_NAME_NYUGAN_VAL);   //9N606000000000049;	//診断をした医師の氏名(乳がん検診)
		syoken.add(JIYUUKISAI_SHIKYUGAN_VAL);   //9N611161400000049;	//医師の診断(子宮がん検診)(自由記載)
		syoken.add(ISHI_NAME_SHIKYUGAN_VAL);   //9N616000000000049";	//診断をした医師の氏名(子宮がん検診)
		syoken.add(JIYUUKISAI_DAITYOGAN_VAL);   //9N621161400000049;	//医師の診断(大腸がん検診)(自由記載)

		syoken.add(ISHI_NAME_DAITYOGAN_VAL);   //9N626000000000049;	//診断をした医師の氏名(大腸がん検診)
		syoken.add(JIYUUKISAI_ZENRITSUSENGAN_VAL);   //9N631161400000049;	//医師の診断(前立腺がん検診)(自由記載)
		syoken.add(ISHI_NAME_ZENRITSUSENGAN_VAL);   //9N636000000000049;	//診断をした医師の氏名(前立腺がん検診)
		syoken.add(JIYUUKISAI_SONOTA_VAL);   //9N641000000000049";	//医師の診断(その他)
		syoken.add(ISHI_NAME_SONOTA_VAL);   //9N646000000000049";	//診断をした医師の氏名(その他)
	}

	static{
		initializeSyokenUmu();
		initializeSyoken();
	}
// move s.inoue 2011/09/30
//	//生活基本チェックリストの項目
//
//	public static final String SEIKATU_HYOKA_1 = "9N556000000000011";	//生活機能評価の結果１
//	public static final String SEIKATU_HYOKA_2 = "9N561000000000011";	//生活機能評価の結果２
//	public static final String SEIKATU_HYOKA_3 = "9N566000000000049";	//生活機能評価の結果３
//	public static final String ISHINO_HANDAN = "9N571000000000049";	//医師の診断（判定）（生活機能評価）
//
//	public static final String KINOU_1 = "9N811000000000011";	//１．バスや電車で1人で外出していますか
//	public static final String KINOU_2 = "9N816000000000011";	//２．日用品の買物をしていますか
//	public static final String KINOU_3 = "9N821000000000011";	//３．預貯金の出し入れをしていますか
//	public static final String KINOU_4 = "9N826000000000011";	//４．友人の家を訪ねていますか
//	public static final String KINOU_5 = "9N831000000000011";	//５．家族や友人の相談にのっていますか
//	public static final String KINOU_6 = "9N836000000000011";	//６．階段を手すりや壁をつたわらずに昇っていますか
//	public static final String KINOU_7 = "9N841000000000011";	//７．椅子に座った状態から何もつかまらずに立ち上がっていますか
//	public static final String KINOU_8 = "9N846000000000011";	//８．15分位続けて歩いていますか
//	public static final String KINOU_9 = "9N851000000000011";	//９．この1年間に転んだことがありますか
//	public static final String KINOU_10 = "9N856000000000011";	//１０．転倒に対する不安は大きいですか
//	public static final String KINOU_11 = "9N861000000000011";	//１１．6ヵ月間で2～3kg以上の体重減少がありましたか
//	public static final String KINOU_12 = "9N866000000000001";	//１２．身長　　　　　ｃｍ　　　体重　　　　　ｋｇ　（ＢＭＩ＝　　　　）
//	public static final String KINOU_13 = "9N871000000000011";	//１３．半年前に比べて固いものが食べにくくなりましたか
//	public static final String KINOU_14 = "9N876000000000011";	//１４．お茶や汁物等でむせることがありますか
//	public static final String KINOU_15 = "9N881000000000011";	//１５．口の渇きが気になりますか
//	public static final String KINOU_16 = "9N886000000000011";	//１６．週に１回以上は外出していますか
//	public static final String KINOU_17 = "9N891000000000011";	//１７．昨年と比べて外出の回数が減っていますか
//	public static final String KINOU_18 = "9N896000000000011";	//１８．周りの人から「いつも同じ事を聞く」などの物忘れがあると言われますか
//	public static final String KINOU_19 = "9N901000000000011";	//１９．自分で電話番号を調べて、電話をかけることをしていますか
//	public static final String KINOU_20 = "9N906000000000011";	//２０．今日が何月何日かわからない時がありますか
//	public static final String KINOU_21 = "9N911000000000011";	//２１．（ここ2週間）毎日の生活に充実感がない
//	public static final String KINOU_22 = "9N916000000000011";	//２２．（ここ2週間）これまで楽しんでやれていたことが楽しめなくなった
//	public static final String KINOU_23 = "9N921000000000011";	//２３．（ここ2週間）以前は楽にできていたことが今ではおっくうに感じられる
//	public static final String KINOU_24 = "9N926000000000011";	//２４．（ここ2週間）自分が役に立つ人間だと思えない
//	public static final String KINOU_25 = "9N931000000000011";	//２５．（ここ2週間）わけもなく疲れたような感じがする

	public static final ArrayList<String> seikatuKihon = new ArrayList<String>();

// move s.inoue 2011/09/30
//	public static final String[] codesSeikatuKihon = {
//		"9N811000000000011","9N816000000000011","9N821000000000011","9N826000000000011",
//		"9N831000000000011","9N836000000000011","9N841000000000011","9N846000000000011",
//		"9N851000000000011","9N856000000000011","9N861000000000011","9N866000000000001",
//		"9N871000000000011","9N876000000000011","9N881000000000011","9N886000000000011",
//		"9N891000000000011","9N896000000000011","9N901000000000011","9N906000000000011",
//		"9N911000000000011","9N916000000000011","9N921000000000011","9N926000000000011",
//		"9N931000000000011",
//		"9N556000000000011","9N561000000000011","9N566000000000049","9N571000000000049"
//	};

	private static final void initializeSeikatuKihon(){
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_1);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_2);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_3);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_4);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_5);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_6);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_7);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_8);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_9);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_10);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_11);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_12);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_13);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_14);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_15);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_16);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_17);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_18);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_19);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_20);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_21);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_22);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_23);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_24);
		seikatuKihon.add(JConstantString.MONSHIN_KINOU_25);
		seikatuKihon.add(JConstantString.SEIKATU_HYOKA_1);
		seikatuKihon.add(JConstantString.SEIKATU_HYOKA_2);
		seikatuKihon.add(JConstantString.SEIKATU_HYOKA_3);
		seikatuKihon.add(JConstantString.ISHINO_HANDAN);
	}

	static {
		initializeSeikatuKihon();
	}

}
