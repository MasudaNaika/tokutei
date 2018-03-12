package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TextFieldWithComboBoxDocumentListener;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JYearAge;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.frame.ProgressWindow;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenTextControl;
import jp.or.med.orca.jma_tokutei.common.orca.JOrcaInfoSearchCtl;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.sql.dao.DaoFactory;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TKojinDao;
import jp.or.med.orca.jma_tokutei.common.sql.dao.TNayoseDao;
import jp.or.med.orca.jma_tokutei.common.sql.model.TKojin;
import jp.or.med.orca.jma_tokutei.common.sql.model.TNayose;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.hokenja.JHokenjyaMasterMaintenanceEditFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameData.T_KOJIN_COLUMN;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectKojinFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.select.JSelectKojinFrameData;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai.JShiharaiMasterMaintenanceEditFrameCtrl;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintTsuikaKenshin;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print.PrintNyuryoku;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.printdata.Kojin;

import org.apache.log4j.Logger;


/**
 * 個人登録フレーム
 */
public class JKojinRegisterFrameCtrl extends JKojinRegisterFrame {
	
	private static final long serialVersionUID = 920280230214173874L;	// edit n.ohkubo 2014/10/01　追加
	
	private static final String STRING_UNIT_PERCENT = "％";
	private static final String STRING_UNIT_YEN = "円";
	private static final int COMBOBOX_NUMBER_START_INDEX = 1;
	private static final String TEXT_NO_NAME = "（名称が設定されていません）";
	final static int ADD_MODE = 1;
	final static int CHANGE_MODE = 2;
	private int mode = ADD_MODE;

	private static final String COMBOBOX_ITEM_ADD_NEWITEM = "＜新規追加＞";
	private JKojinRegisterFrameData validatedData = new JKojinRegisterFrameData();  //  @jve:decl-index=0:
	private boolean isFromKekkaRegster = false;
	private static final String FLAME_TITLE_NAME_MESSAGE = "同姓同名の受診者が存在します。";
	private static final String FLAME_TITLE_JYUSINNO_MESSAGE = "受診券整理番号が同一の受診者が存在します。";

	/** 保険者番号、支払代行機関選択欄の、番号と名称の区切り文字列 */
	private static final String COMBOBOX_NUMBER_NAME_SEPARATOR = " ";
	/** 保険者番号の正規表現 */
//	private static final String REGEX_HOKENJA_NUMBER = "\\d{1,8}";	// edit n.ohkubo 2014/10/01　未使用なので削除
	/** 支払代行機関番号の正規表現 */
//	private static final String REGEX_DAIKOU_NUMBER = "\\d{1,8}";	// edit n.ohkubo 2014/10/01　未使用なので削除

//	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private TKojinDao tKojinDao;
	private TNayoseDao tNayoseDao;
	private static Logger logger = Logger.getLogger(JKojinRegisterFrameCtrl.class);
	private String[] itakuKubunStr = { "1:個別", "2:集団" };

	private boolean keiyakuCode_flg = false;
	private boolean futanCode_flg = false;
	private boolean nyuryokuCode_flg = false;
	// eidt s.inoue 2011/08/01
	private boolean yearOld_flg = false;

	// edit s.inoue 2009/10/04
	private IDialog dateSelectDialog;
	// add s.inoue 2010/01/15
	private static final String TANKA_HANTEI_KIHON = "1";
	private static final String TANKA_HANTEI_DOC = "2";

	// eidt s.inoue 2013/07/01
	private String kensanengapi = "";

	/**
	 * 登録ボタンを押した際の必須項目などを検証
	 */
	protected boolean validateAsRegisterPushed(JKojinRegisterFrameData data) {
		if (data.getHokenjyaNumber().equals(""))
		{
			JErrorMessage.show("M4352", null);
			return false;
		}
		if (data.getNameKana().equals("")) {
			JErrorMessage.show("M4355", null);
			return false;
		}
		if (data.getBirthday().equals("")) {
			JErrorMessage.show("M4356", null);
			return false;
		}
		if (data.getSex().equals("")) {
			JErrorMessage.show("M4357", null);
			return false;
		}
		if (data.getZipcode().equals("")) {
			JErrorMessage.show("M4370", null);
			return false;
		}
		if (data.getAddress().equals("")) {
			JErrorMessage.show("M4371", null);
			return false;
		}
		// edit n.ohkubo 2015/03/01　追加　start
		if (data.getHihokenjyaNumber().equals("")) {
			JErrorMessage.show("M4364", null);
			return false;
		}
		// edit n.ohkubo 2015/03/01　追加　end
		
		// edit n.ohkubo 2014/10/01　追加　start　保険者情報がDBへ登録されているか確認する
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append("HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL, KIGO, HON_GAIKYURATE, HON_NYUKYURATE, KZK_GAIKYURATE, KZK_NYUKYURATE, ITAKU_KBN, TANKA_KIHON, HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, TANKA_NINGENDOC, TANKA_HANTEI, HKNJYA_HISTORY_NO, HKNJYA_LIMITDATE_START, HKNJYA_LIMITDATE_END, YUKOU_FLG FROM T_HOKENJYA ");
			sb.append("WHERE ");
			sb.append("HKNJANUM = ? AND YUKOU_FLG = '1'");
			String[] params = {data.getHokenjyaNumber()};
			ArrayList<Hashtable<String, String>> result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString(), params);
			
			//DB未登録時はエラー
			if (result.size() == 0) {
				JErrorMessage.show("M3140", null);
				return false;
			}
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			logger.error(sqlEx.getMessage());
		}
		// edit n.ohkubo 2014/10/01　追加　end　保険者情報がDBへ登録されているか確認する

		data.setValidationAsDataSet();
		return true;
	}

	/**
	 * 保険者メンテナンス画面に遷移する際の必須項目などを検証
	 */
	protected boolean validateAsToHokenjaAddFrame(JKojinRegisterFrameData data) {
		if (data.getHokenjyaNumber().equals("")) {
			JErrorMessage.show("M4352", null);
			return false;
		}

		data.setValidationAsDataSet();
		return true;
	}

	/**
	 * ORCA呼び出しの際の必須項目の検証
	 */
	public boolean validateAsPushedORCAButton(JKojinRegisterFrameData data) {
		if (validatedData.getORCAID().isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * 支払代行機関マスタメンテナンスへ遷移する際の必須項目などを検証
	 */
	protected boolean validateAsToJShiharaiMasterMaintenanceEditFrame(
			JKojinRegisterFrameData data) {
		if (data.getDaikouNumber().equals("")) {
			JErrorMessage.show("M4358", null);
			return false;
		}
		data.setValidationAsDataSet();
		return true;
	}

	/**
	 * フレームの初期化処理
	 */
	public JKojinRegisterFrameCtrl() {
		super();

		// ４つの窓口負担関連コンボボックスの初期化
		for (int i = 0; i < JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS.length; i++) {
			String syubetsuItem = JKojinRegisterFrameData.MADOGUCHI_HUTAN_SYUBETSU_ITEMS[i][1];

			jComboBox_MadoguchiKihonSyubetu.addItem(syubetsuItem);
			jComboBox_MadoguchiShousaiSyubetu.addItem(syubetsuItem);
			jComboBox_MadoguchiTsuikaSyubetu.addItem(syubetsuItem);
			jComboBox_MadoguchiDockSyubetu.addItem(syubetsuItem);
		}

		validatedData.setMadoguchiDockSyubetu(jComboBox_MadoguchiDockSyubetu.getSelectedIndex());
		validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex());
		validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiShousaiSyubetu.getSelectedIndex());
		validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex());

		/* 保険者選択欄を初期化する。 */
		this.initializeHokenjaNumComboBox();
		jComboBox_ItakuKubun.addItem(itakuKubunStr[0]);
		jComboBox_ItakuKubun.addItem(itakuKubunStr[1]);

		/* 支払代行機関選択欄を初期化する。 */
		this.initializeDaikouNumberComboBox();

		/* コンポーネントの非活性化を行う */
		this.initializeDisableItem();

		/* コンポーネントの背景色を初期化する。 */
		this.initializeComponentBgColor();

		// edit s.inoue 2009/12/20
		initilazeFunctionSetting();

// del s.inoue 2011/12/13
//		/* フォーカスの遷移順を指定する。 */
//		this.initializeFocusTraversal();
//		// add s.inoue 2009/11/09
//		/* Fキー用イベント設定 */
//		functionListner();

		/* 性別の選択をクリア */
		this.groupSex.clearSelection();

		/* ORCA連携の設定に応じて「ORCA連携」ボタンの有効・無効を切り替える。 */
		if (!JApplication.useORCA) {
			jButton_ORCA.setEnabled(false);
		}

		/* T_KOJIN Dao を作成する。 */
		try {
			tKojinDao = (TKojinDao) DaoFactory.createDao(
					JApplication.kikanDatabase.getMConnection(), new TKojin());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		/* 保険者番号、支払代行機関番号入力欄に DocumentLister を追加する。 */
//		DocumentListener listener = this.getComboBoxDocumentListener(
//				this.jComboBox_HokenjyaNumber);
		TextFieldWithComboBoxDocumentListener hokenjaListener =
			new TextFieldWithComboBoxDocumentListener(
				this.jComboBox_HokenjyaNumber, 0);
		// edit s.inoue 2011/12/13
		this.jTextField_hokenjaNumber.getDocument().addDocumentListener(hokenjaListener);

		TextFieldWithComboBoxDocumentListener daikouListener =
			new TextFieldWithComboBoxDocumentListener(this.jComboBox_DaikouNumber, 1);
		this.jTextField_daikouNumber.getDocument().addDocumentListener(daikouListener);

		/* 患者ID入力欄にフォーカスを移動する。 */
		this.jTextField_ORCAID.grabFocus();

// del s.inoue 2012/06/27
//		jTextField_Birthday.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				String selectedItem = JValidate.validateSendSeinengapi(jTextField_Birthday.getDateText());
//				if ( selectedItem == null) {
//					jTextField_YearOld.setText("");
//					return;
//				}
//				jTextField_YearOld.setText(String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem)));
//			}
//		});

		// add s.inoue 2012/07/09
		jButton_ORCA.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				logger.info(jButton_ORCA.getText());
				// eidt s.inoue 2012/07/09
			if (KeyEvent.VK_ENTER != e.getKeyCode()) return;
				int mod = e.getModifiersEx();
				  if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
				    jTextField_ORCAID.grabFocus();
				  }else{
					  pushedORCAButton();
				  }
			}
		});
	}

	/* 個別設定用 */
	private void initilazeFunctionSetting(){
		ArrayList<Hashtable<String, String>> result = null;

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
			sb.append(" FROM T_SCREENFUNCTION ");
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert("001"));

			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> item = result.get(i);

			String functionCd = item.get("FUNCTION_CD");
			if (JApplication.func_yearOldCode.equals(functionCd)){
				yearOld_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}else if (JApplication.func_keiyakuCode.equals(functionCd)){
				keiyakuCode_flg = item.get("FUNCTION_FLG").equals("1")?true:false;
			}else if (JApplication.func_futanCode.equals(functionCd)){
				futanCode_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}else if (JApplication.func_nyuryokuCode.equals(functionCd)){
					nyuryokuCode_flg =  item.get("FUNCTION_FLG").equals("1")?true:false;
			}
		}

	}

	/**
	 * コンポーネントの非活性化を行う
	 */
	private void initializeDisableItem(){
		// del s.inoue 2009/10/15
//		jRadioButton_Kihon.setEnabled(false);
//		jRadioButton_Doc.setEnabled(false);
		jComboBox_ItakuKubun.setEnabled(false);
		// eidt s.inoue 2011/12/13
//		jTextField_KihonTanka.setEditable(false);
//		jTextField_HinketsuTanka.setEditable(false);
//		jTextField_SindenzuTanka.setEditable(false);
//		jTextField_GanteiTanka.setEditable(false);
//		jTextField_NingenDocTanka.setEditable(false);
		jTextField_KihonTanka.setEnabled(false);
		jTextField_HinketsuTanka.setEnabled(false);
		jTextField_SindenzuTanka.setEnabled(false);
		jTextField_GanteiTanka.setEnabled(false);
		jTextField_NingenDocTanka.setEnabled(false);

//		this.jButton_QR.setEnabled(false);
	}
	/**
	 * コンポーネントの背景色を設定する。
	 */
	private void initializeComponentBgColor() {
// del s.inoue 2012/05/10
//		Color requiedItemColor = ViewSettings.getRequiedItemBgColor();
//		Color importantItemColor = ViewSettings.getImportantItemBgColor();
//		Color disableItemColor = ViewSettings.getDisableItemBgColor();

//		jTextField_JyushinkenID.setBackground(importantItemColor);
//		jTextField_hokenjaNumber.setBackground(requiedItemColor);
//
//		jComboBox_HokenjyaNumber.setBackground(requiedItemColor);
//		jTextField_HihokenjyaCode.setBackground(importantItemColor);
//		jTextField_HihokenjyaNumber.setBackground(importantItemColor);

		// add s.inoue 2009/10/12
//		jRadioButton_Doc.setBackground(disableItemColor);
//		jTextField_NingenDocTanka.setBackground(disableItemColor);
//		jRadioButton_Kihon.setBackground(disableItemColor);
//		jTextField_KihonTanka.setBackground(disableItemColor);
//		jTextField_HinketsuTanka.setBackground(disableItemColor);
//		jTextField_SindenzuTanka.setBackground(disableItemColor);
//		jTextField_GanteiTanka.setBackground(disableItemColor);

//		jTextField_NameKana.setBackground(requiedItemColor);
//		jTextField_Birthday.setBackground(requiedItemColor);
//		jRadioButton_Male.setBackground(requiedItemColor);
//		jPanel1.setBackground(requiedItemColor);
//		jRadioButton_Female.setBackground(requiedItemColor);
//		jTextField_Address.setBackground(requiedItemColor);
//		jTextField_ZipCode.setBackground(requiedItemColor);
//		jTextField_sex.setBackground(requiedItemColor);
//		jLabel_requiredExample.setBackground(requiedItemColor);
//		jLabel_importantExample.setBackground(importantItemColor);
	}

// del s.inoue 2011/12/13
//	/**
//	 * フォーカスの遷移順を指定する。
//	 */
//	private void initializeFocusTraversal() {
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_ORCAID);
//
//		this.focusTraversalPolicy.addComponent(jTextField_ORCAID);
//		this.focusTraversalPolicy.addComponent(jTextField_JyushinkenID);
//		this.focusTraversalPolicy.addComponent(jTextField_NameKana);
//		this.focusTraversalPolicy.addComponent(jTextField_IssueDate);
//		this.focusTraversalPolicy.addComponent(jTextField_LimitDate);
//		this.focusTraversalPolicy.addComponent(this.jTextField_hokenjaNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_HokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(this.jTextField_daikouNumber);
//		this.focusTraversalPolicy.addComponent(this.jComboBox_DaikouNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_TorimatomeName);
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiKihonSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiKihon);
//		this.focusTraversalPolicy.addComponent(jTextField_KihonJougen);
//
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiShousaiSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiShousai);
//		this.focusTraversalPolicy.addComponent(jTextField_ShousaiJougen);
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiTsuikaSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiTsuika);
//		this.focusTraversalPolicy.addComponent(jTextField_TsuikaJyougen);
//		this.focusTraversalPolicy.addComponent(jComboBox_MadoguchiDockSyubetu);
//		this.focusTraversalPolicy.addComponent(jTextField_MadoguchiDock);
//		this.focusTraversalPolicy.addComponent(jTextField_DockJougen);
//		this.focusTraversalPolicy.addComponent(jTextField_sonotaHutangaku);
//
//		// add s.inoue 2009/10/11
////		this.focusTraversalPolicy.addComponent(jTextField_TankaHantei);
////		this.focusTraversalPolicy.addComponent(jComboBox_ItakuKubun);
////		this.focusTraversalPolicy.addComponent(jTextField_KihonTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_HinketsuTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_SindenzuTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_GanteiTanka);
////		this.focusTraversalPolicy.addComponent(jTextField_NingenDocTanka);
//
//		this.focusTraversalPolicy.addComponent(jTextField_ZipCode);
//		this.focusTraversalPolicy.addComponent(jTextField_Address);
//		this.focusTraversalPolicy.addComponent(jTextField_HomePhone);
//		this.focusTraversalPolicy.addComponent(jTextField_FAXNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_CellPhone);
//		this.focusTraversalPolicy.addComponent(jTextField_EMail);
//		this.focusTraversalPolicy.addComponent(jTextField_MobileMail);
//
//		this.focusTraversalPolicy.addComponent(jTextField_HihokenjyaCode);
//		this.focusTraversalPolicy.addComponent(jTextField_HihokenjyaNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_NameKanji);
//		this.focusTraversalPolicy.addComponent(jTextField_NameTsushou);
//		this.focusTraversalPolicy.addComponent(jTextField_Birthday);
//		this.focusTraversalPolicy.addComponent(jTextField_sex);
//
//		this.focusTraversalPolicy.addComponent(jButton_Register);
//		this.focusTraversalPolicy.addComponent(jButton_Print);
//		this.focusTraversalPolicy.addComponent(jButton_Call);
////		this.focusTraversalPolicy.addComponent(jButton_QR);
//		this.focusTraversalPolicy.addComponent(jButton_Clear);
//		this.focusTraversalPolicy.addComponent(jButton_End);
//	}
//
//	// add s.inoue 2009/11/09
//	private void functionListner(){
//
//		// add s.inoue 2009/12/02
//		Component comp =null;
//		HashMap<Integer, Component> compHm = new HashMap<Integer, Component>();
//
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			// System.out.println(comp.getName());
//
//			if (!compHm.containsValue(comp)){
//				comp.addKeyListener(this);
//				compHm.put(i, comp);
//			}
//		}
//
////		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
////			Component comp = focusTraversalPolicy.getComponent(i);
////			comp.addKeyListener(this);
////		}
//	}
	/**
	 * 保険者番号、名称の一覧を取得する。
	 */
	private ArrayList<Hashtable<String, String>> getHokenjaNumAndNames() {
		ArrayList<Hashtable<String, String>> result = null;
		// add s.inoue 2010/01/15
		String query = new String(
				"SELECT HKNJANUM,HKNJANAME FROM T_HOKENJYA WHERE YUKOU_FLG = '1' ORDER BY HKNJANUM");
		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 保険者番号コンボボックスの初期化
	 */
	private ArrayList<String> hokenjaNumbers = new ArrayList<String>();

	private void initializeHokenjaNumComboBox() {
		ArrayList<Hashtable<String, String>> result = this
				.getHokenjaNumAndNames();

		jComboBox_HokenjyaNumber.removeAllItems();
		hokenjaNumbers.clear();

		jComboBox_HokenjyaNumber.addItem(COMBOBOX_ITEM_ADD_NEWITEM);
		hokenjaNumbers.add("");

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> resultItem = result.get(i);
			String num = resultItem.get("HKNJANUM").trim();
			String name = resultItem.get("HKNJANAME").trim();

			StringBuffer buffer = new StringBuffer();
			buffer.append(num);
			buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);

			if (name != null && !name.isEmpty()) {
				buffer.append(name);
			} else {
				buffer.append(TEXT_NO_NAME);
			}

			hokenjaNumbers.add(num);
			jComboBox_HokenjyaNumber.addItem(buffer.toString());
		}
	}

	/**
	 * 支払代行機関コンボボックスを初期化する
	 */
	private ArrayList<String> daikouNumbers = new ArrayList<String>();

	private void initializeDaikouNumberComboBox() {
		ArrayList<Hashtable<String, String>> result = getDaikoNumAndNames();

		jComboBox_DaikouNumber.removeAllItems();
		daikouNumbers.clear();

		jComboBox_DaikouNumber.addItem("");
		daikouNumbers.add("");

		jComboBox_DaikouNumber.addItem(COMBOBOX_ITEM_ADD_NEWITEM);
		daikouNumbers.add("");

		for (int i = 0; i < result.size(); i++) {
			Hashtable<String, String> ResultItem = result.get(i);
			String num = ResultItem.get("SHIHARAI_DAIKO_NO").trim();
			String name = ResultItem.get("SHIHARAI_DAIKO_NAME").trim();

			StringBuffer buffer = new StringBuffer();
			buffer.append(num);
			buffer.append(COMBOBOX_NUMBER_NAME_SEPARATOR);

			if (name != null && !num.isEmpty()) {
				buffer.append(name);
			} else {
				buffer.append(TEXT_NO_NAME);
			}

			daikouNumbers.add(num);
			jComboBox_DaikouNumber.addItem(buffer.toString());
		}
	}

	private ArrayList<Hashtable<String, String>> getDaikoNumAndNames() {
		ArrayList<Hashtable<String, String>> result = null;

		String query = new String(
				"SELECT SHIHARAI_DAIKO_NO,SHIHARAI_DAIKO_NAME FROM T_SHIHARAI ORDER BY SHIHARAI_DAIKO_NO");
		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * フレームの初期化処理
	 */
	public JKojinRegisterFrameCtrl(JKenshinKekkaSearchListFrameData data) {
		this();
		isFromKekkaRegster = true;

		String uketukeId = data.getUKETUKE_ID();
		// eidt s.inoue 2013/07/01
		kensanengapi = data.getKENSA_NENGAPI();

		setKojinDataFromDB(uketukeId,kensanengapi);
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M4397", this);
		if (Ret == RETURN_VALUE.YES) {
			if (register()) {
				dispose();
			}
		}
	}

	/**
	 * キャンセルボタン
	 */
	public void pushedCancelButton() {
		RETURN_VALUE Ret = JErrorMessage.show("M4396", this);
		if (Ret == RETURN_VALUE.YES) {
			dispose();
		}
	}

	/**
	 * 登録処理を行う
	 *
	 * Modified 2008/04/20 若月
	 *   登録処理をメソッド化した。
	 */
	public void pushedRegisterButton(ActionEvent e) {
		this.register();

	}

	/**
	 * 登録処理を行なう。
	 */
	private boolean register() {
		boolean success = false;

		/* 入力値を検証する。 */
		if (! validateInputData()) {
			return false;
		}

		if (! validateAsRegisterPushed(validatedData)) {
			return false;
		}

		if (validatedData.isValidationAsDataSet()) {

			// add s.inoue 2011/07/21
			try {
				JApplication.kikanDatabase.Transaction();
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			}

			// eidt s.inoue 2011/07/19
			// 登録前処理
			if (!beforeRegister())return false;

			if(nyuryokuCode_flg){
//				pushedPrintButton(null);	// edit n.ohkubo 2015/08/01　削除
				pushedPrintButton(true);	// edit n.ohkubo 2015/08/01　追加
			}

			String query = this.createRegisterSql();
			if (query != null) {
				// System.out.println("@@ " + query);

				try {
					JApplication.kikanDatabase.sendNoResultQuery(query);
					this.pushedClearButton();

					success = true;

				} catch (SQLException e) {
					logger.error(e.getMessage());

					try {
						JApplication.kikanDatabase.rollback();
					} catch (SQLException g) {
						logger.error(g.getMessage());
					}
				}
			}

			// add s.inoue 2011/07/21
			try {
				JApplication.kikanDatabase.Commit();
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
			}

		}
		return success;
	}

	// eidt s.inoue 2011/07/19
	/**
	 * 受診券情報登録前処理を実施
	 */
	private boolean beforeRegister(){

		boolean registered = true;

		try {
			// edit s.inoue 2009/09/30
			if (mode == ADD_MODE ||
					((mode == CHANGE_MODE) &&
				    (!validatedData.getJyushinkenID().equals(validatedData.getpreJyushinkenID())))){
						Long resultCnt = tKojinDao.selectByCountUniqueKey(validatedData.getJyushinkenID());
						if (resultCnt > 0) {
							JErrorMessage.show("M4348", this);
						}
			}
		}catch (Exception ex){
			registered = false;
			JErrorMessage.show("M4349", this);
			logger.warn(ex.getMessage());
		}

		if (mode == ADD_MODE) {

			try {

				long uketukeId = -1L;

				uketukeId = tKojinDao.selectNewUketukeId(1);

				// 受付IDの保存
				if (!validatedData.getUketukeID().equals("")){
					validatedData.setpreUketukeID(validatedData.getUketukeID());

					registerNayose(uketukeId);
				}

				// 新規登録用に受付IDにセットする
				validatedData.setUketukeID(new Long(uketukeId).toString());

			} catch (Exception e) {
				registered = false;
				JErrorMessage.show("M4349", this);
				logger.error(e.getMessage());
			}
		}
		return registered;
	}

//	/**
//	 * 履歴テーブルへ登録処理を実施する。
//	 */
//	private void registerKojinHistory(Long newUketukeId){
//		// 履歴テーブル登録処理(元データは、個人テーブルより取得)
//		historyQuery = new StringBuilder();
//		historyQuery.append("INSERT INTO T_KOJIN_HISTORY ( NAYOSE_NO, PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU,");
//		historyQuery.append("HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX,HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, ");
//		historyQuery.append("FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON,");
//		historyQuery.append("MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU, MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC,");
//		historyQuery.append("NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC)");
//		historyQuery.append(" SELECT ");
//		historyQuery.append(JQueryConvert.queryConvert(String.valueOf(nayoseNo)) + " NAYOSE_NO,");
//		historyQuery.append(" PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, HIHOKENJYASYO_NO, NAME, KANANAME,");
//		historyQuery.append(" NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI, HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME,");
//		historyQuery.append(" KOUHUBI, SIHARAI_DAIKOU_BANGO, MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU,");
//		historyQuery.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA, HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
//		historyQuery.append(" FROM T_KOJIN WHERE UKETUKE_ID = ");
//		historyQuery.append(JQueryConvert.queryConvert(validatedData.getUketukeID()));
//
//		try {
//			JApplication.kikanDatabase.sendNoResultQuery(historyQuery.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//
//			try {
//				JApplication.kikanDatabase.rollback();
//			} catch (SQLException g) {
//				logger.error(g.getMessage());
//			}
//		}
//	}

	/**
	 * 名寄せテーブルへ登録処理を実施する。
	 */
	private void registerNayose(Long newUketukeId){

		StringBuilder nayoseQuery = null;
		StringBuilder newNayoseQuery = null;

		/* INSERT 文を生成する。 */
		if (mode == ADD_MODE) {

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

				retTNayoseNo = tNayoseDao.selectOldNayoseNo(Long.parseLong(validatedData.getUketukeID()));
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
					nayoseQuery.append(JQueryConvert.queryConvertAppendComma(validatedData.getUketukeID()));
					nayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
					nayoseQuery.append(") ");

					try {
						JApplication.kikanDatabase.sendNoResultQuery(nayoseQuery.toString());
					} catch (SQLException e) {
						logger.error(e.getMessage());

						try {
							// eidt s.inoue 2013/02/20
							// JApplication.kikanDatabase.rollback();
							JApplication.kikanDatabase.getMConnection().rollback();
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
				newNayoseQuery.append(JQueryConvert.queryConvertAppendComma(newUketukeId.toString()));
				newNayoseQuery.append(JQueryConvert.queryConvert(stringTimeStamp));
				newNayoseQuery.append(") ");

				try {
					JApplication.kikanDatabase.sendNoResultQuery(newNayoseQuery.toString());
				} catch (SQLException e) {
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
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}
	}

	/**
	 * 受診券情報登録用 SQL を作成する。
	 */
	private String createRegisterSql() {

		StringBuffer query = null;
		/* INSERT 文を生成する。 */
		if (mode == ADD_MODE) {

			query = new StringBuffer();
			query.append(" INSERT INTO T_KOJIN (");

			// edit s.inoue 20110328
			// T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();
			T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();

			int length = columns.length;

			for (int i = 0; i < length; i++) {
				String columnName = JKojinRegisterFrameData.getColumnName(columns[i]);
				query.append(columnName);

				if (i != length - 1) {
					query.append(",");
				}
			}

			query.append(" )");
			query.append(" VALUES ( ");

			String value = null;
			for (int i = 0; i < length; i++) {
				value = validatedData.get(columns[i]);

				if (i != length - 1) {
					query.append(getAppendCommaString(value));
				}
				else {
					query.append(JQueryConvert.queryConvert(value));
				}
			}

			query.append(" ) ");

		} else if (mode == CHANGE_MODE) {

			/* UPDATE文を生成する。 */
			StringBuffer buffer = new StringBuffer();

			buffer.append(" WHERE ");
			buffer.append(" UKETUKE_ID = ");
			// edit ver2 s.inoue 2009/05/29
			//buffer.append(JQueryConvert.queryConvert(validatedData.getpreUketukeID()));
			buffer.append(JQueryConvert.queryConvert(validatedData.getUketukeID()));

			String condition = buffer.toString();

			query = new StringBuffer();
			query.append("UPDATE T_KOJIN SET ");

			T_KOJIN_COLUMN[] columns = JKojinRegisterFrameData.T_KOJIN_COLUMN.values();
			int length = columns.length;

			String value = null;
			for (int i = 0; i < length; i++) {

				String columnName = JKojinRegisterFrameData.getColumnName(columns[i]);
				query.append(columnName);
				query.append(" = ");

				value = validatedData.get(columns[i]);
				if (i != length - 1) {
					query.append(getAppendCommaString(value));
				}
				else {
					query.append(JQueryConvert.queryConvert(value));
				}
			}

			query.append(condition);
		}

		return query.toString();
	}

	private String getAppendCommaString(String text) {
		return JQueryConvert.queryConvertAppendComma(text);
	}

	/**
	 * 入力データを検証する。
	 *
	 * Modified 2008/06/02 若月
	 * コンボボックス、ラジオボタンの文字列による検証を廃止する。
	 */
	private boolean validateInputData() {

		String hokenjaNumber = this.jTextField_hokenjaNumber.getText();
		String daikouNumber = this.jTextField_daikouNumber.getText();

		String address = jTextField_Address.getText();
		if (!address.equals(""))
			address = JValidate.encodeUNICODEtoConvert(address);
		boolean isValid =
				/* 住所 */
				// edit s.inoue 2009/10/21
				// validatedData.setAddress(jTextField_Address.getText())
				validatedData.setAddress(address,false)

				/* 生年月日 */
				// eidt s.inoue 2012/06/27
				&& validatedData.setBirthday(jTextField_Birthday.getText(),false)
				// && validatedData.setBirthday(jTextField_Birthday.getDateText(),false)

				/* 携帯電話番号 */
				&& validatedData.setCellPhone(jTextField_CellPhone.getText())
				/* 支払代行機関番号 */
				&& validatedData.setDaikouNumber(daikouNumber)
				/* E-Mail */
				&& validatedData.setEMail(jTextField_EMail.getText())
				/* FAX */
				&& validatedData.setFaxNumber(jTextField_FAXNumber.getText())
				/* 被保険者証等記号 */
				&& validatedData.setHihokenjyaCode(jTextField_HihokenjyaCode
						.getText(),false)
				/* 被保険者証等番号 */
				&& validatedData.setHihokenjyaNumber(jTextField_HihokenjyaNumber
								.getText(),false)
				/* 保険者番号 */
				&& validatedData.setHokenjyaNumber(hokenjaNumber,false)
				/* 電話番号 */
				&& validatedData.setHomePhone(jTextField_HomePhone.getText())
				/* 交付日 */
				&& validatedData.setIssueDate(jTextField_IssueDate.getDateText())
				/* 受診券整理番号 */
				&& validatedData.setJyushinkenID(jTextField_JyushinkenID.getText(),false)
				/* 有効期限 */
				&& validatedData.setLimitDate(jTextField_LimitDate.getDateText(),false)
				/* 携帯電話番号 */
				&& validatedData.setMobileMail(jTextField_MobileMail.getText())
				/* 氏名（カナ） */
				&& validatedData.setNameKana(jTextField_NameKana.getText(),false)
				/* 氏名 */
				&& validatedData.setNameKanji(jTextField_NameKanji.getText())
				/* 氏名（通称） */
				&& validatedData.setNameTsusyou(jTextField_NameTsushou.getText())
				/* 患者番号 */
				&& validatedData.setORCAID(jTextField_ORCAID.getText())
				/* 性別 */
				&& validatedData.setSex(jTextField_sex.getText(),false)
				/* 契約取りまとめ機関名 */
				&& validatedData.setTorimatomeName(jTextField_TorimatomeName.getText())
				/* 郵便番号 */
				&& validatedData.setZipcode(jTextField_ZipCode.getText(),false)

				/* 窓口負担（基本的な健診） */
				&& validatedData.setMadoguchiKihonSyubetu(jComboBox_MadoguchiKihonSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiKihon(jTextField_MadoguchiKihon.getText(),false,false)

				/* 窓口負担（詳細な健診） */
				&& validatedData.setMadoguchiSyousaiSyubetu(jComboBox_MadoguchiShousaiSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiSyousai(jTextField_MadoguchiShousai.getText(),false,false)

				/* 窓口負担（追加の健診） */
				&& validatedData.setMadoguchiTsuikaSyubetu(jComboBox_MadoguchiTsuikaSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiTsuika(jTextField_MadoguchiTsuika.getText(),false,false)

				/* 窓口負担（人間ドック） */
				&& validatedData.setMadoguchiDockSyubetu(jComboBox_MadoguchiDockSyubetu.getSelectedIndex())
				&& validatedData.setMadoguchiDock(jTextField_MadoguchiDock.getText(),false,false)

				/* 保険者負担額上限 */
				&& validatedData.setHokenjyaJougenKihon(jTextField_KihonJougen.getText(),false)
				&& validatedData.setHokenjyaJougenSyousai(jTextField_ShousaiJougen.getText(),false)
				&& validatedData.setHokenjyaJougenTsuika(jTextField_TsuikaJyougen.getText(),false)
				&& validatedData.setHokenjyaJougenDoc(jTextField_DockJougen.getText(),false)

				/* その他の健診の負担額 */
				&& validatedData.setMadoguchiSonota(jTextField_sonotaHutangaku.getText(),false)

				/* 受付ID */
				&& validatedData.setUketukeID(jTextField_Uketsukeid.getText())

				/* 年度 */
				&& validatedData.setNendo(jTextField_Nendo.getText());

		return isValid;
	}

	public boolean validateAsCallPushed(JKojinRegisterFrameData data) {
		if (data.getJyushinkenID().equals("")) {
			return false;
		}

		return true;
	}

	public void pushedCallButton(ActionEvent e) {
		// edit ver2 s.inoue 2009/08/18
		// 処理共通化の為、移動
		setKojinDataFromSeiriNo();
	}

	/**
	 * 受診者情報(更新処理時の画面制御)
	 */
	private void presetKojinData(Hashtable<String, String> resultItem, String kensanengapi) {
		// del ver2 s.inoue 2009/05/07 更新処理の時
		// jTextField_JyushinkenID.setEditable(false);
		jTextField_JyushinkenID.setText(resultItem.get("JYUSHIN_SEIRI_NO"));
		mode = CHANGE_MODE;

		setKojinData(resultItem,kensanengapi);
	}

	/**
	 * 受診者情報を画面上に表示する。
	 */
	private void setKojinData(Hashtable<String, String> resultItem,String kensanengapi) {

		jTextField_Uketsukeid.setText(resultItem.get("UKETUKE_ID"));
		jTextField_Nendo.setText(resultItem.get("NENDO"));
		jTextField_HihokenjyaCode.setText(resultItem.get("HIHOKENJYASYO_KIGOU"));
		jTextField_HihokenjyaNumber.setText(resultItem.get("HIHOKENJYASYO_NO"));
		jTextField_ORCAID.setText(resultItem.get("PTNUM").trim());

		// add s.inoue 2012/03/05
		// jTextField_IssueDate.setText(resultItem.get("KOUHUBI"));
		try {
			Date dt = jTextField_IssueDate.getTextToDate(resultItem.get("KOUHUBI"));
			jTextField_IssueDate.setDate(dt);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}

		// add s.inoue 2012/03/05
		// jTextField_LimitDate.setText(resultItem.get("YUKOU_KIGEN"));
		try {
			Date dt = jTextField_LimitDate.getTextToDate(resultItem.get("YUKOU_KIGEN"));
			jTextField_LimitDate.setDate(dt);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}


		String hokenjaNumber = resultItem.get("HKNJANUM");
		if (hokenjaNumber != null) {
			hokenjaNumber = hokenjaNumber.trim();
			int hokenjaNumberIndex = this.hokenjaNumbers.indexOf(hokenjaNumber);

			if (hokenjaNumberIndex >= 0) {
				jComboBox_HokenjyaNumber.setSelectedIndex(hokenjaNumberIndex);
				jTextField_hokenjaNumber.setText(hokenjaNumber);
			}
		}

		String daikouBongo = resultItem.get("SIHARAI_DAIKOU_BANGO");
		if (daikouBongo != null) {
			daikouBongo = daikouBongo.trim();
			int daikouIndex = this.daikouNumbers.indexOf(daikouBongo);
			if (daikouIndex >= 0) {
				this.jComboBox_DaikouNumber.setSelectedIndex(daikouIndex);
				jTextField_daikouNumber.setText(daikouBongo);
			}
		}

		// add s.inoue 2009/10/11
		String itakuKbn = resultItem.get("ITAKU_KBN");
		String tankaKihon = resultItem.get("TANKA_KIHON");
		String tankaHinketu = resultItem.get("TANKA_HINKETU");
		String tankaSindenzu = resultItem.get("TANKA_SINDENZU");
		String tankaGantei = resultItem.get("TANKA_GANTEI");
		String tankaDoc = resultItem.get("TANKA_NINGENDOC");
		String hantei = resultItem.get("TANKA_HANTEI");

		// add s.inoue 2009/10/12
		if (itakuKbn != null) {
			jComboBox_ItakuKubun.setSelectedIndex(Integer.valueOf(itakuKbn) - 1);
		}

		// add s.inoue 2009/10/12
		if (hantei != null) {
			if (hantei.equals(TANKA_HANTEI_DOC)) {
// del s.inoue 2009/10/15
//				jRadioButton_Doc.setSelected(true);
				jTextField_NingenDocTanka.setText(tankaDoc);
			}else{
// del s.inoue 2009/10/15
//				jRadioButton_Kihon.setSelected(true);
				jTextField_KihonTanka.setText(tankaKihon);
				jTextField_HinketsuTanka.setText(tankaHinketu);
				jTextField_SindenzuTanka.setText(tankaSindenzu);
				jTextField_GanteiTanka.setText(tankaGantei);
			}
		}

		jTextField_TorimatomeName.setText(resultItem.get("KEIYAKU_TORIMATOME"));
		jTextField_NameKana.setText(resultItem.get("KANANAME"));
		jTextField_NameKanji.setText(resultItem.get("NAME"));
		jTextField_NameTsushou.setText(resultItem.get("NICKNAME"));

		// eidt s.inoue 2012/06/27
		jTextField_Birthday.setText(resultItem.get("BIRTHDAY"));
//		try {
//			Date dt = jTextField_Birthday.getTextToDate(resultItem.get("BIRTHDAY"));
//			jTextField_Birthday.setDate(dt);
//		} catch (ParseException e) {
//			logger.error(e.getMessage());
//		}

		// eidt s.inoue 2012/03/06
		// jTextField_ZipCode.setText(resultItem.get("HOME_POST"));
		jTextField_ZipCode.setPostCodeFormatText(resultItem.get("HOME_POST"));

		jTextField_Address.setText(resultItem.get("HOME_ADRS"));
		jTextField_HomePhone.setText(resultItem.get("HOME_TEL1"));
		jTextField_CellPhone.setText(resultItem.get("KEITAI_TEL"));
		jTextField_FAXNumber.setText(resultItem.get("FAX"));

		// eidt s.inoue 2012/06/27
		String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getText());
		// String selectedItem = JValidate.validateSendSeinengapi(resultItem.get("BIRTHDAY"));

		if ( selectedItem == null) {
			jTextField_YearOld.setText("");
		}
		// move s.inoue 2011/08/03
//		if (yearOld_flg){
//			selectedItem = String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem));
//		}else{
//			selectedItem = JYearAge.getAge(selectedItem);
//		}
		int age =0;
		if (yearOld_flg){
			if (!kensanengapi.equals("")){
				age = FiscalYearUtil.getFiscalYear(selectedItem,kensanengapi);
			}else{
				age = FiscalYearUtil.getFiscalYear(selectedItem);
			}
		}else{
			if (!kensanengapi.equals("")){
				age = Integer.parseInt(JYearAge.getAge(selectedItem,kensanengapi));
			}else{
				age = Integer.parseInt(JYearAge.getAge(selectedItem));
			}
		}
		System.out.println("age:" + age);

		// eidt s.inoue 2011/08/03
		jTextField_YearOld.setText(String.valueOf(age));

		this.initMadoFutan(
				jComboBox_MadoguchiKihonSyubetu,
				jTextField_MadoguchiKihon,
				resultItem.get("MADO_FUTAN_K_SYUBETU"),
				resultItem.get("MADO_FUTAN_KIHON"));

		this.initMadoFutan(
				jComboBox_MadoguchiShousaiSyubetu,
				jTextField_MadoguchiShousai,
				resultItem.get("MADO_FUTAN_S_SYUBETU"),
				resultItem.get("MADO_FUTAN_SYOUSAI"));

		this.initMadoFutan(
				jComboBox_MadoguchiTsuikaSyubetu,
				jTextField_MadoguchiTsuika,
				resultItem.get("MADO_FUTAN_T_SYUBETU"),
				resultItem.get("MADO_FUTAN_TSUIKA"));

		this.initMadoFutan(
				jComboBox_MadoguchiDockSyubetu,
				jTextField_MadoguchiDock,
				resultItem.get("MADO_FUTAN_D_SYUBETU"),
				resultItem.get("MADO_FUTAN_DOC"));

		this.jTextField_sonotaHutangaku.setText(resultItem.get("MADO_FUTAN_SONOTA"));

		jTextField_EMail.setText(resultItem.get("EMAIL"));
		jTextField_MobileMail.setText(resultItem.get("KEITAI_EMAIL"));

		if (resultItem.get("SEX").equals("1")) {
			jRadioButton_Male.setSelected(true);
		} else {
			jRadioButton_Female.setSelected(true);
		}

		String hokenjyaFutanKihon = resultItem.get("HOKENJYA_FUTAN_KIHON");
		String hokenjyaFutanSyousai = resultItem.get("HOKENJYA_FUTAN_SYOUSAI");
		String hokenjyaFutanTsuika = resultItem.get("HOKENJYA_FUTAN_TSUIKA");
		String hokenjyaFutanDoc = resultItem.get("HOKENJYA_FUTAN_DOC");

		if (hokenjyaFutanKihon != null && ! hokenjyaFutanKihon.isEmpty()) {
			hokenjyaFutanKihon = hokenjyaFutanKihon.replaceAll("^0+", "");
			jTextField_KihonJougen.setText(hokenjyaFutanKihon);
		}
		if (hokenjyaFutanSyousai != null && ! hokenjyaFutanSyousai.isEmpty()) {
			hokenjyaFutanSyousai = hokenjyaFutanSyousai.replaceAll("^0+", "");
			jTextField_ShousaiJougen.setText(hokenjyaFutanSyousai);
		}
		if (hokenjyaFutanTsuika != null && ! hokenjyaFutanTsuika.isEmpty()) {
			hokenjyaFutanTsuika = hokenjyaFutanTsuika.replaceAll("^0+", "");
			jTextField_TsuikaJyougen.setText(hokenjyaFutanTsuika);
		}
		if (hokenjyaFutanDoc != null && ! hokenjyaFutanDoc.isEmpty()) {
			hokenjyaFutanDoc = hokenjyaFutanDoc.replaceAll("^0+", "");
			jTextField_DockJougen.setText(hokenjyaFutanDoc);
		}

		// edit s.inoue 2009/09/30
		validatedData.setpreJyushinkenID(resultItem.get("JYUSHIN_SEIRI_NO"),false);
	}

	private void initMadoFutan(
			ExtendedComboBox comboBox,
			ExtendedOpenTextControl textField,
			String madoFutanSyubetsuValue,
			String madoFutanValueValue) {

		int index = 0;
		if (! madoFutanSyubetsuValue.equals("")) {
			index = Integer.valueOf(madoFutanSyubetsuValue);

			comboBox.setSelectedIndex(index);
			if (index == 3) {
				String percentage = JValidate.to3DigitCode(madoFutanValueValue);

				textField.setText(percentage.replaceAll("^0+", ""));
			} else {
				textField.setText(madoFutanValueValue.replaceAll("^0+", ""));
			}
		}
	}

	/**
	 * データベースから読み込んだ受診者情報を画面上に表示する。
	 *
	 * @param uketukeID
	 *            受付ID
	 */
	public void setKojinDataFromDB(String uketukeId,String kensanengapi) {

		if (!validatedData.setJyushinkenID(jTextField_JyushinkenID.getText(),false))
			return;

		ArrayList<Hashtable<String, String>> result = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT ");
		sb.append("KOJIN.PTNUM, KOJIN.JYUSHIN_SEIRI_NO, KOJIN.YUKOU_KIGEN, KOJIN.HKNJANUM, KOJIN.HIHOKENJYASYO_KIGOU, KOJIN.HIHOKENJYASYO_NO,");
		sb.append("KOJIN.NAME, KOJIN.KANANAME, KOJIN.NICKNAME, KOJIN.BIRTHDAY, KOJIN.SEX, KOJIN.HOME_POST, KOJIN.HOME_ADRS, KOJIN.HOME_BANTI,");
		sb.append("KOJIN.HOME_TEL1, KOJIN.KEITAI_TEL, KOJIN.FAX, KOJIN.EMAIL, KOJIN.KEITAI_EMAIL, KOJIN.KEIYAKU_TORIMATOME, KOJIN.KOUHUBI,");
		sb.append("KOJIN.SIHARAI_DAIKOU_BANGO, KOJIN.MADO_FUTAN_K_SYUBETU, KOJIN.MADO_FUTAN_KIHON, KOJIN.MADO_FUTAN_S_SYUBETU,");
		sb.append("KOJIN.MADO_FUTAN_SYOUSAI, KOJIN.MADO_FUTAN_T_SYUBETU, KOJIN.MADO_FUTAN_TSUIKA, KOJIN.MADO_FUTAN_D_SYUBETU,");
		sb.append("KOJIN.MADO_FUTAN_DOC, KOJIN.NENDO, KOJIN.UKETUKE_ID, KOJIN.MADO_FUTAN_SONOTA, KOJIN.HOKENJYA_FUTAN_KIHON,");
		sb.append("KOJIN.HOKENJYA_FUTAN_SYOUSAI, KOJIN.HOKENJYA_FUTAN_TSUIKA, KOJIN.HOKENJYA_FUTAN_DOC,");

		sb.append("HOKENJYA.ITAKU_KBN, HOKENJYA.TANKA_KIHON, HOKENJYA.HINKETU_CD, HOKENJYA.TANKA_HINKETU, HOKENJYA.SINDENZU_CD,");
		sb.append("HOKENJYA.TANKA_SINDENZU, HOKENJYA.GANTEI_CD, HOKENJYA.TANKA_GANTEI, HOKENJYA.TANKA_NINGENDOC, HOKENJYA.TANKA_HANTEI");
		sb.append(" FROM T_KOJIN AS KOJIN,T_HOKENJYA HOKENJYA ");
		sb.append(" WHERE KOJIN.HKNJANUM = HOKENJYA.HKNJANUM ");
		// add s.inoue 2010/01/15
		sb.append(" AND HOKENJYA.YUKOU_FLG = '1' ");
		sb.append(" AND UKETUKE_ID = ");
		sb.append(JQueryConvert.queryConvert(uketukeId));

		// edit s.inoue 2009/10/11
//		String query = "SELECT * FROM T_KOJIN WHERE UKETUKE_ID = " +
//			JQueryConvert.queryConvert(uketukeId);

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		if (result.isEmpty()) {
			JErrorMessage.show("M4383", this);
		} else {
			/* 受診者情報を画面上に表示する。 */
			this.presetKojinData(result.get(0),kensanengapi);
		}
	}

	/**
	 * データベースから氏名カナをキー検索する
	 * 結果が複数の場合⇒リスト化して表示
	 * 結果が単数の場合⇒受診券情報に対象のデータをセットする
	 * @param kanaName カナ氏名
	 */
	public void setKojinDataFromShimeiKana(String kanaName) {

		if (!validatedData.setNameKana(kanaName,false))
			return;
//		// →1件のみ 完全一致
//		// →複数件 曖昧検索
//		ArrayList<Hashtable<String, String>> resultKanaName = null;
//
//		// edit ver2 s.inoue 2009/08/21
//		String Query = "SELECT PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, " +
//			"HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI," +
//			" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO," +
//			" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU," +
//			" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA," +
//			" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC" +
//			" FROM T_KOJIN WHERE KANANAME CONTAINING " + JQueryConvert.queryConvert(kanaName);
//
//		try {
//			resultKanaName = JApplication.kikanDatabase.sendExecuteQuery(Query);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//		}
//		// 処理共通化の為、移動
//		kojinDataList(resultKanaName,FLAME_TITLE_NAME_MESSAGE);

		// edit s.inoue 2009/09/29
		// 件数取得:曖昧検索で
		StringBuilder kanaQuery = new StringBuilder();
		StringBuilder kanaWhereQuery = new StringBuilder();
		StringBuilder kanaWhereElseQuery = new StringBuilder();

		ArrayList<Hashtable<String, String>> resultKanaName = null;
		ArrayList<Hashtable<String, String>> resultKanaNameElse = null;

		kanaQuery.append("SELECT NAYOSE_NO,PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, ");
		kanaQuery.append("HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI,");
		kanaQuery.append(" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO,");
		kanaQuery.append(" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU,");
		kanaQuery.append(" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, T_KOJIN.UKETUKE_ID, MADO_FUTAN_SONOTA,");
		kanaQuery.append(" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC");
		// edit s.inoue 2010/11/25
		kanaQuery.append(" FROM T_KOJIN LEFT JOIN T_NAYOSE ON  ");
		kanaQuery.append(" T_KOJIN.UKETUKE_ID = T_NAYOSE.UKETUKE_ID");
		kanaQuery.append(" WHERE KANANAME ");
		kanaWhereQuery.append(" = ");
		kanaWhereQuery.append(JQueryConvert.queryConvert(kanaName));

		try {
			resultKanaName = JApplication.kikanDatabase.sendExecuteQuery(
				kanaQuery.toString() + kanaWhereQuery.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		if (resultKanaName.size() > 0){
			// 完全一致で、1件以上ある場合
			RETURN_VALUE Ret = JErrorMessage.show("M4399", this);

			if(Ret == RETURN_VALUE.YES){
				kojinDataList(resultKanaName,FLAME_TITLE_NAME_MESSAGE);
			}
		}else{
			// 完全一致で、1件も無い場合、曖昧検索
			// edit s.inoue 2009/10/27
			// kanaWhereElseQuery.append(" CONTAINING ");
			kanaWhereElseQuery.append(" STARTING WITH ");
			kanaWhereElseQuery.append("'" + kanaName+ "'");

			try {
				resultKanaNameElse = JApplication.kikanDatabase.sendExecuteQuery(
						kanaQuery.toString() + kanaWhereElseQuery.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

			if (resultKanaNameElse.size() > 0){
				RETURN_VALUE Ret = JErrorMessage.show("M4400", this);
				if(Ret == RETURN_VALUE.YES){
					kojinDataList(resultKanaNameElse,FLAME_TITLE_NAME_MESSAGE);
				}
			}
		}
	}

	/**
	 * データベースから受診券整理番号をキー検索する
	 * 結果が複数の場合⇒リスト化して表示
	 * 結果が単数の場合⇒受診券情報に対象のデータをセットする
	 * @param kanaName カナ氏名
	 */
	public void setKojinDataFromSeiriNo() {

		if (validatedData.setJyushinkenID(jTextField_JyushinkenID.getText(),false)) {
			if (validateAsCallPushed(validatedData)) {
				ArrayList<Hashtable<String, String>> resultKanaName = null;

				String Query = "SELECT PTNUM, JYUSHIN_SEIRI_NO, YUKOU_KIGEN, HKNJANUM, HIHOKENJYASYO_KIGOU, " +
					"HIHOKENJYASYO_NO, NAME, KANANAME, NICKNAME, BIRTHDAY, SEX, HOME_POST, HOME_ADRS, HOME_BANTI," +
					" HOME_TEL1, KEITAI_TEL, FAX, EMAIL, KEITAI_EMAIL, KEIYAKU_TORIMATOME, KOUHUBI, SIHARAI_DAIKOU_BANGO," +
					" MADO_FUTAN_K_SYUBETU, MADO_FUTAN_KIHON, MADO_FUTAN_S_SYUBETU, MADO_FUTAN_SYOUSAI, MADO_FUTAN_T_SYUBETU," +
					" MADO_FUTAN_TSUIKA, MADO_FUTAN_D_SYUBETU, MADO_FUTAN_DOC, NENDO, UKETUKE_ID, MADO_FUTAN_SONOTA," +
					" HOKENJYA_FUTAN_KIHON, HOKENJYA_FUTAN_SYOUSAI, HOKENJYA_FUTAN_TSUIKA, HOKENJYA_FUTAN_DOC"
					+" FROM T_KOJIN WHERE JYUSHIN_SEIRI_NO = " + JQueryConvert.queryConvert(validatedData.getJyushinkenID());

				try {
					resultKanaName = JApplication.kikanDatabase.sendExecuteQuery(Query);
				} catch (SQLException ex) {
					logger.error(ex.getMessage());
				}

				if (resultKanaName.isEmpty()) {
					JErrorMessage.show("M4383", this);
				} else {
					// edit ver2 s.inoue 2009/08/18
					// 処理共通化の為、移動
					kojinDataList(resultKanaName,FLAME_TITLE_JYUSINNO_MESSAGE);
					/* 受診者情報を画面上に表示する。 */
					// this.presetKojinData(resultKanaName.get(0));
				}
			}
		}
	}

	// add ver2 s.inoue 2009/08/18
	/**
	 * 個人リスト表示処理
	 */
	public void kojinDataList( ArrayList<Hashtable<String, String>> resultKanaName,String title){

		Hashtable<String, String> targetItem = null;

		// DB取得データからリスト表示用アイテムへ格納
		Vector<JSelectKojinFrameData> sameKojinData
			= new Vector<JSelectKojinFrameData>();

		for(int j = 0 ; j < resultKanaName.size() ; j++)
		{
			Hashtable<String,String> DatabaseItem = resultKanaName.get(j);

			//半角カタカナ、空白を除去した状態で比較
			// del s.inoue 2009/08/18 修正済みの残骸 名前はそのまま表示
			// String Name = JEraseSpace.EraceSpace(DatabaseItem.get("KANANAME"));
			JSelectKojinFrameData DataSame = new JSelectKojinFrameData();

			DataSame.uketukeNo = DatabaseItem.get("UKETUKE_ID");
			// edit s.inoue 2010/11/25
			DataSame.nayoseNo = DatabaseItem.get("NAYOSE_NO");
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
			sameKojinData.add(DataSame);
			targetItem = DatabaseItem;
		}

		// edit s.inoue 2009/09/29
		// 2件以上→1件も含める
		// 同姓同名・同一生年月日の受診者が複数いた場合の処理
		if(sameKojinData.size() > 0)
		{
			JSelectKojinFrameCtrl SelectKojinFrame
				= new JSelectKojinFrameCtrl(sameKojinData,this,title);
			JSelectKojinFrameData selectedData = SelectKojinFrame.GetSelectedData();

			if(selectedData == null){
				targetItem=null;
			}else{
				targetItem = selectedData.DatabaseItem;
			}
		}
		// edit ver2 s.inoue 2009/06/19
		if (targetItem != null) {
			/* 受診者情報を画面上に表示する。 */
			this.setKojinData(targetItem,"");
		}
	}

	// add ver2 s.inoue 2009/08/18
	/*
	 * ボタンアクション
	 */
	public void pushedClearButton() {
		jTextField_Address.setText("");
		// eidt s.inoue 2012/06/27
		jTextField_Birthday.setText("");
		// jTextField_Birthday.setDate(null);

		jTextField_CellPhone.setText("");
		jTextField_EMail.setText("");
		jTextField_FAXNumber.setText("");
		jTextField_HihokenjyaCode.setText("");
		jTextField_HihokenjyaNumber.setText("");

		// add s.inoue 2009/12/20
		// 契約情報
		if (!keiyakuCode_flg){
			jTextField_hokenjaNumber.setText("");
			jComboBox_HokenjyaNumber.setSelectedIndex(0);
			jTextField_daikouNumber.setText("");
			jComboBox_DaikouNumber.setSelectedItem("");
			jComboBox_DaikouNumber.setSelectedIndex(0);
			jTextField_TorimatomeName.setText("");
		}

		// 負担情報
		if (!futanCode_flg){
			jTextField_MadoguchiDock.setText("");
			jTextField_MadoguchiKihon.setText("");
			jTextField_MadoguchiShousai.setText("");
			jTextField_MadoguchiTsuika.setText("");
			this.jComboBox_MadoguchiKihonSyubetu.setSelectedIndex(0);
			this.jComboBox_MadoguchiShousaiSyubetu.setSelectedIndex(0);
			this.jComboBox_MadoguchiTsuikaSyubetu.setSelectedIndex(0);
			this.jComboBox_MadoguchiDockSyubetu.setSelectedIndex(0);
			this.jTextField_KihonSyubetsuNum.setText("");
			this.jTextField_SyousaiSyubetsuNum.setText("");
			this.jTextField_TsuikaSyubetsuNum.setText("");
			this.jTextField_DockSyubetsuNum.setText("");
			this.jTextField_KihonJougen.setText("");
			this.jTextField_ShousaiJougen.setText("");
			this.jTextField_TsuikaJyougen.setText("");
			this.jTextField_DockJougen.setText("");
			this.jTextField_sonotaHutangaku.setText("");
		}

		jTextField_HomePhone.setText("");
		// eidt s.inoue 2012/03/05
		// jTextField_IssueDate.setText("");
		jTextField_IssueDate.setDate(null);
		// jTextField_LimitDate.setText("");
		jTextField_LimitDate.setDate(null);

		jTextField_JyushinkenID.setText("");
		jTextField_MobileMail.setText("");
		jTextField_NameKana.setText("");
		jTextField_NameKanji.setText("");
		jTextField_NameTsushou.setText("");
		jTextField_ORCAID.setText("");
		jTextField_ZipCode.setText("");
		// eidt s.inoue 2011/12/12
		// jTextField_JyushinkenID.setEditable(true);
		jTextField_JyushinkenID.setEnabled(true);

		this.jTextField_sex.setText("");
		this.groupSex.clearSelection();


		this.jTextField_ORCAID.grabFocus();

		// add ver2 s.inoue 2009/08/13
		this.jTextField_Uketsukeid.setText("");
		this.jTextField_ZipCode.setText("");

		// add h.yoshitama 2009/09/30
		this.jTextField_YearOld.setText("");

		// edit s.inoue 2009/10/15
		jComboBox_ItakuKubun.setSelectedIndex(-1);
		jTextField_KihonTanka.setText("");
		jTextField_HinketsuTanka.setText("");
		jTextField_SindenzuTanka.setText("");
		jTextField_GanteiTanka.setText("");
		jTextField_NingenDocTanka.setText("");

		mode = ADD_MODE;
	}
// del s.inoue 2012/02/28 新API
//	/**
//	 * ORCAボタン
//	 */
//	public void pushedORCAButton() {
//		/* 日レセ連携が有効でない場合は、何もしない。 */
//		if (! JApplication.useORCA) {
//			this.jTextField_JyushinkenID.grabFocus();
//			return;
//		}
//
//		/*
//		 * 入力情報を検証する。
//		 */
//		String inputOrcaId = jTextField_ORCAID.getText();
//
//		if (inputOrcaId == null || inputOrcaId.isEmpty()) {
//			this.jTextField_JyushinkenID.grabFocus();
//			return;
//		}
//
//		/* ORCA ID の 0 埋めを行なう。 */
//		if ( JApplication.orcaSetting.isUseOrcaIdDigit() ) {
//			int orcaId = Integer.parseInt(inputOrcaId);
//
//			String digit = JApplication.orcaSetting.getOrcaIdDigit();
//			if (digit != null && ! digit.isEmpty()) {
//
//				String format = "%0" + digit + "d";
//
//				inputOrcaId = String.format(format, orcaId);
//			}
//		}
//
//		boolean validated = validatedData.setORCAID(inputOrcaId);
//		if (validated) {
//			if (validateAsPushedORCAButton(validatedData)) {
//				validated = true;
//			}
//		}
//
//		if (validated) {
//			/* 画面をクリアする。 */
//			this.pushedClearButton();
//
//			/* ORCA DB用コネクションを作成する。 */
//			OrcaConnection orcaCon = new OrcaConnection();
//			JConnection con = orcaCon.getOrcaDbConnectionFromConfigFile();
//
//			try {
//				if (con == null) {
//					JErrorMessage.show("M4394", this);
//					return;
//				}
//			}
//			finally {
//				jTextField_ORCAID.setText(inputOrcaId);
//			}
//
//			try{
//				String version = orcaCon.getOrcaVersion(con);
//
//				/* バージョンを検証する。 */
//				boolean validVersion = orcaCon.validateOrcaVersion(version);
//
//				/* 患者情報を取得する。 */
//				if (validVersion) {
//					ArrayList<Hashtable<String, String>> result =
//						orcaCon.getOrcaData(validatedData.getORCAID());
//
//					if (result == null) {
//						return;
//					}
//
//					/* 保険の終了期限が最大のレコードを選択する。 */
//					if (result.size() != 0) {
//						Hashtable<String, String> lastHokenOrcaData =
//							new Hashtable<String, String>();
//						String maxTekEdYmd = null;
//
//						for (Iterator<Hashtable<String, String>>
//							iter = result.iterator();
//							iter.hasNext();) {
//
//							Hashtable<String, String> item = iter.next();
//							String itemTekEdYmd = item.get("tekedymd");
//
//							if (maxTekEdYmd == null ||
//									itemTekEdYmd.compareTo(maxTekEdYmd) > 0 ) {
//
//								maxTekEdYmd = itemTekEdYmd;
//								lastHokenOrcaData = item;
//							}
//						}
//
//						this.inputOrcaDataToComponent(lastHokenOrcaData);
//						this.jTextField_JyushinkenID.grabFocus();
//
//					} else {
//						JErrorMessage.show("M4380", this);
//					}
//				}
//				else {
//					JErrorMessage.show("M9629", this);
//				}
//			}finally{
//				try {
//					con.Shutdown();
//
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//					JErrorMessage.show("M4392", this);
//				}
//			}
//		}
//	}

	// add s.inoue 2012/03/01
	/**
	 * ORCAボタン
	 */
	public void pushedORCAButton() {
		/* 日レセ連携が有効でない場合は、何もしない。 */
		if (! JApplication.useORCA) {
			this.jTextField_JyushinkenID.grabFocus();
			return;
		}

		/*
		 * 入力情報を検証する。
		 */
		String inputOrcaId = jTextField_ORCAID.getText();

		if (inputOrcaId == null || inputOrcaId.isEmpty()) {
			this.jTextField_JyushinkenID.grabFocus();
			return;
		}

		/* ORCA ID の 0 埋めを行なう。 */
		if ( JApplication.orcaSetting.isUseOrcaIdDigit() ) {
			int orcaId = Integer.parseInt(inputOrcaId);

			String digit = JApplication.orcaSetting.getOrcaIdDigit();
			if (digit != null && ! digit.isEmpty()) {

				String format = "%0" + digit + "d";

				inputOrcaId = String.format(format, orcaId);
			}
		}

		boolean validated = validatedData.setORCAID(inputOrcaId);
		if (validated) {
			if (validateAsPushedORCAButton(validatedData)) {
				validated = true;
			}
		}

		if (validated) {
			/* 画面をクリアする。 */
			this.pushedClearButton();
// eidt s.inoue 2012/03/01 新API対応
////			/* ORCA DB用コネクションを作成する。 */
//			OrcaConnection orcaCon = new OrcaConnection();
//			JConnection con = orcaCon.getOrcaDbConnectionFromConfigFile();
//			try {
//				if (con == null) {
//					JErrorMessage.show("M4394", this);
//					return;
//				}
//			}
//			finally {
//				jTextField_ORCAID.setText(inputOrcaId);
//			}

			String host = JApplication.orcaSetting.getOrcaIpAddress();
			String port = JApplication.orcaSetting.getOrcaPort();
//			String database = JApplication.orcaSetting.getOrcaDatabase();	// edit n.ohkubo 2014/10/01　未使用なので削除
			String user = JApplication.orcaSetting.getNichireseUser();
			String password = JApplication.orcaSetting.getNichiresePassword();

			JOrcaInfoSearchCtl orcaData = new JOrcaInfoSearchCtl();
			try {
				orcaData.setPatientId(inputOrcaId);		  // id
				orcaData.setPort(port);					  // 8000
				orcaData.setHost(host); // trial.orca.med.or.jp
				orcaData.setUser(user);	// trial
				orcaData.setPass(password); // ""
				orcaData.setURL();
			} catch (Exception e) {
				logger.error(e.getMessage());
				JErrorMessage.show("M4392", this);
				return;
			}

			// 取得情報をセット
			inputOrcaDataToComponent(orcaData);
			// eidt s.inoue 2012/03/06
			jTextField_JyushinkenID.grabFocus();

//			try{
//				String version = orcaCon.getOrcaVersion(con);
//
//				/* バージョンを検証する。 */
//				boolean validVersion = orcaCon.validateOrcaVersion(version);
//
//				/* 患者情報を取得する。 */
//				if (validVersion) {
//					ArrayList<Hashtable<String, String>> result =
//						orcaCon.getOrcaData(validatedData.getORCAID());
//
//					if (result == null) {
//						return;
//					}
//
//					/* 保険の終了期限が最大のレコードを選択する。 */
//					if (result.size() != 0) {
//						Hashtable<String, String> lastHokenOrcaData =
//							new Hashtable<String, String>();
//						String maxTekEdYmd = null;
//
//						for (Iterator<Hashtable<String, String>>
//							iter = result.iterator();
//							iter.hasNext();) {
//
//							Hashtable<String, String> item = iter.next();
//							String itemTekEdYmd = item.get("tekedymd");
//
//							if (maxTekEdYmd == null ||
//									itemTekEdYmd.compareTo(maxTekEdYmd) > 0 ) {
//
//								maxTekEdYmd = itemTekEdYmd;
//								lastHokenOrcaData = item;
//							}
//						}
//
//						this.inputOrcaDataToComponent(lastHokenOrcaData);
//						this.jTextField_JyushinkenID.grabFocus();
//
//					} else {
//						JErrorMessage.show("M4380", this);
//					}
//				}
//				else {
//					JErrorMessage.show("M9629", this);
//				}
//			}finally{
//				try {
////					con.Shutdown();
//
//				} catch (SQLException e) {
//					logger.error(e.getMessage());
//					JErrorMessage.show("M4392", this);
//				}
//			}
		}
	}

// del s.inoue 2012/03/01
//	private void inputOrcaDataToComponent(Hashtable<String, String> orcaData) {
//			/* 患者ID */
//			jTextField_ORCAID.setText(orcaData.get("ptnum").trim());
//			/* 氏名（カナ） */
//			jTextField_NameKana.setText(orcaData.get("kananame"));
//			/* 氏名（漢字） */
//			jTextField_NameKanji.setText(orcaData.get("name"));
//			/* 氏名（通称） */
//			jTextField_NameTsushou.setText(orcaData.get("nickname"));
//			/* 生年月日 */
//			// eidt s.inoue 2011/12/12
//			// jTextField_Birthday.setText(orcaData.get("birthday"));
//			try {
//				Date dt = jTextField_Birthday.getTextToDate(orcaData.get("birthday"));
//				jTextField_Birthday.setDate(dt);
//			} catch (ParseException e) {
//				logger.error(e.getMessage());
//			}
//
//			/* 自宅郵便番号 */
//			jTextField_ZipCode.setText(orcaData.get("home_post"));
//
//			/* 自宅住所 */
//			/* 前後にスペースを含む場合は除外する。 */
//			StringBuffer buffer = new StringBuffer();
//
//			String homeAddress = orcaData.get("home_adrs");
//			String homeBanti = orcaData.get("home_banti");
//			if (homeAddress != null && ! homeAddress.isEmpty()) {
//				buffer.append(homeAddress);
//			}
//			if (homeBanti != null && ! homeBanti.isEmpty()) {
//				buffer.append(homeBanti);
//			}
//			String address = buffer.toString();
//			if (address != null && ! address.isEmpty()) {
//				address = address.trim();
//
//				address = address.replaceAll("^　+", "");
//				address = address.replaceAll("　+$", "");
//
//				jTextField_Address.setText(address);
//			}
//
//			/* 自宅電話番号 */
//			jTextField_HomePhone.setText(orcaData.get("home_tel1"));
//			/* 携帯電話番号 */
//			jTextField_CellPhone.setText(orcaData.get("keitai_tel"));
//			/* FAX */
//			jTextField_FAXNumber.setText(orcaData.get("fax"));
//			/* E-mail */
//			jTextField_EMail.setText(orcaData.get("email"));
//
//			/* 自宅地番方書 */
////			validatedData.setChiban(resultItem.get("home_banti"));
//
//			/* 被保険者証等記号 */
//			jTextField_HihokenjyaCode.setText(orcaData.get("kigo"));
//
//			/* 被保険者証等番号 */
//			jTextField_HihokenjyaNumber.setText(orcaData.get("num"));
//
//			/* 性別 */
//			if (orcaData.get("sex").equals("1")) {
//				jRadioButton_Male.setSelected(true);
//			} else if (orcaData.get("sex").equals("2")) {
//				jRadioButton_Female.setSelected(true);
//			}
//	}


	// add s.inoue 2012/03/01
	private void inputOrcaDataToComponent(JOrcaInfoSearchCtl orcaData) {

		// 患者ID
		jTextField_ORCAID.setText(orcaData.getPatientId().trim());
		// 氏名（カナ）
		jTextField_NameKana.setText(orcaData.getSimeiKana());
		// 氏名（漢字）
		jTextField_NameKanji.setText(orcaData.getSimeiKanji());
		// 氏名（通称）→廃止

		// 生年月日
		// eidt s.inoue 2012/06/27
		jTextField_Birthday.setText(orcaData.getBirthDay());
//		try {
//			Date dt = jTextField_Birthday.getTextToDate(orcaData.getBirthDay());
//			jTextField_Birthday.setDate(dt);
//		} catch (ParseException e) {
//			logger.error(e.getMessage());
//		}

		// 自宅郵便番号
		// eidt s.inoue 2013/03/25
		// jTextField_ZipCode.setText(orcaData.getPostCode());
		jTextField_ZipCode.setPostCodeFormatText(orcaData.getPostCode());

		// 自宅住所
		// 前後にスペースを含む場合は除外する。
		StringBuffer buffer = new StringBuffer();

		// String homeAddress = orcaData.get("home_adrs");
		// String homeBanti = orcaData.get("home_banti");
		String homeAddress = orcaData.getAddress1();
		String homeBanti = orcaData.getAddress2();

		if (homeAddress != null && ! homeAddress.isEmpty()) {
			buffer.append(homeAddress);
		}
		if (homeBanti != null && ! homeBanti.isEmpty()) {
			buffer.append(homeBanti);
		}
		String address = buffer.toString();
		if (address != null && ! address.isEmpty()) {
			address = address.trim();

			address = address.replaceAll("^　+", "");
			address = address.replaceAll("　+$", "");

			jTextField_Address.setText(address);
		}

		// 自宅電話番号
		// jTextField_HomePhone.setText(orcaData.get("home_tel1"));
		// eidt s.inoue 2013/03/25
		jTextField_HomePhone.setText(orcaData.getPhoneNumber1().replaceAll("-", ""));

		// 携帯電話番号 廃止
		// jTextField_CellPhone.setText(orcaData.get("keitai_tel"));
		// FAX 廃止
		// jTextField_FAXNumber.setText(orcaData.get("fax"));
		// E-mail 廃止
		// jTextField_EMail.setText(orcaData.get("email"));
		// 自宅地番方書
		// validatedData.setChiban(resultItem.get("home_banti"));

		// 被保険者証等記号
		jTextField_HihokenjyaCode.setText(orcaData.getPersonSymbol());
		// 被保険者証等番号
		jTextField_HihokenjyaNumber.setText(orcaData.getPersonNumber());

		// 性別
		if (orcaData.getSex().equals("1")) {
			jRadioButton_Male.setSelected(true);
		} else if (orcaData.getSex().equals("2")) {
			jRadioButton_Female.setSelected(true);
		}
	}

	// edit n.ohkubo 2015/08/01　未使用なので削除
//	/**
//	 * QRボタン
//	 */
//	public void pushedQRButton(ActionEvent e) {
//		new JQRReader();
//	}

	/**
	 * 印刷機能
	 *
	 * 1ページ 健診項目入力シート（検査結果） 必須データ：受診券整理番号、氏名、受診日（自動生成）、保険者番号、被保険者証等記号、被保険者証等番号
	 * import Print.KenshinKoumoku_Kensa class KenshinKoumoku_Kensa
	 *
	 * 2ページ 健診項目入力シート（問診） 必須データ：受診券整理番号、氏名、受診日（自動生成）、保険者番号、被保険者証等記号、被保険者証等番号
	 * import Print.KenshinKoumoku_Monshin class KenshinKoumoku_Monshin
	 * 
	 * @param isAll	「入力票印刷」ボタン押下時：true、追加健診項目表のみ出力する場合：false
	 */
//	public void pushedPrintButton(ActionEvent e) {	// edit n.ohkubo 2015/08/01　削除
	public void pushedPrintButton(boolean isAll) {	// edit n.ohkubo 2015/08/01　追加
		
		// edit n.ohkubo 2015/08/01　追加　start
		String birthday = jTextField_Birthday.getText();
		if ((birthday != null) && (!birthday.isEmpty())) {
			if (!JValidate.validateCDate(birthday)) {
				JErrorMessage.show("M4317", this);
				return;
			}
		}
		// edit n.ohkubo 2015/08/01　追加　end

		// add s.inoue 2009/10/19
		try {
			dateSelectDialog = DialogFactory.getInstance().createDialog(this, new JButton(),null);
			dateSelectDialog.setShowCancelButton(false);
			// 健診実施日入力ダイアログを表示
			dateSelectDialog.setMessageTitle("健診実施日入力画面");
			dateSelectDialog.setVisible(true);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}

		/*
		 * 個人情報データ作成
		 */
		ProgressWindow progressWindow = new ProgressWindow(this, false, true);
		progressWindow.setGuidanceByKey("tokutei.jushinken.frame.progress.guidance.print1");
		progressWindow.setVisible(true);

		try {
			Hashtable<String, String> tmpKojin = new Hashtable<String, String>();

			// edit s.inoue 2010/04/15
			// 健診実施日
			String selectKenshinDate = dateSelectDialog.getKenshinDate();
			String kenshinDate = dateSelectDialog.getKenshinDate();
			String printKenshinDate = "";
			if (!selectKenshinDate.equals("")){
				printKenshinDate = selectKenshinDate;
			}else{
				printKenshinDate = kenshinDate;
			}
			tmpKojin.put("KENSA_NENGAPI", printKenshinDate);

			// 受診券整理番号
			String jyushinkenID = jTextField_JyushinkenID.getText();
			if (jyushinkenID == null) {
				jyushinkenID = "";
			}
			tmpKojin.put("JYUSHIN_SEIRI_NO", jyushinkenID);

			// 受診者氏名
			String nameKana = jTextField_NameKana.getText();
			if (nameKana == null) {
				nameKana = "";
			}
			tmpKojin.put("KANANAME", nameKana);

			// 保険者番号
			int hokenjaIndex = this.jComboBox_HokenjyaNumber.getSelectedIndex();
			String hokenjyaNumber = this.hokenjaNumbers.get(hokenjaIndex);
			if (hokenjyaNumber == null) {
				hokenjyaNumber = "";
			}
			tmpKojin.put("HKNJANUM", hokenjyaNumber);

			// 被保険者証等記号
			String hihokenjyaCode = jTextField_HihokenjyaCode.getText();
			if (hihokenjyaCode == null) {
				hihokenjyaCode = "";
			}
			tmpKojin.put("HIHOKENJYASYO_KIGOU", hihokenjyaCode);

			// 被保険者証等番号
			String hihokenjyaNumber = jTextField_HihokenjyaNumber.getText();
			if (hihokenjyaNumber == null) {
				hihokenjyaNumber = "";
			}
			tmpKojin.put("HIHOKENJYASYO_NO", hihokenjyaNumber);
			// 名前漢字
			String name = jTextField_NameKanji.getText();
			if (name == null) {
				name = "";
			}
			tmpKojin.put("NAME", name);
			// 生年月日
			// eidt s.inoue 2012/06/27
			String Birthday = jTextField_Birthday.getText();
			// String Birthday = jTextField_Birthday.getDateText();
			if (Birthday == null) {
				Birthday = "";
			}
			tmpKojin.put("Birthday", Birthday);
			// 性別
			String SEX = jTextField_sex.getText();
			if (SEX == null) {
				SEX = "";
			}
			tmpKojin.put("SEX", SEX);
			// 受付ID
			String uketukeID = jTextField_Uketsukeid.getText();
			if (uketukeID == null) {
				uketukeID = "";
			}
			tmpKojin.put("UKETUKE_ID", uketukeID);

			// edit s.inoue 2009/10/19
//			// 年度
//			String Nendo = jTextField_Nendo.getText();
//			if (Nendo == null) {
//				Nendo = "";
//			}
//			tmpKojin.put("KENSA_NENGAPI", Nendo);

			Kojin KojinData = new Kojin();

			if (isFromKekkaRegster) {
				KojinData.setPrintKojinDataSQL(tmpKojin);
			// edit s.inoue 2009/10/31
			} else {
				if (!KojinData.setPrintKojinData(tmpKojin,this.mode)) {
					// データ移送失敗
					System.out.println("印刷に失敗しました。（データ移送失敗　個人）");
				}
			}

			/*
			 * 印刷データ生成 個人データを格納する
			 */
			Hashtable<String, Object> PrintData = new Hashtable<String, Object>();
			PrintData.put("Kojin", KojinData);

			if (isAll) {	// edit n.ohkubo 2015/08/01　追加
				
				/*
				 * 印刷 1ページ目を印刷すると、自動的に最終ページまで出力される
				 */
				// KenshinKoumoku_Kensa Kensa = new KenshinKoumoku_Kensa();
				PrintNyuryoku Kensa = new PrintNyuryoku();
				Kensa.setQueryResult(PrintData);
				progressWindow.setVisible(false);

				try {
					Kensa.beginPrint();

				} catch (PrinterException ex) {
					logger.error(ex.getMessage());
					JErrorMessage.show("M4393", this);
				}
				
			// edit n.ohkubo 2015/08/01　追加　start
			//追加健診項目表のみ出力
			} else {
				PrintTsuikaKenshin tsuikaKenshin = new PrintTsuikaKenshin(PrintData);
				progressWindow.setVisible(false);

				try {
					tsuikaKenshin.beginPrint();

				} catch (PrinterException ex) {
					logger.error(ex.getMessage());
					JErrorMessage.show("M4393", this);
				}
			}
			// edit n.ohkubo 2015/08/01　追加　end
			
		} finally {
			progressWindow.setVisible(false);
		}
	}

	/**
	 * 保険者番号入力後Enterが押された場合 保険者名称と電話番号の表示
	 */
	protected void enterKeyPushedHokenjyaNumber(boolean isSkipCreateDialog) {

		/* 何も入力していない場合は、次のコンポーネントにフォーカスを移動する。 */
		String inputNumber = this.jTextField_hokenjaNumber.getText();
		if (inputNumber.isEmpty()) {
			this.jTextField_hokenjaNumber.transferFocus();
			return;
		}

		int itemIndex = 2;
		for (itemIndex = 2; itemIndex < hokenjaNumbers.size(); itemIndex++) {
			String number = this.hokenjaNumbers.get(itemIndex);
			if (number.equals(inputNumber)) {
				break;
			}
		}
	}

	/**
	 * 支払代行機関番号入力後Enterが押された場合 支払代行機関名称と電話番号の表示
	 */
	protected void enterKeyPushedDaikouNumber(boolean isSkipCreateDialog) {
		/* 何も入力していない場合は、次のコンポーネントにフォーカスを移動する。 */
		String inputNumber = this.jTextField_daikouNumber.getText();
		if (inputNumber.isEmpty()) {
			this.jTextField_daikouNumber.transferFocus();
			return;
		}

//		boolean existsNumber = false;	// edit n.ohkubo 2014/10/01　未使用なので削除

		int itemIndex = 2;
		for (itemIndex = 2; itemIndex < daikouNumbers.size(); itemIndex++) {
			String number = this.daikouNumbers.get(itemIndex);
			if (number.equals(inputNumber)) {
//				existsNumber = true;	// edit n.ohkubo 2014/10/01　未使用なので削除
				break;
			}
		}
	}

	/**
	 * 保険者情報の新規追加ダイアログを表示する
	 */
	private void showAddHokenjaNumberDialog(String inputNumber) {
//		JHokenjyaMasterMaintenanceEditFrameCtrl ctrl = null;	// edit n.ohkubo 2014/10/01　未使用なので削除
		if (inputNumber == null || inputNumber.isEmpty()) {
			// eidt s.inoue 2012/12/21
			// ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl();
			JScene.CreateDialog(
			this,
			new JHokenjyaMasterMaintenanceEditFrameCtrl(),
			new HokenjyaWindowEvent()
			);
		}
		else {
			validatedData.setHokenjyaNumber(inputNumber,false);
			// eidt s.inoue 2012/12/21
			// ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl(validatedData);
			JScene.CreateDialog(
					this,
					new JHokenjyaMasterMaintenanceEditFrameCtrl(validatedData),
					new HokenjyaWindowEvent()
			);
		}
		// move s.inoue 2012/12/21
//		JScene.CreateDialog(
//				this,
//				ctrl,
//				new HokenjyaWindowEvent()
//			);
	}

	/**
	 * 支払代行機関情報の新規追加ダイアログを表示する。
	 */
	private void showAddDaikouNumberDialog(String inputNumber) {

		JShiharaiMasterMaintenanceEditFrameCtrl ctrl = null;

		if (inputNumber == null || inputNumber.isEmpty()) {
			ctrl = new JShiharaiMasterMaintenanceEditFrameCtrl();
		}
		else {
			validatedData.setDaikouNumber(inputNumber);
			ctrl = new JShiharaiMasterMaintenanceEditFrameCtrl(validatedData);
		}

		JScene.CreateDialog(
				this,
				ctrl,
				new DaikouWindowEvent()
			);
	}

	/**
	 * 男性のラジオボタンが押された場合
	 */
	public void changedMaleState(ItemEvent e) {
		if (ItemEvent.SELECTED == e.getStateChange()) {
			jTextField_sex.setText("1");
		} else {
			jTextField_sex.setText("");
		}
	}

	/**
	 * 女性のラジオボタンが押された場合
	 */
	public void changedFemaleState(ItemEvent e) {
		if (ItemEvent.SELECTED == e.getStateChange()) {
			jTextField_sex.setText("2");
		} else {
			jTextField_sex.setText("");
		}
	}

	/**
	 * アクションリスナー
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		/* 終了ボタン */
		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			dispose();
		}
		/* 登録ボタン */
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);

		/* DB読込ボタン */
		} else if (source == jButton_Call) {
			logger.info(jButton_Call.getText());
			pushedCallButton(e);
		}
		/* クリアボタン */
		else if (source == jButton_Clear) {
			logger.info(jButton_Clear.getText());
			pushedClearButton();
		}
		/* 日レセ読込ボタン */
		else if (source == jButton_ORCA) {
			logger.info(jButton_ORCA.getText());
			pushedORCAButton();
		}
//		/* QRボタン */
//		else if (source == jButton_QR) {
//			logger.info(jButton_QR.getText());
//			pushedQRButton(e);
//		}
		/* 入力票印刷ボタン */
		else if (source == jButton_Print) {
			logger.info(jButton_Print.getText());
//			pushedPrintButton(e);	// edit n.ohkubo 2015/08/01　削除
			pushedPrintButton(true);	// edit n.ohkubo 2015/08/01　追加
		}

		/*
		 * 医療機関番号及び支払代行機関番号フィールドでEnterを押した際に名称と電話番号を読み込む
		 */
		else if (source == jTextField_hokenjaNumber) {
			logger.info(jTextField_hokenjaNumber.getText());
			enterKeyPushedHokenjyaNumber(false);

		}
		/* 支払代行機関入力欄 */
		else if (source == jTextField_daikouNumber) {
			logger.info(jTextField_daikouNumber.getText());
			enterKeyPushedDaikouNumber(false);
		}

		/* 患者ID入力欄 */
		else if (source == jTextField_ORCAID) {
			logger.info(jTextField_ORCAID.getText());
			this.pushedORCAButton();
		}

		/* 保険者呼出ボタン */
		else if (source == jButton_CallHokenjya) {
			logger.info(jButton_CallHokenjya.getText());
			String number = this.jTextField_hokenjaNumber.getText();
			number = JValidate.fillZero(number, 8);

			if (!this.hokenjaNumbers.contains(number)) {
				return;
			}

			JHokenjyaMasterMaintenanceEditFrameCtrl ctrl = null;

			if (number == null || number.isEmpty()) {
				ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl();
			} else {
				ctrl = new JHokenjyaMasterMaintenanceEditFrameCtrl(number);
			}

			JScene.CreateDialog(this, ctrl, new HokenjyaWindowEvent());
		}
		
		// edit n.ohkubo 2015/08/01　追加　start
		/* 追加健診ボタン */
		else if (source == jButton_Print_tsuikakenshin) {
			logger.info(jButton_Print_tsuikakenshin.getText());
			pushedPrintButton(false);
		}
		// edit n.ohkubo 2015/08/01　追加　end
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {

		Object source = e.getSource();
		if (source == jRadioButton_Male) {
			changedMaleState(e);
		}

		else if (source == jRadioButton_Female) {
			changedFemaleState(e);
		}

		/*
		 * 対となるテキストフィールドのsetterを呼んだ時点で種別が先にセットされていることを保証する
		 */
		else if (source == jComboBox_MadoguchiKihonSyubetu) {

			this.showUnitAndSyubetsuNumToTextField(
					jTextField_KihonSyubetsuNum,
					jTextField_MadoguchiKihon,
					jComboBox_MadoguchiKihonSyubetu,
					jLabel_madoguchikihon_unit,
					jTextField_KihonJougen
				);
		}
		else if (source == jComboBox_MadoguchiShousaiSyubetu) {
			this.showUnitAndSyubetsuNumToTextField(
					jTextField_SyousaiSyubetsuNum,
					jTextField_MadoguchiShousai,
					jComboBox_MadoguchiShousaiSyubetu,
					jLabel_madoguchishosai_unit,
					jTextField_ShousaiJougen
				);
		}

		else if (source == jComboBox_MadoguchiTsuikaSyubetu) {
			this.showUnitAndSyubetsuNumToTextField(
					jTextField_TsuikaSyubetsuNum,
					jTextField_MadoguchiTsuika,
					jComboBox_MadoguchiTsuikaSyubetu,
					jLabel_madoguchitsuika_unit,
					jTextField_TsuikaJyougen
				);
		}
		else if (source == jComboBox_MadoguchiDockSyubetu) {
			this.showUnitAndSyubetsuNumToTextField(
					jTextField_DockSyubetsuNum,
					jTextField_MadoguchiDock,
					jComboBox_MadoguchiDockSyubetu,
					jLabel_madoguchidock_unit,
					jTextField_DockJougen
				);
		}
		/* 保険者情報選択欄 */
		else if (source == jComboBox_HokenjyaNumber) {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& this.jComboBox_HokenjyaNumber.hasFocus()
					&& ! this.jComboBox_HokenjyaNumber.isPopupVisible()
			) {
				this.showHokenjyaNumberToTextField();
			}
		}
		/* 支払代行機関情報選択 */
		else if (source == jComboBox_DaikouNumber) {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& this.jTextField_daikouNumber.hasFocus()
					&& ! this.jComboBox_DaikouNumber.isPopupVisible()
			) {
				this.showDaikouNumberToTextField();
			}
		}
	}

	private void showDaikouNumberToTextField() {
		int selectedIndex = jComboBox_DaikouNumber.getSelectedIndex();
		int size = this.daikouNumbers.size();

		if (size > selectedIndex && selectedIndex >= 0) {
			String number = this.daikouNumbers.get(selectedIndex);

			if (selectedIndex >= COMBOBOX_NUMBER_START_INDEX) {

				this.jTextField_daikouNumber.setText(number);
			}
		}
	}

	private void showHokenjyaNumberToTextField() {
		int selectedIndex = jComboBox_HokenjyaNumber.getSelectedIndex();
		int size = this.hokenjaNumbers.size();

		if (size > selectedIndex && selectedIndex >= 0) {
			String number = this.hokenjaNumbers.get(selectedIndex);

			if (selectedIndex >= COMBOBOX_NUMBER_START_INDEX ) {

				this.jTextField_hokenjaNumber.setText(number);
				// add s.inoue 2009/10/15
				this.setHokenjyaTanka(number);
			}
		}
	}

	// add s.inoue 2009/10/15
	private void setHokenjyaTanka(String hokenjyaNumber){

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("HKNJANUM, HKNJANAME, POST, ADRS, BANTI, TEL, KIGO, HON_GAIKYURATE, HON_NYUKYURATE, KZK_GAIKYURATE, KZK_NYUKYURATE, ITAKU_KBN, TANKA_KIHON, ");
		sb.append("HINKETU_CD, TANKA_HINKETU, SINDENZU_CD, TANKA_SINDENZU, GANTEI_CD, TANKA_GANTEI, TANKA_NINGENDOC, TANKA_HANTEI");
		sb.append(" FROM T_HOKENJYA WHERE HKNJANUM = ");
		sb.append(JQueryConvert.queryConvert(hokenjyaNumber));
		// add s.inoue 2010/01/15
		sb.append(" AND YUKOU_FLG = '1' ");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		resultItem = result.get(0);

		String hani = resultItem.get("TANKA_HANTEI");
		hani = (hani == null)?TANKA_HANTEI_KIHON: hani;

		if (hani.equals(TANKA_HANTEI_DOC)) {
//			jRadioButton_Doc.setSelected(true);
			String tankaDoc = resultItem.get("TANKA_NINGENDOC");
			jTextField_NingenDocTanka.setText(tankaDoc.equals("")?"0":tankaDoc);
		}else{
			// 基本健診のみ設定
//			jRadioButton_Kihon.setSelected(true);
			String tankaKihon = resultItem.get("TANKA_KIHON");
			String tankaHinketu = resultItem.get("TANKA_HINKETU");
			String tankaSindenzu = resultItem.get("TANKA_SINDENZU");
			String tankaGantei = resultItem.get("TANKA_GANTEI");
			jTextField_KihonTanka.setText(tankaKihon.equals("")?"0":tankaKihon);
			jTextField_HinketsuTanka.setText(tankaHinketu.equals("")?"0":tankaHinketu);
			jTextField_SindenzuTanka.setText(tankaSindenzu.equals("")?"0":tankaSindenzu);
			jTextField_GanteiTanka.setText(tankaGantei.equals("")?"0":tankaGantei);
		}
	}

	/**
	 * 単位と種別コードを表示する。
	 */
	private void showUnitAndSyubetsuNumToTextField(
			ExtendedOpenTextControl jTextField_SyubetsuNum,
			ExtendedOpenTextControl jTextField_Madoguchi,
			ExtendedComboBox jComboBox_MadoguchiSyubetu,
			ExtendedLabel jLabel_madoguchi_unit,
			ExtendedOpenTextControl jTextField_Jougen
			) {

		/*
		 * 「受診者は負担無し」が選択された場合は、
		 * 負担額・負担率欄を空欄にし、無効状態にする。
		 */
		jTextField_Madoguchi.setText("");

		int selectedIndex = jComboBox_MadoguchiSyubetu.getSelectedIndex();
		if (selectedIndex == 0 || selectedIndex == 1) {
			jTextField_Madoguchi.setEnabled(false);
			jTextField_Madoguchi.setOpaque(false);

			jLabel_madoguchi_unit.setText("");
		} else {
			jTextField_Madoguchi.setEnabled(true);
			jTextField_Madoguchi.setOpaque(true);

			if (selectedIndex == 2) {
				jLabel_madoguchi_unit.setText(STRING_UNIT_YEN);
			} else if(selectedIndex == 3){
				jLabel_madoguchi_unit.setText(STRING_UNIT_PERCENT);
			} else {
				jLabel_madoguchi_unit.setText("");
			}
		}
		// eidt s.inoue 2011/12/13
		if (selectedIndex == 0) {
			jTextField_SyubetsuNum.setText("");
			// jTextField_Jougen.setEditable(false);
			jTextField_Jougen.setEnabled(false);
		} else if (selectedIndex == 1){
			jTextField_SyubetsuNum.setText("1");
			// jTextField_Jougen.setEditable(true);
			jTextField_Jougen.setEnabled(true);
		} else if (selectedIndex == 2){
			jTextField_SyubetsuNum.setText("2");
			// jTextField_SyubetsuNum.setEditable(false);
//			jTextField_SyubetsuNum.setEnabled(false);	// edit n.ohkubo 2014/10/01　削除
			jTextField_Jougen.setEnabled(false);	// edit n.ohkubo 2014/10/01　追加
		} else if (selectedIndex == 3){
			jTextField_SyubetsuNum.setText("3");
			// jTextField_Jougen.setEditable(false);
			jTextField_Jougen.setEnabled(false);
		} else {
			jTextField_SyubetsuNum.setText("");
			// jTextField_Jougen.setEditable(false);
			jTextField_Jougen.setEnabled(false);
		}
	}

	/**
	 * 保険者情報登録画面のウィンドウリスナー
	 */
	public class HokenjyaWindowEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (JHokenjyaMasterMaintenanceEditFrameCtrl.isRegistered()) {
				JKojinRegisterFrameCtrl.this.initializeHokenjaNumComboBox();
				String hokenjaNumber = JHokenjyaMasterMaintenanceEditFrameCtrl
						.getLastAddedNumber();

				int hokenjaIndex = JKojinRegisterFrameCtrl.this.hokenjaNumbers
						.indexOf(hokenjaNumber);
				JKojinRegisterFrameCtrl.this.jComboBox_HokenjyaNumber
						.setSelectedIndex(hokenjaIndex);
				JKojinRegisterFrameCtrl.this.jTextField_hokenjaNumber.setText(
						JHokenjyaMasterMaintenanceEditFrameCtrl.getLastAddedNumber());
			}
			else {
				JKojinRegisterFrameCtrl.this.jComboBox_HokenjyaNumber.setSelectedIndex(0);
			}
		}
	}

	/**
	 * 支払代行機関登録画面のウィンドウリスナー
	 */
	public class DaikouWindowEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (JShiharaiMasterMaintenanceEditFrameCtrl.isRegistered()) {
				JKojinRegisterFrameCtrl.this.initializeDaikouNumberComboBox();
				String number = JShiharaiMasterMaintenanceEditFrameCtrl
						.getLastAddedNumber();

				int index = JKojinRegisterFrameCtrl.this.daikouNumbers
						.indexOf(number);
				JKojinRegisterFrameCtrl.this.jComboBox_DaikouNumber
						.setSelectedIndex(index);
				JKojinRegisterFrameCtrl.this.jTextField_daikouNumber.setText(
						JShiharaiMasterMaintenanceEditFrameCtrl.getLastAddedNumber());
			}
			else {
				JKojinRegisterFrameCtrl.this.jComboBox_DaikouNumber.setSelectedIndex(0);
			}
		}
	}

	/**
	 * keyTyped イベントハンドラ
	 */
	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {

		Object source = e.getSource();
		char keyChar = e.getKeyChar();

		/* 男性ラジオボタン */
		if (source == this.jRadioButton_Male) {
			if (keyChar == KeyEvent.VK_SPACE) {
				this.jRadioButton_Male.setSelected(true);
			}
		/* 女性ラジオボタン */
		} else if (source == this.jRadioButton_Female) {
			if (keyChar == KeyEvent.VK_SPACE) {
				this.jRadioButton_Female.setSelected(true);
			}
		/* 性別入力欄 */
		} else if (source == jTextField_sex) {

			if (keyChar == KeyEvent.VK_1) {
				this.jTextField_sex.setText("1");
				this.jRadioButton_Male.setSelected(true);
			} else if (keyChar == KeyEvent.VK_2) {
				this.jTextField_sex.setText("2");
				this.jRadioButton_Female.setSelected(true);
			} else {
				String currentValue = this.jTextField_sex.getText();
				if (!currentValue.equals("1") && !currentValue.equals("2")) {
					this.groupSex.clearSelection();
				}
			}
		} else if (source == jTextField_KihonSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_KihonSyubetsuNum,
					jComboBox_MadoguchiKihonSyubetu
				);

		} else if (source == jTextField_SyousaiSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_SyousaiSyubetsuNum,
					jComboBox_MadoguchiShousaiSyubetu
				);

		} else if (source == jTextField_TsuikaSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_TsuikaSyubetsuNum,
					jComboBox_MadoguchiTsuikaSyubetu
				);

		} else if (source == jTextField_DockSyubetsuNum) {
			this.setSyubetsuNumText(
					keyChar,
					jTextField_DockSyubetsuNum,
					jComboBox_MadoguchiDockSyubetu
				);
		}
		/* 保険者番号 */
		else if (source == this.jComboBox_HokenjyaNumber) {
			if (keyChar == KeyEvent.VK_ENTER) {
				this.typedEnterKeyOnHokenjyaNumberTextField();
			}
		}
		else if (source == this.jComboBox_DaikouNumber) {
			if (keyChar == KeyEvent.VK_ENTER) {
				String selectedItem = (String)
					this.jComboBox_DaikouNumber.getSelectedItem();

				if (selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM)) {
				}
				else {
					this.showDaikouNumberToTextField();
				}
			}
		}
		// del s.inoue 2009/10/15
//		/* 基本ラジオボタン */
//		else if (source == this.jRadioButton_Kihon) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Kihon.setSelected(true);
//			}
//		/* Docラジオボタン */
//		} else if (source == this.jRadioButton_Doc) {
//			if (keyChar == KeyEvent.VK_SPACE) {
//				this.jRadioButton_Doc.setSelected(true);
//			}
//		else if (source == this.jTextField_TankaHantei) {
//			if (keyChar == KeyEvent.VK_1) {
//				this.jTextField_TankaHantei.setText("1");
//				this.jRadioButton_Kihon.setSelected(true);
//			} else if (keyChar == KeyEvent.VK_2) {
//				this.jRadioButton_Doc.setText("2");
//				this.jRadioButton_Doc.setSelected(true);
//			} else {
//				String currentValue = this.jTextField_TankaHantei.getText();
//				if (!currentValue.equals("1") && !currentValue.equals("2")) {
//					this.groupHantei.clearSelection();
//				}
//			}
//		}
	}

	private void typedEnterKeyOnHokenjyaNumberTextField() {
		String selectedItem = (String)
			this.jComboBox_HokenjyaNumber.getSelectedItem();

		if (selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM)) {
		}
		else {
			this.showHokenjyaNumberToTextField();
		}
	}

	private void setSyubetsuNumText(
			char keyChar,
			ExtendedOpenTextControl textField,
			ExtendedComboBox comboBox) {

		switch (keyChar) {
		case KeyEvent.VK_1:
			textField.setText("1");
			comboBox.setSelectedIndex(1);
			break;

		case KeyEvent.VK_2:
			textField.setText("2");
			comboBox.setSelectedIndex(2);
			break;

		case KeyEvent.VK_3:
			textField.setText("3");
			comboBox.setSelectedIndex(3);
			break;

		case KeyEvent.VK_ENTER:
			textField.transferFocus();
			break;

		default:
			textField.setText("");
			comboBox.setSelectedIndex(0);

			break;
		}
	}
//	/**
//	 * 基本のラジオボタンが押された場合
//	 */
//	public void changedKihonState(ItemEvent e) {
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			jTextField_TankaHantei.setText("1");
//		} else {
//			// add s.inoue 2009/10/02
//			clearJTextfield_Tanka(false);
//		}
//	}

//	/**
//	 * 人間ドックのラジオボタンが押された場合
//	 */
//	public void changedDocState(ItemEvent e) {
//		if (ItemEvent.SELECTED == e.getStateChange()) {
//			jTextField_TankaHantei.setText("2");
//		} else {
//			// add s.inoue 2009/10/02
//			clearJTextfield_Tanka(true);
//		}
//	}

//	// add s.inoue 2009/10/02
//	private void clearJTextfield_Tanka(boolean hantei){
//		jTextField_TankaHantei.setText("");
//		if (hantei) {
//			this.jTextField_KihonTanka.setText("");
//			this.jTextField_SindenzuTanka.setText("");
//			this.jTextField_GanteiTanka.setText("");
//			this.jTextField_HinketsuTanka.setText("");
//		}else{
//			this.jTextField_NingenDocTanka.setText("");
//		}
//	}
	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
		Object source = event.getSource();

		if (source == this.jComboBox_HokenjyaNumber) {
			this.showHokenjyaNumberToTextField();
			this.jComboBox_HokenjyaNumber.transferFocus();
		}
		else if(source == this.jComboBox_DaikouNumber){
			this.showDaikouNumberToTextField();
			this.jComboBox_DaikouNumber.transferFocus();
		}
	}

//	// add s.inoue 2012/02/02
//	@Override
//	public void focusGained(FocusEvent event) {
//		Object source = event.getSource();
//
//		if (source instanceof JTextField){
//			JTextField txt = (JTextField)source;
//			txt.setBackground(JApplication.backColor_Focus);
//		}else if (source instanceof JComboBox){
//			JComboBox cmb = (JComboBox)source;
//			cmb.setBackground(JApplication.backColor_Focus);
//		}
//	}

	@Override
	public void focusLost(FocusEvent event) {
		Object source = event.getSource();

		if (source instanceof JTextField){

			JTextField txt = (JTextField)source;
			if(txt.getParent().equals(this.jTextField_NameKana)){
				// 新規、経年処理の場合のみ氏名かなで検索を行う
				if (this.mode == ADD_MODE){
					String selectedItem = this.jTextField_NameKana.getText();

					if (selectedItem == null || selectedItem.isEmpty()) {
						return;
					}
					setKojinDataFromShimeiKana(selectedItem);
				}
			}else if(txt.getParent().equals(this.jTextField_Birthday)){
				// eidt s.inoue 2012/06/27
				String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getText());
				// String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getDateText());
				if ( selectedItem == null) {
					jTextField_YearOld.setText("");
					return;
				}

				// eidt s.inoue 2013/07/01
//				String age ="";
//				if (yearOld_flg){
//					age = String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem));
//				}else{
//					age = JYearAge.getAge(selectedItem);
//				}
				int age =0;
				if (yearOld_flg){
					if (!kensanengapi.equals("")){
						age = FiscalYearUtil.getFiscalYear(selectedItem,kensanengapi);
					}else{
						age = FiscalYearUtil.getFiscalYear(selectedItem);
					}
				}else{
					if (!kensanengapi.equals("")){
						age = Integer.parseInt(JYearAge.getAge(selectedItem,kensanengapi));
					}else{
						age = Integer.parseInt(JYearAge.getAge(selectedItem));
					}
				}
				jTextField_YearOld.setText(String.valueOf(age));

			}else if(txt.getParent().equals(this.jTextField_ZipCode)){
					if (jTextField_ZipCode.getTextTrim().equals(""))
						return;
					// eidt s.inoue 2012/04/24 gettext
					String zipText = setZipCodeAddress(this.jTextField_ZipCode.getText());
					if (zipText.equals(""))
						return;
					jTextField_Address.setText(zipText);
			}
		}else if (source instanceof JComboBox){

			JComboBox cmb = (JComboBox)source;
			if (cmb.equals(this.jComboBox_HokenjyaNumber)) {
				String selectedItem = (String)
					this.jComboBox_HokenjyaNumber.getSelectedItem();

				if (selectedItem == null || selectedItem.isEmpty()) {
					return;
				}

				String inputText = this.jTextField_hokenjaNumber.getText();

				if(selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM) ){
					this.showAddHokenjaNumberDialog(inputText);
				}
			} else if(cmb.equals(this.jComboBox_DaikouNumber)){
				String selectedItem = (String)
					this.jComboBox_DaikouNumber.getSelectedItem();

				if (selectedItem == null || selectedItem.isEmpty()) {
					return;
				}

				String inputText = this.jTextField_daikouNumber.getText();

				if(selectedItem.equals(COMBOBOX_ITEM_ADD_NEWITEM) ){
					this.showAddDaikouNumberDialog(inputText);
				}
			}
		}
		// eidt s.inoue 2011/12/13 上に置き換え
//		else if(source.equals(this.jTextField_NameKana)){
//			// 新規、経年処理の場合のみ氏名かなで検索を行う
//			if (this.mode == ADD_MODE){
//				String selectedItem = this.jTextField_NameKana.getText();
//
//				if (selectedItem == null || selectedItem.isEmpty()) {
//					return;
//				}
//				setKojinDataFromShimeiKana(selectedItem);
//			}
//		}
//		else if(source.equals(this.jTextField_Birthday)){
//			// eidt s.inoue 2011/12/12
//			// String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getText());
//			String selectedItem = JValidate.validateSendSeinengapi(this.jTextField_Birthday.getDateText());
//			if ( selectedItem == null) {
//				jTextField_YearOld.setText("");
//				return;
//			}
//			jTextField_YearOld.setText(String.valueOf(FiscalYearUtil.getFiscalYear(selectedItem)));
//		}
//		// add s.inoue 2010/03/29
//		else if(source.equals(this.jTextField_ZipCode)){
//			if (jTextField_ZipCode.getText().equals(""))
//				return;
//
//			String zipText = setZipCodeAddress(this.jTextField_ZipCode.getText());
//			if (zipText.equals(""))
//				return;
//
//			jTextField_Address.setText(zipText);
//		}
	}

	// add s.inoue 2010/04/02
	private String setZipCodeAddress(String zipCode){

			ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
			Hashtable<String, String> resultItem = new Hashtable<String, String>();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT ADDRESS,POST_CD FROM T_POST ");
			// eidt s.inoue 2012/04/24
//			sb.append("(SELECT SUBSTRING(POST_CD FROM 1 FOR 3) || SUBSTRING(POST_CD FROM 5 FOR 4) POSTCD,");
//			sb.append(" POST_CD SP_POSTCD FROM T_POST) SUB_POST");
//			sb.append(" where SUB_POST.POSTCD = ");
			sb.append(" where POST_CD = ");
			sb.append(JQueryConvert.queryConvert(zipCode));
//			sb.append(" and SUB_POST.SP_POSTCD = T_POST.POST_CD ");

			try {
				result = JApplication.systemDatabase.sendExecuteQuery(sb.toString());
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			if (result.size() <= 0)
				return "";

			resultItem = result.get(0);
			return resultItem.get("ADDRESS");

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
			case KeyEvent.VK_F1:
				logger.info(jButton_End.getText());
				dispose();break;
			case KeyEvent.VK_F2:
				logger.info(jButton_Clear.getText());
				pushedClearButton();break;
			case KeyEvent.VK_F5:
				logger.info(jButton_Print.getText());
//				pushedPrintButton(null);break;			// edit n.ohkubo 2015/08/01　削除
				pushedPrintButton(true);break;	// edit n.ohkubo 2015/08/01　追加
			case KeyEvent.VK_F6:
				logger.info(jButton_Call.getText());
				pushedCallButton(null);break;
			case KeyEvent.VK_F12:
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

