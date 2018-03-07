package jp.or.med.orca.jma_tokutei.common.execlocker;

import java .io   .*;
import java .awt  .*;
import java .util .*;
import java.util.regex.Pattern;
import java .lang .*;
import javax.swing.*;
import java .awt  .event.*;
import javax.swing.table.*;
import javax.swing.event.*;

import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;

// ----------------------------------------------------------------------------
/**
	class::name	JExecUnlocker

	class::expl	起動ロック解除クラス
*/
// ----------------------------------------------------------------------------
public class JExecUnlocker extends JFrame implements ActionListener
{
	private JLabel  m_guiLabel  = null;

	private JButton m_guiButton = null;

	/* Added 2008/03/17 若月  */
	/* --------------------------------------------------- */
	/* システム管理者用ソフトウェア用ロックファイルの存在を示すフラグ */
	private static boolean existsAdminLockFile = false;
	/* 特定健診ソフトウェア用ロックファイルの存在を示すフラグ */
	private static boolean existsKikanLockFile = false;
	/* --------------------------------------------------- */

	/*
	 *  メイン関数
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public static void main( String[] args ){
		/* Modified 2008/03/15 若月  */
		/* --------------------------------------------------- */
//		File lockFile = new File( JExecLockerConfig.LOCKER_FILENAME );
//
//		if( !lockFile.exists() )
//		{
//			JOptionPane.showMessageDialog( null, "二重起動ロックされていません。", "確認", JOptionPane.INFORMATION_MESSAGE );
//
//			System.exit( -1 );
//		}
//
//		new JExecUnlocker( "二重起動ロック解除ソフトウェア" );
		/* --------------------------------------------------- */
		existsAdminLockFile = false;
		existsKikanLockFile = false;
		// add s.inoue 2012/06/06
		boolean existsflg = false;

		File file = new File(".");
		File[] files = file.listFiles();

		if(files != null){
			File adminLockFile = new File( JExecLockerConfig.ADMIN_LOCK_FILENAME );

			/* システム管理者用ソフトウェア */
			if (! adminLockFile.exists()) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(ViewSettings.getUsingValueString("admin.application.name"));
				buffer.append("は二重起動ロックされていません。");
// eidt s.inoue 2012/06/06
//				JOptionPane.showMessageDialog(
//						null,
//						buffer.toString(),
//						"確認",
//						JOptionPane.INFORMATION_MESSAGE
//					);
			}
			else {
				existsAdminLockFile = true;
				existsflg = true;
			}

			/* 特定健診システムソフトウェア用ロックファイルを検索する */
			for(int i = 0 ; files.length > i ; i++)	{
				String filename = files[i].getName();

				if(Pattern.matches(JExecLockerConfig.KIKAN_LOCK_FILENAME_PATTERN, filename)){
					existsKikanLockFile = true;
				}
			}

			if (! existsKikanLockFile) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(ViewSettings.getUsingValueString("tokutei.application.name"));
				buffer.append("は二重起動ロックされていません。");
// eidt s.inoue 2012/06/06
//				JOptionPane.showMessageDialog(
//						null,
//						buffer.toString(),
//						"確認",
//						JOptionPane.INFORMATION_MESSAGE
//					);
			}else{
				existsflg = true;
			}
			// add s.inoue 2012/06/06
			if (!existsflg)
					JOptionPane.showMessageDialog(
						null,
						"二重起動されているアプリケーションはありません。\n問題はありません。",
						"確認",
						JOptionPane.INFORMATION_MESSAGE
					);

		}

		/* ロックファイルが全く存在しない場合は、終了する。 */
		if( ! existsAdminLockFile && ! existsKikanLockFile ){
			System.exit( -1 );
		}

		new JExecUnlocker( "二重起動ロック解除ソフトウェア" );
		/* --------------------------------------------------- */
	}

	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JExecUnlocker( String strTitle )
	{
		super( strTitle );

		// ＵＩ生成
		m_guiLabel  = new JLabel ( "ボタンを押すとロック解除が開始されます。" );

		m_guiButton = new JButton( "ロック解除開始" );

		m_guiLabel .setFont( new Font("Dialog", Font.PLAIN, 12) );
		m_guiButton.setFont( new Font("Dialog", Font.PLAIN, 12) );

		// ＵＩ設定
		getContentPane().setLayout     ( new CardLayout( 5, 5 ) );
		JPanel panelScreen = new JPanel( new GridLayout( 2, 1 ) );

		panelScreen.add( m_guiLabel  );
		panelScreen.add( m_guiButton );

		getContentPane().add( "ScreenGap", panelScreen );

		m_guiButton.addActionListener( this );

		// フレーム生成
		setLocationRelativeTo( null );

		setSize( 384, 256 );

		setVisible( true );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	/*
	 *  入力イベント
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public void actionPerformed( ActionEvent event )
	{
		JButton button = (JButton)(event.getSource());

		if( button == m_guiButton )
		{
			/* Modified 2008/03/17 若月  */
			/* --------------------------------------------------- */
//			File lockFile = new File( JExecLockerConfig.LOCKER_FILENAME );
//
//			if( !lockFile.delete() )
//			{
//				JOptionPane.showMessageDialog( this, "二重起動ロックの解除に失敗しました。", "確認", JOptionPane.INFORMATION_MESSAGE );
//
//				System.exit( -1 );
//			}
//
//			JOptionPane.showMessageDialog( this, "二重起動ロックの解除に成功しました。", "確認", JOptionPane.INFORMATION_MESSAGE );
//
//			System.exit( -1 );
			/* --------------------------------------------------- */

			boolean successDeleteAdminLockFile = false;
			boolean successDeleteKikanLockFile = false;

			/* システム管理者用ソフトウェア用ロックファイルを削除する。 */
			if (existsAdminLockFile) {
				File adminLockFile = new File( JExecLockerConfig.ADMIN_LOCK_FILENAME );

				if (! adminLockFile.delete()) {

					StringBuffer buffer = new StringBuffer();
					buffer.append(ViewSettings.getUsingValueString("admin.application.name"));
					buffer.append("の二重起動ロックの解除に失敗しました。");

					JOptionPane.showMessageDialog(
							null,
							buffer.toString(),
							"確認",
							JOptionPane.INFORMATION_MESSAGE
						);

					System.exit(-1);
				}
				else {
					successDeleteAdminLockFile = true;
				}
			}

			/* 特定健診ソフトウェア用ロックファイルを削除する。 */
			if (existsKikanLockFile) {
				File file = new File(".");
				File[] files = file.listFiles();

				successDeleteKikanLockFile = true;
				for(int i = 0 ; files.length > i ; i++)	{

					String filename = files[i].getName();

					if(Pattern.matches(JExecLockerConfig.KIKAN_LOCK_FILENAME_PATTERN, filename)){
						if (! files[i].delete()) {

							StringBuffer buffer = new StringBuffer();
							buffer.append(ViewSettings.getUsingValueString("tokutei.application.name"));
							buffer.append("の二重起動ロックの解除に失敗しました。");

							successDeleteKikanLockFile = false;

							JOptionPane.showMessageDialog(
									null,
									buffer.toString(),
									"確認",
									JOptionPane.INFORMATION_MESSAGE
								);

							break;
						}
					}
				}

				if (! successDeleteKikanLockFile) {
					System.exit(-1);
				}
			}

			if (successDeleteAdminLockFile || successDeleteKikanLockFile) {
				JOptionPane.showMessageDialog( this, "二重起動ロックの解除に成功しました。", "確認", JOptionPane.INFORMATION_MESSAGE );

				System.exit( 0 );
			}
			/* --------------------------------------------------- */
		}
	}
}
