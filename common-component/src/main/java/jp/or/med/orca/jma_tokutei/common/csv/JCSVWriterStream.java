package jp.or.med.orca.jma_tokutei.common.csv;

import java.io  .*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.filectrl.JFile;

// ----------------------------------------------------------------------------
/**
	class::name	JCSVWriterStream

	class::expl	this is csv writer stream class.
*/
// ----------------------------------------------------------------------------
public class JCSVWriterStream extends JCSVWriter
{
	private final static String SEPARATOR = System.getProperty("file.separator");
	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 */
	public JCSVWriterStream()
	{
	}

	public void saveCSV( String path ) throws IOException
	{
		saveCSV(path,"UTF-8");
	}
	/*
	 *  ＣＳＶを保存
	 *		@param  ファイルパス
	 *		@return none
	 */
	public void saveCSV( String path ,String charSet) throws IOException
	{
		BufferedWriter writer = null;

		try
		{
			writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( path ), charSet ) );

			for( int i=0; i<m_vectTable.size(); ++i )
			{
				Vector<String> data = m_vectTable.get( i );

				for( int j=0; j<data.size(); ++j )
				{
					String element = (j == data.size() - 1 ? data.get( j ).toString() : data.get( j ).toString() + ",");

					writer.write( element, 0, element.length() );
				}

				writer.newLine();
			}
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();

			throw new IOException();
		}
		catch( IOException e )
		{
			e.printStackTrace();

			throw new IOException();
		}
		finally
		{
			try
			{
				if( writer != null )
				{
					writer.flush();
					writer.close();
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();

				throw new IOException();
			}
		}
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

