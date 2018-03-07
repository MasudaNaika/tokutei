package jp.or.med.orca.jma_tokutei.common.frame;

import java.awt.Paint;
import java.awt.event.ComponentListener;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

public class JSplashFrameCtrl extends JSplashFrame implements Runnable
{
	/*
	 *  コンストラクタ
	 *
	 * 		@param  バージョン
	 * 		@param  スプラッシュ
	 *
	 * 		@return none
	 *
	 */
	public JSplashFrameCtrl( String strVersion, String strModuleVersion,String strSchemaVersion,String strDBDataVersion, String strImagePath ,boolean flgReload)
	{
		try
		{
			createSplash( strVersion,strModuleVersion, strSchemaVersion,strDBDataVersion,strImagePath );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			JErrorMessage.show("M9500", null);

			return;
		}

		// add s.inoue 20090113 reload処理
		if (flgReload){
			return;
		}
		// 読込処理を別スレッドで処理
		Thread loadThread = new Thread( this );

		showSplash();

		loadThread.run();

		try
		{
			/* Modified 2008/03/11 若月  */
			/* --------------------------------------------------- */
//			Thread.sleep( 3500 );
			/* --------------------------------------------------- */
			Thread.sleep( ViewSettings.getTokuteiSplashDisplayTime() );
			/* --------------------------------------------------- */
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		hideSplash();

		m_splashScreen.dispose();
	}

	/*
	 *  ロード処理
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	public void loadingProc()
	{
		// You have to write loading process after override.
	}

	/*
	 *  内部スレッド
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	public void run()
	{
		loadingProc();
	}

	/* Added 2008/03/11 若月  */
	/* --------------------------------------------------- */
	public void showSplashWindow(){

		m_splashScreen.setVisible(true);
	}

	public void hideSplashWindow(){
		m_splashScreen.setVisible(false);
	}

	public void addComponentListener(ComponentListener listener){
		m_splashScreen.addComponentListener(listener);
	}
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

