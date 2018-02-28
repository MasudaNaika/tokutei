package jp.or.med.orca.jma_tokutei.common.execlocker;

import java.io  .*;
import java.util.regex.Pattern;

import jp.or.med.orca.jma_tokutei.common.app.JPath;

// ----------------------------------------------------------------------------
/**
	class::name	JExecLocker

	class::expl	起動制限クラス
	
	Modified 2008/03/09 若月
	　コンストラクタによるロック制御を廃止。
	　ファイル名を指定してロックを行なう静的メソッドを追加。
	　ファイル名パターン（正規表現）を指定してロックを行なう静的メソッドを追加。
*/
// ----------------------------------------------------------------------------
public class JExecLocker {
	
	/* Added 2008/05/14 若月  */
	/* --------------------------------------------------- */
	private static File lockFile = null;
	/* --------------------------------------------------- */
	
	private JExecLocker(){
		/* インスタンス生成を禁止する。 */
	}
	
	/**
	 * ファイル名を指定してロックを取得する。 
     */
	public static void getLockWithName(String filepath) throws Exception {
		/* Deleted 2008/05/14 若月  */
		/* --------------------------------------------------- */
//		File lockFile = null;
		/* --------------------------------------------------- */
		
		FileWriter fileWriter = null;
		
		try	{
			lockFile = new File( filepath );
			
			// 二重起動チェック
			if( lockFile.exists() )
			{
				throw new Exception();
			}
			
			// ロックファイル作成
			fileWriter = new FileWriter( lockFile );
			
			fileWriter.write( 0x10 );
		}
		catch( Exception e )
		{
			throw new Exception();
		}
		finally{
			try	{
				if( fileWriter != null )
				{
					fileWriter.close();
					
					// アプリケーション終了時削除
					lockFile.deleteOnExit();
				}
			}
			catch( Exception e ){
				throw new Exception ();
			}
		}
	}
	
	/**
	 * ファイル名のパターン（正規表現）を指定してロックを取得する。 
     */
	public static void getLockWithPattern(String filepath, String pattern) throws Exception {
		
		File file = new File(".");
		File[] files = file.listFiles();
		
		if(files == null){
			throw new Exception();
		}
		
		for(int i = 0 ; files.length > i ; i++)	{
			String filename = files[i].getName();
			
			if(Pattern.matches(pattern, filename)){
				throw new Exception();
			}
		}
		
		getLockWithName(filepath);
	}

	/* Added 2008/05/14 若月  */
	/* --------------------------------------------------- */
	public static File getLockFile() {
		return lockFile;
	}
	/* --------------------------------------------------- */
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

