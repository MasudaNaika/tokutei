package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
import jp.or.med.orca.jma_tokutei.common.app.JApplication;


/**
 * 健診パターンメンテナンス（追加）
 */
public class JKenshinPatternMaintenanceAddFrameCtrl extends JKenshinPatternMaintenanceAddFrame
{
	/**
	 * 新規追加
	 */
	public static final int ADD_MODE = 1;
	/**
	 * 編集
	 */
	public static final int EDIT_MODE = 2;
	private int mode;

	private JKenshinPatternMaintenanceAddFrameData validatedData = new JKenshinPatternMaintenanceAddFrameData();
	/* フォーカス移動制御 */
	private JFocusTraversalPolicy focusTraversalPolicy = null;

	private static Logger logger = Logger.getLogger(JKenshinPatternMaintenanceAddFrameCtrl.class);

	/**
	 * 登録ボタンを押した際の必須項目等を検証
	 */
	protected boolean validateAsRegisterPushed(JKenshinPatternMaintenanceAddFrameData data)
	{
		if( validatedData.getPatternNumber().equals("") )
		{
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M5501",this);
			return false;
		}

		if( validatedData.getPatternName().equals("") )
		{
			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M5502",this);
			return false;
		}

		data.setValidateAsDataSet();
		return true;
	}

	/**
	 * @param KenshinNumber 健診パターン番号
	 * @param patternName パターン名
	 * @param note 備考
	 * @param Mode モード
	 */
	public JKenshinPatternMaintenanceAddFrameCtrl(int KenshinNumber,String patternName,String note,int Mode)
	{
		mode = Mode;
		jTextField_PatternNumber.setText(String.valueOf(KenshinNumber));
		jTextField_PatternNumber.setEditable(false);
		jTextField_PetternName.setText(patternName);
		jTextField_Note.setText(note);

		// focus制御
		// edit s.inoue 2009/10/07
		this.focusTraversalPolicy = new JFocusTraversalPolicy();
		this.setFocusTraversalPolicy(this.focusTraversalPolicy);
		this.focusTraversalPolicy.setDefaultComponent(this.jTextField_PatternNumber);
		this.focusTraversalPolicy.addComponent(this.jTextField_PatternNumber);
		this.focusTraversalPolicy.addComponent(this.jTextField_PetternName);
		this.focusTraversalPolicy.addComponent(this.jTextField_Note);
		this.focusTraversalPolicy.addComponent(this.jButton_Register);
		this.focusTraversalPolicy.addComponent(jButton_End);

		this.jTextField_PetternName.grabFocus();

		// add s.inoue 2009/12/03
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
		if( validatedData.setPatternNumber(jTextField_PatternNumber.getText()) &&
				validatedData.setPatternName(jTextField_PetternName.getText()) &&
				validatedData.setNote(jTextField_Note.getText())
				)
		{
			if( validateAsRegisterPushed( validatedData ) )
			{
				if( validatedData.isValidateAsDataSet() )
				{
					// add s.inoue 20081217
					// 健診機関名称の重複チェック
					try
					{
						ArrayList kikanItem =  JApplication.kikanDatabase.sendExecuteQuery(
								"SELECT * FROM T_KENSHIN_P_M WHERE K_P_NAME =" +
								JQueryConvert.queryConvert(validatedData.getPatternName()));

						if (kikanItem.size() > 0){
							// edit s.inoue 2010/04/21
							mode = EDIT_MODE;
							// JErrorMessage.show("M9637", this);
							// return;
						}

					}catch(Exception err){
						err.printStackTrace();
						JErrorMessage.show("M9601", this);
						return;
					}

					//ここでSQLを投げる
					String Query = null;

					if( mode == ADD_MODE )
					{
						Query = new String("INSERT INTO T_KENSHIN_P_M (K_P_NO,K_P_NAME,BIKOU) VALUES (" +
								JQueryConvert.queryConvertAppendComma(validatedData.getPatternNumber()) +
								JQueryConvert.queryConvertAppendComma(validatedData.getPatternName()) +
								JQueryConvert.queryConvert(validatedData.getNote()) +
								")");
					}

					if( mode == EDIT_MODE )
					{
						Query = new String("UPDATE T_KENSHIN_P_M SET K_P_NAME = " +
								JQueryConvert.queryConvertAppendComma(jTextField_PetternName.getText()) +
								"BIKOU = " + JQueryConvert.queryConvert(jTextField_Note.getText()) +
								"WHERE K_P_NO = " + JQueryConvert.queryConvert(jTextField_PatternNumber.getText()));
					}

					try{
						JApplication.kikanDatabase.sendNoResultQuery(Query);
					}catch(SQLException f)
					{
						f.printStackTrace();
						jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M5520",this);
						return;
					}

					dispose();
					return;
				}
			}
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


