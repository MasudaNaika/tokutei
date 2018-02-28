package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import org.apache.log4j.Logger;
import org.openswing.swing.table.client.GridController;
import java.util.*;

import org.openswing.swing.message.receive.java.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern.JKenshinpatternMasterMaintenanceListFrameCtrl;

import org.openswing.swing.message.send.java.FilterWhereClause;
import org.openswing.swing.table.java.GridDataLocator;
import org.openswing.swing.table.client.Grid;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.server.QueryUtil;
import org.openswing.swing.server.UserSessionParameters;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.java.Consts;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.export.java.ExportOptions;

import sun.java2d.Disposer;

/**
 * 一覧Ctl画面
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator {

	private JKenshinMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
	private JKenshinMasterMaintenanceListFrame frame;

	private JKenshinMasterMaintenanceListData validatedData = new JKenshinMasterMaintenanceListData();
	private static Logger logger = Logger.getLogger(JKenshinMasterMaintenanceListFrameCtrl.class);

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
	 public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.CSV_FORMAT1,ExportOptions.CSV_FORMAT2,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
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
//			return attributeName+" at row "+row;
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

			attribute2dbField.put("HKNJANUM","HKNJANUM");
			attribute2dbField.put("KOUMOKU_CD","KOUMOKU_CD");
			// eidt s.inoue 2011/06/02
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
//				FilterWhereClause clauseDesign = new FilterWhereClause();
//				clauseDesign.setAttributeName("NENDO");
//				clauseDesign.setOperator("=");
//				clauseDesign.setValue(2012);
//
//				gridParams.getFilteredColumns().put(clauseDesign.getAttributeName(),
//						new FilterWhereClause[] { clauseDesign, null });
		    }

//			// add s.inoue 2013/02/25
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT HKNJANUM,KOUMOKU_CD,KOUMOKU_NAME,KENSA_HOUHOU,HISU_FLG,DS_KAGEN,DS_JYOUGEN,JS_KAGEN,");
			sb.append("JS_JYOUGEN,TANI,TANKA_KENSIN,BIKOU");
			sb.append(" FROM T_KENSHINMASTER");

			// add s.inoue 2012/04/20
			sb.append(" WHERE HKNJANUM <> '99999999'");
			// add s.inoue 2013/01/31
			sb.append(" ORDER BY HKNJANUM,XMLITEM_SEQNO");
			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sb.toString(),
							vals,
							attribute2dbField,
							JKenshinMasterMaintenanceListData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewMasterCount, true);

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

}
