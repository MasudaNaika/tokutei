//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.event.ActionEvent;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Hashtable;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//
///**
// * 定型文マスタメンテナンス
// */
//public class JKenshinTeikeiMaintenanceFrameCtrl extends JKenshinTeikeiMaintenanceFrame
//{
//	private String patternNumber = null;
//	private JSimpleTable leftTable = new JSimpleTable();
//	private JSimpleTable rightTable = new JSimpleTable();
//	private static Logger logger = Logger.getLogger(JKenshinTeikeiMaintenanceFrameCtrl.class);
//	/**
//	 * @param PatternNum 編集する健診パターンの番号
//	 */
//	public JKenshinTeikeiMaintenanceFrameCtrl()
//	{
//		ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> ResultItem = new Hashtable<String,String>();
//		String Query = null;
//		String resultStr = "";
//
//		//受け渡された健診パターン番号を表示する
//		//this.patternNumber = PatternNum;
//		try{
//			Query = new String("SELECT TEIKEIBUN FROM T_TEIKEIBUN ");
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//
//
//			if (result != null && result.size() > 0) {
//				resultStr = result.get(0).get("TEIKEIBUN");
//			}
//
//
//		}catch(SQLException e){
//			e.printStackTrace();
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3920",this);
//			dispose();
//			return;
//		}
//
//		//jTextField_PatternName.setText(ResultItem.get("K_P_NAME"));
//		//jTextField_PatternName.setEditable(false);
//
//		//テーブルの初期化
//		JSimpleTableScrollPanel leftPanel = new JSimpleTableScrollPanel(leftTable);
//		JSimpleTableScrollPanel rightPanel = new JSimpleTableScrollPanel(rightTable);
//
//		jPanel_Left.add(leftPanel);
//
//		leftTable.addHeader("定型文No");
//		leftTable.addHeader("定型文");
//
//		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {
//				new JSimpleTableCellPosition(-1,-1)
//				};
//
//		leftTable.setCellEditForbid(iSetColumnList);
//		rightTable.setCellEditForbid(iSetColumnList);
//
//		refreshTable();
//
//	}
//
//	/**
//	 * テーブルのリフレッシュを行う
//	 */
//	public void refreshTable()
//	{
//		ArrayList<Hashtable<String,String>> Result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> ResultItem = new Hashtable<String,String>();
//		String Query = null;
//		String[] row = new String[3];
//
//		leftTable.clearAllRow();
//		rightTable.clearAllRow();
//		leftTable.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);
//		rightTable.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);
//
//		try{
//			//左のテーブルの初期設定
//			Query =
//				"SELECT master.KOUMOKU_CD,master.KOUMOKU_NAME,master.KENSA_HOUHOU " +
//									"FROM T_KENSHINMASTER master " +
//									"INNER JOIN " +
//									"T_KENSHIN_P_S syousai " +
//									"ON master.KOUMOKU_CD = syousai.KOUMOKU_CD " +
//									"WHERE syousai.K_P_NO = " + JQueryConvert.queryConvert(this.patternNumber) +
//									"AND " +
//									"master.HKNJANUM = " + JQueryConvert.queryConvert("99999999") + " " +
//									"ORDER BY K_P_NO, LOW_ID";
//
//
//			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//
//			for( int i = 0;i < Result.size();i++ )
//			{
//				ResultItem = Result.get(i);
//				row[0] = ResultItem.get("KOUMOKU_CD");
//				row[1] = ResultItem.get("KOUMOKU_NAME");
//				row[2] = ResultItem.get("KENSA_HOUHOU");
//				leftTable.addData(row);
//			}
//
//			//右のテーブルの初期設定
//
//			Query = new String("SELECT * FROM T_TEIKEIBUN ");
//			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//
//			for( int i = 0;i < Result.size();i++ )
//			{
//				ResultItem = Result.get(i);
//				Integer intTno = Integer.parseInt(ResultItem.get("TEIKEIBUN_NO"));
//				String strIno = intTno.toString();
//
//				row[0] = strIno;
//				row[1] = ResultItem.get("TEIKEIBUN");
//				leftTable.addData(row);
//
//			}
//		}catch(SQLException e)
//		{
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3920",this);
//			e.printStackTrace();
//			dispose();
//			return;
//
//		}
//	}
//
//
//	/**
//	 * 登録処理を行う
//	 */
//	public void register()
//	{
//		String Query = null;
//		try{
//			//項目並び順の抜けをなくすためまずすべて削除する
//			JApplication.kikanDatabase.Transaction();
//
//			Query = new String("DELETE FROM T_KENSHIN_P_S WHERE K_P_NO = " + JQueryConvert.queryConvert(this.patternNumber));
//			JApplication.kikanDatabase.sendNoResultQuery(Query);
//
//			for(int i = 0;i < leftTable.getRowCount();i++)
//			{
//				Query = new String("INSERT INTO T_KENSHIN_P_S (K_P_NO,KOUMOKU_CD,LOW_ID) VALUES (" +
//						JQueryConvert.queryConvertAppendComma(this.patternNumber) +
//						JQueryConvert.queryConvertAppendComma((String)leftTable.getData(i, 0)) +
//						JQueryConvert.queryConvert(String.valueOf(i)) +
//						")");
//
//				JApplication.kikanDatabase.sendNoResultQuery(Query);
//			}
//			JApplication.kikanDatabase.Commit();
//
//			dispose();
//		}catch(SQLException f)
//		{
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3923",this);
//			f.printStackTrace();
//			try{
//				JApplication.kikanDatabase.rollback();
//			}catch(SQLException g)
//			{
//				jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M0000",this);
//				g.printStackTrace();
//				System.exit(1);
//			}
//
//			dispose();
//			return;
//		}
//	}
//
//	/**
//	 * 右のテーブルから左のテーブルに移動
//	 */
//	public void pushedToLeftButton( ActionEvent e )
//	{
//		if(rightTable.getSelectedRowCount() >= 1 )
//		{
//			String[] row = new String[3];
//			int[] selectedRows = rightTable.getSelectedRows();
//			int rowCount = rightTable.getSelectedRowCount();
//			for(int i = 0;i < rowCount;i++ )
//			{
//				row[0] = new String((String)rightTable.getData(selectedRows[i], 0));
//				row[1] = new String((String)rightTable.getData(selectedRows[i], 1));
//				row[2] = new String((String)rightTable.getData(selectedRows[i], 2));
//				leftTable.addData(row);
//			}
//
//			//前から削除するとindexが変わってしまうため後ろから削除
//			for(int i = rowCount ;i > 0;)
//			{
//				i--;
//				rightTable.clearRow(selectedRows[i]);
//			}
//		}
//	}
//
//	/**
//	 * 左のテーブルから右のテーブルへ移動
//	 */
//	public void pushedToRightButton( ActionEvent e )
//	{
//		if(leftTable.getSelectedRowCount() >= 1 )
//		{
//			String[] row = new String[3];
//			int[] selectedRows = leftTable.getSelectedRows();
//			int rowCount = leftTable.getSelectedRowCount();
//			for(int i = 0;i < rowCount;i++ )
//			{
//				row[0] = new String((String)leftTable.getData(selectedRows[i], 0));
//				row[1] = new String((String)leftTable.getData(selectedRows[i], 1));
//				row[2] = new String((String)leftTable.getData(selectedRows[i], 2));
//				rightTable.addData(row);
//			}
//
//			//前から削除するとindexが変わってしまうため後ろから削除
//			for(int i = rowCount ;i > 0;)
//			{
//				i--;
//				leftTable.clearRow(selectedRows[i]);
//			}
//		}
//
//	}
//
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	/**
//	 * キャンセルボタン
//	 */
//	public void pushedCancelButton( ActionEvent e )
//	{
//		dispose();
//	}
//
//	/**
//	 * 登録ボタン
//	 */
//	public void pushedRegisterButton( ActionEvent e )
//	{
//		register();
//	}
//
//	/**
//	 * 取り消しボタン
//	 */
//	public void pushedClearButton( ActionEvent e )
//	{
//		refreshTable();
//	}
//
//	public void actionPerformed( ActionEvent e )
//	{
//		Object source = e.getSource();
//		if( source == jButton_End )
//		{
//			logger.info(jButton_End.getText());
//			pushedEndButton( e );
//		}
//		else if( source == jButton_Register )
//		{
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton( e );
//		}
//		else if( source == jButton_ToLeft )
//		{
//			logger.info(jButton_ToLeft.getText());
//			pushedToLeftButton( e );
//		}
//		else if( source == jButton_ToRight )
//		{
//			logger.info(jButton_ToRight.getText());
//			pushedToRightButton( e );
//		}
//	}
//}
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

