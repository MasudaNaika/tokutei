package jp.or.med.orca.jma_tokutei.common.frame;

import java .io     .*;
import java .awt    .*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing  .*;
import javax.imageio.*;

import jp.or.med.orca.jma_tokutei.common.printer.JGraphicPrinter;

/**
 * スプラッシュ画面
 */
public class JSplashFrame
{
	/* Modified 2008/03/11 若月  */
	/* --------------------------------------------------- */
	protected JWindow m_splashScreen = null;
	/* --------------------------------------------------- */
//	protected static JWindow m_splashScreen = null;
	/* --------------------------------------------------- */

	/*
	 *  イメージ描画カスタムパネルクラス
	 */
	private class JImagePanel extends JPanel
	{
		private Image m_imgImage = null;

		private String m_strVersion = null;

//		Add E.O 2008/03/30 START
		private String m_strModuleVersion = null;
		private String m_strSchemaVersion = null;
		private String m_strDBDataVersion = null;
//		Add E.O 2008/03/30 END

		/*
		 *  コンストラクタ
		 *
		 *  	@param  none
		 *
		 *  	@return none
		 */
		public JImagePanel( String strVersion, String strModuleVersion,String strSchemaVersion,String strDBDataVersion,String strImagePath ) throws Exception
		{
			m_strVersion = strVersion;
//			Add E.O 2008/03/30 START
			m_strModuleVersion = strModuleVersion;
			m_strSchemaVersion = strSchemaVersion;
			m_strDBDataVersion = strDBDataVersion;
//			Add E.O 2008/03/30 END

			m_imgImage   = ImageIO.read( new FileInputStream( strImagePath ) );

			/* Added 2008/03/11 若月  */
			/* --------------------------------------------------- */
			this.addMouseListener(new MouseAdapter(){

				@Override
				public void mouseClicked(MouseEvent e) {
					JSplashFrame.this.hideSplash();
				}
			});
			/* --------------------------------------------------- */
		}

		/*
		 *  画像横幅取得
		 *
		 * 		@param  none
		 *
		 * 		@return 横幅
		 *
		 */
		public int getWidth()
		{
			return m_imgImage.getWidth( this );
		}

		/*
		 *  画像縦幅取得
		 *
		 *  	@param  none
		 *
		 *  	@return 縦幅
		 *
		 */
		public int getHeight()
		{
			return m_imgImage.getHeight( this );
		}

		/*
		 *  @override
		 *
		 *  	@param  ...
		 *
		 *  	@return ...
		 */
		public void paint( Graphics g )
		{
			g.drawImage ( m_imgImage, 0, 0, getWidth(), getHeight(), this );

			g.setColor  ( Color.BLACK );

			/* Modified 2008/03/11 若月  */
			/* --------------------------------------------------- */
//			g.drawString( m_strVersion, getWidth() - 100, 20 );
			/* --------------------------------------------------- */
			g.drawString( m_strVersion, getWidth() - 110, 20 );
			/* --------------------------------------------------- */
//			Add E.O 2008/03/30 START
			g.drawString( m_strModuleVersion, getWidth() - 110, 40 );
			g.drawString( m_strSchemaVersion, getWidth() - 110, 60 );
			g.drawString( m_strDBDataVersion, getWidth() - 110, 80 );
//			Add E.O 2008/03/30 END
		}
	}

	/*
	 *  スプラッシュ生成
	 *
	 * 		@param  バージョン
	 *
	 * 		@return none
	 *
	 */
	protected void createSplash( String strVersion,String strModuleVersion,String strSchemaVersion,String strDBDataVersion, String strImagePath ) throws Exception
	{
		JImagePanel image = new JImagePanel( strVersion,strModuleVersion,strSchemaVersion,strDBDataVersion,strImagePath );

		m_splashScreen = new JWindow( new JFrame() );

		/* Added 2008/03/11 若月  */
		/* --------------------------------------------------- */
//		m_splashScreen.addWindowListener(new WindowAdapter(){
//			@Override
//			public void windowDeactivated(WindowEvent arg0) {
//				m_splashScreen.setVisible(false);
//			}
//		});
		/* --------------------------------------------------- */


		// パネル設定
		m_splashScreen.setLayout           ( new BorderLayout() );
		m_splashScreen.getContentPane().add( image, BorderLayout.CENTER );

		// サイズ設定
		m_splashScreen.getContentPane().setBackground( Color.white );
		m_splashScreen.setBounds                     ( 0, 0, image.getWidth(), image.getHeight() );

		// 中央表示
		m_splashScreen.setLocationRelativeTo( null );
	}

	/*
	 *  スプラッシュ表示
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	protected void showSplash()
	{
		m_splashScreen.setVisible( true );
	}

	/*
	 *  スプラッシュ非表示
	 *
	 * 		@param  none
	 *
	 * 		@return none
	 *
	 */
	protected void hideSplash()
	{
		m_splashScreen.setVisible( false );
	}
}
