package jp.or.med.orca.jma_tokutei.common.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import jp.or.med.orca.jma_tokutei.common.component.ExtendedCheckBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedComboBox;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedRadioButton;

/**
	class::name JSimpleTable
	class::expl テーブル操作クラス
*/
public class JSimpleColorTable extends JTable implements TableModel
{
	private static final long serialVersionUID = -8197573076193113001L;		// edit n.ohkubo 2014/10/01　追加
	
	protected DefaultTableModel m_objTableModel;
	protected TableRowSorter<TableModel> sorter;

	// 初期設定
	protected final static int DEFAULT_COLUMEN_WIDTH  = 100;
	protected final static int DEFAULT_ROW_HEIGHT =  20;

	private int[] columnWidths = null;
	private int[] rowHeights = null;
	// add s.inoue 2009/11/12
	 String[] modelHeader = null;

	// セル制御
	protected Vector<JSimpleTableCellPosition>     m_vectCellEdit  = null;
	protected Vector<JSimpleTableCellRendererData> m_vectCellColor = null;

	protected Vector<JSimpleTableCellEditorData<ExtendedCheckBox>> m_vectCheckBox = null;
	protected Vector<JSimpleTableCellEditorData<JComboCellEditor>> m_vectComboBox = null;
	protected Vector<JSimpleTableCellEditorData<ExtendedRadioButton>> m_vectRadioButton = null;

	// add s.inoue 2010/01/13
	JRadioButton[] buttons;

	// add s.inoue 2009/10/22
	// ツールチップ第二弾 | 文字オーバーの時
	@Override
	public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {

		Component c = super.prepareRenderer(tcr, row, column);
		// edit s.inoue 2010/01/29
	    if (c instanceof JCheckBox || c instanceof JRadioButton ||
	    		c instanceof ExtendedCheckBox || c instanceof ExtendedRadioButton)
	    	return c;

	    if (c instanceof JCheckBox || c instanceof JRadioButton)
      		return c;
	    if(c instanceof JComponent) {
	       JComponent l = (JComponent)c;
	       Object o = getValueAt(row, column);
	       if (o == null)
	    	   return c;

	       Insets i = l.getInsets();
	       Rectangle rect = getCellRect(row, column, false);
	       rect.width -= i.left + i.right;
	       FontMetrics fm = l.getFontMetrics(l.getFont());
	       String str = o.toString();
	       int cellTextWidth = fm.stringWidth(str);
	       l.setToolTipText(cellTextWidth>rect.width?splitToopTipString(str):null);
	    }
	    return c;
	}

	// add s.inoue 2009/10/23
	private String splitToopTipString(String tooltip){
		String substr ="";
		String strTip ="";
		// edit s.inoue 2009/11/27
		final int split = 26;
		int start = 0;
		int end = split;

		if (tooltip.length() <= end)
			return tooltip;

		// edit s.inoue 2009/11/27
		// for→do 25→26 余り処理追加
		// for (int i = 0; i < tooltip.length(); i++) {
		while(tooltip.length() > split ){
			// if (tooltip.length() < split) {
			//	strTip = strTip + tooltip;break;
			//}
			substr = tooltip.substring(0, split-1);
			strTip = strTip + substr + "<br>";
			tooltip = tooltip.substring(split-1);
			start= end;
			end = start + end;
		}
		if (tooltip.length() <= split -1){
			strTip += tooltip;
		}
		return "<html>" + strTip + "</html>";
	}

	/**
	 *  カスタムエディタ管理クラス
	 */
	protected class JSimpleTableCellEditorData< T >
	{
		private T m_cellEditor;
		private Integer m_iColumn;

		/**
		 *  コンストラクタ
		 *
		 *    @param  追加エディタ
		 *    @param  追加位置
		 *
		 *    @return none
		 */
		public JSimpleTableCellEditorData( T cellEditor, int iColumn )
		{
			  m_iColumn = iColumn;
			  m_cellEditor = cellEditor;
		}

		/**
		 *  列取得
		 *
		 *    @param  none
		 *    @return int
		 */
		public int getColumn()
		{
			return m_iColumn;
		}

		/**
		 *  カスタムエディタ
		 *
		 *    @param  none
		 *    @return T
		 */
		public T getCellEditor()
		{
			return m_cellEditor;
		}
	}

	/**
	 *  カスタムセルレンダラクラス : DefaultCellRenderer
	 */
	protected class JSimpleTableCellRenderer extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = -4878880256587298772L;		// edit n.ohkubo 2014/10/01　追加
		
		private Vector<JSimpleTableCellRendererData> m_vectCellList;

		/**
		 *  コンストラクタ
		 *
		 *    @param  設定リスト
		 *    @return none
		 */
		public JSimpleTableCellRenderer( Vector<JSimpleTableCellRendererData> list )
		{
			super();

			setOpaque( true );
			setBorder( BorderFactory.createEmptyBorder( 0, 5, 0, 5 ) );

			m_vectCellList = list;
		}

		private DecimalFormat formater = new DecimalFormat("###,###.###");

		/**
		 *  @Override getTableCellRendererComponent
		 */
		@Override
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col )
		{
			String displayText = null;
			if (value instanceof Number) {
				Number n = (Number) value;

				try {
					displayText = formater.format(n);
				} catch (Exception e) {
					// valueがnullの場合、NullPointerExceptionで落ちる
					displayText = (value != null) ? value.toString() : "";
				}
			}
			if (displayText == null || displayText.isEmpty()) {
				// valueがnullの場合、NullPointerExceptionで落ちる
				displayText = (value != null) ? value.toString() : "";
			}

			super.getTableCellRendererComponent( table, displayText, isSelected, hasFocus, row, col );

			/* 背景色が設定されている場合、選択時にも、指定された色を
			 * 使用するように変更。 */
				boolean isFound = false;

				for( int i=0; i<m_vectCellList.size(); ++i )
				{
					setForeground( table.getForeground() );

					JSimpleTableCellRendererData rendererData = m_vectCellList.get( i );
					Color color = rendererData.getColor();

					/*
					 *  任意行列
					 */
					if( rendererData.getCellPosition().getRow() == row && rendererData.getCellPosition().getColumn() == col )
					{
						setBackground( color );
						isFound = true;
						break;
					}

					/*
					 *  任意列
					 */
					if( rendererData.getCellPosition().getRow() == -1 && rendererData.getCellPosition().getColumn() == col )
					{
						setBackground( color );
						isFound = true;
						break;
					}

					/*
					 *  任意行
					 */
					if( rendererData.getCellPosition().getRow() == row && rendererData.getCellPosition().getColumn() == -1  )
					{
						setBackground( color );
						isFound = true;
						break;
					}

					/*
					 *  全体行列
					 */
					if( rendererData.getCellPosition().getRow() == -1  && rendererData.getCellPosition().getColumn() == -1  )
					{
						setBackground( color );
						isFound = true;
						break;
					}
				}

				if( !isFound )
				{
					setBackground( table.getBackground() );

					if (isSelected) {
						setForeground( table.getSelectionForeground() );
						setBackground( table.getSelectionBackground() );
					}
				}
			// edit s.inoue 2009/09/28
			setHorizontalAlignment(LEFT);
			// setHorizontalAlignment( (value instanceof Number)?RIGHT:LEFT );

			return this;
		}
	}

	/**
	 *  カスタムセルレンダラクラス : ComboBoxCellRenderer
	 */
	/* JComboBox を拡張クラスに置き換え */
	protected class JComboBoxCellRenderer extends ExtendedComboBox implements TableCellRenderer
	{
		private static final long serialVersionUID = -1216292492631231505L;		// edit n.ohkubo 2014/10/01　追加
		
		private final JTextField m_editor;

		/**
		 *  コンストラクタ
		 *
		 *    @param  none
		 *    @return none
		 */
		public JComboBoxCellRenderer()
		{
			super();

			m_editor = (JTextField)( getEditor().getEditorComponent() );

			setEditable       ( true );
			setBorder         ( BorderFactory.createEmptyBorder() );

			m_editor.setOpaque( true );
			m_editor.setBorder( BorderFactory.createEmptyBorder() );
		}

		/**
		 *  @override : getTableCellRendererComponent
		 */
		@Override
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
		{
			removeAllItems();

			if( isSelected )
			{
				m_editor.setForeground( table.getSelectionForeground() );
				m_editor.setBackground( table.getSelectionBackground() );
			}
			else
			{
				m_editor.setForeground( table.getForeground() );
				m_editor.setBackground( table.getBackground() );
			}

			addItem( (value == null) ? "" : value.toString() );

			return this;
		}
	}

	// add s.inoue 2010/01/11
	/**
	 *  カスタムセルレンダラクラス : RadioButtonCellRenderer
	 */
	protected class JRadioButtonRenderer extends JRadioButton implements TableCellRenderer{

		private static final long serialVersionUID = 8264221521583323573L;		// edit n.ohkubo 2014/10/01　追加

		/**
		 *  コンストラクタ
		 *
		 *    @param  none
		 *    @return none
		 */
		public JRadioButtonRenderer()
		{
			super();
		}

		/**
		 *  @override : getTableCellRendererComponent
		 */
		@Override
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
		{
			setHorizontalAlignment( SwingConstants.CENTER );

//			if( isSelected )
//			{
//				setForeground( table.getSelectionForeground() );
//				setBackground( table.getSelectionBackground() );
//			}
//			else
//			{
//				setForeground( table.getForeground() );
//				setBackground( table.getBackground() );
//			}
//
//			setSelected( false );

//			if( value instanceof Boolean )
//			{
//				 setSelected( Boolean.valueOf((Boolean)value) );
//			}
//			else
//			{
//				 setSelected( Boolean.valueOf((String )value) );
//			}

			// edit s.inoue 2010/01/13
			if( value != null )
			{
				setSelected(false);

				if( isSelected ){
					setSelected(true);
					setForeground( table.getSelectionForeground() );
					setBackground( table.getSelectionBackground() );
				}else{
					setSelected(false);
					setForeground( table.getForeground() );
					setBackground( table.getBackground() );
				}
			}
			return this;
		}
	}

	/**
	 *  カスタムセルレンダラクラス : CheckBoxCellRenderer
	 */
	protected class JCheckBoxCellRenderer extends JCheckBox implements TableCellRenderer
	{
		private static final long serialVersionUID = 6633487157312121273L;		// edit n.ohkubo 2014/10/01　追加

		/**
		 *  コンストラクタ
		 *
		 *    @param  none
		 *    @return none
		 */
		public JCheckBoxCellRenderer()
		{
			super();
		}

		/**
		 *  @override : getTableCellRendererComponent
		 */
		@Override
		public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
		{
			setHorizontalAlignment( SwingConstants.CENTER );

			if( isSelected )
			{
				setForeground( table.getSelectionForeground() );
				setBackground( table.getSelectionBackground() );
			}
			else
			{
				setForeground( table.getForeground() );
				setBackground( table.getBackground() );
			}

			setSelected( false );

			if( value != null )
			{
				if( value instanceof Boolean )
				{
					setSelected( Boolean.valueOf((Boolean)value) );
				}
				else
				{
					setSelected( Boolean.valueOf((String )value) );
				}
			}

			return this;
		}
	}

	/**
	 * tablemodel用コンストラクタ
	 * @param tmodel
	 */
	public JSimpleColorTable(DefaultTableModel dm ){

		m_objTableModel = dm;

		// テーブル初期化
		cellInitialize();
	}

	/**
	 *  コンストラクタ
	 *    @param  none
	 *    @return none
	 */
	public JSimpleColorTable()
	{
		m_objTableModel = new DefaultTableModel();

		// テーブル初期化
		cellInitialize();
	}

	/**
	 * セルの初期化
	 */
	private void cellInitialize(){
		// edit s.inoue 2009/12/07
		m_vectCheckBox  = new Vector<JSimpleTableCellEditorData<ExtendedCheckBox>>       ();
		m_vectComboBox  = new Vector<JSimpleTableCellEditorData<JComboCellEditor>>();

		// モデル設定
		setModel( m_objTableModel );

		// サイズ編集
		setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

		setSurrendersFocusOnKeystroke        ( true  );

		getTableHeader().setReorderingAllowed( false );

		/**
		 *  @Override addFocusListener::focusLost
		 */
		DefaultCellEditor cellEdit = (DefaultCellEditor)getDefaultEditor( Object.class );

		cellEdit.getComponent().addFocusListener( new FocusAdapter()
		{
			@Override
			public void focusLost( FocusEvent e )
			{
				if( isEditing() )
				{
					getCellEditor().stopCellEditing();
				}
			}
		} );

		/**
		 *  @Override addMouseListener::mousePressed
		 */
		getTableHeader().addMouseListener( new MouseAdapter()
		{
			@Override
			public void mousePressed( MouseEvent e )
			{
				if( isEditing() )
				{
					getCellEditor().stopCellEditing();
				}

				// edit s.inoue 2010/07/12 行の選択状態変更
				clearSelection();
				int row = getDoubleClickedSelectedRow();
				int col = getSelectedColumn();

				changeSelection(row, col, true, true);
// del s.inoue 2009/12/15
//				// add s.inoue 2009/12/15
//				int[] selection = getSelectedRows();
//					for (int i = 0; i < selection.length; i++) {
//						// テーブル・モデルの行数に変換
//						int modelRow = convertRowIndexToModel(selection[i]);
//						System.out.println("View Row: " + selection
//						+ " Model Row: " + modelRow);
//					}
			}
		} );

		/* フォーカスを取得した時、行数が 0
		 * なら、フォーカスを次に移動する。 */
		this.addFocusListener(new FocusAdapter(){
		    @Override
		    public void focusGained(FocusEvent e) {
			super.focusGained(e);
			JSimpleTable table = new JSimpleTable();
			if (table.getRowCount() == 0) {
			    table.transferFocus();
			}
		    }
		    @Override
		    public void focusLost(FocusEvent arg0) {
			}
		});
	}
	
	// edit n.ohkubo 2014/10/01　削除　絞込み処理（TableRowSorter）使用時に、このメソッドをオーバーライドしている場合、テーブルの最後の行を触ると「JTable.java:4316」でこいつが呼ばれ、その後の「DefaultRowSorter.java:497」で「IndexOutOfBoundsException」になるので、「getSelectedRowsConvertRowIndexToModel」メソッドへ名称変更
//	// add s.inoue 2009/12/15
//	public int[] getSelectedRows() {
//		int[] rows = super.getSelectedRows();
////		if (rows.length == 0){
////			return new int[0];
////		}
//		for (int i = 0, n = rows.length; i < n; i++) {
//		    rows[i] = convertRowIndexToModel(rows[i]);
//		}
//		return rows;
//	}
	// edit n.ohkubo 2014/10/01　追加 start
	public int[] getSelectedRowsConvertRowIndexToModel() {
		int[] rows = super.getSelectedRows();
		for (int i = 0, n = rows.length; i < n; i++) {
		    rows[i] = convertRowIndexToModel(rows[i]);
		}
		return rows;
	}
	// edit n.ohkubo 2014/10/01　追加 end

	// edit s.inoue 2010/07/07 singleClick
	@Override
	public int getSelectedRow() {
		int row = super.getSelectedRow();
		if(row <= 0) return 0;
		return row;
	}

	// edit s.inoue 2010/07/07 doubleClick
	public int getDoubleClickedSelectedRow() {
		int row = super.getSelectedRow();
		if(row <= 0) return 0;
		return convertRowIndexToModel(row);
	}

	// add s.inoue 2009/12/15
	@Override
	public int convertRowIndexToModel(int viewRowIndex){
		return super.convertRowIndexToModel(viewRowIndex);
	}

	/**
	 *  @Override columnMarginChanged
	 */
	@Override
	public void columnMarginChanged( ChangeEvent e )
	{
		if( isEditing() )
		{
			getCellEditor().stopCellEditing();
		}

		super.columnMarginChanged( e );
	}
// del s.inoue 2009/10/25
//	/**
//	 *  行数取得
//	 *
//	 *    @param  none
//	 *    @return int
//	 *
//	 */
//	public int getRowCount()
//	{
//		return m_objTableModel.getRowCount();
//	}
//
//	/**
//	 *  列数取得
//	 *
//	 *    @param  none
//	 *    @return int
//	 */
//	public int getColumnCount()
//	{
//		return m_objTableModel.getColumnCount();
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	/**
//	 *  列幅取得
//	 *
//	 *    @param  列
//	 *    @return int
//	 */
//	private int getColumnWidth( int iColumn )
//	{
//		TableColumn column = getColumnModel().getColumn( iColumn );
//
//		return column.getPreferredWidth();
//	}

	// edit n.ohkubo 2014/10/01　未使用なので削除
//	/**
//	 *  列幅設定
//	 *
//	 *    @param  横幅
//	 *    @param  列
//	 *
//	 *    @return none
//	 */
//	private void setColumnWidth( int iWidth, int iColumn )
//	{
//		TableColumn column = getColumnModel().getColumn( iColumn );
//
//		column.setPreferredWidth( iWidth );
//	}

	// add s.inoue 2009/12/07
//	public void valueChanged(ListSelectionEvent e) {
//
//		if (e.getValueIsAdjusting()) {
//
//		int index = e.getFirstIndex();
//		this.setValueAt(Boolean.TRUE, index, 0);
//		}
//	}

	/**
	 *  カスタムセル：セルに対して編集制限をかける（ 負数を与えると設定行列全てが対象 ）
	 *
	 *    @param  設定配列
	 *
	 *    @return none
	 */
	public void setCellEditForbid( final JSimpleTableCellPosition[] aryList )
	{
		Vector<JSimpleTableCellPosition> list = new Vector<JSimpleTableCellPosition>();

		try
		{
			for( int i=0; i<aryList.length; ++i )
			{
				list.add( aryList[ i ] );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		setCellEditForbid( list );
	}

	// edit s.inoue 2009/10/27
	/**
	 *  カスタムセル：セルに対して編集制限をかける（ 負数を与えると設定行列全てが対象 ）
	 *    @param  設定リスト
	 *    @return none
	 */
	public void setCellEditCustumForbid( DefaultTableModel model,final JSimpleTableCellPosition[] aryList )
	{
		Vector<JSimpleTableCellPosition> list = new Vector<JSimpleTableCellPosition>();

		try
		{
			for( int i=0; i<aryList.length; ++i )
			{
				list.add( aryList[ i ] );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		setCellEditCustumForbid( model,list );
	}
	/**
	 *  カスタムセル：セルに対して編集制限をかける（ 負数を与えると設定行列全てが対象 ）
	 *
	 *    @param  設定リスト
	 *
	 *    @return none
	 */
	public void setCellEditCustumForbid( DefaultTableModel model, final Vector<JSimpleTableCellPosition> list )
	{
		if( list != null )
		{
//			try
//			{
//				DefaultTableModel model = new DefaultTableModel()
//				{
//					/**
//					 *  @override isCellEditable
//					 */
//					public boolean isCellEditable( int row, int col )
//					{
//						for( int i=0; i<list.size(); ++i )
//						{
//							if( list.get(i).getRow() == row && list.get(i).getColumn() == col )
//							{
//								return false;
//							}
//
//							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() == col )
//							{
//								return false;
//							}
//
//							if( list.get(i).getRow() == row && list.get(i).getColumn() <= -1  )
//							{
//								return false;
//							}
//
//							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() <= -1  )
//							{
//								return false;
//							}
//						}
//						return true;
//					}
//				};
//
//				// テーブル複製
//				this.copyTableModel( model );
//				this.setModel( model );
//
//				// テーブル更新
//				refreshTable();
//
//				m_objTableModel = model;
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//
//				return;
//			}

			// 設定情報保存
			m_vectCellEdit = list;
		}
	}

	/**
	 *  カスタムセル：セルに対して編集制限をかける（ 負数を与えると設定行列全てが対象 ）
	 *
	 *    @param  設定リスト
	 *
	 *    @return none
	 */
	public void setCellEditForbid( final Vector<JSimpleTableCellPosition> list )
	{
		if( list != null )
		{
			try
			{
				DefaultTableModel model = new DefaultTableModel()
				{
					private static final long serialVersionUID = 4310808912142006969L;		// edit n.ohkubo 2014/10/01　追加

					/**
					 *  @override isCellEditable
					 */
					@Override
					public boolean isCellEditable( int row, int col )
					{
						for( int i=0; i<list.size(); ++i )
						{
							if( list.get(i).getRow() == row && list.get(i).getColumn() == col )
							{
								return false;
							}

							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() == col )
							{
								return false;
							}

							if( list.get(i).getRow() == row && list.get(i).getColumn() <= -1  )
							{
								return false;
							}

							if( list.get(i).getRow() <= -1  && list.get(i).getColumn() <= -1  )
							{
								return false;
							}
						}
						return true;
					}
				};

				// テーブル複製
				this.copyTableModel( model );
				this.setModel( model );

				// テーブル更新
				refreshTable();

				m_objTableModel = model;
			}
			catch( Exception e )
			{
				e.printStackTrace();

				return;
			}

			// 設定情報保存
			m_vectCellEdit = list;
		}
	}

	/**
	 *  カスタムセル：セルカラーを設定します（ 負数を与えると設定行列全てが対象 ）
	 *
	 *    @param  設定配列
	 *    @return none
	 */
	public void setCellColor( JSimpleTableCellRendererData[] aryList )
	{
		Vector<JSimpleTableCellRendererData> list = new Vector<JSimpleTableCellRendererData>();

		try
		{
			for( int i=0; i<aryList.length; ++i )
			{
				list.add( aryList[ i ] );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		setCellColor( list );
	}

	/**
	 *  カスタムセル：セルカラーを設定します（ 負数を与えると設定行列全てが対象 ）
	 *
	 *    @param  設定リスト
	 *
	 *    @return none
	 */
	public void setCellColor( Vector<JSimpleTableCellRendererData> list )
	{
		if( list != null )
		{
			try
			{
				JSimpleTableCellRenderer renderer = new JSimpleTableCellRenderer( list );

				setDefaultRenderer( Object .class, renderer );
				setDefaultRenderer( Integer.class, renderer );
				setDefaultRenderer( String .class, renderer );
			}
			catch( Exception e )
			{
				e.printStackTrace();

				return;
			}

			refreshTable();

			// 設定情報保持
			m_vectCellColor = list;
		}
	}

	// add s.inoue 2009/09/28
	/**
	 *  カスタムセル：セルカラーを設定します（ 負数を与えると設定行列全てが対象 ）
	 *    @param  設定リスト
	 *    @return none
	 */
	public void setCellColor( Vector<JSimpleTableCellRendererData> list ,JSimpleTableCellRowRenderer renderer)
	{
		if( list != null )
		{
			try
			{
				setDefaultRenderer( Object .class, renderer );
				setDefaultRenderer( Integer.class, renderer );
				setDefaultRenderer( String .class, renderer );
			}
			catch( Exception e )
			{
				e.printStackTrace();

				return;
			}
			refreshTable();
			// 設定情報保持
			m_vectCellColor = list;
		}
	}

	// add s.inoue 2009/11/15
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {

		 return super.getCellRenderer(row, column);
	}
	/**
	 *  カスタムエディタ：チェックボックスをセルに設定します.
	 *
	 *    @param  追加チェックボックス
	 *    @param  追加列
	 *
	 *    @return none
	 */
	public void setCellCheckBoxEditor( ExtendedCheckBox checkEditor, int iColumn )
	{
		TableColumn column;

		column = getColumnModel().getColumn( iColumn );

		// カスタムレンダラ
		column.setCellRenderer( new JCheckBoxCellRenderer() );

		// カスタムエディタ
		column.setCellEditor( new DefaultCellEditor( checkEditor ) );

		// 中央描画
		checkEditor.setHorizontalAlignment( SwingConstants.CENTER );

		// 設定保持
		boolean isFound = false;

		for( int i=0; i<m_vectCheckBox.size(); ++i )
		{
			if( iColumn == m_vectCheckBox.get(i).getColumn() )
			{
				m_vectCheckBox.set( i, new JSimpleTableCellEditorData<ExtendedCheckBox>( checkEditor, iColumn ) );
				isFound = true;
			}
		}

		if( !isFound )
		{
			// edit s.inoue 2009/12/07
			m_vectCheckBox.add( new JSimpleTableCellEditorData<ExtendedCheckBox>( checkEditor, iColumn ) );
		}

		refreshTable();
	}

	/**
	 *  カスタムエディタ：チェックボックスをセルに設定します.
	 *
	 *    @param  追加チェックボックス
	 *    @param  追加列
	 *
	 *    @return none
	 */
	public void setCellRadioButtonEditor( ExtendedCheckBox radioEditor, int iColumn )
	{
		TableColumn column;

		column = getColumnModel().getColumn( iColumn );

		// カスタムレンダラ
		column.setCellRenderer( new JRadioButtonRenderer() );

		// カスタムエディタ
		column.setCellEditor( new RadioButtonEditor( radioEditor ) );

		// 中央描画
		radioEditor.setHorizontalAlignment( SwingConstants.CENTER );

		// 設定保持
		boolean isFound = false;

		for( int i=0; i<m_vectCheckBox.size(); ++i )
		{
			if( iColumn == m_vectCheckBox.get(i).getColumn() )
			{
				m_vectCheckBox.set( i, new JSimpleTableCellEditorData<ExtendedCheckBox>( radioEditor, iColumn ) );
				isFound = true;
			}
		}

		if( !isFound )
		{
			m_vectCheckBox.add( new JSimpleTableCellEditorData<ExtendedCheckBox>( radioEditor, iColumn ) );
		}

		refreshTable();
	}
	 //テスト用に追加↓
///////////////////////////////////////////
//	// add s.inoue 2010/01/12
//	private JRadioButton[] getHistoryNumber(){
//		JRadioButton[] rbs= new JRadioButton[getRowCount()];
//		String ret ="";
//		for (int i = 0; i < getRowCount(); i++) {
//			// Object[] rd = (Object[]) dmodel.getValueAt(i, 0);
//			// JRadioButton rb = (JRadioButton) rd[0];
//			// rbs[i] = new JRadioButton();
//			rbs[i] = (JRadioButton)getValueAt(i, 0);
////			if(rb.isSelected()){
////				ret = (String) getValueAt(i, 3);
////			}
//		}
//		return rbs;
//	}
//	// edit s.inoue 2010/01/13
//	protected class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
//
//	public RadioButtonEditor(JCheckBox checkBox) {
//		super(checkBox);
//		ButtonGroup buttonGroup = new ButtonGroup();
//		JRadioButton[] buttons  = getHistoryNumber();
//		for (int i=0; i<buttons.length; i++) {
//		buttonGroup.add(buttons[i]);
//		buttons[i].addItemListener(this);
//		}
//
//		for (int i = 0; i < buttons.length; i++) {
//			System.out.println("せんたく；"+buttons[i].isSelected());
//		}
//	}
//
//	@Override
//	public void itemStateChanged(ItemEvent e) {
//
//	}
//}
	 //テスト用に追加
///////////////////////////////////////////

	protected class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
		
		private static final long serialVersionUID = -5961967299381562749L;		// edit n.ohkubo 2014/10/01　追加
		
		private JRadioButton button;

		public RadioButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
			if (value==null) return null;
			button = (JRadioButton)value;
			button.addItemListener(this);
			return (Component)value;
		}

		@Override
		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}
	// edit n.ohkubo 2014/10/01　未使用なので削除
//	// 結果(コード)欄のDefaultCellEditorクラスを追加
//	private class InputSelectComboCellEditor extends DefaultCellEditor {
//		private ImeController imeController = null;
//
//		public InputSelectComboCellEditor(JComboBox comboBox) {
//			super(comboBox);
//			// add s.inoue 2009/10/14
//			imeController = new ImeController();
//			imeController.addFocusListenerForCharcterSubsets(comboBox, ImeMode.IME_OFF);
//		}
//	}

	/**
	 *  カスタムエディタ：コンボボックスをセルに設定します.
	 *
	 *    @param  コンボボックスエディタ
	 *    @param  追加列
	 *
	 *    @return none
	 */
	public void setCellComboBoxEditor( JComboCellEditor comboEditor, int iColumn )
	{
		TableColumn column;

		column = getColumnModel().getColumn( iColumn );

		// カスタムエディタ
		column.setCellEditor( comboEditor );

		JComboBoxCellRenderer renderer = new JComboBoxCellRenderer(){
			
			private static final long serialVersionUID = -2196732552811302735L;		// edit n.ohkubo 2014/10/01　追加
			
			Component editorComponent = getEditor().getEditorComponent();	// edit n.ohkubo 2014/10/01　未使用なので削除

			@Override
			public Component getTableCellRendererComponent(
					JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col ){

				/* Modified 2008/03/22 若月 背景色が設定されているセルは、
				 * 選択しても背景色を選択色にしないよう変更。 */
				Component returnComponent = table.getDefaultRenderer(Object.class)
					.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

				return returnComponent;
			}
		};

		column.setCellRenderer( renderer );

		// 設定保持
		boolean isFound = false;

		for( int i=0; i<m_vectComboBox.size(); ++i )
		{
			if( iColumn == m_vectComboBox.get(i).getColumn() )
			{
				m_vectComboBox.set( i, new JSimpleTableCellEditorData<JComboCellEditor>( comboEditor, iColumn ) );

				isFound = true;
			}
		}

		if( !isFound )
		{
			m_vectComboBox.add( new JSimpleTableCellEditorData<JComboCellEditor>( comboEditor, iColumn ) );
		}

		refreshTable();
	}

	/**
	 *  全行削除
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void clearAllRow()
	{
		m_objTableModel.setRowCount( 0 );
	}

	/**
	 *  全列削除
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
	public void clearAllColumn()
	{
		m_objTableModel.setColumnCount( 0 );
	}

	/**
	 *  単行削除
	 *
	 *    @param  削除行
	 *    @return none
	 */
	public void clearRow( int iRow )
	{
		try
		{
			m_objTableModel.removeRow( iRow );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}
	}

	/**
	 *  ヘッダ追加：テーブル終端列にヘッダを追加します.
	 *
	 *    @param  追加ヘッダ
	 *
	 *    @return none
	 */
	public void addHeader( String header )
	{
		m_objTableModel.addColumn( header );

		refreshTable();
	}

	/**
	 *  ヘッダ追加：テーブル終端列にヘッダを追加します.
	 *
	 *    @param  追加ヘッダ
	 *
	 *    @return none
	 */
	public void addHeader( String[] headers )
	{
		// add s.inoue 2009/11/12
		this.modelHeader = headers;

		// del s.inoue 2009/11/12
//		for( int i=0; i<headers.length; ++i )
//		{
//			m_objTableModel.addColumn( headers[ i ] );
//		}
//		refreshTable();
	}

	/**
	 *  データ追加：テーブル終端行にデータを追加します.
	 *
	 *    @param  追加データ
	 *
	 *    @return none
	 */
	public void addData( String[] data )
	{
		try
		{
			m_objTableModel.addRow( data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	// add s.inoue 2010/01/08
	/**
	 *  データ追加：テーブル終端行にデータを追加します.
	 *
	 *    @param  追加データ
	 *
	 *    @return none
	 */
	public void addData( Object[] data )
	{
		try
		{
			m_objTableModel.addRow( data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}
	// add s.inoue 2009/10/22
	/**
	 *  データ追加：テーブル終端行にデータを追加します.
	 *
	 *    @param  追加データ
	 *    @return none
	 */
	public void addDataVector( Object[][] data, Object[] columnHeader)
	{
		try
		{
			m_objTableModel.setDataVector(data, columnHeader);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return;
		}
		// edit s.inoue 2009/10/22 一時
		// refreshTable();
	}

	/**
	 *  データ追加：テーブル終端行にデータを追加します.
	 *
	 *    @param  追加データ
	 *
	 *    @return none
	 */
	public void addData( Vector data )
	{
		try
		{
			m_objTableModel.addRow( data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  データ挿入：行単位のデータを挿入します.
	 *
	 *    @param  挿入データ
	 *    @param  挿入位置
	 *
	 *    @return none
	 */
	public void insertData( String[] data, int iRow )
	{
		try
		{
			m_objTableModel.insertRow( iRow, data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  データ挿入：行に対して行単位のデータを挿入します.
	 *
	 *    @param  挿入データ
	 *    @param  挿入位置
	 *
	 *    @return none
	 */
	public void insertData( Vector data, int iRow )
	{
		try
		{
			m_objTableModel.insertRow( iRow, data );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  データ設定：既に存在するセルに対してデータを設定します.
	 *
	 *    @param  設定データ (Object)
	 *    @param  設定行
	 *    @param  設定列
	 *
	 *    @return none
	 */
	public void setData( Object data, int iRow, int iColumn )
	{
		try
		{
			m_objTableModel.setValueAt( data, iRow, iColumn );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  データ設定：既に存在するセルに対してデータを設定します.
	 *
	 *    @param  設定データ (String)
	 *    @param  設定行
	 *    @param  設定列
	 *
	 *    @return none
	 */
	public void setData( String data, int iRow, int iColumn )
	{
		try
		{
			m_objTableModel.setValueAt( data, iRow, iColumn );
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return;
		}

		refreshTable();
	}

	/**
	 *  データ取得：テーブル全体のデータを取得します.
	 *
	 *    @param  none
	 *
	 *    @return Vector
	 *    @return null   : error of inside method.
	 */
	public Vector getData()
	{
		try
		{
			for( int iRow=0; iRow<m_objTableModel.getDataVector().size(); ++ iRow )
			{
				Vector rowData = (Vector)(m_objTableModel.getDataVector().get( iRow ));

				for( int iColumn=0; iColumn<rowData.size(); ++ iColumn )
				{
					if( rowData.get( iColumn ) == null )
					{
						m_objTableModel.setValueAt( "", iRow, iColumn );
					}
				}
			}

			return m_objTableModel.getDataVector();
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 *  データ取得：行単位のデータを取得します.
	 *
	 *    @param  行
	 *
	 *    @return Vector
	 *    @return null   : don't exist data.
	 */
	public Vector getData( int iRow )
	{
		try
		{
			Vector rowData = (Vector)(m_objTableModel.getDataVector().get( iRow ));

			for( int iColumn=0; iColumn<rowData.size(); ++iColumn )
			{
				if( rowData.get( iColumn ) == null )
				{
					m_objTableModel.setValueAt( "", iRow, iColumn );
				}
			}

			return rowData;
		}
		catch( Exception e )
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 *  データ取得：セル単位のデータを取得します.
	 *
	 *    @param  行
	 *    @param  列
	 *
	 *    @return Object
	 *    @return null   : data doesn't exist.
	 */
	public Object getData( int iRow, int iColmun )
	{
		try
		{
			return m_objTableModel.getValueAt( iRow, iColmun );
		}
		catch( Exception e )
		{
			return null;
		}
	}

	/**
	 *  テーブル更新
	 *
	 *    @param  none
	 *
	 *    @return none
	 */
//	protected void refreshTable()
	public void refreshTable()
	{
		// 縦幅設定
		int rowCount = this.getRowCount();
		for (int i = 0; i < rowCount; i++) {

			int rowHeight = 0;
			if (this.rowHeights != null &&
					this.rowHeights.length > i &&
					rowHeight > 0) {

				rowHeight = this.rowHeights[i];
			}
			else {
				rowHeight = DEFAULT_ROW_HEIGHT;
			}
			this.setRowHeight(i, rowHeight);
		}

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.getColumnModel();

		if (this.columnWidths != null) {

			int columnCount = columnModel.getColumnCount();
			for (int i = 0; i < columnCount; i++) {

				int columnWidth = 0;
				if (this.columnWidths.length > i ) {
						columnWidth = this.columnWidths[i];
				}else {
					columnWidth = DEFAULT_COLUMEN_WIDTH;
				}
				columnModel.getColumn(i).setPreferredWidth(columnWidth);
				columnModel.getColumn(i).setWidth(columnWidth);
			}
		}

		this.resizeAndRepaint();
	}

	/**
	 *  JSimpleTableのデータをDefaultTalbeModel型で複製します.
	 *
	 *    @param  複製元テーブル
	 *    @return none
	 */
	protected void copyTableModel( DefaultTableModel destTableModel )
	{
		Vector vecData = m_objTableModel.getDataVector();

		for( int i=0; i<getColumnCount(); ++i )
		{
			destTableModel.addColumn( m_objTableModel.getColumnName( i ) );
		}

		for( int i=0; i<vecData.size();   ++i )
		{
			destTableModel.addRow( (Vector)(vecData.get( i )) );
		}
	}

	public int[] getRowHeights() {
		return rowHeights;
	}

	public void setRowHeights(int[] rowHeights) {
		this.rowHeights = rowHeights;
	}

	public int[] getColumnWidths() {
		return columnWidths;
	}

	public void setPreferedColumnWidths(int[] columnWidths) {
		this.columnWidths = columnWidths;
	}

	// add s.inoue 2009/09/25
	/*
	 * ヘッダー固定テーブル:選択行を抽出する。
	 */
	public ArrayList<Integer> getselectedRows(JSimpleTable modelfixed,JSimpleTable model){
		int rowCount = model.getRowCount();
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < rowCount; i++) {
			if ((Boolean) modelfixed.getData(i, 0) == Boolean.TRUE) {
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	// add s.inoue 2009/09/25
	/*
	 * 固定テーブル:選択行を抽出する。
	 */
	public ArrayList<Integer> getselectedRows(JSimpleTable model){
		int rowCount = model.getRowCount();
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < rowCount; i++) {
			if ((Boolean) model.getData(i, 0) == Boolean.TRUE) {
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	// add s.inoue 2009/09/25
	/*
	 * 共通：選択行を選択状態にする(先頭チェックボックス用)
	 */
	public void setselectedRows(JSimpleTable modelfixed ,ArrayList<Integer> selectedRows){

		int selCnt = selectedRows.size();
		for (int i = 0; i < selCnt; i++) {
			int selectIdx = selectedRows.get(i);
			modelfixed.setData(true, selectIdx, 0);
			modelfixed.setRowSelectionInterval(selectIdx, selectIdx);
		}
	}

	// add s.inoue 2009/10/13
	@Override
	public boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {

		boolean retValue = super.processKeyBinding(ks, e, condition, pressed);
        //if(!check.isSelected()) return retValue;
        // if(KeyStroke.getKeyStroke('\t').equals(ks) || KeyStroke.getKeyStroke('\n').equals(ks)) {
		if(KeyStroke.getKeyStroke('\t').equals(ks)){
			// System.out.println("tab or enter typed");
            return retValue;
        }

        // if(getInputContext().isCompositionEnabled() && !isEditing() && !pressed && !ks.isOnKeyRelease()) {
		if(!isEditing() && !ks.isOnKeyRelease() && !pressed) {
            int selectedRow = getSelectedRow();
            int selectedColumn = getSelectedColumn();
            if(selectedRow!=-1 && selectedColumn!=-1) {
                boolean dummy = editCellAt(selectedRow, selectedColumn);
                // System.out.println("editCellAt: "+dummy);
            }
        }
        return retValue;

//		return super.processKeyBinding(ks, e, condition, pressed);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {

	}
}

//Source Code Version Tag System v1.00  -- Do not remove --
//Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

