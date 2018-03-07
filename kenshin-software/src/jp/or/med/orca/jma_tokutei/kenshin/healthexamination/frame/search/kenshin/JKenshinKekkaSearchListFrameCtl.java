package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Serche;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.form.client.FormController;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinKekkaSearchListFrameCtl
		extends GridController
		implements GridDataLocator,IKenshinKekkaSearchListFrame {

	private static final long serialVersionUID = -5836866636048971719L;	// edit n.ohkubo 2014/10/01　追加
	
	private JKenshinKekkaSearchListFrame grid = null;
	private Connection conn = null;
//	private JKenshinKekkaSearchListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除
	private static Logger logger = Logger.getLogger(JKenshinKekkaSearchListFrameCtl.class);
	
//	// 検索ボタン押下時のSQLで使用
//	private static final String[] SQL_SELECT_PART = {
//			"'' as CHECKBOX_COLUMN,",
//			"kojin.UKETUKE_ID,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.BIRTHDAY,",
//			"kojin.SEX,",
//			"case kojin.SEX when 1 then '男性' when 2 then '女性' end as SEX_NAME,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.JYUSHIN_SEIRI_NO,",
//			"kojin.HIHOKENJYASYO_KIGOU,",
//			"kojin.HIHOKENJYASYO_NO,",
//			"kojin.HKNJANUM,",
//			// edit s.inoue 2009/10/31
//			"kojin.SIHARAI_DAIKOU_BANGO,",
//			// edit s.inoue 2009/10/31
//			"kojin.NAME,",
//			"kojin.KANANAME,",
//			"tokutei.KENSA_NENGAPI,",
//			"tokutei.HANTEI_NENGAPI,",
//			"tokutei.TUTI_NENGAPI,",
//			// add ver2 s.inoue 2009/05/22
//			"GET_NENDO.NENDO, ",
//			"case tokutei.KEKA_INPUT_FLG when 1 then '未' "
//					+ "when 2 then '済' else '未' end as KEKA_INPUT_FLG " };


//	/**
//	 * 結果データ入力ボタン
//	 */
//	public void showKekkaRegisterFrame(boolean isCopy) {
//		// edit s.inoue 2010/04/27
//	//	ArrayList<Integer> selectedRow = new ArrayList<Integer>();
//	//
//	//	// 現在チェックされている行のリストを抽出
//	//	for (int i = 0; i < table.getRowCount(); i++) {
//	//		if (table.getData(i, 0) == Boolean.TRUE) {
//	//			selectedRow.add(i);
//	//		}
//	//	}
//		setSelectedRows();
//
//		// 一つ選択されている場合のみ画面遷移を行う
//		if (selectedRows.size() == 1) {
//			resultItem = result.get(selectedRows.get(0));
//
//			// edit ver2 s.inoue 2009/06/01
//			if (isCopy){
//				/* T_KOJIN Dao を作成する。 */
//				try {
//					tKojinDao = (TKojinDao) DaoFactory.createDao(
//							JApplication.kikanDatabase.getMConnection(), new TKojin());
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
//
//				// 複製コピー時、受付ID採番
//				long uketukeId = -1L;
//				try {
//					uketukeId = tKojinDao.selectNewUketukeId();
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//				}
//
//				// add ver2 s.inoue 2009/06/01
//				validatedData.setUketukePre_id(resultItem.get("UKETUKE_ID"));
//				validatedData.setUketuke_id(new Long(uketukeId).toString());
//
//			}else{
//				validatedData.setUketuke_id(resultItem.get("UKETUKE_ID"));
//			}
//
//			validatedData.setHihokenjyaCode(resultItem.get("HIHOKENJYASYO_KIGOU"));
//			validatedData.setHihokenjyaNumber(resultItem.get("HIHOKENJYASYO_NO"));
//			validatedData.setName(resultItem.get("KANANAME"));
//			validatedData.setHokenjyaNumber(resultItem.get("HKNJANUM"));
//			validatedData.setSex(resultItem.get("SEX"));
//			validatedData.setBirthDay(resultItem.get("BIRTHDAY"));
//
//			String kensaJissiDate = resultItem.get("KENSA_NENGAPI");
//
//			if (kensaJissiDate == null) {
//				kensaJissiDate = new String("");
//			}
//
//			JScene.CreateDialog(
//					this,
//					new JKekkaRegisterFrameCtrl(validatedData, kensaJissiDate, isCopy),
//					new WindowRefreshEvent());
//		} else {
//			JErrorMessage.show(
//					"M3520", this);
//		}
//	}

	// add s.inoue 2012/11/16
	private boolean firstViewFlg =true;

	public JKenshinKekkaSearchListFrameCtl(Connection conn) {
		this.conn = conn;
		JApplication.selectedHistoryRows = new ArrayList<Integer>();
		grid = new JKenshinKekkaSearchListFrame(conn, this);
	}

	public JKenshinKekkaSearchListFrame getGridControl(){
		return grid;
	}

	/**
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the column header; this text will be automatically translated according to internationalization settings
	*/
	@Override
	public String getHeaderTooltip(String attributeName) {
	    return attributeName;
	}

    /**
	* @param row row index in the grid
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
	*/
	@Override
	public String getCellTooltip(int row,String attributeName) {
		String item ="";
		try {
			item = (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
		}catch (Exception e) {}
		return item;
//		return attributeName+" at row "+row;
	}

	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
	@Override
	public boolean beforeInsertGrid(GridControl grid) {
// del
//		new JShiharaiMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
	}

	/**
     * Callbackメソッド
     * exportダイアログを表示する前に呼び出す
     * gridに定義されたformatを再定義して呼び出せます
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 @Override
	public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

	 // add s.inoue 2011/04/26
	/**
	 * Callback method called when the data loading is completed.
	 * @param error <code>true</code> if an error occours during data loading, <code>false</code> if data loading is successfully completed
	 */
	 @Override
	public void loadDataCompleted(boolean error) {
//	    frame.getControlCurrency().setCurrencySymbol("$");
//	    frame.getControlCurrency().setDecimals(2);
//	    frame.getControlCurrency().setDecimalSymbol('.');
//	    frame.getControlCurrency().setGroupingSymbol(',');
//		JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)grid.getMainPanel().getVOModel().getValueObject();
//	    frame.getGrid().getOtherGridParams().put("empCode",vo.getEmpCode());
//	    frame.getGrid().reloadData();

		// add s.inoue 2012/11/07
		setSelectedRow(JApplication.selectedHistoryRows);

		// eidt s.inoue 2011/05/10
		// ViewSettings.getGridPageSize()
		int rowCount = ((grid.currentPage - 1)*JApplication.gridViewSearchCount) + grid.currentRow + 1;
		// add s.inoue 2012/12/05
		if (grid.getGrid().getTable().getstartIndex() != 0)
			rowCount = grid.getGrid().getTable().getstartIndex() + grid.currentRow + 1;

		grid.currentTotalRows = rowCount;
		grid.countText.setText(rowCount + "件目");
	 }

	// チェックボックス設定
	private void setSelectedRow(ArrayList<Integer> selectedRows){
		// add s.inoue 2013/02/26
		if (selectedRows == null)return;

		// edit s.inoue 2013/10/24
		// if (selectedRows.size() <= 1)return;
		if (grid.getGrid().getVOListTableModel().getRowCount() == 0)return;

		for (int i = 0; i < selectedRows.size(); i++) {
			grid.getGrid().getVOListTableModel().setValueAt("N", selectedRows.get(i), 0);
			grid.getGrid().getVOListTableModel().setValueAt("Y", selectedRows.get(i), 0);
		}
	}

	// edit n.ohkubo 2014/10/01　start　検索条件の保持等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）  
//	/**
//	 * gridのデータがロードされた時のCallback関数
//	 * @param PREVIOUS_BLOCK_ACTION:前行へ移動, NEXT_BLOCK_ACTION:次行へ移動 or LAST_BLOCK_ACTION:最終行へ移動
//	 * @param startPos start position of data fetching in result set
//	 * @param filteredColumns filtered columns
//	 * @param currentSortedColumns sorted columns
//	 * @param currentSortedVersusColumns ordering versus of sorted columns
//	 * @param valueObjectType v.o. type
//	 * @param otherGridParams other grid parameters
//	 * @return response from the server: an object of type VOListResponse
//	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
//	 */
//	public Response loadData(int action, int startIndex, Map filteredColumns,
//			ArrayList currentSortedColumns,
//			ArrayList currentSortedVersusColumns, Class valueObjectType,
//			Map otherGridParams) {
//		// PreparedStatement stmt = null;
//		try {
//			ArrayList vals = new ArrayList();
//			Map attribute2dbField = new HashMap();
//			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
//			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
//			attribute2dbField.put("NAME","NAME");
//			attribute2dbField.put("HIHOKENJYASYO_KIGOU","TK.HIHOKENJYASYO_KIGOU");
//			attribute2dbField.put("HIHOKENJYASYO_NO","TK.HIHOKENJYASYO_NO");
//			attribute2dbField.put("KENSA_NENGAPI","TT.KENSA_NENGAPI");
//			attribute2dbField.put("SEX","SEX");
//			// eidt s.inoue 2013/03/25
//			// attribute2dbField.put("KEKA_INPUT_FLG","KEKA_INPUT_FLG");
//			attribute2dbField.put("KEKA_INPUT_FLG","GET_INPUT.INPUT_FLG");
//			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
//			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
//			attribute2dbField.put("HKNJANUM","HKNJANUM");
//			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","SIHARAI_DAIKOU_BANGO");
//			attribute2dbField.put("KANANAME","KANANAME");
//			attribute2dbField.put("HANTEI_NENGAPI","TT.HANTEI_NENGAPI");
//			attribute2dbField.put("TUTI_NENGAPI","TT.TUTI_NENGAPI");
//			attribute2dbField.put("NENDO","GET_NENDO.NENDOS");
//
//
//			// add s.inoue 2012/10/12
////			Map pfilteredColumns = new HashMap<String,String>();
//
//			// this.filteredColumns = new ArrayList();
////		    Iterator it = filteredColumns.values().iterator();
//
////		    if (!it.hasNext()){
//
////		    	FilterWhereClause[] filterClauses = null;
////		    	filterClauses[0] = new FilterWhereClause();
////		    	filterClauses[0].setAttributeName("NENDO");
////		    	filterClauses[0].setValue("2012");
//
////		    	filteredColumns.put(
////		    			"status",
////		    			new FilterWhereClause[]{new FilterWhereClause("NENDO",Consts.EQ,"2012"),
////		    			null}
////	    		      );
////
////		    	System.out.println("done");
//
////		    	filteredColumns.put("status",new FilterWhereClause[]{
////		    		        new FilterWhereClause("status",Consts.EQ,"E"),
////		    		        null
////		    		      });
//
////		    	GridParams gridParams = (GridParams) command.getInputParam();
////		    	FilterWhereClause clauseDesign = new FilterWhereClause();
////                 clauseDesign.setAttributeName("NENDO");
////                 clauseDesign.setOperator("=");
////                 clauseDesign.setValue("2012");
////
////                 filteredColumns.add(filterClauses[0]);
//
//
////                 gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
////                                 new FilterWhereClause[] { clauseDesign, null });
////		    }
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
//			// add s.inoue 2013/11/06
//			// 初期値 又は あいまい検索の設定
//		    if (currentSortedColumns.iterator().hasNext()){
//				Iterator it_dt = currentSortedColumns.iterator();
//				Object sortClauses = null;
//
//			    // add s.inoue 2013/11/06
//			    JApplication.currentSortedColumns = new ArrayList();
//
//			    while(it_dt.hasNext()) {
//			      sortClauses = (Object)it_dt.next();
//			      // add s.inoue 2013/11/06
//			      JApplication.currentSortedColumns.add(sortClauses);
//			    }
//		    }
//
//		    // add s.inoue 2013/11/19
//		    JApplication.currentSorted = new HashMap<Integer, String>();		    
//		    int keymap = 0;
//		    
//		    if (currentSortedVersusColumns.iterator().hasNext()){
//				Iterator it_dt = currentSortedVersusColumns.iterator();
//				Object sortClauses = null;
//
//			    // add s.inoue 2013/11/06
//				JApplication.currentSortedVersusColumns = new ArrayList();
//			    
//			    while(it_dt.hasNext()) {
//			      sortClauses = (Object)it_dt.next();
//			      // add s.inoue 2013/11/06
//			      JApplication.currentSortedVersusColumns.add(sortClauses);
//			      // add s.inoue 2013/11/19
//			      JApplication.currentSorted.put(keymap, (String)sortClauses);
//			      keymap++;
//			    }
//		    }
//
//			// 初期値 又は あいまい検索の設定
//		    if (filteredColumns.values().iterator().hasNext()){
//				Iterator it_dt = filteredColumns.values().iterator();
//			    FilterWhereClause[] filterClauses = null;
//
//			    // add s.inoue 2013/11/06
//			    int ii = 0;
//			    JApplication.clauseDesign = new FilterWhereClause[filteredColumns.size()];
//
//			    // add s.inoue 2013/03/25
//			    while(it_dt.hasNext()) {
//			      filterClauses = (FilterWhereClause[])it_dt.next();
//
//			      if (filterClauses[0].getOperator().equals("like")){
//			    	  // add s.inoue 2014/03/18
//			    	  String filterval = filterClauses[0].getValue().toString();
//			    	  if(!filterval.startsWith("%"))
//			    		  filterval = "%"+filterval+"%";
//			    	  filterClauses[0].setValue(filterval);
//
//						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//								new FilterWhereClause[] { filterClauses[0], null });
//// eidt s.inoue 2013/03/25
////			      }else if (filterClauses[0].getOperator().equals("like")){
////			    	  System.out.println("");
//// del s.inoue 2013/03/26
////			      }else{
////			    	  filterClauses[0].setValue(filterClauses[0].getValue());
////					  gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
////								new FilterWhereClause[] { filterClauses[0], null });
//
//			      }
//
//			      // add s.inoue 2013/11/06
//			      JApplication.clauseDesign[ii] = new FilterWhereClause();
//				  JApplication.clauseDesign[ii].setAttributeName(filterClauses[0].getAttributeName());
//				  JApplication.clauseDesign[ii].setOperator(filterClauses[0].getOperator());
//				  
//				  // edit n.ohkubo 2014/08/21
////				  // edit s.inoue 2014/03/18
////		    	  String filterval = filterClauses[0].getValue().toString();
//				  String filterval;
//				  Object obj = filterClauses[0].getValue();
//				  if (obj == null) {
//					  filterval = "";
//				  } else {
//					  filterval = obj.toString();
//				  }
//		    	  
//		    	  // JApplication.clauseDesign[ii].setValue(filterClauses[0].getValue());
//		    	  if(filterval.startsWith("%"))
//		    		  filterval = filterval.replaceAll("%", "");
//		    	  JApplication.clauseDesign[ii].setValue(filterval);
//				  ii++;
//			    }
//		    }else{
//		    	// eidt s.inoue 2012/11/16
//		    	if (firstViewFlg){
//
//		    		// add s.inoue 2013/11/06
//		    		if(JApplication.clauseDesign != null){
//		    			for (int i = 0; i < JApplication.clauseDesign.length; i++) {
//			    			gridParams.getFilteredColumns().put(JApplication.clauseDesign[i].getAttributeName(),
//									new FilterWhereClause[] { JApplication.clauseDesign[i], null });
//						}
//		    		}else{
//						DateFormat format = new SimpleDateFormat("yyyy");
//						// eidt s.inoue 2013/01/21
//						// 今年→本年度
//						// String cYear = format.format(new Date());
//						String cYear = String.valueOf(FiscalYearUtil.getThisYear());
//
//				    	// 年度は健診実施日が空の場合があるので省略
//						FilterWhereClause clauseDesign = new FilterWhereClause();
//						clauseDesign.setAttributeName("NENDO");
//						clauseDesign.setOperator("=");
//						clauseDesign.setValue(cYear);
//
//						gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//								new FilterWhereClause[] { clauseDesign, null });
//		    		}
//		    		
//// del s.inoue 2013/11/19
////		    		// add s.inoue 2013/11/06
////		    		if(JApplication.currentSortedColumns != null){
////		    			for (int i = 0; i < JApplication.currentSortedColumns.size(); i++) {
////			    			gridParams.getCurrentSortedColumns().add(JApplication.currentSortedColumns.get(i));
////						}
////		    		}
////
////		    		if(JApplication.currentSortedVersusColumns != null){
////		    			for (int i = 0; i < JApplication.currentSortedVersusColumns.size(); i++) {
////			    			gridParams.getCurrentSortedVersusColumns().add(JApplication.currentSortedVersusColumns.get(i));
////						}
////		    		}
//
//		    	}
//		    }
//
//			// ViewSettings.getGridPageSize()
//			int curpage = startIndex/JApplication.gridViewSearchCount + action;
//
//			grid.currentPage = curpage;
//			// System.out.println("現在ページ:" + grid.currentPage);
//
//			// eidt s.inoue 2011/04/08
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}
//
////			Set<String> keys = filteredColumns.keySet();
////			System.out.println(keys.toArray()[0]);
////			String keyColumn = (String) keys.toArray()[0];
////			boolean bln = filteredColumns.containsKey(keyColumn);
////			boolean bln2 = filteredColumns.containsValue("'00000000001'");
////			System.out.println(bln + "   " + bln2);
////			HashMap<Object, Object> hm = filteredColumns.get(keyColumn);
////			System.out.println(hm.get(keyColumn));
////			String[] obj = (String[]) keys.toArray();
////			System.out.println(obj[0]);
//
//			// openswing s.inoue 2011/01/14
//			// String sql = buildSQL();
//			StringBuilder sb = new StringBuilder();
//
//			sb.append("SELECT '' as CHECKBOX_COLUMN, GET_NENDO.NENDOS,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO,");
//			sb.append("TK.SEX SEX,TK.BIRTHDAY BIRTHDAY,");
//			sb.append("TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
//			sb.append("TK.KANANAME KANANAME,TT.HANTEI_NENGAPI,TT.TUTI_NENGAPI,");
//			sb.append("TT.KENSA_NENGAPI,");
//			// eidt s.inoue 2013/03/26
//			sb.append("GET_INPUT.INPUT_FLG");
//			// eidt s.inoue 2012/10/24
//			// sb.append("case TT.KEKA_INPUT_FLG when 1 then '未' when 2 then '済' else '未' end as KEKA_INPUT_FLG ");
//			// eidt s.inoue 2013/03/26
//			// sb.append("case TT.KEKA_INPUT_FLG when 1 then '1' when 2 then '2' else '1' end as KEKA_INPUT_FLG ");
//			sb.append(" FROM T_KOJIN TK");
//			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
//			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");
//
//			// eidt s.inoue 2012/11/05
//
//			// 年度がヌルの場合に年度を当て込む
//			DateFormat format = new SimpleDateFormat("yyyy");
//			// eidt s.inoue 2013/01/24
//			// String cYear = format.format(new Date());
//			String cYear = String.valueOf(FiscalYearUtil.getThisYear());
//
//			// sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when TT.NENDO is not null then TT.NENDO ");
//			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
//			// eidt s.inoue 2012/11/16
//			// sb.append(" case when TT.NENDO is null then '"+ cYear+ "' ");
//			sb.append(" case when TT.KENSA_NENGAPI is null then '"+ cYear+ "' ");
//
//			sb.append(" when substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//			sb.append(" then CAST(substring(TT.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1  else substring(TT.KENSA_NENGAPI FROM 1 FOR 4) end as NENDOS ");
//			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_NENDO  ON GET_NENDO.UKETUKE_ID = TT.UKETUKE_ID ");
//			sb.append(" AND GET_NENDO.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//
//			// eidt s.inoue 2013/03/26
//			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
//			sb.append(" case TT.KEKA_INPUT_FLG when 1 then '1' when 2 then '2' else '1' end as INPUT_FLG ");
//			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_INPUT ");
//			sb.append(" ON GET_INPUT.UKETUKE_ID = TT.UKETUKE_ID  AND GET_INPUT.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//
//			// eidt s.inoue 2011/07/28
//			// add s.inoue 2012/11/21
//			if (currentSortedColumns.size()==0  &&
//					currentSortedVersusColumns.size()==0){
//				sb.append(" ORDER BY GET_NENDO.NENDOS DESC,KANANAME,BIRTHDAY,TT.KENSA_NENGAPI DESC");
//			}
//
//			// add s.inoue 2012/12/05
//			firstViewFlg = false;
//
//			cnterrflg = false;
//			// eidt s.inoue 2011/05/10
//			// ViewSettings.getGridPageSize()
//			return QueryUtil
//					.getQuery(
//							conn,
//							new UserSessionParameters(),
//							sb.toString(),
//							vals,
//							attribute2dbField,
//							JKenshinKekkaSearchListFrameData.class,
//							"Y", "N",
//							null, gridParams, JApplication.gridViewSearchCount, true);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return new ErrorResponse(ex.getMessage());
//		}
//	}
	/**
	 * gridのデータがロードされた時のCallback関数
	 * @param PREVIOUS_BLOCK_ACTION:前行へ移動, NEXT_BLOCK_ACTION:次行へ移動 or LAST_BLOCK_ACTION:最終行へ移動
	 * @param startPos start position of data fetching in result set
	 * @param filteredColumns filtered columns//Map<String, FilterWhereClause[]>
	 * @param currentSortedColumns sorted columns//List<String>
	 * @param currentSortedVersusColumns ordering versus of sorted columns//List<String>
	 * @param valueObjectType v.o. type//Class<E> valueObjectType
	 * @param otherGridParams other grid parameters
	 * @return response from the server: an object of type VOListResponse
	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
	 */
	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns, ArrayList currentSortedColumns, ArrayList currentSortedVersusColumns, Class valueObjectType, Map otherGridParams) {
		
		try {
			Map<String, String> attribute2dbField = new HashMap<String, String>();
			// edit n.ohkubo 2015/08/01　削除　start　Firebird2.5でも正常に動くように修正（年度の表示対応）
//			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
//			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
//			attribute2dbField.put("NAME","NAME");
//			attribute2dbField.put("HIHOKENJYASYO_KIGOU","TK.HIHOKENJYASYO_KIGOU");
//			attribute2dbField.put("HIHOKENJYASYO_NO","TK.HIHOKENJYASYO_NO");
//			attribute2dbField.put("KENSA_NENGAPI","TT.KENSA_NENGAPI");
//			attribute2dbField.put("KENSA_NENGAPI2","TT.KENSA_NENGAPI");
//			attribute2dbField.put("SEX","SEX");
//			attribute2dbField.put("KEKA_INPUT_FLG","GET_INPUT.INPUT_FLG");
//			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
//			attribute2dbField.put("BIRTHDAY2","BIRTHDAY");
//			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
//			attribute2dbField.put("HKNJANUM","HKNJANUM");
//			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","SIHARAI_DAIKOU_BANGO");
//			attribute2dbField.put("KANANAME","KANANAME");
//			attribute2dbField.put("HANTEI_NENGAPI","TT.HANTEI_NENGAPI");
//			attribute2dbField.put("TUTI_NENGAPI","TT.TUTI_NENGAPI");
//			attribute2dbField.put("NENDO","GET_NENDO.NENDOS");
			// edit n.ohkubo 2015/08/01　削除　end　Firebird2.5でも正常に動くように修正（年度の表示対応）
			
			// edit n.ohkubo 2015/08/01　追加　start　Firebird2.5でも正常に動くように修正（年度の表示対応）
			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
			attribute2dbField.put("NAME","NAME");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","HIHOKENJYASYO_NO");
			attribute2dbField.put("KENSA_NENGAPI","KENSA_NENGAPI");
			attribute2dbField.put("KENSA_NENGAPI2","KENSA_NENGAPI");
			attribute2dbField.put("SEX","SEX");
			attribute2dbField.put("KEKA_INPUT_FLG","INPUT_FLG");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("BIRTHDAY2","BIRTHDAY");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("HKNJANUM","HKNJANUM");
			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","SIHARAI_DAIKOU_BANGO");
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("HANTEI_NENGAPI","HANTEI_NENGAPI");
			attribute2dbField.put("TUTI_NENGAPI","TUTI_NENGAPI");
			attribute2dbField.put("NENDO","NENDOS");
			// edit n.ohkubo 2015/08/01　追加　end　Firebird2.5でも正常に動くように修正（年度の表示対応）

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);
			
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//検索・ソート条件の設定
			FilterSetting filterSetting = new FilterSetting(JApplication.SCREEN_SHOSAI_CODE, grid.getColumnContainer());
			filterSetting.setFilterSort(firstViewFlg, grid.getSavedJCheckBox(), gridParams, filteredColumns, currentSortedColumns, currentSortedVersusColumns, new String[]{"NENDO", "="});

			//ページの設定
			int curpage = startIndex/JApplication.gridViewSearchCount + action;
			grid.currentPage = curpage;
			// System.out.println("現在ページ:" + grid.currentPage);

			
			//一覧取得SQL作成
			StringBuilder sb = new StringBuilder();
			// edit n.ohkubo 2015/08/01　削除　start　Firebird2.5でも正常に動くように修正（年度の表示対応）
//			sb.append("SELECT '' as CHECKBOX_COLUMN, GET_NENDO.NENDOS,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO,");
//			sb.append("TK.SEX SEX,TK.BIRTHDAY BIRTHDAY,");
//			sb.append("TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
//			sb.append("TK.KANANAME KANANAME,TT.HANTEI_NENGAPI,TT.TUTI_NENGAPI,");
//			sb.append("TT.KENSA_NENGAPI,");
//			sb.append("GET_INPUT.INPUT_FLG");
//			sb.append(" FROM T_KOJIN TK");
//			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
//			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");
//
//			// 年度がヌルの場合に年度を当て込む
//			String cYear = String.valueOf(FiscalYearUtil.getThisYear());
//			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
//			sb.append(" case when TT.KENSA_NENGAPI is null then '"+ cYear+ "' ");
//			sb.append(" when substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//			sb.append(" then CAST(substring(TT.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1  else substring(TT.KENSA_NENGAPI FROM 1 FOR 4) end as NENDOS ");
//			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_NENDO  ON GET_NENDO.UKETUKE_ID = TT.UKETUKE_ID ");
//			sb.append(" AND GET_NENDO.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,");
//			sb.append(" case TT.KEKA_INPUT_FLG when 1 then '1' when 2 then '2' else '1' end as INPUT_FLG ");
//			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_INPUT ");
//			sb.append(" ON GET_INPUT.UKETUKE_ID = TT.UKETUKE_ID  AND GET_INPUT.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//
//			if (currentSortedColumns.size()==0  &&
//					currentSortedVersusColumns.size()==0){
//				sb.append(" ORDER BY GET_NENDO.NENDOS DESC,KANANAME,BIRTHDAY,TT.KENSA_NENGAPI DESC");
//			}
			// edit n.ohkubo 2015/08/01　削除　end　Firebird2.5でも正常に動くように修正（年度の表示対応）
			
			// edit n.ohkubo 2015/08/01　追加　start　Firebird2.5でも正常に動くように修正（年度の表示対応）
			//※FROM句の後に副問い合わせにしているのは、パフォーマンス対応と、WHERER句で"年度と入力"を使用するため
			String cYear = String.valueOf(FiscalYearUtil.getThisYear());	// 年度がヌルの場合に年度を当て込む
			sb.append("SELECT ");
			sb.append("'' AS CHECKBOX_COLUMN, ");
			sb.append("NENDOS, ");
			sb.append("UKETUKE_ID, ");
			sb.append("NAME, ");
			sb.append("HIHOKENJYASYO_KIGOU, ");
			sb.append("HIHOKENJYASYO_NO, ");
			sb.append("SEX, ");
			sb.append("BIRTHDAY, ");
			sb.append("JYUSHIN_SEIRI_NO, ");
			sb.append("HKNJANUM, ");
			sb.append("SIHARAI_DAIKOU_BANGO, ");
			sb.append("KANANAME, ");
			sb.append("HANTEI_NENGAPI, ");
			sb.append("TUTI_NENGAPI, ");
			sb.append("KENSA_NENGAPI, ");
			sb.append("INPUT_FLG ");
			sb.append("FROM ( ");
			sb.append("SELECT ");
			sb.append("CASE ");
			sb.append("WHEN ");
			sb.append("TT.KENSA_NENGAPI IS NULL ");
			sb.append("THEN  ");
			sb.append("'"+ cYear+ "' ");
			sb.append("WHEN ");
			sb.append("SUBSTRING(TT.KENSA_NENGAPI FROM 5 FOR 2) = '01' OR SUBSTRING(TT.KENSA_NENGAPI FROM 5 FOR 2) = '02' OR SUBSTRING(TT.KENSA_NENGAPI FROM 5 FOR 2) = '03' ");
			sb.append("THEN ");
			sb.append("CAST(SUBSTRING(TT.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
			sb.append("ELSE ");
			sb.append("SUBSTRING(TT.KENSA_NENGAPI FROM 1 FOR 4) ");
			sb.append("END AS NENDOS, ");
			sb.append("TK.UKETUKE_ID, ");
			sb.append("TK.NAME, ");
			sb.append("TK.HIHOKENJYASYO_KIGOU, ");
			sb.append("TK.HIHOKENJYASYO_NO, ");
			sb.append("TK.SEX, ");
			sb.append("TK.BIRTHDAY, ");
			sb.append("TK.JYUSHIN_SEIRI_NO, ");
			sb.append("TK.HKNJANUM, ");
			sb.append("TK.SIHARAI_DAIKOU_BANGO, ");
			sb.append("TK.KANANAME, ");
			sb.append("TT.HANTEI_NENGAPI, ");
			sb.append("TT.TUTI_NENGAPI, ");
			sb.append("TT.KENSA_NENGAPI, ");
			sb.append("CASE TT.KEKA_INPUT_FLG ");
			sb.append("WHEN ");
			sb.append("2 ");
			sb.append("THEN ");
			sb.append("'2' ");
			sb.append("ELSE ");
			sb.append("'1' ");
			sb.append("END AS INPUT_FLG ");
			sb.append("FROM ");
			sb.append("T_KOJIN AS TK LEFT OUTER JOIN T_KENSAKEKA_TOKUTEI AS TT ");
			sb.append("ON TK.UKETUKE_ID = TT.UKETUKE_ID ");
			sb.append(") ");
			if ((currentSortedColumns.size() == 0) && (currentSortedVersusColumns.size() == 0)) {
				sb.append("ORDER BY ");
				sb.append("NENDOS DESC, ");
				sb.append("KANANAME ASC, ");
				sb.append("BIRTHDAY ASC, ");
				sb.append("KENSA_NENGAPI DESC ");
			}
			// edit n.ohkubo 2015/08/01　追加　end　Firebird2.5でも正常に動くように修正（年度の表示対応）

			cnterrflg = false;
			
			//データの取得実行
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sb.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JKenshinKekkaSearchListFrameData.class,
											"Y",
											"N",
											null,
											gridParams,
											JApplication.gridViewSearchCount,
											true
			);
			
			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加しているので、付加した"%"を削除する
			filterSetting.setLikeColumns(filteredColumns, false);
			
			//検索画面へソート条件を反映する（「条件を保持しない」の場合、検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
			if (!grid.getSavedJCheckBox().isSelected()) {
				ColumnContainer columnContainer = grid.getColumnContainer();
				filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			}
			
			//0件の場合、メッセージの表示（画面オープン初回以外）
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
//						JErrorMessage.show("M3550", getGridControl());	// edit n.ohkubo 2015/03/01　削除
						JErrorMessage.show("M3553", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
					}
				}
			}

			firstViewFlg = false;
			
			return result;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}
	// edit n.ohkubo 2014/10/01　end　検索条件の保持等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）

//	/**
//	* SQL文を組み立てる
//	*
//	* @return SQL文
//	*/
//	private String buildSQL() {
//		StringBuilder query = new StringBuilder("select ");
//		for (String element : SQL_SELECT_PART) {
//			query.append(element);
//		}
//		query.append(" from T_KOJIN AS kojin ");
//		query.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI AS tokutei ");
//		query.append(" ON kojin.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		query.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when tokutei.NENDO is not null then tokutei.NENDO ");
//		query.append(" when substring(KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//		query.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//		query.append(" substring(KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//		query.append(" then CAST(substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
//		query.append(" else substring(tokutei.KENSA_NENGAPI FROM 1 FOR 4) end as NENDO ");
//		query.append(" from T_KENSAKEKA_TOKUTEI tokutei) as GET_NENDO ");
//		query.append(" ON GET_NENDO.UKETUKE_ID = tokutei.UKETUKE_ID ");
//		query.append(" AND GET_NENDO.KENSA_NENGAPI = tokutei.KENSA_NENGAPI ");
//		query.append(" ORDER BY NENDO DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//		return query.toString();
//	}
	// add s.inoue 2013/02/27
    @Override
	public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }
	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	@Override
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from T_SHIHARAI where SHIHARAI_DAIKO_NO=?");
			for (int i = 0; i < persistentObjects.size(); i++) {
				JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)persistentObjects.get(i);
				stmt.setString(1, vo.getUKETUKE_ID());
				stmt.execute();
			}
			return new VOResponse(new Boolean(true));
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		} finally {
			try {
				stmt.close();
				conn.commit();
			} catch (SQLException ex1) {
			}
		}
	}

	 /**
	   * Callback method invoked each time a cell is edited: this method define if the new value if valid.
	   * This method is invoked ONLY if:
	   * - the edited value is not equals to the old value OR it has exmplicitely called setCellAt or setValueAt
	   * - the cell is editable
	   * Default behaviour: cell value is valid.
	   * @param rowNumber selected row index
	   * @param attributeName attribute name related to the column currently selected
	   * @param oldValue old cell value (before cell editing)
	   * @param newValue new cell value (just edited)
	   * @return <code>true</code> if cell value is valid, <code>false</code> otherwise
	   */
	  @Override
	public boolean validateCell(int rowNumber,String attributeName,Object oldValue,Object newValue) {
	    if (attributeName.equals("currencyValue") || attributeName.equals("numericValue")) {
	      grid.getGrid().getBottomTable().reload();
	    }
	    if ((attributeName.equals("intValue") && new Integer(0).equals(newValue))) {
	      // zero value not allowed...
	      return false;
	    }

	    return true;
	  }

	  // eidt s.inoue 2011/04/28
	  /**
	   * Callback method invoked when the user has selected another row.
	   * @param rowNumber selected row index
	   */
	  @Override
	public void rowChanged(int rowNumber) {
		   // int curpg = grid.currentPage*(grid.currentSepalatePage-1);
		   // eidt s.inoue 2011/05/10
		   // ViewSettings.getGridPageSize()
		  // add s.inoue 2012/12/05
		  // int rowCount = ((grid.currentPage -1)*JApplication.gridViewSearchCount) + rowNumber + 1;
		  int rowCount = rowNumber + 1;

		  if (grid.currentPage -1 > 0){
			  // rowCount = ((grid.currentPage -1)*JApplication.gridViewSearchCount) + rowNumber + 1;
			  rowCount = grid.getGrid().getTable().getstartIndex() + rowNumber + 1;
		  }

		   grid.currentRow = rowNumber;
		   grid.currentTotalRows = rowCount;
		   grid.countText.setText(rowCount + "件目");
		   // add s.inoue 2012/11/21
//		   for (int i=0;i < JApplication.selectedHistoryRows.size(); i++) {
//			   JApplication.selectedHistoryRows.remove(i);
//		   }
	  }

	  private boolean cnterrflg = false;
	  /**
	   * @param grid grid
	   * @param row selected row index
	   * @param attributeName attribute name that identifies the selected grid column
	   * @return <code>true</code> if the selected cell is editable, <code>false</code> otherwise
	   */
	  @Override
	public boolean isCellEditable(GridControl grid,int row,String attributeName) {

		// edit n.ohkubo 2015/03/01　追加　start　「Alt+E」等が正常に動作しない対応（キー押下でチェックボックスの値が反転する）
		if (this.grid.isKeyPressed()) {
			return false;
		}
		// edit n.ohkubo 2015/03/01　追加　end　「Alt+E」等が正常に動作しない対応（キー押下でチェックボックスの値が反転する）
		
		  int jcnt = 0;
		  JApplication.selectedPreservRows = new ArrayList<Integer>();

		  JKenshinKekkaSearchListFrameData vo = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(row);
		    if ("CHECKBOX_COLUMN".equals(attributeName)){

		    	if(cnterrflg){
		    		cnterrflg = false;return false;
		    	}

		    	// add s.inoue 2013/04/05
		    	if (vo == null) return false;

		    	if (vo.getCHECKBOX_COLUMN() == null)
		    		return false;
		    	if (vo.getCHECKBOX_COLUMN().equals("N")){
		    	  vo.setCHECKBOX_COLUMN("Y");
		    	  /* 1件の為の処理 */
				  for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
					  JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
				  }

		    	  if (jcnt==1)
		    			JApplication.firstCheckedFlg=true;
		    		return false;
		    	}else if(vo.getCHECKBOX_COLUMN().equals("Y")){
		    		/* 1件の為の処理 */
					for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
						JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
					}

					int kcnt=0;
					for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
						JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y"))
							kcnt++;
					}
					if(kcnt == 2){
						cnterrflg = true;
					}

					vo.setCHECKBOX_COLUMN("N");
		    		return false;
		    	}else{
		    	  vo.setCHECKBOX_COLUMN("Y");
				  for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
					  JKenshinKekkaSearchListFrameData chk = (JKenshinKekkaSearchListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
				  }
			      if (jcnt==1)
			    		JApplication.firstCheckedFlg=true;
		    		return false;
		    	}
		    }
		    return grid.isFieldEditable(row,attributeName);
	  }

	  /**
	   * Callback method invoked each time an input control is edited: this method define if the new value is valid.
	   * Default behaviour: input control value is valid.
	   * @param attributeName attribute name related to the input control currently edited
	   * @param oldValue old input control value (before editing)
	   * @param newValue new input control value (just edited)
	   * @return <code>true</code> if input control value is valid, <code>false</code> otherwise
	   */
	  public boolean validateControl(String attributeName,Object oldValue,Object newValue) {
	    if (attributeName.equals("numericValue") &&
	        newValue!=null &&
	        ((BigDecimal)newValue).doubleValue()==0) {
	      // zero value not allowed...
	      return false;
	    }
	    return true;
	  }

	  @Override
	public void selectedCell(int i, int j, String s, ValueObject valueobject)
	  {
	  }

	@Override
	public FormController getGrid() {
		return null;
	}

	@Override
	public void jbInit() {
	}

	@Override
	public void reloadData() {
	}

	@Override
	public void setControl(Connection conn,
			JKenshinKekkaSearchListFrameCtl controller) {
	}

	@Override
	public void setJFrameeDitable() {
	}
	
	// edit n.ohkubo 2014/10/01　追加 start　「戻る」ボタンの処理（テーブルの表示項目の「表示 or 非表示」を登録）
	/**
	 * 項目の表示 or 非表示の状態を、DBへ登録する
	 */
	protected void preserveColumnStatus() {
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

			StringBuilder sbwhere = new StringBuilder();
			
			if (JApplication.flag.contains(FlagEnum_Serche.JYUSHIN_SEIRI_NO)){
				// 非表示
				sbwhere.append(" JYUSHIN_SEIRI_NO = '0'");
			}else{
				// 表示
				sbwhere.append(" JYUSHIN_SEIRI_NO = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.NENDO)){
				// 非表示
				sbwhere.append(" ,NENDO = '0'");
			}else{
				// 表示
				sbwhere.append(" ,NENDO = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_KIGOU)){
				// 非表示
				sbwhere.append(" ,HIHOKENJYASYO_KIGOU = '0'");
			}else{
				// 表示
				sbwhere.append(" ,HIHOKENJYASYO_KIGOU = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.HIHOKENJYASYO_NO)){
				// 非表示
				sbwhere.append(" ,HIHOKENJYASYO_NO = '0'");
			}else{
				// 表示
				sbwhere.append(" ,HIHOKENJYASYO_NO = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.KENSA_NENGAPI)){
				// 非表示
				sbwhere.append(" ,KENSA_NENGAPI = '0'");
			}else{
				// 表示
				sbwhere.append(" ,KENSA_NENGAPI = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.SEX)){
				// 非表示
				sbwhere.append(" ,SEX = '0'");
			}else{
				// 表示
				sbwhere.append(" ,SEX = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.BIRTHDAY)){
				// 非表示
				sbwhere.append(" ,BIRTHDAY = '0'");
			}else{
				// 表示
				sbwhere.append(" ,BIRTHDAY = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.KEKA_INPUT_FLG)){
				// 非表示
				sbwhere.append(" ,KEKA_INPUT_FLG = '0'");
			}else{
				// 表示
				sbwhere.append(" ,KEKA_INPUT_FLG = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.HKNJANUM)){
				// 非表示
				sbwhere.append(" ,HKNJANUM = '0'");
			}else{
				// 表示
				sbwhere.append(" ,HKNJANUM = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.SIHARAI_DAIKOU_BANGO)){
				// 非表示
				sbwhere.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
			}else{
				// 表示
				sbwhere.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.KANANAME)){
				// 非表示
				sbwhere.append(" ,KANANAME = '0'");
			}else{
				// 表示
				sbwhere.append(" ,KANANAME = '1'");
			}
			if(JApplication.flag.contains(FlagEnum_Serche.HANTEI_NENGAPI)){
				sbwhere.append(" ,HANTEI_NENGAPI = '0'");
			}else{
				sbwhere.append(" ,HANTEI_NENGAPI = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.TUTI_NENGAPI)){
				// 非表示
				sbwhere.append(" ,TUTI_NENGAPI = '0'");
			}else{
				// 表示
				sbwhere.append(" ,TUTI_NENGAPI = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.CHECKBOX_COLUMN)){
				// 非表示
				sbwhere.append(" ,CHECKBOX_COLUMN = '0'");
			}else{
				// 表示
				sbwhere.append(" ,CHECKBOX_COLUMN = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.TANKA_GOUKEI)){
				// 非表示
				sbwhere.append(" ,TANKA_GOUKEI = '0'");
			}else{
				// 表示
				sbwhere.append(" ,TANKA_GOUKEI = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.MADO_FUTAN_GOUKEI)){
				// 非表示
				sbwhere.append(" ,MADO_FUTAN_GOUKEI = '0'");
			}else{
				// 表示
				sbwhere.append(" ,MADO_FUTAN_GOUKEI = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.SEIKYU_KINGAKU)){
				// 非表示
				sbwhere.append(" ,SEIKYU_KINGAKU = '0'");
			}else{
				// 表示
				sbwhere.append(" ,SEIKYU_KINGAKU = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.UPDATE_TIMESTAMP)){
				// 非表示
				sbwhere.append(" ,UPDATE_TIMESTAMP = '0'");
			}else{
				// 表示
				sbwhere.append(" ,UPDATE_TIMESTAMP = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.JISI_KBN)){
				// 非表示
				sbwhere.append(" ,JISI_KBN = '0'");
			}else{
				// 表示
				sbwhere.append(" ,JISI_KBN = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.HENKAN_NITIJI)){
				// 非表示
				sbwhere.append(" ,HENKAN_NITIJI = '0'");
			}else{
				// 表示
				sbwhere.append(" ,HENKAN_NITIJI = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.METABO)){
				// 非表示
				sbwhere.append(" ,METABO = '0'");
			}else{
				// 表示
				sbwhere.append(" ,METABO = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.HOKENSIDO_LEVEL)){
				// 非表示
				sbwhere.append(" ,HOKENSIDO_LEVEL = '0'");
			}else{
				// 表示
				sbwhere.append(" ,HOKENSIDO_LEVEL = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.KOMENTO)){
				// 非表示
				sbwhere.append(" ,KOMENTO = '0'");
			}else{
				// 表示
				sbwhere.append(" ,KOMENTO = '1'");
			}
			if (JApplication.flag.contains(FlagEnum_Serche.NAME)){
				// 非表示
				sbwhere.append(" ,NAME = '0'");
			}else{
				// 表示
				sbwhere.append(" ,NAME = '1'");
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE T_SCREENCOLUMNS SET ");
			sb.append(sbwhere.toString());
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_SHOSAI_CODE));
			
			//SQL実行
			JApplication.kikanDatabase.sendNoResultQuery(sb.toString());

			//コミット
			JApplication.kikanDatabase.getMConnection().commit();
			
		} catch (SQLException e) {
			try {
				JApplication.kikanDatabase.getMConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(e.getMessage());
		}
	}
	// edit n.ohkubo 2014/10/01　追加 end　「戻る」ボタンの処理（テーブルの表示項目の「表示 or 非表示」を登録）
}
