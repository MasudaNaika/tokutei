package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;

/**
 * 名寄せメンテナンス
 */
public class JNayoseMaintenanceEditFrameCtl extends JNayoseMaintenanceEditFrame
{

	private static Logger logger = Logger.getLogger(JNayoseMaintenanceEditFrameCtl.class);  //  @jve:decl-index=0:
	private JNayoseMaintenanceEditFrameData validatedData = new JNayoseMaintenanceEditFrameData();
	private String uketukeID = null;

	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	/**
	 * This is the default constructor
	 */
	public JNayoseMaintenanceEditFrameCtl() {
		super();
		// focus制御
		initializefocus();
		// function
		functionListner();
	}

	/**
	 * 登録ボタンを押した際の必須項目等を検証
	 */
	protected boolean validateAsRegisterPushed(JKenshinPatternMaintenanceAddFrameData data)
	{
		if( validatedData.getNayoseNumber().equals("") )
		{
			JErrorMessage.show("M9800",this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * @param himotukeID 紐付けID
	 * @param uketukeID 受付ID
	 * @param name 氏名(漢字)
	 * @param name 氏名(カナ)
	 * @param birthDay 生年月日
	 * @param sex 性別
	 */
	public JNayoseMaintenanceEditFrameCtl(String himotukeID,String uketukeID,String name,String nameKana,String birthDay,String sex,String home,String hiKigou, String hiBangou)
	{
		super();

		initilizeControlSetting(himotukeID,uketukeID,name,nameKana,birthDay,sex,home,hiKigou,hiBangou);
		// focus制御
		initializefocus();
		// function
		functionListner();
	}

	private void initilizeControlSetting(String himotukeID,String uketukeID,String name,String nameKana,String birthDay,String sex,String home,String hiKigou, String hiBangou){
		jTextField_Name.setText(name);
		jTextField_KanaName.setText(nameKana);
		jTextField_Birthday.setText(birthDay);
		jTextField_Sex.setText(sex);
		jTextField_Home.setText(home);
		jTextField_HiKigou.setText(hiKigou);
		jTextField_HiBangou.setText(hiBangou);
		jTextField_HimotukeID.setText(himotukeID);

		this.uketukeID = String.valueOf(uketukeID);

		jTextField_Name.setEditable(false);
		jTextField_KanaName.setEditable(false);
		jTextField_Birthday.setEditable(false);
		jTextField_Sex.setEditable(false);
		jTextField_Home.setEditable(false);
		jTextField_HiKigou.setEditable(false);
		jTextField_HiBangou.setEditable(false);
	}

	private void initializefocus(){
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.jTextField_HimotukeID);
		this.focusTraversalPolicy.addComponent(this.jTextField_Name);
		this.focusTraversalPolicy.addComponent(this.jTextField_KanaName);
		this.focusTraversalPolicy.addComponent(this.jTextField_Sex);
		this.focusTraversalPolicy.addComponent(this.jTextField_Birthday);
		this.focusTraversalPolicy.addComponent(this.jTextField_Home);
		this.focusTraversalPolicy.addComponent(this.jTextField_HiKigou);
		this.focusTraversalPolicy.addComponent(this.jTextField_HiBangou);
		this.focusTraversalPolicy.addComponent(this.jTextField_HimotukeID);
		this.focusTraversalPolicy.addComponent(this.jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

	}

	private void functionListner(){
		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
			Component comp = focusTraversalPolicy.getComponent(i);
			comp.addKeyListener(this);
		}
	}
	/**
	 * 終了ボタン
	 */
	public void pushedEndButton( ActionEvent e )
	{
		dispose();
	}

	/**
	 * 登録ボタン
	 */
	public void pushedRegisterButton( ActionEvent e )
	{
		if( validatedData.setNayoseNumber(jTextField_HimotukeID.getText()))
		{
//					try
//					{
//						ArrayList kikanItem =  JApplication.kikanDatabase.sendExecuteQuery(
//								"SELECT COUNT(UKETUKE_ID) FROM T_NAYOSE WHERE UKETUKE_ID =" +
//								JQueryConvert.queryConvert(validatedData.getPatternName()));
//						if (kikanItem.size() > 0){
//							JErrorMessage.show("M9637", this);
//							return;
//						}
//					}catch(Exception err){
//						err.printStackTrace();
//						JErrorMessage.show("M9601", this);
//						return;
//					}

				String Query = null;

				Query = new String("UPDATE T_NAYOSE SET NAYOSE_NO = " +
						JQueryConvert.queryConvert(jTextField_HimotukeID.getText()) +
						"WHERE UKETUKE_ID = " + JQueryConvert.queryConvert(this.uketukeID));

				try{
					JApplication.kikanDatabase.sendNoResultQuery(Query);
				}catch(SQLException ex){
					logger.error(ex.getMessage());
					JErrorMessage.show("M9801",this);
					return;
				}

				dispose();
				return;
		}
	}

	public void actionPerformed( ActionEvent e )
	{
		Object source = e.getSource();
		if( source == jButton_End )
		{
			logger.info(jButton_End.getText());
			pushedEndButton( e );
		}

		else if( source == jButton_Register)
		{
			logger.info(jButton_Register.getText());
			pushedRegisterButton( e );
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
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


