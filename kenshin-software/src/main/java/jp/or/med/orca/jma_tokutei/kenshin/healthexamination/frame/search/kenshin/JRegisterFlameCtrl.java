package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JConstantString;
import jp.or.med.orca.jma_tokutei.common.component.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedButton;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedTextArea;
import jp.or.med.orca.jma_tokutei.common.component.IDialog;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IKekkaRegisterInputDialog;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.JKekkaRegisterInputDialogFactory;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFormattedFloatControl;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaSonotaDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKensakekaTokuteiDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaSonota;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKensakekaTokutei;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.util.PropertyUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.JSoftware;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintDefine;

import org.apache.log4j.Logger;
import org.openswing.swing.domains.java.DomainPair;

/**
 * 特定健診ソフトウエアのログイン画面のフレームのコントロール
 */
public class JRegisterFlameCtrl extends JRegisterFlame {

	private static final long serialVersionUID = -8339956502621232911L;	// edit n.ohkubo 2014/10/01　追加
	
	//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//	private JKekkaRegisterFrameData validatedData = new JKekkaRegisterFrameData();
	private static org.apache.log4j.Logger logger = Logger.getLogger(JRegisterFlameCtrl.class);
	private ArrayList<Hashtable<String, String>> tableResult = null;
	private ArrayList<Hashtable<String, String>> tableMyTabResult = null;

	// top + texthight = 20でフォーカス移動(20ずつ移動)が調整可能
	private int cntrolTop = 0;
	private int controlLeft = 2;
	private int controlRight = 2;
	private int controlTextHeight = 20;
	private int controlLabelHeight = 20;
//	private int controlButtonHeight = 20;	// edit n.ohkubo 2014/10/01　未使用なので削除
	private int koumoku_nm_Width = 200;
//	private int kensahouhou_Width = 50;	// edit n.ohkubo 2014/10/01　未使用なので削除
	private int kekka_Width = 70;
	private int tani_Width = 64;
	private int zenkai_Width = 50;
	private int hl_Width = 20;
	private int hantei_Width = 60;
//	private int controlButtonWidth = 40;
	// 17 →
	private int forcus_range = 20;
	private int forcus_foward = 100;
	private int forcus_back = 60;

	private IDialog patternSelectDialog;
	private ExtendedCheckBox[] jCheckBox_1 = null;
// del s.inoue 2012/07/13
//	private ExtendedButton[] jButton_1 = null;
	// private ExtendedOpenFormattedFloatControl[] jTextField_1 = null;
	private ExtendedOpenFormattedFloatControl[] jTextField_1 = null;
// edit s.inoue 2012/02/23 廃止
//	private ExtendedOpenComboboxControl[] jComboBox_1 = null;
	private ExtendedComboBox[] jComboBox_1 = null;
	private ExtendedTextArea[] jTextAreaField_1 = null;
	// add s.inoue 2013/02/14
	private JScrollPane[] jTextScrollPane_1 = null;

	private ExtendedCheckBox[] jCheckBox_2 = null;
// del s.inoue 2012/07/13
//	private ExtendedButton[] jButton_2 = null;
	private ExtendedOpenFormattedFloatControl[] jTextField_2 = null;
// edit s.inoue 2012/02/23 廃止
//	private ExtendedOpenComboboxControl[] jComboBox_2 = null;
	private ExtendedComboBox[] jComboBox_2 = null;
	private ExtendedTextArea[] jTextAreaField_2 = null;
	// add s.inoue 2013/02/14
	private JScrollPane[] jTextScrollPane_2 = null;

	private static final String NUMBER_FIELD = "1";
	private static final String CODE_FIELD = "2";
	private static final String TEXT_FIELD = "3";

	private static final String strHighMessageValue = "上限値";
	private static final String strLowMessageValue = "下限値";

	private IKekkaRegisterInputDialog dialogs;
	private JRegisterFlameData validatedData = new JRegisterFlameData();
	private TKensakekaTokuteiDao kensakekaTokuteiDao = null;
	private TKensakekaSonotaDao kensakekaSonotaDao = null;
	private TKensakekaTokutei kensakekaTokutei = null;
	private TKensakekaSonota kensakekaSonota = null;

	private int kenshinPatternNumber = -1;
	private HashMap<String, String> kensaHouhouCodeList = null;
	private boolean isCopy = false;
	private boolean isPrev = false;

	// メタボ、保健指導によりフィールド順がずれる事による調整値
	protected int posHokensido = 0;
	protected int posMetabo = 0;

	private boolean fnc_yearOld_flg = false;
//	private boolean fnc_print_flg = false;

	private JScrollBar jScrollbarKihon = null;
	private JScrollBar jScrollbarSyosai = null;
	private JScrollBar jScrollbarTuika = null;
	private JScrollBar jScrollbarMonshin = null;
	private JScrollBar jScrollbarMyTab = null;

	private static final String CODE_BMI = "9N011000000000001";
	private static final String CODE_HIGHT = "9N001000000000001";
	private static final String CODE_WEIGHT = "9N006000000000001";
	private String BMIFormat = "";
	private String height = "";
	private String weight ="";
	private String kensaKekka ="";
//	private boolean noChangeflg = false;

//	private static final String CODE_HBA1C_4_JDS = "3D045000001999902";
//	private static final String CODE_HBA1C_3_JDS = "3D045000001927102";
//	private static final String CODE_HBA1C_2_JDS = "3D045000001920402";
//	private static final String CODE_HBA1C_1_JDS = "3D045000001906202";

	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;	// edit n.ohkubo 2014/10/01　未使用なので削除
	
	// edit n.ohkubo 2015/03/01　追加　start　スクロール位置調整
	//マイパターンタブのスクロールバーの値
	private List<Integer> scrollValueListMyTab = new ArrayList<Integer>();
	//基本健診タブのスクロールバーの値
	private List<Integer> scrollValueListKenshinTab = new ArrayList<Integer>();
	//詳細健診タブのスクロールバーの値
	private List<Integer> scrollValueListSyosaiTab = new ArrayList<Integer>();
	//追加健診タブのスクロールバーの値
	private List<Integer> scrollValueListTuikaTab = new ArrayList<Integer>();
	//問診等タブのスクロールバーの値
	private List<Integer> scrollValueListMonshinTab = new ArrayList<Integer>();
	// edit n.ohkubo 2015/03/01　追加　end　スクロール位置調整

	// edit n.ohkubo 2015/03/01　追加　start　生活機能問診のツールチップ対応（問診等タブの質問1～25）
	private static final Map<String, String> questionMap;
	static {
		questionMap = new HashMap<String, String>();
		questionMap.put("質問1", "１．バスや電車で1人で外出していますか");
		questionMap.put("質問2", "２．日用品の買物をしていますか");
		questionMap.put("質問3", "３．預貯金の出し入れをしていますか");
		questionMap.put("質問4", "４．友人の家を訪ねていますか");
		questionMap.put("質問5", "５．家族や友人の相談にのっていますか");
		questionMap.put("質問6", "６．階段を手すりや壁をつたわらずに昇っていますか");
		questionMap.put("質問7", "７．椅子に座った状態から何もつかまらずに立ち上がっていますか");
		questionMap.put("質問8", "８．15分位続けて歩いていますか");
		questionMap.put("質問9", "９．この1年間に転んだことがありますか");
		questionMap.put("質問10", "１０．転倒に対する不安は大きいですか");
		questionMap.put("質問11", "１１．6ヵ月間で2～3kg以上の体重減少がありましたか");
		questionMap.put("質問12", "１２．身長　　　　cm　体重　　　　kg　（BMI=　　　　）");
		questionMap.put("質問13", "１３．半年前に比べて固いものが食べにくくなりましたか");
		questionMap.put("質問14", "１４．お茶や汁物等でむせることがありますか");
		questionMap.put("質問15", "１５．口の渇きが気になりますか");
		questionMap.put("質問16", "１６．週に１回以上は外出していますか");
		questionMap.put("質問17", "１７．昨年と比べて外出の回数が減っていますか");
		questionMap.put("質問18", "１８．周りの人から「いつも同じ事を聞く」などの物忘れがあると言われますか");
		questionMap.put("質問19", "１９．自分で電話番号を調べて、電話をかけることをしていますか");
		questionMap.put("質問20", "２０．今日が何月何日かわからない時がありますか");
		questionMap.put("質問21", "２１．（ここ2週間）毎日の生活に充実感がない");
		questionMap.put("質問22", "２２．（ここ2週間）これまで楽しんでやれていたことが楽しめなくなった");
		questionMap.put("質問23", "２３．（ここ2週間）以前は楽にできていたことが今ではおっくうに感じられる");
		questionMap.put("質問24", "２４．（ここ2週間）自分が役に立つ人間だと思えない");
		questionMap.put("質問25", "２５．（ここ2週間）わけもなく疲れたような感じがする");
	}
	// edit n.ohkubo 2015/03/01　追加　end　生活機能問診のツールチップ対応（問診等タブの質問1～25）
	
	public JRegisterFlameCtrl(String patternNo){
		JRegisterFrameTabSetting(null,true);
	}

	/**
	 * コンストラクタ
	 * @param srcData : 引渡しデータ
	 */
	public JRegisterFlameCtrl(
			JKenshinKekkaSearchListFrameData srcData,
			boolean isCopy
			) {

		this.isCopy = isCopy;
		JRegisterFrameTabSetting(srcData,true);
	}

	/*
	 * Dao設定
	 */
	private void setInitilizeDBSetting(){
		try {
			Connection connection = JApplication.kikanDatabase.getMConnection();
			kensakekaTokutei = new TKensakekaTokutei();
			kensakekaSonota = new TKensakekaSonota();
			kensakekaTokuteiDao = (TKensakekaTokuteiDao) DaoFactory.createDao(connection,kensakekaTokutei);
			kensakekaSonotaDao = (TKensakekaSonotaDao) DaoFactory.createDao(connection,kensakekaSonota);
		} catch (Exception e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(e.getMessage());
		}
	}

	/************************* パラメータ設定 start *************************/

//	/*
//	 * テスト用コード
//	 */
//	private JKenshinKekkaSearchListFrameData srcData =null;
//	private void testCodeSetting(JKenshinKekkaSearchListFrameData srcData){
//
//		validatedData.setUketuke_id("201109130001");
//		validatedData.setKensaJissiDate("20100909",false);
//		validatedData.setHokenjyaNumber("11111111");

//		srcData = new JKenshinKekkaSearchListFrameData();
//		srcData.setNAME("あいうえお");
//		srcData.setSEX("男");
//		srcData.setBIRTHDAY(19760404);
//		srcData.setHIHOKENJYASYO_KIGOU("ああ");
//		srcData.setHIHOKENJYASYO_NO("１２３４５");
//	}

	private void initilazeFunctionSetting(){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			// eidt s.inoue 2012/01/19
			sb.append(" WHERE SCREEN_CD IN (");
			sb.append(JQueryConvert.queryConvertAppendComma("001"));
			sb.append(JQueryConvert.queryConvert("002"));
			sb.append(" )");

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(ex.getMessage());
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String functionCd = item.get("FUNCTION_CD");
			if (JApplication.func_yearOldCode.equals(functionCd)){
				fnc_yearOld_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
//			// eidt s.inoue 2012/01/19
//			// 領収書フラグ追加
//			}else if (JApplication.func_printCode.equals(functionCd)){
//				if (item.get("FUNCTION_FLG").equals("1"))jButton_PrintRyoshu.setVisible(true);
			}
		}

	}
	/************************* パラメータ設定 end *************************/

	/************************* 全体パネル設置 start *************************/
	/*
	 * srcData 引渡しデータ
	 * eventFlg イベント分け
	 */

	public void JRegisterFrameTabSetting(JKenshinKekkaSearchListFrameData srcData,boolean isEvent){

		int tabCount = jPanelRegisterCenter.getTabCount();
		// combo reflesh
		if (tabCount >= 5){
			for (int i = 0; i < tabCount; i++) {
				jPanelRegisterCenter.remove(0);
			}
		}

		if (srcData != null){
			validatedData.setUketuke_id(srcData.getUKETUKE_ID());
			validatedData.setUketukePre_id(srcData.getUKETUKEPRE_ID());

			validatedData.setKensaJissiDate(srcData.getKENSA_NENGAPI(),false);
			this.initialDate = srcData.getKENSA_NENGAPI();
			validatedData.setHokenjyaNumber(srcData.getHKNJANUM());
			// eidt s.inoue 2011/12/08
			validatedData.setNameKana(srcData.getNAME());
			// testCodeSetting(srcData);
		}

		setInitilizeDBSetting();
		initilazeFunctionSetting();
		setLeftTableDataSetting(srcData,isEvent);

		try {
			// 通常Tab
			tableResult = JApplication.kikanDatabase.sendExecuteQuery(createCellDataSql());
		} catch (SQLException e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(e.getMessage());
		}

		jCheckBox_1 = new ExtendedCheckBox[tableResult.size()];
//		jButton_1 = new ExtendedButton[tableResult.size()];
		jTextField_1 = new ExtendedOpenFormattedFloatControl[tableResult.size()];
		jComboBox_1 = new ExtendedComboBox[tableResult.size()];
		jTextAreaField_1= new ExtendedTextArea[tableResult.size()];
		// add s.inoue 2013/02/14
		jTextScrollPane_1 = new JScrollPane[tableResult.size()];

		settingTabData(false);

		try {
			// MyTab
			tableMyTabResult = JApplication.kikanDatabase.sendExecuteQuery(createCellDataMyTabSql());
		} catch (SQLException e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(e.getMessage());
		}

		jCheckBox_2 = new ExtendedCheckBox[tableMyTabResult.size()];
//		jButton_2 = new ExtendedButton[tableMyTabResult.size()];
		jTextField_2 = new ExtendedOpenFormattedFloatControl[tableMyTabResult.size()];
		jComboBox_2 = new ExtendedComboBox[tableMyTabResult.size()];

		jTextAreaField_2= new ExtendedTextArea[tableMyTabResult.size()];
		// add s.inoue 2013/02/14
		jTextScrollPane_2 = new JScrollPane[tableMyTabResult.size()];

		kensaHouhouCodeList = JConstantString.getKensahouhou();

		settingTabData(true);

		// add s.inoue 2012/02/13
//		initializeFocusTraversal();

//		if (flg){
////			kenshinPatternItemChange();
//		   	jComboBox_Pattern.addItemListener(new ItemListener() {
//	    	      public void itemStateChanged(ItemEvent e) {
//
//	    	    	  System.out.println(kenshinPatternNumber + " これ" + jComboBox_Pattern.getSelectedIndex());
//		    	    if (e.getStateChange() == e.SELECTED && !isInit) {
//						stateChangedKenshinPatternNumber(
//								validatedData.getUketuke_id(),
//								validatedData.getKensaJissiDate(),
//								false,
//								isNewKekkaData);
//						// stateChangedKenshinPatternNumber("201108250001","20110913",false,isNewKekkaData);
//					}
//	    	      }
//	    	});
//		}
		if (isEvent)
			jComboBox_Pattern.addActionListener(this);

		if (jPanelRegisterCenter.getComponentCount()==0){
			// eidt s.inoue 2011/12/09
			String gridMasterCnt = PropertyUtil.getProperty( "register.myTabPos");
			if (Integer.parseInt(gridMasterCnt) == 1){
				jPanelRegisterCenter.addTab("ﾏｲﾊﾟﾀｰﾝ", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("基本健診", jInnerPanelKenshin);
			if (Integer.parseInt(gridMasterCnt) == 2){
				jPanelRegisterCenter.addTab("ﾏｲﾊﾟﾀｰﾝ", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("詳細健診", jInnerPanelSyosai);
			if (Integer.parseInt(gridMasterCnt) == 3){
				jPanelRegisterCenter.addTab("ﾏｲﾊﾟﾀｰﾝ", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("追加健診", jInnerPanelTuika);
			if (Integer.parseInt(gridMasterCnt) == 4){
				jPanelRegisterCenter.addTab("ﾏｲﾊﾟﾀｰﾝ", jInnerPanelMyTab);
			}
			jPanelRegisterCenter.addTab("問診等", jInnerPanelMonshin);
			if (Integer.parseInt(gridMasterCnt) == 5){
				jPanelRegisterCenter.addTab("ﾏｲﾊﾟﾀｰﾝ", jInnerPanelMyTab);
			}

		}

//		/* 機関選択欄を初期化する。 */
//		this.initializeKikanComboBox();

		/* ログイン画面がアクティブで無くなったとき、スプラッシュ画面を非表示にする。 */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				JSoftware.getSplashFrame().hideSplashWindow();
			}
		});

		this.setVisible(true);
		if (isCopy){
			this.jLabel_SeiriNO.setVisible(true);
			this.jTextField_SeiriNo.setVisible(true);
		}
		if (isEvent){
			// eidt s.inoue 2012/03/07
//			this.jTextField_KenshinJisiDay.grabFocus();
			jTextField_1[0].grabFocus();
//			this.jTextField_KenshinJisiDay.grabFocus();
			this.jComboBox_Pattern.grabFocus();
		}
	}

	/************************* 全体パネル設置 end *************************/

	/************************* 左パネル-タブ設置 start *************************/

	// 左パネル設定処理
	private void setLeftTableDataSetting(JKenshinKekkaSearchListFrameData srcData,boolean isEvent){

		TKensakekaTokutei kensaTokutei = null;

		try {
			String uketuke_id = "";
			if (isCopy && !isPrev){
				uketuke_id = validatedData.getUketukePre_id();
			}else{
				uketuke_id = validatedData.getUketuke_id();
			}

			kensaTokutei = kensakekaTokuteiDao.selectKekkaTokuteiByPrimaryKey(new Long(uketuke_id),new Integer(validatedData.getKensaJissiDate()));

		} catch (Exception ex) {
			ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(ex.getMessage());
		}

		if (kensaTokutei != null){

			// add s.inoue 2013/05/10
			jTextArea_SougouComment.addFocusListener(new FocusAdapter(){
				@Override
				public void focusGained(FocusEvent e) {
				}
				@Override
				public void focusLost(FocusEvent e) {
					Object source = e.getSource();

					if (source instanceof JTextArea){
						String areaText = jTextArea_SougouComment.getText().replaceAll("＠", System.getProperty("line.separator"));
						System.out.println(areaText);
						jTextArea_SougouComment.setText(areaText);
					}
				}
			});

			jTextArea_SougouComment.setText(kensaTokutei.getKOMENTO());
			String kensaJissiDate = validatedData.getKensaJissiDate();

			if (srcData != null){
//				jLabel_Name.setText(srcData.getNAME());	// edit n.ohkubo 2015/03/01　削除
				jLabel_Name.setText(srcData.getKANANAME());	// edit n.ohkubo 2015/03/01　追加
//				jLabel_Name.setToolTipText(srcData.getNAME());	// edit n.ohkubo 2015/03/01　削除
				jLabel_Name.setToolTipText(srcData.getKANANAME());	// edit n.ohkubo 2015/03/01　追加
//				jLabel_Field_Sex.setText(srcData.getSEX());	// edit n.ohkubo 2015/03/01　削除
				jLabel_Field_Sex.setText(("1".equals(srcData.getSEX()) ? "男性" : ("2".equals(srcData.getSEX()) ? "女性" : srcData.getSEX())));	// edit n.ohkubo 2015/03/01　追加
				jLable_Field_BirthDate.setText(String.valueOf(srcData.getBIRTHDAY()));

				jLabel_HiHokenjyaCode.setText(srcData.getHIHOKENJYASYO_KIGOU());
				jLabel_HiHokenjyaNumber.setText(srcData.getHIHOKENJYASYO_NO());
				jLabel_Field_JusinSeiriNo.setText(srcData.getJYUSHIN_SEIRI_NO());
	//			jTextField_KenshinJisiDay.setText(String.valueOf(kensaTokutei.getKENSA_NENGAPI()));

				String selectedItem = String.valueOf(srcData.getBIRTHDAY());

				int age =0;
				if (fnc_yearOld_flg){
					if (!kensaJissiDate.equals("")){
						age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
					}else{
						age = FiscalYearUtil.getFiscalYear(selectedItem);
					}
				}else{
					if (!kensaJissiDate.equals("")){
						age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
					}else{
						age = Integer.parseInt(JYearAge.getAge(selectedItem));
					}
				}
				System.out.println("age:" + age);
				jLabel_Field_Nenrei.setText(String.valueOf(age));
			}

			String jisibi = String.valueOf(kensaTokutei.getKENSA_NENGAPI());

			if (isCopy) {
				validatedData.setKensaJissiDate(kensaJissiDate,false);
				// add s.inoue 2012/03/22
				try {
					Date dt = jTextField_KenshinJisiDay.getTextToDate(jisibi);
					jTextField_KenshinJisiDay.setDate(dt);
				} catch (ParseException e) {
					e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(e.getMessage());
				}

				this.isNewKekkaData = true;
			}else {
				try {
					if (jisibi.equals("")) {
						this.isNewKekkaData = true;

						/* システム日時を入力する。 */
						Calendar cal = Calendar.getInstance();

						String year = JValidate.fillZero(String.valueOf(cal.get(Calendar.YEAR)), 4);
						String month = JValidate.fillZero(String.valueOf(cal.get(Calendar.MONTH) + 1), 2);
						String date = JValidate.fillZero(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2);

						String jissiDateString = year + month + date;

	//					jTextField_Date.setText(jissiDateString);
						Date dt = jTextField_KenshinJisiDay.getTextToDate(jisibi);
						jTextField_KenshinJisiDay.setDate(dt);

						this.validatedData.setKensaJissiDate(jissiDateString,false);

					/* 健診実施日が指定されている場合 */
					} else {
						Date dt = jTextField_KenshinJisiDay.getTextToDate(jisibi);
						jTextField_KenshinJisiDay.setDate(dt);
					}
				} catch (ParseException e) {
					e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(e.getMessage());
				}
			}

			/* 請求区分設定 */
			Integer seikyuKBN = kensaTokutei.getSEIKYU_KBN();

			// メタボ判定、保健指導レベルは別処理
			if (seikyuKBN == null){
				jComboBox_SeikyuKubun.setSelectedIndex(0);
			}else{
				// 1: 基本的な健診 2: 基本的な健診＋詳細な健診の場合 3: 基本的な健診＋追加健診項目健診 4: 基本的な健診＋詳細な健診+追加健診項目 5: 人間ドック
				switch (seikyuKBN) {
				case 1:jComboBox_SeikyuKubun.setSelectedIndex(0);break;
				case 2:jComboBox_SeikyuKubun.setSelectedIndex(1);break;
				case 3:jComboBox_SeikyuKubun.setSelectedIndex(2);break;
				case 4:jComboBox_SeikyuKubun.setSelectedIndex(3);break;
				case 5:jComboBox_SeikyuKubun.setSelectedIndex(4);break;
				}
			}

			/* 健診パターン設定 */
			// eidt s.inoue 2011/12/09
//			if (isEvent && !isInit){
			if (isEvent){
				Integer kenshinPattern = kensaTokutei.getK_P_NO();
				if (kenshinPattern == null){
					// eidt s.inoue 2012/03/22 0→1
					jComboBox_Pattern.setSelectedIndex(0);
				}else{
					// eidt s.inoue 2012/02/21
					if (!kenshinPattern.equals(kenshinPatternNumber)){

						// eidt s.inoue 2013/04/10
						// jComboBox_Pattern.setSelectedIndex(kenshinPattern - 1);
						// 空き番号がある場合のバグの原因
						DomainPair[] pairs = jComboBox_Pattern.getDomain().getDomainPairList();
						int selectedIDX = -1;
					    for (int i = 0; i < pairs.length; ++i){
					    	if (pairs[i] != null)
					    	if (pairs[i].getCode().toString().equals(String.valueOf(kenshinPattern))){
					    		selectedIDX = i;
					    		System.out.println(jComboBox_Pattern.getSelectedIndex());
					    		break;
					    	}
					    }
					    jComboBox_Pattern.setSelectedIndex(selectedIDX);

						kenshinPatternNumber =kenshinPattern;
					}
				}
			// eidt s.inoue 2013/07/09
			}else{
				posHokensido = 0;
				posMetabo = 0;
			}
		} else {
			// add s.inoue 2012/04/24
			this.isNewKekkaData = true;

			// eidt s.inoue 2012/05/29
			if (kenshinPatternNumber < 0)
				kenshinPatternNumber = 1;

			// eidt s.inoue 2013/04/10
			// 空き番号がある場合のバグの原因
			DomainPair[] pairs = jComboBox_Pattern.getDomain().getDomainPairList();
			int selectedIDX = -1;
		    for (int i = 0; i < pairs.length; ++i){
		    	if (pairs[i] != null)
		    	if (pairs[i].getCode().toString().equals(String.valueOf(kenshinPatternNumber))){
		    		selectedIDX = i;
		    		System.out.println(jComboBox_Pattern.getSelectedIndex());
		    		break;
		    	}
		    }

		    // eidt s.inoue 2013/04/10
			// jComboBox_Pattern.setSelectedIndex(kenshinPatternNumber -1);
		    jComboBox_Pattern.setSelectedIndex(selectedIDX);

			/**** ↓↓↓↓↓ add s.inoue 2012/03/22 */
			// tokuteiにデータが無いパターン
			jComboBox_SeikyuKubun.setSelectedIndex(0);
			// eidt s.inoue 2013/07/23 1 → 0 ？なんで
			jComboBox_MetaboHantei.setSelectedIndex(0);
			jComboBox_HokenSidouLevel.setSelectedIndex(0);

			if (srcData != null){
//				jLabel_Name.setText(srcData.getNAME());	// edit n.ohkubo 2015/03/01　削除
				jLabel_Name.setText(srcData.getKANANAME());	// edit n.ohkubo 2015/03/01　追加
//				jLabel_Name.setToolTipText(srcData.getNAME());	// edit n.ohkubo 2015/03/01　削除
				jLabel_Name.setToolTipText(srcData.getKANANAME());	// edit n.ohkubo 2015/03/01　追加
//				jLabel_Field_Sex.setText(srcData.getSEX());	// edit n.ohkubo 2015/03/01　削除
				jLabel_Field_Sex.setText(("1".equals(srcData.getSEX()) ? "男性" : ("2".equals(srcData.getSEX()) ? "女性" : srcData.getSEX())));	// edit n.ohkubo 2015/03/01　追加
				jLable_Field_BirthDate.setText(String.valueOf(srcData.getBIRTHDAY()));

				jLabel_HiHokenjyaCode.setText(srcData.getHIHOKENJYASYO_KIGOU());
				jLabel_HiHokenjyaNumber.setText(srcData.getHIHOKENJYASYO_NO());
				jLabel_Field_JusinSeiriNo.setText(srcData.getJYUSHIN_SEIRI_NO());
//				jTextField_KenshinJisiDay.setText(String.valueOf(kensaTokutei.getKENSA_NENGAPI()));

				// add s.inoue 2012/03/22
				String kensaJissiDate = srcData.getKENSA_NENGAPI();

				try {
					Date dt = jTextField_KenshinJisiDay.getTextToDate(kensaJissiDate);
					jTextField_KenshinJisiDay.setDate(dt);
				} catch (ParseException e) {
					e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(e.getMessage());
				}

				String selectedItem = String.valueOf(srcData.getBIRTHDAY());

				int age =0;
				if (fnc_yearOld_flg){
					if (!kensaJissiDate.equals("")){
						age = FiscalYearUtil.getFiscalYear(selectedItem,kensaJissiDate);
					}else{
						age = FiscalYearUtil.getFiscalYear(selectedItem);
					}
				}else{
					if (!kensaJissiDate.equals("")){
						age = Integer.parseInt(JYearAge.getAge(selectedItem,kensaJissiDate));
					}else{
						age = Integer.parseInt(JYearAge.getAge(selectedItem));
					}
				}
				System.out.println("age:" + age);
				jLabel_Field_Nenrei.setText(String.valueOf(age));
			}
			/**** ↑↑↑↑↑↑ add s.inoue 2012/03/22 */
		}
	}

	/************************* 左パネル-タブ設置 end *************************/

	/************************* 右パネル-タブ設置 start *************************/

	private void settingTabData(boolean myTab){

		if (myTab){
			// MyTab
			jInnerPanelMyTab = new JPanel();
			jInnerPanelMyTab.setLayout(new GridBagLayout());
			jInnerPanelMyTab.setName("jInnerPanelMyTab");
			jInnerTranPanelMyTab = new JPanel();
			jInnerTranPanelMyTab.setLayout(new GridBagLayout());
			jInnerTranPanelMyTab.setName("jInnerTranPanelMyTab");
		}else{
			// 基本項目
			jInnerPanelKenshin = new JPanel();
			jInnerPanelKenshin.setLayout(new GridBagLayout());
			jInnerPanelKenshin.setName("jInnerPanelKenshin");
			// del s.inoue 2011/11/18
//			jInnerPanelKenshin.setFocusable(true);

			jInnerTranPanelKenshin = new JPanel();
			jInnerTranPanelKenshin.setLayout(new GridBagLayout());
			jInnerTranPanelKenshin.setName("jInnerTranPanelKenshin");
			// del s.inoue 2011/11/18
//			jInnerTranPanelKenshin.setFocusable(true);

			// 詳細項目
			jInnerPanelSyosai = new JPanel();
			jInnerPanelSyosai.setLayout(new GridBagLayout());
			jInnerPanelSyosai.setName("jInnerPanelSyosai");
			jInnerTranPanelSyosai = new JPanel();
			jInnerTranPanelSyosai.setLayout(new GridBagLayout());
			jInnerTranPanelSyosai.setName("jInnerTranPanelSyosai");

			// 追加項目
			jInnerPanelTuika = new JPanel();
			jInnerPanelTuika.setLayout(new GridBagLayout());
			jInnerPanelTuika.setName("jInnerPanelTuika");
			jInnerTranPanelTuika = new JPanel();
			jInnerTranPanelTuika.setLayout(new GridBagLayout());
			jInnerTranPanelTuika.setName("jInnerTranPanelTuika");

			// 問診
			jInnerPanelMonshin = new JPanel();
			jInnerPanelMonshin.setLayout(new GridBagLayout());
			jInnerPanelMonshin.setName("jInnerPanelMonshin");
			jInnerTranPanelMonshin = new JPanel();
			jInnerTranPanelMonshin.setLayout(new GridBagLayout());
			jInnerTranPanelMonshin.setName("jInnerTranPanelMonshin");
		}

		int irowAll = 0;
		int preType = 0;
		int irow = 0;

		boolean lastflg = false;
		Hashtable<String, String> databaseItem = null;

		int recordCount = 0;
		if (myTab){
			recordCount = tableMyTabResult.size();
		}else{
			recordCount = tableResult.size();
		}

		// add s.inoue 2013/07/24
//		boolean flgm = false;
//		boolean flgh = false;

		// 通常Tab
		for (int i = 0; i < recordCount; i++) {

			if (myTab){
				databaseItem  = tableMyTabResult.get(i);
			}else{
				databaseItem  = tableResult.get(i);
			}

			String koumokuCD = databaseItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
			String kekkaTi = databaseItem.get(JConstantString.COLUMN_NAME_KEKA_TI);

			// add s.inoue 2012/06/08
			System.out.println(koumokuCD + " " + kekkaTi );

			// メタボ判定、保健指導レベルは別処理
			if (koumokuCD.equals(JConstantString.COMBO_METABO_CODE)){
				// eidt s.inoue 2013/07/24
//				flgm = true;
					posMetabo = i;
				if (kekkaTi.equals("")){
					jComboBox_MetaboHantei.setSelectedIndex(0);
				}else{
					// 基準該当:1 予備軍該当:2 非該当:3 判定不能:4
					jComboBox_MetaboHantei.setSelectedIndex(Integer.valueOf(kekkaTi));
				}
			}else if (koumokuCD.equals(JConstantString.COMBO_HOKENSIDO_CODE)){
				// eidt s.inoue 2013/07/24
//				flgh = true;
					posHokensido = i;
				if (kekkaTi.equals("")){
					jComboBox_HokenSidouLevel.setSelectedIndex(0);
				}else{
					// 積極的支援:1 動機づけ支援:2 なし:3 判定不能:4
					jComboBox_HokenSidouLevel.setSelectedIndex(Integer.valueOf(kekkaTi));
				}
			}else{
				// 既存のデータの読み込みを行う
				// edit s.inoue 2012/05/30
				String typeStr=databaseItem.get(JConstantString.COLUMN_NAME_HISU_FLG);

				// add s.inoue 2012/05/30
				String myTabVal = "";
				if (typeStr.length() == 5){
					myTabVal = typeStr.substring(4, 5);
					typeStr = typeStr.substring(0, 4);
				}

				int type = Integer.valueOf(typeStr);
				if(type != preType )
					irow = 0;

				if (recordCount - 1 == i)
					lastflg = true;

				// edit s.inoue 2012/05/30
				// 明細処理
				if (!myTabVal.equals("")){
					setPanelDataGrid(Integer.valueOf(myTabVal),irow,irowAll,databaseItem,myTab,lastflg);
				}else{
					setPanelDataGrid(type,irow,irowAll,databaseItem,myTab,lastflg);
				}
				preType = type;
				irow++;
				irowAll++;
			}
		}

//		// add s.inoue 2013/07/24
//		if (!myTab && (!flgh || !flgm)){
//			posHokensido = 0;
//			posMetabo = 0;
//		}
		// タブ毎のパネル設定処理
		setInnerTranPanel(myTab);
	}

	/*
	 * 実パネルへの配置処理
	 */
	private void setInnerTranPanel(boolean myTab){

		GridBagConstraints gridCtlTabs = new GridBagConstraints();
		gridCtlTabs.gridx = 0;
		gridCtlTabs.gridy = 1;
		gridCtlTabs.insets = new Insets(cntrolTop, controlLeft, 0, controlRight);
		gridCtlTabs.gridheight = 1;
		gridCtlTabs.weightx = 1.0d;
		gridCtlTabs.weighty = 1.0d;
		gridCtlTabs.fill = GridBagConstraints.BOTH;
		gridCtlTabs.anchor = GridBagConstraints.NORTHWEST;

		if (myTab){
			// MyTabF
			JPanel ajustmentMyTab = new JPanel();
			ajustmentMyTab.setLayout(new BorderLayout());
			ajustmentMyTab.add(jInnerTranPanelMyTab,BorderLayout.NORTH);
			JPanel ajustmentMyTab_left = new JPanel();
			ajustmentMyTab_left.setLayout(new BorderLayout());
			ajustmentMyTab_left.add(ajustmentMyTab,BorderLayout.WEST);

			JScrollPane scrollMyTab = new JScrollPane(ajustmentMyTab_left);
			scrollMyTab.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollMyTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMyTab.setPreferredSize(new Dimension(550,450));
			jScrollbarMyTab = scrollMyTab.getVerticalScrollBar();
			jScrollbarMyTab.setValue(jScrollbarMyTab.getMinimum());
			jInnerPanelMyTab.add(scrollMyTab, gridCtlTabs);
		}else{
			// 基本健診
			JPanel ajustmentKihon = new JPanel();
			ajustmentKihon.setLayout(new BorderLayout());
			ajustmentKihon.add(jInnerTranPanelKenshin,BorderLayout.NORTH);
			JPanel ajustmentKihon_left = new JPanel();
			ajustmentKihon_left.setLayout(new BorderLayout());
			ajustmentKihon_left.add(ajustmentKihon,BorderLayout.WEST);

			JScrollPane scrollKihon = new JScrollPane(ajustmentKihon_left);
			scrollKihon.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollKihon.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollKihon.setPreferredSize(new Dimension(550,450));
			jScrollbarKihon = scrollKihon.getVerticalScrollBar();
			jScrollbarKihon.setValue(jScrollbarKihon.getMinimum());
			jInnerPanelKenshin.add(scrollKihon, gridCtlTabs);

			// 詳細健診
			JPanel ajustmentSyosai = new JPanel();
			ajustmentSyosai.setLayout(new BorderLayout());
			ajustmentSyosai.add(jInnerTranPanelSyosai,BorderLayout.NORTH);
			JPanel ajustmentSyosai_left = new JPanel();
			ajustmentSyosai_left.setLayout(new BorderLayout());
			ajustmentSyosai_left.add(ajustmentSyosai,BorderLayout.WEST);

			JScrollPane scrollSyosai = new JScrollPane(ajustmentSyosai_left);
			scrollSyosai.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollSyosai.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollSyosai.setPreferredSize(new Dimension(550,450));
			jScrollbarSyosai = scrollSyosai.getVerticalScrollBar();
			jScrollbarSyosai.setValue(jScrollbarSyosai.getMinimum());
			jInnerPanelSyosai.add(scrollSyosai, gridCtlTabs);

			// 追加健診
			JPanel ajustmentTuika = new JPanel();
			ajustmentTuika.setLayout(new BorderLayout());
			ajustmentTuika.add(jInnerTranPanelTuika,BorderLayout.NORTH);
			JPanel ajustmentTuika_left = new JPanel();
			ajustmentTuika_left.setLayout(new BorderLayout());
			ajustmentTuika_left.add(ajustmentTuika,BorderLayout.WEST);

			JScrollPane scrollTuika = new JScrollPane(ajustmentTuika_left);
			scrollTuika.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollTuika.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollTuika.setPreferredSize(new Dimension(550,450));
			jScrollbarTuika = scrollTuika.getVerticalScrollBar();
			jScrollbarTuika.setValue(jScrollbarTuika.getMinimum());
			jInnerPanelTuika.add(scrollTuika, gridCtlTabs);

			// 問診
			JPanel ajustmentMonshin = new JPanel();
			ajustmentMonshin.setLayout(new BorderLayout());
			ajustmentMonshin.add(jInnerTranPanelMonshin,BorderLayout.NORTH);
			JPanel ajustmentMonshin_left = new JPanel();
			ajustmentMonshin_left.setLayout(new BorderLayout());
			ajustmentMonshin_left.add(ajustmentMonshin,BorderLayout.WEST);

			JScrollPane scrollMonshin = new JScrollPane(ajustmentMonshin_left);
			scrollMonshin.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollMonshin.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollMonshin.setPreferredSize(new Dimension(550,450));
			jScrollbarMonshin = scrollMonshin.getVerticalScrollBar();
			jScrollbarMonshin.setValue(jScrollbarMonshin.getMinimum());
			jInnerPanelMonshin.add(scrollMonshin, gridCtlTabs);
		}
	}

	/************************* 右パネル-タブ設置 end *************************/

	/************************* 入力フィールド設置 start *************************/

	private void setPanelDataGrid(int type,final int irow,int irowAll,Hashtable<String, String> dbItem,boolean myTab,boolean lastFlg){

		GridBagConstraints[] gridCtl = null;

		gridCtl = new GridBagConstraints[8];

		for (int i = 0; i < 8; i++) {
			gridCtl[i] = new GridBagConstraints();
			gridCtl[i].gridx = i;
			gridCtl[i].gridy = irow;
			gridCtl[i].anchor = GridBagConstraints.NORTHWEST;
			gridCtl[i].insets = new Insets(cntrolTop, controlLeft, 0, 0);
		}

		// ヘッダ処理移動
		/* 明細項目 */
		JLabel jLabelKoumokuNm = new JLabel();
		// add s.inoue 2013/01/25
		// hba1c区別
		String tmpCd = dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
		String tmpAdd = "";
		if (tmpCd.equals("3D045000001906202") || tmpCd.equals("3D045000001920402") || tmpCd.equals("3D045000001927102") || tmpCd.equals("3D045000001999902")){
			tmpAdd = "(JDS)";
		}else if (tmpCd.equals("3D046000001906202") || tmpCd.equals("3D046000001920402") || tmpCd.equals("3D046000001927102") || tmpCd.equals("3D046000001999902")){
			tmpAdd = "(NGSP)";
		}

		jLabelKoumokuNm.setText(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME) + tmpAdd);
//		jLabelKoumokuNm.setToolTipText(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME));	// edit n.ohkubo 2015/03/01　削除
		// edit n.ohkubo 2015/03/01　追加　start　生活機能問診のツールチップ対応（問診等タブの質問1～25）
		if (questionMap.get(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME)) == null) {
			jLabelKoumokuNm.setToolTipText(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME));
		} else {
			jLabelKoumokuNm.setToolTipText(questionMap.get(dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME)));
		}
		// edit n.ohkubo 2015/03/01　追加　end　生活機能問診のツールチップ対応（問診等タブの質問1～25）
		
		jLabelKoumokuNm.setPreferredSize(new Dimension(koumoku_nm_Width, controlLabelHeight));
		jLabelKoumokuNm.setFocusable(false);

		// add s.inoue 2012/05/30
		String hisu_flg = dbItem.get(JConstantString.COLUMN_NAME_HISU_FLG);
		Color koumoku_color = null;

		// add s.inoue 2012/05/30
		if (hisu_flg.length() == 5)
			hisu_flg = hisu_flg.substring(4, 5);
		if (hisu_flg.equals(JApplication.HISU_FLG_BASE)){
			koumoku_color = ViewSettings.getRequiedItemFrColor();
		}else if (hisu_flg.equals(JApplication.HISU_FLG_SYOSAI)){
			koumoku_color = ViewSettings.getSyosaiItemFrColor();
		}else if (hisu_flg.equals(JApplication.HISU_FLG_TUIKA) || hisu_flg.equals(JApplication.HISU_FLG_MONSHIN)){
			koumoku_color = ViewSettings.getTuikaItemFrColor();
		}
//		jLabelKoumokuNm.setBackground(koumoku_color);
		jLabelKoumokuNm.setForeground(koumoku_color);

		JLabel jLabelTani = new JLabel();
		jLabelTani.setPreferredSize(new Dimension(tani_Width, controlTextHeight));
		jLabelTani.setText(dbItem.get(JConstantString.COLUMN_NAME_TANI));
		jLabelTani.setFocusable(false);

		// 1:数字 2:コード 3:文字列
		String dataType = dbItem.get(JConstantString.COLUMN_NAME_DATA_TYPE);
		String koumokuCD = dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
//		String koumokuNM = dbItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME);	// edit n.ohkubo 2014/10/01　未使用なので削除
		String kensaHouhou = dbItem.get(JConstantString.COLUMN_NAME_KENSA_HOUHOU);
		String kekaTi = dbItem.get(JConstantString.COLUMN_NAME_KEKA_TI);
		String hisTi = dbItem.get(JConstantString.COLUMN_NAME_HIS_TI);
		String hl = dbItem.get(JConstantString.COLUMN_NAME_H_L);
		String hantei = dbItem.get(JConstantString.COLUMN_NAME_HANTEI);
		String jisiKbn = dbItem.get(JConstantString.COLUMN_NAME_JISI_KBN);
		String maxLength = dbItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);

// del s.inoue 2012/07/13
//		boolean houhouflg = false;
//    	HashMap<String,ArrayList<String>> hm = JConstantString.getKensaHouhouMap(koumokuCD);
//    	String keyStr =(String)hm.keySet().toArray()[0];
//    	ArrayList<String> arr = hm.get(keyStr);
//    	if (arr != null)
//    		houhouflg = true;

		if (myTab){
			jCheckBox_2[irowAll] = new ExtendedCheckBox();
			jCheckBox_2[irowAll].setFocusable(false);
			jCheckBox_2[irowAll].setName(koumokuCD);
			// add s.inoue 2012/03/07
			jCheckBox_2[irowAll].setToolTipText("<html>チェックなし：実施<br>チェック：測定不能</html>");

			jCheckBox_2[irowAll].addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					Object source = e.getSource();

					if (source instanceof JCheckBox){
						JCheckBox chk = (JCheckBox)source;
//						ExtendedOpenCheckboxControl eChk = (ExtendedOpenCheckboxControl)chk.getParent();
//						if ((eChk == null))
//							return;

						// タブへの連動処理(defTab
						String koumokuCD = chk.getName();
						for (int i = 0; i < jCheckBox_1.length; i++) {
							if (jCheckBox_1[i] != null)
							if (jCheckBox_1[i].getName().equals(koumokuCD)){
								if (chk.isSelected()){
									jCheckBox_1[i].setSelected(true);
								}else{
									jCheckBox_1[i].setSelected(false);
								}
								break;
							}
						}
					}
				}
			});

//			// mytab処理
//			jButton_2[irowAll] = new ExtendedButton();
//			jButton_2[irowAll].setText("方法");
//			jButton_2[irowAll].setToolTipText(kensaHouhou);
//			jButton_2[irowAll].setPreferredSize(new Dimension(kensahouhou_Width, controlButtonHeight));
//			jButton_2[irowAll].addActionListener(this);
//			jButton_2[irowAll].addKeyListener(this);
//			jButton_2[irowAll].setFocusable(false);
//			jButton_2[irowAll].setName(koumokuCD);

			// タイプ毎のフィールド生成
			if (dataType.equals(NUMBER_FIELD)){
				jTextField_2[irowAll] = new ExtendedOpenFormattedFloatControl(ImeMode.IME_OFF);
				jTextField_2[irowAll].setPreferredSize(new Dimension(kekka_Width, controlTextHeight));
				// eidt s.inoue 2012/07/13
				// jTextField_2[irowAll].setToolTipText(koumokuNM);
				jTextField_2[irowAll].setToolTipText(kensaHouhou);
				jTextField_2[irowAll].setName(koumokuCD);
				if (koumokuCD.equals(CODE_BMI)){
					jTextField_2[irowAll].setEnabled(false);
					BMIFormat =  maxLength;
				}
				jTextField_2[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
					}
					@Override
					public void keyReleased(KeyEvent e) {
					}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								jScrollbarMyTab.setValue(irow*forcus_range - forcus_back);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMyTab, irow, true);
								jScrollbarMyTab.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else{
//								jScrollbarMyTab.setValue(irow*forcus_range - forcus_foward);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMyTab, irow, false);
								jScrollbarMyTab.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}
						}
					}
				});
				jTextField_2[irowAll].addFocusListener(new FocusAdapter(){
					@Override
					public void focusGained(FocusEvent e) {
						JTextField txt = (JTextField)e.getSource();

						for (int i = 0; i < jTextField_2.length; i++) {
							if ( jTextField_2[i]!= null){
								// eidt s.inoue 2012/02/24
								// if (txt.getParent().getName().equals(jTextField_2[i].getName())){
								if (txt.getName().equals(jTextField_2[i].getName())){
									String houhou =kensaHouhouCodeList.get(jTextField_2[i].getName());
									houhou = (houhou==null)?"":houhou;
									jLabel_KensaHouhou_Value.setText(houhou);
									((JTextField)e.getSource()).setBackground(JApplication.backColor_Focus);
								}
							}
						}
					}
					@Override
					public void focusLost(FocusEvent e) {
						Object source = e.getSource();

						if (source instanceof JTextField){
							JTextField txt = (JTextField)source;
							// eidt s.inoue 2012/02/24
							// ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt.getParent();
							ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt;

							// add s.inoue 2012/07/13
							jLabel_KensaHouhou_Value.setText("");

							if ((eTxt == null))
								return;
							// add s.inoue 2012/02/02
							((JTextField)e.getSource()).setBackground(JApplication.backColor_UnFocus);

							// 小数点処理(myTab
							for (int i = 0; i < jTextField_2.length; i++) {
								if (jTextField_2[i]==null)continue;
								if (eTxt.getName().equals(jTextField_2[i].getName())){
									editingMyTabStopped(i,eTxt.getText());
								}
								// BMI 自動計算
								if (eTxt.getName().equals(CODE_HIGHT)){
									height = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}
								}
								if (eTxt.getName().equals(CODE_WEIGHT)){
									weight = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}
								}
								// eidt s.inoue 2013/04/01
//								if (jTextField_2[i].getName().equals(CODE_BMI) &&
//										!kensaKekka.equals(""))
//									jTextField_2[i].setText(kensaKekka);
								if (jTextField_2[i].getName().equals(CODE_BMI) &&
										!kensaKekka.equals("")){
									jTextField_2[i].setText(kensaKekka);

									// bmi 連携処理 ver2.0.3
									for (int ii = 0; i < jTextField_1.length; ii++) {
										if (jTextField_1[ii] != null){
										if (jTextField_1[ii].getName().equals(CODE_BMI)){
											jTextField_1[ii].setText(kensaKekka);
											break;
										}
										}
									}
								}

							}

							// add s.inoue 2013/02/27
							if (height.equals("") || weight.equals("")){
								for (int i = 0; i < jTextField_2.length; i++) {
									if (jTextField_2[i] != null){
										if (jTextField_2[i].getName().equals(CODE_BMI)){
											jTextField_2[i].setText("");
											break;
										}
									}
								}
							}

							// タブへの連動処理(defTab
							String koumokuCD = eTxt.getName();

							// eidt s.inoue 2013/04/11
							if (!eTxt.getText().equals(""))
							for (int i = 0; i < jTextField_1.length; i++) {
								if (jTextField_1[i] != null)
								if (jTextField_1[i].getName().equals(koumokuCD))
									jTextField_1[i].setText(eTxt.getText());
							}
						}
					}

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

						BigDecimal bmi = new BigDecimal(String.valueOf((Double.valueOf(weight) /((Double.valueOf(height) / 100) * (Double.valueOf(height) / 100)))));
						return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
					}
				});

			}else if (dataType.equals(CODE_FIELD)){
				jComboBox_2[irowAll] = new ExtendedComboBox(ImeMode.IME_OFF);
// eidt s.inoue 2012/02/23 廃止
//				jComboBox_2[irowAll].setAttributeName(koumokuNM);
//				jComboBox_2[irowAll].setDomainId(koumokuCD);
//				jComboBox_2[irowAll].setCanCopy(true);
//				jComboBox_2[irowAll].setRequired(true);
				jComboBox_2[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight));
				jComboBox_2[irowAll].setName(koumokuCD);
				jComboBox_2[irowAll].setSize(new Dimension(kekka_Width*20, controlTextHeight));

				// ↓↓↓↓↓↓前ソース
				// コードの場合
				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
						+ JQueryConvert.queryConvert(koumokuCD));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				// 未入力も選べるようにする
				jComboBox_2[irowAll].addItem("");
				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);
					jComboBox_2[irowAll].addItem(codeResultItem.get("CODE_NAME"));
				}

				// ↑↑↑↑↑↑前ソース
				jComboBox_2[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								jScrollbarMyTab.setValue(irow*forcus_range - forcus_back);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMyTab, irow, true);
								jScrollbarMyTab.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else{
//								jScrollbarMyTab.setValue(irow*forcus_range - forcus_foward);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMyTab, irow, false);
								jScrollbarMyTab.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}
						}
					}
				});

				// ステータス表示処理
				jComboBox_2[irowAll].addFocusListener(new FocusAdapter(){
					@Override
					public void focusGained(FocusEvent e) {
						JComboBox cmb = (JComboBox)e.getSource();
							for (int i = 0; i < jComboBox_2.length; i++) {
								if ( jComboBox_2[i]!= null){
									if (cmb.getParent().getName().equals(jComboBox_2[i].getName())){
										String houhou = kensaHouhouCodeList.get(jComboBox_2[i].getName());
										houhou = (houhou==null)?"":houhou;
										jLabel_KensaHouhou_Value.setText(houhou);
//										jScrollbarMyTab.setValue(irow*forcus_Range);
									}
								}
							}
					}
					@Override
					public void focusLost(FocusEvent e) {
						Object source = e.getSource();

						if (source instanceof JComboBox){
							// JComboBox txt = (JComboBox)source;
							// eidt s.inoue 2012/02/24
							// ExtendedOpenComboboxControl eCmb = (ExtendedOpenComboboxControl)txt.getParent();
							JComboBox eCmb = (JComboBox)source;
						if ((eCmb == null))
							return;

						// eidt s.inoue 2013/04/11
						if (eCmb.getSelectedIndex() > 0)
						for (int i = 0; i < jComboBox_1.length; i++) {
							if (jComboBox_1[i] != null)
							if (jComboBox_1[i].getName().equals(eCmb.getName()))
								jComboBox_1[i].setSelectedIndex(eCmb.getSelectedIndex());
						}
						}

					}
				});
			}else if (dataType.equals(TEXT_FIELD)){
				jTextAreaField_2[irowAll] = new ExtendedTextArea(ImeMode.IME_ZENKAKU);
				// del s.inoue 2013/02/18
				// jTextAreaField_2[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));
				jTextAreaField_2[irowAll].setName(koumokuCD);
				jTextAreaField_2[irowAll].addKeyListener(this);

				// add s.inoue 2013/02/14
				jTextScrollPane_2[irowAll] = new JScrollPane();
				jTextScrollPane_2[irowAll].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jTextScrollPane_2[irowAll].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				jTextScrollPane_2[irowAll].setViewportView(jTextAreaField_2[irowAll]);
				jTextScrollPane_2[irowAll].setAutoscrolls(true);
				// jTextScrollPane_2[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));

				jTextAreaField_2[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								jScrollbarMyTab.setValue(irow*forcus_range - forcus_back);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMyTab, irow, true);
								jScrollbarMyTab.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else{
//								jScrollbarMyTab.setValue(irow*forcus_range - forcus_foward);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMyTab, irow, true);
								jScrollbarMyTab.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}
						}
					}
				});
				jTextAreaField_2[irowAll].addFocusListener(new FocusListener() {
					@Override
					public void focusGained(FocusEvent e) {
						((JTextArea)e.getSource()).setBackground(JApplication.backColor_Focus);
					}
					@Override
					public void focusLost(FocusEvent e) {
						((JTextArea)e.getSource()).setBackground(JApplication.backColor_UnFocus);
						JTextArea txt = (JTextArea)e.getSource();

						// eidt s.inoue 2013/04/11
						if (!txt.getText().equals(""))
						for (int i = 0; i < jTextAreaField_1.length; i++) {
							if (jTextAreaField_1[i] != null)
							if (jTextAreaField_1[i].getName().equals(txt.getName()))
								jTextAreaField_1[i].setText(((String)txt.getText()));
						}

					}
				});

			}
		}else{
			// defTab
			jCheckBox_1[irowAll] = new ExtendedCheckBox();
			jCheckBox_1[irowAll].setFocusable(false);
			jCheckBox_1[irowAll].setName(koumokuCD);
			// add s.inoue 2012/03/07
			jCheckBox_1[irowAll].setToolTipText("<html>チェックなし：実施<br>チェック：測定不能</html>");
			// 0:未実施,1:実施,2:測定不能
			if (jisiKbn.equals("2"))
				jCheckBox_1[irowAll].setSelected(true);

//			jButton_1[irowAll] = new ExtendedButton();
//			jButton_1[irowAll].setText("方法");
//			jButton_1[irowAll].setToolTipText(kensaHouhou);
//			jButton_1[irowAll].setPreferredSize(new Dimension(kensahouhou_Width, controlButtonHeight));
//			jButton_1[irowAll].addActionListener(this);
//			// jButton_1[irowAll].addKeyListener(this);
//			jButton_1[irowAll].setFocusable(false);
//			jButton_1[irowAll].setName(koumokuCD);
//			jButton_1[irowAll].setActionCommand(String.valueOf(irowAll));

			// タイプ毎のフィールド生成
			if (dataType.equals(NUMBER_FIELD)){
				jTextField_1[irowAll] = new ExtendedOpenFormattedFloatControl(ImeMode.IME_OFF);

				jTextField_1[irowAll].setPreferredSize(new Dimension(kekka_Width, controlTextHeight));
				// eidt s.inoue 2012/07/13
				// jTextField_1[irowAll].setToolTipText(koumokuNM);
				jTextField_1[irowAll].setToolTipText(kensaHouhou);
				jTextField_1[irowAll].setName(koumokuCD);
				jTextField_1[irowAll].setText(kekaTi);
				// jTextField_1[irowAll].addKeyListener(this);
				if (koumokuCD.equals(CODE_BMI)){
					jTextField_1[irowAll].setEnabled(false);
					BMIFormat =  maxLength;
				// add s.inoue 2013/01/29
				} else if(koumokuCD.equals(CODE_HIGHT)){
					height =kekaTi;
				} else if(koumokuCD.equals(CODE_WEIGHT)){
					weight =kekaTi;
				}

				jTextField_1[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
					}
					@Override
					public void keyReleased(KeyEvent e) {
					}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();

							String title = jPanelRegisterCenter.getTitleAt(jPanelRegisterCenter.getSelectedIndex());
							// edit n.ohkubo 2015/03/01　未使用なので削除　start
//							int diff = 0;
//							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								diff = forcus_back;
//							}else{
//								diff = forcus_foward;
//							}
							// edit n.ohkubo 2015/03/01　未使用なので削除　end

							if (title.equals("基本健診")){
//								jScrollbarKihon.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListKenshinTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarKihon.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("詳細健診")){
//								jScrollbarSyosai.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListSyosaiTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarSyosai.setValue(scrollValue);
							}else if (title.equals("追加健診")){
//								jScrollbarTuika.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListTuikaTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarTuika.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("問診等")){
//								jScrollbarMonshin.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMonshinTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarMonshin.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}

						}
					}
				});

				jTextField_1[irowAll].addFocusListener(new FocusAdapter(){
					@Override
					public void focusGained(FocusEvent e) {
						JTextField txt = (JTextField)e.getSource();

						for (int i = 0; i < jTextField_1.length; i++) {
							if ( jTextField_1[i]!= null){
								// eidt s.inoue 2012/02/24
								// if (txt.getParent().getName().equals(jTextField_1[i].getName())){
								if (txt.getName().equals(jTextField_1[i].getName())){
									String houhou =kensaHouhouCodeList.get(jTextField_1[i].getName());
									houhou = (houhou==null)?"":houhou;
									jLabel_KensaHouhou_Value.setText(houhou);
									((JTextField)e.getSource()).setBackground(JApplication.backColor_Focus);
								}
							}
						}
					}
					@Override
					public void focusLost(FocusEvent e) {
						// eidt s.inoue 2012/02/14
						JFormattedTextField txt = (JFormattedTextField)e.getSource();
						// JTextField txt = (JTextField)e.getSource();
						// ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt.getParent();
						ExtendedOpenFormattedFloatControl eTxt = (ExtendedOpenFormattedFloatControl)txt;

						// add s.inoue 2012/07/13
						jLabel_KensaHouhou_Value.setText("");

						if ((eTxt == null))
							return;
						// edit s.inoue 2012/02/02
						// ((JTextArea)e.getSource()).setBackground(JApplication.backColor_UnFocus);
						((JTextField)e.getSource()).setBackground(JApplication.backColor_UnFocus);

						for (int i = 0; i < jTextField_1.length; i++) {
							if ( jTextField_1[i]!= null){

								if (eTxt.getName().equals(jTextField_1[i].getName())){
//									editingStopped(i,eTxt.getText());	// edit n.ohkubo 2014/10/01　削除
									editingStopped(i, eTxt.getText(), eTxt.getName());	// edit n.ohkubo 2014/10/01　追加
								}
								// BMI 自動計算
								if (eTxt.getName().equals(CODE_HIGHT)){
									height = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}

								}
								if (eTxt.getName().equals(CODE_WEIGHT)){
									weight = eTxt.getText();
									if (!height.equals("") && !weight.equals("")){
										String bmi = calcBMI(height, weight);
										kensaKekka = JValidate.validateKensaKekkaDecimal(bmi,BMIFormat);
									}
								}
								// eidt s.inoue 2011/12/09
								if (jTextField_1[i].getName().equals(CODE_BMI) &&
										!kensaKekka.equals("")){
									jTextField_1[i].setText(kensaKekka);
									break;
								}
							}
						}
						// add s.inoue 2013/02/27
						if (height.equals("") || weight.equals("")){
							for (int i = 0; i < jTextField_1.length; i++) {
								if (jTextField_1[i] != null){
									if (jTextField_1[i].getName().equals(CODE_BMI)){
										jTextField_1[i].setText("");
										break;
									}
								}
							}
						}
					}

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

						BigDecimal bmi = new BigDecimal(String.valueOf((Double.valueOf(weight) /((Double.valueOf(height) / 100) * (Double.valueOf(height) / 100)))));
						return String.valueOf(bmi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
					}

				});
			/* コードフィールド設定 */
			}else if (dataType.equals(CODE_FIELD)){
				jComboBox_1[irowAll] = new ExtendedComboBox(ImeMode.IME_OFF);
// del s.inoue 2012/02/17 1次
//				jComboBox_1[irowAll].setAttributeName(koumokuNM);
//				jComboBox_1[irowAll].setDomainId(koumokuCD);
//				jComboBox_1[irowAll].setCanCopy(true);
				// jComboBox_1[irowAll].setRequired(true);
				jComboBox_1[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight));
				jComboBox_1[irowAll].setName(koumokuCD);
				jComboBox_1[irowAll].setSize(new Dimension(kekka_Width*20, controlTextHeight));
				// del s.inoue 2012/02/17 1次
//				if (!kekaTi.equals(""))
//					jComboBox_1[irowAll].setSelectedIndex(Integer.parseInt(kekaTi)-1);

				// ↓↓↓↓↓↓前ソース
				// コードの場合
				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "
						+ JQueryConvert.queryConvert(koumokuCD));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				// 未入力も選べるようにする
				jComboBox_1[irowAll].addItem("");
				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);
					jComboBox_1[irowAll].addItem(codeResultItem.get("CODE_NAME"));
				}

				// eidt s.inoue 2012/02/21
				if (!kekaTi.equals("")){
					// eidt s.inoue 2012/12/27
					// indexだとずれる(はい 0 or 1が存在)
					for (int i = 0; i < jComboBox_1[irowAll].getItemCount(); i++) {
						String item = jComboBox_1[irowAll].getItemAt(i).toString();
						if (!item.equals(""))
							item = item.substring(0, 1);
						if (kekaTi.equals(item))
							jComboBox_1[irowAll].setSelectedIndex(i);
					}
					// jComboBox_1[irowAll].setSelectedIndex(Integer.parseInt(kekaTi));
				}

				// ↑↑↑↑↑↑前ソース

				// add s.inoue 2012/02/14
				jComboBox_1[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();

							String title = jPanelRegisterCenter.getTitleAt(jPanelRegisterCenter.getSelectedIndex());
							// edit n.ohkubo 2015/03/01　未使用なので削除　start
//							int diff = 0;
//							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								diff = forcus_back;
//							}else{
//								diff = forcus_foward;
//							}
							// edit n.ohkubo 2015/03/01　未使用なので削除　end

							if (title.equals("基本健診")){
//								jScrollbarKihon.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListKenshinTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarKihon.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("詳細健診")){
//								jScrollbarSyosai.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListSyosaiTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarSyosai.setValue(scrollValue);
							}else if (title.equals("追加健診")){
//								jScrollbarTuika.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListTuikaTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarTuika.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("問診等")){
//								jScrollbarMonshin.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMonshinTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarMonshin.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}
						}
					}
				});

				// ステータス表示処理
				jComboBox_1[irowAll].addFocusListener(new FocusAdapter(){
					@Override
					public void focusGained(FocusEvent e) {
						JComboBox cmb = (JComboBox)e.getSource();
							for (int i = 0; i < jComboBox_1.length; i++) {
								if ( jComboBox_1[i]!= null){
									if (cmb.getParent().getName().equals(jComboBox_1[i].getName())){
										String houhou = kensaHouhouCodeList.get(jComboBox_1[i].getName());
										houhou = (houhou==null)?"":houhou;
										jLabel_KensaHouhou_Value.setText(houhou);
									}
								}
							}
					}
					@Override
					public void focusLost(FocusEvent e) {
					}
				});
			}else if (dataType.equals(TEXT_FIELD)){
				jTextAreaField_1[irowAll] = new ExtendedTextArea(ImeMode.IME_ZENKAKU);
				// eidt s.inoue 2012/03/08
				// del s.inoue 2013/02/18
				// jTextAreaField_1[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));
				jTextAreaField_1[irowAll].setName(koumokuCD);
				jTextAreaField_1[irowAll].addKeyListener(this);
				jTextAreaField_1[irowAll].setText(kekaTi);

				// add s.inoue 2013/02/14
				jTextScrollPane_1[irowAll] = new JScrollPane();
				jTextScrollPane_1[irowAll].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jTextScrollPane_1[irowAll].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				jTextScrollPane_1[irowAll].setViewportView(jTextAreaField_1[irowAll]);
				jTextScrollPane_1[irowAll].setAutoscrolls(true);

				// eidt s.inoue 2013/02/15
				// jTextScrollPane_1[irowAll].setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight-2));

				// add s.inoue 2012/02/14
				jTextAreaField_1[irowAll].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyPressed(KeyEvent keyEvent) {
						if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
							int modfiersKey = keyEvent.getModifiersEx();
//							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								switch (jPanelRegisterCenter.getSelectedIndex()) {
//								case 1:
//									jScrollbarKihon.setValue(irow*forcus_range - forcus_back);break;
//								case 2:
//									jScrollbarSyosai.setValue(irow*forcus_range - forcus_back);break;
//								case 3:
//									jScrollbarTuika.setValue(irow*forcus_range - forcus_back);break;
//								case 4:
//									jScrollbarMonshin.setValue(irow*forcus_range - forcus_back);break;
//								}
//							}else{
//								switch (jPanelRegisterCenter.getSelectedIndex()) {
//								case 1:
//									jScrollbarKihon.setValue(irow*forcus_range - forcus_foward);break;
//								case 2:
//									jScrollbarSyosai.setValue(irow*forcus_range - forcus_foward);break;
//								case 3:
//									jScrollbarTuika.setValue(irow*forcus_range - forcus_foward);break;
//								case 4:
//									jScrollbarMonshin.setValue(irow*forcus_range - forcus_foward);break;
//								}
//							}

							String title = jPanelRegisterCenter.getTitleAt(jPanelRegisterCenter.getSelectedIndex());
							// edit n.ohkubo 2015/03/01　未使用なので削除　start
//							int diff = 0;
//							if ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0){
//								diff = forcus_back;
//							}else{
//								diff = forcus_foward;
//							}
							// edit n.ohkubo 2015/03/01　未使用なので削除　end

							if (title.equals("基本健診")){
//								jScrollbarKihon.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListKenshinTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarKihon.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("詳細健診")){
//								jScrollbarSyosai.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListSyosaiTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarSyosai.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("追加健診")){
//								jScrollbarTuika.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListTuikaTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarTuika.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}else if (title.equals("問診等")){
//								jScrollbarMonshin.setValue(irow*forcus_range - diff);	// edit n.ohkubo 2015/03/01　削除
								// edit n.ohkubo 2015/03/01　追加　start
								int scrollValue = getScrollValue(scrollValueListMonshinTab, irow, ((modfiersKey & InputEvent.SHIFT_DOWN_MASK) == 0));
								jScrollbarMonshin.setValue(scrollValue);
								// edit n.ohkubo 2015/03/01　追加　end
							}
						}
					}
				});

				jTextAreaField_1[irowAll].addFocusListener(new FocusListener() {
					@Override
					public void focusLost(FocusEvent e) {
						((JTextArea)e.getSource()).setBackground(JApplication.backColor_UnFocus);
					}
					@Override
					public void focusGained(FocusEvent e) {
				    	((JTextArea)e.getSource()).setBackground(JApplication.backColor_Focus);
					}
				});

			}
		}

		// 結果値フィールド
		JLabel jLabelZenkai = new JLabel();
		jLabelZenkai.setPreferredSize(new Dimension(zenkai_Width, controlTextHeight));
		jLabelZenkai.setText(hisTi);
//		jLabelZenkai.setToolTipText("前回値:" + hisTi);	// edit n.ohkubo 2016/02/01　削除
		jLabelZenkai.setToolTipText("前年値:" + hisTi);	// edit n.ohkubo 2016/02/01　追加
		jLabelZenkai.setFocusable(false);

		JLabel jLabelHL = new JLabel();
		jLabelHL.setPreferredSize(new Dimension(hl_Width, controlTextHeight));
		jLabelHL.setFocusable(false);
		jLabelHL.setText(hl);
		jLabelHL.setToolTipText("HL判定:" + hl);

		JLabel jLabelHantei = new JLabel();
		jLabelHantei.setPreferredSize(new Dimension(hantei_Width, controlTextHeight));
		jLabelHantei.setFocusable(false);
		jLabelHantei.setText(hantei);
		jLabelHantei.setToolTipText("判定:" + hantei);

		// defTab
		if (!myTab){
			// 測定不能,項目名,検査方法,結果(数値、コード、文字),結果(コメント)
			// type == 必須フラグ
			switch (type) {
			case 1:
				jInnerTranPanelKenshin.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelKenshin.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelKenshin.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelKenshin.add(jTextField_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListKenshinTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelKenshin.add(jComboBox_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListKenshinTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelKenshin.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelKenshin.add(jTextScrollPane_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListKenshinTab, true);	// edit n.ohkubo 2015/03/01　追加
				}
				jInnerTranPanelKenshin.add(jLabelZenkai, gridCtl[4]);
//				// 検査方法項目
//				if(houhouflg)
//					jInnerTranPanelKenshin.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelKenshin.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelKenshin.add(jLabelHantei, gridCtl[7]);
				break;
			case 2:
				jInnerTranPanelSyosai.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelSyosai.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelSyosai.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelSyosai.add(jTextField_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListSyosaiTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelSyosai.add(jComboBox_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListSyosaiTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelSyosai.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelSyosai.add(jTextScrollPane_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListSyosaiTab, true);	// edit n.ohkubo 2015/03/01　追加
				}
				jInnerTranPanelSyosai.add(jLabelZenkai, gridCtl[4]);
//				// 検査方法項目
//				if(houhouflg)
//					jInnerTranPanelSyosai.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelSyosai.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelSyosai.add(jLabelHantei, gridCtl[7]);
				break;
			case 3:
				jInnerTranPanelTuika.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelTuika.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelTuika.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelTuika.add(jTextField_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListTuikaTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelTuika.add(jComboBox_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListTuikaTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelTuika.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelTuika.add(jTextScrollPane_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListTuikaTab, true);	// edit n.ohkubo 2015/03/01　追加
				}
				jInnerTranPanelTuika.add(jLabelZenkai, gridCtl[4]);
//				// 検査方法項目
//				if(houhouflg)
//					jInnerTranPanelTuika.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelTuika.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelTuika.add(jLabelHantei, gridCtl[7]);

				break;
			case 4 :
				jInnerTranPanelMonshin.add(jCheckBox_1[irowAll],gridCtl[0]);
				jInnerTranPanelMonshin.add(jLabelKoumokuNm, gridCtl[1]);
				jInnerTranPanelMonshin.add(jLabelTani, gridCtl[2]);
				if (dataType.equals(NUMBER_FIELD)){
					jInnerTranPanelMonshin.add(jTextField_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListMonshinTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(CODE_FIELD)){
					jInnerTranPanelMonshin.add(jComboBox_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListMonshinTab, false);	// edit n.ohkubo 2015/03/01　追加
				}else if (dataType.equals(TEXT_FIELD)){
					// eidt s.inoue 2013/02/14
					// jInnerTranPanelMonshin.add(jTextAreaField_1[irowAll], gridCtl[3]);
					jInnerTranPanelMonshin.add(jTextScrollPane_1[irowAll], gridCtl[3]);
					addScrollValue(scrollValueListMonshinTab, true);	// edit n.ohkubo 2015/03/01　追加
				}
				jInnerTranPanelMonshin.add(jLabelZenkai, gridCtl[4]);
//				// 検査方法項目
//				if(houhouflg)
//					jInnerTranPanelMonshin.add(jButton_1[irowAll], gridCtl[5]);

				jInnerTranPanelMonshin.add(jLabelHL, gridCtl[6]);
				jInnerTranPanelMonshin.add(jLabelHantei, gridCtl[7]);
				break;
			}
		}else{
			jInnerTranPanelMyTab.add(jCheckBox_2[irowAll],gridCtl[0]);
			jInnerTranPanelMyTab.add(jLabelKoumokuNm, gridCtl[1]);
			jInnerTranPanelMyTab.add(jLabelTani, gridCtl[2]);
			if (dataType.equals(NUMBER_FIELD)){
				jInnerTranPanelMyTab.add(jTextField_2[irowAll], gridCtl[3]);
				addScrollValue(scrollValueListMyTab, false);	// edit n.ohkubo 2015/03/01　追加
			}else if (dataType.equals(CODE_FIELD)){
				jInnerTranPanelMyTab.add(jComboBox_2[irowAll], gridCtl[3]);
				addScrollValue(scrollValueListMyTab, false);	// edit n.ohkubo 2015/03/01　追加
			}else if (dataType.equals(TEXT_FIELD)){
				// eidt s.inoue 2013/02/15
				// jInnerTranPanelMyTab.add(jTextAreaField_2[irowAll], gridCtl[3]);
				jInnerTranPanelMyTab.add(jTextScrollPane_2[irowAll], gridCtl[3]);
				addScrollValue(scrollValueListMyTab, true);	// edit n.ohkubo 2015/03/01　追加
			}
			jInnerTranPanelMyTab.add(jLabelZenkai, gridCtl[4]);
//			// 検査方法項目
//			if(houhouflg)
//				jInnerTranPanelMyTab.add(jButton_2[irowAll], gridCtl[5]);
			jInnerTranPanelMyTab.add(jLabelHL, gridCtl[6]);
			jInnerTranPanelMyTab.add(jLabelHantei, gridCtl[7]);
			// clearButton
			if (lastFlg){
				GridBagConstraints gridButton = new GridBagConstraints();
				gridButton.gridx = 1;
				gridButton.gridy = irow+1;
				gridButton.gridwidth = 1;
				gridButton.gridheight = 1;
				gridButton.anchor = GridBagConstraints.NORTHWEST;
				gridButton.insets = new Insets(cntrolTop, controlLeft, 0, 0);

// del s.inoue 2012/02/09
//				jButton_mytabClear = new ExtendedButton();
//				jButton_mytabClear.setText("クリア");
//				jButton_mytabClear.setToolTipText("クリア");
//				jButton_mytabClear.setPreferredSize(new Dimension(kekka_Width*2, controlTextHeight*2));
//				jButton_mytabClear.addActionListener(this);
//				jInnerTranPanelMyTab.add(jButton_mytabClear, gridButton);
			}
		}
	}
	/************************* 入力フィールド設置 end *************************/

	/************************* 入力フィールド制御 start *************************/
	/*
	 * 編集完了イベントコールバック
	 */
//	public void editingStopped(int irow,String txt) {	// edit n.ohkubo 2014/10/01　削除
	private void editingStopped(int irow, String txt, String name) {	// edit n.ohkubo 2014/10/01　追加

		// edit n.ohkubo 2014/10/01　削除　start　位置調整に不具合がある
//		// 注意）ロジック都合、位置調整用
//		// eidt s.inoue 2013/07/10
//		int choseiRow = irow;
//		if (irow > posHokensido){
//			choseiRow += 1;
//		}
//		if (irow > posMetabo){
//			choseiRow += 1;
//		}
////		if ((irow > posHokensido) &&
////			(irow > posMetabo))
////				choseiRow += 2;
//
//		// フォーマット文字列を取得する
//		Map<String, String> recordMap = tableResult.get(choseiRow);
		// edit n.ohkubo 2014/10/01　削除　end　位置調整に不具合がある
		
		// edit n.ohkubo 2014/10/01　追加　start　対象のマップはname（項目コード）で判定する
		Map<String, String> recordMap = null;
		for (int i = 0; i < tableResult.size(); i++) {
			Map<String, String> hashtable = tableResult.get(i);
			if (name.equals(hashtable.get(JConstantString.COLUMN_NAME_KOUMOKUCD))) {
				recordMap = hashtable;
				break;
			}
		}
		// edit n.ohkubo 2014/10/01　追加　end　対象のマップはname（項目コード）で判定する
		
		String format = recordMap.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);
		// add s.inoue 2013/02/12
		String roundUpValue = textTabFormat(recordMap,irow,format,txt);
		
		// 四捨五入値をセルにセット
		jTextField_1[irow].setText(roundUpValue);
	}

	/*
	 * 編集完了イベントコールバック(Mytab
	 */
	public void editingMyTabStopped(int irow,String txt) {

		// フォーマット文字列を取得する
		Map<String, String> recordMap = tableMyTabResult.get(irow);
		String format = recordMap.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);
		// add s.inoue 2013/02/12
		String roundUpValue = textTabFormat(recordMap,irow,format,txt);
		// 四捨五入値をセルにセット
		jTextField_2[irow].setText(roundUpValue);
	}

	/*
	 * フォーマット処理
	 */
	private String textTabFormat(Map<String, String> recordMap,int irow,String format,String txt){
		// フォーマット文字列の妥当性を検証
		if (!isValidFormat(format))
			return "";

		// add s.inoue 2012/07/02
		// 対応検査項目
		// 撮影年月日
		boolean dayformat = false;
		if (recordMap.get("KOUMOKUCD").equals("9N211161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N226161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N251161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N256161100000049") ||
				recordMap.get("KOUMOKUCD").equals("9N261161100000049") ||
				recordMap.get("KOUMOKUCD").equals("Z9N21161100000049") ||
				recordMap.get("KOUMOKUCD").equals("Z9N22161100000049") ||
				recordMap.get("KOUMOKUCD").equals("Z9N26161100000049") ||
				recordMap.get("KOUMOKUCD").equals("ZI210161100000049"))
				dayformat = true;

		// フォーマット文字列の作成
		int width = getWidth(format);
		int scale = getScale(format);
		String formatStr = "%." + scale + "f";

		String roundUpValue = "";
		try {

// eidt s.inoue 2013/02/12
//			double value = new Double(txt)
//					.doubleValue();
//			// 小数点第2位以下を四捨五入し小数点第1位までを表示
//			roundUpValue = String.format(formatStr, new BigDecimal(
//					value).setScale(scale + 1,
//					BigDecimal.ROUND_HALF_UP).floatValue());

			// 小数点第2位以下を四捨五入し小数点第1位までを表示
			if (dayformat){
				String value = new String(txt);
				roundUpValue = String.format(formatStr, new BigDecimal(
						value));
			}else{
				double value = new Double(txt).doubleValue();
				roundUpValue = String.format(formatStr, new BigDecimal(
						value).setScale(scale + 1,
								BigDecimal.ROUND_HALF_UP).floatValue());
			}

			// 整数部・小数部が指定桁数をオーバーしている場合はクリア
			if ((getWidth(roundUpValue) > width)
					|| (getScale(roundUpValue) > scale))
				roundUpValue = "";
		} catch (NumberFormatException e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
		}

		return roundUpValue;
	}

	private final String DIGIT_FORMAT_REGEXP = "[#\\.0]+";
	/*
	 * 妥当なフォーマット文字列か検証する
	 */
	public boolean isValidFormat(String format) {

		if ((format == null) || (format.length() < 1))
			return false;

		Pattern pattern = Pattern.compile(DIGIT_FORMAT_REGEXP);
		if (!pattern.matcher(format).matches())
			return false;
		int pos = format.indexOf(".");
		if (pos == (format.length() - 1))
			return false;

		// 小数点の指定が無い場合
		if (pos < 0) {
			pattern = Pattern.compile("#+");
			return pattern.matcher(format).matches();
		}

		pos++;
		pattern = Pattern.compile("0+");
		return pattern.matcher(
				format.substring(pos, format.length())).matches();
	}
	/*
	 * 整数部の桁数を取得する
	 */
	public int getWidth(String format) {
		if ((format == null) || (format.length() == 0))
			return -1;
		int pos = format.indexOf(".");
		if (pos < 0)
			return format.length();
		if ((pos == 0) || (pos == (format.length() - 1)))
			return -1;
		return format.substring(0, pos).length();
	}

	/*
	 * 小数部の桁数を取得する
	 */
	public int getScale(String format) {
		int pos = format.indexOf(".");
		if (pos < 0)
			return 0;
		if ((pos == 0) || (pos == (format.length() - 1)))
			return -1;
		pos++;
		return format.substring(pos, format.length()).length();
	}

	// add s.inoue 2012/04/24
	// 再有効化
	@Override
	public void keyPressed(KeyEvent keyEvent) {

    	Object obj = keyEvent.getSource();

    	if (obj instanceof ExtendedTextArea ){
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
				// keyEvent.getKeyText(keyCode)
				// Shiftキーが一緒に押されたか?
				int modfiersKey = keyEvent.getModifiersEx();

				if ((modfiersKey & InputEvent.CTRL_DOWN_MASK) == 0){
					return;
				}else{

					ExtendedTextArea txtArea = (ExtendedTextArea)obj;
					String kekkaMojiretsu = txtArea.getText();

					try {
						if (dialogs == null){
							dialogs = JKekkaRegisterInputDialogFactory.createDialogComment(this,kekkaMojiretsu);
						}else{
							// "結果(文字列)"入力ダイアログのテキストエリアに選択セルデータをセット
							dialogs.setCommentText(kekkaMojiretsu);
						}
						dialogs.setVisible(true);
						if (dialogs.getStatus() == RETURN_VALUE.YES){
							// "結果(文字列)"入力ダイアログから入力された文字列を選択セルにセット
							txtArea.setText(dialogs.getText());
							txtArea.setToolTipText(dialogs.getText());
						}
					}catch(Exception ex){
						ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
						logger.error(ex.getMessage());
					}
				}
			}
		}
	}

    /************************* 入力フィールド制御 end *************************/

	/************************* SQL制御 start *************************/

	/*
	 * 健診種別(1:基本健診,2:詳細健診,3:追加健診,4問診)ごと取得
	 */
	private String createCellDataSql(){

		List kinouCd = Arrays.asList(JConstantString.codesSeikatuKinou);
		List monshinCd = Arrays.asList(JConstantString.codesSeikatuMonshin);

		// del s.inoue 2012/11/07
		// List monshinHisuCd = Arrays.asList(JConstantString.codesSeikatuMonshinHisu);

		String strKoumokuCd = "";
		StringBuilder strsubSQL = new StringBuilder();
//		StringBuilder strSeikatu = new StringBuilder();
//		StringBuilder strWhere = new StringBuilder();

//		strsubSQL = " '9999' AS HISU_FLG ";
		strsubSQL.append("case ");
		// 生活機能評価
		for (int i = 0 ;i<kinouCd.size();i++){
			strKoumokuCd = (String)kinouCd.get(i);
			strsubSQL.append(" when master.KOUMOKU_CD ='" + strKoumokuCd +"' then '4' ");
		}
		// 問診項目
		for (int i = 0 ;i<monshinCd.size();i++){
			strKoumokuCd = (String)monshinCd.get(i);
			strsubSQL.append(" when master.KOUMOKU_CD ='" + strKoumokuCd +"' then '4' ");
		}

		strsubSQL.append(" else master.HISU_FLG end as HISU_FLG ");

		// 複数の検査方法マージ処理
//		strWhere.append(" AND master.KOUMOKU_CD <> '9N016160200000001' AND master.KOUMOKU_CD <> '9N016160300000001'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '9A752000000000001' AND master.KOUMOKU_CD <> '9A755000000000001'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '9A762000000000001' AND master.KOUMOKU_CD <> '9A765000000000001'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F050000002327201' AND master.KOUMOKU_CD <> '3F050000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F015000002327201' AND master.KOUMOKU_CD <> '3F015000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F070000002327201' AND master.KOUMOKU_CD <> '3F070000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3F077000002327201' AND master.KOUMOKU_CD <> '3F077000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3J010000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3B035000002399901' AND master.KOUMOKU_CD <> '3B045000002327201'  AND master.KOUMOKU_CD <> '3B045000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3B090000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3B070000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3C015000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3C020000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3A010000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3A015000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5C095000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3D010000002227101' AND master.KOUMOKU_CD <> '3D010000001927201' AND master.KOUMOKU_CD <> '3D010000001999901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3D010129902227101' AND master.KOUMOKU_CD <> '3D010129901927201' AND master.KOUMOKU_CD <> '3D010129901999901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '3D045000001920402' AND master.KOUMOKU_CD <> '3D045000001927102' AND master.KOUMOKU_CD <> '3D045000001999902'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '1A020000000190111'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '1A010000000190111' AND master.KOUMOKU_CD <> '1A100000000191111' AND master.KOUMOKU_CD <> '1A100000000190111'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '1A030000000199901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '9N221160700000011' AND master.KOUMOKU_CD <> '9N221160800000049' AND master.KOUMOKU_CD <> '9N226161100000049' AND master.KOUMOKU_CD <> '9N226161200000049'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5C070000002306301' AND master.KOUMOKU_CD <> '5C070000002399901'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5H010000001999911'");
//		strWhere.append(" AND master.KOUMOKU_CD <> '5H020000001999911'");

//		strSeikatu = new StringBuilder();
//
//		strSeikatu.append(" SELECT master.XMLITEM_SEQNO,master.KOUMOKU_CD AS KOUMOKUCD,");
//		strSeikatu.append(" case ");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診1'  then '質問1-1'  when master.KOUMOKU_NAME ='生活機能問診2'  then '質問1-2'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診3'  then '質問1-3'  when master.KOUMOKU_NAME ='生活機能問診4'  then '質問4'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診5'  then '質問5'    when master.KOUMOKU_NAME ='生活機能問診6'  then '質問6'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診7'  then '質問7'    when master.KOUMOKU_NAME ='生活機能問診8'  then '質問8'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診9'  then '質問9'    when master.KOUMOKU_NAME ='生活機能問診10' then '質問10'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診11' then '質問11'   when master.KOUMOKU_NAME ='生活機能問診12' then '質問12'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診13' then '質問13'   when master.KOUMOKU_NAME ='生活機能問診14' then '質問14'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診15' then '質問15'   when master.KOUMOKU_NAME ='生活機能問診16' then '質問16'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診17' then '質問17'   when master.KOUMOKU_NAME ='生活機能問診18' then '質問18'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診19' then '質問19'   when master.KOUMOKU_NAME ='生活機能問診20' then '質問20'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診21' then '質問21'   when master.KOUMOKU_NAME ='生活機能問診22' then '質問22'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診23' then '質問23'   when master.KOUMOKU_NAME ='生活機能問診24' then '質問24'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診25' then '質問25'");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='腹囲(実測）' then '腹囲'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '収縮期血圧(その他)' then '収縮期血圧'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '拡張期血圧(その他)' then '拡張期血圧'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(がん:直接撮影)' then '胸部Ｘ線検査(がん)'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(一般:直接撮影)(所見の有無)' then '胸部Ｘ線検査(一般)(所見の有無)'");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(一般:直接撮影)(所見)' then '胸部Ｘ線検査(一般)(所見)' ");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(直接撮影)(撮影年月日)' then '胸部Ｘ線検査(撮影年月日)' ");
//		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(直接撮影)(フィルム番号)' then '胸部Ｘ線検査(フィルム番号)' ");
//		strSeikatu.append(" else master.KOUMOKU_NAME end KOUMOKUNAME,");
//		strSeikatu.append(" master.KENSA_HOUHOU AS KENSA_HOUHOU,sonota.JISI_KBN,sonota.KEKA_TI,master.DS_KAGEN,master.DS_JYOUGEN,master.JS_KAGEN,master.JS_JYOUGEN,master.KAGEN,");
//		strSeikatu.append("  master.JYOUGEN,master.DATA_TYPE,master.MAX_BYTE_LENGTH,sonota.KOMENTO,sonota.H_L,sonota.HANTEI,master.TANI,");

		StringBuilder sql = new StringBuilder();

		/* 健診分野別のタブ */
//		sql.append(" SELECT * FROM ( ");
		sql.append(getCellDataSeikatuSql() + strsubSQL.toString());
		
		// edit n.ohkubo 2016/02/01　削除　start　前年度の結果値を取得する（＋T_KENSACENTER_MASTERは廃止）
//		sql.append(" FROM ( T_KENSHINMASTER master LEFT JOIN T_KENSACENTER_MASTER kensa ON kensa.KOUMOKU_CD = master.KOUMOKU_CD  AND kensa.KENSA_CENTER_CD = null )");
//		sql.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ON sonota.KOUMOKU_CD = master.KOUMOKU_CD");
//		// add s.inoue 2012/04/24
//		if (!validatedData.getKensaJissiDate().equals(""))
//			sql.append(" AND sonota.KENSA_NENGAPI = '" + validatedData.getKensaJissiDate() + "'");
//
//		sql.append(" AND sonota.UKETUKE_ID = ");
//
//		// eidt s.inoue 2011/12/07
//		// sql.append(validatedData.getUketuke_id());
//		if (isCopy && !isPrev){
//			sql.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
//		}else{
//			sql.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
//		}
//
//		sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '" + kenshinPatternNumber  + "'");
//
//		// add s.inoue 2011/11/08
//		sql.append(" LEFT JOIN (select KOUMOKU_CD,KEKA_TI from T_KENSAKEKA_SONOTA where UKETUKE_ID = ");
//		sql.append(" (select MAX(UKETUKE_ID) FROM T_NAYOSE ns,");
//		sql.append(" (select NAYOSE_NO FROM T_NAYOSE WHERE UKETUKE_ID = '" + validatedData.getUketuke_id()  + "') nst ");
//		sql.append(" where ns.NAYOSE_NO = nst.NAYOSE_NO and ns.UKETUKE_ID <> '" + validatedData.getUketuke_id()  + "')) his ON ps.KOUMOKU_CD = his.KOUMOKU_CD ");
//
//		// pattern 5 → pattern 1
//		sql.append(" WHERE master.KOUMOKU_CD IN ( SELECT KOUMOKU_CD FROM T_KENSHIN_P_S WHERE K_P_NO = '" + kenshinPatternNumber  + "' )");
//		// 処理に含める
//		// AND KOUMOKU_CD NOT IN ( '9N501000000000011','9N506000000000011' ) )");
//		sql.append(" AND master.HKNJANUM = '" + validatedData.getHokenjyaNumber() + "'");
//
//		// 複数方法が存在する場合、固定で1つにするメソッド
//		// →廃止
////		sql.append(getCellDataWhereSql());
//
////		sql.append(" UNION ");
////		/* マイタブ用の UNION SQL */
////		sql.append(strSeikatu.toString() + " '9999' AS HISU_FLG ");
////		sql.append(" FROM ( T_KENSHINMASTER master LEFT JOIN T_KENSACENTER_MASTER kensa ON kensa.KOUMOKU_CD = master.KOUMOKU_CD  AND kensa.KENSA_CENTER_CD = null )");
////		sql.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ON sonota.KOUMOKU_CD = master.KOUMOKU_CD  AND sonota.KENSA_NENGAPI = '20110913'  AND sonota.UKETUKE_ID = '201108250001'");
////		sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '" + patternNo  + "'");
////		sql.append(" WHERE master.KOUMOKU_CD IN ( SELECT KOUMOKU_CD FROM T_KENSHIN_P_S WHERE K_P_NO = '9999'  AND KOUMOKU_CD NOT IN ( '9N501000000000011','9N506000000000011' ) )");
////		sql.append(" AND master.HKNJANUM = '11111111'  ");
////		sql.append(strWhere);
////		sql.append("  ) ");
//
//		// no eidt s.inoue 2013/04/10
//		// ここを変えると表示文字の重複のバグとなる
//		// eidt s.inoue 2013/11/07
//		// 再度修正、パターン順にしたい
//		// sql.append(" ORDER BY HISU_FLG,XMLITEM_SEQNO ");
//		sql.append(" ORDER BY HISU_FLG,LOW_ID ");
//
//		System.out.println(sql.toString());
		// edit n.ohkubo 2016/02/01　削除　end　前年度の結果値を取得する（＋T_KENSACENTER_MASTERは廃止）
		
		// edit n.ohkubo 2016/02/01　追加　start　前年度の結果値を取得する
		String nengapi = validatedData.getKensaJissiDate();	//実施年月日
		String uketuke_id;	//受付ID
		if (isCopy && !isPrev) {
			uketuke_id = JQueryConvert.queryConvert(validatedData.getUketukePre_id());
		} else {
			uketuke_id = JQueryConvert.queryConvert(validatedData.getUketuke_id());
		}
		sql.append(" FROM");
		sql.append(" T_KENSHINMASTER AS master INNER JOIN T_KENSHIN_P_S AS ps");
		sql.append(" ON master.KOUMOKU_CD = ps.KOUMOKU_CD");
		sql.append(" LEFT OUTER JOIN T_KENSAKEKA_SONOTA AS sonota");
		sql.append(" ON sonota.KOUMOKU_CD = master.KOUMOKU_CD");
		if (!nengapi.equals("")) {
			sql.append(" AND sonota.KENSA_NENGAPI = '" + nengapi + "'");
		}
		sql.append(" AND sonota.UKETUKE_ID = ");
		sql.append(uketuke_id);
		sql.append(" LEFT OUTER JOIN T_KENSAKEKA_SONOTA AS his");
		sql.append(" ON master.KOUMOKU_CD = his.KOUMOKU_CD");
		sql.append(" AND his.KENSA_NENGAPI = (");
		sql.append(" SELECT");
		sql.append(" MAX(INNER_SONOTA.KENSA_NENGAPI)");
		sql.append(" FROM");
		sql.append(" T_NAYOSE AS NYS_ID INNER JOIN T_NAYOSE AS NYS_NO");
		sql.append(" ON NYS_ID.NAYOSE_NO = NYS_NO.NAYOSE_NO");
		sql.append(" INNER JOIN T_KENSAKEKA_SONOTA AS INNER_SONOTA");
		sql.append(" ON INNER_SONOTA.UKETUKE_ID = NYS_ID.UKETUKE_ID");
		sql.append(" WHERE");
		sql.append(" NYS_NO.UKETUKE_ID = ");
		sql.append(uketuke_id);
		if (!nengapi.equals("")) {
			sql.append(" AND INNER_SONOTA.KENSA_NENGAPI < '" + nengapi + "'");
		}
		sql.append(" )");
		sql.append(" AND his.UKETUKE_ID = (");
		sql.append(" SELECT");
		sql.append(" FIRST 1");
		sql.append(" UKETUKE_ID");
		sql.append(" FROM");
		sql.append(" (");
		sql.append(" SELECT");
		sql.append(" DISTINCT");
		sql.append(" INNER_SONOTA.KENSA_NENGAPI,");
		sql.append(" NYS_ID.UKETUKE_ID");
		sql.append(" FROM");
		sql.append(" T_NAYOSE AS NYS_ID INNER JOIN T_NAYOSE AS NYS_NO");
		sql.append(" ON NYS_ID.NAYOSE_NO = NYS_NO.NAYOSE_NO");
		sql.append(" INNER JOIN T_KENSAKEKA_SONOTA AS INNER_SONOTA");
		sql.append(" ON INNER_SONOTA.UKETUKE_ID = NYS_ID.UKETUKE_ID");
		sql.append(" WHERE");
		sql.append(" NYS_NO.UKETUKE_ID = ");
		sql.append(uketuke_id);
		if (!nengapi.equals("")) {
			sql.append(" AND INNER_SONOTA.KENSA_NENGAPI < '" + nengapi + "'");
		}
		sql.append(" ORDER BY");
		sql.append(" INNER_SONOTA.KENSA_NENGAPI DESC");
		sql.append(" )");
		sql.append(" )");
		sql.append(" WHERE");
		sql.append(" ps.K_P_NO = '" + kenshinPatternNumber  + "'");
		sql.append(" AND master.HKNJANUM = '" + validatedData.getHokenjyaNumber() + "'");
		sql.append(" ORDER BY");
		sql.append(" HISU_FLG,");
		sql.append(" LOW_ID");
		// edit n.ohkubo 2016/02/01　追加　end　前年度の結果値を取得する
		
		return sql.toString();
	}

	/*
	 * 健診種別(MyTab)ごと取得
	 */
	private String createCellDataMyTabSql(){

		StringBuilder sql = new StringBuilder();
		/* マイタブ用の UNION SQL */
		// eidt s.inoue 2012/05/30
		sql.append(getCellDataSeikatuSql() + " '9999'  || HISU_FLG AS HISU_FLG ");
		
		// edit n.ohkubo 2016/02/01　削除　start　前年度の結果値を取得する（＋T_KENSACENTER_MASTERは廃止）
//		sql.append(" FROM ( T_KENSHINMASTER master LEFT JOIN T_KENSACENTER_MASTER kensa ON kensa.KOUMOKU_CD = master.KOUMOKU_CD  AND kensa.KENSA_CENTER_CD = null )");
//		sql.append(" LEFT JOIN T_KENSAKEKA_SONOTA sonota ON sonota.KOUMOKU_CD = master.KOUMOKU_CD ");
//
//		// add s.inoue 2012/04/24
//		if (!validatedData.getKensaJissiDate().equals(""))
//			sql.append(" AND sonota.KENSA_NENGAPI = '" + validatedData.getKensaJissiDate() + "'");
//
//		sql.append(" AND sonota.UKETUKE_ID = ");
//
//		// eidt s.inoue 2011/12/07
//		// sql.append(validatedData.getUketuke_id());
//		if (isCopy && !isPrev){
//			sql.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
//		}else{
//			sql.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
//		}
//
//		// eidt s.inoue 2013/04/10
//		// sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '" + kenshinPatternNumber  + "'");
//		sql.append(" LEFT JOIN T_KENSHIN_P_S ps  ON ps.KOUMOKU_CD = master.KOUMOKU_CD    AND ps.K_P_NO = '9999'");
//
//		// add s.inoue 2011/11/08
//		sql.append(" LEFT JOIN (select KOUMOKU_CD,KEKA_TI from T_KENSAKEKA_SONOTA where UKETUKE_ID = ");
//		sql.append(" (select MAX(UKETUKE_ID) FROM T_NAYOSE ns,");
//		sql.append(" (select NAYOSE_NO FROM T_NAYOSE WHERE UKETUKE_ID = '" + validatedData.getUketuke_id()  + "') nst ");
//		sql.append(" where ns.NAYOSE_NO = nst.NAYOSE_NO and ns.UKETUKE_ID <> '" + validatedData.getUketuke_id()  + "')) his ON ps.KOUMOKU_CD = his.KOUMOKU_CD ");
//
//		sql.append(" WHERE master.KOUMOKU_CD IN ( SELECT KOUMOKU_CD FROM T_KENSHIN_P_S WHERE K_P_NO = '9999' AND KOUMOKU_CD NOT IN ( '9N501000000000011','9N506000000000011' ) )");
//		sql.append(" AND master.HKNJANUM = '" + validatedData.getHokenjyaNumber() + "'");
//
//		// 複数方法が存在する場合、固定で1つにするメソッド
//		// →廃止
////		sql.append(getCellDataWhereSql());
//
//		// eidt s.inoue 2013/04/10
////		sql.append(" ORDER BY HISU_FLG,XMLITEM_SEQNO ");
//		sql.append(" ORDER BY ps.LOW_ID ");
//
//		System.out.println(sql.toString());
		// edit n.ohkubo 2016/02/01　削除　end　前年度の結果値を取得する（＋T_KENSACENTER_MASTERは廃止）

		// edit n.ohkubo 2016/02/01　追加　start　前年度の結果値を取得する
		String nengapi = validatedData.getKensaJissiDate();	//実施年月日
		String uketuke_id;	//受付ID
		if (isCopy && !isPrev) {
			uketuke_id = JQueryConvert.queryConvert(validatedData.getUketukePre_id());
		} else {
			uketuke_id = JQueryConvert.queryConvert(validatedData.getUketuke_id());
		}
		sql.append(" FROM");
		sql.append(" T_KENSHINMASTER master INNER JOIN T_KENSHIN_P_S ps");
		sql.append(" ON master.KOUMOKU_CD = ps.KOUMOKU_CD");
		sql.append(" LEFT OUTER JOIN T_KENSAKEKA_SONOTA sonota");
		sql.append(" ON master.KOUMOKU_CD = sonota.KOUMOKU_CD");
		if (!nengapi.equals("")) {
			sql.append(" AND sonota.KENSA_NENGAPI = '" + nengapi + "'");
		}
		sql.append(" AND sonota.UKETUKE_ID = ");
		sql.append(uketuke_id);
		sql.append(" LEFT OUTER JOIN T_KENSAKEKA_SONOTA AS his");
		sql.append(" ON master.KOUMOKU_CD = his.KOUMOKU_CD");
		sql.append(" AND his.KENSA_NENGAPI = (");
		sql.append(" SELECT");
		sql.append(" MAX(INNER_SONOTA.KENSA_NENGAPI)");
		sql.append(" FROM");
		sql.append(" T_NAYOSE AS NYS_ID INNER JOIN T_NAYOSE AS NYS_NO");
		sql.append(" ON NYS_ID.NAYOSE_NO = NYS_NO.NAYOSE_NO");
		sql.append(" INNER JOIN T_KENSAKEKA_SONOTA AS INNER_SONOTA");
		sql.append(" ON INNER_SONOTA.UKETUKE_ID = NYS_ID.UKETUKE_ID");
		sql.append(" WHERE");
		sql.append(" NYS_NO.UKETUKE_ID = ");
		sql.append(uketuke_id);
		if (!nengapi.equals("")) {
			sql.append(" AND INNER_SONOTA.KENSA_NENGAPI < '" + nengapi + "'");
		}
		sql.append(" )");
		sql.append(" AND his.UKETUKE_ID = (");
		sql.append(" SELECT");
		sql.append(" FIRST 1");
		sql.append(" UKETUKE_ID");
		sql.append(" FROM");
		sql.append(" (");
		sql.append(" SELECT");
		sql.append(" DISTINCT");
		sql.append(" INNER_SONOTA.KENSA_NENGAPI,");
		sql.append(" NYS_ID.UKETUKE_ID");
		sql.append(" FROM");
		sql.append(" T_NAYOSE AS NYS_ID INNER JOIN T_NAYOSE AS NYS_NO");
		sql.append(" ON NYS_ID.NAYOSE_NO = NYS_NO.NAYOSE_NO");
		sql.append(" INNER JOIN T_KENSAKEKA_SONOTA AS INNER_SONOTA");
		sql.append(" ON INNER_SONOTA.UKETUKE_ID = NYS_ID.UKETUKE_ID");
		sql.append(" WHERE");
		sql.append(" NYS_NO.UKETUKE_ID = ");
		sql.append(uketuke_id);
		if (!nengapi.equals("")) {
			sql.append(" AND INNER_SONOTA.KENSA_NENGAPI < '" + nengapi + "'");
		}
		sql.append(" ORDER BY");
		sql.append(" INNER_SONOTA.KENSA_NENGAPI DESC");
		sql.append(" )");
		sql.append(" )");
		sql.append(" WHERE");
		sql.append(" ps.K_P_NO = '9999'");
		sql.append(" AND ps.KOUMOKU_CD NOT IN (");
		sql.append(" '9N501000000000011',");
		sql.append(" '9N506000000000011'");
		sql.append(" )");
		sql.append(" AND master.HKNJANUM = '" + validatedData.getHokenjyaNumber() + "'");
		sql.append(" ORDER BY");
		sql.append(" ps.LOW_ID");
		// edit n.ohkubo 2016/02/01　追加　end　前年度の結果値を取得する
		
		return sql.toString();
	}

//	/*
//	 * SQLWhere句
//	 */
//	private String getCellDataWhereSql(){
//		StringBuilder strSqlWhere = new StringBuilder();
//
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9N016160200000001' AND master.KOUMOKU_CD <> '9N016160300000001'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9A752000000000001' AND master.KOUMOKU_CD <> '9A755000000000001'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9A762000000000001' AND master.KOUMOKU_CD <> '9A765000000000001'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F050000002327201' AND master.KOUMOKU_CD <> '3F050000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F015000002327201' AND master.KOUMOKU_CD <> '3F015000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F070000002327201' AND master.KOUMOKU_CD <> '3F070000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3F077000002327201' AND master.KOUMOKU_CD <> '3F077000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3J010000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3B035000002399901' AND master.KOUMOKU_CD <> '3B045000002327201'  AND master.KOUMOKU_CD <> '3B045000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3B090000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3B070000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3C015000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3C020000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3A010000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3A015000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5C095000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3D010000002227101' AND master.KOUMOKU_CD <> '3D010000001927201' AND master.KOUMOKU_CD <> '3D010000001999901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3D010129902227101' AND master.KOUMOKU_CD <> '3D010129901927201' AND master.KOUMOKU_CD <> '3D010129901999901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '3D045000001920402' AND master.KOUMOKU_CD <> '3D045000001927102' AND master.KOUMOKU_CD <> '3D045000001999902'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '1A020000000190111'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '1A010000000190111' AND master.KOUMOKU_CD <> '1A100000000191111' AND master.KOUMOKU_CD <> '1A100000000190111'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '1A030000000199901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '9N221160700000011' AND master.KOUMOKU_CD <> '9N221160800000049' AND master.KOUMOKU_CD <> '9N226161100000049' AND master.KOUMOKU_CD <> '9N226161200000049'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5C070000002306301' AND master.KOUMOKU_CD <> '5C070000002399901'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5H010000001999911'");
//		strSqlWhere.append(" AND master.KOUMOKU_CD <> '5H020000001999911'");
//		return strSqlWhere.toString();
//	}

	/*
	 * SQLWhere句
	 */
	private String getCellDataSeikatuSql(){
		StringBuilder strSeikatu = new StringBuilder();

		strSeikatu.append(" SELECT master.XMLITEM_SEQNO,master.KOUMOKU_CD AS KOUMOKUCD,");
		strSeikatu.append(" case ");
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診1'  then '質問1-1'  when master.KOUMOKU_NAME ='生活機能問診2'  then '質問1-2'");	// edit n.ohkubo 2015/03/01　削除
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診1'  then '質問1'  when master.KOUMOKU_NAME ='生活機能問診2'  then '質問2'");		// edit n.ohkubo 2015/03/01　追加
//		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診3'  then '質問1-3'  when master.KOUMOKU_NAME ='生活機能問診4'  then '質問4'");	// edit n.ohkubo 2015/03/01　削除
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診3'  then '質問3'  when master.KOUMOKU_NAME ='生活機能問診4'  then '質問4'");		// edit n.ohkubo 2015/03/01　追加
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診5'  then '質問5'    when master.KOUMOKU_NAME ='生活機能問診6'  then '質問6'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診7'  then '質問7'    when master.KOUMOKU_NAME ='生活機能問診8'  then '質問8'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診9'  then '質問9'    when master.KOUMOKU_NAME ='生活機能問診10' then '質問10'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診11' then '質問11'   when master.KOUMOKU_NAME ='生活機能問診12' then '質問12'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診13' then '質問13'   when master.KOUMOKU_NAME ='生活機能問診14' then '質問14'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診15' then '質問15'   when master.KOUMOKU_NAME ='生活機能問診16' then '質問16'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診17' then '質問17'   when master.KOUMOKU_NAME ='生活機能問診18' then '質問18'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診19' then '質問19'   when master.KOUMOKU_NAME ='生活機能問診20' then '質問20'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診21' then '質問21'   when master.KOUMOKU_NAME ='生活機能問診22' then '質問22'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診23' then '質問23'   when master.KOUMOKU_NAME ='生活機能問診24' then '質問24'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='生活機能問診25' then '質問25'");
		strSeikatu.append(" when master.KOUMOKU_NAME ='腹囲(実測）' then '腹囲'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '収縮期血圧(その他)' then '収縮期血圧'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '拡張期血圧(その他)' then '拡張期血圧'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(がん:直接撮影)' then '胸部Ｘ線検査(がん)'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(一般:直接撮影)(所見の有無)' then '胸部Ｘ線検査(一般)(所見の有無)'");
		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(一般:直接撮影)(所見)' then '胸部Ｘ線検査(一般)(所見)' ");
		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(直接撮影)(撮影年月日)' then '胸部Ｘ線検査(撮影年月日)' ");
		strSeikatu.append(" when master.KOUMOKU_NAME = '胸部Ｘ線検査(直接撮影)(フィルム番号)' then '胸部Ｘ線検査(フィルム番号)' ");
		strSeikatu.append(" else master.KOUMOKU_NAME end KOUMOKUNAME,");
		strSeikatu.append(" master.KENSA_HOUHOU AS KENSA_HOUHOU,sonota.JISI_KBN,sonota.KEKA_TI,master.DS_KAGEN,master.DS_JYOUGEN,master.JS_KAGEN,master.JS_JYOUGEN,master.KAGEN,");
		strSeikatu.append("  master.JYOUGEN,master.DATA_TYPE,master.MAX_BYTE_LENGTH,sonota.KOMENTO,master.TANI,his.KEKA_TI HIS_TI,");

		strSeikatu.append(" case when sonota.H_L = '1' then 'L' when sonota.H_L = '2' then 'H' else '' end H_L,");
		strSeikatu.append(" case when sonota.HANTEI = '0' then '未判定' when sonota.HANTEI = '1' then '異常なし' when sonota.HANTEI = '2' then '軽度異常' ");
		strSeikatu.append(" when sonota.HANTEI = '3' then '要経過観察' when sonota.HANTEI = '4' then '異常' when sonota.HANTEI = '5' then '要精検' else '未判定' end HANTEI, ");

		return strSeikatu.toString();
	}

	/*
	 * 複製処理の個人情報登録
	 */
	private String registerKojinCopy(){

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO T_KOJIN ( PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
		query.append(" HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
		query.append(" FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, ");
		query.append(" MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, ");
		query.append(" NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ) ");
		query.append(" SELECT ");
		query.append(" PTNUM, ");
		//JYUSHIN_SEIRI_NOを指定
		query.append(JQueryConvert.queryConvert(validatedData.getcopyJyushinkenID()));
		query.append(" ,YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME, ");

		query.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, ");
		query.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, ");
		query.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, ");

		query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		query.append(" ,MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC ");
		query.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
		query.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));

		return query.toString();

	}

	/*
	 * 特定データ登録
	 */
	private String getUpdateTokutei(String kensaJissiDate){

		StringBuilder query = new StringBuilder();

		query.append("UPDATE T_KENSAKEKA_TOKUTEI SET KENSA_CENTER_CD = ");
		query.append(JQueryConvert.queryConvertAppendComma(validatedData.getKensaCenterCode()));
		query.append("K_P_NO = ");
		query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(kenshinPatternNumber)));
		query.append("KOMENTO = ");
		query.append(JQueryConvert.queryConvertAppendComma(validatedData.getSougouComment()));
		query.append("NENDO = ");
		query.append(JQueryConvert.queryConvertAppendComma(String.valueOf(kensakekaTokutei.getNENDO())));
		query.append("SEIKYU_KBN = ");
		query.append(JQueryConvert.queryConvert(validatedData.getSeikyuKubunCode()));
		query.append(" WHERE UKETUKE_ID = ");
		query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		query.append(" AND KENSA_NENGAPI = ");
		query.append(JQueryConvert.queryConvert(kensaJissiDate));
		return query.toString();
	}

	/*
	 * 特定データ登録
	 */
	private String getRecordCount_Sonota(String kensaJissiDate){
		StringBuilder query = new StringBuilder();

		// 検査結果が未入力のレコードを除いた場合
		query.append(" SELECT KEKA_TI FROM T_KENSAKEKA_SONOTA sonota,T_KENSHINMASTER master WHERE sonota.UKETUKE_ID = ");

		// eidt s.inoue 2011/12/07
		// sql.append(validatedData.getUketuke_id());
		if (isCopy && !isPrev){
			query.append(JQueryConvert.queryConvert(validatedData.getUketukePre_id()));
		}else{
			query.append(JQueryConvert.queryConvert(validatedData.getUketuke_id()));
		}

		query.append(" AND sonota.KENSA_NENGAPI =" );
		query.append(JQueryConvert.queryConvert(kensaJissiDate));
		query.append(" AND sonota.KOUMOKU_CD = master.KOUMOKU_CD " );
		query.append(" AND sonota.KEKA_TI is null ");
		query.append(" AND master.HISU_FLG = 1 ");

//		query.append(" AND KOUMOKU_CD NOT IN ( ");
//		query.append(JQueryConvert.queryConvertAppendComma("2A020000001930101"));
//		query.append(JQueryConvert.queryConvertAppendComma("2A030000001930101"));
//		query.append(JQueryConvert.queryConvertAppendComma("2A040000001930102"));
//		query.append(JQueryConvert.queryConvertAppendComma("9A110160700000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9A110160800000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9A110161000000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166000000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166100000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166200000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100166300000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100160900000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9E100161000000049"));
//		query.append(JQueryConvert.queryConvertAppendComma("9N501000000000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9N506000000000011"));
//		query.append(JQueryConvert.queryConvertAppendComma("9N511000000000049"));
//		query.append(JQueryConvert.queryConvert("9N516000000000049") + " )");

		return query.toString();
	}


	/************************* SQL制御 end *************************/

	/************************* アクション制御 start *************************/

	/*
	 * ActionListner
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		ExtendedButton btnCalled = null;
		JComboBox cmbCalled = null;

		boolean dialogCallFlg = false;
		boolean ptnCallFlg = false;

		if (source instanceof ExtendedButton){
			btnCalled = (ExtendedButton)source;
// del s.inoue 2012/07/13
//			if (btnCalled.getName() != null){
//				for (int i = 0; i < jButton_1.length; i++) {
//					if (jButton_1[i]!= null){
//					if (btnCalled.getName().equals(jButton_1[i].getName()))
//						System.out.println(jButton_1[i].getActionCommand());
//						dialogCallFlg = true;break;
//					}
//				}
//			}
		}else if ( source instanceof JComboBox){
			cmbCalled = (JComboBox)source;
			if (cmbCalled.getParent().getName()!=null){
			if (cmbCalled.getParent().getName().equals("jComboBox_Pattern"))
				ptnCallFlg = true;
			}
		}

		/* 終了ボタン */
		if (btnCalled == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		/* 登録ボタン */
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());

			String jissiDate = jTextField_KenshinJisiDay.getDateText();
			if (jissiDate == null || jissiDate.isEmpty()) {
				JErrorMessage.show("M3633", this);
				this.jTextField_KenshinJisiDay.grabFocus();
				return;
			}
			if (! validatedData.setKensaJissiDate(jissiDate,false)) {
				JErrorMessage.show("M3605", this);
				this.jTextField_KenshinJisiDay.grabFocus();
				return;
			}

			// 複製時
			if (isCopy){
				TKojinDao tKojinDao = null;
				try {
					tKojinDao = (TKojinDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), new TKojin());
				} catch (Exception ex) {
					ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(ex.getMessage());
				}

				long uketukeId = -1L;
				try {
					uketukeId = tKojinDao.selectNewUketukeId();
				} catch (Exception exx) {
					exx.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(exx.getMessage());
				}
				validatedData.setUketuke_id(new Long(uketukeId).toString());

				String copySeiriNo = jTextField_SeiriNo.getText();
				if (! validatedData.setcopyJyushinkenID(copySeiriNo)) {
					JErrorMessage.show("M3606", this);
					this.jTextField_SeiriNo.grabFocus();
					return;
				}
			}

			RETURN_VALUE Ret = JErrorMessage.show("M3622", this);
			if (Ret == RETURN_VALUE.YES) {
				if(registerTabInfomation())
				dispose();
				// add s.inoue 2012/04/24
				myTabClosingPreserve();
			}

			// add s.inoue 2012/11/07
			jButton_End.setFocusable(true);
			jButton_Clear.setFocusable(true);
		}
// del s.inoue 2012/01/24
//		/* 印刷ボタン→請求編集 */
//		else if (btnCalled == jButton_PrintRyoshu) {
//			String uketukeId = validatedData.getUketuke_id();
//			String hihokenjyasyoKigou = validatedData.getHihokenjyaCode();
//			String hihokenjyasyoNo = validatedData.getHihokenjyaNumber();
//			String kensaNengapi = validatedData.getKensaJissiDate();
//// eidt s.inoue 2012/01/20
//// 1.請求処理
//			// 請求ボタン制限解除
//			int count = 0;
//
//			Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
//
//			JOutputNitijiSearchListFrameData vo = new JOutputNitijiSearchListFrameData();
//
//			JInputKessaiDataFrameCtrl ctl = new JInputKessaiDataFrameCtrl();
//			JKessaiProcessData dataItem = ctl.getKesaiItemData(vo);
//			if (dataItem == null)
//				return;
//			datas.add(dataItem);
//
//				if( count != 0 )
//				{
//					try{
//						registerSeikyuData();
//					}catch (Exception ex){
//						try{
//							JApplication.kikanDatabase.rollback();
//						}catch(SQLException exx){
//						}
//						ex.printStackTrace();
//						logger.error(ex.getMessage());
//					}
//				}
//// 2.請求編集呼び出し
////			Vector<JKessaiProcessData> datas = new Vector<JKessaiProcessData>();
////			datas.add(e)
////
////
////			JInputKessaiDataFrameCtrl ctrl = new JInputKessaiDataFrameCtrl(
////					uketukeId,
////					hihokenjyasyoKigou,
////					hihokenjyasyoNo,
////					kensaNengapi,
////					datas);
////
////			WindowRefreshEvent win = new WindowRefreshEvent();
////
////			win.setselectedData(selectedRows.get(0));
////			JScene.CreateDialog(this,ctrl,win);
//		}
		/* クリアボタン */
		else if (btnCalled == jButton_Clear) {
			logger.info(jButton_Clear.getText());
			pushedClearButton(e);
// del s.inoue 2012/02/09
//		}
//		/* 反映ボタン */
//		else if (source == jButton_mytabClear) {
//			for (int i = 0; i < jTextField_2.length; i++) {
//				if (jTextField_2[i] != null)
//					jTextField_2[i].setText("");
//			}
//			for (int i = 0; i < jComboBox_2.length; i++) {
//				if (jComboBox_2[i] != null)
//					jComboBox_2[i].setSelectedIndex(-1);
//			}
//			for (int i = 0; i < jTextAreaField_2.length; i++) {
//				if (jTextAreaField_2[i] != null)
//					jTextAreaField_2[i].setText("");
//			}
		}else if (dialogCallFlg){
			serchKenshinDetailDialog(btnCalled.getName());
		}else if (ptnCallFlg){
//    	    if (e.getStateChange() == e.SELECTED && !isInit) {

//	    	if (kenshinPatternNumber == jComboBox_Pattern.getSelectedIndex() && isInit){
//	    		isInit=false;return;
//	    	}

	    	stateChangedKenshinPatternNumber(
						validatedData.getUketuke_id(),
						validatedData.getKensaJissiDate(),
						false,
						isNewKekkaData);
				// stateChangedKenshinPatternNumber("201108250001","20110913",false,isNewKekkaData);
		}
	}

    /*
     * 健診パターンコンボボックス処理
     */

//    private void kenshinPatternItemChange(){
////  		if (isInit) {
//    	jComboBox_Pattern.addItemListener(new ItemListener() {
//    	      public void itemStateChanged(ItemEvent e) {
//	    	    if (e.getStateChange()==e.SELECTED && !isInit) {
//					stateChangedKenshinPatternNumber(
//							validatedData.getUketuke_id(),
//							validatedData.getKensaJissiDate(),
//							false,
//							isNewKekkaData);
//					// stateChangedKenshinPatternNumber("201108250001","20110913",false,isNewKekkaData);
//				}
//    	      }
//    	});
////  		}
//    }

	/**
	 * 健診パターンのコンボボックスが変更された場合に呼ばれる
	 */
	public boolean stateChangedKenshinPatternNumber(String uketukeId,String jissiDate,boolean blnKensaRegist,boolean blnKekka) {
		
		// edit n.ohkubo 2015/03/01　追加　start
		//スクロール位置格納用変数の初期化
		scrollValueListMyTab = new ArrayList<Integer>();
		scrollValueListKenshinTab = new ArrayList<Integer>();
		scrollValueListSyosaiTab = new ArrayList<Integer>();
		scrollValueListTuikaTab = new ArrayList<Integer>();
		scrollValueListMonshinTab = new ArrayList<Integer>();
		// edit n.ohkubo 2015/03/01　追加　end

		boolean retPattan = false;

//		String query = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ";
//		String kenshinPatternName = null;
//		boolean blnNoEditPattarn = false;

//		ArrayList<Hashtable<String, String>> result = null;
//		Hashtable<String, String> ResultItem;

//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}

//		Object selectedItem = jComboBox_Pattern.getSelectedItem();
//		if (selectedItem != null)
//			kenshinPatternName = selectedItem.toString();

//		int selectedIdx = jComboBox_Pattern.getSelectedIndex();
		String patternValue = (String) jComboBox_Pattern.getValue();

//		// 登録時、変更前の健診パターンを使用し、コンボボックスのパターン変更時健診パターンのバックアップする
//		if (blnKensaRegist){
//			if (prekenshinPatternNumber == -1 || blnKekka){
//				blnNoEditPattarn = true;
//			}
//			kenshinPatternNumber = prekenshinPatternNumber;
//		}else{
//			prekenshinPatternNumber = kenshinPatternNumber;
//		}

//		for (int i = 0; i < result.size(); i++) {
//			ResultItem = result.get(i);
//			if (ResultItem.get("K_P_NAME").equals(kenshinPatternName)) {
//
//				// edit 20081201 s.inoue デフォルト値の場合処理をパターン削除行わない
//				if (kenshinPatternNumber == Integer.valueOf(ResultItem.get("K_P_NO"))) {
//					this.refreshTable(jissiDate, true,false,false);
//					break;
//				} else {
//
//					// add s.inoue 20081125 新規作成のときはチェック処理は行わない
//					Long resultCnt = 0L;
//					try {
//						resultCnt = kensakekaTokuteiDao.selectByCountPrimaryKey(Long.valueOf(
//								uketukeId),Integer.valueOf(jissiDate));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					if (resultCnt <= 0) {
//						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//						this.refreshTable(jissiDate, true,false,false);
//						break;
//					}
//
					// edit s.inoue 20081128 コンボ選択時、登録時でメッセージを変更する
					RETURN_VALUE retValue = null;
//
//					if (blnKensaRegist){ //登録実行時
//						kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//						// edit ver2 s.inoue 2009/08/25
//						// 削除実行
//						if (!blnNoEditPattarn){
//							if(pattarnKensaKoumoku()){
//								this.refreshTable(jissiDate, true,false,true);
//								retPattan= true;
//							}else{
//								// edit ver2 s.inoue 2009/08/25
//								this.refreshTable(jissiDate, true,false,false);
//								retPattan= false;
//							}
//						}
//					}else{ //コンボ選択時
						retValue = JErrorMessage.show("M3638", this);

						if (retValue == RETURN_VALUE.YES) {
//							kenshinPatternNumber = Integer.valueOf(ResultItem.get("K_P_NO"));
//							this.refreshTable(jissiDate, true,false,true);
							kenshinPatternNumber=Integer.parseInt(patternValue);
							JRegisterFrameTabSetting(null,false);
//							isInit = true;
							retPattan= true;
						}else if(retValue == RETURN_VALUE.NO) {
//							isInit = false;

							// eidt s.inoue 2013/04/10
							// jComboBox_Pattern.setSelectedIndex(kenshinPatternNumber - 1);
							DomainPair[] pairs = jComboBox_Pattern.getDomain().getDomainPairList();
							int selectedIDX = -1;
						    for (int i = 0; i < pairs.length; ++i){
						    	if (pairs[i] != null)
						    	if (pairs[i].getCode().toString().equals(String.valueOf(kenshinPatternNumber))){
						    		selectedIDX = i;
						    		System.out.println(jComboBox_Pattern.getSelectedIndex());
						    		break;
						    	}
						    }
						    jComboBox_Pattern.setSelectedIndex(selectedIDX);
						}
//					}
//
//					break;
//				}
//			}
//		}
		return retPattan;
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {

		myTabClosingPreserve();
		dispose();
	}

	// Tab保持
	private void myTabClosingPreserve(){
		// eidt s.inoue 2011/12/09
		try {
			int idxVal = 0;
			for (int i = 0; i < jPanelRegisterCenter.getTabCount(); i++) {
				String title = jPanelRegisterCenter.getTitleAt(i);
				if (title.equals("ﾏｲﾊﾟﾀｰﾝ")){
					idxVal = i+1;break;
				}
			}
			PropertyUtil.setProperty( "register.myTabPos", String.valueOf(idxVal) );
		}catch( Exception ex ){
			ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(ex.getMessage());
			JErrorMessage.show("M3551", this);
			return;
		}
	}
	/**
	 * クリアボタン
	 */
	public void pushedClearButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M3642", this);
		if (Ret == RETURN_VALUE.YES) {
			// 通常tab
			for (int i = 0; i < jTextField_1.length; i++){
				if(jTextField_1[i] != null)
					jTextField_1[i].setText("");
			}
			for (int i = 0; i < jComboBox_1.length; i++){
				if(jComboBox_1[i] != null)
					jComboBox_1[i].setSelectedIndex(-1);
			}
			for (int i = 0; i < jTextAreaField_1.length; i++){
				if(jTextAreaField_1[i] != null)
					jTextAreaField_1[i].setText("");
			}

			// add s.inoue 2012/02/09
			// myパターン
			for (int i = 0; i < jTextField_2.length; i++) {
			if (jTextField_2[i] != null)
				jTextField_2[i].setText("");
			}
			for (int i = 0; i < jComboBox_2.length; i++) {
			if (jComboBox_2[i] != null)
				jComboBox_2[i].setSelectedIndex(-1);
			}

			for (int i = 0; i < jTextAreaField_2.length; i++) {
				if (jTextAreaField_2[i] != null)
					jTextAreaField_2[i].setText("");
			}

			// add s.inoue 2013/02/27
			kensaKekka ="";
			height="";
			weight="";
		}
	}

	/*
	 * 登録処理
	 */
	private int mode;
	private String initialDate = "";
//	private boolean isInit = false;
	private boolean isNewKekkaData = false;

	public final static int INSERT_MODE = 1;
	public final static int UPDATE_MODE = 2;

	private boolean registerTabInfomation(){

		// 健診実施日
		// initialDate = validatedData.getKensaJissiDate();
		String jissiDate = jTextField_KenshinJisiDay.getDateText();

		/*
		 *  case1.初期表示の日付から変更があった場合は、警告を表示する。
		 */
		boolean hasJissiDateChanged = false;
		if (! jissiDate.equals(initialDate))
			hasJissiDateChanged = true;

		if (hasJissiDateChanged && ! this.isNewKekkaData) {
			/* 健診実施日変更した場合 */
			String[] messageParams = {initialDate, jissiDate};
			RETURN_VALUE retValue = JErrorMessage.show("M3543", this, messageParams);
			if (retValue == RETURN_VALUE.NO)
				return false;
		}

		/*
		 * case2.既に健診結果データが存在し、かつ初期表示の日付から変更があった場合は、
		 *       警告メッセージを表示する。
		 */
		boolean existsKensaKekkaData = existsKensaKekkaData(jissiDate);
		if (existsKensaKekkaData) {
			if (hasJissiDateChanged) {
				/* M3544=確認,[%s]には、すでに結果データが存在します。登録した場合、
				 * 以前に入力した結果データは消えてしまいます。登録してよろしいですか？,1,0 */
				String[] messageParams = {jissiDate};
				RETURN_VALUE retValue = JErrorMessage.show("M3544", this, messageParams);
				if (retValue != RETURN_VALUE.YES) {
					return false;
				}
			}

			mode = UPDATE_MODE;
		}
		else {
			mode = INSERT_MODE;
		}
		// eidt s.inoue 2012/04/24 間違い
		// 基準該当:1 予備軍該当:2 非該当:3 判定不能:4
		String hantei = "";
		switch (jComboBox_MetaboHantei.getSelectedIndex()) {
		case 0:hantei = "0";break;case 1:hantei = "1";break;	case 2:hantei = "2";break;	case 3:hantei = "3";break;	case 4:hantei = "4";break;
		}
		// 積極的支援:1 動機づけ支援:2 なし:3 判定不能:4
		String sido = "";
		switch (jComboBox_HokenSidouLevel.getSelectedIndex()) {
		case 0:sido = "0";break;case 1:sido = "1";break;	case 2:sido = "2";break;	case 3:sido = "3";break;case 4:sido = "4";break;
		}
		// 1: 基本的な健診 2: 基本的な健診＋詳細な健診の場合 3: 基本的な健診＋追加健診項目健診 4: 基本的な健診＋詳細な健診+追加健診項目 5: 人間ドック
		String seikyu = "";
		switch (jComboBox_SeikyuKubun.getSelectedIndex()) {
		case 0:seikyu = "1";break;	case 1:seikyu = "2";break;	case 2:seikyu = "3";break;	case 3:seikyu = "4";break;	case 4:seikyu = "5";break;
		}

		// エラーチェック
		if (!(validatedData.setSeikyuKubunCode(seikyu,false)
				&& validatedData.setHihokenjyaCode(jLabel_HihokenjyaKigo.getText(),false)
				&& validatedData.setHihokenjyaNumber(jLabel_HihokenjyaNo.getText(),false)
				&& validatedData.setNameKana(jLabel_Name.getText())
				&& validatedData.setKensaJissiDate(jissiDate,false)
				&& validatedData.setSougouComment(jTextArea_SougouComment.getText())
				&& validatedData.setMetaboHantei(hantei)
				&& validatedData.setHokenSidouLevel(sido)
				&& kenshinPatternNumber != -1))return false;

			TKojinDao tKojinDao = null;

			try {

				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				/*
				 * case3.複製押下時の処理
				 */
				if (isCopy){
					try {
						tKojinDao = (TKojinDao) DaoFactory.createDao(JApplication.kikanDatabase.getMConnection(), new TKojin());
						// 整理番号重複チェック処理
						Long resultCnt = tKojinDao.selectByCountUniqueKey(validatedData.getcopyJyushinkenID());
						if (resultCnt > 0)
							JErrorMessage.show("M3607", this);
					}catch (Exception ex) {
						ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
						logger.error(ex.getMessage());
					}

					// 名寄せ登録
					registerHistory();
					try {
						// T_KOJIN登録処理
						JApplication.kikanDatabase.sendNoResultQuery(registerKojinCopy());
					} catch (SQLException e) {
						e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
						logger.error(e.getMessage());
						// JApplication.kikanDatabase.rollback();
						// eidt s.inoue 2013/02/20
						JApplication.kikanDatabase.getMConnection().rollback();
						return false;
					}
				}

				/*
				 *  case4.その他テーブル登録処理
				 */
				// eidt s.inoue 2013/01/29
				// 戻り値によって変える
				boolean ret = registerKensaKekkaSonota(jissiDate,hasJissiDateChanged);
				if (!ret)return ret;

//					boolean isOtherValidated = false;
//					boolean isKekkaValidated = false;
//					HashMap<Integer, String> kekkaValidated = new HashMap<Integer, String>();
//
//					boolean isCheckCSV = false;
//					String errMessage = "";
//					int i = 0;

			} catch (SQLException f) {
				f.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
				logger.error(f.getMessage());
				try {
					// JApplication.kikanDatabase.rollback();
					// eidt s.inoue 2013/02/20
					JApplication.kikanDatabase.getMConnection().rollback();
					return false;
				} catch (SQLException g) {
					JErrorMessage.show("M0000", this);
					System.exit(1);
				}
			}
		return true;
	}

	/*
	 * case4.その他テーブルへの登録処理
	 */
	private boolean registerKensaKekkaSonota(String jissiDate, boolean hasJissiDateChanged){

		boolean validateKensa = false;
		// del s.inoue 2012/07/12
		// boolean hisuInput = false;
		boolean hanteiBase = false;
		boolean hanteiSyosai = false;
		boolean hanteiTuika = false;
		boolean notExtMessage = false;
		boolean validateInput = false;

		int intBaseCnt = 0;
		int intSyosaiCnt = 0;
		int intTuikaCnt = 0;
		int intAllCnt = 0;

		boolean isOtherValidated = false;
		boolean isKekkaValidated = false;
		boolean isCheckCSV = false;

		HashMap<Integer, String> kekkaValidated = new HashMap<Integer, String>();

		// 4.1.結果値に関する検証
		Hashtable<String, String> resultItem = null;
		String errMessage = "";
		String errhisuMessage = "";


		// move s.inoue 2013/08/13
		// メタボ、保健指導なし判定
		ArrayList<Hashtable<String, String>> codeResultm = null;
		// eidt s.inoue 2013/08/12
		String querym = new String("SELECT K_P_NO FROM T_KENSHIN_P_S WHERE (KOUMOKU_CD = "
		+ JQueryConvert.queryConvert("9N501000000000011") +" OR KOUMOKU_CD = "+ JQueryConvert.queryConvert("9N506000000000011")
		+ ") AND K_P_NO = " +kenshinPatternNumber
		+ " AND K_P_NO <> '9999' AND K_P_NO <> '-1'" );

		try {
			codeResultm = JApplication.kikanDatabase.sendExecuteQuery(querym);

			if (codeResultm.size() < 2){
				JErrorMessage.show("M3645", this);
				JApplication.kikanDatabase.getMConnection().rollback();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.warn(e.getMessage());
			JErrorMessage.show("M3645", this);
			try {
				JApplication.kikanDatabase.getMConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}


		for (int irow = 0; irow < tableResult.size(); irow++) {

			resultItem = tableResult.get(irow);

			String koumokuCode = resultItem.get(JConstantString.COLUMN_NAME_KOUMOKUCD);
			String koumokuName = resultItem.get(JConstantString.COLUMN_NAME_KOUMOKUNAME);
			String maxByte = resultItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH);
			String hl = resultItem.get(JConstantString.COLUMN_NAME_H_L).trim();
			String hantei = resultItem.get(JConstantString.COLUMN_NAME_HANTEI).trim();

			// 結果値以外に関しての検証
//			switch (jComboBox_SeikyuKubun.getSelectedIndex()) {
//			case 0:seikyu = "1";break;	case 1:seikyu = "2";break;	case 2:seikyu = "3";break;	case 3:seikyu = "4";break;	case 4:seikyu = "5";break;
//			}
			// eidt s.inoue 2012/06/27 毎回更新
			isOtherValidated = (validatedData.setHihokenjyaCode(jLabel_HiHokenjyaCode.getText(),false)
					&& validatedData.setHihokenjyaNumber(jLabel_HiHokenjyaNumber.getText(),false)
					&& validatedData.setKensaJissiDate(jissiDate,false)
					&& validatedData.setKensaKoumokuCode(koumokuCode)
					&& validatedData.setHL(hl)
					&& validatedData.setHantei(hantei));

//			&& validatedData.setComment((String) this.model.getData(i, COLUMN_IDX_KOMENTO))
//			&& validatedData.setChkText((String) this.model.getData(i, COLUMN_IDX_JISIKBN))

			// 注意）ロジック都合、位置調整用
			int choseiRow = irow;

			// eidt s.inoue 2013/07/09
			if (irow > posHokensido){
				choseiRow -= 1;
			}
			if (irow > posMetabo){
				choseiRow -= 1;
			}
//			if ((irow > posHokensido){
//				(irow > posMetabo))
//					choseiRow -= 2;

// del s.inoue 2013/08/13
//			// eidt s.inoue 2013/08/12
//			// メタボ、保健指導なし判定
//			ArrayList<Hashtable<String, String>> codeResultm = null;
//			// eidt s.inoue 2013/08/12
//			String querym = new String("SELECT K_P_NO FROM T_KENSHIN_P_S WHERE (KOUMOKU_CD = "
//			+ JQueryConvert.queryConvert("9N501000000000011") +" OR KOUMOKU_CD = "+ JQueryConvert.queryConvert("9N506000000000011")
//			+ " AND K_P_NO = " +kenshinPatternNumber
//			+ ") AND K_P_NO <> '9999' AND K_P_NO <> '-1'" );
//
//			try {
//				codeResultm = JApplication.kikanDatabase.sendExecuteQuery(querym);
//			} catch (SQLException e) {
//				logger.warn(e.getMessage());
//			}
//
//			// eidt s.inoue 2013/05/23
//			if (codeResultm.size() == 0){
//				JErrorMessage.show("M3645", this);
//				try {
//					JApplication.kikanDatabase.getMConnection().rollback();
//					return false;
//				} catch (SQLException e) {
//					logger.warn(e.getMessage());
//				}
//				return false;
//			}

			// 4.2.タイプ別処理
			// add s.inoue 2012/06/27
			// 前回値残る為、処理
			validatedData.setKekkaCode("");
			validatedData.setKekkaDecimal("",maxByte);
			validatedData.setKekkaText("", resultItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH));

			switch (Integer.valueOf(resultItem.get(JConstantString.COLUMN_NAME_DATA_TYPE))) {
			case 1:
				// 数値の場合
				if (jTextField_1[choseiRow] == null)continue;
				String kekka = (jTextField_1[choseiRow].getText()== null)?"":(String)jTextField_1[choseiRow].getText();

				isKekkaValidated = validatedData.setKekkaDecimal(kekka,maxByte);

				if (isKekkaValidated) {
					isCheckCSV = true;
					String retMessage = getHighLawMessage(koumokuCode,koumokuName,validatedData.getHokenjyaNumber(),validatedData.getKekka());

					if (!retMessage.isEmpty()){
						validateInput = true;
						errMessage +=retMessage;
					}
				}
				break;

			case 2:
				// コードの場合
//				if (jComboBox_1[choseiRow] == null)continue;	// edit n.ohkubo 2015/03/01　削除
				// edit n.ohkubo 2015/03/01　追加　start　「メタボ」と「保健指導」の判定に、コード値も追加
				if (jComboBox_1[choseiRow] == null) {
					continue;
				} else if ("9N501000000000011".equals(koumokuCode) || "9N506000000000011".equals(koumokuCode)) {
					continue;
				}
				// edit n.ohkubo 2015/03/01　追加　end　「メタボ」と「保健指導」の判定に、コード値も追加
				
// del s.inoue 2012/02/17 1次
// eidt s.inoue 2012/11/19 再修正
//				String kekkaCode = (jComboBox_1[choseiRow].getValue()== null)?"":(String)jComboBox_1[choseiRow].getValue();
				// eidt s.inoue 2012/12/26 コンボ改修でindexがずれてます！！！
				// String kekkaCode = String.valueOf(jComboBox_1[choseiRow].getSelectedIndex() == 0?"":jComboBox_1[choseiRow].getSelectedIndex());
				// String kekkaCode = String.valueOf(jComboBox_1[choseiRow].getSelectedIndex());
				// eidt s.inoue 2013/02/27 null回避
				String kekkaCode = "";
				if (jComboBox_1[choseiRow].getSelectedItem() != null )
					kekkaCode = String.valueOf(jComboBox_1[choseiRow].getSelectedItem().equals("")?"":jComboBox_1[choseiRow].getSelectedItem().toString().substring(0, 1));

				ArrayList<Hashtable<String, String>> codeResult = null;
				Hashtable<String, String> codeResultItem;
				String query = new String("SELECT KOUMOKU_CD, CODE_NUM, CODE_NAME FROM T_DATA_TYPE_CODE WHERE KOUMOKU_CD = "+ JQueryConvert.queryConvert(koumokuCode));
				try {
					codeResult = JApplication.kikanDatabase.sendExecuteQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.warn(e.getMessage());
				}

				if (kekkaCode.equals("")) {
					isKekkaValidated = validatedData.setKekkaCode("");
					isCheckCSV = true;
					break;
				}

				for (int j = 0; j < codeResult.size(); j++) {
					codeResultItem = codeResult.get(j);

					if (kekkaCode.equals(codeResultItem.get(JConstantString.COLUMN_NAME_CODE_NUM))) {
						isCheckCSV = CheckHighLawItem(kekkaCode,validatedData.getHokenjyaNumber(),codeResultItem.get(JConstantString.COLUMN_NAME_CODE_NUM));
						isKekkaValidated = validatedData.setKekkaCode(codeResultItem.get(JConstantString.COLUMN_NAME_CODE_NUM));
						break;
					}
				}
				break;

			case 3:
				// 文字列の場合
				if (jTextAreaField_1[choseiRow] == null)continue;

				String kekkaArea = (jTextAreaField_1[choseiRow].getText()== null)?"":(String)jTextAreaField_1[choseiRow].getText();
				isCheckCSV = true;
				isKekkaValidated= validatedData.setKekkaText(kekkaArea, resultItem.get(JConstantString.COLUMN_NAME_MAX_BYTE_LENGTH));
				break;
			}

			// 実施区分
			// String jisi = jCheckBox_1[choseiRow].getValue();
			if (jCheckBox_1[choseiRow].isSelected()){
				validatedData.setJisiKbn("2");
			}else{
			// String kekka = validatedData.getKekka();
				validatedData.setJisiKbn("1");
			}

			if (!isKekkaValidated)
				kekkaValidated.put(choseiRow,"項目コード:" + koumokuCode + "  項目名:" + koumokuName);

			if (!(isKekkaValidated && isOtherValidated && isCheckCSV)) {
				if (!isKekkaValidated) {
					String[] params = { kekkaValidated.get(choseiRow) };
					JErrorMessage.show("M3601", this, params);
				} else if (!isCheckCSV) {
					String[] params = { koumokuCode, koumokuName };
					JErrorMessage.show("M3602", this, params);
				} else if (!isOtherValidated){
					String[] params = { koumokuCode, koumokuName };
					JErrorMessage.show("M3608", this, params);
				}
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				// eidt s.inoue 2013/02/20
				try {
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e) {
					logger.warn(e.getMessage());
				}
				return false;
			}

			try {
				// 4.3.その他objへの格納処理
				// 受付ID,年度の追加対応 検査結果特定、その他テーブルへ新規レコード挿入
				kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
				kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
				kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
				try {
					kensakekaSonota.setKENSA_NENGAPI(new Integer(validatedData.getKensaJissiDate()));
					kensakekaSonota.setNENDO(FiscalYearUtil.getThisYear(validatedData.getKensaJissiDate()));
				} catch (NumberFormatException e) {
					e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(e.getMessage());
				}
				kensakekaSonota.setKOUMOKU_CD(validatedData.getKensaKoumokuCode());
				kensakekaSonota.setKEKA_TI(validatedData.getKekka());
				kensakekaSonota.setKOMENTO(validatedData.getComment());

				// 実施区分
				Integer jisiInteger = null;
				String strJisi = validatedData.getJisi_KBN();
				if (strJisi != null && !strJisi.isEmpty())
					jisiInteger = new Integer(strJisi);

				kensakekaSonota.setJISI_KBN(jisiInteger);
				// HL
				Integer hlInteger = null;
				String hlString = validatedData.getHL();
				if (hlString != null && !hlString.isEmpty())
					hlInteger = new Integer(hlString);

				kensakekaSonota.setH_L(hlInteger);
				// 判定
				kensakekaSonota.setHANTEI(new Integer(validatedData.getHantei()));
			} catch (NumberFormatException e) {
				/* 何もしない */
				/*	// edit n.ohkubo 2015/03/01　追加　コメントの追加
					「kensakekaSonota.setHANTEI(new Integer(validatedData.getHantei()));」で
					java.lang.NumberFormatException: For input string: ""が発生している
					printStackTraceしたいが、ループ内なので大量に出るからやめる
					「判定結果」が「未判」だと、""にしているが、空文字は数字ではないので当然エラーになる
					取りあえず正常に動いているなら対応しない
					何かおかしければ再度見直す
				 */
			}

// del s.inoue 2011/11/16
//			// 結果値(未実施,測定不可能)判定
//			if ((validatedData.getJisi_KBN().equals("0") ||
//					validatedData.getJisi_KBN().equals("2")) &&
//					!validatedData.getKekka().equals("")){
//				String[] params = { koumokuCode, koumokuName };
//				JErrorMessage.show("M3634", this, params);
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//				}
//				return false;
//			}
			try{
				// add s.inoue 2013/01/28
				if (!validatedData.getKensaJissiDate().equals("")){
					if (koumokuCode.equals(PrintDefine.CODE_HBA1C_SONOTA_JDS)||
						koumokuCode.equals(PrintDefine.CODE_HBA1C_COUSOHO_JDS)||
						koumokuCode.equals(PrintDefine.CODE_HBA1C_HPLC_JDS)||
						koumokuCode.equals(PrintDefine.CODE_HBA1C_RATEX_JDS)){

						int tDate = 20130401;
						// 健診実施日が'130401以降
						if (tDate <= Integer.parseInt(validatedData.getKensaJissiDate())){
							if (!validatedData.getKekka().equals("")){
								JErrorMessage.show("M3643", this);
								// eidt s.inoue 2013/05/23
								try {
									JApplication.kikanDatabase.getMConnection().rollback();
								} catch (SQLException e) {
									logger.warn(e.getMessage());
								}
								return false;
							}
						}
					// add s.inoue 2013/01/29
					}else if(koumokuCode.equals( PrintDefine.CODE_HBA1C_SONOTA_NGSP)||
							koumokuCode.equals( PrintDefine.CODE_HBA1C_COUSOHO_NGSP)||
							koumokuCode.equals( PrintDefine.CODE_HBA1C_HPLC_NGSP)||
							koumokuCode.equals( PrintDefine.CODE_HBA1C_RATEX_NGSP)
							){
						int tDate = 20130401;
						// 健診実施日が'130401以降
						if (tDate > Integer.parseInt(validatedData.getKensaJissiDate())){
							if (!validatedData.getKekka().equals("")){
								JErrorMessage.show("M3644", this);
								// eidt s.inoue 2013/05/23
								try {
									JApplication.kikanDatabase.getMConnection().rollback();
								} catch (SQLException e) {
									logger.warn(e.getMessage());
								}
								return false;
							}
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
				logger.error(e.getMessage());
				System.out.println(e.getMessage());
			}

			// 登録対象のフラグチェック
			// 例外の項目(質問票に関わる項目)
			String KoumokuHd = koumokuCode.substring(0, 3);
			if(!KoumokuHd.equals(JConstantString.CODE_MONSHIN_HEADER)&&
				!koumokuCode.equals(JConstantString.CODE_HOKEN_SHIDOU) &&
				!koumokuCode.equals(JConstantString.CODE_SEIKATU_KAIZEN) &&
				!koumokuCode.equals(JConstantString.CODE_SAIKETSU_ZIKAN) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_1) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_2) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_3) &&
				!koumokuCode.equals(JConstantString.CODE_ISHINOHANDAN_4)){

				String hisuFlg = resultItem.get(JConstantString.COLUMN_NAME_HISU_FLG);

				// add s.inoue 2012/03/07
				// if (!hisuInput)
//				if (!kekkaMessage.isEmpty())

					// eidt s.inoue 2012/03/08
					// errhisuMessage = errhisuMessage +"[改行]項目コード[" + koumokuCode + "]"+ "項目名[" + koumokuName +"]";
					errhisuMessage = errhisuMessage +"[改行]     " + koumokuName +"  ";

// del s.inoue 2012/07/12
//					if (hisuFlg.equals(JApplication.SEIKYU_KBN_BASE)
//							&& validatedData.getKekka().equals("")){
//						hisuInput = true;
//					}

				if ((validatedData.getJisi_KBN().equals("1") && !validatedData.getKekka().equals("")) ||
					((validatedData.getJisi_KBN().equals("2") || validatedData.getJisi_KBN().equals("0"))
							&& validatedData.getKekka().equals(""))){

					if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_BASE)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;intBaseCnt +=1;
						}
					}else if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_SYOSAI)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
							hanteiSyosai = true;
						}
						if (hanteiBase && hanteiSyosai){
							intSyosaiCnt +=1;
						}
					}else if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_TUIKA)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
							hanteiTuika = true;
						}
						if (hanteiBase && hanteiTuika){
							intTuikaCnt +=1;
						}
					}else if (validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_SYOSAI_TUIKA) ||
							validatedData.getSeikyuKubunCode().equals(JApplication.SEIKYU_KBN_DOC)){
						if (hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							hanteiBase = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_SYOSAI)){
							hanteiSyosai = true;
						}else if (hisuFlg.equals(JApplication.HISU_FLG_TUIKA)){
							hanteiTuika = true;
						}
						if (hanteiBase && hanteiSyosai && hanteiTuika){
							intAllCnt +=1;
						}
					}
				}
			}
			// 検査項目整合性チェック
			validateKensa= isNotExistKensaKoumoku(
					    validatedData.getHokenjyaNumber(),
						validatedData.getUketuke_id(),
						validatedData.getKensaJissiDate(),
						validatedData.getSeikyuKubunCode(),
						validatedData.getKensaKoumokuCode(),
						validatedData.getKekka(),
						validatedData.getJisi_KBN());

			if (validateKensa){

				if (!notExtMessage){
					JErrorMessage.show("M3635", this);
					notExtMessage = true;
				}
			}

		try{
			if (! this.isCopy && ! this.isNewKekkaData) {
					kensakekaSonotaDao.delete(
							new Long(validatedData.getUketuke_id()),
							new Integer(this.initialDate),
							new String(validatedData.getKensaKoumokuCode()));

			}
			kensakekaSonotaDao.insert(kensakekaSonota);
		} catch (Exception e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(e.getMessage());
		}

		// for close
		}

// del s.inoue 2012/07/12
//		if (hisuInput){
//			String[] params = { errhisuMessage };
//			JErrorMessage.show("M3641", this, params);
//		}

		if (validateInput){
			String[] params = { errMessage };
			JErrorMessage.show("M3602", this, params);
// del s.inoue 2013/02/20 継続
//			try {
//				JApplication.kikanDatabase.getMConnection().rollback();
//			} catch (SQLException e) {
//				logger.warn(e.getMessage());
//			}
//			// return true;
//			return false;
		}

		if ((intBaseCnt == 0) &&(intSyosaiCnt == 0) &&(intTuikaCnt == 0) && (intAllCnt == 0)){
			// メッセージは一回きりにする
			if (!notExtMessage && isCheckCSV){
				JErrorMessage.show("M3636", this);
				notExtMessage = true;
			}
		}

//			// テーブルの最後の行をinsertする前にbreakされた場合
//			if (choseiRow != tableResult.size()) {
//				try {
//					JApplication.kikanDatabase.rollback();
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//				}
//				return false;
//			}

		// 次にメタボリックシンドローム判定と保健指導レベルに関しての処理を行う
		try {
			// 検査結果データその他レコード削除・挿入
			kensakekaSonota = new TKensakekaSonota();
			kensakekaSonota.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
			kensakekaSonota.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
			kensakekaSonota.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
			kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(jissiDate));
			kensakekaSonota.setKENSA_NENGAPI(new Integer(jissiDate));

			// まずメタボリックシンドロームに関する処理を行う
			kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
					this.initialDate.equals("")?new Integer(jissiDate):new Integer(this.initialDate),"9N501000000000011");

			// add s.inoue 2012/04/26
			JApplication.kikanDatabase.getMConnection().commit();

			kensakekaSonota.setKOUMOKU_CD("9N501000000000011");
			kensakekaSonota.setKEKA_TI(validatedData.getMetaboHantei());
			kensakekaSonotaDao.insert(kensakekaSonota);

			// 保健指導レベルに関する処理を行う
			kensakekaSonotaDao.delete(new Long(validatedData.getUketuke_id()),
					this.initialDate.equals("")?new Integer(jissiDate):new Integer(this.initialDate),"9N506000000000011");
			// add s.inoue 2012/04/26
			JApplication.kikanDatabase.getMConnection().commit();

			kensakekaSonota.setKOUMOKU_CD("9N506000000000011");
			kensakekaSonota.setKEKA_TI(validatedData.getHokenSidouLevel());
			kensakekaSonotaDao.insert(kensakekaSonota);

			// 特定テーブル(update,insert)処理
			if (mode == UPDATE_MODE) {
				JApplication.kikanDatabase.sendNoResultQuery(getUpdateTokutei(jissiDate));
			} else if (mode == INSERT_MODE) {
				kensakekaTokutei = new TKensakekaTokutei();
				kensakekaTokutei.setUKETUKE_ID(new Long(validatedData.getUketuke_id()));
				kensakekaTokutei.setHIHOKENJYASYO_KIGOU(validatedData.getHihokenjyaCode());
				kensakekaTokutei.setHIHOKENJYASYO_NO(validatedData.getHihokenjyaNumber());
				kensakekaTokutei.setKENSA_CENTER_CD(validatedData.getKensaCenterCode());
				kensakekaTokutei.setK_P_NO(new Integer(kenshinPatternNumber));
				kensakekaTokutei.setKOMENTO(validatedData.getSougouComment());
				kensakekaTokutei.setKENSA_NENGAPI(new Integer(jissiDate));
				kensakekaTokutei.setNENDO(FiscalYearUtil.getThisYear(jissiDate));

				// このあとUPDATEを行うため一時的にデータを入れる
				kensakekaTokutei.setKEKA_INPUT_FLG(new Integer(1));
				kensakekaTokutei.setSEIKYU_KBN(new Integer(validatedData.getSeikyuKubunCode()));
				kensakekaTokuteiDao.insert(kensakekaTokutei);
			}

			/* 複製ではなく、かつ日付変更である場合は、元のデータを削除する。 */
			if (! this.isCopy && hasJissiDateChanged && ! this.isNewKekkaData) {
				kensakekaTokuteiDao.delete(validatedData.getUketuke_id(),this.initialDate);
			}

			ArrayList<Hashtable<String, String>> rs = JApplication.kikanDatabase.sendExecuteQuery(getRecordCount_Sonota(jissiDate));

			/* 入力数 */
			int nullCount = rs.size();

			// 全ての項目について入力がなされていた場合
			String kensakekaInputFlg = (nullCount == 0) ? "2": "1";
			String query = String.format("UPDATE T_KENSAKEKA_TOKUTEI SET KEKA_INPUT_FLG = '%s' WHERE UKETUKE_ID = '%s' AND KENSA_NENGAPI = '%s'",
										kensakekaInputFlg, validatedData.getUketuke_id(),jissiDate);
			System.out.println(query);

			JApplication.kikanDatabase.sendNoResultQuery(query);

			// 結果があるかどうか登録処理時に、コミット前の状態のフラグを渡す
			// JApplication.kikanDatabase.Commit();
			JApplication.kikanDatabase.getMConnection().commit();

			// stateChangedKenshinPatternNumber(validatedData.getUketuke_id(),jissiDate,true,isNewKekkaData);

			this.initialDate = this.validatedData.getKensaJissiDate();
			this.isNewKekkaData = false;

		} catch (Exception e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(e.getMessage());
			try {
				// JApplication.kikanDatabase.rollback();
				// eidt s.inoue 2013/02/20
				JApplication.kikanDatabase.getMConnection().rollback();
				return false;
			} catch (SQLException ex) {
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
		if (mode == INSERT_MODE) {
			mode = UPDATE_MODE;
			// jTextField_SeiriNo.setVisible(false);
			// jLabelSeirino.setVisible(false);
			isCopy = false;
		}
		isPrev = true;
		return true;
	}

	/*
	 * 上限値下限値チェックによるメッセージ取得を行う
	 */
	public String getHighLawMessage(String koumokuCode,String koumokuName,String hknjaNm,String value){

		ArrayList<Hashtable<String, String>> result = null;
		String retMessage = "";
		String kekkaMessage ="";

		try{
			String[] params = { hknjaNm,koumokuCode };

			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";

			// 空値の場合はチェックを行わない
			if (!value.isEmpty() || !hknjaNm.isEmpty()) {
				String strKagen = "";
				String strJyogen = "";

				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);

				for (int i = 0; i < result.size(); i++) {

					strKagen = result.get(i).get("KAGEN");
					strJyogen = result.get(i).get("JYOUGEN");

					// 項目コードの結果判定の部分を除いて比較を行う範囲内の場合 true
					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
						if (value.isEmpty())
							break;
						if (strKagen.isEmpty() && strJyogen.isEmpty()){
							break;
						}else if (strKagen.isEmpty()){
							if (Double.valueOf(strJyogen) < Double.valueOf(value)){
								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"を超えています。";
							}
						}else if(strJyogen.isEmpty()){
							if (Double.valueOf(strKagen) > Double.valueOf(value)){
								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"を下回っています。";
							}
						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
							if (Double.valueOf(strKagen) > Double.valueOf(value)){
								kekkaMessage= strLowMessageValue + "["+ Double.valueOf(strKagen) + "]" +"を下回っています。";
							}else if(Double.valueOf(strJyogen) < Double.valueOf(value)){
								kekkaMessage= strHighMessageValue + "["+ Double.valueOf(strJyogen) + "]" +"を超えています。";
							}
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(ex.getMessage());
		}

		if (!kekkaMessage.isEmpty())
			retMessage = "[改行]項目コード[" + koumokuCode + "] 項目名[" + koumokuName + "][改行]の結果値[" + validatedData.getKekka() + "]が" + kekkaMessage;

		return retMessage;
	}

	/*
	 * 上限値下限値の項目をチェックを行う
	 */
	public static boolean CheckHighLawItem(String koumokuCode,String hknjaNm,String value){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			String[] params = { hknjaNm,koumokuCode };
			String sql = "SELECT KOUMOKU_CD,KAGEN,JYOUGEN FROM T_KENSHINMASTER WHERE HKNJANUM = ? AND KOUMOKU_CD = ? ";

			// 空値の場合はチェックを行わない
			if (!value.isEmpty() || !hknjaNm.isEmpty()) {
				String strKagen = "";
				String strJyogen = "";

				result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);

				for (int i = 0; i < result.size(); i++) {

					strKagen = result.get(i).get("KAGEN");
					strJyogen = result.get(i).get("JYOUGEN");

					// 項目コードの結果判定の部分を除いて比較を行う
					// 範囲内の場合 true
					if (result.get(i).get("KOUMOKU_CD").equals(koumokuCode)) {
						if (value.isEmpty())break;
						if (strKagen.isEmpty() && strJyogen.isEmpty()){
							break;
						}else if (strKagen.isEmpty()){
							return Double.valueOf(strJyogen) >= Double.valueOf(value);
						}else if(strJyogen.isEmpty()){
							return Double.valueOf(strKagen) <= Double.valueOf(value);
						}else if (!strKagen.isEmpty() && !strJyogen.isEmpty()){
							return (Double.valueOf(strKagen) <= Double.valueOf(value)
									&& Double.valueOf(value) <= Double.valueOf(strJyogen));
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.warn(ex.getMessage());
		}
		return true;
	}

	/**
	 * 請求区分による検査項目チェック
	 */
	public static boolean isNotExistKensaKoumoku(
			String hokenjyaNumber,
			String uketukeId,
			String kensaDate,
			String seikyuKbn,
			String koumokuCd,
			String kekkati,
			String jisiKbn
			)
	{
			boolean retChk = false;
			boolean hanteiHisu = false;

			// 例外の項目(質問票に関わる項目)
			String KoumokuHd = koumokuCd.substring(0, 3);

			if(KoumokuHd.equals(JConstantString.CODE_MONSHIN_HEADER) ||
					!koumokuCd.equals(JConstantString.CODE_HOKEN_SHIDOU)  ||
					!koumokuCd.equals(JConstantString.CODE_SEIKATU_KAIZEN)  ||
					!koumokuCd.equals(JConstantString.CODE_SAIKETSU_ZIKAN)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_1)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_2)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_3)  ||
					!koumokuCd.equals(JConstantString.CODE_ISHINOHANDAN_4)){
				return false;
			}

			// 基本的な健診,詳細な健診,追加健診項目,人間ドック
			StringBuffer buffer = new StringBuffer();
			String[] params =null;

			// 新規登録時のチェックが効かない為
			buffer.append(" select master.HISU_FLG ");
			buffer.append(" from T_KENSHINMASTER master ");
			buffer.append(" where master.HKNJANUM = ?");
			buffer.append(" and KOUMOKU_CD = ?");

			params = new String []{ hokenjyaNumber,koumokuCd  };

			String query = buffer.toString();

			ArrayList<Hashtable<String, String>> result = null;
			try{
				result = JApplication.kikanDatabase.sendExecuteQuery(query, params);
			}catch(SQLException ex){
				ex.printStackTrace();
			}

			for (Hashtable<String, String> item : result) {
				String hisuFlg = item.get("HISU_FLG");
				//String tanka = item.get("TANKA_KENSIN");
				// 登録対象のフラグチェック
				if (jisiKbn.equals("1")){
					if (!kekkati.equals("")){
						hanteiHisu = true;
					}
				}else if (jisiKbn.equals("2") ||
						jisiKbn.equals("0")){
					if (kekkati.equals("")){
						hanteiHisu = true;
					}
				}

				if (hanteiHisu){
					// 整合性確認用
					if (seikyuKbn.equals(JApplication.SEIKYU_KBN_BASE)){
						if (!hisuFlg.equals(JApplication.HISU_FLG_BASE)){
							retChk = true;break;
						}
					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_SYOSAI)){
						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
								hisuFlg.equals(JApplication.HISU_FLG_SYOSAI))){
							retChk = true;break;
						}
					}else if (seikyuKbn.equals(JApplication.SEIKYU_KBN_TUIKA)){
						if (!(hisuFlg.equals(JApplication.HISU_FLG_BASE) ||
								hisuFlg.equals(JApplication.HISU_FLG_TUIKA))){
							retChk = true;break;
						}
					}
				}

			}
		return retChk;
	}

	/*
	 * 検査結果データ
	 */
	private boolean existsKensaKekkaData(String kensaJissiDate) {
		Integer kensanengapi = null;
		try {
			kensanengapi = new Integer(kensaJissiDate);
		} catch (NumberFormatException e) {
			e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
		}

		List<TKensakekaSonota> resultList = null;
		try {
			String uketuke_id = validatedData.getUketuke_id();
			resultList = kensakekaSonotaDao.selectByUketukeIdKensaNengapi(
					new Long(uketuke_id),
					kensanengapi);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean exsistsKensaKekkaData = false;
		if (resultList != null &&  resultList.size() > 0) {
			exsistsKensaKekkaData = true;
		}

		return exsistsKensaKekkaData;
	}

	/*
	 * 履歴,名寄せテーブルへ登録処理を実施する。
	 */
	private void registerHistory(){

		TNayoseDao tNayoseDao = null;
		StringBuilder nayoseQuery = null;
		StringBuilder newNayoseQuery = null;

			// 名寄せNO採番
			long nayoseNo = -1L;
			long retTNayoseNo = 0L;

			// 現時刻を取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String stringTimeStamp = dateFormat.format(Calendar.getInstance().getTime());

			try {
				/* T_NAYOSE Dao を作成する。 */
				tNayoseDao = (TNayoseDao) DaoFactory.createDao(
						JApplication.kikanDatabase.getMConnection(), new TNayose());

				retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedData.getUketukePre_id()));
				// 既に履歴がある場合、その名寄せNoを使用する
				if (retTNayoseNo > 0) {
					nayoseNo = retTNayoseNo;
				}else{
					nayoseNo = tNayoseDao.selectNewNayoseNo();

					// 名寄せテーブル登録処理
					// 受付IDの紐付け処理:1、2をセット
					// 1.受診券情報で氏名かなで紐付けた受付IDを登録
					// 2.受診券登録後に新しい受付番号を登録

					// 1.処理:履歴がない場合
					nayoseQuery = new StringBuilder();
					nayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
					nayoseQuery.append("VALUES (");
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketukePre_id()));
					nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
					nayoseQuery.append(") ");

					try {
						JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
					} catch (SQLException e) {
						e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
						logger.error(e.getMessage());

						try {
							// eidt s.inoue 2013/02/20
							// JApplication.kikanDatabase.rollback();
							JApplication.kikanDatabase.getMConnection().rollback();
							logger.warn(e.getMessage());
						} catch (SQLException g) {
							logger.error(g.getMessage());
						}
					}
				}

				// 2.処理:採番した追加用レコード共通処理
				newNayoseQuery = new StringBuilder();
				newNayoseQuery.append("INSERT INTO T_NAYOSE (NAYOSE_NO,UKETUKE_ID,UPDATE_TIMESTAMP)");
				newNayoseQuery.append("VALUES (");
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(String.valueOf(nayoseNo)));
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketuke_id()));
				newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
				newNayoseQuery.append(") ");

				try {
					JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
				} catch (SQLException e) {
					e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
					logger.error(e.getMessage());
					try {
						// JApplication.kikanDatabase.rollback();
						JApplication.kikanDatabase.getMConnection().rollback();
						logger.warn(e.getMessage());
					} catch (SQLException g) {
						logger.error(g.getMessage());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
				logger.error(e.getMessage());
			}
	}


    /*
     * 健診パターン検索ダイアログ
     */
    private boolean serchKenshinDetailDialog(String koumokuCD){

    	boolean cancelFlg = false;


		// HashMap<String,ArrayList<String>> hm = JConstantString.getKensaHouhouMap();
    	HashMap<String,ArrayList<String>> hm = JConstantString.getKensaHouhouMap(koumokuCD);

    	// hm → arraylist
    	Object[] objKey=hm.keySet().toArray();

    	String keyStr = (String)objKey[0];
    	// keyStr = keyStr.substring(0,keyStr.indexOf(","));

    	ArrayList<String> arr = hm.get(keyStr);

//    	for (int i = 0; i < arr.size(); i++) {
//			if (arr.get(i))
//		}

		try {
			patternSelectDialog = DialogFactory.getInstance().createDialog(this,arr);

			// 健診実施日入力ダイアログを表示
			patternSelectDialog.setMessageTitle("検査方法選択");
			patternSelectDialog.setVisible(true);
			// 戻値格納
			// eidt s.inoue 2012/07/06
			if (patternSelectDialog.getStatus() == null)return false;
			if(patternSelectDialog.getStatus().equals(RETURN_VALUE.YES)){
				System.out.println(patternSelectDialog.getTextValue());
				// jLabel_KensaKoumoku.setText(arr.get(0));
				jLabel_KensaHouhou_Value.setText(arr.get(0)+ " " + kensaHouhouCodeList.get(koumokuCD));

//				kenshinPatternNumver = patternSelectDialog.getPrintSelect();
			}else if (patternSelectDialog.getStatus().equals(RETURN_VALUE.CANCEL)){
				cancelFlg = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();	// edit n.ohkubo 2015/03/01　追加
			logger.error(ex.getMessage());
			// eidt s.inoue 2012/07/06
			JErrorMessage.show("M3552", null);
		}

//		System.out.println("選択健診パターン:" + kenshinPatternNumver);
		return cancelFlg;
    }

    /************************* アクション制御 end *************************/
    
	// edit n.ohkubo 2015/03/01　追加　start　スクロール位置調整
	/**
	 * スクロールバーの値（位置）を設定する
	 * 
	 * @param scrollValueList	値を設定するリスト（各タブごと）
	 * @param isTextField		テキストフィールドの場合：true、以外：false
	 */
	private void addScrollValue(List<Integer> scrollValueList, boolean isTextField) {
		int addVal;//WindowsとLinuxで加算する値を変える（同じ値だと位置にずれが出る）
		if (System.getProperty("os.name").indexOf("Windows") >= 0) {
//			System.out.println("addScrollValue Windows");
			addVal = 80;
		} else {
//			System.out.println("addScrollValue Linux");
			addVal = 64;
		}
		int scrollValue;
		int preValue = (scrollValueList.size() == 0) ? 0 : scrollValueList.get(scrollValueList.size() - 1).intValue();
		if (isTextField) {
			scrollValue = (preValue + forcus_range + addVal);
		} else {
			scrollValue = (preValue + forcus_range);
		}
//		System.out.println("addScrollValue　scrollValue:[" + scrollValue + "] isTextField:[" + isTextField + "]");
		scrollValueList.add(Integer.valueOf(scrollValue));
	}
	/**
	 * スクロールバーの値（位置）を取得する
	 * 
	 * @param scrollValueList	値を取得するリスト（各タブごと）
	 * @param index				対象の項目の順番
	 * @param isFoward			下方向に移動（エンター）の場合：true、上方向に移動（シフト＋エンター）の場合：false
	 * @return
	 */
	private int getScrollValue(List<Integer> scrollValueList, int index, boolean isFoward) {
		int result;
		if (isFoward) {
			result = (scrollValueList.get(index).intValue() - forcus_foward);
		} else {
			int temp = (index > 0) ? scrollValueList.get(index - 1).intValue() : scrollValueList.get(0).intValue();
			result = (temp - forcus_back - 60);
		}
//		System.out.println("getScrollValue　result:[" + result + "] index:[" + index + "] isFoward:[" + isFoward + "]");
		return result;
	}
	// edit n.ohkubo 2015/03/01　追加　end　スクロール位置調整
}

/************************* 部品 *************************/
//jTextField_1[irowAll].addKeyListener(new KeyAdapter() {
//public void keyPressed(KeyEvent e) {
//	int keycode = e.getKeyCode();
//    int mod = e.getModifiersEx();
//    int rangeVal = 0;
//
//    if ((keycode == KeyEvent.VK_ENTER) &&
//    			  (mod & InputEvent.SHIFT_DOWN_MASK) != 0){
//    	rangeVal = irow*12;
//	}else if (keycode == KeyEvent.VK_ENTER){
//		rangeVal = irow*20;
//	}
//		jScrollbarKihon.setValue(rangeVal);jScrollbarSyosai.setValue(rangeVal);
//    	jScrollbarTuika.setValue(rangeVal);jScrollbarMonshin.setValue(rangeVal);
//    }
//});
/************************* 部品 *************************/
