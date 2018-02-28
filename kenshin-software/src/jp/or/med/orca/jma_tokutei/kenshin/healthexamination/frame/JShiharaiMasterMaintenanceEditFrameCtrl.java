package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.convert.JZenkakuKatakanaToHankakuKatakana;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKojinRegisterFrameData;

/**
 * 支払代行機関情報の編集のコントロール
 */
public class JShiharaiMasterMaintenanceEditFrameCtrl extends
		JShiharaiMasterMaintenanceEditFrame {

	private static final int ADD_MODE = 1;

	private static final int CHANGE_MODE = 2;

	private JShiharaiMasterMaintenanceEditFrameData validatedData =
		new JShiharaiMasterMaintenanceEditFrameData();

	private int mode;

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JShiharaiMasterMaintenanceEditFrameCtrl.class);

	/**
	 * 支払代行機関情報追加の場合のコンストラクタ（遷移元：マスタメンテナンス）
	 */
	public JShiharaiMasterMaintenanceEditFrameCtrl() {
		super();
		mode = ADD_MODE;

		jTextField_DaikouNumber.setBackground(ViewSettings
				.getRequiedItemBgColor());
		initializefocus();
		// add s.inoue 2009/12/02
		functionListner();
	}

	/**
	 * 支払代行機関情報追加の場合のコンストラクタ（遷移元：個人情報登録フレーム）
	 */
	public JShiharaiMasterMaintenanceEditFrameCtrl(
			JKojinRegisterFrameData ptData) {
		super();
		jTextField_DaikouNumber.setText(ptData.getDaikouNumber());
		mode = ADD_MODE;

		jTextField_DaikouNumber.setBackground(ViewSettings
				.getRequiedItemBgColor());
		initializefocus();
		// add s.inoue 2009/12/02
		functionListner();
	}

	/**
	 * 支払代行機関情報変更の場合のコンストラクタ（遷移元：マスタメンテナンス）
	 */
	public JShiharaiMasterMaintenanceEditFrameCtrl(String daikouNumber) {
		super();
//		init();

		// 遷移元フレームから得た保険者番号から既存のデータを取得
		jTextField_DaikouNumber.setText(daikouNumber);
		jTextField_DaikouNumber.setEditable(false);

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();
		String Query = new String("SELECT * FROM T_SHIHARAI WHERE SHIHARAI_DAIKO_NO = "
				+ JQueryConvert.queryConvert(daikouNumber));

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (result == null || result.size() == 0) {
			JErrorMessage.show("M5101", this);

		}else {
			resultItem = result.get(0);

			String number = resultItem.get("SHIHARAI_DAIKO_NO");
			String daikouName = resultItem.get("SHIHARAI_DAIKO_NAME");
			String zip = resultItem.get("SHIHARAI_DAIKO_ZIPCD");
			String address = resultItem.get("SHIHARAI_DAIKO_ADR");
			String tel = resultItem.get("SHIHARAI_DAIKO_TEL");

			jTextField_DaikouNumber.setText(number);
			jTextField_DaikouName.setText(daikouName);

			if (zip != null && ! zip.isEmpty()) {
				jTextField_Zipcode.setText(zip.trim());
			}

			jTextField_Address.setText(address);
			jTextField_TEL.setText(tel);
		}

		mode = CHANGE_MODE;

		// del s.inoue 2009/12/02
//		this.jTextField_DaikouName.grabFocus();
		initializefocus();
		// edit s.inoue 2009/12/02
		functionListner();
	}
	private void initializefocus(){
		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		// edit s.inoue 2009/12/02
		if (mode == CHANGE_MODE){
			this.focusTraversalPolicy.setDefaultComponent(this.jTextField_DaikouName);
		}else if (mode == ADD_MODE){
			this.focusTraversalPolicy.setDefaultComponent(this.jTextField_DaikouNumber);
		}
		this.focusTraversalPolicy.addComponent(this.jTextField_DaikouNumber);
		this.focusTraversalPolicy.addComponent(this.jTextField_DaikouName);
		this.focusTraversalPolicy.addComponent(this.jTextField_Zipcode);
		this.focusTraversalPolicy.addComponent(this.jTextField_Address);
		this.focusTraversalPolicy.addComponent(this.jTextField_TEL);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_Clear);
		this.focusTraversalPolicy.addComponent(jButton_End);

	}

	// edit s.inoue 2009/12/02
	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * 印刷時の必須項目をチェックする
	 */
	public boolean validateAsPushedORCAButton(
			JShiharaiMasterMaintenanceEditFrameData data) {
		if (data.getDaikouNumber().isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * 登録ボタンを押した際の必須項目の検証
	 */
	protected boolean validateAsRegisterPushed(
			JShiharaiMasterMaintenanceEditFrameData data) {
		if (data.getDaikouNumber().isEmpty()) {
			JErrorMessage.show("M5010", null);
			return false;
		}

		validatedData.setValidationAsDataSet();
		return true;
	}

	private static String lastAddedNumber = null;

	public static String getLastAddedNumber() {
		return lastAddedNumber;
	}

	private static boolean registered = false;
	public static boolean isRegistered() {
		return registered;
	}

	/**
	 * 登録処理を行う
	 */
	public void register() {
		if (!this.validateData()) {
			return;
		}

		// 各フィールドについて検証済み
		if (!validateAsRegisterPushed(validatedData)) {
			return;
		}

		// 必須項目についても抜けがない
		if (!validatedData.isValidationAsDataSet()) {
			return;
		}

		String inputDaikouNumber = validatedData.getDaikouNumber();

		String sql = "select count(SHIHARAI_DAIKO_NO) as ROWCOUNT from T_SHIHARAI where SHIHARAI_DAIKO_NO = ?";
		String[] params = { inputDaikouNumber };

		ArrayList<Hashtable<String, String>> result = null;
		try{
			result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		}catch(SQLException e){
			e.printStackTrace();
		}

		boolean existsNumber = false;
		if (result != null && result.size() > 0) {
			String countString = result.get(0).get("ROWCOUNT");

			int count = 0;
			try {
				count = Integer.valueOf(countString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			if (count > 0) {
				existsNumber = true;
			}
		}

		if (mode == ADD_MODE && existsNumber) {
			JErrorMessage.show("M5104", this);
			return;
		}

		StringBuffer buffer = new StringBuffer();
		// edit s.inoue 2009/10/22
		String address = JValidate.encodeUNICODEtoConvert(validatedData.getAddress());

		if (mode == ADD_MODE) {
			buffer.append("INSERT INTO T_SHIHARAI ( ");
			buffer.append("SHIHARAI_DAIKO_NO,");
			buffer.append("SHIHARAI_DAIKO_NAME," );
			buffer.append("SHIHARAI_DAIKO_ZIPCD, " );
			buffer.append("SHIHARAI_DAIKO_ADR, " );
			buffer.append("SHIHARAI_DAIKO_TEL " );
			buffer.append(")");

			buffer.append("VALUES ( "
					+ JQueryConvert.queryConvertAppendComma(inputDaikouNumber)
					+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getDaikouName())
					+ JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode())
					+ JQueryConvert.queryConvertAppendBlankAndComma(address)
					+ JQueryConvert.queryConvert(validatedData.getTEL())
					+ ")");
		}

		if (mode == CHANGE_MODE) {
			buffer.append("UPDATE T_SHIHARAI SET ");
			buffer.append("SHIHARAI_DAIKO_NO = ");
			buffer.append(JQueryConvert.queryConvertAppendComma(validatedData.getDaikouNumber()));
			buffer.append("SHIHARAI_DAIKO_NAME = ");
			buffer.append(JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getDaikouName()));
			buffer.append("SHIHARAI_DAIKO_ZIPCD = ");
			buffer.append(JQueryConvert.queryConvertAppendBlankAndComma(validatedData.getZipcode()));
			buffer.append("SHIHARAI_DAIKO_ADR = ");
			buffer.append(JQueryConvert.queryConvertAppendBlankAndComma(address));
			buffer.append("SHIHARAI_DAIKO_TEL = ");
			buffer.append(JQueryConvert	.queryConvert(validatedData.getTEL()));
			buffer.append("WHERE SHIHARAI_DAIKO_NO = ");
			buffer.append(JQueryConvert.queryConvert(validatedData.getDaikouNumber()));
		}
		try {
			JApplication.kikanDatabase.Transaction();
			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
			JApplication.kikanDatabase.Commit();

			registered = true;
			lastAddedNumber = validatedData.getDaikouNumber();

			dispose();
		} catch (SQLException f) {
			f.printStackTrace();

			JErrorMessage.show("M5020", this);

			try {
				JApplication.kikanDatabase.rollback();
				return;
			} catch (SQLException g) {
				g.printStackTrace();
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
	}

	private boolean validateData() {

		// edit s.inoue 2010/04/27
		String address = jTextField_Address.getText();
		if (!JValidate.isAllZenkaku(address)){
			address = JZenkakuKatakanaToHankakuKatakana.eisukigoHanToZen(address);
			address = JValidate.encodeUNICODEtoConvert(address);
		}
		return validatedData.setAddress(address)
				&& validatedData.setDaikouName(jTextField_DaikouName.getText())
				&& validatedData.setDaikouNumber(jTextField_DaikouNumber.getText())
				&& validatedData.setTEL(jTextField_TEL.getText())
				&& validatedData.setZipcode(jTextField_Zipcode.getText());
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton(ActionEvent e) {
		register();
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * キャンセルボタン
	 */
	public void pushedCancelButton(ActionEvent e) {
		RETURN_VALUE Ret = JErrorMessage.show("M5032", this);

		if (Ret == RETURN_VALUE.YES) {
			dispose();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);
		}
		else if (source == jButton_Cancel) {
			logger.info(jButton_Cancel.getText());
			pushedCancelButton(e);
		}
		else if (source == jButton_Clear) {
			logger.info(jButton_Clear.getText());
			crearControl();
		}
	}

	// edit s.inoue 2010/04/05
	@Override
	public void focusLost(FocusEvent event) {
		Object source = event.getSource();

		// add s.inoue 2010/03/29
		if(source.equals(this.jTextField_Zipcode)){
			if (jTextField_Zipcode.getText().equals(""))
				return;

			String zipText = setZipCodeAddress(this.jTextField_Zipcode.getText());
			if (zipText.equals(""))
				return;

			jTextField_Address.setText(zipText);
		}
	}

	// edit s.inoue 2010/04/05
	private String setZipCodeAddress(String zipCode){

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT ADDRESS,POST_CD FROM T_POST ");
		// eidt s.inoue 2012/04/24
//		sb.append("(SELECT SUBSTRING(POST_CD FROM 1 FOR 3) || SUBSTRING(POST_CD FROM 5 FOR 4) POSTCD,");
//		sb.append(" POST_CD SP_POSTCD FROM T_POST) SUB_POST");
//		sb.append(" where SUB_POST.POSTCD = ");
		sb.append(" where POST_CD = ");
		sb.append(JQueryConvert.queryConvert(zipCode));
//		sb.append(" and SUB_POST.SP_POSTCD = T_POST.POST_CD ");

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

	// edit s.inoue 2009/12/02
	private void crearControl(){
		if (mode == ADD_MODE) {
			jTextField_DaikouNumber.setText("");
		}

		jTextField_DaikouName.setText("");
		jTextField_Zipcode.setText("");
		jTextField_Address.setText("");
		jTextField_TEL.setText("");
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F2:
			logger.info(jButton_Clear.getText());
			crearControl();break;
		case KeyEvent.VK_F12:
			pushedRegisterButton(null);
			logger.info(jButton_Register.getText());
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

