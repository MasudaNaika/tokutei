package jp.or.med.orca.jma_tokutei.onlineupdate.util;

import java.io  .*;
import java.util.*;

/**
 *  PropertyUtil
 *
 *    設定ファイル操作クラス
 */
public class PropertyUtil
{
	private final static String m_strPropertyFileName = "application.properties";
	
	/**
	 *  設定値取得
	 *
	 *    @param  キー
	 *
	 *    @return VALUE
	 */
	public static String getProperty( String strKey )
	{
		Properties prop = new Properties();
		
		try
		{
			prop.load( new FileInputStream(m_strPropertyFileName) );
		}
		catch( IOException err )
		{
			err.printStackTrace();
			
        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の設定に失敗しました");
			
			return "";
		}
		
		return prop.getProperty( strKey );
	}
	
	/**
	 *  設定値取得
	 *
	 *    @param  キー
	 *    @param  値
	 *
	 *    @return none
	 */
	public static void setProperty( String strKey, String strValue )
	{
		Properties prop = new Properties();
		
		try
		{
			prop.load ( new FileInputStream (m_strPropertyFileName) );
			
			prop.setProperty( strKey, strValue );
			
			prop.store( new FileOutputStream(m_strPropertyFileName), m_strPropertyFileName );
		}
		catch( IOException err )
		{                                                            
			err.printStackTrace();
			
        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の取得に失敗しました");
		}
	}
}
