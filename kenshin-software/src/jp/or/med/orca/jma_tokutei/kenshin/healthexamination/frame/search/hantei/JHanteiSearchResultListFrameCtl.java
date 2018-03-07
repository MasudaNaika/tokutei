package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Hantei;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.export.java.ExportOptions;
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
public class JHanteiSearchResultListFrameCtl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = -7928199140509264701L;	// edit n.ohkubo 2014/10/01　追加
	
	private JHanteiSearchResultListFrame grid = null;
	private Connection conn = null;
//	private JHanteiSearchResultListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除

//	private static final String CODE_HOKENSHIDOU_LEVEL = "9N506000000000011";	// edit n.ohkubo 2014/10/01　未使用なので削除

	private static Logger logger = Logger.getLogger(JHanteiSearchResultListFrameCtl.class);
	// add s.inoue 2012/11/16
	private boolean firstViewFlg =true;
	
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


	public JHanteiSearchResultListFrameCtl(Connection conn) {
		this.conn = conn;
		grid = new JHanteiSearchResultListFrame(conn, this);
	}

	public JHanteiSearchResultListFrame getGridControl(){
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

		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
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
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
// del
//		JShiharaiMasterMaintenanceListData vo = (JShiharaiMasterMaintenanceListData)persistentObject;
//		new JShiharaiMasterMaintenanceDetailCtl(grid, vo.getUketuke_id(), conn);
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

	 // add s.inoue 2012/11/08
	 // チェックボックス設定
	 private void setSelectedRow(ArrayList<Integer> selectedRows){
		 if (selectedRows == null)return;
		 // add s.inoue 2013/04/05
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
//	@Override
//	public Response loadData(int action, int startIndex, Map filteredColumns,
//			ArrayList currentSortedColumns,
//			ArrayList currentSortedVersusColumns, Class valueObjectType,
//			Map otherGridParams) {
//		// PreparedStatement stmt = null;
//		try {
//			ArrayList vals = new ArrayList();
//			Map attribute2dbField = new HashMap();
//			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");
//
//			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
//			attribute2dbField.put("NAME","NAME");
//			attribute2dbField.put("HIHOKENJYASYO_KIGOU","TK.HIHOKENJYASYO_KIGOU");
//			attribute2dbField.put("HIHOKENJYASYO_NO","TK.HIHOKENJYASYO_NO");
//			attribute2dbField.put("KENSA_NENGAPI","TT.KENSA_NENGAPI");
//			attribute2dbField.put("SEX","SEX");
//			attribute2dbField.put("KEKA_INPUT_FLG","KEKA_INPUT_FLG");
//			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
//			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
//			attribute2dbField.put("HKNJANUM","HKNJANUM");
//			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","SIHARAI_DAIKOU_BANGO");
//			attribute2dbField.put("KANANAME","KANANAME");
//			attribute2dbField.put("HANTEI_NENGAPI","HANTEI_NENGAPI");
//			attribute2dbField.put("TUTI_NENGAPI","TUTI_NENGAPI");
//			attribute2dbField.put("NENDO","GET_NENDO.NENDOS");
//			attribute2dbField.put("KOMENTO","KOMENTO");
//
//			// eidt s.inoue 2011/04/13
//			attribute2dbField.put("METABO","MT.METABO");
//			attribute2dbField.put("HOKENSIDO","HS.HOKENSIDO");
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//			
//			// add s.inoue 2013/11/06
//						// 初期値 又は あいまい検索の設定
//					    if (currentSortedColumns.iterator().hasNext()){
//							Iterator it_dt = currentSortedColumns.iterator();
//							String sortClauses = null;
//
//						    // add s.inoue 2013/11/06
//						    JApplication.currentSortedColumns = new ArrayList();
//
//						    while(it_dt.hasNext()) {
//						      sortClauses = (String)it_dt.next();
//						      // add s.inoue 2013/11/06
//						      JApplication.currentSortedColumns.add(sortClauses);
//						    }
//					    }
//
//					    if (currentSortedVersusColumns.iterator().hasNext()){
//							Iterator it_dt = currentSortedVersusColumns.iterator();
//							String sortClauses = null;
//
//						    // add s.inoue 2013/11/06
//						    JApplication.currentSortedVersusColumns = new ArrayList();
//
//						    while(it_dt.hasNext()) {
//						      sortClauses = (String)it_dt.next();
//						      // add s.inoue 2013/11/06
//						      JApplication.currentSortedVersusColumns.add(sortClauses);
//						    }
//					    }
//
//						
//						// 初期値 又は あいまい検索の設定
//					    if (filteredColumns.values().iterator().hasNext()){
//							Iterator it_dt = filteredColumns.values().iterator();
//						    FilterWhereClause[] filterClauses = null;
//
//						    // add s.inoue 2013/11/06
//						    int ii = 0;
//						    JApplication.clauseDesign = new FilterWhereClause[filteredColumns.size()];
//						    
//						    // add s.inoue 2013/03/25
//						    while(it_dt.hasNext()) {
//						      filterClauses = (FilterWhereClause[])it_dt.next();
//
//						      if (filterClauses[0].getOperator().equals("like")){
//					 			  // add s.inoue 2014/03/18
//						    	  String filterval = filterClauses[0].getValue().toString();
//						    	  if(!filterval.startsWith("%"))
//						    		  filterval = "%"+filterval+"%";
//						    	  filterClauses[0].setValue(filterval);
//
//									gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//											new FilterWhereClause[] { filterClauses[0], null });
//			// eidt s.inoue 2013/03/25
////						      }else if (filterClauses[0].getOperator().equals("like")){
////						    	  System.out.println("");
//			// del s.inoue 2013/03/26
////						      }else{
////						    	  filterClauses[0].setValue(filterClauses[0].getValue());
////						    	  JApplication.gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
////											new FilterWhereClause[] { filterClauses[0], null });
//
////							      // add s.inoue 2013/11/05
////							      JApplication.filterClauses = (FilterWhereClause[])it_dt.next();
////							      JApplication.filterClauses[filter_i] = filterClauses[0];
////							      filter_i++;
//						      }
//
//						      // add s.inoue 2013/11/06
//						      JApplication.clauseDesign[ii] = new FilterWhereClause();
//							  JApplication.clauseDesign[ii].setAttributeName(filterClauses[0].getAttributeName());
//							  JApplication.clauseDesign[ii].setOperator(filterClauses[0].getOperator());
//							  
//							// edit n.ohkubo 2014/08/21
////							  // edit s.inoue 2014/03/18
////					    	  String filterval = filterClauses[0].getValue().toString();
//							  String filterval;
//							  Object obj = filterClauses[0].getValue();
//							  if (obj == null) {
//								  filterval = "";
//							  } else {
//								  filterval = obj.toString();
//							  }
//							  
//					    	  // JApplication.clauseDesign[ii].setValue(filterClauses[0].getValue());
//					    	  if(filterval.startsWith("%"))
//					    		  filterval = filterval.replaceAll("%", "");
//					    	  JApplication.clauseDesign[ii].setValue(filterval);
//							  ii++;
//						      
//						    }
//					    }else{
//					    	// add s.inoue 2014/06/23
//					    	if (firstViewFlg){
//					    		
//				    		// add s.inoue 2013/11/06
//				    		if(JApplication.clauseDesign != null){
//				    			for (int i = 0; i < JApplication.clauseDesign.length; i++) {
//					    			gridParams.getFilteredColumns().put(JApplication.clauseDesign[i].getAttributeName(),
//											new FilterWhereClause[] { JApplication.clauseDesign[i], null });
//								}
//				    		}else{
//								DateFormat format = new SimpleDateFormat("yyyy");
//								// eidt s.inoue 2013/01/21
//								// 今年→本年度
//								// String cYear = format.format(new Date());
//								String cYear = String.valueOf(FiscalYearUtil.getThisYear());
//
//						    	// 年度は健診実施日が空の場合があるので省略
//								FilterWhereClause clauseDesign = new FilterWhereClause();
//								clauseDesign.setAttributeName("NENDO");
//								clauseDesign.setOperator("=");
//								clauseDesign.setValue(cYear);
//
//								gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//										new FilterWhereClause[] { clauseDesign, null });
//				    		}
//				    		
//// del s.inoue 2014/06/23					    	
////				    		// add s.inoue 2013/11/06
////				    		if(JApplication.currentSortedColumns != null){
////				    			for (int i = 0; i < JApplication.currentSortedColumns.size(); i++) {
////					    			gridParams.getCurrentSortedColumns().add(JApplication.currentSortedColumns.get(i));
////								}
////				    		}
////
////				    		if(JApplication.currentSortedVersusColumns != null){
////				    			for (int i = 0; i < JApplication.currentSortedVersusColumns.size(); i++) {
////					    			gridParams.getCurrentSortedVersusColumns().add(JApplication.currentSortedVersusColumns.get(i));
////								}
////				    		}
//						    }
//						}    
//// del s.inoue 2014/06/23
////			// 初期値 又は あいまい検索の設定
////		    if (filteredColumns.values().iterator().hasNext()){
////				Iterator it_dt = filteredColumns.values().iterator();
////			    FilterWhereClause[] filterClauses = null;
////
////			    while(it_dt.hasNext()) {
////			      filterClauses = (FilterWhereClause[])it_dt.next();
////			      // comment
//////			      System.out.println(filterClauses[0].getAttributeName());
//////			      System.out.println(filterClauses[0].getValue());
//////			      System.out.println(filterClauses[0].getOperator());
////
////			      if (filterClauses[0].getOperator().equals("like")){
////			    	// add s.inoue 2014/03/18
////			    	  String filterval = filterClauses[0].getValue().toString();
////			    	  if(!filterval.startsWith("%"))
////			    		  filterval = "%"+filterval+"%";
////			    	  filterClauses[0].setValue(filterval);
////
////						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
////								new FilterWhereClause[] { filterClauses[0], null });
////			      }
////			    }
////		    }else{
////
////		    	DateFormat format = new SimpleDateFormat("yyyy");
////		    	// eidt s.inoue 2013/01/21
////		    	// String cYear = format.format(new Date());
////		    	String cYear = String.valueOf(FiscalYearUtil.getThisYear());
////
////		    	// eidt s.inoue 2012/11/16
////		    	if (firstViewFlg){
////					FilterWhereClause clauseDesign = new FilterWhereClause();
////					clauseDesign.setAttributeName("NENDO");
////					clauseDesign.setOperator("=");
////					clauseDesign.setValue(cYear);
////
////					gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
////							new FilterWhereClause[] { clauseDesign, null });
////		    	}
////		    }
//
//			// eidt s.inoue 2011/05/10
//			// ViewSettings.getGridPageSize()
//			int curpage = startIndex/JApplication.gridViewSearchCount + action;
//			grid.currentPage = curpage;
//			System.out.println("現在ページ:" + grid.currentPage);
//
//			// eidt s.inoue 2011/04/08
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}
//
//			// openswing s.inoue 2011/01/14
//			// String sql = buildSQL();
//			StringBuilder sb = new StringBuilder();
//
//			sb.append("SELECT MT.METABO,HS.HOKENSIDO,GET_NENDO.NENDOS,");
//			// sb.append(" SELECT case when TT.NENDO is not null then TT.NENDO else '2011' end as NENDOS,");
//
//			sb.append(" '' as CHECKBOX_COLUMN,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO,");
//			sb.append(" TK.BIRTHDAY BIRTHDAY,TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
//			sb.append(" TK.KANANAME KANANAME,TT.HANTEI_NENGAPI HANTEI_NENGAPI,TT.TUTI_NENGAPI TUTI_NENGAPI,TT.KENSA_NENGAPI,");
//			sb.append(" TT.KOMENTO KOMENTO,");
//			// del 20121108
//			// ,METABO,HOKENSIDO
//			sb.append(" case TK.SEX when 1 then '男' when 2 then '女' end as SEX,case TT.KEKA_INPUT_FLG when 1 then '未' when 2 then '済' else '未' end as KEKA_INPUT_FLG ");
//			sb.append(" FROM T_KOJIN TK");
//			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
//			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");
//
//			/* 判定年月日,通知年月日 */
//			// eidt s.inoue 2011/04/13
//			// sb.append(" LEFT JOIN T_KENSAKEKA_SONOTA ST ");
//			// sb.append(" ON ST.UKETUKE_ID = TK.UKETUKE_ID ");
//			// sb.append(" AND ST.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//			// sb.append(" AND ST.KOUMOKU_CD = ");
//			/* 保健指導レベルの検索結果 */
//			// sb.append(JQueryConvert.queryConvert(CODE_HOKENSHIDOU_LEVEL));
//			sb.append(" LEFT JOIN (SELECT UKETUKE_ID,KENSA_NENGAPI, KEKA_TI, ");
//			// eidt s.inoue 2012/11/08
//			// sb.append(" case when KEKA_TI ='1' then '基準該当' when KEKA_TI ='2' then '予備群該当' when KEKA_TI ='3' then '非該当' when KEKA_TI ='4' then '判定不能' else '未判定' end METABO ");
//			// eidt s.inoue 2013/05/23
//			// sb.append(" case when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '0' end METABO ");
//
//			// eidt s.inoue 2013/05/23
//			// sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '0' end METABO ");
//			sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '1' end METABO ");
//
//			sb.append(" FROM T_KENSAKEKA_SONOTA where KOUMOKU_CD = '9N501000000000011') as MT ");
//			sb.append(" ON MT.UKETUKE_ID = TK.UKETUKE_ID AND MT.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//
//			sb.append(" LEFT JOIN (SELECT UKETUKE_ID,KENSA_NENGAPI, KEKA_TI, ");
//			// eidt s.inoue 2012/11/08
//			// sb.append(" case when KEKA_TI ='0' then '指定無し' when KEKA_TI ='1' then '積極的支援' when KEKA_TI ='2' then '動機づけ支援' when KEKA_TI ='3' then 'なし（情報提供）' when KEKA_TI ='4' then '判定不能' else '未判定' end HOKENSIDO ");
//
//			// eidt s.inoue 2013/05/23
//			// sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '0' end HOKENSIDO ");
//			sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '1' end HOKENSIDO ");
//
//			sb.append(" FROM T_KENSAKEKA_SONOTA where KOUMOKU_CD = '9N506000000000011') as HS ");
//			sb.append(" ON HS.UKETUKE_ID = TK.UKETUKE_ID AND HS.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//
//			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when TT.NENDO is not null then TT.NENDO ");
//			sb.append(" when substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
//			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
//			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
//			sb.append(" then CAST(substring(TT.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
//			sb.append(" else substring(TT.KENSA_NENGAPI FROM 1 FOR 4) end as NENDOS ");
//			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_NENDO ");
//			sb.append(" ON GET_NENDO.UKETUKE_ID = TT.UKETUKE_ID ");
//			sb.append(" AND GET_NENDO.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
//
//			// eidt s.inoue 2011/04/25
//			// sb.append(" AND substring(TT.KENSA_NENGAPI FROM 1 FOR 4) = GET_NENDO.NENDOS ");
//			// edit s.inoue 2013/01/24
//			// sb.append(" Where substring(TT.KENSA_NENGAPI FROM 1 FOR 4) = GET_NENDO.NENDOS ");
//			sb.append(" Where GET_NENDO.NENDOS is not null");
//
//			// add s.inoue 2012/11/21
//			if (currentSortedColumns.size()==0  &&
//					currentSortedVersusColumns.size()==0){
//				sb.append(" ORDER BY GET_NENDO.NENDOS DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
//			}
//
//			// add s.inoue 2012/11/16
//			firstViewFlg = false;
//
//			// eidt s.inoue 2011/05/10
//			// ViewSettings.getGridPageSize()
//			return QueryUtil
//					.getQuery(
//							conn,
//							new UserSessionParameters(),
//							sb.toString(),
//							vals,
//							attribute2dbField,
//							JHanteiSearchResultListFrameData.class,
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
	 * @param filteredColumns filtered columns
	 * @param currentSortedColumns sorted columns
	 * @param currentSortedVersusColumns ordering versus of sorted columns
	 * @param valueObjectType v.o. type
	 * @param otherGridParams other grid parameters
	 * @return response from the server: an object of type VOListResponse
	 *  if data loading was successfully completed, or an ErrorResponse onject if some error occours
	 */
	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns, ArrayList currentSortedColumns, ArrayList currentSortedVersusColumns, Class valueObjectType, Map otherGridParams) {
		
		try {
			Map<String, String> attribute2dbField = new HashMap<String, String>();
			attribute2dbField.put("CHECKBOX_COLUMN", "CHECKBOX_COLUMN");
			attribute2dbField.put("UKETUKE_ID", "UKETUKE_ID");
			attribute2dbField.put("NAME", "NAME");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU", "TK.HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO", "TK.HIHOKENJYASYO_NO");
			attribute2dbField.put("KENSA_NENGAPI", "TT.KENSA_NENGAPI");
			attribute2dbField.put("KENSA_NENGAPI2", "TT.KENSA_NENGAPI");
			attribute2dbField.put("SEX", "SEX");
			attribute2dbField.put("KEKA_INPUT_FLG", "KEKA_INPUT_FLG");
			attribute2dbField.put("BIRTHDAY", "BIRTHDAY");
			attribute2dbField.put("JYUSHIN_SEIRI_NO", "JYUSHIN_SEIRI_NO");
			attribute2dbField.put("HKNJANUM", "HKNJANUM");
			attribute2dbField.put("SIHARAI_DAIKOU_BANGO", "SIHARAI_DAIKOU_BANGO");
			attribute2dbField.put("KANANAME", "KANANAME");
			attribute2dbField.put("HANTEI_NENGAPI", "HANTEI_NENGAPI");
			attribute2dbField.put("TUTI_NENGAPI", "TUTI_NENGAPI");
			attribute2dbField.put("NENDO", "GET_NENDO.NENDOS");
			attribute2dbField.put("KOMENTO", "KOMENTO");
			attribute2dbField.put("METABO", "MT.METABO");
			attribute2dbField.put("HOKENSIDO", "HS.HOKENSIDO");

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);
			
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//検索・ソート条件の設定
			FilterSetting filterSetting = new FilterSetting(JApplication.SCREEN_HANTEI_CODE, grid.getColumnContainer());
			filterSetting.setFilterSort(firstViewFlg, grid.getSavedJCheckBox(), gridParams, filteredColumns, currentSortedColumns, currentSortedVersusColumns, new String[]{"NENDO", "="});
			
			//ページの設定
			int curpage = startIndex/JApplication.gridViewSearchCount + action;
			grid.currentPage = curpage;
//			System.out.println("現在ページ:" + grid.currentPage);

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT MT.METABO,HS.HOKENSIDO,GET_NENDO.NENDOS,");
			sb.append(" '' as CHECKBOX_COLUMN,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO,");
			sb.append(" TK.BIRTHDAY BIRTHDAY,TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
			sb.append(" TK.KANANAME KANANAME,TT.HANTEI_NENGAPI HANTEI_NENGAPI,TT.TUTI_NENGAPI TUTI_NENGAPI,TT.KENSA_NENGAPI,");
			sb.append(" TT.KOMENTO KOMENTO,");
			sb.append(" case TK.SEX when 1 then '男' when 2 then '女' end as SEX,case TT.KEKA_INPUT_FLG when 1 then '未' when 2 then '済' else '未' end as KEKA_INPUT_FLG ");
			sb.append(" FROM T_KOJIN TK");
			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");
			sb.append(" LEFT JOIN (SELECT UKETUKE_ID,KENSA_NENGAPI, KEKA_TI, ");
			sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '1' end METABO ");
			sb.append(" FROM T_KENSAKEKA_SONOTA where KOUMOKU_CD = '9N501000000000011') as MT ");
			sb.append(" ON MT.UKETUKE_ID = TK.UKETUKE_ID AND MT.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
			sb.append(" LEFT JOIN (SELECT UKETUKE_ID,KENSA_NENGAPI, KEKA_TI, ");
			sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '1' end HOKENSIDO ");
			sb.append(" FROM T_KENSAKEKA_SONOTA where KOUMOKU_CD = '9N506000000000011') as HS ");
			sb.append(" ON HS.UKETUKE_ID = TK.UKETUKE_ID AND HS.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
			sb.append(" LEFT JOIN (select UKETUKE_ID,KENSA_NENGAPI,case when TT.NENDO is not null then TT.NENDO ");
			sb.append(" when substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='01' OR ");
			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='02' OR ");
			sb.append(" substring(TT.KENSA_NENGAPI FROM 5 FOR 2) ='03' ");
			sb.append(" then CAST(substring(TT.KENSA_NENGAPI FROM 1 FOR 4) AS INTEGER) - 1 ");
			sb.append(" else substring(TT.KENSA_NENGAPI FROM 1 FOR 4) end as NENDOS ");
			sb.append(" from T_KENSAKEKA_TOKUTEI TT) as GET_NENDO ");
			sb.append(" ON GET_NENDO.UKETUKE_ID = TT.UKETUKE_ID ");
			sb.append(" AND GET_NENDO.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
			sb.append(" Where GET_NENDO.NENDOS is not null");
			
			if (currentSortedColumns.size()==0  &&
					currentSortedVersusColumns.size()==0){
				sb.append(" ORDER BY GET_NENDO.NENDOS DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
			}

			//データの取得実行
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sb.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JHanteiSearchResultListFrameData.class,
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
						JErrorMessage.show("M4957", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
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
				JHanteiSearchResultListFrameData vo = (JHanteiSearchResultListFrameData)persistentObjects.get(i);
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
	    // this method has been overrrided to listen for numeric/currency cell changes:
	    // in this case it will be invoked getTotals method to refresh bottom grid content...
	    if (attributeName.equals("currencyValue") || attributeName.equals("numericValue")) {
	      grid.getGrid().getBottomTable().reload();
	    }
	    if (attributeName.equals("intValue") && new Integer(0).equals(newValue)) {
	      // zero value not allowed...
	      return false;
	    }
	    return true;
	  }

	  /**
	   * Callback method invoked when the user has selected another row.
	   * @param rowNumber selected row index
	   */
	  @Override
	public void rowChanged(int rowNumber) {

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
		  // System.out.println("現在行" + rowCount);
	  }

// eidt s.inoue 2012/11/30
//	  /**
//	   * @param grid grid
//	   * @param row selected row index
//	   * @param attributeName attribute name that identifies the selected grid column
//	   * @return <code>true</code> if the selected cell is editable, <code>false</code> otherwise
//	   */
//	  public boolean isCellEditable(GridControl grid,int row,String attributeName) {
//		  JHanteiSearchResultListFrameData vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(row);
//	    if ("CHECKBOX_COLUMN".equals(attributeName)){
//	    	// eidt s.inoue 2011/04/28
//	    	if (vo.getCHECKBOX_COLUMN() == null)
//	    		return false;
//
//	    	if (vo.getCHECKBOX_COLUMN().equals("N")){
//	    		vo.setCHECKBOX_COLUMN("Y");
//	    		return false;
//	    	}else if(vo.getCHECKBOX_COLUMN().equals("Y")){
//	    		vo.setCHECKBOX_COLUMN("N");
//	    		return false;
//	    	}else{
//	    		vo.setCHECKBOX_COLUMN("Y");
//	    		// eidt s.inoue 2012/11/22
//	    		// return true;
//	    		return false;
//	    	}
////	      return vo.getCheckValue()!=null && vo.getCheckValue().booleanValue();
//	    }
//	    return grid.isFieldEditable(row,attributeName);
//	  }
	  private boolean cntErrFlg = false;
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

		  JHanteiSearchResultListFrameData vo = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(row);
		    if ("CHECKBOX_COLUMN".equals(attributeName)){

		    	if(cntErrFlg){
		    		cntErrFlg = false;return false;
		    	}
		    	// add s.inoue 2013/04/05
		    	if (vo == null) return false;

		    	if (vo.getCHECKBOX_COLUMN() == null)
		    		return false;
		    	if (vo.getCHECKBOX_COLUMN().equals("N")){
		    	  vo.setCHECKBOX_COLUMN("Y");
		    	  /* 1件の為の処理 */
				  for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
					  JHanteiSearchResultListFrameData chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
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
						JHanteiSearchResultListFrameData chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y")){
							jcnt++;
							if (!JApplication.selectedPreservRows.contains(i))
							JApplication.selectedPreservRows.add(i);
						}
					}

					int kcnt=0;
					for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
						JHanteiSearchResultListFrameData chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
						if (chk.getCHECKBOX_COLUMN().equals("Y"))
							kcnt++;
					}
					if(kcnt == 2){
						cntErrFlg = true;
					}

					vo.setCHECKBOX_COLUMN("N");
		    		return false;
		    	}else{
		    	  vo.setCHECKBOX_COLUMN("Y");
				  for (int i = 0; i < grid.getVOListTableModel().getRowCount(); i++) {
					  JHanteiSearchResultListFrameData chk = (JHanteiSearchResultListFrameData)grid.getVOListTableModel().getObjectForRow(i);
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
  
	// edit n.ohkubo 2014/10/01　追加 start　「戻る」ボタンの処理（テーブルの表示項目の「表示 or 非表示」を登録）
	/**
	 * 項目の表示 or 非表示の状態を、DBへ登録する
	 */
	protected void preserveColumnStatus() {
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

			StringBuilder sbwhere_hantei = new StringBuilder();
			
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.JYUSHIN_SEIRI_NO)){
				// 非表示
				sbwhere_hantei.append(" JYUSHIN_SEIRI_NO = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" JYUSHIN_SEIRI_NO = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.NENDO)){
				// 非表示
				sbwhere_hantei.append(" ,NENDO = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,NENDO = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HIHOKENJYASYO_KIGOU)){
				// 非表示
				sbwhere_hantei.append(" ,HIHOKENJYASYO_KIGOU = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,HIHOKENJYASYO_KIGOU = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HIHOKENJYASYO_NO)){
				// 非表示
				sbwhere_hantei.append(" ,HIHOKENJYASYO_NO = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,HIHOKENJYASYO_NO = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KENSA_NENGAPI)){
				// 非表示
				sbwhere_hantei.append(" ,KENSA_NENGAPI = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,KENSA_NENGAPI = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.SEX)){
				// 非表示
				sbwhere_hantei.append(" ,SEX = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,SEX = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.BIRTHDAY)){
				// 非表示
				sbwhere_hantei.append(" ,BIRTHDAY = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,BIRTHDAY = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KEKA_INPUT_FLG)){
				// 非表示
				sbwhere_hantei.append(" ,KEKA_INPUT_FLG = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,KEKA_INPUT_FLG = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HKNJANUM)){
				// 非表示
				sbwhere_hantei.append(" ,HKNJANUM = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,HKNJANUM = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.SIHARAI_DAIKOU_BANGO)){
				// 非表示
				sbwhere_hantei.append(" ,SIHARAI_DAIKOU_BANGO = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,SIHARAI_DAIKOU_BANGO = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KANANAME)){
				// 非表示
				sbwhere_hantei.append(" ,KANANAME = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,KANANAME = '1'");
			}
			if(JApplication.flag_Hantei.contains(FlagEnum_Hantei.HANTEI_NENGAPI)){
				sbwhere_hantei.append(" ,HANTEI_NENGAPI = '0'");
			}else{
				sbwhere_hantei.append(" ,HANTEI_NENGAPI = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.TUTI_NENGAPI)){
				// 非表示
				sbwhere_hantei.append(" ,TUTI_NENGAPI = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,TUTI_NENGAPI = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.CHECKBOX_COLUMN)){
				// 非表示
				sbwhere_hantei.append(" ,CHECKBOX_COLUMN = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,CHECKBOX_COLUMN = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.TANKA_GOUKEI)){
				// 非表示
				sbwhere_hantei.append(" ,TANKA_GOUKEI = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,TANKA_GOUKEI = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.MADO_FUTAN_GOUKEI)){
				// 非表示
				sbwhere_hantei.append(" ,MADO_FUTAN_GOUKEI = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,MADO_FUTAN_GOUKEI = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.SEIKYU_KINGAKU)){
				// 非表示
				sbwhere_hantei.append(" ,SEIKYU_KINGAKU = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,SEIKYU_KINGAKU = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.UPDATE_TIMESTAMP)){
				// 非表示
				sbwhere_hantei.append(" ,UPDATE_TIMESTAMP = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,UPDATE_TIMESTAMP = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.JISI_KBN)){
				// 非表示
				sbwhere_hantei.append(" ,JISI_KBN = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,JISI_KBN = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HENKAN_NITIJI)){
				// 非表示
				sbwhere_hantei.append(" ,HENKAN_NITIJI = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,HENKAN_NITIJI = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.METABO)){
				// 非表示
				sbwhere_hantei.append(" ,METABO = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,METABO = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.HOKENSIDO_LEVEL)){
				// 非表示
				sbwhere_hantei.append(" ,HOKENSIDO_LEVEL = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,HOKENSIDO_LEVEL = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.KOMENTO)){
				// 非表示
				sbwhere_hantei.append(" ,KOMENTO = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,KOMENTO = '1'");
			}
			if (JApplication.flag_Hantei.contains(FlagEnum_Hantei.NAME)){
				// 非表示
				sbwhere_hantei.append(" ,NAME = '0'");
			}else{
				// 表示
				sbwhere_hantei.append(" ,NAME = '1'");
			}
			
			StringBuilder sb_hantei = new StringBuilder();
			sb_hantei.append("UPDATE T_SCREENCOLUMNS SET ");
			sb_hantei.append(sbwhere_hantei.toString());
			sb_hantei.append(" WHERE SCREEN_CD = ");
			sb_hantei.append(JQueryConvert.queryConvert(JApplication.SCREEN_HANTEI_CODE));
			
			//SQL実行
			JApplication.kikanDatabase.sendNoResultQuery(sb_hantei.toString());
			
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