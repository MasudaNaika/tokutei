package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Master;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
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
import org.openswing.swing.util.client.ClientSettings;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator {

	private static final long serialVersionUID = 4608859469882524906L;	// edit n.ohkubo 2014/10/01　追加
	
	private JKenshinMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
//	private JKenshinMasterMaintenanceListFrame frame;	// edit n.ohkubo 2014/10/01　未使用なので削除

	private JKenshinMasterMaintenanceListData validatedData = new JKenshinMasterMaintenanceListData();
	private static Logger logger = Logger.getLogger(JKenshinMasterMaintenanceListFrameCtrl.class);
	
	/**
	 * 画面オープン直後かの判定用
	 * オープン直後：true、直後ではない（自画面遷移等）：false
	 */
	private boolean firstViewFlg = true;	// edit n.ohkubo 2014/10/01　追加

	public JKenshinMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JKenshinMasterMaintenanceListFrame(conn, this);
	}

	public JKenshinMasterMaintenanceListFrame getGridControl(){
		return grid;
	}
	/**
	 * INSERT modeの時、保存前に呼ばれる Callback関数(save buttonが押された時).
	 *
	 * @return <code>true</code> 保存許可, <code>false</code> 保存中断
	 */
	@Override
	public boolean beforeInsertGrid(GridControl grid) {
// 保留
//		new JKenshijnMasterMaintenanceDetailCtl(this.grid, null, conn);
		return false;
	}

	/**
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	@Override
	public void doubleClick(int rowNumber, ValueObject persistentObject) {
// 保留
//		JKenshinMasterMaintenanceListData vo = (JKenshinMasterMaintenanceListData)persistentObject;
//		new JKenshinMasterMaintenanceDetailCtl(grid, vo.getSHIHARAI_DAIKO_NO(), conn);
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
		
		// edit n.ohkubo 2015/03/01　追加　start　初期値ボタン押下後のツールチップ対応
		if ("DS_KAGEN".equals(attributeName) || "DS_JYOUGEN".equals(attributeName) || "JS_KAGEN".equals(attributeName) || "JS_JYOUGEN".equals(attributeName)) {
//			System.out.println("row:[" + row + "] attributeName:[" + attributeName + "] afterDefaultToolTipText:[" + grid.afterDefaultToolTipText + "]");
			if (grid.afterDefaultToolTipText != null) {
				return grid.afterDefaultToolTipText;
			}
		}
		// edit n.ohkubo 2015/03/01　追加　end　初期値ボタン押下後のツールチップ対応
		
		return (String) grid.getGrid().getVOListTableModel().getField(row,attributeName);
//			return attributeName+" at row "+row;
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
//
//			attribute2dbField.put("HKNJANUM","HKNJANUM");
//			attribute2dbField.put("KOUMOKU_CD","KOUMOKU_CD");
//			// eidt s.inoue 2011/06/02
//			attribute2dbField.put("KOUMOKU_NAME","KOUMOKU_NAME");
//			attribute2dbField.put("KENSA_HOUHOU","KENSA_HOUHOU");
//			attribute2dbField.put("HISU_FLG","HISU_FLG");
//			attribute2dbField.put("DS_KAGEN","DS_KAGEN");
//			attribute2dbField.put("DS_JYOUGEN","DS_JYOUGEN");
//			attribute2dbField.put("JS_KAGEN","JS_KAGEN");
//			attribute2dbField.put("JS_JYOUGEN","JS_JYOUGEN");
//			attribute2dbField.put("TANI","TANI");
//			attribute2dbField.put("KAGEN","KAGEN");
//			attribute2dbField.put("JYOUGEN","JYOUGEN");
//			attribute2dbField.put("KIJYUNTI_HANI","KIJYUNTI_HANI");
//			attribute2dbField.put("TANKA_KENSIN","TANKA_KENSIN");
//			attribute2dbField.put("BIKOU","BIKOU");
//
//			GridParams gridParams = new GridParams(action, startIndex,
//					filteredColumns, currentSortedColumns,
//					currentSortedVersusColumns, otherGridParams);
//
//			// 初期値 又は あいまい検索の設定
//		    if (filteredColumns.values().iterator().hasNext()){
//				Iterator it_dt = filteredColumns.values().iterator();
//			    FilterWhereClause[] filterClauses = null;
//
//			    while(it_dt.hasNext()) {
//			      filterClauses = (FilterWhereClause[])it_dt.next();
//			      // comment
////			      System.out.println(filterClauses[0].getAttributeName());
////			      System.out.println(filterClauses[0].getValue());
////			      System.out.println(filterClauses[0].getOperator());
//
//			      if (filterClauses[0].getOperator().equals("like")){
//						// add s.inoue 2014/03/18
//					    String filterval = filterClauses[0].getValue().toString();
//					    if(!filterval.startsWith("%"))
//					      filterval = "%"+filterval+"%";
//					      filterClauses[0].setValue(filterval);
//
//						gridParams.getFilteredColumns().put(filterClauses[0].getAttributeName(),
//								new FilterWhereClause[] { filterClauses[0], null });
//			      }
//			    }
//		    }else{
////				FilterWhereClause clauseDesign = new FilterWhereClause();
////				clauseDesign.setAttributeName("NENDO");
////				clauseDesign.setOperator("=");
////				clauseDesign.setValue(2012);
////
////				gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
////						new FilterWhereClause[] { clauseDesign, null });
//		    }
//
////			// add s.inoue 2013/02/25
////			try {
////				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
////			} catch (SQLException ex) {
////				logger.warn(ex.getMessage());
////			}
//
//			StringBuilder sb = new StringBuilder();
//			// eidt s.inoue 2013/06/18
//			sb.append("SELECT HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,KENSA_HOUHOU,HISU_FLG,DS_KAGEN,DS_JYOUGEN,JS_KAGEN,");
//			sb.append("JS_JYOUGEN,TANI,TANKA_KENSIN,BIKOU,JYOUGEN,KAGEN ");
//			sb.append(" FROM T_KENSHINMASTER");
//
//			// add s.inoue 2012/04/20
//			sb.append(" WHERE HKNJANUM <> '99999999'");
//			// add s.inoue 2013/01/31
//			sb.append(" ORDER BY HKNJANUM,XMLITEM_SEQNO");
//			return QueryUtil
//					.getQuery(
//							conn,
//							new UserSessionParameters(),
//							sb.toString(),
//							vals,
//							attribute2dbField,
//							JKenshinMasterMaintenanceListData.class,
//							"Y", "N",
//							null, gridParams, JApplication.gridViewMasterCount, true);
//
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
			attribute2dbField.put("HKNJANUM","HKNJANUM");
			attribute2dbField.put("KOUMOKU_CD","KOUMOKU_CD");
			attribute2dbField.put("KOUMOKU_NAME","KOUMOKU_NAME");
			attribute2dbField.put("KENSA_HOUHOU","KENSA_HOUHOU");
			attribute2dbField.put("HISU_FLG","HISU_FLG");
			attribute2dbField.put("DS_KAGEN","DS_KAGEN");
			attribute2dbField.put("DS_JYOUGEN","DS_JYOUGEN");
			attribute2dbField.put("JS_KAGEN","JS_KAGEN");
			attribute2dbField.put("JS_JYOUGEN","JS_JYOUGEN");
			attribute2dbField.put("TANI","TANI");
			attribute2dbField.put("KAGEN","KAGEN");
			attribute2dbField.put("JYOUGEN","JYOUGEN");
			attribute2dbField.put("KIJYUNTI_HANI","KIJYUNTI_HANI");
			attribute2dbField.put("TANKA_KENSIN","TANKA_KENSIN");
			attribute2dbField.put("BIKOU","BIKOU");

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);

			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//検索・ソート条件の設定
			FilterSetting filterSetting = new FilterSetting(JApplication.SCREEN_MASTER_KENSHIN_CODE, grid.getColumnContainer());
			filterSetting.setFilterSort(firstViewFlg, grid.getSavedJCheckBox(), gridParams, filteredColumns, currentSortedColumns, currentSortedVersusColumns, null);

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,KENSA_HOUHOU,HISU_FLG,DS_KAGEN,DS_JYOUGEN,JS_KAGEN,");
			sb.append("JS_JYOUGEN,TANI,TANKA_KENSIN,BIKOU,JYOUGEN,KAGEN ");
			sb.append(" FROM T_KENSHINMASTER");
			sb.append(" WHERE HKNJANUM <> '99999999'");
			
			if (currentSortedColumns.size()==0  &&
					currentSortedVersusColumns.size()==0){
				sb.append(" ORDER BY HKNJANUM,XMLITEM_SEQNO");
			}
			
			//データの取得実行
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sb.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JKenshinMasterMaintenanceListData.class,
											"Y",
											"N",
											null,
											gridParams,
											JApplication.gridViewMasterCount,
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
						JErrorMessage.show("M3832", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
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

			StringBuilder sb = new StringBuilder();
			sb.append("delete from T_KENSHINMASTER where HKNJANUM=? and KOUMOKU_CD=?");

			stmt = conn.prepareStatement(sb.toString());
			for (int i = 0; i < persistentObjects.size(); i++) {
				JKenshinMasterMaintenanceListData vo = (JKenshinMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getHKNJANUM());
				stmt.setString(2, vo.getKOUMOKU_CD());
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

//	/**
//	   * Method invoked when the user has clicked on save button and the grid is in EDIT mode.
//	   * @param rowNumbers row indexes related to the changed rows
//	   * @param oldPersistentObjects old value objects, previous the changes
//	   * @param persistentObjects value objects relatied to the changed rows
//	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
//	   */
//	  public Response updateRecords(int[] rowNumbers,ArrayList oldPersistentObjects,ArrayList persistentObjects) throws Exception {
//	    return new VOListResponse(persistentObjects,false,persistentObjects.size());
//	  }

	 /**
	   * Method invoked when the user has clicked on save button and the grid is in EDIT mode.
	   * @param rowNumbers row indexes related to the changed rows
	   * @param oldPersistentObjects old value objects, previous the changes
	   * @param persistentObjects value objects relatied to the changed rows
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  @Override
	public Response updateRecords(int[] rowNumbers,ArrayList oldPersistentObjects,ArrayList persistentObjects) throws Exception {
	    PreparedStatement stmt = null;

	    // add s.inoue 2012/05/08
	    RETURN_VALUE retValue = JErrorMessage.show("M3830", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M3831"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb.append("UPDATE T_KENSHINMASTER");
	    	sb.append(" SET TANI=?, BIKOU=?, KIJYUNTI_HANI=?, HISU_FLG=?,DS_KAGEN=?,");
	    	// eidt s.inoue 2013/05/10
	    	// sb.append(" DS_JYOUGEN=?, JS_KAGEN=?, JS_JYOUGEN=?, KAGEN=?, JYOUGEN=?, TANKA_KENSIN=?");
	    	sb.append(" DS_JYOUGEN=?, JS_KAGEN=?, JS_JYOUGEN=?, TANKA_KENSIN=?");
	    	sb.append(" WHERE HKNJANUM=? and KOUMOKU_CD=?");

	    	stmt = conn.prepareStatement(sb.toString());

	      JKenshinMasterMaintenanceListData vo = null;
	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JKenshinMasterMaintenanceListData)persistentObjects.get(i);
	        if (vo == null)
	        	break;
	        // 項目順
	        stmt.setString(1,vo.getTANI());
	        stmt.setString(2,vo.getBIKOU());
	        stmt.setString(3,vo.getKIJYUNTI_HANI());
	        stmt.setString(4,vo.getHISU_FLG());
	        stmt.setString(5,vo.getDS_KAGEN());
	        stmt.setString(6,vo.getDS_JYOUGEN());
	        stmt.setString(7,vo.getJS_KAGEN());
	        // eidt s.inoue 2013/05/10
	        stmt.setString(8,vo.getJS_JYOUGEN());
//	        stmt.setString(9,vo.getKAGEN());
//	        stmt.setString(8,vo.getJYOUGEN());
	        stmt.setString(9,vo.getTANKA_KENSIN());

	        stmt.setString(10,vo.getHKNJANUM());
	        stmt.setString(11,vo.getKOUMOKU_CD());

//	        stmt.setObject(6,vo.getCheckValue()==null || !vo.getCheckValue().booleanValue() ? "N":"Y");
//	        stmt.setObject(7,vo.getRadioButtonValue()==null || !vo.getRadioButtonValue().booleanValue() ? "N":"Y");
//	        stmt.setString(8,vo.getLookupValue());
//	        stmt.setString(9,vo.getFormattedTextValue());
//	        stmt.setBigDecimal(10,vo.getIntValue()==null?null:new BigDecimal(vo.getIntValue().intValue()));
//	        stmt.setInt(11,vo.getProgressive().intValue());
//	        stmt.setString(12,vo.getStringValue());

	        stmt.execute();
	      }
	      return new VOListResponse(persistentObjects,false,persistentObjects.size());
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	      return new ErrorResponse(ex.getMessage());
	    }
	    finally {
	      try {
	        stmt.close();
	        conn.commit();
	      }
	      catch (SQLException ex1) {
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

		  if (attributeName == null)
			  return true;

		  if (attributeName.equals("HISU_FLG")){
			  return validatedData.setHISU_FLG((String)newValue);
		  }else if (attributeName.equals("DS_KAGEN")){
			  return validatedData.setDS_KAGEN((String)newValue);
		  }else if (attributeName.equals("DS_JYOUGEN")){
			  return validatedData.setDS_JYOUGEN((String)newValue);
		  }else if (attributeName.equals("JS_KAGEN")){
			  return validatedData.setJS_KAGEN((String)newValue);
		  }else if (attributeName.equals("JS_JYOUGEN")){
			  return validatedData.setJS_JYOUGEN((String)newValue);
		  }else if (attributeName.equals("TANI")){
			  return validatedData.setTANI((String)newValue);
// del s.inoue 2013/05/10
//		  }else if (attributeName.equals("KAGEN")){
//			  return validatedData.setKAGEN((String)newValue);
//		  }else if (attributeName.equals("JYOUGEN")){
//			  return validatedData.setJYOUGEN((String)newValue);
		  }else if (attributeName.equals("KIJYUNTI_HANI")){
			  return validatedData.setKIJYUNTI_HANI((String)newValue);
		  }else if (attributeName.equals("TANKA_KENSIN")){
			  return validatedData.setTANKA_KENSIN((String)newValue);
		  }else if (attributeName.equals("BIKOU")){
			  return validatedData.setBIKOU((String)newValue);
		  }
		return true;

//		  validatedData.setHISU_FLG(hisuFlag)
//		  validatedData.setHisuFlag((String) grid.getInputContext());

// openswing s.inoue 2011/02/03
//		  if (attributeName.equals("currencyValue") || attributeName.equals("numericValue")) {
//	      grid.getGrid().getBottomTable().reload();
//	    }
//	    if (attributeName.equals("intValue") && new Integer(0).equals(newValue)) {
//	      // zero value not allowed...
//	      return false;
//	    }
//	    return true;
	  }
//
//
//	  /**
//	   * Callback method invoked when the user has selected another row.
//	   * @param rowNumber selected row index
//	   */
//	  public void rowChanged(int rowNumber) {
//	  }

//	 /**
//	   * @param grid grid
//	   * @param row selected row index
//	   * @param attributeName attribute name that identifies the selected grid column
//	   * @return <code>true</code> if the selected cell is editable, <code>false</code> otherwise
//	   */
//	  public boolean isCellEditable(GridControl grid,int row,String attributeName) {
//		  JKenshinMasterMaintenanceListData vo = (JKenshinMasterMaintenanceListData)grid.getVOListTableModel().getObjectForRow(row);
////	    if ("formattedTextValue".equals(attributeName))
////	      return vo.getCheckValue()!=null && vo.getCheckValue().equals("Y");
////	      return vo.getCheckValue()!=null && vo.getCheckValue().booleanValue();
//	    return grid.isFieldEditable(row,attributeName);
//	  }

	// edit n.ohkubo 2014/10/01　追加 start　「戻る」ボタンの処理（テーブルの表示項目の「表示 or 非表示」を登録）
	/**
	 * 項目の表示 or 非表示の状態を、DBへ登録する
	 */
	protected void preserveColumnStatus() {
		try {
			JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

			StringBuilder sbwhere_master = new StringBuilder();
			
			if (JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_CD)){
				// 非表示
				sbwhere_master.append(" KOUMOKU_CD = '0'");
			}else{
				// 表示
				sbwhere_master.append(" KOUMOKU_CD = '1'");
			}
			if (JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_NAME)){
				// 非表示
				sbwhere_master.append(" ,KOUMOKU_NAME = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,KOUMOKU_NAME = '1'");
			}
			if (JApplication.flag_Master.contains(FlagEnum_Master.KENSA_HOUHOU)){
				// 非表示
				sbwhere_master.append(" ,KENSA_HOUHOU = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,KENSA_HOUHOU = '1'");
			}
			if (JApplication.flag_Master.contains(FlagEnum_Master.HISU_FLG)){
				// 非表示
				sbwhere_master.append(" ,HISU_FLG = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,HISU_FLG = '1'");
			}
			if (JApplication.flag_Master.contains(FlagEnum_Master.DS_KAGEN)){
				// 非表示
				sbwhere_master.append(" ,DS_KAGEN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,DS_KAGEN = '1'");
			}		
			if (JApplication.flag_Master.contains(FlagEnum_Master.DS_JYOUGEN)){
				// 非表示
				sbwhere_master.append(" ,DS_JYOUGEN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,DS_JYOUGEN = '1'");
			}		
			if (JApplication.flag_Master.contains(FlagEnum_Master.JS_KAGEN)){
				// 非表示
				sbwhere_master.append(" ,JS_KAGEN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,JS_KAGEN = '1'");
			}		
			if (JApplication.flag_Master.contains(FlagEnum_Master.JS_JYOUGEN)){
				// 非表示
				sbwhere_master.append(" ,JS_JYOUGEN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,JS_JYOUGEN = '1'");
			}		
			if (JApplication.flag_Master.contains(FlagEnum_Master.TANI)){
				// 非表示
				sbwhere_master.append(" ,TANI = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,TANI = '1'");
			}		
			if (JApplication.flag_Master.contains(FlagEnum_Master.KAGEN)){
				// 非表示
				sbwhere_master.append(" ,KAGEN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,KAGEN = '1'");
			}		
			if (JApplication.flag_Master.contains(FlagEnum_Master.JYOUGEN)){
				// 非表示
				sbwhere_master.append(" ,JYOUGEN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,JYOUGEN = '1'");
			}	
			if (JApplication.flag_Master.contains(FlagEnum_Master.KIJYUNTI_HANI)){
				// 非表示
				sbwhere_master.append(" ,KIJYUNTI_HANI = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,KIJYUNTI_HANI = '1'");
			}			
			if (JApplication.flag_Master.contains(FlagEnum_Master.TANKA_KENSIN)){
				// 非表示
				sbwhere_master.append(" ,TANKA_KENSIN = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,TANKA_KENSIN = '1'");
			}	
			if (JApplication.flag_Master.contains(FlagEnum_Master.BIKOU)){
				// 非表示
				sbwhere_master.append(" ,BIKOU = '0'");
			}else{
				// 表示
				sbwhere_master.append(" ,BIKOU = '1'");
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE T_SCREENCOLUMNS SET ");
			sb.append(sbwhere_master.toString());
			sb.append(" WHERE SCREEN_CD = ");
			sb.append(JQueryConvert.queryConvert(JApplication.SCREEN_MASTER_KENSHIN_CODE));
			
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
	
	// edit n.ohkubo 2015/03/01　追加　start　「初期値」ボタンの処理
	/**
	 * 「再読込」押下後のコールバック
	 * @see org.openswing.swing.table.client.GridController#afterReloadGrid()
	 */
	@Override
	public void afterReloadGrid() {
		super.afterReloadGrid();
		
		//初期値ボタンで変更した情報を戻す
		afterGridDefaultButtonProcessing();
	}
	
	/**
	 * 「登録」押下後のコールバック
	 * @see org.openswing.swing.table.client.GridController#afterEditGrid(org.openswing.swing.client.GridControl)
	 */
	@Override
	public void afterEditGrid(GridControl grid) {
		super.afterEditGrid(grid);
		
		//初期値ボタンで変更した情報を戻す
		afterGridDefaultButtonProcessing();
	}
	
	/**
	 * 画面再表示時の処理（初期値ボタン関係専用）
	 */
	private void afterGridDefaultButtonProcessing() {
		
		//初期値ボタン非活性化
		grid.buttonDefault.setEnabled(false);
		
		//色つきのセルを戻す
		if (grid.tableCellRendererList != null) {
			//退避してある初期設定のTableCellRendererを設定
			if (grid.tableCellRendererList.get(0) != null) {
				grid.getGrid().getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("DS_KAGEN")).setCellRenderer(grid.tableCellRendererList.get(0));
			}
			if (grid.tableCellRendererList.get(1) != null) {
				grid.getGrid().getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("DS_JYOUGEN")).setCellRenderer(grid.tableCellRendererList.get(1));
			}
			if (grid.tableCellRendererList.get(2) != null) {
				grid.getGrid().getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("JS_KAGEN")).setCellRenderer(grid.tableCellRendererList.get(2));
			}
			if (grid.tableCellRendererList.get(3) != null) {
				grid.getGrid().getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("JS_JYOUGEN")).setCellRenderer(grid.tableCellRendererList.get(3));
			}
			
			//初期化しておく
			grid.tableCellRendererList = null;
		}
		
		//ツールチップの初期化（表示値をそのまま表示する）
		grid.afterDefaultToolTipText = null;
	}
	
	/**
	 * デフォルト（HKNJANUM=99999999）のデータを取得する
	 * 
	 * @return
	 */
	protected Map<String, List<String>> getDefaultData() {
		
		//DBからデフォルトのデータを取得する
		String sql = "SELECT KOUMOKU_CD, DS_KAGEN, DS_JYOUGEN, JS_KAGEN, JS_JYOUGEN FROM T_KENSHINMASTER WHERE HKNJANUM = '99999999'";
		ArrayList<Hashtable<String, String>> dbResult = null;
		try {
			dbResult = JApplication.kikanDatabase.sendExecuteQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//取得したデータを設定
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for (int i = 0; i < dbResult.size(); i++) {
			Hashtable<String, String> temp = dbResult.get(i);
			
			//男性下限、男性上限、女性下限、女性上限の順番で格納
			List<String> rowList = new ArrayList<String>();
			rowList.add(temp.get("DS_KAGEN"));
			rowList.add(temp.get("DS_JYOUGEN"));
			rowList.add(temp.get("JS_KAGEN"));
			rowList.add(temp.get("JS_JYOUGEN"));
			
			//キーは項目コード
			result.put(temp.get("KOUMOKU_CD"), rowList);
		}
		return result;
	}
	// edit n.ohkubo 2015/03/01　追加　end　「初期値」ボタンの処理
}
