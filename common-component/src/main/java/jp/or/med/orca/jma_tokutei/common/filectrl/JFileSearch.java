package jp.or.med.orca.jma_tokutei.common.filectrl;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JFileSearch

	class::expl	ファイル検索
*/
// ----------------------------------------------------------------------------
public class JFileSearch
{
	private Vector<String> m_vectFiles;

	private Vector<String> m_vectDirectory;

	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JFileSearch()
	{
		m_vectFiles     = new Vector<String>();

		m_vectDirectory = new Vector<String>();
	}
	
	/*
	 *  ディレクトリ検索初期化
	 *  
	 *  	@param  none
	 *  
	 *  	@return none
	 *  
	 *  	@ToDo   検索処理を実行する前にこのメソッドを呼び出す事.
	 *  
	 */
	public void initializer()
	{
		m_vectFiles    .clear();
		m_vectDirectory.clear();
	}

	/*
	 *  ディレクトリ検索
	 *
	 *		@param  検索ディレクトリ
	 *
	 *		@return none
	 *
	 */
	public void searchDirectory( String dir )
	{
		File dirList = new File( dir );

		// 検索
		File[] fileList = dirList.listFiles();

		if( fileList != null )
		{
			for( int i=0; i<fileList.length; ++i )
			{
				if( fileList[ i ].isFile() )
				{
					m_vectFiles.add( fileList[ i ].getAbsolutePath() );
				}
				else if( fileList[ i ].isDirectory() )
				{
					// 再帰検索
					searchDirectory    ( fileList[ i ].getAbsolutePath() );
	
					m_vectDirectory.add( fileList[ i ].getAbsolutePath() );
				}
			}
		}
	}
	
	/*
	 *  ディレクトリ検索
	 *
	 *		@param  検索ディレクトリ
	 *		@param  検索マッチ
	 *
	 *		@return none
	 *
	 */
	public void searchDirectory( String dir, String match )
	{
		File dirList = new File( dir );

		// 検索
		File[] fileList = dirList.listFiles();

		if( fileList != null )
		{
			for( int i=0; i<fileList.length; ++i )
			{
				if( fileList[ i ].isFile() )
				{
					String path = fileList[ i ].getName();
					
					if( path.matches( match ) )
					{
						m_vectFiles.add( fileList[ i ].getAbsolutePath() );
					}
				}
				else if( fileList[ i ].isDirectory() )
				{
					// 再帰検索
					searchDirectory    ( fileList[ i ].getAbsolutePath(), match );
	
					m_vectDirectory.add( fileList[ i ].getAbsolutePath() );
				}
			}
		}
	}

	/*
	 *  ファイルリスト取得
	 *
	 *		@param  none
	 *
	 *		@return Vector<String>
	 *
	 */
	public Vector<String> getFileList()
	{
		return m_vectFiles;
	}

	/*
	 *  ディレクトリリスト取得
	 *
	 *		@param  none
	 *
	 *		@return Vector<String>
	 *
	 */
	public Vector<String> getDirectoryList()
	{
		return m_vectDirectory;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

