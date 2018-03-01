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
	class::name	JSimpleTableSplitScrollPanel

	class::expl	カスタムパネルクラス
*/
// ----------------------------------------------------------------------------
public class JSimpleTableSplitScrollPanel extends JSplitPane
{
	private JSimpleTable m_modelL = null;
	private JSimpleTable m_modelR = null;

	private JScrollPane m_paneScrollL = null;
	private JScrollPane m_paneScrollR = null;

	/* Added 2008/03/10 若月  */
	/* --------------------------------------------------- */
	private int splitColumnNum = 0;
	/* --------------------------------------------------- */

	/**
	 *  同期クラス
	 */
	private class JSyncScroll implements ChangeListener
	{
		private JScrollPane m_syncPaneA;
		private JScrollPane m_syncPaneB;

		/**
		 *  コンストラクタ
		 *
		 *    @param  同期元パネル
		 *    @param  同期先パネル
		 *
		 *    @return none
		 */
		public JSyncScroll( JScrollPane paneA, JScrollPane paneB )
		{
			m_syncPaneA = paneA;
			m_syncPaneB = paneB;
		}

		/**
		 *  @listener : stateChanged
		 */
		public void stateChanged( ChangeEvent event )
		{
			m_syncPaneB.setVerticalScrollBar( m_syncPaneA.getVerticalScrollBar() );
		}
	}

	/**
	 *  同期クラス
	 */
	private class JSyncCellSelect implements ListSelectionListener
	{
		private JSimpleTable m_syncModelA;
		private JSimpleTable m_syncModelB;

		/**
		 *  コンストラクタ
		 *
		 *    @param  同期元テーブル
		 *    @param  同期先テーブル
		 *
		 *    @return none
		 */
		public JSyncCellSelect( JSimpleTable modelA, JSimpleTable modelB )
		{
			m_syncModelA = modelA;
			m_syncModelB = modelB;
		}

		/**
		 *  @listener : valueChanged
		 */
		public void valueChanged( ListSelectionEvent event )
		{
			if( m_syncModelA.getSelectedRow() != -1 )
			{
				m_syncModelB.setRowSelectionInterval( m_syncModelA.getSelectedRow() , m_syncModelA.getSelectedRow() );
			}
		}
	}

	/**
	 *  コンストラクタ
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public JSimpleTableSplitScrollPanel( int iSplitColumn )
	{
		super( HORIZONTAL_SPLIT );

		/* Added 2008/03/10 若月  */
		/* --------------------------------------------------- */
		this.splitColumnNum = iSplitColumn;
		/* --------------------------------------------------- */

		//
		//  左テーブル変化時のイベントを取得
		//
		m_modelL = new JSimpleTable()
		{
			private static final long serialVersionUID = 1L;

			public void editingStopped( ChangeEvent e )
			{
				if( m_modelR != null )
				{
					m_modelR.refreshTable();
				}

				super.editingStopped( e );
			}
		};

		//
		//  右テーブル変化時のイベントを取得
		//
		m_modelR = new JSimpleTable()
		{
			private static final long serialVersionUID = 1L;

			public void editingStopped( ChangeEvent e )
			{
				if( m_modelL != null )
				{
					m_modelL.refreshTable();
				}

				super.editingStopped( e );
			}
		};

		m_paneScrollL = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_NEVER , JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
		m_paneScrollR = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );

		add( m_paneScrollL );
		add( m_paneScrollR );

		// テーブル同期
		JSyncCellSelect syncLtoR = new JSyncCellSelect( m_modelL, m_modelR );
		JSyncCellSelect syncRtoL = new JSyncCellSelect( m_modelR, m_modelL );

		m_modelL.getSelectionModel().addListSelectionListener( syncLtoR );
		m_modelR.getSelectionModel().addListSelectionListener( syncRtoL );

		m_paneScrollL.getViewport().addChangeListener( new JSyncScroll( m_paneScrollL, m_paneScrollR ) );
		m_paneScrollR.getViewport().addChangeListener( new JSyncScroll( m_paneScrollL, m_paneScrollR ) );

		m_paneScrollL.setViewportView( m_modelL );
		m_paneScrollR.setViewportView( m_modelR );

		// スプリット位置
		/* Modified 2008/03/07 若月  */
		/* --------------------------------------------------- */
//		setDividerLocation( iSplitColumn * JSimpleTable.COLUMEN_WIDTH );
		/* --------------------------------------------------- */
		this.resetDividerPosition();
		/* --------------------------------------------------- */

		setOneTouchExpandable( true );
	}

	/**
	 * ディバイダの位置を再設定する
     */
	private void resetDividerPosition() {
		int location = 0;
		int columnCount = this.m_modelL.getColumnCount();
		for (int i = 0; i < this.splitColumnNum && i < columnCount; i++) {
//			location += this.m_modelL.getColumn(i).getWidth();
			location += this.m_modelL.getColumnModel().getColumn(0).getWidth();
		}
		this.setDividerLocation(location);
	}

	/**
	 *  パネル更新
	 *
	 *    @param  設置テーブル
	 *
	 *    @return none
	 */
	public void updatePanel( JSimpleTable model )
	{
		m_modelL.clearAllRow   ();
		m_modelL.clearAllColumn();

		m_modelR.clearAllRow   ();
		m_modelR.clearAllColumn();

		for( int i=0; i<model.getColumnCount(); ++i )
		{
			m_modelL.addHeader( model.m_objTableModel.getColumnName( i ) );
			m_modelR.addHeader( model.m_objTableModel.getColumnName( i ) );
		}

		for( int i=0; i<model.getRowCount(); ++i )
		{
			m_modelL.addData( model.getData( i ) );
			m_modelR.addData( model.getData( i ) );
		}

		m_modelL.setCellEditForbid( model.m_vectCellEdit );
		m_modelR.setCellEditForbid( model.m_vectCellEdit );

		m_modelL.setCellColor( model.m_vectCellColor );
		m_modelR.setCellColor( model.m_vectCellColor );

		if( model.m_vectComboBox != null )
		{
			for( int i=0; i<model.m_vectComboBox.size(); ++i )
			{
				m_modelL.setCellComboBoxEditor( model.m_vectComboBox.get( i ).getCellEditor(), model.m_vectComboBox.get( i ).getColumn() );
				m_modelR.setCellComboBoxEditor( model.m_vectComboBox.get( i ).getCellEditor(), model.m_vectComboBox.get( i ).getColumn() );
			}
		}

		if( model.m_vectCheckBox != null )
		{
			for( int i=0; i<model.m_vectCheckBox.size(); ++i )
			{
				m_modelL.setCellCheckBoxEditor( model.m_vectCheckBox.get( i ).getCellEditor(), model.m_vectCheckBox.get( i ).getColumn() );
				m_modelR.setCellCheckBoxEditor( model.m_vectCheckBox.get( i ).getCellEditor(), model.m_vectCheckBox.get( i ).getColumn() );
			}
		}

		// add ver2 s.inoue 2009/05/08
		TableColumnModel lcolumns = m_modelL.getColumnModel();
        for(int i=0;i<lcolumns.getColumnCount();i++) {

        	lcolumns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)m_modelL.getDefaultRenderer(m_modelL.getColumnClass(i))));
        }
		TableColumnModel rcolumns = m_modelR.getColumnModel();
        for(int i=0;i<rcolumns.getColumnCount();i++) {

        	rcolumns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
                (DefaultTableCellRenderer)model.getDefaultRenderer(m_modelR.getColumnClass(i))));
        }


		this.resetDividerPosition();
	}
}


//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}


