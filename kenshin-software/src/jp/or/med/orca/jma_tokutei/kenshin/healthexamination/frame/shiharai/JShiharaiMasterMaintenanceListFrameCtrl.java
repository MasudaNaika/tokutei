package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.shiharai;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.FilterSetting;
import jp.or.med.orca.jma_tokutei.common.scene.JScene;

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
public class JShiharaiMasterMaintenanceListFrameCtrl
		extends GridController
		implements GridDataLocator,IShiharaiMasterMaintenanceFrame {

	private static final long serialVersionUID = -7957602576869375335L;	// edit n.ohkubo 2014/10/01
	
	private JShiharaiMasterMaintenanceListFrame grid = null;
	private Connection conn = null;
	private JShiharaiMasterMaintenanceListFrame frame;
	private static Logger logger = Logger.getLogger(JShiharaiMasterMaintenanceListFrameCtrl.class);
	
	private boolean firstViewFlg = true;	// edit n.ohkubo 2014/10/01

	public JShiharaiMasterMaintenanceListFrameCtrl(Connection conn) {
		this.conn = conn;
		grid = new JShiharaiMasterMaintenanceListFrame(conn, this);
	}

	public JShiharaiMasterMaintenanceListFrame getGridControl(){
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
		JShiharaiMasterMaintenanceListData vo = (JShiharaiMasterMaintenanceListData)persistentObject;
		JScene.ChangeScene(grid,new JShiharaiMasterMaintenanceEditFrameCtrl(
				vo.getSHIHARAI_DAIKO_NO()),new WindowRefreshEvent());
	}

    /**
     * Callbackメソッド
     * exportダイアログを表示する前に呼び出す
     * gridに定義されたformatを再定義して呼び出せます
     * @return list of available formats; possible values: ExportOptions.XLS_FORMAT, ExportOptions.CSV_FORMAT1, ExportOptions.CSV_FORMAT2, ExportOptions.XML_FORMAT, ExportOptions.XML_FORMAT_FAT, ExportOptions.HTML_FORMAT, ExportOptions.PDF_FORMAT, ExportOptions.RTF_FORMAT; default value: ClientSettings.EXPORTING_FORMATS
     */
	 @Override
	public String[] getExportingFormats() {
		 return new String[]{ ExportOptions.XLS_FORMAT,ExportOptions.HTML_FORMAT,ExportOptions.XML_FORMAT };
	 }

	// edit n.ohkubo 2014/10/01　start　ソート設定の不具合や0件メッセージ等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）
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
//			attribute2dbField.put("SHIHARAI_DAIKO_NO","SHIHARAI_DAIKO_NO");
//			attribute2dbField.put("SHIHARAI_DAIKO_NAME","SHIHARAI_DAIKO_NAME");
//			attribute2dbField.put("SHIHARAI_DAIKO_ZIPCD","SHIHARAI_DAIKO_ZIPCD");
//			attribute2dbField.put("SHIHARAI_DAIKO_ADR","SHIHARAI_DAIKO_ADR");
//			attribute2dbField.put("SHIHARAI_DAIKO_TEL","SHIHARAI_DAIKO_TEL");
//
//			// eidt s.inoue 2011/04/08
//			try {
//				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
//			} catch (SQLException ex) {
//				logger.warn(ex.getMessage());
//			}
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
//			    	  // add s.inoue 2014/03/18
//			    	  String filterval = filterClauses[0].getValue().toString();
//			    	  if(!filterval.startsWith("%"))
//			    		  filterval = "%"+filterval+"%";
//			    	  filterClauses[0].setValue(filterval);
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
//			// eidt s.inoue 2012/05/07
//			StringBuilder sql = new StringBuilder();
//				sql.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL FROM T_SHIHARAI");
//
//			if (currentSortedVersusColumns.size() == 0)
//				sql.append(" ORDER BY SHIHARAI_DAIKO_NO");
//
//			return QueryUtil
//				.getQuery(
//				conn,
//				new UserSessionParameters(),
//				sql.toString(),
//				vals,
//				attribute2dbField,
//				JShiharaiMasterMaintenanceListData.class,
//				"Y", "N",
//				null, gridParams, JApplication.gridViewMasterCount, true);
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
		// PreparedStatement stmt = null;
		try {
			Map<String, String> attribute2dbField = new HashMap<String, String>();
			attribute2dbField.put("SHIHARAI_DAIKO_NO","SHIHARAI_DAIKO_NO");
			attribute2dbField.put("SHIHARAI_DAIKO_NAME","SHIHARAI_DAIKO_NAME");
			attribute2dbField.put("SHIHARAI_DAIKO_ZIPCD","SHIHARAI_DAIKO_ZIPCD");
			attribute2dbField.put("SHIHARAI_DAIKO_ADR","SHIHARAI_DAIKO_ADR");
			attribute2dbField.put("SHIHARAI_DAIKO_TEL","SHIHARAI_DAIKO_TEL");
			attribute2dbField.put("DUMMY","");	// edit n.ohkubo 2014/10/01　追加　openswingのバグ対応（putする数と"並び順"で指定した個数が一致するとIndexOutOfBoundsExceptionが発生し、検索画面が開かなくなる対応）

			GridParams gridParams = new GridParams(action, startIndex, filteredColumns, currentSortedColumns, currentSortedVersusColumns, otherGridParams);
			
			try {
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);
			} catch (SQLException ex) {
				logger.warn(ex.getMessage());
			}
			
			//検索・ソート条件の設定用共通クラス
			FilterSetting filterSetting = new FilterSetting();
			
			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加する
			filterSetting.setLikeColumns(filteredColumns, true);


			StringBuilder sql = new StringBuilder();
			sql.append("SELECT SHIHARAI_DAIKO_NO, SHIHARAI_DAIKO_NAME, SHIHARAI_DAIKO_ZIPCD, SHIHARAI_DAIKO_ADR, SHIHARAI_DAIKO_TEL FROM T_SHIHARAI");
			if (currentSortedVersusColumns.size() == 0) {
				sql.append(" ORDER BY SHIHARAI_DAIKO_NO");
			}
			
			//データの取得実行
			Response result = QueryUtil.getQuery(
											conn,
											new UserSessionParameters(),
											sql.toString(),
											new ArrayList<String>(),
											attribute2dbField,
											JShiharaiMasterMaintenanceListData.class,
											"Y",
											"N",
											null,
											gridParams,
											JApplication.gridViewMasterCount,
											true
			);

			//「を含む」検索は「like %入力値%」で検索するため、入力値に"%"を付加しているので、付加した"%"を削除する
			filterSetting.setLikeColumns(filteredColumns, false);

			//検索画面へソート条件を反映する（検索後、検索画面を閉じて再度開いたときに、値が反映されていない現象の対応）
			ColumnContainer columnContainer = grid.getColumnContainer();
			filterSetting.setSortColumnsAfter(columnContainer, currentSortedColumns, currentSortedVersusColumns);
			
			//0件の場合、メッセージの表示（画面オープン初回以外）
			if (!firstViewFlg) {
				if ((result != null) && (!result.isError() && (result instanceof VOListResponse))){
					if (((VOListResponse)result).getRows().size() == 0) {
//						JErrorMessage.show("M3550", getGridControl());	// edit n.ohkubo 2015/03/01　削除
						JErrorMessage.show("M5114", getGridControl());	// edit n.ohkubo 2015/03/01　追加　メッセージの変更
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
	// edit n.ohkubo 2014/10/01　end　ソート設定の不具合や0件メッセージ等の修正を行うので、メソッドを新規で作成（既存のロジックは全コメント）


	// eidt s.inoue 2012/05/08
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
	    RETURN_VALUE retValue = JErrorMessage.show("M5112", getGridControl());
		if (retValue == RETURN_VALUE.NO)
			return new ErrorResponse(JErrorMessage.getMessageValue("M5113"));

	    try {

	    	StringBuilder sb = new StringBuilder();
	    	sb.append("UPDATE T_SHIHARAI");
	    	sb.append(" SET SHIHARAI_DAIKO_NAME=?, SHIHARAI_DAIKO_ZIPCD=?, SHIHARAI_DAIKO_ADR=?,SHIHARAI_DAIKO_TEL=?");
	    	sb.append(" WHERE SHIHARAI_DAIKO_NO=?");

	    	stmt = conn.prepareStatement(sb.toString());

	      JShiharaiMasterMaintenanceListData vo = null;
	      for(int i=0;i<persistentObjects.size();i++) {
	        vo = (JShiharaiMasterMaintenanceListData)persistentObjects.get(i);
	        if (vo == null)
	        	break;
	        // 項目順
	        stmt.setString(1,vo.getSHIHARAI_DAIKO_NAME());
	        stmt.setString(2,vo.getSHIHARAI_DAIKO_ZIPCD());
	        stmt.setString(3,vo.getSHIHARAI_DAIKO_ADR());
	        stmt.setString(4,vo.getSHIHARAI_DAIKO_TEL());
	        stmt.setString(5,vo.getSHIHARAI_DAIKO_NO());
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
				JShiharaiMasterMaintenanceListData vo = (JShiharaiMasterMaintenanceListData)persistentObjects.get(i);
				stmt.setString(1, vo.getSHIHARAI_DAIKO_NO());
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
	 * 遷移先の画面から戻ってきた場合
	 */
	public class WindowRefreshEvent extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			grid.setEnabled(true);
			// eidt s.inoue 2011/04/08
			grid.reloadButton.doClick();
		}
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
			JShiharaiMasterMaintenanceListFrameCtrl controller) {
		frame.setControl(conn, controller);
	}

}
