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
	class::name	JSimpleTableCellRendererData

	class::expl	テーブルカスタムレンダラ操作クラス
*/
// ----------------------------------------------------------------------------
public class JSimpleTableCellRendererData
{
	private Color m_color;

	private JSimpleTableCellPosition m_cell;

	/**
	 *  コンストラクタ
	 *
	 *    @param  セル色
	 *    @param  セル位置
	 *
	 *    @return null
	 */
	public JSimpleTableCellRendererData( Color color, JSimpleTableCellPosition cell )
	{
		m_color = color;

		m_cell  = cell;
	}

	/**
	 *  色取得
	 *
	 *    @param  none
	 *
	 *    @return Color
	 */
	public Color getColor()
	{
		return m_color;
	}

	/**
	 *  位置取得
	 *
	 *    @param  none
	 *
	 *    @return JSimpleTableCellPosition
	 */
	public JSimpleTableCellPosition getCellPosition()
	{
		return m_cell;
	}
}



//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

