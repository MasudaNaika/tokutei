package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.hantei;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.openswing.swing.message.receive.java.*;
import org.openswing.swing.table.client.GridController;
import java.util.*;
import java.util.Date;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.util.FiscalYearUtil;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.search.kenshin.JKenshinKekkaSearchListFrameCtl;

import org.openswing.swing.table.java.GridDataLocator;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.form.client.FormController;

import sun.java2d.Disposer;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JHanteiSearchResultListFrameCtl
		extends GridController
		implements GridDataLocator {

	private JHanteiSearchResultListFrame grid = null;
	private Connection conn = null;
	private JHanteiSearchResultListFrame frame;

	private static final String CODE_HOKENSHIDOU_LEVEL = "9N506000000000011";

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
	public String getHeaderTooltip(String attributeName) {
	    return attributeName;
	}

    /**
	* @param row row index in the grid
	* @param attributeName attribute name that identify a grid column
	* @return tooltip text to show in the cell identified by the specified row and attribute name; this text will be automatically translated according to internationalization settings
	*/
	public String getCellTooltip(int row,String attributeName) {

		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
//		return attributeName+" at row "+row;
	}
	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
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
	 public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

	 // add s.inoue 2011/04/26
	/**
	 * Callback method called when the data loading is completed.
	 * @param error <code>true</code> if an error occours during data loading, <code>false</code> if data loading is successfully completed
	 */
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
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {
		// PreparedStatement stmt = null;
		try {
			ArrayList vals = new ArrayList();
			Map attribute2dbField = new HashMap();
			attribute2dbField.put("CHECKBOX_COLUMN","CHECKBOX_COLUMN");

			attribute2dbField.put("UKETUKE_ID","UKETUKE_ID");
			attribute2dbField.put("NAME","NAME");
			attribute2dbField.put("HIHOKENJYASYO_KIGOU","TK.HIHOKENJYASYO_KIGOU");
			attribute2dbField.put("HIHOKENJYASYO_NO","TK.HIHOKENJYASYO_NO");
			attribute2dbField.put("KENSA_NENGAPI","TT.KENSA_NENGAPI");
			attribute2dbField.put("SEX","SEX");
			attribute2dbField.put("KEKA_INPUT_FLG","KEKA_INPUT_FLG");
			attribute2dbField.put("BIRTHDAY","BIRTHDAY");
			attribute2dbField.put("JYUSHIN_SEIRI_NO","JYUSHIN_SEIRI_NO");
			attribute2dbField.put("HKNJANUM","HKNJANUM");
			attribute2dbField.put("SIHARAI_DAIKOU_BANGO","SIHARAI_DAIKOU_BANGO");
			attribute2dbField.put("KANANAME","KANANAME");
			attribute2dbField.put("HANTEI_NENGAPI","HANTEI_NENGAPI");
			attribute2dbField.put("TUTI_NENGAPI","TUTI_NENGAPI");
			attribute2dbField.put("NENDO","GET_NENDO.NENDOS");
			attribute2dbField.put("KOMENTO","KOMENTO");

			// eidt s.inoue 2011/04/13
			attribute2dbField.put("METABO","MT.METABO");
			attribute2dbField.put("HOKENSIDO","HS.HOKENSIDO");

			GridParams gridParams = new GridParams(action, startIndex,
					filteredColumns, currentSortedColumns,
					currentSortedVersusColumns, otherGridParams);

			// 初期値 又は あいまい検索の設定
		    if (filteredColumns.values().iterator().hasNext()){
				Iterator it_dt = filteredColumns.values().iterator();
			    FilterWhereClause[] filterClauses = null;

			    while(it_dt.hasNext()) {
			      filterClauses = (FilterWhereClause[])it_dt.next();
			      // comment
//			      System.out.println(filterClauses[0].getAttributeName());
//			      System.out.println(filterClauses[0].getValue());
//			      System.out.println(filterClauses[0].getOperator());

			      if (filterClauses[0].getOperator().equals("like")){
			    	  filterClauses[0].setValue("%" + filterClauses[0].getValue() + "%");

						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
								new FilterWhereClause[] { filterClauses[0], null });
			      }
			    }
		    }else{

		    	DateFormat format = new SimpleDateFormat("yyyy");
		    	// eidt s.inoue 2013/01/21
		    	// String cYear = format.format(new Date());
		    	String cYear = String.valueOf(FiscalYearUtil.getThisYear());

		    	// eidt s.inoue 2012/11/16
		    	if (firstViewFlg){
					FilterWhereClause clauseDesign = new FilterWhereClause();
					clauseDesign.setAttributeName("NENDO");
					clauseDesign.setOperator("=");
					clauseDesign.setValue(cYear);

					gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
							new FilterWhereClause[] { clauseDesign, null });
		    	}
		    }

			// eidt s.inoue 2011/05/10
			// ViewSettings.getGridPageSize()
			int curpage = startIndex/JApplication.gridViewSearchCount + action;
			grid.currentPage = curpage;
			System.out.println("現在ページ:" + grid.currentPage);

			// eidt s.inoue 2011/04/08
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}

			// openswing s.inoue 2011/01/14
			// String sql = buildSQL();
			StringBuilder sb = new StringBuilder();

			sb.append("SELECT MT.METABO,HS.HOKENSIDO,GET_NENDO.NENDOS,");
			// sb.append(" SELECT case when TT.NENDO is not null then TT.NENDO else '2011' end as NENDOS,");

			sb.append(" '' as CHECKBOX_COLUMN,TK.UKETUKE_ID UKETUKE_ID,TK.NAME NAME,TK.HIHOKENJYASYO_KIGOU,TK.HIHOKENJYASYO_NO,");
			sb.append(" TK.BIRTHDAY BIRTHDAY,TK.JYUSHIN_SEIRI_NO JYUSHIN_SEIRI_NO,TK.HKNJANUM HKNJANUM,TK.SIHARAI_DAIKOU_BANGO SIHARAI_DAIKOU_BANGO,");
			sb.append(" TK.KANANAME KANANAME,TT.HANTEI_NENGAPI HANTEI_NENGAPI,TT.TUTI_NENGAPI TUTI_NENGAPI,TT.KENSA_NENGAPI,");
			sb.append(" TT.KOMENTO KOMENTO,");
			// del 20121108
			// ,METABO,HOKENSIDO
			sb.append(" case TK.SEX when 1 then '男' when 2 then '女' end as SEX,case TT.KEKA_INPUT_FLG when 1 then '未' when 2 then '済' else '未' end as KEKA_INPUT_FLG ");
			sb.append(" FROM T_KOJIN TK");
			sb.append(" LEFT JOIN T_KENSAKEKA_TOKUTEI TT");
			sb.append(" ON TK.UKETUKE_ID = TT.UKETUKE_ID");

			/* 判定年月日,通知年月日 */
			// eidt s.inoue 2011/04/13
			// sb.append(" LEFT JOIN T_KENSAKEKA_SONOTA ST ");
			// sb.append(" ON ST.UKETUKE_ID = TK.UKETUKE_ID ");
			// sb.append(" AND ST.KENSA_NENGAPI = TT.KENSA_NENGAPI ");
			// sb.append(" AND ST.KOUMOKU_CD = ");
			/* 保健指導レベルの検索結果 */
			// sb.append(JQueryConvert.queryConvert(CODE_HOKENSHIDOU_LEVEL));
			sb.append(" LEFT JOIN (SELECT UKETUKE_ID,KENSA_NENGAPI, KEKA_TI, ");
			// eidt s.inoue 2012/11/08
			// sb.append(" case when KEKA_TI ='1' then '基準該当' when KEKA_TI ='2' then '予備群該当' when KEKA_TI ='3' then '非該当' when KEKA_TI ='4' then '判定不能' else '未判定' end METABO ");
			sb.append(" case when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '0' end METABO ");

			sb.append(" FROM T_KENSAKEKA_SONOTA where KOUMOKU_CD = '9N501000000000011') as MT ");
			sb.append(" ON MT.UKETUKE_ID = TK.UKETUKE_ID AND MT.KENSA_NENGAPI = TT.KENSA_NENGAPI ");

			sb.append(" LEFT JOIN (SELECT UKETUKE_ID,KENSA_NENGAPI, KEKA_TI, ");
			// eidt s.inoue 2012/11/08
			// sb.append(" case when KEKA_TI ='0' then '指定無し' when KEKA_TI ='1' then '積極的支援' when KEKA_TI ='2' then '動機づけ支援' when KEKA_TI ='3' then 'なし（情報提供）' when KEKA_TI ='4' then '判定不能' else '未判定' end HOKENSIDO ");
			sb.append(" case when KEKA_TI ='0' then '1' when KEKA_TI ='1' then '2' when KEKA_TI ='2' then '3' when KEKA_TI ='3' then '4' when KEKA_TI ='4' then '5' else '0' end HOKENSIDO ");

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

			// eidt s.inoue 2011/04/25
			// sb.append(" AND substring(TT.KENSA_NENGAPI FROM 1 FOR 4) = GET_NENDO.NENDOS ");
			// edit s.inoue 2013/01/24
			// sb.append(" Where substring(TT.KENSA_NENGAPI FROM 1 FOR 4) = GET_NENDO.NENDOS ");
			sb.append(" Where GET_NENDO.NENDOS is not null");

			// add s.inoue 2012/11/21
			if (currentSortedColumns.size()==0  &&
					currentSortedVersusColumns.size()==0){
				sb.append(" ORDER BY GET_NENDO.NENDOS DESC,KANANAME,BIRTHDAY,KENSA_NENGAPI DESC");
			}

			// add s.inoue 2012/11/16
			firstViewFlg = false;

			// eidt s.inoue 2011/05/10
			// ViewSettings.getGridPageSize()
			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sb.toString(),
							vals,
							attribute2dbField,
							JHanteiSearchResultListFrameData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewSearchCount, true);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}

	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
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
	  public boolean isCellEditable(GridControl grid,int row,String attributeName) {

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
}