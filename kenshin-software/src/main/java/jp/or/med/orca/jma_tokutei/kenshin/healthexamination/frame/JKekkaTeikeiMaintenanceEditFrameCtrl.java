package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.JNayoseMaintenanceListFrameCtl.WindowRefreshEvent;

/**
 * 定型文マスタの編集のコントロール
 */
public class JKekkaTeikeiMaintenanceEditFrameCtrl extends
		JKekkaTeikeiMaintenanceEditFrame {
	private static final int ADD_MODE = 1;
	private static final int CHANGE_MODE = 2;

	private String selectpreCombo ="";
	// edit s.inoue 2010/05/19
	private String preInputSyokenNo = "";

	private JKekkaTeikeiMaintenanceEditFrameData validatedData = new JKekkaTeikeiMaintenanceEditFrameData();
	private int mode;

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	/* Enter キー押下でフォーカスを移動するコンポーネント */
	private List<Component> enterkeyFocusComponents = null;

	private static org.apache.log4j.Logger logger = Logger.getLogger(JKekkaTeikeiMaintenanceEditFrameCtrl.class);

	/**
	 * 保険者情報追加の場合のコンストラクタ（遷移元：マスタメンテナンス）
	 */
	public JKekkaTeikeiMaintenanceEditFrameCtrl(String maxShubetuNo) {
		super();
		mode = ADD_MODE;
		init(true);

		// edit s.inoue 2010/05/18
		jTextField_ShubetuNumber.setText(maxShubetuNo);
		jTextField_TeikeibunNo.setText("01");
//		jCombobox_TeikeibunType.setVisible(false);

		// del s.inoue 2010/05/18
		// initializeComboBox();
	}

	/**
	 * 定型分変更の場合のコンストラクタ（遷移元：マスタメンテナンス）
	 */
	public JKekkaTeikeiMaintenanceEditFrameCtrl(String shubetuNo,String syokenNo) {
		super();
		mode = CHANGE_MODE;
		init(false);

		// 遷移元フレームから得た定型Noから既存のデータを取得
		jTextArea_Teikeibun.setText(syokenNo);
		// edit s.inoue 2010/05/19
		preInputSyokenNo = syokenNo;

		ArrayList<Hashtable<String, String>> result = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> resultItem = new Hashtable<String, String>();

		StringBuilder buffer = new StringBuilder();
		buffer.append("SELECT SYOKEN_TYPE,SYOKEN_TYPE_NAME,");
		buffer.append(" SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP");
		buffer.append(" FROM T_SYOKENMASTER WHERE SYOKEN_TYPE = ");
		buffer.append(JQueryConvert.queryConvert(shubetuNo));
		buffer.append(" AND SYOKEN_NO = ");
		buffer.append(JQueryConvert.queryConvert(syokenNo));

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		resultItem = result.get(0);

		selectpreCombo = resultItem.get("SYOKEN_TYPE").toString();
// del s.inoue 2010/05/18
//		setSyokenCombobox(resultItem.get("SYOKEN_TYPE_STR").toString());
		jTextField_ShubetuNumber.setText(shubetuNo);
		jTextField_TeikeibunNo.setText(resultItem.get("SYOKEN_NO").trim());
		jTextArea_Teikeibun.setText(resultItem.get("SYOKEN_NAME"));
		// edit s.inoue 2010/05/18
		jTextField_TeikeibunType.setText(resultItem.get("SYOKEN_TYPE_NAME"));
//		jCombobox_TeikeibunType.setVisible(false);

		// move s.inoue 2010/05/18
//		initializeComboBox();

		// del s.inoue 2009/12/15
		// jTextField_TeikeibunNo.setEditable(false);
		// jCombobox_TeikeibunType.setEnabled(false);
	}

//	private void setSyokenCombobox(String syokencmb){
//		switch (Integer.valueOf(syokencmb)) {
//		case 1:
//			jCombobox_TeikeibunType.setSelectedIndex(0);
//			break;
//		case 2:
//			jCombobox_TeikeibunType.setSelectedIndex(1);
//			break;
//		case 3:
//			jCombobox_TeikeibunType.setSelectedIndex(2);
//			break;
//		case 4:
//			jCombobox_TeikeibunType.setSelectedIndex(3);
//			break;
//		}
//	}

// edit s.inoue 2010/05/18
//	private void initializeComboBox() {
//		// 所見種別設定
//		jCombobox_TeikeibunType.addItem("その他の既往歴");
//		jCombobox_TeikeibunType.addItem("自覚症状所見");
//		jCombobox_TeikeibunType.addItem("他覚症状所見");
//		jCombobox_TeikeibunType.addItem("心電図所見");
//	}

//	private void initializeComboBox() {
//
//		ArrayList<Hashtable<String, String>> result
//			= new ArrayList<Hashtable<String, String>>();
//		Hashtable<String, String> resultItem = new Hashtable<String, String>();
//
//		StringBuilder buffer = new StringBuilder();
//
//		buffer.append(" SELECT distinct SYOKEN_TYPE, SYOKEN_TYPE_NAME");
//		buffer.append(" FROM T_SYOKENMASTER");
//
//		try {
//			result = JApplication.kikanDatabase.sendExecuteQuery(buffer.toString());
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			resultItem = result.get(i);
//			String selectType = resultItem.get("SYOKEN_TYPE").toString() +
//			":" + resultItem.get("SYOKEN_TYPE_NAME").toString();
//
//			this.jCombobox_TeikeibunType.addItem(selectType);
//		}
//
//	}

	/**
	 * 印刷時の必須項目をチェックする
	 */
	public boolean validateAsPushedORCAButton(
			JHokenjyaMasterMaintenanceEditFrameData data) {
		if (data.getHokenjyaNumber().isEmpty()) {
			return false;
		}

		return true;
	}

	// edit s.inoue 2009/12/15
	// マウスリスナのメソッドを定義
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}
	private void mousePopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			// ポップアップメニューを表示する
			JComponent c = (JComponent)e.getSource();
			jTextArea_Teikeibun.showPopup(c, e.getX(), e.getY());
			e.consume();
		}
	}

	/**
	 * 初期化
	 */
	private void init(boolean initFlg) {

		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);

		// edit s.inoue 2010/05/18
		if (initFlg){
			this.focusTraversalPolicy.setDefaultComponent(jTextField_TeikeibunType);
			this.focusTraversalPolicy.addComponent(jTextField_TeikeibunType);
			jTextField_TeikeibunType.setBackground(ViewSettings.getRequiedItemBgColor());
//			jTextField_ShubetuNumber.setBackground(ViewSettings.getRequiedItemBgColor());
			jButton_Add.setVisible(false);
			jTextField_TeikeibunNo.setEditable(false);
			jTextField_ShubetuNumber.setEditable(false);
		}else{
			this.focusTraversalPolicy.setDefaultComponent(jTextField_TeikeibunNo);
			this.focusTraversalPolicy.addComponent(jTextField_TeikeibunNo);
			jTextField_ShubetuNumber.setEditable(false);
			jTextField_TeikeibunType.setEditable(false);
			jTextField_TeikeibunNo.setBackground(ViewSettings.getRequiedItemBgColor());
		}

		this.focusTraversalPolicy.addComponent(jTextArea_Teikeibun);
		this.focusTraversalPolicy.addComponent(jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/03
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}

		// del s.inoue 2010/05/19
//		jCombobox_TeikeibunType.setBackground(ViewSettings.getRequiedItemBgColor());
//		jTextField_TeikeibunNo.setBackground(ViewSettings.getRequiedItemBgColor());
	}

// edit s.inoue 2009/12/14
// 所見番号はコード扱いの為、重複チェックのみとする
//	/**
//	 * 定型文番号の空き番号の取得
//	 */
//	private int getNextNumber() {
//		ArrayList<Hashtable<String, String>> Items;
//		try {
//			Items = JApplication.kikanDatabase
//					.sendExecuteQuery("SELECT SYOKEN_NO FROM T_SYOKENMASTER");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			JErrorMessage.show("M9904", this);
//			return -1;
//		}
//
//		for (int i = 1; i < Integer.MAX_VALUE; i++) {
//			boolean FindFlag = false;
//
//			for (int j = 0; j < Items.size(); j++) {
//				if (Items.get(j).get("SYOKEN_NO").equals(String.valueOf(i))) {
//					FindFlag = true;
//				}
//			}
//
//			// trueでなければ空きがあるとする。
//			if (FindFlag == false) {
//				return i;
//			}
//		}
//
//		return -1;
//	}

	private static String lastAddedNumber = null;

	public static String getLastAddedNumber() {
		return lastAddedNumber;
	}

	private static boolean registered = false;
	public static boolean isRegistered() {
		return registered;
	}

	/* 入力値検証 */
	private boolean validateData() {
		// edit s.inoue 2010/05/19
//		String syokenIdx = String.valueOf(jCombobox_TeikeibunType.getSelectedIndex() + 1);
		return validatedData.setTeikeiType(jTextField_ShubetuNumber.getText())
				&& validatedData.setTeikeiTypeName(jTextField_TeikeibunType.getText())
				&& validatedData.setTeikeiNumber(jTextField_TeikeibunNo.getText())
				&& validatedData.setTeikeibun(jTextArea_Teikeibun.getText());
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
		if (!validatedData.isValidateAsDataSet()) {
			return;
		}
		String inputTeikeiShubetu = validatedData.getTeikeiType();
		String inputTeikeiNumber = validatedData.getTeikeiNumber();

		String sql = "select count(SYOKEN_NO) as ROWCOUNT from T_SYOKENMASTER where SYOKEN_TYPE = ? and SYOKEN_NO = ?";
		String[] params = { inputTeikeiShubetu,inputTeikeiNumber };

		ArrayList<Hashtable<String, String>> result = null;
		try{
			result = JApplication.kikanDatabase.sendExecuteQuery(sql, params);
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		boolean existsNumber = false;
		if (result != null && result.size() > 0) {
			String countString = result.get(0).get("ROWCOUNT");

			int count = 0;
			try {
				count = Integer.valueOf(countString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			// edit s.inoue 2010/05/19
			if (count == 0){
				mode = ADD_MODE;
			}else if (count > 0) {
				// edit s.inoue 2010/05/19
				if (!preInputSyokenNo.equals(jTextField_TeikeibunNo.getText())){
					RETURN_VALUE retValue = JErrorMessage.show("M9922", this);
					if (retValue != RETURN_VALUE.YES) {
						return;
					}
				}
					existsNumber = true;
			}
		}

		if (mode == ADD_MODE && existsNumber){
			JErrorMessage.show("M9905", this);
			return;
		}

		StringBuffer buffer = new StringBuffer();
		// 現時刻を取得
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String curtimeStamp = dateFormat.format(Calendar.getInstance().getTime());

		if (mode == ADD_MODE) {
			buffer.append("INSERT INTO T_SYOKENMASTER (SYOKEN_TYPE, SYOKEN_TYPE_NAME, SYOKEN_NO, SYOKEN_NAME, UPDATE_TIMESTAMP) ");

			buffer.append("VALUES ( "
							+ JQueryConvert.queryConvertAppendComma(inputTeikeiShubetu)
							+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiTypeName())
							+ JQueryConvert.queryConvertAppendComma(inputTeikeiNumber)
							+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun())
							+ JQueryConvert.queryConvert(curtimeStamp)
							+ ")");
		}

		if (mode == CHANGE_MODE) {
			buffer.append("UPDATE T_SYOKENMASTER SET ");
			// del s.inoue 2010/05/19
			// buffer.append(" SYOKEN_TYPE = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiType()));
			buffer.append(" SYOKEN_NO = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeiNumber()));
			buffer.append(" SYOKEN_NAME = "+ JQueryConvert.queryConvertAppendComma(validatedData.getTeikeibun()));
			buffer.append(" UPDATE_TIMESTAMP = " + JQueryConvert.queryConvert(curtimeStamp));
			buffer.append(" WHERE SYOKEN_TYPE = "+ JQueryConvert.queryConvert(selectpreCombo));
			buffer.append(" AND SYOKEN_NO = "+ JQueryConvert.queryConvert(validatedData.getTeikeiNumber()));
		}

		try {
			JApplication.kikanDatabase.Transaction();
			JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());
			JApplication.kikanDatabase.Commit();
			registered = true;
			// del s.inoue 2009/12/15
			// lastAddedNumber = validatedData.getTeikeiNumber();
			dispose();
		} catch (SQLException f) {
			f.printStackTrace();
			JErrorMessage.show("M9910", this);
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

	/**
	 * 所見Noの空き番号の取得
	 */
	private String getNextSyokenNumber(String shubetuNo) {
		ArrayList<Hashtable<String, String>> Items;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT SYOKEN_NO FROM T_SYOKENMASTER ");
			sb.append("WHERE SYOKEN_TYPE = ");
			sb.append(JQueryConvert.queryConvert(shubetuNo));
			Items = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M9919", this);
			return null;
		}

		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			boolean FindFlag = false;

			for (int j = 0; j < Items.size(); j++) {
				String strVal = String.valueOf(i);
				strVal = (strVal.length() == 1)? "0" + strVal: strVal;
				if (Items.get(j).get("SYOKEN_NO").equals(strVal)) {
					FindFlag = true;
				}
			}

			// trueでなければ空きがあるとする。
			if (FindFlag == false) {
				// edit s.inoue 2010/05/19
				String ret = String.valueOf(i);
				ret = (ret.length() == 1)?"0" + ret:ret;
				return ret;
			}
		}

		return null;
	}

	/**
	 * 登録ボタンを押した際の必須項目の検証
	 */
	protected boolean validateAsRegisterPushed(
			JKekkaTeikeiMaintenanceEditFrameData data) {
		if (data.getTeikeiNumber().equals("")) {
			JErrorMessage.show("M9902", null);
			return false;
		}

		validatedData.setValidateAsDataSet();
		return true;
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton(ActionEvent e) {
		register();
	}

	/**
	 * 追加ボタン
	 */
	public void pushedAddButton(ActionEvent e) {
		jTextField_TeikeibunNo.setText(getNextSyokenNumber(this.jTextField_ShubetuNumber.getText()));
		jTextArea_Teikeibun.setText("");
		jTextArea_Teikeibun.grabFocus();
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter
	{
		public void windowClosed(WindowEvent e)
		{
			//テーブルの再読み込みを行う
			init(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == jButton_End) {
			logger.info(jButton_End.getText());
			pushedEndButton(e);
		}
		// edit s.inoue 2010/05/18
		else if (source == jButton_Add) {
			logger.info(jButton_Add.getText());
			pushedAddButton(e);
		}
		else if (source == jButton_Register) {
			logger.info(jButton_Register.getText());
			pushedRegisterButton(e);
		}
	}
	// add s.inoue 2009/12/03
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F9:
			logger.info(jButton_Add.getText());
			pushedAddButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Register.getText());
			pushedRegisterButton(null);break;
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

