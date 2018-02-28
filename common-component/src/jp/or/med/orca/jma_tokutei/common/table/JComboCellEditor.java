package jp.or.med.orca.jma_tokutei.common.table;

import java .awt  .*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java .util .*;
import javax.swing.*;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ImeController;
import jp.or.med.orca.jma_tokutei.common.component.ImeController.ImeMode;

/**
	class::name	JComboCellEditor
	class::expl	テーブルカスタムエディタ
*/
public class JComboCellEditor extends DefaultCellEditor
{
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel m_comboModel  = null;
	private HashMap<Integer, Vector<String>> m_hashItems = null;
	private ImeController imeController = null;

	/**
	 *  コンストラクタ
	 *    @param  none
	 *    @return none
	 */
	public JComboCellEditor( ExtendedComboBox combo )
	{
		super( combo );

		// add s.inoue 2009/10/14
		imeController = new ImeController();
		imeController.addFocusListenerForCharcterSubsets(combo, ImeMode.IME_OFF);

		m_hashItems  = new HashMap<Integer, Vector<String>>();
		m_comboModel = (DefaultComboBoxModel)combo.getModel();
	}

	/**
	 *  要素追加
	 *
	 *    @param  行
	 *    @param  要素
	 *
	 *    @return none
	 */
	public void addItem( int iRow, String stAddItem )
	{
		Vector<String> memory = (Vector<String>)(m_hashItems.get( Integer.valueOf(iRow) ));

		if( memory == null )
		{
			memory = new Vector<String>();

			memory.add( stAddItem );

			m_hashItems.put( Integer.valueOf(iRow), memory );
		}
		else
		{
			memory.add( stAddItem );
		}
	}

	/**
	 *  要素削除
	 *
	 *    @param  行
	 *
	 *    @return none
	 */
	public void delItems( int iRow )
	{
		m_hashItems.remove( Integer.valueOf(iRow) );
	}

	/**
	 *  全要素削除
	 *
	 *    @param  行
	 *
	 *    @return none
	 *
	 */
	public void delAllItems()
	{
		m_hashItems.clear();
	}

	/**
	 *  @override : getTableCellEditorComponent
	 */
	public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column )
	{
		Vector<String> items = m_hashItems.get( Integer.valueOf(row) );

		m_comboModel.removeAllElements();

		if( items != null )
		{
			for( int i=0; i<items.size(); ++i )
			{
				m_comboModel.addElement( items.get(i) );
			}
		}

		m_comboModel.setSelectedItem( value );

		return super.getTableCellEditorComponent( table, value, isSelected, row, column );
	}


}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


