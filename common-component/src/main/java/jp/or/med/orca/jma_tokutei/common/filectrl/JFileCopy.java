package jp.or.med.orca.jma_tokutei.common.filectrl;

import java.io  .*;
import java.util.*;
import java.nio .channels.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;

import org.apache.log4j.Logger;

// ----------------------------------------------------------------------------
/**
	class::name	JFileCopy

	class::expl	ファイルコピー
*/
// ----------------------------------------------------------------------------
public abstract class JFileCopy
{
	// s.inoue 20080807
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(JFileCopy.class);

	/*
	 *  ファイルコピー
	 *
	 *		@param  コピー元
	 *		@param  コピー先
	 *
	 *		@return none
	 */
	public static boolean copyFile( String strIn, String strOut ) throws IOException
	{

        FileChannel in  = null;
        FileChannel out = null;
        if (strOut.indexOf("\\") > 0){
	        String outDirPath = strOut.substring(0, strOut.lastIndexOf(File.separator));
	        File outDir = new File(outDirPath);
	        if (! outDir.exists()) {
	        	if (outDir.mkdirs()) {
	            	return false;
				}
			}
        }

		try
		{
			// logger.info( "コピー元:" + strIn + " " + "コピー先:" + strOut + "へコピー処理を開始しました。");
			in  = new FileInputStream ( strIn  ).getChannel();
			out = new FileOutputStream( strOut ).getChannel();

			out.transferFrom( in, 0, in.size() );
			// logger.info( "コピー元:" + strIn + " " + "コピー先:" + strOut + "へコピー処理を行いました。");
		}
		catch( IOException e )
		{
			e.printStackTrace();
			logger.error("バックアップ処理及びロールバック処理のファイルコピー時、エラーとなりました。");

			return false;
		}
		finally
		{
			try
			{
				in .close();
				out.close();
			}
			catch( IOException e )
			{
				e.printStackTrace();
				logger.error("バックアップ処理及びロールバック処理のファイルコピー時、エラーとなりました。");
				return false;
			}
		}

		return true;
	}

	/*
	 *  ファイルエックスコピー
	 *
	 *		@param  コピー元 ディレクトリ（相対 / 絶対）
	 *		@param	コピー先 ディレクトリ（相対 / 絶対）
	 *
	 *		@return 0x00000000 : 成功
	 *		@return 0x00000001 : ルートディレクトリが存在しない.
	 *		@return 0x00000010 : コピーディレクトリの作成に失敗.
	 *		@return 0x00000100 : ファイルリストの作成に失敗.
	 *		@return 0x00001000 : ファイルコピーの実行に失敗.
	 *		@return 0x11111111 : 致命的エラー.
	 *
	 *		@note : ファイルコピーに途中で失敗しても処理を続行します.
	 */
	public static int xcopyFile( String inDir, String outDir )
	{
		int iResult = 0x00000000;

		try
		{
			Vector<String> aryDirList  = new Vector<String>();
			Vector<String> arySrcList  = new Vector<String>();
			Vector<String> aryDestList = new Vector<String>();

			// リスト作成
			createDirectoryList( inDir, outDir, aryDirList, arySrcList, aryDestList );

			// ディレクトリチェック
			File copyDir = new File( outDir );

			if( !copyDir.exists() )
			{
				if( !copyDir.mkdirs() )
				{
					iResult |= 0x00000001;
				}
			}

			// ディレクトリ構造作成
			for( int i=0; i<aryDirList.size(); ++i )
			{
				File createDir = new File( aryDirList.get( i ) );

				if( !createDir.exists() )
				{
					if( !createDir.mkdirs() )
					{
						iResult |= 0x00000010;
					}
				}
			}

			// ファイルコピー
			if( arySrcList.size() == aryDestList.size() )
			{
				for( int i=0; i<aryDestList.size(); ++i )
				{
					try
					{
						if( !copyFile( arySrcList.get( i ), aryDestList.get( i ) ) )
						{
							iResult |= 0x00001000;
						}
					}
					catch( IOException e )
					{
						e.printStackTrace();
						logger.error("バックアップ処理及びロールバック処理のファイルコピー時、エラーとなりました。");
						iResult |= 0x00001000;
					}
				}
			}
			else
			{
				iResult |= 0x00000100;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error("バックアップ処理及びロールバック処理のファイルコピー時、エラーとなりました。");
			return 0x11111111;
		}

		return iResult;
	}

	/*
	 *  ファイルリスト作成
	 *
	 *		@param  ...
	 *
	 *		@return ...
	 */
	private static void createDirectoryList( String strIn, String strOut, Vector<String> aryDirList, Vector<String> arySrcList, Vector<String> aryDestList )
	{
		File dirList = new File( strIn );

		File[] fileList = dirList.listFiles();

		for( int i=0; i<fileList.length; ++ i )
		{
			if( fileList[ i ].isFile() )
			{
				// コピー元ファイル
				arySrcList .add( fileList[ i ].getAbsolutePath() );

				// コピー先ファイル
				aryDestList.add( strOut + System.getProperty("file.separator") + fileList[ i ].getName() );
			}
			else if( fileList[ i ].isDirectory() )
			{
				String strTemp = strOut + System.getProperty("file.separator") + fileList[ i ].getName();

				// コピー先ディレクトリ
				aryDirList .add( strTemp );

				// 再帰検索
				createDirectoryList( fileList[ i ].getAbsolutePath(), strTemp, aryDirList, arySrcList, aryDestList );
			}
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

