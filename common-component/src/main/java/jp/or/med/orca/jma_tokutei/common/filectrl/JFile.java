package jp.or.med.orca.jma_tokutei.common.filectrl;
import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class JFile {
	public static void Copy(String Src,String Dest) throws IOException
	{
		FileChannel src;
		src = new FileInputStream(Src).getChannel();
		FileChannel dst = new FileOutputStream(Dest).getChannel();
		dst.transferFrom(src, 0, src.size());
		src.close();
		dst.close();
	}
	
	public static boolean Delete(String Path)
	{
		File f = new File(Path);
		return f.delete();
	}
	
	
	/**
	 * @return カレントディレクトリの取得
	 */
	public static String GetCurrentDirectory()
	{
		File f = new  File(".");
		return f.getAbsoluteFile().getParentFile().toString() + 
						System.getProperties().getProperty("file.separator");
	}
	
	/**
	 *  ディレクトリ削除
	 * 
	 *    @param  対象ディレクトリ
	 *    @param  対象ディレクトリの削除
	 * 
	 *    @return TRUE
	 *    @return FALSE
	 */
	public static boolean deleteDirectory( File directory, boolean isDeleteRoot )
	{
		File[] fileList = directory.listFiles();
		
		if( fileList != null )
		{
			for( int i=0; i<fileList.length; ++ i )
			{
				if( fileList[ i ].isFile() )
				{
					if( !fileList[ i ].delete() )
					{
						return false;
					}
				}
				else
				{
					if( !deleteDirectory( fileList[ i ], isDeleteRoot ) )
					{
						return false;
					}
				}
			}

			if( isDeleteRoot )
			{
				if( !directory.delete() )
				{
					return false;
				}
			}
		}
		
		return true;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

