package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

public class JRegisterUserFrameCtrl extends JRegisterUserFrame {
	static final int ADD_MODE = 1;
	static final int CHANGE_MODE = 2;

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JRegisterUserFrameCtrl.class);

	/**
	 * 実行しているフレームのモード １：ユーザの追加 ２：ユーザの変更
	 */
	int mode;

	JRegisterUserFrameData validatedData = new JRegisterUserFrameData();

	/**
	 * 登録ボタンを押した際の必須項目を検証
	 */
	public boolean validateAsRegisterPushed(JRegisterUserFrameData data) {
		if (data.getKengen().equals("")) {
			JErrorMessage.show("M4801", this);
			return false;
		}

		if (data.getPassword().equals("")) {
			JErrorMessage.show("M4802", this);
			return false;
		}

		if (data.getUserName().equals("")) {
			JErrorMessage.show("M4803", this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * 新規ユーザ追加時のコンストラクタ
	 */
	public JRegisterUserFrameCtrl() {
		super();
		mode = ADD_MODE;
		init();
	}

	/**
	 * ユーザ情報変更時のコンストラクタ
	 */
	public JRegisterUserFrameCtrl(String userName) {
		mode = CHANGE_MODE;
		init();

		jTextField_UserName.setText(userName);
		jTextField_UserName.setEditable(false);

		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from t_user where user_name = ?");
		String query = buffer.toString();

		String[] params = { userName };

		ArrayList<Hashtable<String, String>> result = null;
		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(query, params);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (result != null && ! result.isEmpty()) {
			Hashtable<String, String> item = result.get(0);
			String dbPassword = item.get("PASS");
			String dbKengen = item.get("KENGEN");

			/* 権限を設定する。 */
			String kengenItem = null;
			if (dbKengen.equals("1")) {
				kengenItem = JValidate.USER_KENGEN_KANRISYA;
			}
			else {
				kengenItem = JValidate.USER_KENGEN_IPANUSER;
			}

			/* パスワードを設定する。 */
			this.jPasswordField_Password.setText(dbPassword);
			this.jComboBox_Grant.setSelectedItem(kengenItem);
		}
		// add s.inoue 2009/12/07
		// focus初期設定
		this.addComponentListener(new ComponentAdapter()
		{
		    public void componentShown(ComponentEvent e)
		    {
		        // ensure password text field has focus initially
		    	jPasswordField_Password.requestFocusInWindow();
		    }
		});

	}

	/**
	 * 初期化
	 */
	public void init() {
		jButton_Register.setEnabled(false);

		jComboBox_Grant.addItem("一般ユーザ");
		jComboBox_Grant.addItem("管理者");

		// ログインボタンの状態を管理
		jTextField_UserName.getDocument().addDocumentListener(new LoginButtonStatus());
		jPasswordField_Password.getDocument().addDocumentListener(new LoginButtonStatus());
		// edit s.inoue 2010/03/24
		functionListner();
	}

	private void functionListner(){

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		// edit s.inoue 2010/03/24
		if (mode == ADD_MODE) {
			this.focusTraversalPolicy.setDefaultComponent(this.jTextField_UserName);
			this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
			this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		}else if (mode == CHANGE_MODE){
			this.focusTraversalPolicy.setDefaultComponent(this.jPasswordField_Password);
			this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		}
		// del s.inoue 2010/03/24
		// this.focusTraversalPolicy.addComponent(this.jTextField_UserName);
		// this.focusTraversalPolicy.addComponent(this.jPasswordField_Password);
		this.focusTraversalPolicy.addComponent(this.jComboBox_Grant);
		this.focusTraversalPolicy.addComponent(this.jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		// add s.inoue 2009/12/02
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		dispose();
	}

	/**
	 * 登録ボタンを押下
	 */
	public void pushedRegisterButton(ActionEvent e) {
		if (validatedData.setUserName(jTextField_UserName.getText())
				&& validatedData.setPassword(new String(jPasswordField_Password
						.getPassword()))
				&& validatedData.setKengen((String) jComboBox_Grant
						.getSelectedItem())) {
			if (validateAsRegisterPushed(validatedData)) {

				if (validatedData.isValidateAsDataSet()) {

					String Query = null;

					// 新規ユーザ追加の場合のクエリ
					if (mode == ADD_MODE) {
						// add s.inoue 20081217
						// ユーザ名称の重複チェック
						try
						{
							ArrayList kikanItem =  JApplication.kikanDatabase.sendExecuteQuery(
									"SELECT * FROM T_USER WHERE USER_NAME =" +
									JQueryConvert.queryConvert(validatedData.getUserName()));

							if (kikanItem.size() > 0){
								JErrorMessage.show("M9638", this);
								return;
							}

						}catch(Exception err){
							err.printStackTrace();
							JErrorMessage.show("M9601", this);
							return;
						}

						Query = new String(
								"INSERT INTO T_USER (USER_NAME,PASS,KENGEN) VALUES ( "
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getUserName())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getPassword())
										+ JQueryConvert
												.queryConvert(validatedData
														.getKengen()) + ")");
					}

					// 既存ユーザ情報変更の場合のクエリ
					if (mode == CHANGE_MODE) {
						Query = new String("UPDATE T_USER SET USER_NAME = "
								+ JQueryConvert
										.queryConvertAppendComma(validatedData
												.getUserName())
								+ "PASS = "
								+ JQueryConvert
										.queryConvertAppendComma(validatedData
												.getPassword())
								+ "KENGEN = "
								+ JQueryConvert.queryConvert(validatedData
										.getKengen())
								+ "WHERE USER_NAME = "
								+ JQueryConvert.queryConvert(validatedData
										.getUserName()));
					}

					try {
						JApplication.kikanDatabase.sendNoResultQuery(Query);
						dispose();

					} catch (SQLException f) {
						f.printStackTrace();
					}
				}
			}
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
		// エンターキーでの移動
		else if (source == jTextField_UserName) {
			jTextField_UserName.transferFocus();
		}
		else if (source == jPasswordField_Password) {
			jPasswordField_Password.transferFocus();
		}
// del s.inoue 2009/12/04
//		else if (source == jComboBox_Grant) {
//			// 登録ボタンが有効であったら処理を行う
//			if (jButton_Register.isEnabled()) {
//				pushedRegisterButton(null);
//			}
//		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			if (jButton_Register.isEnabled()){
				logger.info(jButton_Register.getText());
				pushedRegisterButton(null);break;
			}
		}
	}

	/**
	 * 登録ボタンのステータスをテキストフィールドの内容によって 変更するためのイベントリスナ
	 */
	public class LoginButtonStatus implements DocumentListener {
		public void changedUpdate(DocumentEvent arg0) {
		}

		public void insertUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}

		public void removeUpdate(DocumentEvent e) {
			setLoginButtonStatus(e);
		}
	}

	/**
	 * 登録ボタンのステータスチェックを行う。 LoginButtonStatusイベントから呼び出すための関数。
	 */
	public void setLoginButtonStatus(DocumentEvent e) {
		// ログインボタンの有効化
		if (e.getDocument() == jTextField_UserName.getDocument()
				|| e.getDocument() == jPasswordField_Password.getDocument()) {
			if (jTextField_UserName.getText().length() > 0
					&& (new String(jPasswordField_Password.getPassword()))
							.length() > 0) {
				jButton_Register.setEnabled(true);
			} else {
				jButton_Register.setEnabled(false);
			}
		}
	}
}

// Source Code Version Tag System v1.00 -- Do not remove --
// Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

