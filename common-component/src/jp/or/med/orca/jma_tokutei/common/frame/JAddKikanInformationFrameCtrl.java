package jp.or.med.orca.jma_tokutei.common.frame;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.orca.JORCASetting;
import jp.or.med.orca.jma_tokutei.common.orca.JOrcaInfoSearchCtl;
import jp.or.med.orca.jma_tokutei.common.sql.*;
import jp.or.med.orca.jma_tokutei.common.validate.JValidate;

/**
 * 機関情報の追加フレームのコントロール
 */
public class JAddKikanInformationFrameCtrl extends JAddKikanInformationFrame {
	protected JConnection systemDatabase = null;
	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;
	private static Logger logger = Logger.getLogger(JAddKikanInformationFrameCtrl.class);
	protected JAddKikanInformationFrameData validatedData = new JAddKikanInformationFrameData();
	private ArrayList<JTextField> inputFields = null;

// eidt s.inoue 2012/07/05
//	/* 必須項目のコンポーネント */
//	private JComponent[] requiredComponents = new JComponent[]{
//			jTextField_kikanNumber,
//			jTextField_kikanName,
//			jTextField_souhumotoNumber,
//			jTextField_zip,
//			jTextField_address,
////			jTextField_address2,
//			jTextField_tel,
//
////			jTextField_ip,
////			jTextField_portNumber,
////			jTextField_databaseName,
////			jTextField_protocol,
////			jTextField_dbUserId,
////			jTextField_dbPassword,
////			jTextField_nichireseUserId,
////			jTextField_nichiresePassword,
////			jTextField_encoding,
//	};
//
//	/* 日レセのコンポーネント */
//	private JComponent[] nichireseComponents = new JComponent[]{
//            jTextField_ip, jTextField_portNumber, jTextField_nichireseUserId,
//            jTextField_nichiresePassword, jRadioButton_Yes1, jRadioButton_No1,
//            jTextField_orcaIdDigit
//    };
//
//	/* 日レセのコンポーネントで必須のもの */
//	private JComponent[] nichireseRequiredComponents = new JComponent[]{
//			jTextField_ip,
//			jTextField_portNumber,
//			jTextField_databaseName,
//			jTextField_protocol,
//			jTextField_dbUserId,
////			jTextField_dbPassword,
//			jTextField_nichireseUserId,
////			jTextField_nichiresePassword,
//			jTextField_encoding,
//	};

	/* 必須項目のコンポーネント */
	private JComponent[] requiredComponents = new JComponent[] {
            jTextField_kikanNumber, jTextField_kikanName,
            jTextField_souhumotoNumber, jTextField_zip,
            jTextField_address, jTextField_tel
    };
    /* 日レセのコンポーネント */
    private JComponent[] nichireseComponents = new JComponent[] {
            jTextField_ip, jTextField_portNumber,
            jTextField_nichireseUserId, jTextField_nichiresePassword,
            jRadioButton_Yes1, jRadioButton_No1, jTextField_orcaIdDigit
    };
    /* 日レセのコンポーネントで必須のもの */
    private JComponent[] nichireseRequiredComponents = new JComponent[] {
            jTextField_ip, jTextField_portNumber,
            jTextField_nichireseUserId, jTextField_nichiresePassword
    };


	/**
	 * @param SystemDatabase
	 *            接続するシステムデータベース
	 */
	// edit s.inoue 2009/10/15
	public JAddKikanInformationFrameCtrl(JConnection SystemDatabase,boolean addFlg) {
		super();
		this.systemDatabase = SystemDatabase;
// del s.inoue 2012/07/05
//		this.inputFields = new ArrayList<JTextField>();
//		inputFields.add(this.jTextField_kikanNumber);
//		inputFields.add(this.jTextField_souhumotoNumber);
//		inputFields.add(this.jTextField_kikanName);
//		inputFields.add(this.jTextField_zip);
//		inputFields.add(this.jTextField_address);
//		inputFields.add(this.jTextField_address2);
//		inputFields.add(this.jTextField_tel);
//		inputFields.add(this.jTextField_ip);
//		inputFields.add(this.jTextField_portNumber);
//		inputFields.add(this.jTextField_databaseName);
//		inputFields.add(this.jTextField_protocol);
//		inputFields.add(this.jTextField_dbUserId);
//		inputFields.add(this.jTextField_dbPassword);
//		inputFields.add(this.jTextField_nichireseUserId);
//		inputFields.add(this.jTextField_nichiresePassword);
//		inputFields.add(this.jTextField_encoding);

		jRadioButton_No.setSelected(true);
		jRadioButton_No1.setSelected(true);

		/* 機関番号入力欄がフォーカスを失ったとき、
		 * 入力値を送付元機関番号にコピーする。 */
		this.jTextField_kikanNumber.addFocusListener(new FocusAdapter(){
			@Override
			public void focusLost(FocusEvent arg0) {
				/* フォーカスを失ったとき、入力値を送付元機関番号にコピーする。 */
				// edit s.inoue 20081216 新規時のみに値をセットする
				if (jTextField_souhumotoNumber.getText().equals("")){
					String kikanNumber = JAddKikanInformationFrameCtrl.this.jTextField_kikanNumber.getText();
					JAddKikanInformationFrameCtrl.this.jTextField_souhumotoNumber.setText(kikanNumber);
				}
			}
		});

		// eidt s.inoue 2012/07/05
        if(addFlg)
        {
            jTextField_kikanNumber.grabFocus();
            jLabel_kikanNumber.setForeground(ViewSettings.getRequiedItemFrColor());
        } else
        {
            jTextField_souhumotoNumber.grabFocus();
        }
        // eidt s.inoue 2012/11/19
        jLabel_souhumotoNumber.setForeground(ViewSettings.getRequiedItemFrColor());
    	jLabel_name.setForeground(ViewSettings.getRequiedItemFrColor());
    	jLabel_address.setForeground(ViewSettings.getRequiedItemFrColor());
    	jLabel_zip.setForeground(ViewSettings.getRequiedItemFrColor());
//    	jLabel_address2.setForeground(ViewSettings.getRequiedItemFrColor());
    	jLabel_tel.setForeground(ViewSettings.getRequiedItemFrColor());

// del s.inoue 2012/07/10
//		// add s.inoue 2012/07/09
//		jButton_ConnectionTest.addKeyListener(new KeyListener() {
//			@Override
//			public void keyTyped(KeyEvent e) {}
//			@Override
//			public void keyReleased(KeyEvent e) {}
//			@Override
//			public void keyPressed(KeyEvent e) {
//				logger.info(jButton_ConnectionTest.getText());
//
//					// eidt s.inoue 2012/07/09
//				if (KeyEvent.VK_ENTER != e.getKeyCode()) return;
//					int mod = e.getModifiersEx();
//					  if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0){
//					    jTextField_orcaIdDigit.grabFocus();
//					  }else{
//							pushedConnectionTestButton();
//					  }
//				}
//		});

//		// edit s.inoue 2009/09/30
//		// フォーカス制御
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		// add or edit
//		if (addFlg) {
//			this.focusTraversalPolicy.setDefaultComponent(jTextField_kikanNumber);
//		}else{
//			this.focusTraversalPolicy.setDefaultComponent(jTextField_souhumotoNumber);
//		}

//		this.focusTraversalPolicy.addComponent(jTextField_kikanNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_souhumotoNumber);
//		this.focusTraversalPolicy.addComponent(jTextField_kikanName);
//		this.focusTraversalPolicy.addComponent(jTextField_zip);
//		this.focusTraversalPolicy.addComponent(jTextField_address);
//		this.focusTraversalPolicy.addComponent(jTextField_address2);
//		this.focusTraversalPolicy.addComponent(jTextField_tel);
//		this.focusTraversalPolicy.addComponent(jRadioButton_Yes);
//		this.focusTraversalPolicy.addComponent(jRadioButton_No);
//		this.focusTraversalPolicy.addComponent(jTextField_ip);
//		this.focusTraversalPolicy.addComponent(jTextField_portNumber);
////		this.focusTraversalPolicy.addComponent(jTextField_databaseName);
////		this.focusTraversalPolicy.addComponent(jTextField_protocol);
////		this.focusTraversalPolicy.addComponent(jTextField_dbUserId);
////		this.focusTraversalPolicy.addComponent(jTextField_dbPassword);
//		this.focusTraversalPolicy.addComponent(jTextField_nichireseUserId);
//		this.focusTraversalPolicy.addComponent(jTextField_nichiresePassword );
//		this.focusTraversalPolicy.addComponent(jTextField_encoding);
//		this.focusTraversalPolicy.addComponent(jRadioButton_Yes1);
//		this.focusTraversalPolicy.addComponent(jRadioButton_No1);
//		this.focusTraversalPolicy.addComponent(jTextField_orcaIdDigit);
//		this.focusTraversalPolicy.addComponent(jButton_ConnectionTest);
//		this.focusTraversalPolicy.addComponent(jButton_Register);
//		this.focusTraversalPolicy.addComponent(jButton_End);

//		// add s.inoue 2009/12/03
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			Component comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}

//		Color requiedItemColor = ViewSettings.getRequiedItemBgColor();
//		jLabel_requiredExample.setBackground(requiedItemColor);
//
//		for (int i = 0; i < requiredComponents.length; i++) {
//			requiredComponents[i].setBackground(requiedItemColor);
//		}
	}

// eidt s.inoue 2012/07/05
//	protected boolean validateAsRegisterPushed(
//			JAddKikanInformationFrameData data) {
//		boolean useORCA = false;
//		if (jRadioButton_Yes.isSelected() == true) {
//			useORCA = true;
//		}
//
//		if (data.getKikanNumber().isEmpty() || data.getKikanName().isEmpty()
//				|| data.getZipcode().isEmpty() || data.getAddress().isEmpty()
//				|| data.getTel().isEmpty() || data.getORCA().isEmpty()) {
//			JErrorMessage.show(
//					"M9603", this);
//			return false;
//		}
//
//		// ORCAを使用するときの項目チェック
//		if (useORCA == true) {
//					// eidt s.inoue 2012/07/05
//			if (	// data.getORCADatabase().isEmpty()
//					// || data.getORCAEncode().isEmpty()
//					data.getORCAIpAddress().isEmpty()
//					||
//
//					data.getORCAPort().isEmpty()
//					// eidt s.inoue 2012/07/05
//					// || data.getORCAProtocol().isEmpty()
//					|| data.getORCAUser().isEmpty()
//
//					|| ( data.isUseOrcaIdDigit() && (
//							data.getOrcaIdDigit() == null || data.getOrcaIdDigit().isEmpty()) )
//
//			) {
//				JErrorMessage
//						.show("M9604", this);
//				return false;
//			}
//		}
//
//		data.setValidateAsDataSet();
//		return true;
//	}
	/**
	 * 登録ボタンを押した際の必須項目の検証
	 */
	protected boolean validateAsRegisterPushed(JAddKikanInformationFrameData jaddkikaninformationframedata)
	{
        boolean flag = false;
        if(jRadioButton_Yes.isSelected())
            flag = true;
        if(jaddkikaninformationframedata.getKikanNumber().isEmpty()
        		|| jaddkikaninformationframedata.getKikanName().isEmpty()
        		|| jaddkikaninformationframedata.getZipcode().isEmpty()
        		|| jaddkikaninformationframedata.getAddress().isEmpty()
        		|| jaddkikaninformationframedata.getTel().isEmpty()
        		|| jaddkikaninformationframedata.getORCA().isEmpty())
        {
            JErrorMessage.show("M9603", this);
            return false;
        }
        if(flag && (jaddkikaninformationframedata.getORCAIpAddress().isEmpty()
        		|| jaddkikaninformationframedata.getORCAPort().isEmpty()
        		|| jaddkikaninformationframedata.getORCANichireseUser().isEmpty()
        		|| jaddkikaninformationframedata.isUseOrcaIdDigit()
        		&& (jaddkikaninformationframedata.getOrcaIdDigit() == null
        				|| jaddkikaninformationframedata.getOrcaIdDigit().isEmpty())))
        {
            JErrorMessage.show("M9604", this);
            return false;
        } else
        {
            jaddkikaninformationframedata.setValidateAsDataSet();
            return true;
        }
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton(ActionEvent e) {
		logger.info(jButton_End.getText());
		dispose();
	}

	private boolean checkDegit(String JyushinkenID) {

		int i1 = Integer.parseInt(JyushinkenID.substring(0, 1)) * 2;
		int i2 = Integer.parseInt(JyushinkenID.substring(1, 2)) * 1;
		int i3 = Integer.parseInt(JyushinkenID.substring(2, 3)) * 2;
		int i4 = Integer.parseInt(JyushinkenID.substring(3, 4)) * 1;
		int i5 = Integer.parseInt(JyushinkenID.substring(4, 5)) * 2;
		int i6 = Integer.parseInt(JyushinkenID.substring(5, 6)) * 1;
		int i7 = Integer.parseInt(JyushinkenID.substring(6, 7)) * 2;
		int i8 = Integer.parseInt(JyushinkenID.substring(7, 8)) * 1;
		int i9 = Integer.parseInt(JyushinkenID.substring(8, 9)) * 2;

		i1 = this.checkKeta(i1);
		i2 = this.checkKeta(i2);
		i3 = this.checkKeta(i3);
		i4 = this.checkKeta(i4);
		i5 = this.checkKeta(i5);
		i6 = this.checkKeta(i6);
		i7 = this.checkKeta(i7);
		i8 = this.checkKeta(i8);
		i9 = this.checkKeta(i9);

		int yy = i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
		int zz = yy % 10;
		int xx = Integer.parseInt(JyushinkenID.substring(9, 10));
		zz = 10 - zz;

		if (zz == xx) {
			return true;
		}

		return false;
	}

	// 2桁の場合1桁目と2桁目を足す
	private int checkKeta(int a) {

		String s = String.valueOf(a);
		if (s.length() > 1) {
			a = Integer.parseInt(s.substring(0, 1))
					+ Integer.parseInt(s.substring(1, 2));
		}
		return a;
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton(ActionEvent e) {
		boolean useORCA = false;
		if (jRadioButton_Yes.isSelected() == true) {
			useORCA = true;
		}

		// 各入力項目が正当かどうか
		if (
				this.validateInputKikanData(useORCA)
				) {

			// ORCA連携の部分だけ別に評価する
			if (useORCA) {
				if (! validateInputOrcaData()) {
					return;
				}
			}

			// 必須項目が存在するか
			if (validateAsRegisterPushed(validatedData)) {
				if (validatedData.isValidateAsDataSet()) {
					String kikanNumber = validatedData.getKikanNumber();
					String kikanName = validatedData.getKikanName();

					// 機関DBの重複チェック
					File checkFile = new File(JPath
							.GetKikanDatabaseFilePath(kikanNumber));

					if (checkFile.exists()) {
						JErrorMessage.show("M9636", this);
						return;
					}

					// 健診機関名称の重複チェック
					try
					{
						ArrayList kikanItem =  systemDatabase.sendExecuteQuery(
								"SELECT * FROM T_F_KIKAN WHERE KIKAN_NAME =" +
								JQueryConvert.queryConvert(kikanName));

						if (kikanItem.size() > 0){
							JErrorMessage.show("M9635", this);
							return;
						}

					}catch(Exception err){
						err.printStackTrace();
						JErrorMessage.show("M9605", this);
						return;
					}

					// 機関DBの作成
					try {
						JFile.Copy(
								JPath.BaseKikanDatabaseFilePath, JPath
										.GetKikanDatabaseFilePath(kikanNumber));
					} catch (IOException e2) {
						e2.printStackTrace();
						JErrorMessage.show("M9600", this);
						return;
					}

					// 接続開始
					JConnection kikanDatabase = null;
					try {
						kikanDatabase = JConnection.ConnectKikanDatabase(kikanNumber);
					} catch (SQLException e1) {
						e1.printStackTrace();
						JErrorMessage.show("M9601", this);
						rollback();
						return;
					}

					try {
						// システムDBの方はトランザクションをかける必要がある
						systemDatabase.Transaction();

						systemDatabase.sendNoResultQuery(
								"INSERT INTO T_F_KIKAN (TKIKAN_NO, KIKAN_NAME, NITI_RESE)"
										+ "VALUES ("
										+ JQueryConvert
												.queryConvertAppendComma(kikanNumber)
										+ JQueryConvert
												.queryConvertAppendComma(kikanName)
										+ JQueryConvert
												.queryConvert(validatedData
														.getORCA()) + ")");

						// 機関DBに機関情報を登録
						// 機関DBの方は、失敗したらDBごと削除されるため、トランザクションをかける必要はない

						kikanDatabase
								.sendNoResultQuery("INSERT INTO T_KIKAN (TKIKAN_NO, SMOTO_KIKAN, SAKI_KIKAN, KIKAN_NAME, POST, ADR, TIBAN, TEL) "
										+ "VALUES ("
										+ JQueryConvert
												.queryConvertAppendComma(kikanNumber)
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getSendSourceKikan())
										+ JQueryConvert
												.queryConvertAppendComma("")
										+ JQueryConvert
												.queryConvertAppendComma(kikanName)
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getZipcode())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getAddress())
										+ JQueryConvert
												.queryConvertAppendComma(validatedData
														.getChiban())
										+ JQueryConvert
												.queryConvert(validatedData
														.getTel()) + ")");

						systemDatabase.Commit();
					} catch (Exception e2) {
						e2.printStackTrace();
						JErrorMessage
								.show("M9602", this);

						try {
							systemDatabase.rollback();
							kikanDatabase.Shutdown();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JErrorMessage
									.show("M0000", this);
							System.exit(1);
						}

						rollback();
						return;
					}

					try {
						kikanDatabase.Shutdown();

					} catch (SQLException e1) {
						e1.printStackTrace();
						JErrorMessage.show("M0000", this);

						System.exit(1);
					}

					// ORCA連携用のXMLファイルを作成
					JORCASetting orcaSettings = new JORCASetting(kikanNumber);
					orcaSettings.setOrcaDatabase(validatedData.getORCADatabase());
					orcaSettings.setOrcaEncode(validatedData.getORCAEncode());
					orcaSettings.setOrcaIpAddress(validatedData.getORCAIpAddress());
					orcaSettings.setOrcaPassword(validatedData.getORCAPassword());
					orcaSettings.setOrcaPort(validatedData.getORCAPort());
					orcaSettings.setOrcaProtocol(validatedData.getORCAProtocol());
					orcaSettings.setOrcaUser(validatedData.getORCAUser());

					orcaSettings.setNichireseUser(validatedData.getORCANichireseUser());
					orcaSettings.setNichiresePassword(validatedData.getORCANichiresePassword());
					orcaSettings.setUseOrcaIdDigit(validatedData.isUseOrcaIdDigit());
					orcaSettings.setOrcaIdDigit(validatedData.getOrcaIdDigit());

					orcaSettings.Save();

					JApplication.kikanNumber = kikanNumber;
					JApplication.loadORCAData();
					dispose();
				}
			}
		}
	}

	private void clearInputData() {
		for (Iterator<JTextField> iter = inputFields.iterator(); iter.hasNext();) {
			JTextField field = iter.next();
			field.setText("");
		}

		this.jRadioButton_No.setSelected(true);
	}

	/**
	 * ORCA連携情報の入力値を検証する。
	 */
	protected boolean validateInputOrcaData() {

		return
				validatedData.setORCAIpAddress(this.jTextField_ip.getText())
				&& validatedData.setORCAPort(this.jTextField_portNumber.getText())
//				&& validatedData.setORCADatabase(this.jTextField_databaseName.getText())
//				&& validatedData.setORCAProtocol(this.jTextField_protocol.getText())
//
//				&& validatedData.setORCAUser(this.jTextField_dbUserId.getText())
//				&& validatedData.setORCAPassword(this.jTextField_dbPassword.getText())

				&& validatedData.setORCANichireseUser(this.jTextField_nichireseUserId.getText())
				&& validatedData.setORCANichiresePassword(this.jTextField_nichiresePassword.getText());
//				&& validatedData.setORCAEncode(this.jTextField_encoding.getText());
	}

	/**
	 * 機関情報の入力値を検証する。
	 */
	protected boolean validateInputKikanData(boolean useORCA) {

		boolean useOrcaIdDigiti = false;
		if (this.jRadioButton_Yes1.isSelected()) {
			useOrcaIdDigiti = true;
		}

		// edit s.inoue 2009/10/27
		String address = this.jTextField_address.getText();
		if (!address.equals(""))
			address = JValidate.encodeUNICODEtoConvert(address);

		String tiban = this.jTextField_address2.getText();
		if (!tiban.equals(""))
			tiban = JValidate.encodeUNICODEtoConvert(tiban);

		return validatedData.setKikanNumber(this.jTextField_kikanNumber.getText())
		&& validatedData.setSendSourceKikan(this.jTextField_souhumotoNumber.getText())
		&& validatedData.setKikanName(this.jTextField_kikanName.getText())
		&& validatedData.setZipcode(this.jTextField_zip.getText())
		// edit s.inoue 2009/10/27
		// && validatedData.setAddressAndChiban(this.jTextField_address.getText(), this.jTextField_address2.getText())
		&& validatedData.setAddressAndChiban(address,tiban)
		&& validatedData.setTel(this.jTextField_tel.getText())
		&& validatedData.setORCA(useORCA)
		&& validatedData.setUseOrcaIdDigit(useOrcaIdDigiti)
		&& validatedData.setOrcaIdDigit(this.jTextField_orcaIdDigit.getText());
	}

	/**
	 * 処理全体ののロールバック処理を行う
	 */
	private void rollback() {
		try {
			JFile.Delete(JPath.GetKikanDatabaseFilePath(validatedData.getKikanNumber()));
		} catch (Exception e) {
			e.printStackTrace();
			JErrorMessage.show(
					"M0000", this);
			System.exit(1);
		}
	}

	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == jButton_End) {
			pushedEndButton(e);

		} else if (source == jButton_Register) {
			pushedRegisterButton(e);

		/* 日レセ連携「はい」 */
		} else if (source == jRadioButton_Yes) {

			this.changeComponentEnableOnUsingOrca(true);

		/* 日レセ連携「いいえ」 */
		} else if (source == jRadioButton_No) {

			this.changeComponentEnableOnUsingOrca(false);
		}
		/* 接続テストボタン */
		else if(source == jButton_ConnectionTest){
			pushedConnectionTestButton();
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
       Object obj = event.getSource();
        if(obj instanceof JTextField)
        {
            JTextField jtextfield = (JTextField)obj;
            if(jtextfield.getParent().equals(jTextField_zip))
            {
                if(jTextField_zip.getTextTrim().equals(""))
                    return;
                String s = setZipCodeAddress(jTextField_zip.getText());
                if(s.equals(""))
                    return;
                jTextField_address.setText(s);
            }
        }
	}

	// add s.inoue 2010/04/02
	private String setZipCodeAddress(String zipCode){

        ArrayList arraylist = new ArrayList();
        Hashtable hashtable = new Hashtable();
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("SELECT ADDRESS,POST_CD FROM T_POST ");
        stringbuilder.append(" where POST_CD = ");
        stringbuilder.append(JQueryConvert.queryConvert(zipCode));
        try
        {
            arraylist = JApplication.systemDatabase.sendExecuteQuery(stringbuilder.toString());
        }
        catch(SQLException sqlexception)
        {
            logger.error(sqlexception.getMessage());
            sqlexception.printStackTrace();
        }
        if(arraylist.size() <= 0)
        {
            return "";
        } else
        {
            Hashtable hashtable1 = (Hashtable)arraylist.get(0);
            return (String)hashtable1.get("ADDRESS");
        }

	}
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch(keyEvent.getKeyCode()){
		case KeyEvent.VK_F1:
			logger.info(jButton_End.getText());
			pushedEndButton(null);break;
		case KeyEvent.VK_F12:
			logger.info(jButton_Register.getText());
			pushedRegisterButton(null);break;
		}
	}

	private void pushedConnectionTestButton()
    {
        String s = jTextField_ip.getText();
        String s1 = jTextField_portNumber.getText();
        String s2 = jTextField_nichireseUserId.getText();
        String s3 = jTextField_nichiresePassword.getText();
        JOrcaInfoSearchCtl jorcainfosearchctl = new JOrcaInfoSearchCtl();
        try
        {
        	// eidt s.inoue 2013/03/19
        	jorcainfosearchctl.setPatientId("");
            // jorcainfosearchctl.setPatientId("");
            jorcainfosearchctl.setPort(s1);
            jorcainfosearchctl.setHost(s);
            jorcainfosearchctl.setUser(s2);
            jorcainfosearchctl.setPass(s3);
            jorcainfosearchctl.setURL();
        }
        catch(Exception exception)
        {
            logger.error(exception.getMessage());
            JErrorMessage.show("M4392", this);
            return;
        }
        JErrorMessage.show("M9631", this);
    }

	/**
	 * ORCA連携の有無を指定して、コンポーネントの有効、無効を切り替える。
	 */
	private void changeComponentEnableOnUsingOrca(boolean useOrca) {

		for (int i = 0; i < nichireseComponents.length; i++) {
			nichireseComponents[i].setEnabled(useOrca);
//			((ExtendedTextField)nichireseComponents[i]).setEditable(useOrca);
		}

		for (int i = 0; i < nichireseComponents.length; i++) {
			nichireseComponents[i].setBackground(Color.WHITE);
		}

		if (useOrca) {

			// add s.inoue 2012/11/19
			jLabel_ip.setForeground(ViewSettings.getRequiedItemFrColor());
			jLabel_port.setForeground(ViewSettings.getRequiedItemFrColor());
			jLabel_nichiReseUserId.setForeground(ViewSettings.getRequiedItemFrColor());
			jLabel_nichiResePassword.setForeground(ViewSettings.getRequiedItemFrColor());

			for (int i = 0; i < nichireseRequiredComponents.length; i++) {
				nichireseRequiredComponents[i].setBackground(
						ViewSettings.getRequiedItemBgColor());
			}

			/*
			 * 患者 ID の 0 埋め桁数については、0 埋めを行なうことが
			 * 選択されている場合のみ、有効化し、背景色を変更する。
			 */
			if (this.jRadioButton_Yes1.isSelected()) {
				this.jTextField_orcaIdDigit.setEnabled(true);
				this.jTextField_orcaIdDigit.setBackground(
						ViewSettings.getRequiedItemBgColor());
			}
			else {
				this.jTextField_orcaIdDigit.setEnabled(false);
				this.jTextField_orcaIdDigit.setBackground(Color.WHITE);
			}
		}else{
			// add s.inoue 2012/11/19
			jLabel_ip.setForeground(Color.BLACK);
			jLabel_port.setForeground(Color.BLACK);
			jLabel_nichiReseUserId.setForeground(Color.BLACK);
			jLabel_nichiResePassword.setForeground(Color.BLACK);
		}

		this.jButton_ConnectionTest.setEnabled(useOrca);
	}

	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();

		if (source == jRadioButton_Yes) {
			this.changeComponentEnableOnUsingOrca(true);
		}
		else if(source == jRadioButton_No) {
			this.changeComponentEnableOnUsingOrca(false);
		}
		else if(source == jRadioButton_Yes1) {
			if (this.jRadioButton_Yes.isSelected()) {
				// add s.inoue 2012/11/19
				jLabel_orcaIdDegit.setForeground(ViewSettings.getRequiedItemFrColor());
				this.jTextField_orcaIdDigit.setEnabled(true);
				this.jTextField_orcaIdDigit.setBackground(ViewSettings.getRequiedItemBgColor());
			}
		}
		else if(source == jRadioButton_No1) {
			// add s.inoue 2012/11/19
			jLabel_orcaIdDegit.setForeground(Color.BLACK);
			this.jTextField_orcaIdDigit.setEnabled(false);
			this.jTextField_orcaIdDigit.setBackground(Color.WHITE);
		}
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

