package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.imports;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.component.IDialog;
import jp.or.med.orca.jma_tokutei.common.convert.JCalendarConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JEraseSpace;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.THokenjyaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TShiharaiDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.THokenjya;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.sql.model.TShiharai;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.zip.JZipDecompresser;
import jp.or.med.orca.jma_tokutei.db.DBYearAdjuster;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportErrorResultFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportResultFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JOriginalFormatData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JImportDataFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKekkaRegisterFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameData.T_KOJIN_COLUMN;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectHokenjyaFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectHokenjyaFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectKojinFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectKojinFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectShiharaiFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectShiharaiFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.KihonKensaKoumoku;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 外部健診結果データ取り込みのフレームのコントロール
 */
public class JImportDataFrameCtrl extends JImportDataFrame
{
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream Reader = null;
	private static Vector<Vector<String>> CSVItems = null;

	private static TKojin kojin = new TKojin();
	private static TKensakekaTokutei kensakekaTokutei = new TKensakekaTokutei();
	private static TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
	private static TKensakekaTokuteiDao kensakekaTokuteiDao = null;
	private static TKensakekaSonotaDao kensakekaSonotaDao = null;
	private static TKojinDao kojinDao = null;
	private static THokenjyaDao hokenjyaDao = null;
	private static TShiharaiDao shiharaiDao = null;
	private static TNayoseDao tNayoseDao = null;

	private static String ResultMessage = "";
	private Long preuketukeId = 0L;
	private Integer prekensaDate = 0;
	private static final String FLAME_KOJIN_TITLE_MESSAGE = "同姓同名・同一生年月日の受診者が存在します。";
	private String BMIFormat = "##.0";

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private ButtonGroup radioButtonGroup = new ButtonGroup();

	// loggerの objectを作る。
    private static org.apache.log4j.Logger logger = Logger.getLogger(JImportDataFrameCtrl.class);
	private static ArrayList<String>importKenshinItem = new ArrayList<String>();
	private static HashMap<String, String> koumokuCodeMap = null;
	// 未実施、測定不能は実施区分を格納
	private static HashMap<String, String> koumokuCodeElseMap = null;

	private static JKekkaRegisterFrameData validatedKenshinData = new JKekkaRegisterFrameData();
	private static JKojinRegisterFrameData validatedKojinData  = null;

	private static JImportErrorResultFrameData Data =null;
	private static String kikanNumber = null;
	private static Integer kenshinPatternNumver = null;

	private IDialog patternSelectDialog;

	JImportDataFrameData validateDate = new JImportDataFrameData();

	public JImportDataFrameCtrl()
	{
		// add s.inoue 2009/10/14
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
// del s.inoue 2010/04/02
//		this.focusTraversalPolicy.setDefaultComponent(jRadio_SelectDokuji);
//		this.focusTraversalPolicy.addComponent(jRadio_SelectDokuji);
//		this.focusTraversalPolicy.addComponent(jRadio_SelectNitii);
		this.focusTraversalPolicy.setDefaultComponent(jButton_OpenFile);
		this.focusTraversalPolicy.addComponent(jButton_OpenFile);
		this.focusTraversalPolicy.addComponent(jButton_Import);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		//健診センター名称コンボボックスの初期設定
		ArrayList<Hashtable<String,String>> Result = null;
		Hashtable<String,String> ResultItem;
		String Query = new String(	"SELECT DISTINCT KENSA_CENTER_CD,CENTER_NAME " +
									"FROM T_KENSACENTER_MASTER " +
									"ORDER BY KENSA_CENTER_CD ASC");
		try{
			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		}catch(SQLException e){
			e.printStackTrace();
			JErrorMessage.show("M3320",this);
		}

		for(int i = 0;i < Result.size();i++ )
		{
			ResultItem = Result.get(i);
			jComboBox_KensaCenterName.addItem(ResultItem.get("CENTER_NAME"));
		}
		// del s.inoue 2010/04/02
//		radioButtonGroup.add(jRadio_SelectDokuji);
//		radioButtonGroup.add(jRadio_SelectNitii);
//		jRadio_SelectDokuji.setSelected(true);
	}

	public boolean validateAsImportPushed(JImportDataFrameData data)
	{
		if(validateDate.getFilePath().equals(""))
		{
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3301",this);
			return false;
		}
		validateDate.setValidateAsDataSet();
		return true;
	}

	/* Add 2008/07/23 井上 */
	/* --------------------------------------------------- */
	public boolean validateImportJusinSeiriNo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		// エラーの場合、種別をセット
		if (!data.jusinSeiriNo.toString().equals("")){
			// 数値チェック
			//if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
			//	data.errCase=JApplication.ERR_KIND_NUMBER;
			//	errFlg=true;
			//}else

			// 必須チェック
			if (data.jusinSeiriNo.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// 半角チェック
			if (JValidate.isAllHankaku(data.jusinSeiriNo.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 桁数チェック
			if (JValidate.checkMaxLimit(data.jusinSeiriNo.toString(),11) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}
		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_JUSIN_SEIRI_NO;

		return errFlg;
	}

	public boolean validateImportJisiKikanNo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try {
			// 必須チェック
			if (data.jisiKikanNo.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// 半角チェック
			if (JValidate.isAllHankaku(data.jisiKikanNo.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 桁数チェック
			if (JValidate.checkMaxLimit(data.jisiKikanNo.toString(),10) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}else
			// 一致チェック
			if (!JApplication.kikanNumber.equals(data.jisiKikanNo.toString())){
				data.errCase=JApplication.ERR_KIND_NOT_EXIST;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_JISI_KIKAN_NO;

		return errFlg;
	}

	public boolean validateImportJisiDate(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			String temp = JCalendarConvert.JCtoAD(data.jishiDate);
			// 半角チェック
			if (JValidate.isAllHankaku(data.jishiDate.toString()) == false){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 実施日妥当性チェック
			if( temp == null )
			{
				data.errCase=JApplication.ERR_KIND_DATE;
				errFlg = true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_JISI_DATE;

		return errFlg;
	}

	public boolean validateImportkanaShimei(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			// 必須チェック
			if (data.kanaShimei.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else

			// 半角チェック
			if ( !JValidate.isAllHankaku(data.kanaShimei,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else

			// 桁数チェック
			if (JValidate.checkMaxLimit(data.kanaShimei,17) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SHIMEI;

		return errFlg;
	}

	// add s.inoue 2010/02/03
	public boolean validateImportNitiiFormatkanaShimei(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			// 必須チェック
			if (data.kanaShimei.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else

			// 半角チェック
			if ( !JValidate.isAllZenkaku(data.kanaShimei)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else

			// 桁数チェック
			if (JValidate.checkMaxLimit(data.kanaShimei,40) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SHIMEI;

		return errFlg;
	}

	public boolean validateImportseiNenGapi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		String temp ="";

		try{
			// 生年月日日付妥当性チェック
			if (data.seiNenGapi.toString().equals("") &&
					data.jusinSeiriNo.toString().equals("")){
					// keyNotExist
					data.errCase=JApplication.ERR_KIND_DATE;
					errFlg = true;
			// edit ver2 s.inoue 2009/06/24
			}else if(!data.seiNenGapi.toString().equals("")){

				temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
				if( temp == null ){
					data.errCase=JApplication.ERR_KIND_DATE;
					errFlg = true;
				}

				// 再変換処理(和暦→西暦)
				if (!JValidate.checkByte(data.seiNenGapi,8) &&
					!JValidate.isNumber(data.seiNenGapi.toString())){

					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
					if( temp == null ){
						data.errCase=JApplication.ERR_KIND_DATE;
						errFlg = true;
					}
				}

				// 数値チェック
				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
					data.errCase=JApplication.ERR_KIND_NUMBER;
					errFlg=true;
				}
				// 半角チェック
				if ( !JValidate.isAllHankaku(data.seiNenGapi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEINENGAPI;
		return errFlg;
	}

	// add s.inoue 2010/02/03
	public boolean validateImportNitiiFormatseiNenGapi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		String temp ="";

		try{
			// 生年月日日付妥当性チェック
			// 必須チェック
			if (data.seiNenGapi.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			// edit s.inoue 2010/02/03
			}else{
				temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
				if( temp == null ){
					data.errCase=JApplication.ERR_KIND_DATE;
					errFlg = true;
				}

				// 再変換処理(和暦→西暦)
				if (!JValidate.checkByte(data.seiNenGapi,8) &&
					!JValidate.isNumber(data.seiNenGapi.toString())){

					temp = JCalendarConvert.JCtoAD(data.seiNenGapi);
					if( temp == null ){
						data.errCase=JApplication.ERR_KIND_DATE;
						errFlg = true;
					}
				}

				// 数値チェック
				if (JValidate.isNumber(data.jusinSeiriNo.toString()) == false){
					data.errCase=JApplication.ERR_KIND_NUMBER;
					errFlg=true;
				}
				// 半角チェック
				if ( !JValidate.isAllHankaku(data.seiNenGapi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEINENGAPI;
		return errFlg;
	}

	public boolean validateImportSex(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// 性別チェック
		try{
			if (!data.seiBetu.toString().equals("")){
				// 半角チェック
				if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}else if( !JValidate.validateSexFlag(data.seiBetu) )
				{
					// 性別妥当性
					data.errCase=JApplication.ERR_KIND_SEX;
					errFlg = true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEIBETU;
		return errFlg;
	}

	// add s.inoue 2010/02/03
	public boolean validateImportNitiiFormatSex(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// 性別チェック
		try{
			// 生年月日日付妥当性チェック
			// 必須チェック
			if (data.seiBetu.toString().equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;

			// 半角チェック
			}else if ( !JValidate.isAllHankaku(data.seiBetu,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else if( !JValidate.validateSexFlag(data.seiBetu) )
			{
				// 性別妥当性
				data.errCase=JApplication.ERR_KIND_SEX;
				errFlg = true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SEIBETU;
		return errFlg;
	}

	public boolean validateImportNyuBi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// 乳ビ/溶血チェック
		try{
			if (!data.nyuBi.toString().equals("")){
				// 桁数チェック
				if (JValidate.checkMaxLimit(data.nyuBi,10) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_YOUKETU;
		return errFlg;
	}

	public boolean validateImportShokuZenGo(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// 食前/食後チェック
		try{
			if (!data.shokuZenGo.toString().equals("")){
				// 桁数チェック
				if (JValidate.checkMaxLimit(data.shokuZenGo,10) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.ZOKUSEI_FIELD_SHOKUZENGO;
		return errFlg;
	}


	/*** 詳細情報 ***/
	public boolean validateKensaKoumokuCode(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;
		// 検査項目コードチェック
		try{
			// 必須チェック
			if (data.koumokuCd.equals("")){
				data.errCase=JApplication.ERR_KIND_EMPTY;
				errFlg=true;
			}else
			// 半角チェック
			if ( !JValidate.isAllHankaku(data.koumokuCd,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			}else
			// 桁数チェック 17桁のみに変更 s.inoue 20081114
			//if (JValidate.checkMaxLimit(data.koumokuCd,17) == false){
			if (JValidate.checkByte(data.koumokuCd,17) == false){
				data.errCase=JApplication.ERR_KIND_DIGIT;
				errFlg=true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.KENSA_FIELD_KOUMOKU_CD;
		return errFlg;
	}

	public boolean validateImportJisiKbn(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		// 実施区分チェック
		try{
			String temp = JValidate.validateJisiKubun(data.jisiKbn);

			// add s.inoue 20081118(空腹時血糖,ＨｂＡ１ｃ)
			//boolean kensaJisi = checkTokuteiKensaJisi(data);

			// 必須チェック
			if (data.jisiKbn.toString().equals("")){
					data.errCase=JApplication.ERR_KIND_EMPTY;
					errFlg=true;
			}
			// 半角チェック
			else if ( !JValidate.isAllHankaku(data.jisiKbn,JApplication.CSV_CHARSET)){
				data.errCase=JApplication.ERR_KIND_HALF;
				errFlg=true;
			// 値チェック
			}
			else if( temp == null )
			{
				data.errCase=JApplication.ERR_KIND_NOT_EXIST;
				errFlg = true;
			}
		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.KENSA_FIELD_JISI_KBN;
		return errFlg;
	}

	private boolean checkTokuteiKensaJisi(JImportErrorResultFrameData data)
	{
		boolean hantei = false;
		// 空腹時血糖,ＨｂＡ１ｃの場合
		if (KihonKensaKoumoku.KUFUKU_CODES.contains(data.koumokuCd) ||
				KihonKensaKoumoku.HBA1C_CODES.contains(data.koumokuCd)){
			// 結果値無し
			if (data.kekkaTi.equals("")){
				hantei=true;
			}
		}

		return hantei;
	}
//	 del 20080918 s.inoue
//	public boolean validateImportHLKbn(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// 異常値区分チェック
//		try{
//			if (!data.ijyoKbn.toString().equals("")){
//				// 値チェック
//				String temp = JValidate.validateHLKubun(data.ijyoKbn);
//
//				// 半角チェック
//				if ( !JValidate.isAllHankaku(data.ijyoKbn,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}
//				else if( temp == null )
//				{
//					data.errCase=JApplication.ERR_KIND_NOT_EXIST;
//					errFlg = true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_IJYO_KBN;
//
//		return errFlg;
//	}

// del 20080918 s.inoue
//	public boolean validateImportKekkaTiKeitai(JImportErrorResultFrameData data)
//	{
//		boolean errFlg = false;
//		// 結果値形態チェック
//		try{
//			if (!data.kekkaTiKeitai.toString().equals("")){
//				String temp = JValidate.validateKekkaTiKeitai(data.kekkaTiKeitai);
//
//				// 半角チェック
//				if ( !JValidate.isAllHankaku(data.kekkaTiKeitai,JApplication.CSV_CHARSET)){
//					data.errCase=JApplication.ERR_KIND_HALF;
//					errFlg=true;
//				}
//				else if( temp == null )
//				{
//					data.errCase=JApplication.ERR_KIND_NOT_EXIST;
//					errFlg = true;
//				}
//			}
//		}catch(Exception ex){
//			data.errCase=JApplication.ERR_KIND_TYPE;
//			errFlg=true;
//		}
//
//		if (errFlg)
//			data.errField=JApplication.KENSA_FIELD_KEKATI_KEITAI;
//
//		return errFlg;
//	}

	public boolean validateImportKekkaTi(JImportErrorResultFrameData data)
	{
		boolean errFlg = false;

		try{
			// 結果値チェック
			// 必須チェック
// del s.inoue 20080916
//			if (data.kekkaTi.equals("")){
//				data.errCase=JApplication.ERR_KIND_EMPTY;
//				errFlg=true;
//			}else

			// edit s.inoue 20080916
			// 実施区分が1の時
			if (data.jisiKbn.equals("1"))
			{
				// 半角チェック
				if ( !JValidate.isAllHankaku(data.kekkaTi,JApplication.CSV_CHARSET)){
					data.errCase=JApplication.ERR_KIND_HALF;
					errFlg=true;
				}else
				// 桁数チェック
				if (JValidate.checkMaxLimit(data.kekkaTi,14) == false){
					data.errCase=JApplication.ERR_KIND_DIGIT;
					errFlg=true;
				}

				// add 20080916 s.inoue
				// 結果値なし 未実施
				if (data.kekkaTi.equals("")){
					data.jisiKbn = "0";
				}
			}

			// add 20080916 s.inoue
			// 結果値あり 未実施又は測定不能
			else if( !data.kekkaTi.equals("") &&
					(data.jisiKbn.equals("0") ||
					(data.jisiKbn.equals("2"))))
			{
				data.errCase=JApplication.ERR_KIND_DATA;
				errFlg = true;
			}

		}catch(Exception ex){
			data.errCase=JApplication.ERR_KIND_TYPE;
			errFlg=true;
		}

		if (errFlg)
			data.errField=JApplication.KENSA_FIELD_KEKATI;

		return errFlg;
	}

	// CSVファイルformatチェック
	// flg|true:結果 false:日医
	private boolean inputCsvFileCheck(String FilePath,boolean flg){
		boolean retCheck = false;

// move s.inoue 2010/10/20 移動
//		// 1.txt形式かどうか
//		if (!FilePath.endsWith(".txt") &&
//				!FilePath.endsWith(".TXT") &&
//					!FilePath.endsWith(".csv") &&
//						!FilePath.endsWith(".CSV"))
//			retCheck = true;

		Vector<String> insertRow = new Vector<String>();

		// 検査結果データ取込処理
		if (flg){
			for (int i = 0; i < CSVItems.size(); i++) {
				insertRow =CSVItems.get(i);
				int csvCnt = insertRow.size() - 1;

				// 2.属性情報 が13個以上あるか
				if (csvCnt <= 13)
					retCheck = true;

				// 3.属性情報 - 検査項目情報が8で割り切れる
				csvCnt = csvCnt - JApplication.CSV_ZOKUSEI_COUNT;
				int csvKensa = csvCnt % 8;
				if (csvKensa != 0)
					retCheck = true;
			}
		}
		return retCheck;
	}

	/*--------------------------------------nitii-----------------------------------------------
	/**
	 * 日医フォーマットデータの取り込み
	 * @param
	 */
	public void nitiImport(
			JImportErrorResultFrameData Data,
			ArrayList<Hashtable<String, String>> kojinData,
			ArrayList<Hashtable<String, String>> selectKojinData,
			Vector<JImportErrorResultFrameData> ErrorResultData)
	{

		// 登録件数変数
		int intregistCnt = 0;
		boolean retblnALL = false;
		ResultMessage="";

		// 検査結果データ取込処理
		for (int i = 0; i < CSVItems.size(); i++) {

			Vector<String> insertRow = new Vector<String>();

			insertRow =CSVItems.get(i);

			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "件");

			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JUSIN_SEIRI_NO));
			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SHIMEI));
			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISI_DATE));
			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_SEIBETU));
			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_NITII_ZOKUSEI_JISIKIKAN_NO));

			// 初期化処理
			Data.uketukeNo =null;
			kojinData = null;
			preuketukeId = 0L;
			prekensaDate =0;

			// 結果取込データ(キー情報)チェック処理
			if (checkKensaKekkaKeyNitiiFormatData(Data,ErrorResultData,i,false)){
				i++;continue;
			}

			// 個人結果データ:(整理番号,健診日単位で取得)
			try {
				if (!Data.jusinSeiriNo.toString().equals(""))
					kojinData = getKojinData(Data);

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。");
					break;
				}

			// 該当するデータがない場合、人(氏名,生年月日,性別)で検索
			if (kojinData == null ||
					kojinData.size() == 0){
				try{

					selectKojinData = getSimeiData(Data);

					if (selectKojinData == null ||
							selectKojinData.size() == 0){
						break;
					}

					// 該当する人が複数いた場合、リストを表示する
					Hashtable<String, String>selectedKojinData
						= searchKojinData(selectKojinData,Data,false);

					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
						break;
					}

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[日医フォーマット]個人情報データを取得する時にエラーが発生しました。");
					break;
				}
			}

			// add s.inoue 2010/02/10
			// 配列から構造体へ変換（ Arrays.asList）
			HashMap<String, String> csvKoumokuCD = this.registerKoumokuCd(validateDate);

			// 結果取込CSVファイルとDBの値の比較を行う。
			// その他テーブルの項目コード単位で処理を行う。
			for (int j = 0; j < kojinData.size(); j++) {

					Hashtable<String,String> DatabaseItem = kojinData.get(j);

					// del s.inoue 2010/02/17
					// if (Data.uketukeNo == null){
					Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					//}

					// 受付IDを比較する（一致：登録対象、不一致：エラー対象）
					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);

// del s.inoue 2010/02/17 1件のみの対象により削除
					// データベースに登録(T_KENSAKEKKA_TOKUTEI)
					// キー情報(:受付ID,検査実施日)が変わったら登録処理を行う
//					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
//							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
						try{

							// 結果取込データ(属性情報)チェック処理
							if (checkKensaKekkaData(Data,ErrorResultData,i)){
								j++;continue;
							}

							// 既に健診結果データが存在した場合、警告メッセージを表示する。
							// 健診実施日,氏名カナ毎 結果登録するとデータが存在する為、更新処理のみ
							// edit s.inoue 2010/02/10
							String strCsvKoumokuOrder = (String) csvKoumokuCD.get(koumokuCdDB);
							if (strCsvKoumokuOrder == null){
								j++;continue;
							}
							System.out.println("csv:" + strCsvKoumokuOrder);
							// 項目コードはCSV順番を渡して取得
							// edit s.inoue 2010/02/17
							// Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
							Data.koumokuCd = koumokuCdDB;
							Data.kekkaTi = Reader.rmQuart(insertRow.get(Integer.parseInt(strCsvKoumokuOrder)));

							// 項目コードチェック
							if (checkKoumokuCD(Data,ErrorResultData,i)){
								j++;continue;
							}

							boolean existsKensaKekkaData = existsKensaKekkaData(Data);

							if (existsKensaKekkaData) {

								if (!retblnALL){
									// M3328=確認,[%s]には、すでに結果データが存在します。登録した場合、
									// 以前に入力した結果データは消えてしまいます。登録してよろしいですか？
									String[] messageParams = {"受付番号:"+ Data.uketukeNo + "/" + "健診実施日:" + Data.jishiDate + "/" + "氏名:"+ Data.kanaShimei};
									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
									// cancel時
									if (retValue == RETURN_VALUE.CANCEL) {
										try {
											// eidt s.inoue 2011/06/07
											//JApplication.kikanDatabase.rollback();
											JApplication.kikanDatabase.getMConnection().rollback();
										}catch (SQLException err) {}
										return;
									}else if (retValue == RETURN_VALUE.NO){
										preuketukeId=Long.valueOf(uketukeIdDB);
										prekensaDate=Integer.valueOf(kensaDateDB);
										j++;continue;
									// add s.inoue 2010/02/17
									}else if (retValue == RETURN_VALUE.YES){
										updatekensakekaSonota(Data,insertRow);
									}else if (retValue == RETURN_VALUE.ALL){
										retblnALL = true;
									}
								}
							}else{
								updatekensakekaSonota(Data,insertRow);
							}
							intregistCnt++;

							// add s.inoue 2010/03/16
							// BMI自動計算 roop最後に処理
							String height = "";
							String weight = "";
							if(Data.koumokuCd.equals("9N001000000000001")){
								height = Data.kekkaTi;
							}else if (Data.koumokuCd.equals("9N006000000000001")){
								weight = Data.kekkaTi;
							}
							if (j == kojinData.size()-1){
								String bmi = calcBMI(height, weight);
								if (bmi.equals(""))break;

								String kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
								Data.koumokuCd = "9N011000000000001"; //BMI
								Data.kekkaTi = kensaKekka;
								updatekensakekaSonota(Data,insertRow);
							}

						}catch(Exception ex){
							try {
								// eidt s.inoue 2011/06/07
								// JApplication.kikanDatabase.rollback();
								JApplication.kikanDatabase.getMConnection().rollback();
							} catch (SQLException err) {}
							ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);

							logger.error("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。");
							break;
						}
					}

// del s.inoue 2010/02/03
//					// キー情報が一致したら検査結果登録してあり正常
//					// 「その他:1～22の項目(動的に変化する)」の登録
//					Integer kensaKoumokuLoop = JApplication.CSV_KENSA_KOUMOKU_LOOP;
//					int cntKoumoku =0;
//
//					while (cntKoumoku < JApplication.CSV_KENSA_LOOP * JApplication.CSV_KENSA_KOUMOKU_LOOP) {
//
//						if (insertRow.size() <= JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku )
//						{
//							cntKoumoku += kensaKoumokuLoop;break;
//						}
//						Data.koumokuCd = Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku));
//
//						if (Data.koumokuCd.equals("")){
//							checkKoumokuCD(Data,ErrorResultData,i);
//							cntKoumoku += kensaKoumokuLoop;continue;
//						}
//
//						try {
//							Long resultCnt = kensakekaSonotaDao.selectByCountPrimaryKey(
//									Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
//							if (resultCnt < 0) {
//								checkKoumokuCD(Data,ErrorResultData,i);
//								cntKoumoku += kensaKoumokuLoop;
//								continue;
//							}
//
//						} catch (Exception e) {
//							logger.error("[検査結果取込処理]検査結果データその他データ取得時、エラーとなりました。");
//							break;
//						}
//
//						if(JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(JEraseSpace.EraceSpace(uketukeIdDB)) &&
//							JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(JEraseSpace.EraceSpace(kensaDateDB)) &&
//							 JEraseSpace.EraceSpace(Data.koumokuCd.toString()).equals(JEraseSpace.EraceSpace(koumokuCdDB)))
//						{
//							Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN + cntKoumoku));
//							Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI + cntKoumoku));
//
//							// 結果取込データ(検査項目情報)
//							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
//								cntKoumoku += kensaKoumokuLoop;break;
//							}
//
//							// 健診結果その他登録処理
//							try{
//								kensakekaSonotaregister(Data);
//
//							}catch(Exception ex){
//								try {
//									JApplication.kikanDatabase.rollback();
//
//								} catch (SQLException err) {
//								}
//								logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。");
//								ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);
//								break;
//							}
//						}
//
//						cntKoumoku += kensaKoumokuLoop;
//					}
// del s.inoue 2010/02/17
//			}
		}
	}

	// sonotaテーブル更新
	private void updatekensakekaSonota(JImportErrorResultFrameData Data,Vector<String> insertRow){
		// Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN ));
		Data.jisiKbn ="1"; // フィールドなし固定
		// Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI));
		try {
			kensakekaSonotaregister(Data);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// add s.inoue 2010/02/10
	// 項目コード登録
	private HashMap<String, String> registerKoumokuCd(JImportDataFrameData data){

		HashMap<String, String> hm = new HashMap<String, String>();
		List arrKoumokuCD = new ArrayList<String>();
		List arrKoumokuOrder = new ArrayList<String>();

		arrKoumokuCD = Arrays.asList(data.koumokuCD_Situmonhyo);
		arrKoumokuOrder = Arrays.asList(data.koumokuCD_Situmonhyo_Order);

		for (int i = 0; i < arrKoumokuCD.size(); i++) {
			hm.put((String)arrKoumokuCD.get(i), (String)arrKoumokuOrder.get(i));
		}

		return hm;
	}

	// add s.inoue 2010/01/26
	/**
	 * 取り込み前処理
	 * @param FilePath 読み込むファイルのパス
	 */
	private void nitiImportCheck(String FilePath)
	{
		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		ArrayList<Hashtable<String, String>> kojinData=null;
		ArrayList<Hashtable<String, String>> selectKojinData=null;

		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();

		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();

		// CSV読込処理
		Reader = new JCSVReaderStream();

		try {

			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);

			CSVItems = Reader.readAllTable();

			if (inputCsvFileCheck(FilePath,false)){
				JErrorMessage.show("M3327",this);
				return;
			}

		} catch (Exception e) {
			logger.error("[日医フォーマット取込処理]CSV読込処理に失敗しました。");
			JErrorMessage.show("M3329",this);
			return;
		}

		// 機関(特定,その他)接続生成
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();

			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
					connection,
					kensakekaTokutei);

			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
					connection,
					kensakekaSonota);

			// eidt s.inoue 2011/07/27 false → true
			// JApplication.kikanDatabase.Transaction();
			JApplication.kikanDatabase.getMConnection().setAutoCommit(true);

		} catch (Exception e) {
			logger.error("[検査結果取込処理]機関FDBへの接続処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}

		// 取込み
		nitiImport(Data,kojinData,selectKojinData,ErrorResultData);

		// eidt s.inoue 2011/06/07
		// JApplication.kikanDatabase.rollback();
		try {
			JApplication.kikanDatabase.getMConnection().rollback();
		} catch (SQLException ex) {
			logger.warn(ex.getMessage());
		}
	}
	/*--------------------------------------nitii-----------------------------------------------*/

	/*-------------------------------------orgine-----------------------------------------------*/
	/**
	 * データの取り込み
	 * @param FilePath 読み込むファイルのパス
	 */
	public void kekkaImport(String FilePath)
	{

		String logfile = PropertyUtil.getProperty("log.filename");
		PropertyConfigurator.configure(logfile);

		ArrayList<Hashtable<String, String>> kojinData=null;
		ArrayList<Hashtable<String, String>> selectKojinData=null;

		Vector<JImportErrorResultFrameData> ErrorResultData = new Vector<JImportErrorResultFrameData>();

		JImportErrorResultFrameData Data = new JImportErrorResultFrameData();

		// 登録件数変数
		int intregistCnt = 0;
		boolean retblnALL = false;
		ResultMessage="";

		// CSV読込処理
		Reader = new JCSVReaderStream();

		try {

			Reader.openCSV(FilePath,JApplication.CSV_CHARSET);

			CSVItems = Reader.readAllTable();

			// add s.inoue 20081001
			if (inputCsvFileCheck(FilePath,true)){
				JErrorMessage.show("M3327",this);
				return;
			}

		} catch (Exception e) {
			logger.error("[検査結果取込処理]CSV読込処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}

		// 機関(特定,その他)接続生成
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();

			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(
					connection,
					kensakekaTokutei);

			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(
					connection,
					kensakekaSonota);

			// eidt s.inoue 2011/07/27
			// JApplication.kikanDatabase.Transaction();
			JApplication.kikanDatabase.getMConnection().setAutoCommit(true);

		} catch (Exception e) {
			logger.error("[検査結果取込処理]機関FDBへの接続処理に失敗しました。");
			JErrorMessage.show("M3326",this);
			return;
		}

		// 検査結果データ取込処理
		for (int i = 0; i < CSVItems.size(); i++) {

			Vector<String> insertRow = new Vector<String>();

			insertRow =CSVItems.get(i);

			// add s.inoue 20081001
			this.m_guiStatus.setText((i+1) + " / " + CSVItems.size() + "件");

			// 属性情報取得 CSVデータをローカル変数にセット(「"」を除去したもの)
			Data.jusinSeiriNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JUSIN_SEIRI_NO));
			Data.jishiDate = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISI_DATE));
			Data.kanaShimei = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHIMEI));
			Data.seiNenGapi = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEINENGAPI));
			Data.seiBetu = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SEIBETU));
			Data.jisiKikanNo = Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_JISIKIKAN_NO));
			Data.nyuBi =Reader.rmQuart((insertRow.get(JApplication.CSV_ZOKUSEI_NYUBI_YOUKETU)));
			Data.shokuZenGo =Reader.rmQuart(insertRow.get(JApplication.CSV_ZOKUSEI_SHOKUZEN_SHOKUGO));

			// 初期化処理
			Data.uketukeNo =null;
			kojinData = null;
			preuketukeId = 0L;
			prekensaDate =0;

			// 結果取込データ(キー情報)チェック処理
			if (checkKensaKekkaKeyData(Data,ErrorResultData,i,false)){
				i++;continue;
			}

			// 個人結果データ:(整理番号,健診日単位で取得)
			try {
				if (!Data.jusinSeiriNo.toString().equals(""))
					kojinData = getKojinData(Data);

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。");
					break;
				}

			// 該当するデータがない場合、人(氏名,生年月日,性別)で検索
			if (kojinData == null ||
					kojinData.size() == 0){
				try{

					selectKojinData = getSimeiData(Data);

					if (selectKojinData == null ||
							selectKojinData.size() == 0){
						break;
					}

					// 該当する人が複数いた場合、リストを表示する
					// edit s.inoue 2010/02/03
					Hashtable<String, String>selectedKojinData
						= searchKojinData(selectKojinData,Data,true);

					// del s.inoue 20081003
					//if (selectedKojinData == null) {
					//	Data.errCase = JApplication.ERR_KIND_NOT_EXIST;
					//	setImportErrDigit(ErrorResultData,Data,i);
					//	ResultMessage += GetErrorMessage("[検査結果取込処理]該当者がいませんでした。処理を中断します。","検査結果データ:" + Data.kanaShimei);
					//	break;
					//}
					Data.uketukeNo =selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
						break;
					}

				} catch (Exception err) {
					ResultMessage += GetErrorMessage("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。",Data.kanaShimei);
					logger.error("[検査結果取込処理]個人情報データを取得する時にエラーが発生しました。");
					break;
				}
			}

			// 結果取込CSVファイルとDBの値の比較を行う。
			// その他テーブルの項目コード単位で処理を行う。
			// add s.inoue 2010/04/21
			String height = "";
			String weight = "";
			for (int j = 0; j < kojinData.size(); j++) {

					Hashtable<String,String> DatabaseItem = kojinData.get(j);

					if (Data.uketukeNo == null){
						Data.uketukeNo =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					}
					// 受付IDを比較する（一致：登録対象、不一致：エラー対象）
					String uketukeIdDB =DatabaseItem.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					String kensaDateDB =DatabaseItem.get(JApplication.COLUMN_NAME_KENSA_NENGAPI);
					String koumokuCdDB =DatabaseItem.get(JApplication.COLUMN_NAME_KOUMOKUCD);

					// データベースに登録(T_KENSAKEKKA_TOKUTEI)
					// キー情報(:受付ID,検査実施日)が変わったら登録処理を行う
					if(!JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(preuketukeId.toString()) &&
							!JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(prekensaDate.toString())){
						try{

							// 結果取込データ(属性情報)チェック処理
							if (checkKensaKekkaData(Data,ErrorResultData,i)){
								j++;continue;
							}

							// 既に健診結果データが存在した場合、警告メッセージを表示する。
							// 健診実施日,氏名カナ毎 結果登録するとデータが存在する為、更新処理のみ
							Data.koumokuCd= Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD));
							// 項目コードチェック
							if (checkKoumokuCD(Data,ErrorResultData,i)){
								j++;continue;
							}

							boolean existsKensaKekkaData = existsKensaKekkaData(Data);

							if (existsKensaKekkaData) {

								if (!retblnALL){
									// edit ver2 s.inoue 2009/05/22
									// M3328=確認,[%s]には、すでに結果データが存在します。登録した場合、
									// 以前に入力した結果データは消えてしまいます。登録してよろしいですか？
									String[] messageParams = {"受付番号:"+ Data.uketukeNo + "/" + "健診実施日:" + Data.jishiDate + "/" + "氏名:"+ Data.kanaShimei};
									RETURN_VALUE retValue = JErrorMessage.show("M3328", this, messageParams);
									// cancel時
									if (retValue == RETURN_VALUE.CANCEL) {
										try {
											// eidt s.inoue 2011/06/07
											// JApplication.kikanDatabase.rollback();
											JApplication.kikanDatabase.getMConnection().rollback();
										}catch (SQLException err) {}
										return;
									}else if (retValue == RETURN_VALUE.NO){
										preuketukeId=Long.valueOf(uketukeIdDB);
										prekensaDate=Integer.valueOf(kensaDateDB);
										j++;continue;
									}else if (retValue == RETURN_VALUE.ALL){
										retblnALL = true;
									}
								}
							}
							intregistCnt++;

							kensakekaTokuteiregister(Data);
							preuketukeId=Long.valueOf(uketukeIdDB);
							prekensaDate=Integer.valueOf(kensaDateDB);

						}catch(Exception ex){
							try {
								// eidt s.inoue 2011/06/07
								// JApplication.kikanDatabase.rollback();
								JApplication.kikanDatabase.getMConnection().rollback();
							} catch (SQLException err) {}
							ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);

							logger.error("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。");
							break;
						}
					}

					// キー情報が一致したら検査結果登録してあり正常
					// 「その他:1～22の項目(動的に変化する)」の登録
					Integer kensaKoumokuLoop = JApplication.CSV_KENSA_KOUMOKU_LOOP;
					int cntKoumoku =0;

					while (cntKoumoku < JApplication.CSV_KENSA_LOOP * JApplication.CSV_KENSA_KOUMOKU_LOOP) {

						if (insertRow.size() <= JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku )
						{
							cntKoumoku += kensaKoumokuLoop;break;
						}
						Data.koumokuCd = Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KOUMOKU_CD + cntKoumoku));

						if (Data.koumokuCd.equals("")){
							checkKoumokuCD(Data,ErrorResultData,i);
							cntKoumoku += kensaKoumokuLoop;continue;
						}

						try {
							Long resultCnt = kensakekaSonotaDao.selectByCountPrimaryKey(
									Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
							if (resultCnt < 0) {
								checkKoumokuCD(Data,ErrorResultData,i);
								cntKoumoku += kensaKoumokuLoop;
								continue;
							}

						} catch (Exception e) {
							logger.error("[検査結果取込処理]検査結果データその他データ取得時、エラーとなりました。");
							break;
						}

						if(JEraseSpace.EraceSpace(Data.uketukeNo.toString()).equals(JEraseSpace.EraceSpace(uketukeIdDB)) &&
							JEraseSpace.EraceSpace(Data.jishiDate.toString()).equals(JEraseSpace.EraceSpace(kensaDateDB)) &&
							 JEraseSpace.EraceSpace(Data.koumokuCd.toString()).equals(JEraseSpace.EraceSpace(koumokuCdDB)))
						{
							Data.jisiKbn =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_JISI_KBN + cntKoumoku));
							Data.kekkaTi =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI + cntKoumoku));
							// del 20080918 s.inoue
							//Data.ijyoKbn=Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_IJYO_KBN + cntKoumoku));
							//Data.kekkaTiKeitai =Reader.rmQuart(insertRow.get(JApplication.CSV_KENSA_KEKATI_KEITAI + cntKoumoku));

							// 結果取込データ(検査項目情報)
							if (checkKensaKekkaDataDetail(Data,ErrorResultData,i)){
								cntKoumoku += kensaKoumokuLoop;break;
							}

							// 健診結果その他登録処理
							try{
								System.out.println(Data.koumokuCd + " " + Data.kekkaTi);
								kensakekaSonotaregister(Data);

								// add s.inoue 2010/04/21
								// BMI自動計算 roop最後に処理
								if(Data.koumokuCd.equals("9N001000000000001")){
									height = Data.kekkaTi;
								}else if (Data.koumokuCd.equals("9N006000000000001")){
									weight = Data.kekkaTi;
								}
							}catch(Exception ex){
								// eidt s.inoue 2013/02/20
								// JApplication.kikanDatabase.rollback();
								try {
									JApplication.kikanDatabase.getMConnection().rollback();
								} catch (SQLException e) {
									logger.warn(e.getMessage());
								}
								logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。");
								ResultMessage += GetErrorMessage("[検査結果取込処理]検査結果データ特定テーブルに登録時、エラーが発生しました。",Data.kanaShimei);
								break;
							}
						}

						cntKoumoku += kensaKoumokuLoop;
					}
			}

			// add s.inoue 2010/04/21
			String bmi = calcBMI(height, weight);

			// edit s.inoue 2010/07/12
			if (!bmi.equals("")){
				String kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
				Data.koumokuCd = "9N011000000000001"; //BMI
				Data.kekkaTi = kensaKekka;
				updatekensakekaSonota(Data,insertRow);
			}
		}

		try {
			// commit
			// eidt s.inoue 2011/06/07
			// JApplication.kikanDatabase.Commit();
			JApplication.kikanDatabase.getMConnection().commit();

		} catch (SQLException err) {
			logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。" + JApplication.LINE_SEPARATOR + err.getMessage());
		}

		// アンマッチエラー一覧表示
		if(ErrorResultData.size() >= 1){
			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
					this,new JImportErrorResultFrameCtrl(ErrorResultData,this));

		}else if(intregistCnt == 0){
			JErrorMessage.show("M3325",this);

		// 処理中断のエラーメッセージを表示
		}else if(ResultMessage.equals("")){
			JErrorMessage.show("M3324",this);

		// 処理中断のエラーメッセージを表示
		}else{
			try
			{
				BufferedWriter file = new BufferedWriter(new FileWriter("Error.txt"));
				file.write(ResultMessage);
				file.close();

			}catch(Exception err){
				logger.error("[検査結果取込処理]検査項目情報登録時、エラーとなりました。" + JApplication.LINE_SEPARATOR + err.getMessage());
				JErrorMessage.show("M3323",this);
			}
			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(
					this,new JImportResultFrameCtrl(ResultMessage,this));
		}

		// add s.inoue 20081001
		this.m_guiStatus.setText("");

	}
	/*-------------------------------------orgine-----------------------------------------------*/

	/*-------------------------------------hl7取込-----------------------------------------------*/
	/**
	 * xml(HL7)の取り込み
	 * 注意
	 * プログラムでzip解凍している為、想定外のエラーに関しては全て
	 * エラー種別:error扱い
	 * @param FilePath 読み込むファイルのパス
	 */
	public void hl7Import(String filePath)
	{
		String zipAbsolutePath = "";
		String zipCommonSubPath = "";
		String zipSubPath = "";
		String dataSubPath = "";
		String dataDirPath = "";
		String zipPath ="";
		String[] zipfileArray =null;

		try {

			File zipFile = null;
			boolean zipProcess = false;
			Connection connection = JApplication.kikanDatabase.getMConnection();

			kojinDao = (TKojinDao) DaoFactory.createDao(connection,kojin);
			hokenjyaDao = (THokenjyaDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new THokenjya());
			// add s.inoue 2010/11/30
			shiharaiDao = (TShiharaiDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new TShiharai());
			tNayoseDao = (TNayoseDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(),new TNayose());
			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(connection,kensakekaTokutei);
			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(connection,kensakekaSonota);

			// edit s.inoue 2010/11/01
			validatedKojinData = new JKojinRegisterFrameData();

			// zipフォルダの場合※複数ファイルあり
			// 処理後、unzipしたフォルダは削除する
			if (filePath.endsWith(".zip") ||
					filePath.endsWith(".ZIP")){
				zipProcess = true;
			}

			// eidt s.inoue 2011/07/27
			// JApplication.kikanDatabase.Transaction();
			JApplication.kikanDatabase.getMConnection().setAutoCommit(true);

			if (zipProcess){
				JZipDecompresser za = new JZipDecompresser();
				zipFile = new File(filePath);

				zipPath = za.unzip(zipFile);
				System.out.println(zipPath);
				int fistStr = filePath.lastIndexOf(File.separator)+1;
				// zip分
				zipAbsolutePath = filePath.substring(0,fistStr-1);

				// dataフォルダ
				zipCommonSubPath = filePath.substring(fistStr,filePath.length() -4);
				zipSubPath = zipPath +File.separator+zipCommonSubPath +File.separator+ "DATA";

				// claimsフォルダ
				dataSubPath =zipPath +File.separator+zipCommonSubPath +File.separator+ "CLAIMS";

				// edit s.inoue 2010/11/30 ixファイル
				dataDirPath = zipPath + File.separator+zipCommonSubPath;

				// dataフォルダ内検索
				String fpath = zipAbsolutePath + File.separator + zipSubPath;
				File zipFiles = new File(fpath);
				zipfileArray = zipFiles.list();

				// claimsフォルダ内検索
				String cpath = zipAbsolutePath + File.separator + dataSubPath;
				File cFiles = new File(cpath);
				String[] cfileArray = cFiles.list();
				String claimsfileName = "";

				// ファイル命名規則
				// RootDirectory+ "DATA"+ SEPARATOR
				// ["h" :h or c][Souhumoto :送付元機関番号][CreateDate :作成日][AllocDirectoryNumber :変換した日の変換回数]
				// ["1" :固定][Utility.FillZero(ClinicalDocumentCount, 6)][".xml"]
				// Dataフォルダ対象
	            for(int i = 0; i < zipfileArray.length; i++) {

	                // 整合性により同名の場合処理を実施
		            for (int j = 0; j < cfileArray.length; j++) {
						String cfile = cfileArray[j].substring(1);
						if (cfile.equals(zipfileArray[i].substring(1))){
							System.out.println("請求区分取得用" + cfileArray[i]);
							claimsfileName = cfileArray[i];break;
						}
					}

					// 処理件数毎にDataを作成
					Data = new JImportErrorResultFrameData();

		            // data,claims
		            String datafileName = fpath + File.separator + zipfileArray[i];
		            claimsfileName = cpath + File.separator + claimsfileName;

		            // add s.inoue 2010/11/30
					// ixファイル読み込み(のみ)
					String ixPath = zipAbsolutePath + File.separator +dataDirPath + File.separator + "ix08_V08.xml";

	            	hl7filesRead(datafileName,claimsfileName,ixPath);
	                System.out.println(i+1 + "件目 " + zipfileArray[i] + "ファイル処理");
	            }

// move s.inoue 2010/12/01
//				// 解凍直下フォルダ削除
//				File zipFolder = new File(zipAbsolutePath +File.separator + zipPath);
//
//				// deletefolder
//				JFile.deleteDirectory(zipFolder,true);
//				System.out.println("一時zipフォルダ:" + zipFolder);
			}else{
				// 通常プロセス
				// 処理件数毎にDataを作成
				Data = new JImportErrorResultFrameData();

				hl7filesRead(filePath,null,null);
			}

			// add s.inoue 2010/10/27
			// アンマッチエラー一覧表示
			if(validatedKojinData.getErrorMessageCount() == 0){

				// eidt s.inoue 2011/07/27
				// JApplication.kikanDatabase.Commit();
				// JApplication.kikanDatabase.getMConnection().commit();

				// add s.inoue 2010/12/02
				DBYearAdjuster yAjuster = new DBYearAdjuster();
				// データベース切断
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.Shutdown();
					JApplication.kikanDatabase.getMConnection().close();
				} catch (SQLException ex) {
					JErrorMessage.show("M3340",this);
					logger.error(ex.getMessage());
				}

				yAjuster.callfixedNayose(JApplication.kikanNumber);

				try {
					JApplication.kikanDatabase = JConnection
						.ConnectKikanDatabase(JApplication.kikanNumber);
				} catch (SQLException ex) {
					JErrorMessage.show("M3340",this);
					logger.error(ex.getMessage());
				}

			}else if(validatedKojinData.getErrorMessageCount() >= 1){
                // エラー処理
            	String errMessage ="";
            	String zipfileName ="";

            	HashMap<String, String> errorMessage = validatedKojinData.getErrorMessage();
            	java.util.Set stKey = errorMessage.entrySet();
            	java.util.Iterator ite = stKey.iterator();
            	int iCount = 0;
            	while (ite.hasNext()) {
            		Object o = ite.next();

            	    java.util.Map.Entry ent = (java.util.Map.Entry)o;
            	    String key = (String)ent.getKey();
            	    String val = (String)ent.getValue();

            	    if (zipProcess){
            	    	if (!zipfileName.equals(zipfileArray[iCount])
            	    			&& (!key.equals("")))
            	    		errMessage = "ファイル名:" + zipfileArray[iCount] + "[改行]";
            	    	zipfileName = zipfileArray[iCount];
            	    }else if (iCount == 0){
            	    	// eidt s.inoue 2013/03/09
            	    	// errMessage = "●ファイル名:" + filePath.substring(filePath.lastIndexOf("\\")+2) + "[改行]";
            	    	errMessage = "●ファイル名:" + filePath.substring(filePath.lastIndexOf(JApplication.FILE_SEPARATOR)+2) + "[改行]";
            	    }

            	    errMessage+="・" + val + "[改行]";
            	    iCount++;
				}

            	String[] params = { errMessage };
				JErrorMessage.show("M3332", this, params);

				// eidt s.inoue 2011/06/07
//		       	try{
//	        		JApplication.kikanDatabase.rollback();
//	        	}catch(Exception ex){}
				try {
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException ex) {
					logger.warn(ex.getMessage());
				}
			}
		} catch (Exception e) {
        	// 失敗→rollback
        	try{
        		// eidt s.inoue 2011/06/07
        		// JApplication.kikanDatabase.rollback();
        		JApplication.kikanDatabase.getMConnection().rollback();
        	}catch(Exception ex){}

			logger.error("[HL7取込処理]機関FDBへの接続処理に失敗しました。");
			JErrorMessage.show("M3331",this);
		} finally {
			// move s.inoue 2010/12/01 エラー発生時考慮
			// 解凍直下フォルダ削除
			File zipFolder = new File(zipAbsolutePath +File.separator + zipPath);
			// deletefolder
			JFile.deleteDirectory(zipFolder,true);
			 validatedKojinData.clearColumnData();
		}
	}

	/*
	 * hl7(data)ファイル群読込
	 */
	private void hl7filesRead(String datafilePath,String claimsfilePath,String ixPath){

		try{

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// edit s.inoue 2010/11/30
			// Document doc = builder.parse(datafilePath);			// XML文書をパース
			Document doc = builder.parse(new File(datafilePath));			// XML文書をパース

			Document claimsdoc = null;
			if (claimsfilePath != null)
				claimsdoc = builder.parse(new File(claimsfilePath)); // claims用文書読み込み

			// ixファイル用処理
			Document ixdoc = null;
			if (ixPath != null)
				ixdoc = builder.parse(new File(ixPath)); 			// claims用文書読み込み

			// 前準備
			koumokuCodeMap = new HashMap<String, String>();
			koumokuCodeElseMap = new HashMap<String, String>();

			setImportKenshinItemList();						// koumokuCDをセット

	        // 子要素を取得
	        readXMLsearchNode(doc,claimsdoc,ixdoc);

		}catch(Exception ex){
			logger.error(ex.getMessage());
			System.out.println("error:" + ex.getMessage());
		}
	}

	/*
	 * ixファイルよりデータ(代行機関番号)取得
	 */
	private boolean getixXMLsearch(Document ixDoc) {

		boolean retflg = true;
		Element root = ixDoc.getDocumentElement();
		System.out.println("ルート：" + root.getTagName());
		Node child=root.getFirstChild();

		while (child!=null) {
			String nodeNm = child.getNodeName();

			// 実施機関番号
			if (nodeNm.equals("sender")){
				Node claimeChild=child.getFirstChild().getNextSibling();
				String claimeChildnodeNm = claimeChild.getNodeName();
				if (claimeChildnodeNm.equals("id")){
					String extensionVal = getItemAttribute(claimeChild,"extension");
					// 機関番号比較
					if (!JApplication.kikanNumber.equals(extensionVal)){
						retflg =false;break;
					}
				}
			// 代行機関番号
			}else if (nodeNm.equals("receiver")){
				Node claimeChild=child.getFirstChild().getNextSibling();
				String claimeChildnodeNm = claimeChild.getNodeName();
				if (claimeChildnodeNm.equals("id")){
					String extensionVal = getItemAttribute(claimeChild,"extension");
					validatedKojinData.setDaikouNumber(extensionVal);
					break;
				}
			}
			child = child.getNextSibling();
		}
		return retflg;
	}

	/*
	 * claimsファイルよりデータ取得
	 */
	private void getClaimsXMLsearch(Document claimsDoc) {
		Element root = claimsDoc.getDocumentElement();
		System.out.println("ルート：" + root.getTagName());
		Node child=root.getFirstChild();

// del s.inoue 2010/11/17
		/* 窓口負担（基本的な健診） */
		validatedKojinData.setMadoguchiKihonSyubetu(0);
		validatedKojinData.setMadoguchiKihon("000000",true,true);
		/* 窓口負担（詳細な健診） */
		validatedKojinData.setMadoguchiSyousaiSyubetu(0);
		validatedKojinData.setMadoguchiSyousai("000000",true,true);
		/* 窓口負担（追加の健診） */
		validatedKojinData.setMadoguchiTsuikaSyubetu(0);
		validatedKojinData.setMadoguchiTsuika("000000",true,true);
		/* 窓口負担（人間ドック） */
		validatedKojinData.setMadoguchiDockSyubetu(0);
		validatedKojinData.setMadoguchiDock("000000",true,true);
		// 保険者上限は節制しなくて良い

		while (child!=null) {

			String nodeNm = child.getNodeName();

			if (nodeNm.equals("checkupCard")){
				// 特定健診受診券情報
				Node claimeChild=child.getFirstChild();
				while (claimeChild!=null) {
					// 請求区分取得
					String claimeNodeNm = claimeChild.getNodeName();

					// nodeでなければ次の要素へ移動
					if (claimeChild.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE){
						claimeChild = claimeChild.getNextSibling();
						continue;
					}
					// ↓↓↓↓↓窓口負担別処理↓↓↓↓↓
					// ノード取得処理
					Node attrCode = null;
					NamedNodeMap attrsCode = null;
					if (claimeNodeNm.equals("chargeTypeBasic") ||
							claimeNodeNm.equals("chargeTypeDetail") ||
								claimeNodeNm.equals("chargeTypeOther")){
						attrsCode = claimeChild.getAttributes();
			        	attrCode = attrsCode.getNamedItem("code");
			        	if (attrCode == null) return;
					}else if (claimeNodeNm.equals("chargeTypeHumanDryDock")){
						// add s.inoue 2010/11/25
						claimeChild = claimeChild.getFirstChild().getNextSibling();
						attrsCode = claimeChild.getAttributes();
			        	attrCode = attrsCode.getNamedItem("code");
					}

					// 窓口負担種別(基本的な健診)
					if (claimeNodeNm.equals("chargeTypeBasic")){
						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());

						// edit s.inoue 2010/11/24
						String syubetuCode = attrCode.getNodeValue();
						if (syubetuCode.equals("1")){
							// edit s.inoue 2010/12/08
							validatedKojinData.setMadoguchiKihonSyubetu(1);
							claimeChild = claimeChild.getNextSibling();
							continue;
						}
						validatedKojinData.setMadoguchiKihonSyubetu(madogutiSyubetu);

						// add s.inoue 2010/11/17
						claimeChild = claimeChild.getFirstChild().getNextSibling();
						if (claimeChild.getNodeName().equals("amount") ||
								claimeChild.getNodeName().equals("rate")){
							attrsCode = claimeChild.getAttributes();
				        	attrCode = attrsCode.getNamedItem("value");
						}
				        	if (attrCode == null) return;

				        	if (syubetuCode.equals("3")){
				        		validatedKojinData.setMadoguchiKihon(attrCode.getNodeValue(),false,true);
				        	}else if (syubetuCode.equals("4")){
				        		validatedKojinData.setHokenjyaJougenKihon(attrCode.getNodeValue(),false);
				        		validatedKojinData.setMadoguchiKihonSyubetu(1);
				        	}else{
				        		validatedKojinData.setMadoguchiKihon(attrCode.getNodeValue(), false,false);
				        	}

							// edit s.inoue 2010/11/24
							claimeChild = claimeChild.getParentNode();
					// 窓口負担種別(詳細な健診)
					}else if (claimeNodeNm.equals("chargeTypeDetail")){
						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());

						// edit s.inoue 2010/11/24
						String syubetuCode = attrCode.getNodeValue();
						if (syubetuCode.equals("1")){
							// edit s.inoue 2010/12/08
							validatedKojinData.setMadoguchiSyousaiSyubetu(1);
							claimeChild = claimeChild.getNextSibling();
							continue;
						}
						validatedKojinData.setMadoguchiSyousaiSyubetu(madogutiSyubetu);

						// add s.inoue 2010/11/17
						claimeChild = claimeChild.getFirstChild().getNextSibling();
						if (claimeChild.getNodeName().equals("amount") ||
								claimeChild.getNodeName().equals("rate")){
							attrsCode = claimeChild.getAttributes();
				        	attrCode = attrsCode.getNamedItem("value");
						}
				        	if (attrCode == null) return;
				        	if (syubetuCode.equals("3")){
				        		validatedKojinData.setMadoguchiSyousai(attrCode.getNodeValue(),false,true);
				        	}else if (syubetuCode.equals("4")){
				        		validatedKojinData.setHokenjyaJougenSyousai(attrCode.getNodeValue(),false);
				        		validatedKojinData.setMadoguchiSyousaiSyubetu(1);
				        	}else{
				        		validatedKojinData.setMadoguchiSyousai(attrCode.getNodeValue(), false,false);
				        	}

				        	// edit s.inoue 2010/11/24
							claimeChild = claimeChild.getParentNode();

					// 窓口負担種別(追加健診)
					}else if (claimeNodeNm.equals("chargeTypeOther")){
						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());
						// edit s.inoue 2010/11/24
						String syubetuCode = attrCode.getNodeValue();
						if (syubetuCode.equals("1")){
							// edit s.inoue 2010/12/08
							validatedKojinData.setMadoguchiTsuikaSyubetu(1);
							claimeChild = claimeChild.getNextSibling();
							continue;
						}
						validatedKojinData.setMadoguchiTsuikaSyubetu(madogutiSyubetu);
						// add s.inoue 2010/11/17
						claimeChild = claimeChild.getFirstChild().getNextSibling();
						if (claimeChild.getNodeName().equals("amount") ||
								claimeChild.getNodeName().equals("rate")){
							attrsCode = claimeChild.getAttributes();
				        	attrCode = attrsCode.getNamedItem("value");
						}else if (claimeChild.getNodeName().equals("copayment")){
							// add s.inoue 2010/11/25
							attrsCode = claimeChild.getAttributes();
				        	attrCode = attrsCode.getNamedItem("code");
						}
				        	if (attrCode == null) return;
				        	if (syubetuCode.equals("3")){
				        		validatedKojinData.setMadoguchiTsuika(attrCode.getNodeValue(),false,true);
				        	}else if (syubetuCode.equals("4")){
				        		validatedKojinData.setHokenjyaJougenTsuika(attrCode.getNodeValue(),false);
				        		validatedKojinData.setMadoguchiTsuikaSyubetu(1);
				        	}else{
				        		validatedKojinData.setMadoguchiTsuika(attrCode.getNodeValue(), false,false);
				        	}

							// edit s.inoue 2010/11/24
							claimeChild = claimeChild.getParentNode();

					// 窓口負担種別(人間ドック)
					}else if (claimeNodeNm.equals("chargeTypeHumanDryDock")){
						int madogutiSyubetu = Integer.parseInt(attrCode.getNodeValue());
						// edit s.inoue 2010/11/24
						String syubetuCode = attrCode.getNodeValue();
						if (syubetuCode.equals("1")){
							// edit s.inoue 2010/12/08
							validatedKojinData.setMadoguchiDockSyubetu(1);
							claimeChild = claimeChild.getNextSibling();
							continue;
						}
						validatedKojinData.setMadoguchiDockSyubetu(madogutiSyubetu);
						// add s.inoue 2010/11/17
						claimeChild = claimeChild.getFirstChild().getNextSibling();
						if (claimeChild.getNodeName().equals("amount") ||
								claimeChild.getNodeName().equals("rate")){
							attrsCode = claimeChild.getAttributes();
				        	attrCode = attrsCode.getNamedItem("value");
						}
				        	if (attrCode == null) return;

				        	if (syubetuCode.equals("3")){
				        		validatedKojinData.setMadoguchiDock(attrCode.getNodeValue(),false,true);
				        	}else if (syubetuCode.equals("4")){
				        		validatedKojinData.setHokenjyaJougenDoc(attrCode.getNodeValue(),false);
				        		validatedKojinData.setMadoguchiDockSyubetu(1);
				        	}else{
				        		validatedKojinData.setMadoguchiDock(attrCode.getNodeValue(), false,false);
				        	}

							// edit s.inoue 2010/11/24
							claimeChild = claimeChild.getParentNode();
					}
					claimeChild = claimeChild.getNextSibling();
				}

			}else if (nodeNm.equals("settlement")){
				Node claimeChild=child.getFirstChild();

				// settlement配下探索
				while (claimeChild!=null) {
					// 請求区分取得
					String claimeNodeNm = claimeChild.getNodeName();
					Node attrCode = null;

					// nodeでなければ次の要素へ移動
					if (claimeChild.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE){
						claimeChild = claimeChild.getNextSibling();
						continue;
					}

		        	// 決済情報
					NamedNodeMap attrsCode = claimeChild.getAttributes();

					if (claimeNodeNm.equals("unitPriceBasic") ||
							claimeNodeNm.equals("unitPriceDetail") ||
								claimeNodeNm.equals("unitPriceOther") ||
						claimeNodeNm.equals("paymentForBasic") ||
							claimeNodeNm.equals("paymentForDetail") ||
								claimeNodeNm.equals("paymentForOther")){

						Node claimePriceChild = claimeChild.getFirstChild();
						while(claimePriceChild != null){
							if (claimePriceChild.getNodeName().equals("amount"))
								break;
							claimePriceChild = claimePriceChild.getNextSibling();
						}
						NamedNodeMap attrsClaimePriceCode = claimePriceChild.getAttributes();
			        	attrCode = attrsClaimePriceCode.getNamedItem("value");
			        	if (attrCode == null) return;
					}

					if (claimeNodeNm.equals("claimType")){
			        	// 決済情報
						// NamedNodeMap attrsCode = claimeChild.getAttributes();
						attrCode = attrsCode.getNamedItem("code");
			        	if (attrCode == null) return;
			        	validatedKenshinData.setSeikyuKubunCode(attrCode.getNodeValue(),true);
			        	System.out.println("attrCodeNode:" + attrCode.getNodeValue());
					}

					// add s.inoue 2010/11/25
					if (claimeNodeNm.equals("paymentByOtherProgram")){
						attrCode = attrsCode.getNamedItem("value");
						validatedKojinData.setMadoguchiSonota(attrCode.getNodeValue(),true);
					}
// del s.inoue 2010/11/24
//				    // 単価（基本的な健診）
//					}else if (claimeNodeNm.equals("unitPriceBasic")){
//						validatedKojinData.setHokenjyaJougenKihon(attrCode.getNodeValue(),true);
//			    	// 単価（詳細な健診）
//			    	}else if (claimeNodeNm.equals("unitPriceDetail")){
//			    		validatedKojinData.setHokenjyaJougenSyousai(attrCode.getNodeValue(),true);
//			    	// 単価（追加健診）
//			    	}else if (claimeNodeNm.equals("unitPriceOther")){
//			    		validatedKojinData.setHokenjyaJougenTsuika(attrCode.getNodeValue(),true);
//				    // 単価（人間ドック）
//			    	}else if (claimeNodeNm.equals("unitPriceOther")){
//			    		validatedKojinData.setHokenjyaJougenDoc(attrCode.getNodeValue(),true);
//			    	// 窓口負担金額(基本的な健診)
//			    	}else if (claimeNodeNm.equals("paymentForBasic")){
//			    		validatedKojinData.setMadoguchiKihon(attrCode.getNodeValue(),true);
//			    	// 窓口負担金額（詳細な健診）
//			    	}else if (claimeNodeNm.equals("paymentForDetail")){
//			    		validatedKojinData.setMadoguchiSyousai(attrCode.getNodeValue(),true);
//			    	// 窓口負担金額（追加健診又は人間ドック）
//			    	}else if (claimeNodeNm.equals("paymentForOther")){
//			    		validatedKojinData.setMadoguchiTsuika(attrCode.getNodeValue(),true);
//			    	}

					claimeChild = claimeChild.getNextSibling();
				}
			}
			child = child.getNextSibling();
		}
	}

	/*
	 *  XMLReader(jaxParser)メイン読み込み処理
	 */
    private void readXMLsearchNode(Document doc,Document claimsDoc,Document ixDoc){
		try{

			// add s.inoue 2010/11/30
			if (ixDoc != null){
				if (!getixXMLsearch(ixDoc)){
					JErrorMessage.show("M3339", null);
					return;
				}
			}

			// claimsDocを先に読込
			if (claimsDoc != null)
				getClaimsXMLsearch(claimsDoc);

			// ルート下のメインノードでループ
			Element root = doc.getDocumentElement();
			System.out.println("ルート：" + root.getTagName());
			// 子ノードを取得
			Node child=root.getFirstChild();

			// 項目ノードでループ
			while (child!=null) {
				System.out.println("項目：" + child.getNodeName());
				// 項目ノード下で処理（改行は次）
				if (child.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
					traceAllChildNodes(child);
				// Validation
				if (!getElementValueByTags(child)){
					JErrorMessage.show("M3337", null);
					return;
				}
				// nextChild
				child = child.getNextSibling();
			}

			// DBへ登録
			registerXmlData();

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			System.out.println(ex.getMessage());
		}
    }

////ノード最小単位探索↓/////////////////////////////////////////////
    /* childノード追跡 */
    private static void traceAllChildNodes(Node node) {

        Node child=node.getFirstChild();	 // 子ノードを取得

        while (child!=null) {

        	// edit s.inoue 2010/11/01
//        	if (child.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE){
//        		child = child.getNextSibling();
//        		continue;
//        	}

            traceAllChildNodes(child);       // さらに子ノードをたどる
            child = child.getNextSibling();  // 兄弟ノードを取得

            /* 親項目毎にループを行う				 */
            /* 項目下には重複するタグがある為、注意する */
            if (child != null){
        		String nodeval ="";

        		// 結果項目処理対応
        		setNodeItemAttribute(child);
       			nodeval = getAttributeItemValue(child);
        		System.out.println("名前:" + child.getNodeName() + " 値:" + nodeval);
            }
        }
    }

    /* 結果値取得-親タグ別に処理を分岐させる
     * codeタグで17桁の場合、健診項目と見なす
     */
    private static void setNodeItemAttribute(Node node){

    	String nodeNm = node.getNodeName();

    	/* 健診項目コード妥当性チェック */
    	if (nodeNm.equals("code")){
    		// 健診項目リスト取得
    		List kenshinItem = new ArrayList<String>();
        	kenshinItem = Arrays.asList(importKenshinItem);

        	// リスト突き合わせ code属性取得
        	NamedNodeMap attrsCode = node.getAttributes();
        	if (attrsCode==null) return;
        	Node attrCode = attrsCode.getNamedItem("code");
        	if (attrCode == null) return;

        	// 17桁※値を持たないcodeタグ回避
        	if (attrCode.getNodeValue().toString().length() == 17){
	        	// code属性を基にvalueタグを取得し、nextにセットする
	        	String itemValue = "";
				// pnode
				Node pnode =node.getParentNode();

	        	// nodeはnullの場合、未実施の可能性
				while (!node.getNodeName().equals("value")) {
					node = node.getNextSibling();
					if (node == null){
						node = pnode;
						break;
					}
				}

	        	NamedNodeMap attrs = node.getAttributes();

	        	// 属性別に結果値を取得する
	        	// edit s.inoue 2010/10/22
	        	// Node attrValue = attrs.getNamedItem("code");
	        	// if (attrValue != null)
	        	// 	itemValue = attrValue.getNodeValue();

				//実施区分： 「測定不能」→nullFlavor
	        	boolean jishiSokuteifunou = false;
				String nullFlg = getItemAttribute(node, "nullFlavor");
				if (nullFlg != null){
					if(nullFlg.equals("NI")){
						jishiSokuteifunou = true;
					}
				}

				// 実施区分：「未実施」 → value属性が出現しない
				else if (pnode.getNodeName().equals("entry") ||
						pnode.getNodeName().equals("observation")){
					if (pnode.getNodeName().equals("observation")){
						String negationIndFlg = getItemAttribute(pnode, "negationInd");
						if (negationIndFlg != null){
							if(negationIndFlg.equals("true")){
								 // node = node.getParentNode().getNextSibling();
					      		// 子タグidへ移動
								while (!node.getNodeName().equals("code")) {
									if (node.getFirstChild()!=null){
										node = node.getFirstChild().getNextSibling();
									}else{
										node = node.getNextSibling();
									}
								}
								String koumokuCode = getItemAttribute(node, "code");
								koumokuCodeElseMap.put(koumokuCode,"0");
								System.out.println("未実施code:" + koumokuCode);
								return;
							}
						}
					}
				    node = node.getFirstChild();
				}

				// move s.inoue 2013/04/04 ver2.0.3
	        	Node attrValue = null;
	        	Node type = attrs.getNamedItem("xsi:type");

	        	// add s.inoue 2013/04/05 ver2.0.3
	        	// CD/PQの順序判定処理が必要
	        	boolean PQCDflg = false;
				if(type.getNodeValue().equals("CD") ||
					 type.getNodeValue().equals("PQ")){

					Node chnode = pnode.cloneNode(true);
					Node gchildren = chnode.getFirstChild();

					while (!gchildren.getNodeName().equals("value")) {
						gchildren = gchildren.getNextSibling();
						if (gchildren == null)
							break;
					}
					boolean PQflg = false;
					boolean CDflg = false;
					Node PQVal = null;
					while (gchildren != null) {
						System.out.println(gchildren.getNodeValue());

						if (gchildren.getNodeName().equals("value")){
							NamedNodeMap gcattrs = gchildren.getAttributes();
							Node gctype = gcattrs.getNamedItem("xsi:type");
							if (gctype.getNodeValue().equals("PQ")){
								PQflg = true;
								if (gcattrs.getNamedItem("value") != null)
									PQVal = gcattrs.getNamedItem("value");
							}else if (gctype.getNodeValue().equals("CD")){
								CDflg = true;
							}
							System.out.println("Name: " + gctype.getNodeValue());
						}
						gchildren = gchildren.getNextSibling();

						if (PQflg && CDflg){
							PQCDflg = true;
							attrValue = PQVal;
						}

					}
				}

	        	// type別処理
// move s.inoue 2013/04/04 ver2.0.3
//	        	Node attrValue = null;
//	        	Node type = attrs.getNamedItem("xsi:type");
				if(type.getNodeValue().equals("CO") ||
						type.getNodeValue().equals("CD")){
					// add s.inoue 2013/04/05 ver2.0.3
					if (!PQCDflg)
						attrValue = attrs.getNamedItem("code");
				}else if(type.getNodeValue().equals("PQ")){
					// add s.inoue 2013/04/05 ver2.0.3
					if (!PQCDflg)
						attrValue = attrs.getNamedItem("value");
				// edit s.inoue 2010/10/22
				}else if(type.getNodeValue().equals("ST")){
					itemValue = node.getTextContent();
				}

	        	if (attrValue != null)
	        	 	itemValue = attrValue.getNodeValue();

	        	// 全健診項目との突き合わせ
	        	if (importKenshinItem.contains(attrCode.getTextContent())){
	        		if (jishiSokuteifunou){
	        			koumokuCodeElseMap.put(attrCode.getNodeValue(), "2");
	        		}else{
	        			koumokuCodeMap.put(attrCode.getNodeValue(),itemValue);
	        			System.out.println("code:" + attrCode + "値:" +itemValue);
	        		}
	        		// 例外)医師の氏名
	        		if (attrCode.getTextContent().equals("9N511000000000049")){
	        			node = node.getParentNode().getNextSibling();
	        			while (!node.getNodeName().equals("assignedPerson")) {
	        				if (node.getFirstChild()!=null){
	        					node = node.getFirstChild();
	        				}else{
	        					node = node.getNextSibling();
	        				}
	        				if (node == null)break;
	        			}
	        			if (node != null){
		        			node = node.getFirstChild().getNextSibling();
		        			if (node.getNodeName().equals("name")){
		        				koumokuCodeMap.put("9N516000000000049",node.getTextContent());
		        			}
	        			}
	        		}
	        	}
        	}
    	}
    }

    /* 親ノードよりtagを認識させる */
    private static boolean getElementValueByTags(Node node){
    	Node parentNode = node.getParentNode();

    	String HL7_TAGS_RECORDTARGET = "recordTarget";
    	String HL7_TAGS_AUTHOR = "author";
    	String HL7_TAGS_CUSTODIAN = "custodian";
    	String HL7_TAGS_PARTICIPANT = "participant";
    	String HL7_TAGS_DOCUMENTATIONOF = "documentationOf";
    	String HL7_TAGS_COMPONENT = "component";
    	String HL7_COLUMN_KENSINDATE ="effectiveTime";
    	String HL7_COLUMN_KENSINKOUMOKU ="code";

    	// OID対応
    	String OID_HOKENNUM = "1.2.392.200119.6.101";
    	String OID_HIHOKENSHA_KIGO = "1.2.392.200119.6.204";
    	String OID_HIHOKENSHA_NUM = "1.2.392.200119.6.205";
    	String OID_SEX = "1.2.392.200119.6.1104";
    	String OID_JYUSINKEN_NUM = "1.2.392.200119.6.209";
    	String OID_JISIKIKAN_NUM = "1.2.392.200119.6.102";

		String nodeVal = node.getNodeName();

    	/* recordTargetタグ[個人情報] idタグはroot属性 */
    	if (node.getNodeName().equals(HL7_TAGS_RECORDTARGET)){

    		// 子タグidへ移動
			while (!node.getNodeName().equals("id")) {
				if (node.getFirstChild()!=null){
					node = node.getFirstChild().getNextSibling();
				}else{
					node = node.getNextSibling();
				}
				if (node == null)return false;
			}

			// id,addr,postalCode,name,birthTimeタグ処理
			while (node!=null) {

				System.out.println("→ " + node.getNodeName());
	    		if (node.getNodeName().equals("id")){
	    			String attrNm = getItemAttribute(node,"root");
	    			// attrよりOID取得
	    			String extensionVal = getItemAttribute(node,"extension");
	    			if (attrNm.equals(OID_HOKENNUM)){
	    				validatedKojinData.setHokenjyaNumber(extensionVal,true);
	    			}else if (attrNm.equals(OID_HIHOKENSHA_KIGO)){
	    				validatedKojinData.setHihokenjyaCode(extensionVal,true);
	    				validatedKenshinData.setHihokenjyaCode(extensionVal,true);
	    			}else if (attrNm.equals(OID_HIHOKENSHA_NUM)){
	    				validatedKojinData.setHihokenjyaNumber(extensionVal,true);
	    				validatedKenshinData.setHihokenjyaNumber(extensionVal,true);
	    			}
	    		}else if(node.getNodeName().equals("addr")){
	    			Node fnode = node.getFirstChild();
	    			// eidt s.inoue 2011/07/26
	    			// 内容が後置の場合
	    			if (fnode.getNodeValue() == null){
	    				validatedKojinData.setAddress(fnode.getNextSibling().getNodeValue(),true);
	    				if (fnode.getNodeName().equals("postalCode"))
	    					validatedKojinData.setZipcode(fnode.getTextContent(),true);
	    			// 内容が改行あり後置の場合
	    			}else if (fnode.getNodeValue().indexOf("\n") >= 0){
	    				validatedKojinData.setAddress(fnode.getNextSibling().getNextSibling().getTextContent(),true);
	    				fnode = fnode.getNextSibling();
	    				if (fnode.getNodeName().equals("postalCode"))
	    					validatedKojinData.setZipcode(fnode.getTextContent(),true);
	    			}else{
		    			// 内容が前置の場合
		    			validatedKojinData.setAddress(fnode.getNodeValue(),true);
		    			fnode = fnode.getNextSibling();
		    			if (fnode.getNodeName().equals("postalCode"))
		    				validatedKojinData.setZipcode(fnode.getTextContent(),true);
	    			}
	    		}else if (node.getNodeName().equals("patient")){

	    			Node fnode = node.getFirstChild();

	    			while (!fnode.getNodeName().equals("name")){
	    				fnode = fnode.getNextSibling();
	    				if (fnode == null)return false;
	    			}
	    			if (fnode.getNodeName().equals("name"))
	    				validatedKojinData.setNameKana(fnode.getTextContent(),true);

	    			while (!fnode.getNodeName().equals("administrativeGenderCode")) {
	    				fnode = fnode.getNextSibling();
	    				if (fnode == null)return false;
	    			}
	    			if (fnode.getNodeName().equals("administrativeGenderCode")){
	    				validatedKojinData.setSex(getItemAttribute(fnode, "code"),true);
	    				if (fnode == null)return false;
	    			}
	    			while (!fnode.getNodeName().equals("birthTime")) {
	    				fnode = fnode.getNextSibling();
	    				if (fnode == null)return false;
	    			}
	    			if(fnode.getNodeName().equals("birthTime"))
	    				validatedKojinData.setBirthday(getItemAttribute(fnode, "value"),true);
	    		}
				node = node.getNextSibling();
			}
    	}

    	/* authorタグ → 省略 */
    	/* custodianタグ → 省略 */
    	/* participantタグ [個人情報(受診券情報)] */
    	else if(node.getNodeName().equals(HL7_TAGS_PARTICIPANT)){

    		System.out.println("→→ " + node.getNodeName());
    		Node childTime = node.cloneNode(true);

    		// timeタグ{1}※多重度{}
    		// highタグ{0 or 1}※多重度{}
			while (!childTime.getNodeName().equals("time")) {
				if (childTime.getFirstChild()!=null){
					childTime = childTime.getFirstChild().getNextSibling();
				}else{
					childTime = childTime.getNextSibling();
				}
				if (childTime == null)return false;
			}
			// highタグ処理{0 or 1}
    		if(childTime.getNodeName().equals("time")){
    			// highタグの抽出
    			Node timeValue = childTime.cloneNode(true);
    			while (!timeValue.getNodeName().equals("high")) {
    				if (timeValue.getFirstChild()!=null){
    					timeValue = timeValue.getFirstChild();
    				}else{
    					timeValue = timeValue.getNextSibling();
    				}
    				if (timeValue == null)break;
    			}

    			if (timeValue != null){
    				validatedKojinData.setLimitDate(getItemAttribute(timeValue,"value"),true);
    			}else{
    				// nullの場合、{0}のケースで次の要素へ
    				childTime = childTime.getNextSibling();
    			}
    		}

    		/* associatedEntityの子{id}取得 */
    		// edit s.inoue 2010/11/04
    		// childTime.getParentNode().getNextSibling();
			Node childEntity = childTime.getNextSibling();

       		// associatedEntity{1}タグの子id{1}
			while (!childEntity.getNodeName().equals("associatedEntity")) {
				childEntity = childEntity.getNextSibling();
				if (childEntity == null)return false;
			}
			// id
			while (!childEntity.getNodeName().equals("id")) {
				if (childEntity.getFirstChild()!=null){
					childEntity = childEntity.getFirstChild().getNextSibling();
				}else{
					childEntity = childEntity.getNextSibling();
				}
				if (childEntity == null)return false;
			}

    		if(childEntity.getNodeName().equals("id")){
    			String attrNm = getItemAttribute(childEntity,"root");
    			// edit s.inoue 2010/12/01
    			attrNm = attrNm.substring(0,attrNm.lastIndexOf("."));
    			if (attrNm.equals(OID_JYUSINKEN_NUM)){
    				validatedKojinData.setJyushinkenID(getItemAttribute(childEntity,"extension"),true);
    			}
    		}
    	}

    	/* documentationOfタグ[健診実施日] */
    	else if (node.getNodeName().equals(HL7_TAGS_DOCUMENTATIONOF)){

       		// 子タグidへ移動
			while (!node.getNodeName().equals("effectiveTime")) {
				if (node.getFirstChild()!=null){
					node = node.getFirstChild().getNextSibling();
				}else{
					node = node.getNextSibling();
				}
				if (node == null)return false;
			}

    		System.out.println("→→ " + node.getNodeName());
    		validatedKenshinData.setKensaJissiDate(getItemAttribute(node, "value"),true);

    		// edit s.inoue 2010/08/30
       		// 子タグidへ移動
			while (!node.getNodeName().equals("id")) {
				if (node.getFirstChild()!=null){
					node = node.getFirstChild().getNextSibling();
				}else{
					node = node.getNextSibling();
				}
				if (node == null)return false;
			}

    		if(nodeVal.equals("id")){
    			String attrNm = getItemAttribute(node,"root");
    			if (attrNm.equals(OID_JISIKIKAN_NUM)){
    				kikanNumber = getItemAttribute(node,"extension");
    			}
    		}
    	}

    	/* componentタグ [検査結果] */
    	// ※健診項目コードにより判別
    	else if (node.getNodeName().equals(HL7_TAGS_COMPONENT)){
    		System.out.println("→→→→ " + node.getNodeName());

       		// 子タグidへ移動
			while (!node.getNodeName().equals("code")) {
				if (node.getFirstChild()!=null){
					node = node.getFirstChild().getNextSibling();
				}else{
					node = node.getNextSibling();
				}
				if (node == null)break;
			}

			/* 健診項目<code>タグが存在する間ループする */
			Node fnode = node;
			while(fnode != null){
				System.out.println("node名:" + fnode.getNodeName());

//				// edit s.inoue 2010/11/02
//				// 子ノードタグの場合、別処理
//				if (fnode.getNodeName().equals("entry") ||
//					fnode.getNodeName().equals("observation")){
//					// 実施区分：「未実施:0」 → value属性が出現しない
//					if (fnode.getNodeName().equals("observation")){
//						String negationIndFlg = getItemAttribute(fnode, "negationInd");
//						if (negationIndFlg != null){
//							if(negationIndFlg.equals("true")){
//								koumokuCodeElseMap.put("", "0");
//							}
//						}
//					}
//					node = node.getFirstChild();
//				}

				// 項目コード
				String koumokuCode = getItemAttribute(fnode, "code");

	    		// 健診項目コード=codeタグの場合処理を行う
	    		if (fnode.getNodeName().equals(HL7_COLUMN_KENSINKOUMOKU)){
					// 健診項目=17桁のものを対象とする
		    		if (koumokuCode.toString().length() == 17){
	    	        	// code属性を基にvalueタグを取得し、nextにセットする
	    				while (!fnode.getNodeName().equals("value")) {
	    					fnode = fnode.getNextSibling();
	    					if (fnode == null)return false;
	    				}

	       				// 健診項目種別
	    				String itemVal ="";
	    				String type = getItemAttribute(fnode,"xsi:type");
	    				if(type.equals("CO") ||
	    						type.equals("CD")){
	    					itemVal =getItemAttribute(fnode,"code");
	    				}else if(type.equals("PQ")){
	    					// type.equals("ST")
	    					itemVal =getItemAttribute(fnode,"value");

	    				// edit s.inoue 2010/10/22
			    		}else if(type.equals("ST")){
			    			itemVal = node.getTextContent();
			    		}

	    				System.out.println("type:" + type + " value:" + itemVal);

	    				// edit s.inoue 2010/08/26
	    				// 健診項目別設定
	    				// validatedKenshinData.setKensaJissiDate(itemVal);
		    		}
				}
	    		fnode = fnode.getNextSibling();
    		}
    	}
    	return true;
    }

    /* xmlAttributeルート属性取得 -  */
    private static String getAttributeItemValue(Node node){

    	String attributeItemText = "";
		NamedNodeMap attrs = node.getAttributes();  						 		// NamedNodeMapの取得

		if (attrs!=null) {
			// value属性
			Node attr = attrs.getNamedItem("value");  						 		// value属性ノード
			if (attr != null)
				attributeItemText = attr.getNodeValue();				 			// value属性の値

			// code属性
			Node attrCode = attrs.getNamedItem("code");  							// code属性ノード
			if (attrCode != null)
				attributeItemText = attrCode.getNodeValue();				 		// code属性の値

			// extension属性
			Node attrExtension = attrs.getNamedItem("extension");  					 // Extension属性ノード
			if (attrExtension != null)
				attributeItemText = attrExtension.getNodeValue();					// Extension属性の値

			// 直下のテキスト
			if (attributeItemText.equals("") &&
					node.hasChildNodes()){
				attributeItemText = node.getFirstChild().getTextContent().toString().trim();
			}
		}
		return attributeItemText;
    }

    /* NodeAttributeを返却 */
    private static String getItemAttribute(Node node,String attrNm){
    	String itemAttr = null;
		NamedNodeMap attrs = node.getAttributes();  			// NamedNodeMapの取得

		if (attrs!=null) {
			// root属性(key値)
			Node attrRoot = attrs.getNamedItem(attrNm);  		// root属性ノード
			if (attrRoot != null)
				itemAttr = attrRoot.getNodeValue();
		}
		return itemAttr;
    }

////hl取込-ノード探索↑/////////////////////////////////////////////

////hl取込-登録処理↓/////////////////////////////////////////////
    /*
     * xmlDataDB登録へ
     */
    private boolean registerXmlData(){

    	try {
// 			JApplication.kikanDatabase.Transaction();

	    	// 個人情報の特定(整理番号 or (氏名 and 性別 and 生年月日))
	    	boolean existKojin = serchKojin();

	    	// 保険者(既存に無い場合、リストより選択させる)
	    	if (!serchHokenjya()) return false;

	    	// add s.inoue 2010/12/01
	    	// 代行機関(既存に無い場合、リストより選択させる)
	    	if (!serchDaikou()) return false;

	    	// 健診パターン[必須](リストより選択させる)
	    	if (serchKenshinPattern()) return false;

	    	// 個人登録(Kojin) データが無い場合追加する
	    	if (validatedKojinData.getErrorMessageCount() > 0)
	    		return false;

// edit s.inoue 2010/12/01
//	    	if (validatedKojinData.getUketukeID().equals("")
//	    			&& !existKojin)

// 個人データがある場合、既存データを更新。ない場合、新たに受付ID採番
	    	registerKojin(existKojin);

	    	// eidt s.inoue 2011/07/28
	    	JApplication.kikanDatabase.Transaction();

	    	// 特定した対象者の結果ファイル初期化(個人 + 健診実施日別)
	    	// T_KENSAKEKA_TOKUTEI (delete → insert)
	    	if (Data.uketukeNo != null){
	    		deleteKekkaTokutei();
	    	}

	    	// T_KENSAKEKA_SONOTA (delete → insert)
	    	if (Data.uketukeNo != null){
	    		deleteKekkaSonota();
	    	}

	    	JApplication.kikanDatabase.Commit();

	    	// eidt s.inoue 2011/07/28
	    	JApplication.kikanDatabase.Transaction();

	    	// T_KENSAKEKA_TOKUTEI 登録
	    	registerKekkaTokutei();

	    	// T_KENSAKEKA_SONOTA 登録
	    	registerKekkaSonota();

	    	JApplication.kikanDatabase.Commit();
	    	// del s.inoue 2010/12/01
	    	// T_NAYOSE 登録
	    	// registerNayose();

	    	// eidt s.inoue 2011/07/27
	    	// JApplication.kikanDatabase.getMConnection().commit();

	    	String msch = "氏名:"+ Data.kanaShimei + " 健診実施日:" + Data.jishiDate;
	    	String[] messageParams = {msch};
	    	JErrorMessage.show("M3330", this, messageParams);
		} catch (Exception ex) {
			try {
				logger.error(ex.getMessage());
				JApplication.kikanDatabase.rollback();
			} catch (SQLException e) {}
			System.out.println(ex.getMessage());
		}
		return true;
    }

    /*
     * 健診パターン検索ダイアログ
     */
    private boolean serchKenshinPattern(){

    	boolean cancelFlg = false;
    	// 引数用メッセージ生成
    	String messageTaisyosya = "";
    	messageTaisyosya = "氏名:" + Data.kanaShimei + " 健診実施日:" + Data.jishiDate;

		try {
			patternSelectDialog = DialogFactory.getInstance().createDialog(this,messageTaisyosya);

			// 健診実施日入力ダイアログを表示
			patternSelectDialog.setMessageTitle("健診パターン選択画面");
			patternSelectDialog.setVisible(true);
			// 戻値格納
			if(patternSelectDialog.getStatus().equals(RETURN_VALUE.YES)){
				kenshinPatternNumver = patternSelectDialog.getPrintSelect();
			}else if (patternSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
				cancelFlg = true;
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			JErrorMessage.show("M3336", null);
		}

		System.out.println("選択健診パターン:" + kenshinPatternNumver);
		return cancelFlg;
    }

    /*
     * 代行機関(既存に無い場合、リストより選択させる)
     */
    private static boolean serchDaikou(){
		// 個人結果データ:(整理番号,健診日単位で取得)
    	String daikouNum = validatedKojinData.getDaikouNumber();
		ArrayList<Hashtable<String, String>> hokenjyaData = null;
		ArrayList<Hashtable<String, String>> selectShiharaiData;

    	try {
    		int historyCnt = 0;
    		historyCnt = shiharaiDao.selectByCountUniqueKey(daikouNum);

    		// レコードがない場合、既存データをリスト化して表示
    		if (historyCnt == 0){
    			selectShiharaiData = getShiharaiData();
				if (selectShiharaiData == null ||
						selectShiharaiData.size() == 0){
					JErrorMessage.show("M3338", null);
					return false;
				}

				Hashtable<String, String>selectedShiharaiData
					= searchDaikouData(getShiharaiData(),Data,true);

				if (selectedShiharaiData == null)return false;

				String daikouNumber = selectedShiharaiData.get("SHIHARAI_DAIKO_NO");

				validatedKojinData.setDaikouNumber(daikouNumber);

				System.out.println("支払代行機関" +selectedShiharaiData);
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    		JErrorMessage.show("M3338", null);
    		return false;
    	}
    	return true;
    }

    /*
     * 保険者(既存に無い場合、リストより選択させる)
     */
    private static boolean serchHokenjya(){
		// 個人結果データ:(整理番号,健診日単位で取得)
    	String hokenjyaNum = validatedKojinData.getHokenjyaNumber();
		ArrayList<Hashtable<String, String>> hokenjyaData = null;
		ArrayList<Hashtable<String, String>> selectHokenjyaData;

    	try {
    		int historyCnt = 0;
    		historyCnt = hokenjyaDao.selectByCountUniqueKey(hokenjyaNum);

    		// レコードがない場合、既存データをリスト化して表示
    		if (historyCnt == 0){

    			selectHokenjyaData = getHokenjyaData();
				if (selectHokenjyaData == null ||
						selectHokenjyaData.size() == 0){
					JErrorMessage.show("M3333", null);
					return false;
				}

				// 該当する人が複数いた場合、リストを表示する
				Hashtable<String, String>selectedHokenjyaData
					= searchHokenjyaData(getHokenjyaData(),Data,true);

				if (selectedHokenjyaData == null)return false;

				String hokenjyaNumber = selectedHokenjyaData.get("HKNJANUM");

				validatedKojinData.setHokenjyaNumber(hokenjyaNumber,true);

				System.out.println("保険者" +selectedHokenjyaData);
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    		JErrorMessage.show("M3334", null);
    	}
    	return true;
    }

    // add s.inoue 2010/11/30
	/**
	 * 代行機関番号の存在チェック
	 * 存在しなければリスト表示し、選択させる
	 */
	private static Hashtable<String,String> searchDaikouData(
			ArrayList<Hashtable<String,String>> selectedDaikouData,
			JImportErrorResultFrameData Data,
			boolean convertFlg){

		// 該当する人を検索
		String[] row = new String[5];

		Vector<JSelectShiharaiFrameData> shiharaiData = new Vector<JSelectShiharaiFrameData>();
		Hashtable<String,String> targetItem = null;

		String daikouNum = "";
		String predaikouNum = "";

		for(int j = 0 ; j < selectedDaikouData.size() ; j++)
		{
			Hashtable<String,String> resultItem = selectedDaikouData.get(j);
			daikouNum = resultItem.get("SHIHARAI_DAIKO_NO");
			if (!daikouNum.equals(predaikouNum)){
				JSelectShiharaiFrameData DataSame = new JSelectShiharaiFrameData();
				DataSame.shiharai_daiko_no = (String) daikouNum;
				DataSame.shiharai_daiko_name = new String(resultItem.get("SHIHARAI_DAIKO_NAME"));
				DataSame.shiharai_daiko_zipcd = new String(resultItem.get("SHIHARAI_DAIKO_ZIPCD"));
				DataSame.shiharai_daiko_adr = new String(resultItem.get("SHIHARAI_DAIKO_ADR"));
				DataSame.shiharai_daiko_tel= new String(resultItem.get("SHIHARAI_DAIKO_TEL"));

				DataSame.DatabaseItem = resultItem;
				System.out.println("支払no" + DataSame.shiharai_daiko_no+ "支払Nm"+  DataSame.shiharai_daiko_name);
				shiharaiData.add(DataSame);
				targetItem = resultItem;
			}

			predaikouNum = daikouNum;
		}

		if(shiharaiData.size() >= 1)
		{
			JSelectShiharaiFrameCtrl SelectShiharaiFrame = new JSelectShiharaiFrameCtrl(shiharaiData,"");
			JSelectShiharaiFrameData SelectedData = SelectShiharaiFrame.GetSelectedData();

			if(SelectedData == null)
			{
				String message = "氏名:" + Data.kanaShimei + "健診実施日:" + Data.jishiDate;
				ResultMessage += GetErrorMessage("支払代行機関が選択されませんでした。処理をスキップします。",message);
				targetItem=null;
			}else{
				// targetItem = SelectedData.DatabaseItem;
				targetItem = SelectedData.DatabaseItem;
			}
		}
		return targetItem;
	}

	/**
	 * 保険者番号の存在チェック
	 * 存在しなければリスト表示し、選択させる
	 */
	private static Hashtable<String,String> searchHokenjyaData(
			ArrayList<Hashtable<String,String>> selectedHokenjyaData,
			JImportErrorResultFrameData Data,
			boolean convertFlg){

		// 該当する人を検索
		String[] row = new String[5];

		Vector<JSelectHokenjyaFrameData> hokenjyaData = new Vector<JSelectHokenjyaFrameData>();
		Hashtable<String,String> targetItem = null;

		// 保険者データ表示
		String hknjyaNum = "";
		String prehknjyaNum = "";

		for(int j = 0 ; j < selectedHokenjyaData.size() ; j++)
		{
			Hashtable<String,String> resultItem = selectedHokenjyaData.get(j);
			hknjyaNum = resultItem.get("HKNJANUM");
			if (!hknjyaNum.equals(prehknjyaNum)){

				JSelectHokenjyaFrameData DataSame = new JSelectHokenjyaFrameData();
				DataSame.hknjanum = (String) hknjyaNum;
				DataSame.hknjaname = new String(resultItem.get("HKNJANAME"));
				DataSame.post = new String(resultItem.get("POST"));
				DataSame.adress = new String(resultItem.get("ADRS"));
				DataSame.tel= new String(resultItem.get("TEL"));

				DataSame.DatabaseItem = resultItem;
				System.out.println("保険者no" + DataSame.hknjanum+ "保険者Nm"+  DataSame.hknjaname);
				hokenjyaData.add(DataSame);
				targetItem = resultItem;
			}

			prehknjyaNum = hknjyaNum;
		}

		if(hokenjyaData.size() >= 1)
		{
			JSelectHokenjyaFrameCtrl SelectHokenjyaFrame = new JSelectHokenjyaFrameCtrl(hokenjyaData,"");
			JSelectHokenjyaFrameData SelectedData = SelectHokenjyaFrame.GetSelectedData();

			if(SelectedData == null)
			{
				String message = "氏名:" + Data.kanaShimei + "健診実施日:" + Data.jishiDate;
				ResultMessage += GetErrorMessage("保険者が選択されませんでした。処理をスキップします。",message);
				targetItem=null;
			}else{
				// targetItem = SelectedData.DatabaseItem;
				targetItem = SelectedData.DatabaseItem;
			}
		}
		return targetItem;


//		//同姓同名・同一生年月日の受診者が複数いた場合の処理
//		if(SameKojinData.size() > 1)
//		{
//			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,FLAME_TITLE_MESSAGE);
//			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();
//
//			if(SelectedData == null)
//			{
//				ResultMessage += GetErrorMessage("受診者が選択されませんでした。処理をスキップします。",Data.kanaShimei);
//				TargetItem=null;
//			}else{
//				TargetItem = SelectedData.DatabaseItem;
//			}
//		}
//		return TargetItem;


//		for(int j = 0 ; j < selectedHokenjyaData.size() ; j++)
//		{
//			Hashtable<String,String> DatabaseItem = selectedHokenjyaData.get(j);
//
//			String Name = DatabaseItem.get("KANANAME");
//
//			if(JEraseSpace.EraceSpace(Name).equals(JEraseSpace.EraceSpace(Data.kanaShimei)))
//			{
//				JSelectKojinFrameData DataSame = new JSelectKojinFrameData();
//
//				DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
//				DataSame.name = DatabaseItem.get("NAME");
//				DataSame.kanaShimei = DatabaseItem.get("KANANAME");
//				DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
//				DataSame.seiBetu = DatabaseItem.get("SEX");
//				// edit ver2 s.inoue 2009/08/19
//				DataSame.jyusho= DatabaseItem.get("HOME_ADRS");
//				DataSame.hihokenshaKigo = DatabaseItem.get("HIHOKENJYASYO_KIGOU");
//				DataSame.hihokenshaNo = DatabaseItem.get("HIHOKENJYASYO_NO");
//				DataSame.DatabaseItem = DatabaseItem;
//
//				SameKojinData.add(DataSame);
//				TargetItem = DatabaseItem;
//			}
//		}
//
//		//同姓同名・同一生年月日の受診者が複数いた場合の処理
//		if(SameKojinData.size() > 1)
//		{
//			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,FLAME_TITLE_MESSAGE);
//			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();
//
//			if(SelectedData == null)
//			{
//				ResultMessage += GetErrorMessage("受診者が選択されませんでした。処理をスキップします。",Data.kanaShimei);
//				TargetItem=null;
//			}else{
//				TargetItem = SelectedData.DatabaseItem;
//			}
//		}
//		return TargetItem;
	}

	// add s.inoue 2010/11/30
	/**
	 * 支払データ取得(条件:氏名、年齢、性別)
	 */
	private static ArrayList<Hashtable<String, String>>
		getShiharaiData() throws Exception{

		ArrayList<Hashtable<String, String>> shiharaiData = null;

		try{

			String query= new String(
				"SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL" +
				" FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO DESC");
			shiharaiData = JApplication.kikanDatabase.sendExecuteQuery(query);

			} catch (Exception err) {
				throw err;
			}
		return shiharaiData;
	}

	/**
	 * 保険者データ取得(条件:氏名、年齢、性別)
	 */
	private static ArrayList<Hashtable<String, String>>
		getHokenjyaData() throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;

		try{

			String query= new String(
				"SELECT DISTINCT HKNJANUM,HKNJANAME,POST,ADRS,BANTI,TEL FROM T_HOKENJYA ORDER BY HKNJANUM DESC");
			kenshinData = JApplication.kikanDatabase.sendExecuteQuery(query);

			} catch (Exception err) {
				throw err;
			}
		return kenshinData;
	}
    /*
	 *  個人特定検索 リスト化して画面を表示して選択させる
	 *  (受診整理番号) or (カナ氏名 + 生年月日 + 性別)
	 */
    private boolean serchKojin(){

    	boolean retExistKojin = false;

		// 個人結果データ:(整理番号,健診日単位で取得)
		String jusinkenID = validatedKojinData.getJyushinkenID();

		ArrayList<Hashtable<String, String>> kojinData = null;
		ArrayList<Hashtable<String, String>> selectKojinData;

    	try {
	    		System.out.println("ID" + jusinkenID);
				Data.jusinSeiriNo = jusinkenID;
				Data.jishiDate = validatedKenshinData.getKensaJissiDate();
				Data.kanaShimei = validatedKojinData.getNameKana();
				Data.seiNenGapi = validatedKojinData.getBirthday();
				Data.seiBetu = validatedKojinData.getSex();
				Data.jisiKikanNo = kikanNumber;

				if (!jusinkenID.equals("")){
					kojinData = getKojinData(Data);
					if (kojinData != null){
						if (kojinData.size()>0)
							Data.uketukeNo =kojinData.get(0).get("UKETUKE_ID");
							// eidt s.inoue 2011/07/27
							validatedKojinData.setUketukeID(kojinData.get(0).get("UKETUKE_ID"));
					}
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				JErrorMessage.show("M3335", null);
			}

		// 該当するデータがない場合、人(氏名,生年月日,性別)で検索
		if (kojinData == null ||
				kojinData.size() == 0){
			try{

				selectKojinData = getSimeiData(Data);

				if (selectKojinData == null ||
						selectKojinData.size() == 0){
				}
				// 該当する人が複数いた場合、リストを表示する
				// edit s.inoue 2010/02/03
				Hashtable<String, String>selectedKojinData
					= searchKojinData(selectKojinData,Data,false);

				// データが存在しない場合、抜ける
				if(selectedKojinData != null){
					String uketukeId = selectedKojinData.get(JApplication.COLUMN_NAME_UKETUKE_ID);
					validatedKenshinData.setUketuke_id(uketukeId);
					Data.uketukeNo = uketukeId;

					kojinData = getSelectKojinData(Data);
					if (kojinData == null ||
							kojinData.size() == 0){
					}
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				JErrorMessage.show("M3335", null);
			}
		}
		if (kojinData != null)
			retExistKojin = true;

		return retExistKojin;
    }

    /*
	 *  個人登録(T_KOJIN) にっとく側に個人データが無い場合、作成する
	 */
    private static void registerKojin(boolean existflg)throws Exception{

    	String uketukeNewID = "";

    	// edit s.inoue 2010/12/02
    	// 個人データがあれば、その受付IDを使用
    	if (existflg){
    		Data.uketukeNo = validatedKojinData.getUketukeID();
    		// eidt s.inoue 2011/07/28
    		return;
    	}else{
    		uketukeNewID = String.valueOf(kojinDao.selectNewUketukeId());
        	// 受付ID採番
        	validatedKojinData.setUketukeID(uketukeNewID);
        	validatedKenshinData.setUketuke_id(uketukeNewID);
        	Data.uketukeNo = uketukeNewID;
    	}
    	System.out.println("受付ID:" + uketukeNewID);

    	// 個人情報登録
    	JApplication.kikanDatabase.sendNoResultQuery(createRegisterKojinSql());

    	System.out.println("出力結果 " +
    			" hokennm:" + validatedKojinData.getHokenjyaNumber() +
    			" hokenkigo:" + validatedKojinData.getHihokenjyaNumber() +
    			" birthTime:" + validatedKojinData.getBirthday()
    			);
    }

    /**
	 * 受診券情報登録用 SQL を作成する。
	 */
	private static String createRegisterKojinSql() {

		StringBuffer query = null;
			query = new StringBuffer();
			query.append(" INSERT INTO T_KOJIN (");

			T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();
			int length = columns.length;

			for (int i = 0; i < length; i++) {
				String columnName = JKojinRegisterFrameData.getColumnName(columns[i]);
				query.append(columnName);

				if (i != length - 1) {
					query.append(",");
				}
			}
			query.append(" )VALUES ( ");

			String value = null;
			for (int i = 0; i < length; i++) {
				value = validatedKojinData.get(columns[i]);

				if (i != length - 1) {
					query.append(JQueryConvert.queryConvertAppendComma((value)));
				}else {
					query.append(JQueryConvert.queryConvert(value));
				}
			}
			query.append(" ) ");

		return query.toString();
	}

	/*
	 *  結果データ登録(T_KENSAKEKA_TOKUTEI)
	 */
    private static void registerKekkaTokutei()throws Exception{

       	// 受付ID,年度の追加対応
		// 検査結果データ特定レコード挿入
		kensakekaTokutei = new TKensakekaTokutei();

		kensakekaTokutei.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
		kensakekaTokutei.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());

		String kenshinJisidate = validatedKenshinData.getKensaJissiDate();

		kensakekaTokutei.setKENSA_NENGAPI(new Integer(kenshinJisidate));
		kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(kenshinJisidate));

		// 結果値を取り込み → 登録済み(InputFlg:2)
		kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(2));

		// uketukeID(取得した分 or なし)
		String uketukeId = validatedKenshinData.getUketuke_id();
		if ( !uketukeId.equals("")){
			kensakekaTokutei.setUKETUKE_ID(Long.parseLong(uketukeId));
		}else if (!Data.uketukeNo.equals("")){
			kensakekaTokutei.setUKETUKE_ID(Long.parseLong(Data.uketukeNo));
		}

		// 請求区分(claimsより読込)
		// xmlファイル単体の場合、請求区分が取得できないため
		if (!validatedKenshinData.getSeikyuKubunCode().equals("")){
			kensakekaTokutei.setSEIKYU_KBN(new Integer(validatedKenshinData.getSeikyuKubunCode()));
		}else{
			kensakekaTokutei.setSEIKYU_KBN(1);
		}

		// 健診パターンNo
		kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumver));

		kensakekaTokuteiDao.insert(kensakekaTokutei);

		// HANTEI_NENGAPI以降の項目は対象外～
		// 保留項目 ←おそらく取込まない項目
		// kensakekaTokutei.setKENSA_CENTER_CD(validatedKenshinData.getKensaCenterCode());
		// kensakekaTokutei.setUKETUKE_ID(new Long(validatedKenshinData.getUketuke_id()));
		// kensakekaTokutei.setKOMENTO(validatedKenshinData.getSougouComment());
    }

	/*
	 *  結果その他登録(T_KENSAKEKA_SONOTA)
	 */
    private static void registerKekkaSonota()throws Exception{
       	// Mapオブジェクトの「キー」の一覧を取得
    	// 登録前処理:健診コードをリスト化
    	// バリデーションで未登録健診項目チェック or 登録処理※処理を飛ばす
        for (Iterator i = koumokuCodeMap.keySet ().iterator (); i.hasNext (); ) {
        	Object koumokuItem = i.next ();
        	Object koumokuValue = koumokuCodeMap.get (koumokuItem);

        	if(importKenshinItem.contains(koumokuItem)){

        		System.out.println("key:" + koumokuItem + " value:" + koumokuValue);

				// 検査結果データその他レコード削除・挿入
				kensakekaSonota = new TKensakekaSonota();

				// 健診実施日,年度
				kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));

				String kenshinJisidate = validatedKenshinData.getKensaJissiDate();
				kensakekaSonota.setKENSA_NENGAPI(new Integer(kenshinJisidate));
				kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(kenshinJisidate));
				kensakekaSonota.setKOUMOKU_CD(koumokuItem.toString());
				kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
				kensakekaSonota.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());

				// 実施区分：実施
				kensakekaSonota.setJISI_KBN(1);
				kensakekaSonota.setKEKA_TI(koumokuValue.toString());

				kensakekaSonotaDao.insert(kensakekaSonota);

				// 個人情報より特定
				// kensakekaSonota.setUKETUKE_ID(new Long(validatedKenshinData.getUketuke_id()));
				// kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
				// kensakekaSonota.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());
        	}
        }

        // 未実施、測定不能{0,2}分の登録
        for (Iterator i = koumokuCodeElseMap.keySet ().iterator (); i.hasNext (); ) {
        	Object koumokuItem = i.next ();
        	Object koumokuValue = koumokuCodeElseMap.get (koumokuItem);

			// 検査結果データその他レコード削除・挿入
			kensakekaSonota = new TKensakekaSonota();

			kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
			String kenshinJisidate = validatedKenshinData.getKensaJissiDate();
			kensakekaSonota.setKENSA_NENGAPI(new Integer(kenshinJisidate));
			kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(kenshinJisidate));
			kensakekaSonota.setKOUMOKU_CD(koumokuItem.toString());
			kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedKenshinData.getHihokenjyaCode());
			kensakekaSonota.setHIHOKENJYASYO_NO(validatedKenshinData.getHihokenjyaNumber());
			kensakekaSonota.setJISI_KBN(Integer.parseInt((String)koumokuValue));
			// eidt s.inoue 2013/04/04 ver2.0.3
			// 未実施、測定不能は値なしで測定不能のチェックのみ
			if (!koumokuValue.equals("2") && !koumokuValue.equals("0"))
			kensakekaSonota.setKEKA_TI(koumokuValue.toString());

			kensakekaSonotaDao.insert(kensakekaSonota);
        }

    }

//    // add s.inoue 2010/12/01
//	/**
//	 * 名寄せテーブルへ登録処理を実施する。
//	 */
//	private void registerNayose(){
//
//		StringBuilder nayoseQuery = null;
//		StringBuilder newNayoseQuery = null;
//		// 名寄せNO採番
//		long nayoseNo = -1L;
//		long retTNayoseNo = 0L;
//
//		// 現時刻を取得
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());
//
//		try {
//			// 受付ID
//			// edit s.inoue 2010/12/01
////			String newUketukeId ="";
////	    	if (!validatedKojinData.getpreUketukeID().equals("")){
////	    		if (validatedKojinData.getUketukeID().equals(validatedKojinData.getpreUketukeID())){
////	    			int diff = validatedKojinData.getdiff();
////	    			diff++;
////	    			newUketukeId = String.valueOf(kojinDao.selectNewUketukeId(diff));
////	    		}else{
////	    			newUketukeId = validatedKojinData.getUketukeID();
////	    		}
////	    	}else{
////	    		newUketukeId = validatedKojinData.getUketukeID();
////	    	}
////			validatedKojinData.setpreUketukeID(newUketukeId);
//
//			String newUketukeId =validatedKojinData.getUketukeID();
//
//			/* T_NAYOSE Dao を作成する。 */
//			tNayoseDao = (TNayoseDao) DaoFactory.createDao(
//					JApplication.kikanDatabase.getMConnection(), new TNayose());
//
//			retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedKojinData.getUketukeID()));
//			// 既に履歴がある場合、その名寄せNoを使用する
//			if (retTNayoseNo > 0) {
//				nayoseNo = retTNayoseNo;
//			}else{
//				nayoseNo = tNayoseDao.selectNewNayoseNo();
//
//				// 名寄せテーブル登録処理
//				// 受付IDの紐付け処理:1、2をセット
//				// 1.受診券情報で氏名かなで紐付けた受付IDを登録
//				// 2.受診券登録後に新しい受付番号を登録
//
//				// 1.処理:履歴がない場合
//				nayoseQuery = new StringBuilder();
//				nayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
//				nayoseQuery.append("VALUES (");
//				nayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
//				nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedKojinData.getUketukeID()));
//				nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
//				nayoseQuery.append(") ");
//
//				try {
//					JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//
//					try {
//						JApplication.kikanDatabase.rollback();
//					} catch (SQLException g) {
//						logger.error(g.getMessage());
//					}
//				}
//
//			}
//
//			// 2.処理:採番した追加用レコード共通処理
//			newNayoseQuery = new StringBuilder();
//			newNayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
//			newNayoseQuery.append("VALUES (");
//			newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
//			newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(newUketukeId.toString()));
//			newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
//			newNayoseQuery.append(") ");
//
//			try {
//				JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
//			} catch (SQLException e) {
//				logger.error(e.getMessage());
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException g) {
//					logger.error(g.getMessage());
//				}
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//	}

    /*
     * 結果削除(T_KENSAKEKA_TOKUTEI)
     */
    private static void deleteKekkaTokutei() throws Exception{
		kensakekaTokuteiDao.delete(
				Data.uketukeNo,
				Data.jishiDate);
    }

    /*
     * 結果削除(T_KENSAKEKA_SONOTA)
     */
    private static void deleteKekkaSonota() throws Exception{
		kensakekaSonotaDao.deleteByUketukeIdKensaNengapi(
				Long.parseLong(Data.uketukeNo),
				Integer.parseInt(Data.jishiDate));
    }
    /*-------------------------------------hl7-----------------------------------------------*/

	/*-------------------------------------共通-----------------------------------------------*/
	/**
	 * 整理番号で検索できない場合の絞込み処理
	 * convertFlg:かな氏名を半角→全角 true,全角→半角 false
	 */
	private Hashtable<String,String> searchKojinData(
			ArrayList<Hashtable<String,String>> KojinDatabaseData,
			JImportErrorResultFrameData Data,
			boolean convertFlg){

		// 該当する人を検索
		Hashtable<String,String> TargetItem = null;

		Vector<JSelectKojinFrameData> SameKojinData = new Vector<JSelectKojinFrameData>();

		for(int j = 0 ; j < KojinDatabaseData.size() ; j++)
		{
			Hashtable<String,String> DatabaseItem = KojinDatabaseData.get(j);

			// edit s.inoue 2010/02/03
			//半角カタカナ、空白を除去した状態で比較
			String Name = "";
			if (convertFlg){
				Name = JZenkakuKatakanaToHankakuKatakana.Convert(DatabaseItem.get("KANANAME"));
			}else{
				Name = DatabaseItem.get("KANANAME");
			}

			if(JEraseSpace.EraceSpace(Name).equals(JEraseSpace.EraceSpace(Data.kanaShimei)))
			{
				JSelectKojinFrameData DataSame = new JSelectKojinFrameData();

				DataSame.jusinSeiNo = DatabaseItem.get("JYUSHIN_SEIRI_NO");
				DataSame.name = DatabaseItem.get("NAME");
				DataSame.kanaShimei = DatabaseItem.get("KANANAME");
				DataSame.seiNenGapi = DatabaseItem.get("BIRTHDAY");
				DataSame.seiBetu = DatabaseItem.get("SEX");
				// edit ver2 s.inoue 2009/08/19
				DataSame.jyusho= DatabaseItem.get("HOME_ADRS");
				DataSame.hihokenshaKigo = DatabaseItem.get("HIHOKENJYASYO_KIGOU");
				DataSame.hihokenshaNo = DatabaseItem.get("HIHOKENJYASYO_NO");
				DataSame.DatabaseItem = DatabaseItem;

				SameKojinData.add(DataSame);
				TargetItem = DatabaseItem;
			}
		}

		//この段階でnullが入っていたら該当する人がいないので、エラーを出すなどの処理を行う。
		//if(TargetItem == null)
		//{
		//	ResultMessage += GetErrorMessage("該当する受診者がいません。",shimeiKana);
		//}

		//同姓同名・同一生年月日の受診者が複数いた場合の処理
		if(SameKojinData.size() > 1)
		{
			//ResultMessage += GetErrorMessage("同姓同名・同一生年月日の受診者がいます。選択ダイアログを出します。",shimeiKana);

//			JSelectKojinFrameData SelectedData =  new JSelectKojinFrameData();
//			jp.or.med.orca.jma_tokutei.common.scene.JScene.CreateDialog(this, new JSelectKojinFrameCtrl(SameKojinData,this));
//			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this);
//			SelectedData =SelectKojinFrame.GetSelectedData();

			JSelectKojinFrameCtrl SelectKojinFrame = new JSelectKojinFrameCtrl(SameKojinData,this,FLAME_KOJIN_TITLE_MESSAGE);
			JSelectKojinFrameData SelectedData = SelectKojinFrame.GetSelectedData();

			if(SelectedData == null)
			{
				ResultMessage += GetErrorMessage("受診者が選択されませんでした。処理をスキップします。",Data.kanaShimei);
				TargetItem=null;
			}else{
				TargetItem = SelectedData.DatabaseItem;
			}
		}
		return TargetItem;
	}

	/**
	 * エラー種別設定処理
	 */
	private void setImportErrDigit(
			//Hashtable<String,String> DatabaseItem,
			Vector<JImportErrorResultFrameData> ErrorResultData,
			JImportErrorResultFrameData Data,
			Integer i){

		// add ver2 s.inoue 2009/07/27
		// DB項目
		// Data.rowNo =i + 1;
		// ErrorResultData.add(Data);

		JImportErrorResultFrameData DataKeys = new JImportErrorResultFrameData();

		DataKeys.errCase = Data.errCase;
		DataKeys.errField = Data.errField;
		DataKeys.jusinSeiriNo =Data.jusinSeiriNo;
		DataKeys.jishiDate = Data.jishiDate;
		DataKeys.kanaShimei = Data.kanaShimei;
		DataKeys.seiNenGapi = Data.seiNenGapi;
		DataKeys.seiBetu = Data.seiBetu;
		DataKeys.jisiKikanNo = Data.jisiKikanNo;
		DataKeys.nyuBi = Data.nyuBi;
		DataKeys.shokuZenGo = Data.shokuZenGo;

		DataKeys.koumokuCd = Data.koumokuCd;
		DataKeys.jisiKbn = Data.jisiKbn;
		DataKeys.kekkaTi = Data.kekkaTi;
		DataKeys.rowNo =i + 1;

		// DB項目
		//Data.rowNo =i + 1;
		ErrorResultData.add(DataKeys);
	}

	/**
	 * 検査結果データチェック処理(検査項目情報◎必須)
	 */
	private boolean checkKensaKekkaKeyData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i,
				boolean flg)
	{
		boolean checkKensaKekkaFlg=false;

		if (!Data.jusinSeiriNo.toString().equals("")){
			// 受診券整理番号
			if (validateImportJusinSeiriNo(Data))
			{
				setImportErrDigit(ErrorResultData,Data,i);
				checkKensaKekkaFlg =true;

			}
		}
		// カナ氏名◎
		if (validateImportkanaShimei(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 生年月日
		if (validateImportseiNenGapi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 性別
		if (validateImportSex(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 乳び/溶血
		if (validateImportNyuBi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 食前/食後
		if (validateImportShokuZenGo(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 健診実施年月日◎
		if (validateImportJisiDate(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;
	}

	/**
	 * 検査結果データチェック処理(検査項目情報◎必須、●日医フォーマット用必須)
	 */
	private boolean checkKensaKekkaKeyNitiiFormatData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i,
				boolean flg)
	{
		boolean checkKensaKekkaFlg=false;

		if (!Data.jusinSeiriNo.toString().equals("")){
			// 受診券整理番号
			if (validateImportJusinSeiriNo(Data))
			{
				setImportErrDigit(ErrorResultData,Data,i);
				checkKensaKekkaFlg =true;

			}
		}
		// カナ氏名●
		if (validateImportNitiiFormatkanaShimei(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 生年月日●
		if (validateImportNitiiFormatseiNenGapi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 性別●
		if (validateImportNitiiFormatSex(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}
		// 健診実施年月日◎
		if (validateImportJisiDate(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;

	}

	/**
	 * 検査結果データチェック処理(属性情報)★format共通
	 */
	private boolean checkKensaKekkaData(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i) {

		boolean checkKensaKekkaFlg=false;

		// 健診実施機関番号
		if (validateImportJisiKikanNo(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg =true;
		}

		return checkKensaKekkaFlg;
	}

	/**
	 * 検査結果データチェック処理(検査項目情報◎必須)
	 */
	private boolean checkKoumokuCD(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i)
	{
		boolean checkKensaKekkaFlg=false;
		// 項目コード◎
		if (validateKensaKoumokuCode(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}
		return checkKensaKekkaFlg;
	}


	/**
	 * 検査結果データチェック処理(検査項目情報◎必須)
	 */
	private boolean checkKensaKekkaDataDetail(
				JImportErrorResultFrameData Data,
				Vector<JImportErrorResultFrameData> ErrorResultData,
				Integer i)
	{
		boolean checkKensaKekkaFlg=false;

		// 実施区分◎
		if (validateImportJisiKbn(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}

// del 20080918 s.inoue
//		// 異常値区分
//		if (validateImportHLKbn(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
//		// 結果値形態
//		if (validateImportKekkaTiKeitai(Data))
//		{
//			setImportErrDigit(ErrorResultData,Data,i);
//			checkKensaKekkaFlg = true;
//		}
		// 検査結果◎
		if (validateImportKekkaTi(Data))
		{
			setImportErrDigit(ErrorResultData,Data,i);
			checkKensaKekkaFlg = true;
		}
		return checkKensaKekkaFlg;
	}

	/* EDIT 2008/07/23 井上 */
	/* --------------------------------------------------- */
	/**
	 * 検査結果特定登録
	 * @throws SQLException
	 */
	private void kensakekaTokuteiregister(JImportErrorResultFrameData Data)
		throws SQLException
	{

		kensakekaTokutei = new TKensakekaTokutei();
		// 共通
		kensakekaTokutei.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
		kensakekaTokutei.setKENSA_NENGAPI(Integer.valueOf(Data.jishiDate));
		kensakekaTokutei.setNYUBI_YOUKETU(Data.nyuBi);
		kensakekaTokutei.setSYOKUZEN_SYOKUGO(Data.shokuZenGo);
		// pending s.inoue
		//if (mode == INSERT_MODE) {
		//	kensakekaTokutei.setHIHOKENJYASYO_KIGOU("");
		//	kensakekaTokutei.setHIHOKENJYASYO_NO("");
		//	kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
		//	kensakekaTokutei.setHANTEI_NENGAPI(null);
		//	kensakekaTokutei.setTUTI_NENGAPI(null);
		//	kensakekaTokutei.setKENSA_CENTER_CD("");
			// このあとUPDATEを行うため一時的にデータを入れる
		//	kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
		//	kensakekaTokutei.setSEIKYU_KBN(new Integer(1));
		//	kensakekaTokutei.setKOMENTO("");
		//	kensakekaTokutei.setNENDO(null);
		//	kensakekaTokuteiDao.insert(kensakekaTokutei);

		//}else if (mode == UPDATE_MODE) {
		// s.inoue 20080924
		kensakekaTokuteiDao.update(kensakekaTokutei,false);
		//}

		/*String query = "UPDATE T_KENSAKEKA_TOKUTEI SET "
			+ "NYUBI_YOUKETU = "
			+ JQueryConvert.queryConvertAppendComma(this.nyubiYoketu)

			+ "SYOKUZEN_SYOKUGO = "
			+ JQueryConvert.queryConvert(this.shokuzenShokugo)

			//+ "KENSA_CENTER_CD = "
				//+ JQueryConvert.queryConvertAppendComma("")
			//+ "K_P_NO = "
				//+ JQueryConvert.queryConvertAppendComma("")
			//+ "KOMENTO = "
				//+ JQueryConvert.queryConvertAppendComma("")
			//+ "SEIKYU_KBN = "
				//+ JQueryConvert.queryConvert("")
			+ " WHERE "
			+ "UKETUKE_ID = "
				+ JQueryConvert.queryConvert(this.uketukeId.toString())
			+ " AND "
			+ "	KENSA_NENGAPI = "
				+ JQueryConvert.queryConvert(this.kensaDate.toString());
			JApplication.kikanDatabase.sendNoResultQuery(query);*/
	}
	/* --------------------------------------------------- */

	/* EDIT 2008/07/23 井上 */
	/* --------------------------------------------------- */
	/**
	 * 検査結果登録
	 * @throws SQLException
	 */
	private void kensakekaSonotaregister(JImportErrorResultFrameData Data)
		throws SQLException
	{
			// move4. 検査結果登録処理
			// ad s.inoue データベースに登録(T_KENSAKEKKA_SONOTA)
//			2008/3/19 西山 修正
//			T_KOJIN,T_KENSAKEKA_TOKUTEIにUKETUKE_ID,NENDO追加対応
//			-------------------------------------------------------------------------------------------------------
			TKensakekaSonota kensakekaSonota = new TKensakekaSonota();
			// ed s.inoue
			// Map<String, String> recMap = KojinDatabaseData.get(i);
			// ed s.inoue kensakekaSonota.setUKETUKE_ID(new Long(recMap.get("UKETUKE_ID")));
			// kensakekaSonota.setHIHOKENJYASYO_KIGOU(TargetItem.get("HIHOKENJYASYO_KIGOU"));
			// kensakekaSonota.setHIHOKENJYASYO_NO(TargetItem.get("HIHOKENJYASYO_NO"));
			// kensakekaSonota.setKENSA_NENGAPI(new Integer(KensaDate));
			// kensakekaSonota.setKOUMOKU_CD(KoumokuCode);
			// kensakekaSonota.setKEKA_TI(Body.Kekka);

			kensakekaSonota.setUKETUKE_ID(Long.valueOf(Data.uketukeNo));
			kensakekaSonota.setKENSA_NENGAPI(Integer.valueOf(Data.jishiDate));
			kensakekaSonota.setKOUMOKU_CD(Data.koumokuCd);
			// notnullのフィールド値
			kensakekaSonota.setHIHOKENJYASYO_KIGOU("");
			kensakekaSonota.setHIHOKENJYASYO_NO("");

			kensakekaSonota.setJISI_KBN(Integer.valueOf(Data.jisiKbn));
			// del 20080918 s.inoue
			//kensakekaSonota.setH_L(Integer.valueOf(JValidate.validateHLKubun(Data.ijyoKbn)));
			//kensakekaSonota.setKEKA_TI_KEITAI(Integer.valueOf(JValidate.validateKekkaTiKeitai(Data.kekkaTiKeitai)));

			kensakekaSonota.setKEKA_TI(Data.kekkaTi);

			// pending s.inoue
			// if (mode == INSERT_MODE) {
			//	kensakekaSonotaDao.insert(kensakekaSonota);
			// }else if (mode == UPDATE_MODE) {
			kensakekaSonotaDao.update(kensakekaSonota);
			// }

			// del s.inoue
			//try {
			//	TKensakekaSonotaDao kensakekaSonotaDao =
			//	(TKensakekaSonotaDao)DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), kensakekaSonota);
			//	kensakekaSonotaDao.insert(kensakekaSonota);
			//} catch (Exception e) {
			//	e.printStackTrace();
			//	ed s.inoue ImportItem→shimeiKana
			//	ResultMessage += GetErrorMessage("データベースへの登録に失敗しました。同一のデータが存在する可能性があります。",shimeiKana);
			//	 del s.inoue
			//	 continue;
			//}
//		 -------------------------------------------------------------------------------------------------------
//						 del s.inoue }

	}
	/* --------------------------------------------------- */

	/**
	 * 検査結果有無判定
	 */
	private boolean existsKensaKekkaData(JImportErrorResultFrameData Data) {

		TKensakekaSonota resultList = null;

		try {
			resultList = kensakekaSonotaDao.selectByPrimaryKey(
					Long.valueOf(Data.uketukeNo),Integer.valueOf(Data.jishiDate),Data.koumokuCd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean exsistsKensaKekkaData = false;
		if (resultList != null) {

			if (resultList.getKEKA_TI() !=null)
			{
				if (!resultList.getKEKA_TI().equals("")){
					exsistsKensaKekkaData = true;
				}
			}
		}

		return exsistsKensaKekkaData;
	}

	/**
	 * 個人データ取得(条件:氏名、年齢、性別)
	 */
	private static ArrayList<Hashtable<String, String>>
		getSimeiData(JImportErrorResultFrameData Data) throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;

		try{

			String[] params = new String[] { JCalendarConvert.JCtoAD(Data.seiNenGapi),
					Data.seiBetu,
					};

			kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
				getSimeiDataSQL(), params);

			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		return kenshinData;
	}

	/**
	 * 個人データ取得
	 */
	private static ArrayList<Hashtable<String, String>>
		getKojinData(JImportErrorResultFrameData Data) throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;
		try{

				// omschange s.inoue
				//String[] params = new String[] { Data.uketukeNo,
				String[] params = new String[] {
						Data.jusinSeiriNo.toString(),
						Data.jishiDate,
						Data.jishiDate,
						};

				kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
					getKensaDataSQL(false), params);
				if (kenshinData == null ||
						kenshinData.size() <= 0) {
					return null;
				}
			} catch (Exception err) {
				throw err;
			}
		return kenshinData;
	}

	/**
	 * 個人データ取得(受付番号)
	 */
	private static ArrayList<Hashtable<String, String>>
		getSelectKojinData(JImportErrorResultFrameData Data) throws Exception{

		ArrayList<Hashtable<String, String>> kenshinData = null;

		try{
				String[] params = new String[] {
						Data.uketukeNo,
						Data.jishiDate,
						Data.jishiDate,
						};

				kenshinData = JApplication.kikanDatabase.sendExecuteQuery(
					getKensaDataSQL(true), params);
				if (kenshinData == null ||
						kenshinData.size() <= 0) {
					return null;
				}
			} catch (Exception err) {
				throw err;
			}
		return kenshinData;
	}

	// add s.inoue 2010/03/16
	/**
	 * BMI値を計算する
	 */
	public String calcBMI(String height, String weight) {
		// どちらかが空値の場合は空値を返す
		if (height.isEmpty() || weight.isEmpty()) {
			return "";
		}

		// ０除算エラーを防ぐ
		if (Double.valueOf(height) == 0) {
			return "";
		}

		BigDecimal bmi = new BigDecimal(
				String
						.valueOf((Double.valueOf(weight) / ((Double
								.valueOf(height) / 100) * (Double
								.valueOf(height) / 100)))));
		return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	/**
	 * 検査結果その他取得(共通)
	 */
	private static String getFromSQL()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(" T_KENSAKEKA_TOKUTEI AS TOKUTEI");
		buffer.append(" LEFT JOIN T_KENSAKEKA_SONOTA AS SONOTA ");
		buffer.append(" ON");
		buffer.append(" (");
		buffer.append("  TOKUTEI.UKETUKE_ID = SONOTA.UKETUKE_ID");
		buffer.append(" )");
		buffer.append(" LEFT JOIN T_KOJIN AS KOJIN ");
		buffer.append(" ON ");
		buffer.append(" ( ");
		buffer.append(" SONOTA.UKETUKE_ID = KOJIN.UKETUKE_ID ");
		buffer.append(" ) ");
		return buffer.toString();
	}

	/**
	 * 検査結果その他取得
	 * flgKeys true:受付番号, false:整理番号
	 */
	private static String getKensaDataSQL(boolean flgKeys)
	{
		// add 20081002 s.inoue SONOTA⇒MASTER.KOUMOKU_CDへ変更
		StringBuffer buffer = new StringBuffer();
		//buffer.append(" SELECT DISTINCT KOJIN.UKETUKE_ID,TOKUTEI.KENSA_NENGAPI,MASTER.KOUMOKU_CD ");
		buffer.append(" SELECT KOJIN.UKETUKE_ID,TOKUTEI.KENSA_NENGAPI,SONOTA.KOUMOKU_CD ");
		buffer.append(" FROM ");
		buffer.append(getFromSQL());

		buffer.append(" WHERE");

		if (flgKeys){
			buffer.append(" TOKUTEI.UKETUKE_ID = ? ");
		}else{
			buffer.append(" KOJIN.JYUSHIN_SEIRI_NO = ? ");
		}

		buffer.append(" AND");
		buffer.append(" TOKUTEI.KENSA_NENGAPI = ? ");
		buffer.append(" AND");
		buffer.append(" SONOTA.KENSA_NENGAPI = ? ");
		String sql = buffer.toString();
		return sql;
	}

	/**
	 * 検査結果その他取得(条件別)
	 */
	private static String getSimeiDataSQL()
	{

		StringBuffer buffer = new StringBuffer();
		// edit ver2 s.inoue 2009/08/19
		buffer.append("SELECT ");
		buffer.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME,");
		buffer.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL,");
		buffer.append(" KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU,");
		buffer.append(" MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO,");
		buffer.append(" UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");

		buffer.append(" FROM T_KOJIN ");
		buffer.append(" WHERE ");
		buffer.append(" BIRTHDAY = ? ");
		buffer.append(" AND SEX = ? ");

		return buffer.toString();

	}

	/**
	 * 改行コード
	 */
	private static String LineSeparator = System.getProperty("line.separator");

	/**
	 * エラーメッセージの形式でメッセージを取得する。
	 * @param Message メッセージ
	 * @param Data 読み込み中のデータ
	 * @return フォーマット済みのテキスト
	 */
	private String GetErrorMessage(String Message,JOriginalFormatData Data)
	{
		return "(" + Data.Name + ")" + Message + LineSeparator;
	}

	/**
	 * エラーメッセージの形式でメッセージを取得する。
	 * @param Message メッセージ
	 * @param Name 名前
	 * @return フォーマット済みのテキスト
	 */
	private static String GetErrorMessage(String Message,String Name)
	{
		return "(" + Name + ")" + Message + LineSeparator;
	}

	/**
	 * インポートボタン
	 */
	public void pushedImportButton(ActionEvent e)
	{

		String filePath = jTextField_FileName.getText();

		try{
			if( validateDate.setFilePath(filePath))
			{
				if( validateAsImportPushed(validateDate) )
				{
					if( validateDate.isValidateAsDataSet )
					{
						// logファイル
						String logfile = PropertyUtil.getProperty("log.filename");
						PropertyConfigurator.configure(logfile);

	// edit s.inoue 2010/04/02
	//					if(jRadio_SelectDokuji.isSelected()){
							// 結果取り込み
							if (!checkImportOriginalFormatFile(filePath))
								kekkaImport(filePath);

							// add s.inoue 2010/10/20
							if (!checkImportHL7FormatFile(filePath))
								hl7Import(filePath);
	// edit s.inoue 2010/04/02
	//					}else{
	//						// 日医フォーマット
	//						nitiImportCheck(validateDate.getFilePath());
	//					}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("[HL7取込処理]機関FDBへの接続処理に失敗しました。");
		}
	}

	// add s.inoue 2010/10/20
	/*
	 * 結果取込 csvファイル
	 */
	private boolean checkImportOriginalFormatFile(String FilePath){
		boolean retCheck = false;

		// 1.txt形式かどうか
		if (!FilePath.endsWith(".txt") &&
				!FilePath.endsWith(".TXT") &&
					!FilePath.endsWith(".csv") &&
						!FilePath.endsWith(".CSV"))
			retCheck = true;

		return retCheck;
	}

	/*
	 * 結果取込 xml(HL7)ファイル
	 */
	private boolean checkImportHL7FormatFile(String FilePath){
		boolean retCheck = false;

		// xml or zip形式かどうか
		if (!FilePath.endsWith(".xml") &&
				!FilePath.endsWith(".XML") &&
					!FilePath.endsWith(".zip") &&
						!FilePath.endsWith(".ZIP"))
			retCheck = true;

		return retCheck;
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e)
	{
		dispose();
	}

	class KnsaFileFilter extends FileFilter
	{
		public boolean accept(File f)
		{
			if(f.getName().equals("kakka.txt"))
			{
				return true;
			}else{
				return false;
			}
		}

		public String getDescription() { return ""; }
	}

	/**
	 * 参照ボタン
	 */
	public void pushedOpenFileButton(ActionEvent e)
	{
		FileDialog fd = new FileDialog(this,"検査結果データを指定してください",FileDialog.LOAD);
		fd.setVisible(true);

		if(fd.getDirectory() != null && fd.getFile() != null)
		{
			jTextField_FileName.setText(fd.getDirectory() + fd.getFile());
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}

		if( e.getSource() == jButton_Import )
		{
			logger.info(jButton_Import.getText());
			pushedImportButton( e );
		}

		if( e.getSource() == jButton_OpenFile )
		{
			logger.info(jButton_OpenFile.getText());
			pushedOpenFileButton( e );
		}
	}

	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Import.getText());
			pushedImportButton(null);break;
		}
	}

////hl7前準備↑/////////////////////////////////////////////

	   /* OIDを関連付け */
    private static HashMap<String, String> getOIDAssociation(){
    	HashMap<String, String> hmOID = new HashMap<String, String>();
    	hmOID.put("1.2.392.200119.6.101", "HKNJANUM");
    	hmOID.put("1.2.392.200119.6.204", "HIHOKENJYASYO_KIGOU");
    	hmOID.put("1.2.392.200119.6.205", "HIHOKENJYASYO_NO");
    	hmOID.put("1.2.392.200119.6.209.100012120", "JYUSHIN_SEIRI_NO");
    	hmOID.put("1.2.392.200119.6.102", "TKIKAN_NO");
    	return hmOID;
    }

    /*
     *  全ての健診項目をセット
     */
	private static void setImportKenshinItemList(){
		// add s.inoue 2010/08/06
		importKenshinItem.add("9N001000000000001");//身長
		importKenshinItem.add("9N006000000000001");//体重
		importKenshinItem.add("9N011000000000001");//ＢＭＩ
		importKenshinItem.add("9N021000000000001");//内臓脂肪面積
		importKenshinItem.add("9N016160100000001");//腹囲(実測）
		importKenshinItem.add("9N016160200000001");//腹囲(自己判定）
		importKenshinItem.add("9N016160300000001");//腹囲(自己申告）
		importKenshinItem.add("9N026000000000002");//肥満度
		importKenshinItem.add("9N051000000000049");//業務歴
		importKenshinItem.add("9N056000000000011");//既往歴
		importKenshinItem.add("9N056160400000049");//（具体的な既往歴）
		importKenshinItem.add("9N061000000000011");//自覚症状
		importKenshinItem.add("9N061160800000049");//（所見）
		importKenshinItem.add("9N066000000000011");//他覚症状
		importKenshinItem.add("9N066160800000049");//（所見）
		importKenshinItem.add("9N071000000000049");//その他（家族歴等）
		importKenshinItem.add("9N076000000000049");//視診（口腔内含む）
		importKenshinItem.add("9N081000000000049");//打聴診
		importKenshinItem.add("9N086000000000049");//触診（関節可動域含む）
		importKenshinItem.add("9N091000000000001");//反復唾液嚥下テスト
		importKenshinItem.add("9A755000000000001");//収縮期血圧（その他）
		importKenshinItem.add("9A752000000000001");//収縮期血圧（２回目）
		importKenshinItem.add("9A751000000000001");//収縮期血圧（１回目）
		importKenshinItem.add("9A765000000000001");//拡張期血圧（その他）
		importKenshinItem.add("9A762000000000001");//拡張期血圧（２回目）
		importKenshinItem.add("9A761000000000001");//拡張期血圧（１回目）
		importKenshinItem.add("9N121000000000001");//心拍数
		importKenshinItem.add("9N141000000000011");//採血時間（食後）
		importKenshinItem.add("3F050000002327101");//総コレステロール
		importKenshinItem.add("3F050000002327201");//総コレステロール
		importKenshinItem.add("3F050000002399901");//総コレステロール
		importKenshinItem.add("3F015000002327101");//中性脂肪（トリグリセリド）
		importKenshinItem.add("3F015000002327201");//中性脂肪（トリグリセリド）
		importKenshinItem.add("3F015000002399901");//中性脂肪（トリグリセリド）
		importKenshinItem.add("3F070000002327101");//ＨＤＬコレステロール
		importKenshinItem.add("3F070000002327201");//ＨＤＬコレステロール
		importKenshinItem.add("3F070000002399901");//ＨＤＬコレステロール
		importKenshinItem.add("3F077000002327101");//ＬＤＬコレステロール
		importKenshinItem.add("3F077000002327201");//ＬＤＬコレステロール
		importKenshinItem.add("3F077000002399901");//ＬＤＬコレステロール
		importKenshinItem.add("3J010000002327101");//総ビリルビン
		importKenshinItem.add("3J010000002399901");//総ビリルビン
		importKenshinItem.add("3B035000002327201");//GOT（ＡＳＴ）
		importKenshinItem.add("3B035000002399901");//GOT（ＡＳＴ）
		importKenshinItem.add("3B045000002327201");//GPT（ＡＬＴ）
		importKenshinItem.add("3B045000002399901");//GPT（ＡＬＴ）
		importKenshinItem.add("3B090000002327101");//γ-GT(γ-GTP)
		importKenshinItem.add("3B090000002399901");//γ-GT(γ-GTP)
		importKenshinItem.add("3B070000002327101");//ＡＬＰ
		importKenshinItem.add("3B070000002399901");//ＡＬＰ
		importKenshinItem.add("3C015000002327101");//血清クレアチニン
		importKenshinItem.add("3C015000002399901");//血清クレアチニン
		importKenshinItem.add("3C020000002327101");//血清尿酸
		importKenshinItem.add("3C020000002399901");//血清尿酸
		importKenshinItem.add("3A010000002327101");//総蛋白
		importKenshinItem.add("3A010000002399901");//総蛋白
		importKenshinItem.add("3A015000002327101");//アルブミン
		importKenshinItem.add("3A015000002399901");//アルブミン
		importKenshinItem.add("3A016000002327102");//Ａ／Ｇ
		importKenshinItem.add("5C095000002302301");//血清フェリチン
		importKenshinItem.add("5C095000002399901");//血清フェリチン
		importKenshinItem.add("3D010000001926101");//空腹時血糖
		importKenshinItem.add("3D010000002227101");//空腹時血糖
		importKenshinItem.add("3D010000001927201");//空腹時血糖
		importKenshinItem.add("3D010000001999901");//空腹時血糖
		importKenshinItem.add("3D010129901926101");//随時血糖
		importKenshinItem.add("3D010129902227101");//随時血糖
		importKenshinItem.add("3D010129901927201");//随時血糖
		importKenshinItem.add("3D010129901999901");//随時血糖
		importKenshinItem.add("3D045000001906202");//ＨｂＡ１ｃ
		importKenshinItem.add("3D045000001920402");//ＨｂＡ１ｃ
		importKenshinItem.add("3D045000001927102");//ＨｂＡ１ｃ
		importKenshinItem.add("3D045000001999902");//ＨｂＡ１ｃ
		// add s.inoue 2013/01/31
		importKenshinItem.add("3D046000001906202");//ＨｂＡ１ｃ(NGSP)
		importKenshinItem.add("3D046000001920402");//ＨｂＡ１ｃ(NGSP)
		importKenshinItem.add("3D046000001927102");//ＨｂＡ１ｃ(NGSP)
		importKenshinItem.add("3D046000001999902");//ＨｂＡ１ｃ(NGSP)
                
//masuda^ 2018/03/02 non-HDL 
                importKenshinItem.add("3F069000002391901");
//masuda$

		importKenshinItem.add("1A020000000191111");//尿糖
		importKenshinItem.add("1A020000000190111");//尿糖
		importKenshinItem.add("1A010000000191111");//尿蛋白
		importKenshinItem.add("1A010000000190111");//尿蛋白
		importKenshinItem.add("1A100000000191111");//尿潜血
		importKenshinItem.add("1A100000000190111");//尿潜血
		importKenshinItem.add("1A105160700166211");//尿沈渣（所見の有無）
		importKenshinItem.add("1A105160800166249");//尿沈渣（所見）
		importKenshinItem.add("1A030000000190301");//比重
		importKenshinItem.add("1A030000000199901");//比重
		importKenshinItem.add("2A040000001930102");//ヘマトクリット値
		importKenshinItem.add("2A030000001930101");//血色素量［ヘモグロビン値］
		importKenshinItem.add("2A020000001930101");//赤血球数
		importKenshinItem.add("2A020161001930149");//貧血検査（実施理由）
		importKenshinItem.add("2A060000001930101");//ＭＣＶ
		importKenshinItem.add("2A070000001930101");//ＭＣＨ
		importKenshinItem.add("2A080000001930101");//ＭＣＨＣ
		importKenshinItem.add("2A010000001930101");//白血球数
		importKenshinItem.add("2A050000001930101");//血小板数
		importKenshinItem.add("9A110160700000011");//心電図（所見の有無）
		importKenshinItem.add("9A110160800000049");//心電図（所見）
		importKenshinItem.add("9A110161000000049");//心電図（実施理由）
		importKenshinItem.add("9N201000000000011");//胸部エックス線検査（がん：直接撮影）
		importKenshinItem.add("9N206160700000011");//胸部エックス線検査（一般：直接撮影）（所見の有無）
		importKenshinItem.add("9N206160800000049");//胸部エックス線検査（一般：直接撮影）（所見）
		importKenshinItem.add("9N211161100000049");//胸部エックス線検査（直接撮影）（撮影年月日）
		importKenshinItem.add("9N211161200000049");//胸部エックス線検査（直接撮影）（フィルム番号）
		importKenshinItem.add("9N216000000000011");//胸部エックス線検査（がん：間接撮影）
		importKenshinItem.add("9N221160700000011");//胸部エックス線検査（一般：間接撮影）（所見の有無）
		importKenshinItem.add("9N221160800000049");//胸部エックス線検査（一般：間接撮影）（所見）
		importKenshinItem.add("9N226161100000049");//胸部エックス線検査（間接撮影）（撮影年月日）
		importKenshinItem.add("9N226161200000049");//胸部エックス線検査（間接撮影）（フィルム番号）
		importKenshinItem.add("6A010160706170411");//喀痰検査 （塗抹鏡検　一般細菌）（所見の有無）
		importKenshinItem.add("6A010160806170449");//喀痰検査 （塗抹鏡検　一般細菌）（所見）
		importKenshinItem.add("6A205000006171711");//喀痰検査（塗抹鏡検　抗酸菌）
		importKenshinItem.add("6A205165606171711");//喀痰検査（ガフキー号数）
		importKenshinItem.add("7A010000006143311");//喀痰細胞診検査
		importKenshinItem.add("9N251000000000011");//胸部ＣＴ検査（がん）
		importKenshinItem.add("9N251160700000011");//胸部ＣＴ検査（所見の有無）
		importKenshinItem.add("9N251160800000049");//胸部ＣＴ検査（所見）
		importKenshinItem.add("9N251161100000049");//胸部ＣＴ検査（撮影年月日）
		importKenshinItem.add("9N251161200000049");//胸部ＣＴ検査（フィルム番号）
		importKenshinItem.add("9N256160700000011");//上部消化管エックス線（直接撮影）（所見の有無）
		importKenshinItem.add("9N256160800000049");//上部消化管エックス線（直接撮影）（所見）
		importKenshinItem.add("9N256161100000049");//上部消化管エックス線（直接撮影）（撮影年月日）
		importKenshinItem.add("9N256161200000049");//上部消化管エックス線（直接撮影）（フィルム番号）
		importKenshinItem.add("9N261160700000011");//上部消化管エックス線（間接撮影）（所見の有無）
		importKenshinItem.add("9N261160800000049");//上部消化管エックス線（間接撮影）（所見）
		importKenshinItem.add("9N261161100000049");//上部消化管エックス線（間接撮影）（撮影年月日）
		importKenshinItem.add("9N261161200000049");//上部消化管エックス線（間接撮影）（フィルム番号）
		importKenshinItem.add("9N266160700000011");//上部消化管内視鏡検査（所見の有無）
		importKenshinItem.add("9N266160800000049");//上部消化管内視鏡検査（所見）
		importKenshinItem.add("3B339000002399811");//ペプシノゲン
		importKenshinItem.add("9F130160700000011");//腹部超音波（所見の有無）
		importKenshinItem.add("9F130160800000049");//腹部超音波（所見）
		importKenshinItem.add("9N271160700000011");//婦人科診察（所見の有無）
		importKenshinItem.add("9N271160800000049");//婦人科診察（所見）
		importKenshinItem.add("9N276160700000011");//乳房視触診（所見の有無）
		importKenshinItem.add("9N276160800000049");//乳房視触診（所見）
		importKenshinItem.add("9N281160700000011");//乳房画像診断（マンモグラフィー）（所見の有無）
		importKenshinItem.add("9N281160800000049");//乳房画像診断（マンモグラフィー）（所見）
		importKenshinItem.add("9F140160700000011");//乳房超音波検査（所見の有無）
		importKenshinItem.add("9F140160800000049");//乳房超音波検査（所見）
		importKenshinItem.add("9N291160700000011");//子宮頚部視診（所見の有無）
		importKenshinItem.add("9N291160800000049");//子宮頚部視診（所見）
		importKenshinItem.add("9N296160700000011");//子宮内診（所見の有無）
		importKenshinItem.add("9N296160800000049");//子宮内診(所見）
		importKenshinItem.add("7A021165008543311");//子宮頸部細胞診 (細胞診婦人科材料）（日母分類）
		importKenshinItem.add("7A021165108543311");//子宮頸部細胞診 (細胞診婦人科材料）（ベセスダ分類）
		importKenshinItem.add("7A022000008543311");//子宮体部細胞診 (細胞診婦人科材料）
		importKenshinItem.add("9Z771160700000011");//直腸肛門機能（2項目以上）（所見の有無）
		importKenshinItem.add("9Z771160800000049");//直腸肛門機能（2項目以上）（所見）
		importKenshinItem.add("9Z770160700000011");//直腸肛門機能（1項目）（所見の有無）
		importKenshinItem.add("9Z770160800000049");//直腸肛門機能（1項目）（所見）
		importKenshinItem.add("1B030000001599811");//便潜血
		importKenshinItem.add("5D305000002399811");//ＰＳＡ（前立腺特異抗原）
		importKenshinItem.add("9C310000000000001");//肺機能検査（努力肺活量）
		importKenshinItem.add("9C320000000000001");//肺機能検査（１秒量）
		importKenshinItem.add("9C330000000000002");//肺機能検査（１秒率）
		importKenshinItem.add("9C380000000000002");//肺機能検査（％ＶＣ）
		importKenshinItem.add("9E160162100000001");//視力（右）
		importKenshinItem.add("9E160162500000001");//視力（右：矯正）
		importKenshinItem.add("9E160162200000001");//視力（左）
		importKenshinItem.add("9E160162600000001");//視力（左：矯正）
		importKenshinItem.add("9D100163100000011");//聴力（右、1000Hz）
		importKenshinItem.add("9D100163200000011");//聴力（右、4000Hz）
		importKenshinItem.add("9D100163500000011");//聴力（左、1000Hz）
		importKenshinItem.add("9D100163600000011");//聴力（左、4000Hz）
		importKenshinItem.add("9D100164000000011");//聴力（検査方法）
		importKenshinItem.add("9D100160900000049");//聴力（その他の所見）
		importKenshinItem.add("9E100166000000011");//眼底検査（キースワグナー分類）
		importKenshinItem.add("9E100166100000011");//眼底検査（シェイエ分類：Ｈ）
		importKenshinItem.add("9E100166200000011");//眼底検査（シェイエ分類：Ｓ）
		importKenshinItem.add("9E100166300000011");//眼底検査（SCOTT分類)
		importKenshinItem.add("9E100160900000049");//眼底検査（その他の所見）
		importKenshinItem.add("9E100161000000049");//眼底検査（実施理由）
		importKenshinItem.add("9E105162100000001");//眼圧検査（右）
		importKenshinItem.add("9E105162200000001");//眼圧検査（左）
		importKenshinItem.add("5C070000002306201");//ＣＲＰ
		importKenshinItem.add("5C070000002306301");//ＣＲＰ
		importKenshinItem.add("5C070000002399901");//ＣＲＰ
		importKenshinItem.add("5H010000001910111");//血液型（ＡＢＯ）
		importKenshinItem.add("5H010000001999911");//血液型（ＡＢＯ）
		importKenshinItem.add("5H020000001910111");//血液型（Ｒｈ）
		importKenshinItem.add("5H020000001999911");//血液型（Ｒｈ）
		importKenshinItem.add("5E071000002399811");//梅毒反応
		importKenshinItem.add("5F016141002399811");//ＨＢｓ抗原
		importKenshinItem.add("5F360149502399811");//ＨＣＶ抗体
		importKenshinItem.add("5F360149702399811");//ＨＣＶ抗体（力価）
		importKenshinItem.add("5F360150002399811");//ＨＣＶ抗原検査
		importKenshinItem.add("5F360145002399811");//ＨＣＶ核酸増幅検査
		importKenshinItem.add("9N401000000000011");//Ｃ型肝炎ウイルス検診の判定
		importKenshinItem.add("9N406000000000049");//その他の法定特殊健康診断
		importKenshinItem.add("9N411000000000049");//その他の法定検査
		importKenshinItem.add("9N416000000000049");//その他の検査
		importKenshinItem.add("9N501000000000011");//メタボリックシンドローム判定
		importKenshinItem.add("9N506000000000011");//保健指導レベル
		importKenshinItem.add("9N511000000000049");//医師の診断（判定）
		importKenshinItem.add("9N516000000000049");//健康診断を実施した医師の氏名
		importKenshinItem.add("9N521000000000049");//医師の意見
		importKenshinItem.add("9N526000000000049");//意見を述べた医師の氏名
		importKenshinItem.add("9N531000000000049");//歯科医師による健康診断
		importKenshinItem.add("9N536000000000049");//歯科医師による健康診断を実施した歯科医師の氏名
		importKenshinItem.add("9N541000000000049");//歯科医師の意見
		importKenshinItem.add("9N546000000000049");//意見を述べた歯科医師の氏名
		importKenshinItem.add("9N551000000000049");//備考
		importKenshinItem.add("9N556000000000011");//生活機能評価の結果１
		importKenshinItem.add("9N561000000000011");//生活機能評価の結果２
		importKenshinItem.add("9N566000000000049");//生活機能評価の結果３
		importKenshinItem.add("9N571000000000049");//医師の診断（判定）（生活機能評価）
		importKenshinItem.add("9N576000000000049");//診断をした医師の氏名（生活機能評価）
		importKenshinItem.add("9N581161300000011");//医師の診断（肺がん検診）（コード）
		importKenshinItem.add("9N581161400000049");//医師の診断（肺がん検診）（自由記載）
		importKenshinItem.add("9N586000000000049");//診断をした医師の氏名（肺がん検診）
		importKenshinItem.add("9N591161300000011");//医師の診断（胃がん検診）（コード）
		importKenshinItem.add("9N591161400000049");//医師の診断（胃がん検診）（自由記載）
		importKenshinItem.add("9N596000000000049");//診断をした医師の氏名（胃がん検診）
		importKenshinItem.add("9N601161300000011");//医師の診断（乳がん検診）（コード）
		importKenshinItem.add("9N601161400000049");//医師の診断（乳がん検診）（自由記載）
		importKenshinItem.add("9N606000000000049");//診断をした医師の氏名（乳がん検診）
		importKenshinItem.add("9N611161300000011");//医師の診断（子宮がん検診）（コード）
		importKenshinItem.add("9N611161400000049");//医師の診断（子宮がん検診）（自由記載）
		importKenshinItem.add("9N616000000000049");//診断をした医師の氏名（子宮がん検診）
		importKenshinItem.add("9N621161300000011");//医師の診断（大腸がん検診）（コード）
		importKenshinItem.add("9N621161400000049");//医師の診断（大腸がん検診）（自由記載）
		importKenshinItem.add("9N626000000000049");//診断をした医師の氏名
		importKenshinItem.add("9N631161300000011");//医師の診断（前立腺がん検診）（コード）
		importKenshinItem.add("9N631161400000049");//医師の診断（前立腺がん検診）（自由記載）
		importKenshinItem.add("9N636000000000049");//診断を医師の氏名（前立腺がん検診）
		importKenshinItem.add("9N641000000000049");//医師の診断（その他）
		importKenshinItem.add("9N646000000000049");//診断をした医師の氏名（その他）
		importKenshinItem.add("9N701000000000011");//服薬１（血圧）
		importKenshinItem.add("9N701167000000049");//（薬剤）
		importKenshinItem.add("9N701167100000049");//（服薬理由）
		importKenshinItem.add("9N706000000000011");//服薬２（血糖）
		importKenshinItem.add("9N706167000000049");//（薬剤）
		importKenshinItem.add("9N706167100000049");//（服薬理由）
		importKenshinItem.add("9N711000000000011");//服薬３（脂質）
		importKenshinItem.add("9N711167000000049");//（薬剤）
		importKenshinItem.add("9N711167100000049");//（服薬理由）
		importKenshinItem.add("9N716000000000011");//既往歴１（脳血管）
		importKenshinItem.add("9N721000000000011");//既往歴２（心血管）
		importKenshinItem.add("9N726000000000011");//既往歴３（腎不全・人工透析）
		importKenshinItem.add("9N731000000000011");//貧血
		importKenshinItem.add("9N736000000000011");//喫煙
		importKenshinItem.add("9N741000000000011");//２０歳からの体重変化
		importKenshinItem.add("9N746000000000011");//３０分以上の運動習慣
		importKenshinItem.add("9N751000000000011");//歩行又は身体活動
		importKenshinItem.add("9N756000000000011");//歩行速度
		importKenshinItem.add("9N761000000000011");//1年間の体重変化
		importKenshinItem.add("9N766000000000011");//食べ方1（早食い等）
		importKenshinItem.add("9N771000000000011");//食べ方２（就寝前）
		importKenshinItem.add("9N776000000000011");//食べ方３（夜食/間食）
		importKenshinItem.add("9N781000000000011");//食習慣
		importKenshinItem.add("9N786000000000011");//飲酒
		importKenshinItem.add("9N791000000000011");//飲酒量
		importKenshinItem.add("9N796000000000011");//睡眠
		importKenshinItem.add("9N801000000000011");//生活習慣の改善
		importKenshinItem.add("9N806000000000011");//保健指導の希望
		importKenshinItem.add("9N811000000000011");//１．バスや電車で1人で外出していますか
		importKenshinItem.add("9N816000000000011");//２．日用品の買物をしていますか
		importKenshinItem.add("9N821000000000011");//３．預貯金の出し入れをしていますか
		importKenshinItem.add("9N826000000000011");//４．友人の家を訪ねていますか
		importKenshinItem.add("9N831000000000011");//５．家族や友人の相談にのっていますか
		importKenshinItem.add("9N836000000000011");//６．階段を手すりや壁をつたわらずに昇っていますか
		importKenshinItem.add("9N841000000000011");//７．椅子に座った状態から何もつかまらずに立ち上がっていますか
		importKenshinItem.add("9N846000000000011");//８．15分位続けて歩いていますか
		importKenshinItem.add("9N851000000000011");//９．この1年間に転んだことがありますか
		importKenshinItem.add("9N856000000000011");//１０．転倒に対する不安は大きいですか
		importKenshinItem.add("9N861000000000011");//１１．6ヵ月間で2～3kg以上の体重減少がありましたか
		importKenshinItem.add("9N866000000000001");//１２．身長　　　　　ｃｍ　　　体重　　　　　ｋｇ　（ＢＭＩ＝　　　　）
		importKenshinItem.add("9N871000000000011");//１３．半年前に比べて固いものが食べにくくなりましたか
		importKenshinItem.add("9N876000000000011");//１４．お茶や汁物等でむせることがありますか
		importKenshinItem.add("9N881000000000011");//１５．口の渇きが気になりますか
		importKenshinItem.add("9N886000000000011");//１６．週に１回以上は外出していますか
		importKenshinItem.add("9N891000000000011");//１７．昨年と比べて外出の回数が減っていますか
		importKenshinItem.add("9N896000000000011");//１８．周りの人から「いつも同じ事を聞く」などの物忘れがあると言われますか
		importKenshinItem.add("9N901000000000011");//１９．自分で電話番号を調べて、電話をかけることをしていますか
		importKenshinItem.add("9N906000000000011");//２０．今日が何月何日かわからない時がありますか
		importKenshinItem.add("9N911000000000011");//２１．（ここ2週間）毎日の生活に充実感がない
		importKenshinItem.add("9N916000000000011");//２２．（ここ2週間）これまで楽しんでやれていたことが楽しめなくなった
		importKenshinItem.add("9N921000000000011");//２３．（ここ2週間）以前は楽にできていたことが今ではおっくうに感じられる
		importKenshinItem.add("9N926000000000011");//２４．（ここ2週間）自分が役に立つ人間だと思えない
		importKenshinItem.add("9N931000000000011");//２５．（ここ2週間）わけもなく疲れたような感じがする

		// add s.inoue 2010/08/06
		// 追加健診-新規追加項目
		// edit s.inoue 2010/08/30
		importKenshinItem.add("1A020106000190111");//負荷１時間後尿糖
		importKenshinItem.add("1A020106000191111");//負荷１時間後尿糖
		importKenshinItem.add("1A020112000190111");//負荷２時間後尿糖
		importKenshinItem.add("1A020112000191111");//負荷２時間後尿糖
		importKenshinItem.add("1A035000000190101");//尿ｐＨ
		importKenshinItem.add("1A040000000190111");//尿ウロビリノーゲン定性
		importKenshinItem.add("1A055000000190111");//尿ビリルビン定性
		importKenshinItem.add("1A060000000190111");//尿ケトン体定性
		importKenshinItem.add("1A065000000190111");//尿アミラーゼ定性
		importKenshinItem.add("1A105000000166251");//尿沈渣－赤血球
		importKenshinItem.add("3A010000002327101");//総蛋白
		importKenshinItem.add("3A010000002399901");//総蛋白

		importKenshinItem.add("1A105000000166253");//尿沈渣－上皮細胞
		importKenshinItem.add("1A105000000166266");//尿沈渣－円柱
		importKenshinItem.add("1A105000000166294");//尿沈渣－その他
		importKenshinItem.add("1B010000001570111");//虫卵判定(塗抹)(便)
		importKenshinItem.add("1B025000001570111");//ぎょう虫卵判定(便)
		importKenshinItem.add("1B040Z121015Z0111");//免疫便潜血反応(1日
		importKenshinItem.add("1B040Z122015Z0111");//免疫便潜血反応(2日
		importKenshinItem.add("2A090000001930101");//好酸球数
		importKenshinItem.add("2A110000001963202");//網赤血球数
		importKenshinItem.add("2A160000001960351");//血液像－好中球(Neu
		importKenshinItem.add("2A160000001960352");//血液像－好中球桿状
		importKenshinItem.add("2A160000001960353");//血液像－好中球分葉
		importKenshinItem.add("2A160000001960354");//血液像－好酸球(Eos
		importKenshinItem.add("2A160000001960355");//血液像－好塩基球(B
		importKenshinItem.add("2A160000001960356");//血液像－単球(Mono)
		importKenshinItem.add("2A160000001960357");//血液像－リンパ球(L
		importKenshinItem.add("2A160000001960377");//血液像－その他
		importKenshinItem.add("2B010000001732001");//出血時間
		importKenshinItem.add("2B020000002231151");//活性化部分トロンボ
		importKenshinItem.add("2B020000002231157");//活性化部分トロンボ
		importKenshinItem.add("2B030000002231151");//プロトロンビン時間
		importKenshinItem.add("2B030000002231157");//プロトロンビン時間
		importKenshinItem.add("2Z010000001992001");//血沈1時間値
		importKenshinItem.add("3A015000000106101");//尿中アルブミン
		importKenshinItem.add("3A025000002329201");//TTT
		importKenshinItem.add("3A030000002329201");//ZTT
		importKenshinItem.add("3B010000002327101");//CK(CPK)
		importKenshinItem.add("3B010000002327201");//CK(CPK)
		importKenshinItem.add("3B010000002399801");//CK(CPK)
		importKenshinItem.add("3B050000002327101");//LDH
		importKenshinItem.add("3B050000002327201");//LDH
		importKenshinItem.add("3B050000002399801");//LDH
		importKenshinItem.add("3B110000002327101");//コリンエステラーゼ
		importKenshinItem.add("3B110000002327201");//コリンエステラーゼ
		importKenshinItem.add("3B110000002399801");//コリンエステラーゼ
		importKenshinItem.add("3B135000002327101");//LAP
		importKenshinItem.add("3B135000002399801");//LAP
		importKenshinItem.add("3B160000000127101");//尿アミラーゼ定量
		importKenshinItem.add("3B160000002327101");//血清アミラーゼ
		importKenshinItem.add("3B160000002399801");//血清アミラーゼ
		importKenshinItem.add("3B180000002327101");//血清リパーゼ
		importKenshinItem.add("3B180000002327201");//血清リパーゼ
		importKenshinItem.add("3B180000002399801");//血清リパーゼ
		importKenshinItem.add("3B220000002327101");//総酸性フォスファタ
		importKenshinItem.add("3B220000002327201");//総酸性フォスファタ
		importKenshinItem.add("3B220000002388801");//総酸性フォスファタ
		importKenshinItem.add("3B340000002399801");//ペプシノーゲン１定
		importKenshinItem.add("3B345000002399801");//ペプシノーゲン２定
		importKenshinItem.add("3C025000002327101");//BUN(尿素窒素)
		importKenshinItem.add("3C025000002327201");//BUN(尿素窒素)
		importKenshinItem.add("3C025000002399801");//BUN(尿素窒素)
		importKenshinItem.add("3D010100001926101");//負荷前血糖値
		importKenshinItem.add("3D010100001927201");//負荷前血糖値
		importKenshinItem.add("3D010100001999901");//負荷前血糖値
		importKenshinItem.add("3D010100002227101");//負荷前血糖値
		importKenshinItem.add("3D010106001926101");//負荷1時間後血糖値
		importKenshinItem.add("3D010106001927201");//負荷1時間後血糖値
		importKenshinItem.add("3D010106001999901");//負荷1時間後血糖値
		importKenshinItem.add("3D010106002227101");//負荷1時間後血糖値
		importKenshinItem.add("3D010112001926101");//負荷2時間後血糖値
		importKenshinItem.add("3D010112001927201");//負荷2時間後血糖値
		importKenshinItem.add("3D010112001999901");//負荷2時間後血糖値
		importKenshinItem.add("3D010112002227101");//負荷2時間後血糖値
		importKenshinItem.add("3D055000002320402");//グリコアルブミン
		importKenshinItem.add("3F035000002327101");//遊離脂肪酸
		importKenshinItem.add("3F035000002399801");//遊離脂肪酸
		importKenshinItem.add("3F065000002327101");//遊離型コレステロー
		importKenshinItem.add("3F065000002327201");//遊離型コレステロー
		importKenshinItem.add("3F065000002399801");//遊離型コレステロー
		importKenshinItem.add("3F110000002327101");//胆汁酸
		importKenshinItem.add("3F110000002399801");//胆汁酸
		importKenshinItem.add("3F130000002306101");//ベータリポ蛋白
		importKenshinItem.add("3F130000002329201");//ベータリポ蛋白
		importKenshinItem.add("3F130000002399801");//ベータリポ蛋白
		importKenshinItem.add("3F180000002399801");//アポ蛋白A1
		importKenshinItem.add("3F185000002399801");//アポ蛋白A2
		importKenshinItem.add("3F190000002399801");//アポ蛋白B
		importKenshinItem.add("3H010000002326101");//血清ナトリウム
		importKenshinItem.add("3H015000002326101");//血清カリウム
		importKenshinItem.add("3H020000002326101");//血清クロール
		importKenshinItem.add("3H030000002327101");//血清総カルシウム
		importKenshinItem.add("3H040000002327101");//血清無機リン
		importKenshinItem.add("3H040000002327201");//血清無機リン
		importKenshinItem.add("3I010000002327101");//血清鉄
		importKenshinItem.add("3J015000002327101");//直接ビリルビン
		importKenshinItem.add("4A025000002299801");//ACTH定量
		importKenshinItem.add("4A055000002399801");//TSH定量
		importKenshinItem.add("4B010000002399801");//T3定量
		importKenshinItem.add("4B015000002399801");//FT3定量
		importKenshinItem.add("4B035000002399801");//FT4定量
		importKenshinItem.add("5D010000002399801");//CEA定量
		importKenshinItem.add("5D015000002302311");//AFP判定
		importKenshinItem.add("5D015000002399801");//AFP定量
		importKenshinItem.add("5D100000002399801");//CA125定量
		importKenshinItem.add("5D120000002399801");//CA15-3定量
		importKenshinItem.add("5D130000002399801");//CA19-9定量
		importKenshinItem.add("5E035000002306101");//ASO定量
		importKenshinItem.add("5E035000002315305");//ASO(希釈倍数)
		importKenshinItem.add("5E065000000102311");//尿中ヘリコバクター
		importKenshinItem.add("5E065000002302311");//血清ヘリコバクター
		importKenshinItem.add("5G160000002311611");//リウマトイド因子定
		importKenshinItem.add("6Z100000009920311");//ヘリコバクターピロ
		importKenshinItem.add("6Z200000007190111");//ヘリコバクターピロ
		importKenshinItem.add("9A150160700000011");//ホルター型心電図検
		importKenshinItem.add("9A150160800000049");//ホルター型心電図検
		importKenshinItem.add("9A300160700000011");//マスター2段階負荷試
		importKenshinItem.add("9A300160800000049");//マスター2段階負荷試
		importKenshinItem.add("9A320160700000011");//トレッドミル負荷心
		importKenshinItem.add("9A320160800000049");//トレッドミル負荷心
		importKenshinItem.add("9A340160700000011");//エルゴメーター負荷
		importKenshinItem.add("9A340160800000049");//エルゴメーター負荷
		importKenshinItem.add("9A350160700000011");//心肺運動負荷試験(所
		importKenshinItem.add("9A350160800000049");//心肺運動負荷試験(所
		importKenshinItem.add("9I200160700000011");//直腸内視鏡検査(所見
		importKenshinItem.add("9I200160800000049");//直腸内視鏡検査(所見
		importKenshinItem.add("9Z5060000Z9125001");//骨塩定量(DPA法)
		importKenshinItem.add("9Z5060000Z9125002");//骨塩定量(DPA法)対Y
		importKenshinItem.add("9Z5060000Z9125049");//骨塩定量(DPA法)
		importKenshinItem.add("9Z5060000Z91250Z2");//骨塩定量(DPA法)対同
		importKenshinItem.add("9Z5070000Z9225001");//骨塩定量(DXA/DEX法
		importKenshinItem.add("9Z5070000Z9225002");//骨塩定量(DXA/DEX法
		importKenshinItem.add("9Z5070000Z9225049");//骨塩定量(DXA/DEX法
		importKenshinItem.add("9Z5070000Z92250Z2");//骨塩定量(DXA/DEX法
		importKenshinItem.add("9Z5110000Z9399101");//骨塩定量(MD法)
		importKenshinItem.add("9Z5110000Z9399102");//骨塩定量(MD法)対YA
		importKenshinItem.add("9Z5110000Z9399149");//骨塩定量(MD法)判定
		importKenshinItem.add("9Z5110000Z93991Z2");//骨塩定量(MD法)対同
		importKenshinItem.add("9Z5120000Z9499101");//骨塩定量(CXD法)
		importKenshinItem.add("9Z5120000Z9499102");//骨塩定量(CXD法)対Y
		importKenshinItem.add("9Z5120000Z9499149");//骨塩定量(CXD法)判定
		importKenshinItem.add("9Z5120000Z94991Z2");//骨塩定量(CXD法)対同
		importKenshinItem.add("9Z5130000Z9599101");//骨塩定量(DIP法)
		importKenshinItem.add("9Z5130000Z9599102");//骨塩定量(DIP法)対Y
		importKenshinItem.add("9Z5130000Z9599149");//骨塩定量(DIP法)判定
		importKenshinItem.add("9Z5130000Z95991Z2");//骨塩定量(DIP法)対同
		importKenshinItem.add("9Z5210000Z9625001");//骨塩定量(QCT/pQCT法
		importKenshinItem.add("9Z5210000Z9625002");//骨塩定量(QCT/pQCT法
		importKenshinItem.add("9Z5210000Z9625049");//骨塩定量(QCT/pQCT法
		importKenshinItem.add("9Z5210000Z96250Z2");//骨塩定量(QCT/pQCT法
		importKenshinItem.add("9Z5260000Z9725001");//骨塩定量(US法)
		importKenshinItem.add("9Z5260000Z9725002");//骨塩定量(US法)対YA
		importKenshinItem.add("9Z5260000Z9725049");//骨塩定量(US法)判定
		importKenshinItem.add("9Z5260000Z97250Z2");//骨塩定量(US法)対同
		importKenshinItem.add("Z5D01000002305101");//サイトケラチン19フ
		importKenshinItem.add("Z9A43Z010Z9725001");//脈波伝播速度baPWV
		importKenshinItem.add("Z9A43Z010Z9725049");//脈波伝播速度baPWV(
		importKenshinItem.add("Z9A43Z010Z97250ZA");//脈波伝播速度baPWV血
		importKenshinItem.add("Z9A43Z012Z97250ZI");//脈波伝播速度(心臓足
		importKenshinItem.add("Z9A75Z009Z9725002");//足関節・上腕血圧比
		importKenshinItem.add("Z9F120000Z9725049");//頚動脈エコー所見
		importKenshinItem.add("Z9N06000000000001");//標準体重
		importKenshinItem.add("Z9N21000000000011");//頭部CT検査(判定)
		importKenshinItem.add("Z9N21160700000011");//頭部CT検査(所見の有
		importKenshinItem.add("Z9N21160800000049");//頭部CT検査(所見)
		importKenshinItem.add("Z9N21161100000049");//頭部CT検査(撮影年月
		importKenshinItem.add("Z9N21161200000049");//頭部CT検査(フィルム
		importKenshinItem.add("Z9N22000000000011");//頭部MRI検査(判定)
		importKenshinItem.add("Z9N22160700000011");//頭部MRI検査(所見の
		importKenshinItem.add("Z9N22160800000049");//頭部MRI検査(所見)
		importKenshinItem.add("Z9N22161100000049");//頭部MRI検査(撮影年
		importKenshinItem.add("Z9N26000000000011");//腹部CT検査(判定)
		importKenshinItem.add("Z9N26160700000011");//腹部CT検査(所見の有
		importKenshinItem.add("Z9N26160800000049");//腹部CT検査(所見)
		importKenshinItem.add("Z9N26161100000049");//腹部CT検査(撮影年月
		importKenshinItem.add("Z9N26161200000049");//腹部CT検査(フィルム
		importKenshinItem.add("Z9N29000000000011");//FDG-PET検査(判定)
		importKenshinItem.add("Z9N29160700000011");//FDG-PET検査(所見の
		importKenshinItem.add("Z9N29160800000049");//FDG-PET検査(所見)
		importKenshinItem.add("Z9N29161100000049");//FDG-PET検査(撮影年
		importKenshinItem.add("ZG001000000000011");//胃部X線検査・指導区
		importKenshinItem.add("ZG002000000000011");//胃内視鏡検査・指導
		importKenshinItem.add("ZG003000000000011");//腹部超音波検査・指
		importKenshinItem.add("ZG004000000000011");//免疫便潜血反応検査
		importKenshinItem.add("ZG005000000000011");//直腸検査・診指導区
		importKenshinItem.add("ZG006000000000011");//乳房検査・指導区分
		importKenshinItem.add("ZG007000000000011");//HBs抗原検査・指導区
		importKenshinItem.add("ZG008000000000011");//血圧指導区分
		importKenshinItem.add("ZG009000000000011");//脂質検査・指導区分
		importKenshinItem.add("ZG010000000000011");//肝機能検査・等指導
		importKenshinItem.add("ZG011000000000011");//血糖検査・指導区分
		importKenshinItem.add("ZG012000000000011");//尿酸検査・指導区分
		importKenshinItem.add("ZG013000000000011");//尿一般・腎機能指導
		importKenshinItem.add("ZGG01000000000011");//総合所見指導区分
		importKenshinItem.add("ZGGA1000000000049");//指導時注意事項
		importKenshinItem.add("ZI200160700000011");//大腸内視鏡検査(所見
		importKenshinItem.add("ZI200160800000049");//大腸内視鏡検査(所見
		importKenshinItem.add("ZI201160700000011");//S状結腸内視鏡検査(
		importKenshinItem.add("ZI201160800000049");//S状結腸内視鏡検査(
		importKenshinItem.add("ZI210160700000011");//注腸造影検査(所見の
		importKenshinItem.add("ZI210160800000049");//注腸造影検査(所見)
		importKenshinItem.add("ZI210161100000049");//注腸造影検査(撮影年
		importKenshinItem.add("ZI210161200000049");//注腸造影検査(フィル
		importKenshinItem.add("ZI220160700000011");//直腸診(所見の有無)
		importKenshinItem.add("ZI220160800000049");//直腸診(所見)
		importKenshinItem.add("ZN001000000000011");//連絡が取れる時間帯
	}
////hl7前準備↑/////////////////////////////////////////////
	/*-------------------------------------共通-----------------------------------------------*/

}
