package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshinpattern;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.export.java.ExportOptions;
import org.openswing.swing.message.receive.java.ErrorResponse;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.receive.java.VOResponse;
import org.openswing.swing.message.receive.java.ValueObject;
import org.openswing.swing.message.send.java.FilterWhereClause;
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
public class JKenshinpatternMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator,IKenshinpatternMasterMaintenanceFrame {

	private JKenshinpatternMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
	private JKenshinpatternMasterMaintenanceListFrame frame;

	private JKenshinpatternMasterMaintenanceListData validatedData = new JKenshinpatternMasterMaintenanceListData();
	private static Logger logger = Logger.getLogger(JKenshinpatternMasterMaintenanceListFrameCtrl.class);

	public JKenshinpatternMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JKenshinpatternMasterMaintenanceListFrame(conn, this);
	}

	public JKenshinpatternMasterMaintenanceListFrame getGridControl(){
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
//		new xJKenshinpatternMasterMaintenanceDetailCtl(this.grid, null, conn);
//		return false;
		return true;
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
	 * gridがdouble clickedされた時、Callback関数
	 * @param rowNumber selected row index
	 * @param persistentObject v.o. related to the selected row
	 */
	public void doubleClick(int rowNumber, ValueObject persistentObject) {

		JKenshinpatternMasterMaintenanceListData vo = (JKenshinpatternMasterMaintenanceListData)persistentObject;
		// 呼び出し
		System.out.println(vo.getK_P_NO());
		JScene.ChangeScene(grid,
				new JKenshinPatternMaintenanceEditFrameCtrl(
						vo.getK_P_NO()),
				new WindowRefreshEvent());
	}
//	/**
//	 * 遷移先の画面から戻ってきた場合
//	 */
//	public class WindowRefreshEvent extends WindowAdapter {
//		public void windowClosed(WindowEvent e) {
//			grid.setEnabled(true);
//		}
//	}
	/**
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/05/12
			// grid.reloadData();
			grid.reloadButton.doClick();
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
			attribute2dbField.put("K_P_NO","K_P_NO");
			attribute2dbField.put("K_P_NAME","K_P_NAME");
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
						  // add s.inoue 2014/03/18
					      String filterval = filterClauses[0].getValue().toString();
					      if(!filterval.startsWith("%"))
					    	  filterval = "%"+filterval+"%";
					    	  filterClauses[0].setValue(filterval);

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

			// eidt s.inoue 2011/04/08
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}

			String sql ="SELECT K_P_NO, K_P_NAME, BIKOU FROM T_KENSHIN_P_M WHERE K_P_NO <> -1 ORDER BY K_P_NO";
			return QueryUtil
					.getQuery(
							conn,
							new UserSessionParameters(),
							sql,
							vals,
							attribute2dbField,
							JKenshinpatternMasterMaintenanceListData.class,
							"Y", "N",
							null, gridParams, JApplication.gridViewMasterCount, true);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		}
	}

	// add s.inoue 2013/02/27
    public void afterDeleteGrid()
    {
    	grid.reloadButton.doClick();
    }

	/**
	 * 削除ボタンイベント ※gridがREADONLY
	 * @param persistentObjects value objects to delete (related to the currently selected rows)
	 * @return an ErrorResponse value object in case of errors, VOResponse if the operation is successfully completed
	 */
	public Response deleteRecords(ArrayList persistentObjects) throws Exception {
		PreparedStatement stmtPm = null;
		PreparedStatement stmtPs = null;
		try {

			// add s.inoue 2012/04/20
			for (int i = 0; i < persistentObjects.size(); i++) {
				JKenshinpatternMasterMaintenanceListData vo = (JKenshinpatternMasterMaintenanceListData)persistentObjects.get(i);
				// eidt s.inoue 2013/04/10
				// if (vo.getK_P_NO().equals("9999"))return new ErrorResponse(JErrorMessage.getMessageValue("M3945"));
				if (vo.getK_P_NO().equals("9999") || vo.getK_P_NO().equals("1") || vo.getK_P_NO().equals("2"))
					return new ErrorResponse(JErrorMessage.getMessageValue("M3945"));
			}

			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM T_KENSHIN_P_M WHERE K_P_NO = ?");
			sb.append(" AND K_P_NO <> '9999' ");
			stmtPm = conn.prepareStatement(sb.toString());

			StringBuilder sb2 = new StringBuilder();
			sb2.append("DELETE FROM T_KENSHIN_P_S WHERE K_P_NO = ?");
			sb.append(" AND K_P_NO <> '9999' ");
			stmtPs = conn.prepareStatement(sb2.toString());

			JKenshinpatternMasterMaintenanceListData vo = null;

			for (int i = 0; i < persistentObjects.size(); i++) {
				vo = (JKenshinpatternMasterMaintenanceListData)persistentObjects.get(i);
				stmtPm.setString(1, vo.getK_P_NO());
				stmtPs.setString(1, vo.getK_P_NO());
				stmtPm.execute();
				stmtPs.execute();
			}

		    // add s.inoue 2012/04/24
			// 削除できないので、作り直す処理
			JApplication.domains.remove(JApplication.patternDomain.getDomainId());

			// 健診パターンリスト
			ArrayList<Hashtable<String, String>> items = null;
			String pattern = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO";
			try {
				items = JApplication.kikanDatabase.sendExecuteQuery(pattern);
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

			// patternDomain = new Domain("T_KENSHIN_P_M");
			JApplication.patternDomain = new Domain("T_KENSHIN_P_M");

			for (int i = 0; i < items.size(); i++) {
				Hashtable<String, String> Item;
				Item = items.get(i);

				String kpNO = Item.get("K_P_NO");
				String kpNAME = Item.get("K_P_NAME");
				// add s.inoue 2011/11/28
				if(!kpNO.equals("9999")){
					JApplication.patternDomain.addDomainPair(kpNO,kpNAME);
				}
			}
			JApplication.domains.put(
					JApplication.patternDomain.getDomainId(),JApplication.patternDomain);


//			JApplication.patternDomain = new Domain(domainId)
//
//			Domain dm = JApplication.clientSettings.getDomain(JApplication.patternDomain.getDomainId());
//
//			dm.addDomainPair(vo.getK_P_NO(), vo.getK_P_NAME());

			return new VOResponse(new Boolean(true));
		} catch (SQLException ex) {
			ex.printStackTrace();
			return new ErrorResponse(ex.getMessage());
		} finally {
			try {
				if (stmtPm != null && stmtPm != null){
					stmtPm.close();
					stmtPs.close();
					conn.commit();
				}
			} catch (SQLException ex1) {
			}
		}
	}

	  /**
	   * Method invoked when the user has clicked on save button and the grid is in INSERT mode.
	   * @param rowNumbers row indexes related to the new rows to save
	   * @param newValueObjects list of new value objects to save
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  public Response insertRecords(int[] rowNumbers, ArrayList newValueObjects) throws Exception {

		PreparedStatement stmt = null;

		if (rowNumbers[0] < 0)
			return new VOListResponse(newValueObjects,false,newValueObjects.size());

		try {
	    	  StringBuffer sb = new StringBuffer();
			  sb.append("INSERT INTO T_KENSHIN_P_M (K_P_NO,K_P_NAME, BIKOU)");
			  sb.append("VALUES (?,?,?)");

			  stmt = conn.prepareStatement(sb.toString());

		      // 行毎追加処理
			  JKenshinpatternMasterMaintenanceListData vo = null;

		      for(int i=0;i<newValueObjects.size();i++) {
		        vo = (JKenshinpatternMasterMaintenanceListData)newValueObjects.get(i);
		        stmt.setString(1,vo.getK_P_NO());
		        stmt.setString(2,vo.getK_P_NAME());
		        stmt.setString(3,vo.getBIKOU());
		        stmt.execute();
		      }

		      // add s.inoue 2012/04/24
			  Domain dm = JApplication.clientSettings.getDomain(JApplication.patternDomain.getDomainId());
			  dm.addDomainPair(vo.getK_P_NO(), vo.getK_P_NAME());
			  JApplication.domains.put(
						JApplication.patternDomain.getDomainId(),JApplication.patternDomain);

      return new VOListResponse(newValueObjects,false,newValueObjects.size());
		}catch (SQLException ex) {
			// logger.error(ex.getMessage());
	      return new ErrorResponse(JErrorMessage.getMessageValue("M9912"));
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
	   * Method invoked when the user has clicked on save button and the grid is in EDIT mode.
	   * @param rowNumbers row indexes related to the changed rows
	   * @param oldPersistentObjects old value objects, previous the changes
	   * @param persistentObjects value objects relatied to the changed rows
	   * @return an ErrorResponse value object in case of errors, VOListResponse if the operation is successfully completed
	   */
	  public Response updateRecords(int[] rowNumbers,ArrayList oldPersistentObjects,ArrayList persistentObjects) throws Exception {
	    PreparedStatement stmt = null;

	    // add s.inoue 2012/05/08
	    RETURN_VALUE retValue = JErrorMessage.show("M3946", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M3947"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb = new StringBuilder();
	    	sb.append("UPDATE T_KENSHIN_P_M SET K_P_NAME = ? , BIKOU = ? ");
	    	sb.append(" WHERE K_P_NO =  ? ");

	    	stmt = conn.prepareStatement(sb.toString());

	    	JKenshinpatternMasterMaintenanceListData vo = null;
	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JKenshinpatternMasterMaintenanceListData)persistentObjects.get(i);
	        if (vo == null)
	        	break;
	        // 項目順
	        stmt.setString(1,vo.getK_P_NAME());
	        stmt.setString(2,vo.getBIKOU());
	        stmt.setString(3,vo.getK_P_NO());

	        stmt.execute();
	      }

		    // eidt s.inoue 2012/06/29
			// 削除できないので、作り直す処理
			JApplication.domains.remove(JApplication.patternDomain.getDomainId());

			// 健診パターンリスト
			ArrayList<Hashtable<String, String>> items = null;
			String pattern = "SELECT K_P_NO,K_P_NAME FROM T_KENSHIN_P_M WHERE K_P_NO >= 0 ORDER BY K_P_NO";
			try {
				items = JApplication.kikanDatabase.sendExecuteQuery(pattern);
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

			// patternDomain = new Domain("T_KENSHIN_P_M");
			JApplication.patternDomain = new Domain("T_KENSHIN_P_M");

			for (int i = 0; i < items.size(); i++) {
				Hashtable<String, String> Item;
				Item = items.get(i);

				String kpNO = Item.get("K_P_NO");
				String kpNAME = Item.get("K_P_NAME");
				// add s.inoue 2011/11/28
				if(!kpNO.equals("9999")){
					JApplication.patternDomain.addDomainPair(kpNO,kpNAME);
				}
			}
			JApplication.domains.put(
					JApplication.patternDomain.getDomainId(),JApplication.patternDomain);

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

		  if (attributeName.equals("K_P_NAME")){
			  return validatedData.setK_P_NAME((String)newValue);
		  }else if (attributeName.equals("DS_KAGEN")){
			  return validatedData.setBIKOU((String)newValue);
		  }
		return true;
	}

	/* frameInterface */
	@Override
	public GridControl getGrid() {
		return frame.getGrid();
	}

	@Override
	public void jbInit() {
		try {
			frame.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadData() {
		frame.reloadData();
	}

	@Override
	public void setControl(Connection conn,
			JKenshinpatternMasterMaintenanceListFrameCtrl controller) {
		frame.setControl(conn, controller);

	}

}
