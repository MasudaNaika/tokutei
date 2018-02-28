package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io.File;

/**
 *  Update Utility
 *  
 *    ユーティリティークラス
 */
public class Util
{
	/**
	 * 　String ヌルチェック
	 * 
	 *    @param  評価文字列
	 *    
	 *    @return boolean 
	 */
	public static boolean isNull( String str )
	{
		return (str == null) || ("".equals(trim(str)));
	}
	
	/**
	 *　 文字列の先端終端から例外文字を削除
	 *
	 *    @param  処理文字列
	 *    
	 *    @return String
	 */
	public static String trim( String str )
	{
		int start = -1;
		int end   = -1;
		
		if( str == null )
		{
			return "";
		}
		
		String comp = " " + "　" + "\n";
		
		char[] temp = str.toCharArray();
		
		for( int i = 0; i<temp.length; ++i )
		{
			if( comp.indexOf( temp[i] ) == -1 )
			{
				start = i;
				
				break;
			}
		}
		
		if( start == -1 )
		{
			return "";
		}
		
		for( int i = temp.length - 1; i >= 0; --i )
		{
			if( comp.indexOf( temp[i] ) == -1 )
			{
				end = i;
				
				break;
			}
		}
		
		return str.substring( start, end + 1 );
	}
	
	/**
	 *  ディレクトリ作成
	 *  
	 *    @param  作成ディレクトリ名
	 *    
	 *    @return none
	 */
	public static void makeDir( String fileName )
	{
		if( isNull( fileName ) )
		{
			return;
		}
		
	    File file = new File( fileName );
	    
	    if( file.getParent() != null )
	    {
	    	File dir = new File( file.getParent() );
	    	
	    	if( !dir.exists() )
	    	{
		    	dir.mkdirs();
	    	}
	    }
	}
}
