package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

/**
 *  HttpConnecter
 *  
 *    HTTPコネクションクラス
 */
public class HttpConnecter
{
    private static org.apache.log4j.Logger logger = Logger
    .getLogger(HttpConnecter.class);
	/**
	 *  プロキシサーバ設定
	 *  
	 *    @param  プロキシサーバー名
	 *    
	 *    @return none
	 */
	public static void setProxyHost( String host )
	{
		System.setProperty( "http.proxyHost", PropertyUtil.getProperty("http.proxyHost") );
	}
	
	/**
	 *　　プロキシポート設定
	 *
	 *    @param  ポート番号
	 *    
	 *    @return none
	 */
	public static void setProxyPort( String port )
	{
		System.setProperty( "http.proxyPort", PropertyUtil.getProperty("http.proxyPort") );
	}
	
	/**
	 *  プロキシ除外サーバ設定
	 *
	 *    @param  プロキシ除外サーバ設定
	 *    
	 *    @return none
	 */
	public static void setNonProxyHosts( String host )
	{
		System.setProperty( "http.nonProxyHosts", PropertyUtil.getProperty("http.nonProxyHosts") );
	}
	
	/**
	 *  ファイルダウンロード
	 *  
	 *    @param  ダウンロードURL
	 *    @param  ダウンロードディレクトリ
	 *    
	 *    @return none
	 */
	public static void getFile( String strURL, String strDest ) throws Exception
	{
		int i;
		
	    InputStream  in  = null;
	    OutputStream out = null;
	    
	    if( Util.isNull(strURL) )
	    {
	    	LastError.setErrorMessage("Unable to found download URL !!");
	    	
	    	return;
	    }
	    
	    if( Util.isNull(strDest) )
	    {
	    	LastError.setErrorMessage("Unable to found save file name !!");
	    	
	    	return;
	    }
	    
	    // 作業ディレクトリ生成
	    Util.makeDir( strDest );
	    
	    try
	    {
	    	byte[] byteBuf = new byte[ 0x2000 ];
	    	
	    	// ダウンロード先取得
	    	URL url = new URL( strURL );
	    	
	    	in  = url.openStream();
	    	out = new FileOutputStream( strDest );
	    	
	    	while( (i = in.read( byteBuf )) != -1 )
	    	{
	    		out.write( byteBuf, 0, i );
	    	}
	    }
	    catch( FileNotFoundException err )
	    {
        	LastError.setErrorMessage("\""+ strDest + "\"" + " のダウンロードに失敗しました");
        	logger.error("\""+ strDest + "\"" + " のダウンロードに失敗しました");
	    	throw err;
	    }
	    catch( IOException err )
	    {
        	LastError.setErrorMessage("\""+ strDest + "\"" + " のダウンロードに失敗しました");
        	logger.error("\""+ strDest + "\"" + " のダウンロードに失敗しました");
        	
	    	throw err;
	    }
	    finally
	    {
	        try
	        {
	            if( in  != null )
	            {
	                in.close();
	            }
	            
	            if( out != null )
	            {
	                out.flush();
	                out.close();
	            }
	        }
	        catch( Exception err )
	        {
	        	err.printStackTrace();
	        }
	    }
	}
}
