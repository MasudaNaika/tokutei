//package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame;
//
//import java.awt.Component;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Hashtable;
//import java.util.Iterator;
//
//import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
//import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
//import jp.or.med.orca.jma_tokutei.common.focus.JFocusTraversalPolicy;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTable;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition;
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableScrollPanel;
//import jp.or.med.orca.jma_tokutei.common.app.JApplication;
//
//import javax.swing.JScrollBar;
//import javax.swing.ListSelectionModel;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.TableColumnModel;
//
//import org.apache.log4j.Logger;
//
//import jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellRowRenderer;
//
///**
// * 健診パターンメンテナンス（編集）
// */
//public class JKenshinPatternMaintenanceEditFrameCtrl extends JKenshinPatternMaintenanceEditFrame
//{
//
//	// add h.yoshitama 2009/11/19
//	private static final String CODE_METABO_HANTEI = "9N501000000000011";
//	private static final String CODE_HOKEN_SHIDOU = "9N506000000000011";
//
//	private String patternNumber = null;
//	private JSimpleTable leftTable = new JSimpleTable();
//	private JSimpleTable rightTable = new JSimpleTable();
//
//	/* フォーカス移動制御 */
//	private JFocusTraversalPolicy focusTraversalPolicy = null;
//
//	private static Logger logger = Logger.getLogger(JKenshinPatternMaintenanceEditFrameCtrl.class);
//
//	/**
//	 * @param PatternNum 編集する健診パターンの番号
//	 */
//	public JKenshinPatternMaintenanceEditFrameCtrl(String PatternNum)
//	{
//		ArrayList<Hashtable<String,String>> Result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> ResultItem = new Hashtable<String,String>();
//		String Query = null;
//
//		//受け渡された健診パターン番号を表示する
//		this.patternNumber = PatternNum;
//		try{
//			Query = new String("SELECT K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO = " +
//					JQueryConvert.queryConvert(this.patternNumber));
//			Result = JApplication.kikanDatabase.sendExecuteQuery(Query);
//			ResultItem = Result.get(0);
//		}catch(SQLException e){
//			e.printStackTrace();
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3920",this);
//			dispose();
//			return;
//		}
//
//		jTextField_PatternName.setText(ResultItem.get("K_P_NAME"));
//		jTextField_PatternName.setEditable(false);
//
//		//テーブルの初期化
//		JSimpleTableScrollPanel leftPanel = new JSimpleTableScrollPanel(leftTable);
//		JSimpleTableScrollPanel rightPanel = new JSimpleTableScrollPanel(rightTable);
//
//		jPanel_Left.add(leftPanel);
//		jPanel_Right.add(rightPanel);
//		leftTable.addHeader("項目コード");
//		leftTable.addHeader("項目名");
//		leftTable.addHeader("検査方法");
//		rightTable.addHeader("項目コード");
//		rightTable.addHeader("項目名");
//		rightTable.addHeader("検査方法");
//
//		jp.or.med.orca.jma_tokutei.common.table.JSimpleTableCellPosition iSetColumnList[] = {
//				new JSimpleTableCellPosition(-1,-1)
//				};
//
//		leftTable.setCellEditForbid(iSetColumnList);
//		rightTable.setCellEditForbid(iSetColumnList);
//		// edit s.inoue 2009/09/14
//		leftTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//		rightTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//
//		refreshTable();
//		// add h.yoshitama 2009/11/20
//		updateButtonsState();
//
//		// focus制御
//		// edit s.inoue 2009/10/07
//		// edit h.yoshitama 2009/11/20
//		if((patternNumber.equals("1"))||(patternNumber.equals("2"))){
//			this.focusTraversalPolicy = new JFocusTraversalPolicy();
//			this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//			this.focusTraversalPolicy.setDefaultComponent(jButton_End);
//			this.focusTraversalPolicy.addComponent(jButton_End);
//		}else{
//			this.focusTraversalPolicy = new JFocusTraversalPolicy();
//			this.setFocusTraversalPolicy(this.focusTraversalPolicy);
//			this.focusTraversalPolicy.setDefaultComponent(jButton_ToTop);
//			this.focusTraversalPolicy.addComponent(jButton_ToTop);
//			this.focusTraversalPolicy.addComponent(jButton_ToLeft);
//			this.focusTraversalPolicy.addComponent(jButton_ToRight);
//			this.focusTraversalPolicy.addComponent(jButton_ToDown);
//			this.focusTraversalPolicy.addComponent(jButton_Register);
//			this.focusTraversalPolicy.addComponent(jButton_Clear);
//			this.focusTraversalPolicy.addComponent(jButton_End);
//
//		}
//
//		// add s.inoue 2009/12/02
//		Component comp =null;
//		for (int i = 0; i < focusTraversalPolicy.getComponentSize(); i++) {
//			comp = focusTraversalPolicy.getComponent(i);
//			comp.addKeyListener(this);
//		}
//		leftTable.addKeyListener(this);
//		rightTable.addKeyListener(this);
//	}
//
//	/**
//	 * テーブルのリフレッシュを行う
//	 */
//	public void refreshTable()
//	{
//		ArrayList<Hashtable<String,String>> result = new ArrayList<Hashtable<String,String>>();
//		Hashtable<String,String> resultItem = new Hashtable<String,String>();
//		String query = null;
//		String[] row = new String[3];
//
//		leftTable.clearAllRow();
//		rightTable.clearAllRow();
//		leftTable.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);
//		rightTable.setAutoResizeMode(JSimpleTable.AUTO_RESIZE_ALL_COLUMNS);
//
//		try{
//			//左のテーブルの初期設定
//			query =
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
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//
//			// add s.inoue 2010/03/18
//			JKenshinPatternMaintenanceEditFrameData tuikaCode =
//				new JKenshinPatternMaintenanceEditFrameData();
//
//			// add s.inoue 2010/03/19
//			boolean dokujiFlg= getFunctionSetting("04");
//			// edit s.inoue 2010/04/15
//			HashSet<String> dokujiKoumokuCD = tuikaCode.getDokujiTuikaCD();
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				resultItem = result.get(i);
//
//				// edit s.inoue 2010/03/18
//				String koumokuCD = resultItem.get("KOUMOKU_CD");
//// edit s.inoue 2010/04/21
////				if (dokujiFlg){
////					row[0] = koumokuCD;
////					row[1] = resultItem.get("KOUMOKU_NAME");
////					row[2] = resultItem.get("KENSA_HOUHOU");
////					leftTable.addData(row);
////				}else{
//				if(!dokujiKoumokuCD.contains(koumokuCD)){
//					System.out.println(koumokuCD + resultItem.get("KOUMOKU_NAME"));
//					row[0] = koumokuCD;
//					row[1] = resultItem.get("KOUMOKU_NAME");
//					row[2] = resultItem.get("KENSA_HOUHOU");
//					leftTable.addData(row);
//				}
////				}
//			}
//
//			// 独自追加健診
//			if (dokujiFlg){
//				for( int i = 0;i < result.size();i++ )
//				{
//					resultItem = result.get(i);
//					String koumokuCD = resultItem.get("KOUMOKU_CD");
//					if(dokujiKoumokuCD.contains(koumokuCD)){
//						System.out.println(koumokuCD + resultItem.get("KOUMOKU_NAME"));
//						row[0] = koumokuCD;
//						row[1] = resultItem.get("KOUMOKU_NAME");
//						row[2] = resultItem.get("KENSA_HOUHOU");
//						leftTable.addData(row);
//					}
//				}
//			}
//			// add s.inoue 2010/04/21
//			// 独自追加健診項目
//
//
//			query = new String("SELECT KOUMOKU_CD,KOUMOKU_NAME,KENSA_HOUHOU " +
//							"FROM T_KENSHINMASTER " +
//					"WHERE KOUMOKU_CD " +
//					"NOT IN " +
//					"( " +
//					"SELECT KOUMOKU_CD " +
//					"FROM T_KENSHIN_P_S " +
//					"WHERE K_P_NO = " + JQueryConvert.queryConvert(this.patternNumber) +
//					")" +
//					" AND " +
//					"HKNJANUM = " + JQueryConvert.queryConvert("99999999")
//					);
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(query);
//
//			for( int i = 0;i < result.size();i++ )
//			{
//				resultItem = result.get(i);
//				// edit s.inoue 2010/03/18
//				String koumokuCD = resultItem.get("KOUMOKU_CD");
//				// del s.inoue 2010/04/15
//				// HashSet<String> dokujiKoumokuCD = tuikaCode.getDokujiTuikaCD();
//
//				// edit s.inoue 2010/04/15
////				if (dokujiFlg){
////					row[0] = koumokuCD;
////					row[1] = resultItem.get("KOUMOKU_NAME");
////					row[2] = resultItem.get("KENSA_HOUHOU");
////
////					if((row[0].equals(CODE_METABO_HANTEI))||(row[0].equals(CODE_HOKEN_SHIDOU))){
////						leftTable.addData(row);
////					}else{
////						rightTable.addData(row);
////					}
////				}else{
//					if(!dokujiKoumokuCD.contains(koumokuCD)){
//						row[0] = koumokuCD;
//						row[1] = resultItem.get("KOUMOKU_NAME");
//						row[2] = resultItem.get("KENSA_HOUHOU");
//
//						System.out.println(koumokuCD + resultItem.get("KOUMOKU_NAME"));
//
//						if((row[0].equals(CODE_METABO_HANTEI))||(row[0].equals(CODE_HOKEN_SHIDOU))){
//							leftTable.addData(row);
//						}else{
//							rightTable.addData(row);
//						}
//					}
////				}
//			}
//
//			// add s.inoue 2010/04/21
//			// 独自追加健診項目
//			if (dokujiFlg){
//				for( int i = 0;i < result.size();i++ )
//				{
//					resultItem = result.get(i);
//					// edit s.inoue 2010/03/18
//					String koumokuCD = resultItem.get("KOUMOKU_CD");
//					if(dokujiKoumokuCD.contains(koumokuCD)){
//						row[0] = koumokuCD;
//						row[1] = resultItem.get("KOUMOKU_NAME");
//						row[2] = resultItem.get("KENSA_HOUHOU");
//						rightTable.addData(row);
//					}
//				}
//			}
//
//		}catch(SQLException e)
//		{
//			jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage.show("M3920",this);
//			e.printStackTrace();
//			dispose();
//			return;
//
//		}
//
//		// add ver2 s.inoue 2009/05/08
//		TableColumnModel lcolumns = leftTable.getColumnModel();
//        for(int i=0;i<lcolumns.getColumnCount();i++) {
//
//        	lcolumns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)leftTable.getDefaultRenderer(leftTable.getColumnClass(i))));
//        }
//        // 初期選択
//		if (leftTable.getRowCount() > 0) {
//			leftTable.setRowSelectionInterval(0, 0);
//		}
//
//		TableColumnModel rcolumns = rightTable.getColumnModel();
//        for(int i=0;i<rcolumns.getColumnCount();i++) {
//
//        	rcolumns.getColumn(i).setCellRenderer(new JSimpleTableCellRowRenderer(
//                (DefaultTableCellRenderer)rightTable.getDefaultRenderer(rightTable.getColumnClass(i))));
//        }
//        // 初期選択
//		if (rightTable.getRowCount() > 0) {
//			rightTable.setRowSelectionInterval(0, 0);
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
//						// edit s.inoue 2010/05/07
//						JQueryConvert.queryConvertAppendComma((String)leftTable.getData(i, 0)) +
//						JQueryConvert.queryConvert(String.valueOf(i + 1)) +
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
//	// add ver2 s.inoue 2009/07/07
//	/**
//	 * 左のテーブルで上に移動
//	 */
//	public void pushedToTopButton( ActionEvent e )
//	{
//		if(leftTable.getSelectedRowCount() >= 1 )
//		{
//			int[] leftselectedRows = leftTable.getSelectedRows();
//			int rowCount = leftTable.getSelectedRowCount();
//			// edit s.inoue 2009/11/14
//			String[][] row = new String[rowCount][3];
//
//			if (leftselectedRows[0]-1 < 0){
//				return;
//			}
//
//			// edit s.inoue 2009/11/14
//			for(int i = 0;i < rowCount;i++ )
//			{
//				row[i][0] = new String((String)leftTable.getData(leftselectedRows[i], 0));
//				row[i][1] = new String((String)leftTable.getData(leftselectedRows[i], 1));
//				row[i][2] = new String((String)leftTable.getData(leftselectedRows[i], 2));
//			}
//			// edit s.inoue 2009/11/14
//			for(int i = 0;i < rowCount;i++ )
//			{
//				leftTable.insertData(row[i], leftselectedRows[i]-1);
//			}
//
//			// edit s.inoue 2009/11/14
//			//前から削除するとindexが変わってしまうため後ろから削除
//			for(int i = rowCount ;i > 0;)
//			{
//				i--;
//				leftTable.clearRow(leftselectedRows[i]+rowCount);
//			}
//
//			// edit y.okano 2010/05/12
//			if (leftselectedRows[0]-1 >= 0){
//				// edit y.okano 2010/05/12
//				if (leftselectedRows.length == 1){
//					leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]-1);
//				}else{
//					leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]+rowCount-2);
//				}
//			}else{
//				leftTable.setRowSelectionInterval(0, 0);
//			}
//		}
//	}
//	// add ver2 s.inoue 2009/07/07
//	/**
//	 * 左のテーブルで下に移動
//	 */
//	public void pushedToDownButton( ActionEvent e )
//	{
//		if(leftTable.getSelectedRowCount() >= 1 )
//		{
//
//			int[] leftselectedRows = leftTable.getSelectedRows();
//			int rowCount = leftTable.getSelectedRowCount();
//			// edit s.inoue 2009/11/14
//			// String[] row = new String[3];
//			String[][] row = new String[rowCount][3];
//
//			// if (leftselectedRows[0]+2 > leftTable.getRowCount()){
//			if (leftselectedRows[0]+rowCount+1 > leftTable.getRowCount()){
//				return;
//			}
//
//			// edit s.inoue 2009/11/14
//			for(int i = 0;i < rowCount;i++ )
//			{
//				row[i][0] = new String((String)leftTable.getData(leftselectedRows[i], 0));
//				row[i][1] = new String((String)leftTable.getData(leftselectedRows[i], 1));
//				row[i][2] = new String((String)leftTable.getData(leftselectedRows[i], 2));
//			}
//
//			// add s.inoue 2009/11/14
//			for(int i = 0;i < rowCount;i++ )
//			{
//				leftTable.insertData(row[i], leftselectedRows[i]+rowCount+1);
//			}
//
//			//前から削除するとindexが変わってしまうため後ろから削除
//			for(int i = rowCount ;i > 0;)
//			{
//				i--;
//				leftTable.clearRow(leftselectedRows[i]);
//			}
//
//			if (leftselectedRows[0]+1 < leftTable.getRowCount()){
//				// edit s.inoue 2010/05/12
//				if (leftselectedRows.length == 1){
//					leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+1);
//				}else{
//					leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+rowCount);
//				}
//			}else{
//				leftTable.setRowSelectionInterval(0, 0);
//			}
//		}
//	}
//
//	/**
//	 * 右のテーブルから左のテーブルに移動
//	 */
//	public void pushedToLeftButton( ActionEvent e )
//	{
//		// add ver2 s.inoue 2009/07/07 見直し
//		if(rightTable.getSelectedRowCount() >= 1 )
//		{
//			String[] row = new String[3];
//			int[] rightselectedRows = rightTable.getSelectedRows();
//			int[] leftselectedRows = leftTable.getSelectedRows();
//			int rowCount = rightTable.getSelectedRowCount();
//
//			// edit s.inoue 2009/09/14 ソート順を修正
//			for(int i = rowCount-1;i >= 0;i-- )
//			{
//				row[0] = new String((String)rightTable.getData(rightselectedRows[i], 0));
//				row[1] = new String((String)rightTable.getData(rightselectedRows[i], 1));
//				row[2] = new String((String)rightTable.getData(rightselectedRows[i], 2));
//				// edit ver2 s.inoue 2009/09/07
//				if (leftselectedRows.length > 0){
//					leftTable.insertData(row, leftselectedRows[0]+1);
//				}else{
//					leftTable.insertData(row, 0);
//				}
//			}
//
//			//前から削除するとindexが変わってしまうため後ろから削除
//			for(int i = rowCount ;i > 0;)
//			{
//				i--;
//				rightTable.clearRow(rightselectedRows[i]);
//			}
//
//			// edit s.inoue 2009/09/14
//			// 選択処理
//			if (rightTable.getRowCount() > 0){
//				if(rightselectedRows.length == 0){
//					leftTable.setRowSelectionInterval(0,0);
//				}else if (rightselectedRows[0] > 0){
//					// →の時は、下に下がっていく
//					rightTable.setRowSelectionInterval(rightselectedRows[0]-1, rightselectedRows[0]-1);
//				}else{
//					rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]);
//				}
//			}
//
//			// edit s.inoue 2009/09/14
//			if (leftTable.getRowCount() > 0){
//				if(leftselectedRows.length == 0){
//					leftTable.setRowSelectionInterval(0,0);
//				}else if (leftselectedRows[0] >= 0){
//					// →の時は、下に下がっていく
//					// leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]-1);
//					//edit y.okano 2010/05/12
//					if (rightselectedRows.length > 1){
//						leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+rightselectedRows.length);
//					}else{
//						leftTable.setRowSelectionInterval(leftselectedRows[0]+1, leftselectedRows[0]+1);
//					}
//				// edit s.inoue 2010/05/12
//				}else if (leftselectedRows[0] == 0){
//					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]);
//				}else{
//					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]+ rowCount);
//				}
////				}else{
////					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]);
////				}
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
//			int[] rightselectedRows = rightTable.getSelectedRows();
//			int[] leftselectedRows = leftTable.getSelectedRows();
//			int rowCount = leftTable.getSelectedRowCount();
//			String retMessage = "";
//			String kekkaMessage="";
//			// add h.yoshitama 2009/11/19
//			for(int aa : leftselectedRows){
//				String koumokuCode = (String)leftTable.getData(aa).get(0);
//				String koumokuName =  (String)leftTable.getData(aa).get(1);
//				if((koumokuCode.equals(CODE_METABO_HANTEI))||(koumokuCode.equals(CODE_HOKEN_SHIDOU))){
//					kekkaMessage ="項目コード：" + koumokuCode
//						 			+ "、項目名：" + koumokuName;
//
//					if(!retMessage.isEmpty()){
//						retMessage = retMessage + "[改行]"+ kekkaMessage;
//					}else{
//						retMessage = kekkaMessage;
//					}
//				}
//			}
//			// add h.yoshitama 2009/11/19
//			if(!retMessage.isEmpty()){
//				String[] params = {retMessage};
//				JErrorMessage.show("M5521", this, params);
//				return;
//			}
//
//
//			// edit s.inoue 2009/09/14 ソート順を修正
//			for(int i = rowCount-1;i >= 0;i-- )
//			{
//				row[0] = new String((String)leftTable.getData(leftselectedRows[i], 0));
//				row[1] = new String((String)leftTable.getData(leftselectedRows[i], 1));
//				row[2] = new String((String)leftTable.getData(leftselectedRows[i], 2));
//				// edit ver2 s.inoue 2009/09/07
//				if (rightselectedRows.length > 0){
//					rightTable.insertData(row, rightselectedRows[0]);
//				}else{
//					rightTable.insertData(row, 0);
//				}
//			}
//
//			//前から削除するとindexが変わってしまうため後ろから削除
//			for(int i = rowCount ;i > 0;)
//			{
//				i--;
//				leftTable.clearRow(leftselectedRows[i]);
//			}
//
//			// edit s.inoue 2009/09/14
//			// 選択処理
//			if (rightTable.getRowCount() > 0){
//				if(rightselectedRows.length == 0){
//					rightTable.setRowSelectionInterval(0,0);
//					// edit y.okano 2010/05/12
//				}else if (rightselectedRows[0] >= 0){
//					// edit s.inoue 2010/05/12
//					// rightTable.setRowSelectionInterval(rightselectedRows[0]-1, rightselectedRows[0]-1);
//					// edit y.okano 2010/05/12
//					if (leftselectedRows.length > 1){
//						rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]+leftselectedRows.length-1);
//					}else {
//						rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]);
//					}
//				}else if (rightselectedRows[0] == 0){
//						rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]);
//				}else{
//					rightTable.setRowSelectionInterval(rightselectedRows[0], rightselectedRows[0]+ rowCount);
//				}
//			}
//
//			// edit s.inoue 2009/09/14
//			if (leftTable.getRowCount() > 0){
//				if(leftselectedRows.length == 0){
//					leftTable.setRowSelectionInterval(0,0);
//				}else if (leftselectedRows[0] > 0){
//					// →の時は、上に上がっていく
//					leftTable.setRowSelectionInterval(leftselectedRows[0]-1, leftselectedRows[0]-1);
//				}else{
//					leftTable.setRowSelectionInterval(leftselectedRows[0], leftselectedRows[0]);
//				}
//			}
//
//		}
//
//	}
//
//	// add s.inoue 2010/03/18
//	private boolean getFunctionSetting(String functionCode){
//
//		ArrayList<Hashtable<String, String>> result = null;
//		boolean retflg = false;
//
//		try{
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT FUNCTION_CD,FUNCTION_FLG");
//			sb.append(" FROM T_SCREENFUNCTION ");
//			sb.append(" WHERE SCREEN_CD = ");
//			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_MASTER_PATTERN_CODE));
//			sb.append(" AND FUNCTION_CD = ");
//			sb.append(JQueryConvert.queryConvert(functionCode));
//
//			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
//		}catch(Exception ex){
//			logger.error(ex.getMessage());
//		}
//
//		Hashtable<String, String> item = result.get(0);
//		retflg = item.get("FUNCTION_FLG").equals("1")?true:false;
//
//		return retflg;
//	}
//
//	/**
//	 * 終了ボタン
//	 */
//	public void pushedEndButton( ActionEvent e )
//	{
////		register();
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
//		else if( source == jButton_Cancel )
//		{
//			logger.info(jButton_End.getText());
//			pushedCancelButton( e );
//		}
//		else if( source == jButton_Register )
//		{
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton( e );
//		}
//		else if( source == jButton_Clear )
//		{
//			logger.info(jButton_Clear.getText());
//			pushedClearButton( e );
//		}
//		else if( source == jButton_ToTop )
//		{
//			logger.info(jButton_ToTop.getText());
//			pushedToTopButton( e );
//		}
//		else if( source == jButton_ToDown )
//		{
//			logger.info(jButton_ToDown.getText());
//			pushedToDownButton( e );
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
//
//	@Override
//	public void keyPressed(KeyEvent keyEvent) {
//		switch(keyEvent.getKeyCode()){
//		case KeyEvent.VK_F1:
//			logger.info(jButton_End.getText());
//			pushedEndButton(null);break;
//
////		case KeyEvent.VK_F5:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToTop.getText());
////				pushedToTopButton(null);break;
////			}
////		case KeyEvent.VK_F6:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToLeft.getText());
////				pushedToLeftButton(null);break;
////			}
////		case KeyEvent.VK_F7:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToRight.getText());
////				pushedToRightButton(null);break;
////			}
////		case KeyEvent.VK_F8:
////			if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
////				logger.info(jButton_ToDown.getText());
////				pushedToDownButton(null);break;
////			}
//		case KeyEvent.VK_F11:
//			logger.info(jButton_Clear.getText());
//			pushedClearButton(null);break;
//		case KeyEvent.VK_F12:
//			logger.info(jButton_Register.getText());
//			pushedRegisterButton(null);break;
//		}
//	}
//
//	// add h.yoshitama 2009/11/20
//	/**
//	 * ボタンの状態を更新する。
//	 */
//	private void updateButtonsState() {
//		boolean toTopEnable = false;
//		boolean toLeftEnable = false;
//		boolean toRightEnable = false;
//		boolean toDownEnable = false;
//		boolean registerEnable = false;
//		boolean clearEnable = false;
//
//		/* パターン番号が 1でないかつ2でない場合 */
//		if(!(patternNumber.equals("1")) && !(patternNumber.equals("2"))){
//			toTopEnable = true;
//			toLeftEnable = true;
//			toRightEnable = true;
//			toDownEnable = true;
//			registerEnable = true;
//			clearEnable = true;
//		}
//		jButton_ToTop.setEnabled(toTopEnable);
//		jButton_ToLeft.setEnabled(toLeftEnable);
//		jButton_ToRight.setEnabled(toRightEnable);
//		jButton_ToDown.setEnabled(toDownEnable);
//		jButton_Register.setEnabled(registerEnable);
//		jButton_Clear.setEnabled(clearEnable);
//	}
//}
//
////Source Code Version Tag System v1.00  -- Do not remove --
////Product-Tag: {4F85C2F4EE-5847B3BB7A9-ADC5AC59E3-EF66F79A1}

