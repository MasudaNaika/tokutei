package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.print;

import java.io  .*;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;

/**
 *  PropertyUtil
 *
 *    設定ファイル操作クラス
 *    
 *    Modified 2008/07/25 若月
 *    "file.properties" 以外のファイルでも読み込めるよう、仕様を変更。
 */
public class PropertyUtil{
	/* Modified 2008/07/25 若月  */
	/* --------------------------------------------------- */
//	private final static String m_strPropertyFileName = "file.properties";
	/* --------------------------------------------------- */
//	public final static String PATH_FILE_PROPERTIES = "file.properties";
//	public final static String PATH_PRINT_PROPERTIES = "print.properties";
	/* --------------------------------------------------- */
	
	private String filePath = null;
	
	public PropertyUtil(String filePath) {
		this.setFilePath(filePath);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 *  設定値取得
	 *
	 *    @param  キー
	 *
	 *    @return VALUE
	 */
	/* Modified 2008/07/25 若月  */
	/* --------------------------------------------------- */
//	public static String getProperty( String strKey )
//	{
//		Properties prop = new Properties();
//		
//		try
//		{
//			prop.load( new FileInputStream(m_strPropertyFileName) );
//		}
//		catch( IOException err )
//		{
//			err.printStackTrace();
//			
//        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の設定に失敗しました");
//			
//			return "";
//		}
//		
//		return prop.getProperty( strKey );
//	}
	/* --------------------------------------------------- */
	public String getProperty( String strKey )
	{
		if (this.filePath == null || this.filePath.isEmpty()) {
			/* エラーメッセージ：M0003=エラー,ファイル名が設定されていません。,0,0 */
			JErrorMessage.show("M0003", null);
			return null;
		}
		
		String retValue = "";
		
		Properties prop = new Properties();
		
		FileInputStream fileInputStream = null;
		try
		{
			fileInputStream = new FileInputStream(this.filePath);
			prop.load( fileInputStream );
			retValue = prop.getProperty( strKey );
		}
		catch( IOException err ){
			err.printStackTrace();
        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の設定に失敗しました");
		}
		finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return retValue;
	}
	/* --------------------------------------------------- */
	
	/**
	 *  設定値取得
	 *
	 *    @param  キー
	 *    @param  値
	 *
	 *    @return none
	 */
	/* Modified 2008/07/25 若月  */
	/* --------------------------------------------------- */
//	public static void setProperty( String strKey, String strValue )
//	{
//		Properties prop = new Properties();
//		
//		try
//		{
//			prop.load ( new FileInputStream (m_strPropertyFileName) );
//			
//			prop.setProperty( strKey, strValue );
//			
//			prop.store( new FileOutputStream(m_strPropertyFileName), m_strPropertyFileName );
//		}
//		catch( IOException err )
//		{                                                            
//			err.printStackTrace();
//			
//        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の取得に失敗しました");
//		}
//	}
	/* --------------------------------------------------- */
	public void setProperty( String strKey, String strValue )
	{
		if (this.filePath == null || this.filePath.isEmpty()) {
			/* エラーメッセージ：M0003=エラー,ファイル名が設定されていません。,0,0 */
			JErrorMessage.show("M0003", null);
			return;
		}

		Properties prop = new Properties();
		
		/* Modified 2008/07/25 若月  */
		/* --------------------------------------------------- */
//		try
//		{
//			prop.load ( new FileInputStream (this.filePath) );
//			
//			prop.setProperty( strKey, strValue );
//			
//			prop.store( new FileOutputStream(this.filePath), this.filePath );
//		}
//		catch( IOException err )
//		{                                                            
//			err.printStackTrace();
//			
//        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の取得に失敗しました");
//		}
		/* --------------------------------------------------- */
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try
		{
			fileInputStream = new FileInputStream (this.filePath);
			prop.load ( fileInputStream );
			
			prop.setProperty( strKey, strValue );
			
			fileOutputStream = new FileOutputStream(this.filePath);
			prop.store( fileOutputStream, this.filePath );
		}
		catch( IOException err )
		{                                                            
			err.printStackTrace();
			
        	LastError.setErrorMessage("設定値 \"" + strKey + "\" の取得に失敗しました");
		}
		finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* --------------------------------------------------- */
	}
	/* --------------------------------------------------- */
}
