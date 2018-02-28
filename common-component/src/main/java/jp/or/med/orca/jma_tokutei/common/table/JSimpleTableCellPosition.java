package jp.or.med.orca.jma_tokutei.common.table;

import java .io   .*;
import java .awt  .*;
import java .util .*;
import java .lang .*;
import javax.swing.*;
import java .awt  .event.*;
import javax.swing.table.*;
import javax.swing.event.*;

// ----------------------------------------------------------------------------
/**
	class::name	JSimpleTableCellPosition

	class::expl	セルポジション
*/
// ----------------------------------------------------------------------------
public class JSimpleTableCellPosition
{
	private int m_iRow;
	private int m_iColumn;

	/**
	 *  コンストラクタ
	 *
	 *    @param  初期行
	 *    @param  初期列
	 *
	 *    @return none
	 */
	public JSimpleTableCellPosition( int iRow, int iColumn )
	{
		m_iRow    = iRow;
		m_iColumn = iColumn;
	}

	/**
	 *  行数取得
	 *
	 *    @param  none
	 *
	 *    @return 行数
	 */
	public int getRow()
	{
		return m_iRow;
	}

	/**
	 *  列数取得
	 *
	 *    @param  none
	 *
	 *    @return 列数
	 */
	public int getColumn()
	{
		return m_iColumn;
	}

	/**
	 *  行数設定
	 *
	 *    @param  行数
	 *
	 *    @return none
	 */
	public void setRow( int row )
	{
		m_iRow = row;
	}

	/**
	 *  列数設定
	 *
	 *    @param  列数
	 *
	 *    @return none
	 */
	public void setColumn( int column )
	{
		m_iColumn = column;
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


