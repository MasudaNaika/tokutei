package jp.or.med.orca.jma_tokutei.common.csv;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JCSVReaderStream

	class::expl	this is csv reader stream class.
*/
// ----------------------------------------------------------------------------
public class JCSVReaderStream extends JCSVReader
{
	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 */
	public JCSVReaderStream()
	{
	}

	/*
	 *  コンストラクタ
	 *
	 *		@param  none
	 *
	 *		@return none
	 *
	 */
	public JCSVReaderStream( String path ) throws IOException
	{
		openCSV( path );
	}

	/*
	 *  ＣＳＶを開く
	 *
	 *		@param  ファイルパス
	 *
	 *		@return none
	 */
	public void openCSV( String path ) throws IOException
	{
//		BufferedReader reader = null;
//
//		try
//		{
//			String line;
//
//			reader = new BufferedReader( new InputStreamReader( new FileInputStream( path ), "UTF-8" ) );
//
//			do
//			{
//				line = reader.readLine();
//
//				if( line != null )
//				{
//					Vector<String> data = new Vector<String>();
//
//					int iIndex        = 0;
//					int iElementCount = 1;
//
//					for( int i=0; i<line.length(); ++i )
//					{
//						if( line.charAt( i ) == ',' )
//						{
//							iElementCount ++;
//						}
//					}
//
//					char elementBuf[] = new char[ line.length() ];
//
//					for( int i=0; i<iElementCount; ++i )
//					{
//						int iCopyPos = 0;
//
//						while( iIndex < line.length() && line.charAt( iIndex ) != ',' )
//						{
//							elementBuf[ iCopyPos ++ ] = line.charAt( iIndex ++ );
//						}
//
//						iIndex ++;
//
//						if( iCopyPos == 0 )
//						{
//							// 空要素
//							data.add( new String() );
//						}
//						else
//						{
//							// 実要素
//							data.add( String.copyValueOf( elementBuf, 0, iCopyPos ) );
//						}
//					}
//
//					m_vectTable.add( data );
//				}
//
//			}
//			while( line != null );
//		}
//		catch( ArrayIndexOutOfBoundsException e )
//		{
//			e.printStackTrace();
//
//			throw new IOException();
//		}
//		catch( FileNotFoundException e )
//		{
//			e.printStackTrace();
//
//			throw new IOException();
//		}
//		finally
//		{
//			try
//			{
//				if( reader != null )
//				{
//					reader.close();
//				}
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//
//				throw new IOException();
//			}
//		}
		openCSV(path,"UTF-8",',');
	}

	// eidt s.inoue 2013/11/08
	public void openCSV( String path ,String charSet,char sep) throws IOException
	{
		BufferedReader reader = null;

		try
		{
			String line;

			reader = new BufferedReader( new InputStreamReader( new FileInputStream( path ), charSet ) );

			do
			{
				line = reader.readLine();

				if( line != null )
				{
					if (line.length()<=0)
						continue;

					Vector<String> data = new Vector<String>();

					int iIndex        = 0;
					int iElementCount = 1;

					for( int i=0; i<line.length(); ++i )
					{
						// eidt s.inoue 2013/11/08
						// if( line.charAt( i ) == ',' )
						if( line.charAt( i ) == sep )
						{
							iElementCount ++;
						}
					}

					char elementBuf[] = new char[ line.length() ];

					for( int i=0; i<iElementCount; ++i )
					{
						int iCopyPos = 0;

						// eidt s.inoue 2013/11/08
						// while( iIndex < line.length() && line.charAt( iIndex ) != ',' )
						while( iIndex < line.length() && line.charAt( iIndex ) != sep )
						{
							elementBuf[ iCopyPos ++ ] = line.charAt( iIndex ++ );
						}

						iIndex ++;

						if( iCopyPos == 0 )
						{
							// 空要素
							data.add( new String() );
						}
						else
						{
							// 実要素
							data.add( String.copyValueOf( elementBuf, 0, iCopyPos ) );
						}
					}

					m_vectTable.add( data );
				}

			}
			while( line != null );
		}
		catch( ArrayIndexOutOfBoundsException e )
		{
			e.printStackTrace();

			throw new IOException();
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();

			throw new IOException();
		}
		finally
		{
			try
			{
				if( reader != null )
				{
					reader.close();
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();

				throw new IOException();
			}
		}
	}

	/* Modified 2008/07/23 井上  */
	/* --------------------------------------------------- */
	/*
     * 文字列の置換を行う
     * @param input 処理の対象の文字列
     * @param pattern 置換前の文字列
     * @oaram replacement 置換後の文字列
     * @return 置換処理後の文字列
     */
    public String substitute(String input, String pattern, String replacement) {
        // 置換対象文字列が存在する場所を取得
        int index = input.indexOf(pattern);

        // 置換対象文字列が存在しなければ終了
        if(index == -1) {
            return input;
        }

        // 処理を行うための StringBuffer
        StringBuffer buffer = new StringBuffer();

        buffer.append(input.substring(0, index) + replacement);

        if(index + pattern.length() < input.length()) {
            // 残りの文字列を再帰的に置換
            String rest = input.substring(index + pattern.length(), input.length());
            buffer.append(substitute(rest, pattern, replacement));
        }
        return buffer.toString();
    }
    /* --------------------------------------------------- */

    /* Modified 2008/07/23 井上  */
    /* --------------------------------------------------- */
    /*
	 * CSVファイルimportformatフィールド用変換
	 *		@param  quart
	 *		@return none
	 */
	public String rmQuart(String strColmn)
	{
		if (strColmn == null) {
			strColmn ="";
		}

		if (strColmn.indexOf("\"")!=-1)
        {
			strColmn = substitute(strColmn, "\"", "");
        }

		return strColmn;
	}
	/* --------------------------------------------------- */

}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

