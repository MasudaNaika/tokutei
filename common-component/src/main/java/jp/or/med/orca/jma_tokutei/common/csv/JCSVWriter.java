package jp.or.med.orca.jma_tokutei.common.csv;

import java.io  .*;
import java.util.*;

// ----------------------------------------------------------------------------
/**
	class::name	JCSVWriter

	class::expl	this is csv writer class.
*/
// ----------------------------------------------------------------------------
abstract class JCSVWriter extends JCSVInterface
{
	/*
	 *  要素テーブル書込
	 *
	 *		@param  書込データ
	 *
	 *		@return none
	 */
	public void writeTable( String[][] data )
	{
		for( int i=0; i<data.length; ++i )
		{
			writeLineTable( data[ i ] );
		}
	}

	/*
	 *  要素テーブル書込
	 *
	 *		@param  書込データ
	 *
	 *		@return none
	 */
	public void writeTable( Vector<Vector<String>> newTable )
	{
		for( int i=0; i<newTable.size(); ++i )
		{
			writeLineTable( newTable.get( i ) );
		}
	}

	/*
	 *  要素テーブル書込
	 *
	 *		@param  書込データ
	 *
	 *		@return none
	 */
	public void writeLineTable( String[] data )
	{
		Vector<String> newTable = new Vector<String>();

		for( int i=0; i<data.length; ++i )
		{
			newTable.add( data[ i ] );
		}

		writeLineTable( newTable );
	}

	/*
	 *  要素テーブル書込
	 *
	 *		@param  書込データ
	 *
	 *		@return none
	 */
	public void writeLineTable( Vector<String> newTable )
	{
		m_vectTable.add( newTable );
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

