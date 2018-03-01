package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Color;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.orca.JORCASetting;
import jp.or.med.orca.jma_tokutei.common.sql.JConnection;

/**
 * 機関情報を変更するためのフレームのコントロール
 *
 * Modified 2008/03/08 若月
 * 　Javaコーディング規約に基づいてメソッド名、変数名を変更。
 * 　（処理の変更は無し。）
 *
 * Modified 2008/04/20
 *   JAddKikanInformationFrameCtrl を継承する仕様に変更。
 */
public class JChangeKikanInformationFrameCtrl extends JAddKikanInformationFrameCtrl
{
	private JConnection KikanDatabase;
	private String KikanNumber;

	/**
	 * このフレーム内で機関情報データベースが初期化されたかどうか
	 */
	private boolean isKikanDatabaseInited;

	// edit s.inoue 2009/09/30
	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;  //  @jve:decl-index=0:

	/**
	 * 機関データベースへの接続が済んでいる状態でフレームを開く。
	 * 特定健診ソフトウェア側から、参照されることを想定。
	 * @param KikanNumber 特定健診の機関番号
	 */
	public JChangeKikanInformationFrameCtrl(JConnection systemDatabase,JConnection KikanDatabase,String KikanNumber)
	{
		super(systemDatabase,false);

		this.KikanDatabase = KikanDatabase;
		this.KikanNumber = KikanNumber;

		this.isKikanDatabaseInited = false;
		this.init();
	}

	/**
	 * 機関データベースへの接続が行われていない状態でフレームを開く。
	 * システム管理者用ソフトウェア側から、参照されることを想定。
	 * @param KikanNumber 特定健診の機関番号
	 */
	public JChangeKikanInformationFrameCtrl(JConnection systemDatabase,String KikanNumber)
	{
		// edit s.inoue 2009/10/15
		super(systemDatabase,false);

		this.KikanNumber = KikanNumber;

		try {
			this.KikanDatabase = JConnection.ConnectKikanDatabase(KikanNumber);
		} catch (SQLException e) {
			e.printStackTrace();
			JErrorMessage.show("M9700", this);
		}

		this.isKikanDatabaseInited = true;
		this.init();
	}

	/**
	 * 登録ボタンを押した際の必須項目の検証
	 */
	protected boolean validateAsRegisterPushed(JChangeKikanInformationFrameData data)
	{
		boolean UseORCA = false;
		if(jRadioButton_Yes.isSelected() == true)
		{
			UseORCA = true;
		}

		if(
				data.getKikanNumber().isEmpty() ||
				data.getKikanName().isEmpty() ||
				data.getZipcode().isEmpty() ||
				data.getAddress().isEmpty() ||
				data.getTel().isEmpty() ||
				data.getORCA().isEmpty()
		)
		{
			JErrorMessage.show("M9703", this);
			return false;
		}

		// ORCAを使用するときの項目チェック
		if(UseORCA == true)
		{
			if(
				data.getORCADatabase().isEmpty() ||
				data.getORCAEncode().isEmpty() ||
				data.getORCAIpAddress().isEmpty() ||
				data.getORCAPort().isEmpty() ||
				data.getORCAProtocol().isEmpty() ||
				data.getORCAUser().isEmpty()
			)
			{
				JErrorMessage.show("M9604", this);
				return false;
			}
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		DisposeFrame();
	}

	private boolean checkDegit(String JyushinkenID){

		int i1 = Integer.parseInt(JyushinkenID.substring(0,1))*2;
		int i2 = Integer.parseInt(JyushinkenID.substring(1,2))*1;
		int i3 = Integer.parseInt(JyushinkenID.substring(2,3))*2;
		int i4 = Integer.parseInt(JyushinkenID.substring(3,4))*1;
		int i5 = Integer.parseInt(JyushinkenID.substring(4,5))*2;
		int i6 = Integer.parseInt(JyushinkenID.substring(5,6))*1;
		int i7 = Integer.parseInt(JyushinkenID.substring(6,7))*2;
		int i8 = Integer.parseInt(JyushinkenID.substring(7,8))*1;
		int i9 = Integer.parseInt(JyushinkenID.substring(8,9))*2;

		i1 = this.checkKeta(i1);
		i2 = this.checkKeta(i2);
		i3 = this.checkKeta(i3);
		i4 = this.checkKeta(i4);
		i5 = this.checkKeta(i5);
		i6 = this.checkKeta(i6);
		i7 = this.checkKeta(i7);
		i8 = this.checkKeta(i8);
		i9 = this.checkKeta(i9);

		int yy = i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 ;
		int zz = yy%10;
		int xx = Integer.parseInt(JyushinkenID.substring(9,10));
		zz = 10 - zz;

		if (zz==xx){
			return true;
		}

		return false;
	}

	//2桁の場合1桁目と2桁目を足す
	private int checkKeta(int a){

		String s = String.valueOf(a);
		if (s.length()>1){
			a = Integer.parseInt(s.substring(0,1))+
				Integer.parseInt(s.substring(1,2));
		}
		return a;
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton( ActionEvent e ){
		register();
	}

	/**
	 * 登録処理
	 */
	private void register() {
		boolean useORCA = false;
		if(jRadioButton_Yes.isSelected() == true)
		{
			useORCA = true;
		}

		//各入力項目が正当かどうか
		if(
				validateInputKikanData(useORCA)
		)
		{
			// ORCA連携の部分だけ別に評価する
			if (useORCA) {
				if (! validateInputOrcaData()) {
					return;
				}
			}

			//必須項目が存在するか
			if( validateAsRegisterPushed( validatedData ))
			{
				if( validatedData.isValidateAsDataSet() )
				{
					try
					{

						// add s.inoue 20081217
						// 健診機関名称の重複チェック
						ArrayList kikanItem = null;

						try
						{
							kikanItem =  systemDatabase.sendExecuteQuery(
									"SELECT * FROM T_F_KIKAN WHERE KIKAN_NAME =" +
									JQueryConvert.queryConvert(validatedData.getKikanName()) +
									" AND TKIKAN_NO != " + JQueryConvert.queryConvert(validatedData.getKikanNumber()));

							if (kikanItem.size() > 0){
								JErrorMessage.show("M9635", this);
								return;
							}

						}catch(Exception err){
							err.printStackTrace();
							JErrorMessage.show("M9605", this);
							return;
						}


						systemDatabase.Transaction();
						KikanDatabase.Transaction();

						systemDatabase.sendNoResultQuery(
								"UPDATE T_F_KIKAN SET " +
								"KIKAN_NAME = " + JQueryConvert.queryConvertAppendComma(validatedData.getKikanName()) +
								"NITI_RESE = " + JQueryConvert.queryConvert(validatedData.getORCA()) +
								" WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(validatedData.getKikanNumber()));

						KikanDatabase.sendNoResultQuery(
								"UPDATE T_KIKAN SET SMOTO_KIKAN = " +
									JQueryConvert.queryConvertAppendComma(validatedData.getSendSourceKikan()) +
								"KIKAN_NAME = " + JQueryConvert.queryConvertAppendComma(validatedData.getKikanName()) +
								"POST = " + JQueryConvert.queryConvertAppendComma(validatedData.getZipcode()) +
								"ADR = " + JQueryConvert.queryConvertAppendComma(validatedData.getAddress()) +
								"TIBAN = " + JQueryConvert.queryConvertAppendComma(validatedData.getChiban()) +
								"TEL = " + JQueryConvert.queryConvert(validatedData.getTel()) +
								"WHERE TKIKAN_NO = " + JQueryConvert.queryConvert(validatedData.getKikanNumber()));

						systemDatabase.Commit();
						KikanDatabase.Commit();

					}catch(Exception e2)
					{
						e2.printStackTrace();

						// 修正
						JErrorMessage.show("M9702", this);
						try
						{
							systemDatabase.rollback();
							KikanDatabase.rollback();
						}catch(Exception e3)
						{
							e3.printStackTrace();

							JErrorMessage.show("M0000", this);
							System.exit(1);
						}
						return;
					}

					JORCASetting orcaSettings = new JORCASetting(validatedData.getKikanNumber());
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
					orcaSettings.setOrcaIdDigit( validatedData.getOrcaIdDigit());

					orcaSettings.Save();

					JApplication.kikanNumber = validatedData.getKikanNumber();
					JApplication.loadORCAData();
					// ORCA連携用のXMLファイルを作成
					DisposeFrame();
				}
			}
		}
	}

	/**
	 * 初期化。
	 * コンストラクタが複数存在するため、それらの共通処理をここに記述。
	 */
	public void init()
	{
// eidt s.inoue 2012/07/05
//		jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
//
//		this.jTextField_kikanNumber.setEditable(false);
//		// edit s.inoue 2009/09/30
//		// 修正時、フィールドの色変え
//		Color requiedItemColor = ViewSettings.getDisableItemBgColor();
//		this.jTextField_kikanNumber.setBackground(requiedItemColor);
//		// add h.yoshitama 2009/09/30
//		jLabel_disableExample.setBackground(ViewSettings.getDisableItemBgColor());
//
//		// edit s.inoue 2009/09/30
//		// フォーカス制御
//		this.focusTraversalPolicy = new JFocusTraversalPolicy();
//		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//		// add h.yoshitama 2009/09/30
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_kikanNumber);
//		this.focusTraversalPolicy.setDefaultComponent(jTextField_souhumotoNumber);
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

		jLabel_Title = new TitleLabel(ViewSettingsKey.KEY_TOKUTEI_KIKAN_MASTERMAINTENANCE_EDIT_FRAME_TITLE);
        jTextField_kikanNumber.setEnabled(false);
        Color color = ViewSettings.getDisableItemBgColor();
        jTextField_kikanNumber.setBackground(color);
        jTextField_souhumotoNumber.grabFocus();
        Hashtable hashtable = null;

        try
        {
            hashtable = (Hashtable)KikanDatabase.sendExecuteQuery((new StringBuilder()).append("SELECT * FROM T_KIKAN WHERE TKIKAN_NO =").append(JQueryConvert.queryConvert(KikanNumber)).toString()).get(0);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            JErrorMessage.show("M9701", this);
            dispose();
        }

		// 機関情報の取得
		Hashtable<String,String> KikanItem = null;

		try
		{
			KikanItem =  KikanDatabase.sendExecuteQuery(
					"SELECT * FROM T_KIKAN WHERE TKIKAN_NO =" +
					JQueryConvert.queryConvert(KikanNumber)).get(0);
		}catch(Exception e)
		{
			e.printStackTrace();
			JErrorMessage.show("M9701", this);
			dispose();
		}

		String zip = KikanItem.get("POST");

		this.jTextField_kikanNumber.setText(KikanItem.get("TKIKAN_NO"));
		this.jTextField_souhumotoNumber.setText(KikanItem.get("SMOTO_KIKAN"));
		this.jTextField_kikanName.setText(KikanItem.get("KIKAN_NAME"));

		if (zip != null && ! zip.isEmpty()) {
			// eidt s.inoue 2012/07/09
			jTextField_zip.setPostCodeFormatText(zip);
		}

		this.jTextField_address.setText(KikanItem.get("ADR"));
		this.jTextField_address2.setText(KikanItem.get("TIBAN"));
		this.jTextField_tel.setText(KikanItem.get("TEL"));

		// システム情報の取得
		Hashtable<String, String> SystemItem = null;

		try {
			SystemItem =  systemDatabase.sendExecuteQuery(
					"SELECT * FROM T_F_KIKAN WHERE TKIKAN_NO = " +
					JQueryConvert.queryConvert(KikanNumber)).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			DisposeFrame();
			return;
		}

		// ORCA連携周りの表示
		boolean useOrca = SystemItem.get("NITI_RESE").equals("1");
		if(useOrca)
		{
			jRadioButton_Yes.setSelected(true);
			jRadioButton_No.setSelected(false);
		}else{
			jRadioButton_Yes.setSelected(false);
			jRadioButton_No.setSelected(true);
		}

		JORCASetting orcaSettings = new JORCASetting(KikanNumber);
		if(orcaSettings.Load() == true)
		{

			String orcaIpAddress = orcaSettings.getOrcaIpAddress();
			if (orcaIpAddress == null) {
				orcaIpAddress = "";
			}
			this.jTextField_ip.setText(orcaIpAddress);

			String orcaPort = orcaSettings.getOrcaPort();
			if (orcaPort == null) {
				orcaPort = "";
			}
			this.jTextField_portNumber.setText(orcaPort);
// del s.inoue 2012/07/05
//			String orcaDatabase = orcaSettings.getOrcaDatabase();
//			if (orcaDatabase == null) {
//				orcaDatabase = "";
//			}
//			this.jTextField_databaseName.setText(orcaDatabase);
//
//			String orcaProtocol = orcaSettings.getOrcaProtocol();
//			if (orcaProtocol == null) {
//				orcaProtocol = "";
//			}
//			this.jTextField_protocol.setText(orcaProtocol);
//
//			String orcaUser = orcaSettings.getOrcaUser();
//			if (orcaUser == null) {
//				orcaUser = "";
//			}
//			this.jTextField_dbUserId.setText(orcaUser);
//
//			String orcaPassword = orcaSettings.getOrcaPassword();
//			if (orcaPassword == null) {
//				orcaPassword = "";
//			}
//			this.jTextField_dbPassword.setText(orcaPassword);

			String nichireseUser = orcaSettings.getNichireseUser();
			if (nichireseUser == null) {
				nichireseUser = "";
			}
			this.jTextField_nichireseUserId.setText(nichireseUser);

			String nichiresePassword = orcaSettings.getNichiresePassword();
			if (nichiresePassword == null) {
				nichiresePassword = "";
			}
			this.jTextField_nichiresePassword.setText(nichiresePassword);

//			String orcaEncode = orcaSettings.getOrcaEncode();
//			if (orcaEncode == null) {
//				orcaEncode = "";
//			}
//			this.jTextField_encoding.setText(orcaEncode);

			boolean useOrcaIdDigit = orcaSettings.isUseOrcaIdDigit();
			if (useOrcaIdDigit) {
				this.jRadioButton_Yes1.setSelected(true);
			}
			else {
				this.jRadioButton_No1.setSelected(true);
			}

			String orcaIdDigit = orcaSettings.getOrcaIdDigit();
			if (orcaIdDigit == null) {
				orcaIdDigit = "";
			}
			this.jTextField_orcaIdDigit.setText(orcaIdDigit);
		}
	}

	/**
	 * フレームを削除する。その際に、初期化の方法によって削除方法を変える。
	 */
	private void DisposeFrame()
	{
		// 機関データベースがこのフレームの中で接続された場合、フレームから抜ける
		// 時には、接続を解除する
		if(isKikanDatabaseInited == true)
		{
			try {
				this.KikanDatabase.Shutdown();
			} catch (SQLException e1) {
				e1.printStackTrace();
				JErrorMessage.show("M0000", this);
				System.exit(1);
			}
		}
		dispose();
	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

